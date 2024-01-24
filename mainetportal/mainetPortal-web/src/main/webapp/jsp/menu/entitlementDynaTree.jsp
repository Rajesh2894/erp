<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ul>
	<c:forEach var="node" items="${node}">
		<li id="${node.smfid}" class="folder"><a
			target="${node.smfid}/<c:choose><c:when test="${not empty node.moduleFunction}">${node.moduleFunction.smfid}</c:when><c:otherwise>0</c:otherwise></c:choose>" href='#'>${node.smfname}</a>
			<c:set var="node" value="${node.menuHierarchicalList}"
				scope="request" /> <jsp:include page="entitlementDynaTree.jsp"></jsp:include>
		</li>
	</c:forEach>
</ul>
