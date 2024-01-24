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
				<form:hidden path="" value="${validStatus}" id="validStatus"/>
				<form:hidden path="" value="${validHrDate}" id="validHrDate"/>
				<form:hidden path="" value="${validHrStatus}" id="validHrStatus"/>
				<form:hidden path="" value="${validHrPreparation}" id="validHrPreparation"/>
				<form:hidden path="" value="${validJudgeId}" id="validJudgeId"/>
				<form:hidden path="" value="${validAdvId}" id="validAdvId"/>
				<form:hidden path="" value="${validAttendees}" id="validAttendees"/>
				<form:hidden path="" value="${validProceeding}" id="validProceeding"/>
				<form:hidden path="" value="${command.caseHearingId}" id="caseHearingId"/>
				<form:hidden path="" value="${hrDtlForComntRevw.hrDate}" id="hrDateCR"/>
				<form:hidden path="" value="${hrDtlForComntRevw.hrId}" id="hrIdCR"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<jsp:include page="/jsp/legal/caseEntryViewForm.jsp" />
				<jsp:include page="/jsp/legal/hearingDateViewForm.jsp" />
				<form:hidden path="removeAttendeeId" id="removeAttendeeId" />
				<form:hidden path="removeComntId" id="removeComntId" />
				<!-- Hearing Personal details -->
				
				<c:if test="${command.hearing eq true}">
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
												<c:if test="${command.hearingMode ne 'HV'}">
													<th width="50"><a href="#" data-toggle="tooltip"
														data-placement="top" class="btn btn-blue-2  btn-sm"
														data-original-title="Add" onclick="addAttendeeData(event);"><strong
															class="fa fa-plus"></strong><span class="hide"></span></a></th>
												</c:if>
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
															<form:hidden
																path="caseHearingAttendeeDetailsDTOList[${e}].hrId" />	
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraName"
																	id="hraName${e}"
																	cssClass="required-control form-control hasNameClass"
																	disabled="${command.hearingMode eq 'HV' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraDesignation"
																	id="hraDesignation${e}"
																	cssClass="required-control form-control hasNameClass"
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
																	
															<c:if test="${command.hearingMode ne 'HV'}">
																<td><a href="#" data-toggle="tooltip"
																	data-placement="top" class="btn btn-danger btn-sm"
																	data-original-title="Delete"
																	onclick="deleteAttendeeRow($(this),'removeAttendeeId', event);">
																		<strong class="fa fa-trash"></strong> <span
																		class="hide"><spring:message code="lgl.delete"
																				text="Delete" /></span>
																</a></td>
															</c:if>
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
														<form:hidden
																path="caseHearingAttendeeDetailsDTOList[${e}].hrId"/>	
														<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraName"
																	id="hraName${e}"
																	cssClass="required-control form-control hasNameClass"
																	maxlength="50"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>
															<td><form:input
																	path="caseHearingAttendeeDetailsDTOList[${e}].hraDesignation"
																	id="hraDesignation${e}"
																	cssClass="required-control form-control hasNameClass"
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
														
														<c:if test="${command.saveMode ne 'V'}">
															<td><a href="#" data-toggle="tooltip"
																data-placement="top" class="btn btn-danger btn-sm"
																data-original-title="<spring:message code="lgl.delete"
																			text="Delete" />"
																onclick="deleteAttendeeRow($(this),'removeAttendeeId', event);">
																	<strong class="fa fa-trash"></strong> <span
																	class="hide"><spring:message code="lgl.delete"
																			text="Delete" /></span>
															</a></td>
														</c:if>
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
				<c:if test="${command.hearing eq true}">
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
												<c:if test="${command.hearingMode ne 'HV'}">
													<th width="50"><a href="#" data-toggle="tooltip"
														data-placement="top" class="btn btn-blue-2  btn-sm"
														data-original-title="Add" onclick="addComntData(event);"><strong
															class="fa fa-plus"></strong><span class="hide"></span></a></th>
												</c:if>
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
																	cssClass="required-control form-control hasNameClass"
																	disabled="${command.hearingMode eq 'HV' || data.disableField}" /></td>
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].revw"
																	id="revw${count}"
																	cssClass="required-control form-control hasNameClass"
																	disabled="${command.hearingMode eq 'HV' || data.disableField}" /></td>
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].hrDate"
																	id="hrDateRw${count}"
																	cssClass="form-control  date hrDate"
																	readOnly="true" /></td>
															<c:if test="${command.hearingMode ne 'HV'}">
																<td><a href="#" data-toggle="tooltip"
																	data-placement="top" class="btn btn-danger btn-sm"
																	data-original-title="Delete"
																	onclick="deleteComntRow($(this),'removeComntId');">
																		<strong class="fa fa-trash"></strong> <span
																		class="hide"><spring:message code="lgl.delete"
																				text="Delete" /></span>
																</a></td>
															</c:if>
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
																	cssClass="required-control form-control hasNameClass"
																	maxlength="400"
																	disabled="${command.saveMode eq 'HV'}" /></td>
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].revw"
																	id="revw${count}"
																	cssClass="required-control form-control hasNameClass"
																	maxlength="400"
																	disabled="${command.saveMode eq 'HV'}" /></td>
															
															<td><form:input
																	path="tbLglComntRevwDtlDTOList[${count}].hrDate"
																	id="hrDateRw${count}"
																	cssClass="form-control date hrDate"
																	readOnly="true"/></td>
														<%-- <form:input path="hearingEntry[${index.index}].hrDate" 
														class="form-control datepicker" value="" id="hrDate${index.index}" disabled="true"/> --%>
														<c:if test="${command.saveMode ne 'V'}">
															<td><a href="#" data-toggle="tooltip"
																data-placement="top" class="btn btn-danger btn-sm"
																data-original-title="<spring:message code="lgl.delete"
																			text="Delete" />"
																onclick="deleteComntRow($(this),'removeComntId');">
																	<strong class="fa fa-trash"></strong> <span
																	class="hide"><spring:message code="lgl.delete"
																			text="Delete" /></span>
															</a></td>
														</c:if>
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
					
					<label class="control-label col-sm-2 required-control"> <spring:message code="lgl.judgename" text="JUDGE NAMES"/></label>
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
					<!-- judgemaster button -->
					<c:if test="${command.saveMode ne 'V'}">
				<%-- 	<div class ="col-sm-4">
							 <button type="button" class="btn btn-blue-2  btn-sm" title="Add new Judge Details"
										onclick="addJudge('JudgeMaster.html','addJudge','${command.caseHearingId}')"
										>
										<i class="fa fa-plus" aria-hidden="true"></i>
									</button>
					</div> --%>
					<div class="col-sm-2">
										<a href="#" onclick="addJudge('JudgeMaster.html','addJudge','${command.caseHearingId}')"
											class="padding-top-5 link"><spring:message
												code='lgl.judge.detail.link' text = "Click here to add new Judge Details"/></a>
										
									</div>
					</c:if>
					<!-- disabled="${command.saveMode eq 'V' ? true : false }" -->
					
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
							class="form-control mandColorClass hasAlphaNumeric" maxlength="50" pattern="^[a-zA-Z,]*$"
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
				
			<%-- 	<div class="text-center margin-top-10">
					<c:if test="${command.hearingMode eq 'HE'}">
					<input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveHearingData(this);" class="css_btn btn btn-success">
						<c:if test="${command.hearing eq true}">
							<input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveHearingData(this);" class="css_btn btn btn-success">
						</c:if>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<input type="button" class="btn btn-warning " value="<spring:message code="bt.clear" text="Reset"/>" onclick="openForm('HearingDetails.html','create')">
					</c:if>
					<c:choose>
						<c:when test="${command.saveMode eq 'V'}">
					   		 <apptags:backButton url="HearingDetails.html"/>
					    </c:when>
					  <c:otherwise>
						  <input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveHearingData(this);" class="css_btn btn btn-success">
					 	  <apptags:backButton url="HearingDetails.html"/>
					  </c:otherwise>
				  </c:choose>
					
			  </div> --%>
			  <div class="text-center margin-top-10">
					<c:if test="${command.hearingMode eq 'HE'}">
					<c:if test="${command.orgFlag eq 'Y'}">
					<input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveHearingData(this);" class="css_btn btn btn-success" disabled="disabled">
						<%-- <c:if test="${command.hearing eq true}">
							<input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveHearingData(this);" class="css_btn btn btn-success">
						</c:if> --%>
					</c:if>
					</c:if>
					
					<c:if test="${command.hearingMode eq 'HE'}">
					<c:if test="${command.orgFlag ne 'Y'}">
					<input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveHearingData(this);" class="css_btn btn btn-success">
					
					<button type="Reset" class="btn btn-warning"
							onclick="resetHearingDate();" id="resetId">
							<spring:message code="lgl.reset" text="Reset"></spring:message>
						</button>
						
					</c:if>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<input type="button" class="btn btn-warning " value="<spring:message code="bt.clear" text="Reset"/>" onclick="openForm('HearingDetails.html','create')">
					</c:if>
					 <c:if test="${command.saveMode eq 'HE'}">
						<button type="Reset" class="btn btn-warning"
							onclick="resetHearing();" id="resetId">
							<spring:message code="lgl.reset" text="Reset"></spring:message>
						</button>
					</c:if> 
					<apptags:backButton url="HearingDetails.html"/>
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
		$('#hearingAttendeeTable tr:last').find('input[type=hidden]').eq(1).val(hrId);
		});
</script>