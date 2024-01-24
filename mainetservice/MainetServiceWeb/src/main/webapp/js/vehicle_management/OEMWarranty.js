$(document).ready(function() {
	
	$(".purdatepicker").datepicker({                        
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd/mm/yy',   
        maxDate : new Date()
    });
	
	prepareDateTag();
	$("#oemWarrantyTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
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
	
	
/*	$("#oemWarrantyTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		//"ordering":  false,
	    "order": [[ 1, "desc" ]]
	});*/
	$('#OEMWarrantyForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});


	$(function() {
		$('.datetimepicker3').timepicker();
		
	});
	
	
	$('#purchaseDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});
	/*$('#vehicleschedulingTbl').each(function(i) {
		var datePurchase=$('#purchaseDate'+i).val();
		if(datePurchase!=null && datePurchase!=undefined && datePurchase!=""){
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date(datePurchase.replace(pattern, '$3-$2-$1'));
			var day = ("0" + (date.getDate())).slice(-2);
			var month = ("0" + (date.getMonth() + 1)).slice(-2);
			var dateString = day + '/' + month + '/'
					+ date.getFullYear();
			$('#purchaseDate'+i).val(dateString);
		}
		
		var lastDateOfWarranty=$('#lastDateOfWarranty'+i).val();
		if(lastDateOfWarranty!=null && lastDateOfWarranty!=undefined && lastDateOfWarranty!=""){
			//var date1=new Date(lastDateOfWarranty);
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date1 = new Date(lastDateOfWarranty.replace(pattern, '$3-$2-$1'));
			var day = ("0" + (date1.getDate())).slice(-2);
			var month = ("0" + (date1.getMonth() + 1)).slice(-2);
			var dateString1 = day + '/' + month + '/'
					+ date1.getFullYear();
			$('#lastDateOfWarranty'+i).val(dateString1);
		}
		
	});*/
	
	$("#purchaseDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$('#lastDateOfWarranty').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});
	$("#lastDateOfWarranty").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
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
	//runCalendar();
});


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
function resetVehicleOEM() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'OEMWarranty.html');
	$("#postMethodForm").submit();	
	$('.error-div').hide();
}



function openAddOEMWarranty(formUrl, actionParam) {

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

	 var errorList = [];
/*	if(mode !== 'E'){
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
	}*/
    errorList =errorList.concat(ValidateVehicleScheduleForm(errorList));
	
	errorList = validateVehicleScheduleEntryDetails(errorList,mode);
	
	//validation for duplicate record
	errorList = validateUnitDetailTable(errorList);
	
	errorList = validatePurchaseDate(element,errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$( ".wardatepicker" ).prop( "disabled", false );
		return saveOrUpdateForm(element,getLocalMessage('swm.saveVehicleScheduling'),'OEMWarranty.html', 'saveform');
	}
deleteTableRow2(element);
}

function validatePurchaseDate(element,errorList){

		var requestData = {};
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		requestData = __serializeForm(theForm);
		var URL = 'OEMWarranty.html?checkValidInsuranceDate';
		var retData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
		if (retData==false) {
			errorList
					.push(getLocalMessage("oem.warranty.validation.date2 "));
		}
	return errorList;
}

function addEntryData(tableId) {

	var errorList = [];
	//errorList = validateVehicleScheduleEntryDetails(errorList);
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
	var department = $("#department").val();
	var vehicleType = $("#vehicleType").val();
	var veId = $("#veNo").val();
	var remarks = $("#Remarks").val();

	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	/*if (department == "" || department == null)
		errorList.push( getLocalMessage("Please Select Department."));*/
	if (vehicleType == "" || vehicleType == null || vehicleType=="0")
		errorList.push(getLocalMessage("oem.warranty.validation.select.vehicle.type"));
	if (veId == "" || veId == null || veId=="0")
		errorList.push(getLocalMessage("oem.warranty.validation.select.vehicle.number"));
	if (remarks == "" || remarks == null)
		errorList.push(getLocalMessage("oem.warranty.validation.enter.remarks"));
	
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
				$(".purdatepicker").removeClass("hasDatepicker");
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "partType" +i);
				$(this).find("select:eq(1)").attr("id", "partPosition" + i);
				$(this).find("input:text:eq(1)").attr("id", "partName" + i);
				$(this).find("input:text:eq(2)").attr("id", "warrantyPeriod" + i);
				$(this).find("select:eq(2)").attr("id","unit"+i)
				$(this).find("input:text:eq(3)").attr("id", "purchaseDate" + i);
				$(this).find("input:text:eq(4)").attr("id", "lastDateOfWarranty" + i);
				
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("select:eq(0)").attr("name","oemWarrantyDto.tbvmoemwarrantydetails[" + i + "].partType");
				$(this).find("select:eq(1)").attr("name","oemWarrantyDto.tbvmoemwarrantydetails[" + i + "].partPosition");
				$(this).find("input:text:eq(1)").attr("name","oemWarrantyDto.tbvmoemwarrantydetails[" + i + "].partName");
				$(this).find("input:text:eq(2)").attr("name","oemWarrantyDto.tbvmoemwarrantydetails[" + i + "].warrantyPeriod");
				$(this).find("select:eq(2)").attr("name","oemWarrantyDto.tbvmoemwarrantydetails[" + i + "].unit");
				$(this).find("input:text:eq(3)").attr("name","oemWarrantyDto.tbvmoemwarrantydetails[" + i + "].purchaseDate");
				$(this).find("input:text:eq(4)").attr("name","oemWarrantyDto.tbvmoemwarrantydetails[" + i + "].lastDateOfWarranty");
				j=i;
				 
		           $(".purdatepicker").datepicker({                        
		                   changeMonth: true,
		                   changeYear: true,
		                   dateFormat: 'dd/mm/yy',   
		                   maxDate : new Date()
		               });//.datepicker("show");   
			});
	/*$("#empId"+j).append('#vehicleschedulingTbl').multiselect("destroy").multiselect();*/
}
function validateUnitDetailTable(errorList) {

	var rowCount = $('#vehicleschedulingTbl tr').length;
	var cnt = 0;
	var level = 0;
	$('.firstUnitRow')
			.each(function(i) {
						if (rowCount > 1) { //rowCount <= 1
							var partType = $("#partType" + i).val();
							var warrantyPeriod = $("#warrantyPeriod" + i).val();
							var purchaseDate = $("#purchaseDate" + i).val();
							var lastDateOfWarranty = $("#lastDateOfWarranty" + i).val();
							var unit = $('#unit'+i).val();
							level++;
						} /*else {
							var partType = $("#partType" + i).val();
							var warrantyPeriod = $("#warrantyPeriod" + i).val();
							var purchaseDate = $("#purchaseDate" + i).val();
							var lastDateOfWarranty = $("#lastDateOfWarranty" + i).val();
							level = i + 1;
						}*/
						
						/*if (partType == '' || partType == undefined || partType == '0') {
							errorList.push(getLocalMessage('Part Type must be selected')+ " " + level);
						}
						if (warrantyPeriod == '' || warrantyPeriod == undefined || warrantyPeriod == '0') {
							errorList.push(getLocalMessage('Please Enter Warranty Period')+ " " + level);
						}
						if (unit == '' || unit == undefined || unit == '0') {
							errorList.push(getLocalMessage('Please Enter unit')+ " " + level);
						}
						if (purchaseDate == '' || purchaseDate == undefined || purchaseDate == '0') {
							errorList.push(getLocalMessage('Please Enter Purchase Date')+ " " + level);
						}
						if (lastDateOfWarranty == '' || lastDateOfWarranty == undefined || lastDateOfWarranty == '0') {
							errorList.push(getLocalMessage('Please Enter Last Date Of Warranty')+ " " + level);
						}*/
						
					/*	if (endtime == '' || endtime == undefined || endtime == '0') {
							errorList.push(getLocalMessage('Star Time must be selected')+ " " + level);
						}
						if(lastDateOfWarranty < purchaseDate){
							errorList.push(getLocalMessage("swm.vehiclescheduling.validation.timevalidation"));
						}*/
						
						//validation for duplicate record
						if (rowCount > 1) {
							for(m=0; m<i; m++) {
								if( ($("#warrantyPeriod"+m).val()==$("#warrantyPeriod"+i).val())
									&& ($("#purchaseDate"+m).val()==$("#purchaseDate"+i).val())
									&&($("#unit"+m).val()==$("#unit"+i).val())
									&& ($("#lastDateOfWarranty"+m).val()==$("#lastDateOfWarranty"+i).val())
									&& ($("#partType"+m).val()==($("#partType"+i).val())
								)) {
								cnt++;
								}
							}	
						}
					});
	//validation for duplicate record cnt>=0
	if(cnt >=1) { 
		errorList.push(getLocalMessage("Duplicate Record"));
	}
	return errorList;
}

function validateVehicleScheduleEntryDetails(errorList,mode) {
	
	var j = 0;
	var rowCnt = $('#vehicleschedulingTbl tr').length;
	var cnt = 0;
	
	if ($.fn.DataTable.isDataTable('#vehicleschedulingTbl')) {
		$('#vehicleschedulingTbl').DataTable().destroy();
	}
	$("#vehicleschedulingTbl tbody tr").each(function(i) {
		
						var partPosition = $("#partPosition" +i).find("option:selected").attr('value');
						var partType = $("#partType" +i).find("option:selected").attr('value');
					/*	var partType = $("#partType" +i).val();*/
						var warrantyPeriod =$("#warrantyPeriod" + i).val();
						var purchaseDate = $("#purchaseDate" + i).val();
						var unit = $("#unit"+i).val();
						var lastDateOfWarranty = $("#lastDateOfWarranty" + i).val();
					/*	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
						var purchaseDate = new Date(purchaseDate.replace(pattern, '$3-$2-$1'));
						var lastDateOfWarranty = new Date(lastDateOfWarranty.replace(pattern, '$3-$2-$1'));
						var date = new Date();*/
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (partType == "0" || partType == null) {
							errorList.push(getLocalMessage("oem.warranty.validation.part.type.empty")+ rowCount);
						}
						if (partPosition == "0" || partPosition == null) {
							errorList.push(getLocalMessage("oem.warranty.validation.part.position.empty")+ rowCount);
						}
						if (warrantyPeriod == "" || warrantyPeriod == '') {
							errorList.push(getLocalMessage("oem.warranty.validation.enter.warranty.period")+ rowCount);
						}
						if (unit == "" || unit == '') {
							errorList.push(getLocalMessage("oem.warranty.validation.enter.unit")+ rowCount);
						}
					/*
						var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
						var purchaseDate = new Date(purchaseDate.replace(pattern, '$3-$2-$1'));
						var lastDateOfWarranty = new Date(lastDateOfWarranty.replace(pattern, '$3-$2-$1'));
					    var date = new Date();*/
						if (purchaseDate == "" || purchaseDate == null) {
							errorList.push(getLocalMessage("oem.warranty.validation.enter.purchase.date")+ rowCount);
						}
						if (lastDateOfWarranty == "" || lastDateOfWarranty == null) {
							errorList.push( getLocalMessage("oem.warranty.validation.enter.last.date.warranty")+ rowCount);
						}
						
						var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
						var pDate = new Date(purchaseDate.replace(pattern, '$3-$2-$1'));
						var lDate = new Date(lastDateOfWarranty.replace(pattern, '$3-$2-$1'));
						
						if (pDate >  lDate) {
							errorList.push(getLocalMessage("oem.warranty.validation.date1"));
						}
						/*if(purchaseDate < lastDateOfWarranty){
							errorList.push(getLocalMessage("swm.vehiclescheduling.validation.timevalidation"));
						}*/
						
						//validation for duplicate record
						if (rowCnt > 1) {
							for(j=0; j<i; j++) {
								if( ($("#partType"+j).val()==$("#partType"+i).val())
									&& ($("#partPosition"+j).val()==$("#partPosition"+i).val())	
									){
									cnt++;
								}
							}
						}
						
						if(mode=="C"){
						for(j=0; j<i;j++){
							if($('#beatId'+j).val() == $('#beatId'+i).val()){
								if($('#vesCollType'+j).val() == $('#vesCollType'+i).val()){
									errorList.push(getLocalMessage("oem.warranty.validation.collection.type.exists"));
								}
								if($('#startime'+j).val() == $('#startime'+i).val()){
									errorList.push(getLocalMessage("oem.warranty.validation.intime.exists"));
								}
								if($('#endtime'+j).val() == $('#endtime'+i).val()){
									errorList.push(getLocalMessage("oem.warranty.validation.outtime.exists"));
								}
							}
						}
						}
						
					});
	//validation for duplicate record
	/*if(cnt >=1) { 
		errorList.push(getLocalMessage("Duplicate Record"));
	}*/
	return errorList;
}


function addTableRow3(tableId) {
	
    var id = "#" + tableId;
	if ($.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	 $(".purdatepicker").removeClass("hasDatepicker");	
	var content = $(id + ' tr').last().clone();
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('');
	content.find("input:checkbox").removeAttr('checked');
	content.appendTo('tbody');
	reOrderUnitTabIdSequence('.firstUnitRow');
 	
	 $(".purdatepicker").datepicker({                        
         changeMonth: true,
         changeYear: true,
         dateFormat: 'dd/mm/yy',   
         maxDate : new Date()
     });
	//dataTableProperty(id);	
}

function showVehicleRegNo(obj, param) {
		
	$('#veNo').html('');
	var crtElementId = $("#vehicleType").val();
	var mode = param;
	var requestUrl = "OEMWarranty.html?getVehicleNo";
	var requestData = {
		"id" : crtElementId,
		"mode" : mode
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	$('#veNo').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#veNo').append(
				$("<option></option>").attr("value", index).attr("code", index)
						.text(value));
	});
	$('#veNo').trigger('chosen:updated');

}





function getVehicleTypeByDept() {
	
	$('#fuelType').html('');
    $('#vehicleType').html('');
    $('#veNo').html('');
    $('#fuelType').html('');
	var department = $("#department").val();
	var requestUrl = "OEMWarranty.html?getVehicleType";
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


function searchPetrolRequest() {	

	 var errorList = [];
	  var department = "";
	  var vehicleType =replaceZero($("#vehicleType").val());
	  var veNo =$('#veNo').val();
/*	  var warrantyPeriod = $('#warrantyPeriod').val();
	  var purchaseDate = $('#purchaseDate').val();
	  var lastDateOfWarranty = $('#lastDateOfWarranty').val();*/
	  var date = new Date();
	 /* if(department==null || department==""){
		  errorList.push(getLocalMessage("oem.valid.department"));
	  }*/
	  if(vehicleType==null || vehicleType==""){
		  errorList.push(getLocalMessage("oem.valid.vehicleType"));
	  }
	  if (veNo == null || veNo == "0") {
		  errorList.push(getLocalMessage("oem.valid.vehNo"));
	  }
	  
	
	    /* if(department=="" || vehicleType=="0" || vehicleType=="" || veNo=="" || veNo=="0"){
	              errorList.push("Please Select All Mandatory Fields");
	     } */
	
/*  	if (errorList.length > 0) {
	 	checkDate(errorList);
	    }
	    else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	//	var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
	//	var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (eDate > date) {
			errorList
					.push("From date cannot be greater than Current Date");
		}
		
		if (eDate > sDate) {
			errorList
					.push("To Date cannot be less than From Date");
		}
		if (sDate >= date) {
			errorList
					.push("To Date cannot be greater than Current Date");
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
	    }*/
	    if(errorList.length ==0){
	    if (department != '' || vehicleType != '0' || veNo !='') {
	    	
		var requestData = '&department=' + department + '&veNo=' + veNo + '&vehicleType=' + vehicleType;
		var table = $('#oemWarrantyTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest('OEMWarranty.html?SearchOemWarranty', 'POST',requestData, false, 'json');
		var petrolRequisitionDTOs = response;
		if (petrolRequisitionDTOs.length == 0) {
			errorList.push(getLocalMessage("Record not found"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(  petrolRequisitionDTOs,
						function(index) {
			
					    var obj = petrolRequisitionDTOs[index];
				    	let oemId = obj.oemId
				    	let date = getDateFormat(obj.date);
				    	let partType = obj.partDesc;
				    	var partName = obj.partName;
				    	var warrantyPeriod = obj.warrantyPeriod;
				    	let purchaseDate = getDateFormat(obj.purchaseDate);
				    	let lastDateOfWarranty = getDateFormat(obj.lastDateOfWarranty);
			
				    	var viewMessage = getLocalMessage("vehicle.view");
				    	var editMessage = getLocalMessage("vehicle.edit");
				    	
					result
							.push([
									'<div class="text-center">' + (index + 1) + '</div>',
									'<div class="text-center">' + partType + '</div>',
									'<div class="text-center">' + partName + '</div>',
									'<div class="text-center">' + warrantyPeriod + '</div>',
									'<div class="text-center">' + purchaseDate + '</div>',
									'<div class="text-center">' + lastDateOfWarranty + '</div>',
									
									'<div class="text-center">' 
									+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyOemRequest(\''
									+ oemId
									+ '\',\'OEMWarranty.html\',\'viewWarrantyForm\',\'V\')" title="'+viewMessage+'"><i class="fa fa-eye"></i></button>'
									+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyOemRequest(\''
									+ oemId
									+ '\',\'OEMWarranty.html\',\'editWarrantyForm\',\'E\')"  title="'+editMessage+'"><i class="fa fa-pencil"></i></button>'
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

function modifyOemRequest(id, formUrl, actionParam, mode) {
    
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

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

function searchVeNo() {
	
    var requestData = {
	"veNo" : $("#veNo").val()
    };
    
    $('#veNo').html('');
    $('#veNo').append(
	    $("<option></option>").attr("value", "0").text(
		    getLocalMessage('selectdropdown')));

    var ajaxResponse = doAjaxLoading(
	    'OEMWarranty.html?searchVeNo', requestData,
	    'json');
    
	var veNodata='<option value="'+ajaxResponse.veId+'">'+ajaxResponse.veNo+'</option>'
	$("#veNo").html(veNodata);
    
}
function unitNumFun(id){
	var warrantyPeriod = $('#'+id).val()
	warrantyPeriod =warrantyPeriod.replace(/\D/g,'') 
	if(warrantyPeriod == 0 || !$.isNumeric(warrantyPeriod)){
		 $('#'+id).val('');
	}else{
		 $('#'+id).val(warrantyPeriod);
	}
	
	$('#'+id).change();
}
$(document).on('change', '.purdatepicker', function(ele) {
	var purId = ele.currentTarget.id;
	var numPur = purId.substring(12)
	autoCalculateLWP(numPur)
});
$(document).on('change', '.warPerCustCheck', function(ele) {
	var purId = ele.currentTarget.id;
	var numPur = purId.substring(14)
	autoCalculateLWP(numPur)
});
$(document).on('change', '.unitCustClass', function(ele) {
	var unitId = ele.currentTarget.id;
	var numUnit = unitId.substring(4)
	autoCalculateLWP(numUnit)
});

function autoCalculateLWP(numPur) {
	
	var purId = '#purchaseDate' + numPur
	var lasWarDateId = '#lastDateOfWarranty' + numPur
	var warPerId = '#warrantyPeriod' + numPur
	var unitId = '#unit'+numPur
	var demo=$(purId).datepicker('getDate');
	if ($(purId).val() != "" && $(warPerId).val() != ""	&& $(unitId).val() != ""  && $(unitId).val() != 0 && $.isNumeric($(warPerId).val())) {
		if($(purId).datepicker('getDate') != undefined){
		var formatedPurDate = $(purId).datepicker('getDate');
		}else{
			var d = $(purId).val();
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date(d.replace(pattern, '$3-$2-$1'));
			var formatedPurDate =	date;
		}
		var unit = $(unitId+" option:selected").text();
		var myDate = new Date(formatedPurDate);
		var warPerVal = $(warPerId).val();
			if(unit == 'Years'){
			myDate.setFullYear(myDate.getFullYear() + parseInt(warPerVal));
			//myDate.setDate(myDate.getDate() + parseInt(warPerVal));
			}else if(unit == 'Months'){
			   myDate.setMonth(myDate.getMonth()+ parseInt(warPerVal));
			}else if(unit == 'Days'){
				myDate.setDate( myDate.getDate()+ parseInt(warPerVal-1));    
			}
		var day = ("0" + (myDate.getDate())).slice(-2);
		var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
		var dateString = day + '/' + month + '/'
				+ myDate.getFullYear();
		$(lasWarDateId).val(dateString);
	}else{
		$(lasWarDateId).val('');
	}
}
