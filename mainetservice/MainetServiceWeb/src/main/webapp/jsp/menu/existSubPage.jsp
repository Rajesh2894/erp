
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<script type="text/javascript" src="js/menu/existEntitlement.js"></script>


<ul>
	<c:forEach items="${command.listModules}" var="data">
	    <c:out value="${masterId}"></c:out><c:out value="${data.parentId}"></c:out>
		<c:if test="${masterId eq  data.parentId}">
			<li class="folder" id="${data.entitle.etId}"><a href='#'
				target="${data.entitle.etId}/${data.parentId}">${data.entitle.nameEng}</a>

				<c:set value="${data.entitle.etId}" var="masterId" scope="request"></c:set>
				<c:set value="${data.listModules}" var="data" scope="request"></c:set>
				<jsp:include page="existSubPage.jsp"></jsp:include></li>
		</c:if>
	</c:forEach>
</ul> 