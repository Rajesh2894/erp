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
});


function searchWaterDetail(element) {

	var errorList = [];
	
	var connNo = $("#connectionNo").val();
	var csNme = $("#csFirstName").val();
	
	var div = $("#codDwzid1") != null && $("#codDwzid1").val() != '0' ? $("#codDwzid1").val() : ''; 
	var ward = $("#codDwzid2") != null && $("#codDwzid2").val() != '0' ? $("#codDwzid2").val() : ''; 
	var zone = $("#codDwzid3") != null && $("#codDwzid3").val() != '0' ? $("#codDwzid3").val() : ''; 
	

	if (connNo != '' || csNme != '' || (div != '' && ward != '' && zone != '')) {
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var table = $('#advertiserTable').DataTable();
		table.rows().remove().draw();

		var ajaxResponse = doAjaxLoading(
				'WaterDuesCheck.html?searchWaterDetails', requestData, 'html');

		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							result
									.push([
											obj.csCcn,
											obj.csName,
											obj.ward,
											obj.zone,
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
				.push(getLocalMessage("water.select.atleast.oneField.criteria2"));
		displayErrorsOnPage(errorList);
	}

}

function resetWaterDuesForm(element) {
		$("#WaterDuesCheck").submit();
}



function viewWaterDetail(connNo) {

	var divName = '.content-page';
	var requestData = {
		"connNo" : connNo
	};

	var ajaxResponse = doAjaxLoading('WaterDuesCheck.html?getWaterDetail',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function ViewBillDetails(bmid) {

	var data = {
		"bmIdNo" : bmid
	};
	var URL = 'ViewWaterDetails.html?viewWaterDet';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$("#viewWaterDetails").html(returnData);
}
