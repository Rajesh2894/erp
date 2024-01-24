var selectedIds = new Array();
var eventId = '';
var serviceEventId = '';

$(document).ready(function() {
	$('#service-div').hide();
	$('#role-div').hide();
	$('#createData').hide();
	$('#editData').hide();
	$('#viewData').hide();
});

$(function() {
	$("#grid").jqGrid({
		url : "RolesMaster.html?getGridData",
		datatype : "json",
		mtype : "GET",
		colNames : [ "Decision", "Value" ],
		colModel : [ {
			name : "decisionDescFirst",
			width : 55,
			sortable : true,
			search : true
		}, {
			name : "decisionValue",
			width : 55,
			sortable : false,
			search : true
		} ],
		pager : "#pagered",
		rowNum : 5,
		rowList : [ 5, 10, 20, 30 ],
		sortname : "eventId",
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
		editurl : "RolesMaster.html?update",
		caption : getLocalMessage("Decision List")
	});
	jQuery("#grid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});

function statusFormatter(cellvalue, options, rowdata) {
	if (rowdata.isdeleted == 'N') {
		return "<a title='Event is Active' alt='Event is Active' value='Y' class='fa fa-check-circle fa-2x green' href='#'></a>";
	} else {
		return "<a title='Event is Inactive' alt='Event is Inactive' value='N' class='fa fa-times-circle fa-2x red' href='#'></a>";
	}
}

$(function() {
    $("#createData").click(function() {
        var errorList = [];
        var dpDeptId = $('#dpDeptId').val();
        var serviceId = $('#serviceId').val();
        var roleId = $('#roleId').val();
        var serviceIdLength = $('#serviceId option').length;
        var roleIdLength = $('#roleId option').length;

        if (dpDeptId === "") {
            errorList.push(getLocalMessage("workflow.type.val.select.dept"));
        } else if (serviceIdLength <= 1 && roleIdLength > 1) {
            errorList.push(getLocalMessage("workflow.type.val.no.service") +
                $('#dpDeptId option:selected').text() +
                getLocalMessage(",please add service to the department to add decision"));
        } else if (serviceIdLength > 1 && roleIdLength <= 1) {
            errorList.push(getLocalMessage("No Roles found for the department") +
                $('#dpDeptId option:selected').text() +
                getLocalMessage(",please add roles to the department to add decision"));
        } else if (!(serviceId !== "" && serviceId != 0 && serviceId != null)) {
            errorList.push(getLocalMessage("workflow.type.val.select.service"));
        } else if (!(roleIdLength > 1 && roleId !== "" && roleId != 0 && roleId != null)) {
            errorList.push(getLocalMessage("Please select Role"));
        } else {
            var requestData = {
                "dpDeptId": dpDeptId,
                "serviceId": serviceId,
                "roleId": roleId
            };
            var url = "RolesMaster.html?validateDescision";
            var response=__doAjaxRequest(url,'post',requestData,false,'json');
            if(response == false){
				errorList.push(getLocalMessage("Decision Already Exist Against Selected Department, Service And Role"));
				showRoleMstError(errorList);
			}else{
				 var url = "RolesMaster.html?form";
		            $.ajax({
		                url: url,
		                data: requestData,
		                success: function(response) {
		                    var divName = '.form-div';
		                    $(".widget-content").html(response);
		                    $(".widget-content").show();
		                    $('.error-div').hide();
		                },
		                error: function(xhr, ajaxOptions, thrownError) {
		                    errorList.push(getLocalMessage("admin.login.internal.server.error"));
		                    showRoleMstError(errorList);
		                }
		            });
			}
           
        }

        if (errorList.length > 0) {
            showRoleMstError(errorList);
        }
    });
});


function actionFormatter(cellvalue, options, rowObject) {
	return "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"showConfirmBox('"
			+ rowObject.serviceEventId
			+ "')\"><i class='fa fa-trash' aria-hidden='true'></i></a>";
}

function updateViewForm(mode) {

	var errorList = [];
	var deptId = $('#dpDeptId').val();
	var serviceId = $('#serviceId').val();
	var roleId = $('#roleId').val();

	if (deptId == "" && serviceId == "") {
		errorList
				.push(getLocalMessage("workflow.type.val.select.dept.service"));
		showRoleMstError(errorList);
	} else {
		var requestData = {
			"deptId" : deptId,
			"serviceId" : serviceId,
			"roleId" : roleId,
			"mode" : mode
		};
		var url = "RolesMaster.html?formforUpdate";
		var response = __doAjaxRequest(url, 'post', requestData, '', '');

		if (response != null || response != "") {
			$(".widget-content").html(response);
			$(".widget-content").show();
		} else {
			errorList
					.push(getLocalMessage("workflow.type.val.events.not.mapped"));
			showRoleMstError(errorList);
		}
	}
}

function closeOutErrBox() {
	$('#errorDivDeptMas').hide();
}

function getServicesAndRoles() {
	$('#serviceId').val("");
	$('#roleId').val("");
	$("#errorDivDeptMas").hide();
	if ($('#dpDeptId').val() != 0) {
		var requestData = {
			"deptId" : $('#dpDeptId').val()
		}
		var result = __doAjaxRequest("RolesMaster.html?servicesAndRoles",
				'post', requestData, false, 'json');
		if (result != null && result != "" && result.services != null
				&& result.services != "" && result.roles != null
				&& result.roles != "") {
			$('#serviceId').html('');
			$('#roleId').html('');
			$('#service-div').show();
			$('#role-div').show()
			$('#serviceId').append(
					$("<option></option>").attr("value", "0").text(
							getLocalMessage('workflow.form.select.service')));
			$('#roleId').append(
					$("<option></option>").attr("value", "0").text(
							getLocalMessage('workflow.form.select.role')));
			$.each(result.services, function(index, service) {
				 const text = $('#langId').val() == 1 ? service[1] : service[2];
				 $('#serviceId').append($("<option></option>").attr("value", service[0]).text(text));
			});
			$.each(result.roles, function(index, role) {
				const text = $('#langId').val() == 1 ? role[2] : role[3];
				$('#roleId').append($("<option></option>").attr("value", role[0]).text(text));
			});
			$(".chosen-select-no-results").trigger("chosen:updated");
		} else {
			$('#createData').hide();
			var errorList = [];
			$('#service-div').hide();
			$('#role-div').hide()
			if (result != null && result != ""
					&& (result.services == null || result.services == "")
					&& result.roles != null && result.roles != "") {
				errorList.push(getLocalMessage('workflow.type.val.no.service')
						+ $('#dpDeptId option:selected').text());
			} else if (result != null && result != ""
					&& result.services != null && result.services != ""
					&& (result.roles == null || result.roles == "")) {
				errorList.push("No role Defined for deparment"
						+ $('#dpDeptId option:selected').text());
			} else {
				errorList.push("No service And  role Defined for deparment"
						+ $('#dpDeptId option:selected').text());
			}

			showRoleMstError(errorList);
		}
	}
}

function searchDecision() {
	$("#errorDivDeptMas").hide();
	$('#grid').jqGrid('clearGridData').trigger('reloadGrid');
	var errorList = [];
	var serviceId = $('#serviceId').val();
	var deptId = $('#dpDeptId').val();
	var roleId = $('#roleId').val();

	if (deptId == 0) {
		errorList.push(getLocalMessage('tax.error.dpDept'));
		showRoleMstError(errorList);
		return true;
	}

	if (serviceId == 0 || serviceId == null) {
		errorList.push(getLocalMessage('workflow.type.val.select.service'));
		showRoleMstError(errorList);
		return true;
	}

	if (roleId == 0) {
		errorList.push(getLocalMessage('Please Select Role'));
		showRoleMstError(errorList);
		return true;
	}
	var url = "RolesMaster.html?searchDecision";
	var requestData = {
		"dpDeptId" : deptId,
		"serviceId" : serviceId,
		"roleId" : roleId
	};

	var result = __doAjaxRequest(url, 'post', requestData, false, '');

	if (result > 0) {
		$('#createData').hide();
		$('#editData').show();
		$('#viewData').show();
	} else {
		$('#createData').show();
		$('#editData').hide();
		$('#viewData').hide();
	}

	$("#grid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');
}

function showRoleMstError(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$("#errorDivDeptMas").html(errMsg);
	$('#errorDivDeptMas').show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function RoleMstReset() {
	$('#rolemaster').submit();
}
