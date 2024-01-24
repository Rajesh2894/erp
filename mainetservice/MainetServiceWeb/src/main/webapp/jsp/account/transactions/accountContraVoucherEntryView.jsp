<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/script-library.js"></script>
<script>
$(function() {
	
	$("#cashDepositPanel").hide();
	$("#cashWithDrawPanel").hide();
	$("input[name=contraType][value=" + "T" + "]").attr('checked', 'checked');
	var mode = $("#editMode").val();
	if(mode !="edit"){
		 $(".datepicker").datepicker({
			    dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true,
				maxDate: '0'
				
			});
			$(".datepicker").datepicker('setDate', new Date()); 
	}

	if(mode =="edit"){
		
		var bankAcTrn = $("#baAccountidRec").val();
		$('#payToTransUpd').val(bankAcTrn);

		var bankAcWth = $("#baAccountidPayWth").val();
		$('#payToWthUpd').val(bankAcWth);
	}
	
});



$( document ).ready(function() {
	
	
	 if( $('input[name=coTypeFlag]:checked').val() =='T'){
		 
		 	$("#transferPanel").show();
		 	$("#cashWithDrawPanel").hide();
		 	$("#cashDepositPanel").hide();
			$('#withdraw').prop("disabled", true);
			$('#deposit').prop("disabled", true);
		
	 }
	
	 
	 if( $('input[name=coTypeFlag]:checked').val() =='W'){
		 
		 	$("#transferPanel").hide();
			$("#cashDepositPanel").hide();
			$("#cashWithDrawPanel").show();
			$('#transfer').prop("disabled", true);
			$('#deposit').prop("disabled", true); 
		 
	 }
		
	 if( $('input[name=coTypeFlag]:checked').val() =='D'){
		 
		 	$("#transferPanel").hide();
			$("#cashWithDrawPanel").hide();
			$("#cashDepositPanel").show();
			$('#withdraw').prop("disabled", true);
			$('#transfer').prop("disabled", true);
		 }


});

</script>


<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="account.contra" text="Contra" />
			<strong><spring:message code="receipt.payment.transaction"
					text="Transactions" /></strong>
		</h2>
	</div>
	<div class="widget-content padding">
		<c:url value="${saveAction}" var="url_form_submit" />
		<c:url value="${mode}" var="form_mode" />
		<c:if test="${mode eq 'update'}">
			<input type=hidden value="edit" id="editMode" />
		</c:if>
		<!--################################### Transfer entry form ###################################-->

		<form:form method="POST" action="ContraVoucherEntry.html"
			class="form-horizontal" name="contraVoucherTransfer"
			id="contraVoucherTransfer" modelAttribute="contraVoucherBean">
			<form:hidden path="successfulFlag" id="successfulFlag" />
			<form:hidden path="coTypeFlag" id="coTypeFlagTrns" value="T" />


			<div class="form-group" id="contraMode">
				<label for="" class="col-sm-2 control-label"><spring:message
						code="account.contra.voucher.transaction.type"
						text="Transaction Types" /></label>
				<div class="col-sm-4">
					<label class="radio-inline"><form:radiobutton
							path="coTypeFlag" id="transfer" name="coTypeFlag" value="T"
							cssClass="set-radio" /> <spring:message code="account.transfer" text="Transfer" /></label>
					<%-- <label class="radio-inline"><form:radiobutton
							path="coTypeFlag" id="withdraw" name="coTypeFlag" value="W"
							cssClass="set-radio" /> <spring:message code="" text="Withdraw" /></label>
					<label class="radio-inline"><form:radiobutton
							path="coTypeFlag" id="deposit" name="coTypeFlag" value="D"
							cssClass="set-radio" /> <spring:message code="" text="Deposit" /></label> --%>
				</div>
			</div>
			<div id="transferPanel">

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.budgetadditionalsupplemental.transactionnumber"
							text="Transaction Number" /></label>
					<div class="col-sm-3">
						<form:input type="text" value="" path="coVouchernumber"
							disabled="true" class="form-control" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="budget.reappropriation.master.transactiondate"
							text="Transaction Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input id="coEntryDate" path="coDateStr"
								disabled="${viewMode}" cssClass="datepicker form-control" />
							<label class="input-group-addon" for="transactionDateTrns"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="transactionDateTrns"> </label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.receipt.payment_mode" text="Mode" /></label>
					<div class="col-sm-3">

						<form:input id="paymentModeDesc" path="paymentModeDesc"
							disabled="${viewMode}" cssClass="form-control" />
					</div>
				</div>


				<h4>
					<spring:message code="account.contra.voucher.payment.detail"
						text="Payment Details" />
				</h4>
				<div class="table-overflow-sm" id="contraTransferPayTableDiv">
					<table id="contraTransferPayTable"
						class="table table-bordered table-striped">
						<tbody>
							<tr>
								<c:if test="${contraVoucherBean.paymentModeDesc eq 'Bank' }">
									<th scope="col" width="82%"><spring:message code="account.fund.bank.acc"
											text="Bank Account" /></th>
								</c:if>
								<c:if test="${contraVoucherBean.paymentModeDesc eq 'Cheque' }">
									<th scope="col" width="50%"><spring:message code="account.fund.bank.acc"
											text="Bank Account" /></th>
								</c:if>
								<c:if test="${contraVoucherBean.paymentModeDesc eq 'Cheque' }">
									<th scope="col" width="17%"><spring:message code="payment.entry.service.bill.intrument.no"
											text="Instrument No" /></th>
									<th scope="col" width="15%"><spring:message code="account.instrument.date"
											text="Instrument Date" /></th>
								</c:if>
								<th scope="col" width="16%"><spring:message code="budget.allocation.master.amount"
										text="Amount" /></th>
							</tr>
							<tr>
								<td><c:set value="${contraVoucherBean.baAccountidPay}"
										var="bankAcPayKey" /> <c:forEach items="${bankAccountMap}"
										varStatus="status" var="bankItem">
										<c:if test="${bankAcPayKey eq bankItem.key }">
											<form:input type="text" value="${bankItem.value}"
												class="form-control" id="baAccountidPay" path=""
												disabled="${viewMode}"></form:input>
										</c:if>
									</c:forEach></td>
								<c:if test="${contraVoucherBean.paymentModeDesc eq 'Cheque' }">
									<td><c:set value="${contraVoucherBean.chequebookDetid}"
											var="instrumentId" /> <c:forEach items="${chequeNoMap}"
											varStatus="status" var="chequeNo">
											<c:if test="${instrumentId eq chequeNo.key }">
												<form:input type="text" value="${chequeNo.value}"
													class="form-control" id="chequebookDetid" path=""
													disabled="${viewMode}"></form:input>
											</c:if>
										</c:forEach></td>
									<td>
										<div class="input-group">
											<form:input type="text" value=""
												class="datepicker form-control" id="coChequedate"
												path="coChequedateStr" disabled="${viewMode}"></form:input>
											<label class="input-group-addon" for="instrumentDateTrns"><i
												class="fa fa-calendar"></i> <input type="hidden"
												id="instrumentDateTrns"> </label>
										</div>
									</td>
								</c:if>
								<td><form:hidden path="bankBalance" id="bankBalance" /> <form:hidden
										path="payee" id="payeeId" /> <form:input type="text"
										id="coAmountPayTrns" cssClass="form-control text-right"
										path="amountPayStr"
										onkeypress="return hasAmount(event, this, 13, 2)"
										disabled="${viewMode}"></form:input></td>
							</tr>
						</tbody>
					</table>
				</div>

				<h4>
					<spring:message code="accounts.receipt.receipt_details"
						text="Receipt Details" />
				</h4>

				<div class="table-overflow-sm" id="contraTransferRecptTableDiv">
					<table id="contraTransferRecptTable"
						class="table table-bordered table-striped">
						<tbody>
							<tr>
								<th scope="col" width="50%"><spring:message code="account.fund.bank.acc"
										text="Bank Account" /></th>
								<th scope="col" width="34%"><spring:message code="accounts.vendormaster.payTo"
										text="Pay To" /></th>
								<th scope="col" width="16%"><spring:message code="budget.allocation.master.amount"
										text="Amount" /></th>
							</tr>
							<tr>
								<td><c:set value="${contraVoucherBean.baAccountidRec}"
										var="bankAcRecKey" /> <c:forEach items="${bankAccountMap}"
										varStatus="status" var="bankItemRec">
										<c:if test="${bankAcRecKey eq bankItemRec.key }">
											<form:input type="text" value="${bankItemRec.value}"
												class="form-control" path="" disabled="${viewMode}"
												id="baAccountidRec"></form:input>
										</c:if>
									</c:forEach></td>

								<td><c:if test="${mode eq 'create'}">
										<form:input type="text" value="" class="form-control"
											id="payToTrans" path="payTo" disabled="${viewMode}"></form:input>
									</c:if> <c:if test="${mode eq 'update'}">
										<form:input type="text" value="" class="form-control"
											id="payToTransUpd" path="payTo" disabled="${viewMode}"></form:input>
									</c:if></td>
								<td><form:hidden path="coAmountRec"
										id="coAmountRecTrnsHidden" /> <form:input type="text"
										id="coAmountRecTrns" cssClass="form-control text-right"
										path="amountRecStr" disabled="true"
										onkeypress="return hasAmount(event, this, 13, 2)"></form:input>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				&nbsp;
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.common.desc" text="Description" /></label>
					<div class="col-sm-10">
						<form:textarea class="form-control" id="coRemarkPay"
							path="coRemarkPay" disabled="${viewMode}"></form:textarea>
					</div>
				</div>
				<div class="text-center padding-top-20">
					<c:if test="${!viewMode}">
						<button type="button" onclick="saveContraEntry(this,'T')"
							class="btn btn-success btn-submit">
							<spring:message code="account.bankmaster.save" text="Save" />
						</button>
						<button type="button" class="btn btn-warning"
							onclick="clearTransferForm()">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger"
						name="button-1496152380275" style=""
						onclick="window.location.reload()" id="backBtn">
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>

			</div>
		</form:form>

		<!--################################### Cash Withdrwal Entry form ###################################-->
		<div id="cashWithDrawPanel">
			<form:form method="POST" action="ContraVoucherEntry.html"
				class="form-horizontal" name="contraVoucherWithdrawal"
				id="contraVoucherWithdrawal" modelAttribute="contraVoucherBean">

				<form:hidden path="coTypeFlag" id="coTypeFlagWth" value="W" />
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.budgetadditionalsupplemental.transactionnumber"
							text="Transaction Number" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="coVouchernumber" disabled="true"
							class="form-control"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="budget.reappropriation.master.transactiondate"
							text="Transaction Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input id="coEntryDateWth" path="coDateStr"
								cssClass="datepicker form-control" disabled="${viewMode}" />
							<label class="input-group-addon" for="transactionDateWth"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="transactionDateWth"> </label>
						</div>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.contra.voucher.payment.bank" text="Payment Bank" /></label>
					<div class="col-sm-4">

						<c:set value="${contraVoucherBean.baAccountidPay}"
							var="bankAcPayKey" />
						<c:forEach items="${bankAccountMap}" varStatus="status"
							var="bankItemWth">
							<c:if test="${bankAcPayKey eq bankItemWth.key }">
								<form:input type="text" value="${bankItemWth.value}"
									class="form-control" id="baAccountidPayWth" path=""
									disabled="${viewMode}"></form:input>
							</c:if>
						</c:forEach>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="account.contra.voucher.transaction.mode"
							text="Transaction Mode" /></label>
					<div class="col-sm-4">

						<form:input id="paymentModeDescWth" path="paymentModeDesc"
							disabled="${viewMode}" cssClass="form-control" />
					</div>

				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.contra.voucher.instru.number"
							text="Instrument Number" /></label>
					<div class="col-sm-4">

						<c:set value="${contraVoucherBean.chequebookDetid}"
							var="instrumentIdWth" />
						<c:forEach items="${chequeNoMap}" varStatus="status"
							var="chequeNoWth">
							<c:if test="${instrumentIdWth eq chequeNoWth.key }">
								<form:input type="text" value="${chequeNoWth.value}"
									class="form-control" id="chequebookDetidWth" path=""
									disabled="${viewMode}"></form:input>
							</c:if>
						</c:forEach>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="accounts.receipt.cheque_dd_date" text="Instrument Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" value="" class="datepicker form-control"
								id="coChequedateWth" disabled="${viewMode}"
								path="coChequedateStr"></form:input>
							<label class="input-group-addon" for="coChequedateWth"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="instrumentDateWth"> </label>
						</div>
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.contra.voucher.withdraw.amt"
							text="Withdrawal Amount" /></label>
					<div class="col-sm-4">
						<form:hidden path="bankBalance" id="bankBalanceWth" />
						<form:input type="text" value="" class="form-control text-right"
							id="amountPayStr"
							onkeypress="return hasAmount(event, this, 13, 2)"
							disabled="${viewMode}" path="amountPayStr"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.vendormaster.payTo" text="Pay To" /></label>
					<div class="col-sm-4">
						<c:if test="${mode eq 'create'}">
							<form:input type="text" value=""
								class="form-control mandColorClass" id="payToWth" path="payTo"
								disabled="${viewMode}"></form:input>
						</c:if>
						<c:if test="${mode eq 'update'}">
							<form:input type="text" class="form-control" id="payToWthUpd"
								path="payTo" disabled="${viewMode}"></form:input>
						</c:if>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.common.desc" text="Description" /></label>
					<div class="col-sm-10">
						<form:textarea class="form-control" id="coRemarkPayWth"
							path="coRemarkPay" disabled="${viewMode}"></form:textarea>
					</div>
				</div>
				<div class="text-center padding-top-20">
					<c:if test="${!viewMode}">
						<button type="button" onclick="saveContraEntry(this,'W')"
							class="btn btn-success btn-submit">
							<spring:message code="account.bankmaster.save" text="Save" />
						</button>
						<button type="button" class="btn btn-warning"
							onclick="clearWithdrawalForm()">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger"
						name="button-1496152380275" style=""
						onclick="window.location.reload()" id="backBtn">
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
		<!--################################### Cash Deposit Entry form ###################################-->
		<div id="cashDepositPanel">
			<form:form method="POST" action="ContraVoucherEntry.html"
				class="form-horizontal" name="contraVoucherDeposit"
				id="contraVoucherDeposit" modelAttribute="contraVoucherBean">

				<form:hidden path="coTypeFlag" id="coTypeFlagDep" value="D" />

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.budgetadditionalsupplemental.transactionnumber"
							text="Transaction Number" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="coVouchernumber" disabled="true"
							class="form-control"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="budget.reappropriation.master.transactiondate"
							text="Transaction Date" /></label>
					<div class="col-sm-4">
						<form:input id="coEntryDateDeposit" path="coDateStr"
							disabled="${viewMode}" cssClass="datepicker cal form-control" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.bankfortds.ptbbankname" text="Bank Name" /></label>
					<div class="col-sm-4">

						<c:set value="${contraVoucherBean.baAccountidRec}"
							var="bankAcRecKey" />
						<c:forEach items="${bankAccountMap}" varStatus="status"
							var="bankItemDep">
							<c:if test="${bankAcRecKey eq bankItemDep.key }">
								<form:input type="text" value="${bankItemDep.value}"
									class="form-control" path="" disabled="${viewMode}"
									id="baAccountidRecDeposit"></form:input>
							</c:if>
						</c:forEach>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="" text="Deposit Amount" /></label>
					<div class="col-sm-4">
						<form:input type="text" value=""
							cssClass="form-control text-right" id="coAmountRecDeposit"
							path="amountRecStr" disabled="${viewMode}"
							onkeypress="return hasAmount(event, this, 13, 2)"></form:input>
					</div>
				</div>

				<form:hidden path="payTo" id="payToDeposit" />
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.common.desc" text="Description" /></label>
					<div class="col-sm-10">
						<form:textarea class="form-control" id="coRemarkRecDeposit"
							path="coRemarkRec" disabled="${viewMode}"></form:textarea>
					</div>
				</div>
				<div class="table-responsive">
					<table border="0" cellspacing="0" cellpadding="0"
						class="table table-bordered table-condensed">
						<tr>
							<th scope="col" width="15%"><spring:message
									code="account.cheque.dishonour.cash.detail"
									text="Details of Cash" /></th>
							<th scope="col" width="20%"><spring:message
									code="account.cheque.dishonour.number.count"
									text="Number of Count" /></th>
							<th scope="col" width="40%" class="text-right"><spring:message
									code="" text="Denomination Amount" /></th>
						</tr>


						<c:forEach items="${denLookupList}" var="denLookupVar"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class=denomClass>
								<td><form:hidden id="thousandId"
										path="cashDep[${count}].tbComparamDet" /> <form:input
										cssClass="hasNumber form-control" id="denomDesc${count}"
										path="cashDep[${count}].denomDesc" value="" disabled="true"></form:input></td>
								<td><form:input cssClass="hasNumber form-control"
										id="denomination${count}"
										path="cashDep[${count}].denomination"
										value="${denLookupVar.lookUpExtraLongOne}"
										onkeyup="getAmount(${count})" disabled="${viewMode}"></form:input></td>
								<td><form:input cssClass="form-control text-right"
										id="amount${count}" value="0" path=""
										onkeypress="return hasAmount(event, this, 13, 2)"
										disabled="true"></form:input></td>
							</tr>
						</c:forEach>
						<tr>
							<th><spring:message code=""
									text="Total Denomination Amount" /></th>
							<td><fmt:formatNumber value="134000" type="currency"
									var="totalTest" pattern="#,##,##,##,###.00" /> <form:input
									cssClass="form-control text-right"
									onkeypress="return hasAmount(event, this, 13, 2)" path="total"
									disabled="${viewMode}"></form:input></td>
							<th>&nbsp;</th>
						</tr>
					</table>
				</div>
				<div class="text-center padding-top-20">
					<c:if test="${!viewMode}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveContraEntry(this,'D')">
							<spring:message code="account.bankmaster.save" text="Save" />
						</button>
						<button type="button" class="btn btn-warning"
							onclick="clearDepositForm()">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
					</c:if>
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='ContraVoucherEntry.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
				</div>
			</form:form>
		</div>
	</div>

</div>

