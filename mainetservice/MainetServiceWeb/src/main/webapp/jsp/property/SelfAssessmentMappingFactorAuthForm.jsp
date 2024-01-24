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
<script src="js/property/MappingFactorAuth.js" type="text/javascript"></script>

<!-- <link rel="stylesheet"
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	type="text/css">
<script type="text/javascript"
	src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script> -->

<div class="accordion-toggle">
	<h4 class="margin-top-20 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#factordetails"><spring:message
				code="property.mappingfactor" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="factordetails">
		<div id="table-responsive2">
			<table id="customFields1" class="table table-striped table-bordered">
				<tbody>
					<tr>
						<th>Factors</th>
						<th>Factor Value</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th width="150">Sr No.</th>
						<th>Add/Delete</th>
					</tr>

					<c:forEach items="${command.provDataFactorList}"
						var="currUnit" varStatus="loop">
						<c:set value="${loop.index}" var="factorIndex"></c:set>
						
							<form:hidden
								path="provDataFactorList[${factorIndex}].proAssfFactor" />
							<form:hidden
								path="provDataFactorList[${factorIndex}].proAssfActive" />
							<form:hidden
								path="provDataFactorList[${factorIndex}].orgid" />
							<form:hidden
								path="provDataFactorList[${factorIndex}].createdBy" />
							<form:hidden
								path="provDataFactorList[${factorIndex}].createdDate" />
							<form:hidden
								path="provDataFactorList[${factorIndex}].updatedBy" />
							<form:hidden
								path="provDataFactorList[${factorIndex}].updatedDate" />

							<tr class="factorTableClass">
								<td>
								   <input type="hidden" id="srNoId_${factorIndex}" value="">
									<c:set var="baseLookupCode" value="FCT" /> 
									<form:select
										path="provDataFactorList[${factorIndex}].proAssfFactorId"
										onchange="getFactorValue(${factorIndex})" class="form-control"
										id="factorType_${factorIndex}">
										<form:option value="0">
											<spring:message code="property.sel.optn" />
										</form:option>

										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
 
											<c:choose>
												<c:when
													test="${currUnit.proAssfFactorId eq lookUp.lookUpId}">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc}</form:option>
												</c:when>
												<c:otherwise>
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:otherwise>
											</c:choose>

										</c:forEach>

									</form:select>
									</td>

								<td><input type="hidden" id="srNoId_${factorIndex}" value="">
									<c:set var="baseLookupCode" value="RFT" /> <form:select
										path="provDataFactorList[${factorIndex}].proAssfFactorValue"
										onchange="getFactorValue(${factorIndex})" class="form-control"
										id="proAssfFactorValue_${factorIndex}">
										<form:option value="0">
											<spring:message code="property.sel.optn" />
										</form:option>

										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">

											<c:choose>
												<c:when
													test="${currUnit.proAssfFactorValue eq lookUp.lookUpId}">
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
										path="provDataFactorList[${factorIndex}].proAssfStartDate"
										id="fromDate_${factorIndex}" class="form-control datepicker2" /></td>
								<td><form:input type="text"
										path="provDataFactorList[${factorIndex}].proAssfEndDate"
										id="toDate_${factorIndex}" class="form-control datepicker2" /></td>

								<!-- <td>
									 <select class="srNoCss testone form-control"
										multiple="multiple">
									 </select> 
								 </td> -->

								<td>
									<form:select class="srNoCss testone form-control"
										multiple="multiple" id="proAssfActive_${factorIndex}"
										path="provDataFactorList[${factorIndex}].proAssfActive">
									</form:select>
								</td>

								<td>
								<a href="javascript:void(0);" title="Add"
									class="addCF2 btn btn-success btn-sm" > <i
										class="fa fa-plus-circle"></i>
								</a> <a href="javascript:void(0);" title="Delete" 
									class="remCF2 btn btn-danger btn-sm"> <i
										class="fa fa-trash-o"></i>
								</a></td>
							</tr>
						</c:forEach>


					<%-- <c:forEach items="${command.entity.provisionalAssesmentDetails}"
						var="currUnit" varStatus="loop">
						<c:set value="${loop.index}" var="count"></c:set>
						<c:forEach items="${currUnit.listOfProvAsAssesmentFactorDetail}"
							var="currUnitFactorDetail" varStatus="factorCount">
							<c:set value="${factorCount.index}" var="factorIndex"></c:set>

							<form:hidden
								path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfFactor" />
							<form:hidden
								path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfActive" />
							<form:hidden
								path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].orgid" />
							<form:hidden
								path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].createdBy" />
							<form:hidden
								path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].createdDate" />
							<form:hidden
								path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].updatedBy" />
							<form:hidden
								path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].updatedDate" />

							<tr class="factorTableClass">
								<td><input type="hidden" id="srNoId_${count}" value="">
									<c:set var="baseLookupCode" value="FCT" /> <form:select
										path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfFactorId"
										onchange="getFactorValue(${count})" class="form-control"
										id="factorType_${count}">
										<form:option value="0">
											<spring:message code="property.sel.optn" />
										</form:option>

										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">

											<c:choose>
												<c:when
													test="${currUnitFactorDetail.proAssfFactorId eq lookUp.lookUpId}">
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

								<td><input type="hidden" id="srNoId_${count}" value="">
									<c:set var="baseLookupCode" value="RFT" /> <form:select
										path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfFactorValue"
										onchange="getFactorValue(${count})" class="form-control"
										id="proAssfFactorValue_${count}">
										<form:option value="0">
											<spring:message code="property.sel.optn" />
										</form:option>

										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">

											<c:choose>
												<c:when
													test="${currUnitFactorDetail.proAssfFactorValue eq lookUp.lookUpId}">
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

								<td>
								   <form:select id="factorValue_${count}" path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfFactorValue" 
								        value="${currUnitFactorDetail.proAssfFactorValueDesc}" class="form-control">
								   
										<form:option value="">
											<spring:message code="property.sel.optn" text="" />
										</form:option>

									</form:select>
								</td>

								<td><form:input type="text"
										path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfStartDate"
										id="fromDate_${count}" class="form-control datepicker2" /></td>
								<td><form:input type="text"
										path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfEndDate"
										id="toDate_${count}" class="form-control datepicker2" /></td>

								<!-- <td>
									 <select class="srNoCss testone form-control"
										multiple="multiple">
									 </select> 
								 </td> -->

								<td>
									<form:select class="srNoCss testone form-control"
										multiple="multiple" id="proAssfActive_${count}"
										path="entity.provisionalAssesmentDetails[${count}].listOfProvAsAssesmentFactorDetail[${factorIndex}].proAssfActive">
									</form:select>
								</td>

								<td>
								<a href="javascript:void(0);" title="Add"
									class="addCF btn btn-success btn-sm" > <i
										class="fa fa-plus-circle"></i>
								</a> <a href="javascript:void(0);" title="Delete" 
									class="remCF btn btn-danger btn-sm"> <i
										class="fa fa-trash-o"></i>
								</a></td>
							</tr>
						</c:forEach>
					</c:forEach> --%>
				</tbody>
			</table>
		</div>

	</div>
</div>
