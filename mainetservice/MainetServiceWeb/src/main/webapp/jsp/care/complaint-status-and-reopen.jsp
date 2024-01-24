<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-reopen-feedback.js"></script>
<script src="assets/libs/rating/star-rating.js" type="text/javascript"></script> 
<link href="assets/libs/rating/star-rating.css" rel="stylesheet" type="text/css"/>
<style>
	.rating-container .star{
		line-height: normal;
		margin:0px 23px 0px 3px !important;
	}
	
</style>
    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown">
    
	 <div class="widget" id="receipt">
	 
	   <div class="widget-content padding">
	      <form:form method="POST" action="GrievanceDepartmentReopen.html" commandName="command" class="form-horizontal" id="form_grievanceReopen">
			
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
		      	<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
		      	<div class="row">
		      	<div class="col-xs-1 col-sm-1">
		      		<img src="${userSession.orgLogoPath}" width="80">
		      	</div>
	            <div class="col-xs-11 col-sm-11 text-center">
	              <h3 class="margin-bottom-0">${command.complaintAcknowledgementModel.organizationName}</h3>
	              <p><spring:message code="care.status.reopen" text="Reopen Grievance"/></p>
	            </div>
	          </div>
	          
	          <div class="row margin-top-30">
	          	<div class="col-xs-3 text-right"><spring:message code="care.reports.tokenNo" text="Complaint Number :"/></div>
	            <div class="col-xs-3">${command.complaintAcknowledgementModel.tokenNumber}</div>
	            <div class="col-xs-3 text-right"><spring:message code="care.receipt.dateAndTiem" text="Date & Time :"/></div>
	          	<div class="col-xs-3">${command.complaintAcknowledgementModel.formattedDate}</div>
	          </div>
	          <div class="row margin-top-10">
	          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.applicantname" text="Applicant Name :"/></div>
	            <div class="col-xs-3">${command.complaintAcknowledgementModel.complainantName}</div>
	            <div class="col-xs-3 text-right"><spring:message code="care.receipt.status" text="Status :"/></div>
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
					 <span class="text-orange-5"> 
							<spring:message code="care.status.pending"  text="Pending"/>
					</span> 
				</c:if>	
	          	</div>
	          </div>
	          <div class="row margin-top-10">
	          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.department" text="Department :"/></div>
	            <div class="col-xs-3">${command.complaintAcknowledgementModel.department}</div>
	          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.complaintsubtype" text="Complaint Sub Type :"/></div>
	            <div class="col-xs-3">${command.complaintAcknowledgementModel.complaintSubType}</div>
	          </div>
	          <div class="row margin-top-10">
	          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.location" text="Location :"/></div>
	            <%-- <div class="col-xs-3">${command.complaintAcknowledgementModel.landmark}</div> --%>
	            <div class="col-xs-3">${kdmcEnv eq 'Y' || tsclEnv eq 'Y' ?command.complaintAcknowledgementModel.landmark :command.complaintAcknowledgementModel.ward}</div>
	         	<div class="col-xs-3 text-right"><spring:message code="care.receipt.description" text="Description :"/></div>
	            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.description}"></c:out></div>
	          </div>

			 <div class="table-responsive margin-top-10">
			 <label><spring:message code="care.action.history" text="Action History"/></label>
				<table class="table table-bordered table-condensed">
				  <thead>				  	
					  <tr>
					    <th><spring:message code="care.action.datetime" text="Date & Time"/></th>
					    <th><spring:message code="care.action.Action" text="Action"/></th>
					    <th><spring:message code="care.employee.name" text="Employee Name"/></th>
					    <th><spring:message code="care.action.employee.email" text="Email"/></th>
					    <th><spring:message code="care.action.designation" text="Designation"/></th>
					    <th><spring:message code="care.action.remarks" text="Remarks"/></th>
					    <th><spring:message code="care.action.attachments" text="Attachments"/></th>
					  </tr>
				  </thead>
				  <tbody>
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
     					 		<!-- #D130567 -->
     					 		<%-- <c:if test="${empty action.empGroupDescEng}">
     					 			<spring:message code="care.citizen" text="Citizen" />
     					 		</c:if> --%>
		                   </td>
		                   <td><c:out value="${action.comments}"></c:out></td>
		                   <td>
		                   <ul>
		                   <c:forEach items="${action.attachements}" var="lookUp" varStatus="status">
		                   <li><apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${lookUp.defaultVal}" actionUrl="GrievanceDepartmentReopen.html?Download"></apptags:filedownload></li>
		                   </c:forEach>
		                   </ul>
		                   </td>
		                </tr>
		              </c:forEach>
				   </tbody>           
				</table>
				</div>	
				
				 
				<c:if test="${command.complaintAcknowledgementModel.status eq 'PENDING'}">

				 <h6><b><spring:message code="care.receipt.escalationdetails" text="Escalation Details"/></b></h6>
					<div class="table-responsive margin-top-10">
		            	<table class="table table-bordered table-condensed">
						  	<tr>
						     <th><spring:message code="care.receipt.level" text="Level"/></th>
						    <th><spring:message code="care.receipt.duration" text="Duration (D:H:M)"/></th>
						    <th><spring:message code="care.receipt.employeename" text="Employee Name"/></th>
						    <th><spring:message code="care.receipt.designation" text="Designation"/></th>
						    <th><spring:message code="care.receipt.department2" text="Department"/></th>
						    <th><spring:message code="care.receipt.email" text="Email"/></th>
						    <th><spring:message code="care.action.employee.mobileNo" text="Mobile Number"/></th>
						    <th><spring:message code="care.reports.status" text="Status"/></th>
						  </tr>
					  	<c:forEach items="${command.complaintAcknowledgementModel.escalationDetailsList}" var="requestLists" varStatus="status">
					                <tr>
					                  <td class="text-center" width="5%"><c:out value="${requestLists.level}">
					                    </c:out></td>
					                  <td width="5%"><c:out value="${requestLists.sla}">
					                    </c:out></td>
					                  <td><c:out value="${requestLists.empName}">
					                    </c:out></td>
					                  <td><c:out value="${requestLists.designation}">
					                    </c:out></td>
					                  <td width="15%"><c:out value="${requestLists.department}">
					                    </c:out>
					                    </td>
					                  <td><c:out value="${requestLists.email}">
					                    </c:out>
					                    </td>
					                  <td><c:out value="${requestLists.mobile}">
					                    </c:out>
					                    </td>
					                  <td width="10%">
						                  	<c:if test="${requestLists.status eq 'ESCALATED'}">
											 <span class="text-red-1"> 
													<spring:message code="care.status.escalated" text="Escalated"/>
											</span> 
											</c:if>
											<c:if test="${requestLists.status eq 'IN_PROGRESS'}">
											 <span class="text-orange-5"> 
													<spring:message code="care.status.Inprogress"  text="Inprogress"/>
											</span> 
											</c:if>
											<c:if test="${requestLists.status eq 'NOT_ASSIGNED'}">
											 <span class="text-grey"> 
													<spring:message code="care.status.not_assigned"  text="Not Assigned"/> 
											</span>
											</c:if>	
											<c:if test="${requestLists.status eq 'COMPLETED'}">
											 <span class="text-green-1"> 
													<spring:message code="care.status.completed"  text="Completed"/>
											</span>
											</c:if>
											<c:if test="${requestLists.status eq 'EXITED'}">
											 <span class="text-green-1"> 
													<spring:message code="care.status.closed"  text="Closed"/>
											</span>
											</c:if>	
					                    </td>
					                </tr>
					  </c:forEach>
					</table>
		         </div>
				</c:if>
				
				<div id="reopenForm"  style="display:none;">
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
		            	<div class="panel panel-default">
					
						<div class="panel-heading">
		            		<h4 class="panel-title">
		            		<a data-toggle="" class="" data-parent="#accordion_single_collapse" href="#a0">
		            			<spring:message code="care.receipt.reopen.heading" text="Reopen complaint"/>
		            		</a></h4>
		            	</div>
							<div class="panel-body">
								<div id="a0" class="panel-collapse collapse in">
								
								
								<div class="form-group">
								
									<label class="col-sm-2 control-label required-control" for="reasonReopening"><spring:message code="care.reason.reopen"/></label>
									
									<apptags:lookupField items="${command.getLevelData('RRN')}" path="careDepartmentAction.reopeningReason" cssClass="form-control"
									hasChildLookup="false" selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" hasId="true"/>
																		
									<label class="col-sm-2 control-label required-control" for="Remark"><spring:message code="care.remarks" text="Remark"/></label>  
										<div class="col-sm-4">
											<form:textarea class="form-control" 
												id="Remark" path="careDepartmentAction.comments" maxlength="1000"
												onkeyup="countChar(this,1000,'remarkCount')" onfocus="countChar(this,1000,'remarkCount')"></form:textarea>
											<div class="pull-right">
												<spring:message code="charcter.remain" text="characters remaining " /> <span id="remarkCount">1000</span>
											</div>
										</div>
								</div>
								
									<div class="form-group">
										<label class="col-sm-2 control-label" for="UploadDocuments"><spring:message code="care.upload" text="Upload Document"/></label>
										<div class="col-sm-10">
											<apptags:formField fieldType="7" labelCode="" hasId="true"
												fieldPath=""
												isMandatory="false" showFileNameHTMLId="true"
												fileSize="CARE_COMMON_MAX_SIZE" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
												validnFunction="CARE_VALIDATION_EXTENSION"
												currentCount="0" cssClass="btn-sm" />  
											<div class="col-sm-9 padding-0">
												<small class="text-blue-2 "> <spring:message
														code="care.form.validator.fileUploadNote"
														text="(Upload File upto 5MB and only pdf,jpeg,jpg,png)" />
												</small>
											</div>
										</div>
									</div>
									
									
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div id="feedbackForm" >
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
		            	<div class="panel panel-default">
					
						<div class="panel-heading">
		            		<h4 class="panel-title">
		            		<a data-toggle="" class="" data-parent="#accordion_single_collapse" href="#a0">
		            			<spring:message code="care.receipt.feedback.heading" text="Feedback"/>
		            		</a></h4>
		            	</div>
							<div class="panel-body">
								<div id="a0" class="panel-collapse collapse in">
									<div class="row">
	              						<div class="col-sm-6 col-sm-push-3">
											
											<div class="form-group">
												 <label class="control-label col-sm-4" for="rating-input"><spring:message code="care.feedback.rateus" text="Rate us"/><span class="mand">*</span></label>
								                 <div class="col-sm-8">
								                    <form:input id="rating-input" type="number" path=""/>
								                 </div>
								             </div>
								             
								            <div class="form-group">     
												<label class="col-sm-4 control-label required-control" for="feedback"><spring:message code="care.remarks" text="Remark"/></label>  
												<div class="col-sm-8">
													<form:textarea class="form-control" id="feedback"
														path="careFeedback.ratingsContent"></form:textarea>
												</div>
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div id="btnDiv1" class="text-center margin-top-10">
            		<button id="btnPrint" onclick="printContent('receipt'); return false" class="btn btn-success hidden-print"><i class="fa fa-print"></i> <spring:message code="care.receipt.print" text="Print"/></button>
            		<c:if test="${command.complaintAcknowledgementModel.status eq 'CLOSED' && command.complaintAcknowledgementModel.lastDecision eq 'APPROVED'}">
            			<button id="btnReopen" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-repeat"></i> <spring:message code="care.receipt.reopen" text="Reopen"/></button>
            			<button id="btnFeedBack" type="button" class="btn btn-warning hidden-print"><i class="fa fa-commenting"></i><spring:message code="care.feedback" text="Feedback"/></button>
            		</c:if>
             		<button id="btnBack" type="button" class="btn btn-danger hidden-print" onclick="javascript:openRelatedForm('${backBTURL}','this');"><spring:message code="care.back" text="Back"/></button>
          		</div>
          		<div id="btnDiv2" class="text-center margin-top-10" style="display:none;">
          			<input type="button" class="button-input btn btn-success" value="<spring:message code="care.submit"/>" onclick="reopenComplaint(this);"  />
					<button type="button" id="btnCancelId" class="btn btn-blue-2 hidden-print btnCancel"><spring:message code="care.receipt.cancel" text="Cancel"/></button>
          			<button id="btnBack" type="button" class="btn btn-warning hidden-print" onclick="javascript:openRelatedForm('${backBTURL}','this');"><spring:message code="care.back" text="Back"/></button>
          		</div>
          		<div id="btnDiv3" class="text-center margin-top-10" style="display:none;">
          			<input type="button" class="button-input btn btn-success" value="<spring:message code="care.submit"/>" onclick="submitComplaintFeedbck(this);"  />
					<button type="button" class="btn btn-blue-2 hidden-print btnCancel"><spring:message code="care.receipt.cancel" text="Cancel"/></button>
          			<button id="btnBack" type="button" class="btn btn-warning hidden-print" onclick="javascript:openRelatedForm('${backBTURL}','this');"><spring:message code="care.back" text="Back"/></button>
          		</div>
          		<input type="hidden" id="hiddenFeedbackRate" value="${command.careFeedback.ratingsStarCount}"/>
          		<input type="hidden" id="hiddenFeedbackRateUpdtaed" name="careFeedback.ratingsStarCount"/>
	       </form:form>
	   </div>
	</div>
   </div>
     
<script>
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
	//D#128880 start
	var hitFrom= '${backBTURL}';
	if(hitFrom != undefined && hitFrom == 'GrievanceComplaintStatus.html'){
		$('#reopenForm').slideDown( "slow" );
		$('#btnDiv1').hide();
		$('#btnDiv2').show();
		$('#btnCancelId').hide();
		
	}
	//D#128880 end
	
});
</script>