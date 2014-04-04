<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>
<json:object>
	<json:property name="seq" value="${attach.seq}" />
	<json:property name="path" value="${attach.path}" />
	<json:property name="fileName" value="${attach.fileName}" />
	<json:property name="realName" value="${attach.realName}" />
	<json:property name="contextType" value="${attach.contextType}" />
	<json:property name="size" value="${attach.fileSize}" />
	<json:property name="addDate" value="${attach.created}" />
	<json:property name="session" value="${attach.session}" />
</json:object>
