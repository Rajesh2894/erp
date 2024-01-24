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
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/report/fineChargesCollectionReport.js"></script>
<script type="text/javascript">
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
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>Fine/Charges Collection Summary</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="col-sm-2">
						<h1>
							<img width="80" src="${userSession.orgLogoPath}">
						</h1>
					</div>
					<div class="col-xs-8 col-sm-8  text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							ULB Name<br>Fine/Charges Collection Summary
						</h2>
					</div>
					<div class="col-sm-2">
						<p>
							Date:06/03/2017<br>Time:4:49 PM
						</p>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="clearfix padding-10"></div>

					<div class="form-group">
						<label for="select-1479372680758" class="col-sm-2 control-label">From
							Date</label>
						<div class="col-sm-4">
							<p class="padding-5">04/03/2018</p>
						</div>
						<label for="select-1479372680758" class="col-sm-2 control-label">To
							Date</label>
						<div class="col-sm-4">
							<p class="padding-5">06/03/2018</p>
						</div>

						<table class="table table-bordered table-condensed">
							<thead>
								<tr>
									<th rowspan="2">Date</th>
									<th colspan="2">OD Fine Received</th>
									<th colspan="2">Urinal Fine Received</th>
									<th colspan="2">Litter Fine Received</th>
									<th colspan="2">PPE Fine Received</th>
									<th colspan="2">User Charge Received</th>
									<th colspan="2">Total Collection Received</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th colspan="15" class="ts-pager form-horizontal">
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
								<tr>
									<th></th>
									<th>No. of Receipts</th>
									<th>Amount Received</th>

									<th>No. of Receipts</th>
									<th>Amount Received</th>

									<th>No. of Receipts</th>
									<th>Amount Received</th>

									<th>No. of Receipts</th>
									<th>Amount Received</th>

									<th>No. of Receipts</th>
									<th>Amount Received</th>

									<th>No. of Receipts</th>
									<th>Amount Received</th>
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
								</tr>
							</tbody>
						</table>

						<div class="text-center hidden-print padding-10">
							<button onclick="PrintDiv('${cheque.dishonor.register}');"
								class="btn btn-success hidden-print" type="button">
								<i class="fa fa-print"></i> Print
							</button>
							<button type="button" class="btn btn-danger" onclick="back();">
								<spring:message code="" text="Cancel" />
							</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

