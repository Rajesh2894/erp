<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<!-- End JSP Necessary Tags -->
<script type="text/javascript"
	src="js/property/propertyNoDuesCertificate.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Application Details" /></strong>
			</h2>
			<apptags:helpDoc url="PropertyNoDuesCertificate.html"></apptags:helpDoc>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<%-- <span><spring:message code="property.changeInAss" /> </span> --%>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="PropertyNoDuesCertificate.html"
				class="form-horizontal form" name="PropertyNoDuesCertificate"
				id="PropertyNoDuesCertificate">
				
				<input type="hidden" id="serviceShrtCode"	value="${command.serviceShrtCode}" />
				


				<div class="accordion-toggle">
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#Applicant"><spring:message
								code="applicantinfo.label.header" /></a>
					</h4>
					<div class="panel-collapse collapse in" id="Applicant">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"
								for="applicantTitle"><spring:message
									code="applicantinfo.label.title" /></label>
							<c:set var="baseLookupCode" value="TTL" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="applicantDetailDto.applicantTitle" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								disabled="true"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
							<label class="col-sm-2 control-label required-control"
								for="firstName"><spring:message
									code="applicantinfo.label.firstname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									disabled="true" path="applicantDetailDto.applicantFirstName"
									id="firstName" ></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="middleName"><spring:message
									code="applicantinfo.label.middlename" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									disabled="true" path="applicantDetailDto.applicantMiddleName"
									id="middleName"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"
								for="lastName"><spring:message
									code="applicantinfo.label.lastname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									disabled="true" path="applicantDetailDto.applicantLastName"
									id="lastName"></form:input>
							</div>
						</div>
					</div>
				</div>

				<div class="accordion-toggle">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="collapsed"
							data-parent="#accordion_single_collapse" href="#applicantDetails">
							<spring:message code="prop.no.dues.applicant.address"
								text="Applicant Address" />
						</a>
					</h4>

					<div id="applicantDetails" class="panel-collapse padding-top-20">
						<div class="panel-body">

							<div class="form-group">

								<label class="col-sm-2 control-label" for="buildingName"><spring:message
										code="prop.no.dues.applicant.building.name"
										text="Building Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.buildingName" id="buildingName"
										readonly="true" maxlength="100"></form:input>
								</div>
								<label class="col-sm-2 control-label" for="flatBuildingNo"><spring:message
										code="prop.no.dues.applicant.block" text="Block" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.flatBuildingNo" id="flatBuildingNo"
										readonly="true" maxlength="100"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="cbillingRoadName"><spring:message
										code="applicantinfo.label.roadname" text="Road Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.roadName" id="roadName"
										readonly="true" maxlength="100"></form:input>
								</div>


							</div>
							<div class="form-group wardZone">

								<apptags:lookupFieldSet baseLookupCode="CWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="applicantDetailDto.dwzid"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" disabled="true"
									cssClass="form-control required-control " showAll="false"
									showData="true" />
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="areaName"><spring:message
										code="prop.no.dues.applicant.city" text="City" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hideElement"
										path="applicantDetailDto.areaName" id="areaName"
										readonly="true" maxlength="100"></form:input>
								</div>
								<label class="col-sm-2 control-label" for="pinCode"><spring:message
										code="applicantinfo.label.pincode" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasNumber hideElement"
										path="applicantDetailDto.pinCode" id="pinCode" maxlength="6"
										readonly="true"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.mobile" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasMobileNo" maxlength="10"
										disabled="true" path="applicantDetailDto.mobileNo"
										id="mobileNo"></form:input>
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasemailclass"
										path="applicantDetailDto.emailId" id="emailId" disabled="true"></form:input>
								</div>
							</div>

						</div>
					</div>
				</div>


				<div class="accordion-toggle padding-top-20">

					<h4 class="panel-title">
						<a data-toggle="collapse" class="collapsed"
							data-parent="#accordion_single_collapse"
							href="#propertyDetailsDiv"> <spring:message
								code="property.Propertydetail" text="Property Details" />
						</a>
					</h4>

					<div id="propertyDetailsDiv" class="panel-collapse">

						<table class="table table-bordered" id="propertyDetails">
							<thead>
								<tr>
									<th class="text-center" width="1%"><spring:message
											code="bill.srNo" text="Sr. No." /></th>
									<c:if
										test="${command.serviceShrtCode eq 'DUB' || command.serviceShrtCode eq 'EXT' }">
										<th class="text-center required-control" width="7%"><spring:message
												code="property.financialyear" text="Financial Year" /></th>
									</c:if>
									<th class="text-center required-control" width="7%"><spring:message
											code="property.PropertyNo" text="Property No." /></th>
									<th class="text-center" width="10%"><spring:message
											code="eip.payment.flatNo" text="Flat No." /></th>
									<th class="text-center" width="15%"><spring:message
											code="ownerdetail.Ownername" text="Owner Name" /></th>
									<th class="text-center" width="25%"><spring:message
											code="ownerdetail.OwnerAdd" text="Owner Address" /></th>



								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${empty command.noDuesCertificateDto.propertyDetails}">


										<tr class="proDet0" id="proDet0">
											<td><form:input class="form-control" value="1"
													type="text" id="srNo0" path="" readonly="true" /></td>
											<c:if
												test="${command.serviceShrtCode eq 'DUB' || command.serviceShrtCode eq 'EXT' }">
												<td><form:select
														path="noDuesCertificateDto.propertyDetails[0].finacialYearId"
														cssClass="form-control mandColorClass" disabled="true"
														id="finacialYearId0" data-rule-required="true">
														<form:option value="0">
															<spring:message code="applicantinfo.label.select"
																text="select" />
														</form:option>
														<c:forEach items="${command.financialYearMap}"
															var="yearMap">
															<form:option value="${yearMap.key}"
																label="${yearMap.value}"></form:option>
														</c:forEach>
													</form:select></td>
											</c:if>
											<td><form:input
													path="noDuesCertificateDto.propertyDetails[0].propNo"
													class="form-control read" type="text" disabled="true"
													onblur="getFlatDetails(0)" id="propNo0" maxlength="50" /></td>
											<td><form:select
													path="noDuesCertificateDto.propertyDetails[0].flatNo"
													cssClass="form-control read" disabled="true"
													onchange="getPropertyDetails(0)" id="flatNo0">
													<form:option value="">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
												</form:select> <%-- <form:input
													path="noDuesCertificateDto.propertyDetails[0].flatNo"
													class="form-control" type="text"
													onblur="getPropertyDetails(0)" id="flatNo0" maxlength="50" /> --%></td>
											<td><form:input
													path="noDuesCertificateDto.propertyDetails[0].ownerName"
													class="form-control" type="text" id="ownerName0"
													maxlength="100" /></td>
											<td><form:input
													path="noDuesCertificateDto.propertyDetails[0].ownerAddress"
													class="form-control" type="text" id="address0"
													maxlength="100" /></td>

										</tr>
									</c:when>
									<c:otherwise>

										<c:forEach
											items="${command.noDuesCertificateDto.propertyDetails}"
											var="det" varStatus="status">
											<tr id="propDet${status.count-1}"
												id="proDet${status.count-1}">
												<td><form:input class="form-control"
														value="${status.count}" type="text" id="srNo0" path=""
														readonly="true" /></td>
												<c:if
													test="${command.serviceShrtCode eq 'DUB' || command.serviceShrtCode eq 'EXT' }">
													<td><form:select
															path="noDuesCertificateDto.propertyDetails[${status.count-1}].finacialYearId"
															cssClass="form-control mandColorClass" disabled="true"
															id="finacialYearId${status.count-1}"
															data-rule-required="true">
															<form:option value="0">
																<spring:message code="applicantinfo.label.select"
																	text="select" />
															</form:option>
															<c:forEach items="${command.financialYearMap}"
																var="yearMap">
																<form:option value="${yearMap.key}"
																	label="${yearMap.value}"></form:option>
															</c:forEach>
														</form:select></td>
												</c:if>
												<td><form:input
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].propNo"
														class="form-control read" type="text" disabled="true"
														onblur="getFlatDetails(${status.count-1})"
														id="propNo${status.count-1}" maxlength="50" /></td>
												<td><form:select
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].flatNo"
														cssClass="form-control read" disabled="true"
														onchange="getPropertyDetails(${status.count-1})"
														id="flatNo${status.count-1}">
														<form:option value="">
															<spring:message code="solid.waste.select" text="select" />
														</form:option>
													</form:select> <%-- <form:input
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].flatNo"
														class="form-control" type="text"
														id="flatNo${status.count-1}" maxlength="50" /> --%></td>
												<td><form:input
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].ownerName"
														class="form-control" type="text"
														id="ownerName${status.count-1}" maxlength="100"
														readonly="true" /></td>
												<td><form:input
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].ownerAddress"
														class="form-control" type="text"
														id="address${status.count-1}" maxlength="100"
														readonly="true" /></td>

											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>


						</table>
					</div>
				</div>
				
					<c:if test="${not empty command.checkList}">
						<!------------------------------------------------------------  -->
						<!--  AttachDocuments Form starts here -->
						<!------------------------------------------------------------  -->
						<div class="panel panel-default" id="accordion_single_collapse">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a19"> <spring:message
										code="care.attach.document" text="Attached Documents" /></a>
							</h4>
							<div id="a19" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-12 text-left">
											<div class="table-responsive">
												<table class="table table-bordered table-striped"
													id="attachDocs">
													<tr>
														<th><spring:message code="care.document.name"
																text="Document Name" /></th>
														<th><spring:message code="care.view.document"
																text="View Documents" /></th>
													</tr>
													<c:forEach items="${command.checkList}" var="lookUp">
														<tr>
															<td align="center">${lookUp.documentName}</td>
															<td align="center"><apptags:filedownload
																	filename="${lookUp.documentName}"
																	filePath="${lookUp.uploadedDocumentPath}"
																	actionUrl="PropertyNoDuesCertificate.html?Download">
																</apptags:filedownload></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</div>
									</div>
									<div class="form-group"></div>
								</div>
							</div>
						</div>

					</c:if>

<div class="text-center">
				<button type="button" class="btn btn-danger" id="back"
					onclick="window.location.href='CitizenHome.html'">
					<spring:message code="adh.back" text="Back"></spring:message>
				</button>
</div>


			</form:form>
		</div>
	</div>
</div>



