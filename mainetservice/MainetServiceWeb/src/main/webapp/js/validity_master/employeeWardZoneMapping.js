
$(document).ready(function() {
	
	$('#EmployeeWardZoneMapping').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	
	  $("#hoardingDetailTable").dataTable({
			"oLanguage" : {
			    "sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
		    });
	  reOrderAdvertisingIdSequence('.advertisingDetailsClass');
});

function addEntryData(tableId) {
    
    var errorList = [];
    if (errorList.length == 0) {
	$("#errorDiv").hide();
	addTableRow(tableId, false);

    } else {
	displayErrorsOnPage(errorList);
    }
}



function saveEmployeeWardMapping(element) {
    
    var errorList = [];
    var empId = $("#empId").val();
    if (empId == '' || empId == undefined || empId == '0') {
		errorList.push(getLocalMessage("Please select employee"));
	}
    errorList = validateWardZoneMapDetails(errorList);
    if (errorList.length > 0) {
	displayErrorsOnPage(errorList);
    } else {
	return saveOrUpdateForm(element,
		"hjgytgyu",
		'EmployeeWardZoneMapping.html', 'saveform');
    }

}



var idcount;
function reOrderAdvertisingIdSequence(advertisingDetailsClass){
	$(advertisingDetailsClass).each(function(i) {
		var utp = i;
		if (i > 0) {
			utp = i * 6;
		}
		$(this).find("select:eq(0)").attr("id", "ward" + (utp + 1));
		$(this).find("select:eq(1)").attr("id", "ward" + (utp + 2));
		$(this).find("select:eq(2)").attr("id", "ward" + (utp + 3));
		$(this).find("select:eq(3)").attr("id", "ward" + (utp + 4));
		$(this).find("select:eq(4)").attr("id", "ward" + (utp + 5));
	
		
		//NAME
		$(this).find("select:eq(0)").attr("name", "employeeWardZoneMapDto.wardZoneDetalList[" + i + "].ward1");
		$(this).find("select:eq(1)").attr("name", "employeeWardZoneMapDto.wardZoneDetalList[" + i + "].ward2");
		$(this).find("select:eq(2)").attr("name", "employeeWardZoneMapDto.wardZoneDetalList[" + i + "].ward3");
		$(this).find("select:eq(3)").attr("name", "employeeWardZoneMapDto.wardZoneDetalList[" + i + "].ward4");
		$(this).find("select:eq(4)").attr("name", "employeeWardZoneMapDto.wardZoneDetalList[" + i + "].ward5");
		

		idcount = i;
	});
}


$(function() {
	
	/* To add new Row into table */
	$("#wardZoneMappingTable").on('click', '.delButton', function() {
		
		if ($("#wardZoneMappingTable tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderAdvertisingIdSequence('.advertisingDetailsClass'); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("adh.first.row.not.remove"));
			displayErrorsOnPage(errorList);
		}
	});
});



$(function() {
	$("#wardZoneMappingTable").on('click', '.addEntryData', function(e) {
		var errorList = [];
		errorList = validateWardZoneMapDetails(errorList);
		if (errorList.length == 0) {
			$('.error-div').hide();
			e.preventDefault();	
			var content = $("#wardZoneMappingTable").find('tr:eq(1)').clone();
			$("#wardZoneMappingTable").append(content);
			content.find("select").val('0');
			
			reOrderAdvertisingIdSequence('.advertisingDetailsClass');
			$("#ward" + (idcount * 6 + 2) + " option:not(:first)").remove();
			$("#ward" + (idcount * 6 + 3) + " option:not(:first)").remove();
			$("#ward" + (idcount * 6 + 4) + " option:not(:first)").remove();
			$("#ward" + (idcount * 6 + 5) + " option:not(:first)").remove();
			$("#ward" + (idcount * 6 + 6) + " option:not(:first)").remove();
			
		}else{
			displayErrorsOnPage(errorList);
		}	
		});
	});



function validateWardZoneMapDetails(errorList){
	
	var rowCount = $('#wardZoneMappingTable tr').length;
	var waste = [];
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	$('.advertisingDetailsClass').each(function(i) {
			if (rowCount <= 2) {
					var ward1 = $("#ward" + 1).val();
					
				var level = 1;
		} else {
				var utp = i;
				utp = i * 6;
				var ward1 = $("#ward" + (utp + 1)).val();
			  
	     	var level = i + 1;
		}
		if (ward1 == '' || ward1 == undefined || ward1 == '0') {
				errorList.push(getLocalMessage("Please select zone")+" " +level);
			}
		
	});				
	return errorList;
  }


function addWardZoneMapping(formUrl, actionParam) {
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}


function viewWardZoneMapping(contId, saveMode) {

    var requestData = {
	'empId' : contId,
	'saveMode' : saveMode
    }
    var ajaxResponse = __doAjaxRequest(
	    'EmployeeWardZoneMapping.html?EDIT', 'POST',
	    requestData, false, 'html');
    $('.content-page').html(ajaxResponse);
}

function editwWardZoneMapping(contId, saveMode) {

    var requestData = {
	'empId' : contId,
	'saveMode' : saveMode
    }
    var ajaxResponse = __doAjaxRequest(
	    'EmployeeWardZoneMapping.html?EDIT', 'POST',
	    requestData, false, 'html');
    $('.content-page').html(ajaxResponse);
}

function backHomeCompostingForm() {
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'MainetService.html');
    $("#postMethodForm").submit();
}