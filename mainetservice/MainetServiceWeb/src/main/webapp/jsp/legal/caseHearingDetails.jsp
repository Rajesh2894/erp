<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/legal/caseHearing.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="CaseHearingDTO.hrDetails" text="Hearing Details"/>
			</h2>
			<!-- SET HELP DOC URL WHICH YOU SET IN YOU CONTROLLER INDEX METHOD -->
			<apptags:helpDoc url="HearingDetails.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="HearingDetails.html" name="frmHearingDetails" id="frmHearingDetails" class="form-horizontal">
				<spring:message code="CaseHearingDTO.valid.status" text="Can not add new Hearing when Status is Schedule" var="validStatus"/>
				<spring:message code="CaseHearingDTO.valid.hrDate" text="Please Enter Hearing Date" var="validHrDate"/>
				<spring:message code="CaseHearingDTO.valid.hrStatus" text="Please Enter Status" var="validHrStatus"/>
				<spring:message code="CaseHearingDTO.valid.hrPreparation" text="Please Enter Preparation" var="validHrPreparation"/>
				<spring:message code="CaseHearingDTO.valid.judgeId" text="Please Enter Judge" var="validJudgeId"/>
				<spring:message code="CaseHearingDTO.valid.advId" text="Please Enter Advocate" var="validAdvId"/>
				<spring:message code="CaseHearingDTO.valid.attendees" text="Please Enter Attendees" var="validAttendees"/>
				<spring:message code="CaseHearingDTO.valid.proceeding" text="Please Enter Proceeding" var="validProceeding"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<jsp:include page="/jsp/legal/hearingDateViewForm.jsp" />
				<c:if test="${not empty command.hearingEntry}">
				<div class="panel-group accordion-toggle" id="hearingPersonalDiv">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a5"> <spring:message
										code="judgement.attendee.detail" text="Attendee Details" />
								</a>
							</h4>
						</div>
						<div id="a5" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<table id="hearingAttendeeTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th width="100"><spring:message
														code="lgl.srno" /></th>
												<th><spring:message
														code="lgl.hearing.personal.name" /></th>
												<th><spring:message
														code="lgl.hearing.personal.designation" /></th>
												<th><spring:message
														code="lgl.hearing.personal.phoneNo" /></th>
												<th><spring:message
														code="lgl.hearing.personal.emailId" /></th>
												
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${not empty command.caseHearingAttendeeDetailsDTOList}">
													<c:set var="e" value="0" scope="page" />
													<c:forEach items="${command.caseHearingAttendeeDetailsDTOList}" var="data"
														varStatus="index">
														<tr>
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${e}" value="${e+1}" disabled="true" /></td>
															<form:hidden
																path="caseHearingAttendeeDetailsDTOList[${e}].hraId" />
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraName"
																	id="hraName${e}"
																	cssClass="required-control form-control hasCharacter"
																	disabled="${command.hearingMode eq 'HV' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraDesignation"
																	id="hraDesignation${e}"
																	cssClass="required-control form-control"
																	disabled="${command.hearingMode eq 'HV' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraPhoneNo"
																	id="hraPhoneNo${e}"
																	cssClass="hasMobileNo required-control form-control"
																	disabled="${command.hearingMode eq 'HV' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraEmailId"
																	id="hraEmailId${e}"
																	cssClass="required-control form-control"
																	disabled="${command.hearingMode eq 'HV' ? true : false }" /></td>																	
														</tr>
														<c:set var="e" value="${e + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:set var="e" value="0" scope="page" />
													<tr>
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${e}"
																value="${e+1}" disabled="true" /></td>
														<form:hidden
															path="caseHearingAttendeeDetailsDTOList[${e}].hraId" />
														<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraName"
																	id="hraName${e}"
																	cssClass="required-control form-control hasCharacter"
																	maxlength="50"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraDesignation"
																	id="hraDesignation${e}"
																	cssClass="required-control form-control"
																	maxlength="50"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraPhoneNo"
																	id="hraPhoneNo${e}"
																	cssClass="hasMobileNo required-control form-control"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraEmailId"
																	id="hraEmailId${e}"
																	cssClass="required-control form-control"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>	
													</tr>
													<c:set var="e" value="${e + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>

									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
				<!-- Comment and review part started -->
				<c:if test="${not empty command.hearingEntry}">
				<div class="panel-group accordion-toggle" id="hearingComntDiv">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a5"> <spring:message
										code="lgl.hearing.detail.comnt" text="Comment and Review" />
								</a>
							</h4>
						</div>
						<div id="a5" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<table id="hearingComntTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th width="100"><spring:message
														code="lgl.srno" /></th>
												<th><spring:message
														code="lgl.hearing.comnt" /><i class="text-red-1">*</i></th>
												<th><spring:message
														code="lgl.hearing.revw" /><i class="text-red-1">*</i></th>
												<th><spring:message
														code="lgl.hearing.hrDate" /></th>
											</tr>
										</thead>
										<tbody>
											<c:choose>

												<c:when test="${not empty command.tbLglComntRevwDtlDTOList}">
													<c:set var="count" value="0" scope="page" />
													<c:forEach items="${command.tbLglComntRevwDtlDTOList}" var="data"
														varStatus="index">
														<tr>
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${count}" value="${count+1}" disabled="true" /></td>
															<form:hidden
																path="tbLglComntRevwDtlDTOList[${count}].comntId" />
															<form:hidden
																path="tbLglComntRevwDtlDTOList[${count}].hrId" id="hrIdRw${count}"/>
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].comnt"
																	id="comnt${count}"
																	cssClass="required-control form-control"
																	disabled="${command.hearingMode eq 'HV' || data.disableField}" /></td>
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].revw"
																	id="revw${count}"
																	cssClass="required-control form-control"
																	disabled="${command.hearingMode eq 'HV' || data.disableField}" /></td>
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].hrDate"
																	id="hrDateRw${count}"
																	cssClass="form-control  date hrDate"
																	readOnly="true" /></td>
														</tr>
														<c:set var="count" value="${count + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:set var="count" value="0" scope="page" />
													<tr>
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${count}"
																value="${count+1}" disabled="true" /></td>
														<form:hidden
															path="tbLglComntRevwDtlDTOList[${count}].comntId" />
														<form:hidden
															path="tbLglComntRevwDtlDTOList[${count}].hrId" id="hrIdRw${count}" 
															value="${count+1}"/>	
														<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].comnt"
																	id="comnt${count}"
																	cssClass="required-control form-control"
																	maxlength="400"
																	disabled="${command.saveMode eq 'HV'}" /></td>
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].revw"
																	id="revw${count}"
																	cssClass="required-control form-control"
																	maxlength="400"
																	disabled="${command.saveMode eq 'HV'}" /></td>
															
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].hrDate"
																	id="hrDateRw${count}"
																	cssClass="form-control date hrDate"
																	readOnly="true"/></td>
														
													</tr>
													<c:set var="count" value="${count + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>

									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
				
				<c:if test="${command.hearing eq true}">
				<div class="form-group">
					
					<label class="control-label col-sm-2"> <spring:message code="lgl.judgename" text="JUDGE NAMES"/></label>
					<div class="col-sm-4">
				
						<form:select class=" mandColorClass form-control chosen-select-no-results"
							path="hearingEntity.judgeId" id="judgeId" disabled="${command.hearingMode eq 'HV' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${judges}" var="judges">
								<form:option value="${judges.id}">${judges.judgeFName} ${judges.judgeLName}</form:option>
							</c:forEach>
							
						</form:select>
					</div>
				</div>
				<div class="form-group">
				
					<label class="control-label col-sm-2 required-control"> <spring:message code="caseEntryDTO.advId" text="Advocate Name" /></label>
					<div class="col-sm-4">
						<form:select class=" mandColorClass form-control chosen-select-no-results"
							path="hearingEntity.advId" id="advId" disabled="${command.hearingMode eq 'HV' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${advocates}" var="advocate">
								<form:option value="${advocate.advId}">${advocate.advFirstNm} ${advocate.advMiddleNm} ${advocate.advLastNm}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="CaseHearingDTO.hrStatus" text="Hearing Status" /></label>
					
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="HSC" /> 
						<form:select class=" mandColorClass form-control "
							path="hearingEntity.hrStatus" id="hrStatus" disabled="${command.hearingMode eq 'HV' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<c:if test="${lookUp.lookUpCode ne 'SH'}">
									<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
					</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="attendees">
						<spring:message code="lgl.attendees" /></label>
					<div class="col-sm-4">
						<form:textarea id="attendees" path="hearingEntity.hrAttendee"
							class="form-control mandColorClass hasCharacterAndComma" maxlength="50" pattern="^[a-zA-Z,]*$"
							data-rule-required="true" disabled="${command.hearingMode eq 'HV' ? true : false }"/>
					</div>
					<label class="control-label col-sm-2 required-control" for="address">
						<spring:message code="CaseHearingDTO.hrProceeding" text="PROCEEDING"/></label>
					<div class="col-sm-4">
						<form:textarea id="proceeding" path="hearingEntity.hrProceeding"
							class="form-control mandColorClass" maxlength="1000"
							data-rule-required="true" disabled="${command.hearingMode eq 'HV' ? true : false }"/>
					</div>
				</div>
				</c:if>
				
				 <div class="text-center margin-top-10">
				 <apptags:backButton url="CaseEntry.html"></apptags:backButton>
				 </div>
			  
			</form:form>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		var hrDate = $('#hrDateCR').val();	
		var hrId = $('#hrIdCR').val();
		var comnt = $('#comnt0').val();
		var revw = $('#revw0').val();
		if((!comnt || 0 === comnt.length) && (!revw || 0 === revw.length)){
			  $('#hearingComntTable tr:last td').eq(3).find("input[type='text']").datepicker({
				dateFormat: "dd/mm/yy"			
			}).datepicker("setDate", new Date(hrDate)); 
			$('#hearingComntTable tr:last').find('input[type=hidden]').eq(1).val(hrId);
			$(".hrDate").css('pointer-events', 'none');
		}
		});
</script>