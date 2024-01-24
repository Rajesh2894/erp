<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/sfac/farmerMasterForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<style>
.stateDistBlock>label[for="frmSDB3"]+div {
	margin-top: 0.5rem;
	f
}

#uploadPreview ul li {
	display: inline-block !important;
}

#uploadPreview .img-thumbnail {
	display: block !important
}
.charCase {
	text-transform: uppercase;
}

</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.frm.formname"
					text="Farmer Master Details" />
			</h2>
			<apptags:helpDoc url="FarmerMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="farmerMasterForm" action="FarmerMasterForm.html"
				method="post" class="form-horizontal" name="farmerMasterForm">
				<input type="hidden" id="modeId" value="${viewMode}">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
                 <div id="uploadTagDiv">
				<div class="form-group">
				
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.frmphoto" text="Farmer Photo" /></label>
					<c:set var="count" value="0" scope="page"></c:set>
					<div class="col-sm-4">
						<apptags:formField fieldType="7"
							fieldPath="attachments[${count}].uploadedDocumentPath"
							currentCount="${count}" folderName="${count}"  isDisabled="${command.viewMode eq 'V'}"
							fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
							isMandatory="true" maxFileCount="WORKS_MANAGEMENT_MAXSIZE" callbackOtherTask="otherTask();"
							validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" cssClass="clear">
						</apptags:formField>
					</div>
              <c:if test="${command.downloadMode eq 'Y'}">
                 <label class="col-sm-2 control-label"><spring:message
							code="fac.download.photo" text="Uploaded Photo" /></label>
					<div id="uploadPreview" class="col-sm-2">
						<c:forEach items="${command.fileNames}" var="entry">
							<c:if test="${entry.key eq count}">
								<ul>
									<c:forEach items="${entry.value}" var="val">
										<li id=""><img src="${val}" width="100" height="100"
											class="img-thumbnail"><a href="${val}" download><i
												class="fa fa-download"></i></a></li>
									</c:forEach>
								</ul>
							</c:if>
						</c:forEach>
					</div></c:if>
				</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="sfac.FPO.regNo"
						cssClass="mandColorClass alphaNumeric"
						isDisabled="${command.viewMode eq 'V' ? true : false }"
						path="farmerMasterDto.frmFPORegNo" isMandatory="true"
						maxlegnth="21"></apptags:input>

				</div>

				<div class="form-group">
				<apptags:input labelCode="sfac.farmerName"
						cssClass="hasNameClass mandColorClass"
						isDisabled="${command.viewMode eq 'V' ? true : false }"
						path="farmerMasterDto.frmName" isMandatory="true" maxlegnth="20"></apptags:input>
						
						
						<apptags:input labelCode="sfac.farmer.fatherName"
						cssClass="hasNameClass mandColorClass"
						isDisabled="${command.viewMode eq 'V' ? true : false }"
						path="farmerMasterDto.frmFatherName" isMandatory="true"
						maxlegnth="20"></apptags:input>

				</div>

			

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Gender" /></label>
					<c:set var="baseLookupCode" value="GEN" />
					<apptags:lookupField items="${command.getLevelData('GEN')}"
						path="farmerMasterDto.frmGender"
						disabled="${command.viewMode eq 'V' ? true : false }"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="sfac.select" isMandatory="true" />
						
					<label class="col-sm-2 control-label required-control" for="">
						<spring:message code="sfac.dateOfBirth" text="Date Of Birth" />
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="farmerMasterDto.dateOfBirth" type="text"
								class="form-control datepicker mandColorClass"
								disabled="${command.viewMode eq 'V' ? true : false }"
								id="dateOfBirth" placeholder="dd/mm/yyyy" readonly="true" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>

				<div class="form-group">
				
				<label class="col-sm-2 control-label" for="">
						<spring:message code="sfac.farmerType" text="Farmer Type" />
					</label>
					<c:set var="baseLookupCode" value="FMT" />
					<apptags:lookupField
						disabled="${command.viewMode eq 'V' ? true : false }"
						items="${command.getLevelData(baseLookupCode)}"
						path="farmerMasterDto.frmType"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="sfac.select"/>
					
					<label class="col-sm-2 control-label required-control"
						for="Category"> <spring:message code="sfac.frm.category"
							text="Reservation" /></label>
					<c:set var="baseLookupCode" value="RVT" />
					<apptags:lookupField
						disabled="${command.viewMode eq 'V' ? true : false }"
						items="${command.getLevelData(baseLookupCode)}"
						path="farmerMasterDto.frmReservation"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="sfac.select" isMandatory="true" />
				</div>
				
					<div class="form-group">

					<apptags:input labelCode="sfac.address" cssClass="mandColorClass"
						isDisabled="${command.viewMode eq 'V' ? true : false }" isMandatory="true"
						path="farmerMasterDto.address" maxlegnth="300"></apptags:input>

					<apptags:input labelCode="sfac.pincode"
						cssClass="mandColorClass hasPincode" isMandatory="true"
						isDisabled="${command.viewMode eq 'V' ? true : false }"
						path="farmerMasterDto.pinCode" maxlegnth="6"></apptags:input>
				</div>

				<%-- 	<div class="form-group">
					<apptags:input labelCode="sfac.farmer.MotherName" cssClass="hasNameClass mandColorClass"
								path="farmerMasterDto.frmMotherName" isMandatory="true" maxlegnth="20" ></apptags:input>
				</div> --%>

				<div class="form-group stateDistBlock">
					<c:set var="baseLookupCode" value="SDB" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="SDB" hasId="true" disabled="${command.viewMode eq 'V' ? true : false }"
						pathPrefix="farmerMasterDto.frmSDB"
						hasLookupAlphaNumericSort="true" isMandatory="true"
						hasSubLookupAlphaNumericSort="true" showAll="false" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="sfac.frm.adharNo"
						cssClass="hasAadharNo mandColorClass"
						path="farmerMasterDto.frmAadharNo" maxlegnth="12"
						isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

					<apptags:input labelCode="sface.frm.rationCardNo"
						cssClass="mandColorClass" path="farmerMasterDto.frmRationCardNo"
						maxlegnth="12"
						isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="sface.frm.voterCardNo"
						cssClass="mandColorClass alphaNumeric charCase" path="farmerMasterDto.frmVoterCardNo"
						maxlegnth="10" placeholder="AAA1111111"
						isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

					<apptags:input labelCode="sfac.frm.mobileNo"
						cssClass="mandColorClass hasMobileNo"
						path="farmerMasterDto.frmMobNo" isMandatory="true" maxlegnth="10"
						isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
				</div>

				<div class="form-group">
					<%-- 		<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.frm.landDetails" text="Land Details" /></label>
						
						<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="farmerMasterDto.frmLandDet" cssClass="form-control text-right"
								id="frmLandDet"  />
							<div class='input-group-field'>
								<form:select path="farmerMasterDto.frmLandUnit" class="form-control" id="frmLandUnit">
									<form:option value="">
										<spring:message code="sfac.select" />
									</form:option>
									<c:forEach items="${command.getLevelData('LND')}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>
					</div> --%>

					<label class="col-sm-2 control-label required-control"
						for="frmEquityShare"><spring:message
							code="sfac.frm.equityShare" text="Equity Share" /></label>
					<div class="col-sm-4">
						<form:input path="farmerMasterDto.frmEquityShare"
							cssClass="form-control  text-right" id="frmEquityShare"
							onkeypress="return hasAmount(event, this, 13, 2)"
							disabled="${command.viewMode eq 'V' ? true : false }"
							onchange="getAmountFormatInDynamic((this),'frmEquityShare')" />
					</div>


					<label class="col-sm-2 control-label required-control"
						for="frmEquityShare"><spring:message
							code="sfac.frm.equityAmt" text="Equity Amount" /></label>
					<div class="col-sm-4">
						<form:input path="farmerMasterDto.frmTotalEquity"
							cssClass="form-control  text-right" id="frmTotalEquity"
							onkeypress="return hasAmount(event, this, 13, 2)"
							disabled="${command.viewMode eq 'V' ? true : false }"
							onchange="getAmountFormatInDynamic((this),'frmTotalEquity')" />
					</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="sface.frm.landOwned"
						cssClass="mandColorClass hasNumber" isMandatory="true"
						path="farmerMasterDto.landOwned" maxlegnth="5"
						isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

					<apptags:input labelCode="sfac.frm.landLeased"
						cssClass="mandColorClass hasNumber"
						path="farmerMasterDto.landLeased" isMandatory="true" maxlegnth="5"
						isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" align="center" class="btn btn-green-3"
							data-toggle="tooltip" data-original-title="Submit"
							onclick="saveFarmerMasterForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="Reset" onclick="ResetForm()">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" align="center" class="btn btn-danger"
						data-toggle="tooltip" data-original-title="Back"
						onclick="window.location.href ='FarmerMasterForm.html'">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>


			</form:form>
		</div>
	</div>
</div>
