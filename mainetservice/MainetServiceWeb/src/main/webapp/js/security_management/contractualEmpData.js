/** Author : Arun Shinde **/

$(document).ready(function() {
	
	prepareDateTag();
	$("#frmContractualStaffMasterTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$('body').on('focus', ".hasMobileNo", function() {
		$('.hasMobileNo').keyup(function() {
			this.value = this.value.replace(/[^0-9]/g, '');
			$(this).attr('maxlength', '10');
		});
	});
	
	var end = new Date();
	$("#dob").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
	    changeYear: true,
		yearRange: "-200:+200",
		maxDate : new Date(end.getFullYear()-18, 11, 31)
	});
	
});


function SearchContractStaff() {
	var errorList = [];
	var contStaffName=$('#contStaffName').val();
	var vendorId=$('#vmVendorid').val();
	var locId = $('#location').val();
	var cpdShiftId = $('#cpdShiftId').val();
	var dayPrefixId= $('#dayPrefixId').val();
	
	if (contStaffName != "" || vendorId != 0 || locId != 0 || cpdShiftId !=0 || dayPrefixId !=0) {
		var requestData = 'contStaffName=' + contStaffName + '&vendorId=' + vendorId+'&locId=' +locId+'&cpdShiftId=' + cpdShiftId+'&dayPrefixId=' + dayPrefixId;
		var table = $('#frmContractualStaffMasterTbl').DataTable();
		var URL = "ContractualStaffMaster.html?searchStaffDetails";
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
		var staffMasterDTOList = response;
		if (staffMasterDTOList.length == 0) 
		{
			errorList.push(getLocalMessage("ContractualStaffMasterDTO.Validation.noRecord"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$.each(
				staffMasterDTOList,
						function(index) {
			
							var obj = staffMasterDTOList[index];
							let contStaffName = obj.contStaffName;
							let contStaffIdNo = obj.contStaffIdNo;
							let status = obj.status;
							let contStsffId = obj.contStsffId
							let desiDesc = obj.desiDesc
							let shiftDesc= obj.shiftDesc
							let vendorDesc=obj.vendorDesc
							let locDesc=obj.locDesc
							
							if(contStaffName==null|| contStaffName==""){
								contStaffName="-";
							}
							if (contStaffIdNo==""){
								contStaffIdNo="-";
							}
							if (desiDesc==null || desiDesc=="" ){
								desiDesc="-";
							}
							if (vendorDesc==null || vendorDesc==""){
								vendorDesc="-";
							}
							if (locDesc==null|| locDesc==""){
								locDesc="-";
							}
							if (shiftDesc==null|| shiftDesc==""){
								shiftDesc="-";
							}
							
							
					result.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ contStaffName + '</div>',
											'<div class="text-center">'
													+ contStaffIdNo + '</div>',
											'<div class="text-center">'
													+ desiDesc + '</div>',
											'<div class="text-center">'
													+ vendorDesc + '</div>',
											'<div class="text-center">'
													+ locDesc + '</div>',
											'<div class="text-center">'
													+ shiftDesc + '</div>',
											'<div class="text-center">'+
													'<c:choose>'+
													'<c:when test="${'+status +'eq "A"}">'+
													'<a href="#" class="fa fa-check-circle fa-2x green" title="Active"></a>'+
													'</c:when>'+
													'</c:choose>'+'</div>',
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="getContractualStaffMaster(\''
													+ contStsffId
													+ '\',\'ContractualStaffMaster.html\',\'VIEW\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="getContractualStaffMaster(\''
													+ contStsffId
													+ '\',\'ContractualStaffMaster.html\',\'EDIT\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.Validation.selectone"));
		displayErrorsOnPage(errorList);
	}
	
}




function addContractualStaffMaster(formUrl,actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode":mode,
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function getContractualStaffMaster(id,formUrl,actionParam, mode){
	
	var divName = '.content-page';
	var requestData = {
			"mode":mode,
			"id":id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}

function confirmToProceed(element,mode) {
	var errorList = [];
	errorList = validateContractualStaffMaster(errorList,mode);
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		
	}
	else{
		return saveOrUpdateForm(element,"", 'ContractualStaffMaster.html', 'saveform');
	}
	
}

function resetForm2() {
    $("#frmContractualStaffMasterForm").prop('action', '');
    $("select").val("").trigger("chosen:updated");
    $('.error-div').hide();
    prepareTags();
}



function validateContractualStaffMaster(errorList,mode) {
	
	var hideEmpId = $("#hideEmpId").val();
	var empId = $("#empId").val();
	if(hideEmpId=="R"){
		if(empId==null || empId==""){
			errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.empName"));
		}
	}
	var contStaffName = $("#contStaffName").val();
	var sex = $("#sex").val();
	var contStaffMob = $("#contStaffMob").val();
	var contStaffAddress = $("#contStaffAddress").val();
	var designation = $("#designation").val();
	var vmVendorid = $("#vmVendorid").val();
	var contStaffIdNo = $("#contStaffIdNo").val();
	var location = $("#location").val();
	var cpdShiftId = $("#cpdShiftId").val();
	var dayPrefixId = $("#dayPrefixId").val();
	var status = $("#status").val();
	var date=new Date();
	var dob = $("#dob").val();
	var contStaffAppointDate = $("#contStaffAppointDate").val();
	var contStaffSchFrom = $("#contStaffSchFrom").val();
	var contStaffSchTo = $("#contStaffSchTo").val();
	var contStaffMob = $("#contStaffMob").val();
	if(contStaffMob != 0){
		if(contStaffMob.length!=10)
			{
			errorList
			.push(getLocalMessage("ContractualStaffMasterDTO.Validation.validMobileNo"));
			}
	}
	if(contStaffName==""||contStaffName==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.contStaffName"));
	if(sex=="0"||sex==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.sex"));
	/*if(dob==""||dob==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.dob"));*/
	if(designation==""||designation==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.dsgId"));
	if(vmVendorid==""||vmVendorid==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.vendorId"));
	if(contStaffIdNo==""||contStaffIdNo==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.contStaffIdNo"));
	if(contStaffAppointDate==""||contStaffAppointDate==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.contStaffAppointDate"));
	if(location==""||location==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.locId"));
	if(cpdShiftId=="0"||cpdShiftId==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.cpdShiftId"));
	if(dayPrefixId=="0"||dayPrefixId==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.dayPrefixId"));
	if(contStaffSchFrom==""||contStaffSchFrom==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.contStaffSchFrom"));
	if(contStaffSchTo==""||contStaffSchTo==null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.contStaffSchTo"));
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						//From script library
	var dob = new Date(dob.replace(pattern, '$3-$2-$1'));
	var contStaffAppointDate = new Date(contStaffAppointDate.replace(pattern, '$3-$2-$1'));
	var contStaffSchFrom = new Date(contStaffSchFrom.replace(pattern, '$3-$2-$1'));
	var contStaffSchTo = new Date(contStaffSchTo.replace(pattern, '$3-$2-$1'));
	if (dob >= date) {
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.dobAndCurrent"));
	}
	if (contStaffAppointDate >= date) {
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.apointAndCurrent"));
	}
	if (dob >= contStaffAppointDate) {
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.appointAndDob"));
	}
	if (contStaffAppointDate > contStaffSchFrom) {
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.fromdateAndAppoint"));
	}
	if (contStaffSchFrom > contStaffSchTo) {
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.toAndFrom "));
	}
	
	if(vmVendorid!="" && vmVendorid!=null && contStaffIdNo!="" && contStaffIdNo!=null && mode!="E"){
	var URL="ContractualStaffMaster.html?checkEmployeeId";
	var requestData='vmVendorid='+vmVendorid+'&contStaffIdNo='+contStaffIdNo;
	var resData = __doAjaxRequest(URL, 'POST', requestData, false);
	if(resData==true){
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.alreadyExists"));
		}
	}
	return errorList;
	
}

function getEmployeeList(){

	$('#hideEmpId').val('R');
	var empType = $("#permanent").val();
	var requestData = 'empType=' + empType ;
	var URL = "ContractualStaffMaster.html?getEmployeeList";
	if(empType=="R"){
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
		$.each(response, function( index) {
			var obj = response[index];		
			$("#empId").append($("<option></option>")
					.attr("value",obj.empId)
					.text(obj.empname+" "+obj.empmname+" "+obj.emplname));										
		});
		
	}
	$('#empId').prop('disabled', false);
	$('#employeeNames').prop('disabled', false);
}

function getContractEmpList(){
	$("#empId").prop("disabled",true);
	$('#employeeNames').prop('disabled',true);
	var divName = '.content-page';
	var requestData = 'mode=' + 'A' ;
	var URL="ContractualStaffMaster.html?ADD";
	var response = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$(divName).html(response);
}

$(function() {
	$("#empId").change(function() {
	var divName = '.content-page';
	var empId = $("#empId").val();
	var requestData = 'empId=' + empId ;
	var URL="ContractualStaffMaster.html?getEmpData";
	var response = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$(divName).html(response);
	$('#hideEmpId').val('R');
	});
});
