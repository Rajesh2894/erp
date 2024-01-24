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
<script type="text/javascript"
	src="js/works_management/wmsmaterialmaster.js"></script>
<!-- <link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.master.summary" text="" />
			</h2>
			<div class="additional-btn">
			<apptags:helpDoc url="WmsMaterialMaster.html"></apptags:helpDoc>

			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="WmsMaterialMaster.html" class="form-horizontal"
				name="wmsMaterialMaster" id="wmsMaterialMaster">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<!-- Start Each Section -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="material.master.summary" /> </a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<%-- <label for="sortype"
										class="col-sm-2 control-label required-control"><spring:message
											code="material.master.csortype" /> </label>
									<div class="col-sm-4">
										<apptags:lookupField items="${command.getLevelData('SOR')}"
											path="scheduleOfRateMstDto.sorType"
											showOnlyLabel="material.master.csortype"
											selectOptionLabelCode="Select"
											cssClass="mandColorClass form-control col-sm-4 sorType" changeHandler="getsorNameByType();"></apptags:lookupField>
									</div> --%>

									<label class="label-control col-sm-2"><spring:message
											code="material.master.ssorname" text="Select SOR Name" /></label>
									<div class="col-sm-4">
										<form:select path="scheduleOfRateMstDto.sorName" id="sorName"
											onchange="getDateBySorName();" class="form-control">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.activeScheduleRateList}"
												var="activeSor">
												<form:option value="${activeSor.sorId }">${activeSor.sorName }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<apptags:date fieldclass="datepicker"
									datePath="scheduleOfRateMstDto.sorFromDate"
									labelCode="material.master.startdate"
									cssClass="mandColorClass custDate" readonly="true"></apptags:date>
								<apptags:date fieldclass="datepicker"
									datePath="scheduleOfRateMstDto.sorToDate"
									labelCode="material.master.enddate"
									cssClass="mandColorClass custDate" readonly="true"></apptags:date>
							</div>
						</div>
					</div>
				</div>
				<!-- End Each Section -->

				<!-- Start button -->
				<div class="text-center clear padding-10">

					<button class="btn btn-blue-2 search"
						onclick="searchMaterialMaster();" type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="" />
					</button>
					<button class="btn btn-primary add"
						onclick="openAddMaterialMaster('WmsMaterialMaster.html','AddMaterialMaster');"
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="material.master.add" text="" />
					</button>

					<button class="btn btn-success add"
						onclick="showGridOption('0','U');" type="button">
						<i class="button-input"></i>
						<spring:message code="leadlift.master.excel.import.export"
							text="Excel Import/Export" />
					</button>
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
				<!-- End button -->


				<div id="" align="center">
					<table id="MaterialMasterGrid"></table>
					<div id="MaterialPager"></div>
				</div>

			</form:form>
			<!-- End Each Section -->
		</div>

		<!-- End Form -->
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End Widget  here -->
