<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/workflow/chekerAction.js"></script>
<script src="assets/libs/ckeditor/ckeditor.js"></script>
<script src="assets/libs/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/audit/auditParaApproval.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script>
$(document)
.ready(
    function() 
    {
    	 $('#auditAppendix').ckeditor({skin : 'bootstrapck'});

    	 var type=($('option:selected', $("#categoryId")).attr('code'));

    	 if(type == undefined || type == ""){
    	 	$('.amountHide').hide();
    	 } else if(type == 'FIN' || type == 'FL' || type == 'RECA' || type == 'OUTA'){
    	 	$('.amountHide').show();
    	 }else{
    	 	$('.amountHide').hide();
    	 }

 		$("#comments").val("");

    	 	     	 
    });


	
    function closeCurrentTab() {
	close();
    }
</script>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="audit.para" text= "Audit Para" /> - ${command.approvalAuditParaDto.getAuditParaCode()}
			</h2>
			<apptags:helpDoc url="AuditPara.html" />
		</div>

		<div class="widget-content padding">
			
				<div class="mand-label clearfix">
					<span><spring:message code="audit.mgmt.mand"
							text="Field with " /><i class="text-red-1">*</i> <spring:message
							code="audit.mgmt.mand.field" text=" is mandatory" /> </span>
				</div>
		
			<form:form action="${controllerUrl}" name="frmAuditParaChiefAuditorApproval" id="frmAuditParaChiefAuditorApproval" cssClass="form-horizontal form">
				<%-- <form:hidden path="approvalAuditParaDto.auditDeptId" id="audDepId"/> --%>
				<form:hidden path="approvalAuditParaDto.removeFileById" id="removeFileById" />
				<form:hidden path="" value="${command.keyTest}" id="keyTest" />
				<form:hidden path="" value="${command.isEditable}" id="isEditable" />
				<form:hidden path="" value="${userSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode()}" id="deptCode" />
				<input type="hidden" value="true" id="showInitiator">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
										datePath="approvalAuditParaDto.auditEntryDate" labelCode="audit.mgmt.entry.date"
										cssClass="form-control view-mode" isMandatory="true"
										isDisabled="${command.isEditable eq 'V' ? true : false }"></apptags:date>
						
						
						
					<label class="control-label col-sm-2 required-control" for="Department"><spring:message
							code="audit.mgmt.department" text="Department"/></label>	
				
					
					
					<div class="col-sm-4">
							<form:select path="approvalAuditParaDto.auditDeptId" cssClass="form-control view-mode"
										 data-rule-required="true" id="auditDeptId" disabled="${command.isEditable eq 'V' ? true : false }">
										<form:option value="0">
												<spring:message code="Select" text="Select" />
										</form:option>
										
										<c:forEach items="${deptList}" var="dept">	
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
										</c:forEach>
							</form:select>
					</div>
					
				</div>
				
				<div class="form-group">
					<c:set var="baseLookupCode" value="AWZ" />
					<apptags:lookupFieldSet
	                 cssClass="form-control required-control"
	                 baseLookupCode="AWZ" hasId="true"
	                 pathPrefix="approvalAuditParaDto.auditWard" disabled="${command.isEditable eq 'V' ? true : false }"
	                 hasLookupAlphaNumericSort="true" isMandatory="true"
	                 hasSubLookupAlphaNumericSort="true" showAll="false" />
				
				</div>
				
				<div class="form-group">
					<label class="label-control col-sm-2 required-control"> <spring:message
							code="audit.field.from.date" text="From Year"></spring:message>
					</label>
					<div class="col-sm-4">

						<form:select id="auditParaYear" path="approvalAuditParaDto.auditParaYear"
							class="form-control mandColorClass" maxLength="200" disabled="${command.isEditable eq 'V' ? true : false }">
							<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
							<c:forEach items="${aFinancialYr}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="label-control col-sm-2 required-control"> <spring:message
							code="audit.field.to.date" text="To Year"></spring:message>
					</label>
					<div class="col-sm-4">

						<form:select id="auditParaTOYear" path="approvalAuditParaDto.auditParaTOYear"
							class="form-control mandColorClass" maxLength="200" disabled="${command.isEditable eq 'V' ? true : false }">
							<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
							<c:forEach items="${aFinancialYr}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="AuditType"><spring:message
							code="audit.mgmt.audit.type" text="Audit Type" /></label>
					
					<c:set var="baseLookupCode" value="ADT" />  <!-- // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="approvalAuditParaDto.auditType"
						cssClass="form-control view-mode" isMandatory="true"   disabled="${command.isEditable eq 'V' ? true : false }" 
						selectOptionLabelCode="selectdropdown" hasId="true" />
						
						
					<label class="control-label col-sm-2 required-control" for="Severity"><spring:message
							code="audit.mgmt.severity" text="Severity" /></label>	
				
					<c:set var="baseLookupCode" value="ADS" /> <!--  // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="approvalAuditParaDto.auditSeverity"
						cssClass="form-control view-mode" isMandatory="true" disabled="${command.isEditable eq 'V' ? true : false }"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				
				</div>
				
				<div class="form-group">
						<label class="control-label col-sm-2 required-control" for="AuditCategory"><spring:message
							code="audit.field.para.category" text="Audit Para Category" /></label>	
				
					<c:set var="baseLookupCode" value="APC" />
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="approvalAuditParaDto.categoryId"
						cssClass="form-control paraCategory" isMandatory="true" disabled="${command.isEditable eq 'V' ? true : false }"
						selectOptionLabelCode="selectdropdown" hasId="true" />
					<div class="amountHide">
						<label class="col-sm-2 control-label required-control"
							for="DueOnProperty"><spring:message
								code="audit.field.recovery.amount" text="Amount" /></label>

						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text"
									path="approvalAuditParaDto.recAmt" maxlength="10"
									disabled="${command.isEditable eq 'V' ? true : false }"
									class="form-control hasDecimal text-left" id="recAmt"></form:input>
								<label class="input-group-addon"><i class="fa fa-inr"></i><span
									class="hide"><spring:message
											code="" text="Rupees" /></span></label>
							</div>
						</div>
					</div>
				</div>
				
				<div class="form-group">
									
					<apptags:textArea labelCode="audit.mgmt.subject"  
										path="approvalAuditParaDto.auditParaSub" cssClass="form-control view-mode" isMandatory="true"
										isDisabled="${command.isEditable eq 'V' ? true : false }"  maxlegnth="250"></apptags:textArea>
						
						
					<apptags:input labelCode="audit.mgmt.workname"
										path="approvalAuditParaDto.auditWorkName" cssClass="form-control view-mode"  
										maxlegnth="100" isDisabled="${command.isEditable eq 'V' ? true : false }"></apptags:input>
				
				</div>
				
				<div class="form-group">
									
					<apptags:input labelCode="audit.mgmt.contractorname"
										path="approvalAuditParaDto.auditContractorName" cssClass="form-control view-mode"
										 isDisabled="${command.isEditable eq 'V' ? true : false }" maxlegnth="100"></apptags:input>
					
					<apptags:input labelCode="audit.field.para.sub.units" 
										path="approvalAuditParaDto.subUnit" cssClass="form-control"
										isMandatory="true" isDisabled="${command.isEditable eq 'V' ? true : false }" maxlegnth="2"></apptags:input>
					</div>
					
					<div class="form-group">	
									
						<div class="col-sm-2">
							<label><spring:message code="audit.mgmt.description"
							text="Description" /><span class="mand">*</span></label>
				
						</div>
				
						<div class="col-sm-8">
							<form:textarea id="auditAppendix" path="resolutionComments" 
								 disabled="${command.isEditable eq 'V' ? true : false }"/>
								
						</div>
						
					</div>
				
				<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList}">	
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
											code="audit.upload.document" text="Upload Documents" /></label>
					<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocument">
									<tr>
										<th><spring:message code="scheme.document.name" text="Document Name" /></th>
										<th><spring:message code="scheme.view.document" text="View Documents" /></th>																		
									</tr>
									<c:forEach items="${command.attachDocsList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}"  dmsDocId="${lookUp.dmsDocId}"
													filePath="${lookUp.attPath}"
													actionUrl="AuditParaEntry.html?Download" /></td>										
										</tr>
									</c:forEach>
								</table>
							</div>
					</div>
				</div>
				</c:if>
				
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#CheckAction"><spring:message code="workflow.checkAct.userAct"/></a>
						</h4>
					</div>
				
					<input type="hidden" value="${command.approvalAuditParaDto.auditDate}" id="newAuditDate"> 				
				    <div id="CheckAction" class="panel-collapse collapse in"> <!-- class="panel-collapse in" -->
						<div class="panel-body">
							<div class="form-group">
								<apptags:date fieldclass="datepicker"
									datePath="auditDate" 
									labelCode="audit.actionDate"
									cssClass="mandColorClass" isMandatory="true"></apptags:date>
						
		          				<apptags:radio cssClass="addInfo" radioLabel="${radioButtonsRequired}" radioValue="${radioButtonsRequiredVal}" labelCode="work.estimate.approval.decision" path="workflowActionDto.decision" changeHandler="loadForwardData(this)"></apptags:radio>	          
		           			</div>
	           			 
	           			 <%------------------------------------------Forward option------------------------------------------------%>
	          	    <div id="frwdToDepFormGrp">
	           		<div class="form-group">
	           			<label class="control-label col-sm-2 required-control" for="forwardToDepartment"><spring:message
								code="audit.mgmt.forward.to.department" text="Forward To Department"/></label>	
						<div class="col-sm-4">
							<form:select path="approvalAuditParaDto.forwardToDept" cssClass="form-control"
											 data-rule-required="true" id="forwardToDept" disabled="false" onchange="fetchEmpFromDepartment(this)">
											<form:option value="0" selected="selected" >
													<spring:message code="Select" text="Select" />
											</form:option>
											
											<c:forEach items="${forwardToDept}" var="deptList">	
											<c:set var="dept" value="${fn:split(deptList, ',')}"/>
											<%-- <c:set var="count" value="${count + 1}" /> --%>
													<form:option value="${dept[0]}">${dept[1]}</form:option>
											</c:forEach>
							</form:select>
						</div>
	           			
	           			<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="workflow.checkAct.emp"/></label>
	           			
	           			<div class="col-sm-4">
							<form:select path="workflowActionDto.forwardToEmployee"
								class="form-control mandColorClass chosen-select-no-results" id="empId">
								<form:option value="0" selected="selected">
									<spring:message code="Select" text="Select" />
								</form:option>
								<c:forEach items="${command.getCheckActMap()}" var="entry"
									varStatus="key">
									<form:option value="${entry.key}" label="${entry.value}"></form:option>
								</c:forEach>


							</form:select>
						</div>
				</div>
				</div>
			
		<%---------------------------------SendBack option----------------------------------------%>
		
					<div id="sendBack">
	           		<div class="form-group">
	            		<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="workflow.checkAct.event"/></label>
	           			<div class="col-sm-4">
	    					<form:select path="" class="form-control" id="serverEvent" onchange="loadAllEmpBasedOnEvent(this)">
								<form:option value="0">Select Event</form:option>
	           		 			 	<c:forEach items="${command.getWorkflowEventEmpList()}" var="lookUp" varStatus="key">
	           							<form:option value="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</form:option>
					  				</c:forEach>
							</form:select>
						</div>
				
				
					<div id="sendBackEmp">
				 		<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="workflow.checkAct.emp"/></label>
	           			<div class="col-sm-4">
	      					<form:select path="workflowActionDto.sendBackToEmployee" class="form-control" multiple="multiple" id="eventEmp">																
	           		 			<c:forEach items="${command.getCheckActMap()}" var="entry" varStatus="key">
	           						<form:option value="${entry.key}">${entry.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
						</div>
						</div>
						</div> 
						<%---------------------------------Common Comment And Upload----------------------------------------%>				
				
				<div class="form-group">
			    	<apptags:textArea labelCode="workflow.checkAct.remark" path="workflowActionDto.comments" isMandatory="true" cssClass="mandColorClass comment"/>
		    	
				    	
				</div>
					<div id="ActionHistory" class="panel-collapse collapse in">
						<div class="table-responsive">
							<table class="table table-bordered table-condensed">
								<tr>
									<th><spring:message
											code="audit.history.actions.datetime"
											text="audit.history.actions.datetime" /></th>
									<th width="18%"><spring:message
											code="audit.history.actions.action"
											text="audit.history.actions.action" /></th>
									<th><spring:message
											code="audit.history.actions.taskName"
											text="audit.history.actions.taskName" /></th>
									<th width="20%"><spring:message
											code="audit.history.actions.remarks"
											text="audit.history.actions.remarks" /></th>
								</tr>
								<c:set var="rowCount" value="0" scope="page" />
								<c:forEach items="${actions}" var="action" varStatus="status">
									<tr>
										<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
												value="${action.dateOfAction}" /></td>
										<c:set var="statusString" value="${action.decision}" />
										<td><spring:message
												code="workflow.action.decision.${fn:toLowerCase(statusString)}"
												text="${action.decision}" />
										<td><c:out value="${action.taskName}"></c:out></td>
										<td><c:out value="${action.comments}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					<c:if test="${command.keyTest eq 'Y'}">	
					<div class="subUnithide">			
					<div class="form-group">
						<apptags:input labelCode="audit.field.para.sub.closed" 
									path="subUnitClosed" cssClass="form-control hasNumber"
									isMandatory="true" maxlegnth="2"></apptags:input>
					</div>
					
					<div class="form-group">
					
						<apptags:input labelCode="audit.sub.unit.done" 
									path="subUnitCompDone" cssClass="form-control"
									isMandatory="true" maxlegnth="500"></apptags:input>
						
						<apptags:input labelCode="audit.sub.unit.pending" 
									path="subUnitCompPending" cssClass="form-control"
									isMandatory="true" maxlegnth="500"></apptags:input>
					</div>
					</div>
				</c:if>
				
				<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="audit.upload.document" text="Upload Documents" />
									</label>
									<div class="col-sm-4">
										<small class="text-blue-2"> <spring:message
												code="audit.approval.file.upload"
												text="audit.approval.file.upload" />
										</small>
										<apptags:formField fieldType="7" fieldPath=""
											showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
											currentCount="0">
										</apptags:formField>
									</div>												
				</div> 
				
				
			<c:if test="${command.attachDocumentList ne null && not empty command.attachDocumentList}">
				<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
											code="" text="Attached Documents" /></label>	
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocuments">
									<tr>
										<th><spring:message code="scheme.document.name" text="Document Name" /></th>
										<th><spring:message code="scheme.view.document" text="View Documents" /></th>
										<th><spring:message code="scheme.action" text="Action"></spring:message></th>																		
									</tr>
									<c:forEach items="${command.attachDocumentList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}" dmsDocId="${lookUp.dmsDocId}"
													filePath="${lookUp.attPath}"
													actionUrl="AuditParaEntry.html?Download" /></td>
											<td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>										
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</c:if>	          			  
	           		</div>
			   </div>
		  </div>
			
				
				
				
								
				<!-- Start button -->
				<div class="text-center">

					<button type="button" class="btn btn-success" title="Submit"
						onclick="showConfirmBoxForApproval(this);">
						<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
						<spring:message code="audit.mgmt.button.submit" text="Submit" />
					</button>


					<%-- <button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" id="button-Cancel"
						onclick="window.location.href='AdminHome.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="council.button.back" text="Back" />
					</button> --%>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				<!-- End button -->	
				
				
				
		</form:form>		
				
</div>
	</div>
</div>
				