$(document).ready(function(){	

	$("#resetform").on("click", function(){ 
		  window.location.reload("#ViewPropertyDetail")
		});
	
	
	$("#datatables").dataTable({
		"oLanguage" : {
			"sSearch" : "",
			"oPaginate": {
				"sFirst": "First page",				
				"sLast": "Last page",
				"sNext": "Next page",
		        "sPrevious": "Previous page"
		      },
			"oAria": {
		        "sSortAscending": " - click/return to sort ascending",
		        "sSortDescending": " - click/return to sort descending"
		      }
		},		
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true

	});

});

//Defect #154078
function roundDecimal2Digit() {
	var roundDecimalSelector = $('.round-decimal-2-dig');
	roundDecimalSelector.each( function(){
		var value = $(this).text();
		if(value != "" ) {
			value = Number(value.replace(/,/, "." )).toFixed(2); //Round to 2 decimal places
			$(this).text(value);
		}
	});
}

function SearchButton() {
	var errorList = [];
	errorList = ValidateSearch(errorList);	
	if (errorList.length == 0) {		
		var requestData = $('#ViewPropertyDetail').serialize();	
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'ViewPropertyDetail.html?searchData', requestData, 'html');
		
		if (ajaxResponse == "[]") {
			errorList.push(getLocalMessage("property.noRecordFound"));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		else{
		var propNo = $("#proertyNo").val();
		var oldPropNo = $("#oldPid").val();
		var data = {
			"propNo" : propNo,
			"oldPropNo" : oldPropNo
		};
		var URL = 'ViewPropertyDetail.html?checkActiveStatus';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		
		if (!returnData) {
			errorList
					.push(getLocalMessage("property.inactiveProperty"));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		else{
		var prePopulate = JSON.parse(ajaxResponse);		
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							result
									.push([
											obj.proertyNo,
											obj.oldPid,
											obj.ownerName,
											obj.mobileno,
											obj.receiptDate,
											obj.outstandingAmt,
										
											'<td >'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 margin-left-5"  onclick="viewApplication(\''
													+ obj.proertyNo
													+ '\',\'V\')" title="View" aria-label="View Details"><i class="fa fa-eye"></i></button>'
													 +'<button type="button"  class="btn btn-warning"  onclick="billPayment(\''
														+ obj.proertyNo
														+ '\',\'V\')" title="View" aria-label="View Details">Pay Bill</button>'
													
														 ]);
						});
		table.rows.add(result);
		table.draw();
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			showError(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();
		}
		}
		}
	} else {
		
		displayErrorsOnPage(errorList);
	}
}

function viewApplication(propId, formMode) {

	var divName = formDivName;
	var url = "ViewPropertyDetail.html?edit";
	data = {
		"propId" : propId,
		"formMode" : formMode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	roundDecimal2Digit(); //Defect #154078

}

function SearchPropDetails(element) {
	var errorList = [];
	errorList = ValidateInfo(errorList);	
	if (errorList.length == 0) {
		var data = $('#ViewPropertyDetail').serialize();
		var URL = 'ViewPropertyDetailFromMainPage.html?SearchPropDetails';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
		reloadGrid('gridViewPropertyDetail');
	}
	else {
		displayError(errorList);
	}
}

function ValidateInfo(errorList){
	if ($("#assNo").val() == "" && $("#assOldpropno").val() == "" && $("#assoOwnerName").val()=="" && $("#assoMobileno").val()=="") {
		errorList.push(getLocalMessage("property.viewPropDetails.formSearchValid"));
	}
	return errorList;
} 

function ValidateSearch(errorList){
	if ($("#proertyNo").val() == "" && $("#oldPid").val() == "" && $("#ownerName").val()=="" && $("#mobileno").val()=="" && $("#assWard1").val()=="0" && $("#locId").val()=="0" && $("#khasraNo").val()=="" && $("#fromAmout").val()=="" && $("#toAmount").val()=="" ) {
		errorList.push(getLocalMessage("property.viewPropDetails.formSearchValid"));
	}
	
	if((!$("#fromAmout").val()=="" && $("#toAmount").val()=="") || ($("#fromAmout").val()=="" && !$("#toAmount").val()=="") ){
		errorList.push(getLocalMessage("property.viewPropDetails.formFieldValid"));
	}
	
	return errorList;
}

function displayError(errorList){
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
 
function ViewAssDetails(bmid){
	 
	 	var data={
	 		"bmIdNo":bmid
	 	};
		var URL='ViewPropertyDetail.html?viewPropDet';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
}


function DownloadPdfFile(bmid,bmYear,propNo){

	 	var data={
	 			"bmIdNo":bmid,
	 			"finYearId":bmYear,
		 		"propNo":propNo
	 	};
		var URL='ViewPropertyDetail.html?downloadBillBirtReport';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		
		window.open(returnData,'_blank' );
				
}


function DownloadReceiptPdfFile(recptId,receiptNo){

 	var data={
 			"recptId":recptId,
 			"receiptNo":receiptNo
 	};
	var URL='ViewPropertyDetail.html?propertyReceiptDownload';
	var returnData=__doAjaxRequest(URL, 'POST', data, false);
	
	//window.open(returnData,'_blank' );
	if(returnData!=""){
		var title='Revenue Receipt';
		var printWindow = window.open('', '_blank');
		printWindow.document.write('<html><head><title>' + title + '</title>');		
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();	
	}	
	
}


 function BackToDetails(){
	 var data={};
			var URL='ViewPropertyDetail.html?backToPropDet';
			var returnData=__doAjaxRequest(URL, 'POST', data, false);
			$(formDivName).html(returnData);
 }
 
 function BackToSearch(){
	 var data={};
			var URL='ViewPropertyDetail.html?backToSearch';
			var returnData=__doAjaxRequest(URL, 'POST', data, false);
			$(formDivName).html(returnData);
 }
 function Reset(){
	 var data={};
			var URL='ViewPropertyDetail.html?resetViewProp';
			var returnData=__doAjaxRequest(URL, 'POST', data, false);
			$(formDivName).html(returnData);
 }
 
//fetch API details on selection of button 
 function getLandApiDetails(obj){
 	var landTypePrefix=$(".landValue").val();
 	var data = {};
 	var URL = 'ViewPropertyDetail.html?getLandTypeApiDetails';
 	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
 	$("#showAuthApiDetails").html(returnData);
 	$("#showAuthApiDetails").show();		
 }
 
 
 function billPayment(propNo){
		var data={"propNo":propNo};
		var URL='PropertyBillPayment.html?getBillPaymentDetailFromView';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
	}
 
 
 function downoladBill(bmid,bmYear,propNo){
	  
	  var requestData={
		 		"bmIdNo":bmid,
		 		"finYearId":bmYear,
		 		"propNo":propNo
		 	};
		var URL = 'ViewPropertyDetail.html?downloadBillBirtReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
}