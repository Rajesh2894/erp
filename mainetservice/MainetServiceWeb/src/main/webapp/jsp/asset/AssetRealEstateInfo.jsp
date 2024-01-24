<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetRealEstate.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<div class="widget-content padding">
	<form:form action="AssetRegistration.html" id="assetRealEsate"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivS"></div>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="bindingStatus" id="bindingStatus" />
		<input type="hidden" id="moduleDeptUrl"
				value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<%-- <div class="panel-heading">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant1"><spring:message
								code="asset.service.serviceinfo" /></a>
					</h4>
				</div> --%>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse1">
					<div class="panel-heading">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse1" href="#a1"><spring:message
									code="asset.service.realstate" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">


							<!-----  this is for check box condition  only for create   ---->
							<%-- <c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}"> --%>


							<c:choose>
								<c:when test="${command.modeType eq 'V'}">
									<div class="form-group">
										<label class="col-sm-2 control-label" id="realEstateLbl"
											for=""> <spring:message
												code="asset.realestate.aplicable" />
										</label>
										<div class="col-sm-4">
											<form:checkbox
												path="astDetailsDTO.assetRealEstateInfoDTO.isRealEstate"
												checked="${astDetailsDTO.assetRealEstateInfoDTO.isRealEstate}"
												id="isRealEstateApplicable" onclick="showAndHides()"
												class="margin-top-10 margin-left-10" disabled="true"></form:checkbox>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="form-group">
										<label class="col-sm-2 control-label" id="realEstateLbl"
											for=""> <spring:message
												code="asset.realestate.aplicable" />
										</label>
										<div class="col-sm-4">
											<form:checkbox
												path="astDetailsDTO.assetRealEstateInfoDTO.isRealEstate"
												checked="${astDetailsDTO.assetRealEstateInfoDTO.isRealEstate}"
												id="isRealEstateApplicable" onclick="showAndHides()"
												class="margin-top-10 margin-left-10" disabled="false"></form:checkbox>

										</div>
									</div>
								</c:otherwise>
							</c:choose>

							<div id=hideAndShowDeatils>
								<%-- </c:if> --%>


								<!------- ----------------- end  ------------------------------->




								<div class="form-group" id="serviceno">
									<apptags:input labelCode="asset.service.assesmentno"
										cssClass="form-control"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetRealEstateInfoDTO.assessmentNo"
										isMandatory="false" maxlegnth="20"></apptags:input>
									<apptags:input labelCode="asset.service.regno"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetRealEstateInfoDTO.propertyRegistrationNo"
										isMandatory="true" maxlegnth="20"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="asser.service.taxcode"
										cssClass="form-control"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetRealEstateInfoDTO.taxCode"
										isMandatory="false" maxlegnth="20"></apptags:input>
									<apptags:input labelCode="asser.service.amount"
										cssClass="decimal text-right form-control hasNumber"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetRealEstateInfoDTO.realEstAmount"
										isMandatory="false" maxlegnth="18"></apptags:input>
								</div>
								<div class="form-group" id="taxcode">
									<label class="col-sm-2 control-label" for=""> <spring:message
											code="asser.service.taxzone" /></label>
									<div class="col-sm-4">
										<form:select
											path="astDetailsDTO.assetRealEstateInfoDTO.taxZoneLocation"
											disabled="${command.modeType eq 'V'}" cssClass="form-control"
											id="taxZoneId">
											<form:option value="0">
												<spring:message code='asset.info.select' text="Select" />
											</form:option>
											<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
												<c:forEach items="${command.location}" var="lookUp">
													<form:option value="${lookUp.lookUpId}" code="">${lookUp.descLangFirst}</form:option>
												</c:forEach>
											</c:if>
											<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
												<c:forEach items="${command.location}" var="lookUp">
													<form:option value="${lookUp.lookUpId}" code="">${lookUp.descLangSecond}</form:option>
												</c:forEach>
											</c:if>
										</form:select>
									</div>
									<%-- <c:set var="baseLookupCode" value="ATN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							disabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.assetRealEstateInfoDTO.taxZoneLocation"
							cssClass="form-control" hasChildLookup="false" hasId="true"
							showAll="false" selectOptionLabelCode="Select"
							isMandatory="false" /> --%>
									<apptags:input labelCode="asser.service.municipality"
										isDisabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetRealEstateInfoDTO.municipalityName"
										isMandatory="false" maxlegnth="100"></apptags:input>
								</div>
							</div>
						</div>
					</div>




					<!--------------------------------------------- this is service information end---------------------------------------------- -->
				</div>
			</div>

			<c:if test="${command.approvalProcess ne 'Y' }">
				<div class="text-center">

					<c:choose>
						<c:when
							test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">
							<c:set var="backButtonAction" value="showTab('#astPurch')" />
						</c:when>
						<c:otherwise>
							<c:set var="backButtonAction" value="backToHomePage()" />
						</c:otherwise>
					</c:choose>
					<c:if
						test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" onclick="saveRealEstateInfo(this);"
							id="save">
							<spring:message code="asset.service.save&continue" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
						<button type="Reset" class="btn btn-warning"
							onclick="resetService()">
							<spring:message code="reset.msg" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" name="button"
						id="Back" value="Back" onclick="${backButtonAction}">
						<spring:message code="asset.information.back" />
					</button>
				</div>
			</c:if>
	</form:form>
</div>
