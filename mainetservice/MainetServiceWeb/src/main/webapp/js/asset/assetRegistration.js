/*$(document).ready(function(){
	$('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
		localStorage.setItem('activeTab', $(e.target).attr('href'));
	});
	
	
	var activeTab = localStorage.getItem('activeTab');
	
	if(activeTab){
		$('#myTab a[href="' + activeTab + '"]').tab('show');
	}
});*/
$(document).ready(
		function() {
			
			$('a[data-toggle="tab"]').on('hide.bs.tab', function(e) {
				
				if ($(e.relatedTarget).parent().hasClass('disabled')) {
					e.preventDefault();
					return false;
				} // newly activated tab
				/*
				 * else { e.preventDefault(); var response = __doAjaxRequest(
				 * 'AssetRegistration.html?' + $(e.target).attr(
				 * 'data-content-param') + '', 'POST', {}, false, 'html');
				 * //$(e.target).tab('show'); }
				 */
				// e.relatedTarget // previous active tab
			});

			$('a[data-toggle="tab"]').on(
					'show.bs.tab',
					function(e) {
						if (!$(e.target).data('loaded')) {
							var response = __doAjaxRequest(
									'AssetRegistration.html?'
											+ $(e.target).attr(
													'data-content-param') + '',
									'POST', {}, false, 'html');
							$($(e.target).attr('href')).html(response);
							prepareDateTag();
							//$(e.target).data('loaded',true);
						}
						// e.relatedTarget // previous active tab
					});

		});

var tabcheck = false;

function showPreviousTab(previousTab, alternateTab) {
	if(!$('#assetParentTab a[href="'+previousTab+'"]').parent().hasClass('disabled')) {
		$('#assetParentTab a[href="'+previousTab+'"]').tab('show');
	} else {
		$('#assetParentTab a[href="'+alternateTab+'"]').tab('show');
		
	}
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

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
	$('#assetParentTab a[href="'+tabId+'"]').tab('show');
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

function disableTab() {
	
	tabcheck = false;
	$('.nav li#class-tab').find('a').removeAttr("data-toggle");
	$('.nav li#class-tab').addClass('disabled');

	$('.nav li#purchase-tab').find('a').removeAttr("data-toggle");
	$('.nav li#purchase-tab').addClass('disabled');

	$('.nav li#service-tab').find('a').removeAttr("data-toggle");
	$('.nav li#service-tab').addClass('disabled');

	$('.nav li#insurance-tab').find('a').removeAttr("data-toggle");
	$('.nav li#insurance-tab').addClass('disabled');

	$('.nav li#depre-tab').find('a').removeAttr("data-toggle");
	$('.nav li#depre-tab').addClass('disabled');

	$('.nav li#doc-tab').find('a').removeAttr("data-toggle");
	$('.nav li#doc-tab').addClass('disabled');
}

function enableTab() {
	
	tabcheck = true;
	$('.nav li#class-tab').find('a').removeAttr("data-toggle");
	$('.nav li#class-tab').removeClass('disabled');

	$('.nav li#purchase-tab').find('a').removeAttr("data-toggle");
	$('.nav li#purchase-tab').removeClass('disabled');

	$('.nav li#service-tab').find('a').removeAttr("data-toggle");
	$('.nav li#service-tab').removeClass('disabled');

	$('.nav li#insurance-tab').find('a').removeAttr("data-toggle");
	$('.nav li#insurance-tab').removeClass('disabled');

	$('.nav li#depre-tab').find('a').removeAttr("data-toggle");
	$('.nav li#depre-tab').removeClass('disabled');

	$('.nav li#doc-tab').find('a').removeAttr("data-toggle");
	$('.nav li#doc-tab').removeClass('disabled');

}