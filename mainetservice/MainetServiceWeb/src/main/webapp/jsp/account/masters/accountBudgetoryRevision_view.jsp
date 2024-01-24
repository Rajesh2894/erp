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
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetoryRevision" method="POST"
			action="AccountBudgetoryRevision.html">
			<form:hidden path="" id="indexdata" />
			<form:hidden path="index" id="index" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />


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

							<label class="col-sm-2 control-label "><spring:message
									code="budget.budgetaryrevision.master.financialyear" text="" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="financialYearDesc"
									class="form-control" id="financialYearDesc" />
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="budget.budgetaryrevision.master.departmenttype" text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="departmentDesc"
									class="form-control" id="departmentDesc" />
							</div>
														
						</div>

						<div class="form-group">
						
							<c:if test="${budgetTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label "><spring:message
									code="budget.budgetaryrevision.master.budgettype" text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="cpdBugtypeDesc"
									class="form-control" id="cpdBugtypeDesc" />
							</div>
							</c:if>
							
							<c:if test="${budgetSubTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label "><spring:message
									code="budget.budgetaryrevision.master.budgetsubtype" text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="cpdBugsubtypeDesc"
									class="form-control" id="cpdBugsubtypeDesc" />
							</div>
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

								<tr>
									<th colspan="1" class="text-center" scope="col" width="400px"><spring:message
											code="bill.budget.heads" text="Budget Heads" /></th>
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
												text="Collected" /></span></th>
									<th class="text-center" scope="col"><span class="small"></span><span
										class="small"><spring:message
												code="account.balance.to.be.achieved"
												text="Balance To Be achieved" /></span></th>
									<th class="text-center" scope="col"><span class="small"><span
											class="small1"></span><br>
										<spring:message code="account.budgetory.revised.estimates"
												text="Revised Estimates for the current year" /></span></th>
								</tr>
								<tr>



									<form:hidden
										path="bugprojRevBeanList[${count}].prProjectionidRevDynamic"
										value="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
										code="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
										id="prProjectionidRevDynamic${count}" />

									<td style="width: 300px;"><form:hidden
											path="bugprojRevBeanList[${count}].prRevBudgetCode"></form:hidden>
										<form:input cssClass="form-control "
											id="prRevBudgetCode${count}"
											path="bugprojRevBeanList[${count}].prRevBudgetCodeDup"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="orginalEstamt${count}"
											path="bugprojRevBeanList[${count}].orginalEstamt"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="actualTillNovAmount${count}"
											path="bugprojRevBeanList[${count}].actualTillNovAmount"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="budgetedFromDecAmount${count}"
											path="bugprojRevBeanList[${count}].budgetedFromDecAmount"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="revisedAmount${count}"
											path="bugprojRevBeanList[${count}].revisedAmount"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onchange="enteredAmountDynamicallyGeneratedCalcAmount(this);"></form:input></td>
								</tr>

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

								<tr>
									<th colspan="1" class="text-center" scope="col" width="400px"><spring:message
											code="bill.budget.heads" text="Budget Heads" /></th>
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
												text="Actual Expenses" /> </span></th>
									<th class="text-center" scope="col"><span class="small"></span><span
										class="small"><spring:message
												code="account.balance"
												text="Balance" /></span></th>
									<th class="text-center" scope="col"><span class="small"><span
											class="small1"></span><br>
										<spring:message code="account.budgetory.revised.estimates"
												text="Revised Estimates for the current year" /></span></th>
								</tr>
								<tr>

									<form:hidden
										path="bugprojExpBeanList[${count}].prExpenditureidExpDynamic"
										value="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
										code="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
										id="prExpenditureidExpDynamic${count}" />


									<td style="width: 300px;"><form:hidden
											path="bugprojExpBeanList[${count}].prExpBudgetCode"></form:hidden>
										<form:input cssClass="form-control"
											id="prExpBudgetCode${count}"
											path="bugprojExpBeanList[${count}].prExpBudgetCodeDup"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="currentYearBugAmtExp${count}"
											path="bugprojExpBeanList[${count}].orginalEstamt"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="actualTillNovAmountRevisionExp${count}"
											path="bugprojExpBeanList[${count}].actualTillNovAmount"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="budgetedFromDecAmountRevisionExp${count}"
											path="bugprojExpBeanList[${count}].budgetedFromDecAmount"
											readonly="true"></form:input></td>
									<td style="width: 100px;"><form:input
											cssClass="  form-control text-right text-right"
											id="revisedAmountRevisionExp${count}"
											path="bugprojExpBeanList[${count}].revisedAmount"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onkeyup="enteredAmountDynamicallyGeneratedCalcALLAmount(this);"></form:input></td>
								</tr>

							</table>

						</div>
					</div>

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />

			<div class="text-center padding-top-10">
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

