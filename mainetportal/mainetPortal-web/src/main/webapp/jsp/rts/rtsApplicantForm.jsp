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
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/rts/rtsApplicantForm.js"></script>
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
/* .ui-datepicker-calendar {
	display: none;
} */
</style>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="rts.rightToService"
							text="Application Form"></spring:message></b>
				</h2>
				<apptags:helpDoc url="rtsService.html"></apptags:helpDoc>
				<%-- <div class="additional-btn">
					<apptags:helpDoc url="RtsServiceForm.html"></apptags:helpDoc>
				</div> --%>
			</div>

			<div class="widget-content padding">

				<form:form method="POST" action="RtsServiceForm.html"
					class="form-horizontal" id="rtiForm" name="rtiForm">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;"></div>
					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<div class="panel panel-default">
							<form:hidden path="saveMode" id="saveMode" />
							<form:hidden path="serviceCode" id="serviceCode" />
							<form:hidden path="reqDTO.applicationId" id="applicationId" />
							<!------------------------------------------------------------  -->
							<!--  Applicant Details starts here -->
							<!------------------------------------------------------------  -->
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a4" tabindex="-1"> <spring:message code="rts-applicantDetails"
											text="Applicant Details" /></a>
								</h4>
								<div id="a4" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="applicantTitle"><spring:message
													code="rts.title" /></label>
											<c:set var="baseLookupCode" value="TTL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="reqDTO.titleId" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true" showOnlyLabel="rti.title"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<apptags:input labelCode="rti.firstName"
												cssClass="hasCharacter mandColorClass hasNoSpace"
												path="reqDTO.fName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">


											<apptags:input labelCode="rti.middleName"
												cssClass="hasCharacter mandColorClass hasNoSpace"
												path="reqDTO.mName" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

											<apptags:input labelCode="rti.lastName"
												cssClass="hasCharacter mandColorClass hasNoSpace"
												path="reqDTO.lName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">

											<label class="col-sm-2 control-label"><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="reqDTO.gender"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />
										</div>



									</div>
								</div>
							</div>
							<!------------------------------------------------------------  -->
							<!--  Applicant Details starts here -->
							<!------------------------------------------------------------  -->


							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1" tabindex="-1"> <spring:message code="rts-applicantAddress"
										text="Applicant Address" /></a>
							</h4>

							<!------------------------------------------------------------ -->
							<!--  Service form starts here -->
							<!------------------------------------------------------------  -->

							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">


									<div class="form-group">
										<apptags:input labelCode="rti.buildingName"
											cssClass="mandColorClass" path="reqDTO.bldgName"
											isMandatory="true" maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="rti.taluka"
											cssClass=" mandColorClass " path="reqDTO.blockName"
											maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="rts.roadName" cssClass="mandColorClass"
											path="reqDTO.roadName"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }">
										</apptags:input>
										<%-- <apptags:input labelCode="Ward Name"
											cssClass="mandColorClass " path="reqDTO.wardNo"
											isMandatory="true" maxlegnth="100">
										</apptags:input> --%>


										<label class="control-label col-sm-2  required-control"
											for="text-1"><spring:message code="rts.wardName"
										 /></label>
										<div class="col-sm-4">
											<form:select path="reqDTO.wardNo"
												class="form-control mandColorClass chosen-select-no-results"
												label="Select Ward Name" id="wardNo"
												disabled="${command.saveMode eq 'V' ? true : false }">
												<form:option value="" selected="true"><spring:message code="rts.selectWardeName"
										 />
													</form:option>
												<c:forEach items="${command.wardList}" var="ward">
													<form:option value="${ward.key}" code="${ward.key}">${ward.value}
														</form:option>
												</c:forEach>

											</form:select>
										</div>



									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.Try5"
											cssClass="hasCharacter mandColorClass "
											path="reqDTO.cityName" isMandatory="true" maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="rti.pinCode"
											cssClass="hasPincode mandColorClass hasNoSpace"
											path="reqDTO.pincodeNo"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.mobile1"
											cssClass="hasMobileNo mandColorClass " path="reqDTO.mobileNo"
											isMandatory="true" maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="chn.lEmail"
											cssClass="hasemailclass  mandColorClass hasNoSpace"
											path="reqDTO.email"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>
								</div>



							</div>
							<!------------------------------------------------------------ -->
							<!--  Service form ends here -->
							<!------------------------------------------------------------  -->






							<!------------------------------------------------------------  -->
							<!--  Applicant Address starts here -->
							<!------------------------------------------------------------  -->
							<%-- <div class="panel panel-default" id="accordion_single_collapse">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a9"> <spring:message code="rts.serviceDetails"
											 text="Service Details"></spring:message></a>
								</h4>
								<div id="a9" class="panel-collapse collapse in">
									<div class="panel-body">
										<!-- <div class="form-group"> -->
												<label class="control-label col-sm-2  required-control"
												for="text-1">Department Name </label>
											<div class="col-sm-4">
												<form:select path="reqDTO.deptId"
													class="form-control mandColorClass chosen-select-no-results"
													label="Select Department Name" disabled="false" id="deId"
													onchange ="getServiceList22(this,'rtsService.html','getServiceList')"
													>
													<!-- Here the option is being loaded in the drop down list using forEach loop  -->
													<form:option value="" selected="true">Select Department Name</form:option>
													<c:forEach items="${command.depList}" var="dept">
														<form:option value="${dept.key}" code="${dept.key}">${dept.value}</form:option>
													</c:forEach>

												</form:select>
									<!-- 	</div> -->
										<!-- onchange ="getServiceList(this,'rtsService.html','getServiceList')" -->
										<div class="form-group">
											<label class="control-label col-sm-2  required-control"
												for="text-1"><spring:message code="rts.serviceName" />
											</label>
											<div class="col-sm-4">
												<form:select path="reqDTO.serviceShortCode"
													class="form-control mandColorClass chosen-select-no-results"
													label="Select Department Name" id="serviceId"
													disabled="${command.saveMode eq 'V' ? true : false }">
													<!-- Here the option is being loaded in the drop down list using forEach loop  -->
													<form:option value="" selected="true">
														<spring:message code="rts.serviceName" />
													</form:option>
													<c:forEach items="${command.serviceMap}" var="data">
														<form:option value="${data.key}" code="${data.value}"
															label="${data.value}"></form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
								</div>
							</div> --%>
						</div>
						<c:if
							test="${command.reqDTO.serviceShortCode ==null || command.saveMode eq 'V' ? true : false}">
							<form:hidden path="reqDTO.serviceShortCode" />
						</c:if>
						<!------------------------------------------------------------  -->
						<!--  Applicant Address ends here -->
						<!------------------------------------------------------------  -->


						<div class="text-center marginss-top-15">
						<button type="button" class="btn btn-blue-2" title="Proceed"
								onclick="serviceForm(this,'rtsService.html','serviceForm')">
								<spring:message code="rts.proceed"
										 /><i class="fa fa-arrow-right padding-left-5"
									aria-hidden="true"></i>
							</button>
							<c:if test="${command.saveMode eq 'V' ? false : true }">
								<button type="button" class="btn btn-warning"
									onclick="resetApplicantForm()" title="Reset">
									<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message code="rts.reset"
										 />
								</button>
							</c:if>
							<button type="button" class="btn btn-danger"
								data-toggle="tooltip" title="Back" onclick="back()">
								<i class="fa fa-chevron-circle-left padding-right-5"
									aria-hidden="true"></i><spring:message code="rti.back"
										 />
							</button>
						</div>

					</div>
					<br>
					<br>
					<br>
					<br>
				</form:form>
			</div>

		</div>
	</div>
</div>








