<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="js/works_management/wmsLeadLiftMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-content padding">
			<div id="export-excel">
				<div class="row">
					<div class="col-xs-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					<div class="col-xs-6 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}</h3>
						<p class="excel-title">
							<strong><spring:message code="leadlift.master.report" text="Lead/Lift Entry Summary Report"/></strong>
						</p>
					</div>
					<div class="col-sm-2">
						<p>
							<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							<br>
							<fmt:formatDate value="${date}" pattern="hh:mm a" />
						</p>
					</div>
				</div>
				<div>
					<p>
						<strong><spring:message code="leadlift.master.ReportSorName" text="" />
							${command.wmsLeadLiftMasterDto.sorName}</strong>
					</p>
				</div>
				<div>
					<strong><spring:message code="leadlift.master.ReportRateType" text="" />
						${command.rateType}</strong>
				</div>
				<div>
					<strong><spring:message code="leadlift.master.ReportFromDate" text="" />
						${command.fromDate}</strong>
				</div>
				<div>
					<strong><spring:message code="leadlift.master.ReportToDate" text="" />
						${command.toDate}</strong>
				</div>

				<div>
					<c:set var="d" value="0" scope="page"></c:set>
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th scope="col" width="10%" align="center"><spring:message
											code="leadlift.master.from" text="From" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="leadlift.master.to" text="To" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="sor.baserate" text="Rate" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.management.unit" text="Unit" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="leLiData"
									items="${command.wmsleadLiftTableDtos}">
									<tr class="leLiClass appendableClass">
										<td align="center">${leLiData.leLiFrom}</td>
										<td align="center">${leLiData.leLiTo}</td>
										<td align="right">${leLiData.leLiRate}</td>
										<td align="center">${leLiData.unitName}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>

