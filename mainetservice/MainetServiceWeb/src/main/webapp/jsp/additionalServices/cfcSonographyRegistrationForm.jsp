<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/cfcNursingHomeRegistration.js"></script>
<style>
#nursingHomePermission .centre-type input[type=radio] {
	margin-top: 0.4rem;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
	
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		
		<div class="widget-content padding">
			<form:form action="NursingHomePermission.html" method="post"
				class="form-horizontal" name="nursingHomePermission"
				id="nursingHomePermission">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
                <form:hidden path="serviceShortCode" id="serShortCode"/>
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				<h4 class="margin-top-0">
					<spring:message code="NHP.sonography.center" text="Registration of Sonography Center" />
				</h4>
				<div class="form-group centre-type">
				 <label class="col-sm-1"><spring:message code="" text="" /></label>
					<c:set var="baseLookupCode" value="PDC" />
					<c:forEach items="${command.getLevelData(baseLookupCode)}"
						var="lookUp">
						<div class="col-sm-2">
							<c:choose>
								<c:when test="${command.saveMode ne V}">
									<form:radiobutton path="cFCSonographyMastDtos.centerType"
										value="${lookUp.lookUpId}" id="centerType" disabled=""
										cssClass="" onclick="centerTypeCheck(this)"  />
								</c:when>
								<c:otherwise>
									<form:radiobutton path="cFCSonographyMastDtos.centerType"
										value="${lookUp.lookUpId}" id="centerType" disabled="${command.saveMode eq 'V' ? true : false}"
										cssClass="" onclick="centerTypeCheck(this)" />
								</c:otherwise>
							</c:choose>
							<label><spring:message code=""
									text="${lookUp.lookUpDesc}" /></label>
						</div>

					</c:forEach>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.name.applicant" text="Name of Applicant" /></label>
					<div class="col-sm-4">
						<form:input path="cFCSonographyMastDtos.applicantName" id="applicantName"
							placeHolder="Sh/Smt/Kum/Dr." disabled="${command.saveMode eq 'V' ? true : false}"
							class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.capacity.apply" text="Capacity of which applying" /></label>
					<c:set var="baseLookupCode" value="FPT" />
					<div class="col-sm-4">
						<form:select path="cFCSonographyMastDtos.applyCapacity"
							class="form-control mandColorClass chosen-select-no-results"
							id="applyCapacity" disabled="${command.saveMode eq 'V' ? true : false}">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.name.centre" text="Name of Centre" /></label>
					<div class="col-sm-4">
						<form:input path="cFCSonographyMastDtos.centerName" id="centerName"
							placeHolder="" disabled="${command.saveMode eq 'V' ? true : false}"
							class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.address.centre" text="Address of Centre" /></label>
					<div class="col-sm-4">
						<form:input path="cFCSonographyMastDtos.centerAddress" id="centerAddress"
							placeHolder="" disabled="${command.saveMode eq 'V' ? true : false}"
							class="form-control mandColorClass" maxlength="250" />
					</div>

				</div>
				
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.contact.number" text="Contact Number" /></label>
					<div class="col-sm-4">
						<form:input path="cFCSonographyMastDtos.contactNo"
							id="contactNo" class="form-control mandColorClass hasNumber"
							maxlength="10" disabled="${command.saveMode eq 'V' ? true : false}"/>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.emailId" text="Email ID" /></label>
					<div class="col-sm-4">
						<form:input path="cFCSonographyMastDtos.emailId"
							id="emailId" class="form-control" maxlength="50" disabled="${command.saveMode eq 'V' ? true : false}" />
					</div>
					
				</div>
				
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.institution.type" text="Type of Institution" /></label>
					<c:set var="baseLookupCode" value="IST" />
					<div class="col-sm-4">
						<form:select path="cFCSonographyMastDtos.institutionType"
							class="form-control mandColorClass chosen-select-no-results"
							id="institutionType" disabled="${command.saveMode eq 'V' ? true : false}">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.total.work.area" text="Total Work Area" /></label>
					<div class="col-sm-4">
						<form:input path="cFCSonographyMastDtos.workArea" id="workArea"
							placeHolder="" disabled="${command.saveMode eq 'V' ? true : false}"
							class="form-control mandColorClass" maxlength="150" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-6 control-label required-control"><spring:message
							code="NHP.diagnostic.test"
							text="Specify pre-natal diagnostic procedures/tests for which approval in sought" /></label>
					<div class="col-sm-6">
						<form:input path="cFCSonographyMastDtos.diagProcedure"
							id="diagProcedure" placeHolder="" disabled="${command.saveMode eq 'V' ? true : false}"
							class="form-control mandColorClass" maxlength="250" />
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.available.facility" text="Facilities Available" /></label>
					<div class="col-sm-1"></div>
					<div class="col-sm-3" style="font-size: 13px; margin-top: 10px;">
						<c:set var="baseLookupCode" value="FCC" />
						<c:set var="d" value="0" />
						<c:if test="${command.saveMode ne 'V' ? true : false}">
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<input type="checkbox" id="facilityCenter"
									value="${lookUp.lookUpId}" class="selectedRow"
									onchange="addfacilitiesCenter(this)" />${lookUp.lookUpDesc}</br>
							</c:forEach>
						</c:if>
						<c:if test="${command.saveMode eq 'V' ? true : false}">
							<c:forEach items="${command.list1}" var="lookUp">
								<c:if test="${lookUp.otherField eq 'Y' ? true : false}">
									<form:checkbox	path="" value="${lookUp.lookUpId}" id="facilityCenter"
										checked="checked" />${lookUp.lookUpDesc}</br>
								</c:if>
								<c:if test="${lookUp.otherField ne 'Y' ? true : false}">
									<form:checkbox path="" value="${lookUp.lookUpId}"
										id="facilityCenter" />${lookUp.lookUpDesc}</br>
								</c:if>
								<c:set var="d" value="${d + 1}" />
							</c:forEach>
						</c:if>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.available.facility.test"
							text="Facilities Available For Test" /></label>
					<div class="col-sm-1"></div>
					<div class="col-sm-3" style="font-size: 13px; margin-top: 10px;">
						<c:set var="baseLookupCode" value="FAT" />
						<c:set var="d" value="0" />
						<c:if test="${command.saveMode ne 'V' ? true : false}">
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<input type="checkbox" id="facilityTest"
									value="${lookUp.lookUpId}" class="selectedRow" 
									onchange="addfacilitiesTest(this)" />${lookUp.lookUpDesc}</br>
							</c:forEach>
						</c:if>
						<c:if test="${command.saveMode eq 'V' ? true : false}">
							<c:forEach items="${command.list}" var="lookUp">
								<c:if test="${lookUp.otherField eq 'Y' ? true : false}">
									<form:checkbox 	path="" 
										value="${lookUp.lookUpId}" id="facAvailtest" checked="checked" />${lookUp.lookUpDesc}</br>
								</c:if>
								<c:if test="${lookUp.otherField ne 'Y' ? true : false}">
									<form:checkbox path="" value="${lookUp.lookUpId}"
										id="facilityTest" />${lookUp.lookUpDesc}</br>
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V' ? true : false}">
						<button class="btn btn-success  submit"
							onclick="proceedToChecklist(this)" id="Submit" type="button"
							name="button" value="save">
							<i class="button-input"></i>
							<spring:message code="NHP.submit" text="Submit" />
						</button>

						<button type="button" class="btn btn-warning"
							onclick="ResetHospitalForm()">
							<spring:message code="NHP.reset" />
						</button>

						<button type="back" class="btn btn-danger"
							onclick="backToApplicantForm()">
							<spring:message code="NHP.back" />
						</button>
					</c:if>
					
					<c:if test="${command.saveMode eq 'V' ? true : false}">
						<button type="back" id="backButton" class="btn btn-danger"
							onclick="viewNursingHomeData('NursingHomePermission.html','viewNursingHomeData','${command.cfcApplicationMst.apmApplicationId}')">
							<spring:message code="NHP.back" />
						</button>
					</c:if>

				</div>
				
				</form:form>
			</div>
								
	</div>	
</div>