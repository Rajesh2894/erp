var URL="AssetSchedulerMaster.html";
$(document).ready( 
		function(){
			
			$("#assetDateField").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : new Date()
			});

	
			$('.p_element').hide();
			$('.p_assetclass').hide();
});



function showAssetcode(element) {
	var checkValue = $("input[name='astschDto.calculationType']:checked").val();
	
	if(checkValue=="Atype")
		{
	$('.p_element').show();
	$('.p_assetclass').hide();
		}
	else
		{
		$('.p_element').hide();
		$('.p_assetclass').show();
		}
	
}


function CalculateDeprecation(element) {
	var errorList = []
	
	var checkValue = $("input[name='astschDto.calculationType']:checked").val();
	if(checkValue == "Atype"){
	var assetCode = $("#assetCode").val();
	var assetDateField = $("#assetDateField").val();
	if(assetCode!='' && assetCode!=null && assetCode!=undefined)
		{
	var requestData = {
			"assetCode":assetCode,
			"assetDateField":assetDateField,
	};
	var ajaxResponse = __doAjaxRequest(URL+'?calculateDeprecation',
			'POST', requestData, false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
	

}
	}	
	if(checkValue == "Aclass"){
		var assetClass = $("#assetClass").val();
		var assetDateField = $("#assetDateField").val();
		if(assetClass!='' && assetClass!=null && assetClass!=undefined)
			{
			if(!isNaN(assetClass))
				{
		var requestData = {
				"assetClass":assetClass,
				"assetDateField":assetDateField,
		};
		var ajaxResponse = __doAjaxRequest(URL+'?calculateDepByAssetClass',
				'POST', requestData, false, 'html');
		$('.content-page').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse);
		

	}else
		{
		errorList.push(getLocalMessage("asset.schedulermaster.play"));
		displayErrorsOnPage(errorList);
		}
			
			}
		}
}

function resetData() {
	window.location.href = "AssetSchedulerMaster.html";
	
}
function assetClassDetails(element) {
	
	
}

function assetdatewisedetails() {
	
	
	var assetDateField=$("#assetDateField").val();
	
}