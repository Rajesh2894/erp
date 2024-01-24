/**
 * 
 */
$(document).ready(function() {
	$('#mnassward1').val(-1);
	$('#mnassward2').val(-1);
	$('#mnassward3').val(-1);
	$('#mnassward4').val(-1);
	$('#mnassward5').val(-1);

});

function saveOutstandForm(obj) {

	var errorList = [];
	var zone = $("#mnassward1").val();
	var ward = $("#mnassward2").val();
	var block = $("#mnassward3").val();
	var route = $("#mnassward4").val();
	var subroute = $("#mnassward4").val();

	if (zone == 0 || zone == "") {
		errorList.push(getLocalMessage("property.validation.wardZone1"));
	}
	if (ward == 0 || ward == "") {
		errorList.push(getLocalMessage("property.validation.wardZone2"));
	}
	if (block == 0 || block == "") {
		errorList.push(getLocalMessage("property.validation.wardZone3"));
	}
	if (route == 0 || route == "") {
		errorList.push(getLocalMessage("property.validation.wardZone4"));
	}
	if (subroute == 0 || subroute == "") {
		errorList.push(getLocalMessage("property.validation.wardZone5"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1, wardZ2, wardZ3, wardZ4, wardZ5;

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
			"zone" : wardZ1,
			"ward" : wardZ2,
			"block" : wardZ3,
			"route" : wardZ4,
			"subroute" : wardZ5

		};
		var URL = 'SummaryDemandCollectionOutstandingReport.html?GetSummaryReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

$("#resetForm").on("click", function() {
	window.location.reload("#SummaryReport")
});
