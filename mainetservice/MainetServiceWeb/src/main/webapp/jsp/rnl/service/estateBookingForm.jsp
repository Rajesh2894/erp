<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% response.setContentType("text/html; charset=utf-8"); %>

<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rnl/service/estateBookingForm.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script>
$(document).ready(function() {
	        var unavailableDates = []; 
	        <c:forEach items = "${command.fromAndToDate}" var = "record">
	        	unavailableDates.push('${record}'); 
	        </c:forEach>

	        function unavailable(date) {
	            dmy = date.getDate() + "-" + (date.getMonth() + 1) + "-" + date.getFullYear();
	            if ($.inArray(dmy, unavailableDates) == -1) { return [true, ""];} else { return [false, "", "Unavailable"];}
	        }

	         $('.lessthancurrdateto').datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: 0,
	            maxDate: '+6m',
	            beforeShowDay: unavailable,
	            onSelect: checkBookedDate,
	            onClose: function() { $(this).valid();}
	        });
	        
	        $('.lessthancurrdatefrom').datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: 0,
	            maxDate: '+6m',
	            beforeShowDay: unavailable,
	            onSelect: function(selected) {
	                $(".lessthancurrdateto").datepicker("option", "minDate", selected); checkBookedDate();
	            },
	            onClose: function() { $(this).valid();}
	        });
	        $("#displayOrNot").hide();
	    });
</script>

<!-- Start info box -->
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="rnl.book.estate" text="Book Estate" />
		</h2>
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"><i
				class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
						code="rnl.book.help" text="Help"></spring:message></span></a>
		</div>
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
		<form:form action="EstateBooking.html" method="POST"
			class="form-horizontal" name="estateBookingForm"
			id="estateBookingForm">
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
										<th width="50"><spring:message code="estate.label.code"
												text="Estate Code"></spring:message></th>
										<th width="180"><spring:message code="estate.label.name"
												text="Estate Name"></spring:message></th>
										<th width="50"><spring:message
												code="rl.property.label.propNo" text="Property Code"></spring:message></th>
										<th width="180"><spring:message
												code="rl.property.label.name" text="Property Name"></spring:message></th>
										<th width="50"><spring:message
												code="rl.property.label.unitno" text="Unit Number"></spring:message></th>
										<th width="100"><spring:message
												code="rl.property.label.Occupancy" text="Occupancy Type"></spring:message></th>
										<th width="80"><spring:message
												code="rl.property.label.Usage" text="Usage"></spring:message></th>
										<th width="80"><spring:message
												code="rl.property.label.Floor" text="Floor"></spring:message>
										</th>
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
								<spring:message code="rnl.book.app.info"
									text="Applicant Information"></spring:message>
							</a>
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
								data-parent="#accordion_single_collapse" href="#Booking_Details"><spring:message
									code="rnl.book.details" text="Booking Details"></spring:message></a>
						</h4>
					</div>
					<div id="Booking_Details" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="FromDate"><spring:message code="rnl.from.date"
										text="From Date"></spring:message></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="bookingReqDTO.estateBookingDTO.fromDate"
											type="text" id="fromDate"
											class="lessthancurrdatefrom  form-control"
											data-rule-required="true" disabled="${command.amountToPay ne 0.0}"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i><span class="hide"><spring:message
													code="rnl.from.date" text="From Date"></spring:message></span></span>
									</div>
								</div>
								<label class="control-label col-sm-2 required-control"
									for="ToDate"><spring:message code="rnl.to.date"
										text="To Date"></spring:message></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="bookingReqDTO.estateBookingDTO.toDate"
											type="text" id="toDate"
											class="lessthancurrdateto form-control"
											data-rule-required="true" disabled="${command.amountToPay ne 0.0}"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i><span class="hide"><spring:message
													code="rnl.to.date" text="To Date"></spring:message></span></span>
									</div>
								</div>
							</div>
							<form:hidden id="hideShiftId" path=""
								value="${command.bookingReqDTO.estateBookingDTO.shiftId}" />

							<form:hidden id="hidePropId" path=""
								value="${command.bookingReqDTO.estateBookingDTO.propId}" />

							<div class="form-group">
								<label class="control-label col-sm-2 required-control" for="Shift">
									<%-- Defect #157984 --%>
									<spring:message	code='rnl.master.shift' text="Shift" />
								</label>
								<div class="col-sm-4">
									<%-- <c:set var="baseLookupCode"
										value="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}" /> --%>
									<form:select path="bookingReqDTO.estateBookingDTO.shiftId"
										class="shift form-control" id="shiftId"
										data-rule-required="true"
										onchange="getPropertyFromToTime(${command.propId})">
									<form:option value=""><spring:message code='rnl.master.select' text="Select" /></form:option>
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}"
											var="shiftData">
											<form:option value="${shiftData.propShift}"
												code="${shiftData.propShift}">${shiftData.propShiftDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<label class="control-label col-sm-2 required-control"><spring:message
										code='rl.property.label.prop.event.details'
										text="Event Details" /></label>
								<div class="col-sm-4">
									<form:select path="bookingReqDTO.estateBookingDTO.purpose"
										class="form-control" id="eventId" data-rule-required="true">
										<%-- <form:option value=""><spring:message code='rnl.master.select' text="Select" /></form:option> --%>
										<c:forEach items="${command.eventDTOsList}" var="lookUp">
											<form:option value="${lookUp.propEvent}">${lookUp.propEventDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<%-- <label class="control-label col-sm-2 required-control"
									for="PurposeofBooking"><spring:message
										code="rnl.book.purpose" text="Purpose of Booking"></spring:message></label>
								<div class="col-sm-4">
									<form:input path="bookingReqDTO.estateBookingDTO.purpose"
										type="text" id="purposeofBooking" class="form-control"
										data-rule-required="true" data-rule-maxlength="250"></form:input>
								</div> --%>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 "><spring:message
										code='rnl.property.shift.check.in' text="Check In" /></label>
								<div class="col-sm-4">
									<form:input path="" cssClass="form-control" readonly="true"
										id="fromTime" />
								</div>
								<label class="control-label col-sm-2 "><spring:message
										code='rnl.property.shift.check.out' text="Check Out" /></label>
								<div class="col-sm-4">
									<form:input path="" cssClass="form-control" readonly="true"
										id="toTime" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 "><spring:message
										code='rl.property.label.no.of.invitees' text="No. of Invitees" /></label>
								<div class="col-sm-4">
									<form:input path="bookingReqDTO.estateBookingDTO.noOfInvitess"
										cssClass="form-control hasNumber" id="noOfInvitess"
										maxlength="8" onchange="checkValidationOfInvitees();" />
								</div>
							</div>
							<div class="form-group" id="displayOrNot">
								<div class="col-xs-10 col-xs-push-2 ">
									<label class="checkbox-inline"> <form:checkbox
											id="agrreId" value="Y" path="acceptAgree"></form:checkbox> <strong>
											<spring:message code="rnl.estate.book.agree" text="I Agree"></spring:message>
											<a href="javascript:void(0);"
											onclick="downloadFile('${command.docName}','EstateBooking.html?Download')">
												<spring:message code="rnl.estate.booking.plastic.usage"
													text="Terms"></spring:message>&amp;<spring:message
													code="rnl.payment.cond" text="Conditions"></spring:message>
										</a>
									</strong>
									</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="payandCheckIdDiv">
					<jsp:include page="/jsp/rnl/service/paymentCheckList.jsp"></jsp:include>
				</div>
			</div>
			<c:if test="${command.enableCheckList eq true}">
				<div class="padding-top-10 text-center" id="chekListChargeId">
					<button type="button" id="confirmToProceedBT" class="btn btn-success"
						title='<spring:message code="water.btn.proceed" />'
						onclick="getChecklistAndCharges(this)">
						<spring:message code="water.btn.proceed" />
					</button>
					<input type="button" id="backBtn" class="btn btn-danger"
						title='<spring:message code="bt.backBtn"/>'
						onclick="back()" value="<spring:message code="bt.backBtn"/>" />
				</div>
			</c:if>

			<c:if test="${command.enableSubmit eq true}">
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit"
						title='<spring:message code="bt.save" />'
						onclick="saveBooking(this)" id="submit">
						<spring:message code="bt.save" />
					</button>
					<%-- <button type="Reset" class="btn btn-warning" id="resetEstate">
						<spring:message code="bt.clear" />
					</button> --%>
					<input type="button" id="backBtn" class="btn btn-danger"
						title='<spring:message code="bt.backBtn"/>'
						onclick="back()" value="<spring:message code="bt.backBtn"/>" />
				</div>
			</c:if>
		</form:form>
	</div>
</div>

<script>
$(document).ready(function() {
    getShiftList(); if ($('#hideShiftId').val() != '') {$('#shiftId').val($('#hideShiftId').val());}
});
</script>

<!-- End of info box -->