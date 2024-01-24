
var smServiceId = '';
$(function () {
    $("#grid").jqGrid({
        url: "CommonRemarkMaster.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: [getLocalMessage("master.Remark"), getLocalMessage("master.Remark"), getLocalMessage("master.serviceActive"),getLocalMessage("edit.msg"),getLocalMessage("view.msg")],
        colModel: [            
            { name: "artRemarks", width: 70, sortable: false },
            { name: "artRemarksreg", width: 70, sortable: false },
            { name: "statusflag", width: 10, sortable: false, formatter:returnisdeletedUrl},
            { name: 'artId', index: 'smServiceId', width: 10, align: 'center', sortable: false,formatter:editServiceMst},
            { name: 'artId', index: 'smServiceId', width: 10, align: 'center', sortable: false,formatter:viewServiceMst},
           
        ],
        pager: "#pagered",
        rowNum: 5,
        rowList: [5, 10, 20, 30],
        sortname: "cpmId",
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
        editurl:"ServiceMaster.html?update",
        caption: getLocalMessage("master.Remark.gridheader")
    }); 
    
    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
	$("#pagered_left").css("width", "");
}); 

 function returnisdeletedUrl(cellValue, options, rowdata, action) {
	if (rowdata.statusflag =="Y") {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowdata.statusflag+"'  alt='Remark is Active' title='Remark is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowdata.statusflag+"' alt='Remark is  INActive' title='Remark is Active'></a>";
	} 
 }
function editServiceMst(cellValue, options, rowdata, action) {
	//smServiceId = rowdata.smServiceId;
    return "<a href='#'  return false; class='editClass' value='"+rowdata.artId+"'><img src='css/images/edit.png' width='20px' alt='Edit Comparam Master' title='Edit CommonRemark Master' /></a>";
}
function viewServiceMst(cellValue, options, rowdata, action) {
	//smServiceId = rowdata.smServiceId;
    return "<a href='#'  return false; class='viewClass' value='"+rowdata.artId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='Edit Comparam Master' title='View CommonRemark Master' /></a>";
}

$(function() {
	
	$(document).on('click', '.editClass', function() {
		var artId = $(this).attr('value');
		var returnData = "artId="+artId+ "&MODE="
		+ "EDIT"; 
		var url = "CommonRemarkMaster.html?formForUpdate";
		
		$.ajax({
			url : url,
			data : returnData,
			success : function(response) {						
				
				var divName = '.form-div';				
				$("#divid").html(response);				
				/* $("#content").show();  */
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
	});		
	
});

$(function() {
	
	$(document).on('click', '.viewClass', function() {
		
		var artId = $(this).attr('value');
		var returnData = "artId="+artId+ "&MODE="
		+ "VIEW"; 
		var url = "CommonRemarkMaster.html?formForUpdate";
		
		$.ajax({
			url : url,
			data : returnData,
			success : function(response) {						
				
				var divName = '.form-div';
				
				$("#divid").html(response);	
				$('.form-control').find('*').addClass(
				"disablefield");
				$("#saveBtn").hide();
				/* $("#content").show();  */
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
	});		
	
});


$(function() {
	
	$(document).on('click', '.deleteClass', function() {
		var serviceId = $(this).attr('value');
		var returnData = "serviceId="+serviceId;
		var checkUrl = "ServiceMaster.html?checkForTransactionExist";
		
		$.ajax({
			url : checkUrl,
			data : returnData,
			success : function(response) {
				
				if(response != 0) {
					$(errMsgDiv).html("<b>Transaction Exist, can not edit</b>");
					$(errMsgDiv).show();
					showModalBox(errMsgDiv);
					
					return false;
				} else {					
					showConfirmBox(serviceId);
				}				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	});		
});

$(function() {
	
	$(document).on('click', '.deleteClass', function() {
		var serviceId = $(this).attr('value');
		var returnData = "serviceId="+serviceId;
		var checkUrl = "ServiceMaster.html?checkForTransactionExist";
		
		$.ajax({
			url : checkUrl,
			data : returnData,
			success : function(response) {
				
				if(response != 0) {
					$(errMsgDiv).html("<b>"+getLocalMessage("remark.master.val.trnExist")+"</b>");
					$(errMsgDiv).show();
					showModalBox(errMsgDiv);
					
					return false;
				} else {					
					showConfirmBox(serviceId);
				}				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	});		
});


function showConfirmBox(serviceId){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	
	message	+='<p>'+getLocalMessage("remark.master.val.wantToDelete")+'</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="deleteData('+serviceId+')"/>'+	
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function deleteData(returnData){
	
	var url = "ServiceMaster.html?delete";
	var returnData = "serviceId="+returnData;
	$.ajax({
		url : url,
		data : returnData,
		success : function(response) {						
			$.fancybox.close();
			//$("#grid").trigger('reloadGrid');
			$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	
	
}
function closeErrBox() {
	$('.error-div').hide();
}
function searchServiceMst(obj) {
	
	var deptId = $("#artType").val();
	var deptId2 = $("#deptId").val();
	var serviceId = $("#serviceId").val();

	var errorList = [];
	if(deptId == '') {
		/*var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a RemarkType.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;*/
		 errorList.push(getLocalMessage("remark.master.val.selRemType")); 
	}
	
	if(deptId2 == '') {
		/*var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Department.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;*/
		errorList.push(getLocalMessage("remark.master.val.selDep")); 
	}
	
	if(serviceId == '') {
		/*serviceId = -1;
		var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Service.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;*/
		errorList.push(getLocalMessage("remark.master.val.selService")); 
	}
	if(errorList.length>0){
    	/*var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    	});
    	errorMsg +='</ul>';
    	$('#errorId').html(errorMsg);
    	$('.error-div').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');	*/
		displayErrorsOnPage(errorList);
		 return false;
	}
	$('.error-div').hide();
	var url = "CommonRemarkMaster.html?searchServiceMst";
	var returnData = "deptId="+deptId+"&serviceId="+serviceId;
	
	$.ajax({
		url : url,
		method: "POST",
		data : returnData,
		success : function(response) {
            // Handle the case where the user may not belong to any groups
            if( response.length == 0 )
            	{
                            var errMsg = '<div class="error-div alert alert-danger alert-dismissible">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>'+getLocalMessage("remark.master.val.deptAndSevice")+'</li></ul></div>';
                    		$(".error-div").html(errMsg);
                    		$(".error-div").show();
                    		/* $("#serviceRuleDefMas").show(); */
            }
			$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
}

function openAddForm(){
	
	
	var url = "CommonRemarkMaster.html?form";
	
	$.ajax({
		url : url,
		success : function(response) {						
			
			var divName = '.content';				
			$("#divid").html(response);	
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
}

function openSaveForm(){
	$('.error-div').hide();
	var errorList = [];
	var deptId = $("#artType").val();
	var deptId2 = $("#deptId").val();
	var serviceId = $("#serviceId").val();

	
	if(deptId == '') {
	/*	var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a RemarkType.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;*/
		errorList.push(getLocalMessage("remark.master.val.selRemType")); 
	}
	if(deptId2 == '') {
	/*	var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Department.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;*/
		errorList.push(getLocalMessage("remark.master.val.selDep")); 
	}
	
	if(serviceId == 'Select') {
		/*serviceId = -1;
		var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Service.</li></ul></div>';
		$("#serviceRuleDefMas").html(errMsg);
		$("#serviceRuleDefMas").show();
		return false;*/
		 errorList.push(getLocalMessage("remark.master.val.selService")); 
	}
	
	//
	
	
     $('.remarkClass').each(function(i){
    	var remarkList=$.trim($("#remarkList"+i).val());
    	var remarkListreg= $.trim($("#remarkListreg"+i).val());
    	 if(remarkList==''||remarkList==null ){
    		 errorList.push(getLocalMessage("remark.master.val.plsEntRemarkEng")+" "+i); 
    	 }
    	 if(remarkListreg=='' ||remarkListreg==null){
    		 errorList.push(getLocalMessage("remark.master.val.plsEntRemarkHin")+" "+i); 
    		
    	 }
    	 
     });
	
	if(errorList.length>0){
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    	});
    	errorMsg +='</ul>';
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');	
	
		 return false;
	}
	
	var url = "CommonRemarkMaster.html?create";
	var data = $("#serviceruleDefForm,#serviceruleDefForm2").serialize()+ "&MODE="
	+ "create";
	$.ajax({
		url : url,
		data : data,
		success : function(response) {
			showRemarkConfirmBox();
			//$(".widget").html(response);
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
	
}
function showRemarkConfirmBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	
	message	+='<h4 class=\"text-center text-blue-2 margin-bottom-10\">'+getLocalMessage("remark.master.val.formSubmitted")+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\' '+ 
	' onclick="proceed()"/>'+	
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);		

}
function proceed() {
	window.location.href='CommonRemarkMaster.html';
}
function refreshServiceData(obj) {
	
	var url = "ServiceMaster.html?refreshServiceData";
	var deptId = $(obj).val();
	
	
	var returnData = "deptId="+deptId
	
	$.ajax({
		url : url,
		method: "POST",
		data : returnData,
		success : function(response) {
			
			$("#serviceId").html('');
			$('#serviceId').append($("<option></option>").attr("value","").attr("code","").text(getLocalMessage('Select')));
			
			if($("#langId").val() == 1) {
				if(response != ""){
					$.each(response, function( index, value ) {
						
						$('#serviceId')
					    .append($("<option></option>")
					    .attr("value",value.smServiceId).attr("code",value.smShortdesc)
					    .text(value.smServiceName));						
					});
					
					$('#serviceId')
				    .append($("<option></option>")
				    .attr("value",-1).text("All"));
				}
				
			} else {
				
				if(response != ""){					
					$.each(response, function( index, value ) {
						$('#serviceId')
					    .append($("<option></option>")
					    .attr("value",value.smServiceId).attr("code",value.smShortdesc)
					    .text(value.smServiceNameMar));
					});
					
					$('#serviceId')
				    .append($("<option></option>")
				    .attr("value",-1).text("All"));			
				}
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
}

function resetHomePage() {
	
	$("#grid").jqGrid("clearGridData");
	
	$('.error-div').hide();
}



$( document ).ready(function() {
	
  var count = 1;
	$("#addMoreReasonDet").click(function () 
			{	
		
		
		$('#reasonTableID tr:last').after(generateReasonTable(count,$('#hearingId').val(),$('#appStatus').val()));
		count++;
			
			
			});
	
	$("#removeMoreRejDet").click(function () 
			{	
		if(count>1)
		{
		$('#reasonTableID tr:last').remove()
		count= count-1;
		}	
		else
			{
				var errMsg = '<div class="error-div alert alert-danger alert-dismissible">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>'+getLocalMessage("remark.master.val.rowExist")+'</li></ul></div>';
				$(".error-div").html(errMsg);
				$(".error-div").show();
			}	 

			
			});
	
	$("#deletMoreReasonDet").click(function () 
			{	
		var artId = $(this).attr('value');
		var returnData = $("#serviceruleDefForm,#serviceruleDefForm2").serialize(); 
		var url = "CommonRemarkMaster.html?Inactivate";
		
		$.ajax({
			url : url,
			data : returnData,
			success : function(response) {						
				showRemarkDeletedConfirmBox();
				//$(".widget").html(response);
				//$(".form-control").val("");				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
			});
	});


function generateReasonTable(count,hearingId,type)
{
	
	var str	='<tr class="remarkClass" >'+	
		'<td><textarea id="remarkList'+count+'"  name="remarkList['+count+']" class=" form-control"   value="" maxlength="1000"/>'+
	'</td>	'+

	'<td>'+
		'<textarea  id="remarkListreg'+count+'" name="remarkListreg['+count+']"  maxlength="1000" class=" form-control "/>'+	
	'</td> '+'</tr>';
	
	return str;

}

function showRemarkDeletedConfirmBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	
	message	+='<h4 class=\"text-center text-blue-2 margin-bottom-10\">'+getLocalMessage("remark.master.val.formUpdated")+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\' '+ 
	' onclick="proceed()"/>'+	
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);		

}

		