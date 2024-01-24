$(function() {	
		$(document).on('click', '.createData', function() {
	
			var url = "ConfigurationMaster.html?form";
		    $.ajax({
				url : url,
				success : function(response) {					
					$(".widget-content").html(response);
					$(".widget-content").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});				
		});			
	});
    $(function () {
    $("#grid").jqGrid({
        url: "ConfigurationMaster.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: ['',getLocalMessage('account.configuration.compname'),getLocalMessage('accounts.no.levels'),getLocalMessage('accounts.master.status'),"","",getLocalMessage('bill.action')],
        colModel: [ 
            {name : "codcofId",width : 10,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
            {name : "comDesc",width : 80,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
            {name : "codNoLevel",width : 40,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
            {name : "componentStatus",width : 35,sortable : true,align : 'center',editable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
            {name : "comChagflag",width : 10,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true},
            {name : "codcofId",width : 10,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true},
            { name: 'codcofId', index: 'codcofId', width:35 , align: 'center !important',formatter:addLink,search :false},
        ],
        pager: "#pagered",
        emptyrecords: getLocalMessage("jggrid.empty.records.text"),
        rowNum: 30,
        rowList: [5, 10, 20, 30],
        sortname: "comDesc",
        sortorder: "comDesc",
        height:'auto',
        viewrecords: true,
        gridview: true,
        showrow: true,
        rownumbers:  true,
        loadonce: false,
        
       jsonReader : {
            root: "rows",
            page: "page",
            total: "total",
            records: "records", 
            repeatitems: false,
           }, 
        autoencode: true,
        editurl:"ConfigurationMaster.html?update",
        caption: getLocalMessage('account.configuration.master.list')
    }); 
});
    function editMst(cellValue, options, rowdata, action) {
	    return "<a href='#'  return false; class='editClass' value='"+rowdata.codcofId+"'><img src='css/images/edit.png' width='20px' alt='Edit Master' title='Edit Master' /></a>";
	}
    
	function addLink(cellvalue, options, rowdata) 
	 {
		return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.codcofId+"' id='"+rowdata.codcofId+"' ><i class='fa fa-building-o'></i></a> " +
  		 "<a class='btn btn-warning btn-sm editClass' title='Edit'value='"+rowdata.codcofId+"' id='"+rowdata.codcofId+"' ><i class='fa fa-pencil'></i></a> ";
	 }
    
	$(document).on('click', '.editClass', function() {
		var errorList = [];
		var $link = $(this);
		var codcofId = $link.closest('tr').find('td:eq(6)').text();
		var comChagflag = $link.closest('tr').find('td:eq(5)').text();
		var authStatus = $link.closest('tr').find('td:eq(4)').text();
		var url = "ConfigurationMaster.html?formForUpdate";
		var requestData = {"codcofId" : codcofId};
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(comChagflag=="Y" && authStatus!="Inactive"){
			$(".widget-content").html(returnData);
			$(".widget-content").show();
		}
		else{
			if(comChagflag!="Y"){
			errorList.push(getLocalMessage("account.protected.orgonization"));
			}
			else if(authStatus=="Inactive"){
			errorList.push("It is already Inactive so EDIT is not allowed!");
			}
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    	});
		    	errorMsg +='</ul>';
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
		    }
		}
	});
    
    $(document)
	.on(
			'click',
			'.viewClass',
			function() {
				var $link = $(this);
				var codcofId = $link.closest('tr').find('td:eq(6)').text();
				
				var url = "ConfigurationMaster.html?formForView";
				var requestData = "codcofId=" + codcofId + "&MODE_DATA=" + "VIEW";
				
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				$(".widget-content").html(returnData);
				$(".widget-content").show();
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type=checkbox]').attr('disabled', true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				return false;
			});
    
    $(function() {		
		$(document).on('click', '.deleteClass', function() {
			
			var codcofId = $(this).attr('value');
			showConfirmBox(codcofId);
		});
			
	});
    function showConfirmBox(codcofId){
		var	errMsgDiv	='.msg-dialog-box';
		var message='';
		var cls = 'Yes';
		
		message	+='<p>'+getLocalMessage("configuration.master.delete")+'</p>';
		 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
		'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
		' onclick="deleteData('+codcofId+')"/>'+
		'</p>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function deleteData(codcofId){
		
		var url = "ConfigurationMaster.html?delete";
		var returnData = {"codcofId" : codcofId}
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
			data : returnData,
			success : function(response) {
				
					$.fancybox.close();
					$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
		$.fancybox.close();
		$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	}
	 function deleteMst(cellValue, options, rowdata, action) {
	    return "<a href='#'  return false; class='deleteClass' value='"+rowdata.codcofId+"'><img src='css/images/delete.png' width='20px' alt='Delete Master' title='Delete Master' /></a>";
	} 
	 
	 function saveConfiguration(element)
	 {
	 	var errorList = [];	
	     var comType = $.trim($("#comCpdId").val());
	     if(comType==0 || comType=="")
	     errorList.push(getLocalMessage('account.select.componentName'));
	     var noOfLevel = $.trim($("#noOfLevel").val());
	     if(noOfLevel==0 || noOfLevel=="")
	     errorList.push(getLocalMessage('account.enter.no.of.level'));
	     $('.appendable').each(function(i) {
	 	 var digits = $.trim($("#codDigits"+i).val());
	 	 var level=i+1;
	 	 if(digits==0 || digits=="")
	 		    errorList.push(getLocalMessage("Please Enter No of Digits for"+" "+level+" "+"level"));
	      });
	     $('.appendableDesc').each(function(i) {
	 	 var digits = $.trim($("#codDescription"+i).val());
	 	 var level=i+1;
	 	 if(digits==0 || digits=="")
	 		    errorList.push(getLocalMessage("Please Enter Description for"+" "+level+" "+"level"));
	     });    
	    if(errorList.length > 0){ 
	 	 
	 	var errMsg = '<ul>';
	 	$.each(errorList, function(index) {
	 		errMsg += '<li>' + errorList[index] + '</li>';
	 	});

	 	errMsg += '</ul>';

	 	$('#errorId').html(errMsg);
	 	$('#errorDivId').show();
	 	return false;
	 	
	     }
	     var	formName =	findClosestElementId(element, 'form');
	     var theForm	=	'#'+formName;
	     var requestData = __serializeForm(theForm);
	     var url	=	$(theForm).attr('action');
	     var res= __doAjaxRequestForSave(url, 'post', requestData, false,'', element); 
	     var obj=$(res).find('#tbAcCodingstructureMas');
	   if(obj.find('#alreadyExist').val()=='Y')
	         {
	     	  showSuccess();
	         }
	       else
	 	  {
	 	  $(".widget-header").html(res);
	       $(".widget-header").show();
	 	  }
	   
	     }
	 function showSuccess()
	    {
	    	 var	errMsgDiv		=	'.msg-dialog-box';
				var message='';
				var Master=getLocalMessage("account.Configuration.Master.Submitted");
				var cls =  getLocalMessage("account.proceed.btn");
				message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+Master+'</h5>';
				message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
				$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
				$(errMsgDiv).html(message);
				$(errMsgDiv).show();
				$('#btnNo').focus();
				showModalBoxWithoutClose(errMsgDiv);
			
	        }
	 function proceed() {
		window.location.href = 'ConfigurationMaster.html'; 
	 }
	
	 function closeErrBox() {
		$('.error-div').hide();
	 }
	 
	 function searchConfigurationMasterData(){
			$('.error-div').hide();
			var errorList = [];
			var comCpdId = $("#comCpdId").val();
			if ((comCpdId == "" || comCpdId =="0")) {
				errorList.push(getLocalMessage("account.bank.select.component"));
			}
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    		
		    	});
		    	errorMsg +='</ul>';
		    	
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
		    		    	
		    }
			 
			if (errorList.length == 0) 
			{
			
			var url ="ConfigurationMaster.html?getjqGridsearch";
			var requestData = {
					"comCpdId" : comCpdId,
			};
			
			var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
			
			if (result != null && result != "") {
				$("#grid").jqGrid('setGridParam', {
					datatype : 'json'
				}).trigger('reloadGrid');
			} else {
				var errorList = [];
				
				errorList.push(getLocalMessage("account.norecord.criteria"));
				
				if(errorList.length>0){		    	
			    	var errorMsg = '<ul>';
			    	$.each(errorList, function(index){
			    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';		    		
			    	});
			    	errorMsg +='</ul>';		    	
			    	$('#errorId').html(errorMsg);
			    	$('#errorDivId').show();
					$('html,body').animate({ scrollTop: 0 }, 'slow');		    		    	
			    }
				$("#grid").jqGrid('setGridParam', {
					datatype : 'json'
				}).trigger('reloadGrid');
			}
		}
	};
	 