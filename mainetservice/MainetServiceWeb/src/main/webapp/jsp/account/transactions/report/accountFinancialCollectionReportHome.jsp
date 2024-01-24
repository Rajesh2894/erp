<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountCollectionSummaryReport.js"
	type="text/javascript"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
	$(function() {
		$("#toDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		
		$("#fromDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
	});
		</script>
	
	
	<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	
	 <div class="content">
     <div class="widget">
			<div class="widget-header">
				<h2>
					Collection Reports
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

				<form:form action="AccountCollectionSummaryReport.html"
					modelAttribute="collectionReportDto" class="form-horizontal"
					id="AccountCollectionSummaryReportfrm">
					<input type="hidden" value="${reportType}" id="reportTypeHidden">
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"  for="reportTypeId"><spring:message code="account.financial.report.type" text="Report Type" /></label>
						<div class="col-sm-4">
								<form:select path="reportTypeId" class="form-control mandColorClass" data-rule-required="true" id="reportTypeId">
								<form:option value="">
								<spring:message code="account.financial.sel.report.type" text="Select Report Type" />
								</form:option>
								<c:forEach items="${reportTypeLookUps}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
						</div>
								
						<div class="form-group">
							<label for="fromDateId" class="col-sm-2 control-label required-control">From Date</label>
							<div class="col-sm-4">
							<div class="input-group">
							<form:input path="fromDate" id="fromDateId"	cssClass="mandColorClass form-control" data-rule-required="true" />
							<label class="input-group-addon mandColorClass" for="fromDateId"><i class="fa fa-calendar"></i> </label>
							</div>
							</div>
							<label for="toDateId" class="col-sm-2 control-label required-control"><spring:message code="budget.reappropriation.authorization.todate" text="To Date" /></label>
							<div class="col-sm-4">
								<div class="input-group"><form:input path="toDate" id="toDateId" cssClass="mandColorClass form-control datepicker" data-rule-required="true" />
									<label class="input-group-addon mandColorClass" for="toDateId"><i class="fa fa-calendar"></i> </label>
								</div>
							</div>

						</div>
					

					
					
					<div class="text-center">
					<button type="button" class="btn btn-success btn-submit" onclick="viewReport(this)" title="View Report">View Report</button>
					<button type="button" class="btn btn-warning resetSearch" onclick="window.location.href = 'AccountCollectionSummaryReport.html'" title="Reset">Reset</button>
					</div>
				<div>

</div>
</form:form>
			</div>
		</div>
	 
	 </div>
	 
    </div>