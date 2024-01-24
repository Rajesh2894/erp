

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script>
$( document ).ready(function() {
	
	$("#mobNo").focus();
	
jQuery('.hasMobileNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','10');
	});
});

$(function() {
	$("#adminForgotPasswordForm").validate();
});
</script>

<body>
<div class="padding-40 form-group clearfix min-height-400" id="CitizenService">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
  <div class="widget-header">
    <h2><strong><spring:message code="eip.citizen.forgotPassword.FormHeader" text="Citizen Forgot Password" /> </strong></h2>
  </div>
  <div class="widget-content padding">
    <div id="basic-form">
      <jsp:include page="/jsp/tiles/validationerror.jsp" />
    <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
     <spring:message code="feedback.Mobile" var="Mobile"/>
     <form:form id="adminForgotPasswordForm" name="adminForgotPasswordForm" method="POST" role="form" autocomplete="on">
        <div class="form-group">
       <%--  <label><i class="fa fa-user"></i> <spring:message code="eip.admin.forgotPassword.MobileNo" text="Mobile No."/></label> --%>
        <form:input id="mobNo" path="mobileNumber" aria-label="Enter Mobile number" cssClass=" form-control mandClassColor hasMobileNo12" onkeypress="return admintryStep1(event)"  maxlength="12" autocomplete="off" Placeholder="${Mobile}" data-rule-required="true" data-msg-required="Mobile No. must not be empty."/>
        </div>
        <div class="row">
        	<div class="col-lg-3 col-md-3 col-xs-6 col-lg-offset-3 col-md-offset-3"><input type="button" class="btn btn-success btn-block" onclick="adminvalidateFormStep1();"	value="<spring:message code="eip.commons.submitBT" text="Submit"/>" /></div>
        	<div class="col-lg-3 col-md-3  col-xs-6"><input type="reset" class="btn btn-warning btn-block" value="<spring:message code="eip.agency.login.resetBT"/>" id="resettButon" onclick="{$('.error-div').hide();}"></div>
        </div>
        
        
      </form:form>
    </div>
  </div>
</div>
</div>
</div>
</div><hr/>
</body>