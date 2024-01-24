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
	$('#csCcntype').val(-1);
	$('#csMeteredccn').val(-1);
	$('#csCcnsize').val(-1);

	$('#csFromdt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#csFromdt").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#csTodt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#csTodt").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$("#resetForm").on("click", function() {
		window.location.reload("#NewWaterConnection")
	})
});
function resetForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#NewWaterConnection").validate().resetForm();
}

function saveForm(obj) {

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
	var csFromdt = $("#csFromdt").val();
	var csTodt = $("#csTodt").val();
	var ConnType = $("#csCcntype").val();
	var MeteredType = $("#csMeteredccn").val();
	var CcnSize = $("#csCcnsize").val();

	if (WardZoneLevel1 != undefined) {
		if (WardZoneLevel1 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward1"));
		}
	}
	if (WardZoneLevel2 != undefined) {
		if (WardZoneLevel2 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward2"));
		}
	}
	if (WardZoneLevel3 != undefined) {
		if (WardZoneLevel3 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward3"));
		}
	}
	if (WardZoneLevel4 != undefined) {
		if (WardZoneLevel4 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward4"));
		}
	}
	if (WardZoneLevel5 != undefined) {
		if (WardZoneLevel5 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward5"));
		}
	}

	if (tarrifType != undefined) {
		if (tarrifType == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category"));
		}
	}
	if (tarrifType2 != undefined) {
		if (tarrifType2 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category2"));
		}
	}
	if (tarrifType3 != undefined) {
		if (tarrifType3 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category3"));
		}
	}
	if (tarrifType4 != undefined) {
		if (tarrifType4 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category4"));
		}
	}
	if (tarrifType5 != undefined) {
		if (tarrifType5 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category5"));
		}
	}

	if (ConnType == 0) {
		errorList.push(getLocalMessage("water.valid.connType"));
	}

	if (CcnSize == 0) {
		errorList.push(getLocalMessage("water.sel.connectionsize"));
	}
	if (MeteredType == 0) {
		errorList.push(getLocalMessage("water.list.validation.meter/NonMeter"));
	}
	if (csFromdt == "" || csFromdt == 0) {
		errorList.push(getLocalMessage("water.validation.list.from.date"));
	}
	if (csTodt == "" || csTodt == 0) {
		errorList.push(getLocalMessage("water.validation.list.to.date"));
	}

	if ((compareDate(csFromdt)) > compareDate(csTodt)) {
		errorList.push(getLocalMessage("water.validation.list.from.to.date"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var ward1;
		var ward2;
		var ward3;
		var ward4;
		var ward5;
		var tarcategory1, tarcategory2, tarcategory3, tarcategory4, tarcategory5;

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
			tarcategory1 = 0;
		} else {
			tarcategory1 = $("#trmGroup1").val();
		}

		if ($("#trmGroup2").val() == undefined) {
			tarcategory2 = 0;
		} else {
			tarcategory2 = $("#trmGroup2").val();
		}
		if ($("#trmGroup3").val() == undefined) {
			tarcategory3 = 0;
		} else {
			tarcategory3 = $("#trmGroup3").val();
		}
		if ($("#trmGroup4").val() == undefined) {
			tarcategory4 = 0;
		} else {
			tarcategory4 = $("#trmGroup4").val();
		}
		if ($("#trmGroup5").val() == undefined) {
			tarcategory5 = 0;
		} else {
			tarcategory5 = $("#trmGroup5").val();
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
		if (tarcategory1 == -1) {
			tarcategory1 = 0;
		}
		if (tarcategory2 == -1) {
			tarcategory2 = 0;
		}
		if (tarcategory3 == -1) {
			tarcategory3 = 0;
		}
		if (tarcategory4 == -1) {
			tarcategory4 = 0;
		}
		if (tarcategory5 == -1) {
			tarcategory5 = 0;
		}
		if (ConnType == -1) {
			ConnType = 0;
		}
		if (CcnSize == -1) {
			CcnSize = 0;
		}
		if (MeteredType == -1) {
			MeteredType = 0;
		}

		var requestData1 = {
			"WardZoneLevel1" : ward1,
			"WardZoneLevel2" : ward2,
			"WardZoneLevel3" : ward3,
			"WardZoneLevel4" : ward4,
			"WardZoneLevel5" : ward5,
			"tarrifType" : tarcategory1,
			"tarrifType2" : tarcategory2,
			"tarrifType3" : tarcategory3,
			"tarrifType4" : tarcategory4,
			"tarrifType5" : tarcategory5,
			"csFromdt" : csFromdt,
			"csTodt" : csTodt,
			"ConnType" : ConnType,
			"MeteredType" : MeteredType,
			"CcnSize" : CcnSize,

		};
		var URL = 'ListOfNewWaterConnReport.html?GetNewConnection';
		var returnData = __doAjaxRequest(URL, 'POST', requestData1, false);
		if (returnData == "f") {
			errorList.push(getLocalMessage('water.report.validation.FYCheck'));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}

	}
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}