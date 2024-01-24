<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ul>

	<c:forEach var="node" items="${node}">
		<li id="${node.primaryAcHeadId}" class="folder"><a
			target="${node.primaryAcHeadId}/<c:choose>
		<c:when test="${not empty node.primaryAcHeadParentId}">${ node.primaryAcHeadParentId.primaryAcHeadId}</c:when>
		<c:otherwise>0</c:otherwise></c:choose>"
			href='${node.primaryAcHeadParentId.primaryAcHeadId}-${node.primaryAcHeadId}-${node.pacStatusCpdId}-${node.cpdIdAcHeadTypes}-${node.primaryAcHeadCode}-${node.budgetCheckFlag}'>
				${node.primaryAcHeadCompcode} -> ${node.primaryAcHeadDesc}</a> <c:set
				var="node" value="${node.primaryAcHeadHierarchicalList}"
				scope="request" /><jsp:include
				page="treeViewRepresentationPrimaryCode.jsp"></jsp:include></li>
	</c:forEach>
</ul>