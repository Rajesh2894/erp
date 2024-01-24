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
<script src="js/rnl/service/estateBookingHome.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Amenities And Facilities" />
			</h2>

		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1">*</i> <spring:message code="" text="" /></span>
			</div>

			<form:form action="EstateBooking.html" class="form-horizontal"
				name="vieAminities" id=" vieAminities">
				<form:hidden path="checkBookingFlag" id="checkBookingFlag"/>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="rnl.view.aminities.and.facilities"
										text=" Aminitiess/Facilities" /> </a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<h5 style="color: navy; font-size: medium;">
									<spring:message code='rnl.estate.booking.view.ameties.detials'
										text="Details of Amenities" />
								</h5>
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th width="70px" align="center"><spring:message
													code="ser.no" text="Sr.No" /></th>
											<th width="" align="center"><spring:message code=""
													text="Description Of Amenities" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.amenityDTOsList}"
											var="amenity" varStatus="status">
											<tbody>
												<tr>
													<td>${status.count}</td>
													<td align="center">${amenity.propAmtFacilityDesc}</td>
												</tr>
											</tbody>
										</c:forEach>
									</tbody>
								</table>
								<h5 style="color: navy; font-size: medium;">
									<spring:message code='rnl.estate.booking.view.ameties.detials'
										text="Details of Facilities" />
								</h5>
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th width="70px" align="center"><spring:message
													code="ser.no" text="Sr.No" /></th>
											<th width="" align="center"><spring:message code=""
													text="Description Of Facilities" /></th>
											<th width="" align="center"><spring:message code=""
													text="Quantity"></spring:message></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach
											items="${command.bookingReqDTO.estatePropResponseDTO.facilityDtoList}"
											var="amenity" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td align="center">${amenity.propAmtFacilityDesc}</td>
												<td align="right">${amenity.propQuantity}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" onclick="backData();"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="rnl.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>