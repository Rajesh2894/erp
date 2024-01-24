<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>

<table class="tableClass">
	<tr>
		<th>Department Name</th>
		<th>Subject</th>
		<th>Download Links</th>
	</tr>
	<c:forEach var="lookUp" items="${command.rightInformation}">
	<tr>
		<td>${lookUp.department.lookUpDesc}</td>
		<td>${lookUp.lookUpDesc}</td>
		<td>
		<c:set var="links" value="${fn:split(lookUp.otherField,',')}"/>
		<c:forEach items="${links}" var="download" varStatus="status"> 
			<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
			<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
			<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}"/>
			<form action="RightToInformation.html?Download" method="post" id="frm${idappender}_${status.count}" target="_blank" >
				<a href="javascript:void(0);" target="_blank" onclick="javascript:document.getElementById('frm${idappender}_${status.count}').submit();">${link}</a>
				<input type="hidden" name="downloadLink" value="${download}">
			</form>
		</c:forEach>
		</td>
	</tr>
	</c:forEach>
</table>