<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
							:
							<fmt:formatDate pattern="dd-MM-yyyy"
								value="${command.careReportRequest.fromDate}" />
						</div>
						<div class="pull-right">
							<spring:message code="care.reports.toDate" text="To Date" />
							:
							<fmt:formatDate pattern="dd-MM-yyyy"
								value="${command.careReportRequest.toDate}" />
						</div>
					</div>
				</div>
				<div class="row clear">
					<div class="container">
						<div class="pull-right">
							<spring:message code="care.reports.generatedDate"
								text="Generated Date" />
							: <span class="text-bold"><fmt:formatDate
									value="<%=new java.util.Date()%>" pattern="dd-MM-yyyy" /></span>
						</div>
					</div>
				</div>


				<div id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-bordered table-condensed tablesorter"
							id="report-table">
							<thead>
								<tr>
									<th  class="text-center" width="4%">
									<spring:message code="care.reports.SrNo" text="Sr. No."/></th>
									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.Dept.reports.mobileNumber" text="Mobile Number" /></th>
									<c:if
										test="${command.careReportRequest.alertType=='S' || command.careReportRequest.alertType=='B'}">
										<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
												code="care.msg.text" text="Sms Text" /></th>
									</c:if>

									<c:if
										test="${command.careReportRequest.alertType=='E' || command.careReportRequest.alertType=='B'}">

										<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
												code="care.reports.emailId" text="Email Id" /></th>
										<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
												code="care.mail.subject" text="Mail Subject" /></th>
									</c:if>

									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.reports.sentDate" text="Sent Date" /></th>

									<th class="text-center" width="8%" data-sortinitialorder="asc"><spring:message
											code="care.reports.status" text="Status" /></th>

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
								<c:forEach items="${command.smsEmailDTO}" var="complaint"
									varStatus="status">
									<tr>

										<td class="text-center">${status.index + 1}</td>
										<td style="text-align: center;"><c:out
												value="${complaint.mobileNo}"></c:out></td>
										<c:if
											test="${command.careReportRequest.alertType=='S' || command.careReportRequest.alertType=='B'}">
											<td style="text-align: center;"><c:out
													value="${complaint.msgText}"></c:out></td>

										</c:if>
										
										<c:if
											test="${command.careReportRequest.alertType=='E'||command.careReportRequest.alertType=='B'}">
											<td style="text-align: center;"><c:out
													value="${complaint.emailId}"></c:out></td>
											<td style="text-align: center;"><c:out
													value="${complaint.emailsubject}"></c:out></td>

										</c:if>
										
										<td style="text-align: center;"><fmt:formatDate
												pattern="dd-MM-yyyy"
												value="${complaint.sentDt}" /></td>

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
						onclick="javascript:openRelatedForm('GrievanceReport.html?generateSmsAndEmailReport','this');">
						<spring:message code="care.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
