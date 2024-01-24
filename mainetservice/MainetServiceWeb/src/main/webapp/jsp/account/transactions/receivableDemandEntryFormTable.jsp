<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/account/receivableDemandEntry.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/cfc/challan/offlinePay.js"></script>

<div class="panel-group accordion-toggle" id="accordion_single_collapse">
	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-target="#a1" data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#collapse1"> <spring:message code="receivable.demand.entry.consumer.details"
					text="Consumer Details"
				/>
			</a>
		</h4>
	</div>
	<div id="a1" class="panel-collapse collapse in">
		<div class="panel-body">

			<div class="form-group padding-top-20">
				<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.custName" text="Consumer Name" /></label>
				<div class="col-sm-4">

					<form:input path="receivableDemandEntryDto.customerDetails.fName" onchange="" readonly="" cssClass="form-control  mandColorClass required-control" autocomplete="off" id="custName" disabled="" maxlength="100"/>

				</div>
				<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.custAdd" text="Consumer Address" /></label>
				<div class="col-sm-4">

					<form:textarea path="receivableDemandEntryDto.customerDetails.areaName" cssClass="form-control  required-control text-left" id="custAddress" readonly="" disabled="" />


				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.loc" text="Location" /></label>
				<div class="col-sm-4">
					<form:input path="receivableDemandEntryDto.customerDetails.houseComplexName" onchange="" readonly="" cssClass="form-control  mandColorClass " autocomplete="off" id="custLocation" disabled="" maxlength="250"/>
				</div>
				<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.khasara.number" text="khasra number" /></label>
				<div class="col-sm-4">
					<form:input path="receivableDemandEntryDto.customerDetails.roadName" cssClass="form-control   text-left" id="custKhasaraNo" readonly="" disabled="" maxlength="50"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.district" text="City" /></label>
				<div class="col-sm-4">
					<form:input path="receivableDemandEntryDto.customerDetails.cityName" cssClass="form-control text-left" id="cityName" readonly="" disabled="" maxlength="40"/>
				</div>
				<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.pincode" text="PinCode" /></label>
				<div class="col-sm-4">
					<form:input path="receivableDemandEntryDto.customerDetails.pincodeNo" onchange="" readonly="" cssClass="form-control  mandColorClass hasPincode" autocomplete="off" id="pinCode" disabled="" maxlength="6"/>
				</div>

			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for=""><spring:message code="receivable.demand.entry.mobNo" text="Mobile No." /></label>
				<div class="col-sm-4">
					<form:input path="receivableDemandEntryDto.customerDetails.mobileNo" cssClass="form-control text-left hasMobileNo" maxLength="10"
						id="custMobile" readonly="" disabled=""
					/>
				</div>
				<label class="col-sm-2 control-label" for=""><spring:message code="receivable.demand.entry.emailid" text="Email-ID" /></label>
				<div class="col-sm-4">
					<form:input path="receivableDemandEntryDto.customerDetails.email" cssClass="form-control hasemailclass text-left" id="custEmailId" readonly="" disabled="" maxlength="20"/>					
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
				<th scope="col" width="3%"><spring:message code="bill.action" text="Action" /></th>
			</tr>

		</thead>
		<tfoot>
			<tr>
				<th colspan="3" class="text-center" rowspan="1"></th>
				<th align="left"><spring:message code="receivable.demand.entry.total.amnt" text="Total Amount" /></th>
				<th><form:input path="receivableDemandEntryDto.billAmount" cssClass="form-control mandColorClass  text-right" onchange="" id="id_grand_Total" disabled="" readonly="true" /></th>
				<th width="10%"></th>
			</tr>
		</tfoot>
		<tbody>
			<c:choose>
				<c:when test="${command.receivableDemandEntryDto.sliStatus eq 'Active'}">
					<tr class="firstUnitRow">
						<td align="center" width="2%"><form:input path="" cssClass=" text-center form-control mandColorClass " id="sequence${d}" value="${d+1}" disabled="true" /></td>

						<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory1" cssClass="form-control mandColorClass" id="taxCategory${d+1}"
								onchange="showTaxSubCategoryList(${d})" disabled="" data-rule-required="true"
							>
								<form:option value="">
									<spring:message code="solid.waste.select" text="select" />
								</form:option>
								<c:if test="${userSession.languageId eq 1}">
									<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">
										<c:if test="${taxCat.contains( lookup.lookUpId)}">
											<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">
										<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangSecond}</form:option>
									</c:forEach>
								</c:if>
							</form:select></td>
						<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory2" cssClass="form-control mandColorClass" id="taxCategory${d+2}"
								onchange="getAccountCode(${d})" disabled="" data-rule-required="true"
							>
								<form:option value="">
									<spring:message code="solid.waste.select" text="select" />
								</form:option>
							</form:select></td>


						<td><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].sacHeadId" cssClass="form-control mandColorClass" id="accHead${d}" data-rule-required="true">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${accountHeadCode}" var="lookup">
									<form:option value="${lookup.sacHeadId}">${lookup.acHeadCode}</form:option>
								</c:forEach>
							</form:select></td>

						<td><form:input path="receivableDemandEntryDto.rcvblDemandList[${d}].billDetailsAmount" cssClass="form-control  text-right hasNumber" onkeyup="hasNumber1(${d})" onchange="sum1()" id="id_total${d}" disabled="" maxlength="9"/></td>
						<td align="center" width="10%"><a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" onclick="addEntryData('id_receivableDemandEntryTbl','firstUnitRow');"
							class=" btn btn-success btn-sm"
						><i class="fa fa-plus-circle"> </i></a> <a class="btn btn-danger btn-sm delButton" title="Delete" onclick="deleteEntry('id_receivableDemandEntryTbl',$(this),'sequence${d}','firstUnitRow')" id="deleteRow(${d})"> <i
								class="fa fa-minus"
							></i>
						</a></td>

						<form:hidden path="receivableDemandEntryDto.rcvblDemandList[${d}].taxId" id="taxId${d }" />

					</tr>
				</c:when>
				<c:otherwise>
					<tr class="secondUnitRow">
						<td align="center" width="2%"><form:input path="" cssClass=" text-center form-control mandColorClass " id="sequence${d}" value="${d+1}" disabled="true" /></td>

						<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory1" cssClass="form-control mandColorClass" id="taxCategory${d+1}"
								onchange="showTaxSubCategoryList(${d})" disabled="" data-rule-required="true"
							>
								<form:option value="">
									<spring:message code="solid.waste.select" text="select" />
								</form:option>
								<c:if test="${userSession.languageId eq 1}">
									<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">
										<c:if test="${taxCat.contains( lookup.lookUpId)}">
											<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<c:forEach items="${command.getSecondLevelData('TAC',1)}" var="lookup">
										<form:option value="${lookup.lookUpId}" code="${lookup.lookUpCode}">${lookup.descLangSecond}</form:option>
									</c:forEach>
								</c:if>
							</form:select></td>
						<td align="center"><form:select path="receivableDemandEntryDto.rcvblDemandList[${d}].taxCategory2" cssClass="form-control mandColorClass" id="taxCategory${d+2}"
								onchange="getAccountCode(${d})" disabled="" data-rule-required="true"
							>
								<form:option value="">
									<spring:message code="solid.waste.select" text="select" />
								</form:option>
							</form:select></td>


						<td><form:input path="receivableDemandEntryDto.rcvblDemandList[${d}].accNo" cssClass="form-control  text-right" onchange="" id="accHead${d}" disabled="" readonly="" /></td>
						<td><form:input path="receivableDemandEntryDto.rcvblDemandList[${d}].billDetailsAmount" cssClass="form-control  text-right hasNumber" onkeyup="hasNumber1(${d})" onchange="sum1()" id="id_total${d}" disabled="" maxlength="9"/></td>
						<td align="center" width="10%"><a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" onclick="addEntryData('id_receivableDemandEntryTbl','secondUnitRow');"
							class=" btn btn-success btn-sm"
						><i class="fa fa-plus-circle"> </i></a> <a class="btn btn-danger btn-sm delButton" title="Delete" onclick="deleteEntry('id_receivableDemandEntryTbl',$(this),'sequence${d}','secondUnitRow')" id="deleteRow(${d})"> <i
								class="fa fa-minus"
							></i>
						</a></td>

						<form:hidden path="receivableDemandEntryDto.rcvblDemandList[${d}].taxId" id="taxId${d }" />


					</tr>
				</c:otherwise>
			</c:choose>

		</tbody>
	</table>

</div>

<div class="form-group padding-top-20">
	<apptags:date fieldclass="datepicker" labelCode="receivable.demand.entry.demand.date" datePath="receivableDemandEntryDto.billDate" isMandatory="true" cssClass="custDate text-left" isDisabled="true">
	</apptags:date>
</div>


<div class="text-center padding-top-20" id="divSubmit">
	<c:if test="${command.saveMode ne 'V'}">

		<button type="button" class="btn btn-success btn-submit" id="submit" onclick="Proceed(this)">
			<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
			<spring:message code="master.save" text="Save" />
		</button>
	</c:if>
	<c:if test="${command.saveMode eq 'C'}">

		<button type="reset" class="btn btn-warning " title="Reset" onclick="openaddForm('ReceivableDemandEntry.html','openForm')">
			<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
			<spring:message code="water.btn.reset" text="Reset" />
		</button>
	</c:if>
	<button type="button" class="btn btn-danger" onclick="javascript:openRelatedForm('ReceivableDemandEntry.html');">
		<i class="fa fa-chevron-circle-left padding-right-5" aria-hidden="true"></i>
		<spring:message code="back.msg" text="Back" />
	</button>


</div>
