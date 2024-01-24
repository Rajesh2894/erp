$(document).ready(function() {

	$("#id_itemOpeningBalance").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});

});

function openBalnceForm(formUrl, actionParam) {
	editViewFormOpening("", "C")
}

function editViewFormOpening(balId, type) {
	var requestData = 'openBalId=' + balId + '&type=' + type;
	var response = __doAjaxRequest('ItemOpeningBalance.html?form', 'POST',
			requestData, false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	$("#methodName").text($("#valueMethod").val());
	$("#methodName span").removeClass("mandColorClass");
	//prepareDateTag();
}

function resetOpeningForm() {
	/*$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");*/
	$("select").val("0").attr("selected", "selected");
	$('.error-div').hide();
}


function searchItemOpenBalanceData() {
	var errorList = [];
	if ($('#storeIdSearch').val() == '0' && $('#itemIdSearch').val() == '0') {
		$("#errorDiv").show();
		errorList.push(getLocalMessage("material.item.selectAtleast1criteria"));
		displayErrorsOnPage(errorList);
	} else {
		var data = {
			"storeId" : $('#storeIdSearch').val(),
			"itemId" : $('#itemIdSearch').val(),
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('ItemOpeningBalance.html?search',
				data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		//prepareTags();
	}
}

function showConfirmForDeActive(openBalId) {
	var errMsgDiv = '.msg-dialog-box';
	$(errMsgDiv).html('');
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('material.item.WantDelete') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + openBalId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(openBalId) {
	$.fancybox.close();
	var requestData = 'openBalId=' + openBalId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('ItemOpeningBalance.html?deActivate',
			requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/*Pending Item

 Search
 CSV upload*/

