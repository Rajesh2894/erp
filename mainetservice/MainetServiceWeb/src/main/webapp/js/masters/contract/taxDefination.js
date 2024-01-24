$(document).ready(
		function() {
			
			$("#taxTable").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true

			});
			$('#raTaxDetailsTab').DataTable();
			if ($("#saveMode").val() == 'V') {
				$("#taxDefination :input").prop("disabled", true);
				$("#backButton").prop("disabled", false);
			}

			if ($("#saveMode").val() == 'E') {
				if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
					$('#raTaxDetailsTab').DataTable().destroy();
				}
				$("#raTaxDetailsTab tbody tr").each(
						function(i) {
							var amtType = $("#taxFact" + i).find(
									"option:selected").attr('code');
							var taxCode = $("#taxId" + i).find(
									"option:selected").attr('code');

							if (amtType == 'PER') {
								$("#taxPercent" + i).attr('readonly', false);
								$("#taxValue" + i).attr('readonly', true);
							}

							else {
								$("#taxValue" + i).attr('readonly', false);
								$("#taxPercent" + i).attr('readonly', true);
							}

							/*if (taxCode != 'INC' || taxCode == null) {
								$("#cpdVendorSubType" + i).attr('disabled', true);
							}*/

						});

			}

		});

function addTaxDetails() {
	
	var errorList = [];
	errorList = validateTaxDetails();
	if (errorList.length == 0) {
		addTaxTableRow('raTaxDetailsTab');
	} else {
		$('#raTaxDetailsTab').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function validateTaxDetails() {
	
	var errorList = [];
	var taxList = [];
	if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}
	$("#raTaxDetailsTab tbody tr")
			.each(
					function(i) {
						var taxId = $("#taxId" + i).val();
						var vendorSubType = $("#cpdVendorSubType" + i).val();
						var taxType = $("#taxType" + i).val();
						var taxFact = $("#taxFact" + i).val();
						var taxFactCode = $("#taxFact" + i).find(
								"option:selected").attr('code');
						var taxPercent = $("#taxPercent" + i).val();
						var panCard = $("#panCard" + i).val();
						var taxCode = $("#taxId" + i).find("option:selected")
								.attr('code');
						var threshold = $("#threshold" + i).val();
						var taxFixed = $("#taxFixed" + i).val();
						var taxValue = $("#taxValue" + i).val();
						var rowCount = i + 1;

						if (taxId == "" || taxId == null) {
							errorList.push(getLocalMessage("mb.selectTaxCat")
									+ rowCount);
						} else {
							if (vendorSubType == '') {
								if (taxList.includes(taxId)) {
									errorList
											.push(getLocalMessage("wms.DuplicateTaxCategory")
													+ rowCount);
								}
								/*if (errorList.length == 0) {
									taxList.push(taxId);
								}*/
							} else {
								if (panCard == "" || panCard == null) {
									errorList
											.push(getLocalMessage("wms.PleaseSelectPanCard")
													+ rowCount);
								}
							}

						}
						if (taxCode == 'INC' && taxCode != undefined
								&& taxCode != null) {
							if (vendorSubType == '') {
								errorList
										.push(getLocalMessage("wms.PleaseSelectVendorSubtype")
												+ rowCount);
							}
							if (threshold == '' || threshold == null) {
								errorList
										.push(getLocalMessage("wms.PleaseThresholdValue")
												+ rowCount);
							}
						}
						if (taxFixed == "" || taxFixed == null) {
							errorList
									.push(getLocalMessage("wms.PleaseTaxFixed")
											+ rowCount);
						}
						if (taxType == "" || taxType == null) {
							errorList.push(getLocalMessage("mb.mbTaxType")
									+ rowCount);
						}
						if (taxFact == "" || taxFact == null) {
							errorList.push(getLocalMessage("mb.mbTaxFactor")
									+ rowCount);
						}

						if (taxFactCode == 'PER') {
							if (taxPercent == "" || taxPercent == null) {
								errorList
										.push(getLocalMessage("master.enter.percentValue.entryNo")
												+ rowCount);
							}

							if (taxPercent != "" && taxPercent > 100) {
								errorList
										.push(getLocalMessage("wms.PleaseEnterTaxValueLessThan")
												+ rowCount);
							}
						}
						if (taxFactCode == 'AMT') {
							if (taxValue == "" || taxValue == null) {
								errorList
										.push(getLocalMessage("master.enter.taxAmt.entryNo")
												+ rowCount);
							}
						}

					});
	// checkForDuplicateIteamNo();

	return errorList;

}

function getTaxPerCent(obj) {
	
	var errorList = [];
	var taxPercent = $(obj).attr("id");

	var i = taxPercent.replace(/\D/g, '');

	if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}

	var taxFact = $("#taxFact" + i).val();
	var taxFactCode = $("#taxFact" + i).find("option:selected").attr('code');

	if (taxFactCode == 'PER') {
		$("#taxPercent" + i).attr('readonly', false);
		$("#taxValue" + i).attr('readonly', true);
		$("#taxValue" + i).val('');
		$("#taxPercent" + i).val('');
	} else {
		$("#taxPercent" + i).attr('readonly', true);
		$("#taxValue" + i).attr('readonly', false);
		$("#taxValue" + i).val('');
		$("#taxPercent" + i).val('');
	}

	// });
	$('#raTaxDetailsTab').DataTable();
}

function deleteTaxEntry(obj, ids) {
	deleteTableRow('raTaxDetailsTab', obj, ids);
	if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}
	$('#raTaxDetailsTab').DataTable();
}

function saveData(obj) {
	
	var errorList = [];
	var count = 0;
	if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}
	errorList = validateTaxDetails();
	if (errorList.length == 0)
		errorList = checkForDuplicateIteamNo();
	if (errorList.length == 0) {
		var url = "TaxDefination.html";
		return saveOrUpdateForm(obj, "", url, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
		$('#raTaxDetailsTab').DataTable();
	}

}

function openTaxDefinationForm(mode) {
	
	var errorList = [];
	var url = "TaxDefination.html?checkForTaxes";
	var isDefaulValue = __doAjaxRequest(url, 'POST', {}, false, 'json');

	if (isDefaulValue == "N") {
		errorList.push(getLocalMessage("scheme.master.validation.dedTaxAlrDef"));
		displayErrorsOnPage(errorList);
	} else {
		var divName = formDivName;
		var requestData = {
			"mode" : mode
		}
		var url = "TaxDefination.html?openTaxDefinationForm";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(response);
	}

}

function searchTaxDefination() {
	
	var errorList = [];
	var taxId = $("#taxId").val();
	var panCard = $("#panCard").val();

	if (taxId == '' && panCard == '') {
		errorList.push(getLocalMessage('tender.search.validation'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (taxId != '' || panCard != '') {

		var requestData = '&taxId=' + taxId + '&panCard=' + panCard;
		var table = $('#taxTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'TaxDefination.html?searchTaxDefinationForm', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();

			$
					.each(
							prePopulate,
							function(index) {
								var obj = prePopulate[index];

								result
										.push([
												obj.taxDesc,
												obj.vendorSubTypeDesc,
												'<div style="display: flex; justify-content: flex-end"> <div>'
														+ obj.taxThreshold
																.toFixed(2)
														+ '</div></div>',
												obj.taxUnitDesc,
												obj.taxPanApp,
												obj.lookUpDesc,
												obj.raTaxPercent,
												'<div style="display: flex; justify-content: flex-end"> <div>'
														+ obj.raTaxValue
																.toFixed(2)
														+ '</div></div>',

												'<td class="text-center">'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm" style="margin-left:10px;" onclick="getActionForDefination(\''
														+ obj.taxId
														+ '\',\'V\')" title="View Tax Deduction"><i class="fa fa-eye"></i></button>'
														+ '<button type="button"  class="btn btn-warning btn-sm" style="margin-left:10px;" onclick="getActionForDefination(\''
														+ obj.taxId
														+ '\',\'E\')" title="Edit Tax Deduction"><i class="fa fa-pencil"></i></button>' ]);
							});
			table.rows.add(result);
			table.draw();
		}
	}
}

function getActionForDefination(taxDefId, formMode) {
	
	var divName = formDivName;
	var url = "TaxDefination.html?updateTaxDefinitionForm";
	data = {
		"taxDefId" : taxDefId,
		"formMode" : formMode,
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function checkForDuplicateIteamNo() {
	
	var errorList = [];
	if ($("#saveMode").val() == 'E') {
		if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
			$('#raTaxDetailsTab').DataTable().destroy();
		}
	}

	$("#raTaxDetailsTab tbody tr")
			.each(
					function(i) {
						var taxId1 = $("#taxId" + i).val();
						var vendorSubType1 = $("#cpdVendorSubType" + i).val();
						var panCard1 = $("#panCard" + i).val();
						$("#raTaxDetailsTab tbody tr")
								.each(
										function(d) {
											var taxId2 = $("#taxId" + d).val();
											var vendorSubType2 = $(
													"#cpdVendorSubType" + d).val();
											var panCard2 = $("#panCard" + d)
													.val();
											var rowCount = d + 1;
											if (i != d) {
												if (taxId1 == taxId2
														&& vendorSubType1 == vendorSubType2
														&& panCard1 == panCard2) {
													if (errorList.length == 0)
														errorList
																.push(getLocalMessage("wms.DuplicateTaxCategory")
																		+ rowCount);
												}
											}

										});

					});
	if ($("#saveMode").val() == 'E') {
		$('#raTaxDetailsTab').DataTable();
	}
	return errorList;
}

function enableVendorSubtype(obj) {
	
	var taxId = $(obj).attr("id");

	var i = taxId.replace(/\D/g, '');

	if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}

	var tax = $("#taxId" + i).val();
	var taxFactCode = $("#taxId" + i).find("option:selected").attr('code');
	if (tax != "") {
		if (taxFactCode == 'INC' && taxFactCode != undefined
				&& taxFactCode != null) {
			$("#cpdVendorSubType" + i).attr('disabled', false);
		} else {
			$("#cpdVendorSubType" + i).attr('disabled', false);
		}
	}

}
function addTaxTableRow(tableId, isDataTable) {
	
	var id = "#" + tableId;
	// remove datatable specific properties
	if ((isDataTable == undefined || isDataTable) && $.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	$(".datepicker").datepicker("destroy");
	var content = $('' + id + ' tr').last().clone();
	$('' + id + ' tr').last().after(content);
	
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find('div.chosen-container').remove();
	content.find("select").attr("selected", "selected").val('');
	content.find("select:eq(0)").chosen().trigger("chosen:updated"); 
	content.find("select:eq(1)").chosen().trigger("chosen:updated"); 
	content.find("select:eq(2)").chosen().trigger("chosen:updated"); 
	content.find("select:eq(3)").chosen().trigger("chosen:updated"); 
	content.find("select:eq(4)").chosen().trigger("chosen:updated"); 
	content.find("select:eq(5)").chosen().trigger("chosen:updated"); 
	content.find("select:eq(6)").chosen().trigger("chosen:updated"); 
	content.find("input:checkbox").removeAttr('checked');
	
	reOrderTableIdSequence(id);
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	if(isDataTable == undefined || isDataTable) {
		// adding datatable specific properties
		dataTableProperty(id);
	}
}
