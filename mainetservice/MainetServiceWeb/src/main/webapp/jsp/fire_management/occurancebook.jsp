<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />

<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/fire_management/occuranceBook.js"></script>

<script type="text/javascript">



$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});

	$(".Moredatepicker").timepicker({
		//s dateFormat: 'dd/mm/yy',		
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	$("#time").timepicker({

	});

});

</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="OccuranceBookDTO.form.heading"
						text="Occurance Book Detail" /></strong>
				<apptags:helpDoc url="OccuranceBook.html"></apptags:helpDoc>
			</h2>
		</div>
		<%-- <div class="widget-header">
			<h2>
				<strong><spring:message code="OccuranceBookDTO.form.detail" text="Occurance Book" /></strong>
			</h2>
		</div> --%>
       
		<div class="widget-content padding">
			<form:form action="OccuranceBook.html"  name="frmOccuranceBook" id="frmOccuranceBook" 
				method="POST" commandName="command" class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
	
              
				<div  class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="OccuranceBookDTO.date" datePath="occuranceBookDTO.date"
						isMandatory="true" cssClass="custDate mandColorClass">
					</apptags:date>

					<apptags:input labelCode="OccuranceBookDTO.time"
						path="occuranceBookDTO.time" isMandatory="true"
						cssClass="hasNumber mandColorClass" />

				</div>

				<div class="form-group">
					<apptags:textArea labelCode="OccuranceBookDTO.incidentDesc" maxlegnth="500"
						path="occuranceBookDTO.incidentDesc" isMandatory="true" />


					<apptags:textArea labelCode="OccuranceBookDTO.operatorRemarks" maxlegnth="300"
						path="occuranceBookDTO.operatorRemarks" isMandatory="true" />

				</div>

				<div class="text-center margin-top-10">
					<%-- Defect #158116 --%>
					<input type="button" value="<spring:message code="bt.save"/>"
						title='<spring:message code="bt.save"/>'
						onclick="confirmToProceed(this)" class="btn btn-success" id="Save">
					<button type="Reset" class="btn btn-warning"
						title='<spring:message code="lgl.reset" text="Reset" />'
						onclick="resetForm(this);" id="resetAllFields">
						<spring:message code="lgl.reset" text="Reset"></spring:message>
					</button>
				    <input type="button"
				    	title='<spring:message code="bckBtn" text="Back" />'
						onclick="window.location.href='OccuranceBook.html'"
						class="btn btn-danger  hidden-print" value='<spring:message code="bckBtn" text="Back" />'>
					<form:hidden path="occuranceBookDTO.occId" id="occcId"/>

				</div>
				
              
			</form:form>

		</div>
	</div>

</div>





















