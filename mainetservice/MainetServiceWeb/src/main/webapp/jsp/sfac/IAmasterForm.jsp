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
<script type="text/javascript" src="js/sfac/IAmasterForm.js"></script>
<style>
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
				<spring:message code="sfac.Ia.form.title"
					text="Implementing Agency On-Boarding" />
			</h2>
			<apptags:helpDoc url="IAMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="IAMasterForm" action="IAMasterForm.html" method="post"
				class="form-horizontal">
				<form:hidden path="removeContactDetIds" id="removeContactDetIds" />
				<form:hidden path="dupIaName" id="dupIaName" />
				<form:hidden path="dupIaShortNm" id="dupIaShortNm" />
				<form:hidden path="" id="viewMode" value="${command.viewMode}"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<apptags:input labelCode="sfac.IA.name" cssClass="mandColorClass alpaSpecial"
						path="iaMasterDto.IAName" isMandatory="true" maxlegnth="200"></apptags:input>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.iaOnboarding.year" text="IA Onboarding Year" /></label>
					<div class="col-sm-4">
						<form:select path="iaMasterDto.alcYear" id="alcYear"
							cssClass="form-control chosen-select-no-results"
							disabled="${command.viewMode eq 'V' ? true : false }">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<%-- 	<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.state.category" text="State Category" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="STC" />
						<form:select path="iaMasterDto.stateCate"
							class="form-control" id="">
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

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.region" text="Region" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="REG" />
						<form:select path="iaMasterDto.region" id="region"
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

				</div> --%>


				<%-- <div class="form-group">
					<apptags:input labelCode="sfac.first.name"
						cssClass="mandColorClass hasNameClass" path="iaMasterDto.fName"
						isMandatory="true" maxlegnth="250"></apptags:input>

					<apptags:input labelCode="sfac.middel.name"
						cssClass="mandColorClass hasNameClass" path="iaMasterDto.mName"
						isMandatory="true" maxlegnth="250"></apptags:input>
				</div>
				<div class="form-group">
					<apptags:input labelCode="sfac.last.name"
						cssClass="mandColorClass hasNameClass" path="iaMasterDto.lName"
						isMandatory="true" maxlegnth="250"></apptags:input>

					<apptags:input labelCode="sfac.contact.no"
						cssClass="mandColorClass hasMobileNo "
						path="iaMasterDto.iaContactNo" isMandatory="true" maxlegnth="10"></apptags:input>
				</div> --%>

				<div class="form-group">
					<%-- 	<apptags:input labelCode="sfac.emailId" cssClass="mandColorClass"
						path="iaMasterDto.iaEmailId" isMandatory="true" maxlegnth="50"></apptags:input> --%>

					<apptags:input labelCode="sfac.address" cssClass="mandColorClass alpaSpecial"
						isDisabled="${command.viewMode eq 'V' ? true : false }"
						path="iaMasterDto.iaAddress" maxlegnth="300"></apptags:input>

					<apptags:input labelCode="sfac.pincode"
						cssClass="mandColorClass hasPincode"
						isDisabled="${command.viewMode eq 'V' ? true : false }"
						path="iaMasterDto.iaPinCode" maxlegnth="6"></apptags:input>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.level" text="Level" /><span class="mand">*</span></label>
					<div class="col-sm-4">

						<label class="radio-inline" for="State"> <form:radiobutton
								name="State" path="iaMasterDto.level" checked="checked"
								value="S" disabled="${command.viewMode eq 'V' ? true : false }"
								id="levelState"></form:radiobutton> <spring:message
								code="sfac.state" text="State" />
						</label> <label class="radio-inline" for="National"> <form:radiobutton
								name="National" path="iaMasterDto.level" value="N"
								disabled="${command.viewMode eq 'V' ? true : false }"
								id="levelNational"></form:radiobutton> <spring:message
								code="sfac.national" text="National" />
						</label>
					</div>
					<div class="no hidebox">
						<label class="col-sm-2 control-label required-control" for="state">
							<spring:message code="sfac.state" text="State" />
						</label>
						<div class="col-sm-4">
							<form:select path="iaMasterDto.state" class="form-control"
								id="state" disabled="${command.viewMode eq 'V' ? true : false }">
								<form:option value="">
									<spring:message code="tbOrganisation.select" text="Select" />
								</form:option>
								<c:forEach items="${command.stateList}" var="lookUp">
									<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="sfac.ia.short.name" cssClass="mandColorClass hasNameClass charCase"
						path="iaMasterDto.iaShortName" isMandatory="true" maxlegnth="100" isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
				
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Status" /></label>
					<div class="col-sm-4">
						<form:select path="iaMasterDto.activeInactiveStatus"
							class="form-control chosen-select-no-results"
							id="activeInactiveStatus"
							disabled="${command.viewMode eq 'V' ? true : false }"
							data-rule-required="true">
							<form:option value="0"><spring:message code="sfac.select" text="Select" />
							</form:option>
							<form:option value="A" selected="selected"><spring:message code="sfac.active" text="Active" />
							</form:option>
							<form:option value="I"><spring:message code="sfac.inactive" text="InActive" />
							</form:option>
						</form:select>
					</div>


				</div>


				<%-- 	<div class="form-group">
					<apptags:input labelCode="sfac.address" cssClass="mandColorClass"
						path="iaMasterDto.iaAddress" isMandatory="true" maxlegnth="300"></apptags:input>
				</div> --%>


				<!-- 	<div class="panel panel-default"> -->
				<!-- <div class="panel-heading"> -->
				<h4 class="panel-title">
					<a data-toggle="collapse" class="collapsed" href="#contDetails">
						<spring:message code="sfac.contact.details" text="Contact Details" />
					</a>
				</h4>
				<!-- </div> -->
				<div id="contDetails">
					<div class="panel-body">
						<c:set var="d" value="0" scope="page"></c:set>
						<div class="table-responsive">
							<table
								class="table table-bordered table-striped contact-details-table"
								id="contactDetails">
								<thead>
									<tr>
										<th width="8%"><spring:message code="sfac.srno"
												text="Sr. No." /></th>
										<th><spring:message code="sfac.designation"
												text="Designation" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
										<th><spring:message code="sfac.role" text="Role" /></th>
										<th><spring:message code="sfac.title" text="Title" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
										<th><spring:message code="sfac.first.name"
												text="First Name" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
										<th><spring:message code="sfac.middel.name"
												text="Middle Name" /></th>
										<th><spring:message code="sfac.last.name" text="Last Name" /></th>
										<th><spring:message code="sfac.contact.no"
												text="Contact No." /><span class="mand"><i
														class="text-red-1">*</i></span></th>
										<th><spring:message code="sfac.emailId" text="Email Id" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
										<c:if test="${command.viewMode ne 'V'}">
										<th width="10%"><spring:message code="sfac.action"
												text="Action" /></th></c:if>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.iaMasterDto.iaDetailDto)>0 }">
											<c:forEach var="iaMasterDto"
												items="${command.iaMasterDto.iaDetailDto}" varStatus="status">
												<tr class="appendableContactDetails">
	
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sequence${d}"
															value="${d+1}" disabled="true" /> <form:hidden
															path="iaMasterDto.iaDetailDto[${d}].iadId" id="iadId${d}"
															class="contId" /></td>
	
													<td>
														<%-- 				<div>
															<form:select id="dsgId${d}"
																path="iaMasterDto.iaDetailDto[${d}].dsgId"
																disabled="${command.viewMode eq 'V' ? true : false }"
																cssClass="form-control ">
																<form:option value="">
																	<spring:message code="" text="Select" />
																</form:option>
																<c:forEach items="${command.designlist}" var="desig">
																	<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
																</c:forEach>
															</form:select>
														</div> --%> <form:input
															path="iaMasterDto.iaDetailDto[${d}].dsgId" id="dsgId${d}"
															disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control hasNameClass" maxlength="100" />
													</td>
	
													<td>
														<div>
															<c:set var="baseLookupCode" value="ROL" />
															<form:select path="iaMasterDto.iaDetailDto[${d}].role"
																class="form-control" id="role${d}"
																disabled="${command.viewMode eq 'V' ? true : false }">
																<c:set var="baseLookupCode" value="ROL" />
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
													<td>
														<div>
															<c:set var="baseLookupCode" value="TTL" />
															<form:select path="iaMasterDto.iaDetailDto[${d}].titleId"
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
															path="iaMasterDto.iaDetailDto[${d}].fName" id="fName${d}"
															disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control hasNameClass" maxlength="250" /></td>
	
													<td><form:input
															path="iaMasterDto.iaDetailDto[${d}].mName" id="mName${d}"
															disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control hasNameClass" maxlength="250" /></td>
	
													<td><form:input
															path="iaMasterDto.iaDetailDto[${d}].lName" id="lName${d}"
															disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control hasNameClass" maxlength="250" /></td>
	
	
													<td><form:input
															path="iaMasterDto.iaDetailDto[${d}].contactNo"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="contactNo${d}" class="form-control hasMobileNo"
															maxlength="10" /></td>
	
													<td><form:input
															path="iaMasterDto.iaDetailDto[${d}].emailId"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="emailId${d}" class="form-control hasemailclass" /></td>
	
													<c:if test="${command.viewMode ne 'V'}">
														<td class="text-center"><a
															class="btn btn-blue-2 btn-sm addButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addRow(this);"> <i class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteContactDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'>
																<i class="fa fa-trash"></i>
														</a></td>
													</c:if>
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
													<%-- 	<div>
														<form:select id="dsgId${d}"
															disabled="${command.viewMode eq 'V' ? true : false }"
															path="iaMasterDto.iaDetailDto[${d}].dsgId"
															cssClass="form-control">
															<form:option value="">
																<spring:message code="" text="Select" />
															</form:option>
															<c:forEach items="${command.designlist}" var="desig">
																<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
															</c:forEach>
														</form:select>
													</div> --%> <form:input
														path="iaMasterDto.iaDetailDto[${d}].dsgId" id="dsgId${d}"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control hasNameClass" maxlength="100" />
												</td>
	
												<td>
													<div>
														<c:set var="baseLookupCode" value="ROL" />
														<form:select path="iaMasterDto.iaDetailDto[${d}].role"
															class="form-control" id="role${d}"
															disabled="${command.viewMode eq 'V' ? true : false }">
															<c:set var="baseLookupCode" value="ROL" />
															<form:option value="0">
																<spring:message code="sfac.select" text="Select" />
															</form:option>
															<c:forEach items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select>
													</div>
												</td>
	
												<td>
													<div>
														<c:set var="baseLookupCode" value="TTL" />
														<form:select path="iaMasterDto.iaDetailDto[${d}].titleId"
															class="form-control" id="titleId${d}"
															disabled="${command.viewMode eq 'V' ? true : false }">
															<c:set var="baseLookupCode" value="TTL" />
															<form:option value="0">
																<spring:message code="sfac.select" text="Select" />
															</form:option>
															<c:forEach items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select>
													</div>
												</td>
	
	
	
	
	
												<td><form:input
														path="iaMasterDto.iaDetailDto[${d}].fName" id="fName${d}"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control hasNameClass" maxlength="250" /></td>
	
												<td><form:input
														path="iaMasterDto.iaDetailDto[${d}].mName" id="mName${d}"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control hasNameClass" maxlength="250" /></td>
	
												<td><form:input
														path="iaMasterDto.iaDetailDto[${d}].lName" id="lName${d}"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control hasNameClass" maxlength="250" /></td>
	
												<td><form:input
														path="iaMasterDto.iaDetailDto[${d}].contactNo"
														disabled="${command.viewMode eq 'V' ? true : false }"
														id="contactNo${d}" class="form-control hasMobileNo"
														maxlength="10" /></td>
	
												<td><form:input
														path="iaMasterDto.iaDetailDto[${d}].emailId"
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
													</a></td>
												</c:if>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- </div> -->



				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveIAMasterForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="sfac.button.reset" text="Reset"/>'
							onclick="ResetForm()">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger"
						title='<spring:message code="sfac.button.back" text="Back"/>'
						onclick="window.location.href ='IAMasterForm.html'">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>

	</div>
</div>