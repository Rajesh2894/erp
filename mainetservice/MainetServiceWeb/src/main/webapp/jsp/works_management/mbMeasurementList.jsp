<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/measurementBook.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script>
jQuery('#tab2').addClass('active');
</script>
<style>
textarea::placeholder {
	color: black !important;
}
</style>
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
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>

			<form:form action="" class="form-horizontal" name="" id="">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/works_management/measurementBookTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:input type="hidden" path="workOrderDto.workId" id="workId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.order.workOrder.no" text="work Order number" /></label>
					<div class="col-sm-4">
						<form:input path="workOrderDto.workOrderNo"
							cssClass="form-control" id="" readonly="true"
							data-rule-required="true" />
					</div>
				</div>

				<div class="table-responsive">
					<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-bordered table-striped"
						id="datatablesList">
						<c:choose>
							<c:when test="${cpdModeCatSor eq 'N'}">
								<thead>
									<tr>
										<th scope="col" width="6%" align="center"><spring:message
												code="mb.sorNonSor" text="SOR/Non-SOR" /></th>
										<th width="8%"><spring:message
												code="work.estimate.sor.chapter" text="Chapter" /></th>
										<th width="8%"><spring:message
												code="work.estimate.subCat" text="Sub Category" /></th>
										<th width="8%"><spring:message
												code="material.master.itemcode" text="Item Code" /></th>
										<th width="20%"><spring:message
												code="work.management.description" text="Description" /></th>
										<th width="6%"><spring:message
												code="work.management.unit" text="Unit" /></th>
										<th scope="col" width="6%" align="center"><spring:message
												code="mb.BillofQuantity" text="Bill of Quantity" /></th>
										<th width="6%"><spring:message
												code="wms.CumulativeQuantity" text="Cumulative Quantity" /></th>
										<th width="6%"><spring:message code="sor.baserate"
												text="Rate" /></th>
										<th width="6%"><spring:message code="mb.ConsumedQuantity"
												text="Consumed Quantity" /></th>
										<th width="14%"><spring:message code="milestone.amount"
												text="Amount" /></th>
										<th width=""><spring:message
												code="works.management.action" text="Action" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="9" style="text-align: right"><spring:message
												code="mb.Total" text="Total:" /></th>
										<th><form:input id="subTotalAmount"
												class=" form-control text-right" maxlength="500" path=""
												readonly="true" /></th>
										<th></th>
									</tr>
								</tfoot>
							</c:when>
							<c:otherwise>
								<thead>
									<tr>
										<th scope="col" width="6%" align="center"><spring:message
												code="mb.sorNonSor" text="SOR/Non-SOR" /></th>
										<th width="10%"><spring:message
												code="work.estimate.sor.chapter" text="Chapter" /></th>
										<th width="8%"><spring:message
												code="material.master.itemcode" text="Item Code" /></th>
										<th width="16%"><spring:message
												code="work.management.description" text="Description" /></th>
										<th width="6%"><spring:message
												code="work.management.unit" text="Unit" /></th>
										<th scope="col" width="10%" align="center"><spring:message
												code="mb.BillofQuantity" text="Bill of Quantity" /></th>
										<th width="6%"><spring:message
												code="wms.CumulativeQuantity" text="Cumulative Quantity" /></th>
										<th width="10%"><spring:message code="sor.baserate"
												text="Rate" /></th>
										<th width="10%"><spring:message
												code="mb.ConsumedQuantity" text="Consumed Quantity" /></th>
										<th width="14%"><spring:message code="milestone.amount"
												text="Amount" /></th>
										<th width="10%"><spring:message
												code="works.management.action" text="Action" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="9" style="text-align: right"><spring:message
												code="mb.Total" text="Total:" /></th>
										<th><form:input id="subTotalAmount"
												class=" form-control text-right" maxlength="500" path=""
												readonly="true" /></th>
										<th></th>
									</tr>
								</tfoot>
							</c:otherwise>
						</c:choose>
						<tbody>
							<c:forEach var="estimateData"
								items="${command.savedEstimateDtoList}" varStatus="status">
								<c:if
									test="${estimateData.workEstimFlag eq 'S' ||  estimateData.workEstimFlag eq 'MS'}">
									<tr class="appendableClass">
										<td class="text-center">${estimateData.workMbFlag}</td>
										<td><form:select
												path="savedEstimateDtoList[${d}].sordCategory"
												class="form-control" readonly="true" disabled="true">
												<form:option value="0">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.getLevelData('WKC')}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<c:if test="${cpdModeCatSor eq 'N'}">
											<td>${estimateData.sordSubCategory}</td>
										</c:if>
										<td class="text-center">${estimateData.sorDIteamNo}</td>
										<td><form:textarea path=""
												placeholder="${estimateData.sorDDescription}"
												class="form-control" style="margin: 0px; height: 33px;"
												readonly="true" title="${estimateData.sorDDescription}" /></td>
										<td class="text-center">${estimateData.sorIteamUnitDesc}</td>
										<td align="right">${estimateData.workEstimQuantity}</td>
										<td align="right">${estimateData.cummulativeAmount}</td>
										<td align="right">${estimateData.sorBasicRate}</td>
										<c:choose>
											<c:when
												test="${estimateData.workEstimQuantity < estimateData.workEstimQuantityUtl}">
												<td class="red text-right">${estimateData.workEstimQuantityUtl}</td>
											</c:when>
											<c:otherwise>
												<td align="right">${estimateData.workEstimQuantityUtl}</td>
											</c:otherwise>
										</c:choose>

										<td><form:input path=""
												class="form-control hasNumber text-right"
												value="${estimateData.totalMbAmount}" id="totalMbAmount${d}"
												readonly="true" /></td>
										<td>
											<button type="button" class="btn btn-darkblue-2 btn-sm"
												title="<spring:message code="work.estimate.add.measurement" text="Add Measurement"/>"
												onclick="measurementSheetAction('measurementListByworkEstimateId',${estimateData.workEstemateId},${estimateData.mbId},${estimateData.mbDetId},'${estimateData.estimateType}',${estimateData.sorId})">
												<i class="fa fa-calculator"></i>
											</button> <%-- <c:if test="${estimateData.estimateType ne 'U'}"> --%>
											<c:if test="${cpdModeCatSor eq 'N'}">
												<button type="button" class="btn btn-darkblue-2 btn-sm"
													title="Rate Analysis"
													onclick="measurementSheetAction('openRateAnalysis',${estimateData.workEstemateId},${estimateData.mbId},${estimateData.mbDetId},'${estimateData.estimateType}',${estimateData.sorId})">
													<i class="fa fa-shopping-cart"></i>
												</button>
											</c:if>
										</td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="confirmBoxforMbDetail()">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="work.management.SaveContinue"
								text="Save & Continue" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="openMbMasForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
				<!-- End button -->
			</form:form>
		</div>
	</div>
</div>
