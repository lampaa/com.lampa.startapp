cordova-plugin-startapp # Upd 7.11.2015
===========================================================================

Phonegap 3.x.x plugin for check or launch other application in android device.

===========================================
Install: ```cordova plugin add com.lampa.startapp```

Install: ```cordova plugin add https://github.com/lampaa/com.lampa.startapp.git```

Delete:  ```cordova plugin rm com.lampa.startapp```

Delete previos version:  ```cordova plugin rm org.apache.cordova.startapp```

===========================================

Manually installation for Android:
```on progress...```

use:  **ANDROID**



_Check application for installed_

If success, returned application info: `versionName`, `packageName`, `versionCode` and `applicationInfo`.
```javascript
navigator.startApp.check("com.application.name", function(message) { /* success */
    console.log("app exists: "); 
    console.log(message.versionName); 
    console.log(message.packageName); 
    console.log(message.versionCode); 
    console.log(message.applicationInfo); 
}, 
function(error) { /* error */
    console.log(error);
});
```

_Start application without parameters_

```js
navigator.startApp.start("com.application.name", function(message) {  /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log(error);
});
```

_Start application with params. First variant with package and activity:
```js
navigator.startApp.start([["com.app.name", "com.app.name.Activity"]], function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log(error);
});
```
_Start application with params. Second variant with action and action params:
```js
navigator.startApp.start([["action", "ACTION_NAME", "PACKAGE", "TYPE", "URI"]], function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log(error);
});
```
`ACTION_NAME` these is a Intent flag [Intent Flags](http://developer.android.com/reference/android/content/Intent.html) (MAIN, VIEW, CALL, etc..).

`PACKAGE` these is a Intent method [setPackage](http://developer.android.com/intl/ru/reference/android/content/Intent.html#setPackage(java.lang.String)) (optional or set null)

`TYPE` these is a Intent method [setType](http://developer.android.com/intl/ru/reference/android/content/Intent.html#setType(java.lang.String)) (optional or set null)

`URI` these is a Intent data Uri [Uri](http://developer.android.com/intl/ru/reference/android/content/Intent.html#Intent(java.lang.String, android.net.Uri)) (optional or set null)


> **Important!** First value of first parameter of _start_ method can be either a string or an array. And the array must contain two parameters. Example:
> 
> ["com.app.name", [....]] or [["com.app.name", "com.app.name.Activity"] [....]]


_Start application with only values_

```js
navigator.startApp.start([[...], ["set_param1", "set_param2",..., "set_paramN"]], function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log(error);
});
```
_ANDROID: Start application with key:value parameters_

```js
navigator.startApp.start([[...], [{"key1":"value1"},{"key2":"value2"}, {...}, {"keyN":"valueN"}]], function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log(error);
});
```
Example, call application with activity and key:value param:

```js
navigator.startApp.start([["app.com.name", "app.com.name.Activity"], [{"product_id":"100"}]], ...);
```



Example, call skype:
```js
navigator.startApp.start([["action", "VIEW"], ["skype:+79109999999"]], ...);
```
Example, call phone:
```js
navigator.startApp.start([["action", "CALL"], ["tel:+79109999999"]], ...);
```
Example, call browser:
```js
navigator.startApp.start([["action", "VIEW"], ["https://github.com/lampaa"]], ...);
```
Example, call facebook:
```js
navigator.startApp.start([["action", "VIEW"], ["fb://facewebmodal/f?href=https://www.facebook.com/GitHub"]], ...);
```
Example, call whatsapp:
```js
navigator.startApp.start([["action", "SEND", "com.whatsapp", "text/plain"], [{"android.intent.extra.TEXT":"Text..."}]], ...);
```
Example, call whatsapp chat:
```js
navigator.startApp.start([["action", "SEND", "com.whatsapp", "text/plain", "+79123456789"], [{"android.intent.extra.TEXT":"Text..."}, {"chat": true}]], ...);
```



Use **iOS**

_Check iOS application for installed_

```js
navigator.startApp.check("twitter://", function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log(error);
});
```

_Start iOS application_

```js
navigator.startApp.start("twitter://", function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log(error);
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
