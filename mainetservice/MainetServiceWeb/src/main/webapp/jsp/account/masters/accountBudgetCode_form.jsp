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
<script src="js/account/accountBudgetCode.js" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.tooltip-inner {
	text-align: left;
}
</style>
<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>
<c:if test="${tbAcBudgetCode.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="account.budget.code.master.title" text="" />
		</h2>
		<!-- <div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"><i
				class="fa fa-question-circle fa-lg"></i></a>
		</div> -->
		<apptags:helpDoc url="AccountBudgetCode.html" helpDocRefURL="AccountBudgetCode.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcBudgetCode" name="frmMaster" method="POST"
			action="AccountBudgetCode.html">
			<form:hidden path="" id="secondaryId" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" value="0"/>
			<form:hidden path="successFlag" id="successFlag" />
			<form:hidden path="cpdIdStatusFlagDup" id="cpdIdStatusFlagDup" />
			<form:hidden path="objectHeadType" id="objectHeadType" />
			<form:hidden path="" value="${MODE_DATA}" id="formMode_Id" />
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

			<div class="error-div alert alert-danger alert-dismissible "
				id="errorDivIdI" style="display: none;">
				<span id="errorIdI"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<c:set var="count" value="0" scope="page" />

			<ul id="ulId">
				<li>
					<fieldset id="divId" class="clear">
						<%-- <div class="form-group">
							<c:if test="${budgetSubTypeStatus == 'Y'}">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="account.budget.code.master.budgetsubtype" text="" /></label>
								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select path="cpdBugsubtypeId"
											class="form-control mandColorClass" id="cpdBugsubtypeId"
											disabled="${viewMode}" onchange="clearAllData(this)"
											data-rule-required="false">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectbudgetsubtype"
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
										<form:select path="cpdBugsubtypeId" class="form-control"
											id="cpdBugsubtypeId" disabled="${viewMode ne 'true'}"
											onchange="clearAllData(this)">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectbudgetsubtype"
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
							<form:hidden path="prBudgetCodeid" />

							<c:if test="${deptStatus == 'Y'}">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="account.budget.code.master.departmenttype" text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select path="dpDeptid"
											class="form-control mandColorClass chosen-select-no-results"
											id="dpDeptid" disabled="${viewMode}"
											onchange="clearAllData(this)" data-rule-required="false">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectdepartment" text="" />
											</form:option>
											<c:forEach items="${deptMap}" varStatus="status"
												var="deptMap">
												<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
										<form:hidden path="dpDeptid" id="dpDeptid" />
										<form:select path="dpDeptid"
											class="form-control chosen-select-no-results" id="dpDeptid"
											disabled="${viewMode ne 'true'}"
											onchange="clearAllData(this)">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectdepartment" text="" />
											</form:option>
											<c:forEach items="${deptMap}" varStatus="status"
												var="deptMap">
												<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

							</c:if>

							<c:if test="${fundStatus == 'Y'}">
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.budget.code.master.fundcode" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select id="fundId" path="fundId"
											cssClass="form-control mandColorClass chosen-select-no-results"
											disabled="${viewMode}" onchange="clearAllData(this)"
											data-rule-required="true">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectfundcode" />
											</form:option>
											<c:forEach items="${listOfTbAcFundMasterItems}"
												varStatus="status" var="fundItem">
												<form:option value="${fundItem.key}" code="${fundItem.key}">${fundItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
										<form:hidden path="fundId" id="fundId" />
										<form:select id="fundId" path="fundId"
											cssClass="form-control chosen-select-no-results"
											disabled="${viewMode ne 'true'}"
											onchange="clearAllData(this)">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectfundcode" />
											</form:option>
											<c:forEach items="${listOfTbAcFundMasterItems}"
												varStatus="status" var="fundItem">
												<form:option value="${fundItem.key}" code="${fundItem.key}">${fundItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

							</c:if>

						</div>

						<div class="form-group">

							<form:hidden path="prBudgetCodeid" />

							<c:if test="${fieldStatus == 'Y'}">
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.budget.code.master.fieldcode" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select id="fieldId" path="fieldId"
											cssClass="form-control mandColorClass chosen-select-no-results"
											disabled="${viewMode}" onchange="clearAllData(this)"
											data-rule-required="false">
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
										<form:hidden path="fieldId" id="fieldId" />
										<form:select id="fieldId" path="fieldId"
											cssClass="form-control "
											disabled="${viewMode ne 'true'}"
											onchange="clearAllData(this)">
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

						</div> --%>

						<h4>
							<spring:message code="account.budget.code.master.title" text="Budget Head"></spring:message>
						</h4>

						<form:hidden path="prBudgetCodeid" />
						<div id="prBudgetCodeid" class="">
							<div class="table-overflow-sm" id="budRevTableDivID">
								<table class="table  table-bordered table-striped "
									id="budRevTable">
									<c:if test="${MODE_DATA == 'create'}">
										<tr>
											<th scope="col" width="20%"><spring:message
													code="account.budget.code.master.functioncode" text="" /><span
												class="mand">*</span></th>
											<th scope="col" width="30%" style="text-align: center"><spring:message
													code="account.budget.code.primaryaccountcode" text="Primary Head" /><span class="mand">*</span></th>
											<th scope="col" width="40%"><spring:message code="account.budget.head.code.description"
													text="Budget Head Code - Description" /><span class="mand">*</span>
												<a class="pull-right" data-toggle="tooltip"
												data-placement="top" data-html="true"
												title="<ul><li>1).Department (Optional)</li><li>2).Fund (Optional)</li><li>3).Field (Optional)</li><li>4).Function</li><li>5).Primary Head</li><li>6).Secondary Head</li><li>7).Secondary Description</li></ul>">
													<i class="fa fa-info-circle" aria-hidden="true"></i>
											</a></th>
											<th class="text-center" scope="col" width="10%"><span
												class="small"><spring:message
														code="account.budget.code.master.addremove" text="" /></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<tr>
											<th scope="col" width="20%"><spring:message
													code="account.budget.code.master.functioncode" text="" /><span
												class="mand">*</span></th>
											<th scope="col" width="30%" style="text-align: center"><spring:message
													code="budget.allocation.master.primaryaccountcode" text="Primary Head" /><span class="mand">*</span></th>
											<th scope="col" width="10%"><spring:message
													code="account.budget.code.master.status" text="" /><span
												class="mand">*</span></th>
											<th scope="col" width="40%"><spring:message code="account.budget.head.code.description"
													text="Budget Head Code - Description" /><span class="mand">*</span>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'create'}">
										<tr id="budRevId" class="appendableClass">
											<td><form:select id="functionId${count}"
													path="budgCodeMasterDtoList[${count}].functionId"
													cssClass="form-control mandColorClass chosen-select-no-results"
													disabled="${viewMode}" onchange="setBudgetCode(this,${count});"
													data-rule-required="true">
													<form:option value="">
														<spring:message
															code="account.budget.code.master.selectfunctioncode" />
													</form:option>
													<c:forEach items="${listOfTbAcFunctionMasterItems}"
														varStatus="status" var="functionItem">
														<form:option value="${functionItem.key}"
															code="${functionItem.key}">${functionItem.value}</form:option>
													</c:forEach>
											</form:select></td>
											<td><form:select id="pacHeadId${count}"
													path="budgCodeMasterDtoList[${count}].sacHeadId"
													cssClass="form-control mandColorClass chosen-select-no-results"
													disabled="${viewMode}"
													onchange="findduplicatecombinationexit(${count})"
													data-rule-required="true">
													<form:option value="">
														<spring:message
															code="account.budget.code.master.selectprimaryaccountcode" />
													</form:option>
													<c:forEach items="${listOfPrimaryAcHeadMapMasterItems}"
														varStatus="status" var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
													</c:forEach>
												</form:select></td>
											<td><textarea id="prBudgetCode${count}"
													name="budgCodeMasterDtoList[${count}].prBudgetCode"
													class="form-control mandColorClass" maxLength="500"
													tabindex="-1" readonly="${viewMode ne 'true'}"
													data-rule-required="true"></textarea>
												</td>

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
										<tr id="budRevId" class="appendableClass">
											<td>
											<form:select id="functionId0"
													path="budgCodeMasterDtoList[0].functionId"
													cssClass="form-control mandColorClass" readonly="${viewMode ne 'true'}">
													<form:option value="" >
														<spring:message
															code="account.budget.code.master.selectfunctioncode" />
													</form:option>
													<c:forEach items="${listOfTbAcFunctionMasterItems}"
														varStatus="status" var="functionItem">
														<form:option value="${functionItem.key}"
															code="${functionItem.key}">${functionItem.value}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:select
													path="budgCodeMasterDtoList[0].sacHeadId"
													cssClass="form-control mandColorClass chosen-select-no-results"
													id="pacHeadId0"
													onchange="findduplicatecombinationexit(${0})"
													data-rule-required="true" readonly="${viewMode ne 'true'}">
													<form:option value="">
														<spring:message
															code="account.budget.code.master.selectprimaryaccountcode"
															text="" />
													</form:option>
													<c:forEach items="${listOfPrimaryAcHeadMapMasterItems}"
														varStatus="status" var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value} </form:option>
													</c:forEach>
												</form:select></td>

											<td><form:select id="cpdIdStatusFlag0"
													path="budgCodeMasterDtoList[0].cpdIdStatusFlag"
													cssClass="form-control mandColorClass"
													disabled="${viewMode}" onchange=""
													data-rule-required="true" >
													<c:forEach items="${activeDeActiveMap}" varStatus="status"
														var="activeItem">
														<form:option code="${activeItem.lookUpCode}"
															value="${activeItem.lookUpCode}">${activeItem.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>

											<td><form:textarea id="prBudgetCode0"
													path="budgCodeMasterDtoList[0].prBudgetCode"
													class="form-control" maxLength="500" tabindex="-1"
													readonly="${viewMode ne 'true'}" data-rule-required="true" />
											</td>
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
					<input type="button" id="Reset" class="btn btn-warning createData"
						value="<spring:message code="account.bankmaster.reset" text="Reset" />"></input>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="<spring:message code="account.bankmaster.save" text="Save" />"> </input>
				</c:if>
				<spring:url var="cancelButtonURL" value="AccountBudgetCode.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>
<c:if test="${tbAcBudgetCode.hasError =='true'}">
	</div>
</c:if>

