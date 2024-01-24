$(document).ready(function() {

    $("#contDate").prop("readonly", true);
    $("#contDept").prop("readonly", true);
    $("#contp1Name").prop("readonly", true);
    $("#contp2Name").prop("readonly", true);
    $("#contFromDate").prop("readonly", true);
    $("#contToDate").prop("readonly", true);

    $('#adhBillPayment').validate({
	onkeyup : function(element) {
	    this.element(element);
	    console.log('onkeyup fired');
	},
	onfocusout : function(element) {
	    this.element(element);
	    console.log('onfocusout fired');
	}
    });
	
	$('#payModeIn').on('change', function() {
    	var payAmount = $('#payAmount').val();
    	$('#amountToPay').val(payAmount);
	});

});

function searchContract(element) {

    var errorList = [];
    var divName = '.content-page';
    var URL = 'ADHContractBillPayment.html?searchADHBillRecord';
    var formName = findClosestElementId(element, 'form');
    contractNo = $("#contractNo").val();
    var theForm = '#' + formName;
    var requestData = {
	"contractNo" : contractNo
    };

    if (contractNo == null || contractNo == undefined || contractNo == "") {
	errorList.push(getLocalMessage("adh.enter.contract.no"));
    }
    if (errorList.length == 0) {
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
		'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	prepareTags();

    } else {
	displayErrorsOnPage(errorList);
    }

}
function saveBillData(element) {
    if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
	    ":checked").val() == 'N'
	    || $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
		    ":checked").val() == 'P') {
	return saveOrUpdateForm(element, "Bill Payment done successfully!",
		'ADHContractBillPayment.html?PrintReport', 'saveform');
    } else {
	return saveOrUpdateForm(element, "Bill Payment done successfully!",
		'ADHContractBillPayment.html', 'saveform');
    }
}