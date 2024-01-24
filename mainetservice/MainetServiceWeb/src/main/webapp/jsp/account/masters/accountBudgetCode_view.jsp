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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><!-- 
<script type="text/javascript" src="js/account/accountBudgetCode.js" /> -->
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.tooltip-inner {
	text-align: left;
}
</style>

<c:if test="${tbAcBudgetCode.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="account.budget.code.master.title" text="" />
		</h2>
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcBudgetCode" name="frmMaster" method="POST"
			action="AccountBudgetCode.html">
			<form:hidden path="" id="secondaryId" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="cpdIdStatusFlagDup" id="cpdIdStatusFlagDup" />

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
						<%-- <div class="form-group">

							<c:if test="${budgetSubTypeStatus == 'Y'}">
								<label class="col-sm-2 control-label "><spring:message
										code="account.budget.code.master.budgetsubtype" text="" /></label>

								<div class="col-sm-4">
									<form:input type="text" path="cpdBugsubtypeDesc"
										class="form-control" id="cpdBugsubtypeDesc" />
								</div>
							</c:if>
						</div> --%>
				<%-- 		<div class="form-group">
							<form:hidden path="prBudgetCodeid" />
							<c:if test="${deptStatus == 'Y'}">
								<label class="col-sm-2 control-label "><spring:message
										code="account.budget.code.master.departmenttype" text="" /></label>

								<div class="col-sm-4">
									<form:input type="text" path="departmentDesc"
										class="form-control" id="departmentDesc" />
								</div>
							</c:if>

							<c:if test="${fundStatus == 'Y'}">
								<label class="control-label col-sm-2 "><spring:message
										code="account.budget.code.master.fundcode" /></label>
								<div class="col-sm-4">

									<c:set value="${tbAcBudgetCode.fundId}" var="fundId"></c:set>
									<c:forEach items="${listOfTbAcFundMasterItems}"
										varStatus="status" var="pacItem">
										<c:if test="${fundId eq pacItem.key}">
											<form:input type="text" value="${pacItem.value}" path=""
												class="form-control" id="fundId" />
										</c:if>
									</c:forEach>

								</div>
							</c:if>
						</div> --%>

						<%-- <div class="form-group">

							<form:hidden path="prBudgetCodeid" />

							<c:if test="${fieldStatus == 'Y'}">
								<label class="control-label col-sm-2 "><spring:message
										code="account.budget.code.master.fieldcode" /></label>
								<div class="col-sm-4">

									<c:set value="${tbAcBudgetCode.fieldId}" var="fieldId"></c:set>
									<c:forEach items="${listOfTbAcFieldMasterItems}"
										varStatus="status" var="pacItem">
										<c:if test="${fieldId eq pacItem.key}">
											<form:input type="text" value="${pacItem.value}" path=""
												class="form-control" id="fundId" />
										</c:if>
									</c:forEach>
								</div>
							</c:if>

						</div>
 --%>
						<form:hidden path="prBudgetCodeid" />
						<div id="prBudgetCodeid" class="">
							<div class="table-overflow-sm" id="budRevTableDivID">
								<table class="table table-bordered table-striped "
									id="budRevTable">

									<tr>
										<th scope="col" width="20%"><spring:message
												code="account.budget.code.master.functioncode" text="" /></th>
										<th scope="col" width="30%" style="text-align: center"><spring:message
												code="budget.allocation.master.primaryaccountcode" text="Primary Head" /></th>
										<th scope="col" width="10%"><spring:message
												code="account.budget.code.master.status" text="" /></th>
										<th scope="col" width="40%"><spring:message code="account.budget.head.code.description"
												text="Budget Head Code - Description" />
									</tr>


									<c:forEach items="${tbAcBudgetCode.budgCodeMasterDtoList}"
										var="prBudgList" varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<td><c:set value="${prBudgList.functionId}"
													var="functionId"></c:set> <c:forEach
													items="${listOfTbAcFunctionMasterItems}" varStatus="status"
													var="functionItem">
													<c:if test="${functionId eq functionItem.key}">
														<form:input type="text" value="${functionItem.value}"
															path="" class="form-control" id="functionId" />
													</c:if>
												</c:forEach></td>
											<td><c:set value="${prBudgList.sacHeadId}"
													var="sacHeadId"></c:set> <c:forEach
													items="${listOfPrimaryAcHeadMapMasterItems}"
													varStatus="status" var="pacItem">
													<c:if test="${sacHeadId eq pacItem.key}">
														<form:input type="text" value="${pacItem.value}" path=""
															class="form-control" id="pacHeadId0" />
													</c:if>
												</c:forEach></td>
											<td><form:input type="text"
													path="budgCodeMasterDtoList[0].statusDesc"
													class="form-control" id="statusDesc" /></td>
											<td><form:textarea id="prBudgetCode0"
													path="budgCodeMasterDtoList[0].prBudgetCode"
													class="form-control" maxLength="500" /></td>

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
				<spring:url var="cancelButtonURL" value="AccountBudgetCode.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>
<c:if test="${tbAcBudgetCode.hasError =='true'}">
	</div>
</c:if>

