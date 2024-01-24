<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript"
	src="js/asset/chartOfDepreciationMaster.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="asset.depreciationMaster.header" text="" />
				</h2>
				<apptags:helpDoc url="ChartOfDepreciationMaster.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form action="ChartOfDepreciationMaster.html"
					class="form-horizontal" name="depreciationMaster"
					id="depreciationMaster">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				<input type="hidden" id="moduleDeptUrl"  value="${userSession.moduleDeptCode == 'AST' ? 'ChartOfDepreciationMaster.html':'ITChartOfDepreciationMaster.html'}">

					<div class="form-group">
						<apptags:input labelCode="asset.depreciationMaster.groupName" cssClass="hasCharacter"
							path="assetChartOfDepreciationMasterDTO.name" isMandatory="false" maxlegnth="100"></apptags:input>
					<c:if test="${accountFlag eq true}">
						<label class="col-sm-2 control-label"><spring:message
								code="asset.depreciationMaster.accountCode" /></label>
						<div class="col-sm-4">
							<form:select path="assetChartOfDepreciationMasterDTO.accountCode" id="accountCode"
								class="form-control mandColorClass chosen-select-no-results"
								data-rule-required="true">
								<form:option value="0" selected="selected">
									<spring:message code="asset.info.select" />
								</form:option>
								<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
								<c:forEach items="${accountCodeHead}" var="lookUp">
									<form:option value="${lookUp.descLangFirst}">${lookUp.descLangFirst}</form:option>
								</c:forEach>
								</c:if>
								<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
								<c:forEach items="${accountCodeHead}" var="lookUp">
									<form:option value="${lookUp.descLangFirst}">${lookUp.descLangFirst}</form:option>
								</c:forEach>
								</c:if>
							</form:select>
						</div> 	
						</c:if>	
						<c:if test="${accountFlag ne true}">
						<apptags:input labelCode="asset.depreciationMaster.accountCode"
								path="assetChartOfDepreciationMasterDTO.accountCode"
								isDisabled="false" isMandatory="false"
								cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
						</c:if>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for=""> <spring:message
								code="asset.depreciationMaster.assetClass" /></label>
						<div class="col-sm-4">
							<form:select path="assetChartOfDepreciationMasterDTO.assetClass" id="assetClass"
								class="form-control mandColorClass chosen-select-no-results"
								data-rule-required="true">
								<form:option value="0" selected="selected">
									<spring:message code="asset.info.select" />
								</form:option>
								<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
								<c:forEach items="${assetClassHead}" var="lookUp">
									<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
								</c:forEach>
								</c:if>
								<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
								<c:forEach items="${assetClassHead}" var="lookUp">
									<form:option value="${lookUp.lookUpId}">${lookUp.descLangSecond}</form:option>
								</c:forEach>
								</c:if>
							</form:select>
						</div> 								
						<%-- <c:set var="baseLookupCodeASCL" value="IMO" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCodeASCL)}"
							path="assetChartOfDepreciationMasterDTO.assetClass"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" cssClass="form-control" /> --%>

						<label class="col-sm-2 control-label" for=""> <spring:message
								code="asset.depreciationMaster.frequency" /></label>
						<c:set var="baseLookupCodeFRQ" value="${userSession.moduleDeptCode == 'AST' ? 'PRF':'IRF'}" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCodeFRQ)}"
							path="assetChartOfDepreciationMasterDTO.frequency"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" cssClass="form-control" />
					</div>

					<div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-success" id="searchCDM" title='<spring:message code="search.data" text="Search" />'>
							<spring:message code="search.data" text="Search" />
						</button>
						<button type="Reset" class="btn btn-warning" onclick="resetCDM()" title='<spring:message code="reset.msg" text="Reset" />'>
							<spring:message code="reset.msg" text="Reset" />
						</button>
						<button type="button" class="btn btn-blue-2" id="createCDM" title='<spring:message code="add.msg" text="Add" />'>
							<spring:message code="add.msg" text="Add" />
						</button>
					</div>

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered" id="dtCdmHome">
							<thead>
								<tr>

									<th width="30%" align="center"><spring:message
											code="asset.depreciationMaster.groupName" text="" /></th>
									<th width="15%" align="center"><spring:message
											code="asset.depreciationMaster.accountCode" text="" /></th>
									<th width="15%" align="center"><spring:message
											code="asset.depreciationMaster.assetClass" text="" /></th>
									<th width="15%" align="center"><spring:message
											code="asset.depreciationMaster.frequency" text="" /></th>
									<th width="15%" align="center"><spring:message
											code="sor.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${DepreciationList}"
									var="assetChartOfDepreciationMasterDTO">
									<tr>

										<td>${assetChartOfDepreciationMasterDTO.name}</td>
										<td>${assetChartOfDepreciationMasterDTO.accountCodeDesc}</td>
										<td>${assetChartOfDepreciationMasterDTO.assetClassDesc}</td>
										<td>${assetChartOfDepreciationMasterDTO.frequencyDesc}</td>
										<%-- <fmt:parseNumber var="optionCode" integerOnly="true"
											type="number"
											value="${assetChartOfDepreciationMasterDTO.accountCode}" />
										<td>${assetChartOfDepreciationMasterDTO.accountCode}<apptags:lookupField
												items="${accountCodeHead}"
												path="assetChartOfDepreciationMasterDTO.accountCode"
												selectedOption="${optionCode}" showOnlyLabel="true" /></td>

										<fmt:parseNumber var="optionClass" integerOnly="true"
											type="number"
											value="${assetChartOfDepreciationMasterDTO.assetClass}" />
										<td><apptags:lookupField
												items="${command.getLevelData(baseLookupCodeASCL)}"
												path="assetChartOfDepreciationMasterDTO.assetClass"
												selectedOption="${optionClass}" showOnlyLabel="true" /></td>

										<fmt:parseNumber var="optionFrequency" integerOnly="true"
											type="number"
											value="${assetChartOfDepreciationMasterDTO.frequency}" />
										<td><apptags:lookupField
												items="${command.getLevelData(baseLookupCodeFRQ)}"
												path="assetChartOfDepreciationMasterDTO.frequency"
												selectedOption="${optionFrequency}" showOnlyLabel="true" /></td>
												
										<td><c:set var="baseLookupCode" value="${assetChartOfDepreciationMasterDTO.frequency}" />
										<form:select
											path="assetChartOfDepreciationMasterDTO.frequency"
											cssClass="form-control mandColorClass">
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td> --%>
												
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="viewCDM(${assetChartOfDepreciationMasterDTO.groupId})"
												title="View Chart Of Depreciation">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												onClick="editCDM(${assetChartOfDepreciationMasterDTO.groupId})"
												title="Edit Chart Of Depreciation">
												<i class="fa fa-pencil"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>