<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<div class="popup-form-div">
	<form:form method="post" action="CitizenPublicNoticesForm.html"
		name="frmCitizenPublicNoticesForm" id="frmCitizenPublicNoticesForm">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message
						code="admin.publicNotice.DetailEn" />  </label> <span><form:textarea
						path="entity.detailEn" cssStyle="width:150px ; height : 80px;" />
					<span class="mand" style="color: red">*</span></span>
			</div>
		</div>
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message
						code="admin.publicNotice.DetailReg" /> </label> <span><form:textarea
						path="entity.detailReg" cssStyle="width:150px ; height : 80px;" />
					<span class="mand" style="color: red">*</span></span>
			</div>
		</div>


		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="admin.publicNotice.DetailReg" /> : </label>
				<c:choose>
					<c:when test="${not empty  command.entity.profileImgPath }">
						<c:set var="links" value="${fn:split(command.entity.profileImgPath,',')}"/>
						<c:forEach items="${links}" var="download" varStatus="status"> 
							<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
							<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
							<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}"/>
							<form action="PublicNotices.html?Download" method="post" id="frm${idappender}_${status.count}" target="_blank" >
								<a href="javascript:void(0);" target="_blank" onclick="javascript:document.getElementById('frm${idappender}_${status.count}').submit();">${link}</a>
								<input type="hidden" name="downloadLink" value="${download}">
							</form>
						</c:forEach>
					</c:when>
					<c:otherwise>No data to downloads</c:otherwise>
				</c:choose>

			</div>
		</div>
		<div class="buttons btn-fld" align="center">
			<apptags:backButton url="PublicNotices.html" />
		</div>


	</form:form>
</div>