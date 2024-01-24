/**
 * 
 */
$(document).ready(function() {
	$("#proertyNo").val();
	$("#flatNo").val();

	/*
	 * $("#showFlatNo").hide(); $('#flatNo').val('').trigger('chosen:updated');
	 * $('#aioConceptName').find(":selected").text();
	 */

	$('#flatNo').trigger("chosen:updated");

	$("#resetform").on("click", function() {
		window.location.reload("#penalInterestFormReport")
	})
});

function getFlatNo() {

	var errorList = [];
	var propNo = $("#proertyNo").val();

	if (propNo == "") {
		errorList
				.push(getLocalMessage("property.validation.detail.property.wise"));
	}
	if (errorList.length == 0) {
		var propNo = $("#proertyNo").val();
		var requestData = {
			"propNo" : propNo

		};

		var ajaxResponse = doAjaxLoading(
				'penalInterestReport.html?getBillingMethod', requestData,
				'html');

		if (ajaxResponse != null && ajaxResponse != "") {
			var prePopulate = JSON.parse(ajaxResponse);

			$.each(prePopulate, function(index, value) {
				$('#flatNo').append(
						$("<option></option>").attr("value", value).text(
								(value)));
			});
			$('#flatNo').trigger("chosen:updated");
			/* $('#flatNo').val('').trigger('chosen:updated'); */
			$("#showFlatNo").show();
			$("#billingMethod").val('I');
		}
	} else {
		showErrorOnPage(errorList);
	}

}

function saveReportForm(obj) {

	var errorList = [];
	var propNumber = $("#proertyNo").val();
	var flatNo = $("#flatNo").val();

	if (propNumber == "" || propNumber == null || propNumber == undefined) {
		errorList
				.push(getLocalMessage("property.validation.detail.property.wise"));
	}
	/*
	 * if (flatNo == "" || flatNo == null || flatNo == undefined) {
	 * errorList.push(getLocalMessage("enter flatNo.")); }
	 */
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData1 = "&propNumber=" + propNumber + "&flatNo=" + flatNo;

		var URL = 'penalInterestReport.html?getPenalInterest';
		var returnData1 = __doAjaxRequest(URL, 'POST', requestData1, false);
		window.open(returnData1, '_blank');

	}
}
