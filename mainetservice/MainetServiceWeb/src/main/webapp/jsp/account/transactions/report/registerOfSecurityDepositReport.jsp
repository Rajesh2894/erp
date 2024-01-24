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
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
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
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">Register of Security Deposit</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="form-group">
						<div class="col-xs-12 text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">${userSession.getCurrent().organisation.ONlsOrgname}</h3>
							<%-- <p>
							<spring:message code="register.security.deposit.form"
								text="Form No.51M (29B)" /> --%>
							<!-- <br> -->
							<strong class="excel-title"><spring:message code="register.security.deposit"
									text="Register of Security deposits" /></strong>
							<%-- <br> <em><spring:message
									code="register.security.deposit.rule"
									text="(See Rule No. 81,183,187)" /></em>
						</p> --%>
						</div>
					</div>
					<!-- 	<div class="padding-5 clear">&nbsp;</div> -->
					<div class="table-responsive">
						<table class="table table-bordered table-condensed">
							<tbody>
								<tr>
									<th><spring:message code="register.security.deposit.srno"
											text="Sr No" /></th>
									<th><spring:message
											code="register.security.deposit.numberdate"
											text="Number and Date of order under which deposited" /></th>
									<th><spring:message code="register.security.deposit.date"
											text="Date of Deposit" /></th>
									<th><spring:message
											code="register.security.deposit.nameaddress"
											text="Name and address of Depositor" /></th>
									<th><spring:message
											code="register.security.deposit.purpose"
											text="Purpose of deposit" /></th>
									<th><spring:message
											code="register.security.deposit.nature"
											text="Nature of Instrument deposited" /></th>
									<th><spring:message code="register.security.deposit.value"
											text="Value of Deposit (Rs.)" /></th>
									<th><spring:message
											code="register.security.deposit.details"
											text="Details of instrument deposited" /></th>
									<th><spring:message
											code="register.security.deposit.person"
											text="Person in whose custody the instrument is placed" /></th>
									<th><spring:message
											code="register.security.deposit.sanction"
											text="Number and Date of order sanctioning return or forfeiture of deposit" /></th>
									<th><spring:message
											code="register.security.deposit.actualdate"
											text="Actual Date of return or forefeiture" /></th>
									<th><spring:message
											code="register.security.deposit.valueinstrument"
											text="Value of Instrument returned (Rs.)" /></th>
									<th><spring:message
											code="register.security.deposit.instrumentreturn"
											text="Details of instrument returned" /></th>
									<th><spring:message
											code="register.security.deposit.encashment"
											text="Encashment or realisable value of instrument on date of forfeiture" /></th>
									<th><spring:message
											code="register.security.deposit.balance"
											text="Balance at the close of the year (Rs.)" /></th>
									<th><spring:message
											code="register.security.deposit.remarks"
											text="Remarks and signature of Authorised Officer" /></th>
								</tr>
								<tr class="text-center">
									<td>1</td>
									<td>2</td>
									<td>3</td>
									<td>4</td>
									<td>5</td>
									<td>6</td>
									<td>7</td>
									<td>8</td>
									<td>9</td>
									<td>10</td>
									<td>11</td>
									<td>12</td>
									<td>13</td>
									<td>14</td>
									<td>15</td>
									<td>16</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<th colspan="6" class="text-right">Total</th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="printdiv('receipt');"
							class="btn btn-primary hidden-print" title="Print">
							<i class="fa fa-print"></i> Print
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AccountBudgetReports.html'" title="Back">
							<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
							<spring:message code="account.bankmaster.back" text="Back" />
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>