<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/vehicle_management/insuranceDetails.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- End breadcrumb Tags -->

<script>
$(document).ready(function() {
	$("#veid").val("");
});
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="insurance.detail"
						text="Insurance Details" /></strong>
			</h2>
			<apptags:helpDoc url="insuranceDetails.html" />
		</div>

		<div class="widget-content padding">
			<form:form action="insuranceDetails.html" class="form-horizontal form"
				name="command" id="id_insuranceDetails">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<!-- 	<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button> -->
					<span id="errorId"></span>
				</div>


				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="department"><spring:message
							code="insurance.detail.department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceDetailsDto.department" cssClass="form-control chosen-select-no-results"
							 id="department" onchange="getVehicleTypeByDept()">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>


					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}" path="insuranceDetailsDto.vehicleType" 
						cssClass="form-control required-control chosen-select-no-results" isMandatory="true" changeHandler="showVehicleRegNo(this,'E')"
						selectOptionLabelCode="selectdropdown" hasId="true" />

				</div>
				<div class="form-group">
		               <label class="control-label col-sm-2 required-control" for="vehicle"><spring:message
							code="vehicle.sanitary.vehicleNo" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceDetailsDto.veId" id="veid" cssClass="form-control chosen-select-no-results"  
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListVehicles}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.vehicleTypeDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2" onclick="searchInsuranceDetails()" 
						title="<spring:message code="vehicle.search" text="Search" />">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.search" text="Search" />
					</button>
					<button type="Reset" class="btn btn-warning"
						onclick="window.location.href='insuranceDetails.html'"
						title="<spring:message code="lgl.reset" text="Reset"></spring:message>">
						<spring:message code="lgl.reset" text="Reset"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success"
						onclick="openForm('insuranceDetails.html','AddInsuranceDetailsForm');"
						name="button-Add" title="<spring:message code="bt.add" text="Add" />" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="bt.add" text="Add" />
					</button>
				</div>
				
				<!-- table grid start  -->
		       	<div class="table-responsive margin-top-10">
				<table class="table table-striped table-bordered"
					id="InsuranceDetailTable">
					<thead>
						<tr>
							<th><spring:message code="refueling.pump.master.sr.no" text="Sr. No." /></th>
							<th><spring:message code="vehicle.deptId" text="Department" /></th>
							<th><spring:message code="VehicleMaintenanceMasterDTO.veVetype" text="Vehicle Type" /></th>
							<th><spring:message code="oem.warranty.vehicleNumber" text="Vehicle No" /></th>
							<th><spring:message code="insurance.detail.insuredBy" text="Insured By" /></th>
							<th><spring:message code="insurance.detail.issueDate" text="Insurance Issue Date" /></th>
							<th><spring:message code="swm.action" text="Action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="" var="oemwarranty" varStatus="loop">
							<tr>
								<td align="center">${loop.count}</td>
								<td align="center">${oemwarranty.veDesc}</td>
								<td align="center">${oemwarranty.veRegnNo}</td>
								<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${oemwarranty.vesFromdt}" /></td>
								<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${oemwarranty.vesTodt}" /></td>
								<td class="text-center">
									<button type="button" class="btn btn-blue-2 btn-sm"
										title="View"
										onclick="modifyOemRequest(${oemwarranty.vesId}, 'OEMWarranty.html','ViewOEMWarranty','V')">
										<i class="fa fa-eye"></i>
									</button>
									<button type="button" class="btn btn-warning btn-sm"
										title="Edit"
										onclick="modifyOemRequest(${oemwarranty.vesId}, 'OEMWarranty.html','EditOEMWarranty','E')">
										<i class="fa fa-pencil"></i>
									</button>
									<button type="button" class="btn btn-danger btn-sm"
										title="Delete"
										onclick="modifyOemRequest(${oemwarranty.vesId}, 'OEMWarranty.html','DeleteOEMWarranty')">
										<i class="fa fa-trash"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody> 
				</table>
				</div>
				<!-- table grid End  -->
				
			</form:form>
			<!-- End Form -->
		</div>
	</div>
</div>











