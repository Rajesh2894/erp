$(function() {

	$("#searchConnection").click(function() {
        
       var errorList=[]; 
       var connectionNo = $("#conNum").val();

       if (connectionNo == '' || connectionNo == undefined) {
    	   errorList
   		.push(getLocalMessage('water.serchconnectn.entrconnctnno'));
       }
        var errorList = validateApplicantInfo(errorList);
        if (errorList.length == 0) {
		var theForm = '#changeOfUsageId';
		var connectionNumber = $.trim($("#conNum").val());
		console.log(connectionNumber);
		var data = __serializeForm(theForm);
		var URL = 'ChangeOfUsage.html?getConnectionInfo';
		
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		 if (typeof (returnData) === "string") {
			$(formDivName).html(returnData);
			$("#conNum").prop("readonly", true);
			$("#searchConnection").prop('disabled', true);

		}
        }
        else{
        	displayErrorsOnPage(errorList);
        }
	});

});

$(function() {
	showHideBPL($('#povertyLineId').val());
	$("#povertyLineId").change(function() {
		var isBelowPoverty = $('#povertyLineId').val();
		showHideBPL(isBelowPoverty);
	});
});
function showHideBPL(value) {
	if (value == 'Y') {
		$('#bpldiv').show();
	} else {
		$('#bpldiv').hide();
	}
}
function getChecklistAndCharges(obj) {

	var errorList = [];
	/*
	 *  errorList =
	 * validateOldOwnerInfo(errorList); errorList =
	 * validateNewOwnerInfo(errorList);
	 */
	errorList = validateApplicantInfo(errorList);
	var isBPL = $('#povertyLineId').val();

	$('.appendableClass').each(function(i) {
		row = i + 1;
		if (isAdditionalOwnerDetailRequired(i) == 'Y') {
			errorList = validateAdditionalOwnerTableData(errorList, i);
		}
	});

	if (errorList.length == 0) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = {
			"isBPL" : isBPL
		};

		requestData = __serializeForm(theForm);

		var url = 'ChangeOfUsage.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(url, 'post', requestData, false, '',
				obj);

		$('#fomDivId').html(returnData);
		$('#povertyLineId').val(isBPL);
		$('#searchConnection').attr('disabled', true);
		/* $('#confirmToProceedId').attr('disabled',true); */

	} else {
		displayErrorsOnPage(errorList);
	}
}

function displayErrorsOnPage(errorList) {
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

function saveChangeOfUsage(element) {
	var url = "ChangeOfUsage.html?noAmount";
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		url = 'ChangeOfUsage.html?redirectToPay';
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N') {
		url = 'ChangeOfUsage.html?PrintReport';
	}
	return saveOrUpdateForm(element,
			"Your application for change Of Uses saved successfully!", url,
			'saveform');
}

function validateApplicantInfo(errorList) {
	var applicantTitle = $.trim($('#applicantTitle').val());
	var firstName = $.trim($('#firstName').val());
	var lastName = $.trim($('#lastName').val());
	var gender = $.trim($('#gender').val());
	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantAreaName = $.trim($('#areaName').val());

	var villTownCity = $.trim($('#villTownCity').val());
	var applicantPinCode = $.trim($('#pinCode').val());
	var applicantAdharNo = $.trim($('#adharNo').val());
	var dwzid1 = $.trim($('#dwzid1').val());
	var dwzid2 = $.trim($('#dwzid2').val());
	var povertyLineId = $('#povertyLineId').val();
	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	}
	if (firstName == "" || firstName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	}
	if (lastName == "" || lastName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	}

	if (applicantMobileNo == "" || applicantMobileNo == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	}
	if (applicantAreaName == "" || applicantAreaName == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantarea'));
	}
	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	}
	/*
	 * if (blockName == "" || blockName == undefined) {
	 * errorList.push(getLocalMessage('water.validation.ApplicantBlockName')); }
	 */
	if (povertyLineId == "" || povertyLineId == '0'
			|| povertyLineId == undefined) {
		errorList.push(getLocalMessage("water.validation.Applicantpovertyln"));
	} else {
		if (povertyLineId == 'Y') {
			var bplNo = $.trim($('#bplNo').val());
			if (bplNo == '' || bplNo == undefined) {
				errorList
						.push(getLocalMessage('water.validation.bplnocantempty'));
			}
		}
	}

	return errorList;
}

function resetConnection(element){
	
	$("#conNum").prop("readonly", false);
	$("#conNum").val('');

	$('#newTarrif option').prop('selected', function() {
		return this.defaultSelected;
	});

	$("#oldTarrif").hide();
	$("#consumerDetails").hide();
	$("#newType").hide();
	$("#remark").val('');
	$("#confirmToProceedId").hide();
	$("#searchConnection").prop('disabled', false);
	
}

function validateApplicantInfo(errorList) {
    var applicantTitle = $.trim($('#applicantTitle').val());
    var firstName = $.trim($('#firstName').val());
    var lastName = $.trim($('#lastName').val());
    var mobileNo = $.trim($('#mobileNo').val());
    var areaName = $.trim($('#areaName').val());
    var pinCode = $.trim($('#pinCode').val());
    var povertyLineId = $.trim($('#povertyLineId').val());

    if (applicantTitle == "" || applicantTitle == '0'
	    || applicantTitle == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
    }
    if (firstName == "" || firstName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
    }
    if (lastName == "" || lastName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
    }
    if (mobileNo == "" || mobileNo == undefined) {
	errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
    }
    if (areaName == "" || areaName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
    }

    if (pinCode == "" || pinCode == undefined) {
	errorList.push(getLocalMessage('water.validation.applicantPinCode'));
    }

    if (povertyLineId == "" || applicantTitle == '0'
	    || povertyLineId == undefined) {
	errorList.push(getLocalMessage('water.validation.Applicantpovertyln'));
    }
    return errorList;
}






