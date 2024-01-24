<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/council/councilMeeting.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.meeting.master.title"
					text="Meeting Invitation" />
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
			<apptags:helpDoc url="CouncilMeetingMaster.html" />
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="coucil.fiels.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="CouncilMeetingMaster.html"
				cssClass="form-horizontal" id="meetingMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<input type="hidden" name="committeeTypeId"
					value="${command.couAgendaMasterDto.committeeTypeId}">
				<form:hidden path="couMeetingMasterDto.agendaId" id="agendaId" />
				<form:hidden path="couMeetingMasterDto.meetingId" />
				<form:hidden path="couMeetingMasterDto.memberIdByCommitteeType"
					id="memberIdByCommitteeType" />
				<form:hidden path="couMeetingMasterDto.meetingStatus" value="A" />
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="couMeetingMasterDto.prevMeetingPlace" id="prevMeetingPlace" />
				<form:hidden path="couMeetingMasterDto.prevMessage" id="prevMessage" />
				<form:hidden path="couMeetingMasterDto.prevMeetingDateDesc" id="prevMeetingDateDesc" />

				<h4>
					<spring:message code="council.agenda.search.title"
						text="Search Agenda" />
				</h4>
				<div class="form-group">
				
					<label class="control-label col-sm-2 " for="committeeType"><spring:message
							code="council.committeeType" text="Committe Type"></spring:message><i
													class="text-red-1">*</i></label>
					<c:set var="baseLookupCode" value="CPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="couAgendaMasterDto.committeeTypeId"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true"
						disabled="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}" />
						
				   <div class="form-group" align="justify"><spring:message code="council.to.or"
						text="OR" /></div> 
											
					<apptags:input labelCode="council.agenda.agendaNo" isMandatory="true"
						path="couAgendaMasterDto.agendaNo" cssClass="form-control"
						isReadonly="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}"></apptags:input>
						
				</div>

				<!-- date picker input set -->
				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="council.from.date"
						datePath="couAgendaMasterDto.agendaFromDate"
						readonly="${command.saveMode eq 'VIEW'}"
						isDisabled="${command.saveMode eq 'VIEW'|| command.saveMode eq 'EDIT'}"></apptags:date>
					<apptags:date fieldclass="datepicker" labelCode="council.to.date"
						datePath="couAgendaMasterDto.agendaToDate"
						readonly="${command.saveMode eq 'VIEW'}"
						isDisabled="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}"></apptags:date>
				</div>

				<!-- Start button -->
				
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'ADD'}">
						<button type="button" class="btn btn-blue-2" title="Search"
							id="searchCouncilAgenda">
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.search" text="Search" />
						</button>
					</c:if>
			     <c:if test="${command.saveMode eq 'ADD'}">
			 		<button type="button"
						onclick="emptyForm(this);"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.reset" text="Reset" />
					</button>
					</c:if>
				</div>

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="agendaDatatables">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="council.member.srno" text="Sr.No" /></th>
								<%-- <th class="text-center"><spring:message
										code="council.agenda.agendaNo" text="Agenda number" /></th> --%>
								<th class="text-center"><spring:message
										code="council.agenda.committeeType" text="Committee Type" /></th>
								<th class="text-center"><spring:message
										code="council.agenda.date" text="Date" /></th>
								<th class="text-center"><spring:message
										code="council.member.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.couAgendaMasterDtoList}" var="agenda"
								varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${agenda.committeeType}</td>
									<td class="text-center" id="agenda-date">${agenda.agenDate}</td>
									<c:choose>
									  <c:when test="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}">
									    <td class="text-center">
											<button type="button" class="btn btn-blue-2"
												name="button-plus" id="button-plus" disabled="disabled"
												onclick="meetingDetails('${agenda.agendaId}','${agenda.committeeTypeId}','${agenda.agenDate}')"
												title="Create Meeting">
												<i class="fa fa-plus-circle padding-right-5"
													aria-hidden="true"></i>
													<spring:message code="council.button.createMeeting" text="Create Meeting" />
											</button>
											
										</td>
									  </c:when>
									  <c:otherwise>
									    <td class="text-center">
											<button type="button" class="btn btn-blue-2"
												name="button-plus" id="button-plus"
												onclick="meetingDetails('${agenda.agendaId}','${agenda.committeeTypeId}','${agenda.agenDate}')"
												title="Create Meeting">
												<i class="fa fa-plus-circle padding-right-5"
													aria-hidden="true"></i>
												<spring:message code="council.button.createMeeting" text="Create Meeting" />
											</button>
										</td>
									  </c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div id="meetingDetails">
					<h4>
						<spring:message code="council.meeting.details.title"
							text="Meeting Details" />
					</h4>
					<div class="form-group">
						<!-- SET MEETING TYPE PREFIX -->
						<label class="control-label col-sm-2 " for="MeetingType"><spring:message
								code="council.meetingType" text="Meeting Type"></spring:message></label>
						<c:set var="baseLookupCode" value="MPT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="couMeetingMasterDto.meetingTypeId"
							cssClass="form-control required-control" isMandatory="true"
							selectOptionLabelCode="selectdropdown" hasId="true"
							disabled="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}" />
						<%-- <apptags:date fieldclass="datepicker" labelCode="Meeting Date"
									datePath="couMeetingMasterDto.meetingDate"
									cssClass="mandColorClass" isMandatory="true"
									isDisabled="${command.saveMode eq 'V'}"></apptags:date> --%>

						<label class="col-sm-2 control-label required-control"><spring:message
								code="council.meeting.place" text="Meeting Place"/></label>
						<div class="col-sm-4 ">
							<form:input labelCode="Meeting Place" id="meetingPlace"
								path="couMeetingMasterDto.meetingPlace" cssClass="form-control"
								isDisabled="${command.saveMode eq 'VIEW'}" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="Meeting Time">
							<spring:message code="council.meeting.meetingDateTime" /> <span><i
								class="text-red-1">*</i></span>
						</label>
						<div class="col-sm-4 ">
							<div class="input-group">
								<form:input path="couMeetingMasterDto.meetingDateDesc"
									id="meetingDateTime" class="form-control meetingDateTimePicker"
									disabled="${command.saveMode eq 'VIEW'}" onclick="getReasonforChange(this)" />
								<label class="input-group-addon" for="meetingDateTime"><i
									class="fa fa-calendar"></i></span><input type="hidden"
									id="meetingDateTime"></label>
							</div>
									
						</div>

						 <!-- User Story #72221 -->
						<c:if test="${command.saveMode ne 'ADD'}">
						<label class="control-label col-sm-2 required-control"><spring:message
									code='council.meeting.reason' text='Reason' /></label>
						<div class="col-sm-4 ">														
								<form:textarea path="couMeetingMasterDto.reason" id="reason" class="form-control" disabled="${command.saveMode eq 'VIEW'}" />
						</div></c:if>

						<%-- <div class="col-sm-4 ">
							<form:select path="couMeetingMasterDto.meetingPlace"
								id="meetingPlace"
								cssClass="form-control chosen-select-no-results"
								class="form-control mandColorClass" data-rule-required="true"
								disabled="${command.saveMode eq 'VIEW'}">
								<form:option value="0">Select</form:option>
								<c:forEach items="${command.locationList}" var="location">
									<form:option value="${location.locId}">${location.locNameEng}</form:option>
								</c:forEach>
							</form:select>
						</div> --%>
					</div>
					
	                  <!-- User Story #72221 -->
					<c:if test="${command.saveMode ne 'ADD'}">
					<h4>
						<spring:message code="council.meeting.history.title"
							text="Meeting History" />
					</h4>					
						<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="meetingHistorytable">
							
							<thead>
								<tr>
									<th width="3%" align="center"><spring:message
											code="council.member.srno" text="Sr.No" /></th>
									<th class="text-center"><spring:message
											code="council.meetingType" text="Meeting Type" /></th>
									<th class="text-center"><spring:message
											code="council.meeting.meetingDateTime" text="Meeting Date Time" /></th>
									<th class="text-center"><spring:message
											code="council.meeting.place" text="Meeting Place" /></th>
									<th class="text-center"><spring:message
											code="council.meeting.messageInvitation" text="Message" /></th>
									<th class="text-center"><spring:message
											code="council.meeting.history.reason" text="Reason" /></th>
								</tr>
							</thead>
							<tbody>					
							<c:forEach items="${command.councilMeetingMasterDtoList}"
									var="meetingDet" varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${meetingDet.meetingTypeName}</td>
										<td class="text-center">${meetingDet.meetingDateDesc}</td>
										<td class="text-center">${meetingDet.meetingPlace}</td>
										<td class="text-center">${meetingDet.meetingInvitationMsg}</td>
										<td class="text-center">${meetingDet.reason}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							</div>
					</c:if>

					<h4>
						<spring:message code="council.meeting.agenda.title"
							text="Meeting Agenda" />
					</h4>
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="agendaProposalDatatables">

							<thead>
								<tr>
									<th width="3%" align="center"><spring:message
											code="council.member.srno" text="Sr.No" /></th>
									<th class="text-center"><spring:message
											code="council.proposal.details" text="Details of Proposal" /></th>
									<th class="text-center"><spring:message
											code="council.agenda.agendaNo" text="Agenda Number" /></th>
									<th class="text-center"><spring:message
											code="council.proposal.no" text="Proposal Number" /></th>
									<th class="text-center"><spring:message
											code="council.member.department" text="Proposal Number" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.agendaProposalDtoList}"
									var="proposal" varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${proposal.proposalDetails}</td>
										<td class="text-center">${proposal.agendaNo}</td>
										<td class="text-center">${proposal.proposalNo}</td>
										<td class="text-center">${proposal.proposalDeptName}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<h4>
						<spring:message code="council.meeting.invitations.title"
							text="Invitations" />
					</h4>
					
					<div class="form-group">

						<label class="control-label col-sm-2 required-control"><spring:message
								code='council.meeting.messageInvitation' text='Message' /></label>
						<div class="col-sm-10">
							<form:textarea path="couMeetingMasterDto.meetingInvitationMsg"
								id="meetingInvitationMsg" class="form-control alfaNumricSpecial"
								onkeyup="countChar(this,160,'meetingInvitationMsgCount')" onfocus="countChar(this,160,'meetingInvitationMsgCount')"
								maxlength="160" disabled="${command.saveMode eq 'VIEW'}" />
							<div class="pull-right">
								<spring:message code="charcter.remain" text="characters remaining " /><span id="meetingInvitationMsgCount">160</span>
							</div>
						</div>

					</div>
					

					<h4>
						<spring:message code="council.meeting.attenders.title"
							text="Meeting Attenders" />
					</h4>
					<table class="table table-striped table-bordered"
						id="membersDataTable">
						<thead>
							<tr>
								<%-- <c:choose>
									<c:when test="${command.saveMode eq 'VIEW' }">
										<th><input type="checkbox" id="selectall" disabled /></th>
									</c:when>
									<c:otherwise>
										<th><input type="checkbox" id="selectall" /></th>
									</c:otherwise>
								</c:choose> --%>
								
								<th class="text-center"><spring:message
										code="council.member.srno" text="Sr No" /></th>
										
								<th class="text-center"><spring:message
										code="council.commitee.memberName" text="Member Name" /></th>
								<th class="text-center"><spring:message
										code="council.member.type" text="Member Type" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.memberList}" var="member"
								varStatus="status">
								<tr>
									<%-- <c:choose>
										<c:when test="${command.saveMode eq 'VIEW' }">
											<td align="center"><input type="checkbox" class="case"
												name="case" value='${member.memberId}' disabled /></td>
										</c:when>
										<c:otherwise>
											<td align="center"><input type="checkbox" class="case"
												name="case" value='${member.memberId}' /></td>
										</c:otherwise>
									</c:choose> --%>
									<input id="memberId${status.count-1}"  type="hidden" value="${member.memberId}">
									<td class="text-center">${status.count}</td>
									<td class="text-center">${member.memberName}</td>
									<td class="text-center">${member.couMemberTypeDesc}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="text-center">
					<c:if
						test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
						<button type="button" id="createMeetingBtId"
							onClick="createMeeting(this);" class="btn btn-blue-2"
							title="Create">
							<i class="fa fa-pencil padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.createMeeting" text="Create Meeting" />
						</button>
					</c:if>
					


					<button type="button" class="btn btn-danger" title="Back"
						onclick="backMeetingMasterForm();">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i>
							<spring:message code="council.button.back" text="Back" />
					</button>
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

var mode = '${command.saveMode}';
if(mode == 'EDIT' || mode == 'VIEW'){
	$('#meetingDetails').show();
	var memberIdByCommitteeType = '${command.couMeetingMasterDto.memberIdByCommitteeType}';
	var arrayValues = memberIdByCommitteeType.split(',');
	
		$.each(arrayValues, function(i, val){
		   $("input[value='" + val + "']").prop('checked', true);
		});
		if($(".case").length == $(".case:checked").length) {
			$("#selectall").prop("checked", "checked");
		}
		if(mode == 'VIEW'){
			$("#membersDataTable").prop("disabled", false);
		}
}

</script>
