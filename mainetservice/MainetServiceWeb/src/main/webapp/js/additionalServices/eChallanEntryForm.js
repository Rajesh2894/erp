$(document).ready(function() {
	
		$('.fancybox').fancybox();
		$(".myTable").dataTable({
			//your normal options
			"language": { "search": "" }, 
			"lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]]
		});
		$('.chosen-select').chosen();
		
		$("#proceed-btn").click(function () {
			$("#document-download").toggle();
			$(".btn-hide").hide();
		});
		
		$('.datetimepicker').datetimepicker({
			format: 'LT'
		});
		
		$("#challanDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d'
			});
		$('#challanDate').attr('placeholder','');
		
		if($(".challanType:checked").val() == "OS"){
		     
		      $('.challanAmt').show();
		      $('.challanDate').show();
		      $('.chkPay').hide();
		      $('.forSeizedItems').hide();
		      $('#chnPay').show();
		      $('.store').hide();
		    }else{

		      $('.challanAmt').hide();
		      $('.challanDate').hide();
		      $('.chkPay').show();
		      $('.forSeizedItems').show();
		      $('#chnPay').hide();
		      $('.store').show();
		    }
});

function addRow(){
	
	 var errorList = [];
	 $("#errorDiv").hide();
	 errorList = validateDetailsForm(errorList);
	 getOtherItemAmount();
	 if (errorList.length == 0) {
 
    var rowCount = $('#itemTable tr').length;
    $('#itemTable tbody tr:first').clone().insertAfter('#itemTable tbody tr:last');
    
    $('.hasNumber').on('keyup', function () {
	    this.value = this.value.replace(/[^0-9]/g,'');  
	});
    
    rowCount= rowCount-1;
    $('#itemTable tr:last input[id=srno0]').val(rowCount+1); 
    $('#itemTable tr:last select[id=itemName0]').val('');
    $('#itemTable tr:last textarea[id=itemDesc0]').val('');
    $('#itemTable tr:last input[id=itemQuantity0]').val(0);
    $('#itemTable tr:last input[id=storeId0]').val('');
    $('#itemTable tr:last input[id=challanAmt0]').val('');
	
    $('#itemTable tr:last input[id=srno0]').attr('name','srno'+rowCount);
    $('#itemTable tr:last input[id=srno0]').attr('id','srno'+rowCount);
    
    $('#itemTable tr:last select[id=itemName0]').attr('name','challanItemDetailsDtoList['+rowCount+'].itemName');
    $('#itemTable tr:last select[id=itemName0]').attr('id','itemName'+rowCount);

    $('#itemTable tr:last textarea[id=itemDesc0]').attr('name','challanItemDetailsDtoList['+rowCount+'].itemDesc');
    $('#itemTable tr:last textarea[id=itemDesc0]').attr('id','itemDesc'+rowCount);

    $('#itemTable tr:last input[id=itemQuantity0]').attr('name','challanItemDetailsDtoList['+rowCount+'].itemQuantity');
    $('#itemTable tr:last input[id=itemQuantity0]').attr('id','itemQuantity'+rowCount);
    
    $('#itemTable tr:last input[id=storeId0]').attr('name','challanItemDetailsDtoList['+rowCount+'].storeId');
    $('#itemTable tr:last input[id=storeId0]').attr('id','storeId'+rowCount);
    
    $('#itemTable tr:last input[id=challanAmt0]').attr('name','challanItemDetailsDtoList['+rowCount+'].itemAmount');
    $('#itemTable tr:last input[id=challanAmt0]').attr('id','challanAmt'+rowCount);

    $('#itemTable tr:last a#addrow0').attr('id','addrow'+rowCount);
    $('#itemTable tr:last a#removeRow0').attr('onclick','removeRow('+rowCount+')');
    $('#itemTable tr:last a#removeRow0').attr('id','removeRow'+rowCount);
    
    }else{
    	$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		
		return false;
    }
} 

function removeRow(rowNum){
  
    rowNum=rowNum+1;
    var rowCount = $('#itemTable tr').length;
    if(rowCount > 2){
      $("#itemTable tr:eq("+rowNum+")").remove();
      reOrderTable('itemTable');
      }else{

      }
  }

function reOrderTable(tableName){
  
      var tableId="#"+tableName;
      var rowCount = $('#itemTable tr').length;
     
      var reTable = $(tableId+'tr');
      for (var i=0; i<=(rowCount); i++){
          $('#itemTable tr').eq(i+1).find('input[name^=srno]').val(i+1);
          $('#itemTable tr').eq(i+1).find('input[name^=srno]').attr('name','srno'+i);
          $('#itemTable tr').eq(i+1).find('input[name^=srno]').attr('id','srno'+i);

          $('#itemTable tr').eq(i+1).find('select[id^=itemName]').attr('name','challanItemDetailsDtoList['+i+'].itemName');
          $('#itemTable tr').eq(i+1).find('select[id^=itemName]').attr('id','itemName'+i);
          
          $('#itemTable tr').eq(i+1).find('textarea[id^=itemDesc]').attr('name','challanItemDetailsDtoList['+i+'].itemDesc');
          $('#itemTable tr').eq(i+1).find('textarea[id^=itemDesc]').attr('id','itemDesc'+i);
         
          $('#itemTable tr').eq(i+1).find('input[id^=itemQuantity]').attr('name','challanItemDetailsDtoList['+i+'].itemQuantity');
          $('#itemTable tr').eq(i+1).find('input[id^=itemQuantity]').attr('id','itemQuantity'+i);
         
          
          $('#itemTable tr').eq(i+1).find('input[id^=storeId]').attr('name','challanItemDetailsDtoList['+i+'].storeId');
          $('#itemTable tr').eq(i+1).find('input[id^=storeId]').attr('id','storeId'+i);
          
          $('#itemTable tr').eq(i+1).find('input[id^=challanAmt]').attr('name','challanItemDetailsDtoList['+i+'].itemAmount');
          $('#itemTable tr').eq(i+1).find('input[id^=challanAmt]').attr('id','challanAmt'+i);
         
          $('#itemTable tr').eq(i+1).find('a[id^=addrow]').attr('id','addrow'+i);
          $('#itemTable tr').eq(i+1).find('a[id^=removeRow]').attr('id','removeRow'+i);
          $('#itemTable tr').eq(i+1).find('a[id^=removeRow]').attr('onclick','removeRow('+i+')');
      }
  }

function payModefields(){
	var value = $(".challanType:checked").val();
	resetFormForChallanTypeChange();
    if(value == "OS"){
     
      $('.challanAmt').show();
      $('.challanDate').show();
      $('.chkPay').hide();
      $('.forSeizedItems').hide();
      $('#chnPay').show();
      $('.store').hide();
 
    }else{

      $('.challanAmt').hide();
      $('.challanDate').hide();
      $('.chkPay').show();
      $('.forSeizedItems').show();
      $('#chnPay').hide();
      $('.store').show();

    }
    $("#challanType").val(value);
 }

function validateEntryForm(errorList) {
	var errorList = [];
	var mobileNo = $("#offenderMobNo").val();
	
	let emailId = $("#emailId").val();
	if (emailId != '' && emailId != undefined) {
		var filter = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(emailId)) {
			errorList
					.push(getLocalMessage("echallan.valid.email"));
		}
	}
	
	if ($("#challanFromArea").val() == '' || $("#challanFromArea").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanFromArea"));
	}
	if ($("#challanToArea").val() == '' || $("#challanToArea").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanToArea"));
	}
	if ($("#offenderName").val() == '' || $("#offenderName").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanOffenderName"));
	}
	
	if ($("#offenderMobNo").val() == '' || $("#offenderMobNo").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanOffenderMobNo"));
	}else if (!(/^[6-9]\d{9}$/.test(mobileNo))){
		        errorList.push(getLocalMessage("EChallan.MobNoInvalid"));
	}
	
	if ($("#violationPurpose").val() == '' || $("#violationPurpose").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanViolationPurpose"));
	}
	
	if ($("#locality").val() == '' || $("#locality").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateLocality"));
	}
	
/*	if ($("#formFile").val() == '' || $("#formFile").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanFile"));
	}*/
	if ($("#officerAvailableOnsite").val() == '' || $("#officerAvailableOnsite").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanOfficerAvailableOnsite"));
	}
	
	errorList = validateDetailsForm(errorList);

	if ($("#challanType:checked").val() == "OS") {
		if ($("#challanTotalAmt").val() == '' || $("#challanTotalAmt").val() == undefined) {
			errorList.push(getLocalMessage("EChallan.enter.totalAmt"));
		}
	}
	
	return errorList;
}

function proceedSave(element) {
	
	var errorList = [];
	errorList = validateEntryForm();
			
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$("#errorDiv").hide();
		if ($("input:radio[name='challanMasterDto.challanType']").filter(":checked").val() == 'OS'){
			return saveOrUpdateForm(element, "Challan Number Saved Successfully",
					'EChallanEntry.html?PrintReport', 'saveform');
		}else{
			return saveOrUpdateForm(element,"Raid Number Saved Successfully", 
					'EChallanEntry.html?printAcknowledgement', 'saveform');
		}		
	}
}

/******************** back button ************/
function backButton() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('EChallanEntry.html?backToMainSearch');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}

/******************** reset button ************/
function resetForm() {
	 var data={};
		var URL='EChallanEntry.html?AddForm';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
}
/**************** table validation **************/
function validateDetailsForm(errorList) {
	$('.firstRow')
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
				
				if ($("#challanType:checked").val() == "OS") {
					var challanAmt = $.trim($("#challanAmt" + i).val());
					if (itemNameCode == 'OTH' &&(challanAmt == null || challanAmt == "")){
						errorList.push(getLocalMessage("EChallan.enterAmount.forRow")+ " " + (i + 1));
					}
				}else{
					var storeId = $.trim($("#storeId" + i).val());
					if (storeId == null || storeId == ""){
						errorList.push(getLocalMessage("EChallan.enterStorageLocation.forRow")+ " " + (i + 1));
					}
				}
				
	});
	return errorList;
}

function getBrmsCharges(element){
	var errorList = [];
	var divName = ".widget-content";
	 var hoardingId = $(element).attr('id');
	 var index = parseInt(hoardingId.charAt(hoardingId.length - 1), 10);
	 
	 var itemNameCode = $("#itemName" + index).find('option:selected').attr('code');
		
	 if(itemNameCode == 'OTH'){
		 getOtherItemAmount(element);
	 }else{
		
		var itemName = $("#itemName" + index).val();
		var itemQuantity = $("#itemQuantity" + index).val();
		var i = index+1;
		if (itemQuantity == "") {
			errorList.push(getLocalMessage("EChallan.enterItemQuantity.forRow")+ " " + (i));

		}

		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
			return false;
		}
	    else{
		

		 var requestData = {
				 "itemName" : itemName,
				    "itemQuantity" : itemQuantity
				};
		
	
		
		var ajaxResponse = __doAjaxRequest('EChallanEntry.html?getChargesForItem', 'Post', requestData, false,
		'html');
	       }
		 $("#challanAmt" + index).val(ajaxResponse);
		 var totalItemAmnt = 0;
		 $("#itemTable tbody tr")
		    .each(
			    function(i) {
				
				var itemAmount = $("#challanAmt" + i).val();
				
				var rowCount = i + 1;
				 totalItemAmnt = parseInt(itemAmount)
					+ parseInt(totalItemAmnt);

				if (!isNaN(totalItemAmnt)) {
					$("#challanTotalAmt").val(totalItemAmnt);
				}
			    }); 
	 }
	
	 
}

function resetFormForChallanTypeChange() {
	var value = $(".challanType:checked").val();
	 var data={
			 "challanType":value
	 };
		var URL='EChallanEntry.html?resetFormWithChallanType';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
}

function getOtherItemAmount(obj) {
    
	$(".firstRow").each(function(i) {
		var itemName = $.trim($("#itemName" + i).val());
		var itemNameCode = $("#itemName" + i).find('option:selected').attr('code');
			var challanAmt = $('#challanAmt' + i);
			if (itemNameCode == 'OTH') {
				challanAmt.prop("readonly", false);
			} else {
				challanAmt.prop("readonly", true);
			}
	});
}

function addTotalAmount(){
	 var totalItemAmnt = 0;
	 $("#itemTable tbody tr")
	    .each(
		    function(i) {
			
			var itemAmount = $("#challanAmt" + i).val();
			
			var rowCount = i + 1;
			 totalItemAmnt = parseInt(itemAmount)
				+ parseInt(totalItemAmnt);

			if (!isNaN(totalItemAmnt)) {
				$("#challanTotalAmt").val(totalItemAmnt);
			}
		    });
}