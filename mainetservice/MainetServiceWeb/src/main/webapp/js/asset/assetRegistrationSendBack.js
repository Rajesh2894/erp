$(document).ready(
		function() {

			/*$('a[data-toggle="tab"]').on('hide.bs.tab', function(e) {
				
				if ($(e.relatedTarget).parent().hasClass('disabled')) {
					e.preventDefault();
					return false;
				}
			});*/

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

function showPreviousTab(previousTab, alternateTab) {
	if(!$('#assetParentTab a[href='+previousTab+']').parent().hasClass('disabled')) {
		$('#assetParentTab a[href='+previousTab+']').tab('show');
	} else {
		$('#assetParentTab a[href='+alternateTab+']').tab('show');
	}
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function processTabSaveRes(response, nextTab, currentDiv) {
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');
	
	if(!errorsPresent || errorsPresent == undefined || errorsPresent.length == 0) {
		//$(targetDivName).html(response);
		//$('#assetParentTab a[href='+currentDiv+']').data('loaded',false);
		$('#assetParentTab a[href='+nextTab+']').tab('show');
		
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
	$('#assetParentTab a[href='+tabId+']').tab('show');
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function navigateTab(id, tabToSubmit, element) {
	
	if (!($('.nav li#' + id).hasClass("disabled"))) {
		
		var divName = '.tab-pane';//$(element).attr('href');//'.tab-pane';
		var response = __doAjaxRequest('AssetRegistration.html?' + tabToSubmit
				+ '', 'POST', {}, false, 'html');
		
		$(divName).removeClass('ajaxloader');
		
		$(divName).html(response);
		prepareDateTag();
		var activeTab = $('#assetParentTab').find('.active');
		if(activeTab)
			$(activeTab).removeClass('active');
		$('.nav li#' + id).addClass('active');
		
	}
	return false;
}