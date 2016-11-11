Manually installation for Android:

Add to ```/my-app/platforms/android/res/xml/config.xml```:
```xml
<feature name="startApp">
	<param name="android-package" value="com.lampa.startapp.startApp" />
</feature>
```
Add to ```/my-app/platforms/android/assets/www/cordova_plugins.js```:
```javascript
{
	"file": "plugins/com.lampa.startapp/www/startApp.manually.js",
	"id": "com.lampa.startapp.startapp",
	"merges": [
		"startApp"
	]
}
```
Add to ```/my-app/platforms/android/assets/www/plugins/com.lampa.startapp/www/``` file:
https://github.com/lampaa/com.lampa.startapp/blob/master/www/startApp.manually.js

Add to ```/my-app/platforms/android/src/com/lampa/startapp/``` file:
https://github.com/lampaa/com.lampa.startapp/blob/master/src/android/startApp.java
