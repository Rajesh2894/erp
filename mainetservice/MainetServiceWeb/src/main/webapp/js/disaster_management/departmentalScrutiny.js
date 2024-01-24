/* Date Field */
$(document).ready(function() {

	var date=new Date($('#createdDate').val());

	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});
	
	$(".timepicker").timepicker({
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	$("#callAttendDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange: "-200:+200",
		minDate : date,
		maxDate: new Date(),  
	});
	

	$(function() {
		$('.datetimepicker3').timepicker();
	});
	
	$("#complainRegDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	resetFormFields(); //Defect #152078
	$('#billDetailTableDiv').hide();
	

	
	$(".radio input[type='radio']").change(function() {
		var selectedValue = $(this).val();
		if(selectedValue != "F"){
			$(".mandatory").addClass('required-control');
		}else{
			$(".mandatory").removeClass('required-control');
		}
		
	});
	
});

//Defect #152078
function resetFormFields() {
	$('#resetBtn').on('click', function() {
		$('input[type=text], textarea, select').each(function() {
			var rstCond1 = $(this).attr('disabled');
			var rstCond2 = $(this).attr('readonly');
			if (!(rstCond1 || rstCond2)) {
				var errorField = $(this).next().hasClass('error');
				if('input[type=text], textarea') {
					$(this).val('');
				}
				if('select') {
					if($(this).val() != '') {
						$(this).val('0');
					} else {
						$(this).val('');
					}
				}
				$(this).parent().removeClass('has-error');
				if (errorField == true) {
					$(this).next().remove();
				}
			}
        });
		$('.chosen-select-no-results').val('').trigger('chosen:updated');
		$('#approve, #reject').val('').attr('checked', false);
		$('.error-div').empty();
	});
}

/* Search Criteria */
function searchDeptScrutiny() {
	
	var errorList = [];
	var complainNo = $('#complainNo').val();
	var frmDate = $('#frmDate').val();
	var toDate = $('#toDate').val();
	var date = new Date();

	if (frmDate == "" &&  toDate == "" && complainNo == "") {
		//errorList.push(getLocalMessage("Please enter at least one search criteria"));
	} 
	else if (complainNo != "" ) {
		//errorList.push("Please enter complain number");
	} 
	else if (frmDate != "" && toDate != "") {
		//errorList.push("Please enter valid from date and to date");
	} 
	else {
		errorList.push(getLocalMessage("ComplainRegisterDTO.validation.date.1"));
	}
	
	
	/*if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}*/	
		
	/*if(complainNo == null && complainNo == "") {
		errorList.push(getLocalMessage("Please enter complain number"));
	}
	
    if((frmDate == null && frmDate == "") || (toDate == null && toDate == "")) {	
		if (frmDate == null || frmDate == "") {
			errorList.push(getLocalMessage("Please select From Date "));
		}
		if (toDate == null || toDate == "") {
			errorList.push(getLocalMessage("Please select To Date"));
		}
		errorList.push(getLocalMessage("Please select From Date and To Date"));
    }*/
    
	
	if (errorList.length > 0) {
		checkDate(errorList);
	} else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(frmDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (eDate > sDate) {
			errorList.push(getLocalMessage("ComplainRegisterDTO.validation.date.2"));
		}
		if (sDate >= date) {
			errorList.push(getLocalMessage("ComplainRegisterDTO.validation.date.3"));
		}

		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
	}
	
	if ((complainNo != null && complainNo != "") || (frmDate!=null && frmDate!="" && toDate!=null && toDate!="")) {
		var requestData = "complainNo=" + $('#complainNo').val() + "&frmDate=" + $('#frmDate').val() + "&toDate=" + $('#toDate').val();
		var table = $('#complainRegDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest('DepartmentalScrutiny.html?searchComplain', 'POST', requestData, false, 'json');
		
		var complainRegisterDTOList = response;
		if (complainRegisterDTOList.length == 0) {
			errorList.push(getLocalMessage("ComplainRegisterDTO.validation.record.search.criteria"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						complainRegisterDTOList,
						function(index) {
							
							var obj = complainRegisterDTOList[index];
							let complainNo = obj.complainNo;
							let createdDate = obj.createdDate;
							let complaintType1 = obj.complaintType1Desc;
							let complaintDescription = obj.complaintDescription;
							let complainId = obj.complainId;
							var Edit = getLocalMessage("ComplainRegisterDTO.edit.departmental.scrutiny");

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ complainNo + '</div>',
											'<div class="text-center">'
													+ getDateFormat(createdDate) + '</div>',
											'<div class="text-center">'
													+ complaintType1 + '</div>',
											'<div class="text-center">'
													+ complaintDescription + '</div>',
											'<div class="text-center">'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyComplainReg(\''
													+ complainId
													+ '\',\'DepartmentalScrutiny.html\',\'editComplainReg\',\'E\')"  title="'+Edit+'"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);
						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList.push(getLocalMessage("ComplainRegisterDTO.validation.search.criteria"));
		displayErrorsOnPage(errorList);
	}
}


/* Open Form On Edit Action */
function modifyComplainReg(complainId, formUrl, actionParam, mode) {
	
	var divName = '.content';
	var requestData = {
	"mode" : mode,
	"id"   : complainId
	};
	
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}

function setStatusApproval(){
	$("#approve").val('A');
	$("#reject").val('');
	$("#hiddenStatus").val('A');
	$('#billDetailTableDiv').hide();
}
function setStatusRejection(){
	$("#reject").val('R');
	$("#approve").val('');
	$("#hiddenStatus").val('R');
	$('#billDetailTableDiv').hide();
}
function setStatusForward(){
	$("#forward").val('F');
	$("#approve").val('');
	$("#reject").val('');
	$("#hiddenStatus").val('F');
	$('#billDetailTableDiv').show();
}
/* save Complaint Registration Approval */
function confirmToProceed(element) {
	var errorList = [];
	var remark = $("#remark").val();
	var hiddenStatus = $("#hiddenStatus").val();
    var callAttendDate = $("#callAttendDate").val();
    var callAttendTime = $("#callAttendTime").val();
  /*  var callAttendEmployeeList = $("#callAttendEmployeeList").val();*/
    var hiddenStatus = $("#hiddenStatus").val();
    if(hiddenStatus!=null && hiddenStatus!="" && hiddenStatus =='F' ){
    var srtDepart='';
	var srtEmpl='';
	var rowCount = $('#billDetailTable tr').length-1;
	var cnt = 0;
	
	if(rowCount>=2){	
	$('.billDetailClass').each(function(i){
		//validation for duplicate record
		for(m=0; m<i; m++) {
			if($("#department"+m).val()==$("#department"+i).val() && $("#employee"+m).val()==$("#employee"+i).val()){
				cnt++;
			}
		}
		
		//if($("#department"+i).val()!="0" && $("#employee"+i).val()!="0"){
		if(($("#department"+i).val()!="0" && $("#department"+i).val()!="" && $("#department"+i).val()!= null && $("#department"+i).val()!= undefined  )
				&& ($("#employee"+i).val()!="0" && $("#employee"+i).val()!="" && $("#employee"+i).val()!= null && $("#employee"+i).val()!= undefined )){
				
		srtDepart=srtDepart+$("#department"+i).val()+',';
		srtEmpl=srtEmpl+$("#employee"+i).val()+',';
		}
		else{
			errorList.push(getLocalMessage("ComplainRegisterDTO.validation.employee.department"));
		}
		
	});
	}else{
		if(rowCount == 1){
		if( $("#department").val() != undefined){
			if( (($("#department").val()!="0" && $("#department").val()!="" && $("#department").val()!= null && $("#department").val()!= undefined  )
					&& ($("#employee").val()!="0" && $("#employee").val()!="" && $("#employee").val()!= null && $("#employee").val()!= undefined ))){
				srtDepart=srtDepart+$("#department").val()+',';
				srtEmpl=srtEmpl+$("#employee").val()+',';
				}
				else{
					errorList.push(getLocalMessage("ComplainRegisterDTO.validation.employee.department"));
				}
		}else{
			if( (($("#department0").val()!="0" && $("#department0").val()!="" && $("#department0").val()!= null && $("#department0").val()!= undefined  )
				&& ($("#employee0").val()!="0" && $("#employee0").val()!="" && $("#employee0").val()!= null && $("#employee0").val()!= undefined ))){
				srtDepart=srtDepart+$("#department0").val()+',';
				srtEmpl=srtEmpl+$("#employee0").val()+',';
				}
				else{
					errorList.push(getLocalMessage("ComplainRegisterDTO.validation.employee.department"));
				}
		}
		
		
		
		}
	}
    

	$('#dept').val(srtDepart);
	$('#emp').val(srtEmpl);
	
	$('#dept').trigger("chosen:updated");
	$('#emp').trigger("chosen:updated");
    
    }
	if (remark == null || remark == "") {
		errorList.push(getLocalMessage("validation.complainRegisterDTO.enter.remark"));
	}
	if(hiddenStatus==null || hiddenStatus==""){
		errorList.push(getLocalMessage("validation.complainRegisterDTO.select.status"));
	}
	if(hiddenStatus != "F"){
		if (callAttendDate == null || callAttendDate == "" || callAttendDate == undefined) {
			errorList.push(getLocalMessage("validation.complainRegisterDTO.callAttendDate"));
			
		}
		if (callAttendTime == null || callAttendTime == "" || callAttendTime == undefined) {
			errorList.push(getLocalMessage("validation.complainRegisterDTO.callAttendTime"));
			
		}
		/*if (callAttendEmployeeList == null || callAttendEmployeeList == "" || callAttendEmployeeList == undefined) {
			errorList.push(getLocalMessage("validation.complainRegisterDTO.callAttendEmployee"));
			
		}*/	
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
		//checkError(errorList);
		
	}
	else {
		return saveOrUpdateForm(element, "", 'DepartmentalScrutiny.html', 'saveform');
	} 
	
}

/* show error on page */
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

function checkError(errorList) {
	
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}


/* save Complaint Registration Approval */
/*$(document).ready(function() {

	$("#hospitalDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});*/


/* Reset Form */
/*function resetForm(element){
	$("#frmDepartmentalScrutiny").submit();
}*/


function getEmployeeByDept(obj) {

	$('#employee').html('');
	
	//$("#employee option").remove(); 
	//$("#employee").empty();
	//$('#employee').append($("<option></option>").attr("value", "0").text(getLocalMessage('selectdropdown')));
	/*var url = "ComplainRegister.html?getEmployeeByDept";
	var deptId;
	var flag=false;
	if(obj==0){
		deptId=$('#designation').val();
		if(deptId==undefined){
			deptId=$('#designation'+obj).val();
			flag=true;
		}
	}
	else{
	 deptId=$('#designation'+obj).val();
	}
	*/
	var url = "ComplainRegister.html?getEmployeeByDept";
	var deptId;
	var designId;
	var flag=false;
	if(obj==0){
		deptId=$('#department').val();
		designId=$('#designation').val();
		if(deptId==undefined){
			deptId=$('#department'+obj).val();
			designId=$('#designation'+obj).val();
			flag=true;
		}
	}
	else{
	 deptId=$('#department'+obj).val();
	 designId=$('#designation'+obj).val();
	}
	
	if(obj==0 && flag==false){
		$('#employee').find('option').remove().end().append('<option value="0"></option>');
		$("#employee").trigger("chosen:updated"); 
	}
	else{
		$('#employee'+obj).find('option').remove().end().append('<option value="0"></option>');
		$("#employee"+obj).trigger("chosen:updated");
	}
	$('#employee'+obj).html('');
	var requestData = "deptId=" + deptId+ '&designId=' + designId;
	var response = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	//$('#employee'+obj).text('');
	//$('#employee'+obj).val('');
	//$("#employee option").remove();

	$.each(response, function(key, value) {
	 if(obj==0 && flag==false){
		$('#employee').append($('<option>', {value:value.empId, text:value.fullName}));
		$('#employee').trigger("chosen:updated");
	 }
	 else{
		$('#employee'+obj).append($('<option>', {value:value.empId, text:value.fullName}));
		$("#employee"+obj).trigger("chosen:updated");
	 }
	});

}		



$(function() {

	$("#billDetailTable").unbind('click').on("click",'.addButton11',function(e) {

	var errorList=[];
	$('.billDetailClass').each(function(i) {
		var rowCount = $('#billDetailTable tr').length;
		if(rowCount==2){
			var department =$('#department').val();
			var employee=$('#employee').val();
			if(department=="" || department=="0"){
				errorList.push(getLocalMessage("complaintRegister.dept"));
			}
			if(employee=="" || employee=="0"){
				errorList.push(getLocalMessage("complaintRegister.employee"));
			}
		}
		else{
			var department =$('#department'+i).val();
			var employee=$('#employee'+i).val();
			if(department==undefined || department=="0"){
				errorList.push(getLocalMessage("complaintRegister.dept"));
			}
			if(employee==undefined || employee=="0"){
				errorList.push(getLocalMessage("complaintRegister.employee"));
			}
		}			
	});
	if(errorList.length > 0){
		displayErrorsOnPage(errorList);
	}
	else{
		var content = $(this).closest('#billDetailTable tr').clone();
		$(this).closest("#billDetailTable").append(content);
		content.find("select").attr("value", "");
		content.find("input:text").val("");
		content.find("input:checkbox").attr("checked", false);
		content.find('div.chosen-container').remove();
		content.find('label').closest('.error').remove(); //for removal duplicate
		reOrderTableIdSequenceVish();
		e.preventDefault();
		content.find("select:eq(0)").chosen().trigger("chosen:updated");
		}
	});
	//to delete row
	$("#billDetailTable").on("click", '.delButton', function(e) {
		var rowCount = $('#billDetailTable tr').length;
		if (rowCount <= 2) {
			return false;
		}
		$(this).closest('#billDetailTable tr').remove();

		var billAmountTotal = 0;
		var rowCount = $('#billDetailTable tr').length;
		reOrderTableIdSequenceVish();
		e.preventDefault();
	});
	
});
 	
	function reOrderTableIdSequenceVish(){
		$('.billDetailClass').each(function(i) {
			$(this).find("select:eq(0)").attr("id",	"locId" + i);
			$(this).find("select:eq(1)").attr("id",	"department" + i);
			$(this).find("select:eq(2)").attr("id",	"designation" + i);
			$(this).find("select:eq(3)").attr("id",	"employee" + i);
			$(this).find("select:eq(0)").attr("name","complainRegList["+ i + "].locId");
			$(this).find("select:eq(1)").attr("name","complainRegList["+ i + "].department");
			$(this).find("select:eq(2)").attr("name","complainRegList["+ i + "].designation");
			$(this).find("select:eq(3)").attr("name","complainRegList["+ i + "].employee");
			$(this).find("select:eq(0)").attr("onchange", "getDepBasedOnLoc(" + i + ")");
			$(this).find("select:eq(1)").attr("onchange", "getDesgBasedOnDept(" + i + ")");
			$(this).find("select:eq(2)").attr("onchange", "getEmployeeByDept(" + i + ")");
		});
	} 	
	
	function getDesgBasedOnDept(obj) {
		var url = "ComplainRegister.html?getDesgBasedOnDept";
		var languageId=1;
		var deptId;
		if(obj==0){
			deptId=$('#department').val();
			if(deptId==undefined){
				deptId=$('#department'+obj).val();
				flag=true;
			}
		}
		else{
			 deptId=$('#department'+obj).val();
			}
		$('#designation'+obj).html('');
		var postdata = "deptId=" + deptId;
		var json = __doAjaxRequest(url, 'POST', postdata, false, 'json');
		$('#designation option').remove();
		var selectvar = getLocalMessage('contract.master.select.designation');
		var optionsAsString = "<option value='0'>"+selectvar+"</option>";
		for (var j = 0; j < json.length; j++) {
			if(languageId == 1){
				optionsAsString += "<option value='" + json[j].lookUpId + "'>"
				+ json[j].descLangFirst + "</option>";
			}else{
				optionsAsString += "<option value='" + json[j].lookUpId + "'>"
				+ json[j].descLangSecond + "</option>";
			}
			
		}
		 if(obj==0){
		$('#designation').append(optionsAsString).trigger("chosen:updated");
		 }
		 else{
		$('#designation'+obj).append(optionsAsString).trigger("chosen:updated");	 
		 }
	}	
	
	function getDepBasedOnLoc(obj) {
		var url = "ComplainRegister.html?getDepBasedOnLoc";
		var languageId=1;
		var deptId;
		if(obj==0){
			locId=$('#locId').val();
			if(locId==undefined){
				locId=$('#locId'+obj).val();
				flag=true;
			}
		}
		else{
			locId=$('#locId'+obj).val();
			}
		$('#department'+obj).html('');
		var postdata = "locId=" + locId;
		var json = __doAjaxRequest(url, 'POST', postdata, false, 'json');
		$('#department option').remove();
		var selectvar = getLocalMessage('Select');
		var optionsAsString = "<option value='0'>"+selectvar+"</option>";
		for (var j = 0; j < json.length; j++) {
			if(languageId == 1){
				optionsAsString += "<option value='" + json[j].lookUpId + "'>"
				+ json[j].descLangFirst + "</option>";
			}else{
				optionsAsString += "<option value='" + json[j].lookUpId + "'>"
				+ json[j].descLangSecond + "</option>";
			}
			
		}
		 if(obj==0){
		$('#department').append(optionsAsString).trigger("chosen:updated");
		 }
		 else{
		$('#department'+obj).append(optionsAsString).trigger("chosen:updated");	 
		 }
	}	
 		
	
	


