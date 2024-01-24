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
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountReceipt.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
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
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="accounts.receipt"
								text="Receipt" /></h2>
		</div>
		<div class="widget-content padding">
			<form:form class="form-horizontal"
				modelAttribute="oAccountReceiptReportMasDto"
				cssClass="form-horizontal" method="POST">
				<div id="receipt">
					<div class="form-group">
						<div class="col-xs-12 text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
									  ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if> 
									</h3>
							<spring:message code="accounts.receipt"
								text="Receipt" />
						</div>
					</div>
					<table class="table table-bordered table-condensed">
						<tr>
							<th style="text-align: left; width: 15%;"><spring:message
									code="cheque.dd.receipt.number" text="Receipt No." /></th>
							<th colspan="2" style="text-align: center"><spring:message
									code="accounts.chequebookleaf.date" text="Date of Receipt" /></th>
							<th colspan="3" style="text-align: center"><spring:message
									code="accounts.receipt.collection.center"
									text="Collection Center" /></th>
						</tr>
						<tr>
							<td align="left">${oAccountReceiptReportMasDto.rmRcptno}</td>
							<td colspan="2">${oAccountReceiptReportMasDto.rmDate}</td>
							<td colspan="3">${oAccountReceiptReportMasDto.cashCollectNo}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message
									code="accounts.receipt.received_from" text="Received From" /></th>
							<td colspan="5">${oAccountReceiptReportMasDto.vmVendorIdDesc}</td>

						</tr>


						<tr>
							<th style="text-align: left"><spring:message
									code="accounts.receipt.amt.rupees" text="Amount Rs" /></th>
							<td colspan="5">${oAccountReceiptReportMasDto.rmAmount}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message
									code="accounts.receipt.amt.in.words" text="Amount in Words" /></th>
							<td colspan="5">${oAccountReceiptReportMasDto.amountInWords}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message
									code="account.deposit.narration" text="Narration" /></th>
							<td colspan="5">${oAccountReceiptReportMasDto.rmNarration}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message
									code="accounts.receipt.modereceipt" text="Mode of Receipt" /></th>

							<th style="text-align: center; width: 9%;"><spring:message
									code="accounts.receipt.cheque.number" text="Cheque No." /></th>

							<th style="text-align: center; width: 9%"><spring:message
									code="accounts.receipt.cheque.date" text="Cheque date" /></th>
							<th style="text-align: center; width: 30%"><spring:message
									code="account.bankmaster.bankname" text="Bank Name" /></th>
							<th colspan="3" style="text-align: center"><spring:message
									code="accounts.receipt.branch.name" text="Branch Name" /></th>
						</tr>
						<tr>
							<td align="left">${oAccountReceiptReportMasDto.cpdFeemodeDesc}</td>
							<td>${oAccountReceiptReportMasDto.rdChequeddno}</td>
							<td>${oAccountReceiptReportMasDto.rdChequedddatetemp}</td>
							<td>${oAccountReceiptReportMasDto.cbBankidDesc}</td>
							<td colspan="3">${oAccountReceiptReportMasDto.branch}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message
									code="accounts.receipt.ref.number"
									text="Reference No. / Bill No." /></th>
							<th colspan="3"><spring:message
									code="accounts.receipt.head.detail" text="Receipt Head Details" /></th>

							<th colspan="2" style="text-align: center"><spring:message
									code="accounts.receipt.received.amt" text="Received Amount" /></th>
						</tr>
						<c:forEach items="${oAccountReceiptReportMasDto.receiptFeeDetail}"
							var="BankList" varStatus="status">
							<tr>
								<td>${oAccountReceiptReportMasDto.manualReceiptNo}</td>
								<td colspan="3" style="text-align: left">${BankList.receiptHead}</td>
								<td colspan="2" align="right">${BankList.receiptAmount}</td>
							</tr>
						</c:forEach>
						<tr>
							<th colspan="5" class="text-right"><spring:message
									code="account.voucher.total" text="Total" /></th>
							<th class="text-right">${oAccountReceiptReportMasDto.rdAmount}</th>
						</tr>
						<tr>
							<td colspan="6" class="height-155 ">
								<div class="col-sm-5 margin-top-10">
									<p><spring:message
									code="accounts.receipt.by.cheque.DD" text="Note:Receipt by Cheque / DD will be valid subject to
										realization of the Cheque / DD." />
										</p>
								</div>
								<div
									class="col-sm-12 text-right vertical-align-bottom padding-top-30 margin-top-30">
									<p>...................................................................................</p>
									<p>
										<spring:message code="account.voucher.auth.offc.sign"
											text="Signature of Authorised Officer" />
									</p>
									<p>
										<spring:message code="account.voucher.auth.offc.name"
											text="(Name / Designation of the Authorised Officer)" />
									</p>
								</div>
							</td>


						</tr>
					</table>


					<div class="text-center hidden-print padding-10">

						<button onclick="printdiv('receipt');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
						</button>
						<c:if test="${oAccountReceiptReportMasDto.advanceFlag eq 'Y'}">
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('AdvanceEntry.html');"
								value="<spring:message code="account.bankmaster.back"  text ="Back"/>"
								id="cancelEdit" />
						</c:if>
						<c:if test="${oAccountReceiptReportMasDto.advanceFlag ne 'Y' && flag ne 'GRT' && flag ne 'INV' && flag ne 'LNR'}">
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('AccountReceiptEntry.html');"
								value="<spring:message code="account.bankmaster.back" text ="Back"/>"
								id="cancelEdit" />
						</c:if>
						
						<!-- Defect #85098 -->
						<c:if test="${oAccountReceiptReportMasDto.advanceFlag ne 'Y' && flag eq 'GRT'}">
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('grantMaster.html');"
								value="<spring:message code="account.bankmaster.back" text ="Back"/>"
								id="cancelEdit" />
						</c:if>				
						<c:if test="${oAccountReceiptReportMasDto.advanceFlag ne 'Y' && flag eq 'INV'}">
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('investmentMaster.html');"
								value="<spring:message code="account.bankmaster.back" text ="Back"/>"
								id="cancelEdit" />
						</c:if>
						
						<c:if test="${oAccountReceiptReportMasDto.advanceFlag ne 'Y' && flag eq 'LNR'}">
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('loanmaster.html');"
								value="<spring:message code="account.bankmaster.back" text ="Back"/>"
								id="cancelEdit" />
						</c:if>
					</div>
				</div>
			</form:form>
		</div>

	</div>







