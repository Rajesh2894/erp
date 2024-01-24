<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/marriageReg.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<div class="widget-content padding" id="appointmentTabForm">
	<form:form action="MarriageRegistration.html" id="appointmentFormId"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDiv"></div>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="marriageDTO.marId" id="marId" />
		<form:hidden path="payableFlag" id="payableFlag" />
		<form:hidden path="marriageDTO.appointmentDTO.pageNo" id="payableFlag" value="0" />
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#AppointmentDet"><spring:message
								code="mrm.appointment.appointmentDtls" /></a>
					</h4>
				</div>

				<div id="AppointmentDet" class="panel-collapse collapse in">
					<div class="panel-body">

						<div class="form-group">
							<apptags:date fieldclass="datepicker"
								labelCode="mrm.appointment.appointmentDate" isMandatory="true"
								isDisabled="${(command.modeType eq 'V' || command.status eq 'APPROVED') && not empty command.marriageDTO.appointmentDTO.appointmentId}"
								datePath="marriageDTO.appointmentDTO.appointmentDate"></apptags:date>
							<label class="col-sm-2 control-label required-control "
								for="appointmentTime"> <spring:message
									code="mrm.appointment.appointmentTime" />
							</label>
							<div class="col-sm-4">
								<form:input path="marriageDTO.appointmentDTO.appointmentTime"
									disabled="${(command.modeType eq 'V' || command.status eq 'APPROVED') && not empty command.marriageDTO.appointmentDTO.appointmentId}"
									class="form-control datetimepicker3 mandColorClass"
									maxlength="10" id="appointmentTime" />
							</div>
						</div>
						<div class="form-group">
							<%-- <div class="col-sm-3">
								<apptags:input labelCode="mrm.appointment.volume"
									isDisabled="${command.modeType eq 'V'}"
									path="marriageDTO.appointmentDTO.volume" isMandatory="true"
									cssClass="form-control hasNumber"></apptags:input>
							</div> --%>

							<%-- <apptags:input labelCode="mrm.appointment.pageNo"
								isDisabled="${command.modeType eq 'V' && not empty command.marriageDTO.appointmentDTO.appointmentId}"
								path="marriageDTO.appointmentDTO.pageNo" isMandatory="true"
								cssClass="form-control hasNumber"></apptags:input> --%>

							<%-- <div class="col-sm-3">
								<apptags:input labelCode="mrm.appointment.serialNo"
									isDisabled="${command.modeType eq 'V'}"
									path="marriageDTO.appointmentDTO.serialNo" isMandatory="true"
									cssClass="form-control hasNumber"></apptags:input>
							</div> --%>
						</div>


					</div>
				</div>

			</div>
		</div>


		
		<c:choose>
			<c:when
				test='${(command.marriageDTO.status != null) && (command.conditionFlag eq "N") && (command.marriageDTO.status == "PENDING" || command.marriageDTO.status=="PROCESSING"  || command.marriageDTO.status=="SEND_BACK")}'>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<%--D#123056 <apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction> --%>
					<!--D#134156  -->
					
					<c:if test="${command.modeType ne 'V'}">
						 <div class="form-group padding-top-10" style="visibility: hidden">
								<label class="col-sm-2 control-label required-control"> 
								<spring:message code="cfc.service.status" text="Status" /></label> 
								<label class="radio-inline"> <form:radiobutton class="decision"  path="workflowActionDto.decision" value="APPROVED" checked="checked" />
									<spring:message code="eip.dept.auth.approve" /></label> 
									<label class="radio-inline"> <form:radiobutton class="decision"  path="workflowActionDto.decision" value="REJECTED"  /> 
									<spring:message code="eip.dept.auth.reject" /></label> 
								</label>
						</div> 
						
						<div class="col-sm-12">
							<form:checkbox
									path="" id="appApplicable"  class="margin-top-10 margin-left-10"  value="appointment"></form:checkbox>					
								<label class="" style="margin-left:34px;margin-top: 9px;margin-bottom: 15px;"><spring:message code="mrm.appointment.terms" text="click checkbox"/></label>		
	
						</div>
					</c:if>
					
					<div class="text-center">
						<div class="padding-top-10 text-center">
							<button type="button" class="btn btn-success proceed" id="submit"
								onclick="saveMRMInfoApprovalData(this)">
								<spring:message code="mrm.button.submit" text="Submit"></spring:message>
							</button>
	
							<button type="button" class="btn btn-danger" id="back"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code="mrm.button.back" text="Back"></spring:message>
							</button>
						</div>
					</div>

				</div>
			</c:when>
			<c:otherwise>
				<div class="text-center">
					<button type="button" class="btn btn-danger" name="button"
						id="Back" value="Back" onclick="showTab('#witnessDet')">
						<spring:message code="mrm.button.back" />
					</button>
				</div>
			</c:otherwise>
		</c:choose>

	</form:form>
</div>

<script>
	var appntDate = $("#appointmentTabForm input[id=appointmentDate]").val();
	if (appntDate) {
		$("#appointmentTabForm input[id=appointmentDate]").val(
				appntDate.split(' ')[0]);
	}
	//D#134156
	$(".proceed").prop("disabled",true);
	
	$("#appApplicable").change(function(){
		if($("#appApplicable").prop("checked") == true){
			$(".proceed").prop("disabled",false);
		}else{
			$(".proceed").prop("disabled",true);	
		}

	});
</script>