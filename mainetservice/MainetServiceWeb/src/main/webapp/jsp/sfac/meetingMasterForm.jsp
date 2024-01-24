<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/meetingMasterForm.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="meeting.master.form.title"
					text="Meeting Master Form" />
			</h2>
			<apptags:helpDoc url="MeetingMasterForm.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="MeetingMasterForm" action="MeetingMasterForm.html"
				method="post" class="form-horizontal">
				<form:hidden path="removeMomDetIds" id="removeMomDetIds" />
				<form:hidden path="removeMemberIds" id="removeMemberIds" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<h4>
					<spring:message code="meeting.details.title" text="Meeting Details" />
				</h4>
				<div class="form-group">

					<label class="control-label col-sm-2  required-control"
						for="meetingType"><spring:message code="sfac.meeting.type"
							text="Meeting Type"></spring:message></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="masterDto.meetingTypeId" changeHandler="getCommitteeMemDet();"
						disabled="${command.saveMode eq 'V' ? true : false }"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />


					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.meeting.place" text="Meeting Place" /></label>
					<div class="col-sm-4 ">
						<form:input labelCode="Meeting Place" id="meetingPlace"
							disabled="${command.saveMode eq 'V' ? true : false }"
							path="masterDto.meetingPlace" cssClass="form-control" />
					</div>

				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.meeting.dateAndTime" text="Meeting Date And Time" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="masterDto.meetingDateDesc" id="meetingDateTime"
								class="form-control meetingDateTimePicker"
								disabled="${command.saveMode eq 'V' ? true : false }" />
							<label class="input-group-addon" for="meetingDateTime"><i
								class="fa fa-calendar"></i></span><input type="hidden"
								id="meetingDateTime"></label>
						</div>
					</div>

					<label class="control-label col-sm-2 required-control"><spring:message
							code='sfac.meeting.remark' text='Meeting Title' /></label>
					<div class="col-sm-4">
						<form:textarea path="masterDto.remark" id="remark"
							class="form-control" maxlength="250"
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>
				</div>

				<div class="form-group">

					<label class="control-label col-sm-2  required-control"
						for="meetingType"><spring:message
							code="sfac.convenerofTheMeeting" text="Convener of The Meeting"></spring:message></label>
					<c:set var="baseLookupCode" value="CVM" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="masterDto.convenerofMeeting"
						disabled="${command.saveMode eq 'V' ? true : false }"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

				</div>

			<%-- 	<h4>
					<spring:message code="meeting.agenda.title" text="Meeting Agenda" />
				</h4>
 --%>


				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code='sfac.invitation' text='Invitation to Convening the meeting' /></label>
					<div class="col-sm-10">
						<form:textarea path="masterDto.meetingInvitationMsg"
							id="meetingInvitationMsg" class="form-control alfaNumricSpecial"
							onkeyup="countChar(this,160,'meetingInvitationMsgCount')"
							onfocus="countChar(this,160,'meetingInvitationMsgCount')"
							maxlength="160" readonly="${command.saveMode eq 'V'}"/>
						<div class="pull-right">
							<spring:message code="charcter.remain"
								text="characters remaining " />
							<span id="meetingInvitationMsgCount">160</span>
						</div>
					</div>
				</div>

			<%-- 	<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code='sfac.tableAgenda' text='Table of Agenda' /></label>
					<div class="col-sm-10">
						<form:textarea path="masterDto.tableAgenda" id="tableAgenda"
							class="form-control alfaNumricSpecial" readonly="${command.saveMode eq 'V'}"
							onkeyup="countChar(this,160,'tableAgendaCount')" 
							onfocus="countChar(this,160,'tableAgendaCount')" maxlength="160"/>
						<div class="pull-right">
							<spring:message code="charcter.remain"
								text="characters remaining " />
							<span id="tableAgendaCount">160</span>
						</div>
					</div>

				</div> --%>



				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code='sfac.suggestions' text='Submit Suggestions/Recommendations' /></label>
					<div class="col-sm-10">
						<form:textarea path="masterDto.suggestions" id="suggestions"
							class="form-control alfaNumricSpecial" readonly="${command.saveMode eq 'V'}"
							onkeyup="countChar(this,500,'suggestionsCount')"
							onfocus="countChar(this,500,'suggestionsCount')" maxlength="500"/>
						<div class="pull-right">
							<spring:message code="charcter.remain"
								text="characters remaining " />
							<span id="suggestionsCount">500</span>
						</div>
					</div>

				</div>


				<h4>
					<spring:message code="sfac.agenda.title" text="Agenda Details" />
				</h4>
				<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-striped table-bordered" id="agendaDataTable">
						<thead>
							<tr>
								<th class="text-center"><spring:message code="sfac.srno"
										text="Sr No" /></th>
								<th class="text-center"><spring:message
										code="sfac.agenda.points" text="Agenda Points" /></th>
								<c:if test="${command.saveMode ne 'V' ? true : false }">
								<th class="text-center"><spring:message code="sfac.action"
										text="Action Button" /></th></c:if>
							</tr>
						</thead>

						<tbody>
							<c:choose>
								<c:when test="${fn:length(command.masterDto.meetinAgendaDto)>0 }">
									<c:forEach var="dto" items="${command.masterDto.meetinAgendaDto}"
										varStatus="status">
										<tr class="agendaDetails">
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="srNo${d}"
													value="${d+1}" disabled="true" /> <form:hidden
													path="masterDto.meetinAgendaDto[${d}].agendaId" id="agendaId${d}"
													class="agendaId" /></td>
											<td><form:input
													path="masterDto.meetinAgendaDto[${d}].agendaDesc"
													id="agendaDesc${d}"
													disabled="${command.saveMode eq 'V' ? true : false }"
													class="form-control hasNameClass" maxlength="250" /></td>

											
											<c:if test="${command.saveMode ne 'V' }">
											<td class="text-center"><a
												class="btn btn-blue-2 btn-sm addAgendaDetails"
												title='<spring:message code="sfac.fpo.add" text="Add" />'
												onclick="addAgendaDetails(this);"> <i
													class="fa fa-plus-circle"></i></a> <a
												class='btn btn-danger btn-sm deleteAgendaDetails'
												title='<spring:message code="sfac.fpo.delete" text="Delete" />'
												onclick="deleteAgendaDetails(this);"> <i
													class="fa fa-trash"></i>
											</a></td></c:if>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="agendaDetails">
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass" id="srNo${d}"
												value="${d+1}" disabled="true" /></td>
										<td><form:input
												path="masterDto.meetinAgendaDto[${d}].agendaDesc"
												id="agendaDesc${d}"
												disabled="${command.saveMode eq 'V' ? true : false }"
												class="form-control hasNameClass" maxlength="250" /></td>

					
                                        <c:if test="${command.saveMode ne 'V' }">
										<td class="text-center"><a
											class="btn btn-blue-2 btn-sm addAgendaDetails"
											title='<spring:message code="sfac.fpo.add" text="Add" />'
											onclick="addAgendaDetails(this);"> <i
												class="fa fa-plus-circle"></i></a> <a
											class='btn btn-danger btn-sm deleteAgendaDetails'
											title='<spring:message code="sfac.fpo.delete" text="Delete" />'
											onclick="deleteAgendaDetails(this);"> <i class="fa fa-trash"></i>
										</a></td></c:if>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:otherwise>
							</c:choose>
						</tbody>

					</table>
				</div>
               




				<h4>
					<spring:message code="sfac.mom.title" text="MOM Details" />
				</h4>
				<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-striped table-bordered" id="momDataTable">
						<thead>
							<tr>
								<th class="text-center"><spring:message code="sfac.srno"
										text="Sr No" /></th>
								<th class="text-center"><spring:message
										code="sfac.meeting.detail" text="Details of Action Points" /><span
									class="showMand"><i class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="sfac.action.owner" text="Action Owner" /> <span
									class="showMand"><i class="text-red-1">*</i></span></th>
								<c:if test="${command.saveMode ne 'V' ? true : false }">
								<th class="text-center"><spring:message code="sfac.action"
										text="Action Button" /></th></c:if>
							</tr>
						</thead>

						<tbody>
							<c:choose>
								<c:when test="${fn:length(command.masterDto.meetingMOMDto)>0 }">
									<c:forEach var="dto" items="${command.masterDto.meetingMOMDto}"
										varStatus="status">
										<tr class="momDetails">
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="sNo${d}"
													value="${d+1}" disabled="true" /> <form:hidden
													path="masterDto.meetingMOMDto[${d}].momId" id="momId${d}"
													class="momId" /></td>
											<td><form:input
													path="masterDto.meetingMOMDto[${d}].momComments"
													id="momComments${d}"
													disabled="${command.saveMode eq 'V' ? true : false }"
													class="form-control hasNameClass" maxlength="250" /></td>

											<td><c:set var="baseLookupCode" value="AOW" /> <form:select
													path="masterDto.meetingMOMDto[${d}].actionowner"
													class="form-control" id="actionowner${d}" disabled="${command.saveMode eq 'V' ? true : false }">
													<form:option value="0">
														<spring:message code="sfac.select" text="Select" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option code="${lookUp.lookUpCode}"
															value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>
											<c:if test="${command.saveMode ne 'V' }">
											<td class="text-center"><a
												class="btn btn-blue-2 btn-sm addMOMDetails"
												title='<spring:message code="sfac.fpo.add" text="Add" />'
												onclick="addMOMDetails(this);"> <i
													class="fa fa-plus-circle"></i></a> <a
												class='btn btn-danger btn-sm deleteMOMDetails '
												title='<spring:message code="sfac.fpo.delete" text="Delete" />'
												onclick="deleteMOMDetails(this);"> <i
													class="fa fa-trash"></i>
											</a></td></c:if>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="momDetails">
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass" id="sNo${d}"
												value="${d+1}" disabled="true" /></td>
										<td><form:input
												path="masterDto.meetingMOMDto[${d}].momComments"
												id="momComments${d}"
												disabled="${command.saveMode eq 'V' ? true : false }"
												class="form-control hasNameClass" maxlength="250" /></td>

										<td><c:set var="baseLookupCode" value="AOW" /> <form:select
												path="masterDto.meetingMOMDto[${d}].actionowner"
												class="form-control" id="actionowner${d}" disabled="${command.saveMode eq 'V' ? true : false }">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

                                        <c:if test="${command.saveMode ne 'V' }">
										<td class="text-center"><a
											class="btn btn-blue-2 btn-sm addMOMDetails"
											title='<spring:message code="sfac.fpo.add" text="Add" />'
											onclick="addMOMDetails(this);"> <i
												class="fa fa-plus-circle"></i></a> <a
											class='btn btn-danger btn-sm deleteMOMDetails'
											title='<spring:message code="sfac.fpo.delete" text="Delete" />'
											onclick="deleteMOMDetails(this);"> <i class="fa fa-trash"></i>
										</a></td></c:if>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:otherwise>
							</c:choose>
						</tbody>

					</table>
				</div>
               
				
				 <div class="participants">
				 <h4>
					<spring:message code="meeting.attenders.title"
						text="Meeting Participants" />
				</h4>
				<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-striped table-bordered"
						id="membersDataTable">
						<thead>
							<tr>
								<th class="text-center"><spring:message code="sfac.srno"
										text="Sr No" /></th>
								<th class="text-center"><spring:message
										code="sfac.memberName" text="Member Name" /><span
									class="showMand"><i class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="sfac.designation" text="Designation" /><span
									class="showMand"><i class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="sfac.organization" text="Organization" /><span
									class="showMand"><i class="text-red-1">*</i></span></th>
 								<c:if test="${command.showHideFlag eq 'N' && command.saveMode ne 'V'}">
								<th class="text-center"><spring:message code="sfac.action"
										text="Action Button" /></th></c:if>
							</tr>
						</thead>

						<tbody>
							<c:choose>
								<c:when
									test="${fn:length(command.masterDto.meetingDetailDto)>0 }">
									
									<c:forEach var="dto"
										items="${command.masterDto.meetingDetailDto}"
										varStatus="status">
										<tr class="memberDetails">
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="sequence${d}"
													value="${d+1}" disabled="true" /> <form:hidden
													path="masterDto.meetingDetailDto[${d}].memId"
													id="memId${d}" class="memId" /></td>
											<td><form:input
													path="masterDto.meetingDetailDto[${d}].memberName"
													id="memberName${d}"
													disabled="${command.saveMode eq 'V' ? true : false }"
													class="form-control hasNameClass" maxlength="250" /></td>

											<td>
												<div>
													<form:select id="dsgId${d}"
														disabled="${command.saveMode eq 'V' ? true : false }"
														path="masterDto.meetingDetailDto[${d}].dsgId"
														cssClass="form-control">
														<form:option value="0">
															<spring:message code="" text="Select" />
														</form:option>
														<c:forEach items="${command.designlist}" var="desig">
															<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
														</c:forEach>
													</form:select>
												</div>
											</td>
											
											<td><form:input
													path="masterDto.meetingDetailDto[${d}].organization"
													id="organization${d}"
													disabled="${command.saveMode eq 'V' ? true : false }"
													class="form-control hasNameClass" maxlength="200" /></td>
													
											<c:if test="${command.showHideFlag eq 'N' && command.saveMode ne 'V'}">
											<td class="text-center"><a
												class="btn btn-blue-2 btn-sm addMemberDetails"
												title='<spring:message code="sfac.fpo.add" text="Add" />'
												onclick="addMemberDetails(this);"> <i
													class="fa fa-plus-circle"></i></a> <a
												class='btn btn-danger btn-sm deleteMemberDetails'
												title='<spring:message code="sfac.fpo.delete" text="Delete" />'
												onclick="deleteMemberDetails(this);"> <i
													class="fa fa-trash"></i>
											</a></td></c:if>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="memberDetails">
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass" id="sequence${d}"
												value="${d+1}" disabled="true" /></td>

										<td><form:input
												path="masterDto.meetingDetailDto[${d}].memberName"
												id="memberName${d}"
												disabled="${command.saveMode eq 'V' ? true : false }"
												class="form-control hasNameClass" maxlength="250" /></td>

										<td>
											<div>
												<form:select id="dsgId${d}"
													disabled="${command.saveMode eq 'V' ? true : false }"
													path="masterDto.meetingDetailDto[${d}].dsgId"
													cssClass="form-control">
													<form:option value="0">
														<spring:message code="" text="Select" />
													</form:option>
													<c:forEach items="${command.designlist}" var="desig">
														<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</td>
										
										<td><form:input
													path="masterDto.meetingDetailDto[${d}].organization"
													id="organization${d}"
													disabled="${command.saveMode eq 'V' ? true : false }"
													class="form-control hasNameClass" maxlength="200" /></td>
													
										<c:if test="${command.showHideFlag eq 'N' && command.saveMode ne 'V'}">
										<td class="text-center"><a
											class="btn btn-blue-2 btn-sm addMemberDetails"
											title='<spring:message code="sfac.fpo.add" text="Add" />'
											onclick="addMemberDetails(this);"> <i
												class="fa fa-plus-circle"></i></a> <a
											class='btn btn-danger btn-sm deleteMemberDetails'
											title='<spring:message code="sfac.fpo.delete" text="Delete" />'>
												<i class="fa fa-trash"></i>
										</a></td></c:if>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:otherwise>
							</c:choose>
						</tbody>

					</table>
				</div>
                </div>

				<div class="comMemberList">
				<h4>
					<spring:message code="meeting.attenders.title"
						text="Meeting Participants" />
				</h4>
					<div class="table-responsive">
						<table class="table table-striped table-bordered"
							id="committeeMemDataTable">
							<thead>
								<tr>
									<th class="text-center"><spring:message code="sfac.srno"
											text="Sr No" /></th>
									<th class="text-center"><spring:message
											code="sfac.memberName" text="Member Name" /><span
										class="showMand"><i class="text-red-1">*</i></span></th>
									<th class="text-center"><spring:message
											code="sfac.designation" text="Designation" /><span
										class="showMand"><i class="text-red-1">*</i></span></th>
									<th class="text-center"><spring:message
											code="sfac.organization" text="Organization" /><span
										class="showMand"><i class="text-red-1">*</i></span></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.comMemberList}" var="member"
								varStatus="status">
								<tr>
									<input id="memberId${status.count-1}"  type="hidden" value="${member.comMemberId}">
									<td class="text-center">${status.count}</td>
									<td class="text-center">${member.memberName}</td>
									<td class="text-center">${member.designation}</td>
									<td class="text-center">${member.organization}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>

				</div>

				<div class="form-group">
					<c:if
						test="${command.saveMode eq 'C'  || command.saveMode eq 'E' }">
						<label for="" class="col-sm-2 control-label"> <spring:message
								code="sfac.meeting.document.upload"
								text="Document(Meeting Notice/Supporting document etc.)" /><span
							class="mand">*</span></label>
						<c:set var="count" value="0" scope="page"></c:set>
						<div class="col-sm-4">
							<%-- <c:if
								test="${command.saveMode eq 'C'  || command.saveMode eq 'E' }"> --%>
							<apptags:formField fieldType="7"
								fieldPath="attachments[${count}].uploadedDocumentPath"
								currentCount="${count}" folderName="${count}"
								fileSize="WORK_COMMON_MAX_SIZE" showFileNameHTMLId="true"
								isMandatory="true" maxFileCount="WORKS_MANAGEMENT_MAXSIZE"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
								cssClass="clear">
							</apptags:formField>
							<small class="text-blue-2 "> <spring:message
									code="sfac.validator.fileUploadNote"
									text="(Upload File upto 5MB and only pdf,doc,docx,xls, xlsx)" />
							</small>
							<%-- </c:if> --%>

							<%-- 	<c:if
								test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
								<input type="hidden" name="deleteFileId"
									value="${command.attachDocsList[0].attId}">
								<input type="hidden" name="downloadLink"
									value="${command.attachDocsList[0]}">
								<apptags:filedownload
									filename="${command.attachDocsList[0].attFname}"
									filePath="${command.attachDocsList[0].attPath}"
									actionUrl="MeetingMasterForm.html?Download"></apptags:filedownload>
							</c:if> --%>

						</div>
					</c:if>
				</div>


				<!--View Uploaded Documents start-->
				<c:if test="${not empty command.documentDtos}">
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#DocumentUpload"><spring:message
								code="sfac.upload.doc" /></a>
					</h4>
					<div id="DocumentUpload">
						<fieldset class="fieldRound">
							<div class="overflow">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped">
										<tbody>
											<tr>
												<th><spring:message
															code="sfac.srno" text="Sr No" /></th>
												<th><spring:message
															code="doc.download" text="Download" /></th>
											</tr>

											<c:forEach items="${command.documentDtos}" var="lookUp"
												varStatus="lk">
												<tr>
													<td align="center"><label>${lk.count}</label></td>
													<td align="center"><c:set var="links"
															value="${fn:substringBefore(lookUp.uploadedDocumentPath, lookUp.documentName)}" />
														<apptags:filedownload filename="${lookUp.documentName}"
															filePath="${lookUp.uploadedDocumentPath}"
															dmsDocId="${lookUp.documentSerialNo}"
															actionUrl="MeetingMasterForm.html?Download"></apptags:filedownload>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</fieldset>
					</div>
				</c:if>
				<!--View Uploaded Documents end -->

				<div class="text-center">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" id="createMeetingBtId"
							onClick="createMeeting(this);" class="btn btn-blue-2"
							title='<spring:message code="sfac.button.createMeeting" text="Create Meeting" />'>
							<spring:message code="sfac.button.createMeeting"
								text="Create Meeting" />
						</button>
					</c:if>



					<button type="button" class="btn btn-danger"
						title='<spring:message code="sfac.button.back" text="Back" />'
						onclick="backMeetingMasterForm();">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>


			</form:form>
		</div>
	</div>
</div>
