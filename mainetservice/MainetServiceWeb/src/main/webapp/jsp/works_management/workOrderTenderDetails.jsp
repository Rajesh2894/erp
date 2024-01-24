<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <script type="text/javascript"
	src="js/works_management/workOrderGeneration.js"></script> -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<div>
	<h4>
		<spring:message code="work.order.tender.details" text="Tender Details" />
	</h4>

	<div class="form-group">
		<%-- 		<label class="col-sm-2 control-label required-control"><spring:message
				code="work.order.vendor.name" text="Vendor Name" /></label>
		<div class="col-sm-4">
			<form:input path="command.tenderMasterDto.vendorName"
				cssClass="form-control" id="vendorName" readonly="true"
				data-rule-required="true" />
		</div> --%>
	</div>
	<div class="form-group">
		<apptags:input labelCode="work.order.tender.amount"
			path="command.tenderMasterDto.tenderAmount"
			cssClass="form-control text-right" isDisabled="true"></apptags:input>

		<%-- 	<apptags:input labelCode="work.order.security.deposit"
			path="command.tenderMasterDto.tenderSecAmt" cssClass="form-control"
			isReadonly="true"></apptags:input> --%>

		<apptags:input labelCode="work.order.earnest.money.deposit"
			path="command.tenderMasterDto.tenderEmdAmt"
			cssClass="form-control text-right" isReadonly="true"></apptags:input>
	</div>
	<!-- <div class="form-group">
		
	</div> -->

	<div class="text-left text-blue-2 ">
		<p>
			<strong><spring:message
					code="work.order.project.work.details" text="Project/Work Details" /></strong>
			<br>
		</p>
	</div>
	<br>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message
				code="project.master.projcode" text="Project Code" /></label>
		<div class="col-sm-4">
			<form:input path="command.tenderMasterDto.projectCode"
				cssClass="form-control" id="projCode" readonly="true"
				data-rule-required="true" />
		</div>
		<label for="" class="col-sm-2 control-label required-control"><spring:message
				code="project.master.projname" text="Project Name" /> </label>
		<div class="col-sm-4">
			<form:input path="command.tenderMasterDto.projectName"
				cssClass="form-control" id="projName" data-rule-required="true"
				disabled="true" />
		</div>
	</div>
	<div class="table-responsive">
		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-bordered table-striped"
			id="workProjectDetails">
			<thead>
				<tr>
					<th width="10%"><spring:message code="ser.no" text="" /><input
						type="hidden" id="srNo"></th>
					<th width="30%"><spring:message code="work.def.workCode"
							text="Work Code" /></th>
					<th width="30%"><spring:message code="work.def.workname"
							text="Work Name" /></th>
					<th width="30%"><spring:message code="tender.estimateamount"
							text="Estimate Amount" /></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th colspan="3" class="text-right"><spring:message
							code="work.estimate.Total" /></th>
					<th colspan="1"><form:input
							path="command.tenderMasterDto.tenderTotalEstiAmount"
							id="totalAmount" cssClass="form-control text-right"
							readonly="true" /></th>
				</tr>
			</tfoot>
			<c:forEach items="${command.tenderMasterDto.workDto}"
				var="workListData" varStatus="status">
				<tr>
					<td><c:out value=" ${status.count}"></c:out></td>
					<td>${workListData.workCode}</td>
					<td>${workListData.workName}</td>
					<td align="right">${workListData.workEstimateAmt}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

