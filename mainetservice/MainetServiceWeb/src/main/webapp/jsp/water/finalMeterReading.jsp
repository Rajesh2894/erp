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
    var response/* =__doAjaxRequest('FinalMeterReading.html?saveform','POST',requestData, false) */;
    saveOrUpdateForm(element,"Your application saved successfully!", '', 'saveform');
    var appId='${command.applicationId}';
    var serviceId= '${command.serviceId}';
	var labelId='${command.levelId}';
	
	<c:if test="{$command.hasValidationErrors() eq false}">
	if ($.isPlainObject(response))
	{
		loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',appId,labelId,serviceId); 
	}else if (typeof(response) === "string")
	{
		$(".content-page").html(response);
		prepareTags();
	}
	</c:if>
}

$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	changeYear: true,
	maxDate: '-0d'
});

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.disconet.last.reading.detail" text="Final Meter Reading"/></h2>
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
			<form:form action="FinalMeterReading.html" class="form-horizontal form" name="frmExecuteUsageChange" id="frmExecuteUsageChange">
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
              <label class="col-sm-2 control-label"><spring:message code="water.application.date" text="Application Date"/></label>
              <div class="col-sm-4">
                 <form:input cssClass="form-control" disabled="true" path="applicationDate"/>
              </div>
            </div>
            
            <h4><spring:message code="water.disconet.last.reading.detail" text="Final Meter Reading"/></h4>
            	<div class="form-group">
          			<label class="control-label col-sm-2"><spring:message code="water.connectiontype"/></label>
		  
		           <div class="col-sm-4">
						<label class="radio-inline"><form:radiobutton path="metered" value="true" class="conSelect"  disabled="true"/><spring:message code="water.meter.meteredCheckBox" text="Metered"/></label>
		            	<label class="radio-inline"><form:radiobutton path="metered" value="false" class="conSelect" disabled="true"/> <spring:message code="water.meter.non.meteredCheckBox" text="Non Metered"/></label>
					</div>  
				</div>
				
				
            <c:if test="${command.metered eq true}">
	            <div class="form-group">
	              <label class="col-sm-2 control-label"><spring:message code="water.disconet.last.reading" text="Final Meter Reading"/></label>
	              <div class="col-sm-4">
	               	 <form:input path="finalMeterReading" cssClass="form-control" maxlength="10"/>
	              </div>
	              <label class="col-sm-2 control-label"><spring:message code="water.disconet.last.reading.date" text="Final Meter Reading Date"/></label>
	              <div class="col-sm-4">
	                <div class="input-group">
	                    <form:input path="finalReadingDate" cssClass="form-control datepicker" id="datepicker" />
	                  	<label class="input-group-addon" for="datepicker4"><i class="fa fa-calendar"></i></label>
	                </div>
	              </div>
	            </div>
            </c:if>
            
				<div class="text-center padding-top-10">
				<button type="button" class="btn btn-success" onclick="executeUsageChanges(this)"><spring:message code="water.btn.submit"/></button>
					 <input type="button" onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${command.applicationId}','${command.levelId}','${command.serviceId}')" class="btn" value="Back">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

 