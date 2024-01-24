<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<!-- <script type="text/javascript" src="js/land_estate/landEstate.js"></script> -->
<script type="text/javascript" src="js/land_estate/landEstateBill.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Revenue report" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="EstateRevenueReport.html" cssClass="form-horizontal"
				id="revenueReportId">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="" text="Revenue Report" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<!-- date picker input set -->
								<div class="form-group">
									<apptags:date fieldclass="datepicker" labelCode="From Date"
										isMandatory="true" datePath="acquisitionDto.fromDate"></apptags:date>
									<apptags:date fieldclass="datepicker" labelCode="To Date"
										isMandatory="true" datePath="acquisitionDto.toDate"></apptags:date>
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
												code="Type of Proposal" text="Type of Proposal" /></label>
										<div class="radio col-sm-4 margin-top-5">
											<label> <form:radiobutton path="" value="RE" id="type" class="type" checked /> <spring:message code="" /></label> 
											<label> <form:radiobutton path="" value="LE" id="type" class="type" /> <spring:message code="" /></label>
										</div>

								<!-- Start button -->
								<div class="text-center clear padding-10">
									<button type="button" class="btn btn-blue-2" title="Search"
										id="searchlandAcquisition">
										<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
										<spring:message code="land.revenue.viewReport"
											text="View Report" />
									</button>

									<button type="button"
										onclick="landRevenueReport('LandBill.html','landRevenueReport');"
										class="btn btn-warning" title="Reset">
										<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
										<spring:message code="land.revenue.reset" text=" Reset" />
									</button>

								</div>

							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->

		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->