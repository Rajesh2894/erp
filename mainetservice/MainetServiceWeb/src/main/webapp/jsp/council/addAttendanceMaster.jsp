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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/council/councilAttendance.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.button.addAttendance"
					text="Member Attendance" />
			</h2>

		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CouncilAttendanceMaster.html"
				cssClass="form-horizontal" id="memberCommitteeMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="couMeetingMasterDto.meetingId" />
				<form:hidden path="couMeetingMasterDto.memberIdByCommitteeType"
					id="memberIdByCommitteeType" />

				<div class="panel-group accordion-toggle">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="" href="#child-level"><spring:message
								code="council.button.addAttendance" /></a>
					</h4>

					<div id="child-level" class="collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="control-label col-sm-2 " for="MeetingType"><spring:message
										code="council.meetingType" text="Meeting Type"></spring:message></label>
								<c:set var="baseLookupCode" value="MPT" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="couMeetingMasterDto.meetingTypeId"
									cssClass="form-control required-control" isMandatory="true" disabled="${command.disableSelect}"
									selectOptionLabelCode="selectdropdown"
									hasId="true" changeHandler="getMeetingNos()" />

								<%-- <apptags:input labelCode="Meeting Number"
									path="couMeetingMasterDto.meetingNo" cssClass="form-control"
									isReadonly="true"></apptags:input> --%>
									<label class="col-sm-2 control-label "><spring:message
											code="council.meeting.meetingNo" text="Meeting Number" /></label>
									<div class="col-sm-4">
										<form:select path="couMeetingMasterDto.meetingId" disabled="${command.disableSelect}"
											class="form-control chosen-select-no-results" id="meetingId"  
											onchange="getMeetingMembers(this);">
											<form:option value="">
												<spring:message code='council.management.select' />
											</form:option>
											<c:forEach items="${command.councilMeetingMasterDtoList}" var="meetingData">
												<form:option value="${meetingData.meetingId}">${meetingData.meetingNo}</form:option>
											</c:forEach>
										</form:select>
									</div>
							</div>
							<!-- Document code set -->
				<div class="form-group">
					<c:set var="count" value="${count + 1}" scope="page" />

					<label for="" class="col-sm-2 control-label"> <spring:message
							code="council.attendance.upload" text="Attendance List" /></label>
					<c:set var="count" value="0" scope="page"></c:set>
					<div class="col-sm-4">
						<c:if
							test="${command.saveMode eq 'ADD'  || command.saveMode eq 'EDIT' }">
							<apptags:formField fieldType="7"
								fieldPath="attachments[${count}].uploadedDocumentPath"
								currentCount="${count}" folderName="${count}"
								fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
								isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
							</apptags:formField>
							<div class="col-sm-12">
								<small class="text-blue-2 "> <spring:message
										code="council.validator.fileUploadNote"
										text="(Upload File upto 2MB and only pdf,doc,docx,jpeg,jpg,png,gif)" />
								</small>
							</div>
						</c:if>

						<c:if
							test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
							<input type="hidden" name="deleteFileId"
								value="${command.attachDocsList[0].attId}">
							<input type="hidden" name="downloadLink"
								value="${command.attachDocsList[0]}">
							<apptags:filedownload
								filename="${command.attachDocsList[0].attFname}"
								filePath="${command.attachDocsList[0].attPath}"
								actionUrl="CouncilAttendanceMaster.html?Download"></apptags:filedownload>
						</c:if>

					</div>
				</div>

							<div class="table-responsive">
								<table class="table table-striped table-bordered"
									id="membersDataTable">
									<thead>
										<tr>
											<th  class="text-center"><spring:message
											code="council.member.srno" text="Sr No" /></th>
											<%-- <th class="text-center"><spring:message
													code="council.attendance.markAttendance"
													text="Mark Attendance" /></th> --%>
											<th  class="text-center"><spring:message
													code="council.commitee.memberName" text="Member Name" /></th>
											<th  class="text-center"><spring:message
													code="council.member.electionward" text="Election Ward/Zone" /></th>
											<th  class="text-center"><spring:message
													code="council.member.type" text="Member Type" /></th>
											<%-- <th class="text-center"><spring:message
													code="council.attendance.qualification"
													text="Qualification" /></th>
											<th  class="text-center"><spring:message
													code="council.member.partyaffiliation"
													text="Party Affiliation" /></th> --%>			
											<th width="9%" align="center" height="20px">
											<c:choose>
													<c:when test="${command.saveMode eq 'VIEW' }">
														<spring:message code="council.select.all" text="Select All" /><br/>
														<input type="checkbox"  style="margin-left:-10px;position:relative;" class="checkbox-inline" id="selectall" disabled />
													</c:when>
													<c:otherwise>
														<spring:message code="council.select.all" text="Select All" /><br/>
														<input type="checkbox"  style="margin-left:-10px;position:relative;" class="checkbox-inline" id="selectall" />
													</c:otherwise>
												</c:choose>
										   </th></tr>
									</thead>
									<tbody>
										<c:forEach items="${command.memberList}" var="member"
											varStatus="status">
											<tr>
												<td class="text-center">${status.count}</td>
												<td class="text-center">${member.memberName}</td>
												<td class="text-center">${member.elecWardDesc}</td>
												<td class="text-center">${member.couMemberTypeDesc}</td>
												<c:choose>
													<c:when test="${command.saveMode eq 'VIEW' }">
														<c:choose>
															<c:when test="${member.attendanceStatus == 1}">
																<th width="10%" align="center"><input type="checkbox" checked
																	class="case" name="case" value='${member.memberId}' disabled /></td>
															</c:when>
															<c:otherwise>
																<th width="10%" align="center"><input type="checkbox"
																	class="case" name="case" value='${member.memberId}' disabled /></td>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${member.attendanceStatus == 1}">
																<td width="10%" align="center">
																<input aria-label="select record ${status.count}" type="checkbox" checked
																	class="case" style="margin-top:5px;margin-left:-10px" name="case" value='${member.memberId}' />
																</td>
															</c:when>	
															<c:otherwise>
																<td width="10%" align="center">
																<input type="checkbox" aria-label="select record ${status.count}"
																	class="case" style="margin-top:5px;margin-left:-10px" name="case" value='${member.memberId}' />
																	</td>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
												
												<%-- <td class="text-center">${member.qualificationDesc}</td>
												<td class="text-center">${member.partyAFFDesc}</td> --%>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

							<!-- Start button -->
							<div class="text-center">
								<c:if
									test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
									<button type="button" class="btn btn-success" title="Submit"
										onClick="saveAttendence(this,'${command.saveMode}');">
										<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
										<spring:message code="council.button.submit" text="Submit" />
									</button>
									</c:if>
									
                                    <c:if test="${command.saveMode ne 'EDIT' && command.saveMode ne 'VIEW'}">
									<button type="button" onclick="resetAttendanceForm(this);"
										class="btn btn-warning" title="Reset">
										<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
										<spring:message code="council.button.reset" text="Reset" />
									</button>
								    </c:if>
								
								
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel" style=""
									onclick="backAttendanceMasterForm();" id="button-Cancel">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="council.button.back" text="Back" />
								</button>	
							</div>
							<!-- End button -->
						</div>
					</div>
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
$("#selectall").prop("checked", "checked");
if(mode == 'ADD'){
	   $(".case").prop("checked", "checked");
}
</script>
