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
	src="js/solid_waste_management/report/AllFineLogBookReport.js"></script>
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
				<spring:message code="" text="Desludging Report" />
			</div>
			<div class="widget-content padding">
				<form:form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-xs-2">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-8 col-xs-8  text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								<c:if test="${userSession.languageId eq 1}">
									 ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
         							 ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
								<spring:message code="" text="Desludging Report" />
							</h2>
						</div>
						<div class="col-xs-2">
							<p>
								<spring:message code="swm.day.wise.month.report.date"
									text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="swm.day.wise.month.report.time"
									text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="padding-5 clear">&nbsp;</div>
						<div id="export-excel">
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th class="text-center"><spring:message code=""
												text="Date" /></th>

										<th class="text-center"><spring:message code=""
												text="Vehicle Driver Name" /></th>

										<th class="text-center"><spring:message code=""
												text="VehicleNo." /></th>


										<th class="text-center"><spring:message code=""
												text="Vehicle Driver Mobile No" /></th>


										<th class="text-center"><spring:message code=""
												text="Applicants Name." /></th>

										<th class="text-center"><spring:message code=""
												text="Applicants Address" /></th>

										<th class="text-center"><spring:message code=""
												text="Applicants Mobile No." /></th>

										<th class="text-center"><spring:message code=""
												text="Total No Of Trips." /></th>


										<th class="text-center"><spring:message code=""
												text="Total Amount" /></th>

										<th class="text-center"><spring:message code=""
												text="Receipt No." /></th>
										<th class="text-center"><spring:message code=""
												text="Dumping Area Address" /></th>
										<th class="text-center"><spring:message code=""
												text="Nidan RequestNo." /></th>
										<th class="text-center"><spring:message code=""
												text="Sanitation Worker Name" /></th>
										<th class="text-center"><spring:message code=""
												text="Total Quantity" /></th>
										<th class="text-center"><spring:message code=""
												text="Plant Operator Name" /></th>
										<th class="text-center"><spring:message code=""
												text="Solid Waste" /></th>



									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="16" class="ts-pager form-horizontal">
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
												<option value="all"><spring:message
														code="swm.report.all.records" text="All Records" />
												</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<tbody>
									<tr>
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
								</tbody>
							</table>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('${cheque.dishonor.register}');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>
							<spring:message code="solid.waste.print" text="Print" />
						</button>
						<button type="button" class="btn btn-danger" onclick="back();">
							<spring:message code="solid.waste.cancel" text="Cancel" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>