 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
<style>
.multiselect-container{overflow-y: scroll; height: 100px; position: relative!important;}
.fileUpload.fileinput.fileinput-new .fileUploadClass{ left: 30px !important;}

</style>


<script src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script> 
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script>
var fileArray = [];

$(document).ajaxComplete(function() {
	multiselect();
});

function multiselect() {
	$('.multiselect-ui').multiselect({

		buttonText : function(options, select) {
			//  console.log(select[0].length);
			if (options.length === 0) {
				return 'None selected';
			}
			if (options.length === select[0].length) {
				return 'All selected (' + select[0].length + ')';
			} else if (options.length >= 1) {
				return options.length + ' selected';
			} else {
				var labels = [];
				console.log(options);
				options.each(function() {
					labels.push($(this).val());
				});
				return labels.join(', ') + '';
			}
		}

	});
}

function fileCountUpload(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(
			'ActivityManagement.html?fileCountUpload', 'POST', requestData,
			false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();

}


$(document).ready(function() {
	$("#attachDoc").on("click", '.delButton', function(e) {
		var countRows = -1;
		$('.appendableClass').each(function(i) {
			if ($(this).closest('tr').is(':visible')) {
				countRows = countRows + 1;
			}
		});
		row = countRows;
		if (row != 0) {
			$(this).parent().parent().remove();
	
			row--;
			//	reOrderTableIdSequence();
		}
		e.preventDefault();
	});

	$("#deleteDoc").on("click",'#deleteFile',function(e) {
		var errorList = [];
		

		if (errorList.length > 0) {
			$("#errorDiv").show();
			showErr(errorList);
			return false;
		} else {

			$(this).parent().parent().remove();
			var fileId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if (fileId != '') {
				fileArray.push(fileId);
			}
			$('#removeFileById').val(fileArray);
		}
	});

	$("#endDate").val($("#endDate").val().substr(0, 10))
	$("#startDate").val($("#startDate").val().substr(0, 10))

	$(function() {

		$("#startDate").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					minDate: '-0d',
					onSelect : function(selected) {
						$("#endDate").datepicker("option","minDate", selected)
					}
				});

		$("#endDate").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					minDate: '-0d',
					onSelect : function(selected) {
						$("#startDate").datepicker("option", "maxDate",selected)
					}
				});

	});

	<c:if test="${mode eq 'view' }">
		$("#frmActivityManagement :input").prop("disabled",true);
		$(".btn-danger").removeProp("disabled");
		$('.multiselect-ui').multiselect('disable');
		$('#actWatcher').multiselect('disable');
	</c:if>
	<c:if test="${mode eq 'edit' }">
		$("#frmActivityManagement :input").prop("disabled",true);
		$(".btn-danger, .btn-success, #activityType ").removeProp("disabled");
		$('#actWatcher').removeAttr('disabled');
		$('#actEmpid').prop('disabled', false).trigger("chosen:updated");
		$('#actWatcher options').removeAttr('disabled');
		$('#editActNote').removeAttr('disabled');
		$('#removeFileById').removeAttr('disabled');
		$('input[name="attachments[0].uploadedDocumentPath"]').removeAttr('disabled');
		$('input[name="attachments[0].doc_DESC_ENGL"]').removeAttr('disabled');
		$('#actPid').prop('disabled', true).trigger("chosen:updated");
	</c:if>
	<c:if test="${watcher eq 'Y' }">
		$('#actEmpid').prop('disabled', true).trigger("chosen:updated");
		$("#activityType").prop('disabled', true);
	</c:if>
});
</script>



<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

<div class="widget">
  <div class="widget-header">
			<h2>
				<spring:message code="activity.management" text="Activity Maanagement" />
			</h2>
			<apptags:helpDoc url="ActivityManagement.html"></apptags:helpDoc>
		</div>

	<div class="widget-content padding">
		<form:form action="ActivityManagement.html" method="post" name="frmActivityManagement" id="frmActivityManagement"  cssClass="form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="removeFileById" id="removeFileById"/>
			<fieldset>
				<div class="form-group">
					<label for="actName" class="col-sm-2 control-label"><spring:message code="activity.name" text="Activity Name"/></label>
					<div class="col-sm-4">
							<form:input path="entity.actName" cssClass="form-control" placeholder="Activity Name" id="actName"/>
					</div>
					<label for="actId" class="col-sm-2 control-label"><spring:message code="activity.id" text="Activity ID"/></label>
					<div class="col-sm-4">
							<form:input path="entity.actId" cssClass="form-control" placeholder="Activity ID" id="actId" disabled="true"/>
					</div>
				</div>
				<div class="form-group">
					<label for=actType class="col-sm-2 control-label"><spring:message code="activity.type" text="Activity Type"/></label>
					<div class="col-sm-4">
						<form:select id="actType" path="entity.actType" cssClass="form-control">
								<form:option value="0"><spring:message code="activity.type" text="Activity Type"/></form:option>
								<c:forEach items="${activityType}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
						</form:select>
					</div>
					<label for="actPid" class="col-sm-2 control-label"><spring:message code="activity.parent" text="Parent Activity"/></label>
					<div class="col-sm-4">
							<%-- <form:input path="entity.actPid" cssClass="form-control" placeholder="Parent Activity" id="actPid" disabled="true"/> --%>
							<form:select id="actPid" path="entity.actPid" cssClass="form-control chosen-select-no-results">
								<form:option value="0"><spring:message code="activity.parent" text="Parent Activity"/></form:option>
								<c:forEach items="${allActivity}" var="activity">
									<form:option value="${activity.actId}">${activity.actName}</form:option>
								</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">

					<label for="startDate" class="col-sm-2 control-label"><spring:message code="activity.start.date" text="Start Date"/></label>
					<div class="col-sm-4">
							<div class="input-group">
								<form:input path="entity.actStartdt" cssClass="form-control" id="startDate" />
								<label for="datepicker" class="input-group-addon"><i class="fa fa-calendar"></i></label>
							</div>
						</div>
					<label for="endDate" class="col-sm-2 control-label"><spring:message code="activity.end.date" text="End Date"/></label>
					<div class="col-sm-4">
						<div class="input-group">
								<form:input path="entity.actEnddt" cssClass="form-control" id="endDate" />
								<label for="datepicker" class="input-group-addon"><i class="fa fa-calendar"></i></label>
							</div>
					</div>
				</div>
				<div class="form-group">
					<label for="actEmpid" class="col-sm-2 control-label"><spring:message code="activity.assignee" text="Assignee"/></label>
					<div class="col-sm-4">
						<form:select id="actEmpid" path="entity.actEmpid" cssClass="form-control chosen-select-no-results">
								<form:option value="0"><spring:message code="activity.assignee" text="Assignee"/></form:option>
								<c:forEach items="${employees}" var="employee">
									<form:option value="${employee.empId}">${employee.fullName}</form:option>
								</c:forEach>
						</form:select>
					</div>
					<label for="actEsttime" class="col-sm-2 control-label"><spring:message code="activity.time" text="Estimated Time"/></label>
					<div class="col-sm-4">
						<form:input path="entity.actEsttime" id="estimatedTime" cssClass="form-control decimal22" maxlength="5"/>
					</div>
				</div>
				<div class="form-group">
					<label for="145211" class="col-sm-2 control-label"><spring:message code="activity.priority" text="Priority"/></label>
					<div class="col-sm-4">
						<form:select id="actPriority" path="entity.actPriority" cssClass="form-control">
								<form:option value="0"><spring:message code="activity.priority" text="Activity Priority"/></form:option>
								<c:forEach items="${activityPriority}" var="prior">
									<form:option value="${prior.lookUpId}">${prior.lookUpDesc}</form:option>
								</c:forEach>
						</form:select>
					</div>
					<label for="145211" class="col-sm-2 control-label"><spring:message code="activity.status" text="Status"/></label>
					<div class="col-sm-4">
						<form:select id="activityType" path="entity.actStatus" cssClass="form-control">
								<form:option value="0"><spring:message code="activity.status" text="Activity Status"/></form:option>
								<c:forEach items="${activityStatus}" var="status">
									<form:option value="${status.lookUpId}">${status.lookUpDesc}</form:option>
								</c:forEach>
						</form:select>
					</div>
				</div>
			</fieldset>
			<fieldset>
				<!-- <div class="panel-group accordion-toggle"
					id="accordion_single_collapse"> -->


					<!-- Start Each Section -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="" text="Upload Documents"/>
								</a>
							</h4>
						</div>

						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">

								<c:if test="${ mode ne 'create' && fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDoc">
											<tr>
												<%--  <th width="5%"><spring:message code="" text="Sr No." /></th> --%>
												<th><spring:message code="scheme.master.description"
														text="Document Description" /></th>
												<th width="10%"><spring:message code="scheme.master.upload"
														text="Upload" /></th>
												<th><spring:message code="scheme.master.upload"
														text="Action" /></th>
											</tr>
											<c:forEach items="${command.attachDocsList}" var="lookUp" varStatus="status">
												<tr>
													<%-- <td>${status.count}</td> --%>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="ActivityManagement.html?Download" /></td>
													<td class="text-center"><a href='#' id="deleteFile"
														onclick="return false;" class="btn btn-danger btn-sm"><i
															class="fa fa-trash"></i></a> <form:hidden path=""
															value="${lookUp.attId}" /></td>
												</tr>
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>




							<c:if test="${ mode ne 'view'}">
								<div id="uploadTagDiv">
									<div class="table-responsive">
										<c:set var="d" value="0" scope="page" />
										<table class="table table-bordered table-striped" id="attachDoc">
											<tr>
												<%--  <th  width="5%"><spring:message code="" text="Sr No." /></th> --%>
												<th><spring:message code="scheme.master.description"
														text="Document Description" /></th>
												<th width="10%"><spring:message code="scheme.master.upload"
														text="Upload" /></th>
												<th scope="col" width="8%" class="text-center"><a
													onclick='fileCountUpload(this);'
													class="btn btn-blue-2 btn-sm addButton"> <i
														class="fa fa-plus-circle"></i></a></th>
											</tr>

											<tr class="appendableClass">
												<%-- <td>${d+1}</td> --%>
												<td><form:input path="attachments[${d}].doc_DESC_ENGL"
														class=" form-control" /></td>
												<td ><apptags:formField 
														fieldType="7"
														fieldPath="attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="COMMOM_MAX_SIZE"
														isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION">
													</apptags:formField></td>
												<td class="text-center"><a href='#' id="0_file_${d}"
													onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton'><i
														class="fa fa-trash"></i></a></td>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />

										</table>
									</div>
								</div>
							</c:if>
							</div>
						</div>
					</div>
					<!-- End Each Section -->

				<!-- </div> -->
			</fieldset>
			
			<c:if test="${mode eq 'create' }">
				<fieldset>
					<legend><spring:message code="activity.notes" text="Notes"/></legend>
					<spring:message code="activity.notes.placeholder" text="Enter Your Note...." var="notes"/>
					<form:textarea path="entity.editActNote" cssClass="form-control"  rows="4" placeholder="${notes}" id="editActNote"/>
	
				</fieldset>
			</c:if>
			
			<c:if test="${mode ne 'create' }">
				<fieldset>
				
				
						<ul><p class="text-bold text-blue-2"><spring:message code="activity.notes.hist" text="History"/></p>
							<c:set var="notes" value="${command.entity.actNote}" />
							<c:set var="note" value="${fn:split(notes, '|')}" />
									<c:forEach items="${note}" var="n">
										<c:if test="${not empty n }">
										<li> ${n}</li>
										</c:if>
									</c:forEach>
						
					 	</ul>
					<legend><spring:message code="activity.notes" text="Notes"/></legend>
					<spring:message code="activity.notes.placeholder" text="Enter Your Note...." var="notes"/>
					<form:textarea path="entity.editActNote" cssClass="form-control"  rows="4" placeholder="${notes}" id="editActNote"/>
				</fieldset>
			</c:if>
			<fieldset>
				<legend></legend>
				<div class="form-group">
					<label for="1452" class="col-sm-2 control-label"><spring:message code="activity.watcher" text="Watcher"/></label>
					<div class="col-sm-4">
						<form:select id="actWatcher" path="entity.actWatcher" cssClass="form-control multiselect-ui" multiple="true" disabled="${mode eq 'view'}">
								<form:option value="0"><spring:message code="selectdropdown" text="Select"/>-<spring:message code="activity.status" text="Select Activity Status"/></form:option>
								<c:set value="${actWatcherList}" var="listOfActWatcher"/>
								<c:forEach items="${employees}" var="watcher">
									<c:choose>
										<c:when test="${listOfActWatcher.contains(watcher.empId)}">
												<option value="${watcher.empId}" selected>${watcher.fullName}</option>
										</c:when>
										<c:otherwise>
											<option value="${watcher.empId}" >${watcher.fullName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</form:select>
					</div> 
					
				</div>
			</fieldset>
			<div class="text-center margin-top-10">
				<apptags:submitButton entityLabelCode="activity.management" actionParam="saveActivity" successUrl="ActivityManagement.html" buttonLabel="bt.save" cssClass="btn btn-success"/>
				<c:if test="${mode eq 'create' }">
					<input type="button" class="btn btn-warning " value="<spring:message code="bt.clear" text="Reset"/>" onclick="openForm('ActivityManagement.html','create')">
				</c:if>
				<apptags:backButton url="ActivityManagement.html"/>
			</div>
		</form:form>
	</div>
</div>

</div>

