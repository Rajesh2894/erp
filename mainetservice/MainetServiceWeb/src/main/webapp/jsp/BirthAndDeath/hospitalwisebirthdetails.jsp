<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>  
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/birthAndDeath/hospitalwisebirthdetails.js"></script>

   
   
   
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="Hospital Wise Birth Details Report" text="Hospital Wise Birth Details Report" />
				<apptags:helpDoc url="HospitalWiseBirthDetails.html"></apptags:helpDoc>
			</h2>
		</div>
		
		<br>
		
		<div  class="widget-content padding">
		<form:form action="HospitalWiseBirthDetails.html" name="frmHospitalWiseBirthDetails" id="frmHospitalWiseBirthDetails" 	
			method="POST" commandName="command" class="form-horizontal form">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		
		<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 			<span id="errorId"></span>
				</div>
				
				<div class="widget-header">
					<h2>
						<strong><spring:message
								code="HospitalWiseBirthDetails.form.detail" text="Details" /></strong>
					</h2>
				</div>
		
		<div class="form-group">
			
			<apptags:date fieldclass="datepicker"
				labelCode="HospitalWiseBirthDetailsDTO.fromDate"
				datePath="HospitalWiseBirthDetailsDTO.fromDate" isMandatory="true"
				cssClass="custDate mandColorClass date">
			</apptags:date>	
			
			<apptags:date fieldclass="datepicker"	
			labelCode="HospitalWiseBirthDetailsDTO.toDate"
			datePath="HospitalWiseBirthDetailsDTO.toDate" isMandatory="true"
			cssClass="custDate mandColorClass date">
			</apptags:date>
		
		</div>
	
		
		<div class="text-center margin-top-10">
			<input type="button" onClick="viewReport(this)" value="<spring:message code="HospitalWiseBirthDetailsDTO.form.view"/>" class="btn btn-primary hidden-print" id="View">
			<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger  hidden-print" value="Back">
		</div>
		
		</form:form>
			</div>	
	</div>
</div>

