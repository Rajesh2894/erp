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
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="js/security_management/shiftMaster.js"></script>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="ShiftMasterDTO.shiftMaster"
						text="Shift Master" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>
			<!-- End mand-label -->

			<form:form action="ShiftMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmShiftMasterSearch" id="frmShiftMasterSearch">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="ShiftMasterDTO.shiftId" text="Current Shift" />
					</label>
					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="shiftMasterDTO.shiftId" isMandatory="true"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchShift()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="ShiftMasterDTO.form.search" text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='ShiftMaster.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="ShiftMasterDTO.form.reset" text="Reset" />
					</button>

					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="ShiftMasterDTO.form.back" text="Back" />
					</button>

					<button type="button" id="add" class="btn btn-success add"
						onclick="addShiftMaster('ShiftMaster.html','ADD','A');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="ShiftMasterDTO.form.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="frmShiftMasterTbl">
							<thead>
								<tr>
									<th><spring:message code="DeploymentOfStaffDTO.Srno" text="Sr No." /></th>
									<th><spring:message code="ShiftMasterDTO.shiftId"
											text="Shift" /></th>
									<th><spring:message code="ShiftMasterDTO.fromTime"
											text="From Time" /></th>
									<th><spring:message code="ShiftMasterDTO.toTime"
											text="To Time" /></th>
									<th><spring:message code="ShiftMasterDTO.isCrossDayShift"
											text="Cross Day Shift" /></th>
									<th><spring:message code="ShiftMasterDTO.isGeneralShift"
											text="General Shift" /></th>
									<th><spring:message code="ShiftMasterDTO.status" 
											text="Status"></spring:message></th>
									<th width="100"><spring:message code="DailyIncidentRegisterDTO.form.action"
											text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${shiftList}" var="master" varStatus="loop">
									<tr>
											<td align="center">${loop.count}</td>
											<td align="center">${master.shiftIdDesc }</td>
											<td align="center">${master.fromTime }</td>
											<td align="center">${master.toTime }</td>
											<td align="center">${master.isCrossDayShift }</td>
											<td align="center">${master.isGeneralShift }</td>
										    <%-- <td align="center">${master.status}</td> 
										    <td class="text-center"> --%>
											
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
											<c:if test="${master.status eq 'A' || master.status eq 'I'}">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View"
													onclick="getShiftdata(${master.shiftMasId},'ShiftMaster.html','VIEW','V')">
													<i class="fa fa-eye"></i>
												</button>
													</c:if>
												<c:if test="${master.status eq 'A' || master.status eq 'I'}">
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit "
													onclick="getShiftdata(${master.shiftMasId},'ShiftMaster.html','EDIT','E')">
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