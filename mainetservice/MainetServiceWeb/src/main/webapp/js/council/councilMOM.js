var COUNCIL_MOM_URL = "CouncilMOM.html";
var fileArray=[];
var d=0;
$(document)
    .ready(
        function() {

            $("#momSummaryDatatables").dataTable({
                "oLanguage": {
                    "sSearch": ""
                },
                "aLengthMenu": [
                    [5, 10, 15, -1],
                    [5, 10, 15, "All"]
                ],
                "iDisplayLength": 5,
                "bInfo": true,
                "lengthChange": true
            });

            $("#fromDate").datepicker({
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                yearRange: "1900:2200",
                onClose: function(selectedDate) {
                    $("#toDate").datepicker("option",
                        "minDate", selectedDate);
                }
            });

            $("#toDate").datepicker({
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                yearRange: "1900:2200",
                onClose: function(selectedDate) {
                    $("#fromDate").datepicker("option",
                        "maxDate", selectedDate);
                }
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
            
            
            
            

            $('#searchCouncilMOM')
                .click(
                    function() {
                    	$('#createMOMBT').show();
                        var errorList = [];
                        var meetingTypeId = $('#meetingTypeId')
                            .val();
                        var meetingNo = $('#meetingNo').val().trim();
                        var fromDate = $('#fromDate').val();
                        var toDate = $('#toDate').val();

                            if (meetingTypeId != 0 || meetingNo != '' || fromDate != '' || toDate != '') {
                                var requestData = '&meetingTypeId=' +
                                    meetingTypeId +
                                    '&meetingNo=' +
                                    meetingNo +
                                    '&fromDate=' +
                                    fromDate +
                                    '&toDate=' +
                                    toDate;
                                var table = $('#momSummaryDatatables').DataTable();
                                table.rows().remove().draw();
                                $(".warning-div").hide();
                                var ajaxResponse = __doAjaxRequest(
                                    'CouncilMOM.html?searchCouncilMOM',
                                    'POST', requestData,
                                    false, 'json');
                                if (ajaxResponse.councilMeetingMasterDtoList.length == 0) {
                                    errorList
                                        .push(getLocalMessage("council.member.validation.grid.nodatafound"));
                                    displayErrorsOnPage(errorList);
                                    return false;
                                }
                                var result = [];
                                $
                                    .each(
                                        ajaxResponse.councilMeetingMasterDtoList,
                                        function(index) {
                                            var obj = ajaxResponse.councilMeetingMasterDtoList[index];
                                            let meetingId = obj.meetingId;
                                            $('#meetingId').val(meetingId);
                                            let meetingDateDesc = obj.meetingDateDesc;
                                            let meetingTypeName = obj.meetingTypeName;
                                            let meetingNo = obj.meetingNo;
                                            let meetingPlace = obj.meetingPlace;
                                            let meetingTime = obj.meetingTime;
                                            let actionBT = obj.actionBT;
                                            if(actionBT)
                                            	$('#createMOMBT').hide();
                                            	
                                            result.push([
                                                '<div class="text-center">' +
                                                (index + 1) +
                                                '</div>',
                                                '<div class="text-center">' +
                                                meetingTypeName +
                                                '</div>',
                                                '<div class="text-center">' +
                                                meetingNo +
                                                '</div>',
                                                '<div class="text-center">' +
                                                meetingDateDesc +
                                                '</div>',
                                                '<div class="text-center">' +
                                                meetingTime +
                                                '</div>',
                                                '<div class="text-center">' +
                                                meetingPlace +
                                                '</div>',
                            					'<div class="text-center">'
                            					+ '<button type="button"  class="btn btn-blue-2 margin-right-5"  onclick="getActionForDefination(\'' +
                                                meetingId +
                                                '\',\'VIEW\')" title="View"><i class="fa fa-eye"></i></button>' +
                                                '<button type="button" class="btn btn-danger "  onclick="getActionForDefination(\'' +
                                                meetingId +
                                                '\',\'EDIT\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>' +
                                                '</div>' ]);
                                            
                                        });
                                table.rows.add(result);
                                table.draw();
                            } else {
                                errorList
                                    .push(getLocalMessage("council.member.validation.select.any.field"));
                                displayErrorsOnPage(errorList);
                            }
                        
                    });
            
            //DROPDOWN empty when page load
            //$('#meetingNo').html('');
            $('#String1').ckeditor({skin : 'bootstrapck'});
            //$("#praposal").hide();
        });

function addMOM(formUrl, actionParam) {        
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
        actionData = 'editAttendanceMasterData';
        var ajaxResponse = doAjaxLoading('CouncilAttendanceMaster.html?' +
            actionData, requestData, 'html', divName);
        $('.content').removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
     
    }

    if (action == "V") {
        actionData = 'viewAttendanceMasterData';
        var ajaxResponse = doAjaxLoading('CouncilAttendanceMaster.html?' +
            actionData, requestData, 'html', divName);
        $('.content').removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
        
 
        
    }
}

function getMeetingNos() {
	var meetingTypeId = $("#meetingTypeId").find("option:selected").val();
	$('#meetingNo').html('');
	$('#meetingNo').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	
	if(meetingTypeId!="" &&  meetingTypeId!=0){
		var requestData = {
				"meetingTypeId":meetingTypeId
			}
		var meetingNoList = __doAjaxRequest(COUNCIL_MOM_URL+'?getMeetingNo', 'POST', requestData, false,'json');
		$.each(meetingNoList, function(index, value) {
			 $('#meetingNo').append($("<option></option>").attr("value",value.meetingId).attr("code",value.meetingNo).text(value.meetingNo));
		});
		 $('#meetingNo').trigger('chosen:updated');	
	   }
	
	
}

function getMeetingDetails(obj) {
	var meetingNo = $("#meetingNo").find("option:selected").text();

	var requestData
	if(meetingNo!="" &&  meetingNo!=0){
		var requestData = {
				"meetingNo":meetingNo
			}
		
		
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		//var requestData = __serializeForm(theForm);
		
		var response = __doAjaxRequest(COUNCIL_MOM_URL+'?getMeetingData', 'POST', requestData, false,'html');
		$('.content').removeClass('ajaxloader');
		var divName = formDivName;
		$(divName).html(response);
	   }
	var oMMFlag = $("#oMMFlag").val();
	if(oMMFlag == "Y"){
		getProposalEditorOMM();
	}else{
		getProposalEditor(this);
	}

	
	
}


function backMOMForm() {
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'CouncilMOM.html');
    $("#postMethodForm").submit();
  
}

function resetMOMForm(ele) {		
	var meetingId = $("#meetingId").val();
	var mode = $("#saveMode").val();
	
	if(mode == 'EDIT'){
		getActionForDefination(meetingId, mode);
	}
	else{
		let actionParam = 'addCouncilMOM';
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(COUNCIL_MOM_URL + '?' + actionParam, {}, 'html',
				divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		
	}
		
	
}

// code for edit or view mom
function getActionForDefination(meetingId, mode) {
    var divName = '.content-page';
    var url = "CouncilMOM.html?editViewMOMData";
    var actionParam = {
        'meetingId': meetingId,
        'mode': mode
    }
    var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();var oMMFlag = $("#oMMFlag").val();
	if(oMMFlag == "Y"){
		getProposalEditorOMM();
	}else{
		getProposalEditor(this);
	}
}

// code for create MOM screen
function addSumotoAction(params, meetingId, momId, proposalId) {

    if ((meetingId == '' || meetingId == undefined || proposalId == '' || proposalId == undefined)) {
        return false;
    }
    var requestData = $("form").serialize() + '&meetingId=' + meetingId +
        '&momId=' + momId + '&proposalId=' + proposalId;
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading('CouncilMOM.html' + '?' + params,
        requestData, 'html', divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

// script code for create MOM
function createMOM(object) {
     
	var String1 = $("#String1").val();
	$("#resolutionComments").val(String1);
	document.getElementById("resolutionComments").value = String1;	
	let errorList = [];
    let actionParam = "";
    errorList = validateMomDetails(errorList);
    //actionParam = "saveMeetingMOM";
    if (String1 == "" || String1 == undefined) {
        errorList.push(getLocalMessage("council.mom.resolutionCommentEmpty"));
    }
    if (errorList.length > 0) {
        $("#errorDiv").show();
        showErr(errorList);
    } else {
        $("#errorDiv").hide();
        var resolutionCommentsEncrpyt = encodeURIComponent(String1);
	    var requestDataForEncrypt = {
			'resolutionComments' : resolutionCommentsEncrpyt
		};
	    var ajaxResponse = __doAjaxRequest(
	    	    "CouncilMOM.html?encyptData", 'POST',
	    	    requestDataForEncrypt, false, 'json');
        var formName = findClosestElementId(object, 'form');
        var theForm = '#' + formName;
        var requestData = __serializeForm(theForm);
        /*var response = __doAjaxRequest('CouncilMOM.html?' + actionParam,
            'POST', requestData, false);
        finalShowConfirmBox();*/
        return saveOrUpdateForm(object, '', 'CouncilMOM.html?PrintReport', 'saveform');
    }
}

function finalShowConfirmBox() {

    var errMsgDiv = '.msg-dialog-box';
    var message = '';
    var cls = getLocalMessage("council.mom.proceed");

    message += '<h4 class=\"text-center text-blue-2 padding-12\"> ' +
        getLocalMessage('council.mom.savesuccessmsg') + ' </h4>';
    message += '<div class=\'text-center padding-bottom-10\'>' +
        '<input type=\'button\' value=\'' + cls +
        '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    ' +
        ' onclick="proceedForMOM()"/>' + '</div>';

    $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
    $(errMsgDiv).html(message);
    $(errMsgDiv).show();
    $('#btnNo').focus();
    showModalBoxWithoutClose(errMsgDiv);
    return false;
}

function proceedForMOM() {

    $("#postMethodForm").prop('action', 'CouncilMOM.html');
    $("#postMethodForm").submit();
    $.fancybox.close();

}




function showErr(errorList) {
    $(".warning-div").removeClass('hide');
    var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
    $.each(errorList, function(index) {
        errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' +
            errorList[index] + '</li>';
    });
    errMsg += '</ul>';
    $("#errorDiv").html(errMsg);

    $("html, body").animate({
        scrollTop: 0
    }, "slow");
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

// validation method for momForm
function validateMomDetails(errorList) {

    $("#momDataTable tbody tr").each(function(i) {
        let position = i + 1;
        let resolutionComments = $("#resolutionComments" + i).val();
        let status = $("#status" + i).val();

        if (resolutionComments == "" || resolutionComments == undefined) {
            errorList.push(getLocalMessage("council.mom.resolutionCommentEmpty") + " At Row " + position);
        }
       
        if (status == "0" || status == undefined || status == "") {
            errorList.push(getLocalMessage("council.mom.statusAction") + " At Row " + position);
        }
    });
    
    
    let resolutionComments = $("#resolutionComments").val();
  
    
    
    
    
    
    return errorList;
}

function addProposal(isMOMProposal,meetingId,agendaId) {  
	
    /*var divName = '.content-page';
    var requestData = '&flag=' + flag
	var ajaxResponse = doAjaxLoading("CouncilProposalMaster.html?addproposal", {}, 'html', requestData,
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();*/
	var divName = '.content-page';
	var requestData = {
			"isMOMProposal":isMOMProposal,
			"meetingId": meetingId,
			"agendaId" : agendaId
		}
	
	var ajaxResponse = doAjaxLoading('CouncilProposalMaster.html?addproposal', requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
    
}
function getProposalEditor(e) {
	// var proposalId = $("#proposalId").find("option:selected").text();
	var proposalId = $('#proposalId option:selected').attr('value')

	if (proposalId != "" && proposalId != 0 && proposalId != undefined) {
		
		var requestData = {
			"proposalId" : proposalId
		}
		var URL = 'CouncilMOM.html?getPraposalDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');

		if (returnData != null)

		{
			$("#praposal").show();
			$('#praposal').attr('style','display: block !important');
			$("#praposal").html(returnData);
		}

	}
	else
		{
		$("#praposal").hide();}
	
}

function getProposalEditorOMM() {
	var URL = 'CouncilMOM.html?getTextEditOmm';
	var returnData = __doAjaxRequest(URL, 'POST', "", false,
			'html');

	if (returnData != null)

	{
		$("#praposal").show();
		$('#praposal').attr('style','display: block !important');
		$("#praposal").html(returnData);
	}else{
		$("#praposal").hide();
	}
	
}

$("#attachDocuments").on("click",'#deleteFile',function(e) {

	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {

		$(this).parent().parent().remove();
		var fileId = $(this).parent().parent().find(
				'input[type=hidden]:first').attr('value');
		if (fileId != '') {
			fileArray.push(fileId);
		}
		$('#removeFileById').val(fileArray);
	}
});
