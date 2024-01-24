<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/appointmentReschedule.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="mrm.appointResch"
					text="Appointment Reschedule" />
			</h2>

		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="AppointmentReschedule.html"
				cssClass="form-horizontal" id="appResch">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="panel-group accordion-toggle">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="" href="#child-level"><spring:message
								code="mrm.appointResch" text="Appointment Reschedule" /></a>
					</h4>
					<form:hidden path="appointmentDTO.appointmentIdResc" id="appointmentIdResc" />
					<div id="child-level" class="collapse in">
						<div class="panel-body">
							<div class="form-group">
							

								<apptags:date fieldclass="datepicker"
									labelCode="mrm.appointment.appointmentDate"
									datePath="appointmentDTO.appointmentDateSearch"></apptags:date>
							</div>
							
							<div class="text-center clear padding-10">
								<button class="btn btn-blue-2  search" id="searchAppointment"
									type="button">
									<i class="button-input"></i>
									<spring:message code="mrm.button.search" />
								</button>
								<button type="button"
									onclick="window.location.href='AppointmentReschedule.html'"
									class="btn btn-warning" title="Reset">
									<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
									<spring:message code="mrm.button.reset" text="Reset" />
								</button>

							</div>
				

							<div class="table-responsive">
								<table class="table table-striped table-bordered"
									id="appointmentDataTable">
									<thead>
										<tr>
											<th  class="text-center"><spring:message
											code="mrm.srno" text="Sr No" /></th>
											<th  class="text-center"><spring:message
													code="mrm.appointment.appointmentDate" text="Appointment Date" /></th>
											<%-- <th  class="text-center"><spring:message
													code="" text="Appointment Time" /></th> --%>
											<th  class="text-center"><spring:message
													code="mrm.marriage.appNo" text="Application No" /></th>
											<th  class="text-center"><spring:message
													code="mrm.marriage.appName" text="Applicant Name" /></th>		
											<th width="9%" align="center" height="20px">
											<spring:message code="mrm.selectAll" text="Select All" /><br/>
											<input type="checkbox"  style="margin-left:-10px;position:relative;" class="checkbox-inline" id="selectall" />
										   </th></tr>
									</thead>
									<tbody>
										<c:forEach items="${command.appointmentList}" var="appointment"
											varStatus="status">
											<tr>
												<td class="text-center">${status.count}</td>
												<td class="text-center">${appointment.appointmentTime}</td>
												<%-- <td class="text-center">${appointment.appointmentTime}</td> --%>
												<td class="text-center">${appointment.marId.applicationId}</td>
												<td class="text-center">${appointment.marId.applicantName}</td>
												<td width="10%" align="center">
													<input type="checkbox" aria-label="select record ${status.count}"
														class="case" style="margin-top:5px;margin-left:-10px" name="case" value='${appointment.appointmentId}' />
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
							<div class="panel-body">

						<div class="form-group">
							<apptags:date fieldclass="datepicker"
								labelCode="mrm.appoinResc.resDate" isMandatory="true"
								datePath="marriageDTO.appointmentDTO.appointmentDate"></apptags:date>
							<label class="col-sm-2 control-label required-control"
								for="appointmentTime"> <spring:message
									code="mrm.appoinResc.resTime" text="Reschedule Time"/>
							</label>
							<div class="col-sm-4">
								<form:input path="marriageDTO.appointmentDTO.appointmentTime"
									class="form-control datetimepicker3 mandColorClass"
									maxlength="10" id="appointmentTime" />
							</div>
						</div>
						


					</div>

							<!-- Start button -->
							<div class="text-center">
							<button type="button" class="btn btn-success" title="Submit"
										onClick="saveAppointmentResc(this);">
										<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
										<spring:message code="mrm.button.submit" text="Submit" />
									</button>
								<button type="button" onclick="resetAppointmentResc(this);"
									class="btn btn-warning" title="Reset">
									<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
									<spring:message code="mrm.button.reset" text="Reset" />
								</button>



							</div>
							<!-- End button -->
						</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->

<script type="text/javascript">
/* $("#selectall").prop("checked", "checked");
$(".case").prop("checked", "checked"); */
</script>
