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
		<apptags:helpDoc url="AccountBudgetAllocation.html" helpDocRefURL="AccountBudgetAllocation.html"></apptags:helpDoc>
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

			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
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


							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.allocation.master.financialyear" text="" /></label>
							<div class="col-sm-4">
								<form:select id="faYearid" path=""
									cssClass="form-control mandColorClass"
									onchange="setDeafaultFinancialYearEndDate(this)"
									data-rule-required="true">

									<c:forEach items="${financeMap}" varStatus="status"
										var="financeMap">
										<form:option value="${financeMap.key}"
											code="${financeMap.value}">${financeMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.allocation.master.budgettype" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="REX" />
									<form:select path="cpdBugtypeId"
										class="form-control mandColorClass" id="cpdBugtypeId"
										disabled="${viewMode}" onchange="clearAllData(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message
												code="budget.allocation.master.selectbudgettype" text="" />
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
										disabled="${viewMode ne 'true'}" onchange="clearAllData(this)">
										<form:option value="">
											<spring:message
												code="budget.allocation.master.selectbudgettype" text="" />
										</form:option>
										<c:forEach items="${levelMap}" varStatus="status"
											var="levelChild">
											<form:option code="${levelChild.lookUpCode}"
												value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.allocation.master.budgetsubtype" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select path="cpdBugsubtypeId"
										class="form-control mandColorClass" id="cpdBugsubtypeId"
										disabled="${viewMode}" onchange="clearAllData(this)"
										onclick="setDeafaultFinancialYearEndDate(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message
												code="budget.allocation.master.selectbudgetsubtype" text="" />
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
										disabled="${viewMode ne 'true'}" onchange="clearAllData(this)"
										onclick="setDeafaultFinancialYearEndDate(this)">
										<form:option value="">
											<spring:message
												code="budget.allocation.master.selectbudgetsubtype" text="" />
										</form:option>
										<c:forEach items="${bugSubTypelevelMap}" varStatus="status"
											var="levelChild">
											<form:option code="${levelChild.lookUpCode}"
												value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.allocation.master.departmenttype" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select path="dpDeptid"
										class="form-control mandColorClass chosen-select-no-results"
										id="dpDeptid" disabled="${viewMode}"
										onchange="loadBudgetAllocationData(this)"
										onclick="setDeafaultFinancialYearEndDate(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message
												code="budget.allocation.master.departmentttype" text="" />
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
										onchange="loadBudgetAllocationData(this)"
										onclick="setDeafaultFinancialYearEndDate(this)">
										<form:option value="">
											<spring:message
												code="budget.allocation.master.departmentttype" text="" />
										</form:option>
										<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
											<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

						</div>

						<form:hidden path="baId" id="baId${count}" value="${paAdjid}" />
						<input type="hidden" value="${viewMode}" id="test" />

						<c:if test="${!viewMode}">
							<div id="prProjectionid" class="hide">
								<form:hidden path="baId" />


								<div class="table-overflow-sm">
									<table class="table  table-bordered table-striped "
										id="revTable">

										<c:if test="${MODE_DATA == 'create'}">

											<tr>
												<th width="40%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budgethead" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budget" text="" /><span
														class="mand"></span></span></th>
												<th width="6%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.allocation" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.amount" text="" /></span></th>
												<th width="14%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.budgetcontroldate" text="" /><span
														class="mand">*</span></span></th>
												<th width="10%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.addremove" text="" /></span></th>
											</tr>
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
															<spring:message
																code="budget.allocation.master.selectbudgetheads"
																text="" />
														</form:option>
														<c:forEach items="${accountBudgetCodeAllocationMap}"
															varStatus="status" var="budgetCodeItem">
															<form:option value="${budgetCodeItem.key}"
																code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
														</c:forEach>
													</form:select></td>


												<td><form:input cssClass=" form-control text-right"
														id="orginalEstamt${count}"
														path="bugprojRevBeanList[${count}].orginalEstamt"
														readonly="true"></form:input></td>

												<td><form:input
														cssClass="hasMyNumber form-control mandColorClass text-right"
														id="allocation${count}" value="100"
														path="bugprojRevBeanList[${count}].allocation"
														onkeyup="handleChange(this);"></form:input></td>
												<td><form:input cssClass=" form-control text-right"
														id="amount${count}"
														path="bugprojRevBeanList[${count}].amount" readonly="true"></form:input></td>
												<td><form:input
														cssClass="form-control mandColorClass datepick"
														id="budgetControlDate${count}"
														path="bugprojRevBeanList[${count}].budgetControlDate"
														onmousedown="changevaliddateedittime(this);"
														placeholder='DD/MM/YYYY' readonly="${viewMode ne 'true'}"></form:input></td>
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
										</c:if>

										<c:if test="${MODE_DATA == 'EDIT'}">

											<tr>
												<th width="49%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budgethead" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budget" text="" /><span
														class="mand"></span></span></th>
												<th width="6%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.allocation" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.amount" text="" /></span></th>
												<th width="12%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.budgetcontroldate" text="" /><span
														class="mand">*</span></span></th>
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
															<spring:message code="" text="Select" />
														</form:option>
														<c:forEach items="${accountBudgetCodeAllocationMap}"
															varStatus="status" var="budgetCodeItem">
															<form:option value="${budgetCodeItem.key}"
																code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
														</c:forEach>
													</form:select></td>


												<td><form:input cssClass=" form-control text-right"
														id="orginalEstamt${count}"
														path="bugprojRevBeanList[${count}].orginalEstamt"
														readonly="true"></form:input></td>
												<td><form:input
														cssClass="hasMyNumber mandColorClass form-control text-right"
														id="allocation${count}"
														path="bugprojRevBeanList[${count}].allocation"
														onkeyup="handleChange(this);"></form:input></td>
												<td><form:input cssClass=" form-control text-right"
														id="amount${count}"
														path="bugprojRevBeanList[${count}].amount" readonly="true"></form:input></td>
												<td><form:input
														cssClass="form-control mandColorClass datepick"
														id="budgetControlDate${count}"
														path="bugprojRevBeanList[${count}].budgetControlDate"
														onmousedown="changevaliddateedittime(this);"
														placeholder='DD/MM/YYYY' readonly="${viewMode ne 'true'}"></form:input></td>
											</tr>
										</c:if>

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

										<c:if test="${MODE_DATA == 'create'}">

											<tr>
												<th width="40%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budgethead" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budget" text="" /><span
														class="mand"></span></span></th>
												<th width="6%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.allocation" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.amount" text="" /></span></th>
												<th width="14%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.budgetcontroldate" text="" /><span
														class="mand">*</span></span></th>
												<th width="10%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.addremove" text="" /></span></th>
											</tr>
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
														onchange="getOrgBalExpAmount(${count})">
														<form:option value="">
															<spring:message
																code="budget.reappropriation.master.select" text="" />
														</form:option>
														<c:forEach items="${accountBudgetCodeAllocationExpMap}"
															varStatus="status" var="budgetCodeItem">
															<form:option value="${budgetCodeItem.key}"
																code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
														</c:forEach>
													</form:select></td>


												<td><form:input
														path="bugprojExpBeanList[${count}].orginalEstamt"
														id="ExporginalEstamt${count}"
														class=" form-control text-right"
														readonly="${viewMode ne 'true' }" /></td>

												<td><form:input
														cssClass="hasMyNumber form-control mandColorClass text-right"
														id="Expallocation${count}" value="100"
														path="bugprojExpBeanList[${count}].expAllocation"
														onkeyup="handleExpChange(this);"></form:input></td>
												<td><form:input cssClass=" form-control text-right"
														id="Expamount${count}"
														path="bugprojExpBeanList[${count}].expAmount"
														readonly="true"></form:input></td>
												<td><form:input
														cssClass="form-control mandColorClass datepic"
														id="ExpbudgetControlDate${count}"
														path="bugprojExpBeanList[${count}].expBudgetControlDate"
														onmousedown="changeExpvaliddateedittime(this);"
														placeholder='DD/MM/YYYY' readonly="${viewMode ne 'true'}"></form:input></td>
												<td><c:if test="${!viewMode}">
														<a title="Add" class="btn btn-success btn-sm addButtonExp"
															id="addButtonExp${count}"><i
															class="fa fa-plus-circle"></i></a>
														<a title="Delete"
															class="btn btn-danger btn-sm delButtonExp"
															id="delButtonExp${count}"><i class="fa fa-trash-o"></i></a>
													</c:if></td>
											</tr>
										</c:if>

										<c:if test="${MODE_DATA == 'EDIT'}">

											<tr>
												<th width="49%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budgethead" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.budget" text="" /><span
														class="mand"></span></span></th>
												<th width="6%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.allocation" text="" /><span
														class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.master.amount" text="" /></span></th>
												<th width="12%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.allocation.budgetcontroldate" text="" /><span
														class="mand">*</span></span></th>
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
														onchange="getOrgBalExpAmount(${count})">
														<form:option value="">
															<spring:message
																code="budget.reappropriation.master.select" text="" />
														</form:option>
														<c:forEach items="${accountBudgetCodeAllocationExpMap}"
															varStatus="status" var="budgetCodeItem">
															<form:option value="${budgetCodeItem.key}"
																code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
														</c:forEach>
													</form:select></td>


												<td><form:input
														path="bugprojExpBeanList[${count}].orginalEstamt"
														id="ExporginalEstamt${count}"
														class=" form-control text-right"
														readonly="${viewMode ne 'true'}" /></td>
												<td><form:input
														cssClass="hasMyNumber form-control mandColorClass text-right"
														id="Expallocation${count}"
														path="bugprojExpBeanList[${count}].expAllocation"
														onkeyup="handleExpChange(this);"></form:input></td>
												<td><form:input cssClass=" form-control text-right"
														id="Expamount${count}"
														path="bugprojExpBeanList[${count}].expAmount"
														readonly="true"></form:input></td>
												<td><form:input
														cssClass="form-control mandColorClass datepic"
														id="ExpbudgetControlDate${count}"
														path="bugprojExpBeanList[${count}].expBudgetControlDate"
														onmousedown="changeExpvaliddateedittime(this);"
														placeholder='DD/MM/YYYY' readonly="${viewMode ne 'true'}"></form:input></td>
											</tr>
										</c:if>

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
				<c:if test="${MODE_DATA == 'create'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save"> </input>
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.btn.reset" text="Reset" />
					</button>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save"> </input>
				</c:if>
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

