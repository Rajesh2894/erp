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
	src="js/works_management/workContractVariationForm.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>


<div class="widget">

	<div class="widget-content padding">


		<form:hidden path="command.removeChildIds" id="removeChildIds" />
		<form:hidden path="command.cpdModeHideSor" id="cpdModeHideSor" />
		<div id="sortablevariation">
			<div class="table-responsive">
				<c:set var="d" value="0" scope="page"></c:set>
				<table class="table table-bordered table-striped" id="sorDetails">
					<c:choose>
						<c:when test="${command.cpdModeHideSor eq 'N'}">
							<thead>
								<tr>
									<th scope="col" width="3%"><spring:message code="ser.no"
											text="Sr.No." /></th>
									<th width="10%"><spring:message
											code="work.estimate.sor.chapter" text="Chapter" /><span
										class="mand">*</span></th>
									<th width="10%"><spring:message
											code="work.estimate.subCat" text="Sub Category" /></th>
									<th width="8%"><spring:message
											code="material.master.itemcode" text="Item Code" /><span
										class="mand">*</span></th>
									<th width="15%"><spring:message
											code="work.management.description" text="Item Description" /><span
										class="mand">*</span></th>
									<th width="8%"><spring:message code="work.management.unit"
											text="Unit" /><span class="mand">*</span></th>
									<th width="7%"><spring:message
											code="work.estimate.basic.rate" text="Basic Rate" /><span
										class="mand">*</span></th>
									<th width="6%"><spring:message code="work.estimate.labour"
											text="Labour" /></th>
									<th width="7%"><spring:message
											code="work.estimate.quantity" text="Quantity" /><span
										class="mand">*</span></th>
									<th width="8%"><spring:message code="work.estimate.total"
											text="Total" /><span class="mand">*</span></th>
									<c:if test="${command.saveMode ne 'V'}">
										<th class="text-center" width="5%"><button type="button"
												onclick="addEntryData();" class="btn btn-blue-2 btn-sm">
												<i class="fa fa-plus-circle"></i>
											</button></th>
									</c:if>
								</tr>
							</thead>
						</c:when>
						<c:otherwise>
							<thead>
								<tr>
									<th scope="col" width="3%"><spring:message code="ser.no"
											text="Sr.No." /></th>
									<th width="10%"><spring:message
											code="work.estimate.sor.chapter" text="Chapter" /><span
										class="mand">*</span></th>
									<th width="8%"><spring:message
											code="material.master.itemcode" text="Item Code" /><span
										class="mand">*</span></th>
									<th width="15%"><spring:message
											code="work.management.description" text="Item Description" /><span
										class="mand">*</span></th>
									<th width="8%"><spring:message code="work.management.unit"
											text="Unit" /><span class="mand">*</span></th>
									<th width="7%"><spring:message
											code="work.estimate.basic.rate" text="Basic Rate" /><span
										class="mand">*</span></th>
									<th width="7%"><spring:message
											code="work.estimate.quantity" text="Quantity" /><span
										class="mand">*</span></th>
									<th width="8%"><spring:message code="work.estimate.total"
											text="Total" /><span class="mand">*</span></th>
									<c:if test="${command.saveMode ne 'V'}">
										<th class="text-center" width="5%"><button type="button"
												onclick="addEntryData();" class="btn btn-blue-2 btn-sm">
												<i class="fa fa-plus-circle"></i>
											</button></th>
									</c:if>

								</tr>
							</thead>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${command.saveMode eq 'V'}">
							<c:forEach var="estimateData"
								items="${command.measurementsheetViewDataVariation}"
								varStatus="status">
								<tr class="appendableClass">

									<td align="center"><form:input path="command"
											cssClass="form-control mandColorClass " id="sequence${d}"
											value="${d+1}" disabled="true" /></td>
									<td><form:select
											path="command.measurementsheetViewDataVariation[${d}].sordCategory"
											class="form-control" id="sordCategory${d}" readonly="true">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WKC')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<c:if test="${command.cpdModeHideSor eq 'N'}">
										<td><form:textarea id="sordSubCategory${d}"
												readonly="true"
												path="command.measurementsheetViewDataVariation[${d}].sordSubCategory"
												class=" form-control" /></td>
									</c:if>
									<td><form:input id="sorDIteamNo${d}" readonly="true"
											path="command.measurementsheetViewDataVariation[${d}].sorDIteamNo"
											class=" form-control" /></td>

									<td><form:textarea id="sorDDescription${d}"
											readonly="true"
											path="command.measurementsheetViewDataVariation[${d}].sorDDescription"
											class=" form-control" /></td>

									<td><form:select
											path="command.measurementsheetViewDataVariation[${d}].sorIteamUnit"
											readonly="true" class="form-control" id="sorIteamUnit${d}">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input id="sorBasicRate${d}"
											path="command.measurementsheetViewDataVariation[${d}].sorBasicRate"
											class=" form-control text-right calculation" readonly="true"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>
									<c:if test="${command.cpdModeHideSor eq 'N'}">
										<td><form:input id="sorLabourRate${d}"
												path="command.measurementsheetViewDataVariation[${d}].sorLabourRate"
												class=" form-control text-right calculation" readonly="true"
												onkeypress="return hasAmount(event, this, 11, 2)"
												onchange="getAmountFormatInDynamic((this),'sorLabourRate')" /></td>
									</c:if>
									<td><form:input
											path="command.measurementsheetViewDataVariation[${d}].workEstimQuantity"
											cssClass="form-control text-right calculation"
											id="quanity${d}" readonly="true"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'quanity')" /></td>

									<td><form:input
											path="command.measurementsheetViewDataVariation[${d}].workEstimAmount"
											cssClass="form-control text-right amount" id="total${d}"
											readonly="true"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'total')" /></td>

								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />

							</c:forEach>
						</c:when>


						<c:when
							test="${fn:length(command.measurementsheetViewDataVariation) > 0}">
							<c:forEach var="estimateData"
								items="${command.measurementsheetViewDataVariation}"
								varStatus="status">
								<tr class="appendableClass">

									<td align="center"><form:input path="command"
											cssClass="form-control mandColorClass " id="sequence${d}"
											value="${d+1}" disabled="true" /></td>
									<td><form:select
											path="command.measurementsheetViewDataVariation[${d}].sordCategory"
											class="form-control" id="sordCategory${d}"
											readonly="${command.workeReviseFlag eq 'E'}">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WKC')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<c:if test="${command.cpdModeHideSor eq 'N'}">
										<td><form:textarea id="sordSubCategory${d}"
												path="command.measurementsheetViewDataVariation[${d}].sordSubCategory"
												class=" form-control"
												readonly="${command.workeReviseFlag eq 'E'}" /></td>

									</c:if>
									<td><form:input id="sorDIteamNo${d}"
											path="command.measurementsheetViewDataVariation[${d}].sorDIteamNo"
											class=" form-control"
											readonly="${command.workeReviseFlag eq 'E'}" /></td>

									<td><form:textarea id="sorDDescription${d}"
											path="command.measurementsheetViewDataVariation[${d}].sorDDescription"
											class=" form-control"
											readonly="${command.workeReviseFlag eq 'E'}" /></td>

									<td><form:select
											path="command.measurementsheetViewDataVariation[${d}].sorIteamUnit"
											class="form-control" id="sorIteamUnit${d}"
											readonly="${command.workeReviseFlag eq 'E'}">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input id="sorBasicRate${d}"
											path="command.measurementsheetViewDataVariation[${d}].sorBasicRate"
											class=" form-control text-right calculation"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'sorBasicRate')"
											readonly="${command.workeReviseFlag eq 'E'}" /></td>
									<c:if test="${command.cpdModeHideSor eq 'N'}">
										<td><form:input id="sorLabourRate${d}"
												path="command.measurementsheetViewDataVariation[${d}].sorLabourRate"
												class=" form-control text-right calculation"
												onkeypress="return hasAmount(event, this, 11, 2)"
												onchange="getAmountFormatInDynamic((this),'sorLabourRate')"
												readonly="${command.workeReviseFlag eq 'E'}" /></td>
									</c:if>
									<td><form:input
											path="command.measurementsheetViewDataVariation[${d}].workEstimQuantity"
											cssClass="form-control text-right calculation"
											id="quanity${d}"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'quanity')" /></td>

									<td><form:input
											path="command.measurementsheetViewDataVariation[${d}].workEstimAmount"
											cssClass="form-control text-right amount" id="total${d}"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'total')"
											readonly="true" /></td>
									<%-- <td><form:input
										path="measurementsheetViewDataVariation[${d}].totalEsimateAmount"
										cssClass="form-control text-right amount"
										id="subTotal${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'subTotal')" /></td> --%>
									<td class="text-center"><a href='#'
										onclick="deleteTableRow('sorDetails',$(this),'removeChildIds');"
										class='btn btn-danger btn-sm'><i class="fa fa-trash"></i></a></td>
								</tr>

								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="appendableClass">
								<td align="center"><form:input path="command"
										cssClass="form-control mandColorClass " id="sequence${d}"
										value="${d+1}" /></td>
								<td><form:select
										path="command.measurementsheetViewDataVariation[${d}].sordCategory"
										class="form-control" id="sordCategory${d}">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>
								<c:if test="${command.cpdModeHideSor eq 'N'}">
									<td><form:textarea id="sordSubCategory${d}"
											path="command.measurementsheetViewDataVariation[${d}].sordSubCategory"
											class=" form-control" /></td>
								</c:if>

								<td><form:input id="sorDIteamNo${d}"
										path="command.measurementsheetViewDataVariation[${d}].sorDIteamNo"
										class=" form-control" /></td>

								<td><form:textarea id="sorDDescription${d}"
										path="command.measurementsheetViewDataVariation[${d}].sorDDescription"
										class=" form-control" /></td>

								<td><form:select
										path="command.measurementsheetViewDataVariation[${d}].sorIteamUnit"
										class="form-control" id="sorIteamUnit${d}">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input id="sorBasicRate${d}"
										path="command.measurementsheetViewDataVariation[${d}].sorBasicRate"
										class=" form-control text-right calculation"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>
								<c:if test="${command.cpdModeHideSor eq 'N'}">
									<td><form:input id="sorLabourRate${d}"
											path="command.measurementsheetViewDataVariation[${d}].sorLabourRate"
											class=" form-control text-right calculation"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'sorLabourRate')" /></td>
								</c:if>
								<td><form:input
										path="command.measurementsheetViewDataVariation[${d}].workEstimQuantity"
										cssClass="form-control text-right calculation"
										id="quanity${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'quanity')" /></td>

								<td><form:input
										path="command.measurementsheetViewDataVariation[${d}].workEstimAmount"
										cssClass="form-control text-right amount" id="total${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'total')" /></td>
								<%-- <td><form:input
										path="measurementsheetViewDataVariation[${d}].totalEsimateAmount"
										cssClass="form-control text-right amount"
										id="subTotal${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'subTotal')" /></td> --%>
								<td class="text-center"><a href='#'
									onclick="deleteTableRow('sorDetails',$(this),'removeChildIds');"
									class='btn btn-danger btn-sm'><i class="fa fa-trash"></i></a></td>
							</tr>

							<c:set var="d" value="${d + 1}" scope="page" />
						</c:otherwise>
					</c:choose>
				</table>
			</div>

			<div id="NonSorTable"></div>
			<div id="billOfQuantity1"></div>


		</div>
	</div>
</div>


