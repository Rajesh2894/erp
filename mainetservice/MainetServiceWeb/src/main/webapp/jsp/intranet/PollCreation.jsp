<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script> 
<script type="text/javascript" src="js/intranet/pollCreation.js"></script>  

<script>
$(document).ready(function() {
	prepareDateTag();
	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});
	
	prepareDateTag1();
	$('.morethancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: '-0d'
	});
});
</script>

<div class="pagediv">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="intranet.pollCrForm" text="Poll Creation Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span></a>
				</div>
			</div>
			<div class="widget-content padding">

				<form:form id="pollCreationForm"
					action="PollCreation.html" method="POST"
					class="form-horizontal" name="pollRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<div class="warning-div error-div aaaaaalert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						 	<i class="fa fa-exclamation-circle"></i><span id="errorId"></span>
					</div>
					
					<%-- <h4>
						<spring:message code="intranet.pollCrDet" text="Poll Creation Details" />
					</h4> --%>
					
					<div class="form-group">
						<%-- <label class="col-sm-2 control-label required-control" for="department">
							<spring:message code="intranet.dept" text="Department" /></label>
						<div class="col-sm-4">
							<form:select path="pollRequest.deptId" cssClass="form-control"
								id="department" data-rule-required="true" isMandatory="true">
								<form:option value="">
									<spring:message code="intranet.select" text="Select" />
								</form:option>
								<c:forEach items="${departments}" var="dept">
									<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
								</c:forEach>
							</form:select>
						</div> --%>
						<apptags:input labelCode="intranet.pollNm"
							path="pollRequest.pollName" isMandatory=""
							cssClass="hasSpecialChar form-control" maxlegnth="100">
						</apptags:input>
					</div>
					
					<form:input type="hidden" path="pollRequest.mode" id="mode"/>
					<form:input type="hidden" path="pollRequest.pollid" id="pollid"/> 
					
					<div class="form-group">
						<apptags:date fieldclass="morethancurrdate" labelCode="intranet.pollFrmDt"
							datePath="pollRequest.fromDate" isMandatory="true" >
						</apptags:date>
						<apptags:date fieldclass="datepicker" labelCode="intranet.pollToDt"
							datePath="pollRequest.toDate" isMandatory="true">
						</apptags:date> 
					</div>
				
					<div class="text-center">						 
						<button type="button" class="btn btn-success" title="Proceed" id="proceedId"
							onclick="getPollCreateQuestion(this)">
							<spring:message text="Proceed" />
						</button>
						<input type="button" title="Reset" onclick="window.location.href='PollCreation.html'" class="btn btn-warning" value="Reset">
						<input type="button" title="Back" onclick="window.location.href='AdminHome.html'" class="btn btn-danger hidden-print" value="Back">
					</div>	
				</form:form>
				
				</div>
		</div>
	</div>
</div> 

<!-- ashish test -->


