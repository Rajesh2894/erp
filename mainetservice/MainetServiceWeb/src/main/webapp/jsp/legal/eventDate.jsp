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
<!-- <link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="assets/libs/fullcalendar/fullcalendar.min.js"></script> -->
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/legal/eventDate.js"></script>
<script src="assets/libs/tui.calendar-master/js/data/schedules.js"></script>
<!-- End JSP Necessary Tags -->
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>



<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="legal.event.calendar" text="Event Calendar" /></strong>
			</h2>	
			
		</div>

		<div class="widget-content padding">

		
			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /></span>
			</div>
		
		
			    <form:form action="EventDate.html"
				class="form-horizontal form" name="EventDate"
				id="EventDate">
			    <jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="accordion-toggle ">
				
				<div class="form-group">
                  <label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.advId" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="" id="advId"  onchange="getListOfCaseNo()">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.advocates}" var="advocate">
								<form:option value="${advocate.advId}">${advocate.advFirstNm} ${advocate.advMiddleNm} ${advocate.advLastNm}</form:option>
							</c:forEach>
						</form:select>
					</div> 
					
					  <label class="control-label col-sm-2 required-control"> <spring:message
							code="lgl.case.suit.no" text="Case Suite No"/>
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="" id="caseId"  onchange="">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.caseEntryDtoList}" var="caseDTO">
								<form:option value="${caseDTO.cseId}">${caseDTO.cseSuitNo}</form:option>
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
								onclick="window.location.href='EventDate.html'"><spring:message code="legal.btn.reset" text="Reset" /> 
							</button>
													
						<button type="button" class="btn btn-danger" id="btn3" onclick="window.location.href='AdminHome.html'">
					    <spring:message code="legal.btn.back" text="Back" /></button>
							
						</div>
						
						
					</div>
				
				
				</div>
 
		</form:form>
		
	  </div>
		
      </div>
      <div class="widget">
      <div class="widget-header">
			<h2>
				<strong><spring:message code="Comming.Hearing.Dates" text="Comming Hearing Dates" /></strong>
			</h2>	
			
		</div>

		<div class="widget-content padding">
			<div class="table-responsive clear" id="hearingDates">
							<table summary="SUMMARY" class="table table-bordered table-striped pmId" id="hearingTable">
								<thead>
									<tr>
										<th><spring:message code="label.checklist.srno" text="Sr.No."/></th>
										<th><spring:message code="caseEntryDTO.cseSuitNo" text="Case Number"/></th>
										<th><spring:message code="caseEntryDTO.AdvocateName" text="Advocate Name"/></th>
										<th><spring:message code="caseEntryDTO.HearingDate" text="Hearing Date"/></th>
										<th><spring:message code="caseEntryDTO.CourtName" text="Court Name"/></th>
										<th><spring:message code="caseEntryDTO.Description" text="Case Description"/></th>
										
									</tr>
								</thead>
								<tbody>
									
							</table>
					</div>
			</div>
		</div>
      </div>


