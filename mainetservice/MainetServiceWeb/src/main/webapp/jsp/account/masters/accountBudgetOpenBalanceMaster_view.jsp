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
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountBudgetOpenBalanceMaster.js"></script>

<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}      
	});
</script>

<c:if test="${tbAcBugopenBalance.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>

<div class="widget" id="widget">
	<div class="widget-header">

		<h2>
			<spring:message code="account.budgetopenmaster.title" />
		</h2>
	</div>

	<div class="widget-content padding">

		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcBugopenBalance" name="frmMaster" method="POST"
			action="AccountBudgetOpenBalanceMaster.html">
			<form:hidden path="secondaryId" id="secondaryId" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />


			<form:hidden path="hasError" />
			<form:hidden path="opnId" />
			<form:hidden path="index" id="index" />
			<form:hidden path="flagFlzdDup" id="flagFlzdDup" />
			<input type="hidden" value="${viewMode}" id="test" />

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
									code="account.budgetopenmaster.financialyear" text="" /></label>
							<div class="col-sm-4">

								<form:input type="text" path="financialYearDesc"
									class="form-control" id="financialYearDesc" />

							</div>

							<label class="control-label col-sm-2 "><spring:message
									code="account.budgetopenmaster.headcategory" /> </label>
							<div class="col-sm-4">

								<c:set var="baseLookupCode" value="DCR" />

								<form:input type="text" path="opnBalTypeDesc"
									class="form-control" id="opnBalTypeDesc" />

							</div>
						</div>

						<div class="form-group">

							<c:if test="${fundStatus == 'Y'}">
								<label class="control-label col-sm-2 "><spring:message
										code="account.budgetopenmaster.fundcode" text="" /></label>
								<div class="col-sm-4">
									<c:set value="${tbAcBugopenBalance.fundId}" var="fundId"></c:set>
									<c:forEach items="${listOfTbAcFundMasterItems}"
										varStatus="status" var="pacItem">
										<c:if test="${fundId eq pacItem.key}">
											<form:input type="text" value="${pacItem.value}" path=""
												class="form-control" id="fundId" />
										</c:if>
									</c:forEach>
								</div>
							</c:if>

							<c:if test="${fieldStatus == 'Y'}">
								<label class="control-label col-sm-2 "><spring:message
										code="account.budgetopenmaster.fieldcode" text="" /></label>
								<div class="col-sm-4">
									<c:set value="${tbAcBugopenBalance.fieldId}" var="fieldId"></c:set>
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

						<form:hidden path="opnId" />
						<div id="opnId" class="">
							<div class="table-overflow-sm" id="opnBalTableDivID">
								<table class="table table-bordered table-condensed"
									id="opnBalTable">

									<tr>
										<th scope="col" width="50%" style="text-align: center"><spring:message
												code="account.budgetopenmaster.accountheads" text="" /></th>
										<th scope="col" width="25%"><spring:message
												code="account.budgetopenmaster.openingbalance" text="" /><span
											class="mand"></span></th>
										<th scope="col" width="15%"><spring:message code="acc.DR/CR"
												text="DR/CR" /><span class="mand"></span></th>
										<th scope="col" width="15%"><spring:message
												code="account.budgetopenmaster.finalized" text="" />&nbsp;
											&nbsp;<label class="margin-left-10" for="flag"><input
												type="checkbox" id="flag">
											<spring:message code="account.budgetopenmaster.selectall"
													text="" /></label></th>
									</tr>

									<c:forEach items="${tbAcBugopenBalance.bugReappMasterDtoList}"
										var="prBudgList" varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<td><c:set value="${prBudgList.sacHeadId}"
													var="sacHeadId"></c:set> <c:forEach
													items="${bugpacsacHeadList}" varStatus="status"
													var="pacItem">
													<c:if test="${sacHeadId eq pacItem.key}">
														<form:input type="text" value="${pacItem.value}" path=""
															class="form-control" id="pacHeadId0" />
													</c:if>
												</c:forEach></td>
											<td><form:input
													cssClass="hasMyNumber form-control text-right"
													id="openbalAmt${count}"
													path="bugReappMasterDtoList[0].openbalAmt"></form:input></td>
											<td><form:input cssClass="form-control"
													id="cpdIdDrCrDesc${count}"
													path="bugReappMasterDtoList[0].cpdIdDrCrDesc"></form:input></td>
											<td align="center"><label
												class="checkbox-inline padding-top-0" for="flagFlzd"><form:checkbox
														id="flagFlzd" path="bugReappMasterDtoList[0].flagFlzd"
														value="Y" /></label></td>
										</tr>
									</c:forEach>

								</table>
							</div>
						</div>

					</fieldset>
				</li>
			</ul>

			<INPUT type="hidden" id="count" value="0" />

			<div class="text-center padding-top-10">

				<spring:url var="cancelButtonURL"
					value="AccountBudgetOpenBalanceMaster.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>


			</div>




		</form:form>




	</div>
</div>



<c:if test="${tbAcBugopenBalance.hasError =='true'}">
	</div>
</c:if>



