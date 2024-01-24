<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ul>
	<c:forEach var="node" items="${setOfNodeForFunction}">

		<li id="${node.functionId}" class="folder"><a
			target="${node.functionId}/<c:choose>
										<c:when test="${not empty node.functionParentId}">${ node.functionParentId.functionId}</c:when>
										<c:otherwise>0</c:otherwise></c:choose>"
			href='${node.functionStatusCpdId} - ${node.functionId}'>
				${node.functionCompcode} -> ${node.functionDesc}</a> <c:set
				var="setOfNodeForFunction" value="${node.functionHierarchicalList}"
				scope="request" /> <jsp:include
				page="treeViewRepresentationFunctionMaster.jsp"></jsp:include></li>
	</c:forEach>
</ul>