// 백스페이스 뒤로가기 막기
if ( window.Prototype )
{
  Event.observe(document.documentElement, 'keydown', function()
  {
    var elem = Event.element(event);
    //alert([elem.tagName, elem.isTextEdit]);
    if ( event.keyCode == Event.KEY_BACKSPACE && ! elem.isTextEdit ) Event.stop(event);
  });
}

var PageNavigator = {
  navigate : function(args) {
    if(args.URI.length == 0) {
  	  PageNavigator.navigate("/blank.do");
    }
    //$('#body').hide().load( args.URI, function() {
    $('#body').hide().load( "/list.do", function() {
      $('#body').show();
      alert($('#body').html());
    });
  }
};

$.extend({
	// alert 창 띄우기
	dialog : function(title, msg, modal) {
		if (modal == undefined) {
			modal = false;
		}

		var dialogId = 'ui-dialog-' + parseInt(Math.random() * 100000);

		$('body').append($('<div />', {
			'id' : dialogId
		}));

		$('#' + dialogId).attr('title', title);
		$('#' + dialogId).html($('<p />', {
			'html' : msg
		}));

		$('#' + dialogId).dialog({
			'autoOpen' : true,
			'modal' : modal,
			buttons : {
				'확인' : function() {
					$(this).dialog('close');
				}
			},
			'close' : function() {
				$(this).remove();
			}
		});
	}
});

$.fn.error = function(msg, o) {
	if (o == undefined) {
		o = {
			override : true,
			fadeOut : true,
			fadeTime : 5000
		};
	}

	if (o.override == true) {
		$('#error').remove();
	}

	// 바로 아래에 에러정보 표시
	var error = $('<div />', {
		'id' : 'error',
		'css' : {
			'border' : '1px solid #ff0000',
			'margin' : 5,
			'padding' : 5,
			'text-align' : 'left'
		}
	}).append($('<img />', {
		'src' : 'images/error.png',
		'css' : {
			'margin-bottom' : -3
		}
	})).append($('<span />', {
		'text' : msg
	}));

	$(this).append(error);

	if (o.fadeOut == true) {
		if (o.fadeTime == undefined) {
			o.fadeTime = 5000;
		}
		error.fadeOut(o.fadeTime, function() {
			$(this).remove();
		});
	}
};

// 바이트 용량을 단위 포함된 용량으로 변환
$.fn.parseByteUnit = function() {
	var bytes = $(this).get()[0];

	var kb = 1024;
	var mb = 1024 * 1000;
	var gb = 1024 * 1000 * 1000;

	if (bytes <= 1) {
		return bytes + " Byte";
	} else if (bytes < kb) {
		return bytes + " Bytes";
	} else if (bytes < mb) {
		return parseInt(bytes / kb) + " KB";
	} else if (bytes < gb) {
		return parseInt(bytes / mb) + " MB";
	} else {
		return parseInt(bytes / gb) + " GB";
	}
};

/**
 * hidden 폼 만들어서 submit
 * @param property
 * @param parameters
 * @return
 */
function submit2(property, parameters)
{
  var form = $("<form></form>").attr(property);
  //alert(property.method);
  $(form).attr("method", property.method || "post");// default method is post
  var input, value;
  for ( var name in parameters )
  {
    value = parameters[name];
    if ( $.protify(["string", "number"]).include(typeof value) )
    {
      form.append($("<input>").attr({name: name, type: "hidden", value: value}));
    }
    else if ( typeof value == "array" )
    {
      value.each(function(v)
      {
        form.append($("<input>").attr({name: name, type: "hidden", value: v}));
      });
    }
    else
    {
      input = $("<input>").attr({name: name, type: "hidden", value: value || ''});
      form.append(input);
    }
  }
  form.insertAfter(document.body);
  form.submit();
  form.remove();
  form = null;
} 