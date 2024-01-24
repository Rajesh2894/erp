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
<script type="text/javascript"
	src="js/security_management/contractualStaffMaster.js"></script>

<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="ContractualStaffMaster.form.name"
						text="Contractual Staff Master" /></strong>
			</h2>
		</div>
		<apptags:helpDoc url="ContractualStaffMaster.html" />
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>
			<!-- End mand-label -->


			<!-- Start Form -->
			<form:form action="ContractualStaffMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmContractualStaffMaster" id="frmContractualStaffMaster">

				<!-- Start Validation include tag -->
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" /> --%>
				<!-- End Validation include tag -->

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "> <!-- required-control -->
						<spring:message code="ContractualStaffMasterDTO.vendorId"
							text="VendorList" />
					</label>
					<div class="col-sm-4">
						<form:select id="vmVendorid" path="dto.vendorId"
							cssClass="form-control vmVendorid">
							<form:option value="">
								<spring:message code="select" text="Select " />
							</form:option>
							<c:forEach items="${VendorList}" var="Vendor">
								<form:option value="${Vendor.vmVendorid}"
									code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}-${Vendor.vmVendorname}
													</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 " for="location"> <!-- required-control -->
						<spring:message code="ContractualStaffMasterDTO.locId"
							text="Location" /></label>
					<div class="col-sm-4">
						<form:select id="location" path="dto.locId"
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

				<div class="form-group">


					<label class="control-label col-sm-2" for=""> <spring:message
							code="ContractualStaffMasterDTO.cpdShiftId" text="Current Shift" /></label>

					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="dto.cpdShiftId" cssClass="mandColorClass form-control"
						hasId="true" selectOptionLabelCode="selectdropdown" />

					<label class="control-label col-sm-2" for=""> <spring:message
							code="ContractualStaffMasterDTO.dayPrefixId" text="Weekly Off" /></label>

					<c:set var="baseLookupCode" value="DAY" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="dto.dayPrefixId" cssClass="mandColorClass form-control"
						hasId="true" selectOptionLabelCode="selectdropdown" />
				</div>
				
				<!-- Defect #143857 -->
				<%-- <div class="form-group">
					<apptags:input labelCode="ContractualStaffMasterDTO.contStaffName"
						path="dto.contStaffName" cssClass="hasNameClass" maxlegnth="20" />
				</div> --%>


				<!-- Start button -->
				<div class="text-center clear padding-10">

					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchContractStaff()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="ContractualStaffMasterDTO.form.search"
							text="Search" />
					</button>

					
					<button type="button" id="reset"
						onclick="window.location.href='ContractualStaffMaster.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="ContractualStaffMasterDTO.form.reset"
							text="Reset" />
					</button>
					
					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="DeploymentOfStaffDTO.form.back" text="Back" />
					</button>

					<button type="button" id="add" class="btn btn-success add"
						onclick="addContractualStaffMaster('ContractualStaffMaster.html','ADD','A');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="ContractualStaffMasterDTO.form.add"
							text="Add" />
					</button>

				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="frmContractualStaffMasterTbl">
							<thead>
								<tr>
									<th><spring:message code="lgl.Srno" text="Sr. No." /></th>
									<th><spring:message
											code="ContractualStaffMasterDTO.contStaffIdNo"
											text="ID Number" /></th>
									<th><spring:message
											code="ContractualStaffMasterDTO.contStaffName" text="Name" /></th>
									
									<th><spring:message
											code="ContractualStaffMasterDTO.desgId" text="Designation" /></th>
									<th><spring:message
											code="ContractualStaffMasterDTO.vendorId" text="Agency" /></th>
									<th><spring:message code="LocationDetailsOfStaffDTO.locId"
											text="Current Location" /></th>
									<th><spring:message
											code="ContractualStaffMasterDTO.cpdShiftId"
											text="Current Shift" /></th>
									<th><spring:message code="ContractualStaffMasterDTO.status" text="Status"></spring:message></th>
									<th><spring:message code="ContractualStaffMasterDTO.action"
											text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contractualStaffMasters}" var="master"
									varStatus="loop">
									<tr>

										<td align="center">${loop.count}</td>
										
										<c:choose>
											<c:when test="${empty master.contStaffIdNo}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.contStaffIdNo }</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty master.contStaffName}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.contStaffName }</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty master.desiDesc}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.desiDesc }</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty master.vendorDesc}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${master.vendorDesc }</td>
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

										<td class="text-center"><c:choose>
												<c:when test="${master.status eq 'A'}">
													<a href="#" class="fa fa-check-circle fa-2x green "
														title="Active"></a>
												</c:when>
												<c:otherwise>
													<a href="#" class="fa fa-times-circle fa-2x red "
														title="InActive"></a>
												</c:otherwise>
											</c:choose></td>
										<td class="text-center">

											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View CourtDetails"
												onclick="getContractualStaffMaster(${master.contStsffId},'ContractualStaffMaster.html','VIEW','V')">
												<i class="fa fa-eye"></i>
											</button> 
											<c:if test="${master.status eq 'A'}">
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit CourtDetails"
													onclick="getContractualStaffMaster(${master.contStsffId},'ContractualStaffMaster.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
												</button>
											</c:if>

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





