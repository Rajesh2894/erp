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
<script src="js/account/accountBudgetAdditionalSupplemental.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="budget.additionalsupplemental.master.title"
					text=""></spring:message>
			</h2>
			<apptags:helpDoc url="AccountBudgetAdditionalSupplemental.html" helpDocRefURL="AccountBudgetAdditionalSupplemental.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action=""
				modelAttribute="tbAcBudgetAdditionalSupplemental"
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
							code="budget.additionalsupplemental.master.financialyear" text=""></spring:message>
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

					<c:if test="${budgetTypeStatus == 'Y'}">
						<label class="col-sm-2 control-label "> <spring:message
								code="budget.additionalsupplemental.master.budgettype" text=""></spring:message>
						</label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="REX" />
							<form:select path="cpdBugtypeId" class="form-control "
								id="cpdBugtypeId" disabled="${viewMode}">
								<form:option value="">
									<spring:message
										code="budget.additionalsupplemental.master.selectbudgettype"
										text="" />
								</form:option>
								<c:forEach items="${levelMap}" varStatus="status"
									var="levelChild">
									<form:option value="${levelChild.lookUpId}" code="">${levelChild.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="dpDeptid"><spring:message
							code="budget.additionalsupplemental.master.departmenttype"
							text="" /></label>

					<div class="col-sm-4">
						<form:select path="dpDeptid"
							class="form-control chosen-select-no-results" id="dpDeptid"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message
									code="budget.additionalsupplemental.master.selectbudgettype"
									text="" />
							</form:option>
							<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
								<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="prBudgetCodeid"><spring:message
							code="budget.additionalsupplemental.master.budgethead" text="" /></label>
					<div class="col-sm-4">
						<form:select id="prBudgetCodeid" path="prBudgetCodeid"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message
									code="budget.additionalsupplemental.master.selectbudgethead"
									text="" />
							</form:option>
							<c:forEach items="${addSupMap}" varStatus="status"
								var="addSupMap">
								<form:option value="${addSupMap.key}" code="${addSupMap.key}">${addSupMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchBudgetAdditionalSupplementalData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountBudgetAdditionalSupplemental.html" />
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

