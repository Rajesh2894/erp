<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="assets/css/style.css" rel="stylesheet" type="text/css" />
<script src="js/mainet/ui/jquery-1.10.2.min.js"></script> 
<script type="text/javascript"
	src="assets/libs/excel-export/excel-export.js"></script>

<style>
@media print {
	html, body {
		width: 100% !important;
		margine: 0;
	}
	.widget {
		width: 100% !important;
	}
	#wrapper.enlarged .left.side-menu {
		display: none !important;
	}
	#wrapper.enlarged .topbar .topbar-left {
		display: none !important;
	}
	.tothetop, .footer-links {
		display: none !important;
	}
	.topbar {
		display: none !important;
	}
	.print-before {
		page-break-before: always;
	}
	.content-page {
		margin-left: 0px !important;
		margin: 0px !important;
		padding: 0px !important;
	}
	h5 {
		page-break-before: always;
	}
	#receipt .table tr td, #receipt .table tr th {
		padding: 4px 5px !important;
		font-size: 14px !important;
	}
}

.sign-box {
	height: 100px;
	width: 100px;
	border: 1px solid #b5b5b5;
	right: -56px;
}

.p-border {
	border-top: 1px solid #b5b5b5;
	border-bottom: 1px solid #b5b5b5;
}

#receipt .table tr td, #receipt .table tr th {
	font-size: 14px !important;
}
</style>

<script>
	$(window)
			.load(
					function() {
						$(".table-pagination, .remove-btn, .paging-nav, tfoot")
								.remove();
						$(".table thead tr th")
								.removeClass(
										"tablesorter-headerDesc tablesorter-headerAsc tablesorter-header");
						$(".table tr").removeAttr("style");
					});
</script>
<script type="text/javascript"
	src="js/works_management/workMbApproval.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<div class="content">
	<div class="widget">
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">

					<div class="col-xs-12  text-center">
						<h3 class="text-extra-large  margin-bottom-0 margin-top-0">
							<strong>
								<c:if test="${userSession.languageId eq 1}">
							              ${ userSession.getCurrent().organisation.ONlsOrgname}
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
							              ${ userSession.getCurrent().organisation.ONlsOrgnameMar}
								</c:if>
							</strong>
						</h3>
						<h3 class="text-large margin-bottom-0 margin-top-0 text-bold"><spring:message code="works.payment.order.form.twentyseven" text="Form 27 Running Account Bill C." /></h3>
					</div>
					<div class="col-sm-8">
						<p>
							<strong><spring:message code="works.cash.book.voucher.no" text="Cash Book Voucher No.:" /></strong>__
						</p>
					</div>
					<div class="col-sm-4">
						<p>
							<strong><spring:message code="work.estimate.report.date" text="Date:" /></strong>${command.raBillDateStr}</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="work.estimate.report.work.name" text="Name of work : " /></strong>
							${command.definitionDto.workName}
						</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="works.contractor.or.supplier.name" text="Name of the Contractor or Supplier : " /></strong>${command.tenderWorkDto.vendorName}
						</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="works.serial.bill.no" text="Serial No. of bill : " /></strong>
							${command.raBillDto.raSerialNo}
						</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="works.no.and.date.of.previous.bill" text="No. and Date of the previous bill: " /></strong>
							${command.previousRaBillDto.raSerialNo} ,
							${command.previousRaBillDto.raDate}
						</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="works.no.and.date.of.work.order" text="No. and Date of work order : " /></strong>
							${command.workOrder.workOrderNo} ,
							${command.workOrder.workOrderDate}
						</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="works.reference.to.agreement.no" text="Reference to agreement no.: " /></strong>${command.workOrder.contractMastDTO.contNo}</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="works.date.of.commencement" text="Date of commencement : " /></strong>${command.workOrder.workOrderStartDate}
						</p>
					</div>
					<div class="col-sm-12">
						<p>
							<strong><spring:message code="works.date.actual.completion.work" text="Date of actual completion of the work : " /></strong>
						</p>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="col-sm-12 text-center">
						<p class="text-large text-bold"><spring:message code="works.one.account.due" text="1- Account of work due" /></p>
					</div>
					<div class="container ">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th rowspan="3"><spring:message code="work.management.unit" text="Unit" /></th>
									<th rowspan="3"><spring:message code="works.quantity.executed.up.to.date" text="Quantity executed up-to-date per M B." /></th>
									<th rowspan="3"><spring:message code="works.item.sub.work.estimate" text="Items of work grouped under subhead's & sub-work's of Estimate" /></th>
									<th rowspan="2"><spring:message code="sor.baserate" text="Rate" /></th>
									<th colspan="2"><spring:message code="work.estimate.Total" text="Amount" /></th>
									<th rowspan="3" width="15%"><spring:message code="wms.Remark" text="Remark" /></th>
								</tr>
								<tr>
									<th><spring:message code="works.up.to.date" text="Up-to-date" /></th>
									<th><spring:message code="works.since.bill.each.sub.head" text="Since previous bill (Total) for each sub-head" /></th>
								</tr>
								<tr>
									<th width="10%"><spring:message code="works.rs.ps" text="Rs. Ps." /></th>
									<th width="10%"><spring:message code="works.rs.ps" text="Rs. Ps." /></th>
									<th width="10%"><spring:message code="works.rs.ps" text="Rs. Ps." /></th>

								</tr>
								<tr>
									<th><spring:message code="works.number1" text="1" /></th>
									<th><spring:message code="works.number2" text="2" /></th>
									<th><spring:message code="works.number3" text="3" /></th>
									<th><spring:message code="works.number4" text="4" /></th>
									<th><spring:message code="works.number5" text="5" /></th>
									<th><spring:message code="works.number6" text="6" /></th>
									<th><spring:message code="works.number7" text="7" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.measureMentBookList}"
									var="workMBList" varStatus="status">
									<tr>
										<td>${workMBList.sorIteamUnitDesc}</td>
										<td align="right">${workMBList.workEstimQuantity}</td>
										<td>${workMBList.sorDDescription}</td>
										<td align="right">${workMBList.actualRate}</td>
										<td align="right">${workMBList.workActualAmt}</td>
										<td align="right">${workMBList.totalSubHeadAmount}</td>
										<td>${workMBList.remark}</td>
									</tr>
								</c:forEach>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td class="text-right"><spring:message code="works.total.value.done" text="Total value of work done" /></td>
									<td class="text-right">${command.totalRaAmount}</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<c:forEach items="${command.getLevelData('VTY')}" var="payType">
										<c:if
											test="${payType.lookUpCode eq 'PER' && payType.lookUpId eq command.tenderWorkDto.tenderType}">

											<td class="text-right"><c:if
													test="${command.tenderWorkDto.tenderValue gt 0}">
													<strong>+</strong>
												</c:if> <c:if test="${command.tenderWorkDto.tenderValue lt 0}">
													<strong>-</strong>
												</c:if><spring:message code="works.tender.percentage" text="Tender Percentage" />  ${command.tenderWorkDto.tenderValue}%</td>
											<td align="right">${command.tenderWorkDto.tenderAmount}</td>
										</c:if>
										<c:if
											test="${payType.lookUpCode eq 'AMT' && payType.lookUpId eq command.tenderWorkDto.tenderType}">
											<td class="text-right"><spring:message code="tender.tender.amount" text="Tender Amount" /></td>
											<td align="right">0.0</td>
										</c:if>
									</c:forEach>

									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td class="text-right"><spring:message code="works.g.total" text="G. Total" /></td>
									<td align="right">${command.tenderAmount}</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td class="text-right"><spring:message code="works.withheld.amount" text="Withheld Amount" /></td>
									<td align="right">${command.raBillDto.raBillTaxDto.raTaxValue}</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td class="text-right"><spring:message code="wms.SanctionedAmount" text="Bill Amount to be passed" /></td>
									<td align="right">${command.raBillDto.sanctionAmount}</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>

								<c:forEach items="${command.raBillDto.raBillTaxDetails}"
									var="taxDetails" varStatus="status">
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td class="text-right">${taxDetails.taxDesc}<c:if
												test="${taxDetails.raTaxPercent ne null}">
					                             ${taxDetails.raTaxPercent}% 
					</c:if>

										</td>
										<td align="right">${taxDetails.raTaxValue}</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</c:forEach>

								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td class="text-right"><spring:message code="works.net.amount.pay" text="Net Amount Payable" /></td>
									<td align="right">${command.raBillDto.netValue}</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</tbody>
						</table>

						<div class="col-sm-4 col-xs-6 margin-top-20">
							<p><spring:message code="works.total.value.work.upto.bill" text="Total value of work done upto this bill" /></p>
						</div>
						<div class="col-sm-1 text-right col-xs-2 text-bold margin-top-20">
							<p>${command.tenderAmount}</p>
						</div>
						<div class="clear"></div>
						<div class="col-sm-4 col-xs-6 margin-top-10">
							<p><spring:message code="works.withheld.amount" text="Withheld Amount" /></p>
						</div>
						<div class="col-sm-1 text-right col-xs-2 text-bold margin-top-10">
							<p>${command.raBillDto.raBillTaxDto.raTaxValue}</p>
						</div>
						<div class="clear"></div>
						<div class="col-sm-4 col-xs-6 margin-top-10">
							<p><spring:message code="wms.SanctionedAmount" text="Bill amount to be passed" /></p>
						</div>
						<div class="col-sm-1 text-right col-xs-2 text-bold margin-top-10">
							<p>${command.raBillDto.sanctionAmount}</p>
						</div>
						<div class="clear"></div>
						<div class="col-sm-4 col-xs-6 margin-top-10">
							<p><spring:message code="works.amount.paid.since.bill" text="Amount paid since previous to this bill" /></p>
						</div>
						<div class="col-sm-1 text-right col-xs-2 text-bold margin-top-10">
							<p>${command.previousRaBillDto.raBillAmt}</p>
						</div>
						<div class="clear"></div>
						<div class="col-sm-4 col-xs-6 margin-top-10">
							<p><spring:message code="works.amount.now.paid" text="Amount now to be paid" /></p>
						</div>
						<div class="col-sm-1 text-right col-xs-2 text-bold margin-top-10">
							<p>${command.raBillDto.netValue}</p>
						</div>
						<div class="clear"></div>
						<div class="col-sm-12 col-xs-12 margin-top-10">
							<p>
								<spring:message code="works.figure.words" text="Figure in words" /> <span class="text-bold"><spring:message code="works.rupee" text="Rupees" /> 
									${command.raBillDto.raBillAmtStr}</span>
							</p>
						</div>

					</div>

					<h5></h5>

					<div class="col-xs-12 col-sm-12  text-center">
						<h3 class="text-large margin-bottom-0 margin-top-30 text-bold"><spring:message code="works.ii.certificate.sign" text="II-CERTIFICATE AND SIGNATURE" /></h3>
						<div class="col-xs-12 col-sm-12 text-left">
							<p>
								<spring:message code="works.measurement.made.by" text="The measurement were made by Mr" />
								<c:forEach items="${command.measureMentBookMastDtosList}"
									var="workMBList" varStatus="status">
									<strong>${workMBList.workAssigneeName}</strong>
								</c:forEach>

								<spring:message code="works.on.dated" text="on dated" />
								<c:forEach items="${command.measureMentBookMastDtosList}"
									var="workMBList" varStatus="status">
									<strong>${workMBList.mbTakenDate}</strong>
								</c:forEach>

								<spring:message code="works.recorded.page" text="and are recorded on page Nos" />
								<c:forEach items="${command.measureMentBookMastDtosList}"
									var="workMBList" varStatus="status">
									<strong>${workMBList.pageNo}</strong>
								</c:forEach>

								<spring:message code="works.measurement.book.no" text="Of Measurement Book No" />
								<c:forEach items="${command.measureMentBookMastDtosList}"
									var="workMBList" varStatus="status">
									<strong>${workMBList.workMbNo}</strong>
								</c:forEach>
								<spring:message code="works.no.advance.payment.not.proposed.bill" text="No.advance payment has been made previously and not proposed in this bill." />
							</p>
						</div>

						<div class="col-xs-12 col-sm-8 ">
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p class="text-center">
								<strong><spring:message code="works.dated.sign.officer" text="Dated signature of Officer " /><br><spring:message code="works.preparing.bill" text="preparing the bill" /> 
								</strong>
							</p>
						</div>
						<div class="col-xs-12 col-sm-4 text-right">
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>
								<strong><spring:message code="works.rank.sub.div.officer" text="(Rank) Sub Divisional Officer " /><br>.....................
									<spring:message code="works.sub.div" text="Sub Division" />
								</strong>
							</p>
						</div>

						<div class="col-xs-12 col-sm-4 text-left">
							<p class="margin-top-10"><spring:message code="works.thumb.impression.of" text="thumb impression of" /></p>
							<p class="margin-top-10"><spring:message code="works.dates.sign.of" text="Dated signature of ___________________" /></p>
							<p class="margin-top-10"><spring:message code="works.contractor" text="Contractor" /></p>
						</div>

						<div class="col-xs-12 col-sm-8 text-right">
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>
								<spring:message code="works.div" text=".....................Division" /> <br><spring:message code="works.rank" text=" Rank" />
							</p>
						</div>
						<div class="clear"></div>
						<div class="col-xs-12 col-sm-8">
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p class="text-center">
								<spring:message code="works.dated.sign.officer" text="Dated signature of Officer" /> <br><spring:message code="works.authorizing.payment" text=" authorizing payment" />
							</p>
						</div>

						<div class="col-xs-12 col-sm-12">
							<p><spring:message code="works.sign.necs.bill" text="The signature is necessary only when the officer who prepares the bill is not the officer who authorized the payment in such a case two signatures are essential" /></p>
						</div>

						<h5></h5>
						<div class="col-xs-12 col-sm-12  text-center">
							<h3 class="text-large margin-bottom-0 margin-top-30 text-bold"><spring:message code="works.iii.memorandum.payment" text="III-MEMORANDUM OF PAYMENTS" /></h3>
						</div>

						<div class="clearfix padding-10"></div>
						<div class="container">
							<table class="table table-bordered text-left">
								<thead>
									<tr>
										<th width="10%">&nbsp;</th>
										<th>&nbsp;</th>
										<th><spring:message code="works.rs.ps" text="Rs. Ps." /></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="works.total1.col5.entryA" text="1. Total value of work done as per account 1, Col 5 entry (A)" /></td>
										<td class="text-right">${command.tenderAmount}</td>
									</tr>

									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="works.deduction2.amount.withheld" text="2. Deduction amount Withheld" /></td>
										<td class="text-right">${command.raBillDto.raBillTaxDto.raTaxValue}</td>
									</tr>
									<tr>
										<th><spring:message code="works.in.figure.abstract" text="In Figures For works Abstract" /></th>
										<td><spring:message code="works.from.previous.bill.running" text="(a) From previous bills as per last running Account bills" /></td>
										<td class="text-right">${command.raBillDto.totalPreviosWithHeldAmount}</td>
									</tr>
									<tr>
										<th rowspan="5"><spring:message code="works.rs.ps" text="Rs. Ps." /></th>
										<td><spring:message code="works.from.this.bill" text="(b) From this bill" /></td>
										<td class="text-right">${command.raBillDto.raBillTaxDto.raTaxValue}</td>
									</tr>
									<tr>
										<td><spring:message code="works.balance.up.to.date" text="3. Balance I.e.'up to date' payment. (item 1-2)" /></td>
										<td class="text-right">${command.tenderAmount - command.raBillDto.raBillTaxDto.raTaxValue}</td>
									</tr>
									<tr>
										<td colspan="2"><spring:message code="works.total.amount.per.entry.bill.no" text="4. Total amount of payments already made as per entry of last Running Account Bill No. " /><strong>${command.previousRaBillDto.raSerialNo}</strong>
											<spring:message code="works.forwarded.account" text="forwarded with account for" />
										</td>
									</tr>
									<tr>
										<td><spring:message code="works.payments.now.details.below" text="5. Payments now to be made as detailed below." /></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td><spring:message code="works.recovery.work.or.value.stock" text="(a) By recovery of amount creditable to this work or value of stock supplies as detailed in " /><br> <span>
												<spring:message code="works.ledger" text="Ledger........." /><br><spring:message code="works.ditto" text="Ditto....... " /><br><spring:message code="works.ditto.in" text="Ditto in........" />
										</span>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="works.totalABG" text="(Total 2 (b) +5 (A) (G)" /></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td rowspan="2">&nbsp;</td>
										<td><spring:message code="works.b.recovery.amount" text="(b) By recovery of amount creditable to other works or heads of account" /></td>
										<td class="text-right">${command.raBillDto.absoluteTaxAmt}</td>
									</tr>
									<tr>
										<td><spring:message code="works.c.cheque" text="(c) By cheque" /></td>
										<td class="text-right">${command.raBillDto.netValue}</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="works.totalBCH" text="Total 5 (b) + (C) (H)" /></td>
										<td class="text-right">${command.raBillDto.sanctionAmount}</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="clear"></div>
						<div class="col-xs-12 col-sm-12 text-left">
							<p>
								<spring:message code="works.pay.rs" text="Pay Rs" /> &nbsp;<strong>${command.raBillDto.netValue}</strong>&nbsp;
								<spring:message code="works.by.cheque" text="by cheque." />
							</p>
						</div>
						<div class="col-xs-12 col-sm-11 text-right">
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p><spring:message code="works.dated.initials.disbursing.officer" text="(dated initials of Disbursing Officer)" /></p>
						</div>
						<p>&nbsp;</p>

						<div class="col-xs-12 col-sm-12 text-left">
							<p>
								<spring:message code="works.received.rs" text="Received Rs. " />&nbsp;<strong>${command.raBillDto.sanctionAmount}</strong>&nbsp;
							</p>
						</div>
						<div class="col-xs-12 col-sm-6 text-left">
							<p><spring:message code="works.as.per.above.memorandum" text="as per above memorandum on account of this work." /></p>
						</div>
						<div class="col-xs-12 col-sm-6 text-right">
							<p class="padding-right-20"><spring:message code="worsk.amount.vernacular" text="Amount in vernacular" /></p>
						</div>
						<div class="col-xs-8 col-sm-10 text-right margin-top-20">
							<p><spring:message code="works.left.hand.thumb" text="Left hand thumb impression" /></p>
						</div>
						<div class="col-sm-2 col-xs-4 sign-box margin-top-20"></div>
						<div class="col-xs-6 col-sm-6 text-left ">
							<p>
								<spring:message code="works.dated.the" text="Dated the.........." /><br><spring:message code="works.witness" text=" Witness" />
							</p>
						</div>
						<div class="col-xs-6 col-sm-6 text-right">
							<p><spring:message code="works.full.sign" text="[Full signature of contractor]" /></p>
						</div>
						<div class="clear"></div>
						<div class="col-sm-12 margin-top-20 text-left p-border">
							<p><spring:message code="works.paid.me.vide.cheque.no" text="Paid by me Rs........................................vide cheque No............................... Dated ....................." /></p>
						</div>
						<div class="col-sm-6 col-sm-offset-6 margin-top-20">
							<p><spring:message code="works.dated.intials.person" text="(dated initials of person actually making the payment)" /></p>
						</div>
						<h5></h5>
						<div class="col-sm-12">
							<h3 class="text-bold text-center margin-top-30"><spring:message code="works.iv.remarks" text="IV REMARKS" /></h3>
						</div>
						<div class="col-sm-12">

							<div class="table-responsive">
								<table class="table table-bordered table-condensed">
									<tr>
										<th><spring:message code="dashboard.actions.srno"
												text="dashboard.actions.srno" /></th>
										<th><spring:message code="dashboard.actions.datetime"
												text="dashboard.actions.datetime" /></th>
										<th width="18%"><spring:message
												code="dashboard.actions.action"
												text="dashboard.actions.action" /></th>
										<th><spring:message code="dashboard.actions.actor.name"
												text="dashboard.actions.actor.name" /></th>
										<th><spring:message
												code="dashboard.actions.actor.designation"
												text="dashboard.actions.actor.designation" /></th>
										<th width="20%"><spring:message
												code="dashboard.actions.remarks"
												text="dashboard.actions.remarks" /></th>
									</tr>
									<c:set var="rowCount" value="0" scope="page" />
									<c:forEach items="${command.workflowTaskAction}" var="action"
										varStatus="status">
										<c:if test="${action.decision ne 'SUBMITTED'}">
											<tr>
												<td><c:set var="rowCount" value="${rowCount+1}"
														scope="page" /> <c:out value="${rowCount}"></c:out></td>
												<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
														value="${action.dateOfAction}" /></td>
												<c:set var="statusString" value="${action.decision}" />
												<td><spring:message
														code="workflow.action.decision.${fn:toLowerCase(statusString)}"
														text="${action.decision}" />
												<td><c:out value="${action.empName}"></c:out></td>
												<td><c:out value="${action.empGroupDescEng}"></c:out></td>
												<td><c:out value="${action.comments}"></c:out></td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</div>
						<div class="col-sm-12 hidden-print padding-10 text-center">
							<button onclick="window.print('#receipt');"
								class="btn btn-primary hidden-print" type="button">
								<i class="fa fa-print padding-right-5" aria-hidden="true"></i>
								<spring:message code="work.estimate.report.print" text="Print" />
							</button>
							<c:choose>
								<c:when test="${command.saveMode eq 'MB'}">
									<button onclick="window.location.href='MeasurementBook.html'"
										type="button" class="btn btn-danger hidden-print">
										<i class="fa fa-times padding-right-5" aria-hidden="true"></i><spring:message code="wms.close" text="Close" />
									</button>
								</c:when>
								<c:when
									test="${command.saveMode eq 'C' || command.saveMode eq 'E' || command.saveMode eq 'RA'}">
									<button onclick="window.location.href='raBillGeneration.html'"
										type="button" class="btn btn-danger hidden-print">
										<i class="fa fa-times padding-right-5" aria-hidden="true"></i><spring:message code="wms.close" text="Close" />
									</button>
								</c:when>
								<c:otherwise>
									<button onclick="window.location.href='AdminHome.html'"
										type="button" class="btn btn-danger hidden-print">
										<i class="fa fa-times padding-right-5" aria-hidden="true"></i><spring:message code="wms.close" text="Close" />
									</button>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
