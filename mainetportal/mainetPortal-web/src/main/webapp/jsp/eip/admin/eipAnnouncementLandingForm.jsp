<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
$(document).ready(function(){
	
	
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	if(pathname.indexOf(txt) > -1) {

		$("#AdminFaqCheker").show()
		$("#chekkerflag1").val("Y");
		$(".AdminFaqbutton").hide();
		$(".AdminFaqChekerbutton").show();
		$("#AdminFaqChekerback").show();
		$("#AdminFaqbutton").hide();
		$("#AdminFaqChekerbutton").show();
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
			$("#AdminFaqbutton").show();
			$("#AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			
			
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


</script>
<script>
	$(document).ready(function() {

		fileUploadMultipleValidationList();

		fileUploadValidationList();

		jQuery('.maxLength300').keyup(function() {
			$(this).attr('maxlength', '300');

		});

	});
	function saveOrUpdateAnnouneMentLanding(obj, message, successUrl,
			actionParam) {

		var successMessage = getLocalMessage('eip.announcement.sMessagee');
		if (!actionParam) {

			actionParam = "save";
		}
	
		return doFormActionForSave($('#getFormId'), successMessage,
				actionParam, true, successUrl);
	}


	function resetFormPub(obj)
{

	$('.error-div').remove();
    $("#frmEipAnnouncementLandingForm").closest('form').find("input[type=text], textarea").val("");
	
	return false; 
	
	
}
	
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

</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>EIP Announcement Landing Page</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
			<form:form method="post" action="EipAnnouncementLandingForm.html"
				name="frmEipAnnouncementLandingForm"
				id="frmEipAnnouncementLandingForm" class="form-horizontal">
				<input type="hidden" id="hiddenMAxListSize" value="1" />
				<input type="hidden" id="getFormId">
				<div class="error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control">
						<spring:message code="admin.eipAnnounce.DetailEn" />
					</label>
					<div class="col-sm-10">
						<form:textarea path="entity.announceDescEng" maxlength="2000" cssClass="form-control maxLength1950 maxsize"   rows="4" onkeyup="countChar(this)" onclick="countChar(this)" />
						 <p class="charsRemaining" id="P3">characters remaining</p> 
					     <p class="charsRemaining"></p> 
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control">
						<spring:message code="admin.eipAnnounce.DetailReg" />
					</label>
					<div class="col-sm-10">
						<form:textarea path="entity.announceDescReg" maxlength="2000" cssClass="form-control maxLength1950 maxsize"  rows="4" onkeyup="countChar(this)" onclick="countChar(this)" />
						 <p class="charsRemaining" id="P3">characters remaining</p> 
					     <p class="charsRemaining"></p> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Profile Image<span class="mand">*</span></label>
					<div class="col-sm-10">
						<c:set var="errmsg">
							<spring:message code="file.upload.size" />
						</c:set>
						<apptags:formField fieldType="7" hasId="true"
							fieldPath="entity.attachment" isMandatory="false" labelCode=""
							fileSize="COMMOM_MAX_SIZE" currentCount="0"
							showFileNameHTMLId="true" folderName="0"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC" />
							
							<c:if test="${command.mode eq 'U'}">
							
								<input type="hidden" name="downloadLink"
									value="${command.filesDetails}">
								<apptags:filedownload filename="${command.fileName}"
									filePath="${command.filesDetails}"
									actionUrl="EipAnnouncement.html?Download"></apptags:filedownload>
							
						</c:if>
						
					</div>
					</div>
					<div class="form-group" id="AdminFaqCheker">				
						<div class="col-sm-12 text-center">
	                     
	                        <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton"/> <spring:message code="Authenticate" text="Authenticate" />
                            <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton"/>Rejection
			            </div>					
					</div>	

				<div class="text-center">
				    <input class="btn btn-success" type="submit" id="AdminFaqbutton" value="Save" onclick="return saveOrUpdateAnnouneMentLanding('','', 'EipAnnouncementLanding.html','');"></input>
				    <input class="btn btn-success" type="submit" id="AdminFaqChekerbutton" value="Save" onclick="return saveOrUpdateAnnouneMentLanding('','', 'EipAnnouncementLanding.html?AdminFaqCheker','');"></input>
				  
				<input type="button" onclick="openForm('EipAnnouncementLandingForm.html')"  value="<spring:message code="rstBtn"/>" class="btn btn-warning"></input>
				    <a href="EipAnnouncementLanding.html"  id="AdminFaqback" class="btn btn-danger AdminFaqback" >Back</a>
				
				    <a href="EipAnnouncementLanding.html?AdminFaqCheker"  id="AdminFaqChekerback" class="btn btn-danger AdminFaqChekerback" >Back</a>
				</div>
			</form:form>
		</div>
	</div>
</div>

<form action="EipAnnouncementLanding.html?Download" method="post"
	id="frm0_0" target="_blank">
	<a href="javascript:void(0);"
		onclick="javascript:document.getElementById('frm0_0').submit();">
	</a> <input type="hidden" name="downloadLink"
		value="${command.filesDetails}">
</form>
