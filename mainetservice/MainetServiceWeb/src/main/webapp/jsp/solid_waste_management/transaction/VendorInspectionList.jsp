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
	src="js/solid_waste_management/VendorVerificationForm.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vendor.verification.heading"
					text="Vendor Inspection" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="solid.waste.help" text="Help" /></span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory " /></span>
			</div>

			<form:form action="VendorInspection.html" name="VendorInspection"
				id="VendorInspectionId" class="form-horizontal">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="InspectionId"><spring:message
							code="employee.inspection.type" text="Inspection Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="ITY" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" path=""
							cssClass="form-control required-control" isMandatory="true"
							selectOptionLabelCode="selectdropdown" />
					</div>
					<label class="control-label col-sm-2 required-control"
						for="InspectionDate"><spring:message
							code="employee.inspection.date" text="Inspection Date" /></label>

					<div class="col-sm-4">
						<div class="input-group">
							<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
							<form:input path="" class="form-control mandColorClass"
								id="inspectionDateId" value="${date}" maxlength="10" />
							<label class="input-group-addon mandColorClass"
								for="inspectionDateId"><i class="fa fa-calendar"></i> </label>
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-success btn-submit"
						onclick="search('VendorInspection.html','search');">
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="Reset" class="btn btn-warning"
						onclick="resetScheme()">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="add('VendorInspection.html','Add');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="solid.waste.add" text="Add New" />
					</button>
				</div>
				<div class="table-responsive">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped VndIns">
						<thead>
							<tr>
								<th>Sr.No.</th>
								<th>ComplaintNo</th>
								<th>Vendor Name</th>
								<th>ComplaintDate</th>
								<th>EmployeeName</th>
								<th>InspectionType</th>
								<th width="100">Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>081SWM1617001</td>
								<td>XYZ Services</td>
								<td>10/02/2016</td>
								<td>Prasad</td>
								<td>Complete</td>
								<td style="width: 10%"><button type="button"
										class="btn btn-blue-2 btn-sm"
										onClick="getView('VendorInspection.html','view')"
										data-original-title="View" title="View">
										<strong class="fa fa-eye"></strong><span class="hide"><spring:message
												code="" text="View"></spring:message></span>
									</button>
									<button type="button" class="btn btn-warning btn-sm"
										onClick="getEdit('VendorInspection.html','edit')"
										data-original-title="Edit" title="Edit">
										<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
												code="solid.waste.edit" text="Edit"></spring:message></span>
									</button>
									<!-- <button type="button" class="btn btn-danger btn-sm"
										onClick="deleteVendor('VendorInspection.html','delete')"
										data-original-title="Delete" title="Delete">
										<strong class="fa fa-trash"></strong><span class="hide">
											<spring:message code="solid.waste.delete" text="Delete" />
										</span>
									</button> --></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>