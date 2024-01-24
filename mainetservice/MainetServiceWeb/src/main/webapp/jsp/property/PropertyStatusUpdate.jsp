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
<script type="text/javascript" src="js/property/propertyStatusUpdate.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="propertyTax.statusUpdate"
						text="Property Status Update" /></strong>
			</h2>

		</div>

		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="property.changeInAss"
						text="Property Status Update" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="PropertyStatusUpdate.html"
				class="form-horizontal form" name="PropertyStatusUpdate"
				id="PropertyStatusUpdate">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="assNo"><spring:message
							code="property.ChangeInAss.EnterPropertyNo" text="Property No." /></label>
					<div class="col-sm-3">
						<form:input path="provisionalAssesmentMstDto.assNo"
							class="form-control mandColorClass" id="assNo" />
					</div>

					<div class="col-sm-2 text-center">
						<i class="text-red-1"><spring:message code="property.OR" /></i>
					</div>

					<label class="col-sm-2 control-label required-control"
						for="assOldpropno"><spring:message
							code="property.ChangeInAss.oldpid" text="Old Property No." /></label>
					<div class="col-sm-3">
						<form:input path="provisionalAssesmentMstDto.assOldpropno"
							class="form-control mandColorClass" id="assOldpropno" />
					</div>

				</div>				

				<!--  Buttons -->
				<div class="form-group">
					<div class="text-center padding-15 clear">
						<button type="button" class="btn btn-warning"
							onclick="getPropertyDetailss()">
							<spring:message code="property.search" text="Search" />
						</button>
						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="bt.backBtn" text="Back"></spring:message>
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
