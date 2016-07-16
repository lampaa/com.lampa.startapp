
# cordova plugin startapp

Phonegap plugin for check or launch other application, get extras in phonegap app.

> Install: ```cordova plugin add com.lampa.startapp```
> 
> Install: ```cordova plugin add https://github.com/lampaa/com.lampa.startapp.git```
> 
> Delete:  ```cordova plugin rm com.lampa.startapp```
> 
> Delete previos version:  ```cordova plugin rm org.apache.cordova.startapp```

[Manually installation for Android.](/MANUALLY_INSTALL.md)


**NEW!** [Script builder.](http://lampaa.github.io/com.lampa.startapp/index.html) Create script with UI builder.


# ANDROID

To run other application, you need to build data:
```javascript
var sApp = startApp.set({} params [, {} extras]);
```
| Param | Description | Default | Values |
| --- | --- | --- | --- |
| intent | [Intent(String action)](https://developer.android.com/reference/android/content/Intent.html#Intent%28java.lang.String%29)) | null | String |
| application | [Intent (Context packageContext)](https://developer.android.com/reference/android/content/Intent.html#Intent%28android.content.Context,%20java.lang.Class%3C?%3E%29) | null | String |
| action | [Intent setAction](http://developer.android.com/reference/android/content/Intent.html#setAction(java.lang.String)) | null | String |
| action | [Intent setAction](http://developer.android.com/reference/android/content/Intent.html#setAction(java.lang.String)) | null | String |
| category | [Intent addCategory](http://developer.android.com/reference/android/content/Intent.html#addCategory(java.lang.String)) | null | String |
| type | [Intent setType](http://developer.android.com/intl/ru/reference/android/content/Intent.html#setType(java.lang.String))  | null | String |
| package | [Intent setPackage](http://developer.android.com/intl/ru/reference/android/content/Intent.html#setPackage(java.lang.String)) | null | String |
| uri | [Intent Uri](http://developer.android.com/intl/ru/reference/android/content/Intent.html#Intent(java.lang.String, android.net.Uri)) | null | String |
| flags | [Intent setFlags](http://developer.android.com/reference/android/content/Intent.html#setFlags(int)) | null | String |
| component | [Intent setComponent](http://developer.android.com/reference/android/content/Intent.html#setComponent(android.content.ComponentName)) | null |  String |
| intentstart | set type of start intent  | startActivity | startActivity, startActivityForResult, sendBroadcast |

Extras as a set of key-value:
```javascript
{
	"key1":"value1",
    "key2":"value2"
}
```

_Example_

```javascript
var sApp = startApp.set({ /* params */
	"action":"ACTION_MAIN",
	"category":"CATEGORY_DEFAULT",
	"type":"text/css",
	"package":"com.lampa.startapp",
	"uri":"file://data/index.html",
	"flags":["FLAG_ACTIVITY_CLEAR_TOP","FLAG_ACTIVITY_CLEAR_TASK"],
	"component": ["com.app.name","com.app.name.Activity"],
	"intentstart":"startActivity",
}, { /* extras */
	"EXTRA_STREAM":"extraValue1",
	"extraKey2":"extraValue2"
});
```

```startApp.set()``` return object:
```javascript
sApp.start(function() { /* success */
	console.log("OK");
}, function(error) { /* fail */
	alert(error);
});
```
or
```javascript
sApp.check(function(values) { /* success */
	console.log(values);
}, function(error) { /* fail */
	alert(error);
});
```
If success, ```values``` contains data: `versionName`, `packageName`, `versionCode` and `applicationInfo`.

To get all extra fields use method ```extraFiels```:
```javascript
startApp.extraFields(function(fields) { /* success */
	console.log(fields);
}, function() { /* fail */

});
```
Variable ```fields``` contains object array, example:
```javascript
{
	"key1":"value1",
    "key2":"value2"
}
```

To get one extra field use method ```getExtra```:
```javascript
startApp.getExtra(field, function(value) { /* success */
	console.log(fields);
}, function() { /* fail */

});
```
Variable ```field``` is a String.
Variable ```value``` contains String value.

To has one extra field use method ```extraField```:
```javascript
startApp.hasExtra(field, function() { /* success */
	console.log(fields);
}, function() { /* fail */

});
```
Variable ```field``` is a String.

# Samples

_Set application as only package name_:
```js
var sApp = startApp.set({
	"application":"com.application.name"
});
```

_Set application as intent value and flag ([issue](https://github.com/lampaa/com.lampa.startapp/issues/50))_:
```js
var sApp = startApp.set({
	"intent": "com.shazam.android.intent.actions.START_TAGGING",
	"flags": ["FLAG_ACTIVITY_NEW_TASK"]
}); 
```

_Set application as package and activity_:
```js
var sApp = startApp.set({
	"component": ["com.app.name","com.app.name.Activity"]
});
```

_Set application as action, package, type and Uri_:
```js
var sApp = startApp.set({ /* params */
	"action":"ACTION_MAIN",
	"type":"text/css",
	"package":"com.lampa.startapp",
	"uri":"file://data/index.html"
});
```


_Start application with extra fields_

```js
var sApp = startApp.set({ /* params */
	"component": ["com.app.name","com.app.name.Activity"]
}, { /* extras */
	"extraKey1":"extraValue1",
	"extraKey2":"extraValue2"
});
```


Example, call skype:
```js
startApp.set({ /* params */
	"action": "ACTION_VIEW",
	"uri": "skype:+79109999999"
}).start();
```
Example, call phone:
```js
startApp.set({ /* params */
	"action": "ACTION_CALL",
	"uri": "tel:+79109999999"
}).start();
```
Example, call browser:
```js
startApp.set({ /* params */
	"action": "ACTION_VIEW",
	"uri": "https://github.com/lampaa"
}).start();
```
Example, call facebook:
```js
startApp.set({ /* params */
	"action": "ACTION_VIEW",
	"uri": "fb://facewebmodal/f?href=https://www.facebook.com/GitHub"
}).start();
```
Example, call whatsapp:
```js
startApp.set({ /* params */
	"action": "ACTION_SEND",
	"package": "com.whatsapp",
	"type": "text/plain"
}, {
	"android.intent.extra.TEXT":"Text..."
}).start();
```
Example, call whatsapp chat:
```js
startApp.set({ /* params */
	"action": "ACTION_SEND",
	"package": "com.whatsapp",
	"type": "text/plain",
	"uri": "+79123456789"
}, {
	"EXTRA_TEXT":"Text...",
	"chat": true
}).start();
```
Example, call sms:
```js
startApp.set({ /* params */
	"action": "ACTION_MAIN",
	"category": "CATEGORY_DEFAULT",
	"type": "vnd.android-dir/mms-sms"
}).start();
```
Example, play mp4 video:
```js
startApp.set({ /* params */
	"action": "ACTION_VIEW",
	"uri": "http://domain.com/videofile.mp4",
	"type": "video/mp4"
}).start();
```
Example, open contacts book:
```js
startApp.set({ /* params */
	"action": "ACTION_PICK",
	"uri": "ContactsContract.Contacts.CONTENT_URI",
	"intentstart":"startActivityForResult"
}).start();
```


Use **iOS**

_Set iOS application_

```js
var sApp = startApp.set("twitter://");
```

return ```startApp``` object:
```javascript
sApp.start(function() { /* success */
	console.log("OK");
}, function(error) { /* fail */
	alert(error);
});
```
or
```javascript
sApp.check(function(values) { /* success */
	console.log(values);
}, function(error) { /* fail */
	alert(error);
});
```

===========================================
Tags: 

Cordova start external application.
Android open an external application.
Phonegap start external application.
Launching External Intents Works on Cordova.
Android launch external activities.
Android check app availability.
Android launch application with parameters. 

Prose
