
function savePollCreation(element) {
	
	var errorList = [];	
	var question = $('#question').val();
	var text = $('#text').val();
	if (question == "" ) {
		errorList.push(getLocalMessage('Please Enter Question'));
	}
	if (text == "" ) {
		errorList.push(getLocalMessage('Please Enter At Least two Choices'));
	}
		
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		if ($("#pollCreationQuestionForm").valid() == true) {
			saveOrUpdateForm(element, "", 'PollCreation.html', 'saveform');
			showBoxForApproval(getLocalMessage("Poll Question Saved Successfully"));
		} else {
		}
		
	}
}


function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  'Proceed';
	var no = 'No';	
	message += '<p class="text-blue-2 text-center padding-15">'
		    + succesMessage +'</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="closeApproval();"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
	
	getPollCreateQuestionPage();
    $.fancybox.close();
}

function showBoxForApproval1(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  'Proceed';
	var no = 'No';	
	message += '<p class="text-blue-2 text-center padding-15">'
		    + succesMessage +'</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="closeApproval1();"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function showBoxForApproval2(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  'Proceed';
	var no = 'No';	
	message += '<p class="text-blue-2 text-center padding-15">'
		    + succesMessage +'</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="closeApproval2();"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval1() {
	
	window.location.href = 'AdminHome.html';
    $.fancybox.close();
}

function closeApproval2() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}

function getPollCreateQuestion(element) {
	var errorList = [];
	var department = $('#department').val();
	var pollName = $('#pollName').val();
	var	pollFromDate = $('#fromDate').val();
	var	pollToDate = $('#toDate').val();
	var	pollid = $('#pollid').val();
	var	mode = $('#mode').val();
	
	/*if (pollName == "") {
		errorList.push(getLocalMessage('Please Enter Poll Name'));
	}*/
	if (pollFromDate == "" && pollToDate == "" || department == "" || pollName == "") {
		errorList.push(getLocalMessage('Please Enter Valid Details'));
	} else {
		if (pollFromDate == '' && pollToDate != '') {
			errorList.push("Please Select From Date");
		}

		if (pollToDate == '' && pollFromDate != '') {
			errorList.push("Please Select To Date");
		}
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date($("#fromDate").val().replace(pattern, '$3-$2-$1'));
		var sDate = new Date($("#toDate").val().replace(pattern, '$3-$2-$1'));
		if (eDate > sDate) {
			errorList.push("To Date should not be less than From Date");
		}
	}
	
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		//var requestData = {};
		//requestData = __serializeForm(theForm);
		
		if(pollid != null){
		var requestData = {
			"department" : department,
			"pollName" : pollName,
			"pollFromDate" : pollFromDate,
			"pollToDate" : pollToDate,
			"pollid" : pollid,
			"mode" : mode
		};
	    }
		else{
			var requestData = {
				"department" : department,
				"pollName" : pollName,
				"pollFromDate" : pollFromDate,
				"pollToDate" : pollToDate
			};
		}
		var URL = 'PollCreation.html?getPollCreateQuestion';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
		}
	}
}


function getPollCreateForm(element) {
	
	var errorList = [];
	var pollid =  $('#pollid').val();

	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {"pollid":pollid};
		requestData = __serializeForm(theForm);
		var URL = 'PollCreation.html?getPollCreateForm';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
		}
	}
}


function submitPollCreation(element) {
	
	var errorList = [];
	var pollStatus = ("P");
	var question = $('#question').val();
	var text = $('#text').val();
	if (question == "" && text == "" ) {
		errorList.push(getLocalMessage('Please enter and save valid data'));
	}
	//var pollId = (25);
	
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		
		var requestData = {
			"pollStatus" : pollStatus
			//"pollId" : pollId
		};
		
		var URL = 'PollCreation.html?submitPollStatus';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			
			//showBoxForApproval1(getLocalMessage("Poll Published Successfully"));
			window.location.href = 'AdminHome.html';
			
		}
	}
}


function getPollCreateQuestionPage(element) {
	
	var errorList = [];
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {
		};
		var URL = 'PollCreation.html?getPollCreateQuestionPage';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
		}
	}
}


function savePollVeiwData(element) {
	var errorList = [];
	var choiceRadio  =  "";
	var pollQue      =  $('#pollQue').val();
	var pollid       =  $('#pollid').val();
	var pollAnsId    =  $("input[type='radio'][name='mybox']:checked").val();
	
	if(pollAnsId != undefined){
		choiceRadio = "Y";
	}
	else {
		errorList.push(getLocalMessage('Please select one option'));
	}
	
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		
		var requestData = {
			"pollid"      : pollid,
			"pollQue"     : pollQue,
			"choiceRadio" : choiceRadio,
			"pollAnsId"   : pollAnsId
		};
		
		var URL = 'PollView.html?savePollVeiwData';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			showBoxForApproval2(getLocalMessage("Your vote has been registered successfully"));
            //window.location.href = 'AdminHome.html';
			
		}
	}
}

function checkDate(errorList){
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}

/*ashish test*/




