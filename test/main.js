/**
	com.lampa.startapp
	https://github.com/lampaa/com.lampa.startapp
	
	Phonegap 3 plugin for check or launch other application in android device (iOS support).
	bug tracker: https://github.com/lampaa/org.apache.cordova.startapp/issues
*/
function init() {
	/**
	 * check the application is installed
	 */
	navigator.startApp.check('com.teaway.teamenu', function(message) { /* success */
		console.log(message); // => OK
	}, 
	function(error) { /* error */
		console.log('47', error);
	});
	
	/**
	 * start application without parameters
	 */
	navigator.startApp.start('com.teaway.teamenu', function(message) { /* success */
		console.log(message); // => OK
	}, 
	function(error) { /* error */
		console.log('47', error);
	});
	
	/**
	 * start application with parameters
	 */
	navigator.startApp.start([
		'com.teaway.teamenu', // applucation
		'com.teaway.teamenu.MainActivity', // activity
		'product_id', // key
		'102' // value
	], function(message) { /* success */
		console.log(message); // => OK
	}, 
	function(error) { /* error */
		console.log('47', error);
	});
}
