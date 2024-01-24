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
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script src="js/mainet/validation.js"></script>
<script src="js/birthAndDeath/rtsSummaryPage.js"></script>
<!-- End JSP Necessary Tags -->
<script>
	$(document).ready(function() {

		$("#rtsTable").dataTable({
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
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Right To Service" /></strong>
			</h2>
		</div>

		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>

			<form:form action="rtsServices.html" class="form-horizontal form"
				name="" id="rightToService">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<%-- <label class="control-label col-sm-2  required-control"
						for="text-1">Department Name </label>
					<div class="col-sm-4">
						<form:select path="requestDTO.deptId"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select Department Name" disabled="false" id="deId"
							onchange="searchService(this,'rtsService.html','searchService')">
							<form:option value="" selected="true">Select Department Name</form:option>
							<c:forEach items="${command.depList}" var="dept">
								<form:option value="${dept.key}" code="${dept.key}">${dept.value}</form:option>
							</c:forEach>

						</form:select> --%>
				</div>




				<label class="control-label col-sm-2  required-control" for="text-1">Service
					Name </label>
				<div class="col-sm-4">
					<form:select path="requestDTO.serviceId"
						class="form-control mandColorClass chosen-select-no-results"
						label="Select Department Name" disabled="false" id="serviceId"
						onchange="loadApplicationNo('rtsServices.html','searchApplicationId')">
						<!-- Here the option is being loaded in the drop down list using forEach loop  -->
						<form:option value="" selected="true">Select Service Name</form:option>
						<c:forEach items="${command.serviceList}" var="service">
							<form:option value="${service.key}" code="${service.key}">${service.value}</form:option>
						</c:forEach>
					</form:select>
				</div>


				<label class="col-sm-2 control-label"><spring:message
						code="" text="Application No." /></label>
				<div class="col-sm-4">
					<form:select id="applicationId" path="requestDTO.applicationId"
						class="form-control mandColorClass chosen-select-no-results required-control">

						<form:option value="" selected="true">
							<spring:message code="" text="Select" />
						</form:option>

						<c:forEach items="${command.applicationNo}" var="data">
							<form:option value="${data}">${data}</form:option>
						</c:forEach>
					</form:select>
				</div>
		</div>

		<div class="form-group">
			<%-- <label class="col-sm-2 control-label"><spring:message
							code="" text="Application No." /></label>
					<div class="col-sm-4">
						<form:select id="applicationId" path="requestDTO.applicationId"
							class="form-control mandColorClass chosen-select-no-results required-control">
							
							<form:option value="" selected="true">
								<spring:message code="" text="Select" />
							</form:option>
							
					 <c:forEach items="${command.applicationNo}" var="data">
								<form:option value="${data}">${data}</form:option>
					</c:forEach>  
						</form:select>
					</div> --%>
		</div>

		<div class="text-center padding-top-10">

			<%-- <button type="button" class="btn btn-blue-2" onclick=""
				title="Search "  onclick="resetSummaryPage()">
				<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
				<spring:message code="social.btn.search" text="Search"></spring:message>
			</button> --%>
			<button type="button" class="btn btn-blue-2" title="Reset"
				onclick="searchData()">
				<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
				<spring:message code="social.btn.search" text="Search" />
			</button>

			<button type="button" class="btn btn-warning" title="Reset"
				onclick="resetSummaryPage()">
				<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
				<spring:message code="social.btn.reset" text="Reset" />
			</button>
			<button type="button" class="btn btn-success add" title="Add "
				onclick="loadApplicantForm('rtsServices.html','applicantForm',0)">
				<i class="fa fa-plus-circle" aria-hidden="true"></i>
				<spring:message code="social.btn.add" text="Add"></spring:message>
			</button>
		</div>

		<div class="table-responsive" id="export-excel">
			<div class="table-responsive margin-top-10">
				<table class="table table-striped table-condensed table-bordered"
					id="rtsTable" class="rtsTable">
					<thead>
						<tr>
							<th class="text-center"><spring:message code=""
									text="Application Number" /></th>
							<th class="text-center"><spring:message code=""
									text="Service Name" /></th>
							<th><spring:message code="" text="First Name" /></th>
							<th><spring:message code="" text="Last Name" /></th>
							<th><spring:message code="" text="Action" /></th>
						</tr>

					</thead>
					<tbody>
						<c:forEach items="${command.requestDtoList}" var="data">
							<tr>
								<td class="text-center">${data.applicationId}</td>
								<td>${data.serviceShortCode}</td>
								<td>${data.fName}</td>
								<td>${data.lName}</td>
								<td class="text-left">
									<button type="button" class="btn btn-blue-2 btn-sm"
										title="View"
										onclick="loadApplicantForm('rtsServices.html','applicantForm','${data.applicationId}')">
										<i class="fa fa-eye"></i>
									</button>
									<c:forEach items="${command.attachDocsList}" var="lookUp">
									 <c:if
										test="${data.applicationId eq lookUp.idfId}">
										<button type="button"  title="Download">

											<apptags:filedownload filename="${lookUp.attFname}"
												filePath="${lookUp.attPath}"
												actionUrl="rtsServices.html?Download" />

											<!-- <i class="fa fa-print" aria-hidden="true"></i> -->
										</button>
									</c:if>
									</c:forEach>
									 <!-- <button type="button" class="btn btn-warning btn-sm"
										title="Edit Advertiser Master">
										<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
									</button> -->
								</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>


		</form:form>


	</div>
</div>
<!-- End Widget Content here -->
</div>
<!-- End of Content -->
