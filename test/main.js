/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
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
