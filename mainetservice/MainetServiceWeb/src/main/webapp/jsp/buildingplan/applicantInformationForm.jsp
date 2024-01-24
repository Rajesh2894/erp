<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/buildingplan/developerRegForm.js"></script>

<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantInfoForm"
					action="DeveloperRegistrationForm.html" method="post"
					class="form-horizontal">
					<form:hidden path="" value="${command.developerRegistrationDTO.directorInfoFlag}" id="directorInfoFlagId"/>
					<form:hidden path="developerRegistrationDTO.indPanVerifiedFlag" id="indPanVerifiedFlagId"/>
					<form:hidden path="developerRegistrationDTO.companyDetailsAPIFlag" id="companyDetailsAPIFlagId"/>
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div
						class="IND-success-div alert alert-success alert-dismissible margin-bottom-20" style="display: none;">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<ul>
							<li><spring:message code="" text="PAN Details Verified" /></li>
						</ul>
					</div>

					<h4>
						<spring:message code="developer.type" text="Developer's Type" />
					</h4>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="select.developer.type" text="Developer Type" />
						</label>
						<div class="col-sm-4">
							<form:select path="developerRegistrationDTO.devType"
								class="form-control" id="devType">
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
									id="cinNoId" maxlength="21" minlength="21" />
							</div>
						</div>
						<div id="LLPDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="llp.number" text="LLP Number" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.llpNo" id="LLpNo" maxlength="21"
									minlength="21" />
							</div>
						</div>
						<div id="companyDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="company.name" text="Company Name" /></label>
									
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.companyName" id="companyName"
									maxlength="50" minlength="2" />
							</div>

						</div>
						
						<div id="firmNameDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="firm.name" text="Firm Name" /></label>
											
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.firmName" id="firmNameId"
									maxlength="50" minlength="2" />
							</div>

						</div>

						<div id="dateOfIncorporation">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="date.of.incorporation" text="Date of Incorporation" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input type="text" class="form-control licenseDate"
										id="dateOfIncorporationId"
										path="developerRegistrationDTO.dateOfIncorporation"
										placeholder="DD/MM/YYYY" />
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
									minlength="2" />
							</div>
						</div>
						<div id="genderDiv">
							<label class="col-sm-2 control-label required-control"><spring:message
								code="gender.label" text="Gender" /></label>
							<div class="col-sm-2">
								<form:select path="developerRegistrationDTO.gender"
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
									<form:input type="text" class="form-control DOB"
										id="dobId" path="developerRegistrationDTO.dateOfBirth" onchange="verifyIndPanDetails()"
										placeholder="DD/MM/YYYY" />
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
									id="registeredAddress" maxlength="100" minlength="2" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="email.label" text="Email" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text"
									class="form-control hasemailclass mandClassColor"
									path="developerRegistrationDTO.email" id="emailId"
									maxlength="50" minlength="8" data-rule-email="true" data-rule-required="true"/>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="mobile.no" text="Mobile No." /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control hasNumber"
									path="developerRegistrationDTO.mobileNo" id="mobNoId"
									maxlength="10" minlength="10" />
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
									minlength="15" />
							</div>
						</div>

						<div id="panNo">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="pan.number" text="PAN" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control hasAlphaNumericClass text-uppercase"
									path="developerRegistrationDTO.panNo" id="panNoId" maxlength="10" minlength="10" onchange="verifyIndPanDetails()"/>
							</div>

							<button type="button" class="button-input btn btn-success"
								name="button" value="Save" onclick="verifyPanNumber(this)"
								id="panIndVerifyBtn">
								<spring:message code="" text="Verify Pan" />
							</button>
						</div>
					</div>

					<div id="stakeholderDiv">

						<h4>
							<spring:message code="shareholding.patterns"
								text="Shareholding Patterns" />
						</h4>

						<table class="table table-bordered table-striped"
							id="stakeholderTable">
							<c:set var="d" value="0" scope="page" />
							<c:set var="ds" value="500" scope="page" />
							<thead>
								<tr>
									<th width="5%"><spring:message code="sr.no" text="Sr No." /></th>
									<th><spring:message code="name.label" text="Name" /><span
										class="mand">*</span></th>
									<th><spring:message code="designation.label"
											text="Designation" /><span class="mand">*</span></th>
									<th><spring:message code="percentage.label"
											text="Percentage'%'" /><span class="mand">*</span></th>
									<th width="20%"><spring:message
											code="any.supportive.document" text="Any Supportive Document" />
									</th>
									<th width="10%"><spring:message code="add.remove"
											text="Add/Remove" /></th>
								</tr>
							</thead>
							<form:hidden path="stkId" id="stkRowId"/>
							<c:choose>
								<c:when
									test="${empty command.developerRegistrationDTO.developerStakeholderDTOList}">
									<tbody>

										<tr class="developerAppendable">

											<td align="center"><input type="text" 
													class="form-control" id="developerId${d}" value="${d+1}"
													readonly="true" /></td>

											<td><form:input
													path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderName"
													class="form-control" id="devloperNameId${d}" minlength="2"
													maxlength="90" /></td>

											<td><form:input
													path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderDesignation"
													class="form-control" id="devloperDesignationId${d}"
													data-rule-required="true" minlength="2" maxlength="20" /></td>

											<td><form:input
													path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderPercentage"
													class="form-control hasNumber"
													id="devloperPercentageId${d}" minlength="1" maxlength="3"
													data-rule-required="true" /></td>

											<td class="text-center"><apptags:formField fieldType="7"
													fieldPath="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderDoc[${d}].uploadedDocumentPath"
													currentCount="${ds}" showFileNameHTMLId="true"
													folderName="stakeholder${ds}" fileSize="CHECK_COMMOM_MAX_SIZE"
													isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
												</apptags:formField> 
												<i style="font-size: 10px; font-weight: bold;"
												class="text-red-1"><spring:message
														code="file.upload.msg"
														text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
												<c:if
													test="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0] ne null  && not empty command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0]}">
													<input type="hidden" name="deleteFileId"
														value="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0].attId}">
													<input type="hidden" name="downloadLink"
														value="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0]}">
													<apptags:filedownload
														filename="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0].attFname}"
														filePath="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0].attPath}"
														actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
												</c:if></td>



											<td class="text-center"><a
												title='<spring:message code="" text="Add"></spring:message>'
												class="btn btn-success btn-sm" onclick="addDevRow($(this),${d})"
												id="devAddButton${d}"><i class="fa fa-plus-circle"></i></a>
												<a
												title="<spring:message code="" text="Delete"></spring:message>"
												class="btn btn-danger btn-sm"
												onclick="deleteDevRow($(this),${d})" id="devDelButton${d}"><i
													class="fa fa-trash-o"></i></a></td>

										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
										<c:set var="ds" value="${ds + 1}" scope="page" />
									</tbody>
								</c:when>
								<c:otherwise>
									<tbody>
										<c:forEach var="dataList"
											items="${command.developerRegistrationDTO.developerStakeholderDTOList}"
											varStatus="status">
											<tr class="developerAppendable">

												<td align="center"><input type="text" 
														class="form-control" id="developerId${d}"
														value="${d+1}" readonly="true" /></td>


												<td><form:input
														path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderName"
														class="form-control" id="devloperNameId${d}" minlength="2"
														maxlength="50" /></td>

												<td><form:input
														path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderDesignation"
														class="form-control" id="devloperDesignationId${d}"
														data-rule-required="true" minlength="2" maxlength="20" /></td>

												<td><form:input
														path="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderPercentage"
														class="form-control hasNumber"
														id="devloperPercentageId${d}" minlength="1" maxlength="3"
														data-rule-required="true" /></td>


												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="developerRegistrationDTO.developerStakeholderDTOList[${d}].stakeholderDoc[0].uploadedDocumentPath"
														currentCount="${ds}" showFileNameHTMLId="true"
														folderName="stakeholder${ds}" fileSize="CHECK_COMMOM_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
													</apptags:formField> 
													<i style="font-size: 10px; font-weight: bold;"
													class="text-red-1"><spring:message
															code="file.upload.msg"
															text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
													<c:if
														test="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0] ne null  && not empty command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0]}">
														<input type="hidden" name="deleteFileId"
															value="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0].attId}">
														<input type="hidden" name="downloadLink"
															value="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0]}">
														<apptags:filedownload
															filename="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0].attFname}"
															filePath="${command.developerRegistrationDTO.developerStakeholderDTOList[d].stakeholderDocsList[0].attPath}"
															actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
													</c:if></td>

												<td class="text-center"><a
													title='<spring:message code="" text="Add"></spring:message>'
													class="addDevRow btn btn-success btn-sm"
													onclick="addDevRow($(this),${d})" id="devAddButton${d}"><i
														class="fa fa-plus-circle"></i></a> <a
													title="<spring:message code="" text="Delete"></spring:message>"
													class="btn btn-danger btn-sm"
													onclick="deleteDevRow($(this),${d})" id="devDelButton${d}"><i
														class="fa fa-trash-o"></i></a></td>

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
											<c:set var="ds" value="${ds + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</c:otherwise>
							</c:choose>
						</table>

						<h4>
							<spring:message code="directors.information"
								text="Directors Information"></spring:message>
						</h4>
						<div id="directorsData">
							<h6>
								<spring:message code="add.directors.info"
									text="Add Directors Info"></spring:message>
							</h6>
							<table class="table table-bordered table-striped"
								id="directorsInfoDataTable">
								<c:set var="d" value="0" scope="page" />
								<thead>
									<tr>
										<th width="5%"><spring:message code="sr.no" text="Sr No." /></th>
										<th><spring:message code="din.number" text="DIN Number" /></th>
										<th width="30%"><spring:message code="name.label" text="Name" /></th>
										<th><spring:message code="contact.number"
												text="Contact Number" /></th>
									</tr>
								</thead>
								<c:if
									test="${not empty command.developerRegistrationDTO.developerDirectorDetailsDTOList}">
									<tbody>
										<c:forEach var="directorList"
											items="${command.developerRegistrationDTO.developerDirectorDetailsDTOList}"
											varStatus="status">
											<tr class="directorDetailAppendable">
												<td align="center" class="disabled">
													${status.count}
												</td>
												<td class="disabled">
													${directorList.dinNumber}
												</td>
												<td class="disabled">
													${directorList.directorName}
												</td>
												<td class="disabled">
													${directorList.directorContactNumber}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</c:if>
							</table>
						</div>
						<div class="form-group">
							<label class="col-sm-8 control-label"><spring:message
									code="director.question.1"
									text="If the directors are in addition/modification to data fetched from the MCA portal, then the complete details of existing directors may be provided."></spring:message></label>
							<div class="col-sm-4">
								<label for="Yes" class="radio-inline"> <form:radiobutton
										class="directorInfoSelect"
										path="developerRegistrationDTO.directorInfoFlag" value="Y"
										id="Yes" /> <spring:message code="" text="Yes"></spring:message></label>
								<label for="No" class="radio-inline"> <form:radiobutton
										class="directorInfoSelect"
										path="developerRegistrationDTO.directorInfoFlag" value="N"
										id="No" /> <spring:message code="" text="No"></spring:message></label>
							</div>
						</div>
						<div id="directorsInfo">
							<h6>
								<spring:message code="add.directors.info"
									text="Add Directors Info"></spring:message>
							</h6>
							<table class="table table-bordered table-striped"
								id="directorsInfoTable">
								<c:set var="d" value="0" scope="page" />
								<c:set var="e" value="100" scope="page" />
								<thead>
									<tr>
										<th width="5%"><spring:message code="sr.no" text="Sr No." /></th>
										<th><spring:message code="din.number" text="DIN Number" /><span
											class="mand">*</span></th>
										<th><spring:message code="name.label" text="Name" /><span
											class="mand">*</span></th>
										<th><spring:message code="contact.number"
												text="Contact Number" /></th>
										<th width="20%"><spring:message
												code="any.supportive.document"
												text="Any Supportive Document" /> </th>
										<th width="10%"><spring:message code="add.remove"
												text="Add/Remove" /></th>
									</tr>
								</thead>
								<form:hidden path="directId" id="directorRowId"/>
								<c:choose>
									<c:when
										test="${empty command.developerRegistrationDTO.developerDirectorInfoDTOList}">
										<tbody>

											<tr class="directorAppendable">

												<td align="center"><input type="text" 
														class="form-control mandColorClass" id="directorId${d}"
														value="${d+1}" disabled="true" /></td>

												<td><form:input
														path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].dinNumber"
														value="" class="form-control hasNumber"
														id="dinNumberId${d}" minlength="10" maxlength="10" /></td>

												<td><form:input
														path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorName"
														class="form-control" id="directorNameId${d}"
														data-rule-required="true" minlength="2" maxlength="50" /></td>

												<td><form:input
														path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorContactNumber"
														class="form-control hasNumber"
														id="directorContactNumberId${d}" minlength="10"
														maxlength="10" data-rule-required="true" /></td>

												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorDoc[${d}].uploadedDocumentPath"
														currentCount="${e}" showFileNameHTMLId="true"
														folderName="directorInfo${e}" fileSize="CHECK_COMMOM_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
													</apptags:formField> 
													<i style="font-size: 10px; font-weight: bold;"
													class="text-red-1"><spring:message
															code="file.upload.msg"
															text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
													<c:if
														test="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0] ne null  && not empty command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0] }">
														<input type="hidden" name="deleteFileId"
															value="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0].attId}">
														<input type="hidden" name="downloadLink"
															value="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0]}">
														<apptags:filedownload
															filename="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0].attFname}"
															filePath="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0].attPath}"
															actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
													</c:if></td>

												<td class="text-center"><a
													title='<spring:message code="" text="Add"></spring:message>'
													class="btn btn-success btn-sm"
													onclick="addDirectorRow(this)" id="directorAddButton${d}"><i
														class="fa fa-plus-circle"></i></a> <a
													title="<spring:message code="" text="Delete"></spring:message>"
													class="btn btn-danger btn-sm"
													onclick="deleteDirectorRow($(this),${d})"
													id="directorDelButton${d}"><i class="fa fa-trash-o"></i></a></td>

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
											<c:set var="e" value="${e + 1}" scope="page" />
										</tbody>
									</c:when>
									<c:otherwise>
										<tbody>
											<c:forEach var="directorList"
												items="${command.developerRegistrationDTO.developerDirectorInfoDTOList}"
												varStatus="status">
												<tr class="directorAppendable">

													<td align="center"><input type="text" 
															class="form-control mandColorClass"
															id="directorId${d}" value="${d+1}" disabled="true" /></td>

													<td><form:input
															path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].dinNumber"
															value="" class="form-control hasNumber"
															id="dinNumberId${d}" minlength="10" maxlength="10" /></td>

													<td><form:input
															path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorName"
															class="form-control" id="directorNameId${d}"
															data-rule-required="true" minlength="2" maxlength="50" /></td>

													<td><form:input
															path="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorContactNumber"
															class="form-control hasNumber"
															id="directorContactNumberId${d}" minlength="10"
															maxlength="10" data-rule-required="true" /></td>


													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="developerRegistrationDTO.developerDirectorInfoDTOList[${d}].directorDoc[${d}].uploadedDocumentPath"
															currentCount="${e}" showFileNameHTMLId="true"
															folderName="directorInfo${e}" fileSize="CHECK_COMMOM_MAX_SIZE"
															isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
														</apptags:formField> 
														<i style="font-size: 10px; font-weight: bold;"
														class="text-red-1"><spring:message
																code="file.upload.msg"
																text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
														<c:if
															test="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0] ne null  && not empty command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0]}">
															<apptags:filedownload
																filename="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0].attFname}"
																filePath="${command.developerRegistrationDTO.developerDirectorInfoDTOList[d].directorDocsList[0].attPath}"
																actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
														</c:if></td>

													<td class="text-center"><a
														title='<spring:message code="" text="Add"></spring:message>'
														class="btn btn-success btn-sm"
														onclick="addDirectorRow(this)" id="directorAddButton${d}"><i
															class="fa fa-plus-circle"></i></a> <a
														title="<spring:message code="" text="Delete"></spring:message>"
														class="btn btn-danger btn-sm"
														onclick="deleteDirectorRow($(this),${d})"
														id="directorDelButton${d}"><i class="fa fa-trash-o"></i></a></td>

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</c:otherwise>
								</c:choose>
							</table>
						</div>

						<div class="form-group">
							<label class="col-sm-8 control-label padding-top-0"><spring:message
									code="license.question1"
									text="In case the Partner/director of the applicant firm/company is common with any existing colonizer who has been granted a licence under the 1975 act Yes/No." /></label>
							<div class="col-sm-4">
								<label for="Yes" class="radio-inline"> <form:radiobutton
										class="licenseInfoDiv"
										path="developerRegistrationDTO.licenseInfoFlag" value="Y"
										id="Yes" /> <spring:message code="" text="Yes"></spring:message></label>
								<label for="No" class="radio-inline"> <form:radiobutton
										class="licenseInfoDiv"
										path="developerRegistrationDTO.licenseInfoFlag" value="N"
										id="No" /> <spring:message code="" text="No"></spring:message></label>
							</div>
						</div>

						<div class="form-group" id="licenseInfo">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="licence.label" text="Licence" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.licenseNo" id="licenseNo"
									minlength="1" maxlength="15" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="name.of.developer" text="Name of Developer" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.devName" id="DevName"
									minlength="1" maxlength="50" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="date.of.grant.of.licence"
									text="Date of grant of a licence" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input type="text" class="form-control licenseDate"
										id="devLicenseDate"
										path="developerRegistrationDTO.devLicenseDate"
										placeholder="DD/MM/YYYY" />
									<label class="input-group-addon"><i
										class="fa fa-calendar"></i><span class="hide">Date</span></label>
								</div>
							</div>
						</div>
					</div>

					<div class="text-center">
						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" onclick="saveApplicantForm(this)"
							id="applicantInfoId">
							<spring:message code="save.next" text="Save & Next" />
						</button>

						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'" name="button"
							value="Back">
							<spring:message code="back.button" text="Back" />
						</button>


					</div>


				</form:form>
			</div>
		</div>
	</div>
</div>