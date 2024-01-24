<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/accountBudgetProjectedRevenueEntry.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="budget.projected.revenue.entry.master.title"
					text=""></spring:message>
			</h2>
		<apptags:helpDoc url="AccountBudgetProjectedRevenueEntry.html" helpDocRefURL="AccountBudgetProjectedRevenueEntry.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcBudgetProjectedRevenueEntry"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
                 <input type="hidden" value="${deptCheck}" id="deptCheck">
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="budget.projected.revenue.entry.master.budgetyear" text="Budget Year"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select id="faYearid" path="faYearid"
							class="form-control mandColorClass" maxLength="200">
							<c:forEach items="${aFinancialYr}" varStatus="status"
								var="financeMap">
								<form:option value="${financeMap.key}" code="${financeMap.key}">${financeMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<c:if test="${budgetSubTypeStatus == 'Y'}">
						<label class="col-sm-2 control-label "><spring:message
								code="budget.budgetaryrevision.master.budgetsubtype" text="" /></label>

						<div class="col-sm-4">
							<form:select path="cpdBugsubtypeId" class="form-control"
								id="cpdBugsubtypeId" disabled="${viewMode}">
								<form:option value="">
									<spring:message
										code="budget.budgetaryrevision.master.selectbudgetsubtype"
										text="" />
								</form:option>
								<c:forEach items="${bugSubTypelevelMap}" varStatus="status"
									var="levelChild">
									<form:option code="${levelChild.lookUpCode}"
										value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="dpDeptid"><spring:message
							code="budget.projected.expenditure.master.department" text="" /></label>

					<div class="col-sm-4">
						<form:select path="dpDeptid"
							class="form-control chosen-select-no-results" id="dpDeptid"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="budget.allocation.master.departmentttype" text="Select Department" />
							</form:option>
							<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
								<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control" for="prBudgetCodeid"><spring:message
							code="budget.projected.revenue.entry.master.budgethead" text="" /></label>
					<div class="col-sm-4">
						<form:select id="prBudgetCodeid" path="prBudgetCodeid"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message
									code="budget.projected.revenue.entry.master.selectbudgetheads"
									text="" />
							</form:option>

							<c:forEach items="${budrevMap}" varStatus="status"
								var="budrevItem">
								<form:option value="${budrevItem.key}" code="${budrevItem.key}">${budrevItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">

					<c:if test="${fundStatus == 'Y'}">
						<label class="col-sm-2 control-label" for="fundId"><spring:message
								code="account.budget.code.master.fundcode" /></label>
						<div class="col-sm-4">
							<form:select id="fundId" path="fundId"
								cssClass="form-control chosen-select-no-results"
								disabled="${viewMode}">
								<form:option value="">
									<spring:message
										code="account.budget.code.master.selectfundcode" />
								</form:option>
								<c:forEach items="${fundMap}" varStatus="status" var="fundItem">
									<form:option value="${fundItem.key}" code="${fundItem.key}">${fundItem.value}</form:option>
								</c:forEach>
							</form:select>

						</div>
					</c:if>

					<c:if test="${functionStatus == 'Y'}">
						<label class="col-sm-2 control-label" for="functionId"><spring:message
								code="account.budget.code.master.functioncode" text="" /></label>
						<div class="col-sm-4">
							<form:select id="functionId" path="functionId"
								cssClass="form-control chosen-select-no-results"
								disabled="${viewMode}">
								<form:option value="">
									<spring:message
										code="account.budget.code.master.selectfunctioncode" />
								</form:option>
								<c:forEach items="${functionMap}" varStatus="status"
									var="functionItem">
									<form:option value="${functionItem.key}"
										code="${functionItem.key}">${functionItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<label class="control-label col-sm-2 required-control"><spring:message
							code="account.budget.code.master.fieldcode" /></label>
					<div class="col-sm-4">
						<form:select id="fieldId" path="fieldId"
							cssClass="form-control chosen-select-no-results"
							disabled="${viewMode}"
							data-rule-required="false">
							<form:option value="">
								<spring:message
									code="account.budget.code.master.selectfieldcode" />
							</form:option>
							<c:forEach items="${listOfTbAcFieldMasterItems}"
								varStatus="status" var="fieldItem">
								<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchBudgetRevenueData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountBudgetProjectedRevenueEntry.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
					<c:if test="${deptCheck == 'Y'}">
					<button type="button" class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
					</c:if>
					<button type="button" class="btn btn-primary"
						onclick="exportTemplate();">
						<spring:message code="account.deposit.export.import" text="Export/Import" />
					</button>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>

