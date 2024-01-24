
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script>
$(document).ready(function(){
	
	
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	if(pathname.indexOf(txt) > -1) {
	
		$("#AdminFaqCheker").show()
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
			$("#chekkerflag1").val("N");
			$(".AdminFaqbutton").show();
			$(".AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
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
			
	
	 
});
function countChar(val) {
    var len = val.value.length;
 
    if (len >= 1000) {
      val.value = val.value.substring(0, 1000);
    } else {
    	$('.charsRemaining').next('P').text(1000 - len);
    
    }
  }; 

  function resetForm(resetBtn) {
		
		
		cleareFile(resetBtn);
		
		if (resetBtn && resetBtn.form) {
			
			$('[id*=file_list]').html('');
			$('.error-div').remove();
			resetBtn.form.reset();
			
			
		};
		$('.form-control').val("");
	}


</script>

<apptags:breadcrumb></apptags:breadcrumb>
 <div class="content">
 <div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="rti.add.aboutproject" text="Add About Project"/> </strong>
			</h2>
		</div>
		<div class="widget-content padding">
		<div class="mand-label">
					<span><spring:message code="MandatoryMsg" text="MandatoryMsg" /></span>
				</div>
	<form:form method="post" action="ProjectDetail.html" name="frmProjectDetail" id="frmProjectDetail" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="form-group">
			<label  class="col-sm-2 control-label" for="entity.descTitleEng"><spring:message code="rti.projectTitleEng"/><span class="mand">*</span></label>
			<div class="col-sm-10">
				<apptags:inputField fieldPath="entity.descTitleEng" maxlegnth="1000" cssClass="mandColorClass empname subsize form-control" />
			</div>
		</div>
		<div class="form-group">	
			<label class="col-sm-2 control-label" for="entity.descInfoEng"><spring:message code="rti.projectInfoEng" /><span class="mand">*</span></label>
			<div class="col-sm-10">
				<form:textarea path="entity.descInfoEng" maxlength="1000" cssClass="mandColorClass empname subsize form-control" rows="4" cols="50" onkeyup="countChar(this)"  onclick="countChar(this)"/>
				<p class="charsRemaining" id="P2">characters remaining</p> 
			    <p class="charsRemaining">characters remaining</p> 
			</div>
	    </div>
		<div class="form-group">
			<label  class="col-sm-2 control-label" for="entity.descTitleReg"><spring:message code="rti.projectTitleReg"/><span class="mand">*</span></label>
			<div class="col-sm-10">
				<apptags:inputField fieldPath="entity.descTitleReg" maxlegnth="1000" cssClass="mandColorClass empname subsize form-control" />
			</div>
	    </div>
		<div class="form-group">
			<label  class="col-sm-2 control-label" for="entity.descInfoReg"><spring:message code="rti.projectInfoReg" /><span class="mand">*</span></label>
			<div class="col-sm-10">
				<form:textarea path="entity.descInfoReg" maxlength="1000" cssClass="mandColorClass empname subsize form-control" rows="4" cols="50" onkeyup="countChar(this)"  onclick="countChar(this)" />
				 <p class="charsRemaining" id="P2">characters remaining</p> 
				 <p class="charsRemaining">characters remaining</p> 
		</div>
	   </div>
	   
	 
		<div class ="form-group" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton"/> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton"/> <spring:message code="Reject" text="Reject" />
	                </div>					
				</div>
		<div class="text-center padding-top-10">
			<apptags:submitButton successUrl="AboutProject.html"
				entityLabelCode="About Project" cssClass="css_btn btn btn-success AdminFaqbutton" />
				<apptags:submitButton successUrl="AboutProject.html?AdminFaqCheker"
				entityLabelCode="About Project" cssClass="css_btn btn btn-success AdminFaqChekerbutton" />
			<c:if test="${command.MODE eq 'A'}">
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormOnAdd(frmProjectDetail)" class="btn btn-warning"></input>
					</c:if>
			<a href="AboutProject.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="portal.common.button.back" /></a>
			<a href="AboutProject.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback"><spring:message code="portal.common.button.back" /></a>		
		</div>
	</form:form>
</div>
</div>
</div>
