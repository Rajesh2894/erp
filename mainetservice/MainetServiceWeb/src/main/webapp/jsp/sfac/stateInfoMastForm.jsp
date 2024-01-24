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
<script type="text/javascript" src="js/sfac/stateInfoMastForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.state.info.title"
					text="District Information Form" />
			</h2>
			<apptags:helpDoc url="StateInformationMaster.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="StateInfoMastForm"
				action="StateInformationMaster.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					<form:hidden path="dataExistcheck" id="dataExistcheck" />

				<div class="form-group">
					<label for="State" class="col-sm-2 control-label required-control"><spring:message
							code="sfac.state" text="State">
						</spring:message></label>
					<div class="col-sm-4">
						<form:select path="stateInfoDto.state" class="form-control chosen-select-no-results"
							id="state" onchange="getDistrictList();" disabled="${command.viewMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.stateList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label for="District"
						class="col-sm-2 control-label required-control"><spring:message
							code="sfac.district" text="District">
						</spring:message></label>
					<div class="col-sm-4">
						<form:select path="stateInfoDto.district" id="district" onchange="checkDataAlreadyExistByDist();"
							class="form-control mandColorClass chosen-select-no-results" disabled="${command.viewMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code='sfac.select' text="Select" />
							</form:option>
							<c:forEach items="${command.districtList}" var="dist">
								<form:option value="${dist.lookUpId}" code="${dist.lookUpCode}">${dist.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.Area.Type" text="Area Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="STC" />
						<form:select path="stateInfoDto.areaType" class="form-control chosen-select-no-results"
							id="areaType" disabled="${command.viewMode eq 'V' ? true : false }">
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option code="${lookUp.lookUpCode}"
									value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

				<%-- 	<label class="col-sm-2 control-label"><spring:message
							code="sfac.region" text="Region" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="REG" />
						<form:select path="stateInfoDto.zone" id="zone"
							cssClass="form-control chosen-select-no-results" disabled="${command.viewMode eq 'V' ? true : false }">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
					
						<label class="col-sm-2 control-label"><spring:message
							code="sfac.aspirational.district" text="Aspirational District" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="YNC" />
						<form:select path="stateInfoDto.aspirationalDist" disabled="${command.viewMode eq 'V' ? true : false }"
							class="form-control chosen-select-no-results" id="aspirationalDist">
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
				
					<%-- <label class="col-sm-2 control-label"><spring:message
							code="sfac.tribal.district" text="Tribal District" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="YNC" />
						<form:select path="stateInfoDto.tribalDist" class="form-control chosen-select-no-results"
							id="tribalDist" disabled="${command.viewMode eq 'V' ? true : false }">
							<form:option value="0">
								<spring:message code="sfac.select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.Odop" text="Odop" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="ODP" />
						<form:select path="stateInfoDto.odop" disabled="${command.viewMode eq 'V' ? true : false }"
							class="form-control chosen-select-no-results" id="odop">
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

				<div class="text-center padding-top-10">
				<c:if test="${command.viewMode ne 'V' }">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.submit" text="Submit" />'
						onclick="saveStateMasterForm(this);">
						<spring:message code="sfac.submit" text="Submit" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset"/>'
						onclick="ResetForm()">
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger"
						title='<spring:message code="sfac.button.back" text="Back"/>'
						onclick="window.location.href ='StateInformationMaster.html'">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>

	</div>
</div>
