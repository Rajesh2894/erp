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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rnl/service/mpbcancel.js"></script>


<div class="widget">

	<form:form action="mpbCancellation.html" cssClass="form-horizontal"
		id="mpbcancelsave" name="mpbcancelsave">

		<div class="widget-content padding form-horizontal" id="saveCancelMPB">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;"></div>
			<form:hidden path="estateBookingDTO.applicationId" id="estBookId" />
			<c:forEach items="${command.propInfoDtoList}"
				var="propInfoDtoListDTO" varStatus="status">
				<div class="row">
					<div class="col-xs-12 text-center padding-bottom-10">

						<h3>${propInfoDtoListDTO.orgName}</h3>
						<p>
							<spring:message code="rnl.book.app.info"
								text="Applicant Information"></spring:message>
						</p>

					</div>

				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label"><spring:message
							code="rl.property.label.name" text="Property Name"></spring:message></label>

					<div class="col-xs-4 padding-top-5">
						${propInfoDtoListDTO.propName}</div>
					<label class="col-xs-2 control-label"><spring:message
							code="rnl.book.Category" text="Category"></spring:message></label>
					<div class="col-xs-4 padding-top-5">
						${propInfoDtoListDTO.category}</div>


				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label"><spring:message
							code="rl.property.label.Area" text="Area"></spring:message></label>
					<div class="col-xs-4 padding-top-5">
						${propInfoDtoListDTO.areaName}</div>
					<label class="col-xs-2 control-label"><spring:message
							code="rnl.payment.city" text="City"></spring:message></label>
					<div class="col-xs-4 padding-top-5">
						${propInfoDtoListDTO.city}</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label"><spring:message
							code="rnl.payment.pin.code" text="Pin Code"></spring:message></label>
					<div class="col-xs-4 padding-top-5">
						${propInfoDtoListDTO.pinCode}</div>
				</div>



				<div class="padding-top-10">
					<table class="table table-bordered table-striped">
						<tr>
							<th><spring:message text="Booking No"></spring:message></th>
							<th><spring:message code="rnl.payment.applicant"
									text="Name of Applicant"></spring:message></th>
							<th><spring:message code="rnl.propinfo.contactno"
									text="Contact No."></spring:message></th>
							<th><spring:message code="rnl.prop.book.purpose"
									text="Booking Purpose"></spring:message></th>
							<th><spring:message code="rnl.from.date" text="From Date"></spring:message></th>
							<th><spring:message code="rnl.to.date" text="To Date"></spring:message></th>
							<th>Period</th>
						</tr>
						<tr>
							<td>${propInfoDtoListDTO.bookingNo}</td>
							<td>${propInfoDtoListDTO.applicantName}</td>
							<td>${propInfoDtoListDTO.contactNo}</td>
							<td>${propInfoDtoListDTO.bookingPuprpose}</td>
							<td><fmt:formatDate pattern="dd-MM-yyyy"
									value="${propInfoDtoListDTO.fromDate}" /></td>
							<td><fmt:formatDate pattern="dd-MM-yyyy"
									value="${propInfoDtoListDTO.toDate}" /></td>
							<td>${propInfoDtoListDTO.dayPeriod}</td>

						</tr>

					</table>

				</div>

				<br>


				<div class="padding-top-10">
					<table class="table table-bordered table-striped">
						<tr>
							<th><spring:message text="Receipt No"></spring:message></th>
							<th><spring:message code="rnl.payment.applicant"
									text="Name of Applicant"></spring:message></th>
							<th><spring:message code="rnl.propinfo.contactno"
									text="Contact No."></spring:message></th>
							<th><spring:message code="rnl.from.date" text="From Date"></spring:message></th>
							<th><spring:message code="rnl.to.date" text="To Date"></spring:message></th>
							<th>Period</th>
							<th>Payment Mode</th>
							<th>Amount</th>
						</tr>
						<tr>
							<td>${propInfoDtoListDTO.receiptNo}</td>
							<td>${propInfoDtoListDTO.applicantName}</td>
							<td>${propInfoDtoListDTO.contactNo}</td>
							<td><fmt:formatDate pattern="dd-MM-yyyy"
									value="${propInfoDtoListDTO.fromDate}" /></td>
							<td><fmt:formatDate pattern="dd-MM-yyyy"
									value="${propInfoDtoListDTO.toDate}" /></td>
							<td>${propInfoDtoListDTO.dayPeriod}</td>
							<td>${propInfoDtoListDTO.paymentModedesc}</td>
							<td class="text-right">${propInfoDtoListDTO.amount}</td>
						</tr>
					</table>


				</div>
			</c:forEach>


			<br> <br>
			<%-- <div class="form-group">
				<label class="col-xs-2 control-label"><spring:message
						text="Cancellation Charges"></spring:message></label>

				<div class="col-sm-4">
					<input type="text" class="form-control text-right"
						value="${command.amountToPay}" maxlength="12" readonly="readonly" />

				</div>
			</div> --%>


			<%-- <div id="paymentDetails">
				<c:if test="${command.amountToPay > '0'}">

					<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
					<div class="form-group margin-top-10">
						<label class="col-sm-2 control-label"> <spring:message
								text="Amount to Pay" /></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"
								value="${command.amountToPay}" maxlength="12"
								readonly="readonly" /> <a
								class="fancybox fancybox.ajax text-small text-info"> <spring:message
									text="Amount to Pay" /> <i class="fa fa-question-circle "></i></a>
						</div>
					</div>
				</c:if>
			</div> --%>
			<br> <br>
			<c:if test="${command.amountToPay > '0'}">
				<div class="form-group">
					<label class="col-xs-2 control-label"><spring:message
							text="Cancellation Charges"></spring:message></label>

					<div class="col-sm-4">
						<input type="text" class="form-control text-right"
							value="${command.amountToPay}" maxlength="12" readonly="readonly" />

					</div>
				</div>
			</c:if>
			<div id="paymentDetails">
				<c:if test="${command.amountToPay > '0'}">

					<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />

					<div class="form-group margin-top-10">
						<label class="col-sm-2 control-label"> <spring:message
								text="Amount to Pay" /></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"
								value="${command.amountToPay}" maxlength="12"
								readonly="readonly" /> <a
								class="fancybox fancybox.ajax text-small text-info"> <spring:message
									text="Amount to Pay" /> <i class="fa fa-question-circle "></i></a>
						</div>
					</div>
				</c:if>
			</div>


			<br>

			<!-- checkbox for disclaimer -->
			<div class="form-group">
				<div class="col-sm-6">
					<label for="" class="radio-inline"> <b><spring:message
								text="Disclaimer"></spring:message></b> <br> <form:checkbox
							path="" value="" id="checkBoxIds" /> <spring:message
							text="Are you sure you want to cancel this booking" />
					</label>
				</div>
			</div>

			<div class="text-center">

				<button type="button" class="btn btn-blue-2" title="Submit"
					id="submitCancelMPB" onclick="saveMPBCancellation(this);">
					<i class="padding-right-5" aria-hidden="true"></i>
					<spring:message code="saveBtn" text="Submit" />
				</button>

				<apptags:backButton url="mpbCancellation.html"></apptags:backButton>
			</div>

		</div>
	</form:form>
</div>



