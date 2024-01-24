$(document).ready(function() {

	$("#rmDatetemp").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		minDate : 0,
		maxDate: 'today',
		changeYear : true
	});
	$("#transactionDateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	$("#rmDatetemp").datepicker('setDate', new Date());

	$("#rmDatetemp").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$("#receiptDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function searchReceiptData() {
	
	var errorList = [];
	errorList = validateReceiptSearchForm(errorList);
	if (errorList.length == 0) {

		var rmRcptno = $("#rmRcptno").val();
		var rmAmount = $("#rdamount").val();
		var rm_Receivedfrom = $("#rm_Receivedfrom").val();
		var rmDate = $("#rmDatetemp").val();

		var requestData = {
			"rmAmount" : rmAmount,
			"rmRcptno" : rmRcptno,
			"rm_Receivedfrom" : rm_Receivedfrom,
			"rmDate" : rmDate
		};
		var table = $('#receiptDatatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'DepositReceiptEntry.html?getAllReceiptData', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("collection.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
		    var result = [];
			$.each(prePopulate,function(index) {
					var obj = prePopulate[index];
					result.push([
								obj.rmRcptno,
								obj.rmDatetemp,
								obj.rmReceivedfrom,
								obj.rmAmount,
								'<td class="text-center" align="center">'
									+ '<button type="button" class="btn btn-blue-2 btn-sm  margin-right-10" onclick="getActionForReceipt(\''
									+ obj.rmRcptid
									+ '\',\'V'
									+ '\')"  title="view"><i class="fa fa-eye" align="center"></i></button>'
								+ '</td>' ]);
				});
		table.rows.add(result);
		table.draw();
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateReceiptSearchForm(errorList) {
	
	var receiptNo = $("#rmRcptno").val();
	var receiptDate = $("#rmDatetemp").val();
	var receiptAmount = $("#rdamount").val();
	var payeeName = $("#rm_Receivedfrom").val();
	if (receiptNo == "" && receiptDate == "" && receiptAmount == ""
			&& payeeName == "" && payeeName == "") {
		errorList
				.push(getLocalMessage('collection.validation.searchcriteria'));
	}
	return errorList;
}

function openAddDepositForm(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getReceiptCategoryType(){
	
	var receiptCategoryType = $("#receiptCategoryId option:selected").attr("code");
	if(receiptCategoryType == 'M'){
		var actionParam = {
		'actionParam' : receiptCategoryType
	}
	var url = "DepositReceiptEntry.html?selectAlltax";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#receiptHeadTagDiv").html(ajaxResponse);
	  prepareTags();
	}
	
 }

function getVendorName() {
	
	/*var errorList = [];

	$('#mobile_Number').val("");
	$('#email_Id').val("");

	var vmVendrnameId = $("#vm_VendorId option:selected").val();
	var vmVendrname = $("#vm_VendorId option:selected").text();

	if (vmVendrnameId != null && vmVendrnameId != undefined
			&& vmVendrnameId != "" && errorList.length == 0) {
		$("#rm_Receivedfrom").val(vmVendrname).prop("readonly", true);
	} else {
		$("#rm_Receivedfrom").val("").prop("readonly", false);
	}


	*/
	
	var selectedType = $("#vm_VendorId").find("option:selected").attr('value');
	if(selectedType==undefined || selectedType==''){
		$('#mobile_Number').val("");
		$('#email_Id').val("");
		return false;
	}else{
		$("#vm_VendorId").val(selectedType);	
	}		
		var requestData={
				"vmVendorId":$("#vm_VendorId").val()
				};
		var response = __doAjaxRequest('DepositReceiptEntry.html?getVendorPhoneNoAndEmailId', 'POST', requestData, false);
		$.each(response, function(key, value) {
			$('#mobile_Number').val(response[0]);
			$('#email_Id').val(response[1]);

			$("#mobile_Number").prop("readonly", true);
			$("#email_Id").prop("readonly", true);
		});	
}

function enableMobileAndEmail() {
	$("#mobile_Number").prop("readonly", false);
	$("#email_Id").prop("readonly", false);
}
