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

<div class="widget">

	<div class="widget-content padding">

		<%-- <form:form action="WorkNonSorForm.html" class="form-horizontal"
				name="" id="">
		
		 --%>
		<div id="NonSorTable">
			<div class="">
				<c:set var="d" value="0" scope="page"></c:set>
				<table class="table table-bordered table-striped" id="nonSorDetails">
					<thead>
						<tr>
							<th width=""><spring:message code="ser.no" text="" />
								<!-- <input
									type="" id="srNo"> --></th>
							<th scope="col" width="8%" align="center"><spring:message
									code="work.estimate.overheads.ItemCode" /><span class="mand">*</span></th>
							<th scope="col" width="40%" align="center"><spring:message
									code="work.management.description" /><span class="mand">*</span></th>
							<th scope="col" width="10%" align="center"><spring:message
									code="work.management.unit" /><span class="mand">*</span></th>
							<th scope="col" width="10%" align="center"><spring:message
									code="work.estimate.rate" /><span class="mand">*</span></th>
							<th scope="col" width="12%" align="center"><spring:message
									code="work.estimate.quantity" /><span class="mand">*</span></th>
							<th scope="col" width="15%" align="center"><spring:message
									code="work.estimate.Total" /><span class="mand">*</span></th>
							<c:if test="${command.saveMode ne 'V'}">
								<!-- <th class="text-center" width="5%"><button type="button"
										onclick="return false;"
										class="btn btn-blue-2 btn-sm  addNonSORDetails">
										<i class="fa fa-plus-circle"></i>
									</button></th> -->
							</c:if>
						</tr>
					</thead>

					<c:choose>
						<c:when test="${command.saveMode eq 'V'}">
							<c:forEach var="schemeListData"
								items="${command.workEstimateNonSorFormList}" varStatus="status">
								<tr class="appendableClass">

									<td><form:input path="command" id="sequence${d}"
											value="${d+1}" disabled="true" cssClass="form-control" /> <%-- <form:hidden
												path="command.workEstimateNonSorFormList[${d}].workEstemateId"
												id="workEstemateId${d}" /> --%></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorDIteamNo"
											readonly="true"
											onkeyup="inputPreventSpace(event.keyCode,this);"
											cssClass="form-control" id="sorDIteamNo${d}" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorDDescription"
											readonly="true" cssClass="form-control"
											id="sorDDescription${d}" /></td>
									<td><form:select
											path="command.workEstimateNonSorFormList[${d}].sorIteamUnit"
											readonly="true" cssClass="form-control" id="sorIteamUnit${d}">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.unitLookUpList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorBasicRate"
											cssClass="form-control text-right calculation"
											id="sorRate${d}" placeholder="00.00" readonly="true"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'sorRate')" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].workEstimQuantity"
											cssClass="form-control text-right calculation"
											id="workQuantity${d}" placeholder="000.00" readonly="true"
											onkeypress="return hasAmount(event, this, 3, 2)"
											onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].workEstimAmount"
											cssClass="form-control text-right amount" readonly="true"
											id="workAmount${d}" placeholder="0000.00" /></td>

								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>

						<c:when
							test="${fn:length(command.workEstimateNonSorFormList) > 0}">
							<c:forEach var="schemeListData"
								items="${command.workEstimateNonSorFormList}" varStatus="status">
								<tr class="appendableClass">
									<td><form:input path="command" id="sequence${d}"
											value="${d+1}" disabled="true" cssClass="form-control" /> <%-- <form:input
												path="command.workEstimateNonSorFormList[${d}].workEstemateId"
												id="workEstemateId${d}" /> --%></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorDIteamNo"
											onkeyup="inputPreventSpace(event.keyCode,this);"
											cssClass="form-control" id="sorDIteamNo${d}"
											readonly="${command.workeReviseFlag eq 'E'}" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorDDescription"
											cssClass="form-control" id="sorDDescription${d}"
											readonly="${command.workeReviseFlag eq 'E'}" /></td>
									<td><form:select
											path="command.workEstimateNonSorFormList[${d}].sorIteamUnit"
											readonly="${command.workeReviseFlag eq 'E'}"
											cssClass="form-control" id="sorIteamUnit${d}">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.unitLookUpList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorBasicRate"
											cssClass="form-control text-right calculation"
											id="sorRate${d}"
											onkeypress="return hasAmount(event, this, 11, 2)"
											onchange="getAmountFormatInDynamic((this),'sorRate')"
											placeholder="00.00"
											readonly="${command.workeReviseFlag eq 'E'}" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].workEstimQuantity"
											cssClass="form-control text-right calculation"
											id="workQuantity${d}" placeholder="000.00"
											onkeypress="return hasAmount(event, this, 3, 2)"
											onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].workEstimAmount"
											cssClass="form-control text-right amount" id="workAmount${d}"
											placeholder="0000.00" readonly="true" /></td>
									<!-- <td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm deleteNonSORDetails'><i
											class="fa fa-trash"></i></a></td> -->
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>

						<c:otherwise>
							<tbody>
								<tr class="appendableClass">
									<td><form:input path="command" id="sNo${d}" value="1"
											cssClass="form-control" readonly="true" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorDIteamNo"
											onkeyup="inputPreventSpace(event.keyCode,this);"
											cssClass="form-control" id="sorDIteamNo${d}" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorDDescription"
											cssClass="form-control" id="sorDDescription${d}" /></td>
									<td><form:select
											path="command.workEstimateNonSorFormList[${d}].sorIteamUnit"
											cssClass="form-control" id="sorIteamUnit${d}">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.unitLookUpList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].sorBasicRate"
											onkeypress="return hasAmount(event, this, 11, 2)"
											id="sorRate${d}" placeholder="00.00"
											onchange="getAmountFormatInDynamic((this),'sorRate')"
											cssClass="form-control text-right calculation" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].workEstimQuantity"
											cssClass="form-control text-right calculation"
											id="workQuantity${d}" placeholder="000.00"
											onkeypress="return hasAmount(event, this, 3, 2)"
											onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>
									<td><form:input
											path="command.workEstimateNonSorFormList[${d}].workEstimAmount"
											cssClass="form-control text-right amount" id="workAmount${d}"
											placeholder="0000.00" /></td>
									<!-- <td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm deleteNonSORDetails'><i
											class="fa fa-trash"></i></a></td> -->
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</tbody>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div>
	</div>
</div>

