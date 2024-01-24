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
<script type="text/javascript" src="js/property/mutation.js"></script>
<script type="text/javascript" src="js/property/ownershipDetailsForm.js"></script>

<ol class="breadcrumb">
	<li><a href="AdminHome.html"><spring:message code="cfc.home"
				text="Home" /></a></li>
	<li><a href="javascript:void(0);"><spring:message
				code="property.property" text="Property tax" /></a></li>
	<li><a href="javascript:void(0);"><spring:message
				code="cfc.transaction" text="Transactions" /></a></li>
	<li class="active"><spring:message text="Mutation Authorization"
			code="propety.mutation.auth" /></li>
</ol>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">

			<h2>
				<strong><spring:message code="mutation.ownership.transfer"
						text="Ownership Transfer" /></strong>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"><span class="hide"><spring:message
								code="property.Help" /></span></i></a>
			</div>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">


			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">*</i> <spring:message
						code="property.ismandatory" /> </span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="MutationFinalApproval.html"
				class="form-horizontal form" name="MutationFinalApproval"
				id="MutationFinalApproval">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="accordion-toggle">


					<!-- End Each Section -->
					<c:if
						test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">

						<!----------------------------------------Existing Owner Details (First owner will be the primary owner)---------------------------->
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
									code="propertyTax.Mutation.ExistOwnerDetails"
									text="Existing Owner Details" /></a>
						</h4>

						<div class="panel-collapse collapse in" id="OwnshipDetail">

							<c:choose>
								<c:when test="${command.getShowFlag() eq 'Y'}">
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
										</tr>
										<tbody>
											<tr>
												<td><form:input id="occupierName"
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].occupierName"
														class="form-control" disabled="true" /></td>

												<td><form:input id="occupierNameReg"
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].occupierNameReg"
														class="form-control" disabled="true" /></td>

												<td><form:input id="occupierMobNo"
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].occupierMobNo"
														class="hasNumber form-control" disabled="true" /></td>

												<td><form:input id="occupierEmail"
														path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].occupierEmail"
														class="hasemailclass form-control" disabled="true" /></td>
											</tr>
										</tbody>
									</table>
								</c:when>
								<c:otherwise>
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
													<th width="15%"><spring:message
															code="ownersdetail.ownersname" /></th>
													<th width="10%"><spring:message
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
													<th width="25%"><spring:message code="property.NameOf" />
														<%-- ${command.getOwnershipTypeValue()} --%></th>
													<th width="15%"><spring:message
															code="ownersdetail.ownersnameReg" /></th>
													<th width="10%"><spring:message
															code="ownersdetail.mobileno" /></th>
													<th width="10%"><spring:message code="property.email" /></th>
													<th width="10%"><spring:message
															code="ownersdetail.pancard" /></th>
												</tr>
												<tbody>
													<tr>
														<td><form:input id="assoOwnerName_${d}"
																path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName"
																class="form-control" disabled="true" /></td>
														<td><form:input id="assoOwnerNameReg_${d}"
																path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerNameReg"
																class="form-control " data-rule-required="true"
																placeholder="Please Enter" maxlength="200"
																disabled="true" /></td>
														<td><form:input id="assoMobileno_${d}"
																path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno"
																class="hasNumber form-control" disabled="true" /></td>

														<td><form:input id="emailId_${d}"
																path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail"
																class="hasemailclass form-control" disabled="true" /></td>
														<td class="companyDetails"><form:input
																id="assoPanno_${d}"
																path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno"
																class="form-control" disabled="true" /></td>
													</tr>
												</tbody>
											</table>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>

					<!----------------------------------------------Existing Property Address Details-------------------------------------------------------->

					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#propertyAddress"><spring:message
								code="propertyTax.Mutation.ExistPropDetails" /></a>
					</h4>
					<div class="panel-collapse collapse in" id="propertyAddress">
						<div class="form-group">
							<label for="assAddress" class="col-sm-2 control-label "><spring:message
									code="property.propertyaddress" text="Property Address" /></label>
							<div class="col-sm-4">
								<form:textarea class="form-control" id="assAddress"
									path="provisionalAssesmentMstDto.assAddress" disabled="true" />
							</div>

							<label for="location" class="col-sm-2 control-label "><spring:message
									code="property.location" /></label>
							<div class="col-sm-4">
								<form:input path="provisionalAssesmentMstDto.locationName"
									type="text" id="location" class="form-control" disabled="true" />
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label " for="proAssPincode"><spring:message
									code="property.pincode" /></label>
							<div class="col-sm-4">
								<form:input class="form-control hasPincode"
									path="provisionalAssesmentMstDto.assPincode" id="proAssPincode"
									maxlength="6" readonly="true"></form:input>
							</div>
							<%-- <label class="col-sm-2 control-label " for="assdAlv"><spring:message
									code="mutation.arv" text="ARV" /></label>
							<div class="col-sm-4">
								<form:input class="form-control"
									path="provisionalAssesmentMstDto.totalArv" id="assdAlv"
									readonly="true"></form:input>
							</div> --%>
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
									path="provisionalAssesmentMstDto.assWardDesc1"
									isDisabled="true"></apptags:input>

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

					<c:choose>
						<c:when test="${command.getOwnershipPrefixNew() eq 'SO'}">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#SoTable"><spring:message
										code="property.previousOwner" /></a>
							</h4>

							<table id="SoTable" class="table table-striped table-bordered ">
								<tbody>
									<tr>
										<th width="20%" class="required-control"><spring:message
												code="ownersdetail.ownersname" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.ownersnameReg" /></th>

										<th width="10%" class="required-control"><spring:message
												code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email" /></th>
										<th width="12%"><spring:message
												code="ownersdetail.adharno" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.pancard" />
									</tr>
								</tbody>
								<tr>
									<td><form:input id="assoOwnerName"
											path="propTransferDto.propTransferOwnerList[0].ownerName"
											class="form-control mandColorClass hasSpecialChara preventSpace"
											data-rule-required="true"
											placeholder="Please Enter Owners Name" maxlength="200" /></td>

									<td><form:input id="assoOwnerNameReg"
											path="propTransferDto.propTransferOwnerList[0].assoOwnerNameReg"
											class="form-control mandColorClass hasSpecialChara preventSpace"
											data-rule-required="true"
											placeholder="Please Enter Owners Name (Reg)" maxlength="200" />
									</td>

									<td><form:input id="assoMobileno"
											path="propTransferDto.propTransferOwnerList[0].mobileno"
											class="hasMobileNo form-control mandColorClass"
											data-rule-minlength="10" maxlength="10"
											data-rule-required="true" placeholder="Please Enter" /></td>

									<td><form:input id="emailId"
											path="propTransferDto.propTransferOwnerList[0].eMail"
											class="hasemailclass form-control preventSpace"
											maxlegnth="254" data-rule-email="true" /></td>

									<td class="ownerDetails"><form:input id="assoAddharno"
											path="propTransferDto.propTransferOwnerList[0].addharno"
											class="form-control  hasAadharNo" maxlength="12"
											data-rule-minlength="12" type="text" /></td>
									<td class="companyDetails"><form:input id="pannumber"
											path="propTransferDto.propTransferOwnerList[0].panno"
											class="form-control text-uppercase hasNoSpace" maxLength="10"
											onchange="fnValidatePAN(this)" /></td>
									<%-- <form:hidden id="assoPrimaryOwn"
										path="propTransferDto.propTransferOwnerList[0].otype"
										value="P" /> --%>
								</tr>
							</table>
						</c:when>

						<c:when test="${command.getOwnershipPrefixNew() eq 'JO'}">
							<c:choose>
								<c:when
									test="${not empty command.getPropTransferDto().getPropTransferOwnerList()}">
									<h4 class="margin-top-0 margin-bottom-10 panel-title">
										<a data-toggle="collapse" href="#jointOwnerTable"><spring:message
												code="property.previousOwner" /></a>
									</h4>

									<table id="joTable"
										class="table text-left table-striped table-bordered">
										<tbody>

											<tr>
												<th width="15%" class="required-control"><spring:message
														code="ownersdetail.ownersname" /></th>
												<th width="13%"><spring:message
														code="ownersdetail.ownersnameReg" /></th>

												<th width="5%"><spring:message
														code="ownerdetails.PropertyShare" /></th>
												<th width="11%" class="required-control"><spring:message
														code="ownersdetail.mobileno" /></th>
												<th width="10%"><spring:message code="property.email" /></th>
												<th width="13%"><spring:message
														code="ownersdetail.adharno" /></th>
												<th width="10%"><spring:message
														code="ownersdetail.pancard" /></th>
												<%-- <th width="8%"><spring:message
														code="property.add/delete" /></th> --%>
											</tr>

											<c:forEach
												items="${command.getPropTransferDto().getPropTransferOwnerList()}"
												varStatus="status">

												<tr class="jointOwner">
													<td><c:set var="d" value="0" scope="page" /> <form:input
															id="assoOwnerName_${status.count-1}"
															path="propTransferDto.propTransferOwnerList[${status.count-1}].ownerName"
															class="form-control mandColorClass hasSpecialChara preventSpace"
															data-rule-required="true" placeholder="Please Enter"
															maxlength="200" /></td>

													<td><form:input
															id="assoOwnerNameReg_${status.count-1}"
															path="propTransferDto.propTransferOwnerList[${status.count-1}].assoOwnerNameReg"
															class="form-control mandColorClass hasSpecialChara preventSpace"
															data-rule-required="true" placeholder="Please Enter"
															maxlength="200" /></td>

													<td><form:input
															id="assoPropertyShare_${status.count-1}"
															path="propTransferDto.propTransferOwnerList[${status.count-1}].propertyShare"
															class="form-control mandColorClass hasNumber"
															maxlength="3" /></td>

													<td><form:input id="assoMobileno_${status.count-1}"
															path="propTransferDto.propTransferOwnerList[${status.count-1}].mobileno"
															class="hasMobileNo form-control mandColorClass"
															data-rule-maxlength="10" data-rule-minlength="10"
															maxlength="10" data-rule-required="true"
															placeholder="Please Enter" /></td>

													<td><form:input id="emailId_${status.count-1}"
															path="propTransferDto.propTransferOwnerList[${status.count-1}].eMail"
															class="hasemailclass form-control preventSpace"
															maxlegnth="254" data-rule-email="true" /></td>

													<td class="ownerDetails"><form:input
															id="assoAddharno_${status.count-1}"
															path="propTransferDto.propTransferOwnerList[${status.count-1}].addharno"
															class="form-control  hasAadharNo" maxlength="12"
															data-rule-maxlength="12" data-rule-minlength="12" /></td>
													<td class="companyDetails"><form:input
															id="pannumber${status.count-1}"
															path="propTransferDto.propTransferOwnerList[${status.count-1}].panno"
															class="form-control hasPanno text-uppercase hasNoSpace"
															maxlength="10" onchange="fnValidatePAN(this)" /></td>

													<%-- <td id="action"><a href="javascript:void(0);"
														title="Add" class="addOwner btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a> <a
														href="javascript:void(0);" title="Delete"
														class="remOwner btn btn-danger btn-sm"
														id="deleteOwnerRow_${status.count-1}"><i
															class="fa fa-trash-o"></i></a></td> --%>
													<%-- <form:hidden id="assoPrimaryOwn"
														path="propTransferDto.propTransferOwnerList[${status.count-1}].otype"
														value="P" /> --%>

												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:when>

								<c:otherwise>
									<h4 class="margin-top-0 margin-bottom-10 panel-title">
										<a data-toggle="collapse" href="#jointOwnerTable"><spring:message
												code="property.previousOwner" /></a>
									</h4>

									<table id="joTable"
										class="table text-left table-striped table-bordered">
										<tbody>
											<tr>
												<th width="15%" class="required-control"><spring:message
														code="ownersdetail.ownersname" /></th>
												<th width="10%" class="required-control"><spring:message
														code="ownersdetail.ownersnameReg" /></th>
												<th width="5%"><spring:message
														code="ownerdetails.PropertyShare" /></th>
												<th width="11%" class="required-control"><spring:message
														code="ownersdetail.mobileno" /></th>
												<th width="10%"><spring:message code="property.email" /></th>
												<th width="13%"><spring:message
														code="ownersdetail.adharno" /></th>
												<th width="10%"><spring:message
														code="ownersdetail.pancard" /></th>
												<%-- <th width="8%"><spring:message
														code="property.add/delete" /></th> --%>
											</tr>

											<tr class="jointOwner">
												<td><c:set var="d" value="0" scope="page" /> <form:input
														id="assoOwnerName_${d}"
														path="propTransferDto.propTransferOwnerList[0].ownerName"
														class="form-control mandColorClass hasSpecialChara preventSpace"
														data-rule-required="true" placeholder="Please Enter"
														maxlength="200" /></td>

												<td><form:input id="assoOwnerNameReg_${d}"
														path="propTransferDto.propTransferOwnerList[0].assoOwnerNameReg"
														class="form-control mandColorClass hasSpecialChara preventSpace"
														data-rule-required="true" placeholder="Please Enter"
														maxlength="200" /></td>

												<td><form:input id="assoPropertyShare_${d}"
														path="propTransferDto.propTransferOwnerList[0].propertyShare"
														class="form-control mandColorClass hasNumber"
														maxlength="3" /></td>

												<td><form:input id="assoMobileno_${d}"
														path="propTransferDto.propTransferOwnerList[0].mobileno"
														class="hasMobileNo form-control mandColorClass"
														data-rule-maxlength="10" data-rule-minlength="10"
														maxlength="10" data-rule-required="true"
														placeholder="Please Enter" /></td>

												<td><form:input id="emailId_${d}"
														path="propTransferDto.propTransferOwnerList[0].eMail"
														class="hasemailclass form-control preventSpace"
														maxlegnth="254" data-rule-email="true" /></td>

												<td class="ownerDetails"><form:input
														id="assoAddharno_${d}"
														path="propTransferDto.propTransferOwnerList[0].addharno"
														class="form-control  hasAadharNo" maxlength="12"
														data-rule-maxlength="12" data-rule-minlength="12" /></td>
												<td class="companyDetails"><form:input
														id="pannumber${d}"
														path="propTransferDto.propTransferOwnerList[0].panno"
														class="form-control hasPanno text-uppercase hasNoSpace"
														maxlength="10" onchange="fnValidatePAN(this)" /></td>

												<!-- <td><a href="javascript:void(0);" title="Add"
													class="addOwner btn btn-success btn-sm"><i
														class="fa fa-plus-circle"></i></a> <a
													href="javascript:void(0);" title="Delete"
													class="remOwner btn btn-danger btn-sm"
													id="deleteOwnerRow_0"><i class="fa fa-trash-o"></i></a></td> -->
												<%-- <form:hidden id="assoPrimaryOwn"
													path="propTransferDto.propTransferOwnerList[0].otype"
													value="P" /> --%>

												<form:hidden path="ownerDetailTableCount" id="ownerDetail" />

											</tr>
										</tbody>
									</table>
								</c:otherwise>
							</c:choose>
						</c:when>

						<c:otherwise>

							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#OtherTable"><spring:message
										code="property.previousOwner" /></a>
							</h4>

							<table id="OtherTable"
								class="table text-left table-striped table-bordered">

								<tbody>
									<tr>
										<th width="20%" class="required-control"><spring:message
												code="ownersdetail.ownersname" /></th>
										<th width="20%" class="required-control"><spring:message
												code="ownersdetail.ownersnameReg" /></th>
										<th width="15%" class="required-control"><spring:message
												code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email" /></th>
										<th width="15%"><spring:message
												code="ownersdetail.pancard" /></th>
									</tr>

									<tr>
										<td><form:input id="assoOwnerName"
												path="propTransferDto.propTransferOwnerList[0].ownerName"
												class="form-control mandColorClass hasSpecialChara preventSpace"
												data-rule-required="true" placeholder="Please Enter"
												maxlength="200" /></td>

										<td><form:input id="assoOwnerNameReg"
												path="propTransferDto.propTransferOwnerList[0].assoOwnerNameReg"
												class="form-control mandColorClass hasSpecialChara preventSpace"
												data-rule-required="true" placeholder="Please Enter"
												maxlength="200" /></td>

										<td><form:input id="assoMobileno"
												path="propTransferDto.propTransferOwnerList[0].mobileno"
												class="hasMobileNo form-control mandColorClass"
												data-rule-maxlength="10" data-rule-minlength="10"
												maxlength="10" data-rule-required="true"
												placeholder="Please Enter" /></td>

										<td><form:input id="emailId"
												path="propTransferDto.propTransferOwnerList[0].eMail"
												class="hasemailclass form-control preventSpace"
												maxlegnth="254" data-rule-email="true" /></td>

										<td class="companyDetails"><form:input id="pannumber"
												path="propTransferDto.propTransferOwnerList[0].panno"
												class="form-control hasPanno text-uppercase preventSpace"
												maxlength="10" onchange="fnValidatePAN(this)" /></td>
										<%-- <form:hidden id="assoPrimaryOwn"
											path="propTransferDto.propTransferOwnerList[0].otype"
											value="P" /> --%>
									</tr>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>

					<!-- Approve , Reject buttons -->
					<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>

					<div class="text-center padding-top-10">
						<button type="button" class="btn btn-blue-2" id="serchBtn"
							onclick="saveMutationAfterApproval(this)">
							<spring:message code="bt.save" text="Submit" />
						</button>
						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="bt.backBtn" text="Back"></spring:message>
						</button>
					</div>

					<!--  End button -->
				</div>
			</form:form>

		</div>

	</div>
</div>
<!-- End of Content -->
<!-- End Form -->


