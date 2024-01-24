<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/social_security/schemeConfigurationMaster.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->

<script>
$(document).ready(function() {

	$("#configurationId").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});
</script>

<div class="content">
	<div class="widget">
			<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<button type="button" class="close" onclick="closeOutErrBox()"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span id="errorId"></span>
		</div>
		
		<div class="widget-header">
			<h2>
				<strong><spring:message code="config.mst"
						text="Configuration Master" /></strong>
			</h2>
		</div>
	
		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="schemeConfigurationMaster.html" method="POST"
				name="configurationMaster" class="form-horizontal"
				id="configurationMaster" commandName="command">


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="social.sec.schemename" /></label>

					<div class="col-sm-4">

						<form:select name="applicationformdtoId"
							path="configurtionMasterDto.schemeMstId" id="schemeMstId"
							class="form-control chosen-select-no-results" disabled="false">
							<option value="0"><spring:message text="Select" /></option>
							<c:forEach items="${command.serviceList}" var="objArray">
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
										<form:option value="${objArray[0]}" code="${objArray[2]}"
											label="${objArray[1]}"></form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${objArray[0]}" code="${objArray[2]}"
											label="${objArray[3]}"></form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-top-10">
					
					<button type="button" class="btn btn-blue-2"
						onclick="searchConfigurationMaster()" title="Search ">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="social.btn.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="resetlistForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="social.btn.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addConfigurationMaster('schemeConfigurationMaster.html' ,'C')"
						title="Add ">
						<i class="fa fa-plus-circle" aria-hidden="true"></i>
						<spring:message code="social.btn.add" text="Add"></spring:message>
					</button>
				</div>

				<div class="table-responsive" id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="configurationId" class="configurationTable">
							<thead>
								<tr>
									<th class="text-center" width ="8%"><spring:message code="social.schemeCode"
											text="Scheme Code" /></th>
									<th><spring:message code="social.sec.schemename"
											text="Scheme Name" /></th>
									<th><spring:message
											code="social.demographicReport.fromdate" text="From Date" /></th>
									<th><spring:message code="social.demographicReport.todate"
											text="To Date" /></th>
									<th width ="8%"><spring:message
											code="config.mst.beneficiary.count.number"
											text="Beneficary Count" /></th>
									<th width ="8%"><spring:message code="sor.action" text="Action" /></th>
								</tr>

							</thead>
							<tbody>
								<c:forEach items="${command.configurationMasterList}"
									var="data">
									<tr>
										<td class="text-center">${data.schemeCode}</td>
										<td> ${data.schemeName}</td>
										<td class="text-center"><fmt:formatDate value="${data.fromDate }" type="date" pattern="dd/MM/yyyy" /></td>
										<td class="text-center"><fmt:formatDate value="${data.toDate }" type="date" pattern="dd/MM/yyyy" /></td>
										<td>${data.beneficiaryCount}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Advertiser Master"
												onclick="editConfigurationMaster(${data.configurationId},'V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Advertiser Master"
												onclick="editConfigurationMaster(${data.configurationId},'E')">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button>
										</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
</div>
