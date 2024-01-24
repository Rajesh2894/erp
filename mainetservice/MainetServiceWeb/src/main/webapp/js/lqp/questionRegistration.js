$(document).ready(function() {

	$("#queryRegDataTable").dataTable(
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
    
    
   

	$('#searchLegislativeQuery').click(function() {
		let errorList = [];
		let deptId = $('#deptId').val();
		let questionTypeId = $('#questionTypeId').val();
		let questionId = $('#questionId').val();
		let fromDate = $('#fromDate').val();
		let toDate = $('#toDate').val();
		if (deptId != 0 || questionTypeId !=0|| questionId != ''|| fromDate != ''|| toDate != '') {
			var requestData = '&deptId='+ deptId + '&questionTypeId='+ questionTypeId
						+ '&questionId='+ questionId
						+ '&fromDate=' + fromDate
						+ '&toDate=' + toDate;
			var table = $('#queryRegDataTable').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest('LegislativeQuestion.html?searchQueryReg','POST', requestData, false,'json');
			
			if (ajaxResponse.length == 0) {
				errorList.push(getLocalMessage("lqp.validation.grid.nodatafound"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
				$.each(ajaxResponse.queryRegMasrDtoList,function(index) {
						
						let obj = ajaxResponse.queryRegMasrDtoList[index];
						let questionRegId =obj.questionRegId;
						let questionId = obj.questionId;
						let questionType = obj.questionType;
						let questionSubject = obj.questionSubject;
						let mlaName = obj.mlaName;
						/*let meetingDate = obj.meetingDate;
						let questionRaisedDate = obj.questionRaisedDate;
						let deadlineDate = obj.deadlineDate;*/
						
						let meetingDate = new Date(obj.meetingDate);
						let month = meetingDate .getMonth() + 1;
					    let meetingformateDate = meetingDate.getDate() + "-" + month + "-" + meetingDate.getFullYear()
					    
					    let questionRaisedDate = new Date(obj.questionRaisedDate);
						month = questionRaisedDate .getMonth() + 1;
					    let questionRaisedformateDate = questionRaisedDate.getDate() + "-" + month + "-" + questionRaisedDate.getFullYear()
					    
					    let deadlineDate = new Date(obj.deadlineDate);
						month = deadlineDate .getMonth() + 1;
					    let deadlineDateformateDate = deadlineDate.getDate() + "-" + month + "-" + deadlineDate.getFullYear()
					    
					    var disStr	=	"";
						if(obj.status != 'CONCLUDED') {
							disStr	=	'disabled="true"';
						}
						result.push([ 
							'<div align="center">'+ (index + 1) + '</div>',
							'<div align="center">'+questionId + '</div>',
							'<div align="center">'+questionType + '</div>',
							'<div align="center">'+questionSubject + '</div>',
							'<div align="center">'+mlaName + '</div>',
							'<div align="center">'+meetingformateDate + '</div>',
							'<div align="center">'+questionRaisedformateDate + '</div>',
							'<div align="center">'+deadlineDateformateDate + '</div>',
							'<div class="text-center">'
							+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="showGridOption(\''
							+ questionRegId
							+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
							+ '<button type="button" '+ disStr +' class="btn btn-danger btn-sm btn-sm margin-right-5"  onclick="showGridOption(\''
							+ questionRegId
							+ '\',\'VA\')"  title="View Answer"><i class="fa fa-pencil-square-o"></i></button>'
							+ '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-5"  onclick="showGridOption(\''
							+ questionRegId
							+ '\',\'R\')"  title="Reopen"><i class="fa fa-exchange"></i></button>'
							
							
							+(obj.isAttachDoc == "Y" ? '<button type="button"  class="btn btn-primary btn-sm margin-right-5" onClick="draftAST(\''
									+ obj.astId
									+ '\')" title="Attachment"><i class="fa fa-paperclip" aria-hidden="true"></i></button>': '')
							
					     	+ '</div>' ]);
						});
				table.rows.add(result);
				table.draw();
			
			} else {
				errorList.push(getLocalMessage("lqp.validation.select.any.field"));
				displayErrorsOnPage(errorList);
			}
		});
	
	/*Reset Query REG Form*/
	$("#resetQueryRegForm").click(function(){
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('LegislativeQuestion.html?addQueryReg', {}, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	});


});//end of document load

function addQueryReg(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {} , 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function showGridOption(questionRegId, action) {
	var actionData;
	var divName = formDivName;
	var requestData = 'questionRegId=' + questionRegId;
	if (action == "VA") {
		actionData = 'viewQueryAnswer';
		var ajaxResponse = doAjaxLoading('LegislativeQuestion.html?'
				+ actionData, requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}

	if (action == "V") {
		actionData = 'viewQueryReg';
		var ajaxResponse = doAjaxLoading('LegislativeQuestion.html?'
				+ actionData, requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	if (action == "R") {
		actionData = 'reopenQueryReg';
		var ajaxResponse = doAjaxLoading('LegislativeQuestion.html?'
				+ actionData, requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

//save and estate  form
function saveQueryRegform(obj) {
	var errorList = [];
	errorList = queryRegValidation(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'LegislativeQuestion.html', 'saveform');
	}
}

//save answer REG form
function saveAnswerRegform(obj) {
	var errorList = [];
	errorList = answerRegValidation(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'AdminHome.html', 'saveDecision');
	}
}

function queryRegValidation(errorList) {
	let questionDate = $("#questionDate").val();
	let questionTypeId = $("#questionTypeId").val();
	let inwardNo = $("#inwardNo").val();
	let deptId = $("#deptId").val();
	let empId =  $("#empId").val();
	let questionSubject = $("#questionSubject").val();
	let mlaName = $("#mlaName").val();
	let raisedByAssemblyId = $("#raisedByAssemblyId").val();
	let question = $("#question").val();
	let meetingDate = $("#meetingDate").val();
	let deadlineDate = $("#deadlineDate").val();
	let questionRaisedDate = $("#questionRaisedDate").val();
	
	if (questionDate == undefined || questionDate == '') {
		errorList.push(getLocalMessage('lqp.validation.questionDate'));
	}
	if (questionRaisedDate == undefined || questionRaisedDate == '') {
		errorList.push(getLocalMessage('lqp.validation.questionRaisedDate'));
	}
	if (meetingDate == undefined || meetingDate == '') {
		errorList.push(getLocalMessage('lqp.validation.meetingDate'));
	}
	if (questionTypeId == undefined || questionTypeId == '') {
		errorList.push(getLocalMessage('lqp.validation.questionType'));
	}
	if (mlaName == undefined || mlaName == '') {
		errorList.push(getLocalMessage('lqp.validation.mlaName'));
	}
	if (raisedByAssemblyId == "0" || raisedByAssemblyId == undefined || raisedByAssemblyId == '') {
		errorList.push(getLocalMessage('lqp.validation.raisedAssembly'));
	}
	if (questionSubject == undefined || questionSubject == '') {
		errorList.push(getLocalMessage('lqp.validation.subject'));
	}
	
	if (question == undefined || question == '') {
		errorList.push(getLocalMessage('lqp.validation.questionDetails'));
	}
	if (deadlineDate == undefined || deadlineDate == '') {
		errorList.push(getLocalMessage('lqp.validation.deadlineDate'));
	}
	
	if (deptId == "0" || deptId == undefined || deptId == '') {
		errorList.push(getLocalMessage('lqp.validation.deptName'));
	}
	if (empId == "0" || empId == undefined || empId == '') {
		errorList.push(getLocalMessage('lqp.validation.empName'));
	}
	
	if (inwardNo == undefined || inwardNo == '') {
		errorList.push(getLocalMessage('lqp.validation.inwardNo'));
	}
	
	//reopen reason VALIDN
	if($('#saveMode').val()=='REOPEN'){
		let reopenReason=$('#reopenReason').val();	
		if (reopenReason == undefined || reopenReason == '') {
			errorList.push(getLocalMessage('lqp.validation.reopenReason'));
		}	
	}
	
	//date comparison logic for query registration
	if(new Date(questionRaisedDate).getTime() < new Date(meetingDate).getTime()){
		//question raised date should not be less than meeting date
		errorList.push(getLocalMessage('lqp.validation.raisedDateWithMeetingDate'));
	}
	if(new Date(questionRaisedDate).getTime() > new Date(questionDate).getTime()){
		//question raised date should not be greater than question date
		errorList.push(getLocalMessage('lqp.validation.raisedDateWithQuestionDate'));
	}
	if(new Date(meetingDate).getTime() > new Date(questionDate).getTime()){
		//meeting date should not be greater than question date
		errorList.push(getLocalMessage('lqp.validation.MeetingDateWithQuestionDate'));
	}
	
	if(new Date(deadlineDate).getTime() < new Date(questionDate).getTime()){
		//deadline date should not be less than question date
		errorList.push(getLocalMessage('lqp.validation.deadineDateWithQuestionDate'));
	}
	
	/*if(acqDate != ''){
		var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
		if(!pattern.test(acqDate)){
			errorList.push(getLocalMessage('land.acq.val.date'));
		}	
	}*/
	
	return errorList;
}

function answerRegValidation(errorList){
	let answer = $("#answer").val();
	if (answer == undefined || answer == '') {
		errorList.push(getLocalMessage('lqp.validation.answerDetail'));
	}
	return errorList;
}




