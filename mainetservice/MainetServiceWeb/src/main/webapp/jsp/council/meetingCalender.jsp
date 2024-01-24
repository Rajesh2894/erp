<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="assets/libs/fullcalendar/fullcalendar.min.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/council/meetingCalender.js"></script>
<!-- End JSP Necessary Tags -->
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>



<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.select" text="Calender" /></strong>
			</h2>	
			
		</div>

		<div class="widget-content padding">

		
		
			    <form:form action="MeetingCalender.html" class="form-horizontal form" name="EventDate" id="EventDate">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="accordion-toggle ">
				
				
				   <%-- <div class="form-group searchBtn">
						<div class="text-center padding-bottom-10">
							
						<button type="button" class="btn btn-danger" id="btn3" onclick="window.location.href='AdminHome.html'">
					    <spring:message code="lgl.cancel" text="Cancel" /></button>
							
						</div>
					
					</div> --%>
					
					<div class="col-sm-2">
					</div>
				
					<div class="col-sm-8">
					  <div class="calendar-section">
					    <div id='calendar'></div>
					  </div> 
					</div>
					<div class="col-sm-2">
					</div>
					<!-- <div class="col-sm-6">
					  <div class="event-description-section">
					    <table class="table event-details-table">
					      <tbody id="insert_here">
					      </tbody>
					    </table>
					  </div>
					</div> -->
				
				</div>
 
		</form:form>
	
	  </div>

      </div>

      </div>

