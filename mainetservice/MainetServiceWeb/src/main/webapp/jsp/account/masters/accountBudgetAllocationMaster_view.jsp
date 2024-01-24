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
<script src="js/account/accountBudgetAllocation.js"></script>

<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script>
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,	
	});
	 
	$("#prRevBudgetCode0").change(function(event) {
	stt = parseFloat($('#orginalEstamt0').val());
	parentCode = parseFloat($('#allocation0').val());
	if ((stt != "" && stt != undefined &&  !isNaN(stt))
			&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
	  var div = ((stt*parentCode)/100);
		var result = div.toFixed(2);
		$('#amount0').val(result);
  	}
	});
	 
	 $("#prExpBudgetCode0").change(function(event) {
			stt = parseFloat($('#ExporginalEstamt0').val());
			parentCode = parseFloat($('#Expallocation0').val());
			if ((stt != "" && stt != undefined &&  !isNaN(stt))
					&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
			  var div = ((stt*parentCode)/100);
				var result = div.toFixed(2);
				$('#Expamount0').val(result);
		  }
	});
	
</script>

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


<c:if test="${tbAcBudgetProjectedExpenditure.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>



<div class="widget" id="widget">
	<div class="widget-header">

		<h2>
			<spring:message code="budget.allocation.master.title" text="" />
		</h2>
	</div>

	<div class="widget-content padding">

		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetAllocation" method="POST"
			action="AccountBudgetAllocation.html">
			<form:hidden path="secondaryId" id="secondaryId" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="index" id="index" />
			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
			<form:hidden path="faYearid" id="hiddenFinYear"></form:hidden>
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="validtillDate1" id="validtillDate1" />
			<form:hidden path="cpdBugtypeIdHidden" id="cpdBugtypeIdHidden" />


			<form:hidden path="hasError" />
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
									code="budget.allocation.master.financialyear" text="" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="financialYearDesc"
									class="form-control" id="financialYearDesc" />
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="budget.allocation.master.budgettype" text="" /></label>

							<div class="col-sm-4">

								<form:input type="text" path="cpdBugtypeDesc"
									class="form-control" id="cpdBugtypeDesc" />
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message
									code="budget.allocation.master.budgetsubtype" text="" /></label>

							<div class="col-sm-4">

								<form:input type="text" path="cpdBugsubtypeDesc"
									class="form-control" id="cpdBugsubtypeDesc" />
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="budget.allocation.master.departmenttype" text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="departmentDesc"
									class="form-control" id="departmentDesc" />
							</div>
						</div>

						<form:hidden path="baId" id="baId${count}" value="${paAdjid}" />
						<input type="hidden" value="${viewMode}" id="test" />

						<c:if test="${!viewMode}">
							<div id="prProjectionid" class="hide">
								<form:hidden path="baId" />


								<div class="table-overflow-sm">
									<table class="table  table-bordered table-striped "
										id="revTable">


										<tr>
											<th width="44%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.budgethead" text="" /></span></th>
											<th width="15%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.budget" text="" /></span></th>
											<th width="6%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.allocation" text="" /></span></th>
											<th width="15%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.amount" text="" /></span></th>
											<th width="15%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.budgetcontroldate" text="" /></span></th>
										</tr>

										<c:forEach items="${tbAcBudgetAllocation.bugprojRevBeanList}"
											var="prRevList">
											<tr>

												<form:hidden
													path="bugprojRevBeanList[${count}].prProjectionidRevDynamic"
													value="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
													code="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
													id="prProjectionidRevDynamic${count}" />

												<td><c:set value="${prRevList.prRevBudgetCode}"
														var="prRevBudgetCode"></c:set> <c:forEach
														items="${accountBudgetCodeAllocationMap}"
														varStatus="status" var="budgetCodeRevItem">
														<c:if test="${prRevBudgetCode eq budgetCodeRevItem.key}">
															<form:input type="text"
																value="${budgetCodeRevItem.value}" path=""
																class="form-control" id="prRevBudgetCode" />
														</c:if>
													</c:forEach></td>


												<td><form:input cssClass=" form-control text-right"
														id="orginalEstamt${count}"
														path="bugprojRevBeanList[${count}].orginalEstamt"
														readonly="true"></form:input></td>
												<td><form:input
														cssClass="hasMyNumber form-control text-right"
														id="allocation${count}"
														path="bugprojRevBeanList[${count}].allocation"
														onkeyup="handleChange(this);"></form:input></td>
												<td><form:input cssClass=" form-control text-right"
														id="amount${count}"
														path="bugprojRevBeanList[${count}].amount" readonly="true"></form:input></td>
												<td><form:input cssClass="form-control datepick"
														id="budgetControlDate${count}"
														path="bugprojRevBeanList[${count}].budgetControlDate"
														onmousedown="changevaliddateedittime(this);"
														placeholder='DD/MM/YYYY' readonly="${viewMode ne 'true'}"></form:input></td>
											</tr>
										</c:forEach>
									</table>
								</div>

							</div>
						</c:if>


						<c:if test="${!viewMode}">
							<div id="prExpenditureid" class="hide">
								<form:hidden path="baId" />


								<div class="table-overflow-sm">
									<table class="table  table-bordered table-striped "
										id="expTable">

										<tr>
											<th width="44%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.budgethead" text="" /></span></th>
											<th width="15%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.budget" text="" /></span></th>
											<th width="6%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.allocation" text="" /></span></th>
											<th width="15%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.master.amount" text="" /></span></th>
											<th width="15%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.allocation.budgetcontroldate" text="" /></span></th>
										</tr>

										<c:forEach items="${tbAcBudgetAllocation.bugprojExpBeanList}"
											var="prExpList" varStatus="status">
											<tr>

												<form:hidden
													path="bugprojExpBeanList[${count}].prExpenditureidExpDynamic"
													value="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
													code="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
													id="prExpenditureidExpDynamic${count}" />

												<td><c:set value="${prExpList.prExpBudgetCode}"
														var="prExpBudgetCode"></c:set> <c:forEach
														items="${accountBudgetCodeAllocationExpMap}"
														varStatus="status" var="budgetCodeItem">
														<c:if test="${prExpBudgetCode eq budgetCodeItem.key}">
															<form:input type="text" value="${budgetCodeItem.value}"
																path="" class="form-control" id="prExpBudgetCode" />
														</c:if>
													</c:forEach>
												<td><form:input
														path="bugprojExpBeanList[${count}].orginalEstamt"
														id="ExporginalEstamt${count}"
														class=" form-control text-right"
														readonly="${viewMode ne 'true' }" /></td>
												<td><form:input
														cssClass="hasMyNumber form-control text-right"
														id="Expallocation${count}"
														path="bugprojExpBeanList[${count}].expAllocation"
														onkeyup="handleExpChange(this);"></form:input></td>
												<td><form:input cssClass=" form-control text-right"
														id="Expamount${count}"
														path="bugprojExpBeanList[${count}].expAmount"
														readonly="true"></form:input></td>
												<td><form:input cssClass="form-control datepic"
														id="ExpbudgetControlDate${count}"
														path="bugprojExpBeanList[${count}].expBudgetControlDate"
														onmousedown="changeExpvaliddateedittime(this);"
														placeholder='DD/MM/YYYY' readonly="${viewMode ne 'true'}"></form:input></td>
											</tr>
										</c:forEach>

									</table>
								</div>

							</div>
						</c:if>

					</fieldset>

				</li>
			</ul>
			<INPUT type="hidden" id="count" value="0" />
			<INPUT type="hidden" id="countFinalCode" value="0" />

			<div class="text-center padding-top-10">

				<spring:url var="cancelButtonURL"
					value="AccountBudgetAllocation.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>

<script>
$(document).ready(function() {
	
	if($('#hiddenFinYear').val()) 
	$('#faYearid').val($('#hiddenFinYear').val());
	
	});
	
</script>

<c:if test="${tbAcBudgetProjectedExpenditure.hasError =='true'}">
	</div>
</c:if>

