$(function () {
    $("#orgDesgGrid").jqGrid({
        url: "OrgDesignation.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: [getLocalMessage("master.desgName"),getLocalMessage("master.DesignationNameReg"), getLocalMessage("master.desgShortName"),getLocalMessage("master.dsg.active/inactive"),getLocalMessage("master.delSelected")],
        colModel: [
            { name: "desgName", width: 100, sortable: true, search:true},
            { name: "desgNameReg", width: 100, sortable: false, search:true},
            { name: "desgShortName", width: 100, sortable: false, search:true },
            { name: 'deptStatus', index: 'deptStatus', width: 40, align: 'center', sortable: false,search : false,edittype:'checkbox',formatter:checkboxFormatter},
            { name: 'mapId', width: 40,index: 'mapId', align: 'center', sortable: false,search : false,formatter:deleteDesignation}
        ],
        pager: "#pagered",
        rowNum: 30,
        rowList: [5, 10, 20, 30],
        sortname: "desgName",
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
        caption: getLocalMessage("master.orgDesignationMap")
    }); 
    jQuery("#orgDesgGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:true}); 
	$("#pagered_left").css("width", "");
});

function checkboxFormatter(cellvalue, options, rowObject) {
	/*if(rowObject.deptStatus == 'A'){*/
		return "<a title='Department is Active' alt='Department is Active' value='A' class='fa fa-check-circle fa-2x green' href='#'></a>";		
		/*} else{			
		return "<a title='Department is InActive' alt='Department is InActive' value='A' class='fa fa-times-circle fa-2x red' href='#'></a>";
	}*/
}

function deleteDesignation(cellValue, options, rowdata, action) {
    return "<a href='#'  return false; class='btn btn-danger btn-sm deleteClass' value='"+rowdata.mapId+"' ><i class='fa fa-trash' aria-hidden='true'></i></a>";
}

$(function() {		
	$(document).on('click', '.deleteClass', function() {
		var mapId = $(this).attr('value');
		var url =  "OrgDesignation.html?checkEmpExists";
		var requestData = {
			"mapId" : mapId
		}
		var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', '');
		if(returnData == false){
			showDelConfirmBox(mapId);
		}else{
			showDelConfirmBox(0);
		}
	/*	}*/
	});
		
});

function showDelConfirmBox(mapId){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	
	if(mapId == 0){
		message	+='<h4 class=\"text-info padding-10 padding-bottom-0 text-center\">'+getLocalMessage("master.orgDesMap.valmsg.empdes")+'</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'</div>';
	}else{		
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+getLocalMessage("master.orgDesMap.valmsg.areYouSure")+'</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="deleteData('+mapId+')"/>'+
		'</div>';		
	}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function deleteData(mapId){
	$('#errorDivOrgMas').hide();
	var url = "OrgDesignation.html?delete";
	var returnData = {"mapId" : mapId}
	$.ajax({
		url : url,
		datatype: "json",
        mtype: "POST",
		data : returnData,
		success : function(response) {
			
				$.fancybox.close();
				$("#orgDesgGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	
	$.fancybox.close();
	$("#orgDesgGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
}


function findDesignationData(obj) {
	var url = "OrgDesignation.html?findDesignationData";
	var orgId = $(obj).val();
	var returnData = {"orgId" : orgId};
	
	$.ajax({
		url : url,
		datatype: "json",
        mtype: "POST",
		data : returnData,
		success : function(response) {						
			$("#orgDesgGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
}

function closeOutErrBox() {
	$('.error-div').hide();
	$("#errorDivOrgDet").hide();
}

function addDesignation(){
	
	$('#errorDivOrgMas').hide();
	var url = "OrgDesignation.html?addDesignation";
	 var errorList = [];
	$.ajax({
		url : url,
		success : function(response) {
			var respErrorMsg = $(response).find('#errormsg').val();
			if(respErrorMsg == null || respErrorMsg == ""){
				var divName = '.child-popup-dialog';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				showMsgModalBox(divName);
			}else{
				errorList.push(getLocalMessage(respErrorMsg));
				showCmprmError(errorList);
			}
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showCmprmError(errorList);
		}
	});				
}

function showCmprmError(errorList){
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDivOrgMas").html(errMsg);
	$('#errorDivOrgMas').show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
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

function closeOrgErrBox() {
	$("#errorDivOrgDet").hide();
}

function saveDesignationData(obj){
	
		if($("#desgnId").val()== 0 || $("#desgnId").val()==''){
			var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()"><span>&times;</span></button><ul><li><i class="fa fa-exclamation-circle"></i>&nbsp\''+getLocalMessage("master.orgDesignationMap.selDes")+'\'</li></ul>';
			$("#errorDivOrgDet").html(errMsg);
			$("#errorDivOrgDet").show();
			$("html, body").animate({ scrollTop: 0 }, "slow");
			return false;
		}
	
	var requestData = __serializeForm('form');
	$.ajax({
		url : 'OrgDesignation.html?createChildData',
		data : requestData,
		type : 'POST',			
		success : function(response) {
			
			if(response != "error"){
				_closeChildForm('.child-popup-dialog');
				$.fancybox.close();
				showConfirmBox();
				$("#orgDesgGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			} else {
				var errorList = [];
				errorList.push(getLocalMessage("master.not.add.duplicate.designation"));
				$("#errorDivOrgDet").html('<button type="button" class="close"><span onclick="closeOrgErrBox()">&times;</span></button><ul>'
						+'<li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errorList+'</ul>');
				$("#errorDivOrgDet").show();
				return false;
			}
			},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	
}

function showConfirmBox(){

	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls ='OK';
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Record Saved Successfully</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="proceed()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	 return false;
}

function proceed() {		
	$.fancybox.close();
}


function setDesignationData(obj) {
	$("#dsgname").val($("#desgnId").find('option:selected').text());
	$("#dsgshortname").val($("#desgnId").find('option:selected').attr('value1'));
}

function showSubmitConfirmBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	
	message	+='<p>Form Submitted Successfully</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="proceed()"/>'+	
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function proceed() {
	window.location.href='OrgDesignation.html';
}
