var seId = '';
$(function () {
    $("#grid").jqGrid({
        url: "SMSAndEmail.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: [getLocalMessage("dept.master.deptLoc.deptName"),getLocalMessage("master.serviceName"),getLocalMessage("master.TemplateType"),getLocalMessage("master.AlertType"),getLocalMessage("master.grid.column.action")],
        colModel: [
            { name: "deptDesc", width : 25, sortable: true,search : true },
            { name: "serviceDesc", width : 25, sortable: true,search : true },
            /*{ name: "transDesc", width : 25, sortable: true,search : true},  "Transaction Type",*/
            { name: "templateTypeDesc", width : 20, align: 'center', sortable: true,search : true },
            { name: "alertTypeDesc", width : 20, align: 'center' ,sortable: true,search : true },
            { name: 'seId', index: 'seId', align: 'center !important', sortable: false, width : 25,formatter:actionFormatter,search : false}
        ],
        pager: "#pagered",
        rowNum: 30,
        rowList: [5, 10, 20, 30],
        sortname: "",
        //sortorder: "asc",
        height:'auto',
        width:'800',
        viewrecords: true,
        gridview: true,
        loadonce: true,
        jsonReader : {
            root: "rows",
            page: "page",
            total: "total",
            records: "records", 
            repeatitems: false,
           }, 
        autoencode: true,
        editurl:"SMSAndEmail.html?update",
        caption: getLocalMessage("common.event.smsAndEmailEventlist")
    });
    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
	$("#pagered_left").css("width", "");
}); 


function actionFormatter(cellvalue, options, rowObject){
	return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"editPrefix('"+rowObject.seId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
	"<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editPrefix('"+rowObject.seId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> "+
	 "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"deletePrefix('"+rowObject.seId+"')\"><i class='fa fa-trash' aria-hidden='true'></i></a> "

}

function editPrefix(seId,mode){
	var smsMailId= seId;
	var requestData = {"smsMailId" : smsMailId,"mode" : mode };
	var response = __doAjaxRequest("SMSAndEmail.html?formForUpdate", 'POST',requestData, false, '');
	$("#messageType").prop("disabled",true).trigger("chosen:updated");
	$(".smsEmailForm").html(response);
	if(mode=="view"){
		$("#form3 :input").prop("disabled", true);
		$('#form3 select').prop('disabled', true);
		$('#form3 textarea').prop('disabled', true);
		$('#form3 checkbox').prop('disabled', true);
		$("input[type=radio]").attr('disabled', true);
		$("#submitButton").hide();
		$("#resetButton").hide();
		$("#form3 text").prop("disabled", true);
		$("#backBtn").prop("disabled", false);
		
		
	}
	}


function deletePrefix(seId,mode){
	showDelConfirmBox(seId)
}


function showDelConfirmBox(seId){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	
	if(seId != 0){
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+getLocalMessage("sms.and.email.val.del")+'</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="deleteData('+seId+')"/>'+
		'</div>';		
	}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function deleteData(seId){
		postdata='seId='+seId;
		var response = __doAjaxRequest('SMSAndEmail.html?deleteData','POST', postdata, false, '');
		$.fancybox.close();
		$("#grid").jqGrid('setGridParam', {datatype : 'json'}).trigger('reloadGrid');
	}

function fn_setService(obj,id) {
	var langId = $('#langId').val();
	var deptId = obj.value;
	$(".serviceList").find('option:gt(0)').remove();
	
	if (deptId > 0) {
		var postdata = 'deptId=' + deptId;
		
		var json = __doAjaxRequest('SMSAndEmail.html?serviceList',
				'POST', postdata, false, 'json');
	    
		var  optionsAsString='';
		for(var i = 0; i < json.length; i++) {
			if(langId == 1){
				optionsAsString += "<option value='" + json[i].lookUpId + "'>" + json[i].lookUpCode + "</option>";
			}else{
				optionsAsString += "<option value='" + json[i].lookUpId + "'>" + json[i].descLangSecond + "</option>";
			}
		    
		}
		$(".serviceList").append( optionsAsString );
		$("#serviceId").append($("<option></option>").attr("value","").text("Not Applicable"))
		$('#serviceId').chosen().trigger('chosen:updated');

	}
}


function searchTemplate(){
   var errorList = [];
   var transactionaltype=null;
   var alertType=null;
   var deptId= $("#deptId").val(); 
   var serviceId = $("#serviceId").val(); 
   var eventId= $("#eventId").val(); 
   var messageType = $("#messageType").val();
   if($("input[name='entity.alertType']").is(":checked")){
	  alertType = $("input[name='entity.alertType']:checked").val();  
	 }
		var requestData = {
				"deptId" : deptId,
				"serviceId" : serviceId,
				"eventId" : eventId,
				"messageType" : messageType,
				"alertType" : alertType
			};
		var result = __doAjaxRequest("SMSAndEmail.html?searchTemplate", 'POST',requestData, false, '');
		if (result != "") {
			$("#grid").jqGrid('setGridParam', {datatype : 'json'}).trigger('reloadGrid');
			$('#errorDivId').hide();
			return true;
		 }	else{
			errorList.push(getLocalMessage(getLocalMessage("sms.and.email.val.noRecFound")));
			if(errorList.length > 0){ 
		    	showErrorBox(errorList);
		    return false;
		    }
		
		}
}



  function createTemplate(){
		var url = "SMSAndEmail.html?form";
	    $.ajax({
			url : url,
			success : function(response) {					
				$(".smsEmailForm").html(response);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
  }
$(function(e) {
$("input[name='entity.alertType']").click(function(e) {
	 $(".attri").show();
	if ($(this).attr('value') == 'S') {
		$('#smsTemplate').show();
		$('#emailTemplate').hide();
		
	}
	if ($(this).attr('value') == 'E') {
		$('#smsTemplate').hide();
		$('#emailTemplate').show();
	}
	if ($(this).attr('value') == 'B') {
		$('#smsTemplate').show();
		$('#emailTemplate').show();
	}
	if ($(this).attr('value') == 'S') {
		$('#smsTemplateEdit').show();
		$('#emailTemplateEdit').hide();
		
	}
	if ($(this).attr('value') == 'E') {
		$('#smsTemplateEdit').hide();
		$('#emailTemplateEdit').show();
	}
	if ($(this).attr('value') == 'B') {
		$('#smsTemplateEdit').show();
		$('#emailTemplateEdit').show();
	}
	
	
	});

$("input[name='modeType']").click(function(e) {

	if ($(this).attr('value') == 'A') {
		$('.error-div').remove();
		$('#addMode').find('textarea').val('');
		$('#addMode').find('input[type=text]').val('');
		 $('select').find('option').prop("selected", false);
	     $('input[type=radio]').not('input[name=modeType]').prop("checked", false);
		$('#addMode').show();
		$('#editMode').hide();
		
	}
	if ($(this).attr('value') == 'E') {
		$('.error-div').remove();
		$('#editMode').find('textarea').val('');
		$('#editMode').find('input[type=text]').val('');
		$('select').find('option').prop("selected", false);
	    $('input[type=radio]').not('input[name=modeType]').prop("checked", false);
		$('#addMode').hide();
		$('#editMode').show();
	}
	
});


});

$(document).ready(
		function() {
			$(".attri").show();
			$('#addMode').hide();
			$('#editMode').hide();
			$('#smsTemplate').hide();
			$('#emailTemplate').hide();
			
			var selmodeType;
			var editType = $("#editModeSelect").val();
			var addType = $("#addModeSelect").val();

			var mode = $("#mode").val();
			
			if(mode == "E"){
				$("#messageType").prop("disabled",true);
			}
			if (editType) {
				selmodeType = editType;
			}
			if (addType) {
				selmodeType = addType;
			}
	   $('input[name=modeType][value="' + selmodeType + '"]').prop('checked', true);
		var alertType = $("#AlertType").val();
		if (mode == "E") {
			 $(".attri").show();
			if (alertType == "S") {
				$('#smsTemplateEdit').show();
				$('#emailTemplateEdit').hide();
			}
			if (alertType == "E") {
				$('#smsTemplateEdit').hide();
				$('#emailTemplateEdit').show();
			}
			if (alertType == "B") {
				$('#smsTemplateEdit').show();
				$('#emailTemplateEdit').show();
			}
			}
			
			
			if (mode == "A") {
			    $(".attri").hide();
				$('#editMode').hide();
				$('#addMode').show();
				if (alertType == "S") {
					 $(".attri").show();
					$('#smsTemplate').show();
					$('#emailTemplate').hide();
				}
				if (alertType == "E") {
					 $(".attri").show();
					$('#smsTemplate').hide();
					$('#emailTemplate').show();
				}
				if (alertType == "B") {
					 $(".attri").show();
					$('#smsTemplate').show();
					$('#emailTemplate').show();
				}
			}
			setServiceurl();
			$('#deptId').chosen().trigger('chosen:updated');
			$("#serviceId").chosen().trigger('chosen:updated');
			$("#eventId").chosen().trigger('chosen:updated');
			$("#messageType").chosen().trigger('chosen:updated');
		});

function setServiceurl() {
	
	var deptIDEdit ;
     var mode = $("#mode").val();
	if(mode =="A"){
		deptIDEdit=  $("#deptIDAdd").val();
		   
	}else if(mode =="E"){
		deptIDEdit=   $("#deptIDEdit").val();
	}
	if (deptIDEdit > 0) {
		var postdata = 'deptId=' + deptIDEdit;

		var json = __doAjaxRequest('SMSAndEmail.html?serviceList', 'POST',
				postdata, false, 'json');
		var optionsAsString = '';
		for (var i = 0; i < json.length; i++) {
			optionsAsString += "<option value="+ json[i].lookUpId +">"
					+ json[i].lookUpCode + "</option>";
		}
		$("#serviceId").append($("<option></option>").attr("value","").text("Not Applicable"))
		$(".serviceList").append(optionsAsString);
		var serviceid =  $('#selectedServiceId').val();
		$(".serviceList option[value=" + serviceid + "]").prop('selected',true);
		$('#serviceId').chosen().trigger('chosen:updated');
	}
}


function resetSearchList(){
	$("#deptId").val('0').trigger('chosen:updated');
	$("#serviceId").val('0').trigger('chosen:updated');
	$("#eventId").val('0').trigger('chosen:updated');
	$("#messageType").val('0').trigger('chosen:updated');
	 //$('#grid').jqGrid('clearGridData').trigger('reloadGrid');
     //$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
}


function resetFormData(){
	$("#deptId").val('0').trigger('chosen:updated');
	$("#serviceId").val('0').trigger('chosen:updated');
	$("#eventId").val('0').trigger('chosen:updated');
	$("#messageType").val('0').trigger('chosen:updated');  
	
	$("#smsTemplate").hide();
	$("#emailTemplate").hide();
	
}



function saveForm(element) {
		 return saveOrUpdateForm(element, 'Save Successfully', 'SMSAndEmail.html?complete','saveform');
}


function showErrorBox(errorList)
{
	 var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
       errMsg += '</ul>';
        $('#errorId').html(errMsg);
		$('#errorDivId').show();	 
}
