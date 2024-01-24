$(function () {
    $("#grid").jqGrid({
        url: "FormBuilderUtility.html?gridValueData",
        datatype: "json",
        mtype: "GET",
        colNames: [ getLocalMessage("builder.refno"),getLocalMessage("builder.createdBy"),getLocalMessage("builder.createdDate"), getLocalMessage("master.grid.column.action")],
        colModel: [
            { name: "referenceNo", sortable: true },
            { name: "empName", sortable: true },
            { name: "createdDate",  sortable: true,formatter: dateTemplate },
            //{ name: "departmentNameMar", sortable: true },
            { name: 'referenceNo', index: 'referenceNo', width:50, align: 'center !important', sortable: false,formatter:actionFormatter,search:false}
           // { name: 'smServiceId', index: 'smServiceId', align: 'center', width:30, sortable: false,formatter:editScrutinyMst},
           // { name: 'smServiceId', index: 'smServiceId', align: 'center', width:30, sortable: false,formatter:viewScrutinyMst}
        ],
        pager: "#pagered",
        rowNum: 30,
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
        caption: "Form Builder List"
    }); 
    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
	$("#pagered_left").css("width", "");
}); 

function actionFormatter(cellvalue, options, rowObject){
	
	return "<a class='btn btn-blue-3 btn-sm viewClass' value='"+rowObject.referenceNo+"'  title='View' ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		 "<a class='btn btn-warning btn-sm editClass '  value='"+rowObject.referenceNo+"' title='Edit' ><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
}


$(function() {
	$(document).on('click', '.editClass', function() {

		var errorList = validateServiceSelect();
			if (errorList.length > 0) {
				$(".error-div").show();
				displayErrorsOnPage(errorList);
			

			} else {
				$(".error-div").hide();
				
				var url = "FormBuilderUtility.html?edit";
				
				var referenceId = $(this).attr('value');
				var smServiceId = $("#serviceId").val();
				var returnData ={"serviceCode" : smServiceId, "referenceId" : referenceId, "mode": 'E'};
				
				$.ajax({
					url : url,
					datatype: "json",
			        mtype: "POST",
					data : returnData,
					success : function(response) {						
						var divName = '.content-page';						
						$("#content").html(response);
						$("#content").show();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
				
			}
		
		
	});
});

$(function() {
	$(document).on('click', '.addClass', function() {
		

		var errorList = validateServiceSelect();
			if (errorList.length > 0) {
				$(".error-div").show();
				displayErrorsOnPage(errorList);
			

			} else {
				$(".error-div").hide();
				
				var url = "FormBuilderUtility.html?add";
				
				var referenceId = $(this).attr('value');
				var smServiceId = $("#serviceId").val();
				var returnData ={"serviceCode" : smServiceId, "mode": 'C'};
				
				$.ajax({
					url : url,
					datatype: "json",
			        mtype: "POST",
					data : returnData,
					success : function(response) {						
						var divName = '.content-page';						
						$("#content").html(response);
						$("#content").show();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
				
			}
		
		
		
	});
});


$(function() {
	$(document).on('click', '.viewClass', function() {


		var errorList = validateServiceSelect();
			if (errorList.length > 0) {
				$(".error-div").show();
				displayErrorsOnPage(errorList);
			

			} else {
				$(".error-div").hide();
				
				var url = "FormBuilderUtility.html?edit";
				var referenceId = $(this).attr('value');
				var smServiceId = $("#serviceId").val();
				var returnData ={"serviceCode" : smServiceId, "referenceId" : referenceId, "mode": 'V'};
				
				$.ajax({
					url : url,
					datatype: "json",
			        mtype: "POST",
					data : returnData,
					success : function(response) {						
						var divName = '.content-page';						
						$("#content").html(response);
						$("#content").show();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
				
			}
		
		
		
	});
});

/*function openScrutinyAddForm() {
	var url = "FormBuilderUtility.html?form";
	$.ajax({
		url : url,
		success : function(response) {						
			
			var divName = '.form-div';
			$("#content").html(response);				
			$("#content").show();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
}*/

function searchScrutinyData(Obj){
	
	var errorList = validateServiceSelect();
	if (errorList.length > 0) {
		$(".error-div").show();
		displayErrorsOnPage(errorList);
	

	} else {
		$(".error-div").hide();
		var scrutinyId = $("#serviceId").val();
		//var deptId = $("#depId").val();
		var url = "FormBuilderUtility.html?searchData";
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
			data: { "serviceCode" : scrutinyId},
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

function saveScrutinyLabels(obj,successMessage,actionParam,successUrl)
{	
	
	return doFormAction(obj,successMessage, actionParam, true , successUrl);
	
}

function submitScrutinyLabels(obj,successMessage,actionParam,successUrl)
{	
	var errorList=[];
	$("#frmScrutinyLabel option:selected").each(function () {
		   var $this = $(this);
		   if ($this.length) {
		    var selText = $this.val();
		    if(selText=='-1')
		    {
		    errorList.push(getLocalMessage('select.ans'));
	        displayErrorsOnPage (errorList);
		    }
		   }
		});
	
	if(errorList =='')
		{
		  return doFormAction(obj,successMessage, actionParam, true , successUrl);
		}
	else
		{
		  return false;
		}
	

}

function otherTask() {
	
	var URL = 'FormBuilderUtility.html?getCoordinates';
	var data = {};
	var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
	if(returnData[0]!=0){
		$("#latitudeId").val(returnData);
		$("#latlong").text(returnData);
		//$("#longitudeId").val(returnData[0]!=0?returnData[0]:null);
		 $("#locChnage").val('Y');
	}else{
		$("#latitudeId").val(null);
	//	$("#longitudeId").val(null);
		$("#locChnage").val('N');
	}
}

function downloadTamplate() {
	
	var errorList = validateServiceSelect();
	if (errorList.length > 0) {
		$(".error-div").show();
		displayErrorsOnPage(errorList);
	

	} else {
		var smServiceId = $("#serviceId").val();
		var UPLOADURL = "FormBuilderUtility.html";
		window.location.href = UPLOADURL+"?ExcelTemplateData"+"&serviceCode="+smServiceId;
	}
}


function validateServiceSelect() {
	var errorList = [];

	var smServiceId = $("#serviceId").val();

	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';

	if (smServiceId == "" || smServiceId == null || smServiceId == 'undefined')
		errorList.push(getLocalMessage("service.error.serviceId"));
	return errorList;
}


function uploadExcelFile(obj) {
	
	var errorList = [];
	var fileName = $("#uploadFileName").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorList.push(getLocalMessage("excel.upload.vldn.error"));
	}
	errorList = validateServiceSelect();
	if (errorList.length == 0) {
		$("#filePath").val(fileName);
		var UPLOADURL = "FormBuilderUtility.html";
		var url = UPLOADURL+"?loadExcelData";
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var ajaxResponse = __doAjaxRequestForSave(url, 'post', requestData,
				false, '', obj);
		
		showConfirmBoxFoLabour('Excel Imported Successfully');
}
	else {
		displayErrorsOnPage(errorList);
	}
}


function showConfirmBoxFoLabour(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="exportExcelData()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function exportExcelData(){
	window.location.href='FormBuilderUtility.html';
}
