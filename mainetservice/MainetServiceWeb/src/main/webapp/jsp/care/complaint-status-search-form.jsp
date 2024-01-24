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
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/care/complaint-status-search-form.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="care.complaint.status" text="Complaint Status" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
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
										code="care.complaint.status" text="Complaint Status" />
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
									<div id="id_department_div">
										<label class="col-sm-2 control-label"> <spring:message
												code="care.department" text="Department" />
										</label>
										<div class="col-sm-4">
											<form:select path="careRequest.departmentComplaint"
												class="form-control chosen-select-no-results mandColorClass"
												id="departmentComplaint" data-rule-required="true">
												<form:option value="0">
													<spring:message code='Select' />
												</form:option>
												<c:forEach items="${command.departments}" var="lookup">
													<c:choose>
														<c:when test="${userSession.languageId eq 1}">
															<form:option value="${lookup.lookUpId}">${lookup.descLangFirst}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${lookup.lookUpId}">${lookup.descLangSecond}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</div>
									</div>

									<div id="id_complaintType_div">
									<label class="col-sm-2 control-label "> <spring:message code="care.complaintType" text="Service / Complaint Type" />
										</label>
										<div class="col-sm-4">
										<form:select path="careRequest.complaintType"
											class="form-control chosen-select-no-results mandColorClass"
											id="complaintType" data-rule-required="true">
											<form:option value="0">
												<spring:message code='Select' />
											</form:option>
											<c:forEach items="${command.complaintTypes}" var="lookup">
												<c:choose>
													<c:when test="${userSession.languageId eq 1}">
														<form:option value="${lookup.lookUpId}">${lookup.descLangFirst}</form:option>
													</c:when>
													<c:otherwise>
														<form:option value="${lookup.lookUpId}">${lookup.descLangSecond}</form:option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</form:select>
										</div>							
									</div>
								</div>

								<div class="form-group">
									<apptags:input labelCode="care.mobile.complaint.no"
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
							<button type="button" class="btn btn-success"
								title="Search" onclick="searchAndViewComplaint()">
								<spring:message code="care.btn.search" text="Search" />
							</button>
							<button type="button" class="btn reset btn-warning"
								onclick="resetApplication(this);">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="adh.reset" text="Reset"></spring:message>
							</button>
							<button type="button" class="btn btn-danger" id="back"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code="NHP.back" text="Back"></spring:message>
							</button>
						</div>

					
							<div class="table-responsive margin-top-10">
								<table
									class="table table-striped table-condensed table-bordered"
									id="id_complaintList">
									<thead>
										<tr>
											<th width="10%"><spring:message
													code="care.reports.tokenNo" text="Token Number" /></th>
											<th width="20%"><spring:message
													code="care.registration.date" text="Registration Date" /></th>
											<th width="12%"><spring:message
													code="care.reports.department" text="Department" /></th>
											<th width="20%"><spring:message
													code="care.reports.complaintType" text="Complaint Type" /></th>
											<th width="20%"><spring:message
													code="care.complaint.name" text="Complainant Name" /></th>
											<th width="8%"><spring:message
													code="care.complaint.status" text="Complaint Status" /></th>
											<th width="22%"><spring:message
													code="care.action.Action" text="Action" /></th>
										</tr>
									</thead>
									<tbody>
									
										<c:forEach items="${command.careRequests}" var="complaint"
											varStatus="loop">
											<tr>
												<td align="center"><c:choose>
														<c:when test="${empty  complaint.complaintId}">
															<c:out value="${complaint.applicationId}"></c:out>
														</c:when>
														<c:otherwise>
															<c:out value="${complaint.complaintId}"></c:out>
														</c:otherwise>
													</c:choose></td>
												<td align="center"><fmt:formatDate
														pattern="dd/MM/yyyy hh:mm a"
														value="${complaint.dateOfRequest}" /></td>
												<td align="center"><c:out
													value="${userSession.languageId eq 1 ? complaint.departmentComplaintDesc : complaint.departmentComplaintDescReg }"></c:out></td>
												<td align="center"><c:out
														value="${userSession.languageId eq 1 ? complaint.complaintTypeDesc : complaint.complaintTypeDescReg }"></c:out></td>
												<td align="center"><c:out
														value="${complaint.apmName}"></c:out></td>
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
												<td align="center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														title="View Complaint"
														onclick="viewComplaintDetails('${complaint.complaintId}')">
														<i class="fa fa-eye"></i>
													</button>
													<!-- D#127219 -->
													<c:if test="${complaint.status eq 'CLOSED' || complaint.status eq 'EXPIRED' }">
														
														<button id="btnReopen" type="button" onclick="viewReopenComplaint('${complaint.complaintId}')" 
															class="btn btn-blue-2 hidden-print"><i class=""></i>
															 <spring:message code="care.receipt.reopen" text="Reopen"/>
														</button>
														
													
												</c:if>
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