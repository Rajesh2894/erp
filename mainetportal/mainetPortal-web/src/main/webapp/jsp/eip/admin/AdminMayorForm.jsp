<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>

<script>

$('.datetimepicker').datepicker({
	yearRange: "-150:+0",
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true
	//minDate: 0
});
$(document).ready(function(){
	
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if(pathname.indexOf(txt) > -1 || txt2=="Y") {
	//	alert('String Contains Word');
		//$("#AdminFaqCheker").show()
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
		
		/* if($("input:radio:checked").val()=='Y')
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
			$(".AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			 $(".radiobutton").prop('disabled',true);
			 $(".radiobutton").removeClass('mandClassColor');
			 $(".radiobutton").addClass('disablefield');
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
		}
			
	
	 
});

function saveOrUpdateMayor(obj, message, successUrl, actionParam)
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
	if(mode == 'edit'){
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
	

//Transalator Function	   
$( document ).ready(function() {
	   
	   
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$("#pNameEn").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#pNameEn").val().trim();
				if(no_spl_char!='')
				{
					//alert('world');
					commonlanguageTranslate(no_spl_char,'pNameReg',event,'');
				}else{
					$("#pNameReg").val('');
				}
			});
			
			$("#designationEn").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#designationEn").val().trim();
				if(no_spl_char!='')
				{
					//alert('world');
					commonlanguageTranslate(no_spl_char,'designationReg',event,'');
				}else{
					$("#designationReg").val('');
				}
			});
			
			$("#linkTitleEn").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#linkTitleEn").val().trim();
				if(no_spl_char!='')
				{
					//alert('world');
					commonlanguageTranslate(no_spl_char,'linkTitleReg',event,'');
				}else{
					$("#linkTitleReg").val('');
				}
			});
			
			$("#profileEn").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#profileEn").val().trim();
				if(no_spl_char!='')
				{
					//alert('world');
					commonlanguageTranslate(no_spl_char,'profileReg',event,'');
				}else{
					$("#profileReg").val('');
				}
			});
			
			
		}
});

$(function() {
	$("#frmAdminMayorForm").validate();
});
function resetForm(resetBtn) {
	resetFormOnAdd(frmAdminMayorForm);
    var url	=	'AdminMayorForm.html'+'?cleareFile';
    var response= __doAjaxRequest(url,'post',{},false);
	return false; 
}
$('textarea.form-control.remark').on('blur', function() { 
	   $(this).parent().switchClass("has-error","has-success");
	   $(this).removeClass("shake animated");
	   $('#error_msg_cheker').css('display','none');
	   $('#error_msg_cheker').remove();
	     
	});  
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<spring:message code="mayorGrid.Title" var="CMTitle"/>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
			 <strong><spring:message code="mayorGrid.Title" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
		<div class="mand-label clearfix"><span><spring:message code="MandatoryMsg" /></span></div>
			<form:form method="post" action="AdminMayorForm.html"
				name="frmAdminMayorForm" id="frmAdminMayorForm"
				class="form-horizontal">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="entity.chekkerflag1" id="chekkerflag1"/>
				<input type ="hidden" id ="mode" value="${command.mode }"/>
				<form:hidden path="isChecker" id="isCheckerFlag"/>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="pNameEn"><spring:message code="mayorForm.Name" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					<c:set var="NameEnglish" value="${command.getAppSession().getMessage('eip.admin.mayorForm.NameEnglish') }" />
						<form:input path="profileMaster.pNameEn" id="pNameEn" 
							maxlegnth="100"
							cssClass=" form-control hasCharacterwithdot subsize"
							disabled="false" data-rule-required="true" data-msg-required="${NameEnglish}"/>
					</div>
					<label class="col-sm-2 control-label" for="pNameReg"><spring:message code="mayorForm.Name" /> <spring:message code="Regional" text="(Hindi)" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					   <c:set var="NameRegional" value="${command.getAppSession().getMessage('eip.admin.mayorForm.NameRegional') }" />
						<form:input path="profileMaster.pNameReg" id = "pNameReg"
							maxlegnth="1000" cssClass="form-control mandClassColor subsize"
							 disabled="false" data-rule-required="true" data-msg-required="${NameRegional}"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="designationEn"><spring:message code="mayorForm.Designation" text="Designation"/><span class="mand">*</span></label>
					<div class="col-sm-4">
					    <c:set var="DesignationEnglish" value="${command.getAppSession().getMessage('eip.admin.mayorForm.DesignationEnglish') }" />
						<form:input path="profileMaster.designationEn" id= "designationEn"
							maxlegnth="100" cssClass="form-control mandClassColor subsize"
							 disabled="false" data-rule-required="true" data-msg-required="${DesignationEnglish}"/>
					</div>
					<label class="col-sm-2 control-label" for="designationReg"><spring:message code="mayorForm.Designation" text="Designation" /> <spring:message code="Regional" text="(Hindi)" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					    <c:set var="DesignationRegional" value="${command.getAppSession().getMessage('eip.admin.mayorForm.DesignationRegional') }" />
						<form:input path="profileMaster.designationReg" id = "designationReg"
							maxlegnth="1000" cssClass="form-control mandClassColor subsize"
							 disabled="false" data-rule-required="true" data-msg-required="${DesignationRegional}"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="linkTitleEn"><spring:message code="mayorForm.Title"/><span class="mand">*</span></label>
					<div class="col-sm-4">
					     <c:set var="TitleEnglish" value="${command.getAppSession().getMessage('eip.admin.mayorForm.TitleEnglish') }" />
						<form:input path="profileMaster.linkTitleEn" id = "linkTitleEn"
							maxlegnth="150" cssClass="form-control mandClassColor subsize"
							 disabled="false" data-rule-required="true" data-msg-required="${TitleEnglish}"/>
					</div>
					<label class="col-sm-2 control-label" for="linkTitleReg"><spring:message code="mayorForm.Title" /><spring:message code="Regional" text="(Hindi)" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					    <c:set var="TitleRegional" value="${command.getAppSession().getMessage('eip.admin.mayorForm.TitleRegional') }" />
						<form:input path="profileMaster.linkTitleReg" id = "linkTitleReg"
							maxlegnth="1000" cssClass="form-control mandClassColor subsize"
							 disabled="false" data-rule-required="true" data-msg-required="${TitleRegional}"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="profileEn"><spring:message code="mayorForm.Profile" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					    <c:set var="ProfileEnglish" value="${command.getAppSession().getMessage('eip.admin.mayorForm.ProfileEnglish') }" />
						<form:input path="profileMaster.profileEn" id = "profileEn"
							maxlegnth="2000" cssClass="form-control mandClassColor subsize"
							 disabled="false" data-rule-required="true" data-msg-required="${ProfileEnglish}"/>
					</div>
					<label class="col-sm-2 control-label" for="profileReg"><spring:message code="mayorForm.Profile" /> <spring:message code="Regional" text="(Hindi)" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					    <c:set var="ProfileRegional" value="${command.getAppSession().getMessage('eip.admin.mayorForm.ProfileRegional') }" />
						<form:input path="profileMaster.profileReg" id = "profileReg"
							maxlegnth="2000" cssClass="form-control mandClassColor subsize"
							 disabled="false" data-rule-required="true" data-msg-required="${ProfileRegional}"/>
					</div>
				</div>
                <div class="form-group">
                  <label class="col-sm-2 control-label " for="emailId"><spring:message code="Email" text="Emailid" /></label>
                   <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.emailId" maxlegnth="4000" cssClass="form-control"
					hasId="true" isDisabled="" /></div>
               
					<label class="col-sm-2 control-label" for="summaryEng"><spring:message code="mayorForm.msg" /></label>
					<div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.summaryEng"  cssClass="form-control hasFaxNo"
					hasId="true" isDisabled="" />
						<%-- <form:textarea path="profileMaster.summaryEng" maxlength="2000"
							cssClass="form-control mandClassColor subsize" /> --%>
					</div>
					
				</div>
				<c:set var="currCount" value="0" />
				<div class="form-group">
				<c:choose>
					<c:when test="${command.mode eq 'A'}">
						
							<label class="col-sm-2 control-label" for="profileMaster.imageName"><spring:message code="mayorForm.ProfileImage" text="Profile Image" /> <span class="mand">*</span></label>
							<div class="col-sm-4">
								<c:set var="errmsg">
									<spring:message code="file.upload.size" />
								</c:set>
								<apptags:formField fieldType="7" hasId="true"
									fieldPath="profileMaster.imageName" isMandatory="false"
									labelCode="" currentCount="${currCount}"
									showFileNameHTMLId="true" folderName="EIP_HOME"
									fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
								<p class="help-block text-small"><spring:message code="eip.mayor.image.size" />	</p>
								<p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>	
							</div>
						

					</c:when>

					<c:otherwise>
					
						 <label class="col-sm-2 control-label">
						 	<spring:message code="mayorForm.ProfileImage" />
						 </label>
						 <div class="col-sm-4"> 
		 		         	<img alt="Mayor" src="./${command.imagesDetail}" class="img-thumbnail" style="height:150px">
		 		         	
		 		         	<apptags:formField fieldType="7"
									fieldPath="profileMaster.imageName" labelCode="" hasId="true"
									isMandatory="false" fileSize="COMMOM_MAX_SIZE"
									currentCount="${currCount}" showFileNameHTMLId="true"
									folderName="EIP_HOME" maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
								<p class="help-block text-small"><spring:message code="eip.mayor.image.size" />	</p>
								<p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>
							<%-- 	<apptags:filedownload filename="${command.image}" filePath="${command.imagesDetail}" actionUrl="AdminCommissioner.html?Download"></apptags:filedownload> --%>
		 		         </div>
		 		   
					</c:otherwise>
				</c:choose>
				
				 <label class="control-label col-sm-2">
					<spring:message code="admin.commissioner.doj" text ="Date Of Joining" />
				</label>
				<div class="col-sm-4">
					 <apptags:dateField cssClass="form-control " datePath="profileMaster.dtOfJoin" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				
				</div>
				</div>
				<%-- <div class="form-group">
				<apptags:textArea labelCode="eip.cheker.remark" path="profileMaster.remark" cssClass="remark" maxlegnth="1000"></apptags:textArea>
				</div> --%>				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="profileMaster.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="profileMaster.remark" id = "profileMasterRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')" data-rule-required="true" data-msg-required="${cheker}"/>
				<label id="error_msg_cheker" style="display:none; border:none;"></label> 
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
				</div>
				<div class ="form-group" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                      
                        <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton" aria-label="Authenticate"/> <spring:message code="Authenticate" text="Authenticate" />
                         <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton" aria-label="Reject"/><spring:message code="Reject" text="Reject" />
	                </div>					
				</div>
				
				

				<div class="text-center padding-top-10">
				 <input class="btn btn-success AdminFaqChekerbutton" type="button" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateMayor(this,'', 'AdminMayor.html?AdminFaqCheker','save');" id="getFormId"></input>
				<input class="btn btn-success AdminFaqbutton" type="button" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateMayor(this,'', 'AdminMayor.html','save');" id="getFormId"></input>
						<%-- <apptags:submitButton entityLabelCode="${CMTitle}"
							isChildButton="false" successUrl="AdminMayor.html"
							cssClass="btn btn-success AdminFaqbutton" />
							<apptags:submitButton entityLabelCode="${CMTitle}"
							isChildButton="false" successUrl="AdminMayor.html?AdminFaqCheker"
							cssClass="btn btn-success AdminFaqChekerbutton" /> --%>
						<c:if test="${command.mode eq 'A'}">
			<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetForm(this)" class="btn btn-warning"></input>
			</c:if>
					<a href="AdminMayor.html"  id="AdminFaqback"class="btn btn-danger"><spring:message code="bckBtn" text="Back"/></a>	
					<a href="AdminMayor.html?AdminFaqCheker" id="AdminFaqChekerback" class="btn btn-danger"><spring:message code="bckBtn" text="Back"/></a>					
				</div>

			</form:form>
		</div>
	</div>
</div>