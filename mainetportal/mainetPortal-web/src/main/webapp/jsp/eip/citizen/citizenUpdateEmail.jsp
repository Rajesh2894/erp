<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="js/eip/citizen/citizenRegistrationForm.js"></script>

<div class="row padding-40">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
	<div class="widget-header">	
		<h2><spring:message code="eip.citizen.emailId.update.FormHeader" text="Update Email Id" /></h2>
	</div>	

<div class="widget-content padding">

	<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>

	 <form id="updateEMailIdCitizenId" name="updateEMailIdCitizenId" >
		<div class="form-group">
			<label for="citizenEmail"><spring:message code="citizen.editProfile.emailId" text="E-mail ID" /></label>
			<input id="citizenEmail" name="citizenEmail" class="form-control" type="text" onkeypress="return changeEmailEnter(event)"> 
		</div> 
		<div class="row">
			<div class="col-xs-4"><input type="button" class="btn btn-success btn-block" onclick="doUpdateCitizenEmailId(this);" value="<spring:message code="eip.commons.submitBT" text="Submit"/>" /></div>
			<div class="col-xs-4"><input type="reset" class="btn btn-warning btn-block" class="css_btn" value="<spring:message code="citizen.editProfile.reset" text="Reset" />"/></div>
			<div class="col-xs-4"><a href="CitizenHome.html?EditUserProfile" id="editUserProfile" class="btn btn-danger btn-block css_btn">Cancel</a></div>
		</div>
	</form>
</div>
</div>
</div>
</div>
</div><hr/>
<script>
function changeEmailEnter(e) {
    if (e.keyCode == 13) {
    	doUpdateCitizenEmailId();
        return false;
    }
}
</script>
