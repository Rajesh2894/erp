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
<script src="js/rnl/service/waterTankerBookingForm.js"></script>
<script src="js/mainet/dashboard/moment.min.js"></script>
<script>
	$(document).ready(
			function() {
				var unavailableDates = [];
				<c:forEach items="${command.fromAndToDate}" var="record">
				unavailableDates.push('${record}');
				</c:forEach>

				function unavailable(date) {
					dmy = date.getDate() + "-" + (date.getMonth() + 1) + "-"
							+ date.getFullYear();
					if ($.inArray(dmy, unavailableDates) == -1) {
						return [ true, "" ];
					} else {
						return [ false, "", "Unavailable" ];
					}
				}

				$('.lessthancurrdateto').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					yearRange : "-100:-0",
					numberOfMonths : 2,
					minDate : 0,
					maxDate : '+6m',
					beforeShowDay : unavailable,
					onSelect : checkBookedDate,
					onClose : function() {
						$(this).valid();
					}

				});

				$('.lessthancurrdatefrom').datepicker(
						{
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							yearRange : "-100:-0",
							numberOfMonths : 2,
							minDate : 0,
							maxDate : '+6m',
							beforeShowDay : unavailable,
							onSelect : function(selected) {
								$(".lessthancurrdateto").datepicker("option",
										"minDate", selected)
								checkBookedDate();
							},
							onClose : function() {
								$(this).valid();
							}
						});
				$("#displayOrNot").hide();
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
									code="rnl.estate.propInfo" text="Property Information"></spring:message></a>
						</h4>
					</div>
					<div id="estate" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
									<tr>
										<th width="50"><spring:message code="rnl.estate.code" text="Estate Code"></spring:message></th>
										<th width="180"><spring:message code="rnl.estate.name" text="Estate Name"></spring:message></th>
										<th width="50"><spring:message code="rnl.property.code" text="Property Code"></spring:message></th>
										<th width="180"><spring:message code="rnl.property.name" text="Property Name"></spring:message></th>
										<th width="50"><spring:message code="rnl.unit.number" text="Unit Number"></spring:message></th>
										<th width="100"><spring:message code="rnl.occupancy.type" text="Occupancy Type"></spring:message></th>
										<th width="80"><spring:message code="rnl.usage" text="Usage"></spring:message></th>
										<th width="80"><spring:message code="rnl.floor" text="Floor"></spring:message></th>
										<th width="100"><spring:message code="rnl.total.area" text="Total Area"></spring:message></th>
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
								<spring:message
									code="rnl.estate.applicantInfo" text="Applicant Information"></spring:message> </a>
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
									code="rnl.estate.bookingDetails" text="Booking Details"></spring:message></a>
						</h4>
					</div>
					<div id="Booking_Details" class="panel-collapse collapse">
						<div class="panel-body">
						<spring:message code="rnl.empty.validation.message" text="This field is required." var="reqField"/>
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="FromDate"><spring:message
										code='rnl.estate.fromDate' text="From Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="bookingReqDTO.estateBookingDTO.fromDate"
											type="text" id="fromDate"
											class="lessthancurrdatefrom  form-control"
											data-rule-required="true"
											data-msg-required="${reqField}"
											disabled="${command.amountToPay ne 0.0}"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i><span class="hide">From
												Date</span></span>
									</div>
								</div>
								<label class="control-label col-sm-2 required-control"
									for="ToDate"><spring:message
										code='rnl.estate.toDate' text="To Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="bookingReqDTO.estateBookingDTO.toDate"
											type="text" id="toDate"
											class="lessthancurrdateto form-control"
											data-rule-required="true"
											data-msg-required="${reqField}"
											disabled="${command.amountToPay ne 0.0}"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i><span class="hide">To Date</span></span>
									</div>
								</div>
							</div>
							<form:hidden id="hideShiftId" path=""
								value="${command.bookingReqDTO.estateBookingDTO.shiftId}" />
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="Shift"><spring:message
										code='rnl.estate.shift' text="Shift" /></label>
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="SHF" />
									<form:select path="bookingReqDTO.estateBookingDTO.shiftId"
										class="shift form-control" id="shiftId"
										onchange="getPropertyFromToTime(${command.propId});"
										data-rule-required="true" data-msg-required="${reqField}">
										<form:option value="">Select</form:option>
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}"
											var="shiftData">
											<form:option value="${shiftData.propShift}"
												code="${shiftData.propShift}">${shiftData.propShiftDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>

								<label class="control-label col-sm-2 "><spring:message
										code='rl.property.label.prop.event.details'
										text="Event Details" /></label>
								<div class="col-sm-4">
									<form:select path="bookingReqDTO.estateBookingDTO.purpose"
										class="form-control" id="eventId" data-rule-required="true" data-msg-required="${reqField}">
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
										code='rnl.estate.checkIn' text="Check In" /></label>
								<div class="col-sm-4">
									<form:input path="" cssClass="form-control" readonly="true"
										id="fromTime" />
								</div>
								<label class="control-label col-sm-2 "><spring:message
										code='rnl.estate.checkOut' text="Check Out" /></label>
								<div class="col-sm-4">
									<form:input path="" cssClass="form-control" readonly="true"
										id="toTime" />
								</div>
							</div>
	
							<div class="form-group" id="displayOrNot">
								<div class="col-xs-10 col-xs-push-2 required-control">
									<label class="checkbox-inline"> <form:checkbox
											id="agrreId" value="Y" path="acceptAgree"></form:checkbox> <strong>
											<spring:message code="rnl.estate.book.agree" text="I Agree"></spring:message>
											<a href="javascript:void(0);"
											onclick="downloadFile('${command.docName}','EstateBooking.html?Download')">
												<spring:message code="rnl.estate.booking.plastic.usage"
													text="Plastic Usage Terms"></spring:message>&amp;<spring:message
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
					<button type="button" id="confirmToProceedBT"
						class="btn btn-success" onclick="getChecklist(this)">
						<spring:message code="water.btn.proceed" />
					</button>
					<input type="button" id="backBtn" class="btn btn-danger"
						onclick="back()" value="<spring:message code="bt.backBtn"/>" />
				</div>
			</c:if>

			<c:if test="${command.enableSubmit eq true}">
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success"
						onclick="saveBooking(this)" id="submit">
						<spring:message code="bt.save" text="save" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetEstate">
						<spring:message code="bt.clear" text="clear"/>
					</button>
					<input type="button" id="backBtn" class="btn btn-danger"
						onclick="back()" value="<spring:message code="bt.backBtn" text="back"/>" />
				</div>
			</c:if>
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


