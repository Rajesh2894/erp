$(document).ready(function() {
	$("#datatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true

	});
	$("#previous").dataTable();
	$(".reset").click(function() {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'raBillGeneration.html');
		$("#postMethodForm").submit();

	});

	if ($("#saveMode").val() == 'V') {
		$("#raBillGeneration :input").prop("disabled", true);
		$("#backButton").prop("disabled", false);
		calculateMbTotal();
		calculateTotalAmt();
	}
	if ($("#saveMode").val() == 'E') {
		calculateMbTotal();
	}

	// $('.add').addClass("hide");

	$("#raTaxDetailsTab tbody tr").each(function(i) {
		// $("#taxId" +i).find("option:selected").attr('code');
		$("#taxType" + i).attr('readonly', true);
		$("#taxPercent" + i).attr('readonly', true);
		$("#taxFact" + i).attr('readonly', true);
		$("#taxValue" + i).attr('readonly', true);
	});

$("#withHeldTable tbody tr").each(function(i) {
	if ($("#withHeldAmt"+i).val() == "" || $("#withHeldAmt"+i).val() == null || $("#withHeldAmt"+i).val() == undefined)
		$("#withHeldAmt"+i).val("0.00");
});
});

function getWorkName(obj) {
	var requestData = {
		"projId" : $(obj).val()
	}
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var response = __doAjaxRequest('raBillGeneration.html?worksName', 'post',
			requestData, false, 'html');
	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}

function openRaBillAddForm(formUrl, actionParam) {
	var divName = '.content-page';
	
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$("#projId").val('');
	$("#workId").val('');
	prepareTags();
}
function openRaBillGeneration() {
	var errorList = [];

	var projId = $("#projId").val();
	var workId = $("#workId").val();
	if (projId == '') {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (workId == '') {
		errorList.push(getLocalMessage('work.estimate.select.work.name'));
		displayErrorsOnPage(errorList);
		return false;
	} else {
		if (errorList.length == 0) {
			var url = "raBillGeneration.html?validateMbForRa";
			data = {
				"projId" : projId,
				"workId" : workId,
			};
			var ajaxResponse = __doAjaxRequest(url, 'post', data, false, 'json');
			if (ajaxResponse != "Y") {
				errorList
						.push(getLocalMessage('mb.no.measurement.record.found.ra.bill.generation'));
				displayErrorsOnPage(errorList);
				return false;
			} else {
				var divName = formDivName;
				var url = "raBillGeneration.html?CreateRaBill";
				data = {
					"projId" : projId,
					"workId" : workId,
				};
				var response = __doAjaxRequest(url, 'post', data, false, 'html');
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				prepareTags();
			}
		}
	}
}

function addTaxDetails() {
	var errorList = [];
	errorList = validateTaxDetails();
	if (errorList.length == 0) {
		addTableRow('raTaxDetailsTab');
	} else {
		$('#raTaxDetailsTab').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function addTaxDetailsWith(e) {
	var errorList = [];
    errorList = validateWithHeld(errorList);
	
	if (errorList.length > 0) { 
	      $("#errorDiv").show();
	      $("#errorDiv").removeClass('hide');
	       displayErrorsOnPage(errorList);
	 } else{
		var count = $('#withHeldTable tr').length - 1;
		  
	        	$("#errorDiv").addClass('hide');
				     // e.preventDefault();
						var clickedRow = $(this).parent().parent().index();
						var content = $('#withHeldTable tr').last().clone();
						$('#withHeldTable tr').last().after(content);
						content.find("input:text").attr("value", "");
						content.find("select").attr("selected", "selected").val('');
						content.find("input:text").val('');
						content.find("input:hidden").val('');
						reOrderMachineryDetails();
					}
		
}

function validateWithHeld(errorList){
	if ( $.fn.DataTable.isDataTable('#withHeldTable') ) {
	  $('#withHeldTable').DataTable().destroy();
	}
	   $('.withHeld').each(function(i) {
		     var taxDid = $("#taxDid" + i).val();
			 var remark = $("#remark" + i).val();
			 var withHeldAmt = $("#withHeldAmt" + i).val();
			 var no = i+1;	
			 if(taxDid =="" || taxDid =="0"){
				 errorList.push(getLocalMessage("Please select Tax Category")+" "+no);
			 }
			 if(remark == null || remark == ""){
				 errorList.push(getLocalMessage("Please enter remark")+" "+no); 
			 }
			 if(withHeldAmt == 0.00 || withHeldAmt == ""){
				 errorList.push(getLocalMessage("Please enter amount")+" "+no); 
			 }
			 				   
	   });
	  
	   return errorList;
}

function reOrderMachineryDetails(){
	$('.withHeld').each(function(i){		
		
		$(this).find("select:eq(0)").attr("id","taxDid" + (i));	
		$(this).find("input:text:eq(0)").attr("id" ,"remark" + (i));
		$(this).find("input:text:eq(1)").attr("id" ,"withHeldAmt" + (i));
		
		$(this).find("select:eq(0)").attr("name","billMasDto.raBillTaxDtoWith[" + i+ "].taxId");
		$(this).find("input:text:eq(0)").attr("name","billMasDto.raBillTaxDtoWith[" + i+ "].raRemark");
		$(this).find("input:text:eq(1)").attr("name","billMasDto.raBillTaxDtoWith[" + i+ "].raTaxValue");
		
	});
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
						var taxType = $("#taxType" + i).val();
						var taxFact = $("#taxFact" + i).val();
						var taxFactCode = $("#taxFact" + i).find(
								"option:selected").attr('code');
						var taxPercent = $("#taxPercent" + i).val();
						var taxValue = $("#taxValue" + i).val();
						var rowCount = i + 1;
						var taxDet = $("#taxId" + i).find("option:selected")
								.text();
						if (taxDet == 'Other Deduction Tax'
								|| taxDet == 'Withheld Deduction Tax') {
							if (taxValue == "" || taxValue == null) {
								errorList.push(getLocalMessage("mb.mbTaxvalue")
										+ rowCount);
							}
						} else {
							if (taxId == "" || taxId == null) {
								errorList
										.push(getLocalMessage("mb.selectTaxCat")
												+ rowCount);
							} else {

								if (taxList.includes(taxId)) {
									errorList
											.push(getLocalMessage("wms.DuplicateTaxCategory")
													+ rowCount);
								}
								if (errorList.length == 0) {
									taxList.push(taxId);
								}

							}

							if (taxType == "" || taxType == null) {
								errorList.push(getLocalMessage("mb.mbTaxType")
										+ rowCount);
							}
							if (taxFact == "" || taxFact == null) {
								errorList
										.push(getLocalMessage("mb.mbTaxFactor")
												+ rowCount);
							}
							if (taxValue == "" || taxValue == null) {
								errorList.push(getLocalMessage("mb.mbTaxvalue")
										+ rowCount);
							}
						}
					});

	return errorList;

}

function calculateMbTotal() {
	var sum = 0;
	$('#mbTable tbody .appendableClass').each(function(i) {

		var mbAmount = parseFloat($("#mbAmount" + i).val());
		var ChkBx = $("#check" + i).is(':checked');
		if (ChkBx) {
			if ((!isNaN(mbAmount))) {
				sum += mbAmount;
			}
		}

	});
	if (!isNaN(sum)) {
		$("#mbTotal").val(sum.toFixed(2));
		if ($("#tenderTypeCode").val() == "PER") {
			var amt = (parseFloat($("#mbTotal").val()) * parseFloat($(
					"#tenderValue").val())) / 100;
		} else {
			var amt = parseFloat(0);
		}

		$("#tenderAmount").val(amt.toFixed(2));
	}
	calculateSanctionedAmount();
	if ($("#saveMode").val() == 'E') {

		calculateTotalAmt();
	}
}

function getTaxPerCent(obj) {
	
	var errorList = [];
	var taxPercent = $(obj).attr("id");

	var i = taxPercent.replace(/\D/g, '');
	var taxChange = $(obj).attr("readonly");
	if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}

	/*
	 * $("#raTaxDetailsTab tbody .appendableClass").each( function(i) {
	 */
	if (taxChange == 'readonly') {

		return false;
	} else {
		var taxFact = $("#taxFact" + i).val();
		var taxFactCode = $("#taxFact" + i).find("option:selected")
				.attr('code');

		if (taxFactCode == 'PER') {
			$("#taxPercent" + i).attr('readonly', false);
			$("#taxValue" + i).attr('readonly', true);
			$("#taxValue" + i).val('');
		} else {
			$("#taxPercent" + i).val('');
			$("#taxPercent" + i).attr('readonly', true);
			$("#taxValue" + i).attr('readonly', false);
			$("#taxValue" + i).val('');
		}

		// });
		calculateTotalAmt();
		$('#raTaxDetailsTab').DataTable();
	}

}

function calculateAmt(obj) {
	var errorList = [];
	var sum = $("#mbTotal").val();
	var taxVal = $(obj).val();
	var taxPercent = $(obj).attr("id");
	var indexVal= taxPercent.charAt(taxPercent.length-1);
	var type = $("#taxFact" + indexVal).find("option:selected").attr('code');
	if (type != "AMT") {
	var num = taxPercent.replace(/\D/g, '');
	if (taxVal > 100) {
		errorList
				.push(getLocalMessage('wms.EnterTaxPercentageLessThanHundred'));
		displayErrorsOnPage(errorList);
		return false;
	}

	if (!isNaN(taxVal) && taxVal !== '') {
		if (!isNaN(sum) && sum != 0) {

			var amt = (parseFloat(sum) * parseFloat(taxVal)) / 100;
			$("#taxValue" + parseFloat(num)).val(amt.toFixed(2));
			if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
				$('#raTaxDetailsTab').DataTable().destroy();
			}
			calculateTotalAmt();
			$('#raTaxDetailsTab').DataTable();
		} else {
			$(obj).val('');
			errorList.push(getLocalMessage('wms.SelectMBEntry'));
			displayErrorsOnPage(errorList);
			return false;
		}
	}
 }
	else{	
		calculateTotalAmt();
	}
}

function calculateTotalAmt() {
	var count = 0;
	var totSum = 0;
	var sum = $("#mbTotal").val();
	if (sum != "") {
		if ($("#sanctionedAmt").val() != "")
			totSum = parseFloat($("#sanctionedAmt").val());
	}

	$("#raTaxDetailsTab tbody .appendableClass").each(function(i) {

		var taxValue = parseFloat($("#taxValue" + i).val());

		var taxType = $("#taxType" + i).val();
		if (taxType == "D" || taxType == "A" || taxType == "") {
			taxValue = taxValue * (1);
		}

		if ((!isNaN(taxValue))) {
			// ****Defect #40390******
			if (taxType == "A") {
				count += taxValue;
				
				/* Defect #86738 */
				if (!isNaN(count)) {
					$("#taxTotal").val(count.toFixed(2));

				}
				$("#raBillAmt")
				.val(Math.round((parseFloat(count) + parseFloat(totSum)).toFixed(2)));

			}
			if (taxType == "D") {
				count -= taxValue;
				if (!isNaN(count)) {
					$("#taxTotal").val(taxValue.toFixed(2)); // #118933-changes for Deduction total amount negative sign  .

				}
				$("#raBillAmt")
				.val(Math.round(parseFloat(totSum) - (parseFloat(count - (count * 2))).toFixed(2)));

			}

		}
		else if(totSum != "") {
			$("#raBillAmt")
					.val(Math.round(parseFloat(totSum).toFixed(2)));

		}

	});
	
	 //#155188
	if (!isNaN(count)) {
		if(count < 0){
			count = count * -1;
			$("#taxTotal").val(count.toFixed(2));
	    }
		
	}
}
function deleteTaxEntry(obj, ids) {
	deleteTableRow('raTaxDetailsTab', obj, ids);
	if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}
	calculateTotalAmt();
	$('#raTaxDetailsTab').DataTable();
}

function deleteTaxEntryWith(obj, ids) {
	deleteTableRow('withHeldTable', obj, ids);
	if ($.fn.DataTable.isDataTable('#withHeldTable')) {
		$('#withHeldTable').DataTable().destroy();
	}
	calculateSanctionedAmount();
	$('#withHeldTable').DataTable();
}

function saveData(obj, flag) {
	var workId = $("#workOrderNo").val();
	var errorList = [];
	var count = 0;
	var contractBgDate=$("#contractBgDate").val();
	/*if ($.fn.DataTable.isDataTable('#raTaxDetailsTab')) {
		$('#raTaxDetailsTab').DataTable().destroy();
	}*/
	
	$('#raTaxDetailsTab tbody tr').each(function(i) {
		var taxId = $("#taxId" + i).val();
		if(taxId == ""){
			errorList.push(getLocalMessage('wms.enter.taxDetails'));
			}
	});
	$("#status").val(flag);
	/* User Story #34247 -tax details are non mandatory */
	// errorList = validateTaxDetails();
	var taxDid = $("#taxDid").val();
	if (taxDid == "" || taxDid == "0") {
		errorList.push(getLocalMessage('ra.bill.with.held.tax'));
		displayErrorsOnPage(errorList);
		return false;
	}
	$('#mbTable tbody tr').each(function(i) {
		var ChkBx = $("#check" + i).is(':checked');
		if (ChkBx) {
			count++;
		}
	});
	if (flag == "S") {
		var url = "raBillGeneration.html?getCurrentDate";
		var currentDate = __doAjaxRequest(url, 'post', {}, false, 'json');
		if (contractBgDate != "" && contractBgDate < currentDate) {
			errorList.push(getLocalMessage("wms.bgValidity.expire"));
		}

		var status = __doAjaxRequest("raBillGeneration.html?checkBudgetCode",
				'POST', {}, false, 'json');
		if (status != true) {
			errorList
					.push(getLocalMessage('work.ra.bill.add.financial.year.workdef'));
		}
	}
	if (count == 0) {
		errorList.push(getLocalMessage('wms.SelectMeasurementBookEntry'));
	}
	if (errorList.length == 0) {
		var url = "raBillGeneration.html?paymentOrder";
		return saveOrUpdateForm(obj, "", url, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
		$('#raTaxDetailsTab').DataTable();
	}
}

function searchRaBill() {
	var errorList = [];
	var count = 0;
	var projId = $("#projId").val();
	var workId = $("#workId").val();
	/* var mbNo = $("#mbNo").val(); */

	if (projId == '') {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
	}
	if (workId == '') {
		errorList.push(getLocalMessage('work.estimate.select.work.name'));
	}

	if (errorList.length == 0) {

		var requestData = '&projId=' + projId + '&workId=' + workId;
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading('raBillGeneration.html?searchRaBill',
				requestData, 'html');
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							if (obj.raStatus == 'Draft') {
								result
										.push([
												obj.projName,
												obj.workName,
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raBillno
														+ '</div></div>',
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raBillStringDate
														+ '</div></div>',
												'<div style="display: flex; justify-content: flex-end"> <div>'
														+ obj.raBillAmt
																.toFixed(2)
														+ '</div></div>',
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raStatus
														+ '</div></div>',
												'<td >'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''
														+ obj.raId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="getActionForDefination(\''
														+ obj.raId
														+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>' ]);
							} else if (obj.raStatus == 'Send For Approval') {
								result
										.push([
												obj.projName,
												obj.workName,
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raBillno
														+ '</div></div>',
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raBillStringDate
														+ '</div></div>',
												'<div style="display: flex; justify-content: flex-end"> <div>'
														+ obj.raBillAmt
																.toFixed(2)
														+ '</div></div>',
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raStatus
														+ '</div></div>',
												'<td >'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''
														+ obj.raId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>' ]);
							} else {
								result
										.push([
												obj.projName,
												obj.workName,
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raBillno
														+ '</div></div>',
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raBillStringDate
														+ '</div></div>',
												'<div style="display: flex; justify-content: flex-end"> <div>'
														+ obj.raBillAmt
																.toFixed(2)
														+ '</div></div>',
												'<div style="display: flex; justify-content: center"> <div>'
														+ obj.raStatus
														+ '</div></div>',
												'<td >'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''
														+ obj.raId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ '<button type="button"  class="btn btn-warning btn-sm margin-right-10 margin-left-10"  onclick="getPrintRABill(\''
														+ obj.raId
														+ '\',\'R\')" title="Print RA Bill"><i class="fa fa-print"></i></button>'
														+ '<button type="button"  class="btn btn-primary btn-sm margin-right-10 margin-left-30"  onclick="getActionForWorkFlow(\''
														+ obj.raId
														+ '\',\'V\')" title="Action History"><i class="fa fa-history"></i></button>' ]);
							}
							if (obj.raStatus == "Draft") {
								count++;
							}
						});
		table.rows.add(result);
		table.draw();
		/*
		 * if (count == 0) { $('.add').removeClass("hide"); } else {
		 * $('.add').addClass("hide"); }
		 */
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function getActionForDefination(raId, mode) {
	var divName = '.content-page';
	var url = "raBillGeneration.html?editRa";
	var actionParam = {
		'raId' : raId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getPrintRABill(workNo, mode) {

	var divName = formDivName;
	var actionParam = {
		'appNo' : workNo,
	}
	var ajaxResponse = __doAjaxRequest('WorkMBApproval.html?printRABill',
			'post', actionParam, false, 'html');
	var divContents = ajaxResponse;
	$(divName).html(divContents);
	prepareTags();

}

function getTaxDeatils() {
	var ChkBx = 0;
	$('#mbTable tbody .appendableClass').each(function(i) {
		var ChkBxs = $("#check" + i).is(':checked');
		if (ChkBxs) {
			ChkBx = 1;
		}
	});
	var errorList = [];
	if (ChkBx == 0) {
		errorList.push(getLocalMessage('wms.SelectMBEntry'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var rowcount=($("#raTaxDetailsTab tr").length)-3;
	$("#raTaxDetailsTab tbody tr")
			.each(
					function(i) {
						if ($("#taxId" + i).find("option:selected")
								.attr('code') != undefined) {
							if(rowcount==i){
							var taxDet = $("#taxId" + i)
									.find("option:selected").attr('code')
									.split(",");
							var taxDet1 = $("#taxId" + i).find(
									"option:selected").text();
							var taxFixed = taxDet[8];

							if (taxDet1 == 'Other Deduction Tax'
									|| taxDet1 == 'Withheld Deduction Tax') {
								$("#taxType" + i).val("")
								$("#taxPercent" + i).val("");
								$("#taxFact" + i).val("");
								// $("#taxValue" + i).val("");
								$("#taxType" + i).attr('readonly', true);
								$("#taxPercent" + i).attr('readonly', true);
								$("#taxFact" + i).attr('readonly', true);
								$("#taxValue" + i).attr('readonly', false);
							} else {
								$("#taxType" + i).attr('readonly', true);

								$("#taxType" + i).val(taxDet[1]);
								$("#taxPercent" + i).val(taxDet[2]);
								$("#taxValue" + i).val(taxDet[3]);
								$("#taxFact" + i).val(taxDet[4]);
								var amtType = $("#taxFact" + i).find(
										"option:selected").attr('code');
								if (taxFixed == 'N') {
									if (amtType == 'PER') {
										$("#taxPercent" + i).attr('readonly',
												false);
										$("#taxValue" + i).attr('readonly',
												true);
									}

									else {
										$("#taxValue" + i).attr('readonly',
												false);
										$("#taxPercent" + i).attr('readonly',
												true);
									}

									// $("#taxFact" + i).attr('readonly', true);

								} else {
									$("#taxPercent" + i).attr('readonly', true);
									$("#taxFact" + i).attr('readonly', true);
									$("#taxValue" + i).attr('readonly', false);
								}

								if (amtType == 'PER') {
									if (ChkBx != 0) {
										var sum = $("#mbTotal").val();
										var totSum = parseFloat($(
												"#sanctionedAmt").val());
										/*
										 * parseFloat($( "#tenderAmount").val()) +
										 * parseFloat(sum);
										 */
										if ((!isNaN(totSum))) {
											var amt;
											var percentAmt = taxDet[2];
											var percentAmt = taxDet[2];
											var taxPanApp = taxDet[5];
											var otherField = taxDet[6];
											if (otherField == 'INC'
													&& otherField != undefined
													&& otherField != null) {
												var threshold = taxDet[7];
												if (parseFloat(totSum) > parseFloat(threshold)) {
													amt = (parseFloat(totSum) * parseFloat(percentAmt)) / 100;
													$("#taxValue" + i).val(
															amt.toFixed(2));
												} else {
													amt = 0.00;
													$("#taxValue" + i).val(
															amt.toFixed(2));
												}

											} else {
												amt = (parseFloat(totSum) * parseFloat(percentAmt)) / 100;
												$("#taxValue" + i).val(
														amt.toFixed(2));
											}
											if ($.fn.DataTable
													.isDataTable('#raTaxDetailsTab')) {
												$('#raTaxDetailsTab')
														.DataTable().destroy();
											}
											calculateTotalAmt();
											$('#raTaxDetailsTab').DataTable();

										}
									} else {
										errorList
												.push(getLocalMessage('wms.SelectMBEntry'));
										displayErrorsOnPage(errorList);
									}
								} else if (amtType == 'AMT') {
									calculateTotalAmt();
								}
							}
						}
						} else {
							$("#taxType" + i).val("");
							$("#taxPercent" + i).val("");
							$("#taxValue" + i).val("");
							$("#taxFact" + i).val("");
						}

					});
}

function calculateSanctionedAmount() {
	var errorList = [];
	var amount=0.00
	$("#withHeldTable tbody tr").each(function(i) {
		var am=$("#withHeldAmt"+i).val();
		amount=(parseFloat(amount) + parseFloat($("#withHeldAmt"+i).val()))
	});
	var withHeldAmt = amount;
	
	
	var action = $("#tndAction").val();
	var taxDid = $("#taxDid").val();
	if (taxDid == "" || taxDid == "0") {
		errorList.push(getLocalMessage('ra.bill.with.held.tax'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var sum = $("#mbTotal").val();
	if (withHeldAmt != "" || withHeldAmt==0 ) {
		if (sum != "") {
			if (action == "A") {
				var amt = (parseFloat(sum) + parseFloat($("#tenderAmount")
						.val()))
						- parseFloat(withHeldAmt);

			} else {
				var amt = (parseFloat(sum) - parseFloat($("#tenderAmount")
						.val()))
						- parseFloat(withHeldAmt);
			}

			// parseFloat($("#withHeldAmt").val()) + parseFloat(sum);
			$("#sanctionedAmt").val(amt.toFixed(2));
		}
	} else {
		if (sum != "") {
			var amt = parseFloat($("#tenderAmount").val()) + parseFloat(sum);
			$("#sanctionedAmt").val(amt.toFixed(2));
		}
	}
	getTaxDeatils();
	calculateTotalAmt();
}

function backToApprvForm() {
	var divName = '.content-page';
	var url = "WorkMBApproval.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function getActionForWorkFlow(raId, mode) {
	var url = "raBillGeneration.html?getWorkFlowHistory";
	var actionParam = {
		'raId' : raId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	var errMsgDiv = '.msg-dialog-box';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(ajaxResponse);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
	return false;
}