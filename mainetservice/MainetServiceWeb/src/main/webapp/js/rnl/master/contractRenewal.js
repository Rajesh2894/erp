$(document)
		.ready(
				function() {
					$("#NoofInstallments").hide();
					$("#installments").hide();
					$("#renewalSummaryDT").dataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true
							});

					$('.datepicker').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						//minDate : '0d',
						//maxDate:'0d',
						yearRange : "-100:+20",  
					});	
					
					
				});




$(document).on('change', '#contId', function() {
	var errorList = [];
	var theForm = '#landAgreementId';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'ContractAgreementRenewal.html?form';
	var response = __doAjaxRequest(URL, 'POST', requestData, false);
	$(formDivName).html(response);
});


$(document).on('change', '#vendorContId', function() {
	var errorList = [];
	var theForm = '#landAgreementId';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'ContractAgreementRenewal.html?form1';
	var response = __doAjaxRequest(URL, 'POST', requestData, false);
	$(formDivName).html(response);
});


/*$("#contToDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});*/


function showAlertBoxForInstallDate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select Tender Date, Resolution Date,From Date and To Date first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#id_noa').val('');
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}


function contractRenewalValidation(errorList) {
	//var contractNo = $("#contId").val();
	//var vendorName = $("#vendorId").val();
	//var contractPayment = $('#ContractPayment').val();
	var ContractAmount = $.trim($("#ContractAmount").val());
	//var securityDeposit = Number($("#SecurityDeposit").val());

	if ( ContractAmount == undefined || ContractAmount == '') {
		errorList.push(getLocalMessage('rnl.renewal.contractAmtValid'));
	}
	/*if (securityDeposit == "0" || securityDeposit == undefined || securityDeposit == '') {
		errorList.push(getLocalMessage('rnl.renewal.securityDeopsitValid'));
	}*/
	let renewalToDate = $("#renewalToDate").val();
	if (renewalToDate == 0 || renewalToDate == "")
		errorList.push(getLocalMessage("rnl.renewal.toDateValid"));

	var ContractMode = $.trim($("input[name='contractMastDTO.contMode']:checked").val());
	if (ContractMode == 0 || ContractMode == "")
		errorList.push(getLocalMessage("agreement.enterContractMode"));

	var ContractType = $.trim($("#ContractType").val());
	if (ContractType == 0 || ContractType == "")
		errorList.push(getLocalMessage("agreement.selectType"));
	if (ContractType != undefined && ContractType != '0') {
		var ContType = $("#ContractType").find("option:selected").attr('code')
				.split(",");

	}
	if (ContractMode == 'C') {
		var ContractPayment = $.trim($("#ContractPayment").val());

		if (ContractPayment == 0 || ContractPayment == "")
			errorList.push(getLocalMessage("agreement.enterContractPayment"));

		if (ContType == 'CL') {
			var taxId = $.trim($("#taxId").val());
			if (taxId == 0 || taxId == "")
				errorList.push(getLocalMessage("agreement.enterTaxCode"));

			var id_noa = $.trim($("#id_noa").val());
			if (id_noa == 0 || id_noa == "")
				errorList.push(getLocalMessage("agreement.enterNoOfInstall"));

			var totalAmt = 0;
			var count = 0;
			var errcount = 0;
			$('.appendableClassInstallments')
					.each(
							function(i) {
								count++;
								var level = i + 1;
								var PaymentTerms = $
										.trim($("#PaymentTerms" + i).val());
								var PaymentTermsCode = $(
										"#PaymentTerms" + i
												+ " option:selected").attr(
										'code');

								if (PaymentTerms == 0 || PaymentTerms == "")
									errorList
											.push(getLocalMessage("agreement.selectPayTerms")
													+ " " + level);

								var amt = $.trim($("#amt" + i).val());
								if (amt == 0 || amt == "")
									errorList
											.push(getLocalMessage("agreement.enterAmt")
													+ " " + level);

								if (PaymentTermsCode == "AMT") {
									totalAmt = parseFloat(totalAmt)
											+ parseFloat(amt);
								} else if (PaymentTermsCode == "PER") {
									var amount = (parseFloat(ContractAmount) * parseFloat(amt)) / 100;
									totalAmt = totalAmt + amount;
								}

								var installmentsDate = $.trim($(
										"#installmentsDate" + i).val());
								if (installmentsDate == 0
										|| installmentsDate == "") {
									errcount++;
									errorList
											.push(getLocalMessage("rnl.renewal.installmentDueDate")
													+ " " + level);
								}
							});
			if (errcount == 0) {
				for (var i = 0; i < count; i++) {
					var idate = new Date($("#installmentsDate" + i).val());
					for (var j = i + 1; j < count; j++) {
						var jdate = new Date($("#installmentsDate" + j).val());
						if (idate >= jdate) {
							errorList
									.push(getLocalMessage("agreement.dueDate")
											+ (j + 1)
											+ getLocalMessage("agreement.dueDateGreater")
											+ (i + 1));
						}
					}
				}
			}
			if (!id_noa == 0 && !id_noa == "") {
				//D#39694 check in case of Lease 
				//if contract date is within month range of system date than don't fire the validation
				let contractDate = moment($('#contFromDate').val(),'DD-MM-YYYY');
				let contractDateMonthInDays =contractDate.daysInMonth();//selected month count in no of days
				let subDays = moment().diff(contractDate,'days');//get difference of two date than compare
				if(subDays>contractDateMonthInDays){
					if (totalAmt != ContractAmount) {
						errorList
								.push(getLocalMessage("agreement.noInstallNotMatch"));
					}	
				}	
			}
		}
	}
	
		$('.appendableTermConClass').each(function(i) {
					var level = i + 1;
					var termCon = $.trim($("#termCon" + i).val());
					if (termCon == 0 || termCon == "") {
						errorList.push(getLocalMessage("agreement.enterTemsConditions")
										+ " " + level);
					}
				});

	return errorList;
}

function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	return false;
}


function saveContractRenewl(obj) {
	var errorList = [];
	errorList = contractRenewalValidation(errorList);
	if (errorList.length > 0) {
		showRLValidation(errorList);
		return false;
	}else{
		return saveOrUpdateForm(obj, '', 'ContractAgreementRenewal.html', 'saveForm');
	}
}


//search contract renewal 
function searchContractRenewal() {
	var errorList = [];
	var contractNo = $("#contractNo").val().trim();
	var contractDate = $("#contractDate").val();
	var departmentId = $('#departmentId').val();
	var vendorId = $('#vendorId').val();

	if (contractNo != '' || contractDate != '' || vendorId != 0 || departmentId!=0) {
        
		var requestData = {
				"contractNo" : contractNo,
				"contractDate":contractDate,
				"vendorId":vendorId,
				"departmentId":departmentId
			}
		
		var table = $('#renewalSummaryDT').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var prePopulate =__doAjaxRequest('ContractAgreementRenewal.html?filterData', 'POST',requestData, false,'json');
		var result = [];
		
		$.each(prePopulate,function(index) {
							var obj = prePopulate[index];
							result.push([
										    obj.contNo,
										    obj.contDept,
										    obj.contp1Name,
											obj.contp2Name,
											obj.contFromDate,
											obj.contToDate,
											'<td >'
											+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="actionContractRenewal(\''
											+ obj.contId
											+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
											+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="actionContractRenewal(\''
											+ obj.contId
											+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
											+ '</td>' ]);
						});
		table.rows.add(result);
		table.draw();
		if (prePopulate.length == 0) {
			errorList.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();
		}
	} else {
		errorList.push(getLocalMessage('agreement.selectAnyField'));
		displayErrorsOnPage(errorList);
	}
}





function actionContractRenewal(contId,type){		
if(type == 'V'){
    showContractForm(contId,type);
   	 $("#ContractAgreement :input,select").prop("disabled", true);
   	$("#partyDetails :input").prop("disabled", true);
	 $('.addCF4').attr('disabled',true);
	 $('.addCF5').attr('disabled',true);
	 $('.addCF2').attr('disabled',true);
	 $('.remCF2').attr('disabled',true);
	 $('.remCF3').attr('disabled',true);
	 $('.remCF4').attr('disabled',true);
	 $('.remCF5').attr('disabled',true);
	 $('.uploadbtn').attr('disabled',false);
	 $(".backButton").removeProp("disabled");
	// $("#partyDetails").removeProp("disabled");
	 $('#noa_header').show(); //Defect #157003
}

if(type == 'E'){
    var requestData = 'contId='+contId+'&type='+type;
    showContractForm(contId,type);
	/*var ajaxResponse	=	doAjaxLoading('ContractAgreement.html?findContractMapedOrNot', requestData, 'html');
		if(ajaxResponse!='"Y"'){
		    showContractForm(contId,type);
			$("#resetButton").prop("disabled", true);
		   	$("#AgreementDate").prop('disabled',true);
		}else{
			showAlertBox();	
		}*/
	}
}


function showContractForm(contId,type){
var requestData = 'contId='+contId+'&type='+type;
var ajaxResponse	=	doAjaxLoading('ContractAgreementRenewal.html?form', requestData, 'html');
$('.content-page').removeClass('ajaxloader');
$('.content-page').html(ajaxResponse);
prepareTags();
}


function showAlertBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('contract.ok');
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">' + getLocalMessage('contract.already.maped.edit.not.allowed')+ '</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

