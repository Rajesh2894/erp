<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><spring:message
				code="menu.home" /></a></li>
	<li><a href="javascript:void(0);"><spring:message
				code="menu.webportal" /></a></li>
	<li><a href="javascript:void(0);"><spring:message
				code="eip.master" /></a></li>
	<li class="active"><spring:message code="eip.admin.home.title" /></li>
</ol>
<div id="content" class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="eip.admin.home.title" /></strong>
			</h2>
		</div>
		<apptags:helpDoc url="AdminHome.html"></apptags:helpDoc>
		<div class="widget-content padding">

			<form:form method="post" action="AdminHome.html?save"
				name="frmAdminHome" id="frmAdminHome" class="form">
				<form:hidden path="entity.id" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<c:if test="${not empty MSG_TEXT}">
					<div class="message">
						<p>${MSG_TEXT}</p>
					</div>
				</c:if>

				<div class="form-elements clear">
					<label for="entity.descriptionEn"><spring:message
							code="eip.admin.home.descriptionEn" /> :</label>
					<form:textarea path="entity.descriptionEn" maxlength="2000"
						cssClass=" form-control mandClassColor maxsize" rows="6"/>
					<span class="mand">*</span>
					<p class="charsRemaining">characters remaining :</p>
				</div>
				<div class="form-elements clear margin_top_10">
					<label for="entity.descriptionReg"><spring:message
							code="eip.admin.home.descriptionReg" /> :</label>
					<form:textarea path="entity.descriptionReg" maxlength="2000"
						cssClass=" form-control mandClassColor maxsize" rows="6" />
					<span class="mand">*</span>
					<p class="charsRemaining">characters remaining :</p>
				</div>


				<div class="btn_fld clear margin_top_10">
					<input type="submit" value="Save" class="btn btn-success" />
					<apptags:backButton buttonLabel="Cancel" url="CitizenHome.html" cssClass="btn btn-primary" />
				</div>

			</form:form>
		</div>
	</div>
</div>
