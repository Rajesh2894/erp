$(document).ready(function() {
	prepareDateTag();
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
		timeFormat : "HH:mm"
	});

	$("#fromTime").timepicker({

	});

	$("#toTime").timepicker({

	});
	
	$("#frmShiftMasterTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

$('#fromTime').on('keydown', function(e){
	e.preventDefault();
});

$('#toTime').on('keydown', function(e){
	e.preventDefault();
});

function getShiftdata(id, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function addShiftMaster(formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}
function confirmToProceed(obj, mode) {
	var errorList = [];
	errorList = validateShift(obj);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(obj, "", 'ShiftMaster.html', 'saveform');
	}
}

function showBoxForApproval(succesMessage){
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  'Ok';
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
	window.location.href = 'ShiftMaster.html';
    $.fancybox.close();
}

function validateShift(obj) {
	var errorList = [];
	var shiftId = $('#shiftId').val();
	var shiftDesc = $('#shiftDesc').val();
	var fromTime = $('#fromTime').val();
	var toTime = $('#toTime').val();
	var isCrossDayShift = $('input[name="shiftMasterDTO.isCrossDayShift"]:checked').val();
	var isGeneralShift = $('input[name="shiftMasterDTO.isGeneralShift"]:checked').val();
	var status = $('input[name="shiftMasterDTO.status"]:checked').val();

	if (shiftId == 0 || shiftId == null) {
		errorList.push(getLocalMessage("ShiftMasterDTO.validation.shiftId"));
	}
	if (shiftDesc == "" || shiftDesc == null) {
		errorList.push(getLocalMessage("ShiftMasterDTO.validation.shiftDesc"));
	}
	if (fromTime == "" || fromTime == null) {
		errorList.push(getLocalMessage("ShiftMasterDTO.validation.fromTime"));
	}
	if (toTime == "" || toTime == null) {
		errorList.push(getLocalMessage("ShiftMasterDTO.validation.toTime"));
	}
	if (isCrossDayShift != "Y") {
		if (fromTime > toTime) {
			errorList
					.push(getLocalMessage("securityManagement.fromDate.notGreater.toDate"));
		}
	}
	return errorList;
}

function SearchShift() {

	var errorList = [];
	var shiftId = $('#shiftId').val();

	if (shiftId != 0) {
		var requestData = 'shiftId=' + shiftId;
		var table = $('#frmShiftMasterTbl').DataTable();
		var URL = "ShiftMaster.html?searchShift";
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
		var shiftList = response;
		if (shiftList.length == 0) {
			errorList
					.push(getLocalMessage("ShiftMasterDTO.validation.noRecord"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						shiftList,
						function(index) {
						
							var obj = shiftList[index];
							let shiftMasId = obj.shiftMasId;
							let shiftIdDesc = obj.shiftIdDesc;
							let fromTime = obj.fromTime;
							let toTime = obj.toTime;
							let isCrossDayShift = obj.isCrossDayShift;
							let isGeneralShift = obj.isGeneralShift;
							let status = obj.status;
							if(status=='A')
							{
					result.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ shiftIdDesc + '</div>',
											'<div class="text-center">'
													+ fromTime + '</div>',
											'<div class="text-center">'
													+ toTime + '</div>',
											'<div class="text-center">'
													+ isCrossDayShift
													+ '</div>',
											'<div class="text-center">'
													+ isGeneralShift + '</div>',
											'<div class="text-center">'+
													'<c:choose>'+
													'<c:when test="${'+status +'eq "A"}">'+
													'<a href="#" class="fa fa-check-circle fa-2x green" title="Active"></a>'+
													'</c:when>'+
													'</c:choose>'+'</div>',
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="getShiftdata(\''
													+ shiftMasId
													+ '\',\'ShiftMaster.html\',\'VIEW\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="getShiftdata(\''
													+ shiftMasId
													+ '\',\'ShiftMaster.html\',\'EDIT\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);

							}
							else{  
								result.push([
									'<div class="text-center">'
											+ (index + 1) + '</div>',
									'<div class="text-center">'
											+ shiftIdDesc + '</div>',
									'<div class="text-center">'
											+ fromTime + '</div>',
									'<div class="text-center">'
											+ toTime + '</div>',
									'<div class="text-center">'
											+ isCrossDayShift + '</div>',
									'<div class="text-center">'
											+ isGeneralShift + '</div>',
									'<div class="text-center">'+
											'<c:choose>'+
											'<c:when test="${'+status +'eq "I"}">'+
											'<a href="#" class="fa fa-times-circle fa-2x red" title="InActive"></a>'+
											'</c:when>'+
											'</c:choose>'+'</div>',
									'<div class="text-center">'
											+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="getShiftdata(\''
											+ shiftMasId
											+ '\',\'ShiftMaster.html\',\'VIEW\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
											+ '<button type="button" class="btn btn-warning btn-sm "  onclick="getShiftdata(\''
											+ shiftMasId
											+ '\',\'ShiftMaster.html\',\'EDIT\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
											+ '</div>' ]);
							}
							
						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList
				.push(getLocalMessage("ShiftMasterDTO.validation.selectAtLeastOne"));
		displayErrorsOnPage(errorList);

	}
}

function saveOrUpdateForm(obj, successMessage, successUrl, actionParam)
{
	if (!actionParam) {
		
		actionParam = "save";																									
	}
	//return doFormAction(obj,successMessage, actionParam, true , successUrl);
	return doFormActionForSave(obj,successMessage, actionParam, true , successUrl);
}

function doFormActionForSave(obj,successMessage, actionParam, sendFormData, successUrl)
{
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	
	             var requestData = {};
	
				if (sendFormData) {
					
					requestData = __serializeForm(theForm);
				}
			
				var url	=	$(theForm).attr('action')+'?' + actionParam;
				 
				
				var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
				if ($.isPlainObject(returnData))
				{
					var message = returnData.command.message;
					
					var hasError = returnData.command.hasValidationError;
					
					if (!message) {
						message = successMessage;
					}
					
					if(message && !hasError)
						{
						   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
						   		
						   		showSaveResultBox(returnData, message, 'AdminHome.html');
						   	
						   	else
						   		
						   		showSaveResultBox(returnData, message, successUrl);
						}
					else if(hasError)
					{
						$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
					}
					else
						return returnData;
					
				}
				else if (typeof(returnData) === "string")
				{
					$(formDivName).html(returnData);	
					prepareTags();
				}
				else 
				{
					alert("Invalid datatype received : " + returnData);
				}
				
				return false;
	}
	
function showSaveResultBox(returnData, successMessage, redirectUrl) {
	var success = returnData['command']['status'];
	var commandMessageText = returnData['command']['message'];
	var messageText = success ? successMessage : commandMessageText;
			
	var message='';
	var cls = getLocalMessage('ShiftMasterDTO.shiftOk');
	
	 if(redirectUrl == 'ShiftMaster.html'){
		 message	+='<p class="text-blue-2 text-center padding-15">'+ messageText+'</p>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
		' onclick="closeApproval()"/>'+	
		'</div>';
    	 
    } //goToDashBoard()
	else if(redirectUrl=="" || redirectUrl==null){
	
		message	+='<p class="text-blue-2 text-center padding-15">'+ messageText+'</p>';
		message	+='<div class=\'class="text-center padding-bottom-10"\'>'+	
		
		'</p>';
		
	}
	else{
    
	 message	+='<p class="text-blue-2 text-center padding-15">'+ messageText+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
	' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+	
	'</div>';
	}
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
   if(redirectUrl=="" || redirectUrl==null){
	      showModalBox(errMsgDiv);
	}else{
		  showModalBoxWithoutClose(errMsgDiv);
	}
	
	return false;
}
