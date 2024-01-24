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
<link href="assets/libs/rating/starability-slot.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>



<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Citizen Rating And Feedback" />
			</h2>

		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="EstateCitizenRating.html"
				cssClass="form-horizontal" id="estateCitizenRating"
				name="estateCitizenRating">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<div class="form-group">
					<label for="citizenId"
						class="control-label col-sm-2 required-control"><spring:message
							code='rnl.rating.citizenName' text="Citizen Name" /></label>
					<div class="col-sm-4">
						<form:select id="citizenId"
							cssClass="form-control chosen-select-no-results"
							class="form-control required-control"
							path="citizenRatingDTO.estateBooking.id">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.estateBookings}" var="citizen">
								<form:option value="${citizen.id}">${citizen.bookingNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<apptags:textArea labelCode="rnl.rating.feedback"
						path="citizenRatingDTO.feedback" isMandatory="true"
						maxlegnth="400"></apptags:textArea>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="rnl.rating.selectRating" text="Select Ratings" /></label>
					<div class="col-sm-4">
						<c:set var="c" value="1" scope="page" />
						<form:hidden path="citizenRatingDTO.ratingId" id="ratingId" />
						<fieldset class="starability-slot margin-top-0 margin-bottom-0">
							<input type="radio" id="no-rate" class="input-no-rate"
								name="rating" value="0" checked aria-label="No rating." />
							<c:forEach items="${command.lookupList}" var="lookup">
								<input type="radio" id="rate${c}" name="rating"
									value="${lookup.lookUpId}" />
								<label for="rate${c}"></label>
								<c:set var="c" value="${c + 1}" scope="page" />
							</c:forEach>
						</fieldset>
					</div>
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Submit"
						id="submitBT" onclick="saveCitizenRatingAndFeedback(this)">
						<i class="padding-right-5" aria-hidden="true"></i>
						<spring:message code="saveBtn" text="Submit" />
					</button>

					<button type="button"
						onclick="javascript:openRelatedForm('EstateCitizenRating.html');"
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

<script>
	//submit citizen rating and feedback
	function saveCitizenRatingAndFeedback(object) {
		let errorList = [];
		errorList = validateRatingAndFeedBack(errorList);
		if (errorList.length > 0) {
			$("#errorDiv").show();
			//showErr(errorList);
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();
			var formName = findClosestElementId(object, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			return saveOrUpdateForm(object, '', 'AdminHome.html', 'saveform');
		}
	}

	//validation method for cancelBookingDetails
	function validateRatingAndFeedBack(errorList) {
		//check mandatory field
		let citizenId = $('#citizenId').val();
		let feedback = $('#feedback').val();
		let ratingId = $("input:radio[name='rating']").filter(":checked").val();
		$('#ratingId').val(ratingId);//set ratingId to hidden value because it not binding to form
		if (citizenId == '' || citizenId == 0) {
			errorList
					.push(getLocalMessage("rnl.rating.validation.citizenName"));
		}
		if (feedback == '') {
			errorList.push(getLocalMessage("rnl.rating.validation.feedback"));
		}
		if (ratingId == '' || ratingId == 0) {
			errorList.push(getLocalMessage("rnl.rating.validation.rating"));
		}
		return errorList;
	}
</script>
