<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/security_management/deploymentOfStaff.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="DeploymentOfStaffDTO.form.name" text="Deployment Of Staff" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">
		<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message code="leadlift.master.ismand" />
				</span>
			</div>
			<!-- End mand-label -->
			<form:form action="DeploymentOfStaff.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmDeploymentStaffForm" id="frmDeploymentStaffForm">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 " for=""> <spring:message
							code="DeploymentOfStaffDTO.empTypeId" text="Employee Type" />
					</label>
					<c:set var="baseLookupCode" value="EMT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="deploymentOfStaffDTO.empTypeId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />

					<label class="col-sm-2 control-label "> <spring:message
							code="DeploymentOfStaffDTO.vendorId" text="Agency Name" />
					</label>
					<div class="col-sm-4">
						<form:select id="vendorId" path="deploymentOfStaffDTO.vendorId"
							cssClass="form-control vmVendorid " data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select " />
							</form:option>
							<c:forEach items="${VendorList}" var="Vendor">
								<form:option value="${Vendor.vmVendorid}"
									code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}-${Vendor.vmVendorname}
								</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<%-- <label class="control-label col-sm-2 " for=""> <spring:message
							code="DeploymentOfStaffDTO.cpdShiftId" text="Current Shift" />
					</label>
					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="deploymentOfStaffDTO.cpdShiftId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" isMandatory="true" /> --%>
						
					<label class="control-label col-sm-2 " for=""> <spring:message
							code="DeploymentOfStaffDTO.cpdShiftId" text="Current Shift" /></label>
					<div class="col-sm-4">
						<form:select id="cpdShiftId" path="deploymentOfStaffDTO.cpdShiftId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.lookup}" var="lookup">
								<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 " for="location"> <spring:message
							code="ContractualStaffMasterDTO.locId" text="Location" /></label>
					<div class="col-sm-4">
						<form:select id="locId" path="deploymentOfStaffDTO.locId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchStaff(this);" title='<spring:message code="DeploymentOfStaffDTO.form.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="DeploymentOfStaffDTO.form.search"
							text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='DeploymentOfStaff.html'"
						class="btn btn-warning" title='<spring:message code="DeploymentOfStaffDTO.form.reset" text="Reset" />'>
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="DeploymentOfStaffDTO.form.reset"
							text="Reset" />
					</button>

					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" title='<spring:message code="DeploymentOfStaffDTO.form.back" text="Back" />'
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="DeploymentOfStaffDTO.form.back" text="Back" />
					</button>

					<button type="button" id="add" class="btn btn-success add"
						onclick="addDeploymentStaff('DeploymentOfStaff.html','ADD','A');"
						title='<spring:message code="ContractualStaffMasterDTO.form.add" text="Add" />'>
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="ContractualStaffMasterDTO.form.add"
							text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="frmDeploymentStaffTbl">
							<thead>
								<tr>
									<th><spring:message code="DeploymentOfStaffDTO.Srno"
											text="Sr. No." /></th>
									<th><spring:message
											code="DeploymentOfStaffDTO.contStaffName" text="Name" /></th>
									<th><spring:message
											code="DeploymentOfStaffDTO.contStaffMob" text="Mobile Number" /></th>
									<th><spring:message code="ContractualStaffMasterDTO.locId" text="Location" /></th>
									<th><spring:message code="DeploymentOfStaffDTO.cpdShiftId"
											text="Current Shift" /></th>
									<th><spring:message
											code="DeploymentOfStaffDTO.contStaffSchFrom" text="From Date"></spring:message></th>
									<th><spring:message
											code="DeploymentOfStaffDTO.contStaffSchTo" text="To Date" /></th>
									<th><spring:message code="DeploymentOfStaffDTO.action"
											text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${deploymentOfStaffs}" var="master"
									varStatus="loop">
									<tr>

										<td align="center">${loop.count}</td>

										<c:choose>
											<c:when test="${empty master.contStaffName}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.contStaffName }</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty master.contStaffMob}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.contStaffMob }</td>
											</c:otherwise>
										</c:choose>
										
										<c:choose>
											<c:when test="${empty master.locDesc}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.locDesc }</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty master.shiftDesc}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.shiftDesc }</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty master.contStaffSchFrom}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">
												<fmt:formatDate pattern="dd/MM/yyyy"
												value="${master.contStaffSchFrom }" />
												</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty master.contStaffSchTo}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">
												<fmt:formatDate pattern="dd/MM/yyyy"
												value="${master.contStaffSchTo }" />
												</td>
											</c:otherwise>
										</c:choose>

										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Details"
												onclick="getStaff(${master.deplId},'DeploymentOfStaff.html','VIEW','V')">
												<i class="fa fa-eye"></i>
											</button> 
											
												<%-- <button type="button" class="btn btn-warning btn-sm"
													title="Edit Details"
													onclick="getStaff(${master.deplId},'DeploymentOfStaff.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
												</button> --%>
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