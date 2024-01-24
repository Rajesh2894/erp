<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% response.setContentType("text/html; charset=utf-8"); %>

<script src="js/eip/admin/adminForgotPasswordProcess.js"></script>
<script src="js/eip/admin/adminRegistrationForm.js"></script>
<script src="assets/libs/jquery-slimscroll/jquery.slimscroll.js"></script>
<script src="js/eip/admin/landingPage.js" ></script>
<script src="js/eip/admin/adminLoginForm.js"></script>
<script src="js/eip/admin/adminResetPasswordProcess.js"></script>
<script src="js/eip/admin/adminUpdatePersonalDtlsProcess.js"></script>


<script>
function changeLanguage(url) {
	var array = document.URL.split('/');
	array = array[array.length - 1];
	if (array == '' || array == '#') {array = 'Home.html';}
	url = url + '&url=' + array;
	window.location.href = url;
}
var resizefunc = [];
function dispose() {
	$('.dialog').html('');
	$('.dialog').hide();
	disposeModalBox();
}
function hideErrorDiv() {
	$('.error-div').hide();
}
$(document).ready(function() {
	setTimeout(function() {$('#emploginname').focus()}, 500);
	$('.logout').css('display', 'none');
	$(".footer-logos").removeClass('hidden');
});
window.onload = function() {
	$('.form-control').on("copy cut paste drop", function() {return false;});
}
</script>

   <div class="container login-page">
      <div class="full-content-center">
         <input type="hidden" id="languageId"
            value="${userSession.languageId}" />
         <div class="login-wrap animated flipInX">
            <div class="login-block">
               <div class="error-div alert alert-danger"></div>
               <form:form id="adminLoginForm" name="adminLoginForm" method="POST"
                  action="AdminLogin.html" autocomplete="off">
                   <input type="hidden" id ="uniqueKeyId" value ="${userSession.getCurrent().getUniqueKeyId()}">
                  <div class="form-group login-input">
                     <i class="fa fa-user overlay"></i> <input type="text"
                        class="form-control text-input" id="emploginname"
                        name="adminEmployee.emploginname"
                        onkeypress="return tryLogin(event);" maxlength="50"
                        autocomplete="off" readonly
                        onfocus="this.removeAttribute('readonly');hideErrorDiv();"
                        placeholder='<spring:message code="eip.admin.login.loginName"/>' />
                        <%-- onchange="getLocationByUser()" --%>
                  </div>
                  <div class="form-group login-input">
                     <i class="fa fa-key overlay"></i> <input type="password"
                        class="form-control text-input" id="adminEmployee.emppassword"
                        autocomplete="off" name="adminEmployee.emppassword"
                        onkeypress="return tryLogin(event)" maxlength="50"
                        readonly onfocus="this.removeAttribute('readonly');hideErrorDiv();"
                        placeholder='<spring:message code="eip.admin.login.password"/>' />
                  </div>
                  <div class="form-group login-input">
                     <select id="selectedDistrict"
                        name="adminEmployee.organisation.orgid" data-content=""
                        class="form-control chosen-select-no-results"
                        onchange="getLocation();">
                        <option value="-1">
                           <spring:message code="eip.admin.login.select.organisation"/>
                        </option>
                        <c:forEach items="${command.organisationsList}" var="org">
                           <c:if test="${userSession.languageId eq 1}">
                              <option value="${org.orgid}">${org.ONlsOrgname}</option>
                           </c:if>
                           <c:if test="${userSession.languageId eq 2}">
                              <option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
                           </c:if>
                        </c:forEach>
                     </select>
                  </div>
                  <div class="form-group login-input">
                     <select id="selectedLocation" name="loggedLocId"
                        onchange="hideErrorDiv()" data-content=""
                        class="form-control chosen-select-no-results">
                        <option value="-1">
                           <spring:message code="eip.admin.login.select.location"/>
                        </option>
                     </select>
                  </div>
                  <input class="btn btn-success btn-block" type="button"
                     onclick="doAdminLogin(this);"
                     value='<spring:message code="eip.commons.submitBT"/>' />
                  	<ul class="clearfix">
                     <%--<li class="pull-left"><a href="javascript:void(0);"
                        onclick="getAdminForgotPassStep1()"><spring:message
                        code="eip.admin.login.forgotPassword" /></a></li> --%>
                     <li class="pull-right">
                        <a href="javascript:void(0);"
                           onclick="getAdminResetPassStepI();">
                           <spring:message
                              code="eip.admin.login.ResetPassword" />
                        </a>
                     </li>
                  </ul>
               </form:form>
            </div>
         </div>
      </div>
   </div>