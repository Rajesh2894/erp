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
										code="care.reports.service.department.heading"
										text="ServiceType And Department Wise Grievance Report" />
								</a>
							</h4>
						</div>


						<div id="a0" class="panel-collapse collapse in">
							<div class="panel-body">
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

									<div id="id_complaintType">
										<apptags:select labelCode="care.complaintType"
											items="${command.complaintTypes}"
											path="careReportRequest.complaintType"
											selectOptionLabelCode="Select" isMandatory="true"
											showAll="true" isLookUpItem="true"></apptags:select>

									</div>

								</div>


								<%-- <div class="form-group">
									<c:set var="baseLookupCode" value="CWZ" />
									<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="CWZ"
										hasId="true" pathPrefix="careReportRequest.careWardNo"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="true"
										isMandatory="false" />

								</div> --%>
								<div class="form-group">
									<c:set var="baseLookupCode" value="CWZ" />
									<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="CWZ"
										hasId="true" pathPrefix="careReportRequest.careWardNo"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="true"
										isMandatory="false" />

								</div>

								<!--   <div id="zone-ward"></div>  -->
							</div>
						</div>
						<div class="text-center clear padding-10">
							<input type="button" class="btn  btn-success" id="save"
								name="save" value="<spring:message code="care.submit"/>"
								onclick="searchComplaintDeptAndSeviceWise(this);" />
							<%--  <apptags:resetButton cssClass="btnReset"></apptags:resetButton>	  --%>
							<input type=Reset class="btn btn-warning" id="rstButton"
								value="<spring:message code="care.reset" text="Reset"/>" />
							<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
									code="care.btn.back"></spring:message>
							</a>
							<%-- <apptags:submitButton entityLabelCode="Submit" cssClass="button-input btn btn-success" actionParam="grievanceDeptWiseReport"></apptags:submitButton>
		                <apptags:resetButton cssClass="btnReset"></apptags:resetButton>	 --%>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>