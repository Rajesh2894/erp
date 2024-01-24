<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/validity_master/licenseValidityMaster.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="license.validity.title" text="License Validity Master" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form action="LicenseValidityMaster.html"
				name="licenseValidateForm" id="licenseValidateForm"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="license.validity.dept.name" text="Department Name" /></label>

					<div class="col-sm-4">

						<form:select path="" id="departmentId"
							class="chosen-select-no-results" data-rule-required="true" onchange="searchServicesBydeptId()">
							<form:option value="0">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.deparatmentList}" var="department">
								<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="license.validity.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:select path="" id="serviceId"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="0">
								<spring:message code='adh.select' />
							</form:option>
						</form:select>
					</div>
				</div>
				
				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						onclick="searchLicenseValidityMaster()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='LicenseValidityMaster.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="addLicenseValidityEntry()">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.add" text="Add"></spring:message>
					</button>
				</div>
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="licenseValidateTable">
							<thead>
								<tr>
									<th><spring:message
											code="license.validity.dept.name"
											text="Department Name" /></th>
									<th><spring:message
											code="license.validity.service.name"
											text="Sevice Name" /></th>
									<th><spring:message
											code="license.validity.license.type"
											text="License Type" /></th>
									<th><spring:message
											code="license.validity.validate.depends.on"
											text="Validate Depends on" /></th>
									<th><spring:message
											code="license.validity.tenure" text="Tenure" /></th>
											<th><spring:message
											code="license.validity.unit" text="Unit" /></th>
									<th><spring:message code="adh.view.edit" text="View/Edit" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.masterDtoList}"
									var="masterList">
									<tr>
										<td>${masterList.deptName}</td>
										<td>${masterList.serviceName}</td>
										<td>${masterList.licTypeDesc}</td>
										<td>${masterList.licDependsOnDesc}</td>
										<td>${masterList.licTenure}</td>
										<td>${masterList.unitDesc}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View License Validity Master"
												onclick="editOrViewLicValidityMaster(${masterList.licId},'V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit license validity Master"
												onclick="editOrViewLicValidityMaster(${masterList.licId},'E')">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
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
