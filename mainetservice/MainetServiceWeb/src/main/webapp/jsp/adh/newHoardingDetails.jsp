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
<div class="overflow margin-top-10">
	<div class="">
		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-hover table-bordered table-striped"
			id="newAdvertisingTableId">

			<thead>
				<tr>
					<th scope="col" width="5%"><spring:message code=""
							text="Sr.No." /></th>
					<th scope="col" width="10%" align="center"><spring:message
							code="adh.label.hoarding.no" text="Hoarding Number" /></th>
					<th scope="col" width="10%" align="center"><spring:message
							code="adh.label.description" text="Description" /></th>
					<th scope="col" width="10%" align="center"><spring:message
							code="adh.label.height" text="Height" /></th>
					<th scope="col" width="10%" align="center"><spring:message
							code="adh.label.length" text="Length" /></th>
					<th scope="col" width="10%" align="center"><spring:message
							code="adh.label.area" text="Area" /></th>
					<th scope="col" width="10%" align="center"><spring:message
							code="adh.label.facing" text="Facing" /></th>
					<!-- <th scope="col" width="5%"><a href="javascript:void(0);"
						class=" btn btn-blue-2 btn-sm addAdvertising"> <i
							class="fa fa-plus-circle"></i>
					</a></th> -->

					<c:if
						test="${command.scrutinyViewMode ne 'V' && command.enableSubmit ne 'Y' }">
						<th width="50"><a href="#a4" data-toggle="tooltip"
							data-placement="top" class="btn btn-blue-2  btn-sm"
							data-original-title="Add" onclick="addData();"><strong
								class="fa fa-plus addReButton"></strong><span class="hide"></span></a></th>
					</c:if>

				</tr>
			</thead>
			<tbody>
				<c:choose>

					<c:when
						test="${not empty command.advertisementReqDto.advertisementDto.newAdvertDetDtos }">

						<c:forEach var="sorDetailsList"
							items="${command.advertisementReqDto.advertisementDto.newAdvertDetDtos}"
							varStatus="status">
							<tr class="advertisingDetailsClass">
								<td align="center" width="5%"><form:input path=""
										cssClass="form-control mandColorClass " id="sequence${d}"
										value="${d+1}" disabled="true" /></td>

								<td><form:select
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].hoardingId"
										id="hoardingNo${d}" class="form-control"
										data-rule-required="true"
										onchange="getHoardingDetailsByHoardingNumber(this);">
										<form:option value="0">
											<spring:message code="adh.select" text="Select"></spring:message>
										</form:option>
										<c:forEach items="${command.hoardingNumberList }"
											var="hoardingNo">
											<form:option value="${hoardingNo[0]}">${hoardingNo[1] }</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:textarea id="advDetailsDesc${d}"
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsDesc"
										class="form-control" readonly="true" /></td>

								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsHeight"
										readonly="true" class="form-control" id="advDetailsHeight${d}" /></td>

								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsLength"
										readonly="true" class="form-control" id="advDetailsLength${d}" /></td>

								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsArea"
										class="form-control" id="advDetailsArea${d}" readonly="true" /></td>


								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].displayIdDesc"
										readonly="true" class="form-control" id="displayIdDesc${d}" /></td>

								<c:if
									test="${command.scrutinyViewMode ne 'V' && command.enableSubmit ne 'Y' }">
									<td align="center" width="3%"><a
										href="javascript:void(0);"
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick=""><i class="fa fa-minus"></i> </a></td>
								</c:if>

								<c:set var="d" value="${d + 1}" scope="page" />
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>

						<tr class="advertisingDetailsClass">
							<td align="center"><form:input path=""
									cssClass="form-control mandColorClass" id="sequence${d}"
									value="${d+1}" disabled="true" /></td>
							<%-- <form:hidden
						path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].hoardingNumber"
						id="hoardingNumber" /> --%>
							<td><form:select
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].hoardingId"
									id="hoardingNo${d}" class="form-control"
									data-rule-required="true"
									onchange="getHoardingDetailsByHoardingNumber(this);">
									<form:option value="0">
										<spring:message code="adh.select" text="Select"></spring:message>
									</form:option>
									<c:forEach items="${command.hoardingNumberList }"
										var="hoardingNo">
										<form:option value="${hoardingNo[0]}">${hoardingNo[1] }</form:option>
									</c:forEach>
								</form:select></td>

							<td><form:textarea id="advDetailsDesc${d}"
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsDesc"
									class="form-control" readonly="true" /></td>

							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsHeight"
									readonly="true" class="form-control" id="advDetailsHeight${d}" /></td>

							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsLength"
									readonly="true" class="form-control" id="advDetailsLength${d}" /></td>

							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsArea"
									class="form-control" id="advDetailsArea${d}" readonly="true" /></td>


							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].displayIdDesc"
									readonly="true" class="form-control" id="displayIdDesc${d}" /></td>

							<c:if
								test="${command.scrutinyViewMode ne 'V' && command.enableSubmit ne 'Y'}">
								<td align="center" width="3%"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" title="Delete"
									onclick=""><i class="fa fa-minus"></i> </a></td>
							</c:if>


						</tr>
					</c:otherwise>
				</c:choose>
				</tr>
			</tbody>
		</table>
	</div>
</div>