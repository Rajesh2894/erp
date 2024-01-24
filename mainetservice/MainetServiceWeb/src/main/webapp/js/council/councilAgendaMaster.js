$(document).ready(function() {
	
	$("#agendaDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});	
	

	$("#initialTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	
	$("#finalTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$( "#agendaFromDate" ).datepicker({
        dateFormat : 'dd/mm/yy',
    		changeMonth : true,
    		changeYear : true,
    		yearRange : "1900:2200",
    		onClose: function( selectedDate ) {
        $( "#agendaToDate" ).datepicker( "option", "minDate", selectedDate );
      }
    });
    
    $( "#agendaToDate" ).datepicker({
    		dateFormat : 'dd/mm/yy',
    		changeMonth : true,
    		changeYear : true,
    		yearRange : "1900:2200",
    		onClose: function( selectedDate ) {
        $( "#agendaFromDate" ).datepicker( "option", "maxDate", selectedDate );
      }
    });
	
	$('#searchCouncilAgenda').click(function() {
		var errorList = [];
		var committeeTypeId = $('#committeeTypeId').val();
		var agendaNo = $('#agendaNo').val();
		var fromDate = $('#agendaFromDate').val();
		var toDate = $('#agendaToDate').val();
		if ( committeeTypeId != 0  || agendaNo != '' || fromDate != '' || toDate != ''){
			var requestData = '&committeeTypeId=' + committeeTypeId + '&agendaNo=' + agendaNo + '&fromDate=' + fromDate +'&toDate=' +toDate +'&meetingInvitation='+ false;
			var table = $ ('#agendaDatatables').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest('CouncilAgendaMaster.html?searchCouncilAgenda','POST', requestData, false,'json');
			if (ajaxResponse.length == 0) {
				errorList.push(getLocalMessage("council.member.validation.grid.nodatafound"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
			$.each(ajaxResponse.councilAgendaMasterDtoList,function(index) {			
				var obj = ajaxResponse.councilAgendaMasterDtoList[index];
				let agendaId= obj.agendaId;
				let agendaNo = obj.agendaNo;
				let committeeType = obj.committeeType;
				let agendaStatus = obj.agendaStatus;
				let agenDate = obj.agenDate;
										
				result.push([ 
					'<div class="text-center">'+ (index + 1) + '</div>',
					'<div class="text-center">'+agendaNo + '</div>',
					'<div class="text-center">'+committeeType + '</div>',
					'<div class="text-center">'+agenDate + '</div>',
					'<div class="text-center">'
					+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="showGridOption(\''
					+ agendaId
					+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
					+ '<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-10"  onclick="showGridOption(\''
					+ agendaId
					+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
			     	+ '</div>' ]);
			});
			table.rows.add(result);
			table.draw();
		}
		else{
			errorList.push(getLocalMessage("council.member.validation.select.any.field"));
			displayErrorsOnPage(errorList);
		}
	});
});

function addAgendaMaster(formUrl, actionParam,proposalPresent) {
	
	var errorList = [];
	if(!proposalPresent){
		errorList.push(getLocalMessage("council.agenda.validation.proposalListEmpty"));
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();	
	}
	
}

function filterByDept(){
	var deptId = $('#department').val();
	var requestData = 'deptId=' + deptId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CouncilAgendaMaster.html?getproposalByDept' , requestData, 'html',divName);
	$('#department').val(deptId);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
}

function showGridOption (agendaId,action){
	var actionData;
	var divName = formDivName;
	var requestData = 'agendaId=' + agendaId;
	if (action == "E") {
		actionData = 'editCouncilAgendaMasterData';
		var ajaxResponse = doAjaxLoading('CouncilAgendaMaster.html?' + actionData,
				requestData, 'html',divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	
	if (action == "V") {
		actionData = 'viewCouncilAgendaMasterData';
		var ajaxResponse = doAjaxLoading('CouncilAgendaMaster.html?' + actionData,
				requestData, 'html',divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function backAgendaMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CouncilAgendaMaster.html');
	$("#postMethodForm").submit();
}

//script code for create agenda
function createAgenda(obj){
	var errorList = [];
	
	//validation for save form submit
	var committeeTypeId  = $("#committeeTypeId").val();
	var proposalIds = $('#proposalIds').val();
	var agendaDate = $('#agendaDate').val(); 
	var presentProposalNo = $('#presentProposalNo').val();
	if (committeeTypeId == '' || committeeTypeId == '0') {
		errorList.push(getLocalMessage('council.agenda.validation.committeeType'));
	}
	if (proposalIds == '' || proposalIds == undefined) {
		errorList.push(getLocalMessage('council.agenda.validation.proposal'));
	}else if(presentProposalNo == '' || presentProposalNo == undefined ){
		
	}
	if (agendaDate == '') {
		errorList.push(getLocalMessage('council.agenda.validation.agendaDate'));
	}
	
	if (errorList.length > 0) {
		//display error msg
		displayErrorsOnPage(errorList);
        return false;	} else {
	
		return saveOrUpdateForm(obj, '', 'CouncilAgendaMaster.html', 'saveform');
	}
}

//120544-to check committee Dissolve or not
function checkCommitteeDissolve(obj) {
	var errorList = [];
	var committeeTypeId = $("#committeeTypeId").val();
	var divName = formDivName;
	var requestData = 'committeeTypeId=' + committeeTypeId;

	var ajaxResponse = __doAjaxRequest(
			'CouncilAgendaMaster.html?checkCommitteeDissolve', 'post',
			requestData, false, 'json');
	if (ajaxResponse == false) {
		$("#committeeTypeId").val("");
		errorList.push(getLocalMessage('council.validate.committee'));
		displayErrorsOnPage(errorList);
		return false;
	}

}