<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetInsuranceDetails.js"></script>
<!--T#101107 This page assetInsurenseDetial.jsp is used by two modules 
named as 1) Asset Module(AST) 
         2) ITAsset Module(IAST)
         if(Asset Module){
          assetFlag = true;
         }else{
         assetFlag =false;
         }
         <c:choose>
            <c:when test="${assetFlag}">
               Asset Module code
			</c:when>
			<c:otherwise>
              ITAsset Module code
			</c:otherwise>
		</c:choose>
        -->
<div class="widget-content padding">
	<form:form action="AssetRegistration.html" id="assetInsurance"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivI"></div>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="accountIsActiveOrNot" id="accountIsActiveOrNot" />
		<input type="hidden" id="moduleDeptUrl"
			value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<c:set var="assetFlag"
			value="${userSession.moduleDeptCode == 'AST' ? true : false}" />
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="Applicant"
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.insurance.insurancedetail" /></a>
					</h4>
				</div>
				<div class="panel-body">
					<!-----  this is for check box condition  only for create   ---->
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
						<div class="form-group">
							<label class="col-sm-2 control-label" id="insuApplicableLbl"
								for=""> <spring:message
									code="asset.insurance.applicable" />
							</label>
							<div class="col-sm-4">
								<form:checkbox path="astDetailsDTO.astInsuDTO.isInsuApplicable"
									checked="${astDetailsDTO.astInsuDTO.isInsuApplicable}"
									id="isInsuApplicable" onclick="showAndHides()"
									class="margin-top-10 margin-left-10" disabled="false"></form:checkbox>

							</div>
						</div>
						<div id=hideAndShowInsurance>
					</c:if>


					<!------- ----------------- end  ------------------------------->
					<div class="form-group">
						<apptags:input labelCode="asset.insurance.insuranceno"
							cssClass="form-control"
							isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
							path="astDetailsDTO.astInsuDTO.insuranceNo" maxlegnth="20"
							isMandatory="true"></apptags:input>
						<apptags:input labelCode="asset.insurance.insuranceprov"
							path="astDetailsDTO.astInsuDTO.insuranceProvider"
							isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
							isMandatory="true" maxlegnth="100"></apptags:input>
					</div>

					<c:choose>
						<c:when test="${assetFlag }">

							<div class="form-group">
								<label class="col-sm-2 control-label" for="insurancetype">
									<spring:message code="asset.insurance.insurancetype" />
								</label>
								<c:set var="baseLookupCode"
									value="${userSession.moduleDeptCode == 'AST' ? 'TOI':'IOI'}" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									disabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.typeOfInsurance"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="false" />

								<apptags:input labelCode="asset.insurance.insurancerate"
									cssClass="decimal text-right form-control"
									isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.insuranceRate"
									isMandatory="false" maxlegnth="18"></apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="asset.insurance.insuredamount"
									cssClass="decimal text-right form-control"
									isDisabled="${command.modeType eq 'V' or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.insuranceAmount"
									isMandatory="false" maxlegnth="18"></apptags:input>
								<label class="col-sm-2 control-label" for="premiumfrequency">
									<spring:message code="asset.insurance.premiumfrequency" />
								</label>
								<c:set var="baseLookupCode"
									value="${userSession.moduleDeptCode == 'AST' ? 'PRF':'IRF'}" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.astInsuDTO.premiumFrequency"
									disabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="flase" />
							</div>

							<div class="form-group">
								<apptags:input labelCode="asset.insurance.premiumvalue"
									cssClass="decimal text-right form-control"
									isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.premiumValue"
									isMandatory="false" maxlegnth="18"></apptags:input>
								<apptags:input labelCode="asset.insurance.status"
									isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.status" isMandatory="false"
									maxlegnth="100"></apptags:input>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<apptags:input labelCode="asset.insurance.insurancerate"
									cssClass="decimal text-right form-control"
									isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.insuranceRate"
									isMandatory="false" maxlegnth="18"></apptags:input>
								<apptags:input labelCode="asset.insurance.insuredamount"
									cssClass="decimal text-right form-control"
									isDisabled="${command.modeType eq 'V' or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.insuranceAmount"
									isMandatory="false" maxlegnth="18"></apptags:input>
							</div>

							<div class="form-group">

								<label class="col-sm-2 control-label" for="premiumfrequency">
									<spring:message code="asset.insurance.premiumfrequency" />
								</label>
								<c:set var="baseLookupCode"
									value="${userSession.moduleDeptCode == 'AST' ? 'PRF':'IRF'}" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.astInsuDTO.premiumFrequency"
									disabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="flase" />
								<apptags:input labelCode="asset.insurance.premiumvalue"
									cssClass="decimal text-right form-control"
									isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.premiumValue"
									isMandatory="false" maxlegnth="18"></apptags:input>

							</div>


						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<label class="col-sm-2 control-label" for=""><spring:message
								code="asset.insurance.startdate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker"
									id="InsuranceStartDate"
									disabled="${command.modeType eq 'V' or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.insuranceStartDate"
									isMandatory="false" maxlength="10"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<label class="col-sm-2 control-label" for=""><spring:message
								code="asset.insurance.enddate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker"
									id="InsuranceExpiryDate"
									disabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
									path="astDetailsDTO.astInsuDTO.insuranceEndDate"
									isMandatory="false" maxlength="10"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

					</div>

					<!-- In case of Edit an input hidden field is added -->
					<c:if
						test="${command.modeType eq 'E' || command.modeType eq 'C' || command.modeType eq 'D'}">
						<form:input path="subModeType" value="${command.subModeType}"
							style="display:none" />
					</c:if>

					<c:if
						test="${command.modeType eq 'E' || command.modeType eq 'C' || command.modeType eq 'D'}">
						<form:input path="astDetailsDTO.astInsuDTO.revGrpIdentity"
							value="N" style="display:none" />
					</c:if>


					<div class="form-group">
						<c:choose>

							<c:when test="${assetFlag}">
								<c:if test="${command.accountIsActiveOrNot eq true}">
									<label class="col-sm-2 control-label" for="costcenter">
										<spring:message code="asset.insurance.accountHead" />
									</label>
									<div class="col-sm-4">
										<form:select path="astDetailsDTO.astInsuDTO.costCenter"
											disabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
											cssClass="form-control chosen-select-no-results"
											id="insuranceCostCenterId">
											<form:option value="">
												<spring:message code='asset.info.select' text="Select" />
											</form:option>
											<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
												<c:forEach items="${command.acHeadCode}"
													var="costCenterList">
													<form:option value="${costCenterList.lookUpDesc }"
														code="${costCenterList.lookUpDesc}">${costCenterList.lookUpDesc}</form:option>
												</c:forEach>
											</c:if>
											<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
												<c:forEach items="${command.acHeadCode}"
													var="costCenterList">
													<form:option value="${costCenterList.descLangSecond }"
														code="${costCenterList.descLangSecond}">${costCenterList.descLangSecond}</form:option>
												</c:forEach>
											</c:if>
										</form:select>
									</div>
								</c:if>
								<c:if test="${command.accountIsActiveOrNot ne true}">
									<apptags:input labelCode="asset.insurance.accountHead"
										path="astDetailsDTO.astInsuDTO.costCenter"
										isDisabled="${command.modeType eq 'V'}" isMandatory="false"
										cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
								</c:if>
							</c:when>
							<c:otherwise>

							</c:otherwise>
						</c:choose>

						<apptags:input labelCode="asset.insurance.remark"
							isDisabled="${command.modeType eq 'V'  or (not empty command.subModeType and command.subModeType eq 'View') }"
							path="astDetailsDTO.astInsuDTO.remark" isMandatory="false"
							maxlegnth="500"></apptags:input>
					</div>
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
				</div>
				</c:if>
			</div>
		</div>
</div>
<c:if test="${command.approvalProcess ne 'Y' }">
	<div class="text-center">
		<c:choose>
			<c:when
				test="${(command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D') && ( empty command.subModeType)}">
				<c:set var="backButtonAction" value="showTab('#astSer')" />
			</c:when>
			<c:when
				test="${not empty command.subModeType and command.subModeType eq 'View'}">
				<c:set var="backButtonAction" value="CloseFancyBox(this)" />
			</c:when>
			<c:when
				test="${not empty command.subModeType and ( command.subModeType eq 'Add'or command.subModeType eq 'Edit') }">
				<c:set var="backButtonAction" value="backToAstInsurDataTable()" />
			</c:when>
			<c:otherwise>
				<c:set var="backButtonAction" value="backToHomePage()" />
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when
				test="${(command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D') && empty command.subModeType}">
				<button type="button" class="button-input btn btn-success"
					name="button" value="Save" onclick="saveAstInsurDet(this);"
					id="save">
					<spring:message code="asset.insurance.save&continue" />
				</button>
			</c:when>
			<c:when
				test="${command.modeType eq 'E' && not empty command.subModeType && (command.subModeType eq 'Edit' || command.subModeType eq 'Add')}">
				<button type="button" class="button-input btn btn-success"
					name="button" value="Save" onclick="saveAstInsurDet(this);"
					id="save">
					<spring:message code="asset.insurance.save&continue" />
				</button>
			</c:when>
		</c:choose>
		<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
			<button type="Reset" class="btn btn-warning"
				onclick="resetInsurance()">
				<spring:message code="reset.msg" text="Reset" />
			</button>
		</c:if>

		<button type="button" class="btn btn-danger" name="button" id="Back"
			value="Back" onclick="${backButtonAction}">
			<spring:message code="asset.information.back" />
		</button>


	</div>
</c:if>
</form:form>
</div>