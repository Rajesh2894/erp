<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/vehicle_management/OEMWarranty.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<style>
table#oemWarrantyTable tbody tr td:last-child {
	white-space: nowrap;
}
</style>
<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- End breadcrumb Tags -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="oem.warranty"
						text="OEM Warranty" /></strong>
			</h2>
			<apptags:helpDoc url="OEMWarranty.html" />
		</div>

		<div class="widget-content padding">
			<form:form action="OEMWarranty.html" class="form-horizontal form"
				name="command" id="id_OEMWarranty">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<!-- 	<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button> -->
					<span id="errorId"></span>
				</div>


				<div class="form-group">
					<%-- <label class="control-label col-sm-2 required-control" for="department"><spring:message
							code="oem.warranty.department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="oemWarrantyDto.department" cssClass="form-control" id="department" onchange="getVehicleTypeByDept()">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>


					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}" path="oemWarrantyDto.vehicleType" 
						cssClass="form-control required-control" isMandatory="true" changeHandler="showVehicleRegNo(this,'E')"
						selectOptionLabelCode="selectdropdown" hasId="true" />

				<!-- </div>
				<div class="form-group"> -->
		               <label class="control-label col-sm-2 required-control" for="vehicle"><spring:message
							code="oem.warranty.vehicleNumber" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select path="oemWarrantyDto.veId" id="veNo" cssClass="chosen-select-no-results form-control"  
							data-rule-required="true" >
							<form:option value="0">
								<spring:message code="oem.select" text="Select" />
							</form:option>
							<c:forEach items="${ListVehicles}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.vehicleTypeDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						title='<spring:message code="vehicle.search" text="Search" />'
						onclick="searchPetrolRequest()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="vehicle.reset" text="Reset" />'
						onclick="resetVehicleOEM();">
						<spring:message code="vehicle.reset" text="Reset" />
					</button>
					
					<button type="submit" class="button-input btn btn-success"
						title='<spring:message code="bt.add" text="Add" />'
						onclick="openAddOEMWarranty('OEMWarranty.html','AddOEMWarrenty');"
						name="button-Add" style="" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="bt.add" text="Add" />
					</button>
					</div>
					<!-- table grid start  -->
			
					
					<div class="table-responsive">
			       	<div class="table-responsive margin-top-10">
					<table class="table table-striped table-bordered"
						id="oemWarrantyTable">
						<thead>
							<tr>
								<th><spring:message code="refueling.pump.master.sr.no" text="Sr. No." /></th>
								<th><spring:message code="oem.warranty.partType" text="Part Type" /></th>
								<th><spring:message code="oem.warranty.partName" text="Part Name" /></th>
								<th><spring:message code="oem.warranty.warrantyPeriod" text="Warranty Period" /></th>
								<th><spring:message code="oem.warranty.purchaseDate" text="Part Purchase date" /></th>
								<th><spring:message code="oem.warranty.lastDateOfWarranty" text="Last Date Of Warranty" /></th>
								<th width="8%"><spring:message code="swm.action" text="Action" /></th>
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
											title="<spring:message code="vehicle.view" text="View"/>"
											onclick="modifyOemRequest(${oemwarranty.vesId}, 'OEMWarranty.html','ViewOEMWarranty','V')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="<spring:message code="vehicle.edit" text="Edit"/>"
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
			</div>
					
					
					
					<!-- table grid End  -->

				
             	<!-- End Form -->

			</form:form>
		</div>
	</div>
</div>











