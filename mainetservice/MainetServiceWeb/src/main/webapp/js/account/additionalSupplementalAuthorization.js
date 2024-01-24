
$("#tbAcAdditionalSuppAuthorization").validate({
	
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

	$("#grid")
			.jqGrid(
					{
						url : "AuthorizationAdditionalSupplemental.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
								'',
								getLocalMessage('account.budgetadditionalsupplemental.transactionnumber'),
								getLocalMessage('account.budgetadditionalsupplemental.budgethead'),
								getLocalMessage('account.budgetadditionalsupplemental.budgetprovision(indianrupees)'),
								getLocalMessage('account.budgetadditionalsupplemental.tranferprovision(indianrupees)'),
								getLocalMessage('account.budgetadditionalsupplemental.finalprovision(indianrupees)'),
								getLocalMessage('account.budgetadditionalsupplemental.status'),
								getLocalMessage('bill.action')],
						colModel : [ {
							name : "paAdjid",
							width : 30,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							},
							hidden : true
						}, {
							name : "paAdjid",
							width : 20,
							sortable : true,
							editable : true,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						},{
							name : "prBudgetCode",
							width : 45,
							sortable : true,
							editable : true,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {
							name : "formattedCurrency",
							width : 25,
							sortable : true,
							classes : 'text-right',
							editable : true,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {
							name : "transAmountAddSupBug",
							width : 25,
							sortable : true,
							classes : 'text-right',
							editable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {
							name : "finalAmountAddSupBug",
							width : 25,
							sortable : true,
							classes : 'text-right',
							editable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						},{
							name : "status",
							width : 25,
							sortable : true,
							align : 'center',
							editable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {name: 'paAdjid', index: 'paAdjid', width:20 , align: 'center !important',formatter:addLink,search :false} ],
						pager : "#pagered",
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "paAdjid",
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
						caption : getLocalMessage('account.budgetadditionalsupplemental.additionalsupplementalauthorizationlist'),

						formatter : function(v) {
							// uses "c" for currency formatter and "n" for
							// numbers
							return Globalize.format(Number(v), "c");
						},
						unformat : function(v) {
							return Globalize.parseFloat(v);
						}

					});
	jQuery("#grid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		search : true,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewBugReappBalMasterClass' title='View'value='"+rowdata.paAdjid+"' paAdjid='"+rowdata.paAdjid+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editBugReappBalMasterClass' title='Edit'value='"+rowdata.paAdjid+"' paAdjid='"+rowdata.paAdjid+"' ><i class='fa fa-pencil'></i></a> ";
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

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

function closeOutErrBox() {
	$('.error-div').hide();
}

$(function() {
	$(document).on('click', '.createData', function() {
		var $link = $(this);
		/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
		var paAdjid = 1;
		var url = "AuthorizationAdditionalSupplemental.html?form";
		var requestData = "paAdjid=" + paAdjid + "&MODE_DATA=" + "ADD";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;
	});

	$(document).on('click', '.editBugReappBalMasterClass', function() {
		var errorList = [];
		var $link = $(this);

		var paAdjid = $link.closest('tr').find('td:eq(0)').text();
		var authStatus = $link.closest('tr').find('td:eq(6)').text();
		var url = "AuthorizationAdditionalSupplemental.html?update";
		var requestData = "paAdjid=" + paAdjid + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Approved"){
			$('.content').html(returnData);
		}
		else{
			errorList.push("It is already approved so EDIT is not allowed!");
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

	$(document).on('click', '.viewBugReappBalMasterClass', function() {
		var $link = $(this);
		var paAdjid = $link.closest('tr').find('td:eq(0)').text();
		var url = "AuthorizationAdditionalSupplemental.html?formForView";
		var requestData = "paAdjid=" + paAdjid + "&MODE_DATA=" + "VIEW";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr('disabled', true);
		$('input[type=checkbox]').attr('disabled', true);
		$('input[type="text"], textarea').attr('readonly','readonly');
		$('input[type="radio"], radiobutton').attr("disabled", true);
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

$(document).ready(function() {
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,	
		maxDate: '-0d',
	});
	$(".datepicker").datepicker('setDate', new Date()); 
	
	$('body').on('focus',".hasMyNumber", function(){
		$(".hasMyNumber").keyup(function(event) {
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength', '13');
	     });
		}); 
	

	if ($('#test').val() === 'EDIT') {
		var type = $('#cpdBugtypeIdHidden').val();
		if(type=="REV")
			{
			
			$("#prRevBudgetCode0").data('rule-required',true);
			$("#transferAmount0").data('rule-required',true);
			
			$("#prProjectionid").removeClass('hide');
			$('#faYearid').prop('disabled', 'disabled');
			}
		else
			{
			
			$("#prExpBudgetCode0").data('rule-required',true);
			$("#ExptransferAmount0").data('rule-required',true);
			
			$("#prExpenditureid").removeClass('hide'); 
			$('#faYearid').prop('disabled', 'disabled');
			}
	}

	if ($('#test').val() === 'VIEW') {
		var type = $('#cpdBugtypeIdHidden').val();
		
		if(type=="REV")
			{
			$("#prProjectionid").removeClass('hide');
			}
		else
			{
			$("#prExpenditureid").removeClass('hide'); 
			}
	}

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




function searchAdditionalSuppAuthorizationFormData() {
	$('.error-div').hide();
	var errorList = [];
	if(checkValidation())
	{
		if (errorList.length == 0) {
			
		fromDate = $("#fromDate, #fromDate1").val();
		toDate = $("#toDate").val();
		cpdBugtypeId = $("#cpdBugtypeId").val();
		status = $("#status option:selected").attr("code");
		var url = "AuthorizationAdditionalSupplemental.html?getjqGridSearchData";
		var requestData = {
				"fromDate" : fromDate,
				"toDate" : toDate,
				"cpdBugtypeId" : cpdBugtypeId,
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
  }
};


function setHiddenField(e) {
	$('#hiddenFinYear').val($('#faYearid').val());
}

function loadBudgetAdditionalSupplementalData(obj) {

	var prRevBudgetCode = $('#prRevBudgetCode0').val(""); 
	$("#prRevBudgetCode0").val('').trigger('chosen:updated');

	var orginalEstamt = $("#ExporginalEstamt0").val("");
	var ExptransferAmount = $("#ExptransferAmount0").val("");
	var newOrgExpAmount = $("#newOrgExpAmount0").val("");
	
	var orginalEstamtRev = $("#orginalEstamt0").val("");
	var transferAmount = $("#transferAmount0").val("");
	var newOrgRevAmount = $("#newOrgRevAmount0").val("");
	
	var prExpBudgetCode = $('#prExpBudgetCode0').val(""); 
	$("#prExpBudgetCode0").val('').trigger('chosen:updated');
	
	$('.error-div').hide();
	var errorList = [];

	$('#hiddenFinYear').val($('#faYearid').val());
	var cpdBugtypeId = $("#cpdBugtypeId").val();
	var faYearid = $('#faYearid').val();
	var dpDeptid = $('#dpDeptid').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var cpdBugSubTypeId=$('#cpdBugSubTypeId').val("");
	}
	
	if (dpDeptid == '') {
		errorList.push('Please select Deparment Type');
		var cpdBugSubTypeId=$('#cpdBugSubTypeId').val("");
	}
	if (cpdBugtypeId == "0") {
		errorList.push('Please Select Budget Type');
		var cpdBugSubTypeId=$('#cpdBugSubTypeId').val("");
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

		var url = "AuthorizationAdditionalSupplemental.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		var budgetType = $('#cpdBugtypeId').val();

		$('#faYearid').prop('disabled', 'disabled');

		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
		if (budgetType == "REV") {
			$("#prProjectionid").removeClass("hide");
			$("#prExpenditureid").addClass("hide");
		} else if (budgetType == "EXP") {
			$("#prProjectionid").addClass("hide");
			$("#prExpenditureid").removeClass("hide");
		}

	}
	
};

function setHiddenData(){
	 $('#secondaryId').val($('#sacHeadId${count}').val());
}

function saveLeveledData(obj) {
	var errorList = [];
	incrementvalue=++count;

	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	}else{
		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
	}
	if (budgetType === "REV") {
	
		var j = $("#indexdata").val();
		for(n=0; n<j;n++){	
		
		if($('#prRevBudgetCode'+n).val() == $('#prRevBudgetCode'+j).val()){
			errorList.push("The Combination Budget Head already exists!");
		}
		}
	} 
	
	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	}else{
		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
	}
	if (budgetType === "EXP") {
	
		var j = $("#indexdata").val();
		for(n=0; n<j;n++){	
		
		if($('#prExpBudgetCode'+n).val() == $('#prExpBudgetCode'+j).val()){
			errorList.push("The Combination Budget Head already exists!");
		}
		}
		
	}
	    		var	formName =	findClosestElementId(obj, 'form');
	    	    var theForm	=	'#'+formName;
	    	    var requestData = __serializeForm(theForm);
	    	    var url	=	$(theForm).attr('action');
	    	   
	    	    var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
	    	    if(response != false){
	    	       $('.content').html(response);
	    	    }
	}



function setSecondaryCodeFinanceRev(obj)
{
	 
	var orginalEstamt = $("#orginalEstamtO").val("");
	var orginalEstamt1=$('#orginalEstamtOO').val("");
	
	 var faYearId = $("#faYearid").val();
	 var primaryCode=$('#pacHeadIdO').val();
	 
		$('#prRevBudgetCode').find('option:gt(0)').remove();
		
		if (primaryCode > 0) {
			var postdata = {"faYearid": faYearId,"pacHeadId" : primaryCode };
			
			var json = __doAjaxRequest('AuthorizationAdditionalSupplemental.html?sacHeadItemsList',
					'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#prRevBudgetCode').append(optionsAsString );
			
		}
}


function setSecondaryCodeFinanceExp(obj)
{
	 
	var orginalEstamtExp = $("#orginalEstamtExp").val("");
	var orginalEstamtExp1=$('#orginalEstamtExpO').val("");
	
	 var faYearId1 = $("#faYearid").val();
	 var primaryCode1=$('#pacHeadIdExp').val();
	 
		$('#prExpBudgetCode').find('option:gt(0)').remove();
		
		if (primaryCode1 > 0) {
			var postdata = {"faYearid": faYearId1,"pacHeadId" : primaryCode1 };
			
			var json = __doAjaxRequest('AuthorizationAdditionalSupplemental.html?sacHeadItemsExpList',
					'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#prExpBudgetCode').append(optionsAsString );
			
		}
}


function getOrgBalAmount(cnt) {

 $('.error-div').hide();
	var errorList = [];
$("#orginalEstamt"+cnt).val("");
$("#transferAmount"+cnt).val("");
$("#newOrgRevAmount"+cnt).val("");

var theForm = '#tbAcAdditionalSuppAuthorization';

var dpDeptid = $('#dpDeptid').val();
var cpdBugtypeId = $('#cpdBugtypeId').val();
var cpdBugSubTypeId = $('#cpdBugSubTypeId').val();

if (dpDeptid == '') {
	errorList.push('Please Select Department');
	var cpdBugSubTypeIdRev = $("#prRevBudgetCode"+cnt).val("");
	$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
}

if (cpdBugtypeId == '0') {
	errorList.push('Please Select Budget Type');
	var cpdBugSubTypeIdRev = $("#prRevBudgetCode"+cnt).val("");
	$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
}

if (cpdBugSubTypeId == '') {
	errorList.push('Please Select Budget Sub Type');
	var cpdBugSubTypeIdRev = $("#prRevBudgetCode"+cnt).val("");
	$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
}

var id =  $("#indexdata").val();

if(id == "" || id== undefined){
	id = 0;
}
var prRevBudgetCode = $('#prRevBudgetCode'+id).val();

for(m=0; m<=id;m++){
	if($('#prRevBudgetCode'+m).val() == ""){
		
		errorList.push("Please select Budget Head First!");
		var prRevBudgetCode = $("#prRevBudgetCode"+cnt).val("");
		$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
	}
}

 var Revid =  $("#indexdata").val();
	if(prRevBudgetCode != "" ){
		var dec;
		if(Revid == "" || Revid== undefined){
			Revid = 0;
		}
		
	 for(m=0; m<=Revid;m++){
		 for(dec=0; dec<=Revid;dec++){
			 if(m!=dec){
			if(($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+dec).val())){
				errorList.push("The Combination Budget Head already exists!");
				var pacHeadId = $("#prRevBudgetCode"+cnt).val("");
				$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
				var orginalEstamt = $("#orginalEstamt"+cnt).val("");
				var transferAmount = $("#transferAmount"+cnt).val("");
				var newOrgRevAmount = $("#newOrgRevAmount"+cnt).val("");				
				
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
 
var requestData = __serializeForm(theForm);

if (errorList.length == 0) {

	var url = "AuthorizationAdditionalSupplemental.html?getOrgBalGridload&cnt="+cnt;

	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$.each(returnData, function(key, value) {
		
		if (key == 'OAMT') {
			$("#orginalEstamt" + cnt).val(value);
		}
	});
}

if (errorList.length == 0) {

	var url = "AuthorizationAdditionalSupplemental.html?getReappDynamicRevPrimarykeyIdDetails&cnt="+cnt;

	var returnDataRevDynamic = __doAjaxRequest(url, 'post', requestData, false);
	$.each(returnDataRevDynamic, function(key, value) {
		//alert(returnDataRevDynamic+"value="+value);
		$("#prProjectionidRevDynamic"+ cnt).val(value);
		
	});

}
};

function getOrgBalExpAmount(cont) {
	$('.error-div').hide();
	var errorList = [];
$("#ExporginalEstamt"+cont).val("");
$("#Expamount"+cont).val("");
$("#ExptransferAmount"+cont).val("");
$("#newOrgExpAmount"+cont).val("");
var theForm = '#tbAcAdditionalSuppAuthorization';
var dpDeptid = $('#dpDeptid').val();
var cpdBugtypeId = $('#cpdBugtypeId').val();
var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

if (dpDeptid == '') {
	errorList.push('Please Select Department');
	var cpdBugsubtypeId = $("#prExpBudgetCode"+cont).val("");
	$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
}

if (cpdBugtypeId == '') {
	errorList.push('Please Select Budget Type');
	var cpdBugsubtypeId = $("#prExpBudgetCode"+cont).val("");
	$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
}

if (cpdBugsubtypeId == '') {
	errorList.push('Please Select Budget Sub Type');
	var cpdBugsubtypeId = $("#prExpBudgetCode"+cont).val("");
	$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
}

var id =  $("#indexdata").val();

if(id == "" || id== undefined){
	id = 0;
}
var prExpBudgetCode = $('#prExpBudgetCode'+id).val();

for(m=0; m<=id;m++){
	if($('#prExpBudgetCode'+m).val() == ""){
		
		errorList.push("Please select Budget Head First!");
		var prRevBudgetCode = $("#prExpBudgetCode"+cont).val("");
		$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
	}
}

 var Expid =  $("#indexdata").val();
	if(prExpBudgetCode != "" ){
		var dec;
		if(Expid == "" || Expid== undefined){
			Expid = 0;
		}
		
	 for(m=0; m<=Expid;m++){
		 for(dec=0; dec<=Expid;dec++){
			 if(m!=dec){
			if(($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+dec).val())){
				errorList.push("The Combination Budget Head already exists!");
				var pacHeadId = $("#prExpBudgetCode"+cont).val("");
				$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
				var orginalEstamt = $("#ExporginalEstamt"+cont).val("");
				var ExptransferAmount = $("#ExptransferAmount"+cont).val("");
				var newOrgExpAmount = $("#newOrgExpAmount"+cont).val("");				
				
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
 
var requestData = __serializeForm(theForm);

if (errorList.length == 0) {

	var url = "AuthorizationAdditionalSupplemental.html?getOrgBalExpGridload&cont="+cont;

	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$.each(returnData, function(key, value) {
		
		var prExpBudgetCode = $('#prExpBudgetCode'+cont).val();
		if( prExpBudgetCode != "" ){
		
		if (key == 'OEAMT') {
			$("#ExporginalEstamt"+cont).val(value);
		}
		}

	});

}

if (errorList.length == 0) {

	var url = "AuthorizationAdditionalSupplemental.html?getExpPrimarykeyIdDetails&cont="+cont;

	var returnDataRevDynamic = __doAjaxRequest(url, 'post', requestData, false);
	$.each(returnDataRevDynamic, function(key, value) {
		
		$("#prExpenditureidExpDynamic"+cont).val(value);
		
	});

}
};

function clearAmount(obj){
	
	var prRevBudgetCode = $("#prRevBudgetCode").val("");
	var orginalEstamt = $("#orginalEstamtO").val("");
	var prProjectedRev=$('#prProjectedRev').val("");
	var transferAmount=$('#transferAmount').val("");
	var newOrgRevAmount=$('#newOrgRevAmount').val("");
	var prRevBudgetCode=$('#prRevBudgetCode').val("");
}

function clearAmountExp(obj){
	
	var prExpBudgetCode = $("#prExpBudgetCode").val("");
	var orginalEstamt = $("#orginalEstamtExp").val("");
	var prProjectedRev=$('#prBalanceAmtAS').val("");
	var transferAmount=$('#transferAmountO').val("");
	var newOrgRevAmount=$('#newOrgExpAmountO').val("");
	var prRevBudgetCode=$('#prExpBudgetCode').val("");
}

function RemoveTranferAmount(obj){
	
	var transferAmount = $('#transferAmount').val("");
	var transferAmountO= $('#transferAmountO').val("");
	var newOrgRevAmount = $('#newOrgRevAmount').val("");
	var newOrgExpAmountO= $('#newOrgExpAmountO').val("");
	
}

function gettingPacHeadCodebugtypeandbugsuntype(obj)
{
	
	var pacHeadIdO = $('#pacHeadIdO').val("");
	var pacHeadIdExp = $('#pacHeadIdExp').val("");
	
	if ($('#faYearid').val() == "") {
		msgBox('Please Select Financial Year');
		return false;
	}

	if ($('#cpdBugtypeId').val() == "") {
		msgBox('Please Select Budget Type');
		return false;
	}

	 var faYearId = $("#faYearid").val();
	 var cpdBugtypeId=$('#cpdBugtypeId').val();
	 var cpdBugSubTypeId=$('#cpdBugSubTypeId').val();
	 var dpDeptid=$('#dpDeptid').val();
	 
		$('#pacHeadIdO').find('option:gt(0)').remove();
		$('#pacHeadIdExp').find('option:gt(0)').remove();
		
		if (cpdBugSubTypeId > 0) {
			var postdata = {"faYearid": faYearId,"cpdBugtypeId": cpdBugtypeId,"cpdBugSubTypeId" : cpdBugSubTypeId,"dpDeptid" : dpDeptid };
			
			var json = __doAjaxRequest('AuthorizationAdditionalSupplemental.html?gettingPacHeadCodebugtypeandbugsuntypeRev',
					'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#pacHeadIdO').append(optionsAsString );
			
		}
		
		if (cpdBugSubTypeId > 0) {
			var postdata = {"faYearid": faYearId,"cpdBugtypeId": cpdBugtypeId,"cpdBugSubTypeId" : cpdBugSubTypeId,"dpDeptid" : dpDeptid };
			
			var json1 = __doAjaxRequest('AuthorizationAdditionalSupplemental.html?gettingPacHeadCodebugtypeandbugsuntypeExp',
					'POST', postdata, false, 'json');
			var  optionsAsString1='';
			
			$.each( json1, function( key, value ) {
				optionsAsString1 += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#pacHeadIdExp').append(optionsAsString1 );
			
		}
}

function ClearBudgetSubType(obj){
	
	var cpdBugSubTypeId = $("#cpdBugSubTypeId").val("");
	
}

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
	window.location.href='AuthorizationAdditionalSupplemental.html';
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
$("#prProjectionid")
		.on("click",'.addButton',
				function(e){
var errorList = [];

   $('.appendableClass').each(function(i) {
		  
		 var prRevBudgetCode = $.trim($("#prRevBudgetCode"+i).val());
		 if(prRevBudgetCode==0 || prRevBudgetCode=="") 
		 errorList.push("Please select Budget Head");
		
		 var transferAmount = $.trim($("#transferAmount"+i).val());
		 if(transferAmount==0 || transferAmount=="")
		 errorList.push("Please Enter Additional Provision");
		 
		if(prRevBudgetCode != ""){
		 for(m=0; m<i;m++){
				if($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+i).val()){
					
					errorList.push("Budget Head Already Selected!");
				}
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
			$(".datepick").datepicker("destroy");
			var content = $(this).closest('#revTable tr').clone();
			$(this).closest("#revTable").append(content);
			content.find("select").attr("value", "");
			content.find("input:text").val("");
			content.find("select").val("");
			content.find('div.chosen-container').remove();
			content.find("select").chosen().trigger("chosen:updated");
			
			reOrderTableIdSequence();

		});

//to delete row
$("#prProjectionid").on("click", '.delButton', function(e) {

var rowCount = $('#revTable tr').length;
if (rowCount <= 2) {

var msg = "can not remove";
showErrormsgboxTitle(msg);
return false;
}

$(this).closest('#revTable tr').remove();

reOrderTableIdSequence();
e.preventDefault();
});

function reOrderTableIdSequence() {
$('.appendableClass').each(
	function(i) {
		$(".datepick").datepicker("destroy");
		$(this).find("select:eq(0)").attr("id", "prRevBudgetCode" + i);
		$(this).find("input:text:eq(0)").attr("id", "prRevBudgetCode" + i);
		$(this).find("input:text:eq(1)").attr("id", "orginalEstamt" + i);
		$(this).find("input:text:eq(2)").attr("id", "transferAmount" + i);
		$(this).find("input:text:eq(3)").attr("id","newOrgRevAmount" + i);
		$(this).find("input:hidden:eq(0)").attr("id","prProjectionidRevDynamic"+i);
		$(this).find("select:eq(0)").attr("name",
				"bugprojRevBeanList[" + i + "].prRevBudgetCode");
		$(this).find("input:text:eq(0)").attr("name",
				"bugprojRevBeanList[" + i + "].prRevBudgetCode");
		$(this).find("input:text:eq(1)").attr("name",
				"bugprojRevBeanList[" + i + "].orginalEstamt");
		$(this).find("input:text:eq(2)").attr("name",
				"bugprojRevBeanList[" + i + "].transferAmount");
		$(this).find("input:text:eq(3)").attr("name",
				"bugprojRevBeanList[" + i + "].newOrgRevAmount");
		
		$(this).find("input:hidden:eq(0)").attr("name","bugprojRevBeanList["+i+"].prProjectionidRevDynamic");
		
		$(this).parents('tr').find('.delButton').attr("id",
				"delButton" + i);
		$(this).parents('tr').find('.addButton').attr("id",
				"addButton" + i);
	    $(this).find('#prRevBudgetCode'+i).attr("onchange",
				"getOrgBalAmount(" + (i) + ")");
		$(this).closest("tr").attr("id", "bugestIdRev" + (i));
		
		$("#indexdata").val(i);
	
	});
}

//to generate dynamic table
$("#prExpenditureid")
		.on("click",'.addButtonExp',
				function(e){
var errorList = [];

   $('.ExpappendableClass').each(function(i) {
	   
	     var prExpBudgetCode = $.trim($("#prExpBudgetCode"+i).val());
		 if(prExpBudgetCode==0 || prExpBudgetCode=="") 
		 errorList.push("Please select Budget Head");
		
		 var ExptransferAmount = $.trim($("#ExptransferAmount"+i).val());
		 if(ExptransferAmount==0 || ExptransferAmount=="")
		 errorList.push("Please Enter Additional Provision");
  
		 for(m=0; m<i;m++){
				if($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+i).val()){
					
					errorList.push("The Combination Budget Head already exists!");
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
			$(".datepic").datepicker("destroy");
			
			var content = $(this).closest('#expTable tr').clone();
			$(this).closest("#expTable").append(content);
			// reset values
			content.find("select").attr("value", "");
			content.find("input:text").val("");
			content.find("select").val("");
			
			content.find('div.chosen-container').remove();
			content.find("select").chosen().trigger("chosen:updated");
		
			expreOrderTableIdSequence();

		});

//to delete row
$("#prExpenditureid").on("click", '.delButtonExp', function(e) {

var rowCount = $('#expTable tr').length;
if (rowCount <= 2) {
var msg = "can not remove";
showErrormsgboxTitle(msg);
return false;
}

$(this).closest('#expTable tr').remove();

expreOrderTableIdSequence();
e.preventDefault();
});

function expreOrderTableIdSequence() {
$('.ExpappendableClass').each(
	function(i) {
		
		$(".datepic").datepicker("destroy");
		$(this).find("select:eq(0)").attr("id", "prExpBudgetCode" + i);
		$(this).find("input:text:eq(0)").attr("id", "prExpBudgetCode" + i);
		$(this).find("input:text:eq(1)").attr("id", "ExporginalEstamt" + i);
		$(this).find("input:text:eq(2)").attr("id", "ExptransferAmount" + i);
		$(this).find("input:text:eq(3)").attr("id", "newOrgExpAmount" + i);
		$(this).find("input:hidden:eq(0)").attr("id","prExpenditureidExpDynamic"+i);
		
		$(this).find("select:eq(0)").attr("name",
				"bugprojExpBeanList[" + i + "].prExpBudgetCode");
		$(this).find("input:text:eq(0)").attr("name",
				"bugprojExpBeanList[" + i + "].prExpBudgetCode");
		$(this).find("input:text:eq(1)").attr("name",
				"bugprojExpBeanList[" + i + "].orginalEstamt");
		$(this).find("input:text:eq(2)").attr("name",
				"bugprojExpBeanList[" + i + "].transferAmountO");
		$(this).find("input:text:eq(3)").attr("name",
				"bugprojExpBeanList[" + i + "].newOrgExpAmountO");
		$(this).find("input:hidden:eq(0)").attr("name","bugprojExpBeanList["+i+"].prExpenditureidExpDynamic");
		$(this).parents('tr').find('.delButtonExp').attr("id",
				"delButtonExp" + i);
		$(this).parents('tr').find('.addButtonExp').attr("id",
				"addButtonExp" + i);
	      $(this).find('#prExpBudgetCode'+i).attr("onchange",
				"getOrgBalExpAmount(" + (i) + ")");
		$(this).closest("tr").attr("id", "bugestIdExp" + (i));
		$("#indexdata").val(i);
	
	});
}

function copyContent(obj) {

	var id=  $(obj).attr('id');
	var arr = id.split('transferAmount');
	var indx1=arr[1];

	var newOrgRevAmount = $('#newOrgRevAmount'+indx1).val("");
	
	var stt = 0;
	var parentCode = 0;
	stt = parseFloat($('#orginalEstamt'+indx1).val());
	parentCode = parseFloat($('#transferAmount'+indx1).val());
	if ((stt != undefined && !isNaN(stt))
			&& (parentCode != undefined && !isNaN(parentCode))) {
		var num = (stt+parentCode);
		var result = num.toFixed(2);
		$("#newOrgRevAmount"+indx1).val(result);
	}	
}

function copyExpContent(obj) {

	var id=  $(obj).attr('id');
	var arr = id.split('ExptransferAmount');
	var indx1=arr[1];

	var newOrgExpAmount = $('#newOrgExpAmount'+indx1).val("");
	
	var stt = 0;
	var parentCode = 0;
	stt = parseFloat($('#ExporginalEstamt'+indx1).val());
	parentCode = parseFloat($('#ExptransferAmount'+indx1).val());
	if ((stt != undefined && !isNaN(stt))
			&& (parentCode != undefined && !isNaN(parentCode))) {
		var num = (stt+parentCode);
		var result = num.toFixed(2);
		$("#newOrgExpAmount"+indx1).val(result);
	}
}


function clearAllData(obj) {
	var budgetSubType = $('#cpdBugSubTypeId').val("");
	var prRevBudgetCode = $('#prRevBudgetCode0').val(""); 
	$("#prRevBudgetCode0").val('').trigger('chosen:updated');

	var orginalEstamt = $("#ExporginalEstamt0").val("");
	var ExptransferAmount = $("#ExptransferAmount0").val("");
	var newOrgExpAmount = $("#newOrgExpAmount0").val("");
	
	var orginalEstamtRev = $("#orginalEstamt0").val("");
	var transferAmount = $("#transferAmount0").val("");
	var newOrgRevAmount = $("#newOrgRevAmount0").val("");
	
	var prExpBudgetCode = $('#prExpBudgetCode0').val(""); 
	$("#prExpBudgetCode0").val('').trigger('chosen:updated');
	
	$('.error-div').hide();
	var errorList = [];
	
	if (errorList.length == 0) {
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AuthorizationAdditionalSupplemental.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		var budgetSubType = $('#cpdBugSubTypeId').val();

		$('#faYearid').prop('disabled', 'disabled');

		var budgetSubType=$("#cpdBugSubTypeId option:selected").attr("code");
		if (budgetSubType === "RV") {
			$("#prProjectionid").removeClass("hide");
			$("#prExpenditureid").addClass("hide");
		} else if (budgetSubType === "CP") {
			$("#prProjectionid").addClass("hide");
			$("#prExpenditureid").removeClass("hide");
		}
	}	
}

function checkValidation()
{
	
	var errorList=[];
	
		 if($("#fromDate").val()=="" && $("#toDate").val()=="")
			 {
			 errorList.push("Please Select From Date and To Date");
			 }
		 else if($("#fromDate").val()!="" && $("#toDate").val()=="")
			 {
			 errorList.push("Please Select To Date"); 
			 }
		 else if($("#fromDate").val()=="" && $("#toDate").val()!="")
			 {
			 errorList.push("Please Select From Date"); 
			 }
		  
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		  if (eDate > sDate) {
			  errorList.push("From Date can not be less than To Date");
		        
		    }
		  if($("#fromDate").val()!=null)
			  {
			  errorList = validatedate(errorList,'fromDate');
			  }
            if($("#toDate").val()!=null)
            	{
            	errorList = validatedate(errorList,'toDate');
            	}
	if(errorList.length>0)
		{
	 var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
     errMsg += '</ul>';
      $('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
		}
	return true;
}


