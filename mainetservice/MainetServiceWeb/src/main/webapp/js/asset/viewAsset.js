$(document).ready(
		function() {

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
								'AssetRegistration.html?'
										+ $(e.target)
												.attr('data-content-param')
										+ '', 'POST', {}, false, 'html');
						$($(e.target).attr('href')).html(response);
						$(e.target).data('loaded', true);
						// e.relatedTarget // previous active tab
					});

			if ($('#acquisitionMethod option:selected').attr('code') == "LE") {
				$('.nav li#leasing-comp').removeClass('disabled');
			} else {
				$('.nav li#leasing-comp').addClass('disabled');
			}

			if ($('#assetGroup option:selected').attr('code') == "L") {
				$('.nav li#linear-tab').removeClass('disabled');
			} else {
				$('.nav li#linear-tab').addClass('disabled');
			}

		});

//D#34059
function processTabSaveRes(response, nextTab, currentDiv,parentTab) {
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');
	
	if(!errorsPresent || errorsPresent == undefined || errorsPresent.length == 0) {
		//$(targetDivName).html(response);
		//$('#assetParentTab a[href='+currentDiv+']').data('loaded',false);
		$(''+parentTab+' a[href="'+nextTab+'"]').tab('show');
		
		var errorPreviousTab = $(currentDiv).find('#validationerrordiv');
		if(errorPreviousTab.length > 0){
			var divError = $(currentDiv).find('#validationerrordiv');
			$(divError).addClass('hide');
		}
	} else {
		//window.scrollTo(0, 0);
		$(currentDiv).html(response);
		prepareDateTag();
	}
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function showTab(tabId) {
	$('#assetViewParentTab a[href="' + tabId + '"]').tab('show');
}

function showPreviousTab(previousTab, alternateTab) {
	if (!$('#assetViewParentTab a[href="' + previousTab + '"]').parent().hasClass(
			'disabled')) {
		$('#assetViewParentTab a[href="' + previousTab + '"]').tab('show');
	} else {
		$('#assetViewParentTab a[href="' + alternateTab + '"]').tab('show');
	}
}

function saveAssetInfoApprovalData(approvalData){
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
