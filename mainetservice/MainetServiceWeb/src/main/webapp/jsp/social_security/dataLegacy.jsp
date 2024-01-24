<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/social_security/dataLegacy.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Data Legacy Form Social Security Scheme"  code="social.data.legacy.titile"/>
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>

			</div>
			<div class="widget-content padding">
				<form:form id="dataLegacyFormId" action="DataLegacyForm.html"
					method="POST" class="form-horizontal" name="dataLegacyFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"><spring:message
												code="soc.applicant.detail" text=" Applicant Details"/></label></a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body padding-top-0">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.schemename" /></label>

										<div class="col-sm-4">

											<form:select name="applicationformdtoId"
												path="applicationformdto.selectSchemeName" id="schemenameId"
												class="form-control chosen-select-no-results" disabled="false" onChange="checkDate(this);">
												<option value="0"><spring:message text="Select" /></option>
												<c:forEach items="${command.serviceList}" var="objArray">
													<form:option value="${objArray[0]}" code="${objArray[2]}"
														label="${objArray[1]}"></form:option>
												</c:forEach>
											</form:select>
											</div>
											
											<label class="col-sm-2 control-label "><spring:message
												code="social.sec.subschemename" text="Sub - Scheme"/></label>

											<div class="col-sm-4">
												<form:select path="applicationformdto.subSchemeName" id="subschemeId"
												class="form-control chosen-select-no-results"
												disabled="false" >
												<form:option value="">
													<spring:message text="Select" code="soc.select" />
												</form:option>
												<c:forEach items="${command.subTypeList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}" 
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
											</div>
											
										</div>

										<div class="form-group">
										
										<apptags:input labelCode="social.sec.nameapplicant"
											path="applicationformdto.nameofApplicant" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="50"></apptags:input>
											
										<c:set var="baseLookupCode" value="CWZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="CWZ"
											hasId="true" pathPrefix="applicationformdto.swdward" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />
									</div>

									<div class="form-group">
									
									<apptags:input labelCode="social.sec.adharnumber"
											path="applicationformdto.aadharCard" isMandatory="true"
											cssClass="hasAadharNo text-right" maxlegnth="12"></apptags:input>
											
											<label class="col-sm-2 control-label required-control" for=""
											id="dispoDate"><spring:message
												code="social.sec.applicantdob" /></label>
										<div class="col-sm-4"> 
											<div class="input-group">
												<form:input class="form-control datepicker"
													id="applicantId" 
													data-label="#dispoDate"
													path="applicationformdto.applicantDob" isMandatory="true"
													onchange="dobdetails();"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

									</div>
									<div class="form-group">
									
									<apptags:input labelCode="social.sec.ageason"
											path="applicationformdto.ageason" isMandatory="true"
											cssClass="text-right" isReadonly="true"></apptags:input>
									
										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.gen" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.genderId"
												class="form-control" disabled="false">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.genderList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
										
									</div>

									<div class="form-group">
									<apptags:textArea labelCode="social.sec.applicantAdress"
											path="applicationformdto.applicantAdress" isMandatory="true"
											cssClass="hasAddressClass" maxlegnth="200"></apptags:textArea>

										<apptags:input labelCode="social.sec.pincode"
											path="applicationformdto.pinCode"
											cssClass="hasPincode text-right" isMandatory="true"
											maxlegnth="6"></apptags:input>


									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.mobnum"
											path="applicationformdto.mobNum"
											cssClass="hasMobileNo text-right" isMandatory="true"
											maxlegnth="10" onChange="validateMobNum()"></apptags:input>

										<label class="col-sm-2 control-label"><spring:message
												code="social.sec.education" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.educationId"
												class="form-control" disabled="false"
												onchange="geteducationdetails();">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.educationList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.mstatus" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.maritalStatusId"
												class="form-control" disabled="false">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.maritalstatusList}"
													var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.category" /></label>
										<div class="col-sm-4">
											<form:select path="applicationformdto.categoryId"
												class="form-control" disabled="false">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.categoryList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.annualincome"
											path="applicationformdto.annualIncome"
											cssClass="hasNumber text-right" maxlegnth="8"></apptags:input>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.typeofdisability" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.typeofDisId"
												class="form-control" disabled="false"
												onchange="disabilitydetails();">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.typeofdisabilityList}"
													var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.percentofdis"
											path="applicationformdto.percenrofDis" isMandatory="true"
											cssClass="hasNumber text-right" maxlegnth="2"></apptags:input>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.bpl" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.bplid"
												cssClass="form-control" disabled="false"
												onchange="bpldetails();">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.bplList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
											<form:hidden path="applicationformdto.isBplApplicable"
												id="isBplApplicableId" />
										</div>
									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.bplinspectionyr"
											path="applicationformdto.bplinspectyr" isMandatory="true"
											cssClass="hasNumber text-right" maxlegnth="4"></apptags:input>

										<apptags:input labelCode="social.sec.bplfamilyid"
											path="applicationformdto.bplfamily" isMandatory="true"
											cssClass="hasNumber text-right" maxlegnth="12"></apptags:input>
									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control" for=""><spring:message
												code="social.sec.lifecerti" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control" id="lastDateLifeCertiId"
													data-label="#dispoDate"
													path="applicationformdto.lastDateofLifeCerti"
													isMandatory="true"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

									</div>
								</div>
							</div>


							<!----------------------------- Bank Details  ---------------------------->

							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 type="h4" class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse" href="#a2"><spring:message code="social.sec.bankDet" text="Bank Details" />
											</a>
									</h4>
								</div>
								<div id="a2" class="panel-collapse collapse in">
									<div class="panel-body padding-top-0">

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"><spring:message
													code="social.sec.bankname" /></label>


											<div class="col-sm-4">

												<form:select name="applicationformdtoId"
												path="applicationformdto.bankNameId" id="banknameId"
												class="form-control chosen-select-no-results"  disabled="false">
												<option value="0"><spring:message text="Select" /></option>
												<c:forEach items="${command.bankList}" varStatus="status"
													var="cbBankid">
													<form:option value="${cbBankid.bankId}"
														code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} - ${cbBankid.ifsc}</form:option>
												</c:forEach>
											</form:select>
											</div>

											<apptags:input labelCode="social.sec.accountnum"
												path="applicationformdto.accountNumber" isMandatory="true"
												cssClass="hasNumber text-right" maxlegnth="16"></apptags:input>
										</div>
									</div>
								</div>
							</div>
							<!----------------------------- Bank Details End ---------------------------->



							<!-------------------------- Applicant Details Start  ------------------------>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 type="h4" class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse" href="#a3">	<spring:message code="social.sec.app.family.det" text="Applicant Family Details" />
											</a>
									</h4>
								</div>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body padding-top-0">
									<c:set var="d" value="0" scope="page"></c:set>
										<table class="table table-striped table-bordered" id="ownerFamilyDetail">
											<thead>
												<tr>
													<th width="15%"><spring:message
															code="social.ownerFamilyDetail.Name" text="Name">
														</spring:message> <span class="mand">*</span></th>
													<th width="15%"><spring:message
															code="social.ownerFamilyDetail.rel" text="Relation"></spring:message><span
														class="mand">*</span></th>
													<th width="10%"><spring:message
															code="social.ownerFamilyDetail.gen" text="Gender">
															<span class="mand">*</span>
														</spring:message></th>
													<th width="15%"><spring:message
															code="social.ownerFamilyDetail.dob" text="Date Of Birth">
														</spring:message> <span class="mand">*</span></th>
													<th width="5%"><spring:message
															code="social.ownerFamilyDetail.Age" text="Age"></spring:message><span
														class="mand">*</span></th>
													<th width="10%"><spring:message
															code="social.ownerFamilyDetail.edu" text="Education"></spring:message><span
														class="mand">*</span></th>
													<th width="15%"><spring:message
															code="social.ownerFamilyDetail.ocu" text="Occupation"></spring:message><span
														class="mand">*</span></th>
													<th width="15%"><spring:message
															code="social.ownerFamilyDetail.contact" text="Contact No"></spring:message><span
														class="mand">*</span></th>
										
										
													<th colspan="2" width="5%"><a href="javascript:void(0);"
														title="Add" class=" addCF btn btn-success btn-sm" id="addUnitRow"
														onclick=''> <i class="fa fa-plus-circle"></i></a></th>
										
												</tr>
											</thead>
											<tbody>
								<c:choose>
								<c:when
								test="${fn:length(command.applicationformdto.ownerFamilydetailDTO)>0 }">
								<c:forEach var="taxData"
								items="${command.applicationformdto.ownerFamilydetailDTO}"
								varStatus="status">
								<tr class="appendableFamilyClass">
									<td><form:input
								            path="command.applicationformdto.ownerFamilydetailDTO[${d}].famMemName"
											type="text"
											class="form-control unit required-control hasSpecialChara preventSpace"
											maxLength="100" id="famMemName${d}" /></td>
									<td><form:input
											path="command.applicationformdto.ownerFamilydetailDTO[${d}].relation"
											type="text"
											class="form-control unit required-control hasSpecialChara preventSpace"
											maxLength="100" id="relation${d}" /></td>
			
									<td>
							<div>

								<c:set var="baseLookupCode" value="GEN" />
								<form:select
									path=""
									onchange="" class="form-control mandColorClass" id="gender${d}"
									data-rule-required="true">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</td>	
						<td> <form:input
								class="form-control mandColorClass datepicker2 addColor"
								autocomplete="off" id="dob${d}"   maxlength="10" onchange="dobdetails1();"
								path=""></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</td>
						<td><form:input
								path=""
								type="text" 
								class="form-control unit required-control hasNumber preventSpace"
								maxLength="3" id="age${d}" /></td>
						<td><form:input
								path=""
								type="text"
								class="form-control unit required-control hasSpecialChara preventSpace"
								id="education${d}" /></td>
						<td><form:input
								path=""
								type="text"
								class="form-control unit required-control hasSpecialChara preventSpace"
								id="occupation${d}" /></td>
						<td><form:input
								path=""
								type="text" maxlength="10"
								class="form-control unit required-control hasMobileNo preventSpace"
								id="contactNo${d}" /></td>
								
						<td class="text-center"><a href="javascript:void(0);"
							class="btn btn-danger btn-sm delButton"
							onclick="DeleteRow(this);"><i class="fa fa-minus"
								id="deleteRow"></i></a></td>
						
						
						
						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
						</c:when>
						<c:otherwise>
				<tr class="appendableFamilyClass">
					<td><form:input
							path=""
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							maxLength="100" id="famMemName${d}" /></td>
					<td><form:input
							path=""
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							maxLength="100" id="relation${d}" /></td>



					<td>
						<div>

							<c:set var="baseLookupCode" value="GEN" />
							<form:select
								path=""
								onchange="" class="form-control mandColorClass" id="gender${d}"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="trade.sel.optn.gender" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpCode}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</td>
					<td><div class="input-group">
							<form:input
								class="form-control mandColorClass datepicker2 addColor"
								autocomplete="off" id="dob${d}"  maxlength="10" onchange="dobdetails1();"
								path=""></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div></td>
					<td><form:input
							path=""
							type="text" 
							class="form-control unit required-control hasNumber preventSpace"
							maxLength="3" id="age${d}" /></td>
					<td><form:input
							path=""
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							id="education${d}" /></td>
					<td><form:input
							path=""
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							id="occupation${d}" /></td>
					<td><form:input
							path=""
							type="text" maxlength="10"
							class="form-control unit required-control hasMobileNo preventSpace"
							id="contactNo${d}" /></td>
					<td class="text-center"><a href="javascript:void(0);"
						class="btn btn-danger btn-sm delButton" onclick="DeleteRow(this);"><i
							class="fa fa-minus" id="deleteRow"></i></a></td>



				</tr>
				<c:set var="d" value="${d + 1}" scope="page" />
			</c:otherwise>
		</c:choose>
		</tbody>
		</table>

										

									</div>
								</div>
							</div>
							<!----------------------------- Applicant Details End  ---------------------------->

							<!----------------------------- Payment Details Start  ---------------------------->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 type="h4" class="panel-title table" id="paymentdetails">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse" href="#a3"><spring:message code="social.data.legacy.paymentDetails" text="Payment Details" />
											</a>
									</h4>
								</div>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body padding-top-0">

										<div class="form-group">

											<apptags:input labelCode="data.legacy.referencenum"
												path="applicationformdto.referenceNo" isMandatory="true"
												cssClass="hasNumber text-right" maxlegnth="10"></apptags:input>

											<label class="col-sm-2 control-label required-control" for=""><spring:message
													code="data.legacy.lastpaymentdate" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control datepicker"
														id="lastpaymentdateId" data-label="#dispoDate"
														path="applicationformdto.lastPaymentDate"
														isMandatory="true"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
										</div>

									</div>
								</div>
							</div>

							<!----------------------------- Payment Details End  ---------------------------->
							<div class="text-center">
								<button type="button" class="btn btn-success" title="Submit"
									onclick="saveDataLegacy(this)"><spring:message text="Submit" code="social.btn.submit"/></button>

								<button type="Reset" class="btn btn-warning" id="resetform"
									onclick="resetDataLegacy(this)">
									<spring:message text="Reset" code="social.btn.reset"/>
								</button>
								<apptags:backButton url="AdminHome.html"></apptags:backButton>
							</div>
				</form:form>
			</div>
		</div>
	</div>
</div>