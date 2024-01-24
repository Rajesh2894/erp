function openAST(value) {
	var response = __doAjaxRequest('AssetRegistration.html?' + value + '',
			'POST', {}, false, 'html');
	$('.pagediv').html(response);
	prepareDateTag();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function backToSearch(moduleDeptCode) {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html');
	$("#postMethodForm").submit();
}

function backToHomePage() {
	
	var response = __doAjaxRequest('AssetRegistration.html?showEditAssetPage','POST', {}, false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function editModeProcess(response) {
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');
	
	if (!errorsPresent || errorsPresent == undefined
			|| errorsPresent.length == 0) {
		if ($.isPlainObject(response)) {
			
			var message = response.command.message;
			displayMessageOnSubmit(message);
		}
	} else {
		$('.pagediv').html(response);
		prepareDateTag();
	}
}

function displayMessageOnSubmit(message) {
	
	var errMsgDiv = '.msg-dialog-box';
	var cls = getLocalMessage('asset.proceed');

	var d = '<h5 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
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
function proceed() {
//	backToHomePage();
	window.location.href = ""+$("#moduleDeptUrl").val()+"";
	$.fancybox.close();
}

$(document).ready(function() {
	$(function(){
	
		var assetGroup = $("#assetGroup option:selected").attr("code");
		var acquisitionMethod=$("#acquisitionMethod option:selected").attr("code");
		if(assetGroup!="L"){
			$("#accountCode option[value='showAstLinePage']").hide();
		}
		if(acquisitionMethod!="LE"){
			$("#accountCode option[value='showAstLeasePage']").hide();
		}
	
});	

});

function assetGroupChange(element) {
		if ($('#assetGroup option:selected').attr('code') == "L") {
			$("#accountCode option[value='showAstLinePage']").show();
		} else {
			$("#accountCode option[value='showAstLinePage']").hide();
		}
}