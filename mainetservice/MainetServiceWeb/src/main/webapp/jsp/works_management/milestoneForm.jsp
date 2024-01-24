<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/works_management/milestone.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- <script>
jQuery(document).ready(function(){
	jQuery("#projId").chosen();
});
</script> -->
<style>
	table#milestoneTbl tbody tr td:last-child {
		white-space: nowrap;
	}
</style>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="mileStone.title" text="Milestone" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="Milestone.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="Milestone.html" class="form-horizontal"
				name="milestone" id="milestone">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="saveMode" id="mode" />
				<form:hidden path="removedIds" id="removedIds" />
				<c:if test="${command.saveMode ne 'C'}">
					<form:hidden path="mileStoneDTO.projId" id="editProjId" />
					<form:hidden path="mileStoneDTO.workId" id="workId" />
				</c:if>
				<form:hidden path="" id="checkMileStone" />

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="wms.details" text="Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<c:if test="${command.saveMode eq 'C'}">
										<label class="col-sm-2 control-label "><spring:message
												code="project.master.projname" /></label>



										<div class="col-sm-4">
											<form:select path="mileStoneDTO.projId" id="projId"
												cssClass="form-control chosen-select-no-results mandColorClass"
												onchange="getCreateWorkName(this);">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.projectList}"
													var="activeProjName">
													<c:if test="${userSession.getCurrent().getLanguageId() eq '1'}">
													<form:option value="${activeProjName.projId }"
														code="${activeProjName.workId }">${activeProjName.projNameEng}</form:option>
													</c:if>
													<c:if test="${userSession.getCurrent().getLanguageId() ne '1'}">
													<form:option value="${activeProjName.projId }"
														code="${activeProjName.workId }">${activeProjName.projNameReg}</form:option>
													</c:if>
												</c:forEach>
											</form:select>
										</div>
									</c:if>
									
									<c:if test="${command.saveMode ne 'C'}">
										<label class="col-sm-2 control-label "><spring:message
												code="project.master.projname" /></label>
										<div class="col-sm-4">
											<form:input path="mileStoneDTO.projName" id="projId"
												class="form-control" readonly="true" />
										</div>

									</c:if>
									<c:if test="${command.saveMode eq 'C'}">
										<label class="col-sm-2 control-label"><spring:message
												code="work.def.workname" /></label>
										<div class="col-sm-4">
											<form:select path="mileStoneDTO.workId" id="workId"
												cssClass="form-control chosen-select-no-results mandColorClass"
												onchange="getMilestoneEntry(this);">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
											</form:select>
										</div>
									</c:if>
									<c:if test="${command.saveMode ne 'C'}">
										<label class="col-sm-2 control-label"><spring:message
												code="work.def.workname" /></label>
										<div class="col-sm-4">
											<form:input path="mileStoneDTO.workName" id="workId"
												class="form-control" readonly="true" />
										</div>
									</c:if>
								</div>
								<form:hidden path="" id="mileProjId"
									value="${command.mileStoneDTO.projId}" />
								<form:hidden path="" id="mileWorkId"
									value="${command.mileStoneDTO.workId}" />
								<div class="form-group">

									<label class="col-sm-2 control-label"><spring:message
											code="work.management.milestone" text="Milestone" /></label>
									<div class="col-sm-4">
										<c:if test="${command.saveMode ne 'E' && command.saveMode ne 'V'}">
											<form:select path="mileStoneDTO.mileStoneId"
												id="mileStoneName"
												cssClass="form-control chosen-select-no-results mandColorClass"
												onchange="">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
											</form:select>
										</c:if>
										
										<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
											<form:select path="mileStoneDTO.mileStoneId"
												id="mileStoneName"
												class="form-control chosen-select-no-results"
												onchange="getMilestoneDataByMileNm(value)">
												<form:option value="0">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.milestoneEntryDtos}"
													var="activeProjName">
													<form:option value="${activeProjName.mileId}" code="${activeProjName.milestoneName}">${activeProjName.milestoneName}</form:option>
												</c:forEach>

											</form:select>
										</c:if>
										
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="work.management.add.milestone.activities" text="Add Milestone Activities" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<c:set var="d" value="0" scope="page"></c:set>
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="milestoneTbl">
								
											<thead>
												<tr>
													<th scope="col" width="5%"><spring:message
															code="ser.no" text="Sr.No." /></th>
													<th scope="col" width="10%"><spring:message
															code="work.management.description" text="Description" /><span
														class="mand">*</span></th>
													<th scope="col" width="10%"><spring:message
															code="mileStone.weightage" text="Weightage" /><span
														class="mand">*</span></th>
													<th scope="col" width="10%"><spring:message
															code="material.master.startdate" text="Start Date" /><span
														class="mand">*</span></th>
													<th scope="col" width="10%"><spring:message
															code="material.master.enddate" text="End Date" /><span
														class="mand">*</span></th>
													<c:if test="${command.saveMode eq 'L'}">
														<th scope="col" width="10%"><spring:message
																code="mileStone.percentComplete" text="% Completed" /><span
															class="mand">*</span></th>
													</c:if>
													 <!-- D#131866 -->
													<%-- <c:if test="${command.saveMode eq 'V'}"> --%>
														<th scope="col" width="5%"><spring:message code="works.management.action" text="Action" /></th>
													<%-- </c:if> --%>
												</tr>
											</thead>
											<tfoot>
												<tr>
													<th colspan="2" style="text-align: right"><spring:message
															code="wms.TotalWeightage" /></th>
													<th><form:input id="totalWeightage"
															class=" form-control text-right" maxlength="500" path=""
															readonly="true" /></th>
													<th></th>
													<th></th>
													<c:if test="${command.saveMode ne 'V'}">
														<th></th>
													</c:if>
												</tr>
											</tfoot>
											<tbody>
												<c:choose>
													<c:when
														test="${fn:length(command.milestoneDtoList)>0}">
														<c:forEach var="milestoneData"
															items="${command.milestoneDtoList}">
															<tr class="appendableClass">
																<form:hidden path="milestoneDtoList[${d}].mileId"
																	id="mileId${d}" />
																<td align="center"><form:input path=""
																		cssClass="form-control mandColorClass "
																		id="sequence${d}" value="${d+1}" disabled="true" /></td>
																<td align="center" id="from"><form:input
																		path="milestoneDtoList[${d}].mileStoneDesc"
																		cssClass="form-control mandColorClass"
																		id="mileStoneDesc${d}"
																		disabled="${command.saveMode eq 'V' ? true : false }" /></td>
																<td class="weight"><form:input
																		path="milestoneDtoList[${d}].mileStoneWeight"
																		cssClass="form-control mandColorClass text-right"
																		onkeypress="return hasAmount(event, this, 3, 2)"
																		onchange="getAmountFormatInDynamic((this),'mileStoneWeight')"
																		id="mileStoneWeight${d}" onblur="getValue();"
																		disabled="${command.saveMode eq 'V' ? true : false }" /></td>
																<td align="center"><form:input
																		path="milestoneDtoList[${d}].msStartDate"
																		cssClass="form-control mandColorClass datepicker text-center"
																		id="msStartDate${d}" readonly="true" 
																		disabled="${command.saveMode eq 'V' ? true : false }" /></td>
																<td align="center"><form:input
																		path="milestoneDtoList[${d}].msEndDate"
																		cssClass="form-control mandColorClass datepicker text-center"
																		id="msEndDate${d}" readonly="true"
																		disabled="${command.saveMode eq 'V' ? true : false }" /></td>
																<c:if test="${command.saveMode eq 'L'}">
																	<td align="right" id="percentage"><form:input
																			path="milestoneDtoList[${d}].msPercent"
																			cssClass="form-control mandColorClass text-right"
																			onkeypress="return hasAmount(event, this, 3, 2)"
																			onchange="getAmountFormatInDynamic((this),'msPercent')"
																			id="msPercent${d}" /></td>
																</c:if>
																<%-- <c:if test="${command.saveMode eq 'E'}"> --%>
																	<td align="center">
																		<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
																			<a href="javascript:void(0);" data-toggle="tooltip"
																				data-placement="top" onclick="addEntryData();" 
																				title="<spring:message code="works.management.add" text="Add"></spring:message>"
																				class=" btn btn-success btn-sm"><i
																					class="fa fa-plus-circle"></i></a>
																		</c:if>
																		<a href='#'
																		data-toggle="tooltip" data-placement="top"
																		class="btn btn-danger btn-sm delButton"
																		onclick="deleteEntry($(this),'removedIds');"  title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																			class="fa fa-minus"></i></a></td>
																<%-- </c:if> --%>
																<c:set var="d" value="${d + 1}" scope="page" />
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr class="appendableClass">
															<form:hidden path="milestoneDtoList[${d}].mileId"
																id="mileId${d}" />
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass "
																	id="sequence${d}" value="${d+1}" disabled="true" /></td>
															<td align="center" id="from"><form:input
																	path="milestoneDtoList[${d}].mileStoneDesc"
																	cssClass="form-control mandColorClass"
																	id="mileStoneDesc${d}" /></td>
															<td align="right"><form:input
																	path="milestoneDtoList[${d}].mileStoneWeight"
																	cssClass="form-control mandColorClass text-right"
																	onkeypress="return hasAmount(event, this, 3, 2)"
																	onblur="getValue();"
																	onchange="getAmountFormatInDynamic((this),'mileStoneWeight')"
																	id="mileStoneWeight${d}" /></td>
															<td align="center"><form:input
																	path="milestoneDtoList[${d}].msStartDate"
																	cssClass="form-control  mandColorClass datepicker text-center"
																	id="msStartDate${d}"  readonly="true"/></td>
															<td align="center"><form:input
																	path="milestoneDtoList[${d}].msEndDate"
																	cssClass="form-control mandColorClass datepicker text-center"
																	id="msEndDate${d}" readonly="true" /></td>
															<c:if test="${command.saveMode eq 'L'}">
																<td align='right' id="percentage"><form:input
																		path="milestoneDtoList[${d}].msPercent"
																		onkeypress="return hasAmount(event, this, 3, 2)"
																		onchange="getAmountFormatInDynamic((this),'msPercent')"
																		cssClass="form-control mandColorClass text-right "
																		id="msPercent${d}" /></td>
															</c:if>
															<td align="center">
																<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
																	<a href="javascript:void(0);" data-toggle="tooltip"
																		data-placement="top" onclick="addEntryData();" 
																		title="<spring:message code="works.management.add" text="Add"></spring:message>"
																		class=" btn btn-success btn-sm"><i
																			class="fa fa-plus-circle"></i></a>
																</c:if>
																<a href='#' data-toggle="tooltip"
																data-placement="top"
																class="btn btn-danger btn-sm delButton"
																onclick="deleteEntry($(this),'removedIds');" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																	class="fa fa-minus"></i></a></td>
															<c:set var="d" value="${d + 1}" scope="page" />
														</tr>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>


				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button type="button" id="save" class="btn btn-success btn-submit" title='<spring:message code="works.management.save" text="Save" />'
							onclick="saveData(this);" title="<spring:message code="works.management.save" text="Save"></spring:message>">
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button class="btn btn-warning" type="button" id="" title='<spring:message code="works.management.reset" text="Reset" />'
							onclick="resetMilestoneForm();"  title="<spring:message code="works.management.reset" text="Reset"></spring:message>">
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
						name="button-Cancel" value="Cancel" style=""  title="<spring:message code="works.management.back" text="Back"></spring:message>"
						onclick="window.location.href='Milestone.html'" id="button-Cancel">
						<spring:message code="works.management.back" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>
