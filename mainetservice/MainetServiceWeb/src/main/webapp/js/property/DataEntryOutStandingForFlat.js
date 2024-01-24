$(document).ready(function() {
	var finYearFlag = $("#finYearFlag").val();
	if (finYearFlag == 'Y') {
		$("#financialYear").val('0');
	}
});

$(function() {

	$("html, body").animate({
		scrollTop : 0
	}, "slow");

	$(".lessthancurrdate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',

	});

	$(".billToDate")
			.datepicker(
					{
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '-0d',

						onSelect : function() {
							var errorList = [];
							var dateFrom = $(".lessthancurrdate").datepicker(
									'getDate');
							var dateTo = $(".billToDate").datepicker('getDate');
							if (dateFrom >= dateTo) {

								errorList
										.push(getLocalMessage("prop.taxtoDate.later.billDate"));
								showBillError(errorList);
								$(".billToDate").val("");
							}

						}
					});

	var schId = $("#financialYear").val();
	if (schId == null || schId == 0) {
		$("#billWiseDetail").hide();
		$("#nextView").hide();
	} else {
		$("#billList").hide();
	}

});

function getYear(element) {

	var errorList = [];
	var finyear = $("#financialYear").val();
	if (finyear == 0) {
		errorList.push(getLocalMessage("property.report.finYear"));
	}
	if (errorList.length == 0) {
		var theForm = '#DataEntryOutStanding';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'DataEntrySuite.html?financialYearWiseFlatArrears';

		var returnData = __doAjaxRequestValidationAccor(element, URL, 'POST',
				requestData, false, 'html');
		$(formDivName).html(returnData);
		$("#nextView").show();
		$("#billList").hide();
		$("#billWiseDetail").show();
	} else {
		showVendorError(errorList);
	}

}

function getSchedule(element) {
	$("#nextView").hide();
	$("#billList").show();
	$("#billWiseDetail").hide();

}

function showErrorOnPage(errorList) {
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	return false;
}

function confirmToNext(element) {
	var theForm = '#DataEntryOutStanding';
	var ownerType=$('#ownerType').val();
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'DataEntrySuite.html?nextToViewAfterArrearForFlat';
	var returnData = __doAjaxRequest(URL, 'post', requestData, false, 'html');	
	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	//set owner details
	
	if (ownerType != "") {
		var data1 = {
			"ownershipType" : ownerType
		};
		var URL1 = 'DataEntrySuite.html?getOwnershipTypeDiv';
		var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
		$("#owner").html(returnData1);
		$("#owner").show();
	}		

}

function showBillError(errorList) {
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	return false;
}
