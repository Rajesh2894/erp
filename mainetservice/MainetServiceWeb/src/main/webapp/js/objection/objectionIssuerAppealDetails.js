$(document)
		.ready(
				function() {
					var objectionDeptId = $('#objectionDeptId option:selected').attr('value');
					if(objectionDeptId!=""&&objectionDeptId!=undefined){
						getLocationByDeptName();	
					}
					$("#showFlatNo").hide();
					$("#save").hide();
					var isValidationError = $("#isValidationError").val();

					$('#rtiObjIssuerDetailsForm').validate({

						onkeyup : function(element) {
							this.element(element);
							console.log('onkeyup fired');
						},
						onfocusout : function(element) {
							this.element(element);
							console.log('onfocusout fired');
						}
					});

					prepareDateTag();

					$("#serviceId").change(
							function(e) {
								let serviceCode = $(
										'#serviceId option:selected').attr(
										'code');
								if (serviceCode == "RFA") {
									$("#proceedbutton").show();
									$("#save").hide();
									$("#serchBtn").hide();
									
								}
							});

					$('body').on(
							'focus',
							".hasMobileNo",
							function() {
								$('.hasMobileNo').keyup(
										function() {
											this.value = this.value.replace(
													/[^1-9][0-9]{9}/g, '');
											$(this).attr('maxlength', '10');
										});
							});

					$('body').on(
							'focus',
							".hasPincode",
							function() {
								$('.hasPincode').keyup(
										function() {
											this.value = this.value.replace(
													/[^1-9][0-9]{5}/g, '');
											$(this).attr('maxlength', '6');
										});
							});
					/*
					 * $("#objectionDeptId").change(function(e) {
					 * getObjectionServiceByDept(); });
					 */
					/*
					 * 
					 * $("#serviceId").change(function(e) { getDeptName(); });
					 */

					/*
					 * if(isValidationError == "Y"){ loadPreDataOnValidation(); }
					 */

					/*
					 * $("#serviceId").change(function(e) {
					 * 
					 * if($('#serviceId option:selected').attr('value') ==
					 * "1252") { $("#rtiAppealPayment").show(); } else
					 * if($('#serviceId option:selected').attr('value') == "48") {
					 * $("#rtiAppealPayment").hide(); }
					 * 
					 * 
					 * });
					 */

					// based on reference no get data from
					// tb_cfc_application_mst table
					$('#objectionReferenceNumber').on('blur',
									function() {

										var errorList = [];
										let deptCode = $(
												'#objectionDeptId option:selected')
												.attr('code');
										let ojectionOn = $(
												'#objectionOn option:selected')
												.attr('code');
										let objReferenceNo = $(
												'#objectionReferenceNumber')
												.val();
										if (deptCode == 'RTS'
												&& (ojectionOn == 'FRTS' || ojectionOn == 'SRTS')) {
											if (objReferenceNo != undefined
													&& objReferenceNo != '') {
												// AJAX call to get applicant
												// data from
												// tb_cfc_application_mst
												let request = {
													referenceNo : objReferenceNo
												}
												var applicantDataResponse = __doAjaxRequest(
														'FirstAppeal.html?get-applicant-details',
														'post', request, false,
														'json');
												if (applicantDataResponse.applicantDetailDTO != null) {
													let applicant = applicantDataResponse.applicantDetailDTO;
													applicant.applicantTitle != null ? $(
															'#title')
															.val(
																	applicant.applicantTitle)
															.trigger(
																	"chosen:updated")
															: '';
													applicant.applicantFirstName != null ? $(
															'#fName')
															.val(
																	applicant.applicantFirstName)
															: '';
													applicant.applicantMiddleName != null ? $(
															'#mName')
															.val(
																	applicant.applicantMiddleName)
															: '';
													applicant.applicantLastName != null ? $(
															'#lName')
															.val(
																	applicant.applicantLastName)
															: '';
													applicant.gender != null ? $(
															'#gender')
															.val(
																	applicant.gender)
															.trigger(
																	"chosen:updated")
															: '';
													applicant.mobileNo != null ? $(
															'#mobileNo').val(
															applicant.mobileNo)
															: '';
													applicant.emailId != null ? $(
															'#eMail').val(
															applicant.emailId)
															: '';
													applicant.apmUID != null ? $(
															'#uid').val(
															applicant.apmUID)
															: '';
													applicantDataResponse.permanentAddress != null ? $(
															'#address')
															.val(
																	applicantDataResponse.permanentAddress)
															: '';
													// reasonForAppeal
													$(
															'input[name="objectionReason"][value="'
																	+ applicantDataResponse.reasonForAppeal
																	+ '"]')
															.prop('checked',
																	true);
													// groundForAppeal
													applicantDataResponse.groundForAppeal != null ? $(
															'#objectionDetails')
															.val(
																	applicantDataResponse.groundForAppeal)
															: ''
												} else {
													$('#title').trigger(
															'chosen:updated');
													$('#fName').val('');
													$('#mName').val('');
													$('#lName').val('');
													$('#gender').trigger(
															'chosen:updated');
													$('#mobileNo').val('');
													$('#eMail').val('');
													$('#uid').val('');
													$('#address').val('');
													$('#objectionDetails').val(
															'')
												}
											}
										}
										if (deptCode == 'RTI'
												&& (ojectionOn == 'FRTI' || ojectionOn == 'SRTI')) {
											if (objReferenceNo != undefined
													&& objReferenceNo != '') {
												// as per discussion with Mahesh
												// and Aseem for showing Reason
												// for Appeal Radio button Defect#117969
												$('.reasonForAppeal').show();
												$("#dispatch").show();
												// AJAX call to get applicant
												// data from
												// tb_cfc_application_mst
												let request = {
													objectionReferenceNumber : objReferenceNo,
													deptCode : deptCode
												}
												var applicantDataResponse = __doAjaxRequest(
														'ObjectionDetails.html?getDispatchDetails',
														'post', request, false,
														'json');
												if (applicantDataResponse != null) {
													applicantDataResponse.dispachDate != null ? $(
															'#dispachDate')
															.val(
																	applicantDataResponse.dispachDate)
															: '';
													applicantDataResponse.dispatchNo != null ? $(
															'#dispatchNo')
															.val(
																	applicantDataResponse.dispatchNo)
															: '';

												} else {
													$('#dispatchNo').val('');
													$('#dispachDate').val('');
												}
											}
										}
										// Defect #112673
										if (deptCode == 'ML'
												&& (ojectionOn == 'LICN')) {
											if (objReferenceNo != undefined
													&& objReferenceNo != '') {
												let request = {
													referenceNo : objReferenceNo
												}
												var applicantDataResponse = __doAjaxRequest(
														'TradeApplicationForm.html?checkRefNoValidOrNot',
														'post', request, false,
														'json');
												if (applicantDataResponse == false) {
													errorList
															.push(getLocalMessage("obj.valid.ref.no"));
													displayErrorsOnPage(errorList);
												}

											}

										}
									})
					// this method helpful when any error comes from server side
					// than reset input field based on DEPT
					resetBasedOnDept();
				});

function resetBasedOnDept() {
	let deptCode = $('#objectionDeptId option:selected').attr('code');
	if (deptCode == 'RTS') {
		$('#billComponent').hide();
		$('#noticeNoDiv').hide();
		$('.reasonForAppeal').show();
		$('#deptLocationId').removeClass('required-control');
	}
}

function resetRtiObjDetail() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$('#billComponent').show();
	$('#noticeNoDiv').show();
	$('.reasonForAppeal').hide();
	$('#deptLocationId').addClass('required-control');
	$("#rtiObjIssuerDetailsForm").validate().resetForm();
}

function saveObjectionDetailForm(element) {
	var errorList = [];
	errorList = validateObjDetailForm(errorList);
	if (errorList.length == 0) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(element,
					"Objection Against Application is submitted successfully!",
					'ObjectionDetails.html?PrintReport', 'saveObjectionDetails');
		} else {
			let deptCode = $('#objectionDeptId option:selected').attr('code');
			let ojectionOn = $('#objectionOn option:selected').attr('code');
			if (deptCode == 'RTS'
					&& (ojectionOn == 'FRTS' || ojectionOn == 'SRTS')) {
				var result = saveOrUpdateForm(
						element,
						"Objection Against Application is submitted successfully!",
						'AdminHome.html', 'saveRTSObjectionDetails');
			} else if (deptCode = 'ML' && ojectionOn == 'LICN') {
				var result = saveOrUpdateForm(
						element,
						"Objection Against Application is submitted successfully!",
						'AdminHome.html', 'saveLicenseObjectionDetails');
			} else {
				var result = saveOrUpdateForm(
						element,
						"Objection Against Application is submitted successfully!",
						'AdminHome.html', 'saveObjectionDetails');
				if (result == false) {
					// getObjectionServiceByDept();
				}
			}
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateObjDetailForm(errorList) {

	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var gender = $("#gender").val();
	var mobileNo = $("#mobileNo").val();
	var adharno = $("#uid").val();
	var objType = $("#serviceId").val();
	var locationId = $("#locId").val();
	var appealobjdetails = $("#objectionDetails").val();
	var objRefNo = $("#objectionReferenceNumber").val();
	var objectionOn = $("#objectionOn").val();
	var email = $("#EmailID").val();
	var address = $("#address").val();
	var deptCode = $('#objectionDeptId option:selected').attr('code');
	var flatNo = $("#flatNo").val();
	var billMethod = $("#billingMethod").val();
	if (billMethod == 'I') {
		if (flatNo == "" || flatNo == undefined || flatNo == null
				|| flatNo == '0') {
			errorList.push(getLocalMessage('obj.val.flat.not.empty'));
		}
	}
	if (fName == "" || fName == undefined) {
		errorList.push(getLocalMessage("obj.val.first.name.not.empty"));
	}
	if (lName == "" || lName == undefined) {
		errorList.push(getLocalMessage("obj.val.last.name.not.empty"));
	}
	if (gender == "0" || gender == undefined) {
		errorList.push(getLocalMessage("obj.val.sel.gender"));
	}
	if (mobileNo == "" || mobileNo == undefined) {
		errorList.push(getLocalMessage("obj.val.mob.not.empty"));
	} else if (mobileNo.length != 10) {
		errorList.push(getLocalMessage("obj.val.mob.not.length"));
	}
	if (adharno == "" || adharno == undefined) {
		errorList.push(getLocalMessage("obj.val.adhaar.no"));
	} else if (adharno.length != 12) {
		errorList.push(getLocalMessage("obj.val.adhaar.no.length"));
	}
	if (objType == "0" || objType == undefined || objType == "") {
		errorList.push(getLocalMessage("obj.val.service.type.not.empty"));
	}
	if (address == "" || address == undefined) {
		errorList.push(getLocalMessage("obj.val.address.not.empty"));
	}
	if (appealobjdetails == "" || appealobjdetails == undefined) {
		errorList.push(getLocalMessage("obj.val.obj.not.empty"));
	}
	if (deptCode == 'RTI' || deptCode == 'ML') {
		if (locationId == "0" || locationId == undefined || locationId == "") {
			errorList.push(getLocalMessage("obj.val.sel.location"));
		}
	}

	if (objectionOn == "0" || objectionOn == undefined || objectionOn == "") {
		errorList.push(getLocalMessage("obj.val.sel.objection"));
	}
	if (objRefNo == "" || objRefNo == undefined) {
		errorList.push(getLocalMessage("obj.val.pro.no.not.empty"));
	}
	return errorList;
}

function getObjectionServiceByDept() {
	var errorList = [];
	// for hiding dispatch no,date field
	$("#dispatch").hide();
	var requestData = {
		"objectionDeptId" : $('#objectionDeptId option:selected').attr('value')
	}
	var URL = 'ObjectionDetails.html?getObjectionServiceByDepartment';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#serviceId').html('');
	$('#serviceId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));

	if (returnData != null && returnData != undefined && returnData != ""
			&& returnData != "[]") {
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#serviceId')
					.append(
							$("<option></option>")
									.attr("value", value.lookUpId).attr("code",
											value.lookUpCode).text(
											value.descLangFirst));

			/*
			 * $('#serviceId').append( $("<option></option>").attr("value",
			 * value.lookUpCode).text( (value.descLangFirst)));
			 */
		});
		// hide the bill related field based on RTS DEPT
		let deptCode = $('#objectionDeptId option:selected').attr('code');
		if (deptCode == 'RTS') {
			$('#billComponent').hide();
			$('#noticeNoDiv').hide();
			$('.reasonForAppeal').show();
			$('#deptLocationId').removeClass('required-control');
		} else {
			$('#billComponent').show();
			$('#noticeNoDiv').show();
			$('.reasonForAppeal').hide();
			$('#deptLocationId').addClass('required-control');
		}
		// D#84026
		populateObjAppealList();
	} else {

		errorList.push(getLocalMessage('obj.val.configure.service'));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	$("#serviceId").trigger("chosen:updated");
	getLocationByDeptName();
}

function getDeptName() {
	var requestData = {
		"objectionDeptId" : $('#objectionDeptId option:selected').attr('value')
	}
	var URL = 'ObjectionDetails.html?getDeptName';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	if (returnData != null) {
		$("#objectionReferenceNumber").html(returnData);
	}
}
function getLocationByDeptName() {
	// var errorList = []; changed to non mand field
	var requestData = {
		"objectionDeptId" : $('#objectionDeptId option:selected').attr('value')
	}
	var URL = 'ObjectionDetails.html?getLocationByDepartment';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#locId').html('');
	$('#locId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));

	if (returnData != null && returnData != undefined && returnData != ""
			&& returnData != "[]") {
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#locId').append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.descLangFirst)));
		});
	}
	/**
	 * else{
	 * 
	 * errorList .push(getLocalMessage('obj.val.configure.location')); } if
	 * (errorList.length > 0) { displayErrorsOnPage(errorList); }
	 */
}

function loadPreDataOnValidation() {
	getObjectionServiceByDept();
	getDeptName();
}

function getCharges(element) {
	
	var serviceCode = $('#serviceId option:selected').attr('value');
	var theForm = '#ObjIssuerDetailsForm';
	var data = {};
	data = __serializeForm(theForm);
	var URL = 'ObjectionDetails.html?saveObjectionOrGetCharges';
	returnData = __doAjaxRequest(URL, 'POST', data, false);
	if (returnData) {
		$(formDivName).html(returnData);

	}
}
function backToMainPage() {
	var data = {};
	var URL = 'ObjectionDetails.html';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}

//128840
function getFlatListByRefNo(element) {
	var errorList = [];
	var refNo = $("#objectionReferenceNumber").val();
	var serviceId = $("#serviceId").val();
	var objectionDeptId = $("#objectionDeptId").val();
	var serviceCode = $('#serviceId option:selected').attr('code');
	var deptCode = $('#objectionDeptId option:selected').attr('code');
	if (refNo == "") {
		errorList.push(getLocalMessage("obj.val.pro.no.not.empty"));
	}
	if (serviceId == "" || serviceId == "0") {
		errorList.push(getLocalMessage("obj.sel.serive"));
	}
	if (objectionDeptId == "" || objectionDeptId == "0") {
		errorList.push(getLocalMessage("obj.selDepartment"));
	}
	if (errorList.length == 0) {
		if (deptCode == "AS" && serviceCode == "MOS") {
			var requestData = {
				"refNo" : refNo,
				"serviceId" : serviceId,
				"objectionDeptId" : objectionDeptId
			};
			var ajaxResponse = doAjaxLoading(
					'ObjectionDetails.html?getFlatList', requestData, 'html');

			if (ajaxResponse != null && ajaxResponse != ""
					&& ajaxResponse != "[]") {
				var prePopulate = JSON.parse(ajaxResponse);

				$.each(prePopulate, function(index, value) {
					$('#flatNo').append(
							$("<option></option>").attr("value", value).text(
									(value)));
				});
				$('#flatNo').trigger("chosen:updated");
				$("#showFlatNo").show();
				$("#serchBtn").hide();
				$("#save").show();
				$("#billingMethod").val('I');
			} else {
				saveObjectionDetailForm(element);
			}
		} else {
			saveObjectionDetailForm(element);
		}

	} else {
		displayErrorsOnPage(errorList);
	}
}