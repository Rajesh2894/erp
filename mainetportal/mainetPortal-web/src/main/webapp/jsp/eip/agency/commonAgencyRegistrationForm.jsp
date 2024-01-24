<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<head>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script>
$(document).ready(function() {
	
	$("#cpdTypeId").val($("#cpdTypeId option[code='O']").val());
	fileUploadMultipleValidationList();
	
	jQuery('.hasCharacterInUperCase').keyup(function () { 
		
		this.value = this.value.replace(/[^a-z A-Z]/g,'').toUpperCase();
		
	});
	
});
function uploadDoc(element)
{
	return saveOrUpdateForm(element, getLocalMessage("agency.fileUpload"), 'CitizenHome.html', 'saveform'); 
	 
}
</script>

</head>
<div class="form-div">
<div class="clearfix" id="content">

		
<h2><spring:message code="eip.citizen.home.msg" text="Welcome to bihar nagar seva!"></spring:message></h2>
 <c:if test="${(userSession.employee.authStatus eq 'R')  or (empty userSession.employee.authStatus)}">
<div style="color: red;">
	<p ><spring:message code="eip.agency.upload.mandatory.msg" text=""/></p>
</div>
</c:if>

<c:if test="${not command.hasValidationErrors()}">
<c:choose>

	 <c:when test="${userSession.getEmployee().getAuthStatus() eq 'H'}"> 
	<br><br>
		<div class="onHold">
			<h3> <spring:message code="eip.agency.upload.msg.onHold" /> </h3>
	</div>
	</c:when>
	 
	<c:when test="${userSession.getEmployee().getAuthStatus()eq 'R'}">
	<br><br>
			<div class="onReject">
			<h3> <spring:message code="eip.agency.upload.msg.onReject" /> </h3>
	
	</div>
	</c:when>

</c:choose>
</c:if>
					
<form:form action="AgencyDOCVerification.html" name="frmagencyDOCVerification" id="frmagencyDOCVerification" class="form">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<c:set value="${command.agencyType}" var="agency"></c:set>			
	<c:choose>
	    <c:when test="${(agency eq 'CYBER') || (command.agencyType eq 'CFC')}">
	      <jsp:include page="/jsp/eip/agency/cyberCFCDocUpload.jsp" />
	    </c:when>
	    <c:when test="${agency eq 'HOSPITAL'}">
	       <jsp:include  page="/jsp/eip/agency/hospitalDocUpload.jsp" />
	    </c:when>
	     <c:when test="${(agency eq 'BUILDER') || (command.agencyType eq 'ARCHITECT') || (command.agencyType eq 'ENGINEER')}">
	        <jsp:include  page="/jsp/eip/agency/BAEDocUpload.jsp" />
	    </c:when>
	     <c:when test="${agency eq 'CREMATORIA'}">
	        <jsp:include  page="/jsp/eip/agency/crematoriaDocUpload.jsp" />
	    </c:when>
	     <c:when test="${agency eq 'ADVOCATE'}">
	        <jsp:include  page="/jsp/eip/agency/advocateDocUpload.jsp" />
	    </c:when>
	    
	</c:choose>
	<c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
		<div class="form-elements clear" id=results>
			<table class="gridtable margin_top_10">
				<tr>								
				<th><spring:message code="eip.agency.requireDoc" /></th>				
				<th><spring:message code="tp.status" text="Status" /></th>										
				<th><spring:message code="eip.agency.upload" /></th>
				</tr>									
																
				
			</table>
		</div>
	</c:if>
	<c:if test="${(empty command.cfcAttachmentsAfterReject) and ((empty command.entity.isUploaded) or (command.entity.isUploaded eq 'N'))}">
		<div class="form-elements" id=results>
			<table class="gridtable margin_top_10">
				<tr>								
				<th><spring:message code="eip.agency.requireDoc" /></th>				
				<th><spring:message code="tp.status" text="Status" /></th>										
				<th><spring:message code="eip.agency.upload" /></th>
				</tr>
													
				
			</table>
		</div>
	</c:if>
					

	
</form:form>
</div>
</div>

<script type='text/javascript'>

$(document).ready(function(){
	$(".addCF").click(function(){
		
		var value	=	$("#countQual").val();
		if(checkValidn1(value))
			{
	
		$("#customFields").append('<tr id="firstqual"'+ value +'"><td><input class="mandClassColor input2 hasCharacter maxLength500" type="text" name="agencyAdvocateMaster.agencyAdvocateQualification['+ value +'].qQualification" id="qQualification'+ value +'"></td><td><input class="mandClassColor input2 hasCharacter maxLength100" type="text" name="agencyAdvocateMaster.agencyAdvocateQualification['+ value +'].qUniversity" id="qUniversity'+ value +'"></td><td><input class="mandClassColor input2 hasNumber maxLength4" type="text" name="agencyAdvocateMaster.agencyAdvocateQualification['+ value +'].qPassyear" id="qPassyear'+ value +'"></td><td><input class="mandClassColor input2 hasCharacter maxLength500" type="text" name="agencyAdvocateMaster.agencyAdvocateQualification['+ value +'].qRemark" id="qRemark'+ value +'"></td><th><a href="javascript:void(0);" class="remCF css_action"><i class="fa fa-minus-square-o"></i> Remove</a></th></tr>');
		 $("#countQual").val(parseInt($("#countQual").val()) + 1);
		
			}
		});
	function checkValidn1(value)
	{
		value = parseInt(value) - 1;
		
		
		if($('#qQualification'+value).val() == "" ||  $('#qUniversity'+value).val() == "" || $('#qPassyear'+value).val() == 0 || $('#qRemark'+value).val()== "" ) 
			{	
			 var msg = getLocalMessage('qa.field');
				showErrormsgboxTitle(msg);
				
				return false;
			}
		
         return true;
				
	}
    $("#customFields").on('click','.remCF',function(){
    	var value = $("#countQual").val();
    	
    	if(value==1)
        	{
    		showErrormsgboxTitle("Cant remove first row");
        	}
    	else{
    		var value	= parseInt($("#countQual").val()) - 1;
        $(this).parent().parent().remove();
        $("#countQual").val(value);
    	}
    });
});

$('body').on('focus',".hasCharacter", function(){
	 
		$('.hasCharacter').hasCharacter(jQuery('.hasCharacter').keyup(function () { 
		    this.value = this.value.replace(/[^a-z A-Z]/g,'');
		    
		}));

		
});

$('body').on('focus',".hasNumber", function(){

	jQuery('.maxLength4').keyup(function () { 
		 $(this).attr('maxlength','4');

	});
	
	$('.hasNumber').hasCharacter(jQuery('.hasNumber').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	       
	}));
	
	
});	


</script>
<script type='text/javascript'>

$(document).ready(function(){
	$(".addCF1").click(function(){
		var value	=	$("#countExp").val();
		
		if(checkValidn2(value))
		{
		$("#exprience").append('<tr><td><input class="mandClassColor input2 hasCharacter maxLength100" type="text" name="agencyAdvocateMaster.agencyAdvocateExperience['+ value +'].exOrganization"></td><td><input class="mandClassColor input2 hasCharacter maxLength100" type="text" name="agencyAdvocateMaster.agencyAdvocateExperience['+ value +'].exPracticearea"></td><td><input class="mandClassColor input2 hasCharacter maxLength100" type="text" name="agencyAdvocateMaster.agencyAdvocateExperience['+ value +'].exRemark"></td><td><input class="mandClassColor input2 hasNumber" type="text" name="agencyAdvocateMaster.agencyAdvocateExperience['+ value +'].exExperience"></td><th><a href="javascript:void(0);" class="remCF1 css_action"><i class="fa fa-minus-square-o"></i> Remove</a></th></tr>');
		 $("#countExp").val(parseInt($("#countExp").val()) + 1);
		}
		});
    $("#exprience").on('click','.remCF1',function(){
    	var value = $("#countExp").val();
    	if(value==1)
        	{
    		showErrormsgboxTitle("Cant remove first row");
        	}
    	else
        	{
    	var value	= parseInt($("#countExp").val()) - 1;
        $(this).parent().parent().remove();
        $("#countExp").val(value);
        	}
    });
});
function checkValidn2(value)
{
	value = parseInt(value) - 1;
	
	
	if($('#exOrganization'+value).val() == "" ||  $('#exPracticearea'+value).val() == "" || $('#exRemark'+value).val() == "" || $('#exExperience'+value).val()== 0 ) 
		{	
		 var msg = getLocalMessage('qa.field');
			showErrormsgboxTitle(msg);
			
			
			return false;
		}
	
     return true;
			
}
</script>


