
$("#tbAcBudgetoryRevision").validate({
	
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
						url : "AccountBudgetoryRevision.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
								'',
								getLocalMessage('account.budgetaryrevision.budgethead'),
								getLocalMessage('account.budgetaryrevision.originalestimate(indianrupees)'),
								getLocalMessage('account.budgetaryrevision.revisedamount(indianrupees)'),
								getLocalMessage('bill.action')],
						colModel : [ {
							name : "bugrevId",
							width : 30,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							},
							hidden : true
						}, {
							name : "prBudgetCode",
							width : 55,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {
							name : "orginalEstamt",
							width : 45,
							sortable : true,
							classes : 'text-right',
							editable : true,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {
							name : "revisedAmountDup",
							width : 45,
							sortable : true,
							classes : 'text-right',
							editable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {name: 'bugrevId', index: 'bugrevId', width:20 , align: 'center !important',formatter:addLink,search :false}],
						pager : "#pagered",
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "bugrevId",
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
						caption : getLocalMessage('account.budgetaryrevision.accountbudgetaryrevisionlist'),

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
   return "<a class='btn btn-blue-3 btn-sm viewBugEstPrepareMasterClass' title='View'value='"+rowdata.bugrevId+"' bugrevId='"+rowdata.bugrevId+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editBugEstPrepareMasterClass' title='Edit'value='"+rowdata.bugrevId+"' bugrevId='"+rowdata.bugrevId+"' ><i class='fa fa-pencil'></i></a> ";
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
		var bugrevId = 1;
		var url = "AccountBudgetoryRevision.html?form";
		var requestData = "bugrevId=" + bugrevId + "&MODE_DATA=" + "ADD";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;
	});

	$(document).on('click', '.editBugEstPrepareMasterClass', function() {
		var $link = $(this);
		var bugrevId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountBudgetoryRevision.html?formforupdate";
		var requestData = "bugrevId=" + bugrevId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		if ($('#test').val() === 'EDIT') {
			var type = $('#cpdBugtypeIdHidden').val();
			if(type=="REV")
				{
				$("#prProjectionid").removeClass('hide');
				$('#faYearid').prop('disabled', 'disabled');
				}
			else
				{
				$("#prExpenditureid").removeClass('hide'); 
				$('#faYearid').prop('disabled', 'disabled');
				}
		}
		return false;
	});

	$(document).on('click', '.viewBugEstPrepareMasterClass', function() {
		var $link = $(this);
		var bugrevId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountBudgetoryRevision.html?update";
		var requestData = "bugrevId=" + bugrevId + "&MODE_DATA=" + "VIEW";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr('readonly','readonly');
		if ($('#test').val() === 'VIEW') {
			var type = $('#cpdBugtypeIdHidden').val();
			if(type=="REV")
				{
				$("#prProjectionid").removeClass('hide');
				$('#faYearid').prop('disabled', 'disabled');
				$('select').prop('disabled', true).trigger("chosen:updated");
				}
			else
				{
				$("#prExpenditureid").removeClass('hide'); 
				$('#faYearid').prop('disabled', 'disabled');
				$('select').prop('disabled', true).trigger("chosen:updated");
				}
		}
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
	
	$('body').on('focus',".hasNumber", function(){
		$(".hasNumber").keyup(function(event) {
	    this.value = this.value.replace(/[^0-3]/g,'');
	    $(this).attr('maxlength', '1');
	     });
		}); 
	
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
			$("#revisedAmount0").data('rule-required',true);
			
			$("#prProjectionid").removeClass('hide');
			$('#faYearid').prop('disabled', 'disabled');
			}
		else
			{
			
			$("#prExpBudgetCode0").data('rule-required',true);
			$("#revisedAmountExp0").data('rule-required',true);
			
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


function searchBudgetoryRevisionData() {

	$('.error-div').hide();

	var errorList = [];

		var faYearid = $("#faYearid").val();
		var cpdBugtypeId = $("#cpdBugtypeId").val();
		var dpDeptid = $("#dpDeptid").val();
		var prBudgetCodeid = $("#prBudgetCodeid").val();
		var fundId = $("#fundId").val();
		var functionId = $("#functionId").val();
		
		if (faYearid == "" || faYearid =="0") {
			errorList.push('Kindly select financial Year');
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
			
		var url = "AccountBudgetoryRevision.html?getjqGridsearch";
		var requestData = {
			"faYearid" : faYearid,
			"cpdBugtypeId" : cpdBugtypeId,
			"dpDeptid" : dpDeptid,
			"prBudgetCodeid" : prBudgetCodeid,
			"fundId" : fundId,
			"functionId" : functionId
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


function copyContent(obj) {

	var amount = 0;

	var stt = 0;
	var parentCode = 0;

	stt = parseInt($('#orginalEstamtO').val());
	parentCode = parseInt($('#transferAmount').val());
	$('#newOrgRevAmount').val(parentCode + stt);
}


function setHiddenField(e) {
	$('#hiddenFinYear').val($('#faYearid').val());
}

function saveLeveledData(obj) {
	var errorList = [];
	incrementvalue=++count;
	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	}else{
		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
	};
	if (budgetType === "REV") {
		
		var j = $("#indexdata").val();
		for(n=0; n<j;n++){	
		
		if($('#prRevBudgetCode'+n).val() == $('#prRevBudgetCode'+j).val()){
			errorList.push("The Combination code already exists, Please select another combination code again!");
		}
		}
	}
	
	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	}else{
		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
	};
	if (budgetType === "EXP") {
		
		var j =  $("#indexdata").val();
		for(n=0; n<j;n++){	
		if($('#prExpBudgetCode'+n).val() == $('#prExpBudgetCode'+j).val()){
			errorList.push("The Combination code already exists, Please select another combination code again!");
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

function loadBudgetoryRevisionData(obj) {
	$('.error-div').hide();
	var errorList = [];
	$('#hiddenFinYear').val($('#faYearid').val());
	var orginalEstamt = $("#orginalEstamt0").val("");
	var actualTillNovAmount = $("#actualTillNovAmount0").val("");
	var budgetedFromDecAmount = $("#budgetedFromDecAmount0").val("");
	var revisedAmount = $("#revisedAmount0").val("");
	$("#prRevBudgetCode0").val("");
	$("#prExpBudgetCode0").val("");
	var orginalEstamtExp = $("#orginalEstamtExp0").val("");
	var actualTillNovAmountExp = $("#actualTillNovAmountExp0").val("");
	var budgetedFromDecAmountExp = $("#budgetedFromDecAmountExp0").val("");
	var revisedAmountExp = $("#revisedAmountExp0").val("");
	var faYearid = $('#faYearid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	if (faYearid == '') {
		var cpdBugtypeId = $('#cpdBugtypeId').val(0);
	}
	if (cpdBugtypeId == '0') {
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

		var url = "AccountBudgetoryRevision.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);
		
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		var budgetType = $('#cpdBugtypeId').val();

		$('#faYearid').prop('disabled', 'disabled');

		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
		if (budgetType == "REV") {
		
			$("#prRevBudgetCode0").data('rule-required',true);
			$("#revisedAmount0").data('rule-required',true);
			
			$("#prProjectionid").removeClass("hide");
			$("#prExpenditureid").addClass("hide");
		} else if (budgetType == "EXP") {
			
			$("#prExpBudgetCode0").data('rule-required',true);
			$("#revisedAmountExp0").data('rule-required',true);
			
			$("#prProjectionid").addClass("hide");
			$("#prExpenditureid").removeClass("hide");
		}

	}
	
};

function copyContentExp(obj) {
	var amount = 0;
	var stt1 = 0;
	var parentCode1 = 0;
	stt1 = parseInt($('#orginalEstamtExp').val());
	parentCode1 = parseInt($('#transferAmountO').val());
	$('#newOrgExpAmountO').val(parentCode1 + stt1);

}

function getOrgBalExpAmount(obj) {
	if ($('#count').val() == "") {
		count = 1;
	} else {
		count = parseInt($('#count').val());
	}
	var assign = count;

	$('.error-div').hide();
	var errorList = [];

	if (errorList.length == 0) {


		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetoryRevision.html?getOrgBalExpGridload";

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(index , value) {
			
			$('#orginalEstamtExp').val(returnData[0]);
			$('#prBalanceAmtAS').val(returnData[1]);
			
		});

	}
	
	if (errorList.length == 0) {

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetoryRevision.html?getaddsupExpPrimarykeyIdDetails";

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			$('#prExpenditureidExp').val(value);
			
		});

	}
};

function clearAmount(obj){
	
	var orginalEstamt = $("#orginalEstamtO").val("");
	var prProjectedRev=$('#prProjectedRev').val("");
	var transferAmount=$('#transferAmount').val("");
	var newOrgRevAmount=$('#newOrgRevAmount').val("");
	var prRevBudgetCodeO=$('#prRevBudgetCodeO').val("");
}

function clearAmountExp(obj){
	
	var orginalEstamt = $("#orginalEstamtExp").val("");
	var prProjectedRev=$('#prBalanceAmtAS').val("");
	var transferAmount=$('#transferAmountO').val("");
	var newOrgRevAmount=$('#newOrgExpAmountO').val("");
	var prRevBudgetCodeO=$('#prExpBudgetCode').val("");
}


function enteredAmountDynamicallyGeneratedCalcAmount(obj){
	
	var idRev=$('#index').val(); 
	var id=  $(obj).attr('id');
	var arr = id.split('revisedAmount');
	var indx=arr[1];
	var stt = 0;
	var parentCode = 0;
	var minAmt = ($('#actualTillNovAmount'+indx).val());
	var maxAmt = ($('#revisedAmount'+indx).val());
	  if ((minAmt != '') && (maxAmt != '')){
	    try{
	      maxAmt = parseInt(maxAmt);
	      minAmt = parseInt(minAmt);

	      if(maxAmt < minAmt) {
	        var msg = "The Revised benefit amount must be less than the actual Till Nov Amount.!";
	    	  showErrormsgboxTitle(msg);
	        $('#revisedAmount'+indx).val("");
	        return false;
	      }
	    }catch(e){
	      return false;
	    }
	  } //end maxAmt minAmt comparison  
	  
	  var revisedAmount = ($('#revisedAmount'+indx).val());
	  if (revisedAmount != undefined && !isNaN(revisedAmount) && revisedAmount != null && revisedAmount != '') {
			
			var actualAmt = revisedAmount.toString().split(".")[0];
			var decimalAmt = revisedAmount.toString().split(".")[1];
			
			var decimalPart =".00";
			if(decimalAmt == null || decimalAmt == undefined){
				$('#revisedAmount'+indx).val(actualAmt+decimalPart);
			}else{
				if(decimalAmt.length <= 0){
					decimalAmt+="00";
					$('#revisedAmount'+indx).val(actualAmt+(".")+decimalAmt);
				}
				else if(decimalAmt.length <= 1){
					decimalAmt+="0";
					$('#revisedAmount'+indx).val(actualAmt+(".")+decimalAmt);
				}else{
					if(decimalAmt.length <= 2){
					$('#revisedAmount'+indx).val(actualAmt+(".")+decimalAmt);
					} 
				}	
			}
		}
}

function enteredAmountDynamicallyGeneratedCalcALLAmount(obj){
	
	var idExp=$('#index').val(); 
	var id=  $(obj).attr('id');
	var arr = id.split('revisedAmountExp');
	var indx=arr[1];
	var sttExp = 0;
	var parentCodeExp = 0;
	var minAmt = ($('#actualTillNovAmountExp'+indx).val());
	var maxAmt = ($('#revisedAmountExp'+indx).val());
	  if ((minAmt != '') && (maxAmt != '')){
	    try{
	      maxAmt = parseInt(maxAmt);
	      minAmt = parseInt(minAmt);

	      if(maxAmt < minAmt) {
	        var msg = "The Revised benefit amount must be Less than the actual Till Nov Amount.!";
	    	  showErrormsgboxTitle(msg);
	        $('#revisedAmountExp'+indx).val("");
	        return false;
	      }
	    }catch(e){
	      return false;
	    }
	  } //end maxAmt minAmt comparison  
	  
	  var revisedAmountExp = ($('#revisedAmountExp'+indx).val());
	  if (revisedAmountExp != undefined && !isNaN(revisedAmountExp) && revisedAmountExp != null && revisedAmountExp != '') {
			
			var actualAmt = revisedAmountExp.toString().split(".")[0];
			var decimalAmt = revisedAmountExp.toString().split(".")[1];
			
			var decimalPart =".00";
			if(decimalAmt == null || decimalAmt == undefined){
				$('#revisedAmountExp'+indx).val(actualAmt+decimalPart);
			}else{
				if(decimalAmt.length <= 0){
					decimalAmt+="00";
					$('#revisedAmountExp'+indx).val(actualAmt+(".")+decimalAmt);
				}
				else if(decimalAmt.length <= 1){
					decimalAmt+="0";
					$('#revisedAmountExp'+indx).val(actualAmt+(".")+decimalAmt);
				}else{
					if(decimalAmt.length <= 2){
					$('#revisedAmountExp'+indx).val(actualAmt+(".")+decimalAmt);
					} 
				}	
			}
		}
}

function enteredAmountDynamicallyGeneratedCalcEditALLAmount(obj){
	
	var idExp=$('#index').val(); 
	var id=  $(obj).attr('id');
	var arr = id.split('revisedAmountRevisionExp');
	var indx=arr[1];
	var sttExp = 0;
	var parentCodeExp = 0;
	var minAmt = ($('#actualTillNovAmountRevisionExp'+indx).val());
	var maxAmt = ($('#revisedAmountRevisionExp'+indx).val());
	  if ((minAmt != '') && (maxAmt != '')){
	    try{
	      maxAmt = parseInt(maxAmt);
	      minAmt = parseInt(minAmt);

	      if(maxAmt < minAmt) {
	        var msg = "The Revised benefit amount must be Less than the actual Till Nov Amount.!";
	    	  showErrormsgboxTitle(msg);
	        $('#revisedAmountRevisionExp'+indx).val("");
	        return false;
	      }
	    }catch(e){
	      return false;
	    }
	  } //end maxAmt minAmt comparison  
}

function clearBudgetSubType(obj){
	var idRev=$('#index').val(); 
	var prRevBudgetCode =  $("#prRevBudgetCode"+idRev).val("");
	$("#prRevBudgetCode"+idRev).val('').trigger('chosen:updated');
	var prExpBudgetCode =  $("#prExpBudgetCode"+idRev).val("");
	var revisedAmount =  $("#revisedAmount"+idRev).val("");
	var revisedAmountExp =  $("#revisedAmountExp"+idRev).val("");
	var orginalEstamt =  $("#orginalEstamt"+idRev).val("");
	var actualTillNovAmount =  $("#actualTillNovAmount"+idRev).val("");
	var budgetedFromDecAmount =  $("#budgetedFromDecAmount"+idRev).val("");
	var revisedAmount =  $("#revisedAmount"+idRev).val("");
	
}

function clearExpBudgetSubType(obj){
	
	var idExp=$('#index').val(); 
	var prExpBudgetCode =  $("#prExpBudgetCode"+idExp).val("");
	$("#prExpBudgetCode"+idExp).val('').trigger('chosen:updated');
	var revisedAmountExp =  $("#revisedAmountExp"+idExp).val("");
	var orginalEstamtExp =  $("#orginalEstamtExp"+idExp).val("");
	var actualTillNovAmountExp =  $("#actualTillNovAmountExp"+idExp).val("");
	var budgetedFromDecAmountExp =  $("#budgetedFromDecAmountExp"+idExp).val("");
	var revisedAmountExp =  $("#revisedAmountExp"+idRev).val("");
}


function getOrgBalAmount(cnt) {
	$('.error-div').hide();
	var errorList = [];
	$("#orginalEstamt"+cnt).val("");
	$("#actualTillNovAmount"+cnt).val("");
	$("#budgetedFromDecAmount"+cnt).val("");
	$("#revisedAmount"+cnt).val("");
	var theForm = '#tbAcBudgetoryRevision';
	var dpDeptid = $('#dpDeptid').val();
	if (dpDeptid == '') {
		errorList.push('Please Select Department');
		var cpdBugsubtypeIdRev = $("#prRevBudgetCode"+cnt).val("");
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
			var pacHeadId = $("#prRevBudgetCode"+cnt).val("");
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
					var actualTillNovAmount = $("#actualTillNovAmount"+cnt).val("");
					var budgetedFromDecAmount = $("#budgetedFromDecAmount"+cnt).val("");
					var revisedAmount = $("#revisedAmount"+cnt).val("");					
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
		 
		var url = "AccountBudgetoryRevision.html?getOrgBalRevDuplicateGridloadData&cnt="+cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		 var errorList= [];	
		 if(returnData){
			errorList.push("Budget Revised Combination Budget Head is Already Exist!"); 
			var pacHeadId = $("#prRevBudgetCode"+cnt).val("");
			$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
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

		var url = "AccountBudgetoryRevision.html?getOrgBalGridload&cnt="+cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			var prRevBudgetCode = $('#prRevBudgetCode'+cnt).val();
			if( prRevBudgetCode != "" ){
			if (key == 'OEA') {
				$("#orginalEstamt" + cnt).val(value);
			}
			}
		});
	}
	
	if (errorList.length == 0) {
		var url = "AccountBudgetoryRevision.html?getActualTillNovFromDecAmount&cnt="+cnt;

		var returnData1 = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData1, function(key, value) {	
			var prRevBudgetCode = $('#prRevBudgetCode'+cnt).val();
			if( prRevBudgetCode != "" ){
			if (key == 'ATN') {
				$("#actualTillNovAmount" + cnt).val(value);
			}
			if (key == 'DTM') {
				$("#budgetedFromDecAmount" + cnt).val(value);
			}
			}
		});
	}
	
	if (errorList.length == 0) {

		var url = "AccountBudgetAdditionalSupplemental.html?getReappDynamicRevPrimarykeyIdDetails&cnt="+cnt;

		var returnDataRevDynamic = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnDataRevDynamic, function(key, value) {
			$("#prProjectionidRevDynamic"+ cnt).val(value);
			
		});

	}
};



//to generate dynamic table
$("#prProjectionid")
		.on("click",'.addButton',
				function(e){
var errorList = [];

 $('.appendableClass').each(function(i) {
		 var prRevBudgetCode = $.trim($("#prRevBudgetCode"+i).val());
		 if(prRevBudgetCode==0 || prRevBudgetCode=="") 
		 var prProjected = $.trim($("#revisedAmount"+i).val());
		 if(prProjected==0 || prProjected=="")
		 for(m=0; m<i;m++){
				if($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+i).val()){
					
					errorList.push("The Combination Budget Head already exists, Please select again!");
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
			var content = $(this).closest('#revTable tr').clone();
			$(this).closest("#revTable").append(content);
			content.find("select").attr("value", "");
			content.find("input:text").val("");
			content.find("select").val("");
			content.find('div.chosen-container').remove();
			content.find("select").chosen().trigger("chosen:updated");
			content.find('label').closest('.error').remove(); //for removal duplicate
			reOrderTableIdSequence();

		});

//to delete row
$("#prProjectionid").on("click", '.delButton', function(e) {

var rowCount = $('#revTable tr').length;
if (rowCount <= 3) {
//alert("Can Not Remove");
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
		
		$(this).find('div.chosen-container').attr('id',"prRevBudgetCode"+i+"_chosen");
		$(this).find("select:eq(0)").attr("id", "prRevBudgetCode" + i);
		$(this).find("input:text:eq(0)").attr("id", "prRevBudgetCode" + i);
		$(this).find("input:text:eq(1)").attr("id", "orginalEstamt" + i);
		$(this).find("input:text:eq(2)").attr("id", "actualTillNovAmount" + i);
		$(this).find("input:text:eq(3)").attr("id", "budgetedFromDecAmount" + i);
		$(this).find("input:text:eq(4)").attr("id","revisedAmount" + i);
		$(this).find("input:hidden:eq(0)").attr("id","prProjectionidRevDynamic"+i);
		$(this).find("select:eq(0)").attr("name",
				"bugprojRevBeanList[" + i + "].prRevBudgetCode");
		$(this).find("input:text:eq(0)").attr("name",
				"bugprojRevBeanList[" + i + "].prRevBudgetCode");
		$(this).find("input:text:eq(1)").attr("name",
				"bugprojRevBeanList[" + i + "].orginalEstamt");
		$(this).find("input:text:eq(2)").attr("name",
				"bugprojRevBeanList[" + i + "].actualTillNovAmount");
		$(this).find("input:text:eq(3)").attr("name",
				"bugprojRevBeanList[" + i + "].budgetedFromDecAmount");
		$(this).find("input:text:eq(4)").attr("name",
				"bugprojRevBeanList[" + i + "].revisedAmount");
		$(this).find("input:hidden:eq(0)").attr("name","bugprojRevBeanList["+i+"].prProjectionidRevDynamic");
		$(this).parents('tr').find('.delButton').attr("id",
				"delButton" + i);
		$(this).parents('tr').find('.addButton').attr("id",
				"addButton" + i);
	      $(this).find('#prRevBudgetCode'+i).attr("onchange",
				"getOrgBalAmount(" + (i) + ")");
	      
	     $(this).find('#prRevBudgetCode'+i).data('rule-required',true);
	     $(this).find('#revisedAmount'+i).data('rule-required',true);
	      
		$(this).closest("tr").attr("id", "bugestIdRev" + (i));
		$("#indexdata").val(i);
	});
}

function getOrgBalAmountExp(cont) {
	
	if ($('#count').val() == "") {
		count = 1;
	} else {
		count = parseInt($('#count').val());
	}
	var assign = count;

	$('.error-div').hide();
	var errorList = [];
	
	$("#orginalEstamtExp"+cont).val("");
	$("#actualTillNovAmountExp"+cont).val("");
	$("#budgetedFromDecAmountExp"+cont).val("");
	$("#revisedAmountExp"+cont).val("");
	var theForm = '#tbAcBudgetoryRevision';
	var ExpdpDeptid = $('#dpDeptid').val();
	if (ExpdpDeptid == '') {
		errorList.push('Please Select Department');
		var cpdBugsubtypeIdExp = $("#prExpBudgetCode"+cont).val("");
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
			var prExpBudgetCode = $("#prExpBudgetCode"+cont).val("");
			$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
		}
	}
	
	 var Expid =  $("#indexdata").val();
	 var dec;
		if(prExpBudgetCode != "" ){
			if(Expid == "" || Expid== undefined){
				Expid = 0;
			}
			
		 for(m=0; m<=Expid;m++){
			 for(dec=0; dec<=Expid;dec++){
				 if(m!=dec){
				if(($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+dec).val())){
					errorList.push("The Combination Budget Head already exists!");
					var prRevBudgetCode = $("#prExpBudgetCode"+cont).val("");
					$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
					var orginalEstamtExp = $("#orginalEstamtExp"+cont).val("");
					var actualTillNovAmountExp = $("#actualTillNovAmountExp"+cont).val("");
					var budgetedFromDecAmountExp = $("#budgetedFromDecAmountExp"+cont).val("");
					var revisedAmountExp = $("#revisedAmountExp"+cont).val("");					
					
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
		 
		var url = "AccountBudgetoryRevision.html?getOrgBalExpDuplicateGridloadData&cont="+cont;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		 var errorList= [];	
		 if(returnData){
			errorList.push("Budget Revised Combination Budget Head is Already Exist!"); 
			var pacHeadId = $("#prExpBudgetCode"+cont).val("");
			$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
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
	
	if (errorList.length == 0){
		
		var url = "AccountBudgetoryRevision.html?getOrgBalGridloadExp&cont="+cont;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			var prExpBudgetCode = $('#prExpBudgetCode'+cont).val();
			if( prExpBudgetCode != "" ){
			if (key == 'OEAE') {
				$("#orginalEstamtExp" + cont).val(value);
			}
			}
		});
	}
	
	if (errorList.length == 0) {

		var url = "AccountBudgetoryRevision.html?getExpActualTillNovFromDecAmount&cont="+cont;

		var returnData1 = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData1, function(key, value) {	
			var prExpBudgetCode = $('#prExpBudgetCode'+cont).val();
			if( prExpBudgetCode != "" ){
			if (key == 'ATNE') {
				$("#actualTillNovAmountExp" + cont).val(value);
			}
			if (key == 'DTME') {
				$("#budgetedFromDecAmountExp" + cont).val(value);
			}
			}
		});
	}
	
	if (errorList.length == 0) {

		var url = "AccountBudgetAdditionalSupplemental.html?getExpPrimarykeyIdDetails&cont="+cont;

		var returnDataRevDynamic = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnDataRevDynamic, function(key, value) {
			
			$("#prExpenditureidExpDynamic"+cont).val(value);
			
		});

	}
};

//to generate dynamic table
$("#prExpenditureid")
		.on("click",'.addButtonExp',
				function(e){
var errorList = [];

   $('.ExpappendableClass').each(function(i) {
		 var prExpBudgetCode = $.trim($("#prExpBudgetCode"+i).val());
		 if(prExpBudgetCode==0 || prExpBudgetCode=="") 
		 var prProjectedExp = $.trim($("#revisedAmountExp"+i).val());
		 if(prProjectedExp==0 || prProjectedExp=="")
		 for(m=0; m<i;m++){
				if($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+i).val()){
					
					errorList.push("The Combination code already exists, Please select another combination code again!");
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
			var content = $(this).closest('#expTable tr').clone();
			$(this).closest("#expTable").append(content);
			content.find("select").attr("value", "");
			content.find("input:text").val("");
			content.find("select").val("");
			content.find('div.chosen-container').remove();
			content.find("select").chosen().trigger("chosen:updated");
			content.find('label').closest('.error').remove(); //for removal duplicate
			expreOrderTableIdSequence();

		});

//to delete row
$("#prExpenditureid").on("click", '.delButtonExp', function(e) {

var rowCount = $('#expTable tr').length;
if (rowCount <= 3) {
//alert("Can Not Remove");
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
		
		$(this).find('div.chosen-container').attr('id',"prExpBudgetCode"+i+"_chosen");
		$(this).find("select:eq(0)").attr("id", "prExpBudgetCode" + i);
		$(this).find("input:text:eq(0)").attr("id", "prExpBudgetCode" + i);
		$(this).find("input:text:eq(1)").attr("id", "orginalEstamtExp" + i);
		$(this).find("input:text:eq(2)").attr("id", "actualTillNovAmountExp" + i);
		$(this).find("input:text:eq(3)").attr("id", "budgetedFromDecAmountExp" + i);
		$(this).find("input:text:eq(4)").attr("id","revisedAmountExp" + i);
		$(this).find("input:hidden:eq(0)").attr("id","prExpenditureidExpDynamic"+i);
		$(this).find("select:eq(0)").attr("name",
				"bugprojExpBeanList[" + i + "].prExpBudgetCode");
		$(this).find("input:text:eq(0)").attr("name",
				"bugprojExpBeanList[" + i + "].prExpBudgetCode");
		$(this).find("input:text:eq(1)").attr("name",
				"bugprojExpBeanList[" + i + "].orginalEstamt");
		$(this).find("input:text:eq(2)").attr("name",
				"bugprojExpBeanList[" + i + "].actualTillNovAmount");
		$(this).find("input:text:eq(3)").attr("name",
				"bugprojExpBeanList[" + i + "].budgetedFromDecAmount");
		$(this).find("input:text:eq(4)").attr("name",
				"bugprojExpBeanList[" + i + "].revisedAmount");
		$(this).find("input:hidden:eq(0)").attr("name","bugprojExpBeanList["+i+"].prExpenditureidExpDynamic");
		$(this).parents('tr').find('.delButtonExp').attr("id",
				"delButtonExp" + i);
		$(this).parents('tr').find('.addButtonExp').attr("id",
				"addButtonExp" + i);
	      $(this).find('#prExpBudgetCode'+i).attr("onchange",
				"getOrgBalAmountExp(" + (i) + ")");
	      $(this).find('#prExpBudgetCode'+i).data('rule-required',true);
		  $(this).find('#revisedAmountExp'+i).data('rule-required',true);
		$(this).closest("tr").attr("id", "bugestIdExp" + (i));
		$("#indexdata").val(i);
	});
}

function RemoveTranferAmount(obj){
	
	var cpdBugsubtypeId = $("#cpdBugsubtypeId").val("");
}
	

function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.billEntry.savedBtnProceed');
	
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
	window.location.href='AccountBudgetoryRevision.html';
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

function clearAllData(obj) {
	$('#hiddenFinYear').val($('#faYearid').val());
	var orginalEstamt = $("#orginalEstamt0").val("");
	var actualTillNovAmount = $("#actualTillNovAmount0").val("");
	var budgetedFromDecAmount = $("#budgetedFromDecAmount0").val("");
	var revisedAmount = $("#revisedAmount0").val("");
	var orginalEstamtExp = $("#orginalEstamtExp0").val("");
	var actualTillNovAmountExp = $("#actualTillNovAmountExp0").val("");
	var budgetedFromDecAmountExp = $("#budgetedFromDecAmountExp0").val("");
	var revisedAmountExp = $("#revisedAmountExp0").val("");
	$('.error-div').hide();
	var errorList = [];
	if (errorList.length == 0) {
		
		var divName = ".content";
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetoryRevision.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);
		
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		var budgetType = $('#cpdBugtypeId').val();

		$('#faYearid').prop('disabled', 'disabled');

		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
		if (budgetType == "REV") {
			
			$("#prRevBudgetCode0").data('rule-required',true);
			$("#revisedAmount0").data('rule-required',true);
			
			$("#prProjectionid").removeClass("hide");
			$("#prExpenditureid").addClass("hide");
		} else if (budgetType == "EXP") {
			
			$("#prExpBudgetCode0").data('rule-required',true);
			$("#revisedAmountExp0").data('rule-required',true);
			
			$("#prProjectionid").addClass("hide");
			$("#prExpenditureid").removeClass("hide");
		}
	}
};

function getAmountFormat(cnt){
	
	var transferAmount = $('#transferAmount'+cnt).val();

	if (transferAmount != undefined && !isNaN(transferAmount) && transferAmount != null && transferAmount != '') {
		
	var actualAmt = transferAmount.toString().split(".")[0];
	var decimalAmt = transferAmount.toString().split(".")[1];
	
	var decimalPart =".00";
	if(decimalAmt == null || decimalAmt == undefined){
		$('#transferAmount'+cnt).val(actualAmt+decimalPart);
	}else{
		if(decimalAmt.length <= 0){
			decimalAmt+="00";
			$('#transferAmount'+cnt).val(actualAmt+(".")+decimalAmt);
		}
		else if(decimalAmt.length <= 1){
			decimalAmt+="0";
			$('#transferAmount'+cnt).val(actualAmt+(".")+decimalAmt);
		}else{
			if(decimalAmt.length <= 2){
			$('#transferAmount'+cnt).val(actualAmt+(".")+decimalAmt);
			} 
		  }	
	   }
    }
}


