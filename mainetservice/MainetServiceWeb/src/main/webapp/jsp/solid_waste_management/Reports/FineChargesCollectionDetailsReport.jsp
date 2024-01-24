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
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>Fine/Charges Collection Detail</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-xs-2">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-8 col-xs-8  text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								${ userSession.getCurrent().organisation.ONlsOrgname}<br>Fine/Charges
								Collection Detail
							</h2>
						</div>
						<div class="col-xs-2">
							<p>
								Date:
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="clearfix padding-10"></div>

						<div class="form-group">
							<label for="select-1479372680758" class="col-xs-2 control-label">From
								Date</label>
							<div class="col-xs-4">
								<p class="padding-5">04/03/2018</p>
							</div>
							<label for="select-1479372680758" class="col-xs-2 control-label">To
								Date</label>
							<div class="col-xs-4">
								<p class="padding-5">06/03/2018</p>
							</div>
							<div class="padding-5 clear">&nbsp;</div>
							<div class="overflow-visible">
								<div id="export-excel">
									<table class="table table-bordered table-condensed">
										<thead>
											<tr>
												<th>Receipt Date</th>
												<th>Receipt No.</th>
												<th>Fine/Charge Type</th>
												<th>Name of Person</th>
												<th>Photo of Person</th>
												<th>Inspector Name</th>
												<th>Amount Collected</th>
											</tr>
										</thead>
										<tfoot>
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
														<option selected="selected" value="10"
															class="form-control">10</option>
														<option value="20">20</option>
														<option value="30">30</option>
														<option value="all">All Records</option>
												</select> <select class="pagenum input-mini form-control"
													title="Select page number"></select>
												</th>
											</tr>
										</tfoot>
										<tbody>
											<tr class="text-center">
												<td>1</td>
												<td>2</td>
												<td>3</td>
												<td>4</td>
												<td>5</td>
												<td>6</td>
												<td>7</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('${cheque.dishonor.register}');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i> Print
						</button>
						<button type="button" class="btn btn-danger" onclick="back();">
							<spring:message code="solid.waste.cancel" text="Cancel" />
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>