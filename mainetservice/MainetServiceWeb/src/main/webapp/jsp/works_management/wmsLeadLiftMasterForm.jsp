<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/wmsLeadLiftMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="leadlift.master.title" text="Lead-Lift Master" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<form:form action="WmsLeadLiftMaster.html" class="form-horizontal"
				name="WmsLeadLiftMaster" id="WmsLeadLiftMaster">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden path="formMode" id="formMode" />
				<form:hidden path="removeChildIds" id="removeChildIds" />
				<form:hidden path="wmsLeadLiftMasterDto.sorId" id="sorId" />
				<div class="main">
					<div class="form-group">

						<label class="col-sm-2 control-label"><spring:message
								code="sor.name" text="SOR Name" /></label>
						<div class="col-sm-4">
							<form:input path="wmsLeadLiftMasterDto.sorName"
								cssClass="form-control" disabled="true" />
						</div>

						<label class="label-control col-sm-2"><spring:message
								code="material.master.ratetype" text="Rate Type" /></label>
						<div class="col-sm-4">
							<form:select class="form-control"
								path="wmsLeadLiftMasterDto.leLiFlag" id="leLi" disabled="true">
								<form:option value="">
									<spring:message code="work.management.select" text="Select" />
								</form:option>
								<form:option value="L">
									<spring:message code="leadlift.master.lead" text="Lead" />
								</form:option>
								<form:option value="F">
									<spring:message code="leadlift.master.lift" text="Lift" />
								</form:option>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<apptags:date fieldclass="datepicker"
							labelCode="leadlift.master.fromDate"
							datePath="wmsLeadLiftMasterDto.sorFromDate" readonly="true"
							isDisabled="true"></apptags:date>
						<apptags:date fieldclass="datepicker"
							labelCode="leadlift.master.toDate"
							datePath="wmsLeadLiftMasterDto.sorToDate" readonly="true"
							isDisabled="true"></apptags:date>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2"><spring:message
								code="lead.lift.master.is.slab" text="isSlab?" /></label>
						<div class="col-sm-4">
							<label class="checkbox-inline"> <form:checkbox
									path="activeChkBox" id="leLiSlabFlg"
									disabled="${command.formMode eq 'V' ? true : false }" />
							</label>
							<form:hidden path="wmsLeadLiftMasterDto.leLiSlabFlg"
								id="activeChkBox" />
						</div>

					</div>

					<c:if test="${command.formMode eq 'U' }">
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
									name="button-save" value="saveExcel" style=""
									onclick="uploadExcelFile();" id="button-save">
									<spring:message code="sor.save.excel" text="Save Excel" />
								</button>
							</div>

							<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
									code="" text="Excel Template" /></label>
							<div class="col-sm-4">
								<button type="button" class="btn btn-success save"
									name="button-Cancel" value="import" style=""
									onclick="exportExcelData();" id="import">
									<spring:message code="excel.template" text="Excel Template" />
								</button>
							</div>
						</div>
					</c:if>
				</div>
				<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="lelitbl">
						<thead>
							<tr>
								<th scope="col" width="3%"><spring:message code="ser.no"
										text="Sr.No." /></th>
								<th scope="col" width="10%"><spring:message
										code="leadlift.master.from" text="From" /><span class="mand">*</span></th>
								<th scope="col" width="10%"><spring:message
										code="leadlift.master.to" text="To" /><span class="mand">*</span></th>
								<th scope="col" width="10%"><spring:message
										code="sor.baserate" text="Charges" /><span class="mand">*</span></th>
								<th scope="col" width="10%"><spring:message
										code="work.management.unit" text="Unit" /><span class="mand">*</span></th>
								<th scope="col" width="5%"></th>

							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when
									test="${fn:length(command.wmsleadLiftTableDtos)>0 && command.formMode eq 'E'}">
									<c:forEach var="leLiData"
										items="${command.wmsleadLiftTableDtos}">
										<tr class="leLiClass appendableClass">
											<form:hidden path="wmsleadLiftTableDtos[${d}].leLiId"
												id="leLiId${d}" />
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${d+1}" disabled="true" /></td>
											<td align="center" id="from"><form:input
													path="wmsleadLiftTableDtos[${d}].leLiFrom"
													onkeypress="return hasAmount(event, this, 5, 2)"
													onchange="getAmountFormatInDynamic((this),'leLiFrom')"
													cssClass="form-control mandColorClass" id="leLiFrom${d}" />
											</td>
											<td align="center"><form:input
													path="wmsleadLiftTableDtos[${d}].leLiTo"
													onkeypress="return hasAmount(event, this, 5, 2)"
													onchange="getAmountFormatInDynamic((this),'leLiTo')"
													cssClass="form-control mandColorClass " id="leLiTo${d}" /></td>
											<td align="right"><form:input
													path="wmsleadLiftTableDtos[${d}].leLiRate"
													onkeypress="return hasAmount(event, this, 5, 2)"
													onchange="getAmountFormatInDynamic((this),'leLiRate')"
													cssClass="form-control mandColorClass" id="leLiRate${d}" /></td>
											<td align="center"><form:select
													path="wmsleadLiftTableDtos[${d}].leLiUnit"
													cssClass="form-control mandColorClass" id="leLiUnit${d}">
													<form:option value="">
														<spring:message code='work.management.select' />
													</form:option>
													<c:forEach items="${command.lookUpList}" var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>
											
											<c:if test="${command.formMode eq 'C' || command.formMode eq 'E'}">
													<td align="center"><a href="javascript:void(0);"
														data-toggle="tooltip" data-placement="top"
														onclick="addEntryData();" class=" btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a> <a
														href="javascript:void(0);" data-placement="top"
														class="btn btn-danger btn-sm delButton"
														onclick="deleteTableRow('lelitbl',$(this),'removeChildIds');"><i
															class="fa fa-minus"></i></a></td>
												</c:if>
																		
											<c:set var="d" value="${d + 1}" scope="page" />
										</tr>
									</c:forEach>
								</c:when>

								<c:when
									test="${command.formMode eq 'V' || command.formMode eq 'U'}">
									<c:forEach var="leLiData"
										items="${command.wmsleadLiftTableDtos}">
										<tr class="leLiClass appendableClass">
											<form:hidden path="wmsleadLiftTableDtos[${d}].leLiId"
												id="leLiId${d}" />
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${d+1}" disabled="true" /></td>
											<td align="center" id="from"><form:input
													path="wmsleadLiftTableDtos[${d}].leLiFrom"
													cssClass="form-control mandColorClass " id="leLiFrom${d}"
													disabled="true" /></td>
											<td align="center"><form:input
													path="wmsleadLiftTableDtos[${d}].leLiTo"
													cssClass="form-control mandColorClass " id="leLiTo${d}"
													disabled="true" /></td>
											<td align="right"><form:input
													path="wmsleadLiftTableDtos[${d}].leLiRate"
													cssClass="form-control mandColorClass" id="leLiRate${d}"
													disabled="true" /></td>
											<td align="center"><form:select
													path="wmsleadLiftTableDtos[${d}].leLiUnit"
													cssClass="form-control mandColorClass" id="leLiUnit${d}"
													disabled="true">
													<form:option value="">
														<spring:message code='work.management.select' />
													</form:option>
													<c:forEach items="${command.lookUpList}" var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>
											<c:set var="d" value="${d + 1}" scope="page" />
										</tr>
									</c:forEach>
								</c:when>

								<c:otherwise>
									<tr class="leLiClass appendableClass">
										<form:hidden path="wmsleadLiftTableDtos[${d}].leLiId"
											id="leLiId${d}" />

										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass " id="sequence${d}"
												value="${d+1}" disabled="true" /></td>
										<td align="center" id="from"><form:input
												path="wmsleadLiftTableDtos[${d}].leLiFrom"
												onkeypress="return hasAmount(event, this, 5, 2)"
												onchange="getAmountFormatInDynamic((this),'leLiFrom')"
												cssClass="form-control mandColorClass" id="leLiFrom${d}" />
										</td>
										<td align="center"><form:input
												path="wmsleadLiftTableDtos[${d}].leLiTo"
												onkeypress="return hasAmount(event, this, 5, 2)"
												onchange="getAmountFormatInDynamic((this),'leLiTo')"
												cssClass="form-control mandColorClass" id="leLiTo${d}" /></td>
										<td align="right"><form:input
												path="wmsleadLiftTableDtos[${d}].leLiRate"
												onkeypress="return hasAmount(event, this, 5, 2)"
												onchange="getAmountFormatInDynamic((this),'leLiRate')"
												cssClass="form-control mandColorClass"
												onkeyup="inputPreventSpace(event.keyCode,this);"
												id="leLiRate${d}" /></td>
										<td align="center"><form:select
												path="wmsleadLiftTableDtos[${d}].leLiUnit"
												cssClass="form-control mandColorClass" id="leLiUnit${d}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.lookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										
											<c:if test="${command.formMode eq 'C' || command.formMode eq 'E'}">
													<td align="center"><a href="javascript:void(0);"
														data-toggle="tooltip" data-placement="top"
														onclick="addEntryData();" class=" btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a> <a
														href="javascript:void(0);" data-placement="top"
														class="btn btn-danger btn-sm delButton"
														onclick="deleteTableRow('lelitbl',$(this),'removeChildIds');"><i
															class="fa fa-minus"></i></a></td>
												</c:if>
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
					test="${ fn:length(command.errDetails)>0 &&  command.formMode eq 'U'}">
					<h4 class="margin-bottom-10">Error Log</h4>
					<div id="errorTable">
						<c:set var="e" value="0" scope="page" />
						<table class="table table-bordered table-striped"
							id="errorTableLeLiType">
							<thead>
								<tr>
									<th width="20%"><spring:message
											code="excel.upload.filename" text="File Name" /></th>
									<th width="45%"><spring:message
											code="excel.upload.errordescription" text="Error Description" /></th>
									<th width="35%"><spring:message
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

				<!-- Start button -->
				<div class="text-center clear padding-10">

					<c:if test="${command.formMode eq 'C' || command.formMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveData(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>

					<c:if test="${command.formMode eq 'C'}">
						<button class="btn btn-warning" onclick="resetTable(this);"
							type="button">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>

					<c:if test="${command.formMode eq 'V'}">
						<button class="btn btn-primary hidden-print" type="button"
							id="PrintButn" onclick="viewReport();">
							<i class="fa fa-print padding-right-5"></i>
							<spring:message code="work.estimate.report.print" text="Print" />
						</button>
						<button type="button" class="btn btn-success"
							onclick="exportExcelData();">
							<i class="fa fa-download padding-right-5"></i>
							<spring:message code="sor.download" text="Download" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backLeLiMasterForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>

				</div>
				<!-- End button -->
			</form:form>
		</div>
	</div>
</div>

