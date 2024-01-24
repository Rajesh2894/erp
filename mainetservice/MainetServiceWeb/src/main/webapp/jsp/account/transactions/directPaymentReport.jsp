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
<script src="js/account/transaction/paymentEntry.js"
	type="text/javascript"></script>

<script>
$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	maxDate: '0',
});
$(".datepicker").datepicker('setDate', new Date()); 

function printdiv(printpage) {
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}
</script>

<!-- Start Content here -->
<div class="widget">
	<div class="widget-header">
		<h2>${oPaymentReportDto.voucherType}</h2>
	</div>
	<div class="widget-content padding">
		<form:form method="POST" action="DirectPaymentEntry.html"
			cssClass="form-horizontal" name="paymentEntry" id="paymentEntry"
			modelAttribute="oPaymentReportDto">
			<div id="receipt">
				<div class="form-group">
				<div class="col-xs-12 text-right">
				<p>Form GEN-5</p>
				</div>
					<div class="col-xs-12 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">${oPaymentReportDto.organisationName}</h3>
						 ${oPaymentReportDto.voucherType}
					</div>
					
					<div class="col-xs-12">
					<p><strong>Name of the Bank</strong>: ${oPaymentReportDto.bankName}</p>
					</div>
					<div class="col-xs-9">
					<p><strong>Date</strong>: ${oPaymentReportDto.payDate}</p>
					</div>
					<div class="col-xs-3">
					<p><strong>Name of the fund</strong>: ${oPaymentReportDto.nameOfTheFund}</p>
					</div>
					<div class="col-xs-9">
					<p><strong>Department</strong>: ${oPaymentReportDto.depatmentDesc}</p>
					</div>
					<div class="col-xs-3">
					<p><strong>CPV/BPV No.</strong>: ${oPaymentReportDto.bankNumber}</p>
					</div>
					<div class="col-xs-9">
					<p><strong>Name of Claimant</strong>: ${oPaymentReportDto.vendorCodeDescription}</p>
					</div>
					<div class="col-xs-3">
					<p><strong>P.O / W.O No.</strong>: </p>
					</div>
					
				</div>
				<table class="table table-bordered table-condensed">
				<thead>
				<tr>
				<th colspan="2">Budget</th>
				<th rowspan="2">Account Head</th>
				<!-- <th rowspan="2">Account Description</th> -->
				<th rowspan="2">Payment Order No.</th>
				<th rowspan="2">Cheque No.</th>
				<th rowspan="2">Amount (Rs.)</th>
				</tr>
				
				<tr>
				<th>Function</th>
				<th>Functionary</th>
				</tr>
				
				<tr>
				<th>1</th>
				<th>2</th>
				<!-- <th>3</th> -->
				<th>3</th>
				<th>4</th>
				<th>5</th>
				<th>6</th>
				</tr>
				</thead>
				
				<tbody>
				<c:forEach items="${oPaymentReportDto.paymentDetailsDto}"
						var="directPaymentList" varStatus="status">
				<tr>
				<td>${directPaymentList.functionDesc}</td>
				<td>${directPaymentList.functionaryDesc}</td>
				<td>${directPaymentList.accountCode}</td>
				<!-- <td>&nbsp;</td> -->
				<td>${oPaymentReportDto.voucherNo}</td>
				<td>${oPaymentReportDto.chequeNo}</td>
				<td style="text-align: right">${directPaymentList.paymentAmountDesc}</td>
				</tr>
				</c:forEach>
				
				<tr>
				<td colspan="2"><b>Total (in Words)</b></td>
				<td>${oPaymentReportDto.amountInWords}</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<!-- <td>&nbsp;</td> -->
				</tr>
				
				<tr>
				<td colspan="2"><b>Prepared by:</b> ${oPaymentReportDto.preparedBy}</td>
				<td ><b>Verified by:</b> </td>
				<td colspan="2"><b>Approved by:</b> </td>
				<td><b>Posted by:</b> </td>
				</tr>
				
				<tr>
				<td colspan="2"><b>Date:</b> ${oPaymentReportDto.preparedDate}</td>
				<td ><b>Date:</b></td>
				<td colspan="2"><b>Date:</b></td>
				<td><b>Date:</b></td>
				</tr>
				
				<tr>
				<td colspan="3" class="text-right"><b>Received Payment</b></td>
				<td colspan="3" class="text-right">${oPaymentReportDto.voucherAmount}</td>
				</tr>
				<tr>
						<td colspan="7"
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
				</tbody>
				</table>
				<div class="text-center hidden-print padding-10">
					<button onclick="printdiv('receipt');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
				  </button>
						
					<input type="button" class="btn btn-danger"
						onclick="javascript:openRelatedForm('DirectPaymentEntry.html');"
						value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
				</div>
			</div>
		</form:form>
	</div>
</div>



