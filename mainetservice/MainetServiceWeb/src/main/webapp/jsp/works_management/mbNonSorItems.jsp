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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab3').addClass('active');
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
				name="mbNonSor" id="mbNonSor">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/works_management/measurementBookTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
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
						id="nonSordataTables">
						<thead>
							<tr>
								<th rowspan="2" scope="col" width="6%" align="center"><spring:message
										code="mb.sorNonSor" text="SOR/Non-SOR" /></th>
								<th rowspan="2" width="5%"><spring:message
										code="material.master.itemcode" text="Item Code" /></th>
								<th rowspan="2" width="20%"><spring:message
										code="work.management.description" text="Description" /></th>
								<th rowspan="2" width="6%"><spring:message
										code="work.management.unit" text="Unit" /></th>
								<th rowspan="2" width="10%"><spring:message
										code="sor.baserate" text="Rate" /></th>
								<th rowspan="2" width="10%"><spring:message
										code="wms.CumulativeQuantity" text="Cumulative Quantity" /></th>		
								<th colspan="2" width="16%"><spring:message
										code="work.estimate.quantity" text="Quantity" /></th>

								<th colspan="2" width="16%"><spring:message
										code="milestone.amount" text="Amount" /></th>
								<th rowspan="2" width="16%"><spring:message
										code="wms.remark" text="Remark" /></th>

							</tr>

							<tr>
								<th><spring:message code="mb.estimated" text="Estimate" /></th>
								<th><spring:message code="mb.actual" text="Actual" /></th>
								<th><spring:message code="mb.estimated" text="Estimate" /></th>
								<th><spring:message code="mb.actual" text="Actual" /></th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th></th>
								<th colspan="7" style="text-align: right"><spring:message
										code="mb.Total" text="Total:" /></th>
								<th><form:input id="subTotalAmount"
										class=" form-control text-right" maxlength="500" path=""
										readonly="true" /></th>
								<th colspan="8"></th>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach var="estimateData"
								items="${command.nonSorEstimateDtoList}" varStatus="status">

								<%-- <c:if test="${estimateData.workEstimFlag eq 'N' || estimateData.workEstimFlag eq 'MN'}"> --%>

								<tr class="appendableClass">
									<form:input type="hidden"
										path="mbDetNonSorDtoList[${d}].workEstemateId"
										value="${estimateData.workEstemateId}" />
									<form:input type="hidden"
										path="mbDetNonSorDtoList[${d}].workEstimateType"
										value="${estimateData.estimateType}" />
									<form:input type="hidden" path="mbDetNonSorDtoList[${d}].mbId"
										value="${estimateData.mbId}" />
									<form:input type="hidden" path="mbDetNonSorDtoList[${d}].mbdId"
										value="${estimateData.mbDetId}" />
									<form:input type="hidden"
										path="mbDetNonSorDtoList[${d}].sorRate"
										value="${command.estimateMasterDto.sorBasicRate}" />
									<form:input type="hidden"
										path="mbDetNonSorDtoList[${d}].workFlag"
										value="${estimateData.workEstimFlag}" />

									<td>${estimateData.workMbFlag}</td>
									<td align="right">${estimateData.sorDIteamNo}</td>
									<td>${estimateData.sorDDescription}</td>
									<td>${estimateData.sorIteamUnitDesc}</td>
									<td><form:input id="sorBasicRate${d}" path=""
											class=" form-control text-right" readonly="true"
											value="${estimateData.sorBasicRate}"
											title="${estimateData.sorBasicRate}" /></td>
									<td>${estimateData.cummulativeAmount}</td>		
									<td><form:input path=""
											value="${estimateData.workEstimQuantity}"
											class="form-control text-right" readonly="true"
											id="workEstQuantity${d}"
											title="${estimateData.workEstimQuantity}" /></td>
									<td><form:input id="workActMbQuantity${d}"
											path="mbDetNonSorDtoList[${d}].workActualQty"
											onblur="calculateNonSorMbAmt();" placeholder="0.0000"
											class="form-control text-right"
											onkeypress="return hasAmount(event, this, 10, 4)"
											onchange="getAmountFormatInDynamic((this),'workActMbQuantity')"
											readonly="${command.saveMode eq 'V' ? true:false }" /></td>

									<td><form:input id="workEstAmount${d}" path=""
											value="${estimateData.workEstimAmount}"
											class=" form-control text-right" readonly="true"
											title="${estimateData.workEstimAmount}" /></td>
									<td><form:input id="workActAmt${d}"
											path="mbDetNonSorDtoList[${d}].workActualAmt"
											class=" form-control text-right" maxlength="12"
											onkeypress="return hasAmount(event, this, 10, 2)"
											placeholder="0.00" readonly="true"
											onchange="getAmountFormatInDynamic((this),'workActAmt')" /></td>
									<td><form:input path="" class="form-control"
											value="${estimateData.meRemark}" readonly="true"
											title="${estimateData.meRemark}" /></td>
								</tr>

								<c:set var="d" value="${d + 1}" scope="page" />
								<%-- </c:if> --%>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button class="btn btn-success "
							onclick="saveMbNonSorItems(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="work.management.SaveContinue" text="Submit" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="measurementSheet();" id="backButton">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
				<!-- End button -->

			</form:form>

		</div>
	</div>
</div>
