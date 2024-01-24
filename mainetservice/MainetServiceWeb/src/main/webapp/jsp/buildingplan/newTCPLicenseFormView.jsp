<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/buildingplan/newTCPLicenseApprovalForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<style>
<!--
-->
#static {
	position: fixed;
	top: 3.9rem;
	left: 0;
	width: 100%;
	background-color: #ffc107;
	padding: 7px 20px;
	z-index: 1000;
}

#main-content {
	padding-top: 4rem;
}

.btn-blue-2 {
	background-color: #3c989e;
	color: #fff;
}

#acqDetTable tr td input[type=text], #acqDetTable tr td textarea{
	width: 160px;
}
input[type="radio"]:checked {
    filter: brightness(0.7);
}
#a3 .radio-inline{
	padding-top: 14px;
}
#dateOfIncorporation label, #dateOfIncorporation div{
	margin-top:5px;
}
</style>
<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantInfoFormView"
					action="NewTCPLicenseForm.html" method="post"
					class="form-horizontal">
					<form:hidden path="developerRegistrationDTO.companyDetailsAPIFlag" id="companyDetailsAPIFlagId"/>
					<c:set var="search" value="\\" />
					<c:set var="replace" value="\\\\" />
					<div id="static">
					<div style="background-color: #ffc107;">
					
					<div class="row padding-bottom-10">
						<div class="col-sm-2">
							<spring:message
								code="" text="Service Name :" />
						</div>
						<div class="col-sm-3">
								<b>New License</b>
						</div>
						<div class="col-sm-2">
							<spring:message
								code="" text="Application Number :" />
						</div>
						<div class="col-sm-3">
							<b>${command.licenseApplicationMasterDTO.applicationNoEService }</b>
						</div>
						<div class="col-sm-2">
						
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2">
							<spring:message
								code="" text="Case Number :" />
						</div>
						<div class="col-sm-3">
							<b>${command.licenseApplicationMasterDTO.caseNno }</b>
						</div>
						<div class="col-sm-2">
							<spring:message
								code="" text="Diary Number :" />
						</div>
						<div class="col-sm-3">
							<b>${command.licenseApplicationMasterDTO.diaryNo }</b>
						</div>
					</div>
					</div>
					</div>
					<div id="main-content">
					<div class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<div class="alert-msg-div alert alert-info alert-dismissible" 
					id="alertMsgDiv"></div>	
					
						<c:if test="${childPendingFlag eq true}">
							<br>Note: File is under parallel process with Users.
        				</c:if>
						<div class="panel-group accordion-toggle padding-top-100"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code=""
										text="Applicant Info" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-2 control-label " ><spring:message
													code="" text="Application No:" /></label>
											<div class="col-sm-4 ">
												<form:input path="licenseApplicationMasterDTO.applicationNo"
														class="form-control hasNameClass"
														id="applicationNo" readonly="true" disabled="true"/>
												
											</div>
										</div>	
										
										<h4>
						<spring:message code="" text="Developer Information" />
					</h4>
					
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="select.developer.type" text="Developer Type" />
						</label>
						<div class="col-sm-4">
							<form:select path="developerRegistrationDTO.devType"
								class="form-control" id="devType" readonly="true" disabled="true">
								<form:option value="">
									<spring:message code='' text="Select" />
								</form:option>
								<c:forEach items="${command.getLevelData('DEV')}" var="lookup">
									<form:option value="${lookup.lookUpId}"
										code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<h4>
						<spring:message code="developer.details" text="Developer Details" />
					</h4>
					<div class="form-group">
						<div id="showCompanyApiHeading" class="padding-left-15">
							<h5 class="text-red-1">
								<spring:message code="data.from.mca.api"
									text="Data Fetched From MCA API" />
							</h5>
						</div>
						<div id="CINDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="cin.number" text="CIN Number" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text"
									path="developerRegistrationDTO.cinNo" class="form-control" onblur="fetchCinDetails(this)"
									id="cinNoId" maxlength="21" minlength="21" readonly="true"/>
							</div>
						</div>
						<div id="LLPDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="llp.number" text="LLP Number" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.llpNo" id="LLpNo" maxlength="21"
									minlength="21" readonly="true"/>
							</div>
						</div>
						<div id="companyDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="company.name" text="Company Name" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.companyName" id="companyName"
									maxlength="50" minlength="2" readonly="true"/>
							</div>

						</div>
						
						<div id="firmNameDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="firm.name" text="Firm Name" /></label>
											
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.firmName" id="firmName"
									maxlength="50" minlength="2" readonly="true"/>
							</div>

						</div>

						<div id="dateOfIncorporation">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="date.of.incorporation" text="Date of Incorporation" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input type="text" class="form-control licenseDate currentDate"
										id="dateOfIncorporationId"
										path="developerRegistrationDTO.dateOfIncorporation"
										placeholder="DD/MM/YYYY" readonly="true" />
									<label class="input-group-addon"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div id="nameDiv">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="name.label" text="Name" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.name" id="nameId" onchange="verifyIndPanDetails()" maxlength="50"
									minlength="2" readonly="true"/>
							</div>
						</div>
						<div id="genderDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
								code="gender.label" text="Gender" /></label>
							<div class="col-sm-2">
								<form:select path="developerRegistrationDTO.gender" readonly="true"
									class="form-control" id="genderId" onchange="verifyIndPanDetails()">
									<form:option value="">
										<spring:message code='' text="select" />
									</form:option>
									<c:forEach items="${command.getLevelData('GEN')}" var="lookup">
										<form:option value="${lookup.lookUpId}"
											code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						
						<div id="dobDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
								code="date.of.birth" text="Date of Birth" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input type="text" class="form-control DOB currentDate"
										id="dobId" path="developerRegistrationDTO.dateOfBirth" onchange="verifyIndPanDetails()"
										placeholder="DD/MM/YYYY" readonly="true"/>
									<label class="input-group-addon"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>
						</div>
						
					</div>

					<div id="commonDetails">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="registered.address" text="Registered Address" /></label>
							<div class="col-sm-2">
								<form:textarea name="" type="text" class="form-control"
									path="developerRegistrationDTO.registeredAddress"
									id="registeredAddress" maxlength="100" minlength="2" readonly="true" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="email.label" text="Email" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text"
									class="form-control hasemailclass mandClassColor"
									path="developerRegistrationDTO.email" id="emailId" readonly="true"
									maxlength="50" minlength="8" data-rule-email="true" data-rule-required="true"/>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="mobile.no" text="Mobile No." /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control hasNumber"
									path="developerRegistrationDTO.mobileNo" id="mobNoId"
									maxlength="10" minlength="10" readonly="true" />
							</div>
						</div>
					</div>

					<div class="form-group">
						<div id="gstNo">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="gst.number" text="GST Number" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.gstNo" id="gstNoId" maxlength="15"
									minlength="15" readonly="true"/>
							</div>
						</div>

						<div id="panNo">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="pan.number" text="PAN" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control hasAlphaNumericClass text-uppercase" readonly="true"
									path="developerRegistrationDTO.panNo" id="panNoId" maxlength="10" minlength="10" onchange="verifyIndPanDetails()"/>
							</div>
							
						</div>
					</div>
					
					<div class="text-center" id="fetchCinBtn">
						<button type="button" class="button-input btn btn-success"
							name="button" onclick="fetchCinDetails(this)"
							id="fetchCinData">
							<spring:message code="" text="Fetch CIN Data" />
						</button>
					</div>
					
					<div id="mcaCompanyData">
						<h4>
							<spring:message code="developer.details.mca" text="Developer Details As Per MCA API" />
						</h4>
						<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="cin.number" text="CIN Number" /></label>
								<div class="col-sm-2">
									<form:input name="" type="text"
										path="" class="form-control" id="cinNoAApiId" maxlength="21"
										minlength="21" readonly="true" />
								</div>

								<label class="col-sm-2 control-label"><spring:message
										code="company.name" text="Company Name" /></label>
								<div class="col-sm-2">
									<form:input name="" type="text" class="form-control"
										path="" id="companyNameApiId"
										maxlength="50" minlength="2" readonly="true" />
								</div>

								<label class="col-sm-2 control-label"><spring:message
										code="date.of.incorporation" text="Date of Incorporation" /></label>
								<div class="col-sm-2">
									<div class="input-group">
										<form:input type="text" class="form-control licenseDate"
											id="dateOfIncorporationIdApiId"
											path=""
											placeholder="DD/MM/YYYY" readonly="true" />
										<label class="input-group-addon"><i
											class="fa fa-calendar"></i></label>
									</div>
								</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="registered.address" text="Registered Address" /></label>
							<div class="col-sm-2">
								<form:textarea name="" type="text" class="form-control"
									path=""
									id="registeredAddressApiId" maxlength="100" minlength="2"
									readonly="true" />
							</div>

							<label class="col-sm-2 control-label"><spring:message
									code="email.label" text="Email" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text"
									class="form-control hasemailclass mandClassColor"
									path="" id="emailIdApiId"
									readonly="true" maxlength="50" minlength="8"
									data-rule-email="true" data-rule-required="true" />
							</div>

							<label class="col-sm-2 control-label"><spring:message
									code="mobile.no" text="Mobile No." /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control hasNumber"
									path="" id="mobNoIdApiId"
									maxlength="10" minlength="10" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="gst.number" text="GST Number" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="" id="gstNoApiId" maxlength="15"
									minlength="15" readonly="true"/>
							</div>
						</div>
					</div>
					<div id="stakeholderDiv">
						<h4>
							<spring:message code="" text="Director Information as per MCA"></spring:message>
						</h4>

						<div id="directorsInfoMCA" class="margin-bottom-20">
							<table class="table table-bordered table-striped"
								id="directorsInfoMCATable">
								<c:set var="d" value="0" scope="page" />
								<thead>
									<tr>
										<th width="5%"><spring:message code="" text="Sr No." /></th>
										<th><spring:message code="" text="DIN Number" /></th>
										<th><spring:message code="" text="Name" /></th>
										<th><spring:message code="" text="Contact Number" /></th>

									</tr>
								</thead>
								
									<tbody>
								
									</tbody>
							</table>
						</div>
						
						<h4>
							<spring:message code="" text="Director Information as per Developer"></spring:message>
						</h4>

						<div id="directorsInfo" class="margin-bottom-20">
							<table class="table table-bordered table-striped"
								id="directorsInfoTable">
								<c:set var="d" value="0" scope="page" />
								<thead>
									<tr>
										<th width="5%"><spring:message code="" text="Sr No." /></th>
										<th><spring:message code="" text="DIN Number" /></th>
										<th><spring:message code="" text="Name" /></th>
										<th><spring:message code="" text="Contact Number" /></th>
										<th><spring:message code="" text="View Document" /></th>

									</tr>
								</thead>
								<c:if
									test="${not empty command.developerRegistrationDTO.developerDirectorInfoDTOList}">
									<tbody>
										<c:forEach var="director"
											items="${command.developerRegistrationDTO.developerDirectorInfoDTOList}"
											varStatus="status">
											<tr class="directorAppendable">
												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass" id="directorId${d}"
														value="${d + 1}" disabled="true" /></td>
												<td><form:input
														path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].dinNumber"
														value="" class="form-control hasNumber" disabled="true"
														id="dinNumberId${d}" minlength="10" maxlength="10" /></td>
												<td><form:input
														path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorName"
														class="form-control" id="directorNameId${d}" disabled="true"
														data-rule-required="true" minlength="2" maxlength="50" />
												</td>
												<td><form:input
														path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorContactNumber"
														class="form-control hasNumber"
														id="directorContactNumberId${d}" minlength="10"
														maxlength="10" data-rule-required="true" disabled="true"/></td>
												<td class="text-center">
													<c:set var="document" value="${director.directorDocsList[0]}" />
													<c:set var="filePath" value="${document.attPath}" />
													<c:set var="path" value="${fn:replace(filePath,search,replace)}" />
													<c:if test="${not empty path}">
														<button type="button" class="button-input btn btn-blue-2"
															name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','AdminHome.html?Download','','')
															" >
															<spring:message code="" text="VIEW" />
														</button>
													</c:if>
												<%--  <apptags:filedownload
														filename="${document.attFname}"
														filePath="${document.attPath}"
														actionUrl="NewTCPLicenseForm.html?Download"></apptags:filedownload> --%></td>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</c:if>

							</table>
						</div>
						
						<h4>
							<spring:message code="" text="Shareholding Patterns" />
						</h4>

						

						<table class="table table-bordered table-striped"
							id="stakeholderTable" >
							<c:set var="d" value="0" scope="page" />
							<thead>
								<tr>
									<th width="5%"><spring:message code="" text="Sr No." /></th>
									<th><spring:message code="" text="Name" /></th>
									<th><spring:message code="" text="Designation" /></th>
									<th><spring:message code="" text="Percentage'%'" /></th>
									<th><spring:message code="" text="View Document" /></th>
								</tr>
							</thead>
							<c:if
								test="${not empty command.developerRegistrationDTO.developerStakeholderDTOList}">
								<tbody>
									<c:forEach var="dataList"
										items="${command.developerRegistrationDTO.developerStakeholderDTOList}"
										varStatus="status">
										<tr class="developerAppendable">

											<td align="center"><form:input path=""
														cssClass="form-control mandColorClass" id="directorId${d}"
														value="${d + 1}" disabled="true" /></td>


											<td><form:input
														path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderName"
														value="" class="form-control hasNumber" disabled="true"
														id="dinNumberId${d}" minlength="10" maxlength="10" /></td>

											<td><form:input
														path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderDesignation"
														value="" class="form-control hasNumber" disabled="true"
														id="dinNumberId${d}" minlength="10" maxlength="10" /></td>

											<td><form:input
														path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderPercentage"
														value="" class="form-control hasNumber" disabled="true"
														id="dinNumberId${d}" minlength="10" maxlength="10" /></td>


											<td class="text-center">
												<c:set var="document" value="${dataList.stakeholderDocsList[0]}" />	
												<c:set var="filePath" value="${document.attPath}" />
												<c:set var="path" value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','AdminHome.html?Download','','')
														" >
														<spring:message code="" text="VIEW" />
													</button>	
												</c:if>										
											<%-- <apptags:filedownload filename="${document.attFname}"
															filePath="${document.attPath}"
															actionUrl="NewTCPLicenseForm.html?Download"></apptags:filedownload> --%></td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</c:if>
						</table>
					</div>
					
					<h4>
						<spring:message code="" text="Authorized Person Information" />
					</h4>
					<div class="form-group">
						<c:if test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authUserName ne null}">
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="" text="Name" />
						</label>
						<div class="col-sm-4">
							<form:input name="" type="text"
								readonly="true"
								path="licenseApplicationMasterDTO.authPName"
								class="form-control hasCharacter" id=""
								value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authUserName }"
								maxlength="" minlength="" />
						</div>
						</c:if>
						<c:if test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authMobileNo ne null}">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Mobile Number" /></label>
						<div class="col-sm-4">
							<form:input name="" type="text"
								path="licenseApplicationMasterDTO.authPMobile"
								class="form-control hasNumber" id=""
								 readonly="true"
								value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authMobileNo }"
								maxlength="10"  />
						</div>
						</c:if>
						
						
					</div>
					<div class="form-group">
						<c:if test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authEmail ne null}">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Email id for Authorized signatory" /></label>
						<div class="col-sm-4">
							<form:input name="" type="text"
								path="licenseApplicationMasterDTO.authPEmail"
								class="form-control hasCharacter" id=""
								readonly="true"
								value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authEmail }"
								maxlength=""  />
						</div>
						</c:if>
						<c:if test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authPanNumber ne null}">
						<label class="col-sm-2 control-label"> <spring:message
								code="" text="PAN Number" />
						</label>
						<div class="col-sm-4">
							<form:input name="" type="text"
								path="licenseApplicationMasterDTO.authPPAN"
								class="form-control" id=""
								readonly="true"
								value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authPanNumber }"
								maxlength="10" minlength="10" />
						</div>
						</c:if>

						
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="View Upload Digital Signature" /></label>
						<div class="col-sm-4">
							<c:set var="document"
								value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authDocumentList[0]}" />
							<%-- <apptags:filedownload filename="${document.attFname}"
								filePath="${document.attPath}"
								actionUrl="NewTCPLicenseForm.html?Download"></apptags:filedownload> --%>
							<c:set var="filePath" value="${document.attPath}" />
							<c:set var="path" value="${fn:replace(filePath,search,replace)}" />
							<c:if test="${not empty path}">
								<button type="button" class="button-input btn btn-blue-2"
									name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','AdminHome.html?Download','','')
									" >
									<spring:message code="" text="VIEW" />
								</button>
							</c:if>
						</div>

						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="View Upload Board Resolution" /></label>
						<div class="col-sm-4">
							<c:set var="document"
								value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[0].authDigitalPDFList[0]}" />
							<c:set var="filePath" value="${document.attPath}" />
							<c:set var="path" value="${fn:replace(filePath,search,replace)}" />
							<c:if test="${not empty path}">
								<button type="button" class="button-input btn btn-blue-2"
									name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','AdminHome.html?Download','','')
									" >
									<spring:message code="" text="VIEW" />
								</button>
							</c:if>
							<%-- <apptags:filedownload filename="${document.attFname}"
								filePath="${document.attPath}"
								actionUrl="NewTCPLicenseForm.html?Download"></apptags:filedownload> --%>
						</div>
					</div>

						<div>
							<c:if test="${not empty command.applicantDocumentList}">

								<div id="checkListDetails">
									<div class="panel-body">

										<div class="overflow margin-top-10 margin-bottom-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th><spring:message code="water.serialNo"
																	text="Sr No" /></th>
															<th><spring:message code="water.docName"
																	text="Document Name" /></th>
															<th width="500"><spring:message code=""
																	text="Download" /></th>
														</tr>

														<c:forEach items="${command.applicantDocumentList}"
															var="lookUp" varStatus="lk">

															<tr>
																<td>${lookUp.clmSrNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.clmDescEngl}</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.clmDesc}</td>
																	</c:otherwise>

																</c:choose>

																<td class="text-center">
																	<c:set var="filePath" value="${lookUp.attPath}" />
																	<c:set var="path"
																		value="${fn:replace(filePath,search,replace)}" />
																	<c:if test="${not empty path}">
																		<button type="button"
																			class="button-input btn btn-blue-2" name="button"
																			value="VIEW"
																			onclick="downloadFileInTag('${path}${replace}${lookUp.attFname}','AdminHome.html?Download','','')">
																			<spring:message code="" text="VIEW" />
																		</button>
																	</c:if>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
							</c:if>
						</div>
									</div>
							</div>
						</div>
					</div>
					
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code=""
										text="Application Purpose And Land Schedule" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control "
											for="apptype"><spring:message code=""
												text="Application Type" /></label>
										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="ATT" />
											<form:select path="licenseApplicationMasterDTO.appPAppType"
												cssClass="form-control mandColorClass" id="appPAppType"
												readonly="true" disabled="true">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label required-control "
											for="apptype"><spring:message
												code="" text="Purpose Name" /></label>
										<div class="col-sm-4">
											<form:select path="licenseApplicationMasterDTO.appPLicPurposeId"
												cssClass="form-control mandColorClass" id="appPLicPurposeId"
												disabled="true">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.purposeList}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									
										<div class="overflow margin-top-10">
											<div class="table-responsive">
											<c:set var="d" value="0" scope="page"></c:set>
												<table id="acqDetTable"								
													class="table table-striped table-bordered table-wrapper table-responsive">
													<thead>
				
														<tr>
															<th class="text-center" width="20%"><spring:message code="" text="District" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Development Plan" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Zone" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Sector" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Tehsil " /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Revenue Estate" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Hadbast No." /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Rectangle No." /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Khasra No." /></th>
															<th class="text-center" ><spring:message code="" text="Min" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of Land Owner" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Type of land" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Change in information" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Rectangle No./Mustil (Changed)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Khasra Number (Changed)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of the Land Owner as per
																Mutation/Jamabandi" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Whether Khasra been developed in
																collaboration" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of the developer company" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Date of registering
																collaboration agreement" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Whether collaboration agreement
																irrevocable (Yes/No)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of authorized signatory on
																behalf of land owner(s)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of authorized signatory on
																behalf of developer" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Registering Authority" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Collaboration Document" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="SPA/GPA/Board Resolution on
																behalf of land owner" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="SPA/GPA/Board Resolution on
																behalf of developer" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Collaborator Agreement Document" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Consolidation Type" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Kanal" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Marla" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Sarsai" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Bigha" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Biswa" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Biswansi" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Acquisition Status" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Consolidated Area (In Acres)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Non-Consolidated Area (In Acres)" /></th>
				
														</tr>
													</thead>
											<c:choose>
											<c:when
												test="${empty command.licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList}">
												<tbody>
												<tr class="acqAppendClass">
													
													<td class="text-center form-cell" style="width:80px"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].district"
															class="form-control hasNameClass valid" id="district${d}" style="width:80px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devPlan"
															class="form-control hasNameClass valid" id="devPlan${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell" width="40%"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].zone"
															class="form-control hasNameClass valid" id="zone${d}" style="width:70px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sector"
															class="form-control hasNameClass valid" id="sector${d}" style="width:60px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].thesil"
															class="form-control hasNameClass valid" id="thesil${d}" style="width:70px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].revEstate"
															class="form-control hasNameClass valid" id="revEstate${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].hadbastNo"
															class="form-control hasNameClass valid" id="hadbastNo${d}" style="width:70px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].rectangleNo"
															class="form-control hasNameClass valid" id="rectangleNo${d}" style="width:70px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasraNo"
															class="form-control hasNameClass valid" id="khasraNo${d}" style="width:80px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].min"
															class="form-control hasNameClass valid" id="minTable${d}" style="width:80px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerName"
															class="form-control hasNameClass valid" id="landOwnerName${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landType"
															class="form-control hasNameClass valid" id="landType${d}" style="width:90px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].chInfo"
															class="form-control hasNameClass valid" id="chInfo${d}" style="width:80px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].mustilCh"
															class="form-control hasNameClass valid" id="mustilCh${d}" style="width:80px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasaraCh"
															class="form-control hasNameClass valid" id="khasaraCh${d}" style="width:80px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerMUJAM"
															class="form-control hasNameClass valid" id="landOwnerMUJAM${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devColab"
															class="form-control hasNameClass valid" id="devColab${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devCompName"
															class="form-control hasNameClass valid" id="devCompName${d}"
															readonly="true" value="${command.developerRegistrationDTO.companyName}" disabled="true" /></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDate"
															class="form-control hasNameClass valid" id="collabAgrDate${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrRev"
															class="form-control hasNameClass valid" id="collabAgrRev${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignLO"
															class="form-control hasNameClass valid" id="authSignLO${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignDev"
															class="form-control hasNameClass valid" id="authSignDev${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].regAuth"
															class="form-control hasNameClass valid" id="regAuth${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabDec"
															class="form-control hasNameClass valid" id="collabDec${d}" style="width:100px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brLO"
															class="form-control hasNameClass valid" id="brLO${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brDev"
															class="form-control hasNameClass valid" id="brDev${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDoc"
															class="form-control hasNameClass valid" id="collabAgrDoc${d}" style="width:90px"
															readonly="true" disabled="true"/></td>
													<td class="text-center form-cell"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolType"
															class="form-control hasNameClass valid" id="consolType${d}"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].kanal"
															class="form-control hasNameClass valid" id="kanal${d}" style="width:60px"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].marla"
															class="form-control hasNameClass valid" id="marla${d}" style="width:60px"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sarsai"
															class="form-control hasNameClass valid" id="sarsai${d}" style="width:60px"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].bigha"
															class="form-control hasNameClass valid" id="bigha${d}" style="width:60px"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswa"
															class="form-control hasNameClass valid" id="biswa${d}" style="width:60px"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswansi"
															class="form-control hasNameClass valid" id="biswansi${d}" style="width:60px"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].acqStat"
															class="form-control hasNameClass valid" id="acqStat${d}" style="width:80px"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolTotArea" style="width:90px"
															class="form-control hasNameClass valid consolTotArea" id="consolTotArea${d}" onchange="calculateTotalArea()"
															readonly="true" disabled="true"/></td>
													<td class="text-center"><form:textarea
															path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].nonConsolTotArea" style="width:90px"
															class="form-control hasNameClass valid nonConsolTotArea" id="nonConsolTotArea${d}" onchange="calculateTotalArea()"
															readonly="true" disabled="true"/></td>
				
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</tbody>
											</c:when>
														<c:otherwise>
															<tbody>
																<c:forEach var="dataList"
																	items="${command.licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList}"
																	varStatus="status">
																	<tr class="acqAppendClass">
				
																		<td class="text-center form-cell" style="width:80px"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].district"
																				class="form-control hasNameClass valid" style="width:80px"
																				id="district${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devPlan"
																				class="form-control hasNameClass valid" id="devPlan${d}"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell" width="40%"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].zone"
																				class="form-control hasNameClass valid" id="zone${d}" style="width:70px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sector"
																				class="form-control hasNameClass valid" id="sector${d}" style="width:60px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].thesil"
																				class="form-control hasNameClass valid" id="thesil${d}" style="width:70px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].revEstate"
																				class="form-control hasNameClass valid"
																				id="revEstate${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].hadbastNo"
																				class="form-control hasNameClass valid" style="width:70px"
																				id="hadbastNo${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].rectangleNo"
																				class="form-control hasNameClass valid" style="width:70px"
																				id="rectangleNo${d}" readonly="true" disabled="true"/></td class="text-center">
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasraNo"
																				class="form-control hasNameClass valid" style="width:80px"
																				id="khasraNo${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].min"
																				class="form-control hasNameClass valid" style="width:80px"
																				id="minTable${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerName"
																				class="form-control hasNameClass valid"
																				id="landOwnerName${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landType"
																				class="form-control hasNameClass valid" style="width:90px"
																				id="landType${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].chInfo"
																				class="form-control hasNameClass valid" id="chInfo${d}" style="width:80px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].mustilCh"
																				class="form-control hasNameClass valid" style="width:80px"
																				id="mustilCh${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasaraCh"
																				class="form-control hasNameClass valid" style="width:80px"
																				id="khasaraCh${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerMUJAM"
																				class="form-control hasNameClass valid" 
																				id="landOwnerMUJAM${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devColab"
																				class="form-control hasNameClass valid"
																				id="devColab${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devCompName"
																				class="form-control hasNameClass valid"
																				id="devCompName${d}" readonly="true" disabled="true"
																				value="${command.developerRegistrationDTO.companyName}" /></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDate"
																				class="form-control hasNameClass valid"
																				id="collabAgrDate${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrRev"
																				class="form-control hasNameClass valid"
																				id="collabAgrRev${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignLO"
																				class="form-control hasNameClass valid"
																				id="authSignLO${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignDev"
																				class="form-control hasNameClass valid"
																				id="authSignDev${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].regAuth"
																				class="form-control hasNameClass valid" id="regAuth${d}"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabDec"
																				class="form-control hasNameClass valid" style="width:100px"
																				id="collabDec${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brLO"
																				class="form-control hasNameClass valid" id="brLO${d}"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brDev"
																				class="form-control hasNameClass valid" id="brDev${d}"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDoc"
																				class="form-control hasNameClass valid" style="width:90px"
																				id="collabAgrDoc${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center form-cell"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolType"
																				class="form-control hasNameClass valid"
																				id="consolType${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].kanal"
																				class="form-control hasNameClass valid" id="kanal${d}" style="width:60px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].marla"
																				class="form-control hasNameClass valid" id="marla${d}" style="width:60px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sarsai"
																				class="form-control hasNameClass valid" id="sarsai${d}" style="width:60px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].bigha"
																				class="form-control hasNameClass valid" id="bigha${d}" style="width:60px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswa"
																				class="form-control hasNameClass valid" id="biswa${d}" style="width:60px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswansi"
																				class="form-control hasNameClass valid" style="width:60px"
																				id="biswansi${d}" readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].acqStat"
																				class="form-control hasNameClass valid" id="acqStat${d}" style="width:80px"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolTotArea"
																				class="form-control hasNameClass valid consolTotArea" style="width:90px"
																				id="consolTotArea${d}" onchange="calculateTotalArea()"
																				readonly="true" disabled="true"/></td>
																		<td class="text-center"><form:textarea
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].nonConsolTotArea"
																				class="form-control hasNameClass valid nonConsolTotArea" style="width:90px"
																				id="nonConsolTotArea${d}"
																				onchange="calculateTotalArea()" readonly="true" disabled="true"/></td>
				
																		
				
																	</tr>
																	<c:set var="d" value="${d + 1}" scope="page" />
																</c:forEach>
															</tbody>
														</c:otherwise>
													</c:choose>
												</table>
											</div>
										</div>
										
										
									<div class="form-group margin-top-20">
										<label class="col-sm-2 control-label"><spring:message code="" text="Total Area(In Acres)" /></label>
										<div class="col-sm-2 ">
											<form:input name="" type="text"
												class="form-control" readonly="true"
												path="licenseApplicationMasterDTO.ciTotArea" id="ciTotArea" maxlength=""
												minlength="" />
										</div>
									</div>
					
								</div>
									<div>
										<c:if test="${not empty command.appPurposeDocumentList}">

											<div id="checkListDetails">
												<div class="panel-body">

													<div class="overflow margin-top-10 margin-bottom-10">
														<div class="table-responsive">
															<table
																class="table table-hover table-bordered table-striped">
																<tbody>
																	<c:set var="c" value="0" scope="page" />
																	<tr>
																		<th><spring:message code="water.serialNo"
																				text="Sr No" /></th>
																		<th><spring:message code="water.docName"
																				text="Document Name" /></th>
																		<th width="500"><spring:message code=""
																				text="Download" /></th>
																	</tr>

																	<c:forEach items="${command.appPurposeDocumentList}"
																		var="lookUp" varStatus="lk">

																		<tr>
																			<td>${lookUp.clmSrNo}</td>
																			<c:choose>
																				<c:when
																					test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<td>${lookUp.clmDescEngl}</td>
																				</c:when>
																				<c:otherwise>
																					<td>${lookUp.clmDesc}</td>
																				</c:otherwise>

																			</c:choose>

																			<td class="text-center"><c:set var="filePath"
																					value="${lookUp.attPath}" /> <c:set var="path"
																					value="${fn:replace(filePath,search,replace)}" />
																				<c:if test="${not empty path}">
																					<button type="button"
																						class="button-input btn btn-blue-2" name="button"
																						value="VIEW"
																						onclick="downloadFileInTag('${path}${replace}${lookUp.attFname}','AdminHome.html?Download','','')">
																						<spring:message code="" text="VIEW" />
																					</button>
																				</c:if></td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>

												</div>
											</div>
										</c:if>
									</div>
								</div>
						</div>
					</div>
					
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"><spring:message
										code=""
										text="Parameters Pertaining To Land Details" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse ">
								<div class="panel-body">
									<h4>
										<spring:message code="" text="1. Encumbrance" />
									</h4>
									
									<div class="form-group">
										<label class="control-label col-sm-3 required-control"> <spring:message
												code=""
												text="(a) Any encumbrance with respect to following"></spring:message>
										</label>
										<div class="col-sm-9">
											<label for="mortage" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Rehan / Mortgage" id="mortage" name="encumbrance" disabled="true"/> <spring:message code="" text="Rehan / Mortgage" />
											</label> 
											<label for="patta" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Patta/Lease" id="patta" name="encumbrance" disabled="true"/> <spring:message
													code="" text="Patta/Lease" />
											</label>
											<label for="gairmarusi" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Gairmarusi" id="gairmarusi" name="encumbrance" disabled="true" /> <spring:message
													code="" text="Gairmarusi" />
											</label>
											<label for="loan" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Loan" id="loan" name="encumbrance" disabled="true" /> <spring:message
													code="" text="Loan" />
											</label>
											<label for="other" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Any Other" id="other" name="encumbrance" disabled="true" /> <spring:message
													code="" text="Any Other" />
											</label>
											<label for="none" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="None" id="none" name="encumbrance" disabled="true"/> <spring:message
													code="" text="None" />
											</label>
										</div>
									</div>
									<div id="emburanceDiv">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control" for="name">
												<spring:message code="" text="Remark" />
											</label>
											<div class="col-sm-4 ">
												<form:textarea id="emburanceRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumbRemarks"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true"/>
											</div>
												<c:if test="${not empty command.encumbranceDocumentList}">
													<c:forEach items="${command.encumbranceDocumentList}"
														var="enLookUp" varStatus="lk">
														<label class="col-sm-2 control-label" for=""><spring:message
																code="" text="${enLookUp.clmDescEngl}" /></label>
														<div class="col-sm-4">
															<c:set var="filePath" value="${enLookUp.attPath}" />
															<c:set var="path"
																value="${fn:replace(filePath,search,replace)}" />
															<c:if test="${not empty path}">
																<button type="button"
																	class="button-input btn btn-blue-2" name="button"
																	value="VIEW"
																	onclick="downloadFileInTag('${path}${replace}${enLookUp.attFname}','AdminHome.html?Download','','')">
																	<spring:message code="" text="VIEW" />
																</button>
															</c:if>
														</div>
													</c:forEach>
												</c:if>
											</div>
				
									</div>
				
									<div class="form-group">
										<label class="control-label col-sm-5 required-control"> <spring:message
												code=""
												text="(b) Existing litigation, if any, concerning applied land including co-sharers and collaborator."></spring:message>
										</label>
										<div class="col-sm-7">
											<label for="yes" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigation" value="Yes" id="collaboratorYes" name="collaborator" disabled="true" /> <spring:message
													code="" text="Yes" />
											</label> <label for="no" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigation" value="No" id="collaboratorNo" name="collaborator" disabled="true" /> <spring:message code=""
													text="No" />
											</label>
										</div>
									</div>
									
									<div id="collaboratorDiv">
									<div class="form-group">
										<label class="control-label col-sm-5 required-control"> <spring:message
												code=""
												text="Court orders, if any, affecting applied land."></spring:message>
										</label>
										<div class="col-sm-7">
											<label for="yes" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd" value="Yes" id="appliedLandCourtYes" name="appliedLandCourt" disabled="true" /> <spring:message
													code="" text="Yes" />
											</label> <label for="no" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd" value="No" id="appliedLandCourtNo" name="appliedLandCourt" disabled="true" /> <spring:message code=""
													text="No" />
											</label>
										</div>
										</div>
									</div>
									
									<div id="appliedLandCourtDiv">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control" >
												<spring:message code="" text="Remark Case No" />
											</label>
											<div class="col-sm-4 ">
												<form:textarea id="appliedLandCourtRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrdRemAse"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true" />
											</div>
											<c:if test="${not empty command.courtOrdersLandDocumentList}">
												<c:forEach items="${command.courtOrdersLandDocumentList}"
													var="coLookUp" varStatus="lk">
													<label class="col-sm-2 control-label" for=""><spring:message
															code="" text="${coLookUp.clmDescEngl}" /></label>
													<div class="col-sm-4">
														<c:set var="filePath"
															value="${coLookUp.attPath}" /> <c:set var="path"
															value="${fn:replace(filePath,search,replace)}" />
														<c:if test="${not empty path}">
															<button type="button"
																class="button-input btn btn-blue-2" name="button"
																value="VIEW"
																onclick="downloadFileInTag('${path}${replace}${coLookUp.attFname}','AdminHome.html?Download','','')">
																<spring:message code="" text="VIEW" />
															</button>
														</c:if>
												</c:forEach>
											</c:if>
										</div>				
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-5 required-control"> <spring:message
												code=""
												text="(c) Any insolvency/liquidation proceedings against the Land Owing Company/Developer Company"></spring:message>
										</label>
										<div class="col-sm-7">
											<label for="yes" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv" value="Yes" id="insolvencyYes" name="insolvency" disabled="true"/> <spring:message
													code="" text="Yes" />
											</label> <label for="no" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv" value="No" id="insolvencyNo" name="insolvency" disabled="true" /> <spring:message code=""
													text="No" />
											</label>
										</div>
									</div>
									
									<div id="insolvencyDiv">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control" for="name">
												<spring:message code="" text="Remark" />
											</label>
											<div class="col-sm-4 ">
												<form:textarea id="insolvencyRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolvRemarks"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true"/>
											</div>
												<c:if test="${not empty command.insolvencyLandDocumentList}">
													<c:forEach items="${command.insolvencyLandDocumentList}"
														var="insLookUp" varStatus="lk">
														<label class="col-sm-2 control-label" for=""><spring:message
																code="" text="${insLookUp.clmDescEngl}" /></label>
														<div class="col-sm-4">
															<c:set var="filePath" value="${insLookUp.attPath}" />
															<c:set var="path"
																value="${fn:replace(filePath,search,replace)}" />
															<c:if test="${not empty path}">
																<button type="button"
																	class="button-input btn btn-blue-2" name="button"
																	value="VIEW"
																	onclick="downloadFileInTag('${path}${replace}${insLookUp.attFname}','AdminHome.html?Download','','')">
																	<spring:message code="" text="VIEW" />
																</button>
															</c:if>
														</div>
													</c:forEach>
												</c:if>
										</div>
				
									</div>
				
									
									<h4>
										<spring:message code="" text="2. Shajra Plan" />
									</h4>
									
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(a) As per applied land"></spring:message>
										</label>
										<div class="col-sm-10">
											<label for="" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand" value="Yes" id="appliedYes" name="appliedLand"
													disabled="true" /> <spring:message code="" text="Yes" />
											</label> <label for="" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand" value="No" id="appliedNo" name="appliedLand" disabled="true" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
				
									<div id="appliedLandDiv">
										<div class="form-group">
												<c:if test="${not empty command.shajraAppLandDocumentList}">
													<c:forEach items="${command.shajraAppLandDocumentList}"
														var="saLookUp" varStatus="lk">
														<label class="col-sm-2 control-label" for=""><spring:message
																code="" text="${saLookUp.clmDescEngl}" /></label>
														<div class="col-sm-4">
															<c:set var="filePath" value="${saLookUp.attPath}" />
															<c:set var="path"
																value="${fn:replace(filePath,search,replace)}" />
															<c:if test="${not empty path}">
																<button type="button"
																	class="button-input btn btn-blue-2" name="button"
																	value="VIEW"
																	onclick="downloadFileInTag('${path}${replace}${saLookUp.attFname}','AdminHome.html?Download','','')">
																	<spring:message code="" text="VIEW" />
																</button>
															</c:if>
														</div>
													</c:forEach>
												</c:if>
											</div>
									</div>
				
									
									
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(b) Revenue rasta"></spring:message>
										</label>
										<div class="col-sm-10">
											<label for="revenueRasta" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRasta" value="Yes" id="revenueRastaYes" name="revenueRasta" disabled="true"
												 /> <spring:message code="" text="Yes" />
											</label> <label for="revenueRasta" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRasta" value="No" id="revenueRastaNo" name="revenueRasta" disabled="true" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
				
									<div id="revenueRastaDiv">
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="Rasta Type"></spring:message>
										</label>
										<div class="col-sm-3">
											<label  class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTyp" value="Consolidated" id="consolidatedRastaYes" name="consolidatedRasta" disabled="true"
												 /> <spring:message code="" text="Consolidated" />
											</label> <label  class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTyp" value="Unconsolidated" id="consolidatedRastaNo" name="consolidatedRasta" disabled="true" /> <spring:message
													code="" text="Unconsolidated" />
											</label>
										</div>
										
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="Consolidated/Unconsolidated" /></label>
										<div class="col-sm-2">
										<c:set var="baseLookupCode" value="CON" />
											<form:select path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTypConId"
												cssClass="form-control mandColorClass" disabled="true" id="">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
											</div>
										
										<label class="col-sm-1 control-label required-control" for=""><spring:message
												code="" text="Width" /></label>
										<div class="col-sm-2 ">
											<form:input name="" type="text"
												class="form-control"
												path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTypConWid" id="totalAppliedArea"
												maxlength="20"  disabled="true"/>
										</div>
										
										</div>
									</div>
				
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(c) Watercourse"></spring:message>
										</label>
										<div class="col-sm-10">
											<label for="watercourse" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourse" value="Yes" id="watercourseYes" name="watercourse" disabled="true"
													 /> <spring:message code="" text="Yes" />
											</label> <label for="watercourse" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourse" value="No" id="watercourseNo" name="watercourse" disabled="true" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
									
									<div id="watercourseDiv">
										<div class="form-group">
											
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="Type" /></label>
										<div class="col-sm-2">
											<c:set var="baseLookupCode" value="CON" />
											<form:select path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourseTypId"
												cssClass="form-control mandColorClass" id="watercourseType" disabled="true">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
										
										<label class="col-sm-2 control-label required-control" for=""><spring:message
												code="" text="Width" /></label>
										<div class="col-sm-2 ">
											<form:input name="" type="text"
												class="form-control"
												path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourseTypWid" id="totalAppliedArea"
												maxlength="20" disabled="true" />
										</div>
										
										
										<label class="col-sm-2 control-label required-control"> <spring:message
													code="" text="Remarks" /></label>
										<div class="col-sm-2 ">
											<form:textarea id="watercourseRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourseTypRem"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true" />
										</div>
										
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="Original Shajra Plan by Patwari"></spring:message>
										</label>
										<div class="col-sm-4">
											<label for="" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandPatwari" value="Yes" id="OGShajraPlanYes" name="OGShajraPlan" disabled="true"
													 /> <spring:message code="" text="Yes" />
											</label> <label for="" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandPatwari" value="No" id="OGShajraPlanNo" name="OGShajraPlan" disabled="true" /> <spring:message
													code="" text="No" />
											</label>
										</div>
										
									</div>
									
									
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="Original Shajra plan showing Boundary of Applied Land"></spring:message>
										</label>
										<div class="col-sm-4">
											<label for="" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandBoundary" value="Yes" id="boundryAppliesLandYes" name="boundryAppliesLand" disabled="true"
													 /> <spring:message code="" text="Yes" />
											</label> <label for="" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandBoundary" value="No" id="boundryAppliesLandNo" name="boundryAppliesLand" disabled="true" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(d) Acquisition status"></spring:message>
										</label>
										<div class="col-sm-10">
											<label for="acquisitionStatus" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatus" value="Yes" id="acquisitionStatusYes" name="acquisitionStatus" disabled="true"
													 /> <spring:message code="" text="Yes" />
											</label> <label for="acquisitionStatus" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatus" value="No" id="acquisitionStatusNo" name="acquisitionStatus" disabled="true"/> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
									<div class="acquisitionStatusDiv">
										<div class="form-group">
										
											<label class="col-sm-2 radio1 control-label  required-control "><spring:message
													code="" text="Date of section 4 notification" /></label>
											<div class="col-sm-2">
												<div class="input-group">
													<form:input
														class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
														placeholder="DD/MM/YYYY" autocomplete="off"
														id="" disabled="true"
														path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusD4Not"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
											
											<label class="col-sm-2 radio1 control-label  required-control "><spring:message
													code="" text="Date of section 6 notification" /></label>
											<div class="col-sm-2">
												<div class="input-group">
													<form:input
														class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
														placeholder="DD/MM/YYYY" autocomplete="off"
														id="spAcqStatusD6Not"
														disabled="true"
														path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusD6Not"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
											
											<label class="col-sm-2 radio1 control-label  required-control "><spring:message
													code="" text="Date of award" /></label>
											<div class="col-sm-2">
												<div class="input-group">
													<form:input
														class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
														placeholder="DD/MM/YYYY" autocomplete="off"
														id=""
														disabled="true"
														path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusDAward"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
										</div>
									</div>
									
									<div class="acquisitionStatusDiv">
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(g) Whether Land Released/Excluded from aqusition proceeding"></spring:message>
										</label>
										<div class="col-sm-10">
											<label for="compactBlock" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr" value="Yes" id="excludedAqusitionProcYes" name="excludedAqusitionProc"
													disabled="true" /> <spring:message code="" text="Yes" />
											</label> <label for="compactBlock" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr" value="No" id="excludedAqusitionProcNo" name="excludedAqusitionProc" disabled="true" /> <spring:message
													code="" text="No" />
											</label>
										</div>
										</div>
									</div>
									
									<div id="aqusitionProceedingDiv">
									
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"> <spring:message
													code="" text="Whether land compensation"></spring:message>
											</label>
											<div class="col-sm-4">
												<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusLdComp"
														value="Yes" id="landCompensationYes" name="landCompensation" disabled="true" />
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusLdComp"
														value="No" id="landCompensationNo" name="landCompensation" disabled="true"/>
													<spring:message code="" text="No"  />
												</label>
											</div>
				
											<label class="col-sm-2 control-label required-control"><spring:message
													code="" text="Status of release" /></label>
											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="SEB" />
												<form:select path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelStat" cssClass="form-control mandColorClass"
													id="" disabled="true">
													<form:option value="">
														<spring:message code='master.selectDropDwn' />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										
										
										<div class="form-group">
											<label class="col-sm-2 control-label  required-control "><spring:message
													code="" text="Date of release" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input
														class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
														placeholder="DD/MM/YYYY" autocomplete="off" id="" disabled="true"
														path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
				
											<label class="col-sm-2 control-label" for=""><spring:message
													code="" text="Site Details" /></label>
											<div class="col-sm-4 ">
												<form:input name="" type="text" class="form-control" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusSiteDet"
													id="siteDetials" maxlength="20" disabled="true"/>
											</div>
										</div>
										
										
										<div class="form-group">
												<c:if test="${not empty command.releaseOrderDocumentList}">
													<c:forEach items="${command.releaseOrderDocumentList}"
														var="rLookUp" varStatus="lk">
														<label class="col-sm-2 control-label" for=""><spring:message
																code="" text="${rLookUp.clmDescEngl}" /></label>
														<div class="col-sm-4">
															<c:set var="filePath" value="${rLookUp.attPath}" />
															<c:set var="path"
																value="${fn:replace(filePath,search,replace)}" />
															<c:if test="${not empty path}">
																<button type="button"
																	class="button-input btn btn-blue-2" name="button"
																	value="VIEW"
																	onclick="downloadFileInTag('${path}${replace}${rLookUp.attFname}','AdminHome.html?Download','','')">
																	<spring:message code="" text="VIEW" />
																</button>
															</c:if>
														</div>
													</c:forEach>
												</c:if>

												<label class="control-label col-sm-2 required-control"> <spring:message
													code="" text="whether litigation regarding release of Land"></spring:message>
											</label>
											<div class="col-sm-4">
												<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelLitig"
														value="Yes" id="releaseOfLandYes" name="releaseOfLand" /> <spring:message
														code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelLitig"
														value="No" id="releaseOfLandNo" name="releaseOfLand" /> <spring:message
														code="" text="No" />
												</label>
											</div>
				
										</div>
										
										
										<div id="releaseOfLandDiv">
											<div class="form-group">
												<label class="col-sm-2 control-label" for=""><spring:message
														code="" text="CWP/SLP Number" /></label>
												<div class="col-sm-4 ">
													<form:input name="" type="text" class="form-control" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelCwpSl"
														id="cwpSlpNumber" maxlength="20" />
												</div>
											</div>
										</div>
										
									</div>
				
									
				
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(e) Whether in Compact Block"></spring:message>
										</label>
										<div class="col-sm-10">
											<label for="compactBlock" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock" value="Yes" id="compactBlockYes" name="compactBlock"
													 disabled="true"/> <spring:message code="" text="Yes" />
											</label> <label for="compactBlock" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock" value="No" disabled="true" id="compactBlockNo" name="compactBlock" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
									
									<div id="compactBlockDiv">
									
										<div class="form-group">
									
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="Separated by" /></label>
										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="SEB" />
											<form:select path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlockSep"
												cssClass="form-control mandColorClass" id=""
												disabled="true">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
										
										<label class="col-sm-2 control-label" for=""><spring:message
												code="" text="Pocket" /></label>
										<div class="col-sm-4 ">
											<form:input name="" type="text"
												class="form-control"
												path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlockPkt" id="totalAppliedArea"
											maxlength="20" disabled="true" />
										</div>
										
										</div>
										
									</div>
									
									
				
									<h4>
										<spring:message code="" text="3. Surroundings" />
									</h4>
				
										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<c:set var="d" value="0" scope="page" />
												<table class="table table-striped table-bordered table-wrapper" id="surroundingDetail">
													<thead>
				
														<tr>
															<th class="text-center"><spring:message code=""
																	text="Pocket Name" /></th>
															<th class="text-center"><spring:message code=""
																	text="North" /></th>
															<th class="text-center"><spring:message code=""
																	text="South" /></th>
															<th class="text-center"><spring:message code=""
																	text="East" /></th>
															<th class="text-center"><spring:message code=""
																	text="West" /></th>
				
														</tr>
													</thead>
													<tbody>
														<c:forEach var="dataList" items="${command.licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList}" varStatus="status">
							                                <tr class="appendableClass">
							                                    <td class="text-center">
							                                        <form:input name="" type="text"
							                                            class="form-control"
							                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].pocketName"
							                                            id="pocketName${d}" disabled="true"/>
							                                    </td>
							                                    <td class="text-center">
							                                        <form:input name="" type="text"
							                                            class="form-control"
							                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].north"
							                                            id="north${d}" disabled="true"/>
							                                    </td>
							                                    <td class="text-center">
							                                        <form:input name="" type="text"
							                                            class="form-control"
							                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].south"
							                                            id="south${d}" disabled="true"/>
							                                    </td>
							                                    <td class="text-center">
							                                        <form:input name="" type="text"
							                                            class="form-control"
							                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].east"
							                                            id="east${d}" disabled="true"/>
							                                    </td>
							                                    <td class="text-center">
							                                        <form:input name="" type="text"
							                                            class="form-control"
							                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].west"
							                                            id="west${d}" disabled="true"/>
							                                    </td>
							                                    
							                                </tr>
							                                <c:set var="d" value="${d + 1}" scope="page" />
							                            </c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									
									<h4>
										<spring:message code="" text="4. Existing Approach" />
									</h4>
									
									<div class="form-group">
										<label class="control-label col-sm-5 required-control"> <spring:message
												code=""
												text="(a) Details of existing approach as per policy dated 20-10-2020"></spring:message>
										</label>
										<div class="col-sm-7">
											<label for="approach1" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr" value="Category-I approach" disabled="true" id="approachYes" name="approach" /> <spring:message code="" text="Category-I approach" />
											</label>
											<label for="approach2" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr" value="Category-II approach" disabled="true" id="approachNo" name="approach" /> <spring:message
													code="" text="Category-II approach" />
											</label>
										</div>
									</div>
				
									<div id="category1Div">
									
										<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="(a) Approach available from minimum 4 karam (22 ft) wide revenue rasta."></spring:message>
											</label>
											<div class="col-sm-7">
												<label for="approach1" class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.aAppWRevRas" value="Yes" id="minimum4karamYes" name="minimum4karam" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label for="approach2" class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.aAppWRevRas" value="No" id="minimum4karamNo" name="minimum4karam" disabled="true" />
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
										
										<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="(b) Approach available from minimum 11 feet wide revenue rasta and applied site abuts acquired alignment of 
														the sector road and there is no stay regarding construction on the land falling under the abutting sector road"></spring:message>
											</label>
											<div class="col-sm-7">
												<label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.bAppASR" value="Yes" id="abuttingSectorRoadYes" name="abuttingSectorRoad" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.bAppASR" value="No" id="abuttingSectorRoadNo" name="abuttingSectorRoad" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
										
										<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="(c) Applied site abouts already constructed sector road or internal circulation road of approved sectoral plan (of min. 18m/24m width as the case may be) provided
														 its entire stretch required for approach is licenced and is further leading upto atleast 4 karam wide revenue rasta. "></spring:message>
											</label>
											<div class="col-sm-7">
												<label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.cAppCSR" value="Yes" id="atleast4KaramYes" name="atleast4Karam" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.cAppCSR" value="No" id="atleast4KaramNo" name="atleast4Karam" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
										
										<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="(d) Applied land is accessible from a minimum 4 karam wide rasta through adjoining own land of the applicant (but not applied for licence). "></spring:message>
											</label>
											<div class="col-sm-7">
												<label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.dAppAccLOA" value="Yes" id="adjoiningOwnLandYes" name="adjoiningOwnLand" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.dAppAccLOA" value="No" id="adjoiningOwnLandNo" name="adjoiningOwnLand" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
										
										<div id="adjoiningOwnLandDiv">
											<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="(d1) If applicable, whether the applicant has donated at least 4 karam wide strip from its adjoining own land in favour of the Gram Panchayat/Municipality, in order to connect the applied site to existing 4 karam rasta?  "></spring:message>
											</label>
											<div class="col-sm-7">
												<label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.d1AppAccGPM" value="Yes" id="applicantDonatedStripYes" name="applicantDonatedStrip" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.d1AppAccGPM" value="No" id="applicantDonatedStripNo" name="applicantDonatedStrip" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
										</div>
										
										<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="(e) Applied land is accessible from a minimum 4 karam wide rasta through adjoining other’s land"></spring:message>
											</label>
											<div class="col-sm-7">
												<label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.eAppOL" value="Yes" id="adjoiningOtherLandYes" name="adjoiningOtherLand" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.eAppOL" value="No" id="adjoiningOtherLandNo" name="adjoiningOtherLand" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
										
										<div id="adjoiningOtherLandDiv">
											<div class="form-group">
												<label class="control-label col-sm-5 required-control"> <spring:message
														code=""
														text="(e1) whether the land-owner of the adjoining land has donated at least 4 karam wide strip of land to the Gram Panchayat/Municipality, in a manner that the applied site gets 
															connected to existing public rasta of atleast 4 karam width?"></spring:message>
												</label>
												<div class="col-sm-7">
													<label class="radio-inline"> <form:radiobutton
															path="licenseApplicationMasterDTO.landCategoryDTO.e1AppOLGM" value="Yes" id="connectedExistingPublicYes" name="connectedExistingPublic" disabled="true"/>
														<spring:message code="" text="Yes" />
													</label> <label class="radio-inline"> <form:radiobutton
															path="licenseApplicationMasterDTO.landCategoryDTO.e1AppOLGM" value="No" id="connectedExistingPublicNo" name="connectedExistingPublic" disabled="true"/>
														<spring:message code="" text="No" />
													</label>
												</div>
											</div>
										</div>
										
										
									</div>
									<div id="category2Div">
										<div class="form-group">
										<label class="col-sm-2 sub-title control-label"><spring:message
												code="" text="(a) Enter Width in Meters" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text"
												class="form-control"
												path="licenseApplicationMasterDTO.landCategoryDTO.cat2Width" id="cat2Width"
												maxlength="20" disabled="true"/>
										</div>
										</div>
										
										<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="(b) Whether irrevocable consent from such developer/ colonizer for uninterrupted usage of such internal 
														road for the purpose of development of the colony by the applicant or by its agencies and for usage by its allottees submitted "></spring:message>
											</label>
											<div class="col-sm-7">
												<label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.cat2irrv" value="Yes" id="developerConsentYes" name="developerConsent" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.landCategoryDTO.cat2irrv" value="No" id="developerConsentNo" name="developerConsent" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
				
										<div id="developerConsentDiv">
										
											<div class="form-group">
													<c:if test="${not empty command.usageAllotteesDocumentList}">
														<c:forEach items="${command.usageAllotteesDocumentList}"
															var="uLookUp" varStatus="lk">
															<label class="col-sm-2 control-label" for=""><spring:message
																	code="" text="${uLookUp.clmDescEngl}" /></label>
															<div class="col-sm-4">
																<c:set var="filePath"
																	value="${uLookUp.attPath}" /> <c:set var="path"
																	value="${fn:replace(filePath,search,replace)}" />
																<c:if test="${not empty path}">
																	<button type="button"
																		class="button-input btn btn-blue-2" name="button"
																		value="VIEW"
																		onclick="downloadFileInTag('${path}${replace}${uLookUp.attFname}','AdminHome.html?Download','','')">
																		<spring:message code="" text="VIEW" />
																	</button>
																</c:if>
															</div>
														</c:forEach>
													</c:if>
												</div>
				
										</div>
				
										<div class="form-group">
											<label class="control-label col-sm-5 required-control"> <spring:message
													code=""
													text="Access from NH/SR"></spring:message>
											</label>
											<div class="col-sm-7">
												<label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.accessNHSRFlag" value="Yes" id="accessNHSRYes" name="accessNHSR" class="accessNHSRClass" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton
														path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.accessNHSRFlag" value="No" id="accessNHSRNo" name="accessNHSR" class="accessNHSRClass" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
										</div>
										
										
										
										
									</div>
				
									<div id="accessNHSRDiv">
										<div class="form-group">
												<c:if test="${not empty command.accessNHSRDocumentList}">
													<c:forEach items="${command.accessNHSRDocumentList}"
														var="nLookUp" varStatus="lk">
														<label class="col-sm-2 control-label" for=""><spring:message
																code="" text="${nLookUp.clmDescEngl}" /></label>
														<div class="col-sm-4">
															<c:set var="filePath" value="${nLookUp.attPath}" />
															<c:set var="path"
																value="${fn:replace(filePath,search,replace)}" />
															<c:if test="${not empty path}">
																<button type="button"
																	class="button-input btn btn-blue-2" name="button"
																	value="VIEW"
																	onclick="downloadFileInTag('${path}${replace}${nLookUp.attFname}','AdminHome.html?Download','','')">
																	<spring:message code="" text="VIEW" />
																</button>
															</c:if>
														</div>
													</c:forEach>
												</c:if>
											</div>
									</div>
				
									<h4>
										<spring:message code="" text="5. Details of proposed approach." />
									</h4>
									
									<div class="form-group">
										<label class="control-label col-sm-5 required-control"> <spring:message
												code=""
												text="(a) Site approachable from proposed sector road/ Development Plan Road"></spring:message>
										</label>
										<div class="col-sm-7">
											<label for="approach1" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSR" value="Yes" disabled="true" id="siteApproachableYes" name="siteApproachable" /> <spring:message code="" text="Yes" />
											</label>
											<label for="approach2" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSR" value="No" disabled="true" id="siteApproachableNo" name="siteApproachable" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
									
									<div id="siteApproachableDiv">
										<div class="form-group">
										
										<label class="col-sm-2 sub-title control-label" for="appNo"><spring:message
												code="" text="(a) Enter Width in Meters" /></label>
										<div class="col-sm-2">
											<form:input name="" type="text"
												class="form-control"
												path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.aPaSiteAprSrWid" id="widthMtrs"
												maxlength="20"
												disabled="true" />
										</div>
										
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(b) Whether acquired?"></spring:message>
										</label>
										<div class="col-sm-2">
											<label class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSrAcq" value="Yes" disabled="true" id="whetherAcquiredYes" name="whetherAcquired" /> <spring:message code="" text="Yes" />
											</label>
											<label class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSrAcq" value="No" disabled="true" id="whetherAcquiredNo" name="whetherAcquired" /> <spring:message
													code="" text="No" />
											</label>
										</div>
										
										<label class="control-label col-sm-2 required-control"> <spring:message
												code=""
												text="(c) Whether constructed?"></spring:message>
										</label>
										<div class="col-sm-2">
											<label class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSrCons" value="Yes" disabled="true" id="whetherConstructedYes" name="whetherConstructed" /> <spring:message code="" text="Yes" />
											</label>
											<label class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSrCons" value="No" disabled="true" id="whetherConstructedNo" name="whetherConstructed" /> <spring:message
													code="" text="No" />
											</label>
										</div>
										
										</div>
				
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"> <spring:message
													code=""
													text="(d) Whether Service road along sector road acquired?"></spring:message>
											</label>
											<div class="col-sm-4">
												<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.dPaSiteAprSrSRA"
														value="Y" id="sectorRoadAcquiredYes" name="sectorRoadAcquired" disabled="true"/>
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.dPaSiteAprSrSRA"
														value="N" id="sectorRoadAcquiredNo" name="sectorRoadAcquired" disabled="true"/>
													<spring:message code="" text="No" />
												</label>
											</div>
				
											<label class="control-label col-sm-2 required-control"> <spring:message
													code=""
													text="(e) Whether Service road along sector road constructed?"></spring:message>
											</label>
											<div class="col-sm-4">
												<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.ePaSiteAprSrSRC"
														value="Yes" id="sectorRoadConstructedYes"
														name="sectorRoadConstructed" disabled="true"/> <spring:message code=""
														text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.ePaSiteAprSrSRC"
														value="No" id="sectorRoadConstructedNo"
														name="sectorRoadConstructed" disabled="true"/> <spring:message code=""
														text="No" />
												</label>
											</div>
										</div>
				
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-5 required-control"> <spring:message
												code=""
												text="(b) Site approachable from internal circulation / sectoral plan road."></spring:message>
										</label>
										<div class="col-sm-7">
											<label for="approach1" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSPR" value="Yes" disabled="true" id="internalCirculationYes" name="internalCirculation" /> <spring:message code="" text="Yes" />
											</label>
											<label for="approach2" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSPR" value="No" disabled="true" id="internalCirculationNo" name="internalCirculation" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
				
									<div id="internalCirculationDiv">
										<div class="form-group">
				
											<label class="col-sm-2 sub-title control-label" for="appNo"><spring:message
													code="" text="(a) Enter Width in Meters" /></label>
											<div class="col-sm-2">
												<form:input name="" type="text" class="form-control" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.aPaSiteAprSprWid"
													id="internalCirculationwidthMtrs" maxlength="1" disabled="true" />
											</div>
				
											<label class="control-label col-sm-2 required-control"> <spring:message
													code="" text="(b) Whether acquired?"></spring:message>
											</label>
											<div class="col-sm-2">
												<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSprAcq"
														value="Yes" disabled="true" id="internalCirculationAcquiredYes" name="internalCirculationAcquired" /> <spring:message
														code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSprAcq"
														value="No" disabled="true" id="internalCirculationAcquiredNo" name="internalCirculationAcquired" /> <spring:message
														code="" text="No" />
												</label>
											</div>
				
											<label class="control-label col-sm-2 required-control"> <spring:message
													code="" text="(c) Whether constructed?"></spring:message>
											</label>
											<div class="col-sm-2">
												<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSprCons"
														value="Yes" id="internalCirculationConstructedYes" disabled="true" name="internalCirculationConstructed" />
													<spring:message code="" text="Yes" />
												</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSprCons"
														value="No" id="internalCirculationConstructedNo" disabled="true" name="internalCirculationConstructed" />
													<spring:message code="" text="No" />
												</label>
											</div>
				
										</div>
				
									</div>
				
									<div class="form-group">
										<label class="control-label col-sm-5 required-control"> <spring:message
												code=""
												text="(c) Any other type of existing approach available"></spring:message>
										</label>
										<div class="col-sm-7">
											<label for="approach1" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther" value="Yes" disabled="true" id="approachAvailableYes" name="approachAvailable" /> <spring:message code="" text="Yes" />
											</label>
											<label for="approach2" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther" value="No" disabled="true" id="approachAvailableNo" name="approachAvailable" /> <spring:message
													code="" text="No" />
											</label>
										</div>
									</div>
									
									<div id="approachAvailableDiv">
										<div class="form-group">
												<c:if test="${not empty command.existingApproachDocumentList}">
													<c:forEach items="${command.existingApproachDocumentList}"
														var="pLookUp" varStatus="lk">
														<label class="col-sm-2 control-label" for=""><spring:message
																code="" text="${pLookUp.clmDescEngl}" /></label>
														<div class="col-sm-4">
															<c:set var="filePath" value="${pLookUp.attPath}" />
															<c:set var="path"
																value="${fn:replace(filePath,search,replace)}" />
															<c:if test="${not empty path}">
																<button type="button"
																	class="button-input btn btn-blue-2" name="button"
																	value="VIEW"
																	onclick="downloadFileInTag('${path}${replace}${pLookUp.attFname}','AdminHome.html?Download','','')">
																	<spring:message code="" text="VIEW" />
																</button>
															</c:if>
														</div>
													</c:forEach>
												</c:if>
											</div>
									</div>
									
									<h4>
										<spring:message code="" text="6. Site Condition" />
									</h4>
									
									<div class="form-group">
										<label class="col-sm-3 control-label required-control"
											for=""> <spring:message code="" text="(a) Vacant" /></label>
										<div class="col-sm-3">
											<label for="revenue" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac" value="Yes" id="vacantYes" disabled="true" name="vacant"
													 /> <spring:message code="" text="Yes" />
											</label> <label for="revenue" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac" value="No" id="vacantNo" disabled="true" name="vacant" /> <spring:message
													code="" text="No" />
											</label>
											
										</div>
				
										<label class="col-sm-2 control-label" for="name"> <spring:message
													code="" text="Vacant Remark" /></label>
										<div class="col-sm-4 ">
											<form:textarea id="vacantRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVacRemmark"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label required-control"
											for=""> <spring:message code="" text="(b) HT line" /></label>
										<div class="col-sm-3">
											<label for="htLine" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scHtLine" value="Yes" id="htLineYes" name="htLine"
													disabled="true" /> <spring:message code="" text="Yes" />
											</label> <label for="htLine" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scHtLine" value="No" id="htLineNo" disabled="true" name="htLine" /> <spring:message
													code="" text="No" />
											</label>
											
										</div>
				
										<label class="col-sm-2 control-label required-control"> <spring:message
													code="" text="HT Line Remark" /></label>
										<div class="col-sm-4 ">
											<form:textarea id="htLineRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scHtLineRemark"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true" />
										</div>
									</div>
									
									
									<div class="form-group">
										<label class="col-sm-3 control-label required-control"
											for=""> <spring:message code="" text="(c) IOC Gas Pipeline" /></label>
										<div class="col-sm-3">
											<label for="gasPipeline" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scIocGasPline" disabled="true" value="Yes" id="gasPipelineYes" name="gasPipeline"
													 /> <spring:message code="" text="Yes" />
											</label> 
											<label for="gasPipeline" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scIocGasPline" disabled="true" value="No" id="gasPipelineNo" name="gasPipeline" /> <spring:message
													code="" text="No" />
											</label>
											
										</div>
				
										<label class="col-sm-2 control-label required-control"> <spring:message
													code="" text="IOC Gas Pipeline Remark" /></label>
										<div class="col-sm-4 ">
											<form:textarea id="gasPipelineRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scIocGasPlineRemark"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label required-control"
											for=""> <spring:message code="" text="(d) Nallah" /></label>
										<div class="col-sm-3">
											<label for="nallah" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scNallah" value="Yes" disabled="true" id="nallahYes" name="nallah"
												 /> <spring:message code="" text="Yes" />
											</label> 
											<label for="nallah" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scNallah" value="No" disabled="true" id="nallahNo" name="nallah" /> <spring:message
													code="" text="No" />
											</label>
											
										</div>
				
										<label class="col-sm-2 control-label required-control"> <spring:message
													code="" text="Nallah Remark" /></label>
										<div class="col-sm-4 ">
											<form:textarea id="nallahRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scNallahRemark"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true"/>
										</div>
									</div>
									
									
									<div class="form-group">
										<label class="col-sm-3 control-label required-control"
											for=""> <spring:message code="" text="(e) Utility/Permit Line" /></label>
										<div class="col-sm-3">
											<label for="permitLine" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLine" value="Yes" disabled="true" id="permitLineYes" name="permitLine"
												 /> <spring:message code="" text="Yes" />
											</label> 
											<label for="nallah" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLine" value="No" disabled="true" id="permitLineNo" name="permitLine" /> <spring:message
													code="" text="No" />
											</label>
											
										</div>
				
										<label class="col-sm-2 control-label required-control"> <spring:message
													code="" text="Utility/Permit Line Remark" /></label>
										<div class="col-sm-4 ">
											<form:textarea id="permitLineRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineRemark"
													class="form-control mandColorClass" data-rule-maxLength="200"
													data-rule-required="true" disabled="true" />
										</div>
									</div>
									
									<div id="permitLineDiv">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control" for=""><spring:message
												code="" text="Width of Row (in ft.)" /></label>
										<div class="col-sm-4 ">
											<form:input name="" type="text"
												class="form-control"
												path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineWidth" id="permitLineWidth"
												maxlength="20" disabled="true" />
										</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label required-control"
											for=""> <spring:message code="" text="(f) Whether other land falls within the applied land" /></label>
										<div class="col-sm-3">
											<label  class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineAL" value="Yes" disabled="true" id="fallsWithinAppliedLandYes" name="fallsWithinAppliedLand"
												 /> <spring:message code="" text="Yes" />
											</label> 
											<label class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineAL" value="No" disabled="true" id="fallsWithinAppliedLandNo" name="fallsWithinAppliedLand" /> <spring:message
													code="" text="No" />
											</label>
											
										</div>
				
										<label class="col-sm-2 control-label required-control"> <spring:message
												code="" text="Remark" /></label>
										<div class="col-sm-4 ">
											<form:textarea id="fallsWithinAppliedLandRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineALRemark"
												class="form-control mandColorClass" data-rule-maxLength="200"
												data-rule-required="true" disabled="true"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label required-control"
											for=""> <spring:message code="" text="(g) Any other feature passing through site" /></label>
										<div class="col-sm-3">
											<label for="featurePassing" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeature" value="Yes"  disabled="true" id="featurePassingYes" name="featurePassing"
												 /> <spring:message code="" text="Yes" />
											</label> 
											<label for="featurePassing" class="radio-inline"> <form:radiobutton
													path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeature" value="No"  disabled="true" id="featurePassingNo" name="featurePassing" /> <spring:message
													code="" text="No"/>
											</label>
											
										</div>
										
									</div>
				
									<div id="featurePassingDiv">
										<div class="form-group">
											`<label class="col-sm-2 control-label required-control" for=""><spring:message
													code="" text="Details thereof" /></label>
											<div class="col-sm-4 ">
												<form:input name="" type="text" class="form-control" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeatureDet"
													id="detailsthereof" maxlength="20" disabled="true" />
											</div>
										</div>
									</div>
										<div>
											<c:if test="${not empty command.landScheduleDocumentList}">

												<div id="checkListDetails">
													<div class="panel-body">

														<div class="overflow margin-top-10 margin-bottom-10">
															<div class="table-responsive">
																<table
																	class="table table-hover table-bordered table-striped">
																	<tbody>
																		<c:set var="c" value="0" scope="page" />
																		<tr>
																			<th><spring:message code="water.serialNo"
																					text="Sr No" /></th>
																			<th><spring:message code="water.docName"
																					text="Document Name" /></th>
																			<th width="500"><spring:message code=""
																					text="Download" /></th>
																		</tr>

																		<c:forEach items="${command.landScheduleDocumentList}"
																			var="lookUp" varStatus="lk">

																			<tr>
																				<td>${lookUp.clmSrNo}</td>
																				<c:choose>
																					<c:when
																						test="${userSession.getCurrent().getLanguageId() eq 1}">
																						<td>${lookUp.clmDescEngl}</td>
																					</c:when>
																					<c:otherwise>
																						<td>${lookUp.clmDesc}</td>
																					</c:otherwise>

																				</c:choose>

																				<td class="text-center"><c:set var="filePath"
																					value="${lookUp.attPath}" /> <c:set var="path"
																					value="${fn:replace(filePath,search,replace)}" />
																				<c:if test="${not empty path}">
																					<button type="button"
																						class="button-input btn btn-blue-2" name="button"
																						value="VIEW"
																						onclick="downloadFileInTag('${path}${replace}${lookUp.attFname}','AdminHome.html?Download','','')">
																						<spring:message code="" text="VIEW" />
																					</button>
																				</c:if>
																				</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>

													</div>
												</div>
											</c:if>
										</div>

									</div>
							</div>
						</div>
					</div>
					
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<h4 class="panel-title">
								<a data-target="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a4"><spring:message
										code=""
										text="Details of Applied Land" /></a>
							</h4>
							<div id="a4" class="panel-collapse collapse ">
								<div class="panel-body">								
										<div class="form-group">
											<c:if test="${not empty command.DGPSDocumentList}">
												<c:forEach items="${command.DGPSDocumentList}" var="dgLookUp"
													varStatus="lk">
													<label class="col-sm-2 control-label" for=""><spring:message
															code="" text="${dgLookUp.clmDescEngl}" /></label>
													<div class="col-sm-4">
														<c:set var="filePath" value="${dgLookUp.attPath}" />
														<c:set var="path"
															value="${fn:replace(filePath,search,replace)}" />
														<c:if test="${not empty path}">
															<button type="button" class="button-input btn btn-blue-2"
																name="button" value="VIEW"
																onclick="downloadFileInTag('${path}${replace}${dgLookUp.attFname}','AdminHome.html?Download','','')">
																<spring:message code="" text="VIEW" />
															</button>
														</c:if>
													</div>
												</c:forEach>
											</c:if>
										</div>
										
											<h4>
												<spring:message
														code="" text="Bifurcation Of Component of type of Licence" />
											</h4>
											
											<div class="form-group">
												<label class="col-sm-2 control-label " ><spring:message
														code="" text="Total Applied Area (in acres)" /></label>
												<div class="col-sm-4 ">
													<input name="" type="text"
														class="form-control alphaNumeric preventSpace required-control"
														path="" id="licFee"
														maxlength="20"
														value="${command.licenseApplicationMasterDTO.ciTotArea}"
														disabled="true" />
													
												</div>
											</div>
											
											
											<spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${command.licenseApplicationMasterDTO.appPLicPurposeId},${userSession.getCurrent().getOrganisation().getOrgid()})"
													var="purposeType" />
											
											<div class="form-group">
												<label class="col-sm-2 control-label sub-title" ><spring:message
														code="" text="Purpose type" /></label>
												<div class="col-sm-4 ">
													<input name="" type="text"
														class="form-control alphaNumeric preventSpace required-control"
														path="" id="purpsoeDesc"
														maxlength="20"
														value="${purposeType.lookUpDesc}"
														disabled="true" />
													
												</div>
											</div>
											
											
											
												
												
											<table
												class="table table-bordered  table-condensed margin-bottom-10"
												id="itemDetails">
												<c:set var="d" value="0" scope="page" />
												<thead>
						
													<tr>
														<th width=""><spring:message code="" text="Component" /><i
															class="text-red-1">*</i></th>
														<th width=""><spring:message code="" text="Sub Component" /><i
															class="text-red-1">*</i></th>
														<th width=""><spring:message code="" text="FAR" /><i
															class="text-red-1">*</i></th>
														<th width=""><spring:message code="" text="Area(in acres)" /><i
															class="text-red-1">*</i></th>
						
													</tr>
												</thead>
												<c:choose>
													<c:when
														test="${empty command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList}">
														<tbody>
						
						
															<tr class="itemDetailClass">
						
						
						
																<%-- <form:hidden
																	path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].purposeId"
																	id="purposeId${d+1}" /> --%>
						
																<td><form:select
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose2"
																		cssClass="form-control mandColorClass" id="subpurpose1${d}"
																		onchange="getSubpurpose2(${d})">
																		<form:option value="">
																			<spring:message code='master.selectDropDwn' />
																		</form:option>
																		<c:forEach items="${command.suPurposeList}" var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td class="subPurpose2${d}"><form:select
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose3"
																		cssClass="form-control mandColorClass" id="subpurpose2${d}">
																		<form:option value="">
																			<spring:message code='master.selectDropDwn' />
																		</form:option>
																		<c:forEach items="${command.suPurpose2List}" var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select> <input type="hidden" id="selSubPurpose2${d}"
																	value="${command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[d].applicationPurpose3}" />
																</td>
						
																<td><c:set var="baseLookupCode" value="FAR" /> <form:select
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].far"
																		cssClass="form-control mandColorClass" id="far${d}">
																		<form:option value="">
																			<spring:message code='master.selectDropDwn' />
																		</form:option>
																		<c:forEach items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>
						
																<td><form:input
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].area"
																		type="text" disabled=""
																		class="form-control unit required-control  preventSpace"
																		maxLength="100" id="area${d}" /></td>
						
																
						
						
															</tr>
															<c:set var="d" value="${d + 1}" scope="page" />
						
														</tbody>
						
													</c:when>
													<c:otherwise>
														<tbody>
															<c:forEach var="dataList"
																items="${command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList}"
																varStatus="status">
																<tr class="itemDetailClass">
																<td><form:select
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose2"
																		cssClass="form-control mandColorClass" id="subpurpose1${d}"
																		onchange="getSubpurpose2(${d})" disabled="true">
																		<form:option value="">
																			<spring:message code='master.selectDropDwn' />
																		</form:option>
																		<c:forEach items="${command.suPurposeList}" var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td class="subPurpose2${d}"><form:select
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose3"
																		cssClass="form-control mandColorClass" id="subpurpose2${d}" disabled="true">
																		<form:option value="">
																			<spring:message code='master.selectDropDwn' />
																		</form:option>
																		<c:forEach items="${command.suPurpose2List}" var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select> <input type="hidden" id="selSubPurpose2${d}"
																	value="${command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[d].applicationPurpose3}" />
																</td>

																<td><c:set var="baseLookupCode" value="FAR" /> <form:select
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].far"
																		cssClass="form-control mandColorClass" id="far${d}"
																		disabled="true">
																		<form:option value="">
																			<spring:message code='master.selectDropDwn' />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>

															<td><form:input
																		path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].area"
																		type="text" disabled="true"
																		class="form-control unit required-control  preventSpace"
																		maxLength="100" id="area${d}" /></td>
						
																
						
						
																</tr>
																<c:set var="d" value="${d + 1}" scope="page" />
															</c:forEach>
														</tbody>
													</c:otherwise>
												</c:choose>
											</table>							
										<div>
											<c:if test="${not empty command.appLandDocumentList}">

												<div id="checkListDetails">
													<div class="panel-body">

														<div class="overflow margin-top-10 margin-bottom-10">
															<div class="table-responsive">
																<table
																	class="table table-hover table-bordered table-striped">
																	<tbody>
																		<c:set var="c" value="0" scope="page" />
																		<tr>
																			<th><spring:message code="water.serialNo"
																					text="Sr No" /></th>
																			<th><spring:message code="water.docName"
																					text="Document Name" /></th>
																			<th width="500"><spring:message code=""
																					text="Download" /></th>
																		</tr>

																		<c:forEach items="${command.appLandDocumentList}"
																			var="lookUp" varStatus="lk">

																			<tr>
																				<td>${lookUp.clmSrNo}</td>
																				<c:choose>
																					<c:when
																						test="${userSession.getCurrent().getLanguageId() eq 1}">
																						<td>${lookUp.clmDescEngl}</td>
																					</c:when>
																					<c:otherwise>
																						<td>${lookUp.clmDesc}</td>
																					</c:otherwise>

																				</c:choose>

																				<td class="text-center"><c:set var="filePath"
																					value="${lookUp.attPath}" /> <c:set var="path"
																					value="${fn:replace(filePath,search,replace)}" />
																				<c:if test="${not empty path}">
																					<button type="button"
																						class="button-input btn btn-blue-2" name="button"
																						value="VIEW"
																						onclick="downloadFileInTag('${path}${replace}${lookUp.attFname}','AdminHome.html?Download','','')">
																						<spring:message code="" text="VIEW" />
																					</button>
																				</c:if>
																				</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>

													</div>
												</div>
											</c:if>
										</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a5"><spring:message
										code=""
										text="Fees and Charges" /></a>
							</h4>
							<div id="a5" class="panel-collapse collapse ">
								<div class="panel-body">
									<h4>
										<spring:message code="" text="Component wise Fee/Charges" />
									</h4>


									<div class="overflow margin-top-10">
										<div class="table-responsive">
											<table class="table table-bordered table-wrapper">
												<thead>
													<tr>
														<th class="text-left"><spring:message code=""
																text="Fee" /></th>
														<th class="text-left"><spring:message code=""
																text="Amount(in ₹)" /></th>
														<th class="text-left"><spring:message code=""
																text="Calculations" /></th>
													</tr>
												</thead>
												<tbody>
													<c:if
														test="${not empty command.licenseApplicationMasterDTO.feeMasterDto}">
														<c:forEach var="feeMasterDto"
															items="${command.licenseApplicationMasterDTO.feeMasterDto}"
															varStatus="status">
															<tr>
																<td><form:input
																		path="licenseApplicationMasterDTO.feeMasterDto[${status.index}].taxCategory"
																		value="" class="form-control" disabled="true"
																		id="dinNumberId${status.index}" minlength="20"
																		maxlength="10" /></td>
																<td class=""><form:input
																		path="licenseApplicationMasterDTO.feeMasterDto[${status.index}].feesStr"
																		value="" class="form-control text-right"
																		disabled="true" id="dinNumberId${status.index}"
																		minlength="20" maxlength="10" /></td>
																<td><form:input
																		path="licenseApplicationMasterDTO.feeMasterDto[${status.index}].calculation"
																		value="${not empty feeMasterDto.calculation ? feeMasterDto.calculation : ''}"
																		class="form-control hasNumber" disabled="true"
																		id="dinNumberId${status.index}" minlength="20"
																		maxlength="10" /></td>
															</tr>
														</c:forEach>
													</c:if>
												</tbody>
											</table>
										</div>
									</div>

								</div>

									<div class="form-group">
										<label class="col-sm-2 control-label sub-title"><spring:message
												code="" text="Scruitny Fee (100%)" /></label>

										<div class="col-sm-2 ">
											<div class="input-group">
												<form:input name="" type="text"
													class="form-control alphaNumeric preventSpace required-control text-right"
													path="licenseApplicationMasterDTO.scrutinyFeeStr"
													id="scrutinyFee" value="" maxlength="20" disabled="true" />
												<label class="input-group-addon"><i
													class="fa fa-inr"></i><span class="hide"><spring:message
															code="" text="Rupees" /></span></label>
											</div>
										</div>

										<label class="col-sm-2 control-label sub-title"><spring:message
												code="" text="Licence Fees (25%)" /></label>

										<div class="col-sm-2 ">
											<div class="input-group">
												<form:input name="" type="text"
													class="form-control alphaNumeric preventSpace required-control text-right"
													path="licenseApplicationMasterDTO.licenseFeeStr" id="licFee"
													maxlength="20" disabled="true" />
												<label class="input-group-addon"><i
													class="fa fa-inr"></i><span class="hide"><spring:message
															code="" text="Rupees" /></span></label>
											</div>
										</div>

										<label style="" class="col-sm-2 control-label sub-title "><spring:message
												code="" text="Total Payable" /></label>
										<div class="col-sm-2 ">
											<div class="input-group">
												<form:input name="" type="text"
													class="form-control alphaNumeric preventSpace required-control text-right"
													path="licenseApplicationMasterDTO.totalFeesStr" id="totalFee"
													maxlength="20" disabled="true" />
												<label class="input-group-addon"><i
													class="fa fa-inr"></i><span class="hide"><spring:message
															code="" text="Rupees" /></span></label>
											</div>
										</div>
										<div class="text-center padding-top-20 margin-top-30">
											<button type="button" class="btn btn-blue-2 padding-top-10"
												id='btnPrint' onclick="onlinePaymentReceipt()">
												<spring:message code="" text="View Receipt" />
											</button>
										</div>
									</div>

								</div>
							</div>
					</div>

					<%-- <div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<h4 class="panel-title">
								<a data-target="#a6" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a6"><spring:message
										code="" text="Field Label Scrutiny (Role Wise)" /></a>
							</h4>
							<div id="a6" class="panel-collapse collapse in"> --%>
								<div class="panel-body">
									<div class="padding-bottom-10">
										<jsp:include page="/jsp/cfc/sGrid/sGridLebalView.jsp"></jsp:include>
									</div>
								</div>
							<!-- </div>
						</div>
					</div> -->

					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>