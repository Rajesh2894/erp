<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/report/assetSearchReport.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="asset.search.report" text=" Search Report" />
			</h2>
			<apptags:helpDoc url="AssetDetailsReport.html"></apptags:helpDoc>
		</div>
		<div class="pagediv">
			<div class="widget-content padding">
				<form:form id="assetSearch" name="assetSearch"
					class="form-horizontal" action="AssetDetailsReport.html"
					method="post">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
					</div>
					<c:choose>
					<c:when test="${reportType eq 'ADR'}">
<!--------------------------- condition for the asset group and asset type ------------------------------ -->
						<c:set var="baseLookupCodeASG" value="ASG" />
						<c:set var="baseASG"  value="${command.getLevelData(baseLookupCodeASG)}"/>
						<div class="form-group">
						<c:if test="${not empty baseASG}">
						<c:set var="baseLookupCodeASG" value="ASG" />
							<label class="col-sm-2 control-label required-control"
								for="assetgroup"> <spring:message
									code="asset.information.assetgroup" /></label>
							<apptags:lookupField
								items="${baseASG}"
								path="astDetRepDto.assetGroup"
								disabled="false" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false"
								/>
						</c:if>
						<c:set var="baseLookupCodeTNG" value="TNG" />
						<c:set var="baseTNG"  value="${command.getLevelData(baseLookupCodeTNG)}"/>
						<c:if test="${not empty  baseTNG}">
						<!-- <div class="form-group"> -->
						<c:set var="baseLookupCodeTNG" value="TNG" />
							<label for="" class="col-sm-2 control-label "><spring:message
									code="asset.information.assettype" /></label>
							<apptags:lookupField
								items="${baseTNG}"
								path="astDetRepDto.assetType"
								disabled="false" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />
						</c:if>
						</div>
<!--------------------------- condition for the asset group and asset type  end --------------- -->	
					<!--add new prefix  -->
						<div class="form-group">
						<c:set var="baseLookupCodeACL" value="ACL" />
							<label class="col-sm-2 control-label"
								for="assetgroup"> <spring:message
									code="asset.info.assetclass" /></label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeACL)}"
								path="astDetRepDto.assetClass2"
								disabled="false" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false"
								/>
					
						<c:set var="baseLookupCodeASC" value="ASC" />
							<label class="col-sm-2 control-label"
								for="assetgroup"> <spring:message
									code="asset.info.assetClassification" /></label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeASC)}"
								path="astDetRepDto.assetClass1"
								disabled="false" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false"
								showOnlyLabel="applicantinfo.label.title" />
						</div>
						<!--add new prefix  -->
					</c:when>
					<c:otherwise>
					<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="asset.class"
							text="Asset Class" /></label>
							<c:choose>
							<c:when test="${reportType ne 'ROL'}">
							<div class="col-sm-4">					
									<form:select id="assetClass2" path=""
										cssClass="form-control mandColorClass" 
										data-rule-required="true" onchange="populateList();" >
										<option value="-1">
									<spring:message code="asset.report.selectassetclass" text="Select Asset Class"/> 
								    </option>	
										<c:forEach items="${imoPrefixList}" varStatus="status"
											var="imoPrefixList">
											<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
											<form:option value="${imoPrefixList.lookUpId}"
												code="${imoPrefixList.lookUpCode}">${imoPrefixList.lookUpDesc}</form:option>
												</c:if>
												<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
											<form:option value="${imoPrefixList.lookUpId}"
												code="${imoPrefixList.lookUpCode}">${imoPrefixList.descLangSecond}</form:option>
												</c:if>
										</c:forEach> 
									</form:select>						
					</div>
					</c:when>
							<c:otherwise>
		<!------------------- this is only for land register report ------------------------------>
							<div class="col-sm-4">					
									<form:select id="assetClass2" path=""
										cssClass="form-control mandColorClass" 
										data-rule-required="true">	
										<c:forEach items="${imoPrefixList}" varStatus="status"
											var="imoPrefixList">
											<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
											<form:option value="${imoPrefixList.lookUpId}"
												code="${imoPrefixList.lookUpCode}">${imoPrefixList.lookUpDesc}</form:option>
												</c:if>
												<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
											<form:option value="${imoPrefixList.lookUpId}"
												code="${imoPrefixList.lookUpCode}">${imoPrefixList.descLangSecond}</form:option>
												</c:if>
										</c:forEach> 
										
									</form:select>						
					</div>
							
							
							</c:otherwise>
							</c:choose>
					</div>	
							<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
									code="asset.information.assetcode" /></label>
							<div class="col-sm-4">
							<select id="assetCodeselected" name="assetCodeselected" 
								data-content="" class="form-control chosen-select-no-results">	
							<option value="-1">
									<spring:message code="asset.report.selectassetcode" text="Select Asset Code"/> 
								</option>								
						</select>
						
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
							code="asset.report.financialyear" /></label>
									<div class="col-sm-4">						
									<form:select id="faYearId" path=""
										cssClass="form-control mandColorClass" 
										data-rule-required="true">
										<c:forEach items="${financialMap}" varStatus="status"
											var="financial">
											<form:option value="${financial.key}"
												code="${financial.key}">${financial.value}</form:option>
										</c:forEach>
									</form:select>						
					</div>
	
							</div>
							</c:otherwise>
							</c:choose>
					<div class="text-center clear padding-10">
                          <c:if test="${reportType eq 'RIP'}">
						<button class="btn btn-blue-2  registerImmovableId" id=regisId 
							onclick="registerImmovable(this);" type="button">
							<i class="button-input"></i>
							<spring:message code="asset.report.search" text="Search" />
						</button>
						</c:if>
						<c:if test="${reportType eq 'ADR'}">
						<button class="btn btn-blue-2  search" id="searchReports"
							onclick="searchReport(this);" type="button">
							<i class="button-input"></i>
							<spring:message code="asset.report.search" text="Search" />
						</button>
						</c:if>
						<c:if test="${reportType eq 'ROL'}">
						<button class="btn btn-blue-2  search" id="landRegisterId"
							onclick="landRegister(this);" type="button">
							<i class="button-input"></i>
							<spring:message code="asset.report.search" text="Search" />
						</button>
						</c:if>
						<apptags:backButton url="AssetDetailsReport.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>