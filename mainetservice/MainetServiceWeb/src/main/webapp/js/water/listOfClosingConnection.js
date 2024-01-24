/**
 * 
 */
$(document).ready(function() {
	$('#codDwzid1').val('-1');
	$('#codDwzid2').val('-1');
	$('#codDwzid3').val('-1');
	$('#codDwzid4').val('-1');
	$('#codDwzid5').val('-1');
	$('#trmGroup1').val('-1');
	$('#trmGroup2').val('-1');
	$('#trmGroup3').val('-1');
	$('#trmGroup4').val('-1');
	$('#trmGroup5').val('-1');
	$('#discType').val('-1');

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
	$("#resetform").on("click", function() {
		window.location.reload("#ClosedConnectionFormReport")
	})
});
function resetform() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#ClosedConnectionFormReport").validate().resetform();
}
function saveForm(obj) {

	var errorList = [];
	var wardZone1 = $('#codDwzid1').val();
	var wardZone2 = $("#codDwzid2").val();
	var wardZone3 = $("#codDwzid3").val();
	var wardZone4 = $("#codDwzid4").val();
	var wardZone5 = $("#codDwzid5").val();
	var trmgrp = $("#trmGroup1").val();
	var trmgrp2 = $("#trmGroup2").val();
	var trmgrp3 = $("#trmGroup3").val();
	var trmgrp4 = $("#trmGroup4").val();
	var trmgrp5 = $("#trmGroup5").val();
	var Disctype = $("#discType").val();
	var csFromdt = $("#csFromdt").val();
	var csTodt = $("#csTodt").val();

	if (wardZone1 != undefined) {
		if (wardZone1 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward1"));
		}
	}
	if (wardZone2 != undefined) {
		if (wardZone2 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward2"));
		}
	}
	if (wardZone3 != undefined) {
		if (wardZone3 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward3"));
		}
	}
	if (wardZone4 != undefined) {
		if (wardZone4 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward4"))
		}
	}
	if (wardZone5 != undefined) {
		if (wardZone5 == 0) {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward5"))
		}
	}
	if (trmgrp != undefined) {
		if (trmgrp == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category"))
		}
	}
	if (trmgrp2 != undefined) {
		if (trmgrp2 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category2"))
		}
	}
	if (trmgrp3 != undefined) {
		if (trmgrp3 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category3"))
		}
	}
	if (trmgrp4 != undefined) {
		if (trmgrp4 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category4"))
		}
	}
	if (trmgrp5 != undefined) {
		if (trmgrp5 == 0) {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category5"))
		}
	}

	if (Disctype == 0) {
		errorList
				.push(getLocalMessage("water.validation.list.disconnection.type"));
	}
	if (csFromdt == 0) {
		errorList.push(getLocalMessage("water.validation.list.from.date"));
	}
	if (csTodt == 0) {
		errorList.push(getLocalMessage("water.validation.list.to.date"));
	}

	if ((compareDate(csFromdt)) > compareDate(csTodt)) {
		errorList.push(getLocalMessage("water.validation.list.from.to.date"));
	}

	if (errorList.length > 0) {
		// $("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1,wardZ2,wardZ3,wardZ4,wardZ5;
		var tarCategory1,tarCategory2,tarCategory3,tarCategory4,tarCategory5;

		if ($("#codDwzid1").val() == undefined) {
			wardZ1 = 0;
		} else {
			wardZ1 = $("#codDwzid1").val();
		}
		if ($("#codDwzid2").val() == undefined) {
			wardZ2 = 0;
		} else {
			wardZ2 = $("#codDwzid2").val();
		}
		if ($("#codDwzid3").val() == undefined) {
			wardZ3 = 0;
		} else {
			wardZ3 = $("#codDwzid3").val();
		}
		if ($("#codDwzid4").val() == undefined) {
			wardZ4 = 0;
		} else {
			wardZ4 = $("#codDwzid4").val();
		}
		if ($("#codDwzid5").val() == undefined) {
			wardZ5 = 0;
		} else {
			wardZ5 = $("#codDwzid5").val();
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
		
		if (Disctype == -1) {
			Disctype = 0;
		}
		var requestData = {
			"wardZone1" : wardZ1,
			"wardZone2" : wardZ2,
			"wardZone3" : wardZ3,
			"wardZone4" : wardZ4,
			"wardZone5" : wardZ5,
			"trmgrp" : tarCategory1,
			"trmgrp2" : tarCategory2,
			"trmgrp3" : tarCategory3,
			"trmgrp4" : tarCategory4,
			"trmgrp5" : tarCategory5,
			"Disctype" : Disctype,
			"csFromdt" : csFromdt,
			"csTodt" : csTodt,
		}

		var URL = 'ListOfClosedConnection.html?GetClosedConnectionDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
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
