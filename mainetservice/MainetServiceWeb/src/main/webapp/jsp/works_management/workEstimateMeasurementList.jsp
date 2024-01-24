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
	src="js/works_management/workEstimateSummary.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script>
jQuery('#tab2').addClass('active');
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.estimate.title" text="Work Estimation" />
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
				name="WorkEstimate" id="WorkEstimate">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/works_management/workEstimateTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="modeCpd" id="modeCpd" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
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

				<div class="table-responsive">
					<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-bordered table-striped" id="datatables">

						<c:choose>
							<c:when test="${command.modeCpd eq 'N'}">
								<thead>
									<tr>
										<th width="10%"><spring:message
												code="work.estimate.sor.chapter" text="Chapter" /></th>
										<th width="10%"><spring:message
												code="work.estimate.subCat" text="Sub Category" /></th>
										<th width="8%"><spring:message
												code="material.master.itemcode" text="Item Code" /></th>
										<th width="15%"><spring:message
												code="work.management.description" text="Description" /></th>
										<th width="8%"><spring:message
												code="work.management.unit" text="Unit" /></th>
										<th width="7%"><spring:message code="sor.baserate"
												text="Basic Rate" /></th>
										<th width="6%"><spring:message
												code="work.estimate.labour" text="Labour" /></th>
										<th width="7%"><spring:message
												code="work.estimate.quantity" text="Quantity" /></th>
										<th width="8%">Through Rate</th>
										<th width="8%"><spring:message code="work.estimate.total"
												text="Total" /></th>
										<th width="12%"><spring:message
												code="works.management.action" text="Action" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="9" style="text-align: right"><spring:message
												code="mb.Total" /></th>
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
										<th width="10%"><spring:message
												code="work.estimate.sor.chapter" text="Chapter" /></th>
										<th width="12%"><spring:message
												code="material.master.itemcode" text="Item Code" /></th>
										<th width="20%"><spring:message
												code="work.management.description" text="Description" /></th>
										<th width="8%"><spring:message
												code="work.estimate.basic.rate" text="Basic Rate" /></th>
										<th width="8%"><spring:message
												code="work.management.unit" text="Unit" /></th>
										<th width="8%"><spring:message
												code="work.estimate.quantity" text="Quantity" /></th>

										<th width="12%"><spring:message
												code="work.estimate.total" text="Total" /></th>
										<th width="12%"><spring:message
												code="works.management.action" text="Action" /></th>
									</tr>
								</thead>

								<tfoot>
									<tr>
										<th colspan="6" style="text-align: right"><spring:message
												code="mb.Total" /></th>
										<th><form:input id="subTotalAmount"
												class=" form-control text-right" maxlength="500" path=""
												readonly="true" /></th>
										<th></th>
									</tr>
								</tfoot>
							</c:otherwise>
						</c:choose>
						<c:forEach var="estimateData"
							items="${command.measurementsheetViewData}" varStatus="status">
							<tr class="appendableClass">
								<td><form:select
										path="measurementsheetViewData[${d}].sordCategory"
										class="form-control" id="sordCategory${d}" readonly="true"
										disabled="true">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>
								<c:if test="${command.modeCpd eq 'N'}">
									<td><form:textarea id="sordSubCategory${d}"
											path="measurementsheetViewData[${d}].sordSubCategory"
											class=" form-control" readonly="true" /></td>

								</c:if>
								<td><form:input id="sorDIteamNo${d}"
										path="measurementsheetViewData[${d}].sorDIteamNo"
										class=" form-control" readonly="true" /></td>

								<td><form:textarea id="sorDDescription${d}"
										path="measurementsheetViewData[${d}].sorDDescription"
										class=" form-control" readonly="true" /></td>

								<td><form:input id="sorBasicRate${d}"
										path="measurementsheetViewData[${d}].sorBasicRate"
										class=" form-control text-right" readonly="true"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>

								<td><form:select
										path="measurementsheetViewData[${d}].sorIteamUnit"
										class="form-control" id="sorIteamUnit${d}" readonly="true"
										disabled="true">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<c:if test="${command.modeCpd eq 'N'}">
									<td><form:input id="sorLabourRate${d}"
											path="measurementsheetViewData[${d}].sorLabourRate"
											class=" form-control text-right" readonly="true"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'sorLabourRate')" /></td>
								</c:if>
								<td><form:input
										path="measurementsheetViewData[${d}].workEstimQuantity"
										cssClass="form-control text-right" id="quanity${d}"
										readonly="true"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'quanity')" /></td>
								<c:if test="${command.modeCpd eq 'N'}">
									<td><form:input
											path="measurementsheetViewData[${d}].workEstimAmount"
											cssClass="form-control text-right" id="total${d}"
											readonly="true"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'total')" /></td>
								</c:if>
								<td><form:input
										path="measurementsheetViewData[${d}].totalEsimateAmount"
										cssClass="form-control text-right measureMentCal"
										id="subTotal${d}" readonly="true"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'subTotal')" /></td>
								<td>
									<button type="button" class="btn btn-darkblue-2 btn-sm"
										title="<spring:message code="work.estimate.add.measurement" text="Add Measurement" />"
										onclick="measurementSheetAction('selectAllSorDataBySorId',${estimateData.sordId},${estimateData.workEstemateId},${estimateData.sorId});">
										<i class="fa fa-calculator"></i>
									</button> <c:if test="${command.modeCpd eq 'N'}">
										<button type="button" class="btn btn-darkblue-2 btn-sm"
											title="Rate Analysis"
											onclick="measurementSheetAction('openRateAnalysis',${estimateData.sordId},${estimateData.workEstemateId},${estimateData.sorId});">
											<i class="fa fa-shopping-cart"></i>
										</button>
										<button type="button" class="btn btn-darkblue-2 btn-sm"
											title="Add Labour"
											onclick="measurementSheetAction('LabourForm',${estimateData.sordId},${estimateData.workEstemateId},${estimateData.sorId});">
											<i class="fa fa-user-plus"></i>
										</button>
										<button type="button" class="btn btn-darkblue-2 btn-sm"
											title="Add Machinary"
											onclick="measurementSheetAction('AddWorkMachinery',${estimateData.sordId},${estimateData.workEstemateId},${estimateData.sorId});">
											<i class="fa fa-cogs"></i>
										</button>
									</c:if>
								</td>
							</tr>

							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</table>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<%-- <c:if test="${command.saveMode ne 'V'}">
						<button type="button" id="save" class="btn btn-success" onclick="">
							<spring:message code="" text="Upload" />
						</button>

					</c:if> --%>
					<c:if test="${command.requestFormFlag eq 'TNDR'}">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" onclick="backToTender();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>
					</c:if>

					<c:if test="${command.requestFormFlag eq 'AP'}">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel"
							onclick="backSendForApprovalForm(${command.workProjCode});"
							id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'M'}">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backAddMBMasterForm();" id="button-Cancel">
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
				<!-- End button -->

			</form:form>

		</div>
	</div>
</div>
