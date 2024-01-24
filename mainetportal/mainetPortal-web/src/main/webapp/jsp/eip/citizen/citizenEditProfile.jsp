<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<script src="js/eip/citizen/citizenForgotPasswordProcess.js"></script>
<script  src="js/mainet/file-upload.js"></script>
<script  src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script  src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script>
$( document ).ready(function()
		{
			jQuery('.hasMobileNoExt').keyup(function () 
			{ 
			    this.value = this.value.replace(/[^0-9 +]/g, '');
			    $(this).attr('maxlength','6');
			});
		});
		
$(function() {
	$("#citizenEditPage").validate();
});
</script>

<ol class="breadcrumb">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i> <spring:message code="menu.home" /></a></li>
      <li><spring:message code="eip.citizen.User.Proile" text="User Profile" /></li>
   </ol>
  
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="login-heading"><spring:message code="eip.citizen.User.Proile" text="User Profile" /></h2>
		</div>
	<!-- 	<div class="error-div"></div> -->
		<div class="widget-content padding">
		    <form:form action="CitizenHome.html" class="form-horizontal" id="citizenEditPage" name="citizenEditPage">
		    <div class="error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
				<div class="mand-label clearfix">
					<span>Field with <i class="text-red-1">*</i> is mandatory<br>
					<spring:message code="EmailIdAndMobileNoLogin" text="NoteToUser" /></span>
				</div>
				
				<spring:message code='eip.select.gender' var="genderSel" />
				<spring:message code="eip.select.title" var="titleSel" />
				<spring:message code='eip.captcha.placeholder' var="captchaP" />
				<spring:message code='eip.select.occupation' var="selOccupation" />
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 profile-img">
					<div class="thumbnail">

						<c:choose>
							<c:when test="${ not empty  command.logInUserImage}">
								<span class="profile-img"><img
									src="./${command.logInUserImage}" alt="Gender" /></span>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${(not empty command.empGenderCode ) and  command.empGenderCode eq 'F'}">
										<span class="profile-img"><img
											src="./assets/img/default-female.png" alt="Default Female" /></span>
									</c:when>
									<c:otherwise>
										<span class="profile-img"><img
											src="./assets/img/default-male.png" alt="Default Male" /></span>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<h4><spring:message code="ProfileMaster.imageName" text="Profile Image" /></h4>
					</div>
				</div>

				<div class="form-group">
              		<label for="title" class="col-sm-2 control-label required-control"><spring:message code="eip.citizen.reg.title" text="Title" /></label>						
              		<c:set var="baseLookupCode" value="TTL" />
							<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="currentEmpForEditProfile.title" cssClass="form-control"
							selectOptionLabelCode="${titleSel}" hasId="true"
							isMandatory="true" />

					<label for="currentEmpForEditProfile.empname" class="col-sm-2 control-label required-control"><spring:message code="eip.citizen.reg.firstName" /></label>
					<div class="col-sm-4">
						<form:input path="currentEmpForEditProfile.empname" maxlength="50"
							cssClass="form-control hasCharacter" data-rule-required="true" data-msg-required="First Name must not be empty"/>

					</div>
				</div>
            	<div class="form-group">
					<label for="currentEmpForEditProfile.empMName" class="col-sm-2 control-label"><spring:message code="eip.citizen.reg.MiddleName" /></label>
					<div class="col-sm-4">
						<form:input path="currentEmpForEditProfile.empMName"
							maxlength="50" cssClass="form-control hasCharacter" />
					</div>
					<label for="currentEmpForEditProfile.empLName" class="col-sm-2 control-label required-control"><spring:message code="eip.citizen.reg.LastName" /></label>
					<div class="col-sm-4">
						<form:input path="currentEmpForEditProfile.empLName"
							maxlength="50" cssClass="form-control hasCharacter" data-rule-required="true" data-msg-required="Last Name must not be empty" />
					</div>
				</div>
				
				<div class="form-group">
					<label for="empGender" class="col-sm-2 control-label required-control"><spring:message code="eip.citizen.reg.Gender" text="Gender" /></label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="currentEmpForEditProfile.empGender" cssClass="form-control"
							hasId="true" selectOptionLabelCode="${genderSel}" showAll="false"
							isMandatory="true" />

					<label for="currentEmpForEditProfile.empdob" class="col-sm-2 control-label"><spring:message code="eip.citizen.reg.dateOfBirth" text="Date Of Birth" /></label>
					<div class="col-sm-4">
						<apptags:dateField fieldclass="lessthancurrdate"
							datePath="currentEmpForEditProfile.empdob"
							cssClass="datepicker form-control" />

					</div>
					</div>
					<div class="form-group">
						<label for="currentEmpForEditProfile.empemail" class="col-sm-2 control-label" for="newCitizen.empemail"> 
							<spring:message code="eip.citizen.reg.emailAddress" /> 
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.empemail" maxlength="50" cssClass="form-control" readonly="true"/>
							<%-- <a href="javascript:void(0);" onclick="changeCitizenEmailId('Nonregister');">(<spring:message code="eip.mail.change" />)</a> --%>
						</div>
						<label for="currentEmpForEditProfile.empmobno" class="col-sm-2 control-label required-control" for="newCitizen.empmobno"><spring:message code="eip.citizen.reg.mobileNo" /></label>
						<div class="col-sm-1">
							<form:input path="currentEmpForEditProfile.mobileExtension" id="Ext" maxlength="6" cssClass="form-control hasMobileNoExt" data-rule-required="true" data-msg-required="Country code must not be empty"/>
						</div>
						<div class="col-sm-3">
							<form:input path="currentEmpForEditProfile.empmobno" maxlength="12" cssClass="form-control hasMobileNo " readonly="true" data-rule-required="true" data-msg-required="Mobile No. must not be Empty"/>
							<a href="javascript:void(0);" onclick="getCitizenForgotPassStep1('Nonregister');"><br>(<spring:message code="eip.mobile.change" />)</a>
						</div>
					</div>
					<div class="form-group">
						<label for="currentEmpForEditProfile.empAddress" class="col-sm-2 control-label required-control" for="newCitizen.empAddress"> 
							<spring:message code="eip.citizen.reg.address1" text="Parmananet Address" />
							<spring:message code="eip.citizen.reg.add2" />
						</label>
						<div class="col-sm-4">
							<form:textarea path="currentEmpForEditProfile.empAddress"
							maxlength="150" rows="2" cssClass="form-control" data-rule-required="true" data-msg-required="Permanent Address must not be Empty"/>
						</div>
						<label for="currentEmpForEditProfile.empAddress1" class="col-sm-2 control-label required-control" for="newCitizen.empAddress1">
							<spring:message code="eip.citizen.reg.add1" />
						</label>
						<div class="col-sm-4">
							<form:textarea path="currentEmpForEditProfile.empAddress1"
							maxlength="150" rows="2" cssClass="form-control" data-rule-required="true" data-msg-required="Taluka/Town/City/District/State must not be Empty"/>
						</div>
					</div>

					<div class="form-group" id="pincode">
						<label for="currentEmpForEditProfile.pincode" class="col-sm-2 control-label required-control">
							<spring:message code="eip.citizen.reg.pincode" />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.pincode" cssClass="hasPincode form-control" data-rule-required="true" data-msg-required="Pincode must not be Empty"/>
						</div>
						
						<label for="currentEmpForEditProfile.occupation" class="col-sm-2 control-label">
							<spring:message code="eip.citizen.reg.occupation" text="Occupation" />
						</label>
						<%-- <div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.occupation" maxlength="50" cssClass="form-control" />
						</div> --%>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="currentEmpForEditProfile.occupation" cssClass="form-control"
							hasId="true" selectOptionLabelCode="${selOccupation}" showAll="false"
							isMandatory="true" />
					</div>
					
					<div class="form-group">
						
						
						<label for="currentEmpForEditProfile.empuid" class="col-sm-2 control-label">
							<spring:message code="eip.citizen.reg.uid" text="UID No." />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.empuid" maxlength="12" cssClass="form-control hasNumber minLength12" />
						</div>
						<label for="currentEmpForEditProfile.spouse" class="col-sm-2 control-label">
							<spring:message code="eip.citizen.reg.familydetails" text="Family details" />
						</label>
						
						<div class="col-sm-4">
							<form:checkbox path="currentEmpForEditProfile.spouse" value="Y" onclick='handleClick(this);' id="chekkerflag1" required ="true"/> <spring:message code="eip.citizen.reg.spouse" text=" Spouse" />							
						</div>
					</div>
					
					<div class="form-group" id="familyNamesId">
						
						<label for="currentEmpForEditProfile.spFirstName" class="col-sm-2 control-label">
							<spring:message code="eip.citizen.reg.fname" text="Spouse FirstName" />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.spFirstName" maxlength="12" cssClass="form-control hasCharacter"/>
						</div>
						
						<label for="currentEmpForEditProfile.spLastName" class="col-sm-2 control-label">
							<spring:message code="eip.citizen.reg.lname" text="Spouse LastName" />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.spLastName" maxlength="12" cssClass="form-control hasCharacter"/>
						</div>
					</div>
					
					
					<div class="form-group"  id="childrenFmGroupId">
						
						
						<label for="currentEmpForEditProfile.children" class="col-sm-2 control-label">
							<div id="familyNamesId2"><spring:message code="eip.citizen.reg.children" text="Children's No" /></div>
						</label>
						
						<div class="col-sm-4"  style="padding:0px">
						
						<div class="col-sm-6" id="familyNamesId1">
							<form:select path="currentEmpForEditProfile.children" class="form-control">
							<form:option value="1" label="1"/>
							<form:option value="2" label="2"/>
							<form:option value="3" label="3"/>
							<form:option value="4" label="4"/>
							<form:option value="5" label="5"/>
							<form:option value="6" label="6"/>
							<form:option value="7" label="7"/>
							<form:option value="8" label="8"/>
							<form:option value="9" label="9"/>
							<form:option value="10" label="10"/>
							</form:select>
						</div>
						</div>
						
						
					</div>
					
					<div class="form-group">
						
						<label for="currentEmpForEditProfile.panCardNo" class="col-sm-2 control-label ">
							<spring:message code="eip.citizen.reg.panno" text="PAN No." />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.panCardNo" style="text-transform:uppercase" maxlength="10" minlength="10" cssClass="form-control hasAlphaNumeric" />
						</div>
							
						<label for="currentEmpForEditProfile.passportNo" class="col-sm-2 control-label ">
							<spring:message code="eip.citizen.reg.passport" text="Passport" />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.passportNo" style="text-transform:uppercase" maxlength="8" minlength="8" cssClass="form-control hasAlphaNumeric" />
						</div>
					</div>
					<div class="form-group">
						
						<label for="currentEmpForEditProfile.voterNo" class="col-sm-2 control-label ">
							<spring:message code="eip.citizen.reg.voterno" text="Voter Registration No" />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.voterNo" style="text-transform:uppercase" maxlength="10" minlength="10" cssClass="form-control hasAlphaNumericDash" />
						</div>
						
						<label for="currentEmpForEditProfile.identityNo" class="col-sm-2 control-label ">
							<spring:message code="eip.citizen.reg.identityno" text="Any Other Identity No" />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.identityNo" style="text-transform:uppercase" maxlength="12" minlength="10" cssClass="form-control hasAlphaNumeric" />
						</div>
					</div>
					
					<div class="form-group">
						
						<label for="currentEmpForEditProfile.licenseNo" class="col-sm-2 control-label ">
							<spring:message code="eip.citizen.reg.licenceno" text="License No" />
						</label>
						<div class="col-sm-4">
							<form:input path="currentEmpForEditProfile.licenseNo" maxlength="16" minlength="16" cssClass="form-control hasAlphaNumeric" />
						</div>

 <label class="col-sm-2 control-label "> <spring:message code="profile.image" text="Profile Image"/></label>
              <div class="col-sm-4">

	  <form:hidden path="currentEmpForEditProfile.empphotopath" id ="empphotopath"/>
	  	
	 <%--   <form:hidden path="logInUserImage" id ="logInUserImage"/>	 --%>													              
					<c:if
						test="${ not empty  command.logInUserImage}">
						<c:set var="imageName" value="${stringUtility.getStringAfterChar('/',command.logInUserImage)}" />
						<div class="card border-light" id="imgThumbId">
							<img alt="Citizen Image" src="./${command.logInUserImage}"
								class="img-thumb">
							<div class="card-header1">
								<i class="fa fa-picture-o red-thumb" aria-hidden="true"> </i>
								<div class="file-name">
									<span>${imageName}</span>
								</div>
								<a href="#" class="close1" onclick="" id="deleteImg"
									title="Close"> <i class="fa fa-times" aria-hidden="true"
									id="deleteImg"></i></a>
							</div>
						</div>
					</c:if>
					
						<apptags:formField fieldType="7" hasId="true"
							fieldPath="currentEmpForEditProfile.empphotopath"
							isMandatory="false" labelCode="" currentCount="0"
							showFileNameHTMLId="true" folderName="EIP_HOME"
							fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
							cssClass="form-control" callbackOtherTask="otherTaskWithImageThumbNail('CitizenRegistration.html');deleteImageTest()"/>
							<%-- <p class="help-block text-small"><spring:message code="eip.mayor.image.size" />	</p> --%>
							<p class="help-block text-small text-red"><spring:message code="water.plumberLicense.uploadedPhoto.validatn" text=" " /></p>
                           
                           <div id="uploadPreview" class="col-sm-4">
								 <ul></ul> 
							</div>
				</div>
				</div>	
				
				<div class="text-center">
					<input type="button" class="btn btn-success" onclick="return saveForm(this,'','CitizenHome.html','');" value="<spring:message code="citizen.editProfile.submit" text="Submit" />" />
				<%-- 	<input type="reset" class="btn btn-warning" value="<spring:message code="citizen.editProfile.reset" text="Reset" />" /> --%> <!-- 8442 -->
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				</div> 
				
				
			</form:form>
		</div>
	</div>
	</div>
<script>
jQuery('.hasPincode').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','6');
});

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});	
jQuery('.hasCharacter').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z]/g,'');
   
});
jQuery('.hasAlphaNumeric').blur(function () { 		
    this.value = this.value.replace(/[^a-zA-Z0-9]/g,'');
});
jQuery('.hasAlphaNumericDash').blur(function () { 		
    this.value = this.value.replace(/[^a-zA-Z0-9 -]/g,'');
});

jQuery('.hasCharacterInUperCase').keyup(function () { 
	
	this.value = this.value.replace(/[^a-z A-Z]/g,'').toUpperCase();
	
});

jQuery('.minLength10').keyup(function () { 
	 $(this).attr('minlength','10');
 
});
jQuery('.minLength12').keyup(function () { 
	 $(this).attr('minlength','12');

});

jQuery('.minLength16').keyup(function () { 
	 $(this).attr('minlength','16');

});
$("#deleteImg").click(function() {
	
	$('#imgThumbId').remove();
	$('#empphotopath').val('');
	
});

	$(document).ready(function() {
		
		var flag = false;
		if ($("#chekkerflag1").is(':checked')){
		    flag = true;
		}
		if(flag === false){
		  $("#familyNamesId").hide();
		  $("#childrenFmGroupId").hide();
	  	}
		
		<%-- setInterval(maxSizeDate, 500); --%>
		var dob = $("input[name='currentEmpForEditProfile.empdob']");
		dob.val(dob.val().substring(0, 10));		
		$('.datepicker').datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			maxDate: 0,
			yearRange: "-100:+0"
		});
	});
	
	
	
	<%-- function maxSizeDate() {
		var lastchar = $("input[name='currentEmpForEditProfile.empdob']").val()
				.substring(0, 10);
		$("input[name='currentEmpForEditProfile.empdob']").val(lastchar);
	} --%>

	

	function changeCitizenEmailId() {
		openPopup('CitizenHome.html?viewEmialId');

	}
	
	function handleClick(cb) {
		  
		  if(cb.checked === false){
			  $("#familyNamesId").hide();
              $("#childrenFmGroupId").hide();
		  }else{
			  $("#familyNamesId").show();
			  $("#childrenFmGroupId").show();
		  }
	}
	
	function deleteImageTest(){
		
		$("#deleteImg").click();
	}


</script>