<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/material_mgmt/service/itemOpeningBalance.js"
	type="text/javascript"></script>
<style>
.addColor {
	background-color: #fff !important
}
</style>
<script type="text/javascript">
	$('.hasCharacterNumbers').on('keyup', function() {
		this.value = this.value.replace(/[^a-z A-Z 0-9]/g, '');
	});
</script>

<form:form class="form-horizontal" commandName="command"
	action="ItemOpeningBalance.html" method="POST"
	name="openBalanceSerialFrm" id="openBalanceSerialFrm"> 
	<div class="panel-collapse collapse in" id="methodDetail">
          <table id="methodTable" class="table table-striped table-bordered">
			<thead>

				<tr>
					<th width="5%" class="required-control"><spring:message
							code="store.master.srno" text="Sr.No."  /></th>
					<th class="required-control"><spring:message code="material.item.mfgDate"
							text="Mfg Date" /></th>
					<th class="required-control"><span id="labelExpiryDate"><spring:message
								code="material.item.expiryDate" text="Expiry Date"  /></span></th>
					<th class="required-control"><spring:message
							code="binLocMasDto.binLocation" text="Bin location" /></th>
					<c:if
						test="${'N' ne command.itemOpeningBalanceDto.valueMethodCode}">
						<th class="required-control"><span id="labelItemNo"><spring:message
									code="material.item.serialNumber" text="Serial Number" /></span></th>
					</c:if>
					<th class="required-control"><spring:message code="store.master.quantity" text="Quantity"
							 /></th>
					<c:if test="${'V' ne command.modeType}">
						<th colspan="3"><spring:message code="material.management.action" text="Action" /></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${'C' eq command.modeType}">
						<c:set var="index" value="0" scope="page" />
						<tr class="firstRow">
							<td><input type="text" id="srNo${index}" name="srNo${index}" readonly="true"
								value="${index+1}"
								class="form-control mandColorClass text-center required-control" /></td>

							<td><form:input
									path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${index}].mfgDate"
									type="text" class="form-control datepickerMfg"
									id="mfgDate${index}" /></td>
							<td><form:input
									path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${index}].expiryDate"
									type="text" class="form-control datepickerExp"
									id="expiryDate${index}" /></td>
							<td><form:select id="binLocId${index}" class="form-control"
									path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${index}].binLocId">
									<form:option value="0">
										<spring:message code="material.management.select"
											text="Select" />
									</form:option>
									<form:options items="${command.binLocList}"
										itemLabel="binLocation" itemValue="binLocId" />
								</form:select></td>
							<c:if test="${'N' ne command.itemOpeningBalanceDto.valueMethodCode}">
								<td><form:input
										path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${index}].itemNo"
										type="text" onchange="validateUniqueItemNo()" maxlength="20"
										class="form-control mandColorClass required-control hasCharacterNumbers"
										id="itemNo${index}" data-rule-required="true" /></td>
							</c:if>
							<td><c:choose>
									<c:when test="${'S' eq command.itemOpeningBalanceDto.valueMethodCode}">
										<form:input path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${index}].quantity"
											type="text" class="form-control mandColorClass required-control hasNumber"
											value="1" id="quantity${index}" data-rule-required="true" readonly="true" />
									</c:when>
									<c:otherwise>
										<form:input path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${index}].quantity"
											type="text" class="form-control mandColorClass required-control"
											onkeypress="return hasAmount(event, this, 11, 1)"
											id="quantity${index}" data-rule-required="true" />
									</c:otherwise>
								</c:choose></td>
							<td class="text-center">
							  <form:hidden
							path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${index}].openBalDetId"
							id="openBalDetId${index}" />
							    <a
								href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />"
								class="addOF btn btn-success btn-sm " id="addUnitRow" onclick="addUnitRow();"><i
									class="fa fa-plus-circle"></i></a>
								<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />"
									class="remOF btn btn-danger btn-sm btn-sm delete"
									id="deleteRow_"><i class="fa fa-trash-o"></i></a></td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach
							items="${command.itemOpeningBalanceDto.itemOpeningBalanceDetDto}"
							var="balanceDto" varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<%-- <form:hidden
								path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].openBalDetId"
								id="openbaldetid${count}" /> --%>
							<tr class="firstRow">
								<td><input type="text" id="srNo${count}"
									name="srNo${count}" value="${count+1}"
									class="form-control mandColorClass text-center required-control"
									readonly="${command.modeType eq 'V'}" /></td>

								<td><form:input
										path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].mfgDate"
										type="text" class="form-control datepickerMfg"
										id="mfgDate${count}" disabled="${command.modeType eq 'V'}" />
								</td>
								<td><form:input
										path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].expiryDate"
										type="text" class="form-control datepickerExp"
										id="expiryDate${count}"
										disabled="${command.modeType eq 'V' || command.itemOpeningBalanceDto.isExpiry ne 'Y'}" />
								</td>
								<td><form:select id="binLocId${count}" class="form-control"
										path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].binLocId"
										disabled="${command.modeType eq 'V'}">
										<form:option value="0">
											<spring:message code="material.management.select"
												text="Select" />
										</form:option>
										<form:options items="${command.binLocList}"
											itemLabel="binLocation" itemValue="binLocId" />
									</form:select></td>
								<c:if
									test="${'N' ne command.itemOpeningBalanceDto.valueMethodCode}">
									<td><form:input
											path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].itemNo"
											type="text" onchange="validateUniqueItemNo()" maxlength="20"
											class="form-control mandColorClass  required-control hasCharacterNumbers"
											id="itemNo${count}" readonly="${command.modeType eq 'V'}" />
									</td>
								</c:if>
								<td><c:choose>
									<c:when test="${'S' eq command.itemOpeningBalanceDto.valueMethodCode}">
										<form:input path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].quantity"
											type="text" class="form-control mandColorClass required-control hasNumber"
											id="quantity${count}" data-rule-required="true" readonly="true" />
									</c:when>
									<c:otherwise>
										<form:input path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].quantity"
											type="text" class="form-control mandColorClass required-control"
											onkeypress="return hasAmount(event, this, 11, 1)"
											id="quantity${count}" readonly="${command.modeType eq 'V'}" />
									</c:otherwise>
								</c:choose></td> 
								<c:if test="${'E' eq command.modeType}">
									<td class="text-center">
									<form:hidden
								path="itemOpeningBalanceDto.itemOpeningBalanceDetDto[${count}].openBalDetId"
								id="openBalDetId${count}" />
									 <a
										href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />"
										class="addOF btn btn-success btn-sm " id="addUnitRow" onclick="addUnitRow();"><i
											class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />"
											class="remEdit btn btn-danger btn-sm btn-sm delete"
											id="deleteRow_${count}"><i class="fa fa-trash-o"></i></a></td>
								</c:if>
							</tr>
							<%-- <c:set var="index" value="${index + 1}" scope="page" /> --%>
						</c:forEach>
						<form:hidden path="removeChildIds" id="removeChildIds"/>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	
</form:form> 
