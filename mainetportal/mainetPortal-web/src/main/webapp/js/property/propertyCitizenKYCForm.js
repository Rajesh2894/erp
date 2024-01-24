$(document).ready(function(){
	
	 $("#showFlatNo").hide();
	 $("#serchBtnDirect").hide();
	 $("#conDate").hide();
	 $("#showSearchPropNoGrid").hide();
	 $("#showBasicSearch").show();
	 var searchFlag = $("#searchFlag").val();
	 if(searchFlag == 'Y'){
		 $("#showSearchPropNoGrid").show();
		 $("#showBasicSearch").hide();
	 }
	 $(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,
		});
	 
	 
	 $("#propDuesCheck").dataTable({
			"oLanguage" : {
				"sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
		});
	 
	 var isUnderGroundDrainConAvail = $("#isUnderGroundDrainConAvail").val();
		if(isUnderGroundDrainConAvail == 'Yes'){
			$("#conDate").show();
		}else{
			$("#conDate").hide();
			 $("#connectionDate").val('');
		}
	
});



function redirectToSearchPropNo(obj) {
	
	var errorList = [];
	var propNo = $("#assNo").val();
	if (propNo == "") {
		errorList.push(getLocalMessage("prop.no.enter"));
	}
		if (errorList.length == 0){
			 var propNo=$("#assNo").val();
			    var requestData = {
				"propNo" : propNo
			    };

			    var ajaxResponse = __doAjaxRequest(
				'PropertyCitizenKYCForm.html?getBillingMethod', 'POST',
				requestData, false, 'html');

			    if (ajaxResponse != null && ajaxResponse != "") {
			    	var prePopulate = JSON.parse(ajaxResponse);

			    	$.each(prePopulate, function(index, value) {
			    		$('#flatNo').append(
			    				$("<option></option>").attr("value", value).text(
			    						(value)));
			    	});
			    	$('#flatNo').trigger("chosen:updated");
			    	  $("#showFlatNo").show();
			    	  $("#serchBtn").hide();
			    	  $("#serchBtnDirect").show();
			    	  $("#billingMethod").val('I');
			    }else{
			    	SearchButton(obj);
			    } 
		}else{
			displayErrorsOnPage(errorList);
		}
		
			
}


function SearchButton(obj)
{
	
		var errorList = [];
		var billMethod = $("#billingMethod").val();
		if(billMethod == 'I'){
			var flatNo = $("#flatNo").val();
			if (flatNo == "" || flatNo == undefined || flatNo == null) {
				errorList.push(getLocalMessage('prop.select.flatNo'));
			}
		}
			
		if (errorList.length == 0)
		{
			var divName = '.content-page';
			var requestData = $('form').serialize();
			var ajaxResponse =  __doAjaxRequest(
						'PropertyCitizenKYCForm.html?getPropertyDetails', 'POST',
						requestData, false, 'html');

			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			if(billMethod == 'I'){
				 $("#showFlatNo").show();
		    	  $("#serchBtn").show();
		    	  $("#serchBtnDirect").hide();
			}
			return false;
			
	 	}		
		else{
			displayErrorsOnPage(errorList);
		}
		
}


function getOtpGeneration(obj)
{
	 var errorList = [];
	 var mobileNo = $("#mobileNo").val();
	 if (mobileNo == "") {
			errorList.push("please enter mobile No");
		}else{
			if(mobileNo.length < 10) {
				errorList.push("Invalid Mobile No.");
			}
			
			else if(!validateMobile(mobileNo)){
				errorList.push(getLocalMessage('water.validation.ApplicantInvalidmobile'));
			}
		}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	    }else{
	    	var divName = '.content-page';
	    	var requestData = $('form').serialize();
	    	var ajaxResponse =  __doAjaxRequest(
	    				'PropertyCitizenKYCForm.html?generateOtp', 'POST',
	    				requestData, false, 'html');
	    }
		
}

function validateMobile(mobile) {
	
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}

function savePorpertyData(element) {
    var errorList = [];
    
    
    if (errorList.length > 0) {
	displayErrorsOnPage(errorList);
    }

    else {
	$.fancybox.close();
	var divName = '.content-page';

	var formName = findClosestElementId(element, 'form');
	var requestData = $('form').serialize();
	var object = __doAjaxRequest(
		"PropertyCitizenKYCForm.html?savePropertyData", 'POST',
		requestData, false, 'json');

	if (object.error != null && object.error != 0) {
	    $.each(object.error, function(key, value) {
		$.each(value, function(key, value) {
		    if (value != null && value != '') {
			errorList.push(value);
		    }
		});
	    });
	    displayErrorsOnPage(errorList);
	} else {

	    if (object.applicationId != null) {
		showBoxForApproval(getLocalMessage("Data Saved Successfully" + " "+object.applicationId));
	    }
	}

    }

}


function showBoxForApproval(succesMessage) {

    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("proceed");
    var no = 'No';
    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
	    + '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed + '\'  id=\'Proceed\' '
		+ ' onclick="closeApproval();"/>' + '</div>';

    

    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
    $(childDivName).html(message);
    $(childDivName).show();
    $('#Proceed').focus();
    showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'CitizenHome.html';
    $.fancybox.close();
}

function showConnectionDate() {
	debugger;
	var isUnderGroundDrainConAvail = $("#isUnderGroundDrainConAvail").val();
	if(isUnderGroundDrainConAvail == 'Yes'){
		$("#conDate").show();
	}else{
		$("#conDate").hide();
		 $("#connectionDate").val('');
	}
}
function resetorm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'PropertyCitizenKYCForm.html');
	$("#postMethodForm").submit();
}

function showSearchPropNoGrid(){
	$("#showSearchPropNoGrid").show();
	$("#showBasicSearch").hide();
	$("#searchFlag").val('Y');
	
}

function searchPropertyNo(){
	var divName = '.content-page';
	var requestData = $('form').serialize();
	var ajaxResponse =  __doAjaxRequest(
				'PropertyCitizenKYCForm.html?searchPropertyNo', 'POST',
				requestData, false, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}

function getpropertyDetailsFromSearchGrid(propNo, flatNo){
	 var requestData = {
				"propNo" : propNo,
				"flatNo" : flatNo
 			    };
	 
	 
	 var divName = '.content-page';
		var ajaxResponse =  __doAjaxRequest(
					'PropertyCitizenKYCForm.html?getpropertyDetailsFromSearchGrid', 'POST',
					requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	 
}
