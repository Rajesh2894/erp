<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/SmsAndEmail/smsAndEmail.js"></script>
<script src="js/mainet/validation.js"></script>
<script>
$(document).ready(function(){
$('#form2 textarea, #form2 input').on('click',function(){
   $('#form2 textarea, #form2 input').removeClass('active');
    $(this).addClass('active');
});

	$("#eventMapSelectedId").change(function() {
	var cursorPos = $('#form2 input.active, #form2 textarea.active').prop('selectionStart');
	var v = $('#form2 input.active, #form2 textarea.active').val();
    var textBefore = v.substring(0,cursorPos);
    var textAfter  = v.substring( cursorPos, v.length );
    $('#form2 input.active, #form2 textarea.active').val(textBefore+ ' $' + $(this).val()+ '$ '  +textAfter);
});

});
/* var selectedServiceId = $('#selectedServiceId').val();
if(selectedServiceId!=""){  
	   alert("hi"+selectedServiceId);
	   $(".serviceList option[value=" + serviceid + "]").prop('selected',true);
$('#serviceId').val(selectedServiceId);
} 
 */
 
$(function() {
		$("#form2").validate();
});
$('select.form-control.mandColorClass').change(function(){
	    var check = $(this).val();
	    var validMsg =$(this).attr("data-msg-required");
	    var optID=$(this).attr('id');
	    var fildID="#"+optID+"_error_msg";
	    if(check == '' || check == '0'){
	   		 $(this).parent().switchClass("has-success","has-error");
			     $(this).addClass("shake animated");
			     $(fildID).addClass('error');
			     $(fildID).css('display','block');
			     $(fildID).html(validMsg);
		}else
	    {$(this).parent().switchClass("has-error","has-success");
	    $(this).removeClass("shake animated");
	    $(fildID).css('display','none');}
});
</script>
<style>
.widget-content.padding{overflow: visible;}
</style>

<apptags:breadcrumb></apptags:breadcrumb>	
<div class="content"> 
	<div class="widget ">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="eip.smsAndEmail" text="SMS And Email" /></strong>
			</h2>
		</div>


<div class="widget-content padding">
	<jsp:include page="/jsp/tiles/validationerror.jsp" />
	<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
	<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<span id="errorId"></span>
    </div>
	<form:form action="SMSAndEmail.html" method="post" class="form-horizontal" id="form2">

		<input type="hidden" value="A" name="selectedMode" id="mode">
		<input type="hidden" id="AlertType" value="${command.alertType}">
		<input type="hidden" id="deptIDAdd" value="${command.entity.dpDeptid.dpDeptid}">
		<input type="hidden" id="addModeSelect" value="${command.selectedMode}">
		<input type="hidden" value="${command.entity.serviceId.serviceId}" id="selectedServiceId"/>
		<div class="form-group">
			<label class="col-sm-2 control-label required-control" for="deptId"><spring:message code="rti.depName" text="Department Name"/> </label>
			<div class="col-sm-4">
				<c:set var="DeptNameMsg" value="${command.getAppSession().getMessage('eip.admin.sms.deptname') }"></c:set>
				<form:select path="entity.dpDeptid.dpDeptid"
					onchange="fn_setService(this,'serviceList')"
					cssClass="form-control mandColorClass chosen-select-no-results" id="deptId"
					data-rule-required="true" data-msg-required="${DeptNameMsg}">
					<form:option value="0">
						<spring:message code="sms.dept" text="Select" />
					</form:option>
					<form:options items="${command.departmentList}" />
				</form:select>
				<label id="deptId_error_msg" style="display:none; border:none;"></label>
			</div>
			<label class="col-sm-2 control-label required-control" for="serviceId"><spring:message
					code="rti.serviceName" text="Service Name"/></label>
			<div class="col-sm-4">
				<c:set var="ServiceNameMsg" value="${command.getAppSession().getMessage('eip.admin.sms.servicename') }"></c:set>
				<form:select path="entity.serviceId.serviceId"
					cssClass="form-control mediaType serviceList mandColorClass chosen-select-no-results" id="serviceId"
					data-rule-required="true" data-msg-required="${ServiceNameMsg}">
					<form:option value="0"><spring:message code="sms.service" /></form:option>
				</form:select>
				<label id="serviceId_error_msg" style="display:none; border:none;"></label>
			</div>
		</div>
		
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="eventId"> <spring:message code="sms.event" text="Event"/> </label>
		<div class="col-sm-4"> 
		<c:set var="EventMsg" value="${command.getAppSession().getMessage('eip.admin.sms.event') }"></c:set>
		<form:select path="entity.smfid.smfid" cssClass="form-control mandColorClass chosen-select-no-results" id="eventId"
			data-rule-required="true" data-msg-required="${EventMsg}">
			<form:option value="0"><spring:message code="admin.Select" text="Select"/></form:option>
			<c:forEach items="${command.eventsList}" var="eventArray" >
			<form:option value="${eventArray[0]}" label="[ ${eventArray[1]} ]>>[ ${eventArray[4]} ] "></form:option>
			</c:forEach>
		</form:select>
		<label id="eventId_error_msg" style="display:none; border:none;"></label>
	</div>
		 <label class="col-sm-2 control-label"><spring:message code="rti.alertSub" text="Alert Type"/><span class="mand">*</span></label>
		<div class="col-sm-4 margin-top-5">
		  <label class="radio-inline"><form:radiobutton path="entity.alertType" value="S" id="alertType"/><spring:message code="rti.bysms" text="Sms"/></label>
		  <label class="radio-inline"><form:radiobutton path="entity.alertType" value="E" id="alertType"/> <spring:message code="rti.bymail" text="Email"/></label>
		  <label class="radio-inline"><form:radiobutton path="entity.alertType" value="B" id="alertType"/><spring:message code="rti.both" text="Both"/></label>
	   </div> 
<%-- 	 <label class="col-sm-2 control-label"><spring:message code="sms.transactionlType" /><span class="mand">*</span></label>
		<div class="col-sm-4 margin-top-5">				
		  <label class="radio-inline"><form:radiobutton path="entity.tranOrNonTran" value="Y" name="transactionType" id="transactionType"/> <spring:message code="sms.transactional" /></label>
		  <label class="radio-inline"><form:radiobutton path="entity.tranOrNonTran" value="N" name="transactionType" id="transactionType"/><spring:message code="sms.nonTransactional" /></label>
	   </div> --%>
		</div>
		
	 <div class="form-group">
		<label class="col-sm-2 control-label required-control" for="messageType"><spring:message code="eip.typeofmsg" /></label> 
		<div class="col-sm-4">
		<c:set var="TemplateMsg" value="${command.getAppSession().getMessage('eip.admin.sms.templateType') }"></c:set>
		<form:select path="entity.smsAndmailTemplate.messageType" cssClass="form-control mandColorClass chosen-select-no-results" id="messageType"
		data-rule-required="true" data-msg-required="${TemplateMsg}">
		<form:option value=""><spring:message code="admin.Select" text="Select"/></form:option>
		<c:forEach items="${command.messageLookUp}" var="type">
		<form:option value="${type.lookUpCode}">${type.lookUpDesc}</form:option>
		</c:forEach>
		</form:select>
		<label id="messageType_error_msg" style="display:none; border:none;"></label>
		</div>
		<apptags:input labelCode="eip.templateIdEng"
		cssClass="hasNumber form-control" path="entity.smsAndmailTemplate.extTemplate"
		isMandatory="" maxlegnth="19"></apptags:input>
			<%-- <apptags:lookupField items="${command.getLevelData('SMT')}" path="entity.smsAndmailTemplate.messageType" selectOptionLabelCode="Select"
			 hasId="true" isMandatory="true" cssClass="form-control chosen-select-no-results" /> --%>
		
	</div>
	
	<div class="form-group">
	<apptags:input labelCode="eip.templateIdReg"
     cssClass="hasNumber form-control" path="entity.smsAndmailTemplate.extTemplateReg"
	 isMandatory="" maxlegnth="19"></apptags:input>
	</div>
	
	
	<div class="row">
		<div class="col-sm-8">
		
		<!--@@@@@@@@@@@@@@@@@@@@@@@@@ sms template@@@@@@@@@@@@@@@@@@@@@@@@@ -->
		<div id="smsTemplate">
			<h4><spring:message code="sms.Template" text="SMS Template" /></h4>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="output"><spring:message
						code="SMS.Body" /><span class="mand">*</span></label>
				<div class="col-sm-10">
				<c:set var="smsbodyEnglish" value="${command.getAppSession().getMessage('eip.admin.eipsmsemail.smsbodyEnglish') }" />
				<form:textarea id="output" path="entity.smsAndmailTemplate.smsBody" cssClass="form-control mandColorClass attadd"
				data-rule-required="true" data-msg-required="${smsbodyEnglish}" />
				</div>

			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.smsBodyReg"><span class="margin-right-5"><spring:message
						code="SMS.Body" text="SMSBodyReg" /></span> <spring:message code="Regional" text="(Hindi)" /> <span class="mand">*</span></label>
				<div class="col-sm-10">
				<c:set var="smsbodyRegional" value="${command.getAppSession().getMessage('eip.admin.eipsmsemail.smsbodyRegional') }" />
					<form:textarea path="entity.smsAndmailTemplate.smsBodyReg"
						cssClass="form-control mandColorClass attadd"
						data-rule-required="true" data-msg-required="${smsbodyRegional}" />
				</div>


			</div>
		</div>
		<!--@@@@@@@@@@@@@@@@@@@@@@@@@ email template@@@@@@@@@@@@@@@@@@@@@@@@@ -->
		<div id="emailTemplate" >
			<h4><spring:message code="Email.Template" text="Email Template" /></h4>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailSub"><spring:message
						code="Email.Subject" /> <span class="mand">*</span></label>
				<div class="col-sm-10">	
				<c:set var="MailSubjectEnglish" value="${command.getAppSession().getMessage('eip.admin.eipsmsemail.MailSubjectEnglish') }" />	
				<form:input path="entity.smsAndmailTemplate.mailSub"
					cssClass="form-control mandColorClass attadd" 
					data-rule-required="true" data-msg-required="${MailSubjectEnglish}"/>
				</div>	

			</div>
			<div class="form-group">
			<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailSubReg"><span class="margin-right-5"><spring:message code="Email.Subject"
					text="EmailSubjectReg" /></span> <spring:message code="Regional" text="(Hindi)" /><span class="mand">*</span></label>
			<div class="col-sm-10">		
			<c:set var="MailSubjectRegional" value="${command.getAppSession().getMessage('eip.admin.eipsmsemail.MailSubjectRegional') }" />	
			<form:input path="entity.smsAndmailTemplate.mailSubReg"
				cssClass="form-control mandColorClass attadd"
				data-rule-required="true" data-msg-required="${MailSubjectRegional}" />
			</div>	
			</div>	
			<div class="form-group">
			 <label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailBody"><spring:message
					code="Email.Body" /><span class="mand">*</span></label>
			<div class="col-sm-10">		
			<c:set var="MailbodyEnglish" value="${command.getAppSession().getMessage('eip.admin.eipsmsemail.MailbodyEnglish') }" />	
			<form:textarea path="entity.smsAndmailTemplate.mailBody"
				cssClass="form-control mandColorClass attadd"
				onkeyup="javascript:RemoveSpecialChar(this)" 
				data-rule-required="true" data-msg-required="${MailbodyEnglish}"/>
			</div>	
			</div>	
			<div class="form-group">
			<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailBodyReg"><span class="margin-right-5"><spring:message code="Email.Body"
					text="EmailsBodyReg" /></span> <spring:message code="Regional" text="(Hindi)" /><span class="mand">*</span></label>
			<div class="col-sm-10">		
			<c:set var="MailbodyRegional" value="${command.getAppSession().getMessage('eip.admin.eipsmsemail.MailbodyRegional') }" />	
			<form:textarea path="entity.smsAndmailTemplate.mailBodyReg"
				cssClass="form-control mandColorClass attadd"
				onkeyup="javascript:RemoveSpecialChar(this)"
				data-rule-required="true" data-msg-required="${MailbodyRegional}" />
				</div>
			</div>
		</div>
		
		</div>  
		
		<div class="col-sm-4 attri">
			<div class="form-group">
			  <label class="col-sm-4 control-label" for="eventMapSelectedId"><spring:message code="sms.attributes" text="Attributes" /></label>
			  <div class="col-xs-8">
				<form:select id="eventMapSelectedId" path="" class="form-control min-height-350" multiple="multiple">
				<c:forEach items="${command.attributeList}" var="attribute">
				<form:option value="${attribute}">${attribute}</form:option>
				</c:forEach>
				</form:select>
				<input type="hidden" name="_eventMapSelectedId" value="1">
			  </div>
			</div>
		 </div>
	</div>
		 <div class="text-center">
			 <input type="submit"class="btn btn-success" onclick="return saveForm(this);"value="<spring:message code="saveBtn" />" /> 
			 <input type="reset"class="btn btn-warning"  onclick="resetFormData()" value="<spring:message code="eip.commons.resetBT"/>">
			  <input type="button" class="btn btn-danger"  onclick="window.location.href='SMSAndEmail.html?complete'" value="<spring:message code="bckBtn" text="Back"/>"> 
		</div>
	</form:form>
</div>
</div>
</div>
