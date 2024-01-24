$(document).ready(function() {

	$('#codDwzid1').val(-1);
	$('#codDwzid2').val(-1);
	$('#codDwzid3').val(-1);
	$('#codDwzid4').val(-1);
	$('#codDwzid5').val(-1);
	$('#trmGroup1').val(-1);
	$('#trmGroup2').val(-1);
	$('#trmGroup3').val(-1);
	$('#trmGroup4').val(-1);
	$('#trmGroup5').val(-1);
	$("#csCcnFrom").val();
	$("#csCcnTo").val();
	$('#CsdefaulterCount').val();
	$('#csfromAmount').val();
	$('#cstoAmount').val();

});
/*
 * function resetForm() {
 * 
 * $('input[type=text]').val(''); $(".alert-danger").hide();
 * $("#DefaulterForm").validate().resetForm(); }
 */

function  saveDataForm(obj) {
	
	var errorList = [];
	var WardZoneLevel1 = $("#codDwzid1").val();
	var WardZoneLevel2 = $("#codDwzid2").val();
	var WardZoneLevel3 = $("#codDwzid3").val();
	var WardZoneLevel4 = $("#codDwzid4").val();
	var WardZoneLevel5 = $("#codDwzid5").val();
	var tarrifType = $("#trmGroup1").val();
	var tarrifType2 = $("#trmGroup2").val();
	var tarrifType3 = $("#trmGroup3").val();
	var tarrifType4 = $("#trmGroup4").val();
	var tarrifType5 = $("#trmGroup5").val();
	var csFromAmnt = $('#csfromAmount').val();
	var csToAmnt = $('#cstoAmount').val();
	var ConnNoFrom = $("#csCcnFrom").val();
	var ConnNoTo = $("#csCcnTo").val();
	var defaulterCount = $("#CsdefaulterCount").val();

	if (WardZoneLevel1 == 0 || WardZoneLevel1 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone1"));
	}
	if (WardZoneLevel2 == 0 || WardZoneLevel2 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone2"));
	}
	if (WardZoneLevel3 == 0 || WardZoneLevel3 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone3"));
	}
	if (WardZoneLevel4 == 0 || WardZoneLevel4 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone4"));
	}
	if (WardZoneLevel5 == 0 || WardZoneLevel5 == "") {
		errorList.push(getLocalMessage("property.validation.wardZone5"));
	}

	if (tarrifType == 0 || tarrifType == "") {
		errorList
				.push(getLocalMessage("water.validation.list.tarrif.category"));
	}
	
	if (tarrifType2 == 0 || tarrifType2 == "") {
		errorList
				.push(getLocalMessage("water.validation.list.tarrif.category2"));
	}
	if (tarrifType3 == 0 || tarrifType3 == "") {
		errorList
				.push(getLocalMessage("water.validation.list.tarrif.category3"));
	}
	if (tarrifType4 == 0 || tarrifType4 == "") {
		errorList
				.push(getLocalMessage("water.validation.list.tarrif.category4"));
	}
	if (tarrifType5 == 0 || tarrifType5 == "") {
		errorList
				.push(getLocalMessage("water.validation.list.tarrif.category5"));
	}

	if (csFromAmnt == "" || csFromAmnt == undefined) {
		errorList
				.push(getLocalMessage("water.defaulter.validn.report.fromAmount"));
	}
	if (csToAmnt == "" || csToAmnt == undefined) {
		errorList
				.push(getLocalMessage("water.defaulter.validn.report.toAmount"));
	}

	if (csFromAmnt > csToAmnt) {
		errorList
				.push(getLocalMessage("water.defaulter.validn.report.from.toAmount"));
	}

	if (ConnNoFrom != "" || ConnNoFrom != 0) {
		if (ConnNoTo == 0) {
			errorList
					.push(getLocalMessage("water.defaulter.validation.ConnectionTo"));
		}
	}

	if (ConnNoTo != "" || ConnNoTo != 0) {
		if (ConnNoFrom == 0) {
			errorList
					.push(getLocalMessage("water.defaulter.validation.ConnectionFrom"));
		}
	}
	if ((parseFloat(ConnNoTo) < parseFloat(ConnNoFrom))
			&& (ConnNoFrom != "" || ConnNoFrom != 0)
			&& (ConnNoTo != "" || ConnNoTo != 0)) {
		errorList
				.push(getLocalMessage("water.defaulter.validation.ConnectionFromTo"));
	}

	/*
	 * if (defaulterCount == "" || defaulterCount == undefined || defaulterCount ==
	 * null) { errorList.push(getLocalMessage("water.validation.topDefaulter")); }
	 */

	if (ConnNoFrom == "" || ConnNoFrom == null) {
		ConnNoFrom = 0;
	}
	if (ConnNoTo == "" || ConnNoTo == null) {
		ConnNoTo = 0;
	}

	if (defaulterCount == "") {
		defaulterCount = 0;
	}
	if (tarrifType == "") {
		tarrifType = 0;
	}

	if (tarrifType2 == "") {
		tarrifType2 = 0;
	}
	if (tarrifType3 == "") {
		tarrifType3 = 0;
	}
	if (tarrifType4 == "") {
		tarrifType4 = 0;
	}
	if (tarrifType5 == "") {
		tarrifType5 = 0;
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var ward1, ward2, ward3, ward4, ward5;
		var tarCategory1, tarCategory2, tarCategory3, tarCategory4, tarCategory5;

		if ($("#codDwzid1").val() == undefined) {
			ward1 = 0;
		} else {
			ward1 = $("#codDwzid1").val();
		}
		if ($("#codDwzid2").val() == undefined) {
			ward2 = 0;
		} else {
			ward2 = $("#codDwzid2").val();
		}
		if ($("#codDwzid3").val() == undefined) {
			ward3 = 0;
		} else {
			ward3 = $("#codDwzid3").val();
		}
		if ($("#codDwzid4").val() == undefined) {
			ward4 = 0;
		} else {
			ward4 = $("#codDwzid4").val();
		}
		if ($("#codDwzid5").val() == undefined) {
			ward5 = 0;
		} else {
			ward5 = $("#codDwzid5").val();
		}

		if ($("#trmGroup1").val() == undefined) {
			tarCategory1 = 0;
		} else {
			tarCategory1 = $("#trmGroup1").val();
		}
		if ($("#trmGroup2").val() == undefined) {
			tarCategory2 = 0;
		} else {
			tarCategory2 = $("#trmGroup2").val();
		}
		if ($("#trmGroup3").val() == undefined) {
			tarCategory3 = 0;
		} else {
			tarCategory3 = $("#trmGroup3").val();
		}
		if ($("#trmGroup4").val() == undefined) {
			tarCategory4 = 0;
		} else {
			tarCategory4 = $("#trmGroup4").val();
		}
		if ($("#trmGroup5").val() == undefined) {
			tarCategory5 = 0;
		} else {
			tarCategory5 = $("#trmGroup5").val();
		}

		if (ward1 == -1) {
			ward1 = 0;
		}
		if (ward2 == -1) {
			ward2 = 0;
		}
		if (ward3 == -1) {
			ward3 = 0;
		}
		if (ward4 == -1) {
			ward4 = 0;
		}
		if (ward5 == -1) {
			ward5 = 0;
		}

		if (tarCategory1 == -1) {
			tarCategory1 = 0;
		}
		if (tarCategory2 == -1) {
			tarCategory2 = 0;
		}
		if (tarCategory3 == -1) {
			tarCategory3 = 0;
		}
		if (tarCategory4 == -1) {
			tarCategory4 = 0;
		}
		if (tarCategory5 == -1) {
			tarCategory5 = 0;
		}
		var requestData1 = {
			"WardZoneLevel1" : ward1,
			"WardZoneLevel2" : ward2,
			"WardZoneLevel3" : ward3,
			"WardZoneLevel4" : ward4,
			"WardZoneLevel5" : ward5,
			"tarrifType" : tarCategory1,
			"tarrifType2" : tarCategory2,
			"tarrifType3" : tarCategory3,
			"tarrifType4" : tarCategory4,
			"tarrifType5" : tarCategory5,
			"csFromAmnt" : csFromAmnt,
			"csToAmnt" : csToAmnt,
			"ConnNoFrom" : ConnNoFrom,
			"ConnNoTo" : ConnNoTo,
			"defaulterCount" : defaulterCount,

		};
		var URL = 'defaulterReport.html?GetdefaulterReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData1, false);
		window.open(returnData, '_blank');

	}
}
