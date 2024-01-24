<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/SLRMEMployee.js"></script>
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
				<spring:message code="swm.SLRM.employee.form" text="SLRM Employee Details Form"></spring:message>
			</h2>
			<apptags:helpDoc url="SLRMEmployeeMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="SLRMEmployeeMaster.html" name=""
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
						for="desposalsite"><spring:message code="swm.dsplsite" />
					</label>
					<div class="col-sm-4">
						<form:select path="sLRMEmployeeMasterDto.mrfId"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select"
							disabled="${command.saveMode eq 'V' ? true : false }" id="mrfId">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<c:forEach items="${command.mrfMasterList}" var="lookUp">
								<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label" for=""><spring:message
							code="swm.SLRM.employee.code" text="Employee Code" /> </label>
					<div class="col-sm-4">
						<form:input path="sLRMEmployeeMasterDto.empUId"
							cssClass="form-control  mandColorClass" id="empUId" disabled="" />
					</div>

				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchEmployeeList('SLRMEmployeeMaster.html','searchEmployeeMaster')">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<%-- Defect #155464 --%>
					<button type="button" class="btn btn-warning" onclick="javascript:openRelatedForm('SLRMEmployeeMaster.html');" title="Reset">
						<spring:message code="rstBtn" text="Reset" />
					</button>				
					<button type="button" class="btn btn-success add"
						onclick="openaddEmployeeMaster('SLRMEmployeeMaster.html','addEmployeeMaster');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="population.master.add.population" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_SLRMEmployeeTable">
							<thead>
								<tr>
									<th><spring:message code="population.master.srno" text="Sr.No" /></th>
									<th><spring:message code="swm.SLRM.employee.code" text="Employee Code" /></th>
									<th><spring:message code="employee.verification.employee.name" text="Employee Name" /></th>
									<th><spring:message code="vendor.verification.designation" text="Designation" /></th>
									<th><spring:message code="swm.SLRM.employee.associate.mrf" text="Associate MRF Center" /></th>
									<th><spring:message code="swm.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.sLRMEmployeeMasterDtoList}"
									var="employee" varStatus="loop">
									<tr>
										<td align="center">${loop.count}</td>
										<td align="center">${employee.empUId}</td>
										<td align="center">${employee.empName}&nbsp;
											${employee.empLName}</td>
										<td align="center">${employee.desigName}</td>
										<td align="center">${employee.mrfName}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"
												onclick="modifyEmployee(${employee.empId},'SLRMEmployeeMaster.html','viewEmployeeMaster')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit"
												onclick="modifyEmployee(${employee.empId},'SLRMEmployeeMaster.html','editEmployeeMaster')">
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
