<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<script  src="js/mainet/file-upload.js"></script>
<script  src="js/mainet/validation.js"></script>
<script >

$("#deleteImg").click(function() {
	
	$('#imgThumbId').remove();
	$('#imgName').val('');
	$('#imgPath').val('');
	
});
$(document).ready(function(){
	
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#chekkerflag1").val();
	if(pathname.includes(txt) || txt2=="Y") {

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
	
	var sliderOrImgFlagId = $('#sliderOrLogoFlag').val() ;
	if(sliderOrImgFlagId !=undefined && sliderOrImgFlagId == 'S'){
		$('.error-div').hide();
	}
});


function saveOrUpdateEIPHomepage(obj, message, successUrl, actionParam)
{
	
	var sliderOrImgFlagId = $('#sliderOrLogoFlag').val() ;
	if(sliderOrImgFlagId !=undefined && sliderOrImgFlagId == 'S'){
	var	formName =	findClosestElementId($('#getFormId'), 'form');
	var theForm	=	'#'+formName;
	var requestData = {};		
	requestData = __serializeForm(theForm);

	var ajaxResponse = __doAjaxRequest('EIPHomeImages.html?validateEipHomePage', 'post', requestData,
			false, 'json');
	
	if (ajaxResponse.length > 0) {
		
	$(".error-div").show();
		showError(ajaxResponse);
		return false;
	} 
	}
	var errorList = [];
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#chekkerflag1").val();
	if(pathname.indexOf(txt) > -1 || txt2=="Y") {
		 if ($('.remark').val()==null || $('.remark').val()==""){
			 errorList.push(getLocalMessage('eip.admin.validatn.cheker')); 
			 $('#error_msg_cheker').addClass('error');
		     $('#error_msg_cheker').css('display','block');
		     $('#error_msg_cheker').html(getLocalMessage('eip.admin.validatn.cheker'));
		     return;
			}
		} 
	var successMessage ='';
	var addMode = $('#addMode').val() ;
	var appOrRejFlag =  $("input[name='entity.makkerchekerflage']:checked").val();
	if(addMode != undefined && addMode == "true"){
		 successMessage = getLocalMessage('admin.save.successmsg');
	}else if(addMode == "false" ){
		
		if(appOrRejFlag!= undefined && appOrRejFlag == 'Y' && document.URL.indexOf('AdminFaqCheker') > -1){
			  successMessage = getLocalMessage('admin.approve.successmsg');
		}else if(appOrRejFlag!= undefined && appOrRejFlag == 'N' &&  document.URL.indexOf('AdminFaqCheker') > -1){
			  successMessage = getLocalMessage('admin.reject.successmsg');
		}else{
		      successMessage = getLocalMessage('admin.update.successmsg');
		}
	
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

function resetFormOnAdd(obj)
{
	//$('.error-div').remove();
	//$('.alert').remove();
	closeOutErrBox()
	$('[id*=file_list]').html('');
    $(':input',obj)
    .not(':button, :submit, :reset, :hidden')
    .val('')
    .removeAttr('checked')
    .removeAttr('selected');
    $('select').val(0);
    var url	=	'EIPHomeImages.html'+'?cleareFile';
    
    var response= __doAjaxRequest(url,'post',{},false);
	return false; 
}

$( document ).ready(function() {
	   
	   
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$("#caption").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#caption").val().trim();
				if(no_spl_char!='')
				{
					commonlanguageTranslate(no_spl_char,'captionReg',event,'');
				}else{
					$("#captionReg").val('');
				}
			});
		}
});

$(function() {
	$("#frmMasterForm").validate();
});

$('select.form-control.mandColorClass').on('blur', function() { 
    var check = $(this).val();
    var validMsg =$(this).attr("data-msg-required");
    var optID=$(this).attr('id');
    var fildID="#"+optID+"_error_msg";
    if(check == '' || check == '0'){
   		 $(this).parent().switchClass("has-success","has-error");
		     $(this).addClass("shake animated");
		     $(fildID).addClass('error');
		     $(fildID).css('display','block');
		     $(fildID).html(validMsg);
	}else
    {$(this).parent().switchClass("has-error","has-success");
    $(this).removeClass("shake animated");
    $(fildID).css('display','none');}
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
<div class="widget animated">
    <div class="widget-header">
      <h2><spring:message code="eipAdminHomeImages.breadcrumb"/></h2>
       <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> </div>
    </div>
  
<div class="widget-content padding">
     <div class="mand-label">
					<span><spring:message code="MandatoryMsg" text="MandatoryMsg" /></span>
				</div>
				 <input type ="hidden" value ="${command.addMode}" id ="addMode"/>	
  <c:choose>
    <c:when test="${command.flag eq 'S'}">
      <form:form action="EIPHomeImages.html" name="frmMasterForm" id="frmMasterForm" class="form-horizontal">
     <div class="error-div alert alert-danger alert-dismissible"> 
        <jsp:include page="/jsp/tiles/validationerror.jsp" />
        </div>
       
       <form:hidden path="entity.chekkerflag1" id="chekkerflag1"/>
       <input type ="hidden" value ="${command.mode}" id ="mode"/>	
       <form:hidden path="isChecker" id="isCheckerFlag"/>
             
        <input type="hidden" id="getFormId">
        
          <c:if test="${command.addMode}">
            <div class="form-group">
              <label class="col-sm-2 control-label required-control" for="entity.hmImgOrder"><spring:message code="eipAdminHomeImages.imageOrder" /></label>
              <c:set var="maxfilecount">
              <spring:message code="landingPage.maxImages" />
              </c:set>
              <div class="col-sm-4">
              <c:set var="SerialNoMsg" value="${command.getAppSession().getMessage('eip.admin.homePage.serialNo') }"></c:set>
              <form:select path="entity.hmImgOrder" class="form-control mandColorClass" id="SerialNoAdd" data-rule-required="true" data-msg-required="${SerialNoMsg}">
                <form:option value="0">
                  <spring:message code="eipAdminHomeImages.selectImageOrder" />
                </form:option>
               
                <c:forEach begin="1" end="${maxfilecount}" var="i">
                 <c:if test="${fn:containsIgnoreCase(command.existingSequences, i) eq false}">
                  <form:option value="${i}">${i}</form:option>
                  </c:if>
                </c:forEach>
              </form:select>
              <label id="SerialNoAdd_error_msg" style="display:none; border:none;"></label>
              </div>
              
               </div>
               <div class="form-group">
               		<apptags:input labelCode="eipAdminHomeImages.caption" path="entity.caption" maxlegnth="50"></apptags:input>
               		<apptags:input labelCode="eipAdminHomeImages.caption.reg" path="entity.captionReg" cssClass="hasSpecialCharAndNumber" maxlegnth="50"></apptags:input>
               </div>
               
          </c:if>
      
       
        <c:choose>
          <c:when test="${command.addMode}">
            <c:set var="currCount" value="0" />
            <div class="form-group">
              <label class="col-sm-2 control-label" for="entity.imageName"><spring:message code="mayorForm.ProfileImage" text="Profile Image" /><span class="mand">*</span></label>
              <div class="col-sm-4">
                <c:set var="errmsg">
                <spring:message code="file.upload.size" />
                </c:set>
                <apptags:formField fieldType="7" hasId="true"
									fieldPath="entity.imageName" isMandatory="false" labelCode=""
									currentCount="${currCount}" showFileNameHTMLId="true"
									folderName="EIP_HOME" fileSize="COMMOM_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
									cssClass="form-control" />
                <p class="help-block text-small"><spring:message code="eip.home.image.size" /> </p>
                <p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>	
              </div>

									<label class="col-sm-2 control-label"> <spring:message
											code="mobile.check.visible" text="Mobile Visibility" />
									</label>

									<div class="col-sm-4">
									  <form:checkbox path="entity.mobileEnable" value="Y" />
									</div>
								</div>
             <div class="form-group">
		<%-- 	 <apptags:textArea labelCode="eip.cheker.remark" path="entity.remark" maxlegnth="1000" cssClass="remark"></apptags:textArea>   --%>          
            <label class="col-sm-2 control-label" for="entity.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="entity.remark" id = "entityRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
             </div>
             <div class ="form-group" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                        <!-- <label class="checkbox-inline" for="chekkerflag1"> -->
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="Y" class="radiobutton" /> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="N" class="radiobutton" /> <spring:message code="Reject" text="Reject" />
                      
                    </div>					
				</div>
          </c:when>
          <c:otherwise>
          <c:choose>
          <c:when test="${command.mode eq 'edit'}">
          <c:set var="currCount" value="0" />
          <div class="form-group">
              <label class="col-sm-2 control-label required-control" for="entity.hmImgOrder"><spring:message code="eipAdminHomeImages.imageOrder" /></label>
              <c:set var="maxfilecount">
              <spring:message code="landingPage.maxImages" />
              </c:set>
              <div class="col-sm-4">
              <c:set var="SerialNoMsg" value="${command.getAppSession().getMessage('eip.admin.homePage.serialNo') }"></c:set>
              <form:select path="entity.hmImgOrder" class="form-control mandColorClass" id="SerialNoEdit" data-rule-required="true" data-msg-required="${SerialNoMsg}">
                <form:option value="0">
                  <spring:message code="eipAdminHomeImages.selectImageOrder" />
                </form:option>
                <c:forEach begin="1" end="${maxfilecount}" var="i">
                  <c:if test="${ (  command.entity.hmImgOrder eq i) or  ( fn:containsIgnoreCase(command.existingSequences, i) eq false) }">
                  <form:option value="${i}">${i}</form:option>
                  </c:if>
                </c:forEach>
              </form:select>
              <label id="SerialNoEdit_error_msg" style="display:none; border:none;"></label>
              </div>
               </div>
               <div class="form-group">
               		<apptags:input labelCode="eipAdminHomeImages.caption" path="entity.caption" maxlegnth="50"></apptags:input>
               		<apptags:input labelCode="eipAdminHomeImages.caption.reg" path="entity.captionReg" maxlegnth="50"></apptags:input>
               </div>
               
          <div class="form-group">
              <label class="col-sm-2 control-label "> <spring:message code="eipAdminHomeImages.imagesaveLabel" /></label>
              <div class="col-sm-4">
              
              <c:if test ="${ not empty  command.imagesDetail}">
              <div class="card border-light" id ="imgThumbId">
               <img alt="Organisation Logo" src="./${command.imagesDetail}" class="img-thumb"> 
               <div class="card-header1">
               <i class="fa fa-picture-o red-thumb" aria-hidden="true">
               </i><div class="file-name"> <span>${stringUtility.getStringAfterChar('\\',command.imagesDetail)}</span></div><a href="#" class="close1" onclick="" id="deleteImg" title="Close">
               <i class="fa fa-times" aria-hidden="true" id ="deleteImg"></i></a></div></div>
               <form:hidden path="entity.imageName" id ="imgName"/>
               <form:hidden path="entity.imagePath" id ="imgPath"/>
               </c:if>
                <apptags:formField fieldType="7" hasId="true"
									fieldPath="entity.imageName" isMandatory="false" labelCode=""
									currentCount="${currCount}" showFileNameHTMLId="true"
									folderName="EIP_HOME" fileSize="COMMOM_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
									cssClass="form-control" />
                <p class="help-block text-small"><spring:message code="eip.home.image.size" /> </p>
                <p class="help-block text-small"><spring:message code="eip.announce.fileUploadText"/></p>	
                </div>
                <%-- <apptags:textArea labelCode="eip.cheker.remark" path="entity.remark" maxlegnth="1000" cssClass="remark"></apptags:textArea> --%>
                	<label class="col-sm-2 control-label"> <spring:message
											code="mobile.check.visible" text="Mobile Visibility" />
									</label>

									<div class="col-sm-4">
									  <form:checkbox path="entity.mobileEnable" value="Y" />
									</div>
            </div>
          	   <div class="form-group">
          	   
                <label class="col-sm-2 control-label"  for="entity.remark"><spring:message code="eip.cheker.remark" text="Checker Remark"/><span class="mand">*</span></label>
                
                <div class="col-sm-4">
                	<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
                	<form:textarea path="entity.remark" id="entityRemark" maxlegnth="1000" cssClass="form-control remark mandColorClass" 
                	onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')" data-rule-required="true" data-msg-required="${cheker}"/>
               		<label id="error_msg_cheker" style="display:none; border:none;"></label> 
               		 <div>
			 			<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
						<p class="charsRemaining" id="DescriptionCount"></p>
		    		 </div>
		    	</div>	 
          	   </div>
             <div class ="form-group padding-top-10" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center"> 
                       
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="Y" class="radiobutton" /> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="N" class="radiobutton" /> <spring:message code="Reject" text="Reject" />
                      
                    </div>					
				</div>
          </c:when>
          <c:otherwise>
           <div class="form-group">
            <label class="col-sm-2 control-label required-control" for="entity.hmImgOrder"><spring:message code="eipAdminHomeImages.imageOrder" /></label>
            <div class="col-sm-4">
              <apptags:inputField fieldPath="entity.hmImgOrder" isReadonly="true" cssClass="form-control"></apptags:inputField>
            </div>
            </div>
            <div class="form-group" id="divid">
             <c:if test="${not empty command.imagesDetail}">
              <label class="col-sm-2 control-label "> <spring:message code="eipAdminHomeImages.imagesaveLabel" /></label>
              <div class="col-sm-4"> <img src="./${command.imagesDetail}" class="img-responsive" alt="sliderImage" style="height:150px"> </div>
              </c:if>
            </div>
             <div id="showupload"  class="form-elements clear">
 		     <c:set var="currCount" value="0"/> 
               <apptags:formField fieldType="7" fieldPath="profileMaster.imageName" labelCode="mayorForm.ProfileImage" currentCount="${currCount}" showFileNameHTMLId="true" folderName="EIP_HOME" fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT" validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"/>
 		       </div>
          </c:otherwise>
         </c:choose>
          </c:otherwise>
        </c:choose>
      
        <div class="btn_fld margin_top_10 clear">
          <c:choose>
            <c:when test="${command.addMode}">
              <div class="text-center padding-bottom-10">
               <input class="btn btn-success AdminFaqChekerbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html?AdminFaqCheker','');"></input>
	            <input class="btn btn-success AdminFaqbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html','');"></input>	
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormOnAdd(frmMasterForm)" class="btn btn-warning"></input>

             
                 <a href="EIPHomeImagesSearch.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" text="Back"/></a>
				 <a href="EIPHomeImagesSearch.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback" ><spring:message code="bckBtn" text="Back"/></a>
              </div>
            </c:when>
            <c:otherwise>
              <div class="text-center padding-bottom-10">
                  <c:if test="${command.mode eq 'edit'}">
                   <input class="btn btn-success AdminFaqChekerbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html?AdminFaqCheker','');"></input>
	            <input class="btn btn-success AdminFaqbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html','');"></input>	
                </c:if>
              	 <a href="EIPHomeImagesSearch.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" text="Back"/></a>
				 <a href="EIPHomeImagesSearch.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback" ><spring:message code="bckBtn" text="Back"/></a>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
        <form:hidden path="entity.moduleType" value="${command.flag}" id ="sliderOrLogoFlag" />
      </form:form>
    </c:when>
    <c:otherwise>
      <form:form action="EIPLogoImages.html" name="frmMasterForm" id="frmMasterForm" class="form-horizontal">
       
        <div class="error-div"> 
       		 <jsp:include page="/jsp/tiles/validationerror.jsp" />
      </div>
        <c:if test="${command.addMode}">
          <div class="form-group">
            <label class="col-sm-2 control-label required-control" for="entity.hmImgOrder">
              <spring:message code="eipAdminHomeImages.imageSide" /></label>
            <div class="col-sm-4">
              <c:set var="ImgSizeMsg" value="${command.getAppSession().getMessage('eip.admin.homePage.imgSide') }"></c:set>
              <form:select path="entity.hmImgOrder" class="form-control mandColorClass" id="ImgSizeAdd" for="entity.hmImgOrder" data-rule-required="true" data-msg-required="${ImgSizeMsg}">
                <form:option value="0">
                  <spring:message code="eipAdminHomeImages.selectLogoOrder" />
                </form:option>
                <form:option value="1">Left</form:option>
                <form:option value="2">Right</form:option>
              </form:select>
              <label id="ImgSizeAdd_error_msg" style="display:none; border:none;"></label>
            </div>
			 <%--  <apptags:textArea labelCode="eip.cheker.remark" path="entity.remark" cssClass="remark"></apptags:textArea> --%>          
           <label class="col-sm-2 control-label" for="entity.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="entity.remark" id = "entityRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
           
          </div>
        </c:if>
        <c:if test="${!command.addMode}">
      <c:choose>
         <c:when test="${command.mode eq 'edit'}">
           <div class="form-group">
            <label class="col-sm-2 control-label required-control" for="entity.hmImgOrder">
              <spring:message code="eipAdminHomeImages.imageSide" /></label>
            <div class="col-sm-4">
              <c:set var="ImgSizeMsg" value="${command.getAppSession().getMessage('eip.admin.homePage.imgSide') }"></c:set>
              <form:select path="entity.hmImgOrder" class="form-control mandColorClass" id="ImgSizeEdit" for="entity.hmImgOrder" data-rule-required="true" data-msg-required="${ImgSizeMsg}">
                <form:option value="0">
                  <spring:message code="eipAdminHomeImages.selectLogoOrder" />
                </form:option>
                <form:option value="1">Left</form:option>
                <form:option value="2">Right</form:option>
              </form:select>
               <label id="ImgSizeEdit_error_msg" style="display:none; border:none;"></label>
            </div>
			<%-- <apptags:textArea labelCode="eip.cheker.remark" path="entity.remark" cssClass="remark"></apptags:textArea>   --%>        
           <label class="col-sm-2 control-label" for="entity.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="entity.remark" id = "entityRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
           
          </div>
          </c:when>
          <c:otherwise>
          <div class="form-group">
            <label class="col-sm-2 control-label required-control" for="entity.hmImgOrder"><spring:message code="eipAdminHomeImages.imageSide" /></label>
             <div class="col-sm-4"><apptags:inputField fieldPath="entity.hmImgOrder" cssClass="form-control" isReadonly="true"></apptags:inputField></div>
            
			<script>
			$(document) .ready(
				function() {
					if ('${command.entity.hmImgOrder}' == "1")
						$("input[name='entity.hmImgOrder']") .val('Left');
					else
						$("input[name='entity.hmImgOrder']") .val('Right');
				});
			</script> 
          </div>
          </c:otherwise>
          </c:choose>
          </c:if>
        <c:choose>
          <c:when test="${command.addMode}">
            <c:set var="currCount" value="0" />
            <div class="form-group">
             <label class="col-sm-2 control-label" for="entity.imageName"><spring:message code="upload.logo" text="Upload Logo"/> <span class="mand">*</span></label>
              <div class="col-sm-4">
              	<apptags:formField fieldType="7" fieldPath="entity.imageName"
								labelCode="eipAdminHomeImages.imageupload"
								currentCount="${currCount}" showFileNameHTMLId="true"
								folderName="EIP_HOME" fileSize="COMMOM_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
				  <p class="help-block text-small"><spring:message code="eip.mayor.image.size" />  </p>				
				  <p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>				
            </div>
            </div>
            <div class ="form-group" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                       
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="Y" class="radiobutton"/> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="N" class="radiobutton"/> <spring:message code="Reject" text="Reject" />
                      
                    </div>					
				</div>
          </c:when>
          <c:otherwise>
          <c:set var="currCount" value="0" />
            <div class="form-group">
              <label class="col-sm-2 control-label"><spring:message code="eipAdminHomeImages.imagesaveLabel" /><span class="mand">*</span></label>
              <div class="col-sm-4"> 
               <c:if test ="${ not empty  command.imagesDetail}">
              <div class="card border-light" id ="imgThumbId">
               <img alt="Organisation Logo" src="./${command.imagesDetail}" class="img-thumb"> 
               <div class="card-header1">
               <i class="fa fa-picture-o red-thumb" aria-hidden="true">
               </i><div class="file-name"> <span>${stringUtility.getStringAfterChar('\\',command.imagesDetail)}</span></div><a href="#" class="close1" onclick="" id="deleteImg" title="Close">
               <i class="fa fa-times" aria-hidden="true" id ="deleteImg"></i></a></div></div>
               <form:hidden path="entity.imageName" id ="imgName"/>
               <form:hidden path="entity.imagePath" id ="imgPath"/>
               </c:if>
            
              
              <apptags:formField fieldType="7" fieldPath="entity.imageName"
								labelCode="eipAdminHomeImages.imageupload"
								currentCount="${currCount}" showFileNameHTMLId="true"
								folderName="EIP_HOME" fileSize="COMMOM_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
				  <p class="help-block text-small"><spring:message code="eip.mayor.image.size" />  </p>				
				  <p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>	
			  </div>	  
            </div>
              <div class ="form-group" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                      
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="Y" class="radiobutton"  /> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.makkerchekerflage" name="radiobutton" value="N" class="radiobutton"  /> <spring:message code="Reject" text="Reject" />
                      
                    </div>					
				</div>
          </c:otherwise>
        </c:choose>
          <c:choose>
            <c:when test="${command.addMode}">
              <div class="text-center padding-bottom-10">
               <input class="btn btn-success AdminFaqChekerbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html?AdminFaqCheker','');"></input>
	            <input class="btn btn-success AdminFaqbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html','');"></input>	
				
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormOnAdd(frmMasterForm)" class="btn btn-warning"></input>
					
                 <a href="EIPHomeImagesSearch.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" text="Back"/></a>
				 <a href="EIPHomeImagesSearch.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback" ><spring:message code="bckBtn" text="Back"/></a>
              </div>
            </c:when>
            <c:otherwise>
              <div class="text-center padding-bottom-10">
                 <c:if test="${command.mode eq 'edit'}">
                  <input class="btn btn-success AdminFaqChekerbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html?AdminFaqCheker','');"></input>
	            <input class="btn btn-success AdminFaqbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateEIPHomepage('','', 'EIPHomeImagesSearch.html','');"></input>	
			
                </c:if>
                  <a href="EIPHomeImagesSearch.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" text="Back"/></a>
				 <a href="EIPHomeImagesSearch.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback" ><spring:message code="bckBtn" text="Back"/></a>
              </div>
            </c:otherwise>
          </c:choose>
        <form:hidden path="entity.moduleType" value="${command.flag}" />
         <form:hidden path="entity.chekkerflag1" id="chekkerflag1"/>
                      <input type ="hidden" value ="${command.mode}" id ="mode"/>	
        <input type="hidden" id="getFormId">
      </form:form>
    </c:otherwise>
  </c:choose>
</div>
</div></div>