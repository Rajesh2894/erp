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
										code="care.reports.sms.email"
										text="Grievances Sms-Email History Report" />
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
									<%-- <apptags:select labelCode="care.department"
										items="${command.departments}"
										path="careReportRequest.department"
										selectOptionLabelCode="Select" isMandatory="true"
										showAll="true" isLookUpItem="true"></apptags:select> --%>
									<label class="col-sm-2 control-label"><spring:message
											code="rti.alertSub" text="Alert Type" /></label>
									<div class="col-sm-4">
										<label class="radio-inline"><form:radiobutton
												path="careReportRequest.alertType" value="S" id="alertType" />
											<spring:message code="rti.bysms" text="Sms" /></label> <label
											class="radio-inline"><form:radiobutton
												path="careReportRequest.alertType" value="E" id="alertType1" />
											<spring:message code="rti.bymail" text="Email" /></label> 
											<label
											class="radio-inline"><form:radiobutton
												path="careReportRequest.alertType" value="B" id="alertType1" />
											<spring:message code="rti.both" text="Both" /></label> 
									</div>
								</div>
							</div>
							<div class="text-center clear padding-10">
								<input type="button" class="btn  btn-success" id="save"
									name="save" value="<spring:message code="care.submit"/>"
									onclick="submitComplaintSmsEmailHistoryForm(this);" />
								<%--  <apptags:resetButton cssClass="btnReset"></apptags:resetButton>	  --%>
								<input type=Reset class="btn btn-warning" id="rstButton"
									value="<spring:message code="care.reset" text="Reset"/>" />
									
								<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
									code="care.back"></spring:message>
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