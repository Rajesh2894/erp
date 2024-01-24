<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <script src="js/mainet/file-upload.js"></script>
<script src="js/care/complaint-reopen-feedback.js"></script>
<script src="assets/libs/rating/star-rating.js"></script> 
<link href="assets/libs/rating/star-rating.css" rel="stylesheet" type="text/css"/>

    <div class="animated slideInDown"> 
     <div class="widget invoice" id="receipt">
	   <div class="widget-content padding">
        <form:form action="grievance.html" method="POST" id="id_grievanceReopenFeedback" name="grievanceReopenFeedback" commandName="command">
        						
		  <div class="row">
            <div class="col-xs-12 text-center">
              <label class="margin-bottom-0"
				style="font-size: 24px; font-weight: 400;">${command.complaintAcknowledgementModel.organizationName}</label>
			<p>
				<spring:message code="care.complaint.status"
					text="Complaint Status" />
			</p>
            </div>
          </div>
          
          
          <div class="row margin-top-30">
          	<div class="col-xs-3 text-right"><spring:message code="care.token" text="Token :"/></div>
            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.tokenNumber}"></c:out></div>
            <div class="col-xs-3 text-right"><spring:message code="care.dateAndTiem" text="Date & Time :"/></div>
          	<div class="col-xs-"><c:out value="${command.complaintAcknowledgementModel.formattedDate}"></c:out></div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.applicant.name" text="Applicant Name :"/></div>
            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.complainantName}"></c:out></div>
            <div class="col-xs-3 text-right"><spring:message code="care.complaint.mobile" text="Applicant Mobile Number :"/></div>
            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.complainantMobileNo}"></c:out></div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.label.department" text="Department :"/></div>
            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.department}"></c:out></div>
            <div class="col-xs-3 text-right"><spring:message code="care.complaint.subtype" text="Complaint Type :"/></div>
            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.complaintSubType}"></c:out></div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.complaint.location" text="Location :"/></div>
            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.ward}"></c:out></div>
            <div class="col-xs-3 text-right"><spring:message code="care.complaint.status" text="Status :"/></div>
            <div class="col-xs-3">
        		<c:if test="${command.complaintAcknowledgementModel.status eq 'CLOSED'}">
				 <span class="text-green-1"> 
						<spring:message code="care.status.closed" text="Closed"/>
				</span> 
				</c:if>
				<c:if test="${command.complaintAcknowledgementModel.status eq 'EXPIRED'}">
				 <span class="text-red-1"> 
						<spring:message code="care.status.expired"  text="Expired"/>
				</span> 
				</c:if>
				<c:if test="${command.complaintAcknowledgementModel.status eq 'PENDING'}">
				 <span class="text-orange-1"> 
						<spring:message code="care.status.pending"  text="Pending"/>
				</span> 
				</c:if>
		  	</div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.complaint.description" text="Description :"/></div>
            <div class="col-xs-6"><c:out value="${command.complaintAcknowledgementModel.description}"></c:out></div>
          </div>
          <div class="table-responsive margin-top-10">
          <h3><spring:message code="care.action.history" text="Action History"/></h3>
			<table class="table table-bordered table-condensed">
			  <tr>
			    <th class="text-center"><spring:message code="care.action.datetime" text="Date & Time"/></th>
			    <th class="text-center"><spring:message code="care.action.Action" text="Action"/></th>
			    <th class="text-center"><spring:message code="care.action.employee.name" text="Employee Name"/></th>
			    <th class="text-center"><spring:message code="care.action.employee.email" text="Email"/></th>
			    <th class="text-center"><spring:message code="care.action.designation" text="Designation"/></th>
			    <th class="text-center"><spring:message code="care.action.remarks" text="Remarks"/></th>
			    <th class="text-center"><spring:message code="care.action.attachments" text="Attachments"/></th>
			  </tr>
			 
			  <c:forEach items="${command.complaintAcknowledgementModel.actions}" var="action" varStatus="status">
                <tr>
                  <td ><fmt:formatDate pattern="dd/MM/yyyy hh:mm a" value="${action.dateOfAction}" /></td>
                  <td><c:out value="${action.decision}"></c:out></td>
                  <td><c:out value="${action.empName}"></c:out></td>
                   <td><c:out value="${action.empEmail}"></c:out></td>
                   <td>
	                    <c:if test="${not empty action.empGroupDescEng}">
  					 				<c:out value="${action.empGroupDescEng}"></c:out>
				 		</c:if>
				 		<c:if test="${empty action.empGroupDescEng}">
				 			<spring:message code="care.Citizen" text="Citizen" />
				 		</c:if>
                   </td>
                   <td><c:out value="${action.comments}"></c:out></td>
                   <td>
                   <ul>
                   <c:forEach items="${action.attachements}" var="lookUp" varStatus="status">
                   <li><apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${lookUp.defaultVal}" actionUrl="grievance.html?Download"></apptags:filedownload></li>
                   </c:forEach>
                   </ul>
                   </td>
                </tr>
              </c:forEach>
			</table>
			</div>

 			<div id="reopenForm" class="margin-top-10">
           		<h3><spring:message code="care.receipt.reopen.heading" text="Reopen complaint"/></h3>
					<div id="margin-top-10">
					
					
					<div class="form-group">
						
					<label class="col-sm-2 control-label required-control" for="reasonReopening"><spring:message code="care.reason.reopen"/></label>
					<c:set var="baseLookupCode" value="RRN" />
					<apptags:lookupField items="${command.getLevelData('RRN')}" path="action.reopeningReason" cssClass="form-control"
					hasChildLookup="false" selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" hasId="true"/>
		
					</div>
					
					<div class="form-group">
						
							<label class="col-sm-2 control-label required-control" for="Remark"><spring:message code="care.remarks" text="Remark"/></label>  
							<div class="col-sm-4">
								<form:textarea class="form-control" id="Remark"
									path="action.comments"></form:textarea>
							</div>
							<label class="col-sm-2 control-label" for="UploadDocuments"><spring:message code="care.upload" text="Upload Document"/></label>
							<div class="col-sm-4">
							<small class="text-blue-2">
								<spring:message code="care.attachmentsNote" text="(UploadFile upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif)"/>
							</small>
								  <apptags:formField fieldType="7" labelCode="" hasId="true"
									fieldPath=""
									isMandatory="false" showFileNameHTMLId="true"
									fileSize="CARE_COMMON_MAX_SIZE" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									currentCount="0" />   
							</div>
							
							<form:hidden id="applicationId" name="applicationId" path="action.applicationId" value="${command.complaintAcknowledgementModel.tokenNumber}"/>
						</div>
						
						<div class="form-group">
								<c:if test="${userSession.employee.emploginname eq 'NOUSER'}">
		                  			<div class="col-sm-2">  
		                  				<button type="button" id="btnOTP" class="btn btn-success" 
		   			      				onclick="generateOTP()"><spring:message code="care.generateOTP" text="Generate OTP" /></button>
		   			      			</div>
		   			      		<spring:message code="care.placeholder.MobileOTP" text="Please Enter Mobile OTP" var="placeholderMobileOTP"></spring:message>
			    				  <div class="margin-top-10 col-sm-4"> 
			    				  	<form:input type="text" id="mobileOTP" class="form-control" maxlength="6" path="enteredMobileOTP" isMandatory="true" placeholder="${placeholderMobileOTP}"></form:input>
			    				  	<small class="text-blue-2 otp"></small> 
			    				  </div>
						   	  </c:if>
					     </div>
					</div>
				</div>  
				
				 <div id="feedbackForm" >
		        	<h3><spring:message code="care.receipt.feedback.heading" text="Feedback"/></h3>
					<div class="margin-top-10">
          					<div class="col-sm-6 col-sm-push-3">
								<div class="form-group">
									 <label class="control-label col-sm-4" for="rating-input"><spring:message code="care.feedback.rateus" text="Rate us"/><span class="mand">*</span></label>
					                 <div class="col-sm-8">
					                    <form:input id="rating-input" type="number" path="feedbackDetails.ratings"/>
					                 </div>
					             </div>
					            <div class="form-group">     
									<label class="col-sm-4 control-label required-control" for="feedback"><spring:message code="care.remarks" text="Remark"/></label>  
									<div class="col-sm-8">
										<form:textarea class="form-control" id="feedback"
											path="feedbackDetails.ratingsContent"></form:textarea>
									</div>
								</div>
							</div>
					</div>
				</div> 
		 		<div id="btnDiv1" class="text-center col-sm-12  margin-top-10">
		 		<!-- <a href="someRealUrl" onclick="document.title='My new title'; window.print(); return false;"></a> -->
            		<button id="btnPrint"    onclick="document.title='Complaint Registartion Status ${command.complaintAcknowledgementModel.tokenNumber}';  printContent('receipt')" class="btn btn-success hidden-print"><i class="fa fa-print"></i> <spring:message code="care.receipt.print" text="Print"/></button>
            		<!-- D#112431 -->
            		<%-- <c:if test="${command.complaintAcknowledgementModel.status eq 'CLOSED' && command.complaintAcknowledgementModel.lastDecision eq 'APPROVED'}">
            			<button id="btnReopen" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-repeat"></i> <spring:message code="care.receipt.reopen" text="Reopen"/></button>
            			<button id="btnFeedBack" type="button" class="btn btn-warning hidden-print"><i class="fa fa-commenting"></i><spring:message code="care.receipt.feedback" text="Feedback"/></button>
            		</c:if> --%>
             		<button id="btnBack" type="button" class="btn btn-danger hidden-print" onclick="window.location.href='grievance.html?grievanceStatus'"><spring:message code="care.receipt.back" text="Back"/></button>
          		</div> 
          		<!-- D#112431 -->
          		 <%-- <div id="btnDiv2" class="text-center col-sm-12 margin-top-10">
          			<button type="button" class="button-input btn btn-success" onclick="reopenComplaint(this);" > <spring:message code="care.submit"/> </button>
					<button type="button" class="btn btn-blue-2 hidden-print btnCancel"><spring:message code="care.receipt.cancel" text="Cancel"/></button>
          			
          		</div>
          	 	<div id="btnDiv3" class="text-center col-sm-12 margin-top-10">
          			<button type="button" class="button-input btn btn-success" onclick="submitComplaintFeedbck(this);" ><spring:message code="care.submit"/></button>
					<button type="button" class="btn btn-blue-2 hidden-print btnCancel"><spring:message code="care.receipt.cancel" text="Cancel"/></button>
          			
          		</div> --%>
          		 
          		<input type="hidden" id="emploginname" value="${userSession.employee.emploginname}"/>
				<input type="hidden" id="hiddenFeedbackRate" value="${command.feedbackDetails.ratingsStarCount}"/>
				<input type="hidden" id="hiddenFeedbackRateUpdtaed" name="feedbackDetails.ratingsStarCount"/> 
				<input type="hidden" name="" id="hiddenTokenNumber" value="${command.complaintAcknowledgementModel.tokenNumber}"/>
				<input type="hidden" name="" id="complainantMobileNo" value="${command.complaintAcknowledgementModel.complainantMobileNo}"/>
				<input type="hidden" name="" id="complainantEmail" value="${command.complaintAcknowledgementModel.complainantEmail}"/>
				<input type="hidden" name="feedbackDetails.id" id="hiddenFeedbackId" value="${command.feedbackDetails.id}"/>
				<input type="hidden" name="feedbackDetails.ratings" id="hiddenFeedbackRate" value="${command.feedbackDetails.ratingsStarCount}"/>
             
          </form:form>
        </div>
        </div>
    </div>
 
<script>
$(document).ready(function(){
	
	$('#reopenForm').hide();
	$('#feedbackForm').hide();
	$('#btnDiv2').hide();
	$('#btnDiv3').hide();
	var star = $("#hiddenFeedbackRate").val();
	if(star !='' || star !=null){
		$('#rating-input').val(star);
		$('#hiddenFeedbackRateUpdtaed').val(star);
	} 
	
	$('#btnReopen').click(function() {
		$('#reopenForm').slideDown( "slow" );
		$('#btnDiv1').hide();
		$('#btnDiv2').show();
	});
	
	$('#btnFeedBack').click(function() {
		$('#feedbackForm').slideDown( "slow" );
		$('#btnDiv1').hide();
		$('#btnDiv3').show();
	});
	
	
	$('.btnCancel').click(function() {
		$('#reopenForm').slideUp( "slow" );
		$('#feedbackForm').slideUp( "slow" );
		$('#btnDiv1').show();
		$('#btnDiv2').hide();
		$('#btnDiv3').hide();
		$('#errorDiv,#errorDivId').hide();
		$("#form_grievanceReopen").trigger("reset");
	});
		
});

jQuery(document).ready(function () {
	$('#rating-input').rating({
		  min: 0,
		  max: 5,
		  step: 1,
		  size: 'sm',
		  showClear: false
	});
	$('#rating-input').on('rating.change', function() {
		$('#hiddenFeedbackRateUpdtaed').val($('#rating-input').val());
	});
});
</script>
</body>
</html>