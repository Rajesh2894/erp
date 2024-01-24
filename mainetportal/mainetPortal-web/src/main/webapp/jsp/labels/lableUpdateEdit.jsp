<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>

function nospaces(t){
	  if(t.value.match(/\s/g)){
	    t.value=t.value.replace(/\s/g,'');
	  }
	}
	
	var cvs1 = getLocalMessage('label.update.success');

	function saveForm(element) {
		var errorList = [];
		if($('#propFileValue').val().trim()=='')
			{
			errorList.push("Label Value cannot be empty");
			showError(errorList);
			return false;
			}
		else
			{
			return saveOrUpdateForm(element, cvs1,
					"LableUpdateSearch.html?openSelected", 'save');
			}
	}
	function showError(errorList) {
		var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	    errMsg += '<ul>';
	    $.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
	    $('html,body').animate({ scrollTop: 0 }, 'slow');
		$('.error-div').html(errMsg);
		$(".error-div").show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
	}
	function closeOutErrBox() {
		$('.error-div').hide();
	}
	$(function() {
		$("#frmLableUpdateSearchkiran").validate();
	});
</script>
<div id="validationDiv">
				<div class="widget login margin-bottom-0">
	
					<div class="widget-header">
						<h2><spring:message code="label.update" /></h2>
					</div>
					
					
                 <div class="widget-content padding">
                  
					<form:form action="LableUpdateSearch.html"
						name="frmLableUpdateSearchkiran" id="frmLableUpdateSearchkiran" class="form-horizontal">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div" id="error-div1"></div>
						<div class="form-elements clear">
							<c:set var="LabelFieldLevelMsg" value="${command.getAppSession().getMessage('eip.admin.label.msg') }"></c:set>
							<form:textarea path="propFile.value" id="propFileValue" cssClass="form-control mandClassColor" rows="6" cols="50"
							data-rule-required="true" data-msg-required="${LabelFieldLevelMsg}"/>
						</div>
						
						<div class="text-center padding-top-10 clear">
						 <input type="button" class="btn btn-success" onclick="return saveForm(this);" value="<spring:message code="eip.admin.label.save" text="save"/>" /> 
					     <apptags:resetButton /> 
						</div>
						
						
					</form:form>
				</div>
                 </div>
                 </div>
