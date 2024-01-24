var searchUrl="AssetDetailsReport.html";
function searchReport(element) {
	var errorList =[];
	var assetGroup = $('#assetGroup').val();
	var assetType = $('#assetType').val();
	var assetClass1 = $('#assetClass1').val();
	var assetClass2 = $('#assetClass2').val();
	
	if(assetGroup == 0 ){
		errorList.push(getLocalMessage('asset.searchreport.select.assetgroup'));
	}
	
	if(assetGroup != 0 &&  (assetClass1 !=0 || assetClass2 !=0)){
		var requestData = {
				'assetGroup': assetGroup,
				'assetType': assetType,
				'assetClass1': assetClass1,
				'assetClass2': assetClass2
		}	
		var ajaxResponse= __doAjaxRequestValidationAccor(element, searchUrl+'?detailReport', 'POST', requestData, false, 'html');
	    if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push(getLocalMessage('asset.report.norecords'));
	    		//displayErrorsOnPage(errorList);
	    	} else {
	    		$('.pagediv').html(ajaxResponse);
	    	}
	    }
	}else if(assetGroup!= 0 ){
		errorList.push(getLocalMessage('asset.report.enterselectionClass'));
		//displayErrorsOnPage(errorList);//this method is present in script-library 
	}
	
	if(errorList.length >0){
		displayErrorsOnPage(errorList);//this method is present in script-library
	}
	
}

function landRegister(element) {
	
	var errorList =[];
	var assetClass2 = $('#assetClass2').val();
	var assetCodeselected = $('#assetCodeselected').val();
	var faYearId=$('#faYearId').val();
	if(assetClass2 !=-1 && assetCodeselected!=-1)
/*if(assetClass2 !=-1 && assetCodeselected!=-1 && assetCodeselected!=null )*/
	{
	var requestData = {
			'assetClass2': assetClass2,
			'faYearId':faYearId,
			'assetCodeselected':assetCodeselected
	}	
	var ajaxResponse= __doAjaxRequestValidationAccor(element, URL+'?landRegisterReport', 'POST', requestData, false, 'html');
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

function registerMovable(element) {
	
	var errorList =[];
	var assetClass1 = $('#assetClass1').val();
	var faYearId = $('#faYearId').val();
if(assetClass1 !=0 || faYearId!=0)
	{
	var requestData = {
			'assetClass1': assetClass1,
			'faYearId':faYearId
	}	
	var ajaxResponse= __doAjaxRequestValidationAccor(element, searchUrl+'?registerOfMovable', 'POST', requestData, false, 'html');
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

function  populateList() {
	
	 $("#assetCodeselected").html('');
	var assetClass2 = $('#assetClass2').val();
	
	var requestData = {
			'assetClass2': assetClass2
	}	
	var returnData= __doAjaxRequest(searchUrl+'?populateList', 'POST', requestData, false, 'json');
	
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

//Print Div
function PrintDivDefault(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title>'+title+'</title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>') 
	printWindow.document.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot, .filterHeader").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
} 

