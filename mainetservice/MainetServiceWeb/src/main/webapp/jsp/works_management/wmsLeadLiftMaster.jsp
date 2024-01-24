<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script src="js/works_management/wmsLeadLiftMaster.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<div class="pagediv">
	<!-- Start breadcrumb Tags -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- End breadcrumb Tags -->

	<!-- Start Content here -->
	<div class="content">
		<!-- Start Main Page Heading -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="leadlift.master.title"
						text="Lead Lift Master" />
				</h2>
				<div class="additional-btn">
					<apptags:helpDoc url="WmsLeadLiftMaster.html"></apptags:helpDoc>
				</div>
			</div>
			<!-- End Main Page Heading -->
			<!-- Start Widget Content -->
			<div class="widget-content padding">
				<!-- Start mand-label -->
				<div class="mand-label clearfix">
					<span><spring:message code="leadlift.master.fieldmand"
							text="Field with" /> <i class="text-red-1"> * </i> <spring:message
							code="leadlift.master.ismand" text="is mandatory" /></span>
				</div>
				<!-- End mand-label -->
				<!-- Start Form -->
				<form:form action="WmsLeadLiftMaster.html" class="form-horizontal"
					name="WmsLeadLiftMaster" id="" method="post">
					<!-- Start Validation include tag -->
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<!-- End Validation include tag -->


					<!-- Start Each Section -->
					<div class="form-group">

						<label class="label-control col-sm-2 required-control"><spring:message
								code="material.master.ssorname" text="Select SOR Name" /></label>
						<div class="col-sm-4">
							<form:select path="" id="sorName"
								class="form-control chosen-select-no-results mandColorClass"
								onchange="getsorDatesBySorNames();">
								<form:option value="">
									<spring:message code="work.management.select" text="Select" />
								</form:option>
								<c:forEach items="${command.scheduleOfRateMstDtoList}"
									var="sorMstDtoList">
									<form:option value="${sorMstDtoList.sorId}">${sorMstDtoList.sorName }</form:option>
								</c:forEach>

							</form:select>
						</div>

						
							<label class="label-control col-sm-2 required-control"><spring:message
									code="material.master.ratetype" text="Rate Type" /></label>
							<div class="col-sm-4">
								<form:select
									class="form-control mandColorClass chosen-select-no-results"
									path="" id="leLi" required="true">
									<form:option value="">
										<spring:message code="work.management.select" text="Select" />
									</form:option>
									<form:option value="L">
										<spring:message code="leadlift.master.lead" text="Lead" />
									</form:option>
									<form:option value="F">
										<spring:message code="leadlift.master.lift" text="Lift" />
									</form:option>
								</form:select>
							</div>
						

					</div>
					<div class="form-group">
						<apptags:date fieldclass="datepicker" labelCode="From Date"
							datePath="wmsLeadLiftMasterDto.sorFromDate" readonly="true"></apptags:date>
						<apptags:date fieldclass="datepicker" labelCode="To Date"
							datePath="wmsLeadLiftMasterDto.sorToDate" readonly="true"></apptags:date>
					</div>

					<!-- End Each Section -->

					<!-- Start button -->
					<div class="text-center clear padding-10">

						<button class="btn btn-blue-2 search"
							onclick="searchLeadLiftEntry(this)" type="button">
							<i class="fa fa-search padding-right-5"></i>
							<spring:message code="works.management.search" text="Search" />
						</button>
						<button class="btn btn-warning"
							onclick="window.location.href='WmsLeadLiftMaster.html'"
							type="button">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
						<button class="btn btn-success add" id="addbtn"
							onclick="openLeadLiftAddForm(this);" type="button">
							<i class="fa fa-plus-circle padding-right-5"></i>
							<spring:message code="works.management.add" text="Add" />
						</button>

						<button class="btn btn-success add" id="exlbtn"
							onclick="showGridOption('','','U');" type="button">
							<i class="button-input"></i>
							<spring:message code="leadlift.master.excel.import.export"
								text="Excel Import/Export" />
						</button>
					</div>
					<!-- End button -->

					<div class="form-group padding-10" id="grid">
						<table id="leadLiftgrid"></table>
						<div id="pagered"></div>
					</div>
				</form:form>
				<!-- End Form -->
			</div>
			<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->
	</div>
	<!-- End of Content -->
</div>