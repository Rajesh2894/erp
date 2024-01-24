$(document)
	.ready(
		function() {

		    var proposalType = $("input:radio.type:checked");
		    proposalType = proposalType.val();
		    if (proposalType == 'F') {
			$('#yearId').prop('disabled', false).trigger(
				"chosen:updated");
			$('#sacHeadId').prop('disabled', false).trigger(
				"chosen:updated");
			$('#budgetHeadDesc').attr("disabled", false);
			$('#Amount').attr("disabled", false);
			$('#checkBudget').prop("disabled", false);
		    } else if (proposalType == 'N') {
			$('#yearId').prop('disabled', true).trigger(
				"chosen:updated");
			$('#yearId').val('').trigger('chosen:updated');
			$('#sacHeadId').prop('disabled', true).trigger(
				"chosen:updated");
			$('#sacHeadId').val('').trigger('chosen:updated');
			$('#budgetHeadDesc').attr("disabled", "disabled");
			$('#budgetHeadDesc').val('');
			$('#Amount').attr("disabled", "disabled");
			$('#Amount').val('');
			$('#checkBudget').prop("disabled", true);
		    }
		    $("#proposalDatatables").dataTable(
			    {
				"oLanguage" : {
				    "sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
					[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			    });

		    $('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
			yearRange : "-100:-0"
		    });

		    var dateFields = $('.datepicker');
		    dateFields.each(function() {
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
			    $(this).val(fieldValue.substr(0, 10));
			}
		    });

		    /* This method is used to Search All Proposal */
		    $('#searchCouncilproposal')
			    .click(
				    function() {
					var divName = '.content-page';
					var errorList = [];
					var proposalDepId = $('#proposalDepId')
						.val();
					var proposalNo = $('#proposalNo').val();
					var fromDate = $('#fromDate').val();
					var toDate = $('#toDate').val();
					var proposalStatus = $(
						'#proposalStatus').val();
					var wardId = $('#ward').val();
					if (proposalStatus == undefined) {
					    proposalStatus = "D";
					}
					if (proposalNo != ''
						|| proposalDepId != ''
						|| fromDate != 0 || toDate != 0
						|| wardId != 0) {
					    var requestData = '&proposalDepId='
						    + proposalDepId
						    + '&proposalNo='
						    + proposalNo + '&fromDate='
						    + fromDate + '&toDate='
						    + toDate
						    + '&proposalStatus='
						    + proposalStatus
						    + '&wardId=' + wardId;
					    var table = $('#proposalDatatables')
						    .DataTable();
					    table.rows().remove().draw();
					    $(".warning-div").hide();
					    /*
					     * var ajaxResponse =
					     * __doAjaxRequest(
					     * 'CouncilProposalMaster.html?searchCouncilProposal',
					     * 'POST', requestData, false,
					     * 'json');
					     */

					    var ajaxResponse = doAjaxLoading(
						    'CouncilProposalMaster.html?searchCouncilProposal',
						    requestData, 'html',
						    divName);

					    if (ajaxResponse.length == 0) {
						errorList
							.push(getLocalMessage("council.proposal.validation.grid.nodatafound"));
						displayErrorsOnPage(errorList);
						return false;
					    }

					    $(divName)
						    .removeClass('ajaxloader');
					    $(divName).html(ajaxResponse);
					    prepareTags();
					    /*
					     * var result = []; $ .each(
					     * ajaxResponse.councilProposalDto,
					     * function(index) { var obj =
					     * ajaxResponse.councilProposalDto[index];
					     * if (obj.proposalStatus ==
					     * "Draft") {
					     * 
					     * result .push([ index + 1,
					     * obj.proposalDeptName,
					     * obj.proposalNo, obj.wardDescJoin,
					     * obj.proposalDetails,
					     * obj.proposalStatus,
					     * obj.meetingDate,
					     * obj.meetingProposalStatus, '<td >' + '<button
					     * type="button" class="btn
					     * btn-blue-2 btn-sm margin-right-10 "
					     * style="margin-left:10px;"
					     * onclick="showGridOption(\'' +
					     * obj.proposalId + '\',\'V\')"
					     * title="View"><i class="fa
					     * fa-eye"></i></button>' + '<button
					     * type="button" class="btn
					     * btn-danger btn-sm btn-sm
					     * margin-right-10"
					     * onclick="showGridOption(\'' +
					     * obj.proposalId + '\',\'E\')"
					     * title="Edit"><i class="fa
					     * fa-pencil-square-o"></i></button>' + '</td>' + '<button
					     * type="button" class="btn
					     * btn-green-3 btn-sm btn-sm
					     * margin-right-10"
					     * onclick="sendForApproval(' +
					     * obj.proposalId + ",'" +
					     * obj.proposalType + '")"
					     * title="Send for Approval"><i
					     * class="fa fa-share-square-o"></i></button>' + '</td>'
					     * ]); } else {
					     * 
					     * result .push([ index + 1,
					     * obj.proposalDeptName,
					     * obj.proposalNo, obj.wardDescJoin,
					     * obj.proposalDetails,
					     * obj.proposalStatus,
					     * obj.meetingDate,
					     * obj.meetingProposalStatus, '<td >' + '<button
					     * type="button" class="btn
					     * btn-blue-2 btn-sm margin-right-10 "
					     * style="margin-left:10px;"
					     * onclick="showGridOption(\'' +
					     * obj.proposalId + '\',\'V\')"
					     * title="View"><i class="fa
					     * fa-eye"></i></button>' + '<button
					     * type="button" class="btn
					     * btn-danger btn-sm btn-sm
					     * margin-right-10"
					     * disabled="disabled"
					     * onclick="showGridOption(\'' +
					     * obj.proposalId + '\',\'E\')"
					     * title="Edit"><i class="fa
					     * fa-pencil-square-o"></i></button>' + '</td>' + '<button
					     * type="button" class="btn
					     * btn-green-3 btn-sm btn-sm
					     * margin-right-10"
					     * disabled="disabled"
					     * onclick="sendForApproval(' +
					     * obj.proposalId + ",'" +
					     * obj.proposalType + '")"
					     * title="Send for Approval"><i
					     * class="fa fa-share-square-o"></i></button>' + '</td>'
					     * ]);
					     * 
					     * result .push([ index + 1,
					     * obj.proposalDeptName,
					     * obj.proposalNo, obj.wardDescJoin,
					     * obj.proposalDetails,
					     * obj.proposalStatus,
					     * obj.meetingDate,
					     * obj.meetingProposalStatus, '<td >' + '<button
					     * type="button" class="btn
					     * btn-blue-2 btn-sm margin-right-10 "
					     * style="margin-left:10px;"
					     * onclick="showGridOption(\'' +
					     * obj.proposalId + '\',\'V\')"
					     * title="View"><i class="fa
					     * fa-eye"></i></button>' + '<button
					     * type="button" class="btn
					     * btn-danger btn-sm btn-sm
					     * margin-right-10"
					     * disabled="disabled"
					     * onclick="showGridOption(\'' +
					     * obj.proposalId + '\',\'E\')"
					     * title="Edit"><i class="fa
					     * fa-pencil-square-o"></i></button>' + '</td>' + '<button
					     * type="button" class="btn
					     * btn-green-3 btn-sm btn-sm
					     * margin-right-10"
					     * disabled="disabled"
					     * onclick="sendForApproval(' +
					     * obj.proposalId + ",'" +
					     * obj.proposalType + '")"
					     * title="Send for Approval"><i
					     * class="fa fa-share-square-o"></i></button>' + '</td>'
					     * ]); }
					     * 
					     * }); table.rows.add(result);
					     * table.draw();
					     */
					} else {
					    errorList
						    .push(getLocalMessage('council.member.validation.select.any.field'));
					    displayErrorsOnPage(errorList);
					}

				    });

		});

// Function used for Save and Validate Proposal
function saveProposalform(obj, cpdMode) {

    var errorList = [];
    var proposalNo = $('#proposalNo').val();
    var proposalType = $('input[type=radio]:checked').val();
    var proposalDate = $('#proposalDate').val();
    var proposalDepId = $('#proposalDepId').val();
    /* var proposalAmt = $('#proposalAmt').val(); */
    var proposalDetails = $('#proposalDetails').val();
    var proposalSource = $('#proposalSource').val();
    var wards = $('#wards').val();
    var budgetHeadDesc = $('#budgetHeadDesc').val();
    var yearId = $('#yearId').val();
    var sacHeadId = $('#sacHeadId').val();
    var proposalAmt = $('#Amount').val();

    if (budgetHeadDesc == '' && cpdMode == 'S' && proposalType == 'F') {
	errorList
		.push(getLocalMessage('council.proposal.validation.budgetHead'));
    }
    if (proposalNo == '') {
	errorList
		.push(getLocalMessage('council.proposal.validation.proposalNo'));
    }
    if (proposalDate == '') {
	errorList
		.push(getLocalMessage('council.proposal.validation.proposalDate'));
    }
    if (proposalDepId == '' || proposalDepId == '0') {
	errorList
		.push(getLocalMessage('council.proposal.validation.department'));
    }
    if (proposalType == undefined) {
	errorList
		.push(getLocalMessage('council.proposal.validation.proposaltype'));
    }
    if (proposalSource == 0) {
	errorList
		.push(getLocalMessage('council.proposal.validation.proposalsource'));
    }
    /*
     * if(proposalAmt ==''){
     * errorList.push(getLocalMessage('council.proposal.validation.amount')); }
     */
    if (proposalDetails == '') {
	errorList.push(getLocalMessage('council.proposal.validation.details'));
    }
    /*
     * if (wards == '0' || wards == undefined || wards == "") {
     * errorList.push(getLocalMessage('council.proposal.validation.wards')); }
     */

    if (proposalType == 'F') {
	if (yearId == '') {
	    errorList
		    .push(getLocalMessage("council.proposal.validation.yearId"));

	}
    }

    if (proposalType == 'F') {
	if (sacHeadId == '') {
	    errorList
		    .push(getLocalMessage("council.proposal.validation.sacHeadId"));

	}
    }
    if (proposalType == 'F') {
	if (proposalAmt == '') {
	    errorList
		    .push(getLocalMessage('council.proposal.validation.amount'));

	}
    }

    if (errorList.length > 0) {

	displayErrorsOnPage(errorList);
	return false;
    } else {
	return saveOrUpdateForm(obj, '', 'CouncilProposalMaster.html',
		'saveform');

    }

}

// ajax for add proposal form
function addProposalMaster(formUrl, actionParam, isMOMProposal, meetingId,
	agendaId) {

    var divName = '.content-page';
    var requestData = {
	"isMOMProposal" : isMOMProposal,
	"meetingId" : meetingId,
	"agendaId" : agendaId
    }

    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
	    'html', divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

function backProposalMasterForm() {

    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'CouncilProposalMaster.html');
    $("#postMethodForm").submit();
}

function resetProposalMaster(resetBtn) {
    $('#depart').val('').trigger('chosen:updated');
    $('#proposalDepId').val('').trigger('chosen:updated');
    $('#wards').val('').trigger('chosen:updated');
    resetForm(resetBtn);
}

// Function used to Edit and View Proposal
function showGridOption(proposalId, action) {

    var actionData;
    var divName = formDivName;
    var requestData = 'proposalId=' + proposalId;
    if (action == "E") {
	actionData = 'editCouncilProposalMasterData';
	var ajaxResponse = doAjaxLoading('CouncilProposalMaster.html?'
		+ actionData, requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
    }

    if (action == "V") {
	actionData = 'viewCouncilProposalMasterData';
	var ajaxResponse = doAjaxLoading('CouncilProposalMaster.html?'
		+ actionData, requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
    }

    // checking proposal Type is Financial or non-financial
    var proposalType = $('input[type=radio]:checked').val();
    if (proposalType == 'N' || action == "V") {
	$('#yearId').prop('disabled', true).trigger("chosen:updated");
	$('#sacHeadId').prop('disabled', true).trigger("chosen:updated");
	$('#budgetHeadDesc').attr("disabled", "disabled");
	$('#Amount').attr("disabled", "disabled");
	$('#checkBudget').prop("disabled", true);
    } else {
	$('#yearId').prop('disabled', false).trigger("chosen:updated");
	$('#sacHeadId').prop('disabled', false).trigger("chosen:updated");
	$('#budgetHeadDesc').attr("disabled", false);
	$('#Amount').attr("disabled", false);
	$('#checkBudget').prop("disabled", false);
    }

}

// Function used to sent proposal for approval
function sendForApproval(proposalId, proposalType) {
    
    var divName = '.content-page';
    var url = "CouncilProposalMaster.html?sendForApproval";
    var requestData = {
	'proposalId' : proposalId,
	'proposalType' : proposalType
    }
    var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
    showProposalApprovalStatus(ajaxResponse, proposalId);
    // $("#searchCouncilproposal").trigger("click");

}

// function used to show proposal approval status
function showProposalApprovalStatus(ajaxResponse, proposalId) {

    var errMsgDiv = '.msg-dialog-box';
    var message = '';
    var cls = '';
    var sMsg = '';
    var Proceed = getLocalMessage("council.proceed");
    if (ajaxResponse.checkStausApproval == "Y") {
	sMsg = getLocalMessage("council.proposal.approval.initiated");
    } else if (ajaxResponse.checkStausApproval == "N") {
	sMsg = getLocalMessage("council.proposal.approval.process.not.defined");
    } else if (ajaxResponse.checkStausApproval == "A") {
	sMsg = getLocalMessage("council.proposal.creation");
    } else {
	sMsg = getLocalMessage("council.proposal.approval.initiated.fail");
	sMsg1 = getLocalMessage("council.proposal.approval.contact.administration");
    }

    if (ajaxResponse.checkStausApproval == "Y") {
	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center text-blue-2 padding-12">' + sMsg
		+ '</p>' + '</div>';

	message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    } else if (ajaxResponse.checkStausApproval == "N") {

	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center text-blue-2 padding-12">' + sMsg
		+ '</p>' + '</div>';

	message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    } else if (ajaxResponse.checkStausApproval == "A") {

	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center text-blue-2 padding-12">' + sMsg
		+ '</p>' + '</div>';

	message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    } else {
	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center red padding-12">' + sMsg + '</p>'
		+ '<p class="text-center red padding-12">' + sMsg1 + '</p>'
		+ '</div>';

	message += '<div class=\'text-center padding-top-10 padding-bottom-10\'>'
		+ '<input class="btn btn-info" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    }
    $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
    $(errMsgDiv).html(message);
    $(errMsgDiv).show();
    $('#btnNo').focus();
    showModalBoxWithoutClose(errMsgDiv);
    return false;
}

function closeSend() {

    $("#postMethodForm").prop('action', 'CouncilProposalMaster.html');
    $("#postMethodForm").submit();
    $.fancybox.close();
}

function viewExpenditureDetails() {

    var errorList = [];
    var yearId = $('#yearId').val();
    var sacHeadId = $('#sacHeadId').val();
    var proposalAmt = $('#Amount').val();
    var proposalDepId = $('#proposalDepId').val();

    if (yearId == '') {
	errorList.push(getLocalMessage("council.proposal.validation.yearId"));
	displayErrorsOnPage(errorList);
	return false;
    }
    if (sacHeadId == '') {
	errorList
		.push(getLocalMessage("council.proposal.validation.sacHeadId"));
	displayErrorsOnPage(errorList);
	return false;
    }
    if (proposalAmt == '') {
	errorList.push(getLocalMessage('council.proposal.validation.amount'));
	displayErrorsOnPage(errorList);
	return false;
    }
    var requestData = {
	'yearId' : yearId,
	'sacHeadId' : sacHeadId,
	"proposalAmt" : proposalAmt,
	"proposalDepId" : proposalDepId
    };

    var ajaxResponse = __doAjaxRequest(
	    "CouncilProposalMaster.html?getBudgetHeadDetails", 'POST',
	    requestData, false, 'json');

    if (ajaxResponse.authorizationStatus == 'Y') {
	var message = '';
	var sMsg = '';
	message += '<h4 class="text-center">Budget Details</h4>';
	message += '<div class="margin-right-10 margin-left-10">';
	message += '<table class=\"table table-bordered"\>' + '<tr>'
		+ '<th>Budget' + '</th>' + '<td class="text-right"> '
		+ parseFloat(ajaxResponse.invoiceAmount).toFixed(2) + '</td> '
		+ '</tr>';
	message += '<tr>' + ' <th>Previous Expenditure' + '</th>'
		+ '<td class="text-right">'
		+ parseFloat(ajaxResponse.sanctionedAmount).toFixed(2)
		+ '</td>' + '</tr>';
	message += '<tr>' + ' <th>Current Expenditure' + '</th>'
		+ '<td class="text-right">'
		+ parseFloat(ajaxResponse.billAmount).toFixed(2) + '</td>'
		+ '</tr>';
	message += '<tr>' + ' <th>Balance' + '</th>'
		+ '<td class="text-right">'
		+ parseFloat(ajaxResponse.netPayables).toFixed(2) + '</td>'
		+ '</tr></table>';
	message += '</div>';
	if (ajaxResponse.disallowedRemark == 'Y') {
	    sMsg = 'account.budget.check.msg';
	    message += '<h4 class=\"text-center red padding-12\">' + sMsg
		    + '</h4>';
	}
    } else {
	errorList
		.push(getLocalMessage("proposal.no.budget.availabe.against.selected.account"));
	displayErrorsOnPage(errorList);
	return false;
    }
    $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
    $(errMsgDiv).html(message);
    $(errMsgDiv).show();
    $('#btnNo').focus();
    showModalBox(errMsgDiv);
    return false;
}

function setDefaultFY() {

    var proposalDate = $('#proposalDate').val();
    if (proposalDate != '') {
	var requestData = {
	    'proposalDate' : proposalDate,
	}
	var ajaxResponse = __doAjaxRequest(
		"CouncilProposalMaster.html?getFinancialYearByProposalDate",
		'POST', requestData, false, 'json');
	if (ajaxResponse != null) {
	    $('#yearId').val(ajaxResponse).trigger('chosen:updated');
	}
    }
}

$(".type").change(function() {

    var proposalType = $(this).val();
    var saveMode = $('#saveMode').val();
    if (proposalType == 'N' || saveMode == 'V') {
	$('#yearId').prop('disabled', true).trigger("chosen:updated");
	$('#yearId').val('').trigger('chosen:updated');
	$('#sacHeadId').prop('disabled', true).trigger("chosen:updated");
	$('#sacHeadId').val('').trigger('chosen:updated');
	$('#budgetHeadDesc').attr("disabled", "disabled");
	$('#budgetHeadDesc').val('');
	$('#Amount').attr("disabled", "disabled");
	$('#Amount').val('');
	$('#checkBudget').prop("disabled", true);
    } else {
	$('#yearId').prop('disabled', false).trigger("chosen:updated");
	$('#sacHeadId').prop('disabled', false).trigger("chosen:updated");
	$('#budgetHeadDesc').attr("disabled", false);
	$('#Amount').attr("disabled", false);
	$('#checkBudget').prop("disabled", false);
    }
});
