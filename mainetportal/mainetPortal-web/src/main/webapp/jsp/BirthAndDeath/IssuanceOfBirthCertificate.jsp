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
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="" code="Issuance.birth.certificate"/>
			</h2>
		<apptags:helpDoc url="IssuanceBirthCertificate.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="frmIssuCertificateForm" commandName="command"
				action="IssuanceBirthCertificate.html" method="POST"
				class="form-horizontal" name="IssueCertiFormId">
				
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>
	
				<div id="RegisDetail">
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#issueBirthCert-1" tabindex="-1">
										<spring:message text="Issuaance birth certificate detail" code="Issuaance.birth.certificate.detail" /></a>
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
										<%-- <apptags:date fieldclass="datepicker" labelCode="BirthRegDto.brDob"
											datePath="birthRegDto.brDob" readonly="true">
										</apptags:date> --%>
										
										<label class="col-sm-2 control-label"> <spring:message
												code="BirthRegistrationDTO.brDob" text="Date of Birth" />
										</label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="birthRegDto.brDateOfBirth" 
													cssClass="form-control mandColorClass datepicker" id="brDob"
													 disabled="true"/>
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
											</div>
										</div>
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
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#issueBirthCert-2" tabindex="-1">
										<spring:message text="Issuaance birth certificate print" code="Issuaance.birth.certificate.print"/></a>
								</h4>
							</div>
							<div id="issueBirthCert-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
									<c:if test="${command.saveMode ne 'V'}">
										<apptags:input labelCode="BirthRegDto.alreayIssuedCopy"
											path="birthRegDto.alreayIssuedCopy"                                 
											cssClass="hasNumClass form-control" maxlegnth="12"
											 isReadonly="true">
										</apptags:input>
										</c:if>
										<label class="col-sm-2 control-label required-control"><spring:message
												code="BirthRegDto.numberOfCopies" text="Number of Copies" /></label>
										<div class="col-sm-4">
											<form:input class="form-control hasNumber"
												path="birthRegDto.noOfCopies" id="noOfCopies" disabled="${command.saveMode eq 'V'}"
												onblur="getAmountOnNoOfCopes()" value="1" maxlength="2"></form:input>
										</div>
									</div>
									<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
									<div class="form-group" id="amountid">
										<apptags:input labelCode="BirthRegDto.amount"
											path="birthRegDto.amount" cssClass="hasNumClass form-control"
											maxlegnth="12"  isReadonly="true">
										</apptags:input>
									</div>
									</c:if>

										<div class="form-group">
										<c:set var="baseLookupCode" value="BDZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="BDZ"
											hasId="true" pathPrefix="birthRegDto.bndDw"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />
									</div>
								</div>
							</div>
							<form:hidden path="birthRegDto.chargesStatus" id="chargeStatus"/>
						<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
							<c:if test="${command.saveMode ne 'V'}">
							<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						   </div>
						    </c:if>
						    </c:if>
						</div>
					</div>

					<form:hidden path="birthRegDto.brId" id="brId"/>
					<div class="text-center padding-top-10">
						<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success" data-toggle="tooltip" data-original-title="Submit"
							onclick="saveBirthCertFormAndGenerateAppNo(this)">
							<i class="fa padding-left-4" aria-hidden="true"></i>
							<spring:message code="BirthRegDto.submit"/>
						</button>
						</c:if>
						<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='IssuanceBirthCertificate.html'">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="BirthRegDto.back"/>
							</button>
							</c:if>
							
							<c:if test="${command.viewMode eq 'V'}">
							<div class="text-center padding-top-10">
								<apptags:backButton url="CitizenHome.html"></apptags:backButton>
							</div>
						</c:if> 
						
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<!-- </div> -->
</div>






