<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/lqp/questionRegistration.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lqp.query.reg.title"
					text="Assembly Question Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="lqp.fiels.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form method="POST" action="LegislativeQuestion.html"
				cssClass="form-horizontal" id="queryRegFormId" name="queryRegForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="lqp.query.reg.title" text="Assembly Question Details" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="lqp.question.no" isReadonly="true"
										path="queryRegMasrDto.questionId" 
										maxlegnth="14"></apptags:input>

									<apptags:date fieldclass="datepicker"
										labelCode="lqp.question.date" isDisabled="true"
										datePath="queryRegMasrDto.questionDate"></apptags:date>

								</div>

								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="lqp.question.raisedDate"
										isDisabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN' }"
										isMandatory="true"
										datePath="queryRegMasrDto.questionRaisedDate"></apptags:date>

									<apptags:date fieldclass="datepicker"
										labelCode="lqp.meetingDate"
										isDisabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}"
										isMandatory="true" datePath="queryRegMasrDto.meetingDate"></apptags:date>
								</div>

								<div class="form-group">

									<apptags:input labelCode="lqp.mlaName"
										path="queryRegMasrDto.mlaName" cssClass="hasCharacter form-control"
										maxlegnth="99" isMandatory="true"
										isReadonly="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}"></apptags:input>


									<label class="col-sm-2 control-label required-control " for="">
										<spring:message code="lqp.question.type" />
									</label>
									<apptags:lookupField items="${command.getLevelData('QTP')}"
										path="queryRegMasrDto.questionTypeId"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select" isMandatory="true"
										disabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}" />
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="lqp.question.raisedAssembly" text="Raised By Assembly" /></label>
									<c:set var="baseLookupCode" value="ALT" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="queryRegMasrDto.raisedByAssemblyId"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										selectOptionLabelCode="lqp.select" isMandatory="true"
										showOnlyLabel="Raised By"
										disabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}" />

									<apptags:input labelCode="lqp.subject" isMandatory="true"
										isReadonly="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN' }"
										path="queryRegMasrDto.questionSubject" 
										maxlegnth="99"></apptags:input>

								</div>

								<div class="form-group">
									<apptags:textArea isMandatory="true"
										labelCode="lqp.question.details"
										isDisabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}"
										path="queryRegMasrDto.question" maxlegnth="499"></apptags:textArea>
									
									<!-- Upload Document Start -->
									<label for="" class="col-sm-2 control-label"> 
									<spring:message	code="lqp.upload.doc" text="Documents" />
									</label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
											<c:if
												test="${command.saveMode eq 'ADD'}">
												<apptags:formField fieldType="7"
													fieldPath="attachments[${count}].uploadedDocumentPath"
													currentCount="${count}" folderName="${count}"
													fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
													isMandatory="true" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
													cssClass="clear">
												</apptags:formField>
											</c:if>

											<c:if
												test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
												<input type="hidden" name="deleteFileId"
													value="${command.attachDocsList[0].attId}">
												<input type="hidden" name="downloadLink"
													value="${command.attachDocsList[0]}">
												<apptags:filedownload
													filename="${command.attachDocsList[0].attFname}"
													filePath="${command.attachDocsList[0].attPath}"
													actionUrl="CouncilProposalMaster.html?Download"></apptags:filedownload>
											</c:if>
											</div>
									</div>
									<!-- Upload Document end -->

								<div class="form-group">
									<label class="control-label required-control col-sm-2" for="">
										<spring:message code="lqp.dept.name" text="Department" />
									</label>
									<div class="col-sm-4">
										<form:select path="queryRegMasrDto.deptId" id="deptId"
											disabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}"
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${command.departmentList}" var="obj">
												<form:option value="${obj.dpDeptid}"
													code="${obj.dpDeptcode}">${userSession.languageId eq 1 ? obj.dpDeptdesc : obj.dpNameMar}</form:option> 
											</c:forEach>
										</form:select>
									</div>
									
									<apptags:date fieldclass="datepicker"
										labelCode="lqp.question.deadlineDate"
										isDisabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}"
										isMandatory="true" datePath="queryRegMasrDto.deadlineDate"></apptags:date>
								</div>

								<div class="form-group">
									<apptags:input labelCode="lqp.question.inwardNo"
										isMandatory="true"
										isReadonly="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}"
										path="queryRegMasrDto.inwardNo" cssClass="alphaNumeric"
										maxlegnth="19"></apptags:input>
									
									<label class="control-label required-control col-sm-2" for="">
										<spring:message code="lqp.emp.name" text="Follow Up Authority" />
									</label>
									<div class="col-sm-4">
										<form:select id="empId" path="queryRegMasrDto.empId"
											disabled="${command.saveMode eq 'VIEW' ||command.saveMode eq 'REOPEN'}"
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code='asset.info.select' text="Select" />
											</form:option>
											<c:forEach items="${command.employeeList}" var="employee">
												<form:option value="${employee.empId }" code="">${employee.empname }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								
								<div class="form-group">
									<c:if test="${command.saveMode eq 'REOPEN' }">
										<apptags:input labelCode="lqp.question.reopenReason"
											isMandatory="true" path="queryRegMasrDto.reopenReason"
											 maxlegnth="49"></apptags:input>
									</c:if>
								
								</div>
								
								<!-- Uploaded Documents -->
								<c:if test="${not empty command.documentDtos}">
									<h4 class="margin-top-0 margin-bottom-10 panel-title">
										<a data-toggle="collapse" href="#DocumentUpload"><spring:message
												code="lqp.upload.doc" /></a>
									</h4>
									<div id="DocumentUpload">
										<fieldset class="fieldRound">
											<div class="overflow">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><label class="tbold"><spring:message
																			code="lqp.srno" text="Sr No" /></label></th>
																<th><label class="tbold"><spring:message
																			code="lqp.attachBy" text="Attach By" /></label></th>
																<th><label class="tbold"><spring:message
																			code="lqp.downlaod" text="Download"/></label></th>
															</tr>

															<c:forEach items="${command.documentDtos}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td><label>${lk.count}</label></td>
																	<td><label>${lookUp.attBy}</label></td>
																	<td><c:set var="links"
																			value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
																		<apptags:filedownload filename="${lookUp.attFname}"
																			filePath="${lookUp.attPath}"
																			dmsDocId="${lookUp.dmsDocId}"
																			actionUrl="LegislativeAnswer.html?Download"></apptags:filedownload>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</c:if>
								
								<!-- Start button -->
								<div class="text-center">
									<c:if test="${command.saveMode ne 'VIEW'}">
										<button type="button" class="btn btn-success" title="Submit"
											onclick="saveQueryRegform(this)">
											<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
											<spring:message code="bt.save" text="Submit" />
										</button>

									</c:if>

									<c:if test="${command.saveMode eq 'ADD'}">
										<button type="button" id="resetQueryRegForm"
											class="btn btn-warning" title="Reset">
											<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
											<spring:message code="bt.clear" text="Reset" />
										</button>
									</c:if>
									<button type="button" class="button-input btn btn-danger"
										name="button-Cancel" value="Cancel" style=""
										onclick="window.location.href='LegislativeQuestion.html'"
										id="button-Cancel">
										<i class="fa fa-chevron-circle-left padding-right-5"></i>
										<spring:message code="bt.backBtn" text="Back" />
									</button>
								</div>

								<!-- End button -->


							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
<script>
if('${command.saveMode}'=='ADD'){
	$("#questionDate").datepicker({
		dateFormat: "dd/mm/yy"			
	}).datepicker("setDate", new Date());	
}

$( function() {
	var questionDate = $('#questionDate').val();
	
    $('#deadlineDate').datepicker({
    	minDate: 0, 
    	dateFormat: 'dd/mm/yy'
	});
    
    $('#meetingDate').datepicker({
    	maxDate: questionDate, 
    	dateFormat: 'dd/mm/yy'
	});
   
	$('#questionRaisedDate').datepicker({
		maxDate: questionDate,
		minDate: -30,
		dateFormat: 'dd/mm/yy',
		onSelect: function(qRaisedDate){
			$("#meetingDate").datepicker( "option", "maxDate", qRaisedDate);
		}
	});
	
  });
</script>
