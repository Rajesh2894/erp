var URL = "AssetDetailsReport.html";

$(function(){
$('#reportTypeId').change(function(){
	debugger;
	var selectedId = $('#reportTypeId').val();
	var reportTypeCodes = $('#reportTypeId option:selected').attr('code');
	var requestData = {
			'reportTypeCode': $('#reportTypeId option:selected').attr('code')
	}
	var ajaxResponse = __doAjaxRequest(URL+'?onReportType', 'POST', requestData, false,'html');
	$('.content-page').html(ajaxResponse);
	if(reportTypeCodes=='ROL')
	{
	var assetClass2 = $(ajaxResponse).find('#assetClass2').val();
	populateLists(assetClass2);
	}
	
});
});
	
function registerImmovable(element) {
	
	var errorList =[];
	
	var assetClass2 = $('#assetClass2').val();
	var assetCodeselected = $('#assetCodeselected').val();
	var faYearId=$('#faYearId').val();
	if(assetClass2 !=-1 && assetCodeselected!=-1 )
/*if(assetClass2 !=-1 && assetCodeselected!=-1 && assetCodeselected!=null )*/
	{
	var requestData = {
			'assetClass2': assetClass2,
			'faYearId':faYearId,
			'assetCodeselected':assetCodeselected
	}	
	var ajaxResponse= __doAjaxRequestValidationAccor(element, URL+'?registerOfImmovable', 'POST', requestData, false, 'html');
    if(ajaxResponse != false){
    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
    	if (hiddenVal == 'Y') {
    		var errorList =[];
    		errorList.push(getLocalMessage('asset.report.norecords'));
    		displayErrorsOnPage(errorList);
    	} else {
    		$('.content-page').html(ajaxResponse);
    	}
    }
	}
else
	{
	errorList.push(getLocalMessage('asset.report.enterselection'));
    displayErrorsOnPage(errorList);//this method is present in script-library 
	}
	
	
}
function  populateLists(assetClass2) {
	
	 $("#assetCodeselected").html('');
	//var assetClass2 = $('#assetClass2').val();
	
	var requestData = {
			'assetClass2': assetClass2
	}	
	var returnData= __doAjaxRequest(URL+'?populateList', 'POST', requestData, false, 'json');
	
	 if(returnData != ""  && returnData.length>0 ){
		 $.each(returnData, function(i) {
				 $("#assetCodeselected")
				 .append($("<option></option>")
						 .attr("value",returnData[i])
						 .text(returnData[i]));
			 
		 });	
	 }
	 $(".chosen-select-no-results").trigger("chosen:updated");
}


