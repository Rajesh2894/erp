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
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script src="js/account/interBankTransactionsReportReport.js"
	type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2> <spring:message code="inter.Bank.Transactions.Report" text="Inter Bank Transactions Report" /></h2>
			<apptags:helpDoc url="incomeAndExpenditureSheduleReport.html"
				helpDocRefURL="incomeAndExpenditureSheduleReport.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="interBankTransactionsReport.html"
				class="form-horizontal" name="interBankTransactionsReport"
				id="interBankTransactionsReportId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message code="from.date.label" text="From Date" />
						</label>


					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="fromDateId"
								cssClass="mandColorClass form-control datepicker"
								data-rule-required="true" maxlength="10" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30" path=" "> </label>
						</div>
					</div>
					<label for="" class="col-sm-2 control-label required-control"><spring:message code="to.date.label" text="To Date" />
						</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="toDateId"
								cssClass="mandColorClass form-control datepicker"
								data-rule-required="true" maxlength="10" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30" path=" "> </label>
						</div>
					</div>
				</div>
				<div class="text-center margin-top-15">
					<button type="button" class="btn btn-blue-2"
						title="View Report"
						onClick="viewReport('interBankTransactionsReport.html','viewreport')">
						<i class="fa fa-print padding-right-5" aria-hidden="true"></i> <spring:message code="view.report" text="View Report" />
						
					</button>
					<button type="reset" class="btn btn-warning" title="Reset"
						onClick="interBankReset(this)">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message code="account.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>

