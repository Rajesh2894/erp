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
<!-- <script>
$(document).ready(function(){

 if('${command.mode}' == 'U'){

	   $("[name^='profileMaster']").prop('disabled',true);
	   $("[name^='profileMaster']").removeClass('mandClassColor');
	   $("[name^='profileMaster']").addClass('disablefield');	
	   $('span').remove();   

	   }
});
</script> -->
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
		//	$("#AdminFaqCheker").hide()
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
	resetFormOnAdd(frmAdminDeputyCommissioner);
    var url	=	'AdminDeputyCommissionerForm.html'+'?cleareFile';
    var response= __doAjaxRequest(url,'post',{},false);
	return false; 
}
$('textarea.form-control.remark').on('blur', function() { 
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
	    // $('#error_msg_cheker').css('display','none');
	   //  $('#error_msg_cheker').remove();		
	     }  
});
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<spring:message code="dComGrid.Title" var="directorTitle"/>
<div class="content">
      <div class="widget">
         <div class="widget-header"><h2><spring:message code="dComGrid.Title"/></h2>
         <apptags:helpDoc url="AdminDeputyMayorList.html"></apptags:helpDoc></div>
        <div class="widget-content padding">
        <form:form method="post" action="AdminDeputyCommissionerForm.html" name="frmAdminDeputyCommissioner" id="frmAdminDeputyCommissioner" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<form:hidden path="isChecker" id="isCheckerFlag"/>
		<form:hidden path="entity.chekkerflag1" id="chekkerflag1"/>
            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="pNameEn"><spring:message code="Com.Name" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.pNameEn" hasId="true" maxlegnth="100" cssClass="form-control subsize"
					isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="pNameReg"><spring:message code="mayorForm.Name" />&nbsp;<spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.pNameReg" hasId="true" maxlegnth="1000" cssClass="form-control subsize"
					isDisabled="" /></div>
             </div>

            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="designationEn"><spring:message code="Com.Designation" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.designationEn" maxlegnth="100" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="designationReg"><spring:message code="Com.Designation" />&nbsp;<spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.designationReg" maxlegnth="1000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
              </div>

            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="linkTitleEn"><spring:message code="Com.Title" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.linkTitleEn" maxlegnth="150" cssClass="form-control mandClassColor subsize"
					hasId="true" isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="linkTitleReg"><spring:message code="mayorForm.Title" />&nbsp;<spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.linkTitleReg" maxlegnth="1000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
              </div>

            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="profileEn"><spring:message code="Com.Profile" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.profileEn" maxlegnth="4000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="profileReg"><spring:message code="mayorForm.Profile" />&nbsp;<spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.profileReg" maxlegnth="2000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
              </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label " for="profileEn"><spring:message code="Email" text="Emailid" /></label>
                   <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.emailId" maxlegnth="4000" cssClass="form-control"
					hasId="true" isDisabled="" /></div>
               
                <label class="col-sm-2 control-label" for="profileMaster.summaryEng"><spring:message code="mayorForm.msg" /></label>
                <div class="col-sm-4"><%-- <form:textarea path="profileMaster.summaryEng" maxlength="2000" cssClass="form-control subsize"/> --%>
				<apptags:inputField fieldPath="profileMaster.summaryEng"  cssClass="form-control hasFaxNo" hasId="true" isDisabled="" />
				</div>
              </div>
            <div class="form-group">
            <c:set var="currCount" value="0"/>
             <c:choose>
 		     <c:when test="${command.mode eq 'A'}">
       
						   <label class="col-sm-2 control-label" for="profileMaster.imageName"><spring:message code="mayorForm.ProfileImage" text="Profile Image"/><span class="mand">*</span></label>
                             <div class="col-sm-4">
						    <c:set var="errmsg"><spring:message code="file.upload.size" /></c:set>
						
						       <apptags:formField fieldType="7"  hasId="true"
							 fieldPath="profileMaster.imageName" isMandatory="false" labelCode="" currentCount="${currCount}" showFileNameHTMLId="true" folderName="EIP_HOME" fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT" validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />  
							
							 
							  <p class="help-block text-small"><spring:message code="eip.mayor.image.size"/></p>
							  <p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>	
							  
			                </div>
              
 		       </c:when>
 		       <c:otherwise>
	                <label class="col-sm-2 control-label">
	                	<spring:message code="mayorForm.ProfileImage" />
	                </label>
	 		         <div class="col-sm-4"> 
	 		         	<img alt="Deputy Commissioner" src="./${command.imagesDetail}" class="img-thumbnail" style="height:150px">
	 		         	
	 		         	<apptags:formField fieldType="7"
									fieldPath="profileMaster.imageName" labelCode="" hasId="true"
									isMandatory="false" fileSize="COMMOM_MAX_SIZE"
									currentCount="${currCount}" showFileNameHTMLId="true"
									folderName="EIP_HOME" maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
	 		         	<p class="help-block text-small"><spring:message code="eip.mayor.image.size"/></p>
						<p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>
						<%-- <apptags:filedownload filename="${command.image}" filePath="${command.imagesDetail}" actionUrl="AdminDeputyCommissioner.html?Download"></apptags:filedownload> --%>
		 		         
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
			<div class="form-group">
				<%-- <apptags:textArea labelCode="eip.cheker.remark" path="profileMaster.remark" cssClass="remark" maxlegnth="1000"></apptags:textArea> --%>
					<label class="col-sm-2 control-label" for="entity.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="profileMaster.remark" id = "entityRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
				 <label id="error_msg" style="display:none; border:none;"></label>	
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
				</div>
             
             <div class="form-group padding-top-10" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                       <%-- <label class="checkbox-inline" for="chekkerflag1">
                       <form:checkbox path="entity.chekkerflag" value="Y" id="chekkerflag1" required ="true"/> <spring:message code="Authenticate" text="Authenticate" /></label> --%>
                         <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton"/> <spring:message code="Authenticate" text="Authenticate" />
                         <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton"/><spring:message code="Reject" text="Reject" />
	                </div>					
				</div>
				
           
            <div class="btn_fld margin_top_10 padding-top-20" align="center">
				<apptags:submitButton entityLabelCode="${directorTitle}"
					isChildButton="false" successUrl="AdminDeputyCommissioner.html" cssClass="btn btn-success AdminFaqbutton" />
					<apptags:submitButton entityLabelCode="${directorTitle}"
					isChildButton="false" successUrl="AdminDeputyCommissioner.html?AdminFaqCheker" cssClass="btn btn-success AdminFaqChekerbutton" />
				<c:if test="${command.mode eq 'A'}">
			<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetForm(this)" class="btn btn-warning"></input>
			</c:if>
			<a href="AdminDeputyCommissioner.html" id="AdminFaqback" class="btn btn-danger"><spring:message code="bckBtn" text="Back" /></a>
			<a href="AdminDeputyCommissioner.html?AdminFaqCheker" id="AdminFaqChekerback" class="btn btn-danger"><spring:message code="bckBtn" text="Back" /></a>
		</div>
		
          </form:form>
          </div>
          </div>

 </div>