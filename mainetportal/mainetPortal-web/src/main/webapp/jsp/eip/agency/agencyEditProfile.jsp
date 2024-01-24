<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="js/eip/agency/agencyForgotPasswordProcess.js"></script>

 <script>
 jQuery('.hasPincode').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','6');
});

 </script>
 
 <script>


    $(document).ready(function(){
       setInterval(maxSizeDate, 500);
    });

    function maxSizeDate() {
		var lastchar = $("input[name='currentEmpForEditProfile.empdob']").val().substring(0, 10);
		$("input[name='currentEmpForEditProfile.empdob']").val(lastchar);
	}

    function saveForm(element,successurl) {
     	
     	 var errorList = [];
         errorList = validateDob($("input[name='currentEmpForEditProfile.empdob']").val());

         if($('#empGender').val() != '0' && $('#title').val() != '0')  {

           if($('#title :selected').attr('code').toLowerCase() == 'mr.'){
     			if($('#empGender :selected').attr('code').toLowerCase() == 'm' || $('#empGender :selected').attr('code').toLowerCase() == 't'){
     			 }else{
     				errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
     			}
     		}else if($('#title :selected').attr('code').toLowerCase() == 'mrs.' || $('#title :selected').attr('code').toLowerCase() == 'miss.'){
                  if($('#empGender :selected').attr('code').toLowerCase() == 'f' || $('#empGender :selected').attr('code').toLowerCase() == 't'){
     			 }else{
     				 errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
     			 }
     		}
     		
     	}
         if (errorList.length == 0) {
     	       return saveOrUpdateForm(element,'Your information has been saved successfully!',successurl,'CitizenEditSave');
     	   }else{
     		   $('.error-dialog').html(errorList);
     		   showModalBox('.error-dialog');
     		   return false;
     	}  
     	
     }

     function changeCitizenEmailId() {
    	 
    	 openPopup('CitizenHome.html?viewEmialId');
    	 
     }
    
</script>
<div class="form-div">



<h2 align="center"><spring:message code="citizen.editProfile.header" text="Edit Profile"></spring:message></h2>


<div class="clearfix" id="home_content">

	<div class="col-xs-12">

		<div class="row">
		<div id="content">
			
			
		<form:form action="CitizenHome.html" class="form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.title" text="Title" /> :</label>
						<c:set var="baseLookupCode" value="TTL"/>
						<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" 
					   					 path="currentEmpForEditProfile.title"
										 cssClass="titleClass subsize"																				
										 selectOptionLabelCode="--Select Your Title--"  hasId="true" isMandatory="true"/><span class="mand">*</span>
					</div>
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.firstName" text="First Name" /> :</label>
						<apptags:inputField fieldPath="currentEmpForEditProfile.empname" isMandatory="true" cssClass="hasSpecialChara"></apptags:inputField><span class="mand">*</span>
					</div>
				</div>
				
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.middleName" text="Middle Name" /> :</label>
						<apptags:inputField fieldPath="currentEmpForEditProfile.empMName" cssClass="hasSpecialChara"></apptags:inputField>
					</div>
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.lastName" text="Last Name" /> :</label>
						<apptags:inputField fieldPath="currentEmpForEditProfile.empLName" isMandatory="true" cssClass="hasSpecialChara"></apptags:inputField><span class="mand">*</span>
					</div>
				</div>
				
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.gender" text="Gender" /> :</label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" 
											 path="currentEmpForEditProfile.empGender" cssClass="subsize" 
											 hasId="true" selectOptionLabelCode="--Select Your Gender--" 
											 showAll="false" isMandatory="true"/><span class="mand">*</span>
					</div>
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.dateOfBirth" text="Date Of Birth" /> :</label>
						<apptags:dateField fieldclass="lessthancurrdate" datePath="currentEmpForEditProfile.empdob" cssClass="maxSizeDate mandClassColor" isMandatory="true" /><span class="mand">*</span>
					</div>
				</div>
				
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.emailId" text="E-mail ID" /> :</label>
						<apptags:inputField fieldPath="currentEmpForEditProfile.empemail" isDisabled="true" isReadonly="true"></apptags:inputField>
						<div class="pull-element">[<a href="javascript:void(0);" onclick="changeCitizenEmailId('Nonregister');" ><spring:message code="eip.mail.change"/> </a>]</div>
					</div>
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.mobNo" text="Mobile No." /> :</label>
						<apptags:inputField fieldPath="currentEmpForEditProfile.empmobno" isMandatory="true" isDisabled="true" isReadonly="true"></apptags:inputField><span class="mand">*</span>
						<div class="pull-element">[<a href="javascript:void(0);" onclick="getAgencyForgotPassStep1('Nonregister');" ><spring:message code="eip.mobile.change"/></a>]</div>
					</div>
				</div>
				
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.permAddress" text="Permanent Address" /> :</label>
					</div>
				</div>
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="eip.citizen.reg.add2" text="Line 1" /> :</label>
						<form:textarea path="currentEmpForEditProfile.empAddress" maxlength="150" rows="2" cssClass="mandClassColor" /> <span class="mand">*</span>
					</div>
					<div class="element">
						<label for=""><spring:message code="eip.citizen.reg.add1" text="Line 2" /> :</label>
						<form:textarea path="currentEmpForEditProfile.empAddress1" maxlength="150" rows="2" />
					</div>
				</div>
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.pincode" text="Pincode" /> :</label>
						<apptags:inputField fieldPath="currentEmpForEditProfile.pincode" isMandatory="true" cssClass="hasPincode" maxlegnth="6"></apptags:inputField><span class="mand">*</span>
					</div>
				</div>
				
				<div class="form-elements">
					<div class="elementLabel">
						<label for=""><spring:message code="citizen.editProfile.corrAddress" text="Correspondence Address" /> :</label>
					</div>
				</div>
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="eip.citizen.reg.add2" text="Line 1" /> :</label>
						<form:textarea path="currentEmpForEditProfile.empCorAddress1" maxlength="150" rows="2" cssClass="mandClassColor" /> <span class="mand">*</span>
					</div>
					<div class="element">
						<label for=""><spring:message code="eip.citizen.reg.add1" text="Line 2" /> :</label>
						<form:textarea path="currentEmpForEditProfile.empCorAddress2" maxlength="150" rows="2" />
					</div>
				</div>
				<div class="form-elements">
					<div class="element">
						<label for=""><spring:message code="citizen.editProfile.pincode" text="Pincode" /> :</label>
						<apptags:inputField fieldPath="currentEmpForEditProfile.corPincode" isMandatory="true" cssClass="hasPincode" maxlegnth="6"></apptags:inputField><span class="mand">*</span>
					</div>
				</div>
				
				<br>
				<br>
				<div class="btn_fld margin_top_10">
					<input type="submit" class="css_btn" onclick="return saveForm(this,'CitizenHome.html');"
					value="<spring:message code="citizen.editProfile.submit" text="Submit" />" />
					<input type="reset" class="css_btn" value="<spring:message code="citizen.editProfile.reset" text="Reset" />"/>
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				</div>
				
				</form:form>
			
				</div>
			</div>
		</div>
		
	</div>
	
</div>
