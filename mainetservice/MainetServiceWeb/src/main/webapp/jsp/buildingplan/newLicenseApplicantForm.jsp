<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/buildingplan/newTCPLicenseForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script>
$(document)
.ready(
		function() {
			var saveMode =  $('#hiddenSaveMode').val();
			if(saveMode != "A"){
				 $('#confirmCheckbox').prop('checked', true);
				}

			var dateOfIncorporationVal = $('#dateOfIncorporationId').val();
			if (dateOfIncorporationVal) {
				$('#dateOfIncorporationId').val(dateOfIncorporationVal.split(' ')[0]);
			} 
			});

</script>
<style>
.btn-blue-2 {
	background-color: #3c989e;
	color: #fff;
}
#dateOfIncorporation label, #dateOfIncorporation div{
	margin-top:5px;
}
</style>
<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantInfoForm"
					action="NewTCPLicenseForm.html" method="post"
					class="form-horizontal">
					<c:set var="search" value="\\" />
					<c:set var="replace" value="\\\\" />
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<h4>
						<spring:message code="" text="Developer Information" />
					</h4>
					<input type="hidden" value="${command.saveMode}" id="hiddenSaveMode"/>
					<form:hidden path="developerRegistrationDTO.companyDetailsAPIFlag" id="companyDetailsAPIFlagId"/>
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
									<form:input type="text" class="form-control licenseDate"
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
									<form:input type="text" class="form-control DOB"
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
										<%-- <c:forEach var="directorList"
											items="${command.developerRegistrationDTO.developerDirectorInfoDTOList}"
											varStatus="status"> --%>
											<tr class="directorMCAAppendable">

												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass" id="directorMCAId${d}"
														value="${d+1}" disabled="true" /></td>

												<td><form:input
														path=""
														value="" class="form-control hasNumber"
														id="dinNumberId${d}" minlength="10" maxlength="10" disabled="true" /></td>

												<td><form:input
														path=""
														class="form-control" id="directorNameIdMCA${d}"
														data-rule-required="true" minlength="2" maxlength="50" disabled="true" /></td>

												<td><form:input
														path=""
														class="form-control hasNumber"
														id="directorContactNumberIdMCA${d}" minlength="10"
														maxlength="10" data-rule-required="true" disabled="true"/></td>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />

										<%-- </c:forEach> --%>
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
															name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','NewTCPLicenseForm.html?Download','','')
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
														name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','NewTCPLicenseForm.html?Download','','')
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
									name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','NewTCPLicenseForm.html?Download','','')
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
									name="button" value="VIEW" onclick="downloadFileInTag('${path}${replace}${document.attFname}','NewTCPLicenseForm.html?Download','','')
									" >
									<spring:message code="" text="VIEW" />
								</button>
							</c:if>
							<%-- <apptags:filedownload filename="${document.attFname}"
								filePath="${document.attPath}"
								actionUrl="NewTCPLicenseForm.html?Download"></apptags:filedownload> --%>
						</div>
					</div>
					<c:set var="search" value="\\" />
					<c:set var="replace" value="\\\\" />
					<div>
						<c:if test="${not empty command.applicantCheckList}">									
								<h4>
									<spring:message code="" text="Upload Documents" />
									<small class="text-blue-2"><spring:message
													code="" text="Only .pdf and jpeg, jpg, png files allowed"/></small>
								</h4>

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
															<%-- <th><spring:message code="water.status"
																	text="Status" /></th> --%>
															<th width="500"><spring:message
																	code="water.uploadText" text="Upload" />
																<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span></th>
														</tr>

														<c:forEach items="${command.applicantCheckList}" var="lookUp"
															varStatus="lk">

															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if>
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if></td>
																	</c:otherwise>

																</c:choose>															
																<%-- <c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="water.doc.mand" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="water.doc.opt" /></td>
																</c:if> --%>
																<td>
																<c:choose>
																	<c:when test="${command.saveMode ne 'V' }">
																	<div id="applicantDocs_${lk}">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="applicantCheckList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="TCPHR_MAX_FILE_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
																			currentCount="${c}"
																			folderName="applicantCheckList${c}" />
																	</div>
																	</c:when>
																	<c:otherwise>
																	<c:if test="${not empty command.applicantDocumentList[c]}">
																		<c:set var="filePath"
																					value="${command.applicantDocumentList[c].attPath}" /> <c:set var="path"
																					value="${fn:replace(filePath,search,replace)}" />
																				<c:if test="${not empty path}">
																					<button type="button"
																						class="button-input btn btn-blue-2" name="button"
																						value="VIEW"
																						onclick="downloadFileInTag('${path}${replace}${command.applicantDocumentList[c].attFname}','NewTCPLicenseForm.html?Download','','')">
																						<spring:message code="" text="VIEW" />
																					</button>
																				</c:if>
																	</c:if>
																	</c:otherwise>
																</c:choose>
																	
																</td>
															</tr>
															<c:set var="c" value="${c + 1}" scope="page" />
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
						<div class="col-sm-12" style="padding-left: 35px;">
						<input type="checkbox" required="" id="confirmCheckbox" style="margin-top: 4px !important;">
						<label class="required-control check-header" for="ownerTypeId"><spring:message
								code=""
								text="The Information fetched from developer registration is updated" /></label>
						</div>
					</div>



					<div class="text-center">
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save" onclick="saveApplicationForm(this)"
									id="">
									<spring:message code="" text="Next" />
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save" onclick="saveApplicationForm(this)"
									id="">
									<spring:message code="" text="Save & Next" />
								</button>
							</c:otherwise>
						</c:choose>				

						<button type="button" class="btn btn-danger"
							onclick="window.location.href='NewTCPLicenseForm.html'" name="button"
							value="Back">
							<spring:message code="" text="Back" />
						</button>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>