$(document).ready(function() {

	$('#hoardingZone1').val(-1);
	$('#hoardingZone2').val(-1);
	$('#hoardingZone3').val(-1);
	$('#hoardingZone4').val(-1);
	$('#hoardingZone5').val(-1);
	$('#hoardingTypeId1').val(-1);
	$('#hoardingTypeId2').val(-1);
	$('#reportType').val();
	
	
	$("#resetUlbform").on("click", function(){ 
		  window.location.reload("#UlbAdhReports")
		});
	
		$("#resetUabform").on("click", function(){ 
		  window.location.reload("#UadAdhReports")
		});
});

function viewHoardingReport(obj) {

	var errorList = [];
	var hoardingZone1 = $("#hoardingZone1").val();
	var hoardingZone2 = $("#hoardingZone2").val();
	var hoardingZone3 = $("#hoardingZone3").val();
	var hoardingZone4 = $("#hoardingZone4").val();
	var hoardingZone5 = $("#hoardingZone5").val();
	var hoardingType = $("#hoardingTypeId1").val();
	var subhoardingType = $('#hoardingTypeId2').val();

	if (hoardingZone1 == "" || hoardingZone1 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone1"));

	}
	if (hoardingZone2 == "" || hoardingZone2 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone2"));

	}

	if (hoardingZone3 == "" || hoardingZone3 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone3"));

	}
	if (hoardingZone4 == "" || hoardingZone4 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone4"));

	}
	if (hoardingZone5 == "" || hoardingZone5 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone5"));

	}
	if (hoardingType == "" || hoardingType == 0) {
		errorList.push(getLocalMessage("hoarding.master.validate.hrdtype"));
	}
	if (subhoardingType == "" || subhoardingType == 0) {
		errorList.push(getLocalMessage("hoarding.master.validate.subhrdtype"));
	}

	// setting zero incase of All(-1) option
	if (hoardingType == -1) {
		hoardingType = 0;
	}
	if (subhoardingType == -1) {
		subhoardingType = 0;
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {

		var divName = '.content-page';
		$("#errorDiv").hide();

		// In ward zone hierarchy ,if any level is not defined setting that
		// level as zero
		var hoardingWz1;
		var hoardingWz2;
		var hoardingWz3;
		var hoardingWz4;
		var hoardingWz5;

		if ($("#hoardingZone1").val() == undefined) {
			hoardingWz1 = 0;
		} else {
			hoardingWz1 = $("#hoardingZone1").val();
		}
		if ($("#hoardingZone2").val() == undefined) {
			hoardingWz2 = 0;
		} else {
			hoardingWz2 = $("#hoardingZone2").val();
		}
		if ($("#hoardingZone3").val() == undefined) {
			hoardingWz3 = 0;
		} else {
			hoardingWz3 = $("#hoardingZone3").val();
		}
		if ($("#hoardingZone4").val() == undefined) {
			hoardingWz4 = 0;
		} else {
			hoardingWz4 = $("#hoardingZone4").val();
		}
		if ($("#hoardingZone5").val() == undefined) {
			hoardingWz5 = 0;
		} else {
			hoardingWz5 = $("#hoardingZone5").val();
		}

		if (hoardingWz1 == -1) {
			hoardingWz1 = 0;
		}
		if (hoardingWz2 == -1) {
			hoardingWz2 = 0;
		}
		if (hoardingWz3 == -1) {
			hoardingWz3 = 0;
		}
		if (hoardingWz4 == -1) {
			hoardingWz4 = 0;
		}
		if (hoardingWz5 == -1) {
			hoardingWz5 = 0;
		}
		var requestData = {

			"hoardingZone1" : hoardingWz1,
			"hoardingZone2" : hoardingWz2,
			"hoardingZone3" : hoardingWz3,
			"hoardingZone4" : hoardingWz4,
			"hoardingZone5" : hoardingWz5,
			"hoardingType" : hoardingType,
			"subhoardingType" : subhoardingType

		};
		var URL = 'HoardingRegister.html?Gethoardingform';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}

}

/*
 * $(document).ready(function() {
 * 
 * $('#viewreport').click(function() {
 * 
 * $('.error-div').hide(); var errorList = []; var hoardingZone =
 * $("#hoardingZone1").val(); var hoardingTypeId = $("#hoardingTypeId1").val();
 * if(hoardingZone == '0'){ hoardingZone =''; } if(hoardingTypeId == '0'){
 * hoardingTypeId =''; }if (hoardingZone == "") {
 * errorList.push(getLocalMessage("hoarding.master.validate.hoardingZone")); }if
 * (hoardingTypeId == "") {
 * errorList.push(getLocalMessage("hoarding.master.validate.hrdtype")); if
 * (errorList.length > 0) { $("#errorDiv").show();
 * displayErrorsOnPage(errorList);}return errorList;}); });function
 * report(formUrl, actionParam) {
 * 
 * var errorList = []; // errorList = validateForm(errorList); // if
 * (errorList.length > 0) { // displayErrorsOnPage(errorList); // } // else{ var
 * data = { };
 * 
 * var divName = '.content-page'; var ajaxResponse = doAjaxLoading(formUrl + '?' +
 * actionParam,data,'html'); $(divName).removeClass('ajaxloader');
 * $(divName).html(ajaxResponse); prepareTags(); // } } function back() {
 * 
 * $("#postMethodForm").prop('action', ''); $("#postMethodForm").prop('action',
 * 'DCBRegister.html'); $("#postMethodForm").submit(); }
 * 
 * 
 * function PrintDiv(title) { var divContents =
 * document.getElementById("dcb").innerHTML; var printWindow =
 * window.open('','_blank'); printWindow.document.write('<html><head><title>'+title+'</title>');
 * printWindow.document.write('<link
 * href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet"
 * type="text/css" />') printWindow.document.write('<link
 * href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet"
 * />') printWindow.document.write('<link href="assets/css/style.css"
 * rel="stylesheet" type="text/css" />') printWindow.document.write('<link
 * href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
 * printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
 * printWindow.document.write('<script type="text/javascript"
 * src="assets/libs/excel-export/excel-export.js"></script>')
 * printWindow.document.write('<script>$(window).load(function()
 * {$(".table-pagination, .remove-btn, .paging-nav, .tfoot").remove(); $(".table
 * thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc
 * tablesorter-header"); $(".table tr").removeAttr("style"); $(".table
 * .tfoot").addClass("hide");});</script>') printWindow.document.write('</head><body
 * style="background:#fff;">'); printWindow.document.write('<div
 * style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div
 * class="text-center"><button onclick="window.print();" class="btn btn-success
 * hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i>
 * Print</button> <button id="btnExport" type="button" class="btn btn-blue-2
 * hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button
 * onClick="window.close();" type="button" class="btn btn-blue-2
 * hidden-print">Close</button></div></div>')
 * printWindow.document.write(divContents); printWindow.document.write('</body></html>');
 * printWindow.document.close(); }
 */

/* ADH UAD BIRT REPORTS */

function saveUadReportForm(obj) {
	
	var errorList = [];
	var ReportType = $('#reportType').val();

	if (ReportType == "" || ReportType == undefined || ReportType == 0) {
		errorList.push(getLocalMessage("adh.validation.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = "";
		if (ReportType == "A") {
			requestData = "&ReportType=" + ReportType;
		} else {
			requestData = "&ReportType=" + ReportType;
		}

		var URL = 'HoardingRegister.html?GetReportform';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}


/* ADH ULB BIRT REPORTS */

function saveUlbAdhForm(obj) {
	
	var errorList = [];
	var ReportUlbType = $('#reportType').val();

	if (ReportUlbType == "" ||ReportUlbType == undefined || ReportUlbType == 0) {
		errorList.push(getLocalMessage("adh.validation.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData1 = "";
		if (ReportUlbType == "A") {
			requestData1 = "&ReportUlbType=" + ReportUlbType;
		} else {
			requestData1 = "&ReportUlbType=" + ReportUlbType;
		}

		var URL = 'HoardingRegister.html?GetUlbReportform';
		var returnData = __doAjaxRequest(URL, 'POST', requestData1, false);
		window.open(returnData, '_blank');

	}
}
