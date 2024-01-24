<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script type="text/javascript"
	src="js/vehicle_management/RefuelingAdvice.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,
		});
	});
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="fueling.advice.heading"
						text="Fuelling Advice Reconcilation" />
			</h2>
			<apptags:helpDoc url="fuellingAdvice.html" />
		</div>
		<div class="widget-content padding">

			<form:form action="fuellingAdvice.html" name="RefuelingAdvice"
				id="RefuelingAdviceList" class="form-horizontal">

				<div class="form-group">
					<label class="control-label col-sm-2"> <spring:message
							code="vehicle.fuelling.pump.name" /></label>
					<div class="col-sm-4">
						<form:select cssClass="form-control"
							path="vehicleFuelReconciationDTO.puId" id="puId">
							<form:option value=""><spring:message code="solid.waste.select" text="Select"/></form:option>
							<c:forEach items="${pumps}" var="pump">
								<form:option value="${pump.puId}">${pump.puPumpname}</form:option>
							</c:forEach>

						</form:select>
					</div>
				</div>
				<div class="form-group">
					<%-- Defect #154115 --%>
					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.fuelling.date"
						datePath="vehicleFuelReconciationDTO.inrecFromdt"
						cssClass="custDate" >
					</apptags:date>

					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.maintenance.toDate"
						datePath="vehicleFuelReconciationDTO.inrecTodt"
						cssClass="custDate" >
					</apptags:date>

				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2"
						title='<spring:message code="solid.waste.search" text="Search"/>'
						onclick="searchRefuelingAdvice('fuellingAdvice.html', 'searchRefuelingAdviceReconcilation');">
						<i class="fa fa-search"></i> 
						<spring:message code="solid.waste.search" text="Search"/>
						
					</button>
					<button type="Reset" class="btn btn-warning"
						title='<spring:message code="solid.waste.reset" text="Reset" />'
						onclick="resetRefuelingAdvice();">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						title='<spring:message code="fuelling.advice.form.heading" text="Invoice Reconcilation"/>'
						onclick="addRefuelingAdvice('fuellingAdvice.html','AddRefuelingAdviceReconcilation');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="fueling.pump.master.add" text="Add"/>
					</button>
				</div>
				<div class="table-responsive clear">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped vrr">
						<thead>
							<tr>
								<th><spring:message code="vehicle.maintenance.master.id"
										text="Sr. No" /></th>
								<th><spring:message code="vehicle.fuelling.pump.name"
										text="Pump Name" /></th>
								<th><spring:message code="fuelling.advice.invoiceNo"
										text="Invoice No." /></th>
								<th><spring:message code="fuelling.advice.invoicedate"
										text="Invoice Date" /></th>
								<th><spring:message code="vehicle.fuelling.fromDate"
										text="From Date" /></th>
								<th><spring:message code="vehicle.fuelling.toDate"
										text="To Date" /></th>
								<th><spring:message code="fuelling.advice.invoiceAmount"
										text="Invoice Amount" /></th>
								<th width="100"><spring:message code="solid.waste.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.vehicleFuelReconcilationList}"
								var="data" varStatus="status">
								<fmt:formatDate value="${data.inrecdInvdate}"
									var="inrecdInvdateString" pattern="dd/MM/yyyy" />
								<fmt:formatDate value="${data.inrecFromdt}"
									var="inrecFromdtString" pattern="dd/MM/yyyy" />
								<!-- inrecFromdt -->
								<fmt:formatDate value="${data.inrecTodt}" var="inrecTodtString"
									pattern="dd/MM/yyyy" />
								<!-- inrecTodt -->
								<tr class="appendableClass">
									<td align="center">${status.count}</td>
									<td align="left">${data.puPumpname}</td>
									<td align="right">${data.inrecdInvno}</td>
									<!-- vehicleFuelReconciationDetDTO -->
									<td align="center">${inrecdInvdateString}</td>
									<td align="center">${inrecFromdtString}</td>
									<td align="center">${inrecTodtString}</td>
									<td align="right">${data.inrecdInvamt}</td>
									<td style="width: 20%" align="center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getRefuelingAdviceView('fuellingAdvice.html','viewRefuelingAdviceReconcilation',${data.inrecId})"
											title="<spring:message code="vehicle.view" text="View"/>">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getRefuelingAdvice('fuellingAdvice.html','editRefuelingAdviceReconcilation',${data.inrecId})"
											title="<spring:message code="vehicle.edit" text="Edit"/>">
											<i class="fa fa-pencil"></i>
										</button>
										<button onclick="printdiv('fuellingAdvice.html', 'formForPrint', ${data.inrecId});"
											class="btn btn-darkblue-3" type="button" title="<spring:message
													code="vehicle.fuel.print" text="Print"/>" >
											<i class="fa fa-print"></i> 
										</button> 
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
