$(document).ready(function() {
	$(function() {
		$('.datetimepicker3').timepicker();
	});
	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	$("#id_segregationTable").dataTable({
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
	sum();
});

function openaddSegregation(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function Proceed(element) {
	
	var errorList = [];
	errorList = ValidateSegregation(errorList);
	validateUnitDetailTable(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element,
				getLocalMessage('swm.saveSegregation'), 'Segregation.html','saveform');
	}
}



function ValidateSegregation(errorList) {
	
	var grId = $("#grId").val();
	var deId = $("#deId").val();
	var date = $("#grDate").val();
	var ttlgrbg = $("#ttlgrbg").val();
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	if (deId == "0" || deId == null)
		errorList.push(getLocalMessage("swm.validation.deId"));
	if (date == "" || date == null)
		errorList.push(getLocalMessage("swm.validation.date"));
	return errorList;
}

function modifySegregation(segId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : segId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	reOrderUnitTabIdSequence('.firstUnitRow');
	prepareTags();
};

function sum() {
	
	var i = 0;
	var sumadd = 0;
	$("#id_segregationTbl tbody tr").each(function(i) {
		var segVolume = $("#segVolume" + i).val();
		sumadd = parseFloat(sumadd) + parseFloat(segVolume);
	});
	if (isNaN(sumadd)) {
		$('#id_total').val(0);
	} else {
		$('#id_total').val(sumadd);
	}
}

function searchSegregation(formUrl, actionParam) {
	
	var data = {
		"id" : replaceZero($('#deId').val()),
		"fromDate" : $('.fromDate').val(),
		"toDate" : $('.toDate').val(),
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}

$(function() {
	/* To add new Row into table */
	$("#id_segregationTbl").on('click', '.addCF', function() {
		
		var errorList = [];
		errorList = validateUnitDetailTable(errorList);
		if (errorList.length == 0) {
			var content = $("#id_segregationTbl").find('tr:eq(2)').clone();
			$("#id_segregationTbl").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val('0');
			$('.error-div').hide();
			reOrderUnitTabIdSequence('.firstUnitRow'); // reorder id and Path
			$("#codWast" + (idcount * 6 + 2) + " option:not(:first)").remove();
			$("#codWast" + (idcount * 6 + 3) + " option:not(:first)").remove();
			$("#codWast" + (idcount * 6 + 4) + " option:not(:first)").remove();
			$("#codWast" + (idcount * 6 + 5) + " option:not(:first)").remove();
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});
var idcount;
function reOrderUnitTabIdSequence(firstRow) {
	$(firstRow).each(
			function(i) {
				
				var utp = i;
				if (i > 0) {
					utp = i * 6;
				}
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i).val(i + 1);
				$(this).find("select:eq(0)").attr("id", "codWast" + (utp + 1));
				$(this).find("select:eq(1)").attr("id", "codWast" + (utp + 2));
				$(this).find("select:eq(2)").attr("id", "codWast" + (utp + 3));
				$(this).find("select:eq(3)").attr("id", "codWast" + (utp + 4));
				$(this).find("select:eq(4)").attr("id", "codWast" + (utp + 5));
				$(this).find("input:text:eq(1)").attr("id", "segVolume" + i);
				// names
				$(this).find("input:text:eq(0)").attr("name", i);
				$(this).find("select:eq(0)").attr("name","segregationDto.tbSwWastesegDets[" + i + "].codWast1");
				$(this).find("select:eq(1)").attr("name","segregationDto.tbSwWastesegDets[" + i + "].codWast2");
				$(this).find("select:eq(2)").attr("name","segregationDto.tbSwWastesegDets[" + i + "].codWast3");
				$(this).find("select:eq(3)").attr("name","segregationDto.tbSwWastesegDets[" + i + "].codWast4");
				$(this).find("select:eq(4)").attr("name","segregationDto.tbSwWastesegDets[" + i + "].codWast5");
				$(this).find("input:text:eq(1)").attr("name","segregationDto.tbSwWastesegDets[" + i + "].tripVolume");
				idcount = i;
			});
}

function validateUnitDetailTable(errorList) {
	
	var rowCount = $('#id_segregationTbl tr').length;
	var waste = [];
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	$('.firstUnitRow').each(
					function(i) {
						
						if (rowCount <= 2) {
							var codWast1 = $("#codWast" + 1).val();
							var codWast2 = $("#codWast" + 2).val();
							var codWast3 = $("#codWast" + 3).val();
							var codWast4 = $("#codWast" + 4).val();
							var codWast5 = $("#codWast" + 5).val();
							var segVolume = $("#segVolume" + i).val();
							var level = 1;
						} else {
							var utp = i;
							utp = i * 6;
							var codWast1 = $("#codWast" + (utp + 1)).val();
							var codWast2 = $("#codWast" + (utp + 2)).val();
							var codWast3 = $("#codWast" + (utp + 3)).val();
							var codWast4 = $("#codWast" + (utp + 4)).val();
							var codWast5 = $("#codWast" + (utp + 5)).val();
							var segVolume = $("#segVolume" + i).val();
							if (codWast5 != undefined) {
								waste.push($("#codWast" + (utp + 5)).val())
							} else {
								if (codWast4 != undefined) {
									waste.push($("#codWast" + (utp + 4)).val())
								} else {
									waste.push($("#codWast" + (utp + 3)).val())
								}
							}
							var level = i + 1;
						}
						if (codWast1 == '' || codWast1 == undefined || codWast1 == '0') {
							errorList.push(getLocalMessage("swm.validation.wastType")+ level);
						}
						if (codWast2 == '' || codWast2 == undefined || codWast2 == '0') {
							errorList.push(getLocalMessage("swm.validation.wastSubType" + " ")+ level);
						}
						if (codWast3 == '' || codWast3 == undefined || codWast3 == '0') {
							errorList.push(getLocalMessage("swm.validation.wastSubType2"+ " ")+ level);
						}
						if (segVolume == '' || segVolume == undefined || segVolume == '0') {
							errorList.push(getLocalMessage("swm.validation.segVolume"+ " ")+ level);
						}
					});
	var i = 0;
	var j = 1;
	var k = 1;
	var count = 0;
	for (i = 0; i <= waste.length; i++) {
		for (j = k; j <= waste.length; j++) {
			if (waste[i] == waste[j]) {
				if (count == 0) {
					errorList
							.push(getLocalMessage("swm.validation.wastSubType2change")
									+ (j + 1));
				}
				count++;
			}
		}
		k++;
	}
	return errorList;
}

function getDisposal() {
	
	var date = $("#grDate").val();
	var deId = $("#deId").val();
	if (date != '0' && deId != '0') {
		var data = {
			"date" : date,
			"id" : deId
		};
		var URL = 'Segregation.html?disposalVolume';
		var returnData = __doAjaxRequest(URL, 'POST', data, false, 'json');

		if (isNaN(returnData) || returnData == "Internal Server Error.") {
			$('#ttlgrbg').val(0);
		}
		$("#ttlgrbg").val(returnData);
	}
}
function deleteTableRow2(tableId, rowObj, deletedId) {
	
	var id = "#" + tableId;
	var error = [];
	// remove datatable specific properties
	if ($.fn.DataTable.isDataTable(id)) {
		$(id).DataTable().destroy();
		$(".datepicker").datepicker("destroy");
	}
	var rowCount = $("" + id + " tbody tr").length;
	// if rowCount is 1, it means only one row present
	if (rowCount != 1) {
		var deletedSorId = $(rowObj).closest('tr').find('input[type=hidden]:first').attr('value');
		$(rowObj).closest('tr').remove();
		if (deletedSorId != '') {
			var prevValue = $('#' + deletedId).val();
			if (prevValue != '')
				$('#' + deletedId).val(prevValue + "," + deletedSorId);
			else
				$('#' + deletedId).val(deletedSorId);
		}
		reOrderUnitTabIdSequence('.firstUnitRow');
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true
		});
		// adding datatable specific properties
		dataTableProperty(id);
	}
}
function deleteEntry2(tableId, obj, ids) {
	if (ids != "1") {
		deleteTableRow2(tableId, obj, ids);
	}

}

$(".resetBtn").click(function() {
	$('#empId').val("").trigger("chosen:updated");

});