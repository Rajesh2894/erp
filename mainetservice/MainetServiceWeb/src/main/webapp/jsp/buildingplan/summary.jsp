<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/buildingplan/developerRegForm.js"></script>
<style>
.label-val{
	padding-top: 5px;
    margin-bottom: 0;
}

</style>
<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="summaryForm" action="DeveloperRegistrationForm.html"
					method="post" class="form-horizontal">
						<form:hidden path="developerRegistrationDTO.companyDetailsAPIFlag" id="companyDetailsAPIFlagId"/>
						<h4>
							<spring:message code="developer.type" text="Developer's Type" />
						</h4>
						<div class="form-group">
							<label class="col-sm-2 control-label "> <spring:message
									code="select.developer.type" text="Select Developer Type" />
							</label>
							<div class="col-sm-4 label-val">
								${command.developerRegistrationDTO.devTypeDesc}</div>
						</div>

						<h4>
							<spring:message code="" text="Developer Details" />
						</h4>
						<div class="form-group">
							<div id="showCompanyApiHeading" class="padding-left-15">
								<h5>
									<spring:message code="data.from.mca.api"
										text="Data Fetched From MCA API" />
								</h5>
							</div>
							<div id="CINDivSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="cin.number" text="CIN Number" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.cinNo}</div>
							</div>
							<div id="LLPDivSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="llp.number" text="LLP Number" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.llpNo}</div>
							</div>
							<div id="companyDivSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="company.name" text="Company Name" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.companyName}</div>

							</div>
							<div id="firmNameDivSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="firm.name" text="Firm Name" /></label>
	
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.firmName}
								</div>
	
							</div>


						<div id="dateOfIncorporationSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="date.of.incorporation" text="Date of Incorporation" /></label>
								<div class="col-sm-2 label-val">
									<c:set var="dtOfIncorporation"
										value="${command.developerRegistrationDTO.dateOfIncorporation}" />
									<fmt:formatDate pattern="dd/MM/yyyy"
										value="${dtOfIncorporation}" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div id="nameDivSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="name.label" text="Name" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.name}</div>
							</div>
							<div id="genderDivSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="gender.label" text="Gender" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.genderDesc}</div>
							</div>
							<div id="dobDivSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="date.of.birth" text="Date of Birth" /></label>
								<div class="col-sm-2 label-val">
									<c:set var="dtOfBirth"
										value="${command.developerRegistrationDTO.dateOfBirth}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${dtOfBirth}" />
								</div>
							</div>	
						</div>
						<div id="commonDetailsSum">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="registered.address" text="Registered Address" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.registeredAddress}</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="email.label" text="Email" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.email}</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="mobile.no" text="Mobile No." /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.mobileNo}</div>
							</div>
						</div>

						<div class="form-group">
							<div id="gstNoSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="gst.number" text="GST Number" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.gstNo}</div>
							</div>

							<div id="panNoSum">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="pan.number" text="PAN" /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.panNo}</div>
							</div>
						</div>
						<div id="stakeholderDivSum">
							<h4>
								<spring:message code="shareholding.patterns"
									text="Shareholding Patterns" />
							</h4>

							<h4>
								<spring:message code="add.stakeholders" text="Add Stakeholders" />
							</h4>

							<table class="table table-bordered table-striped"
								id="stakeholderTableSum">
								<c:set var="d" value="500" scope="page" />
								<thead>
									<tr>
										<th width="5%"><spring:message code="sr.no" text="Sr No." /></th>
										<th><spring:message code="name.label" text="Name" /></th>
										<th><spring:message code="designation.label"
												text="Designation" /></th>
										<th><spring:message code="percentage.label"
												text="Percentage'%'" /></th>
										<th><spring:message code="any.supportive.document"
												text="Document" /></th>
									</tr>
								</thead>
								<c:if
									test="${not empty command.developerRegistrationDTO.developerStakeholderDTOList}">
									<tbody>

										<c:forEach var="stakeholderList"
											items="${command.developerRegistrationDTO.developerStakeholderDTOList}"
											varStatus="status">
											<tr class="developerAppendable">
												<td align="center">${status.count}</td>

												<td>${stakeholderList.stakeholderName}</td>

												<td>${stakeholderList.stakeholderDesignation}</td>

												<td>${stakeholderList.stakeholderPercentage}</td>

												<td><c:set var="d1" value="${d}doc" scope="page" /> <c:set
														var="stkListData"
														value="${command.developerRegistrationDTO.fileList[d1]}" />
													<c:forEach var="listFileData" items="${stkListData}">
														<apptags:filedownload filename="EIP" eipFileName="${listFileData.name}"
															filePath="${listFileData.path}"
															actionUrl="DeveloperRegistrationForm.html?previewDownload"></apptags:filedownload>
													</c:forEach></td>
											</tr>

											<c:set var="d" value="${d + 1}" scope="page" />

										</c:forEach>
									</tbody>
								</c:if>
							</table>

							<h4>
								<spring:message code="directors.information"
									text="Directors Information"></spring:message>
							</h4>
							<div class="form-group">
								<label class="col-sm-8 control-label"><spring:message
										code="director.question.1"
										text="If the directors are in addition/modification to data fetched from the MCA portal, then the complete details of existing directors may be provided."></spring:message></label>
								<div class="col-sm-4 label-val">
									${command.developerRegistrationDTO.directorInfoFlag}</div>
							</div>
							<div id="directorsInfoSum">
								<h6>
									<spring:message code="add.directors.info"
										text="Add Directors Info"></spring:message>
								</h6>
								<table class="table table-bordered table-striped"
									id="directorsInfoTableSum">
									<c:set var="e" value="100" scope="page" />

									<thead>
										<tr>
											<th width="5%"><spring:message code="sr.no"
													text="Sr No." /></th>
											<th><spring:message code="din.number" text="DIN Number" /></th>
											<th><spring:message code="name.label" text="Name" /></th>
											<th><spring:message code="contact.number"
													text="Contact Number" /></th>
											<th><spring:message code="any.supportive.document"
													text="Any Supportive Document" /></th>
										</tr>
									</thead>
									<c:if
										test="${not empty command.developerRegistrationDTO.developerDirectorInfoDTOList}">
										<tbody>

											<c:forEach var="directorList"
												items="${command.developerRegistrationDTO.developerDirectorInfoDTOList}"
												varStatus="status">
												<tr class="directorAppendable">

													<td align="center">${status.count}</td>

													<td>${directorList.dinNumber}</td>

													<td>${directorList.directorName}</td>

													<td>${directorList.directorContactNumber}</td>

													<td><c:set var="e1" value="${e}doc" scope="page" /> <c:set
															var="directorListData"
															value="${command.developerRegistrationDTO.fileList[e1]}" />
														<c:forEach var="listFileData" items="${directorListData}">
															<apptags:filedownload filename="EIP" eipFileName="${listFileData.name}"
																filePath="${listFileData.path}"
																actionUrl="DeveloperRegistrationForm.html?previewDownload"></apptags:filedownload>
														</c:forEach></td>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</c:if>
								</table>
							</div>

							<div class="form-group">
								<label class="col-sm-10 control-label"><spring:message
										code="license.question1"
										text="In case the Partner/director of the applicant firm/company is common with any existing colonizer who has been granted a licence under the 1975 act Yes/No." /></label>
								<div class="col-sm-2 label-val">
									${command.developerRegistrationDTO.licenseInfoFlag}</div>
							</div>

							<div id="licenseInfoSum">
								<div class="form-group">

									<label class="col-sm-2 control-label "><spring:message
											code="licence.label" text="Licence" /></label>
									<div class="col-sm-2 label-val">
										${command.developerRegistrationDTO.licenseNo}</div>

									<label class="col-sm-2 control-label "><spring:message
											code="name.of.developer" text="Name of Developer" /></label>
									<div class="col-sm-2 label-val">
										${command.developerRegistrationDTO.devName}</div>

									<label class="col-sm-2 control-label "><spring:message
											code="date.of.grant.of.licence"
											text="Date of grant of a licence" /></label>
									<div class="col-sm-2 label-val">
										<c:set var="devLicenseDate"
											value="${command.developerRegistrationDTO.devLicenseDate}" />
										<fmt:formatDate pattern="dd/MM/yyyy" value="${devLicenseDate}" />
									</div>
								</div>
							</div>
						</div>

						<div id="authorizedUserDiv">
							<h4>
								<spring:message code="add.authorised.user"
									text="Add Authorised User" />
							</h4>

							<table class="table table-bordered table-striped"
								id="authorizedUserTableSum">
								<c:set var="a1" value="200" scope="page" />
								<c:set var="a2" value="300" scope="page" />
								<thead>
									<tr>
										<th width="5%"><spring:message code="sr.no" text="Sr No." /></th>
										<th><spring:message code="full.name" text="Full Name" /></th>
										<th><spring:message code="mobile.no" text="Mobile No." /></th>
										<th><spring:message code="email.label" text="Email" /></th>
										<th><spring:message code="gender.label" text="Gender" /></th>
										<th><spring:message code="date.of.birth" text="DOB" /></th>
										<th><spring:message code="pan.number" text="Pan Number" /></th>
										<th><spring:message code="upload.board.resolution"
												text="Upload/View Board Resolution" /></th>
										<th><spring:message code="upload.digital.signature"
												text="Upload/View Digital Signature PDF" /></th>
									</tr>
								</thead>
								<c:if
									test="${not empty command.developerRegistrationDTO.developerAuthorizedUserDTOList}">
									<tbody>
										<c:forEach var="authUserList"
											items="${command.developerRegistrationDTO.developerAuthorizedUserDTOList}"
											varStatus="status">
											<tr class="AuthUserAppendable">

												<td>${status.count}</td>

												<td>${authUserList.authUserName}</td>

												<td>${authUserList.authMobileNo}</td>

												<td>${authUserList.authEmail}</td>

												<td>${authUserList.authGenderDesc}</td>

												<td><c:set var="authDOB"
														value="${authUserList.authDOB}" /> <fmt:formatDate
														pattern="dd/MM/yyyy" value="${authDOB}" /></td>

												<td class="text-uppercase">${authUserList.authPanNumber}</td>

												<td><c:set var="b1" value="${a1}doc" scope="page" /> <c:set
														var="authListData"
														value="${command.developerRegistrationDTO.fileList[b1]}" />
													<c:forEach var="listFileData" items="${authListData}">
														<apptags:filedownload filename="EIP" eipFileName="${listFileData.name}"
															filePath="${listFileData.path}"
															actionUrl="DeveloperRegistrationForm.html?previewDownload"></apptags:filedownload>
													</c:forEach></td>

												<td><c:set var="b2" value="${a2}doc" scope="page" /> <c:set
														var="authDocListData"
														value="${command.developerRegistrationDTO.fileList[b2]}" />
													<c:forEach var="listFileData" items="${authDocListData}">
														<apptags:filedownload filename="EIP" eipFileName="${listFileData.name}"
															filePath="${listFileData.path}"
															actionUrl="DeveloperRegistrationForm.html?previewDownload"></apptags:filedownload>
													</c:forEach></td>

											</tr>
											<c:set var="a1" value="${a1 + 1}" scope="page" />
											<c:set var="a2" value="${a2 + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</c:if>
							</table>
						</div>
						<div id="devCapacity">
							<div>
								<c:if test="${not empty command.checkList}">
									<h4>
										<spring:message code="" text="Document Attachment" />
										<small class="text-blue-2"><spring:message code=""
												text="Only .pdf and jpeg,jpg files allowed" /></small>
									</h4>

									<div id="checkListDetails">
										<div class="panel-body">

											<div class="overflow margin-top-10 margin-bottom-10">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<c:set var="ch" value="0" scope="page" />
															<tr>
																<th><spring:message code="water.serialNo"
																		text="Sr No" /></th>
																<th><spring:message code="water.docName"
																		text="Document Name" /></th>
																<th><spring:message code="water.status"
																		text="Status" /></th>
																<th width="500"><spring:message
																		code="water.uploadText" text="Upload" /></th>
															</tr>

															<c:forEach items="${command.checkList}" var="lookUp"
																varStatus="lk">

																<tr>
																	<td>${lookUp.documentSerialNo}</td>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<td>${lookUp.doc_DESC_ENGL}</td>
																		</c:when>
																		<c:otherwise>
																			<td>${lookUp.doc_DESC_Mar}</td>
																		</c:otherwise>

																	</c:choose>
																	<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																		<td><spring:message code="water.doc.mand" /></td>
																	</c:if>
																	<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																		<td><spring:message code="water.doc.opt" /></td>
																	</c:if>
																	<td><c:set var="chk" value="${ch}doc" scope="page" />
																		<c:set var="chkDoc"
																			value="${command.developerRegistrationDTO.fileList[chk]}" />
																		<c:forEach var="listFileData" items="${chkDoc}">
																			<apptags:filedownload filename="EIP" eipFileName="${listFileData.name}"
																				filePath="${listFileData.path}"
																				actionUrl="DeveloperRegistrationForm.html?previewDownload"></apptags:filedownload>
																		</c:forEach></td>
																</tr>
																<c:set var="ch" value="${ch + 1}" scope="page" />
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>

										</div>
									</div>
								</c:if>
							</div>
							<div class="form-group">
								<label class="col-sm-8 control-label"><spring:message
										code="devloper.question1"
										text="Whether the Developer has earlier been granted permission to set up a colony under HDRU Act, 1975" /></label>
								<div class="col-sm-4 label-val">
									${command.developerRegistrationDTO.licenseHDRUFlag}</div>
							</div>
							<div id="licenseHDRUSum">
								<table class="table table-bordered table-striped"
									id="devLicenseTableSum">
									<thead>
										<tr>
											<th width="5%"><spring:message code="sr.no"
													text="Sr No." /></th>
											<th><spring:message code="licence.label" text="License" /></th>
											<th><spring:message code="date.of.grant.of.licence"
													text="Date of grant of a licence" /></th>
											<th><spring:message code="purpose.of.colony"
													text="Purpose of colony" /></th>
											<th><spring:message code="validity.of.licence"
													text="Validity of licence" /></th>
										</tr>
									</thead>

									<c:if
										test="${not empty command.developerRegistrationDTO.devLicenseHDRUDTOList}">
										<tbody>
											<c:forEach var="devLicenseList"
												items="${command.developerRegistrationDTO.devLicenseHDRUDTOList}"
												varStatus="status">
												<tr class="DevLicense">

													<td>${status.count}</td>

													<td>${devLicenseList.licenseNo}</td>

													<td><c:set var="dateOfGrantLicense"
															value="${devLicenseList.dateOfGrantLicense}" /> <fmt:formatDate
															pattern="dd/MM/yyyy" value="${dateOfGrantLicense}" /></td>

													<td>${devLicenseList.purposeOfColonyDesc}</td>

													<td><c:set var="dateOfValidityLicense"
															value="${devLicenseList.dateOfValidityLicense}" /> <fmt:formatDate
															pattern="dd/MM/yyyy" value="${dateOfValidityLicense}" />
													</td>
												</tr>

											</c:forEach>
										</tbody>
									</c:if>
								</table>

							</div>
							<div id="projectsQueSum">
								<div class="form-group">
									<label class="col-sm-8 control-label"><spring:message
											code="devloper.question2"
											text="Have your company/firm developed projects outside Haryana" /></label>
									<div class="col-sm-4 label-val">
										${command.developerRegistrationDTO.projectsFlag}</div>

								</div>
							</div>
							<div id="licenseProjectSum">
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="name.of.project" text="Name of Project" /></label>
									<div class="col-sm-2 label-val">
										${command.developerRegistrationDTO.projectName}</div>

									<label class="col-sm-2 control-label "><spring:message
											code="name.of.authority" text="Name of Authority " /></label>
									<div class="col-sm-2 label-val">
										${command.developerRegistrationDTO.athorityName}</div>

									<label class="col-sm-2 control-label "><spring:message
											code="status.of.development" text="Status of Development" /></label>
									<div class="col-sm-2 label-val">
										${command.developerRegistrationDTO.devStatus}</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="permission.letter" text="Permission letter" /></label>
									<div class="col-sm-2 label-val">
										<c:set var="f" value="401" scope="page" />
										<c:set var="f1" value="${f}doc" scope="page" />
										<c:set var="permissionDoc"
											value="${command.developerRegistrationDTO.fileList[f1]}" />
										<c:forEach var="listFileData" items="${permissionDoc}">
											<apptags:filedownload filename="EIP" eipFileName="${listFileData.name}"
												filePath="${listFileData.path}"
												actionUrl="DeveloperRegistrationForm.html?previewDownload"></apptags:filedownload>
										</c:forEach>
									</div>

									<label class="col-sm-2 control-label "><spring:message
											code="area.of.the.project.in.acres"
											text="Area of the project in acres" /></label>
									<div class="col-sm-2 label-val">
										${command.developerRegistrationDTO.areaOfProject}</div>

									<label class="col-sm-2 control-label "><spring:message
											code="location" text="Location" /></label>
									<div class="col-sm-2 label-val">
										${command.developerRegistrationDTO.location}</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="any.other.documents" text="Any other documents" /></label>
									<div class="col-sm-2 label-val">
										<c:set var="f2" value="402" scope="page" />
										<c:set var="f3" value="${f2}doc" scope="page" />
										<c:set var="anyDoc"
											value="${command.developerRegistrationDTO.fileList[f3]}" />
										<c:forEach var="listFileData" items="${anyDoc}">
											<apptags:filedownload filename="EIP" eipFileName="${listFileData.name}"
												filePath="${listFileData.path}"
												actionUrl="DeveloperRegistrationForm.html?previewDownload"></apptags:filedownload>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>

						<div class="form-group padding-top-10">
							<div class="col-sm-12">
								<label class="checkbox-inline"><input name=""
									type="checkbox" value="Y" id="acceptTerms"> <strong><spring:message
											code="terms.condition.msg"
											text="It is undertaken that the above information is true and correct for all facts and purposes." /></strong></label>
							</div>
						</div>


						<div class="text-center">
							<button type="button" class="button-input btn btn-success"
								onclick="saveDevloperRegForm(this)">
								<spring:message code="" text="Submit" />
							</button>
							<button type="button" class="button-input btn btn-danger"
								name="button" value="" onclick="showTab('#developerCapacity')">
								<spring:message code="back.button" text="Back" />
							</button>
						</div>
				</form:form>
			</div>
		</div>
	</div>
</div>