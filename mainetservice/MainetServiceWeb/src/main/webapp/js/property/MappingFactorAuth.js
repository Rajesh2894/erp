$(function() {

	jQuery('.hasDecimal').keyup(function() {
		this.value = this.value.replace(/[^0-9\.]/g, '');
	});

	$("#customFields1").on('click', '.addCF2', function() {

		var errorList = [];
		if (errorList.length == 0) {

			$(".datepicker2").datepicker("destroy");
			var content = $(this).closest('tr').clone();
			$(this).closest("tr").after(content);
			var clickedIndex = $(this).parent().parent().index() - 1;
			content.find("input:text").val('');
			
			content.find("select").val('0');

			$('.error-div').hide();
			datePickerLogic();
			reOrderTableSequence('.factorTableClass');
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}

	});

	$("#customFields1").on('click', '.remCF2', function() {

		if ($("#customFields1 tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderTableSequence('.factorTableClass');
		} else {
			var errorList = [];
			errorList.push("First row cannot be remove.");
			displayErrorsOnPage(errorList);

		}

	});

});

function reOrderTableSequence(className) {

	$(className).each(
			function(i) {

				$(".datepicker2").datepicker("destroy");
				$(this).find("select:eq(0)").attr("id", "factorType_" + i);
				$(this).find("select:eq(0)").attr("onchange",
						"getFactorValue(" + i + ")");
				$(this).find("select:eq(1)").attr("id",
						"proAssfFactorValue_" + i);
				$(this).find("select:eq(2)").attr("id", "proAssfActive_" + i);
				$(this).find("input:text:eq(0)").attr("id", "fromDate_" + i);
				$(this).find("input:text:eq(1)").attr("id", "toDate_" + i);
				$(this).find("input:text:eq(2)").attr("id", "srId_" + i);

				$("#srNoId_" + i).text(i + 1);
				
				$(this).find("select:eq(0)").attr("name",
						"currUnit[" + i + "].proAssfFactorId");
				$(this).find("select:eq(1)").attr("name",
						"currUnit[" + i + "].proAssfFactorValue");
				$(this).find("select:eq(2)").attr("name",
						"currUnit[" + i + "].proAssfActive");
				$(this).find("input:text:eq(0)").attr("name",
						"currUnit[" + i + "].proAssfStartDate");
				$(this).find("input:text:eq(1)").attr("name",
						"currUnit[" + i + "].proAssfEndDate");

				datePickerLogic();

			});

}

function getFactorValue(count) {

	var factorType = $("#factorType_" + count + " option:selected")
			.attr("code");

	$('#factorValue_' + count).find('option:gt(0)').remove();
	var postdata = 'factorType=' + factorType;

	var json = __doAjaxRequest(
			'SelfAssessmentAuthorization.html?getMappingFactor', 'POST',
			postdata, false, 'json');
	var optionsAsString = '';

	$.each(json,
			function(key, value) {
				optionsAsString += "<option value='" + key + "'>" + value
						+ "</option>";
			});
	$('#proAssfFactorValue_' + count).append(optionsAsString);
}

function datePickerLogic() {

	$(".datepicker2").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	return true;
}
