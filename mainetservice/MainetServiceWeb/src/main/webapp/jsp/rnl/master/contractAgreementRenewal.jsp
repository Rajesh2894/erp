<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.*,java.util.*"%>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css"
	rel="stylesheet" type="text/css">
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/masters/contract/contractAgreement.js"></script>
<script type="" src="js/rnl/master/contractRenewal.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>


<script>

$(document).ready(function(){
	
	var countdat = 0;
	$('body').on('focus',".installmentDatePicker",function() {
				$('.installmentDatePicker').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true
				});
				var renewalToDate = $("#renewalToDate").val();
				if (renewalFromDate != '' && countdat ==0 && renewalToDate != '') {
					countdat++;
					/*var dateParts = renewalToDate.split("/");
					renewalToDate = new Date(dateParts[2],dateParts[1] - 1, dateParts[0]);
					renewalToDate.setDate(renewalToDate.getDate());*/
					$(".installmentDatePicker").datepicker("option", "minDate",
							renewalFromDate);
					$(".installmentDatePicker").datepicker("option", "maxDate",
							renewalToDate);
				}
			});
	
	$('body').on('change',".datepickerRenewalToDate",function() {
		$('.datepickerRenewalToDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate: $('#renewalFromDate').val()
		});
		let renewalToDate = $(this).val();
        if (renewalToDate != '') {
        	$('.appendableClassInstallments').each(function(i) {
				$("#installmentsDate" + i).val('');
			});
		}
        $(".installmentDatePicker").datepicker("option", "maxDate",
        		renewalToDate);
	});
	$('#submitInstall').click(
			function() {
					let renewalToDate = $("#renewalToDate").val();
					if (renewalToDate == '') {
						popUpErrorMessage(getLocalMessage('rnl.renewal.selectToDateValid'));
						$("#renewalToDate").focus();
						return false;
					}
					$('#installments').show();
					var total = $("#id_noa").val();
					if (total != "" && total != '0') {
						var i = 1;
						var path = 0;
						var optionSelect = getLocalMessage('rnl.master.select');
						$("#noOfInstallmentTable")
								.find("tr:gt(0)")
								.remove();
						$('#noa_header').show();
						while (i <= total) {
							$('.tab_Application')
									.append(
											'<tr class="appendableClassInstallments"><td><input type="text" class="form-control" disabled size="5" value="'+i+'"/></td>'
													+ '<td><select name="contractMastDTO.contractInstalmentDetailList['+path+'].conitAmtType" class="form-control mandColorClass" id="PaymentTerms'+path+'">'
													+ '<c:set var="baseLookupCode" value="VTY" /> <option value="0">' + optionSelect + '</option> <c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
													+ '<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option> </c:forEach></td>'
													+ '<input type="hidden" name="contractMastDTO.contractInstalmentDetailList['+path+'].active"  id="presentRow'+path+'" value="Y"/> '
													+ '<td><input type="text" name="contractMastDTO.contractInstalmentDetailList['
													+ path
													+ '].conitValue" onkeypress="return hasAmount(event, this, 13, 2)"  class="form-control text-right" name="amt" id="amt'
													+ path
													+ '" /></td>'
													+ '<td><div class="input-group"><input type="text" id="installmentsDate'+path+'"  name="contractMastDTO.contractInstalmentDetailList['+path+'].conitDueDate" class="form-control dateClass mandColorClass installmentDatePicker"><span class="input-group-addon"><i class="fa fa-calendar"></i></span></div></td>'
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
						popUpErrorMessage(getLocalMessage("agreement.enterNoOfInstall"));
						$("#id_noa").focus();
						$("#installments").hide();
						return false;
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
	$("#customFields2").on('click', '.remCF2', function() {
		{
			if ($("#customFields2 tr").length != 2) {
				$(this).parent().parent().remove();
				reorderTermCon();
				newTermCon--;
			} else {
				alert("You cannot delete first row");
			}
		}
	});
	//hide installment button when view mode
	if ('${command.modeType}' == 'V') {
		//$('#submitInstall').hide();
		$('#installments').show();
		$("#landAgreementId :input,select").prop("disabled",true);

	}else{
		
		$('#id_noa').val('');
		$("#ContractAmount").val('');
		$('#taxId').prop('selectedIndex',0);
	}

	//Tax code show based on commercial select
	if('${command.contractMastDTO.contMode}' == 'C'){
		$('#taxGroup').show();
		$("#NoofInstallments").show();
	}
	//due date set +1 from currentDate on installment Table
	var renewalFromDate = $('#renewalFromDate').val();
	//checkToDate();
	//renewalFromDate=moment(renewalFromDate).format("YYYY-MM-DD");
	if (renewalFromDate != '') {
		var dateParts = renewalFromDate.split("/");
		renewalFromDate = new Date(dateParts[2],dateParts[1] - 1, dateParts[0]);
		renewalFromDate.setDate(renewalFromDate.getDate()+1);
		$(".datepickerRenewalToDate").datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					minDate: renewalFromDate,
					changeYear : true
				});
		$(".installmentDatePicker").datepicker("option", "maxDate",renewalFromDate);
	}
});

function popUpErrorMessage(messages){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+messages+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

//D#138342
function viewPropertyDet(element) {
	_openPopUpForm('ContractAgreementRenewal.html','viewPropertyDet');
	if($('#hiddeValue').val() == 'V'){
		 $("#propForm :input").prop("disabled", true);
		 $("#backBtn").removeProp("disabled");
		 $('.addCF').bind('click', false);
		 $('.remCF').bind('click', false);
	}
	if($('#hiddeValue').val() == 'E'){
		 $("#resetProp").prop("disabled", true);
	}
}

</script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.contract.title"
					text="Contract Renewal Entry" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form method="POST" action="ContractAgreementRenewal.html"
				cssClass="form-horizontal" id="landAgreementId"
				name="landAgreementForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<%-- <form:hidden path="contractMastDTO.contId"  value="0" /> --%>
				<form:hidden path="contractMastDTO.contractDetailList[0].contdId"  value="0" />
				<div class="panel-group accordion-toggle">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion" href="#Contract"><spring:message code="contract.label.contract" /></a>
							</h4>
						</div>
						<div id="Contract" class="panel-collapse collapse in">
							<div class="panel-body">

								<%-- <div class="form-group">
									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="rnl.renewal.contractNo" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="contactAgreementdto.contractId" id="contId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="${command.saveMode eq 'VIEW'}">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.contractNoList}" var="contractNo">
												<form:option value="${contractNo.contId}">${contractNo.contNo}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label" for="vendorName">
										<spring:message code="rnl.renewal.vendorName" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="contactAgreementdto.contractId1"
											id="vendorContId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="${command.saveMode eq 'VIEW'}">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.contractNoList}" var="vendorName">
												<form:option value="${vendorName.contId}">${vendorName.contp2Name}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div> --%>

								<div class="form-group">
									<apptags:input labelCode="rnl.renewal.tendorNo"
										isReadonly="true" path="contractMastDTO.contTndNo"
										cssClass="alphaNumeric" maxlegnth="20"></apptags:input>

									<apptags:date fieldclass="datepicker"
										labelCode="rnl.renewal.TendorDate"
										datePath="contractMastDTO.contTndDate" isDisabled="true"></apptags:date>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "
										for="ResulationNo"><spring:message
											code="contract.label.resolutionNo" /></label>
									<div class="col-sm-4">
										<form:input path="contractMastDTO.contRsoNo" type="text"
											class="form-control mandColorClass" id="resulationNo"
											disabled="true" />
									</div>
									<label class="col-sm-2 control-label "
										for="ResulationDate"><spring:message
											code="contract.label.resolutionDate" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="contractMastDTO.contRsoDate" type="text"
												class="form-control dateClass datepickerResulation "
												id="resolutionDate" onkeyup="clearInput($(this));"
												disabled="true" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>
								<div class="form-group">

									<label class="col-sm-2 control-label required-control"
										for="ContractType"><spring:message
											code="rnl.renewal.contractType" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="CNT" />
										<form:select path="contractMastDTO.contType"
											class="form-control contractType" id="ContractType"
											onchange="hideDetails();" disabled="true">
											<%-- <form:option value="">${command.contractMastDTO.contType}</form:option> --%>
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
								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="rnl.renewal.contractFromDate"
										datePath="contractMastDTO.contractDetailList[0].contFromDate"
										isMandatory="true" isDisabled="true"></apptags:date>
									<apptags:date fieldclass="datepicker"
										labelCode="rnl.renewal.contractToDate"
										datePath="contractMastDTO.contractDetailList[0].contToDate"
										isMandatory="true" isDisabled="true"></apptags:date>
								</div>
								<form:hidden path="modeType" id="hiddeMode" />
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="rnl.renewal.contractMode" /> <span class="mand">*</span></label>
									<div class="col-sm-4">
										<label class="radio-inline margin-top-5"> <form:radiobutton
												path="contractMastDTO.contMode" class="ContractMode"
												id="commercial" value="C" disabled="true" /> <spring:message
												code="rnl.renewal.commercial" /></label> <label
											class="radio-inline margin-top-5" id="nonCommercial">
											<form:radiobutton path="contractMastDTO.contMode"
												class="ContractMode" value="N" disabled="true" /> <spring:message
												code="rnl.renewal.nonCommercial" />
										</label>
									</div>
									<div id="Renewal">
										<label class="control-label col-sm-2" for="AllowRenewal"><spring:message
												code="rnl.renewal.allow" /></label>
										<div class="col-sm-4">
											<form:select path="contractMastDTO.contRenewal"
												disabled="true" class="form-control" id="allowRenewal">
												<form:option value="0">
													<spring:message code="rnl.renewal.select " text="Select"></spring:message>
												</form:option>
												<form:option value="Y">
													<spring:message code="rnl.renewal.yes" text="Yes"></spring:message>
												</form:option>
												<form:option value="N">
													<spring:message code="rnl.renewal.no" text="No"></spring:message>
												</form:option>
											</form:select>
										</div>
									</div>
								</div>
							</div>
							<!-- 	<div class="display-hide Commercial"> -->
							<div class="form-group">

								<label class="col-sm-2 control-label" for="ContractPayment"><spring:message
										code="rnl.renewal.contractPayment" text="Contract Payment" /> <span
									class="mand">*</span></label>
								<div class="col-sm-4">
									<form:select path="contractMastDTO.contPayType" disabled="true"
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
							<!-- 	</div> -->

							<div class="form-group">
								<label class="col-sm-2 control-label" for="ContractAmount"><spring:message
										code="rnl.renewal.amt" /><span class="mand">*</span></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input
											path="contractMastDTO.contractDetailList[0].contractAmt"
											type="text"  maxlength="12"
											onchange="getAmountFormatInDynamic((this),'ContractAmount')"
											class="form-control text-right mandColorClass"
											id="ContractAmount" disabled="${command.modeType eq 'V'}"/>
										<span class="input-group-addon"><i class="fa fa-inr"></i></span>
									</div>
								</div>
								<label class="col-sm-2" for="SecurityDeposit"><spring:message
										code="rnl.renewal.deopositAmt" /><!-- <span class="mand">*</span> --></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input
											path="contractMastDTO.contractDetailList[0].contSecAmount"
											type="text" class="form-control hasNumber text-right"
											id="SecurityDeposit" disabled="${command.modeType eq 'V'}"
											onkeypress="return hasAmount(event, this, 8, 2)"
											onchange="getAmountFormatInDynamic((this),'SecurityDeposit')" />
										<span class="input-group-addon"><i class="fa fa-inr"></i></span>
									</div>
								</div>
							</div>
							<!-- 	<div class="display-hide Commercial"> -->
							<div class="form-group">
								<apptags:input labelCode="rnl.renewal.securityDepositeReciept"
									path="contractMastDTO.contractDetailList[0].contSecRecNo"
									cssClass="alphaNumeric" maxlegnth="100"
									isDisabled="${command.modeType eq 'V'}"></apptags:input>
								<apptags:date fieldclass="datepicker"
									labelCode="rnl.renewal.SecurityDepositeDate"
									datePath="contractMastDTO.contractDetailList[0].contSecRecDate"
									isDisabled="${command.modeType eq 'V'}"></apptags:date>

							</div>

							<!-- 	</div> -->

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
											<c:forEach
												items="${command.contractMastDTO.getTaxCodeList()}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</c:if>

								</div>
							</div>
							
								<!-- D#138342 -->
								<c:if test="${not empty command.contractMastDTO.propertyNo }">
									<div class="form-group">
										<apptags:input labelCode="rnl.master.property"
											path="contractMastDTO.propertyNo" 
											isReadonly="true"></apptags:input>
										
								<label  class="col-sm-2 control-label"><spring:message
									code="rl.property.label.name" text="Property Name" /></label>
										<div class="col-sm-2">
												<form:input type="text"
													cssClass="form-control" disabled="true"
													path="contractMastDTO.propName" ></form:input>
										</div>
										<div class="col-sm-2">
											<button type="button"
														class="btn btn-blue-2 btn-sm "
														name="button-plus" 
														onclick="viewPropertyDet(this)"
														title="<spring:message code="estate.grid.column.view" text="view property"></spring:message>">
														<i class="fa fa-eye" aria-hidden="true"></i>
													</button>
										</div>
										
										
										
									</div>
									
							
									
									
								</c:if>
								
							</div>
							<!-- -----------     -    -    -   - renew details start ---------------------->
							
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 type="h4" class="panel-title table" id="">
											<a data-toggle="collapse" class=""
												data-parent="#accordion" href="#renewalsData"><spring:message
													code="rnl.renewal.details" text="Renewal Details" /> </a>
										</h4>
									</div>
									<div id="renewalsData" class="panel-collapse collapse in">
										<div class="panel-body padding-top-5">
											<div class="form-group">
												<%-- <apptags:date fieldclass="datepicker"
													labelCode="rnl.renewal.fromDate" isMandatory="true"
													datePath="contractMastDTO.contractDetailList[0].contToDate"
													isDisabled="true"></apptags:date> --%>

												<label class="col-sm-2 control-label required-control"><spring:message
														code="rnl.renewal.fromDate" /></label>
												<div class="col-sm-4">
													<div class="input-group">
														<form:input
															path="contractMastDTO.contractDetailList[0].contToDate"
															type="text"
															class="form-control dateClass datepickerRenewalToDate mandColorClass"
															id="renewalFromDate" onkeyup="clearInput($(this));"
															disabled="true" />
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
													</div>
												</div>

												<%-- <apptags:date fieldclass="datepicker" labelCode="rnl.renewal.toDate" id="renewalToDate"
													isMandatory="true" datePath="contactAgreementdto.toDate"
													isDisabled="${command.modeType eq 'V'}"></apptags:date> --%>

												<label class="col-sm-2 control-label required-control"><spring:message
														code="rnl.renewal.toDate" /></label>
												<div class="col-sm-4">
													<div class="input-group">
														<form:input path="contactAgreementdto.toDate" type="text"
															class="form-control dateClass datepickerRenewalToDate mandColorClass"
															id="renewalToDate" onkeyup="clearInput($(this));"
															disabled="${command.modeType eq 'V'}" />
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								
					<!-- -------------------------------------------------- renew details end ----------------------->
							<div class="form-group margin-top-10" id="NoofInstallments">
								<label class="col-sm-2 control-label required-control"
									for="NoofInstallments"><spring:message
										code="contract.label.noOfInstallments" /></label>
								<div class="col-sm-4">
									<form:input
										path="contractMastDTO.contractDetailList[0].contInstallmentPeriod"
										type="text" class="form-control hasNumber mandColorClass"
										disabled="${command.modeType eq 'V'}" id="id_noa" />
								</div>
								<button type="button" class="btn btn-success" id="submitInstall">
									<spring:message code="contract.label.createInstallment" />
								</button>
							</div>
							<!-- Installment Table -->
							<div class="table-overflow display-hide Commercial" id="installments">
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
											test="${command.modeType eq 'V' || command.modeType eq 'E'}">
											<c:forEach
												items="${command.getContractMastDTO().getContractInstalmentDetailList()}"
												var="details" varStatus="status">
												<form:hidden
														path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitId"  value="0" />
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
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
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
																class="form-control dateClass mandColorClass installmentDatePickertePicker" />
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
						<!-- 1st panel end -->


					
					<!-- toggle end 1st -->
					<!-- inst table end---------------- -->


					<!--------------------------------------------------- documents start------------------------------- -->

					
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion" href="#a1"> <spring:message
											code="agreement.docs" text="Agreement Documents" /></a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse">
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
														<td><apptags:filedownload
																filename="${lookUp.attFname}"
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
															class="btn btn-blue-2 btn-sm addButton"><i
																class="fa fa-plus-circle"></i></a></th>
													</tr>

													<tr class="appendableUploadClass">


														<td><form:input
																path="commonFileAttachment[${cd}].doc_DESC_ENGL"
																class=" form-control" /></td>
														<td class="text-center"><apptags:formField
																fieldType="7"
																fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
																currentCount="${cd}" showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																callbackOtherTask="callbackOtherTask()">
															</apptags:formField></td>
														<td class="text-center"><a href='#' id="0_file_${cd}"
															onclick="doFileDelete(this)"
															class='btn btn-danger btn-sm delButton'><i
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
					
					<!------------------------------------------------ 			documents end	 --------------------------------------->

					<!-- 													terms and condition start ----------- --->
					
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion" href="#Terms"><spring:message
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
												<th><spring:message
														code="contract.label.termsConditions" /><span
													class="mand">*</span></th>
												<th width="50"><a title="Add"
													href="javascript:void(0);" class="addCF2 btn btn-success"><i
														class="fa fa-plus-circle"></i><span class="hide"><spring:message
																code="rl.property.label.add" text="Add"></spring:message></span></a></th>
											</tr>
											<c:choose>
												<c:when
													test="${empty command.getContractMastDTO().getContractTermsDetailList()}">
													<tr class="appendableTermConClass">
														<td width="50"><form:input type="text" path=""
																class="form-control text-center hasNumber" id="sNo0"
																value="1" /></td>
														<td><form:textarea
																path="contractMastDTO.contractTermsDetailList[0].conttDescription"
																cols="" rows="" class="form-control mandColorClass"
																id="termCon0"></form:textarea></td>
														<form:hidden
															path="contractMastDTO.contractTermsDetailList[0].active"
															id="presentRow0" value="Y" />
														<td><a title="Delete" class="btn btn-danger remCF2"
															href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
																class="hide"><spring:message
																		code="rnl.master.delete" text="Delete"></spring:message></span></a></td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach
														items="${command.getContractMastDTO().getContractTermsDetailList()}"
														var="details" varStatus="status">
														<tr class="appendableTermConClass">
														<form:hidden
																path="contractMastDTO.contractTermsDetailList[${status.count-1}].conttId"  value="0" />
															<td width="50"><form:input type="text" path=""
																	class="form-control text-center hasNumber"
																	id="sNo${status.count-1}" value="${status.count}" /></td>
															<td><form:textarea
																	disabled="${command.modeType eq 'V'}"
																	path="contractMastDTO.contractTermsDetailList[${status.count-1}].conttDescription"
																	cols="" rows="" class="form-control mandColorClass"
																	id="termCon${status.count-1}"></form:textarea></td>
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
					

					<!-- -	-	-	-	-	-	-	--		-	-		terms and condition end ----------- --->

					
					<div class="text-center margin-top-10">
						<c:if test="${command.modeType eq 'E'}">
							<button type="button" onclick="saveContractRenewl(this)" id=""
								class="btn btn-success">
								<spring:message code="rnl.renewal.renew" />
							</button>
						</c:if>

						<button type="button"
							onclick="window.location.href='ContractAgreementRenewal.html'"
							id="" class="btn btn-danger backButton">
							<spring:message code="bt.backBtn" />
						</button>


					</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
