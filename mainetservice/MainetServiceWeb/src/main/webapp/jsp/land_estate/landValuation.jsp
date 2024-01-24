<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/cfc/scrutiny.js"></script>

<script type="text/javascript">

	function saveLandValuation(element) {
		var appId = '${command.lableValueDTO.applicationId}';
		var serviceId = '${command.serviceId}';
		var labelId = '${command.lableValueDTO.lableId}';
		var vendorId = $('#vendorId').val();
		var cost = $('#cost').val();
		if(vendorId == '' || vendorId == '0'){
			showErrormsgboxTitle(getLocalMessage("land.acq.val.vendor"));
		}else if(cost <=0){
			showErrormsgboxTitle(getLocalMessage("land.acq.val.acqCost"));
		}
		saveOrUpdateForm(element, "valuation saved successfully!", '', 'save');
		if ($("#error").val() != 'Y') {
			loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',
					appId, labelId, serviceId);
		}
	}
	//currently not using  integrate from LoiPayment Common Code
	function accountIntegrate(obj) {
		var appId = '${command.lableValueDTO.applicationId}';
		var serviceId = '${command.serviceId}';
		var labelId = '${command.lableValueDTO.lableId}';
		//saveOrUpdateForm(element, "Payable LOI Posting successfully!", '', 'accountIntegrate');
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData =__doAjaxRequest('LandAcquisition.html?accountIntegrate','POST',requestData, false); 
		showConfirmBoxFoLoiPayment(returnData);
	}

	function showConfirmBoxFoLoiPayment(sucessMsg) {
		
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = getLocalMessage("bt.proceed");

		message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg.command.message+'</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="showScrutinyLabel()"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		return false;
	}

	function showScrutinyLabel(){
		$.fancybox.close();
		loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${command.lableValueDTO.applicationId}','${command.lableValueDTO.lableId}','${command.serviceId}');
	}	
		
</script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.acq.title" text="Land Valuation" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->

			<form:form action="LandAcquisition.html" cssClass="form-horizontal"
				id="landValId">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="" text="Land Valuation" /> </a>
							</h4>
						</div>
						
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<label class="col-sm-2 control-label required-control">
										<spring:message code="" text="Vendors" />
									</label>
									<div class="col-sm-4">
										<form:select path="acquisitionDto.vendorId" cssClass="form-control chosen-select-no-results" disabled="${handler eq 'account'}"
											id="vendorId" data-rule-required="true">
											<form:option value="">
												<spring:message code="" text="Select" />
											</form:option>
											<c:forEach items="${command.vendorList}" var="vendor">
												<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<%-- <apptags:input labelCode="land.val.amt" 
										path="acquisitionDto.acqCost" isMandatory="true"></apptags:input> --%>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="land.val.amt" text="" /></label>

									<div class="col-sm-4">
										<form:input id="cost"
											cssClass="form-control mandColorClass text-right amount"
											onkeypress="return hasAmount(event, this, 13, 2)" disabled="${handler eq 'account'}"
											path="acquisitionDto.acqCost" data-rule-required="true"></form:input>
									</div>
								</div>
								<c:set var="appId" value="${command.lableValueDTO.applicationId}" />
								<c:set var="labelId" value="${command.lableValueDTO.lableId}" />
								<c:set var="serviceId" value="${command.serviceId}" />
								<form:hidden path="hasError" id="error" />

								
								<div class="text-center padding-top-10">
									<c:if test="${command.accountIntegrateBT}">
										<button type="button" class="btn btn-success btn-submit"
											onclick="accountIntegrate(this)">
											<spring:message code="bt.save" />
										</button>
									</c:if>

									<c:if test="${handler eq 'valuation'}">
										<button type="button" class="btn btn-success btn-submit"
											onclick="saveLandValuation(this)">
											<spring:message code="bt.save" />
										</button>
									</c:if>
									
									<input type="button"
										onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${appId}','${labelId}','${serviceId}')"
										class="btn" value="<spring:message code="bt.backBtn"/>">
								</div>



							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->