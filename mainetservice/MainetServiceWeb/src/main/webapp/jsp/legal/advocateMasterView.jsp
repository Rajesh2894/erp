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
<script src="js/mainet/validation.js"></script>
<script src="js/legal/advocateMaster.js"></script>
<script src="js/mainet/file-upload.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.advocatemaster" text="Advocate Master" />
			</h2>
			<apptags:helpDoc url="AdvocateMaster.html" />
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">

				<span><spring:message code="legal.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="legal.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="AdvocateMaster.html" name="AdvocateMaster"
				id="AdvocateMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">

					<div class="col-sm-12">

						<apptags:input labelCode="lgl.advocate.firstName"
							path="advocateMasterDTO.advFirstNm"
							cssClass="hasNameClass hasNoSpace" isDisabled="true"></apptags:input>

						<apptags:input labelCode="lgl.advocate.middleName"
							path="advocateMasterDTO.advMiddleNm"
							cssClass="hasNameClass form-control hasNoSpace" isDisabled="true"></apptags:input>



					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<apptags:input labelCode="lgl.advocate.lastName"
							path="advocateMasterDTO.advLastNm"
							cssClass="hasNameClass hasNoSpace" isDisabled="true"></apptags:input>

						<apptags:input labelCode="lgl.barcouncil.no"
							path="advocateMasterDTO.adv_barCouncilNo" isMandatory="false"
							cssClass=" form-control hasNoSpace" maxlegnth="16"
							isDisabled="true"></apptags:input>
					</div>

				</div>

				<div class="form-group">

					<div class="col-sm-12">

						<label class="control-label col-sm-2"
							for="Census"><spring:message code="lgl.gender"
								text="Gender" /></label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="advocateMasterDTO.advGen"
							cssClass=" form-control mandColorClass hasNameClass" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />

						<apptags:date fieldclass="datepicker" labelCode="lgl.dateOfBirth"
							datePath="advocateMasterDTO.advDob" isDisabled="true"
							cssClass="custDate mandColorClass">
						</apptags:date>

					</div>
				</div>

				<div class="form-group">

					<div class="col-sm-12">
						<apptags:input labelCode="lgl.phoneNumber" isDisabled="true"
							path="advocateMasterDTO.advMobile"
							cssClass="hasMobileNo mandColorClass "></apptags:input>

						<apptags:input labelCode="lgl.emailid" isDisabled="true"
							path="advocateMasterDTO.advEmail" cssClass="hasemailclass">
						</apptags:input>

					</div>
				</div>

				<div class="form-group">

					<div class="col-sm-12">

						<label class="col-sm-2 control-label" for="pannumber"> <spring:message
								code="lgl.panNumber" /></label>
						<div class="col-sm-4">
							<form:input type="text" name="PAN Number" id="advPanno"
								path="advocateMasterDTO.advPanno"
								class="form-control text-uppercase hasNoSpace" maxLength="10"
								onchange="fnValidatePAN(this)" disabled="true" />
						</div>

						<!-- UID : Aadhar number -->
						<apptags:input labelCode="lgl.aadharNumber"
							path="advocateMasterDTO.advUid" isDisabled="true"
							cssClass="hasAadharNo hasNoSpace">
						</apptags:input>

					</div>
				</div>

				<div class="form-group">

					<div class="col-sm-12">

						<apptags:date fieldclass="datepicker"
							labelCode="lgl.appointment.startDate"
							datePath="advocateMasterDTO.advAppfromdate"
							cssClass="mandColorClass" isDisabled="true"></apptags:date>

						<apptags:date fieldclass="datepicker"
							labelCode="lgl.appointment.endDate"
							datePath="advocateMasterDTO.advApptodate" isDisabled="true"
							cssClass="hasNameClass mandColorClass">
						</apptags:date>

					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<apptags:textArea labelCode="lgl.advocate.advocateAddress"
							path="advocateMasterDTO.advAddress" isDisabled="true"></apptags:textArea>

						<apptags:textArea labelCode="lgl.advocate.advocateOfficeAddress"
							path="advocateMasterDTO.advOfficeAddress" isDisabled="true"></apptags:textArea>


					</div>

				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<apptags:input labelCode="lgl.advocate.experience"
							path="advocateMasterDTO.advExperience" isDisabled="true"
							cssClass="hasDecimal hasNoSpace"></apptags:input>

						<%-- 	 <label for="advMaritalstatus"
						class="col-sm-2 control-label"><spring:message
						code="lgl.advocate.marital.status"
						text="Marital Status" /></label>
						 
						<div class="col-sm-4">
						 
						<form:select path="advocateMasterDTO.advMaritalstatus" cssClass="form-control" 
						disabled="true">  
							<form:option value=''><spring:message
						code="lgl.select"
						text="Select" /></form:option>
                			<form:option value='M'><spring:message code="lgl.advocate.marital.status.married" text="Married" />
                			</form:option>
                			<form:option value='U'><spring:message
						code="lgl.advocate.marital.status.unmarried"
						text="Unmarried" />
                			 </form:option>
            			</form:select>

						</div>  --%>


						<apptags:input labelCode="lgl.advChamberNo"
							path="advocateMasterDTO.advChamberNo" isDisabled="true"
							cssClass="hasDecimal hasNoSpace"></apptags:input>

					</div>
				</div>




				<div class="form-group">

					<div class="col-sm-12">



						<%-- 	<label for="advMaritalstatus"
						class="col-sm-2 control-label"><spring:message
						code="lgl.advocate.marital.status"
						text="Marital Status" /></label>
						 
						<div class="col-sm-4">
						 
						<form:select path="advocateMasterDTO.advMaritalstatus" cssClass="form-control" disabled="true">  <!-- height:28px; -->
							<form:option value=''>Select</form:option>
                			<form:option value='M' ><spring:message
						code="lgl.advocate.marital.status.married"
						text="Married" />
                			</form:option>
                			<form:option value='U'><spring:message
						code="lgl.advocate.marital.status.unmarried"
						text="Unmarried" />
                			 </form:option>
            			</form:select> --%>

						<%-- <apptags:input labelCode="lgl.advChamberNo"
							path="advocateMasterDTO.advChamberNo" isDisabled="true" cssClass= "hasDecimal hasNoSpace"></apptags:input> --%>


						<label for="advFeeType" class="col-sm-2 control-label"><spring:message
								code="lgl.advocate.fee.type" text="Fee Type" /></label>
						<apptags:lookupField items="${command.getLevelData('FET')}"
							path="advocateMasterDTO.advFeeType"
							cssClass="form-control mandColorClass hasNameClass"
							selectOptionLabelCode="Select" hasId="true" isMandatory="true"
							disabled="true" />

						<apptags:input labelCode="lgl.advocate.fee.amount"
							path="advocateMasterDTO.advFeeAmt" cssClass="hasAmount"
							isDisabled="true"></apptags:input>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<%-- <apptags:input labelCode="lgl.advocate.fee.amount" path="advocateMasterDTO.advFeeAmt" cssClass="hasAmount" isDisabled="true"></apptags:input> --%>


						<!--   120797 prefix changed from ADT to AVT bcoz of ADT is used for AuditType in TSCL Proj -->
						<label for="" class="col-sm-2 control-label"><spring:message
								code="lgl.advocate.type" text="Advocate Type" /></label>
						<apptags:lookupField items="${command.getLevelData('AVT')}"
							path="advocateMasterDTO.adv_advocateTypeId"
							cssClass="form-control  " selectOptionLabelCode="Select"
							hasId="true" isMandatory="false" disabled="true" />


						<label class="control-label col-sm-2 "> <spring:message
								code="lgl.courtnm" text="Court Name" />
						</label>
						<div class="col-sm-4">
							<!-- chosen-select-no-results -->
							<form:select
								class=" mandColorClass form-control chosen-select-no-results"
								path="advocateMasterDTO.adv_courtNameId" id="crtId"
								disabled="true">
								<form:option value="">
									<spring:message code="" text="Select" />
								</form:option>
								<c:forEach items="${command.courtNameList}" var="data">
									<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${data.id}">${data.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${data.id}">${data.crtName}</form:option>
									</c:otherwise>
								</c:choose>
								</c:forEach>
							</form:select>
						</div>

					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<%-- <label class="control-label col-sm-2 "> <spring:message
							code="lgl.courtnm" text="Court Name" />
					</label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select class=" mandColorClass form-control chosen-select-no-results"
							path="advocateMasterDTO.adv_courtNameId" id="crtId" disabled="true">
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.courtNameList}"
								var="data">
								<form:option value="${data.id}">${data.crtName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>


						<label class="col-sm-2 control-label"><spring:message
								code="lgl.advocate.status" text="Advocate Status" /></label>
						<div class="col-sm-4">

							<label class="radio-inline" for="advStatusYes"> <form:radiobutton
									name="advStatus" path="advocateMasterDTO.advStatus"
									checked="checked" value="Y" id="advStatusYes" disabled="true"></form:radiobutton>
								<spring:message code="lgl.yes" text="Yes" />
							</label> <label class="radio-inline" for="advStatusNo"> <form:radiobutton
									name="advStatus" path="advocateMasterDTO.advStatus" value="N"
									id="advStatusNo" disabled="true"></form:radiobutton> <spring:message
									code="lgl.no" text="No" />
							</label>
						</div>

					</div>
				</div>



				<div class="form-group">
					<div class="col-sm-12">
						<div class="text-center padding-bottom-10">

							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Back"
								onclick="backAdvocateMasterForm();">
								<spring:message code="lgl.back" text="Back"></spring:message>
							</button>

						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
</body>
</html>