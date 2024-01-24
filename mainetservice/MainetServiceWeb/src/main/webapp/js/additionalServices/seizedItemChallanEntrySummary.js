$(document).ready(function() {
	
	$('#datatables').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true
	});
	
	$('.datetimepicker').datetimepicker({
		format: 'LT'
	});
	
	
	$(".currentDatePicker").datepicker({                        
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd/mm/yy',  
        maxDate : new Date(),       
	});
	
	$("#challanFromDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});
	$('#challanFromDate').attr('placeholder','');
	
	$("#challanToDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d'
	});
	$('#challanToDate').attr('placeholder','');
	
	$("#raidDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d'
	});
	$('#raidDate').attr('placeholder','');
});


function validateSearchForm(errorList) {
	var errorList = [];
	if($("#challanFromDate").val( ) != '' && 
			$("#challanToDate").val() == ''){
		errorList.push(getLocalMessage("EChallan.validateChallanToDate"));
	}
	
	if($("#challanToDate").val( ) != '' && 
			$("#challanFromDate").val() == ''){
		errorList.push(getLocalMessage("EChallan.validateChallanFromDate"));
	}
	
	if($("#raidNo").val()=='' && $("#offenderName").val()=='' && $("#challanFromDate").val()=='' 
		&& $("#challanToDate").val()=='' && $("#offenderMobNo").val()=='' ){
		errorList.push(getLocalMessage("EChallan.selectCriteria"));
	}
	return errorList;
}

/********************SearchFunction************/
function searchRaidData(formUrl, actionParam) {
	showloader(true);
	setTimeout(function() {
		var errorList = [];
		var data = {
			"raidNo" : $('#raidNo').val(),
			"offenderName" : $('#offenderName').val(),
			"challanFromDate" : $('#challanFromDate').val(),
			"challanToDate" : $('#challanToDate').val(),
			"offenderMobNo" : $('#offenderMobNo').val()
		};

		errorList = validateSearchForm();

		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}
		showloader(false);
	}, 200);
}

function getActionForDefination(challanId, mode) {
	var divName = '.content-page';
    var requestData = {
     "challanId" : challanId,
	 "saveMode" : mode
    };
    var ajaxResponse = doAjaxLoading('SeizedItemChallanEntry.html?ViewForm', requestData,
    	    'html', divName);
        $(divName).removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
}

function proceedSave(element) {
	var errorList = [];
	errorList = validateDetailsForm(element);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,"Raid Number Saved Successfully", 
					'SeizedItemChallanEntry.html?printAcknowledgement', 'saveform');
	}
}

/********** Print button ***************/
function printContent(challanId) {
	var divName = '.content-page';
    var requestData = {
	"challanId" : challanId
    };
    var ajaxResponse = doAjaxLoading('SeizedItemChallanEntry.html?receiptDownload', requestData,
    	    'html', divName);
        $(divName).removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
}

/******************** reset button ************/
function resetForm() {
	$('#raidNo').val('');
	$('#offenderName').val('');
	$('#challanFromDate').val('');
	$('#challanToDate').val('');
	$('#offenderMobNo').val('');
	
	$('.error-div').hide();
}

/*********** Action Button ************/
function addRow(){
	
	 var errorList = [];
	 $("#errorDiv").hide();
	 errorList = validateDetailsForm(errorList);
	 if (errorList.length == 0) {

   var rowCount = $('#itemTable tr').length;
   $('#itemTable tbody tr:first').clone().insertAfter('#itemTable tbody tr:last');
   
   $('.hasNumber').on('keyup', function () {
	    this.value = this.value.replace(/[^0-9]/g,'');  
	});
   
   rowCount= rowCount-1;
   $('#itemTable tr:last select[id=itemName0]').val('');
   $('#itemTable tr:last textarea[id=itemDesc0]').val('');
   $('#itemTable tr:last input[id=itemQuantity0]').val(0);
   $('#itemTable tr:last input[id=storeId0]').val('');
   
   $('#itemTable tr:last select[id=itemName0]').attr('name','challanMasterDto.echallanItemDetDto['+rowCount+'].itemName');
   $('#itemTable tr:last select[id=itemName0]').attr('id','itemName'+rowCount);

   $('#itemTable tr:last textarea[id=itemDesc0]').attr('name','challanMasterDto.echallanItemDetDto['+rowCount+'].itemDesc');
   $('#itemTable tr:last textarea[id=itemDesc0]').attr('id','itemDesc'+rowCount);

   $('#itemTable tr:last input[id=itemQuantity0]').attr('name','challanMasterDto.echallanItemDetDto['+rowCount+'].itemQuantity');
   $('#itemTable tr:last input[id=itemQuantity0]').attr('id','itemQuantity'+rowCount);
   
   $('#itemTable tr:last input[id=storeId0]').attr('name','challanMasterDto.echallanItemDetDto['+rowCount+'].storeId');
   $('#itemTable tr:last input[id=storeId0]').attr('id','storeId'+rowCount);

   $('#itemTable tr:last a#addrow0').attr('id','addrow'+rowCount);
   $('#itemTable tr:last a#removeRow0').attr('onclick','removeRow('+rowCount+')');
   $('#itemTable tr:last a#removeRow0').attr('id','removeRow'+rowCount);
   
   }else{
   	$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		
		return false;
   }
} 



function removeRow(rowNum) {

	rowNum = rowNum + 1;
	var rowCount = $('#itemTable tr').length;
	if (rowCount > 2) {
		$("#itemTable tr:eq(" + rowNum + ")").remove();
		reOrderTable('itemTable');
	} else {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
	}
}

var removeIdArray = [];
$("#itemTable").on( 'click', '.deleteItemDetRow', function() {
	var rowCount = $('#itemTable tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}
	$(this).closest('#itemTable tr').remove();
	reOrderTable('.firstItemRow'); 
	id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
	if (id != '') {
		removeIdArray.push(id);
	}
	$('#removeItemIds').val(removeIdArray);
});

//function reOrderTable(tableName){
// 
//     var tableId="#"+tableName;
//     var rowCount = $('#itemTable tr').length;
//    
//     var reTable = $(tableId+'tr');
//     for (var i=0; i<=(rowCount); i++){
//
//         $('#itemTable tr').eq(i+1).find('select[id^=itemName]').attr('name','challanMasterDto.echallanItemDetDto['+i+'].itemName');
//         $('#itemTable tr').eq(i+1).find('select[id^=itemName]').attr('id','itemName'+i);
//         
//         $('#itemTable tr').eq(i+1).find('textarea[id^=itemDesc]').attr('name','challanMasterDto.echallanItemDetDto['+i+'].itemDesc');
//         $('#itemTable tr').eq(i+1).find('textarea[id^=itemDesc]').attr('id','itemDesc'+i);
//        
//         $('#itemTable tr').eq(i+1).find('input[id^=itemQuantity]').attr('name','challanMasterDto.echallanItemDetDto['+i+'].itemQuantity');
//         $('#itemTable tr').eq(i+1).find('input[id^=itemQuantity]').attr('id','itemQuantity'+i);
//                
//         $('#itemTable tr').eq(i+1).find('input[id^=storeId]').attr('name','challanMasterDto.echallanItemDetDto['+i+'].storeId');
//         $('#itemTable tr').eq(i+1).find('input[id^=storeId]').attr('id','storeId'+i);
//        
//         $('#itemTable tr').eq(i+1).find('a[id^=addrow]').attr('id','addrow'+i);
//         $('#itemTable tr').eq(i+1).find('a[id^=deleteItemDetRow]').attr('id','removeRow'+i);
//         //$('#itemTable tr').eq(i+1).find('a[id^=removeRow]').attr('onclick','removeRow('+i+')');
//     }
// }

function reOrderTable(firstItemRow){
	 
	$(firstItemRow).each(
			function(i) {
				$(this).find("input:text:eq(0)").attr("id", "itemId" + i);
				$(this).find("select:eq(0)").attr("id", "itemName" +i);			
				$(this).find("textarea:eq(0)").attr("id", "itemDesc" + i);
				$(this).find("input:text:eq(1)").attr("id", "itemQuantity" + i);
				$(this).find("input:text:eq(2)").attr("id", "storeId" + i);
						
				//$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("input:text:eq(0)").attr("name","challanMasterDto.echallanItemDetDto[" + i + "].itemId");
				$(this).find("select:eq(0)").attr("name","challanMasterDto.echallanItemDetDto[" + i + "].itemName");
				$(this).find("textarea:eq(0)").attr("name","challanMasterDto.echallanItemDetDto[" + i + "].itemDesc");
				$(this).find("input:text:eq(1)").attr("name","challanMasterDto.echallanItemDetDto[" + i + "].itemQuantity");
				$(this).find("input:text:eq(2)").attr("name","challanMasterDto.echallanItemDetDto[" + i + "].storeId");
			});
}

/**************** table validation **************/
function validateDetailsForm(element) {
	var errorList = [];
	$('.firstItemRow')
	.each(
			function(i) {
				
				var itemNameCode= $("#itemName" + i).find('option:selected').attr('code');
				var itemName = $.trim($("#itemName" + i).val());
				if (itemName == null || itemName == ""){
					errorList.push(getLocalMessage("EChallan.enterItemName.forRow")+" "+(i+1));
				}
				
				var itemDesc = $.trim($("#itemDesc" + i).val());
				if (itemNameCode == 'OTH' && (itemDesc == null || itemDesc == "" || itemDesc == undefined)){
					errorList.push(getLocalMessage("EChallan.enterItemDesc.forRow")+ " " + (i + 1));
				}
				
				var itemQuantity = $.trim($("#itemQuantity" + i).val());
				if (itemQuantity == null || itemQuantity == "" || itemQuantity == 0){
					errorList.push(getLocalMessage("EChallan.enterItemQuantity.forRow")+ " " + (i + 1));
				}
		
				var storeId = $.trim($("#storeId" + i).val());
				if (storeId == null || storeId == ""){
					errorList.push(getLocalMessage("EChallan.enterStorageLocation.forRow")+ " " + (i + 1));
				}
				
				
	});
	return errorList;
}

