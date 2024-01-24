<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ul>
	<c:forEach var="addnNodeVar" items="${addnNodeVar}">
		<li id="${addnNodeVar.smfid}" class="folder"><a
		
			target="${addnNodeVar.smfid}#${addnNodeVar.smfflag}#${addnNodeVar.smfname}#${addnNodeVar.smfname_mar}#${addnNodeVar.smfdescription}#${addnNodeVar.smfaction}#${addnNodeVar.smParam1}#${addnNodeVar.smParam2}" href='#'>${addnNodeVar.smfname}</a>
			
			<c:set var="addnNodeVar" value="${addnNodeVar.menuHierarchicalList}"
				scope="request" /> <jsp:include page="addNodeDynaTree.jsp"></jsp:include>
		</li>
	</c:forEach>
</ul>
