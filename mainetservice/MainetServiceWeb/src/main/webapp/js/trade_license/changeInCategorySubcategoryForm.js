 var removeIdArray=[];
 var removeSorRevised = [];

 $(document).ready(function() {
					reOrderItemDetailsSequence();
					$(document).on('change','.tradeCat',function() {
										var p = 1;
										var q = 0;
										var errorList = [];
										var type = ($('option:selected',
												$("#triCategory" + 1)).attr('code'));
										$("#itemDetails tbody tr")
												.each(
														function(i) {
															if (($('option:selected',$("#triCategory"+ p)).attr('code')) == 'TL') {
																$("#trdQuantity" + q).prop("disabled",true);
																$("#addBtn").hide();
																$(".delButton").hide();
															} else {
																$("#trdQuantity" + q).prop("disabled",false);
																$("#addBtn").show();
																$(".delButton").show();
															}
															if (type != ($('option:selected',$("#triCategory" + p)).attr('code'))) {
																errorList.push(getLocalMessage("tradelicense.validation.categoryType"));
																$("#triCategory" + p).val('0');
																displayErrorsOnPage(errorList);
															}
															p = p + 6;
															q = q + 1;
														});
									});

				});

function getLicenseDetails() {
	
	var errorList = [];
	var theForm = '#changeInCategorySubcategory';
	var trdLicno = $("#licenseNo").val();

	// errorList = validateDuplicateLicenseForm(errorList);

	var requestData = {
		'trdLicno' : trdLicno
	}
	var response = __doAjaxRequest(
			'ChangeInCategorySubcategoryForm.html?getLicenseDetails', 'POST',
			requestData, false, 'html');

	$(formDivName).html(response);
}

function editApplication(element) {

	
	var theForm = '#changeInCategorySubcategoryEdit';
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(
			'ChangeInCategorySubcategoryForm.html?editApplication', 'POST',
			requestData, false, 'html');

	$(formDivName).html(response);
	reOrderItemDetailsSequence(); // reorder id and Path
	// getOwnerTypeDetails();

}

function getChecklistAndCharges(obj) {
	
	var errorList = [];

	errorList = errorList.concat(validateitemDetailsTable());

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj,
				"Change in Category Sub Category Form Submitted Successfully",
				"ChangeInCategorySubcategoryForm.html?getCheckListAndCharges",
				'saveCategorySubCategoryForm');

	} else {
		displayErrorsOnPage(errorList);
	}
}

$(function() {
	
	/* To add new Row into table */
	$("#itemDetails").on('click', '.addItemCF', function() {
		
		var errorList = [];
		errorList = validateitemDetailsTable(errorList);
		if (errorList.length == 0) {
			
			var content = $("#itemDetails").find('tr:eq(1)').clone();
			$("#itemDetails").append(content);
			content.find("select").val('0');
			content.find("input:hidden").val('');
			content.find("input:text").val("");
			$('.error-div').hide();
			reOrderItemDetailsSequence(); // reorder id and Path
			//addCategory();
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

$(function() {
	
	/* To add new Row into table */
	$("#itemDetails").on('click', '.delButton', function() {
		

		debugger;
		
		//To remove entry from dto
		var inputId=this.id;		
		var left_text = inputId.lastIndexOf("_");
		var deletedRowCount = inputId.substring(left_text+1);		
		var data = {
					"deletedRowCount" : deletedRowCount
					};
		var URL = 'ChangeInCategorySubcategoryForm.html?deleteCategoryTableRow';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		
		/*var estimateId= $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		
		if(estimateId != ''){
			removeSorRevised.push(estimateId);
		}
		
		$('#removeREMsheet').val(removeIdArray);*/
		if ($("#itemDetails tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderItemDetailsSequence(); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});
});

function reOrderItemDetailsSequence() {
	
	$("#itemDetails tbody tr").each(
			function(i) {
				$(this).find("a:eq(0)").attr("id", "deleteRow_"+i);
				var utp = i;
				if (i > 0) {
					utp = i * 6;
				}
				// IDs
				$(this).find("input:hidden:eq(0)").attr("id",
						"triId" + (utp + 1)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(1)").attr("id",
						"triId" + (utp + 2)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(2)").attr("id",
						"triId" + (utp + 3)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(3)").attr("id",
						"triId" + (utp + 4)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");
				$(this).find("input:hidden:eq(4)").attr("id",
						"triId" + (utp + 5)).attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + (i)
								+ "].triId");

				$(this).find("select:eq(0)").attr("id", "triCategory" + (utp + 1));
				$(this).find("select:eq(1)").attr("id", "triCategory" + (utp + 2));
				$(this).find("select:eq(2)").attr("id", "triCategory" + (utp + 3));
				$(this).find("select:eq(3)").attr("id", "triCategory" + (utp + 4));
				$(this).find("select:eq(4)").attr("id", "triCategory" + (utp + 5));
				$(this).find("input:text:eq(0)").attr("id", "trdUnit" + i);

				$(this).find("select:eq(0)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCategory1");
				$(this).find("select:eq(1)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCategory2");
				$(this).find("select:eq(2)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCategory3");
				$(this).find("select:eq(3)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCategory4");
				$(this).find("select:eq(4)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCategory5");
				$(this).find("input:text:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseItemDetailDTO["+i+"].trdUnit");

			});
}

function validateitemDetailsTable(errorList) {
	var subCategory = [];
	var errorList = [];
	
	var rowCount = $('#itemDetails tr').length;
	if ($.fn.DataTable.isDataTable('#itemDetails')) {
		$('#itemDetails').DataTable().destroy();
	}

	if (errorList == 0)
		$("#itemDetails tbody tr")
				.each(
						function(i) {
							

							if (rowCount <= 2) {
								var itemCode1 = $("#triCategory" + 1).val();
								var itemCode2 = $("#triCategory" + 2).val();
								var itemCode3 = $("#triCategory" + 3).val();
								var itemCode4 = $("#triCategory" + 4).val();
								var itemCode5 = $("#triCategory" + 5).val();

							} else {
								var utp = i;
								utp = i * 6;
								var itemCode1 = $("#triCategory" + (utp + 1)).val();
								var itemCode2 = $("#triCategory" + (utp + 2)).val();
								var itemCode3 = $("#triCategory" + (utp + 3)).val();
								var itemCode4 = $("#triCategory" + (utp + 4)).val();
								var itemCode5 = $("#triCategory" + (utp + 5)).val();
								var level = i + 1;

							}

							if (itemCode1 == "" || itemCode1 == undefined
									|| itemCode1 == "0") {
								errorList
										.push(getLocalMessage("trade.select.category")
												+ " " + (i + 1));
							}

							if (itemCode2 == "" || itemCode2 == undefined
									|| itemCode2 == "0") {
								errorList
										.push(getLocalMessage("trade.select.subcategory")
												+ " " + (i + 1));
							} else {
								if (subCategory.includes(itemCode2)) {
									errorList
											.push(getLocalMessage("tradelicense.validation.dup.subcategory")
													+ (i + 1));
								}
								if (errorList.length == 0) {
									subCategory.push(itemCode2);
								}
							}
						});
	return errorList;
}

/*function generateChallanAndPayment(element) {
	
	var errorList = [];
	if (errorList.length == 0) {

		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(
					element,
					"Your application Data for Change in Category and Sub Category saved successfully!",
					'ChangeInCategorySubcategoryForm.html?PrintReport',
					'generateChallanAndPayement');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			return saveOrUpdateForm(
					element,
					"Your application Data for Change in Category and Sub Category saved successfully!",
					'ChangeInCategorySubcategoryForm.html?redirectToPay',
					'generateChallanAndPayement');
		} else {
			return saveOrUpdateForm(
					element,
					"Your application Data for Change in Category and Sub Category saved successfully!",
					'AdminHome.html', 'generateChallanAndPayement');
		}
	} else {
		displayErrorsOnPage(errorList);
	}

}*/

function generateChallanAndPayment(element) {
	var status;
	saveObj = element;
	var yes = getLocalMessage('license.yes');
	var no = getLocalMessage('license.No');
	var warnMsg = getLocalMessage('payment.popup');

	message = '<p class="text-blue-2 text-center padding-15">' + warnMsg
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" type=\'button\' value=\'' + yes
			+ '\'  id=\'yes\' ' + ' onclick="onPaymentYes()"/>&nbsp;'
			+ '<input class="btn btn-success " type=\'button\' value=\'' + no
			+ '\'  id=\'no\' ' + ' onclick="closeConfirmBoxForm()"/>'
			+ '</div>';

	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;

}

function onPaymentYes() {
	$.fancybox.close();
	var errorList = [];
	errorList = errorList.concat(validateitemDetailsTable());
	var status;

	if (errorList.length == 0) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			status =  saveOrUpdateForm(
					saveObj,
					"Your application Data for Change in Category and Sub Category saved successfully!",
					'ChangeInCategorySubcategoryForm.html?PrintReport',
					'generateChallanAndPayement');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			status =  saveOrUpdateForm(
					saveObj,
					"Your application Data for Change in Category and Sub Category saved successfully!",
					'ChangeInCategorySubcategoryForm.html?redirectToPay',
					'generateChallanAndPayement');
		} else {
			status =  saveOrUpdateForm(
					saveObj,
					"Your application Data for Change in Category and Sub Category saved successfully!",
					'AdminHome.html', 'generateChallanAndPayement');
			
		}
		if (!$.trim($('#validationerrordiv').html()).length) {
			agencyRegAcknow();
		}	
} else {
		displayErrors(errorList);

	}
}

function editCategoryForm(element){
	
	return saveOrUpdateForm(element,
			"Your application Data for Change In category And Sub Category saved successfully!!",
			'AdminHome.html', 'saveCategorySubCategoryForm');	
}

function agencyRegAcknow() {

	var URL = 'ChangeInCategorySubcategoryForm.html?printAgencyRegAckw';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var appId = $($.parseHTML(returnData)).find("#applicationId").html();
	var title = appId;
	var printWindow = window.open('', '_blank');

	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
function showConfirmBoxForTradeLicense(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("bt.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ sucessMsg.command.message + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="showScrutinyLabel()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

