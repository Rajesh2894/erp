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
	var ajaxResponse = doAjaxLoading('CircularNotificationForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];


	var circularTitle = $("#circularTitle").val();
	var circularNo = $("#circularNo").val();
	var divName = '.content-page';
	if ((circularNo == "" || circularNo == undefined) && (circularTitle == "" ||  circularTitle == undefined)) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {

				"circularTitle" :circularTitle,
				"circularNo" :circularNo
				
		};
		var ajaxResponse = doAjaxLoading('CircularNotificationForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}



function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CircularNotificationForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function modifyCase(cnId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"cnId" : cnId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getDistrictList(id) {
	var requestData = {
		"stateId" : $("#sdb1" + id).val()
	};
	var URL = 'CircularNotificationForm.html?getDistrictList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#distId' + id).html('');
	$('#distId' + id).append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#distId' + id).append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#distId' + id).trigger("chosen:updated");
}


function addRecpButton(obj) {
	var errorList = [];
	errorList = validateRecpTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#recpDetailsTable tr').last().clone();
		$('#recpDetailsTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		content.find('[id^="sdb1"]').chosen().trigger("chosen:updated");
		content.find('[id^="distId"]').chosen().trigger("chosen:updated");
		content.find('[id^="orgType"]').chosen().trigger("chosen:updated");

		reordeRecpTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteCBBODetails(obj) {
	var errorList = [];
	var delDetailsRowLength = $('#recpDetailsTable tr').length;
	if ($("#recpDetailsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeRecpTable();
		delDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeRecpTable() {
	$("#recpDetailsTable tbody tr").each(function(i) {
		// Id

		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('select:eq(0)').attr('id','orgType' + i);
		$(this).find('input:text:eq(2)').attr('id','orgName' + i);
		$(this).find('select:eq(1)').attr('id','sdb1' + i);
		$(this).find('select:eq(2)').attr('id','distId' + i);
		



		$(this).find("input:text:eq(0)").attr("name", "dto.circularNotiicationDetDtos[" + i + "].sNo");
		$(this).find("select:eq(0)").attr("name", "dto.circularNotiicationDetDtos[" + i + "].orgType");
		$(this).find("input:text:eq(2)").attr("name", "dto.circularNotiicationDetDtos[" + i + "].orgName");
		$(this).find("select:eq(1)").attr("name", "dto.circularNotiicationDetDtos[" + i + "].sdb1").attr("onchange","getDistrictList(" + i + ")");
		$(this).find("select:eq(2)").attr("name", "dto.circularNotiicationDetDtos[" + i + "].sdb2");
		
		$("#sNo" + i).val(i + 1);

		$('#recpDetailsTable').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});

		


	});
}

function validateRecpTable() {
	var errorList = [];
	var rowCount = $('#recpDetailsTable tr').length;	



	$("#recpDetailsTable tbody tr").each(function(i) {
		if(rowCount <= 3){

			var orgType = $("#orgType" + i).val();
			
			var constant = 1;
		}
		else{
			var orgType = $("#orgType" + i).val();
			var constant = i+1;
		}


		if (orgType == undefined || orgType == "" || orgType == "0") {
			errorList.push(getLocalMessage("sfac.circular.validation.orgType") + " " + (i + 1));
		}
		
	});

	return errorList;
}


function saveCircularForm(obj) {

	var errorList = [];

	errorList = validateCircularForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "Circular/Notification Request Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateCircularForm(errorList){



	var circularTitle =$("#circularTitle").val();
	var circularNo = $("#circularNo").val();
	var convenerName = $("#convenerName").val();
	var dateOfCircular = $("#dateOfCircular").val();
	

	if (circularNo == undefined || circularNo == "" ) {
		errorList.push(getLocalMessage("sfac.circular.validation.circularNo"));
	}

	if (circularTitle == undefined || circularTitle == "" ) {
		errorList.push(getLocalMessage("sfac.circular.validation.circularTitle"));
	}

	if (convenerName == undefined || convenerName == "" ) {
		errorList.push(getLocalMessage("sfac.circular.validation.convenerName"));
	}
	if (dateOfCircular == undefined || dateOfCircular == "" ) {
		errorList.push(getLocalMessage("sfac.circular.validation.dateOfCircular"));
	}

	/*if (emailBody == undefined || emailBody == "" ) {
		errorList.push(getLocalMessage("sfac.circular.validation.emailBody"));
	}*/


	var rowCount1 = $('#recpDetailsTable tr').length;
	$("#recpDetailsTable tbody tr").each(function(i) {
		if(rowCount1 <= 3){


			var orgType = $("#orgType" + i).val();
			
			var constant = 1;
		}
		else{
			var orgType = $("#orgType" + i).val();
			var constant = i+1;
		}


		if (orgType == undefined || orgType == "" || orgType == "0") {
			errorList.push(getLocalMessage("sfac.circular.validation.orgType") + " " + (i + 1));
		}

	});

	return errorList;

}

