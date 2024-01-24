
$("#secondaryheadMaster").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

$(function() {
	$("#tbAcSecondaryheadMasterGrid").jqGrid(
			{
				url : "tbAcSecondaryheadMaster.html?getGridData",datatype : "json",mtype : "POST",colNames : [ '',getLocalMessage('account.common.function'),getLocalMessage('budget.allocation.master.primaryaccountcode'),getLocalMessage('budget.allocation.master.secondaryaccountcode'),getLocalMessage('budget.additionalsupplemental.authorization.status'),"","", getLocalMessage('bill.action')],
				colModel : [ {name : "sacHeadId",sortable :  false,searchoptions: { "sopt": [ "eq"] },hidden:true  },
				             /*{name : "fundCode",width : 20,sortable :  true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']} },
				             {name : "fieldCode",width : 20,sortable :  true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}  },*/
				             {name : "functionCode",sortable :  true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']} },
				             {name : "pacHeadDesc",sortable :  true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}  },
				            /* {name : "sacHeadCode",width : 10,sortable :  true,searchoptions: { "sopt": [ "eq"] }  },*/
				             {name : "sacHeadCodeDesc",sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}},
				             {name : "cpdIdStatusFlag",sortable :  true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']} },
				             {name : "defaultOrgFlag",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name : "sacLeddgerTypeCpdCode",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name: 'sacHeadId', index: 'sacHeadId',  align: 'center !important',formatter:addLink,search :false}
				             
				             /* {name : "lmoddate",width : 30,sortable : true, searchoptions: { "sopt": ["bw", "eq"] },formatter : dateTemplate},*/
				             //{name : "sacHeadId",index : 'functionId',width : 5,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				             //{name : "sacHeadId",index : 'functionId',width : 5,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
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
				caption : getLocalMessage('account.configuration.secondary.account.head.list')
			});
	 jQuery("#tbAcSecondaryheadMasterGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", ""); 
	 
});

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.sacHeadId+"' sacHeadId='"+rowdata.sacHeadId+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editClass' title='Edit'value='"+rowdata.sacHeadId+"' sacHeadId='"+rowdata.sacHeadId+"' ><i class='fa fa-pencil'></i></a> ";
}

/*function returnEditUrl(cellValue, options, rowdata, action) {
	alert(rowdata.sacHeadId+"hi");
	sacHeadId=  rowdata.sacHeadId
    return "<a href='#'  return false; class='editClass' value='"+rowdata.sacHeadId+"' ><img src='css/images/edit.png' width='20px' alt='Edit Charge Master' title='Edit Scrutiny Data' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewClass' value='"+rowdata.sacHeadId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}*/



$(function() {		
	$(document).on('click', '.addSecondaryheadMasterClass', function() {
		var $link = $(this);
		/*var spId = $link.closest('tr').find('td:eq(0)').text();*/
		var functionId = 1;
		var url = "tbAcSecondaryheadMaster.html?add";
		var requestData = "";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		prepareDateTag();
		return false;	
	});
	
});

$( document ).ready(function() {
	$('.error-div').hide();
	$('.hasNumberMaxEight').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','8');
	});
	
	$('.hasMyNumberMaxTwo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	
	if ($('#test').val() === 'create' || ($('#test').val() === 'update')) {
		
	$("#LedgerType option[code="+"BK"+"]").remove();
	$("#LedgerType option[code="+"VD"+"]").remove();

	}
	
	if (($('#test').val() === 'update') || ($('#test').val() === 'view') ) {
		
		var  sacLeddgerTypeCpdCode = $("#sacLeddgerTypeCpdCode").val();
		
		$("#BankAccountId").hide();
		$("#VendorListId").hide();

		if(sacLeddgerTypeCpdCode == "BK") {
			
			$("#BankAccountId").show();
		}
		if(sacLeddgerTypeCpdCode == "VD") {
			
			$("#VendorListId").show();
		}
		
	}
	
	
	$(function() {		
		$(document).on('blur', '.baAccountid', function() {
			
			/*alert($(".bmBankid").find(':selected').attr('code')+"bmBankid");
			alert($(".baAccountid").find(':selected').attr('code')+"baAccountid");*/
			$(".sacHeadDesc").val( $(bmBankid).find(':selected').attr('code')+"-"+$(".baAccountid").find(':selected').attr('code') );
		
			
						
		});
			
	}); 
	$(function() {		
		$(document).on('click', '.baAccountid', function() {
			
			
			
			$(".sacHeadDesc").val( $(bmBankid).find(':selected').attr('code')+"-"+$(".baAccountid").find(':selected').attr('code') );
			
			
			
			
			
		});
			
	}); 
	$(function() {		
		$(document).on('click', '.vmVendorid', function() {
				
			$(".sacHeadDesc").val( $(vmVendorid).find(':selected').attr('code') );
			
		});
			
	}); 

});
$(function() {		
	$(document).on('click', '.editClass', function() {
		var errorList = [];
		var $link = $(this);
		var sacHeadId = $link.closest('tr').find('td:eq(0)').text();
		var authStatus = $link.closest('tr').find('td:eq(4)').text();
		var defaultOrgFlag = $link.closest('tr').find('td:eq(5)').text();
		var sacLeddgerTypeCpdCode = $link.closest('tr').find('td:eq(6)').text();
		var url = "tbAcSecondaryheadMaster.html?editGr";
		var requestData = "sacHeadId=" + sacHeadId + "&MODE=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		var response = false;//checkTransaction(sacHeadId);
			
		if(defaultOrgFlag=="Y" && authStatus!="Deactive" && sacLeddgerTypeCpdCode!="VD" && sacLeddgerTypeCpdCode!="BK" && response==false){
			$('.content').html(returnData);
		}
		else{
			if(defaultOrgFlag!="Y"){
				errorList.push("You are not allowed to edit this selection because it is protected by Default Orgonization.");
			}
			else if(authStatus=="Deactive"){
				errorList.push("It is already Deactive so EDIT is not allowed!");
			}
			else if(sacLeddgerTypeCpdCode=="VD"){
				errorList.push("It is vendor LeadgerType so EDIT is not allowed!");
			}
			else if(sacLeddgerTypeCpdCode=="BK"){
				errorList.push("It is bank LeadgerType so EDIT is not allowed!");
			}
			else if(response==true){
				errorList.push("Transactions are available so EDIT is not allow!");
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
				return false;
		    }
		}
	});
		
});

$(function() {		
	$(document).on('click', '.viewClass', function() {
		var $link = $(this);
		var sacHeadId = $link.closest('tr').find('td:eq(0)').text();
		var url = "tbAcSecondaryheadMaster.html?viewGr";
		var requestData = "sacHeadId=" + sacHeadId + "&MODE=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
		prepareDateTag();
			
	});
		
});

function checkTransaction(sacHeadId){
	
	var url = "AccountVoucherEntry.html?checkTransactions";
	var requestData = "sacHeadId=" + sacHeadId ;
	response =__doAjaxRequest(url,'post',requestData,false,'');
	//alert(returnData);
	return response;
}

$(function() {		
	$(document).on('click', '.hello', function() {
		$(".hello ").hide();
	});
		
});
$(function() {		
	$(document).on('click', '.btn btn-success', function() {
		$(".sacHeadCode ").val(null);
		$(".sacHeadDesc ").val(null);
	});
		
});


/*$(function() {		
	$(document).on('click', '.listOfTbAcFunctionMasterItems', function() {
		
			alert($(".listOfTbAcFunctionMasterItems").val()+"hi");
		alert($(listOfTbAcFunctionMasterItems).find(':selected').attr('code'));
		alert("aaa");
		console.log($(this).val() +" "+$(listOfTbAcFunctionMasterItems).find(':selected').attr('code'));
		$(".primeryObejctdiscription").val( $(listOfTbAcFunctionMasterItems).find(':selected').attr('code'));.chosen().trigger("chosen:updated");
		
	});
		
});*/


 $('#listOfTbAcFunctionMasterItems').chosen().change(function() {
     $("#primeryObejctdiscription").val($(listOfTbAcFunctionMasterItems).find(':selected').attr('code'));
 });
 
 $('#listOfTbAcPrimaryMasterItems').chosen().change(function() {
     $("#primeryObejctdiscription").val($(listOfTbAcPrimaryMasterItems).find(':selected').attr('code'));
 });
 
$(function() {		
	$(document).on('change', '.LedgerType1', function() {
		/*alert($(LedgerType).find(':selected').attr('code')+"heloo");
		alert($("#LedgerType").val()+"hii");*/
		
			/*alert($(".listOfTbAcFunctionMasterItems").val()+"hi");*/
		/*alert($(listOfTbAcFunctionMasterItems).find(':selected').attr('code'));*/
		if($(LedgerType).find(':selected').attr('code') == 'VD')
			{
			
			$(".BankName").hide();
			$(".Bank").hide();
			$(".Other").hide();
			$(".Vendor").show();
			$(".VendorList").show();
			
			}
		
		if($(LedgerType).find(':selected').attr('code') == '0')
		{
		/* alert($("#bmBankid").val()+"hii");
		 alert($("#baAccountid").val()+"hii2");*/
		 $(".BankName").hide();
		 $(".show").show();
		 $(".Bank").hide();
		$(".Other").hide();
		$(".Vendor").hide();
		$(".VendorList").hide();
		}
		
		if($(LedgerType).find(':selected').attr('code') == 'BK')
		{
		
		
		$(".BankName").show();
		$(".Bank").show();
		$(".Other").hide();
		$(".Vendor").hide();
		$(".VendorList").hide();
		}
		if($(LedgerType).find(':selected').attr('code') == 'OT')
		{
		
		$(".Other").show();
		$(".BankName").hide();
		$(".Bank").hide();
		$(".Vendor").hide();
		$(".VendorList").hide();
		}
	});
		
});


function closeErrBox() {
	$('.error-div').hide();
}


function removeRowFunction (){
	var rowCount = $('#divId li').length;
	if(rowCount<=1){
		return false;
	}
	
	$('#ulId li').last().remove();
}

function getParentCode(){
	var count = $('#countFinalCode').val();
	if($('#parentFunLevel'+count).val()=='1'||$('#parentFunLevel'+count).val()=='Level 1'){
		
		$('#childParentCode'+count).val($('#parentFunCode'+count).val());
	}
}



function getFincalCode(){
	
	
	var count = $('#countFinalCode').val();
	var childFunCode = $('#childFunCode'+count).val();
	var childParentCode = $('#childParentCode'+count).val();
	if(childFunCode!=''){
		if(childParentCode!=''){
			var childFinalCode = childFunCode+childParentCode;
			$('#childFinalCode'+count).val(childFinalCode)
			count++;
			$('#countFinalCode').val(count);
		}
	}
}

function searchSecondaryHeadData(){

 	/*alert("In search");*/
	
	$('.error-div').hide();
	
	var errorList = [];

	var fundId = $("#fundId").val();
	var fieldId = $("#fieldId").val();
	var pacHeadId = $("#pacHeadId").val();
	var functionId = $("#functionId").val();
	var sacHeadId = $("#sacHeadId").val();
	var ledgerTypeId = $("#ledgerTypeId").val();
	
	if(fundId === undefined){
		fundId = "";
	}
	if(fieldId === undefined){
		fieldId = "";
	}
	if(pacHeadId === undefined){
		pacHeadId = "";
	}
	if(functionId === undefined){
		functionId = "";
	}
	if(sacHeadId === undefined){
		sacHeadId = "";
	}
	
	if ((fundId == "" || fundId =="0") && (fieldId == "" || fieldId =="0" || fieldId == undefined) && (pacHeadId == "" || pacHeadId =="0") && (functionId == "" || functionId =="0" || functionId == undefined) && (sacHeadId == "" || sacHeadId =="0") && (ledgerTypeId == "" || ledgerTypeId =="0")) {
		errorList.push(getLocalMessage("account.deposit.search.validation"));
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
	
	var url ="tbAcSecondaryheadMaster.html?getjqGridsearch";

	var requestData = {
			"fundId" : fundId,
			"fieldId" : fieldId,
			"pacHeadId" : pacHeadId,
			"functionId" : functionId,
			"sacHeadId" : sacHeadId,
			"ledgerTypeId" : ledgerTypeId,
		};
	
	var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	
	if (result != null && result != "") {
		$("#tbAcSecondaryheadMasterGrid").jqGrid('setGridParam', {
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
		$("#tbAcSecondaryheadMasterGrid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	}
  }
};

function saveLeveledData(obj){
    // debugger;
	var errorList = [];
	
	var  fundStatus= $("#fundStatus").val();
	var  fieldStatus= $("#fieldStatus").val();
	
	if(fundStatus == 'Y'){
		var  fundId= $("#fundId").val();
		if(fundId == '') {
			errorList.push("Please select fund");	
		}
	}
	if(fieldStatus == 'Y'){
		var  fieldId= $("#fieldId").val();
		if(fieldId == '') {
			errorList.push("Please select field");	
		}
	}
	
	var inactiveStatus=$("#sacStatusCpdId").find(':selected').attr('code');
	var IncativeReason=$("#IncativeReason").val();
	if(inactiveStatus=="I" && IncativeReason==""){
		errorList.push("Inactive Reason should not be empty");	
	}
	
	
	var	formName =	findClosestElementId(obj, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');
    
	var returnDupData= __doAjaxRequest(url+'?checkDupFunPriDescExist', 'post', requestData, false);
    if(returnDupData){
    	errorList.push("Duplicate record found.");	
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
	    	return false;	    	
	 }
	 
	/*var  pacHeadId= $("#listOfTbAcFunctionMasterItems").val();
	var  LedgerType= $("#LedgerType").val();
	var  sacHeadDesc= $("#sacHeadDesc").val();
	
	if(pacHeadId == '') {
		errorList.push("Please select primary head");	
	}
	
	if(LedgerType == '') {
		errorList.push("Please select ledger type");	
	}

	if(sacHeadDesc == '') {
		errorList.push("Please enter desciption");	
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
	    	return false;	    	
	    }
	    else{*/					

	 //return saveOrUpdateForm(obj, 'Saved Successfully', 'AccountBudgetCode.html','create');
	//var	formName =	findClosestElementId(obj, 'form');
    //var theForm	=	'#'+formName;
    //var requestData = __serializeForm(theForm);
    //var url	=	$(theForm).attr('action');
    
    var returnData= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
    if(returnData != false){
    	   $('.content').html(returnData);
    }
    
    //var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
    //$('.content').html(response);
	  /*  }*/
    

	/*var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = {};
	
	
		
		requestData = __serializeForm(theForm);

	var url	=	$(theForm).attr('action')+'?' + 'create';
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	
	$('.content').html(returnData);
	
	prepareDateTag();*/
	
	/*return saveOrUpdateForm(obj, 'Saved Successfully', 'AccountFunctionMaster.html', 'create');*/
}





function changeParentLevels(obj){
	
	var levelCode  = $('#childFunLevel0').find(":selected").val();
	$('#childParentFunLevel0').val(levelCode-1);
				
}	 
	
var incrementvalue;

function getParentFieldCode(){
	
	$("#parentFieldCode0").keyup(function(event) {
	       var stt=$(this).val();
	           $("#parentFinalCode0").val(stt);
	});
				
}


function addRowFunction (){
	
	
	var count=0;
	var errorList = [];
	
	var levelData = $('#levelData').val();
	levelData++;
	 var sid = 'childFunLevel' + levelData;
	 var selID = '#'+sid;
    if($('#count').val()==""){
    	
    	count=1;
    }else{
    	count=parseInt($('#count').val());
    }
	var curMaxInput =count;
	
	
	   var assign = count;
    var incrementvalue=++count;

    var  childFunLevel= $("#childFunLevel" + assign).val();
    var  parentFunLevel= $("#parentFunLevel" + assign).val();
    var  childFunCode= $("#childFunCode" + assign).val();
    var  childParentCode= $("#childParentCode" + assign).val();
    var  funDesc= $("#funDesc" + assign).val();
    var  childFinalCode= $("#childFinalCode" + assign).val();
    
     if(childFunLevel == '0') {
		errorList.push("Child Function Level must not empty");
	}
	if(parentFunLevel == '0') {
		errorList.push("Please select a parentFunLevel");
	}
	if(childFunCode == '0' || childFunCode=='') {
		errorList.push("Mode must not empty");
	}
	if(childParentCode == '0' || childParentCode=='') {
		errorList.push("Please Select Child Function Code");
	}
	if(funDesc == '0' || funDesc == '') {
		errorList.push("Label must not empty");
	}
	
	if(errorList.length > 0){
		
		 var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';			 
		$("#clientSideErrorDivScrutiny").html(errMsg);					
		$("#clientSideErrorDivScrutiny").removeClass('hide')
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		
		errorList = [];
		return false;
		
	} 
  
  var content = $('#divId li:last').clone();
  $("#divId ul > li").last().after(content);
  
  
  content.find("input:text").val("")
  var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
  count = stringId.charAt(stringId.length-1);
  incrementvalue=++count;
  
   var id="#ulId";
   content.find('select:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childLevel'}).attr({'name': 'listDto['+incrementvalue+'].childLevel' }).attr({'id': 'childFunLevel'+incrementvalue });
   content.find('select:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childParentLevel'}).attr({'name': 'listDto['+incrementvalue+'].childParentLevel' }).attr({'id': 'childParentFunLevel'+incrementvalue });
   content.find('input:text:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childCode' }).attr({'name': 'listDto['+incrementvalue+'].childCode' });
   content.find('input:text:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childDesc'}).attr({'name': 'listDto['+incrementvalue+'].childDesc' });
   content.find('input:text:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childParentCode'}).attr({'name': 'listDto['+incrementvalue+'].childParentCode' });
   content.find('input:text:eq(3)').attr({'path': 'listDto['+incrementvalue+'].childFinalCode'}).attr({'name': 'listDto['+incrementvalue+'].childFinalCode' }).attr({'id': 'childFinalCode'+incrementvalue});   
 
   $('#count').val(count);
   
   
   var levelCodeForChildField;
	$('#childFunLevel'+incrementvalue+'').change(function () {
		levelCodeForChildField  = $('#childFunLevel'+incrementvalue).find(":selected").val();
		$('#childParentFunLevel'+incrementvalue+'').val(levelCodeForChildField-1);
		
		var levelDigitForChild = levelCodeForChildField-1;
		var digitForChildFieldCode=$('#level'+levelDigitForChild+'').val();
		$('#childFunCode'+incrementvalue+'').keyup(function () { 
			this.value = this.value.replace(/[^0-9]/g,'');
			$(this).attr('maxlength',digitForChildFieldCode);
		});
		
		
		var levelCodeForParentField;
		levelCodeForParentField = $('#childParentLevel'+incrementvalue+'').find(":selected").val();
		
		var parentLevelDigit = levelCodeForParentField-1;
		var digitForParetnrFieldCode = $('#level'+parentLevelDigit+'').val();
		$('#childParentCode'+incrementvalue+'').keyup(function () { 
			this.value = this.value.replace(/[^0-9]/g,'');
			$(this).attr('maxlength',digitForParetnrFieldCode);
		});
		
});
}
$("#bmBankid").change(function() {
	var bmBankid = $(this).val();
	var selectobject=document.getElementById('baAccountid');
	$(selectobject).find('option:gt(0)').remove();
	
	if (bmBankid > 0 && bmBankid!='')
	{
		var postdata = 'bmBankid=' + bmBankid;
		
		var json = __doAjaxRequest('tbAcSecondaryheadMaster.html?bankaccountList','POST', postdata, false, 'json');

		var  optionsAsString='';

		for(var i = 0; i < json.length; i++)
		{
		    optionsAsString += "<option value='" + json[i].baAccountid + "' code='"+ json[i].baAccountcode+"-"+ json[i].baAccountname +"'>" + json[i].baAccountcode+"-"+ json[i].baAccountname + "</option>";
		}
		$("#baAccountid").append( optionsAsString );
	
	}
	});			

function copyContent(obj){

var x = $(".listOfTbAcFunctionMasterItems").find(':selected').attr('code');

$("#sacHeadDesc").val(x);

}

function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.proceed.btn');
	
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToDishonorHomePage () {
	//$.fancybox.close();
	window.location.href='tbAcSecondaryheadMaster.html';
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

function exportTemplate(){
	
	var $link = $(this);
	/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
	//var functionId = 1;
	var url = "tbAcSecondaryheadMaster.html?importExportExcelTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}

function exportTemplateLink(){
	
	var $link = $(this);
	/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
	//var functionId = 1;
	var url = "tbAcSecondaryheadMaster.html?importExportExcelTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}

function exportTemplateLinkMap(){
	
	var $link = $(this);
	/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
	//var functionId = 1;
	var url = "tbAcSecondaryheadMaster.html?exportImportExcelMapBank";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}

function resetForm(){
	$('#frmMaster').trigger("reset");
	$('#listOfTbAcPrimaryMasterItems').val('').trigger('chosen:updated');
	$('#listOfTbAcFunctionMasterItems').val('').trigger('chosen:updated');
}

function PrintSecondaryHeads(obj){
	var requestData = {
	}
	//AccountFinancialReport.html===replace with==>tbAcSecondaryheadMaster.html
	var ajaxResponse= __doAjaxRequestValidationAccor(obj,'tbAcSecondaryheadMaster.html?printreport', 'POST', requestData, false, 'html');
	    if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$("#heading_wrapper").hide();
	    		$('#content1').html(ajaxResponse);
	    		
	    	}
	    }		
}
