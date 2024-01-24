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
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/property/PropertyTaxAuth.js" type="text/javascript"></script>
<style>
body {
	counter-reset: Serial; /* Set the Serial counter to 0 */
}

#customFields tbody tr td:first-child:before {
	counter-increment: Serial; /* Increment the Serial counter */
	content: counter(Serial); /* Display the counter */
}
</style>
<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#UnitDetail"><spring:message
				code="property.unitdetails" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="UnitDetail">


		<div class="table-responsive">
			<c:set var="d" value="0" scope="page" />
			<table id="customFields"
				class="table table-striped table-bordered width-1600">
				
				<form:hidden id="factorSrnoListId" path="entity.factorSrnoList" value="${command.entity.factorSrnoList}" />
				<thead>
					<tr>
						<th><spring:message code="unitdetails.srno" /></th>
						<th width="200"><spring:message code="unitdetails.unittype" /></th>
						<%-- <th width="200"><spring:message code="unitdetails.count" /></th> --%>
						<th width="200"><spring:message code="unitdetails.floorno" /></th>
						<th width="200"><spring:message code="unitdetails.taxable" /></th>

						<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="" isMandatory="true"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control  required-control" showAll="true"
							hasTableForm="true" showData="false" />

						<th width="200"><spring:message
								code="unitdetails.constructiontype" /></th>
						<th width="200"><spring:message
								code="unitdetails.dateofConstruction" /></th>
						<th width="200"><spring:message code="unitdetails.occupancy" /></th>
						<th width="200"><spring:message code="unitdetails.roadFactor" /></th>
						<th width="200"><spring:message code="property.rent" /></th>
						<th width="100"><spring:message code="property.add/delete" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${command.entity.provisionalAssesmentDetails}"
						var="currUnit" varStatus="status">
						<c:set value="${status.index}" var="count"></c:set>


						<%-- <form:hidden
							path="entity.provisionalAssesmentDetails[${count}].proAssdCv" />
						<form:hidden
							path="entity.provisionalAssesmentDetails[${count}].proAssdActive" />
						<form:hidden
							path="entity.provisionalAssesmentDetails[${count}].orgid" />
						<form:hidden
							path="entity.provisionalAssesmentDetails[${count}].createdBy" />
						<form:hidden
							path="entity.provisionalAssesmentDetails[${count}].createdDate" />
						<form:hidden
							path="entity.provisionalAssesmentDetails[${count}].updatedBy" />
						<form:hidden
							path="entity.provisionalAssesmentDetails[${count}].updatedDate" /> --%>

						<tr class="tableRowClass">

							<td>
								<form:hidden path="entity.provisionalAssesmentDetails[${count}].unitSrNo" id="unitSrNo_${count}" value="${count+1}" /> 
								<%-- <label type="label" id="srNoId_${count}" value=""> --%>
							</td>

							<td><c:set var="baseLookupCode" value="UTP" /> <form:select
									path="entity.provisionalAssesmentDetails[${count}].proAssdUnitTypeId"
									class="form-control" id="proAssdUnitTypeId_${count}">
									<form:option value="0">
										<spring:message code="property.sel.optn" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<c:choose>
											<c:when
												test="${currUnit.proAssdUnitTypeId eq lookUp.lookUpId}">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</form:select></td>

							<%-- <td>
							  <form:input id="count_${count}" path="" class="form-control " disabled="${viewMode}" />
							</td> --%>

							<td><c:set var="baseLookupCode" value="IDE" /> <form:select
									path="entity.provisionalAssesmentDetails[${count}].proAssdFloorNo"
									class="form-control" id="proAssdFloorNo_${count}">
									<form:option value="0">
										<spring:message code="property.sel.optn" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">

										<c:choose>
											<c:when test="${currUnit.proAssdFloorNo eq lookUp.lookUpId}">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</form:select></td>

							<td><form:input id="proAssdBuildupArea_${count}"
									path="entity.provisionalAssesmentDetails[${count}].proAssdBuildupArea"
									value="${currUnit.proAssdBuildupArea}"
									class="hasNumber form-control" disabled="${viewMode}" /></td>

							<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
								showOnlyLabel="false"
								pathPrefix="entity.provisionalAssesmentDetails[${count}].proAssdUsagetype"
								isMandatory="true" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true"
								cssClass="form-control  required-control" showAll="true"
								hasTableForm="true" showData="true" />


							<td><c:set var="baseLookupCode" value="CSC" /> <form:select
									path="entity.provisionalAssesmentDetails[${count}].proAssdConstruType"
									class="form-control" id="proAssdConstruType_${count}">
									<form:option value="0">
										<spring:message code="property.sel.optn" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">

										<c:choose>
											<c:when
												test="${currUnit.proAssdConstruType eq lookUp.lookUpId}">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</form:select></td>
							<td><form:input type="text"
									path="entity.provisionalAssesmentDetails[${count}].createdDate"
									class="form-control datepicker2" id="yeardate_${count}" /></td>
							<td><c:set var="baseLookupCode" value="OCS" /> <form:select
									path="entity.provisionalAssesmentDetails[${count}].proAssdOccupancyType"
									class="form-control" id="proAssdOccupancyType_${d}">
									<form:option value="0">
										<spring:message code="property.sel.optn" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">

										<c:choose>
											<c:when
												test="${currUnit.proAssdOccupancyType eq lookUp.lookUpId}">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</form:select></td>
							<td><c:set var="baseLookupCode" value="RFT" /> <form:select
									path="entity.provisionalAssesmentDetails[${count}].proAssdRoadfactor"
									class="form-control" id="proAssdRoadfactor_${d}">
									<form:option value="0">
										<spring:message code="property.sel.optn" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">

										<c:choose>
											<c:when
												test="${currUnit.proAssdRoadfactor eq lookUp.lookUpId}">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</form:select></td>
							<td><form:input id="proAssdAnnualRent_${count}"
									path="entity.provisionalAssesmentDetails[${count}].proAssdAnnualRent"
									value="${currUnit.proAssdAnnualRent}" class="form-control" />
							</td>


							<form:hidden id="proAssdAlv_${count}"
								path="entity.provisionalAssesmentDetails[${count}].proAssdAlv"
								value="${currUnit.proAssdAlv}" class="form-control" />


							<form:hidden id="proAssdRv_${count}"
								path="entity.provisionalAssesmentDetails[${count}].proAssdRv"
								value="${currUnit.proAssdRv}" class="form-control" />


							<form:hidden id="proAssdStdRate_${count}"
								path="entity.provisionalAssesmentDetails[${count}].proAssdStdRate"
								value="${currUnit.proAssdStdRate}" class="form-control" />


							<td><a href="javascript:void(0);" title="Add" id="${count+1}" 
								class="addCF btn btn-success btn-sm"> <i
									class="fa fa-plus-circle"></i>
							</a> <a href="javascript:void(0);" title="Delete" id="${count+1}" 
								class="remCF btn btn-danger btn-sm"> <i
									class="fa fa-trash-o"></i>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
</div>
