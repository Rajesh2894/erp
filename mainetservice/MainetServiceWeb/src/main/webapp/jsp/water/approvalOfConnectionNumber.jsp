 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/cfc/scrutiny.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">

function saveApprovalForm(element)
{
	
	
    var appId='${command.lableValueDTO.applicationId}';
    var serviceId= '${command.serviceId}';
	var labelId='${command.lableValueDTO.lableId}';
	 saveOrUpdateForm(element,"Your application saved successfully!", '', 'save')
	 var connectionNo='${command.csmrInfo.csCcn}';

	 if($("#error").val()!='Y')
          {
	           if($("#isScrutiny").val()!='Y')
	        	   {
	           loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',appId,labelId,serviceId);
	        	   }
	           else
	        	   {
	        	   var msg="Saved Successfully";
	        	   if($('input[name="cfcMasterDTO.apmAppRejFlag"]:checked').val()=="A")
	        		   {
	        		   showConfirmBox(msg+"Connection No.is "+connectionNo);
	        		   }
	        	   else
	        		   {
	        		   showConfirmBox(msg);
	        		   }
	        		
	        	   }
		 } 
}
function showConfirmBox(msg)
{
		
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Proceed';
    	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+msg+'</h5>';
	    message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);

}
function proceed() {
	window.location.href = 'AdminHome.html'; 
}
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.ApprovalOfConnectionNumber"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/> <i class="text-red-1">*</i><spring:message code="water.ismandtry"/> 
				</span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:form action="ApprovalOfConnectionNo.html"
				class="form-horizontal form" name="frmApprovalForm"
				id="frmApprovalForm">
				
			<form:hidden path="isScrutinyRequest" id="isScrutiny"/>
				<h4 class="margin-top-0"><spring:message code="water.form.appdetails"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.service.name"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" path="serviceName" id="serviceName" disabled="true"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.application.number"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" disabled="true"
							path="csmrInfo.applicationNo"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.applicnt.name"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled="true"
							path="applicantFullName"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.application.date"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" disabled="true"
							path="csmrInfo.csApldate"></form:input>
					</div>
				</div>
				<div class="form-group"> 
             <apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true" showOnlyLabel="false"
									pathPrefix="csmrInfo.codDwzid" isMandatory="false" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true" cssClass="form-control" showAll="true"/>
  
             </div>
             <div class="form-group">
						
							<div class="margin-left-20">
								<label class="col-xs-2"> <form:radiobutton
										path="cfcMasterDTO.apmAppRejFlag" value="A" id="approve" /><spring:message code="water.Approve"/>
								</label> <label class="col-xs-2"> <form:radiobutton
										path="cfcMasterDTO.apmAppRejFlag" value="R" id="reject" /> <spring:message code="Reject"/>
								</label>
							</div>
							
						</div>
						  <div class="form-group">
                    <label class="col-sm-2 control-label required-control"><spring:message code="water.remark"/></label>
                 <div class="col-sm-10"><form:textarea class="form-control" path="cfcMasterDTO.apmRemark"></form:textarea></div>
                    </div>
			
				<c:set var="appId" value="${command.lableValueDTO.applicationId}"/>
				<c:set var="labelId" value="${command.lableValueDTO.lableId}"/>
				<c:set var="serviceId" value="${command.serviceId}"/>
			
				<form:hidden path="hasError" id="error"/>
		
				<div class="text-center padding-top-10">
				<button type="button" class="btn btn-success btn-submit" onclick="saveApprovalForm(this)"><spring:message code="water.btn.submit"/>Submit</button>
					 <input type="button" onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${appId}','${labelId}','${serviceId}')"
						class="btn" value="Back">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

 