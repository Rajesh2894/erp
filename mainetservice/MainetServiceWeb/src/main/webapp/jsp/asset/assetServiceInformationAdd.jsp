<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetServiceInfoAdd.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="widget-content padding">
	<form:form action="AssetRegistration.html" id="assetServiceAdd"
		method="post" class="form-horizontal">

		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="bindingStatus" id="bindingStatus1" />
		<!-- This is used for only validate maintenance date -->
		<form:hidden
			path="astDetailsDTO.assetPurchaseInformationDTO.dateOfAcquisition"
			id="dtOfAcqId" />
		<input type="hidden" id="moduleDeptUrl"
			value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
			
		<c:set var="assetFlag"
			value="${userSession.moduleDeptCode == 'AST' ? true : false}" />
			<c:choose>
			<c:when test="${assetFlag}">
				<input type="hidden" id="atype" value="AST" />
			</c:when>
			<c:otherwise>
				<input type="hidden" id="atype" value="IAST" />
			</c:otherwise>
		</c:choose>
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant1"><spring:message
								code="asset.service.serviceinfo" /></a>
					</h4>
				</div>
				<!--------------------------------------------- this is service information start-------------------------------------------------->

				<div id="Applicant" class="panel-collapse collapse in">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDivAdd"></div>
					<div class="panel-body">
						<c:set var="index" value="${command.index}" scope="page" />
						<form:hidden path="astDetailsDTO.astSerList[${index}].editFlag"
							value="E" />
						<c:choose>

							<c:when test="${assetFlag}">
								<div class="form-group">

									<!--Maintenance Type Prefix  -->
									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="Maintenance Type" /></label>
									<c:set var="baseLookupCode" value="AMN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="astDetailsDTO.astSerList[${index}].manTypeId"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true"
										disabled="${command.modeType eq 'V'}"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Maintenance Type" />

									<!--Category Type Prefix  -->
									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="Maintenance Cateogry" /></label>
									<c:set var="baseLookupCode"
										value="${userSession.moduleDeptCode == 'AST' ? 'AMG':'IMG'}" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="astDetailsDTO.astSerList[${index}].manCatId"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true"
										disabled="${command.modeType eq 'V'}"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Category Type" />
								</div>

							</c:when>
							<c:otherwise>
								<form:hidden path="astDetailsDTO.astSerList[${index}].manTypeId"
									id="manTypeId" value="11" />
								<form:hidden path="astDetailsDTO.astSerList[${index}].manCatId"
									id="manCatId" value="11" />
							</c:otherwise>
						</c:choose>


						<c:choose>

							<c:when test="${assetFlag}">
								<div class="form-group">
									<apptags:input labelCode="asset.service.serviceno"
										cssClass="form-control alphaNumeric"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.astSerList[${index}].serviceNo"
										isMandatory="true" maxlegnth="20"></apptags:input>
									<apptags:input labelCode="asset.service.serviceprov"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.astSerList[${index}].serviceProvider"
										isMandatory="true" maxlegnth="100"></apptags:input>
								</div>

								<div class="form-group">

									<apptags:date fieldclass="datepicker"
										labelCode="asset.service.startdate"
										datePath="astDetailsDTO.astSerList[${index}].serviceStartDate"
										cssClass="mandColorClass" isMandatory="true"
										isDisabled="${command.modeType eq 'V'}"></apptags:date>

									<apptags:date fieldclass="datepicker"
										labelCode="asset.service.expiredate"
										datePath="astDetailsDTO.astSerList[${index}].serviceExpiryDate"
										cssClass="mandColorClass" isMandatory="true"
										isDisabled="${command.modeType eq 'V'}"></apptags:date>


									<%-- <label class="col-sm-2 control-label" for=""><spring:message
									code="asset.service.startdate" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker popup-date"
										disabled="${command.modeType eq 'V'}" id="serviceStartDate"
										path="astDetailsDTO.astSerList[${index}].serviceStartDate"
										isMandatory="false" maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							<label class="col-sm-2 control-label" for=""><spring:message
									code="asset.service.expiredate" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker popup-date"
										disabled="${command.modeType eq 'V'}" id="serviceExpiryDate"
										path="astDetailsDTO.astSerList[${index}].serviceExpiryDate"
										isMandatory="false" maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div> --%>

								</div>
								<div class="form-group">
									<apptags:input labelCode="asset.service.amount"
										isDisabled="${command.modeType eq 'V'}"
										cssClass="decimal text-right form-control"
										path="astDetailsDTO.astSerList[${index}].amount"
										isMandatory="false" maxlegnth="18"></apptags:input>
									<apptags:input labelCode="asset.service.warranty"
										isDisabled="${command.modeType eq 'V'}"
										cssClass="form-control hasNumber"
										path="astDetailsDTO.astSerList[${index}].warrenty"
										isMandatory="false" maxlegnth="18"></apptags:input>
								</div>

								<div class="form-group">
									<c:if test="${command.accountIsActiveOrNot eq true}">
										<label class="col-sm-2 control-label " for="costcenter">
											<spring:message code="asset.service.accountHead" />
										</label>
										<div class="col-sm-4">
											<form:select
												path="astDetailsDTO.astSerList[${index}].costCenter"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control chosen-select-no-results"
												id="ServiceCostCenterId">
												<form:option value="">
													<spring:message code='' text="Select" />
												</form:option>
												<c:forEach items="${command.acHeadCode}"
													var="costCenterList">
													<c:if
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<form:option value="${costCenterList.lookUpDesc }"
															code="${costCenterList.lookUpDesc}">${costCenterList.lookUpDesc}</form:option>
													</c:if>
													<c:if
														test="${userSession.getCurrent().getLanguageId() ne 1}">
														<form:option value="${costCenterList.descLangSecond }"
															code="${costCenterList.descLangSecond}">${costCenterList.descLangSecond}</form:option>
													</c:if>
												</c:forEach>

											</form:select>
										</div>
									</c:if>
									<c:if test="${command.accountIsActiveOrNot ne true}">
										<apptags:input labelCode="asset.service.accountHead"
											path="astDetailsDTO.astSerList[${index}].costCenter"
											isDisabled="${command.modeType eq 'V'}" isMandatory="false"
											cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
									</c:if>

									<apptags:textArea labelCode="asset.service.content"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.astSerList[${index}].serviceContent"
										isMandatory="false" maxlegnth="100"></apptags:textArea>
								</div>

								<div class="form-group">
									<apptags:textArea labelCode="assetservice.description"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.astSerList[${index}].serviceDescription"
										isMandatory="false" maxlegnth="100"></apptags:textArea>

									<apptags:date fieldclass="datepicker"
										labelCode="asset.maintenance.date"
										datePath="astDetailsDTO.astSerList[${index}].manDate"
										cssClass="mandColorClass" isMandatory="true"
										isDisabled="${command.modeType eq 'V'}"></apptags:date>

									<%-- <label class="col-sm-2 control-label" for=""><spring:message
									code="asset.maintenance.date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker popup-date"
										disabled="${command.modeType eq 'V'}" id="manDate"
										path="astDetailsDTO.astSerList[${index}].manDate"
										isMandatory="true" maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div> --%>
								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group">
									<apptags:input labelCode="asset.service.serviceprov"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.astSerList[${index}].serviceProvider"
										isMandatory="true" maxlegnth="100"></apptags:input>
									<apptags:date fieldclass="datepicker"
										labelCode="asset.service.startdate"
										datePath="astDetailsDTO.astSerList[${index}].serviceStartDate"
										cssClass="mandColorClass" isMandatory="false"
										isDisabled="${command.modeType eq 'V'}"></apptags:date>


								</div>
								<div class="form-group">
									<apptags:input labelCode="asset.service.warranty"
										isDisabled="${command.modeType eq 'V'}"
										cssClass="form-control hasNumber"
										path="astDetailsDTO.astSerList[${index}].warrenty"
										isMandatory="false" maxlegnth="18"></apptags:input>
									<apptags:date fieldclass="datepicker"
										labelCode="asset.service.expiredate"
										datePath="astDetailsDTO.astSerList[${index}].serviceExpiryDate"
										cssClass="mandColorClass" isMandatory="false"
										isDisabled="${command.modeType eq 'V'}"></apptags:date>

								</div>
								<div class="form-group">
									<apptags:textArea labelCode="assetservice.description"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.astSerList[${index}].serviceDescription"
										isMandatory="false" maxlegnth="100"></apptags:textArea>
								</div>
							</c:otherwise>
						</c:choose>





					</div>
				</div>
				<!--------------------------------------------- this is service information end---------------------------------------------- -->
				<c:if test="${command.modeType ne 'V'}">
					<div class="text-center">
						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" onclick="saveAstServiceInfo(this)"
							id="save">
							<spring:message code="" text="Save" />
						</button>
						<c:set var="index" value="${index + 1}" scope="page" />
						 <c:choose>
							<c:when test="${assetFlag}">
                               <button type="button" class="btn btn-danger" name="button"
							id="Back" value="Back" onclick="backToAstSerPageDataTable();">
							<spring:message code="asset.information.back" />
						</button>
							</c:when>
							<c:otherwise>
                               <button type="button" class="btn btn-danger" name="button"
							id="Back" value="Back" onclick="backToEdit(this);">
							<spring:message code="asset.information.back" />
						</button>
							</c:otherwise>
						</c:choose>
						
					</div>
				</c:if>
			</div>
		</div>
	</form:form>
</div>