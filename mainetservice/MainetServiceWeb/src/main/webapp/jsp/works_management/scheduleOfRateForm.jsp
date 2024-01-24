<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<!-- <script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/scheduleofrate.js"></script>
<!-- Schedule of rate Master add or edit data form page jsp -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sor" text="Schedule Of Rate Master" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WmsMaterialMaster.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<form:form action="ScheduleOfRate.html" class="form-horizontal"
				name="ScheduleOfRate" id="scheduleOfRate">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="mstDto.sorId" id="sorId" />
				<form:hidden path="modeType" id="modeType" />
				<form:hidden path="successFlag" id="successFlag" />
				<form:hidden path="removeChildIds" id="removeChildIds" />
				<form:hidden path="subCatMode" id="subCatMode" />

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sor.name" text="SOR Name" /> </label>
					<c:set var="SRMlookUp" value="SRM" />
					<apptags:lookupField items="${command.getLevelData(SRMlookUp)}"
						path="mstDto.sorCpdId"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="work.management.select" isMandatory="true"
						disabled="${command.modeType eq 'E' || command.modeType eq 'V'}" />
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sor.fromdate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="mstDto.sorFromDate" id="sorFromDate"
								class="form-control mandColorClass datepicker" value=""
								readonly="true" data-rule-required="true"
								disabled="${command.modeType eq 'E' || command.modeType eq 'V'}" />
							<label class="input-group-addon" for="sorFromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=sorFromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="sor.todate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="mstDto.sorToDate" id="sorToDate"
								class="form-control datepickerEndDate" value="" disabled="true" />
							<label class="input-group-addon" for="sorToDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=sorToDate></label>
						</div>
					</div>

				</div>

				<c:if test="${command.modeType eq 'E'}">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
								code="" text="Select Chapter type" /></label>
						<div class="col-sm-4">
							<form:select path="chapterId"
								cssClass="form-control chosen-select-no-results "
								id="editChapterId" onchange="editSorByChapter();"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="work.management.select"></spring:message>
								</form:option>
								<c:forEach items="${command.sorCategoryList}" var="category">
									<form:option value="${category.lookUpId}"
										code="${category.lookUpCode}">${category.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
				</c:if>

				<c:if test="${command.modeType eq 'U'}">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
								code="excel.upload" text="Excel Upload" /></label>
						<div class="col-sm-2">
							<apptags:formField fieldPath="excelFileName"
								showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
								currentCount="0" fieldType="7">
							</apptags:formField>
							<small class="text-blue-2" style="padding-left: 10px;">(Upload
								Excel upto 5MB )</small>
						</div>
						<div class="col-sm-2">
							<form:hidden path="excelFileName" id="filePath" />
							<button type="button" class="btn btn-success save"
								name="button-save" value="saveExcel" id="button-save"
								data-loading-text="<i class='fa fa-spinner fa-spin'></i>Processing Order">
								<spring:message code="sor.save.excel" text="Save Excel" />
							</button>

						</div>
						<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
								code="excel.template" text="Excel Template" /></label>
						<div class="col-sm-4">
							<button type="button" class="btn btn-success save"
								name="button-Cancel" value="import"
								onclick="exportSORExcelData();" id="import">
								<spring:message code="excel.template" text="Excel Template" />
							</button>
						</div>
					</div>
				</c:if>

				<c:set var="d" value="0" scope="page"></c:set>
				<div class="tableDiv">
					<table class="table table-bordered table-striped " id="sortbl">
						<thead>
							<tr>
								<th scope="col" width="10%"><spring:message
										code="sor.category" text="Chapter" /><span class="mand">*</span></th>
								<c:if test="${command.subCatMode ne 'Y'}">
									<th scope="col" width="10%"><spring:message
											code="sor.subcategory" text="Sub category" /></th>
								</c:if>
								<th scope="col" width="8%"><spring:message
										code="sor.iteamno" text="Item No." /><span class="mand">*</span></th>
								<th scope="col" width="25%"><spring:message
										code="work.management.description" text="Description" /><span
									class="mand">*</span></th>
								<th scope="col" width="10%"><spring:message
										code="work.management.unit" text="Unit" /><span class="mand">*</span></th>
								<th scope="col" width="10%"><spring:message
										code="sor.baserate" text="Rate" /><span class="mand">*</span></th>
								<c:if test="${command.subCatMode ne 'Y'}">
									<th scope="col" width="8%"><spring:message
											code="sor.lbrrate" text="Labour Rate" /></th>
								</c:if>
								<th scope="col" width="8%"><spring:message
										code="sor.leadupto" text="Initial Lead Upto" /></th>
								<th scope="col" width="9%"><spring:message
										code="sor.leadunit" text="Lead Unit" /></th>
								<th scope="col" width="8%"><spring:message
										code="sor.liftupto" text="Initial Lift Upto(M)" /></th>
								<c:if
									test="${command.modeType eq 'E' || command.modeType eq 'C' }">
									<th scope="col" width="8%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addSOR"> <i
											class="fa fa-plus-circle"></i></a></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when
									test="${fn:length(command.mstDto.detDto)>0 && command.modeType eq 'V'}">
									<c:forEach var="sorData" items="${command.mstDto.detDto}"
										varStatus="status">
										<tr class="sorClass appendableClass">

											<td>${sorData.sordCategoryDesc}</td>
											<c:if test="${command.subCatMode ne 'Y'}">
												<td>${sorData.sordSubCategory}</td>
											</c:if>
											<td>${sorData.sorDIteamNo}</td>
											<td>${sorData.sorDDescription}</td>
											<td>${sorData.sorIteamUnitDesc}</td>
											<td align="right">${sorData.sorBasicRate}</td>

											<c:if test="${command.subCatMode ne 'Y'}">
												<td>${sorData.sorLabourRate}</td>
											</c:if>
											<td>${sorData.leadUpto}</td>
											<td>${sorData.leadUnitDesc}</td>
											<td>${sorData.liftUpto}</td>
											<c:if
												test="${command.modeType eq 'C' || command.modeType eq 'E' }">
												<td class="text-center"><a href='#'
													onclick='return false;'
													class='btn btn-danger btn-sm deleteSOR'> <i
														class="fa fa-trash"></i></a></td>
											</c:if>
											<c:set var="d" value="${d + 1}" scope="page" />
										</tr>
									</c:forEach>
								</c:when>
								<c:when
									test="${command.modeType eq 'E' && command.chapterId ne null}">
									<c:if test="${fn:length(command.mstDto.detDto)>0}">
										<c:forEach var="sorData" items="${command.mstDto.detDto}"
											varStatus="status">
											<tr class="sorClass appendableClass">
												<form:hidden path="mstDto.detDto[${d}].sordId"
													id="sordId${d}" />
												<form:hidden path="mstDto.detDto[${d}].schActiveFlag"
													id="schActiveFlag${d}" value="A" />
												<td><form:select
														path="mstDto.detDto[${d}].sordCategory"
														cssClass="form-control chosen-select-no-results"
														id="sordCategory${d}" onchange="resetIteamNo(this,${d});"
														disabled="${command.modeType =='V' || command.modeType eq 'U'}"
														data-rule-required="true">
														<form:option value="">
															<spring:message code="work.management.select" />
														</form:option>
														<c:forEach items="${command.sorCategoryList}"
															var="category">
															<form:option value="${category.lookUpId}"
																code="${category.lookUpCode}">${category.descLangFirst}</form:option>
														</c:forEach>
													</form:select></td>
												<c:if test="${command.subCatMode ne 'Y'}">
													<td><form:input
															path="mstDto.detDto[${d}].sordSubCategory"
															cssClass="form-control" id="sordSubCategory${d}"
															disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
												</c:if>
												<td><form:input path="mstDto.detDto[${d}].sorDIteamNo"
														onkeyup="inputPreventSpace(event.keyCode,this);"
														cssClass="form-control" id="sorDIteamNo${d}"
														onchange="checkForDuplicateIteamNo(this,${d});"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}"
														data-rule-required="true" /></td>
												<td><form:textarea
														path="mstDto.detDto[${d}].sorDDescription"
														onkeyup="inputPreventSpace(event.keyCode,this);"
														style="margin: 0px; height: 33px;" cssClass="form-control"
														maxlength="4000" id="sorDDescription${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}"
														data-rule-required="true" /></td>
												<td><form:select
														path="mstDto.detDto[${d}].sorIteamUnit"
														cssClass="form-control" data-rule-required="true"
														id="sorIteamUnit${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}">
														<form:option value="">
															<spring:message code="work.management.select" />
														</form:option>
														<c:forEach items="${command.unitLookUpList}" var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<td><form:input path="mstDto.detDto[${d}].sorBasicRate"
														data-rule-required="true"
														onkeypress="return hasAmount(event, this, 6, 2)"
														onchange="getAmountFormatInDynamic((this),'sorBasicRate')"
														placeholder="999999.99" cssClass="form-control text-right"
														id="sorBasicRate${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>

												<c:if test="${command.subCatMode ne 'Y'}">
													<td><form:input
															path="mstDto.detDto[${d}].sorLabourRate"
															onkeypress="return hasAmount(event, this, 5, 2)"
															onchange="getAmountFormatInDynamic((this),'sorLabourRate')"
															placeholder="99999.99" cssClass="form-control text-right"
															id="sorLabourRate${d}"
															disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
												</c:if>
												<td><form:input path="mstDto.detDto[${d}].leadUpto"
														onkeypress="return hasAmount(event, this, 3, 2)"
														onchange="getAmountFormatInDynamic((this),'leadUpto')"
														cssClass="form-control" id="leadUpto${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
												<td><form:select path="mstDto.detDto[${d}].leadUnit"
														cssClass="form-control" id="leadUnit${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}">
														<form:option value="">
															<spring:message code="work.management.select" />
														</form:option>
														<c:forEach items="${command.unitLookUpList}" var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
														</c:forEach>
													</form:select></td>
												<td><form:input path="mstDto.detDto[${d}].liftUpto"
														onkeypress="return hasAmount(event, this, 3, 3)"
														onchange="getAmountFormatInDynamic((this),'liftUpto')"
														cssClass="form-control" id="liftUpto${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
												<c:if
													test="${command.modeType eq 'C' || command.modeType eq 'E' }">
													<td class="text-center"><a href='#'
														onclick='return false;'
														class='btn btn-danger btn-sm deleteSOR'> <i
															class="fa fa-trash"></i></a></td>
												</c:if>
												<c:set var="d" value="${d + 1}" scope="page" />
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty command.mstDto.detDto}">
										<tr class="sorClass appendableClass">
											<form:hidden path="mstDto.detDto[${d}].sordId"
												id="sordId${d}" />
											<form:hidden path="mstDto.detDto[${d}].schActiveFlag"
												id="schActiveFlag${d}" value="A" />
											<td><form:select path="mstDto.detDto[${d}].sordCategory"
													cssClass="form-control chosen-select-no-results"
													id="sordCategory${d}" onchange="resetIteamNo(this,${d});"
													disabled="${command.modeType =='V' || command.modeType eq 'U'}"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="work.management.select" />
													</form:option>
													<c:forEach items="${command.sorCategoryList}"
														var="category">
														<form:option value="${category.lookUpId}"
															code="${category.lookUpCode}">${category.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<c:if test="${command.subCatMode ne 'Y'}">
												<td><form:input
														path="mstDto.detDto[${d}].sordSubCategory"
														cssClass="form-control" id="sordSubCategory${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
											</c:if>
											<td><form:input path="mstDto.detDto[${d}].sorDIteamNo"
													onkeyup="inputPreventSpace(event.keyCode,this);"
													cssClass="form-control" id="sorDIteamNo${d}"
													onchange="checkForDuplicateIteamNo(this,${d});"
													disabled="${command.modeType eq 'V' || command.modeType eq 'U'}"
													data-rule-required="true" /></td>
											<td><form:textarea
													path="mstDto.detDto[${d}].sorDDescription"
													onkeyup="inputPreventSpace(event.keyCode,this);"
													style="margin: 0px; height: 33px;" cssClass="form-control"
													maxlength="4000" id="sorDDescription${d}"
													disabled="${command.modeType eq 'V' || command.modeType eq 'U'}"
													data-rule-required="true" /></td>
											<td><form:select path="mstDto.detDto[${d}].sorIteamUnit"
													cssClass="form-control" data-rule-required="true"
													id="sorIteamUnit${d}"
													disabled="${command.modeType eq 'V' || command.modeType eq 'U'}">
													<form:option value="">
														<spring:message code="work.management.select" />
													</form:option>
													<c:forEach items="${command.unitLookUpList}" var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input path="mstDto.detDto[${d}].sorBasicRate"
													data-rule-required="true"
													onkeypress="return hasAmount(event, this, 6, 2)"
													onchange="getAmountFormatInDynamic((this),'sorBasicRate')"
													placeholder="999999.99" cssClass="form-control text-right"
													id="sorBasicRate${d}"
													disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>

											<c:if test="${command.subCatMode ne 'Y'}">
												<td><form:input
														path="mstDto.detDto[${d}].sorLabourRate"
														onkeypress="return hasAmount(event, this, 5, 2)"
														onchange="getAmountFormatInDynamic((this),'sorLabourRate')"
														placeholder="99999.99" cssClass="form-control text-right"
														id="sorLabourRate${d}"
														disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
											</c:if>
											<td><form:input path="mstDto.detDto[${d}].leadUpto"
													onkeypress="return hasAmount(event, this, 3, 2)"
													onchange="getAmountFormatInDynamic((this),'leadUpto')"
													cssClass="form-control" id="leadUpto${d}"
													disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
											<td><form:select path="mstDto.detDto[${d}].leadUnit"
													cssClass="form-control" id="leadUnit${d}"
													disabled="${command.modeType eq 'V' || command.modeType eq 'U'}">
													<form:option value="">
														<spring:message code="work.management.select" />
													</form:option>
													<c:forEach items="${command.unitLookUpList}" var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input path="mstDto.detDto[${d}].liftUpto"
													onkeypress="return hasAmount(event, this, 3, 3)"
													onchange="getAmountFormatInDynamic((this),'liftUpto')"
													cssClass="form-control" id="liftUpto${d}"
													disabled="${command.modeType eq 'V' || command.modeType eq 'U'}" /></td>
											<c:if
												test="${command.modeType eq 'C' || command.modeType eq 'E' }">
												<td class="text-center"><a href='#'
													onclick='return false;'
													class='btn btn-danger btn-sm deleteSOR'> <i
														class="fa fa-trash"></i></a></td>
											</c:if>
											<c:set var="d" value="${d + 1}" scope="page" />
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="sorClass appendableClass">
										<td><form:select path="mstDto.detDto[${d}].sordCategory"
												cssClass="form-control chosen-select-no-results "
												id="sordCategory${d}" onchange="resetIteamNo(this,${d});"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="work.management.select" />
												</form:option>
												<c:forEach items="${command.sorCategoryList}" var="category">
													<form:option value="${category.lookUpId}"
														code="${category.lookUpCode}">${category.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>
										<c:if test="${command.subCatMode ne 'Y'}">
											<td><form:input
													path="mstDto.detDto[${d}].sordSubCategory"
													cssClass="form-control" id="sordSubCategory${d}" /></td>
										</c:if>
										<td><form:input path="mstDto.detDto[${d}].sorDIteamNo"
												onkeyup="inputPreventSpace(event.keyCode,this);"
												onchange="checkForDuplicateIteamNo(this,${d});"
												cssClass="form-control" id="sorDIteamNo${d}"
												data-rule-required="true" /></td>
										<td><form:textarea
												path="mstDto.detDto[${d}].sorDDescription" maxlength="4000"
												onkeyup="inputPreventSpace(event.keyCode,this);"
												style="margin: 0px; height: 33px;" cssClass="form-control"
												id="sorDDescription${d}" data-rule-required="true" /></td>
										<td><form:select path="mstDto.detDto[${d}].sorIteamUnit"
												cssClass="form-control" id="sorIteamUnit${d}"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="work.management.select" />
												</form:option>
												<c:forEach items="${command.unitLookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input path="mstDto.detDto[${d}].sorBasicRate"
												onkeypress="return hasAmount(event, this, 6, 2)"
												onchange="getAmountFormatInDynamic((this),'sorBasicRate')"
												placeholder="999999.99" cssClass="form-control text-right"
												id="sorBasicRate${d}" data-rule-required="true" /></td>
										<c:if test="${command.subCatMode ne 'Y'}">
											<td><form:input path="mstDto.detDto[${d}].sorLabourRate"
													onkeypress="return hasAmount(event, this, 5, 2)"
													onchange="getAmountFormatInDynamic((this),'sorLabourRate')"
													placeholder="99999.99" cssClass="form-control text-right"
													id="sorLabourRate${d}" /></td>
										</c:if>
										<td><form:input path="mstDto.detDto[${d}].leadUpto"
												onkeypress="return hasAmount(event, this, 3, 2)"
												onchange="getAmountFormatInDynamic((this),'leadUpto')"
												cssClass="form-control" id="leadUpto${d}" /></td>
										<td><form:select path="mstDto.detDto[${d}].leadUnit"
												cssClass="form-control" id="leadUnit${d}">
												<form:option value="">
													<spring:message code="work.management.select" />
												</form:option>
												<c:forEach items="${command.unitLookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input path="mstDto.detDto[${d}].liftUpto"
												onkeypress="return hasAmount(event, this, 3, 3)"
												onchange="getAmountFormatInDynamic((this),'liftUpto')"
												cssClass="form-control" id="liftUpto${d}" /></td>
										<td class="text-center"><a href='#'
											onclick='return false;'
											class='btn btn-danger btn-sm deleteSOR'><i
												class="fa fa-trash"></i></a></td>
										<c:set var="d" value="${d + 1}" scope="page" />
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

				<form:hidden path="" id="errorLengh"
					value="${command.errDetails.size()}" />
				<c:if
					test="${ fn:length(command.errDetails)>0 &&  command.modeType eq 'U'}">
					<h4 class="margin-bottom-10">Error Log</h4>
					<div id="errorTable">
						<c:set var="e" value="0" scope="page" />
						<table class="table table-bordered table-striped"
							id="errorTableRateType">
							<thead>
								<tr>
									<th width="20%"><spring:message
											code="excel.upload.filename" text="File Name" /></th>
									<th width="15%"><spring:message
											code="excel.upload.errordescription" text="Error Description" /></th>
									<th width="65%"><spring:message
											code="excel.upload.errordata" text="Error Data" /></th>
								</tr>
							</thead>
							<c:forEach var="error" items="${command.errDetails}"
								varStatus="status">
								<tr>
									<td><form:input id="fileName${e}"
											path="errDetails[${e}].fileName" class=" form-control"
											readonly="true" /></td>
									<td><form:input id="maDescription${e}"
											path="errDetails[${e}].errDescription" class=" form-control"
											readonly="true" /></td>
									<td><form:input id="maDescription${e}"
											path="errDetails[${e}].errData" class=" form-control"
											readonly="true" /></td>
								</tr>
								<c:set var="e" value="${e + 1}" scope="page" />
							</c:forEach>
						</table>
					</div>
				</c:if>
				<div class="text-center padding-top-20">
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'E'}">
						<button type="button" class="btn  btn-success" id="save"
							name="save" onclick="saveSOR(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'C'}">
						<button type="Reset" class="btn btn-warning" id="createSOR">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'V'}">
						<button type="button" class="btn btn-secondary" name="Download"
							onclick="exportSORExcelData()">
							<i class="fa fa-download padding-right-5"></i>
							<spring:message code="sor.download" text="Download"></spring:message>
						</button>
						<button type="button" class="btn btn-blue-2" name="print"
							onclick="printSORData()">
							<i class="fa fa-print padding-right-5"></i>
							<spring:message code="work.estimate.report.print" text="Print" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" id="back"
						onclick="BackSOR();">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
