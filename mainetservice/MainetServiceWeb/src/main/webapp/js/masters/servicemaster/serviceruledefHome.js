/**
 * @author Harsha
 */

var smServiceId = '';
$(function () {
    $("#grid").jqGrid({
        url: "ServiceMaster.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: [getLocalMessage('master.serviceNameEng'), getLocalMessage('master.serviceNameHindi'),getLocalMessage('master.serviceShortDesc'),getLocalMessage('master.grid.column.chkListVerfAppl'),getLocalMessage('master.serviceMas.status'),getLocalMessage('master.grid.column.action')],
        colModel: [            
            { name: "smServiceName", width: 60, sortable: false,search:true },
            { name: "smServiceNameMar", width: 60, sortable: false,search:true },
            { name: "smShortdesc", width: 20, sortable: false,search:true },
            { name: "smChklstVerifyName", width: 40, sortable: false,search:true },
            { name: 'smServActive', index: 'smServActive', width: 12, align: 'center', edittype:'checkbox',formatter:statusFormatter, editoptions: { value: "Yes:No" },
        		formatoptions: { disabled: false },search:false
			},
            { name: 'smServiceId', index: 'smServiceId', width: 35, align: 'center !important', sortable: false,formatter:actionFormatter,search:false}
        ],
        pager: "#pagered",
        rowNum: 5,
        rowList: [5, 10, 20, 30],
        sortname: "cpmId",
        sortorder: "desc",
        height:'auto',
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
        caption: getLocalMessage('master.serviceMas.gridheader')
    }); 
    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
	$("#pagered_left").css("width", "");
}); 


function actionFormatter(cellvalue, options, rowObject){
	
	return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewService('"+rowObject.smServiceId+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		 "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editService('"+rowObject.smServiceId+"')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
}

function statusFormatter(cellvalue, options, rowdata) {
	var activeId = $('#activeId').val();
	if(rowdata.smServActive == activeId){
		return "<a title='Service is Active' alt='Service is Active' value='A' class='fa fa-check-circle fa-2x green' href='#'></a>";	
	}else{
		return "<a title='Service is Inactive' alt='Service is Inactive' value='A' class='fa fa-times-circle fa-2x red' href='#'></a>";
	}
		

}

function editService(serviceId){
	
		var url = "ServiceMaster.html?formForUpdate";
		var requestData = "serviceId="+serviceId+"&mode=Edit";
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {
				$(".content").html(response);				
				$(".content").show();	
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
};
		

	
	function viewService(serviceId) {
		
		var url = "ServiceMaster.html?formForUpdate";
		var returnData = "serviceId="+serviceId+"&mode=View";
		$.ajax({
			url : url,
			data : returnData,
			success : function(response) {
				$(".content").html(response);				
				$(".content").show();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});			
	};		

	
/*	function deleteService(serviceId) {
		var returnData = "serviceId="+serviceId;
		var checkUrl = "ServiceMaster.html?checkForTransactionExist";
		
		$.ajax({
			url : checkUrl,
			data : returnData,
			success : function(response) {
				if(response != 0) {
					$(errMsgDiv).html("<h4 class=\"text-center text-blue-2 padding-10\">Transaction Exist, cannot delete.</h4>");
					$(errMsgDiv).show();
					showModalBox(errMsgDiv);
					return false;
				} else {					
					showConfirmBox(serviceId);
				}
				$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	};*/		


function showConfirmBox(serviceId){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Are you sure want to delete?</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="deleteData('+serviceId+')"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function deleteData(serviceId){

	var url = "ServiceMaster.html?delete";
	var requestData = "serviceId="+serviceId;
	$.ajax({
		url : url,
		data : requestData,
		success : function(response) {		
			//$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			searchServiceMst();
			$.fancybox.close();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	
	
}

function closeErrBox() {
	$('.error-div').hide();
}


function searchServiceMst() {

	
	var deptId = $("#deptId").val();
	var serviceId = $("#serviceId").val();
	var errorList = [];
	if(deptId == '') {
		errorList.push(getLocalMessage("service.error.deptId"));
		showServiceError(errorList);
	}
	else{
		if(serviceId == -1){
			errorList.push(getLocalMessage("service.error.serviceId"));
			showServiceError(errorList);
			}
		else{
			$('.error-div').hide();
			var url = "ServiceMaster.html?searchServiceMst";	
			var requestData = "deptId="+deptId+"&serviceId="+serviceId;
			$.ajax({
				url : url,
				method: "POST",
				data : requestData,
				success : function(response) {
					$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});	
		}

	}

}

function hideDiv(){
	$('.error-div').hide();
}

function openAddForm(){

	var url = "ServiceMaster.html?form";
	$.ajax({
		url : url,
		success : function(response) {
			$(".content").html(response);				
			$(".content").show();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
}

function refreshServiceData() {
	 $('.error-div').hide();
	 $("#serviceId").text(''); 
	 $("#serviceId").html(''); 
	 var deptId = $('#deptId').val();
	 var requestData = "deptId="+deptId;
	 
	 if($('#deptId').val() > 0){
		 var url = "ServiceMaster.html?refreshServiceData";
		 var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,''); 
		 if(returnData != ""){
			 
			 $('#serviceId').append($("<option></option>").attr("value",-1).text("Select Service"));
			 $('#serviceId').append($("<option></option>").attr("value",-2).text("All"));
			 
			 $.each(returnData, function( index, value ) {
				 if($("#langId").val() == 1){
					 $('#serviceId')
					    .append($("<option></option>")
					    .attr("value",value.smServiceId).attr("code",value.smShortdesc)
					    .text(value.smServiceName));
				 }else{
					 $('#serviceId')
					    .append($("<option></option>")
					    .attr("value",value.smServiceId).attr("code",value.smShortdesc)
					    .text(value.smServiceNameMar));
					
				 }
			 });
			 
		 }else{
			 var errorList = [];
			 $('#serviceId')
			    .append($("<option></option>")
			    .attr("value",-1).text("Select Service"));
			 	errorList.push(getLocalMessage("service.error.noservice"));
				showServiceError(errorList);
		 }
		 $("#serviceId").trigger("chosen:updated");
		$(".chosen-select-no-results").trigger("chosen:updated");
	}
}

function resetHomePage() {
	$("#grid").jqGrid("clearGridData");
	$('#serviceId').val('').trigger('chosen:updated');
	$('#serviceId').text('').trigger('chosen:updated');
	$('#serviceId')
    .append($("<option></option>")
    .attr("value",-1).text("Select Service"));
	$(".chosen-select-no-results").trigger("chosen:updated");
	$('#deptId').val('').trigger('chosen:updated');
	$('.error-div').hide();
}

function showServiceError(errorList){
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#serviceRuleDefMas").html(errMsg);
	$("#serviceRuleDefMas").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
}


