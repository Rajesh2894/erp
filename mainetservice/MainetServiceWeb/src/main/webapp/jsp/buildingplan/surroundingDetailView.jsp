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
	var returnData = __doAjaxRequest('NewTCPLicenseForm.html?saveSurroundingDetail',
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
						<spring:message code="" text="Surroundings" />
					</h4>
					<div class="form-group">
						<div class="overflow margin-top-10">
							<div class="table-responsive">
								<c:set var="d" value="0" scope="page" />
								<table class="table table-striped table-bordered table-wrapper"
									id="surroundingDetail">
									<thead>

										<tr>
											<th class="text-center"><spring:message code=""
													text="Pocket Name" /></th>
											<th class="text-center"><spring:message code=""
													text="North" /></th>
											<th class="text-center"><spring:message code=""
													text="South" /></th>
											<th class="text-center"><spring:message code=""
													text="East" /></th>
											<th class="text-center"><spring:message code=""
													text="West" /></th>
											<th class="text-center"><spring:message code=""
													text="Decision" /></th>
											<th class="text-center"><spring:message code=""
													text="Remark" /></th>

										</tr>
									</thead>
									<tbody>
                                            <c:if
											test="${not empty command.licenseApplicationLandSurroundingsDtoList}">
											<c:forEach var="feeMasterDto"
												items="${command.licenseApplicationLandSurroundingsDtoList}"
												varStatus="status">
										<tr class="appendableClass">

											<td class="text-center"><form:input name="" type="text"
													class="form-control"  disabled="true" 
													path="licenseApplicationLandSurroundingsDtoList[${d}].pocketName"
													id="pocketName${d}" /></td>
											<td class="text-center"><form:input name="" type="text"
													class="form-control"  disabled="true" 
													path="licenseApplicationLandSurroundingsDtoList[${d}].north"
													id="north${d}" /></td>
											<td class="text-center"><form:input name="" type="text"
													class="form-control"  disabled="true" 
													path="licenseApplicationLandSurroundingsDtoList[${d}].south"
													id="south${d}" /></td>
											<td class="text-center"><form:input name="" type="text"
													class="form-control"  disabled="true" 
													path="licenseApplicationLandSurroundingsDtoList[${d}].east"
													id="east${d}" /></td>
											<td class="text-center"><form:input name="" type="text"
													class="form-control"  disabled="true" 
													path="licenseApplicationLandSurroundingsDtoList[${d}].west"
													id="west${d}" /></td>
											<td><form:select
													path="licenseApplicationLandSurroundingsDtoList[${d}].decision"
													id="decision${d}" cssClass="form-control">

													<c:set var="baseLookupCode" value="DEC" />
													<form:option value="">
														<spring:message code="siteinspection.vldn.answer" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpDesc}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input
													path="licenseApplicationLandSurroundingsDtoList[${d}].resolutionComments"
													class="form-control hasNumber"
													id="resolutionComments${d}" minlength="20"
													maxlength="10" /></td>

										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
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