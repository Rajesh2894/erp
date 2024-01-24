<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script> 
<script type="text/javascript" src="js/intranet/pollCreation.js"></script>  

<script>
/* $(document).ready(function() {
		debugger;
		var counter = 3;
		$("#addButton").click(function() {
			debugger;
			$("#show" + counter).show();
			if (counter > 4) {
				alert("Only 4 textboxes allow");
				return false;
			}
			counter++;
		});

		$("#vish" + counter).click(function() {
			debugger;
			if (counter == 1) {
				alert("No more textbox to remove");
				return false;
			}
			$("#show" + counter).hide();
			counter--;
		});
	}); */
</script> 

<div class="pagediv">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<%-- <h2>
					<spring:message code="intranet.queForm" text="Poll Creation Question Form"/>
				</h2> --%>
			<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">

				<form:form id="pollCreationQuestionForm"
					action="PollCreation.html" method="POST"
					class="form-horizontal" name="pollRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<div class="warning-div error-div aaaaaalert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						 	<i class="fa fa-exclamation-circle"></i><span id="errorId"></span>
					</div>
					
					<h4>
						<spring:message code="intranet.queDet" text="Poll Creation Question Details" />
					</h4>
				
					<div class="form-group" >
				    <apptags:textArea labelCode="intranet.que" path="pollRequest.question" maxlegnth="140"></apptags:textArea>
					</div>
					<div class="form-group">
				   <apptags:textArea labelCode="intranet.ch1" path="pollRequest.choices[0].text" maxlegnth="40"></apptags:textArea>
					</div>
				   
					<div class="form-group" id="TextBoxDiv1">
				   <apptags:textArea labelCode="intranet.ch2" path="pollRequest.choices[1].text" maxlegnth="40"></apptags:textArea>
					</div>
					
					<div class="form-group" id="show3" >
				   		<apptags:textArea labelCode="intranet.ch3" path="pollRequest.choices[2].text" maxlegnth="40"></apptags:textArea>
					</div>
					
					<div class="form-group" id="show4" >
				    	<apptags:textArea labelCode="intranet.ch4" path="pollRequest.choices[3].text" maxlegnth="40"></apptags:textArea>
					</div>
					
					<div class="text-center">
						<button type="button" value="<spring:message code="bt.save"/>"
							class="btn btn-green-3" title="Save"
							onclick="savePollCreation(this)">
							Save<i class="fa padding-left-4" aria-hidden="true"></i>
						</button>
						
						<button type="button" class="btn btn-green-3" title="Submit" id="submitId"
							onclick="submitPollCreation(this)">
							<spring:message text="Submit" />
						</button>
						
						<button type="button" class="btn btn-danger hidden-print" title="Back" id="backId"
							onclick="getPollCreateForm(this)">
							<spring:message text="Back" />
						</button>
						
					</div>	
				</form:form>
				
			</div>
		</div>
	</div>
</div> 

<!-- ashish test -->


