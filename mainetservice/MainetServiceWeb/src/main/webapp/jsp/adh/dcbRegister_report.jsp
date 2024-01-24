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
<apptags:breadcrumb></apptags:breadcrumb>
<div class ="content">
	<div class = "widget">
		<div class = "widget-header">
			<h2>
				<spring:message code="advertiser.register.table.title"
				text="Demand Collection And Balance Register" />
			</h2>
			<apptags:helpDoc url="AdvertiserRegister.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
		<div id = "dcb">
			<form action ="" method = "get" class ="form-horizontal">
				<div class = "dcb">
					<div class="form-group">
						<div class="col-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center text-bold">
							<h2 class="text-extra-large margin-bottom-0 margin-top-0">
								${ userSession.getCurrent().organisation.ONlsOrgname} <br>
							</h2>
							<h2 class="text-extra-large margin-bottom-0 margin-top-0 text-bold">
								Demand Collection And Balance Register <br>
							</h2>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-10 margin-bottom-10">
							<p>
									<span class=""><strong><spring:message code=""
										text="Zone :" /></strong></span>
										<span class="pull-right"><strong><spring:message
										code="" text="Ward :" /> </strong> 
										</span>
							</p>

							<p>
								<span ><strong><spring:message code=""
										text="Financial Year : " /></strong></span>  
							</p>
						</div>
					</div>
					<div class="container">
						<div id="export-excel">
							<table class="table table-bordered margin-bottom-10 dcb">
								<thead>
									<tr>
										<th colspan="1" class="text-center" ><spring:message
												code="" text="Agency Number" /></th>
										<th colspan="1" class="text-center" ><spring:message
												code="" text="Agency Name"  /></th>
										<th colspan="1" class="text-center" ><spring:message
												code="" text="Hording Number" /></th>
										<th colspan="1" class="text-center" ><spring:message
												code="" text="Hording Type" /></th>
										<th colspan="1" class="text-center"><spring:message
												code="" text="Hording Sub-Type" /></th>
										<th colspan="1" class="text-center" ><spring:message
												code="" text="Location" /></th>
										<th colspan="1" class="text-center"><spring:message
												code="" text="Demand" /></th>
										<th colspan="1" class="text-center"  ><spring:message
												code="" text="Collection" /></th>
										<th colspan="1" class="text-center"  ><spring:message
												code="" text="Balance" /></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								
							</tbody>
							</table>
					</div>
				</div>
							
				</div>
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