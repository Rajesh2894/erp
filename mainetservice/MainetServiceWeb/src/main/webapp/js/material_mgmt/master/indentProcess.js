$(document).ready(function() {

	$('#binDefMasterFrm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	
	$("#searchStore").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	
	var indentdate =$('#indentdate').val();
	if (indentdate)
	    $('#indentdate').val(indentdate.split(' ')[0]);

});


function addIndent(formUrl, actionParam) {
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}


function submitIndentForm(obj) {
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateUniqueItem();
	errorList = validateItemTable(errorList);
	errorList = validateWithHeld(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		$("#errorDiv").removeClass('hide');
		displayErrorsOnPage(errorList);
	} else
		return saveOrUpdateForm(obj, " ", 'IndentProcess.html?printDepartmentalIndent', 'saveform');
}


function validateItemTable(errorList){
	 var indenter = $('#indenter').val();
	 var indentdate = $('#indentdate').val();
	 var storeid = $('#storeid').val();
	 var deliveryat = $('#deliveryat').val();
	 var expecteddate = $('#expecteddate').val();
	 if (indentdate == "" || indentdate == undefined)
			errorList.push(getLocalMessage('department.indent.validation.date'));
	 if (indenter == "" || indenter == undefined)
			errorList.push(getLocalMessage('department.indent.validation.indentor'));
	 if (storeid == "" || storeid == undefined)
			errorList.push(getLocalMessage('department.indent.validation.store'));
	 if (deliveryat == "" || deliveryat == undefined)
			errorList.push(getLocalMessage('department.indent.validation.deliveryAdd'));
	 if (expecteddate == "" || expecteddate == undefined)
			errorList.push(getLocalMessage('department.indent.validation.expecteddate'));
	 if (Date.parse(indentdate) > Date.parse(expecteddate))
		 	errorList.push(getLocalMessage('department.indent.validation.deliveryExpected'));
	 return errorList;
}
/**
 * search indent summary
 * 
 * @param errorList
 * @returns
 */
function searchIndentStore(formUrl, actionParam) {
	var errorList = [];
	var data = {
			"storeid" : $('#storeid').val(),
			"indentno" : $('#indentno').val(),
			"indenter" : $('#indenter').val(),
			"status" : $('#status').val(),
			"deptId" : $('#deptId').val()
	};
	if($('#storeid').val() == '' &&  $('#indentno').val() == '' && $('#indenter').val() == '' && $('#status').val() == '0' && $('#deptId').val() == ''){
		errorList.push(getLocalMessage("department.indent.validation.search"));
		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
	}	 
	}else{
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	}
}
/**
 * disable future dates
 * 
 * @param errorList
 * @returns
 */
$('#indentdate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '0d',
	changeYear : true,
	pickTime: false
});

$('#expecteddate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	minDate : '0',
	changeYear : true
});


function addIndentDetailsWith(e) {
	var errorList = [];
	errorList = validateUniqueItem();
    errorList = validateWithHeld(errorList);
	
	if (errorList.length > 0) { 
	      $("#errorDiv").show();
	      $("#errorDiv").removeClass('hide');
	       displayErrorsOnPage(errorList);
	 } else{
		var count = $('#initialTable tr').length - 1;
		  
	        	$("#errorDiv").addClass('hide');
						var clickedRow = $(this).parent().parent().index();
						var content = $('#initialTable tr').last().clone();
						$('#initialTable tr').last().after(content);
						//content.find("input:text").attr("value", "");
						content.find("select").val("");
						content.find("input:text").val('');
						content.find("input:hidden").val('');
						content.find('div.chosen-container').remove();
			            content.find("select:eq(0)").chosen().trigger("chosen:updated");
						reOrderMachineryDetails();
					}
		
}

function validateWithHeld(errorList){
   $('.withHeld').each(function(i) {
	     var itemid = $("#itemid" + i).val();
		 var quantity = $("#quantity" + i).val();
		 var no = i+1;	
		 if(itemid =="" || itemid =="0"){
			 errorList.push(getLocalMessage("material.management.item.name")+" "+no);
		 }
		 if(quantity == "0" || quantity == ""){
			 errorList.push(getLocalMessage("material.management.quantity.validation")+" "+no); 
		 }
   });
   return errorList;
}

function reOrderMachineryDetails(){
	$('.withHeld').each(function(i){		
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find("select:eq(0)").attr("id","itemid" + (i));	
		$(this).find("input:text:eq(2)").attr("id" ,"uom" + (i));
		$(this).find("input:text:eq(3)").attr("id" ,"quantity" + (i));

        $("#sNo"+i).val(i+1);
		$(this).find("select:eq(0)").attr("name","indentProcessDTO.item[" + i+ "].itemid").attr("onchange","getUom(" + i + ")");
		$(this).find("input:text:eq(2)").attr("name","indentProcessDTO.item[" + i+ "].uom");
		$(this).find("input:text:eq(3)").attr("name","indentProcessDTO.item[" + i+ "].quantity");		
	});
}

function deleteEntry(obj, ids) {
	deleteTableRow('initialTable', obj, ids);
	if ($.fn.DataTable.isDataTable('#initialTable')) {
		$('#initialTable').DataTable().destroy();
	}
}


function getEmpDetails() {
	var indenter = $("#indenter").val();
	if (indenter == "" || indenter == null || indenter == undefined || indenter == 0) {
		$('#deptId').val('');
		$('#deptName').val('');
		$('#desgId').val('');
		$('#desgName').val('');
		$('#reportingmgr').val('');
		$('#reportingMgrName').val('');
	} else {
		var requestData = "indenter=" + $("#indenter").val();
		var object = __doAjaxRequest("IndentProcess.html?getEmpDetail", 'POST', requestData, false, 'json');
		$.each(object, function(key, value) {
			if (key == 'deptId') {
				$('#deptId').val(value[0]);
				$('#deptName').val(value[1]);
			}
			if (key == 'desgId') {
				$('#desgId').val(value[0]);
				$('#desgName').val(value[1]);
			}
			if (key == 'reportingmgr') {
				$('#reportingmgr').val(value[0]);
				$('#reportingMgrName').val(value[1] + " " + value[2]);
			}
		});
	}
}


function getLocation() {
	var storeid = $("#storeid").val();
	var requestData = "storeid=" + $("#storeid").val();
	if (storeid == "") {
		$('#location').val('');
		$('#locationName').val('');
	} else {
		var location = __doAjaxRequest("IndentProcess.html?getLocation", 'POST', requestData, false, 'json');	
		$.each(location, function(key, value) {
			$('#location').val(key);
			$('#locationName').val(value);
		});
	}
}


function getUom(i) {
	var errorList = validateUniqueItem();
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		var itemid = $("#itemid" + i).val();
		var requestData = "itemid=" + itemid;
		if (itemid == "")
			$('#uom' + i).val("");
		else {
			var uom = __doAjaxRequest("IndentProcess.html?getUom", 'POST', requestData, false, 'json');
			$('#uom' + i).val(uom);
		}
	}
}


function getIndentDataById(formUrl, actionParam, indentid) {
	var data = {
		"indentid" : indentid
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

$('.beneficiaryPattern').on('keyup', function() {
    this.value = this.value.replace(/[^a-z A-Z , & ' -]/g,'');
});


function validateUniqueItem() {
	var errorList = [];
	var itemArray = [];
	$('.withHeld').each(function(i) {
		var itemId = $("#itemid" + i).val();
		if (itemArray.includes(itemId))
			errorList.push(getLocalMessage("purchase.requisition.duplicate.item.validate") + " " + (i + 1));
		itemArray.push(itemId);
	});
	if (errorList.length > 0) {
		$("#errorDiv").show();
		$("#errorDiv").removeClass('hide');
		displayErrorsOnPage(errorList);
	}
	return errorList;
}
