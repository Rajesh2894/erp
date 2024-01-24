$(document).ready(function() {

	$("#frmMetadataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$( "#docActionDate" ).datepicker({
        dateFormat : 'dd/mm/yy',
    		changeMonth : true,
    		changeYear : true,
    		yearRange : "1900:2200",
    		maxDate: new Date()
    });
	
	var actionValue = $("#docRtrvlDays").val();
	$("#retrvlDays").hide();
	$("#retentionDayLbl").hide();
	$("#deleteDayLbl").hide();
	$("#noOfDays").hide();
	$("#docActDt").hide();
	/*If validation msg throws then show selected doc action*/
	var docActionValue = $('input[name=docAction]:checked').val();
	var actionType = $('input[name=docActType]:checked').val();
	
	if(docActionValue=='R'){
		if(actionType=='BD'){
			$("#noOfDays").show();
			$("#retentionDayLbl").show();
			$("#retrvlDays").show();
		}else if(actionType=='BDT'){
			$("#docActDt").show();
			$("#retrvlDays").show();
		}
	}else if(docActionValue=='D'){
		if(actionType=='BD'){
			$("#deleteDayLbl").show();
			$("#noOfDays").show();
		}else if(actionType=='BDT'){
			$("#docActDt").show();
		}
	}
	$(".actionType").change(
		function() {
		var docActValue = $('input[name=docAction]:checked').val();
		if (docActValue == "R") {
			/*By Days Wise*/
			if (this.value == 'BD') {
				$("#noOfDays").show();
				$("#retentionDayLbl").show();
				$("#docActDt").hide();
				$("#retrvlDays").show();
			/*By Date Wise*/
			} else if (this.value == 'BDT') {
				$("#docActDt").show();
				$("#noOfDays").hide();
				$("#retentionDayLbl").hide();
				$("#retrvlDays").show();
			}
			$("#deleteDayLbl").hide();
		} else if(docActValue == "D"){
			if (this.value == 'BD') {
				$("#deleteDayLbl").show();
				$("#noOfDays").show();
				$("#docActDt").hide();
			} else if (this.value == 'BDT') {
				$("#docActDt").show();
				$("#deleteDayLbl").hide();
				$("#noOfDays").hide();
			}
			$("#retrvlDays").hide();
			$("#retentionDayLbl").hide();
		}

	});
	
	$(".documentAction").change(
			function() {
			var docActType = $('input[name=docActType]:checked').val();
			if (docActType) {
				$("#deleteDayLbl").hide();
				$("#noOfDays").hide();
				$("#docActDt").hide();
				$("#retentionDayLbl").hide();
				$("#retrvlDays").hide();
				$(".actionType").prop('checked', false);
			}

		});

});

$(function() {	
	$("#deptId").change(
			function() {
		
				var divName = '.content-page';
				var deptId = $("#deptId").val();
				var requestData = {
					'deptId' : deptId
				}
				var ajaxResponse = __doAjaxRequest(
						'DocManagement.html?getMetadata', 'POST', requestData,
						false, 'html');
				$(divName).html(ajaxResponse);

			});
});

function saveForm(element) {
	return saveOrUpdateForm(element, "", 'AdminHome.html', 'saveform');
}
