$(document)
		.ready(
				function() {
					/* This method is used to Search All Proposal */
					$("#commiteeDatatables").dataTable(
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

					$('#MemberData').dataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 10, 20, 30, -1 ],
										[ 10, 20, 30, "All" ] ],
								"iDisplayLength" : 10,
								"bInfo" : true,
								"bStateSave" : true,
								"lengthChange" : true,
								"scrollCollapse" : true,
								"bSort" : false
							});

		
					
					$('#dissolveDate').datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								onSelect: function(selected) {
									$("#toDate").datepicker("option","maxDate", selected);
									$("#fromDate").datepicker("option","maxDate", selected);
							      }
							});

					$("#fromDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								maxDate : 0,
								onSelect: function(selected) {
									$("#toDate").datepicker("option","minDate", selected);
									$("#dissolveDate").datepicker("option","minDate", selected);
							      } 
							});

					$("#toDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								onSelect: function(selected) {
									$("#fromDate").datepicker("option","maxDate", selected);
									$("#dissolveDate").datepicker("option","minDate", selected);
							      } 
							});

					// this code is for set date based on dissolve date in EDIT
					// case
					if ($("#saveMode").val() == "EDIT") {
						let toDate = $("#toDate").val();
						let fromDate = $("#fromDate").val();
						$("#toDate").datepicker("option", "maxDate", toDate);
						$("#fromDate").datepicker("option", "maxDate", toDate);
					}

					$('#searchCommitteeMember')
							.click(
									function() {

										var errorList = [];
										var memberId = $('#memberId').val();
										var committeeTypeId = $(
												'#committeeTypeId').val();

										if (memberId != 0
												|| committeeTypeId != ''
												|| committeeTypeId != 0) {
											var requestData = '&memberId='
													+ memberId
													+ '&committeeTypeId='
													+ committeeTypeId;
											var table = $('#commiteeDatatables')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											
											var ajaxResponse = __doAjaxRequest(
													'CouncilMemberCommitteeMaster.html?searchCommitteeMember',
													'POST', requestData, false,
													'json');
											if (ajaxResponse.length == 0) {
												errorList
														.push(getLocalMessage("council.member.validation.grid.nodatafound"));
												displayErrorsOnPage(errorList);
												return false;
											}
											var result = [];
											$
													.each(
															ajaxResponse.councilMemberCommitteeMasterDtoList,
															function(index) {
																var obj = ajaxResponse.councilMemberCommitteeMasterDtoList[index];
																let committeeTypeId = obj.committeeTypeId;
																let memberName = obj.memberName;
																let couMemberTypeDesc = obj.couMemberTypeDesc;
																let elecWardDesc = obj.elecWardDesc;
																let partyAFFDesc = obj.partyAFFDesc;
																let committeeType = obj.committeeType;
																result
																		.push([
																				'<div align="center">'
																						+ (index + 1)
																						+ '</div>',
																				'<div align="center">'
																						+ committeeType
																						+ '</div>',
																				'<div align="center">'
																						+ memberName
																						+ '</div>',
																				'<div align="center">'
																						+ couMemberTypeDesc
																						+ '</div>',
																				'<div align="center">'
																						+ elecWardDesc
																						+ '</div>',
																				/*
																				 * '<div
																				 * align="center">' +
																				 * partyAFFDesc + '</div>'
																				 */
																				'<div class="text-center">'
																						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="showGridOption(\''
																						+ committeeTypeId
																						+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
																						+ '<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-10"  onclick="showGridOption(\''
																						+ committeeTypeId
																						+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
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

					// This is doing because when click on add by default data
					// display of member so set empty initially At the time of
					// Add case
					if ($('#saveMode').val() == 'ADD') {
						$('#couMemberTypeDesc0').val("");
						$('#elecWardDesc0').val("");
						$('#partyAFFDesc0').val("");
						$('#comDsgId0').val("");
						
					}
				});

var varCouMemMasterDtoList;
var varDesignationList;

function addCommitteeMaster(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function addEntryData() {
	var rowCount = $('#MemberData >tbody >tr').length;
	var definedMemberCount = $('#otherField').val();
	var errorList = [];

			let table = $('#MemberData').dataTable();
			let tableSize = -1;
			let memberListNo = -1;
			if (varCouMemMasterDtoList == undefined) {
				varCouMemMasterDtoList = __doAjaxRequest(
						'CouncilMemberCommitteeMaster.html?getMembers', 'POST',
						{}, false, 'json');
				memberListNo = varCouMemMasterDtoList.length;
				// Get the total rows
				tableSize = table.fnGetData().length;

			} else {
				memberListNo = varCouMemMasterDtoList.length;
				tableSize = table.fnGetData().length;
			}

			if (memberListNo == tableSize) {
				// show error MSG
				errorList
						.push(getLocalMessage('council.committee.member.allMemberSelected'));
				displayErrorsOnPage(errorList);
				return false;
			}

			errorList = validateEntryDetails(errorList);
			if (errorList.length == 0) {
				addTableRow('MemberData');
			} else {
				$('#MemberData').DataTable();
				displayErrorsOnPage(errorList);
			}
}
function validateEntryDetails(errorList) {

	let tempArrayIds = [];
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	$("#MemberData tbody tr")
			.each(
					function(i) {
					
						let position = " at sr no. " + (i + 1);
						let memberId = $('#couMemId' + i).val();
						let expiryReason = $('#expiryReason' + i).val();
						let expiryDate = $('#expiryDate' + i).val();
						let comDsgId = $('#comDsgId' + i).val();
						var today = new Date();
						//ExpiryDate should be greater than current Date
						 var expiryDates = moment(expiryDate, "DD.MM.YYYY HH.mm").toDate();
							
							var todays=  moment(today).toDate();
							
							if (expiryDates.getTime() < todays.getTime()) {
								errorList.push(getLocalMessage("council.committee.validation.expirydates")+position);								
							}
						if (expiryDate != '' && expiryDate != undefined) {
                            //when expiry date is entered then add expiry reason also for that
							if (expiryReason == '' || expiryReason == undefined) { 
								errorList
										.push(getLocalMessage('council.committee.validation.expiryreason') +position);
							}
						}

						if (expiryReason != '' && expiryReason != undefined) {
							if (expiryDate == '' || expiryDate == undefined) {
								errorList
										.push(getLocalMessage('council.committee.validation.expirydate') +position);
							}
						}
						
						if (memberId == "0" || memberId == undefined
								|| memberId == "") {
							errorList
									.push(getLocalMessage("council.committee.member.notSelect")
											+ position);
						} 
						if (comDsgId == "0" || comDsgId == undefined
								|| comDsgId == "") {
							errorList
									.push(getLocalMessage("council.select.committee.designation")
											+ position);
						} 
						if ((expiryDate != '' && expiryDate != undefined)
							&& (compareDate(expiryDate) < compareDate(fromDate) || compareDate(expiryDate) > compareDate(toDate))) {
						errorList
								.push(getLocalMessage('council.committee.validation.expirydate.check')+position);
						
					}else {
						tempArrayIds.push(memberId);
					}

					});

	var sorted_arr = tempArrayIds.sort();
	var results = [];
	for (var i = 0; i < sorted_arr.length - 1; i++) {
		if (sorted_arr[i + 1] == sorted_arr[i]) {
			results.push(sorted_arr[i]);
		}
	}
	if (results.length > 0) {
		// show err MSG
		errorList
				.push(getLocalMessage("council.committee.member.duplicateMember"));
	}
	return errorList;

}
function deleteEntry(obj, ids) {
	// var deleteMemId =
	// $(obj).closest('tr').find('input[type=hidden]:last').attr('value');
	// below code is also work for getting deletedMemId
	/*
	 * var indexNo = $(obj).closest('tr').index(); var deleMemId=
	 * $('#deleteMemId'+indexNo).val();
	 */
	// get table row number
	var rowCount = $('#MemberData >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("council.validation.delete.entry"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}

	deleteTableRow('MemberData', obj, ids);
	$('#MemberData').DataTable().destroy();
	triggerTable();
}

function backCommitteeMasterForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CouncilMemberCommitteeMaster.html');
	$("#postMethodForm").submit();
}

function resetCommitteeMaster(resetBtn) {
	let actionParam = 'addCommittee';
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading("CouncilMemberCommitteeMaster.html?"
			+ actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function setMemberData(element) {	
	var couMemId = $("#" + element).val();
	var lookUpId = '';
	var index = element.substring("couMemId".length);
	let errorList = [];

	if (varCouMemMasterDtoList == undefined) {
		varCouMemMasterDtoList = __doAjaxRequest(
				'CouncilMemberCommitteeMaster.html?getMembers', 'POST', {},
				false, 'json');
	}
	var lookUps = __doAjaxRequest('CouncilMemberCommitteeMaster.html?getDesignations', 'POST',{}, false, 'json');
	
	for(let i=0; i< lookUps.length;i++){
		if(lookUps[i].lookUpCode == 'P'){
			lookUpId =lookUps[i].lookUpId; 
			break;
		}
	}

	/*
	 * if (varDesignationList == undefined) { varDesignationList =
	 * __doAjaxRequest( 'CouncilMemberCommitteeMaster.html?getDesignations',
	 * 'POST', {}, false, 'json'); }
	 */
	var couMem = findMember(couMemId, varCouMemMasterDtoList);
	// var designation = findDesignation(couMem.couDesgId, varDesignationList);

	/* $("#designation" + index).val(designation.dsgname); */
	let designation = couMem.couMemberTypeDesc;
	$('#couMemberTypeDesc' + index).val(couMem.couMemberTypeDesc);
	$("#partyAFFDesc" + index).val(couMem.couPartyAffDesc);
	$("#elecWardDesc" + index).val(couMem.couEleWZ1Desc);
	if(designation != null){
		if(designation.toUpperCase() == 'MAYOR'){
			$("#comDsgId" + index).val(lookUpId);
			$('select').trigger("chosen:updated");
			//$("#comDsgId" + index).prop('disabled', 'disabled');
		}else{
			$("#comDsgId" + index).prop('selectedIndex',0);
			//$("#comDsgId" + index).prop('disabled', false);
		}	
	}else{
		$("#comDsgId" + index).prop('selectedIndex',0);
		//$("#comDsgId" + index).prop('disabled', false);
	}
	
}

function findMember(couMemId, varCouMemMasterDtoList) {
	var couMem = {};
	$.each(varCouMemMasterDtoList, function(index) {
		var obj = varCouMemMasterDtoList[index];
		if (obj.couId == couMemId) {
			couMem = obj;
		}
	});
	return couMem;
}

function findDesignation(designationid, designations) {
	var designation = designationid;
	$.each(designations, function(index) {
		var obj = designations[index];
		if (obj.dsgid == designationid) {
			designation = obj;
		}
	});
	return designation;
}
function triggerTable() {
	$('#MemberData').dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"bStateSave" : true,
		"lengthChange" : true,
		"scrollCollapse" : true,
		"bSort" : false
	});
}

function saveform(obj) {
	var errorList = [];
	errorList = validateEntryDetails(errorList);

	// validation check for input field of UI
	var committeeTypeId = $('#committeeTypeId').val();
	var dissolveDate = $('#dissolveDate').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var memberIds = $("#couMemId0").val();
	var today = $.datepicker.formatDate('dd/mm/yy', new Date());
	var expiryDate = $("#expiryDate0").val();
	var expiryReason = $("#expiryReason0").val();

	if (committeeTypeId == '' || committeeTypeId == '0') {
		errorList
				.push(getLocalMessage('council.committee.validation.committeeType'));
	}
	if (dissolveDate == '' || dissolveDate == undefined) {
		errorList
				.push(getLocalMessage('council.committee.validation.dissolveDate'));
	}
	if (fromDate == '' || fromDate == undefined) {
		errorList
				.push(getLocalMessage('council.committee.validation.fromDate'));
	}
	if (toDate == '' || toDate == undefined) {
		errorList.push(getLocalMessage('council.committee.validation.toDate'));
	}
	if (memberIds == '' || memberIds == undefined) {
		errorList
				.push(getLocalMessage('council.committee.validation.committeeMembers'));
	}

	// check dissolve date should be greater than equal to from and toDate

	if (dissolveDate != '' && fromDate != ''
			&& compareDate(dissolveDate) < compareDate(fromDate)) {
		errorList
				.push(getLocalMessage('council.committee.validation.fromDateGreater'));
	}

	if (dissolveDate != '' && toDate != ''
			&& compareDate(dissolveDate) < compareDate(toDate)) {
		errorList
				.push(getLocalMessage('council.committee.validation.toDateGreater'));
	}

	if (fromDate != '' && compareDate(today) < compareDate(fromDate)) {
		errorList
				.push(getLocalMessage('council.committee.validation.fromdate.check'));
	}

/*	if (expiryDate != '') {
		if (expiryReason == '') {
			errorList
					.push(getLocalMessage('council.committee.validation.expiryreason'));
		}
	}

	if (expiryReason != '') {
		if (expiryDate == '') {
			errorList
					.push(getLocalMessage('council.committee.validation.expirydate'));
		}
	}*/

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		/*Defect #40602*/
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		requestData = __serializeForm(theForm);
		var URL = 'CouncilMemberCommitteeMaster.html?checkCommitteeAlreadyPresent';
		var retData = __doAjaxRequest(URL, 'POST', requestData, false);
		if(retData == "Y"){
			errorList.push(getLocalMessage('council.committee.member.count.validate'));
			displayErrorsOnPage(errorList);
			return false;
			
		}else if(retData == "E"){
			errorList.push(getLocalMessage('council.validation.memberCount'));
			displayErrorsOnPage(errorList);
			return false;
		}
		else{
			return saveOrUpdateForm(obj, '', 'CouncilMemberCommitteeMaster.html',
			'saveform');
			}		
	} 
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function showGridOption(committeeTypeId, action) {
	var actionData;
	var divName = formDivName;
	var requestData = 'committeeTypeId=' + committeeTypeId;
	if (action == "E") {
		actionData = 'editCouncilCommitteeMasterData';
		var ajaxResponse = doAjaxLoading('CouncilMemberCommitteeMaster.html?'
				+ actionData, requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}

	if (action == "V") {
		actionData = 'viewCouncilCommitteeMasterData';
		var ajaxResponse = doAjaxLoading('CouncilMemberCommitteeMaster.html?'
				+ actionData, requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}
function getDetails() {
	var no = $('#committeeTypeId option:selected').attr('code');
	$("#otherField").val(no);
}
