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

function resetTable() {
	$('#datatables tbody').empty();
	showSingleMultiple();
}

function serachProperty() {

	showloader(true);	
	var errorList = [];
	if ($("input[name='specialNotGenSearchDto.specNotSearchType']:checked")
			.val() == "GP") {
		var groupPropName = $('#groupPropName').val();
		var parentPropName = $('#parentPropName').val();
		if ((groupPropName == "" || groupPropName == 0)
				&& (parentPropName = "" || parentPropName == 0)) {
			errorList.push(getLocalMessage("property.validGroupname"));
		}
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var formAction = $('#propertyBillGeneration').attr('action');
		var url = formAction + '?SearchPropNo';
		$('#propertyBillGeneration').attr('action', url);
		$('#propertyBillGeneration').submit();
	}
	/*
	 * var ajaxResponse = __doAjaxRequest( formAction+'?SearchPropNo', 'POST',
	 * requestData, false, 'html');
	 * 
	 * $(".content").html(ajaxResponse); return false;
	 */
}

function generateBill(element){
	showloader(true);
	setTimeout(function(){startGenBill(element)},2);
}

function startGenBill(element){
	$('#datatables').DataTable().destroy();
	return saveOrUpdateForm(element,
			"Bill generation  done successfully",
			'PropertyBillGeneration.html', 'saveform');
}

/*function resetForm(element){
	$("#propertyBillGeneration").submit();

}*/
function resetFormDetails(resetBtn) {
	
	var data = {};
	var URL ='PropertyBillGeneration.html?resetFormData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(".content-page").html(returnData);
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
		$("#locId").val("").trigger("chosen:updated");				
		$('#assdUsagetype1').val('');	
		$('#assdUsagetype2').val('');
		$('#parentPropNo').val('');
		$('#parentPropName').val('');
		$('.groupProps').hide();	
		//
		$('.searchBtn').show();	
		$('#PropDetails').show();
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
		$('#parentPropNo').val('');
		$('#parentPropName').val('');
		$('.groupProps').hide();
		//
		$('.searchBtn').show();	
		$('#PropDetails').show();
	}else if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="GP"){
		//Single
		$('.PropDetail').hide();
		$('.sectionSeperator').hide();
		$('.usageType').hide();
		$('.searchBtn').show();			
		$('#assWard1').val('');
		$('#assWard2').val('');
		$("#locId").val("").trigger("chosen:updated");	
		$('#propertyNo').val('');
		$('#oldPropertyNo').val('');		
		$('#assdUsagetype1').val('');	
		$('#assdUsagetype2').val('');		
		$('.groupProps').show();
		//
		$('.searchBtn').hide();	
		$('#PropDetails').hide();
	}else{
		$('.PropDetail').hide();
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.propLable').hide();
		$('.usageType').hide();
		$('.searchBtn').hide();
		$('.sectionSeperator').hide();
		$('.groupProps').hide();
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