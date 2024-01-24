
$(document).ready(function(){
	//
		if($('#mode').val() == 'create'){
			$('#bank').focus();
			$('#bankStatus').val('A');
			$('#bankStatus').attr("disabled", true);
		}		
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});

/*$("#tbBankMaster").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});*/

var dsgid = '';
var dsgid = '';
$(function() {
	
	$("#grid").jqGrid(
			{
				url : "GeneralBankMaster.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['', getLocalMessage('bank.master.header'),getLocalMessage('bank.master.branch'),getLocalMessage('accounts.vendormaster.ifsccode'),getLocalMessage('account.bankmaster.micrcode'),getLocalMessage('account.bankmaster.city'),getLocalMessage('account.bankmaster.status'),getLocalMessage('advance.management.Action')],
				colModel : [
				             {name : "bankId",width : 20,sortable : true,search:false, hidden:true},
				             {name : "bank",width : 30,sortable : true,search:true}, 
				             {name : "branch",width : 30,sortable : true,search:true},
				             {name : "ifsc",width : 20,sortable : true,search:true }, 
				             {name : "micr",width : 20,sortable : true, search:true},
				             {name : "city",width : 20,sortable : true, search:true},
				             {name : "bankStatus",width : 15,sortable : false, search:false,align: 'center !important',formatter:statusFormatter},
				             {name: '', index: 'bankId', width:20 , align: 'center !important',formatter:addLink,search :false,sortable : false}
				       
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "bankId",
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
				caption : getLocalMessage('bank.account.bankList'),
				
				formatter: function (v) {
				    // uses "c" for currency formatter and "n" for numbers
				    return Globalize.format(Number(v), "c");
				},
				unformat: function (v) {
				    return Globalize.parseFloat(v);
				}
				
			});
	jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : true});
	$("#pagered_left").css("width", "");
});

function addLink(cellvalue, options, rowdata) 
{
	if($('#isDefault').val() == 'Y'){
		return "<a class='btn btn-blue-3 btn-sm viewBugopnBalMasterClass' title='View'value='"+rowdata.bankId+"' bankId='"+rowdata.bankId+"' ><i class='fa fa-eye'></i></a> " +
  		 "<a class='btn btn-warning btn-sm editBugopnBalMasterClass' title='Edit'value='"+rowdata.bankId+"' bankId='"+rowdata.bankId+"' ><i class='fa fa-pencil'></i></a> ";
	}else{
		return "<a class='btn btn-blue-3 btn-sm viewBugopnBalMasterClass' title='View'value='"+rowdata.bankId+"' bankId='"+rowdata.bankId+"' ><i class='fa fa-eye'></i></a> ";
	}
   
}

function statusFormatter(cellvalue, options, rowdata){
	if (rowdata.bankStatus == 'A') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowdata.bankStatus+"'  alt='Bank is Active' title='Bank is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowdata.bankStatus+"' alt='Bank is  InActive' title='Bank is InActive'></a>";
	}
}

function returnisdeletedUrl(cellValue, options, rowdata, action) {
	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"
				+ rowdata.isdeleted
				+ "'  alt='Bank is Active' title='Bank is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"
				+ rowdata.isdeleted
				+ "' alt='Bank is  Inactive' title='Bank is InActive'></a>";
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
		var bankId = 1;
		var url = "GeneralBankMaster.html?form";
		var requestData = "bankId=" + bankId  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		
		return false;		
	});			
	
	$(document).on('click', '.editBugopnBalMasterClass', function() {
		var errorList = [];
		var $link = $(this);
		var bankId = $link.closest('tr').find('td:eq(0)').text(); 
		var authStatus = $link.closest('tr').find('td:eq(5)').text();
		var url = "GeneralBankMaster.html?update";
		var requestData = "bankId=" + bankId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Inactive"){
			$('.content').html(returnData);
		}
		else{
			errorList.push(getLocalMessage("bank.error.valid.editnotallowed"));
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
				var bankId = $link.closest('tr').find('td:eq(0)').text();
				var url = "GeneralBankMaster.html?formForView";
				var requestData = "bankId=" + bankId + "&MODE_DATA=" + "VIEW";
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
		errorList.push(getLocalMessage("bank.error.valid.notEditBudHead"));
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

function saveLeveledData(obj){

	var errorList = [];
	//#134943 duplicate IFSC, MICR code and Branch not allowed 
	if($('#mode').val() == 'create'){
	findduplicatecombinationifscexit(obj);	
	checkDuplicateMICR();
	}
	errorList = validateBankData();
	
	if(errorList.length > 0){
		showBankError(errorList);
	}else{
		var	formName =	findClosestElementId(obj, 'form');
	    var theForm	=	'#'+formName;
	    var requestData = __serializeForm(theForm);
	    var url	=	$(theForm).attr('action');
	   
	   var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
	    if(response != false){
	       $('.content').html(response);
	    }
	}      
}

function closeErrBox() {
	$('.error-div').hide();
}

function searchBankMasterData(){

		$('.error-div').hide();

		var errorList = [];

		var bank = $("#bank").val();
		var branch = $("#branch").val();
		var ifsc = $("#ifsc").val();
		var micr = $("#micr").val();
		var city = $("#city").val();

		if ((bank == "" || bank =="0") && (branch == "" || branch =="0") && (ifsc == "" || ifsc =="0") && (micr == "" || micr =="0") && (city == "" || city =="0")) {
			errorList.push(getLocalMessage("bank.error.valid.serchCriteria"));
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
		
		var url ="GeneralBankMaster.html?getjqGridsearch";
		var requestData = {
				"bank" : bank,
				"branch" : branch,
				"ifsc" : ifsc,
				"micr" : micr,
				"city" : city,
			};
		
		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
		
		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} else {
			var errorList = [];
			
			errorList.push(getLocalMessage("bank.error.valid.noRecord"));
			
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
		
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+successMsg+'</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="redirectToDishonorHomePage()"/>'+
		'</div>';
	
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showPopUpMsg(errMsgDiv);
	}

	function redirectToDishonorHomePage () {
		$.fancybox.close();
		window.location.href='GeneralBankMaster.html';
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
	
	function findduplicatecombinationifscexit(obj) {

		$('#errorDivBank').hide();
		var errorList = [];
		
		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		
			 if(errorList.length>0){
			    	
			    	var errorMsg = '<ul>';
			    	$.each(errorList, function(index){
			    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			    		
			    	});
			    	errorMsg +='</ul>';
			    	
			    	$('#errorDivBank').html(errorMsg);
			    	$('#errorDivBank').show();
					$('html,body').animate({ scrollTop: 0 }, 'slow');
			    		    	
			    }
			 
		if (errorList.length == 0) {
			
			var url = "GeneralBankMaster.html?getDuplicateIFSCCodeExit";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			 if(returnData){
				 
				errorList.push(getLocalMessage("bank.error.valid.ifscAlrExists"));
				$('#ifsc').val("");
				showBankError(errorList);
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
				 showBankError(errorList);
			 }else{
				 var url = "GeneralBankMaster.html?getDuplicateBranchNameExit";

					var returnData = __doAjaxRequest(url, 'post', requestData, false);
					
					 if(returnData){ 
						errorList.push(getLocalMessage("bank.error.valid.branchAlrExists")+bank); 
						$('#branch').val("");
						showBankError(errorList);
						return false;
					 }
		}
	};	
	
	function clearBranchName(obj){
		
		$('#branch').val(""); 
	}
	
	function bankreset(){
		window.location.href = 'GeneralBankMaster.html';
	}
	
	function closeOutErrBox(){
		$('#errorDivBank').hide();
	}
	
	function showBankError(errorList){
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorDivBank").html(errMsg);
		$('#errorDivBank').show();
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
	
	
	function validateBankData(){
		var errorList = [];
		var envFlag = $("#envFlag").val();
		var micr = $("#micr").val();
		
		if($.trim($("#bank").val())=='' || $("#bank").val()==null){
			errorList.push(getLocalMessage("bank.error.bankname"));
		}
		if($.trim($("#branch").val())=='' || $("#branch").val()==null){
			errorList.push(getLocalMessage("bank.error.branch"));
		}
		if($.trim($("#ifsc").val())=='' || $("#ifsc").val()==null){
			errorList.push(getLocalMessage("bank.error.ifsc"));
		}else{
			var ifscRegEx = /^[A-Z]{4}\d{1}[A-Z0-9]{6}$/;
			var ifsc = $('#ifsc').val().toUpperCase();
			if(!ifscRegEx.test(ifsc)) {
				errorList.push(getLocalMessage('bank.error.valid.ifsc'));		
			}
		}
		if (envFlag != 'Y'){
		if($.trim(micr)=='' || micr==null){
			errorList.push(getLocalMessage("bank.error.micr"));
		}else if(micr.length < 9){
			errorList.push(getLocalMessage("bank.error.valid.micr"));
		 }
		}
		if($.trim($("#city").val())=='' || $("#city").val()==null){
			errorList.push(getLocalMessage("bank.error.city"));
		}
		if($.trim($("#district").val())=='' || $("#district").val()==null){
			errorList.push(getLocalMessage("bank.error.district"));
		}
		if($.trim($("#state").val())=='' || $("#state").val()==null){
			errorList.push(getLocalMessage("bank.error.state"));
		}
		if($.trim($("#address").val())=='' || $("#address").val()==null){
			errorList.push(getLocalMessage("bank.error.branchaddress"));
		}
		return errorList;
	}
	
	function checkDuplicateMICR(){
		
		$('#errorDivBank').hide();
		var url = "GeneralBankMaster.html?getDuplicateMICR";
		var errorList = [];
		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		 if(returnData){
			 errorList.push(getLocalMessage("bank.error.valid.micrAlrExists"));
			 $('#micr').val("");
			 showBankError(errorList);
		 }
	}
	
	function exportTemplate() {

		
		var $link = $(this);
		/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
		// var functionId = 1;
		var url = "GeneralBankMaster.html?exportTemplateData";
		var requestData = "";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		$('.content').html(returnData);

		prepareDateTag();
		return false;
	}
	function closeOutErrorBox() {
		$('#errorDivSec').hide();
	}
	function closeOutErrBox(){
		$('.error-div').hide();
	}
