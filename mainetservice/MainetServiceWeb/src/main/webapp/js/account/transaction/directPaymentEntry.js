$(function() {
	
	
	$("#baAccountidPay").chosen();
	
	var formMode = $("#formMode").val();
	if(formMode=="create"){
		$("#vendorId").chosen();
		$("#billTypeId").chosen();
		$("#budgetCodeId0").chosen();
		$('.required-control').next().children().addClass('mandColorClass').attr("required",true);
	}
	
	//$("#baAccountidPay").chosen().trigger("chosen:updated");
	
	
	$("#directPayGrid").jqGrid(
			{
				url : "DirectPaymentEntry.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('accounts.payment.no'),getLocalMessage('advance.management.master.paymentdate'),getLocalMessage('account.tenderentrydetails.VendorEntry'), getLocalMessage('advance.management.master.paymentamount'), getLocalMessage('bill.action')],
				colModel : [ {name : "id",width : 20,align : 'center',sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name : "paymentNo",width : 55,align : 'center',sortable : false,searchoptions: { "sopt": [  "bw", "eq", "ne", "ew", "cn", "lt", "gt" ] }}, 
				             {name : "paymentEntryDate",align : 'center',width : 75,sortable : false,searchoptions: { "sopt": [ "eq"] }},
				             {name : "vendorDesc",align : 'center',width : 75,sortable : false,searchoptions: { "sopt": [ "eq"] }}, 
				             {name : "billAmountStr",align : 'right',width : 75,sortable : false,searchoptions: { "sopt": [ "bw", "eq", "ne", "ew", "cn", "lt", "gt" ] }}, 
				             { name: 'id', index: 'id', width:40 , align: 'center !important',formatter:addLink,search :false},
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "dsgid",
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
				caption :getLocalMessage('account.direct.paymentEntry')
			});
	 jQuery("#directPayGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
	
	
	
	 function returnViewUrl(cellValue, options, rowdata, action) {

			return "<a href='#'  return false; class='viewClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
		}
		
	function addLink(cellvalue, options, rowdata) 
	 {
	    return "<a class='btn btn-blue-3 btn-sm viewClass' title='view'value='"+rowdata.id+"' id='"+rowdata.id+"' ><i class='fa fa-building-o'></i></a> " + " <a class='btn btn-blue-3 btn-sm printClass' title='Print' value='"+rowdata.id+"' id='"+rowdata.id+"'><i class='fa fa-print'></i></a>";
	 }
	
	
	
	$(document).on('click', '.createData', function() {
		var url = "DirectPaymentEntry.html?formForCreate";
		var requestData = "MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		/*errorList = [];
		var expenditureExistsFlag = $("#expenditureExistsFlag").val();
		if(expenditureExistsFlag=="N"){
			errorList.push("No expenditure data found under the current financial year");
			displayErrorsPage(errorList);
		}*/
		prepareDateTag();
		return false;
		
	});
	
	$(document).on('click', '.viewClass', function() {	
		var $link = $(this);
		var id = $link.closest('tr').find('td:eq(0)').text();
		var url;
			url = "DirectPaymentEntry.html?formForView";
		var requestData = "id=" + id;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		prepareDateTag();

	});
$(document).on('click', '.printClass', function() {
		var $link = $(this);
		var id = $link.closest('tr').find('td:eq(0)').text();
		var url;
			url = "DirectPaymentEntry.html?formForPrint";
		var requestData = "id=" + id;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		prepareDateTag();
});



	
	$("#budgetDetailsTable").unbind('click').on("click",'.addButton',function(e) {
		
		var content = $(this).closest('#budgetDetailsTable tr').clone();
		$(this).closest("#budgetDetailsTable").append(content);
		content.find("select").attr("value", "");
		content.find("input:text").val("");
		content.find('div.chosen-container').remove();
		content.find("select:eq(0)").chosen().trigger("chosen:updated");
		content.find('label').closest('.error').remove(); //for removal duplicate
		reOrderTableIdSequence();
		e.preventDefault();

	});
	

	
	//to delete row
	$("#budgetDetailsTable").on("click", '.delButton', function(e) {
		
		var rowCount = $('#budgetDetailsTable tr').length;
		if (rowCount <= 2) {
			return false;
		}
		$(this).closest('#budgetDetailsTable tr').remove();

		var billAmountTotal = 0;
		var rowCount = $('#budgetDetailsTable tr').length;

		dynamicTotalBilltAmount(rowCount);
		
		//dynamicTotalPaymentAmount(rowCount)
		//totalPaymentamount();
		//getNetPayable();
		reOrderTableIdSequence();
		e.preventDefault();
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
		    var result = amount.toFixed(2);

	$("#amountToPay").val(result);
	$("#totalAmountHidden").val(result);
	getAmountFormatInStatic('amountToPay');
	getAmountFormatInStatic('totalAmountHidden');
	
	}
}


function reOrderTableIdSequence(){
	
	$('.budgetDetailClass').each(function(i) {
		
		//IDs
		$(this).find("select:eq(0)").attr("id",	"budgetCodeId" + i);
		$(this).find("input:text:eq(1)").attr("id",	"paymentAmount" + i);
		$(this).find(".viewExp").attr("id", "viewExpDet" + i);
		$(this).find("input:hidden:eq(0)").attr("id","notEnoughBudgetFlag" + i);
		
		//names
		$(this).find("select:eq(0)").attr("name","paymentDetailsDto["+ i + "].id");
		$(this).find("input:text:eq(1)").attr("name","paymentDetailsDto["+ i + "].paymentAmount");
		
		//functions
		//$(this).find(".viewExp").attr("onclick", "viewExpenditureDetails(" + i + ")");
		$(this).find("input:text:eq(1)").attr("onchange", "getAmountFormat("+ i + ")");
		//$(this).find("input:text:eq(1)").attr("onblur", "validateBudgetHead("+ i + ")");
		$(this).find('#budgetCodeId'+i).attr("onchange","validateBudgetHead(" + i + ")");
		$(this).find(".viewExp").attr("onclick", "viewBillDetails(" + i + ")");
	});
}


function searchDirectPayData() {
	
	var errorsList = [];
	errorsList = validatePaymentSearchForm(errorsList);
	displayErrorsPage(errorsList);
	
	if(errorsList <= 0){
		var url = "DirectPaymentEntry.html?searchDirectPayData";
		var returnData = {
			"paymentEntryDate" : $("#paymentEntryDate").val(),
			"paymentAmount"  : $("#paymentAmount").val(),
			"vendorId"  : $("#vmVendorname option:selected").attr("value"),
			"budgetCodeId"  : $("#budgetCodeId option:selected").attr("value"),
			"paymentNo" 	: 	$("#paymentNo").val(),
			"baAccountid" :  $("#baAccountid option:selected").attr("value"),
		};
		var result = __doAjaxRequest(url, 'POST', returnData, false, 'json');
		if (result != null && result != '') {
			$("#directPayGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
			$('#errorDivId').hide();
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("account.norecord.criteria"));
			if (errorList.length > 0) {
	
				displayErrorsPage(errorList);
			}
			$("#directPayGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}
}

function validatePaymentSearchForm(errorList){
	var paymentEntryDate = 	$("#paymentEntryDate").val();
	var paymentAmount = $("#paymentAmount").val();
	var vmVendorname =	$("#vmVendorname").val();
	var budgetCodeId = $("#budgetCodeId").val();
	var	paymentNo = 	$("#paymentNo").val();
	var baAccountid = $("#baAccountid").val();
	
	if(paymentEntryDate=="" && paymentAmount=="" && vmVendorname =="" && budgetCodeId =="" && paymentNo =="" && baAccountid ==""){
		
		errorList.push(getLocalMessage('account.select.criteria'));
	}
	if(paymentEntryDate!=null)
		{
		errorList=validatedate(errorList,'paymentEntryDate');
		}
	return errorList;
}


function totalPaymentamount(){
	
	$("#paymentMode").val("");
	$("#utrNo").hide();
	$("#instrumentNo").show();
	
    var amount=0; 
	var rowCount = $('#budgetDetailsTable tr').length;
		for (var m = 0; m <= rowCount - 1; m++) {
			
			   var  n= parseFloat(parseFloat($("#paymentAmount" + m).val()));
			    if (isNaN(n)) {
			        return n=0;
			    }
			    amount += n ;
			    
		var result = amount.toFixed(2);
		
		$("#amountToPay").val(result);
		$("#totalAmountHidden").val(result);
		getAmountFormatInStatic('amountToPay');
		getAmountFormatInStatic('totalAmountHidden');
		}
}


function dynamicTotalPaymentAmount(count){
    var amount=0; 
	//var rowCount = $('#budgetDetailsTable tr').length;
		for (var m = 0; m <= count - 1; m++) {
			
			   var  n= parseFloat(parseFloat($("#paymentAmount" + m).val()));
			    if (isNaN(n)) {
			        return n=0;
			    }
			    amount += n ;
			    
		var result = amount.toFixed(2);
		$("#totalAmount").val(result);
		$("#totalAmountHidden").val(result);
		}
}


/*function validateBudgetHead(count){
	
	var errorList= [];
	
	var budgetCodeId = $("#budgetCodeId"+count).val();
	var entryDate = "";
	var paymentAmount = $("#paymentAmount"+count).val();
	
	var postData = 'budgetCodeId=' + budgetCodeId + '&entryDate='+ entryDate + '&bchChargesAmt=' + paymentAmount + '&count='+ count;
	var url = "AccountBillEntry.html?getExpDetails";
	var response = __doAjaxRequest(url, 'post', postData, false);
	
	if(response[0].hasError == "true"){
		errorList.push(getLocalMessage('Payment amount exceeds budget amount, please enter valid amount'));
		$("#notEnoughBudgetFlag"+count).val("true");
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		}
	}else{
		$("#notEnoughBudgetFlag"+count).val("false");
		$('#errorDivId').hide();
	}
	
}*/


function viewExpenditureDetails(count) {

	// var departmentId = $('#dpDeptid').val();
	var budgetCodeId = $('#budgetCodeId' + count).val();
	var entryDate = $('#bmEntrydate').val();
	var sanctionedAmount = $('#paymentAmount' + count).val().replace(/,/g,'');
	
	var errorList = [];
	if (budgetCodeId == "")
		errorList.push(getLocalMessage('account.select.acchead'));

	if (sanctionedAmount == 0 || sanctionedAmount == "")
		errorList.push(getLocalMessage('account.enter.paymentAmt'));

	$('.budgetDetailClass').each(function(i) {

						for (var m = 0; m < i; m++) {
							if (($('#budgetCodeId' + m).val() == $('#budgetCodeId' + i).val())) {
								errorList.push("Account Heads cannot be same,please select another Account Head");
							}
						}

	});
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	else {

		var postData = 'budgetCodeId=' + budgetCodeId + '&entryDate='
				+ entryDate + '&bchChargesAmt=' + parseInt(sanctionedAmount) + '&count='
				+ count;
		var url = "AccountBillEntry.html?viewExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);

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

function proceedForDirectPayment(element){

	var errorList = [];
	errorList = validatePayForm(errorList);
	if (errorList.length == 0) {
		var url = "DirectPaymentEntry.html?proceedPayment";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		//var returnData = __doAjaxRequest(url, 'post', requestData, false,'html');
		var response = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'html');
		   if(response != false){
			   $('.content').html(response);
		   }
		//prepareDateTag();
		return false;
		
		var totalAmount = $("#totalAmount").val();
		//$("#amountToPay").val(totalAmount);
	}
	else {
		displayErrorsPage(errorList);
	}
}

function validatePayForm(errorList){
	
	var notEnoughBudgetFlag = $("#notEnoughBudgetFlag").val();
	var flag="";
	$('.budgetDetailClass').each(function(i) {
		if($("#notEnoughBudgetFlag"+i).val() == "true"){
			flag = "true";
		}
	});
	
	if(flag == "true"){
		errorList.push(getLocalMessage("account.acchead.paymentAmt.exceed.budgetAmt.validAmt"));
	}
	return errorList;
	
}



function getChequeNos(){
	
	var errorList = [];
	
	var transactionDate = $('#transactionDateId').val();
	
	if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
		errorList.push(getLocalMessage('account.select.direct.paymentDate'));
		$("#baAccountidPay").val('').trigger('chosen:updated');
	}
	
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
	
	var bankAcId = $('#baAccountidPay').val();
	var data = "&bankAcId="+bankAcId;
	var amountToPay = $('#amountToPay').val();
	//var paymentMode =  $('#paymentMode :selected').text();
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
		getChequeNos();
	}
}



function saveDirectPaymentEntry(element) {
	
	var errorList = [];
	//errorList = validatePaymentForm(errorList);
	errorList = validateForm(errorList);
	
	var amountToPay = $("#amountToPay").val();
	var bankBalance = $("#bankBalance").val();
	var fieldId = $("#fieldId").val();
	
	if((amountToPay != "" && amountToPay != 0) && (bankBalance != "" && bankBalance != 0)){
		var allow=getLocalMessage('account.payment.allow.neg.bal');
		if((parseInt(amountToPay)>parseInt(bankBalance))&& allow!='Y'){
			errorList.push("Bank balance is insufficient");
		}
	}
	
	if (fieldId == "" || fieldId == undefined || fieldId == null
		    || fieldId == '0') {
		errorList.push(getLocalMessage('account.field.id'));
	    }
	
	$('.budgetDetailClass').each(function(j) {
		for (var m = 0; m < j; m++) {
			if (($('#budgetCodeId' + m).val() == $('#budgetCodeId' + j).val())) {
				errorList.push("Account Heads cannot be same,please select another Account Head");
			}
		}
	});
	
	//var transactionDate = $("#transactionDateId").val();
	
	var from = $("#transactionDateId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#instrumentDate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	var utrDateForm = $("#utrDateId").val().split("/");
	var utrDate = new Date(utrDateForm[2], utrDateForm[1] - 1, utrDateForm[0]);
	var option=$("#paymentMode option:selected").attr("code");

	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Instrument Date should not be less than Direct Payment Date");
		}
	}
	
	if(option == 'B'){
		if((transactionDate != "" && transactionDate != null) && (utrDate != "" && utrDate != null)){
			if(utrDate > transactionDate){
				errorList.push("UTR Date should not be greater than Direct Payment Date");
			}
		}
	}
	

	
	var totalAmount = $("#amountToPay").val();
	var sumOfDedctionAmt = 0;
	var rowCount = $('#budgetDetailsTable tr').length;
	
	for (var i = 0; i < rowCount - 1; i++) {
		sumOfDedctionAmt += parseFloat($('#paymentAmount' + i).val());
	}
	
	if((sumOfDedctionAmt != null) && (sumOfDedctionAmt != "") && (!isNaN(sumOfDedctionAmt)) && (sumOfDedctionAmt != undefined) ){
		if(parseFloat(sumOfDedctionAmt) != parseFloat(totalAmount)){
			errorList.push(getLocalMessage('account.discrepancy.found.paymentAmt'));
		}
	}
	
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	
	if (errorList.length == 0) {
		
		showConfirmBoxSave();
	}
		 /*if(status != false){
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

function showConfirmBoxSave(){
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+' </h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>   '+ 
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
	
	var url = "DirectPaymentEntry.html?create";
	//var formName = findClosestElementId(element, 'form');
	//var theForm = '#' + formName;
	//var requestData = __serializeForm(theForm);
	var requestData = $('#directPaymentEntry').serialize();
	
	//var status = __doAjaxRequestForSave(url, 'post', requestData, false,'', element);
	
	var response= __doAjaxRequestForSave(url, 'post', requestData, false,'', '');
	    //$('.content').html(response);
	    
	//var status = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'json');
	
	if (response != false) {
		if(response != null && response != '' && response != undefined) {
			showConfirmBox(response);
		}else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	} else {
		displayErrorsPage(errorList);
	}
}

function validateForm(errorList) {
	
	var transactionDateId = $("#transactionDateId").val();
	if(transactionDateId!=null)
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
				errorList.push("Direct payment date can not be less than SLI date");
			  }
			}
		  }
		}
	if (transactionDateId == "" || transactionDateId == null) {
		errorList.push(getLocalMessage('account.select.direct.paymentDate'));
	}
	var billTypeId = $("#billTypeId").val();
	if (billTypeId == "" || billTypeId == null) {
		errorList.push(getLocalMessage('account.select.paymentType'));
	}
	var vendorId = $("#vendorId").val();
	if (vendorId == "" || vendorId == null) {
		errorList.push(getLocalMessage('account.vendorname'));
	}
	/*var rm_Receivedfrom = $("#rm_Receivedfrom").val();
	if (rm_Receivedfrom == "" || rm_Receivedfrom == null) {
		errorList.push(getLocalMessage('account.enter.payeeName'));
	}*/
	var billRefNo = $("#billRefNo").val();
	if (billRefNo == "" || billRefNo == null) {
		errorList.push(getLocalMessage('account.enter.bill.RefNo'));
	}
	
	$('.budgetDetailClass').each(function(j) {
		
		for (var m = 0; m <= j; m++) {
			var budgetCode = $('#budgetCodeId' + m).val();
			if (budgetCode == "" || budgetCode == null) {
				errorList.push(getLocalMessage('account.select.acchead'));
			}
			var paymentAmount = $("#paymentAmount" + m).val();
			if (paymentAmount == "" || paymentAmount == null || paymentAmount == 0 || paymentAmount == 0.00) {
				errorList
						.push(getLocalMessage('account.enter.paymentAmt.accheadDetails'));
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
    	var utrNo = $.trim($("#utrNumber").val());
    	if (utrNo == null || utrNo == ""){
    		errorList.push(getLocalMessage('account.enter.UTR.no'));
    	}
    	var baAccountidPay = $.trim($("#baAccountidPay").val());
    	if (baAccountidPay == null || baAccountidPay == ""){
    		errorList.push(getLocalMessage('account.select.bankAccount'));
    	}
    }else{
    	
    }
    
	var amountToPay = $("#amountToPay").val();
	if (amountToPay == "" || amountToPay == null || amountToPay == 0 || amountToPay == 0.00) {
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
	}
	var bmNarration = $("#bmNarration").val();
	if (bmNarration == "" || bmNarration == null || bmNarration == 0) {
		errorList.push(getLocalMessage('account.narration'));
	}
	
	return errorList;
}

function showConfirmBox(msg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var msgText1 = getLocalMessage("account.direct.payment.no");
	var msgText2 = getLocalMessage("advance.entry.success");
	var cls = getLocalMessage("account.proceed.btn");
	
	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'
			+ msgText1 + ' ' + msg + ' ' + msgText2 + '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	//showModalBox(errMsgDiv);
}


function proceed() {
	var url = "DirectPaymentEntry.html?paymentReportForm";
    var returnData = __doAjaxRequest(url, 'post', null, false);
	
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.widget').html(returnData);
	//window.location.href = 'DirectPaymentEntry.html';
}


function getVendorDescription(){
	var vendorDesc = $('#vendorId :selected').text();
	var editedVendorName = vendorDesc.split("- ").pop();
	 $('#vendorDesc').val(editedVendorName);

	var  vmVendrnameId = $("#vendorId option:selected").val();
	var  vmVendrname= $("#vendorId option:selected").text();
	if(vmVendrnameId != null && vmVendrnameId != undefined && vmVendrnameId != ""){
		$("#rm_Receivedfrom").val(vmVendrname).prop("readonly",true);	
	}else{
		$("#rm_Receivedfrom").val("").prop("readonly",false);
	}
	
}

function getVendorDesc(){

	var vendorDesc = $("#rm_Receivedfrom").val();
	$('#vendorDesc').val(vendorDesc); 
}


function setPrevBalanceAmount(prExpId, amount, count){
	$( ".popUp" ).hide();
	
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

function getAmountFormat(count){

	var bmInvoicevalue = $('#paymentAmount' + count).val();

	if (bmInvoicevalue != undefined && !isNaN(bmInvoicevalue) && bmInvoicevalue != null && bmInvoicevalue != '') {
		
	var actualAmt = bmInvoicevalue.toString().split(".")[0];
	var decimalAmt = bmInvoicevalue.toString().split(".")[1];
	
	var decimalPart =".00";
	if(decimalAmt == null || decimalAmt == undefined){
		$('#paymentAmount' + count).val(actualAmt+decimalPart);
	}else{
		if(decimalAmt.length <= 0){
			decimalAmt+="00";
			$('#paymentAmount' + count).val(actualAmt+(".")+decimalAmt);
		}
		else if(decimalAmt.length <= 1){
			decimalAmt+="0";
			$('#paymentAmount' + count).val(actualAmt+(".")+decimalAmt);
		}else{
			if(decimalAmt.length <= 2){
			$('#paymentAmount' + count).val(actualAmt+(".")+decimalAmt);
			} 
		  }	
	   }
    }
	return viewBugdetDirectPaymentDetails(count);
}


function resetDriectPayForm(){
	
	$('#vendorId').val('').trigger('chosen:updated');
	$('#billTypeId').val('').trigger('chosen:updated');
	
	$('.budgetDetailClass').each(function(i) {
		$('#budgetCodeId'+i).val('').trigger('chosen:updated');
		$('#paymentAmount'+i).val("");
	});
	$('#totalAmount').val("");
	$('#totalAmountHidden').val("");
}

function resetPaymentForm(){
	
	$('#baAccountidPay').val('').trigger('chosen:updated');
	
}


function validateBudgetHead(count){
	
	var errorList = [];
	
	
	$('#paymentAmount' + count).val("");
	
	
	$('.budgetDetailClass').each(function(j) {
		for (var m = 0; m < j; m++) {
			if (($('#budgetCodeId' + m).val() == $('#budgetCodeId' + j).val())) {
				errorList.push("Account Heads cannot be same,please select another Account Head");
			}
		}
	});
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else{
		$('#errorDivId').hide();
	}
}

function setInstrumentDate(obj){
	
	var transactionDate = $("#transactionDateId").val();
	$('#instrumentDate').val(transactionDate); 
	$('#paymentMode').val("");
}

function viewBillDetails(count) {

	
	var entryDate;
	// var departmentId = $('#dpDeptid').val();
	//var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var budgetCodeId = $("#budgetCodeId"+count).val();
	
	entryDate = $('#transactionDateId').val();
	
	if(entryDate == undefined || entryDate == null){
	 entryDate = $('#paymentEntryDate').val();
	}
	var paymentAmt = $('#paymentAmount' + count).val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (budgetCodeId == "" || budgetCodeId == null){
		errorList.push("Please select account head");
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
			
			var postData = 'budgetCodeId=' + budgetCodeId + '&entryDate='
				+ entryDate + '&paymentAmt=' + parseInt(paymentAmt) + '&count='
				+ count;
			var url = "AccountBillEntry.html?viewDirectPaymentExpDetails";
			var response = __doAjaxRequest(url, 'post', postData, false);
			
			var errorMsg = $(response).find('#errorMsg').val();
			if (errorMsg != undefined && errorMsg != '') {
				
			var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();
			if(bugDefParamStatusFlag == 'Y'){
				$('#paymentAmount' + count).val("");
				$('#budgetCodeId' + count).val("").trigger('chosen:updated');
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

function viewBugdetDirectPaymentDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	//var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var budgetCodeId = $("#budgetCodeId"+count).val();
	var entryDate = $('#transactionDateId').val();
	var paymentAmt = $('#paymentAmount' + count).val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (budgetCodeId == "" || budgetCodeId == null){
		errorList.push("Please select account head");
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

		var postData = 'budgetCodeId=' + budgetCodeId + '&entryDate='
				+ entryDate + '&paymentAmt=' + parseInt(paymentAmt) + '&count='
				+ count;
		var url = "AccountBillEntry.html?viewDirectPaymentExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);
		
		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {
			
			
			var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();
			if(bugDefParamStatusFlag == 'Y'){
				$('#paymentAmount' + count).val("");
				$("#amountToPay").val("");
				$('#budgetCodeId' + count).val("").trigger('chosen:updated');
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
		return false;
	}
}

function setPrevBalanceAmount(prExpId, amount, count) {
	$( ".popUp" ).hide();
}

function validateChequeDate(){

	var errorList = [];
	
	var transactionDate = $('#transactionDateId').val();
	if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
		errorList.push(getLocalMessage('account.select.direct.paymentDate'));
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
			errorList.push("Instrument date should not be greater than direct payment date");
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


function checkCashBalanceExists(){
	
	var errorList = [];
	
	var type=$("#paymentMode option:selected").attr("code");
	if(type == "C" || type == "PCA"){
	
		var transactionDate = $('#transactionDateId').val();
		
		if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
			errorList.push(getLocalMessage('account.select.direct.paymentDate'));
			$('#paymentMode').val("");
		}
		
		 var sactionAmt = 0;
		$('.budgetDetailClass').each(function(j) {
			
			for (var m = 0; m <= j; m++) {		
				var paymentAmount = $("#paymentAmount" + m).val();
				if (paymentAmount == "" || paymentAmount == null || paymentAmount == 0 || paymentAmount == 0.00) {
					errorList.push(getLocalMessage('account.enter.paymentAmt.accheadDetails'));
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
		$("#utrDate").show();
		$("#instrumentNo").hide();
		$("#instrumentId").hide();
	}else{
		$("#utrNo").hide();
		$("#utrDate").hide();
		$("#instrumentNo").show();
		$("#instrumentId").show();
	}
}
function resetPaymentCategoryForm(obj){
	$('.error-div').hide();
	var errorList = [];
	
	var billTypeId = $('#billTypeId').val();
	if(billTypeId != null && billTypeId != ""){
	if (errorList.length == 0) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var url = "DirectPaymentEntry.html?getPaymentAccountHeadData";
		var response = __doAjaxRequest(url, 'post', requestData, false);
		$(".content").html(response);
		$(".content").show();
			
		
		}
	}
};

$(document).ready(function() {
	$("#utrNo").hide();
	$("#utrDate").hide();
	$("#instrumentNo").show();
	$("#instrumentDate").show();
});
