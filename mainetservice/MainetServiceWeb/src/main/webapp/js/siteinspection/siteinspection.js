function getDeptEmployee(val, param) {

var requestData = {

		"deptId" : $("#deptId").val()
	};

	var URL = 'SiteInspection.html?' + param;
	
	$.ajax({
		url : URL,
		data : requestData,
        mtype: "GET",
		success : function(response) {
			
			$('#visEmpid').html("");
			$('#visEmpid').append(
					$("<option></option>").attr("value", 0).text(getLocalMessage('Select')))
			
			if(response != ""){
				$.each(response, function( index, value ) {						
					$("#visEmpid")
				    .append($("<option></option>")
				    .attr("value",value.empId).attr("code",value.emposloginname)
				    .text(value.empname+" "+value.empmname+" "+value.emplname));
				});
			}
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList
					.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
	
}


function submitSiteInspectionForm(obj) {
	var errorList = [];	
	
	
	 var deptId = $.trim($("#deptId").val());
	 if(deptId==0 || deptId=="")
	    errorList.push(getLocalMessage('siteinspection.vldn.department'));
	 
	 var visEmpid = $.trim($("#visEmpid").val());
	 if(visEmpid==0 || visEmpid=="")
	    errorList.push(getLocalMessage('siteinspection.vldn.Employee'));
	 
	 var visDate = $.trim($("#visDate").val());
	 if(visDate=="")
	    errorList.push(getLocalMessage('siteinspection.vldn.datetime'));
	
	if(errorList.length > 0){ 
		 
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
		
	 }
		
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = __serializeForm(theForm);

	var url	=	$(theForm).attr('action');
		
	var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
	
	loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',$("#appId").val(),$("#labelId").val(),$("#visServiceId").val());
	
	return true;
}

function closeOutErrBox(){
	$('.error-div').hide();
}


$(function() {	
	$(document).on('click', '.genrateLetter', function() {

		var url = "SiteInspection.html?inpesctionLetter";
		var returnData = "";
		
		$.ajax({
			url : url,
			success : function(response) {					
				var divName = '.form-div';					
				$(".widget-content").html(response);
				$(".widget-content").show();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});				
	});			
});

function printContent1(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

function printContent2(el) {
	 
    var  WorkOrderNumber = $("#WorkOrderNumber").val();
	var returnData = 'workOrderNo='+WorkOrderNumber; 
	var url = "WorkOrder.html?update";
	
	$.ajax({
		url : url,
		method: "POST",
		data : returnData,
		success : function(response) {						
			
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
 
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

