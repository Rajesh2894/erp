<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
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

<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/RTGSPaymentEntry.js"
	type="text/javascript"></script>
<script>
	$(function(){
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
			<strong><spring:message code="account.direct.rtgsPaymentEntry"
					text="RTGS Payment Entry" /></strong>
		</h2>
	<apptags:helpDoc url="RTGSPaymentEntry.html" helpDocRefURL="RTGSPaymentEntry.html"></apptags:helpDoc>	
	</div>
	<div class="widget-content padding" id="paymentEntryDiv">
		<form:form method="POST" action="RTGSPaymentEntry.html"
			cssClass="form-horizontal" name="RTGSPaymentEntry" id="RTGSPaymentEntry"
			modelAttribute="RTGSPaymentEntryDto">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="" value="${templateExistFlag}"
				id="templateExistFlag" />
			<form:hidden path="dupTransactionDate" id="dupTransactionDate" />
			<form:hidden path="dupVendorId" id="dupVendorId" />
			<form:hidden path="dupBillTypeId" id="dupBillTypeId" />
			<form:hidden path="" value="${budgetDefParamStatus}" id="budgetDefParamStatusFlag" />
			
			<div class="form-group">
				<label for="transactionDateId"
					class="col-sm-2 control-label required-control"><spring:message
						code="advance.management.master.paymentdate" text="Payment Date" /></label>
				<div class="col-sm-4">
					<div class="input-group">
					
					<c:set var="now" value="<%=new java.util.Date()%>" />
								<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
								<form:input path="transactionDate" id="transactionDateId" cssClass="mandColorClass form-control" data-rule-required="true" 
									value="${date}" onchange="setInstrumentDate(this)" maxlength="10"></form:input>
							
						<label class="input-group-addon mandColorClass"
							for="transactionDateId"><i class="fa fa-calendar"></i> </label>
						<form:hidden path="transactionDate" id="transactionDateId"/>
					</div>
				</div>
				  
				  <!-- commented below field against id #133284 -->
				<%-- <label for="bmBilltypeCpdId"
					class="col-sm-2 control-label required-control"><spring:message
						code="" text="Payment Order Type"/></label>
				<div class="col-sm-4">
				<form:select type="select" path="billTypeId"
						class="form-control chosen-select-no-results chosenReset"
						name="billTypeId" id="bmBilltypeCpdId" disabled="${viewMode}"
						data-rule-required="true">
						<option value=""><spring:message code="account.select"
								text="Select" /></option>
						<c:forEach items="${billTypeList}" varStatus="status"
							var="billType">
							<form:option code="${billType.lookUpCode}"
													value="${billType.lookUpId}">${billType.descLangFirst}</form:option>
							<form:option value="${billType.lookUpId}" >${billType.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
					<form:hidden path="billTypeId" id="bmBilltypeCpdId" data-rule-required="false"/>
				</div> --%>
			</div>
			<div class="popUp">
				<!-- Budget details will appear here -->
			</div>
			<h4 id="">
				<spring:message code="account.direct.paymentOrderDetails"
					text="Payment Order Details" />
			</h4>
			<div class="table-responsive" id="billDetailTableDiv">
				<table id="billDetailTable"
					class="table table-bordered table-striped billDetailTableClass">
					<thead>
						<tr>
							<th scope="col" id="vendorId" width="20%"><spring:message code="accounts.vendormaster.vendorName"
									text="Vendor Name" /><span class="mand">*</span></th>
							<th scope="col" id="thBillNo" width="24%"><spring:message code="advance.management.master.requisitionnumber"
									text="Payment Order Number" /><span class="mand">*</span></th>
							<th scope="col" id="thNetPayable" width="20%"><spring:message
								code="account.directTable.balancePayable"	text="Balance Payable" /></th>
							<th scope="col" id="thPayableAmount" width="20%"><spring:message
								code="advance.management.master.paymentamount"	text="Payment Amount" /><span class="mand">*</span></th>
							<th scope="col" id="thView" width="6%"><spring:message
								code="account.directTable.viewBill"	text="View Bill" /></th>
							<th scope="col" id="thAction" width="10%"><spring:message
								code="bill.action"	text="Action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${billDetailList}" var="billDetList"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="billDetailClass">
							<td><%-- <form:hidden path="rtgsPaymentDetailsDto[${count}].vendorDesc" id="vendorDesc${count}" /> --%>
								<form:select path="rtgsPaymentDetailsDto[${count}].vendorId"
										class="form-control chosen-select-no-results chosenReset"
										name="vendorId${count}" id="vendorId${count}" data-rule-required="true" onchange="getBillNumbers(${count})">
										<option value=""><spring:message code="account.select"
												text="Select" /></option>
										<c:forEach items="${vendorList}" varStatus="status" var="vendor">
											<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:hidden path="rtgsPaymentDetailsDto[${count}].billTypeId" id="billTypeId${count}"/>
									<form:select id="id${count}" aria-labelledby="thBillNo"
										path="rtgsPaymentDetailsDto[${count}].id"
										onchange="getBillData(${count})"
										class="form-control mandColorClass"
										data-rule-required="true">
										<option value=""><spring:message
												code="account.select" text="Select" /></option>
									</form:select></td>
								<td><form:hidden path="budgetCodeId" id="budgetCodeId" />
								 <form:input id="netPayable${count}"
										aria-labelledby="thNetPayable"
										path="rtgsPaymentDetailsDto[${count}].netPayable" readonly="true"
										cssClass="form-control text-right" /></td>
								<td><form:input id="paymentAmount${count}"
										aria-labelledby="thPayableAmount"
										path="rtgsPaymentDetailsDto[${count}].paymentAmount" onkeyup="calcTotalAmount()"							
										onkeypress="return hasAmount(event, this, 13, 2)" onchange="validatePaymentAmount(${count})" disabled=""
										cssClass="form-control mandColorClass text-right"
										data-rule-required="true" /></td>
										
								<td class="text-center">
									<button type="button" title="View Budget"
										class="btn btn-primary btn-sm viewBill" onclick="viewBillDetails(${count})"
										id="viewBillDet${count}">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
								</td>
								<td class="text-center">
									<button data-placement="top" title="Add"
										class="btn btn-success btn-sm addButton11"
										id="addButton12${count}">
										<i class="fa fa-plus-circle"></i>
									</button>
									<button data-placement="top" title="Delete"
										class="btn btn-danger btn-sm delButton"
										id="delButton12${count}">
										<i class="fa fa-trash-o"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
			<jsp:include page="/jsp/account/transactions/SubRTGSPaymentEntryForm.jsp" />

		</form:form>
	</div>
</div>
