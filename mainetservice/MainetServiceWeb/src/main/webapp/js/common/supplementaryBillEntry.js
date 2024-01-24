$(document).ready(function() {


});


/* Open Add Form   */
function addSupplementaryPayBill(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function getEmployeeData(rowIndex) {  debugger;
	$("#errorDiv").hide();
	var errorList = [];
	var suppType = $("#suppType").val();
	var empId = $("#empId" + rowIndex).val();
	var payMonth = $("#payMonth" + rowIndex).val();
	var payYear = $("#payYear" + rowIndex).val();

	errorList = validateMonthYearData();
	errorList = validateSupplimentoryBillEntry(errorList);
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		var data = {
			"empId": empId,
		};
		var ajaxResponse = __doAjaxRequest("SupplementaryPayBillEntry.html?checkIsValidEmployee", 'post', data, false, 'json');
		if (true == ajaxResponse) {
			if ((empId != "0" || empId != null || empId != "" || empId != undefined)
				&& payMonth != "" && payYear != "") {
				var requestData = {
					"empId": empId,
					"payMonth": payMonth,
					"payYear": payYear,
					"suppType": suppType,
				};
				var ajaxResponse = __doAjaxRequest("SupplementaryPayBillEntry.html?getEmployeeData", 'post', requestData, false, 'json');

				if ("SSB" == suppType) {
					$("#empName" + rowIndex).val(ajaxResponse[0]);
					$("#basicPay" + rowIndex).val(ajaxResponse[1]);
					$("#gradePay" + rowIndex).val(ajaxResponse[2]);
					$("#designation" + rowIndex).val(ajaxResponse[3]);
					$("#eLeave" + rowIndex).val(ajaxResponse[4]);
					$("#mLeave" + rowIndex).val(ajaxResponse[5]);
					$("#hpLeave" + rowIndex).val(ajaxResponse[6]);
					$("#workDays" + rowIndex).val(ajaxResponse[7]);

					$("#refELeave" + rowIndex).val(ajaxResponse[4]);
					$("#refMLeave" + rowIndex).val(ajaxResponse[5]);
					$("#refHpLeave" + rowIndex).val(ajaxResponse[6]);
					$("#refWorkDays" + rowIndex).val(ajaxResponse[7]);					
				}
			}
		} else {
			errorList.push(getLocalMessage("Please Enter Valid Employee Id for Sr. No.") + " " + (rowIndex + 1));
			displayErrorsOnPage(errorList);
		}
	}
}


function getEmployeePayDetails(rowIndex) {  debugger;
	$("#errorDiv").hide();
	var suppType = $("#suppType").val();
	var suppMonth = $("#suppMonth").val();
	var suppYear = $("#suppYear").val();
	var empId = $("#empId" + rowIndex).val();
	var payMonth = $("#payMonth" + rowIndex).val();
	var payYear = $("#payYear" + rowIndex).val();
	var eLeave = $("#eLeave" + rowIndex).val();
	var mLeave = $("#mLeave" + rowIndex).val();
	var hpLeave = $("#hpLeave" + rowIndex).val();
	var workDays = $("#workDays" + rowIndex).val();
	
	var errorList = [];
	errorList = validateMonthYearData();
    errorList = validateSupplimentoryBillEntry(errorList);
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		if ((empId != "0" || empId != null || empId != "" || empId != undefined)
			&& payMonth != "" && payYear != "") {
			var requestData = {
				"empId": empId,
				"payMonth": payMonth,
				"payYear": payYear,
				"suppType": suppType,
				"suppMonth" : suppMonth,
				"suppYear" : suppYear,
				"eLeave" : eLeave,
				"mLeave" : mLeave,
				"hpLeave" : hpLeave,
				"workDays" : workDays,
			};
			var ajaxResponse = __doAjaxRequest("SupplementaryPayBillEntry.html?getEmployeePayDetails", 'post', requestData, false, 'json');

			if ("SSB" == suppType) {
				$("#grossAmount" + rowIndex).val(ajaxResponse[0]);
				$("#deductionAmount" + rowIndex).val(ajaxResponse[1]);
				$("#netPay" + rowIndex).val(ajaxResponse[2]);
			}
		}
	}
}

function getDaysInMonth(month, year) {
    return new Date(year, month, 0).getDate();
}

function validateMonthYearData() { debugger;
	$("#errorDiv").hide();
	var errorList = [];
	var suppMonthCode = parseInt($("#suppMonth option:selected").attr("code"));
	var suppYear = $("#suppYear").val();
	var dataArray = [];
	$(".empDetailsRow").each(function(i) {
		var empId = $("#empId" + i).val();
		var payMonth = $("#payMonth" + i).val();
		var payMonthCode = parseInt($("#payMonth" + i + " option:selected").attr("code"));
		var payYear = $("#payYear" + i).val();
		var rowData = empId + " " + payMonth + " " + payYear;

		if (dataArray.includes(rowData))
			errorList.push(getLocalMessage("The Selection Criterial Allready Available for" + " " + "Sr. No") + " " + (i + 1));
		dataArray.push(rowData);
		
		if(parseInt(suppYear) <= parseInt(payYear)){
			if (suppMonthCode < payMonthCode)
				errorList.push("The Pay Month And Pay Year Should Be Less Than OR Equal To Supplementary Month And Supplementary Year for Sr. No." + " " + (i + 1));	
		}
			
		var eLeave = parseFloat($("#eLeave" + i).val()) || 0;
		var mLeave = parseFloat($("#mLeave" + i).val()) || 0;
		var hpLeave = parseFloat($("#hpLeave" + i).val()) || 0;
		var workDays = parseFloat($("#workDays" + i).val()) ||0;
		var daysInMonth = parseFloat(getDaysInMonth(payMonthCode, parseInt(payYear)));
		if (daysInMonth < eLeave)
			errorList.push("The EL Can Not Be Greater Than No. of Day In Month For Sr. No." + " " + (i + 1));
		if (daysInMonth < mLeave)
			errorList.push("The ML Can Not Be Greater Than No. of Day In Month For Sr. No." + " " + (i + 1));
		if (daysInMonth < hpLeave)
			errorList.push("The HPL Can Not Be Greater Than No. of Day In Month For Sr. No." + " " + (i + 1));
		if (daysInMonth < workDays)
			errorList.push("The Work Days Can Not Be Greater Than No. of Day In Month For Sr. No." + " " + (i + 1));
		if((daysInMonth > eLeave) && (daysInMonth > mLeave) && (daysInMonth > hpLeave) && (daysInMonth > workDays)){
			if (daysInMonth < (eLeave + mLeave + hpLeave + workDays))
				errorList.push("The Sum of EL, ML, HPL And Work Days Can Not Be Greater Than No. of Day In Month For Sr. No." + " " + (i + 1));	
		}
		
		
		var refELeave = parseFloat($("#refELeave" + i).val()) || 0;
		var refMLeave = parseFloat( $("#refMLeave" + i).val()) || 0;
		var refHpLeave = parseFloat($("#refHpLeave" + i).val()) || 0;
		var refWorkDays = parseFloat($("#refWorkDays" + i).val()) || 0;	
		if (eLeave > refELeave)
			errorList.push("The El for Sr. No." + (i+1) + " " + "was " + refELeave);
		if (mLeave > refMLeave)
			errorList.push("The El for Sr. No." + (i+1) + " " + "was " + refMLeave);
		if (hpLeave > refHpLeave)
			errorList.push("The El for Sr. No." + (i+1) + " " + "was " + refHpLeave);
		if (workDays > refWorkDays)
			errorList.push("The El for Sr. No." + (i+1) + " " + "was " + refWorkDays);
	});
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	return errorList;
}


/* for In Batch Add */
function addUnitRowInBatch() {
	var errorList = [];
	errorList = validateMonthYearData();
	errorList = validateEmployeeData(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var content = $("#employeeDataTableId").find('tr:eq(1)').clone();
		$("#employeeDataTableId").append(content);	
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find('[id^="payMonth"]').chosen().trigger("chosen:updated");
		content.find('[id^="payYear"]').chosen().trigger("chosen:updated");
		$('.error-div').hide();
		employeeDataReorder('.empDetailsRow');
	}
}


/*  for In Batch Remove  */
var removeInBatchIdArray = [];
$("#employeeDataTableId").on( 'click', '.deleteEmployeeDataRow', function() {   debugger;
	var rowCount = $('#employeeDataTableId tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}else{
        $(this).closest('tr').remove();
        employeeDataReorder('.empDetailsRow');
	}
});


/*  for Reorder Table  */
function employeeDataReorder(empDetailsRow) {
	$(empDetailsRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "srNo" + i);
		$(this).find("input:text:eq(1)").attr("id", "empId" + i).attr("onchange", "getEmployeeData("+i+")");
		$(this).find("select:eq(0)").attr("id", "payMonth" + i).attr("onchange", "getEmployeeData("+i+")");
		$(this).find("select:eq(1)").attr("id", "payYear" + i).attr("onchange", "getEmployeeData("+i+")");
		$(this).find("input:text:eq(4)").attr("id", "empName" + i);		
		$(this).find("input:text:eq(5)").attr("id", "basicPay" + i);
		$(this).find("input:text:eq(6)").attr("id", "gradePay" + i);
		$(this).find("input:text:eq(7)").attr("id", "designation" + i);
		$(this).find("input:text:eq(8)").attr("id", "eLeave" + i).attr("onchange", "getEmployeePayDetails("+i+")");
		$(this).find("input:text:eq(9)").attr("id", "mLeave" + i).attr("onchange", "getEmployeePayDetails("+i+")");
		$(this).find("input:text:eq(10)").attr("id", "hpLeave" + i).attr("onchange", "getEmployeePayDetails("+i+")");
		$(this).find("input:text:eq(11)").attr("id", "workDays" + i).attr("onchange", "getEmployeePayDetails("+i+")");
		$(this).find("input:text:eq(12)").attr("id", "remark" + i);
		$(this).find("input:text:eq(13)").attr("id", "grossAmount" + i);
		$(this).find("input:text:eq(14)").attr("id", "deductionAmount" + i);
		$(this).find("input:text:eq(15)").attr("id", "netPay" + i);
		$(this).find("button:eq(0)").attr("id", "viewDetail" + (i)).attr("onclick", "");
		$(this).find("input:hidden:eq(0)").attr("id", "refELeave" + i);
		$(this).find("input:hidden:eq(1)").attr("id", "refMLeave" + i);
		$(this).find("input:hidden:eq(2)").attr("id", "refHpLeave" + i);
		$(this).find("input:hidden:eq(3)").attr("id", "refWorkDays" + i);

		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("input:text:eq(1)").attr("name", "supplimentartPayBillDTOList[" + i + "].empId");
		$(this).find("select:eq(0)").attr("name", "supplimentartPayBillDTOList[" + i + "].payMonth");
		$(this).find("select:eq(1)").attr("name", "supplimentartPayBillDTOList[" + i + "].payYear");
		$(this).find("input:text:eq(4)").attr("name", "supplimentartPayBillDTOList[" + i + "].empName");
		$(this).find("input:text:eq(5)").attr("name", "supplimentartPayBillDTOList[" + i + "].basicPay");
		$(this).find("input:text:eq(6)").attr("name", "supplimentartPayBillDTOList[" + i + "].gradePay");
		$(this).find("input:text:eq(7)").attr("name", "supplimentartPayBillDTOList[" + i + "].designation");
		$(this).find("input:text:eq(8)").attr("name", "supplimentartPayBillDTOList[" + i + "].eLeave");
		$(this).find("input:text:eq(9)").attr("name", "supplimentartPayBillDTOList[" + i + "].mLeave");
		$(this).find("input:text:eq(10)").attr("name", "supplimentartPayBillDTOList[" + i + "].hpLeave");
		$(this).find("input:text:eq(11)").attr("name", "supplimentartPayBillDTOList[" + i + "].workDays");
		$(this).find("input:text:eq(12)").attr("name", "supplimentartPayBillDTOList[" + i + "].remark");
		$(this).find("input:text:eq(13)").attr("name", "supplimentartPayBillDTOList[" + i + "].grossAmount");
		$(this).find("input:text:eq(14)").attr("name", "supplimentartPayBillDTOList[" + i + "].deductionAmount");
		$(this).find("input:text:eq(15)").attr("name", "supplimentartPayBillDTOList[" + i + "].netPay");
		$(this).find("button:eq(0)").attr("id", "viewDetail" + (i)).attr("onclick", "");

		$(empDetailsRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	});
}


function submitSupplimentoryBill(element) { debugger;
	var errorList = [];
	errorList = validateSupplimentoryBillEntry(errorList);
	errorList = validateEmployeeData(errorList);

	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else
		return saveOrUpdateForm(element, "", "SupplementaryPayBillEntry.html", "saveform");
}


function validateSupplimentoryBillEntry(errorList){
	var suppMonth = $("#suppMonth").val();
	var suppYear = $("#suppYear").val();
	var suppType = $("#suppType").val();
	
	if(suppMonth == "0" || suppMonth == null || suppMonth == "" || suppMonth == undefined)
		errorList.push("Please Select Supplementary Month");
	if(suppYear == "0" || suppYear == null || suppYear == "" || suppYear == undefined)
		errorList.push("Please Select Supplementary Year");
	if(suppType == "0" || suppType == null || suppType == "" || suppType == undefined)
		errorList.push("Please Select Supplementary Type");
	return errorList;
}

function validateEmployeeData(errorList) { debugger;
	var suppMonthCode = parseInt($("#suppMonth option:selected").attr("code"));
	var suppYear = $("#suppYear").val();
	$(".empDetailsRow").each(function(i) {
		var empId = $("#empId" + i).val();
		var payMonth = $("#payMonth" + i).val();
		var payMonthCode = parseInt($("#payMonth" + i + " option:selected").attr("code"));
		var payYear = $("#payYear" + i).val();

		if (empId == "0" || empId == null || empId == "" || empId == undefined)
			errorList.push("Please Enter Employee Id" + (i+1));
		if (payMonth == "0" || payMonth == null || payMonth == "" || payMonth == undefined)
			errorList.push("Please Select Pay Month" + (i+1));
		if (payYear == "0" || payYear == null || payYear == "" || payYear == undefined)
			errorList.push("Please Select Pay Year" + (i+1));
		if(parseInt(suppYear) <= parseInt(payYear)){
			if (suppMonthCode < payMonthCode)
				errorList.push("The Pay Month And Pay Year Should Be Less Than OR Equal To Supplementary Month And Supplementary Year for Sr. No." + " " + (i + 1));
		}
	});
	return errorList;
}


