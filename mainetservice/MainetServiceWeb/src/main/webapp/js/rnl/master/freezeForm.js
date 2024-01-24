/**
 * ritesh.patil
 * 
 */
propFreezeUrl='PropFreeze.html';
$("#dateDiv").hide();
var unavailableDates=[];
$(document).ready(function(){
	 $('#locationId').chosen();
	 $('#estateId').chosen();
	 $('#propId').chosen();
	 $('#locationId').change(function(){
 		    var locationId=$("#locationId").val();
	 		var data = { "locId" : locationId };
	 		$('#estateId').html("");
	 		$('#estateId').append(
	 				$("<option></option>").attr("value", "").text(getLocalMessage('selectdropdown')))
	 		var ajaxResponse = __doAjaxRequest('EstateMaster.html?getEstate', 'POST', data, false, 'json');
	 		$.each(ajaxResponse, function(key, value) {
	 	        $('#estateId').append($("<option></option>").attr("value", key).text(value));
	           });
	 		$('select').trigger("chosen:updated");
      });
	 
	 
	 $('#estateId').change(function(){
			 var requestData = {"esId":$(this).val()};
			 var result=__doAjaxRequest("PropFreeze.html?propList",'post',requestData,false,'json');
			 $('#propId').html('');
			 $('#propId').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown')));
			 $.each(result, function(index, value) {
				 $('#propId').append($("<option></option>").attr("value",value[0]).text(value[1]));
			 });
			 $('#propId').trigger("chosen:updated");
	  });
	 
	
	
	 $('#propId').change(function(){
		     unavailableDates=[];
			 var result=__doAjaxRequest("PropFreeze.html?getVisibleDates",'post',{"propId":$(this).val()},false,'json');
			 for (var i = 0; i < result.length; i++) {
				   unavailableDates.push(result[i]);
			   }
			 $("#dateDiv").show();
			 //D#75602
			 getShiftList();
			
	 });
	 
	 $('.lessthancurrdateto').datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			/*yearRange: "-100:-0",*/
			numberOfMonths: 1,
		    minDate: 0,
		    maxDate: '+6m',
	        beforeShowDay: unavailable,
	        onSelect: checkBookedDate
		    
		});
		
		$('.lessthancurrdatefrom').datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			/*yearRange: "-100:-0",*/
			numberOfMonths: 1,
		    minDate: 0,
		    maxDate: '+6m',
	        beforeShowDay: unavailable,
	        onSelect: function(selected) {
				$(".lessthancurrdateto").datepicker("option","minDate", selected)	
					checkBookedDate();		
		      } 
		});
	 
});	

function unavailable(date) {
	
	propId=$('#propId').val();
	if(propId != "" || propId !='0' || propId != undefined ){
		    dmy = date.getDate() + "-" + (date.getMonth() + 1) + "-" + date.getFullYear();
			if ($.inArray(dmy, unavailableDates) == -1) {
		        return [true, ""];
		    } else {
		        return [false, "", "Unavailable"];
		    }
	 
        }
}


function checkBookedDate(toDate)
{
	
			var url = 'PropFreeze.html?dateRangBetBookedDate';
			var fromDate =  $("#fromDate").val();
			var toDate =  $("#toDate").val();
			var message='';
			var cls = 'Ok';
			if(toDate!='')
			{
				if(fromDate==null || fromDate==''){
					message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select From Date first</h4>'
					+'<div class=\'text-center padding-bottom-10\'>'+	
					'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
					' onclick="closeAlertForm()"/>'+
					'</div>';
					showAlertBox(message);	
				}	
				else{
					if(fromDate!=toDate)
					{
						var data = {
								"fromDate" :fromDate,
								"toDate":toDate,
								"propId":$('#propId').val()};	
						var result=__doAjaxRequest(url,'POST',data,false,'json');
						if(result=="fail"){
							 message	+='<h4 class=\"text-center text-blue-2 padding-10\">Not Allowed.Dates between From Date and To Date is already booked</h4>'
							            +'<div class=\'text-center padding-bottom-10\'>'+	
										'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
										' onclick="closeAlertForm()"/>'+
										'</div>';
							showAlertBox(message);			
						}
						else if(result=="pass"){
							getShiftList();
						}
					}
					else
					{
						getShiftList();	
					}
				}
			} 
}


function getShiftList()
{
	var fromDate =  $("#fromDate").val();
	var toDate =  $("#toDate").val();
	var message='';
	var cls = 'Ok';
	if(toDate!='' && fromDate!=''){
	var url = 'PropFreeze.html?getShiftsBasedOnDate';
	var data = {
		"fromDate" :fromDate,
		"toDate":toDate,
		"propId":$('#propId').val()
		};	
	var result=__doAjaxRequest(url,'POST',data,false,'json');	
	if(result==''){
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">Not Allowed.All Three Shits are bookrd for selected Date</h4>'
		 +'<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="closeAlertForm()"/>'+
		'</div>';
		showAlertBox(message);
		$('#toDate').val("");
	}
	else{
		$('#shiftId option').remove();
		//D#104569
		let shiftStatus=result[0].otherField;
		if(shiftStatus == 'GENERAL'){
			$('#shiftId option').remove();
			let errorList = [];
			errorList.push("Property is freezed for selected period, please select another period to proceed.");	
			showRLValidation(errorList);
		}else{
			$('#errorDivId').hide();
			var  optionsAsString="<option value='0'>Select</option>";
		 	for (var j = 0; j < result.length; j++){
				optionsAsString += "<option value='" +result[j].lookUpId+"'>" + result[j].lookUpDesc+"</option>";
	              }
			$('#shiftId').append(optionsAsString);
		}
		
		
		
			
	}
}
}


function showAlertBox(message){
	var	errMsgDiv = '.msg-dialog-box';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	 $('#toDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

$('#submitProp').click(function(){
	
	var errorList = [];
	 errorList = validateFreezeFormData(errorList);
	 if (errorList.length == 0) {
		    return saveOrUpdateForm($(this),"", propFreezeUrl,'saveform');
		} else {
		showRLValidation(errorList);
	}
	
});



/**
 * validate booking info
 * @param errorList
 * @returns
 */
function validateFreezeFormData(errorList){
	
	locId=$('#locationId').val();
	estateId=$('#estateId').val();
	propId= $('#propId').val();
	var fromDate= $('#fromDate').val();
	var toDate= $('#toDate').val();
	var shiftId= $('#shiftId').val();
	//var purpose= $.trim($('#purposeofBooking').val());
	var reasonOfFreezing = $.trim($('#reasonOfFreezing').val());
	
	if(locId == "" || locId =='0' || locId == undefined ){
		 errorList.push(getLocalMessage('estate.master.location.validate.msg'));
	 }
	if(estateId == "" || estateId =='0' || estateId == undefined ){
		 errorList.push(getLocalMessage('rnl.select.estate'));
	 }
	if(propId == "" || propId =='0' || propId == undefined ){
		 errorList.push(getLocalMessage('rnl.select.property'));
	 }
	if(propId != "" && propId !='0' && propId != undefined ){
		if(fromDate == "" || fromDate == undefined){
			 errorList.push(getLocalMessage('rnl.estate.from.date'));
		 }
		if(toDate == "" || toDate == undefined){
			 errorList.push(getLocalMessage('rnl.estate.to.date'));
		 }
	 }
	if(shiftId == "" || shiftId =='0' || shiftId == undefined ){
		 errorList.push(getLocalMessage('rnl.select.shift'));
	 }
	if(reasonOfFreezing == "" || reasonOfFreezing == undefined){
		 errorList.push(getLocalMessage('rnl.prop.freez.reason.of.freezing'));
	 }	
	return errorList;
}


function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    errMsg += '<ul>';
    $.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});
    errMsg += '</ul>';
    $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}

function closeAlertForm()
{
	disposeModalBox();
	$.fancybox.close();
}

function resetData(resetBtn){
	
	$('#locationId').val('').trigger('chosen:updated');
	$('#estateId').val('').trigger('chosen:updated');
	$('#propId').val('').trigger('chosen:updated');
	resetForm(resetBtn);
}
