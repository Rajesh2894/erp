$(document).ready(function() {
	$("#occuranceBookDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	fieldValidationInputGroup();
	$('#resetAllFields').on('click', function() {
		var fieldSelector = $('input, textarea');
		if (fieldSelector.hasClass('error')) {
			fieldSelector.next('label.error').css('display', 'none');
			$('.input-group').removeAttr('style');
		}
	});
});

function fieldValidationInputGroup() {
	$(document).on("focusout", ".input-group", function() {
		var validationCond1 = $(this).hasClass('has-error');
		var validationCond2 = $(this).children('label.error');
		if (validationCond1 || validationCond2) {
			$(this).css('margin-bottom', '20px');
		} else {
			$(this).removeAttr('style');
		}
		if (!validationCond2.is(':visible')) {
			$(this).removeAttr('style');
		}
	});
}

$(".datepicker").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '-0d',
	changeYear : true,
});

$(".timepicker").timepicker({

	changeMonth : true,
	changeYear : true,
	minDate : '0',
});

$("#time").timepicker({

});

$("#frmOccuranceBook").validate({
	onkeyup : function(element) {
		this.element(element);
		console.log('onkeyup fired');
	},
	onfocusout : function(element) {
		this.element(element);
		console.log('onfocusout fired');
	}
});

function SearchOccuranceBookDetail() {
	var errorList = [];
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var callType = $('#complaintType1').val();
	var callSubType = $('#complaintType2').val();
	var date = new Date();
	var add = getLocalMessage('ComplainRegisterDTO.Add');

	if (fromDate == null || fromDate == "") {
		errorList
				.push(getLocalMessage("CallDetailsReportDTO.validation.fromDate"));
	}
	if (toDate == null || toDate == "") {
		errorList
				.push(getLocalMessage("CallDetailsReportDTO.validation.toDate"));
	}

	if (errorList.length > 0) {
		checkDate(errorList);
	} else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (eDate > sDate) {
			errorList
					.push(getLocalMessage("CallDetailsReportDTO.validation.date.1"));
		}
		if (sDate >= date) {
			errorList
					.push(getLocalMessage("CallDetailsReportDTO.validation.date.2"));
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
	}
	if (errorList.length == 0) {
		if (fromDate != '' || toDate != '') {
			if (callType == undefined || callType == null || callType=='0'){
				callType = "";
			}
			if (callSubType == undefined || callSubType == null || callSubType=='0' ){
				callSubType = "";
			}
			var requestData = 'fromDate=' + fromDate + '&toDate=' + toDate + '&callType=' + callType + '&callSubType=' + callSubType;
			var table = $('#occuranceBookDataTable').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var response = __doAjaxRequest(
					"DisasterOccuranceBook.html?searchOccurranceBook", 'POST',
					requestData, false, 'json');
			var occDtos = response;
			if (occDtos.length == 0) {
				errorList
						.push(getLocalMessage("OccuranceBookDTO.validation.record.not.found"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
			$

					.each(
							occDtos,
							function(index) {
								var obj = occDtos[index];
								var cmplntNo = obj.complainNo;
								let complaintType1Desc = obj.complaintType1Desc;
								let complaintType2Desc = obj.complaintType2Desc;
								let cmplntId = obj.complainId;
								let incidentDesc = obj.complaintDescription;
								
								result
										.push([
												'<div class="text-center">'
														+ (index + 1)
														+ '</div>',
												'<div class="text-center">'
														+ cmplntNo + '</div>',
												'<div class="text-center">'
														+ complaintType1Desc
														+ '</div>',
												'<div class="text-center">'
														+ complaintType2Desc
														+ '</div>',
												'<div class="text-center">'
														+ incidentDesc
														+ '</div>',
												'<div class="text-center">'
														+ '<button type="button" class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyOccuranceBook(\''
														+ cmplntId
														+ '\',\'DisasterOccuranceBook.html\',\'occuranceBook\',\'A\',\''
														+ cmplntNo
														+ '\')"  title="' + add + '"><i class="fa fa-plus-circle padding-right-5"></i></button>'
														+ '</div>' ]);

							});
			table.rows.add(result);
			table.draw();
		}
	} else {

		displayErrorsOnPage(errorList);
	}
}

function confirmToProceed(element) {

	var errorList = [];
	var date = $("#date").val();
	var time = $("#time").val();
	var incidentDesc = $("#incidentDesc").val();

	if (date == null || date == "") {
		errorList
				.push(getLocalMessage("disaster.occurrencedate.val"));
	}
	if (time == null || time == "") {
		errorList
				.push(getLocalMessage("disaster.occurrencetime.val"));
	}
	if (incidentDesc == null || incidentDesc == "") {
		errorList
				.push(getLocalMessage("disaster.occurrencediscription.val"));
	}

	validateDate(errorList);

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}

	else {

		showConfirmBoxSave(element);

	}

}

function showConfirmBoxSave(element) {
    saveObj = element;
	var saveorAproveMsg = getLocalMessage("disasteroccuranceBook.save");
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("account.btn.save.yes");
	var no = getLocalMessage("account.btn.save.no");

	message += '<h4 class=\"text-center text-blue-2\">' + "" + saveorAproveMsg
			+ "" + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>  '
			+ '<input type=\'button\' value=\''
			+ cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="saveDataAndShowSuccessMsg(\'Y\')"/>   '   
			+ '<input type=\'button\' value=\''
			+ no
			+ '\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '
			+ ' onclick="saveDataAndShowSuccessMsg(\'N\')"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsg(savestatus) {
   saveOrUpdateForm(saveObj, "", 'DisasterOccuranceBook.html','saveform');
   if(savestatus=="N"){
	   window.location.href = 'DisasterOccuranceBook.html';
   }
   else if(savestatus=="Y"){
	   var cmplntId= $("#id").val();
	   var enterComplaintNumber= $("#cmplntNo").val();
	   openFormOcc(cmplntId, 'DisasterOccuranceBook.html', 'occuranceBook', "A", enterComplaintNumber);
	   $.fancybox.close();   
   }
 
  
}


function modifyOccuranceBook(cmplntId, formUrl, actionParam, mode, cmplntNo) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : cmplntId,
		"cmplntNo" : cmplntNo
	};

	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function openFormOcc(cmplntId, formUrl, actionParam, mode, cmplntNo) {

	var divName = '.content-page';
	/*var cmplntNo = $("#cmplntNo").val();*/
	var requestData = {
		"mode" : mode,
		"id" : cmplntId,
		"cmplntNo" : cmplntNo

	};

	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function checkDate(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}

function validateDate(errorList) {

	var odate = $("#date").val();
	var otime = $("#time").val();
	var otime = $("#time").val();
	var date = new Date();
	var time = date.getHours() + ":" + date.getMinutes() + ":"
			+ date.getSeconds();
	var url = "DisasterOccuranceBook.html?validateDate";
	var requestData = "&cmplntId=" + $('#occcId').val()
	var returnData = __doAjaxRequest(url, 'post', requestData, false, 'json');
	var comp = returnData.split(',');
	var oDate = moment(odate, "DD.MM.YYYY").toDate();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var comDate = new Date(comp[0].replace(pattern, '$3-$2-$1'));
	
	 var comTime = formatTime(comDate.getHours()) + ":" + formatTime(comDate.getMinutes()) + ":" + formatTime(comDate.getSeconds());
	comDate.setHours(0, 0, 0, 0);
	oDate.setHours(0, 0, 0, 0);
	if (comDate > oDate) {
		errorList.push(getLocalMessage("OccuranceBookDTO.DateValid"));
	}

		if (
				comDate.getFullYear() === oDate.getFullYear() &&
				comDate.getMonth() === oDate.getMonth() &&
				comDate.getDate() === oDate.getDate()
			){
		if (comTime > otime) {
			errorList.push(getLocalMessage("OccuranceBookDTO.TimeValid"));
		}
	}
	

	if (oDate > date) {
		errorList.push(getLocalMessage("OccuranceBookDTO.occTimeAndDateValid"));
	}
	if (oDate.getDate == date) {
		if (otime > time) {
			errorList
					.push(getLocalMessage("Occurance Time should be less than Current Time"));
		}
	}
}

function formatTime(timeValue) {
    return timeValue < 10 ? '0' + timeValue : timeValue.toString();
}
