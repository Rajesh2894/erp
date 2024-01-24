<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script>
/* $(document).ready(function(){

	 $('#mobNumber').change(function(){
		sendOTP();
	});
}); */

function sendOTP(){
	
// 	if($('#mobNumber').val().length==10){
		var formAction	=	$('#NochangeInAssessmentId').attr('action');	
		var requestData = $('#NochangeInAssessmentId').serialize();
		var ajaxResponse = __doAjaxRequest(
				formAction+'?sendOTP', 'POST',
				requestData, false, 'html');

		$("#dataDiv").html(ajaxResponse);
		return false;
// 		}
}

function serachProperty() {
	var formAction	=	$('#NochangeInAssessmentId').attr('action');
		var requestData = $('#NochangeInAssessmentId').serialize();
		var ajaxResponse = __doAjaxRequest(
				formAction+'?SearchPropNo', 'POST',
				requestData, false, 'html');

		$("#dataDiv").html(ajaxResponse);
		return false;

}
function validatePropertyOTP() {
	var formAction	=	$('#NochangeInAssessmentId').attr('action');
	var requestData = $('#NochangeInAssessmentId').serialize();
	var ajaxResponse = __doAjaxRequest(
			formAction+'?ValidatePropNo', 'POST',
			requestData, false, 'html');

	$("#dataDiv").html(ajaxResponse);
	return false;

}
</script>
<div  id="dataDiv">
<div class="content" >
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
		<c:if test="${command.assType eq 'NC'}">
			<h2><spring:message code="property.NoChangeInAss" text="No Change in Assessment"/></h2>
</c:if>
<c:if test="${command.assType eq 'C'}">
 <h2><spring:message code="property.ChangeInAssessment" text="Change in Assessment"/></h2>
</c:if>
		</div>
		<div class="widget-content padding">
			<form:form action="${command.redirectURL}"
					method="post" class="form-horizontal form"
					name="NoChangeAssessmentSearchForm" id="NochangeInAssessmentId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="mand-label clearfix">
					<span><spring:message code="property.ChangeInAss.EnterPropertyNo"/><strong class="text-red-1"><spring:message code="property.ChangeInAss.OR"/></strong>
						<spring:message code="property.ChangeInAss.OLDPID"/>
					</span>
				</div>
					<div class="form-group">
					<apptags:input labelCode="Enter Property No." path="provisionalAssesmentMstDto.assNo" cssClass=""></apptags:input>
					<apptags:input labelCode="OLD PID." path="provisionalAssesmentMstDto.assOldpropno" cssClass=""></apptags:input>	
					</div>
			<div class="form-group">
					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2"
							onclick="serachProperty()">
							<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/> 
						</button>
					</div>
				</div>
	<%-- 			<c:if test="${not command.mobdisabled}">
				<div class="form-group">
					
					<apptags:input labelCode="Mobile No." path="mobNumber" maxlegnth="10" isMandatory="true"></apptags:input>	
					
					<div class="col-sm-6">
						<i class="text-blue-2"><spring:message code="property.changeInAsse.MobNoVerificationOtp"/></i>
					</div>
				</div> --%>
				<c:if test="${not command.otpdisabled}">
			 	<div class="form-group"> 
					<apptags:input labelCode="Enter OTP" path="userOtp" isMandatory="true" ></apptags:input>	
					<div class="col-sm-6">
						<i class="text-blue-2"><spring:message code="property.ChangeInAss.MobNoOtp"/></i>
					
					<input type="button" id="NoChangeResendOTP" onclick="sendOTP()" value="Resend OTP" class="btn btn-success"/>
					</div>
			 	</div>
				
				<div class="text-center">
					<input type="button" id="NoChangeSubmitBtn" onclick="validatePropertyOTP()" value="Submit" class="btn btn-success"/>
				</div>
				</c:if>
				
	<%-- 			</c:if> --%>
				
			</form:form>
		</div>


	</div>
	<!-- End of info box -->
</div>
</div>