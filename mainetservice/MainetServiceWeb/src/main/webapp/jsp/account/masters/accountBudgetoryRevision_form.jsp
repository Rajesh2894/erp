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
<script src="js/account/accountBudgetoryRevision.js" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
			
			var budgetType = $('#cpdBugtypeId').val();

			$('#faYearid').prop('disabled', 'disabled');

			var budgetType=$("#cpdBugtypeId option:selected").attr("code");
			if (budgetType == "REV") {
				$("#prProjectionid").removeClass("hide");
				$("#prExpenditureid").addClass("hide");
			} else if (budgetType == "EXP") {
				$("#prProjectionid").addClass("hide");
				$("#prExpenditureid").removeClass("hide");
			}
		}
	});
</script>

<c:if test="${tbAcBudgetoryRevision.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>


<div class="widget" id="widget">

	<div class="widget-header">
		<h2>
			<spring:message code="budget.budgetaryrevision.master.title" text="" />
		</h2>
	<apptags:helpDoc url="AccountBudgetoryRevision.html" helpDocRefURL="AccountBudgetoryRevision.html"></apptags:helpDoc>
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetoryRevision" method="POST"
			action="AccountBudgetoryRevision.html">
			<form:hidden path="" id="indexdata" />
			<form:hidden path="index" id="index" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />

			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
			<form:hidden path="faYearid" id="hiddenFinYear"></form:hidden>
			<form:hidden path="cpdBugtypeIdHidden" id="cpdBugtypeIdHidden" />

			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="clientSideErrorDivScrutiny"></div>
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

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.budgetaryrevision.master.financialyear" text="" /></label>
							<div class="col-sm-4">
								<form:select id="faYearid" path=""
									cssClass="form-control mandColorClass"
									onchange="setHiddenField(this);" data-rule-required="true">

									<c:forEach items="${financeMap}" varStatus="status"
										var="financeMap">
										<form:option value="${financeMap.key}"
											code="${financeMap.key}">${financeMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.budgetaryrevision.master.departmenttype" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select path="dpDeptid"
										class="form-control mandColorClass chosen-select-no-results"
										id="dpDeptid" disabled="${viewMode}"
										onchange="loadBudgetoryRevisionData(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message
												code="budget.budgetaryrevision.master.departmentttype"
												text="" />
										</form:option>
										<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
											<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<c:if test="${MODE_DATA == 'EDIT'}">
								<div class="col-sm-4">
									<form:hidden path="dpDeptid" id="dpDeptid" />
									<form:select path="dpDeptid"
										class="form-control mandColorClass" id="dpDeptid"
										disabled="${viewMode ne 'true'}"
										onchange="loadBudgetoryRevisionData(this)">
										<form:option value="">
											<spring:message
												code="budget.budgetaryrevision.master.departmentttype"
												text="" />
										</form:option>
										<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
											<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>														

						</div>

						<div class="form-group">
						
						<c:if test="${budgetTypeStatus == 'Y'}">
							
							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.budgetaryrevision.master.budgettype" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="REX" />
									<form:select path="cpdBugtypeId"
										class="form-control mandColorClass" id="cpdBugtypeId"
										onchange="clearAllData(this)" disabled="${viewMode}"
										data-rule-required="true">
										<form:option value="">
											<spring:message
												code="budget.budgetaryrevision.master.selectbudgettype"
												text="" />
										</form:option>
										<c:forEach items="${levelMap}" varStatus="status"
											var="levelChild">
											<form:option code="${levelChild.lookUpCode}"
												value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<c:if test="${MODE_DATA == 'EDIT'}">
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="REX" />
									<form:hidden path="cpdBugtypeId" id="cpdBugtypeId" />
									<form:select path="cpdBugtypeId"
										class="form-control mandColorClass" id="cpdBugtypeId"
										onchange="clearAllData(this)" disabled="${viewMode ne 'true'}">
										<form:option value="">
											<spring:message
												code="budget.budgetaryrevision.master.selectbudgettype"
												text="" />
										</form:option>
										<c:forEach items="${levelMap}" varStatus="status"
											var="levelChild">
											<form:option code="${levelChild.lookUpCode}"
												value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>
							
						</c:if>
						
						<c:if test="${budgetSubTypeStatus == 'Y'}">
						
							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.budgetaryrevision.master.budgetsubtype" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select path="cpdBugsubtypeId"
										class="form-control mandColorClass" id="cpdBugsubtypeId"
										disabled="${viewMode}" onchange="loadBudgetoryRevisionData(this)"
										data-rule-required="true">
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

							<c:if test="${MODE_DATA == 'EDIT'}">
								<div class="col-sm-4">
									<form:hidden path="cpdBugsubtypeId" id="cpdBugsubtypeId" />
									<form:select path="cpdBugsubtypeId"
										class="form-control mandColorClass" id="cpdBugsubtypeId"
										disabled="${viewMode ne 'true'}" onchange="loadBudgetoryRevisionData(this)">
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
							</c:if>

						</div>

						<form:hidden path="bugrevId" />
					</fieldset> <input type="hidden" value="${viewMode}" id="test" />

					<div id="prProjectionid" class="hide">

						<form:hidden path="bugprojRevBeanList[${count}].prProjectionid"
							value="${bugprojRevBeanList[count].prProjectionid}"
							id="prProjectionid" />

						<form:hidden path="bugprojRevBeanList[${count}].prProjectionidRev"
							value="" code="bugprojRevBeanList[${count}].prProjectionidRev"
							id="prProjectionidRev" />






						<form:hidden path="bugrevId" />


						<div class="table-overflow-sm">
							<table class="table  table-bordered table-striped " id="revTable">

								<c:if test="${MODE_DATA == 'create'}">
									<tr>
										<th colspan="1" class="text-center" scope="col" width="300px"><spring:message
												code="bill.budget.heads" text="Budget Heads" /><span
											class="mand">*</span></th>

										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th colspan="1" id="currentYear" class="text-center"
												scope="col"><spring:message code="account.budget.estimates.for.the.cfy" text="Budget estimates for the C.F.Y" />
												${pacItem.curFinYear}</th>
										</c:forEach>
										<th colspan="3" scope="col" class="text-center"><spring:message
												code="account.budgetory.revised" text="Revised" /></th>
										<th colspan="1" class="text-center" scope="col"></th>
									</tr>

									<tr>

										<th class="text-center" scope="col"><span class="small"><span
												class="mand"></span></span></th>

										<th class="text-center" scope="col"><span class="small"></span></th>
										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th class="text-center" scope="col"><span class="small"></span><span
												class="small"><br>
														<spring:message code="budget.projected.revenue.entry.master.collected" text="Collected" />
														${pacItem.curFinYearfrmdate}</span></th>
										</c:forEach>
										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th class="text-center" scope="col"><span class="small"><br></span><span
												class="small"><spring:message code="account.balance.to.be.achieved" text="Balance To Be achieved" />
													${pacItem.curFinYearfrmdate} 
													${pacItem.curFinYeartodate}</span></th>
										</c:forEach>
										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th class="text-center" scope="col"><span class="small"><span
													class="small1">(${pacItem.curFinYear})</span><br>
												<spring:message code="account.budgetory.revised.estimates"
														text="Revised Estimates for the current year" /></span><span
												class="mand">*</span></th>
										</c:forEach>
										<th class="text-center" scope="col"><span class="small"><spring:message
													code="account.common.add.remove" text="Add/Remove" /></span></th>
									</tr>

									<c:forEach items="${bugpacsacHeadList}" var="pacItem"
										varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>

										<tr id="bugestIdRev0" class="appendableClass">

											<form:hidden
												path="bugprojRevBeanList[${count}].prProjectionidRevDynamic"
												value="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
												code="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
												id="prProjectionidRevDynamic${count}" />

											<td><form:select
													path="bugprojRevBeanList[${count}].prRevBudgetCode"
													cssClass="form-control mandColorClass chosen-select-no-results"
													id="prRevBudgetCode${count}"
													onchange="getOrgBalAmount(${count})">
													<form:option value="">
														<spring:message code="budget.projected.revenue.entry.master.selectbudgetheads" text="Select Budget Heads" />
													</form:option>
													<c:forEach items="${accountBudgetCodeAllocationMap}"
														varStatus="status" var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value} </form:option>
													</c:forEach>
												</form:select></td>

											<td><form:input cssClass="  form-control text-right"
													id="orginalEstamt${count}"
													path="bugprojRevBeanList[${count}].orginalEstamt"
													readonly="true"></form:input></td>
											<td><form:input cssClass="  form-control text-right"
													id="actualTillNovAmount${count}"
													path="bugprojRevBeanList[${count}].actualTillNovAmount"
													readonly="true"></form:input></td>
											<td><form:input cssClass="  form-control text-right"
													id="budgetedFromDecAmount${count}"
													path="bugprojRevBeanList[${count}].budgetedFromDecAmount"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control mandColorClass text-right"
													id="revisedAmount${count}"
													path="bugprojRevBeanList[${count}].revisedAmount"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="enteredAmountDynamicallyGeneratedCalcAmount(this);"></form:input></td>
											<td><c:if test="${!viewMode}">
													<button title="Add"
														class="btn btn-success btn-sm addButton"
														id="addButton${count}">
														<i class="fa fa-plus-circle"></i>
													</button>
													<button title="Delete"
														class="btn btn-danger btn-sm delButton"
														id="delButton${count}">
														<i class="fa fa-trash-o"></i>
													</button>
												</c:if></td>
										</tr>

									</c:forEach>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<tr>
										<th colspan="1" class="text-center" scope="col" width="300px"><spring:message
												code="bill.budget.heads" text="Budget Heads" /><span
											class="mand">*</span></th>
										<th colspan="1" id="currentYear" class="text-center"
											scope="col"><spring:message
												code="account.budgetory.budget.estimate"
												text="Budget estimates for the current financial year" /></th>
										<th colspan="3" scope="col" class="text-center"><spring:message
												code="account.budgetory.revised" text="Revised" /></th>
									</tr>
									<tr>

										<th class="text-center" scope="col"><span class="small"><span
												class="mand"></span></span></th>
										<th class="text-center" scope="col"><span class="small1"></span></th>
										<th class="text-center" scope="col"><span class="small"></span><span
											class="small"><br>
											<spring:message code="budget.projected.revenue.entry.master.collected"
													text="Collected" /> </span></th>
										<th class="text-center" scope="col"><span class="small"></span><span
											class="small"><spring:message
													code="account.balance.to.be.achieved"
													text="Balance To Be achieved" /></span></th>
										<th class="text-center" scope="col"><span class="small"><span
												class="small1"></span><br>
											<spring:message code="account.budgetory.revised.estimates"
													text="Revised Estimates for the current year" /></span><span
											class="mand">*</span></th>
									</tr>
									<tr>



										<form:hidden
											path="bugprojRevBeanList[${count}].prProjectionidRevDynamic"
											value="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
											code="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
											id="prProjectionidRevDynamic${count}" />

										<td><form:select
												path="bugprojRevBeanList[${count}].prRevBudgetCode"
												cssClass="form-control mandColorClass chosen-select-no-results"
												id="prRevBudgetCode${count}"
												onchange="getOrgBalAmount(${count})">
												<form:option value="">
													<spring:message code="" text="Select Budget Heads" />
												</form:option>
												<c:forEach items="${accountBudgetCodeAllocationMap}"
													varStatus="status" var="pacItem">
													<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value} </form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input cssClass="  form-control text-right"
												id="orginalEstamt${count}"
												path="bugprojRevBeanList[${count}].orginalEstamt"
												readonly="true"></form:input></td>
										<td><form:input cssClass="  form-control text-right"
												id="actualTillNovAmount${count}"
												path="bugprojRevBeanList[${count}].actualTillNovAmount"
												readonly="true"></form:input></td>
										<td><form:input cssClass="  form-control text-right"
												id="budgetedFromDecAmount${count}"
												path="bugprojRevBeanList[${count}].budgetedFromDecAmount"
												readonly="true"></form:input></td>
										<td><form:input
												cssClass="  form-control mandColorClass text-right"
												id="revisedAmount${count}"
												path="bugprojRevBeanList[${count}].revisedAmount"
												onkeypress="return hasAmount(event, this, 13, 2)"
												onchange="enteredAmountDynamicallyGeneratedCalcAmount(this);"></form:input></td>
									</tr>
								</c:if>

							</table>
						</div>
					</div>

					<div id="prExpenditureid" class="hide">

						<form:hidden path="bugprojExpBeanList[${count}].prExpenditureid"
							value="${bugprojExpBeanList[count].prExpenditureid}"
							id="prExpenditureid" />

						<form:hidden
							path="bugprojExpBeanList[${count}].prExpenditureidExp" value=""
							code="bugprojExpBeanList[${count}].prExpenditureidExp"
							id="prExpenditureidExp" />






						<form:hidden path="bugrevId" />



						<div class="table-overflow-sm">
							<table class="table  table-bordered table-striped " id="expTable">

								<c:if test="${MODE_DATA == 'create'}">
									<tr>
										<th colspan="1" class="text-center" scope="col" width="300px"><spring:message
												code="bill.budget.heads" text="Budget Heads" /><span
											class="mand">*</span></th>



										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th colspan="1" id="currentYear" class="text-center"
												scope="col"><spring:message code="account.budget.estimates.for.the.cfy" text="Budget estimates for the C.F.Y" />
												${pacItem.curFinYear}</th>
										</c:forEach>
										<th colspan="3" scope="col" class="text-center"><spring:message
												code="account.budgetory.revised" text="Revised" /></th>
										<th colspan="1" class="text-center" scope="col"></th>
									</tr>

									<tr>

										<th class="text-center" scope="col"><span class="small"><span
												class="mand"></span></span></th>

										<th class="text-center" scope="col"><span class="small"></span></th>
										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th class="text-center" scope="col"><span class="small"></span><span
												class="small"><br>
														<spring:message code="account.actual.expenses" text="Actual Expenses" />
														${pacItem.curFinYearfrmdate}</span></th>
										</c:forEach>
										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th class="text-center" scope="col"><span class="small"><br></span><span
												class="small"><spring:message code="account.balance" text="Balance" />
													${pacItem.curFinYearfrmdate} 
													${pacItem.curFinYeartodate}</span></th>
										</c:forEach>
										<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
											var="pacItem" varStatus="status">
											<th class="text-center" scope="col"><span class="small"><span
													class="small1">(${pacItem.curFinYear})</span><br>
												<spring:message code="account.budgetory.revised.estimates"
														text="Revised Estimates for the current year" /></span><span
												class="mand">*</span></th>
										</c:forEach>
										<th class="text-center" scope="col"><span class="small"><spring:message
													code="account.common.add.remove" text="Add/Remove" /></span></th>
									</tr>

									<c:forEach items="${bugpacsacHeadList}" var="pacItem"
										varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>

										<tr id="bugestIdExp0" class="ExpappendableClass">

											<form:hidden
												path="bugprojExpBeanList[${count}].prExpenditureidExpDynamic"
												value="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
												code="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
												id="prExpenditureidExpDynamic${count}" />

											<td><form:select
													path="bugprojExpBeanList[${count}].prExpBudgetCode"
													cssClass="form-control mandColorClass chosen-select-no-results"
													id="prExpBudgetCode${count}"
													onchange="getOrgBalAmountExp(${count})">
													<form:option value="">
														<spring:message code="budget.projected.revenue.entry.master.selectbudgetheads" text="Select Budget Heads" />
													</form:option>
													<c:forEach items="${accountBudgetCodeAllocationExpMap}"
														varStatus="status" var="sacItem">
														<form:option value="${sacItem.key}" code="${sacItem.key}">${sacItem.value} </form:option>
													</c:forEach>
												</form:select></td>



											<td><form:input cssClass="  form-control text-right"
													id="orginalEstamtExp${count}"
													path="bugprojExpBeanList[${count}].orginalEstamt"
													readonly="true"></form:input></td>
											<td><form:input cssClass="  form-control text-right"
													id="actualTillNovAmountExp${count}"
													path="bugprojExpBeanList[${count}].actualTillNovAmount"
													readonly="true"></form:input></td>
											<td><form:input cssClass="  form-control text-right"
													id="budgetedFromDecAmountExp${count}"
													path="bugprojExpBeanList[${count}].budgetedFromDecAmount"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control mandColorClass text-right"
													id="revisedAmountExp${count}"
													path="bugprojExpBeanList[${count}].revisedAmount"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="enteredAmountDynamicallyGeneratedCalcALLAmount(this);"></form:input></td>
											<td><c:if test="${!viewMode}">
													<button title="Add"
														class="btn btn-success btn-sm addButtonExp"
														id="addButtonExp${count}">
														<i class="fa fa-plus-circle"></i>
													</button>
													<button title="Delete"
														class="btn btn-danger btn-sm delButtonExp"
														id="delButtonExp${count}">
														<i class="fa fa-trash-o"></i>
													</button>
												</c:if></td>
										</tr>

									</c:forEach>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<tr>
										<th colspan="1" class="text-center" scope="col" width="300px"><spring:message
												code="bill.budget.heads" text="Budget Heads" /><span
											class="mand">*</span></th>
										<th colspan="1" id="currentYear" class="text-center"
											scope="col"><spring:message
												code="account.budgetory.budget.estimate"
												text="Budget estimates for the current financial year" /></th>
										<th colspan="3" scope="col" class="text-center"><spring:message
												code="account.budgetory.revised" text="Revised" /></th>
									</tr>
									<tr>

										<th class="text-center" scope="col"><span class="small"><span
												class="mand"></span></span></th>
										<th class="text-center" scope="col"><span class="small1"></span></th>
										<th class="text-center" scope="col"><span class="small"></span><span
											class="small"><br>
											<spring:message code="account.actual.expenses"
													text="Actual Expenses" /></span></th>
										<th class="text-center" scope="col"><span class="small"></span><span
											class="small"><spring:message
													code="account.balance"
													text="Balance" /></span></th>
										<th class="text-center" scope="col"><span class="small"><span
												class="small1"></span><br>
											<spring:message code="account.budgetory.revised.estimates"
													text="Revised Estimates for the current year" /></span><span
											class="mand">*</span></th>
									</tr>
									<tr>

										<form:hidden
											path="bugprojExpBeanList[${count}].prExpenditureidExpDynamic"
											value="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
											code="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
											id="prExpenditureidExpDynamic${count}" />

										<td><form:select
												path="bugprojExpBeanList[${count}].prExpBudgetCode"
												cssClass="form-control mandColorClass chosen-select-no-results"
												id="prExpBudgetCode${count}"
												onchange="getOrgBalAmountExp(${count})">
												<form:option value="">
													<spring:message code="budget.projected.revenue.entry.master.selectbudgetheads" text="Select Budget Heads" />
												</form:option>
												<c:forEach items="${accountBudgetCodeAllocationExpMap}"
													varStatus="status" var="sacItem">
													<form:option value="${sacItem.key}" code="${sacItem.key}">${sacItem.value} </form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input cssClass="  form-control text-right"
												id="orginalEstamtExp${count}"
												path="bugprojExpBeanList[${count}].orginalEstamt"
												readonly="true"></form:input></td>
										<td><form:input cssClass="  form-control text-right"
												id="actualTillNovAmountExp${count}"
												path="bugprojExpBeanList[${count}].actualTillNovAmount"
												readonly="true"></form:input></td>
										<td><form:input cssClass="  form-control text-right"
												id="budgetedFromDecAmountExp${count}"
												path="bugprojExpBeanList[${count}].budgetedFromDecAmount"
												readonly="true"></form:input></td>
										<td><form:input
												cssClass="  form-control mandColorClass text-right"
												id="revisedAmountExp${count}"
												path="bugprojExpBeanList[${count}].revisedAmount"
												onkeypress="return hasAmount(event, this, 13, 2)"
												onchange="enteredAmountDynamicallyGeneratedCalcALLAmount(this);"></form:input></td>
									</tr>
								</c:if>

							</table>

						</div>
					</div>

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
				   	<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="<spring:message code="account.bankmaster.save" text="Save" />"> </input>
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.btn.reset" text="Reset" />
					</button>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value='<spring:message code="account.bankmaster.save" text="Save" />'> </input>
				</c:if>
				<spring:url var="cancelButtonURL"
					value="AccountBudgetoryRevision.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>

	</div>

</div>
<script>
$(document).ready(function() {
	
	if($('#hiddenFinYear').val()) 
	$('#faYearid').val($('#hiddenFinYear').val())
	
	var id=$('#index').val();	

	});
	
</script>

<c:if test="${tbAcBudgetoryRevision.hasError =='true'}">

</c:if>

