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
<style>
#Applicant .form-group > label[for="dwzid3"],
#Applicant .form-group > label[for="dwzid3"] + div {
	margin-top: 0.7rem;
}
</style>
<script>
	$(document).ready(
			function() {
				$('#frmPlumberLicenseForm').validate({
					onkeyup : function(element) {
						this.element(element);
						console.log('onkeyup fired');
					},
					onfocusout : function(element) {
						this.element(element);
						console.log('onfocusout fired');
					}
				});

				$(".datepicker").datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					yearRange : "-100:-0",
					maxDate : 0,
					onSelect : function(selected, evnt) {
						updateExperienceDetails();
					}

				});

				if ($("#chargesId").val() == 'Y') {
					$(".hideConfirmBtn").hide();
					$('input[type=text]').attr('disabled', true);
					$('select').attr("disabled", true);
					$("#academicTableId").find("input,button,textarea,select")
							.attr("disabled", "disabled");
					$("#experienceTableId")
							.find("input,button,textarea,select").attr(
									"disabled", "disabled");
					$(".academicAddRow").attr("disabled", "disabled");
					$(".academicDeleteRow").attr("disabled", "disabled");
					$(".experienceAddRow").attr("disabled", "disabled");
					$(".experienceDeleteRow").attr("disabled", "disabled");
					$("#oflPaymentMode").attr('disabled', false);
					$("#bankAccId").attr('disabled', false);
				}

				$("#academicTableId").on(
						'click',
						'.academicAddRow',
						function(e) {

							$('#errorDivId').hide();
							var errorList = [];
							var row = e;
							var val = (row + 1);
							/* $('.academicAppendable').each(
									function(i) {
										errorList = validateAcademicDetails(
												errorList, i);
									}); */
							errorList = validateAcademicDetails(errorList);

							if (errorList.length > 0) {
								displayErrorsOnPage(errorList);
								return false;
							}
							$(".monthPick").datepicker("destroy");
							var content = $(this).closest('tr').clone();
							$(this).closest("tr").after(content);
							var clickedIndex = $(this).parent().parent()
									.index() - 1;

							content.find("input:text").val("");

							$(content).find("td:eq(0)").attr("id",
									"academicSrNoId" + val);
							$(content).find("select:eq(0)").attr("id",
									"qualificationId" + val);
							/*
							 * $(content).find("input:text:eq(0)").attr("id",
							 * "qualificationId" + val);
							 */
							$(content).find("input:text:eq(0)").attr("id",
									"instituteNameId" + val);
							$(content).find("input:text:eq(1)").attr("id",
									"instituteAddrsId" + val);
							/* $(content).find("input:text:eq(2)").attr("id",
									"passYearId" + val); */
							$(content).find("input:text:eq(2)").attr("id",
									"passMonthId" + val);
							$(content).find("input:text:eq(3)").attr("id",
									"percentGradeId" + val);
							content.find('.academicAddRow').attr("id",
									"academicAddButton" + val);
							content.find('.academicDeleteRow').attr("id",
									"academicDelButton" + val);

							content.find("select:eq(0)").attr(
									"name",
									"plumberQualificationDTOList["
											+ (clickedIndex + 1)
											+ "].plumQualification");
							content.find("input:text:eq(0)").attr(
									"name",
									"plumberQualificationDTOList["
											+ (clickedIndex + 1)
											+ "].plumInstituteName");
							content.find("input:text:eq(1)").attr(
									"name",
									"plumberQualificationDTOList["
											+ (clickedIndex + 1)
											+ "].plumInstituteAddress");
							/* content.find("input:text:eq(2)").attr(
									"name",
									"plumberQualificationDTOList["
											+ (clickedIndex + 1)
											+ "].plumPassYear"); */
							content.find("input:text:eq(2)").attr(
									"name",
									"plumberQualificationDTOList["
											+ (clickedIndex + 1)
											+ "].plumPassMonth");
							content.find("input:text:eq(3)").attr(
									"name",
									"plumberQualificationDTOList["
											+ (clickedIndex + 1)
											+ "].plumPercentGrade");
							reOrderQualificationTableIdSequence();
							$('.monthPick').datepicker({
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "-100:-0",
								maxDate : 0,
							});

						});

				$("#academicTableId").on('click', '.academicDeleteRow',
						function() {

							if ($("#academicTableId tr").length != 2) {
								$(this).parent().parent().remove();
							} else {
								alert("You can not delete first row");
							}

							reOrderQualificationTableIdSequence();
						});

				$("#experienceTableId").on(
						'click',
						'.experienceAddRow',
						function(e) {

							$('#errorDivId').hide();
							var errorList = [];
							var row = e;
							$('.experienceAppendable').each(
									function(i) {
										errorList = validateExperienceDetails(
												errorList, i);
									});

							if (errorList.length > 0) {
								displayErrorsOnPage(errorList);
								return false;
							}

							$(".datepicker").datepicker("destroy");
							var content = $(this).closest("tr").clone();
							$(this).closest("tr").after(content);

							content.find("select:eq(0)").attr("value", "");
							content.find("input:text").val("");

							$(content).find("td:eq(0)").attr("id",
									"experienceSrNoId" + (row + 1));
							$(content).find("input:text:eq(0)").attr("id",
									"companyNameId" + (row + 1));
							$(content).find("input:text:eq(1)").attr("id",
									"companyAddrsId" + (row + 1));
							$(content).find("input:text:eq(2)").attr("id",
									"expFromDateId" + (row + 1));
							$(content).find("input:text:eq(3)").attr("id",
									"expToDateId" + (row + 1));
							$(content).find("input:text:eq(4)").attr("id",
									"experienceId" + (row + 1));
							$(content).find("select:eq(0)").attr("id",
									"firmTypeId" + (row + 1));

							content.find('.experienceAddRow').attr("id",
									"experienceAddButton" + (row + 1));
							content.find('.experienceDeleteRow').attr("id",
									"experienceDelButton" + (row + 1));

							content.find("input:text:eq(0)").attr(
									"name",
									"plumberExperienceDTOList[" + (row + 1)
											+ "].plumCompanyName");
							content.find("input:text:eq(1)").attr(
									"name",
									"plumberExperienceDTOList[" + (row + 1)
											+ "].plumCompanyAddress");
							content.find("input:text:eq(2)").attr(
									"name",
									"plumberExperienceDTOList[" + (row + 1)
											+ "].expFromDate");
							content.find("input:text:eq(3)").attr(
									"name",
									"plumberExperienceDTOList[" + (row + 1)
											+ "].expToDate");
							content.find("input:text:eq(4)").attr(
									"name",
									"plumberExperienceDTOList[" + (row + 1)
											+ "].experience");
							content.find("select:eq(0)").attr(
									"name",
									"plumberExperienceDTOList[" + (row + 1)
											+ "].firmType");

							$(".datepicker").datepicker({
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "-100:-0",
								onSelect : function(selected, evnt) {
									updateExperienceDetails();
								}
							});

							reOrderExperienceTableIdSequence();
						});

				$("#experienceTableId").on('click', '.experienceDeleteRow',
						function() {

							if ($("#experienceTableId tr").length != 3) {

								$(this).parent().parent().remove();
							} else {
								alert("You can not delete first row");
							}
							reOrderExperienceTableIdSequence();
						});

			});
</script>
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
				id="frmPlumberLicenseForm">
				<spring:message code="water.field.req.validation.message" text="This field is required." var="reqField"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<form:hidden id="tempFrmDateId" path="" value="" />


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">


					<%-- <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail> --%>
					<apptags:waterReconnection wardZone="WWZ"></apptags:waterReconnection>

					<div class="form-group">
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
								<small class="text-blue-2"> <spring:message code="water.plumberLicense.uploadedPhoto.validatn"
															      text="(Upload Image File upto 2 MB)" />
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#child-level" tabindex="-1"><spring:message
										code="water.plumberLicense.academicProfessionDetails" /></a>
							</h4>
						</div>
						<div id="child-level" class="panel-collapse collapse">
							<div class="panel-body">

								<c:set var="d" value="0" scope="page" />
								<c:set var="e" value="0" scope="page" />
								<div class="table-responsive" id="academicId">
									<label class="hide" for="qualificationId0"><spring:message
											code="water.plumberLicense.Qualification" /></label> <label
										class="hide" for="instituteNameId0"><spring:message
											code="water.plumberLicense.instituteName" /></label> <label
										class="hide" for="instituteAddrsId0"><spring:message
											code="water.plumberLicense.instituteAddress" /></label>
									<%-- <label
										class="hide" for="passYearId0"><spring:message
											code="water.plumberLicense.yearOfPassing" /></label --%>
									<label class="hide" for="passMonthId0"><spring:message
											code="water.plumberLicense.dateOfPassing" /></label> <label
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
													code="water.plumberLicense.dateOfPassing" /><span
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
																<spring:message code='master.selectDropDwn'
																	text="select" />
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
															data-rule-required="true" data-msg-required="${reqField}" /></td>

													<td><form:input
															path="plumberQualificationDTOList[${d}].plumInstituteAddress"
															class="form-control" id="instituteAddrsId${d}"
															data-rule-required="true" data-msg-required="${reqField}" /></td>

													<%-- <td><form:input
															path="plumberQualificationDTOList[${d}].plumPassYear"
															class="form-control hasNumber" id="passYearId${d}"
															placeholder="YYYY" maxlength="4"
															data-rule-required="true" /></td> --%>

													<td><form:input
															path="plumberQualificationDTOList[${d}].plumPassMonth"
															class="form-control monthPick" id="passMonthId${d}"
															placeholder="MM" data-rule-required="true" readonly="true" data-msg-required="${reqField}"/></td>

													<td><form:input
															path="plumberQualificationDTOList[${d}].plumPercentGrade"
															class="form-control gradeClass" id="percentGradeId${d}"
															data-rule-required="true" maxlength="5" data-msg-required="${reqField}"/></td>

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
																id="qualificationId${d}">
																<form:option value="">
																	<spring:message code='master.selectDropDwn'
																		text="select" />
																</form:option>
																<c:forEach items="${command.lookUpList}" var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select>
														</td>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumInstituteName"
																class="form-control" id="instituteNameId${d}" /></td>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumInstituteAddress"
																class="form-control" id="instituteAddrsId${d}" /></td>

														<%-- <td><form:input
																path="plumberQualificationDTOList[${d}].plumPassYear"
																class="form-control hasNumber" id="passYearId${d}"
																maxlength="4" /></td> --%>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumPassMonth"
																class="form-control monthPick" id="passMonthId${d}" readonly="true"/></td>

														<td><form:input
																path="plumberQualificationDTOList[${d}].plumPercentGrade"
																class="form-control" id="percentGradeId${d}" /></td>

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
									data-parent="#accordion_single_collapse" href="#experience" tabindex="-1"><spring:message
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
											<th width="100"><spring:message code="water.Action" /></th>
										</tr>

										<c:choose>
											<c:when test="${empty command.plumberExperienceDTOList}">




												<tr class="experienceAppendable">

													<td id="experienceSrNoId${e}">${e + 1}</td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].plumCompanyName"
															class="form-control" id="companyNameId${e}" /></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].plumCompanyAddress"
															class="form-control" id="companyAddrsId${e}" /></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].expFromDate"
															cssClass="datepicker cal form-control"
															id="expFromDateId${e}" readonly="true" /></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].expToDate"
															cssClass="datepicker cal form-control"
															id="expToDateId${e}" readonly="true" /></td>

													<td><form:input
															path="plumberExperienceDTOList[${e}].experience"
															readonly="true" class="form-control"
															id="experienceId${e}" /></td>

													<td><c:set var="baseLookupCode" value="PFT" /> <form:select
															path="plumberExperienceDTOList[${e}].firmType"
															class="form-control" id="firmTypeId${e}">
															<form:option value="0">
																<spring:message
																	code="water.plumberLicense.valMsg.selectFrmType"
																	text="Select Firm Type" />
															</form:option>
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
																class="form-control" id="companyNameId${e}" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].plumCompanyAddress"
																class="form-control" id="companyAddrsId${e}" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].expFromDate"
																cssClass="datepicker cal form-control"
																id="expFromDateId${e}" readonly="true" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].expToDate"
																cssClass="datepicker cal form-control"
																id="expToDateId${e}" readonly="true" /></td>

														<td><form:input
																path="plumberExperienceDTOList[${e}].experience"
																readonly="true" class="form-control"
																id="experienceId${e}" /></td>

														<td><c:set var="baseLookupCode" value="PFT" /> <form:select
																path="plumberExperienceDTOList[${e}].firmType"
																class="form-control" id="firmTypeId${e}">
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


					<div class="padding-top-10 text-center hideConfirmBtn">
						<button type="button" class="btn btn-success"
							title='<spring:message code="water.btn.proceed" />'
							id="confirmToProceedId"
							onclick="getChecklistAndChargesPlumberLicense(this);">
							<spring:message code="water.btn.proceed" />
						</button>
						<%-- Defect #154088 --%>
						<button type="button" class="btn btn-warning" id="resetBtn" title='<spring:message code="rstBtn" text="Reset" />'>
							<spring:message code="rstBtn" text="Reset" />
						</button>
					</div>
					<form:hidden id="chargesId" path=""
						value="${command.checkListNCharges}" />

					<c:if test="${command.checkListNCharges eq 'Y'}">
						<c:if
							test="${(not empty command.checkList) and (fn:length(command.checkList) > 0 )}">


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

																	<c:choose>
																		<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
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
																				fieldPath="plumberLicenseReqDTO.fileCheckList[${lk.index+1}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="BND_COMMOM_MAX_SIZE"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
																				currentCount="${lk.index+1}"
																				folderName="${lk.index+1}" />
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

						<c:if test="${command.isFree ne 'Y'}">

							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control text-right"
										value="${command.offlineDTO.amountToShow}" maxlength="12"></input>
									<a class="fancybox fancybox.ajax text-small text-info"
										href="PlumberLicenseForm.html?showChargeDetails"><spring:message
											code="water.lable.name.chargedetail" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						</c:if>
						<div class="text-center padding-bottom-20" id="divSubmit">
							<button type="button" class="btn btn-success"
								onclick="savePlumberLicenseForm(this)" id="submit">
								<spring:message code="water.btn.submit" />
							</button>
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='CitizenHome.html'" value="<spring:message code="water.nodues.cancel" text="Cancel" />" />
						</div>
					</c:if>

				</div>

			</form:form>
		</div>
	</div>
</div>

