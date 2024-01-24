<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/WasteRateChart.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.wasteRate.heading"
						text="Waste Sale Rate Master" /></strong>
			</h2>
			<apptags:helpDoc url="WasteRateChart.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand" /><i
					class="text-red-1">* </i> <spring:message
						code="solid.waste.mand.field" /> </span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="WasteRateChart.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="UpdateSaleDetailsForm" id="id_UpdateSaleDetailsForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->
				<c:set value="${command.prefixLevel}" var="level" scope="page"></c:set>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="submit" class="button-input btn btn-success"
						onclick="addRateMaster('WasteRateChart.html', 'AddRateMaster')"
						name="button-Add" style="" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="solid.waste.add" text="Add" />
					</button>
				</div>
				<!-- End button -->

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_updateSaleDetailsTable">
							<thead>
								<tr>
									<th width="8%" class="text-center"><spring:message
											code="public.toilet.master.srno" text="Sr. No." /></th>
									<th><spring:message code="swm.wastetype" text="Waste Type" /></th>
									<th><spring:message code="swm.Expenditure.item.name" text="Item Name" /></th>
									<th><spring:message code="swm.fromDate" text="From Date" /></th>
									<th><spring:message code="swm.toDate" text="To Date" /></th>
									<th><spring:message code="swm.wasteRate.perKg" text="Rate /per Kg" /></th>
									<th width="10%"><spring:message code="swm.action"	text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.wasteRateList}" var="wList"	varStatus="loop">
									<tr>
										<td align="center" width="8%">${loop.count}</td>
										<td align="center">${command.getHierarchicalLookUpObject(command.wasteRateList[loop.index].codWast1).getLookUpDesc()}</td>
										<td align="center">${command.getHierarchicalLookUpObject(command.wasteRateList[loop.index].codWast).getLookUpDesc()}</td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${command.wasteRateList[loop.index].wFromDate}" /></td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${command.wasteRateList[loop.index].wToDate}" /></td>
										<td align="right" width="10%">${command.wasteRateList[loop.index].wasteRate} &nbsp;<i
																class="fa fa-inr"></i></td>
										<td class="text-center" width="10%">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View "
												onclick="modifyWasteRateList(${command.wasteRateList[loop.index].wRateId},'WasteRateChart.html','ViewWasteRateList','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit "
												onclick="modifyWasteRateList(${command.wasteRateList[loop.index].wRateId},'WasteRateChart.html','EditWasteRateList','E')">
												<i class="fa fa-pencil"></i>
											</button>
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
</div>





