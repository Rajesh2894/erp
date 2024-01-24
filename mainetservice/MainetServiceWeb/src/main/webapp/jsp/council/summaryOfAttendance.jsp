<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->

<script type="text/javascript" src="js/council/councilAttendance.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.attendance.summary.title"
					text="Member Attendance Summary " />
			</h2>
			<apptags:helpDoc url="CouncilAttendanceMaster.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CouncilAttendanceMaster.html"
				cssClass="form-horizontal" id="CouncilAttendanceMaster"
				name="CouncilAttendanceMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->


				<div class="form-group">
					<label class="control-label col-sm-2 " for="MeetingType"><spring:message
							code="council.meetingType" text="Meeting Type"></spring:message></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="couMeetingMasterDto.meetingTypeId"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

					<apptags:input labelCode="council.meeting.meetingNo"
						path="couMeetingMasterDto.meetingNo" cssClass="form-control"></apptags:input>
				</div>

				<!-- date picker input set -->
				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="council.from.date"
						datePath="couMeetingMasterDto.fromDate"></apptags:date>
					<apptags:date fieldclass="datepicker" labelCode="council.to.date"
						datePath="couMeetingMasterDto.toDate"></apptags:date>
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchMeetingAttendance">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='CouncilAttendanceMaster.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.reset" text="Reset" />
					</button>
					<!-- doing this because we need meetingId at the time of attendance button click-->
					<%-- <c:set var="meeting" value="${command.councilMeetingMasterDtoList[0]}"/>
					<input type="hidden" id= "meetingId" value = "${not empty meeting ? meeting.meetingId : '-1'}"/>
					<c:set var="meeting" value="${command.councilMeetingMasterDtoList[0]}"/>
					<c:set id = "meetingIdValue" var="meetingId" value = "${not empty meeting ? meeting.meetingId : '-1'}" ></c:set>
					<button type="button" class="btn btn-primary"
						onclick="addAttendanceMaster('CouncilAttendanceMaster.html','addMeetingAttendance');"
						title="Attendance">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.addAttendance" />
					</button> --%>
					<button type="button" class="btn btn-primary"
						onclick="addAttendanceMaster('CouncilAttendanceMaster.html','addMeetingAttendance');"
						title="Attendance">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.add" text="Add" />
					</button>
				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="attendanceSummaryDataTables">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="council.srno" text="Sr.No" /></th>
								<th class="text-center"><spring:message
										code="council.meeting.date" text="Meeting Date" /></th>
								<th class="text-center"><spring:message
										code="council.meetingType" text="Meeting Type" /></th>
								<th class="text-center"><spring:message
										code="council.attendance.totalMembers" text="Total Members" /></th>
								<th class="text-center"><spring:message
										code="council.attendance.noOfMembersPresent"
										text="Number of Members Present" /></th>
								<th class="text-center"><spring:message
										code="council.attendance.noOfMembersAbsent"
										text="Number of Members Absent" /></th>
								<th class="text-center"><spring:message
										code="council.meeting.status" text="Meeting Status" /></th>
								<th class="text-center"><spring:message
										code="council.member.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.councilMeetingMasterDtoList}"
								var="meeting" varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td align="center">${meeting.meetingDateDesc}</td>
									<td align="center">${meeting.meetingTypeName}</td>
									<td align="center">${meeting.totalMember}</td>
									<td align="center">${meeting.memberPresent}</td>
									<td align="center">${meeting.memberAbsent}</td>
									<td align="center">${meeting.meetingStatus}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm margin-right-10 "
											name="button-plus" id="button-plus"
											onclick="showGridOption(${meeting.meetingId},'V')"
											title="
									      <spring:message code="council.button.view" text="view"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-10" name="button-123"
											id="" onclick="showGridOption(${meeting.meetingId},'E')"
											title="<spring:message code="council.button.edit" text="edit"></spring:message>">
											<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->