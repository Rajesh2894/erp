var bpoURL = "BeneficiaryPaymentOrder.html";
$(document)
		.ready(
				function() {

					$('#year').datepicker(
							{
								changeMonth : false,
								changeYear : true,
								showButtonPanel : false,
								dateFormat : 'yy',
								maxDate : '0d',
								duration : 'fast',
								stepMonths : 12,
								monthNames : [ "", "", "", "", "", "", "", "",
										"", "", "", "", "", "" ],
								onChangeMonthYear : function(year) {
									$(this).val(year);
								}
							});

					$("#nextProcessId").hide();
					table_bpo=$("#bpoHome").dataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true,
								"aoColumnDefs": [{
									'bSortable': false,
									'aTargets': [ 0 ]
								}]
							});
					$("#searchBPO")
							.click(
									function() {
										var errorList = [];
										var serviceId = $('#serviceId').val();
										/*var paymentscheId = $('#paymentscheId')
												.val();
										var year = $('#year').val();
										var month = $('#month').val();*/

								        /*if (serviceId == undefined)
											serviceId = "";*/

										if (serviceId != '' && serviceId !='0') {
											var requestData = 'serviceId='
													+ serviceId
												/*	+ '&paymentscheId='
													+ paymentscheId + '&year='
													+ year + '&month=' + month*/;
											var table = $('#bpoHome').on('draw.dt', function () {
												selectAllCheck();
												selectCheckbox();
									        }).DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();

											var ajaxResponse = __doAjaxRequest(
													bpoURL
															+ '?filterSearchData',
													'POST', requestData, false,
													'json');

											var result = [];
											$
													.each(
															ajaxResponse,
															function(index) {
																var obj = ajaxResponse[index];
																result
																		.push([
																				'<div class="text-center"><input type="checkbox" '
																						+ 'name=" \''
																						+ obj.checkBox
																						+ '\'" id="checkBoxId'
																						+ index
																						+ '" value='
																						+ obj.beneficiaryNumber
																						+ '  class="checkboxClass" /></div>',
																				'<div class="text-center">'
																						+ obj.beneficiaryName
																						+ '\</div>',
																				'<div class="text-center">'
																						+ obj.beneficiaryNumber
																						+ '\</div>',
																				'<div class="text-right">'
																						+ obj.amount
																						+ '\</div>',
																				'<div class="text-center">'
																						+ obj.bankName
																						+ '\</div>',
																				'<div class="text-center">'
																						+ obj.ifscCode
																						+ '\</div>',
																				'<div class="text-center">'
																						+ obj.accountNumber
																						+ '\</div>' ]);

															});
											if (result.length > 0) {
												$("#nextProcessId").show();
											}
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage('social.select.security.schemeName.record'));
											displayErrorsOnPage(errorList);
										}
									});

					$('input:checkbox[class^="checkedcheckBox"]').each(
							function() {
								$('input:checkbox[class^="checkedcheckBox"]')
										.prop('checked', true);
							});
					
					$("#resetform").on("click", function() {
						window.location.reload("#BeneficiaryPaymentOrder")
					});
					
					$("#checkBoxIds").click(function () {
						checkBoxValidationCall();
					});
				});

function benefiDetailSubmit(element) {

}
function checkBoxValidationCall(element) {
	const checkOrUnchecked = $('input:checkbox[id=checkBoxIds]').is(':checked');
	if (checkOrUnchecked) {
		$('input:checkbox[class^="checkboxClass"]').each(function() {
			$('input:checkbox[class^="checkboxClass"]').prop('checked', true);
		});
	} else {
		$('input:checkbox[class^="checkboxClass"]').each(function() {
			$('input:checkbox[class^="checkboxClass"]').prop('checked', false);
		});
	}

}

function selectCheckbox(){
	$(".checkboxClass").change(function(){
		if($(".checkboxClass").length == $(".checkboxClass:checked").length) {
			$("#checkBoxIds").prop("checked", true);
		} else {
			$("#checkBoxIds").prop('checked', false);
		}
	});	
}
var selectAllCheck = function (){
	if($('.checkboxClass').is(':checked')){
		if($('.checkboxClass:checked').length == $('.checkboxClass').length){
			$('#checkBoxIds').prop('checked',true);
		}else{
			$('#checkBoxIds').prop('checked',false);
		}
	}else{
		$('#checkBoxIds').prop('checked',false);
	}

}

function saveBeneficiaryDetails(element) {

	var count = 0;
	var list = [];

	var errorList = [];
	var rowCount =table_bpo.fnGetData().length;
	for (let i = 0; i < rowCount; i++) {
		const checkOrUnchecked = $('input:checkbox[id=checkBoxId' + i + ']')
				.is(':checked');
		var certificateDate = $("#caertificateDate" +i).val();
		if (checkOrUnchecked) {
			count++;
			let checkBoxId = $('#checkBoxId' + i).val();
			list.push(checkBoxId);
		}
	}

	if (count > 0) {
		var remark = $('#remark').val();
		var requestData = {
			"list" : list,
			"remark" : remark,
		};
		var ajaxResponse = __doAjaxRequest(bpoURL + '?saveBeneficiaryDetails',
				'POST', requestData, false, 'html');
		$(formDivName).html(ajaxResponse);
		prepareDateTag();
	} else {
		errorList
				.push(getLocalMessage('social.atleast.select.oneRecord.proceed'));
		displayErrorsOnPage(errorList);
	}
	}


function saveRtgsPayment(approvalData) {

	var errorList = [];

	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;

	if (decision == undefined || decision == '')
		errorList.push(getLocalMessage('asset.info.approval'));
	else if (comments == undefined || comments == '')
		errorList.push(getLocalMessage('asset.info.comment'));

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,
				getLocalMessage('work.estimate.approval.creation.success'),
				'AdminHome.html', 'saveDecision');
	}

}

function resetBPO() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#BeneficiaryPaymentOrder").validate().resetForm();
}
