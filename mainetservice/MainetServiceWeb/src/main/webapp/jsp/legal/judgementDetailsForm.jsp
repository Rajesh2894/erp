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

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="CaseHearingDTO.hrDetails" text="Judgement Details"/>
			</h2>
			<!-- SET HELP DOC URL WHICH YOU SET IN YOU CONTROLLER INDEX METHOD -->
			<apptags:helpDoc url="JudgementDetails.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="JudgementDetails.html" name="frmHearingDetails" 	id="frmHearingDetails" class="form-horizontal">
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
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
					<div class="form-group">
					<apptags:input labelCode="caseEntryDTO.cseName"
						path="caseEntryDTO.cseName" isMandatory="true"
						cssClass="hasNameClass form-control"
						isDisabled="${true }"></apptags:input>
				</div>

				<jsp:include page="/jsp/legal/caseEntryViewForm.jsp" />
				<jsp:include page="/jsp/legal/hearingDateViewForm.jsp" />
				<c:if test="${command.hearing eq true}">
				<div class="form-group">
					
					<label class="control-label col-sm-2 required-control"> <spring:message code="lgl.judgename" text="JUDGE NAMES"/></label>
					<div class="col-sm-4">
						<form:select class=" mandColorClass form-control chosen-select-no-results"
							path="hearingEntity.judgeId" id="judgeId" disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${judges}" var="judges">
								<form:option value="${judges.id}">${judges.judgeFName} ${judges.judgeLName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="control-label col-sm-2 required-control"> <spring:message code="caseEntryDTO.advId" text="Advocate Name" /></label>
					<div class="col-sm-4">
						<form:select class=" mandColorClass form-control chosen-select-no-results"
							path="hearingEntity.advId" id="advId" disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${advocates}" var="advocate">
								<form:option value="${advocate.advId}">${advocate.advFirstNm} ${advocate.advMiddleNm} ${advocate.advLastNm}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
				
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="CaseHearingDTO.hrStatus" text="Hearing Status" /></label>
					
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="HSC" /> 
						<form:select class=" mandColorClass form-control "
							path="hearingEntity.hrStatus" id="hrStatus" disabled="${command.saveMode eq 'V' ? true : false }">
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
					<label class="control-label col-sm-2 required-control" for="attendees">
						<spring:message code="lgl.attendees" /></label>
					<div class="col-sm-4">
						<form:textarea id="attendees" path="hearingEntity.hrAttendee"
							class="form-control mandColorClass" data-rule-maxLength="200"
							data-rule-required="true" style="text-transform: uppercase;" />
					</div>
					<label class="control-label col-sm-2 required-control" for="address">
						<spring:message code="CaseHearingDTO.hrProceeding" text="PROCEEDING"/></label>
					<div class="col-sm-4">
						<form:textarea id="proceeding" path="hearingEntity.hrProceeding"
							class="form-control mandColorClass" data-rule-maxLength="200"
							data-rule-required="true" style="text-transform: uppercase;" />
					</div>
				</div>
				</c:if>
				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<c:if test="${command.hearing eq true}">
							<input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveHearingData(this);" class="css_btn btn btn-success">
						</c:if>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<input type="button" class="btn btn-warning " value="<spring:message code="bt.clear" text="Reset"/>" onclick="openForm('JudgementDetails.html','create')">
					</c:if>
					<apptags:backButton url="JudgementDetails.html"/>
			  </div>
			</form:form>
		</div>
	</div>
</div>