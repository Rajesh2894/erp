/**
 * 
 */
$(document).ready(function() {

	$('.viewBlockTable').hide();
	$('.target').hide();

	$("#blockDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$("#blockDetails").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$(".datepic").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
	})

	var orgShortNm = $('#orgShortNm').val();
	var saveMode = $("#saveMode").val();
    if (orgShortNm != 'IA' && saveMode !='V') {
        $('#organizationNameId').chosen('destroy').find('option:selected').prop('selected', false);
        $('#organizationNameId').chosen();
    }if(orgShortNm != 'IA' && saveMode == 'V'){
	  $('#organizationNameId').chosen('destroy').find('option:selected').prop('selected', true);
	  $('#organizationNameId').chosen();
    }
});

function modifyCase(blockId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"blockId" : blockId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchForm(obj) {
	var errorList = [];
	var orgNameId = $("#organizationNameId").val();
	var orgTypeId = $("#orgTypeId").val();
	var allocationYearId = $("#allocationYearId").val();
	var sdb1 = $("#sdb1").val();
	var sdb2 = $("#sdb2").val();
	var sdb3 = $("#sdb3").val();
	var divName = '.content-page';
	if ((orgTypeId == "0" || orgTypeId == undefined)
			&& (allocationYearId == "0" || allocationYearId == undefined) && (sdb1 == "0" || sdb1 =="") && (sdb2 == "0" || sdb2 =="")
			&& (sdb3 == "0" || sdb3 =="")) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}/*
		 * else if ((orgTypeId != null || orgTypeId != '0')){
		 * errorList.push(getLocalMessage("sfac.validation.OrganizationName")); }
		 */
	if (errorList.length == 0) {
		var requestData = {
			"orgTypeId" : orgTypeId,
			"organizationNameId" : orgNameId,
			"allocationYearId" : allocationYearId,
			"sdb1" : sdb1,
			"sdb2" : sdb2,
			"sdb3" : sdb3
		};
		var ajaxResponse = doAjaxLoading(
				'AllocationOfBlocks.html?getAllBlockData', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('AllocationOfBlocks.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function saveAllocationOfBlockForm(obj) {
	var errorList = [];
	var orgNameId = $("#organizationNameId").val();
	var orgTypeId = $("#orgTypeId").val();
	var allocationYearId = $("#allocationYearId").val();
	var allocationTarget = $("#allocationTarget").val();
	var orgShortNm = $("#orgShortNm").val();

	if (orgNameId == "0" || orgNameId == undefined || orgNameId == "")
		errorList.push(getLocalMessage("sfac.validation.OrganizationName"));

	if (orgTypeId == "0" || orgTypeId == undefined || orgTypeId == "")
		errorList.push(getLocalMessage("sfac.validation.organizationType"));

	if (allocationYearId == "0" || allocationYearId == undefined
			|| allocationYearId == "")
		errorList.push(getLocalMessage("sfac.validation.AllocationYear"));
	if (orgShortNm == 'NPMA') {
		var request = {
			"orgTypeId" : orgTypeId,
			"orgNameId" : orgNameId,
			"allocationYearId" : allocationYearId
		};
		var response = __doAjaxRequest(
				'AllocationOfBlocks.html?checKDataExist', 'post', request,
				false, 'json');
		if (response == true) {
			errorList.push(getLocalMessage("sfac.validate.dataAlreadyExist"));
		}
		errorList = errorList.concat(validateTargetDetails());
	}
	if (orgShortNm == 'IA')
		errorList = errorList.concat(validateFormDetails());

	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "Block Details Saved Successfully!",
				'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

$(function() {
	/* To add new Row into table */
	$("#targetDetails").on('click', '.addTareget', function() {
		var errorList = [];
		var orgTypeId = $("#orgTypeId").find("option:selected").attr('code');
		errorList = validateTargetDetails(errorList);
		if (errorList.length == 0) {
			var content = $("#targetDetails").find('tr:eq(1)').clone();
			$("#targetDetails").append(content);
			content.find("select").val('0');
			content.find("input:hidden").val('');
			content.find("input:text").val("");
			$('.error-div').hide();
			reOrderTargetDetailsSequence(); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

$(function() {
	/* To add new Row into table */
	$("#targetDetails").on('click', '.delTarget', function() {
		if ($("#targetDetails tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderTargetDetailsSequence(); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("sfac.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});
});


$(function() {
	/* To add new Row into table */
	$("#blockDetails").on('click', '.addBlockDet', function() {
		var errorList = [];
		var orgTypeId = $("#orgTypeId").find("option:selected").attr('code');
		errorList = validateFormDetails(errorList);
		if (errorList.length == 0) {
			var content = $("#blockDetails").find('tr:eq(1)').clone();
			$("#blockDetails").append(content);
			content.find("select").val('0');
			content.find("input:hidden").val('');
			content.find("input:text").val("");

			$('.error-div').hide();
			reOrderBlockDetailsSequence(); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

$(function() {
	/* To add new Row into table */
	$("#blockDetails").on('click', '.delBlockDet', function() {
		if ($("#blockDetails tr").length != 2) {
			$(this).parent().parent().remove();

			reOrderBlockDetailsSequence(); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("sfac.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});
});
function validateFormDetails(errorList) {
	var block = [];
	var errorList = [];

	var rowCount = $('#blockDetails tr').length;
	if ($.fn.DataTable.isDataTable('#blockDetails')) {
		$('#blockDetails').DataTable().destroy();
	}

	if (errorList == 0)
		$("#blockDetails tbody tr")
				.each(
						function(i) {

							if (rowCount <= 2) {

								var stateId = $("#stateId" + i).val();
								var distId = $("#distId" + i).val();
								var blckId = $("#blckId" + i).val();

								var level = 1;
								var allocationCategory = $(
										"#allocationCategoryDet" + i).val();
								var allocationSubCategory = $(
										"#allocationSubCategoryDet" + i).val();
								var cbboId = $("#cbboId" + i).val();
								var cat = $("#allocationCategoryDet" + i).find("option:selected").attr('code');

							} else {
								var stateId = $("#stateId" + i).val();
								var distId = $("#distId" + i).val();
								var blckId = $("#blckId" + i).val();
								var allocationCategory = $(
										"#allocationCategoryDet" + i).val();
								var allocationSubCategory = $(
										"#allocationSubCategoryDet" + i).val();
								var cbboId = $("#cbboId" + i).val();
								var cat = $("#allocationCategoryDet" + i).find("option:selected").attr('code');

								var level = i + 1;

							}

							if (stateId == "" || stateId == undefined
									|| stateId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.SDB1")
												+ " " + (i + 1));
							}

							if (distId == "" || distId == undefined
									|| distId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.SDB2")
												+ " " + (i + 1));
							}
							if (blckId == "" || blckId == undefined
									|| blckId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.SDB3")
												+ " " + (i + 1));
							} else {
								if (block.includes(blckId)) {
									errorList
											.push(getLocalMessage("sfac.dup.block")
													+ (i + 1));
								}
								if (errorList.length == 0) {
									block.push(blckId);
								}
							}

							if (allocationCategory == ""
									|| allocationCategory == undefined
									|| allocationCategory == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.allCategory")
												+ " " + (i + 1));
							}

							if (cat =='SPC' && (allocationSubCategory == ""
									|| allocationSubCategory == undefined
									|| allocationSubCategory == "0")) {
								errorList
										.push(getLocalMessage("sfac.validation.alcTargetSubCategory")
												+ " " + (i + 1));
							}

							if (cbboId == "" || cbboId == undefined
									|| allocationCategory == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.cbboId")
												+ " " + (i + 1));
							}
						});
	return errorList;

}

function validateTargetDetails(errorList) {
	var errorList = [];
	var rowCount = $('#targetDetails tr').length;

	if (errorList == 0)
		$("#targetDetails tbody tr")
				.each(
						function(i) {
							if (rowCount <= 3) {
								var allocationCategory = $(
										"#allocationCategoryTarget" + i).val();
								var alcTargetSubCategory = $(
										"#alcTargetSubCategory" + i).val();
								var allocationTarget = $(
										"#allocationTarget" + i).val();
								var cat = $("#allocationCategoryTarget" + i).find("option:selected").attr('code');
								var constant = 1;
							} else {
								var allocationCategory = $(
										"#allocationCategoryTarget" + i).val();
								var alcTargetSubCategory = $(
										"#alcTargetSubCategory" + i).val();
								var allocationTarget = $(
										"#allocationTarget" + i).val();
								var cat = $("#allocationCategoryTarget" + i).find("option:selected").attr('code');
								var constant = i + 1;
							}

							if (allocationCategory == '0'
									|| allocationCategory == undefined
									|| allocationCategory == "") {
								errorList
										.push(getLocalMessage("sfac.validation.allocationCategory")
												+ " " + (i + 1));
							}
							
							
							if (cat == 'SPC' && (alcTargetSubCategory == '0'
									|| alcTargetSubCategory == undefined
									|| alcTargetSubCategory == "")) {
								errorList
										.push(getLocalMessage("sfac.validation.alcTargetSubCategory")
												+ " " + (i + 1));
							}
							

							if (allocationTarget == '0'
									|| allocationTarget == undefined
									|| allocationTarget == "") {
								errorList
										.push(getLocalMessage("sfac.validation.allocationTarget")
												+ " " + (i + 1));
							}
						});

	return errorList;
}

function reOrderBlockDetailsSequence() {

	$("#blockDetails tbody tr").each(
			function(i) {
				$(this).find("input:text:eq(0)").attr("id", "seqNo" + (i));
				$(this).find("select:eq(0)").attr("id", "stateId" + i);
				$(this).find("select:eq(1)").attr("id", "distId" + i);
				$(this).find("select:eq(2)").attr("id", "blckId" + i);

				$(this).find("select:eq(3)").attr("id",
						"allocationCategoryDet" + i);
				$(this).find("select:eq(4)").attr('id',
						'allocationSubCategoryDet' + i);
				$(this).find("select:eq(5)").attr("id", "cbboId" + i);

				$(this).find("select:eq(0)").attr("name",
						"blockAllocationDto.blockDetailDto[" + i + "].stateId")
						.attr("onchange", "getDistrictList(" + i + ")");
				$(this).find("select:eq(1)").attr("name",
						"blockAllocationDto.blockDetailDto[" + i + "].distId")
						.attr("onchange", "getBlockList(" + i + ")");
				$(this).find("select:eq(2)").attr("name",
						"blockAllocationDto.blockDetailDto[" + i + "].blckId");
				$(this).find("select:eq(3)").attr(
						"name",
						"blockAllocationDto.blockDetailDto[" + i
								+ "].allocationCategory").attr("onchange",
						"getAlcSubCatListDet(" + i + ")");

				$(this).find("select:eq(4)").attr(
						'name',
						'blockAllocationDto.blockDetailDto[' + i
								+ '].allocationSubCategory');

				$(this).find("select:eq(5)").attr("name",
						"blockAllocationDto.blockDetailDto[" + i + "].cbboId");
				$("#seqNo" + i).val(i + 1);
			});
}

function reOrderTargetDetailsSequence() {

	var rowCount = $('#targetDetails tr').length;
	
	for (var i = 0; i <= rowCount; i++) {
		$('.datepic').removeClass("hasDatepicker");
		
		$('#targetDetails tr').eq(i + 1).find('input[id^=sequence]').val(i + 1);

		$('#targetDetails tr').eq(i + 1).find('select[id^=allocationCategoryTarget]')
				.attr('id', 'allocationCategoryTarget' + i);
		$('#targetDetails tr').eq(i + 1).find('select[id^=allocationCategoryTarget]')
				.attr('name',
						'blockAllocationDto.targetDetDto[' + i
								+ '].allocationCategory').attr("onchange",
						"getAlcSubCatList(" + i + ")");

		$('#targetDetails tr').eq(i + 1).find(
				'select[id^=alcTargetSubCategory]').attr('id',
				'alcTargetSubCategory' + i);
		$('#targetDetails tr').eq(i + 1).find(
				'select[id^=alcTargetSubCategory]').attr(
				'name',
				'blockAllocationDto.targetDetDto[' + i
						+ '].allocationSubCategory');

		$('#targetDetails tr').eq(i + 1).find('input[id^=allocationTarget]')
				.attr('id', 'allocationTarget' + i);
		$('#targetDetails tr').eq(i + 1).find('input[id^=allocationTarget]')
				.attr(
						'name',
						'blockAllocationDto.targetDetDto[' + i
								+ '].allocationTarget');

		$('#targetDetails tr').eq(i + 1).find('input[id^=targetDate]').attr(
				'id', 'targetDate' + i);
		$('#targetDetails tr')
				.eq(i + 1)
				.find('input[id^=targetDate]')
				.attr('name',
						'blockAllocationDto.targetDetDto[' + i + '].targetDate');


	}
	changeMAxAndMinDate();
	
}

function showTable() {
	var orgTypeId = $("#orgTypeId").find("option:selected").attr('code');
	if (orgTypeId == 'IA') {
		/*
		 * $('#blockDetails th:nth-child(5)').hide(); $('#blockDetails
		 * th:nth-child(6)').hide(); $('#blockDetails td:nth-child(5)').hide();
		 * $('#blockDetails td:nth-child(6)').hide(); $("#blockDetails tbody
		 * tr").each(function(i) { $(".showFlag select").val("0"); });
		 */
		$('.target').show();
	} else {
		/*
		 * $('#blockDetails th:nth-child(5)').show(); $('#blockDetails
		 * th:nth-child(6)').show(); $('#blockDetails td:nth-child(5)').show();
		 * $('#blockDetails td:nth-child(6)').show(); $("#blockDetails tbody
		 * tr").each(function(i) { $(".showFlag input:text").val("");
		 * $(".showFlag select").val("0"); });
		 */
		$(".target").hide();
	}
}

function viewBlockDetails(orgTypeId, organizationNameId, allocationYearId, sdb1) {
	$('.viewBlockTable').show();
	var requestData = {
		"orgTypeId" : orgTypeId,
		"organizationNameId" : organizationNameId,
		"allocationYearId" : allocationYearId,
		"sdb1" : sdb1
	};
	var table = $('#ViewBlockDatatables').DataTable();
	table.rows().remove().draw();
	$(".warning-div").hide();
	var ajaxResponse = doAjaxLoading(
			'AllocationOfBlocks.html?getAllBlockDetails', requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);
	if (prePopulate.length == 0) {
		errorList.push(getLocalMessage("collection.validation.nodatafound"));
		displayErrorsOnPage(errorList);
		$("#errorDiv").show();
	} else {
		var result = [];
		$.each(prePopulate, function(index) {
			var dto = prePopulate[index];

			result.push([ '<div align="center">' + (index + 1) + '</div>',
					'<div align="center">' + dto.orgName + '</div>',
					'<div align="center">' + dto.alcYear + '</div>',
					'<div align="center">' + dto.state + '</div>',
					'<div align="center">' + dto.district + '</div>',
					'<div align="center">' + dto.block + '</div>' ]);

		});
		table.rows.add(result);
		table.draw();
	}
}

function ResetForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('AllocationOfBlocks.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function getOrganizationName() {

	$('#organizationNameId').html('');
	$('#organizationNameId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var orgid = $("#orgTypeId").find("option:selected").val();
	var postdata = 'orgid=' + orgid;

	var json = __doAjaxRequest('AllocationOfBlocks.html?getMasterDetail',
			'POST', postdata, false, 'json');
	$.each(json, function(index, value) {
		if (value.id != null) {
			$("#organizationNameId").append(
					$("<option></option>").attr("value", value.id).text(
							value.name));
		}
	});
	$("#organizationNameId").trigger("chosen:updated")
}

function getDistrictList(id) {
	var requestData = {
		"stateId" : $("#stateId" + id).val()
	};
	var URL = 'AllocationOfBlocks.html?getDistrictList';
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

function getBlockList(id) {
	var distId = $("#distId" + id).val();
	var orgTypeId = $("#orgTypeId").val();
	var requestData = {
		"distId" : distId,
		"orgTypeId" : orgTypeId
	};
	var URL = 'AllocationOfBlocks.html?getBlockList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#blckId' + id).html('');
	$('#blckId' + id).append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#blckId' + id).append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#blckId' + id).trigger("chosen:updated");
}

function getAlcSubCatList(id) {
	var cat = $("#allocationCategoryTarget" + id).find("option:selected").attr('code');
	if (cat == 'BLW'){
		$('#alcTargetSubCategory'+ id).attr("disabled", true);
	}else{
		$('#alcTargetSubCategory'+ id).removeAttr('disabled');
	var requestData = {
		"allocationCategory" : $("#allocationCategoryTarget" + id).val()
	};
	var URL = 'AllocationOfBlocks.html?getAlcSubCatList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#alcTargetSubCategory' + id).html('');
	$('#alcTargetSubCategory' + id).append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#alcTargetSubCategory' + id).append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#alcTargetSubCategory' + id).trigger("chosen:updated");
	}
}

function getAlcSubCatListDet(id) {
	var cat = $("#allocationCategoryDet" + id).find("option:selected").attr(
			'code');
	if (cat == 'BLW') {
		$('#allocationSubCategoryDet' + id).attr("disabled", true);
	} else {
		$('#allocationSubCategoryDet' + id).removeAttr('disabled');
		var requestData = {
			"allocationCategory" : $("#allocationCategoryDet" + id).val()
		};
		var URL = 'AllocationOfBlocks.html?getAlcSubCatList';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		$('#allocationSubCategoryDet' + id).html('');
		$('#allocationSubCategoryDet' + id).append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#allocationSubCategoryDet' + id).append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.lookUpDesc)));
		});
		
		$('#allocationSubCategoryDet' + id).trigger("chosen:updated");
	}
}

function changeMAxAndMinDate() {
	var errorList = [];
	var value = $("#allocationYearId").find("option:selected").attr('code');
   
   if (value == "0" || value == undefined || value == ""){
		errorList.push(getLocalMessage("sfac.validation.AllocationYear"));
		displayErrorsOnPage(errorList);
		return false
    }
	var val = $("#allocationYearId").val();
	var arr = value.split('-');
	var start = arr[0];
	var end = arr[1];
	var startDate = "01/04/" + start.trim();
	var endDate = "31/03/" + end.trim();
	$('.datepic').removeClass("hasDatepicker");
	$('.datepic').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : startDate,
		maxDate : endDate
	});
	return true;
}

function checkCbboDataExist(id){
	var orgNameId = $('#organizationNameId').val();
	var allocationYearId = $('#allocationYearId').val();
	var blckId = $("#blckId" + id).val();
	var categoryDet = $("#allocationCategoryDet" + id).val();
	var subCategoryDet = $("#allocationSubCategoryDet" + id).val();
	
	var requestData = {
			"orgNameId" : orgNameId,
			"allocationYearId" : allocationYearId,
			"blckId" : blckId,
			"categoryDet" :  categoryDet,
			"subCategoryDet" :subCategoryDet
		};
	var response = __doAjaxRequest('AllocationOfBlocks.html?checkCbboDataExist', 'post', requestData,
			false, 'json');
	if (response == true) {
		errorList.push(getLocalMessage("sfac.validate.dataAlreadyExist"));
		displayErrorsOnPage(errorList);
	}
}

function getDistrictData() {
	var errorList = [];
	if (errorList.length == 0){
	var requestData = {
		"sdb1" : $("#sdb1").val()
	};
	var URL = 'AllocationOfBlocks.html?getDistrictData';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdb2').html('');
	$('#sdb2').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdb2').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdb2').trigger("chosen:updated");
}
}

function getBlockData() {
	var sdb2 = $("#sdb2").val();
	
	var requestData = {
		"sdb2" : sdb2,
	};
	var URL = 'AllocationOfBlocks.html?getBlockData';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdb3').html('');
	$('#sdb3').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdb3').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdb3').trigger("chosen:updated");
}