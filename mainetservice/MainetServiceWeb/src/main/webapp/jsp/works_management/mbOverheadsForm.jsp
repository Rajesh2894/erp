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
	src="js/works_management/measurementBook.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
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
				<spring:message code="mb.title" text="Measurement Book" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="MeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>

			<form:form action="MeasurementBook.html" class="form-horizontal"
				name="mbOverheads" id="mbOverheads">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/works_management/measurementBookTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<form:input type="hidden" path="estimateMasterDto.workId"
					id="workId" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removeOverHeadById" id="removeOverHeadById" />
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.order.workOrder.no" text="work Order number" /></label>
					<div class="col-sm-4">
						<form:input path="workOrderDto.workOrderNo"
							cssClass="form-control" id="" readonly="true"
							data-rule-required="true" />
					</div>
				</div>

				<div class="text-right text-blue-2 margin-top-15">
					<p>
						<form:hidden path="overHeadAmt" id="overheadEstimateAmount"
							value="${command.totalMbAmount}" />
						<strong><spring:message code='wms.PreviousEstimateValue' /></strong>${command.totalMbAmount}
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
												<th scope="col" width="50%" align="center"><spring:message
														code="work.estimate.overheads.description" /></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="work.estimate.valueType" /></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="work.estimate.rate" /></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="work.estimate.overheads.value" /></th>
												<th scope="col" width="10%" align="center"><spring:message
														code="work.estimate.overheads.actualAmt"
														text="Actual Amount" /></th>
												<%-- <c:if test="${command.saveMode ne 'V'}">
													<th class="text-center" width="5%"><button
															type="button" onclick="return false;"
															class="btn btn-blue-2 btn-sm  addOverHeadsDetails">
															<i class="fa fa-plus-circle"></i>
														</button></th>
												</c:if> --%>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th colspan="6" class="text-right"><spring:message
														code="wms.TotalOverheadsValue"
														text="Total Overheads Value" /></th>
												<th colspan="1"><form:input path="totalMbOverheadValue" id="totalOverhead"
														cssClass="form-control text-right" readonly="true" /></th>
												<th colspan="1" class="text-right"></th>
											</tr>
										</tfoot>
										<c:choose>
											<c:when test="${command.saveMode eq 'V'}">

												<c:forEach var="overheadDataList"
													items="${command.mbeOverHeadDetDtoList}" varStatus="status">
													<tr class="appendableClass">
														<form:input type="hidden"
															path="mbeOverHeadDetDtoList[${d}].workId" id="workId${d}"
															value="${command.newWorkId }" />
														<td><form:input path="" id="sNo${d}" value="${d + 1}"
																readonly="true" cssClass="form-control" /> <form:hidden
																path="mbeOverHeadDetDtoList[${d}].overHeadId"
																id="overHeadId${d}" /></td>

														<td class="text-center"><form:input
																path="mbeOverHeadDetDtoList[${d}].overHeadCode"
																cssClass="form-control" id="overHeadCode${d}"
																readonly="true" /></td>
														<td><form:input
																path="mbeOverHeadDetDtoList[${d}].overheadDesc"
																cssClass="form-control" id="overheadDesc${d}"
																readonly="true" /></td>
														<td class="text-center"><form:select
																path="mbeOverHeadDetDtoList[${d}].overHeadvalType"
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
														<td class="text-right"><form:input
																path="mbeOverHeadDetDtoList[${d}].overHeadRate"
																cssClass="form-control text-right" placeholder="00.00"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadRate')"
																id="overHeadRate${d}" readonly="true" /></td>
														<td class="text-right"><form:input
																path="mbeOverHeadDetDtoList[${d}].overHeadValue"
																cssClass="form-control text-right" readonly="true"
																placeholder="0000.00" /></td>
														<td class="text-right"><form:input
																path="mbeOverHeadDetDtoList[${d}].actualAmount"
																cssClass="form-control text-right overheadcalculation"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'actualAmount')"
																id="actualAmount${d}" placeholder="0000.00" disabled="true"/></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:when
												test="${fn:length(command.mbeOverHeadDetDtoList) > 0}">

												<c:forEach var="overheadDataList"
													items="${command.mbeOverHeadDetDtoList}" varStatus="status">
													<tr class="appendableClass">
														<form:input type="hidden"
															path="mbeOverHeadDetDtoList[${d}].workId" id="workId${d}"
															value="${command.newWorkId}" />
														<td><form:input path="" id="sNo${d}" value="${d + 1}"
																readonly="true" cssClass="form-control" /> <form:hidden
																path="mbeOverHeadDetDtoList[${d}].overHeadId"
																id="overHeadId${d}" /></td>
														<td class="text-center">${overheadDataList.overHeadCode}</td>
														<td>${overheadDataList.overheadDesc}</td>
														<td class="text-center"><form:select
																path="mbeOverHeadDetDtoList[${d}].overHeadvalType"
																cssClass="form-control" disabled="true"
																id="overHeadvalType${d}">
																<c:forEach items="${command.overHeadPercentLookUp}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td class="text-right">${overheadDataList.overHeadRate}</td>
														<td class="text-right">${overheadDataList.overHeadValue}</td>


														<td class="text-right"><form:input
																path="mbeOverHeadDetDtoList[${d}].actualAmount"
																cssClass="form-control text-right overheadcalculation"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'actualAmount')"
																id="actualAmount${d}" placeholder="0000.00" /></td>

														<!-- <td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteOverHeadsDetails'><i
																class="fa fa-trash"></i></a></td> -->
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<%-- <tbody>

													<tr class="appendableClass">
														<td><form:input path="" id="sNo${d}" value="1"
																readonly="true" cssClass="form-control" /></td>
														<form:input type="hidden"
															path="mbeOverHeadDetDtoList[${d}].workId" id="workId${d}"
															value="${command.newWorkId}" />
														<td><form:select
																path="mbeOverHeadDetDtoList[${d}].overHeadCode"
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
																path="mbeOverHeadDetDtoList[${d}].overheadDesc"
																cssClass="form-control" id="overheadDesc${d}"
																readonly="true" /></td>
														<td><form:select
																path="mbeOverHeadDetDtoList[${d}].overHeadvalType"
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
																path="mbeOverHeadDetDtoList[${d}].overHeadRate"
																cssClass="form-control text-right" placeholder="00.00"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadRate')"
																id="overHeadRate${d}"
																onblur="calculateTotalAmount(${d});" /></td>
														<td><form:input
																path="mbeOverHeadDetDtoList[${d}].overHeadValue"
																cssClass="form-control text-right overheadcalculation"
																onkeypress="return hasAmount(event, this, 11, 2)"
																onchange="getAmountFormatInDynamic((this),'overHeadValue')"
																id="overHeadValue${d}" placeholder="0000.00"
																readonly="true" /></td>


														<!-- <td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteOverHeadsDetails'><i
																class="fa fa-trash"></i></a></td> -->
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</tbody> --%>
											</c:otherwise>
										</c:choose>
									</table>
								</div>
								<div class="text-center margin-top-10">
									<c:if test="${command.saveMode ne 'V'}">
										<button type="button" id="save"
											class="btn btn-success btn-submit"
											onclick="saveWorkOverHeads(this);">
											<i class="fa fa-sign-out padding-right-5"></i>
											<spring:message code="work.management.SaveContinue"
												text="Save & Continue" />
										</button>
									</c:if>
									<c:if test="${command.requestFormFlag eq 'TNDR'}">
										<button type="button" class="button-input btn btn-danger"
											name="button-Cancel" onclick="backToTender();"
											id="button-Cancel">
											<i class="fa fa-chevron-circle-left padding-right-5"></i>
											<spring:message code="works.management.back" text="" />
										</button>
									</c:if>

									<c:if test="${command.requestFormFlag eq 'AP'}">
										<button type="button" class="button-input btn btn-danger"
											name="button-Cancel" onclick="" id="button-Cancel">
											<i class="fa fa-chevron-circle-left padding-right-5"></i>
											<spring:message code="works.management.back" text="" />
										</button>
									</c:if>
									<c:if test="${command.saveMode eq 'M'}">
										<button type="button" class="button-input btn btn-danger"
											name="button-Cancel" value="Cancel" style="" onclick=""
											id="button-Cancel">
											<i class="fa fa-chevron-circle-left padding-right-5"></i>
											<spring:message code="" text="Back To MB" />
										</button>
									</c:if>
									<c:if
										test="${command.requestFormFlag ne 'AP' && command.requestFormFlag ne 'TNDR' && command.saveMode ne 'M'}">
										<button type="button" class="button-input btn btn-danger"
											name="button-Cancel" value="Cancel" style=""
											onclick="backForm();" id="button-Cancel">
											<i class="fa fa-chevron-circle-left padding-right-5"></i>
											<spring:message code="works.management.back" text="" />
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