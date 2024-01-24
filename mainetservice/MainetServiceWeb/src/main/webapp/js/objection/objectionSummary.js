
$(document).ready(function() {
	$('#rtiObjectionSummaryForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	
	$("#datatables").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true
	    });	

	$("#objectionDeptId").change(function(e)
	{
		getObjectionServiceByDept();
	});	

	
	$("#objDetailSearch").click(function() {
		var errorList=[];
			var objectionDeptId = $('#objectionDeptId').val();
			var serviceId = $('#serviceId').val();
			var objectionOn = $('#objectionOn').val();
			var refNo = $('#refNo').val();
			errorList = validateSearchCriteria(errorList);	
			
			if(objectionDeptId!=""){
		//	if (errorList.length == 0) {

				var requestData = {
						"objectionDeptId":objectionDeptId,
						"serviceId":serviceId,
						"refNo":refNo,
						"objectionOn":objectionOn
				}
				
				var table = $('#datatables').DataTable();
				table.rows().remove().draw();
				$(".warning-div").hide();
				var ajaxResponse = __doAjaxRequest('ObjectionDetails.html?searchDetails', 'POST',requestData, false,'json');
				var result = [];
			
				$.each(ajaxResponse, function(index){
					var obj = ajaxResponse[index];
					let date = new Date(obj.objectionDate);
					let month = date .getMonth() + 1;
				    let formattedDate = date.getDate() + "/" + month + "/" + date.getFullYear()
				    let name = obj.fName +" "+obj.lName;
						result.push([obj.objectionNumber,formattedDate,name,obj.objectionStatus,obj.objectionAppealType,'<td class="text-center">'+
							  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="viewObjDetail(\''+obj.objectionId+'\',\'V\')" title="View Objection"><i class="fa fa-eye"></i></button>']);
	
				});
				table.rows.add(result);
				table.draw();
			}
			else
			{
				displayErrorsOnPage(errorList);
			}
		});
	
	

});
function validateSearchCriteria(errorList){
	var objectionDeptId = $('#objectionDeptId').val();
	if(objectionDeptId == "" ||objectionDeptId == undefined || objectionDeptId == null){
		errorList.push(getLocalMessage('obj.validation.Department'));
	 } 
	return errorList;
}




function viewObjDetail(objectionId,mode){
	
	var requestData = 'objectionId='+objectionId+'&type=V';
	var response = __doAjaxRequest('ObjectionDetails.html?form', 'POST',requestData, false,'html');
	//$('.pagediv').removeClass('ajaxloader');
	$(formDivName).html(response);
}

function backToMainPage(){
	var data={};
	var URL='ObjectionDetails.html?backToMainPage';
	var returnData=__doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}
function resetObjectionSummaryForm() {
	$('input[type=text]').val('');
	$('#objectionDeptId').val('').trigger('chosen:updated');
	$('#serviceId').val('').trigger('chosen:updated');
	$(".alert-danger").hide();
	$("#rtiObjectionSummaryForm").validate().resetForm();
	}

function addObjectionEntryForm(element) {	

	var theForm = '#rtiObjectionSummaryForm';
	var requestData = __serializeForm(theForm);
	var URL = 'ObjectionDetails.html?addObjection';
	var returnData=  __doAjaxRequest(URL,'POST',requestData, false,'html');
	if (returnData) {
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
	}
}
function validateRtiObjEntryForm(errorList) {
	
	var errorList = [];
	var dept = $("#deptId").val();
	var objType = $("#objType").val();
	var propertyno = $("#property").val();
	var rtino = $("#rti").val();
	if(dept == "0" || dept == undefined){
		
		errorList.push(getLocalMessage("rti.validation.department"));
		
	}/*else if($('#deptId option:selected').attr('value') == "200000026")
	{	
		if(propertyno == "" || propertyno == undefined){
			errorList.push(getLocalMessage("rti.validaion.propertyno"));
		}
	}
	else if($('#deptId option:selected').attr('value') == "1548")
	{	
		if(rtino == "" || rtino == undefined){
			errorList.push(getLocalMessage("rti.validation.rtino"));
		}
	}*/
	if (objType == "" || objType == undefined) {
		errorList.push(getLocalMessage("rti.validation.objectiontype"))
	}
	return errorList;
}

function getObjectionServiceByDept(){
	var errorList = [];
	var requestData = {"objectionDeptId":$('#objectionDeptId option:selected').attr('value')}
	var URL = 'ObjectionDetails.html?getObjectionServiceByDepartment';
	var returnData=  __doAjaxRequest(URL,'POST',requestData, false,'html');
	$('#serviceId').html('');
	$('#serviceId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#serviceId').append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.descLangFirst)));
		});
		
	}else{		
		errorList
		.push(getLocalMessage('obj.val.configure.service'));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	 $("#serviceId").trigger("chosen:updated");
}