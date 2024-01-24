<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/incomeAndExpeditureReport.js"
	type="text/javascript"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />	
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>Income And Expenditure Schedule Report</h2>
		</div>
		<apptags:helpDoc url="incomeAndExpenditureSheduleReport.html"
			helpDocRefURL="incomeAndExpenditureSheduleReport.html"></apptags:helpDoc>
		<div class="widget-content padding">
			<form:form action="" class="form-horizontal">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times; </span>
				</button>
				<span id="errorId"></span>
			</div>
				<div class="form-group">
					<label for="date-1493383113506" class="col-sm-2 control-label "><spring:message
							code="" text="Head Type" /><span class="required-control"></span>
					</label>
			
					<div class="col-sm-4">
						<label for="radio-group-1492067297931-0" class="radio-inline "><input
							type="radio" name="reporttype" class="radio-group" id="radio1"
							value="Income" checked onclick="expenditute()"> <spring:message code=""
								text="Income" /> </label> 
							<label for="radio-group-1492067297931-1"
							class="radio-inline"><input type="radio" name="reporttype"
							class="radio-group" id="radio2" value="Expenditures" onclick="expenditute()"> <spring:message
								code="" text="Expenditures"  /></label>
					</div>
				</div>
				<div class="form-group">
					<label for="select-1478760966687" class="col-sm-2 control-label"><spring:message
							code="" text="Primary Head" /><span class="required-control"></span></label>
					<div class="col-sm-4">
						<form:select
							class="form-control mandColorClass chosen-select-no-results"
							path="accountIEDto.primaryAcHeadId" id="primaryHeadId">
							<form:option value="" selected="true">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.listOfprimaryHead}"
								var="accountHeads">
								<form:option value="${accountHeads.key}">${accountHeads.key} - ${accountHeads.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label for="select-1478760966687" class="col-sm-2 control-label"><spring:message
							code="" text="Financial Year" /><span class="required-control"></span></label>
					<div class="col-sm-4">
						<form:select
							class="form-control mandColorClass chosen-select-no-results"
							path="accountIEDto.financialYrId" id="financialYearId">
							<form:option value="" selected="true">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}" var="financeMap">
								<form:option value="${financeMap.key}" code="${financeMap.key}">${financeMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2" onClick="incomeAndExpenditureReport('incomeAndExpenditureSheduleReport.html','incomereport')" title="View Report">
					<spring:message code="" text="View Report"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onClick="incomeExpendituterReset(this)" title="Reset"> <spring:message code="" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
