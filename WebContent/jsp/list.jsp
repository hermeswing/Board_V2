<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="antop" uri="http://antop.nerv-team.co.kr/jstl"%>

<html>
<head>
<script src="/js/list.js" type="text/javascript"></script>
<script type="text/javascript">
function pageNavi() {
  var args = null;
  if(args == "list") {
    args = { URI:"/list.do" };
    PageNavigator.navigate(args);
  } else if(args == "write") {
    args = { URI:"/form.do?query=write&page=${page}&search=${search}" };
    PageNavigator.navigate(args);
  }
}
</script>
</head>

<body>

<div id="wrap">
    <table>
        <caption>TOTAL : ${total}</caption>
        <thead>
            <tr>
                <th width="40">번호</th>
                <th width="16"></th>
                <th width="16"></th>
                <th>제목</th>
                <th width="80">글쓴이</th>
                <th width="40">조회</th>
                <th width="70">작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${fn:length(list) == 0}">
                <tr>
                    <td colspan="5" align="center"><c:choose>
                            <c:when test="${search == null}">등록된 게시물이 없습니다.</c:when>
                            <c:otherwise>'${search}'으로 검색된 게시물이 없습니다.</c:otherwise>
                        </c:choose></td>
                </tr>
            </c:if>

            <c:forEach var="b" items="${list}" varStatus="stat">
                <tr>
                    <td align="center"><antop:bunho page="${page}" total="${total}" rowIdx="${stat.index}"
                            rowSize="${rowSize}" /></td>
                    <td><c:if test="${fn:length(b.attachs) > 0}">
                            <img src="/images/disk.png" class="icon" title="파일첨부 ${fn:length(b.attachs)}개">
                        </c:if></td>
                    <td><c:if test="${b.security == true}">
                            <img src="/images/lock.png" class="icon" title="비밀글">
                        </c:if></td>
                    <td><c:if test="${b.dept > 0}">
                            <c:forEach begin="1" end="${b.dept}">
						&nbsp;
					</c:forEach>
                        </c:if> <c:if test="${b.dept > 0}">
                            <img src="/images/reply.png" class="icon">
                        </c:if> <a href="view.do?bid=${b.boardId}&page=${page}&search=${search}"> <c:out value="${b.subject}"
                                escapeXml="true" />
                    </a></td>
                    <td align="center"><c:out value="${b.author}" escapeXml="true" /></td>
                    <td align="center">${b.hit}</td>
                    <td align="center"><fmt:formatDate value="${b.created}" pattern="yyyy-MM-dd" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div id="paging">
        <antop:paging action="list.do" page="${page}" total="${total}" rowSize="${rowSize}" search="${search}" />
    </div>

    <form id="form" name="list.do" method="post">
        <div class="buttons">
            <input type=text name="search" value="${search}">
            <a id="search-button" href="submit">검색</a>
            <!-- 
            <a id="write-button" href="form.do?query=write&page=${page}&search=${search}">쓰기</a>
             -->
            <a id="write-button"  href="javascript:pageNavi('write')">쓰기</a>
            <a id="list-button"  href="javascript:pageNavi('list')">목록</a>
        </div>
    </form>
</div>

</body>
</html>