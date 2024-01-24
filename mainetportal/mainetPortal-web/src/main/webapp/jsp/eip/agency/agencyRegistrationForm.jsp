
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>

<script src="js/eip/agency/agencyRegistrationForm.js"></script>
<script>
$(document).ready(function() {
	$('#resettButton').on('click',function() {
						$('#agencyRegistrationForm').find('input:text, input:password, select, textarea').val('');
						$('.alert').hide();
	})
});

$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});

	$(function(e){
		$("#emplType").focus();
	});

 function dispose() {
	
	$('.dialog').html('');
	$('.dialog').hide();
	disposeModalBox();
}

function tryLogin(e) {
    if (e.keyCode == 13) {
    	doAgencyLogin();
        return false;
    }
}
	
	function myFunction(){
		$("#title").val($("#title option[code='0']").val());
		$("#empGender").val($("#empGender option[code='O']").val());
		$("#emplType").val($("#emplType option[code='O']").val());
	}

$('#title').on('change', function(){
    var currentVal = $(this).val();
    var localStorage=0;
    localStorage.setItem('selectVal', currentVal );
});

function set(){
	var titleValue=$('#hiddenTitle').val();
		$('#title option[value="' + titleValue + '"]').prop('selected', true); 
		var genderValue = $('#hiddenGender').val();
		$('#empGender option[value="' + genderValue + '"]').prop('selected', true);
		var empTypeValue = $('#hiddenEmpType').val();
				$('#emplType option[value="' + empTypeValue + '"]').prop('selected', true);
	}
	
 $( document ).ready(function() {	
	 $('.error1-div').hide();
	 $("#emplType").val($("#emplType option[code='O']").val());
	 $("#title").val($("#title option[code='O']").val());
		$("#empGender").val($("#empGender option[code='O']").val());
		set();



		$('.lessthancurrdate').datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			maxDate: '-0d',
			yearRange: "-100:-0",
			onClose  : function() { $input.change(); }
			});
		
		

jQuery('.hasPinNo').keyup(function () { 
    $(this).attr('maxlength','10');
});
jQuery('.hasMobileNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','10');
});
jQuery('.hasCharacter').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z]/g,'');
   
}); 
}); 

 </script>
 <div class="row padding-40">
      <div class="col-md-8 col-md-offset-2">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
  <div class="widget-header">
    <h2><strong><spring:message code="eip.agency.reg.FormHeader" ></spring:message></strong></h2>
  </div>
     
	<div class="error-div"></div>
	
  <div class="widget-content padding">
  <div class="mand-label clearfix"><span><spring:message code="MandatoryMsg" text="MandatoryMsg"/><br>
	<spring:message code="EmailIdAndMobileNoLogin" text="NoteToUser"/></span></div>
    <div id="basic-form">
      <form:form id="agencyRegistrationForm" name="agencyRegistrationForm" method="POST" action="AgencyRegistration.html" class="registration" autocomplete="off">
         <spring:message code='eip.select.gender' var="genderSel"/>
        <spring:message code="eip.select.title" var="titleSel"/><spring:message code="eip.select.type" var="typeSel"/> 
	    <div class="error1-div"></div>
	    <jsp:include page="/jsp/tiles/validationerror.jsp" />
        <div class="row ">
          <div class="col-sm-6 margin-top-5">
			
					<label for="type">
					<spring:message	code="eip.agency.reg.type" text="Type of Agency " /> <span class="mand">*</span> 

					</label>
 			
 			
						<c:set var="baseLookupCode" value="NEC"/>
		<form:select path="entity.emplType" class="form-control">
			<c:set var="baseLookupCode" value="NEC"/>
			<form:option value="">Select</form:option>
			<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
				<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
			</c:forEach>
		</form:select>
			</div>
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.Name" /> <span class="mand">*</span></label>
            <form:input path="entity.agencyName" maxlength="250"  cssClass="mandClassColor form-control"/>
          </div>
        </div>
        <div class="row ">
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.registration.title" text="Title " /> <span class="mand">*</span></label>
            <c:set var="baseLookupCode" value="TTL"/>
		<form:select path="entity.title" class="form-control">
			<c:set var="baseLookupCode" value="TTL"/>
			<form:option value="">Select</form:option>
			<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
				<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
			</c:forEach>
		</form:select>
				
					 <form:hidden path="title" id="hiddenTitle" value="${command.title}"/>
          </div>
           <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.Owner"/> <span class="mand">*</span></label>
            <form:input path="entity.empname" maxlength="50"  cssClass="mandClassColor hasSpecialChara subsize form-control"/>
          </div>
          </div> 
           <div class="row ">
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.OwnerMname"/></label>
           <form:input path="entity.empMName" maxlength="50"  cssClass="mandClassColor hasSpecialChara subsize  form-control"/>
          </div>
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.OwnerLname"/> <span class="mand">*</span></label>
            <form:input path="entity.empLName" maxlength="50"  cssClass="mandClassColor hasSpecialChara subsize  form-control"/>
          </div> 
        </div>
        <div class="row ">
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.Gender" text="Gender" />  <span class="mand">*</span></label>
			<form:select path="entity.empGender" class="form-control">
			<c:set var="baseLookupCode" value="GEN"/>
			<form:option value="">Select</form:option>
			<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
				<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
			</c:forEach>
		</form:select>
		<form:hidden path="gender" id="hiddenGender" value="${command.gender}"/>
          </div>
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.dob" /> <span class="mand">*</span></label>
            <apptags:dateField fieldclass="lessthancurrdate" datePath="entity.empdob"  cssClass="mandClassColor form-control" ></apptags:dateField>
          </div>
          </div>
          <div class="row ">
           <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.mail"/> <span class="mand">*</span></label>
            <form:input path="entity.empemail" maxlength="50"  cssClass="form-control"/>
          </div>
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.mobNo"/> <span class="mand">*</span></label>
            <form:input path="entity.empmobno" maxlength="10"  cssClass="mandClassColor hasMobileNo form-control"/>
          </div>
        </div>
        <div class="row">
        <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.address1" text="Parmananet Address"/><spring:message code="eip.citizen.reg.add2"/> <span class="mand">*</span></label>
           <form:textarea path="entity.empAddress" maxlength="150"  rows="2" cssClass="mandClassColor form-control"/>
          </div>
          <div class="col-sm-6 margin-top-5">
            <label><spring:message code="eip.citizen.reg.add1"/></label>
          <form:textarea path="entity.empAddress1" maxlength="150"  rows="2" cssClass="form-control"/>
          </div>
         </div>
         <div class="row">
          <div class="col-sm-6 margin-top-5">
            <label><spring:message	code="eip.agency.reg.PanNo"/> <span class="mand">*</span></label>
            <form:input path="entity.panCardNo" maxlength="10"  cssClass="mandClassColor form-control"/>
          </div>
        </div>
        <div class="text-center row margin-top-10">
       
         <input type="button" class="btn btn-success" value="<spring:message code="eip.commons.submitBT"/>" onclick="return _agencyAjaxRequest(this, 'Registration Successful!','.popup-form-div','registerAgency','');"/>  
		 <input type="button" class="btn btn-warning" value="<spring:message code="eip.agency.login.resetBT"/>" id="resettButton">
		 <a href="CitizenHome.html" class="btn btn-danger">Back</a>	
        </div>
      </form:form>
    </div>
</div>
</div>
</div>
</div>
</div>
