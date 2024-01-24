$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0

	});

	$('.chosen-select-no-results').chosen();
	
$('.alphaNumeric').keyup(function() {
		
		var regx = /^[A-Za-z0-9.\s]*$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
});


function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MilestoneEntryForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];


	var iaId = $("#iaId").val();
	var fy = $("#milestoneId").val();
	var divName = '.content-page';
	if ((iaId == "0" || iaId == "" || iaId == undefined)  ) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {

				"iaId" :iaId,
				"milestoneId":fy
		};
		var ajaxResponse = doAjaxLoading('MilestoneEntryForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function saveMilestoneEntryForm(obj) {

	var errorList = [];

	errorList = validateMilestoneForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "Milestone Entry Request Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateMilestoneForm(errorList){



	var iaId =$("#iaId").val();
	var milestoneId = $("#milestoneId").val();
	var description = $("#description").val();
	var overallBudget = $("#overallBudget").val();
	var targetAge = $("#targetAge").val();
	var percantageOfPayment = $("#percantageOfPayment").val();

	if (iaId == undefined || iaId == "" || iaId == "0") {
		errorList.push(getLocalMessage("sfac.validation.ianame"));
	}

	if (milestoneId == undefined || milestoneId == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.milestoneId"));
	}

	if (description == undefined || description == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.description"));
	}

	if (overallBudget == undefined || overallBudget == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.budget"));
	}

	if (targetAge == undefined || targetAge == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.targetAge"));
	}

	if (percantageOfPayment == undefined || percantageOfPayment == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.perofpayment"));
	}

	var rowCount = $('#msDetailsTable tr').length;

	$("#msDetailsTable tbody tr").each(function(i) {
		if(rowCount <= 3){

			var deliverables = $("#deliverables" + i).val();

			var constant = 1;
		}
		else{
			var deliverables = $("#deliverables" + i).val();
			var constant = i+1;
		}


		if (deliverables == undefined || deliverables == "" ) {
			errorList.push(getLocalMessage("sfac.milestone.validation.deliverables") + " " + (i + 1));
		}

	});

	var rowCount1 = $('#msCBBODetailsTable tr').length;
	$("#msCBBODetailsTable tbody tr").each(function(i) {
		if(rowCount1 <= 3){

			var cbboId = $("#cbboId" + i).val();
			var dateOfWorkOrder = $("#dateOfWorkOrder" + i).val();
			var noOfFPO = $("#noOfFPO" + i).val();
			var allocationBudget = $("#allocationBudget" + i).val();

			var constant = 1;
		}
		else{
			var cbboId = $("#cbboId" + i).val();
			var dateOfWorkOrder = $("#dateOfWorkOrder" + i).val();
			var noOfFPO = $("#noOfFPO" + i).val();
			var allocationBudget = $("#allocationBudget" + i).val();
			var constant = i+1;
		}


		if (cbboId == undefined || cbboId == "" || cbboId == "0") {
			errorList.push(getLocalMessage("sfac.milestone.validation.cbbo") + " " + (i + 1));
		}
		if (dateOfWorkOrder == undefined || dateOfWorkOrder == "") {
			errorList.push(getLocalMessage("sfac.milestone.validation.dateOfWorkOrder") + " " + (i + 1));
		}
		if (noOfFPO == undefined || noOfFPO == "") {
			errorList.push(getLocalMessage("sfac.milestone.validation.noOfFPO") + " " + (i + 1));
		}
		if (allocationBudget == undefined || allocationBudget == "" ) {
			errorList.push(getLocalMessage("sfac.milestone.validation.allocationBudget") + " " + (i + 1));
		}

	});

	return errorList;

}

function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MilestoneEntryForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function modifyCase(msId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	
	var requestData = {
			"mode" : mode,
			"msId" : msId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function addDelButton(obj) {
	var errorList = [];
	errorList = validateDelTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#msDetailsTable tr').last().clone();
		$('#msDetailsTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("textarea").val('');


		reordeDelTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteDelDetails(obj) {
	var errorList = [];
	var delDetailsRowLength = $('#msDetailsTable tr').length;
	if ($("#msDetailsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeDelTable();
		delDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeDelTable() {
	$("#msDetailsTable tbody tr").each(function(i) {
		// Id

		$("#deliverables").removeClass("alphaNumeric");
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('textarea:eq(0)').attr('id','deliverables' + i);



		$(this).find("input:text:eq(0)").attr("name", "milestoneMasterDto.milestoneDeliverablesDtos[" + i + "].sNo");

		$(this).find("textarea:eq(0)").attr("name", "milestoneMasterDto.milestoneDeliverablesDtos[" + i + "].deliverables");

		$("#sNo" + i).val(i + 1);

		$('.alphaNumeric').keyup(function() {
			
			var regx = /^[A-Za-z0-9.\s]*$/;
			
			var amount	=	$(this).val();
		
			if(!regx.test(amount))
			{
				amount = amount.substring(0, amount.length-1);
				
				$(this).val(amount);	
			} 
		});


	});
}

function validateDelTable() {
	var errorList = [];
	var rowCount = $('#msDetailsTable tr').length;	



	$("#msDetailsTable tbody tr").each(function(i) {
		if(rowCount <= 3){

			var deliverables = $("#deliverables" + i).val();

			var constant = 1;
		}
		else{
			var deliverables = $("#deliverables" + i).val();
			var constant = i+1;
		}


		if (deliverables == undefined || deliverables == "" ) {
			errorList.push(getLocalMessage("sfac.milestone.validation.deliverables") + " " + (i + 1));
		}

	});

	return errorList;
}


function addCBBOButton(obj) {
	var errorList = [];
	errorList = validateCBBOTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#msCBBODetailsTable tr').last().clone();
		$('#msCBBODetailsTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		content.find('[id^="cbboId"]').chosen().trigger("chosen:updated");

		reordeCBBOTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteCBBODetails(obj) {
	var errorList = [];
	var delDetailsRowLength = $('#msCBBODetailsTable tr').length;
	if ($("#msCBBODetailsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeCBBOTable();
		delDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeCBBOTable() {
	$("#msCBBODetailsTable tbody tr").each(function(i) {
		// Id

		$(".datepicker").removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNoCBBO" + (i));
		$(this).find('select:eq(0)').attr('id','cbboId' + i);
		$(this).find('input:text:eq(2)').attr('id','dateOfWorkOrder' + i);
		$(this).find('input:text:eq(3)').attr('id','targetAge' + i);
		$(this).find('input:text:eq(4)').attr('id','noOfFPO' + i);
		$(this).find('input:text:eq(5)').attr('id','allocationBudget' + i);



		$(this).find("input:text:eq(0)").attr("name", "milestoneMasterDto.milestoneCBBODetDtos[" + i + "].sNoCBBO");
		$(this).find("select:eq(0)").attr("name", "milestoneMasterDto.milestoneCBBODetDtos[" + i + "].cbboID").attr("onchange","getFPOCount(" + i + ")");;
		$(this).find("input:text:eq(2)").attr("name", "milestoneMasterDto.milestoneCBBODetDtos[" + i + "].dateOfWorkOrder");
		$(this).find("input:text:eq(3)").attr("name", "milestoneMasterDto.milestoneCBBODetDtos[" + i + "].targetAge");
		$(this).find("input:text:eq(4)").attr("name", "milestoneMasterDto.milestoneCBBODetDtos[" + i + "].noOfFPO");
		$(this).find("input:text:eq(5)").attr("name", "milestoneMasterDto.milestoneCBBODetDtos[" + i + "].allocationBudget");

		$("#sNoCBBO" + i).val(i + 1);

		$('#msCBBODetailsTable').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	});
}

function validateCBBOTable() {
	var errorList = [];
	var rowCount = $('#msCBBODetailsTable tr').length;	



	$("#msCBBODetailsTable tbody tr").each(function(i) {
		if(rowCount <= 3){

			var cbboId = $("#cbboId" + i).val();
			var dateOfWorkOrder = $("#dateOfWorkOrder" + i).val();
			var noOfFPO = $("#noOfFPO" + i).val();
			var allocationBudget = $("#allocationBudget" + i).val();

			var constant = 1;
		}
		else{
			var cbboId = $("#cbboId" + i).val();
			var dateOfWorkOrder = $("#dateOfWorkOrder" + i).val();
			var noOfFPO = $("#noOfFPO" + i).val();
			var allocationBudget = $("#allocationBudget" + i).val();
			var constant = i+1;
		}


		if (cbboId == undefined || cbboId == "" || cbboId == "0") {
			errorList.push(getLocalMessage("sfac.milestone.validation.cbbo") + " " + (i + 1));
		}
		if (dateOfWorkOrder == undefined || dateOfWorkOrder == "") {
			errorList.push(getLocalMessage("sfac.milestone.validation.dateOfWorkOrder") + " " + (i + 1));
		}
		if (noOfFPO == undefined || noOfFPO == "") {
			errorList.push(getLocalMessage("sfac.milestone.validation.noOfFPO") + " " + (i + 1));
		}
		if (allocationBudget == undefined || allocationBudget == "" ) {
			errorList.push(getLocalMessage("sfac.milestone.validation.allocationBudget") + " " + (i + 1));
		}

	});

	return errorList;
}

function getFPOCount(id) {
	var errorList = [];

	errorList = validateBudgetPercentage(errorList); 
	if (errorList.length == 0) {
		var budget = $('#overallBudget').val();
		var per = $('#percantageOfPayment').val();
		var cbboId = $("#cbboId"+id).val();
		var request = {
				"cbboId" : cbboId
		};
		var response = __doAjaxRequest('MilestoneEntryForm.html?getFPOCount', 'post', request,
				false, 'json');
		if (response == 0) {
			$('#noOfFPO'+id).attr("disabled", false);
		}else{
			$('#noOfFPO'+id).val(response);
		}

		$('#targetAge'+id).val($('#targetAge').val());



		var cal = (budget * per/ 100);
		$('#allocationBudget'+id).val(cal)
	}else{

		displayErrorsOnPage(errorList);
	}
}

function validateBudgetPercentage() {
	var errorList = [];
	var budget = $('#overallBudget').val();
	var per = $('#percantageOfPayment').val();

	if (budget == undefined || budget == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.budget"));
	}

	if (per == undefined || per == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.perofpayment"));
	}

	return errorList;
}
