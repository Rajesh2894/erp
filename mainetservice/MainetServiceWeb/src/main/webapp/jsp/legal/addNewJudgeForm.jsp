<!-- Start JSP Necessary Tags -->
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
<script src="js/legal/judgemaster.js"></script>
<script src="js/legal/addNewJudge.js"></script>

<script src="js/mainet/validation.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.judgemasterform"
						text="judgemasterform" /></strong>
			</h2>
		</div>

		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="JudgeMaster.html" method="POST"
				name="judgeMasterForm" class="form-horizontal" id="judgeMasterForm"
				commandName="command">
				<form:hidden path="" id= "caseId" value= "${command.caseId}"/>

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="lgl.judgeinfo" text="Judge Information" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
							<div class="form-group">
								<apptags:input labelCode="lgl.firstName"
									path="judgeMasterDto.judgeFName" isMandatory="true"
									cssClass="" maxlegnth="100"></apptags:input>
									
								<apptags:input labelCode="lgl.lastName"
									path="judgeMasterDto.judgeLName" isMandatory="true"
									maxlegnth="100"></apptags:input>
								</div>
							
							<div class="form-group">
								<apptags:input labelCode="lgl.benchName"
									path="judgeMasterDto.judgeBenchName" isMandatory="true"
									isDisabled="${command.saveMode eq 'V' ? true : false }"
									maxlegnth="100"></apptags:input>
							</div>
                                
						</div>
					</div>



						<%-- grid start--%>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"> <spring:message
											code="lgl.judgeinfor" text="Judge Information" />
									</a>
								</h4>
							</div>

							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">

									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="judgemasterTbl">
										<thead>
											<tr>
												<th scope="col" width="3%"><spring:message
														code="lgl.judgeId" text="Sr.No." /></th>
												<th scope="col" width="10%"><spring:message
														code="lgl.crtName" text="Court Name" /><span class="mand" >*</span></th>
												<th scope="col" width="15%"><spring:message
														code="lgl.crtAddress" text="Court Address" /><span></span></th>
												<th scope="col" width="5%"><spring:message
														code="lgl.appointmentStatus" text=" Status" /><span
													 ></span></th>

												<c:if test="${command.saveMode ne 'V'}">
													<th scope="col" width="5%"><a
														href="javascript:void(0);" data-toggle="tooltip"
														data-placement="top"
														onclick="addEntryData('judgemasterTbl');"
														class=" btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
										</thead>

										<tbody>

											<c:choose>
												<c:when
													test="${command.saveMode eq 'V' || command.saveMode eq 'E' }">

													<c:forEach var="judgeInfo"
														items="${command.judgeMasterDto.judgeDetails}"
														varStatus="status">
														<tr>
															<c:set var="disabledRow"
																value="${not empty command.judgeMasterDto.judgeDetails[d].id}"
																scope="page"></c:set>
															<c:set var="disabledToDate"
																value="${command.judgeMasterDto.judgeDetails[d].judgeStatus eq 'N'|| command.saveMode eq 'V'}"
																scope="page"></c:set>

															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass "
																	id="sequence${d}" value="${d+1}" disabled="true" /></td>
															<td align="center"><form:select
																	path="judgeMasterDto.judgeDetails[${d}].crtId"
																	cssClass="form-control" id="crtName${d}"
																	disabled="${disabledRow}" data-rule-required="true"
																	onchange="showCourtAddress(this)">
																	<form:option value="">Select</form:option>
																	<c:forEach items="${command.courts}" var="category">
																		<form:option value="${category.lookUpId}"
																			code="${category.lookUpCode}">${category.lookUpDesc}</form:option>
																	</c:forEach>

																</form:select></td>
															<td align="center"><form:textarea
																	path="judgeMasterDto.judgeDetails[${d}].crtAddress"
																	style="margin: 0px; height: 33px;"
																	cssClass="form-control" maxlength="4000"
																	id="crtAddress${d}" disabled="true"
																	data-rule-required="true" /></td>
															<td align="center"><form:select
																	path="judgeMasterDto.judgeDetails[${d}].judgeStatus"
																	cssClass="form-control" id="judgeStatus${d}"
																	onchange=""
																	disabled="${disabledRow  && disabledToDate}"
																	data-rule-required="true">
																	<form:option value="">Select</form:option>
																	<form:option value="Y">Yes</form:option>
																	<form:option value="N">No</form:option>
																</form:select></td>
															<c:if test="${command.saveMode ne 'V'}">
																<td align="center"><c:if
																		test="${empty command.judgeMasterDto.judgeDetails[d].id}">
																		<a class="btn btn-danger btn-sm delButton"
																			onclick="deleteEntry('judgemasterTbl',$(this),'removedIds');">
																			<i class="fa fa-minus"></i>
																		</a>
																	</c:if></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequence${d}" value="${d+1}" disabled="true" /></td>
														<td align="center"><form:select
																path="judgeMasterDto.judgeDetails[${d}].crtId"
																cssClass="form-control" id="crtName${d}"
																data-rule-required="true"
																
																disabled="${command.saveMode eq 'V' ? true : false }"
																onchange="showCourtAddress(this)">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.courts}" var="category">
																	<form:option value="${category.lookUpId}"
																		code="${category.lookUpCode}">${category.lookUpDesc}</form:option>
																</c:forEach>
<!-- data-rule-required="true" for validation -->
															</form:select></td>
														<td align="center"><form:textarea
																path="judgeMasterDto.judgeDetails[${d}].crtAddress"
																style="margin: 0px; height: 33px;"
																cssClass="form-control" maxlength="4000"
																id="crtAddress${d}" disabled="true"
																data-rule-required="true" /></td>
														<td align="center"><form:select
																path="judgeMasterDto.judgeDetails[${d}].judgeStatus"
																cssClass="form-control" id="judgeStatus${d}" onchange=""
																disabled="${command.saveMode eq 'V' ? true : false }"
																>
																<form:option value="">Select</form:option>
																<form:option value="Y">Yes</form:option>
																<form:option value="N">No</form:option>
															</form:select></td>

														<c:if test="${command.saveMode ne 'V'}">
															<td align="center"><a
																class="btn btn-danger btn-sm delButton"
																onclick="deleteEntry('judgemasterTbl',$(this),'removedIds');">
																	<i class="fa fa-minus"></i>
															</a></td>
														</c:if>
													</tr>
												</c:otherwise>
											</c:choose>


										</tbody>
									</table>
								</div>
							</div>
						</div>
						<%-- grid end--%>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-success btn-submit"
							onclick="saveJudgeData1(this,'${command.caseId}')" id="btnSave">
							<spring:message code="lgl.save" text="Save" />
						</button>
					<apptags:resetButton></apptags:resetButton>
					<%-- <apptags:backButton url="JudgeMaster.html"></apptags:backButton> --%>
					<button type="button" class="btn btn-danger "
							onclick="backToHearing('HearingDetails.html?EDIT','E','${command.caseId}')" id="btnBack">
							<spring:message code="" text="Back" />
						</button>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>




