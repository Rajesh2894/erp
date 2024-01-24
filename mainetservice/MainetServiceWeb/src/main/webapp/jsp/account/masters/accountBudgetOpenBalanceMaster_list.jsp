<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>

<script src="js/account/accountBudgetOpenBalanceMaster.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="account.budgetopenmaster.opening.balance.sheet.entry" text="Opening Balances Entry"></spring:message>
			</h2>
		<apptags:helpDoc url="AccountBudgetOpenBalanceMaster.html" helpDocRefURL="AccountBudgetOpenBalanceMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcBugopenBalance"
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
					<label class="col-sm-2 control-label"> <spring:message
							code="account.budgetopenmaster.financialyear" text=""></spring:message>
					</label>

					<div class="col-sm-4">
						<form:select id="faYearid" path="faYearid"
							cssClass="form-control " maxLength="200" disabled="${viewMode}">
							<c:forEach items="${financeMap}" varStatus="status"
								var="financeMap">
								<form:option value="${financeMap.key}" code="${financeMap.key}">${financeMap.value}</form:option>
							</c:forEach>
						</form:select>

					</div>

					<label class="col-sm-2 control-label"> <spring:message
							code="account.budgetopenmaster.headcategory" text="Head Category"></spring:message>
					</label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="COA" />
						<form:select path="opnBalType" class="form-control "
							id="opnBalType" disabled="${viewMode}">
							<form:option value="">
								<spring:message code="account.budgetopenmaster.selectheadcategory" text="Select Head Category" />
							</form:option>

							<c:forEach items="${levelMap}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="sacHeadId"><spring:message
							code="account.budgetopenmaster.accountheads" text="Account Head" /></label>
					<div class="col-sm-4">
						<form:select id="sacHeadId" path=""
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="account.budgetopenmaster.selectaccounthead" text="Select Account Head" />
							</form:option>
							<c:forEach items="${pacHeadMap}" varStatus="status"
								var="activeItem">
								<form:option code="${activeItem.key}" value="${activeItem.key}">${activeItem.value}</form:option>
							</c:forEach>

						</form:select>
					</div>

					<label class="control-label col-sm-2 "><spring:message
							code="account.budgetopenmaster.finalized" text="Finalized" /> </label>

					<div class="col-sm-4">
						<form:select path="" class="form-control" id="status"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="account.select" text="Select" />
							</form:option>
							<c:forEach items="${statusLookUpList}" varStatus="status" var="levelChild">		
							<c:if test="${levelChild.lookUpCode eq 'A'}">
							<form:option value="${levelChild.lookUpCode}" code="${levelChild.lookUpCode}">Y</form:option>
							</c:if>	
							<c:if test="${levelChild.lookUpCode eq 'I'}">
							<form:option value="${levelChild.lookUpCode}" code="${levelChild.lookUpCode}">N</form:option>
							</c:if>	
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchBudgetOpeningBalanceData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountBudgetOpenBalanceMaster.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="reset.msg" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.create" text="Add" />
					</button>
					<button type="button" class="btn btn-primary" onclick="exportTemplate();">
						<spring:message code="account.bankmaster.export.import" text="Export/Import" />
					</button>
				</div>
				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>

