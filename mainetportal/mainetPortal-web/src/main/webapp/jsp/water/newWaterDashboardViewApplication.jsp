<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/water/newWaterConnectionForm.js"></script>
<script>



</script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="water.WaterConnection" />
				</h2>
				<apptags:helpDoc url="NewWaterConnectionForm.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				<form:form action="NewWaterConnectionForm.html"
					class="form-horizontal form" name="frmNewWaterForm"
					id="frmNewWaterForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="isBillingSame" id="hiddenBillingSame" />
					<form:hidden path="isConsumerSame" id="hiddenConsumerSame" />
					<form:hidden path="" id="propOutStanding"
						value="${command.propOutStanding}" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">



						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 id="propertyDetailsDiv" class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#waterpropertydetails"> <spring:message
											code="water.dataentry.property.details"
											text="Property Details" />
									</a>
								</h4>
							</div>
							<div id="waterpropertydetails">
								<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.dataentry.property.number"
												text="Property number" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												onblur="getPropertyDetails(this)" path="reqDTO.propertyNo"
												id="propertyNo" readonly="" data-rule-required="true"
												disabled="true"></form:input>
										</div>

										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.bhagirathi.connection"
												text="Is Bhagirathi connection" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.bplFlag" disabled="true"
												class="form-control changeParameterClass" id="bplNo"
												data-rule-required="true" onchange="getConnectionSize();">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="Y">
													<spring:message code="water.yes" text="Yes" />
												</form:option>
												<form:option value="N">
													<spring:message code="water.no" text="No" />
												</form:option>
											</form:select>
										</div>

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="Property OutStanding Amount" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control text-right"
												path="csmrInfo.totalOutsatandingAmt" id="" disabled="true"></form:input>
										</div>
									</div>
								</div>
							</div>
						</div>


						<!--Connection Details-->

						<%-- <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail> --%>
						<%-- 				<jsp:include page="/jsp/mainet/applicantDetails.jsp"></jsp:include> --%>

						<div class="panel panel-default OwnerDetails">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#OwnerDetails">
										<spring:message code="water.dataentry.owner.details"
											text="Owner Details" />
									</a>
								</h4>
							</div>
							<div id="OwnerDetails">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csOname"><spring:message
												code="water.dataentry.owner.name" text="Owner Name" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="csmrInfo.csOname" id="csOname"
												data-rule-required="true" disabled="true"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="gender"><spring:message
												code="applicantinfo.label.gender" /></label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csOGender" cssClass="form-control"
											disabled="true" hasChildLookup="false" hasId="true"
											showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="false" />
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csOadd"><spring:message
												code="water.dataentry.Address" text="Address" /></label>
										<div class="col-sm-4">
											<form:textarea name="" type="text" class="form-control "
												path="csmrInfo.csOadd" id="csOadd" data-rule-required="true"
												disabled="true"></form:textarea>
										</div>

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="opincode"><spring:message code="water.pincode" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text"
												class="form-control hasNumber hideElement"
												path="csmrInfo.opincode" id="opincode" maxlength="6"
												data-rule-required="true" disabled="true"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="adharNo"><spring:message
												code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="reqDTO.applicantDTO.aadharNo" id="aadharNo"
												maxlength="12" disabled="true"></form:input>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="mobileNo"><spring:message
												code="applicantinfo.label.mobile" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.csOcontactno" id="mobileNo"
												data-rule-required="true" data-rule-minlength="10"
												data-rule-maxlength="10" disabled="true"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="emailId"><spring:message
												code="applicantinfo.label.email" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.csOEmail" id="emailId" data-rule-email="true"
												disabled="true"></form:input>
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
										href="#ConsumerDetails"> <spring:message
											code="water.owner.details.consumerAddress"
											text="Consumer Details" />
									</a>
								</h4>
							</div>
							<div id="ConsumerDetails" class="panel-collapse">
								<div class="panel-body">
									<div class="form-group">

										<div class="col-sm-6">
											<label class="checkbox-inline" for="isConsumer"> <form:checkbox
													path="reqDTO.isConsumer" value="Y" id="isConsumer"
													disabled="true" /> <spring:message
													code="water.isConsumerSame"
													text="Is Applicant is Consumer?" />
											</label>
										</div>
									</div>
									<div id="hideConsumerDetails">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="firstName"><spring:message
													code="water.dataentry.consumer.name" text="Consumer Name" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasCharacter" readonly="${disabled}"
													path="csmrInfo.csName" id="csFirstName"
													data-rule-required="true" disabled="true"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="gender"><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="csmrInfo.csGender" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="false" disabled="true" />
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="cbillingAreaName"><spring:message
													code="water.dataentry.address" text="Address" /></label>
											<div class="col-sm-4">
												<form:textarea name="" type="text" class="form-control "
													path="csmrInfo.csAdd" id="csAddress1" disabled="true"></form:textarea>
											</div>

										</div>


										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="cbillingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasPincode hideElement"
													path="csmrInfo.csCpinCode" id="csPinCode" maxlength="6"
													disabled="true"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="cSaadharNo"><spring:message
													code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control"
													path="reqDTO.applicantDTO.aadharNo" id="cSaadharNo"
													maxlength="12" disabled="true"></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="csMobileNo"><spring:message
													code="applicantinfo.label.mobile" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasMobileNo"
													path="csmrInfo.csContactno" id="csMobileNo"
													data-rule-required="true" data-rule-minlength="10"
													data-rule-maxlength="10" disabled="true"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="csEmailId"><spring:message
													code="applicantinfo.label.email" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasemailclass"
													path="csmrInfo.csEmail" id="csEmailId"
													data-rule-email="true" disabled="true"></form:input>
											</div>
										</div>

									</div>


									<div class="form-group">
										<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.codDwzid"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" disabled="true" />
									</div>

								</div>
							</div>
						</div>


						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#BillingDetails"> <spring:message
											code="water.owner.details.buldingadd" />
									</a>
								</h4>
							</div>
							<div id="BillingDetails" class="panel-collapse">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-6">
											<label class="checkbox-inline" for="billing"> <form:checkbox
													path="reqDTO.isBillingAddressSame" value="Billing"
													id="billing" disabled="true" /> <spring:message
													code="water.isBillingSame" />
											</label>
										</div>
									</div>
									<div id="hideBillingDetails">

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="billingAreaName"><spring:message
													code="address.line1" text="Address Line1" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBadd" id="billingAreaName" disabled="true"></form:input>
											</div>

											<label class="col-sm-2 control-label required-control"
												for="billingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasPincode hideElement"
													path="csmrInfo.bpincode" id="billingPinCode" maxlength="6"
													disabled="true"></form:input>
											</div>
										</div>



									</div>
								</div>
							</div>
						</div>


						<div class="panel panel-default ownerDetails">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#additionalOwner"> <spring:message
											code="water.additionalOwner" />
									</a>
								</h4>
							</div>
							<div id="additionalOwner" class="panel-collapse">
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-bordered" id="tbl1">
											<c:choose>
												<c:when test="${empty command.csmrInfo.ownerList}">

												</c:when>
												<c:otherwise>
													<tr>
														<th><spring:message code="water.title" /> <span
															class="mand">*</span></th>
														<th><spring:message code="water.owner.details.fname" />
															<span class="mand">*</span></th>
														<th><spring:message code="water.owner.details.mname" /></th>
														<th><spring:message code="water.owner.details.lname" />
															<span class="mand">*</span></th>
														<th><spring:message code="water.owner.details.gender"
																text="Gender" /> <span class="mand">*</span></th>
														<th><spring:message code="water.owner.details.uid"
																text="Adhar No." /></th>
														<th><a data-toggle="tooltip" data-placement="top"
															title="" class="btn btn-blue-2 btn-sm"
															data-original-title="Add Owner" id="addOwner"><i
																class="fa fa-plus"></i></a></th>
													</tr>
													<c:forEach items="${command.csmrInfo.ownerList}"
														var="details" varStatus="status">
														<tr id="tr${status.count-1}" class="ownerClass">
															<td><c:set var="baseLookupCode" value="TTL" /> <form:select
																	path="csmrInfo.ownerList[${status.count-1}].ownerTitle"
																	class="form-control" id="ownerTitle${status.count-1}"
																	disabled="true">
																	<form:option value="">
																		<spring:message code="water.sel.title" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td><form:input name="" type="text"
																	class="form-control hasCharacter"
																	path="csmrInfo.ownerList[${status.count-1}].ownerFirstName"
																	id="ownerFName${status.count-1}" disabled="true"></form:input></td>
															<td><form:input name="" type="text"
																	class="form-control hasCharacter"
																	path="csmrInfo.ownerList[${status.count-1}].ownerMiddleName"
																	id="ownerMName${status.count-1}" disabled="true"></form:input></td>
															<td><form:input name="" type="text"
																	class="form-control hasCharacter"
																	path="csmrInfo.ownerList[${status.count-1}].ownerLastName"
																	id="ownerLName${status.count-1}"></form:input></td>
															<td><c:set var="baseLookupCode" value="GEN" /> <form:select
																	path="csmrInfo.ownerList[${status.count-1}].gender"
																	class="form-control" id="ownerGender${status.count-1}"
																	disabled="true">
																	<form:option value="">
																		<spring:message code="water.sel.gen" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td><form:input name="" type="text"
																	class="form-control hasNumber"
																	path="csmrInfo.ownerList[${status.count-1}].caoUID"
																	id="ownerUID${status.count-1}" disabled="true"></form:input></td>
															<td><a data-toggle="tooltip" data-placement="top"
																title="" class="btn btn-danger btn-sm"
																data-original-title="Delete Owner" id=deleteOwner
																onclick="removeRow(${status.count-1})"><i
																	class="fa fa-trash"></i></a></td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</table>
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#waterconnectiondetails"> <spring:message
											code="water.connectiondetails" />
									</a>
								</h4>
							</div>
							<div id="waterconnectiondetails" class="panel-collapse">
								<div class="panel-body">



									<div class="form-group">
										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.consumer.type"
												text="Consumer type" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.typeOfApplication"
												class="form-control changeParameterClass"
												id="typeOfApplication" data-rule-required="true"
												disabled="true">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="P">
													<spring:message code="water.perm" text="Permanent" />
												</form:option>
												<form:option value="T">
													<spring:message code="water.temp" text="Temporary" />
												</form:option>
											</form:select>
										</div>

											<div class="col-sm-4">
												<label class="checkbox-inline"> <form:checkbox
														path="reqDTO.existingConsumerNumber"
														value="ExistingConnection" id="ExistingConnection"
														disabled="true" /> <spring:message
														code="water.existing.consumer" />

												</label>
											</div>
									</div>



									<c:if test="${command.csmrInfo.typeOfApplication eq 'T'}">
										<div class="form-group" id="fromtoperiod">

											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.fromPeriod" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input type="text"
														class="form-control Moredatepicker mandColorClass hideElement"
														id="fromdate" path="csmrInfo.fromDate" disabled="true" />
													<label class="input-group-addon" for="fromdate"><i
														class="fa fa-calendar"></i><span class="hide"><spring:message
																code="water.Date" text="Date" /></span></label>
												</div>


											</div>

											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.toPeriod" /></label>

											<div class="col-sm-4">
												<div class="input-group">
													<form:input type="text"
														class="form-control Moredatepicker mandColorClass hideElement"
														id="todate" path="csmrInfo.toDate" disabled="true" />
													<label class="input-group-addon" for="todate"><i
														class="fa fa-calendar"></i><span class="hide"><spring:message
																code="water.Date" text="Date" /></span></label>
												</div>


											</div>

										</div>
									</c:if>

									<div class="form-group">
										<label class="col-sm-2 control-label"> <spring:message
												code="water.dataentry.is.tax.payer"
												text="Is Income Tax Payer" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.csTaxPayerFlag"
												class="form-control changeParameterClass" id="taxPayerFlag"
												disabled="true">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="Y">
													<spring:message code="water.yes" text="Yes" />
												</form:option>
												<form:option value="N">
													<spring:message code="water.no" text="No" />
												</form:option>
											</form:select>
										</div>
										<div id="pandiv1" class="pan_element">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.dataentry.pan.number" text="PAN Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control required-control"
													data-rule-minlength="10" path="reqDTO.applicantDTO.panNo"
													id="panNo" onblur="fnValidatePAN(this)" maxlength="10"
													disabled="true" />
											</div>
										</div>
									</div>

									<div class="form-group ">

										<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.trmGroup"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" disabled="true"
											cssClass="form-control changeParameterClass" />



										<%-- <div id="trans-restaurant" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="water.dataentry.noTable"
													text="No. of Tables" />
											</label>
											<div class="col-sm-4">
												<form:input name="" type="text" disabled="true"
													id="restaurantNumber" class="form-control hasNumber"
													path="csmrInfo.csNoofusers"></form:input>
											</div>
										</div>
 --%>
										<%-- <div id="trans-hotel" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="water.dataentry.noRoom"
													text="No. of rooms" />
											</label>
											<div class="col-sm-4">
												<form:input name="" type="text" disabled="true"
													id="hotelNumber" class="form-control hasNumber"
													path="csmrInfo.csNoofusers"></form:input>
											</div>
										</div> --%>


									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csCcnsize"><spring:message
												code="water.dataentry.connection.size"
												text="Connection Size (in inches)" /></label>

										<div class="col-sm-4" id="notBhagirathi">
											<form:select path="csmrInfo.csCcnsize" class="form-control"
												id="withoutBhagiRathi" disabled="true">
												<c:set var="baseLookupCode" value="CSZ" />
												<form:option value="">
													<spring:message code='master.selectDropDwn' text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookup">
													<form:option value="${lookup.lookUpId}"
														code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="water.plumber.details" /></label>
										<div class="radio col-sm-4">
											<label> <form:radiobutton path="csmrInfo.csPtype"
													value="U" id="ULBRegister" checked="true" /> <spring:message
													code="water.plumber.reg" />
											</label> <label> <form:radiobutton path="csmrInfo.csPtype"
													value="L" id="NotRegister" disabled="true" /> <spring:message
													code="water.plumber.notreg" />
											</label>
										</div>
										<label class="col-sm-2 control-label" for="plumberName"><spring:message
												code="water.plumber.name" text="Plumber Name" /></label>
										<div class="col-sm-4">

											<form:input name="" type="text" class="form-control"
												path="reqDTO.plumberName" id="plumberName" disabled="true"></form:input>
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
										href="#existingConsumerDetails"> <spring:message code=""
											text="Existing Consumer Details" />
									</a>
								</h4>
							</div>
							<c:if test="${not empty command.csmrInfo.linkDetails}">

								<div class="table-responsive">
									<table class="table table-bordered">
										<tr>
											<th><spring:message code="water.ConnectionNo" /> <span
												class="mand">*</span></th>
											<th><spring:message code="water.ConnectionSize" /></th>
											<th><spring:message code="" text="OutStanding Amount" /></th>

										</tr>

										<c:forEach items="${command.csmrInfo.linkDetails}"
											var="details" varStatus="status">
											<c:if test="${details.getIsDeleted() eq 'N'}">
												<tr>
													<td>${command.csmrInfo.linkDetails[status.count-1].lcOldccn}</td>
													<td><c:set var="baseLookupCode" value="CSZ" /> <apptags:lookupField
															items="${command.getLevelData(baseLookupCode)}"
															path="csmrInfo.linkDetails[${status.count-1}].lcOldccnsize"
															cssClass="form-control" hasChildLookup="false"
															hasId="true" showAll="false"
															selectOptionLabelCode="applicantinfo.label.select"
															isMandatory="false" showOnlyLabel="true" /></td>
													<td>
														${command.csmrInfo.linkDetails[status.count-1].ccnOutStandingAmt}</td>

												</tr>
											</c:if>
										</c:forEach>

									</table>
								</div>
							</c:if>
						</div>
						<%-- <c:if test="${not empty command.checkList}"> --%>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 id="applicantDetails" class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#waterformappdetails"><spring:message
											code="water.documentattchmnt" /><small class="text-blue-2">
											<%-- <spring:message
													code="uploadLimitMsg" /> --%>
									</small></a>
								</h4>
							</div>

							<div id="waterformappdetails" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="overflow margin-top-10 margin-bottom-10">
										<div class="table-responsive">
											<table class="table table-bordered table-condensed"
												id="docTable">
												<tr>
													<th width="10%"><spring:message
															code="checklistVerification.srNo" /></th>
													<th width="20%"><spring:message
															code="checklistVerification.document" /></th>
													<th width="15%"><spring:message
															code="checklistVerification.documentStatus" /></th>
													<%-- <th width="30%"><spring:message code="" text="Remarks" /></th> --%>
													<th width="15%"><spring:message
															code="cfc.rejected.doc" /></th>
													<%-- <th width="20%"><spring:message code="cfc.upload.doc" /></th> --%>
												</tr>
												<c:forEach items="${command.checkList}" var="lookUp"
																varStatus="index">
																<tr>

														<td align="center">${index.count}</td>
														<td align="center">${lookUp.doc_DESC_ENGL}</td>


														<td align="center"><apptags:filedownload
																filename="${lookUp.documentName}"
																filePath="${lookUp.uploadedDocumentPath}"
																actionUrl="NewWaterConnectionFormController.html?Download">
															</apptags:filedownload></td>
													</tr>
															</c:forEach>
											</table>
										</div>
									</div>

								</div>
							</div>
						</div>


					</div>


					<div class="form-group text-center padding-bottom-20" id="back">

						<input type="button" class="btn btn-danger"
							onclick="window.location.href='CitizenHome.html'" value="Back">
					</div>


				</form:form>
			</div>
		</div>
	</div>
</div>
