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

							

                             <c:if test="${budgetTypeStatus == 'Y'}">

								<label class="col-sm-2 control-label required-control"><spring:message
										code="budget.estimationpreparation.master.budgettype" text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="REX" />
										<form:select path="cpdBugtypeId"
											class="form-control mandColorClass" id="cpdBugtypeId"
											disabled="${viewMode}"
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
						
							
						<c:if test="${budgetSubTypeStatus == 'Y'}">

								<label class="col-sm-2 control-label required-control"><spring:message
										code="budget.estimationpreparation.master.budgetsubtype"
										text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select path="cpdBugsubtypeId"
											class="form-control mandColorClass" id="cpdBugsubtypeId"
											disabled="${viewMode}"
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

								
							</c:if>	
							
						</div>
					    <div class="form-group">	
						 	<c:if test="${isApplicable}"> 
								<c:if test="${MODE_DATA == 'create'}">
								
								</c:if> 
								
								<c:if test="${MODE_DATA == 'EDIT'}">
								
								</c:if>
							</c:if>
				         </div>
					
						<form:hidden path="bugestId" />
					</fieldset> 
					
					<input type="hidden" value="${viewMode}" id="test" />

					<div id="prProjectionid" class="hide">

						<form:hidden path="bugprojRevBeanList[${count}].prProjectionid"
							value="${bugprojRevBeanList[count].prProjectionid}"
							id="prProjectionid" />

						<form:hidden path="bugprojRevBeanList[${count}].prProjectionidRev"
							value="" code="bugprojRevBeanList[${count}].prProjectionidRev"
							id="prProjectionidRev" />

						<form:hidden path="bugestId" />
						<div class="table-overflow-sm">
						
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


						<div class="table-overflow-sm">
					    
					    </div>
					</div>

				</li>
			</ul>

			<input type="hidden" id="count" value="0" />
				
			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
				    <button type="button" class="btn btn-success searchData"
						onclick="searchBudgetEstimationPreparationDataBulkEditSearch()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="Reset" class="btn btn-warning bulkEditSearch">
						<spring:message code="account.btn.reset" text="Reset" />
					</button>
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

