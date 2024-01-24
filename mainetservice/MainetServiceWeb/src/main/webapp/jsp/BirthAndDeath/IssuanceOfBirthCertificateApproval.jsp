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
	src="js/birthAndDeath/IssuanceBirthCertificateApproval.js"></script>

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
	$("#brRegDate").keyup(function(e){
		debugger;
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });	
	}
</script>

<div class="pagediv">
	<!-- <div class="content animated top"> -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Issuance birth certificate Approval form" code="issuence.birth.approval"/>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form id="frmIssuanceBirthCertificateApproval"
				action="IssuanceBirthCertificateApproval.html" method="POST"
				class="form-horizontal" name="IssueCertiFormId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<%-- <h4>
					<spring:message text="Issuance birth certificate Approval"  code="Issuance.birth.certificate"/>
				</h4> --%>
<%-- 				<div class="form-group">
					<apptags:input labelCode="BirthRegDto.brCertNo"
						path="birthRegDto.brManualCertNo" cssClass="hasNumber form-control"  
						maxlegnth="12">
					</apptags:input>
				</div>
				<div class="form-group" align="center">OR</div>
				<div class="form-group">
					<apptags:input labelCode="BirthRegDto.applicationId"
						path="birthRegDto.applicationId"
						cssClass="hasNumber form-control" maxlegnth="20">
					</apptags:input>
				</div>
				<div class="form-group" align="center">OR</div>
				<div class="form-group">
					<apptags:input labelCode="BirthRegDto.year" path="birthRegDto.year"
						cssClass="hasNumber form-control" maxlegnth="4">
					</apptags:input>
					<apptags:input labelCode="BirthRegDto.brRegNo"
						path="birthRegDto.brRegNo" cssClass="hasNumber form-control"
						maxlegnth="10">
					</apptags:input>
				</div>
				<div class="text-center">
					<button type="button" class="btn btn-blue-3" title="Search"
						onclick="searchBirthData(this)">
						Search
					</button>
					<button type="button" class="btn btn-warning btn-yellow-2"
						title="Submit"
						onclick="window.location.href ='IssuanceBirthCertificate.html'">
						Reset
					</button>
				</div> --%>

				<div id="RegisDetail">
					<h4>
						<spring:message text="Issuaance birth certificate detail" code="Issuaance.birth.certificate.detail"/>
					</h4>

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
						<apptags:input labelCode="BirthRegDto.brSex"
							path="birthRegDto.brSex" cssClass="hasNameClass form-control"
							maxlegnth="12" isReadonly="true">
						</apptags:input>
						<apptags:date fieldclass="datepicker" labelCode="BirthRegDto.brDob"
							datePath="birthRegDto.brDob" readonly="true">
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
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegDto.brBirthAddrMar"
							path="birthRegDto.brBirthAddrMar"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr"
							path="birthRegDto.brBirthAddr"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar"
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

					<h4>
						<spring:message text="Issuaance birth certificate print" code="Issuaance.birth.certificate.print"/>
					</h4>

					<div class="form-group">
						<apptags:input labelCode="BirthRegDto.alreayIssuedCopy"
							path="birthRegDto.alreayIssuedCopy"                                  
							cssClass="hasNumClass form-control" maxlegnth="12"
							 isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegDto.numberOfCopies"
							path="birthRegDto.noOfCopies"
							cssClass="hasNumber form-control" maxlegnth="10"
							isMandatory="true" isReadonly="true">
						</apptags:input>
					</div>
					
                 <div class="form-group">
                   <apptags:radio radioLabel="nac.approve,nac.reject" radioValue="APPROVED,REJECTED" isMandatory="true"
				   labelCode="nac.status" path="birthRegDto.birthRegstatus" defaultCheckedValue="APPROVED" >
			    </apptags:radio>
			     <apptags:textArea
				labelCode="TbDeathregDTO.form.remark" 
				path="birthRegDto.authRemark" cssClass="hasNumClass form-control"
				maxlegnth="100" />
			    
			    
                  </div>
                  
                   <form:hidden path="birthRegDto.brId" id="brId"/>
					<div class="text-center">
						<button type="button" align="center" class="btn btn-green-3" title="Submit"
							onclick="saveIssuanceBirthCertificateApprovalData(this)">
							<spring:message code="BirthRegDto.submit" text="Submit" />
						</button>
						<apptags:backButton url="AdminHome.html" ></apptags:backButton>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<!-- </div> -->
</div>






