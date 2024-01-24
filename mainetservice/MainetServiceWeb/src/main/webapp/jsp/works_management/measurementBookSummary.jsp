<!-- Start JSP Necessary Tags -->
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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/measurementBook.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
table.table tbody tr :is(:not(td:nth-child(6))) {
	text-align: center;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="mb.summary" text="Measurement Book Summary" />
			</h2>
			<div class="additional-btn">
			  <apptags:helpDoc url="MeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="MeasurementBook.html" class="form-horizontal"
				id="measurementBook" name="measurementBook">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label"><spring:message
							code="work.order.workOrder.no" text="Work Order No." /></label>
					<div class="col-sm-4">
						<form:select path="workOrderDto.workId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="workId" data-rule-required="true"
							onchange="getWorkOrderDetail(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.workOrderDtoList}" var="lookUp">
								<form:option value="${lookUp.workId}" code="">${lookUp.workOrderNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="tender.vendorname" text="Contractor Name" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="vendorId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.vendorDetail}" var="lookUp">
								<form:option value="${lookUp.vmVendorid}" code="">${lookUp.venderName}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">
					<label for="" class="col-sm-2 control-label"><spring:message
							code="work.def.workname" text="Work Name" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="workName">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.workList}" var="lookUp">
								<form:option value="${lookUp.workId}">${lookUp.workcode} => ${lookUp.workName}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="wms.MeasurementBookNo" text="Measurement Book No." /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="mbNo"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.mbList}" var="lookUp">
								<form:option value="${lookUp.workMbNo}" code="">${lookUp.workMbNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label "><spring:message
							code="work.estimate.status" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="status">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<form:option value="P">
								<spring:message code='mb.pending' />
							</form:option>
							<form:option value="D">
								<spring:message code='mb.draft' />
							</form:option>
							<form:option value="RA">
								<spring:message code='mb.approved' />
							</form:option>
						</form:select>
					</div>
				</div>


				<div class="text-center padding-bottom-10">
					<button class="btn btn-blue-2 searchMbDetails" type="button"
						onclick="searchMbDetails();">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="window.location.href='MeasurementBook.html'">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary" onclick="addMb();"
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.measurement.add" text="Add" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th scope="col" width="15%" align="center"><spring:message
										code="work.order.workOrder.no" text="Work Order No." />
								<th scope="col" width="10%" class="text-center"><spring:message
										code="work.order.workOrder.date" text="Work Order Date" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="work.order.contract.no" text="Contract No." /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sor.contract.agreement.date" text="Contract Date" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="wms.MeasurementBookNo" text="Measurement Book No." /></th>
								<th scope="col" width="10%" class=""><spring:message
										code="wms.MeasurementAmount" text="Measurement Amount" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.status" text="Status" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<%-- <c:forEach items="${milestoneList}" var="mstDto">
								<tr>
									<td>${mstDto.mileStoneDesc}</td>
									<td></td>
									<td>${mstDto.mileStoneWeight}</td>
									<td>${mstDto.msStartDate}</td>
									<td>${mstDto.msEndDate}</td>
									<td class="text-center">
										<button type="button" class="btn btn-primary btn-sm"
											title="Update Progress"
											onclick="getActionForDefination(${mstDto.mileId});">
											<i class="fa fa-line-chart"></i>
										</button>
									</td>
								</tr>
							</c:forEach> --%>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>