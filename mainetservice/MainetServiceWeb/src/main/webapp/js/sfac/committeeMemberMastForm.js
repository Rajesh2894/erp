/**
 * 
 */

$(document).ready(function() {

	$("#committeeDetails").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$(".memberSince").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate : 0
	});

});

function searchForm(obj, formUrl, actionParam) {
	
	var errorList = [];
	var committeeTypeId = $("#committeeTypeId").val();
	var comMemberId = $("#comMemberId").val();
	var divName = '.content-page';
	if ((committeeTypeId == "0" || committeeTypeId == undefined)
			&& (comMemberId == "0" || comMemberId == undefined)) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
			"committeeTypeId" : committeeTypeId,
			"comMemberId" : comMemberId

		};
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function modifyCase(comMemberId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"comMemberId" : comMemberId,
		"mode" : mode
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function formForCreate() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'CommitteeMemberMaster.html?formForCreate', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'CommitteeMemberMaster.html?formForCreate', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function saveCommiteeDetForm(obj) {
	var errorList = [];
	errorList = validateCommiteeForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "Commitee Member Details Saved Successfully!",
				'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateCommiteeForm(errorList) {
	var committeeTypeId = $("#committeeTypeId").val();
	var organization = $("#organization").val();
	var memberName = $("#memberName").val();
	var designation = $("#designation").val();
	var contactNo = $("#contactNo").val();
	var emailId = $("#emailId").val();
	var fromDate = $("#fromDate").val();
	var status = $("#status").val();

	if (committeeTypeId == "" || committeeTypeId == "0") {
		errorList.push(getLocalMessage("sfac.com.mem.valiate.committeeTypeId"));
	}
	if (organization == "" || organization == undefined) {
		errorList.push(getLocalMessage("sfac.com.mem.valiate.organization"));
	}
	if (memberName == "" || memberName == undefined) {
		errorList.push(getLocalMessage("sfac.com.mem.valiate.memberName"));
	}
	if (designation == "" || designation == undefined) {
		errorList.push(getLocalMessage("sfac.com.mem.valiate.designation"));
	}
	if (contactNo == "" || contactNo == undefined) {
		errorList.push(getLocalMessage("sfac.com.mem.valiate.contactNo"));
	}
	if (emailId == undefined || emailId == "") {
		errorList.push(getLocalMessage("sfac.validation.iaEmailId"));
	}else {
		if (emailId != "") {
			var emailRegex = new RegExp(
					/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
			var valid = emailRegex.test(emailId);
			if (!valid) {
				errorList.push(getLocalMessage("sfac.valid.iaEmailId"));
			}
		}
	}
	if (fromDate == "" || fromDate == undefined) {
		errorList.push(getLocalMessage("sfac.com.mem.valiate.fromDate"));
	}
	if (status == "" || status == "0") {
		errorList.push(getLocalMessage("sfac.com.mem.valiate.status"));
	}
	return errorList;
}