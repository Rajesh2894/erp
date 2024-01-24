<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/workflow/workflow.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('a.role').click(function() {
			$("#toggle-role").slideToggle("slow");
		});
		$('#ward_zoneId').hide();
		/* $('#payModeId').hide(); */
		$('#complaintId').hide(); 
		 $('#complaint_subtype_Id').hide();
		 $('input[name=wardZoneType]').attr("disabled",true);
	});
</script>
<div id="divId">




<ol class="breadcrumb">
	<li><spring:message code="workflow.bredcrum.home" /></li>
	<li class="active"><spring:message code="workflow.bredcrum.workflow" /></li>
</ol>

<div class="content" id="middle_left">

	<div id="content" class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="workflow.header.name" />
			</h2>
		</div>
		<div class="widget-content padding">
			<c:url value="${saveAction}" var="action_url_form_submit" />
			<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form action="${action_url_form_submit}" method="POST" name="" id="workFlowFrmId"
				class="form form-horizontal" modelAttribute="formDTO">
				<form:errors path="*" cssClass="errorblock" element="div" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="workflow.form.field.org" /> :</label>
					<div class="col-sm-4">
						<form:select path="wdOrgid.orgid" id="onOrgSelect"
							class="form-control">
							<form:option value="0">
								<spring:message code="workflow.form.select.org" />
							</form:option>
							<c:forEach items="${orgList}" var="objArray">
								<c:choose>
								<c:when test="${formDTO.wdOrgid.orgid eq objArray[0]}">
								   <form:option value="${objArray[0]}" label="${objArray[1]}" selected="selected">${objArray[1]}</form:option>												
									</c:when>
									<c:otherwise>
										<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
									</c:otherwise>
								</c:choose>	
							</c:forEach>
						</form:select>
						
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="workflow.form.field.dep" /> :</label>
					<div class="col-sm-4">
						<form:select path="wdDeptId.dpDeptid" id="departmentId"
							class="form-control">
							<option value="0"><spring:message
									code="workflow.form.select.department" /></option>
						</form:select>
					</div>
				</div>
				<div class="form-group clear">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="workflow.form.field.service" /> :</label>
					<div class="col-sm-4">
						<form:select name="serviceId" path="wdServiceId" id="serviceId"
							class="form-control">
							<option value="0"><spring:message
									code="workflow.form.select.service" /></option>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="workflow.form.field.workflow.name" /> :</label>
					<div class="col-sm-4">
						<form:input path="wdName" id="wdName" name="wdName"
							class="form-control" />
					</div>
				</div>
				<!-- added ward-zone -->
				<div class="form-group clear">
					<label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.label.wardzonetype"/></label>
					<div class="col-sm-4">
						<span > <label class="radio-inline">
						<form:radiobutton value="A" path="wardZoneType" /><spring:message code="workflow.form.field.label.all"/></label> <label class="radio-inline">
						<form:radiobutton value="N" path="wardZoneType" /><spring:message code="workflow.form.field.label.wardzone"/></label> 
						</span>
					</div>
				</div>
				 <%-- <c:if test="${formDTO.prefixFound}">-------------${formDTO.prefixFound}
					<div class="form-group clear" id="ward_zoneId">
					<apptags:lookupFieldSet baseLookupCode="${formDTO.prefix}" hasId="true"
									showOnlyLabel="false" pathPrefix="dwzId"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  required-control ward_zone" />
				</div>
				</c:if> --%>
					 <div class="form-group clear" id="ward_zoneId">
					
							<%-- <jsp:include page="/jsp/workflow/ward_zone_block.jsp" /> --%>
							
					</div> 
					<!-- added ward-zone -->
				
				<%--  <div class="form-group clear">
					<label class="col-sm-2 control-label required-control">Mode Type:</label>
					<div class="col-sm-4">
						<span id="workflowId"> <label class="radio-inline"><form:radiobutton
									name="wdMode" value="P" path="modeType" id="workflowId" />Payment</label>
							<label class="radio-inline"><form:radiobutton name="wdMode"
									value="C" path="modeType" /><spring:message code="workflow.form.field.paymode.complaint"/></label>
						</span>
					</div>
					<div id="payModeId">
					<label class="col-sm-2 control-label required-control">Payment Mode:</label>
					<div class="col-sm-4" >
						<form:select path="wdMode" class="form-control" id="wdMode">
							<form:option value="0" >Select Mode</form:option>
							<form:option value="Y">Online</form:option>
							<form:option value="N">Offline</form:option>
							<form:option value="F">Free</form:option>
						</form:select>
					</div>
					</div> 
				</div>  --%>
				 <div class="form-group clear">
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
						<form:select path="complaintType" class="form-control" id="complaint_type_id">
							<form:option value="0" >Select Complaint Type</form:option>
							<c:forEach items="${complaintList}" var="objArray">
								<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
							</c:forEach>
						</form:select>
					</div>
					</div>
					<div id="complaint_subtype_Id">
					<label class="col-sm-2 control-label required-control">Complaint SubType:</label>
					<div class="col-sm-4" >
						<form:select path="complaintSubType" class="form-control" id="complaintSubType_id">
							<form:option value="0" >Select Mode</form:option>
						</form:select>
					</div>
					</div>
				</div>
				<div class="text-center padding-10">
					<input name="" type="button" value="Add" class="btn btn-success"
						id="workFlowadd" /> <input name="" type="button" value="Edit"
						class="btn btn-default" id="workFlowedit" /> <input name=""
						type="button" value="Delete" class="btn btn-danger"
						id="workFlowDelete" />
						<button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'">Back</button>
				</div>
				<div id="addMode" style="display: none">
					<c:set var="d" value="0" scope="page" />
					<div class="table-responsive">
						<table class="table table-hover table-striped table-bordered"
							id="customFields">
							<tbody>
								<tr>
									<th><spring:message code="workflow.form.table.column.srno" /></th>
									<th><spring:message
											code="workflow.form.table.column.eventname" /></th>
									<th><spring:message code="workflow.form.table.column.role" /></th>
									<%-- <th><spring:message code="workflow.form.table.column.approver" /></th>	 --%>
									<th><spring:message
											code="workflow.form.table.column.required" /></th>
									<%-- <th><spring:message code="workflow.form.table.column.dependon" /></th> --%>
									<th><spring:message
											code="workflow.form.table.column.conditionalStep" /></th>
									<th><spring:message code="workflow.form.table.column.sla" /></th>
									<th><spring:message
											code="workflow.form.table.column.addremove" /></th>
								</tr>
								<tr class="appendableClass">
									<td id="srNoId_${d}">1</td>
									<td><form:select
											path="workFlowEventDTO[${d}].weServiceEvent"
											class="form-control width120 eventNameId_${d}"
											id="eventNameId_${d}">
											<option value="0"><spring:message
													code="workflow.form.select.event" /></option>
										</form:select></td>
									<td><form:select path="workFlowEventDTO[${d}].roleId"
											multiple="multiple" class="form-control width163 roleId_${d}"
											id="roleId_${d}">
											<option value="0"><spring:message
													code="workflow.form.select.role" /></option>
										</form:select></td>
									<td><form:select
											path="workFlowEventDTO[${d}].weIsrequire"
											class="form-control width120 requiredId_${d}"
											id="requiredId_${d}">
											<form:option value="0">
												<spring:message code="workflow.form.select.required" />
											</form:option>
											<c:forEach items="${requiredType}" var="objEntry">
												<form:option value="${objEntry.lookUpCode}">${objEntry.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>


									<td><form:input
											path="workFlowEventDTO[${d}].weCondiatinalFalseNextStep"
											maxlength="3"
											class="form-control year-width1 conditionalNextstep_${d}"
											id="conditionalNextstep_${d}"   /></td>

									<td><form:input path="workFlowEventDTO[${d}].weSla"
											maxlength="3" class="form-control year-width1 slaId_${d}"
											id="slaId_${d}" /></td>
									<td><a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="addCF btn btn-success btn-sm"
										data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="remCF btn btn-danger btn-sm"
										data-original-title="Delete"><i class="fa fa-trash"></i></a></td>
									<td style="display: none"><form:hidden
											path="workFlowEventDTO[${d}].weId" maxlength="3"
											class="form-control weId_${d}" id="weId_${d}" value="0"
											name="workFlowEventDTO[${d}].weId" /></td>
									<td style="display: none"><form:hidden
											path="workFlowEventDTO[${d}].weStepNo" maxlength="3"
											class="form-control weStepNo_${d}" id="weStepNo_${d}"
											value="${d}" name="workFlowEventDTO[${d}].weStepNo" /></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="text-center padding-top-10">
						<input name="" type="button" value="Submit" class="btn btn-success " id="workFlowSubmit" /> 
						<input type="reset" class="btn btn-default" id="resetId" />
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>
</div>