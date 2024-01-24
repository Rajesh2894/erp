<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/EmployeeVerificationForm.js"></script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="employee.verification.form.heading"
					text="Employee Verification Form" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<form:form action="EmployeeInspection.html"
				name="EmployeeVerification" id="EmployeeVerification"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="" href="#InspectionDetails"><spring:message
										code="employee.inspection.details" text="Inspection Details" />
								</a>
							</h4>
						</div>
						<div id="InspectionDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="DepartmentId"><spring:message
											code="employee.verification.department" text="Department" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="VCH" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}" path=""
											cssClass="form-control required-control" isMandatory="true"
											selectOptionLabelCode="selectdropdown" disabled="true"/>
									</div>

									<label class="control-label col-sm-2 required-control"
										for="Designation"><spring:message
											code="employee.verification.designation" text="Designation" /></label>

									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="VCH" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}" path=""
											cssClass="form-control required-control" isMandatory="true"
											selectOptionLabelCode="selectdropdown" disabled="true"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="InspectorName"><spring:message
											code="employee.inspector.name" text="Inspector Name" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="VCH" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}" path=""
											cssClass="form-control required-control" isMandatory="true"
											selectOptionLabelCode="selectdropdown" disabled="true"/>
									</div>

									<label class="control-label col-sm-2 required-control"
										for="InspectionDate"><spring:message
											code="employee.inspection.date" text="Inspection Date" /></label>

									<div class="col-sm-4">
										<div class="input-group">
											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
												var="date" />
											<form:input path="" class="form-control mandColorClass"
												id="inspectionDateId" value="${date}" maxlength="10" disabled="true"/>
											<label class="input-group-addon mandColorClass"
												for="inspectionDateId"><i class="fa fa-calendar"></i>
											</label>
										</div>
									</div>
								</div>
								<!-- verification Type -->
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class="" href="#InspectionType"><spring:message
												code="employee.inspection.type" text="Inspection Type" /> </a>
									</h4>
								</div>

								<div id="InspectionType" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"
												for="InspectionId"><spring:message
													code="employee.inspection.type" text="Inspection Type" /></label>
											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="ITY" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control" isMandatory="true"
													selectOptionLabelCode="selectdropdown" disabled="true" />
											</div>
											<apptags:input
												labelCode="employee.verification.complaint.number" path=""
												isMandatory="true" maxlegnth="10"
												cssClass="form control mandcolour hasNumber" isDisabled="true" />
											
										</div>
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"
												for="ComplaintDate"><spring:message
													code="employee.verification.complaint.date"
													text="Complaint Date " /></label>

											<div class="col-sm-4">
												<div class="input-group">
													<c:set var="now" value="<%=new java.util.Date()%>" />
													<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
														var="date" />
													<form:input path="" id="complaintDateId"
														cssClass="mandColorClass form-control"
														data-rule-required="true" value="${date}" maxlength="10" disabled="true"></form:input>
													<label class="input-group-addon mandColorClass"
														for="complaintDateId"><i class="fa fa-calendar"></i>
													</label>
												</div>
											</div>

											<label class="control-label col-sm-2 required-control"
												for="Department of Employee"><spring:message
													code="employee.verification.department.employee"
													text="Department of Employee" /></label>
											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="VCH" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control" isMandatory="true"
													selectOptionLabelCode="selectdropdown" disabled="true" />
											</div>
										</div>
									</div>
								</div>

								<!-- End verification Type -->


								<!--   Employee Details -->
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class="" href="#LocationDetails"><spring:message
												code="employee.verification.employee.details"
												text="Employee Details" /> </a>
									</h4>
								</div>
								<div id="InspectionDetails" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"
												for="Designation of Employee"><spring:message
													code="employee.verification.designation.employee"
													text="Designation of Employee" /> </label>
											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="VCH" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control" isMandatory="true"
													selectOptionLabelCode="selectdropdown" disabled="true"/>
											</div>

											<apptags:input
												labelCode="employee.verification.employee.name" path=""
												isMandatory="true" maxlegnth="25"
												cssClass="form control mandcolour hasNameClass"  isDisabled="true">
											</apptags:input>
										</div>
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"
												for="Schedule Period"><spring:message
													code="employee.verification.schedule.period"
													text="Schedule Period" /></label>
											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="VCH" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control" isMandatory="true"
													selectOptionLabelCode="selectdropdown" disabled="true" />
											</div>
										</div>
									</div>
								</div>
								<table class="table table-bordered table-striped"
									id="customFields">
									<tr>
										<th><spring:message code="employee.verification.routenno"
												text="Route No" /></th>
										<th><spring:message
												code="employee.verification.route.name" text="Route Name" /></th>
										<th><spring:message code="employee.verification.status"
												text="Status" /></th>
										<th><spring:message code="employee.verification.remarks"
												text="Remarks" /></th>
										<th><spring:message code="employee.verification.notice"
												text="Notice" /></th>
									</tr>
									<tr>
										<td>Route No #1</td>
										<td><input name="" type="text" class="form-control"
											id="RouteId" disabled></td>

										<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true"/></td>
										<td><input name="" type="text" class="form-control"
											id="Remarks" disabled></td>
										<td align="center"><button onclick="printdiv('receipt');"
												class="btn btn-primary hidden-print">
												<i class="fa fa-print"></i>
												<spring:message code="solid.waste.print" text="Print" />
											</button></td>
									</tr>
								</table>
							</div>
						</div>
						<!--   End Employee Details -->
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" id="save" class="btn btn-success btn-submit"
						onclick="saveEmployeeVerificationForm(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backButton();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
