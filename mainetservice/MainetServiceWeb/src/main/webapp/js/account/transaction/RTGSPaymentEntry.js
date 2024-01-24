$(function() { 
	$("#RTGSPaymentEntryGrid").jqGrid(
			
			{   
				url : "RTGSPaymentEntry.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('accounts.payment.no'),getLocalMessage('advance.management.master.paymentdate'),getLocalMessage('accounts.receipt.drawn_on'),getLocalMessage('advance.management.master.paymentamount'),getLocalMessage('bill.action'),''],
				colModel : [ {name : "paymentId",  hidden :  true},
				             {name : "paymentNo",search :false},
				             {name : "transactionDate",search :true}, 
				             {name : "bankacname", sortable :  true},
				             {name : "paymentAmount",search :false, align: 'right !important',formatter: 'number', formatoptions: { decimalPlaces: 2 }}, 
				             {name: 'enbll', index: 'enbll',  align: 'center !important',formatter:link,search:false},
				             {name : "viewURL",hidden :  true},
				            ],
				pager : "#RTGSPaymentEntryGridPager",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "code",
				sortorder : "desc",
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : true,
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,					
				},
				autoencode : true,
				caption :getLocalMessage('account.rtgsPayment.tbleHeading')
			});
	       $("#RTGSPaymentEntryGrid").jqGrid('navGrid','#RTGSPaymentEntryGridPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");
});

$(function() { 
	
	$("#baAccountidPay").chosen();	
	$('.required-control').next().children().addClass('mandColorClass').attr("required",true);

var templateExistFlag = $("#templateExistFlag").val();
var errorList = [];
	if(templateExistFlag=="N"){
		errorList.push(getLocalMessage('account.paymentVoucher.not.defined'));
		displayErrorsPage(errorList);
	}
	

	
	
	/*$("#RTGSPaymentEntryGrid").jqGrid({   
				url : "RTGSPaymentEntry.html?getGridData",
				datatype : "json",
				colNames : [ '','Payment No.','Payment Date', 'Vendor Name', 'Payment Amount','Action'],
				colModel : [ {name : "paymentId",width : 0,  hidden :  true},
				             {name : "paymentNo",width : 50,search :true,sortable :  true},
				             {name : "transactionDate",width : 50,search :true,align:'center'}, 
				             {name : "vendorName",width:50, search:true, sortable:true,align:'right'},
				             {name : "paymentAmount",width : 200,search :false}, 
				             {name: 'enbll', index: 'enbll', width: 50, align: 'center !important',formatter:link,search:false},
				            ],
				pager : "#RTGSPaymentEntryGridPager",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "code",
				sortorder : "desc",
				multiselect: true,
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : true,
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,					
				},
				autoencode : true,
				caption : 'Payment Entry Records',
			});
	       $("#RTGSPaymentEntryGrid").jqGrid('navGrid','#RTGSPaymentEntryGridPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");*/
	       
	       
	       
	
	
	
	
$('#createBtn').click(function(){
	
	 var ajaxResponse = __doAjaxRequest('RTGSPaymentEntry.html?createPage', 'POST', {}, false,'html');
	 $('#content').html(ajaxResponse);
});	



/**
 * being used to search records for reversal
 */     
$('#searchBtn').click(function() {
	
	 var errorList = validateSearchInput();
	 if (errorList.length == 0) {
		 var formURL = 'RTGSPaymentEntry.html';
		 var requestData = $('#RTGSPaymentEntry').serialize();
		 console.log('requestData'+requestData);
	 	var ajaxResponse = __doAjaxRequest(formURL+'?searchForPaymentEntry', 'POST', requestData, false,'json');
	 	if ( ajaxResponse == 'Internal Server Error.') {
	 		// display error page
		 } else if (ajaxResponse =='N') {
			 var errorList = [];
			 errorList.push("No record Found.");
			 displayErrorsOnPage(errorList);
			 $("#paymentEntryGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		 } else {
			 $("#paymentEntryGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 $("#errorDivId").hide();
			// $('#actionURL').val(ajaxResponse);
//			 var rowids = $('#voucherReversalGrid').jqGrid('getDataIDs');
//			 alert('rowids='+rowids);
		 }
	 } else {
		 displayErrorsOnPage(errorList); 
	 }

});	


});


function link(cellvalue, options, rowObject) 
{
	//map[rowObject.primaryKey] = rowObject.reversedOrNot;
	//transactionMap[rowObject.primaryKey] =rowObject.transactionNo; 
   return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewRTGSPaymentDetails('"+rowObject.paymentId+"','"+rowObject.viewURL+"')\"><i class='fa fa-building-o'></i></a> " +
   "<a class='btn btn-blue-3 btn-sm' title='Print' onclick=\"RTGSprintdiv('"+rowObject.paymentId+"','"+rowObject.viewURL+"')\"><i class='fa fa-print'></i></a> ";
}


/**"printdiv('receipt');"
 * used to open form in view mode
 * @param gridId
 */

function viewRTGSPaymentDetails(gridId, viewURL){
	
		var divName	=	formDivName;
		var url = "RTGSPaymentEntry.html?viewRTGSPaymentEntryDetail";
	    var requestData = 'gridId='+gridId;
	    var ajaxResponse = __doAjaxRequest(url, 'GET', requestData, false,'html');
		$('#frmdivId').html(ajaxResponse);
}
	
function RTGSprintdiv(gridId, viewURL){
	
	var divName	=	formDivName;
	var url = "RTGSPaymentEntry.html?RTGSPaymentGridPrintForm";
    var requestData = 'gridId='+gridId;
    var ajaxResponse = __doAjaxRequest(url, 'GET', requestData, false,'html');
	$('#frmdivId').html(ajaxResponse);
}

function validateSearchInput() {
	
	var errorList = [];
	var paymentDate = $('#paymentDate').val();
	var paymentAmount = $('#paymentAmount').val();
	//var vendorId = $('#vendorId').val();
	//var sacHeadId = $('#sacHeadId').val();
	var paymentNo = $('#paymentNo').val();
	var bankAcId = $('#bankAcId').val();
	var count = 0;
	if (paymentDate != undefined && $.trim(paymentDate) != '') {
		errorList = validatedate(errorList,'paymentDate');
	}
	if (paymentDate != undefined && $.trim(paymentDate) != '') {
		count++;
	} if (paymentAmount != undefined && $.trim(paymentAmount) != '') {
		count++;
	} /*if (vendorId != undefined && vendorId != '0') {
		count++;
	} if (sacHeadId != undefined && sacHeadId != '0') {
		count++;
	}*/ if (paymentNo != undefined && $.trim(paymentNo) != '') {
		count++;
	} if (bankAcId != undefined && bankAcId != '0') {
		count++;
	} if (count == 0) {
		errorList.push(getLocalMessage("account.searchBtn.validation"));
		//errorList.push('Please provide at least one search criteria.');
	}
	
	return errorList;
	
}

function resetBillType(){
	
	$("#bmBilltypeCpdId").val('0');
	$('#bmBilltypeCpdId').trigger('chosen:updated');
	paymentTypeWiseBillType();
}

function getBillNumbers(count){

	var errorList = [];
	
	var billTypeId = $("#bmBilltypeCpdId").val();
	var paymentDate = $("#transactionDateId").val();
	var vendorId =  $("#vendorId"+count).val();
	
	if ((paymentDate == "" || paymentDate =="0")) {
		errorList.push('Please select payment Date');
	}
	  /*commented below field against id #133284 */
	/*if ((billTypeId == "" || billTypeId =="0")) {
		errorList.push('Please select payment order type first.');
	}*/
	
	if (errorList.length == 0) {
		
		$('#errorDivId').hide();
		var postData = __serializeForm("#RTGSPaymentEntry");
		//var postData = 'billTypeId=' + billTypeId + '&vendorId=' + vendorId + '&paymentDate=' + paymentDate;
		
		var url = "RTGSPaymentEntry.html?checkPaymentDateisExists";
		var returnData = __doAjaxRequest(url, 'post', postData, false);
		if (returnData) {
			errorList.push("Bill's not available for payment against selected payment date.");
			displayErrorsPage(errorList);
			$("#transactionDateId").val("");
			//$("#bmBilltypeCpdId").val('0');
			//$('#bmBilltypeCpdId').trigger('chosen:updated');
			$("#bmBilltypeCpdId").prop( "disabled", false );
			$("#bmBilltypeCpdId").trigger('chosen:updated');
			$("#transactionDateId" ).prop( "disabled", false );
			$("#vendorId"+count).val('0');
			$('#vendorId'+count).trigger('chosen:updated');
			//$("#vendorId"+count).prop( "disabled", false );
			//$("#vendorId").trigger('chosen:updated');
			//$("#bmBilltypeCpdId").trigger('chosen:updated');
			//$("#paymentTypeId").prop("disabled", false).trigger('chosen:updated');
		} 
	} if (errorList.length == 0) {
		$('#errorDivId').hide();
		/*commented below field against id #133284 for not required the billTypeId so passing dummi value as 0*/
		billTypeId=0;
		var postData = 'billTypeId=' + billTypeId + '&vendorId=' + vendorId + '&paymentDate=' + paymentDate;
		var url = "RTGSPaymentEntry.html?getBillNumbers";
		var response = __doAjaxRequest(url, 'post', postData, false,"json");
		if (jQuery.isEmptyObject(response)) {
			errorList.push('No Bills are available against selected Vendor Name and Payment Order Type.');
			displayErrorsPage(errorList);
			$("#vendorId"+count).val('0');
			$('#vendorId'+count).trigger('chosen:updated');
			//$("#vendorId"+count).prop( "disabled", false );
			$("#transactionDateId" ).prop( "disabled", false );
			$("#bmBilltypeCpdId" ).prop( "disabled", false );
			$("#bmBilltypeCpdId").trigger('chosen:updated');
			//$("#bmBilltypeCpdId").trigger('chosen:updated');
			//$("#paymentTypeId").prop("disabled", false).trigger('chosen:updated');
		} else {
			
			$('#id'+count).text('');
			$('#id'+count).append($('<option>', { 
		        value: '0',
		        text : getLocalMessage("account.select")
			   }));
			$.each(response, function(key, value) {
				$('#id'+count).append($('<option>', { 
			        value: key,
			        text : value 
			    }));
				$('#id'+count).trigger("chosen:updated");
			});
			/*$.each(response, function(key, value) {
				  $('#id'+count).append($("<option></option>").attr("value",key).text(value));
			});*/
			//$('#id0').trigger('chosen:updated');
			//var vendorDesc = $('#vendorId :selected').text();
			//$('#vendorDesc').val(vendorDesc);
			$("#transactionDateId" ).prop( "disabled", true );
			//$("#vendorId"+count).prop( "disabled", true );
			//$("#vendorId"+count).trigger('chosen:updated');
			//$("#vendorId").prop("data-rule-required",false);
			$("#bmBilltypeCpdId").prop( "disabled", true ); 
			$("#bmBilltypeCpdId").trigger('chosen:updated');
			//$("#paymentTypeId").prop("disabled", true).trigger('chosen:updated');
		}
	} else {
		$("#vendorId"+count).val('0');
		$('#vendorId'+count).trigger('chosen:updated');
		//$("#vendorId"+count).prop( "disabled", false );
		$("#transactionDateId" ).prop( "disabled", false );
		$("#bmBilltypeCpdId").prop( "disabled", false );
		$("#bmBilltypeCpdId").trigger('chosen:updated');
		//$("#paymentTypeId").prop("disabled", false).trigger('chosen:updated');
		displayErrorsPage(errorList);
	}
	// $('#bmBilltypeCpdId').prop("disabled", true);
}

function validateOnBillType() {
	
}


function getBillData(cnt){
	
	var errorList = [];
	$('#paymentAmount' + cnt).val("");
	
	var billTypeId = $("#bmBilltypeCpdId").val();
	//var billTypeId = $("#bmBilltypeCpdId").val();
	/*if ((billTypeId == "" || billTypeId =="0" || billTypeId == null)) {
		errorList.push('Please Select Bill Type');
		$("#id"+cnt).val("");
		$('#id'+cnt).trigger('chosen:updated');
	}*/
	if(errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
		
		$('.error-div').hide();
		var errorList = [];
		getBillSumaryWiseData(cnt);
		checkBudgetBillSumaryWiseData(cnt);
		calcTotalAmount();
	}
}



function viewBillDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	//var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var billId = $("#id"+count).val();
	//var vendorId = $("#vendorId"+count).val();
	//var entryDate = $('#transactionDateId').val();
	//var paymentAmt = $('#paymentAmount' + count).val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (billId == "" || billId == null || billId == 0){
		errorList.push("Please select payment order number");
	}
	/*if (paymentAmt == 0 || paymentAmt == ""){
		errorList.push(getLocalMessage('Please enter payment amount'));
	}*/

	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}else {
		
		//var gr = jQuery("#RTGSPaymentEntryGrid").jqGrid('getGridParam','selrow');	
		
		var url = "RTGSPaymentEntry.html?getRTGSBillEntryViewData";
		
		//var url = "AccountChequeDishonour.html?viewData";
		//var string = $(this).attr('value');
		//var d = string.split(","); 
		//var chequeDishonourId=Number.parseInt(d[0]);
		//var docGroup=d[1];
		
		var returnData = {"billId" : billId};
		
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
			data : returnData,
			success : function(response) {
				//$('.content').html(response);
				//prepareDateTag();
				//return false;
				var divName = '.child-popup-dialog';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				showModalBox(divName);
				//$('.content').html(response);
				//prepareDateTag();
				//return false;
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				
				showError(errorList);
			}
		});		
	}
}

function viewPaymentBillDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	//var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var billId = $("#bchId"+count).val();
	var entryDate = $('#paymentEntryDate').val();
	var paymentAmt = $('#paymentAmount' + count).val().replace(",","");
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (billId == "" || billId == null){
		errorList.push("Please select bill number");
	}
	
	if (paymentAmt == 0 || paymentAmt == ""){
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
	}

	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

	// });
	else {

		var postData = 'billId=' + billId + '&entryDate='
				+ entryDate + '&paymentAmt=' + parseInt(paymentAmt) + '&count='
				+ count;
		var url = "AccountBillEntry.html?viewPaymentExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);
		
		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {
			
			//$('#paymentAmount' + count).val("");
			var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();
			if(bugDefParamStatusFlag == 'Y'){
				$('#bchId' + count).val("").trigger('chosen:updated');
			}
			var errorList = [];
			errorList.push(errorMsg);
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			
		} else {
			//var divName = '.child-popup-dialog';
			var divName = '.popUp';
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			$('#expActAmt0').val(paymentAmt);
			// prepareTags();
			//$(".popUp").append('#expDetailTable tbody tr td:nth-child(10)');
			//showModalBox(divName);
			$(".popUp").show();
			$(".popUp").draggable();
		}
	}
}

function viewBugdetPaymentDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	//var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var billId = $("#bchId"+count).val();
	var entryDate = $('#transactionDateId').val();
	var paymentAmt = $('#paymentAmount' + count).val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (billId == "" || billId == null || billId == 0){
		errorList.push("Please select expenditure head.");
	}
	
	if (paymentAmt == 0 || paymentAmt == ""){
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
	}

	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

	// });
	else {

		var postData = 'billId=' + billId + '&entryDate='
				+ entryDate + '&paymentAmt=' + parseInt(paymentAmt) + '&count='
				+ count;
		var url = "AccountBillEntry.html?viewPaymentExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);
		
		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {
			
			var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();
			if(bugDefParamStatusFlag == 'Y'){
				$('#paymentAmount' + count).val("");
				$("#totalAmount").val("");
				$('#bchId' + count).val("").trigger('chosen:updated');
			}
			var errorList = [];
			errorList.push(errorMsg);
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			
		} 
	}
}

/*function viewBillDetails(count){
	
	var billId = $("#id"+count).val();
	var errorList = [];
	if(billId!=null && billId!=""){
		var postData = '&bmId=' + billId+ "&MODE_DATA=" + "paymentEntryView";
		var url = "AccountBillEntry.html?formForView";
		var response = __doAjaxRequest(url, 'post', postData, false);
		$('.content').html(response);
	}
	else{
		errorList.push("Please select bill number");
		displayErrorsPage(errorList);
	}
}*/

function viewBillDetailsForView(count){
	
	var billId = $("#billId"+count).val();
	
	var errorList = [];
	if(billId!=null && billId!=""){
		var postData = '&bmId=' + billId+ "&MODE_DATA=" + "paymentEntryView";
		var url = "AccountBillEntry.html?formForView";
		var response = __doAjaxRequest(url, 'post', postData, false);
		$('.content').html(response);
	}
	else{
		errorList.push("Please select bill number");
		displayErrorsPage(errorList);
	}
}



//to generate dynamic row
$(function() {

	$("#billDetailTable").unbind('click').on("click",'.addButton11',function(e) {
	
	var content = $(this).closest('#billDetailTable tr').clone();
	$(this).closest("#billDetailTable").append(content);
	content.find("select").attr("value", "");
	content.find("input:text").val("");
	content.find("input:checkbox").attr("checked", false);
	content.find('div.chosen-container').remove();
	content.find("select:eq(0)").chosen().trigger("chosen:updated");
	content.find('label').closest('.error').remove(); //for removal duplicate
	reOrderTableIdSequence();
	//e.preventDefault();

	});

//to delete row
$("#billDetailTable").on("click", '.delButton', function(e) {
	
	var rowCount = $('#billDetailTable tr').length;
	if (rowCount <= 2) {
		return false;
	}
	$(this).closest('#billDetailTable tr').remove();

	var billAmountTotal = 0;
	var rowCount = $('#billDetailTable tr').length;

	dynamicTotalBilltAmount(rowCount);
	calcTotalAmount();
	//getNetPayable();
	reOrderTableIdSequence();
	e.preventDefault();
});

$('#resetId').click(function(){
	$('#RTGSPaymentEntry').trigger("reset");
	$('.chosenReset').chosen().trigger("chosen:updated");
});

});

function dynamicTotalBilltAmount(rowCount){

    	var amount=0; 
		for (var m = 0; m <= rowCount-1; m++) {
			   var  n= parseFloat(parseFloat($("#paymentAmount" + m).val()));
			    if (isNaN(n)) {
			       n=0;
			    }
			    amount += n ;
		$("#totalAmount").val(parseFloat(amount.toFixed(2)));
		getAmountFormatInStatic('totalAmount');
		$("#totalAmountHidden").val(parseFloat(amount.toFixed(2)));
		getAmountFormatInStatic('totalAmountHidden');
		}
}

function totalPaymentamount(){
	
    var amount=0; 
	var count = $('.billDetailTableClass tr').length;
	for (var m = 0; m <= count -1; m++) {
			var  n= parseFloat(parseFloat($("#paymentAmount" + m).val()));
		    if (isNaN(n)) {
		    	return n=0;
		    }
		    amount += n ;

		    $("#totalAmount").val(parseFloat(amount.toFixed(2)));
		    getAmountFormatInStatic('totalAmount');
		    $("#totalAmountHidden").val(parseFloat(amount.toFixed(2)));
		    getAmountFormatInStatic('totalAmountHidden');
		}
}


function reOrderTableIdSequence(){

	$('.billDetailClass').each(function(i) {
		
		//IDs
		$(this).find("select:eq(0)").attr("id",	"vendorId" + i);
		$(this).find("select:eq(1)").attr("id",	"id" + i);
		
		$(this).find("input:hidden:eq(0)").attr("id",	"billTypeId" + i);
		//$(this).find("select:eq(1)").attr("id",	"bchId" + i);
		//$(this).find("input:text:eq(1)").attr("id",	"amount" + i);
		//$(this).find("input:text:eq(2)").attr("id",	"deductions" + i);
		$(this).find("input:text:eq(1)").attr("id",	"netPayable" + i);
		$(this).find("input:text:eq(2)").attr("id",	"paymentAmount" + i);
		//$(this).find("input:checkbox:eq(0)").attr("id",+ i);
		$(this).find(".viewBill").attr("id", "viewBillDet" + i);
		
		//names
		$(this).find("select:eq(0)").attr("name","rtgsPaymentDetailsDto["+ i + "].vendorId");
		$(this).find("select:eq(1)").attr("name","rtgsPaymentDetailsDto["+ i + "].id");
		//$(this).find("select:eq(1)").attr("name","paymentDetailsDto["+ i + "].bchId");
		//$(this).find("input:text:eq(1)").attr("name","paymentDetailsDto["+ i + "].amount");
		//$(this).find("input:text:eq(2)").attr("name","paymentDetailsDto["+ i + "].deductions");
		$(this).find("input:text:eq(1)").attr("name","rtgsPaymentDetailsDto["+ i + "].netPayable");
		$(this).find("input:text:eq(2)").attr("name","rtgsPaymentDetailsDto["+ i + "].paymentAmount");
		//$(this).find("input:checkbox:eq(0)").attr("name","selectBills"+i);
		$(this).find("input:hidden:eq(0)").attr("name","rtgsPaymentDetailsDto["+ i + "].billTypeId");
		//functions
		$(this).find("select:eq(0)").attr("onchange", "getBillNumbers(" + i + ")");
		$(this).find("select:eq(1)").attr("onchange", "getBillData(" + i + ")");
		//$(this).find("select:eq(1)").attr("onchange", "getExpenditureAccountHeadData(" + i + ")");
		//$(this).find("input:checkbox:eq(0)").attr("onchange", "getPayAmount(" + i + ")");
		$(this).find(".viewBill").attr("onclick", "viewBillDetails(" + i + ")");
		$(this).find('#paymentAmount'+i).attr("onchange", "validatePaymentAmount(" + (i) + ")");
	});
}

function validateOnAddRow() {
	
}

$(function() { 
$("#selectAllBills").click(function () {
	   
    var status = this.checked; // "select all" checked status
    $('.selectBill').each(function(){ //iterate all listed checkbox items
        this.checked = status; //change ".checkbox" checked status
    });
    var totalAmount=0;
    $('.billDetailClass').each(function(i) {
  	  var amount=parseFloat($("#paymentAmount"+i).val().replace(/,/g,''));
  	  if (isNaN(amount)) {
    	amount=0;
  	  }
  	  totalAmount=totalAmount+amount;
  	  $("#totalAmount").val(totalAmount);
  	getAmountFormatInStatic('totalAmount');
  	  $("#totalAmountHidden").val(totalAmount);
  	getAmountFormatInStatic('totalAmountHidden');
    });				
    if($("#selectAllBills:checked").length==0)
	  {
	  $("#totalAmount").val(0.00);
	  $("#totalAmountHidden").val(0.00);
	  }
});

});

function getPayAmount(count){
   
	
    var amount=parseFloat($("#paymentAmount"+count).val().replace(/,/g,''));
    if (isNaN(amount)) {
    	amount=0;
	}
	if($("#totalAmount").val()!=undefined && $("#totalAmount").val()!=""){
	  var totalAmount=parseFloat($("#totalAmount").val());
	}
	else{
		var totalAmount=0;
	}
	if($('input[id="'+count+'"]:checked').length >0) {
		if (isNaN(totalAmount)) {
			totalAmount=0;
		}
		
	     totalAmount=totalAmount+amount;
	     $("#totalAmount").val(totalAmount);
	     getAmountFormatInStatic('totalAmount');
	     $("#totalAmountHidden").val(totalAmount);
	     getAmountFormatInStatic('totalAmountHidden');
	} else {
		if (isNaN(totalAmount)) {
			totalAmount=0;
		}
		
	     totalAmount=totalAmount-amount;
	     $("#totalAmount").val(totalAmount);
	     getAmountFormatInStatic('totalAmount');
	     $("#totalAmountHidden").val(totalAmount);
	     getAmountFormatInStatic('totalAmountHidden');
	}
}


$(function() {
	/*$(document).on('click', '.proceedPayment', function() {
		
		//var requestData = "MODE_DATA=" + "EDIT";
		var url = "RTGSPaymentEntry.html?proceedPayment";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		prepareDateTag();
		return false;
	});*/
});


function getChequeNos(){
	
var errorList = [];

	var transactionDate = $('#transactionDateId').val();
	
	if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
		errorList.push(getLocalMessage('account.select.paymentDate'));
		$("#baAccountidPay").val('').trigger('chosen:updated');
	}
	
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
		
	var bankAcId = $('#baAccountidPay').val();
	var data = "&bankAcId="+bankAcId;
	var amountToPay = $('#totalAmount').val();
	var paymentMode=$("#paymentMode option:selected").attr("code");
	
	if(paymentMode == "Q" || paymentMode == "RT" || paymentMode == "B"){
	
		$('#chequebookDetid').find('option:gt(0)').remove();
		var response = __doAjaxRequest('ContraVoucherEntry.html?getChequeNumbers','POST',data,false,'json');
		
		if(bankAcId!='' && bankAcId!=null && bankAcId!='undefined'){
			var balData = "&bankAcId="+bankAcId +"&transactionDate="+transactionDate;
			var responseBal = __doAjaxRequest('ContraVoucherEntry.html?getBankBalance','POST',balData,false,'json');
		}
		$('#bankBalance').val(responseBal);
		var allow=getLocalMessage('account.payment.allow.neg.bal');
		if ((parseInt(amountToPay) > parseInt(responseBal))&& allow!='Y') {
			errorList.push("Bank balance is insufficient : Current Bank A/c Balance is -> "+ responseBal +"");
			$("#baAccountidPay").val('').trigger('chosen:updated');
			displayErrorsPage(errorList);
		}else{
			var chequeNoMap = '';
			$.each(response,function(key,value){
				chequeNoMap += "<option value='" +key+"' selected>" + value + "</option>";
			});
			$('#chequebookDetid').append(chequeNoMap);
			$('#chequebookDetid').val('');
			$('#errorDivId').hide();
			}
		}
	}
}

function proceedForPayment(element){

	var errorList = [];
	errorList = validateListForm(errorList);
	if (errorList.length == 0) {
		var url = "RTGSPaymentEntry.html?proceedPayment";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		
		//var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var returnData = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'html');
		if(returnData!=false){
			$('.content').html(returnData);
			prepareDateTag();
			return false;
			var totalAmount = $("#totalAmount").val();
			$("#amountToPay").val(totalAmount);
		}
	}
	else {
		displayErrorsPage(errorList);
	}
}

function validateListForm(errorList){
	
	var bmBilltypeCpdId = "";
	var paymentTypeCodeP = $("#paymentTypeId option:selected").attr("code"); 
	if(paymentTypeCodeP == 'P'){	
		bmBilltypeCpdId = $("#bmBilltypeCpdIdP").val();
	}else{
		bmBilltypeCpdId = $("#bmBilltypeCpdId").val();
	}
	
	//var bmBilltypeCpdId = $("#bmBilltypeCpdId").val();
	if (bmBilltypeCpdId == undefined|| bmBilltypeCpdId == '0' || bmBilltypeCpdId == '') {
			errorList.push(getLocalMessage('account.billtype'));
		}
		var vmVendorname = $("#vendorId").val();
		if (vmVendorname == undefined || vmVendorname == '0' || vmVendorname == '') {
			errorList.push(getLocalMessage('account.select.name'));
		}
		
		/*	var rowCount = $('.billDetailTableClass tr').length;
		for (var i = 0; i < rowCount - 1; i++) {
			
			var billNo = $('#id' + i).val();
			if (billNo == "" || billNo == null) {
				errorList.push(getLocalMessage('Please select bill number'));
			}
			var paymentAmount = $("#paymentAmount" + i).val();
			if (paymentAmount == "" || paymentAmount == null) {
				errorList.push(getLocalMessage('Please enter payment amount'));
			}
			
			if($('input[name="selectBills'+i+'"]:checked').length < 0){
				errorList.push(getLocalMessage('Please select bill to make payment'));
			}
		}*/
		$('.billDetailClass').each(function(j) {
					for (var m = 0; m < j; m++) {
						if (( ($('#id' + m).val() == $('#id' + j).val()) ) && ( $('#id' + m).val()!="" &&$('#id' + j).val()!="") ) {
							errorList.push("Bill number cannot be same,please select another bill number");
						}
					}
			});
		
		var totalAmount = $("#totalAmount").val();
		if (totalAmount == "" || totalAmount == null) {
			errorList.push(getLocalMessage('account.totalAmt.not.empty'));
		}
	return errorList;
}

function displayErrorsPage(errorList) {

	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}


function savePaymentEntry(element) {
	
	var errorList = [];
	
	errorList = validateForm(errorList);
	
	var totalAmount = $("#totalAmount").val();
	if(totalAmount != null && totalAmount !="" && totalAmount != undefined){
		if (totalAmount == "0" || totalAmount == "0.00") {
			errorList.push(getLocalMessage('account.enter.paymentAmt'));
		}
	}else{
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
	}
	
	var from = $("#transactionDateId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#instrumentDate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	$('.billDetailClass').each(function(j) {
		
		for (var m = 0; m < j; m++) {
			if (($('#vendorId' + m).val() == $('#vendorId' + j).val()) && ($('#id' + m).val() == $('#id' + j).val())) {
				errorList.push("Vendor name and payment order number cannot be same,please select another payment order number vendor name");
			}
		}
		/*var paymentType=$("#paymentTypeId option:selected").attr("code");
		if (paymentType != "W") {
			for (var m = 0; m < j; m++) {
				if (($('#id' + m).val() == $('#id' + j).val()) && ($('#bchId' + m).val() == $('#bchId' + j).val())) {
					errorList.push("Bill number and Expenditure cannot be same,please select another bill number expenditure head");
				}
			}
		}*/
	});
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Instrument Date should not be less than Payment Date");
		}
	}
	
	var totalAmount = $("#totalAmount").val();
	var sumOfDedctionAmt = 0;
	var rowCount = $('#billDetailTable tr').length;
	
	for (var i = 0; i < rowCount - 1; i++) {
		sumOfDedctionAmt += parseFloat($('#paymentAmount' + i).val().replace(/,/g,''));
	}
	
	if((sumOfDedctionAmt != null) && (sumOfDedctionAmt != "") && (!isNaN(sumOfDedctionAmt)) && (sumOfDedctionAmt != undefined) ){
		if(parseFloat(sumOfDedctionAmt) != parseFloat(totalAmount)){
			errorList.push(getLocalMessage('account.discrepancy.found.paymentAmt'));
		}
	}
	
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	//errorList = validatePaymentForm(errorList);
	if (errorList.length == 0) {
		
		showConfirmBoxSave();
	}
}

function showConfirmBoxSave(){
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+' </h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>  '+ 
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsg(){
	
	//var vendorId =  $("#vendorId").val();
	//$("#dupVendorId").val(vendorId);
	var transactionDateId =  $("#transactionDateId").val();
	$("#dupTransactionDate").val(transactionDateId);
	var bmBilltypeCpdId = $("#bmBilltypeCpdId").val();
	//var bmBilltypeCpdId =  $("#bmBilltypeCpdId").val();
	$("#dupBillTypeId").val(bmBilltypeCpdId);	
	$("#paymentTypeId").prop("disabled", false);
	
	var url = "RTGSPaymentEntry.html?create";
	//var formName = findClosestElementId(element, 'form');
	//var theForm = '#' + formName;
	//var requestData = __serializeForm(theForm);
	var requestData = $('#RTGSPaymentEntry').serialize();
	
	var status = __doAjaxRequestForSave(url, 'post', requestData, false,'', '');
	//var status = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'json');
	if (status != false) {
		if(status != null && status != '' && status != undefined) {
			showConfirmBox(status);
		}else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	} else {
		displayErrorsPage(errorList);
	}
	/*if(status!=false){
		var obj = $(status).find('#successfulFlag');
		if ($('.content').html(status)) {
			if (obj.val() == 'Y') {

				showConfirmBox();
			}
		} else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	}*/
/*} else {
	displayErrorsPage(errorList);
}*/

}

function validateForm(errorList) {
	
	var transactionDateId = $("#transactionDateId").val();
	if (transactionDateId == "" || transactionDateId == null) {
		errorList.push(getLocalMessage('account.select.paymentDate'));
	}
	if(transactionDateId !=null)
		{
		errorList = validatedate(errorList,'transactionDateId');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#transactionDateId").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Payment date can not be less than SLI date");
			  }
			}
		  }
		}
	/*var vendorDesc = $("#vendorDesc").val();
	if (vendorDesc == "" || vendorDesc == null) {
		errorList.push(getLocalMessage('Please enter vendor name'));
	}*/
	
	var billTypeId = $("#bmBilltypeCpdId").val();
	//var billTypeId = $("#bmBilltypeCpdId").val();
	 /*commented below field against id #133284*/
	/*if (billTypeId == "" || billTypeId == null) {
		errorList.push(getLocalMessage('account.select.paymentOrderType'));
	}*/
	
	$('.billDetailClass').each(function(j) {
		
		for (var m = 0; m <= j; m++) {
			
			var vendorId = $('#vendorId' + m).val();
			if (vendorId == "" || vendorId == null) {
				errorList.push(getLocalMessage('account.vendorname'));
			}
			
			var budgetCode = $('#id' + m).val();
			if (budgetCode == "" || budgetCode == null) {
				errorList.push(getLocalMessage('account.select.payment.orderNo'));
			}
			
			/*var paymentType=$("#paymentTypeId option:selected").attr("code");
			if (paymentType != "W") {
				var bchId = $('#bchId' + m).val();
				if (bchId == "" || bchId == null || bchId == 0) {
					errorList.push(getLocalMessage('Please select expenditure head.'));
				}
			}*/
			var paymentAmount = $("#paymentAmount" + m).val();
			if (paymentAmount == "" || paymentAmount == null || paymentAmount == 0 || paymentAmount == 0.00) {
				errorList
						.push(getLocalMessage('account.enter.paymentAmt.rtgs.paymentDetails'));
			}
		}
	});

	var paymentMode = $("#paymentMode").val();
	if (paymentMode == "" || paymentMode == null) {
		errorList.push(getLocalMessage('account.select.paymentMode'));
	}
	
	var option=$("#paymentMode option:selected").attr("code");
    if (option=='Q' || option=='RT')
    	{
    	var baAccountidPay = $.trim($("#baAccountidPay").val());
    	if (baAccountidPay == null || baAccountidPay == ""){
    		errorList.push(getLocalMessage('account.select.bankAccount'));
    	}
    	var chequebookDetid = $.trim($("#chequebookDetid").val());
    	if (chequebookDetid == null || chequebookDetid == ""){
    		errorList.push(getLocalMessage('account.enter.instrument.no'));
    	}
    	var instrumentDate = $.trim($("#instrumentDate").val());
       	if (instrumentDate == null || instrumentDate == ""){
    		errorList.push(getLocalMessage('account.select.instrument.date'));
    	}
    }else if(option=='B'){
    	  /*commented below field against id #133284 */
    	/*var utrNo = $.trim($("#utrNumber").val());
    	if (utrNo == null || utrNo == ""){
    		errorList.push(getLocalMessage('account.enter.UTR.no'));
    	}*/
    	var baAccountidPay = $.trim($("#baAccountidPay").val());
    	if (baAccountidPay == null || baAccountidPay == ""){
    		errorList.push(getLocalMessage('account.select.bankAccount'));
    	}
    }else{
    	
    }
    
	/*var amountToPay = $("#totalAmount").val();
	if (amountToPay == "" || amountToPay == null || amountToPay == 0 || amountToPay == 0.00) {
		errorList.push(getLocalMessage('Please enter payment amount'));
	}*/
	var bmNarration = $("#bmNarration").val();
	if (bmNarration == "" || bmNarration == null || bmNarration == 0) {
		errorList.push(getLocalMessage('account.narration'));
	}
	
	return errorList;
}

function showConfirmBox(msg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var msgText1 = getLocalMessage('accounts.receipt.receiptnumber');
	var msgText2 = getLocalMessage('advance.entry.success');
	var cls = getLocalMessage('account.proceed.btn');
	
	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'
			+ msgText1 +' '+ msg + ' ' + msgText2 + '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	//showModalBox(errMsgDiv);
	showModalBoxWithoutClose(errMsgDiv);
}


function proceed() {
	
	//window.location.href = 'RTGSPaymentEntry.html';
	var url = "RTGSPaymentEntry.html?paymentReportForm";
    var returnData = __doAjaxRequest(url, 'post', null, false);
	
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.widget').html(returnData);
}


function validatePaymentForm(errorList) {
	
	var amountToPay = $("#amountToPay").val();
	var bankBalance = $("#bankBalance").val();
	var allow=getLocalMessage('account.payment.allow.neg.bal');
	if((parseInt(amountToPay)>parseInt(bankBalance))&& allow!='Y'){
		errorList.push("Bank balance is insufficient");
	}else{
	
		var baAccountidPay = $("#baAccountidPay").val();
		if (baAccountidPay == "" || baAccountidPay == null) {
			errorList.push(getLocalMessage('account.select.bankAccount'));
		}
		var paymentMode = $("#paymentMode").val();
		if (paymentMode == "" || paymentMode == null) {
			errorList.push(getLocalMessage('account.select.paymentMode'));
		}
		
		var paymentModeTxt = $('#paymentMode :selected').text();
		if (paymentModeTxt != "Cash") {
			
			var chequebookDetid = $("#chequebookDetid").val();
			if (chequebookDetid == "" || chequebookDetid == null) {
				errorList.push(getLocalMessage('account.enter.instrument.no'));
			}
			var instrumentDate = $("#instrumentDate").val();
			if (instrumentDate == "" || instrumentDate == null) {
				errorList.push(getLocalMessage('account.select.instrument.date'));
			}
		}
		if (amountToPay == "" || amountToPay == null) {
			errorList.push(getLocalMessage('account.enter.paymentAmt'));
		}
		var bmNarration = $("#bmNarration").val();
		if (bmNarration == "" || bmNarration == null) {
			errorList.push(getLocalMessage('account.narration'));
		}
	
	}
	return errorList;
}

function toggleInstruments(){
	
	//var selectedMode = $('#paymentMode :selected').text();
	var selectedMode=$("#paymentMode option:selected").attr("code");
	
	if(selectedMode == 'C' || selectedMode == 'PCA'){
		$( "#chequebookDetid" ).prop( "disabled", true );
		$( "#instrumentDate" ).val('');
		$( "#instrumentDate" ).prop( "disabled", true );
		$( "#chequebookDetid" ).val('');
		$("#baAccountidPay" ).prop("disabled", true);
		$("#baAccountidPay").val('').trigger('chosen:updated');
		$("#baAccountidPay").val("");
	}else if(selectedMode == 'A'){
		$( "#chequebookDetid" ).prop( "disabled", true );
		$( "#instrumentDate" ).val('');
		$( "#instrumentDate" ).prop( "disabled", true );
		$( "#chequebookDetid" ).val('');
		$("#baAccountidPay" ).prop("disabled", true);
		$("#baAccountidPay").val('').trigger('chosen:updated');
		$("#baAccountidPay").val("");
	}else if(selectedMode == 'B'){
		$( "#chequebookDetid" ).prop( "disabled", true );
		$( "#instrumentDate" ).val('');
		$( "#instrumentDate" ).prop( "disabled", true );
		$( "#chequebookDetid" ).val('');
		$("#baAccountidPay" ).prop("disabled", false);
		$("#baAccountidPay").val('').trigger('chosen:updated');
		$("#baAccountidPay").val("");
	}else {
		$( "#chequebookDetid" ).prop( "disabled", false );
		$( "#instrumentDate" ).prop( "disabled", false );
		$("#baAccountidPay" ).prop("disabled", false);
		$("#baAccountidPay").val('').trigger('chosen:updated');
		
		var transactionDate = $("#transactionDateId").val();
		$('#instrumentDate').val(transactionDate); 
		
		//$(".datepicker").datepicker('setDate', new Date());
	}
}

function resetPaymentForm(){
	
	$('#baAccountidPay').val('').trigger('chosen:updated');
	$('#vendorId').val('').trigger('chosen:updated');
	$('#bmBilltypeCpdId').val('').trigger('chosen:updated');
	
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

function validatePaymentAmount(cont){
		
		//$('#totalAmount').val("");
		//$('#selectAllBills').attr('checked', false); // Unchecks it
		//$('.selectBill').attr('checked', false);
		var errorList = [];
		$("#paymentMode").val("");
		$("#utrNo").hide();
		$("#instrumentNo").show();
		
		//viewBugdetPaymentDetails(cont);
	
		getAmountFormatInStatic('paymentAmount'+cont);
	
		//var minAmt = $('#netPayable'+cont).val().toString().replace(",","");
		var minAmt = $('#netPayable'+cont).val().toString().replace(/\,/g, '');
		var maxAmt = $('#paymentAmount'+cont).val();
		
		if(minAmt == '0.00' || minAmt == '0' || minAmt == null || minAmt == ""){
			errorList.push("Selected Bill payment has been already done.");
			$('#paymentAmount'+cont).val("");
			$("#totalAmount").val("");
			displayErrorsPage(errorList);
			
		}else if (maxAmt != "" && maxAmt != null && maxAmt != undefined && !isNaN(maxAmt)){
		    try{
		      maxAmt = parseFloat(maxAmt);
		      minAmt = parseFloat(minAmt);

		      if(maxAmt > minAmt) {
		    	var msg = "Payment Amount should be less than balance/netPayable amount!";
		        showErrormsgboxTitle(msg);
				$('#paymentAmount'+cont).val("");
				$("#totalAmount").val("");
		        return false;
		      }
		    }catch(e){
		      return false;
		    }
		} //end maxAmt minAmt comparison 
}

function setInstrumentDate(obj){

	var transactionDate = $("#transactionDateId").val();
	$('#instrumentDate').val(transactionDate); 
	$('#paymentMode').val("");
}

function calcTotalAmount(){
	
	 var amount=0; 
		var rowCount = $('#billDetailTable tr').length;
			for (var m = 0; m <= rowCount - 1; m++) {
				
				   var  n= parseFloat(parseFloat($("#paymentAmount" + m).val().replace(/,/g,'')));
				    if (isNaN(n)) {
				        return n=0;
				    }
				    amount += n ;
				    
			var result = parseFloat(amount.toFixed(2));
			$("#totalAmount").val(result);			
			$("#totalAmountHidden").val(result);
			getAmountFormatInStatic('totalAmount');
			getAmountFormatInStatic('totalAmountHidden');
			}
}

function setPrevBalanceAmount(prExpId, amount, count) {
	$( ".popUp" ).hide();
}

function validateChequeDate(){

	var errorList = [];
	
	var transactionDate = $('#transactionDateId').val();
	if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
		errorList.push(getLocalMessage('account.select.paymentDate'));
		$("#instrumentDate").val("");
	}
	var paymentMode = $('#paymentMode').val();
	if (paymentMode == "" || paymentMode == null || paymentMode == undefined){
		errorList.push(getLocalMessage('account.select.paymentMode'));
		$("#instrumentDate").val("");
	}
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
	var from = $("#transactionDateId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#instrumentDate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Instrument date should not be greater than payment date");
			$("#instrumentDate").val("");
		}
	}
	if (errorList.length > 0) {
			var errMsg = '<ul>';
				$.each(
				errorList,
				function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index]
					+ '</li>';
				});
				errMsg += '</ul>';
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
			return false;
		}
	}
}

function checkPaymetCashBalanceExists(){
	
	var errorList = [];
	
	var type=$("#paymentMode option:selected").attr("code");
	if(type == "C" || type == "PCA"){
	
		var transactionDate = $('#transactionDateId').val();
		
		if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
			errorList.push(getLocalMessage('account.select.paymentDate'));
			$('#paymentMode').val("");
		}
		
		 var sactionAmt = 0;
		$('.billDetailClass').each(function(j) {
			
			for (var m = 0; m <= j; m++) {		
				var paymentAmount = $("#paymentAmount" + m).val();
				if (paymentAmount == "" || paymentAmount == null || paymentAmount == 0 || paymentAmount == 0.00) {
					errorList.push(getLocalMessage('account.enter.paymentAmt.invoiceDetails'));
					$('#paymentMode').val("");
				}else{
					sactionAmt += parseFloat($("#paymentAmount" + m).val());
				}
			}
		});
		
		var resultCashAmt = sactionAmt.toFixed(2); 
		
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		}else {
			var balData = "&transactionDate="+transactionDate;
			var responseBal = __doAjaxRequest('ContraVoucherEntry.html?getCashAmount','POST',balData,false,'json');
			if(parseFloat(responseBal) < 0){
				errorList.push("Cash balance is insufficient/ negative amount : Current Cash A/c Balance is -> "+ responseBal +"");
				$('#paymentMode').val("");
				displayErrorsPage(errorList);
			}else if(parseFloat(resultCashAmt)>parseFloat(responseBal.toFixed(2))){
				errorList.push("Cash balance is insufficient : Current Cash A/c Balance is -> "+ responseBal +"");
				$('#paymentMode').val("");
				displayErrorsPage(errorList);
			}else{
				$('#errorDivId').hide();
			}			
		}
	}
	
	if(type == 'B'){
		$("#utrNo").show();
		$("#instrumentNo").hide();
	}else{
		$("#utrNo").hide();
		$("#instrumentNo").show();
	}
}


function getExpenditureAccountHeadData(count){
		
	var errorList = [];

	$('#paymentAmount' + count).val("");
	
	var billTypeId = "";
	var paymentTypeCodeP = $("#paymentTypeId option:selected").attr("code"); 
	if(paymentTypeCodeP == 'P'){	
		billTypeId = $("#bmBilltypeCpdIdP").val();
	}else{
		billTypeId = $("#bmBilltypeCpdId").val();
	}
	//var billTypeId = $("#bmBilltypeCpdId").val();
	if ((billTypeId == "" || billTypeId =="0" || billTypeId == null)) {
		errorList.push('Please Select Bill Type');
		$("#id"+count).val("");
		$('#id').trigger('chosen:updated');
	}
	var billNo = $("#id"+count).val();
	if ((billNo == "" || billNo =="0" || billNo == null)) {
		errorList.push('Please Select Bill Number');
		$("#bchId"+count).val("");
	}
	if(errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
		
	//$('#totalAmount').val("");
	//$('#selectAllBills').attr('checked', false); // Unchecks it
	//$('.selectBill').attr('checked', false);
		
	var templateExistFlag = $("#templateExistFlag").val();
	if(templateExistFlag=="Y"){
		$('.billDetailClass').each(function(j) {
			for (var m = 0; m < j; m++) {
				if (( ($('#id' + m).val() == $('#id' + j).val()) )&&  ( $('#id' + m).val()!="" &&$('#id' + j).val()!="") && ( ($('#bchId' + m).val() == $('#bchId' + j).val()) )&&  ( $('#bchId' + m).val()!="" &&$('#bchId' + j).val()!="") ) {
					errorList.push("Bill number and Expenditure cannot be same,please select another bill number expenditure head");
				}
			}
		});
		if (errorList.length == 0) {
			var billId = $("#id"+count).val();
			var bchId = $("#bchId"+count).val();
			var postData = '&billId=' + billId + '&bchId=' + bchId;
			var url = "RTGSPaymentEntry.html?searchBillData";
			var response = __doAjaxRequest(url, 'post', postData, false);
			
			if(response[0].netPayableStr == '0.00' || response[0].netPayableStr == '0' || response[0].netPayableStr == null || response[0].netPayableStr == ""){
				errorList.push("Selected Bill payment has been already done.");
				$("#amount"+count).val("");
				$("#deductions"+count).val("");
				$("#netPayable"+count).val("");
				displayErrorsPage(errorList);
				
			}else{
				//$("#billDate"+count).val(response[0].billDate);
				$("#amount"+count).val(response[0].billAmountStr);
				$("#deductions"+count).val(response[0].deductionsStr);
				$("#netPayable"+count).val(response[0].netPayableStr);
				//$("#paymentAmount"+count).val(response[0].netPayable);
				//getAmountFormatInStatic('paymentAmount'+count);
				
				 var amount=0; 
					var rowCount = $('#billDetailTable tr').length;
						for (var m = 0; m <= rowCount - 1; m++) {
							
							   var  n= parseFloat(parseFloat($("#paymentAmount" + m).val()));
							    if (isNaN(n)) {
							        return n=0;
							    }
							    amount += n ;
							    
						//var result = parseFloat(amount.toFixed(2));
						//$("#totalAmount").val(result);			
						$("#totalAmountHidden").val(result);
						//getAmountFormatInStatic('totalAmount');
						getAmountFormatInStatic('totalAmountHidden');
						}
				
				$('#errorDivId').hide();
			}
		}
		else{
			displayErrorsPage(errorList);
		}
	  }
	}
	
}

function getBillSumaryWiseForm(obj){
	
	$("#paymentTypeId").prop("disabled", true).trigger('chosen:updated');
	$("#bchId0").prop("disabled", true);
	$("#paymentAmount0").prop("readonly", true);
	$("#viewBillDet0").prop("disabled", true);
	
	paymentTypeWiseBillType();
}


function getBillSumaryWiseData(count){
	
	var errorList = [];

	$('#paymentAmount' + count).val("");
	
	var billTypeId = $("#bmBilltypeCpdId").val();
	//var billTypeId = $("#bmBilltypeCpdId").val();
	/*if ((billTypeId == "" || billTypeId =="0" || billTypeId == null)) {
		errorList.push('Please Select Bill Type');
		$("#id"+count).val("");
		$('#id'+count).trigger('chosen:updated');
	}*/
	if(errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
		
	//$('#totalAmount').val("");
	//$('#selectAllBills').attr('checked', false); // Unchecks it
	//$('.selectBill').attr('checked', false);
		
	var templateExistFlag = $("#templateExistFlag").val();
	if(templateExistFlag=="Y"){
		$('.billDetailClass').each(function(j) {
			for (var m = 0; m < j; m++) {
				if(($('#id' + m).val()!="" && $('#id' + j).val()!="") && ($('#vendorId' + m).val()!="" && $('#vendorId' + j).val()!="")){
					if (($('#id' + m).val() == $('#id' + j).val()) && ($('#vendorId' + m).val() == $('#vendorId' + j).val())) {
						errorList.push("Vendor name & payment order number cannot be same, please select another payment order number");
						$("#id"+count).val("");
						$('#id'+count).trigger('chosen:updated');
					}
				}
			}
		});
		if (errorList.length == 0) {
			var billId = $("#id"+count).val();
			var postData = '&billId=' + billId;
			var url = "RTGSPaymentEntry.html?searchBillSumaryWiseData";
			var response = __doAjaxRequest(url, 'post', postData, false);
			//$("#billDate"+count).val(response[0].billDate);
			if(response[0].netPayable == '0.00' || response[0].netPayable == '0' || response[0].netPayable == null || response[0].netPayable == "" || response[0].netPayable < 0){
				errorList.push("Selected Bill payment has been already done.");
				$("#amount"+count).val("");
				$("#deductions"+count).val("");
				$("#netPayable"+count).val("");
				displayErrorsPage(errorList);
				
			}else{
				$("#amount"+count).val(response[0].billAmountStr);
				$("#deductions"+count).val(response[0].deductionsStr);
				$("#netPayable"+count).val(response[0].netPayableStr);
				$("#paymentAmount"+count).val(response[0].netPayable);
				$("#billTypeId"+count).val(response[0].billTypeId);
				getAmountFormatInStatic('paymentAmount'+count);
			}
			
			 var amount=0; 
				var rowCount = $('#billDetailTable tr').length;
					for (var m = 0; m <= rowCount - 1; m++) {
						
						   var  n= parseFloat(parseFloat($("#paymentAmount" + m).val()));
						    if (isNaN(n)) {
						        return n=0;
						    }
						    amount += n ;
						    
					//var result = parseFloat(amount.toFixed(2));
					//$("#totalAmount").val(result);			
					$("#totalAmountHidden").val(result);
					//getAmountFormatInStatic('totalAmount');
					getAmountFormatInStatic('totalAmountHidden');
					}
			
			$('#errorDivId').hide();
		}
		else{
			displayErrorsPage(errorList);
		}
	  }
	}
}

$(document).ready(function()
		{
		var paymentTypeCode = $('#paymentTypeCode').val(); 
		if(paymentTypeCode == 'W')
		{
			$('.viewBill').prop( "disabled", true );
			//$("input[id^='viewBillDet']").prop('disabled', true);
		}
		var paymentTypeCodeP = $("#paymentTypeId option:selected").attr("code"); 
		if(paymentTypeCodeP == 'P'){		
			$('#paymentTypeW').hide();
			$('#paymentTypeP').show();
			//$("#bmBilltypeCpdId option:selected").attr("code").find('ESB').removeAttr();
		}else{
			$('#paymentTypeW').show();
			$('#paymentTypeP').hide();
		}
	});

function checkBudgetBillSumaryWiseData(count){
	
	var errorList = [];
	
			var billId = $("#id"+count).val();
			var entryDate = $('#transactionDateId').val();
			var paymentAmt = $('#paymentAmount' + count).val();
			var postData = 'billId=' + billId + '&entryDate='
				+ entryDate + '&paymentAmt=' + parseInt(paymentAmt);
			var url = "AccountBillEntry.html?checkBudgetBillSumaryWiseData";
			var response = __doAjaxRequest(url, 'post', postData, false);
			
			var errorMsg = $(response).find('#errorMsg').val();
			if (errorMsg != undefined && errorMsg != '') {
				
				var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();
				if(bugDefParamStatusFlag == 'Y'){
					$('#paymentAmount' + count).val("");
					$('#amount' + count).val("");
					$('#deductions' + count).val("");
					$('#netPayable' + count).val("");
					$('#totalAmount').val("");
					$('#id' + count).val("").trigger('chosen:updated');
				}
				//$("#id"+count).val("");
				//$('#id'+count).trigger('chosen:updated');
				
				var errorList = [];
				errorList.push(errorMsg);
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
							+ errorList[index] + '</li>';
				});
				errMsg += '</ul>';
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
			}
}

$(document).ready(function() {
	$("#utrNo").hide();
	$("#instrumentNo").show();
});


function paymentTypeWiseBillType(){
	
	var paymentTypeCodeP = $("#paymentTypeId option:selected").attr("code"); 
	if(paymentTypeCodeP == 'P'){		
		$('#paymentTypeW').hide();
		$('#paymentTypeP').show();
		//$("#bmBilltypeCpdId option:selected").attr("code").find('ESB').removeAttr();
	}else{
		$('#paymentTypeW').show();
		$('#paymentTypeP').hide();
	}
}

/**
 * being used to search records for reversal
 */     
$('#searchBtn').click(function() {
	
	 var errorList = validateSearchInput();
	 if (errorList.length == 0) {
		 var formURL = 'RTGSPaymentEntry.html';
		 
		 var	formName =	findClosestElementId(obj, 'form');
		 var theForm	=	'#'+formName;
		 var requestData = __serializeForm(theForm);
		    
		 var requestData = $('#RTGSPaymentEntryFrm').serialize();
		 console.log('requestData'+requestData);
	 	var ajaxResponse = __doAjaxRequest(formURL+'?searchForRTGSPaymentEntry', 'POST', requestData, false,'json');
	 	if ( ajaxResponse == 'Internal Server Error.') {
	 		// display error page
		 } else if (ajaxResponse =='N') {
			 var errorList = [];
			 errorList.push("No record Found.");
			 displayErrorsOnPage(errorList);
			 $("#RTGSPaymentEntryGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		 } else {
			 $("#RTGSPaymentEntryGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 $("#errorDivId").hide();
			// $('#actionURL').val(ajaxResponse);
//			 var rowids = $('#voucherReversalGrid').jqGrid('getDataIDs');
//			 alert('rowids='+rowids);
		 }
	 } else {
		 displayErrorsOnPage(errorList); 
	 }
	 
});


function searchRTGSLeveledData(obj){
	
	var errorList = validateSearchInput();
	 if (errorList.length == 0) {
		 var formURL = 'RTGSPaymentEntry.html';
		 var formName =	findClosestElementId(obj, 'form');
		 var theForm	=	'#'+formName;
		 var requestData = __serializeForm(theForm);
		 //var requestData = $('#RTGSPaymentEntryFrm').serialize();
		 console.log('requestData'+requestData);
	 	var ajaxResponse = __doAjaxRequest(formURL+'?searchForRTGSPaymentEntry', 'POST', requestData, false,'json');
	 	if ( ajaxResponse == 'Internal Server Error.') {
	 		// display error page
		 } else if (ajaxResponse =='N') {
			 var errorList = [];
			 errorList.push("No record Found.");
			 displayErrorsOnPage(errorList);
			 $("#RTGSPaymentEntryGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		 } else {
			 $("#RTGSPaymentEntryGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 $("#errorDivId").hide();
			// $('#actionURL').val(ajaxResponse);
//			 var rowids = $('#voucherReversalGrid').jqGrid('getDataIDs');
//			 alert('rowids='+rowids);
		 }
	 } else {
		 displayErrorsOnPage(errorList); 
	 }
}

function viewRTGSBillDetails(count){
	
	var $link = $(this);
	var bmId = $('#billId' + count).val();
	var paymentId = $('#paymentId').val();
	var url = "AccountBillAuthorization.html?billFormForViewRTGS";
	var requestData = "bmId=" + bmId + "&paymentId=" + paymentId + "&MODE_DATA=" + "VIEW";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$('.content').html(returnData);
	prepareDateTag();
	
	/*var $link = $(this);
	var spId = $link.closest('tr').find('td:eq(0)').text();
	var billId = $('#billId' + count).val();
	var url = "RTGSPaymentEntry.html?viewBillEntryForm";
	var requestData = "billId=" + billId ;
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	var divName = ".content";
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);*/
	
	return false;		
}

function viewExpenditureDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var entryDate = $('#transactionDateId').val();
	var sanctionedAmount = $('#bchChargesAmt' + count).val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (sacHeadId == "")
		errorList.push(getLocalMessage('account.select.expHead'));

	if(entryDate === undefined){
		entryDate = $('#bmEntrydate').val();
	}
	
	if (sanctionedAmount == 0 || sanctionedAmount == "")
		errorList.push(getLocalMessage('account.valid.sanctionedAmount'));

	$('.expenditureClass').each(function(i) {

						for (var m = 0; m < i; m++) {
							if (($('#expenditureBudgetCode' + m).val() == $(
									'#expenditureBudgetCode' + i).val())) {
								errorList.push("Budget Heads cannot be same,please select another Budget Head");
							}
						}

					});

	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

	// });
	else {

		var postData = 'sacHeadId=' + sacHeadId + '&entryDate='
				+ entryDate + '&bchChargesAmt=' + parseFloat(sanctionedAmount) + '&count='
				+ count;
		var url = "RTGSPaymentEntry.html?viewBillExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);
		
		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {
			
			var errorList = [];
			errorList.push(errorMsg);
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			
		} else {
			//var divName = '.child-popup-dialog';
			var divName = '.popUp';
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			$('#expActAmt0').val(sanctionedAmount);
			// prepareTags();
			//$(".popUp").append('#expDetailTable tbody tr td:nth-child(10)');
			//showModalBox(divName);
			$(".popUp").show();
			$(".popUp").draggable();
		}
	}
}
