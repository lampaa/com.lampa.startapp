/**
	com.lampa.startapp, ver. 6.1.6
	https://github.com/lampaa/com.lampa.startapp
	
	Phonegap plugin for check or launch other application in android device (iOS support).
	bug tracker: https://github.com/lampaa/com.lampa.startapp/issues
*/
package com.lampa.startapp;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class startApp extends Assets {

	public static final String TAG = "startApp";
    public startApp() { }

	private boolean NO_PARSE_INTENT_VALS = false;
	private HashMap<Integer, BroadcastReceiver> broadcastReceiverHashMap = new HashMap<Integer, BroadcastReceiver>();
	private CallbackContext callbackContext;
  
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  Always return true.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		/*
		from fork lampaa/com.lampa.startapp 
        this.callbackContext = callbackContext;
		*/
        
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
		else if(action.equals("receiver")) {
			this.receiver(args, callbackContext);
		}
		else if(action.equals("unReceiver")) {
			this.receiver(args, callbackContext);
		}

        return true;
    }


	/**
	 *
	 * @param args
	 * @param callback
	 */
	private void receiver(JSONArray args, final CallbackContext callback) {
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				JSONObject result = new JSONObject();

				try {
					result.put("_ACTION_VALUE_FORMAT_", intent.getAction());

					Bundle bundle = intent.getExtras();
					if (bundle != null) {
						for (String key : bundle.keySet()) {
							result.put(key, bundle.get(key));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, result);
				pluginResult.setKeepCallback(true);

				callback.sendPluginResult(pluginResult);
			}
		};

		try {
			JSONArray values = args.getJSONArray(0);
			IntentFilter filter = new IntentFilter();

			for(int i=0; i < values.length(); i++) {
				filter.addAction(values.getString(i));
			}


			//cordova.getActivity().getApplicationContext()
			cordova.getActivity().getApplicationContext().registerReceiver(receiver, filter);
			broadcastReceiverHashMap.put(receiver.hashCode(), receiver);

			PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, receiver.hashCode());
			pluginResult.setKeepCallback(true);

			callback.sendPluginResult(pluginResult);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			callback.error("Error register receiver: " + ex.getMessage());
		}
	}


	/**
     * startApp
     */
    public void start(JSONArray args, CallbackContext callback) {
		Intent LaunchIntent;
		JSONObject params;
		JSONArray flags;
		JSONArray component;
		
		JSONObject extra;
		String key;
		int i;
		
		try {
			if (args.get(0) instanceof JSONObject) {
				params = args.getJSONObject(0);
			
				/**
				 * disable parsing intent values
				 */
				if(params.has("noParse")) {
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
				 * set intent
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
						JSONObject obj = extra.optJSONObject(key);
						if(obj != null) {
							LaunchIntent.putExtra(parseExtraName(key), toMap(obj));
						}
						else {
							Object value = extra.get(key);

							if(value instanceof Integer) {
								LaunchIntent.putExtra(parseExtraName(key), extra.getInt(key));
							}

							if(value instanceof String) {
								LaunchIntent.putExtra(parseExtraName(key), extra.getString(key));
							}

							if(value instanceof Boolean) {
								LaunchIntent.putExtra(parseExtraName(key), extra.getBoolean(key));
							}
						}
					}
				}

				/**
				 * launch intent
				 */
				PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
				pluginResult.setKeepCallback(true);

				if(params.has("intentstart") && "startActivityForResult".equals(params.getString("intentstart"))) {
                    // Set up the activity result callback to this class
                   cordova.setActivityResultCallback (this);
					/*
					from wyobi frok
					*/
					callbackContext = callback;

					cordova.getActivity().startActivityForResult(LaunchIntent, 1);
				}
                else
                {
                    if(params.has("intentstart") && "sendBroadcast".equals(params.getString("intentstart"))) {
                        cordova.getActivity().sendBroadcast(LaunchIntent);	
                    }
                    else {
                        cordova.getActivity().startActivity(LaunchIntent);	
                    }
					/*
					from fork lampaa/com.lampa.startapp 
                    callback.success();
					*/

					/*
					from wyobi frok
					*/
					callback.sendPluginResult(pluginResult);
					
                }
			}
			else {
				callback.error("Incorrect params, array is not array object!");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			callback.error(e.getClass() + ": " + e.getMessage());
		}
    }

   /**
     * For start to be able to return results to our app from the intent we need to wait 
     * for the result to return. Then only call the callback with our result data.
     */	 
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		from fork lampaa/com.lampa.startapp 
		if(callbackContext != null) {
			JSONObject result = new JSONObject();

			try {
				result.put("_ACTION_requestCode_", requestCode);
				result.put("_ACTION_resultCode_", resultCode);

				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					for (String key : bundle.keySet()) {
						result.put(key, bundle.get(key));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, result);
			pluginResult.setKeepCallback(true);

			callbackContext.sendPluginResult(pluginResult);
		}
		*/

        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                JSONObject info = new JSONObject();
                
                if (extras != null) {
                    for (String key : extras.keySet()) {
                        info.put(key, extras.get(key).toString());
                    }
                }
                
                this.callbackContext.success(info);
            } else {
                this.callbackContext.error("Cancelled");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    /**
     * checkApp
     */	 
	private void check(JSONArray args, CallbackContext callback) {
		JSONObject params;
		
		try {
			if (args.get(0) instanceof JSONObject) {
				params = args.getJSONObject(0);
		
		
				if(params.has("package")) {
					PackageManager pm = cordova.getActivity().getApplicationContext().getPackageManager();
					
					// get package info
					final PackageInfo PackInfo = pm.getPackageInfo(params.getString("package"), PackageManager.GET_ACTIVITIES);
						
					// create json object
					JSONObject info = new JSONObject() {{
						put("versionName", PackInfo.versionName);
						put("packageName", PackInfo.packageName);
						put("versionCode", PackInfo.versionCode);
						put("applicationInfo", PackInfo.applicationInfo);
					}};
						
					callback.success(info);
				}
				else {
					callback.error("Value \"package\" in null!");
				}
			}
			else {
				callback.error("Incorrect params, array is not array object!");
			}
		}
		catch (Exception e) {
			callback.error(e.getClass() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * getExtras
	 */
	private void getExtras(CallbackContext callback) {
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
	private void getExtra(JSONArray args, CallbackContext callback) {
		try {
			String extraName = parseExtraName(args.getString(0));
			Intent extraIntent = cordova.getActivity().getIntent();

			if(extraIntent.hasExtra(extraName)) {
				String extraValue = extraIntent.getStringExtra(extraName);
				
				if (extraValue == null) {
					extraValue = (extraIntent.getParcelableExtra(extraName)).toString();
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

	

	/** JSON and Intent parsing helpers **/

	public static HashMap<String, Object> jsonToMap(JSONObject json) throws JSONException {
		HashMap<String, Object> retMap = new HashMap<String, Object>();

		if(json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while(keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}
}
