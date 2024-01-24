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

function countChar(val) {
    var len = val.value.length;
 
    if (len >= 500) {
      val.value = val.value.substring(0, 500);
    } else {
    	$('.charsRemaining').next('P').text(500 - len);
    
    }
  };
  
function resetForm(resetBtn) {
		resetFormOnAdd(frmAdminCommissionerForm);
	    var url	=	'AdminCommissionerForm.html'+'?cleareFile';
	    var response= __doAjaxRequest(url,'post',{},false);
		return false; 
}
  
  
</script>

 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}"> 
     <spring:message code="ComGrid.Title" var="ministerTitle"/>
     </c:if>
     <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}"> 
     <spring:message code="ulbComGrid.Title" var="ministerTitle"/>
     </c:if>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
      <div class="widget">
         <div class="widget-header"><h2><strong>${ministerTitle}</strong></h2></div>
        <div class="widget-content padding">
        <div class="mand-label clearfix"><span>  <spring:message code="mandatoryField" /> <i class="text-red-1">*</i> <spring:message code="mandatory" /> </span></div>
          <!--Add Section Strat Here-->
        <form:form method="post" action="AdminCommissionerForm.html" name="frmAdminCommissionerForm" id="frmAdminCommissionerForm" class="form-horizontal">
		<form:hidden path="isChecker" id="isCheckerFlag"/>
		<form:hidden path="entity.chekkerflag1" id="chekkerflag1"/>
		 <jsp:include page="/jsp/tiles/validationerror.jsp" />
		
            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="pNameEn"><spring:message code="Com.Name" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.pNameEn" hasId="true" maxlegnth="100" cssClass="form-control mandClassColor subsize"
					isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="pNameReg"><spring:message code="mayorForm.Name" /><spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.pNameReg" hasId="true" maxlegnth="1000" cssClass="form-control subsize"
					isDisabled="" /></div>
             </div>

            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="designationEn"><spring:message code="Com.Designation" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.designationEn" maxlegnth="100" cssClass="form-control mandClassColor subsize"
					hasId="true" isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="designationReg"><spring:message code="Com.Designation" /><spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.designationReg" maxlegnth="1000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
              </div>

            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="linkTitleEn"><spring:message code="Com.Title" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.linkTitleEn" maxlegnth="150" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="linkTitleReg"><spring:message code="mayorForm.Title" /><spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.linkTitleReg" maxlegnth="1000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
              </div>

            <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="profileEn"><spring:message code="Com.Profile" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.profileEn" maxlegnth="4000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
                <label class="col-sm-2 control-label required-control" for="profileReg"><spring:message code="mayorForm.Profile" /><spring:message code="Regional" text="(hindi)" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.profileReg" maxlegnth="2000" cssClass="form-control subsize"
					hasId="true" isDisabled="" /></div>
              </div>
               <div class="form-group">
                <label class="col-sm-2 control-label" for="emailId"><spring:message code="Email" text="Emailid" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="profileMaster.emailId" maxlegnth="4000" cssClass="form-control"
					hasId="true" isDisabled="" /></div>
               
                <label class="col-sm-2 control-label" for="summaryEng"><spring:message code="mayorForm.msg" /></label>
                <div class="col-sm-4"><%-- <form:textarea path="profileMaster.summaryEng" maxlength="2000" cssClass="form-control subsize"/> --%>
				<apptags:inputField fieldPath="profileMaster.summaryEng"  cssClass="form-control hasFaxNo" hasId="true" isDisabled="" />
				</div>
              </div>
            <div class="form-group">
            <c:set var="currCount" value="0"/>
					<c:choose>
						<c:when test="${command.mode eq 'A'}">


							<label class="col-sm-2 control-label"
								for="profileMaster.imageName"><spring:message code="mayorForm.ProfileImage" text="Profile Image"/><span
								class="mand">*</span></label>
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


								<p class="help-block text-small">
									<spring:message code="eip.mayor.image.size" />
								</p>
								<p class="help-block text-small">
									<spring:message code="eip.announce.fileUploadText" />
								</p>
							</div>
				 <label class="control-label col-sm-2">
					<spring:message code="admin.commissioner.doj" text ="Date Of Joining" />
				</label>
				<div class="col-sm-4">
					 <apptags:dateField cssClass="form-control " datePath="profileMaster.dtOfJoin" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				
				</div>
						</c:when>
						<c:otherwise>

							<label class="col-sm-2 control-label"><spring:message code="mayorForm.ProfileImage" text="Profile Image"/> </label>
							<div class="col-sm-4">
								<img alt="Commissioner" src="./${command.imagesDetail}" class="img-thumbnail" style="height:150px">
								
								<apptags:formField fieldType="7"
									fieldPath="profileMaster.imageName" labelCode="" hasId="true"
									isMandatory="false" fileSize="COMMOM_MAX_SIZE"
									currentCount="${currCount}" showFileNameHTMLId="true"
									folderName="EIP_HOME" maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
								<p class="help-block text-small"><spring:message code="eip.mayor.image.size" />	</p>
								<p class="help-block text-small"><spring:message code="eip.announce.fileUploadText" /></p>	
								<%-- <apptags:filedownload filename="${command.image}" filePath="${command.imagesDetail}" actionUrl="AdminCommissioner.html?Download"></apptags:filedownload> --%>
							</div>
 				<label class="control-label col-sm-2">
					<spring:message code="admin.commissioner.doj" text ="Date Of Joining" />
				</label>
				<div class="col-sm-4">
					 <apptags:dateField cssClass="form-control " datePath="profileMaster.dtOfJoin" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				
				</div>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="form-group">
			<%-- 	<apptags:textArea labelCode="eip.cheker.remark" path="profileMaster.remark" cssClass="remark" maxlegnth="1000"></apptags:textArea> --%>
					<label class="col-sm-2 control-label" for="entity.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea path="profileMaster.remark" id = "entityRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
				</div>
			
			<div class ="form-group" id="AdminFaqCheker">				
				<div class="col-sm-12 text-center">
                    
                         <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton" aria-label="Authenticate"/> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton" aria-label="Reject"/> <spring:message code="Reject" text="Reject" />
                </div>					
			</div>
            
            <div class="text-center padding-top-10">
          <%--   <apptags:submitButton entityLabelCode="eip.admin.aboutUs.saveBtn" actionParam="save" successUrl="AdminAboutUs.html" cssClass="btn btn-success AdminFaqbutton"></apptags:submitButton>
			<apptags:submitButton entityLabelCode="eip.admin.aboutUs.saveBtn" actionParam="save" successUrl="AdminAboutUs.html?AdminFaqCheker" cssClass="AdminFaqChekerbutton btn btn-success"></apptags:submitButton>
			 --%>	
			<apptags:submitButton entityLabelCode="${ministerTitle}"
			isChildButton="false" successUrl="AdminCommissioner.html" cssClass="btn btn-success AdminFaqbutton" />
			<apptags:submitButton entityLabelCode="${ministerTitle}"
			isChildButton="false" successUrl="AdminCommissioner.html?AdminFaqCheker" cssClass="btn btn-success AdminFaqChekerbutton" />
			<c:if test="${command.mode eq 'A'}">
			<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetForm(this)" class="btn btn-warning"></input>
			</c:if>
			<a href="AdminCommissioner.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" text="Back"/></a>
			<a href="AdminCommissioner.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback" ><spring:message code="bckBtn" text="Back"/></a>
		</div>
          </form:form>
          </div>
          </div>
</div>