$(function() {
    $("#grid").jqGrid(
	    {
		url : "WaterWorkOrderPrinting.html?getGridData",
		datatype : "json",
		mtype : "GET",
		colNames : [ getLocalMessage("master.Application"),
			getLocalMessage("master.ConsumerName"),
			getLocalMessage("master.PlumberName"),
			getLocalMessage("master.PRINT"),
			getLocalMessage("view.msg") ],
		colModel : [ {
		    name : "applicationId",
		    width : 10,
		    align : 'center',
		    sortable : false
		}, {
		    name : "consumerName",
		    width : 23,
		    align : 'center',
		    sortable : false
		}, {
		    name : "plumberFName",
		    width : 22,
		    align : 'center',
		    sortable : false
		}, {
		    name : 'applicationId',
		    width : 1,
		    align : 'center',
		    sortable : false,
		    formatter : editServiceMst
		}, {
		    name : 'woPrintFlg',
		    width : 1,
		    align : 'center',
		    sortable : false,
		    formatter : viewServiceMst
		},

		],
		pager : "#pagered",
		rowNum : 5,
		rowList : [ 5, 10, 20, 30 ],
		sortname : "cpmId",
		sortorder : "desc",
		height : 'auto',
		viewrecords : true,
		gridview : true,
		loadonce : true,
		jsonReader : {
		    root : "rows",
		    page : "page",
		    total : "total",
		    records : "records",
		    repeatitems : false,
		},
		autoencode : true,
		editurl : "ServiceMaster.html?update",
		caption : "Search Master List"
	    });
});

function returnisdeletedUrl(cellValue, options, rowdata, action) {
    if (rowdata.statusflag == "Y") {
	return "<a href='#'  class='fa fa-check-circle fa-1x green'   value='"
		+ rowdata.statusflag
		+ "'  alt='Remark is Active' title='Remark is Active'></a>";
    } else {
	return "<a href='#'  class='fa fa-times-circle fa-1x red' value='"
		+ rowdata.statusflag
		+ "' alt='Remark is  INActive' title='Remark is Active'></a>";
    }
}
function editServiceMst(cellValue, options, rowdata, action) {
    // smServiceId = rowdata.smServiceId;
    return "<a href='#'  return false; class='editClass btn btn-success' title='Print' value='"
	    + rowdata.applicationId
	    + "'><i class='fa fa-print fa-1x text-white'></i></a>";
}
function viewServiceMst(cellValue, options, rowdata, action) {
    // smServiceId = rowdata.smServiceId;

    return "<a href='#'  return false; class='viewClass  btn btn-info' title='Reprint' value='"
	    + rowdata.applicationId
	    + "'><i class='fa fa-file-text-o fa-1x text-white'></i></a>";
}

$(function() {

    $(document)
	    .on(
		    'click',
		    '.editClass',
		    function() {
			var applicationId = $(this).attr('value');
			var returnData = "applicationId=" + applicationId;
			var url = "WaterWorkOrderPrinting.html?print";

			$
				.ajax({
				    url : url,
				    method : "POST",
				    data : returnData,
				    success : function(response) {

					$("#widget2").html(response);
					$("#showbackbuttton").hide();
					/* $("#content").show(); */
				    },
				    error : function(xhr, ajaxOptions,
					    thrownError) {
					var errorList = [];
					errorList
						.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				    }
				});

		    });

});

$(function() {

    $(document).on('click', '.viewClass', function() {
	
	var applicationId = $(this).attr('value');
	var returnData = "applicationId=" + applicationId;
	var url = "WaterWorkOrderPrinting.html?print";

	/*
	 * $.ajax({ url : url, method: "POST", data : returnData, success :
	 * function(response) {
	 * 
	 * $("#widget2").html(response); $("#hideprintbuttton").hide();
	 * $("#content").show(); }, error : function(xhr, ajaxOptions,
	 * thrownError) { var errorList = [];
	 * errorList.push(getLocalMessage("admin.login.internal.server.error"));
	 * showError(errorList); } });
	 */
	var response = __doAjaxRequest(url, 'POST', returnData, false, 'html');
	$("#widget2").html(response);
	$("#hideprintbuttton").hide();
	$("#content").show();

    });

});
/*
 * function searchServiceMst2(obj) {
 * 
 * var deptId = 200000001; var serviceId = $("#artServiceId").val();
 * 
 * if (deptId == '') { var errMsg = '<div class="closeme"> <ul><li><img
 * alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()"
 * width="32"/>Please Select a Department.</li></ul></div>';
 * $("#serviceRuleDefMas").html(errMsg); $("#serviceRuleDefMas").show(); return
 * false; }
 * 
 * if (serviceId == '') { serviceId = -1; var errMsg = '<div class="closeme">
 * <ul><li><img alt="Close" title="Close" src="css/images/close.png"
 * onclick="closeErrBox()" width="32"/>Please Select a Service.</li></ul></div>';
 * $("#serviceRuleDefMas").html(errMsg); $("#serviceRuleDefMas").show(); return
 * false; } $('.error-div').hide(); var url =
 * "WaterWorkOrderPrinting.html?searchServiceMst"; var returnData = "deptId=" +
 * deptId + "&serviceId=" + serviceId; $ .ajax({ url : url, method : "POST",
 * data : returnData, success : function(response) { alert(response); // Handle
 * the case where the user may not belong to any // groups if (response.length ==
 * 0) { var errMsg = '<div class="error-div alert alert-danger
 * alert-dismissible"> <ul><li><img alt="Close" title="Close"
 * src="css/images/close.png" onclick="closeErrBox()" width="32"/> Select
 * Department and Service Combination Remarks Doesnot Exist.</li></ul></div>';
 * $(".error-div").html(errMsg); $(".error-div").show();
 * $("#serviceRuleDefMas").show(); } $("#grid").jqGrid('setGridParam', {
 * datatype : 'json' }).trigger('reloadGrid'); }, error : function(xhr,
 * ajaxOptions, thrownError) { var errorList = []; errorList
 * .push(getLocalMessage("admin.login.internal.server.error"));
 * showError(errorList); } }); }
 */

function resetHomePage() {

    $("#grid").jqGrid("clearGridData");

    $('.error-div').hide();
}

function refreshServiceData(obj) {
    var url = "ServiceMaster.html?refreshServiceData";
    var deptId = $(obj).val();

    var returnData = "deptId=" + deptId

    $
	    .ajax({
		url : url,
		method : "POST",
		data : returnData,
		success : function(response) {

		    $("#serviceId").html('');
		    $('#serviceId')
			    .append(
				    $("<option></option>").attr("value", "")
					    .attr("code", "").text(
						    getLocalMessage('Select')));

		    if ($("#langId").val() == 1) {
			if (response != "") {
			    $.each(response, function(index, value) {

				$('#serviceId').append(
					$("<option></option>").attr("value",
						value.smServiceId).attr("code",
						value.smShortdesc).text(
						value.smServiceName));
			    });

			    $('#serviceId').append(
				    $("<option></option>").attr("value", -1)
					    .text("All"));
			}

		    } else {

			if (response != "") {
			    $.each(response, function(index, value) {
				$('#serviceId').append(
					$("<option></option>").attr("value",
						value.smServiceId).attr("code",
						value.smShortdesc).text(
						value.smServiceNameMar));
			    });

			    $('#serviceId').append(
				    $("<option></option>").attr("value", -1)
					    .text("All"));
			}
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

function closeErrBox() {
    $(".error-div").hide();
}

function searchServiceMst2() {
    
    var errorList = [];
    var deptId = 200000001;
    var serviceId = $("#artServiceId").val();
    if ($("#artServiceId ")[0].selectedIndex <= 0) {
	errorList.push(getLocalMessage("water.select.service.name"));
    }

    if (errorList.length == 0) {
	var table = $('#datatables').DataTable();
	table.rows().remove().draw();
	$(".warning-div").hide();
	var requestData = "deptId=" + deptId + "&serviceId=" + serviceId;
	var ajaxResponse = doAjaxLoading(
		'WaterWorkOrderPrinting.html?searchServiceMst', requestData,
		'html');
	var prePopulate = JSON.parse(ajaxResponse);
	var result = [];
	$
		.each(
			prePopulate,
			function(index) {
			    var obj = prePopulate[index];
			    result
				    .push([

					    obj.applicationId,
					    obj.consumerName,
					    obj.plumberFName,
					    '<td class="text-center">'

						    + '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="printApplicantData(\''
						    + obj.applicationId
						    + '\')"  title="Print"><i class="fa fa-pencil-square-o"></i></button>',
					    '</td>'
						    + '<td class="text-center">'
						    + '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="printApplicantData(\''
						    + obj.applicationId
						    + '\')" title="View"><i class="fa fa-eye"></i></button>',

					    '</td>'

				    ]);
			});
	table.rows.add(result);
	table.draw();
	if (prePopulate.length == 0) {
	    errorList.push(getLocalMessage("water.nodata.search.criteria"));
	    displayErrorsOnPage(errorList);
	    $("#errorDiv").show();
	} else {
	    $("#errorDiv").hide();
	}
    } else {
	displayErrorsOnPage(errorList);
    }
}

function printApplicantData(applicationId) {
    
    var divName = formDivName;
    var returnData = data = {
	"applicationId" : applicationId,

    };
    var url = "WaterWorkOrderPrinting.html?print";

    var response = __doAjaxRequest(url, 'POST', returnData, false, 'html');
    $
	    .ajax({
		url : url,
		method : "POST",
		data : returnData,
		success : function(response) {

		    $("#widget2").html(response);
		    $("#showbackbuttton").hide();
		},
		error : function(xhr, ajaxOptions, thrownError) {
		    var errorList = [];
		    errorList
			    .push(getLocalMessage("admin.login.internal.server.error"));
		    showError(errorList);
		}
	    });

}

function printContent2(el) {
    
    var WorkOrderNumber = $("#WorkOrderNumber").val();
    var returnData = 'workOrderNo=' + WorkOrderNumber;
    var url = "WorkOrder.html?update";

    $
	    .ajax({
		url : url,
		method : "POST",
		data : returnData,
		success : function(response) {

		},
		error : function(xhr, ajaxOptions, thrownError) {
		    var errorList = [];
		    errorList
			    .push(getLocalMessage("admin.login.internal.server.error"));
		    showError(errorList);
		}
	    });

    var restorepage = document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML = printcontent;
    window.print();
    document.body.innerHTML = restorepage;
}