$(document).ready(function() {
	$("#hearingTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	
	$("#attendees").keypress(
			  function(event){
			    if (event.which == '13') {
			      event.preventDefault();
			    }
		});
	
	var minYear= $("#cseDate").val();

	
	$('.lessthancurrdate2').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: minYear,
	
	});	
	
	var dateFields = $('.date');
	dateFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});		
	
});
function getData(id, mode, url) {
	var data = {
		"id" : id,
		"mode" : mode
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(url, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function addData() {

	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateHearingDate(errorList);
	if (errorList.length == 0) {

		var id = '#hrStatus' + ($('#hearingDateTable tbody tr').length - 1)
		if ($(id).find(":selected").attr('code') == "SH") {
			errorList.push($("#validStatus").val());
		}
		if ($(id).find(":selected").attr('code') == "CL") {
			errorList.push($("#validClose").val());
		}

		if (errorList.length == 0) {
			// addTableRow('hearingDateTable');

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading("HearingDate.html?ADD", {},
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareDateTag();
			/*
			 * var id = '#hrStatus'+($('#hearingDateTable tbody tr').length-1)
			 * $(id).val($('select
			 * option[code="SH"]').val()).attr("disabled",true);
			 */
		}
	}

	if (errorList.length > 0) {
		$('#hearingDateTable').DataTable();
		displayErrorsOnPage(errorList);

	}
	return false;
}

function deleteData(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('hearingDateTable', obj, ids);
	$('#hearingDateTable').DataTable().destroy();
	triggerTable();
}
function validateHearingDate(errorList) {

	
	var caseEntryList = [];
	
	var cseDate= $("#cseDate").val();

	var CD =  moment(cseDate, "DD.MM.YYYY HH.mm").toDate();
	
	var hrD = $("#hrDate0").val();
	
	var HD =  moment(hrD, "DD.MM.YYYY HH.mm").toDate();
	
	if ((HD.getTime())<(CD.getTime())) {
		errorList.push("please select hearing date greater than case registration date");
	}

	$(".hearingClass").each(
			function(i) {

				
				var hrDate = $("#hrDate" + i).val();
				var hrStatus = $("#hrStatus" + i).val();
				var hrPreparation = $("#hrPreparation" + i).val();

				var rowCount = i + 1;
              
				 if (hrDate == "" || hrDate == null || hrDate == 'undefined' ) {
					errorList.push($("#validHrDate").val() + rowCount);
				   }
				
				else {

					if (rowCount > 1) {
						var prevHrDate = $("#hrDate" + (i - 1)).val();
						
						var hd = moment(hrDate, "DD.MM.YYYY HH.mm").toDate();
						
						var phd=  moment(prevHrDate, "DD.MM.YYYY HH.mm").toDate();
						
						
						if (hd.getTime() <= phd.getTime()) {
							errorList.push($("#validHearingDate").val());
						}
					}
				}
				if (hrStatus == "0" || hrStatus == null
						|| hrStatus == 'undefined') {
					errorList.push($("#validHrStatus").val() + rowCount);
				}
				if (hrPreparation == "" || hrPreparation == null
						|| hrPreparation == 'undefined') {
					errorList.push($("#validHrPreparation").val() + rowCount);
				}

			});
	return errorList;
}

//this function called from hearingDateForm.jsp page 
function saveData(element) {
	var errorList = [];
	errorList = validateHearingDate(errorList);
	if (errorList.length == 0) {
			return saveOrUpdateForm(element, 'Hearing Date save sucessfully',
					'HearingDate.html', 'saveform');
	}
	if (errorList.length > 0) {
		$('#hearingDateTable').DataTable();
		displayErrorsOnPage(errorList);
	}
	return false;
}

//this function called from hearingDetailsForm.jsp page
function saveHearingData(element) {
	var errorList = [];
	errorList = validateHearingDetails(errorList);
	errorList = validateAttendeeDetails(errorList);
	errorList = validateComntDetails(errorList);
	if (errorList.length == 0) {
		errorList = checkDuplicateValueOfHearingAttendee(errorList);
		if(errorList.length == 0){
			return saveOrUpdateForm(element, 'Hearing Details save sucessfully',
					'HearingDetails.html', 'saveform');
		}else{
			$('#hearingAttendeeTable').DataTable();
			displayErrorsOnPage(errorList);
		}
			
	}
	if (errorList.length > 0) {
		$('#hearingDateTable').DataTable();
		displayErrorsOnPage(errorList);
	}
	return false;
}

function validateHearingDetails(errorList) {
	var judgeId = $("#judgeId").val();
	var advId = $("#advId").val();
	var hrStatus = $("#hrStatus").val();
	var attendees = $("#attendees").val();
	var proceeding = $("#proceeding").val();

	if (judgeId == "" || judgeId == null || judgeId == 'undefined') {
		errorList.push($("#validJudgeId").val());
	}
	if (advId == "" || advId == null || advId == 'undefined') {
		errorList.push($("#validAdvId").val());
	}
	if (hrStatus == "" || hrStatus == null || hrStatus == 'undefined') {
		errorList.push($("#validHrStatus").val());
	}
	
	if (proceeding == "" || proceeding == null || proceeding == 'undefined') {
		errorList.push($("#validProceeding").val());
	}
	if(proceeding != "0" || proceeding != undefined
			|| proceeding != "")
	{
		
		if(proceeding.length  > 1000)
		{
		errorList.push("Maximum Limit Reached For proceeding Field");
		}
	}
	
	
	
	return errorList;
}

// Hearing Attendee details Code added By ISRAT START
function addAttendeeData(event) {
	event.preventDefault();
	var errorList = [];
	$("#errorDiv").hide();
	
	errorList = validateAttendeeDetails(errorList);
	if (errorList.length == 0) {
		errorList = checkDuplicateValueOfHearingAttendee(errorList);
		if(errorList.length == 0){
			addTableRow('hearingAttendeeTable');
			var hrId = $('#hrIdCR').val();
			$('#hearingAttendeeTable tr:last').find('input[type=hidden]').eq(1).val(hrId);
			//$('#hearingAttendeeTable').DataTable();
			hasCharacter();
	} else {
		$('#hearingAttendeeTable').DataTable();
		displayErrorsOnPage(errorList);
	}
	}
}

function deleteAttendeeRow(obj, ids, event) {
	event.preventDefault();
	var totalWeight = 0;
	deleteTableRow('hearingAttendeeTable', obj, ids);
	$('#hearingAttendeeTable').DataTable().destroy();
	triggerTable();
}

function validateAttendeeDetails(errorList) {
	
	
	$("#hearingAttendeeTable tbody tr")
			.each(
					function(i) {
						let hraName = $("#hraName" + i).val();
						let hraDesignation = $("#hraDesignation" + i).val();
						let hraPhoneNo = $("#hraPhoneNo" + i).val();
						let hraEmailId = $("#hraEmailId" + i).val();
						var rowCount = i + 1;
						
						if(i>0)
							{
						 hraName = $("#hraName" + (i-1)).val();
						if (hraName == undefined || hraName == "") {
							errorList
									.push(getLocalMessage('lgl.validation.hearing.personal.name ')
											+ i);
						
						}
							}
						/*	if(hraName!= undefined || hraName !="")
						{
							if(hraName.length > 50)
								{
									errorList.push(getLocalMessage("Maximum limit reached in Name at row: "+ rowCount +" (Maximum 50 characters allowed)"));
								}
						}
						if (hraDesignation == undefined || hraDesignation == "") {
							errorList
									.push(getLocalMessage('lgl.validation.hearing.personal.designation ')
											+ rowCount);
						}
						if(hraDesignation!= undefined || hraDesignation !="")
						{
							if(hraDesignation.length > 50)
								{
									errorList.push(getLocalMessage("Maximum limit reached in Designation at row: "+ rowCount+" (Maximum 50 characters allowed)"));
								}
						}

						
						if (hraPhoneNo == undefined || hraPhoneNo == "") {
							errorList
									.push(getLocalMessage('lgl.validation.hearing.personal.phoneNo ')
											+ rowCount);
						}
						if (hraEmailId == undefined || hraEmailId == "") {
							errorList
									.push(getLocalMessage('lgl.validation.hearing.personal.emailId ')
											+ rowCount);
						}*/

						if (hraEmailId.trim() != "") {
							let emailRegex = new RegExp(
									/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
							let valid = emailRegex.test(hraEmailId);
							if (!valid) {
								errorList
										.push(getLocalMessage('lgl.validation.emailId'));
							}
						}
					});
	return errorList;
}

//Comnt&Revw details Code added By Akshay START
function addComntData(event) {
	event.preventDefault();
	var errorList = [];
	$("#errorDiv").hide();
	errorList = validateComntDetails(errorList);
	if (errorList.length == 0) {
			addTableRow('hearingComntTable');
			var hrDate = $('#hrDateCR').val();	
			var mydate = new Date(hrDate);
			var hrId = $('#hrIdCR').val();
			$('#hearingComntTable tr:last td').eq(3).find("input[type='text']").val(moment(mydate).format('DD/MM/YYYY'));
			$('#hearingComntTable tr:last').find('input[type=hidden]').eq(1).val(hrId);
	} else {
		$('#hearingComntTable').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function deleteComntRow(obj, ids) {
	var rowCount = $('#hearingComntTable >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("lgl.validation.hearing.delete.entry"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	deleteTableRow('hearingComntTable', obj, ids);
	$('#hearingComntTable').DataTable().destroy();
	triggerTable();
}

function validateComntDetails(errorList) {
		$("#hearingComntTable tbody tr")
			.each(
					function(i) {
						var rowCount = i + 1;
						var	comnt = $("#comnt" + (i)).val();
						var	revw = $("#revw" + (i)).val();
							if (comnt == undefined || comnt == "" && revw == undefined || revw == "") {
								errorList.push(getLocalMessage('lgl.validation.hearing.comntrevw ')+ rowCount);
							
						}
					});
	return errorList;
}

function checkDuplicateValueOfHearingAttendee(errorList) {
	var name = [], phoneNo = [], emailId = [];
	$("#hearingAttendeeTable tbody tr")
			.each(
					function(i) {
						let hraName = $.trim($("#hraName" + i).val());
						let hraPhoneNo = $.trim($("#hraPhoneNo" + i).val());
						let hraEmailId = $.trim($("#hraEmailId" + i).val());
						var rowCount = (i + 1);
					/*	if (name.includes(hraName)) {
							errorList.push(getLocalMessage("lgl.validation.duplicate.name ")+ rowCount);
						}else{
							name.push(hraName);
						}*/
						
						if(hraPhoneNo!='')
							{
						if (phoneNo.includes(hraPhoneNo)) {
							errorList.push(getLocalMessage("lgl.validation.duplicate.phone ")+ rowCount);
						}else{
							phoneNo.push(hraPhoneNo);	
						}
							}
						if(hraEmailId!='')
						{
						if (emailId.includes(hraEmailId)) {
							errorList.push(getLocalMessage("lgl.validation.duplicate.emailId ")+ rowCount);
						}else{
							emailId.push(hraEmailId);	
						}
						}
					});
	return errorList;
}

// Hearing Attendee details Code added By ISRAT END




//Adding new Judge Information in Case Hearing Details. Code added By Rahul S Chaubey : START
function addJudge(formUrl, actionParam, caseId) {
	var requestdata = {
				"id" : caseId
			};

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		
		
}


//Adding new Judge Information in Master Code added By Rahul S Chaubey : END

function showBoxForApproval(succesMessage,caseId) {
	    var childDivName = '.msg-dialog-box';
	    var message = '';
	    var Proceed = getLocalMessage("proceed");
	    var no = 'No';
	    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
		    + '</p>';

	   
	    var mode = 'E';
		var url = 'HearingDetails.html?EDIT';
		message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' '
			+ ' onclick="getData(\'' + caseId + '\',\'' + mode
			+ '\',\'' + url + '\');"/>' + '</div>';

	    

	    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
	    $(childDivName).html(message);
	    $(childDivName).show();
	    $('#Proceed').focus();
	    showModalBoxWithoutClose(childDivName);
	}

$('body').on('focus', ".hasMobileNo", function() {
	$('.hasMobileNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
		$(this).attr('maxlength', '10');
	});
});
$('body').on('focus', ".hasNameClass", function() {
	$('.hasNameClass').keyup(function() {
		 this.value = this.value.replace(/[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~0-9]/g, '');
	});
});	
jQuery('.hasCharacterAndComma').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z,]/g,'');
   
});


//120649
function reset(resetBtn) {
	var caseId = $('#caseId').val();
	getData(caseId,'E','HearingDetails.html?EDIT');
}

//145024
function resetHearing() {
var caseId = $('#caseHearingId').val();
getData(caseId,'E','HearingDetails.html?EDIT');
}

function resetHearingDate() {
	var caseId = $('#hrIdCR').val();
	getData(caseId,'HE','HearingDetails.html?HearingDate');
	}

function resetHearingForm()
{
	
	$('input[type=text]').val('');  
	$(".alert-danger").hide();
	$("#hearingDateForm").validate().resetForm();
}

