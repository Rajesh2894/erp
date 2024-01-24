<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/care/complaint-reopen-feedback.js"></script>
<script src="assets/libs/rating/star-rating.js"></script>
<link href="assets/libs/rating/star-rating.css" rel="stylesheet"
	type="text/css" />

<div class="">
	<div class="widget invoice" id="receipt">

		<div class="widget-content padding">
			<div class="table-responsive margin-top-10">
				<%-- <h3>
					<spring:message code="care.action.history" text="Action History" />
				</h3> --%>
				<table class="table table-bordered table-condensed">
					<tr>
						<th class="text-center"><spring:message
								code="care.action.datetime" text="Date & Time" /></th>
						<th class="text-center"><spring:message
								code="care.token.number" text="Complaint Number" /></th>
						<th class="text-center"><spring:message
								code="care.department" text="Department" /></th>
						<th class="text-center"><spring:message
								code="care.complaintSubType" text="Complaint Sub Type" /></th>
						<th class="text-center"><spring:message
								code="care.status" text="Status" /></th>
						<%-- <th class="text-center"><spring:message
								code="care.action.employee.name" text="Employee Name" /></th> --%>


						<th class="text-center"><spring:message
								code="care.action.remarks" text="Remarks" /></th>
						<%-- <th class="text-center"><spring:message code="care.action.attachments" text="Attachments"/></th> --%>
					</tr>

					<%-- <c:forEach items="${command.complaintAcknowledgementModel}"
						var="action" varStatus="status"> --%>
					<tr>
						<c:forEach var="current" varStatus="status"
							items="${command.complaintAcknowledgementModel.actions}">
							<c:if
								test="${(fn:length(command.complaintAcknowledgementModel.actions)-1) == (status.count-1)}">
								<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
										value="${current.dateOfAction}" /></td>
							</c:if>
						</c:forEach>

						<%-- <td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
								value="${command.complaintAcknowledgementModel.actions[0].dateOfAction}" /></td> --%>
						<td><c:out
								value="${command.complaintAcknowledgementModel.tokenNumber}"></c:out></td>
						<td><c:out
								value="${command.complaintAcknowledgementModel.department}"></c:out></td>
						<td><c:out
								value="${command.complaintAcknowledgementModel.complaintSubType}"></c:out></td>
						<c:forEach var="current" varStatus="status"
							items="${command.complaintAcknowledgementModel.actions}">
							<c:if
								test="${(fn:length(command.complaintAcknowledgementModel.actions)-1) == (status.count-1)}">
								<td><c:out value="${current.decision}"></c:out></td>
							</c:if>
						</c:forEach>
						<%-- <td><c:out
								value="${command.complaintAcknowledgementModel.actions[0].decision}"></c:out></td> --%>
						<%-- <td><c:out value="${command.complaintAcknowledgementModel.empName}"></c:out></td> --%>

						<%-- <td><c:if test="${not empty command.complaintAcknowledgementModel.empGroupDescEng}">
									<c:out value="${command.complaintAcknowledgementModel.empGroupDescEng}"></c:out>
								</c:if> <c:if test="${empty command.complaintAcknowledgementModel.empGroupDescEng}">
									<spring:message code="care.Citizen" text="Citizen" />
								</c:if></td> --%>
						<c:forEach var="current" varStatus="status"
							items="${command.complaintAcknowledgementModel.actions}">
							<c:if
								test="${(fn:length(command.complaintAcknowledgementModel.actions)-1) == (status.count-1)}">
								<td><c:out value="${current.comments}"></c:out></td>
							</c:if>
						</c:forEach>
						<%-- <td><c:out
								value="${command.complaintAcknowledgementModel.actions[0].comments}"></c:out></td> --%>
						<%--  <td>
                   <ul>
                   <c:forEach items="${action.attachements}" var="lookUp" varStatus="status">
                   <li><apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${lookUp.defaultVal}" actionUrl="grievance.html?Download"></apptags:filedownload></li>
                   </c:forEach> 
                   </ul>
                   </td>--%>
					</tr>
					<%-- </c:forEach> --%>
				</table>
			</div>
			<%-- 
			<div id="btnDiv1" class="text-center col-sm-12  margin-top-10">
				<button id="btnBack" type="button"
					class="btn btn-danger hidden-print"
					onclick="window.location.href='CitizenHome.html'">
					<spring:message code="care.receipt.back" text="Back" />
				</button>
			</div> --%>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {

		$('#reopenForm').hide();
		$('#feedbackForm').hide();
		$('#btnDiv2').hide();
		$('#btnDiv3').hide();
		var star = $("#hiddenFeedbackRate").val();
		if (star != '' || star != null) {
			$('#rating-input').val(star);
			$('#hiddenFeedbackRateUpdtaed').val(star);
		}

		$('#btnReopen').click(function() {
			$('#reopenForm').slideDown("slow");
			$('#btnDiv1').hide();
			$('#btnDiv2').show();
		});

		$('#btnFeedBack').click(function() {
			$('#feedbackForm').slideDown("slow");
			$('#btnDiv1').hide();
			$('#btnDiv3').show();
		});

		$('.btnCancel').click(function() {
			$('#reopenForm').slideUp("slow");
			$('#feedbackForm').slideUp("slow");
			$('#btnDiv1').show();
			$('#btnDiv2').hide();
			$('#btnDiv3').hide();
			$('#errorDiv,#errorDivId').hide();
			$("#form_grievanceReopen").trigger("reset");
		});

	});

	jQuery(document).ready(function() {
		$('#rating-input').rating({
			min : 0,
			max : 5,
			step : 1,
			size : 'sm',
			showClear : false
		});
		$('#rating-input').on('rating.change', function() {
			$('#hiddenFeedbackRateUpdtaed').val($('#rating-input').val());
		});
	});
</script>
</body>
</html>