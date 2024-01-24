/*$(document).ready(function() {

	$("#disasterDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function confirmToProceed(element) {


	var errorList = [];
	var status = [];
	var remark = $("#remark").val();
	var status = $("#statusFlag").val();

	if (status == 'P') {
		errorList.push(getLocalMessage("Application is still pending"));
	}

	if (remark == null || remark == "") {
		errorList.push(getLocalMessage("Please Enter Remarks"));
	}

	if (errorList.length > 0) {
		// displayErrorsOnPage(errorList);
		// return false;
		checkDate(errorList);
	} else {
		return saveOrUpdateForm(element, "", 'InjuryDetails.html', 'saveform');
	}
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

function searchDisasterData() {
	
	var errorList = [];
	var complaintType1 = $('#complaintType1').val();
	var complaintType2 = $('#complaintType2').val();
	var location = $('#location').val();
	var complainNo = $('#complainNo').val();
	var URL = "InjuryDetails.html?searchInjSummary";

	if (complaintType1 != 0 || complaintType2 != 0 || location != 0
			|| complainNo != "") {
		var requestData = 'complaintType1=' + complaintType1
				+ '&complaintType2=' + complaintType2 + '&location=' + location
				+ '&complainNo=' + complainNo;
		var table = $('#disasterDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');

		var injurySummaryDTOList = response;
		if (injurySummaryDTOList.length == 0) {
			errorList.push("Records not Found");
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						injurySummaryDTOList,
						function(index) {
							var obj = injurySummaryDTOList[index];
							let complainNo = obj.complainNo;
							let codDesc = obj.codDesc;
							let codDesc1 = obj.codDesc1;
							let complaintDescription = obj.complaintDescription;
							let complaintStatus = obj.complaintStatus;
							if (complaintStatus == null) {
								complaintStatus = "";
							} else if (complaintStatus = 'A') {
								complaintStatus = "Open";
							}

							let complainId = obj.complainId;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ complainNo + '</div>',
											'<div class="text-center">'
													+ codDesc + '</div>',
											'<div class="text-center">'
													+ codDesc1 + '</div>',
											'<div class="text-center">'
													+ complaintDescription
													+ '</div>',
											'<div class="text-center">'
													+ complaintStatus
													+ '</div>',
											'<div class="text-center">'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyInjury(\''
													+ complainId
													+ '\',\'InjuryDetails.html\',\'editDisInjury\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList
				.push(getLocalMessage("Please Enter at Least one search criteria"));
		displayErrorsOnPage(errorList);
	}

}

$(document).ready(function() {

	$("#callScrutinyDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function modifyInjury(complainId, formUrl, actionParam, mode) {
	
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : complainId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function displayErrorsOnPage(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}*/


$(document).ready(function() {

	$("#disasterDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	resetFormFields(); //Defect #152220
});

//Defect #152220
function resetFormFields() {
	$('#resetBtn').on('click', function() {
		$('input[type=text], textarea, select').each(function() {
			var errorField = $(this).next().hasClass('error');
			if('input[type=text], textarea') {
				$(this).val('');
			}
			if('select') {
				if($(this).val() != '') {
					$(this).val('0');
				} else {
					$(this).val('');
				}
			}
			$(this).parent().removeClass('has-error');
			if (errorField == true) {
				$(this).next().remove();
			}
        });
	});
}

function totalDeathCount(element)
{
	var noOfDeathMale=$('#noOfDeathMale').val();
	var noOfDeathFemale=$('#noOfDeathFemale').val();
	var noOfDeathChild=$('#noOfDeathChild').val();
	var totalDeathCount=+noOfDeathMale + +noOfDeathFemale + +noOfDeathChild;
	$("#totalDeath").val(totalDeathCount);
}

function totalInjurCount(element)
{
	var noOfInjuryMale=$('#noOfInjuryMale').val();
	var noOfInjuryFemale=$('#noOfInjuryFemale').val();
	var noOfInjuryChild=$('#noOfInjuryChild').val();
	var totalDeathCount=+noOfInjuryMale + +noOfInjuryFemale + +noOfInjuryChild;
	$("#totalInjured").val(totalDeathCount);
	}


function confirmToProceed(element) {
	
	var errorList = [];
	var status =[];
	var remark = $("#remark").val();
	var status = $("#complainStatus").val();
	var statusVariable = $("#statusVariable").val();
	
	//Defect #157993
	if(statusVariable == 'Pending') {
		errorList.push(getLocalMessage("complainRegisterDTO.validation.status.variable.pending"));
	}
	
	if(status == 'P') {
		errorList.push(getLocalMessage("complainRegisterDTO.validation.status.p"));
	}
	
	if (remark == null || remark == "") {
		errorList.push(getLocalMessage("complainRegisterDTO.validation.remark"));
	}
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	else {
		return saveOrUpdateForm(element, "", 'InjuryDetails.html',
				'saveform');
	}
}

function searchDisasterData() {
	
	var errorList = [];
	var complaintType1=$('#complaintType1').val();
	if (complaintType1 == undefined || complaintType1 == null || complaintType1=='0'){
		complaintType1 = "";
	}
	var complaintType2=$('#complaintType2').val();
	if (complaintType2 == undefined || complaintType2 == null || complaintType2=='0'){
		complaintType2 = "";
	}
	var location = $('#location').val();
	if (location == undefined || location == null || location=='0'){
		location = "";
	}
	var complainNo = $('#complainNo').val();
	var srutinyStatus = $('#srutinyStatus').val();
	var URL = "InjuryDetails.html?searchInjSummary";
	
	if (complaintType1 != 0 || complaintType2 != 0 || location != 0 || complainNo != "" || srutinyStatus != "" ) {
		var requestData = 'complaintType1=' + complaintType1 + '&complaintType2=' + complaintType2+'&location=' +location+'&complainNo=' + complainNo +'&srutinyStatus=' + srutinyStatus;
		var table = $('#disasterDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
		
		var injurySummaryDTOList = response;
		if (injurySummaryDTOList.length == 0) 
		{
			errorList.push("Records not Found");
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$.each(
				injurySummaryDTOList,
						function(index) {
							var obj = injurySummaryDTOList[index];
							let complainNo = obj.complainNo;
							let codDesc= obj.codDesc;
							let codDesc1 = obj.codDesc1;
							let complaintDescription = obj.complaintDescription;
							let complaintStatus = obj.complainStatus ;
							
								
							let complainId=obj.complainId;
							 var isRejected = (complaintStatus.toLowerCase() === "rejected");
							
							result.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ complainNo + '</div>',
											'<div class="text-center">'
													+ codDesc + '</div>',
											'<div class="text-center">'
													+ codDesc1 + '</div>',
											'<div class="text-center">'
													+ complaintDescription + '</div>',
											'<div class="text-center">'
													+ complaintStatus + '</div>',
											
													'<div class="text-center">' +
											        (isRejected
											            ? '<button type="button" class="btn btn-warning btn-sm" disabled title="Edit"><i class="fa fa-pencil"></i></button>'
											            : '<button type="button" class="btn btn-warning btn-sm" onclick="modifyInjury(\'' + complainId + '\',\'InjuryDetails.html\',\'editDisInjury\',\'E\')" title="Edit"><i class="fa fa-pencil"></i></button>'
											        ) +
											        '</div>'

													
													
													
											/*'<div class="text-center">'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyInjury(\''
													+ complainId
													+ '\',\'InjuryDetails.html\',\'editDisInjury\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>'*/ ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList.push(getLocalMessage("complainRegisterDTO.summary.validation.search.criteria")); //Defect #157990
		displayErrorsOnPage(errorList);
	}

}


$(document).ready(function() {

	$("#callScrutinyDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});



function modifyInjury(complainId,formUrl, actionParam, mode) {
	
		var divName = '.content-page';
		var requestData = {
			"mode" : mode,
			"id" : complainId
		};
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,'html', false);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);

	}


function displayErrorsOnPage(errorList)
{
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}


function resetInjury(resetBtn){
	resetForm(resetBtn);
}
