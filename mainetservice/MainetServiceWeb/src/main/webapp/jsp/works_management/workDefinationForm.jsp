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
	src="js/works_management/wmsWorkDefination.js"></script>
<script type="text/javascript"
	src="js/integration/asset/searchAssetUtility.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/workEstimateSummary.js"></script>

<script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvvwgwayDHlTq9Ng83ouZA_HWSxbni25c"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.def.title" text="Work Definition" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WmsWorkDefinationMaster.html"></apptags:helpDoc>

			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<form:form action="WmsWorkDefinationMaster.html"
				class="form-horizontal" name="WmsWorkDefinationMaster"
				id="WmsWorkDefinationMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="cpdMode" id="cpdMode" />
				<form:hidden path="formMode" id="mode" />
				<form:hidden path="removeAssetIds" id="removeAssetIds" />
				<form:hidden path="removeYearIds" id="removeYearIds" />
				<form:hidden path="removeFileById" id="removeFileById" />
				<form:hidden path="removeSancDetId" id="removeSancDetId" />
				<form:hidden path="removeWardZoneDetId" id="removeWardZoneDetId" />
				<form:hidden path="workStatus" value="${definitionDto.workStatus}" id="workStatus"/>

				<div class="form-group">
				
				    <div class="col-sm-8">
                     </div>
                     <div class="form-group">
						 
									<c:if test="${command.getGisValue() eq 'Y'}">
										 <button type="button" class="btn btn-success btn-submit"
												onclick=" window.open('${command.gISUri}&uniqueid=${command.wmsDto.workcode}')"
												id="">
												<spring:message text="Map works on GIS map" code="" />
											</button>
											<button class="btn btn-blue-2" type="button"
												onclick=" window.open('${command.gISUri}&id=${command.wmsDto.workcode}')"
												id="">
												<spring:message text="View works on GIS map" code="" />
											</button>
										 </c:if>
							</div>
				
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workCode" text=" Work Code" /></label>
					<div class="col-sm-4">
						<form:input path="wmsDto.workcode" cssClass="form-control"
							disabled="true" id="workcode" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" text="Work Name" /></label>
					<div class="col-sm-4">
						<form:input path="wmsDto.workName"
							cssClass="form-control mandColorClass" id="workName"
							data-rule-required="true" maxlength="250"
							disabled="${command.formMode eq 'V'}" />
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" text="Project Name" /> </label>
					<div class="col-sm-4">
						<form:select path="wmsDto.projId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="projId" data-rule-required="true"
							disabled="${command.formMode eq 'E' || command.formMode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.projectMasterList}" var="list">
								<form:option value="${list.projId}" code="${list.projCode}">${list.projNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<%-- <label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projcode" text="Project Code" /></label>
					<div class="col-sm-4">
						<form:input path="wmsDto.projCode" cssClass="form-control"
							id="projCode" readonly="true" data-rule-required="true" />
					</div> --%>

					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.def.exDept" text="Executing Department" /> </label>
					<div class="col-sm-4">
						<form:select path="wmsDto.deptId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="deptId" data-rule-required="true"
							disabled="${command.formMode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="departments">
								<form:option value="${departments.dpDeptid }"
									code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workType" text="Type of Work" /> </label>
					<c:set var="WRTlookUp" value="WRT" />
					<div class="col-sm-4">
						<form:select path="wmsDto.workType"
							class="form-control chosen-select-no-results" id="workType"
							disabled="${command.formMode eq 'V'}" onchange="getWorkType();">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.getLevelData(WRTlookUp)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="wms.CategoryWork" text="Category of Work" /> </label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="ACL" />
						<form:select path="wmsDto.workCategory"
							class="form-control chosen-select-no-results" id="workCategory"
							disabled="${command.formMode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<c:if test="${fn:contains(lookUp.otherField, 'IMO')}">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="" text="Subtype of Work" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="WRS" />
						<form:select path="wmsDto.workSubType"
							class="form-control chosen-select-no-results"
							id="workSubCategory" disabled="${command.formMode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">

								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>

							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label "><spring:message
							code="work.def.proposal.no" text=" Proposal No" /></label>
					<div class="col-sm-4">
						<form:select path="wmsDto.proposalNo"
							cssClass="form-control chosen-select-no-results" id="proposalNo"
							disabled="${command.formMode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.commonproposalList}"
								var="councilPropsal">
								<form:option value="${councilPropsal.proposalNo}"
									code="${councilPropsal.proposalDepId}">${councilPropsal.proposalDetails}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<!-- REMOVE AS PER SUDA UAT -->

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="work.def.startDate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="wmsDto.workStartDate" id="workStartDate"
								class="form-control mandColorClass datepicker" readonly="true"
								disabled="${command.formMode eq 'V'}" />
							<label class="input-group-addon" for="workStartDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=workStartDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="work.def.endDate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="wmsDto.workEndDate" id="workEndDate"
								class="form-control mandColorClass datepickerEndDate"
								readonly="true" disabled="${command.formMode eq 'V'}" />
							<label class="input-group-addon" for="workEndDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=workEndDate></label>
						</div>
					</div>
				</div>


				<div class="form-group">
					<label for="" class="col-sm-2 control-label"><spring:message
							code="work.def.projPhase" text="Project Phase" /> </label>
					<c:set var="PPHlookUp" value="PPH" />
					<apptags:lookupField items="${command.getLevelData(PPHlookUp)}"
						path="wmsDto.workProjPhase"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						disabled="${command.formMode eq 'V'}" />

					<label class="col-sm-2 control-label"><spring:message
							code="work.def.deviation.percentage" text="Deviation Percentage" /></label>
					<div class="col-sm-4">
						<form:input path="wmsDto.deviationPercent"
							cssClass="form-control text-right" id="deviationPercent"
							onkeypress="return hasAmount(event, this, 3, 2)"
							onchange="getAmountFormatInDynamic((this),'deviationPercent')" />
					</div>
				</div>
				<!-- CHANGES BY SUHEL -->



				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="work.def.loc.info" text="Work Location Information" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="" class="col-sm-2 control-label required-control"><spring:message
											code="work.def.loc.start" text="Start Location" /></label>
									<div class="col-sm-4">
										<form:select path="wmsDto.locIdSt"
											cssClass="form-control chosen-select-no-results mandColorClass"
											id="locIdSt" data-rule-required="true"
											disabled="${command.formMode eq 'V'}">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.locList}" var="lookUp">
												<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label for="" class="col-sm-2 control-label"><spring:message
											code="work.def.loc.end" text="End Location" /> </label>
									<div class="col-sm-4">
										<form:select path="wmsDto.locIdEn"
											cssClass="form-control chosen-select-no-results mandColorClass"
											id="locIdEn" data-rule-required="true"
											disabled="${command.formMode eq 'V'}">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.locList}" var="lookUp">
												<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<c:if test="${command.formMode ne 'V'}">
									<div class="col-sm-4">
										<a href="#" onclick="openLocationMas();"
											class="padding-top-5 link"><spring:message
												code='work.management.Clickhere' /></a>
										<spring:message code='wms.ToaddNewLocation' />
									</div>
								</c:if>
								<c:if test="${command.formMode ne 'C'}">
									<div class="col-sm-8">
										<a data-toggle="collapse" href="#collapseExample"
											class="btn btn-blue-2" id="viewloc" onclick="showMap()"><i
											class="fa fa-map-marker"></i> <spring:message
												code="disposal.site.master.view.location"
												text="View  Location" /></a>
									</div>
									<div class="col-sm-12">
										<div class="collapse margin-top-10" id="collapseExample">
											<div class="border-1 padding-5"
												style="height: 400px; width: 100%;" id="map-canvas"></div>
										</div>
									</div>

								</c:if>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="work.def.fin.info"
										text="Year-wise Financial Information" /></a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="financeDataDetails">
										<thead>
											<tr>
												<th width="18%"><spring:message
														code="work.def.fin.year" text="Financial Year" /></th>
												<th width="18%"><spring:message
														code="work.def.fin.code" text="Finance Code" /></th>
												<%-- <th width="18%"><spring:message
														code="work.def.percentage" text="Percentage" /></th> --%>
												<th width="18%"><spring:message code="work.def.docs.no"
														text="Approval No." /></th>
												<th width="18%"><spring:message code="work.def.budget"
														text="Budget" /></th>
												<th width="18%"><spring:message code="work.def.view.budget" /></th>
												<th width="10"></th>
											</tr>
										</thead>
										<c:choose>
											<c:when test="${fn:length(command.wmsDto.yearDtos)>0 }">
												<c:forEach items="${command.wmsDto.yearDtos}">
													<tr class="finacialInfoClass">
														<form:hidden path="wmsDto.yearDtos[${d}].yearId"
															id="yearId${d}" />
														<form:hidden path="wmsDto.yearDtos[${d}].finActiveFlag"
															id="finActiveFlag${d}" value="A" />
														<td><form:select
																path="wmsDto.yearDtos[${d}].faYearId"
																cssClass="form-control"
																onchange="resetFinanceCode(this,${d});"
																id="faYearId${d}" disabled="${command.formMode eq 'V'}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.faYears}" var="lookUp">
																	<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
																</c:forEach>
															</form:select></td>
														<c:choose>
															<c:when test="${command.cpdMode eq 'L'}">
																<td><form:select
																		path="wmsDto.yearDtos[${d}].sacHeadId"
																		cssClass="form-control chosen-select-no-results"
																		onchange="checkForDuplicateHeadCode(this,${d});"
																		id="sacHeadId${d}"
																		disabled="${command.formMode eq 'V'}">
																		<form:option value="">
																			<spring:message code='work.management.select' />
																		</form:option>
																		<c:forEach items="${command.budgetList}" var="lookUp">
																			<form:option value="${lookUp.sacHeadId}">${lookUp.acHeadCode}</form:option>
																		</c:forEach>
																	</form:select></td>
															</c:when>
															<c:otherwise>
																<td><form:input
																		path="wmsDto.yearDtos[${d}].financeCodeDesc"
																		cssClass="form-control"
																		onchange="checkForDuplicateFinanceCode(this,${d});"
																		id="financeCodeDesc${d}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
															</c:otherwise>
														</c:choose>
														<%-- <td><form:input
																path="wmsDto.yearDtos[${d}].yearPercntWork"
																cssClass="form-control text-right"
																id="yearPercntWork${d}"
																onkeypress="return hasAmount(event, this, 3, 2)"
																onchange="getAmountFormatInDynamic((this),'yearPercntWork')" /></td> --%>
														<td><form:input
																path="wmsDto.yearDtos[${d}].yeDocRefNo"
																cssClass="form-control" id="yeDocRefNo${d}"
																maxlength="50"
																onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
														<td><form:input
																path="wmsDto.yearDtos[${d}].yeBugAmount" 
																cssClass="form-control text-right " id="yeBugAmount${d}"
																onkeypress="return hasAmount(event, this, 13, 2)"
																onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
																onkeyup="getTotalAmount()" /></td>
														<td class="text-center">
															<button type="button" class="btn btn-primary btn-sm"
																onclick="return viewExpenditureDetails(${d});" 
																id="viewExpDet${d}" title="<spring:message code="work.def.view.budget" text="View Budget"></spring:message>">
																<i class="fa fa-eye" aria-hidden="true" ></i>
															</button>

														</td>
										
												<c:if test="${command.formMode ne 'V'}">
													<td align="center"><a href="javascript:void(0);" 
														class="btn btn-blue-2 btn-sm addFinanceDetails"><i
															class="fa fa-plus-circle" title="<spring:message code="works.management.add" text="Add"></spring:message>"></i></a> <a
														href="javascript:void(0);" 
														data-toggle="tooltip" data-placement="top"
														class="btn btn-danger btn-sm delButton"><i
															class="fa fa-minus" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"></i></a>
													</td>
												 </c:if>
														
														<c:set var="d" value="${d + 1}" scope="page" />
													</tr>
												</c:forEach>
												<%-- <tr>
													<td colspan="4" align="right">Total :</td>
													<td><form:input path=""
															cssClass="form-control text-right" id="totalAmount"
															disabled="true" /></td>
													<c:if test="${command.formMode ne 'V'}">
														<td></td>
													</c:if>
													<td></td>
												</tr> --%>
											</c:when>
											<c:otherwise>
												<tr class="finacialInfoClass">
													<form:hidden path="wmsDto.yearDtos[${d}].yearId"
														id="yearId${d}" />
													<form:hidden path="wmsDto.yearDtos[${d}].finActiveFlag"
														id="finActiveFlag${d}" value="A" />
													<td><form:select path="wmsDto.yearDtos[${d}].faYearId"
															cssClass="form-control "
															onchange="resetFinanceCode(this,${d});" id="faYearId${d}">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.faYears}" var="lookUp">
																<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
															</c:forEach>
														</form:select></td>
													<c:choose>
														<c:when test="${command.cpdMode eq 'L'}">
															<td><form:select
																	path="wmsDto.yearDtos[${d}].sacHeadId"
																	cssClass="form-control chosen-select-no-results"
																	onchange="checkForDuplicateHeadCode(this,${d});"
																	id="sacHeadId${d}">
																	<form:option value="">
																		<spring:message code='work.management.select' />
																	</form:option>
																	<c:forEach items="${command.budgetList}" var="lookUp">
																		<form:option value="${lookUp.sacHeadId}">${lookUp.acHeadCode}</form:option>
																	</c:forEach>
																</form:select></td>
														</c:when>
														<c:otherwise>
															<td><form:input
																	path="wmsDto.yearDtos[${d}].financeCodeDesc"
																	onchange="checkForDuplicateFinanceCode(this,${d});"
																	cssClass="form-control" id="financeCodeDesc${d}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
														</c:otherwise>
													</c:choose>
													<%-- <td><form:input
															path="wmsDto.yearDtos[${d}].yearPercntWork"
															cssClass="form-control text-right "
															id="yearPercntWork${d}"
															onkeypress="return hasAmount(event, this, 3, 2)"
															onchange="getAmountFormatInDynamic((this),'yearPercntWork')" /></td> --%>
													<td><form:input
															path="wmsDto.yearDtos[${d}].yeDocRefNo"
															cssClass="form-control" id="yeDocRefNo${d}"
															maxlength="50"
															onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
													<td><form:input
															path="wmsDto.yearDtos[${d}].yeBugAmount"
															cssClass="form-control Comp text-right "
															id="yeBugAmount${d}" 
															onkeypress="return hasAmount(event, this, 13, 2)"
															onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
															onkeyup="getTotalAmount()" /></td>
													<td class="text-center">
														<button type="button" class="btn btn-primary btn-sm"
															onclick="return viewExpenditureDetails(${d});"
															id="viewExpDet${d}" title="<spring:message code="work.def.view.budget" text="View Budget"></spring:message>">
															<i class="fa fa-eye" aria-hidden="true"></i>
														</button>

													</td>

													<c:if test="${command.formMode ne 'V'}">
													  <td align="center"><a href="javascript:void(0);" 
														class="btn btn-blue-2 btn-sm addFinanceDetails" title="<spring:message code="works.management.add" text="Add"></spring:message>"><i
															class="fa fa-plus-circle"></i></a> <a
														href="javascript:void(0);" 
														data-toggle="tooltip" data-placement="top"
														class="btn btn-danger btn-sm delButton" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
															class="fa fa-minus"></i></a>
													   </td>
													</c:if>
													
													<c:set var="d" value="${d + 1}" scope="page" />
												</tr>
												<%-- <tr>
													<td colspan="4" align="right">Total :</td>
													<td><form:input path=""
															cssClass="form-control text-right " id="totalAmount"
															disabled="true" /></td>
													<c:if test="${command.formMode ne 'V'}">
														<td></td>
													</c:if>
													<td></td>
												</tr> --%>
											</c:otherwise>
										</c:choose>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div id="sanctionDetData">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a4" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a4"><spring:message
											code="wms.SanctionDetails" text="Sanction Details" /></a>
								</h4>
							</div>
							<div id="a4" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="">
										<c:set var="d" value="0" scope="page"></c:set>
										<table class="table table-bordered table-striped"
											id="sanctionDetailsList">
											<thead>
												<tr>
													<th width="2%"><spring:message code="ser.no"
															text="Sr.No" /><input type="hidden" id="srNo"></th>
													<th width="9%"><spring:message
															code="work.estimate.approval.sanct.type"
															text="Sanction Type" /><span class="mand">*</span></th>
													<th width="23%"><spring:message
															code="work.estimate.approval.sanction.type"
															text="Sanction Type" /><span class="mand">*</span></th>
													<th width="16%"><spring:message
															code="work.estimate.approval.sanction.number"
															text="Sanction Number" /></th>
													<th width="15%"><spring:message
															code="work.estimate.approval.sanction.date"
															text="Sanction Date" /></th>
													<th width="20%"><spring:message
															code="work.estimate.approval.sanction.authrity.name"
															text="Sanction Authority" /></th>
													<th width="15%"><spring:message
															code="work.estimate.approval.sanction.auth.desg"
															text="Authority Designation" /></th>
													<c:if test="${command.formMode ne 'V'}">
														<th class="text-center" width="80"><button
																type="button" onclick="return false;"
																class="btn btn-blue-2 btn-sm  addSanctionDetails">
																<i class="fa fa-plus-circle"></i>
															</button></th>
													</c:if>
												</tr>
											</thead>
											<c:choose>
												<c:when
													test="${fn:length(command.wmsDto.sanctionDetails)>0}">
													<c:forEach items="${command.wmsDto.sanctionDetails}"
														varStatus="status">
														<tr class="SanctionDetailsClass">
															<td><form:input path="" id="sNo${d}" value="${d+1}"
																	readonly="true" cssClass="form-control" /> <form:hidden
																	path="wmsDto.sanctionDetails[${d}].workSancId"
																	id="workSancId${d}" /></td>
															<td><form:select
																	path="wmsDto.sanctionDetails[${d}].sancNature"
																	cssClass="form-control" id="sancNature${d}"
																	onchange="changeSanctionMode();">
																	<form:option value="">
																		<spring:message code='work.management.select' />
																	</form:option>
																	<form:option value="A">
																		<spring:message
																			code="work.def.automatic.auto.sanction" text="Auto" />
																	</form:option>
																	<form:option value="M">
																		<spring:message
																			code="work.def.automatic.manual.sanction"
																			text="Manual" />
																	</form:option>

																</form:select></td>
															<td><form:select
																	path="wmsDto.sanctionDetails[${d}].deptId"
																	id="deptId${d}" cssClass="form-control">
																	<form:option value="">
																		<spring:message code='work.management.select' />
																	</form:option>
																	<c:forEach items="${command.sanctionDeptsList}"
																		var="departments">
																		<form:option value="${departments.dpDeptid }"
																			code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
																	</c:forEach>
																</form:select></td>
															<td><form:input
																	path="wmsDto.sanctionDetails[${d}].workSancNo"
																	cssClass="form-control" id="workSancNo${d}" /></td>
															<td align="center"><form:input
																	path="wmsDto.sanctionDetails[${d}].workSancDate"
																	cssClass="form-control worksDate datepicker text-center"
																	placeholder="dd/mm/yyyy" maxlength="10"
																	id="workSancDate${d}" /></td>
															<td><form:input
																	path="wmsDto.sanctionDetails[${d}].workSancBy"
																	cssClass="form-control" id="workSancBy${d}" /></td>
															<td><form:input
																	path="wmsDto.sanctionDetails[${d}].workDesignBy"
																	cssClass="form-control" id="workDesignBy${d}" /></td>
															<c:if test="${command.formMode ne 'V'}">
																<td class="text-center"><a href='#'
																	onclick='return false;'
																	class='btn btn-danger btn-sm deleteSanctionDetails'>
																		<i class="fa fa-minus"></i>
																</a></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tbody>
														<tr class="SanctionDetailsClass">
															<td><form:input path="" id="sNo${d}" value="1"
																	readonly="true" cssClass="form-control" /></td>
															<td><form:select
																	path="wmsDto.sanctionDetails[${d}].sancNature"
																	cssClass="form-control" id="sancNature${d}"
																	onchange="changeSanctionMode();">
																	<form:option value="">
																		<spring:message code='work.management.select' />
																	</form:option>
																	<form:option value="A">
																		<spring:message
																			code="work.def.automatic.auto.sanction" text="Auto" />
																	</form:option>
																	<form:option value="M">
																		<spring:message
																			code="work.def.automatic.manual.sanction"
																			text="Manual" />
																	</form:option>

																</form:select></td>
															<td><form:select
																	path="wmsDto.sanctionDetails[${d}].deptId"
																	id="deptId${d}" cssClass="form-control">
																	<form:option value="">
																		<spring:message code='work.management.select' />
																	</form:option>
																	<c:forEach items="${command.sanctionDeptsList}"
																		var="departments">
																		<form:option value="${departments.dpDeptid }"
																			code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
																	</c:forEach>
																</form:select></td>
															<td><form:input
																	path="wmsDto.sanctionDetails[${d}].workSancNo"
																	cssClass="form-control" id="workSancNo${d}" /></td>
															<td align="center"><form:input
																	path="wmsDto.sanctionDetails[${d}].workSancDate"
																	cssClass="form-control worksDate datepicker text-center"
																	placeholder="dd/mm/yyyy" maxlength="10"
																	id="workSancDate${d}" /></td>
															<td><form:input
																	path="wmsDto.sanctionDetails[${d}].workSancBy"
																	cssClass="form-control" id="workSancBy${d}" /></td>
															<td><form:input
																	path="wmsDto.sanctionDetails[${d}].workDesignBy"
																	cssClass="form-control" id="workDesignBy${d}" /></td>
															<c:if test="${command.formMode ne 'V'}">
																<td class="text-center"><a href='#'
																	onclick='return false;'
																	class='btn btn-danger btn-sm deleteSanctionDetails'>
																		<i class="fa fa-minus"></i>
																</a></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</tbody>
												</c:otherwise>
											</c:choose>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="assetData">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a2"><spring:message
											code="work.def.asset.info" text="Asset Information" /></a>
								</h4>
							</div>
							<c:if test="${command.formMode ne 'V'}">
								<div id="searchAssetDialog">
									<div id="searchAssetChildDialog" class="add">
										<!-- <a onclick="searchAsset('#searchAssetChildDialog')" id="search"
										class="form-control">Asset Search</a> -->
									</div>
									<div>
										<button type="button" id="save" class="btn btn-success"
											onclick="assetSearchFun();">
											<spring:message code="wms.AssetSearch" text="Asset Search" />
										</button>


										<button type="button" id="save" class="btn btn-success"
											onclick="getWorkAssetDetails();">
											<spring:message code="wms.AddSelected.Items"
												text="Add Selected Items" />
										</button>

										<button type="button" id="save"
											class="button-input btn btn-danger" onclick="closeDiv();">
											<spring:message code="wms.close" text="Close" />
										</button>
									</div>
								</div>

							</c:if>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="table-responsive">
										<c:set var="a" value="0" scope="page"></c:set>
										<table class="table table-bordered table-striped" id="asset">
											<thead>
												<tr>
													<th width="18%"><spring:message
															code="work.def.asset.code" text="Asset Code" /></th>
													<th width="18%"><spring:message
															code="work.def.asset.name" text="Asset Name" /></th>
													<th width="18%"><spring:message
															code="work.def.asset.category" text="Asset Category" /></th>
													<th width="18%"><spring:message
															code="work.def.asset.dept" text="Asset Department" /></th>
													<th width="18%"><spring:message
															code="work.def.asset.loc" text="Asset Location" /></th>
													<%-- <th width="18%"><spring:message
														code="work.def.asset.status" text="Asset Status" /></th> --%>

													<th width="10"></th>
												</tr>
											</thead>
											<tbody>
												<c:choose>
													<c:when test="${fn:length(command.wmsDto.assetInfoDtos)>0}">
														<c:forEach items="${command.wmsDto.assetInfoDtos}"
															var="asset">
															<tr class="assetInf">
																<form:hidden
																	path="wmsDto.assetInfoDtos[${a}].workAssetId"
																	id="workAssetId${a}" />
																<form:hidden
																	path="wmsDto.assetInfoDtos[${a}].assetActiveFlag"
																	id="assetActiveFlag${a}" value="A" />
																<form:hidden path="wmsDto.assetInfoDtos[${a}].assetId"
																	id="assetId${a}" class="drid"/>														 
																<td><form:input
																		path="wmsDto.assetInfoDtos[${a}].assetCode"
																		cssClass="form-control" id="assetCode${a}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
																<td><form:input
																		path="wmsDto.assetInfoDtos[${a}].assetName"
																		cssClass="form-control" id="assetName${a}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
																<td><form:input
																		path="wmsDto.assetInfoDtos[${a}].assetCategory"
																		cssClass="form-control" id="assetCategory${a}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
																<td><form:input
																		path="wmsDto.assetInfoDtos[${a}].assetDepartment"
																		cssClass="form-control" id="assetDepartment${a}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
																<td><form:input
																		path="wmsDto.assetInfoDtos[${a}].assetLocation"
																		cssClass="form-control" id="assetLocation${a}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
																<%-- <td><form:input
																	path="wmsDto.assetInfoDtos[${a}].assetStatus"
																	cssClass="form-control" id="assetStatus${a}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td> --%>
																
															
																<c:if test="${command.formMode ne 'V'}">
																	<td align="center"><a href="javascript:void(0);" 
																	class="btn btn-blue-2 btn-sm addAsset"><i
																	class="fa fa-plus-circle" title="<spring:message code="works.management.add" text="Add"></spring:message>"></i></a> <a
																	href="javascript:void(0);" data-toggle="tooltip"
																	data-placement="top" 
																	class="btn btn-danger btn-sm delAsset" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																	class="fa fa-minus"></i></a></td>
																	
																	<c:set var="a" value="${a + 1}" scope="page" />	
																</c:if>
																
																
																
																
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr class="assetInf">
															<form:hidden
																path="wmsDto.assetInfoDtos[${a}].workAssetId"
																id="workAssetId${a}" />
															<form:hidden
																path="wmsDto.assetInfoDtos[${a}].assetActiveFlag"
																id="assetActiveFlag${a}" value="A" />
															<form:hidden path="wmsDto.assetInfoDtos[${a}].assetId"
																id="assetId${a}" class="drid"/>
															<td><form:input
																	path="wmsDto.assetInfoDtos[${a}].assetCode"
																	cssClass="form-control" id="assetCode${a}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
															<td><form:input
																	path="wmsDto.assetInfoDtos[${a}].assetName"
																	cssClass="form-control" id="assetName${a}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
															<td><form:input
																	path="wmsDto.assetInfoDtos[${a}].assetCategory"
																	cssClass="form-control" id="assetCategory${a}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
															<td><form:input
																	path="wmsDto.assetInfoDtos[${a}].assetDepartment"
																	cssClass="form-control" id="assetDepartment${a}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
															<td><form:input
																	path="wmsDto.assetInfoDtos[${a}].assetLocation"
																	cssClass="form-control" id="assetLocation${a}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
															<%-- <td><form:input
																path="wmsDto.assetInfoDtos[${a}].assetStatus"
																cssClass="form-control" id="assetStatus${a}"
																onkeyup="inputPreventSpace(event.keyCode,this);" /></td> --%>
															<c:if test="${command.formMode ne 'V'}">
																<td align="center"><a href="javascript:void(0);"
																	class="btn btn-blue-2 btn-sm addAsset" title="<spring:message code="works.management.add" text="Add"></spring:message>"><i
																	class="fa fa-plus-circle"></i></a> <a
																	href="javascript:void(0);" data-toggle="tooltip"
																	data-placement="top"
																	class="btn btn-danger btn-sm delAsset" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																	class="fa fa-minus"></i></a>
																</td>
																<c:set var="a" value="${a + 1}" scope="page" />
															</c:if>
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


					<!--Ward Zone table  start-->

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
											<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
												showOnlyLabel="false"
												pathPrefix="wmsDto.wardZoneDto[0].codId" isMandatory="true"
												hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true"
												cssClass="form-control required-control" showAll="false"
												disabled="${command.formMode eq 'V' ? true : false }"
												hasTableForm="true" showData="false" columnWidth="20%" />
												
											<th width="5%"></th>
										</tr>
									</thead>

									<tbody>
										<c:choose>
											<c:when test="${fn:length(command.wmsDto.wardZoneDto) > 0}">
												<c:forEach var="taxData"
													items="${command.wmsDto.wardZoneDto}" varStatus="status">
													<tr class="itemDetailClass">
														<form:hidden path="wmsDto.wardZoneDto[${d}].wardZoneId"
															id="wardZoneId${d}" />
														<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
															showOnlyLabel="false"
															pathPrefix="wmsDto.wardZoneDto[${d}].codId"
															isMandatory="true" hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true"
															disabled="${command.formMode eq 'V' ? true : false }"
															cssClass="form-control required-control" showAll="false"
															hasTableForm="true" showData="true" />

														<c:if test="${command.formMode ne 'V'}">
															<td align="center"><a href="javascript:void(0);" 
																id="addBtn"	class="btn btn-blue-2 btn-sm addItemCF" title="<spring:message code="works.management.add" text="Add"></spring:message>">
																<i class="fa fa-plus-circle"></i></a> <a
																href="javascript:void(0);" data-placement="top"
																class="btn btn-danger btn-sm delButton"
																id="deleteBtn" onclick="" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																class="fa fa-minus"></i></a>
															</td>
														</c:if>
														
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />


												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="itemDetailClass">

													<form:hidden path="wmsDto.wardZoneDto[${d}].wardZoneId"
														id="wardZoneId${d+1}" />
													<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
														showOnlyLabel="false"
														pathPrefix="wmsDto.wardZoneDto[${d}].codId"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														disabled="${command.formMode eq 'V' ? true : false }"
														cssClass="form-control required-control" showAll="false"
														hasTableForm="true" showData="true" />

													<td class="text-center"><a href="javascript:void(0);"
															id="addBtn"	class="btn btn-blue-2 btn-sm addItemCF" title="<spring:message code="works.management.add" text="Add"></spring:message>">
															<i class="fa fa-plus-circle"></i></a> <a
															href="javascript:void(0);" 
															class="btn btn-danger btn-sm delButton" onclick="" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
															class="fa fa-minus"></i></a>
													</td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<!--ward zone end  -->


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a5"> <spring:message
										code="work.def.docs" text="Work Documents" /></a>
							</h4>
						</div>
						<div id="a5" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:if
									test="${(command.formMode eq 'E' || command.formMode eq 'V' )&& fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDoc">
											<tr>
												<th width="64%"><spring:message
														code="work.estimate.document.description" /></th>
												<th width="30%"><spring:message
														code="scheme.view.document" text="" /></th>
												<c:if test="${command.formMode ne 'V'}">
													<th width="8%"><spring:message
															code="works.management.action" text="" /></th>
												</c:if>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="WmsWorkDefinationMaster.html?Download" /></td>
													<c:if test="${command.formMode ne 'V'}">
														<td class="text-center"><a href='#' id="deleteFile"
															onclick="return false;" class="btn btn-danger btn-sm">
																<i class="fa fa-trash"></i>
														</a> <form:hidden path="" value="${lookUp.attId}" /></td>
													</c:if>
												</tr>
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>

								<c:if test="${command.formMode ne 'V'}">
									<div id="uploadTagDiv">
										<div class="table-responsive">
											<c:set var="d" value="0" scope="page"></c:set>
											<table class="table table-bordered table-striped"
												id="attachDoc">
												<tr>
													<th><spring:message code="work.management.description"
															text="Document Description" /></th>
													<th><spring:message code="scheme.master.upload"
															text="Upload" /></th>
													<th scope="col" width="8%"></th>		
												</tr>
												<tr class="appendableClass">
													<td><form:input path="attachments[${d}].doc_DESC_ENGL"
															class=" form-control"
															onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
													<td class="text-center" title="Upload"><apptags:formField
															fieldType="7" 
															fieldPath="attachments[${d}].uploadedDocumentPath"
															currentCount="${d}" showFileNameHTMLId="true"
															folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
															isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
														</apptags:formField>
														<small class="text-blue-2" style="font-size: unset;"> <spring:message code="wms.doc.validatation"
															      text="(Upload Image File upto 5 MB)" />
												        </small>
														</td>
												
													<td align="center"><a href="javascript:void(0);"
													onclick='documentUpload(this);'
														class="btn btn-blue-2 btn-sm addButton" title="<spring:message code="works.management.add" text="Add"></spring:message>"><i
															class="fa fa-plus-circle" ></i></a> <a
														href="#" id="0_file_${d}" onclick="doFileDelete(this)"
														class="btn btn-danger btn-sm delBtn" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
															class="fa fa-trash"></i></a></td>
												
													
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</table>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.formMode eq 'E' || command.formMode eq 'C'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveWorkDef(this);" title="<spring:message code="works.management.save" text="Save"></spring:message>">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.formMode eq 'C'}">
						<button class="btn btn-warning" type="button" id="resetWorkDef" title="<spring:message code="works.management.reset" text="Reset"></spring:message>">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='WmsWorkDefinationMaster.html'"
						id="button-Cancel" title="<spring:message code="works.management.back" text="Back"></spring:message>">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>
