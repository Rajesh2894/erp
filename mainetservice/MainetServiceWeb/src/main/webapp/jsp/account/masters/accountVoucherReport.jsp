<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script
	src="js/account/transaction/accountJournalVoucherEntry.js"></script>
<script>
$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : $("#rmDatetemp").val(),
			maxDate : '-0d',
			changeYear : true
		});
	});
	</script>

<script>
	function printdiv(printpage)
	{
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr+newstr+footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>
<div class="widget-content padding">
	<form:form id="frmMaster" class="form-horizontal"
		modelAttribute="reportDto" name="frmMaster" method="POST"
		action="AccountVoucherEntry.html">

		<div id="receipt">
			<div class="form-group">
				<div class="col-xs-12 text-center">
					<h3 class="text-extra-large margin-bottom-0 margin-top-0">
					<c:if test="${userSession.languageId eq 1}">
                               ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
					</c:if>
					<c:if test="${userSession.languageId eq 2}">
								${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
					</c:if> </h3>
						${reportDto.voucherDesc} 
				</div>

			</div>
			<table class="table table-bordered table-condensed">
				<tr>
					<th style="text-align: left"><spring:message
							code="account.voucher.number" text="Voucher No." /></th>
					<td>${reportDto.vouNo}</td>
					<th style="text-align: left"><spring:message code="account.voucher.date"
							text="Voucher Date" /></th>
					<td>${reportDto.vocherDate}</td>
				</tr>
				<tr>
					<th style="text-align: left"><spring:message
							code="voucher.template.entry.master.vouchertype"
							text="Voucher Type" /></th>
					<td>${reportDto.voucherDesc}</td>
					<th style="text-align: left"><spring:message code="account.contra.voucher.subtype"
							text="Voucher Sub Type" /></th>
					<td>${reportDto.vouchersubType}</td>
				</tr>
				<tr>
					<th style="text-align: left"><spring:message
							code="voucher.template.entry.master.department" text="Department" /></th>
					<td colspan="3"><spring:message code="account.field.acc"
							text="Account" /></td>
				</tr>
				<tr>
					<th style="text-align: left" colspan="2"><spring:message
							code="account.Head" text="Account Head" /></th>
					<th style="text-align: center"><spring:message
							code="account.voucher.amount.debit" text="Amount Debit(Rs.)" /></th>
					<th style="text-align: center"><spring:message
							code="account.voucher.amount.credit" text="Amount Credit(Rs.)" /></th>
				</tr>
				<c:forEach items="${reportDto.accountHeadList}" var="RecieptList"
					varStatus="status">
					<c:set value="${status.index}" var="count"></c:set>

					<tr>
						<td align="left" colspan="2">${RecieptList.accountHead}</td>
						<td align="right">${RecieptList.amountDebit}</td>
						<td align="right">${RecieptList.amountCredit}</td>

					</tr>

				</c:forEach>

				<tr>
					<th colspan="2" class="text-right"><spring:message
							code="account.voucher.total" text="Total" /></th>
					<th style="text-align: right">${reportDto.drtotalAmount}</th>
					<th style="text-align: right">${reportDto.drtotalAmount}</th>
				</tr>
				<tr>
					<th style="text-align: left"><spring:message
							code="accounts.receipt.narration" text="Narration" /></th>
					<td colspan="3">${reportDto.narration}</td>
				</tr>
				
				<tr>
				<td ><b><spring:message code="account.report.Prepared.by" text="Prepared by:" /></b> ${reportDto.preparedBy}</td>
				<td ><b><spring:message code="account.report.Verified.by" text="Verified by:" /></b> ${reportDto.verifiedby}</td>
				<td ><b><spring:message code="account.report.Approved.by" text="Approved by:" /></b> ${reportDto.approvedBy}</td>
				<td><b><spring:message code="account.report.Posted.by" text="Posted by:" /></b> ${reportDto.postedby}</td>
				</tr>
				
				<tr>
				<td ><b><spring:message code="accounts.date" text="Date:" /></b> ${reportDto.preparedDate}</td>
				<td ><b><spring:message code="accounts.date" text="Date:" /></b> ${reportDto.verifiedDate}</td>
				<td ><b><spring:message code="accounts.date" text="Date:" /></b> ${reportDto.approvedDate}</td>
				<td><b><spring:message code="accounts.date" text="Date:" /></b> ${reportDto.postedDate}</td>
				</tr>
				
				<tr>
					<td colspan="4" class="height-155 text-right vertical-align-bottom"><p>...................................................................................</p>
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

				<c:if test="${reportDto.depositFlag ne 'Y'}">

				<c:if test="${reportDto.authoFlg eq 'Y'}">

					<input type="button" class="btn btn-danger"
						onclick="window.location.href='AccountVoucherAuthorisation.html'"
						value="Back" id="cancelEdit" />
				</c:if>
				
				<c:if test="${reportDto.authoFlg ne 'Y'}">
				
						<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountVoucherEntry.html'"
							value="<spring:message code="account.bankmaster.back"
						text="Back" />" id="cancelEdit" />
				</c:if>
				</c:if>
				
				<c:if test="${reportDto.depositFlag eq 'Y'}">

					<input type="button" class="btn btn-danger"
						onclick="window.location.href='AccountDeposit.html'" value="Back"
						id="cancelEdit" />

				</c:if>




			</div>
		</div>
	</form:form>
</div>




