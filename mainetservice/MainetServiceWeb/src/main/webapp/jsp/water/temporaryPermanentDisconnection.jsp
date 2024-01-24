 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/cfc/scrutiny.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">

function executeDisconnectionProcess(element) {
    var	formName =	findClosestElementId(element, 'form');
	var theForm	=	'#'+formName;
	var requestData =__serializeForm(theForm);
    var response=__doAjaxRequest('ExecuteDisConnectionProcess.html?saveform','POST',requestData, false);
    
    var appId='${command.applicationId}';
    var serviceId= '${command.serviceId}';
	var labelId='${command.levelId}';
	
	if ($.isPlainObject(response))
	{
		loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',appId,labelId,serviceId); 
	}else if (typeof(response) === "string")
	{
		$(".content-page").html(response);
		prepareTags();
	}
	
}

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.disconet.detail" text="Disconnection Execution Details"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<form:form action="ExecuteDisConnectionProcess.html" class="form-horizontal form" name="frmExecuteDisConnection" id="frmExecuteDisConnection">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<h4 class="margin-top-0"><spring:message code="water.form.appdetails" text="Application Details"/></h4>
            <div class="form-group">
              <label class="col-sm-2 control-label"><spring:message code="water.service.name" text="Service Name"/></label>
              <div class="col-sm-4">
                <form:input cssClass="form-control" disabled="true" path="serviceName"/>
              </div>
              <label class="col-sm-2 control-label"><spring:message code="water.application.number" text="Application Number"/></label>
              <div class="col-sm-4">
                 <form:input cssClass="form-control" disabled="true" path="applicationId"/>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-sm-2 control-label"><spring:message code="water.applicnt.name" text="Applicant Name"/></label>
              <div class="col-sm-4">
                 <form:input cssClass="form-control" disabled="true" path="applicanttName"/>
              </div>
              <label class="col-sm-2 control-label"><spring:message code="water.application.date" text="Application Date"/>Application Date</label>
              <div class="col-sm-4">
                 <form:input cssClass="form-control" disabled="true" path="applicationDate"/>
              </div>
            </div>
            
            <h4><spring:message code="water.disconet.execute.detail" text="Execution Details"/></h4>
            <div class="form-group">
            <label class="col-sm-6 checkbox-inline"><form:checkbox path="disconnectionDTO.discGrantFlag" value="Y"/><spring:message code="water.disconet.execute" text="Execute Disconnection"/></label>
              
            <label class="col-sm-2 control-label"><spring:message code="water.disconet.execute.date" text="Disconnection Execution Date"/></label>
              <div class="col-sm-4">
                <div class="input-group">
                  <form:input path="disconnectionDTO.discExecdate" cssClass="form-control datepicker" id="datepicker3"/>
                  <label class="input-group-addon" for="datepicker3"><i class="fa fa-calendar"></i></label>
                </div>
              </div>
              </div>
            
            <div class="form-group">
              <label class="col-sm-2 control-label"><spring:message code="water.disconet.approve.by" text="Approved By"/></label>
              <div class="col-sm-4">
              	<c:set var="fullName" value="${command.approveBy }"/>
               	 <form:input path="" cssClass="form-control" value="${fullName}"/>
              </div>
              <label class="col-sm-2 control-label"><spring:message code="water.disconet.approve.date" text="Approved Date"/></label>
              <div class="col-sm-4">
                <div class="input-group">
                	 <fmt:formatDate value="${command.approveDate }" var="appDate" pattern="dd/MM/yyyy"/>	
                    <form:input path="" cssClass="form-control" id="datepicker4" value="${appDate }"/>
                   
                  <label class="input-group-addon" for="datepicker4"><i class="fa fa-calendar"></i></label>
                </div>
              </div>
            </div>
            
			<%-- 	<c:set var="appId" value="${command.lableValueDTO.applicationId}"/>
				<c:set var="labelId" value="${command.lableValueDTO.lableId}"/>
				<c:set var="serviceId" value="${command.serviceId}"/> --%>
			    <%-- <form:hidden path="hasError" id="error"/>  --%>
				
				<div class="text-center padding-top-10">
				<button type="button" class="btn btn-success btn-submit" onclick="executeDisconnectionProcess(this)"><spring:message code="water.btn.submit" text="Submit"/></button>
					 <input type="button" onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${command.applicationId}','${command.levelId}','${command.serviceId}')" class="btn" value="Back">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

 