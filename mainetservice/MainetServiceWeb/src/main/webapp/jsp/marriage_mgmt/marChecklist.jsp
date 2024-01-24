<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/marriage_mgmt/marriageCertificate.js"></script>
<script type="text/javascript" src="js/mainet/mfs100-9.0.2.6.js"></script>


<script type="text/javascript">
/********Local Storage Variable Get ***************/
	var appid=localStorage.getItem('appid');
	var chkappid=$('#appid').val();
	if(appid==chkappid){
		var tlcount=localStorage.getItem('count');
		var decs=localStorage.getItem('Decision');
		var comm=localStorage.getItem('comments');
		if(comm !="" || comm != null){
			$('#comments').val(comm);
		}
		for(var i=0; i<tlcount; i++){
			var chkname="#chkbx"+i;
			$(chkname).attr("checked", true);
			$(chkname).attr("disabled", false);
		}
		$('input[name="workflowActionDto.decision"][value='+decs+']').prop("disabled",false);
		$('input[name="workflowActionDto.decision"][value='+decs+']').prop("checked",true);
		
	}
	/********Local Storage Variable Get End ***************/		

//D#134156
function viewApplicantDetails(marId,taskId,element) {
    var url="MarChecklistApproval.html?viewApplicantDetails";
	var data = {};
	data.marId = marId;
	data.taskId = taskId;
	//D#137564
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	
	var returnData = __doAjaxRequest(url, 'post', requestData,false, 'html');
    if (returnData) {
    	var title = 'View Applicant details';
    	prepareTags();
    	var printWindow = window.open('', '_blank');

    	printWindow.document.write('<html><head><title>' + title + '</title>');
    	printWindow.document
    			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
    	printWindow.document
    			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
    	printWindow.document
    			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
    	printWindow.document
    			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
    	printWindow.document
    			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
        printWindow.document.write('</head><body style="background:#fff;">');
    	printWindow.document.write(returnData);
    	printWindow.document.write('</body></html>');
    	printWindow.document.close();

    }
}
	
	//D#127379
	function marChecklistSubmit(obj) {
		return saveOrUpdateForm(obj,getLocalMessage('checklistVerification.saveSuccessMsg'), "AdminHome.html", 'marChecklistSubmit');
	}
	
	
	
	function validateRejMsg(obj,count){		
		canApprove();
		
	 }
		
	 
	function canApprove(){
		var c=true;
		var d=true;
	 	 $(".chkbx").each(function(){
		  e=$(this);
			if(c){
			if((e.attr("data-mandatory")=="Y")) {
		    if(e.is(':checked')){
		      $(".proceed").prop("checked",true).prop("disabled",false);
		      $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",true).prop("disabled",false);
		      $('#approvalBTFlow').hide();
			  $('#normalFlow').show();
			  $("#selectall").prop("checked",true).prop("disabled",false);
		    }else{
		      $(".proceed").prop("checked",false).prop("disabled",true);
		      $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",false).prop("disabled",true);
		      $("#selectall").prop("checked",false);
		      c=false;
		    }
		    }else{
			   	var flag=true;
			  	  $(".chkbx").each(function(){
			      		e=$(this);
			  			if(c){if((e.attr("data-mandatory")=="Y")){		
			  					flag=false;
			  				}else{
								}
			  			}}); 
			  	  if(flag==false){
			  		  c=true;
			  	  }else{	
						c=false;
						 var len=$(".chkbx").length;
						if(len == 1){
							$(".chkbx").each(function(){e=$(this);
							if(e.is(':checked')){
							      $(".proceed").prop("checked",true).prop("disabled",false);
							      $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",true).prop("disabled",false);
							      $('#approvalBTFlow').hide();
								  $('#normalFlow').show();
								  $("#selectall").prop("checked",true).prop("disabled",false);
						      }else{
							      $(".proceed").prop("checked",false).prop("disabled",true);
							      $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",false).prop("disabled",true);
							      $("#selectall").prop("checked",false);
							      c=false;
							    }
							});
						}
			  	  }			
		    }
			}else{
		  	  $(".chkbx").each(function(){
		    		e=$(this);
					if(d){if((e.attr("data-mandatory")=="Y")){
							d=false;
						}
					}
		    	  });
		  	  if(d == true){
		  		  $(".chkbx").each(function(){
		  			  e=$(this);
		  			  if(d){
		  				  if(e.is(':checked')){
		      	      	      $(".proceed").prop("checked",true).prop("disabled",false);
			      	      	  $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",true).prop("disabled",false);
			    		      $('#approvalBTFlow').hide();
			    			  $('#normalFlow').show();
			    			  $("#selectall").prop("checked",true).prop("disabled",false);
		      	      	      d=false;
		      			  }else{
		      			        $(".proceed").prop("checked",false).prop("disabled",true);
		      			      $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",false).prop("disabled",true);
		      			    	$("#selectall").prop("checked",false);
		      			      }
		  			  }
		  			  
		  		  });
						
		 	   }
					
    }})};
	
	
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="checklistVerification.breadcrumb" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<input type="hidden" value="${command.applicationDetails.apmApplicationId }"	id="appid" />
			
			<form:form method="post" action="MarriageRegistration.html"
				name="MarriageRegistration" id="frmMaster" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>

				<c:if test="${not empty documentDetailsList}">
					<h4 class="margin-top-0">
						<spring:message text="Applicant Details"
							code="cfc.applicant.detail" />
					</h4>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="checklistVerification.serviceName" text="Service Name" />
						</label>
						<div class="col-sm-4">
							<span class="form-control height-auto">${command.applicationDetails.applicationService }</span>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								text="Name Of Applicant" code="mrm.marriage.appName" /> </label>
						<div class="col-sm-4">
							<span class="form-control text-hidden">${command.applicationDetails.applicantsName }</span>
						</div>
					</div>

							
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								text="Application ID " code="cfc.application" /> </label>
						<div class="col-sm-4">
							<span class="form-control">${command.applicationDetails.apmApplicationId }</span>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								text="Application Date" code="cfc.applIcation.date" /> </label>
						<div class="col-sm-4">
							<c:set value="${command.applicationDetails.apmApplicationDate }"
								var="appDate" />
							<span class="form-control"><fmt:formatDate type="date"
									value="${appDate}" pattern="dd/MM/yyyy" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								text="Applicant Mobile No " code="master.loi.applicant.mob" />
						</label>
						<div class="col-sm-4">
							<span class="form-control">${command.mobNo }</span>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								text="Applicant Email Id" code="master.loi.applicant.email" />
						</label>
						<div class="col-sm-4">
							<span class="form-control">${command.email}</span>
						</div>
					</div>

				</c:if>
				<c:set var="loopCount" value='0' />
				<c:if test="${not empty documentDetailsList}">

					<input type="hidden" value="${fn:length(documentDetailsList)}"
						id="docCount" />
						
					<h4>
						<spring:message code="checklistVerification.tableCaption" />
					</h4>
					<div class="table-responsive">
						<table class="table table-hover table-bordered table-striped">
							<tr>
								<th><spring:message code="checklistVerification.srNo" /></th>
								<th><spring:message code="checklistVerification.document" /></th>
								<th><spring:message code="mrm.documentDesc"
													text="Document Description" /></th>
								<th><spring:message
										code="checklistVerification.documentStatus" /></th>
								<th><spring:message code="checklistVerification.fileName" /></th>
								<%-- <c:if test="${command.newApplication}">
						<th><spring:message code="cfc.upload.doc" /></th>
						</c:if> --%>
								<th><spring:message code="checklistVerification.verified" /></th>

							</tr>

							<c:forEach var="singleDoc" items="${documentDetailsList}"
								varStatus="count">

								<tr class="checklistData">
									<td>${count.count}</td>
									<td>${singleDoc.lookUpDesc}</td>
									 <td>${singleDoc.docDescription}</td>
									<td><c:choose>
											<c:when test="${singleDoc.otherField eq 'Y'}">
												<spring:message code="checklistVerification.mandatory" />
												<c:set var="docStatus" value="Mandatory" />
											</c:when>
											<c:otherwise>
												<spring:message code="checklistVerification.optional" />
												<c:set var="docStatus" value="Optional" />
											</c:otherwise>
										</c:choose></td>
									<td class="row1"><c:set var="links"
											value="${fn:split(singleDoc.defaultVal,',')}" /> <c:forEach
											items="${singleDoc.defaultVal}" var="download"
											varStatus="status">
											<apptags:filedownload filename="${singleDoc.lookUpCode}"
												dmsDocId="${singleDoc.lookUpType}" filePath="${download}"
												elem="#chkbx${count.index}" callback="true"
												actionUrl="ChecklistVerification.html?Download"></apptags:filedownload>
										</c:forEach></td>


									<td><c:choose>
											<c:when test="${singleDoc.otherField eq 'Y'}">
												<label class="checkbox-inline"> <form:checkbox
														onclick="validateRejMsg(this,${count.index})"
														id="chkbx${count.index}" class="chkbx" disabled="true"
														data-mandatory="${singleDoc.otherField}" path=""
														value="${count.index}" /> <c:set var="loopCount"
														value="${count.index}" /></label>
											</c:when>
											<c:otherwise>
												<label class="checkbox-inline"> <form:checkbox
														onclick="validateRejMsg(this,${count.index})"
														id="chkbx${count.index}" class="chkbx" disabled="false"
														data-mandatory="${singleDoc.otherField}" path=""
														value="${count.index}" /> <c:set var="loopCount"
														value="${count.index}" /></label>
											</c:otherwise>
										</c:choose></td>

								</tr>
							</c:forEach>
						</table>
						<div class="form-group padding-top-10 col-sm-12" >
							 <label class="radio-inline" style="float:right;"><form:checkbox disabled="true"
														path="" value="Marked All"  id="selectall" /><spring:message code="" text="Marked All" /></label>
					</div>
					</div>
					
				
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<apptags:CheckerAction  hideForward="true" hideUpload="true" ></apptags:CheckerAction>
					</div>
					
					

					<div class="form-group padding-top-10">
						<div class="col-sm-4">
							<b><spring:message code="checklist.verify.note"
									text="Note :  Please download all mandatory document for verification" /></b>
						</div>
					</div>


					<c:choose>
						<c:when test="${command.photoThumbDisp eq 'Y'}"> 
							<div class="padding-top-10 text-center">
							<input type="button"
									value="<spring:message code="checklistVerification.viewDetails" text="View Details"/>"
									onclick="viewApplicantDetails(${command.marriageDTO.marId},${command.workflowActionDto.taskId },this);" class="btn btn-blue-3" />
									
								<button type="button"  id="approvalBTFlow" class="btn btn-success btn-submit"
									 onclick="approvalDecision(this);">
									<spring:message code="mrm.button.submit" text="Test" />
								</button>
								<button type="button" id="normalFlow" class="btn btn-success proceed" 
									   disabled="true" onclick="openHusbPhotoThumb(this);">
									<spring:message code="mrm.button.submit"
										text="Submit"></spring:message>
								</button>
								<apptags:backButton url="AdminHome.html" />
							</div>
							
						</c:when>
						<c:otherwise>
							<div class="padding-top-10 text-center">
								<button type="button" class="btn btn-success proceed" 
									disabled="true" onclick="marChecklistSubmit(this);">
									<spring:message code="mrm.button.submit" text="Submit"></spring:message>
								</button>
								<apptags:backButton url="AdminHome.html" />
							</div>
						</c:otherwise>
					</c:choose>


				</c:if>
			</form:form>
		</div>
	</div>
</div>
<script type="text/javascript">
if ($('.chkbx:checked').length == $('.chkbx').length) {
	$('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("disabled",false);
	$(".proceed").prop("checked",true).prop("disabled",false);
	
}else{
	$('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("disabled",true);
	$(".proceed").prop("checked",true).prop("disabled",true);
}

$("#selectall").click(function () {
	$('.chkbx').prop('checked', this.checked);
	  
  if($("#selectall").is(':checked')){
	  $("#rejected").prop("checked",false).prop("disabled",true);
	  $("#approved").prop("checked",true).prop("disabled",false);
	  $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",true).prop("disabled",false);
      $('#approvalBTFlow').hide();
	  $('#normalFlow').show();
	  $(".proceed").prop("checked",true).prop("disabled",false);
  }else{
	  $("#approved").prop("checked",false).prop("disabled",true);
      $("#rejected").prop("disabled",false);
      $('input[name="workflowActionDto.decision"][value="APPROVED"]').prop("checked",false).prop("disabled",true);
      $(".proceed").prop("checked",false).prop("disabled",true);
  }
	  
});
</script>




