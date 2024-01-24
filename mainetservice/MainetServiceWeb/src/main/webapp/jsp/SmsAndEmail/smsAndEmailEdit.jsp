<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/SmsAndEmail/smsAndEmail.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script>
$(document).ready(function(){
$('#form3 textarea, #form3 input').on('click',function(){
   $('#form3 textarea, #form3 input').removeClass('active');
    $(this).addClass('active');
});

	$("#eventMapSelectedId").change(function() {
	var cursorPos = $('#form3 input.active, #form3 textarea.active').prop('selectionStart');
	var v = $('#form3 input.active, #form3 textarea.active').val();
    var textBefore = v.substring(0,cursorPos);
    var textAfter  = v.substring( cursorPos, v.length );
    $('#form3 input.active, #form3 textarea.active').val(textBefore+ ' $' + $(this).val()+ '$ '  +textAfter);
});
});

var selectedSmId = $('#selectedSmId').val();
if(selectedSmId==""){
	$("#smId").append($("<option></option>").attr("value","").text("Not Applicable"))
}else{
	$('#smId').val(selectedSmId);
}




</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content"> 
	<div class="widget ">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="common.smsAndEmail" text="SMS And Email" /></strong>
			</h2>
		</div>
		
<div class="widget-content padding">

	<form:form action="SMSAndEmail.html" method="post" class="form-horizontal" id="form3">
	<jsp:include page="/jsp/tiles/validationerror.jsp" />
	
	  <input type="hidden" value="E" name="selectedMode" id="mode">
	  <input type="hidden" id="AlertType" value="${command.alertType}"> 
	  <input type="hidden" id="deptIDEdit" value="${command.entity.dpDeptid.dpDeptid}"> 
	  <input type="hidden" id="editModeSelect" value="${command.selectedMode}" >
	  <input type="hidden" id="hidddenSeId" name="entity.seId" value="${command.entity.seId}" >
	
	  <div class="form-group">							
		<label class="col-sm-2 control-label" for="deptid"><spring:message code="rti.depName" text="Department Name"/> <span class="mand">*</span></label>
			<div class="col-sm-4"> 
			<form:select path="entity.dpDeptid.dpDeptid" onchange="fn_setService(this,'serviceList')" cssClass="form-control mandColorClass" disabled="true">
				<form:option value="0"><spring:message code="sms.dept" text="Select"/></form:option>
				<form:options items="${command.departmentList}" /></form:select>
			</div>	   
			
			<input type="hidden" value="${command.entity.serviceId.smServiceId}" id="selectedSmId"/>
			 <label class="col-sm-2 control-label" ><spring:message code="rti.serviceName" text="Service Name"/><span class="mand">*</span></label>
				<div class="col-sm-4">
				<select class="form-control mandColorClass serviceList" name="entity.serviceId.smServiceId" id="smId" disabled="disabled">
				</select>
				</div>

		</div>
							
	<div class="form-group">
			<label class="col-sm-2 control-label required-control"> <spring:message code="common.event" text="Event"/> </label>
				<div class="col-sm-4"> 
				<form:select path="entity.smfid.smfid" cssClass="form-control mandColorClass" id="eventId" disabled="true">
				<form:option value="0"><spring:message code="master.selectDropDwn" text="Select" /></form:option>
				<c:forEach items="${command.eventsList}" var="eventArray" >
				<form:option value="${eventArray[0]}" label="[ ${eventArray[4]} ]>>[ ${eventArray[3]} ] "></form:option>
				</c:forEach>
				</form:select>
		     	</div>
					<label class="col-sm-2 control-label"><spring:message code="rti.alertSub" text="Alert Type"/><span class="mand">*</span></label>
					    <div class="col-sm-4">
						<label class="radio-inline"><form:radiobutton path="entity.alertType" id="alertType" value="S" /> <spring:message code="rti.bysms" text="Sms" /></label>
						 <label class="radio-inline"><form:radiobutton path="entity.alertType" id="alertType" value="E" /> <spring:message code="rti.bymail" text="Email"/></label>
						 <label class="radio-inline"><form:radiobutton path="entity.alertType" id="alertType" value="B" /> <spring:message code="rti.both" text="Both"/></label> 
					</div>      	
<%-- 	<label class="col-sm-2 control-label"><spring:message code="sms.transactionlType" /></label>
		<div class="col-sm-4">
							<label class="radio-inline"><form:radiobutton path="entity.tranOrNonTran" value="Y" disabled="true"/> <spring:message code="sms.transactional" /></label>											
							<label class="radio-inline"><form:radiobutton path="entity.tranOrNonTran" value="N" disabled="true"/> <spring:message code="sms.nonTransactional" /></label>
		</div> --%>
	</div>
	
	
	<div class="form-group">
	 <label class="col-sm-2 control-label required-control" for="messageType"><spring:message code="eip.typeofmsg"/></label>
		<div class="col-sm-4">
		<form:select path="entity.smsAndmailTemplate.messageType" cssClass="form-control mandColorClass chosen-select-no-results" id="messageType">
		<form:option value=""><spring:message code="master.selectDropDwn" text="Select" /></form:option>
		<c:forEach items="${command.messageLookUp}" var="type">
		<form:option value="${type.lookUpCode}">${type.lookUpDesc}</form:option>
		</c:forEach>
		</form:select>
		</div>
		<apptags:input labelCode="eip.templateIdEng"
		cssClass="form-control" path="entity.smsAndmailTemplate.extTemplate"
		isMandatory="" maxlegnth="30"></apptags:input>
        <%-- <apptags:lookupField items="${command.getLevelData('SMT')}" path="entity.smsAndmailTemplate.messageType" selectOptionLabelCode="Select"
			 hasId="true" isMandatory="true" cssClass="form-control"/> --%>	
	</div>
	
	<div class="form-group">
	<apptags:input labelCode="eip.templateIdReg"
		cssClass="form-control" path="entity.smsAndmailTemplate.extTemplateReg"
		isMandatory="" maxlegnth="30"></apptags:input>
	</div>
								
	    <c:if test="${not empty command.entity.smsAndmailTemplate.messageType and command.entity.smsAndmailTemplate.messageType ne '0'}">
	    <div class="row">
<div class="col-sm-8">
				<!--@@@@@@@@@@@@@@@@@@@@@@@@@ sms template@@@@@@@@@@@@@@@@@@@@@@@@@ -->
		  <div id="smsTemplateEdit" class="">
			<h4><spring:message code="sms.Template" text="SMS Template" /></h4>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.smsBody"><spring:message code="SMS.Body" /><span class="mand">*</span></label>
				<div class="col-sm-10">
					<form:textarea path="entity.smsAndmailTemplate.smsBody" cssClass="form-control mandColorClass"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.smsBodyReg"><spring:message code="SMS.bodyReg" text="SMSBodyReg" /><span class="mand">*</span></label>
				<div class="col-sm-10">
					<form:textarea path="entity.smsAndmailTemplate.smsBodyReg" cssClass="form-control mandColorClass" />
				</div>
			</div>
		</div>
				<!--@@@@@@@@@@@@@@@@@@@@@@@@@ email template@@@@@@@@@@@@@@@@@@@@@@@@@ -->
		<div id="emailTemplateEdit" >
			<h4><spring:message code="Email.Template" text="Email Template" /></h4>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailSub"><spring:message code="Email.Subject" /> <span class="mand">*</span></label>
				<div class="col-sm-10">		
					<form:input path="entity.smsAndmailTemplate.mailSub" cssClass="form-control mandColorClass" />
				</div>
			</div>
			<div class="form-group">
			<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailSubReg"><spring:message code="Email.SubjectReg" text="EmailSubjectReg" /><span class="mand">*</span></label>
			<div class="col-sm-10">		
			 <form:input path="entity.smsAndmailTemplate.mailSubReg" cssClass="form-control mandColorClass"/>
			</div>	
			</div>	
			<div class="form-group">
			 <label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailBody"><spring:message code="Email.Body" /><span class="mand">*</span></label>
				<div class="col-sm-10">		
					<form:textarea path="entity.smsAndmailTemplate.mailBody" cssClass="form-control mandColorClass" onkeyup="javascript:RemoveSpecialChar(this)" />
				</div>	
			</div>	
			<div class="form-group">
			<label class="col-sm-2 control-label" for="entity.smsAndmailTemplate.mailBodyReg"><spring:message code="Email.BodyReg" text="EmailsBodyReg" /><span class="mand">*</span></label>
				<div class="col-sm-10">		
					<form:textarea path="entity.smsAndmailTemplate.mailBodyReg" cssClass="form-control mandColorClass" onkeyup="javascript:RemoveSpecialChar(this)" />
				</div>
			</div>
		</div>
		</div>
		<div class="col-sm-4 attri">
			<div class="form-group">
			  <label for="text-1493382342320" class="col-sm-4 control-label"><spring:message code="sms.email.attribute" text="Attributes"/></label>
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
					<input type="submit" class="btn btn-success btn-submit" id="submitButton" onclick="return saveForm(this);" value="<spring:message code="saveBtn" />" /> 
					<input type="reset" class="btn btn-warning" id="resetButton" value="<spring:message code="eip.commons.resetBT"/>">
			       <input type="button" class="btn btn-danger"  onclick="window.location.href='SMSAndEmail.html?complete'" value="<spring:message code="back.msg" text="Back" />" id="backBtn"> 
					<!-- </span> -->
				</div>
			 </c:if>
</form:form>
</div>
</div>
</div>