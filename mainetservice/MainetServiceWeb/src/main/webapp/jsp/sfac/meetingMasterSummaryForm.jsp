<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/meetingMasterForm.js"></script>
<style>
	@media (max-width: 480px) {
		table#meetingDatatable tbody tr td button {
			margin: 0 !important;
		}
	}
</style>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.meeting.summary.title"
					text="Summary Meeting" />
			</h2>
			<apptags:helpDoc url="MeetingMasterForm.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="MeetingMasterForm.html" cssClass="form-horizontal"
				id="MeetingMasterSummaryForm" name="MeetingMaster">
				<!-- Start Validation include tag -->
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" />--%>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="form-group">
					<label class="control-label col-sm-2 " for="MeetingType"><spring:message
							code="sfac.meeting.type" text="Purpose of the Meeting"></spring:message></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="masterDto.meetingTypeId"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

					<apptags:input labelCode="sfac.meeting.meetingNo" maxlegnth="15"
						path="masterDto.meetingNo" cssClass="form-control"></apptags:input>
				</div>



				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						onclick="searchForm(this);">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='MeetingMasterForm.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary"
						onclick="addMeetingMaster('MeetingMasterForm.html','addMeetingDetails');"
						title="Create Meeting Request">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add" />
					</button>

				</div>


				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="meetingDatatable">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="council.srno" text="Sr.No" /></th>
								<th><spring:message code="sfac.meeting.type"
										text="Meeting Type" /></th>
								<th><spring:message code="sfac.meeting.meetingNo"
										text="Meeting Number" /></th>
								<th><spring:message code="sfac.meeting.date"
										text="Meeting Date" /></th>
								<th><spring:message code="sfac.meeting.time"
										text="Meeting Time" /></th>
								<th><spring:message code="sfac.meeting.place"
										text="Meeting Place" /></th>
								<th width="10%"><spring:message code="sfac.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<%-- <c:forEach items="${command.meetingMasterDtoList}" var="meeting"
								varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td align="center">${meeting.meetingTypeName}</td>
									<td align="center">${meeting.meetingNo}</td>
									<td align="center">${meeting.meetingDateDesc}</td>
									<td align="center">${meeting.meetingTime}</td>
									<td align="center">${meeting.meetingPlace}</td>


									<td class="text-center">
										<button type="button"
											class="btn btn-blue-2 btn-sm margin-right-10 "
											name="button-plus" id="button-plus"
											onclick="showGridOption(${meeting.meetingId},'V')"
											title="
									        <spring:message code="council.button.view" text="view"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button"
											class="btn btn-danger btn-sm btn-sm margin-right-10"
											name="button-123" id=""
											onclick="showGridOption(${meeting.meetingId},'E')"
											title="<spring:message code="council.button.edit" text="edit"></spring:message>">
											<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										</button>

									</td>
								</tr>
							</c:forEach> --%>
						</tbody>
					</table>
				</div>


				<!-- End button -->
			</form:form>
		</div>
	</div>
</div>
