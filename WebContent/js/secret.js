$(document).ready(function() {
	// 텍스트 입력 엔터키 이벤트
	$('input[name=pwd]').bind({
		keydown: function(e) {
			if(e.keyCode == 13) {
				$('#submit-button').click();
			}
		}
	});
	
	// 확인 버튼
	$('#submit-button').button({
		icons: {
			primary: 'ui-icon-circle-check'
		}
	}).bind({
		click: function() {
			var obj = $('input[name=pwd]');
			if(obj.val() == '') {
				$('#msg').error('암호를 입력하세요', {
					override: true,
					fadeOut: true
				});
				return;
			}
			
			$.ajax({
				url: 'auth.do',
				data: {
					'board_id': $('input[name=bid]').val(),
					'pwd': $('input[name=pwd]').val(),
					'query': $('input[name=query]').val()
				},
				type: 'POST',
				dataType: 'json',
				beforeSend: function(jqXHR, settings) {
					var msg = $('#msg');
					// 메세지 모두 삭제					
					msg.children().each(function() {
						$(this).remove();
					});
					
					var busy = $('<div />', {
						id: 'ui-busy'
					});
					
					busy.append($('<img />', {
						id: 'ui-busy',
						src: 'images/busy.gif',
						align: 'center',
						css: {
							margin: 5
						}
					})).append($('<span />', {
						text: '암호 확인중...',
						css: {
							color: '#000000'
						}
					}));
					
					$('#msg').append(busy);
					
					// 확인버튼 비활성화
					$('#submit-button').button('disable');
				},
				success: function(json) {
					if(json.auth == true) {
						// 비밀번호 일치. 승인.
						$('form').get()[0].submit();
						
						$('#msg').append($('<div />', {
							css: {
								'margin': 3,
								'color': '#000000'
							},
							text: '페이지로 이동합니다...'
						}));
					} else {
						$('#msg').error(json.msg, {
							override: true,
							fadeOut: true
						});
						
						$('input[name=pwd]').select().focus();
					}
				},
				error: function(resp, status, msg) {
					$.dialog(resp.status, msg, true);
				},
				complete: function() {
					$('#ui-busy').remove();
					// 확인버튼 활성화
					$('#submit-button').button('enable');
				}
			});
					
		} // click
	
	
	});
	
	$('#cancel-button').button({
		text: false,
		icons: {
			primary: 'ui-icon-arrowreturn-1-w'
		}
	}).bind({
		click: function() {
			history.back(-1);
		}
	});
	
	$('#list-button').button({
		text: false,
		icons: {
			primary: 'ui-icon-refresh'
		}
	});
	
	$('input[name=pwd]').focus();
});