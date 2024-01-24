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
<script type="text/javascript" src="js/birthAndDeath/cemeterymaster.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="CemeteryMasterDTO.form.name" text="Cemetery Master" /></strong>
			</h2>
		</div>
		<br>

		<div class="widget-content padding">

			<form:form action="CemeteryMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmCemeteryMaster" id="frmCemeteryMaster">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<%-- <div class="widget-header">
					<h2>
						<strong><spring:message
								code="CemeteryMasterDTO.form.detail" text="Cemetery Details" /></strong>
					</h2>
				</div> --%>

				<div class="form-group">
					<apptags:input labelCode="CemeteryMasterDTO.ceName"
						path="cemeteryMasterDTO.ceName" isMandatory="true"
						cssClass="hasCharacter form-control" maxlegnth="20"
						isReadonly="${command.saveMode eq 'V'}"></apptags:input>

                   
                   
                   
						<apptags:input labelCode="CemeteryMasterDTO.ceNameMar"
						path="cemeteryMasterDTO.ceNameMar" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="150"
						isReadonly="${command.saveMode eq 'V'}"></apptags:input>
		
				</div>



            <div class="form-group">
                  		<apptags:textArea labelCode="CemeteryMasterDTO.ceAddr"
						path="cemeteryMasterDTO.ceAddr" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="50"
						isReadonly="${command.saveMode eq 'V'}"></apptags:textArea>

					<apptags:textArea labelCode="CemeteryMasterDTO.ceAddrMar"
						path="cemeteryMasterDTO.ceAddrMar" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="350"
						isReadonly="${command.saveMode eq 'V'}"></apptags:textArea>
				</div>


				<div class="form-group">

					<label class="control-label col-sm-2 required-control" for="Census">
						<spring:message code="CemeteryMasterDTO.cecpdTypeId"
							text="Cemetery Type" />
					</label>
					<c:set var="baseLookupCode" value="CTY" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="cemeteryMasterDTO.cpdTypeId" cssClass="form-control"
						isMandatory="true" hasId="true"
						selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V' }" />


					<div class="form-group">
						<apptags:radio
							radioLabel="CemeteryMasterDTO.ceStatus.active,CemeteryMasterDTO.ceStatus.inactive"
							radioValue="A,I" labelCode="CemeteryMasterDTO.ceStatus"
							path="cemeteryMasterDTO.ceStatus"
							disabled="${command.saveMode eq 'V' }" defaultCheckedValue="A" />
					</div>

				</div>

				<div class="text-center">
				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save" text="Save"/>" class="btn btn-success btn-submit" id="Submit"
							onclick="confirmToProceed(this)">
							<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								onclick="openForm('CemeteryMaster.html','cemeteryMaster')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button></c:if>
					</c:if>
					
					<input type="button" value="<spring:message code="back.msg" text="Back"/>" class="btn btn-danger"
						onclick="window.location.href='CemeteryMaster.html'" id="backBtn">
				</div>
				</div>
				
			</form:form>

		</div>
	</div>
</div>





