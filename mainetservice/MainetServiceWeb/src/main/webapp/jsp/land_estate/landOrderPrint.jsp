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
<input type='button' id='btnPrint' value='Print'
		style='width: 100px; position: fixed; bottom: 10px; left: 47%; z-index: 111;'
		onclick='window.print()' />
	<input type='button' id='btnCancel' value='Cancel'
		style='width: 100px; position: fixed; bottom: 10px; left: 55%; z-index: 111;'
		onclick="window.location.href='AdminHome.html'" />

<div id="revenue_receipt">
<table class="gridtable">
<tr>
<td colspan="8"><div class="header">
<div class="col-sm-3">
<img src="${userSession.orgLogoPath}" alt="Organisation Logo" class="not-logged-avatar"></div>
<div class="col-sm-9">
<c:if test="${userSession.languageId eq 1}"><h1>${receiptDTO.orgName}</h1></c:if>
<c:if test="${userSession.languageId eq 2}"><h1>${receiptDTO.orgNameMar}</h1></c:if>
<%-- <h2><spring:message code="receipt.label.receipt"/></h2> --%><%-- <p><spring:message code="receipt.label.act"/></p> --%>
 <p><strong><spring:message code="receipt.gstno"/></strong> ${userSession.organisation.orgGstNo}</p>
</div>
<%-- <p class="float-right"><spring:message code="receipt.label.offCopy" text="Office Copy"/></p> --%>
</div></td>
</tr>

<tr>
<th align="left" scope="row"><span><spring:message code="land.order.print.dateTime" text="Date Time"/></span></th>
<td colspan="7">${receiptDTO.receiptDate} ${receiptDTO.receiptTime}</td>
</tr>

<%-- <tr>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.number"/></span></th>
<td>${receiptDTO.receiptNo}</td>
<th align="left" width="160" colspan="3"><span><spring:message code="" text="Date Time"/></span></th>
<td>${receiptDTO.receiptDate} ${receiptDTO.receiptTime}</td>
<th align="left" width="120"><span><spring:message code="receipt.label.finyear"/></span></th>
<td>${receiptDTO.finYear}</td>
</tr> --%>
<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.department"/></span></th>
<td colspan="7">${receiptDTO.deptName}</td>
</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="receipt.label.cfcref"/></span></th>
<td>&nbsp;</td>
<th align="left" colspan="3" scope="row"><span><spring:message code="receipt.label.contref"/></span></th>
<td>&nbsp;</td>
<th align="left" scope="row"><span><spring:message code="receipt.label.mode"/></span></th>
<td>${receiptDTO.paymentText}</td>
</tr>
<tr>
<th align="left" scope="row"><span><spring:message code="land.order.print.payedTo" text="Payed To"/></span></th>
<td colspan="7">${receiptDTO.receivedFrom}</td>
</tr>

<tr>
<th align="left" scope="row"><span><spring:message code="chn.applicationNo"/></span></th>
<td>${receiptDTO.applicationNumber}</td>
<th align="left" colspan="3" scope="row"><span><spring:message code="chn.loiNo"/></span></th>
<td>${receiptDTO.referenceNo}</td>
<th align="left" scope="row"><span><spring:message code="chn.ApplnloiDate"/></span></th>
<td>${receiptDTO.date}</td>
</tr>


<tr>
<th align="left" scope="row"><span><spring:message code="land.order.print.valuationAmt"  text="Valuation Amount :"/></span></th>
<fmt:formatNumber type="number" value="${receiptDTO.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amt"/>
<td colspan="7">${amt}</td>
</tr>

</table>


<table class="gridtable">
<tr>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.totalAmtWords"/></span></th>
<td width="344">${receiptDTO.amountInWords}</td>
<th align="left" scope="row" width="120"><span><spring:message code="receipt.label.recvrSign"/></span></th>
<td>&nbsp; ${receiptDTO.receiverName}</td>
</tr>
<tr>
<td colspan="6"><span><b><spring:message code="receipt.label.Note"/></b> <spring:message code="receipt.label.msg"/></span></td>
</tr>
</table>
</div>

</body>
</html>
