<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/eip/admin/quickLink.js"></script>
<script src="js/mainet/validation.js"></script>
<style>.modal-backdrop.in{opacity: 0;}</style>
<script>
	$('.decimal').keyup(function() {
		var val = $(this).val();
		if (isNaN(val)) {
			val = val.replace(/[^0-9]./g, '');
			

			if (val.split('.').length > 2)
				val = val.replace(/\.+$/, "");

			$(this).val("0.0");
		} else {
			$(this).val(val);
		}

	});
	$('.btn-warning').click(function() { 

		$("#linkTitleEg").val("");
		$("#linkTitleReg").val("");
		$("#exLink").val("");
		$("#linkPath").val("");
		
		});
	
</script>

<script>
	$(function() {
		$('[name="linksMaster.linkOrder"]').focus();
	});
	jQuery('.hasNotAllowSpecialLangVal').keyup(function() {
		this.value = this.value.replace(/[^a-zA-Z0-9. _-]/g, '');

	});
	jQuery('.hasDecimal').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	
	$('.required-control').next().children().addClass('mandColorClass');
</script>

<script>
$(document).ready(function(){
	
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if(pathname.indexOf(txt) > -1 || txt2=="Y") {
		
		$("#isCheckerFlag").val('Y');
		$(".remark").prop("disabled",false);
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
		  
			/* $("#AdminFaqCheker").hide() */
			$(".remark").prop("disabled",true);
			$("#chekkerflag1").val("N");
			$(".AdminFaqbutton").show();
			$(".AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			 $(".radiobutton").prop('disabled',true);
			 $(".radiobutton").removeClass('mandClassColor');
			 $(".radiobutton").addClass('disablefield');
		/* 	if($('input[class=radiobutton]:checked').val()=='Y')
	         {
			
				$("#AdminFaqCheker").show();
				 $(".radiobutton").prop('disabled',true);
				 $(".radiobutton").removeClass('mandClassColor');
				 $(".radiobutton").addClass('disablefield');
	         }
			
			if($('input[class=radiobutton]:checked').val()=='N')
				{
			
				$("#AdminFaqCheker").show();
				 $(".radiobutton").prop('disabled',true);
				 $(".radiobutton").removeClass('mandClassColor');
				 $(".radiobutton").addClass('disablefield');
				} */
		}
			
	
	 
	 
});

function saveOrUpdateLink(obj, message, successUrl, actionParam)
{

	var errorList = [];
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if( txt2=="Y") {
		 
		 if ($('.remark').val()==null || $('.remark').val()==""){
			 
			     errorList.push(getLocalMessage('eip.admin.validatn.cheker')); 
				 $('#error_msg_cheker').addClass('error');
			     $('#error_msg_cheker').css('display','block');
			     $('#error_msg_cheker').html(getLocalMessage('eip.admin.validatn.cheker'));
			     return;			
			}
		} 
	var appOrRejFlag =  $("input[name='entity.chekkerflag']:checked").val()
	var mode = $('#mode').val()
	 var successMessage ='';
	if(mode == 'U'){
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
	
	

function resetFormPub(obj)
{

	$('.error-div').remove();
   $(':input','#frmAdminQuickLinkForm')
    .not(':button, :submit, :reset, :hidden')
    .val('')
    .removeAttr('checked')
    .removeAttr('selected');
   $('select').prop('selectedIndex',0);
   $("#linkOrder").val("0.0");
   $("#linkRadioButton1").prop("checked", true);
 //  $('#0_file_0').attr('src', '');
	return false; 
	
	
}
//Transalator Function
$( document ).ready(function() {
	   
	   
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$("#linkTitleEg").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#linkTitleEg").val().trim();
				if(no_spl_char!='')
				{
					//alert('world');
					commonlanguageTranslate(no_spl_char,'linkTitleReg',event,'');
				}else{
					$("#linkTitleReg").val('');
				}
			});
		}
});

$(function() {
	$("#frmAdminQuickLinkForm").validate();
});
   
$(function() {
	$("#adminContact21").validate();
});
 
$('select.form-control.mandColorClass').on('blur', function() { 
	
	      var check = $(this).val();
		     var validMsg =$(this).attr("data-msg-required");
		     if(check == '' || check == '0' || check == "null"){
		    		 $(this).parent().switchClass("has-success","has-error");
				     $(this).addClass("shake animated");
				     $('#error_msg').addClass('error');
				     $('#error_msg').css('display','block');
				     $('#error_msg').html(validMsg);}
		     else
		     {$(this).parent().switchClass("has-error","has-success");
		     $(this).removeClass("shake animated");
		     $('#error_msg').css('display','none');
		     $('#error_msg_cheker').css('display','none');
		     $('#error_msg_cheker').remove();		
		     }  
 });
 $('textarea.form-control.remark').on('blur', function() { 
         $(this).parent().switchClass("has-error","has-success");
	     $(this).removeClass("shake animated");
	     $('#error_msg_cheker').css('display','none');
	     $('#error_msg_cheker').remove();
	       
}); 

</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="admin.qlGridCaption" /></h2>
			<div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a></div>
		</div>
		
		<div class="widget-content padding">
			<div class="mand-label clearfix"><span><spring:message code="MandatoryMsg" text="(*)Fields are mandatory" /></span></div>
			
			<form:form method="post" action="AdminQuickLinkForm.html"
				name="frmAdminQuickLinkForm" id="frmAdminQuickLinkForm"
				class="form-horizontal">
			
			 
			
			
			<div class="error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
		<form:hidden path="linksMaster.checkerFlag1" id="chekkerflag1" />
		<form:hidden path="isChecker" id="isCheckerFlag"/>
		<input type ="hidden" id ="mode" value="${command.mode }"/>
				<div class="form-group">
					<label class="control-label col-sm-2" for="linkOrder">
						<spring:message code="admin.qlFormLinkOrder" /><span class="mand">*</span>
					</label>
					<div class="col-sm-4">
					<c:set var="LinkOrder" value="${command.getAppSession().getMessage('eip.admin.quicklink.LinkOrder') }" />
		         <form:input id="linkOrder" path="linksMaster.linkOrder"  maxlegnth="4"
			         cssClass="form-control hasDecimal"  data-rule-required="true" data-msg-required="${LinkOrder}"/>								
					<button type="button" class="help-link" data-toggle="modal" data-target="#help-link" title="How to set Order"><i class="fa fa-question-circle fa-lg"></i></button>                 
					</div>
					<label class="control-label col-sm-2" for="linkTitleEg">
						<spring:message code="admin.qlFormTitleTextEng" /><span class="mand">*</span>
					</label>
					<div class="col-sm-4">
                    <c:set var="TitleEnglish" value="${command.getAppSession().getMessage('eip.admin.quicklink.TitleEnglish') }" />
		         <form:input id="linkTitleEg" path="linksMaster.linkTitleEg"  maxlegnth="100"
			         cssClass="form-control hasNotAllowSpecialLangVal"  data-rule-required="true" data-msg-required="${TitleEnglish}"/>																
							
					</div>

				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="linkTitleReg">
						<spring:message code="admin.qlFormTitleTextReg" /><span class="mand">*</span>
					</label>
					<div class="col-sm-4">
					 <c:set var="TitleRegional" value="${command.getAppSession().getMessage('eip.admin.quicklink.TitleRegional') }" />
                     <form:input id="linkTitleReg" path="linksMaster.linkTitleReg"  maxlegnth="120"
			         cssClass="form-control mandClassColor subsize"  data-rule-required="true" data-msg-required="${TitleRegional}"/>																
					</div>
					<label class="control-label col-sm-2">
						<spring:message code="admin.qlFormLinkType" /><span class="mand">*</span>
					</label>
					<div class="col-sm-4">
						<c:if test="${command.linkMasterDTO.temp == 'local'}">
							<label class="radio-inline"><form:radiobutton path="linkMasterDTO.linkRadioButton"
								id="linkRadioButton2" name="linkRadioButton2" value="external"
								onclick="chkLinkType()" />
							<spring:message code="admin.qlFormExternalLink" />
							</label>
							<label class="radio-inline">
							<form:radiobutton path="linkMasterDTO.linkRadioButton"
								id="linkRadioButton1" name="linkRadioButton1" checked="checked"
								onclick="chkLinkType();" value="${command.linkMasterDTO.temp}" />
							<spring:message code="admin.qlFormLocalLink" />
							</label>
						</c:if>
						<c:if test="${command.linkMasterDTO.temp == 'external'}">
							<label class="radio-inline"><form:radiobutton path="linkMasterDTO.linkRadioButton"
								id="linkRadioButton2" name="linkRadioButton2"
								value="${command.linkMasterDTO.temp}" checked="checked"
								onclick="chkLinkType()" />
							<spring:message code="admin.qlFormExternalLink" />
							</label>
							<label class="radio-inline">
							<form:radiobutton path="linkMasterDTO.linkRadioButton"
								id="linkRadioButton1" name="linkRadioButton1"
								onclick="chkLinkType();" value="local" />
							<spring:message code="admin.qlFormLocalLink" />
							</label>
						</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="linkPath">
						<spring:message code="admin.qlFormLink" /><span class="mand">*</span>
					</label>
					<div class="col-xs-4">
					<form:input id="exLink" path="linkMasterDTO.exLink" isReadonly="true"
							cssClass=" form-control col-xs-3 mandColorClass"/>					
						
						<form:hidden path="linksMaster.exLink" id="exLink2"/>
						<c:set var="LinkPath" value="${command.getAppSession().getMessage('eip.admin.quicklink.LinkPath') }" />
			          <form:input id="linkPath" path="linksMaster.linkPath" maxlegnth="100" 
							cssClass="form-control col-xs-9  mandColorClass"   data-rule-required="true" data-msg-required="${LinkPath}"/>				
					</div>
					
					<label class="control-label col-sm-2 required-control" for="isLinkModify"><spring:message
							code="admin.isHighlighted" text="Is Highlighted" /> :</label>
					<div class="col-sm-4">
						<c:set var="HighlightedMsg" value="${command.getAppSession().getMessage('eip.admin.quicklink.highlightedMsg') }"></c:set>
						<form:select cssClass=" form-control mandColorClass"
							path="linksMaster.isLinkModify" id="isLinkModify" data-rule-required="true" data-msg-required="${HighlightedMsg}">
							<form:option value="null"> <spring:message
							code="Select" text="Select" /></form:option>
							<form:option value="T"> <spring:message code="admin.true" text="True" /></form:option>
							<form:option value="F"> <spring:message code="admin.false" text="False" /></form:option>
						</form:select>	
						 <label id="error_msg" style="display:none; border:none;"></label>					
					</div>
				</div>
				
				<div class="form-group">
				<%-- <apptags:textArea labelCode="eip.cheker.remark" path="linksMaster.remark" cssClass="remark" maxlegnth="1000"></apptags:textArea> --%>
								
				<label class="col-sm-2 control-label"  for="entity.remark"><spring:message code="eip.cheker.remark mandColorClass" text="Checker Remark"/><span class="mand">*</span></label>
					<div class="col-sm-4">
						<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="linksMaster.remark" id="entityRemark" cssClass="form-control remark" maxlength="1000" 
						onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
							
						<label id="error_msg_cheker" style="display:none; border:none;"></label> 
							<div>
			 					<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
								<p class="charsRemaining" id="DescriptionCount"></p>
		    		 		</div>
					</div>
				</div>
				<div class="form-group padding-top-10" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                     <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton"/> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton"/><spring:message code="Reject" text="Reject" />
	                </div>					
                       
				</div>				
				
				<div class="text-center padding-top-10">
				 <input class="btn btn-success AdminFaqChekerbutton" type="button" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateLink(this,'', 'AdminQuickLink.html?AdminFaqCheker','save');" id="getFormId"></input>
				<input class="btn btn-success AdminFaqbutton" type="button" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateLink(this,'', 'AdminQuickLink.html','save');" id="getFormId"></input> 
				<%-- <apptags:submitButton
						entityLabelCode="eip.admin.master.quickLinkForm"
						isChildButton="false" successUrl="AdminQuickLink.html"
						cssClass="btn btn-success AdminFaqbutton" />
						<apptags:submitButton
						entityLabelCode="eip.admin.master.quickLinkForm"
						isChildButton="false" successUrl="AdminQuickLink.html?AdminFaqCheker"
						cssClass="btn btn-success AdminFaqChekerbutton" /> --%>
						
						<!-- <input type="reset" id="Reset" class="btn btn-warning" value="Reset"/> -->
				
					<c:if test="${command.mode eq 'A'}">
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormPub(frmAdminQuickLinkForm)" class="btn btn-warning"></input>
					</c:if>
					<input type="button" class="btn btn-danger" id="AdminFaqback"
						value="<spring:message code="portal.common.button.back" />"
						onclick="closebox('','AdminQuickLink.html')">
					<input type="button" class="btn btn-danger" id="AdminFaqChekerback"
						value="<spring:message code="portal.common.button.back" />"
						onclick="closebox('','AdminQuickLink.html?AdminFaqCheker')">	
				</div>
			</form:form>
		</div>
	</div>
</div>


<div class="modal fade" id="help-link" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">How to set Order</h4>
        </div>
        <div class="modal-body">
          <p>1.The Link order field accepts Decimal as well as Numbers.</p>
          <p>2.If we want to change the order of the links we can do it by inputting a number lesser than the previous one or vice versa. </p>
        </div>
      </div>
      
    </div>
  </div>
  
  