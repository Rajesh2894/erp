$(document).ready(function() {
	if ($("#hiddeMode").val() == "V") {
		
		$("#partyDetails :input").prop("disabled", true);
		$("#ContractAgreement :input,select").prop("disabled", true);
		$('.addCF3').attr('disabled', true);
		$('.addCF4').attr('disabled', true);
		$('.addCF5').attr('disabled', true);
		$('.addCF2').attr('disabled', true);
		$('.remCF2').attr('disabled', true);
		$('.remCF3').attr('disabled', true);
		$('.remCF4').attr('disabled', true);
		$('.remCF5').attr('disabled', true);
		$('.uploadbtn').attr('disabled', false);
		$(".backButton").removeProp("disabled");
	}

});

function OnChangePrimaryVender(cnt) {
	$('.contpPrimary').attr('checked', false);
	$(".contpPrimary").attr("value", "N");
	$("#contp2Primary" + cnt).prop("checked", true);
	$("#contp2Primary" + cnt).attr("value", "Y");
}

function getEmpBasedOnDesgnation(obj) {
	var url = "ContractAgreement.html?getEmpBasedOnDesgnation";
	var degId = $(obj).val();
	var postdata = "degID=" + degId;

	var json = __doAjaxRequest(url, 'POST', postdata, false, 'json');
	$('#representBy option').remove();
	var optionsAsString = "<option value='0'>select Employee</option>";
	for (var j = 0; j < json.length; j++) {

		optionsAsString += "<option value='" + json[j].lookUpId + "'>"
				+ json[j].descLangFirst + "</option>";
	}
	$('#representBy').append(optionsAsString).trigger("chosen:updated");
}


function getDesgBasedOnDept(obj) {
	var url = "ContractAgreement.html?getDesgBasedOnDept";
	var languageId= $("#languageId").val();
	var deptId = $(obj).val();
	var postdata = "deptId=" + deptId;
	var json = __doAjaxRequest(url, 'POST', postdata, false, 'json');
	$('#desigantionId option').remove();
	var selectvar = getLocalMessage('contract.master.select.designation');
	var optionsAsString = "<option value='0'>"+selectvar+"</option>";
	for (var j = 0; j < json.length; j++) {
		if(languageId == 1){
			optionsAsString += "<option value='" + json[j].lookUpId + "'>"
			+ json[j].descLangFirst + "</option>";
		}else{
			optionsAsString += "<option value='" + json[j].lookUpId + "'>"
			+ json[j].descLangSecond + "</option>";
		}
		
	}
	$('#desigantionId').append(optionsAsString).trigger("chosen:updated");
}

function getVenderNameOnVenderType(count) {
	var url = "ContractAgreement.html?getVenderNameOnVenderType";
	var venTypeId = $("#venderType" + count).val();
	var postdata = "venTypeId=" + venTypeId;

	var json = __doAjaxRequest(url, 'POST', postdata, false, 'json');
	$('#vendorId' + count + ' option').remove();
	var optionsAsString = "<option value='0'>Select Contractor</option>";
	for (var j = 0; j < json.length; j++) {

		optionsAsString += "<option value='" + json[j].lookUpId + "'>"
				+ json[j].descLangFirst + "</option>";
	}
	$('#vendorId' + count).append(optionsAsString).trigger("chosen:updated");
}

function deleteCompleteRow(uploadId, uploadType) {
	if (uploadType == "UW") {
		if ($("#customFields3 tr").length != 2) {
			deleteCompletetRowAjax(uploadId);
		}
	}
	if (uploadType == "V") {
		if ($("#customFields5 tr").length != 2) {
			deleteCompletetRowAjax(uploadId);
		}
	}
	if (uploadType == "VW") {
		if ($("#customFields4 tr").length != 2) {
			deleteCompletetRowAjax(uploadId);

		}
	}
}

function deleteCompletetRowAjax(uploadId) {
	var url = "ContractAgreement.html?deleteCompleteRow";
	var postdata = "uploadId=" + uploadId;
	var json = __doAjaxRequest(url, 'POST', postdata, false);
}

function reorderULB() {
	$('.appendableClass').each(
			function(i) {
				// Ids
				var inc = i + 1;
				var p1 = 1 + i;
				$(this).find("input:text:eq(0)").attr("id",
						"contp1Name" + (inc));
				$(this).find("input:text:eq(1)").attr("id",
						"contp1Address" + (inc));
				$(this).find("input:text:eq(2)").attr("id",
						"contp1ProofIdNo" + (inc));
				$(this).find("input:hidden:eq(0)")
						.attr("id", "ULBType" + (inc));
				$(this).find("input:hidden:eq(1)").attr("id",
						"presentRow" + (inc));
				// names
				$(this).find("input:text:eq(0)").attr(
						"name",
						"contractMastDTO.contractPart1List[" + (inc)
								+ "].contp1Name");
				$(this).find("input:text:eq(1)").attr(
						"name",
						"contractMastDTO.contractPart1List[" + (inc)
								+ "].contp1Address");
				$(this).find("input:text:eq(2)").attr(
						"name",
						"contractMastDTO.contractPart1List[" + (inc)
								+ "].contp1ProofIdNo");
				$(this).find("input:hidden:eq(0)").attr(
						"name",
						"contractMastDTO.contractPart1List[" + (inc)
								+ "].contp1Type");
				$(this).find("input:hidden:eq(1)").attr(
						"name",
						"contractMastDTO.contractPart1List[" + (inc)
								+ "].active");
				$(this).find("a:eq(0)").attr("onclick",
						"fileUpload(" + (p1) + ",'UW')");
				$(this).find("a:eq(2)").attr("onclick",
						"deleteCompleteRow(" + (p1) + ",'UW')");
			});
}

function reorderVender() {
	var countven = 0;
	$('.appendableVenClass')
			.each(
					function(i) {
						// Ids
						var p2 = 11 + i;
						$(this).find("select:eq(0)").attr("id",
								"venderType" + (i));
						$(this).find("select:eq(1)").attr("id",
								"vendorId" + (i));
						$(this).find("input:text:eq(0)").attr("id",
								"venderName" + (i));
						$(this).find("input:radio:eq(0)").attr("id",
								"contp2Primary" + (i));
						$(this).find("input:hidden:eq(0)").attr("id",
								"contp2Type" + (i));
						$(this).find("input:hidden:eq(1)").attr("id",
								"presentRow" + (i));

						// names
						$(this).find("select:eq(0)").attr(
								"name",
								"contractMastDTO.contractPart2List[" + (i)
										+ "].contp2vType");
						$(this).find("select:eq(1)").attr(
								"name",
								"contractMastDTO.contractPart2List[" + (i)
										+ "].vmVendorid");
						$(this).find("input:text:eq(0)").attr(
								"name",
								"contractMastDTO.contractPart2List[" + (i)
										+ "].contp2Name");
						$(this).find("input:radio:eq(0)").attr(
								"name",
								"contractMastDTO.contractPart2List[" + (i)
										+ "].contp2Primary");
						$(this).find("input:hidden:eq(0)").attr(
								"name",
								"contractMastDTO.contractPart2List[" + (i)
										+ "].contp2Type");
						$(this).find("input:hidden:eq(1)").attr(
								"name",
								"contractMastDTO.contractPart2List[" + (i)
										+ "].active");

						$(this).find("select:eq(0)").attr("onchange",
								"getVenderNameOnVenderType(" + (i) + ")");
						$(this).find("input:radio:eq(0)").attr("onchange",
								"OnChangePrimaryVender(" + (i) + ")");
						$(this).find("a:eq(0)").attr("onclick",
								"fileUpload(" + (p2) + ",'V')");
						$(this).find("a:eq(2)").attr("onclick",
								"deleteCompleteRow(" + (p2) + ",'V')");
						countven++;
					});

	$('.appendableWitClass').each(
			function(i) {
				// Ids
				var countWit = i + countven;
				var p2w = 111 + i;
				$(this).find("input:text:eq(0)").attr("id",
						"contp2Name" + (countWit));
				$(this).find("input:text:eq(1)").attr("id",
						"contp2Address" + (countWit));
				$(this).find("input:text:eq(2)").attr("id",
						"contp2ProofIdNo" + (countWit));
				$(this).find("input:hidden:eq(0)").attr("id",
						"contp2Type" + (countWit));
				$(this).find("input:hidden:eq(1)").attr("id",
						"presentRow" + (countWit));
				// names
				$(this).find("input:text:eq(0)").attr(
						"name",
						"contractMastDTO.contractPart2List[" + (countWit)
								+ "].contp2Name");
				$(this).find("input:text:eq(1)").attr(
						"name",
						"contractMastDTO.contractPart2List[" + (countWit)
								+ "].contp2Address");
				$(this).find("input:text:eq(2)").attr(
						"name",
						"contractMastDTO.contractPart2List[" + (countWit)
								+ "].contp2ProofIdNo");
				$(this).find("input:hidden:eq(0)").attr(
						"name",
						"contractMastDTO.contractPart2List[" + (countWit)
								+ "].contp2Type");
				$(this).find("input:hidden:eq(1)").attr(
						"name",
						"contractMastDTO.contractPart2List[" + (countWit)
								+ "].active");
				$(this).find("a:eq(0)").attr("onclick",
						"fileUpload(" + (p2w) + ",'VW')");
				$(this).find("a:eq(1)").attr("onclick",
						"deleteCompleteRow(" + (p2) + ",'V')");

			});

}

function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	return false;
}

function closeAlertForm() {
	var childDivName = '.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}

function fileUpload(uploadId, uploadType) {
	var requestData = {
		'uploadId' : uploadId,
		'uploadType' : uploadType
	};
	var url = "ContractAgreement.html?fileUpload";
	$
			.ajax({
				url : url,
				data : requestData,
				type : 'POST',
				async : false,
				dataType : '',
				success : function(response) {
					var divName = '.child-popup-dialog';

					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					prepareTags();
					$("#file_list_0").hide();
					$("#file_list_1").hide();
					$(divName).removeClass('fancybox-close');
					showModalBoxWithoutClose(divName);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList
							.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
}

// /Contract Upload JS

function onLoadUploadPage() {
	var url = "ContractAgreement.html?getUploadedImage";
	var data = {};
	var returnData = __doAjaxRequest(url, 'post', data, false, 'json');
	$('#showPhoto, #showThumb, #removePhoto, #removeThumb').empty();
	var photoId = $("#photoId").val();
	var thumbId = $("#thumbId").val();
	$
			.each(
					returnData,
					function(key, value) {
						if (key == 0) {
							$('#showPhoto').append(
									'<img src="' + value
											+ '" class="img-thumbnail">');
							$('#removePhoto')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoId
													+ ',0)"><i class="fa fa-trash"></i></a>');
							$("#pho").attr('class', 'row');
							$("#addPhoto").attr('class', 'col-xs-6');
						} else if (key == 1) {
							$('#showThumb').append(
									'<img src="' + value
											+ '" class="img-thumbnail" >');
							$('#removeThumb')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbId
													+ ',1)"><i class="fa fa-trash"></i></a>');
							$("#thum").attr('class', 'row');
							$("#addThumb").attr('class', 'col-xs-6');
						}
					});
}

function deleteSingleUpload(deleteMapId, no) {
	var url = "ContractAgreement.html?deleteSingleUpload";
	var requestData = {
		'deleteMapId' : deleteMapId,
		'deleteId' : no
	};
	var returnData = __doAjaxRequest(url, 'post', requestData, false, 'json');
	$('#showPhoto, #showThumb, #removePhoto, #removeThumb').empty();
	if (no == 0) {
		$("#pho").attr('class', 'text-center');
		$("#addPhoto").attr('class', '');
	} else {
		$("#thum").attr('class', 'text-center');
		$("#addThumb").attr('class', '');
	}
	var photoId = $("#photoId").val();
	var thumbId = $("#thumbId").val();
	$("#file_list_" + (photoId) + "").hide();
	$("#file_list_" + (thumbId) + "").hide();

	$
			.each(
					returnData,
					function(key, value) {
						if (key == 0) {
							$('#showPhoto').append(
									'<img src="' + value
											+ '"  class="img-thumbnail" >');
							$('#removePhoto')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoId
													+ ',0)"><i class="fa fa-trash"></i></a>');
						} else if (key == 1) {
							$('#showThumb').append(
									'<img src="' + value
											+ '"  class="img-thumbnail" >');
							$('#removeThumb')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbId
													+ ',1)"><i class="fa fa-trash"></i></a>');
						}
					});
}
function otherTask() {
	
	var url = "ContractAgreement.html?getUploadedImage";
	var data = {};
	var returnData = __doAjaxRequest(url, 'post', data, false, 'json');
	$('#showPhoto, #showThumb, #removePhoto, #removeThumb').empty();
	var photoId = $("#photoId").val();
	var thumbId = $("#thumbId").val();
	$("#file_list_" + (photoId) + " img").hide();
	$("#file_list_" + (thumbId) + " img").hide();
	$
			.each(
					returnData,
					function(key, value) {
						if (key == 0) {
							$('#showPhoto').append(
									'<img src="' + value
											+ '"  class="img-thumbnail" >');
							$('#removePhoto')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoId
													+ ',0)"><i class="fa fa-trash"></i></a>');
							$("#pho").attr('class', 'row');
							/*$("#addPhoto").attr('class', 'col-xs-6');*/
						} else if (key == 1) {
							$('#showThumb').append(
									'<img src="' + value
											+ '"  class="img-thumbnail" >');
							$('#removeThumb')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbId
													+ ',1)"><i class="fa fa-trash"></i></a>');
							$("#thum").attr('class', 'row');
							/*$("#addThumb").attr('class', 'col-xs-6');*/
						}
					});
}

function deleteUploadedFile() {
	var url = "ContractAgreement.html?deleteUploadedFile";
	var data = {
		"photoId" : $("#photoId").val(),
		"thumbId" : $("#thumbId").val(),
	};
	var returnData = __doAjaxRequest(url, 'post', data, false);
	$('.child-popup-dialog').hide();
	disposeModalBox();
	$.fancybox.close();
}

//D90803
function backToPartyDetails() {
	$.fancybox.close();
}

function submitUpload() {
	$('.child-popup-dialog').hide();
	disposeModalBox();
	$.fancybox.close();
}

function saveContractAgreementForm(element) {
	
	var errorList = [];
	var ulbWitness = [];
	var venWitness = [];

	var contpPrimary = $(".contpPrimary:checked").length;
	if (contpPrimary == 0)
		errorList.push(getLocalMessage("agreement.selectContractor"));

	/*var deptId = $.trim($("#deptId").val());
	if (deptId == 0 || deptId == "")
		errorList.push(getLocalMessage("agreement.selectUlbDept"));

	var desigantionId = $.trim($("#desigantionId").val());
	if (desigantionId == 0 || desigantionId == "")
		errorList.push(getLocalMessage("agreement.selectDesignation"));

	var representBy = $.trim($("#representBy").val());
	if (representBy == 0 || representBy == "")
		errorList.push(getLocalMessage("agreement.selectRepresentedBy"));*/

	/*$('.appendableClass')
			.each(
					function(i) {
						var level = i + 1;
						var contp1Name = $.trim($("#contp1Name" + level).val());
						var contp1Address = $.trim($("#contp1Address" + level)
								.val());
						var contp1ProofIdNo = $.trim($(
								"#contp1ProofIdNo" + level).val());

						if (contp1Name == 0 || contp1Name == "") {
							errorList
									.push(getLocalMessage("agreement.enterUlbWitnessName"
											)+ " " + level);
						}
						if (contp1Address == 0 || contp1Address == "") {
							errorList
									.push(getLocalMessage("agreement.enterUlbWitnessAddress"
											)+ " " + level);
						}
						if (contp1ProofIdNo == 0 || contp1ProofIdNo == "") {
							errorList
									.push(getLocalMessage("agreement.enterUlbWitnessProofId"
											)+ " " + level);
						}
						if (contp1ProofIdNo != ""
								&& contp1ProofIdNo.length != 12) {
							errorList
									.push(getLocalMessage("agreement.aadharNumber"
											)+ " " + level);
						} else {

							if (ulbWitness.includes(contp1ProofIdNo)) {
								errorList
										.push(getLocalMessage("agreement.duplicateAadharNumber")
												+ " "+level);
							}
							if (errorList.length == 0) {
								ulbWitness.push(contp1ProofIdNo);
							}

						}

					});
*/
	var count = 0;
	$('.appendableVenClass')
			.each(
					function(i) {
						count++;
						var venderType = $.trim($("#venderType" + i).val());

						var vendorId = $.trim($("#vendorId" + i).val());
						var venderName = $.trim($("#venderName" + i).val());
						var level = i + 1;
						if (venderType == 0 || venderType == "") {
							errorList
									.push(getLocalMessage("agreement.selectContractorType"
											)+ " " + level);
						}
						if (vendorId == 0 || vendorId == "") {
							errorList
									.push(getLocalMessage("agreement.selectContractorName"
											)+ " " + level);
						}
						/*if (venderName == 0 || venderName == "") {
							errorList
									.push(getLocalMessage("agreement.selectContractorRepresented"
											)+ " " + level);
						}*/
					});

	$('.appendableWitClass')
			.each(
					function(i) {
						var level = i + 1;
						var wit = i + count;
						var contp2Name = $.trim($("#contp2Name" + wit).val());
						var contp2Address = $.trim($("#contp2Address" + wit)
								.val());
						var contp2ProofIdNo = $
								.trim($("#contp2ProofIdNo" + wit).val());
						if (contp2Name == 0 || contp2Name == "") {
							errorList
									.push(getLocalMessage("agreement.enterContractorWitnessName"
											)+ " " + level);
						}
						if (contp2Address == 0 || contp2Address == "") {
							errorList
									.push(getLocalMessage("agreement.enterContractorWitnessAddress"
											)+ " " + level);
						}
						if (contp2ProofIdNo == 0 || contp2ProofIdNo == "") {
							errorList
									.push(getLocalMessage("agreement.enterContractorWitnessProofId"
											)+ " " + level);
						}
						if (contp2ProofIdNo != ""
								&& contp2ProofIdNo.length != 12) {
							errorList
									.push(getLocalMessage("agreement.ContractorWitnessAadharNumber"
											)+ " " + level);
						} else {

							if (venWitness.includes(contp2ProofIdNo)) {
								errorList
										.push(getLocalMessage("agreement.duplicateContractorWitnessAadharNumber")
												+ level);
							}
							if (errorList.length == 0) {
								venWitness.push(contp2ProofIdNo);
							}

						}
					});

	if (errorList.length > 0) {
		showRLValidation(errorList);
		return false;
	}

	$('#empName').val($("#representBy  option:selected").text());

	return saveOrUpdateForm(element,
			"Your application for contract Agreement saved successfully!",
			'ContractAgreement.html', 'save');

}

function BackToContractDetails() {
	var ajaxResponse = __doAjaxRequest(
			'ContractAgreement.html?backToContractDetails', 'POST', {}, false,
			'html');
	$('.content').html(ajaxResponse);
	$('#noa_header').show();
}
