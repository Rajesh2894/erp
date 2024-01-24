<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/legacyMeasurementBook.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="" text="Legacy Measurement Book Summary" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="LegacyMeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="LegacyMeasurementBook.html"
				class="form-horizontal" id="LegacyMeasurementBook"
				name="LegacyMeasurementBook">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.order.workOrder.no" text="Work Order No." /></label>
					<div class="col-sm-4">
						<form:select path="workOrderDto.workId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="workId" data-rule-required="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.workOrderDtoList}" var="lookUp">
								<form:option value="${lookUp.workId}" code="">${lookUp.workOrderNo}</form:option>
							</c:forEach>
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
						onclick="window.location.href='LegacyMeasurementBook.html'">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary add hide" onclick="OpenCreateMb();"
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="wms.CreateMB" text="Create MB" />
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
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>