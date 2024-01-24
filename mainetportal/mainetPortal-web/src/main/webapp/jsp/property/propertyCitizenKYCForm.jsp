<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/property/propertyCitizenKYCForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="proeprty.citizen.kyc.form" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="PropertyCitizenKYCForm.html"
				class="form-horizontal" name="PropertyCitizenKYCForm"
				id="PropertyCitizenKYCForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<form:hidden path="billingMethod" id="billingMethod" />
				<form:hidden path="showForm" id="showForm" />
				<form:hidden path="searchFlag" id="searchFlag" />



<div id="showBasicSearch">
				<c:if test="${command.showForm eq 'N'}">
					<div class="form-group">
						<apptags:input labelCode="propertydetails.PropertyNo."
							path="provisionalAssesmentMstDto.assNo"></apptags:input>

						<div id="showFlatNo">
							<label class="col-sm-2 control-label"><spring:message
									code="property.FlatNo" text="Flat No" /></label>
							<div class="col-sm-4">
								<form:select path="provisionalAssesmentMstDto.flatNo"
									id="flatNo" class="mandColorClass chosen-select-no-results">
									<form:option value="">
										<spring:message code='property.select' text="select" />
									</form:option>
									<c:forEach items="${command.flatNoList}" var="flatNoList">
										<form:option value="${flatNoList}">${flatNoList}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>

					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-success" id="serchBtnDirect"
							onclick="SearchButton(this)">
							<i class="fa fa-search"></i>
							<spring:message code="property.changeInAss.Search" />
						</button>

						<button type="button" class="btn btn-success" id="serchBtn"
							onclick="redirectToSearchPropNo(this)">
							<i class="fa fa-search"></i>
							<spring:message code="property.changeInAss.Search" />
						</button>

						<button type="Reset" id="reset" class="btn btn-warning"
							title="Reset" onclick="resetorm()">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="property.reset" text="Reset" />
						</button>

						<button type="button" class="btn btn-success" id="showSearchPrp"
							onclick="showSearchPropNoGrid()">
							<i class="fa fa-search"></i>
							<spring:message code="" text="Know your Property No" />
						</button>


					</div>


				</c:if>
</div>
				<div id="showSearchPropNoGrid">

					<div class="form-group">
						<apptags:input labelCode="unitdetails.OccupierName"
							path="searchDto.occupierName" cssClass="preventSpace"></apptags:input>
						<apptags:input labelCode="proprty.witness.Address"
							path="searchDto.address" cssClass="preventSpace"></apptags:input>

					</div>
					<div class="form-group">
						<apptags:input labelCode="property.OwnerName"
							path="searchDto.ownerName" cssClass="preventSpace"></apptags:input>
						<apptags:input labelCode="ownersdetail.mobileno"
							path="searchDto.mobileno" cssClass="preventSpace"></apptags:input>

					</div>


					<div class="form-group wardZone">

						<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
							showOnlyLabel="false" pathPrefix="searchDto.assWard"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false"
							showData="true" />
					</div>



					<div class="text-center padding-top-10">
						<button type="button" class="btn btn-blue-2" id="serchBtn"
							onclick="searchPropertyNo()">
							<i class="fa fa-search"></i>
							<spring:message code="property.changeInAss.Search" />
						</button>


					</div>




					<div class="table-responsive">
						<div class="table-responsive margin-top-10">
							<table class="table table-striped table-condensed table-bordered"
								id="propDuesCheck">
								<thead>
									<tr>
										<th><spring:message
												code="property.duesCheck.property.number"
												text="Property No." /></th>
										<th><spring:message code="property.FlatNo" text="Flat No" /></th>
										<th><spring:message code="property.duesCheck.owner.name"
												text="Owner Name" /></th>
										<th><spring:message code="property.occupierName"
												text="Occupier Name" /></th>
										<th><spring:message code="property.mobile"
												text="Mobile Number" /></th>
										<th><spring:message code="property.duesCheck.address"
												text="Address" /></th>
										<th><spring:message code="property.totalDuesAmt"
												text="Total Dues Amount" /></th>
										<th><spring:message code="view.dues"
												text="Click Here For KYC Form" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.searchDtoResult}" var="searchDto">
										<tr>
											<td>${searchDto.proertyNo}</td>
											<td>${searchDto.flatNo}</td>
											<td>${searchDto.ownerName}</td>
											<td>${searchDto.occupierName}</td>
											<td>${searchDto.mobileno}</td>
											<td>${searchDto.address}</td>
											<td>${searchDto.outstandingAmt}</td>
											<td align="center">
												<button type="button" class="btn btn-warning btn-sm"
													title="Click Here For KYC Form"
													onclick="getpropertyDetailsFromSearchGrid('${searchDto.proertyNo}','${searchDto.flatNo}')">
													<i class="fa fa-edit"></i>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>


				</div>






				<c:if test="${command.showForm eq 'Y'}">


					<c:if test="${command.billingMethod eq 'I' }">
						<div class="form-group">
							<apptags:input labelCode="propertydetails.PropertyNo."
								path="provisionalAssesmentMstDto.assNo" isDisabled="true"></apptags:input>
							<apptags:input labelCode="property.FlatNo"
								path="provisionalAssesmentMstDto.flatNo" isDisabled="true"></apptags:input>
						</div>

						<div class="form-group">
							<apptags:input labelCode="property.OwnerName" path="ownerName"
								isDisabled="true"></apptags:input>
							<apptags:input labelCode="ownerdetails.Occupier.Name"
								path="occupierName" isDisabled="true"></apptags:input>
						</div>

					</c:if>

					<c:if
						test="${empty provisionalAssesmentMstDto.flatNo || provisionalAssesmentMstDto.flatNo eq null }">

						<div class="form-group">
							<apptags:input labelCode="propertydetails.PropertyNo."
								path="provisionalAssesmentMstDto.assNo" isDisabled="true"></apptags:input>
							<apptags:input labelCode="property.OwnerName" path="ownerName"
								isDisabled="true"></apptags:input>
						</div>

					</c:if>
					<div class="form-group">
						<apptags:input labelCode="proprty.witness.Address"
							path="provisionalAssesmentMstDto.assAddress" isDisabled="true"></apptags:input>
					</div>

					<div class="form-group">


						<label class="col-sm-2 control-label required-control"><spring:message
								code="ownersdetail.mobileno" /></label>
						<div class="col-sm-4">
							<form:input path="mobileNo" class="form-control" id="mobileNo"
								maxlength="10" />
						</div>



						<%-- <apptags:input labelCode="Mobile No" path="mobileNo"></apptags:input> --%>
						<div class="col-sm-1">
							<button type="button" class="btn btn-success" id="generateOtp"
								onclick="getOtpGeneration(this)">
								<spring:message code="" text="Get Otp" />
							</button>
						</div>

						<label class="col-sm-1 control-label required-control"><spring:message
								code="property.enter.otp" /></label>
						<div class="col-sm-4">
							<form:input path="userOtp" class="form-control" id="mobileNo" />
						</div>


					</div>


					<div class="form-group">
						<apptags:input labelCode="property.billPayment.emailId"
							path="emailId"></apptags:input>

					</div>

					<div class="form-group">

						<apptags:input labelCode="property.Electric.Billing.Unit.Number"
							path="provisionalAssesmentMstDto.electricBillUnitNo"
							maxlegnth="4"></apptags:input>
						<apptags:input labelCode="property.Electric.Consumer.Number"
							path="provisionalAssesmentMstDto.electricConsumerNo"
							maxlegnth="12"></apptags:input>

					</div>

					<div class="form-group">

						<apptags:input labelCode="property.Electric.Meter.Number"
							path="provisionalAssesmentMstDto.electricMeterNo" maxlegnth="12"></apptags:input>
					</div>



					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="property.Is.Underground.Drainage.Connection.Available" /></label>

						<div class="col-sm-4">
							<form:select
								path="provisionalAssesmentMstDto.isUnderGroundDrainConAvail"
								id="isUnderGroundDrainConAvail"
								class="form-control mandColorClass chosen-select-no-results"
								onchange="showConnectionDate()">
								<form:option value="">
									<spring:message code='property.select' text="select" />
								</form:option>
								<form:option value="Yes">
									<spring:message code='' text="Yes" />
								</form:option>
								<form:option value="No">
									<spring:message code='' text="No" />
								</form:option>
							</form:select>
						</div>
						<div id="conDate">
							<label class="col-sm-2 control-label"><spring:message
									code="proeprty.Connection.Date" /></label>
							<div class="col-sm-4">
								<div class=" input-group">

									<form:input class="form-control datepicker"
										path="provisionalAssesmentMstDto.connectionDate"
										isMandatory="true" id="connectionDate" />
									<span class="input-group-addon"> <i
										class="fa fa-calendar"></i>
									</span>
								</div>
							</div>
						</div>

					</div>


					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="property.Is.Underground.Septic.Tank.Available" /></label>
						<div class="col-sm-4">
							<form:select
								path="provisionalAssesmentMstDto.isUnderGroundSpecTankAvail"
								id="isUnderGroundSpecTankAvail"
								class="form-control mandColorClass chosen-select-no-results">
								<form:option value="">
									<spring:message code='property.select' text="select" />
								</form:option>
								<form:option value="Yes">
									<spring:message code='' text="Yes" />
								</form:option>
								<form:option value="No">
									<spring:message code='' text="No" />
								</form:option>
							</form:select>
						</div>


					</div>

					<c:if test="${not empty command.checkList}">

						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#DocumentUpload"><spring:message
									code="propertyTax.DocumentsUpload" /></a>
						</h4>

						<div id="DocumentUpload" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="overflow margin-top-10">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><spring:message code="water.serialNo" text="Sr No" />
													</th>
													<th><spring:message code="water.docName"
															text="Document Name" /></th>
													<th><spring:message code="water.status" text="Status" />
													</th>
													<th><spring:message code="water.uploadText"
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
															<td><spring:message code="water.doc.mand" /></td>
														</c:if>
														<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
															<td><spring:message code="water.doc.opt" /></td>
														</c:if>
														<td><div id="docs_${lk}" class="">
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true" fieldPath="checkList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="COMMOM_MAX_SIZE"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CARE_VALIDATION_EXTENSION"
																	currentCount="${lk.index}" />
															</div>
															<div class="col-sm-12">
																<small class="text-blue-2 "> <spring:message
																		code="property.file.upload.validator"
																		text="Documents/Images up to 20 MB can be upload.only pdf,png,jpg is allowed." />
																</small>
															</div></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>


					</c:if>

					<div class="form-group">
						<apptags:input labelCode="Captcha" path="displayCaptcha"
							isDisabled="true"></apptags:input>
						<apptags:input labelCode="Enter Captcha" path="userCaptcha"></apptags:input>
					</div>

					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-success" id="submit"
							onclick="savePorpertyData(this)">
							<spring:message code="" text="Submit" />
						</button>

					</div>
				</c:if>

			</form:form>
		</div>
	</div>
</div>
