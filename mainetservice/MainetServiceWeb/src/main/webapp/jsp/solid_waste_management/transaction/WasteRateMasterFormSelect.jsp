<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<c:set value="${command.prefixLevel}" var="level" scope="page"></c:set>

<c:choose>
	<c:when test="${empty command.wasteRateMasterDto.wasteRateList}">
		<c:set var="loop" value="1"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="loop" value="${command.wasteRateMasterDto.wasteRateList}"></c:set>
	</c:otherwise>
</c:choose>

<div class="panel-body">

	<c:set var="d" value="0" scope="page"></c:set>
	<table class="table table-bordered table-striped"
		id="id_updateSaleDetailsTable">
		<thead>
			<tr>
				<th scope="col" width="2%"><spring:message
						code="population.master.srno" text="Sr.No." /></th>

				<th scope="col" width="10%"><spring:message
						code="swm.wasteRate.itemList" text="Item List" /></th>
				<th scope="col" width="10%"><spring:message
						code="swm.wasteRate.rate" text="Rate" /></th>
				<c:if test="${command.saveMode ne 'V'}">
					<th scope="col" width="2%"><a href="javascript:void(0);"
						data-toggle="tooltip" title="Add" data-placement="top"
						onclick="addEntryData('id_updateSaleDetailsTable');"
						class=" btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
					<c:forEach items="${command.wasteRateMasterDto.wasteRateList}"
						var="List" varStatus="k">

						<tr class="firstUnitRow">
							<td align="center" width="2%"><form:input
									path="command.SrNo"
									cssClass=" text-center form-control mandColorClass "
									id="sequence${d}" value="${d+1}" disabled="true" /></td>

							<td align="center" width="10%">
								<div class="input-group col-sm-6 ">
									<form:select
										path="command.wasteRateMasterDto.wasteRateList[${d}].codWast"
										cssClass="form-control mandColorClass" id="item${d}"
										onchange=""
										disabled="${command.saveMode eq 'V' ? true : false }"
										data-rule-required="true">
										<form:option value="">Select</form:option>
										<c:forEach items="${command.wasteTypeList}" var="lookup">
											<form:option value="${lookup.lookUpId}"
												code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</td>
							<td align="center" width="10%">
								<div class="input-group col-sm-6 ">
									<form:input
										path="command.wasteRateMasterDto.wasteRateList[${d}].wasteRate"
										cssClass="text-right form-control  mandColorClass hasDecimal "
										onblur="getAmount()" id="rate${d}"
										disabled="${command.saveMode eq 'V' ? true : false }" />
									<span class="input-group-addon"><i class="fa fa-inr"></i>&nbsp;<spring:message
											code="swm.wasteRate.solid.perkg" text="Per Kg" /></span>
								</div>
							</td>
							<c:if test="${command.saveMode ne 'V'}">
								<td align="center" width="2%"><a
									class="btn btn-danger btn-sm delButton" title="Delete"
									onclick="deleteEntry('id_updateSaleDetailsTable',$(this),'sequence${d}')">
										<i class="fa fa-minus"></i>
								</a></td>
							</c:if>
						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="tripInfo" items="${loop}" varStatus="status">
						<tr class="firstUnitRow">
							<td align="center" width="2%"><form:input
									path="command.SrNo"
									cssClass="text-center form-control mandColorClass "
									id="sequence${d}" value="${d+1}" disabled="true" /></td>

							<td align="center" width="10%"><div
									class="input-group col-sm-6 ">
									<form:select
										path="command.wasteRateMasterDto.wasteRateList[${d}].codWast"
										cssClass="form-control mandColorClass" id="item${d}"
										onchange="" disabled="" data-rule-required="true">
										<form:option value="0">Select</form:option>
										<c:forEach items="${command.wasteTypeList}" var="lookup">
											<form:option value="${lookup.lookUpId}"
												code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div></td>

							<td align="center" width="10%">
								<div class="input-group col-sm-6 ">
									<form:input
										path="command.wasteRateMasterDto.wasteRateList[${d}].wasteRate"
										cssClass="form-control  mandColorClass hasDecimal text-right"
										onblur="" id="rate${d}" disabled="" />
									<span class="input-group-addon"><i class="fa fa-inr"></i>&nbsp;<spring:message
											code="swm.wasteRate.solid.perkg" text=" Per Kg" /></span>
								</div>
							</td>

							<td align="center" width="2%"><a
								class="btn btn-danger btn-sm delButton" title="Delete"
								onclick="deleteEntry('id_updateSaleDetailsTable',$(this),'sequence${d}')">
									<i class="fa fa-minus"></i>
							</a></td>
						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:forEach>
				</c:otherwise>
			</c:choose>

		</tbody>

	</table>
</div>