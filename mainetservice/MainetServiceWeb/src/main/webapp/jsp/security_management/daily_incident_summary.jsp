<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />

<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/security_management/dailyIncidentReg.js"></script>

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
<style>
table#incidentDataTable tbody tr td:nth-child(4) {
	word-break: break-all;
}
</style>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="DailyIncidentRegisterDTO.form.name"
						text="Daily Incident Register" /></strong>
				<apptags:helpDoc url="dailyIncidentRegister.html"></apptags:helpDoc>
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
			<form:form action="dailyIncidentRegister.html"
				name="frmDailyIncident" id="frmDailyIncident" method="POST"
				commandName="command" class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="DailyIncidentRegisterDTO.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dailyIncidentRegisterDTO.fromDate" id="fromDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true" placeholder=""
								maxLength="10" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="DailyIncidentRegisterDTO.toDate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dailyIncidentRegisterDTO.toDate" id="toDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true" placeholder=""
								maxLength="10" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div>


				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchDailyIncident()" title='<spring:message code="DailyIncidentRegisterDTO.form.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="DailyIncidentRegisterDTO.form.search"
							text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='dailyIncidentRegister.html'"
						class="btn btn-warning" title='<spring:message code="DailyIncidentRegisterDTO.form.reset" text="Reset" />'>
						<spring:message code="DailyIncidentRegisterDTO.form.reset"
							text="Reset" />
					</button>

					<button type="button" id="add" class="btn btn-blue-2"
						onclick="openForm('dailyIncidentRegister.html','dailyIncident')"
						title='<spring:message code="DailyIncidentRegisterDTO.form.add" text="Add" />'>
						<spring:message code="DailyIncidentRegisterDTO.form.add"
							text="Add" />
					</button>
					
					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						title='<spring:message code="DeploymentOfStaffDTO.form.back" text="Back" />'
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<spring:message code="DeploymentOfStaffDTO.form.back" text="Back" />
					</button>
				</div>

				<!-- Table Grid Start -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="incidentDataTable">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message
										code="audit.mgmt.srno" text="Sr.No" /></th>
								<th width="10%" align="center"><spring:message
										code="DailyIncidentRegisterDTO.date" text="Date" /></th>
								<%-- <th width="10%" align="center"><spring:message
										code="DailyIncidentRegisterDTO.time" text="Time" /></th> --%>
								<th width="25%" align="center"><spring:message
										code="DailyIncidentRegisterDTO.remarks" text="Remarks" /></th>
								<th width="25%" align="center"><spring:message
										code="DailyIncidentRegisterDTO.nameVisitingOff"
										text="Name of Visiting Officer" /></th>
								<th width="8%" align="center"><spring:message
										code="DailyIncidentRegisterDTO.form.action" text="Action" /></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${incidents}" var="incident" varStatus="item">

								<tr>
									<td class="text-center">${item.count}</td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${incident.date}" /></td>
									<%-- <td>${incident.time}</td> --%>
									<td>${incident.remarks}</td>
									<td>${incident.nameVisitingOffJoin}</td>

									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View Incident Register"
											onclick="modifyIncident('${incident.incidentId}','dailyIncidentRegister.html','viewDIR','V')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit Incident Register"
											onclick="modifyIncident('${incident.incidentId}','dailyIncidentRegister.html','editDIR','E')">
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






