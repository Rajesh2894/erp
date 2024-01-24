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
<script src="js/account/accountBudgetProjectedRevenueEntry.js" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>
<c:if test="${tbAcBudgetProjectedRevenueEntry.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="budget.projected.revenue.entry.master.title"
				text="" />
		</h2>
	<apptags:helpDoc url="AccountBudgetProjectedRevenueEntry.html" helpDocRefURL="AccountBudgetProjectedRevenueEntry.html"></apptags:helpDoc>	
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcBudgetProjectedRevenueEntry" name="frmMaster"
			method="POST" action="AccountBudgetProjectedRevenueEntry.html">
			<form:hidden path="secondaryId" id="secondaryId" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" value="0" />
			<form:hidden path="" value="${MODE_DATA}" id="formMode_Id" />
			<form:hidden path="successFlag" id="successFlag" />
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
									code="budget.projected.revenue.entry.master.budgetyear" text="" /></label>

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
							<form:hidden path="prProjectionid" />

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.projected.revenue.entry.master.department" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select path="dpDeptid"
										class="form-control mandColorClass chosen-select-no-results"
										id="dpDeptid" disabled="${viewMode}"
										onchange="loadBudgetReappropriationData(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="budget.allocation.master.departmentttype" text="Select Department" />
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
										onchange="loadBudgetReappropriationData(this)">
										<form:option value="">
											<spring:message code="" text="Select Department" />
										</form:option>
										<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
											<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<c:if test="${fieldStatus == 'Y'}">
							
								<label class="control-label col-sm-2 required-control"><spring:message
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

							<form:hidden path="prProjectionid" />
							
							<c:if test="${functionStatus == 'Y'}">
								<label class="control-label col-sm-2"><spring:message
										code="account.budget.code.master.functioncode" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select id="functionId" path="functionId"
											cssClass="form-control chosen-select-no-results"
											disabled="${viewMode}" onchange="loadBudgetReappropriationFunctionData(this)"
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
											disabled="${viewMode ne 'true'}" onchange="loadBudgetReappropriationFunctionData(this)"
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

						<form:hidden path="prProjectionid" />
						<div id="prProjectionid" class="">
							<div class="table-overflow-sm" id="budRevTableDivID">
								<table class="table  table-bordered table-striped "
									id="budRevTable">

									<c:if test="${MODE_DATA == 'create'}">
										<tr>
											<th scope="col" width="55%" style="text-align: center"><spring:message
													code="budget.projected.revenue.entry.master.budgethead"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="35%"><spring:message
													code="budget.projected.revenue.entry.master.budgetprovision"
													text="" /><span class="mand">*</span></th>
											<th class="text-center" scope="col" width="10%"><span
												class="small"><spring:message
														code="account.budgetopenmaster.addremove" text="" /></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<tr>
											<th scope="col" width="40%" style="text-align: center"><spring:message
													code="budget.projected.revenue.entry.master.budgethead"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.revenue.entry.master.budgetprovision"
													text="" /><span class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.revenue.entry.master.revisebudget"
													text="" /><span class="mand"></span></th>
											<th scope="col" width="20%"><spring:message
													code="budget.projected.revenue.entry.master.collectedamount"
													text="" /><span class="mand"></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'create'}">
										<tr id="budRevId" class="appendableClass">
											<td><form:select id="prBudgetCodeid${count}"
													path="bugRevenueMasterDtoList[${count}].prBudgetCodeid"
													cssClass="form-control mandColorClass chosen-select-no-results"
													disabled="${viewMode}"
													onchange="findduplicatecombinationexit(${count})"
													data-rule-required="true">
													<form:option value="">
														<spring:message
															code="budget.projected.revenue.entry.master.selectaccountheads" />
													</form:option>
													<c:forEach items="${accountBudgetCodeMap}"
														varStatus="status" var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
													</c:forEach>
												</form:select></td>

											<td><form:input
													cssClass="form-control mandColorClass text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="orginalEstamt${count}"
													path="bugRevenueMasterDtoList[${count}].orginalEstamt"
													onchange="getAmountFormatInDynamic((this),'orginalEstamt')"
													data-rule-required="true"></form:input></td>
											<td>
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
													path="bugRevenueMasterDtoList[0].prBudgetCodeid"
													cssClass="form-control mandColorClass chosen-select-no-results"
													id="prBudgetCodeid0"
													onchange="findduplicatecombinationexit(${0})"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="" text="Select Account Head Codes" />
													</form:option>
													<c:forEach items="${accountBudgetCodeMap}"
														varStatus="status" var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value} </form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input
													cssClass="form-control mandColorClass text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="orginalEstamt0"
													path="bugRevenueMasterDtoList[0].orginalEstamt"
													onchange="getAmountFormatInStatic('orginalEstamt0')"
													data-rule-required="true"></form:input></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="revisedEstamt0"
													path="bugRevenueMasterDtoList[0].revisedEstamt"
													readonly="${viewMode ne 'true'}"></form:input></td>
											<td><form:input cssClass="form-control text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="prCollected0"
													path="bugRevenueMasterDtoList[0].prCollected"
													readonly="${viewMode ne 'true'}"></form:input></td>
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
					value="AccountBudgetProjectedRevenueEntry.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>
<c:if test="${tbAcBudgetProjectedRevenueEntry.hasError =='true'}">
	</div>
</c:if>

