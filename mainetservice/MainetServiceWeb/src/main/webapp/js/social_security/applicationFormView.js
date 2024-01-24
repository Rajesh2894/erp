function saveschemeApplicationFormData(approvalData) {
	
	var errorList = [];

	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;

	if (decision == undefined || decision == '')
		errorList.push(getLocalMessage('asset.info.approval'));
	else if (comments == undefined || comments == '')
		errorList.push(getLocalMessage('asset.info.comment'));
	

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {

		if ($("input[id='decision']:checked").val() == 'APPROVED') {

			var divName = '.content-page';
			var URL = 'SchemeApplicationForm.html?validateScheme';
			var formName = findClosestElementId(approvalData, 'form');
			var theForm = '#' + formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
					'html');

			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			prepareTags();

			$("#errorDiv").hide();

			var validate =$("#approvalFlag").val();
			if (validate == "Y") {

				return saveOrUpdateForm(approvalData,
						getLocalMessage('work.estimate.approval.creation.success'),
						'AdminHome.html', 'saveDecision');
			}

		}
		else
			{
			return saveOrUpdateForm(approvalData,
					getLocalMessage('work.estimate.approval.creation.success'),
					'AdminHome.html', 'saveDecision');
			}
	}

}

