$(document)
		.ready(
				function() {

		$('#rtiObjectionSummaryForm').validate({
			
			onkeyup : function(element) {
				this.element(element);
				console.log('onkeyup fired');
			},
			onfocusout : function(element) {
				this.element(element);
				console.log('onfocusout fired');
			}
		});				

		$("#objDetails").dataTable(
		{
			"oLanguage" : {
				"sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ],
					[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
		});

		$("#objDetailSearch").click(function()
		{
			
			var errorList = [];
			var objNo = $('#').val();
			var objDate = $('#').val();
			var ownerName = $('#').val();
			var objType = $('#').val();
			var objStatus = $('#').val();
			var action = $('#').val();
			
			if (objNo != ''|| objDate != '0' || ownerName != '0' || objType != '0' || objStatus != '0' || action !='0') {
				
				var requestData = 'objNo='+ objNo + '&objDate='+ objDate + '&ownerName=' + ownerName + '&objType=' + objType + '&objStatus=' + objStatus + '&action=' + action;
				var table = $('#objDetails').DataTable();
				table.rows().remove().draw();
				$(".warning-div").hide();
				
				var ajaxResponse = __doAjaxRequest(cdmURL+ '?filterCDMListData','POST', requestData, false,'json');
				
				var result = [];
				$.each(ajaxResponse,function(index) 
						{
									var obj = ajaxResponse[index];
									result.push([obj.objNo,obj.objDate,obj.ownerName,obj.objType,obj.objStatus,obj.action,
													'<td class="text-center">'
															+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="viewObjDetails(\''
															+ obj.groupId
															+ '\')" title="View Objection Details"><i class="fa fa-eye"></i></button>'
															+ '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="editObjDetails(\''
															+ obj.groupId
															+ '\')"  title="Edit Objection Details"><i class="fa fa-pencil"></i></button>'
															+ '</td>' ]);
								});
				table.rows.add(result);
				table.draw();
			} else {
				errorList
						.push(getLocalMessage('obj.validation.selAnyOnefiels'));
				displayErrorsOnPage(errorList);
			}
		});


});


function editObjDetails(groupId) {
	
	var requestData = 'groupId=' + groupId + '&type=E';
	
	var response = __doAjaxRequest(cdmURL + '?form', 'POST', requestData,
	false, 'html');
	
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(response);
}

function viewObjDetails(groupId) {
	
	var requestData = 'groupId=' + groupId + '&type=V';	
	var response = __doAjaxRequest(cdmURL + '?form', 'POST', requestData,false, 'html');
	$('.pagediv').removeClass('ajaxloader');	
	$('.pagediv').html(response);
}




