<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/billingMethodChange.js"></script>
<!-- Start Content here -->
<style>
#printapplication h2 {
	font-size: 1em;
	background: #dcdcdc;
	padding-left: 5px;
	font-weight: 600;
}
</style>

<script>
	function printAppliactionDiv(divName) {
		var printContents = document.getElementById(divName).innerHTML;
		var originalContents = document.body.innerHTML;
		document.body.innerHTML = printContents;
		window.print();
		document.body.innerHTML = originalContents;
	}
</script>
<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">

			<div class="widget-content padding">

				<form:form action="BillingMethodChange.html"
					class="form-horizontal form"
					name="frmBillingMethodChangeViewApplication"
					id="frmBillingMethodChangeViewApplication">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div id="printapplication">

						<h2>
							<spring:message code="applicantinfo.label.header" />
						</h2>
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label " for="firstName"><spring:message
									code="property.applicantName" text="Applicant Name" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.applicantDetailDto.applicantFirstName}</span>
							</div>
							<label class="col-sm-2 col-xs-2 control-label "><spring:message
									code="ownersdetail.mobileno" text="Mobile No." /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.applicantDetailDto.mobileNo}</span>
							</div>
						</div>
						<div class="clear"></div>

						<div class="form-group">
							<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
								showOnlyLabel="true"
								pathPrefix="provisionalAssesmentMstDto.assWard"
								isMandatory="false" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true" disabled="true"
								cssClass="form-control col-xs-4" />
						</div>
						<div class="clear"></div>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label " for="csAdd"><spring:message
									code="property.Address" text="Address" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.applicantDetailDto.areaName}</span>
							</div>
						</div>

					</div>

					<%-- <input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> --%>
				</form:form>

				<div id="scrutinyDiv">
					<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
				</div>

			</div>
		</div>
	</div>
</div>
