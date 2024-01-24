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
<script src="js/account/accountBudgetCode.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.budget.code.master.title" text=""></spring:message>
			</h2>
			<apptags:helpDoc url="AccountBudgetCode.html" helpDocRefURL="AccountBudgetCode.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcBudgetCode"
				class="form-horizontal">

				<form:hidden path="objectHeadType" id="objectHeadType" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
			
				<div class="form-group">
					<form:hidden path="prBudgetCodeid" />

					<c:if test="${deptStatus == 'Y'}">
						<label class="col-sm-2 control-label" for="dpDeptid"><spring:message
								code="account.budget.code.master.departmenttype" text="" /></label>

						<div class="col-sm-4">
							<form:select path="dpDeptid"
								class="form-control chosen-select-no-results" id="dpDeptid"
								disabled="${viewMode}">
								<form:option value="">
									<spring:message
										code="account.budget.code.master.selectdepartment" text="" />
								</form:option>
								<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
									<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

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
								<c:forEach items="${listOfTbAcFundMasterItems}"
									varStatus="status" var="fundItem">
									<form:option value="${fundItem.key}" code="${fundItem.key}">${fundItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

				</div>

				<div class="form-group">

					<form:hidden path="prBudgetCodeid" />

				<%-- 	<c:if test="${fieldStatus == 'Y'}">
						<label class="col-sm-2 control-label" for="fieldId"><spring:message
								code="account.budget.code.master.fieldcode" /></label>
						<div class="col-sm-4">
							<form:select id="fieldId" path="fieldId"
								cssClass="form-control chosen-select-no-results"
								disabled="${viewMode}">
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
					</c:if> --%>

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
								<c:forEach items="${listOfTbAcFunctionMasterItems}"
									varStatus="status" var="functionItem">
									<form:option value="${functionItem.key}"
										code="${functionItem.key}">${functionItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

				</div>

				<div class="form-group">

					<form:hidden path="prBudgetCodeid" />
					<label class="col-sm-2 control-label" for="sacHeadId"><spring:message
							code="account.budget.code.master.primaryaccountcode" text="Primary Head" /></label>
					<div class="col-sm-4">
						<form:select id="sacHeadId" path="sacHeadId"
							cssClass="form-control chosen-select-no-results"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message
									code="account.budget.code.master.selectprimaryaccountcode" />
							</form:option>
							<c:forEach items="${listOfPrimaryAcHeadMapMasterItems}"
								varStatus="status" var="accountHeads">
								<form:option value="${accountHeads.key}"
									code="${accountHeads.key}">${accountHeads.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<form:hidden path="prBudgetCodeid" />
					<label class="col-sm-2 control-label"><spring:message
							code="account.budget.code.master.status" text="" /></label>
					<div class="col-sm-4">
						<form:select id="cpdIdStatusFlag" path="cpdIdStatusFlagDup"
							cssClass="form-control">
							<form:option value="">
								<spring:message code="account.budget.code.master.selectstatus"
									text="" />
							</form:option>
							<c:forEach items="${activeDeActiveMap}" varStatus="status"
								var="activeItem">
								<form:option code="${activeItem.lookUpCode}"
									value="${activeItem.lookUpCode}">${activeItem.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchBudgetCodeData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL" value="AccountBudgetCode.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.btn.reset" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
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

