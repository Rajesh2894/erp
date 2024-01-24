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
<script type="text/javascript" src="js/sfac/equityGrantRequestForm.js"></script>
<script src="js/mainet/file-upload.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.quity.grant.title"
					text="Application Form for Seeking Equity Grant by FPO" />
			</h2>

		</div>

		<div class="widget-content padding">
			<form:form id="EquityGrantRequest" action="EquityGrantApproval.html"
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
									data-parent="#accordion_single_collapse" href="#fpcDetails">
									<spring:message code="sfac.fpc.details" text="FPC Details" />
								</a>
							</h4>
						</div>
						<div id="fpcDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="State"
										class="col-sm-2 control-label required-control"><spring:message
											code="sfac.name.of.the.fpc" text="Name of the FPO" /></label>
									<div class="col-sm-4">
										<form:input path="dto.fpoMasterDto.fpoName" id="fpoName$"
											class="form-control " disabled="true" />
										<form:hidden path="dto.fpoMasterDto.fpoId" id="fpoId" />
									</div>
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.correspondence.address"
											text="Correspondence Address" />
									</label>
									<div class="col-sm-4">
										<form:textarea path="dto.fpoMasterDto.fpoOffAddr" id=""
											class="form-control" maxlength="" disabled="true" />
									</div>

								</div>

								<div class="form-group">

									<apptags:input labelCode="sfac.contact.no" cssClass=""
										path="dto.contactNo" maxlegnth="10" isDisabled="true"></apptags:input>
									<apptags:input labelCode="sfac.registration.no" cssClass=""
										path="dto.fpoMasterDto.fpoRegNo" isMandatory="true"
										isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label" for=""> <spring:message
											code="sfac.registration.date.incorporation.of.fpc"
											text="Registration Date/ Incorporation of FPO" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.fpoMasterDto.dateIncorporation"
												type="text" class="form-control datepicker" disabled="true"
												id="regIncorpFpc" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
									<apptags:input labelCode="sfac.business.of.fpc" cssClass=""
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										maxlegnth="500" path="dto.businessofFPC" isMandatory="true"></apptags:input>
								</div>
								<div class="form-group">

									<apptags:input labelCode="sfac.no.of.shareholder.members"
										cssClass="hasNumber" path="dto.fpoMasterDto.noShareMem"
										isMandatory="true" isDisabled="true"></apptags:input>
									<apptags:input
										labelCode="sfac.no.of.small.marginal.landless.shareholder.members"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="hasNumber" path="dto.noOfSMLShareholderMemb"
										maxlegnth="2" isMandatory="true"></apptags:input>
								</div>
								<%-- <div class="form-group">
									
									<apptags:input labelCode="sfac.authorised.capital.in.inr"
										cssClass="" path="dto.fpoMasterDto.authorizeCapital"
										isMandatory="true" isDisabled="true"></apptags:input>
								</div> --%>
								<div class="form-group">
									<apptags:input labelCode="sfac.paid.up.capital.in.inr"
										cssClass="" path="dto.fpoMasterDto.paidupCapital"
										isMandatory="true" isDisabled="true"></apptags:input>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.amount.of.equity.grant.sought.in.inr"
											text="Amount of Equity Grant Sought (in INR)" /></label>
									<div class="col-sm-4">
										<form:input cssClass="form-control"
											path="dto.amountofEquityGrant"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
								<div class="form-group">
									<apptags:input
										labelCode="sfac.maximum.shareholding.of.an.individual.shareholder.member"
										cssClass="hasNumber" path="dto.maxIndShareholdMem"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#bankDetails">
									<spring:message code="sfac.bank.details" text="Bank Details" />
								</a>
							</h4>
						</div>
						<div id="bankDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.bank.name.in.which.account.is.maintained"
											text="Bank Name" /></label>
									<div class="col-sm-4">
										<form:select path="dto.bankId" onchange="getBankDetails();"
											disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control chosen-select-no-results" id="bankId">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach
												items="${command.dto.fpoMasterDto.fpoBankDetailDto}"
												var="lookUp">
												<form:option code="${lookUp.bkId}" value="${lookUp.bkId}">${lookUp.bankName} -- ${lookUp.ifscCode} -- ${lookUp.accountNo}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.equity.grant.branch.name" text="Branch Name" /></label>
									<div class="col-sm-4">
										<form:select path="" disabled="true" class="form-control"
											id="branchId">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach
												items="${command.dto.fpoMasterDto.fpoBankDetailDto}"
												var="lookUp">
												<form:option code="${lookUp.bkId}" value="${lookUp.bkId}">${lookUp.branchName} </form:option>
											</c:forEach>
										</form:select>
									</div>


								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.equity.grant.account.no" text="Account No." /></label>
									<div class="col-sm-4">
										<form:select path="" disabled="true" class="form-control"
											id="accNo">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach
												items="${command.dto.fpoMasterDto.fpoBankDetailDto}"
												var="lookUp">
												<form:option code="${lookUp.bkId}" value="${lookUp.bkId}">${lookUp.accountNo} </form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.equity.grant.ifsc.code" text="IFSC" /></label>
									<div class="col-sm-4">
										<form:select path="" disabled="true" class="form-control"
											id="ifsc">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach
												items="${command.dto.fpoMasterDto.fpoBankDetailDto}"
												var="lookUp">
												<form:option code="${lookUp.bkId}" value="${lookUp.bkId}">${lookUp.ifscCode} </form:option>
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
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#adminDetails">
									<spring:message code="sfac.fpo.administrativeDetails"
										text="Administrative Details" />
								</a>
							</h4>
						</div>
						<div id="adminDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.mode.of.board.formation"
											text="Mode of Board formation(election/ nomination)" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="MBF" />
										<form:select path="dto.modeOfBoardFormation"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="modeOfBoardFormation">
											<form:option value="0">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<apptags:input labelCode="sfac.no.of.directors"
										cssClass="hasNumber" maxlegnth="2" path="dto.noOfDirectors"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control" for="">
										<spring:message
											code="sfac.dates.of.board.meetings.in.the.last.year"
											text="Dates of Board Meetings held in the last year" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.lastYrBoardMeetDt" type="text"
												disabled="${command.viewMode eq 'V' ? true : false }"
												class="form-control datepicker mandColorClass"
												id="dateBoardMeetingsLastYear" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
									<apptags:input labelCode="sfac.no.of.women.directors"
										cssClass="hasNumber" maxlegnth="2" path="dto.womenDirectors"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#fcdDetails">
									<spring:message code="sfac.functional.committees.details"
										text="Functional Committee Details" />
								</a>
							</h4>
						</div>
						<div id="fcdDetails" class="panel-collapse collapse">
							<div class="panel-body">


								<c:set var="d" value="0" scope="page"></c:set>
								<table class="table table-bordered table-striped"
									id="detailsfcdTable">
									<thead>
										<tr>
											<th><spring:message code="sfac.sr.no" text="Sr. No." /></th>
											<th><spring:message
													code="sfac.name.of.functional.committees.of.the.fpc"
													text="Name of Functional Committees of the FPO" /></th>
											<th><spring:message code="sfac.major.activities"
													text="Major Activities" /></th>

											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.equityGrantFunctionalCommitteeDetailDtos)>0 }">
												<c:forEach var="dto"
													items="${command.dto.equityGrantFunctionalCommitteeDetailDtos}"
													varStatus="status">
													<tr>

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoFCD${d}"
																value="${d+1}" disabled="true" /></td>
														<td><form:input
																path="dto.equityGrantFunctionalCommitteeDetailDtos[${d}].fcName"
																id="fcName${d}" maxlength="200" cssClass="form-control "
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.equityGrantFunctionalCommitteeDetailDtos[${d}].majorActivities"
																id="majorActivities${d}" cssClass="form-control "
																maxlength="500"
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>

														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addFCDButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addFCDButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteFCDDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteFCDDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>

													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNoFCD${d}"
															value="${d+1}" disabled="true" /></td>
													<td><form:input
															path="dto.equityGrantFunctionalCommitteeDetailDtos[${d}].fcName"
															id="fcName${d}" maxlength="200" cssClass="form-control "
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td><form:input
															path="dto.equityGrantFunctionalCommitteeDetailDtos[${d}].majorActivities"
															id="majorActivities${d}" cssClass="form-control "
															maxlength="500"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>

													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addFCDButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addFCDButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteFCDDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteFCDDetails(this);"> <i
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
									data-parent="#accordion_single_collapse" href="#bodDetails">
									<spring:message code="sfac.details.of.board.of.directors"
										text="Details of Board of Directors" />
								</a>
							</h4>
						</div>
						<div id="bodDetails" class="panel-collapse collapse">
							<div class="panel-body">


								<c:set var="d" value="0" scope="page"></c:set>
								<table class="table table-bordered table-striped"
									id="detailsBoardOfDirectorsTable">
									<thead>
										<tr>
											<th><spring:message code="sfac.sr.no" text="Sr. No." /></th>
											<th><spring:message code="sfac.name.of.bod.members"
													text="Name of BoD Members" /></th>
											<th><spring:message code="sfac.designation.role.in.fpc"
													text="Designation/Role in FPC" /></th>
											<th><spring:message code="sfac.fpo.aadharNo"
													text="Aadhaar No." /></th>
											<th><spring:message code="sfac.din.no" text="DIN No." /></th>
											<th><spring:message code="sfac.qualification"
													text="Qualification" /></th>
											<th><spring:message code="sfac.tenure.in.yrs"
													text="Tenure(in Yrs.)" /></th>
											<th><spring:message code="sfac.contact.no.address"
													text="Contact No./Address" /></th>
											<th><spring:message code="sfac.land.holding.in.acres"
													text="Land Holding (in Acres)" /></th>
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.equityGrantDetailDto)>0 }">
												<c:forEach var="dto"
													items="${command.dto.equityGrantDetailDto}"
													varStatus="status">
													<tr>

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
														<td><form:input
																path="dto.equityGrantDetailDto[${d}].name" id="name${d}"
																cssClass="form-control " disabled="true" /></td>
														<td>

															<div>
																<form:select id="role${d}" disabled="true"
																	path="dto.equityGrantDetailDto[${d}].role"
																	cssClass="form-control">
																	<form:option value="">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach items="${command.designlist}" var="desig">
																		<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
														<td><form:input
																path="dto.equityGrantDetailDto[${d}].aadhaarNo"
																maxlength="12" id="aadhaarNo${d}"
																cssClass="form-control hasNumber "
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.equityGrantDetailDto[${d}].dinNo"
																id="dinNo${d}" cssClass="form-control hasNumber "
																maxlength="8"
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.equityGrantDetailDto[${d}].qualification"
																id="qualification${d}" cssClass="form-control "
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.equityGrantDetailDto[${d}].tenure"
																id="tenure${d}" cssClass="form-control "
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.equityGrantDetailDto[${d}].contactNoAddress"
																id="contactNoAddress${d}" cssClass="form-control "
																disabled="true" /></td>
														<td><form:input
																path="dto.equityGrantDetailDto[${d}].landHolding"
																id="landHolding${d}" cssClass="form-control hasNumber"
																maxlength="5"
																disabled="${command.viewMode eq 'V' ? true : false }" />
														</td>
														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addBODButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addBODButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteBODDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteBODDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>

													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>
													<td><form:input
															path="dto.equityGrantDetailDto[${d}].name" id="name${d}"
															cssClass="form-control " disabled="true" /></td>
													<td>

														<div>
															<form:select id="role${d}" disabled="true"
																path="dto.equityGrantDetailDto[${d}].role"
																cssClass="form-control">
																<form:option value="">
																	<spring:message code="sfac.select" text="Select" />
																</form:option>
																<c:forEach items="${command.designlist}" var="desig">
																	<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																</c:forEach>
															</form:select>
														</div>
													</td>
													<td><form:input
															path="dto.equityGrantDetailDto[${d}].aadhaarNo"
															id="aadhaarNo${d}" cssClass="form-control hasNumber "
															maxlength="12"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td><form:input
															path="dto.equityGrantDetailDto[${d}].dinNo"
															id="dinNo${d}" cssClass="form-control hasNumber "
															maxlength="8"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td><form:input
															path="dto.equityGrantDetailDto[${d}].qualification"
															id="qualification${d}" cssClass="form-control "
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td><form:input
															path="dto.equityGrantDetailDto[${d}].tenure"
															id="tenure${d}" cssClass="form-control "
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td><form:input
															path="dto.equityGrantDetailDto[${d}].contactNoAddress"
															id="contactNoAddress${d}" cssClass="form-control "
															disabled="true" /></td>
													<td><form:input
															path="dto.equityGrantDetailDto[${d}].landHolding"
															id="landHolding${d}" cssClass="form-control hasNumber"
															maxlength="5"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addBODButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addBODButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteBODDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteBODDetails(this);"> <i
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
									data-parent="#accordion_single_collapse" href="#bomDetails">
									<spring:message code="sfac.details.of.board.of.member"
										text="Details of  Member of Board / Governing Body" />
								</a>
							</h4>
						</div>
						<div id="bomDetails" class="panel-collapse collapse">
							<div class="panel-body">


								<c:set var="d" value="0" scope="page"></c:set>
								<table class="table table-bordered table-striped"
									id="detailsBoardOfMemberTable">
									<thead>
										<tr>
											<th><spring:message code="sfac.sr.no" text="Sr. No." /></th>
											<th><spring:message code="sfac.name.of.bod.members"
													text="Name of BoD Members" /></th>
											<th><spring:message code="sfac.designation.role.in.fpc"
													text="Designation/Role in FPC" /></th>
											<th><spring:message code="sfac.fpo.aadharNo"
													text="Aadhaar No." /></th>

											<th><spring:message code="sfac.qualification"
													text="Qualification" /></th>
											<th><spring:message code="sfac.tenure.in.yrs"
													text="Tenure(in Yrs.)" /></th>
											<th><spring:message code="sfac.contact.no.address"
													text="Contact No./Address" /></th>
											<th><spring:message code="sfac.land.holding.in.acres"
													text="Land Holding (in Acres)" /></th>
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.equityGrantDetailDtoBOM)>0 }">
												<c:forEach var="dto"
													items="${command.dto.equityGrantDetailDtoBOM}"
													varStatus="status">
													<tr>

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoBOM${d}"
																value="${d+1}" disabled="true" /></td>
														<td><form:input
																path="dto.equityGrantDetailDtoBOM[${d}].name"
																id="nameBOM${d}" cssClass="form-control "
																disabled="true" /></td>
														<td>

															<div>
																<form:select id="roleBOM${d}" disabled="true"
																	path="dto.equityGrantDetailDtoBOM[${d}].role"
																	cssClass="form-control">
																	<form:option value="">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach items="${command.designlist}" var="desig">
																		<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
														<td><form:input
																path="dto.equityGrantDetailDtoBOM[${d}].aadhaarNo"
																id="aadhaarNoBOM${d}" cssClass="form-control hasNumber "
																maxlength="12"
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="dto.equityGrantDetailDtoBOM[${d}].qualification"
																id="qualificationBOM${d}" cssClass="form-control "
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.equityGrantDetailDtoBOM[${d}].tenure"
																id="tenureBOM${d}" cssClass="form-control "
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.equityGrantDetailDtoBOM[${d}].contactNoAddress"
																id="contactNoAddressBOM${d}" cssClass="form-control "
																disabled="true" /></td>
														<td><form:input
																path="dto.equityGrantDetailDtoBOM[${d}].landHolding"
																id="landHoldingBOM${d}"
																cssClass="form-control hasNumber" maxlength="5"
																disabled="${command.viewMode eq 'V' ? true : false }" />
														</td>
														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addBOMButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addBOMButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteBOMDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteBOMDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>

													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNoBOM${d}"
															value="${d+1}" disabled="true" /></td>
													<td><form:input
															path="dto.equityGrantDetailDtoBOM[${d}].name"
															id="nameBOM${d}" cssClass="form-control " disabled="true" /></td>
													<td>

														<div>
															<form:select id="roleBOM${d}" disabled="true"
																path="dto.equityGrantDetailDtoBOM[${d}].role"
																cssClass="form-control">
																<form:option value="">
																	<spring:message code="sfac.select" text="Select" />
																</form:option>
																<c:forEach items="${command.designlist}" var="desig">
																	<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																</c:forEach>
															</form:select>
														</div>
													</td>
													<td><form:input
															path="dto.equityGrantDetailDtoBOM[${d}].aadhaarNo"
															id="aadhaarNoBOM${d}" cssClass="form-control hasNumber "
															maxlength="12"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>

													<td><form:input
															path="dto.equityGrantDetailDtoBOM[${d}].qualification"
															id="qualificationBOM${d}" cssClass="form-control "
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td><form:input
															path="dto.equityGrantDetailDtoBOM[${d}].tenure"
															id="tenureBOM${d}" cssClass="form-control "
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td><form:input
															path="dto.equityGrantDetailDtoBOM[${d}].contactNoAddress"
															id="contactNoAddressBOM${d}" cssClass="form-control "
															disabled="true" /></td>
													<td><form:input
															path="dto.equityGrantDetailDtoBOM[${d}].landHolding"
															id="landHoldingBOM${d}" cssClass="form-control hasNumber"
															maxlength="5"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addBOMButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addBOMButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteBOMDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteBOMDetails(this);"> <i
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
									data-parent="#accordion_single_collapse" href="#dosDetails">
									<spring:message code="sfac.details.of.share.holding"
										text="Details of  Shareholdings of FPO  Members" />
								</a>
							</h4>
						</div>
						<div id="dosDetails" class="panel-collapse collapse">
							<div class="panel-body">


								<c:set var="d" value="0" scope="page"></c:set>
								<table class="table table-bordered table-striped"
									id="detailsOfShareholdTable">
									<thead>
										<tr>
											<th><spring:message code="sfac.sr.no" text="Sr. No." /></th>
											<th><spring:message code="sfac.fpo.noShareHolder"
													text="No Of Shareholders" /></th>
											<th><spring:message code="sfac.no.and.face.value"
													text="No. and Face Value (INR) of Shares allotted" /></th>
											<th><spring:message code="sfac.total.amount.paid"
													text="Total amount paid (including premium in INR)" /></th>


											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.equityGrantShareHoldingDetailDtos)>0 }">
												<c:forEach var="dto"
													items="${command.dto.equityGrantShareHoldingDetailDtos}"
													varStatus="status">
													<tr>

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoSH${d}"
																value="${d+1}" disabled="true" /></td>
														<td><form:input
																path="dto.equityGrantShareHoldingDetailDtos[${d}].noOfShareHolder"
																id="noOfShareHolder${d}"
																cssClass="form-control hasNumber"
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="dto.equityGrantShareHoldingDetailDtos[${d}].faceValueShareAllotted"
																id="faceValueShareAllotted${d}" class="form-control  "
																onkeypress="return hasAmount(event, this, 10, 2)"
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="dto.equityGrantShareHoldingDetailDtos[${d}].totalAmtPaid"
																id="totalAmtPaid${d}" cssClass="form-control "
																onkeypress="return hasAmount(event, this, 10, 2)"
																disabled="${command.viewMode eq 'V' ? true : false }" /></td>

														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addSHButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addSHButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteSHDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteSHDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>

													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNoSH${d}"
															value="${d+1}" disabled="true" /></td>
													<td><form:input
															path="dto.equityGrantShareHoldingDetailDtos[${d}].noOfShareHolder"
															id="noOfShareHolder${d}"
															cssClass="form-control hasNumber"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>

													<td><form:input
															path="dto.equityGrantShareHoldingDetailDtos[${d}].faceValueShareAllotted"
															id="faceValueShareAllotted${d}" cssClass="form-control  "
															onkeypress="return hasAmount(event, this, 10, 2)"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>

													<td><form:input
															path="dto.equityGrantShareHoldingDetailDtos[${d}].totalAmtPaid"
															id="totalAmtPaid${d}" cssClass="form-control"
															onkeypress="return hasAmount(event, this, 10, 2)"
															disabled="${command.viewMode eq 'V' ? true : false }" /></td>
													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addSHButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addSHButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteSHDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteSHDetails(this);"> <i
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


					<div class="">

						<apptags:CheckerAction hideForward="true" hideSendback="true"
							hideUpload="true"></apptags:CheckerAction>

					</div>

					<div class="text-center padding-top-10">
						<button type="button" align="center" class="btn btn-green-3"
							data-toggle="tooltip" data-original-title="Submit"
							onclick="saveEquityGrantApprovalData(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>

						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>



