<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->

<script src="assets/libs/ckeditor/ckeditor.js"></script>
<script src="assets/libs/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="js/council/councilMOM.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.mom.summary.title" text="MOM Summary" />
			</h2>
			<apptags:helpDoc url="CouncilMOM.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CouncilMOM.html" cssClass="form-horizontal"
				id="CouncilMOM" name="CouncilMOM">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->


				<div class="form-group">
					<label class="control-label col-sm-2 " for="MeetingType"><spring:message
							code="council.meetingType" text="Meeting Type"></spring:message></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="couMeetingMasterDto.meetingTypeId"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

					<apptags:input labelCode="council.meeting.meetingNo"
						path="couMeetingMasterDto.meetingNo" cssClass="form-control"></apptags:input>
				</div>

				<!-- date picker input set -->
				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="council.from.date"
						datePath="couMeetingMasterDto.fromDate"></apptags:date>
					<apptags:date fieldclass="datepicker" labelCode="council.to.date"
						datePath="couMeetingMasterDto.toDate"></apptags:date>
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchCouncilMOM">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='CouncilMOM.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary"
						onclick="addMOM('CouncilMOM.html','addCouncilMOM');"
						title="Create MOM">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.add" text="Add" />
					</button>

				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="momSummaryDatatables">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="council.srno" text="Sr.No" /></th>
								<th class="text-center"><spring:message
										code="council.meeting.meetingType" text="Meeting Type" /></th>
								<th class="text-center"><spring:message
										code="council.meeting.meetingNo" text="Meeting Number" /></th>
								<th class="text-center"><spring:message
										code="council.meeting.date" text="Meeting Date" /></th>
								<th class="text-center"><spring:message
										code="council.meeting.time" text="Meeting Time" /></th>
								<th class="text-center"><spring:message
										code="council.meeting.place" text="Meeting Place" /></th>
								<th class="text-center"><spring:message
										code="council.member.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.councilMeetingMasterDtoList}"
								var="meeting" varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${meeting.meetingTypeName}</td>
									<td class="text-center">${meeting.meetingNo}</td>
									<td class="text-center">${meeting.meetingDateDesc}</td>
									<td class="text-center">${meeting.meetingTime}</td>
									<td class="text-center">${meeting.meetingPlace}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2"
											name="button-plus" id="button-plus"
											onclick="getActionForDefination(${meeting.meetingId},'VIEW')"
											title="<spring:message code="council.button.view" text="view"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button" class="btn btn-danger"
											onclick="getActionForDefination(${meeting.meetingId},'EDIT')"
											title="<spring:message code="council.button.edit" text="edit"></spring:message>">
											<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										</button>
									</td>
									<!-- set action button based on condition -->
									<%-- <c:choose>
									  <c:when test="${meeting.actionBT}">
									    <td class="text-center">
											<button type="button" class="btn btn-blue-2"
												name="button-plus" id="button-plus"
												onclick="getActionForDefination(${meeting.meetingId},'VIEW')"
												title="<spring:message code="council.button.view" text="view"></spring:message>"
												<i class="fa fa-eye" aria-hidden="true"></i>
											</button>
		
											<button type="button" class="btn btn-danger"
												onclick="getActionForDefination(${meeting.meetingId},'EDIT')"
												title="<spring:message code="council.button.edit" text="edit"></spring:message>"
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button>
										</td>
									  </c:when>
									 <c:otherwise>
									    <td></td>
									  </c:otherwise>
									</c:choose> --%>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->

<script type="text/javascript">
/*this is only for hide createMOM BT if in zero position already mom created so hide BT  */
var actionBT = false;
actionBT = '${command.councilMeetingMasterDtoList[0].actionBT}';
if(actionBT == 'true'){
	$('#createMOMBT').hide();	
}

</script>
