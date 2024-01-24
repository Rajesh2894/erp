/**
 * Harsha
 */

var today = new Date();
var fromYear = today.getFullYear();
$("#fromdate").datepicker({
	dateFormat: 'dd/mm/yy',
	changeDate : false,
	changeMonth: true,
    changeYear: true,
	yearRange: "-200:+200",
	defaultDate: '1/4/'+fromYear,
	maxDate : new Date(today.getFullYear()+1, 11, 31),
	stepMonths: 0,
	

	beforeShowDay: function (date) {
		
        if (date.getDate() == 1) {
            return [true, ''];
        }
        return [false, ''];
        }
});

var toYear = fromYear+1;


function daysInMonth(month,year) {
    return new Date(year, month, 0).getDate();
}

	
 $("#todate").focusin(function(event) {
	 	
		var  startDate  =$("#fromdate").datepicker('getDate');
		selectedYear = startDate.getFullYear();
		var toYearr = 0;
		var month = startDate.getMonth();
		var tomonth = month + 12;
		
		if(tomonth > 12){
			tomonth = tomonth - 12;
		}
		if(month < 2){
			toYearr = selectedYear;
		}else{
			toYearr = selectedYear+1;
		}
		var toDay = daysInMonth(tomonth,toYearr);
		
		if(tomonth.toString().length == 1)
		tomonth = "0" + tomonth;
		$('#todate').val(toDay+"/"+tomonth+"/"+toYearr);
	});
 

$(document).ready(function() {

	var mode = $('#mode').val();
	showMonthDiv();
	
	 if($('.checkboxall:checked').length == $('.checkboxall').length){
         $('#select_all').prop('checked',true);
     }else{
         $('#select_all').prop('checked',false);
     }
	
	$("#select_all").change(function () {
	    $("input:checkbox").prop('checked', $(this).prop("checked"));
	});
	
	 $('.checkboxall').on('click',function(){
	        if($('.checkboxall:checked').length == $('.checkboxall').length){
	            $('#select_all').prop('checked',true);
	        }else{
	            $('#select_all').prop('checked',false);
	        }
	    });

	
	if($('#modevalue').val() == "N"){
		$('#editOrViewRecord').find('*').attr('disabled','disabled').removeClass("mandClassColor"); 
		$('#submit').hide();
		$('#refreshData').hide();
	}

	 if($('#modevalue').val() == "Y"){
			$("#fromdate").datepicker("destroy");
		} 

});

function showMonthDiv(){

	
	var status = $('#status option:selected').attr("code");
	var statusVal = $('#status').val();

	if(status == 'OPN'){
		$('#monthstatus-change-div').show();
		$("#endMonth-lbl").addClass("required-control");
		$("#monthStatus-lbl").addClass("required-control");
		$('#startMonth-lbl').addClass("required-control");
		$('.required-control').next().children().addClass('mandColorClass');
	}else if(status == 'HCL' || statusVal == 0){
		$('#monthstatus-change-div').hide();
	}
}
function submitFinancialYearMaster(element)
{
	var errorList = [];
	
	var startMonth = $('#startMonth').val();
	var endMonth = $('#endMonth').val();
	var monthStatus = $('#monthStatus').val();
	
	
	var fromDt = $.trim($("#fromdate").val());
    if(fromDt==0 || fromDt=="")
    errorList.push(getLocalMessage('finyear.error.fromDate'));

	var toDt = $.trim($("#todate").val());
    if(toDt==0 || toDt=="")
    errorList.push(getLocalMessage('finyear.error.toDate'));
    
    if($('#modevalue').val() == "Y"){		
    	var status =  $.trim($("#status").val());
    	if(status==0)
        errorList.push(getLocalMessage('finyear.error.toDate'));
    }
    
    if($('#monthstatus-change-div').is(':visible')){
    	if(startMonth == ""){
    		errorList.push(getLocalMessage('finyear.error.startMonth'));
    	}
    	if(endMonth == ""){
    		errorList.push(getLocalMessage('finyear.error.endMonth'));
    	}
    	if(monthStatus == 0){
    		errorList.push(getLocalMessage('finyear.error.monthStatus'));
    	}
    }
    
    if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    	return false;
    		    	
    }else{
	 	var url ;
	 	var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var apndClass = '';
		
	 	var mapId = $('#mapId').val();
			 if($('#modevalue').val() == "Y"){
				 url='Financialyear.html?update';
				 apndClass = '.financialyeardiv';
			 }
			 if($('#modevalue').val() == "A"){
				 url='Financialyear.html?create';
				 apndClass = '.content';
			}
			
		var returnData=__doAjaxRequestForSave(url, 'post', requestData , false,'',element);
		if($.isPlainObject(returnData)) {
			showConfirmBox();
			return false;
		} else {				
			$(apndClass).html(returnData);
			$(apndClass).show();
		}
		return false;
    }
} 

function showConfirmBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = '';
	
		cls ='Proceed';
		message	+='<h4 class=\"text-info padding-10 padding-bottom-0 text-center\">Record Saved Successfully</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\'    '+ 
		' onclick="proceed()"/>'+	
		'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	 return false;
}

function proceed() {
	window.location.href='Financialyear.html';
}

$('#btnReset').click(function(){
	$('.error-div').html('');
	$('.alert-dismissible').html('');
	$('#todate').val('');
	$('#fromdate').val('');	
});



function selectMonth(){

	$("#endMonth").find('option').removeAttr("disabled");
	$("#endMonth").val("");
	$("#monthStatus").val(0);
	$("#endMonth-lbl").addClass("required-control");
	$("#monthStatus-lbl").addClass("required-control");
	$('#startMonth-lbl').addClass("required-control");
	
	
	var startMonth = parseInt($('#actualStartMonth').val());
	var selectedStartMonth = parseInt($('#startMonth').val());
		
	var endMonth = parseInt($('#actualEndMonth').val());
	
	if(selectedStartMonth < startMonth){
		
		for(var i = endMonth+1;i<=12;i++){
			if(i.toString().length == 1){
				$("#endMonth option[value=0"+i+"]").attr('disabled','disabled');
			}else{
				$("#endMonth option[value="+i+"]").attr('disabled', 'disabled');
			}
		}
		
		for(var i= 0;i<selectedStartMonth;i++){
			if(i.toString().length == 1){
				$("#endMonth option[value=0"+i+"]").attr('disabled','disabled');
			}else{
				$("#endMonth option[value="+i+"]").attr('disabled', 'disabled');
			}
		}
		
	}else if(selectedStartMonth > startMonth){
		
		for(var i= selectedStartMonth-1;i>=startMonth;i--){
			if(i.toString().length == 1){
				$("#endMonth option[value=0"+i+"]").attr('disabled','disabled');
			}else{
				$("#endMonth option[value="+i+"]").attr('disabled', 'disabled');
			}
		}	
	}else if(selectedStartMonth == startMonth){
		$("#endMonth").find('option').removeAttr("disabled");
	}
	
}


function resetFinYear(){
	$('.error-div').hide();
	showMonthDiv();
}