$(document).ready(
		function() {

			$('.datepicker').datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true
			});

			$("#datatables").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true

			});

			if ($("#typeOfApplication").val() == 'T') {
				$("#fromtoperiod").show();
				$("#numberofday").show();
			} else {
				$("#fromdate").val("");
				$("#todate").val("");
				$("#fromtoperiod").hide();
				$("#numberofday").hide();
			}
			$("#typeOfApplication").change(function(e) {

				if ($("#typeOfApplication").val() == 'T') {
					$("#fromtoperiod").show();
					$("#numberofday").show();
					$("#fromdate").data('rule-required', true);
					$("#todate").data('rule-required', true);
				} else {
					$("#fromdate").val("");
					$("#todate").val("");
					$("#fromtoperiod").hide();
					$("#numberofday").hide();
					$("#fromdate").data('rule-required', false);
					$("#todate").data('rule-required', false);
				}
			});

			$("input[name='csmrInfo.csPtype']").click(function() {

				if ($(this).attr('value') == 'U') {
					$("#ulbPlumber").show();
					$("#licensePlumber").hide();
					$("#licPlumber").attr("disabled", true);
					$("#plumber").attr("disabled", false);

				} else if ($(this).attr('value') == 'L') {
					$("#ulbPlumber").hide();
					$("#licensePlumber").show();
					$("#plumber").attr("disabled", true);
					$("#licPlumber").attr("disabled", false);
				}
			});

			if ($("input[name='csmrInfo.csPtype']").attr('value') == 'U') {

				$("#ulbPlumber").show();
				$("#licensePlumber").hide();
				$("#licPlumber").attr("disabled", true);
			} else {
				$("#ulbPlumber").hide();
				$("#licensePlumber").show();
				$("#plumber").attr("disabled", true);
			}
			$(".datepicker").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true
			});
			$(".disDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : 0,
				onSelect : function(selected) {
					$(".distDate").datepicker("option", "minDate", selected)
				}
			});
			$(".distDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : 0,
			});
			var dateFields = $('.disDate,.distDate');
			dateFields.each(function() {
				var fieldValue = $(this).val();
				if (fieldValue.length > 10) {
					$(this).val(fieldValue.substr(0, 10));
				}
			});
			if ($("#saveMode").val() == 'V') {
				$("#IllegalConnectionNoticeGeneration").find("input,select")
						.attr("disabled", "disabled");
			}

		});
function generateNoticeForm(mode) {
	var divName = formDivName;
	var requestData = {
		"mode" : mode
	}
	var url = "IllegalConnectionNoticeGeneration.html?createNotice";
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function getPropertyDetails(element) {

	var propertyNo = $("#propertyNo").val();
	var theForm = '#IllegalConnectionNoticeGeneration';
	if (propertyNo != '') {
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'IllegalConnectionNoticeGeneration.html?getPropertyDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		var errMsg = returnData["errMsg"];
		if (errMsg != '' && errMsg != undefined) {
			var errorList = [];
			errorList.push(errMsg);
			showMessageOnSubmit(errMsg,
					'IllegalConnectionNoticeGeneration.html');
		} else {
			$(formDivName).html(returnData);
			prepareDateTag();
		}
	}

}

function showMessageOnSubmit(message, redirectURL) {

	var errMsgDiv = '.msg-dialog-box';
	var cls = "Ok";

	var d = '<h5 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function proceed() {
	window.location.href = "IllegalConnectionNoticeGeneration.html";
}
function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

function validateFormDetails() {
	var errorList = [];
	var propertyNo = $.trim($('#propertyNo').val());
	var csOname = $("#csOname").val();
	if (propertyNo == '') {
		errorList.push(getLocalMessage('water.select.prop.num'));
	}

	if (csOname == '') {
		errorList.push(getLocalMessage('water.enter.owner.name'));
	}

	var typeOfApplication = $("#typeOfApplication").val();
	var trmGroup1 = $("#trmGroup1").val();

	if (typeOfApplication == '') {
		errorList.push(getLocalMessage('water.validation.typeApplication'));
	}

	if (trmGroup1 == 0) {
		errorList.push(getLocalMessage('water.validation.tariff'));
	}

	else {
		if ($('#trmGroup1 option:selected').attr('code') == "H") {
			var hotelNumber = $("#hotelNumber").val().trim();
			if (hotelNumber == '') {
				errorList.push(getLocalMessage('water.enter.rooms'));
			}
		} else if ($('#trmGroup1 option:selected').attr('code') == "RFC") {
			var restaurantNumber = $("#restaurantNumber").val().trim();
			if (restaurantNumber == '') {
				errorList.push(getLocalMessage('water.enter.table.num'));
			}
		}
	}
	var csTYpe = document
			.querySelector('input[name="csmrInfo.csPtype"]:checked').value;

	if (csTYpe == 'U') {
		var plumber = $("#plumber").val();
		if (plumber == '') {
			errorList.push(getLocalMessage('water.select.plumber.name'));
		}
	}
	if (csTYpe == 'L') {
		var licPlumber = $("#licPlumber").val();
		if (licPlumber == '') {
			errorList.push(getLocalMessage('water.select.plumber.name'));
		}
	}

	return errorList;
}

function saveData(element) {

	var errorList = validateFormDetails();
	if (errorList.length == 0) {
		return saveOrUpdateForm(
				element,
				"Your application for Illegal Notice Generation saved successfully!",
				'IllegalConnectionNoticeGeneration.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}

}

function searchNotice() {

	var errorList = [];
	var count = 0;
	var codDwzid = $("#codDwzid1").val();

	if (codDwzid == '' || codDwzid == 0) {
		errorList.push(getLocalMessage('water.select.atleast.oneField'));
	}

	if (errorList.length == 0) {

		var theForm = "#IllegalConnectionNoticeGeneration";
		var requestData = __serializeForm(theForm);
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'IllegalConnectionNoticeGeneration.html?searchNotices',
				requestData, 'html');
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];

							result
									.push([

											'<div style="display: flex; justify-content: center"> <div>'
													+ obj.csIllegalNoticeNo
													+ '</div></div>',
											'<div style="display: flex; justify-content: center"> <div>'
													+ obj.csOname
													+ '</div></div>',
											'<div style="display: flex; justify-content: flex-end"> <div>'
													+ obj.csOadd
													+ '</div></div>',
											'<div style="display: flex; justify-content: center"> <div>'
													+ obj.csOcontactno
													+ '</div></div>',
											'<td >'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-10"  onclick="getActionForDefination(\''
													+ obj.csIdn
													+ '\',\'E\')" title="Edit Notice"><i class="fa fa-pencil"></i></button>'
													+ '<button type="button"  class="btn btn-warning btn-sm margin-right-10 margin-left-10"  onclick="getActionForDefination(\''
													+ obj.csIdn
													+ '\',\'V\')" title="View Notice"><i class="fa fa-eye"></i></button>'
													+ '<button type="button"  class="btn btn-warning btn-sm margin-right-10 margin-left-10"  onclick="getActionForDefination(\''
													+ obj.csIdn
													+ '\',\'P\')" title="Print Illegal Notice"><i class="fa fa-print"></i></button>' ]);

						});
		table.rows.add(result);
		table.draw();
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

function getActionForDefination(csId, mode) {

	var divName = formDivName;
	var url = "";
	data = {
		"csId" : csId,
		"mode" : mode
	};
	if (mode == 'P') {
		url = "IllegalConnectionNoticeGeneration.html?IllegalConnectionNoticePrint";
	} else {
		url = "IllegalConnectionNoticeGeneration.html?editNotice";
	}
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}