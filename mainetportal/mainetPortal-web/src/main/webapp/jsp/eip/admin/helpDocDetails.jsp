<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/file-upload.js"></script> 
<script src="js/mainet/validation.js"></script>


		<apptags:breadcrumb></apptags:breadcrumb>
<c:if test="${not command.hasValidationErrors()}">
</c:if>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>

<script>

  function saveForm(element)
{
	return saveOrUpdateForm(element, getLocalMessage("eip.admin.helpDocs.sucessmsg"), 'CommonHelpDocs.html', 'saveform');
}  


	$( document ).ready(function() {
		
	var deptcode = $('#deptCode').val();

	var serviceURl = $('#hideService').val();


		
		
		$("#serviceUrl").find('option:gt(0)').remove();
		
		if(deptcode != '0'){
			
			
			var postdata = 'deptcode=' + deptcode;
			
			var json = __doAjaxRequest('HelpDoc.html?serviceList',
					'POST', postdata, false, 'json');
		    
			var  optionsAsString='';
			for(var i = 0; i < json.length; i++) {
			    optionsAsString += "<option value='" + json[i].lookUpCode + "'>" + json[i].lookUpCode + "</option>";
			}
			$("#serviceUrl").append( optionsAsString );
			
			$('#serviceUrl option[value="' + serviceURl + '"]').prop('selected', true); 
		}
		
		
	});
	$(function() {
		$("#frmHelpDoc").validate();
	});
	
$('select.form-control.mandColorClass').on('blur',function(){
	     var check = $(this).val();
	     var validMsg =$(this).attr("data-msg-required");
	     if(check == '' || check == '0'){
	    		 $(this).parent().switchClass("has-success","has-error");
			     $(this).addClass("shake animated");
			     $('#error_msg').addClass('error');
			     $('#error_msg').css('display','block');
			     $('#error_msg').html(validMsg);}
	     else
	     {$(this).parent().switchClass("has-error","has-success");
	     $(this).removeClass("shake animated");
	     $('#error_msg').css('display','none');}
});
	</script>
<div class="content" >
                 <div class="widget">
		           <div class="widget-header">
                    <h2> <strong><spring:message code="eip.admin.helpDocs.breadcrumb" /></strong></h2>
                   </div>
               <div class="widget-content padding">
				<form:form action="HelpDoc.html"  name="frmHelpDoc" id="frmHelpDoc" method="post" class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<input type="hidden" value="A" name="modeOfType">
					<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="eip.form.name" text="Form Name" /></label>
							<div class="col-sm-4">
							<c:set var="HelpDocMsg" value="${command.getAppSession().getMessage('eip.admin.helpDoc.formName') }"></c:set>
							<form:select path="entity.moduleName"  cssClass=" form-control mandColorClass" id="deptCode"  data-rule-required="true" data-msg-required="${HelpDocMsg}">
							<form:option value="0" > <spring:message code="eip.select.form.name" text="Select Form Name"/></form:option> 
							<c:forEach items="${command.nodes}" var="look">
							  	<form:option value="${look.key}" label="${look.value}"/>
							</c:forEach>
						</form:select>
						<label id="error_msg" style="display:none; border:none;"></label>
						</div>
					</div>
					
                     <table class="table table-bordered">
                     
					<tr>
							<th><spring:message code="eip.language" text="Language" /></th>
							<th width="60%"><spring:message code="eip.document" text="Document" /></th></tr>
							<tr>
							<td><spring:message code="eip.upload.document.english" text="Upload Help Document For English " /></td>
							<td><apptags:formField fieldType="7" 
							fieldPath="entity.cfcAttachments[0].attPath" labelCode="" hasId="true" isMandatory="true"
						fileSize="COMMOM_MAX_SIZE" currentCount="0"
						showFileNameHTMLId="true" folderName="0"
						maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"/></td>
						
						</tr>
						
						<tr>
							<td><spring:message code="eip.upload.document.reg" text="Upload Help Document For Regional" /></td>
							<td> <apptags:formField fieldType="7" 
							fieldPath="entity.cfcAttachments[1].attPath" labelCode="" hasId="true" isMandatory="true"
						fileSize="COMMOM_MAX_SIZE" currentCount="1"
						showFileNameHTMLId="true" folderName="1"
						maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"/></td>
						
						</tr>
						
							</table>
							
					
					 <div class="text-center padding-top-10">				
				     <input type="button" class="btn btn-success"	onclick="return saveForm(this);" value="<spring:message code="eip.admin.helpDocs.save"/>" /> 
				     <apptags:backButton url="CommonHelpDocs.html" cssClass="btn btn-primary"/>
				     <input type="button" id="Reset" class="btn btn-warning" value="<spring:message code="rstBtn" text="Reset"/>" onclick="openForm('HelpDoc.html')"></input>
					 </div>
			    
				</form:form>
			</div>
			</div>
</div>