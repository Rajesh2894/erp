<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="js/buildingplan/newTCPLicenseForm.js"></script>
	
<script>
	function saveFeeChargesRemark(obj) {debugger;
	var errorList=[];
	if (errorList == ''){
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest('NewTCPLicenseForm.html?saveFeeCharges',
			'POST', requestData, false);
	showConfirmBoxFoLoiPayment(returnData);
	}else{
		displayErrorsOnPage(errorList);
	}
}

function showConfirmBoxFoLoiPayment(sucessMsg) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("bt.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ sucessMsg.command.message + '</h4>';
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

function showScrutinyLabel() {
	$.fancybox.close();
	backToApplicationForm($(
	'#appId').val(),$('#taskId').val(),'ApplicationAuthorization.html',$('#serviceId').val(),$('#workflowId').val());
}

</script>

<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantFeesForm" action="NewTCPLicenseForm.html"
					method="post" class="form-horizontal">
					<input type="hidden" id="taskId"
					value="${userSession.scrutinyCommonParamMap.taskId}" />
				<input type="hidden" id="workflowId"
					value="${userSession.scrutinyCommonParamMap.workflowId}" />
				<input type="hidden" id="serviceId"
					value="${userSession.scrutinyCommonParamMap.SM_SERVICE_ID}" />
				<input type="hidden" id="appId"
					value="${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID}" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<h4>
						<spring:message code="" text="Component wise Fee/Charges" />
					</h4>

					<div class="form-group">
						<div class="overflow margin-top-10">
							<div class="table-responsive">
								<table class="table table-bordered table-wrapper">
									<thead>
										<tr>
											<th class="text-left"><spring:message code="" text="Fee" /></th>
											<th class="text-left"><spring:message code=""
													text="Amount(in ₹)" /></th>
											<th class="text-left"><spring:message code=""
													text="Calculations" /></th>
											<th class="text-left"><spring:message code=""
													text="Decision" /></th>
											<th class="text-left"><spring:message code=""
													text="Remark" /></th>
										</tr>
									</thead>
									<tbody>
										<c:if
											test="${not empty command.feeListDto}">
											<c:forEach var="feeMasterDto"
												items="${command.feeListDto}"
												varStatus="status">
												<tr>
													<td><form:input
															path="feeListDto[${status.index}].taxCategory" value=""
															class="form-control" disabled="true"
															id="dinNumberId${status.index}" minlength="20"
															maxlength="10" /></td>
													<td class=""><form:input
															path="feeListDto[${status.index}].fees" value=""
															class="form-control text-right" disabled="true"
															id="dinNumberId${status.index}" minlength="20"
															maxlength="10" /></td>
													<td><form:input
															path="feeListDto[${status.index}].calculation"
															value="${not empty feeMasterDto.calculation ? feeMasterDto.calculation : ''}"
															class="form-control hasNumber" disabled="true"
															id="dinNumberId${status.index}" minlength="20"
															maxlength="10" /></td>

													<td>
													<form:select path="feeListDto[${status.index}].decision" id="decision${status.index}" cssClass="form-control">										
									
									               <c:set var="baseLookupCode" value="DEC" />
									                   <form:option value="">
									                  <spring:message code="siteinspection.vldn.answer" />
									                   </form:option>
									                    <c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											             <form:option value="${lookUp.lookUpDesc}"
												                  code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									                      </c:forEach>
								                        </form:select>   
											    	</td>
													<td><form:input
															path="feeListDto[${status.index}].resolutionComments"
															class="form-control hasNumber"
															id="resolutionComments${status.index}" minlength="20"
															maxlength="10" /></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>

					</div>

					<div class="text-center">

						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" onclick="saveFeeChargesRemark(this);"
							id="">
							<spring:message code="" text="Submit" />
						</button>

						<input type="button" class="btn btn-danger"
							onclick="backToApplicationForm(${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID},${userSession.scrutinyCommonParamMap.taskId},'ApplicationAuthorization.html',${userSession.scrutinyCommonParamMap.SM_SERVICE_ID},${userSession.scrutinyCommonParamMap.workflowId})"
							value="<spring:message code="back.button" />">

					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>