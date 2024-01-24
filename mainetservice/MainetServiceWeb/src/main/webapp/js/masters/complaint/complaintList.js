/**
 * ritesh.patil
 * 
 */
complaintUrl='Complaint.html';
$(document).ready(function(){
	$("#compGrid").jqGrid(
			{   
				url : complaintUrl+"?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '', getLocalMessage('common.master.complaintType.deptName'),getLocalMessage('common.master.complaintType.status'),'','' , getLocalMessage('common.master.complaintType.action')],
				colModel : [ {name : "deptCompId",width : 5,  hidden :  true   },
				             {name : "deptName",width : 45,  sortable :  true , align : 'center'   },
				             {name : "deptStatus",width : 45,  sortable :  true ,formatter:statusFormatter, editoptions: { value: "Yes:No" },
				            		formatoptions: { disabled: false }, align : 'center'   },
				             {name : "orgId",width : 5,  hidden :  true   },
				             {name : "deptId",hidden :  true,   },
				             { name: 'enbll', index: 'enbll', width: 45, align: 'center !important',formatter:addLink,search :false ,sortable :  false}
				            ],
				pager : "#compPager",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "name",
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
				caption : getLocalMessage('common.master.complaintType.list')
			});
	       $("#compGrid").jqGrid('navGrid','#compPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");

	
	  $('#addCompLink').click(function() {
		var ajaxResponse = __doAjaxRequest(complaintUrl+'?form', 'POST', {}, false,'html');
		$('.content').html(ajaxResponse);
	 });
	  
		function statusFormatter(cellvalue, options, rowdata) {	
			if(rowdata.deptStatus == 'A'){
				return "<a title='Department is Active' alt='Department is Active' value='A' class='fa fa-check-circle fa-2x green' href='#'></a>";	
			}else{
				return "<a title='Department is Inactive' alt='Department is Inactive' value='A' class='fa fa-times-circle fa-2x red' href='#'></a>";
			}	
	}
	 
	
	});	 



function addLink(cellvalue, options, rowObject) 
{   
	return  "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"showComp('"+rowObject.deptCompId+"','V')\"><i class='fa fa-building-o'></i></a> " +
 		   " <a class='btn btn-warning btn-sm' title='Edit' onclick=\"showComp('"+rowObject.deptCompId+"','E')\"><i class='fa fa-pencil'></i></a> " +  
 		  " <a class='btn btn-danger btn-sm' title='Delete' onclick=\"deleteComp('"+rowObject.deptCompId+"')\"><i class='fa fa-trash'></i></a>";
}


function deleteComp(deptCompId){
   
	 var yes = getLocalMessage('eip.commons.yes');
	 var warnMsg="Are you sure to delete ";
	 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
	' onclick="onDelete(\''+deptCompId+'\')"/>'+	
	'</div>';
	
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;
}

function onDelete(deptCompId){
	 var requestData = 'deptCompId='+deptCompId;
	var response = __doAjaxRequest(complaintUrl+'?checkActiveComplaints', 'POST', requestData, false,'json');
	if(response){
		$(childDivName).html("Active complaints are associate with Department.");
		showModalBox(childDivName);
	}else{
		 var isInactive = __doAjaxRequest(complaintUrl+'?inactiveComplaintDepartment', 'POST', requestData, false,'json');
		 if(isInactive){
				$("#compGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
				closeFancyOnLinkClick(childDivName);
		     }else{
		    	    $(childDivName).html("Internal errors");
		    		showModalBox(childDivName);
		     }
	 }
}

function showComp(compId,type){
	var divName	=	formDivName;
    var requestData = 'compId='+compId+'&type='+type;
	var ajaxResponse	=	doAjaxLoading(complaintUrl+'?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);
	if($('#hiddeValue').val() == 'V'){
		 $("#complaintForm :input").prop("disabled", true);
		 $("#backBtn").removeProp("disabled");
		 $('.addCF').bind('click', false);
		 $('.remCF').bind('click', false);
	}
	if($('#hiddeValue').val() == 'E'){
		 $("#resetComp").prop("disabled", true);
		 $('#deptId').prop("disabled", true);
	}
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
	$("#postMethodForm").prop('action',complaintUrl);
	$("#postMethodForm").submit();
}