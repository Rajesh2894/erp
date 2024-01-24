$(document).ready(function() {
	 var judgementMasterDate = $('#judgementMasterDate').val();
	$(".datepicker").datepicker({
		// minDate :'0d' ,
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		//minDate : judgementMasterDate + '1d'
	});
	
	
	$('#judgementImplementation').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$("#id_caseEntryTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	var cseTypId = $("#cseTypId").val();
	if (cseTypId == 42208) {
		$("#judgefeeTable").show();
	} else {
		$("#judgefeeTable").hide();
	}

	prepareTags();
});

function displayJudgeFeesTable() {
	var cseTypId = $("#cseTypId").val();
	if (cseTypId == 42208) {
		$("#judgefeeTable").show();
	} else {
		$("#judgefeeTable").hide();
	}
}

function editJudgeImplementation(cseId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	if(cseId =="")
		{
			var cseId =$("#cseId").val();
		}
	var requestData = {
		"mode" : mode,
		"id" : cseId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchCase() {
	
	var errorList = [];
	errorList = validateSearch(errorList);
	if(errorList.length > 0)
	{
		displayErrorsOnPage(errorList);
	}
	else
	{
		var data = {
				"cseSuitNo" : $("#cseSuitNo").val(),
				"cseDeptid" : $("#cseDeptid").val(),
				"cseDate" : $("#cseDate").val(),
				"cseCatId1" : replaceZero($("#cseCatId1").val()),
				"cseCatId2" : replaceZero($("#cseCatId2").val()),
				"cseTypId" : replaceZero($("#cseTypId").val()),
				"crtId" : replaceZero($("#crtId").val()),

			};
			var divName = '.content-page';
			var formUrl = "JudgementImplementation.html?searchCaseEntry";
			var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
	}
}

function validateSearch(errorList){
	
	var errorList = [];
	var cseSuitNo = $('#cseSuitNo').val();
	var cseDeptid = $('#cseDeptid').val();
	var cseCatId = $('#cseCatId').val();
	var cseDate = $('#cseDate').val();
	var crtId = $('#crtId').val();
	var cseTypId = $('#cseTypId').val();
	
	 if ((cseSuitNo == undefined || cseSuitNo == '0' || cseSuitNo == "") && (cseDeptid == undefined || cseDeptid == null || cseDeptid == "")
			 && (cseCatId == undefined || cseCatId == null || cseCatId == "")  && (cseDate == undefined || cseDate == null || cseDate == "")
			  && (crtId == undefined || crtId == null || crtId == "")  && (cseTypId == undefined || cseTypId == null || cseTypId == "0")) 
	 	{
			errorList.push('Please provide at least one search criteria.');
		}
		  return errorList;
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}

function Proceed(element) {
	var errorList = [];
	validatejudgeImplementationForm(errorList);
	errorList = validateAttendeeDetails(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, 'Judge Details saved successfully',
				'JudgementImplementation.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}

}

function addAttendeeData(event) {
	event.preventDefault();
	var errorList = [];
	$("#errorDiv").hide();
	errorList = validateAttendeeDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('attendeeDetailsTable');
		$('#attendeeDetailsTable').DataTable();
	} else {
		$('#attendeeDetailsTable').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function deleteAttendeeRow(obj, ids, event) {
	event.preventDefault();
	var totalWeight = 0;
	deleteTableRow('attendeeDetailsTable', obj, ids);
	$('#attendeeDetailsTable').DataTable().destroy();
	triggerTable();
}

function fetchcaseHearingList() {
	var requestData = {
		'caseId' : $('#cseId').val(),
	};
	var url = "JudgementImplementation.html?viewHearingHistory";
	$
			.ajax({
				url : url,
				data : requestData,
				type : 'POST',
				async : false,
				dataType : '',
				success : function(response) {
					if (response != "") {
						var divName = '.child-popup-dialog';

						$(divName).removeClass('ajaxloader');
						$(divName).html(response);
						//prepareTags();
						$(divName).removeClass('fancybox-close');
						 $.fancybox.close();
					//	showModalBoxWithoutClose(divName);
							showMsgModalBox(divName);
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList
							.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
}

function showMsgModalBox(childDialog) {
	
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic', // 'elastic', 'fade' or 'none'
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



function validatejudgeImplementationForm(errorList) {
	var implementerName = $('#implementerName').val(); 
	console.log(implementerName)
	var implementerPhoneNo = $('#implementerPhoneNo').val();
	var desigOfImplementer = $('#desigOfImplementer').val();
	var impleStatus = $('#impleStatus').val();

	var implementerEmail = $('#implementerEmail').val();
	var cjdActiontaken = $('#cjdActiontaken').val();
	var implementationStartDate = $('#implementationStartDate').val();
	var implementationEndDate = $('#implementationEndDate').val();
	
	 var judgementMasterDate = $('#judgementMasterDate').val();
	
	 $('#implementerName').val().trimLeft();
	 $('#desigOfImplementer').val().trimLeft();
	 $('#impleStatus').val().trimLeft();
	 $('#cjdActiontaken').val().trimLeft();
	 $('#impleStatus').val().trimLeft();
	
	
	
	if (implementerName == "" || implementerName == undefined
			|| implementerName == null) {
		errorList.push(getLocalMessage('lgl.validate.implementerName'));
	}

	if (desigOfImplementer == "" || desigOfImplementer == undefined
			|| desigOfImplementer == null) {
		errorList.push(getLocalMessage('lgl.judgeImplementation.validation.designation'));
	}
	
	if (impleStatus == "" || impleStatus == undefined
			|| impleStatus == null) {
		errorList.push(getLocalMessage('lgl.judgeImplementation.validation.status'));
	}

	if (implementerPhoneNo == "" || implementerPhoneNo == undefined
			|| implementerPhoneNo == null) {
		errorList.push(getLocalMessage('lgl.validate.implementerPhoneNo'));
	}
	else{
		if (!validateMobile(implementerPhoneNo)) {
			errorList
				.push(getLocalMessage('lgl.validate.invalid.implementerPhoneNo'));
			}
	}
	if (implementerEmail == "" || implementerEmail == undefined
			|| implementerEmail == null) {
		errorList.push(getLocalMessage('lgl.validate.implementerEmail'));
	}
	if (cjdActiontaken == "" || cjdActiontaken == undefined
			|| cjdActiontaken == null) {
		errorList.push(getLocalMessage('lgl.validate.cjdActiontaken'));
	}
	
	if(cjdActiontaken != "0" || cjdActiontaken != undefined
			|| cjdActiontaken != "")
	{
		
		if(cjdActiontaken.length  >= 1000)
		{
		errorList.push(getLocalMessage('lgl.validation.cjdActiontaken'));
		}
	}
	
	if (implementationStartDate == "" || implementationStartDate == undefined
			|| implementationStartDate == null) {
		errorList.push(getLocalMessage('lgl.validate.startDate'));
	}

	if (implementationEndDate == "" || implementationEndDate == undefined
			|| implementationEndDate == null) {
		errorList.push(getLocalMessage('lgl.validate.endDate'));
	}
	
	var startDate = moment(implementationStartDate, "DD.MM.YYYY HH.mm").toDate();
	
	var endDate=  moment(implementationEndDate, "DD.MM.YYYY HH.mm").toDate();
	
	var judDate =  moment(judgementMasterDate, "DD.MM.YYYY").toDate();
	
	if (endDate.getTime() <= startDate.getTime()) {
		errorList.push(getLocalMessage("lgl.validate.implementation.endDate"));
	}
	
	if (implementerEmail != "") {
		var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
		var valid = emailRegex.test(implementerEmail);
		if (!valid) {
			errorList.push(getLocalMessage("Invalid.Email"));
		}
	}

	/*if(moment(startDate).isAfter(judDate)== false)
	{
		errorList.push(getLocalMessage("Implementation Start Date must be greater than :"+judgementMasterDate.split(' ')[0]));
	}*/
	return errorList;
}

function validateAttendeeDetails(errorList) {
	$("#attendeeDetailsTable tbody tr")
			.each(
					function(i) {
						var attendeeName = $("#attendeeName" + i).val();
					
						var attendeeAddress = $("#attendeeAddress" + i).val();
						
						var rowCount = i + 1;

						if (attendeeName == "0" || attendeeName == undefined
								|| attendeeName == "") {
							errorList
									.push(getLocalMessage("lgl.validate.attendeeName")
											+ rowCount);
						}
						if(attendeeName != "0" || attendeeName != undefined
								|| attendeeName != "")
						{
							if(attendeeName.length  > 50)
								{
								errorList.push(getLocalMessage("Maximum Limit Reached For Attendee Name in row ")
										+ rowCount + " (Maximum 50 Charachters allowed)");
								}
						}
						if (attendeeAddress == "" || attendeeAddress == '0'
								|| attendeeAddress == undefined) {
							errorList
									.push(getLocalMessage("lgl.validate.attendeeAddress")
											+ rowCount);
						}
						
						if(attendeeAddress != "0" || attendeeAddress != undefined
								|| attendeeAddress != "")
						{
							if(attendeeAddress.length  > 1000)
							{
							errorList.push(getLocalMessage("Maximum Limit Reached For Attendee Address in row ")
									+ rowCount + " (Maximum 1000 Charachters allowed)");
							}
						}
					});
	return errorList;
}
function removeSpace(obj, val)
{
	var ID = $(obj).attr("id");
	var text = val.trimLeft();
	if(ID == "implementerName")
	{
		 $('#implementerName').val(text)
	}
	else if(ID == "desigOfImplementer")
	{
		$('#desigOfImplementer').val(text);
	}
	else if(ID == "impleStatus")
	{
		 $('#impleStatus').val(text)
	}
	else
	{
		$('#cjdActiontaken').val(text);
	}
	
}

//120649
function reset(resetBtn) {
	var cseId = $('#cseId').val();
	editJudgeImplementation(cseId,'JudgementImplementation.html', 'EDIT','E');
}

function resetCaseEntry() {
$("#postMethodForm").prop('action', '');
$("#postMethodForm").prop('action', 'JudgementImplementation.html');
$("#postMethodForm").submit();
}

function validateMobile(implementerPhoneNo) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(implementerPhoneNo);
}
