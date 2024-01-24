<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/workflow/chekerAction.js"></script>

<%@ attribute name="hideForward" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="hideSendback" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="hideReject" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="showInitiator" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="hideUpload" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="showSendbackToApplicant" required="false" rtexprvalue="true" type="java.lang.Boolean"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#CheckAction"><spring:message code="workflow.checkAct.userAct"/></a>
		</h4>
	</div>
		<input type="hidden" value="${showInitiator}" id="showInitiator">
		<%-- <c:set var="radioButtonsRequired" value="Approve" /> --%>
		<c:set var="radioButtonsRequired"><spring:message code="eip.dept.auth.approve" text="Approve" /></c:set>              
		<c:set var="radioButtonsRequiredVal" value="APPROVED" />
		<c:if test="${!hideForward}">
			<%-- <c:set var="radioButtonsRequired" value="${radioButtonsRequired},Forward"/> --%>
			<c:set var="radioButtonsRequired">${radioButtonsRequired},<spring:message code="eip.dept.auth.forward" text="Forward" /></c:set>
			<c:set var="radioButtonsRequiredVal" value="${radioButtonsRequiredVal},FORWARD_TO_EMPLOYEE" />
		</c:if>
		<c:if test="${!hideReject}">
			<%-- <c:set var="radioButtonsRequired" value="${radioButtonsRequired},Reject"/> --%>
			<c:set var="radioButtonsRequired">${radioButtonsRequired},<spring:message code="eip.dept.auth.reject" text="Reject" /></c:set>
			<c:set var="radioButtonsRequiredVal" value="${radioButtonsRequiredVal},REJECTED" />
		</c:if>
		<c:if test="${!hideSendback}">
			<%-- <c:set var="radioButtonsRequired" value="${radioButtonsRequired},Send Back"/> --%>
			<c:set var="radioButtonsRequired">${radioButtonsRequired},<spring:message code="eip.dept.auth.sendBack" text="Send Back" /></c:set>
			<c:set var="radioButtonsRequiredVal" value="${radioButtonsRequiredVal},SEND_BACK" />
		</c:if>
		<c:if test="${showSendbackToApplicant}">
			<c:set var="radioButtonsRequired">${radioButtonsRequired},<spring:message code="" text="Send Back To Applicant" /></c:set>
			<c:set var="radioButtonsRequiredVal" value="${radioButtonsRequiredVal},SEND_BACK_TO_APPLICANT" />
		</c:if>
		 <div id="CheckAction" class="panel-collapse in">
		  <div class="panel-body">
            <div class="form-group">
          		<apptags:radio cssClass="addInfo" radioLabel="${radioButtonsRequired}" radioValue="${radioButtonsRequiredVal}" labelCode="work.estimate.approval.decision" path="workflowActionDto.decision"  changeHandler="loadDataBasedOnDecision(this)" isMandatory="true"></apptags:radio>	          
           </div>
           
 <%------------------------------------------Forward option------------------------------------------------%>
           <div id="forward">
           <div class="form-group">
         	<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="workflow.checkAct.emp"/></label>
           		<div class="col-sm-4">
     <%--       		<c:choose>
 			 <c:when test="${not empty command.getCheckActMap()}" --%>
           	<form:select path="workflowActionDto.forwardToEmployee" class="form-control mandColorClass" multiple="multiple" id="empId">
           		  <c:forEach items="${command.getCheckActMap()}" var="entry" varStatus="key">
           	  		<form:option value="${entry.key}" label="${entry.value}"></form:option>
				</c:forEach>
			</form:select>
			</div>
			</div>
			</div>
		
	<%---------------------------------SendBack option----------------------------------------%>
	
		<div id="sendBack">
           <div class="form-group">
            <label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="workflow.checkAct.event"/></label>
           <div class="col-sm-4">
    		<form:select path="" class="form-control" id="serverEvent" onchange="loadAllEmpBasedOnEvent(this)">
				<form:option value="0">Select Event</form:option>
           		  <c:forEach items="${command.getWorkflowEventEmpList()}" var="lookUp" varStatus="key">
           			<form:option value="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</form:option>
				  </c:forEach>
			</form:select>
			</div>
			<div id="sendBackEmp">
			 <label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="workflow.checkAct.emp"/></label>
           <div class="col-sm-4">
      		<form:select path="workflowActionDto.sendBackToEmployee" class="form-control" multiple="multiple" id="eventEmp">																
           		 <c:forEach items="${command.getCheckActMap()}" var="entry" varStatus="key">
           			<form:option value="${entry.key}">${entry.value}</form:option>
				</c:forEach>
			</form:select>
			</div>
			</div>
			</div>
		</div> 
		
<%---------------------------------Common Comment And Upload----------------------------------------%>
		
		<div class="form-group">
			    <apptags:textArea labelCode="workflow.checkAct.remark" path="workflowActionDto.comments" isMandatory="true" cssClass="mandColorClass comment" maxlegnth="50"/>
		</div>
		<c:if test="${!hideUpload}">
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message code="workflow.checkAct.AttchDocument"/></label>
				    <div class="col-sm-4">
				       <apptags:formField fieldType="7" fieldPath="" showFileNameHTMLId="true" 
				        fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
					    validnFunction="CHECK_LIST_VALIDATION_EXTENSION" currentCount="0">
				      </apptags:formField> 
				      <!-- D#117229 -->
				      <div class="col-sm-12">
						<small class="text-blue-2 "> <spring:message
								code="care.validator.fileUploadNote"
								text="(Upload File upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif)" />
						</small>
					  </div>
				    </div>
			</div> 
		</c:if>
		    </div> 
		 </div>
		 
		 <form:hidden id="selectedServerEvent" path="" value="${command.workflowActionDto.sendBackToGroup}_${command.workflowActionDto.sendBackToLevel}"/>
</div>