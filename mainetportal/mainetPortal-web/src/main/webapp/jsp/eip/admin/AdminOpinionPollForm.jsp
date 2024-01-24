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
<script  src="assets/libs/google-translator/jsapi.js"></script>

<script>
	$(document).ready(function() {
		
		 $('#adminMakerButton').hide()
		 $('#adminChekerButton').hide()
		 
		 
		var dateFields = $('.date,.datepicker');
		dateFields.each(function () {
			
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});

		$('.datepicker').datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			minDate: 0
		});
		
		var d = new Date();
		var strDate = d.getFullYear() + "/" + (d.getMonth()+1) + "/" + d.getDate();
		$('.datetimepicker').datetimepicker({
			dateFormat: 'dd/mm/yy',
			timeFormat: "hh:mm tt",
			changeMonth: true,
			changeYear: true,
			minDate: new Date(strDate)
		});
		
		fileUploadValidationList();

		});

	function deleteUploadFileFromEIP(obj,fileName,cnt,del)
	{
		var formName	=	findClosestElementId(obj,'form');
	    var theForm	=	'#'+formName;
		var url	=	$(theForm).attr('action');
		url=url.split("?");
		var data = "fileName="+fileName+"&del="+del;
		var mainUrl=url[0]+'?DeleteImage';
		__doAjaxRequest(mainUrl, 'post', data , false,'json');
	    if(del=='image'){
		 	$("#id_"+cnt).hide(); 
		}else if(del=='video'){
	   		$("#videoId_"+cnt).hide();
		}else{
	  	 $("#filid_"+cnt).remove();
		}
		$("#fileupload").show();
		
	}
	
	function saveOrUpdateNotice(obj, message, successUrl, actionParam)
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
		
	
	function saveOrUpdateNoticeValidate(obj, message, successUrl, actionParam)
	{
	
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+'frmAdminOpinionPollForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData ='';
		 $.ajax({
				url : 'AdminOpinionPollForm.html?validateSavePool',
				data : requestData,
				type : 'post',
				async : false,
				dataType : '',
				//headers   : {"SecurityToken": token},
				success : function(response) {
					returnData = response;
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
		 
	          if (returnData.length > 0) {
	        	  
				displayErrorsOnPage(returnData);
				$(".error-div").addClass('alert alert-danger alert-dismissible');
				
			}else{
				 
				 var pathname  = document.URL ;
					debugger
					var txt ="AdminFaqCheker";
					//if(pathname.indexOf(txt) > -1 ) {
				if(document.URL.indexOf('AdminFaqCheker') > -1 ) {
						$('#adminChekerButton').click();
					}else{
						$('#adminMakerButton').click();
					}
			}
	}

	function resetFormPub(obj)
	{
		$('.error-div').remove();
	   $(':input','#frmAdminOpinionPollForm')
	    .not(':button, :submit, :reset, :hidden')
	    .val('')
	    .removeAttr('checked')
	    .removeAttr('selected');
	   $('select').prop('selectedIndex',0);
		return false; 
		
	}

</script>
<script>

$( ".document" ).on( "change", function( event ) {
	   $(".documentDiv").remove();
	}); 

$( ".docImage" ).on( "change", function( event ) {
	   $(".uploadImageDiv").remove();
	}); 

$(document).ready(function(){
	var pathname  = document.URL ;
	
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if(pathname.indexOf(txt) > -1 || txt2=="Y") {
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
		}
	   else {
				$(".remark").prop("disabled",true);
				$("#chekkerflag1").val("N");
				$(".AdminFaqbutton").show();
				$(".AdminFaqChekerbutton").hide();
				$("#AdminFaqChekerback").hide();
				$("#AdminFaqback").show();
				$(".radiobutton").prop('disabled',true);
				$(".radiobutton").removeClass('mandClassColor');
				$(".radiobutton").addClass('disablefield');
		} 
});

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
	  	 var maxlength=val.getAttribute("maxlength");
	  	
	      var len = val.value.length;
	   $('.charsRemaining').next('P').text(maxlength - len);
	     
	    }; 
  function Delete(val) {
	  $('.fa-download').hide();
	  
	  var url	=	'';
		
	    url = 'AdminOpinionPollForm.html?delete';
	 	var jsonResponse	=	 __doAjaxRequest(url,'get',"",false,'');

	  }; 


	  function resetFormOnAdd(obj)
	  {
		  $(".document").show();
	  	$('.error-div').remove();
	  	$('.alert').remove();
	  	$('[id*=file_list]').html('');
	      $(':input',obj)
	      .not(':button, :submit, :reset, :hidden')
	      .val('')
	      .removeAttr('checked')
	      .removeAttr('selected');
	  	return false; 
	  }
	   $( document ).ready(function() {
			 
			if($("#fileCount").val()=='N'){
				
				$('input[id=linkType]').attr("disabled",true);
				$('input[id=linkType1]').attr("disabled",true);
				$('input[id=linkType2]').attr("disabled",true);
			}
			else if ($("#fileCount").val()=='Y'){
				$('input[id=linkType]').attr("disabled",false);
				$('input[id=linkType1]').attr("disabled",false);
				$('input[id=linkType2]').attr("disabled",false);
			}
					
			});   
// Changes for Transalator US#102613 --> starts	   
	   $( document ).ready(function() {
		   
		   
		   var langFlag = getLocalMessage('admin.lang.translator.flag');
			if(langFlag ==='Y'){
				$("#pollSubEn").keyup(function(event){
					var no_spl_char;
					no_spl_char = $("#pollSubEn").val().trim();
					if(no_spl_char!='')
					{
						//alert('world');
						commonlanguageTranslate(no_spl_char,'pollSubReg',event,'');
					}else{
						$("#pollSubReg").val('');
					}
				});			
	   $("#optionEn0").keyup(function(event){
			var no_spl_char;
			no_spl_char = $("#optionEn0").val().trim();
			if(no_spl_char!='')
			{
				commonlanguageTranslate(no_spl_char,'optionReg0',event,'');
			}else{
				$("#optionReg0").val('');
			}
		});
		$("#optionEn1").keyup(function(event){
			var no_spl_char;
			no_spl_char = $("#optionEn1").val().trim();
			if(no_spl_char!='')
			{
				commonlanguageTranslate(no_spl_char,'optionReg1',event,'');
			}else{
				$("#optionReg1").val('');
			}
		});
		$("#optionEn2").keyup(function(event){
			var no_spl_char;
			no_spl_char = $("#optionEn2").val().trim();
			if(no_spl_char!='')
			{
				commonlanguageTranslate(no_spl_char,'optionReg2',event,'');
			}else{
				$("#optionReg2").val('');
			}
		});
		$("#optionEn3").keyup(function(event){
			var no_spl_char;
			no_spl_char = $("#optionEn3").val().trim();
			if(no_spl_char!='')
			{
				commonlanguageTranslate(no_spl_char,'optionReg3',event,'');
			}else{
				$("#optionReg3").val('');
			}
		});
		$("#optionEn4").keyup(function(event){
			var no_spl_char;
			no_spl_char = $("#optionEn4").val().trim();
			if(no_spl_char!='')
			{	
				commonlanguageTranslate(no_spl_char,'optionReg4',event,'');
			}else{
				$("#optionReg4").val('');
			}
		});	 
			}
	   });
	// Changes for Transalator  --> ends	 
	
	$(function() {
			$("#frmAdminOpinionPollForm").validate();
		});
	
	$('textarea.form-control.remark').on('blur', function() { 
		   $(this).parent().switchClass("has-error","has-success");
		   $(this).removeClass("shake animated");
		   $('#error_msg_cheker').css('display','none');
		   $('#error_msg_cheker').remove();
		     
		});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div  class="content">
 <div class="widget">
 <div class="widget-header">
          <h2><strong><spring:message code="admin.opinionpoll" text="Opinion Poll"/></strong></h2>
        </div>
        <div class="widget-content padding">
        <div class="mand-label clearfix"><span><spring:message code="MandatoryMsg" /></span></div>
	<form:form method="post" action="AdminOpinionPollForm.html"
		name="frmAdminOpinionPollForm" id="frmAdminOpinionPollForm" class="form-horizontal">
		<div class="error-div">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		</div>
		<form:hidden path="isChecker" id="isCheckerFlag"/>
		<form:hidden path="entity.chekkerflag1" id="chekkerflag1"></form:hidden>
        <input type="hidden" id="getFormId">	
        <input type ="hidden" id ="mode" value="${command.mode }"/>	
			<div class="form-group">
			<label class="control-label col-sm-2" for="entity.pollSubEn">
				<spring:message code="admin.opinionpoll.subEn" /> <span class="mand">*</span>
			</label> 
			<div class="col-sm-4">
			    <c:set var="OpinionEnglish" value="${command.getAppSession().getMessage('eip.admin.opinion.OpinionEnglish') }" />
				<form:textarea id= "pollSubEn" path="entity.pollSubEn" cssClass="form-control" maxlength="1000" isMandatory="true" data-rule-required="true" data-msg-required="${OpinionEnglish}"/>
			</div>
		
			<label class="control-label col-sm-2" for="entity.pollSubReg">
				<spring:message code="admin.opinionpoll.subRg" /> <span class="mand">*</span>
			</label> 
			<div class="col-sm-4">
			    <c:set var="OpinionRegional" value="${command.getAppSession().getMessage('eip.admin.opinion.OpinionRegional') }" />
				<form:textarea id ="pollSubReg" path="entity.pollSubReg" cssClass="form-control" maxlength="1000" isMandatory="true" data-rule-required="true" data-msg-required="${OpinionRegional}"/>
			</div>
				<%-- <apptags:textArea labelCode="admin.opinionpoll.subEn" path="entity.pollSubEn" maxlegnth="1000" isMandatory="true"></apptags:textArea>
				<apptags:textArea labelCode="admin.opinionpoll.subRg" path="entity.pollSubReg" maxlegnth="1000" isMandatory="true"></apptags:textArea> --%>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2 required-control">
					<spring:message code="admin.opinionpoll.IssueDate" />
				</label>
				<div class="col-sm-4">
					<apptags:dateField cssClass="form-control empname subsize" datePath="entity.issueDate" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				</div>
			
				<label class="control-label col-sm-2 required-control">
					<spring:message code="admin.publicNotice.ValidityDate" />
				</label>
				<div class="col-sm-4">
					<apptags:dateField cssClass=" form-control empname subsize" datePath="entity.validityDate" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				</div>
			</div>
			
			<div class="form-group document">			
				<apptags:formField fieldType="9" fileSize="10485760" cssClass="mandClassColor empname subsize" fieldPath="entity.docPath" labelCode="eip.dept.upload" showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" isMandatory="false"  />
				<div class="col-sm-4">
				<div class="uploadDoc">		
					<c:forEach items="${command.attachNameList}" var="Namelist" varStatus="sts">
			         	<c:set var="count" value="${sts.count}"/>
			         	<c:if test="${not empty Namelist}">
			     		 <div id="filid_${count}" class ="documentDiv"> 
			          		<p>${Namelist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${Namelist}','${count}','file')"> </p> 
				        </div>  
				        </c:if>
				    </c:forEach>	 			 	
				</div>	
			</div>
		</div>
			
		<div class="form-group docImage">
				<apptags:formField  fieldType="9" fileSize="204800" cssClass="mandClassColor empname subsize" fieldPath="entity.imgPath" labelCode="eip.uploadImage"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" maxFileCount="1" isMandatory="false"  /> 
				<div class="col-sm-4"> 
				<div class="uploadImage">
				
						<c:forEach items="${command.imgNameList}" var="imageNamelist" varStatus="sts">
			         	<c:set var="count" value="${sts.count}"/>
			         	<c:if test="${not empty imageNamelist}">
			     		 <div id="id_${count}" class="uploadImageDiv"> 
			          		<p>${imageNamelist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${imageNamelist}','${count}','image')"> </p> 
				        </div> 
				        </c:if>
				    </c:forEach>
				</div>
			</div>
		</div>	
		
		<table class="table table-bordered table-striped margin_top_10 arial">
			<tr>
				<th><spring:message code="admin.opinionpoll.subEn" text="Option (English)"/></th>
				<th><spring:message code="admin.opinionpoll.subRg" text="Option (Hindi)"/></th>
				<%-- <th><spring:message code="" text="Delete"/></th> --%>
			</tr>
			
			<c:forEach items="${command.optionList}" var="user" varStatus="i">
            <tr>
                <td><form:textarea id = "optionEn${i.index}"  path="optionList[${i.index}].optionEn" /></td>
                <td><form:textarea id = "optionReg${i.index}" path="optionList[${i.index}].optionReg"/></td>
                
               <%--  <td><a href="javascript:void(0);" onclick="return deleteElementForLink('frmSectionEntryForm${subLinkFieldMap.rowId}','DeleteElement',${subLinkFieldMap.rowId});"><img alt="Delete" src="css/images/delete.png" width="17"/></a></td>
				 --%>		
            </tr>
       		</c:forEach>
		</table>
			<%-- 	<div class="form-group">
		<apptags:textArea labelCode="eip.cheker.remark" path="entity.remark" cssClass="remark" maxlegnth="1000"></apptags:textArea>
		</div>
		 --%>
		
				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.remark"><spring:message
							code="eip.cheker.remark" text="Cheker Remark" /><span class="mand">*</span></label>
					<div class="col-sm-10">
						<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="entity.remark" id="entityRemark" maxlength="1000"
							cssClass="form-control remark mandColorClass" rows="2" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')" data-rule-required="true" data-msg-required="${cheker}"/>
							<label id="error_msg_cheker" style="display:none; border:none;"></label> 
							  <div>
			 					<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
								<p class="charsRemaining" id="DescriptionCount"></p>
		    				  </div>
					</div>
				</div>
		
			<div class="form-group" id="AdminFaqCheker">				
				<div class="col-sm-12 text-center">
	                     <%--  <label class="checkbox-inline" for="chekkerflag1">
	                      <form:checkbox path="entity.chekkerflag" value="Y" id="chekkerflag1" required ="true"/> <spring:message code="Authenticate" text="Authenticate" /></label> --%>
	                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton" aria-label="Authenticate"/> <spring:message code="Authenticate" text="Authenticate" /> 
                           <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton" aria-label="Reject"/><spring:message code="Reject" text="Rejection" />
	               </div>					
			</div>
					
		    <div class="text-center padding-top-10">
	            <input class="btn btn-success " type="submit" id ="adminChekerButton" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateNotice('','', 'Opinionpoll.html?AdminFaqCheker','');"></input>
	            <input class="btn btn-success AdminFaqChekerbutton" type="button"  value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateNoticeValidate('','', 'Opinionpoll.html?AdminFaqCheker','');"></input>
	            
	            <input class="btn btn-success " type="submit" id ="adminMakerButton" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateNotice('','', 'Opinionpoll.html','');"></input>	
	             <input class="btn btn-success AdminFaqbutton" type="button"  value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateNoticeValidate('','', 'Opinionpoll.html','');"></input>	
				<c:if test="${command.mode eq 'A'}">
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormOnAdd(frmAdminOpinionPollForm)" class="btn btn-warning"></input>
					</c:if>
				<input type="button" id="AdminFaqback" class="btn btn-danger" value="<spring:message code="portal.common.button.back" />" onclick="closebox('','Opinionpoll.html')">  
				<input type="button" id="AdminFaqChekerback" class="btn btn-danger" value="<spring:message code="portal.common.button.back" />" onclick="closebox('','Opinionpoll.html?AdminFaqCheker')">    
            </div>
			<form:hidden id="fileCount"  path="filecount" />
			
	</form:form>
	</div>
     </div>
     </div>