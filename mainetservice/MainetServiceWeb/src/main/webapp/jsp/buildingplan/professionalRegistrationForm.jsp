<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/buildingplan/professionalRegForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="professional.reg.heading"
					text="Registration Form"></spring:message>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="ProfessionalRegistrationForm.html" method="post"
				class="form-horizontal" name="professionalRegistrationForm"
				id="professionalRegistrationForm">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="professional.user.type" text="User Type"></spring:message></label>
					<div class="col-sm-4">
						<form:select id="userType"
							cssClass="form-control chosen-select-no-results"
							class="form-control required-control"
							path="professionalRegDTO.userType" onchange="getchecklist()"
							disabled="${'C' ne command.modeType}">
							<form:option value="">
								<spring:message code='professional.select' text="Select" />
							</form:option>
							<<c:forEach items="${command.getLevelData('TPR')}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="firstName"><spring:message
							code="professional.first.name" text="First Name" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control" maxlength="15"
							minlength="2" path="professionalRegDTO.firstName" id="firstName"
							disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:input>
					</div>
					<label class="col-sm-2 control-label" for="middleName"><spring:message
							code="professional.middle.name" text="Middle Name" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control" maxlength="15"
							minlength="2" path="professionalRegDTO.middleName"
							id="middleName" disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="lastName"><spring:message
							code="professional.last.name" text="Last Name" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control" maxlength="15"
							minlength="2" path="professionalRegDTO.lastName" id="lastName"
							disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:input>
					</div>
					<label class="col-sm-2 control-label required-control"
						for="emailId"><spring:message code="professional.email.id"
							text="Email Id" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasemailclass"
							maxlength="25" minlength="8" path="professionalRegDTO.emailId"
							id="emailId" disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="mobileNo"><spring:message
							code="professional.mobile.no" text="Mobile No." /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasMobileNo"
							path="professionalRegDTO.mobileNo" id="mobileNo" maxlength="10"
							minlength="10" disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:input>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="professional.state" text="State"></spring:message></label>
					<div class="col-sm-4">
						<form:select id="state"
							cssClass="form-control chosen-select-no-results"
							class="form-control required-control"
							path="professionalRegDTO.state" onchange=""
							disabled="${'V' eq command.modeType || 'A' eq command.modeType}">
							<form:option value="">
								<spring:message code='professional.select' text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData('STT')}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="district"><spring:message
							code="professional.district" text="District" /></label>
					<div class="col-sm-4">
						<form:select id="district"
							cssClass="form-control chosen-select-no-results"
							class="form-control required-control"
							path="professionalRegDTO.district" onchange=""
							disabled="${'V' eq command.modeType || 'A' eq command.modeType}">
							<form:option value="">
								<spring:message code='professional.select' text="Select" />
							</form:option>
							<<c:forEach items="${command.district}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control"
						for="pincode"><spring:message code="professional.pincode"
							text="Pincode" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasPincode"
							maxlength="6" minlength="6" path="professionalRegDTO.pincode"
							id="pincode" disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="address"><spring:message code="professional.address"
							text="Address" /></label>
					<div class="col-sm-4">
						<form:textarea type="text" class="form-control" maxlength="50"
							minlength="2" path="professionalRegDTO.address" id="address"
							disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:textarea>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="professionalRegDTO.office" text="Office(Circle)"></spring:message></label>
					<div class="col-sm-4">
						<form:select id="officeCircle"
							cssClass="form-control chosen-select-no-results"
							class="form-control required-control"
							path="professionalRegDTO.officeCircle"
							disabled="${'V' eq command.modeType || 'A' eq command.modeType}">
							<form:option value="">
								<spring:message code='professional.select' text="Select" />
							</form:option>
							<c:forEach items="${command.officeCircle}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<div class="form-group architectclass">
					<label class="col-sm-2 control-label required-control" for="coaNo"><spring:message
							code="professional.coa.no" text="COA No." /></label>
					<div class="col-sm-4 architectclass">
						<form:input type="text" class="form-control" maxlength="13"
							minlength="2" path="professionalRegDTO.coaNo" id="coaNo"
							disabled="${'V' eq command.modeType || 'A' eq command.modeType}"></form:input>
					</div>
					<label class="col-sm-2 control-label required-control"
						for="coaDate"><spring:message code="professional.coa.date"
							text="COA Validity Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="professionalRegDTO.coaValDate" type="text"
								class="form-control lessdatepicker mandColorClass" id="coaDate"
								disabled="${'V' eq command.modeType || 'A' eq command.modeType}" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>

				<div id="checkListDiv">
					<c:if
						test="${not empty command.checkList}">

						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#DocumentUpload"><spring:message
									code="professional.document" text="Document" /></a>
						</h4>

						<div id="DocumentUpload" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="overflow margin-top-10">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><spring:message code="professional.srno"
															text="Sr No." /></th>
													<th><spring:message code="professional.document.name"
															text="Document Name" /></th>
													<th><spring:message code="professional.status"
															text="Status" /></th>
													<th><spring:message code="professional.upload"
															text="Upload" /></th>
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
															<td><spring:message code="professional.mandatory"
																	text="Mandatory" /></td>
														</c:if>
														<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
															<td><spring:message code="professional.optional"
																	text="Optional" /></td>
														</c:if>
														<td>
															<div id="docs_${lk}" class="">
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true" fieldPath="checkList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="BND_COMMOM_MAX_SIZE"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_TCP"
																	currentCount="${lookUp.documentId}" />
																<i style="font-size: 10px; font-weight: bold;"
																	class="text-red-1"><spring:message
																		code="professional.checklist.tooltip"
																		text="(Upload file upto 5MB and only pdf,jpeg,jpg)" /></i>
															</div>
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
					<c:if
						test="${('V' eq command.modeType || 'A' eq command.modeType) && not empty command.documentList}">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#DocumentUpload"><spring:message
									code="professional.uploaded.document" text="Uploaded Documents" /></a>
						</h4>

						<fieldset class="fieldRound">
							<div class="overflow">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped">
										<tbody>
											<tr>
												<th><spring:message
															code="professional.srno" text="Sr No." /></th>
												<th><spring:message
															code="professional.document.name" text="Document Name" /></th>
												<th><spring:message
															code="professional.download" text="Download" /></th>
											</tr>

											<c:forEach items="${command.documentList}" var="lookUp"
												varStatus="lk">
												<tr>
													<td><label>${lookUp.clmSrNo}</label></td>
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<td><label>${lookUp.clmDescEngl}</label></td>
														</c:when>
														<c:otherwise>
															<td><label>${lookUp.clmDesc}</label></td>
														</c:otherwise>
													</c:choose>
													<td><c:set var="links"
															value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
														<apptags:filedownload filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															dmsDocId="${lookUp.dmsDocId}"
															actionUrl="ProfessionalRegistrationForm.html?Download"></apptags:filedownload>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</fieldset>

					</c:if>
				</div>
				<c:if test="${'V' eq command.modeType }">
					<c:choose>
						<c:when test="${3 eq command.currentLevel}">
							<div>
								<apptags:CheckerAction hideForward="true" hideUpload="true"
									showSendbackToApplicant="true"></apptags:CheckerAction>
							</div>
						</c:when>
						<c:when test="${4 eq command.currentLevel}">
							<div>
								<apptags:CheckerAction hideForward="true" hideUpload="true"
									hideSendback="true"></apptags:CheckerAction>
							</div>
						</c:when>
						<c:when
							test="${1 eq command.currentLevel || 2 eq command.currentLevel}">
							<div>
								<apptags:CheckerAction hideForward="true" hideSendback="true"
									hideReject="true" hideUpload="true"></apptags:CheckerAction>
							</div>
						</c:when>
						<c:otherwise>

						</c:otherwise>
					</c:choose>

				</c:if>

				<div class="text-center">
					<c:if test="${'C' eq command.modeType}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveRegForm(this)" id="submit">
							<spring:message code="professional.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${'V' eq command.modeType ||  'E' eq command.modeType}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveRegApprovalForm(this)" id="save">
							<spring:message code="professional.save" text="Save" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="professional.back" text="Back" />
					</button>


				</div>
			</form:form>
		</div>
	</div>
</div>
