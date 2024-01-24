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
<%-- <jsp:useBean id="date" class="java.util.Date" scope="request" /> --%>
<apptags:breadcrumb></apptags:breadcrumb>

<script>
$(document).ready(function() {

	$("#advertiserTable").dataTable({
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

<div class = "content">
	<div class = "widget">
		<div class = "widget-header">
			<h2>
				<spring:message code="advertiser.register.table.title"
				text="Advertisement Permit Register" />
			</h2>
			<apptags:helpDoc url="AdvertiserRegister.html"></apptags:helpDoc>
		</div>
		<div class ="widget-content padding">
			<div id = "advertiserRegister">
			<form action="" method="get" class="form-horizontal">

				<div class="adviserRegister ">
					<div class="form-group">
						<div class="form-group">
						<div
							class="col-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
								${ userSession.getCurrent().organisation.ONlsOrgname} <br>
								<h3 class="text-extra-large margin-bottom-0 margin-top-0 text-bold">
								Advertisement Permit Register <br>
							</h3>
							</h3>
						</div>

						<div class="col-sm-2 col-xs-3 pull-right">
							<p>
								<strong><spring:message code="" text="Date" /></strong> :
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
							</p>
						</div>
					</div>
					<div class = "text-center text-bold ">
							<span><spring:message code=""
										text="From Date " /><fmt:formatDate value="${command.fromDate}" pattern="dd/MM/yyyy" /></span>
										&nbsp
							<span><spring:message code=""
									text="To Date " /><fmt:formatDate value="${command.toDate}" pattern="dd/MM/yyyy" /></span>
					</div>
						
							
						
						
				</div>
				
				<!-- <div class="container"> -->
						<div id="export-excel" class ="table-responsive">
							<table class="table table-bordered table-condensed margin-bottom-10 advertiser"
								id = "advertiserTable">
								<thead>
									<tr>
										<th colspan="1" class="text-center" width="10%"><spring:message
												code="" text="Advertisement Permit No." /></th>
										<th colspan="1" class="text-center" width = "15%"><spring:message
												code="" text="Advertiser Name"  /></th>
										<th colspan="1" class="text-center" width ="8%"><spring:message
												code="" text="Registeration Date" /></th>
										<th colspan="1" class="text-center"  width ="8%"><spring:message
												code="" text="Valid Upto" /></th>
										<th colspan="1" class="text-center"><spring:message
												code="" text="Address" /></th>
										<th colspan="1" class="text-center" ><spring:message
												code="" text="PAN" /></th>
										<th colspan="1" class="text-center" width = "10%"><spring:message
												code="" text="GST Number" /></th>
										<th colspan="1" class="text-center"  width ="4%"><spring:message
												code="" text="Status" /></th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="data" items = "${command.advertiserMasterDtoList}">
										<tr>
											<td>${data.agencyLicNo }</td>
											<td>${data.agencyName }</td>
											<td>${data.regDate }</td>
											<td>${data.validUpto }</td>
											<td>${data.agencyAdd }</td>
											<td>${data.panNumber }</td>
											<td>${data.gstNo }</td>
											<td>${data.status }</td>
										</tr>
									</c:forEach>
								
								</tbody>
								</table>
								</div>
							<!-- </div>	 -->
				
			
			</form>
			</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${account.receivable.register}');" 
					 class="btn btn-primary hidden-print"
						data-toggle="tooltip" data-original-title="Print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
	
					<button type="button" class="btn btn-danger" onclick="back();"
						data-toggle="tooltip" data-original-title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
		</div>
	</div>

</div>
