<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
response.setContentType("text/html; charset=utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<style type="text/css">
.side {
	position: relative;
	float: left;
	width : 30%;
	border: solid 1px #000;
	font-family: arial;
	margin: 5px 5px;
}

.text-center {
	text-align: center !important;
}

table.property {
	width: 100%;
	font-size: 10px;
	/* border-collapse: collapse; */
	border-top: 1px solid #000;
	overflow-x: auto;
	font-family: 'Arial';
}

table.property td {
	padding: 0px;
	/* border-top: 1px solid #000; */
	vertical-align: top;
}
.no-top-border{
	border-top: none;
}


</style>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script type="text/javascript">
	function PrintDiv(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>

</head>

<div class="widget">
	<div class="widget-content padding" id="receipt">
		<form action="" method="get" class="form-horizontal">
			<div id="receipt" class="receipt-content">
			<!-- <div id="export-excel"> -->

				<!----------------------------------------- Bank/Treasury Copy Start ------------------------------------------------------->
				<div class="side">
				
					<div class="text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message code="" text="E-CHALLAN" />
						</h2>
					</div>

					<table class="table">
						<tr>
							<td style="text-align: left;"><spring:message code="" text="DDOCode:1321" /></td>
							<td style="text-align: right;"> <spring:message code="" text="Bank/Treasury Copy" /></td>
						</tr>
					</table>
					
					<div class="text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message code="" text="Government of Haryana" />
						</h2>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="10%"><spring:message text="Valid Upto: " /></td> <td>${command.chequeDate}<spring:message text="(Cash)" /></td>
								
							</tr>
							<tr><td width="10%"></td><td>${command.chequeDate}<spring:message text="(Chq./DD)" /></td></tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="20%"><spring:message text="GRNNo:" /></td>
								<td width="30%">${command.transactionId}</td>

								<td width="20%"><spring:message text="Date:" /></td>
								<td width="30%">  <c:set value="${command.paymentDateTime}"
									 var="paymentDateTime">
							       </c:set> 
							       <fmt:formatDate type="date" value="${paymentDateTime}" pattern="dd/MM/yyyy hh:mm a" /></td>
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Office Name:" /></td>
								<td width="50%"><spring:message text="1321-DTCP HR" /></td>
							</tr>
							<tr>
								<td width="50%"><spring:message text="Treasury:" /></td>
								<td width="50%"><spring:message text="Chandigarh" /></td>
							</tr>
							<tr>
								<td width="50%"><spring:message text="Period:" /></td>
								<td width="50%">(${command.finYr})<spring:message text="Yearly" /></td>
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td width="50%" style="font-weight: bold;"><spring:message text="Head of Account:" /></td>
								<td width="50%" style="text-align: right; font-weight: bold;"><spring:message text="Amount" />&#8377</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="0217-60-800-51-51Other Receipts" /></td>
								<td  width="50%" style="text-align: right;"><fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								${amount}</td>
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td style="color: red;"><spring:message
											text="For PNBBank-Challan to be accepted under menu option.VATBRInstitute
													Code-HRGAT Collection Code-HRGAT" />
								</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="50%" style="font-weight: bold;"><spring:message text="PD AcNo"/></td>
								<td style="text-align: width="50%" style="font-weight: bold;">0</td>
							</tr>
						</table>
					</div>
										
					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Deduction Amount:" />&#8377</td>
								<td width="50%" style="text-align: right;"">0</td>
								
							</tr>
							<tr>
								<td width="50%"><spring:message text="Total/Net Amount:" />&#8377</td>
								<td width="50%" style="text-align: right;"><fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								${amount}</td>
							</tr>
							<tr><td width="100%">&#8377 ${command.amountStr}</td></tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td align="center"><spring:message text="Tenderer's Detail" /></td>
							</tr>
						</table>
					</div>					
					
					<div>
						<table class="property">
							<tr>
								<td width="50%" class="no-top-border"><spring:message
										text="GPF/PRANT/TIN/Actt.no/VechicleNo/TaxId:-" /></td>
								<td width="50%"></td>
							</tr>
						</table>
						
						<table class="property">							
							<tr>
								<td width="25%"><spring:message text="PAN No:" /></td>
								<td width="25%"></td>

								<td width="50%"><spring:message text="DTCP HR" /></td>
							</tr>
							<tr>
								<td width="25%"><spring:message text="Tenderer's Name:"/></td>
								<td width="25%"></td>
										
								<td width="50%"><spring:message text="DTCP HR CHD"/></td>
							</tr>
							<tr>
								<td width="25%"><spring:message text="Address:" /></td>
								<td width="25%"></td>

								<td width="50%"><spring:message text="Phone No. 0172-254937" /></td>
							</tr>
							<tr>
								<td width="25%" class="text-left"><spring:message
										text="Particulars:" /></td>
								<td width="15%"></td>

								<td width="50%">
									<spring:message text="Name : " />${command.firstName} 
									<br /> 
									<spring:message text="Case Type : " /> Licence
									<br />
									<spring:message text="Application Type : " />  Grant of Licence(Deficit Fees)
									<br />
									<spring:message text="Charges Type : " /> Licence
Fee(Deficit)  
									<br /> 
									<spring:message text="Application ID : " /> LC-4002A

								</td>
							</tr>
						</table>
						
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Cheque-DD-Details:" /></td>
								<td width="50%" style="text-align: right;">${command.chequeNo}/${command.chequeDate}/${command.bankName}</td>								
							</tr>
							<tr>
								<td width="50%"></td>
								<td width="50%" style="text-align: right; padding-top: 15px;"><spring:message
										text="Depositor Signature" /></td>
							</tr>
						</table>						
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td align="center" style="font-weight: bold;">
									<spring:message text="FOR USE IN RECEIVING BANK" />
								</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td><spring:message text="Bank CIN No:" /></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message text="Payment Date:" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><spring:message text="All PNBBranches" /></td>
							</tr>
							<tr>
								<td><spring:message text="Bank" /></td>
								<td></td>
							</tr>
						</table>
					</div>
								
				</div>
	<!----------------------------------------- Bank/Treasury Copy End ------------------------------------------------------->
	
			
	<!----------------------------------------- Dept Copy Start ------------------------------------------------------->				
				<div class="side">
				
					<div class="text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message code="" text="E-CHALLAN" />
						</h2>
					</div>

					<table class="table">
						<tr>
							<td style="text-align: left;"><spring:message code="" text="DDOCode:1321" /></td>
							<td style="text-align: right;"> <spring:message code="" text="Dept Copy" /></td>
						</tr>
					</table>
					
					<div class="text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message code="" text="Government of Haryana" />
						</h2>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="10%"><spring:message text="Valid Upto: " /></td> <td>${command.chequeDate}<spring:message text="(Cash)" /></td>
								
							</tr>
							<tr><td width="10%"></td><td>${command.chequeDate}<spring:message text="(Chq./DD)" /></td></tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="20%"><spring:message text="GRNNo:" /></td>
								<td width="30%">${command.transactionId}</td>

								<td width="20%"><spring:message text="Date:" /></td>
								<td width="30%">  <c:set value="${command.paymentDateTime}"
									 var="paymentDateTime">
							       </c:set> 
							       <fmt:formatDate type="date" value="${paymentDateTime}" pattern="dd/MM/yyyy hh:mm a" />
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Office Name:" /></td>
								<td width="50%"><spring:message text="1321-DTCP HR" /></td>
							</tr>
							<tr>
								<td width="50%"><spring:message text="Treasury:" /></td>
								<td width="50%"><spring:message text="Chandigarh" /></td>
							</tr>
							<tr>
								<td width="50%"><spring:message text="Period:" /></td>
								<td width="50%">(${command.finYr})<spring:message text="Yearly" /></td>
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td width="50%" style="font-weight: bold;"><spring:message text="Head of Account:" /></td>
								<td width="50%" style="text-align: right; font-weight: bold;"><spring:message text="Amount" />&#8377</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="0217-60-800-51-51Other Receipts" /></td>
								<td  width="50%" style="text-align: right;"><fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								${amount}</td>
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td style="color: red;"><spring:message
											text="For PNBBank-Challan to be accepted under menu option.VATBRInstitute
													Code-HRGAT Collection Code-HRGAT" />
								</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="50%" style="font-weight: bold;"><spring:message text="PD AcNo"/></td>
								<td style="text-align: width="50%" style="font-weight: bold;">0</td>
							</tr>
						</table>
					</div>
										
					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Deduction Amount:" />&#8377</td>
								<td width="50%" style="text-align: right;"">0</td>
								
							</tr>
							<tr>
								<td width="50%"><spring:message text="Total/Net Amount:" />&#8377</td>
								<td width="50%" style="text-align: right;"><fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								${amount}</td>
							</tr>
							<tr><td width="100%">&#8377 ${command.amountStr}</td></tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td align="center"><spring:message text="Tenderer's Detail" /></td>
							</tr>
						</table>
					</div>					
					
					<div>
						<table class="property">
							<tr>
								<td width="50%" class="no-top-border"><spring:message
										text="GPF/PRANT/TIN/Actt.no/VechicleNo/TaxId:-" /></td>
								<td width="50%"></td>
							</tr>
						</table>
						
						<table class="property">							
							<tr>
								<td width="25%"><spring:message text="PAN No:" /></td>
								<td width="25%"></td>

								<td width="50%"><spring:message text="DTCP HR" /></td>
							</tr>
							<tr>
								<td width="25%"><spring:message text="Tenderer's Name:"/></td>
								<td width="25%"></td>
										
								<td width="50%"><spring:message text="DTCP HR CHD"/></td>
							</tr>
							<tr>
								<td width="25%"><spring:message text="Address:" /></td>
								<td width="25%"></td>

								<td width="50%"><spring:message text="Phone No. 0172-254937" /></td>
							</tr>
							<tr>
								<td width="25%" class="text-left"><spring:message
										text="Particulars:" /></td>
								<td width="15%"></td>

								<td width="50%">
									<spring:message text="Name : " />${command.firstName} 
									<br /> 
									<spring:message text="Case Type : " />Licence
									<br />
									<spring:message text="Application Type : " />  Grant of Licence(Deficit Fees)
									<br />
									<spring:message text="Charges Type : " />  Licence Fee(Deficit)  
									<br /> 
									<spring:message text="Application ID : " /> LC-4002A

								</td>
							</tr>
						</table>
						
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Cheque-DD-Details:" /></td>
								<td width="50%" style="text-align: right;">${command.chequeNo}/${command.chequeDate}/${command.bankName} </td>								
							</tr>
							<tr>
								<td width="50%"></td>
								<td width="50%" style="text-align: right; padding-top: 15px;"><spring:message
										text="Depositor Signature" /></td>
							</tr>
						</table>						
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td align="center" style="font-weight: bold;">
									<spring:message text="FOR USE IN RECEIVING BANK" />
								</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td><spring:message text="Bank CIN No:" /></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message text="Payment Date:" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><spring:message text="All PNBBranches" /></td>
							</tr>
							<tr>
								<td><spring:message text="Bank" /></td>
								<td></td>
							</tr>
						</table>
					</div>
								
				</div>
	<!----------------------------------------- Dept Copy End ------------------------------------------------------->				


	<!----------------------------------------- Remitter Copy Start ------------------------------------------------------->				
				<div class="side">
				
					<div class="text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message code="" text="E-CHALLAN" />
						</h2>
					</div>

					<table class="table">
						<tr>
							<td style="text-align: left;"><spring:message code="" text="DDOCode:1321" /></td>
							<td style="text-align: right;"> <spring:message code="" text="Remitter Copy" /></td>
						</tr>
					</table>
					
					<div class="text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message code="" text="Government of Haryana" />
						</h2>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="10%"><spring:message text="Valid Upto: " /></td> <td>${command.chequeDate}<spring:message text="(Cash)" /></td>
								
							</tr>
							<tr><td width="10%"></td><td>${command.chequeDate}<spring:message text="(Chq./DD)" /></td></tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="20%"><spring:message text="GRNNo:" /></td>
								<td width="30%">${command.transactionId}</td>

								<td width="20%"><spring:message text="Date:" /></td>
								<td width="30%">  <c:set value="${command.paymentDateTime}"
									 var="paymentDateTime">
							       </c:set> 
							       <fmt:formatDate type="date" value="${paymentDateTime}" pattern="dd/MM/yyyy hh:mm a" />
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Office Name:" /></td>
								<td width="50%"><spring:message text="1321-DTCP HR" /></td>
							</tr>
							<tr>
								<td width="50%"><spring:message text="Treasury:" /></td>
								<td width="50%"><spring:message text="Chandigarh" /></td>
							</tr>
							<tr>
								<td width="50%"><spring:message text="Period:" /></td>
								<td width="50%">(${command.finYr})<spring:message text="Yearly" /></td>
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td width="50%" style="font-weight: bold;"><spring:message text="Head of Account:" /></td>
								<td width="50%" style="text-align: right; font-weight: bold;"><spring:message text="Amount" />&#8377</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="0217-60-800-51-51Other Receipts" /></td>
								<td  width="50%" style="text-align: right;"><fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								${amount}</td>
							</tr>
						</table>
					</div>

					<div>
						<table class="property">
							<tr>
								<td style="color: red;"><spring:message
											text="For PNBBank-Challan to be accepted under menu option.VATBRInstitute
													Code-HRGAT Collection Code-HRGAT" />
								</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td width="50%" style="font-weight: bold;"><spring:message text="PD AcNo"/></td>
								<td style="text-align: width="50%" style="font-weight: bold;">0</td>
							</tr>
						</table>
					</div>
										
					<div>
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Deduction Amount:" />&#8377</td>
								<td width="50%" style="text-align: right;"">0</td>
								
							</tr>
							<tr>
								<td width="50%"><spring:message text="Total/Net Amount:" />&#8377</td>
								<td width="50%" style="text-align: right;"><fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								${amount}</td>
							</tr>
							<tr><td width="100%">&#8377 ${command.amountStr}</td></tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td align="center"><spring:message text="Tenderer's Detail" /></td>
							</tr>
						</table>
					</div>					
					
					<div>
						<table class="property">
							<tr>
								<td width="50%" class="no-top-border"><spring:message
										text="GPF/PRANT/TIN/Actt.no/VechicleNo/TaxId:-" /></td>
								<td width="50%"></td>
							</tr>
						</table>
						
						<table class="property">							
							<tr>
								<td width="25%"><spring:message text="PAN No:" /></td>
								<td width="25%"></td>

								<td width="50%"><spring:message text="DTCP HR" /></td>
							</tr>
							<tr>
								<td width="25%"><spring:message text="Tenderer's Name:"/></td>
								<td width="25%"></td>
										
								<td width="50%"><spring:message text="DTCP HR CHD"/></td>
							</tr>
							<tr>
								<td width="25%"><spring:message text="Address:" /></td>
								<td width="25%"></td>

								<td width="50%"><spring:message text="Phone No. 0172-254937" /></td>
							</tr>
							<tr>
								<td width="25%" class="text-left"><spring:message
										text="Particulars:" /></td>
								<td width="15%"></td>

								<td width="50%">
									<spring:message text="Name : " />${command.firstName} 
									<br /> 
									<spring:message text="Case Type : " /> Licence
									<br />
									<spring:message text="Application Type : " />  Grant of Licence(Deficit Fees)
									<br />
									<spring:message text="Charges Type : " /> Licence Fee(Deficit)  
									<br /> 
									<spring:message text="Application ID : " /> LC-4002A

								</td>
							</tr>
						</table>
						
						<table class="property">
							<tr>
								<td width="50%"><spring:message text="Cheque-DD-Details:" /></td>
								<td width="50%" style="text-align: right;"> ${command.chequeNo}/${command.chequeDate}/${command.bankName}</td>								
							</tr>
							<tr>
								<td width="50%"></td>
								<td width="50%" style="text-align: right; padding-top: 15px;"><spring:message
										text="Depositor Signature" /></td>
							</tr>
						</table>						
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td align="center" style="font-weight: bold;">
									<spring:message text="FOR USE IN RECEIVING BANK" />
								</td>
							</tr>
						</table>
					</div>
					
					<div>
						<table class="property">
							<tr>
								<td><spring:message text="Bank CIN No:" /></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message text="Payment Date:" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><spring:message text="All PNBBranches" /></td>
							</tr>
							<tr>
								<td><spring:message text="Bank" /></td>
								<td></td>
							</tr>
						</table>
					</div>
								
				</div>
	<!----------------------------------------- Remitter Copy End ------------------------------------------------------->				

			</div>
			
			
			<style>
				@media print {
					@page {
						margin: 0 10px;
					}
					.receipt-content {
						overflow: hidden;
						padding: 10px !important;
						margin-top: 10px !important;
					}
					.border-black {
						border: 1px solid #000;
					}
					.overflow-hidden {
						overflow: hidden !important;
					}
					.receipt-content .ulb-name {
						margin-bottom: 0 !important;
					}
					.side {
						position: relative;
						float: left;
						width : 30%;
						border: solid 1px #000;
						font-family: arial;
						margin: 5px 5px;
					}
					table.property {
						width: 100%;
						font-size: 10px;
						border-top: 1px solid #000;
						overflow-x: auto;
						font-family: 'Arial';
					}
										
				}
			</style>
			
		</form>		
	</div>
</div>

<div class="text-center hidden-print padding-10 ">
	<button onclick="PrintDiv('receipt');"
		class="btn btn-primary hidden-print" type="button">
		<i class="fa fa-print"></i>
		<spring:message code="material.management.print" text="Print" />
	</button>
	<button type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="bckBtn" text="Back" />
	</button>
</div>

