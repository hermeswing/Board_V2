<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>UI Layout Plug-in - Documentation</title>

<link rel="stylesheet" href="/lib/css/global.css" type="text/css">
<link rel="stylesheet" href="/lib/css/documentation.css" type="text/css">
<!--[if lte IE 7]>
    <style type="text/css"> body { font-size: 85%; } </style>
<![endif]-->
<style type="text/css">
/**
 *  Basic Layout Theme
 */
.ui-layout-pane { /* all 'panes' */
    border: 1px solid #BBB;
}

.ui-layout-pane-center { /* IFRAME pane */
    padding: 0;
    margin: 0;
}

.ui-layout-pane-west { /* west pane */
    padding: 0 10px;
    background-color: #EEE !important;
    overflow: auto;
}

.ui-layout-resizer { /* all 'resizer-bars' */
    background: #DDD;
}

.ui-layout-resizer-open:hover { /* mouse-over */
    background: #9D9;
}

.ui-layout-toggler { /* all 'toggler-buttons' */
    background: #AAA;
}

.ui-layout-toggler-closed { /* closed toggler-button */
    background: #CCC;
    border-bottom: 1px solid #BBB;
}

.ui-layout-toggler .content { /* toggler-text */
    font: 14px bold Verdana, Verdana, Arial, Helvetica, sans-serif;
}

.ui-layout-toggler:hover { /* mouse-over */
    background: #DCA;
}

.ui-layout-toggler:hover .content { /* mouse-over */
    color: #009;
}

/* masks are usually transparent - make them visible (must 'override' default) */
.ui-layout-mask {
    background: #C00 !important;
    opacity: .20 !important;
    filter: alpha(opacity =                   20) !important;
}
</style>

<!-- REQUIRED scripts for layout widget -->
<script type="text/javascript" src="/lib/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/lib/js/jquery-ui-1.10.4.js"></script>
<script type="text/javascript" src="/lib/js/jquery.layout-latest.js"></script>
<script type="text/javascript" src="/lib/js/global.js"></script>
<script type="text/javascript" src="/js/common.js"></script>

<script type="text/javascript">
  var myLayout; // a var is required because this page utilizes: myLayout.allowOverflow() method

  $(document).ready(function() {
    //$('#top_container').layout();

    myLayout = $('body').layout({
    //myLayout = $('body > #top_container').layout({
      north__spacing_open : 0,
      north__closable : false,
      north__resizable : false,
      west__size : 250,
      west__spacing_closed : 20,
      west__togglerLength_closed : 100,
      west__togglerAlign_closed : "top",
      west__togglerContent_closed : "M<BR>E<BR>N<BR>U",
      west__togglerTip_closed : "Open & Pin Menu",
      west__sliderTip : "Slide Open Menu",
      west__slideTrigger_open : "mouseover",
      center__maskContents : true,
      //      south__initClosed : true,
      south__spacing_closed : 0,
      south__closable : false,
      south__resizable : false
    // IMPORTANT - enable iframe masking
    });
  });
</script>

</head>
<body>
    <jsp:include page="common/top_menu.jsp"></jsp:include>
    <jsp:include page="common/left_menu.jsp"></jsp:include>
    <!-- Contents Start -->
    <div id="mainContent" class="ui-layout-center content">
        <iframe id="contentsiFrame" name="contentsiFrame" src="/jsp/Contents.html" style="width: 100%; height: 100%;"
            frameBorder="0"></iframe>
    </div>
    <jsp:include page="common/footer.jsp"></jsp:include>
</body>
</html>