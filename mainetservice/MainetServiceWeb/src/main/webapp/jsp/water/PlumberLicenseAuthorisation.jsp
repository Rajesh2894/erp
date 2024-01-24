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
<script type="text/javascript" src="js/water/plumberLicense.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.plumberLicense.headerPlumLicense" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><strong
					class="fa fa-question-circle fa-lg"></strong><span class="hide"><spring:message
							code="" text="Help" /></span></a>
			</div>
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
			<form:form action="PlumberLicenseAuth.html"
				class="form-horizontal form" name="frmPlumberLicenseForm"
				id="frmPlumberLicenseAuthForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden id="tempFrmDateId" path="" value="" />
				<form:hidden id="fromEvent" path="" value="${command.fromEvent}" />
				<form:hidden path="payableFlag" id="payableFlag" />

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
														name="" class="form-control" value="" readonly="true"
														data-rule-required="true" /> --%> <form:select
														path="plumberQualificationDTOList[${d}].plumQualification"
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
														class="form-control" data-rule-required="true"
														readonly="true" /></td>
												<td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumInstituteAddress"
														class="form-control" data-rule-required="true"
														readonly="true" /></td>
												<%-- <td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumPassYear"
														name="" class="form-control hasNumber" value=""
														placeholder="YYYY" maxlength="4" data-rule-required="true"
														readonly="true" /></td> --%>

												<td><form:input
														path="plumberLicenseReqDTO.plumberQualificationDTOList[${status.index}].plumPassMonth"
														class="form-control monthPick" id="passMonthId${d}"
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
							<c:if test="${not empty command.documentList}">
								<fieldset class="fieldRound">
									<div class="overflow">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<tr>
														<th><label class="tbold"><spring:message
																	code="water.serialNo" text="Sr No" /></label></th>
														<th><label class="tbold"><spring:message
																	code="water.docName" text="Document Name" /></label></th>
														<th><label class="tbold"><spring:message
																	code="water.download" /></label></th>
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
																	actionUrl="PlumberLicenseForm.html?Download"></apptags:filedownload>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</c:if>
						</div>
					</div>



					<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>

				</div>
				
				<!--D#91264 -->
				<c:if test="${command.payableFlag eq 'Y' }">
					<h4>LOI Fees and Charges in Details</h4>
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tr>
								<th scope="col" width="80">Sr. No</th>
								<th scope="col" style ="text-align:left !important;">Charge Name</th>
								<th scope="col" style ="text-align:right !important;">Amount</th>
							</tr>
							<c:forEach var="charges" items="${command.loiDetail}"
								varStatus="status">
								<tr>
									<td>1</td>
									<td>${charges.loiRemarks}</td>
									<td><fmt:formatNumber value="${charges.loiAmount}"
											type="number" var="amount" minFractionDigits="2"
											maxFractionDigits="2" groupingUsed="false" /> <form:input
											path="" type="text" class="form-control text-right"
											value="${amount}" readonly="true" /></td>
								</tr>
							</c:forEach>

							<tr>
								<td colspan="2"><span class=""><b>Total
											LOI Amount</b></span></td>
								<td><fmt:formatNumber value="${command.totalLoiAmount}"
											type="number" var="totalamount" minFractionDigits="2"
											maxFractionDigits="2" groupingUsed="false" /> <form:input
											path="" type="text" class="form-control text-right"
											value="${totalamount}" readonly="true" /></td>
								<%-- <td class="text-right" type="number" var="amount" minFractionDigits="2"
											maxFractionDigits="2" groupingUsed="false"">${command.totalLoiAmount}</td> --%>

							</tr>
						</table>
					</div>
				</c:if>
				
				<div class="text-center padding-bottom-20" id="divSubmit">
					<c:if test="${command.payableFlag eq 'N' }">
						<button type="button" class="btn btn-success" id="submit"
							title='<spring:message code="water.btn.submit" text="Submit" />'
							onclick="checkLastApproval(this)">
							<spring:message code="water.btn.submit" text="Submit"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.payableFlag eq 'Y' }">
					<button type="button" class="btn btn-success"
						title='<spring:message code="water.btn.submit" />'
						onclick="savePlumberLicenseAuthForm(this)" id="submit">
						<spring:message code="water.btn.submit" />
					</button>
					</c:if>
					<input type="button" class="btn btn-danger"
						title='<spring:message code="water.btn.cancel" />'
						onclick="window.location.href='AdminHome.html'" value='<spring:message code="water.btn.cancel" />' />
				</div>
			</form:form>
		</div>
	</div>
</div>
