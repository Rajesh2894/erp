<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
.printer {
	width: 21cm;
	height: 30.4cm;
	padding: 20px 20px;
	border: 1px solid #eaeaea;
	margin: 0px auto;
	font-family: verdana, sans-serif;
	font-size: 13px;
	position: relative;
}

.printer table {
	border-collapse: collapse;
	width: 100%;
	font-size: 13px;
}

.tablular-content table th {
	background-color: #eeeeee !important;
	-webkit-print-color-adjust: exact;
	text-align: left;
}

.printer table td, .printer table th {
	padding: 3px;
	font-size: 12px;
	border: 1px solid #cccaca;
	padding: 5px;
}

.printer .content p {
	line-height: 21px;
}

.printer .header {
	text-align: center;
	margin-bottom: 20px;
	overflow: hidden;
}
.printer .header > div:nth-child(1) {
	padding-left: 0;
}

</style>
<div class="printer">
	<div class="tablular-content">
		 
		 <div class="header">
			<div class="col-xs-3 col-sm-3 col-lg-3 text-left">
				<img width="80" src="${userSession.orgLogoPath}">
			</div>
			
			<div class="col-xs-9 col-sm-9 col-lg-9" style="margin-left: -100px; margin-top: 20px;">
				<strong><spring:message code="council.mom.print.header" text="Minutes Of Meeting" /></strong><br /> 
				 <%-- <strong>${entity.ulbName}</strong> --%>
			 </div>
			 
		</div>
		 
			<table>
				<tr>
					<th style="width: 20%"><spring:message code="council.mom.print.ulbName" text="ULB Name" /></th>
					<td>${printDto.ulbName}</td>
				</tr>
				<tr>
					<th><spring:message code="council.mom.print.deptName" text="Department Name" /></th>
					<td>${printDto.departmentName }</td>
				</tr>
				<tr>
					<th><spring:message code="council.mom.print.date" text="Date" /></th>
					<td>${printDto.momDate}</td>
				</tr>
			</table>
		
		<br />
		<table>
			<tr>
				<th style="width: 30%"><spring:message code="council.mom.print.comitName" text="Committee Name" /></th>
				<td>${printDto.committeeName}</td>
			</tr>
			<tr>
				<th><spring:message code="council.mom.print.metAgenNo" text="Meeting Agenda Number" /></th>
				<td>${printDto.agendaNo}</td>
			</tr>
			<tr>
				<th><spring:message code="council.mom.print.meetingNo" text="Meeting Number" /></th>
				<td>${printDto.meetingNo}</td>
			</tr>
			<tr>
				<th><spring:message code="council.mom.print.meetingDate" text="Meeting Date" /></th>
				<td>${printDto.meetingDateDesc}</td>
			</tr>
		</table>
		<br />
		<p>
			<strong><spring:message code="council.mom.print.atteDetails" text="Attendance Details:" /> </strong>
		</p>
		<table>
			<thead>
				<tr>
					<th style="width: 100px" class="text-center"><spring:message
							code="council.meeting.anukramank" text="Sr No" /></th>
					<th class="text-center"><spring:message
							code="council.commitee.memberName" text="Member Name" /></th>
					<th class="text-center"><spring:message
							code="council.member.designation" text="Designation" /></th>
					<th class="text-center"><spring:message
							code="council.member.attendanceStatus" text="Status" /></th>
				</tr>
			</thead>
			<tbody>
			
				<c:forEach items="${printDto.attendees}" var="attendee"
					varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${attendee.memberName}</td>
						<td>${attendee.designation}</td>
						<td>
						<c:choose>
						  <c:when test="${attendee.attendanceStatus eq '1'}">
						  	<!-- PRESENT -->
							<p><spring:message code="council.member.presentStatus" text="PRESENT" /> </p>				  		
						  </c:when>
						  <c:when test="${attendee.attendanceStatus eq '2'}">
						  	<!-- LEAVE -->
							<p><spring:message  text="Leave" /> </p>				  		
						  </c:when>
						  <c:otherwise>
						  	<!-- ABSENT -->
						  	<p><spring:message code="council.member.absentStatus" text="ABSENT" /></p>
						  </c:otherwise>
						</c:choose>
						</td>						
					</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<br />
		<p>
			<strong><spring:message code="council.mom.print.details" text="Details:" /> </strong>
		</p>
		<div>
			${printDto.momResolutionDto.resolutionComments}
		</div>
		
	</div>
</div>

<center>
	<div style="position: fixed; width: 100%; bottom: 0px; z-index: 1111;">
		<div class="text-center">
			<button onclick="window.print();"
				class="btn btn-success hidden-print" type="button">
				<i class="fa fa-print" aria-hidden="true"></i> Print
			</button>
			<button onclick="window.close();" type="button"
				class="btn btn-blue-2 hidden-print">Close</button>
		</div>
	</div>
</center>