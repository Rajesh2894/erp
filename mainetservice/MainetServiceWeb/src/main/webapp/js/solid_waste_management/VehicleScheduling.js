$(document).ready(function() {
	$('#vehicleSchedulingForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	$("#id_vehicleScheduling").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering":  false,
	    "order": [[ 1, "desc" ]]
	});

	$(function() {
		$('.datetimepicker3').timepicker();
		
	});
	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});
	runCalendar();
});
function Daily() {
	$(".hidebox").not(".yes").hide();
	$(".yes").hide();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		$('.days').attr("checked", false);
	});
}
function Weekly() {
	
	$(".hidebox").not(".yes").hide();
	$(".yes").show();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		weekDays.push($(this).val());
		weekDays.join(", ");
		$("#weekdays").val(weekDays);
	});
	if (weekDays != "") {
		$('.days').attr("checked", false);
	}
}
function Monthly() {
	
	$(".hidebox").not(".yes").hide();
	$(".yes").show();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		weekDays.push($(this).val());
		weekDays.join(", ");
		$("#weekdays").val(weekDays);
	});
	if (weekDays != "") {
		$('.days').attr("checked", false);
	}
}
function Yearly() {
	
	$(".hidebox").not(".yes").hide();
	$(".yes").show();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		weekDays.push($(this).val());
		weekDays.join(", ");
		$("#weekdays").val(weekDays);
	});
	if (weekDays != "") {
		$('.days').attr("checked", false);
	}
}
$('input[type="checkbox"]').click(
		function() {
			var errorList = [];
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var fDate = new Date($("#emsFromdate").val().replace(pattern,'$3-$2-$1'));
			var tDate = new Date($("#emsTodate").val().replace(pattern,'$3-$2-$1'));
			var getDateArray = function(fDate, tDate) {
				var arr = new Array();
				var dt = new Date(fDate);
				while (fDate <= tDate) {
					var s1 = fDate.getDay();
					arr.push(s1);
					fDate.setDate(fDate.getDate() + 1);
				}
				return arr;
			}
		});
function searchVehicleScheduling() {
	var errorList = [];
	var veVetype = $("#veVetype").val();
	
	if(veVetype=="0"){
		errorList.push(getLocalMessage('swm.validation.vehicletype'));
		
	}
	
	if(errorList.length>0){
		displayErrorsPage(errorList);
	}
	runCalendar();	
}
function replaceZero(value){
	return value != 0 ? value : "";
}
function replaceUndefine(value){
	return value != 0 ? value : "";
}
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}
function resetVehicleScheduling() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CollectionScheduling.html');
	$("#postMethodForm").submit();	
	$('.error-div').hide();
}
function openAddVehicleScheduling(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	reOrderUnitTabIdSequence('.firstUnitRow');
}

function backBtn(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function Proceed(element,mode) {
	
	 var weekDays = [];
	 var errorList = [];
	if(mode !== 'E'){
		 $("input:checkbox[name=day]:checked").each(function () {
			  weekDays.push($(this).val());
	         weekDays.join(", ");
	       $("#vesWeekday").val(weekDays);
	     });	
	
	 
	  if(! $("#reccurance1").is(":checked")){
		  if(weekDays.length == 0){
			  errorList.push(getLocalMessage("swm.validation.days.selection")); 
		  }
	  }
	}
	errorList =errorList.concat(ValidateVehicleScheduleForm(errorList));
	
	errorList = validateVehicleScheduleEntryDetails(errorList,mode);
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element,getLocalMessage('swm.saveVehicleScheduling'),'CollectionScheduling.html', 'saveform');
	}
deleteTableRow2(element);
}

function addEntryData(tableId) {
	
	var errorList = [];
	errorList = validateVehicleScheduleEntryDetails(errorList);
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow3(tableId);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function modifyVehicleScheduling(ID, formUrl, actionParam,mode) {
	
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : ID
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function showConfirmBoxForDelete(ID){ 
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+ getLocalMessage('swm.delete') +'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + ID +')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(ID){
	$.fancybox.close();
	var requestData = {
			"mode":"D",
			"id":ID
	};
	var ajaxResponse = doAjaxLoading('CollectionScheduling.html?DeleteVehicleScheduling', requestData, 'html');
	 if (ajaxResponse == 'false') {
		 showErrormsgboxTitle(getLocalMessage('swm.error.occur'))
	 }
}

function deleteEntry(tableId, obj, ids) {
	
	let errorList = [];
	var rowCount = $('#vehicleschedulingTbl tbody tr').length;
	rowCount = rowCount - 1;
    if(rowCount < 1){
        errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
    }   
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}else{
		deleteTableRow(tableId, obj, ids);
		reOrderUnitTabIdSequence('.firstUnitRow');
	}
}

function validateVehicleEntryDetails() {
	var errorList = []; 
	var j = 0;
	if ($.fn.DataTable.isDataTable('#vehicleschedulingTbl')) {
		$('#vehicleschedulingTbl').DataTable().destroy();
	}
	return errorList;
}
function ValidateVehicleScheduleForm() {
	
	var errorList = [];
	var veVetype = $("#veVetype").val();
	var veId = $("#veid").val();
	var vesFromdt = $("#vesFromdt").val();
	var vesTodt = $("#vesTodt").val();
	var reccurance1 = $("#reccurance1").val();
	var reccurance2 = $("#reccurance2").val();
	var reccurance3 = $("#reccurance3").val();
	var x = document.getElementById("reccurance1").checked;
	var y = document.getElementById("reccurance2").checked;
	var z=document.getElementById("reccurance3").checked;
	var zyear=document.getElementById("reccurance4").checked;
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	if (veVetype == "0" || veVetype == null)
		errorList.push( getLocalMessage("swm.validation.veVetype"));
	if (veId == "" || veId == null || veId=="0")
		errorList.push(getLocalMessage("swm.validation.ScheduledVeId"));
	if (vesFromdt == "" || vesFromdt == null)
		errorList.push(getLocalMessage("swm.validation.vesFromdt"));
	if (vesTodt == "" || vesTodt == null)
		errorList.push(getLocalMessage("swm.validation.vesTodt"));
	if(x==false && y==false && z==false && zyear == false){
		errorList.push(getLocalMessage("swm.validation.vesReocc"));
	}
	return errorList;
}
$(function() {
	/* To add new Row into table */
	$("#vehicleschedulingTbl").on('click', '.addCF', function() {
		var errorList = [];
		errorList = validateUnitDetailTable(errorList);
		if (errorList.length == 0) {
			var content = $("#vehicleschedulingTbl").find('tr:eq(1)').clone();
			$("#vehicleschedulingTbl").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val('0');
			$('.error-div').hide();
			reOrderUnitTabIdSequence('.firstUnitRow'); // reorder id and Path
			// Addition of new Row
		} else {
			displayErrorsPage(errorList);
		}
	});
});


function reOrderUnitTabIdSequence(firstRow) {
	var j;
	$(firstRow).each(
			function(i) {
				
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "beatId" +i);
				$(this).find("select:eq(1)").attr("id", "empId" + i);
				$(this).find("select:eq(2)").attr("id", "vesCollType" + i);
				$(this).find("input:text:eq(1)").attr("id", "startime" + i);
				$(this).find("input:text:eq(2)").attr("id", "endtime" + i);
				
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("select:eq(0)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].beatId");
				$(this).find("select:eq(1)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].empId");
				$(this).find("select:eq(2)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].vesCollType");
				$(this).find("input:text:eq(1)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].startime");
				$(this).find("input:text:eq(2)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].endtime");
				j=i;
			});
	/*$("#empId"+j).append('#vehicleschedulingTbl').multiselect("destroy").multiselect();*/
}
function validateUnitDetailTable(errorList) {
	var rowCount = $('#vehicleschedulingTbl tr').length;
	$('.firstUnitRow')
			.each(function(i) {
						
						if (rowCount <= 1) {
							var beatId = $("#beatId" + i).val();
							var empId = $("#empId" + i).val();
							var vesCollType = $("#vesCollType" + i).val();
							var startime = $("#startime" + i).val();
							var endtime = $("#endtime" + i).val();
							var level = 1;
						} else {
							var beatId = $("#beatId" + i).val();
							var empId = $("#empId" + i).val();
							var vesCollType = $("#vesCollType" + i).val();
							var startime = $("#startime" + i).val();
							var endtime = $("#endtime" + i).val();
							var level = i + 1;
						}
						if (beatId == '' || beatId == undefined || beatId == '0') {
							errorList.push(getLocalMessage('swm.select.beatNo')+ " " + level);
						}
						if (empId == '' || empId == undefined || empId == '0') {
							errorList.push(getLocalMessage('swm.empName.select')+ " " + level);
						}
						if (vesCollType == '' || vesCollType == undefined || vesCollType == '0') {
							errorList.push(getLocalMessage('collection.validation.collectiontype')+ " " + level);
						}
						if (startime == '' || startime == undefined || startime == '0') {
							errorList.push(getLocalMessage('swm.start.time.select')+ " " + level);
						}
						if (endtime == '' || endtime == undefined || endtime == '0') {
							errorList.push(getLocalMessage('swm.start.time.select')+ " " + level);
						}
						if(endtime < startime){
							errorList.push(getLocalMessage("swm.vehiclescheduling.validation.timevalidation"));
						}
					});
	return errorList;
}
function validateVehicleScheduleEntryDetails(errorList,mode) {
	var j = 0;
	if ($.fn.DataTable.isDataTable('#vehicleschedulingTbl')) {
		$('#vehicleschedulingTbl').DataTable().destroy();
	}
	$("#vehicleschedulingTbl tbody tr").each(function(i) {
						
						var beatId = $("#beatId"+i).find("option:selected").attr('value');
						var vesCollType = $("#vesCollType" + i).find("option:selected").attr('value');
						var startime = $("#startime" + i).val();
						var endtime = $("#endtime" + i).val();
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (beatId == "0" || beatId == null) {
							errorList.push(getLocalMessage("swm.validation.routeId")+ rowCount);
						}
						if (vesCollType == "0" || vesCollType == '') {
							errorList.push(getLocalMessage("swm.validation.vesCollType")+ rowCount);
						}
						if (startime == "" || startime == null) {
							errorList.push(getLocalMessage("swm.validation.startime")+ rowCount);
						}
						if (endtime == "" || endtime == null) {
							errorList.push( getLocalMessage("swm.validation.endtime")+ rowCount);
						}
						if(endtime < startime){
							errorList.push(getLocalMessage("swm.vehiclescheduling.validation.timevalidation"));
						}
						if(mode=="C"){
						for(j=0; j<i;j++){
							if($('#beatId'+j).val() == $('#beatId'+i).val()){
								if($('#vesCollType'+j).val() == $('#vesCollType'+i).val()){
									errorList.push("The Collection Type  already exists!");
								}
								if($('#startime'+j).val() == $('#startime'+i).val()){
									errorList.push("The InTime  already exists!");
								}
								if($('#endtime'+j).val() == $('#endtime'+i).val()){
									errorList.push("The OutTime  already exists!");
								}
							}
						}
						}
					});
	return errorList;
}


function addTableRow3(tableId) {
var id = "#" + tableId;
	if ($.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	$(".datetimepicker3").timepicker("destroy");
	
	var content = $("#vehicleschedulingTbl").find('tr:eq(1)').clone();
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('0');
	content.find("input:checkbox").removeAttr('checked');
	content.find('.multiselect').remove();
	content.appendTo('tbody');
	$(".multiselect-ui").multiselect({
		buttonText : function(options, select) {
			if (options.length === 0) {
				return 'None selected';
			}
			if (options.length === select[0].length) {
				return 'All selected (' + select[0].length + ')';
			} else if (options.length >= 1) {
				return options.length + ' selected';
			} else {
				var labels = [];
				console.log(options);
				options.each(function() {
					labels.push($(this).val());
				});
				return labels.join(', ') + '';
			}
		}
	});
	reOrderUnitTabIdSequence('.firstUnitRow');
 	$(function() {
		$('.datetimepicker3').timepicker();
	});
	dataTableProperty(id);
}

function showVehicleRegNo() {
	
	$('#veid').html('');
	var crtElementId = $("#veVetype").val();
	var requestUrl = "CollectionScheduling.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	 $('#veid').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
	$('#veid').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#veid').trigger('chosen:updated');	

}
/** ******************DeleteFunction*********** */
function deletevehicleScheduling(vesId, formUrl, actionParam) {
	if (actionParam == "DeleteVehicleScheduling") {
		showConfirmBoxForDelete(vesId, actionParam);
	}
}
function showConfirmBoxForDelete(vesId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + vesId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function proceedForDelete(vesId) {
	$.fancybox.close();
	var requestData = 'vesId=' + vesId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CollectionScheduling.html?'+'DeleteVehicleScheduling', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function runCalendar() {
	var errorList = [];
	var veVetype = $("#veVetype").val();
	var veid=$("#veid").val();
	if(veVetype=="0"){
		veVetype="";
		
	}
	if(veid=="0"){
		veid="";
	}
	var data = {
		"veVetype" : veVetype,
		"veid"     : veid
	};
	var ajaxResponse = __doAjaxRequest('CollectionScheduling.html' + '?getCalData','POST', data, false,'json');
	$('#calendar').fullCalendar('destroy');
	if (ajaxResponse.length== 0) {
		errorList.push(getLocalMessage('swm.vehicle.schedule.not.available'));
		displayErrorsPage(errorList);
	}
	console.log('-----------'+ JSON.stringify(ajaxResponse));
    var calendar = $('#calendar').fullCalendar({
    	header:  
        {  
            left: 'prev,next today',  
            center: 'title',  
            right: 'month,agendaWeek,agendaDay'  
        },  
        buttonText: {  
            today: getLocalMessage("vehicle.today"),  
            month: getLocalMessage("vehicle.month"),  
            week: getLocalMessage("vehicle.week"),  
            day: getLocalMessage("vehicle.day")  
        },
        monthNames: [
        	getLocalMessage("vehicle.month.january"),
        	getLocalMessage("vehicle.month.february"),
        	getLocalMessage("vehicle.month.march"),
        	getLocalMessage("vehicle.month.april"),
        	getLocalMessage("vehicle.month.may"),
        	getLocalMessage("vehicle.month.june"),
        	getLocalMessage("vehicle.month.july"),
        	getLocalMessage("vehicle.month.august"),
        	getLocalMessage("vehicle.month.september"),
        	getLocalMessage("vehicle.month.october"),
        	getLocalMessage("vehicle.month.november"),
        	getLocalMessage("vehicle.month.december")
        ],
        dayNamesShort: [
        	getLocalMessage("vehicle.weekday.sun"), 
        	getLocalMessage("vehicle.weekday.mon"),
        	getLocalMessage("vehicle.weekday.tue"),
        	getLocalMessage("vehicle.weekday.wed"),
        	getLocalMessage("vehicle.weekday.thu"),
        	getLocalMessage("vehicle.weekday.fri"),
        	getLocalMessage("vehicle.weekday.sat")
        ],
        eventSources : [
						{
							    events : function(start, end,callback) {
							    	
								var events = [];
								$(ajaxResponse).each(function(index) {
													events.push({
														        
																title : $(this).attr('title'),
																start :new Date( $(this).attr('start')),
																end : new Date($(this).attr('end')),
																className : $(this).attr('className'),
																id : $(this).attr('id'),
																allDay: false
															});
												});
						callback(events);
							},
						}, ],
						eventColor: '#333333',
               // an option!
						timeFormat: 'hh:mm',
						 eventClick: function(calEvent, jsEvent, view) {
							 
	                            var ajaxResponse = __doAjaxRequest('CollectionScheduling.html?SearchVehicleScheduling', 'POST', {"veId":calEvent.id}, false,'html');
								$('.content').html(ajaxResponse);
                            }
						    
                      });
}



