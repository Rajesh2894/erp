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
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="marriageDTO.marId" id="marId" />
		<form:hidden path="payableFlag" id="payableFlag" />
		<form:hidden path="marriageDTO.appointmentDTO.pageNo" id="payableFlag"
			value="0" />
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
								readonly="${command.modeType eq 'V' && not empty command.marriageDTO.appointmentDTO.appointmentId}"
								datePath="marriageDTO.appointmentDTO.appointmentDate"></apptags:date>




							<label class="col-sm-2 control-label required-control "
								for="appointmentTime"> <spring:message
									code="mrm.appointment.appointmentTime" />
							</label>
							<div class="col-sm-4">
								<form:input path="marriageDTO.appointmentDTO.appointmentTime"
									disabled="${command.modeType eq 'V' && not empty command.marriageDTO.appointmentDTO.appointmentId}"
									class="form-control datetimepicker3 mandColorClass"
									maxlength="10" id="appointmentTime" />
							</div>
						</div>
						<div class="form-group"></div>


					</div>
				</div>

			</div>
		</div>

		<div class="text-center">
					<button type="button" class="btn btn-danger" name="button"
						id="Back" value="Back" onclick="showTab('#witnessDet')">
						<spring:message code="mrm.button.back" />
					</button>
				</div>

	</form:form>
</div>

<script>
	var appntDate = $("#appointmentTabForm input[id=appointmentDate]").val();
	if (appntDate) {
		$("#appointmentTabForm input[id=appointmentDate]").val(
				appntDate.split(' ')[0]);
	}
</script>