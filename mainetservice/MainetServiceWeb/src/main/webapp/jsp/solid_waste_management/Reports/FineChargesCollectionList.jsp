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
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/report/fineChargesCollectionReport.js"></script>

<script>
	$(document).ready(function() {
		$(".fromDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".toDateClass").datepicker("option", "minDate", selected)
			}
		});
		$(".toDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".fromDateClass").datepicker("option", "maxDate", selected)
			}
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>Fine/Charges Collection</h2>
		</div>
		<apptags:helpDoc url="FineChargesCollectionReport.html"></apptags:helpDoc>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal">
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div id="a0" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="date-1493383113506" class="col-sm-2 control-label">Report
										Type</label>
									<div class="col-sm-4">
										<label for="radio-group-1492067297931-0" class="radio-inline">
											<form:radiobutton path="" class="radio-group"
												name="reporttype" id="radio1" aria-required="true"
												value="Summary" /> Summary
										</label> <label for="radio-group-1492067297931-1" class="radio-inline">
											<form:radiobutton path="" class="radio-group"
												name="reporttype" id="radio2" aria-required="true"
												value="Detail" /> Detail
										</label>
									</div>
								</div>
								<div class="form-group">
									<label for="select-1479365836957"
										class="col-sm-2 control-label required-control">ULB
										Name</label>
									<div class="col-sm-4">
										<form:select class="form-control chosen-select-no-results mandColorClass"
											name="select-1479365836957" path="" id="select-1479365836957">
											<c:forEach items="${command.listOfUlb}" var="org">
												<option value="${org.orgid}">${org.ONlsOrgname}</option>
											</c:forEach>
										</form:select>
									</div>
									<label for="FineChargeType"
										class="col-sm-2 control-label required-control">Fine/Charge
										Type</label>
									<div class="col-sm-4">
										<form:select path="" class="form-control mandColorClass"
											name="select-1479365836957" id="FineChargeType">
											<option value="option-1">Option-1</option>
											<option value="option-2">Option-2</option>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<apptags:date labelCode="vehicle.fuelling.fromDate"
										fieldclass="fromDateClass" datePath=""
										cssClass="fromDateClass fromDate" isMandatory="true">
									</apptags:date>
									<apptags:date labelCode="To Date" fieldclass="toDateClass"
										datePath="" cssClass="toDateClass toDate" isMandatory="true">
									</apptags:date>
								</div>
							</div>
							<div class="text-center padding-top-10">
								<button type="button" class="btn btn-success btn-submit"
									onClick="fineChargesCollectionPrint('FineChargesCollectionReport.html','report')"
									data-original-title="View">
									<spring:message code="solid.waste.print" text="Print"/>
								</button>
								<button type="Reset" class="btn btn-warning"
									onclick="resetTrip()">
									<spring:message code="solid.waste.reset" text="Reset" />
								</button>
								<apptags:backButton url="AdminHome.html"></apptags:backButton>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>