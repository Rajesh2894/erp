$(document).ready(
		function() {

			$('#appointmentDate').datepicker({
		    	minDate: 0, 
		    	dateFormat: 'dd/mm/yy'
			});
			
			$('a[data-toggle="tab"]').on('hide.bs.tab', function(e) {

				if ($(e.relatedTarget).parent().hasClass('disabled')) {
					e.preventDefault();
					return false;
				}
			});

			$('a[data-toggle="tab"]').on(
					'show.bs.tab',
					function(e) {
						var response = __doAjaxRequest(
								'MarriageRegistration.html?'
										+ $(e.target)
												.attr('data-content-param')
										+ '', 'POST', {}, false, 'html');
						$($(e.target).attr('href')).html(response);
						$(e.target).data('loaded', true);
						// e.relatedTarget // previous active tab
					});

		});

function processTabSaveRes(response, nextTab, currentDiv, parentTab) {
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');

	if (!errorsPresent || errorsPresent == undefined
			|| errorsPresent.length == 0) {
		// $(targetDivName).html(response);
		// $('#assetParentTab a[href='+currentDiv+']').data('loaded',false);
		$('' + parentTab + ' a[href=' + nextTab + ']').tab('show');

		var errorPreviousTab = $(currentDiv).find('#validationerrordiv');
		if (errorPreviousTab.length > 0) {
			var divError = $(currentDiv).find('#validationerrordiv');
			$(divError).addClass('hide');
		}
	} else {
		// window.scrollTo(0, 0);
		$(currentDiv).html(response);
		prepareDateTag();
	}
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
}



function showPreviousTab(previousTab, alternateTab) {
	if (!$('#mrmViewParentTab a[href=' + previousTab + ']').parent().hasClass(
			'disabled')) {
		$('#mrmViewParentTab a[href=' + previousTab + ']').tab('show');
	} else {
		$('#mrmViewParentTab a[href=' + alternateTab + ']').tab('show');
	}
}


function showTab(tabId) {
	$("#mrmParentTab a[href='"+tabId+"']").tab('show');
	$("#mrmViewParentTab a[href='"+tabId+"']").tab('show');
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}



function checkLastApproval(element){
    var divName = '#appointment';
    var URL = 'MarriageRegistration.html?checkLastApproval';
    var formName = findClosestElementId(element, 'form');
    var theForm = '#' + formName;
    var requestData = {};
    requestData = __serializeForm(theForm);
    var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');
    $(divName).removeClass('ajaxloader');
    $(divName).html(returnData);
    prepareTags();
    let payableFlag = $("#appointmentTabForm input[id=payableFlag]").val();
    if (payableFlag == 'N') {
    	saveMRMInfoApprovalData(element)
    }
}

function saveMRMInfoApprovalData(approvalData) {
	var errorList = [];
	
	var decision = $("input[id='decision']:checked").val();
	//here if decision is reject than don't validate appointment details
	if(decision != "REJECTED"){
		validateAppointmentDetails(errorList);	
	}
	var comments = document.getElementById("comments").value;

	if (decision == undefined || decision == '')
		errorList.push(getLocalMessage('mrm.info.approval'));
	else if (comments == undefined || comments == '')
		errorList.push(getLocalMessage('mrm.info.comment'));

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,
				getLocalMessage('Approval Created Successfully'),
				'CitizenHome.html', 'saveDecision');
	}

}



