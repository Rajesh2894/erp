<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="js/mainet/file-upload.js"></script>
<script>

$(document).ready(function() {
	
	$.fancybox.close();
	if($("#documentId").val()!=''){
		$(".document").show();
		}
	else{
		$(".document").hide();
		}
	
	var dateFields = $('.date,.datepicker');
	dateFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d'
	});
});



	function getExcelToDownload(formName, actionParam) {
		
		if ($("#idfId").val() != 0) {
			var theForm = '#' + formName;
			var url = $(theForm).attr('action');
			url += '?' + actionParam;

			var requestData = {};
			requestData["scheme"] = $("#idfId").val();

			var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
					'html');

			$(".document").html(ajaxResponse);
			$(".document").show();
		} else {
			$(".document").html('');
			$(".document").hide();
		}
	}

	function saveForm()
	{
		if ($(".datepicker ").val() != "") {
			var theForm = '#' + "frmExcelImportExportForDashboard";
			var url = $(theForm).attr('action');
			url += '?' + "getData";

			var requestData = {};
			requestData["attachDt"] = $(".datepicker ").val();
			requestData["scheme"] = $("#idfId").val();

			var ajaxResponse = __doAjaxRequest(url, 'POST', requestData , false );
			
			if(ajaxResponse == "Failure"){
				showDecisionBox1('Data already exist do you want to override it','Document Saved Successfully','ExcelImportExportForDashboard.html','save');
			} else if(ajaxResponse == "success") {
				doFormActionForInsert('Document Saved Successfully','save','ExcelImportExportForDashboard.html');
			}
			else if(ajaxResponse == "null")
				{
				var errorList = [];
				errorList.push(getLocalMessage("Defined Class Not Found"));   
				showError(errorList);
				}
		}
	}

	function showDecisionBox1(warnMsg,successMsg, redirectUrl,action) {
		var no = getLocalMessage('eip.commons.no');
		
		var yes = getLocalMessage('eip.commons.yes');
		
		var message='';
		message	+='<p>'+ warnMsg+'</p>';
		message	+='<p >'+	
		'<div class="text-center padding-10">'	+				
				'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
				' onclick="doFormActionForInsert(\''+successMsg+'\',\''+action+'\',\''+redirectUrl+'\')"/>'+
				'<input class="btn btn-success" type=\'button\' value=\''+no+'\'  id=\'no\' '+ 
				' onclick="closeDecisionBox()"/>'+
				'</div>'+
				'</p>';	
				
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		showModalBox(errMsgDiv);
		return false;
	}

	function closeDecisionBox(errorMsgDiv){
		$.fancybox.close();
	}
	
	function doFormActionForInsert(successMessage, actionParam, successUrl)
	{
		var theForm = '#' + "frmExcelImportExportForDashboard";
		var url = $(theForm).attr('action');
		var requestData = {};
		
		requestData = __serializeForm(theForm);

		url	=	url+'?'+actionParam;
		
		
		var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'','');
		if ($.isPlainObject(returnData))
		{
			var message = returnData.command.message;
			
			var hasError = returnData.command.hasValidationError;
			
			if (!message) {
				message = successMessage;
			}
			
			if(message && !hasError)
				{
				   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
				   		
				   		showSaveResultBox(returnData, message, 'ServerError.html');
				   	
				   	else
				   		
				   		showSaveResultBox(returnData, message, successUrl);
				}
			else if(hasError)
			{
				$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
			}
			else
				return returnData;
			
		}
		else if (typeof(returnData) === "string")
		{
			$(formDivName).html(returnData);	
			/*prepareTags()*/
		}
		else 
		{
			alert("Invalid datatype received : " + returnData);
		}
		
		return false;
	}
	
	
</script>

<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i> <spring:message
				code="menu.home" /></a></li>
	<li><a href="javascript:void(0);"><spring:message
				code="menu.webportal" /></a></li>
	<li><a href="javascript:void(0);"><spring:message
				code="" text="ooo"  /></a></li>
	<li class="active"><spring:message code="" text="aaaa" /></li>
</ol>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Upload Template For Dashboard"/></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label">
				<span><spring:message code="MandatoryMsg" text="MandatoryMsg" /></span>
			</div>
			<form:form method="post" action="ExcelImportExportForDashboard.html"
				name="frmExcelImportExportForDashboard" id="frmExcelImportExportForDashboard"
				class="form-horizontal">
				<div class="error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="attachDocs.idfId"><spring:message
							code="" text="Scheme List :" /></label>
						
						<c:set var="baseLookupCode" value="SCM" />
							<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" changeHandler="getExcelToDownload('frmExcelImportExportForDashboard','getExcelDocument');"
							path="attachDocs.idfId" cssClass="form-control mandClassColor subsize"
							selectOptionLabelCode="-- Select Scheme --" hasId="true"
							isMandatory="true" />
							

					<label class="control-label col-sm-2 required-control" for="attachDocs.attDate"> <spring:message
							code="" text="Uploaded for Month :"/>
					</label>
					<div class="col-sm-4">
						<apptags:dateField cssClass="form-control empname subsize"
							datePath="attachDocs.attDate" fieldclass="datepicker"
							readonly="false"></apptags:dateField>
					</div>

				</div>
				<div class="document">
				<form:hidden path="attachDocs.attFname" id="documentId"/>
				</div>
				<div class="form-group">
							
							<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="attachDocs.attPath" labelCode="" hasId="true"
								isMandatory="true" fileSize="CHECK_COMMOM_MAX_SIZE"
								currentCount="0" showFileNameHTMLId="true" folderName="0"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION_DASHBOARD" />
								
									
							<p class="help-block text-small">
								<spring:message code="eip.mayor.image.size" />
							</p>
							<p class="help-block text-small">
								<spring:message code="eip.announce.fileUploadText20"
									text="Upload Upto 20 MB" />
							</p>
						
						</div>
					</div>		

				<div class="text-center padding-top-10">
						 <input type="button" class="btn btn-success" onclick="return saveForm();" value="<spring:message code="eip.admin.helpDocs.save"/>" /> 
					<apptags:backButton url="CitizenHome.html" cssClass="btn btn-danger" />
				</div>

			</form:form>
		</div>
	</div>
</div>