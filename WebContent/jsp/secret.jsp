<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- css -->
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/security.css" rel="stylesheet" type="text/css" />
<link href="css/ui-lightness/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
<!-- jquery -->
<script src="js/jquery/jquery-1.6.2.js" type="text/javascript"></script>
<script src="js/jquery/jquery-ui-1.8.14.custom.min.js" type="text/javascript"></script>
<!-- this -->
<script src="js/common.js" type="text/javascript"></script>
<script src="js/secret.js" type="text/javascript"></script>
</head>
<body>

<form action="${action}" method="get">
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
			<a id="list-button" href="list.do?page=${page}&search=${search}">목록</a>
		</span>
	</div>
	
	<div id="msg"></div>
</div>

</body>
</html>