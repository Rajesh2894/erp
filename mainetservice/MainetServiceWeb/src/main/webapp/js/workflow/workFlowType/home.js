/**
 * ritesh.patil
 * 
 */
workFlowUrl='WorkFlowType.html';
$(document).ready(function(){
	var errorList=[];
	//alert( jQuery('#flowGrid').jqGrid('getGridParam', 'reccount'));
	  $("#flowGrid").jqGrid(
			{     
				url : workFlowUrl+"?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('workflow.type.grid.deptname'),getLocalMessage('workflow.type.service.name'),getLocalMessage('workflow.type.complain'),getLocalMessage('workflow.type.workflowmode'),getLocalMessage('workflow.type.location.type'),getLocalMessage('workflow.type.status'),getLocalMessage('estate.grid.column.action'),'','','',''],
				colModel : [ {name :  "id",width : 5,  hidden :  true},
				             {name :  "depName",width : 20,  sortable : true},
				             {name :  "serviceName",width : 20,search : true}, 
				             {name :  "compDesc",width : 20,search :true}, 
				             {name :  "workFlowMode",width : 15,search :true},
				             {name :  "workflowType",width : 15,search :true,align : 'center'},
				             {name :  "status",width : 10,search :true, align:'center',formatter:statusFormatter},
				             {name :  'enbll', index: 'enbll', width: 15, align: 'center !important',formatter:addLink,search :false},
				             {name :  "deptId",width : 5,  hidden :  true},
				             {name :  "orgId",width : 5,  hidden :  true },
				             {name :  "serviceId",width : 5,  hidden :  true},
				             {name :  "compId",width : 5,  hidden: true }
				            ],
				pager : "#flowPager",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "name",
				sortorder : "desc",
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : true,
				postData : {    
					compHidden : function() {
						  return $('#compHidden').val();
					},
					deptIdHidden : function() {
						  return $("#deptId").val();
					},
					serviceIdHidden : function() {
						  return $("#serviceId").val();
					}
				 },
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,					
				},
				
				autoencode : true,
				caption : getLocalMessage('workflow.grid.workflowmaster'),
				loadComplete: function(data){ 
					var errorList=[];
					if(jQuery('#flowGrid').jqGrid('getGridParam', 'reccount')==0){
						 errorList.push(getLocalMessage('workflow.NoWorkflow.define'));
					}
					if(errorList.length>0){
						showRLValidation(errorList);
					}else{
						$('.error-div').hide();
					}
				   },
			});

	      $("#flowGrid").jqGrid('navGrid','#flowPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");
	
	  $('#addFlowLink').click(function() {
		var ajaxResponse = __doAjaxRequest(workFlowUrl+'?form', 'POST', {}, false,'html');
		$('.content').html(ajaxResponse);
	 });
	  
	  
	  $('#searchWorkFlow').click(function(){
		  var errorList=[];
		 $('#flowGrid').jqGrid('clearGridData').trigger('reloadGrid');
		 $("#deptIdHidden").val( $("#deptId").val());
		 $("#serviceIdHidden").val( $("#serviceId").val());
		 if($("#deptId").val()>0 ||  $("#serviceId").val()>0){
			 $("#flowGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
			 //D#35991 
			  /*if( jQuery('#flowGrid').jqGrid('getGridParam', 'reccount')==0){
			    	$('#flowGrid').jqGrid('clearGridData').trigger('reloadGrid');
			    	 errorList.push(getLocalMessage('No Workflow defined.'));
			    }*/
		 }else{
			 errorList.push(getLocalMessage('workflow.type.validation.dept.service'));
		 }
	    if(errorList.length>0){
			showRLValidation(errorList);
		}else{
			$('.error-div').hide();
		}
	});
	  
});	 



function addLink(cellvalue, options, rowObject) 
{
	if(rowObject.status == 'Y'){
		return  "<a class='btn btn-blue-3 btn-sm' title='"+getLocalMessage('workflow.type.view.mode')+"' onclick=\"showFlow('"+rowObject.id+"','V')\"><i class='fa fa-eye'></i></a> " +
		   " <a class='btn btn-warning btn-sm' title='"+getLocalMessage('workflow.type.edit.mode')+"' onclick=\"showFlow('"+rowObject.id+"','E')\"><i class='fa fa-pencil'></i></a> ";	
	}else if(rowObject.status == 'N'){
		return  "<a class='btn btn-blue-3 btn-sm' title='"+getLocalMessage('workflow.type.view.mode')+"' onclick=\"showFlow('"+rowObject.id+"','V')\"><i class='fa fa-eye'></i></a> " ;
	}
}

function statusFormatter (cellvalue, options, rowObject){
	if(rowObject.status == 'Y'){
		return "<a title='Workflow is Active' alt='Workflow is Active' value='Y' class='fa fa-check-circle fa-2x green' href='#'></a>" ;
	}else{
		return "<a title='Workflow is Inactive' alt='Workflow is Inactive' value='N' class='fa fa-times-circle fa-2x red' href='#'></a>";
	}	
}

function deleteFlow(id,deptId,orgId,comId){
   
	 var yes = getLocalMessage('eip.commons.yes');
	 var warnMsg="Are you sure to delete ?" ;
	 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
	' onclick="onDelete(\''+id+'\',\''+deptId+'\',\''+orgId+'\',\''+comId+'\')"/>'+	
	'</div>';
	
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;
}

function onDelete(id,deptId,orgId,comId){

		 var requestData = 'id='+id;
		 var response = __doAjaxRequest(workFlowUrl+'?deleteFlow', 'POST', requestData, false,'json');
		 if(response){
			   $("#flowGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
			   closeFancyOnLinkClick(childDivName);
			   back();
	     }else{
		    	    $(childDivName).html("Internal errors");
		    		showModalBox(childDivName);
		     }
}

function showFlow(id,type){
	var divName	=	formDivName;
    var requestData = 'id='+id+'&type='+type;
	var ajaxResponse	=	doAjaxLoading(workFlowUrl+'?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);
	if($('#hiddeValue').val() == 'V'){
		 $("#workFlowTypeForm :input").prop("disabled", true);
		// $("select").chosen().trigger("chosen:updated")
		 $("#backBtn").removeProp("disabled");
		 //$('.addCF').bind('click', false);
		 //$('.remCF').bind('click', false);
	}
	if($('#hiddeValue').val() == 'E'){
		 $("#resetFlow").prop("disabled", true);
	}
}


function showConfirmBox(){
	var successMsg = getLocalMessage('workflow.type.sucess');
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	 message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function proceed () {
	window.location.href='WorkFlowType.html';
}


function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    errMsg += '<ul>';
    $.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}

function back() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',workFlowUrl);
	$("#postMethodForm").submit();
}



function getServices(obj){
  	var requestData = {"orgId":$('#orgId').val(),"deptId":$(obj).val()};
  	$('#serviceId').html('');
  	$('#serviceId').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown')));
  	$('#complaintId').html('');
  	$('#complaintId').append($('<option></option>').attr("value","0").text(getLocalMessage('selectdropdown')));
  	var result=__doAjaxRequest("WorkFlowType.html?services",'post',requestData,false,'json');
  	var serviceList=result[0];
  	var compList=result[1]; 
  	 $.each(serviceList, function(index, value) {
  		 if($('#langId').val() == 1){
  			$('#serviceId').append($("<option></option>").attr("value",value[0]).attr("code",value[2]).text(value[1]));
  		 }else{
  			$('#serviceId').append($("<option></option>").attr("value",value[0]).attr("code",value[2]).text(value[3]));
  		 }
  	});
  		$.each(compList, function( index, value ) {
  			$('#complaintId').append($('<option></option>').attr("value",value.compId).text(value.complaintDesc));
  		});
  		$('#serviceId').trigger("chosen:updated");
  		$('#complaintId').trigger("chosen:updated");
  	
  };
  
