$(document).ready(function() {
	prepareDateTag();
	$("#petrolReqFormDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	
	$('#fromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});
	$("#fromDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#toDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});

	$("#toDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});	
	
	$('#date').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});
	$("#date").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
});


function searchPetrolRequest() {
      var errorList = [];
	  var fromDate = $('#fromDate').val();
	  var toDate = $('#toDate').val();
	  var date = new Date();
	  var department = $('#department').val();
	  var veNo =$('#veNo').val();

	  if(fromDate=="" && toDate=="" && veNo=="" && department=="" ){
		  errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.select.field"));
	  }
		    	 
   	if (errorList.length > 0) {
	 	checkDate(errorList);
	    }
	    else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (eDate > date) {
			errorList
					.push(getLocalMessage("PetrolRequisitionDTO.validation.date2"));
		}
		
		if (eDate > sDate) {
			errorList
					.push(getLocalMessage("PetrolRequisitionDTO.validation.date3"));
		}
		if (sDate >= date) {
			errorList
					.push(getLocalMessage("PetrolRequisitionDTO.validation.date4"));
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
	    }
	    if(errorList.length ==0){
	    if (fromDate != '' || toDate != '' || department !='' || veNo !='') {
	    	
		var requestData = '&fromDate=' + fromDate + '&toDate=' + toDate + '&department=' + department + '&veNo=' + veNo;
		var table = $('#petrolReqFormDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(
				'petrolRequisitionForm.html?SearchPetrol', 'POST',
				requestData, false, 'json');
		var petrolRequisitionDTOs = response;
		if (petrolRequisitionDTOs.length == 0) {
			errorList.push(getLocalMessage("record not found"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(  petrolRequisitionDTOs,
						function(index) {
					var obj = petrolRequisitionDTOs[index];
					let requestId = obj.requestId
					let date = getDateFormat(obj.date);
					let department = obj.deptDesc;
					var veNo = obj.veNo;
					var veChasisSrno = obj.veChasisSrno;
					var driverName = obj.driverDesc;
					let requestStatus = obj.requestStatus;
					
					if(requestStatus == 'OPEN')
						requestStatus = getLocalMessage("fuelling.status.open");
					else if(requestStatus == 'REJECTED')
						requestStatus = getLocalMessage("fuelling.status.rejected");
					else if(requestStatus == 'APPROVED')
						requestStatus = getLocalMessage("fuelling.status.approved");
					
					
					var viewMessage = getLocalMessage("vehicle.view");
			
					result
							.push([
									'<div class="text-center">' + (index + 1) + '</div>',
									'<div class="text-center">' + date + '</div>',
									'<div class="text-center">' + department + '</div>',
									'<div class="text-center">' + veNo + '</div>',
									'<div class="text-center">' + veChasisSrno + '</div>',
									'<div class="text-center">' + driverName + '</div>',
									'<div class="text-center">' + requestStatus + '</div>',
									
									'<div class="text-center">' 
									+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyPetrolRequest(\''
									+ requestId
									+ '\',\'petrolRequisitionForm.html\',\'viewPETROL\',\'V\')" title="'+viewMessage+'"><i class="fa fa-eye"></i></button>'
									+ '</div>' ]);

				});
table.rows.add(result);
table.draw();
}
	    } 
	    
else {
//	errorList.push(getLocalMessage("Select At least One Criteria"));
	displayErrorsOnPage(errorList);
}
}

function modifyPetrolRequest(requestId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : requestId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#date').prop('disabled', true); //Defect #157019

 }

function checkDate(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}


function confirmToProceed(element) {
	var errorList = [];
	var date = new Date();
	var date1 = $("#date").val();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var eDate = new Date(date1.replace(pattern, '$3-$2-$1'));
	if (eDate > date) {
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.date1"));
	}
	var time = $("#time").val();
	var department = $("#department").val();
	var vehicleType = $("#vehicleType").val();
	var veNo = $("#veid").val();
    var fuelType = $("#fuelType").val();
    var vehicleMeterRead =$("#vehicleMeterRead").val();
	var fuelQuantity = $("#fuelQuant").val();
	var fuelQuantityUnit = $("#fuelQuantityUnit").val();
	var driverName = $("#driverName").val();
	var couponNo =$("#couponNo").val();
	var chasisno = $("#chasisno").val();
	
	if (date1 == null || date1 == "") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.enter.date"));
	if (time == null || time == "") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.enter.time"));
	if (department == null || department == "") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.select.department"));
	if (vehicleType == null || vehicleType == "0") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.select.vehicle.type"));
	if (fuelType == null || fuelType == "0") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.select.fuel.type"));
	if (couponNo == null || couponNo == "") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.enter.coupon.no"));
	if (fuelQuantity == null || fuelQuantity == "") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.enter.fuel.quantity"));
	if (fuelQuantityUnit == null || fuelQuantityUnit == "0") 
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.select.fuel.quantity.unit"));
	if (driverName == null || driverName == "")
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.select.driver.name"));
	if ((veNo == null || veNo == "" || veNo==0) && (chasisno == "" || chasisno == null || chasisno == "0")) 
		errorList.push(getLocalMessage('vehicle.master.validation.chasisno'));

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		if(veNo==0)
			veNo = chasisno;
		
		var requestData = {
			"veNo" : veNo
		};
		var ajaxResponse = doAjaxLoading('petrolRequisitionForm.html?save', requestData, 'json');
		if (!ajaxResponse) {
			errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.vehicle.number.in.progress"));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			return false;
		} else {
			return saveOrUpdateForm(element, "", 'petrolRequisitionForm.html', 'saveform');
		}
	}
}




$("#frmPetrolRequestForm").validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
		
});

function openForm(formUrl, actionParam) {

	var divName = '.content-page';
	var requestData = {

	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}


 function getVehicleNos() {
	var deptId = $("#deptId").find("option:selected").val();
	var vehicleType =$('#vehicleType').find("option:selected").val();
	$('#veNo').html('');
	$('#veNo').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	
	if(deptId!="" &&  deptId!=0 && vehicleType!="" && vehicleType!=0){
		var requestData = {
			    "deptId" : $("#deptId").val(),
				"vehicleType" : $("#veVetype").val()
			}
		var meetingNoList = __doAjaxRequest(petrolRequestForm.html+'?getMeetingNo', 'POST', requestData, false,'json');
		$.each(meetingNoList, function(index, value) {
			 $('#veNo').append($("<option></option>").attr("value",value.veId).attr("code",value.veNo).text(value.veNo));
		});
		 $('#veNo').trigger('chosen:updated');	
	   }
	
	
}

 function showVehicleRegNo() {
	 $('#veid').html('');
	 $('#chasisno').html('');
	 var crtElementId = $("#vehicleType").val();
	 var deptId = $("#department").val();
	 var requestUrl = "petrolRequisitionForm.html?getVehicleNo";
	 var requestData = {
			 "id" : crtElementId,
			 "deptId" : deptId
	 };
	 var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	 $('#veid').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
		 $('#veid').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	 });
	 $('#veid').prop("disabled", false);
	 $('#veid').trigger('chosen:updated');	
		
	 var requestUrl1 = "petrolRequisitionForm.html?getChasisNo";
	 var ajaxResponse1 = __doAjaxRequest(requestUrl1, 'post', requestData, false,'json');
	 $('#chasisno').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse1, function(index, value) {
		 $('#chasisno').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	 });
	 $('#chasisno').prop("disabled", false);
	 $('#chasisno').trigger('chosen:updated');	
}


 function showFuelType() {
	 $('#fuelType').html('');
	 var veid = $("#veid").val();
	 var chasisno = $("#chasisno").val();
	 if(chasisno==0)
		 chasisno="";
	 else
		 veid = chasisno;
	 
	 var requestUrl = "petrolRequisitionForm.html?getFuelType";
	 var requestData = {
			 "id" : veid,
			 "chasisno":chasisno
	 };
	 var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	 $('#fuelType').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
		 $('#fuelType').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	 });
	 $('#fuelType').trigger('chosen:updated');
		
	 if( "" != chasisno ){
		 $('#veid').prop("disabled", true);
		 $('#veid').trigger('chosen:updated');
	 }else{
		 $('#chasisno').prop("disabled", true);
		 $('#chasisno').trigger('chosen:updated');
	 }
}

function getVehicleTypeByDept() {
		
		$('#fuelType').html('');
	    $('#vehicleType').html('');
	    $('#veid').html('');
	    $('#fuelType').html('');
		var department = $("#department").val();
		var requestUrl = "petrolRequisitionForm.html?getDepartmentType";
		var requestData = {
			"id" : department
		};
		var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
		 $('#vehicleType').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
		 $.each(ajaxResponse, function(index, value) {
		$('#vehicleType').append($("<option></option>").attr("value",index).attr("code",index).text(value));
		});
		$('#vehicleType').trigger('chosen:updated');	

}
 
function searchVeNo() {
    var requestData = {
	"veid" : $("#veNo").val()
    };
    
    $('#veNo').html('');
    $('#veNo').append(
	    $("<option></option>").attr("value", "0").text(
		    getLocalMessage('selectdropdown')));
    
    var ajaxResponse = doAjaxLoading(
	    'petrolRequisitionForm.html?searchVeNo', requestData,
	    'json');
   
	var veNodata='<option value="'+ajaxResponse.veId+'">'+ajaxResponse.veNo+'</option>'
	$("#veNo").html(veNodata);
    
}

function getVehicleNoByDept() {
	
	var department = $("#department").val();
	var requestUrl = "petrolRequisitionForm.html?getVehicleNoByDept";
	var requestData = {
		"department" : department
	};
	
	$('#veNo').html('');
	$('#veNo').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown')));
	
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	
	$.each(ajaxResponse, function(index, value) {
		$('#veNo').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#veNo').trigger('chosen:updated');	

}

 
