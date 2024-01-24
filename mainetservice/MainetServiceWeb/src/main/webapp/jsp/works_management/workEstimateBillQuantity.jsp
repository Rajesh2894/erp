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
<%
    response.setContentType("text/html; charset=utf-8");
%>

<div class="widget">

	<div class="widget-content padding">
		<div id="billOfQuantity">
			<div class="table-responsive">
				<c:set var="d" value="0" scope="page" />
				<table class="table table-bordered table-striped"
					id="directAbstractTab">
					<form:hidden path="command.cpdModeHideSor" id="cpdModeHideSor"/>
					<c:choose>
						<c:when test="${command.cpdModeHideSor eq 'N'}">
							<thead>
								<tr>
									<th scope="col" width="12%" align="center"><spring:message
											code="work.estimate.sor.chapter" text="Chapter" /><span class="mand">*</span></th>
									<th scope="col" width="12%" align="center"><spring:message
											code="sor.subCategory" text="SubCategory" /><span
										class="mand">*</span></th>
									<th scope="col" width="12%" align="center"><spring:message
											code="sor.item.code" text="Item Code" /><span class="mand">*</span></th>
									<th scope="col" width="24%" align="center"><spring:message
											code="sor.item.description" text="Item Description" /><span
										class="mand">*</span></th>
									<th scope="col" width="12%" align="center"><spring:message
											code="work.management.unit" text="Unit" /><span class="mand">*</span></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="work.estimate.rate" text="Rate" /><span class="mand">*</span></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="work.estimate.quantity" text="Quantity" /><span
										class="mand">*</span></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="work.estimate.total" text="Total" /><span class="mand">*</span></th>
									<c:if test="${command.saveMode ne 'V'}">
										<th scope="col" width="7%"><a onclick='return false;'
											class="btn btn-blue-2 btn-sm addButton"> <i
												class="fa fa-plus-circle"></i></a></th>
									</c:if>
								</tr>
							</thead>
						</c:when>
						<c:otherwise>
							<thead>
								<tr>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.estimate.sor.chapter" text="Chapter" /><span class="mand">*</span></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="sor.item.code" text="Item Code" /><span class="mand">*</span></th>
									<th scope="col" width="25%" align="center"><spring:message
											code="sor.item.description" text="Item Description" /><span
										class="mand">*</span></th>
									<th scope="col" width="11%" align="center"><spring:message
											code="work.management.unit" text="Unit" /><span class="mand">*</span></th>
									<th scope="col" width="12%" align="center"><spring:message
											code="work.estimate.rate" text="Rate" /><span class="mand">*</span></th>
									<th scope="col" width="12%" align="center"><spring:message
											code="work.estimate.quantity" text="Quantity" /><span
										class="mand">*</span></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.estimate.total" text="Total" /><span class="mand">*</span></th>
									<c:if test="${command.saveMode ne 'V'}">
										<th scope="col" width="10%"><a onclick='return false;'
											class="btn btn-blue-2 btn-sm addButton"> <i
												class="fa fa-plus-circle"></i></a></th>
									</c:if>
								</tr>
							</thead>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${command.saveMode eq 'V'}">
							<c:forEach var="schemeListData"
								items="${command.workEstimateBillQuantityList}"
								varStatus="status">
								<tr class="appendableClass">
									<td><form:select
											path="command.workEstimateBillQuantityList[${d}].sordCategory"
											readonly="true" class="form-control" id="sordCategory${d}">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WKC')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select> <form:hidden
											path="command.workEstimateBillQuantityList[${d}].workEstemateId"
											readonly="true" id="workEstemateId${d}" /></td>
									<c:if test="${command.cpdModeHideSor eq 'N'}">
										<td><form:input id="sordSubCategory${d}"
												path="command.workEstimateBillQuantityList[${d}].sordSubCategory"
												readonly="true" class=" form-control" maxlength="50" /></td>
									</c:if>

									<td><form:input id="sorDIteamNo${d}"
											path="command.workEstimateBillQuantityList[${d}].sorDIteamNo"
											readonly="true" class=" form-control" maxlength="50" /></td>

									<td><form:input id="sorDDescription${d}"
											path="command.workEstimateBillQuantityList[${d}].sorDDescription"
											readonly="true" class=" form-control" maxlength="50" /></td>

									<td><form:select id="sorIteamUnit${d}"
											path="command.workEstimateBillQuantityList[${d}].sorIteamUnit"
											readonly="true" class="form-control">
											<form:option value="0">
												<spring:message code="work.management.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input id="sorBasicRate${d}"
											path="command.workEstimateBillQuantityList[${d}].sorBasicRate"
											readonly="true" onkeyup="calculateTotalRate();"
											placeholder="00.00" class=" form-control text-right  decimal"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>

									<td><form:input id="workQuantity${d}"
											path="command.workEstimateBillQuantityList[${d}].workEstimQuantity"
											readonly="true" onkeyup="calculateTotalRate();"
											placeholder="000.00" class=" form-control text-right decimal"
											onkeypress="return hasAmount(event, this, 3, 2)"
											onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>

									<td><form:input id="workEstimAmount${d}"
											path="command.workEstimateBillQuantityList[${d}].workEstimAmount"
											readonly="true" class=" form-control text-right"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onchange="getAmountFormatInDynamic((this),'workEstimAmount')"
											placeholder="0000.00" /></td>

								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:when
							test="${fn:length(command.workEstimateBillQuantityList) > 0}">
							<c:forEach var="schemeListData"
								items="${command.workEstimateBillQuantityList}"
								varStatus="status">
								<tr class="appendableClass">

									<td><form:select
											path="command.workEstimateBillQuantityList[${d}].sordCategory"
											readonly="${command.workeReviseFlag eq 'E'}"
											class="form-control" id="sordCategory${d}">
											<form:option value="0">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WKC')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select> <form:hidden
											path="command.workEstimateBillQuantityList[${d}].workEstemateId"
											readonly="${command.workeReviseFlag eq 'E'}"
											id="workEstemateId${d}" /></td>
									<c:if test="${command.cpdModeHideSor eq 'N'}">
										<td><form:input id="sordSubCategory${d}"
												path="command.workEstimateBillQuantityList[${d}].sordSubCategory"
												readonly="${command.workeReviseFlag eq 'E'}"
												class=" form-control" maxlength="50" /></td>
									</c:if>
									<td><form:input id="sorDIteamNo${d}"
											path="command.workEstimateBillQuantityList[${d}].sorDIteamNo"
											readonly="${command.workeReviseFlag eq 'E'}"
											class=" form-control" maxlength="50" /></td>

									<td><form:input id="sorDDescription${d}"
											path="command.workEstimateBillQuantityList[${d}].sorDDescription"
											readonly="${command.workeReviseFlag eq 'E'}"
											class=" form-control" maxlength="50" /></td>

									<td><form:select id="sorIteamUnit${d}"
											path="command.workEstimateBillQuantityList[${d}].sorIteamUnit"
											readonly="${command.workeReviseFlag eq 'E'}"
											class="form-control">
											<form:option value="0">
												<spring:message code="work.management.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input id="sorBasicRate${d}"
											path="command.workEstimateBillQuantityList[${d}].sorBasicRate"
											readonly="${command.workeReviseFlag eq 'E'}"
											onkeyup="calculateTotalRate();" placeholder="00.00"
											class=" form-control text-right  decimal"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>

									<td><form:input id="workQuantity${d}"
											path="command.workEstimateBillQuantityList[${d}].workEstimQuantity"
											onkeyup="calculateTotalRate();" placeholder="000.00"
											class=" form-control text-right decimal"
											onkeypress="return hasAmount(event, this, 3, 2)"
											onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>

									<td><form:input id="workEstimAmount${d}"
											path="command.workEstimateBillQuantityList[${d}].workEstimAmount"
											class=" form-control text-right"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onchange="getAmountFormatInDynamic((this),'workEstimAmount')"
											readonly="true" placeholder="0000.00" /></td>

									<td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm delButton'><i
											class="fa fa-trash"></i></a></td>

								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="appendableClass">

								<td><form:select
										path="command.workEstimateBillQuantityList[${d}].sordCategory"
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
									<td><form:input id="sordSubCategory${d}"
											path="command.workEstimateBillQuantityList[${d}].sordSubCategory"
											class=" form-control" maxlength="50" /></td>
								</c:if>
								<td><form:input id="sorDIteamNo${d}"
										path="command.workEstimateBillQuantityList[${d}].sorDIteamNo"
										class=" form-control" maxlength="50" /></td>

								<td><form:input id="sorDDescription${d}"
										path="command.workEstimateBillQuantityList[${d}].sorDDescription"
										class=" form-control" maxlength="50" /></td>

								<td><form:select id="sorIteamUnit${d}"
										path="command.workEstimateBillQuantityList[${d}].sorIteamUnit"
										class="form-control">
										<form:option value="0">
											<spring:message code="work.management.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input id="sorBasicRate${d}"
										path="command.workEstimateBillQuantityList[${d}].sorBasicRate"
										onkeyup="calculateTotalRate();" placeholder="00.00"
										class=" form-control decimal text-right"
										onkeypress="return hasAmount(event, this, 13, 2)"
										onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>

								<td><form:input id="workQuantity${d}"
										path="command.workEstimateBillQuantityList[${d}].workEstimQuantity"
										onkeyup="calculateTotalRate();" placeholder="000.00"
										class=" form-control decimal text-right"
										onkeypress="return hasAmount(event, this, 3, 2)"
										onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>

								<td><form:input id="workEstimAmount${d}"
										path="command.workEstimateBillQuantityList[${d}].workEstimAmount"
										class=" form-control text-right"
										onkeypress="return hasAmount(event, this, 13, 2)"
										onchange="getAmountFormatInDynamic((this),'workEstimAmount')"
										placeholder="0000.00" /></td>

								<td class="text-center"><a href='#' onclick='return false;'
									class='btn btn-danger btn-sm delButton'><i
										class="fa fa-trash"></i></a></td>

							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div>
	</div>
</div>

