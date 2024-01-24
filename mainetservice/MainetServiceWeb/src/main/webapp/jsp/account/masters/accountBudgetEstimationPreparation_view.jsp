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
<script src="js/account/accountBudgetEstimationPreparation.js" />
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
<style>
	.overflow-y-hidden {
		overflow-y: hidden;
	}
	table#revTable thead tr th,
	table#expTable thead tr th,
	table#revTable tbody tr td,
	table#expTable tbody tr td {
		white-space: nowrap;
	}
	table#revTable tbody tr td .chosen-container.chosen-with-drop.chosen-container-active > .chosen-drop,
	table#expTable tbody tr td .chosen-container.chosen-with-drop.chosen-container-active > .chosen-drop {
		position: relative;
	}
	.width-120 {
		width: 120px !important;
	}
	.width-150 {
		width: 150px !important;
	}
	.width-200,
	select.width-200 + .chosen-container {
		width: 200px !important;
	}
</style>

<c:if test="${tbAcBudgetEstimationPreparation.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>



<div class="widget" id="widget">

	<div class="widget-header">
		<h2>
			<spring:message code="budget.estimationpreparation.master.title"
				text="" />
		</h2>
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetEstimationPreparation" method="POST"
			action="AccountBudgetEstimationPreparation.html">
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
									code="budget.estimationpreparation.master.current.financialyear"
									text="Current Financial Year" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="financialYearDesc"
									class="form-control" id="financialYearDesc" />
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="budget.estimationpreparation.master.next.financialyear"
									text="Estimate Financial Year(Next Financial Year)" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="nextFinancialYearDesc"
									class="form-control" id="nextFinancialYearDesc" />
							</div>
							
						</div>

						<div class="form-group">
						
							<label class="col-sm-2 control-label "><spring:message
									code="budget.estimationpreparation.master.departmenttype"
									text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="departmentDesc"
									class="form-control" id="departmentDesc" />
							</div>
							
							<c:if test="${budgetTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label "><spring:message
									code="budget.estimationpreparation.master.budgettype" text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="cpdBugtypeDesc"
									class="form-control" id="cpdBugtypeDesc" />
							</div>
							</c:if>

						</div>
						
						<div class="form-group">
						
						   <c:if test="${budgetSubTypeStatus == 'Y'}">
							<label class="col-sm-2 control-label "><spring:message
									code="budget.estimationpreparation.master.budgetsubtype"
									text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="cpdBugsubtypeDesc"
									class="form-control" id="cpdBugsubtypeDesc" />
							</div>
							</c:if>
							
							<c:if test="${isApplicable}"> 
							<!-- 
							<label for="fundId"
					     	class="col-sm-2 control-label"><spring:message
							code="account.budget.code.master.fundcode" text ="Fund Name"/></label>
							<div class="col-sm-4">
							<form:select path="fundId"
								class="form-control mandColorClass chosen-select-no-results"
								name="fundId" id="fundId"  
								disabled="true">
								<form:option value="" selected="true">
									<spring:message code="" text="Select" />
								</form:option>
								<c:forEach items="${fundList}" var="fundData">
									<form:option code="${fundData.fundCode}"
										value="${fundData.fundId}">${fundData.fundCompositecode} ${fundData.fundDesc}</form:option>
								</c:forEach>
							</form:select>
						    </div>
						     -->
					       </c:if>
					       
					       <label class="control-label col-sm-2"><spring:message
										code="account.budget.code.master.fieldcode" /></label>
								<div class="col-sm-4">
									<form:hidden path="fieldId" id="fieldId" />
									<form:select id="fieldId" path="fieldId"
										cssClass="form-control mandColorClass"
										onchange="setHiddenField(this);" data-rule-required="true"
										disabled="true">
										<form:option value="">
											<spring:message
												code="account.budget.code.master.selectfieldcode" />
										</form:option>
										<c:forEach items="${listOfTbAcFieldMasterItems}"
											varStatus="status" var="fieldItem">
											<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							
					</div>
                  	<div class="form-group">
                  	           <!-- 
                  	           <label class="control-label col-sm-2"><spring:message
										code="account.budget.code.master.fieldcode" /></label>
								<div class="col-sm-4">
									<form:hidden path="fieldId" id="fieldId" />
									<form:select id="fieldId" path="fieldId"
										cssClass="form-control mandColorClass"
										onchange="setHiddenField(this);" data-rule-required="true"
										disabled="true">
										<form:option value="">
											<spring:message
												code="account.budget.code.master.selectfieldcode" />
										</form:option>
										<c:forEach items="${listOfTbAcFieldMasterItems}"
											varStatus="status" var="fieldItem">
											<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
								 -->
                  	</div>

						<form:hidden path="bugestId" />
					</fieldset> <input type="hidden" value="${viewMode}" id="test" />

					<div id="prProjectionid" class="hide">

						<form:hidden path="bugprojRevBeanList[${count}].prProjectionid"
							value="${bugprojRevBeanList[count].prProjectionid}"
							id="prProjectionid" />

						<form:hidden path="bugprojRevBeanList[${count}].prProjectionidRev"
							value="" code="bugprojRevBeanList[${count}].prProjectionidRev"
							id="prProjectionidRev" />


						<form:hidden path="bugestId" />





						<div class="table-responsive">
							<table class="table  table-bordered table-striped " id="revTable">

								<c:if test="${MODE_DATA == 'VIEW'}">

									<thead>
										<tr>
											<th colspan="1" class="text-center width-200" scope="col"><spring:message
													code="bill.budget.heads" text="Budget Heads" /></th>
											<th colspan="3" class="text-center" scope="col"><spring:message
															code="account.budget.estimate.actual"
															text="Actual of previous Years Rs." /></th>			
											<th colspan="4" id="currentYear" class="text-center"
												scope="col"><spring:message
													code="account.budget.estimate.current.budget"
													text="Current Year Budget" /></th>
											<th colspan="3" scope="col" class="text-center"><spring:message
													code="account.budget.estimate.next.year"
													text="Budget Estimates For The Next Year" /></th>
											<th colspan="1" class="text-center" scope="col"></th>
										</tr>
										<tr>
	
											<th class="text-center" scope="col"></th>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<c:forEach items="${pacItem.atualOfLastFaYearsList}"
													var="atualOfLastFaYearsList" varStatus="status">
													<th class="text-center" scope="col">L.F.Y ${atualOfLastFaYearsList}</th>
												</c:forEach>
											</c:forEach>			
											<th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.budgetprovision"
														text="Original Budget" /></th>
														
											<th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.budgetactuals"
														text="Actuals of 01.04 to 31.12" /></th>
											<th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.budgetexpected"
														text="Expected for 01.01 to 31.03" /></th>			
														
											<th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.revisebudget"
														text="Revise Budget" /></th>
											<th class="text-center" scope="col">
												<spring:message code="account.budget.ulb.estimate"
														text="ULB Estimates for Next Year" /></th>
											<th class="text-center" scope="col"><spring:message
														code="account.budget.approval"
														text="Approval Budget (Standing Committee)" /></th>
														
											<th class="text-center" scope="col"><spring:message 
													    code="account.budget.finalize"
														text="Finalized Budget (General Body)" /></th>
											<th class="text-center width-150" scope="col"><spring:message
													code="account.budget.code.master.remark" 
													text="Remark" /></th>	
														
										</tr>
									</thead>
									<tbody>
										<tr>
	
											<td><form:hidden
													path="bugprojRevBeanList[${count}].prRevBudgetCode"></form:hidden>
												<form:input cssClass="form-control width-200"
													id="prRevBudgetCode${count}"
													path="bugprojRevBeanList[${count}].prRevBudgetCodeDup"
													readonly="true"></form:input></td>
											 <td><form:input
													cssClass="  form-control text-right text-right"
													id="lastYearCountDupOne${count}"
													path="bugprojRevBeanList[${count}].lastYearCountDupOne"
													value="${pacItem.lastYearCountDup}" readonly="true"></form:input></td>		
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="lastYearCountDupTwo${count}"
													path="bugprojRevBeanList[${count}].lastYearCountDupTwo"
													value="${pacItem.lastYearCountDup}" readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="lastYearCountDupThree${count}"
													path="bugprojRevBeanList[${count}].lastYearCountDupThree"
													value="${pacItem.lastYearCountDup}" readonly="true"></form:input></td>	
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="currentYearBugAmt${count}"
													path="bugprojRevBeanList[${count}].orginalEstamt"
													readonly="true"></form:input></td>
													
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="actualsCurrentYear${count}"
													path="bugprojRevBeanList[${count}].actualsCurrentYear"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="expectedCurrentYear${count}"
													path="bugprojRevBeanList[${count}].expectedCurrentYear"
													value="" readonly="true"></form:input></td>
													
													
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="revisedEstamt${count}"
													path="bugprojRevBeanList[${count}].revisedEstamt"
													value="" readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="estimateForNextyearEdit${count}"
													path="bugprojRevBeanList[${count}].estimateForNextyear"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAll(this);"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right width-120"
													id="apprBugStandComEdit${count}"
													path="bugprojRevBeanList[${count}].apprBugStandCom"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right width-120"
													id="finalizedBugGenBodyEdit${count}"
													path="bugprojRevBeanList[${count}].finalizedBugGenBody"
													readonly="true"></form:input></td>
													
											<td><form:textarea
													cssClass="  form-control mandColorClass width-150"
													id="remark${count}"
													path="bugprojRevBeanList[${count}].remark"
													readonly="true"></form:textarea></td>
										</tr>
									</tbody>

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


						<form:hidden path="bugestId" />


						<div class="table-responsive">
							<table class="table  table-bordered table-striped " id="expTable">


								<c:if test="${MODE_DATA == 'VIEW'}">

									<thead>
										<tr>
											<th class="text-center width-200" scope="col"><spring:message
													code="bill.budget.heads" text="Budget Heads" /></th>
											 <th colspan="3" class="text-center" scope="col"><spring:message
															code="account.budget.estimate.actual"
															text="Actual of previous Years Rs." /></th>			
											<th colspan="4" id="currentYear" class="text-center"
												scope="col"><spring:message
													code="account.budget.estimate.current.budget"
													text="Current Year Budget" /></th>
											<th colspan="3" scope="col" class="text-center"><spring:message
													code="account.budget.estimate.next.year"
													text="Budget Estimates For The Next Year" /></th>
											<th class="text-center" scope="col"></th>
										</tr>
										<tr>
	
											<th class="text-center" scope="col"></th>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<c:forEach items="${pacItem.atualOfLastFaYearsList}"
													var="atualOfLastFaYearsList" varStatus="status">
													<th class="text-center" scope="col">L.F.Y ${atualOfLastFaYearsList}</th>
												</c:forEach>
											</c:forEach>	
											<th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.budgetprovision"
														text="Original Budget" /></th>
														
											<th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.budgetactuals"
														text="Actuals of 01.04 to 31.12" /></th>
	                                        <th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.budgetexpected"
														text="Expected for 01.01 to 31.03" /></th>	
																	
											<th class="text-center" scope="col"><spring:message code="budget.projected.revenue.entry.master.revisebudget"
														text="Revise Budget" /></th>
											<th class="text-center" scope="col">
												<spring:message code="account.budget.ulb.estimate"
														text="ULB Estimates for Next Year" /></th>
											<th class="text-center" scope="col"><spring:message
														code="account.budget.approval"
														text="Approval Budget (Standing Committee)" /></th>
											<th class="text-center" scope="col"><spring:message 
													    code="account.budget.finalize"
														text="Finalized Budget (General Body)" /></th>
											<th class="text-center width-150" scope="col"><spring:message
														code="account.budget.code.master.remark" 
														text="Remark" /></th>			
										</tr>
									</thead>
									<tbody>
										<tr>
	
											<td><form:hidden
													path="bugprojExpBeanList[${count}].prExpBudgetCode"></form:hidden>
												<form:input cssClass="form-control width-200"
													id="prExpBudgetCode${count}"
													path="bugprojExpBeanList[${count}].prExpBudgetCodeDup"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="lastYearCountExpDupOne${count}"
													path="bugprojExpBeanList[${count}].lastYearCountDupOne"
													value="${pacItem.lastYearCountDup}" readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="lastYearCountExpDupTwo${count}"
													path="bugprojExpBeanList[${count}].lastYearCountDupTwo"
													value="${pacItem.lastYearCountDup}" readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="lastYearCountExpDupThree${count}"
													path="bugprojExpBeanList[${count}].lastYearCountDupThree"
													value="${pacItem.lastYearCountDup}" readonly="true"></form:input></td>		
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="currentYearBugAmtExp${count}"
													path="bugprojExpBeanList[${count}].orginalEstamt"
													readonly="true"></form:input></td>
													
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="actualsCurrentYearO${count}"
													path="bugprojExpBeanList[${count}].actualsCurrentYearO"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="expectedCurrentYearO${count}"
													path="bugprojExpBeanList[${count}].expectedCurrentYearO"
													readonly="true"></form:input></td>			
													
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="revisedEstamt${count}"
													path="bugprojExpBeanList[${count}].revisedEstamt"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'revisedEstamt')"
													value="" readonly="true"></form:input></td>	
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="estimateForNextyearEditExp${count}"
													path="bugprojExpBeanList[${count}].estimateForNextyear"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAll(this);"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right width-120"
													id="apprBugStandComEditExp${count}"
													path="bugprojExpBeanList[${count}].apprBugStandCom"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right width-120"
													id="finalizedBugGenBodyEditExp${count}"
													path="bugprojExpBeanList[${count}].finalizedBugGenBody"
													readonly="true"></form:input></td>
													
											<td><form:textarea
													cssClass="  form-control mandColorClass width-150"
													id="remark${count}"
													path="bugprojExpBeanList[${count}].remark"
													readonly="true"></form:textarea></td>	
										</tr>
									</tbody>

								</c:if>

							</table>
						</div>
					</div>

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${viewMode ne 'VIEW' }">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save"> </input>
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.btn.reset" text="Reset" />
					</button>
				</c:if>
				<spring:url var="cancelButtonURL"
					value="AccountBudgetEstimationPreparation.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="" /></a>
			</div>

		</form:form>

	</div>

</div>
<script>
$(document).ready(function() {
	
	
	if($('#hiddenFinYear').val()) 
	$('#faYearid').val($('#hiddenFinYear').val())
	
	var id=$('#index').val();	
	var estimateForNextyear = $("#estimateForNextyear"+id).val("");
	var apprBugStandCom = $("#apprBugStandCom"+id).val("");
	var	finalizedBugGenBody = $("#finalizedBugGenBody"+id).val("");
	
	var estimateForNextyearExp = $("#estimateForNextyearExp"+id).val("");
	var apprBugStandComExp = $("#apprBugStandComExp"+id).val("");
	var	finalizedBugGenBodyExp = $("#finalizedBugGenBodyExp"+id).val("");
	
	});
	
</script>

<c:if test="${tbAcBudgetEstimationPreparation.hasError =='true'}">

</c:if>

