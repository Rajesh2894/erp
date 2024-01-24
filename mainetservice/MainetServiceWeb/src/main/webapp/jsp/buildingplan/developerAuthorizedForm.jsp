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
				<form:form id="authorizedUserForm"
					action="DeveloperRegistrationForm.html" method="post"
					class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden path="panVerifySuccessMsg" id="panVerifySuccessMsgId"/>
			
						<div
							class="success-div alert alert-success alert-dismissible margin-bottom-20">
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<ul>
								<li><spring:message code="" text="PAN Details Verified"/></li>
							</ul>
						</div>


					<h4>
						<spring:message code="add.authorised.user"
							text="Add Authorised User" />
					</h4>
					
					<form:hidden path="isMobileValidation" id="isMobileValidation"/>

					<table class="table table-bordered table-striped"
						id="authorizedUserTable">
						<c:set var="d" value="0" scope="page" />
						<c:set var="d1" value="200" scope="page" />
						<c:set var="d2" value="300" scope="page" />
						<thead>
							<tr>
								<th width="5%"><spring:message code="" text="Sr No." /></th>
								<th><spring:message code="full.name" text="Full Name" /><span
									class="mand">*</span></th>
								<th><spring:message code="mobile.no" text="Mobile No." /><span
									class="mand">*</span></th>
								<th><spring:message code="email.label" text="Email" /><span
									class="mand">*</span></th>
								<th><spring:message code="gender.label" text="Gender" /><span
									class="mand">*</span></th>
								<th><spring:message code="date.of.birth" text="DOB" /><span
									class="mand">*</span></th>
								<th><spring:message code="pan.number" text="Pan Number" /><span
									class="mand">*</span></th>
								<th width="10%"><spring:message code="upload.board.resolution"
										text="Upload/View Board Resolution" /> </th>
								<th width="12%"><spring:message code="upload.digital.signature"
										text="Upload/View Digital Signature PDF" /> </th>
								<th width="15%"><spring:message code="" text="Action" /></th>
							</tr>
						</thead>
						<form:hidden path="authId" id="authUserId"/>
						<c:choose>
							<c:when
								test="${empty command.developerRegistrationDTO.developerAuthorizedUserDTOList}">
								<tr class="AuthUserAppendable">
									<form:hidden path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].panVerifiedFlag" id="panVerifiedFlagId${d}"/>
									<form:hidden path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].panDetailsFlag" id="panDetailsFlagId${d}"/>
									<td><form:input path="" value="${d+1}"
											class="form-control" id="devloperAuthId${d}" />
									</td>
									<td><form:input
											path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authUserName"
											class="form-control" id="authUserNameId${d}"
											data-rule-required="true" minlength="2" maxlength="50" onchange="checkVerifyPanDetails(${d})" /></td>

									<td><form:input
											path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authMobileNo"
											class="form-control hasNumber" id="authMobileNoId${d}"
											minlength="10" maxlength="10" data-rule-required="true" /></td>

									<td><form:input
											path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authEmail"
											class="form-control hasemailclass" id="authEmailId${d}"
											data-rule-required="true" minlength="10" maxlength="30" /></td>

									<td><form:select
											path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authGender"
											class="form-control" id="authGenderId${d}" onchange="checkVerifyPanDetails(${d})">
											<form:option value="">
												<spring:message code='' text="select" />
											</form:option>
											<c:forEach items="${command.getLevelData('GEN')}"
												var="lookup">
												<form:option value="${lookup.lookUpId}"
													code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>
									<td><div class="input-group">
											<form:input type="text" class="form-control authDOB"
												id="authDOBId${d}"
												path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authDOB"
												placeholder="DD/MM/YYYY" data-rule-required="true" onchange="checkVerifyPanDetails(${d})" />
											<label class="input-group-addon"><i
												class="fa fa-calendar"></i><span class="hide">Date</span></label>
										</div></td>

									<td><form:input
											path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authPanNumber"
											class="form-control hasAlphaNumericClass text-uppercase" id="authPanNumberId${d}"
											data-rule-required="true" minlength="10" maxlength="10" onchange="checkVerifyPanDetails(${d})" /></td>

									<td class="text-center"><apptags:formField fieldType="7"
											fieldPath="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authDocument[${d}].uploadedDocumentPath"
											currentCount="${d1}" showFileNameHTMLId="true"
											folderName="boardResolution${d1}" fileSize="CHECK_COMMOM_MAX_SIZE"
											isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
										</apptags:formField> 
										<i style="font-size: 10px; font-weight: bold;"
										class="text-red-1"><spring:message code="file.upload.msg"
												text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
										<c:if
											test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0] ne null  && not empty command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0]}">
											<input type="hidden" name="deleteFileId"
												value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0]}">
											<apptags:filedownload
												filename="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0].attFname}"
												filePath="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0].attPath}"
												actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
										</c:if></td>

									<td class="text-center"><apptags:formField fieldType="7"
											fieldPath="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authDigitalPDF[${d}].uploadedDocumentPath"
											currentCount="${d2}" showFileNameHTMLId="true"
											folderName="digitalSign${d2}" fileSize="CHECK_COMMOM_MAX_SIZE"
											isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
										</apptags:formField> 
										<i style="font-size: 10px; font-weight: bold;"
										class="text-red-1"><spring:message code="file.upload.msg"
												text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
										<c:if
											test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0] ne null  && not empty command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0]}">
											<input type="hidden" name="deleteFileId"
												value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0]}">
											<apptags:filedownload
												filename="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0].attFname}"
												filePath="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0].attPath}"
												actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
										</c:if></td>
										
									<td class="text-center"><a
										title='<spring:message code="" text="Verify Pan"></spring:message>'
										class="btn btn-success btn-sm" onclick="verifyPan(${d})"><spring:message
												code="" text="Verify Pan"></spring:message></a> <a
										title='<spring:message code="" text="Add"></spring:message>'
										class="btn btn-success btn-sm"  id="addAuthUserId${d}" onclick="addAuthUserRow(this)"
										id="AuthAddButton${d}" ><i class="fa fa-plus-circle"></i></a> <a
										title="<spring:message code="" text="Delete"></spring:message>"
										class="btn btn-danger btn-sm"
										onclick="deleteAuthUserRow($(this),${d})"
										id="AuthDelButton${d}"><i class="fa fa-trash-o"></i></a></td>

								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
								<c:set var="d1" value="${d1 + 1}" scope="page" />
								<c:set var="d2" value="${d2 + 1}" scope="page" />
							</c:when>
							<c:otherwise>
								<c:forEach var="dataList"
									items="${command.developerRegistrationDTO.developerAuthorizedUserDTOList}"
									varStatus="status">
									<tr class="AuthUserAppendable">
										<form:hidden path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].panVerifiedFlag" id="panVerifiedFlagId${d}"/>
										<form:hidden path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].panDetailsFlag" id="panDetailsFlagId${d}"/>
										
										<td><form:input path="" value="${d+1}"
												class="form-control" id="devloperAuthId${d}" />
										</td>

										<td><form:input
												path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authUserName"
												class="form-control" id="authUserNameId${d}"
												data-rule-required="true" minlength="2" maxlength="50" onchange="checkVerifyPanDetails(${d})"/></td>

										<td><form:input
												path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authMobileNo"
												class="form-control hasNumber" id="authMobileNoId${d}"
												minlength="10" maxlength="10" data-rule-required="true" /></td>

										<td><form:input
												path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authEmail"
												class="form-control hasemailclass" id="authEmailId${d}"
												data-rule-required="true" minlength="10" maxlength="30" /></td>

										<td><form:select
												path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authGender"
												class="form-control" id="authGenderId${d}" onchange="checkVerifyPanDetails(${d})">
												<form:option value="">
													<spring:message code='' text="select" />
												</form:option>
												<c:forEach items="${command.getLevelData('GEN')}"
													var="lookup">
													<form:option value="${lookup.lookUpId}"
														code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>
										<td><div class="input-group">
												<form:input type="text" class="form-control authDOB"
													id="authDOBId${d}"
													path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authDOB"
													placeholder="DD/MM/YYYY" data-rule-required="true" onchange="checkVerifyPanDetails(${d})"/>
												<label class="input-group-addon"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div></td>

										<td><form:input
												path="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authPanNumber"
												class="form-control hasAlphaNumericClass text-uppercase" id="authPanNumberId${d}"
												data-rule-required="true" minlength="10" maxlength="10" onchange="checkVerifyPanDetails(${d})"/></td>

										<td><apptags:formField fieldType="7"
												fieldPath="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authDocument[${d}].uploadedDocumentPath"
												currentCount="${d1}" showFileNameHTMLId="true"
												folderName="boardResolution${d1}" fileSize="CHECK_COMMOM_MAX_SIZE"
												isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
											</apptags:formField>
											<i style="font-size: 10px; font-weight: bold;"
											class="text-red-1"><spring:message code="file.upload.msg"
													text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
											<c:if
												test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0] ne null  && not empty command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0]}">
												<input type="hidden" name="deleteFileId"
													value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0].attId}">
												<input type="hidden" name="downloadLink"
													value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0]}">
												<apptags:filedownload
													filename="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0].attFname}"
													filePath="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDocumentList[0].attPath}"
													actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
											</c:if></td>

										<td><apptags:formField fieldType="7"
												fieldPath="developerRegistrationDTO.developerAuthorizedUserDTOList[${d}].authDigitalPDF[${d}].uploadedDocumentPath"
												currentCount="${d2}" showFileNameHTMLId="true"
												folderName="digitalSign${d2}" fileSize="CHECK_COMMOM_MAX_SIZE"
												isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
											</apptags:formField> 
											<i style="font-size: 10px; font-weight: bold;"
											class="text-red-1"><spring:message code="file.upload.msg"
													text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
											<c:if
												test="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0] ne null  && not empty command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0]}">
												<input type="hidden" name="deleteFileId"
													value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0].attId}">
												<input type="hidden" name="downloadLink"
													value="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0]}">
												<apptags:filedownload
													filename="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0].attFname}"
													filePath="${command.developerRegistrationDTO.developerAuthorizedUserDTOList[d].authDigitalPDFList[0].attPath}"
													actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
											</c:if></td>

										<td class="text-center"><a
											title='<spring:message code="verify.pan" text="Verify Pan"></spring:message>'
											class="btn btn-success btn-sm" onclick="verifyPan(${d})"><spring:message
													code="verify.pan" text="Verify Pan"></spring:message></a> <a
											title='<spring:message code="" text="Add"></spring:message>'
											class="btn btn-success btn-sm" onclick="addAuthUserRow(this)"
											id="addAuthUserId${d}" ><i class="fa fa-plus-circle"></i></a>
											<a
											title="<spring:message code="" text="Delete"></spring:message>"
											class="btn btn-danger btn-sm"
											onclick="deleteAuthUserRow($(this),${d})"
											id="AuthDelButton${d}"><i class="fa fa-trash-o"></i></a></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
									<c:set var="d1" value="${d1 + 1}" scope="page" />
									<c:set var="d2" value="${d2 + 1}" scope="page" />
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>

					<div class="text-center">
						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" onclick="saveAuthUserForm(this)" id="saveAuthUser">
							<spring:message code="save.next" text="Save & Next" />
						</button>

						<button type="button" class="button-input btn btn-danger"
							name="button" value="Save" onclick="showTab('#applicantInfo')">
							<spring:message code="back.button" text="Back" />
						</button>


					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>