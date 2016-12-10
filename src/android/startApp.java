/**
	com.lampa.startapp
	https://github.com/lampaa/com.lampa.startapp
	
	Phonegap plugin for check or launch other application in android device (iOS support).
	bug tracker: https://github.com/lampaa/com.lampa.startapp/issues
*/
package com.lampa.startapp;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import android.util.Base64;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.Iterator;
import android.net.Uri;
import java.lang.reflect.Field;
import android.content.ActivityNotFoundException;
import android.util.Log;
import android.os.Bundle;

public class startApp extends CordovaPlugin {

	public static final String TAG = "startApp";
	private CallbackContext callback = null;
	private JSONObject params = null;
    public startApp() { }
	private boolean NO_PARSE_INTENT_VALS = false;

	/* hardcoded shit starts */
	String[] filePath = new String[] {
		"/storage/emulated/0/DocumentScanner/New Doc/New Doc.pdf",
		"/storage/emulated/0/DocumentScanner/New Doc/Image1.jpg"
	};
	int fileType = 0;
	/* hardcoded shit ends */

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  Always return true.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            this.start(args, callbackContext);
        }
	else if(action.equals("go")) {
            this.start(args, callbackContext);
        }
        else if(action.equals("check")) {
                this.check(args, callbackContext);
        }
        else if(action.equals("getExtras")) {
                this.getExtras(callbackContext);
        }
        else if(action.equals("getExtra")) {
                this.getExtra(args, callbackContext);
        }

        return true;
    }


    /**
     * startApp
     */
    public void start(JSONArray args, CallbackContext callback) {
		Intent LaunchIntent;
		JSONArray flags;
		JSONArray component;
		
		JSONObject extra;
		JSONObject key_value;
		String key;
		
		int i;
		
		try {
			if (args.get(0) instanceof JSONObject) {
				params = args.getJSONObject(0);
			
				/**
				 * disable parsing intent values
				 */
				if(params.has("no_parse")) {
					NO_PARSE_INTENT_VALS = true;
				}
				
				/**
				 * set application
				 * http://developer.android.com/reference/android/content/Intent.html(java.lang.String)
				 */
				if(params.has("application")) {
					PackageManager manager = cordova.getActivity().getApplicationContext().getPackageManager();
					LaunchIntent = manager.getLaunchIntentForPackage(params.getString("application"));
						
					if (LaunchIntent == null) {
						callback.error("Application \""+ params.getString("application") +"\" not found!");
						return;
					}
				}
				/**
				 * set application
				 * http://developer.android.com/reference/android/content/Intent.html (java.lang.String)
				 */
				else if(params.has("intent")) {
					LaunchIntent = new Intent(params.getString("intent"));
				}
				else {
					LaunchIntent = new Intent();
				}
        		

				/**
				 * set package
				 * http://developer.android.com/reference/android/content/Intent.html#setPackage(java.lang.String)
				 */
				if(params.has("package")) {
					LaunchIntent.setPackage(params.getString("package"));
				}
				
				/**
				 * set action
				 * http://developer.android.com/intl/ru/reference/android/content/Intent.html#setAction%28java.lang.String%29
				 */
				if(params.has("action")) {
					LaunchIntent.setAction(getIntentValueString(params.getString("action")));	
				}
				
				/**
				 * set category
				 * http://developer.android.com/intl/ru/reference/android/content/Intent.html#addCategory%28java.lang.String%29
				 */
				if(params.has("category")) {
					LaunchIntent.addCategory(getIntentValueString(params.getString("category")));	
				}
				
				/**
				 * set type
				 * http://developer.android.com/intl/ru/reference/android/content/Intent.html#setType%28java.lang.String%29
				 */
				if(params.has("type")) {
					LaunchIntent.setType(params.getString("type"));	
				}
				

				
				/**
				 * set data (uri)
				 * http://developer.android.com/intl/ru/reference/android/content/Intent.html#setData%28android.net.Uri%29
				 */
				if(params.has("uri")) {
					LaunchIntent.setData(Uri.parse(params.getString("uri")));
				}
				
				/**
				 * set flags
				 * http://developer.android.com/intl/ru/reference/android/content/Intent.html#addFlags%28int%29
				 */
				if(params.has("flags")) {
					flags = params.getJSONArray("flags");
					
					for(i=0; i < flags.length(); i++) {
						LaunchIntent.addFlags(getIntentValue(flags.getString(i))); 	
					}
				}
				
				/**
				 * set component
				 * http://developer.android.com/intl/ru/reference/android/content/Intent.html#setComponent%28android.content.ComponentName%29
				 */
				if(params.has("component")) {
					component = params.getJSONArray("component");
					
					if(component.length() == 2) {
						LaunchIntent.setComponent(new ComponentName(component.getString(0), component.getString(1)));	
					}
				}
				
				/**
				 * set extra fields
				 */
				if(!args.isNull(1)) {
					extra = args.getJSONObject(1);
					Iterator<String> iter = extra.keys();
							
					while (iter.hasNext()) {
						key = iter.next();
						
						Object value = extra.get(key);
						if (value != null) {
							String keyStr = parseExtraName(key);
							if (value instanceof Number)
								LaunchIntent.putExtra(keyStr, (Number) value);
							else if (value instanceof Boolean)
								LaunchIntent.putExtra(keyStr, (Boolean) value);
							else
								LaunchIntent.putExtra(keyStr, (String) value);
							/* hardcoded shit starts */
							if ("fileType".equals(key)) {
								fileType = "pdf".equals((String) value) ? 0 : 1;
							}
							/* hardcoded shit ends */
						}
					}
				}

				/**
				 * launch intent
				 */
				if(params.has("intentstart") && "startActivityForResult".equals(params.getString("intentstart"))) {
					this.callback = callback;
					cordova.setActivityResultCallback(this);
					cordova.getActivity().startActivityForResult(LaunchIntent, 2);
				}
				else if(params.has("intentstart") && "sendBroadcast".equals(params.getString("intentstart"))) {
					cordova.getActivity().sendBroadcast(LaunchIntent);
					callback.success();
				}
				else {
					cordova.getActivity().startActivity(LaunchIntent);
					callback.success();
				}
			}
			else {
				callback.error("Incorrect params, array is not array object!");
			}
		} 
		catch (JSONException e) {
			callback.error("JSONException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			callback.error("IllegalAccessException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			callback.error("NoSuchFieldException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (ActivityNotFoundException e) {
			callback.error("ActivityNotFoundException: " + e.getMessage());
			e.printStackTrace();
		}
    }

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		webView.loadUrl("javascript:console.log('onActivityResult requestCode:" + requestCode + ", resultCode:" + resultCode + "');");
		try {
			JSONObject extras = new JSONObject();
			extras.put("requestCode", requestCode);
			extras.put("resultCode", resultCode);
			if (intent != null) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					for (String key : bundle.keySet()) {
						extras.put(key, bundle.get(key));
					}
				}
				Uri uri = intent.getData();
				if (uri != null) {
					extras.put("uri", uri.toString());
				} else {
					extras.put("uri", extras.get("result"));
				}
			}
			/* hardcoded shit starts */
			webView.loadUrl("javascript:console.log('onActivityResult fileType:" + fileType + "');");
			extras.put("uri", filePath[fileType]);
			/* hardcoded shit ends */
			if (params.has("uri") && "data".equals(params.getString("uri"))) {
				String uriPath = (String) extras.get("uri");
				File file = new File(uriPath);
				String base64 = encodeFileToBase64Binary(file);
				extras.put("dataUri", base64);
			}
			this.callback.success(extras);
		} catch (JSONException e) {
			this.callback.error(e.getMessage());
		}
	}

	private String encodeFileToBase64Binary(File file) {
		int size = (int) file.length();
		byte[] bytes = new byte[size];
		try {
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
			buf.read(bytes, 0, bytes.length);
			buf.close();
			return Base64.encodeToString(bytes, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * checkApp
     */	 
	public void check(JSONArray args, CallbackContext callback) {
		JSONObject params;
		
		try {
			if (args.get(0) instanceof JSONObject) {
				params = args.getJSONObject(0);
		
		
				if(params.has("package")) {
					PackageManager pm = cordova.getActivity().getApplicationContext().getPackageManager();
					
					/**
					 * get package info
					 */
					PackageInfo PackInfo = pm.getPackageInfo(params.getString("package"), PackageManager.GET_ACTIVITIES);
						
					/**
					 * create json object
					 */
					JSONObject info = new JSONObject();
						
					info.put("versionName", PackInfo.versionName);
					info.put("packageName", PackInfo.packageName);
					info.put("versionCode", PackInfo.versionCode);
					info.put("applicationInfo", PackInfo.applicationInfo);
						
					callback.success(info);
				}
				else {
					callback.error("Value \"package\" in null!");
				}
			}
			else {
				callback.error("Incorrect params, array is not array object!");
			}
		} catch (JSONException e) {
			callback.error("json: " + e.toString());
			e.printStackTrace();
		}
		catch (NameNotFoundException e) {
			callback.error("NameNotFoundException: " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * getExtras
	 */
	public void getExtras(CallbackContext callback) {
		try {
			Bundle extras = cordova.getActivity().getIntent().getExtras(); 
			JSONObject info = new JSONObject();

			if (extras != null) {
				for (String key : extras.keySet()) {
					info.put(key, extras.get(key).toString());
				}
			}
			
			callback.success(info);
		}
		catch(JSONException e) {
			callback.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * getExtra
	 */
	public void getExtra(JSONArray args, CallbackContext callback) {
		try {
			String extraName = parseExtraName(args.getString(0));
			Intent extraIntent = cordova.getActivity().getIntent();

			if(extraIntent.hasExtra(extraName)) {
				String extraValue = extraIntent.getStringExtra(extraName);
				
				if (extraValue == null) {
					extraValue = ((Uri) extraIntent.getParcelableExtra(extraName)).toString();
				}

				callback.success(extraValue);
			}
			else {
				callback.error("extra field not found");	
			}
		}
		catch(JSONException e) {
			callback.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * functions
	 */
	private String parseExtraName(String extraName) {
		String parseIntentExtra = extraName;
		
		try {
			parseIntentExtra = getIntentValueString(extraName);
		}
		catch(NoSuchFieldException e) {
			parseIntentExtra = extraName;	
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
			return extraName;
		}
		
		Log.e(TAG, parseIntentExtra);
		
		return parseIntentExtra;
	}
	
	private String getIntentValueString(String flag) throws NoSuchFieldException, IllegalAccessException {
		
		if(NO_PARSE_INTENT_VALS) {
			return flag;
		}
		
		Field field = Intent.class.getDeclaredField(flag);
		field.setAccessible(true);

		return (String) field.get(null);
	}
	
	private int getIntentValue(String flag) throws NoSuchFieldException, IllegalAccessException {
		Field field = Intent.class.getDeclaredField(flag);
		field.setAccessible(true);
		
		return field.getInt(null);
	}
}
