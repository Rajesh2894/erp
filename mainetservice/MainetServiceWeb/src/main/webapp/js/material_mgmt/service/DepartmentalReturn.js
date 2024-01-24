$(document).ready(function() {
	
	$("#deptIndentReturnTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});
	
	var dreturndate =$('#dreturndate').val();
	if (dreturndate) {
		$('#dreturndate').val(dreturndate.split(' ')[0]);
	}
	
	$('#applicableYes').on('click', function() {
		$('#indentno').show();
		$('#indentItems').show();
	});
	$('#ApplicableNo').on('click', function() {
		$('#indentno').hide();
		$('#indentItems').hide();
	});
	
	$(".fromDateClass").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate : new Date(),
		onSelect: function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	
	$(".toDateClass").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate : new Date(),
		onSelect: function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});
	

	$(".firstUnitRow").each(function(i) {
		if ($("#isdisposal" + i).prop('checked', true).val() == 'Y') {
			$("#isdisposal" + i).prop('checked', true);
		} else {
			$("#isdisposal" + i).prop('checked', false);
		}
	});

});


function addDeptReturn(formUrl, actionParam) {
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}


function submitIndentReturnForm(obj) {
	var errorList = [];
	if (1 == $('#levelcheck').val())
		errorList = ValidateIndentApproval(errorList);
	errorList = validateItemTable(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else
		return saveOrUpdateForm(obj, "Successful", 'DeptReturn.html', 'saveform');
}


function validateItemTable(errorList) {
	var indenter = $('#indenter').val();
	var dreturndate = $('#dreturndate').val();
	var storeid = $('#storeid').val();
	if (dreturndate == "" || dreturndate == undefined)
		errorList.push(getLocalMessage('material.management.return.date.valid'));
	if (indenter == "" || indenter == undefined)
		errorList.push(getLocalMessage('material.management.validation.employee'));
	if (storeid == "" || storeid == undefined)
		errorList.push(getLocalMessage('department.indent.validation.store'));

	$("#initialTable tbody tr").each(function(i) {
		var returnqty = $("#returnqty" + i).val();
		var issueqty = $("#issueqty" + i).val();
		var itemcondition = $("#itemcondition" + i).val();
		var reasonforreturn = $("#reasonforreturn" + i).val();
		var BinId = $("#BinId" + i).val();
		var notForDisposal = !$("#isdisposal" + i).is(':checked');
		var no = i + 1;

		if (returnqty == "0" || returnqty == "")
			errorList.push(getLocalMessage("material.management.validation.return.quantity") + " " + no);
		if(parseFloat(returnqty) > parseFloat(issueqty))
			errorList.push(getLocalMessage("material.management.return.quantity.validation") + " " + no);		
		if (itemcondition == "0" || reasonforreturn == "")
			errorList.push(getLocalMessage("material.management.validation.item.condition") + " " + no);
		if (reasonforreturn == "0" || reasonforreturn == "")
			errorList.push(getLocalMessage("material.management.validation.reasonforreturn") + " " + no);
		if ('true' == $('#lastChecker').val()) {
			if (notForDisposal && (BinId == "0" || BinId == "" ||  BinId == undefined))
				errorList.push(getLocalMessage("material.management.return.disposal.bin.validate") + " " + no);
		}
	});
	return errorList;
}


$('#dreturndate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '0d',
	changeYear : true,
	pickTime: false
});


function deleteEntry(obj, ids) {
    deleteTableRow('initialTable', obj, ids);
    
    if ($.fn.DataTable.isDataTable('#initialTable')) {
        var table = $('#initialTable').DataTable();
        var row = $(obj).closest('tr'); // Find the row element
        table.row(row).remove().draw(); // Remove the row and redraw the table
        
        // Reorder rows after deletion
        var rows = table.rows().nodes(); // Get all rows
        $(rows).each(function(index, row) {
            $(row).find('td:first-child').text(index + 1); // Update the Sr.No. column
        });
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
		var object = __doAjaxRequest("DeptReturn.html?getEmpDetail", 'POST', requestData, false, 'json');
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

function searchIndentData(element) {   
	var errorList = [];
	var indentid = $('#indentid').val();
	if (indentid == "" || indentid == null || indentid == undefined || indentid =='0' )
		errorList.push(getLocalMessage("material.management.validation.Indent.No"));
	if (errorList.length == 0) {
		var requestData = $('form').serialize();
		var ajaxResponse = doAjaxLoading('DeptReturn.html?searchindentData', requestData, 'html', '');
		$('.content').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse); 
	}else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}	
}


function IndetNoEmpDetails() {
	$('#indentid').html("");
	$("#indentid").trigger('chosen:updated');
	var indenter = $("#indenter").val();

	if (indenter != "0" && indenter != "" && indenter != undefined) {
		var data = {
			"indenter": indenter
		};

		var ajaxResponse = __doAjaxRequest('DeptReturn.html?IndetNoEmpDetails', 'POST', data, false, 'json');
		if ($('#indentid option[value="0"]').length === 0) {
			var newOption = '<option value="0" selected="selected">Select</option>';
			$('#indentid').prepend(newOption);
		}
		$.each(ajaxResponse, function(key, value) {
			$('#indentid').append($("<option></option>").attr("value", key).text(value));
		});
		$('#indentid').trigger("chosen:updated");
	}

}


function getIndentDetail() {
	var indentid = $("#indentid").val();

	if (indentid != "0" && indentid != "" && indentid != undefined) {
		var data = { "indentid": indentid };
		var ajaxResponse = __doAjaxRequest('DeptReturn.html?getIndentDetail', 'POST', data, false, 'json');
		$.each(ajaxResponse, function(key, value) {
			if (key == 'locId') {
				$('#locId').val(value[0]);
				$('#locationName').val(value[1]);
			}
			if (key == 'storeId') {
				$('#storeid').val(value[0]);
				$('#storeName').val(value[1]);
			}
			if (key == 'beneficiary') {
				$('#beneficiary').val(value[0]);
			}
		});
	}
}

function CheckReturnQuantity() {
	var errorList = [];
	$("#initialTable tbody tr").each(function(i) {
		var prereceived = BigInt($("#prereceived" + i).val());
		var returnqty = BigInt($("#returnqty" + i).val());
		var issueqty = BigInt($("#issueqty" + i).val());
		var no = i + 1;

		var quantity = issueqty - prereceived;
		if (returnqty > quantity) {
			errorList.push(getLocalMessage("material.management.return.quantity.validation") + " " + no);
		}
	});
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}



function reOrderpurRequisitionDetTabIdSequence(firstUnitRow) {
	$(firstUnitRow).each(
		function(i) {
			$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
			$(this).find("input:text:eq(1)").attr("id", "itemDesc" + i);
			$(this).find("input:text:eq(2)").attr("id", "uomDesc" + i);
			$(this).find("input:text:eq(3)").attr("id", "itemno" + i);
			$(this).find("input:text:eq(4)").attr("id", "issueqty" + i);
			$(this).find("input:text:eq(5)").attr("id", "returnqty" + i);
			$(this).find("select:eq(0)").attr("id", "itemcondition" + i);
			$(this).find("select:eq(1)").attr("id", "reasonforreturn" + i);

			$(this).find("input:text:eq(0)").val(i + 1);
			$(this).find("input:text:eq(1)").attr("name", "deptReturnDto.deptItemDetailsDTOList[" + i + "].itemDesc");
			$(this).find("input:text:eq(2)").attr("name", "deptReturnDto.deptItemDetailsDTOList[" + i + "].uomDesc");
			$(this).find("input:text:eq(3)").attr("name", "deptReturnDto.deptItemDetailsDTOList[" + i + "].itemno");
			$(this).find("input:text:eq(4)").attr("name", "deptReturnDto.deptItemDetailsDTOList[" + i + "].issueqty");
			$(this).find("input:text:eq(5)").attr("name", "deptReturnDto.deptItemDetailsDTOList[" + i + "].reasonforreturn");
			$(this).find("select:eq(0)").attr("name", "deptReturnDto.deptItemDetailsDTOList[" + i + "].itemcondition");
			$(this).find("select:eq(1)").attr("name", "deptReturnDto.deptItemDetailsDTOList[" + i + "].reasonforreturn");
		});
}

function ValidateIndentApproval(errorList) {
	var decision=$("input[id='decision']:checked").val();
	if(decision == undefined || decision == '' )
		errorList.push(getLocalMessage("lgl.validate.decision"));
	if ($("#comments").val() == "")
		errorList.push(getLocalMessage("material.management.validate.Remark"))
	return errorList;
}


function delButton(obj,index) {
    var rowCount = $('#initialTable tr').length;
    if (rowCount <= 2) {
        var errorList = [];
        errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
        $("#errorDiv").show();
        displayErrorsOnPage(errorList);
        return false;
    } else {
        var deletedDetId = $("#primId" + index).val();
        if (deletedDetId != '') {
            requestData = {
                "id": deletedDetId
            };
            var response = __doAjaxRequest('DeptReturn.html?doDetailsDeletion', 'POST', requestData, false, 'json');
            deleteTableRow('initialTable', obj, index);
        }
    }  
}

function checkDisposal(i) {
	$(".firstUnitRow").each(function(i) {
		var BinLocId = $('#BinId' + i);
		var ChkBx = $("#isdisposal" + i).is(':checked');
		if (ChkBx) {
			$("#isdisposal" + i).prop('checked', true).val('Y');
			BinLocId.prop("disabled", true).trigger("chosen:updated");
		} else {
			$("#isdisposal" + i).prop('checked', false).val('N');
			BinLocId.prop("disabled", false).trigger("chosen:updated");
		}
	});
}


function searchIndentReturn() {
	var errorList = [];
	var dreturnno = $('#dreturnno').val();
	var indentid = $('#indentno').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var storeid = $('#storeid').val();	
	var status= $('#status').val();
	
	if((dreturnno == "" || dreturnno == null || dreturnno == undefined || dreturnno == "0") &&
			(indentid == "" || indentid == null || indentid == undefined || indentid =="0" ) &&
			(fromDate == "" || fromDate == null || fromDate == undefined) &&
			(toDate == "" || toDate == null || toDate == undefined)&&
			(storeid == "" || storeid == null || storeid == undefined || storeid == "0")&&
			(status == "" || status == null || status == undefined || status == "0")){
		errorList.push(getLocalMessage("grn.Select"));
	}
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		var data = {
			"dreturnno" : dreturnno,
			"indentid" : indentid,
			"fromDate" : fromDate,
			"toDate" : toDate,
			"storeid" : storeid,
			"status" : status,			
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('DeptReturn.html?searchIndentReturn', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function getIndentReturnDataById(formUrl, actionParam, dreturnid) {
	var data = {
		"dreturnid": dreturnid,
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

