<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/birthAndDeath/IssuenceBirthCertificate.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maate : '-0d',
			changeYear : true,
		});
	});

	$(document).ready(function() {
		prepareDateTag();
	$("#frmIssuCertificateForm").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });	
	});

	$('[data-toggle="tooltip"]').tooltip({
	    trigger : 'hover'
	});
</script>

<div class="pagediv">
	<!-- <div class="content animated top"> -->
	<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="" code="Issuance.birth.certificate.form"/>
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div> -->
			<apptags:helpDoc url="IssuanceBirthCertificate.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="frmIssuCertificateForm" commandName="command"
				action="IssuanceBirthCertificate.html" method="POST"
				class="form-horizontal" name="IssueCertiFormId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
	
				<div id="RegisDetail">
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
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
											cssClass=" mandColorClass " path="birthRegDto.requestDTO.blockName"
											 maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="applicant.roadName"
											cssClass="hasNumClass mandColorClass"
											path="birthRegDto.requestDTO.roadName"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }">
										</apptags:input>
										
										<label class="control-label col-sm-2 "
											for="text-1"><spring:message
										code="tbDeathregDTO.applicant.wardName" text="Ward Name" /></label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.requestDTO.wardNo"
											cssClass="form-control" hasId="true"
											selectOptionLabelCode="selectdropdown" />
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
											maxlegnth="100"></apptags:input>
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
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#issueBirthCert-1">
										<spring:message text="Issuaance birth certificate detail" code="Issuaance.birth.certificate.detail"/></a>
								</h4>
							</div>
							<div id="issueBirthCert-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brChildName"
											path="birthRegDto.brChildName"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.brChildNameMar"
											path="birthRegDto.brChildNameMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
			
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegistrationDTO.brSex" text="Sex" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.brSex" cssClass="form-control"
											isMandatory="true" hasId="true"  disabled="true"
											selectOptionLabelCode="selectdropdown" />
										<apptags:date fieldclass="datepicker" labelCode="BirthRegDto.brDob"
											datePath="birthRegDto.brDob" isDisabled="true" readonly="true">
										</apptags:date>
										<!--  cssClass="custDate mandColorClass date" -->
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdFathername"
											path="birthRegDto.parentDetailDTO.pdFathername"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdFathernameMar"
											path="birthRegDto.parentDetailDTO.pdFathernameMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdMothername"
											path="birthRegDto.parentDetailDTO.pdMothername"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdMothernameMar"
											path="birthRegDto.parentDetailDTO.pdMothernameMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brBirthPlace"
											path="birthRegDto.brBirthPlace"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.brBirthPlaceMar"
											path="birthRegDto.brBirthPlaceMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brBirthAddr"
											path="birthRegDto.brBirthAddr"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.brBirthAddrMar"
											path="birthRegDto.brBirthAddrMar"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="ParentDetailDTO.permanent.parent.addr"
											path="birthRegDto.brBirthAddr"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="ParentDetailDTO.permanent.parent.addrMar"
											path="birthRegDto.brBirthAddrMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.BrParAddr.childBirthEng"
											path="birthRegDto.parentDetailDTO.pdParaddress"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.BrParAddr.childBirthReg"
											path="birthRegDto.parentDetailDTO.pdParaddressMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#issueBirthCert-2">
										<spring:message text="Issuaance birth certificate print" code="Issuaance.birth.certificate.print"/></a>
								</h4>
							</div>
							<div id="issueBirthCert-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.alreayIssuedCopy"
											path="birthRegDto.alreayIssuedCopy"                                 
											cssClass="hasNumClass form-control" maxlegnth="12"
											 isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.numberOfCopies"
											path="birthRegDto.noOfCopies"  isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumber form-control" maxlegnth="2"
											isMandatory="true" onBlur="getAmountOnNoOfCopes()">
										</apptags:input>
									</div>
									<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
									<div class="form-group" id="amountid">
										<apptags:input labelCode="BirthRegDto.amount"
											path="birthRegDto.amount" cssClass="hasNumClass form-control"
											maxlegnth="12"  isReadonly="true">
										</apptags:input>
									</div>
									</c:if>
								</div>
							</div>
							<form:hidden path="birthRegDto.chargesStatus" id="chargeStatus"/>
						<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
							<c:if test="${command.saveMode ne 'V'}">
							<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						    </div>
						    </c:if>
						    </c:if>
						</div>
					</div>

					<form:hidden path="birthRegDto.brId" id="brId"/>
					<div class="text-center">
					<div class="text-center padding-top-10" align="center">
						<c:if test="${command.saveMode ne 'V'}">
						<button type="button" align="center" class="btn btn-green-3" data-toggle="tooltip" data-original-title="Submit"
							onclick="saveBirthCertFormAndGenerateAppNo(this)">
							<spring:message code="BirthRegDto.submit"/>
						</button>
						</c:if>
						<button type="button" align="center" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='IssuanceBirthCertificate.html'">
								<spring:message code="BirthRegDto.back"/>
							</button> 
						
					</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	</div>
	<!-- </div> -->
</div>






