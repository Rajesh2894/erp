<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-resubmit.js"></script>


    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown">
    
	 <div class="widget" id="receipt">
	
				<apptags:helpDoc url="GrievanceResubmission.html"></apptags:helpDoc>
			
	   <div class="widget-content padding">
	      <form:form method="POST" action="GrievanceResubmission.html"
					commandName="command" 
					class="form-horizontal"
					id="form_grievanceReopen">
			
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
		      	<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
		      	<div class="row">
	            <div class="col-xs-12 text-center">
	              <h3 class="margin-bottom-0">${command.complaintAcknowledgementModel.organizationName}</h3>
	              <p><spring:message code="care.status" text="Grievances Status"/></p>
	            </div>
	          </div>
	          
	          <div class="row margin-top-30">
	          	<div class="col-xs-3 text-right"><spring:message code="care.token" text="Token :"/></div>
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
	            <div class="col-xs-6">${command.complaintAcknowledgementModel.ward}</div>
	          </div>
	          <div class="row margin-top-10">
	          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.description" text="Description :"/></div>
	            <div class="col-xs-6">${command.complaintAcknowledgementModel.description}</div>
	          </div>

			 <div class="table-responsive margin-top-10">
			 <label><spring:message code="care.action.history" text="Action History"/></label>
				<table class="table table-bordered table-condensed">
				  <thead>				  	
					  <tr>
					    <th><spring:message code="care.action.datetime" text="Date & Time"/></th>
					    <th><spring:message code="care.action.Action" text="Action"/></th>
					    <th><spring:message code="care.action.employee.name" text="Employee Name"/></th>
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
     					 		<c:if test="${empty action.empGroupDescEng}">
     					 			<spring:message code="care.citizen" text="Citizen" />
     					 		</c:if>
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
				
				
				<div class="panel-group accordion-toggle" id="accordion_single_collapse">
	            	<div class="panel panel-default">
				
					<div class="panel-heading">
	            		<h4 class="panel-title">
	            		<a data-toggle="" class="" data-parent="#accordion_single_collapse" href="#a0">
	            			<spring:message code="care.receipt.resubmit.heading" text="Resubmit complaint"/>
	            		</a></h4>
	            	</div>
						<div class="panel-body">
							<div id="a0" class="panel-collapse collapse in">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control" for="Remark"><spring:message code="care.remarks" text="Remark"/></label>  
									<div class="col-sm-4">
										<form:textarea class="form-control" id="Remark"
											path="careDepartmentAction.comments"></form:textarea>
									</div>
									<label class="col-sm-2 control-label" for="UploadDocuments"><spring:message code="care.upload" text="Upload Document"/></label>
									<div class="col-sm-4">
									<small class="text-blue-2">
										<spring:message code="care.validator.fileUploadNote" text="(Upload File upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif)"/>
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
						</div>
					</div>
				</div>
          		<div class="text-center margin-top-10">
          			<input type="button" class="button-input btn btn-success" value="<spring:message code="care.submit"/>" onclick="resubmitComplaint(this);"  />
          			<button type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-warning"><spring:message code="care.back" text="Back" /></button>
          		</div>
	       </form:form>
	   </div>
	</div>
   </div>
