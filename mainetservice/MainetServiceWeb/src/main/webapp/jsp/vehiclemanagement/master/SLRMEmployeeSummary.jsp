<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/vehicle_management/VehicleEMployee.js"></script>
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
				<spring:message code="vehicle.employee.details"
					text="Vehicle Employee Details Form"></spring:message>
			</h2>
			<apptags:helpDoc url="vehicleEmpDetails.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="vehicleEmpDetails.html" name=""
				id="PopulationMasterList" class="form-horizontal">
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

					<label class="col-sm-2 control-label required-control"
						for="department"><spring:message code="vehicle.deptId"
							text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="sLRMEmployeeMasterDto.mrfId"
							disabled="${command.saveMode eq 'V'}" cssClass="form-control chosen-select-no-results"
							id="mrfId" data-rule-required="true" isMandatory="true"
							onchange="">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.deptObjectList}" var="departmentt">								
								<c:if test="${userSession.languageId eq 1}">
									<form:option value="${departmentt[0]}">${departmentt[1]}</form:option>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<form:option value="${departmentt[0]}">${departmentt[2]}</form:option>
								</c:if>								
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="employee.verification.employee.name" text="Employee Name" />
					</label>
					<div class="col-sm-4">
						<form:select path="sLRMEmployeeMasterDto.empId"
							disabled="${command.saveMode eq 'V'}" cssClass="form-control chosen-select-no-results"
							id="empId" data-rule-required="true" isMandatory="true">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${sLRMEmployeeMasterlist}" var="lookup">
								<form:option value="${lookup.empId}">${lookup.empName} ${lookup.empLName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchEmployeeList('vehicleEmpDetails.html','searchEmployeeMaster')"
						title="<spring:message code="solid.waste.search" text="Search"></spring:message>">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" id="reset"
						onclick="window.location.href='vehicleEmpDetails.html'" class="btn btn-warning"
						title="<spring:message code="vehicle.reset" text="Reset" />">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-success add"
						onclick="openaddEmployeeMaster('vehicleEmpDetails.html','addEmployeeMaster');"
						title="<spring:message code="population.master.add.population" text="Add" />">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="population.master.add.population" text="Add" />
					</button>
				</div>

				<div class="table-responsive margin-top-10">
					<table class="table table-striped table-condensed table-bordered"
						id="id_SLRMEmployeeTable">
						<thead>
							<tr>
								<th><spring:message code="population.master.srno"
										text="Sr.No" /></th>
								<th><spring:message
										code="employee.verification.employee.name"
										text="Employee Name" /></th>
								<th><spring:message code="vendor.verification.designation"
										text="Designation" /></th>
								<th><spring:message code="vehicle.deptId"
										text="Department" /> </th>
								<th><spring:message code="swm.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.sLRMEmployeeMasterDtoList}"
								var="employee" varStatus="loop">
								<tr>
									<td align="center">${loop.count}</td>
									<%-- <td align="center">${employee.empUId}</td> --%>
									<td align="center">${employee.empName}&nbsp;
										${employee.empLName}</td>
									<td align="center">${employee.desigName}</td>
									<td align="center">${employee.deptName}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="<spring:message code="vehicle.view" text="View"/>"
											onclick="modifyEmployee(${employee.empId},'vehicleEmpDetails.html','viewEmployeeMaster')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="<spring:message code="vehicle.edit" text="Edit"/>"
											onclick="modifyEmployee(${employee.empId},'vehicleEmpDetails.html','editEmployeeMaster')">
											<i class="fa fa-pencil"></i>
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
