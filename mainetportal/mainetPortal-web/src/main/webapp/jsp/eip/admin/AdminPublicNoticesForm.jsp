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

	$(document).ready(function() {
		
		$('#moduleId').change(function(){		
			$('#link').val("SectionInformation.html?editForm&rowId="+$(this).val()+"&page=");
		});
		linkChange();
		//$("#isHighlighted").prop("checked","checked");
				
		
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
		
		$('.datetimepicker').datetimepicker({
			yearRange: "-150:+0",
			dateFormat: 'dd/mm/yy',
			timeFormat: "hh:mm tt",
			changeMonth: true,
			changeYear: true
		});
		
		$('.datetimepickervaliditydate').datetimepicker({
			yearRange: "-150:+20",
			dateFormat: 'dd/mm/yy',
			timeFormat: "hh:mm tt",
			changeMonth: true,
			changeYear: true
		});
		
		fileUploadValidationList();

		if($("input[type='radio'][name='entity.linkType']:checked ").val() === 'E' || $("input[type='radio'][name='entity.linkType']:checked ").val() === 'L'){
			//$(".document").hide();
			}

		$('#dpDeptid  option[code="CFC"]').prop('selected', true);
		$('.dept').hide();

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
	
	function linkChange(ue){
		$(".cmp-nle").show();
		$(".cmp-le").hide();		
		if($('#linkType1:checked').val() === 'E'){
				$('#moduleId').val('0');
				if(ue=="uec"){$("#link").val("http://");}
				$('#moduleId').prop('disabled',true);
			}else if($('#linkType:checked').val() === 'L'){				
				if(ue=="uec"){$("#link").val("SectionInformation.html?editForm&rowId=&page=");}
				$('#moduleId').prop('disabled',false);
			} else{
				$('#moduleId').val('0');
				$(".cmp-nle").hide();
				$(".cmp-le").show();
				$('#moduleId').prop('disabled',true);
			} 	
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
	 //  $("#frmAdminPublicNoticesForm").closest('form').find("input[type=text], textarea").val("");
	   $(':input','#frmAdminPublicNoticesForm')
	    .not(':button, :submit, :reset, :hidden')
	    .val('')
	    .removeAttr('checked')
	    .removeAttr('selected');
	   $('select').prop('selectedIndex',0);

	 //  $('#0_file_0').attr('src', '');
		return false; 
		
		
	}
	//Transalator Function
	   $( document ).ready(function() {
		   var langFlag = getLocalMessage('admin.lang.translator.flag');
			if(langFlag =='Y'){
				$("#noticeSubEn").keyup(function(event){
					var no_spl_char;
					no_spl_char = $("#noticeSubEn").val().trim();
					if(no_spl_char!='')
					{
						//alert('world');
						commonlanguageTranslate(no_spl_char,'noticeSubReg',event,'');
					}else{
						$("#noticeSubReg").val('');
					}
				});
				
				$("#detailEn").keyup(function(event){
					var no_spl_char;
					no_spl_char = $("#detailEn").val().trim();
					if(no_spl_char!='')
					{
						//alert('world');
						commonlanguageTranslate(no_spl_char,'detailReg',event,'');
					}else{
						$("#detailReg").val('');
					}
				});
			}
	   });
	   
	   
	  
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

  function countChar(val) {
	  	 var maxlength=val.getAttribute("maxlength");
	  	
	      var len = val.value.length;
	   $('.charsRemaining').next('P').text(maxlength - len);
	     
	    }; 
  function Delete(val) {
	  $('.fa-download').hide();
	  
	  var url	=	'';
		
	    url = 'AdminPublicNoticesForm.html?delete';
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
	   
	   $(function() {
			$("#frmAdminPublicNoticesForm").validate();
		});

</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div  class="content">
 <div class="widget">
 <div class="widget-header">
          <h2><strong><spring:message code="admin.publicNotice" /></strong></h2>
        </div>
        <div class="widget-content padding">
	<form:form method="post" action="AdminPublicNoticesForm.html"
		name="frmAdminPublicNoticesForm" id="frmAdminPublicNoticesForm" class="form-horizontal">
		<div class="error-div">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		</div>
		<form:hidden path="isChecker" id="isCheckerFlag"/>
		<form:hidden path="entity.chekkerflag1" id="chekkerflag1"></form:hidden>
		<input type ="hidden" id ="mode" value="${command.mode }"/>
        <input type="hidden" id="getFormId">
			<div class="form-group dept">
				<label class="control-label col-sm-2 required-control" for="dpDeptid">
					<spring:message code="cms.depName" text="DepName" />
				</label> 
				<apptags:lookupField items="${command.departmentLookUp}" path="entity.department.dpDeptid" selectOptionLabelCode="Select Department" hasId="true" cssClass="form-control mandClassColor empname subsize" showAll="false" />
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2" for="noticeSubEn">
					<spring:message code="admin.publicNotice.NoticeSubjectEn" /><span class="mand">*</span></label> 
				<div class="col-sm-4"> 
				    <c:set var="NoticeSubjectEnglish" value="${command.getAppSession().getMessage('eip.admin.publicNotice.NoticeSubjectEnglish') }" />
					<form:input cssClass="form-control empname subsize" maxlength="250" path="entity.noticeSubEn" id="noticeSubEn" hasId="true" disabled="false"  data-rule-required="true" data-msg-required="${NoticeSubjectEnglish}" />
				</div>
				<label class="control-label col-sm-2" for="noticeSubReg">
					<spring:message code="admin.publicNotice.NoticeSubjectReg" /><span class="mand">*</span></label> 
				<div class="col-sm-4"> 
				    <c:set var="NoticeSubjectRegional" value="${command.getAppSession().getMessage('eip.admin.publicNotice.NoticeSubjectRegional') }" />
					<form:input cssClass="form-control empname subsize" maxlength="500" path="entity.noticeSubReg" id="noticeSubReg" hasId="true" disabled="false"  data-rule-required="true" data-msg-required="${NoticeSubjectRegional}" />
				</div>
			</div>

			<div class="form-group">			
				  <label class="control-label col-sm-2" for="entity.detailEn">
					<spring:message code="admin.publicNotice.DetailEn" /> 
				</label>
				<div class="col-sm-4">
					<form:textarea id="detailEn" cssClass="form-control empname subsize" maxlength="500" path="entity.detailEn" onkeyup="countChar(this)" onfocus="countChar(this)"/>
					<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining"/></p>
					<p class="charsRemaining">characters remaining</p>
				</div>
				<label class="control-label col-sm-2" for="entity.detailReg">
					<spring:message code="admin.publicNotice.DetailReg" />
				</label> 
				<div class="col-sm-4">
					<form:textarea id="detailReg" cssClass="form-control empname subsize" maxlength="1000" path="entity.detailReg" onkeyup="countChar(this)" onfocus="countChar(this)"/>
			 	    <p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining"/></p>
					<p class="charsRemaining">characters remaining</p>
				</div>   
				
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2 required-control">
					<spring:message code="admin.publicNotice.IssueDate" />
				</label>
				<div class="col-sm-4">
					<apptags:dateField cssClass="form-control empname subsize" datePath="entity.issueDate" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				</div>
			
				<label class="control-label col-sm-2 required-control">
					<spring:message code="admin.publicNotice.ValidityDate" />
				</label>
				<div class="col-sm-4">
					<apptags:dateField cssClass=" form-control empname subsize" datePath="entity.validityDate" fieldclass="datetimepickervaliditydate" readonly="true"></apptags:dateField>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2 required-control" for="publishFlag">
					<spring:message code="admin.publicNotice.Publish" />
				</label> 
				<%-- <apptags:lookupField items="${command.publish}" cssClass="form-control empname subsize" path="entity.publishFlag" selectOptionLabelCode="Select" hasId="true" showAll="false" />
				<apptags:input labelCode="admin.publicNotice.seo.keyword" path="entity.noticeTitle"></apptags:input> --%>
				<div class="col-sm-4">
					<c:set var="PublishMsg" value="${command.getAppSession().getMessage('eip.admin.publicNotice.publishMsg') }"></c:set>
					<form:select path="entity.publishFlag"  cssClass="form-control empname subsize" data-rule-required="true" data-msg-required="${PublishMsg}" >
						<form:option value="" > <spring:message code="Select" text="Select"/></form:option> 
							<c:forEach items="${command.publish}" var="lookUp">
							  	<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
					</form:select>
				</div>		
			</div>
		
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
			
		<div class="form-group">
			<apptags:checkbox labelCode="admin.publicNotice.highlighted" value="Y" path="entity.isHighlighted"/>
			
			<c:if test="${command.linkCatagoryFlag eq false}">
			<apptags:checkbox labelCode="admin.publicNotice.usefullLink" value="Y" path="entity.isUsefullLink"/>
			</c:if>
			<c:if test="${command.linkCatagoryFlag eq true}">
				<label class="control-label col-sm-2 required-control" for="newOrImpLink">
					<spring:message code="admin.publicNotice.catagory" text="Catagory"/>
				</label>
				<apptags:lookupField items="${command.linkCatagory}" cssClass="form-control empname subsize" path="entity.newOrImpLink" selectOptionLabelCode="Select" hasId="false" isMandatory="true" showAll="false" />
			</c:if>
		</div>
		
			<div class="form-group document">
			<span class="cmp-le">
				<%-- <label class="control-label col-sm-2"><spring:message code="eip.dept.upload" text="Upload" /><span class="mand"></span></label>
				<div class="col-sm-4"> 
					<apptags:formField fieldType="7" fieldPath="entity.profileImgPath" labelCode="" hasId="true" isMandatory="true" fileSize="MAX_SIZE_100_MB" currentCount="0" showFileNameHTMLId="true" folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT" validnFunction="CHECK_LIST_VALIDATION_EXTENSION"/> 
					 <p class="help-block text-small"><spring:message code="eip.mayor.image.size"/></p>
					 <p class="help-block text-small"><spring:message code="eip.announce.fileUploadText20" text="Upload Upto 20 MB"/></p>
				<c:if test="${command.mode eq 'U'}">
					<input type="hidden" name="downloadLink" value="${command.filesDetails}">
	    			<apptags:filedownload filename="${command.fileName}" filePath="${command.filesDetails}" actionUrl="EipAnnouncement.html?Download"></apptags:filedownload>
				</c:if>	 --%>	
				
				<apptags:formField fieldType="9" fileSize="10485760" cssClass="mandClassColor empname subsize" fieldPath="entity.profileImgPath" labelCode="eip.dept.upload" showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" isMandatory="false"  />
				<div class="col-sm-4">
				<div class="">		
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
			</span>
		</div>
			
		<div class="form-group docImage">
				<%-- <label class="control-label col-sm-2"><spring:message code="eip.uploadImage" text="Upload Image"/><span class="mand"></span></label> --%>
				<apptags:formField  fieldType="9" fileSize="204800" cssClass="mandClassColor empname subsize" fieldPath="entity.imagePath" labelCode="eip.uploadImage"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" maxFileCount="1" isMandatory="false"  /> 
				
				<div class="col-sm-4"> 
					<%-- <apptags:formField fieldType="7" fieldPath="entity.imagePath" labelCode="" hasId="true" isMandatory="true" fileSize="BND_COMMOM_MAX_SIZE" currentCount="1" showFileNameHTMLId="true" folderName="1" maxFileCount="CHECK_LIST_MAX_COUNT" validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"/> 
					 <p class="help-block text-small"><spring:message code="eip.mayor.image.size"/></p>
					 <p class="help-block text-small"><spring:message code="eip.announce.fileUploadText5" text="Upload Upto 5 MB"/></p>
					
					<c:if test="${command.mode eq 'U'}">
					<input type="hidden" name="downloadLink" value="${command.imageDetails}">
	    			<apptags:filedownload filename="${command.imageName}" filePath="${command.imageDetails}" actionUrl="EipAnnouncement.html?Download"></apptags:filedownload>
				</c:if>		 --%>
				
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
			<%-- 		<div class="form-group">
		<apptags:textArea labelCode="eip.cheker.remark" path="entity.remark" cssClass="remark" maxlegnth="1000"></apptags:textArea>
		</div> --%>		
			  <div class="form-group">
				<label class="col-sm-2 control-label" for="entity.remark">
					<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
				</label>
				<div class="col-sm-4">
				<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
					<form:textarea path="entity.remark" id="remark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countChar(this)" onfocus="countChar(this)" data-rule-required="true" data-msg-required="${cheker}"/>
				<div>
			 	    <p class="charsRemaining" id="P4"><spring:message code="charcter.remain" text="characters remaining " /></p>
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
	            <input class="btn btn-success AdminFaqChekerbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateNotice('','', 'PublicNotices.html?AdminFaqCheker','');"></input>
	            <input class="btn btn-success AdminFaqbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateNotice('','', 'PublicNotices.html','');"></input>	
				<c:if test="${command.mode eq 'A'}">
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="openForm('AdminPublicNoticesForm.html')" class="btn btn-warning"></input>
					</c:if>
				<input type="button" id="AdminFaqback" class="btn btn-danger" value="<spring:message code="portal.common.button.back" />" onclick="closebox('','PublicNotices.html')">  
				<input type="button" id="AdminFaqChekerback" class="btn btn-danger" value="<spring:message code="portal.common.button.back" />" onclick="closebox('','PublicNotices.html?AdminFaqCheker')">    
            </div>
			<form:hidden id="fileCount"  path="filecount" />
			
	</form:form>
	</div>
     </div>
     </div>