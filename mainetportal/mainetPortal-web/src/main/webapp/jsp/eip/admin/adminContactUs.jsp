<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/mainet/validation.js"></script>

<script>
$(document).ready(function(){
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if(pathname.indexOf(txt) > -1 || txt2=="Y") {
		//$("#AdminFaqCheker").show()
		$("#isCheckerFlag").val('Y');//resetting the vlaue in case of superAdmin for checking approved or rejected
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
	
		/* if($('.radiobutton:checked').val()=='Y')
         {
			$("#AdminFaqCheker").show();
			
         }
		
		if($('.radiobutton:checked').val()=='N')
			{
			$("#AdminFaqCheker").show();
			
			}
		} */
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
			/*  if($('.radiobutton:checked').val()=='Y')
	         {
				$("#AdminFaqCheker").show();
				$(".radiobutton").prop('disabled',true);
				$(".radiobutton").removeClass('mandClassColor');
				$(".radiobutton").addClass('disablefield');
	         }
			
			if($('.radiobutton:checked').val()=='N')
				{
				$("#AdminFaqCheker").show();
				$(".radiobutton").prop('disabled',true);
				$(".radiobutton").removeClass('mandClassColor');
				$(".radiobutton").addClass('disablefield');
				}  */
		}

		});

function saveOrUpdateContactUs(obj, message, successUrl, actionParam)
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

function resetForm(resetBtn) {

	cleareFile(resetBtn);
	
	if (resetBtn && resetBtn.form) {
		
		$('[id*=file_list]').html('');
		$('.error-div').remove();
		resetBtn.form.reset();

		
	};
	$('.form-control').val("");
}

$(document).ready(function () {
	
	jQuery('.phonNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9 -]/g,'');
	});
	});

$( document ).ready(function() {
	   
	
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$("#address2En").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#address2En").val().trim();
				if(no_spl_char!='')
				{
					commonlanguageTranslate(no_spl_char,'address2Reg',event,'');
				}else{
					$("#address2Reg").val('');
				}
			});
			$("#contactNameEn").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#contactNameEn").val().trim();
				if(no_spl_char!='')
				{
					commonlanguageTranslate(no_spl_char,'contactNameReg',event,'');
				}else{
					$("#contactNameReg").val('');
				}
			});
			$("#departmentEn").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#departmentEn").val().trim();
				if(no_spl_char!='')
				{
					
					commonlanguageTranslate(no_spl_char,'departmentReg',event,'');
				}else{
					$("#departmentReg").val('');
				}
			});
		}
});

$(function() {
	$("#adminContact21").validate();
});
$('select.form-control.mandColorClass').on('change',function(){
		     var check = $(this).val();
		     var validMsg =$(this).attr("data-msg-required");
		     if(check == '' || check == '0'){
		    		 $(this).parent().switchClass("has-success","has-error");
				     $(this).addClass("shake animated");
				     $('#error_msg').addClass('error');
				     $('#error_msg').css('display','block');
				     $('#error_msg').html(validMsg);}
		     else
		     {$(this).parent().switchClass("has-error","has-success");
		     $(this).removeClass("shake animated");
		     $('#error_msg').css('display','none');}
 });

</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" >
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="eip.citizen.footer.contactUs" /></strong>
			</h2>
		</div>
		<apptags:helpDoc url="AdminContactUs.html"></apptags:helpDoc>
		<div class="widget-content padding">
			<div class="mand-label text-right">
				<span> <spring:message code="mandatoryField" /> <i class="text-red-1">*</i> <spring:message code="mandatory" /> </span>
			</div>
						
			<form:form method="POST" action="AdminContactUs.html" name="adminContactUsFrm" id="adminContact21" class="form-horizontal">
			<div class="error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
			<form:hidden path="isChecker" id="isCheckerFlag"/>
			<form:hidden path="entity.chekkerflag1" id="chekkerflag1"/>
			<input type ="hidden" id ="mode" value="${command.mode }"/>
				<div class="error-div"></div>
				<c:if test="${not empty CONTACTUS_MSG}">
					<div class="message">
						<p>${CONTACTUS_MSG}</p>
					</div>
				</c:if>
				<br />
				<div class="form-group">
					<label class="col-sm-2 control-label">						
						<spring:message code="eip.admin.contactUs.contacttype" /><span class="mand">*</span>
					</label>
					<div class="col-sm-4 checkbox">
						<label><form:radiobutton path="entity.flag" class="contactUsType" name="contactUsType" value="P" id="flag1" /><spring:message code="eip.admin.contactUs.contacttypep" /></label>
						<label><form:radiobutton path="entity.flag" class="contactUsType" name="contactUsType" value="S" id="flag2" /><spring:message code="eip.admin.contactUs.contacttypes" /></label>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.address2En">
						<spring:message code="eip.admin.contactUs.address" /> &nbsp;
						<spring:message code="eip.admin.contactUs.english" /> 
					</label>
					<div class="col-sm-4">
						<form:textarea path="entity.address2En" maxlength="100" cssClass="form-control" id="address2En"/>
					</div>

					<label class="col-sm-2 control-label" for="address2Reg">
						<spring:message code="eip.admin.contactUs.address" /> &nbsp;
						<spring:message code="eip.admin.contactUs.regional"/>
					</label>
					<div class="col-sm-4">
						<form:textarea path="entity.address2Reg" maxlength="200" cssClass="form-control" id="address2Reg" />
					</div>

				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label required-control" for="entity.muncipalityName">
						<spring:message code="eip.admin.contactUs.muncipalitynm"/>&nbsp;
						<spring:message code="eip.admin.contactUs.english" />
					</label>
					<div class="col-sm-4">
						<apptags:inputField fieldPath="entity.muncipalityName" isMandatory="true" cssClass="form-control mandColorClass" maxlegnth="100"/>
					</div>

					<label class="col-sm-2 control-label required-control" for="muncipalityNameReg">
						<spring:message code="eip.admin.contactUs.muncipalitynm"/> &nbsp;
						<spring:message code="eip.admin.contactUs.regional"/>
					</label>
					<div class="col-sm-4">
						<apptags:inputField fieldPath="entity.muncipalityNameReg" isMandatory="true" cssClass="form-control mandColorClass" hasId="true" maxlegnth="200"/>
					</div>
				</div> --%>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.contactNameEn">
						<spring:message code="eip.admin.contactUs.name" /> &nbsp;
						<spring:message code="eip.admin.contactUs.english" /><span class="mand">*</span>
					</label>
					<div class="col-sm-4">
					<c:set var="contactNameEnglish" value="${command.getAppSession().getMessage('eip.admin.contactus.contactNameEnglish') }" />					
					<form:input id="contactNameEn" path="entity.contactNameEn" maxlength="100"
							cssClass=" form-control hasCharacterwithdot mandColorClass"  data-rule-required="true" data-msg-required="${contactNameEnglish}"/>					
					</div>
					<label class="col-sm-2 control-label" for="contactNameReg">
						<spring:message code="eip.admin.contactUs.name" /> &nbsp;
						<spring:message code="eip.admin.contactUs.regional" /><span class="mand">*</span>
					</label>
					<div class="col-sm-4">
					    <c:set var="contactNameRegional" value="${command.getAppSession().getMessage('eip.admin.contactus.contactNameRegional') }" />
						<form:input id="contactNameReg" path="entity.contactNameReg" maxlength="200"
							cssClass=" form-control mandColorClass"  data-rule-required="true" data-msg-required="${contactNameRegional}"/>						
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label" for="entity.departmentEn"><spring:message code="eip.admin.contactUs.dept.name"  text="Dept. Name"/>  &nbsp;
						<spring:message code="eip.admin.contactUs.english" /><span class="mand">*</span> 
					</label>
					<div class="col-sm-4">
					<c:set var="DeptNameEnglish" value="${command.getAppSession().getMessage('eip.admin.contactus.DeptNameEnglish') }" />
					<form:input id="departmentEn" path="entity.departmentEn" maxlength="100"
							cssClass=" form-control mandColorClass"  data-rule-required="true" data-msg-required="${DeptNameEnglish}"/>
					</div>

					<label class="col-sm-2 control-label" for="departmentReg"><spring:message code="eip.admin.contactUs.dept.name"  text="Dept. Name"/> &nbsp;
						<spring:message code="eip.admin.contactUs.regional"/><span class="mand">*</span> 
					</label>
					<div class="col-sm-4">
					     <c:set var="DeptNameRegional" value="${command.getAppSession().getMessage('eip.admin.contactus.DeptNameRegional') }" />
						<form:input id="departmentReg" path="entity.departmentReg" maxlength="100"
							cssClass=" form-control mandColorClass"  data-rule-required="true" data-msg-required="${DeptNameRegional}"/>					
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="entity.designationEn"><spring:message code="Employee.designation.dsgid"/> 
					</label>
					
						<%-- <apptags:inputField fieldPath="entity.designationEn" isMandatory="true" cssClass="form-control mandColorClass" maxlegnth="100" hasId="true"/>
				 --%>
				 <%-- <c:set var="categoryTypePrefix" value="PDL" />
							    <apptags:lookupField
								items="${command.getLevelData(categoryTypePrefix)}" path="entity.designationEn"
								cssClass="form-control required-control chosen-select-no-results"
								 selectOptionLabelCode="selectdropdown"
								hasId="true" /> --%>
								
					<div class="col-sm-4">
						<c:set var="DesigMsg" value="${command.getAppSession().getMessage('eip.admin.contactus.Designation') }"></c:set>
						<form:select path="entity.designationEn" cssClass="form-control mandColorClass chosen-select-no-results" aria-label="title" 
							data-rule-required="true" data-msg-required="${DesigMsg}">
							<c:set var="categoryTypePrefix" value="PDL" />
							<form:option value=""><spring:message code="Select" text="select" /></form:option>
								<c:forEach items="${command.getLevelData(categoryTypePrefix)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
						</form:select>
						<label id="error_msg" style="display:none; border:none;"></label>
					</div>				
				 	
					<%-- <label class="col-sm-2 control-label required-control" for="designationReg">
						<spring:message code="Employee.designation.dsgid" /> &nbsp;
						<spring:message code="eip.admin.contactUs.regional" />
					</label>
					<div class="col-sm-4">
						<apptags:inputField fieldPath="entity.designationReg" isMandatory="true" cssClass="form-control mandColorClass" hasId="true" maxlegnth="200" />
					</div> --%>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.telephoneNo1En">
						<spring:message code="eip.admin.contactUs.phoneNo" />
					</label>
					<div class="col-sm-4">
						<apptags:inputField cssClass="form-control phonNo" fieldPath="entity.telephoneNo1En" isMandatory="true" hasId="true" maxlegnth="12" />
					</div>
					<label class="col-sm-2 control-label" for="entity.email1">
						<spring:message code="eip.admin.contactUs.emailAddress" />
					</label>
					<div class="col-sm-4">
						<apptags:inputField cssClass="form-control" fieldPath="entity.email1" maxlegnth="100" hasId="true"/>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label" for="entity.telephoneNo2En"><spring:message code="eip.admin.auth.mobNo"  text="Mobile No."/></label>
					<div class="col-sm-4">
						<apptags:inputField cssClass="hasNumber form-control" fieldPath="entity.telephoneNo2En" isMandatory="true" maxlegnth="10" />
					</div>
					<label class="col-sm-2 control-label" for="entity.sequenceNo">
						<spring:message code="eip.admin.contactUs.seqno" /><span class="mand">*</span> 
					</label>
					<c:set var="SequenceNo" value="${command.getAppSession().getMessage('eip.admin.contactus.SequenceNo') }" />
					<div class="col-sm-4">
						<form:input path="entity.sequenceNo" cssClass="form-control mandColorClass hasNumber" isMandatory="true" maxlength="3" data-rule-required="true" data-msg-required="${SequenceNo}"/>
					</div>
				</div>
				<div class="form-group">
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
				<%-- <div class="form-group">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<label for="feedback.feedBackSubject"> <spring:message code="contactus.designation" text = "Designation"/> </label>
								<c:set var="categoryTypePrefix" value="PDL" />
							    <apptags:lookupField
								items="${command.getLevelData(categoryTypePrefix)}" path="feedback.catagoryType"
								cssClass="form-control required-control chosen-select-no-results"
								 selectOptionLabelCode="selectdropdown"
								hasId="true" />
							</div>	   
						</div> 
				</div> --%>
				<div class="form-group padding-top-10" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                      <%--  <label class="checkbox-inline" for="chekkerflag1">
                       <form:checkbox path="entity.chekkerflag" value="Y" id="chekkerflag1" required ="true"/> <spring:message code="Authenticate" text="Authenticate" /></label> --%>
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton"/> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton"/><spring:message code="Reject" text="Rejection" />
	                </div>					
				</div>			

				<div class="text-center padding-top-10">
					<input class="btn btn-success AdminFaqChekerbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateContactUs(this,'', 'AdminContactUsList.html?AdminFaqCheker','save');" id="getFormId"></input>
					<input class="btn btn-success AdminFaqbutton" type="submit" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateContactUs(this,'', 'AdminContactUsList.html','save');" id="getFormId"></input>
					<%-- <apptags:submitButton entityLabelCode="eip.admin.contactUs.title" successUrl="AdminContactUsList.html" cssClass=" AdminFaqbutton btn btn-success" />
					<apptags:submitButton entityLabelCode="eip.admin.contactUs.title" successUrl="AdminContactUsList.html?AdminFaqCheker" cssClass="AdminFaqChekerbutton btn btn-success" /> --%>
					<c:if test="${command.mode eq 'A'}">
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormOnAdd(adminContact21)" class="btn btn-warning"></input>
					</c:if>
					<a href="AdminContactUsList.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" text="Back"/></a>
					<a href="AdminContactUsList.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback"><spring:message code="bckBtn" text="Back"/></a>
				</div>
			</form:form>
		</div>
	</div>
</div>