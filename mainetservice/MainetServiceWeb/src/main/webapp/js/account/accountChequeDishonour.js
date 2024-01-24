
$("#tbAccountChequeDishonour").validate({
	
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
				url : "AccountChequeDishonour.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['',getLocalMessage('accounts.payslip.no'), getLocalMessage('accounts.payinslip.date'),getLocalMessage('challan.receipt.amount.Instrument'),getLocalMessage('accounts.receipt.cheque_dd_date'),getLocalMessage('accounts.receipt.transaction.amount'),"", getLocalMessage('bank.master.view'),getLocalMessage('accounts.process')],//"Edit","View"
				colModel : [
				             {name : "chequeDishonourId",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
				             /*{name : "prBudgetCode",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},*/
				             {name : "number",width : 50,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "date",width : 50,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "chequeddno",width : 50,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "chequedddate",width : 50,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "amount",width : 40,align : 'right',sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "id",index:'id',width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true},
				             { name: 'chequeDishonourId', index: 'chequeDishonourId', width:20, align: 'center', edittype:'radio',formatter:viewChecklistMst, editoptions: { value: "Yes:No" },
				          			formatoptions: { disabled: false },
				      		 },
				             {name: '', index: 'chequeDishonourId', width:25 , align: 'center !important',formatter:addLink,search :false},
				             //{name : 'chequeDishonourId',index : 'chequeDishonourId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				             //{name : 'chequeDishonourId',index : 'chequeDishonourId',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "chequeDishonourId",
				sortorder : "desc",
				height : 'auto', 
				viewrecords : true,
				gridview : true,
				loadonce : true,
				multiselect : true,
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,
				},
				autoencode : true,
				caption : getLocalMessage("account.cheque.dishonour.list"),
				
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
	var process=getLocalMessage("accounts.process");
   return "<a class='btn btn-blue-2 btn-sm editBugopnBalMasterClass' title='"+process+"' value='"+rowdata.chequeDishonourId+"' chequeDishonourId='"+rowdata.chequeDishonourId+"' ><i class='fa fa-file-text-o'></i></a> ";
   		 /*"<a class='btn btn-warning btn-sm editBugopnBalMasterClass' title='Edit'value='"+rowdata.chequeDishonourId+"' chequeDishonourId='"+rowdata.chequeDishonourId+"' ><i class='fa fa-pencil'></i></a> ";*/
}

function viewChecklistMst(cellValue, options, rowdata, action) {
	var myVar = rowdata.chequeDishonourId;
	var viewcheque= getLocalMessage("account.View.Cheque.Dishonour");
    return "<a href='#'  return false; class='viewClass' value='"+myVar+"'><img src='css/images/view.png' width='20px' alt='View Cheque Dishonour' title='"+viewcheque+"' /></a>";
}

$(function() {		
	$(document).on('click', '.viewClass', function() {
		var gr = jQuery("#grid").jqGrid('getGridParam','selrow');	
		
		var url = "AccountChequeDishonour.html?viewData";
		var string = $(this).attr('value');
		var d = string.split(","); 
		var chequeDishonourId=Number.parseInt(d[0]);
		
		//var docGroup=d[1];
		
		var returnData = {"chequeDishonourId" : chequeDishonourId};
		
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
			data : returnData,
			success : function(response) {
				var divName = '.child-popup-dialog';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				showModalBox(divName);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				
				showError(errorList);
			}
		});			
	});
});

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
	chequeDishonourId = rowdata.chequeDishonourId;
	return "<a href='#'  return false; class='editBugopnBalMasterClass' value='"+chequeDishonourId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}
*/
/*function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewBugopnBalMasterClass' value='"+chequeDishonourId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}*/

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

$(document).ready(function() {
	$('.error-div').hide();
	var Error_Status = '${Errore_Value}';

});

function closeOutErrBox() {
	$('.error-div').hide();
}


$(function() {	
	$(document).on('click', '.createData', function() {
	
		var $link = $(this);
		/*var spId = $link.closest('tr').find('td:eq(0)').text();*/
		var chequeDishonourId = 1;
		var url = "AccountChequeDishonour.html?form";
		var requestData = "chequeDishonourId=" + chequeDishonourId  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		
		return false;		
	});			
	
	$(document).on('click', '.editBugopnBalMasterClass', function() {
		var errorList = [];
		
		var $link = $(this);
		var chequeDishonourId = $link.closest('tr').find('td:eq(1)').text();
		var chequedddate = $link.closest('tr').find('td:eq(3)').text();
		var chequeNo = $link.closest('tr').find('td:eq(4)').text();
		var id = $link.closest('tr').find('td:eq(7)').text();
		var bankAccount = $("#bankAccount").val();
		var url = "AccountChequeDishonour.html?update";
		var requestData = "chequeDishonourId=" + chequeDishonourId +"&chequedddate=" + chequedddate +"&id=" + id +"&bankAccount=" + bankAccount +"&MODE_DATA=" + "EDIT" +"&chequeddno="+chequeNo;
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;
	});
	
	$(document)
	.on(
			'click',
			'.viewBugopnBalMasterClass',
			function() {
				var $link = $(this);
				var chequeDishonourId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountChequeDishonour.html?formForView";
				var requestData = "chequeDishonourId=" + chequeDishonourId + "&MODE_DATA=" + "VIEW";
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
	
	$("#flag").click(function()
			{
		
		 var chk = $('#flag').is(':checked');
		 if(chk)
			 {
			 $("input[id^='flagFlzd']").prop('checked', true);
			 }
		 else
			 {
			 $("input[id^='flagFlzd']").prop('checked', false);
			 }
	});

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
	
	var count = 0;
	
	var from = $("#chequedddate").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#dishonourDate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate < transactionDate){
			errorList.push("Dishonour date should not be less than cheque deposit date");
		}
	}
	if(to!=null)
		{
		errorList = validatedate(errorList,'dishonourDate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#dishonourDate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Dishonour date can not be less than SLI date");
			  }
			}
		  }
		}
	
/*	$('#grid').each(function() {
		
		var type = $('.cbox').is(':checked');
		
		 if(type==true){
			 count++;
		}
		//$("#debitCredit_"+i+" option:selected").attr("code");
		
	});

	if(count == '0' ){
		errorList.push("Please select At least one Flag Type");
	}
	
		var s;
		s = jQuery("#grid").jqGrid('getGridParam','selarrrow');
		
		$('#dishonourIds').val(s);*/
		
		/*var gr = jQuery("#grid").jqGrid('getGridParam','selrow');	
		
		var string = $(this).attr('value');
		var d = string.split(","); 
		var chequeDishonourId=Number.parseInt(d[6]);
		alert(chequeDishonourId);*/
		
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
	 //return saveOrUpdateForm(obj, 'Saved Successfully', 'AccountChequeDishonour.html','create');
		showConfirmBoxSave();
		/*var	formName =	findClosestElementId(obj, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');
    
   // var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
    var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
    if(response != false){
       $('.content').html(response);
       }*/
	}     
}

function showConfirmBoxSave(){
	
	
	var saveorAproveMsg="save"; 
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+'</h4>';
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
	var	formName =	findClosestElementId(objTemp, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');
    
   // var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
    var response= __doAjaxRequestValidationAccor(objTemp,url+'?create', 'post', requestData, false, 'html');
    if(response != false){
       $('.content').html(response);
       }	
	
}


function closeErrBox() {
	$('.error-div').hide();
}

function searchAccountChequeDishonourData(){

	 	/*alert("In search");*/
		
		$('.error-div').hide();

		var errorList = [];

		var serchType = $('input[name=serchType]:checked').val();
		
		if(serchType == "" || serchType =="0" || serchType == undefined){
			errorList.push('Kindly select Search Type');
		}else{
		var number = $("#number").val();
		var date = $("#date").val();
		var amount = $("#amount").val();
		var bankAccount = $("#bankAccount").val();	

		if (number == "" || number =="0") {
			errorList.push(getLocalMessage('account.enter.number'));
		}
		if (bankAccount == "" || bankAccount =="0") {
			errorList.push(getLocalMessage('account.select.bank'));
		}
		if(date!=null)
			{
			errorList = validatedate(errorList,'date');
			}
		
		/*if ((number == "" || number =="0") && (date == "" || date =="0") && (amount == "" || amount =="0") && (bankAccount == "" || bankAccount =="0")) {
			errorList.push('Kindly select at least one search criteria');
			}*/
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
		
		var url ="AccountChequeDishonour.html?getjqGridPayInSlipChequeDDNoSearch";
		var requestData = {
				"number" : number,
				"date" : date,
				"amount" : amount,
				"bankAccount" : bankAccount,
				"serchType" : serchType,
			};
		
		 /*var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	
		 if (ajaxResponse == 'ERROR') {
			 renderErrorPage();
			 var errorList = [];
			 errorList.push(ajaxResponse);
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
		 } else {
			 if ( ajaxResponse != '') {
				
				 var errorList = [];
				 errorList.push(ajaxResponse);
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
			 } else {
				// $("#voucherTemplatesGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
				 console.log('reloadGrid');
			 }
			
		 }*/
		 
		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
		
		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
			$("#viewDishonourDetails").show();
		} else {
			var errorList = [];
			
			errorList.push(getLocalMessage("account.norecord.criteria"));
			$("#viewDishonourDetails").hide();
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
		var cls =  getLocalMessage('account.billEntry.savedBtnProceed');
		
		message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
		 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
		 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutCloseaccount(errMsgDiv);
	}

	function redirectToDishonorHomePage () {
		$.fancybox.close();
		window.location.href='AccountChequeDishonour.html';
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

	$(function() {
	    $('#divId input').keyup(function() {
	        this.value = this.value.toUpperCase();
	    });
	});
	
	function findduplicatecombinationifscexit(obj) {

		$('.error-div').hide();
		var errorList = [];

		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		
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
			
			var url = "AccountChequeDishonour.html?getDuplicateIFSCCodeExit";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			 if(returnData){
				 
				errorList.push("IFSC Code is Already Exists, against this Bank Master!"); 
				$('#ifsc').val("");
				
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
	};	
	
	function findduplicatecombinationBranchexit(obj) {

		$('.error-div').hide();
		var errorList = [];

		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		
		var bank = $('#bank').val();
		
		if (bank == '') {
			errorList.push('Please Enter Bank Name');
			$('#branch').val(""); 
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
			
			var url = "AccountChequeDishonour.html?getDuplicateBranchNameExit";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			 if(returnData){
				 
				errorList.push("Branch Name is Already Exists, against this Bank Name!"); 
				$('#branch').val(""); 
				
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
	};	
	
	function clearBranchName(obj){
		
		$('#branch').val(""); 
	}
	
	$(function() {
	    $('#divId textarea').keyup(function() {
	        this.value = this.value.toUpperCase();
	    });
	});
	
	/*function getBankAccountData(obj) {

		$('.error-div').hide();
		var errorList = [];
		
		$('#bankAccount').val("");
		$("#bankAccount").val('').trigger('chosen:updated');
		
		$('#bankAccountCheque').val("");
		$("#bankAccountCheque").val('').trigger('chosen:updated');
		
		var cpdVendortype = $('#cpdVendortype').val();

		if (cpdVendortype == '' || cpdVendortype =="0") {
			errorList.push('Please Select Vendor Type');
			$('#cpdVendorSubType').val("");
			$("#cpdVendorSubType").val('').trigger('chosen:updated');
		}
		
		if (errorList.length > 0) {
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			return false;
		} 
		
		if (errorList.length == 0) {
			
			var divName = ".content";
			
			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);
			
			var url = "AccountChequeDishonour.html?getBankAccountData";

			var response = __doAjaxRequest(url, 'post', requestData, false);
			alert(response);
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
				
				 var type = $('input[name=serchType]:checked').val();
				 
		    	 var postdata = 'type=' + type;
		    	 var json = __doAjaxRequest('AccountChequeDishonour.html?getBankAccountData',
		    				'POST', postdata, false, 'json');
		    		var  optionsAsString='';
		    		
		    		$.each( json, function( key, value ) {
		    			
		       			 optionsAsString += "<option value='" +key+"'>" + value + "</option>";
		       	   
		    			});
		    		$('#bankAccountCheque').append(optionsAsString );
		    		$('#bankAccountCheque').trigger('chosen:updated');
		    		
		    		if( type =='D'){
		    			 
		    			 $("#payInSlip").hide();
		    			 $("#chequeDDSlip").show();
		    			 
		    		 }else{
		    			 $("#payInSlip").show();
		    			 $("#chequeDDSlip").hide();
		    		 }
		}
	};
	*/
	
	function renderRedirectPage(requestData) {
		
		var url = "AccountChequeDishonour.html?form"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
	}