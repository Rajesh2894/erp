<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/council/councilCommitteeMaster.js"></script>

<script src="assets/libs/fullcalendar/moment.min.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.add.committee.title"
					text="Member Commitee Mapping" />
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
			<apptags:helpDoc url="CouncilMemberCommitteeMaster.html" />
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="coucil.fiels.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="CouncilMemberCommitteeMaster.html"
				cssClass="form-horizontal" id="memberCommitteeMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removedIds" id="removedIds" />

				<form:hidden
					path="councilMemberCommitteeMasterDto.memberCommmitteeId" />

				<c:set var="d" value="0"></c:set>
				<div class="panel-group accordion-toggle">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="" href="#child-level"><spring:message
								code="council.add.committee.member.title" text="Add Committee Member" /></a>
					</h4>

					<div id="child-level" class="collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="Year"><spring:message code="council.committeeType"
										text="Committee Type"></spring:message></label>
								<%-- <c:set var="baseLookupCode" value="CPT" /> --%>
								<%-- <apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="councilMemberCommitteeMasterDto.committeeTypeId"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									selectOptionLabelCode="applicantinfo.label.select"
									showOnlyLabel="Committee Type" isMandatory="true"
									disabled="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}" /> --%>
								<div class="col-sm-4">
									<form:select
										path="councilMemberCommitteeMasterDto.committeeTypeId"
										cssClass="form-control" id="committeeTypeId"
										onchange="getDetails();"
										disabled="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}">
										<form:option value="">
											<spring:message code='council.dropdown.select' />
										</form:option>

										<c:forEach items="${command.getLevelData('CPT')}" var="look">
											<form:option value="${look.lookUpId}"
												code="${look.otherField}">${look.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div>

								<apptags:input labelCode="council.commitee.memberCount"
									path="councilMemberCommitteeMasterDto.otherField"
									isDisabled="true"></apptags:input>


							</div>
							<div class="form-group">
								<apptags:date fieldclass="datepicker"
									datePath="councilMemberCommitteeMasterDto.fromDate"
									labelCode="council.from.date" cssClass="mandColorClass"
									isMandatory="true" isDisabled="${command.saveMode eq 'VIEW'}"></apptags:date>

								<apptags:date fieldclass="datepicker"
									datePath="councilMemberCommitteeMasterDto.toDate"
									labelCode="council.to.date" cssClass="mandColorClass"
									isMandatory="true" isDisabled="${command.saveMode eq 'VIEW'}"></apptags:date>
							</div>
							<div class="form-group">
								<apptags:date fieldclass="datepicker"
									datePath="councilMemberCommitteeMasterDto.dissolveDate"
									labelCode="council.add.committee.dissolveDate" cssClass="mandColorClass"
									isMandatory="true" isDisabled="${command.saveMode eq 'VIEW'}"></apptags:date>
							</div>
							

							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="MemberData">
									<thead>
										<tr>
											<th scope="col" width="3%"><spring:message
													code="council.srno.comitee.mapping" text="Sr.No." /></th>
											<th scope="col"><spring:message
													code="council.commitee.memberName" text="Member Name" /><span
												class="mand">*</span></th>
											<th scope="col"><spring:message
													code="council.member.type" text="Member Type" /></th>
											<th scope="col"><spring:message
													code="council.member.electionward" text="Election Ward" /></th>
											<%-- <th scope="col" width="10%"><spring:message
													code="council.member.partyaffiliation"
													text="Party Affiliation" /></th> --%>
											<th scope="col"><spring:message
													code="council.member.committee.designation"
													text="Committee Designation" /><span class="mand">*</span></th>
											<c:if
												test="${command.saveMode eq 'EDIT' || command.saveMode eq 'VIEW'}">
												<th scope="col"><spring:message
														code="council.committee.member.expiry.date"
														text="Expiry Date" /></th>

												<th scope="col"><spring:message
														code="council.committee.member.expiry.reason"
														text="Expiry Reason" /></th>
											</c:if>
											<c:if
												test="${command.saveMode ne 'VIEW'}">
											<th scope="col" width="10%"><spring:message
														code="council.member.action" text="Action" /></th>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.councilMemberCommitteeMasterDtoList)>0 && command.saveMode eq 'EDIT' || command.saveMode eq 'VIEW'}">
												<c:forEach var="committeeData"
													items="${command.councilMemberCommitteeMasterDtoList}">
													<tr class="appendableClass">
														<form:hidden
															path="councilMemberCommitteeMasterDtoList[${d}].memberCommmitteeId"
															id="memberCommmitteeId${d}" />
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequence${d}" value="${d+1}" disabled="true" /></td>
														<td align="center"><form:select
																path="councilMemberCommitteeMasterDtoList[${d}].memberId"
																cssClass="form-control mandColorClass" id="couMemId${d}"
																onchange="setMemberData(this.id)"
																disabled="${command.saveMode eq 'VIEW' }">
																<form:option value="">
																	<spring:message code='council.dropdown.select' />
																</form:option>

																<c:forEach items="${command.couMemMasterDtoList}"
																	var="couMem">
																	<form:option value="${couMem.couId}"
																		code="${couMem.couId}">${couMem.couMemName}</form:option>
																</c:forEach>
															</form:select> <%-- <form:hidden path="councilMemberCommitteeMasterDtoList[${d}].memberId" id="deleteMemId${d}" /></td> --%>
															</td>
														<td align="center"><form:input
																path="councilMemberCommitteeMasterDtoList[${d}].couMemberTypeDesc"
																cssClass="form-control mandColorClass"
																id="couMemberTypeDesc${d}" disabled="true" /></td>
														<td align="center"><form:input
																path="councilMemberCommitteeMasterDtoList[${d}].elecWardDesc"
																cssClass="form-control mandColorClass"
																id="elecWardDesc${d}" disabled="true" /></td>
														<%-- <td align="center"><form:input
																path="councilMemberCommitteeMasterDtoList[${d}].partyAFFDesc"
																cssClass="form-control mandColorClass"
																id="partyAFFDesc${d}" disabled="true" /></td> --%>
														<td align="center">
															<%-- 		<c:set var="baseLookupCode"
																value="CDS" /> <apptags:lookupField
																items="${command.getLevelData(baseLookupCode)}"
																path="councilMemberCommitteeMasterDtoList[${d}].comDsgId" cssClass="form-control"
																hasChildLookup="false" hasId="true"
																selectOptionLabelCode="applicantinfo.label.select"
																isMandatory="true"
																showOnlyLabel="council.member.committee.designation"
																disabled="${command.saveMode eq 'V'}" /> --%> <form:select
																path="councilMemberCommitteeMasterDtoList[${d}].comDsgId"
																cssClass="form-control" id="comDsgId${d}"
																disabled="${command.saveMode eq 'VIEW'}">
																<form:option value="">
																	<spring:message code='council.dropdown.select' />
																</form:option>

																<c:forEach items="${command.getLevelData('CDS')}"
																	var="look">
																	<form:option value="${look.lookUpId}"
																		code="${look.otherField}">${look.descLangFirst}</form:option>
																</c:forEach>
															</form:select>

														</td>

														<td>
															<div class="input-group">
																<form:input
																	path="councilMemberCommitteeMasterDtoList[${d}].expiryDate"
																	id="expiryDate${d}"
																	class="form-control mandColorClass datepicker dateValidation "
																	disabled="${command.saveMode eq 'VIEW'}"
																	data-rule-required="true" maxLength="10" />
																<label class="input-group-addon" for="expiryDate"><i
																	class="fa fa-calendar"></i><span class="hide"> <spring:message
																			code="" text="icon" /></span><input type="hidden"
																	id=expiryDate></label>
															</div>
														</td>

														<td><form:textarea
																path="councilMemberCommitteeMasterDtoList[${d}].expiryReason"
																id="expiryReason${d}"
																class="form-control preventSpace alfaNumricSpecial" maxlength="1000"
																disabled="${command.saveMode eq 'VIEW'}" /></td>

														<c:if
															test="${command.saveMode eq 'ADD' }">
															<td align="center"><a data-toggle="tooltip"
																data-placement="top"
																class="btn btn-danger btn-sm delButton"
																onclick="deleteEntry($(this),'removedIds');"><i
																	class="fa fa-minus"></i></a></td>
														</c:if>
														<c:if
															test="${command.saveMode eq 'EDIT' }">
															<td class="text-center"><a 
															    href="javascript:void(0);" data-toggle="tooltip"
															    data-placement="top" onclick="addEntryData()"
														        class="addCF btn btn-success btn-sm"><i
																class="fa fa-plus-circle"></i></a> <a data-toggle="tooltip"
																data-placement="top"
																class="btn btn-danger btn-sm delButton"
																onclick="deleteEntry($(this),'removedIds');"><i
																	class="fa fa-minus"></i></a>
															</td>
														</c:if>
														<c:set var="d" value="${d + 1}" />
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableClass">
													<form:hidden
														path="councilMemberCommitteeMasterDtoList[${d}].memberCommmitteeId"
														id="memberCommmitteeId${d}" />
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence${d}"
															value="${d+1}" disabled="true" /></td>
													<td align="center"><form:select
															path="councilMemberCommitteeMasterDtoList[${d}].memberId"
															cssClass="form-control mandColorClass" id="couMemId${d}"
															onchange="setMemberData(this.id)"
															disabled="${command.saveMode eq 'VIEW' }">
															<form:option value="">
																<spring:message code='council.dropdown.select' />
															</form:option>

															<c:forEach items="${command.couMemMasterDtoList}"
																var="couMem">
																<form:option value="${couMem.couId}"
																	code="${couMem.couId}">${couMem.couMemName}</form:option>
															</c:forEach>
														</form:select> <%-- <form:hidden path="councilMemberCommitteeMasterDtoList[${d}].memberId" id="deleteMemId${d}" /></td> --%>
													<td align="center"><form:input
															path="councilMemberCommitteeMasterDtoList[${d}].couMemberTypeDesc"
															cssClass="form-control mandColorClass"
															id="couMemberTypeDesc${d}" disabled="true" /></td>
													<td align="center"><form:input
															path="councilMemberCommitteeMasterDtoList[${d}].elecWardDesc"
															cssClass="form-control mandColorClass"
															id="elecWardDesc${d}" disabled="true" /></td>
													<%-- <td align="center"><form:input
															path="councilMemberCommitteeMasterDtoList[${d}].partyAFFDesc"
															cssClass="form-control mandColorClass"
															id="partyAFFDesc${d}" disabled="true" /></td> --%>
													<td align="center">
														<%-- <c:set var="baseLookupCode"
															value="CDS" /> <apptags:lookupField
															items="${command.getLevelData(baseLookupCode)}"
															path="councilMemberCommitteeMasterDtoList[${d}].comDsgId"
															cssClass="form-control" hasChildLookup="false"
															hasId="true"
															selectOptionLabelCode="applicantinfo.label.select"
															isMandatory="true"
															showOnlyLabel="council.member.committee.designation"
															disabled="${command.saveMode eq 'V'}" /> --%> <form:select
															path="councilMemberCommitteeMasterDtoList[${d}].comDsgId"
															cssClass="form-control" id="comDsgId${d}"
															disabled="${command.saveMode eq 'VIEW'}">
															<form:option value="">
																<spring:message code='council.dropdown.select' />
															</form:option>

															<c:forEach items="${command.getLevelData('CDS')}"
																var="look">
																<form:option value="${look.lookUpId}"
																	code="${look.otherField}">${look.descLangFirst}</form:option>
															</c:forEach>
														</form:select>
													</td>
													<c:if
														test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
																
														<td align="center"><a  
														 	href="javascript:void(0);" data-toggle="tooltip"
														 	data-placement="top"onclick="addEntryData()"
														 	class="addCF btn btn-success btn-sm"><i
														 	class="fa fa-plus-circle"></i></a> <a data-toggle="tooltip"
														 	data-placement="top" onclick="deleteEntry($(this),'removedIds')"
														 	class=" btn btn-danger btn-sm delButton"><i
														 	class="fa fa-minus"></i></a>
														</td>
													</c:if>
													<c:set var="d" value="${d + 1}" />
												</tr>
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>
							</div>

							<!-- Start button -->
							<div class="text-center">
								<c:if
									test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
									<button type="button" class="btn btn-success" title="Submit"
										onclick="saveform(this)">
										<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
										<spring:message code="council.button.submit" text="Submit" />
									</button>

								</c:if>
								<c:if test="${command.saveMode eq 'ADD'}">
									<button type="button" onclick="resetCommitteeMaster(this);"
										class="btn btn-warning" title="Reset">
										<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
										<spring:message code="council.button.reset" text="Reset" />
									</button>
								</c:if>

								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel" style=""
									onclick="backCommitteeMasterForm();" id="button-Cancel">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="council.button.back" text="Back" />
								</button>
							</div>
							<!-- End button -->

						</div>
					</div>
				</div>

			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->