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
<script type="text/javascript" src="js/security_management/contractualStaffMaster.js"></script>

<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="ContractualStaffMaster.form.name"
						text="Contractual Staff Master" /></strong>
			</h2>
		</div>
		<apptags:helpDoc url="ContractualStaffMaster.html" />
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message code="leadlift.master.ismand" />
				</span>
			</div>
			<!-- End mand-label -->


			<!-- Start Form -->
			<form:form action="ContractualStaffMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmContractualStaffMaster" id="frmContractualStaffMaster">

				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="submit" class="button-input btn btn-success"
						onclick="addContractualStaffMaster('ContractualStaffMaster.html','ADD','A');"
						name="button-Add" style="" id="button-submit">
						<spring:message code="bt.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered" id="frmContractualStaffMasterTbl">
							<thead>
								<tr>
									<th><spring:message code="lgl.Srno" text="Sr. No." /></th>
									<th><spring:message code="ContractualStaffMasterDTO.name" text="Name" /></th>
									<th><spring:message code="ContractualStaffMasterDTO.idNumber" text="ID Number" /></th>
									<th><spring:message code="ContractualStaffMasterDTO.desgId" text="Designation" /></th>
									<th><spring:message code="ContractualStaffMasterDTO.vendorId" text="Agency" /></th>
									<th><spring:message code="tualStaffMasterDTO.locId" text="Current Location" /></th>
									<th><spring:message code="ContractualStaffMasterDTO.currentShift" text="Current Shift" /></th>
									<th><spring:message code="master.status" text="Status"></spring:message></th> 
									<th><spring:message code="comparamMas.action" text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contractualStaffMasters}" var="master" varStatus="loop">
									<tr>
										<td align="center">${loop.count}</td>
										<td align="center">${master.name }</td>
										<td align="center">
											<%-- ${command.getNonHierarchicalLookUpObject(court.crtType).getLookUpDesc()} --%>
										</td>
         
         								<td align="center"></td>
										<td align="center"></td>
										<td align="center"></td>
										<td align="center"></td>
         								
										<td class="text-center">
										<c:choose>
											<c:when test="${court.crtStatus eq 'Y'}">
												<a href="#" class="fa fa-check-circle fa-2x green "
													title="Active"></a>	
											</c:when>
											<c:otherwise>
												<a href="#" class="fa fa-times-circle fa-2x red "
													title="InActive"></a>
											</c:otherwise>
										</c:choose>
										</td>	
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View CourtDetails"
												onclick="getContractualStaffMaster(${master.staffId},'ContractualStaffMaster.html','EDIT','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit CourtDetails"
												onclick="getContractualStaffMaster(${master.staffId},'ContractualStaffMaster.html','EDIT','E')">
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





