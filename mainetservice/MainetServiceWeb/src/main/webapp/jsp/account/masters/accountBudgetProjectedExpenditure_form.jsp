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
<script src="js/account/accountBudgetProjectedExpenditure.js" />
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>





<c:if test="${tbAcBudgetProjectedExpenditure.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>


<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>
<style>
select#prBudgetCodeid0[readonly] {
  background: #eee;
  pointer-events: none;
  touch-action: none;
}
</style>



<div class="widget" id="widget">
	<div class="widget-header">

		<h2>
			<spring:message code="budget.projected.expenditure.master.title"
				text="" />
		</h2>
	<apptags:helpDoc url="AccountBudgetProjectedExpenditure.html" helpDocRefURL="AccountBudgetProjectedExpenditure.html"></apptags:helpDoc>
	</div>

	<div class="widget-content padding">

		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcBudgetProjectedExpenditure" name="frmMaster"
			method="POST" action="AccountBudgetProjectedExpenditure.html">
			<form:hidden path="secondaryId" id="secondaryId" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" value="${MODE_DATA}" id="formMode_Id" />
			<form:hidden path="successFlag" id="successFlag" />
			<form:hidden path="sacHeadStatus" id="sacHeadStatus"/>
		
			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
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
							<label class="control-label col-sm-2 required-control"><spring:message
									code="budget.projected.expenditure.master.budgetyear" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select id="faYearid" path="faYearid"
										cssClass="form-control mandColorClass" disabled="${viewMode}"
										onchange="clearAllData(this)" data-rule-required="true">
										<c:forEach items="${financeMap}" varStatus="status"
											var="financeMap">
											<form:option value="${financeMap.key}"
												code="${financeMap.key}">${financeMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<c:if test="${MODE_DATA == 'EDIT'}">
								<div class="col-sm-4">
									<form:hidden path="faYearid" id="faYearid" />
									<form:select id="faYearid" path="faYearid"
										cssClass="form-control mandColorClass"
										disabled="${viewMode ne 'true'}" onchange="clearAllData(this)">
										<c:forEach items="${financeMap}" varStatus="status"
											var="financeMap">
											<form:option value="${financeMap.key}"
												code="${financeMap.key}">${financeMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<c:if test="${budgetSubTypeStatus == 'Y'}">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="budget.budgetaryrevision.master.budgetsubtype" text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select path="cpdBugsubtypeId"
											class="form-control mandColorClass" id="cpdBugsubtypeId"
											disabled="${viewMode}" onchange="clearAllData(this)"
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
											disabled="${viewMode ne 'true'}"
											onchange="clearAllData(this)">
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

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.projected.expenditure.master.department" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select path="dpDeptid"
										class="form-control mandColorClass chosen-select-no-results"
										id="dpDeptid" disabled="${viewMode}"
										onchange="loadBudgetExpenditureData(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="budget.reappropriation.master.selectdepartmenttype" text="Select Department" />
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
										class="form-control mandColorClass chosen-select-no-results"
										id="dpDeptid" disabled="${viewMode ne 'true'}"
										onchange="loadBudgetExpenditureData(this)">
										<form:option value="">
											<spring:message code="budget.reappropriation.master.selectdepartmenttype" text="Select Department" />
										</form:option>
										<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
											<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>
							<form:hidden path="prExpenditureid" />

							<c:if test="${fieldStatus == 'Y'}">
							
								<label class="control-label required-control col-sm-2"><spring:message
										code="account.budget.code.master.fieldcode" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select id="fieldId" path="fieldId"
											cssClass="form-control chosen-select-no-results"
											disabled="${viewMode}" 
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
								
								<c:if test="${MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
									<form:hidden path="fieldId" />
										<form:select id="fieldId" path="fieldId"
											cssClass="form-control chosen-select-no-results"
											disabled="${viewMode ne 'true'}" 
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
						
						<div class="form-group">

							<form:hidden path="prExpenditureid" />
							
							<c:if test="${functionStatus == 'Y'}">
								<label class="control-label col-sm-2"><spring:message
										code="account.budget.code.master.functioncode" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select id="functionId" path="functionId"
											cssClass="form-control chosen-select-no-results"
											disabled="${viewMode}"   onchange="loadBudgetExpenditureFunctionData(this)"
											data-rule-required="false">
											<form:option value="">
														<spring:message
															code="account.budget.code.master.selectfunctioncode" />
											</form:option>
											<c:forEach items="${listOfTbAcFunctionMasterItems}"
														varStatus="status" var="functionItem">
														<form:option value="${functionItem.key}"
															code="${functionItem.key}">${functionItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								
								<c:if test="${MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
										<form:select id="functionId" path="functionId"
											cssClass="form-control chosen-select-no-results"
											disabled="${viewMode ne 'true'}" onchange="loadBudgetExpenditureFunctionData(this)"
											data-rule-required="false">
											<form:option value="">
														<spring:message
															code="account.budget.code.master.selectfunctioncode" />
											</form:option>
											<c:forEach items="${listOfTbAcFunctionMasterItems}"
														varStatus="status" var="functionItem">
														<form:option value="${functionItem.key}"
															code="${functionItem.key}">${functionItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								
							</c:if>
						</div>
						<form:hidden path="prExpenditureid" />
						<div id="prExpenditureid" class="">
							<div class="table-overflow-sm" id="budExpTableDivID">
								<table class="table  table-bordered table-striped "
									id="budExpTable">

									<c:if test="${MODE_DATA == 'create'}">
										<tr>
											<th scope="col" width="30%" style="text-align: center"><spring:message
													code="budget.projected.expenditure.master.budgethead"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.budgetprocision"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.currentspillOverAmount"
													text="Current Year Spill Over Amount" /></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.nextspillOverAmount"
													text="Next Years Spill Over Amount" /></th>		
											<th class="text-center" scope="col" width="10%"><span
												class="small"><spring:message
														code="account.budgetopenmaster.addremove" text="" /></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<tr>
											<th scope="col" width="40%" style="text-align: center"><spring:message
													code="budget.projected.expenditure.master.budgethead"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.budgetprocision"
													text="" /><span class="mand">*</span></th>
													
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.currentspillOverAmount"
													text="Current Year Spill Over Amount" /></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.nextspillOverAmount"
													text="Next Years Spill Over Amount" /></th>		
											<%-- <th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.revisebudget"
													text="" /><span class="mand"></span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.expenditure.master.expenditureamount"
													text="" /><span class="mand"></span></th> --%>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'create'}">
										<tr id="budExpId" class="appendableClass">

											<td><form:select id="prBudgetCodeid${count}"
													path="bugExpenditureMasterDtoList[${count}].prBudgetCodeid"
													cssClass="form-control mandColorClass chosen-select-no-results"
													disabled="${viewMode}"
													onchange="findduplicatecombinationexit(${count})"
													data-rule-required="true">
													<form:option value="">
														<spring:message
															code="budget.projected.expenditure.master.selectbudgetheads" />
													</form:option>
													<c:forEach items="${accountBudgetCodeMap}"
														varStatus="status" var="bugCodeItem">
														<form:option value="${bugCodeItem.key}"
															code="${bugCodeItem.key}">${bugCodeItem.value}</form:option>
													</c:forEach>
												</form:select></td>

											<td><form:input
													cssClass="form-control mandColorClass text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="orginalEstamt${count}"
													path="bugExpenditureMasterDtoList[${count}].orginalEstamt"
													onkeyup="copyContent(this)"
													onchange="getAmountFormatInDynamic((this),'orginalEstamt')"
													data-rule-required="true"></form:input></td>
													
											<td><form:input
													class="form-control text-right hasNumber" id="curYrSpamt${count}"
													path="bugExpenditureMasterDtoList[${count}].curYrSpamt"></form:input></td>
													
											<td><form:input
													class="form-control text-right hasNumber" id="nxtYrSpamt${count}"
													path="bugExpenditureMasterDtoList[${count}].nxtYrSpamt"></form:input></td>
															
											<td class="text-center">
												<button title="Add" class="btn btn-success btn-sm addButton"
													id="addButton${count}">
													<i class="fa fa-plus-circle"></i>
												</button>
												<button title="Delete"
													class="btn btn-danger btn-sm delButton"
													id="delButton${count}">
													<i class="fa fa-trash-o"></i>
												</button>
											</td>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<td><form:select
													path="bugExpenditureMasterDtoList[0].prBudgetCodeid"
													cssClass="form-control mandColorClass"
													id="prBudgetCodeid0"
													onchange="findduplicatecombinationexit(${0})"
													data-rule-required="true">
													<form:option value="">
														<spring:message
															code="budget.projected.expenditure.master.selectbudgetheads"
															text="" />
													</form:option>
													<c:forEach items="${accountBudgetCodeMap}"
														varStatus="status" var="bugCodeItem">
														<form:option value="${bugCodeItem.key}"
															code="${bugCodeItem.key}">${bugCodeItem.value} </form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input
													cssClass="form-control mandColorClass text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="orginalEstamt0"
													path="bugExpenditureMasterDtoList[0].orginalEstamt"
													onkeyup="copyContent(this)"
													onchange="getAmountFormatInStatic('orginalEstamt0')"
													data-rule-required="true" readonly="false"></form:input></td>
											<td><form:input cssClass="form-control text-right hasNumber"
													path="bugExpenditureMasterDtoList[0].curYrSpamt"></form:input></td>
													
											<td><form:input cssClass="form-control text-right hasNumber"
													path="bugExpenditureMasterDtoList[0].nxtYrSpamt"></form:input></td>		
											<form:input cssClass="form-control text-right" type="hidden"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="revisedEstamt0"
													path="bugExpenditureMasterDtoList[0].revisedEstamt"
													readonly="${viewMode ne 'true'}"></form:input>
											<form:input cssClass="form-control text-right" type="hidden"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="expenditureAmt0"
													path="bugExpenditureMasterDtoList[0].expenditureAmt"
													readonly="${viewMode ne 'true'}"></form:input>
										</tr>
									</c:if>

								</table>
							</div>
						</div>

					</fieldset>
				</li>
			</ul>
			<INPUT type="hidden" id="count" value="0" />
			<INPUT type="hidden" id="countFinalCode" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="<spring:message code="account.bankmaster.save" text="Save" />"> </input>
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="<spring:message code="account.bankmaster.save" text="Save" />"> </input>
				</c:if>
				<spring:url var="cancelButtonURL"
					value="AccountBudgetProjectedExpenditure.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>
<c:if test="${tbAcBudgetProjectedExpenditure.hasError =='true'}">
	</div>
</c:if>

