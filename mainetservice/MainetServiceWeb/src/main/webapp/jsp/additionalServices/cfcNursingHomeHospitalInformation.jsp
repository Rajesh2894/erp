<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
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
#hospitalInformation .hospital-type input[type=radio] {
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
				class="form-horizontal" name="hospitalInformation"
				id="hospitalInformation">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="NHP.hospital.info"
						text="Hospital Information" />
				</h4>

				<div class="form-group hospital-type">
					<label class="col-sm-1"><spring:message code="" text="" /></label>
					<c:set var="baseLookupCode" value="HPT" />
					<c:forEach items="${command.getLevelData(baseLookupCode)}"
						var="lookUp">
						<div class="col-sm-2">
							<c:if test="${command.saveMode ne 'V' ? true : false}">
								<input type="radio" name="check" onclick="onlyOne(this)"
									value="${lookUp.lookUpId}">
							</c:if>
							<c:if test="${command.saveMode eq 'V' ? true : false}">
								<form:radiobutton path="cfcHospitalInfoDTO.hospitalType"
									value="${lookUp.lookUpId}" id="hospitalType"
									onclick="onlyOne(this)" />
							</c:if>
							<label><spring:message code=""
									text="${lookUp.lookUpDesc}" /></label>
						</div>

					</c:forEach>

				</div>

				<form:hidden path="cfcHospitalInfoDTO.hospitalType" value=""
					id="hospitalType" />
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.hospital.doc.name" text="Doctor's Name" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.doctorName" id="doctorName"
							placeHolder="Enter Doctor's Name"
							class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.doctor.maternity.count" text="Maternity Bed Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.mtrntyBedCount" onchange="totalCount()"
							id="maternityBedCount" placeHolder="Enter Maternity Bed Count"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.hospital.other.bed.count" text="Other Bed Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.otherBedCount" onchange="totalCount()"
							id="otherBedCount" placeHolder="Enter Other Bed Count"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.nursing.bed.count" text="Nursing Bed Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.nursingBedCount" onchange="totalCount()"
							id="nursingBedCount" placeHolder="Enter Nursing Bed Count"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.hospital.total.bed.count" text="Total Bed Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.totalBedCount"
							id="totalBedCount" placeHolder="Enter Total Bed Count"
							class="form-control mandColorClass hasNumber" maxlength="10" readonly="true" />
					</div>
				</div>

				<!-- Hospital Registration Information -->

				<h4 class="margin-top-0">
					<spring:message code="NHP.hospital.reg.info"
						text="Hospital Registration Information" />
				</h4>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.hospital.address.clinic"
							text="Name of Hospital" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.nameAddClinic"
							id="clinicAddress" placeHolder="Enter Name of Hospital"
							class="form-control hasNameClass mandColorClass" data-rule-minlength="2" maxlength="200" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.hospital.address.hospital"
							text="Address of Hospital" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.nameAddHospital"
							id="hospitalAddress"
							placeHolder="Enter Address of Hospital"
							class="form-control mandColorClass" data-rule-minlength="2" maxlength="250" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.hospital.contact.clinic"
							text="Contact Number of Doctor" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.contactNoClinic"
							id="clinicContactNo" placeHolder="Enter Contact Number of Clinic"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="NHP.hospital.contact.hospital"
							text="Contact Number of Hospital" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.contactNoHospital"
							id="hospitalContactNo"
							placeHolder="Enter Contact Number of Hospital"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.edu.elgblty" text="Educational Eligibility" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.eduEligibility"
							id="eduEligibility" placeHolder="Enter Education Eligibility"
							class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.reg.number" text="Registration Number" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.regNo" id="regNumber"
							placeHolder="Enter Registration Number"
							class="form-control mandColorClass" maxlength="10" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.reg.date" text="Registration Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="cfcHospitalInfoDTO.regDate" id="regDate"
								placeHolder="dd-mm-yyyy"
								class="form-control mandColorClass datepicker" readonly="true" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>
						</div>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.reg.branch.name"
							text="Registration Branch Name and State" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.regBranchANdState"
							id="regBranchANdState"
							placeHolder="Enter Registration Branch Name and State"
							class="form-control mandColorClass" maxlength="100" />
					</div>


				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.medical.prof.type" text="Medical Professional Type" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.medicalProfType"
							id="medicalProfType"
							placeHolder="Enter Medical Professional Type"
							class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.years.coplete.business"
							text="How many years completed to business" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.complYears" id="complYears"
							placeHolder="How number of years"
							class="form-control mandColorClass hasNumber" maxlength="3" />
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.business.start.date" text="Business Start Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="cfcHospitalInfoDTO.businessStartDate"
								id="businessStartDate" placeHolder="dd-mm-yyyy"
								class="form-control mandColorClass datepicker" readonly="true" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>
						</div>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.doctor.count" text="Total Doctor Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.doctorCount" id="doctorCount"
							placeHolder="Enter Total doctor Count" onchange="totalEmpCount()"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.nurse.count" text="Total Nurse Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.nurseCount" id="nurseCount"
							placeHolder="Enter Total Nurse Count" onchange="totalEmpCount()"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.employee.count" text="Total Mix Employee Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.empCount" id="employeeCount"
							placeHolder="Enter Total Mix Employee Count"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.security.count" text="Total Security Count" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.securityCount" onchange="totalEmpCount()"
							id="securityCount" placeHolder="Enter Total Security Count"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.number.years" text="From how many years have operation" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.yearsOfOperation"
							id="numberOfYears" placeHolder="Enter number of years"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.reg.MNP" text="Do you registered for MNP Nursing Home" /></label>
					<div class="col-sm-4">
						<label class="radio-inline"> <form:radiobutton
								id="registeredNursingHome" path="cfcHospitalInfoDTO.regMNP"
								value="Y" checked="true" /> <spring:message code="NHP.hosp.yes" text="Yes" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="registeredNursingHome" path="cfcHospitalInfoDTO.regMNP"
								value="N" /> <spring:message code="NHP.hosp.no" text="No" />
						</label>

					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.abrtion.center" text="Is Abortion Center Granted" /></label>
					<div class="col-sm-4">
						<label class="radio-inline"> <form:radiobutton
								id="abortionCenterGranted"
								path="cfcHospitalInfoDTO.abortionCntrFlag" value="Y"
								checked="true" /> <spring:message code="NHP.hosp.yes" text="Yes" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="abortionCenterGranted"
								path="cfcHospitalInfoDTO.abortionCntrFlag" value="N" /> <spring:message
								code="NHP.hosp.no" text="No" />
						</label>

					</div>
				</div>

				<div class="form-group">
					<%-- <label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.viditing.doctor" text="Visiting Doctor" /></label>
					<div class="col-sm-4">
						<form:input path="cfcHospitalInfoDTO.visitingDoctorName"
							id="visitingDoctor" placeHolder="Enter Visiting Doctor Name"
							class="form-control mandColorClass" maxlength="100" />
					</div> --%>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.dept" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="cfcHospitalInfoDTO.deptId"
							class="form-control mandColorClass chosen-select-no-results"
							id="department">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.tbDepartments}" var="tbDepartment">
								<form:option value="${tbDepartment.dpDeptid}">${tbDepartment.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control"><spring:message
							code="NHP.nota.fiber"
							text="Is the register of Nota Fiber Disease and Family Welfare Work Register kept" /></label>
					<div class="col-sm-4">
						<label class="radio-inline"> <form:radiobutton
								id="notaFiberDisease"
								path="cfcHospitalInfoDTO.regNotaFiberDeseaseFlag" value="Y"
								checked="true" /> <spring:message code="NHP.hosp.yes" text="Yes" />
						</label> <label class="radio-inline"> <form:radiobutton
								id="notaFiberDisease"
								path="cfcHospitalInfoDTO.regNotaFiberDeseaseFlag" value="N" />
							<spring:message code="NHP.hosp.no" text="No" />
						</label>

					</div>
				</div>

              <!--  #129287 -->
				<%-- <h4 class="margin-top-0">
					<spring:message code="NHP.national.program"
						text="Select Following National Program Will Be Conduct" />
				</h4>
				<div class="form-group">
					<div class="table-responsive-np clear">
						<table class="table table-striped table-bordered"
							id="searchAssetHome">
							<tbody>

								<c:set var="baseLookupCode" value="NPL" />
								<c:set var="d" value="0" />
								<c:if test="${command.saveMode ne 'V' ? true : false}">
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<div class="col-sm-2">

											<input type="checkbox" id="natinalProgram"
												value="${lookUp.lookUpId}" class="selectedRow"
												onchange="addProgeams(this)" />${lookUp.lookUpDesc}

										</div>
									</c:forEach>
								</c:if>
								<c:if test="${command.saveMode eq 'V' ? true : false}">
									<c:forEach items="${command.list}" var="lookUp">

										<div class="col-sm-2">
											<c:if test="${lookUp.otherField eq 'Y' ? true : false}">
												<form:checkbox path="" value="${lookUp.lookUpId}"
													id="natinalProgram" checked="checked" />${lookUp.lookUpDesc}
												</c:if>
											<c:if test="${lookUp.otherField ne 'Y' ? true : false}">
												<form:checkbox path="" value="${lookUp.lookUpId}"
													id="natinalProgram" />${lookUp.lookUpDesc}
												</c:if>
											<c:set var="d" value="${d + 1}" />
										</div>

									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div> --%>
				


				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V' ? true : false}">
						<button class="btn btn-success  submit"
							onclick="proceedToChecklist(this)" id="Submit" type="button"
							name="button" value="save">
							<i class="button-input"></i>
							<spring:message code="NHP.proceed" text="Proceed" />
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
						<%-- <button type="button" class="btn btn-blue-2 btn-sm"
							id="viewButton"
							onclick="viewHospitalInfo('NursingHomePermission.html','viewHospitalInfo')">

							<spring:message code="" text="CheckList" />
							<i class="fa fa-eye"></i>
						</button> --%>

						<button type="back" id="backButton" class="btn btn-danger"
							onclick="viewNursingHomeData('NursingHomePermission.html','viewNursingHomeData','${command.cfcApplicationMst.apmApplicationId}')">
							<spring:message code="NHP.back" />
						</button>

						<%-- <button type="back" class="btn btn-danger" id="backButton"
							onclick="backToApplicantForm()">
							<spring:message code="common.sequenceconfig.back" />
						</button> --%>
					</c:if>

				</div>

			</form:form>
		</div>
	</div>
</div>

