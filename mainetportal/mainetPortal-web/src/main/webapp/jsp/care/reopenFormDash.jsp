<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script src="js/care/reopen-complaint-registration.js"></script>

<script>
$(document).ready(function() {
   $('.showRequestDetailsLink').trigger('click');
});
</script>

<!-- Start Content here -->

<div class="content animated slideInDown">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="widget">
		<div class="widget-header">
	      <h2><spring:message code="care.reopenComplaint" text="Reopen Grievance" /></h2>
	      <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="care.help"/></span></a> </div>
	    </div>
 		<div class="widget-content padding">
 		<div class="mand-label clearfix"><span><spring:message code="care.fieldwith"/><i class="text-red-1">* </i><spring:message code="care.ismendatory"/></span></div>
 		<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
        <form:form id="care" name="care" class="form-horizontal"
					commandName="command"
					action="grievance.html"
					method="POST" enctype="multipart/form-data">
					
					
					<input type="hidden" data-complaint="${compNo}" id="<c:out value="${appId}"></c:out>" class="showRequestDetailsLink"  value="${appId}" />
					
			
          
          
          
          
          <div class="margin-top-10" id="collapseExample" style="display:none">
            <h4><spring:message code="care.action.history" text="Action History"/></h4>
            <div class="table-responsive">
              <table class="table table-bordered table-striped" id="actionHistory">
             			<thead>
						<tr>
						
							<th><spring:message code="care.sr.no" text="Sr No" /></th>
							<th><spring:message code="care.datetime" text="Date & Time" /></th>
							<th><spring:message code="care.Action" text="Action" /></th>
							<th><spring:message code="care.action.employee.name" text="Employee Name" /></th>
							<th><spring:message code="care.action.employee.email" text="Email" /></th>
							<th><spring:message code="care.action.designation" text="Designation" /></th>
							<th><spring:message code="care.action.remarks" text="Remarks" /></th>
							<th><spring:message code="care.action.attachments" text="Attachment" /></th>
						</tr>
						</thead>
					
						<tbody>
						</tbody>
              </table>
              
            </div>
            <div class="margin-top-10">
              <!-- <button id="btnShowReopnForm" class="btn btn-blue-3" type="button" data-toggle="collapse" data-target="#collapseExample2" aria-expanded="false" aria-controls="collapseExample2"> Reopen Complaint </button> -->
              
              <div id="collapseExample2">
              	<!-- <div class="warning-div error-div alert alert-danger alert-dismissible margin-top-10"></div> -->
              	
              	
				
				<div class="row margin-top-20">
					<div class="col-sm-2" style="color: black;"><spring:message code="care.token" text="Complaint No :" /></div>
					<div id="complaintNo" class="col-sm-3" style="color: black;"></div>
				</div>
				
				<div class="row margin-top-10">
					<h4 style="font-size: 15px;"><spring:message code="care.complaintDescription" text="Grievance Description" /></h4>
				</div>
              	
                
                
                <div class="form-group margin-top-10">
						
							<label class=" col-sm-2 control-label required-control" for="reasonReopening"><spring:message code="care.reason.reopen" text="Reopening Description"/></label>
							<c:set var="baseLookupCode" value="RRN" />
							<apptags:lookupField items="${command.getLevelData('RRN')}" path="action.reopeningReason" cssClass="margin-top-10 form-control"
							hasChildLookup="false" selectOptionLabelCode="applicantinfo.label.select" isMandatory="true"  hasId="true"/>
							
							
							<label class="col-sm-2 control-label required-control" for="YourReply"><spring:message code="care.remark" text="Remark"/></label>
                  <div class="margin-top-10 col-sm-4">
                    <form:textarea name="" cols="" rows="" id="YourReply" class="form-control"
                    onkeyup="countCharacter(this,1000,'reopenCommentCount')" onfocus="countCharacter(this,1000,'reopenCommentCount')"
                     path="action.comments"></form:textarea>
                     <div class="pull-right">
						<spring:message code="charcter.remain" text="characters remaining " /><span id="reopenCommentCount">1000</span>
					</div>
                  </div>
							
						</div>
                
                <div class="form-group">
                  
                  <label class="col-sm-2 control-label" for="UploadPhoto"><spring:message code="care.uploadphoto" /></label>
                  <div class="col-sm-10">
                      <apptags:formField fieldType="7" labelCode="" hasId="true"
						fieldPath=""
						isMandatory="false" showFileNameHTMLId="true"
						fileSize="BND_COMMOM_MAX_SIZE" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
						currentCount="0" />
						<small class="text-blue-2">
							<spring:message code="care.attachmentsNote" text="(UploadFile upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif)"/>
						</small>
                  </div>
                </div>
                <div class="text-center">
                 <input type="button" class="button-input btn btn-success" value="<spring:message code="care.submit"/>" onclick="reopenComplaint(this);"  />
                  
                  <button type="button" class="btn btn-warning" onclick="resetReopenForm(this)"><spring:message code="care.reset" /></button>
                  <button type="button" class="btn btn-danger" onclick="window.location.href='CitizenHome.html'"><spring:message code="care.back" /></button>
                </div>
              </div>
            </div>
          </div>

          <input type="hidden" id="reopened" name="reopened" value="yes" />
          <form:hidden id="applicationId" name="applicationId" path="action.applicationId" />
          
          <input type="hidden" id="csrf_parameter_name" name="csrf_parameter_name" value="${_csrf.parameterName}" />
         <input type="hidden" id="csrf_token" name="csrf_token" value="${_csrf.token}" />
        </form:form>
    	</div>    
	</div>  
</div>
