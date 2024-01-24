
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
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet"
	type="text/css" />
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/fullcalendar/fullcalendar.min.js"></script>
<script src="js/rnl/service/estateBookingHome.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="https://maps.google.com/maps/api/js?sensor=false"></script>
<script async
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCd0nuNcJ4n8CpoP9tkaLkjV-ccGdoYa9o&callback=initMap"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->

<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.estate.booking" text="Estate Booking"></spring:message>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="rnl.book.help" text="Help"></spring:message></span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form method="POST" action="EstateBooking.html"
				class="form-horizontal" name="estateBookingForm"
				id="estateBookingForm">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>

				<div class="form-group">
					<label for="category" class="control-label col-sm-2"><spring:message
							code="rnl.book.Category" text="Category"></spring:message></label>
					<div class="col-sm-4">
						<select class="form-control mandColorClass" id="category" onChange="dispalyCategory()">
							<option value=""><spring:message code="selectdropdown" /></option>
							<c:forEach items="${categorySubType}" var="objArray">
								<option value="${objArray.lookUpId}">${objArray.lookUpDesc}</option>
							</c:forEach>
						</select>
					</div>
					<label for="eventId" class="control-label col-sm-2"><spring:message
							code="rnl.search.event" text="Category"></spring:message></label>
					<div class="col-sm-4">
					
						<select class=" form-control mandColorClass" id="eventId"
							data-rule-required="true">
							<option value="">Select</option>
							
						</select>

					</div>

				</div>
				<div class="form-group">
					<label for="capcityFrom" class="control-label col-sm-2"><spring:message
							code="rnl.search.capFrom" text="capacity From"></spring:message></label>
					<div class="col-sm-4">
						<form:input path="" type="text" id="capcityFrom"
							cssClass="hasNumber form-control text-right" maxlength="8" />
					</div>
					<label for="capcityTo" class="control-label col-sm-2"><spring:message
							code="rnl.search.capTo" text="capacity To"></spring:message></label>
					<div class="col-sm-4">
						<form:input id="capcityTo" path=""
							class="hasNumber form-control text-right" maxlength="8" />
					</div>

				</div>
				<div class="form-group">
					<label for="rentFrom" class="control-label col-sm-2"><spring:message
							code="rnl.search.rentFrom" text="Rent From"></spring:message></label>
					<div class="col-sm-4">
						<form:input path="" id="rentFrom" type="text"
							cssClass="form-control text-right"
							onkeypress="return hasAmount(event, this, 8, 2)" />
					</div>
					<label for="rentTo" class="control-label col-sm-2"><spring:message
							code="rnl.search.rentTo" text="Rent To"></spring:message></label>
					<div class="col-sm-4">
						<form:input path="" id="rentTo" cssClass="form-control text-right"
							onkeypress="return hasAmount(event, this, 8, 2)" />
					</div>
				</div>

				<div class="text-center padding-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="getListOfPropertyForBooking()" id="proceed">
						<spring:message code="rnl.master.search" />
					</button>
				</div>
				<hr>
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="5%"><spring:message
										code="estate.table.column.srno" text="Sr. No."></spring:message></th>
								<th width="25%"><spring:message
										code="rl.property.label.name" text="Property Name"></spring:message></th>
								<th width="10%"><spring:message code="rnl.book.Category"
										text="Category"></spring:message></th>
								<th width="15%"><spring:message code="rnl.grid.capacity"
										text="Capacity"></spring:message></th>
								<th width="20%"><spring:message
										code="estate.label.Location" text="Location"></spring:message></th>
								<th width="10%"><spring:message code="rnl.grid.rentTotal"
										text="Total Rent"></spring:message></th>
								<th width="5%"><spring:message
										code="rnl.grid.view.facility.aminity" text="Amenity/Facility"></spring:message>
								</th>
								<th width="10%"><spring:message
										code="rnl.book.availability" text="Availability"></spring:message></th>
							</tr>
						</thead>
						<tbody id="propertyListId">
							<%--                    <c:forEach items="${propList}" var="data" varStatus="count">
                     <tr>  
                           <td>${count.index+1}</td>
                           <td>${data.propName}</td>
                           <td>${data.category}</td>
                           <td>${data.location}</td>
                           <td><a href="javascript:void(0);" class="btn btn-blue-2 btn-sm" onClick="showCalAndMap(${data.propId})"><i class="fa fa-check-circle-o"></i> <spring:message code="rnl.book.check" text="Check"></spring:message></a></td>
                      </tr>
                   </c:forEach> --%>

						</tbody>
					</table>
				</div>

			</form:form>
			<div id="Availabilitydiv">
				<div class="text-center padding-top-5">
					<button type="button" class="btn btn-blue-2" id="openbookingFormId">
						<i class="fa fa-plus-circle"></i>&nbsp;
						<spring:message code="rnl.book.now" text="Book Now"></spring:message>
					</button>
					<input type="hidden" id="propId">
				</div>
				<div class="row">
					<div class="col-sm-5">
						<div class="text-left padding-bottom-5" id="termCondi"></div>
						<div id="map" style="width: 100%; height: 320px;"></div>
					</div>
					<div class="col-sm-2 margin-top-30" id="propImages"
						style="height: 330px; overflow-y: auto;"></div>
					<div class="col-sm-5">
						<div id="calendar"></div>
						<p class="col-xs-3 padding-left-0">
							<i class="fa fa-square text-red"></i>
							<spring:message code="rnl.book.booked" text="Booked"></spring:message>
						</p>
						<p class="col-xs-5">
							<i class="fa fa-square text-blue-2"></i>
							<spring:message code="rnl.book.partial.booked"
								text="Partial Booked"></spring:message>
						</p>
						<p class="col-xs-4 padding-left-0">
							<i class="fa fa-square text-green-1"></i>
							<spring:message code="rnl.freeze.booking" text="Freeze Booking"></spring:message>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>