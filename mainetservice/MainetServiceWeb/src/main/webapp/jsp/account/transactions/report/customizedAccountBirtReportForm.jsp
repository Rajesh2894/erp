<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="js/account/customizedAccountBirtReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="acc.birt.customize.reports"
					text="Customized Account Reports" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="acc.report.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="customizedAccountBirtReport.html" cssClass="form-horizontal"
				id="customizedAccountBirtReports">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="acc.report.type" text="Report Type" /></label>
                        <div class="col-sm-4">
						<form:select path="reportType"
							id="reportType"
							class="form-control chosen-select-no-results"
							data-rule-required="true" onChange="showReportType()">

<form:option value="0"><spring:message code="acc.report.select" text="Select" /></form:option>
<form:option value="A"><spring:message code="acc.report.Advance.Payment.Interest.Calculation" text="Advance Payment Interest Calculations" /></form:option>	
<form:option value="A1"><spring:message code="acc.report.Ageing.Analysis.Outstanding.Invoices" text="Ageing Analysis For Outstanding Invoices" /></form:option>	
<form:option value="A2"><spring:message code="acc.report.Cheque.Book.Print" text="Cheque Book Print Report" /></form:option>	
<form:option value="A3"><spring:message code="acc.report.Deposit.Forfeiture.Register" text="Deposit Forfeiture Register" /></form:option>	
<form:option value="A4"><spring:message code="acc.report.MIS.Payments" text="MIS Report Payments" /></form:option>	
<form:option value="A5"><spring:message code="acc.report.MIS.Receipts" text="MIS Report Receipts" /></form:option>	
<form:option value="A6"><spring:message code="acc.report.Monthly.Summary.Payments" text="Monthly Accounts Summary Payments" /></form:option>
<form:option value="A7"><spring:message code="acc.report.Monthly.Summary.Receipt" text="Monthly Accounts Summary Receipt" /></form:option>
<form:option value="B"><spring:message code="acc.report.balance.sheet" text="Balance Sheet Reports" /></form:option>

		</form:select>
					</div>
</div>
			<div class="form-group" id="SubType" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="acc.sub.report.type" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType" class="form-control chosen-select-no-results"
							data-rule-required="true">

<form:option value="0"><spring:message code="acc.report.select" text="Select" /></form:option>
<form:option value="BN"><spring:message code="acc.report.NMAM_BLSH" text="NMAM Balance Sheet" /></form:option>
<form:option value="B1"><spring:message code="acc.report.balance.sheet1" text="Balance Sheet 1 " /></form:option>
<form:option value="B2"><spring:message code="acc.report.balance.sheet2" text="Balance Sheet 2 " /></form:option>
<form:option value="B3"><spring:message code="acc.report.balance.sheet3" text="Balance Sheet 3 " /></form:option>
<form:option value="B4"><spring:message code="acc.report.balance.sheet4" text="Balance Sheet 4" /></form:option>
<form:option value="B5"><spring:message code="acc.report.balance.sheet5" text="Balance Sheet 5 " /></form:option>
<form:option value="B6"><spring:message code="acc.report.balance.sheet6" text="Balance Sheet 6 " /></form:option>
<form:option value="B7"><spring:message code="acc.report.balance.sheet7" text="Balance Sheet 7 " /></form:option>
<form:option value="B8"><spring:message code="acc.report.balance.sheet8" text="Balance Sheet 8 " /></form:option>
<form:option value="B9"><spring:message code="acc.report.balance.sheet9" text="Balance Sheet 9 " /></form:option>
<form:option value="B10"><spring:message code="acc.report.balance.sheet10" text="Balance Sheet 10 " /></form:option>
<form:option value="B11"><spring:message code="acc.report.balance.sheet11" text="Balance Sheet 11 " /></form:option>
<form:option value="B12"><spring:message code="acc.report.balance.sheet12" text="Balance Sheet 12 " /></form:option>
<form:option value="B13"><spring:message code="acc.report.balance.sheet13" text="Balance Sheet 13 " /></form:option>
<form:option value="B14"><spring:message code="acc.report.balance.sheet14" text="Balance Sheet 14 " /></form:option>
 <form:option value="B15"><spring:message code="acc.report.balance.sheet15" text="Balance Sheet 15 " /></form:option>
<form:option value="B16"><spring:message code="acc.report.balance.sheet16" text="Balance Sheet 16 " /></form:option>
<form:option value="B17"><spring:message code="acc.report.balance.sheet17" text="Balance Sheet 17 " /></form:option>
<form:option value="B18"><spring:message code="acc.report.balance.sheet18" text="Balance Sheet 18 " /></form:option>
<form:option value="B18A"><spring:message code="acc.report.balance.sheet18A" text="Balance Sheet 18A " /></form:option>
<form:option value="B19"><spring:message code="acc.report.balance.sheet19" text="Balance Sheet 19 " /></form:option>
<form:option value="B20"><spring:message code="acc.report.balance.sheet20" text="Balance Sheet 20 " /></form:option>

		


						</form:select>
					</div>
				</div>
			
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveBirtForm(this)">
						<spring:message code="acc.report.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform"
					onclick="window.location.href='customizedAccountBirtReport.html'">
						<spring:message code="account.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="acc.report.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>






