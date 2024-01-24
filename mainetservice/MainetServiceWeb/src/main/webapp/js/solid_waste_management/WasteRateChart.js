$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
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
	$("#id_updateSaleDetailsTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});	
	yearLength();
});

function addEntryData(tableId) {
	
	var errorList = [];
	errorList = validateEntryDetails();
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow1(tableId);
	} else {
		displayErrorsOnPage(errorList);
	}	
	
}
function deleteEntry(tableId, obj, ids) {
	if (ids != "1") {
		deleteTableRow1(tableId, obj, ids);
	}
}
function addTableRow1(tableId) {
	
	var id = "#" + tableId;	
	var content = $('' + id + ' tr').last().clone();
	$('' + id + ' tr').last().after(content);	
	content.find("input:text").val('');
	content.find("select").val('');	
	reOrderTableIdSequence1(id);	
	$("#item" + (idcount) + " option:not(:first)").remove();
}

function reOrderTableIdSequence1(tableId){
	$("" + tableId + " tbody tr")
	.each(function(i) {
				
				// IDs	
				$(this).find("input:text:eq(0)").attr("id",	"sequence" + i).val(i+1);
				$(this).find("input:text:eq(1)").attr("id",	"wasteRate" + i);	
				$(this).find("select:eq(0)").attr("id", "Waste" + i);
				$(this).find("select:eq(1)").attr("id",	"item" + i);
				$(this).find("select:eq(0)").attr("onchange","showWasteList(" + i + ")");
							
				// names
				$(this).find("input:text:eq(0)").attr("name",	"wasteRateMasterDto.wasteRateList[" + i	+ "].SrNo");
				$(this).find("input:text:eq(1)").attr("name",	"wasteRateMasterDto.wasteRateList[" + i	+ "].wasteRate");
				$(this).find("select:eq(0)").attr("name",	"wasteRateMasterDto.wasteRateList[" + i	+ "].codWast1");
				$(this).find("select:eq(1)").attr("name",	"wasteRateMasterDto.wasteRateList[" + i	+ "].codWast");
				idcount=i;
			});
	$("#item"+last).val(0);	
}

var idcount;

function deleteTableRow1(tableId, rowObj, deletedId) {
	
	var id = "#" + tableId;
	var error = [];
	var rowCount = $("" + id + " tbody tr").length;
	if (rowCount != 1) {
		var deletedSorId = $(rowObj).closest('tr').find('input[type=hidden]:first').attr('value');
		$(rowObj).closest('tr').remove();	
		reOrderTableIdSequence1(id);	
	}
}

function validateEntryDetails(){
	var errorList = [];
	
	var i = 0;
	var waste = [];
	if ($.fn.DataTable.isDataTable('#id_updateSaleDetailsTable1')) {
		$('#id_updateSaleDetailsTable').DataTable().destroy();
	}
	$("#id_updateSaleDetailsTable1 tbody tr").each(
					function(i) {
						
						var itemList = $("#item" + i).val();
						var wasteType = $("#Waste" + i).val();
						if (itemList != undefined) {
						waste.push(itemList);
						}
						var rate = $("#wasteRate" + i).val();					
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (wasteType == "" || wasteType == "0" || wasteType == null) {
							errorList.push( getLocalMessage("swm.wasteRate.wasteType") + rowCount);
						}
						if (itemList == "" || itemList == "0" || itemList == null) {
							errorList.push( getLocalMessage("swm.wasteRate.item") + rowCount);
						}					
						if (rate == "" || rate == null) {
							errorList.push( getLocalMessage("swm.wasteRate.validation.rate") + rowCount);
						}
					});
	 
	var j = 1, i = 0, k = 1, count = 0;
	for (i = 0; i <= waste.length; i++) {
		for (j = k; j <= waste.length; j++) {
			if (waste[i] == waste[j]) {
				if (count == 0) {
					errorList.push(getLocalMessage("swm.wasteRate.duplicate.validation1")+ (j + 1)+ getLocalMessage("swm.wasteRate.duplicate.validation2"));
				}
				count++;
			}
		}
		k++;
	}
	
	return errorList;
}



function Proceed(element) {
	
	var errorList = [];	
	errorList = ValidateTripSheetMaster(errorList);
	errorList = errorList.concat(validateEntryDetails());
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, getLocalMessage("swm.wasteRate.save"), 'WasteRateChart.html','saveform');
	}
}
function ValidateTripSheetMaster(errorList){
	var fromDate = $("#wFromDate").val();
	var toDate = $("#wToDate").val();
	//var wasteType = $("#wasteType").val();
	if (fromDate == "" || fromDate == null)
		errorList.push( getLocalMessage("swm.validation.emsFromdate"));
	if (toDate == "" || toDate == null)
		errorList.push( getLocalMessage("swm.validation.emsTodate"));	
	return errorList;
}
function addRateMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function modifyWasteRateList(wstId, formUrl, actionParam, mode) {
	
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : wstId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function yearLength(){
	var frmdateFields = $('#wFromDate');
	var todateFields = $('#wToDate');
	frmdateFields.each(function () {
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
    todateFields.each(function () {
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}
function showWasteList(rowNo){
	
	var  optionsAsString='';
	$('#item'+rowNo).html('');
	var wasteType = $("#Waste"+ rowNo + " option:selected").attr("value");
	if (wasteType != 0) {
		var data = {
			"wasteType" : wasteType
		};
		var URL = 'WasteRateChart.html?selectWasteType';
		var ajaxResponse = __doAjaxRequest(URL,	'POST', data, false, 'json');
		 optionsAsString += "<option value='0'> Select</option>";
		for(var i = 0; i < ajaxResponse.length; i++) {
		    optionsAsString += "<option value='" + ajaxResponse[i].lookUpId + "'>" + ajaxResponse[i].descLangFirst + "</option>";
		}
		$("#item"+rowNo).append( optionsAsString );
	}
}