<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<!-- End JSP Necessary Tags -->
<script type="text/javascript"
	src="js/works_management/wmsprojectmaster.js"></script>
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
				<spring:message code="project.master.title" text="Project Master" />
			</h2>

			<div class="additional-btn">
				<apptags:helpDoc url="WmsMaterialMaster.html"></apptags:helpDoc>
			</div>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span> <spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="WmsProjectMaster.html" class="form-horizontal"
				name="wmsProjectMaster" id="wmsProjectMaster">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->

				<!-- Start Collapsable Panel -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">


					<!-- Start Each Section -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="project.master.title" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<form:hidden path="saveMode" id="saveMode" />
									<form:hidden path="removeFileById" id="removeFileById" />
									<form:hidden path="wmsProjectMasterDto.departmentCode"
										id="departmentCode" />

									<c:if test="${command.saveMode eq 'A'}">
										<apptags:input labelCode="project.master.projcode"
											path="wmsProjectMasterDto.projCode" cssClass="mandColorClass"
											maxlegnth="10" isReadonly="true"></apptags:input>

									</c:if>
									<c:if test="${command.saveMode eq 'E'}">
										<apptags:input labelCode="project.master.projcode"
											path="wmsProjectMasterDto.projCode" cssClass="mandColorClass"
											maxlegnth="10" isReadonly="true"></apptags:input>
									</c:if>
									<label for="select-start-date"
										class="col-sm-2 control-label required-control"><spring:message
											code="project.master.dept" /> </label>
									<div class="col-sm-4">
										<c:if test="${command.saveMode eq 'A'}">
											<form:select path="wmsProjectMasterDto.dpDeptId"
												cssClass="form-control chosen-select-no-results mandColorClass"
												id="dpDeptId" onchange="setDepertmentCode();"
												data-rule-required="true">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.departmentsList}"
													var="departments">
													<form:option value="${departments.dpDeptid }"
														code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
												</c:forEach>
											</form:select>
										</c:if>
										<c:if test="${command.saveMode eq 'E'}">
											<form:select path="wmsProjectMasterDto.dpDeptId"
												cssClass="form-control" id="dpDeptId" disabled="true">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.departmentsList}"
													var="departments">
													<form:option value="${departments.dpDeptid }"
														code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
												</c:forEach>
											</form:select>
										</c:if>
									</div>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="project.master.projnameeng"
										path="wmsProjectMasterDto.projNameEng"
										cssClass="mandColorClass" maxlegnth="250" isMandatory="true"></apptags:textArea>

									<apptags:textArea labelCode="project.master.projnamereg"
										path="wmsProjectMasterDto.projNameReg" cssClass=""
										maxlegnth="250" isMandatory="true"></apptags:textArea>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="project.master.projdesc"
										path="wmsProjectMasterDto.projDescription"
										cssClass="mandColorClass" isMandatory="" maxlegnth="1000"></apptags:textArea>

									<label class="col-sm-2 control-label"><spring:message
											code="project.master.projectTimeline" text="Project Timeline" /></label>

									<div class="col-sm-4">
										<div class="input-group col-sm-12 ">
											<form:input type="text"
												class="form-control hasNumber text-right" path="wmsProjectMasterDto.projPrd"
												id="projPrd" />
											<div class='input-group-field'>

												<c:set var="baseLookupCode" value="UTS" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path="wmsProjectMasterDto.projPrdUnit"
													cssClass="form-control chosen-select-no-results"
													selectOptionLabelCode="selectdropdown" hasId="true" />


											</div>
										</div>
									</div>
								</div>

								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										datePath="wmsProjectMasterDto.projStartDate"
										labelCode="project.master.startdate"></apptags:date>
									<apptags:date fieldclass="datepicker"
										datePath="wmsProjectMasterDto.projEndDate"
										labelCode="project.master.enddate"></apptags:date>
								</div>
								<!-- Remove As Per SUDA UAT -->
								<%--
								<div class="form-group">
									<label for="risk" class="col-sm-2 control-label "><spring:message
											code="project.master.projrisk" /></label>
									<div class="col-sm-4">
										<apptags:lookupField items="${command.getLevelData('RIS')}"
											path="wmsProjectMasterDto.projRisk"
											showOnlyLabel="project.master.type"
											selectOptionLabelCode="Select"
											cssClass=" form-control col-sm-4 "></apptags:lookupField>
									</div>
									<label for="complexity" class="col-sm-2 control-label ">
										<spring:message code="project.master.projcomplexity" />
									</label>
									<div class="col-sm-4">
										<apptags:lookupField items="${command.getLevelData('CMP')}"
											path="wmsProjectMasterDto.projComplexity"
											showOnlyLabel="project.master.type"
											selectOptionLabelCode="Select"
											cssClass=" form-control col-sm-4 "></apptags:lookupField>
									</div>
								</div> 
								 <!-- Remove As Per SUDA UAT -->
								 <div class="form-group">
									<apptags:input labelCode="project.master.estimatecost"
										path="wmsProjectMasterDto.projEstimateCost"
										cssClass="decimal text-right" maxlegnth="16"></apptags:input>
									<apptags:input labelCode="project.master.actualcost"
										path="wmsProjectMasterDto.projActualCost"
										cssClass="decimal text-right" maxlegnth="16"></apptags:input>
								</div> --%>
								<div class="form-group">
									<label for="" class="col-sm-2 control-label "><spring:message
											code="project.master.schemename" /> </label>
									<div class="col-sm-4">
										<form:select path="wmsProjectMasterDto.schId"
											cssClass="form-control chosen-select-no-results " id="schId">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.schemeMasterDtoList}"
												var="schemeName">
												<form:option value="${schemeName.wmSchId }">${schemeName.wmSchNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<div class="col-sm-2">
										<a href="#" onclick="openSchemeMasterForm(this);"
											class="padding-top-5 link"><spring:message
												code='work.management.Clickhere' /></a>
										<spring:message code='project.master.toaddNew.scheme.master' />
									</div>
								</div>
								<!-- Remove As Per SUDA UAT -->

								<%-- <div class="form-group">
									<apptags:date fieldclass="" datePath=""
										cssClass="schemeFromDate date"
										labelCode="scheme.master.startdate" readonly="true"></apptags:date>
									<apptags:date fieldclass="" datePath=""
										labelCode="scheme.master.enddate" cssClass="schemeToDate date"
										readonly="true"></apptags:date>
								</div> --%>

								<div class="form-group">
									<apptags:input path="wmsProjectMasterDto.rsoNumber"
										labelCode="project.master.rsonumber" maxlegnth="38"></apptags:input>
									<apptags:date fieldclass="datepicker"
										datePath="wmsProjectMasterDto.rsoDate"
										labelCode="project.master.rsodate"></apptags:date>
								</div>
								<div class="form-group">
									<label for="" class="col-sm-2 control-label "><spring:message
											code="project.master.project.Status" /></label>
									<div class="col-sm-4">
										<%-- <apptags:lookupField items="${command.getLevelData('PRS')}"
											path="wmsProjectMasterDto.projStatus"
											showOnlyLabel="project.master.project.Status"
											selectOptionLabelCode="Select"
											cssClass=" form-control col-sm-4 "></apptags:lookupField> --%>

										<c:if test="${command.saveMode eq 'A'}">
											<form:select path="wmsProjectMasterDto.projStatus"
												class="form-control chosen-select-no-results"
												id="projStatus">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.getLevelData('PRS')}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}"
														selected="${lookUp.lookUpCode eq 'Y'}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</c:if>
										<c:if test="${command.saveMode ne 'A'}">
											<form:select path="wmsProjectMasterDto.projStatus"
												class="form-control chosen-select-no-results"
												id="projStatus">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.getLevelData('PRS')}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- End Each Section -->
				</div>
				<!-- End Collapsable Panel -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<!-- Start Each Section -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="project.master.projdoc" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:if
									test="${command.saveMode eq 'E' && fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDoc">
											<tr>
												<th width="64%"><spring:message
														code="work.estimate.document.description" /></th>
												<th width="30%"><spring:message
														code="scheme.view.document" text="" /></th>
												<th width="8%"><spring:message
														code="works.management.action" text=""></spring:message></th>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="WmsProjectMaster.html?Download" /></td>
													<td class="text-center"><a href='#' id="deleteFile"
														onclick="return false;" class="btn btn-danger btn-sm"><i
															class="fa fa-trash"></i></a> <form:hidden path=""
															value="${lookUp.attId}" /></td>
												</tr>
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>
								<div id="uploadTagDiv">
									<div class="table-responsive">
										<c:set var="d" value="0" scope="page" />
										<table class="table table-bordered table-striped"
											id="attachDoc">
											<tr>
												<%--  <th><spring:message code="" text="Sr No." /></th> --%>
												<th><spring:message code="work.management.description"
														text="Document Description" /></th>
												<th><spring:message code="scheme.master.upload"
														text="Upload" /></th>
												<th scope="col" width="8%"><a
													onclick='fileCountUpload(this);'
													class="btn btn-blue-2 btn-sm addButton"> <i
														class="fa fa-plus-circle"></i></a></th>
											</tr>

											<tr class="appendableClass">
												<%-- <td>${d+1}</td> --%>
												<td><form:input path="attachments[${d}].doc_DESC_ENGL"
														class=" form-control" /></td>
												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
														isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
													</apptags:formField></td>
												<td class="text-center"><a href='#' id="0_file_${d}"
													onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton'><i
														class="fa fa-trash"></i></a></td>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- End Each Section -->
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button class="btn btn-success add"
						onclick="saveProjectMaster(this);" type="button">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="works.management.save" text="" />
					</button>
					<button class="btn btn-warning reset"
						onclick="resetProjectMaster(this);" type="button">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backProjectMasterForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
				<!-- End button -->
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
</div>
<!-- End of Content -->