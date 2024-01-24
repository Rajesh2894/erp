<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/newWaterConnectionForm.js"></script>
<script type="text/javascript" src="js/water/newWaterDetails.js"></script>
<!-- Start Content here -->
<style>
#printapplication h2 {
	font-size: 1em;
	background: #dcdcdc;
	padding-left: 5px;
	font-weight: 600;
}
</style>

<script>
	function printAppliactionDiv(divName) {
		var printContents = document.getElementById(divName).innerHTML;
		var originalContents = document.body.innerHTML;
		document.body.innerHTML = printContents;
		window.print();
		document.body.innerHTML = originalContents;
	}
</script>
<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">

			<div class="widget-content padding">

				<form:form action="NewWaterConnectionForm.html"
					class="form-horizontal form" name="frmNewWaterFormViewApplication"
					id="frmNewWaterFormViewApplication">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div id="printapplication">

						<%-- <h2>
							<spring:message code="water.consumer.details" />
						</h2>
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label "
								for="billingPinCode"><spring:message code="prop.number" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.propertyNo}</span>
							</div>
							<label class="col-sm-2 col-xs-2 control-label "
								for="billingPinCode"><spring:message
									code="water.outStanding" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.totalOutsatandingAmt}</span>
							</div>
						</div> --%>
							<%-- <div class="form-group">
							<c:if test="${not empty command.csmrInfo.propertyUsageType}">
								<label class="col-sm-2 control-label"><spring:message
										code="water.propert.usageType" text="Property Usage Type" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.propertyUsageType}</span>
								</div>
								</c:if>
								<c:if test="${not empty command.csmrInfo.occupancyType}">
								<label class="col-sm-2 control-label"><spring:message
										code="water.property.occupancyType" text="Property Occupancy Type" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.occupancyType}</span>
								</div>
								</c:if>
							</div>
						<div class="clear"></div> --%>
						
						<h2>
							<spring:message code="water.owner.details.consumerAddress"
								text="Consumer Details" />
						</h2>
						
						<div class="form-group">
							<c:if test="${command.sudaEnv eq 'Y'}">
								<label class="col-sm-2 col-xs-2 control-label" for="csIsBpl"><spring:message
									code="water.connectiontype" text="Connection Type" /></label>
								<div class="col-sm-4 col-xs-4">
									<c:if test="${command.csmrInfo.bplFlag eq 'Y'}">
										<span><spring:message code="water.bhagirathi.conn" text="Bhagirathi Connection" /></span>
									</c:if>
									<c:if test="${command.csmrInfo.bplFlag ne 'Y'}">
										<span><spring:message code="water.normal" text="Normal" /></span>
									</c:if>
								</div>
							</c:if>
							<c:if test="${command.sudaEnv ne 'Y'}">
								<label class="col-sm-2 col-xs-2 control-label" for="csIsBpl"><spring:message
									code="water.dataentry.bhagirathi.connection" text="Is Bhagirathi Connection?" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.bplFlag}</span>
								</div>										
							</c:if>
							
							
							<c:if test="${command.csmrInfo.bplFlag eq 'Y'}">
								 <label class="col-sm-2 col-xs-2 control-label" for="csIsBpl"><spring:message
										code="applicantinfo.label.bplno" text="BPL Number" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.bplNo}</span>
								</div> 
							</c:if>
							
						</div>
						<div class="clear"></div>
						
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="csName"><spring:message
									code="water.road.applName" text="Consumer Name" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csName}</span>
							</div>
							
							<label class="col-sm-2 col-xs-2 control-label" for="cSfatherGuardian"><spring:message
									code="water.dataentry.fatherName" text="Father/Guardian Name" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.fatherName}</span>
							</div>							
							
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="csGender"><spring:message
									code="applicantinfo.label.gender" text="Gender" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.genderDesc}</span>
							</div> 
							<label class="col-sm-2 col-xs-2 control-label" for="cShouseNo"><spring:message
									code="water.dataentry.HouseNo" text="House Number" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.houseNumber}</span>
							</div>
						</div>
						<div class="clear"></div>
						
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="cbillingAreaName"><spring:message
									code="water.dataentry.address" text="Address" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csAdd}</span>
							</div>
							<%-- <c:if test="${command.sudaEnv eq 'Y'}"> --%>
							<label class="col-sm-2 control-label required-control"
									for="csDistrict"><spring:message code="water.dataentry.district"
										text="District" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csDistrictDesc}</span>
							</div> 
							<%-- </c:if> --%>
						</div>
						<div class="clear"></div>
						
						<div class="form-group">
						<label class="col-sm-2 col-xs-2 control-label"
								for="cbillingPinCode"><spring:message
									code="water.pincode" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csCpinCode}</span>

							</div>
							<label class="col-sm-2 col-xs-2 control-label" for="cSLandmark"><spring:message
									code="water.dataentry.landmark" text="Landmark" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.landmark}</span>
							</div>
							
						</div>
						<div class="clear"></div>
						
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="csMobileNo"><spring:message
									code="applicantinfo.label.mobile" text="Mobile Number" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csContactno}</span>
							</div>
							<label class="col-sm-2 col-xs-2 control-label" for="csEmailId"><spring:message
									code="applicantinfo.label.email " text="Email" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csEmail}</span>
							</div>
						</div>
						<div class="clear"></div>
						
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="cSaadharNo"><spring:message
									code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.aadharBilling}</span>
							</div>
							
						</div>
						<div class="clear"></div>
						
						<div class="form-group">
							<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
								showOnlyLabel="true" pathPrefix="csmrInfo.codDwzid"
								isMandatory="false" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true" disabled="${disabled}"
								cssClass="form-control col-xs-4" />
						</div>
						
						<%-- div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="csName"><spring:message
									code="water.Ward" text="Ward" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.reqDTO.ward}</span>
							</div>
							
						</div> --%>
						<div class="clear"></div> 
						
						 <div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="csName"><spring:message
									code="water.Category" text="Category" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.reqDTO.category}</span>
							</div>
							<label class="col-sm-2 col-xs-2 control-label" for="csName"><spring:message
									code="water.SubCategory" text="Sub Category" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.reqDTO.subCategory}</span>
							</div>
						</div>
						<div class="clear"></div> 
						
						<c:if test="${command.csmrInfo.typeOfApplication ne null and command.csmrInfo.typeOfApplication eq 'T'}">
							<div class="form-group">
								<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
									showOnlyLabel="true" pathPrefix="csmrInfo.trmGroup"
									isMandatory="false" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control col-xs-4" />
							</div>
							<div class="form-group">

								<label class="col-sm-2 col-xs-2 control-label "><spring:message
										code="water.fromPeriod" /></label>
								<div class="col-sm-4 col-xs-4">
									<div class="input-group">
										<c:set var="fromDate" value="${command.csmrInfo.fromDate}" />
										<span><fmt:formatDate pattern="dd/MM/yyyy"
												value="${fromDate}" /></span>

									</div>
								</div>

								<label class="col-sm-2 col-xs-2 control-label "><spring:message
										code="water.toPeriod" /></label>

								<div class="col-sm-4 col-xs-4">
									<div class="input-group">
										<c:set var="todAte" value="${command.csmrInfo.toDate}" />
										<span><fmt:formatDate pattern="dd/MM/yyyy"
												value="${todAte}" /></span>
									</div>


								</div>

							</div>
							<div class="clear"></div>
						</c:if>

						<div class="clear"></div>
						
						

						


						<%-- <h2>
							<spring:message code="applicantinfo.label.header" />
						</h2>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label " for="firstName"><spring:message
									code="water.road.applName" text="Applicant Name" /></label>
							<div class="col-sm-4 col-xs-4">
							<c:choose>
							<c:when test="${not empty command.csmrInfo.csName}">
							<span>${command.csmrInfo.csName}</span>
							</c:when>
							<c:otherwise><span>${command.applicantDetailDto.applicantFirstName}</span></c:otherwise>
							</c:choose>
							</div>  
							<label class="col-sm-2 col-xs-2 control-label "><spring:message
									code="applicantinfo.label.mobile" /></label>
							<div class="col-sm-4 col-xs-4">
							<c:choose>
							<c:when test="${not empty command.csmrInfo.csContactno}">
							<span>${command.csmrInfo.csContactno}</span>
							</c:when>
							<c:otherwise><span>${command.applicantDetailDto.mobileNo}</span></c:otherwise>
							</c:choose>
								
							</div>
						</div>
						
						<div class="form-group">
						<label class="col-sm-2 col-xs-2 control-label " for="csAdd"><spring:message
									code="water.dataentry.Address" text="Address" /></label>
							<div class="col-sm-4 col-xs-4">
						
						<c:choose>
						<c:when test="${not empty command.csmrInfo.csAdd}">
						<span>${command.csmrInfo.csAdd}</span>
						</c:when>
						<c:otherwise>
						<c:choose>
						<c:when test="${empty command.csmrInfo.csAdd && not empty command.csmrInfo.csBadd}">
						<span>${command.csmrInfo.csBadd}</span>
						</c:when>
						<c:otherwise><span>${command.csmrInfo.csOadd}</span></c:otherwise>
						</c:choose>
						</c:otherwise>
						</c:choose>
							</div>
							<label class="col-sm-2  col-xs-2 control-label "><spring:message
									code="applicantinfo.label.pincode" /></label>
							<div class="col-sm-4 col-xs-4">
							
						<c:choose>
						<c:when test="${not empty command.csmrInfo.csCpinCode}">
						<span>${command.csmrInfo.csCpinCode}</span>
						</c:when>
						<c:otherwise>
						<c:choose>
						<c:when test="${empty command.csmrInfo.csCpinCode && not empty command.csmrInfo.bpincode}">
						<span>${command.csmrInfo.bpincode}</span>
						</c:when>
						<c:otherwise><span>${command.csmrInfo.opincode}</span></c:otherwise>
						</c:choose>
						</c:otherwise>
						</c:choose>
							</div>
						</div>
						<div class="clear"></div>

						<div class="form-group">
						<c:choose>
						<c:when test="${not empty command.csmrInfo.csEmail}">
						<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.csEmail}</span>
								</div>
						</c:when>
						<c:otherwise>
						<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.applicantDetailDto.emailId}</span>
								</div>
						</c:otherwise>
						</c:choose>
							
								<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="applicantinfo.label.gender" /></label>
								<div class="col-sm-4 col-xs-4">
								<c:choose>
								<c:when test="${not empty command.csmrInfo.csOGender}"><span>${command.csmrInfo.genderDesc}</span></c:when>
								<c:otherwise>${command.applicantDetailDto.gender}</c:otherwise>
								</c:choose>
								</div>
						</div>
						<div class="clear"></div>
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="applicantinfo.label.isabovepovertyline" /></label>
							<div class="col-sm-4 col-xs-4">
								<c:if test="${command.csmrInfo.bplFlag eq 'Y'}">
									<span><spring:message code="applicantinfo.label.yes" /></span>
								</c:if>
								<c:if test="${command.csmrInfo.bplFlag eq 'N'}">
									<span><spring:message code="applicantinfo.label.no" /></span>
								</c:if>
							</div>
							<c:if test="${command.csmrInfo.bplFlag eq 'Y'}">
								<label class="col-sm-2 col-xs-2 control-label "><spring:message
										code="applicantinfo.label.bplno" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.bplNo}</span>

								</div>
							</c:if>
							
						</div>
						<div class="clear"></div>
						<div class="form-group">
							<c:if test="${not empty command.csmrInfo.csUid}">
								<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="applicantinfo.label.aadhaar" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.csUid}</span>
								</div>
							</c:if>

						</div>
						<div class="clear"></div>
						<div class="form-group">
							<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
								showOnlyLabel="true" pathPrefix="csmrInfo.codDwzid"
								isMandatory="false" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true" disabled="${disabled}"
								cssClass="form-control col-xs-4" />
						</div>
						<div class="clear"></div>

						 --%>

						<h2>
							<spring:message code="water.connectiondetails" />
						</h2>
						
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
							<div class="clear"></div>
						</c:if>
						
						
						<div class="form-group">

							<label class=" col-sm-2  col-xs-2 control-label" for="taxPayer"><spring:message
									code="taxPayer" /> </label>
							<div class="col-sm-4 col-xs-4">
								<c:choose>
									<c:when test="${command.csmrInfo.csTaxPayerFlag eq 'Y'}">
										<span><spring:message code="applicantinfo.label.yes" /></span>
									</c:when>
									<c:otherwise>
										<span><spring:message code="applicantinfo.label.no" /></span>
									</c:otherwise>
								</c:choose>

							</div>
							
							<c:if test="${command.csmrInfo.csTaxPayerFlag eq 'Y'}">
								<label class=" col-sm-2  col-xs-2 control-label" for="taxPayer"><spring:message
									code="taxPayer" /> </label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.csPanNo}</span>
								</div>
							</c:if>
							

						</div>
						<div class="clear"></div>
						
						<%-- <div class="form-group">

							<label class="col-sm-2 col-xs-2 control-label"> <spring:message
									code="water.typeOfApplication" />
							</label>
							<div class="col-sm-4 col-xs-4">


								<c:choose>
									<c:when test="${command.csmrInfo.typeOfApplication eq 'P'}">
										<span><spring:message code="water.perm" /></span>
									</c:when>
									<c:otherwise>
										<span><spring:message code="water.temp" /></span>
									</c:otherwise>
								</c:choose>

							</div>

						</div>
						<div class="clear"></div>
 --%>
						
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="water.tarrif.category" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.reqDTO.tarrifCategory}</span>
							</div>

						</div>
						<div class="clear"></div>
						
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="water.connection.size" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.reqDTO.connSize}</span>
							</div>

						</div>
						<div class="clear"></div>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="water.plumber.details" /></label>
							<div class="col-sm-4 col-xs-4">
								<c:choose>
									<c:when test="${command.csmrInfo.csPtype eq 'U'}">
										<span><spring:message code="water.plumber.reg" /></span>
									</c:when>
									<c:otherwise>
										<span><spring:message code="water.plumber.notreg" /></span>
									</c:otherwise>
								</c:choose>

							</div>
							
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="water.plumber.name" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.reqDTO.plumberName}</span>

							</div>

						</div>
						<div class="clear"></div>

					</div>
					<c:if test="${command.scrutinyFlag ne 'Y'}">
						<div class="panel-group accordion-toggle"
							id="accordion_single_collapse">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class="collapsed"
											data-parent="#accordion_single_collapse"
											href="#DocumentDetails"> <spring:message
												code="uploade.doc" />
										</a>
									</h4>
								</div>
								<div id="DocumentDetails" class="panel-collapse collapse">
									<div class="panel-body">
										<c:if test="${not empty command.checkListForPreview}">
											<fieldset class="fieldRound">
												<div class="overflow">
													<div class="table-responsive">
														<table
															class="table table-hover table-bordered table-striped">
															<tbody>
																<tr>
																	<th><label class="tbold"><spring:message
																				code="water.serialNo" text="Sr No" /></label></th>
																	<th><label class="tbold"><spring:message
																				code="water.docName" text="Document Name" /></label></th>
																	<th><label class="tbold"><spring:message
																				code="water.download" /></label></th>
																</tr>

																<c:forEach items="${command.checkListForPreview}"
																	var="lookUp" varStatus="lk">
																	<tr>
																		<td><label>${lk.count}</label></td>
																		<c:choose>
																			<c:when
																				test="${userSession.getCurrent().getLanguageId() eq 1}">
																				<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																			</c:when>
																			<c:otherwise>
																				<td><label>${lookUp.doc_DESC_Mar}</label></td>
																			</c:otherwise>
																		</c:choose>
																		<td><c:set var="links"
																				value="${fn:substringBefore(lookUp.uploadedDocumentPath, lookUp.documentName)}" />
																			<apptags:filedownload
																				filename="${lookUp.documentName}"
																				filePath="${lookUp.uploadedDocumentPath}"
																				actionUrl="NewWaterConnectionForm.html?previewDownload"></apptags:filedownload>
																		</td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</div>
												</div>
											</fieldset>
										</c:if>
									</div>
								</div>
							</div>


							<form:hidden path="free" id="free" />
							<div id="payment">
								<c:if test="${command.free eq 'N'}">
									<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
									<div class="form-group margin-top-10">
										<label class="col-sm-2 control-label"> <spring:message
												code="water.field.name.amounttopay" />
										</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												value="${command.offlineDTO.amountToShow}" maxlength="12"
												readonly="true" /> <a
												class="fancybox fancybox.ajax text-small text-info"
												href="NewWaterConnectionForm.html?showChargeDetails"> <spring:message
													code="water.field.name.amounttopay" /> <i
												class="fa fa-question-circle "></i>
											</a>
										</div>
									</div>
								</c:if>
							</div>

						</div>

						<p>
							<spring:message code="Note.selfdeclaration.newwater" />
						</p>


						<div class="form-group padding-top-10">
							<div class="col-sm-12">
								<label class="checkbox-inline"><input name=""
									type="checkbox" value="Y" id="accept"> <strong><spring:message
											code="accept.termCondition" /></strong></label>
							</div>
						</div>




						<div class="text-center" id="divSubmit">
							<button type="button" class="btn btn-success btn-submit"
								onclick="saveWaterForm(frmNewWaterFormViewApplication)" id="submit">
								<spring:message code="water.btn.submit" />
							</button>

							<input type="button" class="btn btn-warning"
								onclick="editApplicationForm(this)"
								value="<spring:message code="edit.app"/>" /> <input
								type="button" class="btn btn-blue-2"
								onclick="printAppliactionDiv('printapplication')"
								value="<spring:message code="print.app"/>" /> <input
								type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'"
								value="<spring:message code="water.btn.cancel"/>" />

						</div>

					</c:if>




					<c:if test="${command.scrutinyFlag eq 'Y'}">
						<c:if test="${not empty command.documentList}">
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="water.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="water.docName" text="Document Name" /></label></th>
													<th><label class="tbold"><spring:message
																code="water.download" /></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														<td><c:set var="links"
																value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<apptags:filedownload filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																dmsDocId="${lookUp.dmsDocId}"
																actionUrl="NewWaterConnectionForm.html?Download"></apptags:filedownload>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
						</c:if>



						<div class="text-center padding-top-10">
							<input type="button" class="btn btn-danger"
								onclick="editApplicationForm(this)"
								value="<spring:message code="edit.app"/>" />
						</div>


					</c:if>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form:form>
				<c:if test="${command.scrutinyFlag eq 'Y'}">
					<div id="scrutinyDiv">
						<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
