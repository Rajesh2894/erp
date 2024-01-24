
	var selectedIds = new Array();

	var cpmId = '';
	
	/*$(document).ready(function(){
	    $('#isAll').prop('checked',true);
	});
	*/
	$(function () {
	    $("#grid").jqGrid({
	        url: "ComparamMaster.html?getGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage('comparamMas.prefix'),
	                   getLocalMessage('comparamMas.description'),getLocalMessage('comparamMas.prefixtype'),
	                   getLocalMessage('comparamMas.department'),
	                   getLocalMessage('comparamMas.editable'),getLocalMessage('comparamMas.loadAtStartup'),
	                   getLocalMessage('comparamMas.status'),getLocalMessage('comparamMas.action')],
	        colModel: [
	            { name: "cpmPrefix", width : 15, sortable: true,search : true },
	            { name: "cpmDesc", width : 70, sortable: true,search : true },
	            { name: "cpmTypeValue", width : 25, sortable: true,search : true},
	            { name: "cpmModuleNameStr", width : 30, sortable: true,search : true},
	            { name: "cpmReplicateFlag", width : 25, align: 'center', sortable: true,search : true },
	            { name: "loadAtStartup", width : 25, align: 'center' ,sortable: true,search : true },
	            { name: 'cpmStatus', index: 'cpmStatus', align: 'center', width : 25, sortable: false,formatter:checkIsActive, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },search : false
      			},	            
	            { name: 'cpmId', index: 'cpmId', align: 'center !important', sortable: false, width : 35,formatter:actionFormatter,search : false}
	        ],
	        pager: "#pagered",
	        rowNum: 50,
	        rowList: [10, 20, 50, 100],
	        sortname: "cpmPrefix",
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
	        editurl:"ComparamMaster.html?update",
	        caption: getLocalMessage("comparamMas.gridheader")
	    });
	    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
		$("#pagered_left").css("width", "");
	}); 
	
	function actionFormatter(cellvalue, options, rowObject){
		if($('#isDefault').val() == 'N'){
			if(rowObject.cpmReplicateFlag == "Y"){
				return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"editViewPrefix('"+rowObject.cpmId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
				"<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editViewPrefix('"+rowObject.cpmId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
			}else{
				return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"editViewPrefix('"+rowObject.cpmId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> ";
			}
			
		}else{
			if(rowObject.cpmReplicateFlag == "Y"){
				
				if(rowObject.cpmStatus == 'A'){
					return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"editViewPrefix('"+rowObject.cpmId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
					"<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editViewPrefix('"+rowObject.cpmId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> "+
						 "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"deletePrefix('"+rowObject.cpmId+"')\"><i class='fa fa-trash' aria-hidden='true'></i></a> ";
				}else{
					return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"editViewPrefix('"+rowObject.cpmId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
					"<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editViewPrefix('"+rowObject.cpmId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
				}
				
			}else{
				return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"editViewPrefix('"+rowObject.cpmId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
				"<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editViewPrefix('"+rowObject.cpmId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
			}
		}
		
	}

	
	function editViewPrefix(cpmId,mode){

		var gr = jQuery("#grid").jqGrid('getGridParam','selrow');

		if( cpmId != '' ) {				
			var url = "ComparamMaster.html?formForUpdate";
			var returnData ={
					"cpmId" : cpmId,
					"mode" : mode
					};
			$.ajax({
				url : url,
				datatype: "json",
		        mtype: "POST",
				data : returnData,
				success : function(response) {					
					var divName = '.form-div';						
					$("#comparamMasDiv").html(response);
					$("#comparamMasDiv").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
		}
		else {				
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage("master.row.select.msg")+'</li></ul>';
			$(".warning-div").html(errMsg);
			$(".warning-div").removeClass('hide');
		}
	}

	function deletePrefix(cpmId){
		if( cpmId != '' ) {				
			showConfirmBox(cpmId);
		}
	}

	
	function showConfirmBox(cpmId){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Yes';
		
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">Are you sure want to delete?</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="deleteData('+cpmId+')"/>'+
		'</div>';
	
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function deleteData(cpmId){

		var url = "ComparamMaster.html?delete";
		var requestData = "cpmId="+cpmId;
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {
				$.fancybox.close();
				if(response != 1) {					
					errorList.push("Unable to delete data");
					showErr(errorList);
				}else{
					searchPrefixData();
				}
				/*$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');*/
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	}
	
	function createPrefix(obj){
		$('.error-div').hide();
		
		var reqData = {
			"deptCode" :	$('#tbComparamDet_cpmModuleName').val()
		}
		var url = "ComparamMaster.html?form";
		var returnData = "";
		$.ajax({
			url : url,
			data : reqData,
			success : function(response) {						
				$("#comparamMasDiv").html(response);					
				$("#comparamMasDiv").show();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
	}

	
	function checkboxFormatter(cellvalue, options, rowObject) {	
		return "<input type='radio' name='checkboxIsCC' id='checkboxIsCC' onchange='checkboxClick(this);'>";
	}

/*	function checkboxClick(obj){
		cpmId = $(obj).closest('tr').find('td:eq(0)').text();
	}*/
	
	function checkIsActive(cellValue, options, rowdata, action) {
		if (rowdata.cpmStatus == 'A') {
			return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowdata.cpmStatus+"'  alt='Prefix is Active' title='Prefix is Active'></a>";
		} else {
			return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowdata.cpmStatus+"' alt='Prefix is  InActive' title='Prefix is InActive'></a>";
		}

	}
	
	/* function showChildForm(divName, ajaxResponse) {

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		//prepareTags();
		showMsgModalBox(divName);
	} */

	function showMsgModalBox(childDialog) {

		$.fancybox({
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
	
	function closePrefixErrBox(){
		$('.warning-div').addClass('hide');
	}
	
	function searchPrefixData() {
		
		$('.warning-div').addClass('hide');
		var department 	= $("#tbComparamDet_cpmModuleName").val();
		var cpmPrefix	= $.trim($("#tbComparamMas_cpmPrefix").val());
		var errorList = [];
		
		/*if($('#isReplicate').is(':checked') == false && $('#isNotReplicate').is(':checked') == false){
			errorList.push("Please select Replication");
		}*/
		
		/*if(department == '' && cpmPrefix==''){
			errorList.push("Please enter any one search criteria");
		}*/
		
		if(errorList.length > 0){
			showErr(errorList);
		}else{
			var url = "ComparamMaster.html?searchPrefixData";
			var requestData = {
					"department" 	: department,
					"cpmPrefix"		: cpmPrefix
			}
		
			var returnData =__doAjaxRequestForSave(url, 'post',requestData, false,'','');
			
			for(var i in returnData){
				if(returnData[i] == 0){
					errorList.push(getLocalMessage("prefix.error.norecord"));			
				}else if(returnData[i] == 2){
					errorList.push(getLocalMessage("prefix.error.dept.actual"));
				}else if(returnData[i] == 3){
					errorList.push(getLocalMessage("prefix.error.dept.active"));
				}else if(returnData[i] == 4){
					errorList.push(getLocalMessage("prefix.error.dept.notMapped"));
				}
			}
						
			if(errorList.length > 0){
				showErr(errorList);
			}		
		}
		$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	}
	
	function resetPrefixForm() {
		$("#tbComparamDet_cpmModuleName").val('').trigger('chosen:updated');
		$("#grid").jqGrid("clearGridData");
		$("#tbComparamMas_cpmPrefix").val('');
		$('#isReplicate').prop('checked',true);
		$('.warning-div').addClass('hide');
	}
	
	
	function showErr(errorList){
		$(".warning-div").removeClass('hide');
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';		
		$(".warning-div").html(errMsg);
		
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
	
	function getData(){
		
		$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		$("#tbComparamDet_cpmModuleName").val('').trigger('chosen:updated');
		//$("#grid").jqGrid("clearGridData");
		$('.warning-div').addClass('hide');
		var cpmPrefix = $('#tbComparamMas_cpmPrefix').val();
		var cpmPrfxLen = cpmPrefix.length;
		var url = "ComparamMaster.html?getPrefixDataByPrefix";
		
		var requestData = {
				"cpmPrefix"	: cpmPrefix
		}
		if(cpmPrfxLen >= 3){
			var returnData =__doAjaxRequestForSave(url, 'post',requestData, false,'','');
		/*	if(returnData.cpmReplicateFlag != null || returnData.cpmReplicateFlag != undefined || returnData.cpmReplicateFlag != ""){
				$("input:radio[name='replicateFlag'][value ="+returnData.cpmReplicateFlag+"]").prop('checked', true);
			}*/
		}	
	}
	
	function hideErrorDiv(){
		$('.warning-div').addClass('hide');
	}

	
function printPrefixData() {

	

	var formDivName = '.content-page';
	var departmentShortCode = $("#tbComparamDet_cpmModuleName").val();

	deptName = $("#tbComparamDet_cpmModuleName option:selected").text();

	var requestData = "departmentShortCode=" + departmentShortCode;
	var url = "ComparamMaster.html?printPrefixDataByDept";
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false);

	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}
	
	
	
	