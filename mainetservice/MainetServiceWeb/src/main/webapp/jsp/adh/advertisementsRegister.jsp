<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/adh/advertisementsRegister.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertisements.register.table.title"
					text="Advertisements Register"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="AdvertisementsRegister.html"
				cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="adh.from.date" text="From Date"></spring:message>
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<input class="form-control datepicker" id="fromdate"
								maxlength="10"> <label class="input-group-addon"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="adh.to.date" text="To Date"></spring:message>
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<input class="form-control datepicker" id="todate" maxlength="10">
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.master.advertiser.status" /></label>
					<div class="col-sm-4">
						<form:select path="advertiserMasterDto.agencyStatus"
							id="agencyStatus" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="0">
								<spring:message code="adh.select" />
							</form:option>
							<form:option value="-1">
								<spring:message code="adh.all" text="All" />
							</form:option>
							<form:option value="A">
								<spring:message code="adh.active" />
							</form:option>
							<form:option value="I">
								<spring:message code="adh.inactive" />
							</form:option>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="adh.label.license.type" text="License Type" /></label>
					<c:set var="baseLookupCode" value="LIT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advertisementDto.licenseType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel="License Type" />


				</div>

				<div class="text-center">
					<button type="button" class="btn btn-blue-2" data-toggle="tooltip"
						data-original-title="View Report" id="viewreport"
						onclick="viewAdvReport(this)">
						<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.view.report" text="View Report"></spring:message>
					</button>

					<button type="button" class="btn btn-warning resetSearch"
						data-toggle="tooltip" data-original-title="Reset"
						onclick="window.location.href='AdvertisementsRegister.html'">
						<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
				</div>
		</div>
		</form:form>
	</div>
</div>
</div>