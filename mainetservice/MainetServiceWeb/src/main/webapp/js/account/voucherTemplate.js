
$("#formDTO").validate({
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

formURL='VoucherTemplate.html';
$(document).ready(function(){
	$("#voucherTemplatesGrid").jqGrid(
			{   
				url : formURL+"?getGridData",
				datatype : "json",
				mtype : "GET",
				colNames : [ '',getLocalMessage('voucher.template.entry.templatetype'),getLocalMessage('voucher.template.entry.vouchertype'), getLocalMessage('voucher.template.entry.templatefor'), getLocalMessage('receivable.demand.entry.deptName'),getLocalMessage('account.budgetopenmaster.financialyear'),getLocalMessage('voucher.template.entry.status'), getLocalMessage('voucher.template.entry.action')],
				colModel : [ {name : "gridId",width : 5,  hidden :  true},
				             {name : "templateTypeDesc",width : 15,search :false},
				             {name : "voucherTypeDesc",width : 15,search :true}, 
				             {name : "templateForDesc",width : 15,  sortable :  true},
				             {name : "departmentDesc",width : 15,search :true}, 
				             {name : "financialYearDesc",width : 15,  sortable :  true},
				             {name : "statusDesc",width : 10,search :false}, 
				             { name: 'enbll', index: 'enbll', width: 15, align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#voucherTemplatesPager",
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
				caption : getLocalMessage('voucher.template.entry.vouchertemplateentryDetails')
			});
	       $("#voucherTemplatesGrid").jqGrid('navGrid','#voucherTemplatesPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");

	
	 /**
	  * being used to open voucher template create page
	  */     
	 $('#createTemplate').click(function() {
		var ajaxResponse = __doAjaxRequest(formURL+'?createPage', 'GET', {}, false,'html');
		$('.content').html(ajaxResponse);
		chosen();
		var prefixNotFound = $.trim($('#noDataFound').val());
		var errorList = [];
		errorList.push(''+prefixNotFound);
		if (prefixNotFound == 'N' || prefixNotFound == '' || prefixNotFound == null || prefixNotFound == undefined) {
			// do nothing
		} else {
			displayErrorsOnPage(errorList);
		}
	 });
	 
	 /**
	  * being used to save voucher template
	  */     
	 $('#saveBtn').click(function(e) {
		 	$('.error-div').hide();
			var errorList = [];
		 	    var requestData = $('#VoucherTemplateCreate').serialize();
		 		var response= __doAjaxRequestValidationAccor(e,formURL+'?saveForm', 'POST', requestData, false, 'json');
		 	    if(response != false){
		 	    	var url = 'VoucherTemplate.html';
					displayMessageOnSubmit(ajaxResponse, url);
		 	    }
	 });
	 
	
	 /**
	  * being used to search records 
	  */
	 $('#searchTemplate').click(function() {
		 
		 $('.error-div').hide();
			var errorList = [];

			var faYearid = $('#financialYear').val();
			var templateType = $('#templateType').val();
			var voucherType = $('#voucherType').val();
			var department = $('#department').val();
			var templateFor = $('#templateFor').val();
			var status = $('#status').val();
			
			if ((faYearid == '' || faYearid =="0" || faYearid==undefined) && (templateType == '' || templateType =="0" || templateType==undefined) && (voucherType == '' || voucherType =="0" || voucherType==undefined) && (department == '' || department =="0" || department==undefined) && (templateFor == '' || templateFor =="0" || templateFor==undefined) && (status == '' || status =="0" || status==undefined)) {
				errorList.push(getLocalMessage('account.deposit.search.validation'));
			}
			 if(errorList.length>0){
				 displayErrorsOnPage(errorList);
			    }
			 
		 if (errorList.length == 0) {
		 
		 var requestData = $('#VoucherTemplateList').serialize();
		 var ajaxResponse = __doAjaxRequest(formURL+'?searchTemplate', 'GET', requestData, false,'json');
		 if (ajaxResponse == 'ERROR') {
			 renderErrorPage();
			 var errorList = [];
			 errorList.push(ajaxResponse);
			 displayErrorsOnPage(errorList);
		 } else {
			 if ( ajaxResponse != '') {
				 var errorList = [];
				 errorList.push(ajaxResponse);
				 displayErrorsOnPage(errorList);
				 $("#voucherTemplatesGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 } else {
				 console.log('reloadGrid');
				 $("#voucherTemplatesGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 }
			
		 }
		}
	 });
	 
	 /**
	  * event on select of Template Type
	  */
	 $('#templateType').change(function() {
		 
		var templateCode = $("#templateType option:selected").attr("code");
		if (templateCode == 'PN') {
			$("#financialYear").prop('disabled', true).trigger("chosen:updated");
			$('#financialYear').val('0').trigger("chosen:updated");
		} else if (templateCode == 'FYW') {
			$('#financialYear').val($('#currentFYearId').val()).trigger("chosen:updated");
			$("#financialYear").prop('disabled', false).trigger("chosen:updated");
			
			$("#financialYear").data('rule-required',true);
			
		}
		
		
	 });
	 
	
	 /**
	  * being used to add row dynamically in Mapping Details
	  */
	 $("#mappingDetails").on('click','.addRow',function(){
			var errorList = [];
			
			if (errorList.length == 0) {
				errorList = validateTableRows(errorList);
				if (errorList.length == 0) {
					var content = $(this).closest('tr').clone();
					$(this).closest("tr").after(content);
					var clickedIndex = $(this).parent().parent().index() - 1;	
					content.find('div.chosen-container').remove();
    				$(".applyChoosen").chosen().trigger("chosen:updated");
    				content.find("select").val('0');
    				content.find('label').closest('.error').remove(); //for removal duplicate
					$('.error-div').hide();
					$("#indexdata").val(i);
					reOrderIdAndPath('.tableRowClass');
					 $('.required-chosen').next().addClass('mandColorClass');
					 $('.required-control').next().children().addClass('mandColorClass');
					return false;
				} else {
				displayErrorsOnPage(errorList);
			}
			} else {
				displayErrorsOnPage(errorList);
			}
		}); 
	 
	 /**
	  * 
	  */
	 $("#mappingDetails").on('click','.removeRow',function(){
			
			if($("#mappingDetails tr").length != 2) {
				$(this).parent().parent().remove();					
				reOrderIdAndPath('.tableRowClass');
			} else {
				var errorList = [];
				errorList.push("First row cannot be remove.");
				displayErrorsOnPage(errorList);
			}
			 
		});
	 
	 
	
	 
	 
});	 

function addLink(cellvalue, options, rowObject) 
{
   return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewVoucherTemplate('"+rowObject.gridId+"')\"><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editVoucherTemplate('"+rowObject.gridId+"')\"><i class='fa fa-pencil'></i></a> ";
}

/**
 * used to open form in view mode
 * @param gridId
 */
function viewVoucherTemplate(gridId){
	var divName	=	formDivName;
    var requestData = 'gridId='+gridId;
    var ajaxResponse = __doAjaxRequest(formURL+'?view', 'GET', requestData, false,'html');
	$('.content').html(ajaxResponse);
	
}
/**
 * used to open form in edit mode
 * @param gridId
 */
function editVoucherTemplate(gridId) {
	 var requestData = 'gridId='+gridId;
	    var ajaxResponse = __doAjaxRequest(formURL+'?edit', 'GET', requestData, false,'html');
		$('.content').html(ajaxResponse);
}


function deleteTenant(tenantId,code){
   
	 var yes = getLocalMessage('eip.commons.yes');
	 var warnMsg="Are you sure to delete " + code ;
	 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
	' onclick="onDelete(\''+tenantId+'\')"/>'+	
	'</div>';
	
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;
}

function onDelete(id){
	 var requestData = 'tenantId='+id;
	 var response = __doAjaxRequest(tenantUrl+'?deleteTenant', 'POST', requestData, false,'json');
	 if(response){
			$("#tenantGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
			closeFancyOnLinkClick(childDivName);
	     }else{
	    	    $(childDivName).html("Internal errors");
	    		showModalBox(childDivName);
	     }
}



function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',tenantUrl);
	$("#postMethodForm").submit();
}

/**
 * this function being used to validate mapping details(Table records)
 * @param errorList : empty errorList Array
 * @returns Array of Error
 */
function validateTableRows(errorList) {
	
	$('.tableRowClass').each(function(i) {
		errorList = validateRowData(errorList,i);
	});
	return errorList
}



/**
 * being used to validate Mapping details combination 
 * duplicate rows cannot be added.
 * @param errorList
 * @returns
 */
function validateMappingCombination(errorList) {
	var rows= $('#mappingDetails tr').length - 1;
	if (rows > 1) {
	}
	return errorList
}

/**
 * 
 * @param selectedObj
 */
function onModeChange (selectedObj) {
	
	var selectedRowMode = $('option:selected', selectedObj).attr('code');
	var id = selectedObj.id;
	console.log('id='+id);
	var splitArr= id.split('_');
	var index =splitArr[1];
	var debitCredit_Id= "#debitCredit_"+index;
	var accountType_Id= "#accountType_"+index;
	var selectedRowDebitCredit = $('option:selected', $(debitCredit_Id)).attr('code');//$(debitCredit_Id).val(); 
	var selectedRowAccountType = $('option:selected', $(accountType_Id)).attr('code');//$(accountType_Id).val(); 
	var rows= $('#mappingDetails tr').length - 1;
	console.log('selected row='+selectedRowAccountType+'|'+selectedRowDebitCredit+'|'+selectedRowMode);
	if (rows > 1) {
		console.log('rows='+rows);
		$('.tableRowClass').each(function(i) {
			 var accountType = $('option:selected', $("#accountType_"+i)).attr('code');//$.trim($("#accountType_"+i).val());
			 var debitCredit = $('option:selected', $("#debitCredit_"+i)).attr('code');//$.trim($("#debitCredit_"+i).val());		
			 var mode = $('option:selected', $("#mode_"+i)).attr('code');//$.trim($("#mode_"+i).val());		
			 console.log('each row='+accountType+'|'+debitCredit+'|'+mode);	
			if (accountType == selectedRowAccountType
					&& debitCredit == selectedRowDebitCredit
					&& mode == selectedRowMode) {
				if (index == i) {
					
				} else {
					$("#"+id).val('0').trigger("chosen:updated");
					var errorList = [];
					errorList.push('Row cannot be duplicate in Mapping Details');
					displayErrorsOnPage(errorList);
					return false;
				}
				
			} else {
				$("#errorDivId").hide();
			}
			 
		});
		
	}
	
}

/**
 * being used to reset Mode on change of Account Type
 */
function onAccountTypeChange(selectedObj) {
	console.log('onAccountTypeChange');
	var id = selectedObj.id;
	var splitArr= id.split('_');
	var index =splitArr[1];
	// reset Mode 
	$('#mode_'+index).val('0').trigger("chosen:updated");
}

/**
 *  being used to reset Mode on change of Dr./Cr.
 * @param selectedObj
 */
function onDrCrChange(selectedObj) {
	console.log('onDrCrChange');
	var id = selectedObj.id;
	var splitArr= id.split('_');
	var index =splitArr[1];
	// reset Mode 
	$('#mode_'+index).val('0').trigger("chosen:updated");;
}



/**
 * this function being used to validate each row records
 * @param errorList
 * @param i
 * @returns
 */
function validateRowData(errorList, i) {
	 
	 var accountType = $.trim($("#accountType_"+i).val());
	 var debitCredit = $.trim($("#debitCredit_"+i).val());		
	 var mode = $.trim($("#mode_"+i).val());		
	 var budgetId = $.trim($("#budgetId_"+i).val());
	 if(accountType =="" || accountType =='0'  || accountType == undefined ){
		 errorList.push(getLocalMessage('account.type.selected.row ')+(i+1));
	 }
	 if(debitCredit == "" || debitCredit =='0' || debitCredit == undefined){
		 errorList.push(getLocalMessage('account.Dr.Cr.selected.validation')+(i+1));  
	 }
	 if(mode == "" || mode =='0' || mode == undefined){
		 errorList.push(getLocalMessage('account.mode.selected.validation')+(i+1));  
	 }
	 
	 var templateForDMD = $("#templateFor option:selected").attr("code");

		if(templateForDMD === 'DMD') {
			if(budgetId == "" || budgetId =='0' || budgetId == undefined){
			 }
		}
	 return errorList;
}

/**
 * being used to re-ordering table id's and path bindings
 * @param className : pass cssClass name
 */
function reOrderIdAndPath(className) {
	
	$(className).each(function(i) {
		
		$(this).find($('[id^="accountType_"]')).attr('id',"accountType_"+i+"_chosen");
		$(this).find($('[id^="mode_"]')).attr('id',"mode_"+i+"_chosen");
		$(this).find($('[id^="budgetId_"]')).attr('id',"budgetId_"+i+"_chosen");
		
		// re-ordering id 	
		$(this).find("select:eq(0)").attr("id", "accountType_"+i);
		$(this).find("select:eq(1)").attr("id", "debitCredit_"+i);
		$(this).find("select:eq(2)").attr("id", "mode_"+i);
		$(this).find("select:eq(3)").attr("id", "budgetId_"+i);
		$("#srNoId_"+i).text(i+1);
		
		// re-ordering path binding
		$(this).find("select:eq(0)").attr("name","mappingDetails["+i+"].accountType");
		$(this).find("select:eq(1)").attr("name","mappingDetails["+i+"].debitCredit");
		$(this).find("select:eq(2)").attr("name","mappingDetails["+i+"].mode");
		$(this).find("select:eq(3)").attr("name","mappingDetails["+i+"].sacHeadId");
		$(this).find('#mode_'+i).attr("onchange", "findduplicatecombinationexitDynamic(" + (i) + ")");
		$(this).find('#accountType_'+i).attr("onchange", "onAccountTypeModeBudgetHeadChange(" + (i) + ")");
		$(this).find('#budgetId_'+i).attr("onchange", "selectAccountType(" + (i) + ")");
		
		$("#indexdata").val(i);		
	});
	
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

/**
 * being used on mode select to get related budget head
 * @param selectedObj
 */


function renderErrorPage() {
	var response =__doAjaxRequest(formURL+'?errorPage', 'GET', {}, false,'HTML');
	$('.content').html(response);
}

function displayMessageOnSubmit(successMsg,redirectURL) {
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.proceed.btn');
	//var redirectToURL = ''+redirectURL;
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToHomePage(\''+redirectURL+'\')"/></div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToHomePage (redirectURL) {
	$.fancybox.close();
	window.location.href=redirectURL;
}

function showPopUpMsg(childDialog) {
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

function changeAllFlagStatus(obj) {

	var type=$("#status option:selected").attr("code");
	var typeId = $("#status").val();
	
	$('.tableRowClass').each(function(i) {
		if(type=="I")
		{
		$("select[id^='status_"+i+"']").val(typeId).trigger("chosen:updated");
		}
		else
		{
		$("select[id^='status_"+i+"']").val(typeId).trigger("chosen:updated");
		}
	});
}

function clearTemplateForData(obj){
	
	var templateType = $("#templateType option:selected").attr("code");
	if(templateType == "PN"){
	}
	$('#templateFor').val("0"); 
	$("#templateFor").val('').trigger('chosen:updated');
}

function selectTemplateTypeData(obj){

	$('.error-div').hide();
	var errorList = [];
	
	$('#templateFor').val("0"); 
	$("#templateFor").val('').trigger('chosen:updated');
	
	var  templateType= $("#templateType").val();
	if(templateType == '' || templateType == '0') {
	 		errorList.push(getLocalMessage("account.template.type.validation"));
	 		$('#financialYear').val("0"); 
			$('#financialYear').val('').trigger('chosen:updated');
	}
	
	 if(errorList.length>0){
	    	
		 displayErrorsOnPage(errorList);
			return false;
	 }
}

function findduplicatecombinationexit(obj) {
	
	if ($('#count').val() == "") {
		count = 1;
	} else {
		count = parseInt($('#count').val());
	}
	var assign = count;

	$('.error-div').hide();
	var errorList = [];
	var  templateType= $("#templateType").val();
 	var  voucherType= $("#voucherType").val();
 	var  department= $("#department").val();
 	if(templateType == '' || templateType == '0') {
 		errorList.push(getLocalMessage("account.template.type.validation"));
 		$('#templateFor').val("0"); 
		$("#templateFor").val('').trigger('chosen:updated');
 	}
 	
 	var templateType=$("#templateType option:selected").attr("code");
	if (templateType === "FYW") {
	var  financialYear= $("#financialYear").val();	
 	if(financialYear == '' || financialYear == '0') {
 		errorList.push("Please select financial Year");	
 		$('#templateFor').val("0"); 
		$("#templateFor").val('').trigger('chosen:updated');
 	}
	}
	
	var templateForDMD = $("#templateFor option:selected").attr("code");
	var enableTemplateForArr = ['RBT','RV','DMD','DMP','WCC','DSR','CHQ','RDL','DS','PCW','CWE','PCD','RDC','NAJ','PAJ','PVE','DHC','SAD','ACE','ACB','ACS','ACW','ACP','ASE','RCR'];
	var found = enableTemplateForArr.indexOf(templateForDMD);
	if (found >=0 ) {
		$(".budgetIdCss").prop("disabled",false).trigger("chosen:updated");	
	} else {
		$(".budgetIdCss").prop("disabled",true).trigger("chosen:updated");
	}
 	if(voucherType == '' || voucherType == '0') {
 		errorList.push(getLocalMessage("account.select.voucher.type"));
 		$('#templateFor').val("0"); 
		$("#templateFor").val('').trigger('chosen:updated');
 	}
 	if(department == '' || department == '0') {
 		errorList.push(getLocalMessage("account.dept"));
 		$('#templateFor').val("0"); 
		$("#templateFor").val('').trigger('chosen:updated');
 	}
		 if(errorList.length>0){
				displayErrorsOnPage(errorList);
				return false;
		    }
		 
	if (errorList.length == 0) {
		
		var requestData = $('#VoucherTemplateCreate').serialize();
		var url = 'VoucherTemplate.html?getVoucherDuplicateData';
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		
		 if(returnData){
					
			errorList.push("Voucher template is already exists for this combination!"); 
			$('#templateFor').val("0"); 
			$("#templateFor").val('').trigger('chosen:updated');
			
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

		  		$.each(errorList, function(index) {
		  			
					displayErrorsOnPage(errorList);
		  		});
		  		return false;
		}
	}
	
};

function findduplicatecombinationexitDynamic(cnt) {
	$('.error-div').hide();
	var errorList = [];
	var  accountType = $("#accountType_"+cnt).val();
 	var  debitCredit = $("#debitCredit_"+cnt).val();
 	if(accountType == '' || accountType == '0') {
 		errorList.push("Please select account Type");
 		$('#mode_'+cnt).val("0"); 
		$("#mode_"+cnt).val('').trigger('chosen:updated');
 	}
 	
 	if(debitCredit == '' || debitCredit == '0') {
 		errorList.push("Please select debitCredit Type");
 		$('#mode_'+cnt).val("0"); 
		$("#mode_"+cnt).val('').trigger('chosen:updated');
 	}
 	
 	var id =  $("#indexdata").val();
	var mode = $('#mode_'+id).val();
 	var Revid =  $("#indexdata").val();
	if(mode != "" ){
		var dec;
	 for(m=0; m<=Revid;m++){
		 for(dec=0; dec<=Revid;dec++){
			 if(m!=dec){
			if(($('#accountType_'+m).val() == $('#accountType_'+dec).val()) && ($('#debitCredit_'+m).val() == $('#debitCredit_'+dec).val()) && ($('#mode_'+m).val() == $('#mode_'+dec).val())){
				errorList.push("Row cannot be duplicate in Mapping Details!");
				$('#mode_'+cnt).val("0"); 
				$("#mode_"+cnt).val('').trigger('chosen:updated');
				$('#budgetId_'+cnt).val("0"); 
				$("#budgetId_"+cnt).val('').trigger('chosen:updated');
			}
		  }
		} 
	  }
	}
	
		 if(errorList.length>0){
			 
			 displayErrorsOnPage(errorList);
				return false;
		    }
};


function findduplicatecombinationexitEditDynamic(cnt) {

	$('.error-div').hide();
	var errorList = [];
	var  accountType = $("#accountType_"+cnt).val();
 	var  debitCredit = $("#debitCredit_"+cnt).val();
 	if(accountType == '' || accountType == '0') {
 		errorList.push("Please select account Type");
 		$('#mode_'+cnt).val("0"); 
		$("#mode_"+cnt).val('').trigger('chosen:updated');
 	}
 	
 	if(debitCredit == '' || debitCredit == '0') {
 		errorList.push("Please select debitCredit Type");
 		$('#mode_'+cnt).val("0"); 
		$("#mode_"+cnt).val('').trigger('chosen:updated');
 	}
 	
 	var count = 0;
 	$('.tableRowClass').each(function(i) {
		var type=$("#mode_"+i+" option:selected").attr("code");
	    count++;
	});
 	

	var mode = $('#mode_'+count).val();
	if(mode != "" ){
		var dec;
	 for(m=0; m<=count;m++){
		 for(dec=0; dec<=count;dec++){
			 if(m!=dec){
			if(($('#accountType_'+m).val() == $('#accountType_'+dec).val()) && ($('#debitCredit_'+m).val() == $('#debitCredit_'+dec).val()) && ($('#mode_'+m).val() == $('#mode_'+dec).val())){
				errorList.push("Row cannot be duplicate in Mapping Details!");
				$('#mode_'+cnt).val("0"); 
				$("#mode_"+cnt).val('').trigger('chosen:updated');
			}
		  }
		} 
	  }
	}
	
		 if(errorList.length>0){
			 
			 displayErrorsOnPage(errorList);
				return false;
		    }
};

function onAccountTypeModeBudgetHeadChange(cnt) {
	
	console.log('onAccountTypeModeBudgetHeadChange');
	$('.error-div').hide();
	var errorList = [];
	
	var  templateType= $("#templateType").val();
 	var  voucherType= $("#voucherType").val();
 	var  department= $("#department").val();
 	var  templateFor = $("#templateFor").val();
 	
 	if(templateType == '' || templateType == '0') {
 		errorList.push(getLocalMessage("account.template.type.validation"));
 		$('#accountType_'+cnt).val("0"); 
		$('#accountType_'+cnt).val('').trigger('chosen:updated');
 	}
 	
 	var templateType=$("#templateType option:selected").attr("code");
	if (templateType === "FYW") {
	var  financialYear= $("#financialYear").val();	
 	if(financialYear == '' || financialYear == '0') {
 		errorList.push("Please select financial Year");	
 		$('#accountType_'+cnt).val("0"); 
		$('#accountType_'+cnt).val('').trigger('chosen:updated');
 	}
	}
	if(voucherType == '' || voucherType == '0') {
 		errorList.push(getLocalMessage("account.select.voucher.type"));
 		$('#accountType_'+cnt).val("0"); 
		$('#accountType_'+cnt).val('').trigger('chosen:updated');
 	}
 	if(department == '' || department == '0') {
 		errorList.push(getLocalMessage("account.dept"));
 		$('#accountType_'+cnt).val("0"); 
		$('#accountType_'+cnt).val('').trigger('chosen:updated');
 	}
 	if(templateFor == '' || templateFor == '0' || templateFor == undefined) {
 		errorList.push(getLocalMessage("account.template.select.for.validation"));
 		$('#accountType_'+cnt).val("0"); 
		$('#accountType_'+cnt).val('').trigger('chosen:updated');
 	}
 	
 	$("#debitCredit_"+cnt).val("0");
 	$("#mode_"+cnt).val("0");
	$("#mode_"+cnt).val('').trigger('chosen:updated');
	$("#budgetId_"+cnt).val("0");
	$("#budgetId_"+cnt).val('').trigger('chosen:updated');
	 
	var templateForA = $("#templateFor").val();

	if(templateForA != null && templateForA !="0" && templateForA !=undefined){
	var templateForDMD = $("#templateFor option:selected").attr("code");
	var enableTemplateForArr = ['RBT','RV','DMD','DMP','WCC','DSR','CHQ','RDL','DS','PCW','CWE','PCD','RDC','NAJ','PAJ','PVE','DHC','SAD','ACE','ACB','ACS','ACW','ACP','ASE','RCR'];
	var found = enableTemplateForArr.indexOf(templateForDMD);
	if (found >= 0) {
		$("#budgetId_"+cnt).prop("disabled",false).trigger("chosen:updated");	
	} else {
		$("#budgetId_"+cnt).prop("disabled",true).trigger("chosen:updated");
	}
	}
	
	var templateForE = $("#templateForEdit").val();

	if(templateForE != null && templateForE !="0" && templateForE !=undefined){
	if (found >= 0) {
		$("#budgetId_"+cnt).prop("disabled",false).trigger("chosen:updated");	
	}else{
		$("#budgetId_"+cnt).prop("disabled",true).trigger("chosen:updated");
	}
	}
	
	 if(errorList.length>0){
	    	
		 displayErrorsOnPage(errorList);
			return false;
	    	  	
	 }
	 
	if (errorList.length == 0) {
		var requestData = $('#VoucherTemplateCreate').serialize();
		var url = "VoucherTemplate.html?getVoucherPacSacHeadData&cnt="+cnt;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('#budgetId_'+cnt).text('');
		$('#budgetId_'+cnt).append($('<option>', { 
	        value: '0',
	        text : 'Select'
		   }));
		$.each(returnData, function(key, value) {
			$('#budgetId_'+cnt).append($('<option>', { 
		        value: key,
		        text : value 
		    }));
			$('#budgetId_'+cnt).trigger("chosen:updated");
		});
	}
};


function onAccountTypeModeBudgetHeadEditChange(cnt) {
	console.log('onAccountTypeModeBudgetHeadChange');
	
	$('.error-div').hide();
	var errorList = [];
	
	 if(errorList.length>0){
	    	
		 displayErrorsOnPage(errorList);
			return false; 	
	 }
	 
	if (errorList.length == 0) {

		var requestData = $('#VoucherTemplateCreate').serialize();
		var url = "VoucherTemplate.html?getVoucherPacSacHeadData&cnt="+cnt;
		
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		
		$('#budgetId_'+cnt).text('');
		$('#budgetId_'+cnt).append($('<option>', { 
	        value: '0',
	        text : 'Select'
		   }));
		
		$.each(returnData, function(key, value) {
		
			$('#budgetId_'+cnt).append($('<option>', { 
		        value: key,
		        text : value 
		    }));
			$('#budgetId_'+cnt).trigger("chosen:updated");
		});
	}
};

/**
 * event on select of TemplateFor DMD All BudgetHead Data is Enabled otherwise disabled
 */
	$(document).ready(function(){
		
		$('.error-div').hide();
		var errorList = [];
		
		 var status = $("#status option:selected").attr("code");
			if(status === 'I') {
				errorList.push("You can not edit Inactive voucher Template!");
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
			
		var templateFor=$("#templateForEdit option:selected").attr("code");
		var enableTemplateForArr = ['RBT','RV','DMD','DMP','WCC','DSR','ASE','CHQ','RDL','DS','PCW','CWE','PCD','RDC','NAJ','PAJ','PVE','DHC','SAD','ACE','ACB','ACS','ACW','ACP','RCR'];
		var found = enableTemplateForArr.indexOf(templateFor);
		$('.tableRowClass').each(function(i) {
			
			if(found >= 0){
			$("select[id^='budgetId_"+i+"']").prop("disabled",false).trigger("chosen:updated");
			}
			else
			{
				$("select[id^='budgetId_"+i+"']").prop("disabled",true).trigger("chosen:updated");
			}
		});
		
		$("#templateTypeEdit").prop('disabled', true).trigger("chosen:updated");
		$("#templateForEdit").prop('disabled', true).trigger("chosen:updated");
		
		var curFinYearIdValue = $('#curFinYearIdExistFlag').val();
		if(curFinYearIdValue === 'Y') {
			errorList.push('Current Financial Year Is not Set.');
		}
		
		if(errorList.length>0){
			displayErrorsOnPage(errorList);	
		}
		 
	});

	function selectAccountType(cnt) {
		console.log('selectAccountType in BudgetHaed');
		
		$('.error-div').hide();
		var errorList = [];
		
		var  accountType = $("#accountType_"+cnt).val();
	
		if(accountType == '' || accountType == '0') {
	 		errorList.push("Please select Account Type");
	 		$('#budgetId_'+cnt).val("0"); 
			$('#budgetId_'+cnt).val('').trigger('chosen:updated');
	 	}
		
		if(errorList.length>0){
			 
		 displayErrorsOnPage(errorList);
   		 return false;
		}
	};
	
	
 function createTemplateSave(obj) {
	 	$('.error-div').hide();
		var errorList = [];
		var count = 0;
		var countCr = 0;
		
		$('.tableRowClass').each(function(i) {

			var type=$("#debitCredit_"+i+" option:selected").attr("code");
			if(type == "DR"){
		    count++;
			}
			if(type == "CR"){
			countCr++;
			}
    	});
		
		var debitCredit = $("#debitCredit_0").val();
		if(debitCredit != "" && debitCredit !='0'){
		if(count == '0' ){
			errorList.push("Please select At least one Debit Type");
		}
		if(countCr == '0' ){
			errorList.push("Please select At least one Credit Type");
		}
		}
		
		//incrementvalue=++count;
		$('.tableRowClass').each(function(i) {
	 	var accountType_ = $('#accountType_'+i).val();
	 	var debitCredit_ = $("#debitCredit_"+i).val();
	 	var mode_ = $('#mode_'+i).val();
	 	
	 	if (accountType_ == '' || accountType_ == '0') {
	 		errorList.push(getLocalMessage('account.select.accType'));
	 	}
	 	 if(debitCredit_ == '' || debitCredit_ == '0'){
	 	 errorList.push(getLocalMessage('account.bank.select.card'));
	 	}
	 	if(mode_ == '' || mode_ == '0'){
		 	 errorList.push(getLocalMessage('contra.select.mode'));
		}
		});
		//mode value validation
	 	var status = $('#status').val();
	 	if (status == '' || status == '0') {
	 		errorList.push("Please select Status");
	 	}
		
		 if(errorList.length>0){
				
				displayErrorsOnPage(errorList);
				return false;
				
			    	var errorMsg = '<ul>';
			    	$.each(errorList, function(index){
			    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			    	});
			    	errorMsg +='</ul>';
			    	$('#errorId').html(errorMsg);
			    	$('#errorDivId').show();
					$('html,body').animate({ scrollTop: 0 }, 'slow');	   	
			   }
		 
		 if(errorList.length == 0){
		 var requestData = $('#VoucherTemplateCreate').serialize();
	 		var response= __doAjaxRequestValidationAccor(obj,formURL+'?saveForm', 'POST', requestData, false, 'json');
	 	    if(response != false){
	 	    	var url = 'VoucherTemplate.html';
				displayMessageOnSubmit(response, url);
	 	    }
		 }  
	 }
	 
