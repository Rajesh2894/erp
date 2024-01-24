var mrmURL = 'IssuanceCounter.html';
$(document).ready(
		function() {

			//$('#issueBT').prop('disabled', true);
		  
			 $("#issuanceCounterTB").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 10,
				"bInfo" : true,
				"lengthChange" : true,
			
				"aoColumnDefs": [{
					'bSortable': false,
					'aTargets': [ 0 ]
				}],
				"columns" :[
					null,null,null,null,null,null,null,{'visible' :false}
				]
			});
	
			
			
			var reuqestData={
					"status" : "CLOSED"
			}	
			showloader(true);	
			var ajaxResponse = __doAjaxRequest("IssuanceCounter.html?getIssuanceDataList", 'POST',reuqestData, false,'json');
			if (ajaxResponse.length > 0) {
			var table = $('#issuanceCounterTB').on('draw.dt', function () {
				SelectAllCheck();
	        }).DataTable();
			table.rows().remove().draw();
			var result = [];
				$.each(ajaxResponse,function(index) {
				var obj = ajaxResponse[index];
				
					result.push([
						'<div class="text-center">'
						+ '<input type="checkbox" class="issue" name="issue" onClick= "hitCheckBox(this)"  value="'
						+ obj.applicationId + '~'+ obj.type + '' + "," + '"></input>'
						+ '</div>',
						
						'<div align="center">'
						+ obj.applicationId
						+ '</div>',
						
						'<div align="center">'
						+ obj.type
						+ '</div>',
						
						'<div align="center">'
						+'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span>'+obj.requestDate
						+ '</div>',
										
						'<div align="center">'
						+ obj.actorId
						+ '</div>',
						'<div align="center">'
						+ obj.serviceName
						+ '</div>',
						'<div align="center">'
						+ obj.deptName
						+ '</div>',
						
						'<div align="center">'
						+
						'</div>'
						
						//obj.serviceEventName,
						//obj.taskStatus
						]);
				});
				table.rows.add(result);
				table.draw();
			}
		
			
			$("#selectall").click(function () {
				  $('.issue').prop('checked', this.checked);
			});
			
			// if all checkbox are selected, check the selectall checkbox and viceversa
			$(".issue").change(function(){
				if($(".issue").length == $(".issue:checked").length) {
					$("#selectall").prop("checked", "checked");
				} else {
					$("#selectall").removeProp("checked");
				}
			});
			
			
		});



function closeErrBox() {
	$('.warning-div').addClass('hide');
}

// Select All checkbox refresh when click on next page
var SelectAllCheck = function (){
if($('.issue').is(':checked')){
	if($('.issue:checked').length == $('.issue').length){
		$('#selectall').prop('checked',true);
	}else{
		$('#selectall').prop('checked',false);
	}
}else{
	$('#selectall').prop('checked',false);
}

}



function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$(".warning-div").show();
	errorList = [];
	return false;
}

function triggerDatatable() {
	$("#dtCdmForm").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"lengthChange" : true,
		"scrollCollapse" : true,
		"bSort" : false
	}).fnPageChange('last');
}


function hitCheckBox() {
	$(".issue").change(function() {
		if ($(".issue").length == $(".issue:checked").length) {
			$("#selectall").prop("checked", "checked");
		} else {
			$("#selectall").removeProp("checked");
		}

	});
}

function saveIssuanceCounter(obj){
	var errorList = [],applicationIds=[];
	$('input[name="issue"]:checked').each(function() {
		applicationIds.push(this.value);
		});
	$('#applicationSelected').val(applicationIds);
	
	
	if(applicationIds.length == 0){
		errorList.push(getLocalMessage('issuance.record.selection.required'));
	}
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'IssuanceCounter.html', 'saveform');
	}
}

function getIssuedTaskList() {
	$('#issuanceCounterTB').DataTable().clear().destroy();
	$("#issuanceCounterTB").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"lengthChange" : true,
		
		"aoColumnDefs": [{
			'bSortable': false,
			'aTargets': [ 0 ]
		}]
		
	});
	var reuqestData={
			"status" : "CLOSED"
	}	
	showloader(true);
	var issuedFlag = $("#issuedFlag").val();
	var ajaxResponse = __doAjaxRequest("IssuanceCounter.html?getIssuedDataList", 'POST',reuqestData, false,'json');

	if (ajaxResponse.length > 0) {

	var table = $('#issuanceCounterTB').DataTable();
	table.rows().remove().draw();
	var result = [];
		$.each(ajaxResponse,function(index) {
		var obj = ajaxResponse[index];
		if (obj.flag == 'Y') {
			$('.issueBT').prop('disabled', true);
			$('#selectall').prop('disabled', true);	
		}
			result.push([
				'<div class="text-center">'
				+ '<input type="checkbox" class="issue" name="issue" onClick= "hitCheckBox(this)"  disabled="true" value="'
				+ obj.applicationId + '"></input>'
				+ '</div>',
				
				'<div align="center">'
				+ obj.applicationId
				+ '</div>',
				
				'<div align="center">'
				+ obj.type
				+ '</div>',
				
				'<div align="center">'
				+'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span>'+obj.requestDate
				+ '</div>',
				'<div align="center">'
				+ obj.actorId
				+ '</div>',
				'<div align="center">'
				+ obj.serviceName
				+ '</div>',
				'<div align="center">'
				+ obj.deptName
				+ '</div>',
				
				'<div align="center">'
				+'<span style="display:none">'+moment(obj.issuedDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span>'+obj.issuedDate
				+ '</div>'
				]);
		});
		table.rows.add(result);
		table.draw();
	
	}
}

function getPendingTaskList() {

	$('#issuanceCounterTB').DataTable().clear().destroy();
	$("#issuanceCounterTB").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"lengthChange" : true,
		
		"aoColumnDefs": [{
			'bSortable': false,
			'aTargets': [ 0 ]
		}],
		"columns" :[
			null,null,null,null,null,null,null,{'visible' :false}
		]
		
	});
	var reuqestData={
			"status" : "CLOSED"
	}	
	showloader(true);
	
	var ajaxResponse = __doAjaxRequest("IssuanceCounter.html?getIssuanceDataList", 'POST',reuqestData, false,'json');
	
	if (ajaxResponse.length > 0) {
	var table = $('#issuanceCounterTB').DataTable();
	table.rows().remove().draw();
	var result = [];
		$.each(ajaxResponse,function(index) {
		var obj = ajaxResponse[index];
		if (obj.flag != 'Y') {
			$('.issueBT').prop('disabled', false);
			$('#selectall').prop('disabled', false);	
		}
			result.push([
				'<div class="text-center">'
				+ '<input type="checkbox" class="issue" name="issue" onClick= "hitCheckBox(this)"  value="'
				+ obj.applicationId + '~'+ obj.type + '' + "," + '"></input>'
				+ '</div>',
				
				'<div align="center">'
				+ obj.applicationId
				+ '</div>',
				
				'<div align="center">'
				+ obj.type
				+ '</div>',
				
				'<div align="center">'
				+'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span>'+obj.requestDate
				+ '</div>',
		
				'<div align="center">'
				+ obj.actorId
				+ '</div>',
				'<div align="center">'
				+ obj.serviceName
				+ '</div>',
				'<div align="center">'
				+ obj.deptName
				+ '</div>',
				
				'<div align="center">'
				+
				'</div>'
				]);
		});
		table.rows.add(result);
		table.draw();
	}
	
}