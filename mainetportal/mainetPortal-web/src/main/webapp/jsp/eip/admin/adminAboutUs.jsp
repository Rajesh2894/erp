<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link rel="shortcut icon" href="../assets/img/favicon.ico">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<script src="js/mainet/validation.js"></script>
<script src="js/eip/admin/adminAboutUs.js"></script>
<script>


	
$(document).ready(function(){
	
	$(".charsRemaining").hide();
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if(pathname.indexOf(txt) > -1) {
		$("#AdminFaqCheker").show()
		$("#isCheckerFlag").val('Y');
		$(".remark").prop('disabled',false);
		$("#chekkerflag1").val("Y");
		$(".AdminFaqbutton").hide();
		$(".AdminFaqChekerbutton").show();
		$("#AdminFaqChekerback").show();
		$("#AdminFaqback").hide();
		$("#Rejction").hide();
		
		$(".radiobutton").click(function() {
			 if( $(this).is(":checked") ){
		            var val = $(this).val(); 
		            if( val =='Y')
		            	{
		            	  $("#Rejction").hide();
		            	  $("#Rejction").val('');
		            	}
		            else
		            	{
		            	 $("#Rejction").show();
		            	}
		            
		          
		        };
	    });
		
		
		if($("input:radio:checked").val()=='Y')
         {
			$("#AdminFaqCheker").show();
			
         }
		
		if($("input:radio:checked").val()=='N')
			{
			$("#AdminFaqCheker").show();
			
			}
		
	
		}
	   else {
			$("#AdminFaqCheker").hide()
			$(".remark").prop('disabled',true);
			$("#chekkerflag1").val("N");
			$(".AdminFaqbutton").show();
			$(".AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			$(".remark").prop('disabled',true);
			if($("input:radio:checked").val()=='Y')
	         {
				$("#AdminFaqCheker").show();
				 $(".radiobutton").prop('disabled',true);
				 $(".radiobutton").removeClass('mandClassColor');
				 $(".radiobutton").addClass('disablefield');
	         }
			
			if($("input:radio:checked").val()=='N')
				{
				$("#AdminFaqCheker").show();
				 $(".radiobutton").prop('disabled',true);
				 $(".radiobutton").removeClass('mandClassColor');
				 $(".radiobutton").addClass('disablefield');
				}
		}
	$( ".btn-warning").click(function() {
	
		 $('.form-control').val("");
		});		
	
	
});

$(function() {
	$("#adminAboutUsFrmCheker").validate();
});


</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div id="content" class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="eip.admin.aboutUs.title" />
				
			</h2>
		</div>
		<apptags:helpDoc url="AdminAboutUs.html"></apptags:helpDoc>	
		<div class="widget-content padding">
		<div class="mand-label clearfix"><span><spring:message code="MandatoryMsg" /></span></div>
		
		<form:form method="POST" action="AdminAboutUs.html" name="adminAboutUsFrm" id="adminAboutUsFrmCheker" class="form-horizontal">
			
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="chekkerflag" id="chekkerflag1" />
			<c:if test="${not empty ABOUTUS_MSG}">
				<div class="message text-center">
					<b class="text-large text-red padding-left-5">${ABOUTUS_MSG}</b>
				</div>
			</c:if>
			<input type="hidden" value="${userSession.languageId}" id="langIDForDiv" />
			
			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for="descriptionEn">
					<spring:message code="eip.admin.aboutUs.descriptionEnPara1" />
				</label>
				<div class="col-sm-10">
                    <c:set var="EnglishPara1English" value="${command.getAppSession().getMessage('eip.admin.aboutUs.EnglishPara1English') }" />
					<form:textarea cssClass="form-control" path="adminAboutUs.descriptionEn" rows="6" maxlength="3000" required ="true" id="descriptionEn" onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${EnglishPara1English}"/>
					<p class="charsRemaining" id="P3"><spring:message code="characters.remaining" text="characters remaining"/></p> 
					<p class="charsRemaining"></p> 
				</div>
			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for="descriptionEn1"> 
					<spring:message code="eip.admin.aboutUs.descriptionEnPara2" />
				</label>
				<div class="col-sm-10">
                    <c:set var="EnglishPara2English" value="${command.getAppSession().getMessage('eip.admin.aboutUs.EnglishPara2English') }" />
					<form:textarea cssClass="form-control" path="adminAboutUs.descriptionEn1" id="descriptionEn1" rows="6" maxlength="3000" required ="true" onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${EnglishPara2English}"/>

					<p class="charsRemaining" id="P3"><spring:message code="characters.remaining" text="characters remaining"/></p> 
					<p class="charsRemaining"></p> 
				</div>
			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for="descriptionReg"> 
					<spring:message code="eip.admin.aboutUs.descriptionRegPara1" />
				</label>
				<div class="col-sm-10">
                    <c:set var="RegionalPara1Regional" value="${command.getAppSession().getMessage('eip.admin.aboutUs.RegionalPara1Regional') }" />
					<form:textarea cssClass="form-control" path="adminAboutUs.descriptionReg" id="descriptionReg" rows="6"  maxlength="4000" required ="true" onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${RegionalPara1Regional}"/>

					<p class="charsRemaining" id="P3"><spring:message code="characters.remaining" text="characters remaining"/></p> 
					<p class="charsRemaining"></p> 
				</div>
			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for="descriptionReg1">
					<spring:message code="eip.admin.aboutUs.descriptionRegPara2" /></label>
				<div class="col-sm-10">
                     <c:set var="RegionalPara2Regional" value="${command.getAppSession().getMessage('eip.admin.aboutUs.RegionalPara2Regional') }" />
					<form:textarea cssClass="form-control" path="adminAboutUs.descriptionReg1" id="descriptionReg1" rows="6" maxlength="4000" required ="true" onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${RegionalPara2Regional}"/>

					<p class="charsRemaining" id="P3"><spring:message code="characters.remaining" text="characters remaining"/></p> 
					<p class="charsRemaining"></p> 
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label" for="entity.remark">
					<spring:message code="eip.cheker.remark" text="Cheker Remark" /><span class="mand">*</span></label>
				<div class="col-sm-10">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea cssClass="form-control remark" path="adminAboutUs.remark" rows="3" maxlength="1000" onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${cheker}"/>
					
					<p class="charsRemaining" id="P3"><spring:message code="characters.remaining" text="characters remaining"/></p> 
					<p class="charsRemaining"></p> 
				</div>
			</div>
			
			
				
				<div class="form-group" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                    
                       <form:radiobutton path="adminAboutUs.chekkerflag" name="radiobutton" value="Y" class="radiobutton" aria-label="Authenticate content"/> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="adminAboutUs.chekkerflag" name="radiobutton" value="N" class="radiobutton" aria-label="Reject content"/><spring:message code="Reject" text="Reject" />
	                </div>					
				</div>
				

			<div class="text-center padding-top-10">
				<apptags:submitButton entityLabelCode="eip.admin.aboutUs.saveBtn" actionParam="save" successUrl="AdminAboutUs.html" cssClass="btn btn-success AdminFaqbutton"></apptags:submitButton>
				<apptags:submitButton entityLabelCode="eip.admin.aboutUs.saveBtn" actionParam="save" successUrl="AdminAboutUs.html?AdminFaqCheker" cssClass="AdminFaqChekerbutton btn btn-success"></apptags:submitButton>
				<%--  <input type="reset" class="btn btn-warning" value="<spring:message code="eip.admin.login.resetBT" />"> --%>
				 <a href="CitizenHome.html" class="btn btn-danger" ><spring:message code="bt.back"/></a>
					
					
			</div>
		</form:form>
	</div>
</div>
</div>
