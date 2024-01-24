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

#udyogAadharApplicable, #isWomenCentric,#approved {
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
			<apptags:helpDoc url="FPOMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="fpoMasterForm" action="FPOMasterForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="cbboMasterName" id="cbboMasterName" />
				<form:hidden path="iaMasterName" id="iaMasterName" />
				<form:hidden path="speciCategorycheck" id="speciCategorycheck" />
				<form:hidden path="womenCentric" id="womenCentric" />
				<form:hidden path="appStatus" id="appStatus" />
				<form:hidden path="" id="viewMode" value="${command.viewMode}"/>
				<form:hidden path="" id="orgshortName" value="${userSession.getCurrent().getOrganisation().getOrgShortNm()}"/>
				<form:hidden path="dupFpoName" id="dupFpoName" />
				<form:hidden path="dupCompRegNo" id="dupCompRegNo" />
				<form:hidden path="" id="showUdyogDet" value="${command.showUdyogDet}" />
				<%-- <form:hidden path="oldBlockDetPresent" id="oldBlockDetPresent" /> --%>
				



				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#cbboDetails">
									<spring:message code="sfac.fpo.cbbo.details"
										text="CBBO Details" />
								</a>
							</h4>
						</div>
						<div id="cbboDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
								<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'CBBO'}">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.IA.name" text="IA Name" /></label>
									<div class="col-sm-4">
										<form:select path="dto.iaId" id="iaId"
											onchange="getIaALlocationYear();"
											disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select'" />
											</form:option>
											<c:forEach items="${command.iaNameList}" var="dto">
												<form:option value="${dto.iaId}" code="${dto.IAName}">${dto.IAName}</form:option>
											</c:forEach>
										</form:select>
									</div>
									</c:if>
									<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() ne 'CBBO'}">
									<apptags:input labelCode="sfac.IA.name" isReadonly="true"
										cssClass="mandColorClass hasNameClass" path="dto.iaName"
										isMandatory="true" maxlegnth="100"></apptags:input>
										</c:if>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cbbo.ia.allocation.yr" text="IA EmpanelMent Year" /></label>
									<div class="col-sm-4">
										<form:select path="dto.iaAlcYear" id="iaAlcYear"
											disabled="true"
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
									<%-- 	<label class="col-sm-2 control-label required-control" for="">
										<spring:message code="sfac.fpo.cbbo.name" text="CBBO Name" />
									</label>
									<div class="col-sm-4">
								
										
										 <form:select path="dto.cbboId" id="cbboId"
											onchange="getCbboALlocationYear();"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select'" />
											</form:option>
											<c:forEach items="${command.cbboMasterList}" var="dto">
												<form:option value="${dto.cbboId}" code="${dto.cbboName}">${dto.cbboName}</form:option>
											</c:forEach>
										</form:select> 
									</div>--%>

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.cbbo.allcyr" text="CBBO Allocation Year" /></label>
									<div class="col-sm-4">
										<form:select path="dto.cbboAlcYear" id="cbboAlcYear"
											disabled="${command.viewMode eq 'V' ? true : false }"
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

							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#fpoDetails">
									<spring:message code="sfac.fpo.fpoDetails" text="FPO Details" />
								</a>
							</h4>
						</div>
						<div id="fpoDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"> <spring:message
											code="sfac.fpo.dmc.approval.status"
											text="DMC Approval Status" /> <span class="mand">*</span></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="APS" />
										<form:select path="dto.dmcApproval" id="dmcApproval" onchange="getAppPendingField();"
											cssClass="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }">
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
											code="sfac.approvalPending.date"
											text="Approval Pending Since" /><span class="showMand"><i
											class="text-red-1">*</i></span></label>
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
									<apptags:input labelCode="sfac.fpo.fpoName"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass hasNameClass " path="dto.fpoName"
										isMandatory="true" maxlegnth="100"></apptags:input>
									<apptags:input labelCode="sfac.fpo.cmpnyFpoRegNo"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass alpaSpecial charCase"
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
												class="form-control datepicker mandColorClass"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="dateIncorporation" placeholder="dd/mm/yyyy"
												onchange="getAgeOfFPO(this);" readonly="true" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
									
									<apptags:input labelCode="sfac.fpo.fpoAge"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass" path="dto.fpoAge" isMandatory="true"
										isReadonly="true"></apptags:input>
										
								</div>
								<div class="form-group">
									
								<%-- 	<label class="col-sm-2 control-label required-control"
										for="fpoOffAddr"><spring:message
											code="sfac.fpo.fpoAddress" text="FPO Address" /></label>
									<div class="col-sm-4">
										<form:textarea path="dto.fpoOffAddr" maxlegnth="100"
											class="form-control mandColorClass alphaNumeric"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="fpoOffAddr"></form:textarea>
									</div> --%>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.fpoTAN"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass alphaNumeric charCase"
										path="dto.fpoTanNo" isMandatory="true" maxlegnth="10"
										placeholder="ABCD12345X"></apptags:input>
									<apptags:input labelCode="sfac.fpo.fpoPAN"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass alphaNumeric charCase"
										path="dto.fpoPanNo" isMandatory="true" maxlegnth="10"
										placeholder="BLUPS4233S"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.noShareHolder"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass hasNumber" path="dto.noShareMem"
										isMandatory="true" maxlegnth="10"></apptags:input>
										
											<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.northEastRegion" text="North East Region" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.northEastRegion" class="form-control"
											id="NorthEastRegion"
											disabled="${command.viewMode eq 'V' ? true : false }">
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
									
									<%-- <apptags:input labelCode="sfac.fpo.totalEquity"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass hasNumber" path="dto.totalEquityAmt"
										isMandatory="true"></apptags:input>
 --%>
								</div>

								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.authorizeCapital"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass hasNumber" maxlegnth="10"
										path="dto.authorizeCapital" isMandatory="true"></apptags:input>
										
										<apptags:input labelCode="sfac.fpo.paidUpCapitalROC"
										isDisabled="${command.viewMode eq 'V' ? true : false }" maxlegnth="10"
										cssClass="mandColorClass hasNumber" path="dto.paidupCapital"
										isMandatory="true"></apptags:input>
										

									<%-- <apptags:input labelCode="sfac.fpo.sharedCapital"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass hasNumber" path="dto.sharedCapital"
										isMandatory="true"></apptags:input> --%>
								</div>



								<div class="form-group">
									<%-- 	<apptags:input labelCode="sfac.fpo.baseLineSurvey"
										cssClass="mandColorClass" path="dto.baseLineSurvey"
										isMandatory="true"></apptags:input> --%>

									<label class="col-sm-2 control-label required-control"
										for="regAct"> <spring:message
											code="sfac.fpo.baseLineSurvey" text="Base Line Survey" />
									</label>
									<c:set var="baseLookupCode" value="BLS" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="dto.baseLineSurvey"
										disabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="sfac.select" isMandatory="true" />

									<apptags:input labelCode="sfac.fpo.gstin" maxlegnth="15"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass alphaNumeric charCase"
										path="dto.gstin" placeholder="11AAAAA1111Z1A1"></apptags:input>

								</div>


								<div class="form-group stateDistBlock">
									<c:set var="baseLookupCode" value="SDB" />
									<apptags:lookupFieldSet
										disabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="form-control required-control " baseLookupCode="SDB"
										hasId="true" pathPrefix="dto.sdb"
										hasLookupAlphaNumericSort="true" isMandatory="true"
										hasSubLookupAlphaNumericSort="true" showAll="false" />
										
									<%-- <label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.state" text="State" /></label>
									<div class="col-sm-4">
										<form:select path="dto.sdb1"
											disabled="${command.viewMode eq 'V' ? true : false }"
											onchange="getDistrictList(this);"   class="form-control"
											id="sdb1">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach items="${command.dto.stateList}" var="lookUp">
												<form:option code="${lookUp.lookUpCode}"
													value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									
								<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.district" text="District" /></label>
									<div class="col-sm-4">
										<form:select path="dto.sdb2"
											disabled="${command.viewMode eq 'V' ? true : false }"
											onchange="getBlockList(this);"   class="form-control"
											id="sdb2">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:if test="${fn:length(command.dto.districtList)>0 }">
											<c:forEach items="${command.dto.districtList}" var="lookUp">
												<form:option code="${lookUp.lookUpCode}"
													value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
											</c:if>
										</form:select>
									</div> --%>

								</div>

							<%-- 	<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.block" text="Block" /></label>
									<div class="col-sm-4">
										<form:select path="dto.sdb3"
											disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control" id="sdb3">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:if test="${fn:length(command.dto.blockList)>0 }">
											<c:forEach items="${command.dto.blockList}" var="lookUp">
												<form:option code="${lookUp.lookUpCode}"
													value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
											</c:if>
										</form:select>
									</div>
								</div> --%>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.allocation.category" text="Allocation Category" /></label>
									<div class="col-sm-4">
										<form:select path="dto.allocationCategory"
											disabled="${command.viewMode eq 'V' ? true : false }"
											onchange="disableSubCateForBLock();"   class="form-control"
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


									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.allocation.subcategory" text="Allocation SubCategory" /></label>
									<div class="col-sm-4">
										<form:select path="dto.allocationSubCategory"
											disabled="${command.viewMode eq 'V' ? true : false }"
											onchange="checkSpecialCateExist();" class="form-control" id="allocationSubCategory">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach items="${command.allocationSubCatgList}"
												var="lookUp">
												<form:option code="${lookUp.lookUpCode}"
													value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>



								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="regAct"> <spring:message
											code="sfac.fpo.fpoRegistrationAct"
											text="FPO Registration Act" />
									</label>
									<c:set var="baseLookupCode" value="FRA" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="dto.regAct"
										disabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="form-control chosen-select-no-results"
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
											id="typeofPromAgen"
											disabled="${command.viewMode eq 'V' ? true : false }">
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
											code="sfac.aspirational.district"
											text="Aspirational District" /> <span class="mand">*</span></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass"
											id="isAspirationalDist" path="dto.isAspirationalDist"
											readonly="true"></form:input>
									</div>

									<label class="col-sm-2 control-label"> <spring:message
											code="sfac.tribal.district" text="Tribal District" /> <span
										class="mand">*</span></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass"
											id="isTribalDist" path="dto.isTribalDist" readonly="true"></form:input>
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
											code="sfac.fpo.womenCentricFPO"
											text="Is it Women centric FPO" /></label>
									<div class="col-sm-4 height-2rem">
										<form:checkbox id="isWomenCentric" path="dto.isWomenCentric"
											value="" checked="${command.dto.isWomenCentric eq 'Y'? 'checked' : '' }" disabled="${command.viewMode eq 'V' ? true : false }"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.registeredOnEnam" text="Registered On E-NAM" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.registeredOnEnam" class="form-control"
											id="registeredOnEnam"
											disabled="${command.viewMode eq 'V' ? true : false }">
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
									<apptags:input labelCode="sfac.fpo.userIdEnam"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass " path="dto.userIdEnam"
										maxlegnth="50"></apptags:input>
								</div>
								
								
								<div class="form-group">
									<label class="col-sm-2 control-label required-control" for="fpoCaAddress">
										<spring:message code="sfac.fpo.office.address"
											text="FPO Regd Office Address" />
									</label>
									<div class="col-sm-4">
										<form:input path="dto.officeAddress" maxlength="200"
											disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control mandColorClass alphaNumeric"
											id="officeAddress"></form:input>
									</div>
									<label class="col-sm-2 control-label" for="officeVillageName">
										<spring:message code="sfac.fpo.office.village.name"
											text="FPO Office Village Name" />
									</label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass alphaNumeric"
											maxlength="100" path="dto.officeVillageName"
											disabled="${command.viewMode eq 'V' ? true : false }"
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
											disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control mandColorClass alphaNumeric"
											id="fpoPostOffice"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control" for="officePinCode">
										<spring:message code="sfac.fpo.office.pin.code"
											text="FPO Office PIN Code" />
									</label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass hasPincode"
											disabled="${command.viewMode eq 'V' ? true : false }"
											path="dto.officePinCode" id="officePinCode" maxlength="6"></form:input>
									</div>

								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.Udyog.aadhar.appl" text="Udyog Aadhar Applicable?" /></label>
									<div class="col-sm-4 height-2rem">
										<form:checkbox id="udyogAadharApplicable"
											disabled="${command.viewMode eq 'V' ? true : false }"
											path="dto.udyogAadharApplicable" value="" checked="${command.dto.udyogAadharApplicable eq 'Y'? 'checked' : '' }" />
									</div>

								
								</div>
	                        
								<div class="form-group udyogDet">
								
									<label class="col-sm-2 control-label" for=udyogAadharNo>
										<spring:message code="sfac.Udyog.aadhar.no"
											text="Udyog Aaddhar No" /><span class="udyogMand"><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4">
										<form:input path="dto.udyogAadharNo" maxlength="20"
											disabled="${command.viewMode eq 'V' ? true : false }"
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
												disabled="${command.viewMode eq 'V' ? true : false }"
												class="form-control udyogAadharDatePicker mandColorClass"
												id="udyogAadharDate" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="" text="Acknowledgement Number" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass hasNumber"
											disabled="${command.viewMode eq 'V' ? true : false }"
											path="dto.acknowledgementNumber" id="acknowledgementNumber"
											maxlength="15"></form:input>
									</div>
								
 										<label class="col-sm-2 control-label"><spring:message
											code="" text="Status" /></label>
									<div class="col-sm-4">
										<form:select path="dto.activeInactiveStatus"
											class="form-control chosen-select-no-results" id="activeInactiveStatus"
											disabled="${command.viewMode eq 'V' ? true : false }" data-rule-required="true">
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
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#cropDetails">
									<spring:message code="sfac.fpo.cropDetails" text="Crop Details" />
								</a>
							</h4>
						</div>
						<div id="cropDetails" class="panel-collapse collapse">
							<div class="form-group padding-top-15">
								<label class="col-sm-2 control-label"><spring:message
										code="sfac.odop.Cultivation" text="ODOP Cultivation" /></label>
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="YNC" />
									<form:select path="dto.odopCultivation" class="form-control"
										id="odopCultivation"
										disabled="${command.viewMode eq 'V' ? true : false }">
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
								<label class="col-sm-2 control-label" for=udyogAadharNo>
									<spring:message code="sfac.totalAreaCovKharif"
										text="Total Area Covered(Kharif)" />
								</label>
								<div class="col-sm-4">
									<form:input path="dto.totalAreaCovKharif" disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control mandColorClass hasNumber"
										id="totalAreaCovKharif"></form:input>
								</div>

								<label class="col-sm-2 control-label" for=udyogAadharNo>
									<spring:message code="sfac.totalAreaCovRabi"
										text="Total Area Covered(Rabi)" />
								</label>
								<div class="col-sm-4">
									<form:input path="dto.totalAreaCovRabi" disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control mandColorClass hasNumber"
										id="totalAreaCovRabi"></form:input>
								</div>
							</div>

					
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<div class="table-responsive">
									<table
										class="table table-bordered table-striped crop-details-table"
										id="cropDetailsTable">
										<thead>
											<tr>
											   <th width="8%"><spring:message
														code="sfac.srno" text="Sr. No." /></th>
												<th width="12%"><spring:message
														code="sfac.fpo.cropSeason" text="Crop Season" /></th>
												<th width="12%"><spring:message code="sfac.fpo.cropType"
														text="Crop Type" /></th>
												<th width="15%"><spring:message code="sfac.fpo.cropName"
														text="Crop Name" /></th>
												<th width="12%"><spring:message
														code="sfac.fpo.primarySecondary.crop"
														text="Primary/Secondary Crop" /></th>
	
												<th width="12%"><spring:message
														code="sfac.crop.approved.by.dmc" text="Approved By DMC/Interception Crop" /></th>
	                                            <c:if test="${command.viewMode ne 'V'}">
												<th width="10%"><spring:message code="sfac.fpo.action"
														text="Action" /></th></c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.dto.fpoMasterDetailDto)>0 }">
													<c:forEach var="fpoMasterDetailDto"
														items="${command.dto.fpoMasterDetailDto}"
														varStatus="status">
														<tr class="appendableCropDetails">
															<td><form:input path=""
																	cssClass="form-control mandColorClass" id="sqNo${d}"
																	value="${d+1}" disabled="true" /> <form:hidden
																	path="dto.fpoMasterDetailDto[${d}].fpocId"
																	id="fpocId${d}" class="fpocId" /></td>
															<td>
																<div>
																	<c:set var="baseLookupCode" value="CRS" />
																	<form:select
																		path="dto.fpoMasterDetailDto[${d}].cropSeason"
																		class="form-control" id="cropSeason${d}" disabled="${command.viewMode eq 'V' ? true : false }">
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
															<td><div>
																	<c:set var="baseLookupCode" value="CRT" />
																	<form:select
																		path="dto.fpoMasterDetailDto[${d}].cropType"
																		class="form-control" id="cropType${d}" disabled="${command.viewMode eq 'V' ? true : false }"
																		onchange="disbledOtherCropDet(this,${d});">
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</div></td>
															<td><form:input
																	path="dto.fpoMasterDetailDto[${d}].cropName" disabled="${command.viewMode eq 'V' ? true : false }"
																	id="cropName${d}" class="form-control " maxlength="100"
																	value="" /></td>
															<td>
																<div>
																	<c:set var="baseLookupCode" value="PSC" />
																	<form:select
																		path="dto.fpoMasterDetailDto[${d}].priSecCrop"
																	  disabled="${command.viewMode eq 'V' ? true : false }"	class="form-control" id="priSecCrop${d}">
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
	
															<td>
																<div>
																	<c:set var="baseLookupCode" value="YNC" />
																	<form:select
																		path="dto.fpoMasterDetailDto[${d}].approvedByDmc"
																		class="form-control" id="approvedByDmc${d}" disabled="${command.viewMode eq 'V' ? true : false }">
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
	
	                                                        <c:if test="${command.viewMode ne 'V'}">
															<td class="text-center"><a
																class="btn btn-blue-2 btn-sm addButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addCropDetailsRow(this,${d});"> <i
																	class="fa fa-plus-circle"></i></a> <a
																class='btn btn-danger btn-sm delButton'
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="removeCropDetailsRow(this);"> <i
																	class="fa fa-trash"></i></a></td></c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="appendableCropDetails">
												      <td><form:input path=""
																cssClass="form-control mandColorClass" id="sqNo${d}"
																value="${d+1}" disabled="true" /></td>
														<td>
															<div>
																<c:set var="baseLookupCode" value="CRS" />
																<form:select
																	path="dto.fpoMasterDetailDto[${d}].cropSeason"
																	class="form-control" id="cropSeason${d}" disabled="${command.viewMode eq 'V' ? true : false }">
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
														<td><div>
																<c:set var="baseLookupCode" value="CRT" />
																<form:select path="dto.fpoMasterDetailDto[${d}].cropType"
																	class="form-control" id="cropType${d}" disabled="${command.viewMode eq 'V' ? true : false }"
																	onchange="disbledOtherCropDet(this,${d});">
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>
															</div></td>
														<td><form:input
																path="dto.fpoMasterDetailDto[${d}].cropName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="cropName${d}" class="form-control" maxlength="20"
																value="" /></td>
	
														<td>
															<div>
																<c:set var="baseLookupCode" value="PSC" />
																<form:select
																	path="dto.fpoMasterDetailDto[${d}].priSecCrop"
																	class="form-control" id="priSecCrop${d}" disabled="${command.viewMode eq 'V' ? true : false }">
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
	
														<td>
															<div>
																<c:set var="baseLookupCode" value="YNC" />
																<form:select
																	path="dto.fpoMasterDetailDto[${d}].approvedByDmc"
																	class="form-control" id="approvedByDmc${d}" disabled="${command.viewMode eq 'V' ? true : false }">
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
	
														 <c:if test="${command.viewMode ne 'V'}">
														<td class="text-center"><a
															class="btn btn-blue-2 btn-sm addButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCropDetailsRow(this,${d});"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm delButton'
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="removeCropDetailsRow(this);"> <i
																class="fa fa-trash"></i></a></td></c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#bankDetails">
									<spring:message code="sfac.fpo.bankDetails" text="Bank Details" />
								</a>
							</h4>
						</div>
						<div id="bankDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
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
												<c:if test="${command.viewMode ne 'V'}">
												<th width="10%"><spring:message code="sfac.action"
														text="Action" /></th></c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${fn:length(command.dto.fpoBankDetailDto)>0 }">
													<c:forEach var="dto" items="${command.dto.fpoBankDetailDto}"
														varStatus="status">
														<tr class="appendableBankDetails">
	
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass" id="sNo${d}"
																	value="${d+1}" disabled="true" /> <form:hidden
																	path="dto.fpoBankDetailDto[${d}].bkId" id="bkId${d}"
																	class="bkId" /></td>
	
															<td>
																<div>
																	<form:select path="dto.fpoBankDetailDto[${d}].bankName"
																		class="form-control chosen-select-no-results" disabled="${command.viewMode eq 'V' ? true : false }"
																		id="bankName${d}" onchange="getBankCode(${d});">
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach items="${command.bankName}" var="bank"
																			varStatus="counter">
																			<form:option value="${bank}" code="${bank}">${bank}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
	
															<td><form:select
																	path="dto.fpoBankDetailDto[${d}].ifscCode"
																	class="form-control chosen-select-no-results" disabled="${command.viewMode eq 'V' ? true : false }"
																	id="ifscCode${d}" onchange="getBankBranch(${d});">
																	<c:forEach items="${dto.bankMasterList}" var="bankMst"
																		varStatus="counter">
																		<c:choose>
																			<c:when test="${dto.bkId eq bankMst.bankId}">
																				<form:option value="${bankMst.bankId}"
																					code="${bankMst.bankId}" selected="selected">${bankMst.ifsc}</form:option>
																			</c:when>
																			<c:otherwise>
																				<form:option value="${bankMst.bankId}"
																					code="${bankMst.bankId}">${bankMst.ifsc}</form:option>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																</form:select></td>
	
	
															<td><form:input
																	path="dto.fpoBankDetailDto[${d}].branchName"
																	id="branchName${d}" readonly="true"
																	class="form-control " maxlength="400" /></td>
	
	
															<td><form:input
																	path="dto.fpoBankDetailDto[${d}].accountNo"
																	id="accountNo${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control hasNumber" maxlength="16" /></td>
	
	
	                                                      <c:if test="${command.viewMode ne 'V'}">
															<td class="text-center"><a
																class="btn btn-blue-2 btn-sm addBankButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addBankButton(this);"> <i
																	class="fa fa-plus-circle"></i></a> <a
																class='btn btn-danger btn-sm deleteBankDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteBankDetails(this);"> <i
																	class="fa fa-trash"></i>
															</a></td></c:if>
	
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="appendableBankDetails">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
	
														<td>
															<%-- <form:input
																path="dto.fpoBankDetailDto[${d}].bankName"
																id="bankName${d}"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control" maxlength="500" /> --%>
															<div>
																<form:select path="dto.fpoBankDetailDto[${d}].bankName"
																	class="form-control chosen-select-no-results" disabled="${command.viewMode eq 'V' ? true : false }"
																	id="bankName${d}" onchange="getBankCode(${d});">
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach items="${command.bankName}" var="bank"
																		varStatus="counter">
																		<form:option value="${bank}" code="${bank}">${bank}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
	
														<td><form:select
																path="dto.fpoBankDetailDto[${d}].ifscCode"
																class="form-control chosen-select-no-results" disabled="${command.viewMode eq 'V' ? true : false }"
																id="ifscCode${d}" onchange="getBankBranch(${d});">
																<form:option value="0">
																	<spring:message code="sfac.select" text="Select" />
																</form:option>
																<%-- <c:forEach items="${command.bankName}" var="bank" varStatus="counter">
																		<form:option value="${bank}"
																			code="${bank}">${bank}</form:option>
																	</c:forEach> --%>
															</form:select></td>
	
	
	
	
	
														<td><form:input
																path="dto.fpoBankDetailDto[${d}].branchName"
																id="branchName${d}" readonly="true" class="form-control "
																maxlength="400" /></td>
														<td><form:input
																path="dto.fpoBankDetailDto[${d}].accountNo"
																id="accountNo${d}"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control hasNumber" maxlength="16" /></td>
	                                                    <c:if test="${command.viewMode ne 'V'}">
														<td class="text-center"><a
															class="btn btn-blue-2 btn-sm addBankButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addBankButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteBankDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteBankDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></td></c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
	
										</tbody>
									</table>
								</div>

								<%-- 		<div class="form-group">
									<label class="col-sm-2 control-label required-control" for="">
										<spring:message code="sfac.fpo.bankName" text="Bank Name" />
									</label>
									<div class="col-sm-4">
										<form:select path="dto.bankName"
											class="form-control chosen-select-no-results" id="bankName"
											onchange="getBankCode(this);">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach items="${command.banks}" var="bank">
												<form:option value="${bank.bankId}" code="${bank.bankId}">${bank.bank} :: ${bank.ifsc} :: ${bank.branch}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<apptags:input labelCode="sfac.fpo.accountNo"
										cssClass="mandColorClass hasNumber" path="dto.accountNo"
										isMandatory="true" maxlegnth="10"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.ifscCode"
										cssClass="mandColorClass" path="dto.ifscCode"
										isMandatory="true" isReadonly="true" maxlegnth=""></apptags:input>

									<apptags:input labelCode="sfac.fpo.branchName"
										cssClass="mandColorClass" path="dto.branch" isMandatory="true"
										isReadonly="true"></apptags:input>
								</div> --%>

							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#administrativeDetails"> <spring:message
										code="sfac.fpo.administrativeDetails"
										text="Administrative Details" /></a>
							</h4>
						</div>
						<div id="administrativeDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<div class="table-responsive">
									<table
										class="table table-bordered table-striped contact-details-table"
										id="adminDetailsTable">
										<thead>
											<tr>
												<th width="8%"><spring:message code="sfac.srno"
														text="Sr. No." /></th>
												<th><spring:message code="sfac.designation"
														text="Designation" /><span class="showMand"><i
														class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.title" text="Title" /><span
													class="showMand"><i class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.name" text="Name" /><span
													class="showMand"><i class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.contact.no"
														text="Contact No." /><span class="showMand"><i
														class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.fpo.dob"
														text="Date Of Joining" /><span class="showMand"><i
														class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.emailId" text="Email Id" /><span
													class="showMand"><i class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.fpo.boardMem" text="Whether Board Member (Yes/No)" /></th>
												<th><spring:message code="sfac.fpo.din" text="DIN" /></th>
												 <c:if test="${command.viewMode ne 'V'}">
												<th width="10%"><spring:message code="sfac.action"
														text="Action" /></th></c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.dto.fpoAdministrativeDto)>0 }">
													<c:forEach var="dto"
														items="${command.dto.fpoAdministrativeDto}"
														varStatus="status">
														<tr class="appendableAdminDetails">
	
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass" id="sNo${d}"
																	value="${d+1}" disabled="true" /> <form:hidden
																	path="dto.fpoAdministrativeDto[${d}].adId" id="adId${d}"
																	class="contId" /></td>
	
															<td>
																<div>
																	<form:select id="dsgId${d}"
																		path="dto.fpoAdministrativeDto[${d}].dsgId"
																		disabled="${command.viewMode eq 'V' ? true : false }"
																		cssClass="form-control ">
																		<form:option value="">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach items="${command.designlist}" var="desig">
																			<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
															<td>
																<div>
																	<c:set var="baseLookupCode" value="TTL" />
																	<form:select
																		path="dto.fpoAdministrativeDto[${d}].titleId"
																		class="form-control" id="titleId${d}"
																		disabled="${command.viewMode eq 'V' ? true : false }">
																		<c:set var="baseLookupCode" value="TTL" />
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
	
	
	
															<td><form:input
																	path="dto.fpoAdministrativeDto[${d}].name" id="name${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control hasNameClass" maxlength="250" /></td>
	
															<td><form:input
																	path="dto.fpoAdministrativeDto[${d}].contactNo"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	id="contactNo${d}" class="form-control hasMobileNo"
																	maxlength="10" /></td>
	
															<td align="center"><form:input
																	path="dto.fpoAdministrativeDto[${d}].dateOfJoining"
																	cssClass="form-control datepickerOfJoiningDate datepicker text-center"
																	placeholder="DD/MM/YYYY" maxlength="10"
																	id="dateOfJoining${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }" /></td>
	
															<td><form:input
																	path="dto.fpoAdministrativeDto[${d}].emailId"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	id="emailId${d}" class="form-control hasemailclass" /></td>

															<td><c:set var="baseLookupCode" value="YNC" /> <form:select
																	path="dto.fpoAdministrativeDto[${d}].nameOfBoard" class="form-control"
																	id="nameOfBoard${d}" onchange="enableDinOnYes(${d});"
																	disabled="${command.viewMode eq 'V' ? true : false }">
																	<form:option value="0">
																		<spring:message code="sfac.select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																	</form:select>
																</td>
															<td><form:input
																	path="dto.fpoAdministrativeDto[${d}].din"
																	maxlength="50" disabled="true"
																	id="din${d}" class="form-control" /></td>
	 														 
	 														<c:if test="${command.viewMode ne 'V'}">
															<td class="text-center"><a
																class="btn btn-blue-2 btn-sm addAdminButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addAdminButton(this);"> <i
																	class="fa fa-plus-circle"></i></a> <a
																class='btn btn-danger btn-sm deleteAdminDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteAdminDetails(this);"> <i
																	class="fa fa-trash"></i>
															</a></td></c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="appendableAdminDetails">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
	
														<td>
															<div>
																<form:select id="dsgId${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	path="dto.fpoAdministrativeDto[${d}].dsgId"
																	cssClass="form-control">
																	<form:option value="">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach items="${command.designlist}" var="desig">
																		<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
	
														<td>
															<div>
																<c:set var="baseLookupCode" value="TTL" />
																<form:select
																	path="dto.fpoAdministrativeDto[${d}].titleId"
																	class="form-control" id="titleId${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }">
																	<c:set var="baseLookupCode" value="TTL" />
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
	
	
	
														<td><form:input
																path="dto.fpoAdministrativeDto[${d}].name" id="name${d}"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control hasNameClass" maxlength="250" /></td>
	
														<td><form:input
																path="dto.fpoAdministrativeDto[${d}].contactNo"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="contactNo${d}" class="form-control hasMobileNo"
																maxlength="10" /></td>
	
														<td align="center"><form:input
																path="dto.fpoAdministrativeDto[${d}].dateOfJoining"
																cssClass="form-control datepicker datepickerOfJoiningDate text-center"
																placeholder="DD/MM/YYYY" maxlength="10"
																id="dateOfJoining${d}"
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
	
														<td><form:input
																path="dto.fpoAdministrativeDto[${d}].emailId"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="emailId${d}" class="form-control hasemailclass" /></td>

														<td><c:set var="baseLookupCode" value="YNC" /> <form:select
																path="dto.fpoAdministrativeDto[${d}].nameOfBoard" class="form-control"
																id="nameOfBoard${d}" onchange="enableDinOnYes(${d});"
																disabled="${command.viewMode eq 'V' ? true : false }">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach></form:select></td>

														<td><form:input
																path="dto.fpoAdministrativeDto[${d}].din"
																maxlength="50" disabled="true"
																id="din${d}" class="form-control" /></td>
														<c:if test="${command.viewMode ne 'V'}">
														<td class="text-center"><a
															class="btn btn-blue-2 btn-sm addAdminButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addAdminButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteAdminDetails'
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteAdminDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></td></c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
						

			<%-- 	<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#approvedDetails">
									<spring:message code="sfac.fpo.approveDetails" text="Status Details" />
								</a>
							</h4>
						</div>
						<div id="approvedDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.approved.ia" text="Approved BY IA" /><span class="mand iaMand">*</span>
											</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="FMA" />
										<form:select path="dto.approvedByIa" class="form-control"
											id="approvedByIa"
											disabled="${command.viewMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() ne 'IA'? true : false }">
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
									
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.approved.cbbo" text="Approved By CBBO" /><span class="mand cbboMand">*</span></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="FMA" />
										<form:select path="dto.approvedByCbbo" class="form-control"
											id="approvedByCbbo"
											disabled="${command.viewMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() ne 'CBBO'? true : false }">
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
										<label class="col-sm-2 control-label"><spring:message
												code="sfac.approved.fpo" text="Approved By FPO" />
												<span class="mand fpoMand">*</span></label>
									<c:set var="baseLookupCode" value="FMA" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="dto.approvedByFpo"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										showAll="false"
										disabled="${command.viewMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() ne 'FPO'? true : false }"
										selectOptionLabelCode="sfac.select" />
								</div>


							</div> 
						</div>
					</div> --%>

				</div>



				<h5 class="bg-deep-danger" style="margin-top: 10px;">
					<strong><i><spring:message code="sfac.fpo.cbbo.disclaimer" text="Note" /></i></strong>
				</h5>

				<div class="text-center padding-top-10">
							<c:if test="${command.viewMode ne 'V'}">
								<button type="button" class="btn btn-success"
									title='<spring:message code="sfac.submit" text="Submit" />'
									onclick="saveFPOMasterForm(this);">
									<spring:message code="sfac.submit" text="Submit" />
								</button></c:if>
								<c:if test="${command.viewMode eq 'A'}">
									<button type="button" class="btn btn-warning"
										title='<spring:message code="sfac.button.reset" text="Reset"/>'
										onclick="window.location.href ='FPOMasterForm.html'">
										<spring:message code="sfac.button.reset" text="Reset" />
									</button>
								</c:if>
								<apptags:backButton url="FPOMasterForm.html"></apptags:backButton>
							</div>
          </form:form>
		</div>
	</div>
</div>