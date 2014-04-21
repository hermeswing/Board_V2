<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${query == 'delete'}">
		<c:set var="action" value="delete.do" />	
	</c:when>
	<c:when test="${query == 'view'}">
		<c:set var="action" value="view.do" />
	</c:when>
	<c:otherwise>
		<c:set var="action" value="form.do" />	
	</c:otherwise>
</c:choose>
<html>
<head>
<%@include file="common/header.jsp"%>
<!-- css -->
<link rel="stylesheet" type="text/css" href="/css/security.css" />
<!-- this -->
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/secret.js"></script>
<script type="text/javascript">
  function pageNavi(p) {
    if (p == "list") {
      PageNavigator.navigate({
        action : "/list.do"
      }, {
        page : "${page}",
        search : "${search}"
      });
    }
  }
</script>
</head>
<body>

<form action="${action}" method="post">
	<input type="hidden" name="query" value="${query}" />
	<input type="hidden" id="bid" name="bid" value="${boardId}" />
	<input type="hidden" id="page" name="page" value="${page}" />
	<input type="hidden" id="search" name="search" value="${search}" />
</form>

<div id="wrap">
	<div class="title">암호를 입력 해주세요.</div>
	
	<div class="form">
		<span id="busy"></span>
		<input type="password" id="pwd" name="pwd" title="비밀번호" />
		<span class="buttons">
			<button id="submit-button" type="button">확인</button>
			<button id="cancel-button" type="button">취소</button>
            <a id="list-button" href="javascript:pageNavi('list')">목록</a>
		</span>
	</div>
	
	<div id="msg"></div>
</div>

</body>
</html>