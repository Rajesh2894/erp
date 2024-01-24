<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ul>
	<c:forEach var="node" items="${setOfNode}">

		<li id="${node.fundId}" class="folder"><a
			target="${node.fundId}/<c:choose>
										<c:when test="${not empty node.fundParentId}">${node.fundParentId.fundId}</c:when>
										<c:otherwise>0</c:otherwise></c:choose>"
			href='${node.fundStatusCpdId}'> ${node.fundCompositecode} ->
				${node.fundDesc}</a> <c:set var="setOfNode"
				value="${node.fundHierarchicalList}" scope="request" /> <jsp:include
				page="treeViewRepresentationFundMaster.jsp"></jsp:include></li>
	</c:forEach>
</ul>