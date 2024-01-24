
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/billingMethodChange.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="property.billMethodChange"
						text="Bill Method Change" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="property.billMethodChange"
						text="Bill Method Change" /> </span>
			</div>
			<form:form action="BillingMethodAuthorization.html"
				class="form-horizontal form" name="BillingMethodChangeAuth"
				id="BillingMethodChangeAuth">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden path="flatNoOfRow" id="flatNoOfRow" />

				<div class="form-group">
					<apptags:input labelCode="propertydetails.PropertyNo."
						path="provisionalAssesmentMstDto.assNo" cssClass="mandColorClass"
						isDisabled="true"></apptags:input>

					<apptags:input labelCode="property.billingMethod"
						path="billMethodDesc" cssClass="mandColorClass" isDisabled="true"></apptags:input>
				</div>

				<div class="accordion-toggle">
					<c:if
						test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">

						<!----------------------------------------Existing Owner Details (First owner will be the primary owner)---------------------------->
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
									code="propertyTax.Mutation.ExistOwnerDetails"
									text="Existing Owner Details" /></a>
						</h4>

						<div class="panel-collapse collapse in" id="OwnshipDetail">
							<div class="form-group">
								<label class="col-sm-2 control-label" for="ownershiptype"><spring:message
										code="property.ownershiptype" /></label>
								<div class="col-sm-4">
									<form:input
										path="provisionalAssesmentMstDto.proAssOwnerTypeName"
										id="ownershiptype" class="form-control" disabled="true" />
									<form:hidden path="ownershipPrefix" id="ownershipId"
										class="form-control" />
								</div>
							</div>

							<c:choose>
								<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
									<table id="singleOwnerTable"
										class="table table-striped table-bordered ">

										<tr>
											<th width="20%"><spring:message
													code="ownersdetail.ownersname" /></th>
											<th width="10%"><spring:message
													code="ownersdetail.ownersnameReg" /></th>
											<th width="10%"><spring:message
													code="ownersdetail.mobileno" /></th>
											<th width="10%"><spring:message code="property.email" /></th>
											<th width="12%"><spring:message
													code="ownersdetail.adharno" /></th>
											<th width="10%"><spring:message
													code="ownersdetail.pancard" />
										</tr>
										<tbody>
											<tr>
												<td><form:input id="assoOwnerName"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName"
														class="form-control" disabled="true" /></td>

												<td><form:input id="assoOwnerNameReg"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerNameReg"
														class="form-control" disabled="true" /></td>

												<td><form:input id="assoMobileno"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno"
														class="hasNumber form-control" disabled="true" /></td>

												<td><form:input id="emailId"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail"
														class="hasemailclass form-control" disabled="true" /></td>

												<td><form:input id="assoAddharno"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno"
														class="form-control " disabled="true" /></td>
												<td><form:input id="assoPanno"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno"
														class="form-control" disabled="true" /></td>
											</tr>
										</tbody>
									</table>
								</c:when>

								<c:when test="${command.getOwnershipPrefix() eq 'JO'}">
									<c:set var="a" value='0' />
									<table id="jointOwnerTable"
										class="table text-left table-striped table-bordered">

										<tr>
											<th width="10%"><spring:message
													code="ownersdetail.ownersname" /></th>
											<th width="7%"><spring:message
													code="ownersdetail.ownersnameReg" /></th>
											<th width="5%"><spring:message
													code="ownerdetails.PropertyShare" /></th>
											<th width="11%"><spring:message
													code="ownersdetail.mobileno" /></th>
											<th width="10%"><spring:message code="property.email" /></th>
											<th width="12%"><spring:message
													code="ownersdetail.adharno" /></th>
											<th width="10%"><spring:message
													code="ownersdetail.pancard" /></th>
										</tr>
										<tbody>
											<c:forEach var="ownershipTypeList"
												items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">
												<tr>
													<td>${ownershipTypeList.assoOwnerName}</td>
													<td>${ownershipTypeList.assoOwnerNameReg}</td>
													<td>${ownershipTypeList.propertyShare}</td>
													<td>${ownershipTypeList.assoMobileno}</td>
													<td>${ownershipTypeList.eMail}</td>
													<td>${ownershipTypeList.assoAddharno}</td>
													<td>${ownershipTypeList.assoPanno}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</c:when>
								<c:otherwise>
									<table id="companyDetailTable"
										class="table text-left table-striped table-bordered">

										<tr>
											<th width="25%"><spring:message
													code="ownersdetail.ownersname" /></th>
											<th width="10%"><spring:message
													code="ownersdetail.ownersnameReg" /></th>
											<th width="10%"><spring:message
													code="ownersdetail.mobileno" /></th>
											<th width="10%"><spring:message code="property.email" /></th>
											<th width="10%"><spring:message
													code="ownersdetail.pancard" /></th>
										</tr>
										<tbody>
											<tr>
												<td><form:input id="assoOwnerName"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName"
														class="form-control" disabled="true" /></td>

												<td><form:input id="assoOwnerNameReg"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerNameReg"
														class="form-control" disabled="true" /></td>

												<td><form:input id="assoMobileno"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno"
														class="hasNumber form-control" disabled="true" /></td>

												<td><form:input id="emailId"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail"
														class="hasemailclass form-control" disabled="true" /></td>

												<td class="companyDetails"><form:input id="assoPanno"
														path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno"
														class="form-control" disabled="true" /></td>
											</tr>
										</tbody>
									</table>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>
				<!----------------------------------------------Existing Property Address Details-------------------------------------------------------->

				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#propertyAddress"><spring:message
							code="propertyTax.Mutation.ExistPropDetails" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="propertyAddress">
					<div class="form-group">
						<label for="assAddress" class="col-sm-2 control-label "><spring:message
								code="property.propertyaddress" text="Property address" /></label>
						<div class="col-sm-4">
							<form:textarea class="form-control" id="assAddress"
								path="provisionalAssesmentMstDto.assAddress" disabled="true" />
						</div>

						<apptags:input labelCode="property.location"
							path="provisionalAssesmentMstDto.locationName" isDisabled="true"></apptags:input>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="proAssPincode"><spring:message
								code="property.pincode" /></label>
						<div class="col-sm-4">
							<form:input class="form-control hasPincode"
								path="provisionalAssesmentMstDto.assPincode" id="proAssPincode"
								maxlength="6" readonly="true"></form:input>
						</div>
					</div>
				</div>

				<!----------------------------------------------------------Tax-Zone details------------------------------------------------------ -->
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#taxZone"><spring:message
							code="property.taxZoneDetails" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="taxZone">
					<div class="panel-body">
						<div class="form-group">

							<apptags:input labelCode="property.propertyZone"
								path="provisionalAssesmentMstDto.assWardDesc1" isDisabled="true"></apptags:input>

							<c:if
								test="${command.provisionalAssesmentMstDto.assWard2 ne null}">
								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc2"
									isDisabled="true"></apptags:input>
							</c:if>
						</div>
						<div class="form-group">
							<c:if
								test="${command.provisionalAssesmentMstDto.assWard3 ne null}">

								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc3"
									isDisabled="true"></apptags:input>
							</c:if>
							<c:if
								test="${command.provisionalAssesmentMstDto.assWard4 ne null}">
								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc4"
									isDisabled="true"></apptags:input>
							</c:if>
						</div>
						<div class="form-group">
							<c:if
								test="${command.provisionalAssesmentMstDto.assWard5 ne null}">

								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc5"
									isDisabled="true"></apptags:input>
							</c:if>
						</div>
						<div class="form-group">
							<apptags:input labelCode="unitdetails.RoadType"
								path="provisionalAssesmentMstDto.proAssdRoadfactorDesc"
								isDisabled="true"></apptags:input>
						</div>

					</div>
				</div>

				<!-- ---------------------------------------------Unit Details --------------------------------------------------------------------------->

				<h4 class="margin-top-10 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#UnitDetail"><spring:message
							code="property.unitdetails" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="UnitDetail">

					<table id="unitDetailTable"
						class="table table-striped table-bordered appendableClass unitDetails">
						<tbody>
							<tr>
								<th width="7%"><spring:message code="unitdetails.year" /></th>
								<th width="4%"><spring:message code="unitdetails.unitno" /></th>
								<th width="12%" class="required-control"><spring:message
										code="unitdetails.flatNo" /></th>
								<th width="8%"><spring:message code="unitdetails.floorno" /></th>
								<th width="10%"><spring:message
										code="unitdetails.ConstcompletionDate" /></th>

								<th width="12%"><spring:message
										code="unitdetails.constructiontype" /></th>
								<th width="10%"><spring:message
										code="unitdetails.usagefactor" /></th>
								<c:if
									test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype2 ne null }">
									<th width="12%"><spring:message
											code="property.Usagesubtype" text="Usage sub type" /></th>
								</c:if>
								<c:if
									test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype3 ne null}">
									<th width="12%"><spring:message
											code="property.Usagesubtype" text="Usage sub type" /></th>
								</c:if>
								<c:if
									test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype4 ne null}">
									<th width="12%"><spring:message
											code="property.Usagesubtype" text="Usage sub type" /></th>
								</c:if>
								<c:if
									test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype5 ne null}">
									<th width="12%"><spring:message
											code="property.Usagesubtype" text="Usage sub type" /></th>
								</c:if>
								<th width="" class=""><spring:message
										code="unitdetails.carpetArea" text="Carpet Area" /></th>
								<th width="12%"><spring:message code="unitdetails.taxable" /></th>
								<th width="" class=""><spring:message
										code="unitdetails.constructPermissionNo"
										text="Construction Permission No." /></th>
								<th width="" class=""><spring:message
										code="unitdetails.permissionUseNo" text="Permission Use No." /></th>
								<th width="" class=""><spring:message
										code="unitdetails.assessmentRemark" text="Assessment Remark" /></th>
								<th colspan=""><spring:message
										code="taxdetails.adjustArrears" text="Adjust Arrears" /></th>
								<%-- <th width="6%"><spring:message code="unit.ViewMoreDetails" /></th> --%>
							</tr>


							<c:forEach var="unitDetails"
								items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}"
								varStatus="status">
								<tr>
									<td class="text-center">${unitDetails.proFaYearIdDesc}</td>
									<td class="text-center">${unitDetails.assdUnitNo}</td>
									<td class="text-center">${unitDetails.flatNo}</td>
									<td class="text-center">${unitDetails.proFloorNo}</td>
									<td class="text-center">${unitDetails.proAssdConstructionDate}</td>
									<td class="text-center">${unitDetails.proAssdConstruTypeDesc}</td>
									<td class="text-center">${unitDetails.proAssdUsagetypeDesc}
									</td>
									<c:if test="${unitDetails.proAssdUsagetypeDesc2 ne null}">
										<td class="text-center">${unitDetails.proAssdUsagetypeDesc2}
										</td>
									</c:if>
									<c:if test="${unitDetails.proAssdUsagetypeDesc3 ne null}">
										<td class="text-center">${unitDetails.proAssdUsagetypeDesc3}
										</td>
									</c:if>

									<c:if test="${unitDetails.proAssdUsagetypeDesc4 ne null}">
										<td class="text-center">${unitDetails.proAssdUsagetypeDesc4}
										</td>
									</c:if>
									<c:if test="${unitDetails.proAssdUsagetypeDesc5 ne null}">
										<td class="text-center">${unitDetails.proAssdUsagetypeDesc5}
										</td>
									</c:if>
									<td class="text-center">${unitDetails.carpetArea}</td>
									<td class="text-center">${unitDetails.assdBuildupArea}</td>
									<td class="text-center">${unitDetails.constructPermissionNo}</td>
									<td class="text-center">${unitDetails.permissionUseNo}</td>
									<td class="text-center">${unitDetails.assessmentRemark}</td>
									<td><input type="button"
										id="addArrearForFlat${status.count-1}"
										class="btn btn-success btn-submit addArrearClass"
										onclick="addArrearDetailsForFlat(${status.count-1},this)"
										value="Arrears"></td>
									<%-- <td class="text-center"><a
										class="clickable btn btn-success btn-xs click_advance"
										data-toggle="collapse"
										data-target="#group-of-rows-${status.count-1}"
										aria-expanded="false"
										aria-controls="group-of-rows-${status.count-1}"><i
											class="fa fa-caret-down" aria-hidden="true"></i></a></td> --%>
								</tr>

								<%-- 								<tr class="secondUnitRow collapse in previewTr hideDetails"
									id="group-of-rows-${status.count-1}">
									<td colspan="20"><legend class="text-left">
											<spring:message code="unitdetails.AdditionalUnitDetails"></spring:message>
										</legend>

										<div
											class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
											<div class="form-group">
												<label for="occupancy"
													class="col-sm-2 control-label required-control"><spring:message
														code="unitdetails.occupancy" /> </label>
												<div class="col-sm-4">
													<c:set var="baseLookupCode" value="OCS" />
													<apptags:lookupField
														items="${command.getLevelData(baseLookupCode)}"
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdOccupancyType"
														cssClass="form-control changeParameterClass "
														hasChildLookup="false" hasId="true" showAll="false"
														disabled="true"
														selectOptionLabelCode="prop.selectSelectOccupancyType"
														isMandatory="true" hasTableForm="true"
														changeHandler="loadOccupierName(this)" />
												</div>

												<apptags:lookupFieldSet baseLookupCode="PTP" hasId="true"
													showOnlyLabel="false"
													pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfproperty"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true" disabled="true"
													cssClass="form-control " showAll="false" columnWidth="5%" />

											</div>
											<div class="form-group">
												<label for="occupierName" class="col-sm-2 control-label"><spring:message
														code="unitdetails.OccupierName" /></label>
												<div class="col-sm-4">
													<form:input
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName"
														type="text" class="form-control" id="occupierName"
														disabled="true" />
												</div>

												<label for="occupier-name" class="col-sm-2 control-label"><spring:message
														code="unitdetails.occupierNameReg" /></label>
												<div class="col-sm-4">
													<form:input
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierNameReg"
														type="text" class="form-control  preventSpace"
														id="occupierNameReg" disabled="true" />
												</div>
											</div>


											<div class="form-group">

												<label for="occupierMobNo" class="col-sm-2 control-label"><spring:message
														code="unitdetails.occupierMobNo" /></label>
												<div class="col-sm-4">
													<form:input
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierMobNo"
														type="text" class="form-control hasMobileNo  preventSpace"
														id="occupierMobNo" disabled="true" />
												</div>

												<label for="occupierEmail" class="col-sm-2 control-label"><spring:message
														code="unitdetails.occupierEmail" /></label>
												<div class="col-sm-4">
													<form:input
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierEmail"
														type="text"
														class="form-control hasemailclass  preventSpace"
														id="occupierEmail" disabled="true" />
												</div>
											</div>
										</div></td>
								</tr> --%>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- Approval Buttons -->
				<%-- <div class="form-group">
					<apptags:CheckerAction showInitiator="true" hideForward="true"
						hideSendback="true"></apptags:CheckerAction>
				</div> --%>
				<div class="text-center padding-bottom-20">

					<c:if
						test="${command.getProvisionalAssesmentMstDto().getBillMethodChngFlag() ne 'Y'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="displayMessageOnSubmit(this)" id="submit">
							<spring:message code="bt.save" text="Submit" />
						</button>
					</c:if>

					<c:set var="appId" value="${command.lableValueDTO.applicationId}" />
					<c:set var="labelsId" value="${command.lableValueDTO.lableId}" />
					<c:set var="servicesId" value="${command.serviceId}" />
					<form:hidden path="lableValueDTO.applicationId"
						value="${command.lableValueDTO.applicationId}" />
					<form:hidden path="lableValueDTO.lableId"
						value="${command.lableValueDTO.lableId}" />
					<form:hidden path="serviceId" value="${command.serviceId}" />

					<input type="button"
						onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${appId}','${labelsId}','${servicesId}')"
						class="btn btn-danger"
						value="<spring:message code="water.btn.back"/>">
				</div>

			</form:form>

		</div>
	</div>
</div>


