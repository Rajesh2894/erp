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
<script type="text/javascript" src="js/vehicle_management/VehicleMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.master.heading" text="Vehicle Master" />
			</h2>
			<apptags:helpDoc url="VehMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="VehMaster.html" name="VehicleMaster"
				id="VehicleMasterList" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<form:hidden path="vehicleMasterDTO.searchMessage" value=""
					id="Existdata" />
			
			<div class="form-group">
				  
	              	<label class="control-label col-sm-2" for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMasterDTO.veVetype"
						cssClass="form-control"
						isMandatory="false" selectOptionLabelCode="selectdropdown"
						hasId="true" />
						
						<label class="col-sm-2 control-label" for="VehicleRegNo"><spring:message
							code="swm.vehicleRegNo" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.veNo" id="VehicleRegNo" 
							class="chosen-select-no-results form-control mandColorClass " label="Select">
							<form:option value="0">
								<spring:message code="solid.waste.select" text="select" /></form:option>
							<c:forEach items="${vehicle}" var="lookup">
								<form:option value="${lookup.veNo}" code="${lookup.veNo}">${lookup.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
						
						<%-- <label class="control-label col-sm-2" for="VehicleRegNo"><spring:message
							code="vehicle.master.vehicle.no" text="Vehicle No." /></label>
					<div class="col-sm-4">
						<form:input path="vehicleMasterDTO.veNo" class="form-control"
							id="VehicleRegNo"></form:input>
					</div> --%>
					</div>		
						
			<div class="form-group">
							     <label class="col-sm-2 control-label"
										for="department"><spring:message
											code=""
											text="Department" /></label>
									<div class="col-sm-4">
										<form:select path="vehicleMasterDTO.deptId" cssClass="form-control"
											id="deptId"  onchange="getEmployeeByDept(this);">
											<form:option value="">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${departments}" var="dept">
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
	                      
	                      				<label class="col-sm-2 control-label"
										for="location"><spring:message
											code=""
											text="Location" /></label>
									<div class="col-sm-4">
										<form:select path="vehicleMasterDTO.locId" cssClass="form-control"
											id="location">
											<form:option value="">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${locations}" var="loc">
												<form:option value="${loc.locId}">${loc.locNameEng}-${loc.locArea}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div> 
	

				 <div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchVehicle(this);">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetVehicle();">
						<spring:message code="vehicle.reset" text="Reset"></spring:message>
					</button>
					
					<input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="<spring:message code="vehicle.back" text="Back"/>">
					
					<button type="button" class="btn btn-success add"
						onclick="addVehicle('VehMaster.html','AddVehicleMaster');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="vehicle.add"
							text="Add" />
					</button>
					
				</div>
				<div class="table-responsive clear">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped vm">
						<thead>
							<tr>
								<th><spring:message code="vehicle.master.vehicle.srno"
										text="Sr. No." /></th>
								<th><spring:message code="vehicle.master.vehicle.type"
										text="Vehicle Type" /></th>
								<th><spring:message code="vehicle.master.vehicle.no"
										text="Vehicle No." /></th>
								<th width="15%"><spring:message
										code="vehicle.master.vehicle.department.own.vehicle"
										text="Department owned vehicle" /></th>
								<th width="15%"><spring:message
										code="vehicle.master.vehicle.status" text="Status" /></th>
								<th width="130"><spring:message
										code="vehicle.master.vehicle.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.vehicleMasterList}" var="data"
								varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(data.veVetype,'')" /></td>
									<td>${data.veNo}</td>
									<c:choose>
										<c:when test="${data.veFlag eq 'Y'}">
											<td align="center"><spring:message
													code="solid.waste.Yes" text="Yes" /></td>
										</c:when>
										<c:otherwise>
											<td align="center"><spring:message code="solid.waste.No" text="No" /></td>
										</c:otherwise>
									</c:choose>
									<td class="text-center"><c:choose>
											<c:when test="${data.veActive eq 'Y'}">
												<a href="#" class="fa fa-check-circle fa-2x green "
													title="Active"></a>
											</c:when>
											<c:otherwise>
												<a href="#" class="fa fa-times-circle fa-2x red "
													title="Inactive"></a>
											</c:otherwise>
										</c:choose></td>
									<td style="width: 12%" class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getViewVehiclemasterData('VehMaster.html','viewVehicleMaster', ${data.veId})"
											title="View">
											<strong class="fa fa-eye"></strong><span class="hide"><spring:message
													code="solid.waste.view" text="View"></spring:message></span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getVehiclemasterData('VehMaster.html','editVehicleMaster', ${data.veId})"
											title="Edit">
											<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
													code="solid.waste.edit" text="Edit"></spring:message></span>
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