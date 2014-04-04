$(document).ready(function() {
	// TinyMCE
	$('textarea[name=content]').tinymce({
		// Location of TinyMCE script
		script_url : 'js/tiny_mce/tiny_mce.js',
		// General options
		width: '100%',
		height: '300',
		theme: 'advanced',
		plugins : "autolink,advlink,print,fullscreen",
		// theme options
		theme_advanced_buttons1: 'newdocument,|,forecolor,backcolor,|,bold,italic,underline,strikethrough,|,'
			+ 'justifyleft,justifycenter,justifyright,justifyfull,|,bullist,numlist,|,link,unlink,|,'
			+ 'hr,charmap,media,image,|,code,|print,fullscreen',
		theme_advanced_buttons2: '',
		theme_advanced_buttons3: '',
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		// css
		content_css : "css/style.css"
	});
	
	// swfupload 초기화
	$('#swfupload').swfupload({
		flash_url: 'js/swfupload/swfupload.swf',
		upload_url: 'file/upload.do',
		file_post_name: 'file',
		file_size_limit : "5 MB",
		debug: false,
		
		button_placeholder_id: 'folder-button',
		button_image_url: 'images/open.png',
		button_width: 31,
		button_height: 26,
		button_action : SWFUpload.BUTTON_ACTION.SELECT_FILES,
		button_disabled : false,
		button_cursor : SWFUpload.CURSOR.HAND,
		button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT
	});
	// swfupload 이벤트
	$('#swfupload').bind({
		// sefupload 가 최초 로드 되었을 때
		swfuploadLoaded: function(evt) {
			// 최대 업로드 용량을 화면에 표시해준다.
			var swfu = $.swfupload.getInstance('#swfupload');
			var limit = swfu.settings.file_size_limit;
			// 업로드 제한 표시
			$('#swfupload-msg').append(limit == 0 ? '무제한' : limit);
			// 선택 가능하게 만든다.
			$('#swfupload-queue').selectable({
				selected: function() {
					if($('.ui-selected', this).length > 0) {
						$('#paste-button').button('enable');
					};
				},
				unselected: function(evt, ui) {
					if($('.ui-selected', this).length == 0) {
						$('#paste-button').button('disable');
					};
				}
			});
						
			$('div .swfupload-queue').bind({
				click: function() {
					// 파일 목록 영역에서 파일칸 이외의 공간을 클릭하면 모두 선택 취소
					$('.ui-selected', this).each(function() {
						$(this).removeClass('ui-selected');
					});
					// 본문에 삽입 버튼 비활성화
					$('#paste-button').button('disable');
				}
			});
		},
		// 큐에 업로드할 파일이 쌓였을 때
		fileQueued: function(evt, file) {
			// 큐에 파일들이 등록되면 화면에 보여준다.
			var li = $('<li />', {
				id: file.id,
				'class': 'ui-widget-content',
				'html': '&nbsp;'
			});
			
			var icon = $('<img />', {
				'id': 'icon',
				'src': 'images/pending.png'
			});
			var name = $('<span />', { 
				id: 'name', 
				text: file.name
			});
			var size = $('<span />', {
				id: 'size',
				text: file.size
			});
			var ext = $('<span />', {
				id: 'ext',
				text: file.type
			});
			
			// query ui 상태바
			var progress = $('<div />', {
				id: 'progress'
			}).bind({
				progressbarcomplete: function(evt, ui) {
					$(this).fadeOut(500, function() {
						$(this).remove();
					});
				}
			});
			
			li.append(icon, name, size, ext, progress);
			
			$('#swfupload-queue').append(li);
		},
		// 선택한 파일들이 모두 큐에 등록 되었을때
		fileDialogComplete: function(evt, selected, queued, total) {
			// 업로드 시작
			$('#swfupload').swfupload('startUpload');
		},
		// 하나의 파일이 업로드가 시작 될때
		uploadStart: function(evt, file) {
			var obj = $('#' + file.id);
			// 진행중 이미지 변경
			$('#icon', obj).attr('src', 'images/busy.gif');
			// 취소 버튼 활성화
			$('#cancel-button').button('enable');
		},
		// 업로드 진행 상태
		uploadProgress: function(evt, file, bytes, total) {
			// 진행바(progressbar) 갱신
			$('#' + file.id + ' > #progress').progressbar({
				value: parseInt(bytes / total * 100)
			});
			// 파일 용량 표시
			$('#' + file.id + ' > #size').text('(' + bytes + '/' + total + ')');
		},
		// 하나의 파일 업로드 완료
		uploadSuccess: function(evt, file, response, received) {
			var json = JSON.parse(response);
			
			var obj = $('#' + file.id);
			// 업로드 성공 이미지로 변경
			$('#icon', obj).attr('src', 'images/success.png');
			// byte 용량을 단위 계산해서 출력
			$('#size', obj).text('(' + $(file.size).parseByteUnit() + ')');
			// seq, session 정보를 남긴다.
			obj.append($('<div />', {
				id: 'seq',
				text: json.seq
			})).append($('<div />', {
				id: 'session',
				text: json.session
			}));
		},
		// 하나의 파일을 큐에 넣다가 에러가 났을 때
		// 에러가 나면서 아예 큐에 들어가지 않게 된다.
		fileQueueError: function(evt, file, code, msg) {
			$.dialog(file.name + ' (' + $(file.size).parseByteUnit() + ')', msg);
		},
		// 하나의 파일을 업로드하는 중에 에러가 났을 때
		uploadError: function(evt, file, code, msg) {
			// 에러 실패일 수도 있지만 취소일 수도 있다.
			var text = '';
			
			switch(code) {
			case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
				text = '서버 에러입니다. (코드: ' + msg + ')';
				break;
			case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
				text = 'The upload_url setting was not set';
				break;
			case SWFUpload.UPLOAD_ERROR.IO_ERROR:
				text = 'Some kind of error occurred while reading or transmitting the file. This most commonly occurs when the server unexpectedly terminates the connection';
				break;
			case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
				text = 'The upload violates a security restriction. This error is rare';
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
				text = 'The user has attempted to upload more files than is allowed by the file_upload_limit setting';
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED :
				text = 'The attempt to initiate the upload caused an error. This error is rare';
				break;
			case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND :
				text = 'A file ID was passed to startUpload but that file ID could not be found';
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
				text = 'False was returned from the uploadStart event';
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
				text = '파일 업로드가 취소되었습니다.';
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED :
				text = '파일 업로드가 중지되었습니다.';
				break;
			default:
				text = '파일 업로드 에러, ' + code + ', ' + msg;
				break;
			}
			
			var obj = $('#' + file.id);
			// 에러 아이콘으로 변경
			$('#icon', obj).attr('src', 'images/error.png');
			// 에러 표시
			obj.error(text);
		},
		// 업로드가 완료(전체 완료 또는 취소)
		uploadComplete: function(evt, file) {
			// 업로드 완료 후 취소 버튼 비활성화
			$('#cancel-button').button('disable');
		}
	});
	
	// 업로드 취소 버튼
	$('#cancel-button').button({
		disabled: true,
		text: false,
		icons: {
			primary: 'ui-icon-circle-close'
		}
	}).click(function() {
		var swfu = $.swfupload.getInstance('#swfupload');
		swfu.cancelQueue();
	});
	
	// 삭제 버튼
	$('#remove-button').button({
		text: false,
		icons: {
			primary: 'ui-icon-circle-minus'
		}
	}).bind( {
		click: function() {
			$('#swfupload-queue > .ui-selected').each(function() {
				var seq = $('#seq', $(this)).text();
				var session = $('#session', $(this)).text();
				
				// seq 와 seesion 값이 없으면 아직 업로드 하지 않은 것
				// 그냥 화면에서 지우기
				if(seq == '' || session == '') {
					$(this).fadeOut(1000, function() {
						$(this).remove();
					});
					
					return;
				}
				
				// 이미 업로드가 된것 삭제
				$.ajax({
					url: 'file/unload.do',
					type: 'POST',
					cache: false,
					data: {
						'seq': seq,
						'session': session
					},
					dataType: 'json',
					context: $(this),
					beforeSend: function() {
						$('#icon', this).attr('src', 'images/loading.gif');
					},
					success: function(json, textStatus, jqXHR) {
						if(json.success == true) {
							// 성공
							$(this).fadeOut(1000, function() {
								$(this).remove();
							});
						} else {
							$(this).error(json.msg);
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						var text = jqXHR.status + '(' + errorThrown + ')';
						// 에러 표시
						$(this).error(text);
					},
					complete: function() {
						
					}
					
				});
			});
		} // click event
	});
	
	// 본문에 삽입 버튼
	$('#paste-button').button({
		disabled: true,
		icons: {
			primary: 'ui-icon-circle-triangle-n'
		}
	}).bind({
		click: function() {
			$('#swfupload-queue > .ui-selected').each(function() {
				var isImage = false;
				
				// 이미지로 판단할 확장자
				var exts = ['.jpg', '.jpeg', '.gif', '.png', '.bmp'];
				var ext = $('#ext', $(this)).text();
				
				var grep = $.grep(exts, function(e){
					return e == ext;
				});
				
				if(grep.length > 0) {
					isImage = true;
				}
								
				var seq = $('#seq', $(this)).text();
				var name = $('#name', $(this)).text();
				
				var link;
				if(isImage) {
					link = '<img src="file/download.do?seq=' + seq + '" title="' + name + '" alt="' + name + '" />';
				} else {
					link = '<a href="file/download.do?seq=' + seq + '">' + name + '</a>';
				}

				// 본문에 삽입
				$('#content').tinymce().execCommand('mceInsertContent', false, link);
			});
		}
	});
	
	// 쓰기/수정 버튼
	$('#write-button').button({
		icons: {
			primary: 'ui-icon-pencil'
		}
	});
	
	// 목록 버튼
	$('#list-button').button({
		icons: {
			primary: 'ui-icon-arrowreturn-1-w'
		}
	});
	
	// kaptcha reload
	$('#kaptcha-img').click(function(evt) {
		var url = $(this).attr('src');

		var idx = url.indexOf("?");
		if (idx == -1) {
			idx = url.length;
		}

		$(this).attr('src', url.substring(0, idx) + "?r=" + Math.random());
	});
	
	// form submit
	$('#form').submit(function() {
		// 간단하게 폼 검사
		var valid = true;

		$('input[type=text], input[type=password]').each(function() {
			// 새로운 비밀번호는 검사하지 않음
			if($(this).attr('name') == 'newPwd') {
				return; // continue
			}
			
			if($.trim( $(this).val() ) == '') {
				valid = false;
				$.dialog('유효성 검사 실패', $(this).attr('title') + '을(를) 입력하세요.', true);
				return false;
			}
		});

		// 유효성 검사 실패시 멈춤
		if(!valid) {
			return false;
		}
		
		if($('input[name=query]').val() == 'modify') {
			/**
			 * 비밀번호 검사
			 */
			var isAuth = false;

			$.ajax({
				url: 'auth.do',
				type: 'POST',
				async: false,
				data: {
					'board_id': $('input[name=bid]').val(),
					'pwd': $('input[name=pwd]').val()
				},
				dataType: 'json',
				success: function(json) {
					if(json.auth == true) {
						isAuth = true;
					} else {
						$.dialog('비밀번호 불일치', '비밀번호가 틀립니다.', true);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					$.dialog('서버 에러', jqXHR.status + '(' + errorThrown + ')');
				}
			});
			
			if(!isAuth) {
				return false;
			}
			
		}
		
		/**
		 * 캡챠 검사
		 */
		
		var isKaptcha = true;
		
		// 캡챠 검사
		$.ajax({
			url: "kaptcha.do",
			type: "POST",
			data: {
				'kaptcha': $('input[name=kaptcha]').val()  
			},
			async:false,
			dataType: 'json',
			success: function(json) {
				isKaptcha = json.auth;
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var title = jqXHR.status + ' ' + errorThrown;
				var msg = jqXHR.responseText;
				
				$.dialog(title, msg, true);
			}
		});
		
		if(!isKaptcha) {
			$.dialog('자동등록방지 실패', '자동등록방지 코드가 일치하지 않습니다.', true);
			return false;
		}
		
		$('.ui-widget-content').each(function() {
			var seq = $('#seq', $(this)).text();
			// 파일 업로드 갯수만큼 <input type="hidden" name="file_seq" value="${seq}" /> 만들어준다.
			$('#form').append($('<input />', {
				type: 'hidden',
				name: 'file_seq',
				value: seq
			}));
		});
		
		// ie swfupload memory leak
		var swfu = $.swfupload.getInstance('#swfupload');		
		swfu.destroy();
	});
	
});