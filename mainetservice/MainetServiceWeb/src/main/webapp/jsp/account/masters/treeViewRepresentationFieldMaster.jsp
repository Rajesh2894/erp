<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ul>
	<c:forEach var="node" items="${node}">

		<li id="${node.fieldId}" class="folder"><a
			target="${node.fieldId}/<c:choose>
										<c:when test="${not empty node.fieldId}">${ node.fieldParentId.fieldId}</c:when>
										<c:otherwise>0</c:otherwise></c:choose>"
			href='${node.fieldStatusCpdId}'> ${node.fieldCompcode} ->
				${node.fieldDesc}</a> <c:set var="node"
				value="${node.fieldHierarchicalList}" scope="request" /> <jsp:include
				page="treeViewRepresentationFieldMaster.jsp"></jsp:include></li>
	</c:forEach>
</ul>