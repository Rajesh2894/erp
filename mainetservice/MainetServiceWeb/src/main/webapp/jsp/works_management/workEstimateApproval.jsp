<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/workEstimateApproval.js"></script>
<script type="text/javascript"
	src="js/works_management/wmsWorkDefination.js"></script>
<script type="text/javascript"
	src="js/works_management/workEstimateSummary.js"></script>
<script type="text/javascript"
	src=js/works_management/reports/workEstimateReports.js></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/termsAndConditionForApproval.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<!-- Defect #80503 -->
<ol class="breadcrumb">
	<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
	<li><a href="javascript:void(0);"><spring:message text="Public Works Department" code="public.works.dept" /></a></li>
	<li><a href="javascript:void(0);"><spring:message text="Transactions" code="works.dept.transaction" /></a></li>
	<li class="active"><spring:message text="Work Estimate Approval" code="work.estimate.approval"/></li>
</ol>
<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>
<style>
div.radio label.radio-inline:nth-child(1) {
		color: #5cb85c;
}

div.radio label.radio-inline:nth-child(2) {
		color: #f0ad4e;
}

div.radio label.radio-inline:nth-child(3) {
		color: #d9534f;
}

div.radio label.radio-inline:nth-child(4) {
		color: #428bca;
}
</style>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.estimate.approval.title" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WorkEstimateApproval.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<form:form action="WorkEstimateApproval.html"
				cssClass="form-horizontal" name="estimateApproval"
				id="estimateApproval">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="taskName" id="taskName" />

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#collapse1"><spring:message
										code="work.estimate.approval.estimate.details" /> </a>
							</h4>
						</div>
						<div id="" class="panel-collapse ">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="project.master.dept" /></label>
									<div class="col-sm-4">
										<form:select path="workDefinitionDto.deptId"
											cssClass="form-control mandColorClass" disabled="true"
											id="depId">
											<c:forEach items="${command.departmentsList}"
												var="department">
												<form:option value="${department.dpDeptid}"
													code="${department.dpNameMar}">${department.dpDeptdesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<apptags:input labelCode="project.master.projname"
										path="workDefinitionDto.projName" cssClass=""
										isReadonly="true"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="work.def.workname"
										path="workDefinitionDto.workName" maxlegnth="250"
										isReadonly="true"></apptags:input>
									<apptags:input labelCode="tender.estimateamount"
										path="workDefinitionDto.workEstAmt" isReadonly="true"
										cssClass="text-right"></apptags:input>
								</div>
								<%-- <div class="form-group">
									<apptags:input labelCode="work.def.startDate"
										path="workDefinitionDto.workStartDate"
										cssClass="mandColorClass" isReadonly="true"></apptags:input>

									<apptags:input labelCode="work.def.endDate"
										path="workDefinitionDto.workEndDate" cssClass=""
										isReadonly="true"></apptags:input>
								</div> --%>
								<div class="form-group">
									<apptags:input
										labelCode="work.estimate.approval.sanction.number"
										path="sanctionNumber" isReadonly="true"></apptags:input>
									<apptags:date labelCode="work.estimate.approval.sanction.date"
										datePath="sancDate" fieldclass="" isDisabled="true"></apptags:date>

								</div>
								<div class="form-group">
									<apptags:input
										labelCode="work.estimate.approval.sanction.authrize.name"
										path="empName" isReadonly="true"></apptags:input>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="work.estimate.approval.download.estimate.documents" /></label>
									<div class="col-sm-4">
										<a class="text-center" href="#"
											onclick="viewWorkEstimateAbstractReport(${command.workDefinitionDto.projId},${command.workDefinitionDto.workId},'${command.reportType}',${command.workDefinitionDto.workType},${command.workDefinitionDto.deptId});"
											style="text-decoration: underline; text-align: center;"><span><c:out
													value="${command.workDefinitionDto.workcode}"></c:out></span></a>
									</div>

									<c:choose>
										<c:when
											test="${command.taskName eq 'Initiator' && command.flagForSendBack eq 'SEND_BACK' }">
											<label class="col-sm-2 control-label"><spring:message
													code="work.estimate.approval.edit.estimate" /></label>
											<div class="col-sm-4">
												<a class="text-center" href="#"
													onclick="getEditWorkEstimate(${command.workDefinitionDto.workId},'${command.estimateMode}')"
													style="text-decoration: underline; text-align: center;"><span><c:out
															value="${command.workDefinitionDto.workcode}"></c:out></span></a>
											</div>
										</c:when>
										<c:otherwise>
											<label class="col-sm-2 control-label"><spring:message
													code="work.estimate.approval.view.estimate" /></label>
											<div class="col-sm-4">
												<a class="text-center" href="#"
													onclick="getEditWorkEstimate(${command.workDefinitionDto.workId},'${command.estimateMode}')"
													style="text-decoration: underline; text-align: center;"><span><c:out
															value="${command.workDefinitionDto.workcode}"></c:out></span></a>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>

					<c:if test="${command.sanctionFlag eq 'T' }">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a1" data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#collapse1"><spring:message
											code="work.estimate.approval.estimate.budget.details"
											text="Budget Details" /> </a>
								</h4>
							</div>
							<div id="" class="panel-collapse ">
								<div class="panel-body">
									<div class="table-responsive clear">
										<table class="table table-striped table-bordered" id="">
											<thead>
												<tr>
													<th width="10%" scope="col" align="center"><spring:message
															code="ser.no" text="Sr.No" /></th>
													<th width="45%" scope="col" align="center"><spring:message
															code="work.def.fin.year" /></th>
													<th width="45%" scope="col" align="center"><spring:message
															code="work.estimate.approval.budget.head"
															text="Budget Head" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${command.yearDetDtosList}" var="yearDto"
													varStatus="status">
													<tr>
														<td>${status.count}</td>
														<td align="center">${yearDto.faYearFromTo}</td>
														<td align="center">${yearDto.financeCodeDesc}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${command.sanctionFlag eq 'A' }">
						<c:if test="${command.cpdMode ne 'L'}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-target="#a2" data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse" href="#a2"><spring:message
												code="work.def.fin.info"
												text="Year-wise Financial Information" /> </a>
									</h4>
								</div>
								<div id="a2" class="panel-collapse ">
									<div class="panel-body">
										<div class="">
											<c:set var="d" value="0" scope="page"></c:set>
											<table class="table table-striped table-bordered"
												id="financeDataDetails">
												<thead>
													<tr>
														<%-- <th width="10%" scope="col" align="center"><spring:message
															code="ser.no" text="Sr.No" /></th> --%>
														<th width="18%"><spring:message
																code="work.def.fin.year" text="Financial Year" /><span
															class="mand">*</span></th>
														<th width="18%"><spring:message
																code="work.def.fin.code" text="Finance Code" /><span
															class="mand">*</span></th>
														<th width="18%"><spring:message
																code="work.def.docs.no" text="Approval No." /><span
															class="mand">*</span></th>
														<th width="18%"><spring:message
																code="work.def.amount" text="Approved Amount" /><span
															class="mand">*</span></th>

													</tr>
												</thead>

												<tbody>

													<tr style="text-align: center;">
														<form:hidden
															path="workDefinitionDto.yearDtos[${d}].yearId"
															id="yearId${d}" />
														<td><form:select
																path="workDefinitionDto.yearDtos[${d}].faYearId"
																cssClass="form-control text-center"
																onchange="resetFinanceCode(this,${d});"
																id="faYearId${d}" disabled="true">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.faYears}" var="lookUp">
																	<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:input
																path="workDefinitionDto.yearDtos[${d}].financeCodeDesc"
																cssClass="form-control " id="yerFinCode${d}"
																disabled="true"
																maxlength="50"
																onkeyup="inputPreventSpace(event.keyCode,this);" /></td>

														<td><form:input
																path="workDefinitionDto.yearDtos[${d}].yeDocRefNo"
																cssClass="form-control text-center" id="yeDocRefNo${d}"
																disabled="true"
																maxlength="50"
																onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
														<td><form:input
																path="workDefinitionDto.yearDtos[${d}].yeBugAmount"
																cssClass="form-control text-right " id="yeBugAmount${d}"
																disabled="true"
																onkeypress="return hasAmount(event, this, 8, 2)"
																onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
																onkeyup="getTotalAmount()" /></td>

													</tr>

													<c:set var="d" value="${d + 1}" scope="page" />
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>

						</c:if>
						<c:if test="${command.cpdMode eq 'L'}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-target="#a2" data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse" href="#a2"><spring:message
												code="work.def.fin.info"
												text="Year-wise Financial Information" /> </a>
									</h4>
								</div>
								<div id="a2" class="panel-collapse ">
									<div class="panel-body">
										<div class="">
											<c:set var="d" value="0" scope="page"></c:set>
											<table class="table table-striped table-bordered"
												id="financeDataDetails">
												<thead>
													<tr>
														<%-- <th width="10%" scope="col" align="center"><spring:message
															code="ser.no" text="Sr.No" /></th> --%>
														<th width="18%"><spring:message
																code="work.def.fin.year" text="Financial Year" /><span
															class="mand">*</span></th>
														<th width="18%"><spring:message
																code="work.def.fin.code" text="Finance Code" /><span
															class="mand">*</span></th>
														<th width="18%"><spring:message
																code="work.def.docs.no" text="Approval No." /><span
															class="mand">*</span></th>
														<th width="18%"><spring:message
																code="work.def.amount" text="Approved Amount" /><span
															class="mand">*</span></th>

													</tr>
												</thead>

												<tbody>

													<tr>
														<form:hidden
															path="workDefinitionDto.yearDtos[${d}].yearId"
															id="yearId${d}" />
														<td><form:select
																path="workDefinitionDto.yearDtos[${d}].faYearId"
																cssClass="form-control"
																onchange="resetFinanceCode(this,${d});"
																id="faYearId${d}" disabled="true">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.faYears}" var="lookUp">
																	<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:select
																path="workDefinitionDto.yearDtos[${d}].sacHeadId"
																cssClass="form-control chosen-select-no-results"
																onchange="checkForDuplicateHeadCode(this,${d});"
																id="sacHeadId${d}" disabled="true">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.budgetList}" var="lookUp">
																	<form:option value="${lookUp.sacHeadId}">${lookUp.acHeadCode}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:input
																path="workDefinitionDto.yearDtos[${d}].yeDocRefNo"
																cssClass="form-control text-center" id="yeDocRefNo${d}"
																disabled="true"
																maxlength="50"
																onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
														<td><form:input
																path="workDefinitionDto.yearDtos[${d}].yeBugAmount"
																cssClass="form-control text-right " id="yeBugAmount${d}"
																disabled="true"
																onkeypress="return hasAmount(event, this, 8, 2)"
																onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
																onkeyup="getTotalAmount()" /></td>


													</tr>

													<c:set var="d" value="${d + 1}" scope="page" />
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a4"> <spring:message
											code="" text="Ward Zone Details" /></a>
								</h4>
								<div id="a4" class="panel-collapse collapse in">
									<div class="panel-body">
										<c:set var="d" value="0" scope="page"></c:set>
										<table
											class="table table-bordered  table-condensed margin-bottom-10"
											id="itemDetails">
											<thead>

												<tr>
													<%-- <apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
														showOnlyLabel="false"
														pathPrefix="workDefinitionDto.wardZoneDto[0].codId"
														hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control" showAll="false"
														disabled="${command.saveMode eq 'V' ? true : false }"
														hasTableForm="true" showData="false" columnWidth="20%" /> --%>
														
															<c:if test="${command.workDefinitionDto.wardZoneDto[0].level1 ne null}">
															<th><spring:message code="" text="${command.workDefinitionDto.wardZoneDto[0].level1} " /></th></c:if>
															<c:if test="${command.workDefinitionDto.wardZoneDto[0].level2 ne null}">
															<th><spring:message code="" text="${command.workDefinitionDto.wardZoneDto[0].level2}" /></th></c:if>
															<c:if test="${command.workDefinitionDto.wardZoneDto[0].level3 ne null}">
															<th><spring:message code="" text="${command.workDefinitionDto.wardZoneDto[0].level3}" /></th></c:if>
															<c:if test="${command.workDefinitionDto.wardZoneDto[0].level4 ne null}">
															<th><spring:message code="" text="${command.workDefinitionDto.wardZoneDto[0].level4}" /></th></c:if>
															<c:if test="${command.workDefinitionDto.wardZoneDto[0].level5 ne null}">
															<th><spring:message code="" text="${command.workDefinitionDto.wardZoneDto[0].level5}" /></th></c:if>
														


													<%-- <c:if test="${command.saveMode ne 'V'}">
														<th width="5%"><a title="Add" id="addBtn"
															class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
																class="fa fa-plus"></i></a></th>
													</c:if> --%>
												</tr>
											</thead>

											<tbody>
												<c:choose>
													<c:when
														test="${fn:length(command.workDefinitionDto.wardZoneDto) > 0}">
														<c:forEach var="taxData"
															items="${command.workDefinitionDto.wardZoneDto}"
															varStatus="status">
															
															<tr class="itemDetailClass">
																<form:hidden
																	path="workDefinitionDto.wardZoneDto[${d}].wardZoneId"
																	id="wardZoneId${d}" />
																	<c:if test="${taxData.codId1 ne null}">
																	<td style="text-align: center"><c:out value="${taxData.codId1Desc}"></c:out></td></c:if>
																	<c:if test="${taxData.codId2 ne null}">
																	<td style="text-align: center"><c:out value="${taxData.codId2Desc}"></c:out></td></c:if>
																	<c:if test="${taxData.codId3 ne null}">
																	<td style="text-align: center"><c:out value="${taxData.codId3Desc}"></c:out></td></c:if>
																	<c:if test="${taxData.codId4 ne null}">
																	<td style="text-align: center"><c:out value="${taxData.codId4Desc}"></c:out></td></c:if>
																	<c:if test="${taxData.codId5 ne null}">
																	<td style="text-align: center"><c:out value="${taxData.codId5Desc}"></c:out></td></c:if>
																<%-- <apptags:lookupFieldSet baseLookupCode="ZWB"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="workDefinitionDto.wardZoneDto[${d}].codId"
																	hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	disabled="${command.saveMode eq 'V' ? true : false }"
																	cssClass="form-control required-control"
																	showAll="false" hasTableForm="true" showData="true" /> --%>
																	

																<%-- <c:if test="${command.saveMode ne 'V'}">
																	<td class="text-center"><a
																		href="javascript:void(0);"
																		class="btn btn-danger btn-sm delButton" id="deleteBtn"
																		onclick=""><i class="fa fa-minus"></i></a></td>
																</c:if> --%>

															</tr>
															<c:set var="d" value="${d + 1}" scope="page" />


														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr class="itemDetailClass">

															<form:hidden
																path="workDefinitionDto.wardZoneDto[${d}].wardZoneId"
																id="wardZoneId${d+1}" />
															<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
																showOnlyLabel="false"
																pathPrefix="workDefinitionDto.wardZoneDto[${d}].codId"
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																disabled="${command.saveMode eq 'V' ? true : false }"
																cssClass="form-control required-control" showAll="false"
																hasTableForm="true" showData="true" />

															<td class="text-center"><a
																href="javascript:void(0);"
																class="btn btn-danger btn-sm delButton" onclick=""><i
																	class="fa fa-minus"></i></a></td>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</c:if>
					</c:if>

					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#remarks"> <spring:message
									code="" text="Note Sheet Remarks" /></a>
						</h4>

						<div id="remarks" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered  table-condensed margin-bottom-10"
									id="remarks">
									<thead>
										<th width="15%"><spring:message code="" text="Date" /><span
											class="mand"></span></th>
										<th width="12%"><spring:message code="" text="Task Name" /><span
											class="mand"></span></th>
										<th width="12%"><spring:message code="" text="Decision" /><span
											class="mand"</span></th>
										<th width="35%"><spring:message code="" text="Remark" /><span
											class="mand"</span></th>

									</thead>
									<c:forEach var="taxData" items="${command.actionWithDocs}"
										varStatus="remarks">
										<c:if test="${taxData.comments != null}">
											<tr style="text-align: center;">
												<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
														value="${taxData.createdDate}" /></td>
												<td><c:out value="${taxData.taskName}"></c:out></td>
												<td><c:out value="${taxData.decision}"></c:out></td>
												<td><c:out value="${taxData.comments}"></c:out></td>

											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</div>

					</div>
					<jsp:include
						page="/jsp/works_management/termsAndConditionForApproval.jsp"></jsp:include>
					<apptags:CheckerAction showInitiator="true" />
					<!-- Remove As per Suda UAT -->
					<%-- <div class="form-group">
						<label class="col-sm-2 control-label "><spring:message
								code="work.mb.approval.isFinalApproval" text="" /></label>
						<form:checkbox path="finalApproval"
							cssClass="checkbox-inline col-sm-1"
							disabled="${command.taskName eq 'Initiator'}" />
					</div> --%>
				</div>
				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success"
						onclick="showConfirmBoxForApproval(this);">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="mileStone.submit" text="" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel"
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

