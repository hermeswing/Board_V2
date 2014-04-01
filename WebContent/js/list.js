$(document).ready(function() {
	// 검색 버튼
	$('#search-button').button({
		icons: {
			primary: 'ui-icon-search'
		}
	});
	// 쓰기 버튼
	$('#write-button').button({
		icons: {
			primary: 'ui-icon-pencil'
		}
	});
	// 목록 버튼
	$('#list-button').button({
		icons: {
			primary: 'ui-icon-refresh'
		}
	});
	// 페이징 버튼들
	$('#ui-paging-first').button({
		text: false,
		icons: {
			primary: 'ui-icon-seek-first'
		}
	});
	$('#ui-paging-prev').button({
		text: false,
		icons: {
			primary: 'ui-icon-triangle-1-w'
		}
	}).css({
		'margin-right': 5 
	});
	$('#ui-paging-next').button({
		text: false,
		icons: {
			primary: 'ui-icon-triangle-1-e'
		}
	}).css({
		'margin-left': 5 
	});
	$('#ui-paging-last').button({
		text: false,
		icons: {
			primary: 'ui-icon-seek-end'
		}
	});
});