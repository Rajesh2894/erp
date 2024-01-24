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
<script src="js/account/accountBudgetoryRevision.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="budget.budgetaryrevision.master.title" text=""></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcBudgetoryRevision"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="budget.budgetaryrevision.master.financialyear" text=""></spring:message>
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

					<label class="col-sm-2 control-label "> <spring:message
							code="budget.budgetaryrevision.master.budgettype" text=""></spring:message>
					</label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="RE" />
						<form:select path="cpdBugtypeId" class="form-control"
							id="cpdBugtypeId" disabled="${viewMode}">
							<form:option value="">
								<spring:message
									code="budget.budgetaryrevision.master.selectbudgettype" text="" />
							</form:option>
							<c:forEach items="${levelMap}" varStatus="status"
								var="levelChild">
								<form:option value="${levelChild.lookUpId}" code="">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="dpDeptid"><spring:message
							code="budget.budgetaryrevision.master.departmenttype" text="" /></label>

					<div class="col-sm-4">
						<form:select path="dpDeptid"
							class="form-control chosen-select-no-results" id="dpDeptid"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message
									code="budget.budgetaryrevision.master.departmentttype" text="" />
							</form:option>
							<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
								<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="prBudgetCodeid"><spring:message
							code="budget.budgetaryrevision.master.budgethead" text="" /></label>
					<div class="col-sm-4">
						<form:select id="prBudgetCodeid" path="prBudgetCodeid"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message
									code="budget.budgetaryrevision.master.selectbudgethead" text="" />
							</form:option>
							<c:forEach items="${list}" varStatus="status" var="activeItem">
								<form:option code="${activeItem.prBudgetCodeid}"
									value="${activeItem.prBudgetCodeid}">${activeItem.prBudgetCode}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="fundId"><spring:message
							code="account.budget.code.master.fundcode" /></label>
					<div class="col-sm-4">
						<form:select id="fundId" path=""
							cssClass="form-control chosen-select-no-results"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="account.budget.code.master.selectfundcode" />
							</form:option>
							<c:forEach items="${fundMap}" varStatus="status" var="fundItem">
								<form:option value="${fundItem.key}" code="${fundItem.key}">${fundItem.value}</form:option>
							</c:forEach>
						</form:select>

					</div>

					<label class="col-sm-2 control-label" for="functionId"><spring:message
							code="account.budget.code.master.functioncode" text="" /></label>
					<div class="col-sm-4">
						<form:select id="functionId" path=""
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
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchBudgetoryRevisionData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountBudgetoryRevision.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>

