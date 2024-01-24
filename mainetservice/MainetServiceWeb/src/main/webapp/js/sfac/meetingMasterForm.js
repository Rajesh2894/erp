var removeMomDetArray = [];
var removeMemberDetArray = [];
var removeAgendaDetArray = [];

$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2200",
		maxDate : 0

	});

	$('.meetingDateTimePicker').datetimepicker('destroy').datetimepicker({

		dateFormat : 'dd/mm/yy',
		minDate : 0,
		changeMonth : true,
		changeYear : true,
		timeFormat : "HH:mm"
	});

	$("#meetingDatatable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$("#momDataTable").dataTable({
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

});

function searchForm(obj) {

	var errorList = [];
	var msg = '';
	var meetingTypeId = $('#meetingTypeId').val();
	var meetingNumber = $('#meetingNo').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var divName = '.content-page';
	if (meetingTypeId == 0 && meetingNumber == '') {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
			"meetingTypeId" : meetingTypeId,
			"meetingNumber" : meetingNumber
		};
		var table = $('#meetingDatatable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'MeetingMasterForm.html?searchMeetingDetails', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);
		if (prePopulate.length == 0) {
			errorList.push(getLocalMessage("sfac.validation.grid.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			var result = [];
			$
					.each(
							prePopulate,
							function(index) {
								var dto = prePopulate[index];

								result
										.push([
												'<div align="center">'
														+ (index + 1)
														+ '</div>',
												'<div align="center">'
														+ dto.meetingTypeName
														+ '</div>',
												'<div align="center">'
														+ dto.meetingNo
														+ '</div>',
												'<div align="center">'
														+ dto.meetingDateDesc
														+ '</div>',
												'<div align="center">'
														+ dto.meetingTime
														+ '</div>',
												'<div align="center">'
														+ dto.meetingPlace
														+ '</div>',

												'<div class="text-center">'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="showGridOption(\''
														+ dto.meetingId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ msg
														+ '<button type="button" class="btn btn-warning btn-sm btn-sm" onclick="showGridOption(\''
														+ dto.meetingId
														+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>',
												'</div>' ]);
							});
			table.rows.add(result);
			table.draw();
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function addMeetingMaster(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);	
	$('.comMemberList').hide();
	prepareTags();
}

function showGridOption(meetingId, formMode) {
	var divName = formDivName;
	var url = "MeetingMasterForm.html?editAndViewForm";
	data = {
		"meetingId" : meetingId,
		"formMode" : formMode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	var type = $("#meetingTypeId").find("option:selected").attr('code');
	if (type == 'SLCC' || type == 'DMC'){
		$('.comMemberList').show();
		$('.participants').hide();
		}else{
			$('.participants').show();
			$('.comMemberList').hide();
		}
	
	prepareTags();

	$('.meetingDateTimePicker').datetimepicker('destroy').datetimepicker({

		dateFormat : 'dd/mm/yy',
		minDate : 0,
		changeMonth : true,
		changeYear : true,
		timeFormat : "HH:mm"
	});

}
// script code for create meeting
function createMeeting(obj) {
	var errorList = [];
	var meetingTypeId = $("#meetingTypeId").val();
	var meetingDateTime = $("#meetingDateTime").val();
	var meetingPlace = $("#meetingPlace").val();
	var meetingInvitationMsg = $("#meetingInvitationMsg").val();
	var convenerofMeeting = $("#convenerofMeeting").val();
	var remark = $("#remark").val();

	if (meetingTypeId == '' || meetingTypeId == '0') {
		errorList.push(getLocalMessage('sfac.meeting.validation.meetingType'));
	}
	if (meetingDateTime == '' || meetingDateTime == undefined) {
		errorList
				.push(getLocalMessage('sfac.meeting.validation.meetingDateTime'));
	}

	if (meetingPlace == '' || meetingPlace == undefined) {
		errorList.push(getLocalMessage('sfac.meeting.validation.meetingPlace'));
	}

	if (remark == '') {
		errorList.push(getLocalMessage('sfac.meeting.validation.remark'));
	}

	if (convenerofMeeting == '' || convenerofMeeting == undefined
			|| convenerofMeeting == '0') {
		errorList
				.push(getLocalMessage('sfac.meeting.validation.convenerofMeeting'));
	}

	errorList = errorList.concat(validateMOMDetailsTable());
	errorList = errorList.concat(validateMemeberDetailsTable());

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, 'Meeting Created Successfully !',
				'MeetingMasterForm.html?PrintReport', 'saveform');
	}
}

function backMeetingMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'MeetingMasterForm.html');
	$("#postMethodForm").submit();
}

function addMOMDetails(obj) {
	var errorList = [];
	errorList = validateMOMDetailsTable(errorList);
	if (errorList.length == 0) {
		var content = $('#momDataTable tr').last().clone();
		$('#momDataTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		reordeMOMDetailsTable();
		//content.find("select:eq(0), select:eq(1), select:eq(2)").chosen().trigger("chosen:updated");
	} else {
		displayErrorsOnPage(errorList);
	}
}
$('#momDataTable').on(
		"click",
		'.deleteMOMDetails',
		function(e) {

			/*
			 * var errorList = []; var rowLength = $('#momDataTable tr').length;
			 * if ($("#momDataTable tr").length != 2) {
			 * $(obj).parent().parent().remove(); reordeMOMDetailsTable();
			 * rowLength--; } else { errorList.push("You cannot delete first
			 * row"); displayErrorsOnPage(errorList); }
			 */

			var errorList = [];
			var count = 0;
			$('.momDetails').each(function(i) {
				count += 1;
			});
			var rowCount = $('#momDataTable tr').length;
			if (rowCount <= 2) {
				return false;
			}
			$(this).parent().parent().remove();
			var momDId = $(this).parent().parent().find(
					'input[type=hidden]:first').attr('value');
			if (momDId != '') {
				removeMomDetArray.push(momDId);
			}
			$('#removeMomDetIds').val(removeMomDetArray);
			reordeMOMDetailsTable();

		});

function reordeMOMDetailsTable() {
	$("#momDataTable tbody tr").each(
			function(i) {
				// Id

				$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
				$(this).find('input:text:eq(1)').attr('id', 'momComments' + i);
				$(this).find('select:eq(0)').attr('id', 'actionowner' + i);

				$(this).find("input:text:eq(1)").attr("name",
						"masterDto.meetingMOMDto[" + i + "].momComments");
				$(this).find('select:eq(0)').attr("name",
						"masterDto.meetingMOMDto[" + i + "].actionowner");

				$("#sNo" + i).val(i + 1);
			});
}

function validateMOMDetailsTable(errorList) {
	var errorList = [];
	var rowCount = $('#momDataTable tr').length;

	if (errorList == 0)
		$("#momDataTable tbody tr")
				.each(
						function(i) {
							if (rowCount <= 3) {

								var momComments = $("#momComments" + i).val();
								var actionowner = $("#actionowner" + i).val();
								var constant = 1;
							} else {
								var momComments = $("#momComments" + i).val();
								var actionowner = $("#actionowner" + i).val();
								var constant = i + 1;
							}

							if (momComments == undefined || momComments == "") {
								errorList
										.push(getLocalMessage("sfac.validation.momComments")
												+ " " + (i + 1));
							}
							if (actionowner == '0' || actionowner == undefined
									|| actionowner == "") {
								errorList
										.push(getLocalMessage("sfac.validation.actionowner")
												+ " " + (i + 1));
							}

						});

	return errorList;
}

function addMemberDetails(obj) {
	var errorList = [];
	errorList = validateMemeberDetailsTable(errorList);
	if (errorList.length == 0) {
		var content = $('#membersDataTable tr').last().clone();
		$('#membersDataTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		reordeMemeberDetailsTable();
		//content.find("select:eq(0), select:eq(1), select:eq(2)").chosen().trigger("chosen:updated");
	} else {
		displayErrorsOnPage(errorList);
	}
}
$('#membersDataTable').on(
		"click",
		'.deleteMemberDetails',
		function(e) {

			/*
			 * var errorList = []; var rowLength = $('#membersDataTable
			 * tr').length; if ($("#membersDataTable tr").length != 2) {
			 * $(obj).parent().parent().remove(); reordeMemeberDetailsTable();
			 * rowLength--; } else { errorList.push("You cannot delete first
			 * row"); displayErrorsOnPage(errorList); }
			 */

			var errorList = [];
			var count = 0;
			$('.memberDetails').each(function(i) {
				count += 1;
			});
			var rowCount = $('#membersDataTable tr').length;
			if (rowCount <= 2) {
				return false;
			}
			$(this).parent().parent().remove();
			var memDId = $(this).parent().parent().find(
					'input[type=hidden]:first').attr('value');
			if (memDId != '') {
				removeMemberDetArray.push(memDId);
			}
			$('#removeMemberIds').val(removeMemberDetArray);
			reordeMemeberDetailsTable();
		});

function reordeMemeberDetailsTable() {
	$("#membersDataTable tbody tr").each(
			function(i) {
				// Id

				$(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
				$(this).find('input:text:eq(1)').attr('id', 'memberName' + i);
				$(this).find('select:eq(0)').attr('id', 'dsgId' + i);
				$(this).find('input:text:eq(2)').attr('id', 'organization' + i);

				$(this).find("input:text:eq(1)").attr("name",
						"masterDto.meetingDetailDto[" + i + "].memberName");
				$(this).find('select:eq(0)').attr("name",
						"masterDto.meetingDetailDto[" + i + "].dsgId");
				$(this).find("input:text:eq(2)").attr("name",
						"masterDto.meetingDetailDto[" + i + "].organization");

				$("#sequence" + i).val(i + 1);
			});
}

function validateMemeberDetailsTable(errorList) {
	var errorList = [];
	var rowCount = $('#membersDataTable tr').length;

	if (errorList == 0)
		$("#membersDataTable tbody tr")
				.each(
						function(i) {
							if (rowCount <= 3) {

								var memberName = $("#memberName" + i).val();
								var dsgId = $("#dsgId" + i).val();
								var constant = 1;
								var organization = $("#organization" + i).val();
								
								var type = $("#meetingTypeId").find("option:selected").attr('code');
								
							} else {
								var memberName = $("#memberName" + i).val();
								var dsgId = $("#dsgId" + i).val();
								var constant = i + 1;
								var type = $("#meetingTypeId").find("option:selected").attr('code');
								var organization = $("#organization" + i).val();
							}
							if (type != 'SLCC' && type != 'DMC'){
							if (memberName == undefined || memberName == "") {
								errorList
										.push(getLocalMessage("sfac.validation.memberName")
												+ " " + (i + 1));
							}
							if (dsgId == '0' || dsgId == undefined
									|| dsgId == "") {
								errorList
										.push(getLocalMessage("sfac.validation.dsgId")
												+ " " + (i + 1));
							}
							if (organization == '0' || organization == undefined
									|| organization == "") {
								errorList.push(getLocalMessage("sfac.validation.organization")
												+ " " + (i + 1));
							}
							}

						});

	return errorList;
}

function getCommitteeMemDet(){
	var type = $("#meetingTypeId").find("option:selected").attr('code');
	var meetingTypeId = $("#meetingTypeId").val();
	if (type == 'SLCC' || type == 'DMC'){
		$('.comMemberList').show();
		$('.participants').hide();
		var table = $('#committeeMemDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var url = "MeetingMasterForm.html?getCommitteeMemDet";
		data = {
			"meetingTypeId" : meetingTypeId
		};
		var response = __doAjaxRequest(url, 'post', data,false, 'json');
		//var prePopulate = JSON.parse(response);

		var result = [];
		$
				.each(
						response,
						function(index) {
							var dto = response[index];
							let comMemberId = dto.comMemberId;
							let memberName = dto.memberName;
							let designation = dto.designation;
							let organization = dto.organization;
							result
									.push([
										'<div class="text-center">'
										+ (index + 1) + '  <input type="hidden" id="memberId'+index+'" value="'+comMemberId+'" /> </div>',
											'<div align="center">'
													+ dto.memberName + '</div>',
											'<div align="center">'
													+ dto.designation + '</div>',
											'<div align="center">'
													+ dto.organization + '</div>']);
						});
		table.rows.add(result);
		table.draw();
	}
	else{
		$('.participants').show();
		$('.comMemberList').hide();
	}
}


function addAgendaDetails(obj) {
	var errorList = [];
	//errorList = validateAgendaDetailsTable(errorList);
	if (errorList.length == 0) {
		var content = $('#agendaDataTable tr').last().clone();
		$('#agendaDataTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		reordeAgendaDetailsTable();
		//content.find("select:eq(0), select:eq(1), select:eq(2)").chosen().trigger("chosen:updated");
	} else {
		displayErrorsOnPage(errorList);
	}
}
$('#agendaDataTable').on(
		"click",'.deleteAgendaDetails',
		function(e) {
			var errorList = [];
			var count = 0;
			$('.agendaDetails').each(function(i) {
				count += 1;
			});
			var rowCount = $('#agendaDataTable tr').length;
			if (rowCount <= 2) {
				return false;
			}
			$(this).parent().parent().remove();
			var agenDId = $(this).parent().parent().find(
					'input[type=hidden]:first').attr('value');
			if (agenDId != '') {
				removeAgendaDetArray.push(agenDId);
			}
			$('#removeAgendaIds').val(removeAgendaDetArray);
			reordeAgendaDetailsTable();

		});

function reordeAgendaDetailsTable() {
	$("#agendaDataTable tbody tr").each(
			function(i) {
				// Id

				$(this).find("input:text:eq(0)").attr("id", "srNo" + (i));
				$(this).find('input:text:eq(1)').attr('id', 'agendaDesc' + i);

				$(this).find("input:text:eq(1)").attr("name",
						"masterDto.meetinAgendaDto[" + i + "].agendaDesc");

				$("#srNo" + i).val(i + 1);
			});
}