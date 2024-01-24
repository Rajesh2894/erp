$(document).ready(
		function() {
			$('.hasNumberWithFSlash').on('keyup', function () {
			    this.value = this.value.replace(/[^0-9/]/g,'');
			  
			});

});
		
/********************SearchFunction************/
function searchContractData(formUrl, actionParam) {
	showloader(true);
	setTimeout(function() {
		var errorList = [];
		var data = {
				"contNo" : $('#contractNo').val(),
				
		};
		//errorList = validateSearchForm();
		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			// prepareTags();
		}
		showloader(false);
	}, 200);

}


function createData() {
	var data = {
		"leaseFlag" : "Y",

	};
	var url = "Vendormaster.html?form";
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(url, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function backTenderForm() {
	var divName = '.content-page';
	var url = "TransferOfLease.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}
