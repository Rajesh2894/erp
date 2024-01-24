<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="assets/js/init.js"></script>
<script type="text/javascript" src="js/asset/assetClassification.js"></script>
<div class="widget-content padding" id="classAssetEdit">
	<form:form action="AssetRegistration.html" id="assetClassificationId"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivC"></div>
		<form:hidden path="modeType" id="modeType" />
			<form:hidden path="accountIsActiveOrNot" id="accountIsActiveOrNot" />
			<input type="hidden" id="moduleDeptUrl"
				value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.classification.details" text="Location Details" /></a>
					</h4>
				</div>
				<div id="Applicant" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="form-group ">
							<%-- <label class="col-sm-2 control-label" for="">
								<spring:message code="asset.classification.locationcode" />
							</label>
							<div class="col-sm-4">
								<form:select
									path="astDetailsDTO.assetClassificationDTO.functionalLocationCode"
									id="functionalLocationCode" cssClass="form-control chosen-select-no-results"
									disabled="${command.modeType eq 'V'}">
									<form:option value=""><spring:message code="asset.info.select" /></form:option>
									<c:forEach items="${command.funcLocDTOList}" var="obj">
										<form:option value="${obj.funcLocationId}"
											code="${obj.funcLocationId}">${obj.funcLocationCode}</form:option>
									</c:forEach>
								</form:select>
							</div> --%>
							<label class="control-label required-control col-sm-2" for="">
								<spring:message code="asset.classification.department" />
							</label>
							<div class="col-sm-4">
								<form:select
									path="astDetailsDTO.assetClassificationDTO.department"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									cssClass="form-control mandColorClass chosen-select-no-results" id="dpDeptId"
									onchange="setDepartmentCode();" data-rule-required="true">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
									<c:forEach items="${command.departmentsList}" var="departments">
										<form:option value="${departments.dpDeptid }"
											code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
									</c:forEach>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
									<c:forEach items="${command.departmentsList}" var="departments">
										<form:option value="${departments.dpDeptid }"
											code="${departments.dpDeptcode }">${departments.dpNameMar }</form:option>
									</c:forEach>
									</c:if>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<apptags:input labelCode="asset.classification.gisid"
								isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetClassificationDTO.gisId"  maxlegnth="18"
								isMandatory="false" cssClass="decimal text-right form-control"></apptags:input>
                          <c:if test="${command.accountIsActiveOrNot eq true}">
							<label class="col-sm-2 control-label" for="costcenter"> <spring:message
									code="asset.classification.accountHead" /></label>
									 <div class="col-sm-4"> 
								<form:select
									path="astDetailsDTO.assetClassificationDTO.costCenter"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									cssClass="form-control chosen-select-no-results" id="costCenterId">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
									<c:forEach items="${command.acHeadCode}" var="costCenterList">
										<form:option value="${costCenterList.lookUpDesc }"
											code="${costCenterList.lookUpId}">${costCenterList.lookUpDesc}</form:option>
									</c:forEach>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
									<c:forEach items="${command.acHeadCode}" var="costCenterList">
										<form:option value="${costCenterList.descLangSecond }"
											code="${costCenterList.lookUpId}">${costCenterList.descLangSecond}</form:option>
									</c:forEach>
									</c:if>
									
								</form:select>
							</div>
							</c:if>
                          <c:if test="${command.accountIsActiveOrNot ne true}">
							<apptags:input labelCode="asset.classification.accountHead"
								path="astDetailsDTO.assetClassificationDTO.costCenter"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
								</c:if>
						</div>
						<div class="form-group">
							<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
									code="asset.classification.coordinate" /><br> <spring:message
									code="asset.classification.coordinate.range" /></label>
							<div class="col-sm-2">
							<spring:message
									code="asset.classification.longitude" var="longitude"/>
								<form:input type="text" class="form-control"
									disabled="${command.modeType eq 'V'}"  maxlegnth="10"
									cssClass="decimal text-right form-control" id="classLongitude"
									path="astDetailsDTO.assetClassificationDTO.longitude"
									placeholder="${longitude}"></form:input>
							</div>
							<div class="col-sm-2">
							<spring:message
									code="asset.classification.latitude"  var="latitude"/>
								<form:input type="text" class="form-control"
									cssClass="decimal text-right form-control" id="classLatitude"
									disabled="${command.modeType eq 'V'}"  maxlegnth="10"
									path="astDetailsDTO.assetClassificationDTO.latitude"
									placeholder ="${latitude}"></form:input>
							</div>
							
							<label class="control-label col-sm-2" for=""> <spring:message
									code="asset.information.location" />
							</label>
							<div class="col-sm-4">
								<form:select
									path="astDetailsDTO.assetClassificationDTO.location"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									cssClass="form-control chosen-select-no-results"
									id="assetLocId">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
									<c:forEach items="${command.locList}" var="locationList">
										<form:option value="${locationList.locId }" code="">${locationList.locNameEng }</form:option>
									</c:forEach>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
									<c:forEach items="${command.locList}" var="locationList">
										<form:option value="${locationList.locId }" code="">${locationList.locNameReg }</form:option>
									</c:forEach>
									</c:if>
									
								</form:select>
							</div>
						
						</div>
						
						<div class="form-group">
							<apptags:input labelCode="asset.classification.surveyNo"
								isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetClassificationDTO.surveyNo"  maxlegnth="290"
								isMandatory="false" cssClass="form-control"></apptags:input>
								
								<apptags:input labelCode="asset.classification.add"
								isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetClassificationDTO.address"  maxlegnth="499"
								isMandatory="false" cssClass="form-control"></apptags:input>
								
								
						</div>
						
						
					</div>
				</div>
			</div>
		</div>
		<c:if test="${command.approvalProcess ne 'Y' }">
		<div class="text-center">
			<c:choose>
				<c:when test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D' }">
					<c:set var="backButtonAction" value="showTab('#astInfo')" />
				</c:when>
				<c:otherwise>
					<c:set var="backButtonAction" value="backToHomePage()" />
				</c:otherwise>
			</c:choose>
			<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D' }">
				<button type="button" class="button-input btn btn-success"
					name="button" value="Save" onclick="saveAssetClassification(this);"
					id="save">
					<spring:message code="asset.classification.save&continue" />
				</button>
			</c:if>
			<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
				<button type="Reset" class="btn btn-warning"
					onclick="resetClass()">
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