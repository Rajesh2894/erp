<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/adh/agencyRegistration.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/adh/adhOwner.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="agency.registration"
					text="Agency Registration" />
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form action="AgencyRegistrationAuth.html"
				name="AgencyRegistrationAuth" id="AgencyRegistrationAuth"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<form:hidden path="successFlag" id="successFlag" />
				<form:hidden path="payableFlag" id="payableFlag" />
				<form:hidden path="" id="viewMode" value="${command.viewMode}" />

				<div class="accordion-toggle">

					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#Applicant"><spring:message
								code="" text="Applicant Details" /></a>
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
									readonly="${disabled}"
									path="applicantDetailDto.applicantFirstName" id="firstName"
									data-rule-required="true" disabled="true"></form:input>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label" for="middleName"><spring:message
									code="applicantinfo.label.middlename" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}"
									path="applicantDetailDto.applicantMiddleName" id="middleName"
									disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"
								for="lastName"><spring:message
									code="applicantinfo.label.lastname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}"
									path="applicantDetailDto.applicantLastName" id="lastName"
									data-rule-required="true" disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="applicantinfo.label.mobile" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasMobileNo"
									readonly="${disabled}" maxlength="10"
									path="applicantDetailDto.mobileNo" id="mobileNo"
									data-rule-required="true" disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.email" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.emailId"
									id="emailId" disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="address.line1" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.areaName"
									id="areaName" data-rule-required="true" disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="address.line2" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.villageTownSub"
									id="villTownCity" disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="address.line3" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.roadName"
									id="roadName" disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="applicantinfo.label.pincode" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasNumber"
									readonly="${disabled}" path="applicantDetailDto.pinCode"
									id="pinCode" maxlength="6" data-rule-required="true"
									disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.aadhaar" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasNumber"
									readonly="${disabled}" path="applicantDetailDto.aadharNo"
									id="aadharNo" maxlength="12" data-mask="9999 9999 9999"
									disabled="true" />
							</div>

						</div>
					</div>
				</div>


				<div class="accordion-toggle">

					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#a1"><spring:message
								code="agency.detail" text="Agency Details" /></a>
					</h4>
					<div class="panel-collapse collapse in" id="a1">
					<div class="panel-body">
											<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
							        code="adh.new.advertisement.agencyType" text="Agency Type" /></label>
							       <!--  <div class="col-sm-4"> -->
										<c:set var="baseLookupCode" value="AOT" />
								 <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
							    path="agencyRequestDto.masterDtolist[0].trdFtype"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								showAll="false" selectOptionLabelCode="property.sel.optn.ownerType"
								disabled="true" isMandatory="true" 
								showOnlyLabel="Agency Type" /> 
										<%-- <form:select path="agencyRequestDto.masterDtolist[0].trdFtype"
											
											class="form-control mandColorClass" id="trdFtype"
											onchange="getOwnerTypeDetails()"
											disabled="${command.viewMode eq 'V' ? true : false }"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="property.sel.optn.ownerType" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>  --%>

							  <!-- </div> -->
								  <div id="owner">
						
								  </div>  
								 </div>
					
			  <%-- <jsp:include page="/jsp/adh/adhLicenseOwnershipTableDetails.jsp"></jsp:include> --%>   
						<div class="form-group">

							<label class="col-sm-2 control-label required-control" for=""><spring:message
									code="agency.category" text="Agency Category" /></label>
							<c:set var="baseLookupCode" value="ADC" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="agencyRequestDto.masterDtolist[0].agencyCategory"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								showAll="false" selectOptionLabelCode="adh.select"
								disabled="true" isMandatory="true"
								showOnlyLabel="Agency Category" />
							<apptags:input labelCode="agency.name"
								path="agencyRequestDto.masterDtolist[0].agencyName" maxlegnth="400"
								isMandatory="true" isDisabled="true"></apptags:input>
						</div>

						 <div class="form-group">
							<%-- <apptags:input labelCode="agency.owner"
								path="agencyRequestDto.masterDtolist[0].agencyOwner" cssClass=""
								isMandatory="true" isDisabled="true"></apptags:input> --%>
							<apptags:input labelCode="agency.address"
								path="agencyRequestDto.masterDtolist[0].agencyAdd" maxlegnth="400"
								isMandatory="true" isDisabled="true"></apptags:input>
								
								<apptags:input labelCode="agency.gst.no"
								path="agencyRequestDto.masterDtolist[0].gstNo" maxlegnth="15"
								cssClass="hasNumber" isDisabled="true"></apptags:input>
						</div> 

						<%-- <div class="form-group">
							<apptags:input labelCode="agency.mobile.no"
								path="agencyRequestDto.masterDtolist[0].agencyContactNo"
								cssClass="hasMobileNo" isMandatory="true" isDisabled="true"></apptags:input>
							<apptags:input labelCode="agency.emailid"
								path="agencyRequestDto.masterDtolist[0].agencyEmail" maxlegnth="50"
								isMandatory="true" cssClass="hasemailclass" isDisabled="true"></apptags:input>
						</div> --%>

						<%-- <div class="form-group">
							apptags:input labelCode="agency.uid.no"
								path="agencyRequestDto.masterDtolist[0].uidNo" isDisabled="true"></apptags:input>
								<apptags:input labelCode="agency.address"
									path="agencyRequestDto.masterDtolist[0].agencyAdd" maxlegnth="400"
									cssClass="preventSpace" isMandatory="true"></apptags:input>
							<apptags:input labelCode="agency.gst.no"
								path="agencyRequestDto.masterDtolist[0].gstNo" maxlegnth="15"
								cssClass="hasNumber" isDisabled="true"></apptags:input>
						</div> --%>


						<%-- <div class="form-group">
							<apptags:input labelCode="agency.pan"
								path="agencyRequestDto.masterDtolist[0].panNumber" maxlegnth="20"
								isDisabled="true"></apptags:input>
						</div> --%>

					</div>
				</div>
				<div class="accordion-toggle">
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#a2"><spring:message code=""
								text="Licence Details" /></a>
					</h4>
					<div class="panel-collapse collapse in" id="a2">
						<div class="form-group">
							<apptags:date labelCode="agency.licence.from.date"
								datePath="agencyRequestDto.masterDtolist[0].agencyLicFromDate"
								fieldclass="datepicker" isMandatory="true" isDisabled="true"></apptags:date>

							<apptags:date labelCode="agency.licence.to.date"
								datePath="agencyRequestDto.masterDtolist[0].agencyLicToDate"
								fieldclass="datepicker" isMandatory="true" isDisabled="true"></apptags:date>
						</div>


					</div>
				</div>


				<c:if test="${not empty command.documentList}">

					<div class="panel-group accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a3"><spring:message code=""
									text="Upload Attachment" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="a3">

							<div class="panel-body">

								<div class="overflow margin-top-10 margin-bottom-10">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="adh.sr.no" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="adh.document.name" text="Document Name" /></label></th>
													<th><label class="tbold"><spring:message
																code="" text="Download" /></label></th>
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


														<td><apptags:filedownload
																filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																actionUrl="AgencyRegistrationAuth.html?Download" /></td>

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
				
				<c:if test="${not empty command.documentListAtchdAtApprovalTm}">

					<div class="panel-group accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a3"><spring:message code=""
									text="Upload Attachment For Approval" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="a3">

							<div class="panel-body">

								<div class="overflow margin-top-10 margin-bottom-10">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="adh.sr.no" text="Sr No" /></label></th>
													<%-- <th><label class="tbold"><spring:message
																code="adh.document.name" text="Document Name" /></label></th> --%>
													<th><label class="tbold"><spring:message
																code="" text="Download" /></label></th>
												</tr>

												<c:forEach items="${command.documentListAtchdAtApprovalTm}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<%-- <c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose> --%>


														<td><apptags:filedownload
																filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																actionUrl="AgencyRegistrationAuth.html?Download" /></td>

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
				
					<apptags:CheckerAction  hideForward="true" hideSendback="true"></apptags:CheckerAction>
					
				
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

				<!-- START D#126605 -->
				<c:if test="${not empty command.getRemarkList() && command.payableFlag eq 'Y' }">
					<div class="overflow margin-top-10">
						<table class="table table-hover table-bordered table-striped"
							id="">
							<thead>
								<tr>
									<th scope="col" width="5%"><label
										class="padding-left-20 margin-bottom-10"> <input
											type="checkbox" name="All" id="selectall" class="pull-left">All
									</label></th>
									<th scope="col" width="5%"><spring:message
											code="adh.sr.no" text="Sr.No." /></th>
									<th scope="col" width="" align="center"><spring:message
											code="dashboard.actions.remarks" text="Remarks" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.getRemarkList()}" var="remark"
									varStatus="status">
									<tr>
										<td align="center"><label class="checkbox margin-left-20"><form:checkbox
													path="remarkList[${status.index}]" value="Y"
													id="${status.count}" class="case" name="case" /></label></td>
										<td>${status.count}</td>
										<td>${remark.artRemarks}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<!-- END D#126605 -->

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
			</form:form>
		</div>
	</div>
</div>

<script>
	var agencyLicFromDate = $('#agencyLicFromDate').val();
	var agencyLicToDate = $('#agencyLicToDate').val();
	
	if (agencyLicFromDate) {
		$('#agencyLicFromDate').val(agencyLicFromDate.split(' ')[0]);
	}
	if (agencyLicToDate) {
		$('#agencyLicToDate').val(agencyLicToDate.split(' ')[0]);
	}

</script>
