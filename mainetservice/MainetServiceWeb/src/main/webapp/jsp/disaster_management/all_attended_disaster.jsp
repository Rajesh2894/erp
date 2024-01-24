<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
  
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/disaster_management/allAttendedDisaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<style>
.error-div ul > li {
	position: relative;
	padding: 0 0 0 1.4rem;
}
.error-div ul > li::before {
	content: "\f06a";
	font-family: 'FontAwesome';
	/* position: absolute; */
	left: 0;
	margin-right:5px;
}
</style>   
   
   
   
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="AllAttendedDisasterDTO.all.attended.disaster" text="All Attended Disaster" />
				<apptags:helpDoc url="AllAttendedDisaster.html"></apptags:helpDoc>
			</h2>
		</div>
		<div  class="widget-content padding">
		
	
		
		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<div id="errorId"></div>
</div>


		
		<form:form action="AllAttendedDisaster.html" id="frmallattdisaster" method="POST" commandName="command" class="form-horizontal form">
		<div class="form-group">
		<label class="control-label col-sm-2 required-control" for="location"> <spring:message code="AllAttendedDisasterDTO.location" text="" /></label>
		
			<div class="col-sm-4"> 
			<form:select id="location" path="AllAttendedDisasterDTO.location" cssClass="form-control chosen-select-no-results " data-rule-required="true" >
		
				<form:option value="">
					<spring:message code="Select" text="Select" />
				</form:option>
				<c:forEach items="${location}" var="location">
					<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
				</c:forEach>
			</form:select>
			</div>
			<apptags:date fieldclass="datepicker"
				labelCode="AllAttendedDisasterDTO.fromDate"
				datePath="allAttendedDisasterDTO.fromDate" isMandatory="true"
				cssClass="custDate mandColorClass date">
			</apptags:date>	
			
		</div>
		
		<div class="form-group">	
			<apptags:date fieldclass="datepicker"	
			labelCode="AllAttendedDisasterDTO.toDate"
			datePath="allAttendedDisasterDTO.toDate" isMandatory="true"
			cssClass="custDate mandColorClass date">
			</apptags:date>
		</div>

		
		<div class="text-center margin-top-10">
			<input type="button" onClick="confirmToProceed(this);" value="<spring:message code="bt.search" text="Search" />"
			title='<spring:message code="bt.search" text="Search" />'
			class="btn btn-success" id="Search">
			<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger  hidden-print"
			title='<spring:message code="bt.backBtn" text="Back" />'
			value="<spring:message code="bt.backBtn" text="Back" />">
		</div>
		
		</form:form>
			</div>	
	</div>
</div>
		