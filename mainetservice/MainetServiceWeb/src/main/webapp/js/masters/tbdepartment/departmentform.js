
	var organisationId = '';

	$(document).ready(function(){
		if($('#formModeId').val() == 'create'){
			$('#isActive1').prop('checked',true);
		}
		
		if($('#formModeId').val() == 'edit' || $('#formModeId').val() == 'view' ){
			 if( $("#activeChkBox").val() =='true'){
				 $("#dpCheckId").prop("checked", true);
			 }else{
				 $("#dpCheckId").prop("checked", false);
			 }
		}
		
	});
	
	$(function () {
	    $("#childGrid").jqGrid({
	        url: "Department.html?getChildGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("master.OrgId"), getLocalMessage("master.OrgName"), getLocalMessage("master.serviceActive")],
	        colModel: [
	            { name: "orgid", width: 35, sortable: true },
	            { name: "orgName", width: 100, sortable: false },
	            { name: 'enbl', index: 'enbl', width: 30, align: 'center', edittype:'checkbox',formatter:checkboxFormatter, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },
      			}      ],
	        pager: "#pagered",
	        rowNum: 5,
	        rowList: [5, 10, 20, 30],
	        sortname: "cmBankid",
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
	        editurl:"Department.html?update",
	        caption: "Organisation List"
	    }); 
	}); 

	
	$(function() {	
		$("#createData").click(function(){			
			var cmBankname = $("#tbCustbanksMas_cmBankname").val();
			
			if(cmBankname == ''){
	
				var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button><ul><li>Please enter Bank Name</li></ul>';
				$("#errorDivCustBankMas").html(errMsg);
				$("#errorDivCustBankMas").show();
				return false;
			}			
			var url = "Department.html?addChildForm";
			$.ajax({
				url : url,
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
				
		});
			
	});
	
	
	$('#dpCheckId').change(function(){
	     if($('input:checkbox[id=dpCheckId]').is(':checked')){
	          
	    	 $('#dpCheckId').attr('checked', true); 
	    	 $('#dpCheckId').val(true); 
	     }else{
	    	 $('#dpCheckId').attr('checked', false); 
	    	 $('#dpCheckId').val(false);
	       
	     }
	    
	});
	
	function closeOutErrBox(){
		$('.error-div').hide();
	}
	
	 function checkForTransaction(){
		 var requestData = "dpDeptid="+$('#dpDeptid').val();
		 var checkUrl = "Department.html?checkIfDepOrgMapExists";
		 var returnCheck=__doAjaxRequestForSave(checkUrl, 'post',requestData, false,'');
		 if(returnCheck > 0){
			 var msg = 'Transaction exists with '+$('#tbDepartment_dpDeptdesc').val()+' department, cannot make it inactive';
			 $(errMsgDiv).html("<h4 class=\"text-center text-blue-2 padding-10\">"+msg+"</h4>");
				$(errMsgDiv).show();
				showMsgModalBoxDep(errMsgDiv);
				$('#isActive1').prop('checked',true).val('A');
		 }
	 }
	 
		function showMsgModalBoxDep(childDialog) {
			$.fancybox({
				type : 'inline',
				href : childDialog,
				openEffect : 'elastic',
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
	 

	function validateData(obj) {

		var errorList = [];
		var formMode = $('#formModeId').val();
		
		var dpDeptDesc = $("#tbDepartment_dpDeptdesc").val();
		var dpNameMar  = $("#tbDepartment_dpNameMar").val();
		var dpDeptcode = $.trim($("#tbDepartment_dpDeptcode").val())
		
		if(dpDeptDesc == "" || dpDeptDesc == " "){
			errorList.push(getLocalMessage("department.error.dpDeptdesc"));
		}
		if(dpNameMar == "" || dpNameMar == " "){
			errorList.push(getLocalMessage("department.error.dpNameMar"));
		}
		if(dpDeptcode == "" || dpDeptcode == " "){
			errorList.push(getLocalMessage("department.error.dpDeptcode"));
		}
		if(dpDeptcode.length<2){
			errorList.push(getLocalMessage("department.error.depShortCodeTwochar"));
		}

		if($('#isActive1').prop('checked') == false && $('#isActive2').prop('checked') == false){
			errorList.push(getLocalMessage("department.error.activeness"));
		}
			
		//Validations
		if(formMode == 'create') {	

			if($("#tbDepartment_dpDeptdesc").val() != null && $("#tbDepartment_dpDeptdesc").val() != ''){
				var url1 = "Department.html?validateDepartment";
				var requestData1 = {
						"deptName" : $("#tbDepartment_dpDeptdesc").val()
					};
				var response1 =__doAjaxRequest(url1, 'post',requestData1, false,'','');
				if(response1 != 0){
					errorList.push("Duplicate Name exists");
				}
			}
			
			if($("#tbDepartment_dpDeptcode").val() != null && $("#tbDepartment_dpDeptcode").val() != ''){
				var url2 = "Department.html?validateDeptOnDeptcode";
				var requestData2 = {
						"deptCode" : $("#tbDepartment_dpDeptcode").val()
					};
				
				var response2 =__doAjaxRequest(url2, 'post',requestData2, false,'','');
				if(response2 != 0){
					errorList.push("Duplicate Short Code exists");
				}
			}
			
		}

		if(errorList.length > 0){
			showDeptError(errorList);
		}else {
			var	formName =	findClosestElementId(obj, 'form');
			var theForm	=	'#'+formName;
			var requestData = __serializeForm(theForm);	
			var url	=	$(theForm).attr('action');	
			var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
			if($.isPlainObject(returnData)) {								
				showConfirmBox();
			} else {
				$(".widget-content").html(returnData);
				$(".widget-content").show();
				$('.error-div').hide();
				return false;
			}
		}
		return false;
	}
	
	function showDeptError(errorList){
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';			 
		$("#errorDivDeptMas").html(errMsg);
		$("#errorDivDeptMas").show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		return false;
	}
	
	function showConfirmBox(){
		$('.alert').hide();
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">Form Submitted Successfully</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="proceed()"/>'+
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}
	
	function proceed() {
		window.location.href='Department.html';
	}
	
	function checkboxFormatter(cellvalue, options, rowObject) {
		if(rowObject.mapStatus == 'A'){			
			return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='checkboxClick(this);' checked >";			
		} else{			
			return "<input type='checkbox' name='checkboxIsCC' id='checkboxIsCC' onchange='checkboxClick(this);' >";			
		}
	}

	function checkboxClick(obj){
		 
		organisationId = $(obj).closest('tr').find('td:eq(0)').text();		
		var isChecked = 'N';
		
		if($(obj).attr( "checked" )){			
			isChecked = 'Y';
		}
		

		
		var url = "Department.html?updateStatus";
		var returnData = "organisationId="+organisationId+"&isChecked="+isChecked;
		$.ajax({
			url : url,
			data : returnData,
			success : function(response) {
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	}
	
	function resetDept(){
	    $('.error-div').hide();
	}
	