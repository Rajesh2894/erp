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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/ckeditor/ckeditor.js"></script>
<script src="assets/libs/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="js/audit/auditParaEntry.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/audit/auditParaApproval.js"></script>
<script type="text/javascript">

$(document)
.ready(
    function() 
    {
    	 $('#auditAppendix').ckeditor({skin : 'bootstrapck'});
    	 var saveMode = $("#saveMode").val();
    	 if (saveMode == "C"){
    	 	$('#subUnit').val("1");
    	 }
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
				<spring:message code="audit.Para.Entry.add.heading" text="Add Audit Para" />
			</h2>
			<apptags:helpDoc url="AuditPara.html" />
		</div>

		<div class="widget-content padding">
			
		
			<form:form action="AuditParaEntry.html" name="frmAddAuditParaEntry" id="frmAddAuditParaEntry" cssClass="form-horizontal">
				<form:hidden path="auditParaEntryDto.mode" id="mode"/>
				<form:hidden path="removeFileById" id="removeFileById" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<div class="mand-label clearfix">
					<span><spring:message code="audit.mgmt.mand"
							text="Field with " /><i class="text-red-1">*</i> <spring:message
							code="audit.mgmt.mand.field" text=" is mandatory" /> </span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<c:if test="${command.saveMode eq 'V'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="audit.mgmt.auditParaNo" text="Audit Para Code" /></label>
						<div class="col-sm-4">
							<form:input path="auditParaEntryDto.auditParaCode" class="form-control"
							id="auditParaCode" disabled="true"></form:input>
						</div>
					</div>
				</c:if>
				
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
										datePath="auditParaEntryDto.auditEntryDate" labelCode="audit.mgmt.entry.date"
										cssClass="mandColorClass" isMandatory="true" readonly="false"
										isDisabled="${command.saveMode eq 'V'}"></apptags:date>
						
						
						
					<label class="control-label col-sm-2 required-control" for="Department"><spring:message
							code="audit.mgmt.department" text="Department" /></label>	
				
					
					
					<div class="col-sm-4">
							<form:select path="auditParaEntryDto.auditDeptId" cssClass="form-control chosen-select-no-results"
										 data-rule-required="true" id="auditDeptId" disabled="${command.saveMode eq 'V'}">
										<form:option value="0">
												<spring:message code="Select" text="Select" />
										</form:option>
										
										<c:forEach items="${paramsList.deptLst}" var="dept">
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
										</c:forEach>
							</form:select>
					</div>
					
				</div>
				
			
				<div class="form-group">
				<c:set var="baseLookupCode" value="AWZ" />
				<apptags:lookupFieldSet
                 cssClass="form-control required-control chosen-select-no-results"
                 baseLookupCode="AWZ" hasId="true"
                 pathPrefix="auditParaEntryDto.auditWard" disabled="${command.saveMode eq 'V'}"
                 hasLookupAlphaNumericSort="true" isMandatory="true"
                 hasSubLookupAlphaNumericSort="true" showAll="false" />
				
				</div>
				
				
				<div class="form-group">
					<label class="label-control col-sm-2 required-control"> <spring:message
							code="audit.field.from.date" text="From Year"></spring:message>
					</label>
					<div class="col-sm-4">

						<form:select id="auditParaYear" path="auditParaEntryDto.auditParaYear"
							class="form-control mandColorClass chosen-select-no-results" maxLength="200" disabled="${command.saveMode eq 'V'}">
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

						<form:select id="auditParaTOYear" path="auditParaEntryDto.auditParaTOYear"
							class="form-control mandColorClass chosen-select-no-results" maxLength="200" disabled="${command.saveMode eq 'V'}">
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
						items="${command.getLevelData(baseLookupCode)}" path="auditParaEntryDto.auditType"
						cssClass="form-control" isMandatory="true"   disabled="${command.saveMode eq 'V'}" 
						selectOptionLabelCode="selectdropdown" hasId="true" />
						
						
					<label class="control-label col-sm-2 required-control" for="Severity"><spring:message
							code="audit.mgmt.severity" text="Severity" /></label>	
				
					<c:set var="baseLookupCode" value="ADS" /> <!--  // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="auditParaEntryDto.auditSeverity"
						cssClass="form-control" isMandatory="true" disabled="${command.saveMode eq 'V'}"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				
				</div>
				
				<div class="form-group">
						<label class="control-label col-sm-2 required-control" for="AuditCategory"><spring:message
							code="audit.field.para.category" text="Audit Para Category" /></label>	
				
					<c:set var="baseLookupCode" value="APC" />
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="auditParaEntryDto.categoryId"
						cssClass="form-control paraCategory" isMandatory="true" disabled="${command.saveMode eq 'V'}"
						selectOptionLabelCode="selectdropdown" hasId="true" />
					<div class="amountHide">
						<label class="col-sm-2 control-label required-control"
							for="DueOnProperty"><spring:message
								code="audit.field.recovery.amount" text="Amount" /></label>

						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text"
									path="auditParaEntryDto.recAmt" maxlength="10"
									disabled="${command.saveMode eq 'V'}"
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
										path="auditParaEntryDto.auditParaSub" cssClass="form-control" isMandatory="true"
										isDisabled="${command.saveMode eq 'V'}"  maxlegnth="250"></apptags:textArea>
						
						
					<apptags:input labelCode="audit.mgmt.workname"
										path="auditParaEntryDto.auditWorkName" cssClass="form-control" isMandatory="false"  
										isDisabled="${command.saveMode eq 'V'}" maxlegnth="150"></apptags:input>
				
				</div>
				
				<div class="form-group">
									
					<apptags:input labelCode="audit.mgmt.contractorname"
										path="auditParaEntryDto.auditContractorName" cssClass="form-control"
										 isDisabled="${command.saveMode eq 'V'}" maxlegnth="150"></apptags:input>
					
					
					
					<c:if test="${command.saveMode eq 'C'  || command.saveMode eq 'E' }">
									<label for="" class="col-sm-2 control-label"> <spring:message
											code="audit.upload.document" text="Attach Documents" /></label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
											<small class="text-blue-2"> <spring:message
												code="audit.attach.docs.valid"
												text="word(docx,doc),excel(xlsx,xls),pdf files only" />
											</small>
											<apptags:formField fieldType="7"
												fieldPath="attachments[${count}].uploadedDocumentPath"
												currentCount="${count}" folderName="${count}"
												fileSize="COMMOM_MAX_SIZE" showFileNameHTMLId="true"
												isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
												cssClass="clear">
											</apptags:formField>											
																			
									</div>
									</c:if>
						
						
				
					
				
				</div>
				
				<div class="form-group">
					<apptags:input labelCode="audit.field.para.sub.units" 
									path="auditParaEntryDto.subUnit" cssClass="form-control hasNumber"
									isDisabled="${command.saveMode eq 'V'}" maxlegnth="2" isMandatory="true"></apptags:input>
				</div>
				
				<%-- <c:if
				test="${command.saveMode eq 'C'  || command.saveMode eq 'E' }">
					<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="audit.attach.documents" text="Attach Documents" />
									</label>
									<div class="col-sm-4">
										<apptags:formField fieldType="7" fieldPath=""
											showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
											currentCount="0">
										</apptags:formField>
									</div>
					</div>
				</c:if> --%>
				
							
				<div class="form-group">
				
				
					<div class="col-sm-2">
						<label><spring:message code="audit.mgmt.description"
							text="Description" /><span class="mand">*</span></label>
			
					</div>
			
					<div class="col-sm-8">
						<form:textarea id="auditAppendix" path="resolutionComments" 
							 disabled="${command.saveMode eq 'V'}"/>
							
					</div>
				</div>
				<form:hidden path="auditParaEntryDto.auditAppendix"
					cssClass="form-control mandColorClass" id="resolutionComments"
					value="${auditAppendix}" />
							
				<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList && (command.saveMode eq 'E'  || command.saveMode eq 'V')}">
				
					<div class="form-group">
						<label for="" class="col-sm-2 control-label"> <spring:message
											code="audit.upload.document" text="Attach Documents" /></label>						
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocuments">
									<tr>
										<th><spring:message code="scheme.document.name" text="Document Name" /></th>
										<th><spring:message code="scheme.view.document" text="View Documents" /></th>
										<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList && command.saveMode eq 'E'}">
										<th><spring:message code="scheme.action" text="Action"></spring:message></th>
										</c:if>																		
									</tr>
									<c:forEach items="${command.attachDocsList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}" dmsDocId="${lookUp.dmsDocId}"
													filePath="${lookUp.attPath}"
													actionUrl="AuditParaEntry.html?Download" /></td>
											<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList && command.saveMode eq 'E'}">
											<td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>	
											</c:if>									
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</c:if>
					
					<c:if test="${command.attachDocumentList ne null && not empty command.attachDocumentList && command.saveMode eq 'V'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
											code="" text="Attached Documents" /></label>	
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocument">
									<tr>
										<th><spring:message code="scheme.document.name" text="Document Name" /></th>
										<th><spring:message code="scheme.view.document" text="View Documents" /></th>																												
									</tr>
									<c:forEach items="${command.attachDocumentList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}" dmsDocId="${lookUp.dmsDocId}"
													filePath="${lookUp.attPath}"
													actionUrl="AuditParaEntry.html?Download" /></td>																			
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
					</c:if>	
				
				
				<!-- Start button -->
							<div class="text-center">
								<c:if test="${command.saveMode ne 'V'}">
									<button type="button" id="save" class="btn btn-blue-2" title='<spring:message code="audit.mgmt.button.save" text="Save" />'
										onclick="saveform(this)">
										<spring:message code="audit.mgmt.button.save" text="Save" />										
									</button>
								</c:if>	
								
								<c:if test="${command.saveMode eq 'C'}">
									<button type="button" onclick="resetAuditParaEntry(this);"
										class="btn btn-warning" title='<spring:message code="audit.mgmt.button.reset" text="Reset" />'>
										<spring:message code="audit.mgmt.button.reset" text="Reset" />
									</button>
								</c:if>
								<button type="button" id= "back" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel" style="" title='<spring:message code="audit.mgmt.button.back" text="Back" />'
									onclick="backAuditParaEntryForm()" id="button-Cancel">
									<spring:message code="audit.mgmt.button.back" text="Back" />
								</button>
								
								<c:if test="${command.saveMode ne 'V'}">
									<button type="button" id="submit" class="btn btn-success" title='<spring:message code="audit.mgmt.button.submit" text="Submit" />'
										onclick="submitform(this)">
										<spring:message code="audit.mgmt.button.submit" text="Submit" />										
									</button>
								</c:if>
								
							
								
							</div>
							
							<!-- End button -->
				
		</form:form>		
				
</div>
	</div>
</div>
				