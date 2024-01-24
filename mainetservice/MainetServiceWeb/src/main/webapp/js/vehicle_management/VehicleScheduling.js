$(document).ready(function() {
	
	var vesFromdt = $("#vesFromdt").val();
	var vesTodt = $("#vesTodt").val();
	
	$(".fromToDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate: vesFromdt,  
		maxDate:vesTodt
		
	});
	
	
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
		minDate: new Date(),  
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
	$("#postMethodForm").prop('action', 'vehicleScheduling.html');
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
	 
	    errorList = errorList.concat(ValidateVehicleScheduleForm(errorList));
		errorList = validateVehicleScheduleEntryDetails(errorList,mode);
		if(mode !== 'E'){
			 $("input:checkbox[name=day]:checked").each(function () {
				  weekDays.push($(this).val());
		         weekDays.join(", ");
		       $("#vesWeekday").val(weekDays);
		     });	

			 if(! $("#reccurance1").is(":checked")){
				  if(weekDays.length == 0){
					  errorList.push(getLocalMessage("vehicle.validation.days.selection")); 
				  }
			  }
		}

		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		}else{
		errorList = [];
		
	 var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var requestUrl = "vehicleScheduling.html?validSaveform";
		var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData,
				false, 'json');
		if (ajaxResponse != "") {			
			errorList.push(ajaxResponse);
		}
		}
	 
	 //commented because calling two ajax calls and will not work because checking for single enrty and we are having multiple entries
		/*var veId = $("#veid").val();
		var vesFromdt = $("#vesFromdt").val();
		var vesTodt = $("#vesTodt").val();
		var request="";
		
		var requestData = {
				"veId"        : veId,
				"vesFromdt"   : vesFromdt,
				"vesTodt"     : vesTodt
		}
		var URL = 'vehicleScheduling.html?recordAlreadyExistsCheck';
		var returnDataDateCheck = __doAjaxRequest(URL, 'POST', requestData, false, 'json'); 
		var checkDateFlag = returnDataDateCheck.flagMsg;
		
		$("#vehicleschedulingTbl tbody tr").each(function(i) {
			request="startime"+i+"="+$("#startime" + i).val() +",endtime"+i+"="+$("#endtime" + i).val()+"&"; 
		});
		var requestData = {
				"veId"        : veId,
				"vesFromdt"   : vesFromdt,
				"vesTodt"     : vesTodt,
				"request"     : request
		}
		var URL = 'vehicleScheduling.html?recordAlreadyExists';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
		if(returnData && checkDateFlag == null){
*/		
			
			if (errorList.length > 0) {
				$("#errorDiv").show();
				displayErrorsOnPage(errorList);
			} else {
			return  saveOrUpdateForm(element,getLocalMessage('vehicle.saveVehicleScheduling'),'vehicleScheduling.html', 'saveform');
				
			}
			deleteTableRow2(element);
		
		
			if(errorList.length > 0){
			displayErrorsOnPage(errorList);
			}
		
/*		
	 if(mode !== 'E'){
	 $("input:checkbox[name=day]:checked").each(function () {
		  weekDays.push($(this).val());
         weekDays.join(", ");
       $("#vesWeekday").val(weekDays);
     });	

	 if(! $("#reccurance1").is(":checked")){
		  if(weekDays.length == 0){
			  errorList.push(getLocalMessage("vehicle.validation.days.selection")); 
		  }
	  }
	}
	errorList = errorList.concat(ValidateVehicleScheduleForm(errorList));
	errorList = validateVehicleScheduleEntryDetails(errorList,mode);
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element,getLocalMessage('vehicle.saveVehicleScheduling'),'vehicleScheduling.html', 'saveform');
	}
deleteTableRow2(element);
*/
		return errorList;
}

function addEntryData(tableId) {
	
	var errorList = [];
	errorList = validateVehicleScheduleEntryDetails(errorList);
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		//addTableRow(tableId);
		addTableRow3(tableId);
	} else {
		displayErrorsOnPage(errorList);
	}
}
/*
function addEntryData() {

	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		addTableRow('frmEmployeeSchedulingTbl');
	} else {
		$('#frmEmployeeSchedulingTbl').DataTable();
		displayErrorsOnPage(errorList);
	}

}*/

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
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+ getLocalMessage('vehicle.delete') +'</h4>';
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
	var ajaxResponse = doAjaxLoading('vehicleScheduling.html?DeleteVehicleScheduling', requestData, 'html');
	 if (ajaxResponse == 'false') {
		 showErrormsgboxTitle(getLocalMessage('ERROR OCCURED'))
	 }
}

function deleteEntry(tableId, obj, ids) {
	deleteTableRow(tableId, obj, ids);
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
	var reccurance4 = $("#reccurance4").val();
	var x = document.getElementById("reccurance1").checked;
	var y = document.getElementById("reccurance2").checked;
	var z=document.getElementById("reccurance3").checked;
	var zyear=document.getElementById("reccurance4").checked;
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	if (veVetype == "0" || veVetype == null)
		errorList.push( getLocalMessage("vehicle.validation.veVetype"));
	if (veId == "" || veId == null || veId=="0")
		errorList.push(getLocalMessage("vehicle.validation.vehicleno"));
	if (vesFromdt == "" || vesFromdt == null)
		errorList.push(getLocalMessage("vehicle.validation.vesFromdt"));
	if (vesTodt == "" || vesTodt == null)
		errorList.push(getLocalMessage("vehicle.validation.vesTodt"));
	if(x==false && y==false && z==false && zyear == false){
		errorList.push(getLocalMessage("vehicle.validation.vesReocc"));
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
				$(this).find($('[id^="department"]')).attr('id',"department"+i+"_chosen");
				$(this).find($('[id^="occEmpName"]')).attr('id',"occEmpName"+i+"_chosen");
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				/*$(this).find("select:eq(0)").attr("id", "beatId" +i);*/
			//	$(this).find("select:eq(0)").attr("id", "sorChapter"+ i).attr("onchange","getAllItemsList("+i+");");
				$(this).find("select:eq(0)").attr("id", "department" + i).attr("onchange","getAllItemsList("+i+");");
				$(this).find("select:eq(1)").attr("id", "occEmpName" + i);
				emptyIfDeptisNull(i);
				$(this).find("select:eq(2)").attr("id", "empId" + i);
				$(this).find("select:eq(3)").attr("id", "cpdShiftId" + i);
			/*	$(this).find("select:eq(2)").attr("id", "vesCollType" + i);*/
				$(this).find("input:text:eq(1)").attr("id", "startime" + i);
				$(this).find("input:text:eq(2)").attr("id", "endtime" + i);
				
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				/*$(this).find("select:eq(0)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].beatId");*/
				$(this).find("select:eq(0)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].department");
				$(this).find("select:eq(1)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].occEmpName");
				$(this).find("select:eq(2)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].empId");
				$(this).find("select:eq(3)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].cpdShiftId");
				/*$(this).find("select:eq(2)").attr("name","vehicleScheduleDto.tbSwVehicleScheddets[" + i + "].vesCollType");*/
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
							var department = $("#department" + i).val();
							var empId = $("#empId" + i).val();
							/*var vesCollType = $("#vesCollType" + i).val();*/
							var startime = $("#startime" + i).val();
							var endtime = $("#endtime" + i).val();
							var level = 1;
						} else {
						    var department = $("#department" + i).val();
							var empId = $("#empId" + i).val();
						/*	var vesCollType = $("#vesCollType" + i).val();*/
							var startime = $("#startime" + i).val();
							var endtime = $("#endtime" + i).val();
							var level = i + 1;
						}
					    if (department == '' || department == undefined || department == '0') {
							errorList.push(getLocalMessage('Department must be selected')+ " " + level);
						}
						if (empId == '' || empId == undefined || empId == '0') {
							errorList.push(getLocalMessage('Employee Name must be selected')+ " " + level);
						}
						/*if (vesCollType == '' || vesCollType == undefined || vesCollType == '0') {
							errorList.push(getLocalMessage('Waste Generated Type must be selected')+ " " + level);
						}*/
						/*if (startime == '' || startime == undefined || startime == '0') {
							errorList.push(getLocalMessage('Star Time must be selected')+ " " + level);
						}
						if (endtime == '' || endtime == undefined || endtime == '0') {
							errorList.push(getLocalMessage('Star Time must be selected')+ " " + level);
						}*/
						/*if(endtime < startime){
							errorList.push(getLocalMessage("vehicle.vehiclescheduling.validation.timevalidation"));
						}*/
					});
	return errorList;
}
function validateVehicleScheduleEntryDetails(errorList,mode) {
	
	var j = 0;
	if ($.fn.DataTable.isDataTable('#vehicleschedulingTbl')) {
		$('#vehicleschedulingTbl').DataTable().destroy();
	}
	$("#vehicleschedulingTbl tbody tr").each(function(i) {	
		
						if(mode == 'E'){
							var department = $("#department"+i).val();
						}else{
						var department = $("#department"+i).find("option:selected").attr('value');
						}
						//var occEmpName = $("#occEmpName" + i).find("option:selected").attr('value');
						
						var occEmpName = [];
						$("#occEmpName"+i).find("option:selected").each(function () {					
                            var $this = $(this);
                                     if ($this.length) {
                                             var selText = $this.val();
                                             occEmpName.push($(this).val());
                                        }
                                      });
									if(occEmpName.length > 0){
										occEmpName =  occEmpName.join(', ') + '';
									  }
									  //return labels.join(', ') + '';
									
						//var empId = $("#empId" + i).find("option:selected").attr('value');
						var empId = [];
						$("#empId"+i).find("option:selected").each(function () {
                            var $this = $(this);
                                     if ($this.length) {
                                             var selText = $this.val();
                                          empId.push($(this).val());
                                        }
                                      });
									if(empId.length > 0){
										empId =  empId.join(', ') + '';
									  }
						var sheduleDate = $("sheduleDate" + i).val();
						var cpdShiftId = $("#cpdShiftId" + i).find("option:selected").attr('value');
						var startime = $("#startime" + i).val();
						var cpdShiftId2 = $("#cpdShiftId" + i).val();
						var endtime = $("#endtime" + i).val();
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						
						if (department == "0" || department == null) {
							errorList.push(getLocalMessage("vehicle.validation.routeId")+ rowCount);
						}
						/*if (vesCollType == "0" || vesCollType == '') {
							errorList.push(getLocalMessage("vehicle.validation.vesCollType")+ rowCount);
						}*/
						if (startime == "" || startime == null) {
							errorList.push(getLocalMessage("vehicle.validation.startime")+ rowCount);
						}
						if (endtime == "" || endtime == null) {
							errorList.push( getLocalMessage("vehicle.validation.endtime")+ rowCount);
						}
						if (cpdShiftId2 == "0" || cpdShiftId2 == null) {
							errorList.push( getLocalMessage("vehicle.validation.shift")+ rowCount);
						}
						/*if(endtime < startime){
							errorList.push(getLocalMessage("vehicle.vehiclescheduling.validation.timevalidation"));
						}*/
						if(mode=='E'){
							for(j=0; j<i; j++){
								if($('#cpdShiftId'+j).val() == $('#cpdShiftId'+i).val()){
									if($('#sheduleDate'+j).val() == $('#sheduleDate'+i).val()){
										if($('#startime'+i).val() <= $('#startime'+j).val() && $('#startime'+j).val() <=$('#endtime'+i).val()){
											errorList.push(("Shift,Schedule date and time already exists!") + " " + rowCount);
										}
										else if($('#endtime'+j).val()>=$('#startime'+i).val() && $('#endtime'+i).val()>=$('#endtime'+j).val()){
											errorList.push(("Shift,Schedule date and time already exists!") + " " + rowCount);
											}
										else if($('#startime'+i).val() >= $('#startime'+j).val() && $('#startime'+i).val() <=$('#endtime'+j).val()){
											errorList.push(("Shift,Schedule date and time already exists!") + " " + rowCount);
										}
										else if($('#endtime'+i).val()>=$('#startime'+j).val() && $('#endtime'+j).val()>=$('#endtime'+i).val()){
											errorList.push(("Shift,Schedule date and time already exists!") + " " + rowCount);
											}
									}
								}
							}
						}
						if(mode=="C"){
						for(j=0; j<i;j++){
							if($('#department'+j).val() == $('#department'+i).val()){
								if($('#occEmpName'+j).val() == $('#occEmpName'+i).val()){
									errorList.push("The Collection Type already exists!");
								}
							}
								if($('#startime'+j).val() == $('#startime'+i).val()){
									if($('#endtime'+j).val() == $('#endtime'+i).val()){
										errorList.push("The InTime and OutTime  already exists!");
									}
								}
								
								if($('#startime'+i).val() <= $('#startime'+j).val() && $('#startime'+j).val() <=$('#endtime'+i).val()){
									errorList.push("Time already exists! " + rowCount);
								}
								else if($('#endtime'+j).val()>=$('#startime'+i).val() && $('#endtime'+i).val()>=$('#endtime'+j).val())
									{
									errorList.push("Time already exists! " + rowCount);
									}
								else if($('#startime'+i).val() >= $('#startime'+j).val() && $('#startime'+i).val() <=$('#endtime'+j).val()){
                                	errorList.push("Time already exists! " + rowCount);
								}
								else if($('#endtime'+i).val()>=$('#startime'+j).val() && $('#endtime'+j).val()>=$('#endtime'+i).val())
									{
									errorList.push("Time already exists! " + rowCount);
									}
								/*if($('#endtime'+j).val() == $('#endtime'+i).val()){
									errorList.push("The OutTime  already exists!");
								}*/
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
	
	var content = $(id + ' tr').last().clone();
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('');
	content.find("span").remove();
	content.find("li.search-choice").remove();
	//content.removeClass("search-choice-close");
	//content.find("ul.chosen-choices").remove();
	//content.find("a").remove();
	content.find("ul").remove();
	//content.find("ul").add();
	
	content.removeClass("search-choice-close");
	content.find("input:checkbox").removeAttr('checked');
    content.appendTo('tbody');
	reOrderUnitTabIdSequence('.firstUnitRow');
 	$(function() {
		$('.datetimepicker3').timepicker();
	});
	dataTableProperty(id);	
}


function addTableRow4(tableId) {
	var id = "#" + tableId;
		if ($.fn.DataTable.isDataTable('' + id + '')) {
			$('' + id + '').DataTable().destroy();
		}
		$(".datetimepicker3").timepicker("destroy");
		
		var content = $(id + ' tr').last().clone();
		content.find("input:text").val('');
		content.find("input:hidden").val('');
		content.find("textarea").val('');
		content.find("select").val('');
		content.find("input:checkbox").removeAttr('checked');
		content.appendTo('tbody');
		reOrderUnitTabIdSequence('.firstUnitRow');
	 	$(function() {
			$('.datetimepicker3').timepicker();
		});
		dataTableProperty(id);	
	}

function showVehicleRegNo() {
	
	$('#veid').html('');
	var crtElementId = $("#veVetype").val();
	var requestUrl = "vehicleScheduling.html?vehicleNo";
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


function getEmployeeByDept1() {
	
    //$('#occEmpName0').html('');
	var department = $("#department").val();
	var requestUrl ="vehicleScheduling.html?getEmployeeByDept1";
	var requestData = {
		"department":department
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	 $('#occEmpName').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
	$('#occEmpName').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#occEmpName').trigger('chosen:updated');	

}
function emptyIfDeptisNull(index){

	var dept = $('#department' + index).val();
	var department = $("#department"+index).find("option:selected").attr('value');
	if(dept == 0 && department == 0 ){
		
		 $('#occEmpName' + index).empty();
		
	}
}
function getAllItemsList(index){
	
	$('#occEmpName' + index).html('');
	/*$('#occEmpName' + index).append($("<option></option>").attr("value", "").text(getLocalMessage('selectdropdown'))).trigger(
	'chosen:updated');*/
	 $('#occEmpName' + index).trigger('chosen:updated');
	var requestData = {
		"department" : $('#department' + index).val()
	}
	var sorItemsList = __doAjaxRequest("vehicleScheduling.html" + '?getEmployeeByDept1',
			'POST', requestData, false, 'json');
	/* $('#occEmpName' + index).append($("<option></option>").attr("value",0).attr("code",0).text("select"));*/
	 $.each(sorItemsList, function(i, value) {
			$('#occEmpName' + index).append($("<option></option>").attr("value",i).text(value));
			});
	      $('#occEmpName' + index).trigger('chosen:updated');

}

function getEmployeeByDept(obj) {	
	$('#employee').html('');
	$('#employee').append($("<option></option>").attr("value", "0").text(getLocalMessage('selectdropdown')));
	var url = "ComplainRegister.html?getEmployeeByDept";
	var deptId;
	if(obj==0){
		deptId=$('#department').val();
	}
	else{
	 deptId=$('#department'+obj).val();
	}
	var requestData = "deptId=" + deptId;
	var response = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	$('#employee'+obj).text('');
	$('#employee'+obj).val('');
	$.each(response, function(key, value) {
	 if(obj==0){
		$('#employee').append($('<option>', {value:value.empId, text:value.empname}));
		$('#employee').trigger("chosen:updated");
	 }
	 else{
		$('#employee'+obj).append($('<option>', {value:value.empId, text:value.empname}));
		$("#employee"+obj).trigger("chosen:updated");
	 }
	});
}	

function getEmployeeByDept2(index) {
	
    //$('#occEmpName0').html('');
	var department = $("#department0").val();
	$('#occEmpName' + index).append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown'))).trigger(
			'chosen:updated');
	var requestUrl ="vehicleScheduling.html?getEmployeeByDept1";
	var requestData = {
		"department":department
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	 $('#occEmpName0').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
	$('#occEmpName0').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#occEmpName0').trigger('chosen:updated');	

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
	var ajaxResponse = doAjaxLoading('vehicleScheduling.html?'+'DeleteVehicleScheduling', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function runCalendar() {
	

	var errorList = [];
	var veVetype = $("#veVetype").val();
	var veid=$("#veid").val();
	var veDriverName=$("#veDriverName").val();
	if(veVetype=="0"){
		veVetype="";
	}
	if(veid=="0"){
		veid="";
	}
	if(veDriverName=="0"){
		veDriverName="";
	}
	var data = {
		"veVetype" : veVetype,
		"veid"     : veid,
		"veDriverName" : veDriverName
	};
	var ajaxResponse = __doAjaxRequest('vehicleScheduling.html' + '?getCalData','POST', data, false,'json');
	$('#calendar').fullCalendar('destroy');
	if (ajaxResponse.length== 0) {
		errorList.push(getLocalMessage('Vehicle Scheduled Not Available.'));
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
																allDay: false,
																veNo:ajaxResponse.veNo
															});
												});
						callback(events);
							},
						}, ],
						eventColor: '#333333',
               // an option!
						timeFormat: 'hh:mm',
						 eventClick: function(calEvent, jsEvent, view) {
							 
	                            var ajaxResponse = __doAjaxRequest('vehicleScheduling.html?SearchVehicleScheduling', 'POST', {"veId":calEvent.id}, false,'html');
								$('.content').html(ajaxResponse);
                            }
						    
         });
}

function searchVeNo() {
    var requestData = {
	"veid" : $("#veid").val()
    };
    
    $('#veid').html('');
    $('#veid').append(
	    $("<option></option>").attr("value", "0").text(
		    getLocalMessage('selectdropdown')));

    var ajaxResponse = doAjaxLoading(
	    'vehicleScheduling.html?searchVeNo', requestData,
	    'json');
   
	var veNodata='<option value="'+ajaxResponse.veId+'">'+ajaxResponse.veNo+'</option>'
	$("#veid").html(veNodata);
    
}

function showConfirmBoxForMultiDelete(e,mode){
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+ getLocalMessage('vehicle.delete') +'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForMultiDelete()"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
	
}
$('#deleteAll').change(function(e){
	$("#hidenDel").val('BulkDelete');
	var deletVal="BulkDelete";
	var theForm = '#vehicleSchedulingForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var ajaxResponse = doAjaxLoading(
		    'vehicleScheduling.html?deleteSave', requestData,
		    'json');
	var mode="E";
	var requestData1={
	"id" : $("#id").val(),
	"mode"     : mode,
    };
	var divName = '.content-page';
	var ajaxResponse1 = doAjaxLoading(
		    'vehicleScheduling.html?EditVehicleScheduling', requestData1,
		    'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse1);
	//return saveOrUpdateForm(this,getLocalMessage('vehicle.saveVehicleScheduling'),'vehicleScheduling.html', 'saveform');
	
});

function proceedForMultiDelete(){
	$('.fancybox-close').click();
	$('#deleteAll').change();
}