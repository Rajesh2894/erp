<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/file-upload.js"></script>

<script>
function searchDoc(formName, actionParam) {
	
	if ($("#fileName").val() != '') {
		var theForm = '#' + formName;
		var url = $(theForm).attr('action');
		url += '?' + actionParam;

		var requestData = {};
		requestData["fileName"] = $("#fileName").val();

		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');

		$(".document").html(ajaxResponse);
		$(".document").show();
	} else {
		$(".document").html('');
		$(".document").hide();
	}
}

function saveForm(element)
{
	
	return saveOrUpdateForm(element, "Payslip Uploaded Successfully", 'PaySlipGeneration.html', 'save');
}
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Upload Payslip"/></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label">
				<span><spring:message code="MandatoryMsg" text="MandatoryMsg" /></span>
			</div>
			<form:form method="post" action="PaySlipGeneration.html"
				name="frmPaySlipGeneration" id="frmPaySlipGeneration"
				class="form-horizontal">
				<div class="error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				
				<div class="form-group">
                <label class="col-sm-2 control-label required-control" for="pNameEn"><spring:message code="payslip.no" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="fileName" hasId="true" maxlegnth="100" cssClass="form-control mandClassColor hasNumber subsize"
					isDisabled="" /></div>
				<input type="button" class="btn btn-success" onclick="return searchDoc('frmPaySlipGeneration','getPaySlip');" value="<spring:message code="" text="Search"/>" />
            	 </div>
             
				<div class="document">
				<%-- <form:hidden path="" id="documentId"/> --%>
				
				</div>
				<div class="form-group">
							
							<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="attPath" labelCode="" hasId="true"
								isMandatory="true" fileSize="CHECK_COMMOM_MAX_SIZE"
								currentCount="0" showFileNameHTMLId="true" folderName="0"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC" />

							<p class="help-block text-small">
								<spring:message code="eip.announce.fileUploadText20"
									text="Upload Upto 20 MB" />
							</p>
						
						</div>
					</div>		

				<div class="text-center padding-top-10">
					<input type="button" class="btn btn-success" onclick="return saveForm(this);" value="<spring:message code="eip.admin.helpDocs.save"/>" /> 
					<apptags:backButton url="CitizenHome.html" cssClass="btn btn-danger" />
				</div>

			</form:form>
		</div>
	</div>
</div>
