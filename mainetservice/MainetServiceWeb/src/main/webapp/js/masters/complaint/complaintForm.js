var removeIdArray=[];
var currentRow;
$(document).ready(function(){
	
	if($("#hiddeValue").val() == 'V' ){
		$(".addCF").prop("disabled", true);
		$(".remCF1").prop("disabled", true);
	}
	if($("#hiddeValue").val() == 'C' ){
		$(".isActive").prop("checked", true);
	}

	$("#customFields").on('click','.addCF',function(i){
		var row=0;
		var errorList = [];
		errorList = validateAdditionalDtl(errorList);
		if (errorList.length == 0 ) {
			var romm=0;
			// var content = $(this).closest('tr').clone();
			// $(this).closest("tr").after(content);
			var content = $('#customFields tr').last().clone();
			$('#customFields tr').last().after(content);
			//var clickedIndex = $(this).parent().parent().index() - 1;	
			content.find("input:text").val('');
			content.find("input:hidden").val('');
			content.find("input:checkbox").prop('checked',true);
			$('.error-div').hide();
			reOrderTableIdSequences();
		}else {
			displayErrorsOnPageView(errorList);
		}
	});

	$("#customFields").on('click','.remCF',function(){
		if($("#customFields tr").length != 2)
		{
			$(this).parent().parent().remove();
			reOrderTableIdSequences();
			var deletedId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(deletedId != ''){
				removeIdArray.push(deletedId);
			}
			$('#removeChildIds').val(removeIdArray);
		}
		else{
			var errorList = [];
			errorList.push(getLocalMessage("rl.subChild.deletrw.validtn"));   
			displayErrorsOnPageView(errorList);
		}
	});

	$("#customFields").on('click','.remCF1',function(){

		if($("#customFields tr").length != 2)
		{
			id=$(this).parent().parent().find('input[type=hidden]:first').attr('value');		
			var requestDataflow={"comId":id}
			var response = __doAjaxRequest('Complaint.html?checkPendingApplication', 'POST', requestDataflow, false,'json');
			if(response){
				var warnMsg= getLocalMessage("care.complaint.subtype.workflow.error.msg") ;
				message	='<p class="text-blue-2 text-center padding-30">'+ warnMsg+'</p>';
				$(childDivName).addClass('ok-msg').removeClass('warn-msg');
				$(childDivName).html(message);
				showModalBox(childDivName);
			}else{
				currentRow=$(this).parent().parent();
				deleteComSubtype(id);
				//doDeletion(id);
			}
		}
		else{
			var errorList = [];
			errorList.push(getLocalMessage("rl.subChild.deletrw.validtn"));   
			displayErrorsOnPageView(errorList);
		}
	});


	$('#submitComp').click(function(){
		var	formName =	findClosestElementId($(this), 'form');
		var theForm	=	'#'+formName;
		var errorList = [];
		errorList = validateCompMasterForm(errorList);
		if (errorList.length == 0) {
			errorList=validateAdditionalDtl(errorList);
			if(errorList.length == 0){
				var	formName =	findClosestElementId($(this), 'form');
				var theForm	=	'#'+formName;
				return saveOrUpdateForm($(this),"", complaintUrl,'saveform');
			}else{
				showRLValidation(errorList);
			}	 
		}else{
			showRLValidation(errorList);
		}
	});

	$("#resetComp").click(function(){
		var ajaxResponse = __doAjaxRequest('Complaint.html?form', 'POST', {}, false,'html');
		$('.content').html(ajaxResponse);
	});
});

function deleteComSubtype(id){
	var yes = getLocalMessage('eip.commons.yes');
	 var warnMsg = getLocalMessage("care.common.delettion.msg");
	 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
	' onclick="doDeletion(\''+id+'\')"/>'+	
	'</div>';
	 
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;
}


function hasAlphaNumeric(key,obj){

	if(key == 32 && obj.value.charAt(0)==' '){
		$(obj).val('');
	}

	if (obj.value.match(/[^a-zA-Z0-9 ]/g)) {
		obj.value = obj.value.replace(/[^a-zA-Z0-9 ]/g, '');
	} 
}


function reOrderTableIdSequences() {

	$('.appendableClass').each(function(i) {
		
		
		$(this).find("input:hidden:eq(0)").attr("id", "compId_"+i);
		$(this).find("input:checkbox:eq(0)").attr("id", "isActive_"+i);
		
		$(this).find("input:hidden:eq(0)").attr("name", "departmentComplaint.complaintTypesList["+i+"].compId");
		$(this).find("input:checkbox:eq(0)").attr("name", "departmentComplaint.complaintTypesList["+i+"].isActive");
		
		$(this).find("input:text:eq(0)").attr("id", "complaintDesc_"+i);
		$(this).find("input:text:eq(0)").attr("name", "departmentComplaint.complaintTypesList["+i+"].complaintDesc");
		
		$(this).find("input:text:eq(1)").attr("id", "complaintDescreg_"+i);
		$(this).find("input:text:eq(1)").attr("name", "departmentComplaint.complaintTypesList["+i+"].complaintDescreg");
		
		$(this).find("select:eq(0)").attr("id", "complaintResident_"+i);
		$(this).find("select:eq(0)").attr("name", "departmentComplaint.complaintTypesList["+i+"].residentId");
		
		$(this).find("select:eq(1)").attr("id", "complaintAmtDues_"+i);
		$(this).find("select:eq(1)").attr("name", "departmentComplaint.complaintTypesList["+i+"].amtDues");
		
		$(this).find("select:eq(2)").attr("id", "complaintDocument_"+i);
		$(this).find("select:eq(2)").attr("name", "departmentComplaint.complaintTypesList["+i+"].documentReq");
		
		$(this).find("select:eq(3)").attr("id", "complaintOTP_"+i);
		$(this).find("select:eq(3)").attr("name", "departmentComplaint.complaintTypesList["+i+"].otpValidReq");
		
		$(this).find("select:eq(4)").attr("id", "externalFlag_"+i);
		$(this).find("select:eq(4)").attr("name", "departmentComplaint.complaintTypesList["+i+"].externalWorkFlowFlag");
		$('#complaintOTP_'+i+' option[value="Y"]').attr("selected",true)
		
	});

}



function validateAdditionalDtl(errorList){

	$('.appendableClass').each(function(i) {
		row=i+1;
		errorList =  validateDetailsTableData(errorList,i);
	});
	return errorList;

}


function validateDetailsTableData(errorList, i) {
	var active = $("#isActive_"+i).is('checked');
	var desc = $.trim($("#complaintDesc_"+i).val());
	var descreg = $.trim($("#complaintDescreg_"+i).val());
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	var j=i+1;
	if(desc == "" || desc == undefined){
		errorList.push(info + getLocalMessage('master.enter.complaint.desc')+" "+j);
	}
	if(descreg == "" || descreg == undefined){
		errorList.push(info + getLocalMessage('master.enter.complaint.regional.desc')+" "+j);
	}
	return errorList;
}

function validateCompMasterForm(errorList) {
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	if($("#hiddeValue").val() == 'C'){
		deptId= $('#deptId').val();
		if(deptId == '0' || deptId == undefined ){
			errorList.push(info + getLocalMessage('master.select.dept'));

		}
	}

	return errorList;	
}

function displayErrorsOnPageView(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();

	return false;
}

function deptChange(){
	$(".error-div").hide();
}
function doDeletion(id) {
	requestData = {
		"id" : id
	};
	url = 'Complaint.html?doDeletion';
	var row = $("#customFields tbody .appendableClass").length;
	if (row != 1) {
		var response = __doAjaxRequest(url, 'POST', requestData, false, 'html');
		currentRow.remove();
		reOrderTableIdSequences();
		closeFancyOnLinkClick(childDivName);
		if(id != ''){
			//console.log(id);
			removeIdArray.push(id);
		}
		$('#removeChildIds').val(removeIdArray);
		
	}
}