/**
 * 
 */

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$("#errorDivId").show();
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	return false;
}

function closeOutErrBox() {
	$('.error-div').hide();
}

$(function() {

	$("#customFields").on('click', '.addCF', function(i) {

		var row = 0;
		var errorList = [];
		errorList = validateAdditionalOwners(errorList);

		if (errorList.length == 0) {
			if (errorList.length == 0) {
				var romm = 0;
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				var clickedIndex = $(this).parent().parent().index() - 1;
				content.find("input:text").val('');
				content.find("select").val('0');

				reOrderTableIdSequence();
			} else {
				displayErrorsOnPage(errorList);
			}

		} else {
			displayErrorsOnPage(errorList);
		}

	});

	$("#customFields").on('click', '.remCF', function() {

		if ($("#customFields tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderTableIdSequence();
		} else {
			alert("You cannot delete first row");
		}

	});
});

/**
 * 
 */
function reOrderTableIdSequence() {

	$('.appendableClass')
			.each(
					function(i) {

						// $(this).find("td:eq(0)").attr("id", "srNoId_"+i);
						$(this).find("select:eq(0)").attr("id",
								"caoNewTitle_" + i);
						$(this).find("input:text:eq(0)").attr("id",
								"caoNewFName_" + i);
						$(this).find("input:text:eq(1)").attr("id",
								"caoNewMName_" + i);
						$(this).find("input:text:eq(2)").attr("id",
								"caoNewLName_" + i);
						$(this).find("select:eq(1)").attr("id",
								"caoNewGender_" + i);
						$(this).find("input:text:eq(3)").attr("id",
								"caoNewUID_" + i);
						$("#srNoId_" + i).text(i + 1);

						$(this).find("select:eq(0)").attr("name",
								"additionalOwners[" + i + "].caoNewTitle");
						$(this).find("input:text:eq(0)").attr("name",
								"additionalOwners[" + i + "].caoNewFName");
						$(this).find("input:text:eq(1)").attr("name",
								"additionalOwners[" + i + "].caoNewMName");
						$(this).find("input:text:eq(2)").attr("name",
								"additionalOwners[" + i + "].caoNewLName");
						$(this).find("select:eq(1)").attr("name",
								"additionalOwners[" + i + "].caoNewGender");
						$(this).find("input:text:eq(3)").attr("name",
								"additionalOwners[" + i + "].caoNewUID");

					});

}

$(function() {
	$('#editBtn').click(function() {
		/* $('#searchConnection').attr('disabled',false); */
		$("#submitBtn").attr('disabled', false);
		$('input[type=text]').attr('disabled', false);
		$('select').attr("disabled", false);
		$('#editBtn').attr('disabled', true);
		$('#scrutinyBtn').attr('disabled', true);
		$('.trfClass').attr('disabled', true);

		$('#applicantTitle').attr('disabled', true);
		$('#villTownCity').attr('disabled', true);
		$('#aadharNo').attr('disabled', true);
		$('#povertyLineId').attr('disabled', true);
		$('#firstName').attr('disabled', true);
		$('#middleName').attr('disabled', true);
		$('#lastName').attr('disabled', true);
		$('#mobileNo').attr('disabled', true);
		$('#emailId').attr('disabled', true);
		$('#flatNo').attr('disabled', true);
		$('#buildingName').attr('disabled', true);
		$('#roadName').attr('disabled', true);
		$('#areaName').attr('disabled', true);
		$('#pinCode').attr('disabled', true);
		$('#adharNo').attr('disabled', true);
		$('#dwzid1').attr('disabled', true);
		$('#dwzid2').attr('disabled', true);

		$('#remark').attr('disabled', false);
		// disable old owner section
		$('#conNum').attr('disabled', true);
		$('#conName').attr('disabled', true);
		$('#oldAdharNo').attr('disabled', true);
		$('#conSize').attr('disabled', true);

	});
});

function editChangeOfOwnershipByDept(element) {

	var errorList = [];
	errorList = validateOwnershipDataOnEdit(errorList);
	if (errorList.length == 0) {
		var response = saveOrUpdateForm(element,
				"Application for change of ownership has Edited succesfully!",
				'ChangeOfOwnership.html?proceed', 'saveform');

		/*
		 * $('#editBtn').attr('disabled',false);
		 * $("#submitBtn").attr('disabled',true);
		 * $('#scrutinyBtn').attr('disabled',false);
		 * $('select').attr("disabled", true);
		 * $('input[type=text]').attr('disabled',true);
		 * $('input[type=textarea]').attr('disabled',true);
		 * $('#remark').attr('disabled',true);
		 */

		// disabling form fields
		/* if (iddd == 0){ */

		if ($('#testId').val() == 1) {

			$('input[type=text]').attr('disabled', false);
			$('input[type=textarea]').attr('disabled', false);
			$('#remark').attr('disabled', false);
			$('select').attr("disabled", false);
			$('#editBtn').attr('disabled', true);
			$("#submitBtn").attr('disabled', false);

			$('#conNum').attr('disabled', true);
			$('#conName').attr('disabled', true);
			$('#oldAdharNo').attr('disabled', true);
			$('#conSize').attr('disabled', true);
			$('.trfClass').attr('disabled', true);

			document.getElementById('testId').value = '';
			// $("#testId").reset();

		} else {

			$('input[type=text]').attr('disabled', true);
			$('input[type=textarea]').attr('disabled', true);
			$('#remark').attr('disabled', true);
			$('select').attr("disabled", true);
			$('#editBtn').attr('disabled', false);

			$('#conNum').attr('disabled', true);
			$('#conName').attr('disabled', true);
			$('#oldAdharNo').attr('disabled', true);
			$('#conSize').attr('disabled', true);
			$('.trfClass').attr('disabled', true);

			$(".error-div").hide();
			$("#errorDivId").hide();

		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateOwnershipDataOnEdit(errorList) {
	var emailPattern = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;// /^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+
	// "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$/;
	var mobileNoValidPattern = /^[7-9][0-9]{9}$/; // /^[(789)](\\d){9}$/;

	var pincodeValidPattern = /^([0-9]{6})?$/;

	/*
	 * if(!isEmpty('newCitizen.empemail')){
	 * 
	 * var empEmail=document.getElementById('newCitizen.empemail').value; var
	 * reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	 * 
	 * if (reg.test(empEmail) == false) {
	 * errorList.push(getLocalMessage("citizen.login.valid.email.error")); } }
	 */

	/*
	 * function phonenumber(inputtxt) { var phoneno = /^\d{10}$/;
	 * if((inputtxt.value.match(phoneno)) { return true; } else {
	 * alert("message"); return false; } }
	 */

	var applicantTitle = $.trim($('#applicantTitle').val());
	var firstName = $.trim($('#firstName').val());
	var lastName = $.trim($('#lastName').val());
	var gender = $.trim($('#gender').val());
	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantBlockName = $.trim($('#blockName').val());
	var applicantVillTownCity = $.trim($('#villTownCity').val());
	var applicantPinCode = $.trim($('#pinCode').val());
	var applicantPovertyLineId = $.trim($('#povertyLineId').val());
	var chNewTitle = $.trim($('#chNewTitle').val());
	var chNewName = $.trim($('#ownerDTO\\.chNewName').val());
	var chNewLname = $.trim($('#ownerDTO\\.chNewLname').val());
	var newGender = $.trim($('#newGender').val());

	var applicantEmail = $.trim($('#emailId').val());

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	}
	if (firstName == "" || firstName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	}
	if (lastName == "" || lastName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	}
	/*
	 * if (gender == "" || gender == '0' || gender == undefined) {
	 * errorList.push('water.validation.ApplicantGender.'); }
	 */

	/*
	 * if(applicantMobileNo == "" || applicantMobileNo == undefined){
	 * errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 * }else { if (mobileNoValidPattern.test(applicantMobileNo)==false){ alert("
	 * invalid No.......");
	 * errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile")); }
	 * else alert("valid No"); }
	 */

	if (applicantMobileNo == "" || applicantMobileNo == undefined) {

		errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	} else {
		if (mobileNoValidPattern.test(applicantMobileNo) == false) {

			errorList
					.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));
		}
	}

	/*
	 * if (emailPattern.test(applicantEmail) == false){ alert("Email");
	 * errorList.push(getLocalMessage("Invalid Email...........")); }
	 * if(!isEmpty($('#emailId').val())){
	 * 
	 * alert("Email avil.");
	 */

	if (applicantEmail.length != 0) {

		if (emailPattern.test(applicantEmail) == false) {

			errorList.push(getLocalMessage("citizen.login.valid.email.error"));
		}
	}

	/*
	 * if (applicantBlockName == "" || applicantBlockName == undefined) {
	 * errorList.push('water.validation.ApplicantBlockName'); }
	 */
	/*
	 * if (applicantVillTownCity == "" || applicantVillTownCity == undefined) {
	 * errorList.push('water.validation.ApplicantcityVill'); }
	 */
	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	}
	if (applicantPovertyLineId == "" || applicantPovertyLineId == '0'
			|| applicantPovertyLineId == undefined) {
		errorList.push('water.validation.isabovepovertyline');
	} else {
		if (povertyLineId == 'Y') {
			var bplNo = $.trim($('#bplNo').val());
			if (bplNo == '' || bplNo == undefined) {
				errorList
						.push(getLocalMessage('water.validation.bplnocantempty'));
			}
		}
	}
	if (chNewTitle == "" || chNewTitle == '0' || chNewTitle == undefined) {
		errorList.push('water.validation.ctitle');
	}
	if (chNewName == "" || chNewName == undefined) {
		errorList.push('water.newowner.fname.validtn');
	}
	if (chNewLname == "" || chNewLname == undefined) {
		errorList.push('water.newowner.lname.validtn');
	}
	if (newGender == "" || newGender == '0' || newGender == undefined) {
		errorList.push('water.newowner.gender.validtn');
	}

	return errorList;

}

function validateChangeOfOwnershipFormData(errorList) {

	var applicantTitle = $.trim($('#applicantTitle').val());
	var firstName = $.trim($('#firstName').val());
	var lastName = $.trim($('#lastName').val());
	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantAreaName = $.trim($('#areaName').val());
	var applicantPinCode = $.trim($('#pinCode').val());
	var applicantAdharNo = $.trim($('#adharNo').val());
	var conNum = $.trim($('#conNum').val());
	var cooNotitle = $.trim($('#cooNotitle').val());
	var cooNoname = $.trim($('#changeOwnerMaster\\.cooNoname').val());
	var cooNolname = $.trim($('#changeOwnerMaster\\.cooNolname').val());

	var payMode = $("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val();

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	}
	if (firstName == "" || firstName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	}
	if (lastName == "" || lastName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	}
	if (applicantMobileNo == "" || applicantMobileNo == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	}
	if (applicantAreaName == "" || applicantAreaName == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantarea'));
	}
	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	}
	if (applicantAdharNo == "" || applicantAdharNo == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantAdharNo'));
	}
	if (conNum == "" || conNum == undefined) {
		errorList.push(getLocalMessage('water.validation.connectionno'));
	}
	if (cooNotitle == "" || cooNotitle == '0' || cooNotitle == undefined) {
		errorList.push(getLocalMessage('water.validation.ctitle'));
	}
	if (cooNoname == "" || cooNoname == undefined) {
		errorList.push(getLocalMessage('water.validation.ownerfirstname'));
	}
	if (cooNolname == "" || cooNolname == undefined) {
		errorList.push(getLocalMessage('water.validation.ownerlastname'));
	}
	if (payMode == "" || payMode == undefined) {
		errorList.push(getLocalMessage('water.validation.paymode'));
	}

	return errorList;

}
