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
<script type="text/javascript" src="js/rnl/report/report.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script>
	$(function() {
		$("#fromDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		$("#fromDateId").datepicker('setDate', new Date());

		$('#fromDateId, #toDateId').change(function() {
			var check = $(this).val();
			if (check == '') {
				$(this).parent().switchClass("has-success", "has-error");
			} else {
				$(this).parent().switchClass("has-error", "has-success");
			}
		});

		$("#toDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		$("#toDateId").datepicker('setDate', new Date());

		$("#toDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		$("#fromDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
	});

	
</script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.outstanding.report" text="Outstanding Report" />
			</h2>
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
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<form:form action="EstateOutstandingReport.html" cssClass="form-horizontal"
					id="outstandingReportId">
					<!-- Start Validation include tag -->
					<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" /> --%>
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
											code="rnl.outstanding.report" text="Outstanding Report" /> </a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">
									<!-- date picker input set -->
									<div class="form-group">
										<label for="fromDateId"
											class="col-sm-2 control-label required-control"><spring:message
												code="rnl.outstanding.report.date" text="Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="reportDTO.fromDate" id="fromDateId"
													cssClass="mandColorClass form-control"
													data-rule-required="true" maxlength="10" />
												<label class="input-group-addon mandColorClass"
													for="fromDateId"><i class="fa fa-calendar"></i> </label>
											</div>
										</div>
										<%-- <label for="toDateId"
											class="col-sm-2 control-label required-control"><spring:message
												code="" text="To Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="reportDTO.fromDate" id="toDateId"
													cssClass="mandColorClass form-control datepicker"
													data-rule-required="true" maxlength="10" />
												<label class="input-group-addon mandColorClass"
													for="toDateId"><i class="fa fa-calendar"></i> </label>
											</div>
										</div> --%>
									</div>

									<!-- Start button -->
									<div class="text-center clear padding-10">
										<button type="button" class="btn btn-blue-2"
											onclick="viewOutstandingReport(this)" title="Search">
											<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
											<spring:message code="rnl.revenue.viewReport"
												text="View Report" />
										</button>

										<button type="button"
											onclick="window.location.href = '${resetPage}'"
											class="btn btn-warning" title="Reset">
											<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
											<spring:message code="rnl.revenue.reset" text=" Reset" />
										</button>
										<%-- Defect #153688 --%>
										<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
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