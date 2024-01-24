<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		 $(".datepicker").datepicker({
			    dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true,
				maxDate: '0'
				
			});
			$(".datepicker").datepicker('setDate', new Date()); 
	}

	if(mode =="edit"){
		
		var bankAcTrn = $("#baAccountidRec option:selected").text();
		$('#payToTransUpd').val(bankAcTrn);

		var bankAcWth = $("#baAccountidPay option:selected").text();
		$('#payToWthUpd').val(bankAcWth);
	}
	
	
	$('#baAccountidPay').chosen().trigger("chosen:updated");
	$('#baAccountidRec').chosen().trigger("chosen:updated");
	
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
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>${contraVoucherBean.voucherType}</h2>
		</div>
		<div class="widget-content padding">
			<form:form method="POST" action="ContraVoucherEntry.html"
				class="form-horizontal" name="contraVoucherTransfer"
				id="contraVoucherTransfer" modelAttribute="contraVoucherBean">
				<div id="receipt">
					<div class="form-group">
						<div class="col-xs-12 text-center"><h3 class="text-extra-large margin-bottom-0 margin-top-0">
						
					<c:choose>
							<c:when
							test="${userSession.getCurrent().getLanguageId() eq 1}">
							${contraVoucherBean.organizationName}
							</c:when>
						<c:otherwise>
							${contraVoucherBean.organizationNameReg}
						</c:otherwise>
					</c:choose>
					
					
					</h3>
					
						${contraVoucherBean.voucherType}
					</div>
					</div>
					<table class="table table-bordered table-condensed">
						<tr>
						
							<th style="text-align: left"><spring:message code="account.voucher.number"
									text="Voucher No." /></th>
							<td>${contraVoucherBean.coVouchernumber}</td>
							<th style="text-align: left"><spring:message code="account.voucher.date"
									text="Voucher Date" /></th>
							<td>${contraVoucherBean.coEntryDateStr}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message
									code="voucher.template.entry.master.vouchertype"
									text="Voucher Type" /></th>
							<td>${contraVoucherBean.voucherType}</td>
							<th style="text-align: left"><spring:message code="account.contra.voucher.subtype"
									text="Voucher Sub type" /></th>
							<td>${contraVoucherBean.vouchersubType}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message
									code="voucher.template.entry.master.department"
									text="Department" /></th>
							<td colspan="3">${contraVoucherBean.departmentDesc}</td>
						</tr>
						<tr>
							<th style="text-align: left"  colspan="2"><spring:message code="classified.abstact.acc.code"
									text="Account Code" /></th>
							<th><spring:message code="account.voucher.amount.debit"
									text="Amount Debit(Rs.)" /></th>
							<th><spring:message code="account.voucher.amount.credit"
									text="Amount Credit(Rs.)" /></th>
						</tr>

						<c:forEach items="${contraVoucherBean.oacBeanList}"
							var="ContraList" varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>

							<tr>
								<td  colspan="2">${ContraList.budgetCodeId}<br> <br>
									${ContraList.budgetCode}
								</td>							
								<td class="text-right">${ContraList.amountPaymentDebit}<br>
									<br> ${ContraList.amountPaymentCredit}
								</td>
								<td class="text-right">${ContraList.amountRecieptDebit}<br>
									<br> ${ContraList.amountRecieptCredit}
								</td>

							</tr>

						</c:forEach>
						<tr>
							<th colspan="2" class="text-right"><spring:message
									code="account.voucher.total" text="Total" /></th>
							<th class="text-right">${contraVoucherBean.totalAmount}</th>
							<th class="text-right">${contraVoucherBean.totalAmount}</th>
						</tr>
						
						<tr>
							<th style="text-align: left"><spring:message code="accounts.receipt.narration"
									text="Narration" /></th>
							<td colspan="3">${contraVoucherBean.narration}</td>
						</tr>
						<tr>
							<td colspan="4"
								class="height-155 text-right vertical-align-bottom"><p>...................................................................................</p>
								<p>
									<spring:message code="account.voucher.auth.offc.sign"
										text="Signature of Authorised Officer" />
								</p>
								<p>
									<spring:message code="account.voucher.auth.offc.name"
										text="(Name / Designation of the Authorised Officer)" />
								</p></td>
						</tr>
					</table>
					<div class="text-center hidden-print padding-10">
						<button onclick="printdiv('receipt');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
						</button>
						<input type="button" class="btn btn-danger"
							onclick="javascript:openRelatedForm('ContraVoucherEntry.html');"
							value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<!--Scroll To Top-->
<a class="tothetop" href="javascript:;"><i class="fa fa-angle-up"></i></a>
<!-- End content here -->
