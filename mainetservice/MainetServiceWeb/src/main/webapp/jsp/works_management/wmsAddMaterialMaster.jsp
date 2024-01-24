<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript"
	src="js/works_management/wmsmaterialmaster.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.master.summary" text="" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WmsMaterialMaster.html"></apptags:helpDoc>

			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start Form -->
			<form:form action="WmsMaterialMaster.html" class="form-horizontal"
				name="wmsMaterialMaster" id="wmsMaterialMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<form:hidden path="removeChildIds" id="removeChildIds" />
					<!-- Start Each Section -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="material.master.summary" /> </a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<%-- <label for="sortype"
										class="col-sm-2 control-label required-control"><spring:message
											code="material.master.csortype" /> </label>
									<div class="col-sm-4">
										<apptags:lookupField items="${command.getLevelData('SOR')}"
											path="sorType"
											showOnlyLabel="material.master.csortype"
											selectOptionLabelCode="Select"
											cssClass="mandColorClass form-control col-sm-4 sorType" disabled="true"></apptags:lookupField>
									</div> --%>
									<apptags:input labelCode="sor.name"
										path="sorName" cssClass="mandColorClass" isReadonly="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:date fieldclass="datepicker" datePath="fromDate"
										labelCode="material.master.startdate"
										cssClass="mandColorClass " readonly="true" isDisabled="true"></apptags:date>
									<apptags:date fieldclass="datepicker" datePath="toDate"
										labelCode="material.master.enddate" cssClass="mandColorClass "
										readonly="true" isDisabled="true"></apptags:date>
								</div>
							</div>
						</div>
					</div>
					<!-- End Each Section -->
					<div class="form-group">
						<label for="sortype" class="col-sm-2"> <spring:message
								code="material.master.searchratetype" />
						</label>
						<div class="col-sm-4">
							<form:select path="" class="form-control" id="filterType"
								onchange="getFilterCode();">
								<form:option value="0">
									<spring:message code="holidaymaster.select" />
								</form:option>
								<c:forEach items="${command.getLevelData('MTY')}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>


					</div>
					<c:if test="${command.modeType eq 'U' }">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
									code="excel.upload" text="Excel Upload" /></label>
							<div class="col-sm-2">
								<small class="text-blue-2"><spring:message code="work.upload.size" text="(Upload Excel upto 5MB )" /></small>
								<apptags:formField fieldPath="excelFilePath"
									showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
									currentCount="0" fieldType="7">
								</apptags:formField>
							</div>
							<div class="col-sm-2">
								<form:hidden path="excelFilePath" id="filePath" />

								<button type="button" class="btn btn-success save"
									name="button-save" value="saveExcel" style=""
									onclick="uploadExcelFile();" id="button-save">
									<spring:message code="sor.save.excel" text="Save Excel" />
								</button>
							</div>

							<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
									code="additional.master.excel.template" text="Excel Template" /></label>
							<div class="col-sm-4">
								<button type="button" class="btn btn-success save"
									name="button-Cancel" value="import" style=""
									onclick="exportExcelData();" id="import">
									<spring:message code="additional.master.excel.template"
										text="Excel Template" />
								</button>
							</div>
						</div>
					</c:if>
				</div>
				<c:set var="d" value="0" scope="page" />
				<div class="materialMasterAdd" id="materialMasterAdd">
					<table class="table table-bordered table-striped"
						id="materialMasterTab">
						<thead>
							<tr>
								<th width="15%" align="center"><spring:message
										code="material.master.other.rate.type" text="Other Rate Type" /><span
									class="mand">*</span></th>
								<th width="15%" align="center"><spring:message
										code="material.master.itemcode" text="" /><span class="mand">*</span></th>
								<th width="40%" align="center"><spring:message
										code="material.master.matreialname" text="" /><span
									class="mand">*</span></th>
								<th width="12%" align="center"><spring:message
										code="work.management.unit" text="" /><span class="mand">*</span></th>
								<th width="12%" align="center"><spring:message
										code="sor.baserate" text="" /><span class="mand">*</span></th>
								<c:if
									test="${command.modeType eq 'A' || command.modeType eq 'E'}">
									<th scope="col" width="6%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addButton"> <i
											class="fa fa-plus-circle"></i></a></th>
								</c:if>
							</tr>
						</thead>

						<c:choose>
							<c:when
								test="${fn:length(command.materialMasterListDto)>0 && command.modeType eq 'A' || command.modeType eq 'E'}">
								<c:forEach var="sorData"
									items="${command.materialMasterListDto}" varStatus="status">
									<tr class="appendableClass" id="filterId${d}">
										<form:hidden path="materialMasterListDto[${d}].maId"
											id="maId${d}" />
										<td><form:select
												path="materialMasterListDto[${d}].maTypeId"
												class="form-control" id="maTypeId${d}"
												onchange="resetIteamNo(this,${d});">
												<form:option value="0">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.getLevelData('MTY')}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input id="maItemNo${d}"
												path="materialMasterListDto[${d}].maItemNo"
												class=" form-control dataTables_filter" maxlength="50"
												onchange="checkForDuplicateIteamNo(this,${d});" /></td>
										<td><form:input id="maDescription${d}"
												path="materialMasterListDto[${d}].maDescription"
												class=" form-control" maxlength="45" /></td>
										<%-- <td> <form:input id="maItemUnit${d}" path="materialMasterListDto[${d}].maItemUnit" class=" form-control hasNumber"/> </td> --%>
										<td><form:select
												path="materialMasterListDto[${d}].maItemUnit"
												class="form-control" id="maItemUnit${d}">
												<form:option value="0">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.getLevelData('WUT')}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input id="maRate${d}"
												path="materialMasterListDto[${d}].maRate"
												class=" form-control hasNumber text-right"
												onkeypress="return hasAmount(event, this, 5, 2)"
												onchange="getAmountFormatInDynamic((this),'maRate')" /></td>
										<td class="text-center"><a href='#'
											onclick='return false;'
											class='btn btn-danger btn-sm delButton'><i
												class="fa fa-trash"></i></a></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:when
								test="${command.modeType eq 'V' || command.modeType eq 'U'}">
								<c:forEach var="sorData"
									items="${command.materialMasterListDto}" varStatus="status">
									<tr class="appendableClass" id="filterId${d}">
										<form:hidden path="materialMasterListDto[${d}].maId"
											id="maId${d}" />
										<td><form:select
												path="materialMasterListDto[${d}].maTypeId"
												class="form-control" id="maTypeId${d}" disabled="true">
												<form:option value="0">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.getLevelData('MTY')}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input id="maItemNo${d}"
												path="materialMasterListDto[${d}].maItemNo"
												class=" form-control" readonly="true" /></td>
										<td><form:input id="maDescription${d}"
												path="materialMasterListDto[${d}].maDescription"
												class=" form-control" readonly="true" /></td>
										<td><form:select
												path="materialMasterListDto[${d}].maItemUnit"
												class="form-control" id="maItemUnit${d}" disabled="true">
												<form:option value="0">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.getLevelData('WUT')}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input id="maRate${d}"
												path="materialMasterListDto[${d}].maRate"
												class=" form-control hasNumber text-right"
												onkeypress="return hasAmount(event, this, 5, 2)"
												onchange="getAmountFormatInDynamic((this),'maRate')"
												readonly="true" /></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:when test="${command.modeType eq 'A'}">
								<tr class="appendableClass">

									<td><form:select
											path="materialMasterListDto[${d}].maTypeId"
											class="form-control" id="maTypeId${d}"
											onchange="resetIteamNo(this,${d});">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('MTY')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input id="maItemNo${d}"
											path="materialMasterListDto[${d}].maItemNo"
											class=" form-control" maxlength="50"
											onchange="checkForDuplicateIteamNo(this,${d});" /></td>
									<td><form:input id="maDescription${d}"
											path="materialMasterListDto[${d}].maDescription"
											class=" form-control" maxlength="45" /></td>
									<td><form:select
											path="materialMasterListDto[${d}].maItemUnit"
											class="form-control" id="maItemUnit${d}">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input id="maRate${d}"
											path="materialMasterListDto[${d}].maRate"
											class=" form-control hasNumber  text-right"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'maRate')" /></td>
									<td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm delButton'><i
											class="fa fa-trash"></i></a></td>

								</tr>
							</c:when>
						</c:choose>
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
				<!-- End Each Section -->
				<div class="text-center clear padding-10">
					<c:if test="${command.modeType eq 'A' || command.modeType eq 'E'}">
						<button class="btn btn-success save"
							onclick="saveMaterialMasterList(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backMaterialMasterForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->