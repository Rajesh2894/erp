var url = "DataLegacyForm.html";
$(document).ready(function() {

	$("#lastpaymentdateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		
	});

	$("#lastpaymentdateId").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

$("#applicantId").datepicker({
		
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});

	$("#applicantId").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#lastDateLifeCertiId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		
	});
	$("#lastDateLifeCertiId").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#percenrofDis").prop("disabled", true);
	$("#bplinspectyr").prop("disabled", true);
	$("#bplfamily").prop("disabled", true);
	
	$("#resetform").on("click", function(){ 
		  window.location.reload("#dataLegacyFormId")
		});
});

$(function() {
	$(".datepicker2").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-150:-0"
	});

	$(".datepicker2").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
});

$(function() { 
	/* To add new Row into table */
	$("#addUnitRow").on('click', function() {

		
		var errorList = [];
		errorList = validateownerFamilyDetailsTable(errorList);
		if (errorList.length == 0) {

			// fileCountUpload();
			var content = $("#ownerFamilyDetail").find('tr:eq(1)').clone();
			$("#ownerFamilyDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');
			$(".datepicker2").removeClass("hasDatepicker");
			// content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			$(document).on("focus", ".datepicker2", function(){
		        $(this).datepicker({
		        	dateFormat : 'dd/mm/yy',
		    		changeMonth : true,
		    		changeYear : true
		        });
		});
			reOrderownerFamilyDetailsSequence('.appendableFamilyClass'); // reorder
																			// id
																			// and
																			// Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

function saveDataLegacy(element) { 
	var errorList = [];
	errorList = dataLegacyFormValidation(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, '', 'DataLegacyForm.html', 'saveform');
		$(divName).html(response);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function dataLegacyFormValidation(errorList) {
	var schemenameId = $("#schemenameId").val();
	var nameofApplicant = $("#nameofApplicant").val();
	var applicationDobId = $("#applicantId").val();
	var ageason = $("#ageason").val();
	var genderId = $("#applicationformdto\\.genderId").val();
	var applicantAdress = $("#applicantAdress").val();
	var aadharCard = $("#aadharCard").val();
	var pinCode = $("#pinCode").val();
	var mobNum = $("#mobNum").val();
	var educationId = $("#applicationformdto\\.educationId").val();
	var maritalStatusId = $("#applicationformdto\\.maritalStatusId").val();
	var categoryId = $("#applicationformdto\\.categoryId").val();
	var annualIncome = $("#annualIncome").val();
	var typeofDisId = $("#applicationformdto\\.typeofDisId").val();
	var percenrofDis = $("#percenrofDis").val();
	var bplid = $("#applicationformdto\\.bplid").val();
	var bplinspectyr = $("#bplinspectyr").val();
	var bplfamily = $("#bplfamily").val();
	var banknameId = $("#banknameId").val();
	var accountNumber = $("#accountNumber").val();
	var lastpaymentdateId = $("#lastpaymentdateId").val();
	var referenceNo = $("#referenceNo").val();
	var lastDateLifeCertiId = $("#lastDateLifeCertiId").val();

	if (schemenameId == "0" || schemenameId == undefined || schemenameId == '') {
		errorList.push(getLocalMessage('social.sec.schemename.req'));
	}

	if (nameofApplicant == "0" || nameofApplicant == undefined
			|| nameofApplicant == '') {
		errorList.push(getLocalMessage('social.sec.nameofapp.req'));
	}

	if (applicationDobId == "0" || applicationDobId == undefined
			|| applicationDobId == '') {
		errorList.push(getLocalMessage('social.sec.appdob.req'));
	}

	if (ageason == "0" || ageason == undefined || ageason == '') {
		errorList.push(getLocalMessage('social.sec.ageason.req'));
	}

	if (genderId == "0" || genderId == undefined || genderId == '') {
		errorList.push(getLocalMessage('social.sec.gen.req'));
	}

	if (applicantAdress == "0" || applicantAdress == undefined
			|| applicantAdress == '') {
		errorList.push(getLocalMessage('social.sec.appadd.req'));
	}
	
	

	if (pinCode == "0" || pinCode == undefined || pinCode == '') {
		errorList.push(getLocalMessage('social.sec.pincode.req'));
	}
	if(pinCode.length < 6 || pinCode.length > 6)
	{
	errorList.push(getLocalMessage('social.sec.valid.pincode'));
	}
	
	if (aadharCard == "0" || aadharCard == undefined
			|| aadharCard == '') {
		errorList.push(getLocalMessage('social.aadharNo.required'));
	}

	if (mobNum == "0" || mobNum == undefined || mobNum == '') {
		errorList.push(getLocalMessage('social.sec.mobnum.req'));
	}

	

	if (maritalStatusId == "0" || maritalStatusId == undefined
			|| maritalStatusId == '') {
		errorList.push(getLocalMessage('social.sec.maritalstat.req'));
	}

	if (categoryId == "0" || categoryId == undefined || categoryId == '') {
		errorList.push(getLocalMessage('social.sec.cate.req'));
	}

	

	if (typeofDisId == "0" || typeofDisId == undefined || typeofDisId == '') {
		errorList.push(getLocalMessage('social.sec.typeofdis.req'));
	}

	let typeofdisvar = $('#applicationformdto\\.typeofDisId option:selected')
			.attr('code');
	if (typeofdisvar != "NA") {
		if (percenrofDis == "0" || percenrofDis == undefined
				|| percenrofDis == '') {
			errorList.push(getLocalMessage('social.sec.percentofdis.req'));
		}
	}

	if (bplid == "0" || bplid == undefined || bplid == '') {
		errorList.push(getLocalMessage('social.sec.bpl.req'));
	}
	let bplvar = $('#applicationformdto\\.bplid option:selected').attr('code');
	$("#isBplApplicableId").val("Y");
	if (bplvar != "NNO") {
		$("#isBplApplicableId").val("N");
		if (bplinspectyr == "0" || bplinspectyr == undefined
				|| bplinspectyr == '') {
			errorList.push(getLocalMessage('social.sec.bplyr.req'));
		}

		if (bplfamily == "0" || bplfamily == undefined || bplfamily == '') {
			errorList.push(getLocalMessage('social.sec.bplid.req'));
		}
	}

	if (banknameId == "0" || banknameId == undefined || banknameId == '') {
		errorList.push(getLocalMessage('social.sec.bankname.req'));
	}

	if (accountNumber == "0" || accountNumber == undefined
			|| accountNumber == '') {
		errorList.push(getLocalMessage('social.sec.accnum.req'));
	}

	if (lastpaymentdateId == "0" || lastpaymentdateId == undefined
			|| lastpaymentdateId == '') {
		errorList.push(getLocalMessage('data.legacy.lastpaymentdate.req'));
	}

	if (referenceNo == "0" || referenceNo == undefined || referenceNo == '') {
		errorList.push(getLocalMessage('data.legacy.referencenum.req'));
	}

	if (lastDateLifeCertiId == "0" || lastDateLifeCertiId == undefined
			|| lastDateLifeCertiId == '') {
		errorList.push(getLocalMessage('social.sec.lastdtlifecerti.req'));
	}
	return errorList;
}

//function to validate contact number
function validateContactNum() {
	var errorList = [];
	var contactNumber = $("#contactNumber").val();
	errorList = validateMobNo(contactNumber);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}

//function to validate mobile number
function validateMobNum() {
	var errorList = [];
	var mobNum = $("#mobNum").val();
	errorList = validateMobNo(mobNum);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}

function geteducationdetails() {
	let educationId = $("#applicationformdto\\.educationId").val();
	if (educationId == undefined || educationId == '' || educationId == '0') {
		$("#classs").prop("disabled", true);
		//$("#classs").val(156);
	} else {

		$("#classs").prop("disabled", false);
	}
}

//function to hide and show type of disability textbox
function disabilitydetails() {
	let typeofdisvar = $('#applicationformdto\\.typeofDisId option:selected')
			.attr('code');
	if (typeofdisvar == "NA") {
		$("#percenrofDis").prop("disabled", true);
	} else {
		$("#percenrofDis").prop("disabled", false);
		//$("#classs").val(156);
	}
}

//function to hide and show type of disability bpl
function bpldetails() {
	let bplvar = $('#applicationformdto\\.bplid option:selected').attr('code');

	if (bplvar == "YES") {
		$("#bplinspectyr").prop("disabled", false);
		$("#bplfamily").prop("disabled", false);
	} else {
		$("#bplinspectyr").prop("disabled", true);
		$("#bplfamily").prop("disabled", true);
	}
}

function dobdetails1() {
	$("#ownerFamilyDetail tbody tr td div").each(function(i) {
		var dateString = $("#dob"+i).val(); 
		var dateParts = dateString.split("/");
		var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
		if (dateString != undefined || dateString != ''
				|| dateString != '0') {

			var dates = new Date(dateObject.toString());
			var age = getAge(dates);
			$("#age"+i).val(age);
		}
	});
}
// function to calculate age on entry of dob
function getAge(DOB) {

	var today = new Date();
	var birthDate = new Date(DOB);
	var age = today.getFullYear() - birthDate.getFullYear();
	var m = today.getMonth() - birthDate.getMonth();
	if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
		age = age - 1;
	}

	return age;
}

function resetDataLegacy()
{
	
	$('input[type=text]').val('');  
	$(".alert-danger").hide();
	$("#dataLegacyFormId").validate().resetForm();
}

function DeleteRow(obj)

{

	if ($("#ownerFamilyDetail tr").length != 2) {
		$(obj).parent().parent().remove();
		/* $(deleteRow).closest('tr').remove(); */
		reOrderownerFamilyDetailsSequence('.appendableFamilyClass');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
}

function validateownerFamilyDetailsTable(errorList) {
	
	var errorList = [];
	var rowCount = $('#ownerFamilyDetail tr').length - 1;
	var mobNo = [];
	var famMemUidNo = [];
	var email = [];

	if ($.fn.DataTable.isDataTable('#ownerFamilyDetail')) {
		$('#ownerFamilyDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerFamilyDetail tbody tr")
				.each(
						function(i) {

							if (rowCount < 2) {

								var famMemName = $("#famMemName" + i).val();
								var relation = $("#relation" + i).val();
								var gender = $("#gender" + i).val();
								var dob = $("#dob" + i).val();
								var age = $("#age" + i).val();
								var education = $("#education" + i).val();
								var occupation = $("#occupation" + i).val();
								var contactNo = $("#contactNo" + i).val();

								var constant = 1;
							} else {
								var famMemName = $("#famMemName" + i).val();
								var relation = $("#relation" + i).val();
								var gender = $("#gender" + i).val();
								var dob = $("#dob" + i).val();
								var age = $("#age" + i).val();
								var education = $("#education" + i).val();
								var occupation = $("#occupation" + i).val();
								var contactNo = $("#contactNo" + i).val();

								var constant = i + 1;
							}
							if (famMemName == '0' || famMemName == undefined
									|| famMemName == "") {
								errorList
										.push(getLocalMessage("social.validation.famMemName")
												+ " " + constant);
							}
							if (relation == '0' || relation == undefined
									|| relation == "") {
								errorList
										.push(getLocalMessage("social.validation.relation")
												+ " " + constant);
							}
							if (gender == '0' || gender == undefined
									|| gender == "") {
								errorList
										.push(getLocalMessage("social.validation.gender")
												+ " " + constant);
							}
							if (dob == '0' || dob == undefined
									|| dob == "") {
								errorList
										.push(getLocalMessage("social.validation.dob")
												+ " " + constant);
							}
							if (age == '0' || age == undefined
									|| age == "") {
								errorList
										.push(getLocalMessage("social.validation.age")
												+ " " + constant);
							}
							if (education == '0' || education == undefined
									|| education == "") {
								errorList
										.push(getLocalMessage("social.validation.education")
												+ " " + constant);
							}
							if (occupation == '0' || occupation == undefined
									|| occupation == "") {
								errorList
										.push(getLocalMessage("social.validation.occupation")
												+ " " + constant);
							}
							if (contactNo == '0' || contactNo == undefined
									|| contactNo == "") {
								errorList
										.push(getLocalMessage("social.validation.contactNo")
												+ " " + constant);
							}

						});

	return errorList;
}

function reOrderownerFamilyDetailsSequence(classNameFirst) {

	$(classNameFirst).each(
			function(i) {
				
				var famMemName = $("#famMemName" + i).val();
				var relation = $("#relation" + i).val();
				var gender = $("#gender" + i).val();
				var dob = $("#dob" + i).val();
				var age = $("#age" + i).val();
				var education = $("#education" + i).val();
				var occupation = $("#occupation" + i).val();
				var contactNo = $("#contactNo" + i).val();

				// id binding
				$(this).find("input:text:eq(0)").attr("id", "famMemName" + i);
				$(this).find("input:text:eq(1)").attr("id", "relation" + i);
				$(this).find("select:eq(0)").attr("id", "gender" + i);
				$(this).find("input:text:eq(2)").attr("id", "dob" + i);

				$(this).find("input:text:eq(3)").attr("id", "age" + i);
				$(this).find("input:text:eq(4)").attr("id", "education" + i);
				$(this).find("input:text:eq(5)").attr("id", "occupation" + i);
				$(this).find("input:text:eq(6)").attr("id", "contactNo" + i);

				// path binding
				$(this).find("input:text:eq(0)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].famMemName");
				$(this).find("input:text:eq(1)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].relation");
				$(this).find("select:eq(0)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].gender");
				$(this).find("input:text:eq(2)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].dob");
				$(this).find("input:text:eq(3)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].age");
				$(this).find("input:text:eq(4)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].education");
				$(this).find("input:text:eq(5)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].occupation");
				$(this).find("input:text:eq(6)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].contactNo");

			});
}

$('body').on('focus', ".hasAadharNo", function() {
	$('.hasAadharNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

function checkDate(element) {
	var divName = '.content-page';
	var URL = 'DataLegacyForm.html?checkDate';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');
	/*$.each(returnData, function(key, value) {
		  $('#subschemeId').append($("<option></option>").attr("value",key).text(value));
	});*/
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	//prepareTags();
	$("#errorDiv").hide();
}
