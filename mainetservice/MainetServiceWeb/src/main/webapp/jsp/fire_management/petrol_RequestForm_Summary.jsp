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
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />

<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/fire_management/petrolRequisition.js"></script>

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

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Petrol Requisition Form" /></strong>
				<apptags:helpDoc url="petrolRequestForm.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="petrolRequestForm.html" id="frmPetrolRequestForm"
				name="frmPetrolRequestForm" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"> <spring:message
							code="" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="fromDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" maxLength="10" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"> <spring:message
							code="" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="toDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" maxLength="10" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label" for="department"><spring:message
							code="" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control" id="department"
							onchange="getEmployeeByDept(this);">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="vehicle"><spring:message
							code="" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select id="veNo" path="" cssClass="form-control"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListVehicles}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="searchPetrolRequest()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="VehicleLogBookDTO.form.search" text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='petrolRequestForm.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="VehicleLogBookDTO.form.reset" text="Reset" />
					</button>

					<input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="Back">

					<button type="button" id="add" class="btn btn-blue-2"
						onclick="openForm('petrolRequestForm.html','petrolRequest')" title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="VehicleLogBookDTO.form.add" text="Add" />
					</button>


					<!-- Table Grid Start -->

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="petrolReqFormDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="audit.mgmt.srno" text="Sr.No" /></th>
									<th width="8%" align="center"><spring:message
											code="" text="Date" /></th>
									<th width="10%" align="center"><spring:message
											code="" text="Department" /></th> 
									<th width="8%" align="center"><spring:message
											code="" text="Vehicle Number" /></th>
									<th width="5%" align="center"><spring:message
											code="" text="Driver Name" /></th>
									<th width="8%" align="center"><spring:message
											code="" text="Request Status" /></th>
									<th width="5%" align="center"><spring:message
											code="DailyIncidentRegisterDTO.form.action" text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${PetrolRequisitionData}" var="petrol"
									varStatus="item">
									<tr>
										<td class="text-center">${item.count}</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${petrol.date}" /></td>
										<td>${petrol.deptDesc}</td>		
										<td>${petrol.veNo}</td>
										<td>${petrol.driverDesc}</td>
									    <td>${petrol.requestStatus}</td>										
									
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Petrol Request Form"
												onclick="modifyPetrolRequest('${petrol.requestId}','petrolRequestForm.html','viewPETROL','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Petrol Request Form"
												onclick="modifyPetrolRequest('${petrol.requestId}','petrolRequestForm.html','editPETROL','E')">
												<i class="fa fa-pencil"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

				</div>

			</form:form>
		</div>
	</div>
</div>
























