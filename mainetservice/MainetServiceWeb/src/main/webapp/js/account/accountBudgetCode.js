
$("#tbAcBudgetCode").validate({
	
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
				url : "AccountBudgetCode.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['', getLocalMessage('account.budget.code.functioncode'),getLocalMessage('account.budget.code.primaryaccountcode'),getLocalMessage('account.budget.code.master.status'),getLocalMessage('bill.action')],//"Edit","View"
				colModel : [
				             {name : "prBudgetCodeid",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
				             /*{name : "prBudgetCode",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},*/
				             /*{name : "fundCode",width : 35,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "fieldCode",width : 35,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},*/
				             {name : "functionCode",width : 45,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "accountHeads",width : 70,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "cpdIdStatusFlagDup",width : 20,sortable : true,align : 'center',editable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name: 'prBudgetCodeid', index: 'prBudgetCodeid', width:30 , align: 'center !important',formatter:addLink,search :false}
				             //{name : 'prBudgetCodeid',index : 'prBudgetCodeid',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				             //{name : 'prBudgetCodeid',index : 'prBudgetCodeid',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "prBudgetCodeid",
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
				caption : getLocalMessage('account.budget.code.accountbudgetcodelist'),
				
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
   return "<a class='btn btn-blue-3 btn-sm viewBugopnBalMasterClass' title='View'value='"+rowdata.prBudgetCodeid+"' prBudgetCodeid='"+rowdata.prBudgetCodeid+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editBugopnBalMasterClass' title='Edit'value='"+rowdata.prBudgetCodeid+"' prBudgetCodeid='"+rowdata.prBudgetCodeid+"' ><i class='fa fa-pencil'></i></a> ";
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
	prBudgetCodeid = rowdata.prBudgetCodeid;
	return "<a href='#'  return false; class='editBugopnBalMasterClass' value='"+prBudgetCodeid+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}
*/
/*function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewBugopnBalMasterClass' value='"+prBudgetCodeid+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}*/
function setBudgetCode(element,id) {
        var errorList = [];
        $('.error-div').hide();
	    var functionId = $(element).val();
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		
		var postData = 'functionId=' + functionId;
		var ajaxResponse = __doAjaxRequest(
				'AccountBudgetCode.html?getBudgetHead', 'POST',postData, false,"json");
		
		if (jQuery.isEmptyObject(ajaxResponse)) {
			var selectOption = getLocalMessage('account.common.select');
			$("#pacHeadId" + id).empty().append(
					'<option selected="selected" value="0">' + selectOption
							+ '</option>');
			$("#pacHeadId" + id).trigger('chosen:updated');
			errorList.push('No Budget head are available against selected function Id');
			
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
			//displayErrorsOnPage(errorList);
			//$('#errorDivIdI').remove();
		} else {
			var selectOption = getLocalMessage('account.common.select');
			$("#pacHeadId" + id).empty().append(
					'<option selected="selected" value="0">' + selectOption
							+ '</option>');
			$.each(ajaxResponse, function(key, value) {
				$("#pacHeadId" + id).append(
						$("<option></option>").attr("value", key).text(value));
			});
			$("#pacHeadId" + id).trigger('chosen:updated');
		}
}



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
		var prBudgetCodeid = 1;
		var url = "AccountBudgetCode.html?form";
		var requestData = "prBudgetCodeid=" + prBudgetCodeid  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		
		return false;		
	});			


	/*$(document).on('click', '.editBugopnBalMasterClass', function() {
		var $link = $(this);
		var prBudgetCodeid = $link.closest('tr').find('td:eq(0)').text(); 
		var prBudgetCodeid = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountBudgetCode.html?update";
		var requestData = "prBudgetCodeid=" + prBudgetCodeid + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		var obj=$(returnData).find('#cpdIdStatusFlagDup'); 
		//alert(obj.val());
	    if(obj.val()=='I')
		    	{
	        	$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type=checkbox]').attr('disabled', true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('input[type=button]').attr('disabled', true);
				$('select').prop('disabled', true).trigger("chosen:updated");
		    	}
		return false;
	});*/
	
	$(document).on('click', '.editBugopnBalMasterClass', function() {
		var errorList = [];
		var $link = $(this);
		/* var paAdjid = $link.closest('tr').find('td:eq(0)').text(); */
		var prBudgetCodeid = $link.closest('tr').find('td:eq(0)').text(); 
		var authStatus = $link.closest('tr').find('td:eq(3)').text();
		var url = "AccountBudgetCode.html?update";
		var requestData = "prBudgetCodeid=" + prBudgetCodeid + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Inactive"){
			$('.content').html(returnData);
		}
		else{
			errorList.push("It is already Inactive so edit is not allowed!");
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
				var prBudgetCodeid = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountBudgetCode.html?formForView";
				var requestData = "prBudgetCodeid=" + prBudgetCodeid + "&MODE_DATA=" + "VIEW";
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
	
	incrementvalue=++count;
	
	 var Revid =  $("#indexdata").val();
	 
	 var functionId= $('#functionId'+Revid).val();
	 var pacHeadId= $('#pacHeadId'+Revid).val();
	 
	if(functionId=='' ||functionId==undefined||functionId=='0'){
	//	errorList.push("Plese select function");
		errorList.push(getLocalMessage("account.bill.entry.exp.functionId "));
	}
	if(pacHeadId=='' ||pacHeadId==undefined||pacHeadId=='0'){
		//errorList.push("Plese select primary head");
		errorList.push(getLocalMessage("account.bdgtHeadBtn.saveSlctPriHead "));
	}
	 if((functionId != null) && (pacHeadId != "")){
			var dec;
		 for(m=0; m<=Revid;m++){
			 for(dec=0; dec<=Revid;dec++){
				 if(m!=dec){
				if(($('#functionId'+m).val() == $('#functionId'+dec).val()) && ($('#pacHeadId'+m).val() == $('#pacHeadId'+dec).val())){
					errorList.push("The Combination AccountHead already exists!");
				}
			  }
			} 
		 }
   	   }		

	 //return saveOrUpdateForm(obj, 'Saved Successfully', 'AccountBudgetCode.html','create');
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
	 showConfirmBoxSave();
	    }
	 //$(".fancybox-close").hide();
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

function showConfirmBoxSave(){
	
	
	var saveorAproveMsg=getLocalMessage("account.btn.save.msg");
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\">'+saveorAproveMsg+'</h4>';
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
	
	var formMode_Id = $("#formMode_Id").val();	
	if(formMode_Id=="create"){
		var successMsg = getLocalMessage("account.bank.record.rubmitted.successfully");
	}else if(formMode_Id=="EDIT"){
		var successMsg = getLocalMessage("account.update.succ");
	}
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage("account.proceed.btn");
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}
function proceed () {
	window.location.href='AccountBudgetCode.html';
}

function setHiddenData(){
	 $('#secondaryId').val($('#sacHeadId0').val());
}

function closeErrBox() {
	$('.error-div').hide();
}

function searchBudgetCodeData(){

	 	/*alert("In search");*/
		
		$('.error-div').hide();

		var errorList = [];

		var dpDeptid = $("#dpDeptid").val();
		var fundId = $("#fundId").val();
		var fieldId = $("#fieldId").val();
		var functionId = $("#functionId").val();
		var sacHeadId = $("#sacHeadId").val();
		var cpdIdStatusFlag = $("#cpdIdStatusFlag").val();
		var objectHeadType = $("#objectHeadType").val();
		
		if(functionId===undefined)
		{
			 functionId=""; 
		}
		if(dpDeptid === undefined){
			dpDeptid = "";
		}
		if(fundId === undefined){
			fundId = "";
		}
		if(fieldId === undefined){
			fieldId = "";
		}
		
		if ((dpDeptid == "" || dpDeptid =="0" || dpDeptid == undefined) && (fundId == "" || fundId =="0" || fundId == undefined) && (fieldId == "" || fieldId =="0" || fieldId == undefined) && (functionId == "" || functionId =="0" || functionId == undefined) && (sacHeadId == "" || sacHeadId =="0") && (cpdIdStatusFlag == "" || cpdIdStatusFlag =="0")) {
		//	errorList.push('Kindly select at least one search criteria');
			errorList.push(getLocalMessage("account.deposit.search.validation "));
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
		
		var url ="AccountBudgetCode.html?getjqGridsearch";
	
		
		
		var requestData = {
				"dpDeptid" : dpDeptid,
				"fundId" : fundId,
				"fieldId" : fieldId,
				"functionId" : functionId,
				"sacHeadId" : sacHeadId,
				"cpdIdStatusFlag" : cpdIdStatusFlag,
				"objectHeadType" : objectHeadType,
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
		//showPopUpMsg(errMsgDiv);
		showModalBoxWithoutCloseaccount(errMsgDiv);
	}

	function redirectToDishonorHomePage () {
		//$.fancybox.close();
		window.location.href='AccountBudgetCode.html';
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
				 errorList.push(getLocalMessage("account.bill.entry.exp.functionId"));
			 
			 var sacHeadId = $.trim($("#pacHeadId"+i).val());
			 if(sacHeadId==0 || sacHeadId=="") 
				 errorList.push(getLocalMessage("please.select.Account.heads"));
			
			 var prBudgetCode = $.trim($("#prBudgetCode"+i).val());
			 if(prBudgetCode==0 || prBudgetCode=="") 
			 //errorList.push("Please Enter Budget Head Description");
			 
			 var cpdIdStatusFlag = $.trim($("#cpdIdStatusFlag"+i).val());
			 if(cpdIdStatusFlag==0 || cpdIdStatusFlag=="")
			 //errorList.push("Please Select Status Flag");
			
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
				//alert(content);
				// reset values
				//content.find("select").attr("value", "");
				content.find("input:text").val("");
				content.find("textarea").val("");
				//content.find("select").val("");
				content.find("input:checkbox").attr('checked',false);
				content.find('div.chosen-container').remove();
				//
				content.find("select").chosen().trigger("chosen:updated");
				content.find('.has-error').removeClass('has-error');
				content.find('label').closest('.error').remove(); //for removal duplicate
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
			$(this).find($('[id^="functionId"]')).attr('id',"functionId"+i+"_chosen");
			$(this).find($('[id^="pacHeadId"]')).attr('id',"pacHeadId"+i+"_chosen");

			//$(this).find("select:eq(0)").attr("id", "pacHeadId" + i);
			$(this).find("select:eq(0)").attr("id", "functionId" + i);
			$(this).find("input:text:eq(0)").attr("id", "functionId" + i);
			//$(this).find('div.chosen-container').attr('id',"functionId"+i+"_chosen");
			$(this).find("select:eq(1)").attr("id", "pacHeadId" + i);
			$(this).find("input:text:eq(1)").attr("id", "pacHeadId" + i);
			//$(this).find('div.chosen-container').attr('id',"pacHeadId"+i+"_chosen");
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
			$(this).find('#functionId'+i).attr("onchange", "setBudgetCode(this," + (i) + ")");
			$(this).find('#pacHeadId'+i).attr("onchange", "findduplicatecombinationexit(" + (i) + ")");
			
			//$(this).find('div.chosen-container').attr('id',"functionId"+i+"_chosen");
			
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
			
			/*if ($('#cpdBugsubtypeId').val() == "") {
				msgBox('Please Select Budget Sub Type');
				var sacHeadId = $('#pacHeadId'+cnt).val("");
				$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
				return false;
			}*/
			
			if ($('#dpDeptid').val() == "") {
				msgBox('Please Select Department');
				var sacHeadId = $('#pacHeadId'+cnt).val("");
				$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
				return false;
			}
			
			var url = "AccountBudgetCode.html?getBudgetRevDuplicateGridloadData&cnt="+cnt;

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			 if(returnData){
				 
				 errorList.push(getLocalMessage("account.budget.code.already.exist.against.code"));
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
			var url = "AccountBudgetCode.html?getBudgetHeadDescDetails&cnt="+cnt;
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
			//errorList.push("Please select Fund");	
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}
		/*if(fieldId == '') {
			//errorList.push("Please select Field");
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}*/
		
		if (dpDeptid == '') {
			//errorList.push('Please Select Department');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}
		
		/*if (cpdBugtypeId == '') {
			//errorList.push('Please Select Budget Type');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}

		if (cpdBugsubtypeId == '') {
			//errorList.push('Please Select Budget Sub Type');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}*/

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

				var url = "AccountBudgetCode.html?getjqGridload";

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

				var url = "AccountBudgetCode.html?getjqGridload";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
				//$('#faYearid').prop('disabled', 'disabled');

			}
		};
		function exportTemplate(){
			
			var $link = $(this);
			/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
			//var functionId = 1;
			var url = "AccountBudgetCode.html?importExportExcelTemplateData";
			var requestData = "";
			var returnData = __doAjaxRequest(url, 'post', requestData, false);

			$('.content').html(returnData);

			prepareDateTag();
			return false;
		}
		function closeOutErrorBox(){
			$('#errorDivSec').hide();
		}
		
		