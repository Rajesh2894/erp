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

function executeUsageChanges(element) {
    var	formName =	findClosestElementId(element, 'form');
	var theForm	=	'#'+formName;
	var requestData =__serializeForm(theForm);
    var response=__doAjaxRequest('ExecuteChangeOfUsage.html?saveform','POST',requestData, false);
    
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

$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	changeYear: true
});
$(document).ready(function() {
	var applicationDate = $("#applicationDate").val();
	$(".datepicker").datepicker("option", "minDate", applicationDate);
});

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.usage.change" text="Execute Uses Changes"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i> <spring:message code="water.ismandtry"/>
				</span>
			</div>
			<form:form action="ExecuteChangeOfUsage.html" class="form-horizontal form" name="frmExecuteUsageChange" id="frmExecuteUsageChange">
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
              <label class="col-sm-2 control-label"><spring:message code="water.application.date" text="Application Date"/><spring:message code="water.ismandtry"/></label>
              <div class="col-sm-4">
                 <form:input cssClass="form-control" disabled="true" path="applicationDate" id="applicationDate"/>
              </div>
            </div>
            
            <h4><spring:message code="water.disconet.execute.detail" text="Execution Details"/></h4>
            <div class="form-group">
            <label class="col-sm-6 checkbox-inline"><form:checkbox path="commonDto.chanGrantFlag" value="Y"/><spring:message code="water.usage.execute" text="Execute Change Of Usage"/></label>
              
            <label class="col-sm-2 control-label"><spring:message code="water.usage.execute.date" text="Change Of Usage Execution Date"/></label>
              <div class="col-sm-4">
                <div class="input-group">
                  <form:input path="commonDto.chanExecdate" cssClass="form-control datepicker" id="datepicker3"/>
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
                    <form:input path="" cssClass="form-control" id="datepicker4" value="${appDate }" readonly="true"/>
                   
                  <label class="input-group-addon" for="datepicker4"><i class="fa fa-calendar"></i></label>
                </div>
              </div>
            </div>
            
			<%-- 	<c:set var="appId" value="${command.lableValueDTO.applicationId}"/>
				<c:set var="labelId" value="${command.lableValueDTO.lableId}"/>
				<c:set var="serviceId" value="${command.serviceId}"/> --%>
			    <%-- <form:hidden path="hasError" id="error"/>  --%>
				
				<div class="text-center padding-top-10">
				<c:if test="${command.homeGrid eq false }">
				<button type="button" class="btn btn-success" onclick="executeUsageChanges(this)"><spring:message code="water.btn.submit"/></button>
					 <input type="button" onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${command.applicationId}','${command.levelId}','${command.serviceId}')" class="btn" value="Back">
				</c:if>
				<c:if test="${command.homeGrid eq true }">
					<apptags:submitButton entityLabelCode="" actionParam="saveform" successUrl="AdminHome.html" cssClass="btn btn-success"></apptags:submitButton>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</c:if>
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

 