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
<script src="js/care/resubmit-complaint.js"></script>

    <!-- Start Content here -->
    <div class="content animated slideInDown"> 
     <div class="widget invoice" id="receipt">
	   <div class="widget-content padding">
        
          <div class="row">
            <div class="col-xs-12 text-center">
              <h3 class="margin-bottom-0">${command.complaintAcknowledgementModel.organizationName}</h3>
              <p><spring:message code="care.complaint.status" text="Complaint Status"/></p>
            </div>
          </div>
          
          <div class="row margin-top-30">
          	<div class="col-xs-3 text-right"><spring:message code="care.token" text="Token :"/></div>
            <div class="col-xs-3">${command.complaintAcknowledgementModel.tokenNumber}</div>
            <div class="col-xs-3 text-right"><spring:message code="care.dateAndTiem" text="Date & Time :"/></div>
          	<div class="col-xs-">${command.complaintAcknowledgementModel.formattedDate}</div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.applicant.name" text="Applicant Name :"/></div>
            <div class="col-xs-3">${command.complaintAcknowledgementModel.complainantName}</div>
            <div class="col-xs-3 text-right"><spring:message code="care.complaint.mobile" text="Applicant Mobile Number :"/></div>
            <div class="col-xs-3"><c:out value="${command.complaintAcknowledgementModel.complainantMobileNo}"></c:out></div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.label.department" text="Department :"/></div>
            <div class="col-xs-3">${command.complaintAcknowledgementModel.department}</div>
            <div class="col-xs-3 text-right"><spring:message code="care.complaint.subtype" text="Complaint Type :"/></div>
            <div class="col-xs-3">${command.complaintAcknowledgementModel.complaintSubType}</div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.complaint.location" text="Location :"/></div>
            <div class="col-xs-3">${command.complaintAcknowledgementModel.ward}</div>
            <div class="col-xs-3 text-right"><spring:message code="care.label.status" text="Status :"/></div>
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
            <div class="col-xs-6"><c:out value="${command.complaintAcknowledgementModel.description}"/></div>
          </div>
          
          <form:form action="GrievanceResubmission.html" commandName="command" class="form-horizontal" method="POST">
	          <h4><spring:message code="care.action.history" text="Action History"/></h4>
				<table class="table table-bordered table-condensed">
				  <tr>
				    <th class="text-center"><spring:message code="care.action.datetime" text="Date & Time"/></th>
				    <th class="text-center"><spring:message code="care.action.Action" text="Action"/></th>
				    <th class="text-center"><spring:message code="care.action.employee.name" text="Employee Name"/></th>
				    <th class="text-center"><spring:message code="care.action.employee.email" text="Email"/></th>
				    <th class="text-center"><spring:message code="care.action.designation" text="Designation"/></th>
				    <th class="text-center"><spring:message code="care.action.remarks" text="Remarks"/></th>
				    <th class="text-center"><spring:message code="care.action.action.attachments" text="Attachments"/></th>
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
	                   <li><apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${lookUp.defaultVal}" actionUrl="NewWaterConnectionForm.html?Download"></apptags:filedownload></li>
	                   </c:forEach>
	                   </ul>
	                   </td>
	                </tr>
	              </c:forEach>
				</table>
	
				<div class="margin-top-10">
				  <div class="warning-div error-div alert alert-danger alert-dismissible"></div>	
				  <h4><spring:message code="care.complaintDescription" /></h4>
	               
	               <div class="margin-top-10">
		               <label class="col-sm-2 control-label required-control" for="YourReply"><spring:message code="care.remark" text="Remark" /></label>
		               <div class="col-sm-4">
		                 <form:textarea name="" cols="" rows="" id="YourReply" class="form-control" path="action.comments"></form:textarea>
		               </div>
		               <label class="col-sm-2 control-label" for="UploadPhoto"><spring:message code="care.uploadphoto" /></label>
		               <div class="col-sm-4">
		               		<small class="text-blue-2" style="padding-left: 10px;">
		               			<spring:message code="care.attachmentsNote" text="(UploadFile upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif)"/>
		               		</small>
		                   <apptags:formField fieldType="7" labelCode="" hasId="true"
								fieldPath=""
								isMandatory="false" showFileNameHTMLId="true"
								fileSize="CARE_COMMON_MAX_SIZE" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
								currentCount="0" />  
					     </div>
				     </div>
	             </div>
			  <form:hidden id="applicationId" name="applicationId" path="action.applicationId"/>
	
	          <div class="text-center margin-top-10">
	            <input type="button" class="button-input btn btn-success" value="<spring:message code="care.submit"/>" onclick="resubmitComplaint(this);"  />
	            <apptags:backButton url="CitizenHome.html"></apptags:backButton>
	          </div>
          </form:form>
        </div>
        </div>
    </div>