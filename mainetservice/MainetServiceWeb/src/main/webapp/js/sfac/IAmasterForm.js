var removeContactDetArray = [];

$(document).ready(function() {
	$("#IADetailsTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$("#contactDetails").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});


	$('input[name="iaMasterDto.level"]').click(function() {
		if ($(this).attr("value") == "S") {
			$(".no").show();
		}
		if ($(this).attr("value") == "N") {
			$(".no").hide();
		}
	});

	if($('#levelNational').is(':checked') == true){
		$(".no").hide();
		$("#state").val('');
	}
	
	$('.alpaSpecial').keypress(function(e) {
		var regex = new RegExp("^[a-zA-Z0-9 ()/|,._';:-]+$");
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		if (regex.test(str)) {
			return true;
		}
		e.preventDefault();
		return false;
	});
});

function searchForm(obj, formUrl, actionParam) {
	var errorList = [];
	var IAName = $("#IAName").val();
	var allocationYear = $("#allocationYear").val();
	var parentOrg = $("#parentOrg").val();
	var divName = '.content-page';
	if ((IAName == "" || IAName == undefined || IAName == "0")
			&& (allocationYear == "0" || allocationYear == undefined)) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {

		var requestData = {
			"IAName" : IAName,
			"allocationYear" : allocationYear
		};
		var table = $('#IADetailsTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading('IAMasterForm.html?getIADetails', requestData, 'html');
		var prePopulate = JSON.parse(ajaxResponse);
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
												'<div align="center">'
														+ dto.allcYear
														+ '</div>',
												'<div align="center">'
														+ dto.summaryStatus
														+ '</div>',
												'<div class="text-center">'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="getActionForDefination(\''
														+ dto.iaid
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ '<button type="button" class="btn btn-warning btn-sm btn-sm " id="editButton" onclick="getActionForDefination(\''
														+ dto.iaid
														+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>',
												'</div>'  ]);

							});
			table.rows.add(result);
			table.draw();
			if (parentOrg == "Y"){
				 $("#editButton").removeClass('hide');
			}
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function formForCreate() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('IAMasterForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function ResetForm() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('IAMasterForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function saveIAMasterForm(obj) {
	
	var errorList = [];

	var iaName = $("#IAName").val();
	var allocationYear = $("#alcYear").val();
	/*var fName = $("#fName").val();
	var mName = $("#mName").val();
	var lName = $("#lName").val();
	var iaContactNo = $("#iaContactNo").val();
	var iaEmailId = $("#iaEmailId").val();*/
	var iaPinCode = $("#iaPinCode").val();
	var iaAddress = $("#iaAddress").val();
	var iaShortName =$("#iaShortName").val();
	var dupIaName = $('#dupIaName').val();
	var dupIaShortNm = $('#dupIaShortNm').val();
	
	var level = $('input[type=radio]:checked').val();
	var viewMode = $("#viewMode").val();
	

	if (iaName == "" || iaName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.ianame"));
	}
	if (viewMode == 'A'){
	if (dupIaName === 'true') {
		errorList.push(getLocalMessage("sfac.fpo.validation.IaName.exist"));
	}
	if (dupIaShortNm === 'true'){
		errorList.push(getLocalMessage("sfac.fpo.validation.iaShortName.exist"));
	}
	}
	if (allocationYear == "0" || allocationYear == undefined) {
		errorList.push(getLocalMessage("sfac.valid.iaOnboarding.year"));
	}
	var pattern = /^(?!0{6})[A-Z0-9][0-9]{5}$/;
	if (iaPinCode != "") {
		if (!pattern.test(iaPinCode)) {
			errorList.push(getLocalMessage("sfac.validation.pinCode.valid"));
		}
	}

	if (level == "S") {
		var state = $("#state").val();
		if (state == "" || state == undefined || state == "0") {
			errorList.push(getLocalMessage("sfac.validation.state"));
		}
	}
	if (iaShortName == "" || iaShortName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.iaShortName"));
	}
	
	

	/*if (fName == "" || fName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.fName"));
	}

	if (mName == "" || mName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.mName"));
	}

	if (lName == "" || lName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.lName"));
	}
	if (iaContactNo == "" || iaContactNo == undefined) {
		errorList.push(getLocalMessage("sfac.validation.iaContactNo"));
	}

	if (iaEmailId == "" || iaEmailId == undefined) {
		errorList.push(getLocalMessage("sfac.validation.iaEmailId"));
	} else {
		if (iaEmailId != "") {
			var emailRegex = new RegExp(
					/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
			var valid = emailRegex.test(iaEmailId);
			if (!valid) {
				errorList.push(getLocalMessage("sfac.valid.iaEmailId"));
			}
		}
	}
	
	if (iaPinCode == "" || iaPinCode == undefined) {
		errorList.push(getLocalMessage("sfac.validation.pinCode"));
	}

	if (iaAddress == "" || iaAddress == undefined) {
		errorList.push(getLocalMessage("sfac.validation.address"));
	}*/
	

    errorList = errorList.concat(validateFormDetails());
    
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "IA Master Details Saved Successfully!",
				'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
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
		//content.find("select:eq(0), select:eq(1)").chosen().trigger("chosen:updated");
	} else {
		displayErrorsOnPage(errorList);
	}
}

$('#contactDetails').on("click",'.deleteContactDetails',function(e) {	
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
});

function deleteRow(obj) {
	var errorList = [];
	var countRows = -1;
/*	var cropDetailsRowLength = $('#contactDetails tr').length;
	if ($("#contactDetails tr").length != 2) {
		$(obj).parent().parent().remove();
		
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}*/
	
/*	$('#contactDetails').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	var row = countRows;

	if (row == 0) {

		var deletedYearId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		
		if (deletedYearId != '') {
			removeContactDetArray.push(deletedYearId);
		}
	}
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
		var deletedYearId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (deletedYearId != '') {
			removeContactDetArray.push(deletedYearId);
		}
	}
	$('#removeContactDetIds').val(removeContactDetArray);
	reorderContactDetailsTable();
	*/
	
	
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
/*	var contactId = $(this).parent().parent().find(
	'input[type=hidden]:first').attr('value');*/
	var contactId = $(this).parent().parent().find('input.contId').attr('value');
	if (contactId != '') {
		removeContactDetArray.push(contactId);
	}
	$('#removeContactDetIds').val(removeContactDetArray);
	reorderContactDetailsTable();

}


function reorderContactDetailsTable() {
	$("#contactDetails tbody tr").each(function(i) {
		// Id
		$(this).find("input:hidden:eq(0)").attr("id","iadId" + i);
    	$(this).find("input:text:eq(0)").attr("id" ,"sequence" + (i));
    
		$(this).find('select:eq(1)').attr('id','titleId' + i);
		$(this).find('select:eq(2)').attr('id','role' + i);
		
		$(this).find('input:text:eq(1)').attr('id','dsgId' + i);
		$(this).find('input:text:eq(2)').attr('id','fName' + i);
		$(this).find('input:text:eq(3)').attr('id','mName' + i);
		$(this).find('input:text:eq(4)').attr('id','lName' + i);
		$(this).find('input:text:eq(5)').attr('id','contactNo' + i);
		$(this).find('input:text:eq(6)').attr('id','emailId' + i);
		
		
		$(this).find("input:hidden:eq(0)").attr("name","iaMasterDto.iaDetailDto[" + i + "].iadId");
		
		
		$(this).find("select:eq(1)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].titleId");
		$(this).find("select:eq(2)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].role");
		$(this).find("input:text:eq(1)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].dsgId");
		$(this).find("input:text:eq(2)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].fName");
		$(this).find("input:text:eq(3)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].mName");
		$(this).find("input:text:eq(4)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].lName");
		$(this).find("input:text:eq(5)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].contactNo");
		$(this).find("input:text:eq(6)").attr("name", "iaMasterDto.iaDetailDto[" + i + "].emailId");
		$("#sequence" + i).val(i + 1);
	});
}

function getActionForDefination(iaid, formMode) {

	var divName = formDivName;
	var url = "IAMasterForm.html?editAndViewForm";
	data = {
		"IAId" : iaid,
		"formMode" : formMode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();
}


function validateFormDetails(errorList) {
	var errorList =[];
	var contact = [];
	var  email =[];
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
								var contactNo = $("#contactNo"+ i ).val();
								var emailId = $("#emailId" + i).val();
								var rowCount = i + 1;
								
							if (dsgId == "" || dsgId == undefined) {
								errorList
										.push(getLocalMessage("sfac.validation.dsgId")
												+ " " + rowCount);
							}
			
							if (titleId == "" || titleId == undefined || titleId == "0") {
								errorList.push(getLocalMessage("sfac.validation.titleId")
												+ " " + rowCount);
							}
							
							if (fName == "" || fName == undefined || fName == "0") {
								errorList.push(getLocalMessage("sfac.validation.fName")
												+ " " + rowCount);
							}
						/*	if (mName == "" || mName == undefined || mName == "0") {
								errorList.push(getLocalMessage("sfac.validation.mName")
												+ " " + rowCount);
							}
							
							if (lName == "" || lName == undefined || lName == "0") {
								errorList.push(getLocalMessage("sfac.validation.lName") + " " + rowCount);
							}*/
				
							if (contactNo == "" || contactNo == undefined || contactNo == "0") {
								errorList.push(getLocalMessage("sfac.validation.contactNo")	+ " " + rowCount);
							} else { 
								if (contactNo != "" && (contactNo.length < 10 || contactNo == '0000000000')) {
									errorList.push(getLocalMessage("sfac.valid.contactNo")	+ " " + rowCount);
								}
								if (contact.includes(contactNo)) {
									errorList.push(getLocalMessage("sfac.dup.contact") + " "+ rowCount);
								}
								if (errorList.length == 0) {
									contact.push(contactNo);
								}
							}
							if (emailId == "" || emailId == undefined || emailId == "0") {
								errorList.push(getLocalMessage("sfac.validation.emailId")	+ " " + rowCount);
							}else if (emailId != "") {
									var emailRegex = new RegExp(
											/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
									var valid = emailRegex.test(emailId);
									if (!valid) {
										errorList.push(getLocalMessage("sfac.valid.iaEmailId")+ " "+rowCount);
									}
									
									if (email.includes(emailId)) {
										errorList.push(getLocalMessage("sfac.dup.emailId")	+" "+ rowCount);
									}
									if (errorList.length == 0) {
										email.push(emailId);
									}
							}
							
						});
	return errorList;
}

$('#IAName').on('blur', function(){
	var errorList = [];
	var IAName = $("#IAName").val();
	var viewMode = $("#viewMode").val();
	if (IAName != '' && viewMode == 'A'){
	var request = {
			"IAName" : IAName
		};
		var response = __doAjaxRequest('IAMasterForm.html?checkIaNameExist', 'post', request,
				false, 'json');
		if (response == true){
			$('#dupIaName').val(response);
			errorList.push(getLocalMessage("sfac.fpo.validation.IaName.exist"));
			displayErrorsOnPage(errorList);
		}
	}
});

$('#iaShortName').on('blur', function(){
	var errorList = [];
	var iaShortName = $("#iaShortName").val();
	var viewMode = $("#viewMode").val();
	if (iaShortName != ''  && viewMode == 'A'){
	var request = {
			"iaShortName" : iaShortName
		};
		var response = __doAjaxRequest('IAMasterForm.html?checkIaShortNmExist', 'post', request,
				false, 'json');
		if (response == true){
			$('#dupIaShortNm').val(response);
			errorList.push(getLocalMessage("sfac.fpo.validation.iaShortName.exist"));
			displayErrorsOnPage(errorList);
		}
	}
});
