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
	src="js/works_management/tenderinitiation.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<div>
	<h4>
		<spring:message code="tender.add.estimate" text="Add Work Estimates" />
	</h4>
	<c:set var="c" value="0" scope="page" />
	<div class="table-responsive">
		<table class="table table-bordered table-striped" id="tendWorks">
			<thead>
				<tr>
					<th width="8%"><spring:message code='work.management.select'
							text="Select" /></th>
					<th width="8%"><spring:message code='work.def.workCode'
							text="Work Code" /></th>
					<th width="15%"><spring:message code='work.def.workname'
							text="Work Name" /></th>
					<th width="8%"><spring:message code='tender.type'
							text="Tender Type" /></th>
					<th width="10%"><spring:message code='wms.TenderFees'
							text="Tender Fees" /></th>
					<th width="10%"><spring:message code='tender.vender.classes'
							text="Vender Class" /></th>
					<%-- <th width="10%"><spring:message code='tender.createddate'
							text="Created Date" /></th> --%>
					<th width="10%"><spring:message code='tender.emd'
							text="Security Deposite" /></th>
					<th width="8%"><spring:message code='tender.work.durations'
							text="Work Duration" /></th>
                      <th width="10%"><spring:message code='wms.WorkDurationUnit'
							text="Work Duration Unit" /></th>
					<th width="8%"><spring:message code='tender.estimate.cost'
							text="Estimate Cost" /></th>
					<!-- <th>Status</th> -->
					<th width="5%"><spring:message code="works.management.action"
							text="Action" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${command.workList}" var="mstDto">
					<tr class="tenderWork">
						<td align="center"><form:input type="hidden"
								path="command.initiationDto.workDto[${c}].tndWId"
								id="tndWId${c}" /> <form:input type="hidden"
								path="command.initiationDto.workDto[${c}].workId"
								value="${mstDto.workId}" /> <c:if
								test="${command.modeCpd eq 'Y'}">
								<form:input type="hidden"
									path="command.initiationDto.workDto[${c}].workEstimateAmt"
									value="${mstDto.workEstAmt}"
									id="workEstimateAmt${c}" />
							</c:if> <c:if test="${command.modeCpd ne 'Y'}">
								<form:input type="hidden"
									path="command.initiationDto.workDto[${c}].workEstimateAmt"
									value="${mstDto.workEstAmt}" id="workEstimateAmt${c}" />
							</c:if> <form:checkbox
								path="command.initiationDto.workDto[${c}].initiated"
								id="initiated${c}" onclick="setAmount(${c})" />
						<td align="center">${mstDto.workcode}</td>
						<td>${mstDto.workName}</td>

						<td><form:select
								path="command.initiationDto.workDto[${c}].tenderType"
								cssClass="form-control mandColorClass " id="tenderType${c}"
								data-rule-required="true">
								<form:option value="">
									<spring:message code='work.management.select' />
								</form:option>
								<c:forEach items="${command.tenderTpyes}" var="payType">
									<form:option value="${payType.lookUpId }"
										code="${payType.lookUpCode}">${payType.lookUpDesc}</form:option>
								</c:forEach>
							</form:select></td>

						<td><form:input
								path="command.initiationDto.workDto[${c}].tenderFeeAmt"
								cssClass="form-control mandColorClass text-right"
								id="tenderFeeAmt${c}"
								onkeypress="return hasAmount(event, this, 6, 2)"
								onchange="getAmountFormatInDynamic((this),'tenderFeeAmt')"
								placeholder="999999.99" data-rule-required="true" /></td>

						<td><form:select
								path="command.initiationDto.workDto[${c}].venderClassId"
								cssClass="form-control mandColorClass" id="venderClassId${c}"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="work.management.select" />
								</form:option>
								<c:forEach items="${command.venderCategoryList}" var="vClass">
									<form:option value="${vClass.lookUpId}"
										code="${vClass.lookUpCode}">${vClass.lookUpDesc}</form:option>
								</c:forEach>
							</form:select></td>

						<%-- <td><apptags:lookupField items="${command.getLevelData('VEC')}"
						path="command.initiationDto.workDto[${c}].venderClassId"
						cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select" hasTableForm="true" isMandatory="true" /></td> --%>

						<td><form:input
								path="command.initiationDto.workDto[${c}].tenderSecAmt"
								cssClass="form-control mandColorClass text-right"
								id="tenderSecAmt${c}"
								onkeypress="return hasAmount(event, this, 6, 2)"
								onchange="getAmountFormatInDynamic((this),'tenderSecAmt')"
								placeholder="999999.99" data-rule-required="true" /></td>



						<%--  <td><apptags:lookupField items="${command.getLevelData('VEC')}"
						path="command.initiationDto.workDto[${c}].vendorWorkPeriodUnit"
						cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select" hasTableForm="true" isMandatory="true"/></td> --%>


						<td><form:input
								path="command.initiationDto.workDto[${c}].vendorWorkPeriod"
								cssClass="form-control mandColorClass" id="vendorWorkPeriod${c}"
								data-rule-required="true" /></td>
								
								
						<td><form:select
								path="command.initiationDto.workDto[${c}].vendorWorkPeriodUnit"
								cssClass="form-control mandColorClass"
								id="vendorWorkPeriodUnit${c}" data-rule-required="true">
								<form:option value="">
									<spring:message code="work.management.select" />
								</form:option>
								<c:forEach items="${command.workDurationUnit}" var="duration">
									<form:option value="${duration.lookUpId}"
										code="${duration.lookUpCode}">${duration.lookUpDesc}</form:option>
								</c:forEach>
							</form:select></td>


						<%-- <td align="right">${mstDto.createdDateDesc}</td> --%>

						<c:if test="${command.modeCpd eq 'Y'}">
							<td align="right">${mstDto.workEstAmt}</td>
						</c:if>
						<c:if test="${command.modeCpd ne 'Y'}">
							<td align="right">${mstDto.workEstAmt}</td>
						</c:if>

						<%-- <td>${mstDto.workStatus}</td> --%>
						<td class="text-center "><button type="button"
								class="btn btn-blue-2 btn-sm viewEstimate"
								onClick="viewWorkEstimate(${mstDto.workId})"
								title="View Estimate">
								<i class="fa fa-eye"></i>
							</button></td>
						<c:set var="c" value="${c + 1}" scope="page" />
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align="right"><b><spring:message
								code="work.estimate.total.amount" text="Total Amount" /></b></td>
					<td align="right"><form:input class="form-control text-right"
							type="input" readonly="true" id="totalAmt"
							path="command.initiationDto.tenderTotalEstiAmount" /></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
