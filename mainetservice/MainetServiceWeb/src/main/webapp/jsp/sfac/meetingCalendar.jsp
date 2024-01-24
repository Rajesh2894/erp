<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet"
	type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="assets/libs/fullcalendar/fullcalendar.min.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/meetingCalendar.js"></script>
<!-- End JSP Necessary Tags -->
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>



<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Meeting Calender" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">
			<form:form action="MeetingCalendar.html" class="form-horizontal form"
				name="EventDate" id="EventDate">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div class="accordion-toggle ">
					<!-- <div class="col-sm-2">
					</div>
				
					<div class="col-sm-8">
					  <div class="calendar-section">
					    <div id='calendar'></div>
					  </div> 
					</div>
					<div class="col-sm-2">
					</div>
				 -->

					<div class="form-group">
					<label class="control-label col-sm-2  required-control"
						for="meetingType"><spring:message code="sfac.meeting.type"
							text="Meeting Type"></spring:message></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="masterDto.meetingTypeId" changeHandler="getMeetingNoList();"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

						<label class="control-label col-sm-2 required-control"> <spring:message
								code="" text="Meeting No." />
						</label>
						<div class="col-sm-4">
							<form:select
								class=" mandColorClass form-control chosen-select-no-results"
								path="masterDto.meetingNo" id="meetingNo" >
								<form:option value="0">
									<spring:message code="sfac.select" text="Select" />
								</form:option>
								<c:forEach items="${command.mastDto}" var="dto">
									<form:option value="${dto.meetingId}">${dto.meetingNo}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group searchBtn">
					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2 " id="serchBtn"
							onclick="getEvent(this)">
							<i class="fa fa-search"></i>
							<spring:message code="property.changeInAss.Search" />
						</button>
						<button type="button" class="btn btn-warning " id="resetBtn"
							onclick="window.location.href='MeetingCalendar.html'">
							<spring:message code="legal.btn.reset" text="Reset" />
						</button>

						<button type="button" class="btn btn-danger" id="btn3"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="legal.btn.back" text="Back" />
						</button>
					</div>
				</div>
				
				</div>

			</form:form>

		</div>

	</div>

</div>

