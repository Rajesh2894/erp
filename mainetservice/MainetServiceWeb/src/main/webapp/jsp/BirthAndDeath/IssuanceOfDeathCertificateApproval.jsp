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
<script type="text/javascript"
	src="js/birthAndDeath/IssuanceDeathCertificateApproval.js"></script>
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
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Issuance of Death certificate approve form" code="Issuance.death.certificate.form"/>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form id="frmIssuanceDeathCertificateApproval"
				action="IssuanceDeathCertificateApproval.html" method="POST"
				class="form-horizontal" name="IssueCertiFormId">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<h4>
					<spring:message text="Issue Death certificate " code="Issuance.death.certificate" />
				</h4>

				<%-- <div class="form-group">
					<apptags:input labelCode="TbDeathregDTO.drCertNo"
						path="tbDeathregDTO.drManualCertno" cssClass="hasNumber form-control">
					</apptags:input>
				</div>
				<div class="form-group" align="center">OR</div>
				<div class="form-group">
					<apptags:input labelCode="TbDeathregDTO.applicationNo"
						path="tbDeathregDTO.applicationNo"
						cssClass="hasNumber form-control">
					</apptags:input>
				</div>
				<div class="form-group" align="center">OR</div>
				<div class="form-group">
					<apptags:input labelCode="TbDeathregDTO.year"
						path="tbDeathregDTO.year" cssClass="hasNumber form-control"
						maxlegnth="4">
					</apptags:input>
					<apptags:input labelCode="TbDeathregDTO.drRegno"
						path="tbDeathregDTO.drRegno" cssClass="hasNumber form-control">
					</apptags:input>
				</div>
				<div class="text-center">
					<button type="button" class="btn btn-blue-3" title="Search"
						onclick="searchDeathData(this)">
					</button>
					<button type="button" class="btn btn-warning btn-yellow-2"
						title="Submit"
						onclick="window.location.href ='IssuanceDeathCertificate.html'">
						Reset
					</button>
				</div> --%>

				<div id="RegisDetail">
					<h4>
						<spring:message text="Registration Detail" code="Issuaance.death.certificate.detail"/>
					</h4>

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
						<apptags:input labelCode="TbDeathregDTO.drSex"
							path="tbDeathregDTO.drSex" cssClass="hasNameClass form-control"
							isReadonly="true">
						</apptags:input>
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


					<h4>
						<spring:message text="Certificate print details"  code="Issuaance.death.certificate.print"/>
					</h4>

					<div class="form-group">
						 <apptags:input labelCode="TbDeathregDTO.alreayIssuedCopy"
							path="tbDeathregDTO.alreayIssuedCopy"
							cssClass="hasNumClass form-control" isMandatory="true"
							isReadonly="true">
						 </apptags:input>
						 <apptags:input labelCode="TbDeathregDTO.numberOfCopies"
							path="tbDeathregDTO.numberOfCopies" 
							cssClass="hasNumber form-control" isMandatory="true" isReadonly="true">
						</apptags:input>
					</div>
				
			 <div class="form-group">
                   <apptags:radio radioLabel="nac.approve,nac.reject" radioValue="APPROVED,REJECTED" isMandatory="true"
				   labelCode="nac.status" path="tbDeathregDTO.DeathRegstatus" defaultCheckedValue="APPROVED" >
			    </apptags:radio>
			     <apptags:textArea
				labelCode="TbDeathregDTO.form.remark" 
				path="tbDeathregDTO.authRemark" cssClass="hasNumClass form-control"
				maxlegnth="100" />
	        </div>
				
				

					<div class="text-center" align="center">
						<button type="button" align="center" class="btn btn-green-3" title="submit"
							onclick="saveIssuanceDeathCertificateApprovalData(this)">
							<spring:message code="BirthRegDto.submit" text="Submit" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<!-- </div> -->






