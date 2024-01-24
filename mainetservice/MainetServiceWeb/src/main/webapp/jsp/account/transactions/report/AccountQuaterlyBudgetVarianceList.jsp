<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/quaterlyBudgetVarianceReport.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="quaterly.Budget.Variance.Report" text="Quaterly Budget Variance Report" /></h2>
		</div>
		<apptags:helpDoc url="QuaterlyBudgetVarianceReport.html"
			helpDocRefURL="QuaterlyBudgetVarianceReport.html"></apptags:helpDoc>
		<div class="widget-content padding">
			<form:form action="QuaterlyBudgetVarianceReport.html" class="form-horizontal form" name="" id="BudgetVariance">
			   <div
					class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
						<span id="errorId"></span>
				</div>	
				
				<div class="form-group">
					<label for="date-1493383113506" class="col-sm-2 control-label ">
					<spring:message code="account.budget.code.master.financialyear" text="FinancialYear" /><span class="required-control"></span>
					</label>
					<div class="col-sm-4">
						<form:select class="form-control mandColorClass chosen-select-no-results" path="" id="financialYearId">
							<form:option value="" selected="true">
								<spring:message code="advance.management.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}" var="financeMap">
								<form:option value="${financeMap.key}" code="${financeMap.key}">${financeMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2" onClick="budgetVarianceReport('QuaterlyBudgetVarianceReport.html','quaterlybudgetreport')" title="View Report ">
					<spring:message code="account.financial.view.report" text="View Report"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onClick="varianceBudgetReset()"><spring:message code="account.btn.reset" text="Reset"/></button>
					<apptags:backButton url="AdminHome.html" ></apptags:backButton>
			  </div>
			</form:form>
		</div>
	</div>
</div>