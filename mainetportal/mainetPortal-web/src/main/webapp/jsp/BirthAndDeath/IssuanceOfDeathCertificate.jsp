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
		$("#frmIssuCertificateForm").validate({
			onkeyup : function(element) {
				this.element(element);
				console.log('onkeyup fired');
			},
			onfocusout : function(element) {
				this.element(element);
				console.log('onfocusout fired');
			}
		});
	});

	$('[data-toggle="tooltip"]').tooltip({
	    trigger : 'hover'
	});
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Issuance of Death certificate form" code="Issuance.death.certificate"/>
			</h2>
			<apptags:helpDoc url="IssuanceDeathCertificate.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="frmIssuCertificateForm" commandName="command"
				action="IssuanceDeathCertificate.html" method="POST"
				class="form-horizontal" name="IssueCertiFormId">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>

				<div id="RegisDetail">
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#issueDeathCert-1" tabindex="-1">
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
									
										<%-- <apptags:date fieldclass="datepicker" labelCode="TbDeathregDTO.drDod"
											datePath="tbDeathregDTO.drDod" readonly="true" >
										</apptags:date> --%>

										<label class="col-sm-2 control-label"> <spring:message
												code="TbDeathregDTO.drDod" text="Deceased date" />
										</label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="tbDeathregDTO.drDod"
													cssClass="form-control mandColorClass" id="drDod"
													disabled="true"/>
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
											</div>
										</div>
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
									<a data-toggle="collapse" class="collapsed" href="#issueDeathCert-2" tabindex="-1">
										<spring:message text="Certificate print details"  code="Issuaance.death.certificate.print"/></a>
								</h4>
							</div>
							<div id="issueDeathCert-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
									<c:if test="${command.saveMode ne 'V'}">
										<apptags:input labelCode="TbDeathregDTO.alreayIssuedCopy"
											path="tbDeathregDTO.alreayIssuedCopy"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
										</c:if>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="TbDeathregDTO.numberOfCopies" text="Number of Copies" /></label>
										<div class="col-sm-4">
											<form:input class="form-control hasNumber"
												path="tbDeathregDTO.numberOfCopies" id="numberOfCopies" disabled="${command.saveMode eq 'V'}"
												onblur="getAmountOnNoOfCopes()" value="1" maxlength="2"></form:input>
										</div>
									</div>
									<c:if test="${command.tbDeathregDTO.chargeStatus eq 'CA'}">
									<div class="form-group" id="amountid">
										<apptags:input labelCode="TbDeathregDTO.amount"
											path="tbDeathregDTO.amount" cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									</c:if>

									<div class="form-group">
										<c:set var="baseLookupCode" value="BDZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="BDZ"
											hasId="true" pathPrefix="tbDeathregDTO.bndDw"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />
									</div>
								</div>
							</div>
							<form:hidden path="tbDeathregDTO.chargeStatus" id="chargeStatus"/>
						<c:if test="${command.tbDeathregDTO.chargeStatus eq 'CA'}">
							<c:if test="${command.saveMode ne 'V'}">
							<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						</div>
						   </c:if>
						   </c:if>
						</div>
					</div>
					<div class="text-center padding-top-10">
						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" class="btn btn-success"
								data-toggle="tooltip" data-original-title="Submit"
								onclick="saveDeathCertFormAndGenerateAppNo(this)">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="BirthRegDto.submit" text="Submit" />
							</button>
                        </c:if>
						<c:if test="${command.viewMode ne 'V'}">
							<button type="button" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='IssuanceDeathCertificate.html'">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
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
</div>
<!-- </div> -->






