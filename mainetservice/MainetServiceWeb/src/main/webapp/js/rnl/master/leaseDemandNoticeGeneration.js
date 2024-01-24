$(document).ready(function() {
	
	$('.fancybox').fancybox();
	$(".myTable").dataTable({
		//your normal options
		"language": { "search": "" }, 
		"lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]]
	});

		$('.chosen-select').chosen();
		
		if($(".noticeType:checked").val() == "S"){
		     $('.propNo').show();
		     $('.nType').show();
		     $('.location').hide();
		     $('#datatables').hide();
		     $('#searchBtn').hide();
		     $('#generateBtn').show();
		}else{
			$('.propNo').hide();
			$('.nType').show();
			$('.location').show();
			$('#datatables').show();
			$('#searchBtn').show();
			$('#generateBtn').hide();
		}
});

function payModefields(){
    if($(".noticeType:checked").val() == "S"){
    	$('.propNo').show();
    	$('.nType').show();
    	$('.location').hide();
    	$('#datatables').hide();
    	$('#searchBtn').hide();
    	$('#generateBtn').show();
    }else{
    	$('.propNo').hide();
    	$('.nType').show();
    	$('.location').show();
    	$('#datatables').show();
    	$('#searchBtn').show();
    	$('#generateBtn').hide();
    }
 }

/******************** reset button ************/
function resetForm() {
	$('#propNo').val('');
	$('.nType').val('');
	$('.location').val('');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

/********************SearchFunction************/
function searchData(formUrl, actionParam) {
	showloader(true);
	setTimeout(function() {
		var errorList = [];
		var notTyp =  $('#notTyp option:selected').val();
		var noticeTypeDesc = $('.noticeType:checked').val();
		var data = {
			"locationId" : $('#locationId').val(),
			"notTyp" : notTyp,
			"noticeTypeDesc" : noticeTypeDesc
		};
		errorList = validateSearchForm();
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

function validateSearchForm(errorList) {
	var errorList = [];
	if($("#locationId").val( ) == 0){
		errorList.push(getLocalMessage("rnl.selectLocation"));
	}
	if($('#notTyp option:selected').val() == 0){
		errorList.push(getLocalMessage("rnl.selectNoticeType"));
	}
	return errorList;
}

/******************** back button ************/
function backButton() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('LeaseDemandNoticeGeneration.html?back');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}
