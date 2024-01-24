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
<script type="text/javascript" src="js/sfac/CBBOMasterForm.js"></script>
<style>
#approved {
	margin: 0.6rem 0 0 0;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.cbbo.summary.form.title"
					text="Cluster Based Business Organization On-Boarding" />
			</h2>
			<apptags:helpDoc url="CBBOMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="cbboMasterForm" action="CBBOMasterForm.html"
				method="post" class="form-horizontal">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="iaMastName" id="iaMastName" />
                <form:hidden path="iaAllYr" id="iaAllYr"/>
                <form:hidden path="masterDto.cbboAppoitmentYr" id="cbboAppoitmentYr" />
                
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#cbboDataEntry">
									<spring:message code="sfac.cbbo.data.entry" text="Data Entry" />
								</a>
							</h4>
						</div>
						<div id="cbboDataEntry" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

                                       <apptags:input labelCode="sfac.pan.no"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass alphaNumeric charCase" onChange="getDetailsByPanNo();"
										path="masterDto.panNo" isMandatory="true" maxlegnth="10"
										placeholder="BLUCS4233S"></apptags:input>

								
									<apptags:input labelCode="sfac.CBBO.name"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass"
										path="masterDto.cbboName" maxlegnth="200" isMandatory="true"></apptags:input>
								</div>


								<div class="form-group">
								<apptags:input labelCode="sfac.IA.name" isReadonly="true"
										cssClass="mandColorClass hasNameClass" path="masterDto.IAName"
										isMandatory="true" maxlegnth="100"></apptags:input>
									<%--	<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.IA.name" text="IA Name" /></label>
							 	<div class="col-sm-4">
										<form:select path="masterDto.iaId" id="iaName"
											disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.iaMasterDtoList}" var="lookUp">
												<form:option value="${lookUp.IAId}" code="${lookUp.IAName}">${lookUp.IAName}</form:option>
											</c:forEach>
										</form:select>
									</div> --%>


									<label class="col-sm-2 control-label required-control"
										for="state"> <spring:message code="sfac.state" text="State" /></label>
									<div class="col-sm-4">
										<form:select path="masterDto.sdb1" class="form-control"
											id="sdb1"
											disabled="${command.viewMode eq 'V' ? true : false }">
											<form:option value="">
												<spring:message code="tbOrganisation.select" text="Select" />
											</form:option>
											<c:forEach items="${command.stateList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>




									<%-- 		<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.IA.AllocationYear" text="IA Allocation Year" /></label>
									<div class="col-sm-4">
										<form:select path="masterDto.alcYear" id="iaAlcYear"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.faYears}" var="lookUp">
												<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select>
									</div> --%>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.EmpanelmentOfCbbo" text="Empanelment of Cbbo" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="masterDto.alcYearToCBBO" type="text"
												onchange="getAppoitmentYear();"
												class="form-control datepicker mandColorClass alcYearToCBBO"
												id="alcYearToCBBO" placeholder="dd/mm/yyyy" readonly="true" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.appointment.year" text="CBBO Appointment Year" /></label>
									<div class="col-sm-4">
										<%-- <form:select path="masterDto.cbboAppoitmentYr"
											id="cbboAppoitmentYr" disabled="true"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.faYears}" var="lookUp">
												<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select> --%>
										<form:input path="cbboAppyr"  id ="cbboAppyr" readonly="true"  class="form-control mandColorClass" />
									</div>
								</div>

								<%-- 	<div class="form-group stateDistBlock">
									<c:set var="baseLookupCode" value="SDB" />
									<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="SDB"
										hasId="true" pathPrefix="masterDto.sdb"
										hasLookupAlphaNumericSort="true" isMandatory="true"
										hasSubLookupAlphaNumericSort="true" showAll="false" />
								</div> 
									
								<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
											code="sfac.allocation.category" text="Allocation Category" /></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="ALC" />
									<form:select
										path="masterDto.allocationCategory"
										class="form-control" id="allocationCategory">
										<form:option value="0">
											<spring:message code="sfac.select" text="Select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option code="${lookUp.lookUpCode}"
												value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
                                  </div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cbbo.dmc.approval.status"
											text="DMC Approval Status" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="APS" />
										<form:select path="masterDto.dMCApprStatus"
											id="dmcApprovalStatus"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>--%>
								<!-- #164648 this field are not required  on cbbo added on fpo master form -->
								<%-- 	<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.state.category" text="State Category" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass"
											id="stateCategory" path="masterDto.statecategory"
											readonly="true"></form:input>

									</div>
									
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.region" text="Region" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass" id="region"
											path="masterDto.region" readonly="true"></form:input>
									</div>
								</div>
								
							
								
								<div class="form-group">
									<label class="col-sm-2 control-label"> <spring:message
											code="sfac.cbbo.is.aspirational.district"
											text="Is Aspirational District" /> <span class="mand">*</span></label>
									<div class="col-sm-4">
										<form:checkbox id="isAspirationalDistrict" path="masterDto.isAspirationalDist"  value=""  />
									</div>
									
									<label class="col-sm-2 control-label"> <spring:message
											code="sfac.cbbo.is.tribal.district"
											text="Is Tribal District" /> <span class="mand">*</span></label>
									<div class="col-sm-4">
										<form:checkbox id="isTribalDistrict" path="masterDto.isTribalDist"  value=""  />
									</div>
								</div> --%>

								<div class="form-group">
									<%-- 	<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cbbo.odop" text="ODOP" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass" id="odop"
											path="masterDto.odop" readonly="true"></form:input>
									</div> --%>

								</div>

								<div class="form-group">

									<apptags:input labelCode="sfac.address"
										cssClass="mandColorClass"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										path="masterDto.address" maxlegnth="300"></apptags:input>

									<apptags:input labelCode="sfac.pincode"
										cssClass="mandColorClass hasPincode" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										path="masterDto.pinCode" maxlegnth="6"></apptags:input>
								</div>

						
						<%-- 		<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cbbo.dmc.approval.status"
											text="DMC Approval Status" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="APS" />
										<form:select path="masterDto.dmcApproval" id="dmcApproval"
											onchange="checkStatusAndshowDate();" disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label hideDate"><spring:message
											code="sfac.approvalPending.date" text="Approval Pending Date" /><span
										class="showMand"><i class="text-red-1">*</i></span></label>
									<div class="col-sm-4 hideDate">
										<div class="input-group ">
											<form:input path="masterDto.appPendingDate" type="text"
												class="form-control datepicker mandColorClass appPendingDate"
												id="appPendingDate" placeholder="dd/mm/yyyy" readonly="true" />
											<span class="input-group-addon "><i
												class="fa fa-calendar "></i></span>
										</div>
									</div>
								</div> --%>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="regAct"> <spring:message
											code="sfac.fpo.typeOfPromotionAge"
											text="Type of Promotion Agency" />
									</label>
									<c:set var="baseLookupCode" value="PAT" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="masterDto.typeofPromAgen"
										disabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="sfac.select" isMandatory="true" />

									<apptags:input labelCode="sfac.cbbo.fpoAllocationTarge"
										cssClass="mandColorClass hasNumber" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										path="masterDto.fpoAllocationTarget" maxlegnth="3"></apptags:input>

								</div>
								<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
											code="" text="Status" /></label>
									<div class="col-sm-4">
										<form:select path="masterDto.activeInactiveStatus"
											class="form-control chosen-select-no-results" id="activeInactiveStatus"
											disabled="${command.viewMode eq 'V' ? true : false }" data-rule-required="true">
											<form:option value="0"><spring:message code="sfac.select" text="Select" />
											</form:option>
											<form:option value="A"><spring:message code="sfac.active" text="Active" />
											</form:option>
											<form:option value="I"><spring:message code="sfac.inactive" text="InActive" />
											</form:option>
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
									data-parent="#accordion_single_collapse"
									href="#cbboContactDetails"> <spring:message
										code="sfac.contact.details" text="Contact Details" />
								</a>
							</h4>
						</div>
						<%-- 		<div id="cbboContactDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="sfac.cbbo.contact.person"
										cssClass="mandColorClass hasNameClass" path="masterDto.cBBOContactPerson"
										isMandatory="true" maxlegnth="100"></apptags:input>
									<apptags:input labelCode="sfac.cbbo.mobile.no"
										cssClass="mandColorClass hasNumber" path="masterDto.contactNo"
										isMandatory="true" maxlegnth="10"></apptags:input>
								</div>
								
								<div class="form-group">
									<apptags:input labelCode="sfac.cbbo.email.id"
										cssClass="mandColorClass hasemailclass" path="masterDto.emailId"
										isMandatory="true" maxlegnth="100"></apptags:input>
								</div>
							</div>
						</div> --%>


						<div id="cbboContactDetails">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<div class="table-responsive">
									<table
										class="table table-bordered table-striped contact-details-table"
										id="contactDetails">
										<thead>
											<tr>
												<th width="5%"><spring:message code="sfac.srno" text="Sr. No." /></th>
												<th><spring:message code="sfac.designation" text="Designation" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
												<th width="10%"><spring:message code="sfac.title" text="Title" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.first.name"
														text="First Name" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.middel.name"
														text="Middle Name" /></th>
												<th><spring:message code="sfac.last.name"
														text="Last Name" /></th>
												<th><spring:message code="sfac.contact.no"
														text="Contact No." /><span class="mand"><i
														class="text-red-1">*</i></span></th>
												<th><spring:message code="sfac.emailId" text="Email Id" /></th>
	                                            <c:if test="${command.viewMode ne 'V'}">
												<th width="15%"><spring:message code="sfac.action" text="Action" /></th></c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${fn:length(command.masterDto.cbboDetDto)>0 }">
													<c:forEach var="masterDto"
														items="${command.masterDto.cbboDetDto}" varStatus="status">
														<tr class="appendableContactDetails">
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass" id="sequence${d}"
																	value="${d+1}" disabled="true" /> <form:hidden
																	path="masterDto.cbboDetDto[${d}].cbboDId"
																	id="cbboDId${d}" class="contId" /></td>
	
															<td>
																<div>
																	<form:select id="dsgId${d}"
																		path="masterDto.cbboDetDto[${d}].dsgId"
																		disabled="${command.viewMode eq 'V' ? true : false }"
																		cssClass="form-control">
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach items="${command.designlist}" var="desig">
																			<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
															<td>
																<div>
																	<c:set var="baseLookupCode" value="TTL" />
																	<form:select path="masterDto.cbboDetDto[${d}].titleId"
																		class="form-control" id="titleId${d}"
																		disabled="${command.viewMode eq 'V' ? true : false }">
																		<c:set var="baseLookupCode" value="TTL" />
																		<form:option value="0">
																			<spring:message code="sfac.select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</div>
															</td>
	
	
															<td><form:input
																	path="masterDto.cbboDetDto[${d}].fName" id="fName${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control hasNameClass" maxlength="250" /></td>
	
															<td><form:input
																	path="masterDto.cbboDetDto[${d}].mName" id="mName${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control hasNameClass" maxlength="250" /></td>
	
															<td><form:input
																	path="masterDto.cbboDetDto[${d}].lName" id="lName${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control hasNameClass" maxlength="250" /></td>
	
	
															<td><form:input
																	path="masterDto.cbboDetDto[${d}].contactNo"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	id="contactNo${d}" class="form-control hasMobileNo"
																	maxlength="10" /></td>
	
															<td><form:input
																	path="masterDto.cbboDetDto[${d}].emailId"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	id="emailId${d}" class="form-control hasemailclass" /></td>
	
															  <c:if test="${command.viewMode ne 'V'}">
															<td class="text-center"><a
																class="btn btn-blue-2 btn-sm addButton" onclick="addRow(this);"
																title='<spring:message code="sfac.fpo.add" text="Add" />'>
																	<i class="fa fa-plus-circle"></i>
															</a> <a class='btn btn-danger btn-sm deleteContactDetails'
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'>
																	<i class="fa fa-trash"></i>
															</a></td></c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="appendableContactDetails">
	
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${d}"
																value="${d+1}" disabled="true" /></td>
	
														<td>
															<div>
																<form:select id="dsgId${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	path="masterDto.cbboDetDto[${d}].dsgId"
																	cssClass="form-control">
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach items="${command.designlist}" var="desig">
																		<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
	
														<td>
															<div>
																<c:set var="baseLookupCode" value="TTL" />
																<form:select path="masterDto.cbboDetDto[${d}].titleId"
																	class="form-control" id="titleId${d}"
																	disabled="${command.viewMode eq 'V' ? true : false }">
																	<c:set var="baseLookupCode" value="TTL" />
																	<form:option value="0">
																		<spring:message code="sfac.select" text="Select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</td>
	
													
	
														<td><form:input
																path="masterDto.cbboDetDto[${d}].fName" id="fName${d}"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control hasNameClass" maxlength="250" /></td>
	
														<td><form:input
																path="masterDto.cbboDetDto[${d}].mName" id="mName${d}"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control hasNameClass" maxlength="250" /></td>
	
														<td><form:input
																path="masterDto.cbboDetDto[${d}].lName" id="lName${d}"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control hasNameClass" maxlength="250" /></td>
	
														<td><form:input
																path="masterDto.cbboDetDto[${d}].contactNo"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="contactNo${d}" class="form-control hasMobileNo"
																maxlength="10" /></td>
	
														<td><form:input
																path="masterDto.cbboDetDto[${d}].emailId"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="emailId${d}" class="form-control hasemailclass" /></td>
	
	                                                    <c:if test="${command.viewMode ne 'V'}">
														<td class="text-center"><a
															class="btn btn-blue-2 btn-sm addButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addRow(this);"> <i class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteContactDetails'
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'>
																<i class="fa fa-trash"></i>
														</a></td></c:if>
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

				</div>
				<%-- 	<div class="form-group padding-top-10">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.approved" text="Approved" /></label>
					<div class="col-sm-4 height-2rem">
						<form:checkbox id="approved"
							disabled="${command.viewMode eq 'V' ? true : false }"
							path="masterDto.approved" value="" checked="${masterDto.approved eq 'Y'? 'checked' : '' }" />
					</div>
				</div> --%>
				
				

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveCBBOMasterForm(this)">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset"/>'
						onclick="ResetForm()">
						<spring:message code="sfac.button.reset" text="Reset" />
					</button></c:if>
					<button type="button" class="btn btn-danger"
						title='<spring:message code="sfac.button.back" text="Back"/>'
						onclick="window.location.href ='CBBOMasterForm.html'">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>
				
			</form:form>
		</div>

	</div>
</div>