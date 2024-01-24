
$("#tbAcBudgetReappOfAuthorization").validate({
	
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
						url : "ReappropriationOfBudgetAuthorization.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
								'',
								getLocalMessage('account.budgetreappropriationmaster.transactionnumber'),
								getLocalMessage('account.budgetreappropriationmaster.transactiondate'),
								getLocalMessage('account.budgetreappropriationmaster.reappropriationamount'),
								getLocalMessage('account.budgetreappropriationmaster.status'),
								getLocalMessage('bill.action') ],
						colModel : [ {
							name : "paAdjid",
							width : 30,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							},
							hidden : true
						}, {
							name : "budgetTranRefNo",
							width : 15,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {
							name : "tempDate",
							width : 25,
							sortable : true,
							align : 'center',
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
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {
							name : "status",
							width : 15,
							sortable : true,
							align : 'center',
							editable : true,
							sortable : true,
							searchoptions : {
								"sopt" : ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']
							}
						}, {name: 'paAdjid', index: 'paAdjid', width:15 , align: 'center !important',formatter:addLink,search :false} ],
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
						caption : getLocalMessage('account.budgetreappropriationmaster.reappropriationauthorizationlist'),

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
		edit : true,
		add : false,
		del : false,
		search : true,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewBugReappBalMasterClass' title='View'value='"+rowdata.prProjectionid+"' prProjectionid='"+rowdata.prProjectionid+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editBugReappBalMasterClass' title='Edit'value='"+rowdata.prProjectionid+"' prProjectionid='"+rowdata.prProjectionid+"' ><i class='fa fa-pencil'></i></a> ";
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
		var paAdjid = 1;
		var url = "ReappropriationOfBudgetAuthorization.html?form";
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
		var authStatus = $link.closest('tr').find('td:eq(4)').text();
		var url = "ReappropriationOfBudgetAuthorization.html?update";
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
		var url = "ReappropriationOfBudgetAuthorization.html?formForView";
		var requestData = "paAdjid=" + paAdjid + "&MODE_DATA=" + "VIEW";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
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
	
	$('#flag').click(function(e) {
		  if($('#flag').attr('checked') == 'checked') {
		    $("#checkboxes input").attr('checked','checked');
		    $('#flagFlzd0').val('off');
		  } else {
		    $("#checkboxes input").attr('checked', false);
		    $('#flagFlzd0').val('on'); 
		  }
		});
	
	if ($('#test').val() === 'EDIT') {
		var type = $('#cpdBugtypeIdHidden').val();
		if(type=="REV")
		{
		var count = $('#revCount').val();
		for (i = 0; i <count; i++) { 
			$("#dpDeptid"+i).data('rule-required',true);
			$("#prRevBudgetCode"+i).data('rule-required',true);
			$("#prCollected"+i).data('rule-required',true);
		}
		
		$("#dpDeptidRev").data('rule-required',true);
		$("#prRevBudgetCodeO").data('rule-required',true);
		$("#remark").data('rule-required',true);

		$("#prProjectionid").removeClass('hide');
		$('#faYearid').prop('disabled', 'disabled');
		}
	else
		{
		
		var count = $('#expCount').val();
		for (i = 0; i <count; i++) { 
			$("#dpDeptidExp"+i).data('rule-required',true);
			$("#prExpBudgetCode"+i).data('rule-required',true);
			$("#expenditureAmt"+i).data('rule-required',true);
		}
		
		$("#ExpdpDeptid").data('rule-required',true);
		$("#budgetCodeExp").data('rule-required',true);
		$("#expRemark").data('rule-required',true);
		
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


function searchBudgetReappropriationData() {

	$('.error-div').hide();
	var errorList = [];
	if(checkValidation())
	{
	if (errorList.length == 0) {
	 fromDate = $("#fromDate").val();
	 toDate = $("#toDate").val();
	 cpdBugtypeId = $("#cpdBugtypeId").val();
	 status = $("#status option:selected").attr("code");
	 var url = "ReappropriationOfBudgetAuthorization.html?getjqGridSearchData";
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

function searchReappropriationOfBudgetData() {
	$('.error-div').hide();
	var errorList=[];
	if(checkValidation())
		{
	if (errorList.length == 0) {
		 fromDate = $("#fromDate").val();
		 toDate = $("#toDate").val();
		 cpdBugtypeId = $("#cpdBugtypeId").val();
		 status = $("#status option:selected").attr("code");
		var url = "ReappropriationOfBudgetAuthorization.html?getjqGridSearchData";
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
$("#prProjectionid")
		.on(
				"click",
				'.addButton',
				function(e) {
					var errorList = [];
					$('.appendableClass tr').each(function(i) {
						row = i-1;
						$('#index').val(i);
					});
					 var dpDeptid = $.trim($("#dpDeptid"+row).val());
					 if(dpDeptid==0 || dpDeptid=="") 
					 errorList.push("Please select required Department to re-appropriation to entry");
									
					 var prRevBudgetCode = $.trim($("#prRevBudgetCode"+row).val());
					 if(prRevBudgetCode==0 || prRevBudgetCode=="") 
					 errorList.push("Please select required budget code to re-appropriation to entry");
					 
					 var prCollected = $.trim($("#prCollected"+row).val());
					 if(prCollected==0 || prCollected=="")
					 errorList.push("Please enter required deduct amount against selected budget head");
					 
					 var prRevBudgetCodeO =  $.trim($("#prRevBudgetCodeO").val()); 
					 var dpDeptidRev =  $.trim($("#dpDeptidRev").val()); 
					 
					 if((prRevBudgetCodeO !="" && prRevBudgetCode != "") && (dpDeptidRev !="" && dpDeptid != "")){
					 if(($('#prRevBudgetCodeO').val() == $('#prRevBudgetCode'+row).val()) && ($('#dpDeptidRev').val() == $('#dpDeptid'+row).val())){
							
							errorList.push("The Budget code already exists in Re-Appropriation From Case!");
					 }
					 }
					 
					 if(prRevBudgetCode != ""){
					 for(m=0; m<row;m++){
							if(($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+row).val()) && ($('#dpDeptid'+m).val() == $('#dpDeptid'+row).val())){
								
								errorList.push("The Budget code already exists in Re-Appropriation To Case!");
							}
						}
					 }
					 
					if (errorList.length > 0) {
						$('#index').val(row);
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
					var content = $(this).closest('#prProjectionid1 tr')
							.clone();
					
					$(this).closest("#prProjectionid1").append(content);

					// reset values
					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find("input:hidden").val("");

					content.find("select:eq(0)").attr("id", "dpDeptid" + (row));
					content.find("input:text:eq(0)").attr("id", "dpDeptid" + (row));
					content.find("select:eq(1)").attr("id", "prRevBudgetCode" + (row));
					content.find("input:text:eq(1)").attr("id", "prRevBudgetCode" + (row));
					content.find("input:text:eq(2)").attr("id","orginalEstamt" + (row));
					content.find("input:text:eq(3)").attr("id","prProjected" + (row));
					content.find("input:text:eq(4)").attr("id","prCollected" + (row));
					content.find("input:text:eq(5)").attr("id","revisedEstamt" + (row));
					content.find("input:hidden:eq(0)").attr("id","prProjectionidRevDynamic"+(row));
					
					// for generating dynamic name
					content.find("select:eq(0)").attr("name",
							"bugprojRevBeanList1[" + row + "].dpDeptid");
					content.find("input:text:eq(0)").attr("name",
							"bugprojRevBeanList1[" + row + "].dpDeptid");
					content.find("select:eq(1)").attr("name",
							"bugprojRevBeanList1[" + row + "].prRevBudgetCode");
					content.find("input:text:eq(1)").attr("name",
							"bugprojRevBeanList1[" + row + "].prRevBudgetCode");
					content.find("input:text:eq(2)").attr("name",
							"bugprojRevBeanList1[" + row + "].orginalEstamt");
					content.find("input:text:eq(3)").attr("name",
							"bugprojRevBeanList1[" + row + "].prProjected");
					content.find("input:text:eq(4)").attr("name",
							"bugprojRevBeanList1[" + row + "].prCollected");
					content.find("input:text:eq(5)").attr("name",
							"bugprojRevBeanList1[" + row + "].revisedEstamt");
					content.find('.delButton').attr("id", "delButton" + (row));
					content.find('.addButton').attr("id", "addButton" + (row));
					content.find("select").val("");
					content.find("input:hidden:eq(0)").attr("name","bugprojRevBeanList1["+i+"].prProjectionidRevDynamic");
					content.find('div.chosen-container').remove();
					content.find("select").chosen().trigger("chosen:updated");
					reOrderTableIdSequence();
					$('#prCollected'+(row+1)).prop("disabled",true);
				});

//to delete row
$("#prProjectionid1").on("click", '.delButton', function(e) {
	var rowCount = $('#prProjectionid1 tr').length;
	if (rowCount <= 2) {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
	}

	$(this).closest('#prProjectionid1 tr').remove();

	reOrderTableIdSequence();
	getRevSumCalcAmount();
	e.preventDefault();
});

function reOrderTableIdSequence() {

	$('.appendableClass tr').each(
			function(i) {
				i = i - 1;

				$(this).find($('[id^="dpDeptid"]')).attr('id',"dpDeptid"+i+"_chosen");
				$(this).find($('[id^="prRevBudgetCode"]')).attr('id',"prRevBudgetCode"+i+"_chosen");
				
				$(this).find("select:eq(0)").attr("id", "dpDeptid" + i);
				$(this).find("input:text:eq(0)").attr("id", "dpDeptid" + i);
				$(this).find("select:eq(1)").attr("id", "prRevBudgetCode" + i);
				$(this).find("input:text:eq(1)").attr("id", "prRevBudgetCode" + i);
				$(this).find("input:text:eq(2)").attr("id", "orginalEstamt" + i);
				$(this).find("input:text:eq(3)").attr("id","prProjected" + i);
				$(this).find("input:text:eq(4)").attr("id", "prCollected" + i);
				$(this).find("input:text:eq(5)").attr("id","revisedEstamt" + i);
				$(this).find("input:hidden:eq(0)").attr("id","prProjectionidRevDynamic"+i);
				
				$(this).find("select:eq(0)").attr("name",
						"bugprojRevBeanList1[" + i + "].dpDeptid");
				$(this).find("input:text:eq(0)").attr("name",
						"bugprojRevBeanList1[" + i + "].dpDeptid");
				$(this).find("select:eq(1)").attr("name",
						"bugprojRevBeanList1[" + i + "].prRevBudgetCode");
				$(this).find("input:text:eq(2)").attr("name",
						"bugprojRevBeanList1[" + i + "].prRevBudgetCode");
				$(this).find("input:text:eq(2)").attr("name",
						"bugprojRevBeanList1[" + i + "].orginalEstamt");
				$(this).find("input:text:eq(3)").attr("name",
						"bugprojRevBeanList1[" + i + "].prProjected");
				$(this).find("input:text:eq(4)").attr("name",
						"bugprojRevBeanList1[" + i + "].prCollected");
				$(this).find("input:text:eq(5)").attr("name",
						"bugprojRevBeanList1[" + i + "].revisedEstamt");
				$(this).find("input:hidden:eq(0)").attr("name","bugprojRevBeanList1["+i+"].prProjectionidRevDynamic");
				$(this).find('#prRevBudgetCode'+i).attr("onchange","getOrgBalAmount1(" + (i) + ")");
				$(this).find('#dpDeptid'+i).attr("onchange","onDepartmentWiseBudgetHeadChange(" + (i) + ")");
				$(this).parents('tr').find('.delButton').attr("id",
						"delButton" + i);
				$(this).parents('tr').find('.addButton').attr("id",
						"addButton" + i);

			});
}

function copyContent(obj) {

	var id=  $(obj).attr('id');
	var arr = id.split('prCollected');
	var indx1=arr[1];
	var OldAmount1 = $('#revisedEstamt'+indx1).val("");
	var revisedEstamt = $('#revisedEstamtO').val("");
	
	 var prRevBudgetCode = $.trim($("#prRevBudgetCode0").val());
	
	 if(prRevBudgetCode==0 || prRevBudgetCode=="") {
	 var msg = "Please select Budget code.!";
	  showErrormsgboxTitle(msg);
	 $('#prCollected0').val("");
	 $('#revisedEstamt0').val("");
	 $('#prCollectedO').val("");
	  return false;
	 }
	  
	var stt = 0;
	var parentCode = 0;
	var copyAmt = 0;
	var stt1 = 0;
	var parentCode1 = 0;
	
	var id=  $(obj).attr('id');
	var arr = id.split('prCollected');
	var indx=arr[1];
	
	var editMode = $('#editMode').val();
	if(editMode == "edit"){
		var idSum = $('#revCount').val();
	}
	
		var total =0;
		for(k=0; k<=idSum;k++){
			var collectedAmt = $("#prCollected"+k).val();
			
			if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
				total += parseFloat($("#prCollected"+k).val());
			}
			
		}
		var result = total.toFixed(2); 
		$("#prCollectedO").val(result);
	
	stt1 = parseFloat($('#orginalEstamtO').val());
	parentCode1 = parseFloat($('#prCollectedO').val());
	if ((parentCode1 != "" && parentCode1 != undefined && !isNaN(parentCode1))
			&& (stt1 != "" && stt1 != undefined && !isNaN(stt1))) {
		var num = (stt1+parentCode1);
		var result = num.toFixed(2);
		$("#revisedEstamtO").val(result);
	}
	
	
	
	stt = parseFloat($('#orginalEstamt'+indx).val());
	parentCode = parseFloat($('#prCollected'+indx).val());
	if ((stt != "" && stt != undefined &&  !isNaN(stt))
			&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
		var substract = (stt - parentCode);
		var result = substract.toFixed(2);
		$('#revisedEstamt'+indx).val(result);
	}
	
	
	var minAmt = ($('#prProjected'+indx).val());
	var maxAmt = ($('#prCollected'+indx).val());
	  
	  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
	    try{
	      maxAmt = parseFloat(maxAmt);
	      minAmt = parseFloat(minAmt);

	      if(maxAmt > minAmt) {
	        var msg = "Deduct amount can not be greater than Current Balance Amount.!";
	    	  showErrormsgboxTitle(msg);
	        $('#prCollected'+indx).val("");
	        $('#revisedEstamt'+indx).val("");
	        return false;
	      }
	    }catch(e){
	      return false;
	    }
	  } //end maxAmt minAmt comparison  
}


function setHiddenField(e) {
	$('#hiddenFinYear').val($('#faYearid').val());
}

function loadBudgetReappropriationData(obj) {

	$('.error-div').hide();
	var errorList = [];

var theForm = '#tbAcBudgetReappOfAuthorization';
	var orginalEstamt0=$('#orginalEstamt0').val("");
	var prProjected0=$('#prProjected0').val("");
	var prCollected0 = $("#prCollected0").val("");
	var revisedEstamt0=$('#revisedEstamt0').val("");
	var orginalEstamtO = $("#orginalEstamtO").val("");
	var prProjectedO=$('#prProjectedO').val("");
	var prCollectedO=$('#prCollectedO').val("");
	var revisedEstamtO=$('#revisedEstamtO').val("");
	var remark=$('#remark').val("");
	var expRemark=$('#expRemark').val("");
	var ExporginalEstamt0=$('#ExporginalEstamt0').val("");
	var prBalanceAmt0=$('#prBalanceAmt0').val("");
	var expenditureAmt0 = $("#expenditureAmt0").val("");
	var ExprevisedEstamt0=$('#ExprevisedEstamt0').val("");
	var orginalEstamtExp = $("#orginalEstamtExp").val("");
	var prBalanceAmtExp=$('#prBalanceAmtExp').val("");
	var expenditureAmtExp=$('#expenditureAmtExp').val("");
	var revisedEstamtExp=$('#revisedEstamtExp').val("");
	var dpDeptid0=$('#dpDeptid0').val("");
	var dpDeptidRev=$('#dpDeptidRev').val("");
	var ExpdpDeptid=$('#ExpdpDeptid').val("");
	var dpDeptidExp=$('#dpDeptidExp0').val("");
	$('#hiddenFinYear').val($('#faYearid').val());
	var faYearid = $('#faYearid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugSubTypeId = $('#cpdBugSubTypeId').val();
	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var cpdBugSubTypeId = $('#cpdBugSubTypeId').val("");
	}
	if (cpdBugtypeId == '') {
		errorList.push('Please Select Budget Type');
		var cpdBugSubTypeId = $('#cpdBugSubTypeId').val("");
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
		var divName = ".content";
		var url = "ReappropriationOfBudgetAuthorization.html?getjqGridload";
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

//to generate dynamic table
$("#prExpenditureid")
		.on(
				"click",
				'.addButton',
				function(e) {
					var errorList = [];
					$('.appendableExpClass tr').each(function(i) {
						row = i-1;
						$('#index').val(i);
					});
					
					var dpDeptidExp = $.trim($("#dpDeptidExp"+row).val());
					 if(dpDeptidExp==0 || dpDeptidExp=="") 
					 errorList.push("Please select required Department to re-appropriation to entry");
					 
					 var prExpBudgetCode = $.trim($("#prExpBudgetCode"+row).val());
					 if(prExpBudgetCode==0 || prExpBudgetCode=="")
					 errorList.push("Please select required budget code to re-appropriation to entry");
					 
					 var expenditureAmt = $.trim($("#expenditureAmt"+row).val());
					 if(expenditureAmt==0 || expenditureAmt=="")
					 errorList.push("Please enter required deduct amount against selected budget head");
					 
					 var budgetCodeExp =  $.trim($("#budgetCodeExp").val());
					 var ExpdpDeptid =  $.trim($("#ExpdpDeptid").val());
					 
					 if(budgetCodeExp != "" && prExpBudgetCode != "" ){
					 if(($('#budgetCodeExp').val() == $('#prExpBudgetCode'+row).val()) && ($('#ExpdpDeptid').val() == $('#dpDeptidExp'+row).val())){
							
						errorList.push("The Budget code already exists in Re-Appropriation From Case!");
					 	
					 }
					 }
					 
					 if(prExpBudgetCode != ""){
					 for(m=0; m<row;m++){
							if(($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+row).val()) && ($('#dpDeptidExp'+m).val() == $('#dpDeptidExp'+row).val())){
								
								errorList.push("The Budget code already exists in Re-Appropriation To Case!");
							}
						}
					 }
					 
					if (errorList.length > 0) {
						$('#index').val(row);
						var errMsg = '<ul>';
						$
								.each(
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

					var content = $(this).closest('#prExpenditureid1 tr')
							.clone();
					$(this).closest("#prExpenditureid1").append(content);

					// reset values
					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find("input:hidden").val("");

					// for generating dynamic Id
					content.find("select:eq(0)").attr("id", "dpDeptidExp" + (row));
					content.find("input:text:eq(0)").attr("id","dpDeptidExp" + (row));
					content.find("select:eq(1)").attr("id", "prExpBudgetCode" + (row));
					content.find("input:text:eq(1)").attr("id","prExpBudgetCode" + (row));
					content.find("input:text:eq(2)").attr("id","ExporginalEstamt" + (row));
					content.find("input:text:eq(3)").attr("id","prBalanceAmt" + (row));
					content.find("input:text:eq(4)").attr("id","expenditureAmt" + (row));
					content.find("input:text:eq(5)").attr("id","ExprevisedEstamt" + (row));

					content.find("input:hidden:eq(0)").attr("id","prExpenditureidExpDynamic"+(row));
					// for generating dynamic name
					content.find("select:eq(0)").attr("name","bugprojExpBeanList1[" + row + "].dpDeptid");
					content.find("input:text:eq(0)").attr("name","bugprojExpBeanList1[" + row + "].dpDeptid");
					content.find("select:eq(1)").attr("name","bugprojExpBeanList1[" + row + "].prExpBudgetCode");
					content.find("input:text:eq(1)").attr("name","bugprojExpBeanList1[" + row + "].prExpBudgetCode");
					content.find("input:text:eq(2)").attr("name","bugprojExpBeanList1[" + row + "].orginalEstamt");
					content.find("input:text:eq(3)").attr("name","bugprojExpBeanList1[" + row + "].prBalanceAmt");
					content.find("input:text:eq(4)").attr("name","bugprojExpBeanList1[" + row + "].expenditureAmt");
					content.find("input:text:eq(5)").attr("name","bugprojExpBeanList1[" + row + "].revisedEstamt");

					content.find("input:hidden:eq(0)").attr("name","bugprojExpBeanList1["+ row +"].prExpenditureidExpDynamic");
					
					content.find('.delButton').attr("id", "delButton" + (row));
					content.find('.addButton').attr("id", "addButton" + (row));

					content.find("select").val("");
					content.find('div.chosen-container').remove();
					content.find("select").chosen().trigger("chosen:updated");
					reOrderTableIdSequence1();
					$('#expenditureAmt'+(row+1)).prop("disabled",true);

				});

//to delete row
$("#prExpenditureid1").on("click", '.delButton', function(e) {
	var rowCount = $('#prExpenditureid1 tr').length;
	if (rowCount <= 2) {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
	}

	$(this).closest('#prExpenditureid1 tr').remove();

	reOrderTableIdSequence1();
	getExpSumCalcAmount();
	e.preventDefault();
});

function reOrderTableIdSequence1() {

	$('.appendableExpClass tr')
			.each(
					function(i) {
						i = i - 1;

						$(this).find($('[id^="dpDeptidExp"]')).attr('id',"dpDeptidExp"+i+"_chosen");
						$(this).find($('[id^="prExpBudgetCode"]')).attr('id',"prExpBudgetCode"+i+"_chosen");
						
						$(this).find("select:eq(0)").attr("id", "dpDeptidExp" + i);
						$(this).find("input:text:eq(0)").attr("id","dpDeptidExp" + i);
						$(this).find("select:eq(1)").attr("id", "prExpBudgetCode" + i);
						$(this).find("input:text:eq(1)").attr("id","prExpBudgetCode" + i);
						$(this).find("input:text:eq(2)").attr("id","ExporginalEstamt" + i);
						$(this).find("input:text:eq(3)").attr("id","prBalanceAmt" + i);
						$(this).find("input:text:eq(4)").attr("id","expenditureAmt" + i);
						$(this).find("input:text:eq(5)").attr("id","ExprevisedEstamt" + i);
						$(this).find("input:hidden:eq(0)").attr("id","prExpenditureidExpDynamic"+i);
						$(this).find("select:eq(0)").attr("name","bugprojExpBeanList1[" + i + "].dpDeptid");
						$(this).find("input:text:eq(0)").attr("name","bugprojExpBeanList1[" + i + "].dpDeptid");
						$(this).find("select:eq(1)").attr("name","bugprojExpBeanList1[" + i + "].prExpBudgetCode");
						$(this).find("input:text:eq(1)").attr("name","bugprojExpBeanList1[" + i + "].prExpBudgetCode");
						$(this).find("input:text:eq(2)").attr("name","bugprojExpBeanList1[" + i + "].orginalEstamt");
						$(this).find("input:text:eq(3)").attr("name","bugprojExpBeanList1[" + i + "].prBalanceAmt");
						$(this).find("input:text:eq(4)").attr("name","bugprojExpBeanList1[" + i + "].expenditureAmt");
						$(this).find("input:text:eq(5)").attr("name","bugprojExpBeanList1[" + i + "].revisedEstamt");
						$(this).find("input:hidden:eq(0)").attr("name","bugprojExpBeanList1["+i+"].prExpenditureidExpDynamic");
						$(this).find('#dpDeptidExp'+i).attr("onchange","onDepartmentWiseBudgetHeadExpChange(" + (i) + ")");
						$(this).find('#prExpBudgetCode'+i).attr("onchange","getOrgBalExpAmount1(" + (i) + ")");
						$(this).parents('tr').find('.delButton').attr("id","delButton" + i);
						$(this).parents('tr').find('.addButton').attr("id","addButton" + i);
						

					});
}


function copyContentExp(obj) {
	var id=  $(obj).attr('id');
	var arr = id.split('expenditureAmt');
	var indx1=arr[1];
	var OldAmount1 = $('#ExprevisedEstamt'+indx1).val("");
	var revisedEstamtExp = $('#revisedEstamtExp').val("");
	 var prExpBudgetCode = $.trim($("#prExpBudgetCode0").val());
	 if(prExpBudgetCode==0 || prExpBudgetCode=="") {
	 var msg = "Please select Budget code.!";
	  showErrormsgboxTitle(msg);
	 $('#expenditureAmt0').val("");
	 $('#expenditureAmtExp').val("");
	 $('#ExprevisedEstamt0').val("");
	  return false;
	 }
	  
	var stt = 0;
	var parentCode = 0;
	var copyAmt = 0;
	var stt1 = 0;
	var parentCode1 = 0;
	
	var id=  $(obj).attr('id');
	var arr = id.split('expenditureAmt');
	var indx=arr[1];
	
	var editModeExp = $('#editModeExp').val();
	if(editModeExp == "edit"){
		var idSum = $('#expCount').val();
	}

		var total =0;
		for(k=0; k<=idSum;k++){
			var collectedAmt = $("#expenditureAmt"+k).val();
			
			if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
				total += parseFloat($("#expenditureAmt"+k).val());
			}
			
		}
		var result = total.toFixed(2); 
		$("#expenditureAmtExp").val(result);
	
	stt1 = parseFloat($('#orginalEstamtExp').val());
	parentCode1 = parseFloat($('#expenditureAmtExp').val());
	if ((parentCode1 != "" && parentCode1 != undefined && !isNaN(parentCode1))
			&& (stt1 != "" && stt1 != undefined && !isNaN(stt1))) {
		var num = (stt1+parentCode1);
		var result = num.toFixed(2);
		$("#revisedEstamtExp").val(result);
	}
	
	
	
	stt = parseFloat($('#ExporginalEstamt'+indx).val());
	parentCode = parseFloat($('#expenditureAmt'+indx).val());
	if ((stt != "" && stt != undefined &&  !isNaN(stt))
			&& (parentCode != "" && parentCode != undefined &&  !isNaN(parentCode))) {
		var num = (stt - parentCode);
		var result = num.toFixed(2);
		$('#ExprevisedEstamt'+indx).val(result);
	}
	
	
	var minAmt = ($('#prBalanceAmt'+indx).val());
	var maxAmt = ($('#expenditureAmt'+indx).val());
	  
	  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
	    try{
	      maxAmt = parseFloat(maxAmt);
	      minAmt = parseFloat(minAmt);

	      if(maxAmt > minAmt) {
	        var msg = "Deduct amount can not be greater than Current Balance Amount.!";
	    	  showErrormsgboxTitle(msg);
	        $('#expenditureAmt'+indx).val("");
	        $('#ExprevisedEstamt'+indx).val("");
	        return false;
	      }
	    }catch(e){
	      return false;
	    }
	  } //end maxAmt minAmt comparison  
}


function getOrgBalExpAmount(obj) {
	var orginalEstamtExp = $("#orginalEstamtExp").val("");
	var prBalanceAmtExp=$('#prBalanceAmtExp').val("");
	var revisedEstamtExp=$("#revisedEstamtExp").val("");
	
if ($('#count').val() == "") {
	count = 1;
} else {
	count = parseFloat($('#count').val());
}
var assign = count;

$('.error-div').hide();
var errorList = [];

var editModeExp = $('#editModeExp').val();
if(editModeExp == "edit"){
	var Expid = $('#expCount').val();
}

var prExpBudgetCode = $('#prExpBudgetCode'+Expid).val();
if(prExpBudgetCode == '') {
	errorList.push("Please select required budget code to re-appropriation From entry");
	$("#budgetCodeExp").val("");
	$("#budgetCodeExp").val('').trigger('chosen:updated');
	$("#revisedEstamtExp").val("");
	
}

var prExpBudgetCode = $('#prExpBudgetCode'+Expid).val();
var budgetCodeExp = $('#budgetCodeExp').val();

if(budgetCodeExp != "" && prExpBudgetCode !=""){
for(m=0; m<=Expid;m++){
if( ($('#budgetCodeExp').val() == $('#prExpBudgetCode'+m).val())){
	errorList.push("The Budget code already exists in Re-Appropriation From Case!");
	$("#budgetCodeExp").val("");
	$("#budgetCodeExp").val('').trigger('chosen:updated');
	$("#revisedEstamtExp").val("");
}
}
}

if(prExpBudgetCode !=""){
for(m=0; m<Expid;m++){
	if(($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+Expid).val())){
		errorList.push("The Budget code already exists in Re-Appropriation From Case!");
		$("#budgetCodeExp").val("");
		$("#budgetCodeExp").val('').trigger('chosen:updated');
		$("#revisedEstamtExp").val("");
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


		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "ReappropriationOfBudgetAuthorization.html?getOrgBalExpGridload";

		var returnExpData = __doAjaxRequest(url, 'post', requestData, false);
		
		$.each(returnExpData, function(index , value) {
			$('#orginalEstamtExp').val(returnExpData[0]);
			$('#prBalanceAmtExp').val(returnExpData[1]);
			var stt = 0;
			var parentCode = 0;
			stt = parseFloat($('#orginalEstamtExp').val());
			parentCode = parseFloat($('#expenditureAmtExp').val());
			if ((stt != "" && stt != undefined && !isNaN(stt))
					&& (parentCode != "" && parentCode != undefined && !isNaN(parentCode))) {
				var num = (stt+parentCode);
				var result = num.toFixed(2);
				$("#revisedEstamtExp").val(result);
			}
			
		});

	}
	
	if (errorList.length == 0) {

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "ReappropriationOfBudgetAuthorization.html?getReappExpPrimarykeyIdDetails";

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			$('#prExpenditureidExp').val(value);
			
		});

	}
};



function checkingBenefitAmount(obj) {

	var idExp=$('#index').val();
	
	var id =  $(obj).attr('id');
	var arr = id.split('transferAmountO');
	var indx=arr[1];
	var OldAmount = $('#orginalEstamtExpO').val();
		
	if (OldAmount == '') {
		var msg = "The Parent Original estimate Amount or Old Original Amount amount must not be empty!";
		showErrormsgboxTitle(msg);
         $('#transferAmountO'+idExp).val("");
         $('#prBalanceAmt'+idExp).val("");
         $('#totalTransAmountO').val("");
         $('#newOrgExpAmountO').val("");
         return false;
	}

	var maxAmt = ($('#prBalanceAmt'+indx).val());
    var minAmt = ($('#transferAmountO'+indx).val());
    
    if ((minAmt != '') && (maxAmt != '')){
      try{
        maxAmt = parseFloat(maxAmt);
        minAmt = parseFloat(minAmt);

        if(maxAmt < minAmt) {
          var msg1 = "The transferAmount benefit amount must be larger than the Balance amount.!";
    	  showErrormsgboxTitle(msg1);
          $('#transferAmountO'+indx).val("");
          $('#totalTransAmountO').val("");
          $('#newOrgExpAmountO').val("");
          return false;
          
        }
      }catch(e){
        return false;
      }
    } //end maxAmt minAmt comparison

    return true;

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
		
		var editMode = $('#editMode').val();
		if(editMode == "edit"){
			var id = $('#revCount').val();
		}
		
		var prRevBudgetCodeO = $('#prRevBudgetCodeO').val();
		var prRevBudgetCode= $('#prRevBudgetCode'+id).val();
		var  dpDeptidRev = $("#dpDeptidRev").val();
		var  dpDeptid = $("#dpDeptid"+id).val();
		var  remark= $("#remark").val();
		
		if((prRevBudgetCodeO != "0" && prRevBudgetCode != "0") && (dpDeptidRev != "0" && dpDeptid != "0")){
		 if(($('#prRevBudgetCodeO').val() == $('#prRevBudgetCode'+id).val()) && ($('#dpDeptidRev').val() == $('#dpDeptid'+id).val())){
				
				errorList.push("The Budget code already exists in Re-Appropriation From Case!");
		 }
		 }
		 	
		var editMode = $('#editMode').val();
		if(editMode == "edit"){
			var Revid = $('#revCount').val();
		}
		if(prRevBudgetCode != "" && prRevBudgetCode != "0" ){
			var dec;
		 for(m=0; m<=Revid;m++){
			 for(dec=0; dec<=Revid;dec++){
				 if(m!=dec){
				if(($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+dec).val()) && ($('#dpDeptid'+m).val() == $('#dpDeptid'+dec).val())){
					
					errorList.push("The Budget code already exists in Re-Appropriation From Case!");
				}
			  }
			} 
		 }
		}
	} 

	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	}else{
		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
	}
	if (budgetType === "EXP") {
		
		var editModeExp = $('#editModeExp').val();
		if(editModeExp == "edit"){
			var id = $('#expCount').val();
		}
	
		var budgetCodeExp = $('#budgetCodeExp').val();
		var  expRemark= $("#expRemark").val();
		var prExpBudgetCode= $('#prExpBudgetCode'+id).val();
		var  dpDeptidExp = $("#dpDeptidExp"+id).val();
		var  ExpdpDeptid = $("#ExpdpDeptid").val();

		if((budgetCodeExp != "0" && prExpBudgetCode != "0") && (dpDeptidExp != "0" && ExpdpDeptid != "0")){
			 if(($('#budgetCodeExp').val() == $('#prExpBudgetCode'+id).val()) && ($('#ExpdpDeptid').val() == $('#dpDeptidExp'+id).val())){
					
					errorList.push("The budget head already exists in Re-Appropriation From Case!");
			 }
			 }
		 	
		var editModeExp = $('#editModeExp').val();
		if(editModeExp == "edit"){
			var Expid = $('#expCount').val();
		}

		if(prExpBudgetCode != "" && prExpBudgetCode != "0" ){
			var dec;
		 for(m=0; m<=Expid;m++){
			 for(dec=0; dec<=Expid;dec++){
				 if(m!=dec){
				if(($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+dec).val()) && ($('#dpDeptidExp'+m).val() == $('#dpDeptidExp'+dec).val())){
					
					errorList.push("The Budget code already exists in Re-Appropriation From Case!");
				}
			  }
			} 
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


function clearAmount(obj){
	var orginalEstamt = $("#orginalEstamtO").val("");
	var orginalEstamt1=$('#orginalEstamtOO').val("");
	
}

function clearAmount1(obj){
	var id=  $(obj).attr('id');
	var arr = id.split('fundId');
	var indx=arr[1];
	var prProjected = $("#prProjected"+indx).val("");
	var transferAmount = $("#transferAmount"+indx).val("");
	var totalTransAmount=$('#totalTransAmount').val("");
	var newOrgRevAmount=$('#newOrgRevAmount').val("");
	
}


function clearAmountExp(obj){
	var orginalEstamtExp = $("#orginalEstamtExp").val("");
	var orginalEstamtExp1=$('#orginalEstamtExpO').val("");
	
}

function clearAmountExp1(obj){
	var Expid=  $(obj).attr('id');
	var Exparr = Expid.split('ExpfundId');
	var Expindx=Exparr[1];
	var prBalanceAmt = $("#prBalanceAmt"+Expindx).val("");
	var transferAmountO = $("#transferAmountO"+Expindx).val("");
	var totalTransAmountO=$('#totalTransAmountO').val("");
	var newOrgExpAmountO=$('#newOrgExpAmountO').val("");
	
}



function getOrgBalExpAmount1(cont) {
	$('.error-div').hide();
	var errorList = [];
	var theForm = '#tbAcBudgetReappOfAuthorization';
	$("#ExporginalEstamt" + cont).val("");
	$("#prBalanceAmt" + cont).val("");
	$("#expenditureAmt" + cont).val("");
	$("#ExprevisedEstamt" + cont).val("");
	$("#expenditureAmtExp").val("");
	$("#revisedEstamtExp").val("");
	$("#expenditureAmt"+cont).prop("disabled",false);
	var editModeExp = $('#editModeExp').val();
	if(editModeExp == "edit"){
		var id = $('#expCount').val();
	}
	var prExpBudgetCode1 = $('#prExpBudgetCode'+cont).val();
	if(prExpBudgetCode1 == '') {
		errorList.push("Please select required budget code to re-appropriation from entry");
		$("#prExpBudgetCode"+cont).val("");
		$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
	}
	var prExpBudgetCode = $('#prExpBudgetCode'+cont).val();
	var budgetCodeExp = $('#budgetCodeExp').val();
	if(budgetCodeExp != "" && prExpBudgetCode != ""){
	for(m=0; m<=id;m++){
		if( ($('#budgetCodeExp').val() == $('#prExpBudgetCode'+m).val())){
			errorList.push("The Budget code already exists in Re-Appropriation To Case!");
			$("#prExpBudgetCode"+cont).val("");
			$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
			var expAmt = $("#expenditureAmt" + cont).val("");
		}
		}
	}
	var editModeExp = $('#editModeExp').val();
	if(editModeExp == "edit"){
		var Expid = $('#expCount').val();
	}
	if(prExpBudgetCode != "" ){
		var dec;
	 for(m=0; m<=Expid;m++){
		 for(dec=0; dec<=Expid;dec++){
			 if(m!=dec){
			if(($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+dec).val())){
				errorList.push("The Budget code already exists in Re-Appropriation From Case!");
				$("#prExpBudgetCode"+cont).val("");
				$("#prExpBudgetCode"+cont).val('').trigger('chosen:updated');
				$("#expenditureAmt"+cont).prop("disabled",true);
				$("#expenditureAmt" + cont).val("");
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
		var url = "ReappropriationOfBudgetAuthorization.html?getOrgBalExpGridload1&cont="+cont;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			if (key == 'BEAMT') {
				$("#prBalanceAmt" + cont).val(value);
			}
			if (key == 'OEAMT') {
				$("#ExporginalEstamt" + cont).val(value);
			}
		});

	}
	if (errorList.length == 0) {
		var url = "ReappropriationOfBudgetAuthorization.html?getReappDynamicExpPrimarykeyIdDetails&cont="+cont;
		var returnDataExpDynamic = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnDataExpDynamic, function(key, value) {
			$("#prExpenditureidExpDynamic"+ cont).val(value);
			
		});

	}
};


function getOrgBalAmount(obj) {
	var orginalEstamt = $("#orginalEstamtO").val("");
	var prCollectedO=$('#prProjectedO').val("");
	var revisedEstamt=$("#revisedEstamtO").val("");
	
if ($('#count').val() == "") {
	count = 1;
} else {
	count = parseFloat($('#count').val());
}
var assign = count;

$('.error-div').hide();
var errorList = [];

var editMode = $('#editMode').val();
if(editMode == "edit"){
	var Revid = $('#revCount').val();
}

var prRevBudgetCode = $('#prRevBudgetCode'+Revid).val();
if(prRevBudgetCode == '') {
	errorList.push("Please select required budget code to re-appropriation From entry");
	$("#prRevBudgetCodeO").val("");
	$("#prRevBudgetCodeO").val('').trigger('chosen:updated');
	$("#revisedEstamtO").val("");
}

var prRevBudgetCode = $('#prRevBudgetCode'+Revid).val();
var prRevBudgetCodeO = $('#prRevBudgetCodeO').val();

if(prRevBudgetCode != "" && prRevBudgetCodeO != ""){
for(m=0; m<=Revid;m++){
if( ($('#prRevBudgetCodeO').val() == $('#prRevBudgetCode'+m).val())){
	errorList.push("The Budget code already exists in Re-Appropriation From Case!");
	$("#prRevBudgetCodeO").val("");
	$("#prRevBudgetCodeO").val('').trigger('chosen:updated');
	$("#revisedEstamtO").val("");
}
}
}

if(prRevBudgetCode != ""){
for(m=0; m<Revid;m++){
	if(($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+Revid).val())){
		errorList.push("The Budget code already exists in Re-Appropriation From Case!");
		$("#prRevBudgetCodeO").val("");
		$("#prRevBudgetCodeO").val('').trigger('chosen:updated');
		$("#revisedEstamtO").val("");
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

	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);

	var url = "ReappropriationOfBudgetAuthorization.html?getOrgBalGridload";
	var returnRevData = __doAjaxRequest(url, 'post', requestData, false);
	
	$.each(returnRevData, function(index , value) {
		$('#orginalEstamtO').val(returnRevData[0]);	
		$('#prProjectedO').val(returnRevData[1]);
		var stt = 0;
		var parentCode = 0;
		stt = parseFloat($('#orginalEstamtO').val());
		parentCode = parseFloat($('#prCollectedO').val());
		if ((stt != "" && stt != undefined && !isNaN(stt))
				&& (parentCode != "" && parentCode != undefined && !isNaN(parentCode))) {
			var num = (stt+parentCode);
			var result = num.toFixed(2);
			$("#revisedEstamtO").val(result);
		}
	});

}

if (errorList.length == 0) {

	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var requestData = __serializeForm(theForm);

	var url = "ReappropriationOfBudgetAuthorization.html?getReappRevPrimarykeyIdDetails";

	var returnDataRev = __doAjaxRequest(url, 'post', requestData, false);
	$.each(returnDataRev, function(key, value) {
		$("#prProjectionidRev").val(value);
		
	});

}
};


function getOrgBalAmount1(cnt) {
	$('.error-div').hide();
	var errorList = [];
	var theForm = '#tbAcBudgetReappOfAuthorization';
	$("#orginalEstamt" + cnt).val("");
	$("#prProjected" + cnt).val("");	
	$("#prCollected" + cnt).val("");
	$("#revisedEstamt" + cnt).val("");
	$("#prCollectedO").val("");
	$("#revisedEstamtO").val("");
	$("#prCollected"+cnt).prop("disabled",false);
	var editMode = $('#editMode').val();
	if(editMode == "edit"){
		var id = $('#revCount').val();
	}
	
	var prRevBudgetCode1 = $('#prRevBudgetCode'+cnt).val();
	if(prRevBudgetCode1 == '') {
		errorList.push("Please select required budget code to re-appropriation from entry");
		$("#prRevBudgetCode"+cnt).val("");
		$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
	}
	
	var prRevBudgetCode = $('#prRevBudgetCode'+cnt).val();
	var prRevBudgetCodeO = $('#prRevBudgetCodeO').val();
	
	if(prRevBudgetCode != "" && prRevBudgetCodeO != ""){
	for(m=0; m<=id;m++){
		if( ($('#prRevBudgetCodeO').val() == $('#prRevBudgetCode'+m).val())){
			errorList.push("The Budget code already exists in Re-Appropriation To Case!!");
			$("#prRevBudgetCode"+cnt).val("");
			$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
		}
		}
	}
	
	var editMode = $('#editMode').val();
	if(editMode == "edit"){
		var Revid = $('#revCount').val();
	}
		
	if(prRevBudgetCode != "" ){
		var dec;
	 for(m=0; m<=Revid;m++){
		 for(dec=0; dec<=Revid;dec++){
			 if(m!=dec){
			if(($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+dec).val())){
				errorList.push("The Budget code already exists in Re-Appropriation From Case!");
				$("#prRevBudgetCode"+cnt).val("");
				$("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated');
				$("#prCollected"+cnt).prop("disabled",true);
				$("#prCollected" + cnt).val("");
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
		var url = "ReappropriationOfBudgetAuthorization.html?getOrgBalGridload1&cnt="+cnt;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			if (key == 'BAMT') {
				$("#prProjected" + cnt).val(value);
			}
			if (key == 'OAMT') {
				$("#orginalEstamt" + cnt).val(value);
			}
		});

	}
	
	if (errorList.length == 0) {
		var url = "ReappropriationOfBudgetAuthorization.html?getReappDynamicRevPrimarykeyIdDetails&cnt="+cnt;
		var returnDataRevDynamic = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnDataRevDynamic, function(key, value) {
			$("#prProjectionidRevDynamic"+ cnt).val(value);
			
		});

	}
};

function checkingRevBenefitAmount(obj) {
	var idRev=$('#index').val();
	var id=  $(obj).attr('id');
	var arr = id.split('transferAmount');
	var indx=arr[1];
	var OldAmount = $('#orginalEstamtOO').val();
	if (OldAmount == '') {
		var msg = "The Parent Original estimate Amount or Old Original Amount amount must not be empty!";
		showErrormsgboxTitle(msg);
       $('#transferAmount'+idRev).val("");
       $('#prProjected'+idRev).val("");
       $('#totalTransAmount').val("");
       $('#newOrgRevAmount').val("");
       return false;
	}
	var maxAmt = ($('#prProjected'+indx).val());
    var minAmt = ($('#transferAmount'+indx).val());
  
  if ((minAmt != '') && (maxAmt != '')){
    try{
      maxAmt = parseFloat(maxAmt);
      minAmt = parseFloat(minAmt);
      if(maxAmt < minAmt) {
        var msg1 = "The transfer Amount benefit amount must be larger than the Projected provision amount.!";
    	  showErrormsgboxTitle(msg1);
        $('#transferAmount'+indx).val("");
        $('#totalTransAmountO').val("");
        $('#newOrgExpAmountO').val("");
        return false;
        
      }
    }catch(e){
      return false;
    }
  } //end maxAmt minAmt comparison

  return true;
}

function clearBudgetType(obj){
	var cpdBugtypeId = $("#cpdBugtypeId").val("0");
}


function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage("account.proceed.btn");
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToDishonorHomePage () {
	//debugger;
	$.fancybox.close();  //window.location.href="AdminHome.html";
	window.location.href='AdminHome.html';
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

function getRevSumCalcAmount(){
	
	var idSum = $('#index').val();
	var total =0;
	for(k=0; k<=idSum;k++){
		var collectedAmt = $("#prCollected"+k).val();
		
		if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
			total += parseFloat($("#prCollected"+k).val());
		}
		
	}
	var result = total.toFixed(2); 
	$("#prCollectedO").val(result);
stt1 = parseFloat($('#orginalEstamtO').val());
parentCode1 = parseFloat($('#prCollectedO').val());
if ((parentCode1 != "" && parentCode1 != undefined && !isNaN(parentCode1))
		&& (stt1 != "" && stt1 != undefined && !isNaN(stt1))) {
	var num = (stt1+parentCode1);
	var result = num.toFixed(2);
	$("#revisedEstamtO").val(result);
}

}

function getExpSumCalcAmount(){
	var idSum = $('#index').val();
	var total =0;
	for(k=0; k<=idSum;k++){
		var collectedAmt = $("#expenditureAmt"+k).val();
		if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
			total += parseFloat($("#expenditureAmt"+k).val());
		}
		
	}
	$("#expenditureAmtExp").val(total);

stt1 = parseFloat($('#orginalEstamtExp').val());
parentCode1 = parseFloat($('#expenditureAmtExp').val());
if ((parentCode1 != "" && parentCode1 != undefined && !isNaN(parentCode1))
		&& (stt1 != "" && stt1 != undefined && !isNaN(stt1))) {
	var num = (stt1+parentCode1);
	var result = num.toFixed(2);
	$("#revisedEstamtExp").val(result);
}
}

function clearAllData(obj) {
	
	var cpdBugSubTypeId=$('#cpdBugSubTypeId').val("");
	var orginalEstamt0=$('#orginalEstamt0').val("");
	var prProjected0=$('#prProjected0').val("");
	var prCollected0 = $("#prCollected0").val("");
	var revisedEstamt0=$('#revisedEstamt0').val("");
	var orginalEstamtO = $("#orginalEstamtO").val("");
	var prProjectedO=$('#prProjectedO').val("");
	var prCollectedO=$('#prCollectedO').val("");
	var revisedEstamtO=$('#revisedEstamtO').val("");
	var remark=$('#remark').val("");
	var expRemark=$('#expRemark').val("");
	var ExporginalEstamt0=$('#ExporginalEstamt0').val("");
	var prBalanceAmt0=$('#prBalanceAmt0').val("");
	var expenditureAmt0 = $("#expenditureAmt0").val("");
	var ExprevisedEstamt0=$('#ExprevisedEstamt0').val("");
	var orginalEstamtExp = $("#orginalEstamtExp").val("");
	var prBalanceAmtExp=$('#prBalanceAmtExp').val("");
	var expenditureAmtExp=$('#expenditureAmtExp').val("");
	var revisedEstamtExp=$('#revisedEstamtExp').val("");
	$('.error-div').hide();
	var errorList = [];
	
	if (errorList.length == 0) {
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "ReappropriationOfBudgetAuthorization.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		var budgetSubType = $('#cpdBugSubTypeId').val();
		$('#faYearid').prop('disabled', 'disabled');
		var budgetSubType=$("#cpdBugSubTypeId option:selected").attr("code");
		if (budgetType === "RV") {
			$("#prProjectionid").removeClass("hide");
			$("#prExpenditureid").addClass("hide");
		} else if (budgetType === "CP") {
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
			  errorList=validatedate(errorList,'fromDate');
			  }
		  if( $("#toDate").val()!=null)
			  {
			  errorList=validatedate(errorList,'toDate');
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

function onDepartmentWiseBudgetHeadChange(cnt) {
	console.log('onDepartmentWiseBudgetHeadChange');

	$('.error-div').hide();
	var errorList = [];

	var theForm = '#tbAcBudgetReappOfAuthorization';

	var prRevBudgetCode=$("#prRevBudgetCode"+cnt).val("");
	var orginalEstamt=$("#orginalEstamt"+cnt).val("");
	var prProjected=$("#prProjected"+cnt).val("");
	var prCollected=$("#prCollected"+cnt).val("");
	var revisedEstamt=$("#revisedEstamt"+cnt).val("");
	
	$('#hiddenFinYear').val($('#faYearid').val());
	
	var faYearid = $('#faYearid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugSubTypeId = $('#cpdBugSubTypeId').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid"+cnt).val('').trigger('chosen:updated');
	}
	if (cpdBugtypeId == '') {
		errorList.push('Please Select Budget Type');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid"+cnt).val('').trigger('chosen:updated');
	}
	if (cpdBugSubTypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid"+cnt).val('').trigger('chosen:updated');
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
		var divName = ".content";
		var url = "ReappropriationOfBudgetAuthorization.html?getAllBudgetHeadDesc&cnt="+cnt;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('#prRevBudgetCode'+cnt).text('');
		$('#prRevBudgetCode'+cnt).append($('<option>', { 
	        value: '0',
	        text : 'Select'
		   }));
		
		$.each(returnData, function(key, value) {
		
			$('#prRevBudgetCode'+cnt).append($('<option>', { 
		        value: key,
		        text : value 
		    }));
			$('#prRevBudgetCode'+cnt).trigger("chosen:updated");
		});
	}
};

function onDepartmentWiseBudgetHeadDesc(obj) {
	console.log('onDepartmentWiseBudgetHeadDesc');

	$('.error-div').hide();
	var errorList = [];

	var prRevBudgetCodeO=$("#prRevBudgetCodeO").val("");
	var orginalEstamtO=$("#orginalEstamtO").val("");
	var prProjectedO=$("#prProjectedO").val("");
	var revisedEstamtO=$("#revisedEstamtO").val("");
	
	$('#hiddenFinYear').val($('#faYearid').val());
	
	var faYearid = $('#faYearid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugSubTypeId = $('#cpdBugSubTypeId').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var dpDeptidRev = $('#dpDeptidRev').val("");
		$("#dpDeptidRev").val('').trigger('chosen:updated');
	}
	if (cpdBugtypeId == '') {
		errorList.push('Please Select Budget Type');
		var dpDeptidRev = $('#dpDeptidRev').val("");
		$("#dpDeptidRev").val('').trigger('chosen:updated');
	}
	if (cpdBugSubTypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var dpDeptidRev = $('#dpDeptidRev').val("");
		$("#dpDeptidRev").val('').trigger('chosen:updated');
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

		var url = "ReappropriationOfBudgetAuthorization.html?getAllBudgetHeadDetails";
		
		var returnRevData = __doAjaxRequest(url, 'post', requestData, false);
		
		$('#prRevBudgetCodeO').text('');
		$('#prRevBudgetCodeO').append($('<option>', { 
	        value: '0',
	        text : 'Select'
		   }));
		
		$.each(returnRevData, function(key, value) {
		
			$('#prRevBudgetCodeO').append($('<option>', { 
		        value: key,
		        text : value 
		    }));
			$('#prRevBudgetCodeO').trigger("chosen:updated");
		});
	}
};

function onDepartmentWiseBudgetHeadExpChange(cont) {
	console.log('onDepartmentWiseBudgetHeadExpChange');

	$('.error-div').hide();
	var errorList = [];

	var theForm = '#tbAcBudgetReappOfAuthorization';

	var prExpBudgetCode=$("#prExpBudgetCode"+cont).val("");
	var ExporginalEstamt=$("#ExporginalEstamt"+cont).val("");
	var prBalanceAmt=$("#prBalanceAmt"+cont).val("");
	var expenditureAmt=$("#expenditureAmt"+cont).val("");
	var ExprevisedEstamt=$("#ExprevisedEstamt"+cont).val("");
	
	$('#hiddenFinYear').val($('#faYearid').val());
	
	var faYearid = $('#faYearid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugSubTypeId = $('#cpdBugSubTypeId').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var dpDeptidExp = $('#dpDeptidExp').val("");
		$("#dpDeptidExp"+cont).val('').trigger('chosen:updated');
	}
	if (cpdBugtypeId == '') {
		errorList.push('Please Select Budget Type');
		var dpDeptidExp = $('#dpDeptidExp').val("");
		$("#dpDeptidExp"+cont).val('').trigger('chosen:updated');
	}
	if (cpdBugSubTypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var dpDeptidExp = $('#dpDeptidExp').val("");
		$("#dpDeptidExp"+cont).val('').trigger('chosen:updated');
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
		
		var divName = ".content";

		var url = "ReappropriationOfBudgetAuthorization.html?getAllBudgetHeadExpDesc&cont="+cont;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		
		$('#prExpBudgetCode'+cont).text('');
		$('#prExpBudgetCode'+cont).append($('<option>', {
	        value: '0',
	        text : 'Select'
		   }));
		
		$.each(returnData, function(key, value) {
		
			$('#prExpBudgetCode'+cont).append($('<option>', { 
		        value: key,
		        text : value 
		    }));
			$('#prExpBudgetCode'+cont).trigger("chosen:updated");
		});
	}
};

function onDepartmentWiseBudgetHeadExpDesc(obj) {
	console.log('onDepartmentWiseBudgetHeadExpDesc');

	$('.error-div').hide();
	var errorList = [];

	var budgetCodeExp=$("#budgetCodeExp").val("");
	var orginalEstamtExp=$("#orginalEstamtExp").val("");
	var prBalanceAmtExp=$("#prBalanceAmtExp").val("");
	var revisedEstamtExp=$("#revisedEstamtExp").val("");
	
	$('#hiddenFinYear').val($('#faYearid').val());
	
	var faYearid = $('#faYearid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugSubTypeId = $('#cpdBugSubTypeId').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var ExpdpDeptid = $('#ExpdpDeptid').val("");
		$("#ExpdpDeptid").val('').trigger('chosen:updated');
	}
	if (cpdBugtypeId == '') {
		errorList.push('Please Select Budget Type');
		var ExpdpDeptid = $('#ExpdpDeptid').val("");
		$("#ExpdpDeptid").val('').trigger('chosen:updated');
	}
	if (cpdBugSubTypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var ExpdpDeptid = $('#ExpdpDeptid').val("");
		$("#ExpdpDeptid").val('').trigger('chosen:updated');
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

		var url = "ReappropriationOfBudgetAuthorization.html?getAllBudgetHeadExpDetails";
		
		var returnRevData = __doAjaxRequest(url, 'post', requestData, false);
		
		$('#budgetCodeExp').text('');
		$('#budgetCodeExp').append($('<option>', { 
	        value: '0',
	        text : 'Select'
		   }));
		
		$.each(returnRevData, function(key, value) {
		
			$('#budgetCodeExp').append($('<option>', { 
		        value: key,
		        text : value 
		    }));
			$('#budgetCodeExp').trigger("chosen:updated");
		});
	}
};

function saveLeveledWorkFlowData(obj) {
	var errorList = [];
	incrementvalue=++count;
	checkCrossReappropriation(obj,errorList);
	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	}else{
		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
	}
	if (budgetType === "REV") {
		
		var editMode = $('#editMode').val();
		if(editMode == "edit"){
			var id = $('#revCount').val();
		}
		
		var prRevBudgetCodeO = $('#prRevBudgetCodeO').val();
		var prRevBudgetCode= $('#prRevBudgetCode'+id).val();
		var  dpDeptidRev = $("#dpDeptidRev").val();
		var  dpDeptid = $("#dpDeptid"+id).val();
		var  remark= $("#remark").val();
		
		if((prRevBudgetCodeO != "0" && prRevBudgetCode != "0") && (dpDeptidRev != "0" && dpDeptid != "0")){
		 if(($('#prRevBudgetCodeO').val() == $('#prRevBudgetCode'+id).val()) && ($('#dpDeptidRev').val() == $('#dpDeptid'+id).val())){
				
				errorList.push("The Budget code already exists in Re-Appropriation From Case!");
		 }
		 }
		 	
		var editMode = $('#editMode').val();
		if(editMode == "edit"){
			var Revid = $('#revCount').val();
		}
		if(prRevBudgetCode != "" && prRevBudgetCode != "0" ){
			var dec;
		 for(m=0; m<=Revid;m++){
			 for(dec=0; dec<=Revid;dec++){
				 if(m!=dec){
				if(($('#prRevBudgetCode'+m).val() == $('#prRevBudgetCode'+dec).val()) && ($('#dpDeptid'+m).val() == $('#dpDeptid'+dec).val())){
					
					errorList.push("The Budget code already exists in Re-Appropriation From Case!");
				}
			  }
			} 
		 }
		}
	} 

	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	}else{
		var budgetType=$("#cpdBugtypeId option:selected").attr("code");
	}
	if (budgetType === "EXP") {
		
		var editModeExp = $('#editModeExp').val();
		if(editModeExp == "edit"){
			var id = $('#expCount').val();
		}
	
		var budgetCodeExp = $('#budgetCodeExp').val();
		var  expRemark= $("#expRemark").val();
		var prExpBudgetCode= $('#prExpBudgetCode'+id).val();
		var  dpDeptidExp = $("#dpDeptidExp"+id).val();
		var  ExpdpDeptid = $("#ExpdpDeptid").val();

		if((budgetCodeExp != "0" && prExpBudgetCode != "0") && (dpDeptidExp != "0" && ExpdpDeptid != "0")){
			 if(($('#budgetCodeExp').val() == $('#prExpBudgetCode'+id).val()) && ($('#ExpdpDeptid').val() == $('#dpDeptidExp'+id).val())){
					
					errorList.push("The budget head already exists in Re-Appropriation From Case!");
			 }
			 }
		 	
		var editModeExp = $('#editModeExp').val();
		if(editModeExp == "edit"){
			var Expid = $('#expCount').val();
		}

		if(prExpBudgetCode != "" && prExpBudgetCode != "0" ){
			var dec;
		 for(m=0; m<=Expid;m++){
			 for(dec=0; dec<=Expid;dec++){
				 if(m!=dec){
				if(($('#prExpBudgetCode'+m).val() == $('#prExpBudgetCode'+dec).val()) && ($('#dpDeptidExp'+m).val() == $('#dpDeptidExp'+dec).val())){
					
					errorList.push("The Budget code already exists in Re-Appropriation From Case!");
				}
			  }
			} 
		 }
		}
		
	}

	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>&nbsp';
    		
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    		    	
    }else{
	    	var	formName =	findClosestElementId(obj, 'form');
    	    var theForm	=	'#'+formName;
    	    var requestData = __serializeForm(theForm);
    	    var url	=	$(theForm).attr('action');
    	    var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
    	    if(response != false){
    	    	$('.content').html(response);
    	       var val=$(response).find('#keyTest').val();
    	       successDisplayMessage(val);
    	    }
         }
	}


function checkCrossReappropriation(obj,errorList){
	//debugger;
	var	formName =	findClosestElementId(obj, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');
    var result = __doAjaxRequest(url+'?crossReappropriation', 'POST', requestData, false, 'json');
    if(result){
    	errorList.push("Please select same budget heads type because it is not cross reappropriation");
    }	
}


function successDisplayMessage(val){
	if (val != '' && val != undefined) {
		displayMessageOnSubmit(val);
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
}

$(document).ajaxComplete(function() {
	var errorList = [];
		 if($("#isServiceActive").val()=="active")
		 {
			 $('.editClass').removeAttr("class");
			 $("#search").prop( "disabled", true);
		     $('#disAnchor').attr("disabled","disabled");
		 }
	});	
	
