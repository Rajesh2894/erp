
$(function () {
    $("#deptLocationGrid").jqGrid({
        url: "TbDeptLocation.html?getChildGridData",
        datatype: "json",
        mtype: "GET",
        colNames: [getLocalMessage("dept.master.deptLoc.deptId"), getLocalMessage("dept.master.deptLoc.deptName"),
                   getLocalMessage("dept.master.common.status"),getLocalMessage("dept.master.common.delete")
              ],
        colModel: [
            { name: "dpDeptid", width: 35, sortable: false,search:false},
            { name: "autLo1", width: 100, sortable: true,search : true },
            { name: 'isdeleted', index: 'isdeleted', width: 30, align: 'center', 
            	edittype:'checkbox',formatter:statusFormatter, editoptions: { value: "Yes:No" },
        		formatoptions: { disabled: false },search:false
            },
  			{ name: 'locId', index: 'locId', width: 50, align: 'center !important', 
  				sortable: false,search:false,formatter:deleteLocation }
  			
       ],
        pager: "#pagered",
        rowNum: 5,
        rowList: [5, 10, 20, 30],
        sortname: "autLo1",
        sortorder: "asc",
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
        editurl:"TbDeptLocation.html?update",
        caption: getLocalMessage("dept.master.deptLoc.gridCap")
    });
    jQuery("#deptLocationGrid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : true});
	$("#pagered_left").css("width", "");
});


function statusFormatter(cellvalue, options, rowObject) {
	if(rowObject.isdeleted == 0){
		return "<a title='Department is mapped' alt='Department is mapped' value='A' class='fa fa-check-circle fa-2x green' href='#'></a>";		
	} else{			
		return "<a title='Department is not mapped' alt='Department is not mapped' value='A' class='fa fa-times-circle fa-2x red' href='#'></a>";
	}
}


function deleteLocation(cellvalue, options, rowObject) {
	return "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"deleteDeptLoc('"+rowObject.dpDeptid+"')\"><i class='fa fa-trash' aria-hidden='true'></i></a>"
}


function closeAlertForm()
{
	var childDivName	=	'.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}

function getDepartmentData(obj) {
	$("#errorDivScrutiny").hide();
	var url = "TbDeptLocation.html?getDepartmentData";
	var locId = $(obj).val();
	var reqData = "locId="+locId;
	$.ajax({
		url : url,
		data : reqData,
		success : function(response) {
			$("#deptLocationGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
}

 
function deleteDeptLoc(dpDeptid){
	$("#errorDivServiceCheckList").hide();
	getMapedEmpCount(dpDeptid);	
}

function getMapedEmpCount(dpDeptid){
	
	var url = "TbDeptLocation.html?getMapedEmpCount";
	var returnData = {"dpDeptid" : dpDeptid};
	$.ajax({
		url : url,
		datatype: "json",
        mtype: "POST",
		data : returnData,
		success : function(response) {
	
			if(response=="N")
				{
				showAlertBox();
				}
			 else if(response=="Y"){
				showConfirmBox(dpDeptid);		
			}
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
}

function showConfirmBox(dpDeptid){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+getLocalMessage("master.deptLocMap.valmsg.areYouSure")+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="deleteData('+dpDeptid+')"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}

function showAlertBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+getLocalMessage("master.deptLocMap.valmsg.empAldyMap")+'<br>'+getLocalMessage("master.deptLocMap.valmsg.delNotAllowed")+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}

function deleteData(dpDeptid){
	
	var url = "TbDeptLocation.html?delete";
	var returnData = {"dpDeptid" : dpDeptid};
	$.ajax({
		url : url,
		datatype: "json",
        mtype: "POST",
		data : returnData,
		success : function(response) {
				$.fancybox.close();
				$("#deptLocationGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
}

function showMsgModalBox(childDialog) {

	$.fancybox({
		'width':300,
        'height':150,
        'autoSize' : false,
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic', // 'elastic', 'fade' or 'none'
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});

	return false;
}


function saveGrid(obj) {
	return saveOrUpdateForm(obj, 'Saved Successfully', 'TbDeptLocation.html', 'create');
}

function closeCheckbox(){
	window.location.href='TbDeptLocation.html';
}


function addDepLoc(){
		
		var locId = $("#locId").val();
		var errorList = [];
		
		if	(locId == ''|| locId=='0'){
			errorList.push(getLocalMessage("master.deptLocMap.valmsg.selLoc"));
			showLocError(errorList);
			return false;
		}
	
		
		var url = "TbDeptLocation.html?getAvailableDeptCount";
		var requestData = {"locId" : locId};
		
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {

				if(response=="Y")
				{
					showAlertNoDeptAvailable();
					}
				else if(response=="N"){
					getAllDeptList(locId);
				}
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
	}

function getAllDeptList(locId) {
		var url = "TbDeptLocation.html?addChildForm";
		var requestData = {"locId" : locId};
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {

				var divName = '.child-popup-dialog';
				
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				showMsgModalBox(divName);
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	}
	
	function showAlertNoDeptAvailable(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Ok';
		
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">Department is not available for Mapping</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="closeAlertForm()"/>'+
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}


function showLocError(errorList){
	
	$("#errorDivScrutiny").show();
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox1()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDivScrutiny").html(errMsg);
	$("html, body").animate({ scrollTop: 0 }, "slow");
}

function closeErrBox1(){
	$("#errorDivScrutiny").hide();
}
function depLocReset(){
	$('#locId').val(0).trigger('chosen:updated');
	$("#errorDivScrutiny").hide();
	$(".chosen-select-no-results").trigger("chosen:updated");
	$('#deptLocationGrid').jqGrid('clearGridData').trigger('reloadGrid');
}
