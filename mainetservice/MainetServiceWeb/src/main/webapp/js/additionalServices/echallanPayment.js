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
	
	if('E' == $('#saveMode').val()){
		checkItem();
	}
});

function checkItem() { 
	    $(".firstItemRow").each(function(i) {
	        var itemName = $.trim($("#itemName" + i).val());
			var itemNameCode = $("#itemName" + i).find('option:selected').attr('code');
	        var challanAmt= $('#challanAmt' + i);
	        if (itemNameCode == 'OTH') {
				challanAmt.prop("disabled", false);
			} else {
				challanAmt.prop("disabled", true);
			}                
	    });
	}

function addTotalAmount(){
	 var totalItemAmnt = 0;
	 $(".firstItemRow")
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

/** ******************SearchFunction*********** */
function searchPaymentDetails(element) {
	showloader(true);
	setTimeout(function() {
		
		var errorsList = [];
		errorsList = validateForm(errorsList);
		if (errorsList.length > 0) {
			displayErrorsOnPage(errorsList);
		} else {

		var data = {
			"raidNo" : $('#raidNo').val(),
			"offenderMobNo" : $('#offenderMobNo').val(),
		};

		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('EChallanPayment.html?searchRaidDetails', data,
				'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		}
		showloader(false);
	}, 200);
}

function validateForm(errorList) {
	var errorList = [];
	
	if (($("#raidNo").val() == "" || $("#raidNo").val() == undefined) 
			&& ($("#offenderMobNo").val() == "" || $("#offenderMobNo").val() == undefined) ) {
		errorList.push(getLocalMessage("EChallan.selectCriteria"));
	}else if (($("#raidNo").val() != "") && ($("#offenderMobNo").val() != "") ) {
		errorList.push(getLocalMessage("EChallan.selectCriteria"));
	}

	return errorList;
}

function validatePaymentForm(errorList) {
	var errorList = [];
	
	if ($("#challanTotalAmt").val() == '' || $("#challanTotalAmt").val() == undefined) {
		errorList.push(getLocalMessage("EChallan.validateChallanAmt"));
	}
	return errorList;
}

/** ******************for payment*********** */
function challanPayment(element) {
	var errorList = [];
	errorList = validatePaymentForm();
			
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,"Raid Number Saved Successfully", 
			'EChallanPayment.html?PrintReport', 'saveform');
	}
	
	return false;
}

/******************** reset button ************/
function resetForm() {
	$('#raidNo').val('');
	$('#offenderMobNo').val('');
	
	$('.error-div').hide();
}