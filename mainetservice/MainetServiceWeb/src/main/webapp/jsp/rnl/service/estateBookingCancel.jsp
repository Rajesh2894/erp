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
<script type="text/javascript" src="js/rnl/service/bookingCancel.js"></script>


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
			<form:form action="EstateBookingCancel.html"
				cssClass="form-horizontal" id="estateCancel"
				name="estateCancel">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="estateBookingDTO.id" id="bookingIdSet" />
				<form:hidden path="estateBookingDTO.bookingNo" id="bookingNoSet"/>
				<form:hidden path="flag" id="flag"/>
				<div class="form-group">
					<label for="bookingId" class="control-label col-sm-2 required-control"><spring:message
							code='rnl.estate.booking.id' text="Booking Id" /></label>
					<div class="col-sm-4">
						<form:select id="bookingId" cssClass="form-control chosen-select-no-results"
						 class="form-control required-control"
							path="estateBookingDTO.id">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.estateBookings}" var="booking">
								<form:option value="${booking.id}" data-apmId="${booking.applicationId}" data-orgId="${booking.orgId}">${booking.bookingNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				
					<div id="reasonId">
						<apptags:input labelCode="rnl.estate.reason" isMandatory="true" maxlegnth="400"
							path="estateBookingDTO.cancelReason" cssClass="form-control"></apptags:input>
					</div>
				</div>
				
				<div class="form-group" id="vendor">
						<label class="col-sm-2 control-label required-control">
							<spring:message code="rnl.estate.vendor" text="Vendor" />
						</label>
						<div class="col-sm-4">
							<form:select path="estateBookingDTO.vendorId" cssClass="form-control chosen-select-no-results"
								id="vendorId" data-rule-required="true">
								<form:option value="">
									<spring:message code="master.selectDropDwn" text="Select" />
								</form:option>
								<c:forEach items="${command.vendorList}" var="vendor">
									<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
								</c:forEach>
							</form:select>
						</div>
				</div>
				
				<c:if test="${command.offlineDTO ne null}">
							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"></input>
									<a class="fancybox fancybox.ajax text-small text-info"
										href="EstateBookingCancel.html?showChargeDetails"><spring:message
											code="ChargeMaster.chargeDetails"  text="Charge details"/> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>
							<%-- <jsp:include page="/jsp/payment/onlineOfflinePay.jsp" /> --%>
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
				</c:if>
				
				
				<!-- display Refund details data from reciept_det start-->
				
				<div id="taxDetails">
					<h4>
						<spring:message code="rnl.estate.tax.details" text="Tax Details" />
					</h4>
							<c:set var="d" value="0" scope="page" />
							<div class="table-responsive">
								<table class="table table-bordered table-condensed">
									<thead>
										<tr>
											<th><spring:message
													code="estate.table.column.srno" text="Sr. No" /></th>
											<th><spring:message
													code="rnl.estate.fee.description" text="Fee Description"/></th>
											<th><spring:message
													code="rnl.estate.amount" text="Amount"/></th>
											<th><spring:message
														code="rnl.estate.refund.amount" text="Refund Amount"/></th>
											<c:if test="${command.flag eq 'L' }">		
											<th><spring:message
														code="" text="Account Budget Code"/><span class="mand">*</span> </th>
											</c:if>	
											
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${command.bookingCancelList}" var="charge" varStatus="status">
											<tr>
												<td class="text-center">${status.count}</td>
												<td class="text-center" id="feeDesc${d}">${charge.feeDescription}</p></td>
												<td class="text-center" id="feeAmt${d}">${charge.rfFeeamount}</p></td>
												<td><form:input path="bookingCancelList[${d}].refundAmt" id="refundAmt${d}"
															cssClass="form-control  text-right" 
															onkeypress="return hasAmount(event, this, 8, 2)"
															onchange="getAmountFormatInDynamic((this),'refundAmt')"/>
															</td>
															
										<c:if test="${command.flag eq 'L' }">	
										<td><form:select id="dedPacHeadId${d}" path="bookingCancelList[${d}].rfFeeid"
												class="form-control chosen-select-no-results" onchange="checkDuplicateHead(${d})">
												<form:option value="">
													<spring:message code="account.common.select" />
												</form:option>
												<c:forEach items="${expenditureHeadMap}" varStatus="status"
													var="pacItem">
													<form:option value="${pacItem.key}" code="${pacItem.key}"
														>${pacItem.value}</form:option>

												</c:forEach>
											</form:select></td>
										</c:if>

									</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</table>
					</div>				
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="search" id="searchBT"
						onclick="getChargesForCancelBooking(this)">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="searchBtn" text="Search" />
					</button>
					<button type="button" class="btn btn-blue-2" title="Submit" id="submitBT"
						onclick="saveBookingCancel(this)">
						<i class="padding-right-5" aria-hidden="true"></i>
						<spring:message code="saveBtn" text="Submit" />
					</button>
					<%-- <button type="button" class="btn btn-success btn-submit"
								id="proceedId" onclick="getChargesForCancelBooking(this)">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="bt.proceed" /> --%>
								
					<button type="button"
						onclick="javascript:openRelatedForm('EstateBookingCancel.html');"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="rstBtn" text="Reset" />
					</button>

					<button type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'">
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
