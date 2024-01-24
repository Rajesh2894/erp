<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-report.js"></script>
<script>
	$(function() {
		 var kdmcEnv = $("#kdmcEnv").val();
         if (kdmcEnv == 'Y' || kdmcEnv != ''){
        	 /* D#132276 */
        		$('.reportName').show();				
           }else{
        	   $('.reportName').html('');
        	   $('.reportName').hide();
           }
		
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
	});
	var lastSelected;
	$(function () {
		lastSelected = $('[name="careReportRequest.reportName"]:checked').val();
	});
	$(document).on('click', '[name="careReportRequest.reportName"]', function () {
	    if (lastSelected != $(this).val() && typeof lastSelected != "undefined") {
	    	window.location.href='GrievanceReport.html?grievanceUserWise';
	    	}
	    lastSelected = $(this).val();
	});
</script>

    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown">
    
	 <div class="widget">
	   <div class="widget-header">
	      <h2><spring:message code="care.reports.heading" text="Grievances Report"/></h2>
	       
			<apptags:helpDoc url="GrievanceReport.html"></apptags:helpDoc>
				
	   </div>
	   <div class="widget-content padding">
	      <form:form method="POST" action="GrievanceReport.html"
					commandName="command" 
					class="form-horizontal"
					id="form_grievanceReport">
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
			<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />		
	      	<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
	         <div class="panel-group accordion-toggle" id="accordion_single_collapse">
	            <div class="panel panel-default">
	            <div class="panel-heading">
	            	<h4 class="panel-title">
	            		<a data-toggle="" class="" data-parent="#accordion_single_collapse" href="#a0">
	            			<spring:message code="care.reports.feedback.heading" text="Grievance Feedback Report"/>
	            		</a></h4>
	            </div>
	            
	          
	               <div id="a0" class="panel-collapse collapse in">
	                  <div class="panel-body">
	                  <c:if test="${command.kdmcEnv eq 'Y'}">
	                  <div class="form-group reportName">
									<label class="col-sm-2 control-label  required-control"><spring:message
											code="care.report.type" text="Report Type" /></label>
									<div class="col-sm-4">
										<label class="radio-inline margin-top-5"> <form:radiobutton
												path="careReportRequest.reportName" value="D" id="Detail"/> 
												<spring:message	code="care.detail.report" /></label> 
										<label class="radio-inline margin-top-5"><form:radiobutton	path="careReportRequest.reportName" value="S" 
												id="Summary" /> <spring:message	code="care.summary.report" /></label>
									</div>
						</div>
						</c:if>
	                     <div class="form-group">
							<apptags:date  labelCode="care.reports.fromDate" fieldclass="datepicker" showDefault="true" datePath="careReportRequest.fromDate" isMandatory="true"></apptags:date>
							<apptags:date  labelCode="care.reports.toDate" fieldclass="datepicker" showDefault="true" datePath="careReportRequest.toDate" isMandatory="true"></apptags:date>
	                     </div>
	                     <div class="form-group">
		                  	<label class="col-sm-2 control-label" for="id_status"><spring:message code="care.reports.feedback" text="Feedback"/></label>
							<div class="col-sm-4">
			                    <form:select class="form-control" id="id_status" path="careReportRequest.feedbackRating" data-rule-required="true">
										<form:option value="0"><spring:message code="care.select" text="Select"/></form:option>
										<form:option value="-1"><spring:message code="care.all" text="All"/></form:option>
										<form:option value="1"><spring:message code="care.reports.feedback.dissatisfied" text="Dissatisfied"/></form:option>
										<form:option value="2"><spring:message code="care.reports.feedback.dislike" text="Dislike"/></form:option>
										<form:option value="3"><spring:message code="care.reports.feedback.itsOk" text="Its Ok"/></form:option>
										<form:option value="4"><spring:message code="care.reports.feedback.likedIt" text="Liked It"/></form:option>
										<form:option value="5"><spring:message code="care.reports.feedback.satisfied" text="Satisfied"/></form:option>
								</form:select>
			                 </div>
		                 </div>
	                  </div>
	               </div>
	               <div class="text-center clear padding-10">
	                <input type="button" class="btn  btn-success" id="save" name="save" value="<spring:message code="care.submit"/>" onclick="submitComplaintFeedback(this);"  />
	                <input type="Reset" class="btn btn-warning" id="rstButton" value="<spring:message code="care.reset" text="Reset"/>" />
	                <a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
									code="care.back"></spring:message>
							</a>
	               </div>
	            </div>
	         </div>
	         </form:form>
	   </div>
	</div>
   </div>