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
<script src="js/property/billingMethodChange.js"
	type="text/javascript"></script>
<div id="dataDiv">
	<!-- End JSP Necessary Tags -->

	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- Start Content here -->
	<div class="content">
		<div class="widget">

			<div class="widget-header">
				<h2>
					<strong><spring:message code="property.billMethodChange"
							text="Bill Method Change" /></strong>
				</h2>
			</div>

			<div class="widget-content padding">
				<!-- Start Form -->
				<form:form action="BillingMethodChange.html"
					class="form-horizontal form" name="BillingMethodChange"
					id="BillingMethodChange">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					
					<div class="form-group">
						<label class="col-sm-2 control-label required-control" for="assNo"><spring:message
								code="property.ChangeInAss.EnterPropertyNo" text="Property No" /></label>
						<div class="col-sm-3">
							<form:input path="provisionalAssesmentMstDto.assNo"
								class="form-control mandColorClass" id="assNo" />
						</div>

						<div class="col-sm-2 text-center">
							<i class="text-red-1"><spring:message code="property.OR" /></i>
						</div>

						<label class="col-sm-2 control-label required-control"
							for="assOldpropno"><spring:message
								code="property.ChangeInAss.oldpid" text="Old Property No" /></label>

						<div class="col-sm-3">
							<form:input path="provisionalAssesmentMstDto.assOldpropno"
								class="form-control mandColorClass" id="assOldpropno" />
						</div>
					</div>

					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2" id="serchBtn"
							onclick="openPropertyDetailss(this)">
							<i class="fa fa-search"></i>
							<spring:message code="property.changeInAss.Search" />
						</button>

						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="bt.backBtn" text="Back"></spring:message>
						</button>
					</div>

					<!-- End Each Section -->
				</form:form>
				<!-- End Form -->

			</div>
		</div>
	</div>
</div>
