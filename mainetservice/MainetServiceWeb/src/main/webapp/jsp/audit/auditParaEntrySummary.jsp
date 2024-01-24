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
<script type="text/javascript" src="js/audit/auditParaEntry.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
 -->
 <script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script>

$(document).ready(function() {


 function fetchDepartment(auditDeptId, depList)
 {
	var depName = "";
	$.each(depList,function(index) {
		var depObj = depList[index];
		if(depObj.dpDeptid == auditDeptId){
			depName = depObj.dpDeptdesc;
		}
	});
	return depName;
	
 }
 
 
});

</script>
<style>
table#addAuditParaDataTable tbody tr td:last-child {
	text-align: center;
}
</style>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="audit.Para.Entry.heading" text="Audit Para Entry - Summary Form" />
			</h2>
			<apptags:helpDoc url="AuditPara.html" />
		</div>

		<div class="widget-content padding">
			
		
			<form:form action="AuditParaEntry.html" name="frmAuditParaEntry" id="frmAuditParaEntry" cssClass="form-horizontal">
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
				
				<div class="form-group">
				<apptags:input labelCode="audit.mgmt.auditParaNo"  
										path="auditParaEntryDto.auditParaCode" cssClass="form-control" isMandatory="false"
										 maxlegnth="25"></apptags:input>
						
				
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2" for="AuditType"><spring:message
							code="audit.mgmt.audit.type" text="Audit Type" /></label>
					
					<c:set var="baseLookupCode" value="ADT" />  <!-- // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="auditParaEntryDto.auditType"
						cssClass="form-control" isMandatory="false"
						selectOptionLabelCode="selectdropdown" hasId="true" />
						
						
						
					<label class="control-label col-sm-2" for="Department"><spring:message
							code="audit.mgmt.department" text="Department" /></label>	
				
						
					<div class="col-sm-4">
							<form:select path="auditParaEntryDto.auditDeptId" cssClass="form-control"
										 data-rule-required="true" id="auditDeptId">
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
	                 cssClass="form-control required-control"
	                 baseLookupCode="AWZ" hasId="true"
	                 pathPrefix="auditParaEntryDto.auditWard" disabled="${command.saveMode eq 'V'}"
	                 hasLookupAlphaNumericSort="true"
	                 hasSubLookupAlphaNumericSort="true" showAll="false" />
						
					<label class="control-label col-sm-2" for="Status"><spring:message
							code="audit.mgmt.status" text="Status" /></label>	
				
					<c:set var="baseLookupCode" value="ADU" />  <!-- // Need info from BA on prefix lookup values -->
					
					<apptags:lookupField 
						items="${command.getLevelData(baseLookupCode)}" path="auditParaEntryDto.auditParaStatus"
						cssClass="form-control" isMandatory="false"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				
				</div>
				
				<div class="form-group">
				
					<label class="col-sm-2 control-label"><spring:message
							code="audit.mgmt.from.date" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepickers"
								id="fromDate" />
							<label class="input-group-addon" for="formDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id=fromDate></label>
						</div>
					</div>
					
					<label class="col-sm-2 control-label"><spring:message
							code="audit.mgmt.to.date" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepickers"
								id="toDate" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id=toDate></label>
						</div>
					</div>
					
				</div>
				<!-- Start button -->
					<div class="text-center clear padding-10">

						<button type="button" id="search" class="btn btn-blue-2" 
						title="Search">
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="audit.mgmt.button.search" text="Search" />
						</button>

						<button type="button" id="reset"
							onclick="window.location.href='AuditParaEntry.html'"
							class="btn btn-warning" title="Reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="audit.mgmt.button.reset" text="Reset" />
						</button>

						<button type="button" id="add" class="btn btn-blue-2"
							onclick="addAuditPara('AuditParaEntry.html','addAuditPara')"
							title="Add">
							<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
							<spring:message code="audit.mgmt.button.addAuditPara"
								text="Add" />
						</button>
					</div>
					<!-- End button -->
					
					
					<!-- Start for Result table for Search Criteria  -->
					
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="addAuditParaDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="audit.mgmt.srno" text="Sr.No" /></th>
									<th width="15%" align="center"><spring:message
											code="audit.mgmt.auditParaNo" text="Audit Para No" /></th>
									<th width="10%" align="center"><spring:message
											code="audit.mgmt.date" text="Date" /></th>
									<th width="20%" align="center"><spring:message
											code="audit.mgmt.department" text="Department" /></th>
									<th width="25%" align="center"><spring:message
											code="audit.mgmt.zone" text="Zone" /></th>
									<th width="15%" align="center"><spring:message
											code="audit.mgmt.status" text="Status" /></th>
									<th width="10%" align="center"><spring:message
											code="audit.history.actions.action" text="Actions" /></th>
								</tr>
							</thead>
							
							<tbody>
								<%-- Handled in Javascript if need to prepopulate then code --%>
								
								<c:forEach items="${command.auditParaEntryDtoList}"	
									var="masterData" varStatus="status">
									<tr>
										<td>${status.count}</td>
										<td>${masterData.auditParaCode}</td>
										<td>${masterData.auditDateDesc}</td>
										<td>
										<c:forEach items="${paramsList.deptLst}" var="dept"> 
											<c:if test="${dept.dpDeptid eq masterData.auditDeptId}">
												${dept.dpDeptdesc}
											</c:if>
										</c:forEach>
										</td>
										<td>
											${masterData.auditWardDesc}
										<td>										
										<c:forEach items="${paramsList.statusLst}" var="status"> 
											<c:if test="${status.lookUpId eq masterData.auditParaStatus}">
												${status.lookUpDesc}
											</c:if>
										</c:forEach>
										
										</td>
										 <td class="text-center">
										 
										 <c:forEach items="${paramsList.statusLst}" var="statusCode"> 
											<c:if test="${statusCode.lookUpId eq masterData.auditParaStatus}">
												 <%-- <c:set var= "stCode" value = "${statusCode.lookUpCode}"/>  --%>
												 <%-- ${stCode} --%>
												 <c:if test="${statusCode.lookUpCode eq 'D' }">
												 	<button type="button" class="btn btn-blue-2 btn-sm margin-right-5 "
													name="button-plus" id="button-plus"
													onclick="showGridOption(${masterData.auditParaId},'V')"
													title="
									      			<spring:message code="audit.mgmt.view" text="view"></spring:message>">
													<i class="fa fa-eye" aria-hidden="true"></i>
													</button>

													<button type="button" class="btn btn-danger btn-sm btn-sm"
													name="button-123" id=""
													onclick="showGridOption(${masterData.auditParaId},'E')"
													title="<spring:message code="audit.mgmt.edit" text="edit"></spring:message>">
													<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										 	    	</button>	
												 </c:if>
												 <c:if test="${statusCode.lookUpCode ne 'D' }">
												 	<button type="button" class="btn btn-blue-2 btn-sm margin-right-5 "
													name="button-plus" id="button-plus"
													onclick="showGridOption(${masterData.auditParaId},'V')"
													title="
									      			<spring:message code="audit.mgmt.view" text="view"></spring:message>">
													<i class="fa fa-eye" aria-hidden="true"></i>
													</button>

													<button type="button" class="btn btn-sm"
													name="button-123" id=""
													onclick="dashboardViewHistory('null','${masterData.auditParaCode}','${masterData.auditEntryDate}','Audit Para Entry Approval','${masterData.auditParaChk}')"
													title="<spring:message code="audit.history" text="history"></spring:message>">
													<i class="fa fa-history" aria-hidden="true"></i>
										 	    	</button>
												 </c:if>
											</c:if>
											
																						
										</c:forEach>
											
											
																				
										</td>  
									
									</tr>
								
							
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					
					
					
					
					
			</form:form>
			
		</div>
	</div>
</div>
