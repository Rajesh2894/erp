<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/asset/report/assetReportHome.js"
	type="text/javascript"></script>
<!-- 	<script type="text/javascript" src="js/asset/report/assetSearchReport.js"></script> -->

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
	<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	
	 <div class="content">
     <div class="widget">
			<div class="widget-header">
				<h2>
				<spring:message code="asset.report" />
					
				</h2>
			</div>
			<div class="widget-content padding">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<form:form action="AssetDetailsReport.html"
					 class="form-horizontal"
					id="AssetDetailsReportId">
					<input type="hidden" value="${reportType}" id="reportTypeHidden">
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"  for="reportTypeId"><spring:message code="asset.report.types" text="Report Type" /></label>
						<div class="col-sm-4">
								<form:select path="" class="form-control mandColorClass" data-rule-required="true" id="reportTypeId">
								<form:option value="">
								<spring:message code="asset.report.selecttype" text="Select Report Type" />
								</form:option>
								<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
								<c:forEach items="${reportTypeLookUps}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
								</c:forEach>
								</c:if>
								<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
								<c:forEach items="${reportTypeLookUps}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.descLangSecond}</form:option>
								</c:forEach>
								</c:if>
							</form:select>
						</div>
						</div>
								
					<%-- <div class="text-center">
					<button type="button" class="btn btn-success btn-submit" onclick="viewReport(this)"><spring:message code="asset.report.viewreport" text="View Report" /></button>
					<button type="button" class="btn btn-warning resetSearch" onclick="window.location.href = 'AssetDetailsReport.html'"><spring:message code="asset.report.reset" text="Reset" /></button>
					</div> --%>
				<div>

</div>
</form:form>
			</div>
		</div>
	 
	 </div>
	 
    </div>