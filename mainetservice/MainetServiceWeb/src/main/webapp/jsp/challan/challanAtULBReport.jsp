<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="dto" value="${command.challanDTO}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<style type="text/css">
.wrapper {
	position: relative;
	width: 1124px;
	margin: auto;
}

.side {
	position: relative;
	float: left;
	width: 550px;
	border: solid 1px #000;
	font-family: arial;
	margin: 5px 5px 40px 5px;
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
	width: 400px;
	color: #000;
	margin: auto;
}

.logo ul li .header1 h1 {
	font-size: 15px;
	font-weight: bold;
	line-height: 18px;
	padding: 3px 0px;
	margin: 0px;
	text-align: center;
}

.logo ul li .header1 h3 {
	font-size: 13px;
	font-weight: normal;
	line-height: 15px;
	padding: 1px 0px;
	margin: 0px;
	text-align: center;
}

.logo ul li .header1 p {
	font-size: 12px;
	text-align: center;
	font-weight: normal;
	padding: 4px 0px;
	line-height: 16px;
}

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

.logo ul li h1 {
	color: #000;
	text-align: center;
	font-size: 14px;
	font-weight: 600;
	line-height: 25px;
}

.logo ul li:last-child {
	float: right;
}

.left {
	float: left;
}

.right {
	float: right;
}

.text-center {
	text-align: center !important;
}

.text-right {
	text-align: right !important;
}

.hide {
	display: none !important;
}

/* .side h3 { */
/* 	color: #000; */
/* 	width: 48%; */
/* 	font-size: 11px; */
/* 	font-weight: 600; */
/* 	text-align: left; */
/* 	font-family: Arial, Helvetica, sans-serif; */
/* 	line-height: 13px; */
/* 	padding: 5px 0px; */
/* 	margin: 10px 0px 0px 10px; */
/* } */
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

.addNote {
	border-bottom: 1px solid #123456;
}

table.challan_table td {
	font-size: 15px;
	padding: 2px 3px;
	color: #000;
	line-height: 22px;
	border-top: 0px solid #000;
	text-align: left;
	vertical-align: top;
}

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
	@page {
		size: A4 landscape;
		margin: 0.05cm;
	}
	#btnPrint {
		display: none;
	}
	#btnCancel {
		display: none;
	}
	 html, body {
      height:100vh; 
      margin: 0 !important; 
      padding: 0 !important;
      overflow: hidden;
    }
}
.col-sm-3{width:20%;float:left;padding:5px;}
.col-sm-9{width: 75%;float:left;padding:5px;}
.logo{margin-top:10px;}
.not-logged-avatar{width:75px;height:75px;}
/* Please copy the class from below gridtable UL Li */
.btn-primary {
	background-color: #4A525F;
	border-color: #4A525F;
	color: #FFFFFF;
}
.btn-primary:hover, .btn-primary:focus, .btn-primary:active, .btn-primary.active, .open .dropdown-toggle.btn-primary {
	background-color: #3E444F;
	border-color: #3E444F;
	color: #FFFFFF;
}
.floating-buttons {
	width: 100%;
	text-align: center;
	z-index: 111;
	position: fixed;
    bottom: 0.5rem;
}
.floating-buttons .btn {
	width: 10rem;
}
</style>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>



</head>
<body>
	<div class="floating-buttons">
	<input type='button' id='btnPrint' value='Print' class="btn btn-primary"
		onclick='window.print()' />
	<input type='button' id='btnCancel' value='Cancel' class="btn btn-danger"
		onclick='window.close()' />
	</div>
	<div class="wrapper">

		<div class="side">
		<div class="col-sm-3">
				<img src="${userSession.orgLogoPath}" alt="Organisation Logo"
					class="not-logged-avatar img-responsive">
			</div>
			<div class="col-sm-9 logo clear">
				<ul>
					<li><div class="header1">
							<h1>${dto.orgName}</h1>
							<h3>${dto.lPaymentForRti}</h3>
							<p>${dto.labelCitizen}</p>
						</div></li>
				</ul>
			</div>

			

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="property">
				<tr>
					<td>

						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr class="addNote">
								<th width="50%">${dto.lvalidDate}</th>
								<th width="50%"><c:set var="date1" value="${dto.validDate}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></th>
							</tr>

						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.labelRtiNo}</th>
								<td>${dto.rtiNo}<c:if
										test="${dto.loiNo ne null && not empty dto.loiNo}">
								/ ${dto.loiNo}
								</c:if>
								</td>

								<th width="25%">${dto.lDateOfFilling}</th>
								<td><c:set var="date1" value="${dto.todate}" /> <fmt:formatDate
										pattern="dd/MM/yyyy" value="${date1}" /></td>

							</tr>
						</table>
					</td>
				</tr>
				<%--<tr>
				 	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								
								<th width="25%">${dto.lDateOfFilling}</th>
								<td><c:set var="date1" value="${dto.todate}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></td>
							</tr>
						</table> 
					</td>
				</tr>--%>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lChallanNo}</th>
								<td width="25%">${dto.challanNo}</td>
								<th width="25%">${dto.lDateOfDeposite}</th>
								<td width="25%"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lName}</th>
								<td>${dto.name}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lContact}</th>
								<td width="75%">${dto.contact}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lEmail}</th>
								<td>${dto.email}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lAmountPayable}</th>
								<c:set var="amount" value="${dto.amount}"/>
								<%String amountPayable = String.format("%.2f", pageContext.getAttribute("amount"));%>
								<td><%=amountPayable%></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lAmountInWords}</th>
								<td>${dto.amountInWords}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.ipaymentMode}</th>
								<td>${dto.paymentModeValue}</td>
							</tr>
						</table>
					</td>
				</tr>



				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th>${dto.ipaymentText}</th>
							</tr>
						</table>
					</td>
				</tr>


				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">



							<tr>
								<th width="25%">${dto.lcd}</th>
								<td width="25%">${dto.ddOrPpnumber}</td>
								<th width="25%">${dto.lcdd}</th>
								<td width="25%">${dto.ddOrPpDate}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lBankName}</th>
								<td width="25%">${dto.bankName}</td>
								<th width="25%">${dto.lBranch}</th>
								<td width="25%">${dto.branch}</td>
							</tr>

						</table>
					</td>
				</tr>


				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lbankaccId}</th>
								<td>${dto.bankAccId}</td>
							</tr>

						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lTransactionId}</th>
								<td width="75%"></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="40%"><br /> <br /> <br />${dto.lSignatureStamp}</th>
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
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<%-- <tr>
								<th width="50%">${dto.lvalidDate}</th>
								<th width="50%"><c:set var="date1" value="${dto.validDate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></th>
							</tr> --%>
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
		<tr>
<td colspan="8">
		<div class="header">
		<div class="col-sm-3">
				<img src="${userSession.orgLogoPath}" alt="Organisation Logo"
					class="not-logged-avatar img-responsive">
			</div>
			<div class="col-sm-9 logo">
				<ul>
					<li><div class="header1">
							<h1>${dto.orgName}</h1>
							<h3>${dto.lPaymentForRti}</h3>
							<p>${dto.labelMun}</p>
						</div></li>
				</ul>
			</div>
			
			</div></td></tr>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="property">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr class="addNote">
								<th width="50%">${dto.lvalidDate}</th>
								<th width="50%"><c:set var="date1" value="${dto.validDate}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></th>
							</tr>

						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.labelRtiNo}</th>
								<td>${dto.rtiNo}<c:if
										test="${dto.loiNo ne null && not empty dto.loiNo}">
								/ ${dto.loiNo}
								</c:if>
								</td>


								<th width="25%">${dto.lDateOfFilling}</th>
								<td><c:set var="date1" value="${dto.todate}" /> <fmt:formatDate
										pattern="dd/MM/yyyy" value="${date1}" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<%-- 	<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								
								<th width="25%">${dto.lDateOfFilling}</th>
								<td><c:set var="date1" value="${dto.todate}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></td>
							</tr>
						</table>
					</td>
				</tr> --%>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lChallanNo}</th>
								<td width="25%">${dto.challanNo}</td>
								<th width="25%">${dto.lDateOfDeposite}</th>
								<td width="25%"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lName}</th>
								<td>${dto.name}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lContact}</th>
								<td width="75%">${dto.contact}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lEmail}</th>
								<td>${dto.email}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.lAmountPayable}</th>
								<td><%=amountPayable%></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lAmountInWords}</th>
								<td>${dto.amountInWords}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="25%">${dto.ipaymentMode}</th>
								<td>${dto.paymentModeValue}</td>
							</tr>
						</table>
					</td>
				</tr>



				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th>${dto.ipaymentText}</th>
							</tr>
						</table>
					</td>
				</tr>


				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">



							<tr>
								<th width="25%">${dto.lcd}</th>
								<td width="25%">${dto.ddOrPpnumber}</td>
								<th width="25%">${dto.lcdd}</th>
								<td width="25%">${dto.ddOrPpDate}</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lBankName}</th>
								<td width="25%">${dto.bankName}</td>
								<th width="25%">${dto.lBranch}</th>
								<td width="25%">${dto.branch}</td>
							</tr>

						</table>
					</td>
				</tr>


				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lbankaccId}</th>
								<td>${dto.bankAccId}</td>
							</tr>

						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">

							<tr>
								<th width="25%">${dto.lTransactionId}</th>
								<td width="75%"></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<tr>
								<th width="40%"> <br /> <br /> <br />${dto.lSignatureStamp}</th>
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
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="challan_table">
							<%-- <tr>
								<th width="50%">${dto.lvalidDate}</th>
								<th width="50%"><c:set var="date1" value="${dto.validDate}" /><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></th>
							</tr> --%>
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
