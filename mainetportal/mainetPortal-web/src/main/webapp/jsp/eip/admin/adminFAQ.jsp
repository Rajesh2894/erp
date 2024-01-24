<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/mainet/validation.js"></script>
<script>
$(document).ready(function(){
	
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	
	$(".AdminFaqbutton").prop("disabled",false);
	$(".AdminFaqChekerbutton").prop("disabled",false);
	if(pathname.indexOf(txt) > -1 || txt2=="Y") {
		//$("#AdminFaqCheker").show()
		$("#isCheckerFlag").val('Y');
		$(".remark").prop("disabled",false);
		$("#chekkerflag1").val("Y");
		$(".AdminFaqbutton").hide();
		$(".AdminFaqChekerbutton").show();
		$("#AdminFaqChekerback").show();
		$("#AdminFaqbutton").hide();
		$("#AdminFaqChekerbutton").show();
		$("#AdminFaqChekerback").show();
		$("#AdminFaqback").hide();
		$("#Rejction").hide();
		$(".AdminFaqbutton").hide();
		$(".AdminFaqChekerbutton").show();
		$("#AdminFaqChekerback").show();
		$("#AdminFaqback").hide();
		
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
		
	/* 	if($("input:radio:checked").val()=='Y')
         {
			$("#AdminFaqCheker").show();
			
         }
		
		if($("input:radio:checked").val()=='N')
			{
			$("#AdminFaqCheker").show();
			
			} */

		}
	   else {
		//$("#AdminFaqCheker").hide()
			$(".remark").prop("disabled",true);
			$("#chekkerflag1").val("N");
			$(".AdminFaqbutton").show();
			$("#AdminFaqbutton").show();
			$("#AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			$(".AdminFaqbutton").show();
			$(".AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			$("#AdminFaqback").show();
			
			/* if($("input:radio:checked").val()=='Y')
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
				} */
			$(".radiobutton").prop('disabled',true);
			 $(".radiobutton").removeClass('mandClassColor');
			 $(".radiobutton").addClass('disablefield');
		}
 
});
function resetFormPub(obj)
{

	$('.error-div').remove();
//     $("#frmAdminFAQ").closest('form').find("input[type=text], textarea").val("");

 $(':input','#frmAdminFAQ')
    .not(':button, :submit, :reset, :hidden')
    .val('')
    .removeAttr('checked')
    .removeAttr('selected');
   $('select').prop('selectedIndex',0);
    
	return false; 
	
	
}

function saveOrUpdateFAQ(obj, message, successUrl, actionParam)
{
	var errorList = [];
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if( txt2=="Y") {
		 if ($('.remark').val()==null || $('.remark').val()==""){
			 errorList.push(getLocalMessage('eip.admin.validatn.cheker'));   
			}
		} 
	/* $(".AdminFaqbutton").prop("disabled",true);
	$(".AdminFaqChekerbutton").prop("disabled",true); */
	var appOrRejFlag =  $("input[name='entity.chekkerflag']:checked").val()
	var mode = $('#mode').val()
	 var successMessage ='';
	if(mode == 'Edit'){
		if(appOrRejFlag!= undefined && appOrRejFlag == 'Y' && document.URL.indexOf('AdminFaqCheker') > -1){
			  successMessage = getLocalMessage('admin.approve.successmsg');
		}else if(appOrRejFlag!= undefined && appOrRejFlag == 'N' &&  document.URL.indexOf('AdminFaqCheker') > -1){
			  successMessage = getLocalMessage('admin.reject.successmsg');
		}else{
		      successMessage = getLocalMessage('admin.update.successmsg');
		}
	} else{
		 successMessage = getLocalMessage('admin.save.successmsg');
	}
    
	if (!actionParam) {
		
		actionParam = "save";
	}
	if (errorList.length == 0){
		return doFormActionForSave($('#getFormId'),successMessage, actionParam, true , successUrl);
	}else{
		return errorList;
		}
	}
	
</script>
  
    <script>

    function resetForm(resetBtn) {

    	cleareFile(resetBtn);
    	
    	if (resetBtn && resetBtn.form) {
    		
    		$('[id*=file_list]').html('');
    		$('.error-div').remove();
    		resetBtn.form.reset();
	
    		
    	};
    	$('.form-control').val("");
    }
  
    function countChar(val) {
    	var maxlength=val.maxLength; 
        var len = val.value.length;
        if (len >= maxlength) {
          val.value = val.value.substring(0, maxlength);
          $('.charsRemaining').next('P').text(maxlength - len);
        } else {
        	$('.charsRemaining').next('P').text(maxlength - len);
        	$(this).siblings(".charsRemaining").show();
        }
      };
    //Transalator Function
	   $( document ).ready(function() {
		   var langFlag = getLocalMessage('admin.lang.translator.flag');
			if(langFlag ==='Y'){
				$("#questionEn").keyup(function(event){
					var no_spl_char;
					no_spl_char = $("#questionEn").val().trim();
					if(no_spl_char!='')
					{
						//alert('world');
						commonlanguageTranslate(no_spl_char,'questionReg',event,'');
					}else{
						$("#questionReg").val('');
					}
				});
				
				$("#answerEn").keyup(function(event){
					var no_spl_char;
					no_spl_char = $("#answerEn").val().trim();
					if(no_spl_char!='')
					{
						//alert('world');
						commonlanguageTranslate(no_spl_char,'answerReg',event,'');
					}else{
						$("#answerReg").val('');
					}
				});
			}
	   });
	   
	   $(function() {
			$("#frmAdminFAQ").validate();
		});
	  
    </script>
  </head>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="admin.adminFAQHeader" />
			</h2>
		</div>
		<div class="widget-content padding">
		<div class="mand-label clearfix"><span><spring:message code="MandatoryMsg" /></span></div>
			<!--Add Section Strat Here-->
			<form:form action="AdminFAQ.html" name="frmAdminFAQ" id="frmAdminFAQ"
				class="form-horizontal">
		<div class="error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
		</div>
		<form:hidden path="isChecker" id="isCheckerFlag"/>
		<form:hidden path="chekkerflag" id="chekkerflag1"/>
				<input type ="hidden" id ="mode" value="${command.mode }"/>
		<input type="hidden" id="getFormId">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.questionEn"><spring:message
							code="admin.questionEn" text="Question(ENG)" /><span class="mand">*</span></label>
					<div class="col-sm-10">
					    <c:set var="QuestionEnglish" value="${command.getAppSession().getMessage('eip.admin.faq.QuestionEnglish') }" />
						<form:textarea id="questionEn" path="entity.questionEn" maxlength="250"
							cssClass=" form-control mandColorClass empname maxsize" rows="4" onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${QuestionEnglish}"/>
						 <div>
						 <p class="charsRemaining" id="P2"><spring:message
							code="characters.remaining" text="characters remaining" /></p>
						 <p class="charsRemaining"></p> 
					     </div>
					</div>
				 	<!-- <p class="charsRemaining" id="P2">characters remaining</p>  -->
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.answerEn"><spring:message
							code="admin.ansEn" text="Answer(ENG)" /><span class="mand">*</span></label>
					<div class="col-sm-10">
					    <c:set var="AnswerEnglish" value="${command.getAppSession().getMessage('eip.admin.faq.AnswerEnglish') }" />
						<form:textarea id="answerEn"  path="entity.answerEn" maxlength="500"
							cssClass=" form-control mandColorClass empname maxsize" rows="4" onkeyup="countChar(this)"  onfocus="countChar(this)" data-rule-required="true" data-msg-required="${AnswerEnglish}"/>
						<!-- <p class="charsRemaining">characters remaining</p> -->
					 <div>
					 <p class="charsRemaining" id="P2"><spring:message
							code="characters.remaining" text="characters remaining" /></p> 
					   <p class="charsRemaining"></p> 
					</div>
					</div>

				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.questionReg"><spring:message
							code="admin.questionReg" text="Question(REG)" /><span
						class="mand">*</span></label>
					<div class="col-sm-10">
					     <c:set var="QuestionRegional" value="${command.getAppSession().getMessage('eip.admin.faq.QuestionRegional') }" />
						<form:textarea id="questionReg" path="entity.questionReg" maxlength="1000"
							cssClass=" form-control mandColorClass empname maxsize" rows="4"  onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${QuestionRegional}"/>
						<div>
						 <p class="charsRemaining" id="P3"><spring:message
							code="characters.remaining" text="characters remaining" /></p> 
					   <p class="charsRemaining"></p> 
					</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.answerReg"><spring:message
							code="admin.ansReg" text="Answer(REG)" /><span class="mand">*</span></label>
					<div class="col-sm-10">
					     <c:set var="AnswerRegional" value="${command.getAppSession().getMessage('eip.admin.faq.AnswerRegional') }" />
						<form:textarea id="answerReg" path="entity.answerReg" maxlength="1000"
							cssClass="form-control mandColorClass empname maxsize" rows="4"   onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${AnswerRegional}"/>
					   <div>
					   <p class="charsRemaining" id="P4"><spring:message
							code="characters.remaining" text="characters remaining" /></p> 
					   <p class="charsRemaining"></p> 
					  </div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.remark"><spring:message
							code="eip.cheker.remark" text="Cheker Remark" /><span class="mand">*</span>
					</label>
					<div class="col-sm-10">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="entity.remark" id = "entityRemark" maxlength="1000"
							cssClass="form-control remark mandColorClass" rows="2" onkeyup="countChar(this)" onfocus="countChar(this)"   data-rule-required="true" data-msg-required="${cheker}"/>
						<div>
			 				<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
							<p class="charsRemaining" id="DescriptionCount"></p>
		    			</div>
					</div>
				</div>
				<div class="form-group padding-top-10" id="AdminFaqCheker">
					<div class="col-sm-12 text-center">
						<form:radiobutton path="entity.chekkerflag" name="radiobutton"
							value="Y" class="radiobutton"  aria-label="Authenticate content"/>
						<spring:message code="Authenticate" text="Authenticate" />
						<form:radiobutton path="entity.chekkerflag" name="radiobutton"
							value="N" class="radiobutton" aria-label="Reject content"/>
						<spring:message code="eip.dept.auth.reject" text="Reject" />
					</div>
				</div>
				<div class="text-center padding-top-10">
			 <input class="btn btn-success AdminFaqChekerbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateFAQ(this,'', 'AdminFAQSearch.html?AdminFaqCheker','');"></input>
	            <input class="btn btn-success AdminFaqbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateFAQ(this,'', 'AdminFAQSearch.html','');"></input>	
					<!-- <button type="Reset" class="btn btn-warning"></button> -->
					<c:if test="${command.mode eq 'A'}">
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormOnAdd(frmAdminFAQ)" class="btn btn-warning"></input>
					</c:if>
					<a href="AdminFAQSearch.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" text="Back" /></a>
					<a href="AdminFAQSearch.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback"><spring:message code="bckBtn" text="Back" /></a>
				</div>
			</form:form>
		</div>
	</div>
</div>
