/**
 * ritesh.patil
 * 
 */

var removeIdArray=[];
var floorCheck;
var removeAmenityArray =[];
var removeFacilityArray =[];
var removeEventArray = [];
var removeShiftArray = [];

$(document).ready(function(){
	$('#estateId').change(function(){
		$('#estatecode').val(__doAjaxRequest(propertyUrl+'?code', 'POST', 'id='+$(this).val(), false));
		floorCheck = __doAjaxRequest(propertyUrl+'?isFloorMandatoty', 'POST', 'id='+$(this).val(), false, 'json');
		$('#floorLabelId').addClass('required-control');
		if(!floorCheck){
			$('#floorLabelId').removeClass('required-control');
		 }
		 
	});

	 $('.hasNumber').blur(function () { 
		    this.value = this.value.replace(/[^0-9]/g,'');
	  });
	 $('.alfaNumric').blur(function () { 
		    this.value = this.value.replace(/[^a-zA-Z0-9 ]/g,'');
	  }); 
	

		       
	
$("#customFields").on('click','.addCF',function(i){
		
		var row=0;
		var errorList = [];
		errorList = validateAdditionalDtl(errorList);
		
		if (errorList.length == 0) {
			if (errorList.length == 0 ) {
				var romm=0;
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				var clickedIndex = $(this).parent().parent().index() - 1;	
				content.find("input:text").val('');
				content.find("input:hidden").val('');
				content.find("select").val('0');
				$('.error-div').hide();
				reOrderTableIdSequence();
			}else {
				displayErrorsOnPageView(errorList);
			}
		}else {
			displayErrorsOnPageView(errorList);
		}
	
});

$("#customFields").on('click','.remCF',function(){
	
	$(this).tooltip('hide'); //Defect #158216
	if($("#customFields tr").length != 3)
		{
			 $(this).parent().parent().remove();
			 reOrderTableIdSequence();
			 id=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
			 if(id != ''){
				 removeIdArray.push(id);
			 }
			 $('#removeChildIds').val(removeIdArray);
			 totalCount();
		}
   else
		{
	   var errorList = [];
	   errorList.push(getLocalMessage("rl.subChild.deletrw.validtn"));   
	   displayErrorsOnPageView(errorList);
		}
 });


$(document).on('blur','.areatotal', function(){
	 this.value = this.value.replace(/[^0-9.]/g,'');
	 totalCount();
 });

$('#submitProp').click(function(){
	  var	formName =	findClosestElementId($(this), 'form');
		var theForm	=	'#'+formName;
	    var errorList = [];
		 errorList = validatePropMasterForm(errorList);
		 let occupacy = $("#occupancy").find("option:selected").attr('code');
		 if (errorList.length == 0) {
			      errorList=validateAdditionalDtl(errorList);
			      //errorList=validatePropShiftData(errorList); //User Story #98287
			      if(occupacy == 'RE'){
			    	  errorList = validateAmenityData(errorList);
				      errorList=validateFacilityData(errorList);
				      errorList=validatePropEventData(errorList);
				      errorList=validatePropShiftData(errorList);  
			      }
			      if(errorList.length == 0){
						 var formName =	findClosestElementId($(this), 'form');
						 var theForm	=	'#'+formName;
						 if($('#hiddeValue').val() == 'E'){
							 return saveOrUpdateForm($(this),"", propertyUrl,'saveform');
						  }else{
							    addProperty = __doAjaxRequest(propertyUrl+'?checkAddProperty', 'POST', 'esId='+$('#estateId').val(), false, 'json');
						        if(addProperty){
							          return saveOrUpdateForm($(this),"", propertyUrl,'saveform');
							     }
								 else{
									 showAddPropertyValidation();
								 }
						  } 
						   
					}else{
						showRLValidation(errorList);
					 }	 
		 }else{
			showRLValidation(errorList);
		 }
   });

 	$('.datetimepicker3').timepicker({timeFormat: "HH:mm"});
 	
 	if($("#modeType").val() != 'C'){
 		var occupacy = $("#occupancy").find("option:selected").attr('code');
 	 	//D#72266
 	 	if(occupacy == 'LE'){
 			$('.courtCase').show();
 			//hide fields in case of Leases
 			$("#propLatitudeLbl").removeClass( "required-control")		
 			$("#propLatitude").removeClass("mandColorClass").parent().removeClass("has-error");				
 			$("#propLongitudeLbl").removeClass( "required-control")		
 			$("#propLongitude").removeClass("mandColorClass").parent().removeClass("has-error");		
 			$("#propCapacityLbl").removeClass( "required-control")		
 			$("#propCapacity").removeClass("mandColorClass").parent().removeClass("has-error");				
 			$("#propNoDaysAllowLbl").removeClass( "required-control")		
 			$("#propNoDaysAllow").removeClass("mandColorClass").parent().removeClass("has-error");
 		}else{
 			$('.courtCase').hide();
 			$("#propLatitudeLbl").addClass("required-control");		
 			$("#propLatitude").addClass("mandColorClass");				
 			$("#propLongitudeLbl").addClass( "required-control");		
 			$("#propLongitude").addClass("mandColorClass");		
 			$("#propCapacityLbl").addClass( "required-control");		
 			$("#propCapacity").addClass("mandColorClass");				
 			$("#propNoDaysAllowLbl").addClass( "required-control");		
 			$("#propNoDaysAllow").addClass("mandColorClass");		
 		}
 	 	
 	 	
 	}
 	if($("#modeType").val() == 'C'){
 		$("input:radio[value='Y']").attr("checked","checked");
 	   }
 	
 	//check property code value is non empty than hit to get data and disable necessory field
 	/*let propertyCode = $("#occupancy").find("option:selected").attr('code');
 	alert();*/
 	
 	if($("#assesmentPropId").val() != '' && $('#hiddeValue').val() == 'E'){
 		getPropertyMasterDetails();	
 	}
 	
});

function totalCount(){
	 var value=0.0;
	 $(".areatotal").each(function(i) {
			if($('#area_'+i).val() == ''){
				value+=parseFloat('0');
			}else{
				value+=parseFloat($('#area_'+i).val());
			 }
		    $('#totalCal').val(value);
		});
}

function reOrderTableIdSequence() {

	$('.appendableClass').each(function(i) {

		$(this).find("select:eq(0)").attr("id", "areaType_"+i);
		$(this).find("input:hidden:eq(0)").attr("id", "propDetId_"+i);
		$(this).find("input:text:eq(0)").attr("id", "area_"+i);
		$(this).find("select:eq(0)").attr("name","estatePropMaster.details["+i+"].areaType");
		$(this).find("input:hidden:eq(0)").attr("name", "estatePropMaster.details["+i+"].propDetId");
		$(this).find("input:text:eq(0)").attr("name", "estatePropMaster.details["+i+"].area");
	});
	
}

function validateAdditionalDtl(errorList){

$('.appendableClass').each(function(i) {
	row=i+1;
		errorList =  validateDetailsTableData(errorList,i);
  });
  
return errorList;

}


/**
 * validate each mandatory column of additional owner details 
 * @param errorList
 * @param i
 * @returns
 */
function validateDetailsTableData(errorList, i) {

	 var type = $.trim($("#areaType_"+i).val());
	 var area = $.trim($("#area_"+i).val());		
	 var j=i+1;
	 if(type =="" || type =='0'  || type == undefined ){
		 errorList.push(getLocalMessage('rl.property.master.areatype.validate.msg') +" "+j);
	 }
	 if(area == "" || area == undefined){
		 errorList.push(getLocalMessage('rl.property.master.area.validate.msg')+" "+j);
	 }
	
	return errorList;
}

function validatePropMasterForm(errorList) {
		//tenantType= $('#type :selected').attr('code');
		estateName= $('#estateId').val();
		name= $.trim($('#name').val());
		occupancy= $('#occupancy').val();
		roadType= $('#roadType').val();
		propLatitude=$("#propLatitude").val();
		propLongitude=$("#propLongitude").val();
		var radioValue = $("input[name='estatePropMaster.status']:checked").val();
		var propCapacity = $("#propCapacity").val();
		var propMaintainBy = $("#propMaintainBy").val();
		var propNoDaysAllow = $("#propNoDaysAllow").val();
		var usage = $('#usageId').val();

		if(estateName == '0' || estateName == undefined ){
			 errorList.push(getLocalMessage('rl.property.master.estate.validate.msg'));
		 }
		if(name == '' || name == undefined ){
			 errorList.push(getLocalMessage('rl.property.master.prop.validate.msg'));
		 }
		if(occupancy == '0' || occupancy == undefined ){
			 errorList.push(getLocalMessage('rl.property.master.occup.validate.msg'));
		}
		if(usage == '0' || usage =='' ||usage == undefined ){
			 errorList.push(getLocalMessage('rl.property.master.usage.validate.msg'));
		 }
		
        if(floorCheck){
        	 floor= $('#floor').val();
			 if(floor == '0' || floor == undefined ){
				 errorList.push(getLocalMessage('rl.property.master.floor.validate.msg'));
			 }
			 $('#floorLabelId').addClass('required-control');
	     }
			
		if(roadType == '0' || roadType == undefined ){
			 errorList.push(getLocalMessage('rl.property.master.road.validate.msg'));
		 }
		
		var occupacy = $("#occupancy").find("option:selected").attr('code');
		if(occupacy == 'RE'){
			if(propLatitude == '' || propLatitude == undefined ){
				 errorList.push(getLocalMessage('rl.property.master.latitude.validate.msg'));
			 }
			
			if(propLongitude == '' || propLongitude == undefined ){
				 errorList.push(getLocalMessage('rl.property.master.longitude.validate.msg'));
			 }
			
			if(propCapacity == '' || propCapacity == undefined ){
				 errorList.push(getLocalMessage('rnl.enter.capacity'));
			 }	
			if(propMaintainBy == '0' || propMaintainBy == undefined ){
				 errorList.push(getLocalMessage('rnl.select.property.maintainBy'));
			 }
			if(propNoDaysAllow == '' || propNoDaysAllow == undefined ){
				 errorList.push(getLocalMessage('rnl.enter.noOfDays.allow.book'));
			 }
		}
		
		if(radioValue == '' || radioValue == undefined ){
			 errorList.push(getLocalMessage('rnl.select.status'));
		 }
		
		
	 return errorList;	
}

function displayErrorsOnPageView(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}

function showAddPropertyValidation(){
	
	var warnMsg=getLocalMessage("rl.common.can.not.add.property");
	message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	$(childDivName).html(message);
	$(childDivName).show();
	showModalBox(childDivName);
	return false;
	
}

// Added By Vishwajeet.kumar 

/**
 * validate property Facility List of data 
 * @param errorList
 * @returns
 */
function validateAmenityData(errorList){
	$('.amenitiesClass').each(function(i) {		
		var AmenityTpe = $("#AmenityTpe" +i).find("option:selected").attr('value');			
		var count = i+1;
		
		if (AmenityTpe == "" ||AmenityTpe == undefined ) {
			errorList.push(getLocalMessage("rnl.select.amenityType")+ " " + count);
		}
	});
	return errorList;
}

/**
* Add Property Amenity Start List 
* @param e
* @returns
*/
$("#ametiesTableClassId").on("click", '.addAmenities', function(e) {
	var count = $('#ametiesTableClassId tr').length - 1;
	var errorList = [];
	errorList = validateAmenityData(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPageView(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#ametiesTableClassId tr').last().clone();
		$('#ametiesTableClassId tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("select").attr("selected", "selected").val('');
		content.find("input:text").val('');
		reOrderPropAmenity();
	}
});

function reOrderPropAmenity() {	
	$('.amenitiesClass').each(function(i) {
		
       $(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find("select:eq(0)").attr("id", "AmenityTpe"+i).attr('onchange' ,'checkDuplicateAmenities(this,'+ i+')');
		$(this).find("input:hidden:eq(0)").attr("id", "propAmenityId"+i);		
		$("#sNo" + i).val(i + 1);		
		$(this).find("select:eq(0)").attr("name","estatePropMaster.aminityDTOlist["+i+"].propAmtFacility");
		$(this).find("input:hidden:eq(0)").attr("name", "estatePropMaster.aminityDTOlist["+i+"].propAmenityId");
	});	
}

function checkDuplicateAmenities(amenties , row){
	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		$('.amenitiesClass').each(function(i) {
			if (row != i && (amenties.value == $("#AmenityTpe" + i).val())) {
				$("#AmenityTpe" + row).val("");
				errorList.push(getLocalMessage("rnl.duplicate.amenities.not.allowed"));
				$("#errorDiv").show();
				displayErrorsOnPageView(errorList);
				return false;
			}
		});
	}else{
		$("#AmenityTpe" + row).val("");
		displayErrorsOnPageView(errorList);
		return false;
	}	
}

$("#ametiesTableClassId").on("click",'.delAminitiesButton', function(e) {
	var errorList = [];
	var counter = -1;
	$('.amenitiesClass').each(function(i) {
		counter += 1;
	});
	var rowCount = counter;
	if (rowCount == 0) {
		$("#AmenityTpe0").val("");
		var amenityId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');		
		if (amenityId != '') {
			removeAmenityArray.push(amenityId);
		}
		$("#propAmenityId0").val("");
	}
	if (rowCount != 0) {
		$(this).parent().parent().remove();
		rowCount--;
		var amenityId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (amenityId != '') {
			removeAmenityArray.push(amenityId);
		}
	}
	$('#removeAminities').val(removeAmenityArray);
	reOrderPropAmenity();
});

//End Property Amenity List Data


 /**
  * validate property Facility List of data 
  * @param errorList
  * @returns
  */
function validateFacilityData(errorList){
	$('.faciltiesClass').each(function(i) {
		
		var facilityType = $("#facilityType" +i).find("option:selected").attr('value');	
		var facilityQuanity = $("#facilityQuanity" +i).val();		
		var count = i+1;
		
		if (facilityType == "" ||facilityType == undefined ) {
			errorList.push(getLocalMessage("rnl.select.facilityType")+ " " + count);
		}
		
		if (facilityQuanity == "" ||facilityQuanity == undefined ) {
			errorList.push(getLocalMessage("rnl.enter.quantity")+ " " + count);
		}
	});
	return errorList;
}

/**
 * Add Property facility Start List 
 * @param e
 * @returns
 */
$("#faciltiesTableClassId").on("click", '.addFacility', function(e) {

	var count = $('#faciltiesTableClassId tr').length - 1;
	var errorList = [];
	errorList = validateFacilityData(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPageView(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#faciltiesTableClassId tr').last().clone();
		$('#faciltiesTableClassId tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("select").attr("selected", "selected").val('');
		content.find("input:text").val('');
		reOrderPropFacility();
	}
});

function reOrderPropFacility() {	
	$('.faciltiesClass').each(function(i) {
		
        $(this).find("input:text:eq(0)").attr("id", "serNo" + (i));
		$(this).find("select:eq(0)").attr("id", "facilityType"+ (i)).attr('onchange','checkDuplicateFacility(this,'+ i +')');
		$(this).find("input:hidden:eq(0)").attr("id", "propFacilityId"+ (i));
		$(this).find("input:text:eq(1)").attr("id", "facilityQuanity"+ (i));
		
		$("#serNo" + i).val(i + 1);
		
		$(this).find("select:eq(0)").attr("name","estatePropMaster.facilityDTOlist["+i+"].propAmtFacility");
		$(this).find("input:hidden:eq(0)").attr("name", "estatePropMaster.facilityDTOlist["+i+"].propAmenityId");
		$(this).find("input:text:eq(1)").attr("name", "estatePropMaster.facilityDTOlist["+i+"].propQuantity");
	});	
}

function checkDuplicateFacility(facility , row){
	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		$('.faciltiesClass').each(function(i) {
			if (row != i && (facility.value == $("#facilityType" + i).val())) {
				$("#facilityType" + row).val("");
				errorList.push(getLocalMessage("rnl.duplicate.facility.not.allowed"));
				$("#errorDiv").show();
				displayErrorsOnPageView(errorList);
				return false;
			}
		});
	}else{
		$("#facilityType" + row).val("");
		displayErrorsOnPageView(errorList);
		return false;
	}	
}

$("#faciltiesTableClassId").on("click",'.delFacilitiesButton', function(e) {
	var errorList = [];
	var counter = -1;
	$('.faciltiesClass').each(function(i) {
		counter += 1;
	});
	var rowCount = counter;
	if (rowCount == 0) {
		$("#facilityType0").val("");
		$("#facilityQuanity0").val("");
		var facilityId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');		
		if (facilityId != '') {
			removeFacilityArray.push(facilityId);
		}
		$("#propFacilityId0").val("");
	}
	if (rowCount != 0) {
		$(this).parent().parent().remove();
		rowCount--;
		var facilityId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (facilityId != '') {
			removeFacilityArray.push(facilityId);
		}
	}
	$('#removeFacilityIds').val(removeFacilityArray);
	reOrderPropFacility();
});

// End Property facility List Data


/* Add Select Event Type Related Data */

function validatePropEventData(errorList){
	
	$('.eventTypeClass').each(function(i) {	
		var eventType = $("#eventType" +i).find("option:selected").attr('value');
		var propAllowFlag = $("#propAllowFlag" +i).val();		
		var count = i+1;
		
		if (eventType == "" || eventType == undefined ) {
			errorList.push(getLocalMessage("rnl.select.eventType")+ " " + count);
		}
		
		if (propAllowFlag == "" || propAllowFlag == undefined ) {
			errorList.push(getLocalMessage("rnl.select.status")+ " " + count);
		}
	});	
	return errorList;
}

$("#eventTypeTableClassId").on("click", '.addEventType', function(e) {

	var count = $('#eventTypeTableClassId tr').length - 1;
	var errorList = [];
	errorList = validatePropEventData(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPageView(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#eventTypeTableClassId tr').last().clone();
		$('#eventTypeTableClassId tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("select").attr("selected", "selected").val('');
		content.find("input:text").val('');
		reOrderPropEvent();
	}
});

function reOrderPropEvent() {
	$('.eventTypeClass').each(function(i) {
        $(this).find("input:text:eq(0)").attr("id", "srNo" + (i));
		$(this).find("select:eq(0)").attr("id", "eventType"+i).attr('onchange','checkDuplicateEvent(this, '+ i +')');
		$(this).find("select:eq(1)").attr("id", "propAllowFlag"+i)
		$(this).find("input:hidden:eq(0)").attr("id", "propEventId"+i);
		
		$("#srNo" + i).val(i + 1);
		
		$(this).find("select:eq(0)").attr("name","estatePropMaster.eventDTOList["+i+"].propEvent");
		$(this).find("select:eq(1)").attr("name","estatePropMaster.eventDTOList["+i+"].propAllowFlag");
		$(this).find("input:hidden:eq(0)").attr("name", "estatePropMaster.eventDTOList["+i+"].propEventId");
	});	
}

function checkDuplicateEvent(event , row){
	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		$('.eventTypeClass').each(function(i) {
			if (row != i && (event.value == $("#eventType" + i).val())) {
				$("#eventType" + row).val("");
				errorList.push(getLocalMessage("rnl.duplicate.event.not.allowed"));
				$("#errorDiv").show();
				displayErrorsOnPageView(errorList);
				return false;
			}
		});
	}else{
		$("#eventType" + row).val("");
		displayErrorsOnPageView(errorList);
		return false;
	}	
}

$("#eventTypeTableClassId").on("click",'.delEventButton', function(e) {
	var errorList = [];
	var counter = -1;
	$('.eventTypeClass').each(function(i) {
		counter += 1;
	});
	var rowCount = counter;
	if (rowCount == 0) {
		$("#eventType0").val("");
		$("#propAllowFlag0").val("");
		var eventId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');		
		if (eventId != '') {
			removeEventArray.push(eventId);
		}
		$("#propEventId0").val("");
	}
	if (rowCount != 0) {
		$(this).parent().parent().remove();
		rowCount--;
		var eventId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (eventId != '') {
			removeEventArray.push(eventId);
		}
	}
	$('#removeEventIds').val(removeEventArray);
	reOrderPropEvent();
});


//***** Add Select Shift Related Data ***********//

function validatePropShiftData(errorList){
	
	$('.shiftClass').each(function(i) {	
		var shift = $("#shift" +i).find("option:selected").attr('value');
		var propFromTime = $("#propFromTime" +i).val();	
		var propToTime = $("#propToTime" +i).val();
		var count = i+1;
		
		if (shift == "" || shift == undefined) {
			errorList.push(getLocalMessage("rnl.select.shift")+ " " + count);
		}
		
		if (propFromTime == "" || propFromTime == undefined) {
			errorList.push(getLocalMessage("rnl.select.fromTime")+ " " + count);
		}
		
		
		if (propToTime == "" || propToTime == undefined ) {
			errorList.push(getLocalMessage("rnl.select.toTime")+ " " + count);
		}
		
		
		//D#74783
		let checkInTime = timeValidationCheck(propFromTime);
		let checkOutTime = timeValidationCheck(propToTime);
		if(!checkInTime){
			errorList.push(getLocalMessage("rnl.valid.checkin.time"));
		}
		if(!checkOutTime){
			errorList.push(getLocalMessage("rnl.valid.checkout.time"));
		}
	    	
		if(propToTime < propFromTime){
			errorList.push(getLocalMessage("rnl.toTime.greater.fromTime"));
		}
	});
	
	return errorList;
}

function timeValidationCheck(value){
	if (!/^\d{2}:\d{2}$/.test(value)){
		return false;
	}	
    var parts = value.split(':');
    if (parts[0] > 23 || parts[1] > 59 ){
    	return false;
    }
    return true;
}

$("#shiftTableClassId").on("click", '.addShift', function(e) {
	var count = $('#shiftTableClassId tr').length - 1;
	var errorList = [];
	errorList = validatePropShiftData(errorList);
	$(".datetimepicker3").timepicker("destroy");
	if (errorList.length > 0) {
		displayErrorsOnPageView(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#shiftTableClassId tr').last().clone();
		$('#shiftTableClassId tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("select").attr("selected", "selected").val('');
		content.find("input:text").val('');
		
		reOrderPropShift();
		$("#errorDivId").hide();
	}
	$('.datetimepicker3').timepicker({timeFormat: "HH:mm"});
});

function reOrderPropShift() {
	$('.shiftClass').each(function(i) {

        $(this).find("input:text:eq(0)").attr("id", "shiftSrNo" + (i));
		$(this).find("select:eq(0)").attr("id", "shift"+i).attr('onchange',
				'checkDuplicateShift(this,' + i + ')');
		 $(this).find("input:text:eq(1)").attr("id", "propFromTime" + (i));
		 $(this).find("input:text:eq(2)").attr("id", "propToTime" + (i));
		$(this).find("input:hidden:eq(0)").attr("id", "propShifId"+i);
		
		$("#shiftSrNo" + i).val(i + 1);
		
		$(this).find("input:hidden:eq(0)").attr("name", "estatePropMaster.propertyShiftDTOList["+i+"].propShifId");
		$(this).find("select:eq(0)").attr("name","estatePropMaster.propertyShiftDTOList["+i+"].propShift");
		$(this).find("input:text:eq(1)").attr("name","estatePropMaster.propertyShiftDTOList["+i+"].startTime");
	    $(this).find("input:text:eq(2)").attr("name","estatePropMaster.propertyShiftDTOList["+i+"].endTime");
		
	});	
}

function checkDuplicateShift(shift , row){
	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		$('.shiftClass').each(function(i) {
			if (row != i && (shift.value == $("#shift" + i).val())) {
				$("#shift" + row).val("");
				errorList.push(getLocalMessage("rnl.duplicate.shift.not.allowed"));
				$("#errorDiv").show();
				displayErrorsOnPageView(errorList);
				return false;
			}
		});
	}else{
		$("#shift" + row).val("");
		displayErrorsOnPageView(errorList);
		return false;
	}	
}

$("#shiftTableClassId").on("click",'.delShiftButton', function(e) {
	var errorList = [];
	var counter = -1;
	$('.shiftClass').each(function(i) {
		counter += 1;
	});
	var rowCount = counter;
	if (rowCount == 0) {
		$("#eventType0").val("");
		$("#propAllowFlag0").val("");
		var shiftId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');		
		if (shiftId != '') {
			removeShiftArray.push(shiftId);
		}				
	}
	if (rowCount != 0) {
		$(this).parent().parent().remove();
		rowCount--;
		var shiftId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (shiftId != '') {
			removeShiftArray.push(shiftId);
		}
	}
	$('#removeShiftIds').val(removeShiftArray);
	reOrderPropShift();
});

$("#occupancy").change(function(){
	var occupacy = $("#occupancy").find("option:selected").attr('code');
	//D#72266
	if(occupacy == 'LE'){
		$('.courtCase').show();
		//hide fields in case of Leases
		$("#propLatitudeLbl").removeClass( "required-control")		
		$("#propLatitude").removeClass("mandColorClass").parent().removeClass("has-error");				
		$("#propLongitudeLbl").removeClass( "required-control")		
		$("#propLongitude").removeClass("mandColorClass").parent().removeClass("has-error");		
		$("#propCapacityLbl").removeClass( "required-control")		
		$("#propCapacity").removeClass("mandColorClass").parent().removeClass("has-error");				
		$("#propNoDaysAllowLbl").removeClass( "required-control")		
		$("#propNoDaysAllow").removeClass("mandColorClass").parent().removeClass("has-error");
		$("#propMaintainByLbl").removeClass( "required-control")		
		$("#propMaintainBy").removeClass("mandColorClass").parent().removeClass("has-error");
	}else{
		$('.courtCase').hide();
		$("#propLatitudeLbl").addClass("required-control");		
		$("#propLatitude").addClass("mandColorClass");				
		$("#propLongitudeLbl").addClass( "required-control");		
		$("#propLongitude").addClass("mandColorClass");		
		$("#propCapacityLbl").addClass( "required-control");		
		$("#propCapacity").addClass("mandColorClass");				
		$("#propNoDaysAllowLbl").addClass( "required-control");		
		$("#propNoDaysAllow").addClass("mandColorClass");
		$("#propMaintainByLbl").addClass( "required-control");		
		$("#propMaintainBy").addClass("mandColorClass");	
	}
});


function allownumeric(obj) {
	 var val = obj.value;
	 val = val.replace(/[^\d].+/, "");
           if ((event.which < 48 || event.which > 57)) {
               event.preventDefault();
           }
       }
function getPropertyMasterDetails(){
	var errorList = [];
	var propNo = $("#assesmentPropId").val();
	if(propNo == null ||propNo == '' ){
		errorList.push(getLocalMessage("rnl.enter.property.number"));
		displayErrorsOnPageView(errorList);
		return false;
	}
	var requestData = {
		propNo : propNo
	}
	var response = __doAjaxRequest('EstatePropMas.html?getPropertyDetails', 'post',requestData, false, 'json');
	//data set base on property
	var property = response;
	if(property.name != null){
		$('#name').val(property.name);
		//$('#assesmentPropId').val(property.assesmentPropId);
		$('#usageId').val(property.usage);
		//$('#usageHiddeId').val(property.usage);
		$("#usageId").prop('disabled', 'disabled');
		$('select').trigger("chosen:updated");
		$("#name").prop('readonly', true);
	}else{ 
		$("#name").prop('readonly', false);
		$('#name').val('');
		$("#usageId").prop("disabled", false);
		$('#usageId').val('').trigger('chosen:updated');
		//$('#usageHiddeId').val('');
	}
}
