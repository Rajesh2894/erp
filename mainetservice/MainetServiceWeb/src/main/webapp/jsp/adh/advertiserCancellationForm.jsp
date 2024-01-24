<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/advertiserCancellation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertiser.cancellation.titile" />
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form action="AdvertiserCancellation.html"
				name="AdvertiserCancellation" id="AdvertiserCancellation"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.cancellation.licNo" text="" /></label>

					<div class="col-sm-4">

						<form:select path="advertiserDto.agencyLicNo" id="agencyLicNo"
							class="chosen-select-no-results" style="width: 100%" data-rule-required="true"
							onchange="searchAdvertiserNameByLicNo()">
							<form:option value="0">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.agencyLicNoAndNameList}"
								var="agencyLicNoList">
								<form:option value="${agencyLicNoList[0]}">${agencyLicNoList[0]} - ${agencyLicNoList[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<apptags:input labelCode="advertiser.cancellation.name"
						path="advertiserDto.agencyName" isDisabled="true"
						isMandatory="true"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:date labelCode="advertiser.cancellation.date"
						datePath="advertiserDto.cancellationDate" fieldclass="datepicker"
						isMandatory="true"></apptags:date>
					<apptags:input labelCode="advertiser.cancellation.reason"
						cssClass="hasCharacter" path="advertiserDto.cancellationReason"
						isMandatory="true"></apptags:input>
				</div>
				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" id="save"
						onclick="saveAdvertiser(this)">
						<spring:message code="adh.save" text="Save"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='AdvertiserCancellation.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>
