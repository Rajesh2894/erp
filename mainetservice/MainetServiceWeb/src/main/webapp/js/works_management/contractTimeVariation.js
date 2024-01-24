function getContractDetail(obj) {
	
	var requestData = {
		"contId" : $(obj).val()
	}
	var response = __doAjaxRequest(
			'ContractTimeVariation.html?getContractDetails', 'post',
			requestData, false, 'json');
	var value = response.split(",");
	if (value[0] != "null") {

		$('#contFromDate').val(value[0]);
	} else {
		$('#contFromDate').val("");
	}
	if (value[1] != 'null') {
		$('#contToDate').val(value[1]);
	} else {
		$('#contToDate').val("");
	}
	if (value[2] != 'null') {
		$('#contp2Name').val(value[2]);
	} else {
		$('#contp2Name').val("");
	}
	prepareTags();
}

function fileCountUpload(element) {
	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(
			'ContractTimeVariation.html?fileCountUpload', 'POST', requestData,
			false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();
	//Defect #155703
	var addTitle = getLocalMessage("works.management.add");
	var deleteTitle = getLocalMessage("works.management.delete");
	$('.addButton').attr('title',addTitle);
	$('.delButton').attr('title',deleteTitle);
}

$("#attachDoc").on("click", '.delButton', function(e) {
	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
	}
	e.preventDefault();
});

$("#deleteDoc").on(
		"click",
		'#deleteFile',
		function(e) {
			
			var errorList = [];
			var fileArray = [];

			if (errorList.length > 0) {
				$("#errorDiv").show();
				showErr(errorList);
				return false;
			} else {
				$(this).parent().parent().remove();
				var fileId = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');
				if (fileId != '') {
					fileArray.push(fileId);
				}
				$('#removeVariationFileById').val(fileArray);
			}
		});

function updateContractPeriod() {
	
	var errorList = [];
	var divName = formDivName;
	var contId=$("#contId").val();
	var contTimeExtPer = $('#contTimeExtPer').val();
	var contTimeExtUnit = $('#contTimeExtUnit').val();
	
	if(contId== null || contId==''){
		errorList.push(getLocalMessage("work.order.please.select.contract.no"));	
		}
	if(contTimeExtPer== null || contTimeExtPer==''){
	errorList.push(getLocalMessage("wms.EnterTimeExtPeriod"));	
	}
    if(contTimeExtUnit== '0' || contTimeExtUnit==''|| contTimeExtUnit==undefined ){
	errorList.push(getLocalMessage("wms.EnterTimeExtPeriodUnit"));	
	}
    
    if(errorList.length==0){
    	
	var contTimeUnit = $('#contTimeExtUnit').find("option:selected").attr(
			'code').split(",")
	var contTimeExtUnit = contTimeUnit[0];
	var requestData = $.param($('#contractTimeVariation').serializeArray())
	var response = __doAjaxRequest(
			'ContractTimeVariation.html?updateContractPeriod', 'POST',
			requestData, false);
	showConfirmBox(getLocalMessage("wms.ContractTimeVariationSaved"));
    }else{
    	displayErrorsOnPage(errorList);
    }
}
function showConfirmBox(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + sucessMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}
function proceed() {
	$.fancybox.close();
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'ContractTimeVariation.html');
	$("#postMethodForm").submit();
}

function formatDate(date) {
	var d = new Date(date), month = '' + (d.getMonth() + 1), day = ''
			+ d.getDate(), year = d.getFullYear();

	if (month.length < 2)
		month = '0' + month;
	if (day.length < 2)
		day = '0' + day;

	return [ day, month, year ].join('/');
}