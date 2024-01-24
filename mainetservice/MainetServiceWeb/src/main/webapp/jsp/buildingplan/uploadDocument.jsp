<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/cfc/scrutiny.js"></script>

<script type="text/javascript">
function saveDocument(obj) {
	var errorList=[];
	if (errorList == ''){
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest('SiteAffected.html?saveDocument',
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

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">


			<h4>
				<spring:message code="professional.uploaded.document" text="Uploaded Document" />
			</h4>
		</div>
		<div class="widget-content padding">
			<form:form action="SiteAffected.html" class="form-horizontal form"
				name="frmSiteAff" id="frmSiteAff">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
                <form:hidden path="flag" id="flag" />
				<input type="hidden" id="taskId"
					value="${userSession.scrutinyCommonParamMap.taskId}" />
				<input type="hidden" id="workflowId"
					value="${userSession.scrutinyCommonParamMap.workflowId}" />
				<input type="hidden" id="serviceId"
					value="${userSession.scrutinyCommonParamMap.SM_SERVICE_ID}" />
				<input type="hidden" id="appId"
					value="${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID}" />
				<div class="container">
					<table class="table table-bordered table-striped" id="tbl1">
						<tr>
							<th width="20%" align="center"><spring:message
									code="sr.no" /></th>
							<th width="50%" align="center"><spring:message
									code="field.name" text="Field Name" /></th>
							<th width="30%" align="center"><spring:message
									code="file.upload" text="File Upload" /></th>
						</tr>
						<tr id="sUpload1">
							<td class="text-center">1</td>
							<td><c:if test="${command.flag eq 'D' }">
							<spring:message code="uploadDocumment.first" text="Applied land to be shown on the Development Plan (.pdf)" /></c:if>
							<c:if test="${command.flag eq 'J' }">
							<spring:message  text="Shajra cum demacration plan (.pdf)" /></c:if>
							</td>
							<td><apptags:formField fieldType="7" labelCode=""
									hasId="true" folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT"
									fieldPath="attachments[0].uploadedDocumentPath"
									fileSize="COMMOM_MAX_SIZE"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									isMandatory="false" showFileNameHTMLId="true" currentCount="0" />
							</td>
						</tr>
						<tr id="sUpload2">
							<td class="text-center">2</td>
							<td><c:if test="${command.flag eq 'D' }">
							<spring:message
									code="uploadDocumment.second" text="Applied land to be shown on the sectoral plan (.pdf)" /></c:if>
							<c:if test="${command.flag eq 'J' }">
							<spring:message  text="Site cum demacration plan (.dwg)" /></c:if>		
							</td>
							<td><apptags:formField fieldType="7" labelCode=""
									hasId="true" folderName="1" maxFileCount="CHECK_LIST_MAX_COUNT"
									fieldPath="attachments[1].uploadedDocumentPath"
									fileSize="COMMOM_MAX_SIZE"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									isMandatory="false" showFileNameHTMLId="true" currentCount="1" />
							</td>
						</tr>
						<tr id="sUpload3">
							<td class="text-center">3</td>
							<td><c:if test="${command.flag eq 'D' }">
									<spring:message code="uploadDocumment.third" text="Site plan vis-a-vis planning parameters (.pdf)" />
								</c:if> <c:if test="${command.flag eq 'J' }">
									<spring:message text="Site cum demacration plan (.pdf)" />
								</c:if></td>
							<td><apptags:formField fieldType="7" labelCode=""
									hasId="true" folderName="2" maxFileCount="CHECK_LIST_MAX_COUNT"
									fieldPath="attachments[2].uploadedDocumentPath"
									fileSize="COMMOM_MAX_SIZE"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									isMandatory="false" showFileNameHTMLId="true" currentCount="2" />
							</td>
						</tr>
					</table>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveDocument(this)">
						<spring:message code="professional.submit" />
					</button>
					<input type="button" class="btn btn-danger"
						onclick="backToApplicationForm(${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID},${userSession.scrutinyCommonParamMap.taskId},'ApplicationAuthorization.html',${userSession.scrutinyCommonParamMap.SM_SERVICE_ID},${userSession.scrutinyCommonParamMap.workflowId})"
						value="<spring:message code="back.button" />">
				</div>
			</form:form>
		</div>
	</div>
</div>

