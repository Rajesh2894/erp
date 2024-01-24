<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/accountLiabilityBooking.js"
	type="text/javascript"></script>

<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.liability" text="Liability" />
				<strong> <spring:message code="account.liability.bookentry"
						text="Booking Entry" /></strong>
			</h2>
		<apptags:helpDoc url="AccountLiabilityBooking.html" helpDocRefURL="AccountLiabilityBooking.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding" id="lbBookingDiv">
			<form:form method="POST" action="AccountLiabilityBooking.html"
				class="form-horizontal" name="liabilityBooking"
				id="liabilityBooking" modelAttribute="liabilityBookingBean">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="account.liability.booking.tender.no" text="Tender No." /></label>
					<div class="col-sm-4">
						<form:select path="trTenderId"
							cssClass="form-control chosen-select-no-results" id="trTenderId">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${tenderNoMap}" varStatus="status"
								var="tenderMap">
								<form:option value="${tenderMap.key}">${tenderMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success" id="search">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
				</div>
			</form:form>
		</div>


	</div>
</div>
