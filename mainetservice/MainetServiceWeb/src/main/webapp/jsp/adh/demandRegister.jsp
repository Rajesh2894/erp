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
<script type="text/javascript" src="js/adh/demandRegister.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="demand.register.table.title"
					text="Demand Register"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="DemandRegister.html" cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<!-- <This is for Hierarchical> -->
					<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
						pathPrefix="hoardingMasterDto.hoardingZone"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="false"
						cssClass="form-control margin-bottom-10" showAll="true"
						columnWidth="20%" />
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="adh.financial.year" /></label>
					<div class="col-sm-4">
						<form:select path="" id="finalcialYear"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}"
								var="finalcialYear">
								<form:option value="${finalcialYear.key}"
									code="${finalcialYear.key}">${finalcialYear.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center">
					<button type="button" class="btn btn-blue-2" data-toggle="tooltip"
						data-original-title="View Report" id="viewreport"
						onclick="viewDemandReport(this)">
						<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.view.report" text="View Report"></spring:message>
					</button>

					<button type="button" class="btn btn-warning resetSearch"
						data-toggle="tooltip" data-original-title="Reset"
						onclick="window.location.href='DemandRegister.html'">
						<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>