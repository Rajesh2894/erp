<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/directPaymentEntry.js"
	type="text/javascript"></script>

<style>
.popUp {
	width: 350px;
	position: absolute;
	z-index: 111;
	display: block;
	top: 275px;
	left: 400px;
	border: 5px solid #ddd;
	border-radius: 5px;
	display: none;
}

.popUp table th {
	padding: 3px !important;
	font-size: 12px;
	background: none !important;
}

.popUp table td {
	padding: 3px !important;
	font-size: 12px;
}
</style>
<script>
$(function() {
	$("#transactionDateId").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	
});
</script>

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="direct.payment.entry.title"
				text="Direct Payment Entry" />
		</h2>
		<apptags:helpDoc url="DirectPaymentEntry.html"
			helpDocRefURL="DirectPaymentEntry.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<form:form action="DirectPaymentEntry.html" method="post"
			class="form-horizontal" id="directPaymentEntry"
			name="directPaymentEntry" novalidate="novalidate"
			modelAttribute="paymentEntryDto">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="" value="${mode}" id="formMode" />
			<form:hidden path="" value="${budgetDefParamStatus}"
				id="budgetDefParamStatusFlag" />

			<div class="form-group">
				<label for="transactionDateId"
					class="col-sm-2 control-label required-control"><spring:message
						code="advance.management.master.paymentdate" text="Payment Date" /></label>
				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="transactionDate" id="transactionDateId"
							cssClass="mandColorClass form-control" data-rule-required="true"
							onchange="setInstrumentDate(this)" maxlength="10"></form:input>

						<label class="input-group-addon mandColorClass"
							for="transactionDateId"><i class="fa fa-calendar"></i> </label>
					</div>
				</div>

				<label for="bmBilltypeCpdId"
					class="col-sm-2 control-label required-control"><spring:message
						code="direct.payment.entry.paymenttype" text="Payment Type" /> </label>
				<div class="col-sm-4">
					<form:select path="billTypeId" class="form-control mandColorClass"
						id="billTypeId" onchange="resetPaymentCategoryForm(this)"
						data-rule-required="true">
						<option value=""><spring:message
								code="account.common.select" /></option>
						<c:forEach items="${billTypeList}" varStatus="status"
							var="billType">
							<c:if test="${billTypeCode.contains(billType.lookUpCode)}">
								<form:option value="${billType.lookUpId}"> ${billType.descLangFirst} </form:option>
							</c:if>
						</c:forEach>
					</form:select>
				</div>

			</div>

			<div class="form-group">
				<form:hidden path="vendorDesc" id="vendorDesc" />
				<form:hidden path="successfulFlag" id="successfulFlag" />
				<label for="vendorId"
					class="col-sm-2 control-label required-control"><spring:message
						code="account.tenderentrydetails.VendorEntry" text="Vendor Name" />
				</label>
				<div class="col-sm-4">
					<form:select type="select"
						class="form-control chosen-select-no-results" id="vendorId"
						path="vendorId" onchange="getVendorDescription()">
						<option value=""><spring:message
								code="account.common.select" /></option>
						<c:forEach items="${vendorList}" varStatus="status" var="vendor">
							<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
						</c:forEach>
					</form:select>
				</div>
				 
				<!--  commented against id :#133175 -->
				<%-- <label for="rm_Receivedfrom"
					class="control-label  col-sm-2  required-control "> <spring:message
						code="accounts.receipt.name"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="rm_Receivedfrom" path="payeeName"
						class="form-control" maxLength="200" onkeyup="getVendorDesc()" />
				</div> --%>
			</div>

			<div class="form-group">
				<label for="billRefNo"
					class="control-label  col-sm-2  required-control"> <spring:message
						code="direct.payment.entry.bill.ref.no" text="Bill Ref No."></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="billRefNo" path="billRefNo" class="form-control"
						maxLength="30" />
				</div>
					<label for="Field" class="col-sm-2 control-label required-control"><spring:message
							code="account.common.field" text="Field" /></label>
					<div class="col-sm-4">
						<form:select path="fieldId" class="form-control mandColorClass"
							name="field" id="fieldId" >
							<option value="0"><spring:message
									code="account.common.select" /></option>
							<c:forEach items="${listOfTbAcFieldMasterItems}"
								varStatus="status" var="fieldItem">
								<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
			</div>
			<div class="popUp">
				<!-- Budget details will appear here -->
			</div>
			<h4 type="h4" class="" id="">
				<spring:message code="direct.payment.entry.acc.head.detail"
					text="Account Head Details" />
			</h4>
			<div id="table-responsive1">
				<table id="budgetDetailsTable"
					class="table text-left table-striped table-bordered">
					<tbody>
						<tr>
							<th scope="col" width="65%" id="thBudgetHead"><spring:message
									code="account.deposit.accountHead" text="Account Head" /><span
								class="mand">*</span></th>
							<th scope="col" width="20%" id="thAmount"><spring:message
									code="advance.management.master.paymentamount"
									text="Payment Amount" /><span class="mand">*</span></th>
							<th scope="col" width="5%" id="thView"><spring:message
									code="budget.reappropriation.master.budget" text="Budget" /></th>
							<th scope="col" width="10%" id="thAction"><spring:message
									code="bill.action" text="Action" /></th>
						</tr>
						<c:forEach items="${projectedExpenditureList}" var="prExpList"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="budgetDetailClass">
								<td><form:select path="paymentDetailsDto[${count}].id"
										aria-labelledby="thBudgetHead"
										class="form-control mandColorClass chosen-select-no-results"
										id="budgetCodeId${count}"
										onchange="validateBudgetHead(${count})"
										data-rule-required="true">
										<option value=""><spring:message
												code="account.common.select" /></option>
										<c:forEach items="${expBudgetCodeMap}" varStatus="status"
											var="expItem">
											<form:option value="${expItem.key}" code="${expItem.key}">${expItem.value}</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:hidden id="notEnoughBudgetFlag${count}" path="" />
									<form:input type="text" aria-labelledby="thAmount"
										onchange="getAmountFormat(${count})"
										onkeyup="totalPaymentamount()"
										onkeypress="return hasAmount(event, this, 13, 2)"
										class="form-control mandColorClass text-right"
										path="paymentDetailsDto[${count}].paymentAmount"
										id="paymentAmount${count}" data-rule-required="true" /></td>
								<td>
									<button type="button" title="View Budget"
										class="btn btn-primary btn-sm viewExp"
										onclick="viewBillDetails(${count})" id="viewExpDet${count}"
										name="">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
								</td>
								<td>
									<button type="button" class="btn btn-success btn-sm addButton"
										name="button-plus" id="button-plus">
										<i class="fa fa-plus-circle" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-danger btn-sm delButton"
										name="button-123" id="button-minus">
										<i class="fa fa-minus-circle" aria-hidden="true"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>


			</div>
			<div id="padad">
				<jsp:include page="/jsp/account/transactions/directPaymentEntry.jsp" />
			</div>


		</form:form>
	</div>
</div>