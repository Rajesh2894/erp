<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/birthAndDeath/birthCorrection.js"></script>

<script>
	$('[data-toggle="tooltip"]').tooltip({
	    trigger : 'hover'
	})
	
	$(document).ready(function() {
		var list = document.getElementById('corrCategory');
		var listArray = new Array();
		if (list != null) {
			for (i = 0; i < list.options.length; i++) {
				listArray[i] = list.options[i].value;
			}
			$.each(listArray, function(index) {

				$("." + listArray[index]).attr("disabled", true);
			});
		}

	});
</script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="BirthRegDto.BrRegCorr" text="Birth Registration Correction" />
				</h2>
				<!-- <div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div> -->
				<apptags:helpDoc url="BirthCorrectionForm.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding" id="ashish">
				<form:form id="frmBirthCorrectionForm" commandName="command"
					action="BirthCorrectionForm.html" method="POST"
					class="form-horizontal" name="birthCorrectionFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="birthRegDto.brId"
						cssClass="hasNumber form-control" id="brId" />
					<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv">
					</div>

					<!-- fetch data after search start -->
					
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
							<c:if test="${command.saveMode eq 'E'}">
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
												path="birthRegDto.requestDTO.titleId"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<apptags:input labelCode="rti.firstName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="birthRegDto.requestDTO.fName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">


											<apptags:input labelCode="rti.middleName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="birthRegDto.requestDTO.mName" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

											<apptags:input labelCode="rti.lastName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="birthRegDto.requestDTO.lName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>
										<div class="form-group">

											<label class="col-sm-2 control-label "><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="birthRegDto.requestDTO.gender"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
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

									<div class="form-group">
										<apptags:input labelCode="rti.buildingName"
											cssClass="hasNumClass mandColorClass " path="birthRegDto.requestDTO.bldgName"
											maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="rti.taluka"
											cssClass="hasNumClass mandColorClass " path="birthRegDto.requestDTO.blockName"
											 maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="applicant.roadName"
											cssClass="hasNumClass mandColorClass"
											path="birthRegDto.requestDTO.roadName"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }">
										</apptags:input>
										<c:if test="${command.kdmcEnv eq 'N'}">
											<label class="control-label col-sm-2 " for="text-1"><spring:message
													code="tbDeathregDTO.applicant.wardName" text="Ward Name" /></label>
											<c:set var="baseLookupCode" value="BWD" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="birthRegDto.requestDTO.wardNo" cssClass="form-control"
												hasId="true" selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.kdmcEnv eq 'Y'}">
											<label class="control-label col-sm-2 required-control"
												for="text-1"><spring:message code="ParentDetailDTO.pdRegUnitId"
													text="Registration Unit" /></label>
											<c:set var="baseLookupCode" value="REU" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="birthRegDto.parentDetailDTO.pdRegUnitId"
												cssClass="form-control" hasId="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>

									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.Try5"
											cssClass="hasNameClass mandColorClass "
											path="birthRegDto.requestDTO.cityName"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											isMandatory="true" maxlegnth="100"></apptags:input>
										<apptags:input labelCode="rti.pinCode"
											cssClass="hasPincode mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.requestDTO.pincodeNo" 
											maxlegnth="6"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.mobile1"
											cssClass="hasMobileNo mandColorClass "
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.requestDTO.mobileNo" isMandatory="true" maxlegnth="10"></apptags:input>
										<apptags:input labelCode="chn.lEmail"
											cssClass="hasemailclass  mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.requestDTO.email" maxlegnth="100"></apptags:input>
									</div>
								</div>



							</div>
					<div class="panel panel-default" id="correct">
              
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="bnd.correction.category" text="Correction Catagory" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="corrCategory"><spring:message
										code="bnd.correction.category" text="Correction Catagory" /></label>
										<div class="col-sm-4">
											<form:select path="birthRegDto.corrCategory" multiple="true" onchange="correctionsCategory(this)" id="corrCategory"
												class="form-control chosen-select-no-results "  disabled="false" label="Select">
												<c:set var="baseLookupCode" value="BCC" />
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpCode}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>

							</div>
                </div>



						</c:if>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#birthCor-1">
										<spring:message code="BirthRegistrationDTO.birthRegistration" text="Birth Registration" /></a>
								</h4>
							</div>
							<div id="birthCor-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:date fieldclass="datepicker" labelCode="BirthRegistrationDTO.brDob"
							datePath="birthRegDto.brDob" cssClass="form-control mandColorClass BRDOB" isDisabled="true" >
						</apptags:date>
										
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegistrationDTO.brSex" text="Sex" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.brSex" cssClass="form-control BRSEX"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>		
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brRegNo"
											path="birthRegDto.brRegNo" isReadonly="true"
											isMandatory="false" cssClass="alphaNumeric" maxlegnth="20">
										</apptags:input>
										</div>						
								</div>
							</div>
						</div>
							
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-2">
										<spring:message code="BirthRegistrationDTO.childbirthDetails" text="Child Birth Details" /></a>
								</h4>
							</div>
							<div id="birthCor-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brChildName"
											path="birthRegDto.brChildName" isMandatory="" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brChildNameMar"
											path="birthRegDto.brChildNameMar" isMandatory="" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
										</apptags:input>
									</div>
				
									<%-- <div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegistrationDTO.brBirthPlaceType"
												text="Birth Place Type" />
										</label>
										<c:set var="baseLookupCode" value="DPT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											changeHandler="selecthosp(this)" disabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.brBirthPlaceType" cssClass="form-control"
											isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
				
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegistrationDTO.hiId"
												text="Hospital Name" />
										</label>
				
										<div class="col-sm-4">
											<form:select path="birthRegDto.hiId" cssClass="form-control" disabled="${command.saveMode eq 'V' ? true : false }"
												id="hospitalList" data-rule-required="true">
												<form:option value="">
													<spring:message code="Select" text="Select" />
												</form:option>
												<c:forEach items="${hospitalList}" var="hospList">
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<form:option value="${hospList.hiId}">${hospList.hiName}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${hospList.hiId}">${hospList.hiNameMar}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</div>
									</div> --%>
				
									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brBirthPlace"
											path="birthRegDto.brBirthPlace" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brBirthPlaceMar" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brBirthPlaceMar" isMandatory="true"
											cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddr"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brBirthAddr" isMandatory="true"
											cssClass="hasNumClass form-control BRBIRTHADDR" maxlegnth="800">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddrMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brBirthAddrMar" isMandatory="true"
											cssClass="hasNumClass form-control BRBIRTHADDR" maxlegnth="800">
										</apptags:input>
									</div>

									
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-3">
										<spring:message code="BirthRegistrationDTO.parentDetails"
											text="Parent Details" />
									</a>
								</h4>
							</div>
							<div id="birthCor-3" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdFathername"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdFathername"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdFathernameMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdFathernameMar"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegDto.BrFatherEdu"
												text="Select Father Education" />
										</label>
										<c:set var="baseLookupCode" value="EDU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdFEducnId"
											cssClass="form-control CPDFEDUCNID" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" 
											disabled="${command.saveMode eq 'V' ? true : false }"/>

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegDto.BrFatherOcc"
												text="Select Father Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdFOccuId"
											cssClass="form-control CPDFOCCUID" isMandatory="" hasId="true"
											selectOptionLabelCode="selectdropdown" 
											disabled="${command.saveMode eq 'V' ? true : false }"/>

									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdMothername"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdMothername"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdMothernameMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdMothernameMar"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegDto.BrMotherEdu"
												text="Select Mother Education" />
										</label>
										<c:set var="baseLookupCode" value="EDU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdMEducnId"
											cssClass="form-control CPDMEDUCNID" isMandatory="" hasId="true"
											selectOptionLabelCode="selectdropdown" 
											disabled="${command.saveMode eq 'V' ? true : false }"/>

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegDto.BrMotherOcc"
												text="Select Mother Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdMOccuId"
											cssClass="form-control CPDMOCCUID" isMandatory="" hasId="true"
											selectOptionLabelCode="selectdropdown" 
											disabled="${command.saveMode eq 'V' ? true : false }"/>

									</div>
									<div class="form-group">

										<apptags:input labelCode="ParentDetailDTO.pdAgeAtBirth"
											path="birthRegDto.parentDetailDTO.pdAgeAtBirth"
											isMandatory="true" cssClass="hasNumber form-control PDAGEATBIRTH"
											maxlegnth="2" isDisabled="${command.saveMode eq 'V'}">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdLiveChildn"
											path="birthRegDto.parentDetailDTO.pdLiveChildn"
											isMandatory="" cssClass="hasNumber form-control PDLIVECHILDN"
											maxlegnth="2" isDisabled="${command.saveMode eq 'V'}">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-4">
										<spring:message code="ParentDetailDTO.parentAddressDetails" text="Parent's Address Details" /></a>
								</h4>
							</div>
							<div id="birthCor-4" class="panel-collapse collapse">
								<div class="panel-body">
								
									<div class="form-group">
										<apptags:input
											labelCode="ParentDetailDTO.pdParaddress" isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.parentDetailDTO.pdParaddress"
											isMandatory="true" cssClass="hasNumClass form-control ADDRESS" maxlegnth="350">
										</apptags:input>
										<apptags:input
											labelCode="ParentDetailDTO.pdParaddressMar" isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.parentDetailDTO.pdParaddressMar"
											isMandatory="true" cssClass="hasNumClass form-control ADDRESS" maxlegnth="350">
										</apptags:input>
									</div>
									
									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr"
											path="birthRegDto.parentDetailDTO.pdAddress" isDisabled="${command.saveMode eq 'V' ? true : false }"
											isMandatory="true" cssClass="hasNumClass form-control ADDRESS"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar"
											path="birthRegDto.parentDetailDTO.pdAddressMar" isDisabled="${command.saveMode eq 'V' ? true : false }"
											isMandatory="true" cssClass="hasNumClass form-control ADDRESS"
											maxlegnth="350">
										</apptags:input>
									</div>

									<div class="form-group">

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdReligionId" text="Religion" />
										</label>
										<c:set var="baseLookupCode" value="RLG" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdReligionId"
											cssClass="form-control CPDRELIGIONID" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" 
											disabled="${command.saveMode eq 'V' ? true : false }"/>
											
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="applicant.wardName" text="WARD" />
										</label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.wardid"
											cssClass="form-control WARDID" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" 
											disabled="${command.saveMode eq 'V' ? true : false }"/>
									</div>
									
									
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-5">
										<spring:message code="TbDeathregDTO.form.informantDetails" text="Informant Details" />
									</a>
								</h4>
							</div>
							<div id="birthCor-5" class="panel-collapse collapse">
								<div class="panel-body">
								 <div class="form-group">
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantName"
											path="birthRegDto.brInformantName" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control BRINFORMANTNAME" maxlegnth="800">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantAddr"
											path="birthRegDto.brInformantAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNumClass form-control BRINFORMANTNAME" maxlegnth="800">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-6">
										<spring:message text="Certificate print details"  code="Issuaance.death.certificate.print"/></a>
								</h4>
							</div>
							<div id="birthCor-6" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.alreayIssuedCopy" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.alreayIssuedCopy"
											cssClass="hasNumClass form-control"
											isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.numberOfCopies" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.noOfCopies" maxlegnth="2"
											cssClass="hasNumber form-control" isMandatory="true" >
										</apptags:input>
									</div>
									<%-- <c:if test="${command.birthRegDto.chargesStatus eq 'CC' || command.birthRegDto.chargesStatus eq 'CA'}">
									<div class="form-group" id="amountid">
										<apptags:input labelCode="BirthRegDto.amount"
											path="birthRegDto.amount" cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>	
									</div>
									</c:if> --%>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${command.birthRegDto.statusCheck eq 'A'}">
					<div class="text-center">
					<div class="padding-top-10 text-center" align="center" id="chekListChargeId">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" align="center" class="btn btn-submit" data-toggle="tooltip" data-original-title="" id="proceedId"
							onclick="getChecklistAndCharges(this)">
							<spring:message code="BirthRegDto.proceed" text="Proceed" />
						</button>
						</c:if>
						<c:if test="${command.saveMode ne 'V'}">
						<button type="button" align="center" onclick="resetMemberMaster(this);"
										class="btn btn-warning" data-toggle="tooltip" data-original-title="" id="resetId">
										<spring:message code="BirthRegDto.reset"/>
									</button>
									</c:if>
									
						<button type="button" align="center" class="btn btn-danger "
							id="backId" data-toggle="tooltip" data-original-title=""
							onclick="window.location.href ='BirthCorrectionForm.html'">
							<spring:message code="BirthRegDto.back"/>
						</button>
					</div>
					</div>

					<c:if test="${not empty command.checkList}">
						<h4>
							<spring:message text="Upload Documents" />
						</h4>
						<fieldset class="fieldRound">
							<div class="overflow">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped" id="BirthCorrTable">
										<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="label.checklist.srno" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="bnd.documentName" text="Document Type" /></label></th>
													<th><label class="tbold"><spring:message
																code="bnd.documentDesc" text="Document Description" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.status" text="Status" /></label></th>
													<th><label class="tbold"><spring:message
																code="bnd.upload.document" text="Upload" /></label></th>
												</tr>


												<c:forEach items="${command.checkList}" var="lookUp" varStatus="lk">
												<tr>
													<td>${lookUp.documentSerialNo}</td>
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
															<td><label>${lookUp.doc_DESC_ENGL}</label></td>
														</c:when>
														<c:otherwise>
															<c:set var="docName" value="${lookUp.doc_DESC_Mar}" />
															<td><label>${lookUp.doc_DESC_Mar}</label></td>
														</c:otherwise>
													</c:choose>
													<%-- <td><form:input	path="checkList[${lk.index}].docDescription"
														type="text"	class="form-control alphaNumeric "
														maxLength="50" id="docDescription[${lk.index}]" data-rule-required="true" />
													</td> --%>
												<c:choose>
													<c:when test="${lookUp.docDes ne null}">
													<td><form:select 
													path="checkList[${lk.index}].docDescription"
													 class="form-control" id="docTypeSelect_${lk.index}">
													<form:option value="">
														<spring:message code="mrm.select" />
													</form:option>
													<c:set var="baseLookupCode" value="${lookUp.docDes}" />
													<c:forEach items="${command.getLevelData(baseLookupCode)}" var="docLookup">	
														<form:option value="${docLookup.lookUpDesc}" >${docLookup.lookUpDesc}</form:option>
													</c:forEach>
												    </form:select></td>
												    </c:when>
													<c:otherwise>
													<td></td>
													</c:otherwise>
												</c:choose>
													<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
														<td>${lookUp.descriptionType}<spring:message code="bnd.acknowledgement.doc.mand"
																text="Mandatory" /></td>
													</c:if>
										
													<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
														<td>${lookUp.descriptionType}<spring:message code="bnd.acknowledgement.doc.opt"
																text="Optional" /></td>
													</c:if>
													<td>
														<div id="docs_${lk}">
															<apptags:formField fieldType="7" labelCode=""
																hasId="true" fieldPath="checkList[${lk.index}]"
																isMandatory="false" showFileNameHTMLId="true"
																fileSize="CARE_COMMON_MAX_SIZE"
																maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC"
																checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																checkListDesc="${docName}" currentCount="${lk.index}" />
														</div>
														<small class="text-blue-2"> <spring:message code="bnd.checklist.tooltip"
															      text="(Upload Image File upto 1 MB)" />
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</fieldset>
						</c:if>
						</c:if>
						 <form:hidden path="birthRegDto.chargesStatus" id="chargeStatus"/>
						
                  <c:if test="${command.birthRegDto.statusCheck eq 'NA' || not empty command.checkList || command.birthRegDto.chargesStatus eq 'CA'}">

					<div class="text-center">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-green-3" data-toggle="tooltip" data-original-title=""
							onclick="saveBirthCorrData(this)">
							<spring:message code="BirthRegDto.save"/>
						</button>
						</c:if>
						<c:if test="${command.saveMode ne 'V'}">
						<button type="button" onclick="resetMemberMaster(this);"
										class="btn btn-warning" data-toggle="tooltip" data-original-title="" id="resetId">
										<spring:message code="BirthRegDto.reset"/>
					       </button>
					       </c:if>
						<button type="button" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title=""
								onclick="window.location.href ='BirthCorrectionForm.html'">
								<spring:message code="BirthRegDto.back"/>
							</button>
					</div>
					</c:if>
					<!-- fetch data after search end -->
						
				</form:form>
			</div>
		</div>
	</div>
</div>
