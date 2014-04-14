$(function() {
  $(".ui-layout-north a").click(function() {
    PageNavigator.navigate({
      action : $(this).attr("page"),
      istop : $(this).attr("istop")
    }, null);
  });
});

// submit2({target: 'ehrTopFrame', action: '/main/jsp/logout.jsp'}, null);
var PageNavigator = {
  navigate : function(property, parameters) {
    if (property.istop == "Y") {
      property.target = "document.window";
    }
    if (!property.action) {
      PageNavigator.navigate({
        action : "blank.do"
      }, null);
    }
    if (!property.target) {
      property.target = "contentsiFrame";
    }
    var form = $("<form></form>").attr(property);
    // property.method은 undefined 형이다.
    // || (또는)을 붙여줌으로서 undefined가 get으로 변경된다. ( or의 의미인듯..)
    $(form).attr("method", property.method || "post");// default method is post

    var input, value;
    for ( var name in parameters) {
      value = parameters[name];
      if ($.protify([
          "string", "number"
      ]).include(typeof value)) {
        form.append($("<input>").attr({
          name : name,
          type : "hidden",
          value : value
        }));
      } else if (typeof value == "array") {
        value.each(function(v) {
          form.append($("<input>").attr({
            name : name,
            type : "hidden",
            value : v
          }));
        });
      } else {
        input = $("<input>").attr({
          name : name,
          type : "hidden",
          value : value || ''
        });
        form.append(input);
      }
    }
    form.insertAfter(document.body);
    form.submit();
    form.remove();
    form = null;
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
 * jqGrid desc : form의 데이터를 json 형태로 변환해 준다. return : 성공시에는 객체(JSON)을 리턴한다.
 * 실패시에는 null을 리턴한다.
 */
jQuery.fn.serializeObject = function() {
  var obj = null;
  try {
    if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
      var arr = this.serializeArray();
      if (arr) {
        obj = {};
        jQuery.each(arr, function() {
          obj[this.name] = this.value;
        });
      }// if ( arr ) {
    }
  } catch (e) {
    alert(e.message);
  } finally {
  }

  return obj;
};

// 백스페이스 뒤로가기 막기
if (window.Prototype) {
  Event.observe(document.documentElement, 'keydown', function() {
    var elem = Event.element(event);
    // alert([elem.tagName, elem.isTextEdit]);
    if (event.keyCode == Event.KEY_BACKSPACE && !elem.isTextEdit) Event.stop(event);
  });
}

// Source 보기
window.attachEvent("onload", function() {
  document.body.attachEvent("onkeydown", function() {
    if (window.event.ctrlLeft && window.event.shiftLeft && window.event.altLeft) {
      switch (window.event.keyCode) {
      case 83: // View Source
        var wnd = window.open("about:blank", "_blank",
            "width=800px,height=600px,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
        wnd.document.open("text/plain", "replace");
        wnd.document.write(document.documentElement.outerHTML);
        wnd.document.close();
        wnd.focus();
        window.event.returnValue = false;
        break;
      case 82: // Reload
        window.location.reload();
        window.event.returnValue = false;
        break;
      }
    }
  });
});