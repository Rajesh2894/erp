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
					<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="asset.class"
							text="Asset Class" /></label>
							<div class="col-sm-4">						
									<form:select id="assetClass1" path=""
										cssClass="form-control mandColorClass" 
										data-rule-required="true">
										<c:forEach items="${prifixList}" varStatus="status"
											var="prifixList">
											<form:option value="${prifixList.lookUpId}"
												code="${prifixList.lookUpCode}">${prifixList.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>						
					</div>
							<label class="col-sm-2 control-label required-control"><spring:message
							code="asset.report.financialyear"
							 /></label>
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
					<div class="text-center clear padding-10">
						<button class="btn btn-blue-2  registerMovableId" id=regisMovId 
							onclick="registerMovable(this);" type="button">
							<i class="button-input"></i>
							<spring:message code="asset.report.search" text="Search" />
						</button>
						
					
						<apptags:backButton url="AssetDetailsReport.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>