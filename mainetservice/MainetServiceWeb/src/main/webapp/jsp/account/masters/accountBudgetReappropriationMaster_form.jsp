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
<script src="js/account/accountBudgetReappropriationMaster.js" />
<script src="js/mainet/file-upload.js"></script>
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

<c:if test="${tbAcBudgetReappropriation.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>

<script>
 $('#sacHeadId${count}').val($('#secondaryId').val());
</script>

<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="budget.reappropriation.master.title" text="" />
		</h2>
	<apptags:helpDoc url="AccountBudgetReappropriation.html" helpDocRefURL="AccountBudgetReappropriation.html"></apptags:helpDoc>	
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetReappropriation" method="POST"
			action="AccountBudgetReappropriation.html">

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
			<form:hidden path="paAdjid" id="paAdjid" />
			<form:hidden path="cpdBugtypeIdHidden" id="cpdBugtypeIdHidden" />
			<form:hidden path="budgetTranRefNo" id="budgetTranRefNo" />
			<form:hidden path="langId" id="langId" />
			<form:hidden path="userId" id="userId" />
			<form:hidden path="createdDate" id="createdDate" />
			<form:hidden path="lgIpMac" id="lgIpMac" />
			<form:hidden path="removeFileById" id="removeFileById" />
			<form:hidden path="" id="filedConfigure" value="${fieldStatus}"/>
			<input type="hidden" value="${isCrossReapropriation}" id="isCrossReapropriation" />
			
			<input type="hidden"
				value="${fn:length(tbAcBudgetReappropriation.bugprojRevBeanList1)}"
				id="revCount" />
			<input type="hidden"
				value="${fn:length(tbAcBudgetReappropriation.bugprojExpBeanList1)}"
				id="expCount" />
			<form:hidden path="" />

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
									onchange="setHiddenField(this);" data-rule-required="true">
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

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="REX" />
										<form:select path="cpdBugtypeId"
											class="form-control mandColorClass" id="cpdBugtypeId"
											onchange="loadBudgetReappropriationData(this)"
											disabled="${viewMode}" data-rule-required="true">
											<form:option value="">
												<spring:message
													code="budget.reappropriation.master.selectbudgettype"
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
											onchange="loadBudgetReappropriationData(this)"
											disabled="${viewMode ne 'true'}">
											<form:option value="">
												<spring:message
													code="budget.reappropriation.master.selectbudgettype"
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

						</div>

						<div class="form-group">

							<c:if test="${budgetSubTypeStatus == 'Y'}">
								<label class="col-sm-2 control-label"><spring:message
										code="budget.additionalsupplemental.master.budgetsubtype"
										text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">

									<div class="col-sm-4">
										<form:select path="cpdBugSubTypeId"
											class="form-control mandColorClass" id="cpdBugSubTypeId"
											disabled="${viewMode}"
											onchange="loadBudgetReappropriationData(this)"
											>
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

								<c:if test="${MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
										<form:hidden path="cpdBugSubTypeId" id="cpdBugSubTypeId" />
										<form:select path="cpdBugSubTypeId"
											class="form-control mandColorClass" id="cpdBugSubTypeId"
											disabled="${viewMode ne 'true'}"
											onchange="loadBudgetReappropriationData(${0})">
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
											disabled="${viewMode == 'EDIT'}" 
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
																	code="budget.reappropriation.master.transferBudget"
																	text="" /><span class="mand">*</span></th>
															<th><spring:message
																	code="budget.reappropriation.master.revisedbalance"
																	text="" /></th>
															<c:if test="${MODE_DATA == 'create'}">
																<th width="8%"><spring:message
																		code="account.common.add.remove" /></th>
															</c:if>
														</tr>
													</thead>
													<tbody>

														<c:if test="${MODE_DATA == 'create'}">
															<input type=hidden value="create" id="createMode" />

															<tr class="revenueClass">
																<form:hidden
																	path="bugprojRevBeanList1[${count}].prProjectionidRevDynamic"
																	value="${bugprojRevBeanList1[count].prProjectionidRevDynamic}"
																	code="${bugprojRevBeanList1[count].prProjectionidRevDynamic}"
																	id="prProjectionidRevDynamic${count}" />
																<form:hidden path="bugprojRevBeanList1[${count}].langId" />
																<form:hidden path="bugprojRevBeanList1[${count}].userId" />
																<form:hidden path="bugprojRevBeanList1[${count}].orgid" />
																<form:hidden
																	path="bugprojRevBeanList1[${count}].lgIpMac" />

																<td><form:select
																		path="bugprojRevBeanList1[${count}].dpDeptid"
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
																		path="bugprojRevBeanList1[${count}].prRevBudgetCode"
																		cssClass="form-control mandColorClass chosen-select-no-results"
																		id="prRevBudgetCode${count}"
																		onchange="getOrgBalAmount1(${count})">
																		<form:option value="">
																			<spring:message
																				code="budget.reappropriation.master.select" text="" />
																		</form:option>
																	</form:select></td>


																<td><form:input
																		path="bugprojRevBeanList1[${count}].orginalEstamt"
																		id="orginalEstamt${count}"
																		class=" form-control text-right"
																		readonly="${viewMode ne 'true' }" /></td>

																<td><form:input
																		path="bugprojRevBeanList1[${count}].prProjected"
																		id="prProjected${count}"
																		class=" form-control text-right "
																		readonly="${viewMode ne 'true' }" /></td>
																<td><form:input
																		path="bugprojRevBeanList1[${count}].prCollected"
																		id="prCollected${count}"
																		class="form-control text-right mandColorClass"
																		onkeypress="return hasAmount(event, this, 13, 2)"
																		disabled="${viewMode}" onkeyup="copyContent(this)"
																		onchange="getAmountFormatInDynamic((this),'prCollected')" />
																</td>

																<td><form:input
																		path="bugprojRevBeanList1[${count}].revisedEstamt"
																		id="revisedEstamt${count}"
																		class="  form-control text-right "
																		readonly="${viewMode ne 'true' }" /></td>
																<td class="text-center"><c:if test="${!viewMode}">
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
															<input type=hidden value="edit" id="editMode" />

															<c:forEach
																items="${tbAcBudgetReappropriation.bugprojRevBeanList1}"
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
																			path="bugprojRevBeanList1[${count}].dpDeptid"
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
																			path="bugprojRevBeanList1[${count}].prRevBudgetCode"
																			cssClass="form-control mandColorClass chosen-select-no-results"
																			id="prRevBudgetCode${count}"
																			onchange="getOrgBalAmount1(${count})">
																			<form:option value="">
																				<spring:message
																					code="budget.reappropriation.master.select" text="" />
																			</form:option>
																			<c:forEach
																				items="${tbAcBudgetReappropriation.bugprojRevBeanList1[count].budgetMapDynamic}"
																				varStatus="status" var="budgetCodeItem">
																				<form:option value="${budgetCodeItem.key}"
																					code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
																			</c:forEach>
																		</form:select></td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].orginalEstamt"
																			id="orginalEstamt${count}"
																			class=" form-control text-right"
																			readonly="${viewMode ne 'true' }" /></td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].prProjected"
																			id="prProjected${count}"
																			class=" form-control text-right "
																			readonly="${viewMode ne 'true' }" /></td>
																	<td><form:input
																			path="bugprojRevBeanList1[${count}].prCollected"
																			id="prCollected${count}"
																			class="form-control text-right mandColorClass"
																			onkeypress="return hasAmount(event, this, 13, 2)"
																			disabled="${viewMode}" onkeyup="copyContent(this)"
																			onchange="getAmountFormatInDynamic((this),'prCollected')" />
																	</td>

																	<td><form:input
																			path="bugprojRevBeanList1[${count}].revisedEstamt"
																			id="revisedEstamt${count}"
																			class="  form-control text-right "
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

													<c:if test="${MODE_DATA == 'create'}">

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
																	path="bugprojRevBeanList[0].dpDeptid"
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
																	path="bugprojRevBeanList[0].prRevBudgetCode"
																	cssClass="form-control mandColorClass chosen-select-no-results"
																	id="prRevBudgetCodeO" onchange="getOrgBalAmount(this)">
																	<form:option value="">
																		<spring:message
																			code="budget.reappropriation.master.select" text="" />
																	</form:option>
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
													</c:if>

													<c:if test="${MODE_DATA == 'EDIT'}">

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
																	path="bugprojRevBeanList[0].dpDeptid"
																	cssClass="form-control chosen-select-no-results"
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
																	path="bugprojRevBeanList[0].prRevBudgetCode"
																	cssClass="form-control chosen-select-no-results"
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

													</c:if>

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
												<!-- <table class="table table-bordered table-condensed appendableClassO" id="prExpenditureid1"> -->
												<table
													class="table  table-bordered table-striped appendableExpClass"
													id="prExpenditureid1">
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
																code="budget.reappropriation.master.transferBudget"
																text="" /><span class="mand">*</span></th>
														<th><spring:message
																code="budget.reappropriation.master.revisedbalance"
																text="" /></th>
														<c:if test="${MODE_DATA == 'create'}">
															<th width="8%"><spring:message code="account.common.add.remove" /></th>
														</c:if>
													</tr>
													</thead>
													<tbody>
													<c:if test="${MODE_DATA == 'create'}">
														<input type=hidden value="create" id="createModeExp" />

														<tr class="revenueClass">

															<form:hidden
																path="bugprojExpBeanList1[${count}].prExpenditureidExpDynamic"
																value="${bugprojExpBeanList1[count].prExpenditureidExpDynamic}"
																code="${bugprojExpBeanList1[count].prExpenditureidExpDynamic}"
																id="prExpenditureidExpDynamic${count}" />
															<form:hidden path="bugprojExpBeanList1[${count}].langId" />
															<form:hidden path="bugprojExpBeanList1[${count}].userId" />
															<form:hidden path="bugprojExpBeanList1[${count}].orgid" />
															<form:hidden path="bugprojExpBeanList1[${count}].lgIpMac" />


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

															<td class="text-center"><c:if test="${!viewMode}">
																	<button title="Add"
																		class="btn btn-success btn-sm addButton"
																		onclick="test()" id="addButton${count}">
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
														<input type=hidden value="edit" id="editModeExp" />

														<c:forEach
															items="${tbAcBudgetReappropriation.bugprojExpBeanList1}"
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
																			items="${tbAcBudgetReappropriation.bugprojExpBeanList1[count].budgetMapDynamicExp}"
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

													<c:if test="${MODE_DATA == 'create'}">

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
																	id="expenditureAmtExp"
																	class="  form-control text-right"
																	readonly="${viewMode ne 'true' }" /></td>

															<td><form:input
																	path="bugprojExpBeanList[0].revisedEstamt"
																	id="revisedEstamtExp"
																	class="  form-control text-right "
																	readonly="${viewMode ne 'true' }" /></td>

														</tr>
													</c:if>

													<c:if test="${MODE_DATA == 'EDIT'}">

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
																	cssClass="form-control chosen-select-no-results"
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
																	cssClass="form-control chosen-select-no-results"
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
																	id="expenditureAmtExp"
																	class="  form-control text-right"
																	readonly="${viewMode ne 'true' }" /></td>

															<td><form:input
																	path="bugprojExpBeanList[0].revisedEstamt"
																	id="revisedEstamtExp"
																	class="  form-control text-right "
																	readonly="${viewMode ne 'true' }" /></td>

														</tr>
													</c:if>

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
									<form:textarea id="expRemark"
										path="bugprojExpBeanList[0].expRemark" class="form-control"
										maxLength="200" />
								</div>
								
							</div>

						</div>
					</c:if>
                <!-- file upload start -->
                      <div class="form-group" id="reload">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="account.subRtgsPayment.uploadDoc" text="Upload document" /></label>

					<div class="col-sm-3 text-left">
						<form:hidden path="attachments[0].documentName" />
						<apptags:formField fieldType="7"
							fieldPath="attachments[0].uploadedDocumentPath" currentCount="0"
							showFileNameHTMLId="true" folderName="0"
							fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
						</apptags:formField>
						<small class="text-blue-2"><spring:message
							code="account.budgt.uploadInvoice5Mb" text="(Upload Invoice upto 5MB )" /></small>
					</div>

					<c:if test="${MODE_DATA == 'EDIT'}">
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="scheme.document.name" text="" /></th>
										<th><spring:message code="scheme.view.document" text="" /></th>
										<th><spring:message code="scheme.action" text=""></spring:message>
										</th>
									</tr>
									<c:forEach items="${tbAcBudgetReappropriation.attachDocsList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}"
													filePath="${lookUp.attPath}"
													actionUrl="AccountBudgetReappropriation.html?Download" /></td>
											<td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</c:if>
				</div>
                             <!--  file upload end -->

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />
			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value='<spring:message code="account.bankmaster.save" text="Save" />'> </input>
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="<spring:message code="account.bankmaster.save" text="Save" />"> </input>
				</c:if>
				<spring:url var="cancelButtonURL"
					value="AccountBudgetReappropriation.html" />
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

