
$("#tbAcBugopenBalance").validate({
	
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
				url : "AccountBudgetOpenBalanceMaster.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['', getLocalMessage('account.budgetopenmaster.accountheads'),getLocalMessage('account.budgetopenmaster.openingbalance(indianrupee)'),getLocalMessage('accounts.head.category'),getLocalMessage('account.budgetopenmaster.finalized'), getLocalMessage('bill.action')],
				colModel : [
				             {name : "opnId",width : 30,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
				             /*{name : "fundCode",width : 35,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "fieldCode",width : 35,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},*/
				             {name : "accountHeads",width : 70,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "formattedCurrency",width : 45,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }, classes:'text-right', editable: true, sortable: false, formatoptions:{decimalSeparator:".", decimalPlaces: 2, defaultValue: '0.00'} },
				             {name : "cpdIdDrCrDesc",width : 30,align : 'center',sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "flagFlzd",width : 30,align : 'center',sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             { name: 'opnId', index: 'opnId', width:30 , align: 'center !important',formatter:addLink,search :false}
				             //{name : 'opnId',index : 'opnId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false	}, 
				             //{name : 'opnId',index : 'opnId',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "opnId",
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
				caption : getLocalMessage('account.budgetopenmaster.budget.head.opening.balance.master.list'),
				
				formatter: function (v) {
				    // uses "c" for currency formatter and "n" for numbers
				    return Globalize.format(Number(v), "c");
				},
				unformat: function (v) {
				    return Globalize.parseFloat(v);
				}
				
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewBugopnBalMasterClass' title='View'value='"+rowdata.opnId+"' opnId='"+rowdata.opnId+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editBugopnBalMasterClass' title='Edit'value='"+rowdata.opnId+"' opnId='"+rowdata.opnId+"' ><i class='fa fa-pencil'></i></a> ";
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
	opnId = rowdata.opnId;
	return "<a href='#'  return false; class='editBugopnBalMasterClass' value='"+opnId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewBugopnBalMasterClass' value='"+opnId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}*/

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

/*$(document).ready(function() {
	$('.error-div').hide();
	var Error_Status = '${Errore_Value}';

});
*/
function closeOutErrBox() {
	$('.error-div').hide();
}


$(function() {	
	$(document).on('click', '.createData', function() {
		var $link = $(this);
		var errorList = [];
		var  faYearid= $("#faYearid").val();
		/*var spId = $link.closest('tr').find('td:eq(0)').text();*/
		var opnId = 1;
		var url = "AccountBudgetOpenBalanceMaster.html?form";
		var requestData = "opnId=" + opnId  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		if(faYearid==null ||faYearid==""){
			errorList.push("Set Go-live date (DD/MM/YYYY) format in other value of SLI prefix");
		}
         if(errorList.length>0){
        	 displayErrorsPage(errorList);
		}
		return false;	
		
	});			


	/*$(document).on('click', '.editBugopnBalMasterClass', function() {
		var $link = $(this);
		var opnId = $link.closest('tr').find('td:eq(0)').text(); 
		var opnId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountBudgetOpenBalanceMaster.html?update";
		var requestData = "opnId=" + opnId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		var obj=$(returnData).find('#flagFlzdDup'); 
	    if(obj.val()=='Y')
		    	{
	        	$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type=checkbox]').attr('disabled', true);
				$('input[type=button]').attr('disabled', true);
				$('select').prop('disabled', true).trigger("chosen:updated");
		    	}
		return false;
	});*/
	
	$(document).on('click', '.editBugopnBalMasterClass', function() {
		var errorList = [];
		var $link = $(this);

		var opnId = $link.closest('tr').find('td:eq(0)').text(); 
		var authStatus = $link.closest('tr').find('td:eq(4)').text();
		var url = "AccountBudgetOpenBalanceMaster.html?update";
		var requestData = "opnId=" + opnId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Y"){
			$('.content').html(returnData);
		}
		else{
			errorList.push(getLocalMessage("account.opening.bal"));
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
				var opnId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountBudgetOpenBalanceMaster.html?formForView";
				var requestData = "opnId=" + opnId + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type=checkbox]').attr('disabled', true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				return false;
			});

});

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
	
	var  flagFlzd= $("#flagFlzdDup").val();
	
		if(flagFlzd === 'Y') {
			errorList.push("You can not edit finalized opening Balance entry!");
		}
		
			if(errorList.length>0){
	    	
			var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    	});
	    	errorMsg +='</ul>';
	    	$('#errorIdF').html(errorMsg);
	    	$('#errorDivIdF').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
		}
	
	$('body').on('focus',".hasMyNumber", function(){
		$(".hasMyNumber").keyup(function(event) {
	    this.value = this.value.replace(/[^0-9.]/g,'');
	    $(this).attr('maxlength', '13');
	     });	
		}); 	
});

function deleteDataFromGrid(dsgid) {

	var url = "Designation.html?update";
	var requestData = "dsgid=" + dsgid + "&MODE1=" + "Delete";

	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$.fancybox.close();
	$("#grid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');
	return false;
}

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
		/*var levelData = $('#opnId').val();
		levelData++;
		var sid = 'faYearid' + levelData;
		var selID = '#'+sid;
				
		if($('#count').val()=="0"){
			count=0;
		}else{
			count=parseInt($('#count').val());
		}
		//var id=$('#index').val();	
		var assign = count;
		var  fundId= $("#fundId").val();
		var  fieldId= $("#fieldId").val();
		
		var  faYearid= $("#faYearid").val();
		var  opnBalType= $("#opnBalType").val();*/
		//var  fundId = $("#fundId").val();

		//var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		//count = stringId.charAt(stringId.length-1);

		incrementvalue=++count;

		var balanceType=$("#opnBalType option:selected").attr("code");
		if (balanceType === "DR") {
			/*var sacheadIdO = $('#pacHeadIdO').val();
			var openbalAmtO = $('#openbalAmtO').val();
			 
			if (sacheadIdO == '') {
					errorList.push("Please select Account heads");
				}
			if (openbalAmtO == '' || openbalAmtO==0) {
				errorList.push("Please Enter Opening Balance Amount");
			}*/
				
			/*var j =  $("#indexdata").val();		
			var pacHeadId= $('#pacHeadId'+j).val();
			if(pacHeadId != ""){
			for(n=0; n<j;n++){	
				
			if(($('#pacHeadId'+n).val() == $('#pacHeadId'+j).val())){
				errorList.push("The Combination Account Head already exists!");
			}
			}
			}*/
			
			 var Revid =  $("#indexdata").val();
			 
			 var pacHeadId= $('#pacHeadId'+Revid).val();
			 if(pacHeadId != ""){
					var dec;
				 for(m=0; m<=Revid;m++){
					 for(dec=0; dec<=Revid;dec++){
						 if(m!=dec){
						if(($('#pacHeadId'+m).val() == $('#pacHeadId'+dec).val())){
							errorList.push("The Combination AccountHead already exists!");
						}
					  }
					} 
				 }
		   	   }
				
			/*var k =  $("#indexdata").val();
			for(n=0; n<=k;n++){	
			var sacheadIdRev = $('#pacHeadId'+n).val();
			var openbalAmt = $("#openbalAmt"+n).val();
			if (sacheadIdRev == '') {
				errorList.push("Please select Account heads");
			}		 	 
			 if(openbalAmt == '' || openbalAmt==0){
			 errorList.push("Please Enter Opening Balance Amount");
			}
			}*/
			
		} 
		
		var balanceType1=$("#opnBalType option:selected").attr("code");
		if (balanceType1 === "CR") {
			/*var sacheadIdExp = $('#pacHeadIdO').val();
			var openbalAmtExp = $('#openbalAmtO').val();
			
			if (openbalAmtExp == '' || openbalAmtExp==0) {
				errorList.push("Please Enter Opening Balance Amount");
			}
			if(sacheadIdExp == '') {
				errorList.push("Please select Account heads ");
			}*/
			/*var j =  $("#indexdata").val();
			for(n=0; n<j;n++){	
			if(($('#pacHeadId'+n).val() == $('#pacHeadId'+j).val())){
				errorList.push("The Combination Account Head already exists!");
			}
			}*/
			
			var Expid =  $("#indexdata").val();
			
			var pacHeadId= $('#pacHeadId'+Expid).val();
			 if(pacHeadId != ""){
					var dec;
				 for(m=0; m<=Expid;m++){
					 for(dec=0; dec<=Expid;dec++){
						 if(m!=dec){
						if(($('#pacHeadId'+m).val() == $('#pacHeadId'+dec).val())){
							errorList.push("The Combination AccountHead already exists!");
						}
					  }
					} 
				 }
		   	   }
			 
			/*var j =  $("#indexdata").val();
			for(n=0; n<=j;n++){	
				var sacheadIdExp = $('#pacHeadId'+n).val();
				var openbalAmtExp = $('#openbalAmt'+n).val();
				if (sacheadIdExp == '') {
					errorList.push("Please select Account heads");
				}
				 if(openbalAmtExp == '' || openbalAmtExp==0){
				 errorList.push("Please Enter Opening Balance Amount");
				}
				}*/
		}

		/*if(faYearid == '') {
			errorList.push("Please select Financial Year");
		}

		if(opnBalType == '') {
			errorList.push("Please select Opening Balance Type");	
		}
		
		if(fundId == '') {
			errorList.push("Please select Fund");	
		}
		if(fieldId == '') {
			errorList.push("Please select Field");	
		}
		
		if(fundId == '') {
			errorList.push("Please select fund ");
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
		    else{*/
		    /*					

		 return saveOrUpdateForm(obj, 'Saved Successfully', 'AccountBudgetOpenBalanceMaster.html','create');
	    	
	*/		    var	formName =	findClosestElementId(obj, 'form');
	    	    var theForm	=	'#'+formName;
	    	    var requestData = __serializeForm(theForm);
	    	    var url	=	$(theForm).attr('action');
	    	    
	    	    var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
	    	    if(response != false){
	    	       $('.content').html(response);
	    	    }
	    	    
	    	   // var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
	    	   // $('.content').html(response);
	    	   /* if($.isPlainObject(response)) {
	    	    	alert("plain");
					showConfirmBox();
				} else {				
					var divName = '.widget';
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					alert("response");
					return false;
				}*/
	    	 /*   
		    	}*/
	}
	
$(document).ready(function()
		{
	
	//var primaryCode=$('#pacHeadId0').val();
	//$('#sacHeadId0').find('option:gt(0)').remove();
	
	/*if (primaryCode > 0) {
		var postdata = 'pacHeadId=' + primaryCode;
		
		var json = __doAjaxRequest('AccountBudgetOpenBalanceMaster.html?sacHeadItemsList',
				'POST', postdata, false, 'json');
		var  optionsAsString='';
		$.each( json, function( key, value ) {
			optionsAsString += "<option value='" +key+"'>" + value + "</option>";
			});
		$('#sacHeadId0').append(optionsAsString );	
	 }*/
	
	$("#flag").click(function()
			{
		
		if ($('#opnBalType').val() == "") {
			msgBox('Please Select Open Balance Type');
			return false;
		}
		
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


function getPrimarySecondaryALLOpeningBalanceData(obj) {

	$('.error-div').hide();
	var errorList = [];
	
	var fundId = $("#fundId0").val("");
	$("#fundId").val('').trigger('chosen:updated');
	var fieldId = $("#fieldId0").val("");
	$("#fieldId").val('').trigger('chosen:updated');
	var pacHeadId = $("#pacHeadId0").val("");
	$("#pacHeadId").val('').trigger('chosen:updated');
	var openbalAmt = $("#openbalAmt0").val("");
	var flagFlzd = $("#flagFlzd0").val("");
	var cpdIdDrcr = $("#cpdIdDrcr0").val("");
	
	var faYearid = $("#faYearid").val();
	if (faYearid == "" || faYearid =="0") {
		errorList.push('Kindly Select Financial Year');
		$("#opnBalType").val("");
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
       
		var opnBalType = $("#opnBalType").val();
		var drTypeValue = $("#drTypeValue").val();
		var crTypeValue = $("#crTypeValue").val();
		
		if (opnBalType == "L") {
			$("#cpdIdDrcr0").val(crTypeValue);
		}else{
			$("#cpdIdDrcr0").val(drTypeValue);
		}
		
		var openbalAmt = $('#openbalAmt0').val("");
		
		var divName = ".content";
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetOpenBalanceMaster.html?getPrimaryALLOpeningBalanceData";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		
		var id=$('#index').val();	
		$('#openbalAmt'+id).val("");
        return false;
	}
	
};

function searchBudgetOpeningBalanceData(){

	$('.error-div').hide();

	var errorList = [];
	
	var faYearid = $("#faYearid").val();
	var opnBalType = $("#opnBalType").val();
	var sacHeadId = $("#sacHeadId").val();
	var status = $("#status").val();
	
	if ((faYearid == "" || faYearid =="0") && (opnBalType == "" || opnBalType =="0") && (sacHeadId == "" || sacHeadId =="0") && (status == "" || status =="0")) {
		errorList.push('Kindly select at least one search criteria');
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
		var url = "AccountBudgetOpenBalanceMaster.html?getjqGridsearch";
		var requestData = {
			"faYearid" : faYearid,
			"cpdIdDrcr" : opnBalType,
			"sacHeadId" : sacHeadId,
			"status" : status
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

/*function searchBudgetOpeningBalanceData() {

	$('.error-div').hide();

	var errorList = [];
	
	var faYearid = $("#faYearid").val();
	var opnBalType = $("#opnBalType").val();
	var sacHeadId = $("#sacHeadId").val();
	var status = $("#status").val();
	
	if ((faYearid == "" || faYearid =="0") && (opnBalType == "" || opnBalType =="0") && (sacHeadId == "" || sacHeadId =="0") && (status == "" || status =="0")) {
		errorList.push('Kindly select at least one search criteria');
	}
	
	if (errorList.length == 0) {
			
		var url = "AccountBudgetOpenBalanceMaster.html?getjqGridsearch";
		var requestData = {
			"faYearid" : faYearid,
			"cpdIdDrcr" : opnBalType,
			"sacHeadId" : sacHeadId,
			"status" : status
		};

		$.ajax({
					url : url,
					data : requestData,
					datatype : "json",
					type : 'GET',
					success : function(response) {

						$("#grid").jqGrid('setGridParam', {
							datatype : 'json'
						}).trigger('reloadGrid');

					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	} else {
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$('.error-div').html(errMsg);
		$('.error-div').show();
		return false;
	}
};
*/

//to generate dynamic table
$("#opnBalTableDivID").on("click", '.addButton', function(e) {
var errorList = [];

   $('.appendableClass').each(function(i) {
		 var sacHeadId = $.trim($("#pacHeadId"+i).val());
		 if(sacHeadId==0 || sacHeadId=="") 
		 //errorList.push("Please select Account Heads");
		
		 var openbalAmt = $.trim($("#openbalAmt"+i).val());
		 if(openbalAmt==0 || openbalAmt=="")
		 //errorList.push("Please Enter Opening Balance Amount");
		
		 for(m=0; m<i;m++){
				if(($('#pacHeadId'+m).val() == $('#pacHeadId'+i).val())){
					
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
			var content = $(this).closest('#opnBalTable tr').clone();
			// call that script function which enable for search
			// remove extra added div by id 
			$(this).closest("#opnBalTable").append(content);
			// reset values
			content.find("select").attr("value", "");
			content.find("input:text").val("");
			content.find("select").val("");
			content.find("input:checkbox").attr('checked',false);
			content.find('div.chosen-container').remove();
			//content.find("select").chosen().trigger("chosen:updated");
			$(".applyChoosen").chosen().trigger("chosen:updated");
			content.find('label').closest('.error').remove(); //for removal duplicate
			reOrderTableIdSequence();

		});

	
	//to delete row
	$("#opnBalTableDivID").on("click", '.delButton', function(e) {
	
	var rowCount = $('#opnBalTable tr').length;
	if (rowCount <= 2) {
	//alert("Can Not Remove");
	var msg = "can not remove";
	showErrormsgboxTitle(msg);
	return false;
	}
	
	$(this).closest('#opnBalTable tr').remove();
	reOrderTableIdSequence();
	e.preventDefault();
	});

function reOrderTableIdSequence() {
$('.appendableClass').each(function(i) {
		
		$(this).find('div.chosen-container').attr('id',"pacHeadId"+i+"_chosen");
		//$(this).find("select:eq(0)").attr("id", "pacHeadId" + i);
		$(this).find("select:eq(0)").attr("id", "pacHeadId" + i);
		$(this).find("input:text:eq(0)").attr("id", "pacHeadId" + i);
		$(this).find("input:text:eq(1)").attr("id", "openbalAmt" + i);
		$(this).find("input:checkbox:eq(0)").attr("id", "flagFlzd" + i);
		$(this).find("select:eq(1)").attr("id", "cpdIdDrcr" + i);
		
		$(this).find("select:eq(0)").attr("name","bugReappMasterDtoList[" + i + "].sacHeadId");
		$(this).find("input:text:eq(0)").attr("name","bugReappMasterDtoList[" + i + "].sacHeadId");
		$(this).find("input:text:eq(1)").attr("name","bugReappMasterDtoList[" + i + "].openbalAmt");
		$(this).find("input:checkbox:eq(0)").attr("name","bugReappMasterDtoList[" + i + "].flagFlzd");
		$(this).find("select:eq(1)").attr("name","bugReappMasterDtoList[" + i + "].cpdIdDrcr");
		$(this).find('.delButton').attr("id",	"delButton" + i);
		$(this).find('.addButton').attr("id",	"addButton" + i);
		$(this).find('#pacHeadId'+i).attr("onchange", "findduplicatecombinationexit(" + (i) + ")");
		$(this).find('#openbalAmt'+i).attr("onchange", "getAmountIndianCurrencyFormat(" + (i) + ")");
		$(this).closest("tr").attr("id", "opnBalId" + (i));
		$("#indexdata").val(i);
		/*var opnBalType = $("#opnBalType").val();
		var drTypeValue = $("#drTypeValue").val();
		var crTypeValue = $("#crTypeValue").val();
		
		
		if(opnBalType != null && opnBalType != ""){
		if (opnBalType == "L") {
			$("#cpdIdDrcr"+i).val(crTypeValue);
		}else{
			$("#cpdIdDrcr"+i).val(drTypeValue);
			}
		}*/
	});

}

function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var succ = getLocalMessage('accounts.fieldmaster.update');
	var cls = getLocalMessage('account.proceed.btn');
	
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+succ+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToDishonorHomePage () {
	//$.fancybox.close();
	window.location.href='AccountBudgetOpenBalanceMaster.html';
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


function clearAccountHeadsCode(obj){
	var errorList = [];
	var opnBalType = $('#opnBalType').val();
	
	if (opnBalType == '') {
		errorList.push('Please Select Open Balance Type');
		var fundId = $("#fundId").val("");
		$("#fundId").val('').trigger('chosen:updated');
		var fieldId = $("#fieldId").val("");
		$("#fieldId").val('').trigger('chosen:updated');
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
	
	 /*var id =$('#indexdata').val();
	 
	 var pacHeadIdO = $("#pacHeadId0").val("");
	var pacHeadId = $("#pacHeadId"+id).val("");*/
	
}

function clearAll(obj){
	
		var opnBalType = $("#opnBalType").val("");
		var fundId = $("#fundId").val("");
		
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
	
	 var id =  $("#indexdata").val();
	 
	var pacHeadId = $('#pacHeadId'+id).val();
	
	if ($('#faYearid').val() == "") {
		//errorList.push("Please Select Financial Year!");
		var sacHeadId = $('#pacHeadId'+cnt).val("");
		$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
	}
	
	if ($('#opnBalType').val() == "") {
		//errorList.push("Please Select Open Balance Type!");
		var sacHeadId = $('#pacHeadId'+cnt).val("");
		$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
	}
	
	if ($('#fundId').val() == "") {
		//errorList.push("Please Select Fund!");
		var sacHeadId = $('#pacHeadId'+cnt).val("");
		$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
	}
	 
	if ($('#fieldId').val() == "") {
		//errorList.push("Please Select Field!");
		var sacHeadId = $('#pacHeadId'+cnt).val("");
		$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
	}
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
					if(($('#pacHeadId'+m).val() == $('#pacHeadId'+dec).val())){
						errorList.push("The Combination AccountHead already exists!");
						$("#pacHeadId"+cnt).val("");
						$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
						var openbalAmt = $("#openbalAmt"+cnt).val("");
						var flagFlzd = $("#flagFlzd"+cnt).attr('checked',false);
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
	
		/*//var orginalEstamtO = $("#orginalEstamtO").val("");
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);
		var url = "AccountBudgetOpenBalanceMaster.html?getOpnBalDuplicateGridloadData&cnt="+cnt;
		var returnData = __doAjaxRequest(url, 'post', requestData, false); 
		*/
		var url = "AccountBudgetOpenBalanceMaster.html?getOpnBalDuplicateGridloadData&cnt="+cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		
		 if(returnData){
			 
			 errorList.push(getLocalMessage("account.bal.already.enter"));
			var sacHeadId = $('#pacHeadId'+cnt).val("");
			$("#pacHeadId"+cnt).val('').trigger('chosen:updated');
			var sacHeadIdO = $('#pacHeadIdO').val("");
			$("#pacHeadIdO").val('').trigger('chosen:updated');
			
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


function findClearAllData(obj) {

	$('.error-div').hide();
	var errorList = [];
	
	var pacHeadId = $("#pacHeadId0").val("");
	$("#pacHeadId").val('').trigger('chosen:updated');
	var openbalAmt = $("#openbalAmt0").val("");
	var flagFlzd = $("#flagFlzd0").val("");
	
	var opnBalType = $("#opnBalType").val();
	var faYearid = $("#faYearid").val();
	
	if(opnBalType==0 || opnBalType==""){
		//errorList.push("Please Select Opening Balance Type!");
		var fundId = $('#fundId').val("");
		$("#fundId").val('').trigger('chosen:updated');
		var fieldId = $('#fieldId').val("");
		$("#fieldId").val('').trigger('chosen:updated');
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
       
		var openbalAmt = $('#openbalAmt0').val("");
		
		var divName = ".content";
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetOpenBalanceMaster.html?getPrimaryALLOpeningBalanceData";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		
		var id=$('#index').val();	
		$('#openbalAmt'+id).val("");
        return false;
        
	}
};

function getAmountIndianCurrencyFormat(cont){
	
	$('.error-div').hide();
	var errorList = [];
	
	var pacHeadId = $("#pacHeadId"+cont).val();
	var openbalAmt = $('#openbalAmt'+cont).val();
	
	if(pacHeadId==0 || pacHeadId==""){
		errorList.push(getLocalMessage("account.select.acchead"));
		$('#openbalAmt'+cont).val("");
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
		
		if(openbalAmt != null){

		/*var divName = ".content";
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		*/
		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		var url = "AccountBudgetOpenBalanceMaster.html?getAmountIndianCurrencyFormat&cont="+cont;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		$('#openbalAmt'+cont).val(returnData);
		}
	}
};


function getAmountIndianCurrencyFormatStatic(cont){
	
	$('.error-div').hide();
	var errorList = [];
	
	var pacHeadIdO = $("#pacHeadIdO").val();
	var openbalAmtO = $('#openbalAmtO').val();
	
	if(pacHeadIdO==0 || pacHeadIdO==""){
		errorList.push("Please Select Account Head");
		$('#openbalAmtO').val("");
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
		
		if(openbalAmtO != null){

		/*var divName = ".content";
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		*/
		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		var url = "AccountBudgetOpenBalanceMaster.html?getAmountIndianCurrencyFormat&cont="+cont;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		$('#openbalAmtO').val(returnData);
		}
	}
};

function exportTemplate(){
	
	var $link = $(this);
	/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
	//var functionId = 1;
	var url = "AccountBudgetOpenBalanceMaster.html?exportTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}
function closeOutErrorBox(){
	$('#errorDivSec').hide();
}
