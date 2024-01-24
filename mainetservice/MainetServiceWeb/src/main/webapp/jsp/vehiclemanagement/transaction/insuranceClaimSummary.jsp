<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- <link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script type="text/javascript" src="js/vehicle_management/insuranceClaim.js"></script>
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
				<strong><spring:message code="insurance.claim"
						text="Insurance Details" /></strong>
			</h2>
			<apptags:helpDoc url="insuranceClaim.html" />
		</div>

		<div class="widget-content padding">
			<form:form action="insuranceClaim.html" class="form-horizontal form"
				name="command" id="id_insuranceDetails">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<span id="errorId"></span>
				</div>


				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="department"><spring:message
							code="insurance.detail.department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceClaimDto.department" cssClass="form-control chosen-select-no-results"
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
						items="${command.getLevelData(baseLookupCode)}" path="insuranceClaimDto.vehicleType" 
						cssClass="form-control required-control chosen-select-no-results"
						isMandatory="true" changeHandler="showVehicleRegNo(this,'E')"
						selectOptionLabelCode="selectdropdown" hasId="true" />

				</div>
				<div class="form-group">
		               <label class="control-label col-sm-2 required-control" for="vehicle"><spring:message
							code="vehicle.sanitary.vehicleNo" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceClaimDto.veId" id="veid" cssClass="form-control chosen-select-no-results"  
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
					<button type="button" id="search" class="btn btn-blue-2" onclick="searchInsuranceClaim()" 
						title="<spring:message code="vehicle.search" text="Search" />">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.search" text="Search" />
					</button>
					<button type="Reset" class="btn btn-warning"
						onclick="window.location.href='insuranceClaim.html'"
						title="<spring:message code="lgl.reset" text="Reset"></spring:message>">
						<spring:message code="lgl.reset" text="Reset"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success"
						onclick="openForm('insuranceClaim.html','AddInsuranceDetailsForm');"
						name="button-Add" title="<spring:message code="bt.add" text="Add" />" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="bt.add" text="Add" />
					</button>
				</div>
				
				<!-- table grid start  -->	
		       	<div class="table-responsive margin-top-10">
					<table class="table table-striped table-bordered"
						id="InsuranceClaimTable">
						<thead>
							<tr>
								<th><spring:message code="refueling.pump.master.sr.no" text="Sr. No." /></th>
								<th><spring:message code="vehicle.deptId" text="Department" /></th>
								<th><spring:message code="VehicleMaintenanceMasterDTO.veVetype" text="Vehicle Type" /></th>
								<th><spring:message code="vehicle.sanitary.vehicleNo" text="Vehicle No" /></th>
								<th><spring:message code="insurance.detail.insuredBy" text="Insured By" /></th>
								<th><spring:message code="insurance.detail.issueDate" text="Insurance Issue Date" /></th>
								<th><spring:message code="swm.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${InsuranceDetailsData}" var="dto" varStatus="loop">
								<tr>
									<td align="center">${loop.count}</td>
									<td align="center">${dto.deptDesc}</td>
									<td align="center">${dto.veRegnNo}</td>
									<td align="center">${dto.veNo}</td>
									<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${dto.issueDate}" /></td>
									<%-- <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${dto.endDate}" /></td> --%>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="<spring:message code="vehicle.view" text="View"/>"
											onclick="modifyInsuranceRequest(${dto.insuranceClaimId}, 'insuranceClaim.html','viewInsuranceForm','V')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="<spring:message code="vehicle.edit" text="Edit"/>"
											onclick="modifyInsuranceRequest(${dto.insuranceClaimId}, 'insuranceClaim.html','editInsuranceForm','E')">
											<i class="fa fa-pencil"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody> 
					</table>
				</div>
				<!-- table grid End  -->
				
             	<!-- End Form -->

			</form:form>
		</div>
	</div>
</div>


