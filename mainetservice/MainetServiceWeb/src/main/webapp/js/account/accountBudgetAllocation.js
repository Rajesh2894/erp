
$("#tbAcBudgetAllocation").validate({
	
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
				url : "AccountBudgetAllocation.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['', getLocalMessage('account.budgetallocationnmaster.budgethead'),getLocalMessage('account.budgetallocationnmaster.budgetamount'),getLocalMessage('account.budgetallocationnmaster.releasepercentage'),getLocalMessage('account.budgetallocationnmaster.balanceBudget'),getLocalMessage('bill.action')],
				colModel : [
				             {name : "baId",width : 30,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
				             {name : "prBudgetCode",width : 45,sortable : true, editable: true,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] } },
				             {name : "orginalEstamt",width : 25,sortable : true,classes:'text-right', editable: true,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] } },				            
				             {name : "releasePer",width : 25,sortable : true,classes:'text-right', editable: true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "amount",width : 25,sortable : true,classes:'text-right', editable: true,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] } },
				             {name: 'baId', index: 'baId', width:20 , align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "baId",
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
				caption : getLocalMessage('account.budgetallocationnmaster.accountbudgetallocationlist'),
				
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
   return "<a class='btn btn-blue-3 btn-sm viewBugopnBalMasterClass' title='View'value='"+rowdata.baId+"' baId='"+rowdata.baId+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editBugopnBalMasterClass' title='Edit'value='"+rowdata.baId+"' baId='"+rowdata.baId+"' ><i class='fa fa-pencil'></i></a> ";
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
		var baId = 1;
		var url = "AccountBudgetAllocation.html?form";
		var requestData = "baId=" + baId  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;		
	});			


	$(document).on('click', '.editBugopnBalMasterClass', function() {
		var $link = $(this);
		var baId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountBudgetAllocation.html?formForUpdate";
		var requestData = "baId=" + baId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
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
				var baId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountBudgetAllocation.html?formForView";
				var requestData = "baId=" + baId + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$('select').attr("disabled", true);
				$('input[type=text]').attr('disabled', true);
				$('input[type=checkbox]').attr('disabled', true);
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
	$('body').on('focus',".hasMyNumber", function(){
		$(".hasMyNumber").keyup(function(event) {
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength', '3');
	     });
		});
	if ($('#test').val() === 'EDIT') {
		var type = $('#cpdBugtypeIdHidden').val();
		if(type=="REV")
			{
			
			$("#prRevBudgetCode0").data('rule-required',true);
			$("#allocation0").data('rule-required',true);
			$("#budgetControlDate0").data('rule-required',true);
			
			$("#prProjectionid").removeClass('hide');
			$('#faYearid').prop('disabled', 'disabled');
			}
		else
			{
			
			$("#prExpBudgetCode0").data('rule-required',true);
			$("#Expallocation0").data('rule-required',true);
			$("#ExpbudgetControlDate0").data('rule-required',true);
			
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


var	errMsgDiv		=	'.msg-dialog-box';

function setSecondaryCodeFinanceRev(obj)
{
	 var faYearId = $("#faYearid").val();
	 var primaryCode=$('#pacHeadIdO').val();
	 
		$('#prRevBudgetCode').find('option:gt(0)').remove();
		
		if (primaryCode > 0) {
			var postdata = {"faYearid": faYearId,"pacHeadId" : primaryCode };
			
			var json = __doAjaxRequest('AccountBudgetAllocation.html?sacHeadItemsList',
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
	 var faYearId1 = $("#faYearid").val();
	 var primaryCode1=$('#pacHeadIdExp').val();
	 
		$('#prExpBudgetCode').find('option:gt(0)').remove();
		
		if (primaryCode1 > 0) {
			var postdata = {"faYearid": faYearId1,"pacHeadId" : primaryCode1 };
			
			var json = __doAjaxRequest('AccountBudgetAllocation.html?sacHeadItemsExpList',
					'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#prExpBudgetCode').append(optionsAsString );
			
		}
}


function handleChange(obj) {
  
	var id=  $(obj).attr('id');
	var arr = id.split('allocation');
	var indx=arr[1];
	
  if (obj.value < 0 || obj.value > 100)
  {
  var msg = "The Release Percentage % value must be (max) 100 % only.!";
  showErrormsgboxTitle(msg);
  $('#allocation'+indx).val("");
  $('#amount'+indx).val("");
  return false;
  }
  
  if($("#allocation"+indx).val()=="0" || $("#allocation"+indx).val()=="00" || $("#allocation"+indx).val()=="000"){
	  var msg = "Entered Release Percentage % value must not be currect!";
	  showErrormsgboxTitle(msg);
	  $('#allocation'+indx).val("");
	  $('#amount'+indx).val("");
	  return false;

	}
  
	stt = parseFloat($('#orginalEstamt'+indx).val());
	parentCode = parseFloat($('#allocation'+indx).val());
	if ((stt != "" && stt != undefined &&  !isNaN(stt))
			&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
	  var div = ((stt*parentCode)/100);
		var result = div.toFixed(2);
		$('#amount'+indx).val(result);
  }
		
}

function handleExpChange(obj) {
	  
	var id=  $(obj).attr('id');
	var arr = id.split('Expallocation');
	var indx=arr[1];
	
  if (obj.value < 0 || obj.value > 100)
  {
  var msg = "The Release Percentage % value must be (max) 100 % only.!";
  showErrormsgboxTitle(msg);
  $('#Expallocation'+indx).val("");
  $('#Expamount'+indx).val("");
  return false;
  }
  
  if($("#Expallocation"+indx).val()=="0" || $("#Expallocation"+indx).val()=="00" || $("#Expallocation"+indx).val()=="000"){
	  var msg = "Entered Release Percentage % value must not be currect!";
	  showErrormsgboxTitle(msg);
	  $('#Expallocation'+indx).val("");
	  $('#Expamount'+indx).val("");
	  return false;

	}
  
	stt = parseFloat($('#ExporginalEstamt'+indx).val());
	parentCode = parseFloat($('#Expallocation'+indx).val());
	if ((stt != "" && stt != undefined &&  !isNaN(stt))
			&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
	  var div = ((stt*parentCode)/100);
		var result = div.toFixed(2);
		$('#Expamount'+indx).val(result);
  }
		
}

function setHiddenData(){
	 $('#secondaryId').val($('#sacHeadId0').val());
}
	function setHiddenField(e) {
		$('#hiddenFinYear').val($('#faYearid').val());
		var value = $('option:selected', $("#faYearid")).attr('code');
		var arr = value.split('-');
		var start=arr[0];
		var end=arr[1];
		var startDate="01/04/"+start.trim();
		var endDate="31/03/"+end.trim();
		$('.datepicker').datepicker('destroy');
		$('.datepicker').datepicker({dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,minDate: startDate,maxDate: endDate});
	}


	function selectFirstFinyear(obj) {
		var faYearValid = $('#faYearid').val();		
		if (faYearValid == '') {
			var msg = "Please select financial year!";
			showErrormsgboxTitle(msg);
	         $('#validtillDate').val("");
	         return false;
		}
	    return true;
	}

	function changevaliddateedittime(obj){
		$('body').on('focus',".datepick", function(){
			$("#budgetControlDate0").datepicker({
				dateFormat: 'dd/mm/yy',	
				changeMonth: true,
				changeYear: true,
			});	
			});
		var value = $('option:selected', $("#faYearid")).attr('code');
		var arr = value.split('-');
		var start=arr[0];
		var end=arr[1];
		var startDate="01/04/"+start.trim();
		var endDate="31/03/"+end.trim();
		$('.datepick').datepicker('destroy');
		$('.datepick').datepicker({dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,minDate: startDate,maxDate: endDate});
		return true;
	}
	
function changeExpvaliddateedittime(obj){
		$('body').on('focus',".datepic", function(){
			$("#ExpbudgetControlDate0").datepicker({
				dateFormat: 'dd/mm/yy',	
				changeMonth: true,
				changeYear: true,
			});	
			});
		var value = $('option:selected', $("#faYearid")).attr('code');
		var arr = value.split('-');
		var start=arr[0];
		var end=arr[1];
		var startDate="01/04/"+start.trim();
		var endDate="31/03/"+end.trim();
		$('.datepic').datepicker('destroy');
		$('.datepic').datepicker({dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,minDate: startDate,maxDate: endDate});
		return true;
	}
	
	
	function loadBudgetAllocationData(obj) {

		$('.error-div').hide();
		var errorList = [];

		var orginalEstamt = $('#orginalEstamt0').val(""); 
		var amount = $('#amount0').val(""); 
		
		$('#hiddenFinYear').val($('#faYearid').val());
		var faYearid = $('#faYearid').val();
		var cpdBugtypeId = $('#cpdBugtypeId').val();
		var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

		if (faYearid == '') {
			var dpDeptid = $('#dpDeptid').val("");
		}
		
		if (cpdBugtypeId == '0') {
			var dpDeptid = $('#dpDeptid').val("");
		}
		
		if (cpdBugsubtypeId == '') {
			var dpDeptid = $('#dpDeptid').val("");
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
			
			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "AccountBudgetAllocation.html?getjqGridAllocationload";

			var response = __doAjaxRequest(url, 'post', requestData, false);

			var divName = ".content";
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);

			var budgetType = $('#cpdBugtypeId').val();

			$('#faYearid').prop('disabled', 'disabled');

			var budgetType=$("#cpdBugtypeId option:selected").attr("code");
			if (budgetType === "REV") {
				
				$("#prRevBudgetCode0").data('rule-required',true);
				$("#allocation0").data('rule-required',true);
				$("#budgetControlDate0").data('rule-required',true);
				
				$("#prProjectionid").removeClass("hide");
				$("#prExpenditureid").addClass("hide");
				
			} else if (budgetType === "EXP") {
				
				$("#prExpBudgetCode0").data('rule-required',true);
				$("#Expallocation0").data('rule-required',true);
				$("#ExpbudgetControlDate0").data('rule-required',true);
				
				$("#prProjectionid").addClass("hide");
				$("#prExpenditureid").removeClass("hide");
			}

		}
		
	};


	function clearAllData(obj) {
		var prRevBudgetCode = $('#prRevBudgetCode0').val(""); 
		$("#prRevBudgetCode0").val('').trigger('chosen:updated');
		
		var orginalEstamt = $('#orginalEstamt0').val(""); 
		var amount = $('#amount0').val(""); 

		var prExpBudgetCode = $('#prExpBudgetCode0').val(""); 
		$("#prExpBudgetCode0").val('').trigger('chosen:updated');
		
		var ExporginalEstamt = $('#ExporginalEstamt0').val(""); 
		var Expamount = $('#Expamount0').val("");
		
		$('.error-div').hide();
		var errorList = [];
		
		if (errorList.length == 0) {
			
			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "AccountBudgetAllocation.html?getjqGridAllocationload";

			var response = __doAjaxRequest(url, 'post', requestData, false);

			var divName = ".content";
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);

			var budgetSubType = $('#cpdBugsubtypeId').val();

			$('#faYearid').prop('disabled', 'disabled');
			var budgetType=$("#cpdBugtypeId option:selected").attr("code");
			if (budgetType === "REV") {
				
				$("#prRevBudgetCode0").data('rule-required',true);
				$("#allocation0").data('rule-required',true);
				$("#budgetControlDate0").data('rule-required',true);
				
				$("#prProjectionid").removeClass("hide");
				$("#prExpenditureid").addClass("hide");
				
			} else if (budgetType === "EXP") {
				
				$("#prExpBudgetCode0").data('rule-required',true);
				$("#Expallocation0").data('rule-required',true);
				$("#ExpbudgetControlDate0").data('rule-required',true);
				
				$("#prProjectionid").addClass("hide");
				$("#prExpenditureid").removeClass("hide");
			}
		}	
	}
	
	
	function saveLeveledData(obj){
		
		var errorList = [];
		
		
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
				
			/*for(k=0; k<j+1;k++){
			var prExpBudgetCode= $('#prExpBudgetCode'+k).val();
			var Expallocation= $('#Expallocation'+k).val();
			var ExpbudgetControlDate= $('#ExpbudgetControlDate'+k).val();
				
			if(prExpBudgetCode == '') {
				errorList.push("Please select Budget Head");
			}
			if(Expallocation == '' || Expallocation ==0){
				errorList.push("Please Enter Allocation Percentage");
			}
			if(ExpbudgetControlDate == ''){
				errorList.push("Please Select budget Control Date");
			}
			}*/
		}

		/*if(faYearid == '') {
			errorList.push("Please select Financial Year");
		}

		if(cpdBugtypeId == '0') {
			errorList.push("Please select Budget Type");
			
		}
		
		if(cpdBugsubtypeId == '') {
			errorList.push("Please select Budget Sub Type");
			
		}
		
		if(dpDeptid == ''){
			errorList.push("Please select Deparment Type");
		}*/
	
		/*var checked= $('#isFundRequired').is(':checked');
		if(authoFlg == ''){
			errorList.push("Please select auto Flag Box");
		}*/
		
		   /* if(errorList.length>0){
		    	
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

		/* return saveOrUpdateForm(obj, 'Saved Successfully', 'AccountBudgetAllocation.html','create');*/
		    	var	formName =	findClosestElementId(obj, 'form');
	    	    var theForm	=	'#'+formName;
	    	    var requestData = __serializeForm(theForm);
	    	    var url	=	$(theForm).attr('action');
	    	   
	    	    var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
	    	    if(response != false){
	    	       $('.content').html(response);
	    	    }
	    	    
	    	   // var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
	    	   // $('.content').html(response);
	    	
	/*}*/
	}

	function searchBudgetAllocationData() {

		$('.error-div').hide();
/*alert("Hi");*/
		var errorList = [];

			var faYearid = $("#faYearid").val();
			var cpdBugtypeId = $("#cpdBugtypeId").val();
			var dpDeptid = $("#dpDeptid").val();
			var prBudgetCodeid = $("#prBudgetCodeid").val();
			var fundId = $("#fundId").val();
			var functionId = $("#functionId").val();
			
			if (faYearid == "" || faYearid == "0") {
				errorList.push('Please Select Financial Year');
			}
			
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
			
			if (errorList.length == 0) {
				
			var url = "AccountBudgetAllocation.html?getjqGridsearch";
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
			
			/*$.ajax({
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
		}*/
	};

	function clearSacHeadCodeValue(obj){
		
		var sacHeadId = $('#prRevBudgetCode').val("");
		var orgRev = $("#orginalEstamtO").val("");
		
	}
	


function clearExpSacHeadCodeValue(obj){
		
		var sacHeadId = $('#prExpBudgetCode').val("");
		var orgExp = $("#orginalEstamtExp").val("");

	}

	function findduplicatecombinationexitExp(obj) {

		if ($('#count').val() == "") {
			count = 1;
		} else {
			count = parseInt($('#count').val());
		}
		var assign = count;

		$('.error-div').hide();
		var errorList = [];

		if (errorList.length == 0) {
			 
			var orginalEstamtExp = $("#orginalEstamtExp").val("");
			
			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);
			var url = "AccountBudgetAllocation.html?getOrgBalExpDuplicateGridloadData";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			 var errorList= [];	
			 
			 if(returnData){
				errorList.push("Budget Allocation Combination is Already Exist, Please select another one again!"); 
				var sacHeadId = $('#prExpBudgetCode').val("");
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

			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "AccountBudgetAllocation.html?getExpPrimarykeyIdDetails";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			$.each(returnData, function(key, value) {

				$('#prExpenditureidExp').val(value);
				
			});

		}
		
		if (errorList.length == 0) {

			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "AccountBudgetAllocation.html?getExpBudgetProvisionAmtDetails";

			var returnExpData = __doAjaxRequest(url, 'post', requestData, false);
			//alert(returnExpData+"Exp");
			$.each(returnExpData, function(index , value) {
				
			$('#orginalEstamtExp').val(returnExpData);	
				
			});

		}
	};

	
	function findduplicatecombinationexit(obj) {

		if ($('#count').val() == "") {
			count = 1;
		} else {
			count = parseInt($('#count').val());
		}
		var assign = count;

		$('.error-div').hide();
		var errorList = [];

		if (errorList.length == 0) {
			 
			var orginalEstamtO = $("#orginalEstamtO").val("");
			
			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);
			var url = "AccountBudgetAllocation.html?getOrgBalRevDuplicateGridloadData";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			 var errorList= [];	
			 if(returnData){
				errorList.push("Budget Allocation Combination is Already Exist, Please select another one again!"); 
				var sacHeadId = $('#prRevBudgetCode').val("");
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

			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "AccountBudgetAllocation.html?getRevPrimarykeyIdDetails";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			$.each(returnData, function(key, value) {

				$('#prProjectionidRev').val(value);
				
			});

		}
		
		if (errorList.length == 0) {

			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "AccountBudgetAllocation.html?getRevBudgetprovisionAmtDetails";

			var returnRevData = __doAjaxRequest(url, 'post', requestData, false);
			//alert(returnRevData+"Rav");
			$.each(returnRevData, function(index , value) {
				
				$('#orginalEstamtO').val(returnRevData);	
			
			});

		}
	};

	
	function clearBudgetType(obj){
		
		var cpdBugtypeId = $("#cpdBugtypeId").val("0");
		var orgRev = $("#orginalEstamtO").val("");
		var orgExp = $("#orginalEstamtExp").val("");
		
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
		window.location.href='AccountBudgetAllocation.html';
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
			 //errorList.push("Please select Budget Head");
			
			 var allocation = $.trim($("#allocation"+i).val());
			 if(allocation==0 || allocation=="")
			// errorList.push("Please Enter Allocation percentage");
			 
			 var budgetControlDate = $.trim($("#budgetControlDate"+i).val());
			 if(budgetControlDate==0 || budgetControlDate=="")
			// errorList.push("Please Select budget Control Date");
			 
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
				datePickerLogic();
				reOrderTableIdSequence();

			});

	//to delete row
	$("#prProjectionid").on("click", '.delButton', function(e) {

	var rowCount = $('#revTable tr').length;
	if (rowCount <= 2) {
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
			$(".datepick").datepicker("destroy");
			
			$(this).find('div.chosen-container').attr('id',"prRevBudgetCode"+i+"_chosen");
			$(this).find("select:eq(0)").attr("id", "prRevBudgetCode" + i);
			$(this).find("input:text:eq(0)").attr("id", "prRevBudgetCode" + i);
			$(this).find("input:text:eq(1)").attr("id", "orginalEstamt" + i);
			$(this).find("input:text:eq(2)").attr("id", "allocation" + i);
			$(this).find("input:text:eq(3)").attr("id","amount" + i);
			$(this).find("input:text:eq(4)").attr("id", "budgetControlDate" + i);
			$(this).find("input:hidden:eq(0)").attr("id","prProjectionidRevDynamic"+i);
			
			$(this).find("select:eq(0)").attr("name",
					"bugprojRevBeanList[" + i + "].prRevBudgetCode");
			$(this).find("input:text:eq(0)").attr("name",
					"bugprojRevBeanList[" + i + "].prRevBudgetCode");
			$(this).find("input:text:eq(1)").attr("name",
					"bugprojRevBeanList[" + i + "].orginalEstamt");
			$(this).find("input:text:eq(2)").attr("name",
					"bugprojRevBeanList[" + i + "].allocation");
			$(this).find("input:text:eq(3)").attr("name",
					"bugprojRevBeanList[" + i + "].amount");
			$(this).find("input:text:eq(4)").attr("name",
					"bugprojRevBeanList[" + i + "].budgetControlDate");
			
			$(this).find("input:hidden:eq(0)").attr("name","bugprojRevBeanList["+i+"].prProjectionidRevDynamic");
			
			$(this).parents('tr').find('.delButton').attr("id",
					"delButton" + i);
			$(this).parents('tr').find('.addButton').attr("id",
					"addButton" + i);
			/*$(this).parents('tr').find('#pacHeadId'+i).attr("onchange",
					"setSecondaryCodeFinance" + i); */
		    $(this).find('#prRevBudgetCode'+i).attr("onchange",
					"getOrgBalAmount(" + (i) + ")");
			$(this).closest("tr").attr("id", "bugestIdRev" + (i));
			   
			$(this).find('#prRevBudgetCode'+i).data('rule-required',true);
			$(this).find('#allocation'+i).data('rule-required',true);
			$(this).find('#budgetControlDate'+i).data('rule-required',true);
					
			$("#indexdata").val(i);
			
			datePickerLogic();
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
			// errorList.push("Please select Budget Head");
			
			 var Expallocation = $.trim($("#Expallocation"+i).val());
			 if(Expallocation==0 || Expallocation=="")
			// errorList.push("Please Enter Allocation percentage");
			 
			 var ExpbudgetControlDate = $.trim($("#ExpbudgetControlDate"+i).val());
			 if(ExpbudgetControlDate==0 || ExpbudgetControlDate=="")
			// errorList.push("Please Select budget Control Date");
	  
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
				content.find('label').closest('.error').remove(); //for removal duplicate
				datePickerExpLogic();
				expreOrderTableIdSequence();

			});

	//to delete row
	$("#prExpenditureid").on("click", '.delButtonExp', function(e) {

	var rowCount = $('#expTable tr').length;
	if (rowCount <= 2) {
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
			
			$(".datepic").datepicker("destroy");
			
			$(this).find('div.chosen-container').attr('id',"prExpBudgetCode"+i+"_chosen");
			$(this).find("select:eq(0)").attr("id", "prExpBudgetCode" + i);
			$(this).find("input:text:eq(0)").attr("id", "prExpBudgetCode" + i);
			$(this).find("input:text:eq(1)").attr("id", "ExporginalEstamt" + i);
			$(this).find("input:text:eq(2)").attr("id", "Expallocation" + i);
			$(this).find("input:text:eq(3)").attr("id","Expamount" + i);
			$(this).find("input:text:eq(4)").attr("id", "ExpbudgetControlDate" + i);
			$(this).find("input:hidden:eq(0)").attr("id","prExpenditureidExpDynamic"+i);
			
			$(this).find("select:eq(0)").attr("name",
					"bugprojExpBeanList[" + i + "].prExpBudgetCode");
			$(this).find("input:text:eq(0)").attr("name",
					"bugprojExpBeanList[" + i + "].prExpBudgetCode");
			$(this).find("input:text:eq(1)").attr("name",
					"bugprojExpBeanList[" + i + "].orginalEstamt");
			$(this).find("input:text:eq(2)").attr("name",
					"bugprojExpBeanList[" + i + "].expAllocation");
			$(this).find("input:text:eq(3)").attr("name",
					"bugprojExpBeanList[" + i + "].expAmount");
			$(this).find("input:text:eq(4)").attr("name",
					"bugprojExpBeanList[" + i + "].expBudgetControlDate");
			$(this).find("input:hidden:eq(0)").attr("name","bugprojExpBeanList["+i+"].prExpenditureidExpDynamic");
			$(this).parents('tr').find('.delButtonExp').attr("id",
					"delButtonExp" + i);
			$(this).parents('tr').find('.addButtonExp').attr("id",
					"addButtonExp" + i);
			
		    $(this).find('#prExpBudgetCode'+i).attr("onchange",
					"getOrgBalExpAmount(" + (i) + ")");
			$(this).closest("tr").attr("id", "bugestIdExp" + (i));
			
			$(this).find('#prExpBudgetCode'+i).data('rule-required',true);
			$(this).find('#Expallocation'+i).data('rule-required',true);
			$(this).find('#ExpbudgetControlDate'+i).data('rule-required',true);
			
			$("#indexdata").val(i);
			
			datePickerExpLogic();
		});
	}
	
 function getOrgBalAmount(cnt) {
	 $('.error-div').hide();
		var errorList = [];
	
	$("#orginalEstamt"+cnt).val("");
	$("#amount"+cnt).val("");
	var theForm = '#tbAcBudgetAllocation';
	
	var dpDeptid = $('#dpDeptid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();
	
	if (dpDeptid == '') {
		errorList.push('Please Select Department');
		var cpdBugsubtypeIdRev = $("#prRevBudgetCode"+cnt).val("");
		$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
	}
	
	if (cpdBugtypeId == '0') {
		errorList.push('Please Select Budget Type');
		var cpdBugsubtypeIdRev = $("#prRevBudgetCode"+cnt).val("");
		$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
	}
	
	if (cpdBugsubtypeId == '') {
		errorList.push('Please Select Budget Sub Type');
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
					var actualTillNovAmount = $("#allocation"+cnt).val("");
					var budgetedFromDecAmount = $("#amount"+cnt).val("");
					var revisedAmount = $("#budgetControlDate"+cnt).val("");					
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
		 
		var url = "AccountBudgetAllocation.html?getOrgBalRevDuplicateGridloadData&cnt="+cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		 var errorList= [];	
		 if(returnData){
			errorList.push("Budget Allocation Combination Budget Head is Already Exist!"); 
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

		var url = "AccountBudgetAllocation.html?getOrgBalGridload&cnt="+cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			
			var prRevBudgetCode = $('#prRevBudgetCode'+cnt).val();
			if( prRevBudgetCode != "" ){
			if (key == 'OAMT') {
				$("#orginalEstamt" + cnt).val(value);
			}
			
			stt = parseFloat($('#orginalEstamt'+cnt).val());
			parentCode = parseFloat($('#allocation'+cnt).val());
			if ((stt != "" && stt != undefined &&  !isNaN(stt))
					&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
			  var div = ((stt*parentCode)/100);
				var result = div.toFixed(2);
				$('#amount'+cnt).val(result);
		  	}
			}

		});

	}

	if (errorList.length == 0) {
		var url = "AccountBudgetAllocation.html?getReappDynamicRevPrimarykeyIdDetails&cnt="+cnt;
		var returnDataRevDynamic = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnDataRevDynamic, function(key, value) {
			$("#prProjectionidRevDynamic"+ cnt).val(value);
			
		});

	}
	};
	
	
function getOrgBalExpAmount(cont) {
		$('.error-div').hide();
		var errorList = [];
	$("#ExporginalEstamt"+cont).val("");
	$("#Expamount"+cont).val("");
	var theForm = '#tbAcBudgetAllocation';
	
	var dpDeptid = $('#dpDeptid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	
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
					var actualTillNovAmount = $("#Expallocation"+cont).val("");
					var budgetedFromDecAmount = $("#Expamount"+cont).val("");
					var revisedAmount = $("#ExpbudgetControlDate"+cont).val("");					
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
		 
		var url = "AccountBudgetAllocation.html?getOrgBalExpDuplicateGridloadData&cont="+cont;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		 var errorList= [];	
		 if(returnData){
			errorList.push("Budget Allocation Combination Budget Head is Already Exist!"); 
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
	
	if (errorList.length == 0) {

		var url = "AccountBudgetAllocation.html?getOrgBalExpGridload&cont="+cont;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			
			var prExpBudgetCode = $('#prExpBudgetCode'+cont).val();
			if( prExpBudgetCode != "" ){
			if (key == 'OEAMT') {
				$("#ExporginalEstamt"+cont).val(value);
			}
			
			stt = parseFloat($('#ExporginalEstamt'+cont).val());
			parentCode = parseFloat($('#Expallocation'+cont).val());
			if ((stt != "" && stt != undefined &&  !isNaN(stt))
					&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
			  var div = ((stt*parentCode)/100);
				var result = div.toFixed(2);
				$('#Expamount'+cont).val(result);
		  	}
			}

		});

	}

	if (errorList.length == 0) {

		var url = "AccountBudgetAllocation.html?getExpPrimarykeyIdDetails&cont="+cont;

		var returnDataRevDynamic = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnDataRevDynamic, function(key, value) {
			
			$("#prExpenditureidExpDynamic"+cont).val(value);
			
		});

	}
	};

	
	function datePickerLogic(){
		
		 $(".datepick").datepicker({
		        dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true
			});
		 
			$('#hiddenFinYear').val($('#faYearid').val());
			var value = $('option:selected', $("#faYearid")).attr('code');
			var arr = value.split('-');
			var start=arr[0];
			var end=arr[1];
			var startDate="01/04/"+start.trim();
			var endDate="31/03/"+end.trim();
			$('.datepick').datepicker('destroy');
			$('.datepick').datepicker({dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,minDate: startDate,maxDate: endDate});
		return true;
	}
	
	function datePickerExpLogic(){
		
		 $(".datepic").datepicker({
		        dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true
			});
		 
			$('#hiddenFinYear').val($('#faYearid').val());
			var value = $('option:selected', $("#faYearid")).attr('code');
			var arr = value.split('-');
			var start=arr[0];
			var end=arr[1];
			var startDate="01/04/"+start.trim();
			var endDate="31/03/"+end.trim();
			$('.datepic').datepicker('destroy');
			$('.datepic').datepicker({dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,minDate: startDate,maxDate: endDate});
		return true;
	}
	
	function setDeafaultFinancialYearEndDate(obj){
		
		$('#hiddenFinYear').val($('#faYearid').val());
		var value = $('option:selected', $("#faYearid")).attr('code');
		var arr = value.split('-');
		var start=arr[0];
		var end=arr[1];
		var startDate="01/04/"+start.trim();
		var endDate="31/03/"+end.trim();
		$('#budgetControlDate0').val("31/03/"+end);
		$('#ExpbudgetControlDate0').val("31/03/"+end);
	}
	
	function reSetDepartmentAllData(){
		
		var dpDeptid = $("#dpDeptid").val("");
	}
	
	