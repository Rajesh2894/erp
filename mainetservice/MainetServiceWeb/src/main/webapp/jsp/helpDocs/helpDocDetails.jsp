<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script> 
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- <script type="text/javascript" src="js/helpDocs/helpDocs.js"></script> -->

<apptags:breadcrumb></apptags:breadcrumb>
<c:if test="${not command.hasValidationErrors()}">
</c:if>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>

<script type="text/javascript">

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
</script>
		
		<div class="content" >
        <div class="widget">
		<div class="widget-header">
        <h2> <strong><spring:message code="eip.admin.helpDocs.breadcrumb" /></strong></h2>
        <apptags:helpDoc url="CommonHelpDocs.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
        <div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
		<form:form action="HelpDoc.html"  name="frmHelpDoc" id="frmHelpDoc" method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<input type="hidden" value="A" name="modeOfType">
		<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message code="eip.admin.helpDocs.formname" text="Form Name" /></label>
		<div class="col-sm-4">
		<form:select path="entity.moduleName"  cssClass=" form-control mandClassColor chosen-select-no-results" id="deptCode">
		<form:option value="0" > <spring:message code="eip.admin.helpDocs.selformname" text="Select Form Name"/></form:option> 
		<c:forEach items="${command.nodes}" var="look">
		<form:option value="${look.value}" label="${look.key}"/>
		</c:forEach>
		</form:select>
		</div>
		</div>
		<table class="table table-bordered">
              <tr>
			  <th><spring:message code="eip.admin.helpDocs.lang" text="Language" /></th>
			  <th width="60%"><spring:message code="eip.admin.helpDocs.document" text="Document" /></th></tr>
			  <tr>
			  <td><spring:message code="eip.admin.helpDocs.lang.english" text="Upload Help Document For English"/></td>
			  <td>
			  <apptags:formField fieldType="7" fieldPath="entity.cfcAttachments[0].attPath" labelCode="" hasId="true" isMandatory="true"
						fileSize="COMMOM_MAX_SIZE" currentCount="0" showFileNameHTMLId="true" folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"/>
			  </td>
			  </tr>
						
			  <tr>
			  <td><spring:message code="eip.admin.helpDocs.lang.hindi" text="Upload Help Document For Regional"/></td>
			  <td> <apptags:formField fieldType="7" fieldPath="entity.cfcAttachments[1].attPath" labelCode="" hasId="true" isMandatory="true"
						fileSize="COMMOM_MAX_SIZE" currentCount="1" showFileNameHTMLId="true" folderName="1" maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"/>
			  </td>
			  </tr>
			  </table>
							
		<div class="text-center padding-top-10">				
				<input type="submit" class="btn btn-success btn-submit"	onclick="return saveForm(this);" value="<spring:message code="eip.admin.helpDocs.save"/>" /> 
				<apptags:backButton url="CommonHelpDocs.html" cssClass="btn btn-primary"/>
				<input type="button" id="Reset" class="btn btn-warning" value="<spring:message code="reset.msg" text="Reset"/>" onclick="openForm('HelpDoc.html')"></input>
		</div>
		</form:form>
		</div>
		</div>
		</div>