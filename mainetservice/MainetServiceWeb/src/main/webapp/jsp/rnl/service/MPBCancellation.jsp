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

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.estate.booking.cancel"
					text="Estate Booking Cancellation" />
			</h2>

		</div>


		<div class="widget-content padding">

			<form:form action="MPBCancellation.html" cssClass="form-horizontal"
				id="mpbestateCancel" name="mpbestateCancel">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden path="estateBookingDTO.id" id="bookingIdSet" />
				<form:hidden path="estateBookingDTO.bookingNo" id="bookingNoSet" />
				<div class="form-group">
					<label for="bookingId"
						class="col-sm-2 control-label required-control"><spring:message
							code='rnl.estate.booking.id' text="Booking Id" /></label>
					<div class="col-sm-4">

						<form:select id="bookingId"
							cssClass="form-control chosen-select-no-results" path="">
							<form:option value="">
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

				<div class="text-center clear padding-10">

					<button type="button" class="btn btn-blue-2" title="Submit"
						id="submitBT" onclick="getBookedPropertyDetails(this)">
						<i class="padding-right-5" aria-hidden="true"></i>
						<spring:message code="saveBtn" text="Submit" />
					</button>


					<button type="button"
						onclick="javascript:openRelatedForm('MPBCancellation.html');"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="rstBtn" text="Reset" />
					</button>

					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="rnl.master.back" text="Back" />
					</button>

				</div>

			</form:form>

		</div>

	</div>

</div>

