/**
 * ritesh.patil
 * 
 */
tenantUrl='TenantMaster.html';
$(document).ready(function(){
	
	$("#tenantGrid").jqGrid(
			{   
				url : tenantUrl+"?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('estate.label.code'),getLocalMessage('estate.label.name'), getLocalMessage('estate.label.Location'), getLocalMessage('estate.label.Category'),getLocalMessage('estate.label.Type'), getLocalMessage('estate.grid.column.view'),getLocalMessage('estate.grid.column.action')],
				colModel : [ {name : "id",width : 5,  hidden :  true},
				             {name : "code",width : 30,  sortable :  true},
				             {name : "typeName",width : 20,search :true}, 
				             {name : "fName",width : 20,search :false},
				             {name : "lName",width : 20,search :false}, 
				             {name : "mobileNo",width : 20,search :false}, 
				             { name: 'enbl', index: 'enbl', width: 30, align: 'center !important',formatter:addLinkView,search :false},
				             { name: 'enbll', index: 'enbll', width: 30, align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#tenantPager",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "code",
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
				caption : getLocalMessage('master.estate.form.name')
			});
	       $("#tenantGrid").jqGrid('navGrid','#tenantPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");

	
	      
	 $('#addTenantLink').click(function() {
		var ajaxResponse = __doAjaxRequest(tenantUrl+'?form', 'POST', {}, false,'html');
		$('.content-page').html(ajaxResponse);
	 });
	 
});	 



function addLinkView(cellvalue, options, rowObject) 
{
	return "<a class='btn btn-blue-3 btn-sm' title='View Properties' onclick=\"showTenant('"+rowObject.id+"','V')\"><i class='fa fa-building-o'></i></a>";
}


function addLink(cellvalue, options, rowObject) 
{
   return "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"showTenant('"+rowObject.id+"','E')\"><i class='fa fa-pencil'></i></a> " +  
	       "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"deleteTenant('"+rowObject.id+"','"+rowObject.code+"')\"><i class='fa fa-trash'></i></a>";
}


function deleteTenant(tenantId,code){
   
	 var yes = getLocalMessage('eip.commons.yes');
	 var warnMsg="Are you sure you want to delete " + code ;
	 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
	' onclick="onDelete(\''+tenantId+'\')"/>'+	
	'</div>';
	
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;
}

function onDelete(id){
	 var requestData = 'tenantId='+id;
	 var response = __doAjaxRequest(tenantUrl+'?deleteTenant', 'POST', requestData, false,'json');
	 if(response){
			$("#tenantGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
			closeFancyOnLinkClick(childDivName);
	     }else{
	    	    $(childDivName).html("Internal errors");
	    		showModalBox(childDivName);
	     }
}

function showTenant(tntId,type){
	var divName	=	formDivName;
    var requestData = 'tntId='+tntId+'&type='+type;
	var ajaxResponse	=	doAjaxLoading(tenantUrl+'?form', requestData, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
	
	if($('#hiddeValue').val() == 'V'){
		 $("#tenantForm :input").prop("disabled", true);
		 $("#backBtn").removeProp("disabled");
		 $('.addCF').bind('click', false);
		 $('.remCF').bind('click', false);
	}
	if($('#hiddeValue').val() == 'E'){
		 $("#resetTenant").prop("disabled", true);
	}
}

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',tenantUrl);
	$("#postMethodForm").submit();
}

