cordova-plugin-startapp
===========================================================================

Phonegap 3.*.* plugin for check or launch other application in android device.


Install: ```cordova plugin add https://github.com/lampaa/org.apache.cordova.startapp.git```

Delete:  ```cordova plugin rm org.apache.cordova.startapp```

use: 

**Check the application is installed**

```js
navigator.startApp.check("com.example.hello", function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log('47', error);
});
```

**Start application without parameters**

```js
navigator.startApp.start("com.example.hello", function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log('47', error);
});
```

**Start application with parameters**

```js
navigator.startApp.start([
	"com.example.hello", // applucation
	"com.example.hello.MainActivity", // activity
	"product_id", // key
	"100" // value
], function(message) { /* success */
	console.log(message); // => OK
}, 
function(error) { /* error */
	console.log('47', error);
});
```