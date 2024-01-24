<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rnl/service/mpbcancel.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.estate.booking.cancel"
					text="Estate Booking Cancellation" />
			</h2>

		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="mpbCancellation.html" cssClass="form-horizontal"
				id="estateCancel" name="estateCancel">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->
				<form:hidden path="estateBookingDTO.id" id="bookingIdSet" />
				<form:hidden path="estateBookingDTO.bookingNo" id="bookingNoSet" />
				<div class="form-group">
					<label for="bookingId"
						class="control-label col-sm-2 required-control"><spring:message
							code='rnl.estate.booking.id' text="Booking Id" /></label>
							
							
							
					 <div class="col-sm-4">
						<form:select id="bookingId"
							cssClass="form-control chosen-select-no-results"
							class="form-control required-control" path="">
							
							<form:option value="0">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							
							<c:forEach items="${command.estateBookings}" var="booking">
								<form:option value="${booking.id}"
									data-apmId="${booking.applicationId}"
									data-orgId="${booking.orgId}">${booking.bookingNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">

					<button type="button" class="btn btn-blue-2" title="Submit"
						id="submitBT" onclick="getBookedPropertyDetails(this)">
						<i class="padding-right-5" aria-hidden="true"></i>
						<spring:message text="Submit" />
					</button>
					
					
					<button type="Reset" class="btn btn-warning" id="resetform" onclick="resetCancelForm(this)"><spring:message text="Reset" /></button>

					<button type="button" class="btn btn-danger"
						onclick="window.location.href='CitizenHome.html'">
						<spring:message code="rnl.master.back" text="Back" />
					</button>

				</div>
				<!-- End button -->

			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
