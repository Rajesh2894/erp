var removeContactDetArray = [];

$(document).ready(function() {

	// $(".hideDate").hide();
	$('.showMand').hide();

	$(".alcYearToCBBO").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0,
		minDate : new Date(2020, 3, 1)
	});



	$("#CBBODetailsTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	$('.chosen-select-no-results').chosen();

	$("#contactDetails").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

});

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CBBOMasterForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function ResetForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CBBOMasterForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function saveCBBOMasterForm(obj) {
	var errorList = [];
	errorList = validateCBBOMasterForm(errorList);
	errorList = errorList.concat(validateFormDetails());

	if (errorList.length == 0) {
		/*
		 * var name = $("#iaName").find("option:selected").attr('code');
		 * $('#iaMastName').val(name);
		 */
		return saveOrUpdateForm(obj, "CBBO Master Details Saved Successfully!",
				'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateCBBOMasterForm(errorList) {

	/*
	 * var name = $("#iaName").find("option:selected").attr('code');
	 * $('#iaMastName').val(name);
	 */	
	var iaName = $("#IAName").val();
	var panNo = $("#panNo").val();
	var cbboName = $("#cbboName").val();
	var typeofPromAgen = $("#typeofPromAgen").val();
	var pinCode = $("#pinCode").val();
	var address = $("#address").val();
	var sdb1 = $("#sdb1").val();
	var alcYearToCBBO = $("#alcYearToCBBO").val();
	var dmcApproval = $("#dmcApproval").find("option:selected").attr('code');
	var iaAllYr = $("#iaAllYr").val();
	var cbboAppyr =$("#cbboAppyr").val();
	var fpoAllocationTarget =$("#fpoAllocationTarget").val();
	
	var iaYear = iaAllYr.split('-');
	var cbboYear = cbboAppyr.split('-');
	var iaYr = iaYear[0];
	var cbboYr = cbboYear[0];
	if (cbboYr !='' && (Number(cbboYr) < Number(iaYr))){
		errorList.push(getLocalMessage("sfac.ia.cbbo.year.validation")+ " " +iaAllYr);
	}
	
	 

	if (iaName == "" || iaName == undefined || iaName == "0") {
		errorList.push(getLocalMessage("sfac.validation.ianame"));
	}
	if (panNo == "" || panNo == undefined) {
		errorList.push(getLocalMessage("sfac.validation.pan.no"));
	}
	

	if ($.trim($("#panNo").val()) != "") {
		var panCardNo = $.trim($("#panNo").val().toUpperCase());
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = panCardNo.substring(3, 4);
		if (code_chk == "P") {
			errorList.push(getLocalMessage("sfac.valid.fourth.char.pan.no"));
		}
		if (!panPat.test(panCardNo)) {
			errorList.push(getLocalMessage("sfac.valid.pan.no"));
		}
	}
	if (cbboName == "" || cbboName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.cbboName"));
	}
	
	if (sdb1 == "0" || sdb1 == "" || sdb1 == undefined) {
		errorList.push(getLocalMessage("sfac.validation.SDB1"));
	}

	if (alcYearToCBBO == "" || alcYearToCBBO == undefined) {
		errorList.push(getLocalMessage("sfac.validation.Empanelmentofcbbo"));
	}
	if (pinCode == "" || pinCode == undefined) {
		errorList.push(getLocalMessage("sfac.validation.pinCode"));
	}
	var pattern = /^(?!0{6})[A-Z0-9][0-9]{5}$/;
	if (pinCode != "") {
		if (!pattern.test(pinCode)) {
			errorList.push(getLocalMessage("sfac.validation.pinCode.valid"));
		}
	}
	
	if (typeofPromAgen == "" || typeofPromAgen == undefined
			|| typeofPromAgen == "0") {
		errorList.push(getLocalMessage("sfac.fpo.validation.typeofPromAgen"));
	}


	if (fpoAllocationTarget == "" || fpoAllocationTarget == undefined
			|| fpoAllocationTarget == "0") {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoAllocationTarget"));
	}
	
	 

	/*
	 * if (address == "" || address == undefined) {
	 * errorList.push(getLocalMessage("sfac.validation.address")); }
	 */




	if ($.trim($("#panNo").val()) != "") {
		var request = {	
				"panNo" : panNo
			};
		var ajaxResponse = __doAjaxRequest('CBBOMasterForm.html?checkPanNoExist', 'post', request,
				false, 'json');
		if (ajaxResponse == true)
			errorList.push(getLocalMessage("sfac.pan.no.already.exist"));
	}
	

	return errorList;

}

function searchForm(obj, formUrl, actionParam) {
	var errorList = [];
	var parentOrg = $("#parentOrg").val();
	if (parentOrg == 'CBBO'){
		var cbboId = $("#cbId").val();
		var iaId = $("#iaId").val();	
	}
	else
		var cbboId = $("#cbboName").val();
	if (parentOrg == 'IA'){
		var iaId = $("#iId").val();
		var cbboId = $("#cbboName").val();
	}
	else
		var iaId = $("#iaId").val();	
	var alcYearToCBBO = $("#alcYearToCBBO").val();
	
	
	var divName = '.content-page';
	if ((cbboId == "" || cbboId == undefined || cbboId == '0') && (iaId  == "" || iaId == undefined || iaId  == '0')
			&& (alcYearToCBBO == "" || alcYearToCBBO == undefined)) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
			"cbboId" : cbboId,
			"alcYearToCBBO" : alcYearToCBBO,
			"iaId" : iaId
		};

		var ajaxResponse = doAjaxLoading(
				'CBBOMasterForm.html?getCbboLDeatails', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();

		/*var prePopulate = JSON.parse(ajaxResponse);
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
														+ dto.ianame + '</div>',
												
												 * '<div align="center">' +
												 * dto.iaAlcYear + '</div>', '<div
												 * align="center">' +
												 * dto.alYrCbbo + '</div>',
												 
												'<div align="center">'
														+ dto.cbboName
														+ '</div>',
												'<div align="center">'
														+ dto.cbboUniqueId
														+ '</div>',
												'<div class="text-center">'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="getActionForDefination(\''
														+ dto.cbboId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ '<button type="button" class="btn btn-warning btn-sm btn-sm hide" id="editButton" onclick="getActionForDefination(\''
														+ dto.cbboId
														+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>',
														
												'</div>' ]);

							});
			table.rows.add(result);
			table.draw();
			if (parentOrg == "IA") {
				$("#editButton").removeClass('hide');
			}
		}*/
	} else {
		displayErrorsOnPage(errorList);
	}
}

$('#sdb1').on('change', function() {

	var sdb1 = $("#sdb1").val();
	var requestData = {
		"sdb1" : sdb1
	};
	var URL = 'CBBOMasterForm.html?fetchAllAreaAndZoneByStateCode';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	if (returnData.length != 0) {
		$('#stateCategory').val(returnData.areaType);
		$('#region').val(returnData.zone);
	}
});

$('#sdb2')
		.on(
				'change',
				function() {
					var sdb2 = $("#sdb2").find("option:selected").attr('code');
					var request = {
						"sdb2" : sdb2
					};
					var AjaxResponse = __doAjaxRequest(
							'CBBOMasterForm.html?checkIsAispirationalDist',
							'post', request, false, 'json');
					if (AjaxResponse == true) {

						var aspirationalDistrictCheckbox = $('#isAspirationalDistrict');
						aspirationalDistrictCheckbox.prop('checked', true);
						if (aspirationalDistrictCheckbox.prop('checked') == true) {
							aspirationalDistrictCheckbox.val('Y');
						} else {
							aspirationalDistrictCheckbox.val('N');
						}
					}

					var responseData = __doAjaxRequest(
							'CBBOMasterForm.html?checkIsTribalDist', 'post',
							request, false, 'json');
					if (responseData == true) {
						var tribalDistrictCheckbox = $('#isTribalDistrict');
						tribalDistrictCheckbox.prop('checked', true);
						if (tribalDistrictCheckbox.prop('checked') == true) {
							tribalDistrictCheckbox.val('Y');
						} else {
							tribalDistrictCheckbox.val('N');
						}
					}

					var responseData = __doAjaxRequest(
							'CBBOMasterForm.html?getOdopByDist', 'post',
							request, false, 'json');
					if (responseData.length != 0) {
						$('#odop').val(responseData);
					}

				});

function getActionForDefination(cbboId, formMode) {

	var divName = formDivName;
	var url = "CBBOMasterForm.html?editAndViewForm";
	data = {
		"cbboId" : cbboId,
		"formMode" : formMode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(".alcYearToCBBO").removeClass("datepicker");
	$(divName).html(response);
	$(".alcYearToCBBO").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0,
		minDate : new Date(2020, 3, 1)
	});

	
	prepareTags();
}

function validateFormDetails() {

	var errorList = [];
	var contact = [];
	var email =[];
	var rowCount = $('#contactDetails tr').length;
	if ($.fn.DataTable.isDataTable('#contactDetails')) {
		$('#contactDetails').DataTable().destroy();
	}

	if (errorList == 0)
		$("#contactDetails tbody tr")
				.each(
						function(i) {
							var titleId = $("#titleId" + i).val();
							var dsgId = $("#dsgId" + i).val();
							var fName = $("#fName" + i).val();
							var mName = $("#mName" + i).val();
							var lName = $("#lName" + i).val();
							var contactNo = $("#contactNo" + i).val();
							var emailId = $("#emailId" + i).val();
							var rowCount = i + 1;

							if (dsgId == "" || dsgId == undefined
									|| dsgId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.dsgId")
												+ " " + rowCount);
							}
							if (titleId == "" || titleId == undefined
									|| titleId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.titleId")
												+ " " + rowCount);
							}
							
							if (fName == "" || fName == undefined
									|| fName == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.fName")
												+ " " + rowCount);
							}
							/*
							 * if (mName == "" || mName == undefined || mName ==
							 * "0") { errorList
							 * .push(getLocalMessage("sfac.validation.mName") + " " +
							 * rowCount); }
							 * 
							 * if (lName == "" || lName == undefined || lName ==
							 * "0") { errorList
							 * .push(getLocalMessage("sfac.validation.lName") + " " +
							 * rowCount); }
							 */

							if (contactNo == "" || contactNo == undefined || contactNo == "0") {
								errorList.push(getLocalMessage("sfac.validation.contactNo")	+ " " + rowCount);
							}else {
								if (contactNo != "" && (contactNo.length < 10 || contactNo == '0000000000')) {
									errorList.push(getLocalMessage("sfac.valid.contactNo")	+ " " + rowCount);
								}
							 
								if (contact.includes(contactNo)) {
									errorList.push(getLocalMessage("sfac.dup.contact")
											+ " "+ rowCount);
								}
								if (errorList.length == 0) {
									contact.push(contactNo);
								}
							}

							/*
							 * if (emailId == "" || emailId == undefined ||
							 * emailId == "0") { errorList
							 * .push(getLocalMessage("sfac.validation.emailId") + " " +
							 * rowCount); } else {
							 */
							if (emailId != "") {
								var emailRegex = new RegExp(
										/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
								var valid = emailRegex.test(emailId);
								if (!valid) {
									errorList
											.push(getLocalMessage("sfac.valid.iaEmailId")
													+ " " + rowCount);
								}
								if (email.includes(emailId)) {
									errorList.push(getLocalMessage("sfac.dup.emailId")
													+" "+ rowCount);
								}
								if (errorList.length == 0) {
									email.push(emailId);
								}
							}
							// }
						});
	return errorList;
}

function addRow(obj) {
	var errorList = [];
	errorList = validateFormDetails(errorList);
	if (errorList.length == 0) {
		var content = $('#contactDetails tr').last().clone();
		$('#contactDetails tr').last().after(content);
		content.find("select").val('');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		reorderContactDetailsTable();
		// content.find("select:eq(0),
		// select:eq(1)").chosen().trigger("chosen:updated");
	} else {
		displayErrorsOnPage(errorList);
	}
}
$('#contactDetails').on(
		"click",
		'.deleteContactDetails',
		function(e) {
			var errorList = [];
			var count = 0;
			$('.appendableContactDetails').each(function(i) {
				count += 1;
			});
			var rowCount = $('#contactDetails tr').length;
			if (rowCount <= 2) {
				return false;
			}
			$(this).parent().parent().remove();
			var contId = $(this).parent().parent().find(
					'input[type=hidden]:first').attr('value');
			if (contId != '') {
				removeContactDetArray.push(contId);
			}
			$('#removeContactDetIds').val(removeContactDetArray);
			reorderContactDetailsTable();
			// content.find("select:eq(0),
			// select:eq(1)").chosen().trigger("chosen:updated");
		});

function reorderContactDetailsTable() {
	$("#contactDetails tbody tr").each(
			function(i) {

				// Id
				$(this).find("input:hidden:eq(0)").attr("id", "cbboDId" + i);
				$(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
				$(this).find('select:eq(0)').attr('id', 'dsgId' + i);
				$(this).find('select:eq(1)').attr('id', 'titleId' + i);

				$(this).find('input:text:eq(1)').attr('id', 'fName' + i);
				$(this).find('input:text:eq(2)').attr('id', 'mName' + i);
				$(this).find('input:text:eq(3)').attr('id', 'lName' + i);
				$(this).find('input:text:eq(4)').attr('id', 'contactNo' + i);
				$(this).find('input:text:eq(5)').attr('id', 'emailId' + i);

				$(this).find("input:hidden:eq(0)").attr("name",
						"masterDto.cbboDetDto[" + i + "].cbboDId");

				$(this).find("select:eq(0)").attr("name",
						"masterDto.cbboDetDto[" + i + "].dsgId");
				$(this).find("select:eq(1)").attr("name",
						"masterDto.cbboDetDto[" + i + "].titleId");
				$(this).find("input:text:eq(1)").attr("name",
						"masterDto.cbboDetDto[" + i + "].fName");
				$(this).find("input:text:eq(2)").attr("name",
						"masterDto.cbboDetDto[" + i + "].mName");
				$(this).find("input:text:eq(3)").attr("name",
						"masterDto.cbboDetDto[" + i + "].lName");
				$(this).find("input:text:eq(4)").attr("name",
						"masterDto.cbboDetDto[" + i + "].contactNo");
				$(this).find("input:text:eq(5)").attr("name",
						"masterDto.cbboDetDto[" + i + "].emailId");
				$("#sequence" + i).val(i + 1);
			});
}

function getAppoitmentYear() {

	var alcYearToCBBO = $('#alcYearToCBBO').val();
	if (alcYearToCBBO != '') {
		var requestData = {
			'alcYearToCBBO' : alcYearToCBBO,
		}
		var ajaxResponse = __doAjaxRequest(
				"CBBOMasterForm.html?getAppoitmentYear", 'POST', requestData,
				false, 'json');
		if (ajaxResponse != null) {
			$('#cbboAppoitmentYr').val(ajaxResponse[0]).trigger('chosen:updated');
			$('#cbboAppyr').val(ajaxResponse[1])
		}
	}
}
function checkStatusAndshowDate() {

	var dmcApproval = $("#dmcApproval").find("option:selected").attr('code');
	if (dmcApproval == 'PND') {
		$('.showMand').show();
	} else
		$('.showMand').hide();
}

function getDetailsByPanNo() {

	var errorList = [];
	var panNo = $('#panNo').val();
	var enteredPanNo = $('#panNo').val();
	if ($.trim($("#panNo").val()) != "") {
		var panCardNo = $.trim($("#panNo").val().toUpperCase());
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = panCardNo.substring(3, 4);
		if (!panPat.test(panCardNo)) {
			errorList.push(getLocalMessage("sfac.valid.pan.no"));
		}
	}
	if ($.trim($("#panNo").val()) != "") {
		var request = {	
				"panNo" : panNo
			};
		var ajaxResponse = __doAjaxRequest('CBBOMasterForm.html?checkPanNoExist', 'post', request,
				false, 'json');
		if (ajaxResponse == true){
			errorList.push(getLocalMessage("sfac.pan.no.already.exist"));
			}else{
				
			}
			
	}
	
	if ($.trim($("#panNo").val()) != "") {
	if (errorList.length == 0) {
		var requestData = {
			"panNo" : panNo
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(
				'CBBOMasterForm.html?getDetailsByPanNo', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	 }
	}
}

$('#approved').on('change', function(){
    var ifChecked = $(this).prop('checked');
    if(ifChecked) 
        $(this).val('Y')
     else 
        $(this).val('N')
});


function saveApprovalData(element) {
	var errorList = [];
	if ($("#remark").val() == "") {
		errorList.push(getLocalMessage("sfac.reamrk.validate"));
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				"CBBO Master Approval Details Saved Successfully!", 'AdminHome.html','saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}
