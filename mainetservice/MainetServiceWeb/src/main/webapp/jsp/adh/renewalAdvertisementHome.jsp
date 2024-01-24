<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/adh/renewalAdvertisementApplication.js"></script>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="adh.renewal.advertisement.application.license"
					text=" Renewal Of Advertisement License " />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form method="POST"
				action="RenewalAdvertisementApplication.html"
				class="form-horizontal" id="renewalAdvertisementApplicationHome"
				name="renewalAdvertisementApplicationHome">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.renewal.advertisement.license.no" text="License No." /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="licenseNo">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
							<c:forEach items="${command.licenseDataList}" var="license">
								<form:option value="${license.licenseNo}"
									code="${license.licenseNo}">${license.licenseNo} ==> ${license.applicantName} </form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center margin-top-15">
					<button type="button" class="btn btn-success"
						onclick="getAvertisementApplicationDetails()">
						<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetRenewalForm(this);">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href ='AdminHome.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i>
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<!-- End Content here -->