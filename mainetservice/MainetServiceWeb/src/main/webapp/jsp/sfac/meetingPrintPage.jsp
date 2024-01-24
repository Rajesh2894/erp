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

.printer .header>div:nth-child(1) {
	padding-left: 0;
}
      
                table, .meeting-attendees{
                  margin-top: 2rem;
                }
                .meeting-attendees ol li{

                }
                table tr td, table tr th{
                  border: 1px solid #000;
                  padding:0.7rem 1rem;
                }
               .text-right{
                  text-align: right;
                }
                .text-bold{
                  font-weight: bold;
                }
                .meeting-tittle{
                  border-bottom: 1px solid #000;
                  padding: 0.3rem 0rem;
                  margin-bottom: 1rem;
                  font-size: 1.2rem;
                }
         
</style>


<div class="container">
	<div class="row">
		<div
			class="col-sm-12 col-md-12 col-lg-12 col-12 text-center text-bold meeting-tittle"><spring:message code="" text="${printDto.title}" /></div>
		<div class="col-sm-12 col-md-12 col-lg-12 col-12 text-left">
			<div id="meeting-purpose">
				<span class="text-bold">${printDto.meetingTypeName}</span>
			</div>
			<div id="meeting-datetime">
				<span class="text-bold">${printDto.meetingDateDesc}</span>
			</div>
			<div id="meeting-place">
				<span class="text-bold">${printDto.meetingPlace}</span>
			</div>
			<div id="meeting-convener">
				<span class="text-bold">${printDto.convenerOfMeeting}</span>
			</div>
		</div>
		<div class="col-sm-12 col-md-12 col-lg-12 col-12 text-center mt-2">
			<span class="text-bold">Meeting Agenda</span>
		</div>
		<div class="col-sm-12 col-md-12 col-lg-12 col-12 text-center">${printDto.tableAgenda}</div>
		
		<div
			class="col-sm-12 col-md-12 col-lg-12 col-12 text-center meeting-attendees">
			<div class="text-bold text-center">MOM Details</div>
			
			<ol>
			<c:forEach items="${printDto.momDetList}" var="lookup"
					varStatus="loop">
					<li>
						<p class="text-left">${lookup.momComments}</p>
						<p class="text-right">
								(Action-<span class="text-bold">${lookup.actionable}</span>)</p>
					</li>
					
				</c:forEach>
			
			</ol>
		</div>
		
		
		<div class="col-sm-12 col-md-12 col-lg-12 col-12 text-left">
			<table cellpadding="0" cellspacing="0" style="width: 100%;">
				<tr>
					<th class="text-center" colspan="3">Meeting Attendees</th>
				</tr>
				<tr>
					<th class="text-center">Sr No.</th>
					<th class="text-center">Name</th>
					<th class="text-center">Desigantion</th>
				</tr>
				<c:forEach items="${printDto.attendees}" var="attendee"
					varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${attendee.memberName}</td>
						<td>${attendee.designation}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		
	<%-- 			<div
			class="col-sm-12 col-md-12 col-lg-12 col-12 text-left meeting-attendees">
			<div class="text-bold text-left">(Meeting Attendees)</div>
			
			<ol>
			<c:forEach items="${printDto.attendees}" var="attendee"
					varStatus="status">
					<li>
						${attendee.memberName}
						${attendee.designation}
					</li>
					
				</c:forEach>
			
			</ol>
		</div> --%>

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