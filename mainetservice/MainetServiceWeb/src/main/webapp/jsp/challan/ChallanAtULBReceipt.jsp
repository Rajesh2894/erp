<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Payment Receipt</title>

<style>
*::-moz-selection {background: none repeat scroll 0 0 #63b2f5; color: #fff; text-shadow: none;}
* {margin: 0; padding: 0; outline:0;}
a {text-decoration: none; color: inherit;}
img {border: 0;}
ul, ol {list-style-type: none;}
.clear:after, .clearfix:after {clear: both; display:table;	content: "";}
body{font-family:Arial, Helvetica, sans-serif; color:#000; font-weight:400; overflow-x: hidden;}

table.gridtable {width: 100%; font-size:11px; color:#000; border-collapse: collapse; overflow-x:auto;}
table.gridtable th {padding: 4px; background-color: rgba(81, 81, 81, 0.1); border:1px solid rgba(81, 81, 81, 0.4);  line-height:20px;}
table.gridtable td {font-size: 12px; line-height:20px; padding:4px; background-color: rgba(255, 255, 255, 0.5); border:1px solid rgba(81, 81, 81, 0.4);  vertical-align: top;}

#revenue_receipt{position:relative; width:900px; margin:auto; margin-top:30px; border-radius:4px; border:1px #ccc solid; overflow:hidden;margin-bottom: 35px;}
.header{ position:relative; clear:both; padding:10px; overflow:hidden;}
.header h1{ position:relative; clear:both; padding:10px 0px; text-align:center; font-size:20px; color:#000; text-transform:uppercase; font-weight:normal;}
.header ul{position:relative; clear:both; list-style:none;}
.header ul li{float:left; list-style:none; position:relative;}
.header h2{position:relative; clear:both; padding:3px 0px; text-align:center; line-height:13px; font-size:16px; color:#000; text-transform:uppercase; font-weight:bold;}
.header p{position:relative; clear:both; padding:3px 0px; text-align:center; line-height:13px; font-size:15px;}

.float-right{float:right !important;}
.float-left{float:left !important;}

.header ul li:nth-child(01){width:80px; position:relative;}
.header ul li:nth-child(02){width:680px; text-align:center;}
.header ul li:nth-child(03){width:100px; text-align:center;}
.header img.logo{width:70px; height:70px; position:absolute; top:10px; left:10px;}

input[type="text"]{
	float: left;
	background-color: rgba(255, 255, 255, 0.9);
	border: 1px solid rgba(81, 81, 81, 0.5);
	width:98%;
	font-size: 11px;
	font-weight: 400;
	color: #000;
	padding: 5px;
	height: 25px;
	line-height:11px;
	box-sizing: border-box;
	font-family: inherit;
	white-space: pre-line;
}




textarea[type="text"] {float: left; background-color: rgba(255, 255, 255, 0.9); border: 1px solid rgba(81, 81, 81, 0.5); width:98%; font-size: 11px; font-weight: 400; color:#000; padding:4px; height: 100px; line-height:11px; box-sizing: border-box; font-family:inherit; white-space:pre-line;}
.border-left{border-left: 1px solid rgba(81, 81, 81, 0.5) !important;}
.border-top{border-top: 1px solid rgba(81, 81, 81, 0.5)  !important;}
.border-right{border-right: 1px solid rgba(81, 81, 81, 0.5)  !important;}
.border-bottom{border-bottom: 1px solid rgba(81, 81, 81, 0.5)  !important;}

page[size="A4"] {width:21cm; height:29.7cm; margin:10mm;}


 


@media print {
body {-webkit-print-color-adjust:exact; margin:0mm 10mm 0mm 10mm;}
#btnPrint, #btnCancel{display:none;}
#revenue_receipt {page-break-after: always;}



}

.col-sm-3{    width: 10%;    float: left;}
.col-sm-9{    width: 75%;float: left;}
.logo{margin-top:10px;}
.not-logged-avatar{width:75px;height:75px;} 


</style>

</head>

<body>
<input type='button' id='btnPrint' value='<spring:message code="master.PRINT" text="Print"/>'
		style='width: 100px; position: fixed; bottom: 10px; left: 47%; z-index: 111;'
		onclick='window.print()' />
	<input type='button' id='btnCancel' value='<spring:message code="master.cancel" text="Cancel"/>'
		style='width: 100px; position: fixed; bottom: 10px; left: 55%; z-index: 111;'
		onclick='window.close()' />

<div id="revenue_receipt">
<table class="gridtable">
<tr>
<td colspan="8"><div class="header">
<div class="col-sm-3">
<img src="${userSession.orgLogoPath}" alt="Organisation Logo" style="width: 100px; height: 100px;"></div>
<div class="col-sm-9">
<c:if test="${userSession.languageId eq 1}"><h1>${command.receiptDTO.orgName}</h1></c:if>
<c:if test="${userSession.languageId eq 2}"><h1>${command.receiptDTO.orgNameMar}</h1></c:if>
<h2><spring:message code="receipt.label.receipt"/> &nbsp; <spring:message code="rti.information.rule.reciept" text=""/></h2><%-- <p><spring:message code="receipt.label.act"/></p> --%>
 <p><strong><spring:message code="receipt.gstno"/></strong> ${userSession.organisation.orgGstNo}</p>
</div>
<p class="float-right"><spring:message code="receipt.label.offCopy" text="Office Copy"/></p>
</div></td>
</tr>


<tr>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.number"/></span></th>
<td>${command.receiptDTO.receiptNo}</td>
<th align="left" width="160" colspan="3"><span><spring:message code="receipt.label.dateTime"/></span></th>
<td>${command.receiptDTO.receiptDate} ${command.receiptDTO.receiptTime}</td>
<th align="left" width="120"><span><spring:message code="receipt.label.finyear"/></span></th>
<td>${command.receiptDTO.finYear}</td>
</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.department"/></span></th>
<td colspan="7">${command.receiptDTO.deptName}</td>
</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.cfcref"/></span></th>
<td>${command.cfcSchedulingCounterDet.collcntrno}</td>
<th align="left" colspan="3" scope="row"><span><spring:message code="receipt.label.contref"/></span></th>
<td>${command.cfcSchedulingCounterDet.counterno}</td>
<th align="left" scope="row"><span><spring:message code="receipt.label.mode"/></span></th>
<td>${command.receiptDTO.paymentText}</td>
</tr>

<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.recFrom"/></span></th>
<td colspan="7">${command.receiptDTO.receivedFrom}</td>
</tr>

<tr>
<th align="left" scope="row" width="50"><span><spring:message code="receipt.label.subject"/></span></th>
<td scope="row" colspan="5">${command.receiptDTO.subject}</td>
</tr>

<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.address"/></span></th>
<td colspan="7">${command.receiptDTO.address}</td>
</tr>


<tr>
<th align="left" scope="row"><span><spring:message code="receipt.zone" text="Zone :"/></span></th>
<td><c:if test="${command.receiptDTO.dwz1 ne null}">${command.receiptDTO.dwz1}</c:if></td>

<th align="left" scope="row" width="50"><span><spring:message code="receipt.ward" text="Ward :"/></span></th>
<td scope="row" width="50"><c:if test="${command.receiptDTO.dwz2 ne null}">${command.receiptDTO.dwz2}</c:if></td>


<th align="left" scope="row" width="50"><span><spring:message code="receipt.block" text="Block :"/></span></th>
<td><c:if test="${command.receiptDTO.dwz3 ne null}">${command.receiptDTO.dwz3}</c:if></td>


<th align="left" scope="row"><span><spring:message code="receipt.route" text="Route :"/></span></th>
<td><c:if test="${command.receiptDTO.dwz4 ne null}">${command.receiptDTO.dwz4}</c:if></td>

</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="chn.applicationNo"/></span></th>
<td><c:choose><c:when test="${not empty command.refNo}">${command.refNo}</c:when><c:otherwise>${command.receiptDTO.applicationNumber}</c:otherwise></c:choose></td>
<th align="left" colspan="3" scope="row"><span><spring:message code="chn.loiNo"/></span></th>
<td>${command.receiptDTO.referenceNo}</td>
<th align="left" scope="row"><span><spring:message code="chn.ApplnloiDate"/></span></th>
<td>${command.receiptDTO.date}</td>
</tr>



<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.payMode"/></span></th>
<td>${command.receiptDTO.paymentMode}</td>
<th align="left" scope="row" colspan="3"><span><spring:message code="chn.amount"/></span></th>
<fmt:formatNumber type="number" value="${command.receiptDTO.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amt"/>
<td align="left">${amt}</td>
<th align="left" scope="row"><span><spring:message code="pay.option.account"/></span></th>
<td>${command.receiptDTO.ddOrPpnumber}</td>
</tr>

 


<tr>
<th align="left" scope="row"><span><spring:message code="pay.option.accountDate"/></span></th>
<td>${command.receiptDTO.ddOrPpDate}</td>
<th align="left" colspan="3" scope="row"><span><spring:message code="pay.option.bank.name"/></span></th>
<td colspan="3">${command.receiptDTO.bankName}</td>
</tr>
</table>

<table class="gridtable">
<tr>
<th align="left" scope="col" width=""><span><spring:message code="receipt.label.details"/></span></th>
<th align="left" scope="col" width="120"><span><spring:message code="receipt.label.payAmount"/></span></th>
<th align="left" scope="col" width="150"><span><spring:message code="receipt.label.recAmount"/></span></th>
</tr>
<c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment"> 
<tr>
<td class="border-left  border-right"><span class="float-right"><b>${collection.details}</b></span></td>
<fmt:formatNumber type="number" value="${collection.amountPayable}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amtpayble"/>
<td class="border-left  border-right"><span class="float-left">${amtpayble}</span></td>
<fmt:formatNumber type="number" value="${collection.amountReceived}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amtReceived"/>
<td class="border-left  border-right"><span class="float-left">${amtReceived}</span></td>
</tr>
</c:forEach>

<tr>
<td class="border-left border-right border-top border-bottom"><span class="float-right"><b><spring:message code="receipt.label.totalAmt"/></b></span></td>
<fmt:formatNumber type="number" value="${command.receiptDTO.totalAmountPayable}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalamtpayable"/>
<td class="border-right border-top border-bottom"><span class="float-left"><b>${totalamtpayable}</b></span></td>
<fmt:formatNumber type="number" value="${command.receiptDTO.totalReceivedAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalamtReceived"/>
<td class="border-right border-top border-bottom"><span class="float-left"><b>${totalamtReceived}</b></span></td>
</tr>
</table>
<table class="gridtable">
<tr>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.totalAmtWords"/></span></th>
<td width="344">${command.receiptDTO.amountInWords}</td>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.recvrSign"/></span></th>
<td>&nbsp; ${command.receiptDTO.receiverName}</td>
</tr>
<tr>
<td colspan="6"><span><b><spring:message code="receipt.label.Note"/></b> <spring:message code="receipt.label.msg"/></span></td>
</tr>
</table>
</div>

<div id="revenue_receipt">
<table class="gridtable">
<tr>
<td colspan="8"><div class="header">
<div class="col-sm-3">
<img src="${userSession.orgLogoPath}" alt="Organisation Logo" style="width: 100px; height: 100px;"></div>							
<div class="col-sm-9">
<c:if test="${userSession.languageId eq 1}"><h1>${command.receiptDTO.orgName}</h1></c:if>
<c:if test="${userSession.languageId eq 2}"><h1>${command.receiptDTO.orgNameMar}</h1></c:if>
<h2><spring:message code="receipt.label.receipt"/> &nbsp;<spring:message code="rti.information.rule.reciept" text=" "/></h2><%-- <p><spring:message code="receipt.label.act"/></p> --%>
 <p><strong><spring:message code="receipt.gstno"/></strong> ${userSession.organisation.orgGstNo}</p>
</div>
<p class="float-right"><spring:message code="receipt.label.couCopy" text="Citizen Copy"/></p>
</div></td>
</tr>


<tr>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.number"/></span></th>
<td>${command.receiptDTO.receiptNo}</td>
<th align="left" width="160" colspan="3"><span><spring:message code="receipt.label.dateTime"/></span></th>
<td>${command.receiptDTO.receiptDate} ${command.receiptDTO.receiptTime}</td>
<th align="left" width="120"><span><spring:message code="receipt.label.finyear"/></span></th>
<td>${command.receiptDTO.finYear}</td>
</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.department"/></span></th>
<td colspan="7">${command.receiptDTO.deptName}</td>
</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.cfcref"/></span></th>
<td>${command.cfcSchedulingCounterDet.collcntrno}</td>
<th align="left" colspan="3" scope="row"><span><spring:message code="receipt.label.contref"/></span></th>
<td>${command.cfcSchedulingCounterDet.counterno}</td>
<th align="left" scope="row"><span><spring:message code="receipt.label.mode"/></span></th>
<td>${command.receiptDTO.paymentText}</td>
</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.recFrom"/></span></th>
<td colspan="7">${command.receiptDTO.receivedFrom}</td>
</tr>

<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.subject"/></span></th>
<td colspan="7">${command.receiptDTO.subject}</td>
</tr>

<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.address"/></span></th>
<td colspan="7">${command.receiptDTO.address}</td>
</tr>


<tr>
<th align="left" scope="row"><span><spring:message code="receipt.zone" text="Zone :"/></span></th>
<td><c:if test="${command.receiptDTO.dwz1 ne null}">${command.receiptDTO.dwz1}</c:if></td>

<th align="left" scope="row" width="50"><span><spring:message code="receipt.ward" text="Ward :"/></span></th>
<td scope="row" width="50"><c:if test="${command.receiptDTO.dwz2 ne null}">${command.receiptDTO.dwz2}</c:if></td>


<th align="left" scope="row" width="50"><span><spring:message code="receipt.block" text="Block :"/></span></th>
<td><c:if test="${command.receiptDTO.dwz3 ne null}">${command.receiptDTO.dwz3}</c:if></td>


<th align="left" scope="row"><span><spring:message code="receipt.route" text="Route :"/></span></th>
<td><c:if test="${command.receiptDTO.dwz4 ne null}">${command.receiptDTO.dwz4}</c:if></td>

</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="chn.applicationNo"/></span></th>
<td><c:choose><c:when test="${not empty command.refNo}">${command.refNo}</c:when><c:otherwise>${command.receiptDTO.applicationNumber}</c:otherwise></c:choose></td>
<th align="left" colspan="3" scope="row"><span><spring:message code="chn.loiNo"/></span></th>
<td>${command.receiptDTO.referenceNo}</td>
<th align="left" scope="row"><span><spring:message code="chn.ApplnloiDate"/></span></th>
<td>${command.receiptDTO.date}</td>
</tr>



<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.payMode"/></span></th>
<td>${command.receiptDTO.paymentMode}</td>
<th align="left" scope="row" colspan="3"><span><spring:message code="chn.amount"/></span></th>
<fmt:formatNumber type="number" value="${command.receiptDTO.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amt"/>
<td align="left">${amt}</td>
<th align="left" scope="row"><span><spring:message code="pay.option.account"/></span></th>
<td>${command.receiptDTO.ddOrPpnumber}</td>
</tr>

 


<tr>
<th align="left" scope="row"><span><spring:message code="pay.option.accountDate"/></span></th>
<td>${command.receiptDTO.ddOrPpDate}</td>
<th align="left" colspan="3" scope="row"><span><spring:message code="pay.option.bank.name"/></span></th>
<td colspan="3">${command.receiptDTO.bankName}</td>
</tr>
</table>

<table class="gridtable">
<tr>
<th align="left" scope="col" width=""><span><spring:message code="receipt.label.details"/></span></th>
<th align="left" scope="col" width="120"><span><spring:message code="receipt.label.payAmount"/></span></th>
<th align="left" scope="col" width="150"><span><spring:message code="receipt.label.recAmount"/></span></th>
</tr>
<c:forEach items="${command.receiptDTO.getPaymentList()}" var="collection" varStatus="statusPayment"> 
<tr>
<td class="border-left  border-right"><span class="float-right"><b>${collection.details}</b></span></td>
<fmt:formatNumber type="number" value="${collection.amountPayable}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amtpayble"/>
<td class="border-left  border-right"><span class="float-left">${amtpayble}</span></td>
<fmt:formatNumber type="number" value="${collection.amountReceived}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amtReceived"/>
<td class="border-left  border-right"><span class="float-left">${amtReceived}</span></td>
</tr>
</c:forEach>

<tr>
<td class="border-left border-right border-top border-bottom"><span class="float-right"><b><spring:message code="receipt.label.totalAmt"/></b></span></td>
<fmt:formatNumber type="number" value="${command.receiptDTO.totalAmountPayable}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalamtpayable"/>
<td class="border-right border-top border-bottom"><span class="float-left"><b>${totalamtpayable}</b></span></td>
<fmt:formatNumber type="number" value="${command.receiptDTO.totalReceivedAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalamtReceived"/>
<td class="border-right border-top border-bottom"><span class="float-left"><b>${totalamtReceived}</b></span></td>
</tr>
</table>
<table class="gridtable">
<tr>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.totalAmtWords"/></span></th>
<td width="344">${command.receiptDTO.amountInWords}</td>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.recvrSign"/></span></th>
<td>&nbsp; ${command.receiptDTO.receiverName}</td>
</tr>
<tr>
<td colspan="6"><span><b><spring:message code="receipt.label.Note"/></b> <spring:message code="receipt.label.msg"/></span></td>
</tr>
</table>
</div>
</body>
</html>
