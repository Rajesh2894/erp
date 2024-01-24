<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.*,java.util.*"%>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css"
	rel="stylesheet" type="text/css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/contract/contractAgreement.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<style>
/* #ApprAmt-div{
	display:none;
} */
</style>
<script>
$(document).ready(function(){
	
	var icon="<i class='fa fa-inr'></i>";
	$('#textIcon').html(icon);
	$('#leavyPenaltyIcon').html(icon);
	showHidefileds();
	showHideLeavyPenaltyfileds();
	
	if($('.ContractMode').attr("value") == "C"){
		$("#Appreciation").show();
		
	}else{
		$("#Appreciation").hide();
	}
	
	
});
$('#apprApplicable').change(function(){

	showHidefileds();
});
$('#leavyPenalty').change(function(){
	showHideLeavyPenaltyfileds();
});
$('#apprType').change(function(){
	
	changeIcon();
});
$('#penaltyMode').change(function(){
	changeleavyPenaltyIcon();
});

	$(document)
			.ready(
					function() {
						$('#submitInstall')
								.click(
										function() {
											checkInstallDate();
											var errorList = [];
											if (checkInstallDate()) {
												var total = $("#id_noa").val();
												if (total != "" && total != '0') {
													var i = 1;
													var path = 0;
													$("#noOfInstallmentTable")
															.find("tr:gt(0)")
															.remove();
													$('#noa_header').show();
													while (i <= total) {
														$('.tab_Application')
																.append(
																		'<tr class="appendableClassInstallments"><td><input type="text" class="form-control" disabled size="5" value="'+i+'"/></td>'
																				+ '<td><select name="contractMastDTO.contractInstalmentDetailList['+path+'].conitAmtType" class="form-control mandColorClass" id="PaymentTerms'+path+'">'
																				+ '<c:set var="baseLookupCode" value="VTY" /> <option value="0"> Select </option> <c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
																				+ '<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option> </c:forEach></td>'
																				+ '<input type="hidden" name="contractMastDTO.contractInstalmentDetailList['+path+'].active"  id="presentRow'+path+'" value="Y"/> '
																				+ '<td><input type="text" name="contractMastDTO.contractInstalmentDetailList['
																				+ path
																				+ '].conitValue" onkeypress="return hasAmount(event, this, 13, 2)"  class="form-control text-right" name="amt" id="amt'
																				+ path
																				+ '" /></td>'
																				+ '<td><div class="input-group"><input type="text" id="installmentsDate'+path+'"  name="contractMastDTO.contractInstalmentDetailList['+path+'].conitDueDate" class="form-control dateClass mandColorClass Insdatepicker"><span class="input-group-addon"><i class="fa fa-calendar"></i></span></div></td>'
																				+ '<td><input type="text" size="5" name="contractMastDTO.contractInstalmentDetailList['+path+'].conitMilestone" id="mileStone'+path+'" class="form-control" /></td></tr>');
														i = i + 1;
														path++;
													}
													while (i - 1 > total) {
														$(
																".tab_Application tr:last")
																.remove();
														i = i - 1;
													}
													$('</table>').appendTo(
															'.tab_Application');
												} else {
													//alert("please Enter some valid value");
													errorList
															.push("please Enter No. of Installments");
													displayErrorsOnPage(errorList);
												}
											}
										});

						var newTermCon = $('#customFields2 tr').length;
						var count = 2;
						$(".addCF2")
								.click(
										function() {
											$("#customFields2")
													.append(
															'<tr class="appendableTermConClass"> <td width="50"><input type="text" class="form-control text-center hasNumber" id="sNo'+newTermCon+'" value="'+count+'" /></td>'
																	+ '<td> <textarea name="contractMastDTO.contractTermsDetailList['+newTermCon+'].conttDescription" cols="" rows="" class="form-control mandColorClass" id="termCon'+newTermCon+'"></textarea> </td>'
																	+ '<input type="hidden" name="contractMastDTO.contractTermsDetailList['+newTermCon+'].active"  id="presentRow'+newTermCon+'" value="Y"/> '
																	+ '<td><a title="Delete" class="btn btn-danger remCF2" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
											count++;
											newTermCon++;
											reorderTermCon();
										});
						$("#customFields2")
								.on(
										'click',
										'.remCF2',
										function() {
											{
												if ($("#customFields2 tr").length != 2) {
													$(this).parent().parent()
															.remove();
													reorderTermCon();
													newTermCon--;
												} else {
													//alert("You cannot delete first row");
													errorList
															.push("You cannot delete first row");
													displayErrorsOnPage(errorList);
												}
											}
										});

					});

	$(document)
			.ready(
					function() {
						var newULBWit = $('#customFields3 tr').length;
						var uploadCount = 2;
						var uploadtype = "UW";
						var errorList = [];
						$(".addCF3")
								.click(
										function() {

											$("#customFields3")
													.append(
															'<tr class="appendableClass"> <td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Name" type="text" class="form-control mandColorClass" id="contp1Name'+newULBWit+'"/> </td> '
																	+ '<td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Address" type="text" class="form-control mandColorClass" id="contp1Address'+newULBWit+'"/> </td> '
																	+ '<td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12"  id="contp1ProofIdNo'+newULBWit+'"/> </td>'
																	+ '<input type="hidden" name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Type"  id="ULBType'+newULBWit+'" value="W"> '
																	+ '<input type="hidden" name="contractMastDTO.contractPart1List['+newULBWit+'].active"  id="presentRow'+newULBWit+'" value="Y"/> '
																	+ '<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload('
																	+ uploadCount
																	+ ',\''
																	+ uploadtype
																	+ '\')"><i class="fa fa-camera"></i> Upload & View</a></td>'
																	+ '<td><a title="Delete" class="btn btn-danger remCF3" onclick="deleteCompleteRow('
																	+ uploadCount
																	+ ',\''
																	+ uploadtype
																	+ '\')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
											newULBWit++;
											uploadCount++;
											reorderULB();
										});
						$("#customFields3")
								.on(
										'click',
										'.remCF3',
										function() {
											{
												if ($("#customFields3 tr").length != 2) {
													$(this).parent().parent()
															.remove();
													reorderULB();
													newULBWit--;
													uploadCount--;
												} else {
													//alert("You cannot delete first row");
													errorList
															.push("You cannot delete first row");
													displayErrorsOnPage(errorList);
												}
											}
										});
					});
</script>


<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="contract.breadcrumb.contractAgreement" />
		</h2>
		<apptags:helpDoc url="ContractAgreement.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="contract.breadcrumb.fieldwith" />
				<i class="text-red-1">*</i> <spring:message
					code="contract.breadcrumb.ismandatory" /> </span>
		</div>
		<form:form action="ContractAgreement.html"
			class="form-horizontal form" name="ContractAgreement"
			id="ContractAgreement">
			<form:hidden path="contractMastDTO.contractType" id="contrType" />
			<form:hidden id="hideTaxId" path=""
				value="${command.contractMastDTO.taxId}" />

			<c:if
				test="${command.modeType ne 'V' && not empty command.contractMastDTO.loaNo}">
				<form:hidden path="contractMastDTO.contTndDate" id="" />
				<form:hidden path="contractMastDTO.contTndNo" id="" />
				<%-- <form:hidden path="contractMastDTO.contRsoDate" id="" /> --%>
				<%-- <form:hidden path="contractMastDTO.contRsoNo" id="" /> --%>
				<%-- <form:hidden path="contractMastDTO.contractType" id="contrType" /> --%>


			</c:if>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span>&times;</span>
				</button>
				<span id="errorId"></span>
			</div>


			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#Contract"><spring:message
									code="contract.label.contract" /></a>
						</h4>
					</div>
					<div id="Contract" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="AgreementDate"><spring:message
										code="contract.label.AgreementEntryDate" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="contractMastDTO.contDate" type="text"
											class="form-control lessdatepicker mandColorClass dateClass"
											id="AgreementDate" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
								<c:if
									test="${command.modeType ne 'C' && not empty command.contractMastDTO.contNo}">
									<label class="col-sm-2 control-label" for="ContractToDate"><spring:message
											code="master.contract.no" /></label>
									<div class="col-sm-4">
										<form:input type='text' path=""
											value="${command.contractMastDTO.contNo}"
											class='form-control text-right' readonly="true"
											id="contToPeriod" />
									</div>
								</c:if>
							</div>
							<div class="form-group">

								<label class="col-sm-2 control-label required-control"><spring:message
										code="agreement.tndDept" text="Tender Department" /></label>
								<div class="col-sm-4">
									<form:select path="contractMastDTO.contDept"
										class="form-control chosen-select-no-results mandColorClass"
										id="tndDept" onchange="getAllTaxesBasedOnDept();">
										<form:option value="">
											<spring:message code="common.master.select.dept" text=""></spring:message>
										</form:option>
										<c:forEach items="${command.getMapDeptList()}" var="lookUp">
											<form:option value="${lookUp.dpDeptid}"
												code="${lookUp.dpDeptcode}">${lookUp.dpDeptdesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<div class="hidden" id="CAloanNo">
									<label class="col-sm-2 control-label"><spring:message
											code="agreement.LOA.number" text="Letter Of Acceptance No." /></label>
									<div class="col-sm-4">
										<c:choose>
											<c:when test="${command.modeType eq 'C'}">
												<div class="input-group">
													<form:input path="contractMastDTO.loaNo" type="text"
														class="form-control " id="loaNo"
														readonly="${not empty command.contractMastDTO.loaNo ? true:false}" />

													<a href="#" class="input-group-addon"
														onclick="getTenderDetails();"><i class="fa fa-search"></i></a>
												</div>
											</c:when>
											<c:otherwise>
												<form:input path="contractMastDTO.loaNo" type="text"
													class="form-control " id="loaNo"
													readonly="${not empty command.contractMastDTO.loaNo ? true:false}" />
											</c:otherwise>
										</c:choose>
										<c:if test="${command.modeType eq 'C'}">
											<span class="text-small red"><spring:message
													code="agreement.LOA.Enternumber" /></span>
										</c:if>

									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="ResulationNo"><spring:message
										code="contract.label.resolutionNo" /></label>
								<div class="col-sm-4">
									<form:input path="contractMastDTO.contRsoNo" type="text"
										class="form-control mandColorClass" id="resulationNo" />
								</div>
								<label class="col-sm-2 control-label required-control"
									for="ResulationDate"><spring:message
										code="contract.label.resolutionDate" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="contractMastDTO.contRsoDate" type="text"
											class="form-control dateClass datepickerResulation mandColorClass"
											id="resolutionDate" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="TenderName"><spring:message
										code="contract.label.tenderNo" /></label>
								<div class="col-sm-4">
									<form:input path="contractMastDTO.contTndNo" type="text"
										class="form-control mandColorClass" id="TenderNo"
										disabled="${not empty command.contractMastDTO.loaNo ? true:false}" />
								</div>
								<label class="col-sm-2 control-label required-control"
									for="TenderDate"><spring:message
										code="contract.label.tenderDate" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="contractMastDTO.contTndDate" type="text"
											class="form-control datepickerTender dateClass mandColorClass"
											id="tenderDate" onkeyup="clearInput($(this));"
											disabled="${not empty command.contractMastDTO.loaNo ? true:false}" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
							</div>
							<!-- 			change by Suhel -->
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="ContractType"><spring:message
										code="contract.label.contractType" /></label>
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="CNT" />
									<form:select path="contractMastDTO.contType"
										class="form-control chosen-select-no-results contractType"
										id="ContractType" onchange="hideDetails();" disabled="">
										<form:option value="0">
											<spring:message code="master.select.contract.type"
												text="Select Contract Type"></spring:message>
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>

								<label class="col-sm-2 control-label"><spring:message
										code="agreement.defect.liability"
										text="Defect Liability Period" /></label>

								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type='text'
											path="contractMastDTO.contractDetailList[0].contDefectLiabilityPer"
											class='form-control hasNumber' placeholder="" id="vc" />
										<div class='input-group-field'>
											<c:set var="baseLookupCode" value="UTS" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="contractMastDTO.contractDetailList[0].contDefectLiabilityUnit"
												cssClass="form-control required-control chosen-select-no-results"
												isMandatory="true" selectOptionLabelCode="selectdropdown"
												hasId="true"
												disabled="${command.modeType eq 'V' ? true :false}" />

										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="ContractFromDate"><spring:message
										code="contract.label.contractDate" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input
											path="contractMastDTO.contractDetailList[0].contFromDate"
											type="text" onkeyup="clearInput($(this));"
											class="form-control dateClass lessthancurrdatefrom mandColorClass"
											id="contractFromDate" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="Contract Period" /></label>

								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type='text'
											path="contractMastDTO.contractDetailList[0].contToPeriod"
											class='form-control hasNumber text-right' placeholder=""
											id="contToPeriod" />
										<div class='input-group-field'>

											<c:set var="baseLookupCode" value="UTS" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="contractMastDTO.contractDetailList[0].contToPeriodUnit"
												cssClass="form-control required-control chosen-select-no-results"
												isMandatory="true" selectOptionLabelCode="selectdropdown"
												changeHandler="dueDateUpdate()" hasId="true" />


										</div>
									</div>
								</div>

							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label " for="ContractFromDate"><spring:message
										code="contract.label.contrtToDate" text="Contract To Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<c:choose>
											<c:when
												test="${command.modeType eq 'V' || command.modeType eq 'E' }">
												<form:input
													path="contractMastDTO.contractDetailList[0].contToDate"
													type="text" onkeyup="clearInput($(this));"
													class="form-control dateClass lessthancurrdatefrom mandColorClass"
													id="contractToDate" disabled="true" />
											</c:when>


											<c:otherwise>
												<form:input path="" type="text"
													onkeyup="clearInput($(this));"
													class="form-control dateClass lessthancurrdatefrom mandColorClass"
													id="contractToDate" disabled="true" />
											</c:otherwise>
										</c:choose>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
								<label class="col-sm-2 control-label required-control"
									for="ContractAmount"><spring:message
										code="contract.label.contractAmount" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input
											path="contractMastDTO.contractDetailList[0].contractAmt"
											type="text" maxlength="12"
											onblur="getAmountFormatInDynamic((this),'ContractAmount')"
											class="form-control text-right mandColorClass"
											id="ContractAmount" />
										<span class="input-group-addon"><i class="fa fa-inr"></i></span>
									</div>
								</div>
							</div>

							<form:hidden path="modeType" id="hiddeMode" />
							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="contract.label.contractMode" /> <span class="mand">*</span></label>
								<div class="col-sm-4">
									<label class="radio-inline margin-top-5"> <form:radiobutton
											path="contractMastDTO.contMode" class="ContractMode"
											id="commercial" value="C"
											disabled="${command.modeType ne 'C' ? true : false}" /> <spring:message
											code="contract.label.commercial" /></label> <label
										class="radio-inline margin-top-5" id="nonCommercial">
										<form:radiobutton path="contractMastDTO.contMode"
											class="ContractMode" value="N"
											disabled="${command.modeType ne 'C' ? true : false}" /> <spring:message
											code="contract.label.nonCommercial" />
									</label>
								</div>
								<div id="Renewal">
									<label class="control-label col-sm-2" for="AllowRenewal"><spring:message
											code="contract.label.allowRenewal" /></label>
									<div class="col-sm-4">
										<form:select path="contractMastDTO.contRenewal"
											class="form-control" id="allowRenewal">
											<form:option value="0">
												<spring:message code="master.selectDropDwn" text="Select"></spring:message>
											</form:option>
											<form:option value="Y">
												<spring:message code="contract.master.yes" text="Yes"></spring:message>
											</form:option>
											<form:option value="N">
												<spring:message code="contract.master.no" text="No"></spring:message>
											</form:option>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group" id="Appreciation">
								<label class="col-sm-2 control-label"><spring:message
										code="contract.label.aapprAppl" text="Appreciation" /> </label>
								<c:set var="baseLookupCode" value="APA" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="contractMastDTO.apprApplicable" cssClass="form-control"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showOnlyLabel="applicantinfo.label.title" />
								<div id="ApprAmt-div">
									<label class="control-label col-sm-2" for="AllowRenewal"><spring:message
											code="contract.label.aprType" text="Appreciation Type" /></label>
									<c:set var="baseLookupCode1" value="VTY" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode1)}"
										path="contractMastDTO.apprType" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="applicantinfo.label.title" />	</br></br>
									<label class="col-sm-2 control-label"
										for="ContractAmount"><spring:message
											code="contract.label.apprAmt" text="Appreciation Amount" /></label>
									<div class="col-sm-4">
								
										<div class="input-group">
											<form:input
												path="contractMastDTO.apprAmt"
												type="text" maxlength="12"
												onblur="getAmountFormatInDynamic((this),'ContractAmount')"
												class="form-control text-right mandColorClass"
												id="ApprAmt" />
											<span class="input-group-addon" id="textIcon"></i></span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="contract.label.leavyPenalty" text="Leavy Penalty Applicable" /> </label>
								<c:set var="baseLookupCode" value="LPA" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="contractMastDTO.leavyPenalty" cssClass="form-control"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showOnlyLabel="applicantinfo.label.title" />
								<div id="leavyPenalty-div">
									<label class="control-label col-sm-2" for="AllowRenewal"><spring:message
											code="contract.label.penaltyMode" text="Leavy Penalty Calculation Method" /></label>
									<c:set var="baseLookupCode1" value="VTY" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode1)}"
										path="contractMastDTO.penaltyMode" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="applicantinfo.label.title" />
									</br>
									</br> <label class="col-sm-2 control-label" for="ContractAmount"><spring:message
											code="contract.label.penaltyAmt" text="Penalty Amount" /></label>
									<div class="col-sm-4">

										<div class="input-group">
											<form:input path="contractMastDTO.penaltyAmt" type="text"
												maxlength="12"
												onblur="getAmountFormatInDynamic((this),'ContractAmount')"
												class="form-control text-right mandColorClass" id="ApprAmt" />
											<span class="input-group-addon" id="leavyPenaltyIcon"></i></span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label" for="ContractPayment"><spring:message
										code="contract.label.contractPayment" /> <span class="mand">*</span></label>
								<div class="col-sm-4">
									<form:select path="contractMastDTO.contPayType"
										class="form-control" id="ContractPayment">

										<form:option value="0">
											<spring:message code="master.selectDropDwn" text="Select"></spring:message>
										</form:option>
										<form:option value="P">
											<spring:message code="contract.master.payable" text="Payable"></spring:message>
										</form:option>
										<form:option value="R">
											<spring:message code="contract.master.receivable"
												text="Receivable"></spring:message>
										</form:option>
									</form:select>
								</div>

							</div>
						</div>
						<div class="form-group" id="taxGroup">
							<label class="col-sm-2 control-label required-control"
								for="ContractType"><spring:message
									code="contract.label.taxCode" /></label>
							<div class="col-sm-4">
								<c:if
									test="${command.modeType ne 'V' && command.modeType ne 'E'}">
									<form:select path="contractMastDTO.taxId" class="form-control"
										id="taxId">
										<form:option value="0">
											<spring:message code="agreement.selectTax" />
										</form:option>
									</form:select>
								</c:if>
								<c:if
									test="${command.modeType eq 'V' || command.modeType eq 'E'}">
									<form:select path="contractMastDTO.taxId" class="form-control"
										id="taxId">
										<form:option value="0">
											<spring:message code="rnl.master.select.tax"
												text="Select tax"></spring:message>
										</form:option>
										<c:forEach items="${command.contractMastDTO.getTaxCodeList()}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</c:if>
							</div>
						</div>
						<div class="form-group " id="NoofInstallments">
							<label class="col-sm-2 control-label required-control"
								for="NoofInstallments"><spring:message
									code="contract.label.noOfInstallments" /></label>
							<div class="col-sm-4">
								<form:input
									path="contractMastDTO.contractDetailList[0].contInstallmentPeriod"
									type="text" class="form-control hasNumber mandColorClass"
									id="id_noa" />
							</div>
							<button type="button" class="btn btn-success" id="submitInstall">
								<spring:message code="contract.label.createInstallment" />
							</button>
						</div>

						<!-- </div> -->

						<div class="table-overflow display-hide Commercial"
							id="installments">
							<table
								class="table table-bordered table-striped tab tab_Application"
								id="noOfInstallmentTable">
								<tbody>
									<tr id='noa_header' style="display: none;">
										<th width="5%"><spring:message code="contract.label.no" /></th>
										<th width="10%"><spring:message
												code="contract.label.select" /></th>
										<th width="10%"><spring:message
												code="contract.label.value" /></th>
										<th width="10%"><spring:message
												code="contract.label.dueDate" /></th>
										<th width="30%"><spring:message
												code="contract.label.Milestone" /></th>
									</tr>
									<c:if
										test="${command.modeType eq 'V' || command.modeType eq 'E'|| command.modeType eq 'C'}">
										<c:forEach
											items="${command.getContractMastDTO().getContractInstalmentDetailList()}"
											var="details" varStatus="status">
											<tr class="appendableClassInstallments">
												<td><form:input path="" type="text"
														class="form-control" size="5" value="${status.count}"
														id="contractNo${status.count-1}" /></td>
												<td><form:select
														path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitAmtType"
														class="form-control mandColorClass"
														id="PaymentTerms${status.count-1}">
														<c:set var="baseLookupCode" value="VTY" />
														<form:option value="0">
															<spring:message code="rnl.master.select" text="Select"></spring:message>
														</form:option>
														<c:forEach items="${command.getLevelData(baseLookupCode)}"
															var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<td><form:input type="text"
														path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitValue"
														class="form-control text-right hasNumber"
														id="amt${status.count-1}" /></td>
												<form:hidden
													path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].active"
													id="presentRow${status.count-1}" value="Y" />
												<td><div class="input-group">
														<form:input type="text"
															id="installmentsDate${status.count-1}"
															path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitDueDate"
															class="form-control dateClass mandColorClass Insdatepicker" />
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
													</div></td>
												<td><form:input type="text" size="5"
														path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitMilestone"
														id="mileStone${status.count-1}" class="form-control" /></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#bank"><spring:message
									code="" text="SD / PG / BG Details" /></a>
						</h4>
					</div>
					<div id="bank" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="display-hide bank">

								<div class="form-group">
									<label class="col-sm-2 control-label" for="ContractPayment"><spring:message
											code="" text="Deposite type" /> </label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="DTY" />

										<form:select
											path="contractMastDTO.contractDetailList[0].contDepTyp"
											class="form-control" id="contDepTyp" disabled="false">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>

									</div>
									<label class="col-sm-2" for="SecurityDeposit"><spring:message
											code="" text="Deposite Amount" /></label>
									<div class="col-sm-4">

										<form:hidden path="" value="${command.contBGamount}"
											id="BGAmount" />
										<div class="input-group">
											<form:input
												path="contractMastDTO.contractDetailList[0].contSecAmount"
												type="text" class="form-control hasNumber text-right"
												id="SecurityDeposit"
												onkeypress="return hasAmount(event, this, 8, 2)"
												onchange="getAmountFormatInDynamic((this),'SecurityDeposit')" />
											<span class="input-group-addon"><i class="fa fa-inr"></i></span>
										</div>
									</div>
								</div>
							</div>
							<div class="display-hide Commercial">
								<div class="form-group">
									<label class="col-sm-2" for="SecurityDepositReceipt"><spring:message
											code="" text="Account No" /></label>
									<div class="col-sm-4">
										<!-- contract.label.securityDepositReceipt -->
										<form:input
											path="contractMastDTO.contractDetailList[0].contSecRecNo"
											type="text" class="form-control" id="SecurityDepositReceipt" />
									</div>
									<label class="col-sm-2" for="SecurityDepositDate"><spring:message
											code="" text="Particulars" /></label>

									<div class="col-sm-4">
										<form:input
											path="contractMastDTO.contractDetailList[0].contDepPrtcl"
											type="text" class="form-control" id="particular" />
									</div>
								</div>
								<div class="form-group">

									<label class="col-sm-2" for="SecurityDepositDate"><spring:message
											code="" text="Valid from date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input
												path="contractMastDTO.contractDetailList[0].contSecRecDate"
												type="text" onkeyup="clearInput($(this));"
												class="form-control datepicker dateClass"
												id="SecurityDepositDate" />

											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
									<label class="col-sm-2" for="SecurityDepositDate"><spring:message
											code="" text="Valid till date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input
												path="contractMastDTO.contractDetailList[0].contDepDueDt"
												type="text" onkeyup="clearInput($(this));"
												class="form-control datepicker dateClass"
												id="SecurityDepositEndDate" />

											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2"> <spring:message
											code="accounts.vendormaster.bankname">
										</spring:message>
									</label>

									<div class="col-sm-4">

										<form:select
											path="contractMastDTO.contractDetailList[0].contDepBankID"
											class="form-control" id="contDepBankID" disabled="false">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.bankMapList}" var="bankId">
												<form:option value="${bankId.key}">${bankId.value}</form:option>
											</c:forEach>
										</form:select>

									</div>
									<label class="col-sm-2 control-label" for="ContractPayment"><spring:message
											code="" text="Payment Mode" /> </label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="PAY" />
										<form:select
											path="contractMastDTO.contractDetailList[0].contDepModeId"
											class="form-control mandColorClass  required-control "
											id="contDepModeId">
											<form:option value="">

												<spring:message code="collection.receipt.Mode"></spring:message>
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												varStatus="status" var="levelChild">
												<form:option value="${levelChild.lookUpId}"
													code="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<%-- <div id="addDeposite">
									<div class="form-group">
										<label class="col-sm-2 control-label" for=""><spring:message
												code="agreement.AdditionalPerformanceSecurityDeposits"
												text="Additional Performance Security Deposits" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input
													path="contractMastDTO.contractDetailList[0].contAddPerSecurityDet"
													class="form-control text-right" id="contAddPerSecurityDet"
													onkeypress="return hasAmount(event, this, 8, 2)"
													onchange="getAmountFormatInDynamic((this),'contAddPerSecurityDet')" />
												<span class="input-group-addon"><i class="fa fa-inr"></i></span>
											</div>
										</div>

										<label class="col-sm-2 control-label" for=""><spring:message
												code="agreement.OtherDepositDetails"
												text="Other Deposit Details" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input
													path="contractMastDTO.contractDetailList[0].contOtherDepositDet"
													class="form-control text-right" id="contOtherDepositDet"
													onkeypress="return hasAmount(event, this, 8, 2)"
													onchange="getAmountFormatInDynamic((this),'contOtherDepositDet')" />
												<span class="input-group-addon"><i class="fa fa-inr"></i></span>
											</div>
										</div>

									</div>
								</div> --%>
							</div>
						</div>
					</div>
				</div>

				<%-- <div class="form-group" id="taxGroup">
					<label class="col-sm-2 control-label required-control"
						for="ContractType"><spring:message
							code="contract.label.taxCode" /></label>
					<div class="col-sm-4">
						<c:if test="${command.modeType ne 'V' && command.modeType ne 'E'}">
							<form:select path="contractMastDTO.taxId" class="form-control"
								id="taxId">
								<form:option value="0">
									<spring:message code="agreement.selectTax" />
								</form:option>
							</form:select>
						</c:if>
						<c:if test="${command.modeType eq 'V' || command.modeType eq 'E'}">
							<form:select path="contractMastDTO.taxId" class="form-control"
								id="taxId">
								<form:option value="0">
									<spring:message code="rnl.master.select.tax" text="Select tax"></spring:message>
								</form:option>
								<c:forEach items="${command.contractMastDTO.getTaxCodeList()}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</c:if>
					</div>
				</div>
				<div class="form-group " id="NoofInstallments">
					<label class="col-sm-2 control-label required-control"
						for="NoofInstallments"><spring:message
							code="contract.label.noOfInstallments" /></label>
					<div class="col-sm-4">
						<form:input
							path="contractMastDTO.contractDetailList[0].contInstallmentPeriod"
							type="text" class="form-control hasNumber mandColorClass"
							id="id_noa" />
					</div>
					<button type="button" class="btn btn-success" id="submitInstall">
						<spring:message code="contract.label.createInstallment" />
					</button>
				</div>

				<!-- </div> -->

				<div class="table-overflow display-hide Commercial"
					id="installments">
					<table
						class="table table-bordered table-striped tab tab_Application"
						id="noOfInstallmentTable">
						<tbody>
							<tr id='noa_header' style="display: none;">
								<th width="5%"><spring:message code="contract.label.no" /></th>
								<th width="10%"><spring:message
										code="contract.label.select" /></th>
								<th width="10%"><spring:message code="contract.label.value" /></th>
								<th width="10%"><spring:message
										code="contract.label.dueDate" /></th>
								<th width="30%"><spring:message
										code="contract.label.Milestone" /></th>
							</tr>
							<c:if
								test="${command.modeType eq 'V' || command.modeType eq 'E'}">
								<c:forEach
									items="${command.getContractMastDTO().getContractInstalmentDetailList()}"
									var="details" varStatus="status">
									<tr class="appendableClassInstallments">
										<td><form:input path="" type="text" class="form-control"
												size="5" value="${status.count}"
												id="contractNo${status.count-1}" /></td>
										<td><form:select
												path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitAmtType"
												class="form-control mandColorClass"
												id="PaymentTerms${status.count-1}">
												<c:set var="baseLookupCode" value="VTY" />
												<form:option value="0">
													<spring:message code="rnl.master.select" text="Select"></spring:message>
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input type="text"
												path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitValue"
												class="form-control text-right hasNumber"
												id="amt${status.count-1}" /></td>
										<form:hidden
											path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].active"
											id="presentRow${status.count-1}" value="Y" />
										<td><div class="input-group">
												<form:input type="text"
													id="installmentsDate${status.count-1}"
													path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitDueDate"
													class="form-control dateClass mandColorClass Insdatepicker" />
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div></td>
										<td><form:input type="text" size="5"
												path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitMilestone"
												id="mileStone${status.count-1}" class="form-control" /></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
 --%>

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#Terms"><spring:message
									code="contract.label.termsConditions" /></a>
						</h4>
					</div>
					<div id="Terms" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="customFields2">
									<tr>
										<th><spring:message code="contract.label.srNo" /></th>
										<th><spring:message code="contract.label.termsConditions" /><span
											class="mand">*</span></th>
										<c:if test="${command.termsFlag ne 'Y'}">
											<th width="50"><a title="Add" href="javascript:void(0);"
												class="addCF2 btn btn-success" title="<spring:message code="contract.label.add" text="Add"></spring:message>"><i
													class="fa fa-plus-circle"></i><span class="hide"><spring:message
															code="rl.property.label.add" text="Add"></spring:message></span></a></th>
										</c:if>
										<c:if test="${command.termsFlag eq 'Y'}">
											<th scope="col" width="5%"><a href="javascript:void(0);"
												data-toggle="tooltip" data-placement="top"
												onclick="addTermsDetails();" class=" btn btn-success btn-sm" title="<spring:message code="contract.label.add" text="Add"></spring:message>"><i
													class="fa fa-plus-circle"></i></a></th>
										</c:if>
									</tr>
									<c:choose>
										<c:when
											test="${empty command.getContractMastDTO().getContractTermsDetailList()}">
											<tr class="appendableTermConClass">
												<td width="50"><form:input type="text" path=""
														class="form-control text-center hasNumber" id="sNo0"
														value="1" /></td>
												<td><c:if test="${command.termsFlag eq 'Y'}">
														<form:select
															path="contractMastDTO.contractTermsDetailList[0].conttDescription"
															class="form-control" id="termCon0">
															<form:option value="">Select</form:option>
															<c:forEach items="${command.termsList}" var="lookUp">
																<form:option value="${lookUp}">${lookUp} </form:option>
															</c:forEach>
														</form:select>
													</c:if> <c:if test="${command.termsFlag ne 'Y'}">
														<form:textarea
															path="contractMastDTO.contractTermsDetailList[0].conttDescription"
															cols="" rows="" class="form-control mandColorClass"
															id="termCon0"></form:textarea>
													</c:if></td>
												<form:hidden
													path="contractMastDTO.contractTermsDetailList[0].active"
													id="presentRow0" value="Y" />
												<td><a title="Delete" class="btn btn-danger remCF2"
													href="javascript:void(0);" title="<spring:message code="contract.label.delete" text="Delete"></spring:message>"><i class="fa fa-trash-o"></i><span
														class="hide"><spring:message
																code="rnl.master.delete" text="Delete"></spring:message></span></a></td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach
												items="${command.getContractMastDTO().getContractTermsDetailList()}"
												var="details" varStatus="status">
												<tr class="appendableTermConClass">
													<td width="50"><form:input type="text" path=""
															class="form-control text-center hasNumber"
															id="sNo${status.count-1}" value="${status.count}" /></td>
													<td><c:if test="${command.termsFlag eq 'Y'}">
															<form:select
																path="contractMastDTO.contractTermsDetailList[${status.count-1}].conttDescription"
																class="form-control" id="termCon${status.count-1}">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.termsList}" var="lookUp">
																	<form:option value="${lookUp}">${lookUp} </form:option>
																</c:forEach>
															</form:select>
														</c:if> <c:if test="${command.termsFlag ne 'Y'}">
															<form:textarea
																path="contractMastDTO.contractTermsDetailList[${status.count-1}].conttDescription"
																cols="" rows="" class="form-control mandColorClass"
																id="termCon${status.count-1}"></form:textarea>
														</c:if></td>
													<form:hidden
														path="contractMastDTO.contractTermsDetailList[${status.count-1}].active"
														id="presentRow${status.count-1}" value="Y" />
													<td><a title="Delete" class="btn btn-danger remCF2"
														href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
															class="hide">Delete</span></a></td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</table>
							</div>
						</div>
					</div>
				</div>

			</div>


			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="agreement.docs" text="Agreement Documents" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<form:hidden path="removeCommonFileById" id="removeCommonFileById" />

						<div class="panel-body">

							<c:if test="${fn:length(command.attachDocsList)>0}">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="deleteCommonDoc">
										<tr>
											<th width="" align="center"><spring:message
													code="ser.no" text="" /><input type="hidden" id="srNo"></th>
											<th scope="col" width="64%" align="center"><spring:message
													code="work.estimate.document.description"
													text="Document Description" /></th>
											<th scope="col" width="30%" align="center"><spring:message
													code="scheme.view.document" /></th>
											<c:if test="${command.modeType ne 'V'}">
												<th scope="col" width="8%"><spring:message
														code="works.management.action" text=""></spring:message></th>
											</c:if>
										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td>${e+1}</td>
												<td>${lookUp.dmsDocName}</td>
												<td><apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="ContractAgreement.html?Download" /></td>
												<c:if test="${command.modeType ne 'V'}">
													<td class="text-center"><a href='#'
														id="deleteCommonFile" onclick="return false;"
														class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a>
														<form:hidden path="" value="${lookUp.attId}" /></td>
												</c:if>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
									</table>
								</div>
								<br>
							</c:if>

							<div id="doCommonFileAttachment">
								<div class="table-responsive">
									<c:set var="cd" value="0" scope="page" />
									<c:if test="${command.modeType ne 'V'}">
										<table class="table table-bordered table-striped"
											id="attachCommonDoc">
											<tr>
												<th><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th><spring:message code="work.estimate.upload"
														text="Upload Document" /></th>
												<th scope="col" width="8%"><a
													onclick='doCommonFileAttachment(this);'
													class="btn btn-blue-2 btn-sm addButton" title="<spring:message code="contract.label.add" text="Add"></spring:message>"><i
														class="fa fa-plus-circle"></i></a></th>
											</tr>

											<tr class="appendableUploadClass">


												<td><form:input
														path="commonFileAttachment[${cd}].doc_DESC_ENGL"
														class=" form-control" /></td>
												<td class="text-center" title="Upload"><apptags:formField
														fieldType="7"
														fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
														currentCount="${cd}" showFileNameHTMLId="true"
														fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
														maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
														callbackOtherTask="callbackOtherTask()">
													</apptags:formField>
													<small class="text-blue-2" style="font-size: unset;"> <spring:message code="contract.doc.validatation"
															      text="(Upload Image File upto 5 MB)" />
												     </small></td>
												<td class="text-center"><a href='#' id="0_file_${cd}"
													onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton' title="<spring:message code="contract.label.delete" text="Delete"></spring:message>"><i
														class="fa fa-trash"></i></a></td>
											</tr>
											<c:set var="cd" value="${cd+1}" scope="page" />
										</table>
									</c:if>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			<c:if test="${command.contMapFlag ne 'B' }">
				<div class="text-center">
					<c:if test="${command.modeType ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="addPartyDetails();" id="submit">
							<spring:message code="agreement.addPartyDetail"
								text="Add Party Details" />
						</button>
						<c:if test="${command.modeType eq 'C'}">
							<button type="reset" id="resetButton" class="btn btn-warning"
								onclick="resetForm();">
								<spring:message code="contract.label.Reset" />
							</button>
						</c:if>
					</c:if>
					<c:if
						test="${command.modeType eq 'V' && not empty command.contractMastDTO.contNo}">
						<button type="button" class="btn btn-success backButton"
							onclick="getPartyDetails();" id="submit" id="party">
							<spring:message code="agreement.partyDetail" text="Party Details" />
						</button>

					</c:if>
					<button type="button" onclick="back('${command.showForm}')" id=""
						class="btn btn-danger backButton">
						<spring:message code="contract.label.Back" />
					</button>
				</div>
			</c:if>
			<c:if test="${command.contMapFlag eq 'B' }">
				<div class="text-center">

					<button type="button" class="btn btn-success btn-submit"
						onclick="addPartyDetails();" id="submit">
						<spring:message code="agreement.addPartyDetail"
							text="Add Party Details" />
					</button>
					<button type="button" onclick="back('${command.showForm}')" id=""
						class="btn btn-danger backButton">
						<spring:message code="contract.label.Back" />
					</button>
				</div>
			</c:if>

		</form:form>
	</div>
</div>