<?xml version="1.0" encoding="UTF-8" standalone="no"?>
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
<script src="js/account/accountBudgetProjectedRevenueEntry.js" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<c:if test="${tbAcBudgetProjectedRevenueEntry.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="budget.projected.revenue.entry.master.title"
				text="" />
		</h2>
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcBudgetProjectedRevenueEntry" name="frmMaster"
			method="POST" action="AccountBudgetProjectedRevenueEntry.html">
			<form:hidden path="secondaryId" id="secondaryId" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" />

			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close"
					onclick="closeErrBox()">
					<span aria-hidden="true">&times;</span>
				</button>
				<ul>
					<li><form:errors path="*" /></li>
				</ul>
			</div>

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<c:set var="count" value="0" scope="page" />

			<ul id="ulId">
				<li>
					<fieldset id="divId" class="clear">
						<div class="form-group">
							<label class="control-label col-sm-2 "><spring:message
									code="budget.projected.revenue.entry.master.budgetyear" text="" /></label>
							<div class="col-sm-4">

								<form:input type="text" path="financialYearDesc"
									class="form-control" id="financialYearDesc" />

							</div>

							<c:if test="${budgetSubTypeStatus == 'Y'}">
								<label class="col-sm-2 control-label "><spring:message
										code="budget.budgetaryrevision.master.budgetsubtype" text="" /></label>

								<div class="col-sm-4">

									<form:input type="text" path="cpdBugsubtypeDesc"
										class="form-control" id="cpdBugsubtypeDesc" />

								</div>
							</c:if>
						</div>
						<div class="form-group">
							<form:hidden path="prProjectionid" />

							<label class="col-sm-2 control-label "><spring:message
									code="budget.projected.revenue.entry.master.department" text="" /></label>

							<div class="col-sm-4">

								<form:input type="text" path="departmentDesc"
									class="form-control" id="departmentDesc" />

							</div>

						</div>

						<form:hidden path="prProjectionid" />
						<div id="prProjectionid" class="">
							<div class="table-overflow-sm" id="budRevTableDivID">
								<table class="table  table-bordered table-striped "
									id="budRevTable">

									<tr>
										<th scope="col" width="55%" style="text-align: center"><spring:message
												code="budget.projected.revenue.entry.master.budgethead"
												text="" /></th>
										<th scope="col" width="15%"><spring:message
												code="budget.projected.revenue.entry.master.budgetprovision"
												text="" /></th>
										<th scope="col" width="15%"><spring:message
												code="budget.projected.revenue.entry.master.revisebudget"
												text="" /></th>
										<th scope="col" width="15%"><spring:message
												code="budget.projected.revenue.entry.master.collectedamount"
												text="" /></th>
									</tr>
									<c:forEach
										items="${tbAcBudgetProjectedRevenueEntry.bugRevenueMasterDtoList}"
										var="prExpList" varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<td><c:set value="${prExpList.prBudgetCodeid}"
													var="budgetCodeId"></c:set> <c:forEach
													items="${accountBudgetCodeMap}" varStatus="status"
													var="pacItem">
													<c:if test="${budgetCodeId eq pacItem.key}">
														<form:input type="text" value="${pacItem.value}" path=""
															class="form-control" id="departmentDesc" />
													</c:if>
												</c:forEach></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="orginalEstamt${count}"
													path="bugRevenueMasterDtoList[0].orginalEstamt"></form:input></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="revisedEstamt${count}"
													path="bugRevenueMasterDtoList[0].revisedEstamt"
													readonly="${viewMode ne 'true'}"></form:input></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="prCollected${count}"
													path="bugRevenueMasterDtoList[0].prCollected"
													readonly="${viewMode ne 'true'}"></form:input></td>
										</tr>
									</c:forEach>

								</table>
							</div>
						</div>

					</fieldset>
				</li>
			</ul>

			<INPUT type="hidden" id="count" value="0" />
			<INPUT type="hidden" id="countFinalCode" value="0" />

			<div class="text-center padding-top-10">
				<spring:url var="cancelButtonURL"
					value="AccountBudgetProjectedRevenueEntry.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>
<c:if test="${tbAcBudgetProjectedRevenueEntry.hasError =='true'}">
	</div>
</c:if>

