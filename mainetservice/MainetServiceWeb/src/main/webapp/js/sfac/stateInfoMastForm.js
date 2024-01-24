$(document).ready(function() {
	$("#stateInfo").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	var parentOrg = $("#parentOrg").val();
	if (parentOrg == "Y") {
		$(".editButton").removeClass('hide');
	}

});

function getDistrictList() {
	var requestData = {
		"state" : $("#state").val()
	};
	var URL = 'StateInformationMaster.html?getDistrictList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#district').html('');
	$('#district').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#district').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#district').trigger("chosen:updated");
}

function formForCreate() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'StateInformationMaster.html?formForCreate', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj, formUrl, actionParam) {
	var errorList = [];
	var msg = '';
	var state = $("#state").val();
	var district = $("#district").val();
	var divName = '.content-page';
	var parentOrg = $("#parentOrg").val();
	if ((state == "0" || state == undefined)
			&& (district == "0" || district == undefined)) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {

		var requestData = {
			"state" : state,
			"district" : district
		};
		var table = $('#stateInfo').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'StateInformationMaster.html?getStateInfoDetails', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("collection.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			var result = [];
			$
					.each(
							prePopulate,
							function(index) {
								var dto = prePopulate[index];

								result
										.push([
												'<div align="center">'
														+ (index + 1)
														+ '</div>',
												'<div align="center">'
														+ dto.stateName
														+ '</div>',
												'<div align="center">'
														+ dto.distName 
														+ '</div>',
												'<div align="center">'
														+ dto.areaTypeDesc
														+ '</div>',
												'<div class="text-center">'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="getActionForDefination(\''
														+ dto.stId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ msg
														+ '<button type="button" class="btn btn-warning btn-sm btn-sm hide editButton"  onclick="getActionForDefination(\''
														+ dto.stId
														+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>',
												'</div>' ]);
							});
			table.rows.add(result);
			table.draw();
			if (parentOrg == "Y") {
				$(".editButton").removeClass('hide');
			}
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function ResetForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'StateInformationMaster.html?formForCreate', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function saveStateMasterForm(obj) {
	var errorList = [];
	var state = $("#state").val();
	var district = $("#district").val();
	var areaType = $("#areaType").val();
	var zone = $("#zone").val();
	var aspirationalDist = $("#aspirationalDist").val();
	var tribalDist = $("#tribalDist").val();
	var odop = $("#odop").val();
	var dataExistcheck = $("#dataExistcheck").val();

	if (state == "0" || state == undefined || state == "") {
		errorList.push(getLocalMessage("sfac.validation.state"));
	}

	if (district == "0" || district == undefined || district == "") {
		errorList.push(getLocalMessage("sfac.validation.district"));
	}

	if (areaType == "0" || areaType == undefined || areaType == "") {
		errorList.push(getLocalMessage("sfac.validation.areaType"));
	}

	/*if (zone == "0" || zone == undefined || zone == "") {
		errorList.push(getLocalMessage("sfac.validation.zone"));
	}
	if (aspirationalDist == "" || aspirationalDist == undefined
			|| aspirationalDist == "") {
		errorList.push(getLocalMessage("sfac.validation.aspirationalDist"));
	}

	if (tribalDist == "0" || tribalDist == undefined || tribalDist == "") {
		errorList.push(getLocalMessage("sfac.validation.tribalDist"));
	}
	*/
	if (dataExistcheck == true){
		 errorList.push(getLocalMessage("sfac.record.exist.validation"));
	}

	if (odop == "0" || odop == undefined || odop == "") {
		errorList.push(getLocalMessage("sfac.validation.odop"));
	}
	
	

	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"State Information Details Saved Successfully!",
				'StateInformationMaster.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function getActionForDefination(stId, formMode) {

	var divName = formDivName;
	var url = "StateInformationMaster.html?editAndViewForm";
	data = {
		"stId" : stId,
		"formMode" : formMode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();
}

function checkDataAlreadyExistByDist() {
	var errorList = [];
	var district =$("#district").val();
	var request = {
			"district" : district
		};
		var response = __doAjaxRequest('StateInformationMaster.html?checkDataAlreadyExistByDist', 'post', request,
				false, 'json');
		if (response == true){
			$('#dataExistcheck').val(response);
			errorList.push(getLocalMessage("sfac.record.exist.validation"));
			displayErrorsOnPage(errorList);
		}
}