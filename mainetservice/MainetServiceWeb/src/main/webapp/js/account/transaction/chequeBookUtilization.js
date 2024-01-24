var lastsel2;
$(function() {
	$("#chequeBookGrid").jqGrid(
			{
				url : "ChequeBookUtilization.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '', "",getLocalMessage('accounts.receipt.cheque.number'),getLocalMessage('account.cheque.status'),getLocalMessage('account.cheque.cash.transaction'),getLocalMessage('account.transaction.date'),getLocalMessage('accounts.receipt.transaction.amount'),getLocalMessage('accounts.clearance.date'),getLocalMessage('bill.action')],
				colModel : [ {name : "paymentId",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name : "paymentType",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name : "chequeNo",width : 55,sortable : true, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
				             {name : "chequeStatusDesc",width : 55,editable : true, align : 'center',edittype:"select"
				             }, 
				             {name : "transactionNo",width : 65,sortable : false, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : "transactionDate",width : 65,sortable : false, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : "amountDesc",width : 65,sortable : true, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
				             {name : "clearanceDate",width : 65,sortable : false,searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : 'chequeId', index: 'coTranId', width:30 , align: 'center !important',formatter:addLink,search :false},
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "",
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
				caption : getLocalMessage("account.chequeBookUtili.cheUtiDetails")
			});
	 jQuery("#chequeBookGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});


function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.paymentId+"' id='"+rowdata.paymentId+"' ><i class='fa fa-building-o'></i></a>";
}

$(document).on('click', '.viewClass', function() {
	var $link = $(this);
	var id =  $link.closest('tr').find('td:eq(0)').text();
	var type = $link.closest('tr').find('td:eq(1)').text();
	var url ;
	var requestData;

	if(id!=''){
		if(type=="D"|| type=="T" || type=="W"){
			url = "DirectPaymentEntry.html?formForView";
			requestData = "id=" + id;
		}
		if(type=="P"){
			url = "PaymentEntry.html?formForView";
			requestData = "id=" + id;
		}
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		prepareDateTag();
	}
});



function searchUtilizationData(element){
	
	var chequeBookId = $("#chequeBookId").val();
	var url = "ChequeBookUtilization.html?searchChequeUtilization"
	var requestData = "chequeBookId="+chequeBookId;
	var response =__doAjaxRequestValidationAccor(element,url,'post',requestData,false);
	if(response!=false){
		if (response != null && response != '') {
			$("#chequeBookGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} 
	}
}





function getChequeRange(){
	
	var bankAccountId = $("#bankAccountId").val(); 	
	var url = "ChequeBookUtilization.html?getChequeNumberRange"
	var requestData = "bankAccountId="+bankAccountId;
	var response =__doAjaxRequest(url,'post',requestData,false);
	$('#chequeBookId').find('option:gt(0)').remove();
	var  optionsAsString='';
	$.each(response, function( key, value ) {
		optionsAsString += "<option value='" +key+"'>" + value + "</option>";
		});
	$('#chequeBookId').append(optionsAsString);
	$('#chequeBookId').trigger('chosen:updated');
	
}