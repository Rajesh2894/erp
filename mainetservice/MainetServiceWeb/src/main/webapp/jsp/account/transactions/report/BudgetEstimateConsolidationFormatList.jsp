<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/account/budgetEstimateConsolidationFormat.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="Budget.Estimate.Consolidation.Format" text="Budget Estimate Consolidation Format"></spring:message>
		  </h2>
			<apptags:helpDoc url="budgetEstimateConsolidationFormat.html"
				helpDocRefURL="budgetEstimateConsolidationFormat.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="" class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
				
				<label for="date-1493383113506" class="col-sm-2 control-label "><spring:message
					code="account.budget.code.master.budgettype" text="Budget Type" /><span class="required-control"></span></label>
					<div class="col-sm-4">
						<label for="radio-group-1492067297931-0" class="radio-inline ">
						<input type="radio" name="reporttype" class="radio-group" id="radio1"
						value="Receipt" checked> <spring:message code="accounts.receipt" text="Receipt" /> </label> 
					<label for="radio-group-1492067297931-1" class="radio-inline">
					<input type="radio" name="reporttype" class="radio-group" id="radio2" value="Payment"> 
					<spring:message code="account.budgetprojectedexpendituremaster.payment" text="Payment"  /></label>
					</div>
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="budget.additionalsupplemental.master.financialyear" text=""></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select id="faYearId" path=""
							class="form-control mandColorClass" >
							<form:option value="" selected="true">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}" var="financeMap">
								<form:option value="${financeMap.key}" code="${financeMap.key}">${financeMap.value}</form:option>
							</c:forEach>
						</form:select>
						</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2" onClick="budgetReport('budgetEstimateConsolidationFormat.html','report')" title="View Report">
					<spring:message code="account.financial.view.report" text="View Report"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onClick="budgetReset(this)"  title="Reset"> <spring:message code="account.btn.reset" text="Reset"/>
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
	  </div>
	</div>
</div>
