$(document).ready(function() {
	$("#advertiserTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$('#ViewWaterDetails').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
});

function searchWaterDetail(element) {
	debugger;

	var errorList = [];
	var propNo = $("#propertyNo").val();
	var connNo = $("#connectionNo").val();
	var csNme = $("#csFirstName").val();
	var mobileNo = $("#csMobileNo").val();
	var zone1 = $("#codDwzid1").val();

	if (propNo != '' || connNo != '' || csNme != '' || mobileNo != ''
			|| zone1 > 0) {
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var table = $('#advertiserTable').DataTable();
		table.rows().remove().draw();

		var ajaxResponse = doAjaxLoading(
				'ViewWaterDetails.html?searchWaterDetails', requestData, 'html');

		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							result
									.push([
											obj.propertyNo,
											obj.csCcn,
											obj.csName,
											obj.csContactno,
											obj.csAdd,
											'<td >'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 margin-left-5"  onclick="viewWaterDetail(\''
													+ obj.csCcn
													+ '\')" title="View"><i class="fa fa-eye"></i></button>' ]);

						});
		table.rows.add(result);
		table.draw();
		if (prePopulate.length == 0) {
			errorList.push(getLocalMessage("water.norecord.search.criteria"));
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();
		}
	} else {
		errorList
				.push(getLocalMessage("water.select.atleast.oneField.criteria"));
		displayErrorsOnPage(errorList);
	}

}

function ViewBillDetails(bmid) {
	debugger;
	var data = {
		"bmIdNo" : bmid
	};
	var URL = 'ViewWaterDetails.html?viewWaterDet';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$("#viewWaterDetails").html(returnData);
}

function BackToDetails() {
	var data = {};
	var URL = 'ViewWaterDetails.html?backToWaterDet';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(".content").html(returnData);
}

function downoladReceipt(reciptId, receiptNo) {
	var data = {
		"reciptId" : reciptId,
		"receiptNo" : receiptNo
	};
	var URL = 'ViewWaterDetails.html?receiptDownload';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	if (returnData != "") {
		var title = 'Revenue Receipt';
		var printWindow = window.open('', '_blank');
		printWindow.document.write('<html><head><title>' + title + '</title>');
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();
	}
}

function viewWaterDetail(connNo) {
	debugger;
	var divName = '.content-page';
	var requestData = {
		"connNo" : connNo
	};

	var ajaxResponse = doAjaxLoading('ViewWaterDetails.html?getWaterDetail',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

/*Birt report*/
function downoladBill(billno) {

	var requestData = {

		"billNo" : billno
	};

	var URL = 'ViewWaterDetails.html?getWaterBillBirtReport';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	window.open(returnData, '_blank');
}
