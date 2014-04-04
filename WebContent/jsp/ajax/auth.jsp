<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>
<json:object>
	<json:property name="auth" value="${auth}" />
	<c:if test="${msg != null}">
		<json:property name="msg" value="${msg}" />
	</c:if>
</json:object>