<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/care/operator-dashoboard.js"></script>
<script type="text/javascript"
	src="js/workflow/deptDashBoard.js"></script>

<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>

<div class="content animated slideInDown" >
	<div class="widget" id="taskDiv">
		<div class="widget-header">
			<h4 class="text-left padding-left-10">
				<b><spring:message code="care.opr.dashboardView" text="Dashboard View" /></b>
			</h4>
		</div>
		<div class="widget-content padding" >
			<form:form method="POST" action="GrievanceComplaintStatus.html"
				commandName="command" class="form-horizontal"
				id="form_grievanceCheckStatus">

				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#details"> <spring:message
										code="care.opr.application.status" text="Application Status" />
								</a>
							</h4>
						</div>

						<div id="details" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:date labelCode="care.reports.fromDate"
										datePath="careRequest.fromDate"
										cssClass="form-control fromDate" fieldclass="datepicker"
										mask="99/99/9999"></apptags:date>

									<apptags:date labelCode="care.reports.toDate"
										datePath="careRequest.toDate" cssClass="form-control toDate"
										fieldclass="datepicker" mask="99/99/9999"></apptags:date>
								</div>
								<div class="form-group">
									<apptags:input labelCode="care.opr.applicationmob.no" 
										path="careRequest.searchString" cssClass="searchString"
										maxlegnth="25"></apptags:input>

									<label class="col-sm-2 control-label " for="id_status"><spring:message
											code="care.reports.status" text="Status" /></label>
									<div class="col-sm-4">
										<form:select class="form-control" id="status"
											path="careRequest.status" data-rule-prefixvalidation="true">
											<form:option code="care.select" value="">
												<spring:message code="care.select" text="Select" />
											</form:option>
											<form:option code="care.closed" value="Closed">
												<spring:message code="care.closed" text="Closed" />
											</form:option>
											<form:option code="care.pending" value="Pending">
												<spring:message code="care.pending" text="Pending" />
											</form:option>
											<form:option code="care.rejected" value="Rejected">
												<spring:message code="care.rejected" text="Rejected" />
											</form:option>
										</form:select>
									</div>
								</div>

							</div>
						</div>

						<div class="text-center clear padding-10">
							<button type="button" class="btn btn-success btn-submit"
								title="Search" onclick="searchAndViewDetails()">
								<spring:message code="care.btn.search" text="Search" />
							</button>
							<button type="reset" class="btn btn-warning">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="adh.reset" text="Reset"></spring:message>
							</button>
							<button type="button" class="btn reset btn-info">
								<a title="File Complaint" style="color:white"
							href="GrievanceDepartmentRegistration.html">
							<spring:message code="care.opr.fileComplaint" text="File Complaint" /></a>
							</button>
							<button type="button" class="btn reset btn-info">
								<a title="File RTI" style="color:white"
							href="RtiApplicationDetailForm.html">
							<spring:message code="care.opr.fileRti" text="File RTI" /></a>
							</button>
						</div>
						
						

					
							<div class=" margin-top-10">
								<table
									class="table table-striped table-condensed table-bordered"
									id="id_complaintList">
									<thead>
										<tr>
											<th width="10%"><spring:message
													code="care.opr.application.no" text="Applicatin No" /></th>
											<th width="20%"><spring:message
													code="care.registration.date" text="Registration Date" /></th>
											<th width="12%"><spring:message
													code="care.reports.department" text="Department" /></th>
											<th width="20%"><spring:message
													code="care.opr.serviceName" text="Service Name" /></th>
											<th width="15%"><spring:message
													code="care.reports.status" text="Status" /></th>
											<th width="13%"><spring:message
													code="care.opr.pendingAt" text="Pending At" /></th>
											<th width="13%"><spring:message
													code="care.opr.level" text="Level" /></th>	
											<th width="13%"><spring:message
													code="care.opr.lastComment" text="Last Remark" /></th>
											<th width="13%"><spring:message
													code="care.action.Action" text="Action" /></th>						
										</tr>
									</thead>
									<tbody>
									
										<c:forEach items="${command.careRequests}" var="complaint"
											varStatus="loop">
											<tr>
											<td align="center"><c:out
													value="${complaint.applicationId}"></c:out></td>
											<td align="center"><fmt:formatDate
														pattern="dd/MM/yyyy hh:mm a"
														value="${complaint.dateOfRequest}" /></td>
												<td align="center"><c:out
														value="${userSession.languageId eq 1 ? complaint.departmentComplaintDesc : complaint.departmentComplaintDescReg }"></c:out></td>
												<td align="center"><c:out
														value="${userSession.languageId eq 1 ? complaint.complaintTypeDesc : complaint.complaintTypeDescReg }"></c:out></td>
												<td align="center"><c:if
														test="${complaint.status eq 'CLOSED'}">
														<span class="text-green-1"> <spring:message
																code="care.status.closed" text="Closed" />
														</span>
													</c:if> <c:if test="${complaint.status eq 'EXPIRED'}">
														<span class="text-red-1"> <spring:message
																code="care.status.expired" text="Expired" />
														</span>
													</c:if> <c:if test="${complaint.status eq 'PENDING'}">
														<span class="text-orange-5"> <spring:message
																code="care.status.pending" text="Pending" />
														</span>
													</c:if>
													
													<c:if
														test="${complaint.status eq 'REJECTED'}">
														<span class="text-green-1"> <spring:message
																code="care.status.rejected" text="REJECTED" />
														</span>
													</c:if>
													</td>
												<td align="center"><c:out
														value="${complaint.actorName}"></c:out></td>
												<td align="center"><c:out
														value="${complaint.level}"></c:out></td>
												<td align="center"><c:out
														value="${complaint.comment}"></c:out></td>
												<td align="center">
													<%-- <button type="button" class="btn btn-blue-2 btn-sm"
														title="View Complaint"
														onclick="viewComplaintDetails('${complaint.complaintId}')">
														<i class="fa fa-eye"></i>
													</button> --%>
													<%-- <button type="button" class="btn btn-blue-2 btn-sm"
														title="View Complaint"
														onclick="dashboardViewHistory('${complaint.applicationId}','${complaint.complaintId}','${complaint.complaintTypeDesc}','${complaint.dateOfRequest}','${complaint.workflowReqId}')">
														<i class="fa fa-eye"></i>
													</button> --%>
													<button type="button" class="btn btn-blue-2 btn-sm"
														title="View Complaint"
														onclick="dashboardViewHistory('${complaint.applicationId}','${complaint.complaintId}','${complaint.complaintTypeDesc}','${complaint.dateOfRequest}','${complaint.workflowReqId}')">
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
			</form:form>
		</div>
	</div>
</div>