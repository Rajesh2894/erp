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
<script>
$(document).ready(function(){
	$('#moduleId').change(function(){		
		$('#link').val("SectionInformation.html?editForm&rowId="+$(this).val()+"&page=");
	});
	
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
			$("#AdminFaqbutton").show();
			$("#AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			$(".AdminFaqbutton").show();
			$(".AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
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
			
	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		yearRange: "-150:+0",
		changeMonth: true,
		changeYear: true,
		onClose: function() {
	        $(this).valid();
	    }
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

	$("#newsDate").val($("#newsDate").val().substr(0,10));
	 
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
  
</script>
<script>
	$(document).ready(function() {
		
		fileUploadValidationList();
		
		jQuery('.maxLength300').keyup(function () { 
			 $(this).attr('maxlength','300');
		});
		linkChange();
	});
	function saveOrUpdateAnnouneMent(obj, message, successUrl, actionParam)
	{
		
		var theForm	=	'#'+'frmEipAnnouncementForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData ='';
		
		var returnData =__doAjaxRequest('EipAnnouncementForm.html?validateSaveAnnouncement','post',requestData, false,'','');
		 if (returnData.length > 0) {
       	  
				displayErrorsOnPage(returnData);
				$(".error-div").addClass('alert alert-danger alert-dismissible');
			
			}else{
				
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
		}if (!actionParam) {
			actionParam = "save";
		}
		if (errorList.length == 0){
			return doFormActionForSave($('#getFormId'),successMessage, actionParam, true , successUrl);
		}else{
			return errorList;
			}
		
  }
	}

	function resetForm(resetBtn) {

		cleareFile(resetBtn);
		
		if (resetBtn && resetBtn.form) {
			
			$('[id*=file_list]').html('');
			$('.error-div').remove();
			resetBtn.form.reset();

			/*prepareTags();*/
			
		};
		$('.form-control').val("");
	}
	function resetFormPub(obj)
	{

		$('.error-div').remove();
	    $("#frmEipAnnouncementForm").closest('form').find("input[type=text], textarea").val("");
		
		return false; 
		
		
	}
	function linkChange(ue){
		$(".cmp-nle").show();
		$(".cmp-le").hide();		
		if($('#linkType1:checked').val() === 'E'){
				if(ue=="uec"){$("#link").val("http://");}
				$('#moduleId').prop('disabled',true);
				$('#moduleId').val('0');				
			}else if($('#linkType:checked').val() === 'L'){
				if(ue=="uec"){$("#link").val("SectionInformation.html?editForm&rowId=&page=");}
				$('#moduleId').prop('disabled',false);				
			} else{
				$(".cmp-nle").hide();
				$(".cmp-le").show();
				$('#moduleId').prop('disabled',true);
				$('#moduleId').val('0');
			} 	
	}
	
	 $( document ).ready(function() {
		   
		   
		   var langFlag = getLocalMessage('admin.lang.translator.flag');
			if(langFlag ==='Y'){
				$("#announceDescEng").keyup(function(event){
					var no_spl_char;
					no_spl_char = $("#announceDescEng").val().trim();
					if(no_spl_char!='')
					{
						commonlanguageTranslate(no_spl_char,'announceDescReg',event,'');
					}else{
						$("#announceDescReg").val('');
					}
				});
			}
	   });
	 
	 $(function() {
			$("#frmEipAnnouncementForm").validate();
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
                    <h2><strong><spring:message code="EIP.Announcement.Form" /></strong></h2>
                   </div>
               <div class="widget-content padding">
				<div class="mand-label">
					<span><spring:message code="MandatoryMsg" text="MandatoryMsg" /></span>
				</div>
	<form:form method="post" action="EipAnnouncementForm.html" name="frmEipAnnouncementForm" id="frmEipAnnouncementForm" class="form-horizontal">
			<div class="error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
			<form:hidden path="isChecker" id="isCheckerFlag"/>
			<form:hidden path="entity.chekkerflag1" id="chekkerflag1"/>
			<input type ="hidden" id ="mode" value="${command.mode }"/>
		<input type="hidden" id="getFormId">
		 <div class="form-group">
		<label class="control-label col-sm-2" for="entity.announceDescEng">
				<spring:message code="admin.eipAnnounce.DetailEn" /><span class="mand">*</span>
			</label> 
			<div class="col-sm-4">
			<c:set var="DetailsEnglish" value="${command.getAppSession().getMessage('eip.admin.EIPAnnouncement.DetailsEnglish') }" />
				<form:textarea path="entity.announceDescEng" id="announceDescEng" cssClass="form-control" maxlength="4000" isMandatory="true" data-rule-required="true" data-msg-required="${DetailsEnglish}"/>
			</div>
		
			<label class="control-label col-sm-2" for="entity.announceDescReg">
				<spring:message code="admin.eipAnnounce.DetailReg" /><span class="mand">*</span> 
			</label> 
			<div class="col-sm-4">
			    <c:set var="DetailsRegional" value="${command.getAppSession().getMessage('eip.admin.EIPAnnouncement.DetailsRegional') }" />
				<form:textarea path="entity.announceDescReg" id="announceDescReg" cssClass="form-control" maxlength="4000" isMandatory="true" data-rule-required="true" data-msg-required="${DetailsRegional}"/>
			</div>
			<%-- <apptags:textArea labelCode="admin.eipAnnounce.DetailEn" path="entity.announceDescEng" maxlegnth="4000" isMandatory="true"></apptags:textArea>
			<apptags:textArea labelCode="admin.eipAnnounce.DetailReg" path="entity.announceDescReg" maxlegnth="4000" isMandatory="true"></apptags:textArea> --%>
		</div>
		
		 <div class="form-group">
			<apptags:date fieldclass="datepicker" datePath="entity.newsDate" labelCode="admin.eipAnnounce.newsDate" readonly="true" ></apptags:date>
			<apptags:date fieldclass="datetimepicker" datePath="entity.validityDate" labelCode="admin.publicNotice.ValidityDate" isMandatory="true" readonly="true"></apptags:date>
		 </div>
		
		<div class="form-group">
			<apptags:checkbox labelCode="admin.publicNotice.highlighted" value="Y" path="entity.isHighlightedFlag"/>
			
		</div>
		
		<%-- Start of Feature addition for accepting links in latest news section by ABM2144 --%>
		
		<div class="form-group">
			<label class="control-label col-sm-2">
				<spring:message code="admin.qlFormLinkType" /><span class="mand required-control"></span>
			</label>
			<div class="col-sm-4">
				<label class="radio-inline">
					<form:radiobutton path="entity.linkType" id="linkType" name="linkType" value="L" onclick="linkChange('uec')" />
					<spring:message code="admin.qlFormLocalLink" />
				</label>
				<label class="radio-inline">
					<form:radiobutton path="entity.linkType" id="linkType1" name="linkType" value="E" onclick="linkChange('uec')" />
					<spring:message code="admin.qlFormExternalLink" />
				</label>
				<label class="radio-inline">
					<form:radiobutton path="entity.linkType" id="linkType2" name="linkType" value="R" onclick="linkChange('uec')" />
					<spring:message code="admin.publicNotice.none" text="none" />
				</label>
			</div>			
			
		</div>
		<div class="form-group">
		<span class="cmp-nle"><apptags:input labelCode="admin.qlFormLink" path="entity.link"/>
		<label class="col-sm-2 control-label"><spring:message code=""  text="Select Internal link"/></label>
		 <apptags:lookupField items="${command.modules}" path="moduleId" cssClass="form-control" selectOptionLabelCode="eip.selectModule" hasId="true" disabled="true"/>
		</span>
		</div>
		
		<%-- End of Feature addition for accepting links in latest news section by ABM2144 --%>
		
		<div class="form-group document">	
		<span class="cmp-le">
		<%-- <label class="control-label col-sm-2"><spring:message code="label.checklist.upload" text="UPLOAD"/><span class="mand">*</span></label> --%>
			<!-- <div class="col-sm-4"> -->
				<%-- <apptags:formField fieldType="7" fieldPath="entity.attach" labelCode="" hasId="true" isMandatory="true" fileSize="MAX_SIZE_100_MB" currentCount="0" showFileNameHTMLId="true" folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT" validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"/> --%>
				<apptags:formField fieldType="9" fileSize="10485760" cssClass="mandClassColor empname subsize" fieldPath="entity.attach" labelCode="label.checklist.upload"  showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" isMandatory="true"  /> 
				<c:forEach items="${command.attachNameList}" var="docNamelist" varStatus="sts">
		         	<c:set var="count" value="${sts.count}"/>
		         	<c:if test="${not empty docNamelist}">
		     		 <div id="filid_${count}" class = "uploadDocumentDiv"> 
		          		<p>${docNamelist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${docNamelist}','${count}','file')"> </p> 
			        </div> 
			        </c:if>												       
			    </c:forEach>
				<%-- <c:if test="${command.mode eq 'U'}">
					<input type="hidden" name="downloadLink" value="${command.filesDetails}">
			     	<apptags:filedownload filename="${command.fileName}" filePath="${command.filesDetails}" actionUrl="EipAnnouncement.html?Download"></apptags:filedownload>
				</c:if>	
				<p class="help-block text-small">[<spring:message code="eip.announce.fileUploadText" />]</p> --%>	
			<!-- </div> -->
		</span>	
			
		</div>
		<div class="form-group">
		<apptags:date fieldclass="datepicker" datePath="entity.highlightedDate" labelCode="Highlighted Date" readonly="true"></apptags:date>
		</div>
		<div class="form-group docImage">
		<%-- <label class="control-label col-sm-2"><spring:message code="pg.uploadImage" text="UPLOAD IMAGE"/><span class="mand">*</span></label> --%>
			<!-- <div class="col-sm-4"> -->
				<%-- <apptags:formField fieldType="7" fieldPath="entity.attachImage" labelCode="" hasId="true" isMandatory="true" fileSize="COMMOM_MAX_SIZE" currentCount="1" showFileNameHTMLId="true" folderName="1" maxFileCount="CHECK_LIST_MAX_COUNT" validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"/> --%>
				<apptags:formField  fieldType="9" fileSize="204800" cssClass="mandClassColor empname subsize" fieldPath="entity.attachImage" labelCode="pg.uploadImage"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" maxFileCount="1" isMandatory="false"  /> 				 
				<%-- <input type="hidden" name="downloadLink" value="${command.imageDetails}"> --%>
				<c:forEach items="${command.imgNameList}" var="imageNamelist" varStatus="sts">
		         	<c:set var="count" value="${sts.count}"/>
		         	<c:if test="${not empty imageNamelist}">
		     		 <div id="id_${count}" class="uploadImageDiv"> 
		          		<p>${imageNamelist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${imageNamelist}','${count}','image')"> </p> 
			        </div> 
			        </c:if>
			    </c:forEach>
				<%-- <c:if test="${command.mode eq 'U'}">
					<input type="hidden" name="downloadLink" value="${command.imageDetails}">
			     	<apptags:filedownload filename="${command.imageFileName}" filePath="${command.imageDetails}" actionUrl="EipAnnouncement.html?Download"></apptags:filedownload>
				</c:if>	
				<p class="help-block text-small">[<spring:message code="eip.announce.fileUploadText" />]</p> --%>
			<!-- </div> -->
			
		</div>
		
		<div class="form-group">
					<%-- <apptags:textArea labelCode="eip.cheker.remark" path="entity.remark" cssClass="remark" maxlegnth="1000"></apptags:textArea>
		</div> --%>
					<label class="col-sm-2 control-label" for="entity.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="entity.remark" id = "entityRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
				<label id="error_msg_cheker" style="display:none; border:none;"></label> 
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
				</div>
		
		<div class="form-group padding-top-10" id="AdminFaqCheker">				
			<div class="col-sm-12 text-center">
                  
                   
                     <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton" aria-label="Authenticate"/> <spring:message code="Authenticate" text="Authenticate" />
                     <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton" aria-label="Reject"/><spring:message code="Reject" text="Reject" />
               </div>					
		</div>
		<div class="text-center padding-top-10">
			<div id="AdminFaqback">
			<input class="btn btn-success AdminFaqbutton" type="button" value="<spring:message code="master.save"/>"  onclick="return saveOrUpdateAnnouneMent('','', 'EipAnnouncement.html','');"></input>
			<input class="btn btn-success  AdminFaqChekerbutton" type="button" value="<spring:message code="master.save"/>"  onclick="return saveOrUpdateAnnouneMent('','', 'EipAnnouncement.html?AdminFaqCheker','');"></input>
<%-- 			<input type="button" value="<spring:message code="rstBtn"/>" onclick="reset('frmEipAnnouncementForm');" class="btn btn-warning"></input> --%>
			<c:if test="${command.mode eq 'A'}">
			<input type="button" onclick="openForm('EipAnnouncementForm.html')" value="<spring:message code="rstBtn"/>" onclick="resetFormPub('frmEipAnnouncementForm')" class="btn btn-warning"></input>
			</c:if>
			<apptags:backButton url="EipAnnouncement.html"  cssClass="btn btn-warning" />
			</div>
			<div id="AdminFaqChekerback">
			<input class="btn btn-success AdminFaqbutton" type="button" value="Save"  onclick="return saveOrUpdateAnnouneMent('','', 'EipAnnouncement.html','');"></input>
			<input class="btn btn-success  AdminFaqChekerbutton" type="button" value="Save"  onclick="return saveOrUpdateAnnouneMent('','', 'EipAnnouncement.html?AdminFaqCheker','');"></input>
<%-- 			<input type="button" value="<spring:message code="rstBtn"/>" onclick="reset('frmEipAnnouncementForm');" class="btn btn-warning"></input> --%>
			<c:if test="${command.mode eq 'A'}">
			<input type="button" onclick="openForm('EipAnnouncementForm.html')" value="<spring:message code="rstBtn"/>" onclick="resetFormPub('frmEipAnnouncementForm')" class="btn btn-warning"></input>
			</c:if>
			<apptags:backButton url="EipAnnouncement.html?AdminFaqCheker" cssClass="btn btn-danger" />
		    </div>
		</div>
	</form:form>
</div>
</div>
</div>
<script>
$(document).ready(function(){
	$( ".document" ).on( "change", function( event ) {
	   $(".uploadDocumentDiv").remove();
	}); 

	$(".docImage" ).on( "change", function( event ) {
	   $(".uploadImageDiv").remove();
	}); 
})
</script>