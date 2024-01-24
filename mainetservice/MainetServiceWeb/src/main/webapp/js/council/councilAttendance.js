var ATTENDANCE_URL = "CouncilAttendanceMaster.html";
$(document).ready(function() {
	
	$("#attendanceSummaryDataTables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$("#membersDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});	
	
	
	 $( "#fromDate" ).datepicker({
	        dateFormat : 'dd/mm/yy',
	    		changeMonth : true,
	    		changeYear : true,
	    		yearRange : "1900:2200",
	    		onClose: function( selectedDate ) {
	        $( "#toDate" ).datepicker( "option", "minDate", selectedDate );
	      }
	    });
	    
	    $( "#toDate" ).datepicker({
	    		dateFormat : 'dd/mm/yy',
	    		changeMonth : true,
	    		changeYear : true,
	    		yearRange : "1900:2200",
	    		onClose: function( selectedDate ) {
	        $( "#fromDate" ).datepicker( "option", "maxDate", selectedDate );
	      }
	    });	
	    
	  //code functionality for checkbox check/uncheck
		// add multiple select / deselect functionality
		$("#selectall").click(function () {
			  $('.case').prop('checked', this.checked);
			  /*if(this.checked){
				  checkMemberExistDissolveDate();
			  }*/
		});

		// if all checkbox are selected, check the selectall checkbox and viceversa
		$(".case").change(function(){
			if($(".case").length == $(".case:checked").length) {
				$("#selectall").prop("checked", "checked");
			} else {
				$("#selectall").removeProp("checked");
			}

		});
	
	$('#searchMeetingAttendance').click(function() {
		
		var errorList = [];
		var meetingTypeId = $('#meetingTypeId').val();
		var meetingNo = $('#meetingNo').val();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		
		//special validation for meetingNo if it has than skip for other field else mandatory or field
		/*if(meetingNo == ''){
			errorList.push(getLocalMessage("council.attendance.validation.select.all.field"));
			displayErrorsOnPage(errorList);
		}else{*/
			if ( meetingTypeId != 0  || meetingNo != '' || fromDate != '' || toDate != ''){
				var requestData = '&meetingTypeId=' + meetingTypeId + '&meetingNo=' + meetingNo + '&fromDate=' + fromDate +'&toDate=' +toDate;
				var table = $ ('#attendanceSummaryDataTables').DataTable();
				table.rows().remove().draw();
				$(".warning-div").hide();
				var ajaxResponse = __doAjaxRequest('CouncilAttendanceMaster.html?searchMeetingAttendance','POST', requestData, false,'json');
				if (ajaxResponse.councilMeetingMasterDtoList.length == 0 ) {
					errorList.push(getLocalMessage("council.member.validation.grid.nodatafound"));
					displayErrorsOnPage(errorList);
					return false;
				}
				var result = [];
				$.each(ajaxResponse.councilMeetingMasterDtoList,function(index) {			
					var obj = ajaxResponse.councilMeetingMasterDtoList[index];
					let meetingId= obj.meetingId;
					$('#meetingId').val(meetingId);
					let meetingDateDesc = obj.meetingDateDesc;
					let meetingTypeName = obj.meetingTypeName;
					let totalMember = obj.totalMember;
					let memberPresent = obj.memberPresent;
					let memberAbsent = obj.memberAbsent;
					let meetingStatus = obj.meetingStatus;
											
					result.push([ 
						'<div align="center">'+ (index + 1) + '</div>',
						'<div align="center">'+meetingDateDesc + '</div>',
						'<div align="center">'+meetingTypeName + '</div>',
						'<div align="center">'+totalMember + '</div>',
						'<div align="center">'+memberPresent + '</div>',
						'<div align="center">'+memberAbsent + '</div>',
						'<div align="center">'+meetingStatus + '</div>',
						'<div class="text-center">'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="showGridOption(\''
						+ meetingId
						+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
						+ '<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-10"  onclick="showGridOption(\''
						+ meetingId
						+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
				     	+ '</div>' ]);
				});
				table.rows.add(result);
				table.draw();
		}else{
			errorList.push(getLocalMessage("council.member.validation.select.any.field"));
			displayErrorsOnPage(errorList);
		}
	});
});

function addAttendanceMaster(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function showGridOption (meetingId,action){
	var actionData;
	var divName = formDivName;
	var requestData = 'meetingId=' + meetingId;
	if (action == "E") {
		actionData = 'editAttendanceMasterData';
		var ajaxResponse = doAjaxLoading('CouncilAttendanceMaster.html?' + actionData,
				requestData, 'html',divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	
	if (action == "V") {
		actionData = 'viewAttendanceMasterData';
		var ajaxResponse = doAjaxLoading('CouncilAttendanceMaster.html?' + actionData,
				requestData, 'html',divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function backAttendanceMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CouncilAttendanceMaster.html');
	$("#postMethodForm").submit();
}

function resetAttendanceForm(ele) {
	let actionParam = 'addMeetingAttendance';
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(ATTENDANCE_URL + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function getMeetingNos() {
	var meetingTypeId = $("#meetingTypeId").find("option:selected").val();
	$('#meetingId').html('');
	$('#meetingId').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	
	if(meetingTypeId!="" &&  meetingTypeId!=0){
		var requestData = {
				"meetingTypeId":meetingTypeId
			}
		var meetingNoList = __doAjaxRequest(ATTENDANCE_URL+'?getMeetingNo', 'POST', requestData, false,'json');
		$.each(meetingNoList, function(index, value) {
			 $('#meetingId').append($("<option></option>").attr("value",value.meetingId).attr("code",value.meetingNo).text(value.meetingNo));
		});
		 $('#meetingId').trigger('chosen:updated');	
	   }
}

function getMeetingMembers(obj) {
	var meetingId = $("#meetingId").find("option:selected").val();
	if(meetingId!="" &&  meetingId!=0){
		var requestData = {
				"meetingId":meetingId
			}
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var response = __doAjaxRequest(ATTENDANCE_URL+'?getMeetingMembers', 'POST', requestData, false,'html');
		$('.content').removeClass('ajaxloader');
		var divName = formDivName;
		$(divName).html(response);		
	 }
}
//script code for create agenda
function saveAttendence(obj,saveMode){
	var errorList = [];
	
	var memberIds=[];
	$('input[name="case"]:checked').each(function() {
		   memberIds.push(this.value);
		});
	$('#memberIdByCommitteeType').val(memberIds);
	$('#saveMode').val(saveMode);
	
	//	var meetingId  = $("#meetingId").val();
	var meetingTypeId  = $("#meetingTypeId").val();
	
	//	validation for save form submit
	if (meetingTypeId == '' || meetingTypeId == '0') {
		errorList.push(getLocalMessage('council.meeting.validation.meetingType'));
	}
	if(memberIds.length == 0){
		errorList.push(getLocalMessage('council.meeting.validation.members'));
	}
	
	if (errorList.length > 0) {
		//display error msg
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'CouncilAttendanceMaster.html', 'saveform');
	}
}
