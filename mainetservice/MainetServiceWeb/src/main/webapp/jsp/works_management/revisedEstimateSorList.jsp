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
	src="js/works_management/revisedEstimate.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<!-- <script type="text/javascript" src="js/mainet/file-upload.js"></script> -->

<c:choose>
	<c:when test="${command.workeReviseFlag eq 'N'}">

		<c:set var="d" value="0" scope="page" />
		<table class="table table-bordered table-striped"
			id="estimateSorRevDetails">
			<thead>
				<tr>
					<th scope="col" width="3%"><spring:message code=""
							text="Sr.No." /></th>
					<th width="10%"><spring:message
							code="work.estimate.sor.chapter" text="Chapter" /><span
						class="mand">*</span></th>
					<th width="8%"><spring:message code="material.master.itemcode"
							text="Item Code" /><span class="mand">*</span></th>
					<th width="15%"><spring:message code="work.estimate.item.desc"
							text="Item Description" /><span class="mand">*</span></th>
					<th width="8%"><spring:message code="leadlift.master.unit"
							text="Unit" /><span class="mand">*</span></th>
					<th width="8%"><spring:message code="work.estimate.basic.rate"
							text="Basic Rate" /><span class="mand">*</span></th>
					<th width="8%"><spring:message code="work.estimate.quantity"
							text="Quantity" /><span class="mand">*</span></th>

					<th width="10%"><spring:message code="work.estimate.total"
							text="Total" /><span class="mand">*</span></th>
					<c:if test="${command.saveMode ne 'V'}">
						<th class="text-center" width="10%"><a
							title='<spring:message code="works.management.add" />'
							onclick='return false;'
							class="btn btn-blue-2 btn-sm addRevisedButtonForSor"> <i
								class="fa fa-plus-circle"></i></a></th>
					</c:if>
					<c:if test="${command.saveMode eq 'V'}">
						<th width="10%" align="center"><spring:message
								code="sor.action" text="Action" /></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(command.revisedEstimateSorList) > 0}">
						<c:forEach var="estimateData"
							items="${command.revisedEstimateSorList}" varStatus="status">
							<tr class="revisedAppendableClass">

								<td align="center"><form:input path="command"
										cssClass="form-control mandColorClass " id="sequence${d}"
										value="${d+1}" disabled="true" /></td>
								<td><form:select
										path="command.revisedEstimateSorList[${d}].sordCategory"
										class="form-control" id="sordCategory${d}"
										disabled="${command.saveMode eq 'V' }"
										onchange="getAllItemsRevisedList(${d});">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select> <form:hidden
										path="command.revisedEstimateSorList[${d}].workEstemateId"
										id="sorCheckedEstimationId${d}" /> <form:hidden
										path="command.revisedEstimateSorList[${d}].sorId"
										id="sorId${d}" /> <form:hidden
										path="command.revisedEstimateSorList[${d}].sordId"
										id="sorDetailsId${d}" /> <%-- <form:hidden
										path="command.revisedEstimateSorList[${d}].sorIteamUnit"
										id="sorIteamUnitHidden${d}" /> --%></td>
								<td><form:select id="sorDIteamNo${d}"
										path="command.revisedEstimateSorList[${d}].sorDIteamNo"
										class="form-control chosen-select-no-results"
										disabled="${command.saveMode eq 'V' }"
										onchange="getSorRevisedItmsDescription(this,${d});">
										<form:option selected="selected"
											value="${estimateData.sorDIteamNo}">${estimateData.sorDIteamNo}
									</form:option>
									</form:select></td>
								<td><form:textarea id="sorDDescription${d}"
										path="command.revisedEstimateSorList[${d}].sorDDescription"
										class=" form-control" readonly="true" /></td>

								<td><form:select
										path="command.revisedEstimateSorList[${d}].sorIteamUnit"
										class="form-control" id="sorIteamUnit${d}" readonly="true">
										<form:option value="">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input id="sorBasicRate${d}"
										path="command.revisedEstimateSorList[${d}].sorBasicRate"
										class=" form-control text-right calculation" readonly="true"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>
								<td><form:input
										path="command.revisedEstimateSorList[${d}].workEstimQuantity"
										cssClass="form-control text-right calculation"
										id="quanity${d}" readonly="true"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'quanity')" /></td>

								<td><form:input
										path="command.revisedEstimateSorList[${d}].workEstimAmount"
										cssClass="form-control text-right amount" id="total${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'total')"
										readonly="true" /></td>
								<td class="text-center"><c:if
										test="${command.saveMode ne 'V'}">
										<a href='#' onclick='return false;'
											title='<spring:message code="works.management.delete" />'
											class='btn btn-danger btn-sm delRevButton'><i
											class="fa fa-trash "></i></a>
									</c:if>
									<button type="button" class="btn btn-darkblue-2 btn-sm"
										title="<spring:message code="work.estimate.add.measurement" />" id="addMeasurement${d}"
										onclick="measurementSheetAction('selectAllSorDataBySorId',${estimateData.sordId},${estimateData.sorId},${estimateData.workEstemateId});">
										<i class="fa fa-calculator"></i>
									</button></td>
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="revisedAppendableClass">
							<td align="center"><form:input path="command"
									cssClass="form-control mandColorClass " id="sequence${d}"
									value="${d+1}" /></td>
							<td><form:select
									path="command.revisedEstimateSorList[${d}].sordCategory"
									class="form-control chosen-select-no-results"
									id="sordCategory${d}" onchange="getAllItemsRevisedList(${d});">
									<form:option value="">
										<spring:message code="holidaymaster.select" />
									</form:option>
									<c:forEach items="${command.chaperList}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select> <form:hidden
									path="command.revisedEstimateSorList[${d}].workEstemateId"
									id="sorCheckedEstimationId${d}" /> <form:hidden
									path="command.revisedEstimateSorList[${d}].sorId"
									id="sorId${d}" /> <form:hidden
									path="command.revisedEstimateSorList[${d}].sordId"
									id="sorDetailsId${d}" /> <%-- <form:hidden
									path="command.revisedEstimateSorList[${d}].sorIteamUnit"
									id="sorIteamUnitHidden${d}" /> --%></td>
							<td><form:select id="sorDIteamNo${d}"
									path="command.revisedEstimateSorList[${d}].sorDIteamNo"
									class="form-control chosen-select-no-results"
									onchange="getSorRevisedItmsDescription(this,${d});">
									<form:option value="">
										<spring:message code="work.management.select" />
									</form:option>
								</form:select></td>
							<td><form:textarea id="sorDDescription${d}"
									path="command.revisedEstimateSorList[${d}].sorDDescription"
									readonly="true" class=" form-control" /></td>
							<td><form:select
									path="command.revisedEstimateSorList[${d}].sorIteamUnit"
									class="form-control" id="sorIteamUnit${d}" readonly="true">
									<form:option value="">
										<spring:message code="work.management.select" />
									</form:option>
									<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>
							<td><form:input id="sorBasicRate${d}"
									path="command.revisedEstimateSorList[${d}].sorBasicRate"
									readonly="true" class="form-control" /></td>
							<td><form:input
									path="command.revisedEstimateSorList[${d}].workEstimQuantity"
									cssClass="form-control text-right calculation" id="quanity${d}"
									onkeypress="return hasAmount(event, this, 11, 2)"
									readonly="true"
									onchange="getAmountFormatInDynamic((this),'quanity')" /></td>
							<td><form:input
									path="command.revisedEstimateSorList[${d}].workEstimAmount"
									cssClass="form-control text-right amount" id="total${d}"
									onkeypress="return hasAmount(event, this, 11, 2)"
									readonly="true"
									onchange="getAmountFormatInDynamic((this),'total')" /></td>

							<td class="text-center"><a href='#' onclick='return false;'
								title='<spring:message code="works.management.delete" />'
								class='btn btn-danger btn-sm delRevButton'><i
									class="fa fa-trash"></i></a>
								<button type="button" class="btn btn-darkblue-2 btn-sm"
									title="<spring:message code="work.estimate.add.measurement" />" id="addMeasurement${d}" onclick="">
									<i class="fa fa-calculator"></i>
								</button></td>
						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:otherwise>
				</c:choose>
			</tbody>

		</table>
	</c:when>
	<c:when test="${command.workeReviseFlag eq 'E'}">
		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-bordered table-striped"
			id="ExistingSorDetails">
			<thead>
				<tr>
					<th scope="col" width="3%"><spring:message code="ser.no"
							text="Sr.No." /></th>
					<th width="10%"><spring:message
							code="work.estimate.sor.chapter" text="Chapter" /><span
						class="mand">*</span></th>
					<th width="8%"><spring:message code="material.master.itemcode"
							text="Item Code" /><span class="mand">*</span></th>
					<th width="19%"><spring:message code="work.estimate.item.desc"
							text="Item Description" /><span class="mand">*</span></th>
					<th width="8%"><spring:message code="leadlift.master.unit"
							text="Unit" /><span class="mand">*</span></th>
					<th width="8%"><spring:message code="work.estimate.basic.rate"
							text="Basic Rate" /><span class="mand">*</span></th>
					<th width="8%"><spring:message code="work.estimate.quantity"
							text="Quantity" /><span class="mand">*</span></th>
					<th width="10%"><spring:message code="work.estimate.total"
							text="Total" /><span class="mand">*</span></th>
					<th width="8%"><spring:message code="work.estimate.revised.quantity" text="Revised Quantity" /><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="work.estimate.revised.total" text="Revised Total" /><span
						class="mand">*</span></th>
					<th width="8%" align="center"><spring:message
							code="sor.action" text="Action" /></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(command.revisedEstimateSorList) > 0}">
						<c:forEach var="estimateData"
							items="${command.revisedEstimateSorList}" varStatus="status">
							<tr class="appendableClass">

								<td align="center"><form:input path="command"
										cssClass="form-control mandColorClass " id="sequence${d}"
										value="${d+1}" disabled="true" /></td>
								<td><form:select
										path="command.revisedEstimateSorList[${d}].sordCategory"
										class="form-control" id="sordCategory${d}" onchange=""
										readonly="true">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<%-- <form:hidden path="command.revisedEstimateSorList[${d}].sordId"
								id="sorDetailsId${d}" />
							<form:hidden
								path="command.revisedEstimateSorList[${d}].sorIteamUnit"
								id="sorIteamUnitHidden${d}" />
							</td> --%>

								<%-- <td><form:select id="sorDIteamNo${d}"
										path="command.revisedEstimateSorList[${d}].sorDIteamNo"
										class="form-control chosen-select-no-results"
										onchange="" readonly="true">
										<form:option value="">
											<spring:message code="work.management.select" />
										</form:option>
									</form:select></td> --%>

								<td><form:input id="sorDIteamNo${d}"
										path="command.revisedEstimateSorList[${d}].sorDIteamNo"
										class="form-control "
										onchange="getSorRevisedItmsDescription(${d});" readonly="true" /></td>

								<td><form:textarea id="sorDDescription${d}"
										path="command.revisedEstimateSorList[${d}].sorDDescription"
										class=" form-control" readonly="true" /></td>

								<td><form:select
										path="command.revisedEstimateSorList[${d}].sorIteamUnit"
										class="form-control" id="sorIteamUnit${d}" readonly="true">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input id="sorBasicRate${d}"
										path="command.revisedEstimateSorList[${d}].sorBasicRate"
										class=" form-control text-right calculation"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'sorBasicRate')"
										readonly="true" /></td>
								<td><form:input
										path="command.revisedEstimateSorList[${d}].workEstimQuantity"
										cssClass="form-control text-right calculation"
										id="quanity${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'quanity')"
										readonly="true" /></td>

								<td><form:input
										path="command.revisedEstimateSorList[${d}].workEstimAmount"
										cssClass="form-control text-right amount" id="total${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'total')"
										readonly="true" /></td>

								<td><form:input
										path="command.revisedEstimateSorList[${d}].reviseEstimQty"
										cssClass="form-control text-right " id="revQuanity${d}"
										readonly="true"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'revQuanity')" /></td>

								<td><form:input
										path="command.revisedEstimateSorList[${d}].workRevisedEstimAmount"
										cssClass="form-control text-right amount"
										id="revisedTotal${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'revisedTotal')"
										readonly="true" /></td>

								<td><button type="button" class="btn btn-darkblue-2 btn-sm"
										title="<spring:message code="work.estimate.add.measurement" />"
										onclick="measurementSheetAction('selectAllSorDataBySorId',${estimateData.sordId},${estimateData.sorId},${estimateData.workEstemateId});">
										<i class="fa fa-calculator"></i>
									</button></td>
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
	</c:when>
</c:choose>

