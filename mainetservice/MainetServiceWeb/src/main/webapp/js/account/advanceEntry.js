
$("#tbAcAdvanceEntry").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

var dsgid = '';
var dsgid = '';
$(function() {
	
	$("#grid").jqGrid(
			{
				url : "AdvanceEntry.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['', getLocalMessage('advance.management.number'), getLocalMessage('advance.management.advancedate'),"", getLocalMessage('advance.management.name'), getLocalMessage('advance.management.advanceamount'),getLocalMessage('advance.management.advancebalance'),"",getLocalMessage('advance.management.status'),getLocalMessage('advance.management.Action'),'',''],//"Edit","View"
				colModel : [
				             {name : "prAdvEntryId",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 				        
				             {name : "advanceNumber",width : 10,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "advanceDate",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "vendorId",  sortable: true ,hidden:true}, 
				             {name : "vendorName",width : 35,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "advanceAmount",width : 25,sortable : true,classes:'text-right',searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "balanceAmount",width : 25,sortable : true,classes:'text-right',searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "pacHeadId",  sortable: true ,hidden:true}, 
				             {name : "cpdIdStatusDup",width : 20,sortable : true,align : 'center',editable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : 'prAdvEntryId', index: 'prAdvEntryId', width:30 , align: 'center !important',formatter:addLink,search :false},
				             {name : "sliStatusFlag",  sortable: true ,hidden:true},
				             {name : "categoryTypeId",  sortable: true ,hidden:true}
				             //{name : 'prAdvEntryId',index : 'prAdvEntryId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				             //{name : 'prAdvEntryId',index : 'prAdvEntryId',width : 30,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jqgrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "prAdvEntryId",
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
				caption : getLocalMessage('advance.management.headline2'),
				
				formatter: function (v) {
				    // uses "c" for currency formatter and "n" for numbers
				    return Globalize.format(Number(v), "c");
				},
				unformat: function (v) {
				    return Globalize.parseFloat(v);
				}
				
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	// $("#pagered_left").css("width", "");
});

function addLink(cellvalue, options, rowdata) 
{
	//User Story #6673 10. Rename bill label as Adjustment in the advance entry form action section.
	
   return "<a class='btn btn-blue-3 btn-sm viewBugopnBalMasterClass' title='View' value='"+rowdata.prAdvEntryId+"' prAdvEntryId='"+rowdata.prAdvEntryId+"' ><i class='fa fa-building-o'></i></a> "+
   		 "<a class='btn btn-warning btn-sm editBugopnBalMasterClass'  title='Edit' value='"+rowdata.prAdvEntryId+"' prAdvEntryId='"+rowdata.prAdvEntryId+"' ><i class='fa fa-pencil'></i></a> " +
   		 "<a class='btn btn-danger btn-sm' title='Receipt' onclick=\"doOpenReceiptPageForAdvance('"+rowdata.prAdvEntryId+"','"+rowdata.advanceNumber+"','"+rowdata.advanceDate+"','"+rowdata.vendorId+"','"+rowdata.balanceAmount+"','"+rowdata.pacHeadId+"','"+rowdata.cpdIdStatusDup+"','"+rowdata.categoryTypeId+"')\"><i class='fa fa-file-text-o'></i></a> " +
   		 "<a class='btn btn-success btn-sm' title='Adjustment' onclick=\"doOpenBillPageForAdvance('"+rowdata.prAdvEntryId+"','"+rowdata.advanceNumber+"','"+rowdata.advanceDate+"','"+rowdata.vendorId+"','"+rowdata.balanceAmount+"','"+rowdata.pacHeadId+"','"+rowdata.cpdIdStatusDup+"')\"><i class='fa fa-file-text text-white'></i></a> ";
		 /*"<button class='btn btn-success btn-sm ' title='Receipt' onclick=\"doOpenReceiptPageForAdvance('"+rowdata.prAdvEntryId+"','"+rowdata.advanceNumber+"','"+rowdata.advanceDate+"','"+rowdata.vendorId+"','"+rowdata.balanceAmount+"','"+rowdata.pacHeadId+"','"+rowdata.cpdIdStatusDup+"')\">Receipt</button> ";*/
}

function returnisdeletedUrl(cellValue, options, rowdata, action) {

	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"
				+ rowdata.isdeleted
				+ "'  alt='Designation is Active' title='Designation is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"
				+ rowdata.isdeleted
				+ "' alt='Designation is  INActive' title='Designation is InActive'></a>";
	}

}
/*function returnEditUrl(cellValue, options, rowdata, action) {
	prAdvEntryId = rowdata.prAdvEntryId;
	return "<a href='#'  return false; class='editBugopnBalMasterClass' value='"+prAdvEntryId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}
*/
/*function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewBugopnBalMasterClass' value='"+prAdvEntryId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}*/

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

$(document).ready(function() {
	//debugger
	$('.error-div').hide();
	var Error_Status = '${Errore_Value}';
	
	 //#79497
	 $("#paymentDate").attr("readonly",true);

});

function closeOutErrBox() {
	$('.error-div').hide();
}


$(function() {	
	$(document).on('click', '.createData', function() {
	
		var $link = $(this);
		/*var spId = $link.closest('tr').find('td:eq(0)').text();*/
		var prAdvEntryId = 1;
		var url = "AdvanceEntry.html?form";
		var requestData = "prAdvEntryId=" + prAdvEntryId  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		
		return false;		
	});			

	$(document).on('click', '.editBugopnBalMasterClass', function() {
		var errorList = [];
		var $link = $(this);
		
		/* var paAdjid = $link.closest('tr').find('td:eq(0)').text(); */
		var prAdvEntryId = $link.closest('tr').find('td:eq(0)').text(); 
		var authStatus = $link.closest('tr').find('td:eq(8)').text();
		var sliStatusFlag = $link.closest('tr').find('td:eq(10)').text();
		var url = "AdvanceEntry.html?formForUpdate";
		var requestData = "prAdvEntryId=" + prAdvEntryId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Close" && sliStatusFlag!="Y"){
			$('.content').html(returnData);
		}
		else{
			if(authStatus=="Close"){
				errorList.push(getLocalMessage("account.edit.not.allowed"));
			}
			else if(sliStatusFlag=="Y"){
				errorList.push("Edit is denied for integrated advance entry. !");
			}
			
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    	});
		    	errorMsg +='</ul>';
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
		    }
		}
		
	});
	
	$(document)
	.on(
			'click',
			'.viewBugopnBalMasterClass',
			function() {
				var $link = $(this);
				var prAdvEntryId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AdvanceEntry.html?formForView";
				var requestData = "prAdvEntryId=" + prAdvEntryId + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				return false;
			});

});


function doOpenReceiptPageForAdvance(prAdvEntryId,advanceNumber,advanceDate,vendorId,balanceAmount,pacHeadId,cpdIdStatusDup,categoryTypeId){
    var errorList = [];
    
	var gr = jQuery("#grid").jqGrid('getGridParam','selrow');	
	var url = "AccountReceiptEntry.html?createformFromAdvance";
	var requestData = {"prAdvEntryId" : prAdvEntryId,"advanceNumber" : advanceNumber,"advanceDate" : advanceDate,"vendorId" : vendorId,"balanceAmount" : balanceAmount,"pacHeadId" : pacHeadId, "categoryTypeId" : categoryTypeId ,"MODE"  :"CREATE"};
	if(cpdIdStatusDup!="Close"){
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);	
	}
	else{
		
		errorList.push(getLocalMessage("account.close.receipt"));
		if(errorList.length>0){
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    	});
	    	errorMsg +='</ul>';
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
	    }
	}
	var divName = '.content';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	return false;
};

function doOpenBillPageForAdvance(prAdvEntryId,advanceNumber,advanceDate,vendorId,balanceAmount,pacHeadId,cpdIdStatusDup){
    var errorList = [];
	var gr = jQuery("#grid").jqGrid('getGridParam','selrow');	
	var url = "AccountBillEntry.html?createformFromAdvance";
	var requestData = {"prAdvEntryId" : prAdvEntryId,"advanceNumber" : advanceNumber,"advanceDate" : advanceDate,"vendorId" : vendorId,"balanceAmount" : balanceAmount,"pacHeadId" : pacHeadId,"MODE"  :"CREATE"};
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	if(cpdIdStatusDup!="Close"){
		$('.content').html(returnData);
	}
	else{
		errorList.push(getLocalMessage("account.bill.not.allowed"));
		if(errorList.length>0){
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    	});
	    	errorMsg +='</ul>';
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
	    }
	}

};


$(function() {

	$(document).on('click', '.deleteDesignationClass', function() {
		var $link = $(this);
		var dsgid = $link.closest('tr').find('td:eq(0)').text();
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
		showConfirmBoxEmployee(dsgid);

	});	

});


$( document ).ready(function() {
	
	$('.error-div').hide();
	var errorList = [];
	
	var  status= $("#cpdIdStatusFlagDup").val();
	
	if(status === 'I') {
		errorList.push("You can not edit Inactive Budget Head!");
	}
	
		if(errorList.length>0){
    	
		var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    	});
    	errorMsg +='</ul>';
    	$('#errorIdI').html(errorMsg);
    	$('#errorDivIdI').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
	}
		
	$('.hasMyNumber').keyup(function () {
		/*this.value = this.value.replace(/[^0-9]+\/.?[^0-9]*$/,'');*/
	    this.value = this.value.replace(/[^0-9.]/g,'');
	    $(this).attr('maxlength','13');
	});
	
	$(".datepiker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,	
		maxDate: '-0d',
	});
	
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,	
		maxDate: '-0d',
	});
	//User Story #6673 14. Remove default current advance date from the advance entry search form.
	//$(".datepicker").datepicker('setDate', new Date()); 
	
});

function displayAddView(obj) {
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var divName = childDivName;

	var requestData = __serializeForm(theForm);

	var formAction = $(theForm).attr('action');

	var url = formAction + '?create';

	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$(divName).html(returnData);

	prepareDateTag();
}


$(".warning-div ul")
		.each(
				function() {
					var lines = $(this).html().split("<br>");
					$(this)
							.html(
									'<li>'
											+ lines
													.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;")
											+ '</li>');
				});
$('html,body').animate({
	scrollTop : 0
}, 'slow');

 var objTemp;
function saveLeveledData(obj){
	
	objTemp=obj;
	var errorList = [];
	var  advanceDate= $("#advanceDate").val();
	var  paymentDate= $("#paymentDate").val();
	var  paymentOrderDate= $("#paymentOrderDate").val();
	if(advanceDate!=null)
		{
		errorList = validatedate(errorList,'advanceDate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push(getLocalMessage("account.receipt.sli"));
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#advanceDate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date > sliDate) {
				errorList.push(getLocalMessage("account.advance.date.less.sli"));
			  }
			}
		  }
		}
	if(paymentDate!=null)
		{
		errorList = validatedate(errorList,'paymentDate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push(getLocalMessage("account.receipt.sli"));
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#paymentDate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date > sliDate) {
				errorList.push(getLocalMessage("account.payment.date.less.sli"));
			  }
			}
		  }
		}
	if(paymentOrderDate!=null)
	{
	errorList = validatedate(errorList,'paymentOrderDate');
	if (errorList.length == 0) {
		var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
		if(response == "Y"){
			errorList.push(getLocalMessage("account.receipt.sli"));
		}else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var date = new Date($("#paymentOrderDate").val().replace(pattern,'$3-$2-$1'));
		var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
		if (date > sliDate) {
			errorList.push(getLocalMessage("account.paymentOrder.less.advance"));
		  }
		}
	  }
	}
	 var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	  var eDate = new Date($("#paymentOrderDate").val().replace(pattern,'$3-$2-$1'));
	  var eDate1 = new Date($("#paymentDate").val().replace(pattern,'$3-$2-$1'));
	  var sDate = new Date($("#advanceDate").val().replace(pattern,'$3-$2-$1'));
	  if (eDate > sDate) {
		  errorList.push(getLocalMessage("account.paymentOrder.less.advance"));
	    }
	  if (eDate1 > sDate) {
		  errorList.push(getLocalMessage("account.payment.date.less.advance"));
	    }
	var  advanceType= $("#advanceType").val();
	var  advanceDate= $("#advanceDate").val();
	var  pacHeadId= $("#pacHeadId").val();
	var  vendorName= $("#vendorName").val();
	var  advanceAmount= $("#advanceAmount").val();
	var  balanceAmount= $("#balanceAmount").val();
	var  partOfAdvance= $("#partOfAdvance").val();
	var  departmentId= $("#departmentId").val();
	//var  paymentOrderNo= $("#paymentOrderNo").val();
	//
	var  paymentNumber= $("#paymentNumber").val();
	
	var  amount= $("#amount").val();
	
	if(advanceDate == '' || advanceDate =="0") {
		errorList.push(getLocalMessage("account.advance.date"));
	}
	
	if(advanceType == '' || advanceType =="0") {
		errorList.push(getLocalMessage("account.select.advanceType"));
	}
	
	if(pacHeadId == '' || pacHeadId =="0") {
		errorList.push(getLocalMessage("account.select.acchead"));
	}
	
	if(vendorName == '' || vendorName =="0") {
		errorList.push(getLocalMessage("account.vendor.or.employee.name"));
	}
	
	if(advanceAmount == '' || advanceAmount =="0") {
		errorList.push(getLocalMessage("account.advance.amount"));
	}
	
	if(balanceAmount == '' || balanceAmount =="0") {
		errorList.push(getLocalMessage("account.balance.ammount"));
	}
	
	if(partOfAdvance == '' || partOfAdvance =="0") {
		errorList.push(getLocalMessage("accounts.advance.narration"));
	}
	
	if(paymentOrderNo == '' || paymentOrderNo =="0") {
		errorList.push(getLocalMessage("account.enter.requisition"));
	}
	
	/*if(paymentOrderDate == '' || paymentOrderDate =="0") {
		errorList.push("Please Enter Requisition Date");
	}
	*/
	if(paymentNumber == '' || paymentNumber =="0") {
		errorList.push(getLocalMessage("account.enter.paymentNum"));
	}
	
	if(paymentDate == '' || paymentDate =="0") {
		errorList.push(getLocalMessage("account.enter.paymentDate"));
	}
	
	if(amount == '' || amount =="0") {
		errorList.push(getLocalMessage("account.enter.paymentAmt"));
	}
	if(departmentId == '' || departmentId =="0") {
		errorList.push(getLocalMessage("account.bill.entry.department"));
	}
	
	 if(errorList.length>0){
	    	
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    		
	    	});
	    	errorMsg +='</ul>';
	    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
	    		    	
	    }
	    else{				

	    //return saveOrUpdateForm(obj, 'Saved Successfully', 'AdvanceEntry.html','create');
	    	showConfirmBoxSave();	
	    /*var	formName =	findClosestElementId(obj, 'form');
	    var theForm	=	'#'+formName;
	    var requestData = __serializeForm(theForm);
	    var url	=	$(theForm).attr('action');
	    
	    var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
	    if(response != false){
	       $('.content').html(response);
	    }*/
	    
	    //var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
	    //$('.content').html(response);
	}
    
}


function showConfirmBoxSave(){

	var saveorAproveMsg=getLocalMessage("account.btn.save.msg");
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\">'+ ""+saveorAproveMsg+""+ '</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>  '+	
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
	
	
	var formName = findClosestElementId(objTemp, 'form');
	var theForm = '#' + formName;
    var requestData = __serializeForm(theForm);
    var url = $(theForm).attr('action');
    
    var response= __doAjaxRequestValidationAccor(objTemp,url+'?create', 'post', requestData, false, 'html');
    var obj=$(response).find('#successFlag'); 
    if(obj.val()=='Y')
    	{
    	showConfirmBox();
    	}
     else
    	{
    	 $(".widget-content").html(status);
	     $(".widget-content").show();
    	}
	
}
function showConfirmBox(){
	debugger;
	var formMode_Id = $("#formMode_Id").val();	
	if(formMode_Id=="create"){
		var successMsg = getLocalMessage("account.submit.succ");
	}else if(formMode_Id=="EDIT"){
		var successMsg = getLocalMessage("account.update.succ");
	}
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage("account.secondaryvalidationmsg.proceed");
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}




/*function setSecondaryCodeFinance(count)
{
	 var primaryCode=$('#pacHeadId'+count).val();
	 
		$('#sacHeadId'+count).find('option:gt(0)').remove();
		
		if (primaryCode > 0) {
			var postdata = 'pacHeadId=' + primaryCode;
			
			var json = __doAjaxRequest('AdvanceEntry.html?sacHeadItemsList',
					'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#sacHeadId'+count).append(optionsAsString );
			
		}
}*/	

function setHiddenData(){
	 $('#secondaryId').val($('#sacHeadId0').val());
}
/*
$(document).ready(function()
		{
	
	var primaryCode=$('#pacHeadId0').val();
	$('#sacHeadId0').find('option:gt(0)').remove();
	
	if (primaryCode > 0) {
		var postdata = 'pacHeadId=' + primaryCode;
		
		var json = __doAjaxRequest('AdvanceEntry.html?sacHeadItemsList',
				'POST', postdata, false, 'json');
		var  optionsAsString='';
		$.each( json, function( key, value ) {
			optionsAsString += "<option value='" +key+"'>" + value + "</option>";
			});
		$('#sacHeadId0').append(optionsAsString );
			
	 }
	
	});*/
function closeErrBox() {
	$('.error-div').hide();
}

function searchAdvanceEntryData(){

	 	/*alert("In search");*/
		
		$('.error-div').hide();

		var errorList = [];

		var advanceNumber = $("#advanceNumber").val();
		var advanceDate = $("#advanceDate").val();
		var name = $("#name").val();
		var advanceAmount = $("#advanceAmount").val();
		var advanceType = $("#advanceType").val();
		var cpdIdStatus = $("#cpdIdStatus").val();

		if ((advanceNumber == "" || advanceNumber =="0") && (advanceDate == "" || advanceDate =="0") && (name == "" || name =="0") && (advanceAmount == "" || advanceAmount =="0") && (advanceType == "" || advanceType =="0") && (cpdIdStatus == "" || cpdIdStatus =="0")) {
			errorList.push(getLocalMessage("account.deposit.search.validation"));
		}
		if(advanceDate != null)
			{
			errorList = validatedate(errorList,'advanceDate');
			}
		
		if(errorList.length>0){
	    	
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    		
	    	});
	    	errorMsg +='</ul>';
	    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
	    		    	
	    }
		 
		if (errorList.length == 0) 
		{
		
		var url ="AdvanceEntry.html?getjqGridsearch";
		var requestData = {
				"advanceNumber" : advanceNumber,
				"advanceDate" : advanceDate,
				"name" : name,
				"advanceAmount" : advanceAmount,
				"advanceType" : advanceType,
				"cpdIdStatus" : cpdIdStatus,
			};
		
		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
		
		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} else {
			var errorList = [];
			
			errorList.push(getLocalMessage("account.norecord.criteria"));
			
			if(errorList.length>0){		    	
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';		    		
		    	});
		    	errorMsg +='</ul>';		    	
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');		    		    	
		    }
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}
};
	
	function displayMessageOnSubmit(successMsg){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		
		message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
		 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
		 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showPopUpMsg(errMsgDiv);
	}

	function redirectToDishonorHomePage () {
		$.fancybox.close();
		window.location.href='AdvanceEntry.html';
	}

	function showPopUpMsg(childDialog){
		$.fancybox({
	        type: 'inline',
	        href: childDialog,
	        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
	        closeBtn : false ,
	        helpers: {
				overlay : {
					closeClick : false
				}
			},
			 keys : {
				    close  : null
				  }
	    });
		return false;
	}

	
	//to generate dynamic table
	$("#budRevTableDivID").on("click", '.addButton', function(e) {
	var errorList = [];

	   $('.appendableClass').each(function(i) {
			 
		   	 var functionId = $.trim($("#functionId"+i).val());
			 if(functionId==0 || functionId=="") 
			 errorList.push("Please select Function");
			 
			 var sacHeadId = $.trim($("#pacHeadId"+i).val());
			 if(sacHeadId==0 || sacHeadId=="") 
			 errorList.push("Please select Account Heads");
			
			 var prBudgetCode = $.trim($("#prBudgetCode"+i).val());
			 if(prBudgetCode==0 || prBudgetCode=="") 
			 errorList.push("Please Enter Budget Head Description");
			 
			/* var cpdIdStatusFlag = $.trim($("#cpdIdStatusFlag"+i).val());
			 alert(cpdIdStatusFlag);
			 if(cpdIdStatusFlag==0 || cpdIdStatusFlag=="")
			 errorList.push("Please Select Status Flag");*/
			
			 for(m=0; m<i;m++){
					if(($('#functionId'+m).val() == $('#functionId'+i).val()) && ($('#pacHeadId'+m).val() == $('#pacHeadId'+i).val())){
						
						errorList.push("The Combination AccountHead code already exists!");
					}
				}

			$("#indexdata").val(i);
	   });
				if (errorList.length > 0) {
					$('#index').val(i);
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
				e.preventDefault();
				var content = $(this).closest('#budRevTable tr').clone();
				// call that script function which enable for search
				// remove extra added div by id 
				
				$(this).closest("#budRevTable").append(content);
				// reset values
				//content.find("select").attr("value", "");
				content.find("input:text").val("");
				content.find("textarea").val("");
				//content.find("select").val("");
				content.find("input:checkbox").attr('checked',false);
				content.find('div.chosen-container').remove();
				content.find("select").chosen().trigger("chosen:updated");
				reOrderTableIdSequence();

			});

		
		//to delete row
		$("#budRevTableDivID").on("click", '.delButton', function(e) {
		
		var rowCount = $('#budRevTable tr').length;
		if (rowCount <= 2) {
		//alert("Can Not Remove");
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
		}
		
		$(this).closest('#budRevTable tr').remove();
		reOrderTableIdSequence();
		e.preventDefault();
		});

	function reOrderTableIdSequence() {
	$('.appendableClass').each(function(i) {
			
			//$(this).find("select:eq(0)").attr("id", "pacHeadId" + i);
			$(this).find("select:eq(0)").attr("id", "functionId" + i);
			$(this).find("input:text:eq(0)").attr("id", "functionId" + i);
			$(this).find("select:eq(1)").attr("id", "pacHeadId" + i);
			$(this).find("input:text:eq(1)").attr("id", "pacHeadId" + i);
			//$(this).find("select:eq(2)").attr("id", "cpdIdStatusFlag" + i);
			$(this).find($('[id^="prBudgetCode"]')).attr("id", "prBudgetCode" + i);
			//$(this).find("input:textarea:eq(0)").attr("id", "prBudgetCode" + i);
			//$(this).find("input:text:eq(4)").attr("id", "prCollected" + i);
			//$(this).find("input:text:eq(5)").attr("id", "prBudgetCode" + i);
			
			$(this).find("select:eq(0)").attr("name","budgCodeMasterDtoList[" + i + "].functionId");
			$(this).find("input:text:eq(0)").attr("name","budgCodeMasterDtoList[" + i + "].functionId");
			$(this).find("select:eq(1)").attr("name","budgCodeMasterDtoList[" + i + "].sacHeadId");
			$(this).find("input:text:eq(1)").attr("name","budgCodeMasterDtoList[" + i + "].sacHeadId");
			//$(this).find("select:eq(2)").attr("name","budgCodeMasterDtoList[" + i + "].cpdIdStatusFlagDup");
			//$(this).find("input:textarea:eq(0)").attr("name","budgCodeMasterDtoList[" + i + "].prBudgetCode");
			//$(this).find("input:text:eq(4)").attr("name","budgCodeMasterDtoList[" + i + "].prCollected");
			//$(this).find("input:text:eq(5)").attr("name","budgCodeMasterDtoList[" + i + "].prBudgetCode");
			$(this).find($('[id^="prBudgetCode"]')).attr("name","budgCodeMasterDtoList[" + i + "].prBudgetCode");
			$(this).find('.delButton').attr("id",	"delButton" + i);
			$(this).find('.addButton').attr("id",	"addButton" + i);
			$(this).find('#functionId'+i).attr("onchange", "clearFormData(" + (i) + ")");
			$(this).find('#pacHeadId'+i).attr("onchange", "findduplicatecombinationexit(" + (i) + ")");
			$(this).closest("tr").attr("id", "budRevId" + (i));
			$("#indexdata").val(i);
			
		});

	}

	function findduplicatecombinationexit(cnt) {
		if ($('#count').val() == "") {
			count = 1;
		} else {
			count = parseInt($('#count').val());
		}
		var assign = count;

		$('.error-div').hide();
		var errorList = [];

		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		
		var functionId = $('#functionId'+cnt).val();
		
		if (functionId == '') {
			errorList.push('Please Select Function');
			var pacHeadId = $('#pacHeadId'+cnt).val(""); 
			$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
		}
		
		var id =  $("#indexdata").val();
		 
		var functionId = $('#functionId'+id).val(); 
		var pacHeadId = $('#pacHeadId'+id).val();
		
			/*var j =  $("#indexdata").val();
			 for(m=0; m<j;m++){
				 
					if(($('#pacHeadId'+m).val() == $('#pacHeadId'+j).val())){
						
						errorList.push("The Combination AccountHead already exists!");
						var pacHeadId = $("#pacHeadId"+j).val("");
						//$("#prCollected"+Revid).prop("disabled",true);
					}
			 }*/
			 
			 var Revid =  $("#indexdata").val();
				if(pacHeadId != "" ){
					var dec;
				 for(m=0; m<=Revid;m++){
					 for(dec=0; dec<=Revid;dec++){
						 if(m!=dec){
						if(($('#functionId'+m).val() == $('#functionId'+dec).val()) && ($('#pacHeadId'+m).val() == $('#pacHeadId'+dec).val())){
							errorList.push("The Combination AccountHead already exists!");
							$('#functionId'+cnt).val(""); 
							$("#functionId"+cnt).val('').trigger('chosen:updated');
							$("#pacHeadId"+cnt).val("");
							$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
							var orginalEstamt = $("#orginalEstamt"+cnt).val("");
						}
					  }
					} 
				  }
				}
				
			 
			 if(errorList.length>0){
			    	
			    	var errorMsg = '<ul>';
			    	$.each(errorList, function(index){
			    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			    		
			    	});
			    	errorMsg +='</ul>';
			    	
			    	$('#errorId').html(errorMsg);
			    	$('#errorDivId').show();
					$('html,body').animate({ scrollTop: 0 }, 'slow');
			    		    	
			    }
			 
		if (errorList.length == 0) {
			
			if ($('#cpdBugtypeId').val() == "") {
				msgBox('Please Select Budget Type');
				var sacHeadId = $('#pacHeadId'+cnt).val("");
				$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
				return false;
			}
			
			if ($('#cpdBugsubtypeId').val() == "") {
				msgBox('Please Select Budget Sub Type');
				var sacHeadId = $('#pacHeadId'+cnt).val("");
				$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
				return false;
			}
			
			if ($('#dpDeptid').val() == "") {
				msgBox('Please Select Department');
				var sacHeadId = $('#pacHeadId'+cnt).val("");
				$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
				return false;
			}
			
			var url = "AdvanceEntry.html?getBudgetRevDuplicateGridloadData&cnt="+cnt;

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			 if(returnData){
				 
				errorList.push("Account Budget Code is Already Exists, against this Account Head!"); 
				var sacHeadId = $('#pacHeadId'+cnt).val("");
				$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
				$('#functionId'+cnt).val(""); 
				$("#functionId"+cnt).val('').trigger('chosen:updated');
				$('#prBudgetCode'+cnt).val("");
				
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			  		$.each(errorList, function(index) {
			  			var errorMsg = '<ul>';
				    	$.each(errorList, function(index){
				    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				    	});
				    	errorMsg +='</ul>';	
				    	$('#errorId').html(errorMsg);
				    	$('#errorDivId').show();
						$('html,body').animate({ scrollTop: 0 }, 'slow');
			  		});
			  		return false;
			}
		}
		
		if (errorList.length == 0) {

			var url = "AdvanceEntry.html?getBudgetHeadDescDetails&cnt="+cnt;

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			$.each(returnData, function(key, value) {
				
				$('#prBudgetCode'+cnt).val(value);
				
			});

		}
	};
	
	function clearFormData(cnt) {

		 $('.error-div').hide();
			var errorList = [];

		var pacHeadId = $('#pacHeadId'+cnt).val(""); 
		$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
		var prBudgetCode = $('#prBudgetCode'+cnt).val(""); 
		
		var theForm = '#frmMaster';

		var dpDeptid = $('#dpDeptid').val();
		var cpdBugtypeId = $('#cpdBugtypeId').val();
		var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();
		
		var  fundId= $("#fundId").val();
		var  fieldId= $("#fieldId").val();
		
		if(fundId == '') {
			errorList.push("Please select Fund");	
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}
		if(fieldId == '') {
			errorList.push("Please select Field");
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}
		
		if (dpDeptid == '') {
			errorList.push('Please Select Department');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}
		
		if (cpdBugtypeId == '') {
			errorList.push('Please Select Budget Type');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}

		if (cpdBugsubtypeId == '') {
			errorList.push('Please Select Budget Sub Type');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}

			if(errorList.length>0){
		    	
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    		
		    	});
		    	errorMsg +='</ul>';
		    	
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
		    		    	
		    }
		};

		function clearAllData(obj) {
			
			var functionId = $('#functionId0').val(""); 
			$("#functionId0").val('').trigger('chosen:updated');
			
			var pacHeadId = $('#pacHeadId0').val(""); 
			$("#pacHeadId0").val('').trigger('chosen:updated');
			
			var cpdIdStatusFlag = $('#cpdIdStatusFlag0').val(""); 
			
			$('.error-div').hide();
			var errorList = [];
			
				if (errorList.length == 0) {
				
				var divName = ".content";
				
				var formName = findClosestElementId(obj, 'form');

				var theForm = '#' + formName;

				var requestData = __serializeForm(theForm);

				var url = "AdvanceEntry.html?getjqGridload";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
			}
		}
		
		function loadBudgetReappropriationData(obj) {

			$('.error-div').hide();
			var errorList = [];
			
			var functionId = $('#functionId0').val(""); 
			$("#functionId0").val('').trigger('chosen:updated');
			
			var pacHeadId = $('#pacHeadId0').val(""); 
			$("#pacHeadId0").val('').trigger('chosen:updated');
			
			//var prBudgetCode = $('#prBudgetCode0').val(""); 
			
			//$('#hiddenFinYear').val($('#faYearid').val());
			
			var cpdBugtypeId = $('#cpdBugtypeId').val();
			//var dpDeptid = $('#dpDeptid').val();

			if (cpdBugtypeId == '') {
				errorList.push('Please Select Budget Type');
				var cpdBugsubtypeId = $('#cpdBugsubtypeId').val("");
			}
			
			if(errorList.length>0){
		    	
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    		
		    	});
		    	errorMsg +='</ul>';
		    	
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
		    		    	
		    }
			
			if (errorList.length == 0) {
				
				var divName = ".content";
				
				var formName = findClosestElementId(obj, 'form');

				var theForm = '#' + formName;

				var requestData = __serializeForm(theForm);

				var url = "AdvanceEntry.html?getjqGridload";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
				//$('#faYearid').prop('disabled', 'disabled');

			}
		};
		
		function copyContent(obj) {

			var balanceAmount = $('#balanceAmount').val("");
			var amount = $('#amount').val("");
			
			var stt = 0;
			stt = parseFloat($('#advanceAmount').val());
			if ((stt != undefined && !isNaN(stt))) {
				var num = (stt);
				var result = num.toFixed(2);
				$("#balanceAmount").val(result);
				$("#amount").val(result);
			}	
		}

		function minMaxBalanceAmount(obj){
			
			var minAmt = ($('#advanceAmount').val());
			var maxAmt = ($('#balanceAmount').val());
			  
			  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
			    try{
			      maxAmt = parseFloat(maxAmt);
			      minAmt = parseFloat(minAmt);

			      if(maxAmt > minAmt) {
			        var msg = "Balance amount can not be greater than Advance Amount.!";
			    	  showErrormsgboxTitle(msg);
			        $('#balanceAmount').val("");
			        return false;
			      }
			    }catch(e){
			      return false;
			    }
			  } //end maxAmt minAmt comparison
			  
			  	var balanceAmount = $('#balanceAmount').val();

				if (balanceAmount != undefined && !isNaN(balanceAmount) && balanceAmount != null && balanceAmount != '') {
					
				var actualAmt = balanceAmount.toString().split(".")[0];
				var decimalAmt = balanceAmount.toString().split(".")[1];
				
				var decimalPart =".00";
				if(decimalAmt == null || decimalAmt == undefined){
					$('#balanceAmount').val(actualAmt+decimalPart);
				}else{
					if(decimalAmt.length <= 0){
						decimalAmt+="00";
						$('#balanceAmount').val(actualAmt+(".")+decimalAmt);
					}
					else if(decimalAmt.length <= 1){
						decimalAmt+="0";
						$('#balanceAmount').val(actualAmt+(".")+decimalAmt);
					}else{
						if(decimalAmt.length <= 2){
						$('#balanceAmount').val(actualAmt+(".")+decimalAmt);
						} 
					  }	
				   }
			    }
		}
		
		function minMaxPaymentAmount(obj){
			
			var minAmt = ($('#advanceAmount').val());
			var maxAmt = ($('#amount').val());
			  
			  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
			    try{
			      maxAmt = parseFloat(maxAmt);
			      minAmt = parseFloat(minAmt);

			      if(maxAmt > minAmt) {
			        var msg = "Payment amount can not be greater than Advance Amount.!";
			    	  showErrormsgboxTitle(msg);
			        $('#amount').val("");
			        return false;
			      }
			    }catch(e){
			      return false;
			    }
			  } //end maxAmt minAmt comparison 
			  
			    var amount = $('#amount').val();

				if (amount != undefined && !isNaN(amount) && amount != null && amount != '') {
					
				var actualAmt = amount.toString().split(".")[0];
				var decimalAmt = amount.toString().split(".")[1];
				
				var decimalPart =".00";
				if(decimalAmt == null || decimalAmt == undefined){
					$('#amount').val(actualAmt+decimalPart);
				}else{
					if(decimalAmt.length <= 0){
						decimalAmt+="00";
						$('#amount').val(actualAmt+(".")+decimalAmt);
					}
					else if(decimalAmt.length <= 1){
						decimalAmt+="0";
						$('#amount').val(actualAmt+(".")+decimalAmt);
					}else{
						if(decimalAmt.length <= 2){
						$('#amount').val(actualAmt+(".")+decimalAmt);
						} 
					  }	
				   }
			    }
		}
		
		  function getBudgetHeadOnAdvanceType(obj) {

				$('.error-div').hide();
				var errorList = [];
				
				$("#pacHeadId").val(0);	
				$("#pacHeadId").trigger("chosen:updated");
				
				var accountType = $("#advanceType").val();
				
			    if(accountType== 0 || accountType ==""){
					//errorList.push(getLocalMessage('Please select Account type'));
				}
				 
			    if(errorList.length > 0){ 
			    	showError(errorList);
			    return false;
			    }
				
				if (errorList.length == 0) {
					
					var divName = ".content";
					
					var formName = findClosestElementId(obj, 'form');

					var theForm = '#' + formName;

					var requestData = __serializeForm(theForm);
					
					var url = "AdvanceEntry.html?getBudgetHeadCodeDesc";	

					var response = __doAjaxRequest(url, 'post', requestData, false);
					
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					$(divName).show();

				}
			};
			
			function changeValidLiveAdvanceDate(obj){
				debugger;
				$('.error-div').hide();
				var errorList = [];
				/*var maxDate = ($('#liveModeDate').val());
				var minDate = ($('#advanceDate').val());
			
				var arr = minDate.split('/');
				var day=arr[0];
				var month=arr[1];
				var year=arr[2];
				var curArr = month+"/"+day+"/"+year;
				
				var D1 = new Date(maxDate); 
				var D2 = new Date(curArr);*/ 
			
				var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
				var D1 = new Date($("#liveModeDate").val().replace(pattern,'$3-$2-$1'));
				var D2 = new Date($("#advanceDate").val().replace(pattern,'$3-$2-$1'));
				
				if(D2<D1)
				{
				  $("#paymentDate").val($("#advanceDate").val());
				  $("#paymentDate").attr("readonly",true);
				}
				else{
					errorList.push(getLocalMessage('account.advanceDate.notEqual.greater.sli.prefixDate'));
					$('#advanceDate').val("");
				}
				 if(errorList.length>0){
				    	var errorMsg = '<ul>';
				    	$.each(errorList, function(index){
				    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				    		
				    	});
				    	errorMsg +='</ul>';			    	
				    	$('#errorId').html(errorMsg);
				    	$('#errorDivId').show();
						$('html,body').animate({ scrollTop: 0 }, 'slow');
				    		    	
				 }
			}
			
function changeValidLiveRequisitionDate(obj){
				
				$('.error-div').hide();
				var errorList = [];
				
				/*var maxDate = ($('#liveModeDate').val());
				var minDate = ($('#paymentOrderDate').val());
				
				var arr = minDate.split('/');
				var day=arr[0];
				var month=arr[1];
				var year=arr[2];
				var curArr = month+"/"+day+"/"+year;
				
				var D3 = new Date(maxDate); 
				var D4 = new Date(curArr);*/ 
				
				var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
				var D3 = new Date($("#liveModeDate").val().replace(pattern,'$3-$2-$1'));
				var D4 = new Date($("#paymentOrderDate").val().replace(pattern,'$3-$2-$1'));
				
				if(D4<D3)
				{
				  
				}
				else{
					errorList.push(getLocalMessage('account.requisitionDate.notEqual.greater.sli.prefixDate'));
					$('#paymentOrderDate').val("");
				}
				 if(errorList.length>0){
				    	var errorMsg = '<ul>';
				    	$.each(errorList, function(index){
				    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				    		
				    	});
				    	errorMsg +='</ul>';			    	
				    	$('#errorId').html(errorMsg);
				    	$('#errorDivId').show();
						$('html,body').animate({ scrollTop: 0 }, 'slow');
				    		    	
				 }
			}
			
function changeValidLivePaymentDate(obj){
	
	$('.error-div').hide();
	var errorList = [];
	
	/*var maxDate = ($('#liveModeDate').val());
	var minDate = ($('#paymentDate').val());
	
	var arr = minDate.split('/');
	var day=arr[0];
	var month=arr[1];
	var year=arr[2];
	var curArr = month+"/"+day+"/"+year;
	
	var D5 = new Date(maxDate); 
	var D6 = new Date(curArr);*/ 
	
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var D5 = new Date($("#liveModeDate").val().replace(pattern,'$3-$2-$1'));
	var D6 = new Date($("#paymentDate").val().replace(pattern,'$3-$2-$1'));
	
	if(D6<D5)
	{
	  
	}
	else{
		errorList.push(getLocalMessage('account.paymentDate.notEqual.greater.sli.prefixDate'));
		$('#paymentDate').val("");
	}
	 if(errorList.length>0){
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    		
	    	});
	    	errorMsg +='</ul>';			    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
	    		    	
	 }
}

	function getAmountFormatAdvance(obj){
	
	var advanceAmount = $('#advanceAmount').val();

	if (advanceAmount != undefined && !isNaN(advanceAmount) && advanceAmount != null && advanceAmount != '') {
		
	var actualAmt = advanceAmount.toString().split(".")[0];
	var decimalAmt = advanceAmount.toString().split(".")[1];
	
	var decimalPart =".00";
	if(decimalAmt == null || decimalAmt == undefined){
		$('#advanceAmount').val(actualAmt+decimalPart);
	}else{
		if(decimalAmt.length <= 0){
			decimalAmt+="00";
			$('#advanceAmount').val(actualAmt+(".")+decimalAmt);
		}
		else if(decimalAmt.length <= 1){
			decimalAmt+="0";
			$('#advanceAmount').val(actualAmt+(".")+decimalAmt);
		}else{
			if(decimalAmt.length <= 2){
			$('#advanceAmount').val(actualAmt+(".")+decimalAmt);
			} 
		  }	
	   }
    }
}
	function proceed () {
		window.location.href='AdvanceEntry.html';
	}
