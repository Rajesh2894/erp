<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<script src="js/eip/admin/adminRegistrationForm.js"></script>


<script >
jQuery('.hasCharacter').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z]/g,'');
   
});

	$(function(e){
		$("#title").focus();
	});
	

function dispose() {
	
	$('.dialog').html('');
	$('.dialog').hide();
	disposeModalBox();
}


$('#resettButon').on('click', function()
{ $('.error-div').html("");
    $('#adminRegistrationForm').find('input:text, input:password, select, textarea').val('');
});


$( document ).ready(function() 
{	
	 
	$('.error1-div').hide();
	 $("#title").val($("#title option[code='O']").val());
		$("#empGender").val($("#empGender option[code='O']").val());
		set();
		$('.lessthancurrdate').datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			maxDate: '-0d',
			yearRange: "-100:-0"
		});
	fileUploadMultipleValidationList();
	test();

});
function set(){
var titleValue=$('#hiddenTitle').val();
	$('#title option[value="' + titleValue + '"]').prop('selected', true); 
	var genderValue = $('#hiddenGender').val();
	$('#empGender option[value="' + genderValue + '"]').prop('selected', true); 
}

function test(){
	var mode=$('#dpDeptid').val();

	var media=$("#hiddendesgid").val();	
	$('#desgid').find('option:gt(0)').remove();
	
	if (mode > 0) {
		var postdata = 'deptId=' + mode;
	
		var json = __doAjaxRequest('AdminRegistration.html?searchDesg', 'POST', postdata, false, 'json');

		var  optionsAsString='';
		for(var i = 0; i < json.length; i++) {
		    optionsAsString += "<option value='" + json[i].lookUpId + "'>" + json[i].lookUpDesc + "</option>";
		}
		$('#desgid').append( optionsAsString );
	}
	$('#desgid option[value="' + media + '"]').prop('selected', true);
	}
</script>

 <div class="popup-form-div widget margin-bottom-0">
<div class="widget-header margin-top-0 ">
	<h2 ><strong> <spring:message code="eip.admin.reg.FormHeader" /></strong></h2>
</div>
	 <b class="text-small text-red padding-left-5"><spring:message code="MandatoryMsg" text="MandatoryMsg"/> <spring:message code="EmailIdAndMobileNoLogin" text="NoteToUser"/></b>
    <div class="widget-content padding">
    <div id="basic-form">
	<form:form id="adminRegistrationForm" name="adminRegistrationForm" method="post" action="AdminRegistration.html" class="registration" role="form" autocomplete="off">
	<spring:message code='eip.select.gender' var="genderSel"/><spring:message code="eip.select.desg" var="desgSel"/>
    <spring:message code="eip.select.title" var="titleSel"/><spring:message code="eip.select.dept" var="deptSel"/>
	<div class="error1-div"></div>	
	
		<jsp:include page="/jsp/tiles/validationerror.jsp" />	
		
			 <div class="row form-group">
		       <div class="col-lg-3 col-md-6">
					<label for="title">
					
						<spring:message	code="eip.admin.reg.Title" text="Title " /> <span class="mand">*</span> 

					</label>
					
					
 				
						<c:set var="baseLookupCode" value="TTL"/>
						<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" 
					   					 path="entity.title"
										 cssClass=" titleClass form-control"																				
										 selectOptionLabelCode="${titleSel}" hasId="true"  isMandatory="true"/>
										 <form:hidden path="title" id="hiddenTitle" value="${command.title}"/>
										 <c:out value="${hiddenTitle}"></c:out>
					  
				 </div>	
			   <div class="col-lg-3 col-md-6">
					
					<label for="firstName">
					 
						<spring:message	code="eip.admin.reg.firstName" />  <span class="mand">*</span> :
					</label>
					
					
						<form:input path="entity.empname" maxlength="50"  cssClass="form-control mandClassColor empname  hasSpecialChara"/>
					  
			  </div>
			  <div class="col-lg-3 col-md-6">
					<label for="middleName">
						<spring:message	code="eip.admin.reg.MiddleName" />
					</label>
					
										<form:input path="entity.empMName" maxlength="50"  cssClass="form-control hasSpecialChara"/>
					
				</div>
				<div class="col-lg-3 col-md-6">
					<label for="lastName">
						
						<spring:message	code="eip.admin.reg.LastName" /><span class="mand">*</span> 
						
					</label>
					
						<form:input path="entity.empLName" maxlength="50"  cssClass="form-control mandClassColor  hasSpecialChara"/>
					    
					    </div>
        </div>
        <div class="row form-group">
          <div class="col-lg-3 col-md-6">
           <label for="entity.employeeNo">
						<spring:message	code="eip.admin.reg.empNo" /><span class="mand">*</span>
					</label>
					
										<form:input path="entity.employeeNo" maxlength="50"  cssClass="form-control"/>
					
				
       
          </div>
          <div class="col-lg-3 col-md-6">
            <label><spring:message	code="eip.admin.reg.empDept" /> <span class="mand">*</span></label>
            <apptags:lookupField items="${command.getDepartmentLookUp()}" 
					   					 path="entity.tbDepartment.dpDeptid"
					   					 changeHandler="fn_setDesignation(this,'desgid')"
										 cssClass="form-control"
										 selectOptionLabelCode="${deptSel}" hasId="true"  isMandatory="true"/>
             
          </div>
          <div class="col-lg-3 col-md-6">
            <label><spring:message code="eip.admin.reg.selectDesignation"  /> <span class="mand">*</span></label>
            <form:select cssClass="mandClassColor form-control"  path="entity.designation.dsgid"  id="desgid" isMandatory="true">
					<option value="0" >${desgSel}</option>
						</form:select>
					
          </div>
         <div class="col-lg-3 col-md-6">
            <label><spring:message	code="eip.agency.reg.Gender" text="Gender" />  <span class="mand">*</span></label>
           <c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" 
											 path="entity.empGender" cssClass="mandClassColor form-control" 
											 hasId="true" selectOptionLabelCode="${genderSel}" 
											 showAll="false"  isMandatory="true" />
											 <form:hidden path="gender" id="hiddenGender" value="${command.gender}"/>
          </div>
        </div>
         <div class="row form-group">
          
          <div class="col-lg-3">
            <label><spring:message	code="eip.agency.reg.dob" /> <span class="mand">*</span></label>
            <apptags:dateField fieldclass="lessthancurrdate" datePath="entity.empdob"  cssClass="mandClassColor form-control" ></apptags:dateField>
          </div>
           <div class="col-lg-3">
            <label><spring:message	code="eip.agency.reg.mail"/> <span class="mand">*</span></label>
            <form:input path="entity.empemail" maxlength="50"  cssClass="form-control"/>
          </div>
          <div class="col-lg-3">
            <label><spring:message	code="eip.agency.reg.mobNo"/> <span class="mand">*</span></label>
            <form:input path="entity.empmobno" maxlength="10"  cssClass="mandClassColor hasMobileNo form-control"/>
          </div>
          <div class="col-lg-3">
            <label><spring:message	code="eip.agency.reg.PanNo"/> <span class="mand">*</span></label>
            <form:input path="entity.panCardNo" maxlength="10"  cssClass="mandClassColor form-control"/>
          </div>
         
        </div>
         <div class="row form-group">
          <div class="col-lg-4">
           <label><spring:message	code="eip.admin.reg.address1" text="Parmananet Address"/> <span class="mand">*</span></label>
            <form:textarea path="entity.empAddress" maxlength="150"  rows="2" cssClass="form-control mandClassColor"/>
          </div>
          <div class="col-lg-4">
            <label><spring:message	code="eip.citizen.reg.address2" text="Corresponding Address"/> </label>
            <form:textarea path="entity.empAddress1" maxlength="150"  class="form-control" rows="2"/>
          </div>
          
        
        <div class="col-lg-4">
            <label><spring:message	code="eip.admin.reg.pincode" /> <span class="mand">*</span> </label>
            <form:input path="entity.pincode" maxlength="6"  cssClass="form-control mandClassColor hasPincode subsize" />
          </div> 
          </div>
        <div class="text-center">
          <input type="button" class="btn btn-info" value="<spring:message code="eip.commons.submitBT"/>" onclick="return _adminAjaxRequest(this, 'Registration Successful!','.popup-form-div','registerEmployee','');"/>
		  <input type="button" class="btn btn-primary"  value="<spring:message code="eip.commons.resetBT"/>" id="resettButon"  onclick="resetForm(this)"/> 	
        </div>
      
    </form:form>
    </div>
  </div>
</div>






