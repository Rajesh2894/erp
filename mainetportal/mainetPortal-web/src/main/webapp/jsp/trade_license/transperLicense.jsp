<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/trade_license/transperLicense.js"></script>

<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="trade.transper.form"
						text="Transfer of License"></spring:message></strong>
			</h2>
			<apptags:helpDoc url="TransperLicenseForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">


			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /></span>
			</div>


			<form:form action="TransperLicense.html" class="form-horizontal form"
				name="TransperLicense" id="TransperLicense">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="license.no" text="License No." /></label>
					<div class="col-sm-4">

						<form:input path="tradeMasterDetailDTO.trdLicno" type="text"
							class="form-control" id="trdLicno" />

					</div>


				</div>


				<div class="form-group searchBtn">
					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2 " id="serchBtn"
							onclick="searchLicenseDetails()">
							<i class="fa fa-search"></i>
							<spring:message code="property.changeInAss.Search" />
						</button>
						<button type="button" class="btn btn-danger" id="btn3"
							onclick="window.location.href='CitizenHome.html'">
							<spring:message code="trade.cancel" text="Cancel" />
						</button>

					</div>

				</div>


			</form:form>

		</div>

	</div>

</div>