<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<form:form id="pensionSchemeId1" action="PensionSchemeMaster.html"
					method="POST" class="form-horizontal" name="pensionSchemeId1">
					<form:hidden path="modeType" id="modeType" />
					<form:hidden path="applValidFlag" id="applValidFlag" />
					<form:hidden path="envFlag" id="envFlag" />
<table class="table table-bordered margin-bottom-10"
						id="criteriatableId">
						<thead>
							<tr>
								<th colspan="2" class="text-center"><spring:message code="eligibility.criteria.factorapplicable" /></th>
								<th class="text-center" width="20%;"><spring:message code="eligibility.criteria.selectcriteria"/></th>
								<th class="text-center"><spring:message code="eligibility.criteria.rangefrom"/></th>
								<th class="text-center"><spring:message code="eligibility.criteria.rangeto"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.sourceLookUps}" var="lookUp"
								varStatus="varcount">
								<tr class="appendableClass1" id="appendableClassId">

									<form:hidden
										path="pensionSchmDto.pensioneligibilityList[${varcount.index}].factorApplicableId"
										value="${lookUp.lookUpId}" />
									<form:hidden
										path="pensionSchmDto.pensioneligibilityList[${varcount.index}].factorApplicableDesc"
										value="${lookUp.lookUpDesc}" />
									<td align="right"><form:checkbox
											path="pensionSchmDto.pensioneligibilityList[${varcount.index}].checkBox"
											onclick="removeDisable(${varcount.index},'${lookUp.lookUpDesc}');" cssClass="checkboxClass" value="true"/></td>
									<td align="center">${lookUp.lookUpDesc}</td>
									<td><form:select
											path="pensionSchmDto.pensioneligibilityList[${varcount.index}].criteriaId"
											class="form-control chosen-select-no-results" disabled="false">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.secondLevellookUps}"
												var="slookUp">
												<c:if test="${lookUp.lookUpId eq slookUp.lookUpParentId}">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:if>
											</c:forEach>

										</form:select></td>
									<td><form:input
											path="pensionSchmDto.pensioneligibilityList[${varcount.index}].rangeFrom"
											cssClass="text-right form-control" disabled="false" /></td>
									<td><form:input
											path="pensionSchmDto.pensioneligibilityList[${varcount.index}].rangeTo"
											cssClass="text-right form-control" disabled="false" /></td>
								</tr>
							</c:forEach>

							<th colspan="2" class="text-center vertical-align-middle"><spring:message code="eligibility.criteria.paymentschedule"/></th>
							<td><form:select
									path="pensionSchmDto.paySchedule"
									class="form-control chosen-select-no-results" disabled="false">
									<form:option value="">
										<spring:message code='master.selectDropDwn' />
									</form:option>
									<c:forEach items="${command.paymentList}" var="slookUp">
										<form:option value="${slookUp.lookUpId}"
											code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
									</c:forEach>

								</form:select></td>
							<th class="text-center vertical-align-middle"><spring:message code="eligibility.criteria.amount"/></th>
							<td><form:input
									path="pensionSchmDto.amt"
									cssClass="text-right form-control hasDecimal" disabled="false" /></td>
							</td>

						</tbody>
					</table>
					<div class="text-center margin-top-15 margin-bottom-15">
						<button type="button" class="btn btn-success" title="Save" onclick="savePensionCriteria(this)">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true" ></i>Save
						</button>
						<button type="button" class="btn btn-warning" title="Reset" onclick="resetPensionCriteria(this,${command.pensionSchmDto.schmeMsId},${command.pensionSchmDto.orgId})">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>Reset
						</button>
					</div>
					<!---------------- view the entered data start--------------------->
					<h4><spring:message text="Selected Details"/></h4>
					<c:forEach items="${command.saveDataList}" var="viewList"
								varStatus="varcount">
					<table class="table table-bordered margin-bottom-15" id="summitAfterSaveId">
						<tbody>
							<tr>
								<th class="text-center"><spring:message code="eligibility.criteria.factapp"/></th>
								<th class="text-center"><spring:message code="eligibility.criteria.selectcrite"/></th>
								<th class="text-center"><spring:message code="eligibility.criteria.rangefroms"/></th>
								<th class="text-center"><spring:message code="eligibility.criteria.rangetos"/></th>
								<th class="text-center"><spring:message code="eligibility.criteria.amounts"/></th>
								<th class="text-center"><spring:message code="eligibility.criteria.edits"/></th>
							</tr>
							<c:forEach items="${viewList}" var="viewLists"
								varStatus="varcount">
								<tr>
								<td><c:forEach items="${command.sourceLookUps}"
													var="lookUp">
													<c:if
														test="${lookUp.lookUpId eq viewLists.factorApplicableId}">
													 ${lookUp.lookUpDesc}
												</c:if>
												</c:forEach></td>
								<td>
								<c:forEach items="${command.secondLevellookUps}"
												var="slookUp">
												<c:if test="${slookUp.lookUpId eq viewLists.criteriaId}">
													 ${slookUp.lookUpDesc}
												</c:if>
											</c:forEach>
								</td>
								<td align="right">${viewLists.rangeFrom}</td>
								<td align="right">${viewLists.rangeTo}</td>
								<c:if test="${varcount.index==0}">
								<td rowspan="${fn:length(viewList)}" align="right">${viewLists.amt}</td>
								<td class="text-center vertical-align-middle" rowspan="${fn:length(viewList)}">
									<button type="button" class="deleteDetails btn btn-danger" name="button-123"
										id="" title="Delete" onclick="deleteDetails(${viewLists.batchId},this)">
										<i class="fa fa-trash" aria-hidden="true"></i>
									</button>
								</td>
								</c:if>
								</tr>
								</c:forEach>
							
						</tbody>
					</table>
					</c:forEach>
					<!---------------- view the entered data end--------------------->
					</form:form>