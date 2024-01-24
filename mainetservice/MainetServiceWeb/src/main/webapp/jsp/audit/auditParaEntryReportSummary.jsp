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

<!-- <style>
table#addAuditParaDataTable tbody tr td:last-child {
	text-align: center;
}
</style> -->


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Audit Para Report Summary Form" />
			</h2>
			<apptags:helpDoc url="AuditParaReport.html" />
		</div>

		<div class="widget-content padding">
			
		
			<form:form action="AuditParaReport.html" name="AuditParaEntryReport" id="AuditParaEntryReport" cssClass="form-horizontal">
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
						
						
						
					<label class="control-label col-sm-2 required-control" for="Department"><spring:message
							code="audit.mgmt.department" text="Department" /></label>	
				
						
					<div class="col-sm-4">
							<form:select path="auditParaEntryDto.auditDeptId" cssClass="form-control"
										 data-rule-required="true" id="auditDeptId">
										 <form:option value="">
												<spring:message code="Select" text="Select" />
										</form:option>
										<form:option value="0">
												<spring:message code="" text="All" />
										</form:option>
										<c:forEach items="${deptLst}" var="dept">
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
										</c:forEach>
							</form:select>
					</div>
					
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="audit.report2" text="Financial Year"></spring:message>
					</label>
					<div class="col-sm-4">

						<form:select id="auditParaYear" path="auditParaEntryDto.auditParaYear"
							class="form-control mandColorClass" maxLength="200" disabled="${command.saveMode eq 'V'}">
							<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
							<c:forEach items="${aFinancialYr}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				
				</div>
				
				<div class="form-group">
				
					<label class="col-sm-2 control-label required-control"><spring:message
							code="audit.report4" text="From Date" /></label>
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
					
					<label class="col-sm-2 control-label required-control"><spring:message
							code="audit.report5" text="To Date" /></label>
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

						<button type="button" id="searchReport" class="btn btn-blue-2"
							onclick="searchAuditReport('AuditParaReport.html','searchAuditReport');" 
							title="Search">
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="audit.mgmt.button.search" text="Search" />
						</button>

						<button type="button" id="reset"
							onclick="window.location.href='AuditParaReport.html'"
							class="btn btn-warning" title="Reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="audit.mgmt.button.reset" text="Reset" />
						</button>
						
					</div>	
					
			</form:form>
			
		</div>
	</div>
</div>
