<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-report.js"></script>
<script>
	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
	});
	var lastSelected;
	$(function () {
		lastSelected = $('[name="careReportRequest.reportName"]:checked').val();
	});
	$(document).on('click', '[name="careReportRequest.reportName"]', function () {
	    if (lastSelected != $(this).val() && typeof lastSelected != "undefined") {
	    	window.location.href='GrievanceReport.html?grievanceUserWise';
	    	}
	    lastSelected = $(this).val();
	});
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content animated slideInDown">

	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="care.reports.heading" text="Grievances Report" />
			</h2>

			<apptags:helpDoc url="GrievanceReport.html"></apptags:helpDoc>

		</div>
		<div class="widget-content padding">
			<form:form method="POST" action="GrievanceReport.html"
				commandName="command" class="form-horizontal"
				id="form_grievanceReport">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">

						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a0"> <spring:message
										code="care.reports.department.status.heading"
										text="Status Wise Grievance Report" />
								</a>
							</h4>
						</div>

						<div id="a0" class="panel-collapse collapse in">
							<div class="panel-body">
							 <c:if test="${command.kdmcEnv eq 'Y'}">
							<div class="form-group">
									<label class="col-sm-2 control-label  required-control"><spring:message
											code="care.report.type" text="Report Type" /></label>
									<div class="col-sm-4">
										<label class="radio-inline margin-top-5"> <form:radiobutton
												path="careReportRequest.reportName" value="D" id="Detail" />
											<spring:message code="care.detail.report" /></label> <label
											class="radio-inline margin-top-5"><form:radiobutton
												path="careReportRequest.reportName" value="S" id="Summary" />
											<spring:message code="care.summary.report" /></label>
									</div>
							</div>
							</c:if>
								<div class="form-group">
									<apptags:date labelCode="care.reports.fromDate"
										fieldclass="datepicker" showDefault="true"
										datePath="careReportRequest.fromDate" isMandatory="true"></apptags:date>
									<apptags:date labelCode="care.reports.toDate"
										fieldclass="datepicker" showDefault="true"
										datePath="careReportRequest.toDate" isMandatory="true"></apptags:date>
								</div>
								<div class="form-group">
									<apptags:select labelCode="care.department"
										items="${command.departments}"
										path="careReportRequest.department"
										selectOptionLabelCode="Select" isMandatory="true"
										showAll="true" isLookUpItem="true"></apptags:select>
									<label class="col-sm-2 control-label required-control"
										for="id_status"><spring:message code="care.reports.status" text="Status" /></label>
									<div class="col-sm-4">
									<!-- Defect #131217- check whether environment is kdmc or not -->
										<c:choose>
											<c:when test="${command.kdmcEnv eq 'Y'}">
            									<form:select class="form-control" id="id_status"
											path="careReportRequest.status"
											data-rule-prefixvalidation="true">
											<form:option code="care.select" value="0">
												<spring:message code="care.select" text="Select" />
											</form:option>
											<form:option code="care.all" value="1">
												<spring:message code="care.all" text="All" />
											</form:option>
											<form:option code="care.closed" value="2">
												<spring:message code="care.closed" text="Closed" />
											</form:option>
											<form:option code="care.pending" value="3">
												<spring:message code="care.pending" text="Pending" />
											</form:option>
											<form:option code="care.all" value="-1"
												cssStyle="display:none;">
												<spring:message code="care.all" text="All" />
											</form:option>
											<form:option code="care.closed" value="APPROVED"
												cssStyle="display:none;">
												<spring:message code="care.closed" text="Closed" />
											</form:option>
											<form:option code="care.pending" value="PENDING"
												cssStyle="display:none;">
												<spring:message code="care.pending" text="Pending" />
											</form:option>
										</form:select>
        									 </c:when>
											 <c:otherwise>
									            <form:select class="form-control" id="id_status"
											path="careReportRequest.status"
											data-rule-prefixvalidation="true">
											<form:option code="care.select" value="0">
												<spring:message code="care.select" text="Select" />
											</form:option>
											<form:option code="care.all" value="1">
												<spring:message code="care.all" text="All" />
											</form:option>
											<form:option code="care.closed" value="2">
												<spring:message code="care.closed" text="Closed" />
											</form:option>
											<form:option code="care.pending" value="3">
												<spring:message code="care.pending" text="Pending" />
											</form:option>
											<form:option code="care.rejected" value="4">
												<spring:message code="care.rejected" text="Rejected" />
											</form:option>
											<form:option code="care.hold" value="5">
												<spring:message code="care.hold" text="Hold" />
											</form:option>

											<form:option code="care.all" value="-1"
												cssStyle="display:none;">
												<spring:message code="care.all" text="All" />
											</form:option>
											<form:option code="care.closed" value="APPROVED"
												cssStyle="display:none;">
												<spring:message code="care.closed" text="Closed" />
											</form:option>
											<form:option code="care.pending" value="PENDING"
												cssStyle="display:none;">
												<spring:message code="care.pending" text="Pending" />
											</form:option>
											<form:option code="care.rejected" value="REJECTED"
												cssStyle="display:none;">
												<spring:message code="care.rejected" text="Rejected" />
											</form:option>
											<form:option code="care.hold" value="HOLD"
												cssStyle="display:none;">
												<spring:message code="care.hold" text="Hold" />
											</form:option>

										</form:select>
									         </c:otherwise>
										</c:choose>
									
									</div>

								</div>
								
								<c:choose>
									<c:when test="${command.kdmcEnv eq 'Y'}">
										<div id="zone-ward"></div>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<c:set var="baseLookupCode" value="CWZ" />
											<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="CWZ"
										hasId="true" pathPrefix="careReportRequest.codIdOperLevel"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="true"
										isMandatory="false" />
										</div>
									</c:otherwise>
								</c:choose>
								</div>
							</div>
						</div>
						<div class="text-center clear padding-10">
							<input type="button" class="btn  btn-success" id="save"
								name="save" value="<spring:message code="care.submit"/>"
								onclick="submitComplaintStsWise(this);" /> <input type="Reset"
								class="btn btn-warning" id="rstButton" 
								value="<spring:message code="care.reset" text="Reset"/>" />
							<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
									code="care.back"></spring:message>
							</a>
							<%-- <apptags:submitButton entityLabelCode="Submit" cssClass="button-input btn btn-success" actionParam="grievanceDeptStatWiseReport"></apptags:submitButton>	
		                <apptags:resetButton cssClass="btnReset"></apptags:resetButton>	  --%>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>