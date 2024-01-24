<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/Segregation.js"></script>


<script type="text/javascript" src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.segwst"
						text="Segregation Of Waste" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand" /><i
					class="text-red-1">* </i> <spring:message
						code="solid.waste.mand.field" /> </span>
			</div>
			<!-- End mand-label -->


			<!-- Start Form -->
			<form:form action="Segregation.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="segregationform" id="id_segregationform">

				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="swm.wstdetails" text="Waste Details" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<c:choose>
									<c:when
										test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
										<div class="form-group">
											<apptags:input labelCode="swm.transactionno"
												path="segregationDto.grId" isMandatory="" isDisabled="true"></apptags:input>
											<label class="col-sm-2 control-label required-control"
												for="desposalsite"><spring:message
													code="swm.dsplsite" /> </label>
											<div class="col-sm-4">
												<form:select path="segregationDto.deId"
													class="form-control mandColorClass " label="Select"
													id="deId"
													disabled="${command.saveMode eq 'V' ? true : false }">
													<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
													<c:forEach items="${command.mrfMasterList}" var="lookUp">
												<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
											</c:forEach>
												</form:select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label " for="date"><spring:message
													code="swm.date" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="segregationDto.grDate"
														onchange="getDisposal()" readonly="false"
														cssClass="form-control  mandColorClass datepicker"
														autocomplete="off" id="grDate"
														disabled="${command.saveMode eq 'V' ? true : false }" placeholder="dd/mm/yyyy" maxlength="10" />
													<label class="input-group-addon"><i
														class="fa fa-calendar"></i></label>
												</div>
											</div>
											<label class="col-sm-2 control-label" for="garbage"><spring:message
													code="swm.ttlgarbage" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="segregationDto.grTotal"
														cssClass="form-control  mandColorClass hasDecimal text-right"
														id="ttlgrbg" readonly="true"
														disabled="${command.saveMode eq 'V' ? true : false }" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label " for=""><spring:message
													code="swm.Employee" text="Employee / Inspector Name" /></label>
											<div class="col-sm-4">
												<form:select path="segregationDto.empId"
													class="form-control  chosen-select-no-results"
													label="Select" id="empId"
													disabled="${command.saveMode eq 'V' ? true : false }">
													<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
													<c:forEach items="${command.employeeList}" var="lookup">
														<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
													</c:forEach>
												</form:select>
											</div>							
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<!-- <div class="col-sm-6"></div> -->
											<label class="col-sm-2 control-label required-control"
												for="desposalsite"><spring:message
													code="swm.dsplsite" /> </label>
											<div class="col-sm-4">
												<form:select path="segregationDto.deId"
													class="form-control mandColorClass " label="Select"
													id="deId">
													<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
													<c:forEach items="${command.mrfMasterList}" var="lookUp">
												<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
											</c:forEach>
												</form:select>
											</div>
											<label class="col-sm-2 control-label " for=""><spring:message
													code="swm.Employee" text="Employee / Inspector Name" /></label>
											<div class="col-sm-4">
												<form:select path="segregationDto.empId"
													class="form-control  chosen-select-no-results"
													label="Select" id="empId"
													disabled="">
													<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
													<c:forEach items="${command.employeeList}" var="lookup">
														<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
													</c:forEach>
												</form:select>
											</div>
											
										</div>
										<div class="form-group">
											
											<label class="col-sm-2 control-label" for="garbage"><spring:message
													code="swm.ttlgarbage" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="segregationDto.grTotal"
														cssClass="form-control  mandColorClass hasDecimal text-right"
														id="ttlgrbg" disabled="" readonly="true" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
											<label class="col-sm-2 control-label" for="date"><spring:message
													code="swm.date" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="segregationDto.grDate"
														onchange="getDisposal()" readonly="false"
														cssClass="form-control  mandColorClass datepicker"
														autocomplete="off" id="grDate" disabled="" placeholder="dd/mm/yyyy" maxlength="10" />
													<label class="input-group-addon"><i
														class="fa fa-calendar"></i></label>
												</div>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"> <spring:message
										code="swm.segbreakup" text="Segregation/Break Up " /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table class="table table-bordered table-striped"
									id="id_segregationTbl">
									<thead>
										<tr>
											<th scope="col" width="6%"><spring:message
													code="population.master.srno" text="Sr.No." /></th>

											<apptags:lookupFieldSet baseLookupCode="WTY" hasId="true"
												showOnlyLabel="false"
												pathPrefix="segregationDto.tbSwWastesegDets[0].codWast"
												isMandatory="true" hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true"
												cssClass="form-control required-control" showAll="false"
												hasTableForm="true" showData="false" columnWidth="22%" />
											<th scope="col" width="20%"><spring:message
													code="swm.volume" text="Volume" /></th>
											<c:if test="${command.saveMode ne 'V'}">		
												<th scope="col" width="8%"><spring:message code="solid.waste.action" text="Action" /></th>
											</c:if>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td align="right"><span style="font-weight: bold"><spring:message
														code="swm.total" text="Total" /></span></td>
											<td align="center"><div align="center"
													class="input-group">
													<input type="text"
														class="form-control mandColorClass text-right"
														id="id_total" disabled><span
														class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div></td>

											<c:if test="${command.saveMode ne 'V'}">
												<td></td>
											</c:if>
										</tr>
									</tfoot>
									<tbody>
										<c:choose>
											<c:when
												test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
												<c:forEach var="tripInfo"
													items="${command.segregationDto.tbSwWastesegDets}"
													varStatus="status">
													<tr class="firstUnitRow">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequence${status.index}" value="${status.count}"
																disabled="true" /></td>

														<apptags:lookupFieldSet baseLookupCode="WTY" hasId="true"															
															pathPrefix="segregationDto.tbSwWastesegDets[${status.index}].codWast"
															isMandatory="true" hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true"
															disabled="${command.saveMode eq 'V' ? true : false }"
															cssClass="form-control required-control " showAll="false"
															hasTableForm="true" showData="true" />
														<td align="center">
															<div class="input-group">
																<form:input
																	path="segregationDto.tbSwWastesegDets[${status.index}].tripVolume"
																	cssClass="form-control  mandColorClass hasDecimal text-right"
																	onchange="sum()" id="segVolume${status.index}"
																	disabled="${command.saveMode eq 'V' ? true : false }" />
																<span class="input-group-addon"><spring:message
																		code="swm.kgs" text="Kilograms" /></span>
															</div>
														</td>
														<c:if test="${command.saveMode ne 'V'}">
															<td class="text-center">
															<a href="javascript:void(0);" title="Add"
																class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i
																	class="fa fa-plus-circle"></i></a>
															<a class="btn btn-danger btn-sm delButton"
																onclick="deleteEntry2('id_segregationTbl',$(this),'sequence${status.index}')">
																	<i class="fa fa-minus"></i>
															</a></td>

														</c:if>
													</tr>
													<c:set var="d" value="${status.count}" scope="page" />
												</c:forEach>

											</c:when>
											<c:otherwise>
												<tr class="firstUnitRow">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence${d}"
															value="${d+1}" disabled="true" /></td>

													<apptags:lookupFieldSet baseLookupCode="WTY" hasId="true"													
														pathPrefix="segregationDto.tbSwWastesegDets[0].codWast"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control " showAll="false"
														hasTableForm="true" showData="true" />
													<td align="center">
														<div class="input-group">
															<form:input
																path="segregationDto.tbSwWastesegDets[0].tripVolume"
																cssClass="form-control  mandColorClass hasDecimal text-right"
																onchange="sum()" id="segVolume${d}" disabled="" />
															<span class="input-group-addon"><spring:message
																	code="swm.kgs" text="Kilograms" /></span>
														</div>
													</td>
													<td class="text-center">
													<a href="javascript:void(0);" title="Add"
														class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i
															class="fa fa-plus-circle"></i></a>
													<a class="btn btn-danger btn-sm delButton" title="Delete"
														onclick="deleteEntry2('id_segregationTbl',$(this),'sequence${d}')">
															<i class="fa fa-minus"></i>
													</a></td>

												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit" title='<spring:message code="solid.waste.submit" text="Submit" />'
							onclick="Proceed(this)" id="btnSave">
							<spring:message code="solid.waste.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<apptags:resetButton cssClass="resetBtn"></apptags:resetButton>
					</c:if>
					<apptags:backButton url="Segregation.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
