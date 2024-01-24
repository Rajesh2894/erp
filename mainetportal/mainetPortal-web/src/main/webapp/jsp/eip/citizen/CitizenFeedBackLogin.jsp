<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="assets/css/style.css" rel="stylesheet" type="text/css" />
<script>
	
 $( document ).ready(function() {	
	 $('#resettButon').on('click', function()
			    { 
		 getfeedbackForm();
}); 
	 $('.charsRemaining').hide();
 });

 function doRefreshLoginCaptcha(){
	 var idxRAND=Math.floor(Math.random()*90000) + 10000;
	 $("#cimg").attr("src",'CitizenFeedBack.html?captcha&id='+idxRAND);
}
 
 jQuery('.hasMobileNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','10');
	});
	
	jQuery('.hasSpecialChara').keyup(function () { 
	    this.value = this.value.replace(/[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~0-9]/g, '');
	 
	});
	
	function showError1(errorList) {
		var errMsg ='<button type="button" class="close" onclick="closeErrBox1()"><span aria-hidden="true">&times;</span></button><ul>'
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';
	    $('.error-div').hide();
	    $('#frmCitizenFeedBack').find('input:text, input:password, select, textarea').val('');
	    getfeedbackForm();
	    $('.message').html(errMsg);
	    $('.message').show();
	}
	function closeErrBox1()
	{
		 $('.message').hide();
	}
	
	function countChar(val) {
	    var len = val.value.length;
	 
	    if (len >= 1001) {
	      val.value = val.value.substring(0, 1000);
	    } else {
	    	$('.charsRemaining').show();
	    	$('.charsRemaining').next('P').text(1000 - len);
	    
	    }
	  }
	
	function spanHide() {
	  	$('.charsRemaining').hide();
	  }
</script>
<style>
.message {
	border:1px solid rgba(0, 153, 0, 0.2);
	background:rgba(0, 153, 0, 0.2);
	text-align:center;
	width: 95.5%;
	padding:7px 5px;
	margin:5px 0;
	font-style:14px;
	color:#333;
}
</style>
<div class="row padding-40">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
<div class="widget  margin-bottom-0" id="content">
<div class="widget-header">
<h2><strong><spring:message code="feedback.formTitle"/></strong></h2>
</div>



	      
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
		       <div class="widget-content padding">
		       <div class="message alert" role="alert" style="display: none"></div>
		       <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
				<form:form method="post" action="CitizenFeedBack.html?sendfeedback"
					name="frmCitizenFeedBack" id="frmCitizenFeedBack" role="form">
			      
					<div class="form-group">
						
							<label for="fdUserName"><spring:message code="feedback.name"  /><span class="mand">*</span>  </label>
							<apptags:inputField fieldPath="feedback.fdUserName" cssClass="form-control hasSpecialChara mandClassColor " hasId="true" />
						
					</div>
			
					<div class="form-group">
							<label for="mobileNo"><spring:message code="feedback.Mobile" /><span class="mand">*</span>  </label>
							<apptags:inputField fieldPath="feedback.mobileNo"   cssClass=" hasMobileNo  form-control mandClassColor " hasId="true"/>
					</div>
			
					<div class="form-group">
							<label for="emailId"><spring:message code="feedback.Email" /><span class="mand">*</span>  </label>
							<apptags:inputField fieldPath="feedback.emailId" hasId="true"  cssClass=" form-control mandClassColor " isDisabled="" />
					</div>
					<div class="form-group">
							<label for="feedback.feedBackDetails"> <spring:message code="feedback.comments" /><span class="mand">*</span> </label>
							
							<form:textarea path="feedback.feedBackDetails"   cssClass="form-control mandClassColor" onkeyup="countChar(this)" onclick="countChar(this)" onblur="spanHide()"/>	
							<p class="charsRemaining" id="P3">characters remaining</p>
				  <p class="charsRemaining">characters remaining</p> 			
					</div>
					
					<div class="row form-group" id="captchaL">
					<div class="col-lg-6" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="CitizenFeedBack.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="#" onclick="doRefreshLoginCaptcha()"><i class="fa fa-refresh"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="col-lg-6">
					<label for="captchaSessionLoginValue" class="hide">captchaP</label>
						<form:input path="captchaSessionLoginValue"
							cssClass="form-control" placeholder='${captchaP}'
							autocomplete="off" />
					</div>
				</div>
			
					<div class="row">
                        <div class="col-xs-4">
						<input type="button" class="btn btn-primary btn-block" onclick="dofeedback(this);"  value="<spring:message code="feedback.Sendfeedback1"/>"/>
						</div>
						<div class="col-xs-4">
 						<input type="button" class="btn btn-warning btn-block" value="<spring:message code="eip.agency.login.resetBT"/>" id="resettButon" >  
						</div>
						<div class="col-xs-4">
						<apptags:backButton url="CitizenHome.html" cssClass="btn-block" buttonLabel="feedback.Back"></apptags:backButton>
						</div>
					</div>
				</form:form>
				</div>
			</div>
	</div>
	</div>
	</div>
	</div>
		
			