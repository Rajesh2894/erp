<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/vehicle_management/petrolRequisition.js"></script>

<script type="text/javascript">
	$(".Moredatepicker").timepicker({
		//s dateFormat: 'dd/mm/yy',		
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	$("#time").timepicker({

	});
</script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<style>
	.error-div #errorId ul li {
		margin-bottom: 10px;
	}
</style>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="fuel.request.form" text="Fuel Requisition Form" /></strong>
				<apptags:helpDoc url="petrolRequestForm.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="petrolRequisitionForm.html" id="frmPetrolRequestForm"
				name="frmPetrolRequestForm" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				

				<div class="form-group">
					<label class="col-sm-2 control-label"> <spring:message
							code="vehicle.fuelling.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="fromDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" maxLength="10" placeholder="dd/mm/yyyy" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"> <spring:message
							code="vehicle.maintenance.toDate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="toDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" maxLength="10" placeholder="dd/mm/yyyy" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label" for="department"><spring:message
							code="vehicle.deptId" text="Department" /></label>
					<div class="col-sm-4">
					    <form:select path="PetrolRequisitionDTO.department"
							cssClass="chosen-select-no-results form-control" id="department" data-rule-required="true"
							onchange="getVehicleNoByDept()" >
							<form:option value="0">
								<spring:message code="oem.select" text="Select" />
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

					<label class="col-sm-2 control-label" for="vehicle"><spring:message
							code="oem.warranty.vehicleNumber" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select id="veNo" path="PetrolRequisitionDTO.veNo" cssClass="chosen-select-no-results form-control"
							data-rule-required="true"  >
							<form:option value="0">
								<spring:message code="oem.select" text="Select" />
							</form:option>
							<c:forEach items="${ListVehicles}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.vehicleTypeDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
			
				<div class="text-center clear padding-10">
					<%-- Defect #154109 --%>
					<button type="button" id="search" class="btn btn-blue-2"
						title='<spring:message code="vehicle.search" text="Search" />'
						onclick="searchPetrolRequest()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.search" text="Search" />
					</button>

					<button type="button" id="reset"
						title='<spring:message code="vehicle.reset" text="Reset" />'
						onclick="window.location.href='petrolRequisitionForm.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.reset" text="Reset" />
					</button>

					<button type="button" id="add" class="btn btn-blue-2"
						title='<spring:message code="fueling.pump.master.add" text="Add" />'
						onclick="openForm('petrolRequisitionForm.html','petrolRequest')" title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="fueling.pump.master.add" text="Add" />
					</button>
				</div>

				<!-- Table Grid Start -->

				<div class="table-responsive">
					<table class="table table-striped table-bordered"
						id="petrolReqFormDataTable">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message
										code="audit.mgmt.srno" text="Sr.No" /></th>
								<th width="8%" align="center"><spring:message
										code="VehicleLogBookDTO.date" text="Date" /></th>
								<th width="10%" align="center"><spring:message
										code="vehicle.deptId" text="Department" /></th> 
								<th width="8%" align="center"><spring:message
										code="vehicle.vehicleRegNo" text="Vehicle Number" /></th>
								<th width="8%" align="center"><spring:message code="vehicle.master.vehicle.chasis.no"
										text="Vehicle No." /></th>
								<th width="5%" align="center"><spring:message
										code="VehicleLogBookDTO.driverName" text="Driver Name" /></th>
								<th width="8%" align="center"><spring:message
										code="vehicle.request.status" text="Request Status" /></th>
								<th width="5%" align="center"><spring:message
										code="DailyIncidentRegisterDTO.form.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${PetrolRequisitionData}" var="petrol"
								varStatus="item">
								<tr>
									<td class="text-center">${item.count}</td>
									<td><span class="hide"><fmt:formatDate pattern="yyyyMMdd" value="${petrol.date}" />
										</span> <fmt:formatDate pattern="dd/MM/yyyy" value="${petrol.date}" /></td>
									<td>${petrol.deptDesc}</td>		
									<td>${petrol.veNo}</td>
									<td>${petrol.veChasisSrno}</td>
									<td>${petrol.driverDesc}</td>
								    <td>
								    	<c:choose>
								    		<c:when test="${petrol.requestStatus eq 'OPEN'}">
								    			<spring:message code="purchase.maintenance.pending.status" text="Pending" />
								    		</c:when>
								    		<c:when test="${petrol.requestStatus eq 'REJECTED'}">
								    			<spring:message code="vehicle.maintenance.reject.status" text="Rejected" />
								    		</c:when>
								    		<c:when test="${petrol.requestStatus eq 'APPROVED'}">
								    			<spring:message code="vehicle.approved" text="APPROVED" />
								    		</c:when>
								    	</c:choose>
								    </td>										
								
									 <td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="<spring:message code="vehicle.view" text="View"/>"
											onclick="modifyPetrolRequest('${petrol.requestId}','petrolRequisitionForm.html','viewPETROL','V')">
											<i class="fa fa-eye"></i>
										</button> 
										</td> 
									<%-- 	<button type="button" class="btn btn-warning btn-sm"
											title="Edit Petrol Request Form"
											onclick="modifyPetrolRequest('${petrol.requestId}','petrolRequisitionForm.html','editPETROL','E')">
											<i class="fa fa-pencil"></i>
										</button> --%>
									
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				

			</form:form>
		</div>
	</div>
</div>
























