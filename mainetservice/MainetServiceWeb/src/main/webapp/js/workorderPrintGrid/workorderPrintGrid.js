$(function () {
    $("#grid").jqGrid({
        url: "WorkOrder.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: [getLocalMessage("master.Application"), getLocalMessage("master.ConsumerName"), getLocalMessage("master.PlumberName"),getLocalMessage("master.PRINT"),getLocalMessage("view.msg")],
        colModel: [            
            { name: "applicationId", width: 10, align: 'center', sortable: false },
            { name: "consumerName", width: 23, align: 'center', sortable: false },
            { name: "plumberFName", width: 22, align: 'center', sortable: false},
            { name: 'applicationId', width: 1, align: 'center',  sortable: false,formatter:editServiceMst},
            { name: 'woPrintFlg',  width: 1, align: 'center', sortable: false,formatter:viewServiceMst},
           
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
        editurl:"ServiceMaster.html?update",
        caption: "Search Master List"
    }); 
}); 

 function returnisdeletedUrl(cellValue, options, rowdata, action) {
	if (rowdata.statusflag =="Y") {
		return "<a href='#'  class='fa fa-check-circle fa-1x green'   value='"+rowdata.statusflag+"'  alt='Remark is Active' title='Remark is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-1x red' value='"+rowdata.statusflag+"' alt='Remark is  INActive' title='Remark is Active'></a>";
	} 
 }
function editServiceMst(cellValue, options, rowdata, action) {
	//smServiceId = rowdata.smServiceId;
    return "<a href='#'  return false; class='editClass btn btn-success' title='Print' value='"+rowdata.applicationId+"'><i class='fa fa-print fa-1x text-white'></i></a>";
}
function viewServiceMst(cellValue, options, rowdata, action) {
	//smServiceId = rowdata.smServiceId;
	
    		 return "<a href='#'  return false; class='viewClass  btn btn-info' title='Reprint' value='"+rowdata.applicationId+"'><i class='fa fa-file-text-o fa-1x text-white'></i></a>";
}

$(function() {
	
	$(document).on('click', '.editClass', function() {
		var applicationId = $(this).attr('value');
		var returnData = "applicationId="+applicationId; 
		var url = "WorkOrder.html?print";
		
		$.ajax({
			url : url,
			method: "POST",
			data : returnData,
			success : function(response) {						
				
				$("#widget2").html(response);
				$("#showbackbuttton").hide();
				/* $("#content").show();  */
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
	});		
	
});

$(function() {
	
	$(document).on('click', '.viewClass', function() {
		var applicationId = $(this).attr('value');
		var returnData = "applicationId="+applicationId; 
		var url = "WorkOrder.html?print";
		
		$.ajax({
			url : url,
			method: "POST",
			data : returnData,
			success : function(response) {						
				
				$("#widget2").html(response);
				$("#hideprintbuttton").hide();
				/* $("#content").show();  */
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
	});		
	
});
function searchServiceMst2(obj) {
	
	var deptId = 200000001;
	var serviceId = $("#artServiceId").val();
	
	
	if(deptId == '') {
		var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Department.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;
	}
	
	if(serviceId == '') {
		serviceId = -1;
		var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Service.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;
	}
	$('.error-div').hide();
	var url = "WorkOrder.html?searchServiceMst";
	var returnData = "deptId="+deptId+"&serviceId="+serviceId;
	
	$.ajax({
		url : url,
		method: "POST",
		data : returnData,
		success : function(response) {
			/*alert(response);*/
            // Handle the case where the user may not belong to any groups
            if( response.length == 0 )
            	{
                            var errMsg = '<div class="error-div alert alert-danger alert-dismissible">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/> Select  Department and Service Combination  Remarks Doesnot Exist.</li></ul></div>';
                    		$(".error-div").html(errMsg);
                    		$(".error-div").show();
                    		/* $("#serviceRuleDefMas").show(); */
            }
			$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
}

function resetHomePage() {
	
	$("#grid").jqGrid("clearGridData");
	
	$('.error-div').hide();
}

function refreshServiceData(obj) {
	var url = "ServiceMaster.html?refreshServiceData";
	var deptId = $(obj).val();
	
	var returnData = "deptId="+deptId
	
	$.ajax({
		url : url,
		method: "POST",
		data : returnData,
		success : function(response) {
			
			$("#serviceId").html('');
			$('#serviceId')
		    .append($("<option></option>")
		    .attr("value","").attr("code","")
		    .text(getLocalMessage('Select')));
			
			if($("#langId").val() == 1) {
				if(response != ""){
					$.each(response, function( index, value ) {
						
						$('#serviceId')
					    .append($("<option></option>")
					    .attr("value",value.smServiceId).attr("code",value.smShortdesc)
					    .text(value.smServiceName));						
					});
					
					$('#serviceId')
				    .append($("<option></option>")
				    .attr("value",-1).text("All"));
				}
				
			} else {
				
				if(response != ""){					
					$.each(response, function( index, value ) {
						$('#serviceId')
					    .append($("<option></option>")
					    .attr("value",value.smServiceId).attr("code",value.smShortdesc)
					    .text(value.smServiceNameMar));
					});
					
					$('#serviceId')
				    .append($("<option></option>")
				    .attr("value",-1).text("All"));			
				}
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
}

