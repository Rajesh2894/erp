<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/rnl/service/waterTankerBookingForm.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>

<script>
	$(document).ready(function() {
		$(".returnDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : '-0d',
			changeYear : true,
		});
	});
</script>
<!-- Start info box -->

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="rnl.water.tanker.approval"
				text="Water Tanker Approval" />
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
		<form:form action="WaterTankerApproval.html" method="POST"
			class="form-horizontal" name="waterTankerApproval"
			id="waterTankerApproval">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="payableFlag" id="payableFlag" />

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
									<fmt:formatDate pattern="dd/MM/yyyy"
										value="${command.bookingReqDTO.estateBookingDTO.fromDate}"
										var="fromDate" />
									<form:input type="text" class="form-control" disabled="true"
										path="" value="${fromDate}" id="fromDate"></form:input>
								</div>
								<%-- <div class="col-sm-4">
									<div class="input-group">
										<form:input path="bookingReqDTO.estateBookingDTO.fromDate"
											type="text" id="" class="  form-control"
											data-rule-required="true" readonly="${disabled}"
											disabled="true"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i><span class="hide"><spring:message
													code="rnl.from.date" text="From Date"></spring:message></span></span>
									</div>
								</div> --%>
								<label class="control-label col-sm-2 required-control"
									for="ToDate"><spring:message code="rnl.to.date"
										text="To Date"></spring:message></label>
								<div class="col-sm-4">
									<fmt:formatDate pattern="dd/MM/yyyy"
										value="${command.bookingReqDTO.estateBookingDTO.toDate}"
										var="toDate" />
									<form:input type="text" class="form-control" disabled="true"
										path="" value="${toDate}" id="toDate"></form:input>
								</div>
								<%-- <div class="col-sm-4">
									<div class="input-group">
										<form:input path="bookingReqDTO.estateBookingDTO.toDate"
											type="text" id="" class=" form-control"
											data-rule-required="true" readonly="${disabled}"
											disabled="true"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i><span class="hide"><spring:message
													code="rnl.to.date" text="To Date"></spring:message></span></span>
									</div>
								</div> --%>
							</div>
							<form:hidden id="hideShiftId" path=""
								value="${command.bookingReqDTO.estateBookingDTO.shiftId}" />

							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="Shift"> <spring:message code='rnl.master.shift'
										text="Shift" />
								</label>
								<div class="col-sm-4">
									<form:select path="" class="shift form-control" id=""
										data-rule-required="true" readonly="${disabled}"
										disabled="true">
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}"
											var="shiftData">
											<form:option value="">${shiftData.propShiftDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<label class="control-label col-sm-2 required-control"><spring:message
										code='rl.property.label.prop.event.details'
										text="Event Details" /></label>
								<div class="col-sm-4">
									<form:select path="bookingReqDTO.estateBookingDTO.purpose"
										class="form-control" id="eventId" data-rule-required="true"
										readonly="${disabled}" disabled="true">
										<form:option value="">
											<spring:message code='rnl.master.select' text="Select" />
										</form:option>
										<c:forEach items="${command.eventDTOsList}" var="lookUp">
											<form:option value="${lookUp.propEvent}">${lookUp.propEventDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 "><spring:message
										code='rnl.property.shift.check.in' text="Check In" /></label>
								<div class="col-sm-4">
									<form:select path="" class="shift form-control" id=""
										data-rule-required="true" readonly="${disabled}"
										disabled="true">
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.shiftDTOsList}"
											var="shiftData">
											<form:option value="">${shiftData.startTime}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<label class="control-label col-sm-2 "><spring:message
										code='rnl.property.shift.check.out' text="Check Out" /></label>
								<div class="col-sm-4">
									<form:select path="" class="shift form-control" id=""
										data-rule-required="true" readonly="${disabled}"
										disabled="true">
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
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#Driver_Details">
								<spring:message code="rnl.driver.detail" text="Driver Details"></spring:message>
							</a>
						</h4>
					</div>
					<div id="Driver_Details" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="employee"><spring:message
										code="VehicleLogBookDTO.driverName" text="Driver Name" /></label>
								<div class="col-sm-4">
									<form:select
										class=" mandColorClass form-control chosen-select-no-results"
										id="driverId"
										path="bookingReqDTO.tankerBookingDetailsDTO.driverId"
										data-rule-required="true"
										disabled="${2 eq command.levelcheck}">
										<c:choose>
											<c:when test="${1 eq command.levelcheck}">
												<form:option value="">
													<spring:message code="Select" text="Select" />
												</form:option>
												<c:forEach items="${command.driverName}" var="driver">
													<form:option value="${driver[0]}">${driver[1]}</form:option>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach items="${command.driverName}" var="driver">
													<form:option value="${driver[0]}">${driver[1]}</form:option>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</form:select>
								</div>
								<label class="control-label col-sm-2 " for="remark"><spring:message
										code="rnl.water.remark" text="Remark"></spring:message></label>
								<div class="col-sm-4">
									<form:input path="bookingReqDTO.tankerBookingDetailsDTO.remark"
										type="text" id="remark" class="form-control"
										data-rule-maxlength="250"
										disabled="${2 eq command.levelcheck}"></form:input>
								</div>
							</div>
						</div>
					</div>

				</div>
				<c:if test="${2 eq command.levelcheck}">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#Tanker_Return_Details"> <spring:message
										code="rnl.return.details" text="Return Details"></spring:message>
								</a>
							</h4>
						</div>
						<div id="Tanker_Return_Details" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label for="text-1"
										class="control-label col-sm-2 required-control"> <spring:message
											code="rnl.return.date" text="Return Date" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input class="form-control returnDate" id="returnDate"
												path="bookingReqDTO.tankerBookingDetailsDTO.tankerReturnDate"
												maxlength="10"
												onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
											<label class="input-group-addon" for="trasaction-date-icon30"><i
												class="fa fa-calendar"></i></label>

										</div>
									</div>
									<label class="control-label col-sm-2 " for="returnRemark"><spring:message
											code="rnl.water.remark" text="Remark"></spring:message></label>
									<div class="col-sm-4">
										<form:input
											path="bookingReqDTO.tankerBookingDetailsDTO.returnRemark"
											type="text" id="returnRemark" class="form-control"
											data-rule-maxlength="250"></form:input>
									</div>
								</div>
							</div>
						</div>

					</div>
				</c:if>
				<!-- for level-wise validations -->
				<form:hidden path="" id="levelCheck" value="${command.levelcheck}" />
			</div>

			<div class="padding-top-10 text-center">
				<button type="button" class="btn btn-success" id="submit"
					onclick="saveWaterTankerApprovalForm(this)">
					<spring:message code="adh.save" text="Save"></spring:message>
				</button>
				<button type="button" class="btn btn-danger" id="back"
					onclick="window.location.href='AdminHome.html'">
					<spring:message code="adh.back" text="Back"></spring:message>
				</button>
			</div>
		</form:form>
	</div>
</div>


<!-- End of info box -->