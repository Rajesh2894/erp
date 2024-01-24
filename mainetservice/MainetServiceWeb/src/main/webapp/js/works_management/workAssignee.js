/**
 * @Author hiren.poriya
 */

var WORK_ASSIGNEE_URL = "WorkAssignee.html";
$(document).ready(function(){
	
	$('#WorkAssignee').validate({
		onkeyup: function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout: function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}});
	
	
	$("#datatables").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true
	    });	 
	
	
	$("#addAssignee").click(function(){
		var requestData ={"mode":"C"}
		var ajaxResponse = __doAjaxRequest(WORK_ASSIGNEE_URL+'?assigneeForm', 'POST', requestData, false,'html');
		$('.content-page').html(ajaxResponse);	
	});
	
	$("#resetAssignee").click(function(){
		var requestData ={"mode":"C"}
		var ajaxResponse = __doAjaxRequest(WORK_ASSIGNEE_URL+'?assigneeForm', 'POST', requestData, false,'html');
		$('.content-page').html(ajaxResponse);	
	});
	
	$("#searchAssignee").click(function() {
		var errorList=[];
			var workOrderId = $('#workOrderId').val();
			//var assignee = $('#assignee').val();
			if( workOrderId!= '' ){//|| assignee != ''
				var requestData = 'workOrderId='+workOrderId;//+"&assignee="+assignee;
				var table = $('#datatables').DataTable();
				table.rows().remove().draw();
				$(".warning-div").hide();
				var ajaxResponse = __doAjaxRequest(WORK_ASSIGNEE_URL+'?searchAssignee', 'POST',requestData, false,'json');
				var result = [];
				$.each(ajaxResponse, function(index){
					var obj = ajaxResponse[index];
						result.push([obj.workOrderNo,obj.tenderMasterDto.vendorName,obj.tenderMasterDto.workAssigneeName,obj.tenderMasterDto.tenderAllWorks ,'<td class="text-center">'+
							  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 " style="margin-left:58px;" onClick="viewAssignee(\''+obj.workId+'\',\''+obj.tenderMasterDto.workAssigneeId+'\')" title="View"><i class="fa fa-eye"></i></button>'+
							  '<button type="button" class="btn btn-warning btn-sm margin-right-10" onClick="editAssignee(\''+obj.workId+'\',\''+obj.tenderMasterDto.workAssigneeId+'\')"  title="Edit"><i class="fa fa-pencil"></i></button>'+
							  '</td>']);
		         });
				table.rows.add(result);
				table.draw();
			}else{
				errorList.push(getLocalMessage('work.assignee.select.work.orderNo.to.search.records'));
				displayErrorsOnPage(errorList);
			}
	   });
	
if($("#mode").val()=='E'|| $("#mode").val()=='V'){
	var d = $("#tenderDate").val();
	d = d.split(' ')[0];
	$("#tenderDate").val(d)
}
});


function convertDate(dateString){
	var p = dateString.split(/\D/g)
	return [p[2],p[1],p[0] ].join("/")
	}

function editAssignee(woId,empId){
	var requestData ={"assignee":empId, "workOrderId":woId,"mode":"E" }
	var ajaxResponse = __doAjaxRequest(WORK_ASSIGNEE_URL+'?assigneeForm', 'POST', requestData, false,'html');
	$('.content-page').html(ajaxResponse);	
	
}

function viewAssignee(woId,empId){
	var requestData ={"workOrderId":woId,"assignee":empId,"mode":"V" }
	var ajaxResponse = __doAjaxRequest(WORK_ASSIGNEE_URL+'?assigneeForm', 'POST', requestData, false,'html');
	$('.content-page').html(ajaxResponse);	
	$("#WorkAssignee :input").prop("disabled",true);
	$("select").prop("disabled", true).trigger("chosen:updated");
	$("#button-Cancel").prop("disabled", false);
	
}
function getWorks(){
	$(".error-div").hide();
	var errorList=[];
	var workOrderId= $("#workOrderId").val();
	$('#workCode').html('');
	if(workOrderId!= null && workOrderId!="" ){
	var requestData ={"workOrderId":workOrderId}  
	var workObj = __doAjaxRequest(WORK_ASSIGNEE_URL+'?getWorkList', 'POST', requestData, false,'json');
	if( workObj.tenderMasterDto.workDto.length != 0){
	$("#tenderNo").val(workObj.tenderMasterDto.tenderNo);
	$("#projectName").val(workObj.tenderMasterDto.projectName);
	$("#tenderDate").val(workObj.tenderMasterDto.tenderDate);
	var date = convertDate(workObj.tenderMasterDto.tenderDate);
	$("#tenderDate").val(date);
	
		$.each(workObj.tenderMasterDto.workDto,function(index,value){
			$('#workCode').append($("<option></option>").attr("value",value.workId).text(value.workCode+">>"+value.workName));
		});
		}else{
		
		$("#workOrderId").val("");//.trigger("chosen:updated");
		resetFields();
		errorList.push(getLocalMessage('work.assignee.nounassigned.work'));
		displayErrorsOnPage(errorList);
	}
	}else{
		resetFields();
	}
	
}
function resetFields(){
	$("#tenderNo").val("");
	$("#projectName").val("");
	$("#tenderDate").val("");
	$('#workCode').append($("<option></option>").attr("value","").text("Select"));
}

function saveAssignee(formObj){
	var errorList=[];
	var workOrderId = $("#workOrderId").val();
	var workCode = $("#workCode").val();
	var assignee = $("#assignee").val();
	
	if(workOrderId == null ||workOrderId == ""){
		errorList.push(getLocalMessage('work.assignee.select.workorde'));
	}
	if(workCode == null ||workCode == ""){
		errorList.push(getLocalMessage('work.assignee.select.workcode'));
	}
	if(assignee == null ||assignee == ""){
		errorList.push(getLocalMessage('work.assignee.select.assignee'));
	}
	if(errorList.length == 0){
	return saveOrUpdateForm(formObj,"", WORK_ASSIGNEE_URL, 'saveform');
	}else{
			displayErrorsOnPage(errorList);
	}
}

