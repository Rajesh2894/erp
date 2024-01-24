$(document).ready(function() {
	$('#PgBankParameterDetail').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$("#pgParamDetailTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function save(element) {
	debugger;
	var errorList = [];
	errorList = validatePgBank(errorList);
	errorList = validatePgDetails(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				'Pg bank parameter details saved successfully',
				'PgBankParameterDetail.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}

}

function addPgDetails() {
	var errorList = [];
	$("#errorDiv").hide();
	errorList = validatePgDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('pgParamDetailTable');
		$('#pgParamDetailTable').DataTable();
	} else {
		$('#pgParamDetailTable').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function deletePgDetailRow(obj, ids) {
	deleteTableRow('pgParamDetailTable', obj, ids);
	$('#pgParamDetailTable').DataTable().destroy();
	triggerTable();
}

function validatePgDetails(errorList) {

	var i = 0;

	$("#pgParamDetailTable tbody tr").each(
			function(i) {

				var parName = $("#parName" + i).val();
				var parValue = $("#parValue" + i).val();

				var rowCount = i + 1;

				if (parName == "0" || parName == undefined || parName == null
						|| parName == "") {
					errorList.push(getLocalMessage("common.enter.param.name")
							+ rowCount);
				}
				if (parValue == "0" || parValue == undefined
						|| parValue == null || parValue == "") {
					errorList.push(getLocalMessage("common.enter.param.value")
							+ rowCount);
				}

			});
	return errorList;

}

function validatePgBank(errorList) {
	debugger;
	 var errorList = [];
	var pgName = $("#pgName").val();
	var merchantId = $("#merchantId").val();
	var pgUrl = $("#pgUrl").val();
	var bankid = $("#bankid").val();

	if (pgName == "0" || pgName == undefined || pgName == null || pgName == "") {
		errorList.push(getLocalMessage("common.enter.pg.name"));
	}

	if (merchantId == "0" || merchantId == undefined || merchantId == null
			|| merchantId == "") {
		errorList.push(getLocalMessage("common.enter.merchantId"));
	}

	if (pgUrl == "0" || pgUrl == undefined || pgUrl == null || pgUrl == "") {
		errorList.push(getLocalMessage("common.enter.pg.url"));
	}

	if (bankid == "0" || bankid == undefined || bankid == null || bankid == "") {
		errorList.push(getLocalMessage("common.enter.bankId"));
	}
	
	return errorList;
}
