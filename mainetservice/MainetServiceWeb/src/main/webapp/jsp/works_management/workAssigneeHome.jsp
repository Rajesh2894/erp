<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/works_management/workAssignee.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.assignee.summery"
					text="Work Assignee Summary" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="WorkAssignee.html" class="form-horizontal" name=""
				id="WorkAssignee">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="work.order.workOrder.no" text="Work Order No." /></label>
					<div class="col-sm-4">
						<form:select path="" cssClass="form-control " id="workOrderId">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${workOrderList}" var="WOArray">
								<form:option value="${WOArray[0]}">${WOArray[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<%-- <label class="col-sm-2 control-label"><spring:message
							code="Work Assignee" text="Work Assignee" /></label>
					<div class="col-sm-4">
						<form:select path="empId" cssClass="form-control" id="assignee" >
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${employee}" var="emp">
								<form:option value="${emp[3]}"
									label="${emp[0]} ${emp[2]}  => ${emp[4]}"></form:option>
							</c:forEach>
						</form:select>
					</div> --%>

				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success" id="searchAssignee">
						<strong class="fa fa-search"></strong>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning" type="button"
						onclick="window.location.href='WorkAssignee.html'">
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-blue-2" id=addAssignee>
						<spring:message code="" text="Add Assignee" />
					</button>
				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="15%" align="center"><spring:message
										code="work.order.workOrder.no" text="Work Order No." /></th>
								<th width="15%" align="center"><spring:message
										code="work.assignee.vendorName" text="Vendor Name" /></th>
								<th width="15%" align="center"><spring:message
										code="work.assignee" text="Assignee" /></th>
								<th width="15%" align="center"><spring:message
										code="work.def.workCode " text="Work Code" /></th>
								<th width="10%" align="center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${workAssigneeGroup}" var="work">
								<tr>
									<td>${work.workOrderNo}</td>
									<td>${work.tenderMasterDto.vendorName }</td>
									<td>${work.tenderMasterDto.workAssigneeName }</td>
									<td>${work.tenderMasterDto.tenderAllWorks }</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="<spring:message code="works.management.view"></spring:message>">
											onClick="viewAssignee(${work.workId},${work.tenderMasterDto.workAssigneeId})">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="editAssignee(${work.workId},${work.tenderMasterDto.workAssigneeId})"
											title="<spring:message code="works.management.edit"></spring:message>">
											<i class="fa fa-pencil"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>
