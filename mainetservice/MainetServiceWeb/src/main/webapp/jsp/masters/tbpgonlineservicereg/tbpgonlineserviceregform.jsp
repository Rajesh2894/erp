
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function saveData1(obj){
if($("#tbPgOnlineservicereg_isdeleted").is(':checked')){
		
		$("#tbPgOnlineservicereg_isdeleted").val('N');
		
	}
	else{
		$("#tbPgOnlineservicereg_isdeleted").val('Y');
		
	}

if($("#tbPgOnlineservicereg_serviceId").val() ==0){
	alert($("#tbPgOnlineservicereg_serviceId").val());
	var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="32"/></div><ul><li>please enter all the fields</li></ul>';
	$("#errorDivCustBankMas").html(errMsg);
	$("#errorDivCustBankMas").show();
	return false;
} 
if($("#tbPgOnlineservicereg_orgid").val() ==0 || $("#tbPgOnlineservicereg_orgid").val()==""){
	alert($("#tbPgOnlineservicereg_orgid").val());
	var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="32"/></div><ul><li>please enter org id</li></ul>';
	$("#errorDivCustBankMas").html(errMsg);
	return false;
}
if($("#tbPgOnlineservicereg_mmMrcntId").val() == '' || $("#tbPgOnlineservicereg_mmMrcntId").val() == undefined){
	alert($("#tbPgOnlineservicereg_mmMrcntId").val());
	var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="32"/></div><ul><li>please enter Merchant Name</li></ul>';
	$("#errorDivCustBankMas").html(errMsg);
	$("#errorDivCustBankMas").show();
	return false;
}
if($("#tbPgOnlineservicereg_smServiceId").val() ==''|| $("#tbPgOnlineservicereg_smServiceId").val()==undefined){
	alert($("#tbPgOnlineservicereg_smServiceId").val());
	var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="32"/></div><ul><li>please enter Service NAme</li></ul>';
	$("#errorDivCustBankMas").html(errMsg);
	$("#errorDivCustBankMas").show();
	return false;
}
var	formName =	findClosestElementId(obj, 'form');
var theForm	=	'#'+formName;
var requestData = {};
requestData = __serializeForm(theForm);
var url	=	"tbPgOnlineservicereg/create";
var redirectUrl='tbPgOnlineservicereg';

$.ajax({
	url : url,
	data : requestData,
	success : function(response) {
		 
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'OK';
		var save="Saved Successfully!";
		 message	+='<p>'+ save+'</p>';
		 message	+='<div class=\'btn_fld clear padding_10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
		' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+	
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	},
	error : function(xhr, ajaxOptions, thrownError) {
		var errorList = [];
		errorList.push(getLocalMessage("admin.login.internal.server.error"));
		showError(errorList);
	}
});


return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}
</script>
 
			<div id="heading_wrapper">
<div id="content">
	<div class="form-div">
	
		<c:url value="${saveAction}" var="url_form_submit" />
		
		<div class="error-div" style="display:none;" id="errorDivCustBankMas"></div>
	
		<form:form method="post" action="${url_form_submit}" name="hormaster"
			id="hormaster" class="form">
			
			<c:if test="${mode == 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				
			
			</c:if>
				 <div class="form-elements">
		      <div class="element">
					<label for="tbPgOnlineservicereg_serviceId" class="col-sm-2 control-label"><spring:message code="tbPgOnlineservicereg.serviceId"/></label>
					<form:input id="tbPgOnlineservicereg_serviceId" path="serviceId" class="form-control mandClassColor hasNumber" maxLength="500"  /><span class="mand">*</span>
				</div>
				</div> 
			<div class="form-elements">
			<div class="element">
			<label for="tbPgOnlineservicereg_mmMrcntId" class="col-sm-2 control-label"><spring:message code="tbPgOnlineservicereg.mmMrcntId"/></label>
					<form:select id="tbPgOnlineservicereg.mmMrcntId" path="mmMrcntId" cssClass="form-control mandClassColor">
						<form:option value="0">Select Merchant</form:option>
						<c:forEach items="${merchantlist}" var="merchant">
						<form:option value="${merchant.mrcntId}">${merchant.mrcntCode}</form:option>
					    </c:forEach>
					</form:select><span class="mand">*</span>
					</div>
					</div>
						<div class="form-elements">
					<div class="element">
					<label for="tbPgOnlineservicereg_orgid" class="col-sm-2 control-label"><spring:message code="tbPgOnlineservicereg.orgid"/></label>
					<form:input id="tbPgOnlineservicereg_orgid" path="orgid" class="form-control mandClassColor hasNumber" maxLength="500"  /><span class="mand">*</span>
				</div>
					</div>
					<div class="form-elements">
			<div class="element">
			<label for="tbPgOnlineservicereg_smServiceId" class="col-sm-2 control-label"><spring:message code="tbPgOnlineservicereg.smServiceId"/></label>
					<form:select  path="smServiceId" cssClass="form-control mandClassColor">
						<form:option value="0">Select Services</form:option>
						<c:forEach items="${servicelist}" var="service">
						<form:option value="${service.smServiceId}">${service.smServiceName}</form:option>
					    </c:forEach>
					</form:select><span class="mand">*</span>
					</div>
					</div>
					<div class="form-elements">
				<div class="element">
					<label for="tbPgOnlineservicereg_serUrl" class="col-sm-2 control-label"><spring:message code="tbPgOnlineservicereg.serUrl"/></label>
					<form:input id="tbPgOnlineservicereg_serUrl" path="serUrl" class="form-control mandClassColor hasNumber" maxLength="500"  /><span class="mand">*</span>
				</div>
				</div>
				<div class="form-elements">
			<div class="element">
			<label for="tbPgOnlineservicereg_isdeleted" class="col-sm-2 control-label"><spring:message code="tbPgOnlineservicereg.isdeleted"/></label>
			<form:checkbox path="isdeleted"  value="N" id="tbPgOnlineservicereg_isdeleted"/>
			</div> 
			</div>
				<div class="btn_fld float-right clear">
			
				<input type="BUTTON" id="back" value="Back" class="css_btn"  onclick="window.location.href='TbPgOnlineservicereg.html'"/>
				<input type="BUTTON" class="css_btn" value="<spring:message code="save"/>" onclick="saveData1(this)">
			</div>
			</form:form>
			</div>
			</div>
			</div>