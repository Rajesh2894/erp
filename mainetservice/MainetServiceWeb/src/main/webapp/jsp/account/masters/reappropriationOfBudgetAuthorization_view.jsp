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
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetReappOfAuthorization" method="POST"
			action="ReappropriationOfBudgetAuthorization.html">

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
									code="budget.reappropriation.master.financialyear" text="" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="financialYearDesc"
									class="form-control" id="financialYearDesc" />
							</div>

							<c:if test="${budgetTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label "><spring:message
									code="budget.reappropriation.master.budgettype" text="" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="cpdBugtypeDesc"
									class="form-control" id="cpdBugtypeDesc" />
							</div>
							</c:if>
							
						</div>

						<div class="form-group">
						
							<c:if test="${budgetSubTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label "><spring:message
									code="budget.additionalsupplemental.master.budgetsubtype"
									text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="cpdBugsubtypeDesc"
									class="form-control" id="cpdBugsubtypeDesc" />
							</div>
							</c:if>
							
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
																	text="" /></th>
															<th><spring:message
																	code="budget.reappropriation.master.revisedbalance"
																	text="" /></th>
														</tr>
													</thead>
													<tbody>

														<c:forEach
															items="${tbAcBudgetReappOfAuthorization.bugprojRevBeanList1}"
															var="prRevList" varStatus="status">
															<c:set value="${status.index}" var="count"></c:set>

															<c:if test="${viewMode eq 'VIEW' }">
																<tr class="revenueClass">
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].prProjectionid"
																		value="${bugprojRevBeanList1[count].prProjectionid}" />
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].langId" />
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].userId" />
																	<form:hidden path="bugprojRevBeanList1[${count}].orgid" />
																	<form:hidden
																		path="bugprojRevBeanList1[${count}].lgIpMac" />

																	<td><c:set value="${prRevList.dpDeptid}"
																			var="dpDeptid"></c:set> <c:forEach items="${deptMap}"
																			varStatus="status" var="deptMap">
																			<c:if test="${dpDeptid eq deptMap.key}">
																				<form:input type="text" value="${deptMap.value}"
																					path="" class="form-control" id="dpDeptid" />
																			</c:if>
																		</c:forEach></td>
																	<td><c:set value="${prRevList.prRevBudgetCode}"
																			var="prRevBudgetCode"></c:set> <c:forEach
																			items="${accountBudgetCodeAllocationMap}"
																			varStatus="status" var="budgetCodeRevItem">
																			<c:if
																				test="${prRevBudgetCode eq budgetCodeRevItem.key}">
																				<form:input type="text"
																					value="${budgetCodeRevItem.value}" path=""
																					class="form-control" id="prRevBudgetCode" />
																			</c:if>
																		</c:forEach></td>



																	<td><form:input
																			path="bugprojRevBeanList1[${count}].orginalEstamt"
																			id="orginalEstamt${count}"
																			class="  form-control text-right"
																			readonly="${viewMode ne 'true' }" /></td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].prProjected"
																			id="prProjected${count}"
																			class="  form-control text-right"
																			disabled="${viewMode}" /></td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].prCollected"
																			id="prCollected${count}"
																			class="  form-control text-right mandColorClass"
																			disabled="${viewMode}" onkeyup="copyContent(this)" />
																	</td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].revisedEstamt"
																			id="revisedEstamt${count}"
																			class="  form-control text-right"
																			readonly="${viewMode ne 'true' }" /></td>

																</tr>
															</c:if>
														</c:forEach>

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
													<c:forEach
														items="${tbAcBudgetReappOfAuthorization.bugprojRevBeanList}"
														var="prRevSList" varStatus="status">

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

															<td><c:set value="${prRevSList.dpDeptid}"
																	var="dpDeptid"></c:set> <c:forEach items="${deptMap}"
																	varStatus="status" var="deptMap">
																	<c:if test="${dpDeptid eq deptMap.key}">
																		<form:input type="text" value="${deptMap.value}"
																			path="" class="form-control" id="deptMap" />
																	</c:if>
																</c:forEach></td>

															<td><c:set value="${prRevSList.prRevBudgetCode}"
																	var="prRevBudgetCode"></c:set> <c:forEach
																	items="${budgetCodeMap}" varStatus="status"
																	var="budgetCodeRevSItem">
																	<c:if
																		test="${prRevBudgetCode eq budgetCodeRevSItem.key}">
																		<form:input type="text"
																			value="${budgetCodeRevSItem.value}" path=""
																			class="form-control" id="prRevBudgetCode" />
																	</c:if>
																</c:forEach></td>


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
													</c:forEach>
												</table>
											</div>

										</div>
									</div>
								</div>


							</div>

							<div class="form-group">
								<label class="control-label col-sm-2 required-control">
									<spring:message code="budget.reappropriation.master.remark"
										text=""></spring:message>
								</label>
								<div class="col-sm-10">
									<form:textarea id="remark" path="bugprojRevBeanList[0].remark"
										class="form-control" maxLength="200" />
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
																text="" /></th>
														<th><spring:message
																code="budget.reappropriation.master.revisedbalance"
																text="" /></th>
													</tr>

													<c:forEach
														items="${tbAcBudgetReappOfAuthorization.bugprojExpBeanList1}"
														var="prExpList" varStatus="status">
														<c:set value="${status.index}" var="count"></c:set>

														<c:if test="${viewMode eq 'VIEW' }">
															<tr class="revenueClass">
																<form:hidden
																	path="bugprojExpBeanList1[${count}].prExpenditureid"
																	value="${bugprojExpBeanList[count].prExpenditureid}" />
																<form:hidden path="bugprojExpBeanList1[${count}].langId" />
																<form:hidden path="bugprojExpBeanList1[${count}].userId" />
																<form:hidden path="bugprojExpBeanList1[${count}].orgid" />
																<form:hidden
																	path="bugprojExpBeanList1[${count}].lgIpMac" />

																<td><c:set value="${prExpList.dpDeptid}"
																		var="dpDeptidExp"></c:set> <c:forEach
																		items="${deptMap}" varStatus="status" var="deptMapExp">
																		<c:if test="${dpDeptidExp eq deptMapExp.key}">
																			<form:input type="text" value="${deptMapExp.value}"
																				path="" class="form-control" id="deptMapExp" />
																		</c:if>
																	</c:forEach></td>

																<td><c:set value="${prExpList.prExpBudgetCode}"
																		var="prExpBudgetCode"></c:set> <c:forEach
																		items="${accountBudgetCodeAllocationExpMap}"
																		varStatus="status" var="budgetCodeExpItem">
																		<c:if
																			test="${prExpBudgetCode eq budgetCodeExpItem.key}">
																			<form:input type="text"
																				value="${budgetCodeExpItem.value}" path=""
																				class="form-control" id="prRevBudgetCode" />
																		</c:if>
																	</c:forEach></td>



																<td><form:input
																		path="bugprojExpBeanList1[${count}].orginalEstamt"
																		id="ExporginalEstamt${count}"
																		class="  form-control text-right"
																		readonly="${viewMode ne 'true' }" /></td>

																<td><form:input
																		path="bugprojExpBeanList1[${count}].prBalanceAmt"
																		id="prBalanceAmt${count}"
																		class="  form-control text-right"
																		disabled="${viewMode}" /></td>

																<td><form:input
																		path="bugprojExpBeanList1[${count}].expenditureAmt"
																		id="expenditureAmt${count}"
																		class="  form-control text-right mandColorClass"
																		disabled="${viewMode}" onkeyup="copyContent(this)" />
																</td>

																<td><form:input
																		path="bugprojExpBeanList1[${count}].revisedEstamt"
																		id="ExprevisedEstamt${count}"
																		class="  form-control text-right "
																		readonly="${viewMode ne 'true' }" /></td>



															</tr>
														</c:if>
													</c:forEach>

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

													<c:forEach
														items="${tbAcBudgetReappOfAuthorization.bugprojExpBeanList}"
														var="prExpSList" varStatus="status">
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

															<td><c:set value="${prExpSList.dpDeptid}"
																	var="ExpdpDeptid"></c:set> <c:forEach
																	items="${deptMap}" varStatus="status" var="ExpdeptMap">
																	<c:if test="${ExpdpDeptid eq ExpdeptMap.key}">
																		<form:input type="text" value="${ExpdeptMap.value}"
																			path="" class="form-control" id="ExpdeptMap" />
																	</c:if>
																</c:forEach></td>

															<td><c:set value="${prExpSList.prExpBudgetCode}"
																	var="prExpBudgetCode"></c:set> <c:forEach
																	items="${budgetCodeExpStaticMap}" varStatus="status"
																	var="budgetCodeSExpItem">
																	<c:if
																		test="${prExpBudgetCode eq budgetCodeSExpItem.key}">
																		<form:input type="text"
																			value="${budgetCodeSExpItem.value}" path=""
																			class="form-control" id="prRevBudgetCode" />
																	</c:if>
																</c:forEach></td>



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
																	id="expenditureAmtExp"
																	class="  form-control text-right"
																	readonly="${viewMode ne 'true' }" /></td>

															<td><form:input
																	path="bugprojExpBeanList[0].revisedEstamt"
																	id="revisedEstamtExp"
																	class="  form-control text-right "
																	readonly="${viewMode ne 'true' }" /></td>

														</tr>
													</c:forEach>

												</table>
											</div>


										</div>
									</div>
								</div>

							</div>

							<div class="form-group">
								<label class="control-label col-sm-2 required-control">
									<spring:message code="" text="Authorizer Remark"></spring:message>
								</label>
								<div class="col-sm-10">
									<form:textarea id="expRemark"
										path="bugprojExpBeanList[0].expRemark" class="form-control"
										maxLength="200" />
								</div>
							</div>

						</div>
					</c:if>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code="" text="Authorization"></spring:message></label>
						<div class="col-sm-10">

							<label for="approved1" class="radio-inline"><form:radiobutton
									path="approved" value="Y" id="approved1" /> <spring:message
									code="account.pay.tds.approved" text="Approved" /> </label> <label
								for="disApproved" class="radio-inline"><form:radiobutton
									path="approved" value="N" id="disApproved" /> <spring:message
									code="account.pay.tds.disapproved" text="Unapproved" /></label>

						</div>
					</div>

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />
			<div class="text-center padding-top-10">

				<spring:url var="cancelButtonURL"
					value="ReappropriationOfBudgetAuthorization.html" />
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

