<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-report.js"></script>
<script type="text/javascript" src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript" src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<style>
#report-table tr th{
	padding-right:2rem !important;
}
.padding-left-10-per {
	padding-left:10%;	
}
.widget-content.padding.detail-summary-reports {
	padding: 2rem;
}
#tlExcel{
display: none;
}
</style>
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
						  {bSortable: false, targets: [0,1]}
						]
				});
			});
				
/* 	$(".table").tablesorter().tablesorterPager({
		container: $(".ts-pager"),
		cssGoto  : ".pagenum",
		removeRows: false,
 	});
	var count = $("#report-table thead th").length;
	$('#report-table tfoot tr th.ts-pager').attr('colspan',count); */
});
</script>

    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown">
      <div class="widget invoice" id="report-print">
        <div class="widget-content padding detail-summary-reports">
          <form:form action="" method="get">
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
		                
		                <p class="excel-title">${command.complaintReport.title}</p>
		              </div>
              <!-- D#127292 -->
					<c:choose>
						<c:when test="${command.kdmcEnv eq 'Y' || command.dsclEnv eq 'Y' || command.asclEnv eq 'Y'}">
							<%--<div class="col-xs-3 text-right"></div>--%>
						</c:when>
						<c:otherwise>
							<div class="col-xs-3 text-right"><img src="${userSession.orgLogoPath}" width="80"></div>
						</c:otherwise>
					</c:choose>
            	<div class="col-xs-12 col-sm-12"> 
		            <div class="row margin-top-5 margin-left-5 label-text">
		            	
		            	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.fromDate" text="From Date"/></div>
		            	<div class="col-xs-3 col-xs-3"> ${command.complaintReport.fromDate}</div>
		            	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.toDate" text="To Date"/></div>
		            	<div class="col-xs-3 col-xs-3"> ${command.complaintReport.toDate}</div>
		            	
		            </div>
		            <div class="row margin-top-5 margin-left-5 label-text">
		            	
		            	<div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.reports.department" text="Department"/></div>
		            	<div class="col-xs-3 col-xs-3"> ${command.complaintReport.departmentName}</div>
		            	<div class="col-xs-3 col-xs-3 control-label"><spring:message code="care.reports.generatedDate" text="Generated Date"/></div>
		            	<div class="col-xs-3 col-xs-3"> ${command.complaintReport.dateTime}</div>
		            	
		            </div>
		            
		            <div class="row margin-top-5 margin-left-5 label-text">
			             <div class="col-xs-3 col-sm-3 control-label"><spring:message code="care.datetime" text="Date & Time"/></div>
				         <div class="col-xs-3 col-sm-3">${command.complaintReport.dateTime}</div>
			        </div>
		         </div>   
				 </div>     
            </div>
            <div id="export-excel">
            <div class="table-responsive margin-top-10">
              <table class="table table-bordered margin-bottom-10" id="report-table">
              	<div class="excel-title" id='tlExcel'>${command.complaintReport.title}</div>
              		<thead>
	              		<tr >
	              		<th class="text-center" width="10%" style="vertical-align: middle;"><spring:message code="care.reports.SrNo" text="Sr. No."/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.tokenNo" text="Token No."/></th>
		                  <th class="text-center" width="15%"><spring:message code="prop.rec.print.app.no" text="Application Number"/></th>
		                  <c:set var="prefix" value="${command.prefixName}"></c:set>
		                  <c:set var="prefixlookUps" value="${command.getLookupLabel(prefix)}"/> 
		                  <c:set var="prefixlookUpsLength" value="${fn:length(prefixlookUps)}"></c:set>
		                  <apptags:lookupFieldSet cssClass="form-control required-control"
							baseLookupCode="${prefix}" hasId="true" pathPrefix="careReportRequest.codIdOperLevel" 
							showAll="false"  hasTableForm="true" showData="false"/>
		                  
		                  <c:if test="${command.complaintReport.showDepartment}">
		                  	<th class="text-center"width="10%"><spring:message code="care.reports.department" text="Department"/></th>
		                  </c:if>
		                  <th class="text-center" width="20%"><spring:message code="care.reports.complaintType" text="Complaint Type"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.dateOfRequest" text="Date of Request"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.complaintStatus" text="Complaint Status"/></th>
		                  <th class="text-center" width="15%"><spring:message code="care.reports.noOfDaysTaken" text="No of days taken"/></th>
		                </tr>
	                </thead>
	                
	                <!--  <tfoot>
						<tr  class="print-remove">
							<th class="ts-pager form-horizontal">
								<div class="btn-group">
									<button type="button" class="btn first"><i class="fa fa-step-backward" aria-hidden="true"></i></button>
									<button type="button" class="btn prev"><i class="fa fa-arrow-left" aria-hidden="true"></i></button>
								</div>
								<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
							 <!--	<div class="btn-group">
									<button type="button" class="btn next"><i class="fa fa-arrow-right" aria-hidden="true"></i></button>
									<button type="button" class="btn last"><i class="fa fa-step-forward" aria-hidden="true"></i></button>
								</div>
								<select class="pagesize input-mini form-control" title="Select page size">
									<option selected="selected" value="10" class="form-control">10</option>
									<option value="20">20</option>
									<option value="30">30</option>
									<option value="all">All Records</option>
								</select>
								<select class="pagenum input-mini form-control" title="Select page number"></select>
							</th>
						</tr>
					</tfoot> -->
					

							<tbody>
     				<c:forEach items="${command.complaintReport.complaints}" var="complaint" varStatus="status">
     					<tr>
     					<td class="text-center"><c:out value="${status.index + 1}"></c:out></td>
     					 	<td ><c:out value="${complaint.complaintId}"></c:out></td>
     					 	<td ><c:out value="${complaint.applicationId}"></c:out></td>
     					 	<c:if test="${not empty prefix}">
     					 		<c:if test="${prefixlookUpsLength >= 1}">
	     					 		<td>
	     					 			<c:if test="${not empty complaint.codIdOperLevel1}">
		     					 			<c:out value="${complaint.codIdOperLevel1}"></c:out>
		     					 		</c:if>
		     					 		<c:if test="${empty complaint.codIdOperLevel1}">
		     					 			<spring:message code="common.all" text="All" />
		     					 		</c:if>
	     					 		</td>
     					 		</c:if>
	     					 	<c:if test="${prefixlookUpsLength >= 2}">
	     					 		<td>
	     					 			<c:if test="${not empty complaint.codIdOperLevel2}">
		     					 			<c:out value="${complaint.codIdOperLevel2}"></c:out>
		     					 		</c:if>
		     					 		<c:if test="${empty complaint.codIdOperLevel2}">
		     					 			<spring:message code="common.all" text="All" />
		     					 		</c:if>
	     					 		</td>
     					 		</c:if>
	     					 	<c:if test="${prefixlookUpsLength >= 3}">
	     					 		<td>
	     					 			<c:if test="${not empty complaint.codIdOperLevel3}">
		     					 			<c:out value="${complaint.codIdOperLevel3}"></c:out>
		     					 		</c:if>
		     					 		<c:if test="${empty complaint.codIdOperLevel3}">
		     					 			<spring:message code="common.all" text="All" />
		     					 		</c:if>
	     					 		</td>
     					 		</c:if>
     					 		<c:if test="${prefixlookUpsLength >= 4}">
	     					 		<td>
	     					 			<c:if test="${not empty complaint.codIdOperLevel4}">
		     					 			<c:out value="${complaint.codIdOperLevel4}"></c:out>
		     					 		</c:if>
		     					 		<c:if test="${empty complaint.codIdOperLevel4}">
		     					 			<spring:message code="common.all" text="All" />
		     					 		</c:if>
	     					 		</td>
     					 		</c:if>
     					 		<c:if test="${prefixlookUpsLength >= 5}">
	     					 		<td>
	     					 			<c:if test="${not empty complaint.codIdOperLevel5}">
		     					 			<c:out value="${complaint.codIdOperLevel5}"></c:out>
		     					 		</c:if>
		     					 		<c:if test="${empty complaint.codIdOperLevel5}">
		     					 			<spring:message code="common.all" text="All" />
		     					 		</c:if>
	     					 		</td>
     					 		</c:if>
     					 	</c:if>
     					 	<c:if test="${command.complaintReport.showDepartment}">
     					 		<td ><c:out value="${complaint.departmentName}"></c:out></td>
     					 	</c:if>
     					 	<td ><c:out value="${complaint.comlaintSubType}"></c:out></td>
     					 	<td style="text-align: center;">
     					 		<fmt:parseDate pattern="dd/mm/yyyy" value="${complaint.dateOfRequest}" var="date" />
								<fmt:formatDate type="date"   var="marDate"  value="${date}" pattern="yyyymmdd" />
								<span style="display:none"> ${marDate} </span>
     					 		<c:out value="${complaint.dateOfRequest}"></c:out>
     					 	</td>
     					 	<c:set var = "statusString" value = "${complaint.status}"/>
		              		<td style="text-align: center;"><spring:message code="care.status.${fn:toLowerCase(statusString)}" text=""/>     					 	
     					 	<td style="text-align: center;"><c:out value="${complaint.numberOfDay}"></c:out></td>
  					   </tr>
     				</c:forEach>
     				</tbody>  				
              </table>
            </div>
            </div>
             <div class="text-center margin-top-10 remove-btn">
              <button onclick="PrintDiv('${command.complaintReport.title}');" class="btn btn-success hidden-print" type="button"><i class="fa fa-print"></i>
              	<spring:message code="care.receipt.print" text="Print"/>
              </button>
              <button type="button" class="btn btn-warning hidden-print" onclick="javascript:openRelatedForm('GrievanceReport.html?grievanceDeptStatWise','this');"><spring:message code="care.back" text="Back"/></button>
            </div> 
          </form:form>
        </div>
      </div>
    </div>