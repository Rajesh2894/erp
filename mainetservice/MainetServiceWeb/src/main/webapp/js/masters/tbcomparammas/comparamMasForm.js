
	var cpdId = 0;	
	var comParentMasId = '';
	var level = '';
	var comId = 0;	
	$( document ).ready(function() {
		
		$('#isCheckedFlag').val("");
		$('#levelSelected').val(0);
		
		var mainFormMode = $("#mainFormMode").val();
		if(mainFormMode == 'update'){
			$('#cpmConfig').prop("checked", true);
		}
		
		if(mainFormMode == 'view'){
			if($('#isDefault').val() == "N" && $('#cpmReplicateFlag').prop('checked') == false){
				$('#addRightsDiv').hide();			
			}
			$('#addBtnDiv').hide();
			$('#saveBtn').hide();
			$('#commonMaster input').prop('disabled', true);
			$('#commonMaster select').prop('disabled', true);
			$('#commonMaster textarea').prop('disabled', true);
			$('#commonMaster checkbox radio').prop('disabled', true);
			$('#backBtn').prop('disabled', false);		
			$('#createData').prop('disabled', true);			
		}
		
		if($("#tbComparamMas_cpmType").val() == '' || $("#tbComparamMas_cpmType").val() == 'N') {			
			$("#comparentDetId").addClass('hide');
			$("#comparamDetId").removeClass('hide');
			$('#tbComparamMas_cpmEdit').prop("checked", false);
			
		} else {			
			$("#comparamDetId").addClass('hide');
			$("#comparentDetId").removeClass('hide');
			$('#tbComparamMas_cpmEdit').prop("checked", true);
		}		
	});

	//ComparamDet data
	function loadChildGridData() {
	    $("#childGrid").jqGrid({
	        url: "ComparamMaster.html?getChildGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: ['',getLocalMessage("comparamDet.description"), getLocalMessage("comparamDet.value"), getLocalMessage("comparamDet.other"),getLocalMessage("comparamDet.default"), getLocalMessage("comparamDet.status"),getLocalMessage("comparamDet.edit")],
	        colModel: [
	        	{ name: "cpdId", sortable: false ,search : false, hidden: true},
	            { name: "cpdDesc", sortable: false ,search : true},
	            { name: "cpdValue", sortable: false ,search : true},
	            { name: "cpdOthers", sortable: false ,search : true},
	            { name: "cpdDefault", sortable: false ,search : true},
	            { name: 'cpdStatus', index: 'enbl', align: 'center', sortable: false, edittype:'checkbox',formatter:checkboxFormatter, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },},
          		{ name: 'enbl', index: 'enbl', align: 'center', sortable: false, formatter:returnHyperLink}	],
	        pager: "#pagered",
	        rowNum: 20,
	        rowList: [5, 10, 20, 30],
	        sortname: "cpdDesc",
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
	        caption: getLocalMessage("comparamDet.gridheader")
	    }); 
	    jQuery("#childGrid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
		$("#pagered").css("width", "");
	}
	
	function loadComparentMasChildGridData() {
	    $("#comparentMasChildGrid").jqGrid({
	        url: "ComparamMaster.html?getComparentMasChildGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("comparentMas.select"),getLocalMessage("comparentMas.description"), getLocalMessage("comparentMas.value"), getLocalMessage("comparentMas.lavel"),getLocalMessage("comparentMas.status"), getLocalMessage("comparentMas.edit"),'SrNO'],
	        colModel: [
	            { name: 'enbl', index: 'enbl', align: 'center !important', sortable: false, edittype:'checkbox',formatter:radioButtonMasFormatter, editoptions: { value: "Yes:No" },
	            		formatoptions: { disabled: false },},
	            { name: "comDesc", sortable: false ,search : true},
	            { name: "comValue", sortable: false ,search : true},
	            { name: "comLevel", sortable: false ,search : true},
	            { name: 'comStatus', index: 'enbl', align: 'center', sortable: false, edittype:'checkbox',formatter:childCheckboxFormatter, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },},
          		{ name: 'enbl', index: 'enbl', align: 'center', sortable: false,formatter:returnComparentMasHyperLink},
          		{ name: "comId", sortable: false ,search : false, hidden: true}],
	        pager: "#cmPagered",
	        rowNum: 20,
	        rowList: [5, 10, 20, 30],
	        sortname: "comDesc",
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
	        caption: getLocalMessage("comparentMas.gridheader")
	    }); 
	    jQuery("#comparentMasChildGrid").jqGrid('navGrid', '#cmPagered', {edit : false,add : false,del : false,refresh : false});
		$("#cmPagered").css("width", "");
	}
	
	function loadComparentDetChildGridData() {
	    $("#comparentDetChildGrid").jqGrid({
	        url: "ComparamMaster.html?getComparentDetChildGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("comparentdet.description"), getLocalMessage("comparentdet.value"),getLocalMessage("comparentdet.other"),getLocalMessage("comparentdet.default"), getLocalMessage("comparentdet.status"),getLocalMessage("comparentdet.edit")],
	        colModel: [
	            { name: "codDesc", sortable: false ,search : true},
	            { name: "codValue", sortable: false ,search : true},
	            { name: "codOthers", sortable: false ,search : true},
	            { name: "cpdDefault", sortable: false ,search : true},
	            { name: 'codStatus', index: 'enbl', align: 'center', sortable: false,search : false, edittype:'checkbox',formatter:childCheckbox, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },},
          		{ name: 'enbl', index: 'enbl', align: 'center', sortable: false,search : false,formatter:returnComparentDetHyperLink}],
	        pager: "#cdPagered",
	        rowNum: 20,
	        rowList: [5, 10, 20, 30],
	        sortname: "codDesc",
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
	        caption: getLocalMessage("comparentdet.gridheader"),
	        loadComplete : function () {
	        	editStatus();
	        }
	    }); 
	    jQuery("#comparentDetChildGrid").jqGrid('navGrid', '#cdPagered', {edit : false,add : false,del : false,refresh : false});
		$("#cdPagered").css("width", "");
	}

	function childCheckbox(cellvalue, options, rowObject) {	
		if(rowObject.codStatus == 'Y'){
			if($("#mainFormMode").val() == 'view'){
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled='true' onchange='comparentDetCheckboxClick(this);' checked class='statusClass'>";
			}else{
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='comparentDetCheckboxClick(this);' checked class='statusClass'>";
			}		
		} else {
			if($("#mainFormMode").val() == 'view'){
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled='true' onchange='comparentDetCheckboxClick(this);' class='statusClass'>";
			}else{
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='comparentDetCheckboxClick(this);' class='statusClass'>";
			}
			
		}
	}

	
	function childCheckboxFormatter(cellvalue, options, rowObject) {
		if(rowObject.comStatus == 'Y'){
			if($("#mainFormMode").val() == 'view'){
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled='true' onchange='comparentMasCheckboxClick(this);' checked class='statusClass'>";
			}else{
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='comparentMasCheckboxClick(this);' disabled='true' checked class='statusClass'>";
			}		
		} else {
			if($("#mainFormMode").val() == 'view'){
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled='true' onchange='comparentMasCheckboxClick(this);' class='statusClass'>";
			}else{
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='comparentMasCheckboxClick(this);' class='statusClass'>";
			}
			
		}
	}
	function returnComparentDetHyperLink(cellValue, options, rowdata, action) {
		
		if($("#mainFormMode").val() == 'view'){
			return "<a href='#'  return false; class=' btn btn-warning btn-sm' onclick=editHierPreParentDetList('"+options.rowId+"') title='Edit Comparent Details' disabled='true'><i class='fa fa-pencil' aria-hidden='true'></i></a>";
		}else{
			return "<a href='#'  return false; class=' btn btn-warning btn-sm' onclick=editHierPreParentDetList('"+options.rowId+"') title='Edit Comparent Details'><i class='fa fa-pencil' aria-hidden='true'></i></a>";
		}
		 
	}
	
	function returnComparentMasHyperLink(cellValue, options, rowdata, action) {
		
		
		if($("#mainFormMode").val() == 'view'){
			return "<a href='#'  return false; class=' btn btn-warning btn-sm' onclick=editHierPreParentList('"+options.rowId+"') title='Edit Comparent Master' disabled='true'><i class='fa fa-pencil' aria-hidden='true'></i></a>";
		}else{
			return "<a href='#'  return false; class=' btn btn-warning btn-sm' onclick=editHierPreParentList('"+options.rowId+"') title='Edit Comparent Master'><i class='fa fa-pencil' aria-hidden='true'></i></a>";
		}	     
	}
	
	function returnHyperLink(cellValue, options, rowdata, action) {
		var cpdId = 0;
		if (rowdata.cpdId == null || rowdata.cpdId == 'null' || rowdata.cpdId == undefined) {
			cpdId = -1;
		} else {
			cpdId = rowdata.cpdId;
		}
		if($("#mainFormMode").val() == 'view'){
			return "<a href='#'  return false; class=' btn btn-warning btn-sm'   onclick=editPrefixData('"+cpdId+"','"+options.rowId+"') title='Edit Comparam Master' disabled='true'><i class='fa fa-pencil' aria-hidden='true'></i></a>";
		}else{
			return "<a href='#'  return false; class=' btn btn-warning btn-sm'  onclick=editPrefixData('"+cpdId+"','"+options.rowId+"') title='Edit Comparam Master'><i class='fa fa-pencil' aria-hidden='true'></i></a>";
		}
	}  
	
	function radioButtonMasFormatter(cellvalue, options, rowObject) {
		return "<input type='radio' name='selectDataId' id='selectDataId' onclick=getComparentDetData('"+rowObject.comId+"','"+rowObject.comLevel+"')>";
	}
	
	function comparentDetCheckboxClick(obj) {
		
		var codValue = $(obj).closest('tr').find('td:eq(1)').text();
		
		var isChecked = 'N';
		
		if($(obj).is(":checked")){
			isChecked = 'Y';
		}
		
		var url = "ComparamMaster.html?updateComparentDetStatus";
		var requestData = "codValue="+codValue+"&isChecked="+isChecked;
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "GET",
	        data:requestData,
			success : function(response) {				
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
	}
	
	function comparentMasCheckboxClick(obj) {
		var comValue = $(obj).closest('tr').find('td:eq(2)').text();
		var comLevel=$(obj).closest('tr').find('td:eq(3)').text();
		var isChecked = 'N';
		
		if($(obj).is(":checked")){
			isChecked = 'Y';
		}
		var url = "ComparamMaster.html?updateComparentMasStatus";
		var requestData = "comValue="+comValue+"&isChecked="+isChecked+"&comLevel="+comLevel;
		var returnData=__doAjaxRequest(url, 'post',requestData, false,'','');
		$("#comparentMasChildGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
		$("#comparentDetChildGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
		//$('#comparentDetChildGrid').jqGrid('clearGridData');
	}
	
	
	function getComparentDetData(obj, level1) {
		$('#addChildbtn').removeClass('hide');
		comParentMasId = obj;
		level = level1;

		var tempMode = "UPDATE";
		var mainFormMode = $("#mainFormMode").val();
		
		if(comParentMasId == 'null') {
			comParentMasId = -1;
		}

		var url = "ComparamMaster.html?getComparentDetData"
		var requestData = "comParentMasId="+comParentMasId+"&level="+level+"&tempMode="+tempMode+"&mainFormMode="+mainFormMode+"&flag=R";
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {

			   $("#parentDataDivId").html(response);		   
			   $("#comparentDetChildGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
		
	}
	
	function editPrefixData(cpdId,rowNo){
		
		var url = "ComparamMaster.html?editChildForm";
		var requestData = "cpdId="+cpdId+"&rowNo="+(rowNo-1);
		
		$.ajax({
			url : url,
			data : requestData,
			type : "GET",
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
		
	    return false;
	}
	
	
function editHierPreParentList(rowNo){
			
			
			var url = "ComparamMaster.html?editComparentMasForm";
			var requestData = "rowNo="+(rowNo-1);
			$.ajax({
				url : url,
				data : requestData,
				success : function(response) {
					
					var divName = '.child-popup-dialog';					
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					//prepareTags();
					showMsgModalBox(divName);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});	
		    
		    return false;
 }
		

function editHierPreParentDetList(rowNo){
			var url = "ComparamMaster.html?editComparentDetForm";
			var requestData = "rowNo="+(rowNo-1);
			$.ajax({
				url : url,
				data : requestData,
				success : function(response) {
					
					var divName = '.child-popup-dialog';
					
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					//prepareTags();
					showMsgModalBox(divName);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});		    
		    return false;
 }

	
	$(document).on('click', '#createData', function () {
	
			var prefixType = $("#tbComparamMas_cpmType").val();
			var errorList = [];
			
			if( prefixType == '' ) {
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage("prefix.error.plsEnterPreType")+'</li></ul>';

				$("#errorDivComparamMas").html(errMsg);
				$("#errorDivComparamMas").show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
			}			
			
			var countLevels = 0;
			var rows = jQuery("#comparentMasChildGrid").getDataIDs();
			 for(a=0;a<rows.length;a++)
			 {
			    row=jQuery("#comparentMasChildGrid").getRowData(rows[a]);
			    countLevels = countLevels +1;
			 }
			 
			if(countLevels < 5){
				var url = "ComparamMaster.html?addChildForm";
				var requestData = "prefixType="+prefixType;
				
				$.ajax({
					url : url,
					data : requestData,
					success : function(response) {
						
						var divName = '.child-popup-dialog';
						
						$(divName).removeClass('ajaxloader');
						$(divName).html(response);
						//prepareTags();
						showMsgModalBox(divName);
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showErr(errorList);
					}
				});	
			}else{
				errorList.push(getLocalMessage("prefixMas.valmsg.onlyFiveLevels"));
				showErr(errorList);
			}
						
		});
	
	function closeOutErrBox(){
		$('.error-div').hide();
	}
	
	
	function checkboxFormatter(cellvalue, options, rowObject) {
		if(rowObject.cpdStatus == 'A'){
			if($("#mainFormMode").val() == 'update'){
				if( $("#cpmEditStatus").is(':checked')){
					return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='checkBoxToggle(this);' checked class='statusClass'>";					
				} else {
					return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled checked class='statusClass'>";
				}
			} else if($("#cpmEditStatus").is(':checked')){
					if($("#mainFormMode").val() == 'view'){
						return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled='true' checked class='statusClass'>";
					}else{
						return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='checkBoxToggle(this);' checked class='statusClass'>";
					}			
			} 
			else {
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled='true' checked class='statusClass'>";
			}			
		} else {
			if($("#mainFormMode").val() == 'view'){
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' disabled='true' class='statusClass'>";
			}else{
				return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='checkBoxToggle(this);' class='statusClass'>";
			}
		}
	}
	
	
	function checkBoxToggle(obj){
		if($(obj).attr( "checked" )){
			$(obj).attr('checked', false);
		}else{
			$(obj).attr('checked', true);
		}
		checkboxClick(obj);
	}
	
	function checkboxClick(obj){
		var cpdId = $(obj).closest('tr').find('td:eq(0)').text();		
		var isChecked = 'N';
		if($(obj).attr( "checked" )){
			isChecked = 'Y';
		}
		
		var url = "ComparamMaster.html?updateStatus";
		var requestData = {
				"cpdId" : cpdId,
				"isChecked": isChecked
		}
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	}
	
	function toggleStatus(obj) {					
		if( $(obj).is(':checked') ) {
			$('.statusClass').each(function(index){					
				$(this).prop("disabled", false);					
			});
		} else {				
			$('.statusClass').each(function(index){					
				$(this).prop("disabled", true);					
			});
		}
	}
	
	function toggleAddDetails(obj) {		
		if( $(obj).is(':checked') ) {
			$('#createData').prop("disabled", false);			
		} else {				
			$('#createData').prop("disabled", true);
		}		
	}
	
	function checkForSpace(checkVal){
		var hasSpace = /\s/g.test(checkVal);
		return hasSpace;
	}
	
	function validateData(obj) {
		var errorList = [];
		
		var prefix = $("#tbComparamMas_cpmPrefix").val();
		
		if($("#tbComparamDet_cpmModuleName").val() == ''){
			errorList.push(getLocalMessage("prefixMas.valmsg.deptName"));
		}
		if($("#tbComparamMas_cpmType").val() == ''){
			errorList.push(getLocalMessage("prefixMas.valmsg.preType"));
		}
		if($.trim( prefix ) == ''){
			errorList.push(getLocalMessage("prefixMas.valmsg.enterPrefix"));			
		}else if(checkForSpace(prefix)){
			errorList.push(getLocalMessage("prefixMas.valmsg.spaceNotAllowed"));
		}
		if($.trim( $('#tbComparamMas_cpmDesc').val() ) == ''){
			errorList.push(getLocalMessage("prefixMas.valmsg.enterDes"));
		}

		//IsDefault Validation
		var isDefaultAvblCount = 0;
		
		var rows = jQuery("#childGrid").getDataIDs();
		 for(a=0;a<rows.length;a++)
		 {
		    row=jQuery("#childGrid").getRowData(rows[a]);
		    if(row.cpdDefault == "Y"){
		    	isDefaultAvblCount = isDefaultAvblCount +1;
		    }
		 }
		
		if(isDefaultAvblCount > 1){
			errorList.push(getLocalMessage("prefixMas.valmsg.onlyOneValueInNonHier"));
		}
		
		
		if(errorList.length > 0){				
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';			 
			$(".error-div").html(errMsg);					
			$(".error-div").show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');			
			errorList = [];
			return false;
		} else {
			if($("#mainFormMode").val() != 'update') {
				var prefixUrl = 'ComparamMaster.html?validatePrefix';
				var data = {
						"prefixData" : $("#tbComparamMas_cpmPrefix").val()
				}
				$.ajax({
					url : prefixUrl,
					datatype: "json",
			        mtype: "POST",
					data : data,
					success : function(response) {						
						if(response != 0 || response != '0') {
							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul><li><i class="fa fa-exclamation-circle"></i>&nbsp;Prefix already Exist</li></ul>';
							$(".error-div").html(errMsg);
							$(".error-div").show();
							$("html, body").animate({ scrollTop: 0 }, "slow");
							return false;
						} else {
								
								var	formName =	findClosestElementId(obj, 'form');
								var theForm	=	'#'+formName;
								var requestData = __serializeForm(theForm);
								var url	=	$(theForm).attr('action');							
								var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
								
								if($.isPlainObject(returnData)) {
									showConfirmBox();
								} else {
									$("#content").html(returnData);
									$("#content").show();
									$('.error-div').hide();
									return false;
								}

						}
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
			} else {
				var	formName =	findClosestElementId(obj, 'form');
				var theForm	=	'#'+formName;
				var requestData = __serializeForm(theForm);
				var url	=	$(theForm).attr('action');
				var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
				
				if($.isPlainObject(returnData)) {
					showConfirmBox();
				} else {
					$("#content").html(returnData);
					$("#content").show();
					$('.error-div').hide();
					return false;
				}
			}
			return false;
		}
	}
	
	function showConfirmBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';

		message	+='<h4 class=\"text-center text-blue-2 padding-5\">Record Saved Successfully</h4>';
		 message	+='<div class\="text-center padding-bottom-5\">'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\'    '+ 
		' onclick="proceed()"/>'+	
		'</div>';
		
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}
	
	function proceed() {
		window.location.href='ComparamMaster.html';
	}
	
	function toggleDetailForm(obj) {		
		if($(obj).val() == 'N') {
			
			$("#comparentDetId").addClass('hide');
			$("#comparamDetId").removeClass('hide');
			$('#tbComparamMas_cpmEdit').prop("checked", false);
			
		} else {			
			$("#comparamDetId").addClass('hide');
			$("#comparentDetId").removeClass('hide');
			$('#addChildbtn').prop("disabled", true);
			$('#tbComparamMas_cpmEdit').prop("checked", true);
		}
	}
	
	function addChildData(obj) {
		var tempMode = "CREATE";			
		$("#levelId").val(level);
		
		var mainFormMode = $("#mainFormMode").val();
		if(comParentMasId == 'null' || comParentMasId == '') {
			comParentMasId = -1;
		}

		var url = "ComparamMaster.html?getComparentDetData";
		var requestData = "comParentMasId="+comParentMasId+"&level="+level+"&tempMode="+tempMode+"&mainFormMode="+mainFormMode+"&flag=B";
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {
				var divName = '.child-popup-dialog';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				//prepareTags();
				showMsgModalBox(divName);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});		
	}
	
	function toggleEditDesc(obj) {
		if( $(obj).is(':checked') ) {
			$("#isEditDesc").val("Y");
		} else {
			$("#isEditDesc").val("N");
		}		
	}
	function toggleEditDefault(obj) {
		if( $(obj).is(':checked') ) {
			$("#isEditDefault").val("Y");
		} else {
			$("#isEditDefault").val("N");
		}
	}
	function toggleEditValue(obj) {
		if( $(obj).is(':checked') ) {
			$("#isEditValue").val("Y");
		} else {
			$("#isEditValue").val("N");
		}
	}
	
	
	function check_char(e, obj) {
		var keycode;

		if (window.event)
			keycode = window.event.keyCode;
		else if (e) {
			keycode = e.which;
		} else {
			return true;
		}

		var fieldval = (obj.value);
		if (keycode == 8 || keycode == 9 || keycode == 13) {
			// back space, tab, delete, enter 
			return true;
		}
		if ((keycode > 32 && keycode <= 64)
				|| (keycode >= 91 && keycode <= 96)
				|| (keycode >= 123 && keycode <= 126)) {
			return false;
		} else {
			return true;
		}
	}
	
	
	function showErr(errorList){
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';		
		$(".warning-div").html(errMsg);
		$(".warning-div").removeClass('hide');
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
	
	
	function closePrefixErrBox(){
		$(".warning-div").addClass('hide');
	}

	function getData(){
		$(".error-div").hide();
		var cpmPrefix = $('#tbComparamMas_cpmPrefix').val();
		var cpmPrfxLen = cpmPrefix.length;
		var url = "ComparamMaster.html?getPrefixDataByPrefix";
		var errorList = [];
		
		var requestData = {
				"cpmPrefix"	: cpmPrefix
		}
		if(cpmPrfxLen >= 3){
			var returnData =__doAjaxRequestForSave(url, 'post',requestData, false,'','');
			if(returnData != null && returnData != ""){
				errorList.push(cpmPrefix.toUpperCase()+" "+getLocalMessage("prefixMas.valmsg.preAlrExist"));
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[0] + '</li></ul>';		 
				$(".error-div").html(errMsg);					
				$(".error-div").show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');			
				errorList = [];
				return false;
			}
		}	
	}
	
	function resetPrefixMasterForm(){
		
		var mode = $('#mainFormMode').val();
		var cpmId = $('#cpmId').val();
		
		if(mode == 'create'){
			var reqData = {
					"deptCode" :	$('#tbComparamDet_cpmModuleName').val()
				}
				var url = "ComparamMaster.html?form";
				$.ajax({
					url : url,
					data : reqData,
					success : function(response) {						
						$("#comparamMasDiv").html(response);					
						$("#comparamMasDiv").show();
					}
				});
		}else{
			if( cpmId != '') {				
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
					}
				});
			}
		}
	}
	
	
	function editStatus() {					
		if( $('#cpmEditStatus').is(':checked') ) {
			$('.statusClass').each(function(index){					
				$(this).prop("disabled", false);					
			});
		} else {				
			$('.statusClass').each(function(index){					
				$(this).prop("disabled", true);					
			});
		}
	}
	