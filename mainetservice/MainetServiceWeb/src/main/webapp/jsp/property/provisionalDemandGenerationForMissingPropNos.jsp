
<!-- Start JSP Necessary Tags -->
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
<script type="text/javascript"
	src="js/property/provisionalDemandGenerationForMissingPropNos.js"></script>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="propertyBill.BillGeneration" /></strong>
			</h2>

			<apptags:helpDoc url="PropertyBillGeneration.html"></apptags:helpDoc>

		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="PropertyBillProvisionalDemandGen.html"
				class="form-horizontal form" name="PropertyBillProvisionalDemandGen"
				id="PropertyBillProvisionalDemandGen">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
					<div class="form-group">
					<label class="col-sm-4 control-label"> <spring:message
										code="" text="Click here to generate missing property no for Demand Generation"/> <span class="mand">*</span>
								</label>
								<div class="col-sm-4">
									<button type="button" class="btn btn-info" id="btn1" onclick="generateBillForMissingNos(this);">
					<spring:message code="propertyBill.GenerateBill"/></button>
					</div>
					
					</div>
			</form:form>
		</div>
	</div>
</div>
