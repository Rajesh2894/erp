function saveschemeApplicationFormData(approvalData)
{
	
	var errorList = [];
	
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	
	
	
	if(decision == undefined || decision == '')
		errorList.push(getLocalMessage('asset.info.approval'));
	else if(comments == undefined || comments =='')
		errorList.push(getLocalMessage('asset.info.comment'));

	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,
				getLocalMessage('work.estimate.approval.creation.success'),
				'AdminHome.html', 'saveDecision');
	}

}