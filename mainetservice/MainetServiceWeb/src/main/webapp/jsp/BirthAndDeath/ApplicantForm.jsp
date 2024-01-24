<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<script type="text/javascript" src="js/birthAndDeath/rtsApplicantForm.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

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
					<%-- <b><spring:message code="" text="Application form"></spring:message></b> --%>
					<b>${command.serviceName}</b>

				</h2>

				<apptags:helpDoc url=""></apptags:helpDoc>
			</div>


			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="leadlift.master.fieldmand"
							text="Field with" /> <i class="text-red-1">*</i> <spring:message
							code="leadlift.master.ismand" text="is mandatory" /></span>
				</div>
				<form:form action="rtsServices.html" method="POST" name="rtsService"
					class="form-horizontal" id="rtsService" commandName="command">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="requestDTO.serviceId"  />

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
							<form:hidden path="saveMode" id="saveMode" />
							<!------------------------------------------------------------  -->
							<!--  Applicant Details starts here -->
							<!------------------------------------------------------------  -->
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a4"> <spring:message
											code="applicant.details" text="Applicant Details" /></a>
								</h4>
								<div id="a4" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="applicantTitle">
												<spring:message
											code="applicant.title" text="Title" />
												</label>
											<c:set var="baseLookupCode" value="TTL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="requestDTO.titleId"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<apptags:input labelCode="rti.firstName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="requestDTO.fName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">


											<apptags:input labelCode="rti.middleName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="requestDTO.mName" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

											<apptags:input labelCode="rti.lastName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="requestDTO.lName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="requestDTO.gender"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />
										</div>



									</div>
								</div>
							</div>

							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="applicant.ApplicantAddress" text="Applicant Address" /></a>
							</h4>


							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<%-- 	<div class= "form-group">
											<label for="text-1"
												class="control-label col-sm-2 required-control">
												Date</label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control datepicker" id="toDate"
														path="" maxlength="10" disabled="false"
														onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
													<label class="input-group-addon"
														for="trasaction-date-icon30"><i
														class="fa fa-calendar"></i></label>

												</div>
											</div>
								</div> --%>

									<div class="form-group">
										<apptags:input labelCode="rti.buildingName"
											cssClass="hasNumClass mandColorClass " path="requestDTO.bldgName"
											isMandatory="true" maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="rti.taluka"
											cssClass=" mandColorClass " path="requestDTO.blockName"
											isMandatory="true" maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="applicant.roadName"
											cssClass="hasNumClass mandColorClass"
											path="requestDTO.roadName" isMandatory="true" maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }">
										</apptags:input>
										<%-- <apptags:input labelCode="Ward Name"
											cssClass="mandColorClass " path="requestDTO.wardNo"
											isMandatory="true" maxlegnth="100">
										</apptags:input> --%>


										<label class="control-label col-sm-2  required-control"
											for="text-1"><spring:message
										code="applicant.wardName" text="Ward Name" /></label>
										<div class="col-sm-4">
											<form:select path="requestDTO.wardNo"
												class="form-control mandColorClass chosen-select-no-results"
												label="Select Ward Name"
												disabled="${command.saveMode eq 'V' ? true : false }"
												id="wardNo">
												<form:option value="" selected="true">Select Ward Name</form:option>
												<c:forEach items="${command.wardList}" var="ward">
													<form:option value="${ward.key}" code="${ward.key}">${ward.value}</form:option>
												</c:forEach>

											</form:select>
										</div>



									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.Try5"
											cssClass="hasNameClass mandColorClass "
											path="requestDTO.cityName"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											isMandatory="true" maxlegnth="100"></apptags:input>
										<apptags:input labelCode="rti.pinCode"
											cssClass="hasPincode mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="requestDTO.pincodeNo" isMandatory="true"
											maxlegnth="100"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.mobile1"
											cssClass="hasMobileNo mandColorClass "
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="requestDTO.mobileNo" isMandatory="true" maxlegnth="10"></apptags:input>
										<apptags:input labelCode="chn.lEmail"
											cssClass="hasemailclass  mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="requestDTO.email" isMandatory="true" maxlegnth="100"></apptags:input>
									</div>
								</div>



							</div>

							<div class="text-center margin-top-15">
								<button type="button" class="btn btn-danger" title="Back"
									onclick="back()">
									<i class="fa fa-chevron-circle-left padding-right-5"
										aria-hidden="true"></i>
									<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
								</button>
									<button type="button" class="btn btn-warning"
										onclick="resetMemberMaster(this);" title="Reset">
										<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
										<spring:message code="bt.clear" />
									</button>
								<button type="button" class="btn btn-blue-2" title="Proceed"
									onclick="serviceForm(this,'rtsServices.html','serviceForm')">
									<spring:message code="TbDeathregDTO.form.proceed" />
									<i class="fa fa-arrow-right padding-left-5"
										aria-hidden="true"></i>
								</button>
							</div>

						</div>
					</div>

				</form:form>

			</div>

		</div>
	</div>
</div>







