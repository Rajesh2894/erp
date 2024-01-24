<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/works_management/workOrderGeneration.js"></script>

<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.work.order.summary"
					text="Work Order Summary" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="PwWorkOrderGeneration.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="PwWorkOrderGeneration.html"
				cssClass="form-horizontal" name="WorkOrderGenerationSummary"
				id="WorkOrderGenerationSummary">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="work.order.workOrder.no" /></label>
					<div class="col-sm-4">
						<form:select path="" id="workOrderNo"
							class="form-control  chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${workOrderDtosList}" var="workOrderNoDeatils">
								<form:option value="${workOrderNoDeatils.workOrderNo }">${workOrderNoDeatils.workOrderNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="work.order.agrement.start.date" text=" " /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepickers"
								id="workOrderDate" readonly="true" />
							<label class="input-group-addon" for="workOrderDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=workOrderDate></label>
						</div>
					</div>
				</div>

				<!-- Remove As Per Suda UAT -->

				<%--	<div class="form-group">
					 	<label class="control-label col-sm-2"><spring:message
							code="work.order.vendor.name" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<form:input path="" id="vendorName" type="text"
							class="form-control preventSpace" />
					</div> 
					<label class="col-sm-2 control-label"><spring:message
							code="work.order.contract.from.date" text="Contract From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepickers"
								id="contractFromDate" readonly="true" />
							<label class="input-group-addon" for="contractFormDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id=contractFormDate></label>
						</div>
					</div>


					<label class="col-sm-2 control-label"><spring:message
							code="work.order.contract.to.date" text="Contract To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepickers"
								id="contractToDate" readonly="true" />
							<label class="input-group-addon" for="contractToDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id="contractToDate"></label>
						</div>
					</div>
				</div>--%>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button class="btn btn-blue-2 search" type="button"
						id="searchWorkOrder">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning" onclick="resetWorkOrder();"
						type="button">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary add" type="button"
						onclick="openAddWorkOrderGeneration('PwWorkOrderGeneration.html','ShowWorkOrderGeneration');">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.management.add" text="Add" />
					</button>
				</div>
				<!-- End button -->
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="25%" align="center"><spring:message
										code="work.order.workOrder.no" text="Work Order No." /></th>

								<%-- <th width="10%" align="center"><spring:message
										code="work.order.workOrder.date" text="Work Order Date" /></th> --%>

								<th width="30%" align="center"><spring:message
										code="work.def.workname" text="Work Name" /></th>

								<th width="15%" align="center"><spring:message
										code="work.order.contract.no" text=" " /></th>

								<!-- Remove As per SUDA UAT -->

								<%-- <th width="10%" align="center"><spring:message
										code="work.order.contract.from.date" text="Contract From Date" /></th>

								<th width="10%" align="center"><spring:message
										code="work.order.contract.to.date" text="Contract To Date" /></th>--%>

								<th width="15%" align="center"><spring:message
										code="work.order.agrement.start.date" text=" " /></th>

								<th width="15%" align="center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${workOrderDtosList}" var="workOrderDto">
								<tr>
									<td class="text-center">${workOrderDto.workOrderNo}</td>
									<%-- <td>${workOrderDto.orderDateDesc}</td> --%>
									<td>${workOrderDto.workName}</td>
									<td class="text-center">${workOrderDto.contractMastDTO.contNo}</td>

									<!-- Remove As per SUDA UAT -->
									<%-- <td>${workOrderDto.contractFromDateDesc}</td>
									<td>${workOrderDto.contractToDateDesc}</td> --%>

									<td class="text-center">${workOrderDto.actualStartDateDesc}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="editAndViewWorkOrder(${workOrderDto.workId},'V')"
											title="<spring:message code="works.management.view"></spring:message>">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-success btn-sm"
											onClick="editAndViewWorkOrder(${workOrderDto.workId} ,'E')"
											title="<spring:message code="works.management.edit"></spring:message>">
											<i class="fa fa-pencil-square-o"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onclick="workPrintWorkOrder(${workOrderDto.workId})"
											title="<spring:message code="works.management.print.work.order"></spring:message>">
											<i class="fa fa-print"></i>
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