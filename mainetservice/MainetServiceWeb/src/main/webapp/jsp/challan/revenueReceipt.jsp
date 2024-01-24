<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
table.revenueDetails tr th{text-align:left !important}
</style>


<div class="content">
<div class="widget invoice revenue" id="receipt">
<div class="widget-content padding">

				<table class="table table-bordered revenueDetails">
				<tr>
				<td colspan="8">
				<div class="col-sm-2 col-xs-2">
					<img src="${userSession.orgLogoPath}" alt="Organisation Logo" class="not-logged-avatar">
				</div>
				<div class="col-sm-8 col-xs-8 text-center">
					<c:if test="${userSession.languageId eq 1}"><h4>${command.receiptDTO.orgName}</h4></c:if>
					<c:if test="${userSession.languageId eq 2}"><h4>${command.receiptDTO.orgNameMar}</h4></c:if>
					<h5><spring:message code="receipt.label.receipt"/></h5>
					<h6><spring:message code="revenue.receipt.label.act"/></h6> 
				</div>
				</td>
				</tr>
				<tr>
				<th align="left"><span><spring:message code="receipt.label.number"/></span></th>
				<td  colspan="2">${command.receiptDTO.receiptNo}</td>
				<th><span><spring:message code="receipt.label.dateTime"/></span></th>
				<td colspan="2">${command.receiptDTO.receiptDate} ${command.receiptDTO.receiptTime}</td>
				<th><span><spring:message code="receipt.label.finyear"/></span></th>
				<td>${command.receiptDTO.finYear}</td>
				</tr>
				
				<tr>
				<th><spring:message code="" text="Manual Receipt No:"/></th>
				<td colspan="2">${command.receiptDTO.manualReceiptNo}</td>
				<th colspan="2"><spring:message code="" text="Manual Receipt Date:"/></th>
				<td colspan="3">${command.receiptDTO.manualReceiptDate}</td>
				</tr>
				
				<tr>
				<th>${command.receiptDTO.propNo_connNo_estateNo_L}</th>
				<td colspan="2">${command.receiptDTO.propNo_connNo_estateN_V}</td>
				<c:choose>
	              	<c:when test="${command.receiptDTO.deptShortCode eq 'WT'}">
	              	       <th colspan="2"><spring:message code="revenue.receipt.PropNo" text=" property number:"/></th>
	              	</c:when>
	              	<c:otherwise>	              	
	                      <th colspan="2"><spring:message code="revenue.receipt.oldPropNo" text="Old property number:"/></th>
					</c:otherwise>
					</c:choose>
				<td colspan="3">${command.receiptDTO.old_propNo_connNo_V}</td>
				</tr>
				
				<tr>
				<th><spring:message code="receipt.label.department" text="Department"/></th>
				<td colspan="2">${command.receiptDTO.deptName}</td>
				<th colspan="2"><spring:message code="receipt.label.subject"/></th>
				<td colspan="3">${command.receiptDTO.subject}</td>
				</tr>
						
				<tr>
				<th><spring:message code="receipt.label.recFrom" text="Receive From/Owner Name"/></th>
				<td colspan="2">${command.receiptDTO.receivedFrom}</td>
				<th colspan="2"><spring:message code="receipt.label.address" text="Address"/></th>
				<td colspan="3">${command.receiptDTO.address}</td>
				</tr>
				
				<c:if test="${command.receiptDTO.deptShortCode eq 'WT' || command.receiptDTO.deptShortCode eq 'AS'}">
				<tr>
				<c:forEach items="${command.receiptDTO.wardZoneList}" var="wardZone">
				<th colspan="0">${wardZone[0]}</th>
				<td colspan="0">${wardZone[1]}</td>
				</c:forEach>
				</tr>
				</c:if>
				
				<c:if test="${command.receiptDTO.deptShortCode ne 'WT' && command.receiptDTO.deptShortCode ne 'AS'}">
				<tr>
				<th><spring:message code="receipt.zone" text="Zone :" /></th>
				<td>${command.receiptDTO.dwz1}</td>
				<th><spring:message code="receipt.ward" text="Ward :"/></th>
				<td>${command.receiptDTO.dwz2}</td>
				<th><spring:message code="receipt.block" text="Block :"/></th>
				<td>${command.receiptDTO.dwz3}</td>
				<th><spring:message code="receipt.route" text="Route :"/></th>
				<td>${command.receiptDTO.dwz4}</td>
				</tr>
				</c:if>
				
				<tr>
				<th ><spring:message code="revenue.receipt.Usagetype" text="Usage type:"/></th>
				<td colspan="7">
				<c:if test="${command.receiptDTO.usageType1_V ne null}"><div class="col-sm-2" style="padding-left:0px !important">${command.receiptDTO.usageType1_V} </div></c:if>
				<c:if test="${command.receiptDTO.usageType2_V ne null}"><div class="col-sm-2" style="padding-left:0px !important">${command.receiptDTO.usageType2_V} </div></c:if>
				<c:if test="${command.receiptDTO.usageType3_V ne null}"><div class="col-sm-2" style="padding-left:0px !important">${command.receiptDTO.usageType3_V}</div></c:if>
				<c:if test="${command.receiptDTO.usageType4_V ne null}"><div class="col-sm-2" style="padding-left:0px !important">${command.receiptDTO.usageType4_V}</div></c:if>
				<c:if test="${command.receiptDTO.usageType5_V ne null}"><div class="col-sm-2" style="padding-left:0px !important">${command.receiptDTO.usageType5_V}</div></c:if>
				</td>
				</tr>
				
				<tr>
				<th><spring:message code="receipt.label.sumOfRs" text="A sum of Rs."/></th>
				
				<fmt:formatNumber type="number" value="${command.receiptDTO.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="sumOfRs"/>				
				<td colspan="2">${sumOfRs}</td>
				<th colspan="2"><spring:message code="receipt.label.totalAmtWords"/></th>
				<td colspan="3">${command.receiptDTO.amountInWords}</td>
				</tr>
				
				<tr>
				<th><span><spring:message code="receipt.label.payMode" text="Payment Mode"/></span></th>
				<td>${command.receiptDTO.paymentMode}</td>
				<th><span><spring:message code="receipt.label.DrawnOn" text="Drawn On"/></span></th>
				<td>${command.receiptDTO.bankName}</td>
				<th align="left" width="150"><span><spring:message code="pay.option.account"/></span></th>
				<td>${command.receiptDTO.ddOrPpnumber}</td>
				<th class="text-right"><spring:message code="pay.option.accountDate"/></th>
				 <td>${command.receiptDTO.ddOrPpDate}</td>
				</tr>						
				
				 <tr>
							<th rowspan="2" style="text-align:center !important"><spring:message code="receipt.label.billNumber" text="Bill Number"/></th>
							<th rowspan="2" style="text-align:center !important"><spring:message code="receipt.label.billDate" text="Bill Date"/></th>
							<th rowspan="2" colspan="2" style="text-align:center !important" ><spring:message code="receipt.label.detailsOfBill" text="Details of Bill"/></th>
							<th colspan="2" style="text-align:center !important"><spring:message code="receipt.label.payableAmount" text="Payable amount"/></th>
							<th colspan="2" style="text-align:center !important"><spring:message code="receipt.label.receivedAmount" text="Received amount"/></th>
				</tr>
				
				<tr>
				<th style="width:12% ;text-align:center !important"><spring:message code="receipt.label.Arrears" text="Arrears"/></th>
				<th style="width:12%; text-align:center !important"><spring:message code="receipt.label.Current" text="Current"/></th>
				<th style="width:12%;text-align:center !important"><spring:message code="receipt.label.Arrears" text="Arrears"/></th>
				<th style="width:12%;text-align:center !important"><spring:message code="receipt.label.Current" text="Current"/></th>
				</tr>
				
				<tr>
				<td class="text-center"><c:forEach items="${command.receiptDTO.getBillDetails()}" var="entry" varStatus="key">
				${entry.key}<br></c:forEach></td>
				<td class="text-center"><c:forEach items="${command.receiptDTO.getBillDetails()}" var="entry" varStatus="key">
				${entry.value}<br></c:forEach></td>
				<td class="text-left" colspan="2"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">${collection.details}<br></c:forEach></td>
				
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">
				<fmt:formatNumber type="number" value="${collection.amountPayableArrear}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amountPayableArrear"/>
				
				${amountPayableArrear}<br></c:forEach></td>
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">
				<fmt:formatNumber type="number" value="${collection.amountPayableCurrent}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amountPayableCurrent"/>
				
				${amountPayableCurrent}<br></c:forEach></td>
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">
				<fmt:formatNumber type="number" value="${collection.amountReceivedArrear}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amountReceivedArrear"/>
				
				${amountReceivedArrear}<br></c:forEach></td>
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">
				<fmt:formatNumber type="number" value="${collection.amountReceivedCurrent}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amountReceivedCurrent"/>
				
				${amountReceivedCurrent}<br></c:forEach></td>
				</tr>
				<tr>
				<th><spring:message code="receipt.label.advanceAmount" text="Advance Amount"/></th>
				<fmt:formatNumber type="number" value="${command.receiptDTO.advOrExcessAmt}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="advOrExcessAmt"/>
				
				<th style="text-align:right !important" class="text-right">${advOrExcessAmt}</th>
				<th colspan="2"><spring:message code="receipt.label.totalAmount" text="Total Amount"/></th>
				<fmt:formatNumber type="number" value="${command.receiptDTO.totalPayableArrear}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalPayableArrear"/>
				
				<th style="text-align:right !important" class="text-right">${totalPayableArrear}</th>
				
				<fmt:formatNumber type="number" value="${command.receiptDTO.totalPayableCurrent}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalPayableCurrent"/>				
				<th style="text-align:right !important" class="text-right">${totalPayableCurrent}</th>
				
				<fmt:formatNumber type="number" value="${command.receiptDTO.totalReceivedArrear}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalReceivedArrear"/>								
				<th style="text-align:right !important" class="text-right">${totalReceivedArrear}</th>
				
				<fmt:formatNumber type="number" value="${command.receiptDTO.totalreceivedCurrent}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalreceivedCurrent"/>												
				<th style="text-align:right !important" class="text-right">${totalreceivedCurrent}</th>
				</tr>
			</table>	
			 <tr><table class="table table-bordered revenueDetails"><tr>
			 <th><spring:message code="receipt.label.payableAmount" text="Payable amount"/></th>
			 <fmt:formatNumber type="number" value="${command.receiptDTO.amountPayable}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amountPayable"/>
			 <td class="text-right">${amountPayable}</td>	
			 		 
			 <th><spring:message code="receipt.label.rebateAmount" text="Rebate Amount"/></th>
			 <fmt:formatNumber type="number" value="${command.receiptDTO.rebateAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="rebateAmount"/>
			 <td class="text-right">${rebateAmount}</td>
			 
			 <!--Defect #34200  -->
			 <th><spring:message code="" text="Early Payment Discount"/></th>	
			 <fmt:formatNumber type="number" value="${command.receiptDTO.earlyPaymentDiscount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="earlyPaymentAmount"/>			 		
			 <td colspan="1" class="text-right">${earlyPaymentAmount}</td>
			 <!--  -->
			 
			 <th><spring:message code="receipt.label.actualPayableAmount" text="Actual Payable Amount"/></th>	
			 <fmt:formatNumber type="number" value="${command.receiptDTO.totalAmountPayable}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalAmountPayable"/>			 		
			 <td class="text-right">${totalAmountPayable}</td>
			 
			 
			 <th><spring:message code="receipt.label.totalReceivedAmount" text="Total Received Amount"/></th>
			 
			 <fmt:formatNumber type="number" value="${command.receiptDTO.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
			 <td class="text-right">${amount}</td>
			 </tr></table>
			 </tr>
			 <table class="table table-bordered revenueDetails">
			 <tr>
		
			 <td colspan="6">
			 <ul>
			 <li class="bold"><br/><spring:message code="receipt.label.Note" text="Note:"/></li>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.Note1" text="This is computer generated receipt and does not require physical signature"/></li>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.Note2" text="This receipt does not prove the ownership of property or land."/></li>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.Note3" text="For details please visit "/>&nbsp;<a href="https://urbanecg.gov.in" target="_blank"><spring:message code="revenue.receipt.url" text="https://urbanecg.gov.in"/></a></li>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.Note4" text="You can calculate your tax using tax calculator facility at "/>&nbsp;<a href="https://urbanecg.gov.in" target="_blank"><spring:message code="revenue.receipt.url" text="https://urbanecg.gov.in"/></a></li>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.Note5" text="You may validate receipt at "/>&nbsp;<a href="https://urbanecg.gov.in" target="_blank"><spring:message code="revenue.receipt.url" text="https://urbanecg.gov.in"/></a></li>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.realizationNote" text="Cheque/Draft/Banker Cheque/Online payment are subject to realization"/></li>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.Note6" text="This document should not be treated as ownership document"/></li>			     
			 </ul>	
			 <hr style="margin-top:3px !important; margin-bottom:3px !important;">
			 <ul>
			 <li style="font-size: 0.7em !important;"><spring:message code="receipt.label.receivedReceiptNo." text="Received Receipt No."/> ${command.receiptDTO.receiptNo} at ${command.receiptDTO.receiptDate} of Rs. ${command.receiptDTO.amount} against payment for ${command.receiptDTO.deptName} of ${command.receiptDTO.propNo_connNo_estateN_V} in  <c:if test="${command.receiptDTO.dwz1 ne null}"><spring:message code="receipt.zone" text="Zone :" />${command.receiptDTO.dwz1} </c:if><c:if test="${command.receiptDTO.dwz2 ne null}"><spring:message code="receipt.ward" text="Ward :"/> ${command.receiptDTO.dwz2} </c:if>
			 </li>
			 </ul>
			 </td>
			 <td colspan="2">
			  <div class="col-xs-12 text-center" style="margin-top:100px;">
			 <p><spring:message code="receipt.label.signature" text="Signature of Receipient"/></p>
			 </div>
			 			 
			 </td>
			 </tr>
			 </table>

				<div class="text-center hidden-print padding-20">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="" text="Print" />
					</button>
					<button onClick="window.close();" type="button" class="btn btn-danger hidden-print">Close</button>
				</div>					
		</div>
</div>		    				        
<script type="text/javascript">
function printdiv(printpage) {
		var headstr = "<html><head><title></title><link href='assets/css/style-responsive.css' rel='stylesheet' type='text/css' /></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		//document.body.innerHTML = headstr + newstr + footstr;
		document.body.innerHTML = newstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
}
</script>