<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/file-upload.js"></script>
<%-- <script src="js/rnl/service/waterTankerBookingForm.js"></script> --%>
<script src="js/mainet/dashboard/moment.min.js"></script>
<script>
    $(document).ready(function(){
    	    $('select').attr("disabled", true);
    	    $('input[type=text]').attr("disabled", true);
    	    $('input[type="text"], textarea').attr("disabled", true);
    	    $('select').prop('disabled', true).trigger("chosen:updated");
    });
</script>
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="rnl.water.tanker.booking"
				text="Water Tanker Booking" />
		</h2>
		<apptags:helpDoc url="WaterTankerBooking.html" />
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="rnl.book.field" text="Field with"></spring:message><i
				class="text-red-1">*</i> <spring:message
					code="master.estate.field.mandatory.message" text="is mandatory"></spring:message>
			</span>
		</div>
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:form action="WaterTankerBooking.html" method="POST"
			class="form-horizontal" name="waterTankerBookingForm"
			id="waterTankerBookingForm">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse"
								data-parent="#accordion_single_collapse" href="#estate"> <spring:message
									code="rl.property.label.info" text="Property Information"></spring:message></a>
						</h4>
					</div>
					<div id="estate" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
									<tr>
										<th width="50">Estate Code</th>
										<th width="180">Estate Name</th>
										<th width="50">Property Code</th>
										<th width="180">Property Name</th>
										<th width="50">Unit Number</th>
										<th width="100">Occupancy Type</th>
										<th width="80">Usage</th>
										<th width="80">Floor</th>
										<th width="100">Total Area</th>
									</tr>
									<tr>
										<td>${command.bookingReqDTO.estatePropResponseDTO.estateCode}</td>
										<td><c:choose>
												<c:when test="${userSession.languageId eq 1}">${command.bookingReqDTO.estatePropResponseDTO.estateName}</c:when>
												<c:otherwise>${command.bookingReqDTO.estatePropResponseDTO.estateNameReg}</c:otherwise>
											</c:choose></td>
										<td>${command.bookingReqDTO.estatePropResponseDTO.propertyNo}</td>
										<td>${command.bookingReqDTO.estatePropResponseDTO.propName}</td>
										<td>${command.bookingReqDTO.estatePropResponseDTO.unit}</td>
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<td>${command.bookingReqDTO.estatePropResponseDTO.occupancy}</td>
												<td>${command.bookingReqDTO.estatePropResponseDTO.usage}</td>
												<td>${command.bookingReqDTO.estatePropResponseDTO.floor}</td>
											</c:when>
											<c:otherwise>
												<td>${command.bookingReqDTO.estatePropResponseDTO.occupancyForm}</td>
												<td>${command.bookingReqDTO.estatePropResponseDTO.usageForm}</td>
												<td>${command.bookingReqDTO.estatePropResponseDTO.floorForm}</td>
											</c:otherwise>
										</c:choose>
										<td>${command.bookingReqDTO.estatePropResponseDTO.totalArea}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#Applicant">
								Applicant Information </a>
						</h4>
					</div>
					<div id="Applicant" class="panel-collapse collapse">
						<jsp:include page="/jsp/rnl/service/applicantDetails.jsp"></jsp:include>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#Booking_Details">Booking
								Details</a>
						</h4>
					</div>
					<div id="Booking_Details" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="FromDate">From Date</label>
								<div class="col-sm-4">
									<fmt:formatDate pattern="dd/MM/yyyy"
										value="${command.bookingReqDTO.estateBookingDTO.fromDate}"
										var="fromDate" />
									<form:input type="text" class="form-control" path=""
										value="${fromDate}" id="fromDate"></form:input>
								</div>
								<label class="control-label col-sm-2 required-control"
									for="ToDate">To Date</label>
								<div class="col-sm-4">
									<fmt:formatDate pattern="dd/MM/yyyy"
										value="${command.bookingReqDTO.estateBookingDTO.toDate}"
										var="toDate" />
									<form:input type="text" class="form-control" path=""
										value="${toDate}" id="toDate"></form:input>
								</div>
							</div>
							<form:hidden id="hideShiftId" path=""
								value="${command.bookingReqDTO.estateBookingDTO.shiftId}" />
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="Shift">Shift</label>
								<div class="col-sm-4">

									<form:select path="" class="shift form-control" id=""
										data-rule-required="true">
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}"
											var="shiftData">
											<form:option value="">${shiftData.propShiftDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>


								<label class="control-label col-sm-2 "><spring:message
										code='rl.property.label.prop.event.details'
										text="Event Details" /></label>
								<div class="col-sm-4">
									<form:select path="bookingReqDTO.estateBookingDTO.purpose"
										class="form-control" id="eventId" data-rule-required="true">
										<form:option value="">Select</form:option>
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.eventDTOList}"
											var="lookUp">
											<form:option value="${lookUp.propEvent}">${lookUp.propEventDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 "><spring:message
										code='rl.property.label.from.time' text="Check In" /></label>
								<div class="col-sm-4">
									<form:select path="" class="shift form-control" id=""
										data-rule-required="true">
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}"
											var="shiftData">
											<form:option value="">${shiftData.startTime}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<label class="control-label col-sm-2 "><spring:message
										code='rl.property.label.prop.to.time' text="Check Out" /></label>
								<div class="col-sm-4">
									<form:select path="" class="shift form-control" id=""
										data-rule-required="true">
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}"
											var="shiftData">
											<form:option value="">${shiftData.endTime}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="padding-top-10 text-center">
				<apptags:backButton url="CitizenHome.html" cssClass="btn btn-danger"></apptags:backButton>
			</div>
		</form:form>
	</div>
</div>

<script>
	$(document).ready(function() {
		getShiftList();
		if ($('#hideShiftId').val() != '') {
			$('#shiftId').val($('#hideShiftId').val());
		}
	});
</script>


