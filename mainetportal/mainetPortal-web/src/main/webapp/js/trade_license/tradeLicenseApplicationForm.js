var formDivName = '.content-page';
var divName = ".widget-content";

$(document).ready(
		function() {
			var errorList = [];		
			$(document).on('change', '.tradeCat', function() {
				var p=1;
				var q=0;
				var type=($('option:selected', $("#triCod"+1)).attr('code'));
				$("#itemDetails tbody tr").each(
						function(i) {
				//#142379-> code changes as per requirement  multiple category allowed to select in case of trade also
	
				/*		if(($('option:selected', $("#triCod"+p)).attr('code')) == 'TL'){
				
					$("#trdQuantity"+q).prop("disabled", true); 
					$("#addBtn").hide();
					$(".delButton").hide();
					//$("#trdUnit"+q).prop("disabled", true); 			
				} else{
					$("#trdQuantity"+q).prop("disabled", false); 
					$("#addBtn").show();
					$(".delButton").show();
					//$("#trdUnit"+q).prop("disabled", false); 
				}*/
				if(type!=($('option:selected', $("#triCod"+p)).attr('code'))){					
					errorList.push(getLocalMessage("tradelicense.validation.categoryType"));
					$("#triCod"+p).val('0');
					
					displayErrorsOnPage(errorList);
				}	
				p=p+6;
				q=q+1;			
			});
		});

			$("#NOCapplicable").hide();
			var NOCApplicable = $('input[type=radio]:checked').val();
			$(function() {
				$('#trdLicfromDate').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					minDate : 0
				});
			});

			$(function() {
				$('#trdLictoDate').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					minDate : 0
				});
			});

			$('#tradeLicenseForm').validate({
				onkeyup : function(element) {
					this.element(element);
					console.log('onkeyup fired');
				},
				onfocusout : function(element) {
					this.element(element);
					console.log('onfocusout fired');
				}
			});

			/* $('#businessStartDate').attr('readonly', true); */

			$(function() {
				$('#businessStartDate').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,

				});
			});
		
			var ownType = $("#trdFtype").val();

			if (ownType == "0" || ownType == undefined || ownType == "") {

				$("#agreementDate").hide();

			}

			if (ownType == "R") {

				$("#agreementDate").show();

			}
			if (NOCApplicable == "Y") {
				$("#NOCapplicable").show();
			}

			$("#agreementFromDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,

			});

			$('#agreementToDate').datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,

			});

			var flag = $("#trdFtype").val();
			if (flag != null && flag != "" && flag != undefined) {
				getOwnerTypeDetails();
			}

			$("#licensePeriod").show();
			$("#showMand").hide();
			$("#trdLictype")
					.change(
							function(e) {
								if ($('#trdLictype option:selected').attr(
										'code') == "T") {
									$("#licensePeriod").show();
									$("#showMand").hide();

								} else if ($('#trdLictype option:selected')
										.attr('code') == "P") {
									$("#licensePeriod").hide();
									$("#showMand").show();
								}

							});

			if ($("#viewMode").val() == 'V') {
				$("#checkId").attr("checked", true);
			} else {
				$("#checkId").attr("checked", false);
			}

			$("#resetform").on("click", function() {
				window.location.reload("#tradeLicenseForm")
			});

			var flag = $("#hideAddBtn").val();
			if (flag == 'Y') {
				$("#addBtn").hide();
				$("#hidePlusBtn").hide();
			} else {
				$("#addBtn").show();
				$("#hidePlusBtn").show();
			}

			var flag = $("#hideDeleteBtn").val();
			if (flag == 'Y') {
				$("#deleteBtn").hide();
				$("#hideMinusBtn").hide();
			} else {
				$("#deleteBtn").show();
				$("#hideMinusBtn").show();
			}
			var liceseTypeFlag = $("#hideTemporaryDate").val();
			if ((liceseTypeFlag == 'N')
					|| ($('#trdLictype option:selected').attr('code') == "P")) {
				$("#licensePeriod").hide();
				$("#showMand").show();
			} else {
				$("#licensePeriod").show();
				$("#showMand").hide();
			}
			
			var viewMode = $("#viewMode").val();
			if (viewMode == 'V'){
				$('.tradeCat').attr('disabled', true);
			}else{
				$('.tradeCat').attr('disabled', false);	
			}
			//#129508
			 var propertyNo = $("#propertyNo").val();
			 if (propertyNo == "0" || propertyNo == undefined || propertyNo == ""){
			     $(".propDetails").hide();
			    }else{
			    	$(".propDetails").show();
			    }

		});

$('body').on('focus', ".hasMobileNo", function() {
	$('.hasMobileNo').keyup(function() {
		this.value = this.value.replace(/[^1-9][0-9]{9}/g, '');
		$(this).attr('maxlength', '10');
	});
});

$('body').on('focus', ".hasAadharNo", function() {
	$('.hasAadharNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

function saveTradeLicenseForm(obj) {

    var errorList = [];
    var propertyNo = $("#propertyNo").val();
    var kdmcEnv = $("#kdmcEnv").val();
    if (kdmcEnv == 'N'){
    if (propertyNo != "" && propertyNo != null && propertyNo != undefined){
	var URL = 'TradeApplicationForm.html?checkAssStatus';
	var response = __doAjaxRequest(URL, 'POST', {}, false, 'json');
	if (response == 'N') {
		errorList.push(getLocalMessage("trade.assesment.validate"));
	  }
     }
    }
	var rowCount = $('#ownerDetail tr').length;	
	var ownerType = $("#trdFtype" + " option:selected").attr("code");
	
	errorList = validateMarketLicenseForm(errorList);
	errorList = errorList.concat(validateOwnerDetailsTable());
	errorList = errorList.concat(validateitemDetailsTable());
	//126060
	if(errorList.length == 0){
		if(ownerType == 'A' || ownerType == 'P'){
			if (rowCount <=2 )
			errorList.push(getLocalMessage("trade.owner.validate"));
		}
	}

	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
			getLocalMessage("tradelicense.save.succ.form.msg"),
				"TradeApplicationForm.html?getCheckListAndCharge",
				'saveTradeLicenseForm');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function getChecklistAndCharge(obj) {

	var theForm = '#tradeLicenseForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'TradeApplicationForm.html?getCheckListAndCharges';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	if (returnData != null) {

		// var divName = '.content';
		// $(divName).html(returnData);
		$(formDivName).html(returnData);
		// $(divName).show();

		// $('.showDivDetails').html(returnData);
		prepareTags();
	}
}

function showTermsConditionForm(element) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(
			'TradeApplicationForm.html?viewTermsCondition', 'POST',
			requestData, false, 'html');
	// $(formDivName).html(response);
	var divContents = response;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title></title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="assets/libs/jquery/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

}

function validateMarketLicenseForm(errorList) {
	var licenseType = $("#trdLictype").val();
	var businessName = $("#trdBusnm").val();
	var businessAddress = $("#trdBusadd").val();
	var licenseFromPeriod = $("#trdLicfromDate").val().split(' ')[0];
	var licenseToPeriod = $("#trdLictoDate").val().split(' ')[0];
	var businessDate =$("#businessStartDate").val().split(' ')[0];
	var ownershipType = $("#trdFtype").val();
	var propertyNo = $("#propertyNo").val();
	var checkbox = document.getElementById("checkId");
	var gstNo = $("#app_gstNumber").val();
	var trdWard1 = $("#trdWard1").val();
	var trdWard2 = $("#trdWard2").val();
	var totalOutsatandingAmt = $('#totalOutsatandingAmt').val();
	var kdmcEnv = $("#kdmcEnv").val();
	var sudaEnv =  $("#sudaEnv").val();
	var pincode = $('#pincode').val();
	var landMark = $('#landMark').val();
	var totalWaterOutsatandingAmt = $('#totalWaterOutsatandingAmt').val();
	
	if(sudaEnv!='Y'){
		if (trdWard1 == "0" || trdWard1 == undefined || trdWard1 == "") {
	
			errorList.push(getLocalMessage("trade.license.zone"));
		}
		if (trdWard2 == "0" || trdWard2 == undefined || trdWard2 == "") {
	
			errorList.push(getLocalMessage("trade.license.ward"));
		}
	}
	if (licenseType == "0" || licenseType == undefined || licenseType == "") {

		errorList.push(getLocalMessage("tradelicense.validation.licensetype"));
	}
	if (businessDate == "0" || businessDate == undefined || businessDate == "") {

		errorList.push(getLocalMessage("tradelicense.validation.businessDate"));
	}
	
	if ($('#trdLictype option:selected').attr('code') == "T") {
		if (licenseFromPeriod == "" || licenseFromPeriod == undefined
				|| licenseFromPeriod == "") {
			errorList
					.push(getLocalMessage("tradelicense.validation.licensefromperiod"));
		}
		if (licenseToPeriod == "" || licenseToPeriod == undefined
				|| licenseToPeriod == "") {
			errorList
					.push(getLocalMessage("tradelicense.validation.licensetoperiod"));
		}
		if (process(licenseFromPeriod) > process(licenseToPeriod)) {

			errorList
					.push("Please select  licence to date greater than license from  date ");

		}
		if (businessDate != null && licenseToPeriod != null) {

			if (process(businessDate) > process(licenseToPeriod)) {

				errorList
						.push("Please select  business date less than  license to date ");

			}

		}
	}
	if (businessName == "" || businessName == undefined || businessName == "") {
		errorList.push(getLocalMessage("tradelicense.validation.businessname"));
	}

	if (businessAddress == "" || businessAddress == undefined
			|| businessAddress == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.businessaddress"));
	}

	if (ownershipType == "0" || ownershipType == undefined
			|| ownershipType == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.ownershipType"));
	}
       if (sudaEnv != 'Y' && $('#trdLictype option:selected').attr('code') == "P") {
		if (propertyNo == "" || propertyNo == undefined || propertyNo == "") {
			errorList
					.push(getLocalMessage("tradelicense.validation.propertyNo"));
		}
	}

	/*
	 * if($("#checkId").prop('checked')==false){
	 * errorList.push(getLocalMessage('Please Accept Terms And Condition')); }
	 */
	if (checkbox.checked == false) {
		errorList.push(getLocalMessage('trd.valid.term.cond'));
	}

	/*
	 * if ($('#trdLictype option:selected').attr('code') == "O") { if
	 * (aadharNo.value.length < 12) {
	 * errorList.push(getLocalMessage("tradelicense.validation.aadhar")); } }
	 */
	if (gstNo != 0 && gstNo != '' && gstNo != undefined) {
		var regExGst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([Z]){1}([0-9|a-zA-Z]){1}?$/;
		if (!regExGst.test(gstNo) && gstNo != '') {
			errorList.push("Please Enter valid GST No.");
		}
	}
	var NOCApplicable = $('input[type=radio]:checked').val();
	if (NOCApplicable == '' || NOCApplicable == undefined) {
		errorList
				.push(getLocalMessage("tradelicense.validation.nocApplicable"));
	}

	if (NOCApplicable == "Y") {
		var fireNOCNo = $("#fireNOCNo").val();

		if (fireNOCNo == "" || fireNOCNo == undefined || fireNOCNo == "") {
			errorList
					.push(getLocalMessage("tradelicense.validation.fireNOCNo"));
		}
	}
	
	if (sudaEnv == 'Y') {	
		if (totalOutsatandingAmt > 0) {
			errorList.push(getLocalMessage("trade.property.due"));
		}

		if (totalWaterOutsatandingAmt > 0) {
			errorList.push(getLocalMessage("trade.Water.due"));
		}
		if (landMark == "" || landMark == undefined || landMark == null) {
			errorList.push(getLocalMessage("trade.valid.landMark"));
		}

		if (pincode == "" || pincode == undefined || pincode == null) {
			errorList.push(getLocalMessage("trade.valid.pincode"));
		}
	}
	
	return errorList;
}

function validateTradeForm(errorList) {

	var errorList = [];
	var amountToPay = $("#amountToPay").val();

	if (amountToPay != undefined) {
		if (amountToPay > 0
				&& $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == ""
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == undefined) {
			errorList.push(getLocalMessage("rti.validation.collectionMode"));
		}
	}
	return errorList;
}

/*
 * function backTermsConditionPage(element) {
 * 
 * 
 * var formName = findClosestElementId(element, 'form'); var theForm = '#' +
 * formName; var requestData = __serializeForm(theForm); var response =
 * __doAjaxRequest('TradeApplicationForm.html?backPage', 'POST', requestData,
 * false, 'html'); $(formDivName).html(response); }
 */
function backPage() {

	window.location.href = getLocalMessage("CitizenHome.html");
}

/*
 * function addEntryData() {
 * 
 * var errorList = []; errorList = validateOwnerDetails(); if (errorList.length ==
 * 0) { addTableRow('ownerDetail'); } else { $('#ownerDetail').DataTable();
 * displayErrorsOnPage(errorList); } }
 */

/*
 * function validateOwnerDetails() {
 * 
 * var errorList = []; if ($.fn.DataTable.isDataTable('#ownerDetail')) {
 * $('#ownerDetail').DataTable().destroy(); } if (errorList == 0)
 * $("#ownerDetail tbody tr") .each( function(i) { var ownerName = $("#troName" +
 * i).val(); var fatherhusbandName = $("#troMname" + i).val(); var ownerGender =
 * $("#troGen" + i).val(); var ownerAddress = $("#troAddress" + i).val(); var
 * ownerMobileNo = $("#troMobileno" + i).val(); //var constant = 1;
 * 
 * if (ownerName == '0' || ownerName == undefined || ownerName == "") {
 * errorList .push(getLocalMessage("tradelicense.validation.ownername") ); } if
 * (fatherhusbandName == "" || fatherhusbandName == undefined ||
 * fatherhusbandName == "0") { errorList
 * .push(getLocalMessage("tradelicense.validation.fatherhusbandname") ); } if
 * (ownerGender == "" || ownerGender == undefined || ownerGender == "0") {
 * errorList .push(getLocalMessage("tradelicense.validation.ownergender") ); }
 * if (ownerAddress == "" || ownerAddress == undefined || ownerAddress == "0") {
 * errorList .push(getLocalMessage("tradelicense.validation.owneraddress") ); }
 * if (ownerMobileNo == "" || ownerMobileNo == undefined || ownerMobileNo ==
 * "0") { errorList
 * .push(getLocalMessage("tradelicense.validation.ownerMobileNo") ); }
 * 
 * }); errorList .push(getLocalMessage("tradelicense.validation.ownerMobileNo") + " " +
 * constant);
 * 
 * return errorList; }
 */

function validateMobile(mobile) {
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(mobile);
}

function deleteEntry(obj, ids) {

	deleteTableRow('ownerDetail', obj, ids);
	$('#ownerDetail').DataTable().destroy();
	$("#ownerDetail tbody tr").each(function(i) {

	});

}

/*
 * function addItemDetailData() {
 * 
 * var errorList = []; errorList = validateItemDetails(); if (errorList.length ==
 * 0) { addTableRow('itemDetails'); } else { $('#itemDetails').DataTable();
 * displayErrorsOnPage(errorList); } }
 * 
 * function validateItemDetails(errorList) {
 * 
 * 
 * var errorList = []; if ($.fn.DataTable.isDataTable('#itemDetails')) {
 * $('#itemDetails').DataTable().destroy(); } if (errorList == 0)
 * $("#itemDetails tbody tr") .each( function(i) {
 * 
 * var itemCode = $("#triCod" + i).val(); var itemConstant = 1;
 * 
 * if (itemCode == "" || itemCode == undefined || itemCode == "0") { errorList
 * .push(getLocalMessage("tradelicense.validation.category") + " " +
 * itemConstant); } }); return errorList; }
 */

function deleteItemDetailEntry(obj, ids) {

	deleteTableRow('itemDetails', obj, ids);
	$('#itemDetails').DataTable().destroy();
	$("#itemDetails tbody tr").each(function(i) {

	});
}

/*
 * function deleteEntry2(tableId, obj, ids) { if (ids != "1") {
 * deleteTableRow2(tableId, obj, ids); }
 */

function getOwnerTypeDetails() {

	var ownerType = $("#trdFtype" + " option:selected").attr("code");

	if (ownerType == 'R') {
		$("#agreementDate").show();
	} else {
		$("#agreementDate").hide();
	}

	if (ownerType != undefined) {
		var data = {
			"ownershipType" : ownerType
		};
		var URL = 'TradeApplicationForm.html?getOwnershipTypeDiv';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#owner").html(returnData);
		$("#owner").show();
	} else {
		$("#owner").html("");
	}
}

function getPropertyDetails(element) {

	var errorList = [];
	var kdmcEnv = $("#kdmcEnv").val();
	var theForm = '#tradeLicenseForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'TradeApplicationForm.html?getPropertyDetails';
	var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');

	/* if(response.pmPropid==null || response.pmPropid=="") */
	if (response.pmPropNo != null) {
		$('#primaryOwnerName').val(response.primaryOwnerName);
		$('#usage').val(response.usage);
		$('#totalOutsatandingAmt').val(response.totalOutsatandingAmt);
		$('#trdpradd').val(response.propertyAddress);
		$('#villageName').val(response.villageName);
		$('#surveyNumber').val(response.surveyNumber);
		$('#partNo').val(response.partNo);
		$('#plotNo').val(response.plotNo);
		$('#propLvlRoadType').val(response.propLvlRoadType);
		$('#assPlotArea').val(response.assPlotArea);
		$('#landType').val(response.landType);
		$('#totalWaterOutsatandingAmt').val(response.totalWaterOutsatandingAmt);
		$(".propDetails").show();
		if (kdmcEnv == 'N')
		if (response.assessmentCheckFlag == 'N'){
			errorList.push(getLocalMessage("trade.assesment.validate"));
			displayErrorsOnPage(errorList);
		}
	} else if (response.length == null) {
		errorList.push(getLocalMessage("trade.valid.propNo"));
		displayErrorsOnPage(errorList);
		$('#primaryOwnerName').val("");
		$('#usage').val("");
		$('#totalOutsatandingAmt').val("");
		$('#trdpradd').val("");
		$('#villageName').val("");
		$('#surveyNumber').val("");
		$('#partNo').val("");
		$('#plotNo').val("");
		$('#propLvlRoadType').val("");
		$('#assPlotArea').val("");
		$('#landType').val("");
		$('#propertyNo').val("");
		$('#totalWaterOutsatandingAmt').val("");
		$(".propDetails").hide();
	}
	if (kdmcEnv == 'N'){
	if (response.plotNo == "")
		$("#plotNo").attr("readonly", false);
	if (response.surveyNumber == "")
		$("#surveyNumber").attr("readonly", false);
	if (response.villageName == null)
		$("#villageName").attr("readonly", false);
	if (response.partNo == "")
		$("#partNo").attr("readonly", false);
	if (response.propLvlRoadType == null)
		$("#propLvlRoadType").attr("disabled", false);
	if (response.assPlotArea == "")
		$("#assPlotArea").attr("readonly", false);
	if (response.landType == null)
	   $("#landType").attr("disabled", false);
	}
}

$(function() {
	/* To add new Row into table */
	$("#itemDetails").on('click', '.addItemCF', function() {

		var errorList = [];
		errorList = validateitemDetailsTable(errorList);
		if (errorList.length == 0) {

			var content = $("#itemDetails").find('tr:eq(1)').clone();
			$("#itemDetails").append(content);

			content.find("select").val('0');
			content.find("input:hidden").val('');
			content.find("input:text").val("");
			$('.error-div').hide();
			reOrderItemDetailsSequence(); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

$(function() {

	/* To add new Row into table */
	$("#itemDetails").on('click', '.delButton', function() {

		if ($("#itemDetails tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderItemDetailsSequence(); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});
});

function reOrderItemDetailsSequence() {
	$("#itemDetails tbody tr").each(
			function(i) {

				var utp = i;
				if (i > 0) {
					utp = i * 6;
				}
				// IDs
				$(this).find("input:hidden:eq(0)").attr("id",
						"triId" + (utp + 1)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(1)").attr("id",
						"triId" + (utp + 2)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(2)").attr("id",
						"triId" + (utp + 3)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(3)").attr("id",
						"triId" + (utp + 4)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(4)").attr("id",
						"triId" + (utp + 5)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("select:eq(0)").attr("id", "triCod" + (utp + 1));
				$(this).find("select:eq(1)").attr("id", "triCod" + (utp + 2));
				$(this).find("select:eq(2)").attr("id", "triCod" + (utp + 3));
				$(this).find("select:eq(3)").attr("id", "triCod" + (utp + 4));
				$(this).find("select:eq(4)").attr("id", "triCod" + (utp + 5));
				$(this).find("input:text:eq(0)").attr("id", "trdUnit" + i);

				$(this).find("select:eq(0)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod1");
				$(this).find("select:eq(1)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod2");
				$(this).find("select:eq(2)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod3");
				$(this).find("select:eq(3)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod4");
				$(this).find("select:eq(4)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod5");
				$(this).find("input:text:eq(0)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].trdUnit");

			});
}

function validateitemDetailsTable(errorList) {
	var errorList = [];
	var subCategory = [];
	var flag = false;

	var rowCount = $('#itemDetails tr').length;
	if ($.fn.DataTable.isDataTable('#itemDetails')) {
		$('#itemDetails').DataTable().destroy();
	}

	if (errorList == 0)
		$("#itemDetails tbody tr")
				.each(
						function(i) {

							if (rowCount <= 2) {
								var itemCode1 = $("#triCod" + 1).val();
								var itemCode2 = $("#triCod" + 2).val();
								var itemCode3 = $("#triCod" + 3).val();
								var itemCode4 = $("#triCod" + 4).val();
								var itemCode5 = $("#triCod" + 5).val();
								var tradeQuantity = $("#trdUnit" + i).val();

							} else {
								var utp = i;
								utp = i * 6;
								var itemCode1 = $("#triCod" + (utp + 1)).val();
								var itemCode2 = $("#triCod" + (utp + 2)).val();
								var itemCode3 = $("#triCod" + (utp + 3)).val();
								var itemCode4 = $("#triCod" + (utp + 4)).val();
								var itemCode5 = $("#triCod" + (utp + 5)).val();
								var tradeQuantity = $("#trdUnit" + i).val();
								var level = i + 1;

							}

							if (validatePropertyNo(itemCode1)) {
								flag = true;
							}

							if (itemCode1 == "" || itemCode1 == undefined
									|| itemCode1 == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.category")
												+ " " + (i + 1));
							}

							if (itemCode2 == "" || itemCode2 == undefined
									|| itemCode2 == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.subCategory")
												+ " " + (i + 1));

							}
							if (tradeQuantity == ""
									|| tradeQuantity == undefined
									|| tradeQuantity == "0") {
								errorList
										.push(getLocalMessage("trade.item.value")
												+ " " + (i + 1));
							}

							else {
								if (subCategory.includes(itemCode2)) {
									errorList
											.push(getLocalMessage("Duplicate Sub Category.")
													+ (i + 1));
								}
								if (errorList.length == 0) {
									subCategory.push(itemCode2);
								}
							}
						});

	/*if (flag) {
		errorList.push("Please Select property No.");
	}*/

	return errorList;
}

/*
 * function fileCountUpload(obj) {
 * 
 * var errorList = []; errorList = validateOwnerDetails(); if (errorList.length ==
 * 0) { //addTableRow('ownerDetail'); var row = $("#ownerDetail tbody
 * .appendableClass").length; $("#length").val(row); var formName =
 * findClosestElementId(obj, 'form'); var theForm = '#' + formName; var
 * requestData = __serializeForm(theForm);
 * 
 * var response = __doAjaxRequest('TradeApplicationForm.html?fileCountUpload',
 * 'POST', requestData, false, 'html'); $("#owner").html(response);
 * prepareTags(); } else { $('#ownerDetail').DataTable();
 * displayErrorsOnPage(errorList); } }
 */

function doFileDeletion(obj, id) {

	requestData = {
		"id" : id
	};
	url = 'TradeApplicationForm.html?doEntryDeletion';
	var row = $("#ownerDetail tbody .appendableClass").length;
	if (row != 1) {
		// $("#ownerDetail tbody .appendableClass").parent().parent().remove();
		var response = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	}

}

/*
 * $("#ownerDetail").on("click", '.delButton', function(e) {
 * 
 * var row = $("#ownerDetail tbody .appendableClass").length; if (row != 1) {
 * $(this).parent().parent().remove(); var id = $(this).attr('id'); requestData = {
 * "id" : id }; url = 'TradeApplicationForm.html?doEntryDeletion'; var response =
 * __doAjaxRequest(url, 'POST', requestData, false, 'html'); }
 * e.preventDefault(); });
 */

function generateChallanAndPayment(element) {
	// #129512
	var status;
	saveObj = element;
	var yes = getLocalMessage('license.yes');
	var no = getLocalMessage('license.No');
	var warnMsg = getLocalMessage('payment.popup');

	message = '<p class="text-blue-2 text-center padding-15">' + warnMsg
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" type=\'button\' value=\'' + yes
			+ '\'  id=\'yes\' ' + ' onclick="onPaymentYes()"/>&nbsp;'
			+ '<input class="btn btn-success " type=\'button\' value=\'' + no
			+ '\'  id=\'no\' ' + ' onclick="closeConfirmBoxForm()"/>'
			+ '</div>';

	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;

}

//#129512
function onPaymentYes() {
	$.fancybox.close();
	var errorList = [];
	var status;
	$("#validationerrordiv").html("");
	if (errorList.length == 0) {

		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			status = saveOrUpdateForm(saveObj,
					getLocalMessage("tradelicense.save.succ.msg"),
					'TradeApplicationForm.html?PrintReport',
					'generateChallanAndPayement');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			status = saveOrUpdateForm(saveObj,
					getLocalMessage("tradelicense.save.succ.msg"),
					'TradeApplicationForm.html?redirectToPay',
					'generateChallanAndPayement');
		} else {

			status = saveOrUpdateForm(saveObj,
					getLocalMessage("tradelicense.save.succ.msg"),
					'CitizenHome.html', 'generateChallanAndPayement');
			// errorList.push(getLocalMessage('Please select any mode of
			// payment'));
			// displayErrorsOnPage(errorList);
		}
		//Defect #129362
		if (!$.trim($('#validationerrordiv').html()).length) {
			agencyRegAcknow(status);
		}
	} else {
		displayErrorsOnPage(errorList);
	}

}

function editForm(element) {
	return saveOrUpdateForm(element,
			getLocalMessage("tradelicense.save.succ.msg"), 'AdminHome.html',
			'saveTradeLicenseForm');
}

function editApplication(element) {	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('TradeApplicationForm.html?editApplication',
			'POST', requestData, false, 'html');

	$(formDivName).html(response);
	reOrderItemDetailsSequence(); // reorder id and Path
	getOwnerTypeDetails();
}

function resetTradeForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#tradeLicenseForm").validate().resetForm();
}

function validateOwnerDetailsTable() {

	var errorList = [];
	var rowCount = $('#ownerDetail tr').length;

	if ($.fn.DataTable.isDataTable('#ownerDetail')) {
		$('#ownerDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerDetail tbody tr")
				.each(
						function(i) {

							if (rowCount <= 2) {

								var ownerName = $("#troName" + i).val();
								var fatherhusbandName = $("#troMname" + i)
										.val();
								var ownerGender = $("#troGen" + i).val();
								var ownerAddress = $("#troAddress" + i).val();
								var ownerMobileNo = $("#troMobileno" + i).val();
								var ownerAdharNo = $("#troAdhno" + i).val();
								var emailId = $("#troEmailid" + i).val();
								var constant = 1;
							} else {
								var ownerName = $("#troName" + i).val();
								var fatherhusbandName = $("#troMname" + i)
										.val();
								var ownerGender = $("#troGen" + i).val();
								var ownerAddress = $("#troAddress" + i).val();
								var ownerMobileNo = $("#troMobileno" + i).val();
								var ownerAdharNo = $("#troAdhno" + i).val();
								var emailId = $("#troEmailid" + i).val();

								var constant = i + 1;
							}
							if (ownerName == '0' || ownerName == undefined
									|| ownerName == "") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownername")
												+ " " + constant);
							}
							if (fatherhusbandName == ""
									|| fatherhusbandName == undefined
									|| fatherhusbandName == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.fatherhusbandname")
												+ " " + constant);
							}
							if (ownerGender == "" || ownerGender == undefined
									|| ownerGender == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownergender")
												+ " " + constant);
							}
							if (ownerAddress == "" || ownerAddress == undefined
									|| ownerAddress == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.owneraddress")
												+ " " + constant);
							}

							if (ownerMobileNo == ""
									|| ownerMobileNo == undefined
									|| ownerMobileNo == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownerMobileNo")
												+ " " + constant);
							} else {
								if (!validateMobile(ownerMobileNo)) {
									errorList
											.push(getLocalMessage("tradelicense.validation.validMobileNo")
													+ " " + constant);
								}
							}

							if (emailId != "") {
								var emailRegex = new RegExp(
										/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
								var valid = emailRegex.test(emailId);
								if (!valid) {
									errorList
											.push(getLocalMessage('trade.vldnn.emailid')
													+ " " + constant);
								}
							}

							if (ownerAdharNo.length != 0
									&& ownerAdharNo.length < 12) {
								errorList
										.push(getLocalMessage('trade.valid.adharno')
												+ " " + constant);
							}

							if (ownerAdharNo != "") {
								var adharRegex = new RegExp(/^[0-9]\d{12}$/i);
								var valid = adharRegex.test(ownerAdharNo);
								if (!valid) {
									errorList
											.push(getLocalMessage('trade.valid.adharno')
													+ " " + constant);
								}
							}

						});

	return errorList;
}

$('body').on('focus', ".hasAadharNo", function() {
	$('.hasAadharNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

function validatePropertyNo(itemCode1) {

	if (itemCode1 != '0') {
		var requestData = {
			"itemCode1" : itemCode1
		};

		var response = __doAjaxRequest(
				'TradeApplicationForm.html?getOtherValue', 'POST', requestData,
				false, 'html');

		if (response === 1 || response === '1' || response == 1) {

			var propertyNo = $("#propertyNo").val();

			if (propertyNo == "" || propertyNo == undefined
					|| propertyNo == "0") {
				return true;

			}

		}

	}

}
function showNOC() {
	var r = $('input[type=radio]:checked').val();

	if (r == "Y") {
		$("#NOCapplicable").show();
		$('.addClass').addClass('required-control');

	}

	if (r == "N") {
		$("#NOCapplicable").hide();
		$('#fireNOCNo').val('');

	}

}

function process(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}
function agencyRegAcknow(status) {

	

	var URL = 'TradeApplicationForm.html?printAgencyRegAckw';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	var title = 'License Registration Acknowlegement';
	
	var printWindow = window.open('', '_blank');

	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print-theme-6.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
			.write('<script src="assets/libs/jquery/jquery.min.js" ></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

$(document).ready(function(){
	$("#itemDetails thead tr th:first-child").append("<i class='text-red-1'>*</i>");
	$("#itemDetails thead tr th:nth-child(2)").append("<i class='text-red-1'>*</i>");
});