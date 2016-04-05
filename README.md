cordova plugin startapp
===========================================================================

Phonegap plugin for check or launch other application in android device.

===========================================
Install: ```cordova plugin add com.lampa.startapp```

Install: ```cordova plugin add https://github.com/lampaa/com.lampa.startapp.git```

Delete:  ```cordova plugin rm com.lampa.startapp```

Delete previos version:  ```cordova plugin rm org.apache.cordova.startapp```

===========================================
[Manually installation for Android.](/lampaa/com.lampa.startapp/MANUALLY_INSTALL.md)


NEW! [Script builder](/lampaa/com.lampa.startapp/MANUALLY_INSTALL.md)


use:  **ANDROID**

_Set appication parameters_

```javascript
var sApp = startApp.set({ /* params */
	"action":"ACTION_MAIN",
	"category":"CATEGORY_DEFAULT",
	"type":"text/css",
	"package":"com.lampa.startapp",
	"uri":"file://data/index.html",
	"intentstart":"startActivity",
	"flags":["FLAG_ACTIVITY_CLEAR_TOP","FLAG_ACTIVITY_CLEAR_TASK"],
	"component": ["com.app.name","com.app.name.Activity"]
}, { /* extras */
	"extraKey1":"extraValue1",
	"extraKey2":"extraValue2"
});
```

`action` these is a Intent action [Intent setAction](http://developer.android.com/reference/android/content/Intent.html#setAction(java.lang.String)) (optional, defaul null).

`category` these is a Intent method [Intent addCategory](http://developer.android.com/reference/android/content/Intent.html#addCategory(java.lang.String)) (optional, defaul null).

`type` these is a Intent method [Intent setType](http://developer.android.com/intl/ru/reference/android/content/Intent.html#setType(java.lang.String)) (optional, defaul null).

`package` (default null) these is a Intent method [Intent setPackage](http://developer.android.com/intl/ru/reference/android/content/Intent.html#setPackage(java.lang.String)) (optional, defaul null).

`uri` (default null) these is a Intent data Uri [Intent Uri](http://developer.android.com/intl/ru/reference/android/content/Intent.html#Intent(java.lang.String, android.net.Uri)) (optional, defaul null).

`flags` (default null) these is a Intent method [Intent setFlags](http://developer.android.com/reference/android/content/Intent.html#setFlags(int)) (optional, defaul null).

`component` (default null) these is a Intent method [Intent setComponent](http://developer.android.com/reference/android/content/Intent.html#setComponent(android.content.ComponentName)) (optional, defaul null).


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
If success, values contains data: `versionName`, `packageName`, `versionCode` and `applicationInfo`.


**Samples**

_Set application as only package name_:
```js
var sApp = startApp.set({
	"package":"com.application.name"
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
	"android.intent.extra.TEXT":"Text...",
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
	"type": "video/mp4",
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
