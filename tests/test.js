var TAG = "TEST";
var LAST_EXTRA = 0;
var ONE_TO_PARAM = ['action', 'uri', 'package', 'type', 'category', 'intentstart'];

jQuery(document).on('deviceready ready', function(e) {
	console.log(TAG, e.type);
	
	var TEMPLATE = $('#template').html();
	var $result = $('#result');
	
	/**
	 * form builder
	 */
	$('form input, form select, form').on('change build', function() {
		var data = $('form').serializeArray();
		var build = {};
		var array_build = [];
		var i, g;
		var flags = []; 
		var extras = []; 
		var extras_assoc = {};
		var extras_build = [];
		var component = [];
		var match;
		var key;
		var out = "";
		
		for(var i=0; i < data.length; i++) {
			console.log(data[i].name, data[i].name.indexOf('action'));
			
			for(g=0; g < ONE_TO_PARAM.length; g++) {
				if(data[i].name.indexOf(ONE_TO_PARAM[g]) != -1) {
					if(data[i].value) {
						build[ONE_TO_PARAM[g]] = '"'+ONE_TO_PARAM[g]+'":"'+data[i].value+'"';
					}
				}	
			}
			
			if(data[i].name.indexOf('flags') != -1) {
				if(data[i].value) {
					flags.push(data[i].value);
				}
			}
			
			if(data[i].name.indexOf('component') != -1) {
				if(data[i].value) {
					component.push(data[i].value);
				}
			}
			
			if(data[i].name.indexOf('extras') != -1) {
				if(data[i].value) {
					match = data[i].name.match(/\[([0-9]+)\]/);
					
					if(extras[match[1]] == undefined) {
						extras[match[1]] = [];
					}
					
					extras[match[1]].push(data[i].value);
				}
			}	
		}
		
		
		// flags
		if(flags.length > 0) {
			build['flags'] = '"flags":["'+flags.join('","')+'"]';
		}
		
		// component
		if(component.length > 0) {
			build['component'] = '"component": ["'+component.join('","')+'"]';
		}
		
		// component
		if(extras.length > 0) {
			for(i=0; i < extras.length; i++) {
				if(extras[i][0] && extras[i][1]) {
					extras_assoc[extras[i][0]] = extras[i][1];
				}
			}
			
			for (key in extras_assoc) {
				extras_build.push('"'+key+'":"'+extras_assoc[key]+'"');
			}
		}
		
		console.log(extras_build);
		
		
		
		for (key in build) {
			array_build.push(build[key]);
		}
		
		out = TEMPLATE
			.replace('{$VALUES$}', array_build.join(",\n	"))
			.replace('{$EXTRAS$}', "{ /* extras */\n	" + extras_build.join(",\n	") + "\n}");
		
		
		$result.html(out);
	});
	
	/**
	 * buttons
	 */
	$('#build').on('click', function() {
		$('form').trigger('build');
	});
	
	$('#run').on('click', function() {
		// eval
		console.log(eval($result.html()));
	});
	
	/**
	 * extras
	 */
	$('#add_extras').on('click', function() {
		LAST_EXTRA++;
		
		$(this).before("<div class='extras'>\
			<input type='text' name='extras["+LAST_EXTRA+"][0]'> = \
			<input type='text' name='extras["+LAST_EXTRA+"][1]'>\
			<button class='remove_extras' type='button'>- Remove</button>\
		</div>");
	});
	
	$('.l_extras').on('click', '.remove_extras', function() {
		if(confirm('Really delete?')) {
			$(this).parent().remove();
		}
	});
});