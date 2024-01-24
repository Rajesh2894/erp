<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
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
			<form:form action="veMasterCon.html" name="VehicleMaster"
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
						path="vehicleMasterDTO.veVetype" cssClass="chosen-select-no-results form-control"
						isMandatory="false" selectOptionLabelCode="selectdropdown"
						hasId="true" />

					<label class="col-sm-2 control-label" for="VehicleRegNo"><spring:message
							code="swm.vehicleRegNo" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.veNo" id="VehicleRegNo"
							class="chosen-select-no-results form-control mandColorClass "
							label="Select">
							<form:option value="0">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<c:forEach items="${vehicle}" var="lookup">
								<form:option value="${lookup.veNo}" code="${lookup.veNo}">${lookup.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="department"><spring:message
							code="vehicle.deptId" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.deptId"
							cssClass="chosen-select-no-results form-control" id="deptId"
							onchange="getEmployeeByDept(this);">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<c:if test="${userSession.languageId eq 1}">
									<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<form:option value="${dept.dpDeptid}">${dept.dpNameMar}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="location"><spring:message
							code="vehicle.locId" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.locId" cssClass="chosen-select-no-results form-control"
							id="location">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${locations}" var="loc">
								<c:if test="${userSession.languageId eq 1}">
									<form:option value="${loc.locId}">${loc.locNameEng} - ${loc.locArea}</form:option>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<form:option value="${loc.locId}">${loc.locNameReg} - ${loc.locAreaReg}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchVehicle(this);"
						title="<spring:message code="solid.waste.search" text="Search"></spring:message>">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetVehicle();"
						title="<spring:message code="vehicle.reset" text="Reset"></spring:message>">
						<spring:message code="vehicle.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addVehicle('veMasterCon.html','AddVehicleMaster');"
						title="<spring:message code="fueling.pump.master.add" text="Add" />">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="fueling.pump.master.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive clear">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped vm">
						<thead>
							<tr>
								<th width="5%"><spring:message
										code="vehicle.master.vehicle.srno" text="Sr. No." /></th>
								<th width="15%"><spring:message
										code="vehicle.master.vehicle.type" text="Vehicle Type" /></th>
								<th width="15%"><spring:message
										code="vehicle.master.vehicle.chasis.no" text="Vehicle No." /></th>
								<th width="15%"><spring:message
										code="vehicle.master.vehicle.registration.no"
										text="Vehicle Registration No" /></th>
								<th width="20%"><spring:message
										code="vehicle.master.vehicle.depart.owned.vehicle"
										text="Department Owned Vehicle" /></th>
								<th width="20%"><spring:message
										code="vehicle.master.vehicle.status" text="Status" /></th>
								<th width="150"><spring:message
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
									<td>${data.veChasisSrno}</td>
									<td>${data.veNo}</td>
									<c:choose>
										<c:when test="${data.veFlag eq 'Y'}">
											<td align="center"><spring:message
													code="solid.waste.Yes" text="Yes" /></td>
										</c:when>
										<c:otherwise>
											<td align="center"><spring:message code="solid.waste.No"
													text="No" /></td>
										</c:otherwise>
									</c:choose>
									<td class="text-center"><c:choose>
											<c:when test="${data.veActive eq 'Y'}">
												<spring:message code="vehicle.master.active" text="Active"></spring:message>
											</c:when>
											<c:when test="${data.veActive eq 'N'}">
												<spring:message code="vehicle.master.inactive"
													text="Inactive"></spring:message>
											</c:when>
											<c:otherwise>
												<spring:message code="vehicle.master.scrap" text="Scrap"></spring:message>
											</c:otherwise>
										</c:choose></td>
									<td style="width: 12%" class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getViewVehiclemasterData('veMasterCon.html','viewVehicleMaster', ${data.veId})"
											title="<spring:message code="vehicle.view" text="View"/>">
											<strong class="fa fa-eye"></strong><span class="hide"><spring:message
													code="solid.waste.view" text="View"></spring:message></span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getVehiclemasterData('veMasterCon.html','editVehicleMaster', ${data.veId})"
											title="<spring:message code="vehicle.edit" text="Edit"/>">
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