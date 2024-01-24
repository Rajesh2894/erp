<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="dto" value="${command.challanDTO}"></c:set>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<style type="text/css">
.wrapper {position: relative; width: 1124px; margin: auto;}

.side {
	position: relative;
	float: left;
	width: 360px;
	border:solid 1px #000;
	font-family:arial;
	margin:5px 5px;
}

.side:after {
	height: 10px;
	display: block;
	width: 1px;
	height: 100%;
	position: absolute;
	top: 0px;
	right: -7px;
	background: #fff;
	border-right: 1px black dashed;
	content: '';
}

.challan {
	position: relative;
	float: left;
	font-family: Arial, Helvetica, sans-serif;
	border: solid 1px #000;
	min-height: 500px;
	width: 98%;
	margin: 0.5% 0.5%;
}

.last:after {
	display: block;
	width: 1px;
	height: 100%;
	position: absolute;
	top: 0px;
	right: -8px;
	background: #fff;
	border-right: 0px black dashed;
	content: '';
}

.logo {
	position: relative;
	clear: both;
	width: 100%;
}

.logo ul {
	position: relative;
	list-style: none;
	display: block;
	margin: 0px;
	overflow: hidden;
	padding: 2px 4px;
}

.logo ul li {
	position: relative;
	list-style: none;
	display: block;
	padding: 0px 2px;
	float: left;
}

.logo ul li .header {
	position: relative;
}

.logo ul li .header p {
	padding: 0px;
}

.logo ul li .header1 {
	position: relative;
	width: 212px;
	color: #000;
	margin:0px 3px; 
}


.logo ul li .header1 h1{ font-size:15px; font-weight:bold; line-height:18px; padding:3px 0px; margin:0px; text-align:center;}
.logo ul li .header1 h3{ font-size:13px; font-weight:normal; line-height:15px; padding:1px 0px; margin:0px;  text-align:center;}
.logo ul li .header1 p{ font-size:12px; text-align:center; font-weight:normal; padding:4px 0px; line-height:16px;}

.logo ul li .header1 p {
	padding: 0px;
	margin: 0px;
}

.logo ul li .header2 {
	position: absolute;
	left: 60px;
	top: 0px;
	width: 200px;
	color: #000;
}

.logo ul li .header2 p {
	padding: 0px;
	margin: 0px;
}

.logo ul li img {
	padding: 5px 5px;
	max-width: 50px;
	max-height: 50px;
}

.logo ul li h1 {color: #000; text-align: center; font-size: 14px; font-weight: 600; line-height: 25px;}


.logo ul li:last-child {
	float: right;
}

.left {
	float: left;
}

.right {
	float: right;
}
.text-center{text-align:center !important;}
.text-right{text-align:right !important;}
.line-height{line-height:12px !important;}


.hide {
	display: none !important;
}


.side p {
	color: #666;
	font-size: 9px;
	font-weight: normal;
	clear: both;
	text-align: left;
	font-family: Arial, Helvetica, sans-serif;
	line-height: 11px;
	padding: 5px 0px;
	margin: 0px 10px;
}

.challan h1 {
	color: #000;
	font-size: 13px;
	font-weight: 600;
	text-align: center;
	line-height: 13px;
	padding: 5px 0px;
	margin: 0px;
}

.challan h2 {
	color: #000;
	font-size: 11px;
	font-weight: normal;
	text-align: center;
	line-height: 12px;
	padding: 2px 0px;
	margin: 0px;
}

.challan p {
	color: #000;
	font-size: 11px;
	font-weight: normal;
	text-align: center;
	line-height: 12px;
	padding: 2px 0px;
	margin: 0px;
}

table.property {
	width: 100%;
	font-size: 10px;
	border-collapse: collapse;
	overflow-x: auto;
	font-family: 'Arial';
}

table.property td {
	padding: 0px;
	border-top: 1px solid #000;
	vertical-align: top;
}

table.challan_table {
	width: 100%;
	border-collapse: collapse;
	overflow-x: auto;
	font-family: Arial, Helvetica, sans-serif;
}

table.challan_table td {
	font-size: 9px;
	padding: 2px 3px;
	color: #000;
	line-height: 22px;
	border-top: 0px solid #000;
	text-align: left;
	vertical-align: top;
}


table.challan_table td.break-word{word-break: break-all !important;}



table.challan_table th {
	padding: 2px 3px;
	border-top: 0px solid #000;
	line-height: 22px;
	text-align: left;
	vertical-align: top;
}

.border-right {
	border-right: 1px solid #000;
}

.border-bottom {
	border-bottom: 1px solid #000;
}

.margin-left {
	margin-left: 20px;
}

.align-right {
	text-align: right !important;
	padding: 2px 4px !important;
}

@media print {
	@page {size: A4 landscape; margin: 0.5cm;}
	#btnPrint {display: none;}
	#btnCancel {display: none;}
}

/* Please copy the class from below gridtable UL Li */
</style>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>



</head>
<body>
	<input type='button' id='btnPrint' value='Print'
		style='width: 100px; position: fixed; bottom: 10px; left: 47%; z-index: 111;'
		onclick='window.print()' />
	<input type='button' id='btnCancel' value='Cancel'
		style='width: 100px; position: fixed; bottom: 10px; left: 55%; z-index: 111;'
		onclick='window.close()' />
	<div class="wrapper">


		<div class="side">
			<div class="logo clear">
				<ul>
					<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
						<c:set var="parts" value="${fn:split(logo, '*')}" />
						<c:if test="${parts[1] eq '1'}"><li><img src="./${parts[0]}" class="left" alt="Patna  Municipal Corporation"></li></c:if>
					</c:forEach>
					<li><div class="header1">
						<h1>${dto.orgName}</h1>
							<h3>${dto.lPaymentForRti}</h3>
							<p>${dto.labelCitizen}</p>
						</div></li>
					<c:forEach items="${userSession.logoImagesList}" var="logo"
						varStatus="status">
						<c:set var="parts" value="${fn:split(logo, '*')}" />
						<c:if test="${parts[1] eq '2'}">
							<li><img src="./${parts[0]}" class="left" alt="Patna  Municipal Corporation"></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="property">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.labelRtiNo}</th>
								<td width="65%" class="break-word">${dto.rtiNo}
								<c:if test="${dto.loiNo ne null && not empty dto.loiNo}">
								/ ${dto.loiNo}
								</c:if>
								</td>
								
							</tr>
							
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							
							<tr>
								<th width="35%">${dto.lDateOfFilling}</th>
								<td width="65%"><c:set var="date1" value="${dto.todate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="35%">${dto.lChallanNo}</th>
								<td width="20%" class="break-word">${dto.challanNo}</td>
								<th width="25%">${dto.lDateOfDeposite}</th>
								<td width="20%"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lName}</th>
								<td width="65%">${dto.name}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lContact}</th>
								<td width="65%">${dto.contact}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lEmail}</th>
								<td width="65%">${dto.email}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lAmountPayable}</th>
								<td width="65%">${dto.amount}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="35%" class="line-height">${dto.lAmountInWords}</th>
								<td width="65%">${dto.amountInWords}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.ipaymentMode}</th>
								<td width="65%">${dto.paymentModeValue}</td>
							</tr>
						</table>
					</td>
				</tr>



				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">

							<tr>
								<th>${dto.ipaymentText}</th>
							</tr>
						</table>
					</td>
				</tr>


				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								<th width="35%">${dto.lcd}</th>
								<td width="65%">${dto.ddOrPpnumber}</td>
								
							</tr>
						</table>
					</td>
				</tr>
				
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								
								<th width="35%">${dto.lcdd}</th>
								<td width="65%">${dto.ddOrPpDate}</td>
							</tr>
						</table>
					</td>
				</tr>
				
				
				
				
				
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="35%">${dto.lBankName}</th>
								<td width="65%">${dto.bankName}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="35%">${dto.lBranch}</th>
								<td width="65%">${dto.branch}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="35%">${dto.lbankaccId}</th>
								<td width="65%">${dto.bankAccId}</td>
							</tr>

						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">

							<tr>
								<th width="35%">${dto.lTransactionId}</th>
								<td width="65%"></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								<th width="40%"><br/><br/><br/><br/>${dto.lSignatureStamp}</th>
								<td width="60%"></td>
							</tr>
							<tr>
								<th width="50%">${dto.lAR}</th>
								<th width="50%" align="right" class="text-right">${dto.lSignOfDepositer}</th>
							</tr>
						</table>
					</td>
				</tr>

				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="50%">${dto.lvalidDate}</th>
								<th width="50%"><c:set var="date1" value="${dto.validDate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></th>
							</tr>
							<!-- Defect #80293 -->
							<%-- <tr>
								<th colspan="2" width="100%" align="center" class="text-center">${dto.lwebSiteLink}</th>
							</tr> --%>
						</table>
					</td>
				</tr>
			</table>
		</div>



	<!-- ################################################################ -->
			<div class="side last">
			<div class="logo clear">
				<ul>
					<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
						<c:set var="parts" value="${fn:split(logo, '*')}" />
						<c:if test="${parts[1] eq '1'}"><li><img src="./${parts[0]}" class="left" alt="Patna  Municipal Corporation"></li></c:if>
					</c:forEach>
					<li><div class="header1">
						<h1>${dto.orgName}</h1>
							<h3>${dto.lPaymentForRti}</h3>
							<p>${dto.labelBank}</p>
						</div></li>
					<c:forEach items="${userSession.logoImagesList}" var="logo"
						varStatus="status">
						<c:set var="parts" value="${fn:split(logo, '*')}" />
						<c:if test="${parts[1] eq '2'}">
							<li><img src="./${parts[0]}" class="left" alt="Patna  Municipal Corporation"></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="property">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.labelRtiNo}</th>
								<td width="65%" class="break-word">${dto.rtiNo}
								<c:if test="${dto.loiNo ne null && not empty dto.loiNo}">
								/ ${dto.loiNo}
								</c:if>
								</td>
								
							</tr>
							
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							
							<tr>
								<th width="35%">${dto.lDateOfFilling}</th>
								<td width="65%"><c:set var="date1" value="${dto.todate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lChallanNo}</th>
								<td width="20%" class="break-word">${dto.challanNo}</td>
								<th width="25%">${dto.lDateOfDeposite}</th>
								<td width="20%"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lName}</th>
								<td width="65%">${dto.name}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lContact}</th>
								<td width="65%">${dto.contact}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lEmail}</th>
								<td width="65%">${dto.email}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lAmountPayable}</th>
								<td width="65%">${dto.amount}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="35%" class="line-height">${dto.lAmountInWords}</th>
								<td width="65%">${dto.amountInWords}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.ipaymentMode}</th>
								<td width="65%">${dto.paymentModeValue}</td>
							</tr>
						</table>
					</td>
				</tr>



				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">

							<tr>
								<th>${dto.ipaymentText}</th>
							</tr>
						</table>
					</td>
				</tr>


				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								<th width="35%">${dto.lcd}</th>
								<td width="65%">${dto.ddOrPpnumber}</td>
								
							</tr>
						</table>
					</td>
				</tr>
				
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								
								<th width="35%">${dto.lcdd}</th>
								<td width="65%">${dto.ddOrPpDate}</td>
							</tr>
						</table>
					</td>
				</tr>
				
				
				
				
				
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="35%">${dto.lBankName}</th>
								<td width="65%">${dto.bankName}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="35%">${dto.lBranch}</th>
								<td width="65%">${dto.branch}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="35%">${dto.lbankaccId}</th>
								<td width="65%">${dto.bankAccId}</td>
							</tr>

						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">

							<tr>
								<th width="35%">${dto.lTransactionId}</th>
								<td width="65%"></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								<th width="40%"><br/><br/><br/><br/>${dto.lSignatureStamp}</th>
								<td width="60%"></td>
							</tr>
							<tr>
								<th width="50%">${dto.lAR}</th>
								<th width="50%" align="right" class="text-right">${dto.lSignOfDepositer}</th>
							</tr>
						</table>
					</td>
				</tr>

				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="50%">${dto.lvalidDate}</th>
								<th width="50%"><c:set var="date1" value="${dto.validDate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></th>
							</tr>
							<tr>
								<th colspan="2" width="100%" align="center" class="text-center">${dto.lwebSiteLink}</th>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		
		
		<div class="side last">
			<div class="logo clear">
				<ul>
					<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
						<c:set var="parts" value="${fn:split(logo, '*')}" />
						<c:if test="${parts[1] eq '1'}"><li><img src="./${parts[0]}" class="left" alt="Patna  Municipal Corporation"></li></c:if>
					</c:forEach>
					<li><div class="header1">
						<h1>${dto.orgName}</h1>
							<h3>${dto.lPaymentForRti}</h3>
							<p>${dto.labelMun}</p>
						</div></li>
					<c:forEach items="${userSession.logoImagesList}" var="logo"
						varStatus="status">
						<c:set var="parts" value="${fn:split(logo, '*')}" />
						<c:if test="${parts[1] eq '2'}">
							<li><img src="./${parts[0]}" class="left" alt="Patna  Municipal Corporation"></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="property">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.labelRtiNo}</th>
								<td width="65%" class="break-word">${dto.rtiNo}
								<c:if test="${dto.loiNo ne null && not empty dto.loiNo}">
								/ ${dto.loiNo}
								</c:if>
								</td>
								
							</tr>
							
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							
							<tr>
								<th width="35%">${dto.lDateOfFilling}</th>
								<td width="65%"><c:set var="date1" value="${dto.todate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lChallanNo}</th>
								<td width="20%" class="break-word">${dto.challanNo}</td>
								<th width="25%">${dto.lDateOfDeposite}</th>
								<td width="20%"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lName}</th>
								<td width="65%">${dto.name}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lContact}</th>
								<td width="65%">${dto.contact}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lEmail}</th>
								<td width="65%">${dto.email}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.lAmountPayable}</th>
								<td width="65%">${dto.amount}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="35%" class="line-height">${dto.lAmountInWords}</th>
								<td width="65%">${dto.amountInWords}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="35%">${dto.ipaymentMode}</th>
								<td width="65%">${dto.paymentModeValue}</td>
							</tr>
						</table>
					</td>
				</tr>



				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">

							<tr>
								<th>${dto.ipaymentText}</th>
							</tr>
						</table>
					</td>
				</tr>


				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								<th width="35%">${dto.lcd}</th>
								<td width="65%">${dto.ddOrPpnumber}</td>
								
							</tr>
						</table>
					</td>
				</tr>
				
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								
								<th width="35%">${dto.lcdd}</th>
								<td width="65%">${dto.ddOrPpDate}</td>
							</tr>
						</table>
					</td>
				</tr>
				
				
				
				
				
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="35%">${dto.lBankName}</th>
								<td width="65%">${dto.bankName}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="35%">${dto.lBranch}</th>
								<td width="65%">${dto.branch}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="35%">${dto.lbankaccId}</th>
								<td width="65%">${dto.bankAccId}</td>
							</tr>

						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">

							<tr>
								<th width="35%">${dto.lTransactionId}</th>
								<td width="65%"></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="challan_table">
							<tr>
								<th width="40%"><br/><br/><br/><br/>${dto.lSignatureStamp}</th>
								<td width="60%"></td>
							</tr>
							<tr>
								<th width="50%">${dto.lAR}</th>
								<th width="50%" align="right" class="text-right">${dto.lSignOfDepositer}</th>
							</tr>
						</table>
					</td>
				</tr>

				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="challan_table">
							<tr>
								<th width="50%">${dto.lvalidDate}</th>
								<th width="50%"><c:set var="date1" value="${dto.validDate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></th>
							</tr>
							<!-- Defect #80293 -->
							<%-- <tr>
								<th colspan="2" width="100%" align="center" class="text-center">${dto.lwebSiteLink}</th>
							</tr> --%>
						</table>
					</td>
				</tr>
			</table>
		</div>
</div>
</body>
</html>
