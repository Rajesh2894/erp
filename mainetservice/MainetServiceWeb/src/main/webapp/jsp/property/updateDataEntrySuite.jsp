<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/updateDataEntry.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Update Data Entry" /></strong>
			</h2>
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start Form -->
			<form:form action="UpdateDataEntrySuite.html"
				class="form-horizontal form" name="UpdateDataEntrySuite"
				id="UpdateDataEntrySuite">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- Start Each Section -->
				<div class="form-group">

					<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo"
						path="provisionalAssesmentMstDto.assNo" maxlegnth="20"></apptags:input>
					<apptags:input labelCode="property.OldPropertyNo"
						path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20"></apptags:input>


					<!--Search Button-->
					<div class="text-center padding-15 clear">
						<button type="button" class="btn btn-warning"
							onclick="getAssessmentDetail()">
							<spring:message code="property.search" text="" />
						</button>

						<button type="button" class="btn btn-danger"
							data-original-title="Back"
							onclick="window.location.href='UpdateDataEntrySuite.html'">
							<spring:message code="property.reset" text="Reset"></spring:message>
						</button>
					</div>

				</div>
				<!-- End Each Section -->

			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->

