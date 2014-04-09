<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::</title>
<%@include file="common/header.jsp"%>
<script type="text/javascript">
  $(function() {
    $("#header .extension ul li a").click(function() {
      var args = {
        URI : $(this).attr('page')
      };
      PageNavigator.navigate(args);
    });
  });

  $(document).ready(function () {
      var myIframe = document.getElementById("mainFrame");
      if (myIframe) {
        if (myIframe.contentDocument && myIframe.contentDocument.body.offsetHeight) {
          // W3C DOM (and Mozilla) syntax
          myIframe.height = myIframe.contentDocument.body.offsetHeight;
        } else if (myIframe.Document && myIframe.Document.body.scrollHeight) {
          // IE DOM syntax
          myIframe.height = myIframe.Document.body.scrollHeight;
        }
      }
  });
</script>
</head>
<body>
    <div id="xe" class="hybrid">
        <div id="container" class="ece">
            <!-- Menu 시작 -->
            <jsp:include page="common/top_menu.jsp"></jsp:include>
            <!-- Menu 종료 -->
            <hr />
            <!-- Main 시작 -->
            <iframe id="mainFrame" name="mainFrame" style="border: none; overflow: visible; width: 100%; height: 100%;" vspace="0"
                hspace="0" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" src="/content1.htm"></iframe>
            <!-- Main 종료 -->
            <hr />
            <!-- Bottom 시작 -->
            <jsp:include page="common/footer.jsp"></jsp:include>
            <!-- Bottom 종료 -->
        </div>
    </div>
</body>
</html>