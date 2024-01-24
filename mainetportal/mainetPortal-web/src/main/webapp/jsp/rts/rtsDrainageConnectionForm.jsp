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
<script src="js/mainet/validation.js"></script>
<script src="js/rti/rtiApplicationForm.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/rts/rtsDrainageConnectionForm.js"></script>

<script>
	$(function() {

		$(".datepicker31").datepicker({
			dateFormat : 'dd/mm/yy',

		});
	});
</script>



<style>
textarea.form-control {
	resize: vertical !important;
	height: 2.3em;
}

.hide-calendar .ui-datepicker-calendar {
	display: none;
}
</style>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="rts.drainageConnApplication"
							text="Drainage Connection Application Form"></spring:message></b>

				</h2>

				<apptags:helpDoc url=""></apptags:helpDoc>
			</div>


			<div class="widget-content padding">
				<form:form action="drainageConnection.html" method="POST"
					name="Drainage" class="form-horizontal" id="rtsService"
					commandName="command">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<form:hidden path="checkListApplFlag" id="checkListApplFlag" />
					<form:hidden path="applicationchargeApplFlag"
						id="applicationchargeApplFlag" />
					<form:hidden path="reqDTO.applicationId" id="applicationId" />


					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<button type="button" class="close" onclick="closeOutErrBox()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span id="errorId"></span>
					</div>


					<div class="mand-label clearfix">
						<span>Field with <i class="text-red-1">*</i> is mandatory
						</span>
					</div>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<div class="panel panel-default">

							<!------------------------------------------------------------  -->
							<!--  Personal Info starts here -->
							<!------------------------------------------------------------  -->
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a4"> <spring:message code="rts.personalInfo"
											text="Personal Info" /></a>
								</h4>
								<div id="a4" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<apptags:input labelCode="rti.firstName"
												cssClass="hasCharacter mandColorClass hasNoSpace"
												path="reqDTO.fName" isMandatory="true" maxlegnth="100"
												isDisabled="true"></apptags:input>

											<apptags:input labelCode="rti.lastName"
												cssClass="hasCharacter mandColorClass hasNoSpace"
												path="reqDTO.lName" isMandatory="true" maxlegnth="100"
												isDisabled="true"></apptags:input>
										</div>
										<div class="form-group">
											<apptags:input labelCode="rti.mobile1"
												cssClass="hasMobileNo mandColorClass "
												path="reqDTO.mobileNo" isMandatory="true" maxlegnth="100"
												isDisabled="true"></apptags:input>
											<apptags:input labelCode="chn.lEmail"
												cssClass="hasemailclass  mandColorClass hasNoSpace"
												path="reqDTO.email" isMandatory="true" maxlegnth="100"
												isDisabled="true"></apptags:input>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!------------------------------------------------------------  -->
						<!--  Personal Info ends here -->
						<!------------------------------------------------------------  -->

						<div class="panel panel-default">

							<!------------------------------------------------------------  -->
							<!--  Drainage Connection Information -->
							<!------------------------------------------------------------  -->
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a4"> <spring:message code="rts.DraInfo"
											text="Drainage Connection Information" /></a>
								</h4>
								<div id="a4" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="address"><spring:message code="rti.address"
													text="Address" /></label>
											<div class="col-sm-4">
												<form:textarea class="form-control" id="rtiAddress"
													maxlength="1000"
													path="drainageConnectionDto.drainageAddress"
													disabled="${command.saveMode eq 'V' ? true : false }"></form:textarea>
											</div>

											<label class="col-sm-2 control-label required-control"><spring:message
													code="rts.applicantType" text="Applicant Type" /></label>
											<c:set var="baseLookupCode" value="APT" />
											<apptags:lookupField items="${command.getLevelData('APT')}"
												path="drainageConnectionDto.applicantType"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />
										</div>


										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="rts.sizeOfConnection" text="Size of Connection" /></label>
											<apptags:lookupField items="${command.getLevelData('CSZ')}"
												path="drainageConnectionDto.sizeOfConnection"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<%-- <apptags:input labelCode="Type Of Connection"
												cssClass="hasNumber mandColorClass "
												path="drainageConnectionDto.typeOfConnection" isMandatory="true"
												maxlegnth="100"></apptags:input> --%>

											<label class="col-sm-2 control-label required-control"
												for="applicantTitle"><spring:message
													code="rts.typeOfConnection" text="Type Of Connection" /></label>
											<c:set var="baseLookupCode" value="WCT" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="drainageConnectionDto.typeOfConnection"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />

										</div>

										<div class="form-group">
											<%-- <label class="control-label col-sm-2  required-control"
												for="text-1">Ward Name </label>
											<div class="col-sm-4">
												<form:select path="drainageConnectionDto.ward"
													class="form-control mandColorClass chosen-select-no-results"
													label="Select Ward Name" disabled="false" id="deId">
													<form:option value="" selected="true">Select Ward Name</form:option>
													<c:forEach items="${command.wardList}" var="ward">
														<form:option value="${ward.key}" code="${ward.key}">${ward.value}</form:option>
													</c:forEach>

												</form:select>
											</div> --%>
											<label class="control-label col-sm-2  required-control"
												for="text-1"><spring:message code="rts.wardName"
										 /> </label>
											<%-- <div class="col-sm-4">
												<form:select path="drainageConnectionDto.wardNo"
													class="form-control mandColorClass chosen-select-no-results"
													label="Select Ward Name" disabled="false" id="wardNo">
													<form:option value="" selected="true">Select Ward Name
													</form:option>
													<c:forEach items="${command.wardList}" var="ward">
														<form:option value="${ward.key}" code="${ward.key}">${ward.value}
														</form:option>
													</c:forEach>

												</form:select>
											</div> --%>
											<div class="col-sm-4">
												<form:select path="reqDTO.wardNo"
													class="form-control mandColorClass chosen-select-no-results"
													label="Select Ward Name" id="wardNo"
													disabled="${command.saveMode eq 'V' ? true : false }">
													<form:option value="" selected="true"><spring:message code="rts.selectWardeName" />
													</form:option>
													<c:forEach items="${command.wardList}" var="ward">
														<form:option value="${ward.key}" code="${ward.key}">${ward.value}
														</form:option>
													</c:forEach>

												</form:select>
											</div>




											<apptags:input labelCode="rts.propertyIndexNo"
												cssClass=" mandColorClass "
												path="drainageConnectionDto.propertyIndexNo"
												isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>


										</div>

										<div class="form-group"></div>
									</div>
								</div>
							</div>
						</div>
						<!------------------------------------------------------------  -->
						<!--  Drainage Connection Information -->
						<!------------------------------------------------------------  -->


						<div class="text-center margin-top-15">
							<button type="button" class="btn btn-danger"
								data-toggle="tooltip" data-original-title="Back"
								onclick="previousPage()">
								<i class="fa fa-chevron-circle-left padding-right-5"
									aria-hidden="true"></i><spring:message code="rti.back"
										 />
							</button>
							<c:if test="${command.saveMode eq 'V' ? false : true }">
							<button type="button" class="btn btn-warning"
								onclick="ResetForm()" data-toggle="tooltip"
								data-original-title="Reset">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message code="rts.reset"
										 />
							</button>
							</c:if>
							<button type="button" class="btn btn-blue-2" title="Submit"
								onclick="getCheckList(this)">
								<spring:message code="rts.proceed" /><i class="fa fa-arrow-right padding-left-5"
									aria-hidden="true"></i>
							</button>
						</div>

					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>