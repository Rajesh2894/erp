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
<script src="js/account/additionalSupplementalAuthorization.js" />
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

<c:if test="${tbAcAdditionalSuppAuthorization.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>



<script>
 $('#sacHeadId${count}').val($('#secondaryId').val());
</script>

<div class="widget" id="widget">

	<div class="widget-header">
		<h2>
			<spring:message
				code="budget.additionalsupplemental.authorization.title" text="" />
		</h2>
	<apptags:helpDoc url="AuthorizationAdditionalSupplemental.html" helpDocRefURL="AuthorizationAdditionalSupplemental.html"></apptags:helpDoc>	
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcAdditionalSuppAuthorization" method="POST"
			action="AuthorizationAdditionalSupplemental.html">
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
			<form:hidden path="paAdjid" id="paAdjid" />
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


							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.additionalsupplemental.master.financialyear"
									text="" /></label>
							<div class="col-sm-4">
								<form:select id="faYearid" path=""
									cssClass="form-control mandColorClass"
									onchange="setHiddenField(this);">

									<c:forEach items="${financeMap}" varStatus="status"
										var="financeMap">
										<form:option value="${financeMap.key}"
											code="${financeMap.key}">${financeMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.additionalsupplemental.master.departmenttype"
									text="" /></label>

							<div class="col-sm-4">
								<form:hidden path="dpDeptid" id="dpDeptid" />
								<form:select path="dpDeptid" class="form-control mandColorClass"
									id="dpDeptid" disabled="${viewMode ne 'true'}"
									onchange="clearAllData(this)">
									<form:option value="">
										<spring:message
											code="budget.additionalsupplemental.master.departmentttype"
											text="" />
									</form:option>
									<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
										<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.additionalsupplemental.master.budgettype" text="" /></label>

							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="REX" />
								<form:hidden path="cpdBugtypeId" id="cpdBugtypeId" />
								<form:select path="cpdBugtypeId"
									class="form-control mandColorClass" id="cpdBugtypeId"
									onchange="clearAllData(this)" disabled="${viewMode ne 'true'}">
									<form:option value="">
										<spring:message
											code="budget.additionalsupplemental.master.selectbudgettype"
											text="" />
									</form:option>
									<c:forEach items="${levelMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.additionalsupplemental.master.budgetsubtype"
									text="" /></label>

							<div class="col-sm-4">
								<form:hidden path="cpdBugSubTypeId" id="cpdBugSubTypeId" />
								<form:select path="cpdBugSubTypeId"
									class="form-control mandColorClass" id="cpdBugSubTypeId"
									disabled="${viewMode ne 'true'}"
									onchange="loadBudgetAdditionalSupplementalData(this)">
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
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="budget.additionalsupplemental.master.remark" text=""></spring:message>
							</label>
							<div class="col-sm-10">
								<form:textarea id="remark" path="remark"
									class="form-control mandColorClass" maxLength="200"
									data-rule-required="true" />
							</div>
						</div>



						<form:hidden path="paAdjid" id="paAdjid${count}"
							value="${paAdjid}" />
						<input type="hidden" value="${viewMode}" id="test" />

						<c:if test="${!viewMode}">
							<div id="prProjectionid" class="hide">
								<form:hidden path="paAdjid" />


								<div class="table-overflow-sm">
									<table class="table  table-bordered table-striped "
										id="revTable">

										<c:if test="${MODE_DATA == 'EDIT'}">

											<tr>
												<th width="50%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.budgethead"
															text="" /><span class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.budgetprovision"
															text="" /><span class="mand"></span></span></th>
												<th width="20%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.additionalprovision"
															text="" /><span class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.revisedamount"
															text="" /></span></th>
											</tr>
											<tr id="bugestIdRev0" class="appendableClass">

												<form:hidden
													path="bugprojRevBeanList[${0}].prProjectionidRevDynamic"
													value="${bugprojRevBeanList[0].prProjectionidRevDynamic}"
													code="${bugprojRevBeanList[0].prProjectionidRevDynamic}"
													id="prProjectionidRevDynamic${0}" />

												<td><form:select
														path="bugprojRevBeanList[${0}].prRevBudgetCode"
														cssClass="form-control mandColorClass chosen-select-no-results"
														id="prRevBudgetCode${0}" onchange="getOrgBalAmount(${0})">
														<form:option value="">
															<spring:message code="" text="Select" />
														</form:option>
														<c:forEach items="${accountBudgetCodeMap}"
															varStatus="status" var="budgetCodeItem">
															<form:option value="${budgetCodeItem.key}"
																code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
														</c:forEach>
													</form:select></td>

												<td><form:input cssClass="form-control text-right"
														id="orginalEstamt${0}"
														path="bugprojRevBeanList[${0}].orginalEstamt"
														readonly="true"></form:input></td>
												<td><form:input
														cssClass="form-control mandColorClass text-right"
														onkeypress="return hasAmount(event, this, 13, 2)"
														id="transferAmount${0}"
														path="bugprojRevBeanList[${0}].transferAmount"
														onkeyup="copyContent(this)"
														onchange="getAmountFormatInStatic('transferAmount0')"></form:input></td>
												<td><form:input cssClass="form-control text-right"
														id="newOrgRevAmount${0}"
														path="bugprojRevBeanList[${0}].newOrgRevAmount"
														readonly="true"></form:input></td>

											</tr>
										</c:if>

									</table>
								</div>

							</div>
						</c:if>

						<c:if test="${!viewMode}">
							<div id="prExpenditureid" class="hide">
								<form:hidden path="paAdjid" />


								<div class="table-overflow-sm">
									<table class="table  table-bordered table-striped "
										id="expTable">

										<c:if test="${MODE_DATA == 'EDIT'}">

											<tr>
												<th width="50%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.budgethead"
															text="" /><span class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.budgetprovision"
															text="" /><span class="mand"></span></span></th>
												<th width="20%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.additionalprovision"
															text="" /><span class="mand">*</span></span></th>
												<th width="15%" class="text-center" scope="col"><span
													class="small"><spring:message
															code="budget.additionalsupplemental.master.revisedamount"
															text="" /></span></th>
											</tr>
											<tr id="bugestIdExp0" class="ExpappendableClass">

												<form:hidden
													path="bugprojExpBeanList[${0}].prExpenditureidExpDynamic"
													value="${bugprojExpBeanList[0].prExpenditureidExpDynamic}"
													code="${bugprojExpBeanList[0].prExpenditureidExpDynamic}"
													id="prExpenditureidExpDynamic${0}" />

												<td><form:select
														path="bugprojExpBeanList[${0}].prExpBudgetCode"
														cssClass="form-control mandColorClass chosen-select-no-results"
														id="prExpBudgetCode${0}"
														onchange="getOrgBalExpAmount(${0})">
														<form:option value="">
															<spring:message
																code="budget.reappropriation.master.select" text="" />
														</form:option>
														<c:forEach items="${accountBudgetCodeMap}"
															varStatus="status" var="budgetCodeItem">
															<form:option value="${budgetCodeItem.key}"
																code="${budgetCodeItem.key}">${budgetCodeItem.value}</form:option>
														</c:forEach>
													</form:select></td>

												<td><form:input cssClass="form-control text-right"
														id="ExporginalEstamt${0}"
														path="bugprojExpBeanList[${0}].orginalEstamt"
														readonly="true"></form:input></td>
												<td><form:input
														cssClass="form-control mandColorClass text-right"
														onkeypress="return hasAmount(event, this, 13, 2)"
														id="ExptransferAmount${0}"
														path="bugprojExpBeanList[${0}].transferAmountO"
														onkeyup="copyExpContent(this)"
														onchange="getAmountFormatInStatic('ExptransferAmount0')"></form:input></td>
												<td><form:input cssClass="form-control text-right"
														id="newOrgExpAmount${0}"
														path="bugprojExpBeanList[${0}].newOrgExpAmountO"
														readonly="true"></form:input></td>

											</tr>
										</c:if>

									</table>
								</div>

							</div>
						</c:if>

					</fieldset>

					<div class="form-group margin-top-10">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code="budget.additionalsupplemental.authorization.fieldname"
								text=""></spring:message></label>
						<div class="col-sm-10">

							<label for="approved1" class="radio-inline mandColorClass"><form:radiobutton
									path="approved" value="Y" id="approved1"
									data-rule-required="true" /> <spring:message
									code="account.pay.tds.approved" text="Approved" /> </label> <label
								for="disApproved" class="radio-inline mandColorClass"><form:radiobutton
									path="approved" value="N" id="disApproved"
									data-rule-required="true" /> <spring:message
									code="account.pay.tds.disapproved" text="Unapproved" /> </label>

						</div>
					</div>

				</li>
			</ul>


			<input type="hidden" id="count" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save"> </input>
				</c:if>
				<spring:url var="cancelButtonURL"
					value="AuthorizationAdditionalSupplemental.html" />
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


