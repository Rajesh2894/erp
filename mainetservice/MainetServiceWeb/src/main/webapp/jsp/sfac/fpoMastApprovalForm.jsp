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
<script type="text/javascript" src="js/sfac/fpoMasterForm.js"></script>

<style>
table.crop-details-table tbody tr td>input[type="checkbox"] {
	margin: 0.5rem 0 0 -0.5rem;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

#udyogAadharApplicable, #isWomenCentric, #approved {
	margin: 0.6rem 0 0 0;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>


<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.fpo.formname" text="FPO Master Details" />
			</h2>
			<apptags:helpDoc url="FpoMastApprovalController.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="fpoMasterApprovalForm"
				action="FpoMastApprovalController.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<h4>
					<spring:message code="sfac.fpo.cbbo.details" text="CBBO Details" />
				</h4>
				<div class="form-group">
					<apptags:input labelCode="sfac.IA.name" isReadonly="true"
						cssClass="mandColorClass hasNameClass" path="dto.iaName"
						isMandatory="true" maxlegnth="100"></apptags:input>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.cbbo.ia.allocation.yr" text="IA EmpanelMent Year" /></label>
					<div class="col-sm-4">
						<form:select path="dto.iaAlcYear" id="iaAlcYear" disabled="true"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="sfac.fpo.cbbo.name" isReadonly="true"
						cssClass="mandColorClass hasNameClass" path="dto.cbboName"
						isMandatory="true" maxlegnth="100"></apptags:input>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.cbbo.allcyr" text="CBBO Allocation Year" /></label>
					<div class="col-sm-4">
						<form:select path="dto.cbboAlcYear" id="cbboAlcYear"
							disabled="true" cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<h4>
					<spring:message code="sfac.fpo.fpoDetails" text="FPO Details" />
				</h4>

				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="sfac.fpo.dmc.approval.status" text="DMC Approval Status" />
							<span class="mand">*</span></label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="APS" />
							<form:select path="dto.dmcApproval" id="dmcApproval"
								onchange="getAppPendingField();"
								cssClass="form-control chosen-select-no-results" disabled="true">
								<form:option value="0">
									<spring:message text="Select" code="sfac.select" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>

						<label class="col-sm-2 control-label hideDate"><spring:message
								code="sfac.approvalPending.date" text="Approval Pending Since" /><span
							class="showMand"><i class="text-red-1">*</i></span></label>
						<div class="col-sm-4 hideDate">
							<div class="input-group ">
								<form:input path="dto.appPendingDate" type="text"
									class="form-control datepicker mandColorClass appPendingDate"
									id="appPendingDate" placeholder="dd/mm/yyyy" readonly="true" />
								<span class="input-group-addon "><i
									class="fa fa-calendar "></i></span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<apptags:input labelCode="sfac.fpo.fpoName" isDisabled="true"
							cssClass="mandColorClass hasNameClass " path="dto.fpoName"
							isMandatory="true" maxlegnth="100"></apptags:input>
						<apptags:input labelCode="sfac.fpo.cmpnyFpoRegNo"
							isDisabled="true" cssClass="mandColorClass alpaSpecial charCase"
							path="dto.companyRegNo" isMandatory="true" maxlegnth="21"></apptags:input>
					</div>
					<div class="form-group">

						<label class="col-sm-2 control-label required-control" for="">
							<spring:message code="sfac.fpo.fpoRegistrationDate"
								text="FPO Registration Date" />
						</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="dto.dateIncorporation" type="text"
									class="form-control datepicker mandColorClass" disabled="true"
									id="dateIncorporation" placeholder="dd/mm/yyyy"
									onchange="getAgeOfFPO(this);" readonly="true" />
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<apptags:input labelCode="sfac.fpo.fpoAge" isDisabled="true"
							cssClass="mandColorClass" path="dto.fpoAge" isMandatory="true"
							isReadonly="true"></apptags:input>

					</div>

					<div class="form-group">
						<apptags:input labelCode="sfac.fpo.fpoTAN" isDisabled="true"
							cssClass="mandColorClass alphaNumeric charCase"
							path="dto.fpoTanNo" isMandatory="true" maxlegnth="10"
							placeholder="ABCD12345X"></apptags:input>
						<apptags:input labelCode="sfac.fpo.fpoPAN" isDisabled="true"
							cssClass="mandColorClass alphaNumeric charCase"
							path="dto.fpoPanNo" isMandatory="true" maxlegnth="10"
							placeholder="BLUPS4233S"></apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="sfac.fpo.noShareHolder"
							isDisabled="true" cssClass="mandColorClass hasNumber"
							path="dto.noShareMem" isMandatory="true"></apptags:input>

						<label class="col-sm-2 control-label"><spring:message
								code="sfac.fpo.northEastRegion" text="North East Region" /></label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="YNC" />
							<form:select path="dto.northEastRegion" class="form-control"
								id="NorthEastRegion" disabled="true">
								<form:option value="0">
									<spring:message code="sfac.select" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>


					</div>

					<div class="form-group">
						<apptags:input labelCode="sfac.fpo.authorizeCapital"
							isDisabled="true" cssClass="mandColorClass hasNumber"
							path="dto.authorizeCapital" isMandatory="true"></apptags:input>

						<apptags:input labelCode="sfac.fpo.paidUpCapitalROC"
							isDisabled="true" cssClass="mandColorClass hasNumber"
							path="dto.paidupCapital" isMandatory="true"></apptags:input>

					</div>



					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="regAct"> <spring:message
								code="sfac.fpo.baseLineSurvey" text="Base Line Survey" />
						</label>
						<c:set var="baseLookupCode" value="BLS" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="dto.baseLineSurvey" disabled="true"
							cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="sfac.select" isMandatory="true" />

						<apptags:input labelCode="sfac.fpo.gstin" maxlegnth="15"
							isDisabled="true" cssClass="mandColorClass alphaNumeric charCase"
							path="dto.gstin" placeholder="11AAAAA1111Z1A1"></apptags:input>

					</div>


					<div class="form-group stateDistBlock">
						<c:set var="baseLookupCode" value="SDB" />
						<apptags:lookupFieldSet disabled="true"
							cssClass="form-control required-control" baseLookupCode="SDB"
							hasId="true" pathPrefix="dto.sdb"
							hasLookupAlphaNumericSort="true" isMandatory="true"
							hasSubLookupAlphaNumericSort="true" showAll="false" />
					</div>

					<div class="form-group">

						<label class="col-sm-2 control-label  required-control"><spring:message
								code="sfac.allocation.category" text="Allocation Category" /></label>
						<div class="col-sm-4">
							<form:select path="dto.allocationCategory" disabled="true" class="form-control"
								id="allocationCategory">
								<form:option value="0">
									<spring:message code="sfac.select" text="Select" />
								</form:option>
								<c:forEach items="${command.allocationCatgList}" var="lookUp">
									<form:option code="${lookUp.lookUpCode}"
										value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>


						<label class="col-sm-2 control-label  required-control"><spring:message
								code="sfac.allocation.subcategory" text="Allocation SubCategory" /></label>
						<div class="col-sm-4">
							<form:select path="dto.allocationSubCategory" disabled="true" class="form-control"
								id="allocationSubCategory">
								<form:option value="0">
									<spring:message code="sfac.select" text="Select" />
								</form:option>
								<c:forEach items="${command.allocationSubCatgList}" var="lookUp">
									<form:option code="${lookUp.lookUpCode}"
										value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>

					</div>



					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="regAct"> <spring:message
								code="sfac.fpo.fpoRegistrationAct" text="FPO Registration Act" />
						</label>
						<c:set var="baseLookupCode" value="FRA" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" path="dto.regAct"
							disabled="true" cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="sfac.select" isMandatory="true" />

						<label class="col-sm-2 control-label required-control"
							for="regAct"> <spring:message
								code="sfac.fpo.typeOfPromotionAge"
								text="Type of Promotion Agency" />
						</label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="PAT" />
							<form:select path="dto.typeofPromAgen" class="form-control"
								id="typeofPromAgen" disabled="true">
								<form:option value="">
									<spring:message code="sfac.select" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="sfac.state.category" text="Area type" /></label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass"
								id="stateCategory" path="dto.statecategory" readonly="true"></form:input>
						</div>

						<label class="col-sm-2 control-label required-control"><spring:message
								code="sfac.region" text="Zone" /></label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass" id="region"
								path="dto.region" readonly="true"></form:input>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="sfac.aspirational.district" text="Aspirational District" />
							<span class="mand">*</span></label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass"
								id="isAspirationalDist" path="dto.isAspirationalDist"
								readonly="true"></form:input>
						</div>

						<label class="col-sm-2 control-label"> <spring:message
								code="sfac.tribal.district" text="Tribal District" /> <span
							class="mand">*</span></label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass" id="isTribalDist"
								path="dto.isTribalDist" readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="sfac.Odop" text="ODOP" /> <span class="mand">*</span></label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass" id="odop"
								path="dto.odop" readonly="true"></form:input>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="sfac.fpo.womenCentricFPO" text="Is it Women centric FPO" /></label>
						<div class="col-sm-4 height-2rem">
							<form:checkbox id="isWomenCentric" path="dto.isWomenCentric" disabled="true"
								value="" checked="${command.dto.isWomenCentric eq 'Y'? 'checked' : '' }" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="sfac.fpo.registeredOnEnam" text="Registered On E-NAM" /></label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="YNC" />
							<form:select path="dto.registeredOnEnam" class="form-control"
								id="registeredOnEnam" disabled="true">
								<form:option value="0">
									<spring:message code="sfac.select" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<apptags:input labelCode="sfac.fpo.userIdEnam" isDisabled="true"
							cssClass="mandColorClass " path="dto.userIdEnam" maxlegnth="50"></apptags:input>
					</div>


					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="fpoCaAddress"> <spring:message
								code="sfac.fpo.office.address" text="FPO Regd Office Address" />
						</label>
						<div class="col-sm-4">
							<form:input path="dto.officeAddress" maxlength="200"
								disabled="true" class="form-control mandColorClass alphaNumeric"
								id="officeAddress"></form:input>
						</div>
						<label class="col-sm-2 control-label" for="officeVillageName">
							<spring:message code="sfac.fpo.office.village.name"
								text="FPO Office Village Name" />
						</label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass alphaNumeric"
								maxlength="100" path="dto.officeVillageName" disabled="true"
								id="officeVillageName"></form:input>
						</div>
					</div>



					<div class="form-group">
						<label class="col-sm-2 control-label" for="fpoPostOffice">
							<spring:message code="sfac.fpo.post.office"
								text="FPO Post Office" />
						</label>
						<div class="col-sm-4">
							<form:input path="dto.fpoPostOffice" maxlength="100"
								disabled="true" class="form-control mandColorClass alphaNumeric"
								id="fpoPostOffice"></form:input>
						</div>
						<label class="col-sm-2 control-label required-control"
							for="officePinCode"> <spring:message
								code="sfac.fpo.office.pin.code" text="FPO Office PIN Code" />
						</label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass hasPincode"
								disabled="true" path="dto.officePinCode" id="officePinCode"
								maxlength="6"></form:input>
						</div>

					</div>


					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="sfac.Udyog.aadhar.appl" text="Udyog Aadhar Applicable?" /></label>
						<div class="col-sm-4 height-2rem">
							<form:checkbox id="udyogAadharApplicable" disabled="true"
								path="dto.udyogAadharApplicable" value="" checked="${command.dto.udyogAadharApplicable eq 'Y'? 'checked' : '' }"  />
						</div>
					</div>
					<c:if test="${command.showUdyogDet eq 'Y'}">
						<div class="form-group">
							<label class="col-sm-2 control-label" for=udyogAadharNo>
								<spring:message code="sfac.Udyog.aadhar.no"
									text="Udyog Aaddhar No" /><span class="udyogMand"><i
									class="text-red-1">*</i></span>
							</label>
							<div class="col-sm-4">
								<form:input path="dto.udyogAadharNo" disabled="true"
									class="form-control mandColorClass hasNumber"
									id="udyogAadharNo"></form:input>
							</div>
							<label class="col-sm-2 control-label" for="udyogAadharDate">
								<spring:message code="sfac.Udyog.aadhar.date"
									text="Udyog Aaddhar Date" /><span class="udyogMand"><i
									class="text-red-1">*</i>
							</label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="dto.udyogAadharDate" type="text"
										disabled="true"
										class="form-control udyogAadharDatePicker mandColorClass"
										id="udyogAadharDate" placeholder="dd/mm/yyyy" />
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>

						</div>
					</c:if>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="" text="Acknowledgement Number" /></label>
						<div class="col-sm-4">
							<form:input class="form-control mandColorClass hasNumber"
								disabled="true"
								path="dto.acknowledgementNumber" id="acknowledgementNumber"
								maxlength="15"></form:input>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="sfac.status" text="Status" /></label>
						<div class="col-sm-4">
							<form:select path="dto.activeInactiveStatus"
								class="form-control chosen-select-no-results"
								id="activeInactiveStatus" disabled="true"
								data-rule-required="true">
								<form:option value="0"><spring:message code="sfac.select" text="Select" />
								</form:option>
								<form:option value="A"><spring:message code="sfac.active" text="Active" />
								</form:option>
								<form:option value="I"><spring:message code="sfac.inactive" text="InActive" />
								</form:option>
							</form:select>
						</div>
					</div>
				</div>

			 	<h4>
					<spring:message code="sfac.fpo.cropDetails" text="Crop Details" />
				</h4>

				<div class="form-group padding-top-15">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.odop.Cultivation" text="ODOP Cultivation" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="YNC" />
						<form:select path="dto.odopCultivation" class="form-control"
							id="odopCultivation"
							disabled="true">
							<form:option value="0">
								<spring:message code="sfac.select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=udyogAadharNo> <spring:message
							code="sfac.totalAreaCovKharif" text="Total Area Covered(Kharif)" />
					</label>
					<div class="col-sm-4">
						<form:input path="dto.totalAreaCovKharif" disabled="true"
							class="form-control mandColorClass hasNumber"
							id="totalAreaCovKharif"></form:input>
					</div>

					<label class="col-sm-2 control-label" for=udyogAadharNo> <spring:message
							code="sfac.totalAreaCovRabi" text="Total Area Covered(Rabi)" />
					</label>
					<div class="col-sm-4">
						<form:input path="dto.totalAreaCovRabi" disabled="true"
							class="form-control mandColorClass hasNumber"
							id="totalAreaCovRabi"></form:input>
					</div>
				</div>
                <c:if test="${not empty command.dto.fpoMasterDetailDto}">
				<div class="panel-body">
					<div class="table-responsive">
						<table
							class="table table-bordered table-striped crop-details-table"
							id="cropDetailsTable">
							<thead>
								<tr>
									<th width="8%"><spring:message code="sfac.srno"
											text="Sr. No." /></th>
									<th width="12%"><spring:message code="sfac.fpo.cropSeason"
											text="Crop Season" /></th>
									<th width="12%"><spring:message code="sfac.fpo.cropType"
											text="Crop Type" /></th>
									<th width="15%"><spring:message code="sfac.fpo.cropName"
											text="Crop Name" /></th>
									<th width="12%"><spring:message
											code="sfac.fpo.primarySecondary.crop"
											text="Primary/Secondary Crop" /></th>
									<th width="12%"><spring:message
											code="sfac.crop.approved.by.dmc"
											text="Approved By DMC/Interception Crop" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.dto.fpoMasterDetailDto}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.cropSeasonDesc}</td>
										<td class="text-center">${dto.cropTypeDesc}</td>
										<td class="text-center">${dto.cropName}</td>
										<td class="text-center">${dto.priSecCropDesc}</td>
										<td class="text-center">${dto.approvedByDmcDesc}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				</c:if>
               <c:if test="${not empty command.dto.fpoBankDetailDto}">
			    <h4>
					<spring:message code="sfac.fpo.bankDetails" text="Bank Details" />
				</h4>

				<div class="panel-body">
					<div class="table-responsive">
						<table
							class="table table-bordered table-striped contact-details-table"
							id="bankDetailsTable">
							<thead>
								<tr>
									<th width="8%"><spring:message code="sfac.srno"
											text="Sr. No." /></th>
									<th><spring:message code="sfac.fpo.bankName"
											text="Bank Name" /></th>
									<th><spring:message code="sfac.fpo.ifscCode"
											text="IFSC Code" /></th>
									<th><spring:message code="sfac.fpo.branchName"
											text="Branch Name" /></th>
									<th><spring:message code="sfac.fpo.accountNo"
											text="Account No." /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.dto.fpoBankDetailDto}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.bankName}</td>
										<td class="text-center">${dto.ifscCode}</td>
										<td class="text-center">${dto.branchName}</td>
										<td class="text-center">${dto.accountNo}</td>
									</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</div>
				</div>
                   </c:if>
                    
                 <c:if test="${not empty command.dto.fpoAdministrativeDto}">
				<h4>
					<spring:message code="sfac.fpo.administrativeDetails" text="Administrative Details" />
				</h4>

				<div class="panel-body">
				
					<div class="table-responsive">
						<table
							class="table table-bordered table-striped contact-details-table"
							id="adminDetailsTable">
							<thead>
								<tr>
									<th width="8%"><spring:message code="sfac.srno"
											text="Sr. No." /></th>
									<th  width="10%"><spring:message code="sfac.designation"
											text="Designation" /><span class="showMand"><i
											class="text-red-1">*</i></span></th>
									<th  width="10%"><spring:message code="sfac.title" text="Title" /><span
										class="showMand"><i class="text-red-1">*</i></span></th>
									<th  width="10%"><spring:message code="sfac.name" text="Name" /><span
										class="showMand"><i class="text-red-1">*</i></span></th>
									<th  width="10%"><spring:message code="sfac.contact.no"
											text="Contact No." /><span class="showMand"><i
											class="text-red-1">*</i></span></th>
									<th  width="10%"><spring:message code="sfac.fpo.dob"
											text="Date Of Joining" /><span class="showMand"><i
											class="text-red-1">*</i></span></th>
									<th  width="10%"><spring:message code="sfac.emailId" text="Email Id" /><span
										class="showMand"><i class="text-red-1">*</i></span></th>
									<th  width="10%"><spring:message code="sfac.fpo.boardMem"
											text="Whether Board Member (Yes/No)" /></th>
									<th  width="10%"><spring:message code="sfac.fpo.din" text="DIN" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.dto.fpoAdministrativeDto}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.designation}</td>
										<td class="text-center">${dto.titleDesc}</td>
										<td class="text-center">${dto.name}</td>
										<td class="text-center">${dto.contactNo}</td>
										<td class="text-center">${dto.dofJoiningDesc}</td>
										<td class="text-center">${dto.emailId}</td>
										<td class="text-center">${dto.nameOfBoardDesc}</td>
										<td class="text-center">${dto.din}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				</c:if>

				<div class="form-group">
					<apptags:radio radioLabel="sfac.approve,sfac.reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="sfac.decision" path="dto.appStatus"
						defaultCheckedValue="APPROVED">
					</apptags:radio>
					<apptags:textArea labelCode="sfac.remark"
						path="dto.remark" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="100" />
				</div>

				<div class="text-center padding-top-10">
					<button type="button" align="center" class="btn btn-green-3"
						data-toggle="tooltip" data-original-title="Submit"
						onclick="saveApprovalData(this);">
						<spring:message code="sfac.submit" text="Submit" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
 
			</form:form>
		</div>
	</div>
</div>


