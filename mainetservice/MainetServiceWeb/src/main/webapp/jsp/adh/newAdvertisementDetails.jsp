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
<!-- <script type="text/javascript" src="js/adh/newAdvertisementDetails.js"></script> -->
 <!-- <script type="text/javascript"
	src="js/adh/newAdvertisementDetails.js"></script> -->
	<style>.bluebtn{background-color:#3498db!important}</style>
<div class="overflow margin-top-10">
	<div class="table-responsive">
		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-hover table-bordered table-striped"
			id="newAdvertisingTableId">
			<thead>
				<tr>
					<th scope="col" width="5%"><spring:message code=""
							text="Sr.No." /></th>
							
					<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
						showOnlyLabel="false"
						pathPrefix="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].adhTypeId"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control" showAll="false"
						hasTableForm="true" showData="false" columnWidth="17%" />

					<th scope="col" width="10%" class="required-control" align="center"><spring:message
							code="adh.label.description" text="Description" /></th>
					<th scope="col" width="7%" class="required-control" align="center"><spring:message
							code="adh.label.height" text="Height" /></th>
					<th scope="col" width="7%" class="required-control" align="center"><spring:message
							code="adh.label.length" text="Length" /></th>
					<th scope="col" width="10%" align="center"><spring:message
							code="adh.label.area" text="Area" /></th>
					<th scope="col" width="10%" class="required-control" align="center"><spring:message
							code="adh.label.unit" text="Unit" /></th>
					<th scope="col" width="10%" class="required-control" align="center"><spring:message
							code="adh.label.facing" text="Facing" /></th>
					<c:if
						test="${command.scrutinyViewMode ne 'V' && command.enableSubmit ne 'Y' && command.saveMode ne 'R'}">
						<th scope="col" width="5%"><a href="javascript:void(0);"
							class=" btn btn-blue-2 btn-sm addAdvertising bluebtn"> <i
								class="fa fa-plus-circle"></i>
						</a></th>
					</c:if>
				</tr>
			</thead>
			<tbody>

				<c:choose>
					<c:when
						test="${fn:length(command.advertisementReqDto.advertisementDto.newAdvertDetDtos) > 0}">
						<c:forEach var="sorDetailsList"
							items="${command.advertisementReqDto.advertisementDto.newAdvertDetDtos}"
							varStatus="status">
							<tr class="advertisingDetailsClass">
								<td align="center" width="5%"><form:input path=""
										cssClass="form-control mandColorClass " id="sequence${d}"
										value="${d+1}" disabled="true" /></td>

								<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true" 
									pathPrefix="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].adhTypeId"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control required-control " showAll="false"
									hasTableForm="true" showData="true" columnWidth="10%" />

								<td><form:textarea id="advDetailsDesc${d}"
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsDesc"
										class="form-control" /></td>

								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsHeight"
										class="form-control decimal text-right"
										id="advDetailsHeight${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'advDetailsHeight')"
										onkeyup="calculateArea()" /></td>

								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsLength"
										class="form-control decimal text-right "
										id="advDetailsLength${d}"
										onkeypress="return hasAmount(event, this, 11, 2)"
										onchange="getAmountFormatInDynamic((this),'advDetailsLength')"
										onkeyup="calculateArea()" /></td>

								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].advDetailsArea"
										class="form-control decimal text-right"
										id="advDetailsArea${d}" readonly="true" /></td>

								<td><form:input
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].unit"
										class="form-control hasDecimal" id="unit${d}" /></td>

								<td><form:select id="dispTypeId${d}"
										path="advertisementReqDto.advertisementDto.newAdvertDetDtos[${d}].dispTypeId"
										class="form-control">
										<form:option value="">
											<spring:message code="adh.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('DSP')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>
								<c:if
									test="${command.scrutinyViewMode ne 'V' && command.enableSubmit ne 'Y' && command.saveMode ne 'R'}">
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
							<td align="center" width="5%"><form:input path=""
									cssClass="form-control mandColorClass " id="sequence${d}"
									value="${d+1}" disabled="true" /></td>

							<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
								pathPrefix="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].adhTypeId"
								isMandatory="true" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true"
								cssClass="form-control required-control " showAll="false"
								hasTableForm="true" showData="true" columnWidth="10%" />

							<td><form:textarea id="advDetailsDesc${d}"
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].advDetailsDesc"
									class="form-control" /></td>

							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].advDetailsHeight"
									class="form-control decimal text-right"
									id="advDetailsHeight${d}"
									onkeypress="return hasAmount(event, this, 11, 2)"
									onchange="getAmountFormatInDynamic((this),'advDetailsHeight')"
									onkeyup="calculateArea()" /></td>

							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].advDetailsLength"
									class="form-control decimal text-right "
									id="advDetailsLength${d}"
									onkeypress="return hasAmount(event, this, 11, 2)"
									onchange="getAmountFormatInDynamic((this),'advDetailsLength')"
									onkeyup="calculateArea()" /></td>

							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].advDetailsArea"
									class="form-control decimal text-right" id="advDetailsArea${d}"
									readonly="true" /></td>

							<td><form:input
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].unit"
									class="form-control hasDecimal" id="unit${d}" /></td>

							<td><form:select id="dispTypeId${d}"
									path="advertisementReqDto.advertisementDto.newAdvertDetDtos[0].dispTypeId"
									class="form-control">
									<form:option value="">
										<spring:message code="adh.select" />
									</form:option>
									<c:forEach items="${command.getLevelData('DSP')}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>
							<c:if
								test="${command.scrutinyViewMode ne 'V' && command.enableSubmit ne 'Y' && command.saveMode ne 'R'}">
								<td align="center" width="3%"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" title="Delete"
									onclick=""><i class="fa fa-minus"></i> </a></td>
							</c:if>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>