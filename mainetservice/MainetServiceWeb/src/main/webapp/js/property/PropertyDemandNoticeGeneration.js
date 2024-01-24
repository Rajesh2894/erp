$(document)
		.ready(
				function() {
					
						
						showSingleMultiple();
						
						
						
						$('#datatables').DataTable({
				    		"oLanguage": { "sSearch": "" } ,
				    		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
				    	    "iDisplayLength" : 5, 
				    	    "bInfo" : true,
				    	    "lengthChange": true,
				    	    "bPaginate": true,
				    	    "bFilter": true
				    	    });
						
						$('#selectall').click(function(event) {
							if (this.checked) {
								$('.checkall').each(function() {
									this.checked = true;
								});
							} else {
								$('.checkall').each(function() {
									this.checked = false;

								});
							}
						});
				});




function serachProperty() {
	showloader(true);
		/*  $('#datatables').DataTable().destroy();
	
		var requestData = $('#propertyBillGeneration').serialize();*/
		
	var formAction	=	$('#propertyDemandNotice').attr('action');
		var url=formAction+'?SearchPropNo';
		$('#propertyDemandNotice').attr('action', url);

		$('#propertyDemandNotice').submit();
		
		/*
		var ajaxResponse = __doAjaxRequest(
				formAction+'?SearchPropNo', 'POST',
				requestData, false, 'html');

		$(".content").html(ajaxResponse);
		return false;*/
}

function generateNotice(element){
	$('#datatables').DataTable().destroy();
	return saveOrUpdateForm(element,
			"Demand Notice generation  done successfully",
			'PropertyDemandNoticeGeneration.html', 'saveform');
}


function resetSearchForm(element) {

	var theForm = '#propertyDemandNotice';
	var requestData = {};	
	var URL = 'PropertyDemandNoticeGeneration.html?resetForm';
	var returnData = __doAjaxRequest(URL, 'post', requestData, false, 'html');
	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}



function showSingleMultiple(){
	
	
	$('#selectall').prop('checked', true);
	$('.checkall').each(function() {
		this.checked = true;
	});

	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="SM"){
		$('.PropDetail').show();
		$('.propLable').show();
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.usageType').hide();
		$('.searchBtn').show();
		$('.sectionSeperator').hide();
		
		$('#assWard1').val('');
		$('#assWard2').val('');
		$('#locId').val('');
		
		
	}
	else if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="AL"){
		$('.PropDetail').hide();
		$('.wardZone').show();
		$('.Loc').show();
		$('.usageType').show();
		$('.searchBtn').show();
		$('.sectionSeperator').show();
		$('.PropDetail').val('');

		$('#propertyNo').val('');
		$('#oldPropertyNo').val('');
	}else{
		$('.PropDetail').hide();
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.propLable').hide();
		$('.usageType').hide();
		$('.searchBtn').hide();
		$('.sectionSeperator').hide();
	}
	
}

function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}