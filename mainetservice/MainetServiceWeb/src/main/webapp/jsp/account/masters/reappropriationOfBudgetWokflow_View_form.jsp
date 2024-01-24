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
<script src="js/account/reappropriationOfBudgetAuthorization.js" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>
	
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,	
	});
	
	$("#prRevBudgetCodeO").change(function(event) {
		var stt = 0;
		var parentCode = 0;
		stt = parseFloat($('#orginalEstamtO').val());
		parentCode = parseFloat($('#prCollectedO').val());
		if ((stt != "" && stt != undefined && !isNaN(stt))
				&& (parentCode != "" && parentCode != undefined && !isNaN(parentCode))) {
			var num = (stt+parentCode);
			var result = num.toFixed(2);
			$("#revisedEstamtO").val(result);
		}
		
	});
	
	$("#budgetCodeExp").change(function(event) {
		var stt = 0;
		var parentCode = 0;
		stt = parseFloat($('#orginalEstamtExp').val());
		parentCode = parseFloat($('#expenditureAmtExp').val());
		if ((stt != "" && stt != undefined && !isNaN(stt))
				&& (parentCode != "" && parentCode != undefined && !isNaN(parentCode))) {
			var num = (stt+parentCode);
			var result = num.toFixed(2);
			$("#revisedEstamtExp").val(result);
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

<c:if test="${tbAcBudgetReappOfAuthorization.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>

<script>
 $('#sacHeadId${count}').val($('#secondaryId').val());
</script>

<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="budget.reappropriation.authorization.title"
				text="" />
		</h2>
	<apptags:helpDoc url="ReappropriationOfBudgetAuthorization.html" helpDocRefURL="ReappropriationOfBudgetAuthorization.html"></apptags:helpDoc>	
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetReappOfAuthorization" method="POST"
			action="ReappropriationOfBudgetAuthorization.html">

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
			<form:hidden path="paAdjid" />
			<form:hidden path="cpdBugtypeIdHidden" id="cpdBugtypeIdHidden" />
			<form:hidden path="approvedDup" id="approvedDup"></form:hidden>
			<form:hidden path="actualTaskId" id="actualTaskId" />
			<form:hidden path="budgetTranRefNo" id="budgetTranRefNo" />
			<form:hidden path="langId" id="langId" />
			<form:hidden path="userId" id="userId" />
			<form:hidden path="createdDate" id="createdDate" />
			<form:hidden path="lgIpMac" id="lgIpMac" />
			
			<input type="hidden"
				value="${fn:length(tbAcBudgetReappOfAuthorization.bugprojRevBeanList1)}"
				id="revCount" />
			<input type="hidden"
				value="${fn:length(tbAcBudgetReappOfAuthorization.bugprojExpBeanList1)}"
				id="expCount" />

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
									code="budget.reappropriation.master.financialyear" text="" /></label>
							<div class="col-sm-4">
								<form:select id="faYearid" path=""
									cssClass="form-control mandColorClass"
									onchange="setHiddenField(this);"
									disabled="${viewMode ne 'true'}">
									<c:forEach items="${financeMap}" varStatus="status"
										var="financeMap">
										<form:option value="${financeMap.key}"
											code="${financeMap.key}">${financeMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>
							
							<c:if test="${budgetTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.reappropriation.master.budgettype" text="" /></label>
							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="REX" />
								<form:hidden path="cpdBugtypeId" id="cpdBugtypeId" />
								<form:select path="cpdBugtypeId"
									class="form-control mandColorClass" id="cpdBugtypeId"
									onchange="loadBudgetReappropriationData(this)"
									disabled="${viewMode ne 'true'}">
									<form:option value="">
										<spring:message
											code="budget.reappropriation.master.selectbudgettype" text="" />
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
						
						<c:if test="${budgetSubTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.additionalsupplemental.master.budgetsubtype"
									text="" /></label>

							<div class="col-sm-4">
								<form:hidden path="cpdBugSubTypeId" id="cpdBugSubTypeId" />
								<form:select path="cpdBugSubTypeId"
									class="form-control mandColorClass" id="cpdBugSubTypeId"
									disabled="${viewMode ne 'true'}"
									onchange="loadBudgetReappropriationData(this)">
									<form:option value="">
										<spring:message
											code="budget.additionalsupplemental.master.selectbudgetsubtype"
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
							
							<div>
							<c:if test="${fieldStatus == 'Y'}">
							
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.budget.code.master.fieldcode" /></label>

								<c:if test="${MODE_DATA == 'create' || MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
									 <c:if test="${MODE_DATA == 'EDIT'}">
									   <form:hidden path="fieldId"/>
									  </c:if>
										<form:select id="fieldId" path="fieldId"
											cssClass="form-control chosen-select-no-results"
											disabled="true"
											data-rule-required="true">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectfieldcode" />
											</form:option>
											<c:forEach items="${listOfTbAcFieldMasterItems}"
												varStatus="status" var="fieldItem">
												<form:option value="${fieldItem.key}"
													code="${fieldItem.key}">${fieldItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
							</c:if>
							</div>
							
						</div>

						<form:hidden path="bugReappMasterDtoList[${count}].paAdjid"
							id="paAdjid" value="${bugReappMasterDtoList[count].paAdjid}" />
					</fieldset> <input type="hidden" value="${viewMode}" id="test" /> <c:if
						test="${!viewMode}">
						<div id="prProjectionid" class="hide">

							<div class="panel-group accordion-toggle">


								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse"
												href="#collapse_two"><spring:message
													code="budget.reappropriation.master.from" text="" /></a>
										</h4>
									</div>
									<div id="collapse_two" class="panel-collapse collapse in">
										<div class="panel-body">

											<div class="table-overflow-sm">
												<table
													class="table  table-bordered table-striped appendableClass"
													id="prProjectionid1">
													<thead>
														<tr>
															<th style="width: 150px;"><spring:message
																	code="budget.reappropriation.master.departmenttype"
																	text="" /><span class="mand">*</span></th>
															<th style="width: 250px;"><spring:message
																	code="budget.reappropriation.master.budgetCode" text="" /><span
																class="mand">*</span></th>
															<th><spring:message
																	code="budget.reappropriation.master.budget" text="" /></th>
															<th><spring:message
																	code="budget.reappropriation.master.currentbalance"
																	text="" /></th>
															<th><spring:message
																	code="budget.reappropriation.master.deductamount"
																	text="" /><span class="mand">*</span></th>
															<th><spring:message
																	code="budget.reappropriation.master.revisedbalance"
																	text="" /></th>
														</tr>
													</thead>
													<tbody>
														<c:if test="${viewMode eq 'EDIT' }">
															<input type=hidden value="edit" id="editMode" />

															<c:forEach
																items="${tbAcBudgetReappOfAuthorization.bugprojRevBeanList1}"
																var="funMasterLevel" varStatus="status">
																<c:set value="${status.index}" var="count"></c:set>

																<tr class="revenueClass">
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].prProjectionidRevDynamic"
																		value="${bugprojRevBeanList1[count].prProjectionidRevDynamic}"
																		code="${bugprojRevBeanList1[count].prProjectionidRevDynamic}"
																		id="prProjectionidRevDynamic${count}" />
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].langId" />
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].userId" />
																	<form:hidden path="bugprojRevBeanList1[${count}].orgid" />
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].lgIpMac" />
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].paAdjidTr"
																		value="${bugprojRevBeanList1[count].paAdjidTr}"
																		code="${bugprojRevBeanList1[count].paAdjidTr}"
																		id="paAdjidTr${count}" />

																	<td><form:select
																			path="bugprojRevBeanList1[${count}].dpDeptid" disabled="true"
																			cssClass="form-control mandColorClass chosen-select-no-results"
																			id="dpDeptid${count}"
																			onchange="onDepartmentWiseBudgetHeadChange(${count})">
																			<form:option value="">
																				<spring:message
																					code="budget.reappropriation.master.select" text="" />
																			</form:option>
																			<c:forEach items="${deptMap}" varStatus="status"
																				var="deptMap">
																				<form:option value="${deptMap.key}"
																					code="${deptMap.key}">${deptMap.value}</form:option>
																			</c:forEach>
																		</form:select></td>

																	<td><form:select 
																			path="bugprojRevBeanList1[${count}].prRevBudgetCode" disabled="true"
																			cssClass="form-control mandColorClass chosen-select-no-results"
																			id="prRevBudgetCode${count}"
																			onchange="getOrgBalAmount1(${count})">
																			<form:option value="">
																				<spring:message
																					code="budget.reappropriation.master.select" text="" />
																			</form:option>
																			<c:forEach
																				items="${tbAcBudgetReappOfAuthorization.bugprojRevBeanList1[count].budgetMapDynamic}"
																				varStatus="status" var="budgetCodeItem">
																				<form:option value="${budgetCodeItem.key}"
																					code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
																			</c:forEach>
																		</form:select></td>



																	<td><form:input
																			path="bugprojRevBeanList1[${count}].orginalEstamt"
																			id="orginalEstamt${count}"
																			class="  form-control text-right"
																			readonly="${viewMode ne 'true' }" /></td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].prProjected"
																			id="prProjected${count}"
																			class="  form-control text-right"
																			readonly="${viewMode ne 'true' }" /></td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].prCollected"
																			id="prCollected${count}"
																			class="form-control text-right mandColorClass"
																			onkeypress="return hasAmount(event, this, 13, 2)"
																			disabled="true" onkeyup="copyContent(this)"
																			onchange="getAmountFormatInDynamic((this),'prCollected')" />
																	</td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].revisedEstamt"
																			id="revisedEstamt${count}"
																			class="  form-control text-right"
																			readonly="${viewMode ne 'true' }" /></td>

																</tr>
															</c:forEach>
														</c:if>

													</tbody>
												</table>
											</div>

										</div>
									</div>
								</div>

								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse"
												href="#collapse_one"><spring:message
													code="budget.reappropriation.master.to" text="" /></a>
										</h4>
									</div>

									<div id="collapse_one" class="panel-collapse collapse in">
										<div class="panel-body">
											<div class="table-overflow-sm">

												<table class="table  table-bordered table-striped">

													<tr>
														<th style="width: 150px;"><spring:message
																code="budget.reappropriation.master.departmenttype"
																text="" /><span class="mand">*</span></th>
														<th style="width: 250px;"><spring:message
																code="budget.reappropriation.master.budgetCode" text="" /><span
															class="mand">*</span></th>
														<th><spring:message
																code="budget.reappropriation.master.budget" text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.currentbalance"
																text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.reappropriationamount"
																text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.revisedbudget"
																text="" /></th>
													</tr>

													<tr class="revenueClass">
														<form:hidden
															path="bugprojRevBeanList[0].prProjectionidRev"
															value="${bugprojRevBeanList[0].prProjectionidRev}"
															code="bugprojRevBeanList[0].prProjectionidRev"
															id="prProjectionidRev" />
														<form:hidden path="bugprojRevBeanList[0].langId" />
														<form:hidden path="bugprojRevBeanList[0].userId" />
														<form:hidden path="bugprojRevBeanList[0].orgid" />
														<form:hidden path="bugprojRevBeanList[0].lgIpMac" />

														<td><form:select
																path="bugprojRevBeanList[0].dpDeptid" disabled="true"
																cssClass="form-control mandColorClass chosen-select-no-results"
																id="dpDeptidRev"
																onchange="onDepartmentWiseBudgetHeadDesc(this)">
																<form:option value="">
																	<spring:message
																		code="budget.reappropriation.master.select" text="" />
																</form:option>
																<c:forEach items="${deptMap}" varStatus="status"
																	var="deptMap">
																	<form:option value="${deptMap.key}"
																		code="${deptMap.key}">${deptMap.value}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:select
																path="bugprojRevBeanList[0].prRevBudgetCode" disabled="true"
																cssClass="form-control mandColorClass chosen-select-no-results"
																id="prRevBudgetCodeO" onchange="getOrgBalAmount(this)">
																<form:option value="">
																	<spring:message
																		code="budget.reappropriation.master.select" text="" />
																</form:option>
																<c:forEach items="${budgetCodeMap}" varStatus="status"
																	var="budgetCodeItem">
																	<form:option value="${budgetCodeItem.key}"
																		code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
																</c:forEach>
															</form:select></td>


														<td><form:input
																path="bugprojRevBeanList[0].orginalEstamt"
																id="orginalEstamtO" class="  form-control text-right"
																readonly="${viewMode ne 'true' }" /></td>

														<td><form:input
																path="bugprojRevBeanList[0].prProjected"
																id="prProjectedO" class="  form-control text-right"
																readonly="${viewMode ne 'true' }" /></td>
														<td><form:input
																path="bugprojRevBeanList[0].prCollected"
																id="prCollectedO" class="  form-control text-right"
																readonly="${viewMode ne 'true' }" /></td>

														<td><form:input
																path="bugprojRevBeanList[0].revisedEstamt"
																id="revisedEstamtO" class="  form-control text-right "
																readonly="${viewMode ne 'true' }" /></td>
													</tr>
												</table>
											</div>

										</div>
									</div>
								</div>

					  </div>

							<div class="form-group">
								<label class="control-label col-sm-2 required-control">
									<spring:message code="account.Authorizer.Remark" text="Authorizer Remark"></spring:message>
								</label>
								<div class="col-sm-10">
									<form:textarea id="remark" path="bugprojRevBeanList[0].remark" disabled="true"
										class="form-control mandColorClass" maxLength="200" />
								</div>
							</div>

						</div>
					</c:if> <c:if test="${!viewMode}">
						<div id="prExpenditureid" class="hide">
							<div class="panel-group accordion-toggle">

								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse1"
												href="#collapse_two2"><spring:message
													code="budget.reappropriation.master.from" text="" /></a>
										</h4>
									</div>
									<div id="collapse_two2" class="panel-collapse collapse in">
										<div class="panel-body">

											<div class="table-overflow-sm">
												<table
													class="table  table-bordered table-striped appendableExpClass"
													id="prExpenditureid1">

													<tr>
														<th style="width: 150px;"><spring:message
																code="budget.reappropriation.master.departmenttype"
																text="" /><span class="mand">*</span></th>
														<th style="width: 250px;"><spring:message
																code="budget.reappropriation.master.budgetCode" text="" /><span
															class="mand">*</span></th>
														<th><spring:message
																code="budget.reappropriation.master.budget" text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.currentbalance"
																text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.deductamount"
																text="" /><span class="mand">*</span></th>
														<th><spring:message
																code="budget.reappropriation.master.revisedbalance"
																text="" /></th>
													</tr>

													<c:if test="${viewMode eq 'EDIT' }">
														<input type=hidden value="edit" id="editModeExp" />

														<c:forEach
															items="${tbAcBudgetReappOfAuthorization.bugprojExpBeanList1}"
															var="funMasterLevel" varStatus="status">
															<c:set value="${status.index}" var="count"></c:set>

															<tr class="revenueClass">
																<form:hidden
																	path="bugprojExpBeanList1[${count}].prExpenditureidExpDynamic"
																	value="${bugprojExpBeanList1[count].prExpenditureidExpDynamic}"
																	code="${bugprojExpBeanList1[count].prExpenditureidExpDynamic}"
																	id="prExpenditureidExpDynamic${count}" />
																<form:hidden path="bugprojExpBeanList1[${count}].langId" />
																<form:hidden path="bugprojExpBeanList1[${count}].userId" />
																<form:hidden path="bugprojExpBeanList1[${count}].orgid" />
																<form:hidden
																	path="bugprojExpBeanList1[${count}].lgIpMac" />
																<form:hidden
																	path="bugprojExpBeanList1[${count}].paAdjidTr"
																	value="${bugprojExpBeanList1[count].paAdjidTr}"
																	code="${bugprojExpBeanList1[count].paAdjidTr}"
																	id="paAdjidTrExp${count}" />

																<td><form:select
																		path="bugprojExpBeanList1[${count}].dpDeptid"
																		cssClass="form-control mandColorClass chosen-select-no-results"
																		id="dpDeptidExp${count}"
																		onchange="onDepartmentWiseBudgetHeadExpChange(${count})">
																		<form:option value="">
																			<spring:message
																				code="budget.reappropriation.master.select" text="" />
																		</form:option>
																		<c:forEach items="${deptMap}" varStatus="status"
																			var="deptMap">
																			<form:option value="${deptMap.key}"
																				code="${deptMap.key}">${deptMap.value}</form:option>
																		</c:forEach>
																	</form:select></td>

																<td><form:select
																		path="bugprojExpBeanList1[${count}].prExpBudgetCode"
																		cssClass="form-control mandColorClass chosen-select-no-results"
																		id="prExpBudgetCode${count}"
																		onchange="getOrgBalExpAmount1(${count})">
																		<form:option value="">
																			<spring:message
																				code="budget.reappropriation.master.select" text="" />
																		</form:option>
																		<c:forEach
																			items="${tbAcBudgetReappOfAuthorization.bugprojExpBeanList1[count].budgetMapDynamicExp}"
																			varStatus="status" var="budgetCodeItem">
																			<form:option value="${budgetCodeItem.key}"
																				code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
																		</c:forEach>
																	</form:select></td>



																<td><form:input
																		path="bugprojExpBeanList1[${count}].orginalEstamt"
																		id="ExporginalEstamt${count}"
																		class="  form-control text-right"
																		readonly="${viewMode ne 'true' }" /></td>

																<td><form:input
																		path="bugprojExpBeanList1[${count}].prBalanceAmt"
																		id="prBalanceAmt${count}"
																		class="  form-control text-right"
																		readonly="${viewMode ne 'true' }" /></td>

																<td><form:input
																		path="bugprojExpBeanList1[${count}].expenditureAmt"
																		id="expenditureAmt${count}"
																		class="form-control text-right mandColorClass"
																		onkeypress="return hasAmount(event, this, 13, 2)"
																		disabled="${viewMode}" onkeyup="copyContentExp(this)"
																		onchange="getAmountFormatInDynamic((this),'expenditureAmt')" />
																</td>

																<td><form:input
																		path="bugprojExpBeanList1[${count}].revisedEstamt"
																		id="ExprevisedEstamt${count}"
																		class="  form-control text-right "
																		readonly="${viewMode ne 'true' }" /></td>



															</tr>

														</c:forEach>
													</c:if>

												</table>
											</div>

										</div>
									</div>
								</div>

								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">

											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse1"
												href="#collapse_one1"><spring:message
													code="budget.reappropriation.master.to" text="" /></a>
										</h4>
									</div>
									<div id="collapse_one1" class="panel-collapse collapse in">
										<div class="panel-body">
											<div class="table-overflow-sm">
												<table class="table  table-bordered table-striped">
													<tr>
														<th style="width: 150px;"><spring:message
																code="budget.reappropriation.master.departmenttype"
																text="" /><span class="mand">*</span></th>
														<th style="width: 250px;"><spring:message
																code="budget.reappropriation.master.budgetCode" text="" /><span
															class="mand">*</span></th>
														<th><spring:message
																code="budget.reappropriation.master.budget" text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.currentbalance"
																text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.reappropriationamount"
																text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.revisedbudget"
																text="" /></th>
													</tr>

													<tr class="revenueClass">
														<form:hidden
															path="bugprojExpBeanList[0].prExpenditureidExp"
															value="${bugprojExpBeanList[0].prExpenditureidExpDynamic}"
															code="bugprojExpBeanList[${count}].prExpenditureidExp"
															id="prExpenditureidExp" />
														<form:hidden path="bugprojExpBeanList[0].langId" />
														<form:hidden path="bugprojExpBeanList[0].userId" />
														<form:hidden path="bugprojExpBeanList[0].orgid" />
														<form:hidden path="bugprojExpBeanList[0].lgIpMac" />

														<td><form:select
																path="bugprojExpBeanList[0].dpDeptid"
																cssClass="form-control mandColorClass chosen-select-no-results"
																id="ExpdpDeptid"
																onchange="onDepartmentWiseBudgetHeadExpDesc(this)">
																<form:option value="">
																	<spring:message
																		code="budget.reappropriation.master.select" text="" />
																</form:option>
																<c:forEach items="${deptMap}" varStatus="status"
																	var="deptMap">
																	<form:option value="${deptMap.key}"
																		code="${deptMap.key}">${deptMap.value}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:select
																path="bugprojExpBeanList[0].prExpBudgetCode"
																cssClass="form-control mandColorClass chosen-select-no-results"
																id="budgetCodeExp" onchange="getOrgBalExpAmount(this)">
																<form:option value="">
																	<spring:message
																		code="budget.reappropriation.master.select" text="" />
																</form:option>
																<c:forEach items="${budgetCodeExpStaticMap}"
																	varStatus="status" var="budgetCodeItem1">
																	<form:option value="${budgetCodeItem1.key}"
																		code="${budgetCodeItem1.key}">${budgetCodeItem1.value}</form:option>
																</c:forEach>
															</form:select></td>



														<td><form:input
																path="bugprojExpBeanList[0].orginalEstamt"
																id="orginalEstamtExp" class="  form-control text-right"
																readonly="${viewMode ne 'true' }" /></td>

														<td><form:input
																path="bugprojExpBeanList[0].prBalanceAmt"
																id="prBalanceAmtExp" class="  form-control text-right"
																readonly="${viewMode ne 'true' }" /></td>
														<td><form:input
																path="bugprojExpBeanList[0].expenditureAmt"
																id="expenditureAmtExp" class="  form-control text-right"
																readonly="${viewMode ne 'true' }" /></td>

														<td><form:input
																path="bugprojExpBeanList[0].revisedEstamt"
																id="revisedEstamtExp" class="  form-control text-right "
																readonly="${viewMode ne 'true' }" /></td>

													</tr>

												</table>
											</div>


										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2 required-control">
									<spring:message code="account.Authorizer.Remark" text="Authorizer Remark"></spring:message>
								</label>
								<div class="col-sm-10">
									<form:textarea id="expRemark" disabled="true" readonly="true"
										path="bugprojExpBeanList[0].expRemark"
										class="form-control mandColorClass" maxLength="200" />
								</div>
							</div>

						</div>
					</c:if>


                            <!-- Uploaded Documents start-->
								<c:if test="${not empty documentDtos}">
									<h4 class="margin-top-0 margin-bottom-10 panel-title">
										<a data-toggle="collapse" href="#DocumentUpload"><spring:message
												code="account.common.account.doc" /></a>
									</h4>
									<div id="DocumentUpload">
										<fieldset class="fieldRound">
											<div class="overflow">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><label class="tbold"><spring:message
																			code="account.common.account.srno" text="Sr No" /></label></th>
																<th><label class="tbold"><spring:message
																			code="account.common.account.attachBy" text="Attach By" /></label></th>
																<th><label class="tbold"><spring:message
																			code="account.common.account.download" text="Download"/></label></th>
															</tr>

															<c:forEach items="${documentDtos}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td><label>${lk.count}</label></td>
																	<td><label>${lookUp.attBy}</label></td>
																	<td><c:set var="links"
																			value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
																		<apptags:filedownload filename="${lookUp.attFname}"
																			filePath="${lookUp.attPath}"
																			dmsDocId="${lookUp.dmsDocId}"
																			actionUrl="AccountBillAuthorization.html?Download"></apptags:filedownload>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</c:if>
					<!-- Uploaded Documents end-->

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />
			<div class="text-center padding-top-10">
				<spring:url var="cancelButtonURL"
					value="AdminHome.html" />
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
	
	});	
</script>

