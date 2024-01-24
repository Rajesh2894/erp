/**
 * 
 */
$(document).ready(function() {
	$('#mnassward1').val(-1);
	$('#mnassward2').val(-1);
	$('#mnassward3').val(-1);
	$('#mnassward4').val(-1);
	$('#mnassward5').val(-1);
	$('#mnDefaulterCount').val();
	$('#mnAmount').val();
	$('#mntoAmount').val();

});

function saveForm(obj) {
	
	var errorList = [];
	var wardZone1 = $('#mnassward1').val();
	var wardZone2 = $("#mnassward2").val();
	var wardZone3 = $("#mnassward3").val();
	var wardZone4 = $("#mnassward4").val();
	var wardZone5 = $("#mnassward5").val();
	var mnDefaulterCount = $("#mnDefaulterCount").val();
	var mnAmount = $("#mnAmount").val();
	var mntoAmount =$('#mntoAmount').val();

	if (wardZone1 == 0 || wardZone1 == "" ) {
		errorList.push(getLocalMessage("property.validation.wardZone1"));
	}
	if (wardZone2 == 0 || wardZone2 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone2"));
	}
	if (wardZone3 == 0 || wardZone3 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone3"));
	}
	if (wardZone4 == 0 || wardZone4 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone4"));
	}
	if (wardZone5 == 0 || wardZone5 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone5"));
	}

	if (mnAmount == "" || mnAmount == null || mnAmount == undefined) {
		errorList
				.push(getLocalMessage("property.validation.defaulter.amount.report"));
	}

	if (mntoAmount == "" || mntoAmount == null || mntoAmount == undefined) {
		errorList
				.push(getLocalMessage("property.validation.defaulter.to.amount.report"));
	}
	
	if(mnAmount > mntoAmount){
		errorList
		.push(getLocalMessage("property.validation.defaulter.from.to.amount.report"));
	}
	if (mnDefaulterCount == "" || mnDefaulterCount == null
			|| mnDefaulterCount == undefined) {
		errorList.push(getLocalMessage("property.validation.defaulter.report"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1;
		var wardZ2;
		var wardZ3;
		var wardZ4;
		var wardZ5;

		if ($("#mnassward1").val() == undefined) {
			wardZ1 = 0;
		} else {
			wardZ1 = $("#mnassward1").val();
		}
		if ($("#mnassward2").val() == undefined) {
			wardZ2 = 0;
		} else {
			wardZ2 = $("#mnassward2").val();
		}
		if ($("#mnassward3").val() == undefined) {
			wardZ3 = 0;
		} else {
			wardZ3 = $("#mnassward3").val();
		}
		if ($("#mnassward4").val() == undefined) {
			wardZ4 = 0;
		} else {
			wardZ4 = $("#mnassward4").val();
		}
		if ($("#mnassward5").val() == undefined) {
			wardZ5 = 0;
		} else {
			wardZ5 = $("#mnassward5").val();
		}

		if (wardZ1 == -1) {
			wardZ1 = 0;
		}
		if (wardZ2 == -1) {
			wardZ2 = 0;
		}
		if (wardZ3 == -1) {
			wardZ3 = 0;
		}
		if (wardZ4 == -1) {
			wardZ4 = 0;
		}
		if (wardZ5 == -1) {
			wardZ5 = 0;
		}

		var requestData = {
			"wardZone1" : wardZ1,
			"wardZone2" : wardZ2,
			"wardZone3" : wardZ3,
			"wardZone4" : wardZ4,
			"wardZone5" : wardZ5,
			"mnDefaulterCount" : mnDefaulterCount,
			"mnAmount" : mnAmount,
			"mntoAmount" :mntoAmount
		};
		var URL = 'DefaulterReport.html?GetDefaulterReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

/*$("#resetForm").on("click", function() {
	window.location.reload("#DefaulterRegisterForm")
});*/