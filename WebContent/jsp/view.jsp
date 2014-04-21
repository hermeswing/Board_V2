<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="antop" uri="http://antop.nerv-team.co.kr/jstl" %>
<html>
<head>
<%@include file="common/header.jsp"%>
<!-- css -->
<link rel="stylesheet" type="text/css"  href="/css/view.css" />
<!-- lightbox -->
<link rel="stylesheet" type="text/css" href="/js/lightbox/css/lightbox.css" />
<script type="text/javascript" src="/js/lightbox/lightbox.js"></script>
<!-- this -->
<script type="text/javascript" src="/js/view.js"></script>
<script type="text/javascript">
  function pageNavi(p) {
    if (p == "list") {
      PageNavigator.navigate({
        action : "/list.do"
      }, {
        page : "${page}",
        search : "${search}"
      });
    } else if (p == "reply") {
      PageNavigator.navigate({
        action : "/form.do"
      }, {
        bid : "${board.boardId}",
        page : "${page}",
        search : "${search}"
      });
    } else if (p == "modify") {
      PageNavigator.navigate({
        action : "/form.do"
      }, {
        query : "modify",
        bid : "${board.boardId}",
        page : "${page}",
        search : "${search}"
      });
    } else if (p == "delete") {
      PageNavigator.navigate({
        action : "/delete.do"
      }, {
        bid : "${board.boardId}",
        page : "${page}",
        search : "${search}"
      });
    }
  }
</script>
</head>
<body>

<div id="wrap">
	<div>
		<span class="label">작성자</span><span><c:out value="${board.author}" escapeXml="true" /></span>
	</div>
	<div class="line">
		<span class="label">조회수</span><span><c:out value="${board.hit}" escapeXml="true" /></span>
	</div>
	<div class="line">
		<span class="label">작성일</span><span><fmt:formatDate value="${board.created}" pattern="yyyy년 M월 d일 a h시 m분" /></span>
	</div>
	<c:if test="${board.modified != null}">
	<div class="line">
		<span class="label">수정일</span><span><fmt:formatDate value="${board.modified}" pattern="yyyy년 M월 d일 a h시 m분" /></span>
	</div>		
	</c:if>

	<div class="line">
		<span class="label">제목</span><span><c:out value="${board.subject}" escapeXml="true" /></span>
	</div>
	
	<!-- 내용 -->
	<div class="line content">
		<span class="label">내용</span>
		<span class="tinymce">${board.content}</span>
	</div>
	<!-- 첨부 -->
	<c:if test="${fn:length(board.attachs) > 0}">
	<div class="line attach">
		<span class="label">첨부파일</span>
		<span>
			<c:forEach var="attach" items="${board.attachs}">
			<div>
				<a href="file/download.do?seq=${attach.seq}" >${attach.fileName}</a>
			</div>
			</c:forEach>
		</span>
	</div>
	</c:if>
	<!-- 버튼 -->
	<div class="buttons">
		<a id="reply-button" href="javascript:pageNavi('reply')">답변</a>
		<a id="modify-button" href="javascript:pageNavi('modify')">수정</a>
		<a id="delete-button" href="javascript:pageNavi('delete')">삭제</a>
		<a id="list-button" href="javascript:pageNavi('list')" class='button'>목록</a>
	</div>
</div>

</body>
</html>