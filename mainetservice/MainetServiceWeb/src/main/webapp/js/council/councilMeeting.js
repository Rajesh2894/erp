$(document)
		.ready(
				function() {

					var globalCommitteeTypeId = "";
					var globalAgendaId = "";

					$('#meetingDetails').hide();

					$('#createMeetingBtId').hide();

					$("#meetingDatatables").dataTable(
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

					$("#agendaDatatables").dataTable(
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
					$("#agendaProposalDatatables").dataTable(
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

					$("#membersDataTable").dataTable(
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

					$("#meetingHistorytable").dataTable(
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
					
					$("#fromDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								onClose : function(selectedDate) {
									$("#toDate").datepicker("option",
											"minDate", selectedDate);
								}
							});

					$("#toDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								onClose : function(selectedDate) {
									$("#fromDate").datepicker("option",
											"maxDate", selectedDate);
								}
							});

					/*
					 * $("#meetingDate").keyup(function(e) { if (e.keyCode != 8) {
					 * if ($(this).val().length == 2) {
					 * $(this).val($(this).val() + "/"); } else if
					 * ($(this).val().length == 5) { $(this).val($(this).val() +
					 * "/"); } } });
					 * 
					 * $('#meetingDate').datepicker({ dateFormat : 'dd/mm/yy',
					 * changeMonth : true, changeYear : true, yearRange :
					 * "1900:2200" });
					 */
					
					/*var minDate = $("#meetingDateTime").val();

					$('.meetingDateTimePicker').datetimepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : minDate,
						maxDate : '0',
					});
*/
					/*
					 * $('.datepicker').datepicker({ dateFormat : 'dd/mm/yy',
					 * changeMonth : true, changeYear : true, maxDate : '-0d',
					 * yearRange : "-100:-0" });
					 */

					// code functionality for checkbox check/uncheck
					// add multiple select / deselect functionality
					$("#selectall").click(function() {
						$('.case').prop('checked', this.checked);
						if (this.checked) {
							checkMemberExistDissolveDate();
						}
					});

					// if all checkbox are selected, check the selectall
					// checkbox and viceversa
					$(".case").change(function() {
						if ($(".case").length == $(".case:checked").length) {
							$("#selectall").prop("checked", "checked");
							checkMemberExistDissolveDate();
						} else {
							$("#selectall").removeProp("checked");
							checkMemberExistDissolveDate();
						}

					});

					/*
					 * if ($("#validationerrordiv").text().length > 0) {
					 * 
					 * meetingDetails(globalAgendaId, globalCommitteeTypeId);
					 * $('#meetingDetails').show(); }
					 */

					$('#searchCouncilMeeting')
							.click(
									function() {
										var errorList = [];
										var meetingTypeId = $('#meetingTypeId')
												.val();
										var meetingNumber = $('#meetingNo')
												.val();
										var fromDate = $('#fromDate').val();
										var toDate = $('#toDate').val();
										if (meetingTypeId != 0
												|| meetingNumber != ''
												|| fromDate != ''
												|| toDate != '') {
											/*
											 * var requestData =
											 * '&meetingTypeId=' + meetingTypeId +
											 * '&meetingNumber=' + meetingNumber +
											 * '&fromDate=' + fromDate +
											 * '&toDate=' + toDate;
											 */
											var requestData = '&meetingTypeId='
													+ meetingTypeId
													+ '&meetingNumber='
													+ meetingNumber
													+ '&fromDate=' + fromDate
													+ '&toDate=' + toDate;
											var table = $('#meetingDatatables')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();

											var ajaxResponse = __doAjaxRequest(
													'CouncilMeetingMaster.html?searchCouncilMeeting',
													'POST', requestData, false,
													'json');
											if (ajaxResponse.length == 0) {
												errorList
														.push(getLocalMessage("council.member.validation.grid.nodatafound"));
												displayErrorsOnPage(errorList);
												return false;
											}
							
											var result = [];
											$.each(
															ajaxResponse.councilMeetingMasterDtoList,
															function(index) {
																var obj = ajaxResponse.councilMeetingMasterDtoList[index];
																let meetingId = obj.meetingId;
																let meetingTypeName = obj.meetingTypeName;
																let meetingNo = obj.meetingNo;
																let meetingDateDesc = obj.meetingDateDesc;
																let meetingTime = obj.meetingTime;
																let meetingPlace = obj.meetingPlace;
																let meetingStatus = obj.meetingStatus;
																let actionBT = obj.actionBT;
																
																
																let action = '<div class="text-center">'
																	+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="showGridOption(\''
																	+ meetingId
																	+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
																	+ '<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-10"  onclick="showGridOption(\''
																	+ meetingId
																	+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
																	+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10"  onclick="showGridOption(\''
																	+ meetingId
																	+ '\',\'M\')"  title="Edit"><i class="fa fa-envelope"></i></button>'
																	+ '</div>'
																	
																	if(!actionBT){
																		action = '<div class="text-center">'
																			+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="showGridOption(\''
																			+ meetingId
																			+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
																			+ '<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-10"  onclick="showGridOption(\''
																			+ meetingId
																			+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
																			+ '</div>'
																	}
																	
																result.push([
																				'<div class="text-center">'
																						+ (index + 1)
																						+ '</div>',
																				'<div align="center">'
																						+ meetingTypeName
																						+ '</div>',
																				'<div align="center">'
																						+ meetingNo
																						+ '</div>',
																				'<div align="center">'
																						+ meetingDateDesc
																						+ '</div>',
																				'<div align="center">'
																						+ meetingTime
																						+ '</div>',
																				'<div align="center">'
																						+ meetingPlace
																						+ '</div>',
																				'<div align="center">'
																						+ meetingStatus
																						+ '</div>',
																						action
																				 ]);
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage("council.member.validation.select.any.field"));
											displayErrorsOnPage(errorList);
										}
									});

					// this search for agenda at meeting invitation
					$('#searchCouncilAgenda')
							.click(
									function() {
										var errorList = [];
										$("#agendaProposalDatatables tbody")
												.empty();
										let committeeTypeId = $(
												'#committeeTypeId').val();
										globalCommitteeTypeId = committeeTypeId;
										var agendaNo = $('#agendaNo').val();
										var fromDate = $('#agendaFromDate')
												.val();
										var toDate = $('#agendaToDate').val();
										if((committeeTypeId === undefined || committeeTypeId == null || committeeTypeId == 0) && (agendaNo == null || agendaNo === undefined || agendaNo==0)){
											errorList
											.push(getLocalMessage("council.member.validation.select.any.field"));
									        displayErrorsOnPage(errorList);
									        return false;
										}
										if (committeeTypeId != 0 
												|| agendaNo != ''
												|| fromDate != ''
												|| toDate != '') {
											var requestData = '&committeeTypeId='
													+ committeeTypeId
													+ '&agendaNo='
													+ agendaNo
													+ '&fromDate='
													+ fromDate
													+ '&toDate='
													+ toDate
													+ '&meetingInvitation='
													+ true;
											var table = $('#agendaDatatables')
													.DataTable();
											$("#agendaDatatables tbody")
													.empty();
											table.rows().remove().draw();
											$(".warning-div").hide();
											var ajaxResponse = __doAjaxRequest(
													'CouncilAgendaMaster.html?searchCouncilAgenda',
													'POST', requestData, false,
													'json');
											if (ajaxResponse.councilAgendaMasterDtoList.length == 0) {
												errorList
														.push(getLocalMessage("council.member.validation.grid.nodatafound"));
												displayErrorsOnPage(errorList);
												return false;
											}
											var result = [];
											var btnCreateMeeting = getLocalMessage("council.button.createMeeting");
											$
													.each(
															ajaxResponse.councilAgendaMasterDtoList,
															function(index) {
																var obj = ajaxResponse.councilAgendaMasterDtoList[index];
																let agendaId = obj.agendaId;
																let agendaNo = obj.agendaNo;
																let committeeType = obj.committeeType;
																let agendaStatus = obj.agendaStatus;
																let agenDate = obj.agenDate;
																let agendaDate = obj.agendaDate;
																let committeeTypeId = obj.committeeTypeId;
																var stringRequest = agendaId + "/"+ committeeTypeId + "/"+ agendaDate;
																result
																		.push([
																				'<div class="text-center">'
																						+ (index + 1)
																						+ '</div>',
																				'<div class="text-center">'
																						+ committeeType
																						+ '</div>',
																				'<div class="text-center">'
																						+ agenDate
																						+ '</div>',
																				'<div class="text-center">'
																						+ '<button type="button" class="btn btn-blue-2" name="button-plus" title="'+ btnCreateMeeting +'" id="button-plus" value="'+stringRequest+'" onclick="meetingDetails(this)" ><i class="fa fa-plus-circle padding-right-5" ></i>'+ btnCreateMeeting +'</button>'
																						+ '</div>' ]);
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage("council.member.validation.select.any.field"));
											displayErrorsOnPage(errorList);
										}
									});

				});

function addMeetingMaster(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function showGridOption(meetingId, action) {
	var actionData;
	var divName = formDivName;
	var requestData = 'meetingId=' + meetingId;
	if (action == "E") {
		actionData = 'editCouncilMeetingData';
		var ajaxResponse = doAjaxLoading('CouncilMeetingMaster.html?'
				+ actionData, requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		$('#createMeetingBtId').show();
		var agenda=$("#agenda-date").html();
		$('.meetingDateTimePicker').datetimepicker('destroy').datetimepicker({
			
			dateFormat : 'dd/mm/yy',
		    minDate: agenda,
		    changeMonth : true,
			changeYear : true,
			timeFormat : "HH:mm"
		   });
		globalCommitteeTypeId = "";
		prepareTags();
	}

	if (action == "V") {
		actionData = 'viewCouncilMeetingData';
		var ajaxResponse = doAjaxLoading('CouncilMeetingMaster.html?'
				+ actionData, requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	
	//for send msg
	if (action == "M") {
		requestData = '&meetingId=' + meetingId;	
		let response = __doAjaxRequest('CouncilMeetingMaster.html?sendMsg','POST', requestData, false, 'json');
		showMessageOnSubmit(response, 'CouncilMeetingMaster.html');
	}
}

//pop up msg display
function showMessageOnSubmit(message, redirectURL) {
	var errMsgDiv = '.msg-dialog-box';
	var cls = "Ok";

	var d = '<h4 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h4>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

function proceed() {
	window.location.href = "CouncilMeetingMaster.html";
}


function backMeetingMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CouncilMeetingMaster.html');
	$("#postMethodForm").submit();
}

// DISPLAY MEETING DETAILS
function meetingDetails(data) {
	//split data 
	var info = data.value.split('/');
	let agendaId = Number(info[0]);
	let committeeTypeId = info[1];
	var requestData = '&agendaId=' +agendaId  + '&committeeTypeId='+committeeTypeId ;
	var agendaDate = info[2];
	globalCommitteeTypeId = committeeTypeId;
	
	//convert date from yyyy-mm-dd to dd/mm/yy		
    agendaDate = agendaDate.replace(/(\d{4})-(\d{1,2})-(\d{1,2})/, function(match,y,m,d) { 
        return d + '/' + m + '/' + y;  
    });


$('.meetingDateTimePicker').datetimepicker('destroy').datetimepicker({
	
	dateFormat : 'dd/mm/yy',
    minDate: agendaDate,
    changeMonth : true,
	changeYear : true,
	timeFormat : "HH:mm"
   });
	// set committeeTypeId for validation in BACKEND at the time of create
	// meeting
	$("input[name=committeeTypeId]").val(committeeTypeId);
	var divName = '.content-page';
	// var ajaxResponse =
	// doAjaxLoading('CouncilMeetingMaster.html?getMeetingDetails', requestData,
	// 'html',divName);
	var ajaxResponse = __doAjaxRequest(
			'CouncilMeetingMaster.html?getMeetingDetails', 'POST', requestData,
			false, 'json');

	var errorList = [];
	var proposalTable = $('#agendaProposalDatatables').DataTable();
	$("#agendaProposalDatatables tbody").empty();
	proposalTable.rows().remove().draw();
	$(".warning-div").hide();
	if (ajaxResponse.councilProposalDto.length == 0) {
		errorList
				.push(getLocalMessage("council.meeting.proposal.grid.nodatafound"));
		displayErrorsOnPage(errorList);
		$("#agendaProposalDatatables tbody").empty();
		$('#createMeetingBtId').hide();
		return false;
	}
	var result = [];
	$.each(ajaxResponse.councilProposalDto, function(index) {
		var obj = ajaxResponse.councilProposalDto[index];
		let agendaId = obj.agendaId;
		let agendaNo = obj.agendaNo;
		let proposalDetails = obj.proposalDetails;
		let proposalNo = obj.proposalNo;
		let proposalDeptName = obj.proposalDeptName;

		result.push([ '<div class="text-center">' + (index + 1) + '</div>',
				'<div class="text-center">' + proposalDetails + '</div>',
				'<div class="text-center">' + agendaNo + '</div>',
				'<div class="text-center">' + proposalNo + '</div>',
				'<div class="text-center">' + proposalDeptName + '</div>' ]);

	});
	proposalTable.rows.add(result);
	proposalTable.draw();

	$('#agendaId').val(agendaId);
	$('#meetingDetails').show();
	$('#createMeetingBtId').show();

	// set data in membersDataTable

	var membersDataTable = $('#membersDataTable').DataTable();
	$("#membersDataTable tbody").empty();
	membersDataTable.rows().remove().draw();
	$(".warning-div").hide();
	if (ajaxResponse.memberList.length == 0) {
		errorList
				.push(getLocalMessage("council.meeting.memberCommittee.grid.nodatafound"));
		displayErrorsOnPage(errorList);
		$('#createMeetingBtId').hide();
		return false;
	}
	var membersResult = [];
	$
			.each(
					ajaxResponse.memberList,
					function(index) {
						var obj = ajaxResponse.memberList[index];
						let memberId = obj.memberId;
						let memberName = obj.memberName;
						let couMemberTypeDesc = obj.couMemberTypeDesc;
						/* let designation = obj.designation; */

						/*membersResult
								.push([
										'<div class="text-center">'
												+ '<input type="checkbox" class="case" name="case" onClick= "hitCheckBox(this)"  value="'
												+ memberId + '"></input>'
												+ '</div>',
										'<div class="text-center">'
												+ (index + 1) + '</div>',
										'<div class="text-center">'
												+ memberName + '</div>',
										'<div class="text-center">'
												+ couMemberTypeDesc + '</div>' ]);*/
						membersResult
						.push([
							'<div class="text-center">'
							+ (index + 1) + '  <input type="hidden" id="memberId'+index+'" value="'+memberId+'" /> </div>',
								'<div class="text-center">'
										+ memberName + '</div>',
								'<div class="text-center">'
										+ couMemberTypeDesc + '</div>' ]);

					});
	membersDataTable.rows.add(membersResult);
	membersDataTable.draw();

}

// script code for create meeting
function createMeeting(obj) {
	var checkComId = $("input[name=committeeTypeId]").val();
	var errorList = [];
	var rowCount = $('#agendaProposalDatatables >tbody >tr').length;
	var meetingTypeId = $("#meetingTypeId").val();
	var meetingDateTime = $("#meetingDateTime").val();
	// var meetingTime = $("#meetingTime").val();
	var meetingPlace = $("#meetingPlace").val();
	var agendaId = $("#agendaId").val();
	var meetingInvitationMsg = $("#meetingInvitationMsg").val();
	
	//User Story #72221	
	var prevMsg = $("#prevMessage").val();
	var prevDateTime=$("#prevMeetingDateDesc").val();
	var prevPlace=$("#prevMeetingPlace").val();
	var reason = $("#reason").val();
	var saveMode = $('#saveMode').val();
	// one more time check at final submit member in dissolve date or not
	var memberIds = [];
	checkMemberExistDissolveDate();
	$("#membersDataTable").DataTable().destroy();
	$("#membersDataTable tbody tr").each(function(i) {
		let memberId = $('#memberId'+i).val();
		memberIds.push(memberId);
	});
	$("#membersDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
				[ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	/*$('input[name="case"]:checked').each(function() {
		memberIds.push(this.value);
	});*/
	$('#memberIdByCommitteeType').val(memberIds);

	if (meetingTypeId == '' || meetingTypeId == '0') {
		errorList
				.push(getLocalMessage('council.meeting.validation.meetingType'));
	}
	if (meetingDateTime == '' || meetingDateTime == undefined) {
		errorList
				.push(getLocalMessage('council.meeting.validation.meetingDateTime'));
	}
	/*
	 * if (meetingTime == '' || meetingTime == undefined) {
	 * errorList.push(getLocalMessage('council.meeting.validation.meetingTime')); }
	 */
	if (meetingPlace == '' || meetingPlace == undefined) {
		errorList
				.push(getLocalMessage('council.meeting.validation.meetingPlace'));
	}
	if (agendaId == '' || agendaId == undefined) {
		errorList.push(getLocalMessage('council.meeting.validation.agenda'));
	}
	if (rowCount == 0) {
		errorList
				.push(getLocalMessage('council.meeting.proposal.grid.nodatafound'));
	}
	if (meetingInvitationMsg == '') {
		errorList
				.push(getLocalMessage('council.meeting.validation.msgInvitation'));
	}
	if (memberIds.length == 0) {
		errorList.push(getLocalMessage('council.meeting.validation.members'));
	}
	
	if(meetingDateTime != prevDateTime || meetingPlace != prevPlace || meetingInvitationMsg != prevMsg){
		if(saveMode = 'EDIT' && reason == ''){
			errorList.push(getLocalMessage('council.meeting.validity.reason'));
		}	
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var meetingInvitationMsgEncrpyt = encodeURIComponent(meetingInvitationMsg);
	    var requestDataForEncrypt = {
			'meetingMessage' : meetingInvitationMsgEncrpyt
		};
	    var ajaxResponse = __doAjaxRequest(
	    	    "CouncilMeetingMaster.html?encyptData", 'POST',
	    	    requestDataForEncrypt, false, 'json');
		
		return saveOrUpdateForm(obj, '',
				'CouncilMeetingMaster.html?PrintReport', 'saveform');
	}
}

function checkMemberExistDissolveDate() {
	let memberIds = [];

	let errorList = [];
	/*$('input[name="case"]:checked').each(function() {
		memberIds.push(this.value);
	});*/
	$("#membersDataTable tbody tr").each(function(i) {
		let memberId = $('#memberId'+i).val();
		memberIds.push(memberId);
	});

	if (memberIds.length > 0) {
		var requestData = '';
		if (globalCommitteeTypeId == undefined || globalCommitteeTypeId == "") {
			globalCommitteeTypeId = $('#committeeTypeId').val();
		}
		requestData = '&memberIds=' + memberIds.toString()
				+ '&committeeTypeId=' + globalCommitteeTypeId;
		let response = __doAjaxRequest(
				'CouncilMemberCommitteeMaster.html?memberExistInDissolveDate',
				'POST', requestData, false, 'json');
		if (response.length > 0) {
			displayErrorsOnPage(response);
		}

		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			return false;
		}
	} else {
		return false;
	}

}

// this is doing because CHECKBOX change not hitting when we append dataTable
// using table draw so creating function to achieve
function hitCheckBox() {
	$(".case").change(function() {
		if ($(".case").length == $(".case:checked").length) {
			$("#selectall").prop("checked", "checked");
			checkMemberExistDissolveDate();
		} else {
			$("#selectall").removeProp("checked");
			checkMemberExistDissolveDate();
		}

	});
}

function emptyForm(){
	$('input[type=text]').val('');  
	$('#committeeTypeId').val('0').trigger('chosen:updated');	
	$('#agendaDatatables tbody > tr').remove();
	$(".alert-danger").hide();
	$("#meetingMaster").validate().resetForm();
}

