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
<script src="js/account/approvalForExpenditureEntry.js" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>





<c:if test="${tbAcApprovalForExpenditureEntry.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>



<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>



<div class="widget" id="widget">
	<div class="widget-header">

		<h2>
			<spring:message code="" text="Approval For Expenditure Entry" />
		</h2>
	<apptags:helpDoc url="ApprovalForExpenditureEntry.html" helpDocRefURL="ApprovalForExpenditureEntry.html"></apptags:helpDoc>	
	</div>

	<div class="widget-content padding">

		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcApprovalForExpenditureEntry" name="frmMaster"
			method="POST" action="ApprovalForExpenditureEntry.html">
			<form:hidden path="" id="secondaryId" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
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
							<label class="control-label col-sm-2 required-control"><spring:message
									code="budget.projected.expenditure.master.budgetyear" text="" /></label>
							<div class="col-sm-4">
								<form:select id="faYearid" path="" cssClass="form-control"
									disabled="${viewMode}" onchange="clearAllData(this)">
									<c:forEach items="${financeMap}" varStatus="status"
										var="financeMap">
										<form:option value="${financeMap.key}"
											code="${financeMap.key}">${financeMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.budgetaryrevision.master.budgetsubtype" text="" /></label>

							<div class="col-sm-4">
								<form:select path="" class="form-control " id="cpdBugsubtypeId"
									disabled="${viewMode}" onchange="clearAllData(this)">
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
						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.projected.expenditure.master.department" text="" /></label>

							<div class="col-sm-4">
								<form:select path=""
									class="form-control chosen-select-no-results" id="dpDeptid"
									disabled="${viewMode}"
									onchange="loadBudgetExpenditureData(this)">
									<form:option value="">
										<spring:message code="" text="Select Department" />
									</form:option>
									<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
										<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<form:hidden path="prApprForExpid" />

						</div>

						<form:hidden path="prApprForExpid" />
						<div id="prApprForExpid" class="">
							<div class="table-overflow-sm" id="budExpTableDivID">
								<table class="table table-hover table-bordered table-striped "
									id="budExpTable">

									<c:if test="${MODE_DATA == 'create'}">
										<tr>
											<th scope="col" width="55%" style="text-align: center"><spring:message
													code="budget.projected.expenditure.master.budgethead"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="35%"><spring:message
													code="budget.projected.expenditure.master.budgetprocision"
													text="" /><span class="mand">*</span></th>
											<th class="text-center" scope="col" width="10%"><span
												class="small"><spring:message
														code="account.budgetopenmaster.addremove" text="" /></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<tr>
											<th scope="col" width="40%" style="text-align: center"><spring:message
													code="budget.projected.expenditure.master.budgethead"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.budgetprocision"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.revisebudget"
													text="" /><span class="mand"></span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.expenditureamount"
													text="" /><span class="mand"></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'create'}">
										<tr id="budExpId" class="appendableClass">

											<td><form:select id="prBudgetCodeid${count}" path=""
													cssClass="form-control chosen-select-no-results"
													disabled="${viewMode}"
													onchange="findduplicatecombinationexit(${count})">
													<form:option value="">
														<spring:message
															code="budget.projected.expenditure.master.selectbudgetheads" />
													</form:option>
													<c:forEach items="${accountBudgetCodeMap}"
														varStatus="status" var="bugCodeItem">
														<form:option value="${bugCodeItem.key}"
															code="${bugCodeItem.key}">${bugCodeItem.value}</form:option>
													</c:forEach>
												</form:select></td>

											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="orginalEstamt${count}" path=""
													onkeyup="copyContent(this)"></form:input></td>
											<td>
												<button title="Add" class="btn btn-success btn-sm addButton"
													id="addButton${count}">
													<i class="fa fa-plus-circle"></i>
												</button>
												<button title="Delete"
													class="btn btn-danger btn-sm delButton"
													id="delButton${count}">
													<i class="fa fa-trash-o"></i>
												</button>
											</td>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<td><form:select path=""
													cssClass="form-control chosen-select-no-results"
													id="prBudgetCodeid0"
													onchange="findduplicatecombinationexit(${0})">
													<form:option value="">
														<spring:message
															code="budget.projected.expenditure.master.selectbudgetheads"
															text="" />
													</form:option>
													<c:forEach items="${accountBudgetCodeMap}"
														varStatus="status" var="bugCodeItem">
														<form:option value="${bugCodeItem.key}"
															code="${bugCodeItem.key}">${bugCodeItem.value} </form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="orginalEstamt0" path="" onkeyup="copyContent(this)"></form:input></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="revisedEstamt0" path=""
													readonly="${viewMode ne 'true'}"></form:input></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="expenditureAmt0" path=""
													readonly="${viewMode ne 'true'}"></form:input></td>
										</tr>
									</c:if>

								</table>
							</div>
						</div>

					</fieldset>
				</li>
			</ul>
			<INPUT type="hidden" id="count" value="0" />
			<INPUT type="hidden" id="countFinalCode" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${viewMode ne 'true' }">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save"> </input>
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</c:if>
				<spring:url var="cancelButtonURL"
					value="ApprovalForExpenditureEntry.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>
<c:if test="${tbAcApprovalForExpenditureEntry.hasError =='true'}">
	</div>
</c:if>

