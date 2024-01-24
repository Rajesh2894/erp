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
		
	});

	$('[data-toggle="tooltip"]').tooltip({
	    trigger : 'hover'
	});
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Issuance of Death certificate form" code="Issuance.death.certificate.form"/>
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div> -->
			<apptags:helpDoc url="IssuanceDeathCertificate.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="frmIssuCertificateForm" commandName="command"
				action="IssuanceDeathCertificate.html" method="POST"
				class="form-horizontal" name="IssueCertiFormId">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<%-- <form:hidden code="" path="" value="${command.chargesAmount}"
						cssClass="hasNumber form-control" id="charge" /> --%>

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
												path="tbDeathregDTO.requestDTO.titleId"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<apptags:input labelCode="rti.firstName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="tbDeathregDTO.requestDTO.fName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">


											<apptags:input labelCode="rti.middleName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="tbDeathregDTO.requestDTO.mName" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

											<apptags:input labelCode="rti.lastName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="tbDeathregDTO.requestDTO.lName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>
										<div class="form-group">

											<label class="col-sm-2 control-label "><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="tbDeathregDTO.requestDTO.gender"
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
											cssClass="hasNumClass mandColorClass " path="tbDeathregDTO.requestDTO.bldgName"
											maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="rti.taluka"
											cssClass=" mandColorClass " path="tbDeathregDTO.requestDTO.blockName"
											 maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="applicant.roadName"
											cssClass="hasNumClass mandColorClass"
											path="tbDeathregDTO.requestDTO.roadName"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }">
										</apptags:input>
										
										<label class="control-label col-sm-2 "
											for="text-1"><spring:message
										code="tbDeathregDTO.applicant.wardName" text="Ward Name" /></label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.requestDTO.wardNo"
											cssClass="form-control" hasId="true"
											selectOptionLabelCode="selectdropdown" />
									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.Try5"
											cssClass="hasNameClass mandColorClass "
											path="tbDeathregDTO.requestDTO.cityName"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											isMandatory="true" maxlegnth="100"></apptags:input>
										<apptags:input labelCode="rti.pinCode"
											cssClass="hasPincode mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="tbDeathregDTO.requestDTO.pincodeNo" 
											maxlegnth="100"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.mobile1"
											cssClass="hasMobileNo mandColorClass "
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="tbDeathregDTO.requestDTO.mobileNo" isMandatory="true" maxlegnth="10"></apptags:input>
										<apptags:input labelCode="chn.lEmail"
											cssClass="hasemailclass  mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="tbDeathregDTO.requestDTO.email" maxlegnth="100"></apptags:input>
									</div>
								</div>



							</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#issueDeathCert-1">
										<spring:message text="Registration Detail" code="Issuaance.death.certificate.detail"/></a>
								</h4>
							</div>
							<div id="issueDeathCert-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
											path="tbDeathregDTO.drDeceasedname"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="tbDeathregDTO.drMarDeceasedname"
											path="tbDeathregDTO.drMarDeceasedname"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
			
									<div class="form-group">
									<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="TbDeathregDTO.drSex"
												text="Gender" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.drSex" cssClass="form-control"
											isMandatory="true" hasId="true"  disabled="true"
											selectOptionLabelCode="selectdropdown" />
									
										<apptags:date fieldclass="datepicker" labelCode="TbDeathregDTO.drDod"
											datePath="tbDeathregDTO.drDod" readonly="true" isDisabled="true">
										</apptags:date>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="TbDeathregDTO.parentDetailDTO.pdFathername"
											path="tbDeathregDTO.drRelativeName"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="TbDeathregDTO.parentDetailDTO.pdFathernameMar"
											path="tbDeathregDTO.drMarRelativeName"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="TbDeathregDTO.parentDetailDTO.pdMothername"
											path="tbDeathregDTO.drMotherName"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="TbDeathregDTO.parentDetailDTO.pdMothernameMar"
											path="tbDeathregDTO.drMarMotherName"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr"
											path="tbDeathregDTO.drDeceasedaddr"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr"
											path="tbDeathregDTO.drMarDeceasedaddr"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeathplace"
											path="tbDeathregDTO.drDeathplace"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
											path="tbDeathregDTO.drMarDeathplace"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeath"
											path="tbDeathregDTO.drDcaddrAtdeath"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeathMar"
											path="tbDeathregDTO.drDcaddrAtdeathMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#issueDeathCert-2">
										<spring:message text="Certificate print details"  code="Issuaance.death.certificate.print"/></a>
								</h4>
							</div>
							<div id="issueDeathCert-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.alreayIssuedCopy"
											path="tbDeathregDTO.alreayIssuedCopy"
											cssClass="hasNumClass form-control"
											isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.numberOfCopies"
											path="tbDeathregDTO.numberOfCopies" isDisabled="${command.saveMode eq 'V'}" maxlegnth="2"
											cssClass="hasNumber form-control" isMandatory="true" onBlur="getAmountOnNoOfCopes()">
										</apptags:input>
									</div>
									<c:if test="${command.tbDeathregDTO.chargeStatus eq 'CA'}">
									<div class="form-group" id="amountid">
										<apptags:input labelCode="TbDeathregDTO.amount"
											path="tbDeathregDTO.amount" cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									</c:if>
								</div>
							</div>
							<form:hidden path="tbDeathregDTO.chargeStatus" id="chargeStatus"/>
						<c:if test="${command.tbDeathregDTO.chargeStatus eq 'CA'}">
							<c:if test="${command.saveMode ne 'V'}">
							<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						   </div>
						   </c:if>
						   </c:if>
						</div>
					</div>
					<div class="text-center">
					<div class="text-center padding-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" align="center" class="btn btn-green-3" data-toggle="tooltip" data-original-title="Submit"
							onclick="saveDeathCertFormAndGenerateAppNo(this)">
							<spring:message code="BirthRegDto.submit" text="Submit" />
						</button>
						</c:if>
						<button type="button" align="center" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='IssuanceDeathCertificate.html'">
								<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
							</button>
					</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<!-- </div> -->






