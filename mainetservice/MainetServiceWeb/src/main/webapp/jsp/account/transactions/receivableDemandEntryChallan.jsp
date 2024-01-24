<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="assets/css/style.css" rel="stylesheet" type="text/css" />
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="rChallan.heading" text="Tax Invoice / Challan" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="ReceivableDemandEntry.html" method="POST" class="form-horizontal">

				<div id="receipt">
					<!-- <div class="table-responsive padding-left-0 padding-right-0"> -->
					<table class="table table-bordered" style="table-layout: solid #848484 !important">
						<thead>
							<tr>
								<th colspan="16" class="text-center padding-top-10 padding-bottom-10"><strong style="font-size: 14px;">${userSession.getCurrent().organisation.ONlsOrgname}</strong> <br /> <spring:message
										code="rChallan.heading" text="Tax Invoice / Challan"
									/></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th class="text-left-imp"><spring:message code="rChallan.deptName" text="Department Name:" /></th>
								<td colspan="3">${command.receivableDemandEntryDto.deptName}</td>
								<th class="text-left-imp"><spring:message code="rChallan.ccn" text="CCN / IDN" /></th>
								<td colspan="2">${command.receivableDemandEntryDto.customerDetails.ccnNumber}</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.wardAddress" text="Ward & Address:" /></th>
								<td colspan="7">${command.receivableDemandEntryDto.wardAddress}</td>
							</tr>
							<tr>
								<th class="text-left-imp"><spring:message code="rChallan.gst" text="GSTIN:" /></th>
								<td colspan="2">${command.receivableDemandEntryDto.orgGSTIN}</td>
								<th class="text-left-imp"><spring:message code="rChallan.binderFolio" text="Binder / Folio:" /></th>
								<td>${command.receivableDemandEntryDto.customerDetails.binder}/${command.receivableDemandEntryDto.customerDetails.folio}</td>
								<th class="text-left-imp"><spring:message code="rChallan.ownershp" text="Ownership:" /></th>
								<td><c:if test="${command.receivableDemandEntryDto.customerDetails.applicationType ne null }">
								<spring:eval expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(${command.receivableDemandEntryDto.customerDetails.applicationType},'')" />							
								</c:if></td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.placeOfSupply" text="Place of Supply" /></th>
								<td colspan="7">${command.receivableDemandEntryDto.locName}</td>
							</tr>
							<tr>
								<th class="text-left-imp"><spring:message code="rChallan.name" text="Name:" /></th>
								<td colspan="2">${ userSession.getCurrent().organisation.ONlsOrgname}</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.meterSize" text="Meter / CCN Size:" /></th>
								<td colspan="2"><c:if test="${command.receivableDemandEntryDto.customerDetails.meterSize ne null }">
										<spring:eval expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(${command.receivableDemandEntryDto.customerDetails.meterSize},'')" />
									</c:if>/ <c:if test="${command.receivableDemandEntryDto.customerDetails.ccnSize ne null }">
										<spring:eval expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(${command.receivableDemandEntryDto.customerDetails.ccnSize},'')" />
									</c:if></td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.invoiceNo" text="Invoice Number / Date of Invoice:" /></th>
								<td colspan="7">${command.receivableDemandEntryDto.billNo}&nbsp;/&nbsp;<fmt:formatDate type="date" value="${command.receivableDemandEntryDto.createdDate}" pattern="dd-MM-yyyy" />
								</td>
							</tr>
							<tr>
								<th class="text-left-imp"><spring:message code="rChallan.add" text="Address:" /></th>
								<td colspan="6">${command.receivableDemandEntryDto.orgAddress}</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.reverseChange" text="Reverse Change:" /></th>
								<td colspan="7">No</td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.fundCode" text="Fund Code:" /></th>
								<td>40</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.functionalCode" text="Functional Code:" /></th>
								<td colspan="2">49</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.original" text="Original:" /></th>
								<td colspan="2"></td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.serialNo.invoice" text="Serial Number of Invoice:" /></th>
								<td colspan="3"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.fundCenterCode" text="Fund Centre / Cost Centre Code:" /></th>
								<td colspan="4">4110490000</td>
								<th class="text-left-imp" colspan="4"><spring:message code="rChallan.fundCenterName" text="Fund Centre / Cost Centre Name:" /></th>
								<td colspan="5">${command.receivableDemandEntryDto.locName}</td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.geoAreaCode" text="Linked Geographical Area Code:" /></th>
								<td colspan="4">4110</td>
								<th class="text-left-imp" colspan="4"><spring:message code="rChallan.geoAreaName" text="Linked Geographical Area Name:" /></th>
								<td colspan="5">${command.receivableDemandEntryDto.locName}</td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.functionCode" text="Function Code:" /></th>
								<td colspan="5">551070000000</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.functionName" text="Function Name:" /></th>
								<td colspan="7"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.vendorCode" text="Vendor Code:" /></th>
								<td></td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.glIndicator" text="Special GL Indicator:" /></th>
								<td colspan="2"></td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.vendorName" text="Vendor Name:" /></th>
								<td colspan="7"></td>
							</tr>
							<tr>
								<th colspan="7" class="text-center"><spring:message code="rChallan.receiver.details" text="Details of Receiver (Billed to)" /></th>
								<th colspan="9" class="text-center"><spring:message code="rChallan.consignee.details" text="Details of Cosignee (Shipped to)" /></th>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.name" text="Name:" /></th>
								<td colspan="5">${command.receivableDemandEntryDto.customerDetails.fName}</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.name" text="Name:" /></th>
								<td colspan="7"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.add" text="Address:" /></th>
								<td colspan="5">${command.receivableDemandEntryDto.custFullAddress}</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.add" text="Address:" /></th>
								<td colspan="7"></td>
							</tr>

							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.state" text="State:" /></th>
								<td colspan="2">MAHARASHTRA</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.stateCode" text="State Code:" /></th>
								<td>27</td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.state" text="State:" /></th>
								<td colspan="4"></td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.stateCode" text="State Code:" /></th>
								<td></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.gst.uniqId" text="GSTIN / Unique ID:" /></th>
								<td colspan="5"></td>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.gst.uniqId" text="GSTIN / Unique ID:" /></th>
								<td colspan="7"></td>
							</tr>
							<tr>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.sr.no." text="Sr. No." /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.description" text="Description of Goods / Services" /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.glcode" text="GL Code" /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.sac" text="HSN / SAC" /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.qty" text="Qty." /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.unit" text="Unit" /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.rate.item" text="Rate (Per Item)" /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.total" text="Total" /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.discount" text="Discount" /></th>
								<th rowspan="2" class="text-center"><spring:message code="rChallan.taxValue" text="Taxable Value" /></th>
								<th colspan="2" class="text-center"><spring:message code="rChallan.cgst" text="CGST" /></th>
								<th colspan="2" class="text-center"><spring:message code="rChallan.sgst" text="SGST" /></th>
								<th colspan="2" class="text-center"><spring:message code="rChallan.igst" text="IGST" /></th>
							</tr>
							<tr>
								<th class="text-center"><spring:message code="rChallan.rate" text="Rate" /></th>
								<th class="text-center"><spring:message code="rChallan.amount" text="Amount" /></th>
								<th class="text-center"><spring:message code="rChallan.rate" text="Rate" /></th>
								<th class="text-center"><spring:message code="rChallan.amount" text="Amount" /></th>
								<th class="text-center"><spring:message code="rChallan.rate" text="Rate" /></th>
								<th class="text-center"><spring:message code="rChallan.amount" text="Amount" /></th>
							</tr>

							<c:set var="d" value="0" scope="page" />
							<c:forEach items="${command.receivableDemandEntryDto.rcvblDemandList }" var="demandList" varStatus="loop">
							<c:if test="${demandList.isDeleted eq 'N'}">
								<tr>
									<td class="text-center">${d+1}</td>
									<td>${demandList.taxName}</td>
									<c:if test="${command.receivableDemandEntryDto.sliStatus eq 'Active'}">
										<td>${demandList.accHead}</td>
									</c:if>
									<c:if test="${command.receivableDemandEntryDto.sliStatus eq 'InActive'}">
										<td>${demandList.accNo}</td>
									</c:if>
									<td>999111</td>
									<td></td>
									<td></td>
									<td></td>
									<fmt:formatNumber var="billAmtdet" value="${demandList.billDetailsAmount}" pattern="#"></fmt:formatNumber>
									<td class="text-right">${billAmtdet}</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>									
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
								</c:if>								
							</c:forEach>
							<fmt:formatNumber var="billAmt" value="${command.receivableDemandEntryDto.billAmount}" pattern="#"></fmt:formatNumber>
							<tr>
								<th class="text-left-imp"><spring:message code="rChallan.frigent" text="Friegnt:" /></th>
								<td></td>
								<th class="text-left-imp"><spring:message code="rChallan.insurance" text="Insurance:" /></th>
								<td></td>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.total" text="Total" />:</th>

								<td class="text-right">${billAmt}</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="2"><spring:message code="rChallan.packing.charge" text="Packing and Forwarding Charges" /></th>
								<td colspan="14"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="4"><spring:message code="rChallan.total.invoiceValue.fig" text="Total Invoice Value (in figure)" /></th>
								<td class="text-right" colspan="6">${billAmt}</td>
								<td colspan="6" rowspan="6" class="text-center text-bold">${userSession.getCurrent().organisation.ONlsOrgname}<div class="margin-top-20">THIS IS ONLINE GENERATED CHALLAN</div></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="4"><spring:message code="rChallan.total.invoiceValue.words" text="Total Invoice Value (in words)" /></th>
								<td class="text-right" colspan="6">${command.receivableDemandEntryDto.billAmountStr}</td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="4"><spring:message code="rChallan.amount.reverse.charge" text="Amount Of Tax Subject to Reverse Charge" /></th>
								<td colspan="6"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="4" rowspan="5">
									<div>
										<spring:message code="rChallan.createdBy" text="Created by:" />
										${userSession.employee.empname}&nbsp;${userSession.employee.emplname}
									</div>
									<div class="margin-top-10">
										<spring:message code="rChallan.createdDate" text="Created date and time:" />
										${command.receivableDemandEntryDto.createdDate}
									</div>
									<div class="margin-top-10">${userSession.employee.tbLocationMas.locNameEng}</div>
									<div class="margin-top-20 text-center">
										<spring:message code="rChallan.accept.cash.dd" text="Please accept the above Cash / DD" />
									</div>
								</th>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.cash.dd.receipt" text="In Case of Cash / DD Receipt:" /></th>
								<td colspan="3"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.bank.accno" text="Bank Account No.:" /></th>
								<td colspan="3"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.branch.name" text="Branch Name:" /></th>
								<td colspan="3"></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.ifsc.code" text="Branch IFSC Code:" /></th>
								<td colspan="3"></td>
								<td colspan="6" rowspan="2" class="text-center text-bold"><spring:message code="rChallan.stamp" text="Authorised Person's User ID / Stamp" /></td>
							</tr>
							<tr>
								<th class="text-left-imp" colspan="3"><spring:message code="rChallan.pan" text="PAN:" /></th>
								<td colspan="3"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="text-center hidden-print padding-top-20">
					<button onclick="printdiv('receipt');" class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="" text="Print" />
					</button>

					<apptags:backButton url="ReceivableDemandEntry.html"></apptags:backButton>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
	/* JS for Print button */
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