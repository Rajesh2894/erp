
$(function(){
$('#reportTypeId').change(function(){
	var selectedId = $('#reportTypeId').val();
	var reportTypeCode=$('#reportTypeId option:selected').attr('code');
	var requestData = {
			'reportTypeCode': reportTypeCode
	}
	var ajaxResponse = __doAjaxRequest('AccountFinancialReport.html?onReportType', 'POST', requestData, false,'html');
	var reportTypeHidden = $(ajaxResponse).find('#reportTypeHidden').val();
	$('#content').html(ajaxResponse);
	 $('.required-control').next().children().addClass('mandColorClass');
	$('#reportTypeId').val(selectedId);
	$( "#reportTypeId" ).prop( "disabled", true );
	if(reportTypeCode=='NAB' || reportTypeCode=='CFB' ||reportTypeCode=='BSB' ||reportTypeCode=='NMB' ||reportTypeCode=='IEB'){
		$("#transactionDateId").hide();
		$("#transactionDateIdlabel").hide();
		$("#transactionDateIdcal").hide();
	}
});
	
	

});

function viewReport(obj) {
    //debugger;
	showloader(true);
	setTimeout(function(){
			    $( "#reportTypeId" ).prop( "disabled", false );
			    var errorList =[];
			    var transactionTypeId = $('#transactionTypeId option:selected').attr('code');
			    if(checkValidation())
			    	{
				var transactionDate= $('#transactionDateId').val();
				var fromDate= $('#fromDateId').val();
				var toDate= $('#toDateId').val();
				var financialId=$('#faYearid').val();
				var accountHead= $('#accountHeadId').val();
				var registerDepTypeId= $('#registerDepTypeId').val();
				var categoryId= $('#categoryId').val();
				var paymodeId= $('#paymodeId').val();
				var vendorId=$('#vmVendorname').val();
				var fieldId=$('#fieldId').val();
				
				if (transactionDate == undefined ) {
					transactionDate = "";
				} if (fromDate == undefined ) {
					fromDate = "";
				} if (toDate == undefined ) {
					toDate = "";
				} if (accountHead == undefined) {
					accountHead = 0;
				}
				if (financialId == undefined ) {
					 financialId = "";
				}
				if (transactionTypeId == undefined ) {
					transactionTypeId = "";
				}
				if (registerDepTypeId == undefined ) {
					registerDepTypeId = "";
				}
				if (categoryId == undefined ) {
					categoryId = "";
				}
				if (paymodeId == undefined ) {
					paymodeId = "";
				}
				if (vendorId == undefined ) {
					vendorId = "";
				}
				if (fieldId == undefined ) {
					fieldId = "";
				}
				
				var selectedReportType = $('#reportTypeId option:selected').attr('code');
					if (selectedReportType != null && selectedReportType != ""
							&& selectedReportType == 'TRR') {
						if (transactionTypeId == "") {
							errorList.push(getLocalMessage('Select.Transaction.Type'));
							}
					}
					if (errorList.length > 0) {
						displayErrorsOnPage(errorList);
					}else if(selectedReportType=='NAB' || selectedReportType=='CFB' ||selectedReportType=='BSB' ||selectedReportType=='NMB' ||selectedReportType=='IEB'){
						var divName = '.content-page';
						$("#errorDiv").hide();
						var requestData = "";
						
							requestData = '&ReportType=' + selectedReportType;
						

						var URL = 'AccountFinancialReport.html?GetBirtReport';
						var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
						window.open(returnData, '_blank');
						window.open('AccountFinancialReport.html','_self');
					} 
					else {
				var requestData = {
						'reportTypeCode': selectedReportType,
						'transactionDate': transactionDate,
						'fromDate': fromDate,
						'toDate': toDate,
						'accountHeadId': accountHead,
						'financialId':financialId,
						'transactionTypeCode':transactionTypeId,
						'registerDepTypeId':registerDepTypeId,
						'categoryId':categoryId,
						'paymodeId':paymodeId,
						'vendorId':vendorId,
						'fieldId':fieldId
				}
				var ajaxResponse= __doAjaxRequestValidationAccor(obj,'AccountFinancialReport.html?report', 'POST', requestData, false, 'html');
				    if(ajaxResponse != false){
				    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
				    	if (hiddenVal == 'Y') {
				    		//var errorList =[];
				    		errorList.push('No record found!');
				    		displayErrorsOnPage(errorList);
				    		var selectedId = $('#reportTypeId').val();
				    		$('#reportTypeId').val(selectedId);
				    		$( "#reportTypeId" ).prop( "disabled", true );
				    		
				    	} else {
				    		$('#content').html(ajaxResponse);
				    	}
				    }
				}
			}
	showloader(false);
	},2);
}

/**
 * being used to display validation errors on page 
 * @param errorList : pass array of errors
 * @returns {Boolean} 
 */
function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	
	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	
	$('#errorDivId').html(errMsg);
	$("#errorDivId").show();
	
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}
function checkValidation()
{
	var errorList=[];
	
	var selectedId = $('#reportTypeId').val();
	if(selectedId==0 || selectedId=="" || selectedId == null) {
		errorList.push(getLocalMessage('acc.report.val.report.type'));
	}
	
	var type=$("#reportTypeId option:selected").attr("code");
	if(type == 'GCB'|| type == 'GBB'||type == 'JRB'||type == 'GLR'||type == 'TBR'|| type == 'RDP'|| type == 'CRR' || type == 'CAR'||type == 'INE'||type == 'RPR'||type == 'DYB' || type =='CDR' || type == 'CSR')
	{
	
		 if($("#fromDateId").val()=="" && $("#toDateId").val()=="")
			 {
			 errorList.push("Please Select From Date and To Date");
			 $( "#reportTypeId" ).prop( "disabled", true );
			 }
		 else if($("#fromDateId").val()!="" && $("#toDateId").val()=="")
			 {
			 errorList.push("Please Select To Date"); 
			 $( "#reportTypeId" ).prop( "disabled", true );
			 }
		 else if($("#fromDateId").val()=="" && $("#toDateId").val()!="")
			 {
			 errorList.push("Please Select From Date"); 
			 $( "#reportTypeId" ).prop( "disabled", true );
			 }
		  if($("#fromDateId").val()!=null)
			  {
			  errorList = validatedate(errorList,'fromDateId');
			  }
		  if($("#toDateId").val()!=null)
			  {
			  errorList = validatedate(errorList,'toDateId');
			  }
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($("#fromDateId").val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDateId").val().replace(pattern,'$3-$2-$1'));
		  if (eDate > sDate) {
			  errorList.push("To Date can not be less than From Date");
			  $( "#reportTypeId" ).prop( "disabled", true );
		    }
	
	if(errorList.length>0)
		{
	 var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
     errMsg += '</ul>';
      $('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
		}
	}
	if(type == 'RDP'){
		if($("#registerDepTypeId").val()==null || $("#registerDepTypeId").val()=="")
		 {
			errorList.push(getLocalMessage('Please.select.deposit.type'));
		 $( "#reportTypeId" ).prop( "disabled", true );
		 }
	}
	if(type == 'RBS'){
		if($("#registerDepTypeId").val()==null || $("#registerDepTypeId").val()=="")
		 {
			errorList.push(getLocalMessage('account.bill.entry.department'));
		 }
	}
	if(type == 'CRR'){
		
		if($("#categoryId").val()==null || $("#categoryId").val()=="")
		 {
		 errorList.push("Please select category.");
		 $( "#reportTypeId" ).prop( "disabled", true );
		 }
	}
	if(type == 'GCB'){
		
		if($("#paymodeId").val()==null || $("#paymodeId").val()=="")
		 {
			errorList.push(getLocalMessage('Please.select.type'));
		 $( "#reportTypeId" ).prop( "disabled", true );
		 }
	}
	
	if(type == 'TFC'){
		if($("#vmVendorname").val()==null || $("#vmVendorname").val()=="")
		 {
		 errorList.push("Please select vendor name.");
		 $( "#reportTypeId" ).prop( "disabled", true );
		 }
	}
	var transactionDate= $('#transactionDateId').val();
	if (transactionDate != undefined && transactionDate!=null ) {
		errorList = validatedate(errorList,'transactionDateId');
	}
	var fromDateId= $("#fromDateId").val();
	if (fromDateId != undefined && fromDateId!=null ) {
		errorList = validatedate(errorList,'fromDateId');
	}
	var toDateId= $('#toDateId').val();
	if (toDateId != undefined && toDateId!=null ) {
		errorList = validatedate(errorList,'toDateId');
	}
	
	if(toDateId != undefined && fromDateId != undefined )
		{
	  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	  var eDate = new Date($("#fromDateId").val().replace(pattern,'$3-$2-$1'));
	  var sDate = new Date($("#toDateId").val().replace(pattern,'$3-$2-$1'));
	  if (eDate > sDate) {
		  errorList.push("To Date can not be less than From Date");
		  $( "#reportTypeId" ).prop( "disabled", true );
	    }
		}
	
     if(type == 'RBS'){
		if($("#categoryId").val()==null || $("#categoryId").val()=="")
		        errorList.push(getLocalMessage('account.bill.entry.exp.functionId'));
	
		 $( "#reportTypeId" ).prop("disabled", true);
		}
	
	if(errorList.length>0)
	{
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
    errMsg += '</ul>';
    $('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
	}
	
	return true;
}

//Print Div
function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title>'+title+'</title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>') 
	printWindow.document.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, .tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style"); $(".table .tfoot").addClass("hide");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport1" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write('<style>    .width7 {width: 8%;}.width47 {width: 42%;}.width18 {width: 19.5%;} tr, td {border: 1px solid; padding: 5px;}</style>');
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
} 

function backAbstractForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdvanceRegister.html');
	$("#postMethodForm").submit();
}

$(document).ready(function() {
	$('#importexcel tr th[data-sorter="false"]').css('background-image','none');
 });
