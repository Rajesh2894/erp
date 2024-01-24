<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/challan/offlinePay.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<!-- End JSP Necessary Tags -->
<script type="text/javascript"	src="js/property/propertyNoDuesCertificate.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="duplicate.bill.heading"
						text="Duplicate Bill" /></strong>
			</h2>
			<apptags:helpDoc url="DuplicateBillForm.html"></apptags:helpDoc>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="property.changeInAss" /> </span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="DuplicateBillForm.html"
				class="form-horizontal form" name="PropertyNoDuesCertificate"
				id="PropertyNoDuesCertificate">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="payableFlag" id="payableFlag" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="accordion-toggle">
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#Applicant" tabindex="-1"><spring:message
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
								disabled="${disabled}"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
							<label class="col-sm-2 control-label required-control"
								for="firstName"><spring:message
									code="applicantinfo.label.firstname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasCharacter preventSpace"
									readonly="${disabled}"
									path="applicantDetailDto.applicantFirstName" id="firstName"
									data-rule-required="true"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="middleName"><spring:message
									code="applicantinfo.label.middlename" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasCharacter preventSpace"
									readonly="${disabled}"
									path="applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"
								for="lastName"><spring:message
									code="applicantinfo.label.lastname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasCharacter preventSpace"
									readonly="${disabled}"
									path="applicantDetailDto.applicantLastName" id="lastName"
									data-rule-required="true"></form:input>
							</div>
						</div>
					</div>
				</div>

				<div class="accordion-toggle">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="collapsed"
							data-parent="#accordion_single_collapse" href="#applicantDetails" tabindex="-1">
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
										maxlength="100"></form:input>
								</div>
								<label class="col-sm-2 control-label" for="flatBuildingNo"><spring:message
										code="prop.no.dues.applicant.block" text="Block" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.flatBuildingNo" id="flatBuildingNo"
										maxlength="100"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="cbillingRoadName"><spring:message
										code="applicantinfo.label.roadname" text="Road Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.roadName" id="roadName"
										maxlength="100"></form:input>
								</div>
								<%-- 	<label class="col-sm-2 control-label" for="location"><spring:message
										code="property.location" /></label>
								<div class="col-sm-4">
									<form:select path="applicantDetailDto.dwzid1"
										class="chosen-select-no-results" id="location">
										<form:option value="">
											<spring:message code="property.selectLocation"
												text="Select location" />
										</form:option>
										<c:forEach items="${command.location}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>

								</div> --%>

							</div>
							<div class="form-group wardZone">

								<apptags:lookupFieldSet baseLookupCode="CWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="applicantDetailDto.dwzid"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
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
										maxlength="100"></form:input>
								</div>
								<label class="col-sm-2 control-label" for="pinCode"><spring:message
										code="applicantinfo.label.pincode" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasNumber hideElement"
										path="applicantDetailDto.pinCode" id="pinCode" maxlength="6"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="applicantinfo.label.mobile" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasMobileNo" maxlength="10"
										path="applicantDetailDto.mobileNo" id="mobileNo"></form:input>
								</div>

								<label class="col-sm-2 control-label"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasemailclass"
										path="applicantDetailDto.emailId" id="emailId"></form:input>
								</div>
							</div>

						</div>
					</div>
				</div>
				<%-- <button type="button" class="btn btn-blue-2"
					onclick="showConfirmBoxToProceed()" id="submitdiv">
					<spring:message code="prop.search.button"
						text="Search Property Details" />
				</button> --%>

				<div class="accordion-toggle padding-top-20">

					<h4 class="panel-title">
						<a data-toggle="collapse" class="collapsed"
							data-parent="#accordion_single_collapse"
							href="#propertyDetailsDiv" tabindex="-1"> <spring:message
								code="property.Propertydetail" text="Property Details" />
						</a>
					</h4>

					<div id="propertyDetailsDiv" class="panel-collapse">

						<table class="table table-bordered" id="propertyDetails">
							<thead>
								<tr>
									<th class="text-center" width="1%"><spring:message
											code="bill.srNo" text="Sr. No." /></th>
									<th class="text-center required-control" width="7%"><spring:message
											code="property.financialyear" text="Financial Year" /></th>
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
											<td><form:select
													path="noDuesCertificateDto.propertyDetails[0].finacialYearId"
													cssClass="form-control mandColorClass read"
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
											<td><form:input
													path="noDuesCertificateDto.propertyDetails[0].propNo"
													class="form-control read" type="text"
													onblur="getFlatDetails(0)" id="propNo0" maxlength="50" /></td>
											<td><form:select
													path="noDuesCertificateDto.propertyDetails[0].flatNo"
													cssClass="form-control read"
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
													readonly="true" class="form-control read" type="text"
													id="ownerName0"  /></td>
											<td><form:input
													path="noDuesCertificateDto.propertyDetails[0].ownerAddress"
													readonly="true" class="form-control" type="text"
													id="address0"  /></td>
											<%-- 	<td class="text-center" style="white-space: nowrap"><a
												data-placement="top" title="Delete"
												class="btn btn-danger btn-sm"
												data-original-title="<spring:message code="water.delete"/>"
												id=deleteprop
												onclick="removeConnectionProp('propertyDetails',0,'propDet')"><i
													class="fa fa-trash"></i></a></td> --%>
											<form:hidden
												path="noDuesCertificateDto.propertyDetails[0].totalOutsatandingAmt"
												id="totalOutsatandingAmt0" />
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
												<td><form:select
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].finacialYearId"
														cssClass="form-control mandColorClass read"
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
												<td><form:input
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].propNo"
														class="form-control read" type="text"
														onblur="getFlatDetails(${status.count-1})"
														id="propNo${status.count-1}" maxlength="50" /></td>
												<td><form:select
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].flatNo"
														cssClass="form-control read"
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
														class="form-control read" type="text"
														id="ownerName${status.count-1}"  /></td>
												<td><form:input
														path="noDuesCertificateDto.propertyDetails[${status.count-1}].ownerAddress"
														class="form-control read" type="text"
														id="address${status.count-1}"  /></td>
												<%-- <td class="text-center" style="white-space: nowrap"><a
													data-placement="top" title="Delete"
													class="btn btn-danger btn-sm"
													data-original-title="<spring:message code="water.delete"/>"
													id=deleteprop
													onclick="removeConnectionProp('propertyDetails',${status.count-1},'propDet')"><i
														class="fa fa-trash"></i></a></td> --%>
												<form:hidden
													path="noDuesCertificateDto.propertyDetails[${status.count-1}].totalOutsatandingAmt"
													id="totalOutsatandingAmt${status.count-1}" />

											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>


						</table>
					</div>
				</div>


				<!-- Start Each Section -->
				<c:if test="${command.getAllowToGenerate() eq 'N'}">
					<div class="form-group padding-top-20">

						<apptags:input labelCode="Property No."
							path="noDuesCertificateDto.propNo" cssClass="mandColorClass "
							isDisabled="${command.getAllowToGenerate() eq 'Y' ? true : false}"></apptags:input>
						<apptags:input labelCode="OLD PID."
							path="noDuesCertificateDto.oldpropno" cssClass="mandColorClass"
							isDisabled="${command.getAllowToGenerate() eq 'Y' ? true : false}"></apptags:input>

						<form:hidden path="scrutinyAppliFlag" id="immediateService" />


						<!--Search Button-->
						<div class="text-center padding-15 clear">
							<button type="button" class="btn btn-warning"
								onclick="getNoDuesDetails(this)" id="detailSearch">
								<spring:message code="property.search" text="" />
							</button>
						</div>
						<form:hidden path="allowToGenerate" id="allowToGenerate" />
					</div>
				</c:if>

				<div class="form-group padding-top-20">
					<apptags:input labelCode="No of copies" cssClass="required-control hasNumber"
						isMandatory="true" path="noDuesCertificateDto.noOfCopies"></apptags:input>
				</div>

				<div id="detailDiv">
					<div class="accordion-toggle">
						<%-- <c:if test="${not empty command.checkList}">  

<h4 class="margin-top-0 margin-bottom-10 panel-title"> <a data-toggle="collapse" href="#DocumentUpload"><spring:message code="propertyTax.DocumentsUpload"/></a></h4>
                
                <div id="DocumentUpload" class="panel-collapse collapse in">
                  <div class="panel-body">
                    
										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">
															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}</td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td> <spring:message
																				code="water.doc.opt" />
																	</td>
																</c:if>
																<td><div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			currentCount="${lk.index}" />
																	</div>
																	</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>									
                  </div>
                </div>
    
          
</c:if>  --%>


						<c:if test="${not empty command.checkList}">

							<div class="panel-group accordion-toggle">
								<h4 class="margin-top-0 margin-bottom-10 panel-title">
									<a data-toggle="collapse" href="#a3" tabindex="-1"><spring:message
											code="" text="Upload Attachment" /></a>
								</h4>
								<div class="panel-collapse collapse in" id="a3">

									<div class="panel-body">

										<div class="overflow margin-top-10 margin-bottom-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped"
													id="documentList">
													<tbody>
														<tr>
															<th><spring:message code="adh.sr.no" text="Sr No" /></th>
															<th><spring:message code="adh.document.name"
																	text="Document Name" /></th>
															<th><spring:message code="adh.status" text="Status" /></th>
															<th width="500"><spring:message code="adh.upload"
																	text="Upload" /></th>
														</tr>

														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">

															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}</td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="adh.doc.mandatory" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="adh.doc.optional" /></td>
																</c:if>
																<td>
																	<div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
																			currentCount="${lk.index}" />
																	</div>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${not empty command.noDuesCertificateDto.charges}">
							<h4 class="margin-top-10 margin-bottom-10 panel-title ">
								<a data-toggle="collapse" href="#TaxCalculation"
									class="contentRemove" tabindex="-1"><spring:message
										code="property.Charges" text="Charges" /></a>
							</h4>

							<div class="panel-collapse collapse in" id="TaxCalculation">

								<c:set var="totPayAmt" value="0" />
								<div class="table-responsive">
									<table
										class="table table-striped table-condensed table-bordered">
										<tbody>
											<tr>
												<th width="50"><spring:message code="propertyTax.SrNo" /></th>
												<th width="600"><spring:message
														code="prop.no.dues.charge.desc"
														text="Charge / Fee Description" /></th>
												<th width="400"><spring:message
														code="propertyTax.Total" /></th>
											</tr>

											<c:forEach var="tax"
												items="${command.noDuesCertificateDto.charges}"
												varStatus="status">
												<tr>
													<td>${status.count}</td>
													<td>${tax. getTaxDesc()}</td>
													<td class="text-right">${tax.getTotalTaxAmt()}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>

								<div class="table-responsive margin-top-10">
									<form:hidden path="noDuesCertificateDto.totalPaybleAmt"
										id="totalPayAmt" />
									<table class="table table-striped table-bordered">
										<tr>
											<th width="500"><spring:message
													code="prop.no.dues.total.charge.desc"
													text="Total Service Charges" /></th>
											<th width="500" class="text-right">${command.noDuesCertificateDto.totalPaybleAmt}</th>
										</tr>
									</table>
								</div>
							</div>
						</c:if>

						<c:if test="${not empty command.noDuesCertificateDto.charges}">
							<div class="panel panel-default">
								<div id="Payment_mode" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">

											<!-- Defect #32656 -->
											<label class="control-label col-sm-2"><spring:message
													code="onlineoffline.label.selectPaymrntMode" /><span
												class="mand">*</span></label>
											<div class="col-sm-4">
												<label class="radio-inline" id="onlineLabel"> <form:radiobutton
														path="offlineDTO.onlineOfflineCheck" value="Y"
														onclick="showDiv(this);" /> <spring:message
														code="onlineoffline.label.onlinePay" />
												</label><%--  <label class="radio-inline" id="offLineLabel"> <form:radiobutton
														path="offlineDTO.onlineOfflineCheck" value="N"
														onclick="showDiv(this);" id="onlineOfflineCheck1" /> <spring:message
														code="onlineoffline.label.offlinePay" />
												</label> --%>
											</div>
										</div>


									<%-- 	<div class="offlinepayment" id="offlinepayment">
											<div class="form-group">
												<label class="col-sm-2 control-label required-control"><spring:message
														code="onlineoffline.label.offlinePaymentSelection" /></label>
												<c:set var="baseLookupCode" value="OFL" />
												<div class="col-sm-4">
													<form:select path="offlineDTO.oflPaymentMode"
														cssClass="form-control" id="oflPaymentMode">
														<c:forEach items="${command.getLevelData(baseLookupCode)}"
															var="oflMode">
															<form:option code="${oflMode.lookUpCode}"
																value="${oflMode.lookUpId}">${oflMode.lookUpDesc}</form:option>
														</c:forEach>
													</form:select>
												</div>

												<script>
	fieldsVisible(${command.offlinePay},${command.getBank()},${command.getUlb()},${command.getDd()},${command.getPostal()});
	</script>
											</div>
										</div> --%>

										<div class="form-group margin-top-10">
											<label class="col-sm-2 control-label"> <spring:message
													code="water.field.name.amounttopay" />
											</label>
											<div class="col-sm-4">
												<input type="text" class="form-control"
													value="${command.offlineDTO.amountToShow}"
													data-rule-required="true" data-rule-maxlength="8"
													readonly="readonly" /> <a
													class="fancybox fancybox.ajax text-small text-info"
													href="EstateBooking.html?showRnLChargeDetails"> <spring:message
														code="water.field.name.amounttopay" /> <i
													class="fa fa-question-circle "></i>
												</a>
											</div>
										</div>


									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>

				<c:if test="${command.getAllowToGenerate() eq 'Y'}">
					<c:if
						test="${command.appliChargeFlag eq 'Y' || command.checkListApplFlag eq 'Y'}">
						<div class="text-center padding-10">
							<button type="button" class="btn btn-success"
								onclick="getCheckListAndCharges(this,'DuplicateBillForm.html')"
								id="checkListCharge">
								<spring:message code="unit.proceed" />
							</button>
							<button type="button" class="btn btn-warning"
								onclick="ResetForm('DuplicateBillForm.html')" id="btndelete">
								<spring:message code="solid.waste.reset" text="Reset" />
							</button>
						</div>
					</c:if>
				</c:if>

				<c:if
					test="${command.appliChargeFlag eq 'N' && command.checkListApplFlag eq 'N'}">
					<div class="text-center padding-top-10">

						<button type="button" class="btn btn-success btn-submit"
							onclick="savePropertyFrom(this,'DuplicateBillForm.html')"
							id="saveNoDue">
							<spring:message code="" text="Save Application" />
						</button>
					</div>
				</c:if>


				<c:if test="${command.authFlag eq 'Y' }">
					<%-- <apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction> --%>

					<c:if test="${command.payableFlag eq 'Y' }">
						<h4>LOI Fees and Charges in Details</h4>
						<div class="table-responsive">
							<table class="table table-bordered table-striped">
								<tr>
									<th scope="col" width="80">Sr. No</th>
									<th scope="col">Charge Name</th>
									<th scope="col">Amount</th>
								</tr>
								<c:forEach var="charges" items="${command.loiDetail}"
									varStatus="status">
									<tr>
										<td>1</td>
										<td>${charges.loiRemarks}</td>
										<td><fmt:formatNumber value="${charges.loiAmount}"
												type="number" var="amount" minFractionDigits="2"
												maxFractionDigits="2" groupingUsed="false" /> <form:input
												path="" type="text" class="form-control text-right"
												value="${amount}" readonly="true" /></td>
									</tr>
								</c:forEach>

								<tr>
									<td colspan="2"><span class="pull-right"><b>Total
												LOI Amount</b></span></td>
									<td class="text-right">${command.totalLoiAmount}</td>

								</tr>
							</table>
						</div>
					</c:if>

					<div class="padding-top-10 text-center">

						<c:if test="${command.payableFlag eq 'N' }">
							<button type="button" class="btn btn-success" id="submit"
								onclick="checkLastApproval(this)">
								<spring:message code="adh.adh.submit" text="Submit"></spring:message>
							</button>
						</c:if>
						<c:if test="${command.payableFlag eq 'Y' }">

							<button type="button" class="btn btn-success" id="submit"
								onclick="saveAgencyApprovalForm(this)">
								<spring:message code="adh.save" text="Save"></spring:message>
							</button>
						</c:if>
						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="adh.back" text="Back"></spring:message>
						</button>
					</div>
				</c:if>

			</form:form>
		</div>
	</div>
</div>



