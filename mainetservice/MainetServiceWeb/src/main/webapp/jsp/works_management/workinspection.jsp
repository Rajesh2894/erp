<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/workVigilance.js"></script>



<!-- End JSP Necessary Tags -->

<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-target="#a2" data-toggle="collapse" class=""
				data-parent="#accordion_single_collapse" href="#a2"><spring:message
					code="work.mb.approval.mbdetails" text="Details Of MB" /> </a>
		</h4>
	</div>
	<div id="a2" class="panel-collapse ">
		<div class="panel-body">
			<div class="table-responsive clear">
				<table class="table table-striped table-bordered" id="workMb">
					<thead>
						<tr>
							<th width="25%" align="center"><spring:message
									code="work.approvla.mb.number" text="MB No."></spring:message></th>
							<th width="25%" align="center"><spring:message
									code="work.approvla.mb.date" text="MB Date"></spring:message></th>
							<th width="25%" align="center"><spring:message
									code="work.order.employee.enginner.name" text="Engineer Name"></spring:message></th>
							<th width="12%" align="center"><spring:message
									code="work.abstract.sheet.report" text="Abstract Sheet Report"></spring:message></th>
							<th width="13%" align="center"><spring:message
									code="works.management.action" text="Action"></spring:message></th>
						</tr>
					</thead>

					<c:forEach items="${command.measureMentBookMastDtosList}"
						var="mbDetails">
						<tr>
							<td>${mbDetails.workMbNo}</td>
							<td>${mbDetails.mbTakenDate}</td>
							<td>${mbDetails.workAssigneeName}</td>
							<td class="text-center">
								<button type="button" class="btn btn-warning btn-sm"
									onClick="viewMbAbstractSheet(${mbDetails.workMbId});"
									title='<spring:message code="wms.abstract.report"></spring:message>'>
									<i class="fa fa-print"></i>
								</button>
							</td>
							<td class="text-center">

								<button type="button" class="btn btn-blue-2 btn-sm"
									onClick="getVIewMB(${mbDetails.workMbId},'V');"
									title='<spring:message code="works.management.view"></spring:message>'>
									<i class="fa fa-eye"></i>
								</button>

							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
