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
	src="js/works_management/wmsprojectmaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

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


									<apptags:input labelCode="project.master.projcode"
										path="wmsProjectMasterDto.projCode" cssClass="" maxlegnth="10"
										isReadonly="true"></apptags:input>

									<label for="select-start-date" class="col-sm-2 control-label"><spring:message
											code="project.master.dept" /> </label>
									<div class="col-sm-4">
										<form:select path="wmsProjectMasterDto.dpDeptId"
											cssClass="form-control chosen-select-no-results"
											id="dpDeptId" onchange="setDepertmentCode();"
											data-rule-required="true" disabled="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.departmentsList}"
												var="departments">
												<form:option value="${departments.dpDeptid }"
													code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="project.master.projnameeng"
										path="wmsProjectMasterDto.projNameEng" cssClass=""
										maxlegnth="250" isDisabled="true"></apptags:textArea>

									<apptags:textArea labelCode="project.master.projnamereg"
										path="wmsProjectMasterDto.projNameReg" cssClass=""
										maxlegnth="250" isDisabled="true"></apptags:textArea>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="project.master.projdesc"
										path="wmsProjectMasterDto.projDescription" cssClass=""
										isDisabled="true"></apptags:textArea>

									<label class="col-sm-2 control-label"><spring:message
											code="project.master.projectTimeline" text="Project Timeline" /></label>

									<div class="col-sm-4">
										<div class="input-group col-sm-12 ">
											<form:input type="text"
												class="form-control hasNumber text-right" path="wmsProjectMasterDto.projPrd"
												id="contToPeriod" disabled="true"/>
											<div class='input-group-field'>

												<c:set var="baseLookupCode" value="UTS" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path="wmsProjectMasterDto.projPrdUnit"
													cssClass="form-control chosen-select-no-results"
													selectOptionLabelCode="selectdropdown" hasId="true" disabled="true"/>


											</div>
										</div>
									</div>
								</div>

								<div class="form-group">
									<apptags:date fieldclass=""
										datePath="wmsProjectMasterDto.startDateDesc"
										labelCode="project.master.startdate" cssClass=""
										readonly="true"></apptags:date>

									<apptags:date fieldclass=""
										datePath="wmsProjectMasterDto.endDateDesc"
										labelCode="project.master.enddate" cssClass="mandColorClass"
										readonly="true"></apptags:date>

								</div>
								<%-- <div class="form-group">

									<label for="risk" class="col-sm-2 control-label "><spring:message
											code="project.master.projrisk" /></label>
									<div class="col-sm-4">
										<apptags:lookupField items="${command.getLevelData('RIS')}"
											path="wmsProjectMasterDto.projRisk"
											showOnlyLabel="project.master.type"
											selectOptionLabelCode="Select" disabled="true"
											cssClass=" form-control col-sm-4 "></apptags:lookupField>
									</div>

									<label for="complexity" class="col-sm-2 control-label ">
										<spring:message code="project.master.projcomplexity" />
									</label>
									<div class="col-sm-4">
										<apptags:lookupField items="${command.getLevelData('CMP')}"
											path="wmsProjectMasterDto.projComplexity"
											showOnlyLabel="project.master.type"
											selectOptionLabelCode="Select" disabled="true"
											cssClass=" form-control col-sm-4 "></apptags:lookupField>
									</div>

								</div> --%>

								<%-- <div class="form-group">

									<apptags:input labelCode="project.master.estimatecost"
										path="wmsProjectMasterDto.projEstimateCost"
										cssClass="decimal text-right" isDisabled="true" maxlegnth="16"></apptags:input>

									<apptags:input labelCode="project.master.actualcost"
										path="wmsProjectMasterDto.projActualCost"
										cssClass="decimal text-right" isDisabled="true" maxlegnth="16"></apptags:input>
								</div> --%>

								<div class="form-group">
									<apptags:input path="wmsProjectMasterDto.rsoNumber"
										labelCode="project.master.rsonumber" cssClass="mandColorClass"
										maxlegnth="20" isReadonly="true" ></apptags:input>
									<apptags:date fieldclass=""
										datePath="wmsProjectMasterDto.rsoDateDesc"
										labelCode="project.master.rsodate" cssClass="mandColorClass"
										readonly="true"></apptags:date>
								</div>

								<div class="form-group">

									<label for="" class="col-sm-2 control-label "><spring:message
											code="project.master.project.Status" /></label>
									<div class="col-sm-4">
										<apptags:lookupField items="${command.getLevelData('PRS')}"
											path="wmsProjectMasterDto.projStatus"
											showOnlyLabel="project.master.project.Status"
											selectOptionLabelCode="Select" disabled="true"
											cssClass=" form-control col-sm-4 "></apptags:lookupField>
									</div>
								</div>

							</div>
						</div>
					</div>
					<!-- End Each Section -->
				</div>
			
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="datatablesProjectView">
						<thead>
							<tr>
								<th width="20%" align="center"><spring:message
										code="work.def.workname" text="Work Name" /></th>
								<th width="15%" align="center"><spring:message
										code="work.def.workCode" text="Work Code" /></th>
								<th width="19%" align="center"><spring:message
										code="work.def.startDate" text="Start Date" /></th>
								<th width="18%" align="center"><spring:message
										code="work.def.endDate" text="End Date" /></th>
								<th width="10%" align="center"><spring:message
										code="work.status" text="Work Status" /></th>
								<th width="10%" align="center"><spring:message
										code="wms.WorkEstimateCost" text="Work Estimate Coast" /></th>
								<c:if test="${command.saveMode ne 'SM'}">
									<th width="8%" align="center"><spring:message
											code="works.management.action" text="Action" /></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.workDefDtoList}" var="DefDto">
								<tr>
									<td>${DefDto.workName}</td>
									<td align="center">${DefDto.workcode}</td>
									<td align="center">${DefDto.startDateDesc}</td>
									<td align="center">${DefDto.endDateDesc}</td>
									<td align="center">${DefDto.workStatus}</td>
									<td align="right">${DefDto.workEstAmt}</td>
									<c:if test="${command.saveMode ne 'SM'}">
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="viewWorkDef(${DefDto.workId})" title="View">
												<i class="fa fa-eye"></i>
											</button>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backProjectMasterForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>

					<%-- <c:otherwise>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backProjectMasterForm();" id="button-Cancel">
								<spring:message code="" text="" />
							</button>
						</c:otherwise>

					</c:choose> --%>

				</div>
				<!-- End button -->
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
</div>
<!-- End of Content -->