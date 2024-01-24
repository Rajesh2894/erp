/**
 * ritesh.patil
 * 
 */
propFreezeUrl='PropFreeze.html';
/*$(document).ready(function(){
	

	$("#propertyGrid").jqGrid(
			{   url : 'PropFreeze.html'+ "?getGridData",
				//url : propFreezeUrl+"?getGridData",
				datatype : "json",
				mtype : "POST",
$("#propertyGrid").jqGrid(
		{url : 'PropFreeze.html'+ "?getGridData",
			datatype : "json",
			
			mtype : "POST",
			colNames : ['','Location','Estate Name','Property Name','From Date','To Date','Shift','Reason','Unfreeze'],
			//colNames : [ '','Location','Estate Name','Property Name','From Date','To Date','Shift','Reason','Unfreeze'],
			colModel : [ {name : "id",width : 5,  hidden :  true   },
				             {name : "location",width : 30,align : 'center' },
				             {name : "estate",width : 30, align : 'center' ,search :false}, 
				             {name : "property",width : 30,align : 'center',search :false},
				             {name : "fromDate",width : 20,align : 'center',search :false}, 
				             {name : 'toDate',width : 20,align : 'center',search :false},
				             {name : 'shift',align : 'center',width : 20,search :false}, 
				             {name : 'purpose',align : 'center',width : 40,search :false}, 
				             { name: 'enbll', index: 'enbll', width: 20, align: 'center !important',formatter:addLink,search :false}
				            ],
			colModel : [ {name : "id",width : 5,hidden : true},
				{name : "location",width : 30,sortable : true}, 
				{name : "estate",width : 30,sortable : true,align : 'center' ,search :false},
				{name : "property",width : 30,sortable : true,align : 'center' ,search :false},
				{name : "fromDate",width : 30,sortable : true,formatter : dateTemplate}, 
				{name : "toDate",width : 30,sortable : true,formatter : dateTemplate},
				{name : "shift",width : 30,sortable : true,align : 'center',search :false},
				{name : "purpose",width : 30,sortable : true,align : 'center',search :false},
				{name : 'enbll',index : 'enbll',width : 30,align : 'center !important',formatter:addLink,search : false} ],
				pager : "#propertyPager",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "shift",
				sortorder : "desc",
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : true,
				jsonReader : {root : "rows",page : "page",total : "total",records : "records",repeatitems : false,},
				autoencode : true,
				caption : ''
			});

	       $("#propertyGrid").jqGrid('navGrid','#propertyFreezePager',{edit : false,add : false,del : false,search : true,refresh : true,closeAfterSearch : true}); 
	       $("#propertyFreezePager_left").css("width", "");
	     
	      
	 $('#addPropForm').click(function() {
		var ajaxResponse = __doAjaxRequest(propFreezeUrl+'?form', 'POST', {}, false,'html');
		$('.content-page').html(ajaxResponse);
	 });
		 
});	 




function addLink(cellvalue, options, rowObject) 
{
   return "<a class='btn btn-danger btn-sm' data-toggle='tooltip' data-placement='top' onclick=\"unFreezeProperty('"+rowObject.id+"')\"><i class='fa fa-trash-o'></i><span class='hide'>Delete</span</a>";
}*/


$(document).ready(function(){
	$('#addPropForm').click(function() {
		var ajaxResponse = __doAjaxRequest(propFreezeUrl+'?form', 'POST', {}, false,'html');
		$('.content-page').html(ajaxResponse);
	 });
		
	$("#propFreezeDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
			[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
	});
	
});

function unFreezeProperty(id){
	var errMsgDiv = '.msg-dialog-box';
     var yes = getLocalMessage('eip.commons.yes');
	 var warnMsg=getLocalMessage("rl.common.delettion.msg ") ;
	 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
	' onclick="onDelete(\''+id+'\')"/>'+	
	'</div>';
    $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
    $('#yes').focus();
    $(errMsgDiv).html(message);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
	return false;
}

function onDelete(id){
	
	$.fancybox.close();
	 var requestData = 'id='+id;
	 var response = __doAjaxRequest('PropFreeze.html?unFreezeProp', 'POST', requestData, false,'json');
     if(response){
		  //$("#propertyGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
    	 $("#postMethodForm").prop('action', '');
    	 $("#postMethodForm").prop('action', propFreezeUrl);
    	 $("#postMethodForm").submit();	 	 
        }else{
    	    // $(childDivName).html("Internal errors");
    	     showErrormsgboxTitle(getLocalMessage("Internal errors"));
    		// showModalBox(errMsgDiv);
        }
  }

	
function back() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', propFreezeUrl);
	$("#postMethodForm").submit();
}
function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

