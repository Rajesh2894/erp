<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-report.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>	
<script>
	$(function() {

		$(document).ready(
				function() {
					$("#report-table").dataTable({
						"oLanguage" : {
							"sSearch" : ""
						},
						"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
						"iDisplayLength" : 5,
						"bInfo" : true,
						"lengthChange" : true
					});
				});

/* 		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});

		var count = $("#report-table thead th").length;
		$('#report-table tfoot tr th.ts-pager').attr('colspan', count); */

	});
</script>
<style>
.row h3{
	font-size:20pt;
}
.row p{
	font-size: 10pt;
}
.padding-left-10-per {
	padding-left:10%;
		
	
}
.care-report-table-header{
	width:100%;
	margin-left: 2rem;
	padding :0px;
}
.care-report-table-header tr td, .care-report-table-header tr th{
	padding :2px;
	background:none !important;
	text-align:left !important;
}
.logo_heading h3{
	text-align:center;
	padding-bottom:3rem;
}
.widget-content.padding.detail-summary-reports {
	padding: 2rem;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="report-print">
		<div class="widget-content padding detail-summary-reports">
			<form:form action="" method="get">
				<div class="row clear" style="margin:5px auto;">
					<%-- <div class="col-sm-12 col-xs-12 text-right">
					<p>
							<spring:message code="care.reports.generatedDate" text="Generated Date " />: ${command.complaintReport.dateTime}</p>
					</div>
				</div> --%>
				<div class="row">
					  <div class="col-xs-1 col-sm-1 org-logo-div"><img src="${userSession.orgLogoPath}"></div> 
					  <div class="col-xs-11 col-sm-11">	
							<div class="text-center org-name-div">
								<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										<h3 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h3>
									</c:when>
									<c:otherwise>
										<h3 class="margin-bottom-0">${userSession.organisation.oNlsOrgnameMar}</h3>
									</c:otherwise>
								</c:choose>
								<p class="excel-title" style="font-size: 12pt;">${command.complaintReport.title}</p>
								
								<%-- <p class="margin-bottom-0 margin-top-0 padding-top-5"><spring:message code="care.reports.fromDate" text="From Date" /> : ${command.complaintReport.fromDate} &nbsp;&nbsp;
								<spring:message code="care.reports.toDate" text="To Date" /> : ${command.complaintReport.toDate}</p>
							    <p><spring:message code="care.reports.generatedBy" text="Generated By"/>
								  : ${userSession.employee.empname} ${userSession.employee.empmname} ${userSession.employee.emplname} &nbsp;&nbsp;
								<spring:message code="care.reports.department" text="Department" />
									: ${command.complaintReport.departmentName}</p> --%>
							</div>
					  <!-- D#127292 -->
					<c:choose>
						<c:when test="${command.kdmcEnv eq 'Y'}">
							<%--<div class="col-xs-3 text-right"></div> --%>
						</c:when>
						<c:otherwise>
							<div class="col-xs-3 text-right"><img src="${userSession.orgLogoPath}" width="80%"></div>
						</c:otherwise>
					</c:choose>
				
				<%-- <div class="row margin-top-30 padding-left-10-per">
            	
            	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.fromDate" text="From Date"/></div>
            	<div class="col-xs-3 col-sm-3"> ${command.complaintReport.fromDate}</div>
            	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.toDate" text="To Date"/></div>
            	<div class="col-xs-3 col-sm-3"> ${command.complaintReport.toDate}</div>
            	
            </div>
            <div class="row margin-top-10 padding-left-10-per">
            	
            	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.generatedBy" text="Generated By"/></div>
            	<div class="col-xs-3 col-sm-3"> ${userSession.employee.empname} ${userSession.employee.empmname} ${userSession.employee.emplname}</div>
            	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.generatedDate" text="Generated Date"/></div>
            	<div class="col-xs-3 col-sm-3"> ${command.complaintReport.dateTime}</div>
            	
            </div>
            <div class="row margin-top-10 padding-left-10-per">
              	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.department" text="Department"/></div>
              	<div class="col-xs-3 col-sm-3">${command.complaintReport.departmentName}</div>
             </div> --%>
             		 	<div class="col-xs-12 col-sm-12 label-text">
							 <table class="care-report-table-header care-exl-export margin-left-25" align="center">
								<tr>
									<th><spring:message code="care.reports.fromDate" text="From Date"/></th>
									<td> ${command.complaintReport.fromDate}</td>
									<th><spring:message code="care.reports.toDate" text="To Date"/></th>
									<td colspan="2"> ${command.complaintReport.toDate}</td>
								</tr>
								<tr>
									<th><spring:message code="care.reports.generatedBy" text="Generated By"/></th>
									<td> ${userSession.employee.empname} ${userSession.employee.empmname} ${userSession.employee.emplname}</td>
									<th><spring:message code="care.reports.generatedDate" text="Generated Date"/></th>
									<td colspan="2"> ${command.complaintReport.dateTime}</td>
								</tr>
								<tr>
									<th><spring:message code="care.reports.department" text="Department"/></th>
									<td>${command.complaintReport.departmentName}</td>
									<th><spring:message code="care.reports.complaintType" text="Complaint Type" /></th>
									<td colspan="2">${command.complaintReport.complaintType}</td>
								</tr>
							</table>
						</div>	
					</div>
				</div>
				<div class="row margin-top-10 padding-left-10-per zone-ward">
	             				<div class="container"> 
	             						<div><apptags:lookupFieldSet baseLookupCode="${command.prefixName}" hasId="true" pathPrefix="careReportRequest.careWardNo" showOnlyLabel="true"/></div>
								</div>			
				</div>
				
				<div id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-bordered margin-bottom-10 care-exl-export"
							id="report-table">
							<div class="excel-title" id='tlExcel'>${command.complaintReport.title}</div>
							<thead>
								<tr>
                                    <th class="text-center"><spring:message code="care.srno" text="Sr. No." /></th>  
									
									<th class="text-center" data-sortinitialorder="asc"><spring:message
											code="care.skdclCompType" text="Complaint Type" /></th>
														
									<th class="text-center" data-sortinitialorder="asc"><spring:message
											code="care.skdclCompSubType" text="Complaint Sub Type" /></th>
									<th class="text-center" data-sortinitialorder="asc"><spring:message
											code="care.reports.modeType" text="Mode Type" /></th>
									<th class="text-center" data-sortinitialorder="asc"><spring:message
											code="care.reports.ward" text="Ward" /></th>
									<th class="text-center" data-sortinitialorder="asc"><spring:message code="care.reports.totalComplaints"
											text="Total Complaints" /></th>
								
								</tr>
							</thead>
					
							<tbody>
						
							   <c:set var="totalCount" value="0" scope="page" /> 
								<c:forEach items="${command.complaintReport.complaintList}" var="complaint" varStatus="status">
									<tr>						
										<td class="text-center">${status.index + 1}</td>														 
										<td><c:out
												value="${complaint.departmentName}"></c:out></td>											
										<td><c:out
												value="${complaint.comlaintSubType}"></c:out></td>
                                        <td><c:out value="${complaint.referenceMode}"></c:out></td> 
                                        <td><c:out value="${complaint.careWardNoEng}"></c:out></td>   																
								      	<td style="text-align: center;"><c:out value="${complaint.totalnoOfComplaints}"></c:out></td>
					                    <c:set var="totalCount" value="${totalCount + complaint.totalnoOfComplaints}"></c:set>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
	                   		<tr class="text-bold totalCount">
									<th ></th>
									<th ></th>
									<th></th>
									<th class="text-center"><spring:message code="care.reports.total" text="Total"/></th>
									<th class="text-center"><c:out value="${totalCount}"/></th>	
									<th></th>								
							</tr>  
						</tfoot>

						</table>

					</div>
				</div>
				<div class="text-center margin-top-10 remove-btn">
					<button onclick="PrintDiv('${command.complaintReport.title}');"
						class="btn btn-success hidden-print" type="button">
						<i class="fa fa-print"></i>
						<spring:message code="care.receipt.print" text="Print" />
					</button>
					<button type="button" class="btn btn-warning hidden-print"
						onclick="javascript:openRelatedForm('GrievanceReport.html?grievanceUserWise','this');">
						<spring:message code="care.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
