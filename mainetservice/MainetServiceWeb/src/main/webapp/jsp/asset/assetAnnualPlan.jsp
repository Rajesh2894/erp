<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/asset/assetAnnualPlan.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="${userSession.moduleDeptCode == 'AST' ? 'asset.annualPlan.header':'asset.ITannualPlan.header'}"
					text="Asset Annual Plan" />
			</h2>
			<apptags:helpDoc url="AssetAnnualPlan.html"
				helpDocRefURL="AssetAnnualPlan.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="AssetAnnualPlan.html" method="POST"
				class="form-horizontal" id="assetAnnualPlanId">
				<input type="hidden" id="moduleDeptUrl"
					value="${userSession.moduleDeptCode == 'AST' ? 'AssetAnnualPlan.html':'ITAssetAnnualPlan.html'}">
				<div class="error-div alert-danger padding-10 margin-bottom-10"
					id="errorDivId" style="display: none;">
					<ul>
						<li><label id="errorId"></label></li>
					</ul>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#annual"><spring:message
										code="asset.annualPlan.header" text="Annual Plan" /> </a>
							</h4>
						</div>
						<div id="annual" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="asset.annualPlan.finYear" text="Financial Year" /></label>
									<div class="col-sm-4">
										<form:select path="astAnnualPlanDTO.financialYear"
											id="financialYear"
											disabled="${command.approvalViewFlag eq 'V'}"
											class="form-control chosen-select-no-results"
											onchange="selectTemplateTypeData(this)">
											<form:option value="">
												<spring:message
													code="asset.info.select"
													text="Select" />
											</form:option>
											<%-- <c:forEach items="${command.financialYearMap}" var="entry">
												<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
											</c:forEach> --%>
											<c:forEach items="${command.faYears}" var="lookUp">
															<form:option value="${lookUp.faYear}" code="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="assetgroup"> <spring:message
											code="asset.annualPlan.department" /></label>
									<div class="col-sm-4">
										<form:select path="astAnnualPlanDTO.department.dpDeptid"
											disabled="${command.approvalViewFlag eq 'V'}" id="astDept"
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${command.departmentsList}" var="obj">
												<form:option value="${obj.dpDeptid}"
													code="${obj.dpDeptcode}">${obj.dpDeptdesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="assetgroup"> <spring:message
											code="asset.annualPlan.location" /></label>
									<div class="col-sm-4">
										<form:select path="astAnnualPlanDTO.locationMas.locId"
											disabled="${command.approvalViewFlag eq 'V'}" id="astLoc"
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${command.locList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#assetdetails"><spring:message
										code="voucher.template.map.detail" text="Asset Details" /></a>
							</h4>
						</div>

						<div id="assetdetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<c:set var="d" value="0" scope="page"></c:set>
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="assetDetTable">
											<thead>
												<tr>
													<th scope="col" width="3%"><spring:message
															code="ser.no" text="Sr.No." /></th>
													<th width="30%"><spring:message
															code="asset.information.hardwareName" text="Hardware Name" /><span
														class="mand">*</span></th>
													<th width="30%" class="text-center"><spring:message
															code="asset.annualPlan.description" text="Description"  /><span
														class="mand">*</span></th>
													<th width="15%"><spring:message
															code="asset.annualPlan.quantity" text="Quantity" /><span
														class="mand">*</span></th>
													<c:if test="${command.approvalViewFlag eq 'C'}">
														<th scope="col" width="5%"><a
															href="javascript:void(0);" data-toggle="tooltip"
															data-placement="top" onclick="addEntryData();"
															class=" btn btn-success btn-sm"><i
																class="fa fa-plus-circle"></i></a></th>
													</c:if>
												</tr>
											</thead>
											<tbody>
												<c:choose>
													<c:when test="${fn:length(command.astAnnualPlanDetDTO)>0 }">
														<c:forEach var="planDet"
															items="${command.astAnnualPlanDetDTO}">
															<tr class="appendableClass">
																<td align="center"><form:input path=""
																		cssClass="form-control mandColorClass "
																		id="sequence${d}" value="${d+1}" disabled="true" /></td>

																<td><form:select
																		path="astAnnualPlanDetDTO[${d}].astClass"
																		disabled="${command.approvalViewFlag eq 'V'}"
																		class="form-control" id="astClass${d}">
																		<form:option value="0"><spring:message code="asset.info.select" text="Select" /></form:option>
																		<c:forEach items="${command.astClassMovList}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td align="center" id="from"><form:textarea
																		path="astAnnualPlanDetDTO[${d}].astDesc" maxlength="500"
																		disabled="${command.approvalViewFlag eq 'V'}"
																		cssClass="form-control mandColorClass"
																		id="astDesc${d}" /></td>
																<td align="right"><form:input
																		path="astAnnualPlanDetDTO[${d}].astQty"
																		disabled="${command.approvalViewFlag eq 'V'}"
																		cssClass="form-control mandColorClass text-right"
																		onkeypress="return hasAmount(event, this, 3, 2)"
																		onchange="getAmountFormatInDynamic((this),'astQty')"
																		id="astQty${d}" /></td>

																<c:if test="${command.approvalViewFlag eq 'C'}">
																	<td align="center"><a href='#'
																		data-toggle="tooltip" data-placement="top"
																		class="btn btn-danger btn-sm delButton"
																		onclick="deleteEntry($(this),'removedIds');"><i
																			class="fa fa-minus"></i></a></td>
																</c:if>
																<c:set var="d" value="${d + 1}" scope="page" />
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr class="appendableClass">
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass "
																	id="sequence${d}" value="${d+1}" disabled="true" /></td>

															<td><form:select
																	path="astAnnualPlanDetDTO[${d}].astClass"
																	disabled="${command.approvalViewFlag eq 'V'}"
																	class="form-control" id="astClass${d}">
																	<form:option value="0"><spring:message code="asset.info.select" text="Select" /></form:option>
																	<c:forEach items="${command.astClassMovList}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td align="center" id="from"><form:textarea
																	path="astAnnualPlanDetDTO[${d}].astDesc" maxlength="500"
																	disabled="${command.approvalViewFlag eq 'V'}"
																	cssClass="form-control mandColorClass" id="astDesc${d}" /></td>
															<td align="right"><form:input
																	path="astAnnualPlanDetDTO[${d}].astQty"
																	disabled="${command.approvalViewFlag eq 'V'}"
																	cssClass="form-control mandColorClass text-right"
																	onkeypress="return hasAmount(event, this, 3, 2)"
																	onchange="getAmountFormatInDynamic((this),'astQty')"
																	id="astQty${d}" /></td>

															<c:if test="${command.approvalViewFlag eq 'C'}">
																<td align="center"><a href='#'
																	data-toggle="tooltip" data-placement="top"
																	class="btn btn-danger btn-sm delButton"
																	onclick="deleteEntry($(this),'removedIds');"><i
																		class="fa fa-minus"></i></a></td>
															</c:if>
															<c:set var="d" value="${d + 1}" scope="page" />
														</tr>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
				<c:if test="${command.approvalViewFlag eq 'C'}">
					<div class="text-center padding-top-10">

						<button type="button" class="btn btn-success btn-submit" title='<spring:message code="bt.save" text="Submit" />'
							onclick="saveAnnualPlan(this)">
							<spring:message code="bt.save" text="Submit" />
						</button>

						<button type="button" class="btn btn-warning" title='<spring:message code="bt.clear" text="Reset" />'
							onclick="window.location.href='${userSession.moduleDeptCode == 'AST' ? 'AssetAnnualPlan.html':'ITAssetAnnualPlan.html'}'">
							<spring:message code="bt.clear" text="Reset" />
						</button>
						
						<button type="button" class="btn btn-danger" title='<spring:message code="bt.backBtn" text="Back" />'
							onclick="window.location.href='${userSession.moduleDeptCode == 'AST' ? 'AssetAnnualPlan.html':'ITAssetAnnualPlan.html'}'" id="backBtn">
							<spring:message code="bt.backBtn" text="Back" />
						</button>

						
					</div>
				</c:if>
				
				<c:if test="${command.saveMode eq 'V'}">
					<div class="text-center padding-top-10">
						
						<button type="button" class="btn btn-danger" title='<spring:message code="bt.backBtn" text="Back" />'
							onclick="window.location.href='${userSession.moduleDeptCode == 'AST' ? 'AssetAnnualPlan.html':'ITAssetAnnualPlan.html'}'" id="backBtn">
							<spring:message code="bt.backBtn" text="Back" />
						</button>

					</div>
				</c:if>


				<c:if test="${command.approvalViewFlag eq 'V' && command.saveMode eq 'A' && command.completedFlag ne 'V' }">
					<div class="widget-content padding panel-group accordion-toggle"
						id="accordion_single_collapse1">
					<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
					</div>

					<div class="text-center widget-content padding">
						<button type="button" id="save" class="btn btn-success btn-submit" title='<spring:message code="master.save" text="Save" />'
							onclick="saveDecisionAction(this);">
							<spring:message code="master.save" text="Save" />
						</button>
						
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="bt.backBtn" text="Back" />'
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="bt.backBtn" text="Back" />
						</button>
					</div>
				</c:if>
				
						
						
				<c:if test="${command.completedFlag eq 'V'}">
					<div class="text-center widget-content padding">
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="bt.backBtn" text="Back" />'
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="bt.backBtn" text="Back" />
						</button>
					</div>
				</c:if>
			</form:form>
		</div>
	</div>
</div>