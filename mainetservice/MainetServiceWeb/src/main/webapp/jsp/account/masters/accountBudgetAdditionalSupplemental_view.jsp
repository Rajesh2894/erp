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
<script src="js/account/accountBudgetAdditionalSupplemental.js" />
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

<c:if test="${tbAcBudgetAdditionalSupplemental.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>

<script>
 $('#sacHeadId${count}').val($('#secondaryId').val());
</script>

<div class="widget" id="widget">

	<div class="widget-header">
		<h2>
			<spring:message code="budget.additionalsupplemental.master.title"
				text="" />
		</h2>
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAcBudgetAdditionalSupplemental" method="POST"
			action="AccountBudgetAdditionalSupplemental.html">
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
									code="budget.additionalsupplemental.master.financialyear"
									text="" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="financialYearDesc"
									class="form-control" id="financialYearDesc" />
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="budget.additionalsupplemental.master.departmenttype"
									text="" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="departmentDesc"
									class="form-control" id="departmentDesc" />
							</div>
						</div>

						<div class="form-group">

							<c:if test="${budgetTypeStatus == 'Y'}">
								<label class="col-sm-2 control-label "><spring:message
										code="budget.additionalsupplemental.master.budgettype" text="" /></label>

								<div class="col-sm-4">
									<form:input type="text" path="cpdBugtypeDesc"
										class="form-control" id="cpdBugtypeDesc" />
								</div>
							</c:if>

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

						<div class="form-group">
							<label class="control-label col-sm-2 "> <spring:message
									code="budget.additionalsupplemental.master.remark" text=""></spring:message>
							</label>
							<div class="col-sm-10">
								<form:textarea id="remark" path="remark" class="form-control"
									maxLength="200" />
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

										<tr>
											<th width="40%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.budgethead"
														text="" /></span></th>
											<th width="20%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.budgetprovision"
														text="" /></span></th>
											<th width="20%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.additionalprovision"
														text="" /></span></th>
											<th width="20%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.revisedamount"
														text="" /></span></th>
										</tr>

										<c:forEach
											items="${tbAcBudgetAdditionalSupplemental.bugprojRevBeanList}"
											var="prRevList">
											<tr id="bugestIdRev0" class="appendableClass">

												<form:hidden
													path="bugprojRevBeanList[${count}].prProjectionidRevDynamic"
													value="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
													code="${bugprojRevBeanList[count].prProjectionidRevDynamic}"
													id="prProjectionidRevDynamic${count}" />

												<td><c:set value="${prRevList.prRevBudgetCode}"
														var="prRevBudgetCode"></c:set> <c:forEach
														items="${accountBudgetCodeMap}" varStatus="status"
														var="budgetCodeRevItem">
														<c:if test="${prRevBudgetCode eq budgetCodeRevItem.key}">
															<form:input type="text"
																value="${budgetCodeRevItem.value}" path=""
																class="form-control" id="prRevBudgetCode" />
														</c:if>
													</c:forEach></td>

												<td><form:input cssClass="form-control text-right"
														id="orginalEstamt${count}"
														path="bugprojRevBeanList[${count}].orginalEstamt"
														readonly="true"></form:input></td>
												<td><form:input cssClass="form-control text-right"
														onkeypress="return hasAmount(event, this, 13, 2)"
														id="transferAmount${count}"
														path="bugprojRevBeanList[${count}].transferAmount"
														onkeyup="copyContent(this)"></form:input></td>
												<td><form:input cssClass="form-control text-right"
														id="newOrgRevAmount${count}"
														path="bugprojRevBeanList[${count}].newOrgRevAmount"
														readonly="true"></form:input></td>

											</tr>
										</c:forEach>

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

										<tr>
											<th width="40%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.budgethead"
														text="" /></span></th>
											<th width="20%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.budgetprovision"
														text="" /></span></th>
											<th width="20%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.additionalprovision"
														text="" /></span></th>
											<th width="20%" class="text-center" scope="col"><span
												class="small"><spring:message
														code="budget.additionalsupplemental.master.revisedamount"
														text="" /></span></th>
										</tr>

										<c:forEach
											items="${tbAcBudgetAdditionalSupplemental.bugprojExpBeanList}"
											var="prExpList" varStatus="status">
											<tr id="bugestIdExp0" class="ExpappendableClass">

												<form:hidden
													path="bugprojExpBeanList[${count}].prExpenditureidExpDynamic"
													value="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
													code="${bugprojExpBeanList[count].prExpenditureidExpDynamic}"
													id="prExpenditureidExpDynamic${count}" />

												<td><c:set value="${prExpList.prExpBudgetCode}"
														var="prExpBudgetCode"></c:set> <c:forEach
														items="${accountBudgetCodeMap}" varStatus="status"
														var="budgetCodeItem">
														<c:if test="${prExpBudgetCode eq budgetCodeItem.key}">
															<form:input type="text" value="${budgetCodeItem.value}"
																path="" class="form-control" id="prExpBudgetCode" />
														</c:if>
													</c:forEach></td>
												<td><form:input cssClass="form-control text-right"
														id="ExporginalEstamt${count}"
														path="bugprojExpBeanList[${count}].orginalEstamt"
														readonly="true"></form:input></td>
												<td><form:input cssClass="form-control text-right"
														onkeypress="return hasAmount(event, this, 13, 2)"
														id="ExptransferAmount${count}"
														path="bugprojExpBeanList[${count}].transferAmountO"
														onkeyup="copyExpContent(this)"></form:input></td>
												<td><form:input cssClass="form-control text-right"
														id="newOrgExpAmount${count}"
														path="bugprojExpBeanList[${count}].newOrgExpAmountO"
														readonly="true"></form:input></td>

											</tr>
										</c:forEach>

									</table>
								</div>

							</div>
						</c:if>

					</fieldset>

				</li>
			</ul>


			<input type="hidden" id="count" value="0" />

			<div class="text-center padding-top-10">

				<spring:url var="cancelButtonURL"
					value="AccountBudgetAdditionalSupplemental.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.back" text="Back" /></a>
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


