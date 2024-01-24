<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script src="js/masters/vendorMaster/vendorMaster.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function proceed() {
		window.location.href = "javascript:openRelatedForm('Vendormaster.html');";
	}

	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>

<script language="javascript">
	function printhiv(printpage) {

		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}

	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.vendor.master" text="Vendor Master" />
			</h2>
			<apptags:helpDoc url="Vendormaster.html"
				helpDocRefURL="Vendormaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
		<div id = "vendorReport">
			<form:form class="form-horizontal" modelAttribute="tbAcVendormaster"
				cssClass="form-horizontal" action="Vendormaster.html"
				id="vendorMasterReport">
				<div
					class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-2 text-center">
					<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
									                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if></h3>
					<strong><spring:message code="accounts.vendor.master.report"
							text="Vendor Register Report" /></strong>
				</div>
				<div class="col-sm-2 margin-bottom-20">
					<p>
						<spring:message code="accounts.date" text="Date:"/><fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
					<br><spring:message code="acounts.time" text="Time:"/><fmt:formatDate value="${date}" pattern="hh:mm a" />
					</p>
				</div>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th class="text-center"><spring:message code="account.srno" text="Sr. No."/></th>
							<th class="text-center"><spring:message code="accounts.vendormaster.report.vendorName" text="Name Of The Vendor"/></th>
							<th class="text-center"><spring:message code="accounts.vendormaster.address" text="Address"/></th>
							<th class="text-center"><spring:message code="accounts.vendormaster.panNo" text="PAN No."/></th>
							<th class="text-center"><spring:message code="accounts.vendormaster.report.gstno" text="GST No."/></th>
							<th class="text-center"><spring:message code="bank.master.accountNo" text="Bank Account No."/></th>
							<th class="text-center"><spring:message code="accounts.master.status" text="Status"/></th>
						</tr>
					</thead>
					<tfoot class="tfoot">
						<tr>
							<th colspan="7" class="ts-pager form-horizontal">
								<div class="btn-group">
									<button type="button" class="btn first">
										<i class="fa fa-step-backward" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn prev">
										<i class="fa fa-arrow-left" aria-hidden="true"></i>
									</button>
								</div> <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
								<div class="btn-group">
									<button type="button" class="btn next">
										<i class="fa fa-arrow-right" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn last">
										<i class="fa fa-step-forward" aria-hidden="true"></i>
									</button>
								</div> <select class="pagesize input-mini form-control"
								title="Select page size">
									<option selected="selected" value="10" class="form-control">10</option>
									<option value="20">20</option>
									<option value="30">30</option>
									<option value="all">All Records</option>
							</select> <select class="pagenum input-mini form-control"
								title="Select page number"></select>
							</th>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${list}" var="vendorReport" varStatus="index">
							<tr>
								<td>${index.count}</td>
								<td>${vendorReport.vmVendorname}</td>
								<td>${vendorReport.vmVendoradd}</td>
								<td>${vendorReport.vmPanNumber}</td>
								<td>${vendorReport.vmGstNo}</td>
								<td>${vendorReport.bankaccountnumber}</td>
								<td>${vendorReport.vmCpdStatusDesc}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="text-center margin-top-15 hidden-print">
					<button  onclick="printContent('vendorReport')"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
					</button>	
					<input type="button" class="btn btn-danger"
						onclick="javascript:openRelatedForm('Vendormaster.html');"
						value="<spring:message code="account.bankmaster.back" text="Back"/>" />	
				</div>
			</form:form>
			<style>
			@media print{
				.tfoot{
					display: none;
				}
				@page {
					margin: 15px;
				} 
			}
		  </style>	
		</div>
		</div>
	</div>
</div>
