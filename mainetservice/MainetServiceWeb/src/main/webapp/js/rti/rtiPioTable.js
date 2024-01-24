$(function() {
	
	/* To add new Row into table */
	$("#attachDoc").on('click', '.addCF', function() {

		var errorList = [];
		errorList = validatePioResponseTable(errorList);
		if (errorList.length == 0) {
			var content = $("#attachDoc").find('tr:eq(1)').clone();
			$("#attachDoc").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			reOrderRtiRequestTableSequence('.appendableClass');
			// return false;
		} else {
			displayErrorsOnPage(errorList);
		}

	});

	/* To delete Row From the table */
	$("#attachDoc").on('click', '.delButton', function() {
		

		var row = $("#attachDoc tbody .appendableClass").length;
		if (row != 1) {
			$(this).parent().parent().remove();
			reOrderRtiRequestTableSequence('.appendableClass');
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("rti.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
		
	});

});

function reOrderRtiRequestTableSequence(classNameFirst) {
	
	$(classNameFirst).each(
			function(i) {
				
				// id binding
				$(this).find("input:text:eq(0)")
						.attr("id", "mediaSerialNo" + i);
				$(this).find("select:eq(0)").attr("id", "mediaType" + i);
				$(this).find("input:text:eq(1)").attr("id", "quantity" + i);
				$(this).find("input:text:eq(2)").attr("id", "mediaDesc" + i);

				// path binding
				$(this).find("input:text:eq(0)").attr("name",
						"rtiMediaListDTO[" + i + "].mediaSerialNo");
				$(this).find("select:eq(0)").attr("name",
						"rtiMediaListDTO[" + i + "].mediaType");
				$(this).find("input:text:eq(1)").attr("name",
						"rtiMediaListDTO[" + i + "].quantity");
				$(this).find("input:text:eq(2)").attr("name",
						"rtiMediaListDTO[" + i + "].mediaDesc");

				// to increment serial number
				var incr = i + 1;
				$("#unitNo" + i).val(incr);

			});
}

function fileCountUpload(element) {

	
	var errorList = [];
	errorList = validatePioResponseTable(errorList);
	if (errorList.length == 0) {
		var row = $("#attachDoc tbody .appendableClass").length;
		$("#length").val(row);
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('PioResponse.html?fileCountUpload',
				'POST', requestData, false, 'html');
		$("#uploadTagDiv").html(response);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
	// prepareTags();
}

function validatePioResponseTable(errorList) {
	

	var errorList = [];
	$("#attachDoc tbody .appendableClass").each(
			function(i) {
				var mediaType = $("#mediaType" + i).val();
				var quantity = $("#quantity" + i).val();
				var description = $("#mediaDesc" + i).val();

				var constant = i + 1;

				if (mediaType == '0' || mediaType == undefined
						|| mediaType == "") {
					errorList.push(getLocalMessage("rti.validation.MediaType")
							+ " " + constant);
				}
				if (quantity == "" || quantity == undefined) {
					errorList.push(getLocalMessage("rti.validation.Quantity")
							+ " " + constant);
				}
				if (description == "" || description == undefined) {
					errorList
							.push(getLocalMessage("rti.validation.Description")
									+ " " + constant);
				}

			});
	return errorList;
}

function doFileDeletion(obj, id) {
	
	requestData = {
		"id" : id
	};
	url = 'PioResponse.html?doEntryDeletion';
	var row = $("#attachDoc tbody .appendableClass").length;
	if (row != 1) {
		var response = __doAjaxRequest('PioResponse.html?doEntryDeletion',
				'POST', requestData, false, 'html');
	}
}
