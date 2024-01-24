<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-report.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script>
	$(function() {

		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});

		var count = $("#report-table thead th").length;
		$('#report-table tfoot tr th.ts-pager').attr('colspan', count);

	});
</script>


<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="report-print">
		<div class="widget-content padding">
			<form:form action="" method="get">
				<div class="row">
					  <div class="col-xs-3"><img src="${userSession.orgLogoPath}" width="80"></div>
					<div class="col-xs-6 text-center">
						<c:choose>
							<c:when test="${userSession.languageId eq 1}">
								<h3 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h3>
							</c:when>
							<c:otherwise>
								<h3 class="margin-bottom-0">${userSession.organisation.oNlsOrgnameMar}</h3>
							</c:otherwise>
						</c:choose>
						<p class="excel-title">${command.complaintReport.title}</p>
					</div>
					  <!-- D#127292 -->
					<c:choose>
						<c:when test="${command.kdmcEnv eq 'Y'}">
							<div class="col-xs-3 text-right"></div>
						</c:when>
						<c:otherwise>
							<div class="col-xs-3 text-right"><img src="${userSession.orgLogoPath}" width="80"></div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="row margin-top-50">
					<div class="container">
						<div class="pull-left">
							<spring:message code="care.reports.fromDate" text="From Date" />
							: ${command.complaintReport.fromDate}
						</div>
						<div class="pull-right">
							<spring:message code="care.reports.toDate" text="To Date" />
							: ${command.complaintReport.toDate}
						</div>
					</div>
				</div>
				<div class="row clear">
					<div class="container">
						<div class="pull-left">
							<spring:message code="care.reports.department" text="Department" />
							: ${command.complaintReport.departmentName}
						</div>
						<div class="pull-right">
							<spring:message code="care.reports.generatedDate"
								text="Generated Date" />
							: ${command.complaintReport.dateTime}
						</div>
					</div>
				</div>


				<div id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-bordered table-condensed tablesorter"
							id="report-table">
							<thead>
								<tr>

									<th rowspan="2" class="text-center" style="vertical-align: middle;">
									<spring:message code="care.reports.SrNo" text="Sr. No."/></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.reports.tokenNo" text="Complaint Number" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.reports.department" text="Department" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.Dept.reports.zone" text="Ward karyalaya" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.Dept.reports.ward" text="Ward " /></th>
									<th class="text-center" width="10%" data-sortinitialorder="asc"><spring:message
											code="care.reports.complaintType" text="Complaint Type" /></th>
									<th class="text-center" width="10%" data-sortinitialorder="asc"><spring:message
											code="care.reports.complaintDesc"
											text="Complaint Description" /></th>
									<%-- 
									<th class="text-center" width="5%" data-sortinitialorder="asc"><spring:message
											code="care.reports.daysAssignedForResolution"
											text="No. of Days assigned for resolution" /></th> --%>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.reports.dateOfRequest" text="Date of Request" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.Dept.reports.applicantName" text="Applicant Name" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.Dept.reports.address" text="Address" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.Dept.reports.mobileNumber" text="Mobile Number" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.Dept.reports.email" text="Email Id" /></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.reports.status" text="Complaint Status" /></th>




								</tr>
							</thead>
							<tfoot>
								<tr class="print-remove">
									<th class="ts-pager form-horizontal">
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
								<c:forEach items="${command.complaintReport.complaints}"
									var="complaint" varStatus="status">
									<tr>
										<td class="text-center">${status.index + 1}</td>
										<td data-sortinitialorder="desc"><c:out
												value="${complaint.complaintId}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.departmentName}"></c:out></td>

										<td style="text-align: center;"><c:out
												value="${complaint.careWardNoEng}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.careWardNoEng1}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.comlaintSubType}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.complaintDesc}"></c:out></td>
										<%-- 	<td style="text-align: center;"><c:out value="${complaint.slaDuration}"></c:out></td> --%>
										<%-- <td style="text-align: center;"><c:out
												value="${complaint.numberOfDay}"></c:out></td> --%>
										<td style="text-align: center;"><c:out
												value="${complaint.dateOfRequest}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.apmName}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.locNameEng}"></c:out> <c:out
												value="${complaint.pincode}"></c:out></td>

										<td style="text-align: center;"><c:out
												value="${complaint.apaMobilno}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.apaEmail}"></c:out></td>
										<td style="text-align: center;"><c:out
												value="${complaint.status}"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>

					</div>
				</div>
				<div class="text-center margin-top-10 remove-btn">
					<button onclick="PrintDiv('${command.complaintReport.title}');"
						class="btn btn-success hidden-print" type="button">
						<i class="fa fa-print"></i>
						<spring:message code="care.receipt.print" text="Print" />
					</button>
					<button type="button" class="btn btn-warning hidden-print"
						onclick="javascript:openRelatedForm('GrievanceReport.html?grievanceServiceWise','this');">
						<spring:message code="care.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
