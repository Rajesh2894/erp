
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/quartz/quartzMaster.js"></script>
<script>

$(".Moredatepicker").timepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	changeYear: true,	
	minDate: '0',
});
</script>






<c:if test="${command.hasValidationErrors() eq true}">
			
		
<script>
	
getAvailableJobsAfterValidationError();

</script>						
					
</c:if>	

<ol class="breadcrumb">
      <li><a href="CitizenHome.html"><strong class="fa fa-home"></strong><span class="hide">Home</span></a></li>
      <li><spring:message code="quartz.bredCrum.commonMaster"/></li>
</ol>

 <div id="content" class="content"> 
          <div class="widget">
          
            <div class="widget-header">
              <h2><spring:message code="quartz.bredCrum.jobSchedule"/></h2>
              <div class="additional-btn"><a href="#" class="widget-toggle"><i class="icon-down-open-2"><span class="hide">help</span></i></a> 
              </div>
 			</div>
 			
<div class="widget-content padding">


	
	<form:form action="QuartzSchedulerMaster.html"		name="frmQuartzSchedulerMaster" id="frmQuartzSchedulerMaster"	cssClass="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		
		
		<div class="form-group">
		
			<label class="control-label col-sm-2 required-control" for="department"><spring:message	code="quartz.form.fieldName.department" /> :</label>
			<div class="col-sm-4">
				<apptags:selectField items="${command.departmentList}" selectOptionLabelCode="quartz.form.fieldName.selectDep"
					 fieldPath="departmentForQuartz" cssClass="input2 hasNameClass form-control" changeHandler=" return findJobs(this,'jobName')"/> 
			</div>
				
						
			<label class="control-label col-sm-2 required-control" for="jobName"><spring:message	code="quartz.form.fieldName.jobName" /> :</label>
			<div class="col-sm-4">
				<apptags:selectField items="${command.availableJobsForDept}" selectOptionLabelCode="quartz.form.fieldName.selectJob" 
						fieldPath="jobId" hasId="true" cssClass="input2 hasNameClass form-control" changeHandler="return setJobCode()"/> 
						<form:hidden path="entity.hiddenJobCode" value="${command.entity.hiddenJobCode}" id="hiddenJobCode"/>
			</div>
		</div>
		
		
		
		<div class="form-group">
						
 	<apptags:select labelCode="quartz.form.fieldName.jobFrequency" items="${command.jobRunFrequencyLookUp}" changeHandler="runOnDateAndTime()" 
 		path="entity.cpdIdBfr" selectOptionLabelCode="quartz.form.fieldName.selectJobFrequency" isLookUpItem="true"></apptags:select> 
 	 
	<form:hidden path="entity.hiddenJobFrequencyType" id="hiddenJobFrequencyType"/>		
	
			<div id="daily">
			<label class="control-label col-sm-2 required-control" for="timeAt"><spring:message	code="quartz.form.fieldName.timeAt" /> :</label>
			<div class="col-sm-4" >
			<form:input path="entity.startAtTime_Daily"  id="startAt_daily"  cssClass="startAt_daily input2 hasNameClass form-control" readonly="true"/>
			</div>
			</div>
			
			<div id="startOnDate_monthly_div">
			<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.timeAt" /> :</label>
			<div class="col-sm-4">
			<form:input path="entity.startAtTime"  id="startAt_others"	cssClass="input2 hasNameClass form-control" readonly="true"/>
			</div>
			</div> 
			
			
			
		</div>
		
		
		<%-- <div class="form-group" >
 			
			<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.startOn" /> :</label>
			<form:input path="entity.startOnDate_yearly" id="startOnDate_yearly" placeholder="DD-MM-YYYY" cssClass="input2 hasNameClass form-control"/>
		</div> --%>
			
		<div class="form-group">
		
			<%-- <div id="startOnDate_monthly_div">
			<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.timeAt" /> :</label>
			<div class="col-sm-4">
			<form:input path="entity.startAtTime"  id="startAt_others"	cssClass="input2 hasNameClass form-control" readonly="true"/>
			</div>
			</div> --%>
		
			<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.procOrFuncName" /> </label>
			
			<div class="col-sm-4">
			<apptags:inputField fieldPath="entity.jobProcName" cssClass="input2 hasNameClass form-control"/>	
			</div>
			
			<div id="others">
			<apptags:date fieldclass="Moredatepicker" datePath="entity.startOnDate_monthly" labelCode="quartz.form.fieldName.startOn"></apptags:date>
			</div>
			
			<div id="startOnDate_yearly_div">
			<apptags:date fieldclass="Moredatepicker" datePath="entity.startOnDate_yearly" labelCode="quartz.form.fieldName.startOn"></apptags:date>
			</div>
		</div>
	
		
		<div class="form-group">
		
			<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.serviceClass" /> :</label>
			<div class="col-sm-4">
			<apptags:inputField fieldPath="entity.jobClassName" cssClass="input2 hasNameClass form-control"/>
			</div>
			
			<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.serviceMethod" /> :</label>
			<div class="col-sm-4">
			<apptags:inputField fieldPath="entity.jobFuncName" cssClass="input2 hasNameClass form-control"/>	
			</div>
		</div>
		
		<div class="form-group">
			<label class="control-label col-sm-2 required-control"><spring:message code="quartz.form.gridLabel.status" />:</label>
			
			<div class="col-sm-1" style="padding-left: 45px;">
				<spring:message code="quartz.form.field.status.active" />
			</div>
			
			<div class="col-sm-1">
				<form:radiobutton path="entity.status"  value="A" />
			</div>
			
			<div class="col-sm-1" style="padding-left: 45px;">	
				<spring:message code="quartz.form.field.status.inActive" />
			</div>
			
			<div class="col-sm-1">
				<form:radiobutton path="entity.status" value="I" />
			</div>
		</div>
		
		<div class="form-elements padding_10" align="center">
		
		<input type="submit" class="btn btn-success" onclick="return saveQuartzMasterForm(this)" value="<spring:message code="eip.commons.bt.save" text="save" />" />
		<apptags:resetButton/> <apptags:backButton url="QuartzSchedulerMaster.html" />
		</div>
</form:form>
</div>
</div>
</div>		