var contraId = '';
$(function() {
		$("#contraEntryGrid").jqGrid(
				{
					url : "ContraVoucherEntry.html?getGridData",
					datatype : "json",
					mtype : "POST",
					//colNames : [ '', "Transaction Date", "Transaction Number", "Transaction Type", /*"Mode",*/ "Amount", /*getLocalMessage('edit.msg'),*/ getLocalMessage('master.view')],
					colNames : [ '', getLocalMessage('account.budgetreappropriationmaster.transactiondate'),getLocalMessage('account.budgetadditionalsupplemental.transactionnumber'), getLocalMessage('accounts.receipt.transaction.type'),getLocalMessage('bill.amount'),getLocalMessage('bill.action')],
					colModel : [ {name : "coTranId",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
					             {name : "coEntryDateStr",width : 75,sortable : true, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
					             {name : "coVouchernumber",width : 75,sortable : true, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
					             {name : "contraType",width : 75,sortable : false, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
					             {name : "amountStr",width : 75,sortable : true, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
					             { name: 'coTranId', index: 'coTranId', width:20 , align: 'center !important',formatter:addLink,search :false},
					            ],
					pager : "#pagered",
					emptyrecords: getLocalMessage("jggrid.empty.records.text"),
					rowNum : 30,
					rowList : [ 5, 10, 20, 30 ],
					sortname : "coVouchernumber",
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
					caption : getLocalMessage("account.contra.transaction")
				});
		 jQuery("#contraEntryGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
		 $("#pagered_left").css("width", "");
	});	
function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.coTranId+"' id='"+rowdata.coTranId+"' ><i class='fa fa-building-o'></i></a>";
}


$(function() {
	
	$("#chequebookDetid").chosen();
	$("#baAccountidPayWth").chosen();
	$("#chequebookDetidWth").chosen();
	$("#baAccountidRecDeposit").chosen();
	
	$('.required-control').next().children().addClass('mandColorClass').attr("required",true);
	
	
			$(document).on('click', '.createData', function() {
				var url = "ContraVoucherEntry.html?formForCreate";
				var requestData ="MODE_DATA=" + "EDIT";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				$('.widget').html(returnData);
				prepareDateTag();
				return false;			
			});			
		});

$(function() {
	$(document).on('click', '.viewClass', function() {
		var $link = $(this);
		var contraId = $link.closest('tr').find('td:eq(0)').text();
		var url = "ContraVoucherEntry.html?formForUpdateOrView";
		var requestData = "contraId=" + contraId + "&MODE_DATA=" + "VIEW";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.widget').html(returnData);
		prepareDateTag();

	});
});

$(document).on('click', '.resetSearch', function() {
	$('#baAccountid').val('').trigger('chosen:updated');
	
});

function searchContraEntry(){
	var errorsList = [];
	
	
	errorsList = validateSearchForm(errorsList);
	displayErrorsPage(errorsList);
	
	if(errorsList <= 0){
		
		var url = "ContraVoucherEntry.html?searchContraEntry";
		var returnData = {
			"fromDate" : $("#fromDate").val(),
			"toDate" : $("#toDate").val(),
			"transactionType" : $("#entryType option:selected").attr("value"),
			"transactionNo" : $("#transactionNo").val(),
		};
		var result = __doAjaxRequest(url, 'POST', returnData, false, 'json');
		
		if (result != null && result != '') {
			$("#contraEntryGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
			$('#errorDivId').hide();
		} else {
			var errorList = [];
			
			errorList.push(getLocalMessage("account.norecord.criteria"));
			displayErrorsPage(errorList);
			
			$("#contraEntryGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}
}



function validateSearchForm(errorList){
	
	var fromDate = 	$("#fromDate").val();
	var toDate = $("#toDate").val();
	var entryType =	$("#entryType").val();
	var transactionNo = $("#transactionNo").val();
	if(fromDate=="" && toDate=="" && entryType =="" && transactionNo ==""){
		
		errorList.push(getLocalMessage('account.select.criteria'));
	}
	if(fromDate!=null)
		{
		errorList=validatedate(errorList,'fromDate');
		}
if(toDate!=null)
	{
	errorList=validatedate(errorList,'toDate');
	}
var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
if (eDate > sDate) {
	  errorList.push("From Date can not be less than To Date");
	  $( "#reportTypeId" ).prop( "disabled", true );
  }
	return errorList;
	
}



$( document ).ready(function() {
	
	$("#contraEntryGrid").setGridParam({sortname:'coVouchernumber', sortorder: 'desc'})
	   .trigger('reloadGrid');
	

$('input[name=contraType]').change(function(){
	
	 if( $('input[name=contraType]:checked').val() =='T'){
		 
		 	$("#transferPanel").show();
		 	$("#cashWithDrawPanel").hide();
		 	$("#cashDepositPanel").hide();
		 
	 }
	 if( $('input[name=contraType]:checked').val() =='W'){
		 
		 	$("#transferPanel").hide();
			$("#cashDepositPanel").hide();
			$("#cashWithDrawPanel").show();
			var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
			var disableBeforeDate = new Date(response[0], response[1], response[2]);
			var date = new Date();
			var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());			
			 $("#transactionDateWithDrawId").datepicker({
				    dateFormat: 'dd/mm/yy',		
					changeMonth: true,
					changeYear: true,
					minDate : disableBeforeDate,
					maxDate: today,
					onSelect: function(date) {
						$('#transactionDateHidden').val(date);
						$('#coChequedateWth').val(date);
				     }
				});
			 
		 }
		
	 if( $('input[name=contraType]:checked').val() =='D'){
		 
		 	$("#transferPanel").hide();
			$("#cashWithDrawPanel").hide();
			$("#cashDepositPanel").show();
			// find SLI date from SLI Prefix
			var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
			var disableBeforeDate = new Date(response[0], response[1], response[2]);
			var date = new Date();
			var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());			
			 $("#transactionDateDepositId").datepicker({
				    dateFormat: 'dd/mm/yy',		
					changeMonth: true,
					changeYear: true,
					minDate : disableBeforeDate,
					maxDate: today,
					onSelect: function(date) {
						$('#transactionDateHidden').val(date);
				     }
				}); 
			
		 }
	
});


});







function showContraEntryPanels(){
	
	var selectedEntry = $('#entryType :selected').text();
	
	if(selectedEntry == "Cash Withdrawal Entry"){
		
		$("#transferPanel").hide();
		$("#cashDepositPanel").hide();
		$("#cashWithDrawPanel").show();
		
	}
	if(selectedEntry == "Cash Deposit Entry"){
		
		$("#transferPanel").hide();
		$("#cashWithDrawPanel").hide();
		$("#cashDepositPanel").show();
	}
	
	if(selectedEntry == "Transfer Entry"){
		
		$("#transferPanel").show();
		$("#cashWithDrawPanel").hide();
		$("#cashDepositPanel").hide();
	}
	
	if(selectedEntry == "Select"){
		
		$("#cashWithDrawPanel").hide();
		$("#cashDepositPanel").hide();
	}
	
}



function setReceiptAmount(){
	
	var errorList = [];
	var payAmount = parseFloat($("#coAmountPayTrns").val()).toFixed(2);
	var bankBalance = parseFloat($("#bankBalance").val()).toFixed(2);
	var allow=getLocalMessage('account.payment.allow.neg.bal');
	if((parseFloat(payAmount)>parseFloat(bankBalance))&& allow!='Y'){
		errorList.push(getLocalMessage('acccount.bank.blnc'));
		$("#coAmountPayTrns").val("");
	}
	displayErrorsPage(errorList);
	$("#coAmountRecTrns").val("");
	$("#coAmountRecTrnsHidden").val("");
	if(errorList<=0){
		$("#coAmountRecTrns").val(payAmount);
		getAmountFormatInStatic('coAmountRecTrns');
		$("#coAmountRecTrnsHidden").val(payAmount);
		getAmountFormatInStatic('coAmountRecTrnsHidden');
	}
}



function checkBalanceForWithdraw(){
	var errorList = [];
	var withdrawAmount = $("#coAmountPayWth").val();
	var bankBalance = $("#bankBalanceWth").val();
	if(parseInt(withdrawAmount)>parseInt(bankBalance)){
		errorList.push(getLocalMessage('acccount.bank.blnc'));
		$("#coAmountPayWth").val("");
	}
	displayErrorsPage(errorList);
}





function getBankAccountListPay(){
	
	var bankIdTrns = $('#bankIdPay').val();
	var bankIdTrnsID = $('#bankIdPay').attr('id');
	$('#bankAccountPay').find('option:gt(0)').remove();
	if(bankIdTrnsID == 'bankIdPay'){
		
		var postData = 'bankId=' + bankIdTrns;
		var response = __doAjaxRequest('ContraVoucherEntry.html?bankAccountList','POST',postData,false,'json');
		var  optionsAsString='';
		
		$.each(response, function( key, value ) {
			optionsAsString += "<option value='" +key+"'>" + value + "</option>";
			});
		$('#bankAccountPay').append(optionsAsString);
	}
	
	var bankIdWth = $('#bankIdWth').val();
	var bankIdWthID = $('#bankIdWth').attr('id');
	$('#baAccountWth').find('option:gt(0)').remove();
	if(bankIdWthID == 'bankIdWth'){
		var postData = 'bankId=' + bankIdWth;
		var response = __doAjaxRequest('ContraVoucherEntry.html?bankAccountList','POST',postData,false,'json');
		var  optionsAsString='';
		
		$.each(response, function( key, value ) {
			optionsAsString += "<option value='" +key+"'>" + value + "</option>";
			});
		$('#baAccountWth').append(optionsAsString);
	}
	
	var bankIdDeposit = $('#bankIdDeposit').val();
	var bankIdDepositID = $('#bankIdDeposit').attr('id');
	$('#baAccountDeposit').find('option:gt(0)').remove();
	if(bankIdDepositID == 'bankIdDeposit'){
		var postData = 'bankId=' + bankIdDeposit;
		var response = __doAjaxRequest('ContraVoucherEntry.html?bankAccountList','POST',postData,false,'json');
		var  optionsAsString='';
		
		$.each(response, function( key, value ) {
			optionsAsString += "<option value='" +key+"'>" + value + "</option>";
			});
		$('#baAccountDeposit').append(optionsAsString);
	}
	
}

function getBankAccountListRec(){
	
	var bankId = $('#bankIdRec').val();
	
	$('#baAccountRec').find('option:gt(0)').remove();
	if(bankId > 0){
		var postData = 'bankId=' + bankId;
		var response = __doAjaxRequest('ContraVoucherEntry.html?bankAccountList','POST',postData,false,'json');
		var  optionsAsString='';
		
		$.each(response, function( key, value ) {
			optionsAsString += "<option value='" +key+"'>" + value + "</option>";
			});
		$('#baAccountRec').append(optionsAsString);
	}
}



function getPacAndSacHeadCodesPay(){
	
	var bankAcId = $('#bankAccountPay').val(); 
	var bankAcIDTrnsID = $('#bankAccountPay').attr('id');
	
	if(bankAcIDTrnsID == 'bankAccountPay'){
		var postData = 'bankAcId=' + bankAcId;
		var response = __doAjaxRequest('ContraVoucherEntry.html?getSacHeadCodes','POST',postData,false,'json');
		var response2 = __doAjaxRequest('ContraVoucherEntry.html?getPacHeadCodes','POST',postData,false,'json');
		
		var svalueData='';
		var skeyData='';
		
		var pvalueData='';
		var pkeyData='';
		
		$.each(response, function( key, value ) {
			skeyData = key;
			svalueData =  value;
		});
		
		$.each(response2, function( key, value ) {
			pkeyData = key;
			pvalueData =  value;
		});
		
		$('#sacHeadIdPay').val(skeyData);
		$('#sacHeadIdPayDesc').val(svalueData);
		
		$('#pacHeadIdPay').val(pkeyData);
		$('#pacHeadIdPayDesc').val(pvalueData);
	}
}

function getPacAndSacHeadCodesRec(){
	
	
	var bankAcIdRec = $('#baAccountRec').val(); 
	if(bankAcIdRec > 0){
		
		var postData = 'bankAcId=' + bankAcIdRec;
		var response = __doAjaxRequest('ContraVoucherEntry.html?getSacHeadCodes','POST',postData,false,'json');
		var response2 = __doAjaxRequest('ContraVoucherEntry.html?getPacHeadCodes','POST',postData,false,'json');
		
		
		var svalueData='';
		var skeyData='';
		
		var pvalueData='';
		var pkeyData='';
		
		$.each(response, function( key, value ) {
			skeyData = key;
			svalueData =  value;
	
		});
		
		$.each(response2, function( key, value ) {
			pkeyData = key;
			pvalueData =  value;
			
		});
		
		$('#sacHeadIdRec').val(skeyData);
		$('#sacHeadIdRecDesc').val(svalueData);
		
		$('#pacHeadIdRec').val(pkeyData);
		$('#pacHeadIdRecDesc').val(pvalueData);
	}	
	
}

function getPacAndSacHeadCodesPayCashWth(){
	
	var bankAcIdWth = $('#baAccountWth').val(); 
	
	var postData = 'bankAcId=' + bankAcIdWth;
	var response = __doAjaxRequest('ContraVoucherEntry.html?getSacHeadCodes','POST',postData,false,'json');
	var response2 = __doAjaxRequest('ContraVoucherEntry.html?getPacHeadCodes','POST',postData,false,'json');
	
	var svalueData='';
	var skeyData='';
	
	var pvalueData='';
	var pkeyData='';
	
	$.each(response, function( key, value ) {
		skeyData = key;
		svalueData =  value;
	});
	
	$.each(response2, function( key, value ) {
		pkeyData = key;
		pvalueData =  value;
	});
	
	$('#sacHeadIdPayWth').val(skeyData);
	$('#sacHeadIdPayDescWth').val(svalueData);
	
	$('#pacHeadIdPayWth').val(pkeyData);
	$('#pacHeadIdPayDescWth').val(pvalueData);
}


function getPacAndSacHeadCodesPayCashDeposit(){
	
	var bankAcIdDeposit = $('#baAccountDeposit').val(); 
	
	var postData = 'bankAcId=' + bankAcIdDeposit;
	var response = __doAjaxRequest('ContraVoucherEntry.html?getSacHeadCodes','POST',postData,false,'json');
	var response2 = __doAjaxRequest('ContraVoucherEntry.html?getPacHeadCodes','POST',postData,false,'json');
	
	var svalueData='';
	var skeyData='';
	
	var pvalueData='';
	var pkeyData='';
	
	$.each(response, function( key, value ) {
		skeyData = key;
		svalueData =  value;
	});
	
	$.each(response2, function( key, value ) {
		pkeyData = key;
		pvalueData =  value;
	});
	
	$('#sacHeadIdRecDeposit').val(skeyData);
	$('#sacHeadIdDepositDesc').val(svalueData);
	
	$('#pacHeadIdRecDeposit').val(pkeyData);
	$('#pacHeadIdDepositDesc').val(pvalueData);
	
}

function disableInstruments(){
	
	var selectedMode = $('#cpdModePay :selected').text();
	
	if(selectedMode == 'Cheque'){
		$( "#chequebookDetid" ).prop( "disabled", false );
		$( "#coChequedate" ).prop( "disabled", false );
		$(".datepicker").datepicker('setDate', new Date());
		$( "#chequebookDetid" ).val('');
		$('#chequebookDetid').trigger('chosen:updated');
	}
	if(selectedMode == 'Bank'){
		
		$( "#chequebookDetid" ).prop( "disabled", true );
		$( "#chequebookDetid" ).val('');
		$('#chequebookDetid').trigger('chosen:updated');
				
	}
	
	
}



function getChequeNos(){
	
	var errorList = [];
	
		var transactionDate = $('#transactionDateId').val();
		
		if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
			errorList.push(getLocalMessage('account.select.transactiondate'));
			$("#baAccountidPay").val('').trigger('chosen:updated');
		}
		
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		}else {
			
	var bankAcId = $('#baAccountidPay').val();
	var bankAcIdRec = $('#baAccountidRec').val();
	var data = "&bankAcId="+bankAcId;
	var errorList =[];
	
	if(bankAcId!=bankAcIdRec){

		$('#chequebookDetid').find('option:gt(0)').remove();
		var response = __doAjaxRequest('ContraVoucherEntry.html?getChequeNumbers','POST',data,false,'json');
		
		if(bankAcId!='' && bankAcId!=null && bankAcId!='undefined'){
			var balData = "&bankAcId="+bankAcId +"&transactionDate="+transactionDate;
			var responseBal = __doAjaxRequest('ContraVoucherEntry.html?getBankBalance','POST',balData,false,'json');
		}
		
		var selectedMode = $('#cpdModePay :selected').text();
		if(selectedMode == 'Cheque'){
			var chequeNoMap = '';
			$.each(response,function(key,value){
				chequeNoMap += "<option value='" +key+"' selected>" + value + "</option>";
			});
			$('#chequebookDetid').append(chequeNoMap);
			$('#chequebookDetid').val('');
		}
		$('#bankBalance').val(responseBal);
		$("#baAccountidRec option[value="+bankAcId+"]").remove();
		$('#baAccountidRec').trigger('chosen:updated');
		$('#chequebookDetid').trigger('chosen:updated');
		
		var bankAc = $("#baAccountidPay option:selected").text();
		var payeeId = $('#payeeId').attr('id');
		createPayee(bankAc,payeeId);
		
		
		
	}
	else{
		errorList.push(getLocalMessage('acccount.payment.receipt.not.same'));		
		displayErrorsPage(errorList);
		
	}
	}
}


function validateRecptBankAc(){
	
	var bankAcId = $('#baAccountidPay').val();
	var bankAcIdRec =$('#baAccountidRec').val();
	var contraType = "T"
	var data = "&contraType="+contraType;
	var response = __doAjaxRequest('ContraVoucherEntry.html?checkTemplate','POST',data,false,'json');
	$('#templateExistFlagTrans').val(response);
	var errorList =[];
	if(bankAcId==bankAcIdRec){
		errorList.push(getLocalMessage('acccount.payment.receipt.not.same'));		
		displayErrorsPage(errorList);
	}
	var bankAcRec = $("#baAccountidRec option:selected").text();;
	var paytToId = $('#payToTrans').attr('id');
	createPayTo(bankAcRec,paytToId);
}

function createPayTo(bankAccount,payToId){
	
	$('#'+payToId+'').val(bankAccount);
	
}


function createPayee(bankAccount,payeeId){
	
	$('#'+payeeId+'').val(bankAccount);
	
}

function getPayTo(){
	
	var errorList = [];
	
		var transactionDate = $('#transactionDateWithDrawId').val();
		
		if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
			errorList.push(getLocalMessage('account.select.transactiondate'));
			$("#baAccountidPayWth").val('').trigger('chosen:updated');
		}
		
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		}else {
	
	var bankAc = $("#baAccountidPayWth option:selected").text();
	var payToId = $('#payToWth').attr('id');
	createPayTo(bankAc,payToId);
	var contraType ="W";
	var templData = "&contraType="+contraType;
	var response = __doAjaxRequest('ContraVoucherEntry.html?checkTemplate','POST',templData,false,'json');
	$('#templateExistFlagWth').val(response);
	
	var bankAcId = $('#baAccountidPayWth').val();
	if(bankAcId!='' && bankAcId!=null && bankAcId!='undefined'){
		var balData = "&bankAcId="+bankAcId +"&transactionDate="+transactionDate;
		var responseBal = __doAjaxRequest('ContraVoucherEntry.html?getBankBalance','POST',balData,false,'json');
	}
	$('#bankBalanceWth').val(responseBal);
	
	getChequeNosCashWth();
	$('#chequebookDetidWth').val('').trigger('chosen:updated');	
	}
}

function getPayToDeposit(){
	
	var bankAc = $("#baAccountidRecDeposit option:selected").text();
	var payToId = $('#payToDeposit').attr('id');
	
	$('#'+payToId+'').val(bankAc);
	
	var contraType = "D";
	var templData = "&contraType="+contraType;
	var response = __doAjaxRequest('ContraVoucherEntry.html?checkTemplate','POST',templData,false,'json');
	$('#templateExistFlagDep').val(response);
	
}



function getChequeNosCashWth(){
	
	var bankAcIdWth = $('#baAccountidPayWth').val();
	var data = "bankAcId="+bankAcIdWth;
	
	$('#chequebookDetidWth').find('option:gt(0)').remove();
	
	var response = __doAjaxRequest('ContraVoucherEntry.html?getChequeNumbers','POST',data,false,'json');
	
	var selectedMode = $('#cpdModePayWth :selected').text();
	if(selectedMode == 'Cheque'){
		var chequeNoMap = '';
		$.each(response,function(key,value){
			
			chequeNoMap += "<option value='" +key+"' selected>" + value + "</option>";
			
		});
		
		$('#chequebookDetidWth').append(chequeNoMap);
		$('#chequebookDetidWth').val('');
	}
}

function saveContraEntry(element,type){
	
	var errorList = [];
	if(type == "T"){
		$("#cashWithDrawPanel").hide();
		$("#cashDepositPanel").hide();	
		
		errorList = validateTransferForm(errorList);
		if (errorList.length == 0) {
			var url = "ContraVoucherEntry.html?create";
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			requestData = requestData+'&'+'transactionDate='+$('#transactionDateHidden').val();
			var status = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'html');
			if(status != false){
			var obj = $(status).find('#successfulFlag');
			if ($('.widget').html(status)) {
					if (obj.val() == 'Y') {
						$("#cashWithDrawPanel, #cashDepositPanel").hide(); //Defect #145248
						showConfirmBox();
					}
			} else {
				$(".widget-content").html(status);
				$(".widget-content").show();
			}
		}
		} else {
			displayErrorsPage(errorList);
		}
		
	}
	if(type == "W"){
		$("#transferPanel").hide();
		$("#cashDepositPanel").hide();
		errorList = validateWithdrawForm(errorList);
		if (errorList.length == 0) {
			var url = "ContraVoucherEntry.html?create";
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			requestData = requestData+'&'+'transactionDate='+$('#transactionDateHidden').val();
			var status = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'html');
				if(status!=false){
				var obj = $(status).find('#successfulFlag');
				if ($('.content').html(status)) {
					if (obj.val() == 'Y') {
	
						showConfirmBox();
					}
				} else {
					$(".widget-content").html(status);
					$(".widget-content").show();
				}
			}
		} else {
			displayErrorsPage(errorList);
		}
		
	}
	if(type == "D"){
		$("#cashWithDrawPanel").hide();
		$("#transferPanel").hide();	
		var depoReceivedFrom = $("#baAccountidRecDeposit option:selected").text();
		$('#payeeId').val(depoReceivedFrom);
		errorList = validateDepositForm(errorList);
		if (errorList.length == 0) {
			var url = "ContraVoucherEntry.html?create";
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			requestData = requestData+'&'+'transactionDate='+$('#transactionDateHidden').val();
			var status = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'html');
			if(status!=false){
				var obj = $(status).find('#successfulFlag');
				if ($('.content').html(status)) {
					if (obj.val() == 'Y') {
	
						showConfirmBox();
					}
				} else {
					$(".widget-content").html(status);
					$(".widget-content").show();
				}
			}
		} else {
			displayErrorsPage(errorList);
		}
		
	}

}


function validateTransferForm(errorList){
	
	var coAmountPayTrns = $("#coAmountPayTrns" ).val();
	if (coAmountPayTrns != "" ){
	if (coAmountPayTrns == 0) {
		errorList.push(getLocalMessage('account.valid.amt'));
		}
	}

	var transactionDateId = $("#transactionDateId" ).val();
	var coChequedate = $("#coChequedate" ).val();
	
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
				errorList.push("Transaction date can not be less than SLI date");
				}
			 }
		  }
		}
	
	if(coChequedate!=null)
		{
		errorList = validatedate(errorList,'coChequedate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#coChequedate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Cheque date can not be less than SLI date");
				}
			 }
		  }
		}
	var templateExistFlagTrans = $("#templateExistFlagTrans").val();
	if(templateExistFlagTrans=="N"){
		errorList.push(getLocalMessage('account.valid.contra.voucher'));
	}
	
	return errorList;
}

function validateWithdrawForm(errorList){
	
	var coAmountPayWth = $("#coAmountPayWth" ).val();
	if (coAmountPayWth != "" ){
	if (coAmountPayWth == 0) {
		errorList.push(getLocalMessage('account.valid.withdrawal.amt'));
		}
	}
	
	var transactionDateWithDrawId = $("#transactionDateWithDrawId" ).val();
	var coChequedateWth = $("#coChequedateWth" ).val();
	
	if(transactionDateWithDrawId!=null)
		{
		errorList = validatedate(errorList,'transactionDateWithDrawId');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#transactionDateWithDrawId").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Transaction date can not be less than SLI date");
			  }
			}
		  }
		}
	
	if(coChequedateWth!=null)
		{
		errorList = validatedate(errorList,'coChequedateWth');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#coChequedateWth").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Cheque date can not be less than SLI date");
			  }
			}
		  }
		}
	
	var templateExistFlagWth = $("#templateExistFlagWth").val();
	if(templateExistFlagWth=="N"){
		errorList.push(getLocalMessage('account.valid.contra.voucher'));
	}
	
	return errorList;
}

function validateDepositForm(errorList){
	var coAmountRecDeposit = $.trim($("#coAmountRecDeposit").val());
	var transactionDateDeposit= $('#transactionDateDepositId').val();
	var baAccountidRecDeposit= $('#baAccountidRecDeposit').val();
	var coRemarkRecDeposit= $.trim($('#coRemarkRecDeposit').val());
	
	
	if (transactionDateDeposit == '' || transactionDateDeposit == undefined) {
		errorList.push(getLocalMessage('account.select.transactiondate'));
	} if (baAccountidRecDeposit == '' ||baAccountidRecDeposit == '0' || baAccountidRecDeposit == undefined) {
		errorList.push(getLocalMessage('account.select.bank.name'));
	} if (coAmountRecDeposit == '' || coAmountRecDeposit == '0' || coAmountRecDeposit == undefined) {
		errorList.push(getLocalMessage('account.valid.amt'));
	} if (coRemarkRecDeposit == '' || coRemarkRecDeposit == undefined) {
		errorList.push(getLocalMessage('account.enter.desc'));
	}
	if(transactionDateDeposit!=null)
		{
		errorList = validatedate(errorList,'transactionDateDepositId');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#transactionDateDepositId").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Transaction date can not be less than SLI date");
			  }
			}
		  }
		}
	var total = $("#total").val();
	if (total !="0" && total !="") {
	if (coAmountRecDeposit !=total) {
		errorList.push(getLocalMessage('account.denomination.equal.deposit'));
	}else{
		$('#errorDivId').hide();
		}
	}
	
	if (total=="0" || total =="") {
		errorList.push(getLocalMessage('account.enter.denomination'));
	}
	var templateExistFlagDep = $("#templateExistFlagDep").val();
	if(templateExistFlagDep=="N"){
		errorList.push(getLocalMessage('account.valid.contra.voucher'));
	}
	return errorList;
}


function displayErrorsPage(errorList){
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


function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var succ = getLocalMessage('account.transaction.saved');
	var cls = getLocalMessage('account.proceed.btn');
	
	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'
			+ succ +'</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}


function proceed() {
	
	var url = "ContraVoucherEntry.html?contraVoucherReport";
    var returnData = __doAjaxRequest(url, 'post', null, false);
	
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.widget').html(returnData);
}

function getAmount(count){
	
	var descCash = $('#denomDesc'+count).val();
	var denom = 0 ;
	if( $('#denomination'+count).val() != '' && $('#denomination'+count).val() >0)
		{
		 denom = $('#denomination'+count).val();
		
		}
	
	 var amount  = parseFloat(descCash) * parseFloat(denom);
		$('#amount'+count).val(amount);
	
	var total=0;
	var coins = $('#coins2').val();
	var rowCount = $('.table-condensed tr').length;

	for (var i = 0; i < rowCount -2; i++) {
	  
		total += parseFloat($("#amount" + i).val());
	
	}
	
	$("#total").val(total.toFixed(2));
}

$( document ).ready(function() {
	$('.denomClass').each(function(i) {
		getAmount(i);
	});
});



function clearTransferForm(){

	$('#cpdModePay').val("");
	$('#baAccountidPay').val('').trigger('chosen:updated');
	$('#bankAccountPay').val("Select");
	$('#pacHeadIdPayDesc').val("");
	$('#sacHeadIdPayDesc').val("");
	$('#coAmountPayTrns').val("");
	$('#coAmountRecTrns').val("");
	$('#chequebookDetid').val("");
	$('#coRemarkPay').val("");
	$('#baAccountidRec').val('').trigger('chosen:updated');
	$('#bankAccountRec').val("Select");
	$('#payToTrans').val("");
	$('#transactionDateId').val("");
	$('#chequebookDetid').val('').trigger('chosen:updated');
	$('#coChequedate').val("");
}

function clearWithdrawalForm(){
	
	$('#baAccountidPayWth').val('').trigger('chosen:updated');
	$('#bankIdWth').val("Select");
	$('#baAccountWth').val("Select");
	$('#pacHeadIdPayDescWth').val("");
	$('#sacHeadIdPayDescWth').val("");
	$('#coAmountPayWth').val("");
	$('#chequebookDetidWth').val("");
	$('#coRemarkPay').val("");
	$('#coRemarkPayWth').val("");
	$('#payToWth').val("");
}

function clearDepositForm(){
	
	$('#baAccountidRecDeposit').val('').trigger('chosen:updated');
	$('#bankIdDeposit').val("Select");
	$('#baAccountDeposit').val("Select");
	$('#pacHeadIdDepositDesc').val("");
	$('#sacHeadIdDepositDesc').val("");
	$('#coAmountRecDeposit').val("");
	$('#cpdModeRecDeposit').val("Select");
	$('#chequebookDetidWth').val("Select");
	$('#coRemarkRecDeposit').val("");
	$('#total').val("");
	
	var rowCount = $('.table-condensed tr').length;
	
	for (var i = 0; i < rowCount - 4; i++) {
		$('#denomination' + i).val("");
		$("#amount" + i).val("0");
	
	}
	
}

function checkPettyCashBalanceExists(){
	
	var errorList = [];
	
		var transactionDate = $('#transactionDateDepositId').val();
		var coAmountRecDeposit = $('#coAmountRecDeposit').val();
		
		if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
			errorList.push(getLocalMessage('account.select.transactiondate'));
			$('#coAmountRecDeposit').val("");
		}
		
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		}else {
			var balData = "&transactionDate="+transactionDate;
			var responseBal = __doAjaxRequest('ContraVoucherEntry.html?getPettyCashAmount','POST',balData,false,'json');
			if(parseFloat(responseBal) < 0){
				errorList.push("Petty cash balance is insufficient/ negative amount : Current Petty Cash A/c Balance is -> "+ responseBal +"");
				$('#coAmountRecDeposit').val("");
				displayErrorsPage(errorList);
			}else if(parseFloat(coAmountRecDeposit)>parseFloat(responseBal.toFixed(2))){
				errorList.push("Petty cash balance is insufficient : Current Petty Cash A/c Balance is -> "+ responseBal +"");
				$('#coAmountRecDeposit').val("");
				displayErrorsPage(errorList);
			}else{
				$('#errorDivId').hide();
			}			
		}
}


function validateChequeDate(){

	var errorList = [];
	
	var transactionDate = $('#transactionDateId').val();
	if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
		errorList.push(getLocalMessage('account.select.transactiondate'));
		$("#coChequedate").val("");
	}
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
	var from = $("#transactionDateId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#coChequedate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Instrument date should not be greater than transaction date");
			$("#coChequedate").val("");
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

function validateChequeDateWith(){

	var errorList = [];
	
	var transactionDate = $('#transactionDateWithDrawId').val();
	if (transactionDate == "" || transactionDate == null || transactionDate == undefined){
		errorList.push(getLocalMessage('account.select.transactiondate'));
		$("#coChequedateWth").val("");
	}
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else {
	var from = $("#transactionDateWithDrawId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#coChequedateWth").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Instrument date should not be greater than transaction date");
			$("#coChequedateWth").val("");
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

function printdiv(printpage)
{
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}



/*  Suda Defect #178151  */

function addRow(tablName){
	var tblName = '#'+tablName;
	var clonedRow = tblName+" tbody tr:first";
	var insertedRow = tblName+" tbody tr:last";
	var rowCount = $(tblName).length;
	$(clonedRow).clone().insertAfter(insertedRow);
	$(insertedRow).find("select").val("").trigger("chosen:updated");
	$(insertedRow).find("input").val("");
	 reOrderTable(tablName);
}


function removeRow(rowNum, tablName){
    debugger;
    var tblName = '#'+tablName;
    var bodyRow= tblName+" tbody tr"
    var rowCount = $(bodyRow).length;
    if(rowCount > 1){
      $(bodyRow+":eq("+rowNum+")").remove();
      reOrderTable(tablName);
      }else{

      }
  }


function reOrderTable(tableName){
	var tblName = '#'+tableName;
	var bodyRow= tblName+" tbody tr";
	 var rowCount = $(bodyRow).length;
	 for (var i=0; i<=rowCount; i++){
		if(tableName == 'contraTransferPayTableDiv'){
			$(bodyRow).eq(i).find("select[id^=baAccountidPay]").attr('name','baAccountidPay'+i);
			 $(bodyRow).eq(i).find("select[id^=baAccountidPay]").attr('id','baAccountidPay'+i);
			 
			 $(bodyRow).eq(i).find("select[id^=chequebookDetid]").attr('name','chequebookDetid'+i);
			 $(bodyRow).eq(i).find("select[id^=chequebookDetid]").attr('id','chequebookDetid'+i);
			 
			 $(bodyRow).eq(i).find("input[id^=coChequedate]").attr('name','coChequedateStr'+i);
			 $(bodyRow).eq(i).find("input[id^=coChequedate]").attr('id','coChequedate'+i);
			 
			 $(bodyRow).eq(i).find("input[id^=coAmountPayTrns]").attr('name','coAmountPay'+i);
			 $(bodyRow).eq(i).find("input[id^=coChequedate]").attr('id','coAmountPayTrns'+i);	
			 
			 $(bodyRow).eq(i).find("a[id^=deleteAccount]").attr('onclick','removeRow('+i+',"contraTransferPayTableDiv")');
			 $(bodyRow).eq(i).find("input[id^=deleteAccount]").attr('id','deleteAccount'+i);
		}if(tableName == 'contraTransferRecptTableDiv'){
			
			$(bodyRow).eq(i).find("select[id^=baAccountidRec]").attr('name','baAccountidRec'+i);
			 $(bodyRow).eq(i).find("select[id^=baAccountidRec]").attr('id','baAccountidRec'+i);
			 
			/* $(bodyRow).eq(i).find("select[id^=chequebookDetid]").attr('name','chequebookDetid'+i);
			 $(bodyRow).eq(i).find("select[id^=chequebookDetid]").attr('id','chequebookDetid'+i);*/
			 
			 $(bodyRow).eq(i).find("input[id^=payToTrans]").attr('name','payTo'+i);
			 $(bodyRow).eq(i).find("input[id^=payToTrans]").attr('id','payToTrans'+i);
			 
			 $(bodyRow).eq(i).find("input[id^=payToTransUpd]").attr('name','payTo'+i);
			 $(bodyRow).eq(i).find("input[id^=payToTransUpd]").attr('id','payToTransUpd'+i);
			 
			 $(bodyRow).eq(i).find("input[id^=coAmountRecTrnsHidden]").attr('name','coAmountPay'+i);
			 $(bodyRow).eq(i).find("input[id^=coAmountRecTrnsHidden]").attr('id','coAmountRec'+i);
			 
			 $(bodyRow).eq(i).find("input[id^=coAmountRecTrns]").attr('name','coAmountRecDesc'+i);
			 $(bodyRow).eq(i).find("input[id^=coAmountRecTrns]").attr('id','coAmountRecTrns'+i);
			 
			 $(bodyRow).eq(i).find("a[id^=deleteAccountpay]").attr('onclick','removeRow('+i+',"contraTransferRecptTableDiv")');
			 $(bodyRow).eq(i).find("input[id^=deleteAccountpay]").attr('id','deleteAccountpay'+i);
		}
		 
	 }
	
}

