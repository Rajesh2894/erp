$(document).ready(function() {
	 var judgementMasterDate = $('#judgementMasterDate').val();
	$(".datepicker").datepicker({
		 minDate :'0d' ,
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : judgementMasterDate + '1d'
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
	if(errorList > 0)
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
			var formUrl = "JudgementImplementationDetail.html?searchCaseEntry";
			var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
	}
}

function validateSearch(errorList)
{
	
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
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, 'Judge Details saved successfully',
				'JudgementImplementationDetail.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}

}



function fetchcaseHearingList() {
	var requestData = {
		'caseId' : $('#cseId').val(),
	};
	var url = "JudgementImplementationDetail.html?viewHearingHistory";
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
	
	var implementerPhoneNo = $('#implementerPhoneNo').val();
	var desigOfImplementer = $('#desigOfImplementer').val();
	

	var implementerEmail = $('#implementerEmail').val();
	
	
	
	if (implementerName == "" || implementerName == undefined
			|| implementerName == null) {
		errorList.push(getLocalMessage('lgl.validate.implementerName'));
	}

	if (desigOfImplementer == "" || desigOfImplementer == undefined
			|| desigOfImplementer == null) {
		errorList.push(getLocalMessage('lgl.judgeImplementation.validation.designation'));
	}
	


	if (implementerPhoneNo == "" || implementerPhoneNo == undefined
			|| implementerPhoneNo == null) {
		errorList.push(getLocalMessage('lgl.validate.implementerPhoneNo'));
	}
	if (implementerEmail == "" || implementerEmail == undefined
			|| implementerEmail == null) {
		errorList.push(getLocalMessage('lgl.validate.implementerEmail'));
	}

	
	if (implementerEmail != "") {
		var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
		var valid = emailRegex.test(implementerEmail);
		if (!valid) {
			errorList.push(getLocalMessage("Invalid.Email"));
		}
	}

	
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
function resetCaseEntry() {

	$("#judgementImplementation").prop('action', '');
	$("#judgementImplementation").prop('action', 'JudgementImplementationDetail.html');
	$("#judgementImplementation").submit();

}