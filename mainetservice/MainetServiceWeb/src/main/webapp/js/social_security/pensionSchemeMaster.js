var URL = "PensionSchemeMaster.html";

$(document).ready(function() {

});

$(function() {
    
	$("#sourcefund").on('click', '.addCF', function() {
		
		var errorList = [];
		errorList = validateUnitDetailTable(errorList);
		if (errorList.length == 0) {
			var content = $("#sourcefund").find('tr:eq(1)').clone();
			$("#sourcefund").append(content);

			reOrderTableIdSequences();
			content.find("input:text:eq(0)").val('');
			content.find("select:eq(0)").val('');
			// content.find("select:eq(0)").val('');
			// content.find("select").val('0');

		} else {
			displayErrorsOnPage(errorList);
		}

	});

	$("#sourcefund").on('click', '.remCF', function() {
		
		if ($("#sourcefund tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderTableIdSequences();
		} else {
			var errorList = [];
			errorList.push(getLocalMessage('pension.sch.onerowreq'));
			displayErrorsOnPageView(errorList);
			// alert("You cannot delete first row");
		}

	});

	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate :'0d',
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)

		}
	});
	
});

function reOrderTableIdSequences() {
	
	$('.appendableClass').each(
			function(i) {
				$(this).find("select:eq(0)").attr(
						"id",
						"pensionSchmDto.pensionSourceFundList" + i
								+ ".sponcerId");
				$(this).find("input:text:eq(0)").attr(
						"id",
						"pensionSchmDto.pensionSourceFundList" + i
								+ ".sharingAmt");

				$(this).find("select:eq(0)").attr(
						"name",
						"pensionSchmDto.pensionSourceFundList[" + i
								+ "].sponcerId").attr("onchange","findDuplicate(this,"+i+")");
				$(this).find("input:text:eq(0)").attr(
						"name",
						"pensionSchmDto.pensionSourceFundList[" + i
								+ "].sharingAmt").attr("onkeyup",
						"verifyDecimalNo(this)");
				;

			});
}

function verifyDecimalNo(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, '') // numbers and decimals only
	.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
	.replace(/(\.[\d]{6})./g, '$1'); // max 2 digits after decimal
}
function displayErrorsOnPageView(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();

	return false;
}
function validateUnitDetailTable(errorList) {
	
	var rowCount = $('#sourcefund tr').length;
	$('.appendableClass').each(
			function(i) {
				
				if (rowCount <= 1) {
					let sponcerId = "#pensionSchmDto\\.pensionSourceFundList"
							+ i + "\\.sponcerId";
					var sharingAmt = "#pensionSchmDto\\.pensionSourceFundList"
							+ i + "\\.sharingAmt";
					var sponcerId1 = $(sponcerId).val();
					var sharingAmt1 = $(sharingAmt).val();
					var level = 1;
				} else {
					var utp = i;
					utp = i * 6;
					let sponcerId = "#pensionSchmDto\\.pensionSourceFundList"
							+ i + "\\.sponcerId";
					var sharingAmt = "#pensionSchmDto\\.pensionSourceFundList"
							+ i + "\\.sharingAmt";
					var sponcerId1 = $(sponcerId).val();
					var sharingAmt1 = $(sharingAmt).val();

					var level = i + 1;
				}
				if (sponcerId1 == undefined || sponcerId1 == '') {
					errorList.push(getLocalMessage('pension.sch.sponsor')
							+ " at Row " + level);
				}
				if (sharingAmt1 == undefined || sharingAmt1 == '') {
					errorList.push(getLocalMessage('pension.sch.amountreq')
							+ " at Row " + level);
				}

			});
	return errorList

}
function removeDisable(indexValue, validatevalue) {
	
	var rangeFrom = $("#pensionSchmDto\\.pensioneligibilityList" + indexValue
			+ "\\.rangeFrom");
	var rangeTo = $("#pensionSchmDto\\.pensioneligibilityList" + indexValue
			+ "\\.rangeTo");
	var criteria = $("#pensionSchmDto\\.pensioneligibilityList" + indexValue
			+ "\\.criteriaId");
	if ($(
			'input:checkbox[id=pensionSchmDto\\.pensioneligibilityList'
					+ indexValue + '\\.checkBox1]').is(':checked')) {
		criteria.prop("disabled", false).trigger("chosen:updated");
		if (validatevalue == 'Age' || validatevalue == 'Education'
				|| validatevalue == 'Income' || validatevalue =='Disability') {
			rangeFrom.prop("disabled", false);
			rangeTo.prop("disabled", false);
		}
		else
			{
			rangeFrom.prop("disabled", true);
			rangeTo.prop("disabled", true);
			}
	} else {
		criteria.val("");
		rangeFrom.val("");
		rangeTo.val("");
		criteria.prop("disabled", true).trigger("chosen:updated");
		rangeFrom.prop("disabled", true);
		rangeTo.prop("disabled", true);

	}

}
function savePensionCriteria(element) {

	let errorList = [];
	errorList = validatePansionCriteria(errorList);
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length == 0) {
		
	$(".checkboxClass ").attr("disabled", false);
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
	
		
		var divName = '.nextPageClass';
		var response = __doAjaxRequest(URL + '?savePensionScheme', 'POST',
				requestData, false, '', 'html');
		
		
		$(divName).html(response);
		
		prepareTags();
		$("#serviceId").prop("disabled", true);
		$("#objOfschme").prop("disabled", true);
		var rowCount = $('#sourcefund tr').length;
		for (let i = 0; i < rowCount - 1; i++) {
			$("#pensionSchmDto\\.pensionSourceFundList" + i + "\\.sponcerId")
					.prop("disabled", true);
			$("#pensionSchmDto\\.pensionSourceFundList" + i + "\\.sharingAmt")
					.prop("disabled", true);
			$("#addCF").attr("disabled", true);
			$(".remCF ").attr("disabled", true);
		}
		$('input:checkbox[class^="checkboxClass"]').each(function() {
			$('input:checkbox[class^="checkboxClass"]').prop('checked', true);
		});
		$('.appendableClass1').each(
				function(i) {
					
					$(
							"#pensionSchmDto\\.pensioneligibilityList" + i
									+ "\\.criteriaId").val("");
					$(
							"#pensionSchmDto\\.pensioneligibilityList" + i
									+ "\\.rangeFrom").val("");
					$(
							"#pensionSchmDto\\.pensioneligibilityList" + i
									+ "\\.rangeTo").val("");
					let factorName=$("#pensionSchmDto\\.pensioneligibilityList" + i + "\\.factorApplicableDesc").val();
					removeDisable(i,factorName);
					if (i === 0) {
						$(
								"#pensionSchmDto\\.pensioneligibilityList" + i
										+ "\\.paySchedule").val("");
						$(
								"#pensionSchmDto\\.pensioneligibilityList" + i
										+ "\\.amt").val("");
					}
				});
		$(".checkboxClass ").attr("disabled", true);
		$('#errorDiv').hide();
	} else {
		displayErrorsOnPage(errorList);
	}

}


function validatePansionCriteria(errorList) {
	
	let count = 0;
	let paySchedule = "#pensionSchmDto\\.paySchedule";
	let amount = "#pensionSchmDto\\.amt";
	let paySchedule1 = $(paySchedule).val();
	let amount1 = $(amount).val();
	let serviceId = $("#serviceId").val();
	let objofschme = $("#objOfschme").val();
	var beneficiaryCount = $("#beneficiaryCount").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var envFlag = $("#envFlag").val();
	
	if (paySchedule1 == "0" || paySchedule1 == undefined || paySchedule1 == '') {
		errorList.push(getLocalMessage('pension.sch.paymentreq'));
	}
	if (amount1 == "0" || amount1 == undefined || amount1 == ' ' || amount1 == '') {
		errorList.push(getLocalMessage('pension.sch.amountreq'));
	}
	/*else if(amount1>=10000)
		{
		errorList.push(getLocalMessage('pension.sch.amountgrt'));
		}*/

	if (serviceId == "0" || serviceId == undefined || serviceId == '') {
		errorList.push(getLocalMessage('social.sec.schemename.req'));
	}
	if (objofschme == "0" || objofschme == undefined || objofschme == '') {
		errorList.push(getLocalMessage('pension.sch.objscheme'));
	}
	
	if (envFlag == 'Y'){
	if (beneficiaryCount == null || beneficiaryCount == undefined
			|| beneficiaryCount == "") {
		errorList.push(getLocalMessage('social.validation.eneficaryCount'));
	}
	if (fromDate == null || fromDate == undefined|| fromDate == "") {
		errorList.push(getLocalMessage('social.demographicReport.valid.fromdate'));
	}
	
	else
	{
		var dateErrList = validateDateFormat(fromDate);
		if(correct == false)
			{
			errorList.push('From Date : '+dateErrList);
			}
	}
	if (toDate==""||toDate == null || toDate == undefined) {
		errorList.push(getLocalMessage('social.demographicReport.valid.todate'));
	}
	else
	{
		var dateErrList = validateDateFormat(toDate );
		if(correct == false)
			{
			errorList.push('To Date : '+dateErrList);
			}
	}
	//compare date
	if((toDate!=false|| toDate!=""||toDate != null || toDate != undefined) && (fromDate != null || fromDate != undefined|| fromDate != ""))
	{
		var formaterror = compareDate1(fromDate,toDate);
		 if(formaterror.length > 0) 	
		 errorList.push(getLocalMessage("date.validation"));
	 }
	}
	
	var rowCount = $('#criteriatableId tr').length;
	for (let i = 0; i < rowCount - 2; i++) {
		let criteriaId = "#pensionSchmDto\\.pensioneligibilityList" + i
				+ "\\.criteriaId";
		let rangeFrom = "#pensionSchmDto\\.pensioneligibilityList" + i
				+ "\\.rangeFrom";
		let rangeTo = "#pensionSchmDto\\.pensioneligibilityList" + i
				+ "\\.rangeTo";
		let factorApplicableDesc = "#pensionSchmDto\\.pensioneligibilityList" + i
		+ "\\.factorApplicableDesc";
		const checkOrUnchecked = $(
				'input:checkbox[id=pensionSchmDto\\.pensioneligibilityList' + i
						+ '\\.checkBox1]').is(':checked');
		let criteriaId1 = $(criteriaId).val();
		let rangeFrom1 = $(rangeFrom).val();
		let rangeTo1 = $(rangeTo).val();
		let factorApplicableDesc1= $(factorApplicableDesc).val();
		if (checkOrUnchecked) {
			if (criteriaId1 == "0" || criteriaId1 == undefined
					|| criteriaId1 == '') {
				errorList.push(getLocalMessage('pension.sch.criteria')+" " +factorApplicableDesc1);
			}
			
			
		
			
			if (factorApplicableDesc1 =="Age")
			{		
				if (rangeFrom1 == "0" || rangeFrom1 == undefined 
						|| rangeFrom1 == '') {
					errorList.push(getLocalMessage('Enter "Range from" for  ')+" " +factorApplicableDesc1);
				}
				
				if (rangeTo1 == "0" || rangeTo1 == undefined || rangeTo1 == '') {
					errorList.push(getLocalMessage('Enter "Range to" for  ')+" " + factorApplicableDesc1);
				}
				
				if (parseInt(rangeTo1) < parseInt(rangeFrom1))
					{
					errorList.push(getLocalMessage("social.range.toValue.notLess.fromValue.age"));
					}
				else if(rangeFrom1>=151 || rangeTo1>=151)
				{
				errorList.push(getLocalMessage('pension.sch.invalidage'));
				}
				
				}
			
			
			if (factorApplicableDesc1 =="Disability")
			{
				
			if (rangeFrom1 == "0" || rangeFrom1 == undefined 
					|| rangeFrom1 == '') {
				errorList.push(getLocalMessage('Enter "Range from" for  ')+" "  + factorApplicableDesc1);
			}
			
			if (rangeTo1 == "0" || rangeTo1 == undefined || rangeTo1 == '') {
				errorList.push(getLocalMessage('Enter "Range to" for  ')+" " + factorApplicableDesc1);
			}
			
			if (parseInt(rangeTo1) < parseInt(rangeFrom1))
			{
			errorList.push(getLocalMessage("social.range.toValue.notLess.fromValue.disability"));
			}
			
			else if(rangeFrom1>101 || rangeTo1>101)
				{
				errorList.push(getLocalMessage('pension.sch.invaliddis'));
				}
			}
			
			
			if (factorApplicableDesc1 =="Income")
			{
				
			if (rangeFrom1 == "0" || rangeFrom1 == undefined 
					|| rangeFrom1 == '') {
				errorList.push(getLocalMessage('Enter "Range from" for  ')+" " + factorApplicableDesc1);
			}
			
			if (rangeTo1 == "0" || rangeTo1 == undefined || rangeTo1 == '') {
				errorList.push(getLocalMessage('Enter "Range to" for  ')+" " + factorApplicableDesc1);
			}
			if (parseInt(rangeTo1) < parseInt(rangeFrom1))
			{
			errorList.push(getLocalMessage("social.range.toValue.notLess.fromValue.income"));
			}
			}
			
			if (factorApplicableDesc1 =="Education")
			{
				
			if (rangeFrom1 == "0" || rangeFrom1 == undefined 
					|| rangeFrom1 == '') {
				errorList.push(getLocalMessage('Enter "Range from" for  ')+" " + factorApplicableDesc1);
			}
			
			if (rangeTo1 == "0" || rangeTo1 == undefined || rangeTo1 == '') {
				errorList.push(getLocalMessage('Enter "Range to" for  ')+" "+ factorApplicableDesc1);
			}
			
			if (parseInt(rangeTo1) < parseInt(rangeFrom1))
			{
			errorList.push(getLocalMessage("social.range.toValue.notLess.fromValue.education"));
			}
			if(rangeFrom1>9999 || rangeTo1>9999)
			{
			errorList.push(getLocalMessage('pension.sch.invalidedu'));
			}
			
			}
			
						
			
		} else {
			count++;
			if (rowCount - 2 == count) {
				errorList
						.push(getLocalMessage('pension.sch.onecheckedvalue'));
			}
		}

	}
	return errorList;

}

function validateSourceOfFound(errorList){
	if (serviceId == "0" || serviceId == undefined || serviceId == '') {
		errorList.push(getLocalMessage('social.sec.schemename.req'));
	}
	
}

function deleteDetails(batchId, element) {

	
	var requestData = {
		"batchId" : batchId,
	};
	var divName = '.nextPageClass';
	var ajaxResponse = __doAjaxRequest(URL + '?deleteSchemeDetails', 'POST',
			requestData, false, 'html');

	$(divName).html(ajaxResponse);
	let modeType = $("#modeType").val();
	if (modeType === "E") {
		$('input:checkbox[class^="checkboxClass"]').each(function() {
			$('input:checkbox[class^="checkboxClass"]').prop('checked', true);
		});
	}
	prepareTags();
}

function savePensionDetails(element) {
	
	var errorList = [];
	//errorList = validatePansionCriteria(errorList);
	var rowCount = $('#summitAfterSaveId tr').length;
	if (rowCount>0)
	{
		errorList = validateUnitDetailTable(errorList);
		
		if (errorList.length == 0) {
			/*
			 * var ajaxResponse = __doAjaxRequest(URL+'?savePensionDetails',
			 * 'POST', requestData, false, 'html');
			 */

			return saveOrUpdateForm(element, '', 'PensionSchemeMaster.html', 'saveform');
		} else {
			displayErrorsOnPage(errorList);
		}
	} else {
		errorList.push(getLocalMessage('pension.sch.atleastonerecord'));
		displayErrorsOnPage(errorList);
	}
}

function updatePensionDetails(element) {
	var errorList = [];
	var applValidFlag=  $("#applValidFlag").val();
	var envFlag  = $("#envFlag").val();
  

	var theForm = '#pensionSchemeId';
	var requestData = {};
	requestData = __serializeForm(theForm);
	 
	var checkOrUnchecked = $('input:checkbox[id=checkBoxIds' + i + ']').is(
			':checked');
	var checkOrUnchecked = $('input:checkbox[id=checkBoxIds]').is(':checked');
     //validation based on inactive scheme
	  if (envFlag  == 'Y' && applValidFlag == 'Y' && !checkOrUnchecked) {
		errorList.push(getLocalMessage("scheme.edit.validation"));
		displayErrorsOnPage(errorList);
	}
	
	if (errorList == 0){
	if (checkOrUnchecked) {
		var errorList = [];
		
		var URL = 'PensionSchemeMaster.html?inactiveScheme';
		var response = __doAjaxRequest(URL, 'POST', requestData, false);
		$(formDivName).html(response);
		editModeProcess(response);
		prepareDateTag();
	}
	else
	{
		var ajaxResponse = __doAjaxRequest('PensionSchemeMaster.html?updatePensionDetails', 'POST',
				requestData, false, 'json');
		$(formDivName).html(ajaxResponse);
		editModeProcess(ajaxResponse);
		prepareDateTag();
		
	}
	}else{
		displayErrorsOnPage(errorList);
	}
	
	
	
	
	
	
	
}

function resetPensionCriteria(element,id,orgId) {
/*	
	$('.appendableClass1')
			.each(
					function(i) {
						$(
								"#pensionSchmDto\\.pensioneligibilityList" + i
										+ "\\.criteriaId").val("");
						$(
								"#pensionSchmDto\\.pensioneligibilityList" + i
										+ "\\.rangeFrom").val("");
						$(
								"#pensionSchmDto\\.pensioneligibilityList" + i
										+ "\\.rangeTo").val("");
						if (i === 0) {
							$(
									"#pensionSchmDto\\.pensioneligibilityList"
											+ i + "\\.paySchedule").val("");
							$(
									"#pensionSchmDto\\.pensioneligibilityList"
											+ i + "\\.amt").val("");
						}
					});*/
	
	var requestData = {
			"id":id,
			"orgId":orgId,
			"type":"R"
			
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PensionSchemeMaster.html?resetForm', requestData, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();

}

function editModeProcess(response) {
	
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');
	
	if (!errorsPresent || errorsPresent == undefined
			|| errorsPresent.length == 0) {
		
		//var message = response.command.message;
		var message = "Form updated succesfully"
		displayMessageOnSubmit(message);
	} else {
		$('.pagediv').html(response);
		prepareDateTag();
	}
}

function displayMessageOnSubmit(message) {
	
	var errMsgDiv = '.msg-dialog-box';
	var cls = getLocalMessage('asset.proceed');

	var d = '<h5 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}
function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}
function proceed() {
	
	//	backToHomePage();
    
	window.location.href = URL;
	$.fancybox.close();
}

function findDuplicate(event, currentRow){
	
	 $(".error-div").hide();
	var errorList = [];
	$('.appendableClass').each(function(i) {
		var unit = "#pensionSchmDto\\.pensionSourceFundList"+ i + "\\.sponcerId"; 
	    				if(currentRow != i && ($("#pensionSchmDto\\.pensionSourceFundList"+ currentRow + "\\.sponcerId").val() == $(unit).val()))
		  	    		{	
		  	    				errorList.push(getLocalMessage('Duplicate Selection'));
		  	    				$("#pensionSchmDto\\.pensionSourceFundList"+ currentRow + "\\.sponcerId").val("");		
		  	    				displayErrorsOnPage(errorList);
		  	    		} 
	
	    		    
	});
}

function checkBoxDisableAll(element) {
	
	var checkOrUnchecked = $('input:checkbox[id=checkBoxIds]').is(':checked');
	$('.appendableClass').each(function(i) {
	if (checkOrUnchecked) {
		$("#pensionSchemeId").prop("disabled",true);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.criteriaId").prop("disabled",true);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.rangeFrom").prop("disabled",true);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.rangeTo").prop("disabled",true);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.paySchedule").prop("disabled",true);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.amt").prop("disabled",true);
		$("#Savebutton").prop("disabled",true);
		$("#Resetbutton").prop("disabled",true);
		$(".deleteDetails ").attr("disabled",true);
	
		
	}
	else {
		$("#pensionSchemeId").prop("disabled",false);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.criteriaId").prop("disabled",false);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.rangeFrom").prop("disabled",false);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.rangeTo").prop("disabled",false);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.paySchedule").prop("disabled",false);
		$("#pensionSchmDto\\.pensioneligibilityList"+ i + "\\.amt").prop("disabled",false);
		$("#Savebutton").prop("disabled",false);
		$("#Resetbutton").prop("disabled",false);
		$("#Deletebutton").prop("disabled",false);
		$(".deleteDetails ").attr("disabled",false);
	}
	});
	
	

}


function checkBoxValidationCall(element) {
	
	const checkOrUnchecked = $('input:checkbox[id=checkBoxIds]').is(':checked');
	if (checkOrUnchecked) {
		$('input:checkbox[class^="checkboxClass"]').each(function() {
			$('input:checkbox[class^="checkboxClass"]').prop('checked', true);
		});
	} else {
		$('input:checkbox[class^="checkboxClass"]').each(function() {
			$('input:checkbox[class^="checkboxClass"]').prop('checked', false);
		});
	}

}



var correct =true;
function validateDateFormat(dateElementId)
{
	
	 var errorList = [];
	
	 var dateValue= dateElementId;
	 if(dateValue != null && dateValue != ""){
	 	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	 	if(dateValue.match(dateformat))
	 	{
	 	var opera1 = dateValue.split('/');
	 	lopera1 = opera1.length;
	 	if (lopera1>1)
	 	{
	 	var pdate = dateValue.split('/');
	 	}
	 	var dd = parseInt(pdate[0]);
	 	var mm  = parseInt(pdate[1]);
	 	var yy = parseInt(pdate[2]);
	 	var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	 	if (mm==1 || mm>2)
	 	{
	 	if (dd>ListofDays[mm-1])
	 	{
	 	errorList.push('Invalid Date Format');
	 	}
	 	}
	 	if (mm==2)
	 	{
	 	var lyear = false;
	 	if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
	 	{
	 	lyear = true;
	 	}
	 	if ((lyear==false) && (dd>=29))
	 	{
	 	errorList.push('Invalid Date Format');
	 	}
	 	if ((lyear==true) && (dd>29))
	 	{
	 	errorList.push('Invalid Date Format');
	 	}
	 	}
	 	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	 	var sDate = new Date(dateValue.replace(pattern,'$3-$2-$1'));
	 	if (sDate > new Date()) {
	 	}
	 	}
	 	else
	 	{
	 	errorList.push('Invalid Date Format');
	 	}	
	 }
	 
	 displayErrorsOnPage(errorList);
	  if(errorList.length > 0)
	 { correct = false;}
	 else{correct = true;}
		return errorList;
	
	 
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}


function compareDate1(date1,date2)
{

	var errorList = [];
	var dateValue1 = date1;
	var dateValue2 = date2;
	//split the dates
	var split1 = dateValue1.split('/');
	var split2 = dateValue2.split('/');
	
	var dd1 = parseInt(split1[0]);
	var mm1 = parseInt(split1[1]);
	var yy1 = parseInt(split1[2]);
	
	var dd2 = parseInt(split2[0]);
	var mm2 = parseInt(split2[1]);
	var yy2 = parseInt(split2[2]);
	//comapre the dates directly
	
	//1 should not be greater than 2
	
	if(yy1 == yy2)
	{
			if(mm1 > mm2)
			{
				errorList.push("month is invalid");
			}
			if(mm1 == mm2)
				{
					if(dd1 > dd2)
						{
						errorList.push("Date is invalid");
						}
				}
	}
	else if(yy1 > yy2)
		{
		errorList.push("Year is invalid");
		}
	 return errorList;
}
