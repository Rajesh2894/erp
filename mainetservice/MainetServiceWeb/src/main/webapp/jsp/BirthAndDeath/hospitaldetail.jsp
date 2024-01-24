<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/birthAndDeath/hospitalMaster.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="HospitalMasterDTO.form.name" text="Hospital Master" /></strong>
			</h2>
		</div>
		<br>
	


		<div class="widget-content padding">

			<form:form action="HospitalMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmHospitalMaster" id="frmHospitalMaster">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
			<%-- 	<div class="widget-header">
					<h2>
						<strong><spring:message
								code="HospitalMasterDTO.form.detail" text="Hospital Details" /></strong>
					</h2>
				</div> --%>

				<div class="form-group">
					<apptags:input labelCode="HospitalMasterDTO.hiName"
						path="hospitalMasterDTO.hiName" isMandatory="true"
						cssClass="hasCharacter form-control" maxlegnth="20"
						isReadonly="${command.saveMode eq 'V'}"></apptags:input>
						
						
						<apptags:input labelCode="HospitalMasterDTO.hiNameMar"
						path="hospitalMasterDTO.hiNameMar" isMandatory="true"
						cssClass="hasNameClass form-control" maxlegnth="150"
						isReadonly="${command.saveMode eq 'V'}"></apptags:input>
						
				</div>
				
				<div class="form-group">
				
						<apptags:textArea labelCode="HospitalMasterDTO.hiAddr"
						path="hospitalMasterDTO.hiAddr" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="50"
						isReadonly="${command.saveMode eq 'V'}"></apptags:textArea>

					<apptags:textArea labelCode="HospitalMasterDTO.hiAddrMar"
						path="hospitalMasterDTO.hiAddrMar" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="350"
						isReadonly="${command.saveMode eq 'V'}"></apptags:textArea>
				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control" for="Census">
						<spring:message code="HospitalMasterDTO.hicpdTypeId"
							text="Description" />
					</label>
					<c:set var="baseLookupCode" value="HTY" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="hospitalMasterDTO.cpdTypeId" cssClass="form-control"
						isMandatory="true" hasId="true"
						selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V'}" />

					<apptags:input labelCode="HospitalMasterDTO.hiCode" path="hospitalMasterDTO.hiCode"
						cssClass="form-control hasCharacter form-control mandColorClass" maxlegnth="3"
						isReadonly="${command.saveMode eq 'V'}">
					</apptags:input>
				</div>	

				<div class="form-group">
					<apptags:radio
						radioLabel="HospitalMasterDTO.hiStatus.active,HospitalMasterDTO.hiStatus.inactive"
						radioValue="A,I" labelCode="HospitalMasterDTO.hiStatus"
						path="hospitalMasterDTO.hiStatus"
						disabled="${command.saveMode eq 'V' }" defaultCheckedValue="A" />

				</div>

				<div class="text-center">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save"/>"
							onclick="confirmToProceed(this)" class="btn btn-success"
							id="Submit">
						<c:if test="${command.saveMode ne 'E'}">
						
						
					<button type="Reset" class="btn btn-warning"
								onclick="openForm('HospitalMaster.html','hospitalMaster')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button></c:if>
					</c:if>
					<!-- <input type="button"
						onclick="window.location.href='HospitalMaster.html'"
						class="btn btn-danger  hidden-print" value="Back"> -->
					<input type="button" value="<spring:message code="back.msg" text="Back"/>" class="btn btn-danger"
						onclick="window.location.href='HospitalMaster.html'" id="backBtn">	


				</div>

			</form:form>


		</div>
	</div>
</div>





