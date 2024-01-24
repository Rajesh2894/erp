<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="" src="js/land_estate/landEstate.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.acq.title"
					text="Land Acquisition Proposal" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form method="POST" action="LandAcquisition.html"
				cssClass="form-horizontal" id="landAcqId" name="landAcqForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="land.acq.form.details" text="Land Acquisition Details" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="land.acq.pay.to" isMandatory="true" isReadonly="${command.saveMode eq 'VIEW'}"
										path="acquisitionDto.payTo" cssClass="alphaNumeric"
										maxlegnth="20"></apptags:input>

									<apptags:input labelCode="land.acq.area"
										path="acquisitionDto.lnArea" isMandatory="true" isReadonly="${command.saveMode eq 'VIEW'}"
										cssClass='form-control noInDecimal'></apptags:input>

								</div>
								
								<div class="form-group">
									<apptags:input labelCode="land.acq.mobNo"
										path="acquisitionDto.lnMobNo" cssClass="hasMobileNo" isReadonly="${command.saveMode eq 'VIEW'}"
										maxlegnth="10" ></apptags:input>

									<apptags:input labelCode="land.acq.address"
										path="acquisitionDto.lnAddress" cssClass="alphaNumeric"
										maxlegnth="500" isMandatory="true" isReadonly="${command.saveMode eq 'VIEW'}"></apptags:input>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.location" /> <span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.locId" id="locId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="${command.saveMode eq 'VIEW'}">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.locationList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="land.acq.acqType" text="Acquisition Type" /></label>
									<c:set var="baseLookupCode" value="AQM" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="acquisitionDto.acqModeId" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Acquisition Mode"
										disabled="${command.saveMode eq 'VIEW'}" />
								</div>


								<div class="form-group">
									<apptags:input labelCode="land.acq.surveyNo"
										path="acquisitionDto.lnServno" cssClass="alphaNumeric"
										maxlegnth="10" isMandatory="true" isReadonly="${command.saveMode eq 'VIEW'}"></apptags:input>


									<apptags:input labelCode="land.acq.currentUsage"
										path="acquisitionDto.currentUsage" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" isReadonly="${command.saveMode eq 'VIEW'}"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:input labelCode="land.acq.currentMarktValue"
										path="acquisitionDto.currentMarktValue" cssClass="hasNumber1"
										maxlegnth="100" isMandatory="false" isReadonly="${command.saveMode eq 'VIEW'}"></apptags:input>

									<apptags:input labelCode="land.acq.purpose" isMandatory="true"
										path="acquisitionDto.acqPurpose" cssClass="hasCharacter"
										maxlegnth="100" isReadonly="${command.saveMode == 'VIEW'}"></apptags:input>

								</div>
								
								<div class="form-group">
									<apptags:input labelCode="land.acq.desc"
										path="acquisitionDto.lnDesc" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" isReadonly="${command.saveMode eq 'VIEW'}"></apptags:input>


									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.oth" /> <span><i class="text-red-1">*</i></span>
									</label>

									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.lnOth" id="lnOth"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass" disabled="${command.saveMode eq 'VIEW'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>

								</div>

								<div class="form-group">



									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.Title" text="Tittle Document available" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.lnTitle"
											cssClass="form-control chosen-select-no-results" disabled="${command.saveMode eq 'VIEW'}"
											class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>

									<apptags:input labelCode="land.acq.remark"
										path="acquisitionDto.lnRemark" cssClass="alphaNumeric " isReadonly="${command.saveMode eq 'VIEW'}"
										maxlegnth="100"></apptags:input>

								</div>

								<%-- <div class="form-group">
									<apptags:date fieldclass="datepicker" labelCode="land.acq.date"
										datePath="acquisitionDto.acqDate"
										isDisabled="${command.saveMode eq 'VIEW'}"></apptags:date>

								</div> --%>

								<!-- Document code set -->
								<div class="form-group">
									<c:set var="count" value="${count + 1}" scope="page" />

									<label for="" class="col-sm-2 control-label"> <spring:message
											code="council.member.documents" text="Documents" /></label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
										<c:if
											test="${command.saveMode eq 'ADD'  || command.saveMode eq 'EDIT' }">
											<apptags:formField fieldType="7"
												fieldPath="checkList[${count}].uploadedDocumentPath"
												currentCount="${count}" folderName="${count}"
												fileSize="BND_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
												isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
											</apptags:formField>
										</c:if>

										<c:if
											test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
											<input type="hidden" name="deleteFileId"
												value="${command.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.attachDocsList[0].attFname}"
												filePath="${command.attachDocsList[0].attPath}"
												actionUrl="LandAcquisition.html?Download"></apptags:filedownload>
										</c:if>

									</div>
								</div>

								<!-- checklist code set here -->
								<c:if test="${checkList != null}">
									<!---------------------------------------------------------------document upload start------------------------ -->
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title table" id="">
												<a data-toggle="collapse" class=""
													data-parent="#accordion_single_collapse1" href="#a5"><spring:message
														text="Document Upload Details" /></a>
											</h4>
										</div>
										<div id="a5" class="panel-collapse collapse in">
											<div class="panel-body">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><spring:message text="Sr.No" /></th>
																<th><spring:message text="Document Group" /></th>
																<th><spring:message text="Document Status" /></th>
																<th><spring:message text="Upload document" /></th>
															</tr>
															<c:forEach items="${command.checkList}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td>${lookUp.documentSerialNo}</td>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																			<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																		</c:when>
																		<c:otherwise>
																			<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																			<td><label>${lookUp.doc_DESC_Mar}</label></td>
																		</c:otherwise>
																	</c:choose>
																	<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																		<td><spring:message code="water.doc.mand" /></td>
																	</c:if>
																	<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																		<td><spring:message code="water.doc.opt" /></td>
																	</c:if>
																	<td>
																		<div id="docs_${lk}" class="">
																			<apptags:formField fieldType="7" labelCode=""
																				hasId="true" fieldPath="checkList[${lk.index}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="BND_COMMOM_MAX_SIZE"
																				checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																				currentCount="${lk.index}"
																				checkListDesc="${docName}" />
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>

											<!---------------------------------------------------------------document upload end------------------------ -->
										</div>
									</div>
									
									
									<div class="padding-top-10 text-center">

										<button type="button" class="btn btn-danger" id="back"
											onclick="window.location.href='LandAcquisition.html'">
											<spring:message code="bt.backBtn"></spring:message>
										</button>
									</div>
								</c:if>
								
								
								
								<!-- Start button -->
								<div class="text-center">

									<c:choose>
										<c:when test="${command.isChecklistApplicable}">
											<button type="button" class="btn btn-success"
												id="continueForm" onclick="getChecklistAndCharge(this);">
												<spring:message code="bt.proceed"  text="Proceed"/>
											</button>
										</c:when>
										<c:otherwise>
											<c:if test="${command.saveMode ne 'VIEW'}">
												<button type="button" class="btn btn-success" title="Submit"
													onclick="saveLaqform(this)">
													<i class="fa fa-sign-out padding-right-5"
														aria-hidden="true"></i>
													<spring:message code="bt.save" text="Submit" />
												</button>
											</c:if>
										</c:otherwise>
									</c:choose>

									<c:if test="${command.saveMode eq 'ADD'}">
										<button type="button" id="resetForm" class="btn btn-warning" 
										title="Reset">
											<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
											<spring:message code="bt.clear" text="Reset" />
										</button>
									</c:if>
									<button type="button" class="button-input btn btn-danger"
										name="button-Cancel" value="Cancel" style=""
										onclick="window.location.href='LandAcquisition.html'"
										id="button-Cancel">
										<i class="fa fa-chevron-circle-left padding-right-5"></i>
										<spring:message code="bt.backBtn" text="Back" />
									</button>
								</div>
								
								<!-- End button -->
								
								
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->