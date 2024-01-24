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
		//alert("form Jsp $('#keyTest').val() :  *"+$('#keyTest').val()+"*");
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
	table#revTable tbody tr td .chosen-container ul.chosen-results li,
	table#expTable tbody tr td .chosen-container ul.chosen-results li {
	    white-space: normal;
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
	table#sumrytbl thead th,
	table#sumrytbl tbody td {
		padding: 0.5rem;
	}
	table#sumrytbl thead th,
	table#sumrytbl tbody th {
		font-size: 0.9em !important;
		background: none !important;
		color: #4b4b4b;
		text-align: left;
	}
	table#sumrytbl tbody td {
		font-size: 0.75em !important;
		color: #555;
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
		<apptags:helpDoc url="AccountBudgetEstimationPreparation.html"
			helpDocRefURL="AccountBudgetEstimationPreparation.html"></apptags:helpDoc>
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetEstimationPreparation" method="POST"
			action="AccountBudgetEstimationPreparation.html">
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
            <form:hidden path="" value="${BudgetRevPercent}"  id="BudgetRevPercent"/>
            <form:hidden path="" value="${BudgetEXPPercent}" id="BudgetExpPercent"/>
                <form:hidden path="" value="${MODE_DATA}" id="MODE_DATA"/>
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
									code="budget.estimationpreparation.master.current.financialyear"
									text="Current Financial Year" /></label>
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

							<c:if test="${MODE_DATA == 'EDIT'}">
								<label class="col-sm-2 control-label"><spring:message
										code="budget.estimationpreparation.master.next.financialyear" text="Estimate Financial Year(Next Financial Year)" /></label>
								<div class="col-sm-4">
								<form:input type="text" path="nextFinancialYearDesc"
									class="form-control" id="nextFinancialYearDesc" readonly="true"/>
									<%-- <form:select id="nextFaYearid" path="nextFaYearid"
										cssClass="form-control mandColorClass"
										onchange="setHiddenField(this);" data-rule-required="true"
										disabled="true">
										<c:forEach items="${financeMap}" varStatus="status"
											var="financeMap">
											<form:option value="${financeMap.key}"
												code="${financeMap.key}">${financeMap.value}</form:option>
										</c:forEach>
									</form:select> --%>
								</div>
							</c:if>
						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.estimationpreparation.master.departmenttype"
									text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select path="dpDeptid"
										class="form-control mandColorClass chosen-select-no-results"
										id="dpDeptid" disabled="${viewMode}"
										onchange="loadBudgetEstimationPreparationData(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message
												code="budget.estimationpreparation.master.departmentttype"
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
										onchange="loadBudgetEstimationPreparationData(this)">
										<form:option value="">
											<spring:message
												code="budget.estimationpreparation.master.departmentttype"
												text="" />
										</form:option>
										<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
											<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

                             <c:if test="${budgetTypeStatus == 'Y'}">

								<label class="col-sm-2 control-label required-control"><spring:message
										code="budget.estimationpreparation.master.budgettype" text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="REX" />
										<form:select path="cpdBugtypeId"
											class="form-control mandColorClass" id="cpdBugtypeId"
											onchange="clearAllData(this)" disabled="${viewMode}"
											data-rule-required="true">
											<form:option value="">
												<spring:message
													code="budget.estimationpreparation.master.selectbudgettype"
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
											onchange="clearAllData(this)"
											disabled="${viewMode ne 'true'}">
											<form:option value="">
												<spring:message
													code="budget.estimationpreparation.master.selectbudgettype"
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
							
							<c:if test="${MODE_DATA == 'create'}">
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.budget.code.master.fieldcode" /></label>
								<div class="col-sm-4">
									<form:select id="fieldId" path="fieldId"
										cssClass="form-control chosen-select-no-results"
										readonly="true" data-rule-required="true">
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
							</c:if>
							<c:if test="${MODE_DATA == 'EDIT'}">
								<label class="control-label col-sm-2 required-control"><spring:message
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
							</c:if>
							
						<c:if test="${budgetSubTypeStatus == 'Y'}">

								<label class="col-sm-2 control-label required-control"><spring:message
										code="budget.estimationpreparation.master.budgetsubtype"
										text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select path="cpdBugsubtypeId"
											class="form-control mandColorClass" id="cpdBugsubtypeId"
											disabled="${viewMode}"
											onchange="loadBudgetEstimationPreparationData(this)"
											data-rule-required="true">
											<form:option value="">
												<spring:message
													code="budget.estimationpreparation.master.selectbudgetsubtype"
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
											disabled="${viewMode ne 'true'}"
											onchange="loadBudgetEstimationPreparationData(this)">
											<form:option value="">
												<spring:message
													code="budget.estimationpreparation.master.selectbudgetsubtype"
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
					 <div class="form-group">	
					<c:if test="${isApplicable}"> 
					<c:if test="${MODE_DATA == 'create'}">
					 <!--	
					<label for="remark"
						class="col-sm-2 control-label"><spring:message
							code="account.budget.code.master.remark" text ="Remark"/></label>
							<div class="col-sm-4">
					<form:input
							cssClass="  form-control text-left text-left"
							id="remark"
							path="remark"
							readonly="false"></form:input>	
					 -->		
					 <!-- 
					 <label for="fundId"
						class="col-sm-2 control-label"><spring:message
							code="account.budget.code.master.fundcode" text ="Fund Name"/></label>
						<div class="col-sm-4">
							<form:select path="fundId"
								class="form-control mandColorClass chosen-select-no-results"
								name="fundId" id="fundId"  
								disabled="">
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
					
					<c:if test="${MODE_DATA == 'EDIT'}">
					<!-- 
					<label for="remark"
						class="col-sm-2 control-label"><spring:message
							code="account.budget.code.master.remark" text ="Remark"/></label>
							<div class="col-sm-4">
					<form:input
							cssClass="  form-control text-left text-left"
							id="remark"
							path="remark"
							readonly="true"></form:input>	
					 -->		
					<!-- 
					 <label for="fundId"
						class="col-sm-2 control-label"><spring:message
							code="account.budget.code.master.fundcode" text ="Fund Name"/></label>
						<div class="col-sm-4">
						<form:hidden path="fundId" id="fundId" />
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
					</c:if>
				</div>
					<!-- <div class="form-group"> -->
					
					
					
				<%-- 	<c:if test="${MODE_DATA == 'create'}">
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.budget.code.master.fieldcode" /></label>
								<div class="col-sm-4">
									<form:select id="fieldId" path="fieldId"
										cssClass="form-control chosen-select-no-results"
										readonly="true" data-rule-required="true">
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
							</c:if>
							<c:if test="${MODE_DATA == 'EDIT'}">
								<label class="control-label col-sm-2 required-control"><spring:message
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
							</c:if> --%>
					<!-- </div> -->	

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


						<div class="table-responsive overflow-y-hidden">
							<table class="table  table-bordered table-striped " id="revTable">
								
								<c:if test="${MODE_DATA == 'create'}">
								    <thead>
										<tr>
											<th colspan="1" class="text-center width-200" scope="col"><spring:message
													code="bill.budget.heads" text="Budget Heads" /><span
												class="mand">*</span></th>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<c:forEach items="${pacItem.atualOfLastFaYearsList}"
													begin="0" end="0" var="atualOfLastFaYearsList"
													varStatus="status">
													<th colspan="3" class="text-center" scope="col"><spring:message
															code="account.budget.estimate.actual"
															text="Actual of previous Years Rs." /></th>
												</c:forEach>
											</c:forEach>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<th colspan="4" id="currentYear" class="text-center"
													scope="col"><spring:message
														code="account.budget.estimate.current.budget"
														text="Current Year Budget" /> &nbsp; ${pacItem.curFinYear}</th>
											</c:forEach>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<th colspan="1" scope="col" class="text-center"><spring:message
														code="account.budget.estimate.next.year"
														text="Budget Estimates For" /><br>
														${pacItem.curNextFinYear} </th>
											</c:forEach>
											<th colspan="1" class="text-center" scope="col"></th>
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
														text="ULB Estimates for Next Year" /><span class="mand">*</span></th>
											<!-- 			
											 <th class="text-center" scope="col"><span class="small"><br></span><span
												class="small"><spring:message
														code="account.budget.approval" text="SC" /></span></th>
											<th class="text-center" scope="col"><span class="small"><span
													class="small1"></span><br>
												<spring:message code="account.budget.finalize" text="GB" /></span></th> 
												 -->
											<th class="text-center width-150" scope="col"><spring:message
														code="account.budget.code.master.remark" text="Remark" /></th>	 
											<th class="text-center" scope="col" width="8%"><spring:message
														code="bill.action" text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<tr id="bugestIdRev0" class="appendableClass">
	
											<td><form:select
													path="bugprojRevBeanList[${count}].prRevBudgetCode"
													cssClass="form-control mandColorClass chosen-select-no-results width-200"
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
													id="orginalEstamt${count}"
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
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAll(this);"
													onchange="getAmountFormatInDynamic((this),'expectedCurrentYear')"
													value="" readonly="false"></form:input></td>
													
													
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="revisedEstamt${count}"
													path="bugprojRevBeanList[${count}].revisedEstamt"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'revisedEstamt')"
													value="" readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control mandColorClass text-right text-right"
													id="estimateForNextyear${count}"
													path="bugprojRevBeanList[${count}].estimateForNextyear"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAll(this);"
													onchange="getAmountFormatInDynamic((this),'estimateForNextyear')"></form:input></td>
											
											<form:hidden
													cssClass="  form-control text-right text-right"
													id="apprBugStandCom${count}"
													path="bugprojRevBeanList[${count}].apprBugStandCom"
													readonly="true"></form:hidden>
											<form:hidden
													cssClass="  form-control text-right text-right"
													id="finalizedBugGenBody${count}"
													path="bugprojRevBeanList[${count}].finalizedBugGenBody"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'finalizedBugGenBody')"
													readonly="true"></form:hidden>
											<td><form:textarea
													cssClass="  form-control mandColorClass width-150"
													id="remark${count}"
													path="bugprojRevBeanList[${count}].remark"
													readonly="false"></form:textarea></td>
											
											<td class="text-center" width="8%"><c:if test="${!viewMode}">
													<button title="Add" class="btn btn-success btn-sm addButton"
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
									</tbody>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<thead>
									    <tr>
											<th colspan="1" class="text-center width-200" scope="col"><spring:message
													code="bill.budget.heads" text="Budget Heads" /><span
												class="mand">*</span></th>
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
														text=" ULB Estimates for Next Year" /><span class="mand">*</span></th>
											 <th class="text-center" scope="col"><spring:message
														code="account.budget.approval" text="SC" /></th>
											<th class="text-center" scope="col">
												<spring:message code="account.budget.finalize" text="GC" /></th>
												
											<th class="text-center width-150" scope="col"><spring:message
														code="account.budget.code.master.remark" text="Remark" /></th> 	
										</tr>
									</thead>
									<tbody>
									    <c:set var="count" value="0" scope="page" />
									    <c:forEach items="${tbAcBudgetEstimationPreparation.bugprojRevBeanList}" varStatus="status" var="pacItemTmp">
										
										<tr>
											<td><form:hidden
													path="bugprojRevBeanList[${count}].budgetId"></form:hidden>
												<form:hidden
													path="bugprojRevBeanList[${count}].prRevBudgetCode"
													id="prRevBudgetCode${count}" /> <form:select
													id="prRevBudgetCode${count}"
													path="bugprojRevBeanList[${count}].prRevBudgetCode"
													cssClass="form-control mandColorClass width-200"
													onchange="setHiddenField(this);" data-rule-required="true"
													disabled="true">
													<form:option value="">
														<spring:message code="" text="Select Budget Heads" />
													</form:option>
													<c:forEach items="${accountBudgetCodeAllocationMap}"
														varStatus="status" var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
													</c:forEach>
												</form:select>
												</td>
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
													id="orginalEstamt${count}"
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
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAll(this);"
													onchange="getAmountFormatInDynamic((this),'expectedCurrentYear')"
													value="" readonly="false"></form:input></td>
													
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="revisedEstamt${count}"
													path="bugprojRevBeanList[${count}].revisedEstamt"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'revisedEstamt')"
													value="" readonly="false"></form:input></td>		
											<td><form:input
													cssClass="  form-control mandColorClass text-right text-right"
													id="estimateForNextyearEdit${count}"
													path="bugprojRevBeanList[${count}].estimateForNextyear"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAll(this);"
													onchange="getAmountFormatInStatic('estimateForNextyearEdit0')"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right width-120"
													id="apprBugStandComEdit${count}"
													path="bugprojRevBeanList[${count}].apprBugStandCom"
													readonly="false"></form:input></td>
											<td><form:input
													cssClass="  form-control text-right text-right width-120"
													id="finalizedBugGenBodyEdit${count}"
													path="bugprojRevBeanList[${count}].finalizedBugGenBody"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'finalizedBugGenBodyEdit')"
													readonly="false"></form:input></td>
											<td><form:textarea
													cssClass="  form-control mandColorClass width-150"
													id="remark${count}"
													path="bugprojRevBeanList[${count}].remark"
													readonly="true"></form:textarea></td>
										</tr>
										<c:set var="count" value="${count + 1}" scope="page"/>
										</c:forEach>
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


						<div class="table-responsive overflow-y-hidden">
							<table class="table  table-bordered table-striped " id="expTable">

								<c:if test="${MODE_DATA == 'create'}">
									<thead>
									    <tr>
											<th colspan="1" class="text-center width-200" scope="col"><spring:message
													code="bill.budget.heads" text="Budget Heads" /><span
												class="mand">*</span></th>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<c:forEach items="${pacItem.atualOfLastFaYearsList}"
													begin="0" end="0" var="atualOfLastFaYearsList"
													varStatus="status">
													<th colspan="3" class="text-center" scope="col"><spring:message
															code="account.budget.estimate.actual"
															text="Actual of previous Years Rs." /></th>
												</c:forEach>
											</c:forEach>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<th colspan="4" id="currentYear" class="text-center"
													scope="col"><spring:message
														code="account.budget.estimate.current.budget"
														text="Current Year Budget" /> &nbsp; ${pacItem.curFinYear}</th>
											</c:forEach>
											<c:forEach items="${bugpacsacHeadList}" begin="0" end="0"
												var="pacItem" varStatus="status">
												<th colspan="1" scope="col" class="text-center"><spring:message
														code="account.budget.estimate.next.year"
														text="Budget Estimates For" /><br>
														${pacItem.curNextFinYear} </th>
											</c:forEach>
											<th colspan="1" class="text-center" scope="col"></th>
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
														text="ULB Estimates for Next Year" /><span class="mand">*</span></th>
											<!-- 		
											<th class="text-center" scope="col"><span class="small"><br></span><span
												class="small"><spring:message
														code="account.budget.approval"
														text="Approval Budget (Standing Committee)" /></span></th>
											<th class="text-center" scope="col"><span class="small"><span
													class="small1"></span><br>
												<spring:message code="account.budget.finalize"
														text="Finalized Budget (General Body)" /></span></th> 	
											 -->	
											<th class="text-center width-150" scope="col"><spring:message
														code="account.budget.code.master.remark" text="Remark" /></th>			
											<th class="text-center" scope="col" width="8%"><spring:message
														code="bill.action" text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<tr id="bugestIdExp0" class="ExpappendableClass">
	
											<td><form:select
													path="bugprojExpBeanList[${count}].prExpBudgetCode"
													cssClass="form-control mandColorClass chosen-select-no-results width-200"
													id="prExpBudgetCode${count}"
													onchange="getOrgBalAmountExp(${count})">
													<form:option value="">
														<spring:message code="" text="Select Budget Heads" />
													</form:option>
													<c:forEach items="${accountBudgetCodeAllocationExpMap}"
														varStatus="status" var="sacItem">
														<form:option value="${sacItem.key}" code="${sacItem.key}">${sacItem.value} </form:option>
													</c:forEach>
												</form:select></td>
	
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
													id="orginalEstamtExp${count}"
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
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAllExp(this);"
													onchange="getAmountFormatInDynamic((this),'expectedCurrentYearO')"
													value="" readonly="false"></form:input></td>		
													
													
											<td><form:input
													cssClass="  form-control text-right text-right"
													id="revisedEstamtExp${count}"
													path="bugprojExpBeanList[${count}].revisedEstamt"
													readonly="true"></form:input></td>
											<td><form:input
													cssClass="  form-control mandColorClass text-right text-right"
													id="estimateForNextyearExp${count}"
													path="bugprojExpBeanList[${count}].estimateForNextyear"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onkeyup="enteredAmountDynamicallyGeneratedAllExp(this);"
													onchange="getAmountFormatInDynamic((this),'estimateForNextyearExp')"></form:input></td>
											 	
										   <form:hidden
													cssClass="  form-control text-right text-right"
													id="apprBugStandComExp${count}"
													path="bugprojExpBeanList[${count}].apprBugStandCom"
													readonly="true"></form:hidden>
											<form:hidden
													cssClass="  form-control text-right text-right"
													id="finalizedBugGenBodyExp${count}"
													path="bugprojExpBeanList[${count}].finalizedBugGenBody"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'finalizedBugGenBodyExp')"
													readonly="true"></form:hidden>
											 <td><form:textarea
													cssClass="  form-control mandColorClass width-150"
													id="remark${count}"
													path="bugprojExpBeanList[${count}].remark"
													readonly="false"></form:textarea></td>
														
											<td class="text-center" width="8%"><c:if test="${!viewMode}">
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
									</tbody>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<thead>
									    <tr>
											<th colspan="1" class="text-center width-200" scope="col"><spring:message
													code="bill.budget.heads" text="Budget Heads" /><span
												class="mand">*</span></th>
											<th colspan="3" class="text-center" scope="col"><spring:message
															code="account.budget.estimate.actual"
															text="Actual of previous Years Rs." /></th>	
											<th colspan="4" id="currentYear" class="text-center"
												scope="col"><spring:message
													code="account.budget.estimate.current.budget"
													text="Current Year Budget" /></th>
											<th colspan="3" scope="col" class="text-center">Budget
												Estimates For The Next Year</th>
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
														text="ULB Estimates for Next Year" /><span class="mand">*</span></th>
											 <th class="text-center" scope="col"><spring:message
														code="account.budget.approval"
														text="Approval Budget (Standing Committee)" /></th>
											<th class="text-center" scope="col">
												<spring:message code="account.budget.finalize"
														text="Finalized Budget (General Body)" /></th>	
											<th class="text-center width-150" scope="col"><spring:message
														code="account.budget.code.master.remark" text="Remark" /></th>			
										</tr>
									</thead>
									<tbody>
									    <c:set var="count" value="0" scope="page" />
									    <c:forEach items="${tbAcBudgetEstimationPreparation.bugprojExpBeanList}" varStatus="status" var="pacItemTmp">
										
											<tr>
												<td><form:hidden
													path="bugprojExpBeanList[${count}].budgetId"></form:hidden>
												    <form:select
														path="bugprojExpBeanList[${count}].prExpBudgetCode"
														cssClass="form-control mandColorClass chosen-select-no-results width-200"
														id="prExpBudgetCode${count}"
														onchange="getOrgBalAmountExp(${count})" disabled="true">
														<form:option value="">
															<spring:message code="" text="Select Budget Heads" />
														</form:option>
														<c:forEach items="${accountBudgetCodeAllocationExpMap}"
															varStatus="status" var="sacItem">
															<form:option value="${sacItem.key}" code="${sacItem.key}">${sacItem.value} </form:option>
														</c:forEach>
													</form:select>
												   	<form:hidden path="bugprojExpBeanList[${count}].prExpBudgetCode"  id="prExpBudgetCode${count}" />
												 </td>
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
														id="orginalEstamtExp${count}"
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
														onkeypress="return hasAmount(event, this, 13, 2)"
														onkeyup="enteredAmountDynamicallyGeneratedAllExp(this);"
														onchange="getAmountFormatInDynamic((this),'expectedCurrentYearO')"
														readonly="false"></form:input></td>		
														
												<td><form:input
														cssClass="  form-control text-right text-right"
														id="revisedEstamtExp${count}"
														path="bugprojExpBeanList[${count}].revisedEstamt"
														onkeypress="return hasAmount(event, this, 13, 2)"
														onchange="getAmountFormatInDynamic((this),'revisedEstamtExp')"
														readonly="false"></form:input></td>			
												<td><form:input
														cssClass="  form-control mandColorClass text-right text-right"
														id="estimateForNextyearEditExp${count}"
														path="bugprojExpBeanList[${count}].estimateForNextyear"
														onkeypress="return hasAmount(event, this, 13, 2)"
														onkeyup="enteredAmountDynamicallyGeneratedAllExp(this);"
														onchange="getAmountFormatInStatic('estimateForNextyearEditExp0')"></form:input></td>
												<td><form:input
														cssClass="  form-control text-right text-right width-120"
														id="apprBugStandComEditExp${count}"
														path="bugprojExpBeanList[${count}].apprBugStandCom"
														readonly="false"></form:input></td>
												<td><form:input
														cssClass="  form-control text-right text-right width-120"
														id="finalizedBugGenBodyEditExp${count}"
														path="bugprojExpBeanList[${count}].finalizedBugGenBody"
														onkeypress="return hasAmount(event, this, 13, 2)"
														onchange="getAmountFormatInDynamic((this),'finalizedBugGenBodyEditExp')"
														readonly="false"></form:input></td>
												<td><form:textarea
														cssClass="  form-control mandColorClass width-150"
														id="remark${count}"
														path="bugprojExpBeanList[${count}].remark"
														readonly="true"></form:textarea></td>
											</tr>
										    <c:set var="count" value="${count + 1}" scope="page"/>
										</c:forEach>
									</tbody>
								</c:if>

							</table>
						</div>
					</div>

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />
			<br/>
			<h4>Summary of Budget :</h4>
			<table id="sumrytbl">
				<thead>
				    <tr>
						<td></td>
						<th><spring:message code="" text="Receipts" /></th>
						<th><spring:message code="" text="Payments" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th><spring:message code="" text="Municipal Commissioner" /></th>
						<td>${tbAcBudgetEstimationPreparation.estimateForNextyearRevAvg}</td>
						<td>${tbAcBudgetEstimationPreparation.estimateForNextyearExpAvg}</td>
					</tr>
					<tr>
						<th><spring:message code="" text="Standing Committee" /></th>
						<td>${tbAcBudgetEstimationPreparation.apprBugStandComRevAvg}</td>
						<td>${tbAcBudgetEstimationPreparation.apprBugStandComExpAvg}</td>
					</tr>
					<tr>
						<th><spring:message code="" text="General Body" /></th>
						<td>${tbAcBudgetEstimationPreparation.finalizedBugGenBodRevAvg}
						<td>${tbAcBudgetEstimationPreparation.finalizedBugGenBodExpAvg}</td>
					</tr>
				</tbody>
			</table>

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
					<input type="button" id="saveBtn"
						class="btn btn-success btn-submit" onclick="saveLeveledData(this)"
						value="Save"> </input>
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.btn.reset" text="Reset" />
					</button>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn"
						class="btn btn-success btn-submit" onclick="saveLeveledDataBulkEdit(this)"
						value="Save"> </input>
				</c:if>
				<spring:url var="cancelButtonURL"
					value="AccountBudgetEstimationPreparation.html" />
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
	var estimateForNextyear = $("#estimateForNextyear"+id).val("");
	var apprBugStandCom = $("#apprBugStandCom"+id).val("");
	var	finalizedBugGenBody = $("#finalizedBugGenBody"+id).val("");
	var estimateForNextyearExp = $("#estimateForNextyearExp"+id).val("");
	var apprBugStandComExp = $("#apprBugStandComExp"+id).val("");
	var	finalizedBugGenBodyExp = $("#finalizedBugGenBodyExp"+id).val("");
	
	//var revisedEstamt = $("#revisedEstamt"+id).val("");
	//var expectedCurrentYearO = $("#expectedCurrentYearO"+id).val("");
	});
	
</script>

<c:if test="${tbAcBudgetEstimationPreparation.hasError =='true'}">

</c:if>

