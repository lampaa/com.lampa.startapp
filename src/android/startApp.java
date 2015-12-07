/**
	com.lampa.startapp
	https://github.com/lampaa/com.lampa.startapp
	
	Phonegap 3 plugin for check or launch other application in android device (iOS support).
	bug tracker: https://github.com/lampaa/com.lampa.startapp/issues
*/
package com.lampa.startapp;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import java.util.Iterator;
import android.net.Uri;

/**
 * This class provides access to vibration on the device.
 */
public class startApp extends CordovaPlugin {

    /**
     * Constructor.
     */
    public startApp() { }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  True when the action was valid, false otherwise.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            this.start(args, callbackContext);
        }
		else if(action.equals("check")) {
			this.check(args.getString(0), callbackContext);
		}
		
		return true;
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------
    /**
     * startApp
     */
    public void start(JSONArray args, CallbackContext callback) {
		
		Intent LaunchIntent;
		
		String com_name = null;
		
		String activity = null;
		String spackage = null;
		String intetype = null;
		String intenuri = null;
		
		try {
			if (args.get(0) instanceof JSONArray) {
				com_name = args.getJSONArray(0).getString(0);
				activity = args.getJSONArray(0).getString(1);
				
				if(args.getJSONArray(0).length() > 2) {
					spackage = args.getJSONArray(0).getString(2);
				}
				
				if(args.getJSONArray(0).length() > 3) {
					intetype = args.getJSONArray(0).getString(3);
				}
				
				if(args.getJSONArray(0).length() > 4) {
					intenuri = args.getJSONArray(0).getString(4);
				}
			}
			else {
				com_name = args.getString(0);
			}
		
			/**
			 * call activity
			 */
			if(activity != null) {
				if(com_name.equals("action")) {
					/**
					 * . < 0: VIEW
					 * . >= 0: android.intent.action.VIEW
					 */
					if(activity.indexOf(".") < 0) {
						activity = "android.intent.action." + activity;
					}
					
					// if uri exists
					if(intenuri != null) {
						LaunchIntent = new Intent(activity, Uri.parse(intenuri));
					}
					else {
						LaunchIntent = new Intent(activity);
					}
				}
				else {
					LaunchIntent = new Intent();
					LaunchIntent.setComponent(new ComponentName(com_name, activity));
				}
			}
			else {
				LaunchIntent = this.cordova.getActivity().getPackageManager().getLaunchIntentForPackage(com_name);
			}
			
			/**
			 * setPackage, http://developer.android.com/intl/ru/reference/android/content/Intent.html#setPackage(java.lang.String)
			 */
			if(spackage != null) {
				LaunchIntent.setPackage(spackage);
			}
			
			/**
			 * setType, http://developer.android.com/intl/ru/reference/android/content/Intent.html#setType(java.lang.String)
			 */
			if(intetype != null) {
				LaunchIntent.setType(intetype);
			}
			
			/**
			 * put arguments
			 */
			if(args.length() > 1) {
				JSONArray params = args.getJSONArray(1);
				JSONObject key_value;
				String key;
				String value;

				for(int i = 0; i < params.length(); i++) {
					if (params.get(i) instanceof JSONObject) {
						Iterator<String> iter = params.getJSONObject(i).keys();
						
						 while (iter.hasNext()) {
							key = iter.next();
							try {
								value = params.getJSONObject(i).getString(key);
								
								LaunchIntent.putExtra(key, value);
							} catch (JSONException e) {
								callback.error("json params: " + e.toString());
							}
						}
					}
					else {
						LaunchIntent.setData(Uri.parse(params.getString(i)));
					}
				}
			}
			
			/**
			 * start activity
			 */
			this.cordova.getActivity().startActivity(LaunchIntent);
			callback.success();
			
		} catch (JSONException e) {
			callback.error("json: " + e.toString());
		} catch (Exception e) {
			callback.error("intent: " + e.toString());
        }
    }

    /**
     * checkApp
     */	 
	public void check(String component, CallbackContext callback) {
		PackageManager pm = this.cordova.getActivity().getApplicationContext().getPackageManager();
		try {
			/**
			 * get package info
			 */
			PackageInfo PackInfo = pm.getPackageInfo(component, PackageManager.GET_ACTIVITIES);
			
			/**
			 * create json object
			 */
			JSONObject info = new JSONObject();
			
			info.put("versionName", PackInfo.versionName);
			info.put("packageName", PackInfo.packageName);
			info.put("versionCode", PackInfo.versionCode);
			info.put("applicationInfo", PackInfo.applicationInfo);
			
			callback.success(info);
		} catch (Exception e) {
			callback.error(e.toString());
		}
	}
}
