
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/quartz/quartzMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<c:if test="${command.hasValidationErrors() eq true}">
<script type="text/javascript">
getAvailableJobsAfterValidationError();
</script>						
</c:if>	

<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>


  
    <div class="widget">
          
           <%--  <div class="widget-header">
              <h2><spring:message code="quartz.bredCrum.edit.jobSchedule"/></h2>
              <div class="additional-btn"><a href="#" class="widget-toggle"><i class="icon-down-open-2"><span class="hide">help</span></i></a> 
              </div>
 			</div> --%>

	<div class="widget-content padding">
			
			<form:form action="QuartzSchedulerMaster.html"	name="frmQuartzSchedulerMaster" id="frmQuartzSchedulerMaster" cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="form-group">
								
				<label class="control-label col-sm-2 required-control"><spring:message code="quartz.form.fieldName.department" /> :</label>
				<div class="col-sm-4">
				<apptags:selectField items="${command.departmentList}" selectOptionLabelCode="quartz.form.fieldName.selectDep"
				 fieldPath="departmentForQuartz" cssClass="input2 hasNameClass form-control" changeHandler=" return findJobs(this,'jobName')"/> 
				</div>
			
			<form:hidden path="" value="${command.entity.departmentForQuartz}" id="hiddenDept"/>
				
				<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.jobName" /> :</label>
				<div class="col-sm-4">
					<apptags:selectField items="${command.availableJobsForDept}" selectOptionLabelCode="quartz.form.fieldName.selectJob" 
					fieldPath="jobId" hasId="true" cssClass="input2 hasNameClass form-control" changeHandler="return setJobCode()"/>
				</div>	 
					<form:hidden path="entity.hiddenJobCode" value="${command.entity.hiddenJobCode}" id="hiddenJobCode"/>
					<form:hidden path="" value="${command.entity.cpdIdBjo}" id="hiddencpdIdBjo"/>
			</div>
			
			
			<div class="form-group">
			
				<apptags:select labelCode="quartz.form.fieldName.jobFrequency" items="${command.jobRunFrequencyLookUp}" changeHandler="runOnDateAndTime()" 
 				path="entity.cpdIdBfr" selectOptionLabelCode="quartz.form.fieldName.selectJobFrequency" isLookUpItem="true"></apptags:select> 
				
				<form:hidden path="entity.hiddenJobFrequencyType" id="hiddenJobFrequencyType"/>											
			
			
			<div id="daily">
				
				<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.timeAt" /> :</label>
				<div class="col-sm-4">
				<form:input path="entity.startAtTime_Daily"  id="startAt_daily" cssClass="input2 hasNameClass form-control"	readonly="true"/>													
				</div>
				</div>
				
				<div id="startOnDate_monthly_div">
				<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.timeAt" /> :</label>
				<div class="col-sm-4">
					<form:input path="entity.startAtTime"  id="startAt_others" cssClass="input2 hasNameClass form-control"	readonly="true"/>
				</div>
				</div>
			</div>
			
				
				
				
			<div class="form-group">
				<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.procOrFuncName" /> :</label>
				<div class="col-sm-4">
				<apptags:inputField fieldPath="entity.jobProcName" cssClass="input2 hasNameClass form-control"/>												
				</div>
				
				
				<div id="startOnDate_yearly_div">
					<apptags:date fieldclass="Moredatepicker" datePath="entity.startOnDate_yearly" labelCode="quartz.form.fieldName.startOn"></apptags:date>										
				</div>
				
				<div id="others">
					<!-- <div id="startOnDate_monthly_div"> -->
					<apptags:date fieldclass="Moredatepicker" datePath="entity.startOnDate_monthly" labelCode="quartz.form.fieldName.startOn"></apptags:date>										
					<!-- </div> -->
				
				<%-- <div class="element">
					<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.timeAt" /> :</label>
					<form:input path="entity.startAtTime"  id="startAt_others" cssClass="mandClassColor startAt_others"	readonly="true"/>													
					<span class="mand">*</span>
				</div> --%>
				</div>
			</div>
					
			
			<div class="form-group">	
			
				<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.serviceClass" /> :</label>
				<div class="col-sm-4">
				<apptags:inputField fieldPath="entity.jobClassName" cssClass="input2 form-control"/>
				</div>
				
				<label class="control-label col-sm-2 required-control"><spring:message	code="quartz.form.fieldName.serviceMethod" /> :</label>
				<div class="col-sm-4">
				<apptags:inputField fieldPath="entity.jobFuncName" cssClass="input2 hasNameClass form-control"/>
				</div>												
			</div>
	      	
	      	<div class="form-group">	
	      		<label class="control-label col-sm-2 required-control"><spring:message code="quartz.form.gridLabel.status" />:</label>
				<%-- <div class="col-sm-1" style="padding-left: 45px;">
					<spring:message code="quartz.form.field.status.active" />
				</div>
				
				<div class="col-sm-1">
					<form:radiobutton path="entity.status"  value="A" />
				</div>
				
				<div class="col-sm-1">
					<spring:message code="quartz.form.field.status.inActive" />
				</div>
				
				<div class="col-sm-1">
					<form:radiobutton path="entity.status" value="I" />
				</div>
 --%>				
				<div class="col-sm-4">
							<label class="radio-inline mandColorClass">
							<form:radiobutton path="entity.status"  value="A" />
							<spring:message code="quartz.form.field.status.active" /></label>
								 <label class="radio-inline mandColorClass">
								<form:radiobutton path="entity.status" value="I" /> 
								<spring:message code="quartz.form.field.status.inActive" /></label>
					</div>
					<div id="min">
			<label class="control-label col-sm-2 required-control" for="Min/hr"><spring:message	text="Interval(In Minute)" code="quartz.form.gridLabel.interval.minute" /> :</label>
			<div class="col-sm-4" >
			<form:input path="entity.repeatMinTime"  id="startAt_min"  cssClass="input2 form-control hasNumber"/>
			</div>
			</div>
			<div id="hour">
			<label class="control-label col-sm-2 required-control"><spring:message	text="Interval(In Hour)" code="quartz.form.gridLabel.interval.hour"/> :</label>
			<div class="col-sm-4">
			<form:input path="entity.repeatHour"  id="startAt_hour"	cssClass="input2 form-control hasNumber"/>
			</div>
			</div> 
					</div>
			
			
			
			
			
		
							
	      
				<div class="form-group">			
			<div class="form-elements padding_10" align="center">
			<input type="submit" class="btn btn-success btn-submit" onclick="return saveQuartzMasterAfterEdit(this)" value="<spring:message code="eip.commons.bt.save"  text="save"/>" />
 			<apptags:backButton		url="QuartzSchedulerMaster.html" />
			</div>
</div>
				
				
			</form:form>
		</div>
		</div>
	


