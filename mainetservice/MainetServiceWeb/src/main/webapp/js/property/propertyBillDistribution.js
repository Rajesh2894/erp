$(document).ready(function() {

	$("#showFlatNo").hide();
	showSingleMultiple();
	
	$('#datatables').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true
	    });
	
	$(".billDistribDate1").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
	});
	var dateFields = $('.Moredatepicker');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	
	$('#selectall').click(function(event) {
		if (this.checked) {
			$('.checkall').each(function() {
				this.checked = true;
			});
		} else {
			$('.checkall').each(function() {
				this.checked = false;

			});
		}
	});
	
});

function getFlatNo() {

	var errorList = [];
	var propNo = $("#propertyNo").val();
	var oldPropNo = $("#oldPropertyNo").val();
	if (propNo == "" && oldPropNo == "") {
		errorList.push(getLocalMessage("property.changeInAss"));
	}
	if (errorList.length == 0) {
		var propNo = $("#propertyNo").val();
		var requestData = {
			"propNo" : propNo
		};

		var ajaxResponse = doAjaxLoading(
				'PropertyBillDistribution.html?getBillingMethod', requestData,
				'html');

		if (ajaxResponse != null && ajaxResponse != "") {
			var prePopulate = JSON.parse(ajaxResponse);

			$.each(prePopulate, function(index, value) {
				$('#flatNo').append(
						$("<option></option>").attr("value", value).text(
								(value)));
			});
			$('#flatNo').trigger("chosen:updated");
			$("#showFlatNo").show();
			// $("#serchBtn").hide();
			// $("#serchBtnDirect").show();
			$("#billingMethod").val('I');
		}
	} else {
		showErrorOnPage(errorList);
	}

}

function showSingleMultiple() {

	$('#selectall').prop('checked', false);
	$('.checkall').each(function() {
		this.checked = false;
	});

	if ($("input[name='specialNotGenSearchDto.specNotSearchType']:checked")
			.val() == "S") {
		$('.PropDetail').show();
		$('.propLable').show();
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.usageType').hide();
		$('.searchBtn').show();
		$('.sectionSeperator').hide();

		$('#assWard1').val('');
		$('#assWard2').val('');
		$('#locId').val('');

	} else if ($(
			"input[name='specialNotGenSearchDto.specNotSearchType']:checked")
			.val() == "M") {
		$('.PropDetail').hide();
		$('.wardZone').show();
		$('.Loc').show();
		$('.usageType').show();
		$('.searchBtn').show();
		$('.sectionSeperator').show();
		$('.PropDetail').val('');

		$('#propertyNo').val('');
		$('#oldPropertyNo').val('');
	} else {
		$('.PropDetail').hide();
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.propLable').hide();
		$('.usageType').hide();
		$('.searchBtn').show();
		$('.sectionSeperator').hide();
	}

}

function serachProperty() {

	var errorList = [];
	var billMethod = $("#billingMethod").val();
	if (billMethod == 'I') {
		var flatNo = $("#flatNo").val();
		if (flatNo == "" || flatNo == undefined || flatNo == null
				|| flatNo == '0') {
			errorList.push(getLocalMessage('Please select flat no'));
		}
	}
	if (errorList.length == 0) {
		showloader(true);

		var formAction = $('#PropertyBillDistribution').attr('action');
		var url = formAction + '?serachPropForBillDistribution';
		$('#PropertyBillDistribution').attr('action', url);

		$('#PropertyBillDistribution').submit();
				
	} else {
		displayErrorsOnPage(errorList);
	}
}

function updateDueDate(element){
	return saveOrUpdateForm(element,
			"Bill generation  done successfully",
			'PropertyBillDistribution.html', 'saveform');
}

function resetFormDetails(element){
	value = "PropertyBillDistribution.html";
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', value);
	$("#postMethodForm").submit();
	

}

function setBillDistriDate(element) {
	var multiBillDistrDate = $('#multiBillDistrDate').val();
	$('.checkall').each(function(i) {
		if (this.checked) {
			$('#billDistribDate' + i).val(multiBillDistrDate);
		}
	});
}
