<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <script type="text/javascript" src="js/workflow/workflow.js"></script> 
<script type="text/javascript">
$(document).ready(function() {
	$('a.role').click(function() {
		  $("#toggle-role").slideToggle("slow");
		});
		
	$('#ward_zoneId').hide();
	/* $('#payModeId').hide(); */
	
	if ($('#hiddenModeId').val() == 'C') {
		$('#complaintId').show();
		$('#complaint_subtype_Id').show();
		/* populateSubType($('#hiddenSubTypeList').val()); */
	} else {
		$('#complaintId').hide(); 
		$('#complaint_subtype_Id').hide();
	}
	
	 if ($('#wardZoneHiddenId').val() == 'N') {
		 $('#ward_zoneId').show();
	 }
	 $('input:radio[name="wardZoneType"]').change(function () {
		 var wardZoneType= $("input[name='wardZoneType']:checked").val();
         if (wardZoneType == 'A') {
             $('#ward_zoneId').hide();
         } else if (wardZoneType == 'N') {
        	 $('#ward_zoneId').show();
         }
         
		});
	 
		});
</script>

<ol class="breadcrumb">
	<li><spring:message code="workflow.bredcrum.home" /></li>
	<li class="active"><spring:message code="workflow.bredcrum.workflow" /></li>
	<li class="active">Edit</li>
</ol>
<div class="content" id="middle_left">

	<div id="content" class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="workflow.header.name" />
			</h2>
		</div>
		<c:url value="${saveAction}" var="action_url_form_submit" />
		
		<%-- <div class="widget-header"><h2><spring:message code="workflow.header.name" /></h2></div> --%>
		
		<div class="error-div alert alert-danger" id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<div class="widget-content padding">
		<form:form action="${action_url_form_submit}" method="POST" name="workflowDefDTOedit" id="workflowDefDTOedit"
			class="form-horizontal" modelAttribute="workflowDefDTO">
			<form:errors path="*" cssClass="errorblock" element="div" />
			
			
			<div class="form-group">
                <label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.org" /> :</label>
					<div class="col-sm-4">
					<form:select path="wdOrgid.orgid" cssClass="form-control" id="onOrgSelect">						
						 <c:forEach items="${orgIdList}" var="obj">	
							<c:choose>
								<c:when test="${workFlowEventDTO.wdOrgid.orgid eq obj[0]}">
								   <form:option value="${obj[0]}" label="${obj[1]}" selected="selected">${obj[1]}</form:option>												
									</c:when>
									<c:otherwise>
										<form:option value="${obj[0]}" label="${obj[1]}"></form:option>
									</c:otherwise>
								</c:choose>								
						</c:forEach> 
					</form:select>				
				</div>
				<label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.dep" /> :</label>
				<div class="col-sm-4"><form:select path="wdDeptId.dpDeptid" cssClass="form-control" id="departmentId" >
				      <option value="0"><spring:message
								code="workflow.form.select.department" /></option>
				        <c:forEach items="${deptList}" var="List">	
							<c:choose>
								<c:when test="${workFlowEventDTO.wdDeptId.dpDeptid eq List[0]}">
								   <form:option value="${List[0]}" label="${List[1]}" selected="selected">${List[1]}</form:option>												
									</c:when>
									<c:otherwise>
										<form:option value="${List[0]}" label="${List[1]}"></form:option>
									</c:otherwise>
								</c:choose>								
						</c:forEach> 
					  </form:select>
				</div>
			</div>
			
			<div class="form-group">
                <label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.service" />:</label>
				<div class="col-sm-4"><form:select name="serviceId" path="wdServiceId" id="serviceId" cssClass="form-control">
						<option value="0"><spring:message code="workflow.form.select.service" /></option>
						  <c:forEach items="${servicelist}" var="List">	
							<c:choose>
								<c:when test="${workFlowEventDTO.wdServiceId eq List[0]}">
								   <form:option value="${List[0]}" label="${List[1]}" selected="selected">${List[1]}</form:option>												
									</c:when>
									<c:otherwise>
										<form:option value="${List[0]}" label="${List[1]}"></form:option>
									</c:otherwise>
								</c:choose>								
						</c:forEach> 
					</form:select>
				      </div>
		            <label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.workflow.name" /> :</label>
					<div class="col-sm-4"><form:input path="wdName" cssClass="form-control" id = "wdName" name ="wdName" value="${workflowDefDTO.wdName}"/></div>				
			</div>

			<%-- <div class="form-group">
                <label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.paymode" /> :</label> 
					<div class="col-sm-4" id="workflowId">
						<label class="radio-inline"><form:radiobutton name="wdMode" value="Y" path="wdMode" id="workflowId" />
						<spring:message code="workflow.form.field.paymode.online" /> </label>
						<label class="radio-inline"><form:radiobutton name="wdMode" value="N" path="wdMode" id="workflowId" />
						<spring:message code="workflow.form.field.paymode.offline" /> </label>
						<label class="radio-inline"><form:radiobutton name="wdMode" value="F" path="wdMode" id="workflowId" />
						<spring:message code="workflow.form.field.paymode.free" /></label>
							
						<form:hidden path="weIdlist" maxlength="3" class="form-control weIdlist" id="weIdlist"   value=""/>	
					</div>
														
			</div>	 --%>
			<form:hidden path="wdId" maxlength="3" class="form-control wdId" id="wdId"   value="${workflowDefDTO.wdId}"/>
			<div class="form-group clear">
					<label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.label.wardzonetype"/></label>
					<div class="col-sm-4">
						<span > <label class="radio-inline">
						<form:hidden path="" value="${command.wardZoneType}" id="wardZoneHiddenId"/>
						<form:radiobutton value="A" path="wardZoneType" /><spring:message code="workflow.form.field.label.all"/></label> <label class="radio-inline">
						<form:radiobutton value="N" path="wardZoneType" /><spring:message code="workflow.form.field.label.wardzone"/></label> 
						</span>
					</div>
				</div>
				<div class="form-group clear" id="ward_zoneId">
					<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="dwzId"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  required-control ward_zone" />
				</div>
			 <div class="form-group clear">
			 		<form:hidden path="" value="${command.modeType}" id="hiddenModeId"/>
					<label class="col-sm-2 control-label">Complaint:</label>
					<div class="col-sm-4">
						<span id="workflowId"><label class="radio-inline"><form:checkbox
									name="wdMode" value="C" path="modeType" id="isComplaintId"/>Is Compliant</label>
						</span>
					</div>
				</div> 
				<div class="form-group clear">
				<div id="complaintId">
					<label class="col-sm-2 control-label required-control">Complaint Type:</label>
					<div class="col-sm-4" >
						<%-- <form:select path="complaintType" class="form-control" id="complaint_type_id">
							<form:option value="0" >Select Complaint Type</form:option>
							<c:forEach items="${complaintList}" var="objArray">
								<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
							</c:forEach>
						</form:select> --%>
						<form:select path="complaintType" id="complaint_type_id" class="form-control">
							<form:option value="0" >Select Complaint Type</form:option>
							<c:forEach items="${complaintList}" var="objArray">
								<c:choose>
								<c:when test="${command.complaintType eq objArray[0]}">
								   <form:option value="${objArray[0]}" label="${objArray[1]}" selected="selected">${objArray[1]}</form:option>												
									</c:when>
									<c:otherwise>
										<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
									</c:otherwise>
								</c:choose>	
							</c:forEach>
						</form:select>
						
					</div>
					</div>
					<div id="complaint_subtype_Id">
					<label class="col-sm-2 control-label required-control">Complaint SubType:</label>
					<div class="col-sm-4" >
					<form:hidden path="" value="${subTypeList}" id="hiddenSubTypeList"/>
					<c:forEach items="${subTypeList}" var="objArray">
						<form:select path="complaintSubType" class="form-control" id="complaintSubType_id">
							<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
						</form:select>
					</c:forEach>
						<%-- <form:select path="complaintSubType" class="form-control" id="complaintSubType_id">
							<form:option value="0" >Select Mode</form:option>
						</form:select> --%>
					</div>
					</div>
				</div>
							
    		<c:set var="d" value="0" scope="page" />
			<div class="table-responsive">
                  <table class="table table-hover table-bordered table-striped" id="customFields">
					<tbody>
						<tr>
							<th><spring:message code="workflow.form.table.column.srno" /></th>
							<th><spring:message code="workflow.form.table.column.eventname" /></th>
							<th><spring:message code="workflow.form.table.column.role" /></th>							
							<%-- <th><spring:message code="workflow.form.table.column.approver" /></th>	 --%>					
							<th><spring:message code="workflow.form.table.column.required" /></th>
							<%-- <th><spring:message code="workflow.form.table.column.dependon" /></th> --%>
							<th><spring:message code="workflow.form.table.column.conditionalStep" /></th>
							<th><spring:message code="workflow.form.table.column.sla" /></th>
							<th><spring:message code="workflow.form.table.column.addremove" /></th>
						</tr>
						<c:choose>			
						<c:when test="${fn:length(workflowDefDTO.workFlowEventDTO) > 0}">
									
						<c:forEach var="workFlowEventDTO" items="${workflowDefDTO.workFlowEventDTO}" varStatus="status">
						    <tr class="appendableClass">
							<td id="srNoId_1">${ workFlowEventDTO.weStepNo}</td>														
							 
							<td><form:select path="workFlowEventDTO[${d}].weServiceEvent" class="form-control width120 eventNameId_${d}" id="eventNameId_${d}">
									<form:option value="0"><spring:message code="workflow.form.select.event" /></form:option>
									<c:forEach items="${eventList}" var="eventList">									
										<c:choose>
											<c:when test="${workFlowEventDTO.weServiceEvent eq eventList[0]}">
												<form:option value="${eventList[0]}"  selected="selected">${eventList[1]}</form:option>												
											</c:when>
											<c:otherwise>
												<form:option value="${eventList[0]}" >${eventList[1]}</form:option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</td>																		
							 <td>
							  <form:select path="workFlowEventDTO[${d}].roleId" multiple="multiple" style="height: auto;"  class="form-control width163 roleId_${d}" id="roleId_${d}">
							    <form:option value="0"><spring:message code="workflow.form.select.role" /></form:option>		
							     <c:forEach items="${rolelist}" var="rolelist">
							       <c:set var="isMatch" value="false"/>
							       <c:forEach items="${workFlowEventDTO.evntRolelist}" var="eventRole">	  		  								
										<c:choose>
											<c:when test="${eventRole eq rolelist[0]}">
											   <c:set var="isMatch" value="true"/>
											</c:when>
										</c:choose>
										</c:forEach>
										<c:choose>
											<c:when test="${isMatch eq 'true'}">
												<form:option value="${rolelist[0]}"  selected="selected">${rolelist[1]}</form:option>												
											</c:when>
										<c:otherwise>
												<form:option value="${rolelist[0]}" >${rolelist[1]}</form:option>
										</c:otherwise>
									</c:choose>
								</c:forEach> 					 
							 </form:select>							
							</td>
							
							<%-- <td>
							  <form:select path="workFlowEventDTO[${d}].roleId" multiple="multiple" style="height: auto;"  class="form-control roleId_${d}" id="roleId_${d}">
							    <form:option value="0"><spring:message code="workflow.form.select.role" /></form:option>		
							     <c:forEach items="${rolelist}" var="rolelist">									
										<c:choose>
											<c:when test="${workFlowEventDTO.roleId eq rolelist[0]}">
												<form:option value="${rolelist[0]}"  selected="selected">${rolelist[1]}</form:option>												
											</c:when>
											<c:otherwise>
												<form:option value="${rolelist[0]}" >${rolelist[1]}</form:option>
											</c:otherwise>
										</c:choose>
									</c:forEach> 					 
							 </form:select>							
							</td> --%>
							
							<%--  <td>
							  <form:select path="workFlowEventDTO[${d}].roleId" multiple="multiple" style="height: auto;" class="form-control roleId_${d}" id="roleId_${d}">
							    <form:option value="0"><spring:message code="workflow.form.select.role" /></form:option>		
							      <c:forEach items="${rolelist}" var="rolelist">																
								      <form:option value="${rolelist[0]}" >${rolelist[1]}</form:option>																				
								</c:forEach>							    					 
							 </form:select>							
							</td> --%>
							
							<%-- <td><form:input path="workFlowEventDTO[${d}].weNoOfApprover" maxlength="2" class="form-control year-width1 appNoId_${d}" id="appNoId_${d}" value="${workFlowEventDTO.weNoOfApprover}" /></td> --%>
							
							<td><form:select path="workFlowEventDTO[${d}].weIsrequire" class="form-control width120 requiredId_${d}" id="requiredId_${d}">
									<form:option value="0"><spring:message code="workflow.form.select.required" />
									</form:option>
									<c:forEach items="${requiredType}" var="objEntry">
									<c:choose>
											<c:when test="${workFlowEventDTO.weIsrequire eq objEntry.lookUpCode}">
												<form:option value="${objEntry.lookUpCode}"  selected="selected">${objEntry.descLangFirst}</form:option>												
											</c:when>
											<c:otherwise>
												<form:option value="${objEntry.lookUpCode}">${objEntry.descLangFirst}</form:option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select></td>
							<%-- <td><form:input path="workFlowEventDTO[${d}].weDependsOnSteps" maxlength="3" class="form-control year-width1 dependId_${d}" id="dependId_${d}" value="${workFlowEventDTO.weDependsOnSteps}" /></td> --%>
							
							<td><form:input path="workFlowEventDTO[${d}].weCondiatinalFalseNextStep" maxlength="3" class="form-control year-width1 conditionalNextstep_${d}" id="conditionalNextstep_${d}"  value="${workFlowEventDTO.weCondiatinalFalseNextStep}"/></td>							
							<td><form:input path="workFlowEventDTO[${d}].weSla" maxlength="3" class="form-control year-width1 slaId_${d}" id="slaId_${d}" value="${workFlowEventDTO.weSla}" /></td>							
							<td>
							<a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" class="addCF btn btn-success btn-sm" data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
							<a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" class="remCF btn btn-danger btn-sm" data-original-title="Delete" onclick="removeEvent('${workFlowEventDTO.weId}')"><i class="fa fa-trash"></i></a>

							</td>
						    <td style="display: none"><form:hidden path="workFlowEventDTO[${d}].weId" maxlength="3" class="form-control weId_${d}" id="weId_${d}"  name="workFlowEventDTO[${d}].weId"  value="${workFlowEventDTO.weId}"/></td>
							<td style="display: none"><form:hidden path="workFlowEventDTO[${d}].weStepNo" maxlength="3" class="form-control weStepNo_${d}" id="weStepNo_${d}" name="workFlowEventDTO[${d}].weStepNo"  value="${ workFlowEventDTO.weStepNo}"/></td>
						</tr>
						<c:set var="d" value="${d + 1}" scope="page"/>
						 </c:forEach>
				         </c:when>
				       </c:choose>
					</tbody>
				</table>
			</div>
			<div class="text-center padding-top-10">
				<input name="workFlowEditSubmit" type="button" value="Submit" class="btn btn-success" id="workFlowEditSubmit" /> 
				<input type="reset" class="btn btn-danger" id="resetId" />
				<button type="button" class="btn btn-danger" onclick="window.location.href='WorkflowHome.html'">Back</button>
			</div>
		</form:form>
		</div>
		</div>
</div>
