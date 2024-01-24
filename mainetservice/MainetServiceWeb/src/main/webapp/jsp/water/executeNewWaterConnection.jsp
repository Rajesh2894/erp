
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
	function executeNewWaterConnectionProcess(element) {
		debugger;
		var checked = $('#execute').is(':checked');
		if (checked) {
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			var response = __doAjaxRequestForSave(
					'NewWaterConnectionExecutionForm.html?saveNewWater',
					'POST', requestData, false, '', element);

			//var message = response;
			showConfirmBoxForWat(response);

		} else {
			var errorList = [];
			errorList
					.push(getLocalMessage('water.select.execute.new.water.connection'));
			displayErrorsOnPage(errorList);
		}

	}
	function showConfirmBoxForWat(sucmessage) {
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = "Proceed";

		message += '<h4 class=\"text-center text-blue-2 padding-12\">'
				+ sucmessage + '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="proceed()"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		return false;
	}

	function proceed() {
		debugger;
		$.fancybox.close();
		var appId = '${command.applicationId}';
		var serviceId = '${command.serviceId}';
		var labelId = '${command.levelId}';

		loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',
				appId, labelId, serviceId);
		
		prepareTags();
	}
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.connect.execute" text="New Water Connection Execution" />
			</h2>
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
			<form:form action="NewWaterConnectionExecutionForm.html"
				class="form-horizontal form" name="frmNewWaterConnectionExecute"
				id="frmExecuteDisConnection">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<h4 class="margin-top-0">
					<spring:message code="water.form.appdetails"
						text="Application Details" />
				</h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:input cssClass="form-control" disabled="true"
							path="serviceName" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.application.number" text="Application Number" /></label>
					<div class="col-sm-4">
						<form:input cssClass="form-control" disabled="true"
							path="applicationId" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.applicnt.name" text="Applicant Name" /></label>
					<div class="col-sm-4">
						<form:input cssClass="form-control" disabled="true"
							path="applicanttName" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.application.date" text="Application Date" /> <spring:message
							code="water.application.date" /></label>
					<div class="col-sm-4">
						<form:input cssClass="form-control" disabled="true"
							path="applicationDate" />
					</div>
				</div>

				<h4>
					<spring:message code="" text="Execution Details" />
				</h4>
				<div class="form-group">
					<div class="col-sm-6">
						<label class="col-sm-6 checkbox-inline"><form:checkbox
								path="" value="Y" id="execute" /> <spring:message code="water.exeute.newconnection"
								text="Execute New Water Connection" /></label>
					</div>

					
				</div>

				

				<div class="text-center padding-top-10">

					<button type="button" class="btn btn-success"
						onclick="executeNewWaterConnectionProcess(this)">
						<spring:message code="water.btn.submit" />
					</button>
					<input type="button"
						onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${command.applicationId}','${command.levelId}','${command.serviceId}')"
						class="btn" value="Back">
				</div>

			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

