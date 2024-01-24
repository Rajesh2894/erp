<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/account/receivableDemandEntry.js"></script>
<script src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="receivable.demand.entry.form" text="Receivable Demand Entry Form" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="ReceivableDemandEntry.html" commandName="command" class="form-horizontal form" name="ReceivableDemandEntry" id="id_ReceivableDemandEntry">
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<form:hidden path="receivableDemandEntryDto.taxCount" id="taxCount" />
				<form:hidden path="receivableDemandEntryDto.sliStatus" id="sliStatus" />
				<input type="hidden" id="serId" value="${command.receivableDemandEntryDto.serviceId}">
				<input type="hidden" id="depId" value="${command.receivableDemandEntryDto.deptId}">
				<input type="hidden" id="langId" value="${userSession.languageId}" />
				<input type="hidden" id="saveMode" value="${command.saveMode}">


				<div class="form-group padding-top-20">

					<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.deptName" text="Department Name" /></label>
					<div class="col-sm-4">
						<form:select class="form-control" id="deptId" path="receivableDemandEntryDto.deptId" data-rule-required="true" disabled="true" onchange="refreshServiceData()">

							<c:choose>
								<c:when test="${userSession.languageId eq 1}">
									<c:forEach items="${command.deptList}" var="deptData">
										<c:if test="${command.receivableDemandEntryDto.deptId eq deptData.dpDeptid}">
											<option value="${deptData.dpDeptid}">${deptData.dpDeptdesc}</option>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.deptList}" var="deptData">
										<c:if test="${command.receivableDemandEntryDto.deptId eq deptData.dpDeptid}">
											<option value="${deptData.dpDeptid}">${deptData.dpNameMar}</option>
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>

					<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:select class="form-control" id="serviceId" path="receivableDemandEntryDto.serviceId" onchange="" disabled="true">
							<option value="">Select Service</option>
						</form:select>
					</div>

				</div>
				<div class="form-group ">
					<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.casNo" text="Case No." /></label>
					<div class="col-sm-4">
						<form:input path="receivableDemandEntryDto.caseNo" onchange="" readonly="" cssClass="form-control  mandColorClass " autocomplete="off" id="caseNo" disabled="true" />
					</div>

					<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.locWard" text="Location" /></label>
					<div class="col-sm-4">
						<form:input path="receivableDemandEntryDto.locName" onchange="" readonly="true" cssClass="form-control  mandColorClass " autocomplete="off" id="LocName" disabled="true" />

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control " id="" for=""><spring:message code="receivable.demand.entry.ccn.idn" text="IDN/CCN Number" /></label>
					<div class="col-sm-4 ">
						<form:input path="receivableDemandEntryDto.customerDetails.ccnNumber" cssClass="form-control " id="" readonly="true" disabled="true" />
					</div>
				</div>
				<div class="panel-group accordion-toggle" id="accordion_single_collapse">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-target="#a1" data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#collapse1"> <spring:message code="" text="Consumer Details" />
							</a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group padding-top-20">
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.custName" text="Consumer Name" /></label>
								<div class="col-sm-4">

									<form:input path="receivableDemandEntryDto.customerDetails.fName" onchange="" readonly="true" cssClass="form-control  mandColorClass required-control" autocomplete="off" id="custName"
										disabled=""
									/>

								</div>
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.custAdd" text="Consumer Address" /></label>
								<div class="col-sm-4">

									<form:textarea path="receivableDemandEntryDto.customerDetails.areaName" cssClass="form-control  required-control text-left" id="custAddress" readonly="true" disabled="" />


								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.loc" text="Location" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.houseComplexName" onchange="" readonly="true" cssClass="form-control  mandColorClass " autocomplete="off" id="custLocation"
										disabled=""
									/>
								</div>
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.khasara.number" text="khasra number" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.roadName" cssClass="form-control   text-left" id="custKhasaraNo" readonly="true" disabled="" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.district" text="City" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.cityName" cssClass="form-control text-left" id="cityName" readonly="true" disabled="" />
								</div>
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.pincode" text="PinCode" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.pincodeNo" onchange="" readonly="true" cssClass="form-control  mandColorClass hasPincode" autocomplete="off" id="pinCode"
										disabled=""
									/>
								</div>

							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label" for=""><spring:message code="receivable.demand.entry.mobNo" text="Mobile No." /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.mobileNo" cssClass="form-control text-left hasMobileNo" data-rule-number="10" data-rule-minLength="10" data-rule-maxLength="10"
										id="custMobile" readonly="true" disabled=""
									/>
								</div>
								<label class="col-sm-2 control-label" for=""><spring:message code="receivable.demand.entry.emailid" text="Email-ID" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.email" cssClass="form-control hasemailclass text-left" id="custEmailId" readonly="true" disabled="" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<c:set var="d" value="0" scope="page" />

				<div class="table-responsive clear">
					<table class="table table-bordered table-striped" id="id_receivableDemandEntryTbl">
						<thead>
							<tr>
								<th colspan="${6}" class="text-center" rowspan="1"><spring:message code="receivable.demand.entry.demand.det" text="Demand Details" /></th>
							</tr>
							<tr>
								<th scope="col" width="3%"><spring:message code="" text="Sr.No." /><input type="hidden" id="srNo"></th>
								<th scope="col" width="10%"><spring:message code="receivable.demand.entry.collType" text="Collection Type" /></th>
								<th scope="col" width="10%"><spring:message code="receivable.demand.entry.desc" text="Description" /></th>
								<th scope="col" width="10%"><spring:message code="receivable.demand.entry.accCode" text="Account Code" /></th>
								<th scope="col" width="10%"><spring:message code="receivable.demand.entry.amnt" text="Amount" /></th>
							</tr>

						</thead>
						<tfoot>
							<tr>

								<th></th>

								<th colspan="${2}" class="text-center" rowspan="1"></th>
								<th align="left"><spring:message code="receivable.demand.entry.total.amnt" text="Total Amount" /></th>
								<th><form:input path="receivableDemandEntryDto.billAmount" cssClass="form-control mandColorClass  text-right" onchange="" id="id_grand_Total" disabled="" readonly="true" /></th>

							</tr>
						</tfoot>
						<tbody>
							<c:choose>
								<c:when test="${command.receivableDemandEntryDto.sliStatus eq 'Active'}">
									<c:forEach var="demandInfo" items="${command.receivableDemandEntryDto.rcvblDemandList}" varStatus="status">
										<c:if test="${demandInfo.isDeleted eq 'N'}">
											<tr class="firstUnitRow">
												<td align="center" width="2%"><form:input path="" cssClass=" text-center form-control mandColorClass " id="sequence${d}" value="${d+1}" disabled="true" /></td>
												<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory1" cssClass="form-control mandColorClass" id="taxCategory${d+1}"
														onchange="showTaxSubCategoryList(${d})" disabled="true" data-rule-required="true"
													>
														<form:option value="">
															<spring:message code="solid.waste.select" text="select" />
														</form:option>
														<c:if test="${userSession.languageId eq 1}">
															<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">

																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>

															</c:forEach>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">
																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangSecond}</form:option>
															</c:forEach>
														</c:if>
													</form:select></td>
												<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory2" cssClass="form-control mandColorClass" id="taxCategory${d+2}"
														onchange="getAccountCode(${d})" disabled="true" data-rule-required="true"
													>
														<form:option value="">
															<spring:message code="solid.waste.select" text="select" />
														</form:option>
														<c:if test="${userSession.languageId eq 1}">
															<c:forEach items="${command.getSecondLevelData('TAC',2)}" var="lookup">

																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>


															</c:forEach>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<c:forEach items="${command.getSecondLevelData('TAC',2)}" var="lookup">

																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangSecond}</form:option>

															</c:forEach>
														</c:if>
													</form:select></td>



												<td><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].sacHeadId" cssClass="form-control mandColorClass" id="accHead${d}" disabled="true" data-rule-required="true">
														<form:option value="">
															<spring:message code="solid.waste.select" />
														</form:option>
														<c:forEach items="${accountHeadCode}" var="lookup">
															<form:option value="${lookup.sacHeadId}">${lookup.acHeadCode}</form:option>
														</c:forEach>
													</form:select></td>

												<td><form:input path="receivableDemandEntryDto.rcvblDemandList[${d}].billDetailsAmount" cssClass="form-control  text-right hasNumber" onchange="sum1()" onkeyup="hasNumber1(${d})"
														id="id_total${d}" disabled="true" maxlength="9"
													/></td>


											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach var="demandInfo" items="${command.receivableDemandEntryDto.rcvblDemandList}" varStatus="status">
										<c:if test="${demandInfo.isDeleted eq 'N'}">
											<tr class="secondUnitRow">
												<td align="center" width="2%"><form:input path="" cssClass=" text-center form-control mandColorClass " id="sequence${d}" value="${d+1}" disabled="true" /></td>


												<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory1" cssClass="form-control mandColorClass" id="taxCategory${d+1}"
														onchange="showTaxSubCategoryList(${d})" disabled="true" data-rule-required="true"
													>
														<form:option value="">
															<spring:message code="solid.waste.select" text="select" />
														</form:option>
														<c:if test="${userSession.languageId eq 1}">
															<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">
																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
															</c:forEach>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">
																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangSecond}</form:option>
															</c:forEach>
														</c:if>
													</form:select></td>
												<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory2" cssClass="form-control mandColorClass" id="taxCategory${d+2}"
														onchange="getAccountCode(${d})" disabled="true" data-rule-required="true"
													>
														<form:option value="">
															<spring:message code="solid.waste.select" text="select" />
														</form:option>
														<c:if test="${userSession.languageId eq 1}">
															<c:forEach items="${command.getSecondLevelData('TAC',2)}" var="lookup">
																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>

															</c:forEach>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<c:forEach items="${command.getSecondLevelData('TAC',2)}" var="lookup">

																<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangSecond}</form:option>

															</c:forEach>
														</c:if>
													</form:select></td>



												<td><form:input path="receivableDemandEntryDto.rcvblDemandList[${d}].accNo" cssClass="form-control  text-right" onchange="" id="accHead${d}" disabled="" readonly="true" /></td>
												<td><form:input path="receivableDemandEntryDto.rcvblDemandList[${d}].billDetailsAmount" cssClass="form-control  text-right hasNumber" onchange="sum1()" onkeyup="hasNumber1(${d})"
														id="id_total${d}" disabled="true" maxlength="9"
													/></td>


											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>

						</tbody>
					</table>

				</div>

				<div class="form-group padding-top-20">
					<apptags:date fieldclass="datepicker" labelCode="receivable.demand.entry.demand.date" isDisabled="true" datePath="receivableDemandEntryDto.billDate" isMandatory="true"
						cssClass="custDate text-left"
					>
					</apptags:date>


				</div>
				<div class="text-center padding-top-20" id="divSubmit">
					<button type="button" class="btn btn-danger" onclick="javascript:openRelatedForm('ReceivableDemandEntry.html');">
						<i class="fa fa-chevron-circle-left padding-right-5" aria-hidden="true"></i>
						<spring:message code="back.msg" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>





