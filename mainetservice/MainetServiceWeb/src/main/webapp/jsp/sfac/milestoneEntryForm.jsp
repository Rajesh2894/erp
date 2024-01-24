<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script type="text/javascript" src="js/sfac/milestoneEntryForm.js"></script>

<style>
table.crop-details-table tbody tr td>input[type="checkbox"] {
	margin: 0.5rem 0 0 -0.5rem;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

#udyogAadharApplicable, #isWomenCentric {
	margin: 0.6rem 0 0 0;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.milestone.entry.fund.request.title"
					text="Milestone Entry Form" />
			</h2>
			<apptags:helpDoc url="MilestoneEntryForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="MilestoneEntryForm" action="MilestoneEntryForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#milestoneDetails"> <spring:message
										code="sfac.milestone.details" text="Milestone Details" />
								</a>
							</h4>
						</div>
						<div id="milestoneDetails" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.IA.name" text="IA Name" /></label>
									<div class="col-sm-4">
										<form:input path="milestoneMasterDto.iaName" id="iaName"
											class="form-control " disabled="true" />
										<form:hidden path="milestoneMasterDto.iaId" id="iaId" />

									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.milestone.name" text="Milestone Name" /></label>
									<div class="col-sm-4">
										<form:input path="milestoneMasterDto.milestoneId"
											id="milestoneId" class="form-control alphaNumeric"
											disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }" maxlength="200" />


									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.desc" text="Description" /></label>
									<div class="col-sm-10">
										<form:textarea path="milestoneMasterDto.description"
											id="description" class="form-control alphaNumeric" 
											disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }" />


									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.overall.budget"
											text="Overall Budget (per FPO per CBBO)" /></label>
									<div class="col-sm-4">
										<form:input path="milestoneMasterDto.overallBudget"
											id="overallBudget"
											onkeypress="return hasAmount(event, this, 10, 2)"
											class="form-control "
											disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }" />

									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.target.age"
											text="Target Age of Completion (in months) " /></label>
									<div class="col-sm-4">
										<form:input path="milestoneMasterDto.targetAge" id="targetAge"
											class="form-control hasNumber" maxlength="3"
											disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }" />


									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.pert.payment"
											text="Percentage of payment to be released" /></label>
									<div class="col-sm-4">
										<form:input path="milestoneMasterDto.percantageOfPayment"
											id="percantageOfPayment"
											onkeypress="return hasAmount(event, this, 4, 2)"
											class="form-control"
											disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }" />


									</div>

								</div>


							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#deliverablesDetails"> <spring:message
										code="sfac.milestone.deliverables.title"
										text="Deliverables Details" />
								</a>
							</h4>
						</div>
						<div id="deliverablesDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="msDetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th width="25%"><spring:message
													code="sfac.milestone.deliverables" text="Deliverables" /></th>

											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.milestoneMasterDto.milestoneDeliverablesDtos)>0 }">
												<c:forEach var="dto"
													items="${command.milestoneMasterDto.milestoneDeliverablesDtos}"
													varStatus="status">
													<tr class="appendableMSDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>



													 	<td><form:textarea
																path="milestoneMasterDto.milestoneDeliverablesDtos[${d}].deliverables"
																disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }"
																id="deliverables${d}" class="form-control alphaNumeric" /></td> 



														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addDelButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addDelButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteDelDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteDelDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableMSDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>


													<td><form:textarea
															path="milestoneMasterDto.milestoneDeliverablesDtos[${d}].deliverables"
															disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }"
															id="deliverables${d}" class="form-control alphaNumeric" /></td>



													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addDelButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addDelButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteDelDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteDelDetails(this);"> <i
																class="fa fa-trash"></i>
															</a>
														</c:if></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>




							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#msCBBODetails">
									<spring:message code="sfac.milestone.cbbo.details"
										text="CBBO Details" />
								</a>
							</h4>
						</div>
						<div id="msCBBODetails" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="msCBBODetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>


											<th width="20%"><spring:message code="sfac.fpo.cbbo.name"
													text="CBBO Name" /></th>
											<th><spring:message
													code="sfac.milestone.date.of.workorder"
													text="Date Of Work Order" /></th>
											<th><spring:message
													code="sfac.milestone.target.age.month"
													text="Target Age (in months)" /></th>
											<th><spring:message code="sfac.milestone.no.of.fpo"
													text="No Of FPO" /></th>
											<th><spring:message
													code="sfac.milestone.allocation.budget"
													text="Allocation Budget" /></th>
													<c:if
																test="${command.viewMode eq 'U' || command.viewMode eq 'V'}">	
												<th><spring:message
													code="sfac.milestone.payment.status"
													text="Payment Status" /></th>	
													</c:if>	
												<c:if
															test="${command.viewMode ne 'V' && command.viewMode ne 'U'}">
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
													</c:if>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.milestoneMasterDto.milestoneCBBODetDtos)>0 }">
												<c:forEach var="dto"
													items="${command.milestoneMasterDto.milestoneCBBODetDtos}"
													varStatus="status">
													<tr class="appendablemsCBBODetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoCBBO${d}"
																value="${d+1}" disabled="true" />
														<td><form:select
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].cbboID" id="cbboId${d}"
																disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }"
																cssClass="form-control chosen-select-no-results">
																<form:option value="0">
																	<spring:message text="Select" code="sfac.select" />
																</form:option>
																<c:forEach items="${command.cbboMasterDtos}" var="dto">
																	<form:option value="${dto.cbboId}">${dto.cbboName}</form:option>
																</c:forEach>
															</form:select></td>

														<td><div class="input-group"><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].dateOfWorkOrder"
																type="text"
																class="form-control datepicker hasDatepicker mandColorClass"
																disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }"
																id="dateOfWorkOrder${d}" placeholder="dd/mm/yyyy"
																readonly="true" /> <span class="input-group-addon"><i
																class="fa fa-calendar"></i></span></div></td>

														<td><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].targetAge"
																readonly="true" id="targetAge${d}"
																class="form-control hasNumber " maxlength="3" /></td>

														<td><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].noOfFPO"
																readonly="true" id="noOfFPO${d}"
																class="form-control hasNumber " maxlength="3" /></td>
														<td><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].allocationBudget"
																 id="allocationBudget${d}"
																onkeypress="return hasAmount(event, this, 10, 2)"
																class="form-control"
																readonly="true" /></td>
															<c:if
																test="${command.viewMode eq 'U' || command.viewMode eq 'V'}">	
														<td><c:set var="baseLookupCode" value="PYS" /> <form:select
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].paymentStatus"
																class="form-control chosen-select-no-results"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="paymentStatus${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
															</c:if>
																<c:if
															test="${command.viewMode ne 'V' && command.viewMode ne 'U'}">
														<td class="text-center">
																<a class="btn btn-blue-2 btn-sm addCBBOButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addCBBOButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteCBBODetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteCBBODetails($(this));"> <i
																	class="fa fa-trash"></i>
																</a>
															</td></c:if>
 
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendablemsCBBODetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNoCBBO${d}"
															value="${d+1}" disabled="true" />
													<td><form:select
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].cbboID" id="cbboId${d}"
																disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }"
																cssClass="form-control chosen-select-no-results" onchange="getFPOCount(${d});">
																<form:option value="0">
																	<spring:message text="Select" code="sfac.select" />
																</form:option>
																<c:forEach items="${command.cbboMasterDtos}" var="dtoo">
																	<form:option value="${dtoo.cbboId}">${dtoo.cbboName}</form:option>
																</c:forEach>
															</form:select></td>

														 <td><div class="input-group"><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].dateOfWorkOrder"
																type="text"
																class="form-control datepicker mandColorClass"
																disabled="${command.viewMode eq 'V' || command.viewMode eq 'U' ? true : false }"
																id="dateOfWorkOrder${d}" placeholder="dd/mm/yyyy"
																readonly="true" /> <span class="input-group-addon"><i
																class="fa fa-calendar"></i></span></div></td>

														<td><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].targetAge"
																readonly="true" id="targetAge${d}"
																class="form-control hasNumber " maxlength="3" /></td>

														<td><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].noOfFPO"
																readonly="true" id="noOfFPO${d}"
																class="form-control hasNumber " maxlength="3" /></td>
														<td><form:input
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].allocationBudget"
																 id="allocationBudget${d}"
																onkeypress="return hasAmount(event, this, 10, 2)"
																class="form-control"
																readonly="true" /></td>
														<c:if
																test="${command.viewMode eq 'U' || command.viewMode eq 'V'}">	
														<td><c:set var="baseLookupCode" value="PYS" /> <form:select
																path="milestoneMasterDto.milestoneCBBODetDtos[${d}].paymentStatus"
																class="form-control chosen-select-no-results"
																disabled="${command.viewMode eq 'V'   ? true : false }"
																id="paymentStatus${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
															</c:if>		
													<c:if
															test="${command.viewMode ne 'V' && command.viewMode ne 'U'}">
													<td class="text-center">
															<a class="btn btn-blue-2 btn-sm addCBBOButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addCBBOButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteCBBODetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteCBBODetails($(this));"> <i
																class="fa fa-trash"></i>
															</a>
														</td> </c:if>

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>


					</div> 




				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveMilestoneEntryForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="sfac.button.reset" text="Reset"/>'
							onclick="ResetForm();">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="MilestoneEntryForm.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>