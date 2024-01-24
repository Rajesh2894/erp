<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/workEstimateSummary.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab4').addClass('active');
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.work.estimation" text="Work Estimation" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WorkEstimate.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="WorkEstimate.html" class="form-horizontal"
				name="workEstimateOverheads" id="workEstimateOverheads">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/works_management/workEstimateTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removeOverHeadById" id="removeOverHeadById" />
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workCode" text="Work Code" /></label>
					<div class="col-sm-4">
						<form:input path="newWorkCode" cssClass="form-control"
							id="definationNumber" readonly="true" data-rule-required="true" />
					</div>
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" text="" /> </label>
					<div class="col-sm-4">
						<form:select path="newWorkId"
							class="form-control chosen-select-no-results mandColorClass"
							id="workDefination" onchange="getDefinationNumber();"
							disabled="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.workDefinitionDto}" var="workDef">
								<form:option code="${workDef.workcode}"
									value="${workDef.workId }">${workDef.workName }</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<div class="text-right text-blue-2 margin-top-15">
					<p>
						<form:hidden path="" id="overheadEstimateAmount"
							value="${command.totalEstimateAmount}" />
						<strong><spring:message code='wms.estimated.amount' /></strong>${command.totalEstimateAmount}
					</p>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="wms.AddOverheadsDetails" text="Add Overheads Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="overheadsDetails">
										<thead>
											<tr>
												<th width=""><spring:message code="ser.no" text="" /><input
													type="hidden" id="srNo"></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="work.estimate.overheads.ItemCode" /></th>
												<th scope="col" width="30%" align="center"><spring:message
														code="work.estimate.overheads.description" /></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="work.estimate.valueType" /></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="work.estimate.rate" /></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="milestone.amount" /></th>
												<th scope="col" width="15%" align="center"><spring:message
														code="wms.Remark" text="Remark" /></th>
												<c:if test="${command.saveMode ne 'V'}">
											    <th align="center" width="10%" scope="col"><spring:message code="works.management.action" text="Action" /><!-- <button
															type="button" onclick="return false;"
															class="btn btn-blue-2 btn-sm  addOverHeadsDetails">
															<i class="fa fa-plus-circle"></i>
														</button> --></th>
												</c:if>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th colspan="5" class="text-right"><spring:message
														code="wms.TotalOverheadsValue"
														text="Total Overheads Value" /></th>
												<th colspan="1"><form:input path="" id="totalOverhead"
														cssClass="form-control text-right" readonly="true" /></th>
												<th colspan="1" class="text-right"></th>
											</tr>
										</tfoot>
										<c:choose>
											<c:when test="${command.saveMode eq 'V'}">
												<c:forEach var="overheadDataList"
													items="${command.estimOverHeadDetDto}" varStatus="status">
													<tr class="appendableClass">
														<td><form:input path="" id="sNo${d}" value="${d + 1}"
																readonly="true" cssClass="form-control" /> <form:hidden
																path="estimOverHeadDetDto[${d}].overHeadId"
																id="overHeadId${d}" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overHeadCode"
																cssClass="form-control" id="overHeadCode${d}"
																readonly="true" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overheadDesc"
																cssClass="form-control" id="overheadDesc${d}"
																readonly="true" /></td>
														<td><form:select
																path="estimOverHeadDetDto[${d}].overHeadvalType"
																cssClass="form-control" id="overHeadvalType${d}"
																disabled="true">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.overHeadPercentLookUp}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overHeadRate"
																cssClass="form-control text-right" placeholder="00.00"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadRate')"
																id="overHeadRate${d}" readonly="true" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overHeadValue"
																cssClass="form-control text-right overheadcalculation"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadValue')"
																id="overHeadValue${d}" readonly="true"
																placeholder="0000.00" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].meRemark"
																cssClass="form-control" readonly="true" id="meRemark${d}" /></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:when test="${fn:length(command.estimOverHeadDetDto) > 0}">
												<c:forEach var="overheadDataList"
													items="${command.estimOverHeadDetDto}" varStatus="status">
													<tr class="appendableClass">
														<td><form:input path="" id="sNo${d}" value="${d + 1}"
																readonly="true" cssClass="form-control" /> <form:hidden
																path="estimOverHeadDetDto[${d}].overHeadId"
																id="overHeadId${d}" /></td>
														<td><form:select
																path="estimOverHeadDetDto[${d}].overHeadCode"
																cssClass="form-control chosen-select-no-results"
																id="overHeadCode${d}"
																onchange="getAllOverheadDetails(${d});">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.overHeadLookUp}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpCode}"
																		code="${lookUp.lookUpId},${lookUp.lookUpDesc}">${lookUp.lookUpCode}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overheadDesc"
																cssClass="form-control" id="overheadDesc${d}"
																readonly="true" /></td>
														<td><form:select
																path="estimOverHeadDetDto[${d}].overHeadvalType"
																cssClass="form-control" id="overHeadvalType${d}"
																onchange="calculateTotalAmount(${d});">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.overHeadPercentLookUp}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overHeadRate"
																cssClass="form-control text-right" placeholder="00.00"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadRate')"
																id="overHeadRate${d}"
																onblur="calculateTotalAmount(${d});" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overHeadValue"
																cssClass="form-control text-right overheadcalculation"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadValue')"
																id="overHeadValue${d}" placeholder="0000.00"
																readonly="true" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].meRemark"
																cssClass="form-control" id="meRemark${d}" /></td>
														<!-- <td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteOverHeadsDetails'><i
																class="fa fa-trash"></i></a></td> -->
														<td class="text-center"><button type="button" title="<spring:message
												code="works.management.add" text="Add" />"
																onclick="return false;" id="addButton${d}"
																class="btn btn-blue-2 btn-sm  addOverHeadsDetails">
																<i class="fa fa-plus-circle"></i>
															</button>
															<button type="button" onclick="return false;" title="<spring:message
																code="works.management.delete" text="Delete" />"
																class='btn btn-danger btn-sm deleteOverHeadsDetails'
																id="delButton${d}">
																<i class="fa fa-trash"></i>
															</button></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tbody>
													<tr class="appendableClass">
														<td><form:input path="" id="sNo${d}" value="1"
																readonly="true" cssClass="form-control" /></td>
														<td><form:select
																path="estimOverHeadDetDto[${d}].overHeadCode"
																cssClass="form-control chosen-select-no-results"
																id="overHeadCode${d}"
																onchange="getAllOverheadDetails(${d});">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.overHeadLookUp}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpCode}"
																		code="${lookUp.lookUpId},${lookUp.lookUpDesc}">${lookUp.lookUpCode}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overheadDesc"
																cssClass="form-control" id="overheadDesc${d}"
																readonly="true" /></td>
														<td><form:select
																path="estimOverHeadDetDto[${d}].overHeadvalType"
																cssClass="form-control" id="overHeadvalType${d}"
																onchange="calculateTotalAmount(${d});">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.overHeadPercentLookUp}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overHeadRate"
																cssClass="form-control text-right" placeholder="00.00"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadRate')"
																id="overHeadRate${d}"
																onblur="calculateTotalAmount(${d});" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].overHeadValue"
																cssClass="form-control text-right overheadcalculation"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadValue')"
																id="overHeadValue${d}" placeholder="0000.00"
																readonly="true" /></td>
														<td><form:input
																path="estimOverHeadDetDto[${d}].meRemark"
																cssClass="form-control" id="meRemark${d}" /></td>		
														<!-- <td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteOverHeadsDetails'><i
																class="fa fa-trash"></i></a></td> -->
														<td class="text-center"><button type="button" title="<spring:message
												code="works.management.add" text="Add" />"
																onclick="return false;"
																class="btn btn-blue-2 btn-sm  addOverHeadsDetails">
																<i class="fa fa-plus-circle"></i>
															</button>
															<button type="button" onclick="return false;" title="<spring:message
																code="works.management.delete" text="Delete" />"
																class='btn btn-danger btn-sm deleteOverHeadsDetails'
																id="delButton${d}">
																<i class="fa fa-trash"></i>
															</button></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</tbody>
											</c:otherwise>
										</c:choose>
									</table>
								</div>
								<div class="text-center margin-top-10">
									<c:if test="${command.saveMode ne 'V'}">
										<button type="button" id="save" title='<spring:message code="work.management.SaveContinue" text="Save & Continue" />'
											class="btn btn-success btn-submit"
											onclick="saveWorkOverHeads(this);">
											<spring:message code="work.management.SaveContinue"
												text="Save & Continue" />
										</button>
									</c:if>
									<c:if test="${command.requestFormFlag eq 'TNDR'}">
										<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
											name="button-Cancel" onclick="backToTender();"
											id="button-Cancel">
											<spring:message code="works.management.back" text="Back" />
										</button>
									</c:if>

									<c:if test="${command.requestFormFlag eq 'AP'}">
										<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
											name="button-Cancel"
											onclick="backSendForApprovalForm(${command.workProjCode});"
											id="button-Cancel">
											<spring:message code="works.management.back" text="Back" />
										</button>
									</c:if>
									<c:if test="${command.saveMode eq 'M'}">
										<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back.to.mb" text="Back To MB" />'
											name="button-Cancel" value="Cancel" style=""
											onclick="backAddMBMasterForm();" id="button-Cancel">
											<spring:message code="works.management.back.to.mb" text="Back To MB" />
										</button>
									</c:if>
									<c:if
										test="${command.requestFormFlag ne 'AP' && command.requestFormFlag ne 'TNDR' && command.saveMode ne 'M'}">
										<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
											name="button-Cancel" value="Cancel" style=""
											onclick="backForm();" id="button-Cancel">
											<spring:message code="works.management.back" text="Back" />
										</button>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>