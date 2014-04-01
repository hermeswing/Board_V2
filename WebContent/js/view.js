$(document).ready(function() {
	
	$('#reply-button').button({
		icons: {
			primary: 'ui-icon-pencil'
		}
	});
	
	$('#modify-button').button({
		icons: {
			primary: 'ui-icon-gear'
		}
	});
	
	$('#delete-button').button({
		icons: {
			primary: 'ui-icon-circle-close'
		}
	});
	
	$('#list-button').button({
		icons: {
			primary: 'ui-icon-arrowreturn-1-w'
		}
	});
		
	$('img', $('.tinymce')).each(function() {
		$(this).wrap($('<a>', {
			'href': $(this).attr('src'),
			'rel': 'lightbox'
		}));
	});
});