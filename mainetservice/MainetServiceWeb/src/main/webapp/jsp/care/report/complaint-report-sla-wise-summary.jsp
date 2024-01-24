<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-report.js"></script>
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
					"lengthChange" : true,
					"columnDefs": [
						  {bSortable: false, targets: [0]}
						]
				});
			});
});
</script>
<style>
.row h3{
	font-size:20pt;
}
.row p{
	font-size: 10pt;
}
#img{
			background-image: none;
		}
#report-table tr th{
	padding-right:2rem !important;
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
          	<div class="row margin-top-5 clear">
					<%-- <div class="col-sm-12 col-xs-12 text-right">
					<p>
							<spring:message code="care.reports.generatedDate" text="Generated Date " />: ${command.complaintReport.dateTime}</p>
					</div> --%>
				</div>
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
	               <%--  <p class="excel-title" style="font-size: 15pt;">${command.complaintReport.title}</p>
	                <p class="text-center"> <spring:message code="care.reports.fromDate" text="From Date"/>: ${command.complaintReport.fromDate} &nbsp;&nbsp;
	                <spring:message code="care.reports.toDate" text="To Date"/>: ${command.complaintReport.toDate} </p>
	                <p><spring:message code="care.reports.department" text="Department"/>: ${command.complaintReport.departmentName}</p> --%>
	              
	              </div>
              <!-- D#127292 -->
					<c:choose>
						<c:when test="${command.kdmcEnv eq 'Y'}">
							<%--<div class="col-xs-3 text-right"></div>--%>
						</c:when>
						<c:otherwise>
							<div class="col-xs-3 text-right"><img src="${userSession.orgLogoPath}" width="80"></div>
						</c:otherwise>
					</c:choose>
          

   			<%--  <div class="row margin-top-30 padding-left-10-per">
            	
            	<div class="col-xs-3 col-sm-2 control-label"><spring:message code="care.reports.fromDate" text="From Date"/></div>
            	<div class="col-xs-3 col-sm-3"> ${command.complaintReport.fromDate}</div>
            	<div class="col-xs-3 col-sm-2 control-label"><spring:message code="care.reports.toDate" text="To Date"/></div>
            	<div class="col-xs-3 col-sm-2"> ${command.complaintReport.toDate}</div>
            	
            </div>
            <div class="row margin-top-10 padding-left-10-per">
            	
            	<div class="col-xs-3 col-sm-2 control-label"><spring:message code="care.reports.department" text="Department"/></div>
            	<div class="col-xs-3 col-sm-3">${command.complaintReport.departmentName}</div>
            	<div class="col-xs-3 col-sm-2 control-label"><spring:message code="care.reports.status" text="Status"/></div>
            	<div class="col-xs-3 col-sm-2"> ${command.complaintReport.slaStatus}</div>
            	
            </div>
            <div class="row margin-top-10 padding-left-10-per">
            	
            	<div class="col-xs-3 col-sm-2 control-label"><spring:message code="care.reports.generatedBy" text="Generated By"/></div>
            	<div class="col-xs-3 col-sm-3"> ${userSession.employee.empname} ${userSession.employee.empmname} ${userSession.employee.emplname}</div>
            	<div class="col-xs-3 col-sm-2 control-label"><spring:message code="care.reports.generatedDate" text="Generated Date"/></div>
            	<div class="col-xs-3 col-sm-2"> ${command.complaintReport.dateTime}</div>
            	
            </div> --%>
            		<div class="col-xs-12 col-sm-12 label-text">
		             	<table class="care-report-table-header care-exl-export margin-left-25" align="center">
							<tr>
								<th colspan="2"><spring:message code="care.reports.fromDate" text="From Date"/></th>
								<td colspan="2"> ${command.complaintReport.fromDate}</td>
								<th colspan="2"><spring:message code="care.reports.toDate" text="To Date"/></th>
								<td colspan="2"> ${command.complaintReport.toDate}</td>
							</tr>
							<tr>
								<th colspan="2"><spring:message code="care.reports.department" text="Department"/></th>
								<td colspan="2">${command.complaintReport.departmentName}</td>
								<th colspan="2"><spring:message code="care.reports.status" text="Status"/></th>
								<td colspan="2">${command.complaintReport.slaStatus}</td>
							</tr>
							<tr>
								<th colspan="2"><spring:message code="care.reports.generatedBy" text="Generated By"/></th>
								<td colspan="2"> ${userSession.employee.empname} ${userSession.employee.empmname} ${userSession.employee.emplname}</td>
								<th colspan="2"><spring:message code="care.reports.generatedDate" text="Generated Date"/></th>
								<td colspan="2"> ${command.complaintReport.dateTime}</td>
							</tr>
							
						</table>
					</div>	
				</div>
			 </div>	
            <div id="export-excel">
            <div class="table-responsive margin-top-10">
              <table class="table table-bordered margin-bottom-10 care-exl-export" id="report-table">
              	<div class="excel-title" id='tlExcel'>${command.complaintReport.title}</div>
              		<thead>
	              		<tr >
	              		  <th  width="10%" class="text-center" id="img"><spring:message code="care.reports.SrNo" text="Sr. No."/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.department" text="Complaint Type"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.complaintType" text="Complaint Sub Type"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.resolved.withinSLa" text="Total Complaint Resolved Within SLA"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.resolved.beyoundSLa" text="Total Complaint Resolved beyond SLA"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.open.withinSLa" text="Total Complaint open Within SLA"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.open.beyoundSLa" text="Total Complaint open beyond SLA"/></th>
		                </tr>
	                </thead>
	                
	            
	                <tbody>
     				
	               <c:set var="totalCountWithinResSla" value="0"></c:set>
	               <c:set var="totalCountBeyoundResSla" value="0"></c:set>
	               <c:set var="totalCountWithinPenSla" value="0"></c:set>
	               <c:set var="totalCountBeyoundPenSla" value="0"></c:set>				
     				
     				<c:forEach items="${command.complaintReport.complaintList}" var="complaint" varStatus="status">
     					<tr>
     						<td class="text-center">${status.index + 1}</td>
     					 	<td><c:out value="${complaint.departmentName}"></c:out></td>		 	
     					 	<td><c:out value="${complaint.comlaintSubType}"></c:out></td>
     					 	<td class="text-center"><c:out value="${complaint.totalResolvedWithinSla}"></c:out></td>
     					 	<td class="text-center"><c:out value="${complaint.totalResolvedBeyoundSla}"></c:out></td>
     					 	<td class="text-center"><c:out value="${complaint.totalPendingWithinSla}"></c:out></td>  					 	
     					 	<td class="text-center"><c:out value="${complaint.totalPendingbeyoundSla}"></c:out></td>    					  
     					  <c:set var="totalCountWithinResSla" value="${totalCountWithinResSla + complaint.totalResolvedWithinSla}"></c:set>
     					  <c:set var="totalCountBeyoundResSla" value="${totalCountBeyoundResSla + complaint.totalResolvedBeyoundSla}"></c:set>
     					  <c:set var="totalCountWithinPenSla" value="${totalCountWithinPenSla + complaint.totalPendingWithinSla}"></c:set>
     					  <c:set var="totalCountBeyoundPenSla" value="${totalCountBeyoundPenSla + complaint.totalPendingbeyoundSla}"></c:set>
  					   </tr>
     				</c:forEach>
     				</tbody>
     				   		<tr class="text-bold totalCount">
									<th ></th>
									<th ></th>									
									<th class="text-center"><spring:message code="care.reports.total" text="Total"/></th>
									<th class="text-center"><c:out value="${totalCountWithinResSla}"/></th>
									<th class="text-center"><c:out value="${totalCountBeyoundResSla}"/></th>
									<th class="text-center"><c:out value="${totalCountWithinPenSla}"/></th>
									<th class="text-center"><c:out value="${totalCountBeyoundPenSla}"/></th>									
							</tr>  
						</tfoot>
              </table>
            </div>
            </div>
             <div class="text-center margin-top-10 remove-btn">
              <button onclick="PrintDiv('${command.complaintReport.title}');" class="btn btn-success hidden-print" type="button"><i class="fa fa-print"></i>
              	<spring:message code="care.receipt.print" text="Print"/>
              </button>
              <button type="button" class="btn btn-warning hidden-print" onclick="javascript:openRelatedForm('GrievanceReport.html?grievanceSlaWise','this');"><spring:message code="care.back" text="Back"/></button>
            </div> 
          </form:form>
        </div>
      </div>
    </div>