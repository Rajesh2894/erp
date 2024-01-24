<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/plumberRenewal.js"></script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.plumberLicense.headerPlumLicense" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><strong
					class="fa fa-question-circle fa-lg"></strong><span class="hide"><spring:message
							code="water.Help" text="Help" /></span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /><i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<form:form action="PlumberLicenseRenewal.html"
				class="form-horizontal form" name="frmPlumberLicenseRenewal"
				id="frmPlumberLicenseRenewal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" id="plumIdFlag"
					value="${command.plumberLicenseReqDTO.flag}" />
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.license.number" text="License Number" /></label>
					<div class="col-sm-4">
						<form:input path="plumberLicenseReqDTO.plumberLicenceNo"
							id="plumberLicenceNo" type="text" class="form-control" />
					</div>
				</div>
				<div class="form-group" id="searchTab">
					<div class="text-center padding-bottom-10">

						<button type="button" id="searchDet" class="btn btn-blue-2" title='<spring:message code="water.btn.view.details" text="View Details" />'
							onclick="viewLicenseDetails(this)">
							<i class="fa fa-search"></i> <spring:message code="water.btn.view.details" text="View Details" />
						</button>
						<%-- Defect #166252 --%>
						<button type="button" class="btn btn-warning" onclick="javascript:openRelatedForm('PlumberLicenseRenewal.html');"
							title='<spring:message code="water.btn.reset" text="Reset" />'>
							<spring:message code="water.btn.reset" text="Reset" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>

				</div>
			</form:form>

		<div id="selectHideShow">
			<form:form action="PlumberLicenseRenewal.html" method="post"
				class="form-horizontal" name="licGenerarion" id="licGeneration">
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div id="appli">
						<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="plumberLicenseReqDTO.plumImage"><spring:message
								code="water.plumberLicense.uploadPhoto" /></label>
						<div class="col-sm-4">
							<%-- <apptags:formField
									fieldType="7" fieldPath="plumberLicenseReqDTO.plumImage" labelCode="" currentCount="0" 
									showFileNameHTMLId="true"  fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT" folderName="0"
									validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"  /> --%>
							<div class="col-sm-4">
								<img class="img_thumbnail" src="${command.fileDownLoadPath}">
							</div>
						</div>
					</div>
					<%-- <div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="applicantTitle"> Application Number </label>
						<div class="col-sm-4">
							<form:input name="" type="text" class="form-control"
								path="plumberLicenseReqDTO.applicationId" id="title"
								readonly="true" data-rule-required="true"></form:input>
						</div>
						<label class="col-sm-2 control-label required-control"
							for="firstName">Application Apply Date</label>
						<div class="col-sm-4">
							<form:input name="" type="text" class="form-control"
								path="plumberLicenseReqDTO.plumAppDate" id="firstName"
								readonly="true" data-rule-required="true"></form:input>
						</div>
					</div> --%>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#child-level"><spring:message
										code="water.plumberLicense.academicProfessionDetails" /></a>
							</h4>
						</div>
						<div id="child-level" class="panel-collapse collapse">
							<div class="panel-body">

								<c:set var="d" value="0" scope="page" />
								<c:set var="e" value="0" scope="page" />
								<div class="table-responsive" id="academicId">
									<table class="table table-bordered table-striped"
										id="academicTableId">
										<tr>
											<th><spring:message
													code="water.plumberLicense.qualificationGained" /><span
												class="mand">*</span></th>
											<th><spring:message
													code="water.plumberLicense.instituteName" /><span
												class="mand">*</span></th>
											<th><spring:message
													code="water.plumberLicense.instituteAddress" /><span
												class="mand">*</span></th>
											<%-- <th><spring:message
													code="water.plumberLicense.yearOfPassing" /><span
												class="mand">*</span></th> --%>
											<th><spring:message
													code="water.plumberLicense.monthOfPassing" /><span
												class="mand">*</span></th>
											<th><spring:message
													code="water.plumberLicense.percentGrade" /><span
												class="mand">*</span></th>

										</tr>
										<c:forEach
											items="${command.plumberLicenseReqDTO.getPlumberQualificationDTOList()}"
											var="data" varStatus="status">

											<tr class="academicAppendable">
												<td>
													<%-- <form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumQualification"
														 class="form-control"  readonly="true"
														data-rule-required="true" /> --%> <form:select
														path="plumberQualificationDTOList[${status.index}].plumQualification"
														cssClass="form-control mandColorClass"
														id="qualificationId${d}" disabled="true">
														<form:option value="">
															<spring:message code='master.selectDropDwn' />
														</form:option>
														<c:forEach items="${command.lookUpList}" var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select>
												</td>

												<td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumInstituteName"
														name="" class="form-control" value=""
														data-rule-required="true" readonly="true" /></td>
												<td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumInstituteAddress"
														name="" class="form-control" value=""
														data-rule-required="true" readonly="true" /></td>
												<%-- <td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumUniversityName"
														name="" class="form-control hasNumber" value=""
														data-rule-required="true" readonly="true" /></td> --%>
												<%-- <td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumPassYear"
														name="" class="form-control hasNumber" value=""
														placeholder="YYYY" maxlength="4" data-rule-required="true"
														readonly="true" /></td> --%>

												<td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumPassMonth"
														class="form-control hasNumber" id="passMonthId${d}"
														readonly="true" /></td>

												<td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumPercentGrade"
														class="form-control" id="percentGradeId${d}"
														readonly="true" /></td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#experience"><spring:message
										code="water.plumberLicense.experianceDetails" /></a>
							</h4>
						</div>
						<div id="experience" class="panel-collapse collapse">
							<div class="panel-body">

								<label class="hide" for="companyNameId0"><spring:message
										code="water.plumberLicense.CompanyName" /></label> <label
									class="hide" for="companyAddrsId0"><spring:message
										code="water.plumberLicense.CompanyAddrs" /></label> <label
									class="hide" for="expFromDateId0"><spring:message
										code="water.plumberLicense.frmDate" /></label> <label class="hide"
									for="expToDateId0"><spring:message
										code="water.plumberLicense.toDate" /></label> <label class="hide"
									for="experienceId0"><spring:message
										code="water.plumberLicense.experience" /></label> <label class="hide"
									for="firmTypeId0"><spring:message
										code="water.plumberLicense.firmType" /></label> <label class="hide"
									for="totalExpId"><spring:message
										code="water.plumberLicense.totalExperience" /></label>


								<div class="table-responsive" id="experienceId">
									<table class="table table-bordered table-striped"
										id="experienceTableId">
										<tr>
											<th><spring:message code="water.serialNo" /></th>
											<th><spring:message
													code="water.plumberLicense.employerName" /></th>
											<th><spring:message
													code="water.plumberLicense.employerAddress" /></th>
											<th width="140"><spring:message
													code="water.plumberLicense.frmDate" /></th>
											<th width="140"><spring:message
													code="water.plumberLicense.toDate" /></th>
											<th><spring:message
													code="water.plumberLicense.experience" /></th>
											<th width="150"><spring:message
													code="water.plumberLicense.firmType" /></th>
										</tr>

										<c:forEach var="dataList"
											items="${command.plumberExperienceDTOList}"
											varStatus="status">
											<tr class="experienceAppendable">

												<td id="experienceSrNoId${e}">${e + 1}</td>

												<td><form:input
														path="plumberExperienceDTOList[${e}].plumCompanyName"
														name="" class="form-control" id="companyNameId${e}"
														value="" readonly="true" /></td>

												<td><form:input
														path="plumberExperienceDTOList[${e}].plumCompanyAddress"
														name="" class="form-control" id="companyAddrsId${e}"
														value="" readonly="true" /></td>

												<td><form:input
														path="plumberExperienceDTOList[${e}].expFromDate" name=""
														cssClass="datepicker cal form-control"
														id="expFromDateId${e}" value="" readonly="true" /></td>

												<td><form:input
														path="plumberExperienceDTOList[${e}].expToDate" name=""
														cssClass="datepicker cal form-control"
														id="expToDateId${e}" value="" readonly="true" /></td>

												<td><form:input
														path="plumberExperienceDTOList[${e}].experience" name=""
														readonly="true" class="form-control" id="experience${e}"
														value="" /></td>

												<td><c:set var="baseLookupCode" value="PFT" /> <form:select
														path="plumberExperienceDTOList[${e}].firmType"
														class="form-control" id="firmTypeId${e}" disabled="true">
														<form:option value="0">Select Firm Type</form:option>
														<c:forEach items="${command.getLevelData(baseLookupCode)}"
															var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>

												<%-- <td><a data-toggle="tooltip" data-placement="top"
															title="Add"
															class="experienceAddRow btn btn-success btn-sm"
															id="experienceAddButton${e}"><i
																class="fa fa-plus-circle"></i></a> <a data-toggle="tooltip"
															data-placement="top" title="Delete"
															class="experienceDeleteRow btn btn-danger btn-sm"
															id="experienceDelButton${e}"><i class="fa fa-trash-o"></i></a>
														</td> --%>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
										<tfoot>
											<tr>
												<th colspan="5"><span class="pull-right"><spring:message
															code="water.plumberLicense.totalExperience" /></span></th>
												<td><form:input path="totalExp" id="totalExpId"
														readonly="true" class="form-control" /></td>
												<th colspan="2"><span class="hide"><spring:message
															code="" text="" /></span></th>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control">Valid
							From</label>
						<c:choose>
							<c:when
								test="${empty command.plumberLicenseReqDTO.plumLicFromDate}">
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="plumberLicenseReqDTO.plumLicFromDate"
											type="text" id="licGenDate"
											class="lessthancurrdate form-control" readonly='true'></form:input>
										<label for="datepicker" class="input-group-addon"><i
											class="fa fa-calendar"></i></label>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-sm-4">
									<form:input path="plumberLicenseReqDTO.plumLicFromDate"
										type="text" id="toDate" class="form-control lessthancurrdate"
										readonly="true" />
								</div>
							</c:otherwise>
						</c:choose>

						<label class="col-sm-2 control-label required-control">Valid
							To</label>
						<div class="col-sm-4">
							<form:input path="plumberLicenseReqDTO.plumLicToDate" type="text"
								id="toDate" class="form-control lessthancurrdate"
								readonly="true" />
						</div>
					</div>
					<div class="padding-top-10 text-center hideConfirmBtn">
						<button type="button" class="btn btn-success"
							id="confirmToProceedPlId"
							onclick="getChecklistAndChargesPLRenewal(this);">
							<spring:message code="water.btn.proceed" />
						</button>
					</div>
					<form:hidden id="chargesId" path=""
						value="${command.checkListNCharges}" />
					<c:if test="${command.checkListNCharges eq 'Y'}">
						<c:if test="${(not empty command.checkList)}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class="collapsed"
											data-parent="#accordion_single_collapse" href="#child-level3"><spring:message
												code="water.documentattchmnt" /><small class="text-blue-2"><spring:message
													code="water.uploadfile.validtn" /></small></a>
									</h4>
								</div>
								<div id="child-level3" class="panel-collapse">
									<div class="panel-body">

										<fieldset class="fieldRound">
											<div class="overflow">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><label class="tbold"><spring:message
																			code="label.checklist.srno" text="Sr No" /></label></th>
																<th><label class="tbold"><spring:message
																			code="label.checklist.docname" text="Document Name" /></label></th>
																<th><label class="tbold"><spring:message
																			code="label.checklist.status" text="Status" /></label></th>
																<th><label class="tbold"><spring:message
																			code="label.checklist.upload" text="Upload" /></label></th>
															</tr>
															<c:forEach items="${command.checkList}" var="lookUp"
																varStatus="lk">

																<tr>
																	<td><label>${lookUp.documentSerialNo}</label></td>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																		</c:when>
																		<c:otherwise>
																			<td><label>${lookUp.doc_DESC_Mar}</label></td>
																		</c:otherwise>
																	</c:choose>
																	<c:set var="isCheckListMandatory" value="N"></c:set>
																	<c:choose>
																		<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
																			<c:set var="isCheckListMandatory" value="Y"></c:set>
																			<td><label><spring:message
																						code="label.checklist.status.mandatory" /></label></td>
																		</c:when>
																		<c:otherwise>
																			<td><label><spring:message
																						code="label.checklist.status.optional" /></label></td>
																		</c:otherwise>
																	</c:choose>
																	<td>
																		<div id="docs_${lk}" class="">
																			<apptags:formField fieldType="7" labelCode=""
																				hasId="true"
																				fieldPath="plumberLicenseReqDTO.fileCheckList[${lk.index}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="BND_COMMOM_MAX_SIZE"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
																				currentCount="${lk.index}"
																				checkListMandatoryDoc="${isCheckListMandatory}"
																				folderName="${lk.index}" />
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</c:if>

						<c:if test="${command.isFree eq 'N'}">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"> <spring:message
										code="water.field.name.amounttopay" />
								</label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"
										readonly="true" /> <a
										class="fancybox fancybox.ajax text-small text-info"
										href="PlumberLicenseRenewal.html?showChargeDetails"> <spring:message
											code="water.field.name.amounttopay" /> <i
										class="fa fa-question-circle "></i>
									</a>
								</div>
							</div>
						</c:if>
						<div class="text-center padding-bottom-20" id="divSubmit">
							<button type="button" class="btn btn-success"
								onclick="savePlRenwData(this)" id="submit">
								<spring:message code="water.btn.submit" />
							</button>
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'" value="Cancel" />
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
		</div>

	</div>
</div>
