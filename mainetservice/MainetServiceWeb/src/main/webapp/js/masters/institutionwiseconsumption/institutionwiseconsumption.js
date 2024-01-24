var dsgid = '';
$(function() {
	$("#grid").jqGrid(
			{
				url : "WaterInstiWiseConsuption.html?getGridData",datatype : "json",mtype : "post",
				colNames : [ getLocalMessage('master.frmDt'), getLocalMessage('master.toDt'), "",getLocalMessage('master.instcsmp.instType'),getLocalMessage('master.instcsmp.ltrperhead'),getLocalMessage('master.dsg.active/inactive'), getLocalMessage('edit.msg'), getLocalMessage('master.view')],
				colModel : [ {name : "instFrmdt",width : 40,sortable : true,searchoptions: { "sopt": ["bw", "eq"] },formatter : dateTemplate},
				             {name : "instTodt",width : 40,sortable : true, searchoptions: { "sopt": ["bw", "eq"] },formatter : dateTemplate},
				             {name : "instId",width : 10,sortable : false, hidden: true},
				             {name : "instType",width : 85,sortable : false,searchoptions: { "sopt": [ "eq"] }},
				             {name : "instLitPerDay",width : 25,sortable : false,searchoptions: { "sopt": [ "eq"] }},
				             {name : "instFlag",width : 25,sortable : false,formatter : returnisdeletedUrl,search:false},
				             {name : 'instId',index : 'instId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				             {name : 'instId',index : 'instId',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "dsgid",
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
				caption : "Institute Wise Consuption Masters List"
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});



function returnEditUrl(cellValue, options, rowdata, action) {
	instId = rowdata.instId;
	return "<a href='#'  return false; class='editConsuptionClass'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewConsuptionClass' value='"+rowdata.instId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function returnisdeletedUrl(cellValue, options, rowdata, action) {

	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowdata.isdeleted+"'  alt='Designation is Active' title='Designation is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowdata.isdeleted+"' alt='Designation is  INActive' title='Designation is InActive'></a>";
	}

}

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}




$(function() {		
	$(document).on('click', '.viewConsuptionClass', function() {
		var $link = $(this);
		var instId = $link.closest('tr').find('td:eq(2)').text();
		var url = "WaterInstiWiseConsuption.html?formForUpdate";
		var requestData = "instId=" + instId + "&MODE1=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		$('.content').html(returnData);
		
		prepareDateTag();
			
	});
		
});

$(function() {
	$(document).on('click','.addConnectionClass',function() {
		var $link = $(this);
		var instId = $link.closest('tr').find('td:eq(0)').text();
		var url = "WaterInstiWiseConsuption.html?formForUpdate";
		var requestData = "instId=" + instId + "&MODE1=" + "CREATE";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		$('.content').html(returnData);
		
		prepareDateTag();
		return false;
	});
});



$(function() {		
	$(document).on('click', '.editConsuptionClass', function() {
		var $link = $(this);
		var instId = $link.closest('tr').find('td:eq(2)').text();
		
		var url = "WaterInstiWiseConsuption.html?formForUpdate";
		var requestData = "instId=" + instId + "&MODE1=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		$('.content').html(returnData);
		
		prepareDateTag();
			
	});
	
});



/*Add form*/

function setOrgName(obj){
	$("#dpDeptdesc").val($("#tbOrganisation_orgid").find('option:selected').text());
}


$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	changeYear: true
});


$(".warning-div ul").each(function () {
    var lines = $(this).html().split("<br>");
    $(this).html('<li>' + lines.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;") + '</li>');						
});
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	
	
function SaveInstituteWiseConsumptionDetails(obj){
	return saveOrUpdateForm(obj, 'Saved Successfully', 'WaterInstiWiseConsuption.html', 'create');
}

function updateInstituteWiseConsumptionDetails(obj){
	return saveOrUpdateForm(obj, 'Saved Successfully', 'WaterInstiWiseConsuption.html', 'update');
}