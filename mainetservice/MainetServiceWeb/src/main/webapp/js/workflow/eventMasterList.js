
 $(document).ready(function(){
	 $('#service-div').hide();
 });


var selectedIds = new Array();

	var eventId = '';
	var serviceEventId ='';
	
	 $(document).ready(function(){
		  	$('#createData').hide();
			$('#editData').hide();
			$('#viewData').hide();
	 });
	
	$(function () {
	    $("#grid").jqGrid({
	        url: "EventMaster.html?getGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("event.name"), 
	                   getLocalMessage("event.nameReg"), 
	                   getLocalMessage("event.desc"),
	                   getLocalMessage("event.url")
	                 /*  ,"Status"*/
	                   /*""*/],
	        colModel: [
	            { name: "eventName", width: 55, sortable: true,search : true },
	            { name: "eventNameReg", width: 55, sortable: false,search : true },
	            { name: "eventDesc", width: 66, sortable: false,search : true },
	            { name: "serviceUrl", width: 70, sortable: false,search : true }
	            /*, { name: 'isdeleted', index: 'isdeleted', width: 30, align: 'center', edittype:'checkbox',formatter:statusFormatter, editoptions: { value: "Yes:No" },
            		formatoptions: { disabled: false },search:false
	            }*//*,
	            { name: 'serviceEventId',hidden:true}*/
	        ],
	        pager: "#pagered",
	        rowNum: 5,
	        rowList: [5, 10, 20, 30],
	        sortname: "eventId",
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
	        editurl:"EventMaster.html?update",
	        caption: getLocalMessage("workflow.grid.header")
	    }); 
	    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
		$("#pagered_left").css("width", "");
	}); 
	
	
	function statusFormatter(cellvalue, options, rowdata) {	
		if(rowdata.isdeleted == 'N'){
			return "<a title='Event is Active' alt='Event is Active' value='Y' class='fa fa-check-circle fa-2x green' href='#'></a>";
			/*href='javascript:showConfirmBox(\"" + rowdata.serviceEventId + "\",\"" + rowdata.isdeleted+ "\")\'
*/		}else{
			return "<a title='Event is Inactive' alt='Event is Inactive' value='N' class='fa fa-times-circle fa-2x red' href='#'></a>";
		}
	}
	
	/*
	 * Might Require aftewards
	 */
	/*function showConfirmBox(serviceEventId,isdeleted){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Yes';
		var msg = '';
		if(isdeleted == 'N'){
			msg = "Are you sure want to deactivate the event?";
		}else{
			msg = "Are you sure want to activate the event?";
		}
		message	+='<p class=\"text-blue-2 text-center padding-10\">'+msg+'</p>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="deleteData(\''+serviceEventId+ '\',\'' + isdeleted+'\')"/>'+	
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function deleteData(serviceEventId,isDeleted){

		var url = "EventMaster.html?delete";	
		var requestData = "serviceEventId="+serviceEventId+"&isDeleted="+isDeleted;	
		console.log(requestData);
		$.ajax({
			url : url,
			data : requestData,
			type: 'post',
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
			
	}*/
	
	$(function() {
		
		$("#createData").click(function(){
			
			var errorList = [];
			
			if($('#dpDeptId').val() != ""){
				if($('#serviceId option').length > 1 && $('#serviceId').val() != "" && $('#serviceId').val() != 0 && $('#serviceId').val() != null){
					var url = "EventMaster.html?validateEvent";
					var requestData = {
							"dpDeptId":$('#dpDeptId').val(),
							"serviceId":$('#serviceId').val()
					}
					
					var response=__doAjaxRequest(url,'post',requestData,false,'json');
					if(response == false){
						//errorList.push('Events are added for \"'+$('#dpDeptId option:selected').text()+'\" department and \"'+$('#serviceId option:selected').text()+'\" service');
						errorList.push(getLocalMessage("workflow.type.val.events.exist"));
						showEvntError(errorList);
					}else{
						var url = "EventMaster.html?form";
						var returnData = "";			
						$.ajax({
							url : url,
							data : requestData,
							success : function(response) {
								
								var divName = '.form-div';
								$(".widget-content").html(response);
								$(".widget-content").show();
								$('.error-div').hide();
							},
							error : function(xhr, ajaxOptions, thrownError) {
								var errorList = [];
								errorList.push(getLocalMessage("admin.login.internal.server.error"));
								showEvntError(errorList);
							}
						});	
					}					
				}else if($('#serviceId option').length <= 1){
					errorList.push(getLocalMessage("workflow.type.val.no.service")+$('#dpDeptId option:selected').text()+getLocalMessage("workflow.type.val.service.dept"));
					showEvntError(errorList);
				}else{
					errorList.push(getLocalMessage("workflow.type.val.select.service"));
					showEvntError(errorList);
				}
			}else{
				errorList.push(getLocalMessage("workflow.type.val.select.dept"));
				showEvntError(errorList);
			}						
		});
			
	});
	
	function actionFormatter(cellvalue, options, rowObject) {
		return "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"showConfirmBox('"+rowObject.serviceEventId+"')\"><i class='fa fa-trash' aria-hidden='true'></i></a>";
	}

	
	function updateViewForm(mode){
		
		var errorList = [];
		var deptId = $('#dpDeptId').val();
		var serviceId = $('#serviceId').val();
		
		if(deptId == "" && serviceId == ""){
			errorList.push(getLocalMessage("workflow.type.val.select.dept.service"));
			showEvntError(errorList);
		}else{
			var requestData = {
					"deptId" : deptId,
					"smserviceId": serviceId,
					"mode" : mode
			};
			var url = "EventMaster.html?formforUpdate";
			var response =__doAjaxRequest(url,'post',requestData,'','');

			if(response != null || response != ""){
				$(".widget-content").html(response);
				$(".widget-content").show();	
			}else{
				errorList.push(getLocalMessage("workflow.type.val.events.not.mapped"));
				showEvntError(errorList);
			}
		}
	}
	
	
	function closeOutErrBox(){
		$('#errorDivDeptMas').hide();
	}
	
	function getServices(){
		$('#serviceId').val(0);
		$("#errorDivDeptMas").hide();
		
		if($('#dpDeptId').val() != 0){

			var requestData = {
					"deptId":$('#dpDeptId').val()
			}		
			var result=__doAjaxRequest("EventMaster.html?services",'post',requestData,false,'json');
			if(result != null && result != ""){				
				$('#serviceId').html('');
				$('#service-div').show();
				$('#serviceId').append($("<option></option>").attr("value","0").
						text(getLocalMessage('workflow.form.select.service')));
				$.each(result, function(index, value) {
					if($('#langId').val() == 1){
						$('#serviceId').append($("<option></option>").attr("value",value[0]).text(value[1]));
					}else{
						$('#serviceId').append($("<option></option>").attr("value",value[0]).text(value[2]));
					}
				});
				$(".chosen-select-no-results").trigger("chosen:updated");
			}else{
				$('#createData').hide();
				var errorList = [];
				$('#service-div').hide();
				errorList.push(getLocalMessage('workflow.type.val.no.service') + $('#dpDeptId option:selected').text());
				showEvntError(errorList);
			
			}
		}
		
		

	}
	
	function searchEvents() {
		$("#errorDivDeptMas").hide();
		$('#grid').jqGrid('clearGridData').trigger('reloadGrid');
		var errorList = [];
		var serviceId = $('#serviceId').val();
		var deptId = $('#dpDeptId').val();
		if(serviceId != 0 && deptId != 0 && serviceId !=null){
			
			var url = "EventMaster.html?searchEvents";
			var requestData = {"dpDeptId" : deptId,
					"serviceId" : serviceId
					};
			var result=__doAjaxRequest(url,'post',requestData,false,'');

			if(result > 0){
				$('#createData').hide();
				$('#editData').show();$('#viewData').show();
			}else{
				$('#createData').show();
				$('#editData').hide();$('#viewData').hide();
			}
			$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		}else if(deptId == 0 && serviceId == 0){
			errorList.push(getLocalMessage('workflow.type.val.select.dept.service'));
			showEvntError(errorList);
		}else if(serviceId == 0 && serviceId ==null){
			errorList.push(getLocalMessage('workflow.type.val.select.service'));
			showEvntError(errorList);
		}else if(deptId == 0){
			errorList.push(getLocalMessage('tax.error.dpDept'));
			showEvntError(errorList);
		}
	}
	
	function showEvntError(errorList){
		
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorDivDeptMas").html(errMsg);
		$('#errorDivDeptMas').show();
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
	
	
	function eventMstReset(){
		$('#dpDeptId').val('').trigger('chosen:updated');
		$('#serviceId').val('').trigger('chosen:updated');
		$('#serviceId').html('').trigger('chosen:updated');
		$("#serviceId") .append($("<option></option>").attr("value","").text("Select Service"));
		$(".chosen-select-no-results").trigger("chosen:updated");
		$(".error-div").hide();
		$('#service-div').hide();
		$('#grid').jqGrid('clearGridData').trigger('reloadGrid');
		$('#editData').hide();
		$('#viewData').hide();
		$('#createData').show();
	}