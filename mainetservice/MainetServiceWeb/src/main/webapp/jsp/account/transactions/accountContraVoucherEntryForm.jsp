<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/accountContraVoucherEntry.js"
	type="text/javascript"></script>
<script>
$(function() {
	
	$("#cashDepositPanel").hide();
	$("#cashWithDrawPanel").hide();
	$("input[name=contraType][value=" + "T" + "]").attr('checked', 'checked');
	var mode = $("#editMode").val();
	if(mode !="edit"){
		 $("#transactionDateId").keyup(function(e){
			    if (e.keyCode != 8){    
			        if ($(this).val().length == 2){
			            $(this).val($(this).val() + "/");
			        }else if ($(this).val().length == 5){
			            $(this).val($(this).val() + "/");
			        }
			     }
			    $('#coChequedate').val($('#transactionDateId').val());
			    $('#transactionDateHidden').val($('#transactionDateId').val());
			    });
		 
		 $("#coChequedate").keyup(function(e){
			    if (e.keyCode != 8){    
			        if ($(this).val().length == 2){
			            $(this).val($(this).val() + "/");
			        }else if ($(this).val().length == 5){
			            $(this).val($(this).val() + "/");
			        }
			     }
			    });
		 $(".datepicker").datepicker({
			    dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true,
				maxDate: '0'
				
			});
		 $(".datepicker").keyup(function(e){
			    if (e.keyCode != 8){    
			        if ($(this).val().length == 2){
			            $(this).val($(this).val() + "/");
			        }else if ($(this).val().length == 5){
			            $(this).val($(this).val() + "/");
			        }
			     }
			    });
				$(".datepicker").datepicker('setDate', new Date()); 
		// find SLI date from SLI Prefix
		var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var date = new Date();
		var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());			
		 $("#transactionDateId").datepicker({
			    dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true,
				minDate : disableBeforeDate,
				maxDate: today,
				onSelect: function(date) {
					$('#transactionDateHidden').val(date);
					$('#coChequedate').val(date);
				}
			});		 
	}

	if(mode =="edit"){
		
		var bankAcTrn = $("#baAccountidRec option:selected").text();
		$('#payToTransUpd').val(bankAcTrn);

		var bankAcWth = $("#baAccountidPay option:selected").text();
		$('#payToWthUpd').val(bankAcWth);
	}
	
	
	$('#baAccountidPay').chosen().trigger("chosen:updated");
	$('#baAccountidRec').chosen().trigger("chosen:updated");
	
	
	
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

	 $("#transactionDateWithDrawId").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    $('#coChequedateWth').val($('#transactionDateWithDrawId').val());
		    $('#transactionDateHidden').val($('#transactionDateWithDrawId').val());
		    });
	 $("#transactionDateDepositId").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    $('#transactionDateHidden').val($('#transactionDateDepositId').val());
		    });
	
	
});



$( document ).ready(function() {
	
	
});





</script>

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="account.contra" text="Contra" />
			<strong><spring:message code="receipt.payment.transaction"
					text="Transactions" /></strong>
		</h2>
		<apptags:helpDoc url="ContraVoucherEntry.html" helpDocRefURL="ContraVoucherEntry.html"></apptags:helpDoc>		
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
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="successfulFlag" id="successfulFlag" />
			<form:hidden path="templateExistFlag" id="templateExistFlagTrans" />
			<form:hidden path="coTypeFlag" id="coTypeFlagTrns" value="T" />
			<form:hidden path="transactionDate" id="transactionDateHidden"
				value="" />

			<c:if test="${mode eq 'create'}">
				<div class="form-group" id="contraMode">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="account.contra.voucher.transaction.type"
							text="Transaction Types" /></label>
					<div class="col-sm-4">
						<label class="radio-inline"><form:radiobutton path=""
								name="contraType" cssClass="set-radio" value="T" />  <spring:message
								code="account.transfer" text="Transfer" /></label> 
								
					</div>
				</div>
			</c:if>
			<c:if test="${mode eq 'update'}">
				<div class="form-group" id="contraMode">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="account.contra.voucher.transaction.type"
							text="Transaction Types" /></label>
					<div class="col-sm-4">
						<label class="radio-inline"><form:radiobutton
								path="coTypeFlag" id="transfer" name="coTypeFlag" value="T"
								cssClass="set-radio" /> <spring:message code="" text="Transfer" /></label>
						<%-- <label class="radio-inline"><form:radiobutton
								path="coTypeFlag" id="withdraw" name="coTypeFlag" value="W"
								cssClass="set-radio" /> <spring:message code="" text="Withdraw" /></label>
						<label class="radio-inline"><form:radiobutton
								path="coTypeFlag" id="deposit" name="coTypeFlag" value="D"
								cssClass="set-radio" /> <spring:message code="" text="Deposit" /></label> --%>
					</div>
				</div>
			</c:if>


			<div id="transferPanel">


				<c:if test="${mode eq 'update'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="account.budgetadditionalsupplemental.transactionnumber"
								text="Transaction Number" /></label>
						<div class="col-sm-4">
							<form:input type="text" value="" path="coVouchernumber"
								disabled="true" class="form-control" />
						</div>
						<label for="coEntryDate"
							class="col-sm-2 control-label required-control"><spring:message
								code="budget.reappropriation.master.transactiondate"
								text="Transaction Date" /></label>
						<div class="col-sm-4">
							<form:input id="coEntryDate" path="coDateStr"
								disabled="${viewMode}" cssClass="datepicker cal form-control" />
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label for="cpdModePay"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.receipt.payment_mode" text="Mode" /></label>
					<div class="col-sm-3">
						<form:select id="cpdModePay" path="cpdModePay"
							cssClass="form-control" disabled="${viewMode}"
							onchange="disableInstruments()" data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${payModeMap}" varStatus="status" var="payMode">
								<form:option value="${payMode.key}">${payMode.value}</form:option>
							</c:forEach>
						</form:select>
					</div>


					<label for="transactionDateId"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.transaction.date"
							text="Transaction Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="transactionDateId"
								cssClass="mandColorClass form-control" data-rule-required="true" maxlength="10" />
							<label class="input-group-addon mandColorClass"
								for="transactionDateId"><i class="fa fa-calendar"></i> </label>
						</div>
					</div>

				</div>


				<h4>
					<spring:message code="account.contra.voucher.payment.detail"
						text="Payment Details" />
				</h4>
				<div class="table-overflow-sm" id="contraTransferPayTableDiv">
					<table id="contraTransferPayTable"
						class="table table-bordered table-striped">
						<thead>
							<tr>
								<th scope="col" width="50%" id="thBankAcc"><spring:message
										code="account.fund.bank.acc" text="Bank Account" /><span class="mand float-right">*</span></th>
								<th scope="col" width="16%" id="thIntNo"><spring:message
										code="payment.entry.service.bill.intrument.no" text="Instrument No" /><span class="mand float-right">*</span></th>
								<th scope="col" width="16%" id="thIntDate"><spring:message
										code="account.instrument.date" text="Instrument Date" /><span
									class="mand float-right">*</span></th>
								<th scope="col" width="16%" id="thAmount"><spring:message
										code="account.amount" text="Amount" /><span class="mand float-right">*</span></th>
								<%--  <th scope="col">
									<a title="Add" class="btn btn-blue-2 btn-sm addClass" onclick="addRow('contraTransferPayTable');">
										<i class="fa fa-plus"></i>
									</a>
								</th>--%>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td><form:select id="baAccountidPay"
										aria-labelledby="thBankAcc" path="baAccountidPay"
										cssClass="form-control mandColorClass chosen-select-no-results"
										disabled="${viewMode}" onchange="getChequeNos()"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="account.common.select" />
										</form:option>
										<c:forEach items="${bankAccountMap}" varStatus="status"
											var="bankItem">
											<form:option value="${bankItem.key}">${bankItem.value}</form:option>
										</c:forEach>
									</form:select></td>
								<td><c:if test="${mode eq 'create'}">

										<form:select
											class="form-control mandColorClass chosen-select-no-results"
											aria-labelledby="thIntNo" id="chequebookDetid"
											path="chequebookDetid" disabled="${viewMode}"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="account.common.select" />
											</form:option>
										</form:select>

									</c:if> <c:if test="${mode eq 'update'}">

										<form:select class="form-control" aria-labelledby="thIntNo"
											id="chequebookDetid" path="chequebookDetid"
											disabled="${viewMode}">
											<form:option value="">
												<spring:message code="account.common.select" />
											</form:option>
											<c:forEach items="${chequeNoMap}" varStatus="status"
												var="chequeNo">
												<form:option value="${chequeNo.key}">${chequeNo.value}</form:option>
											</c:forEach>
										</form:select>

									</c:if></td>
								<td>
									<div class="input-group">
										<form:input type="text" value="" aria-labelledby="thIntDate"
											class="form-control text-left" id="coChequedate"
											path="coChequedateStr" onchange="validateChequeDate()" readonly="true" 
											data-rule-required="true" maxlength="10" ></form:input>
										<label class="input-group-addon" for="coChequedate"><i
											class="fa fa-calendar"></i> <input type="hidden"
											id="instrumentDate"> </label>
									</div>
								</td>
								<td><form:hidden path="bankBalance" id="bankBalance" /> <form:hidden
										path="payee" id="payeeId" /> <form:input type="text"
										aria-labelledby="thAmount" id="coAmountPayTrns"
										cssClass="form-control mandColorClass text-right amount"
										onblur="setReceiptAmount()" path="coAmountPay"
										onkeypress="return hasAmount(event, this, 13, 2)"
										disabled="${viewMode}" data-rule-required="true"></form:input>
								</td>
						<%--		<td class="text-center">
									<a title="Delete" href="#" class="btn btn-danger btn-sm deletClass" id="deleteAccount0" onclick="removeRow(0,'contraTransferPayTableDiv')"><i class="fa fa-trash"></i></a>
								</td>  --%>
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
						<thead>
							<tr>
								<th scope="col" width="50%" id="thBankAccount"><spring:message
										code="account.fund.bank.acc" text="Bank Account" /><span class="mand float-right">*</span></th>
								<th scope="col" width="34%" id="thPayTo"><spring:message
										code="accounts.vendormaster.payTo" text="Pay To" /><span class="float-right"></span></th>
								<th scope="col" width="16%" id="thAmountRec"><spring:message
										code="account.amount" text="Amount" /><span class="float-right"></span></th>
								<%-- <th scope="col">
									<a title="Add" class="btn btn-blue-2 btn-sm addClass" onclick="addRow('contraTransferRecptTableDiv');">
										<i class="fa fa-plus"></i>
									</a>
								</th> --%>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td><form:select id="baAccountidRec"
										aria-labelledby="thBankAccount" path="baAccountidRec"
										cssClass="form-control chosen-select-no-results"
										disabled="${viewMode}" onchange="validateRecptBankAc()"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="account.common.select" />
										</form:option>
										<c:forEach items="${bankAccountMap}" varStatus="status"
											var="bankItem">
											<form:option value="${bankItem.key}">${bankItem.value}</form:option>
										</c:forEach>
									</form:select></td>

								<td><c:if test="${mode eq 'create'}">
										<form:input type="text" value="" aria-labelledby="thPayTo"
											class="form-control" id="payToTrans" path="payTo"
											disabled="${viewMode}"></form:input>
									</c:if> <c:if test="${mode eq 'update'}">
										<form:input type="text" value="" aria-labelledby="thPayTo"
											class="form-control" id="payToTransUpd" path="payTo"
											disabled="${viewMode}"></form:input>
									</c:if></td>
								<td><form:hidden path="coAmountRec"
										id="coAmountRecTrnsHidden" /> <form:input type="text"
										aria-labelledby="thAmountRec" id="coAmountRecTrns"
										cssClass="form-control text-right" path="coAmountRecDesc"
										disabled="true"
										onkeypress="return hasAmount(event, this, 13, 2)"></form:input>
								</td>
								<%-- <td class="text-center">
									<a title="Delete" href="#" class="btn btn-danger btn-sm deletClass" id="deleteAccountpay0" onclick="removeRow(0,'contraTransferRecptTableDiv')"><i class="fa fa-trash"></i></a>
								</td>--%>
								</td>
							</tr>
						</tbody>
					</table>
				</div>


				&nbsp;
				<div class="form-group">
					<label for="coRemarkPay"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.common.desc" text="Description" /></label>
					<div class="col-sm-10">
						<form:textarea class="form-control" id="coRemarkPay"
							path="coRemarkPay" disabled="${viewMode}"
							data-rule-required="true"></form:textarea>
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
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='ContraVoucherEntry.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
				</div>
			</div>
		</form:form>

		<!--################################### Cash Withdrwal Entry form ###################################-->
		<div id="cashWithDrawPanel">
			<form:form method="POST" action="ContraVoucherEntry.html"
				class="form-horizontal" name="contraVoucherWithdrawal"
				id="contraVoucherWithdrawal" modelAttribute="contraVoucherBean">



				<form:hidden path="coTypeFlag" id="coTypeFlagWth" value="W" />
				<form:hidden path="templateExistFlag" id="templateExistFlagWth" />
				<c:if test="${mode eq 'update'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="account.budgetadditionalsupplemental.transactionnumber"
								text="Transaction Number" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="coVouchernumber" disabled="true"
								class="form-control"></form:input>
						</div>
						<label for="coEntryDateWth"
							class="col-sm-2 control-label required-control"><spring:message
								code="budget.reappropriation.master.transactiondate"
								text="Transaction Date" /></label>
						<div class="col-sm-4">
							<form:input id="coEntryDateWth" path="coDateStr"
								cssClass="datepicker cal form-control" disabled="${viewMode}" />
						</div>
					</div>
				</c:if>

				<div class="form-group">
					<label for="transactionDateWithDrawId"
						class="col-sm-2 control-label required-control"><spring:message
							code="budget.reappropriation.master.transactiondate"
							text="Transaction Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="transactionDateWithDrawId"
								cssClass="mandColorClass form-control" data-rule-required="true"  maxlength="10"/>
							<label class="input-group-addon mandColorClass"
								for="transactionDateWithDrawId"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="account.contra.voucher.payment.bank" text="Payment Bank" /></label>
					<div class="col-sm-4">
						<form:select id="baAccountidPayWth" path="baAccountidPay"
							cssClass="form-control chosen-select-no-results"
							disabled="${viewMode}" onchange="getPayTo()"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${bankAccountMap}" varStatus="status"
								var="bankItem">
								<form:option value="${bankItem.key}">${bankItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="account.contra.voucher.transaction.mode"
							text="Transaction Mode" /></label>
					<div class="col-sm-4">
						<form:select id="cpdModePayWth" path="cpdModePay"
							cssClass="form-control" disabled="true"
							onchange="getChequeNosCashWth()">
							<c:forEach items="${payMapChequeWith}" varStatus="status"
								var="payMode">
								<form:option value="${payMode.key}">${payMode.value}</form:option>
								<form:hidden id="cpdModePayWthHidden" path="cpdModePay"
									value="${payMode.key}" />
							</c:forEach>
						</form:select>
					</div>

				</div>




				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="account.contra.voucher.instru.number"
							text="Instrument Number" /></label>
					<c:if test="${mode eq 'create'}">
						<div class="col-sm-4">
							<form:select class="form-control chosen-select-no-results"
								id="chequebookDetidWth" path="chequebookDetid"
								disabled="${viewMode}" data-rule-required="true">
								<option value=""><spring:message code="account.select"
										text="Select" /></option>
							</form:select>
						</div>
					</c:if>
					<c:if test="${mode eq 'update'}">
						<div class="col-sm-4">
							<form:select class="form-control" id="chequebookDetidWth"
								path="chequebookDetid" disabled="${viewMode}">
								<option value=""><spring:message code="account.select"
										text="Select" /></option>
								<c:forEach items="${chequeNoMap}" varStatus="status"
									var="chequeNo">
									<form:option value="${chequeNo.key}">${chequeNo.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="accounts.receipt.cheque_dd_date" text="Instrument Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" value="" class="form-control text-left"
								id="coChequedateWth" readonly="true"
								path="coChequedateStr" onchange="validateChequeDateWith()"  data-rule-required="true" maxlength="10"></form:input>
							<label class="input-group-addon" for="coChequedateWth"><i
								class="fa fa-calendar"></i><span class="hide">icon</span> <input
								type="hidden" id="instrumentDateWth"> </label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="account.contra.voucher.withdraw.amt"
							text="Withdrawal Amount" /></label>
					<div class="col-sm-4">
						<form:hidden path="bankBalance" id="bankBalanceWth" />
						<form:input type="text" value=""
							class="form-control mandColorClass text-right amount"
							id="coAmountPayWth" onblur="checkBalanceForWithdraw()"
							onkeypress="return hasAmount(event, this, 13, 2)"
							disabled="${viewMode}" path="coAmountPay"
							data-rule-required="true"></form:input>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="accounts.vendormaster.payTo" text="Pay To" /></label>
					<div class="col-sm-4">
						<c:if test="${mode eq 'create'}">
							<form:input type="text" value=""
								class="form-control mandColorClass" id="payToWth" path="payTo"
								disabled="${viewMode}" data-rule-required="true"></form:input>
						</c:if>
						<c:if test="${mode eq 'update'}">
							<form:input type="text" value=""
								class="form-control mandColorClass" id="payToWthUpd"
								path="payTo" disabled="${viewMode}"></form:input>
						</c:if>
					</div>
				</div>

				<div class="form-group">
					<label for="coRemarkPayWth"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.common.desc" text="Description" /></label>
					<div class="col-sm-10">
						<form:textarea class="form-control" id="coRemarkPayWth"
							path="coRemarkPay" disabled="${viewMode}"
							data-rule-required="true"></form:textarea>
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
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='ContraVoucherEntry.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
					
				</div>

			</form:form>
		</div>
		<!--################################### Cash Deposit Entry form ###################################-->
		<div id="cashDepositPanel">
			<form:form method="POST" action="ContraVoucherEntry.html"
				class="form-horizontal" name="contraVoucherDeposit"
				id="contraVoucherDeposit" modelAttribute="contraVoucherBean">

				<form:hidden path="coTypeFlag" id="coTypeFlagDep" value="D" />
				<form:hidden path="templateExistFlag" id="templateExistFlagDep" />
				<c:if test="${mode eq 'update'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="account.budgetadditionalsupplemental.transactionnumber"
								text="Transaction Number" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="coVouchernumber" disabled="true"
								class="form-control"></form:input>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message
								code="budget.reappropriation.master.transactiondate"
								text="Transaction Date" /></label>
						<div class="col-sm-4">
							<form:input id="coEntryDateDeposit" path="coDateStr"
								disabled="${viewMode}" cssClass="datepicker cal form-control" />
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label for="transactionDateDepositId"
						class="col-sm-2 control-label required-control"><spring:message
							code="budget.reappropriation.master.transactiondate"
							text="Transaction Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="transactionDateDepositId"
								cssClass="mandColorClass form-control" data-rule-required="true" maxlength="10" />
							<label class="input-group-addon mandColorClass"
								for="transactionDateDepositId"><i class="fa fa-calendar"></i>
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="accounts.bankfortds.ptbbankname" text="Bank Name" /></label>
					<div class="col-sm-4">
						<form:select id="baAccountidRecDeposit" path="baAccountidRec"
							cssClass="form-control chosen-select-no-results"
							disabled="${viewMode}" onchange="getPayToDeposit()"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${bankAccountMap}" varStatus="status"
								var="bankItem">
								<form:option value="${bankItem.key}">${bankItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Deposit Amount" /></label>
					<div class="col-sm-4">
						<form:input type="text" value=""
							cssClass="form-control text-right amount" id="coAmountRecDeposit"
							path="coAmountRec" disabled="${viewMode}"
							onkeypress="return hasAmount(event, this, 13, 2)"
							data-rule-required="true" onchange="checkPettyCashBalanceExists();getAmountFormatInDynamic((this),'coAmountRecDeposit')"></form:input>
					</div>
				</div>

				<form:hidden path="payTo" id="payToDeposit" />
				<div class="form-group">
					<label for="coRemarkRecDeposit"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.common.desc" text="Description" /></label>
					<div class="col-sm-10">
						<form:textarea cssClass="form-control" id="coRemarkRecDeposit"
							path="coRemarkRec" disabled="${viewMode}"
							data-rule-required="true"></form:textarea>
					</div>
				</div>
				<c:if test="${mode eq 'create'}">
					<div class="table-responsive">
						<table 
							class="table table-bordered table-condensed">
							<tr>
								<th scope="col" width="15%" id="cashDetails"><spring:message
										code="account.cheque.dishonour.cash.detail"
										text="Details of Cash" /></th>
								<th scope="col" width="20%" id="count"><spring:message
										code="account.cheque.dishonour.number.count"
										text="Number of Count" /><span class="mand float-right">*</span></th>
								<th scope="col" width="40%" class="text-right" id="depAmount"><spring:message
										code="" text="Denomination Amount" /></th>
							</tr>
							<c:forEach items="${denLookupList}" var="denLookupVar"
								varStatus="status">
								<c:set value="${status.index}" var="count"></c:set>
								<tr class="denomClass">
									<td><form:hidden id="thousandId"
											path="cashDep[${count}].tbComparamDet" /> <form:input
											cssClass="hasNumber form-control"
											aria-labelledby="cashDetails" id="denomDesc${count}"
											path="cashDep[${count}].denomDesc" disabled="true"></form:input></td>
									<td><form:input cssClass="hasNumber form-control"
											aria-labelledby="count" id="denomination${count}"
											path="cashDep[${count}].denomination"
											onkeyup="getAmount(${count})"  onchange = "getAmountFormatInDynamic((amount${count}),'amount${count}')" disabled="${viewMode}" ></form:input></td>
									<td><form:input cssClass="form-control text-right"
											aria-labelledby="depAmount" id="amount${count}" value="0"
											path="" onkeypress="return hasAmount(event, this, 13, 2)" 
											disabled="true" ></form:input></td>
								</tr>
							</c:forEach>

							<tr>
								<th id="thTotal"><spring:message
										code="" text="Total Denomination Amount" /></th>
								<td><form:input id="total" aria-labelledby="thTotal"
										cssClass="form-control"
										onkeypress="return hasAmount(event, this, 13, 2)" path="total"
										disabled="${viewMode}" ></form:input></td>
								<th id="thSpace">&nbsp;</th>
							</tr>
						</table>
					</div>
				</c:if>
				<c:if test="${mode eq 'update'}">
					<div class="table-responsive">
						<table 
							class="table table-bordered table-condensed">
							<tr>
								<th scope="col" width="15%"><spring:message
										code="account.cheque.dishonour.cash.detail"
										text="Details of Cash" /></th>
								<th scope="col" width="20%"><spring:message
										code="account.cheque.dishonour.number.count"
										text="Number of Count" /><span class="mand float-right">*</span></th>
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
								<th><spring:message code="account.voucher.total"
										text="Total Denomination Amount" /></th>
								<th><form:input id="total" cssClass="form-control"
										onkeypress="return hasAmount(event, this, 13, 2)" path="total"
										disabled="${viewMode}"></form:input></th>
								<th>&nbsp;</th>
							</tr>
						</table>
					</div>
				</c:if>
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
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
					
				</div>

			</form:form>
		</div>
	</div>

</div>

