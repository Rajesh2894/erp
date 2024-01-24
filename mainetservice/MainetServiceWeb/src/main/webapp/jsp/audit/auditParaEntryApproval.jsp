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
    });
</script>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message  text= "Audit Para - ${command.approvalAuditParaDto.getAuditParaCode()}" />
			</h2>
			<apptags:helpDoc url="AuditPara.html" />
		</div>

		<div class="widget-content padding">
			
				<div class="mand-label clearfix">
					<span><spring:message code="audit.mgmt.mand"
							text="Field with " /><i class="text-red-1">*</i> <spring:message
							code="audit.mgmt.mand.field" text=" is mandatory" /> </span>
				</div>
		
			<form:form action="AuditParaEntryApproval.html" name="frmAddAuditParaEntryApproval" id="frmAddAuditParaEntryApproval" cssClass="form-horizontal">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				<div class="form-group">
				<c:set var="statusLookup" value="${command.approvalAuditParaDto.getAuditWfStatus()}" />
					<apptags:date fieldclass="datepicker"
										datePath="approvalAuditParaDto.auditEntryDate" labelCode="audit.mgmt.entry.date"
										cssClass="form-control view-mode" isMandatory="true"
										isDisabled="true"></apptags:date>
						
						
					<c:if test="${(statusLookup != 'Start')}">	
					<label class="control-label col-sm-2 required-control" for="Department"><spring:message
							code="audit.mgmt.department" text="Department"/></label>	
				
					
					
					<div class="col-sm-4">
							<form:select path="approvalAuditParaDto.auditDeptId" cssClass="form-control view-mode"
										 data-rule-required="true" id="auditDeptId" disabled="true">
										<form:option value="0">
												<spring:message code="Select" text="Select" />
										</form:option>
										
										<c:forEach items="${deptList}" var="dept">
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
										</c:forEach>
							</form:select>
					</div>
					</c:if>
					
					
					
					
					<c:if test="${(statusLookup == 'Start')}">
					<label class="control-label col-sm-2 required-control" for="Department"><spring:message
							code="audit.mgmt.department" text="Department"/></label>	
							
					<div class="col-sm-4">
							<form:select path="approvalAuditParaDto.auditDeptId" cssClass="form-control view-mode"
										 data-rule-required="true" id="auditDeptId" disabled="true">
										<form:option value="0">
												<spring:message code="Select" text="Select" />
										</form:option>
										
										<c:forEach items="${command.depAppList}" var="dept">
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
										</c:forEach>
							</form:select>
					</div>
					</c:if>
					
				</div>
				<div class="form-group">
					<c:set var="baseLookupCode" value="AWZ" />
					<apptags:lookupFieldSet
	                 cssClass="form-control required-control"
	                 baseLookupCode="AWZ" hasId="true"
	                 pathPrefix="approvalAuditParaDto.auditWard" disabled="true"
	                 hasLookupAlphaNumericSort="true" isMandatory="true"
	                 hasSubLookupAlphaNumericSort="true" showAll="false" />
				
				</div>
				
				<div class="form-group">
					<label class="label-control col-sm-2 required-control"> <spring:message
							code="audit.field.from.date" text="From Year"></spring:message>
					</label>
					<div class="col-sm-4">

						<form:select id="auditParaYear" path="approvalAuditParaDto.auditParaYear"
							class="form-control mandColorClass" maxLength="200" disabled="true">
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
							class="form-control mandColorClass" maxLength="200" disabled="true">
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
						cssClass="form-control view-mode" isMandatory="true"   disabled="true" 
						selectOptionLabelCode="selectdropdown" hasId="true" />
						
						
					<label class="control-label col-sm-2 required-control" for="Severity"><spring:message
							code="audit.mgmt.severity" text="Severity" /></label>	
				
					<c:set var="baseLookupCode" value="ADS" /> <!--  // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="approvalAuditParaDto.auditSeverity"
						cssClass="form-control view-mode" isMandatory="true" disabled="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				
				</div>
				
				<div class="form-group">
						<label class="control-label col-sm-2 required-control" for="AuditCategory"><spring:message
							code="audit.field.para.category" text="Audit Para Category" /></label>	
				
					<c:set var="baseLookupCode" value="APC" />
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="approvalAuditParaDto.categoryId"
						cssClass="form-control paraCategory" isMandatory="true" disabled="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
					<div class="amountHide">
						<label class="col-sm-2 control-label required-control"
							for="DueOnProperty"><spring:message
								code="audit.field.recovery.amount" text="Amount" /></label>

						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text"
									path="approvalAuditParaDto.recAmt" maxlength="10"
									disabled="true"
									class="form-control hasDecimal text-left" id="recAmt"></form:input>
								<label class="input-group-addon"><i class="fa fa-inr"></i><span
									class="hide"><spring:message
											code="" text="Rupees" /></span></label>
							</div>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="AuditType"><spring:message
							code="audit.mgmt.audit.type" text="Audit Type" /></label>
					
					<c:set var="baseLookupCode" value="ADT" />  <!-- // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="approvalAuditParaDto.auditType"
						cssClass="form-control view-mode" isMandatory="true"   disabled="true" 
						selectOptionLabelCode="selectdropdown" hasId="true" />
						
						
					<label class="control-label col-sm-2 required-control" for="Severity"><spring:message
							code="audit.mgmt.severity" text="Severity" /></label>	
				
					<c:set var="baseLookupCode" value="ADS" /> <!--  // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="approvalAuditParaDto.auditSeverity"
						cssClass="form-control view-mode" isMandatory="true" disabled="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				
				</div>
				
				<div class="form-group">
									
					<apptags:textArea labelCode="audit.mgmt.subject"  
										path="approvalAuditParaDto.auditParaSub" cssClass="form-control view-mode" isMandatory="true"
										isDisabled="true"  maxlegnth="250"></apptags:textArea>
						
						
					<apptags:input labelCode="audit.mgmt.workname"
										path="approvalAuditParaDto.auditWorkName" cssClass="form-control view-mode" 
										maxlegnth="100" isDisabled="true"></apptags:input>
				
				</div>
				
				<div class="form-group">
									
					<apptags:input labelCode="audit.mgmt.contractorname"
										path="approvalAuditParaDto.auditContractorName" cssClass="form-control view-mode"
										 isDisabled="true" maxlegnth="100"></apptags:input>
					
					
					<apptags:input labelCode="audit.field.para.sub.units" 
										path="approvalAuditParaDto.subUnit" cssClass="form-control"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}" maxlegnth="2"></apptags:input>
					</div>
					
					<div class="form-group">	
									
						<div class="col-sm-2">
							<label><spring:message code="audit.mgmt.description"
							text="Description" /><span class="mand">*</span></label>
				
						</div>
				
						<div class="col-sm-8">
							<form:textarea id="auditAppendix" path="resolutionComments" 
								 disabled="true"/>
								
						</div>
						
					</div>
				
				<c:set var="statusLookup" value="${command.approvalAuditParaDto.getAuditWfStatus()}" />
				
				<c:if test="${(statusLookup == 'Submitted')}">
				<apptags:CheckerAction showInitiator="true" hideForward="true" hideSendback="false" />
				</c:if>

				<c:if test="${(statusLookup == 'L1-Approved')}">
				<apptags:CheckerAction showInitiator="true" hideForward="true" hideSendback="true" />
				</c:if>

				<c:if test="${(statusLookup == 'L2-Approved')}">
				<apptags:CheckerAction showInitiator="true" hideForward="false" hideSendback="true" hideReject="true" />
				</c:if>
				
				<!-- Start button -->
				<div class="text-center">
					<c:set var="statusLookup" value="${command.approvalAuditParaDto.getAuditWfStatus()}" />
					
					<%-- <c:if test="${(statusLookup != 'Start')}">
						<button type="button" class="btn btn-success" title="Submit"
							onclick="showConfirmBoxForApproval(this);">
							<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.submit" text="Submit" />
						</button>
					</c:if> --%>


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
				