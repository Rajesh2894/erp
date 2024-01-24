<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/water/plumberLicense.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.plumberLicense.headerPlumLicense" />
			</h2>
			<%--  <div class="additional-btn">
						<a href="#" data-toggle="tooltip" data-original-title="Help"><strong class="fa fa-question-circle fa-lg"></strong><span class="hide"><spring:message code="water.Help"/></span></a>
					</div>  --%>
			<apptags:helpDoc url="PlumberLicenseForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /><i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form:form action="PlumberLicenseForm.html"
				class="form-horizontal form" name="frmPlumberLicenseForm"
				id="frmPlumberLicenseForm" disabled="true">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<form:hidden id="tempFrmDateId" path="" value="" />


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">


					<apptags:applicantDetail wardZone="WWZ" disabled="true"></apptags:applicantDetail>

					<%-- <div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="plumberLicenseReqDTO.fileList0"><spring:message
								code="water.plumberLicense.uploadPhoto" /></label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="plumberLicenseReqDTO.fileList[0]" labelCode=""
								currentCount="0" showFileNameHTMLId="true"
								fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
								folderName="0"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
						</div>
					</div> --%>
					
					<%-- <div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="plumberLicenseReqDTO.plumImage"><spring:message
								code="water.plumberLicense.uploadPhoto" /></label>
						<div class="col-sm-4">
							<div class="col-sm-4">
								<img class="img_thumbnail" src="${command.fileDownLoadPath}">
							</div>
						</div>
					</div> --%>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#child-level"><spring:message
										code="water.plumberLicense.academicProfessionDetails" /></a>
							</h4>
						</div>
						<div id="child-level" class="panel-collapse in collapse">
							<div class="panel-body">

								<c:set var="d" value="0" scope="page" />
								<c:set var="e" value="0" scope="page" />
								<div class="table-responsive" id="academicId">
									<label class="hide" for="qualificationId0"><spring:message
											code="water.plumberLicense.Qualification"/></label> <label
										class="hide" for="instituteNameId0"><spring:message
											code="water.plumberLicense.instituteName" /></label> <label
										class="hide" for="instituteAddrsId0"><spring:message
											code="water.plumberLicense.instituteAddress" /></label>
									<%-- <label
										class="hide" for="passYearId0"><spring:message
											code="water.plumberLicense.yearOfPassing" /></label --%>
									<label class="hide" for="passMonthId0"><spring:message
											code="water.plumberLicense.monthOfPassing" /></label> <label
										class="hide" for="percentGradeId0"><spring:message
											code="water.plumberLicense.percentGrade" /></label>


									<table class="table table-bordered table-striped"
										id="academicTableId">
										<tr>
											<th><spring:message code="water.serialNo" /></th>
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
											<th width="105"><spring:message code="water.Action" /></th>
										</tr>

										<c:choose>
											<c:when test="${empty command.plumberQualificationDTOList}">

												<tr class="academicAppendable">

													<td id="academicSrNoId${d}">${d + 1}</td>

													<td>
														<%-- <form:input
															path="plumberQualificationDTOList[${d}].plumQualification"
															class="form-control" id="qualificationId${d}"
															data-rule-required="true" /> --%> <form:select
															path="plumberQualificationDTOList[${d}].plumQualification"
															cssClass="form-control mandColorClass"
															id="qualificationId${d}">
															<form:option value="">
																<spring:message code='master.selectDropDwn' text="select"/>
															</form:option>
															<c:forEach items="${command.lookUpList}" var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select>
													</td>

													<td><form:input
															path="plumberQualificationDTOList[${d}].plumInstituteName"
															class="form-control " id="instituteNameId${d}"
															data-rule-required="true" disabled="true"/></td>

													<td><form:input
															path="plumberQualificationDTOList[${d}].plumInstituteAddress"
															class="form-control" id="instituteAddrsId${d}"
															data-rule-required="true" disabled="true"/></td>

													<%-- <td><form:input
															path="plumberQualificationDTOList[${d}].plumPassYear"
															class="form-control hasNumber" id="passYearId${d}"
															placeholder="YYYY" maxlength="4"
															data-rule-required="true" /></td> --%>

													<td><form:input
															path="plumberQualificationDTOList[${d}].plumPassMonth"
															class="form-control monthPick" id="passMonthId${d}"
															placeholder="MM" maxlength="2" data-rule-required="true" disabled="true"/></td>

													<td><form:input
															path="plumberQualificationDTOList[${d}].plumPercentGrade"
															class="form-control " id="percentGradeId${d}"
															data-rule-required="true" disabled="true"/></td>

													<td><a data-toggle="tooltip" data-placement="top"
														title="Add" class="academicAddRow btn btn-success btn-sm"
														id="academicAddButton${d}"><i
															class="fa fa-plus-circle"></i></a> <a data-toggle="tooltip"
														data-placement="top" title="Delete"
														class="academicDeleteRow btn btn-danger btn-sm"
														id="academicDelButton${d}"><i class="fa fa-trash-o"></i></a>
													</td>
													<c:set var="d" value="${d + 1}" scope="page" />
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach var="dataList"
													items="${command.plumberQualificationDTOList}"
													varStatus="status">
													<tr class="academicAppendable">

														<td id="academicSrNoId${d}">${d + 1}</td>

														<td>
															<%-- <form:input
																path="plumberQualificationDTOList[${d}].plumQualification"
																class="form-control" id="qualificationId${d}" /> --%> <form:select
																path="plumberQualificationDTOList[${d}].plumQualification"
																cssClass="form-control mandColorClass"
																id="qualificationId${d}" disabled="true">
																<form:option value="">
																	<spring:message code='master.selectDropDwn' text="select"/>
																</form:option>
																<c:forEach items="${command.lookUpList}" var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select>
														</td>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumInstituteName"
																class="form-control" id="instituteNameId${d}" disabled="true"/></td>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumInstituteAddress"
																class="form-control" id="instituteAddrsId${d}" disabled="true"/></td>

														<%-- <td><form:input
																path="plumberQualificationDTOList[${d}].plumPassYear"
																class="form-control hasNumber" id="passYearId${d}"
																maxlength="4" /></td> --%>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumPassMonth"
																class="form-control monthPick" id="passMonthId${d}"
																maxlength="2" disabled="true"/></td>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumPercentGrade"
																class="form-control" id="percentGradeId${d}" disabled="true"/></td>

														<td><a data-toggle="tooltip" data-placement="top"
															title="Add" class="academicAddRow btn btn-success btn-sm"
															id="academicAddButton${d}" ><i
																class="fa fa-plus-circle"></i></a> <a data-toggle="tooltip"
															data-placement="top" title="Delete"
															class="academicDeleteRow btn btn-danger btn-sm"
															id="academicDelButton${d}"><i class="fa fa-trash-o" ></i></a>
														</td>
														<c:set var="d" value="${d + 1}" scope="page" />
													</tr>

												</c:forEach>
											</c:otherwise>
										</c:choose>
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
						<div id="experience" class="panel-collapse in collapse">
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
											<th width="100"><spring:message code="water.Action" /></th>
										</tr>

										<c:choose>
											<c:when test="${empty command.plumberExperienceDTOList}">




												<tr class="experienceAppendable">

													<td id="experienceSrNoId${e}">${e + 1}</td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].plumCompanyName"
															name="" class="form-control" id="companyNameId${e}"
															value="" disabled="true" /></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].plumCompanyAddress"
															name="" class="form-control" id="companyAddrsId${e}"
															value="" disabled="true"/></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].expFromDate" name=""
															cssClass="datepicker cal form-control"
															id="expFromDateId${e}" value="" disabled="true" /></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].expToDate" name=""
															cssClass="datepicker cal form-control"
															id="expToDateId${e}" value="" disabled="true" /></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].experience" name=""
															readonly="true" class="form-control"
															id="experienceId${e}" value="" disabled="true" /></td>

													<td><c:set var="baseLookupCode" value="PFT" /> <form:select
															path="plumberExperienceDTOList[${e}].firmType"
															class="form-control" id="firmTypeId${e}">
															<form:option value="0"><spring:message
															code="water.plumberLicense.valMsg.selectFrmType" text="Select Firm Type" /></form:option>
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>

													<td><a data-toggle="tooltip" data-placement="top"
														title="Add"
														class="experienceAddRow btn btn-success btn-sm"
														id="experienceAddButton${e}"><i
															class="fa fa-plus-circle"></i></a> <a data-toggle="tooltip"
														data-placement="top" title="Delete"
														class="experienceDeleteRow btn btn-danger btn-sm"
														id="experienceDelButton${e}"><i class="fa fa-trash-o"></i></a>
													</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach var="dataList"
													items="${command.plumberExperienceDTOList}"
													varStatus="status">
													<tr class="experienceAppendable">

														<td id="experienceSrNoId${e}">${e + 1}</td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].plumCompanyName"
																name="" class="form-control" id="companyNameId${e}"
																value="" disabled="true" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].plumCompanyAddress"
																name="" class="form-control" id="companyAddrsId${e}"
																value="" disabled="true" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].expFromDate"
																name="" cssClass="datepicker cal form-control"
																id="expFromDateId${e}" value="" disabled="true" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].expToDate" name=""
																cssClass="datepicker cal form-control"
																id="expToDateId${e}" value="" disabled="true" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].experience" name=""
																readonly="true" class="form-control"
																id="experienceId${e}" value="" disabled="true" /></td>

														<td><c:set var="baseLookupCode" value="PFT" /> <form:select
																path="plumberExperienceDTOList[${e}].firmType"
																class="form-control" id="firmTypeId${e}" disabled="true">
																<form:option value="0">Select Firm Type</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td><a data-toggle="tooltip" data-placement="top"
															title="Add"
															class="experienceAddRow btn btn-success btn-sm"
															id="experienceAddButton${e}"><i
																class="fa fa-plus-circle"></i></a> <a data-toggle="tooltip"
															data-placement="top" title="Delete"
															class="experienceDeleteRow btn btn-danger btn-sm"
															id="experienceDelButton${e}"><i class="fa fa-trash-o"></i></a>
														</td>
													</tr>
													<c:set var="e" value="${e + 1}" scope="page" />
												</c:forEach>
											</c:otherwise>
										</c:choose>
										<tfoot>
											<tr>
												<th colspan="5"><span class="pull-right"><spring:message
															code="water.plumberLicense.totalExperience" /></span></th>
												<td><form:input path="totalExp" id="totalExpId"
														readonly="true" class="form-control" /></td>
												<th colspan="2"><span class="hide"><spring:message
															code="water.Blank" /></span></th>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</div>
					</div>


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
													<table class="table table-bordered table-condensed"
												id="docTable">
												<tr>
													<th width="10%"><spring:message
															code="checklistVerification.srNo" /></th>
													<th width="20%"><spring:message
															code="checklistVerification.document" /></th>
													<th width="15%"><spring:message
															code="checklistVerification.documentStatus" /></th>
													<%-- <th width="30%"><spring:message code="" text="Remarks" /></th> --%>
													<th width="15%"><spring:message
															code="cfc.rejected.doc" /></th>
													<%-- <th width="20%"><spring:message code="cfc.upload.doc" /></th> --%>
												</tr>
												<c:forEach var="singleDoc" items="${command.documentsList}"
													varStatus="count">
													<c:if test="${count.index eq 0}">
														<input type="hidden"
															value=" ${fn:length(command.documentsList)}" id="attSize">
													</c:if>
													<tr>
														<td class="hide">${singleDoc.lookUpExtraLongOne}</td>
														<td>${count.count}</td>
														<td>${singleDoc.lookUpDesc}</td>
														<td><c:choose>
																<c:when test="${singleDoc.otherField eq 'Y'}">
																	<spring:message code="checklistVerification.mandatory" />
																	<c:set var="docStatus" value="Mandatory" />
																</c:when>
																<c:otherwise>
																	<spring:message code="checklistVerification.optional" />
																	<c:set var="docStatus" value="Optional" />
																</c:otherwise>
															</c:choose></td>
														<%-- <td>${singleDoc.extraStringField1}</td> --%>
														<td class="row1"><c:set var="links"
																value="${fn:split(singleDoc.descLangSecond,',')}" /> <c:forEach
																items="${links}" var="download" varStatus="status">
																<apptags:filedownload filename="${singleDoc.lookUpCode}"
																	filePath="${download}"
																	actionUrl="DocumentResubmission.html?Download"></apptags:filedownload>
															</c:forEach></td>
														<%-- <td><apptags:formField fieldType="7"
																fieldPath="attachment.attPath" labelCode=""
																currentCount="${count.index}" showFileNameHTMLId="true"
																fileSize="CHECK_COMMOM_MAX_SIZE"
																maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="ALL_UPLOAD_VALID_EXTENSION"
																folderName="${count.index}"
																checkListSrNo="${singleDoc.lookUpExtraLongOne}"
																checkListSStatus="${singleDoc.lookUpExtraLongTwo}"
																checkListMStatus="${singleDoc.lookUpType}"
																checkListMandatoryDoc="${singleDoc.otherField}"
																checkListDesc="${singleDoc.lookUpDesc}"
																checkListId="${singleDoc.lookUpId}" /></td> --%>
													</tr>
												</c:forEach>
											</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						
					<div class="form-group text-center padding-bottom-20" id="back">

								<input type="button" class="btn btn-danger"
									onclick="window.location.href='CitizenHome.html'" value="Back">
							</div>

				</div>

			</form:form>
		</div>
	</div>
</div>

