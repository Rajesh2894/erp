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
	src="js/solid_waste_management/VendorVerificationForm.js"></script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vendor.verification.heading"
					text="Vendor Inspection" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="solid.waste.help" text="Help" /></span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory " /></span>
			</div>
			<form:form action="VendorInspection.html" name="VendorInspection"
				id="VendorInspectionId" class="form-horizontal">
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
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#VendorInspection"><spring:message
										code="vendor.verification.heading" text="Vendor Inspection " /></a>
							</h4>
						</div>
						<div id="TripSheet" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="Department"><spring:message
											code="vendor.verification.department" text="Department " /></label>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.saveMode eq 'V'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true" />
										</c:if>
									</div>
									<label class="control-label col-sm-2 required-control"
										for="Designation"><spring:message
											code="vendor.verification.designation" text="Designation" /></label>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.saveMode eq 'V'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true" />
										</c:if>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="InspectorName"><spring:message
											code="vendor.verification.inspector.name"
											text="Inspector Name" /></label>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.saveMode eq 'V'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true" />
										</c:if>
									</div>
									<label class="control-label col-sm-2 required-control"
										for="InspectionDate"><spring:message
											code="vendor.verification.date" text="Inspection Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<c:if
												test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
												<c:set var="now" value="<%=new java.util.Date()%>" />
												<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
													var="date" />
												<form:input path="" id="inspectionDateId"
													cssClass="mandColorClass form-control"
													data-rule-required="true" value="${date}" maxlength="10"></form:input>
												<label class="input-group-addon mandColorClass"
													for="inspectionDateId"><i class="fa fa-calendar"></i>
												</label>
											</c:if>
											<c:if test="${command.saveMode eq 'V' }">
												<c:set var="now" value="<%=new java.util.Date()%>" />
												<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
													var="date" />
												<form:input path="" id="inspectionDateId"
													cssClass="mandColorClass form-control"
													data-rule-required="true" value="${date}" maxlength="10"
													disabled="true"></form:input>
												<label class="input-group-addon mandColorClass"
													for="inspectionDateId"><i class="fa fa-calendar"></i>
												</label>
											</c:if>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="InspectionType"><spring:message
											code="vendor.verification.type" text="Inspection Type" /></label>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.saveMode eq 'V' }">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true" />
										</c:if>
									</div>
									<c:if
										test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
										<apptags:input labelCode="Complaint Number" path=""
											isMandatory="true" maxlegnth="10"
											cssClass="form control mandcolour hasNumber">
										</apptags:input>
									</c:if>
									<c:if test="${command.saveMode eq 'V' }">
										<apptags:input labelCode="Complaint Number" path=""
											isMandatory="true" maxlegnth="10"
											cssClass="form control mandcolour hasNumber"
											isDisabled="true">
										</apptags:input>
									</c:if>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="ComplaintDate"><spring:message
											code="employee.verification.complaint.date"
											text="Complaint Date " /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<c:if
												test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
												<c:set var="now" value="<%=new java.util.Date()%>" />
												<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
													var="date" />
												<form:input path="" id="complaintDateId"
													cssClass="mandColorClass form-control"
													data-rule-required="true" value="${date}" maxlength="10"></form:input>
												<label class="input-group-addon mandColorClass"
													for="complaintDateId"><i class="fa fa-calendar"></i>
												</label>
											</c:if>
											<c:if test="${command.saveMode eq 'V'}">
												<c:set var="now" value="<%=new java.util.Date()%>" />
												<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
													var="date" />
												<form:input path="" id="complaintDateId"
													cssClass="mandColorClass form-control"
													data-rule-required="true" value="${date}" maxlength="10"
													disabled="true"></form:input>
												<label class="input-group-addon mandColorClass"
													for="complaintDateId"><i class="fa fa-calendar"></i>
												</label>
											</c:if>
										</div>
									</div>
									<label class="control-label col-sm-2 required-control"
										for="VendorName"><spring:message
											code="vendor.verification.vendor.name" text="Vendor Name" /></label>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.saveMode eq 'V' }">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true" />
										</c:if>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="ContractNumber"><spring:message
											code="vendor.verification.contract.no" text="Contract Number" /></label>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.saveMode eq 'V'}">
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true" />

										</c:if>
									</div>
									<label class="control-label col-sm-2 required-control"
										for="SchedulePeriod"><spring:message
											code="vendor.verification.schedule.period"
											text="Contract Number" /></label>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.saveMode eq 'V'}">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" disabled="true" />
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="RoteSelect" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="table-responsive">
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
										<th><spring:message
												code="vendor.verification.payment.hold" text="Payment Hold" /></th>
										<th><spring:message code="employee.verification.notice"
												text="Notice" /></th>
									</tr>
									<tr>
										<td>Route No #1</td>
										<td><form:input name="" path="" class="form-control"
												id="RouteId" disabled="true" /></td>
										<td><c:if
												test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
												<c:set var="baseLookupCode" value="VCH" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control" isMandatory="true"
													selectOptionLabelCode="selectdropdown" />
											</c:if> <c:if test="${command.saveMode eq 'V'}">
												<c:set var="baseLookupCode" value="VCH" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control" isMandatory="true"
													selectOptionLabelCode="selectdropdown" disabled="true" />
											</c:if></td>
										<td><c:if
												test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
												<form:input name="" path="" class="form-control"
													id="Remarks" placeholder="Please Enter" />
											</c:if> <c:if test="${command.saveMode eq 'V'}">
												<form:input name="" path="" class="form-control"
													id="Remarks" placeholder="Please Enter" disabled="true" />
											</c:if></td>
										<td class="text-center"><form:checkbox path=""
												value="true" /></td>
										<td align="center"><button onclick="printdiv('receipt');"
												class="btn btn-primary hidden-print">
												<i class="fa fa-print"></i>
												<spring:message code="solid.waste.print" text="Print" />
											</button></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveVendorVerificationForm(this);">
							<spring:message code="solid.waste.submit" text="Submit" />
						</button>
						<button type="Reset" class="btn btn-warning">
							<spring:message code="solid.waste.reset" text="Reset" />
						</button>
					</c:if>
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