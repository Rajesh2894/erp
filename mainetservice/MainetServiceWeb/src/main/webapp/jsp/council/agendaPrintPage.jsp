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
	padding: 20px 20px;
	border: 1px solid #000000;
	margin: 0px auto;
	font-family: verdana, sans-serif;
	font-size: 13px;
	position: relative;
	margin-bottom: 40px;
	background: #ffffff;
	page-break-after: always;
}

.printer .header {
	text-align: center;
	margin-bottom: 30px;
}

.printer table {
	border-collapse: collapse;
	width: 100%;
}

.tablular-content table th {
	background-color: #eeeeee !important;
	-webkit-print-color-adjust: exact;
	font-size: 12px;
	padding: 5px;
}

.printer table td, #printer table th {
	padding: 3px;
	font-size: 12px;
	border: 1px solid #eaeaea;
	text-align: center;
}

.footer table td, .footer table th {
	border: 0px none;
}

.printer .footer {
	position: absolute;
	bottom: 10px;
	width: 97%;
}

.printer .content p {
	line-height: 21px;
}

h1 {
	font-size: 18px;
	margin: 10px;
	padding-bottom: 10px;
	border-bottom: 3px solid #cccccc;
}

h2 {
	font-size: 16px;
	margin: 0px;
	font-weight: normal;
}
</style>

<c:forEach items="${printingEntities}" var="entity" varStatus="status">
	<div class="printer">
		<div class="header">
			<div class="col-xs-3 col-sm-3 col-lg-3">
				<img width="80" src="${userSession.orgLogoPath}">
			</div>
			
			<div class="col-xs-9 col-sm-9 col-lg-9" style="margin-left: -100px; margin-top: 20px;">
				<strong><spring:message code="council.meeting.info" text="Noice Of Meeting" /></strong><br /> 
				 <strong>${entity.ulbName}</strong>
			 </div>
			 
		</div>
		
		<div class="content"  style="margin-top: 100px;">
			<p>
				<spring:message code="council.meeting.to" text="To" />,<br /> 
				<spring:message code="council.meeting.salutation" text="Shri" />,<br />
				 ${entity.memberName}<br />
				<spring:message code="council.meeting.councillor" text="Councillor" /> ${entity.designation}, <spring:message code="council.meeting.wardNo" text="ward No" />: ${entity.electionWard}<br />
				<spring:message code="council.meeting.member.address" text="Address" />: ${entity.memberAddress}<br/><br/>
				
				
				<strong>${entity.meetingInvitationMsg}</strong><br />
				<c:choose>
				  <c:when test="${userSession.languageId eq 1}">
				  	<!-- English Data -->
				  		It is hereby informed that the General/Special meeting of ${entity.muncipal} has been organised on ${entity.meetingDateDesc} (date) at ${entity.meetingTime}  Agenda of Business 
				  		of the meeting is enclosed in Annexure.
				  </c:when>
				  <c:otherwise>
				  	<!-- Hindi Data -->
					<spring:message code="council.meeting.informed" text="informed" /> ${entity.muncipal} <spring:message code="council.meeting.ki" text="ki" /> ${entity.committeeName} ${entity.meetingName}
					<spring:message code="council.meeting.dinank" text="date" />${entity.meetingDateDesc} <spring:message code="council.meeting.ko.time" text="time" />${entity.meetingTime} 
					<spring:message code="council.meeting.baje" text="" /> ${entity.location} <spring:message code="council.meeting.aayojit" text="time" /><br /> <br />
				  </c:otherwise>
				</c:choose>
			</p>
			<br />
			<p><spring:message code="council.meeting.upasthit" text="" /></p>
			<p style="text-align: right"><spring:message code="council.meeting.adhikari" text="" /></p>
			<br />
			<div class="header">
				<strong><spring:message code="council.meeting.parishisht" text="" /></strong><br />
			</div>
			
				<c:choose>
				  <c:when test="${userSession.languageId eq 1}">
				  	<!-- English Data -->
					<p><spring:message code="council.meeting.subject.list" text="" /> ${entity.meetingDateDesc} at ${entity.meetingTime} (Time)</p>				  		
				  </c:when>
				  <c:otherwise>
				  	<!-- Hindi Data -->
				  	<p><spring:message code="council.meeting.nigam.parisad" text="" /> ${entity.meetingDateDesc} <spring:message code="council.meeting.samay" text="" /> ${entity.meetingTime} <spring:message code="council.meeting.vishay" text="" /></p>
				  </c:otherwise>
				</c:choose>
			
			<br />
			<div class="tablular-content">
				<table>
					<thead>
						<tr>
							<th style="width: 100px" class="text-center"><spring:message
									code="council.meeting.anukramank" text="Sr. No" /> <br />(1)</th>
							<th class="text-center"><spring:message
									code="council.report.proposal.subject" text="Subject" /> <br />(2)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${entity.subjects}" var="subject"
							varStatus="status">
							<tr>
								<td class="text-center">${status.count}</td>
								<td class="text-center">${subject.proposalDetails}</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>
			
				<c:choose>
				  <c:when test="${userSession.languageId eq 1}">
				  	<!-- English Data -->
					<p>2. <spring:message code="council.meeting.shortNote" text="" /></p> 
									  		
				  </c:when>
				  <c:otherwise>
				  	<!-- Hindi Data -->
				  	<p>2. <spring:message code="council.meeting.sanshipt" text="" /></p> 
					
				  </c:otherwise>
				</c:choose>
				<br />
					<p style="text-align: right"><spring:message code="council.meeting.signature" text="" /></p>
		
		</div>
	</div>
</c:forEach>



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