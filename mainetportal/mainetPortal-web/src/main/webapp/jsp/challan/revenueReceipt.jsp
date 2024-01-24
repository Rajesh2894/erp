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


<script>
function printdiv(printpage) {
		var headstr = "<html><head><title></title><link href='assets/css/dashboard-theme-2.css' rel='stylesheet' type='text/css' /></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
}
</script>
<style>
@media print{
.revenue  tr th,.revenue tr td{padding: 4px 10px !important;
    font-size: 12px;
    line-height: 14px;}}
</style>
<div class="content" id="receipt">
<div class="widget invoice revenue">
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
					<h4><spring:message code="receipt.label.receipt"/></h4>
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
				<th>${command.receiptDTO.propNo_connNo_estateNo_L}</th>
				<td colspan="2">${command.receiptDTO.propNo_connNo_estateN_V}</td>
				<th colspan="2"><spring:message code="revenue.receipt.oldPropNo" text="Old property number:"/></th>
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
				<td colspan="2">${command.receiptDTO.amount}</td>
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
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">${collection.amountPayableArrear}<br></c:forEach></td>
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">${collection.amountPayableCurrent}<br></c:forEach></td>
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">${collection.amountReceivedArrear}<br></c:forEach></td>
				<td class="text-right"><c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment">${collection.amountReceivedCurrent}<br></c:forEach></td>
				</tr>
				<tr>
				<th><spring:message code="receipt.label.advanceAmount" text="Advance Amount"/></th>
				<th style="text-align:right !important" class="text-right">${command.receiptDTO.advOrExcessAmt}</th>
				<th colspan="2"><spring:message code="receipt.label.totalAmount" text="Total Amount"/></th>
				<th style="text-align:right !important" class="text-right">${command.receiptDTO.totalPayableArrear}</th>
				<th style="text-align:right !important" class="text-right">${command.receiptDTO.totalPayableCurrent}</th>
				<th style="text-align:right !important" class="text-right">${command.receiptDTO.totalReceivedArrear}</th>
				<th style="text-align:right !important" class="text-right">${command.receiptDTO.totalreceivedCurrent}</th>
				</tr>

			 <tr>
			 <th><spring:message code="receipt.label.payableAmount" text="Payable amount"/></th>
			 <td class="text-right">${command.receiptDTO.amountPayable}</td>
			 <th><spring:message code="receipt.label.rebateAmount" text="Rebate Amount"/></th>
			 <td class="text-right">${command.receiptDTO.rebateAmount}</td>
			 <th><spring:message code="receipt.label.actualPayableAmount" text="Actual Payable Amount"/></th>			
			 <td class="text-right">${command.receiptDTO.totalAmountPayable}</td>
			 <th><spring:message code="receipt.label.totalReceivedAmount" text="Total Received Amount"/></th>
			 <td class="text-right">${command.receiptDTO.amount}</td>
			 </tr>
			 
			 <tr>
		
			 <td colspan="6">
			 <ul>
			 <li class="bold"><spring:message code="receipt.label.Note" text="Note:"/></li>
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
				<br>
				<div class="text-center hidden-print padding-20">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="" text="Print" />
					</button>
					<button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button>
				</div>					
		</div>
</div>		    				        
</div>				
				    				        
