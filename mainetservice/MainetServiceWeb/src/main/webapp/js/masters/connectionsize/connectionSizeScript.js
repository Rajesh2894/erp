$(function() {
	$("#childGrid").jqGrid(
			{
				url : "TBCcnsizePrm.html?getGridData",datatype : "json",mtype : "GET",colNames : [ 'Conn Id',getLocalMessage('water.connsize.frmDt'), getLocalMessage('water.connsize.toDt'), getLocalMessage('water.connsize.frm'),getLocalMessage('water.connsize.to'), getLocalMessage('edit.msg'), getLocalMessage('master.view')],
				colModel : [ {name : "cnsId",width : 10,sortable :  false,searchoptions: { "sopt": [ "eq"] }},
				             {name : "cnsFrmdt",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] },formatter : dateTemplate}, 
				             {name : "cnsTodt",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] },formatter : dateTemplate}, 
				             {name : "cnsFrom",width : 20,sortable : false,searchoptions: { "sopt": [ "eq"] }}, 
				             {name : "cnsTo",width : 20,sortable :  false,searchoptions: { "sopt": [ "eq"] }}, 
				             {name : 'cnsId',index : 'cnsId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false	}, 
				             {name : 'cnsId',index : 'cnsId',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
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
				caption : getLocalMessage('water.connsize.gridTtl')
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});



function returnEditUrl(cellValue, options, rowdata, action) {
    return "<a href='#'  return false; class='editClass' value='"+rowdata.cnsId+"' ><img src='css/images/edit.png' width='20px' alt='Edit Charge Master' title='Edit Scrutiny Data' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewConnectionClass' value='"+rowdata.cnsId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function returnisdeletedUrl(cellValue, options, rowdata, action) {

	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowdata.isdeleted+"'  alt='Designation is Active' title='Designation is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowdata.isdeleted+"' alt='Designation is  INActive' title='Designation is InActive'></a>";
	}

}


$(function() {
	$(document)
			.on('click','.addConnectionClass',function() {
						var $link = $(this);
						var cnsId = $link.closest('tr').find('td:eq(0)').text();
						var url = "TBCcnsizePrm.html?formForUpdate";
						var requestData = "cnsId=" + cnsId + "&MODE1=" + "EDIT";
						var returnData =__doAjaxRequest(url,'post',requestData,false);

						$('.content').html(returnData);
						
						prepareDateTag();
					});
	});

$(function() {		
	$(document).on('click', '.editClass', function() {
		var $link = $(this);
		var cnsId = $link.closest('tr').find('td:eq(0)').text();
		var url = "TBCcnsizePrm.html?formForUpdate";
		var requestData = "cnsId=" + cnsId + "&MODE1=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		prepareDateTag();
			
	});
		
});

$(function() {		
	$(document).on('click', '.viewConnectionClass', function() {
		var $link = $(this);
		var cnsId = $link.closest('tr').find('td:eq(0)').text();
		var url = "TBCcnsizePrm.html?formForUpdate";
		var requestData = "cnsId=" + cnsId + "&MODE1=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		prepareDateTag();
			
	});
		
});


/*ADD Form*/


function saveConnectionSizeDetails(obj){
	return saveOrUpdateForm(obj, 'Saved Successfully', 'TBCcnsizePrm.html', 'create');
}

function updateConnectionSizeDetails(obj){
	return saveOrUpdateForm(obj, 'Saved Successfully', 'TBCcnsizePrm.html', 'update');
}


function showConfirmBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	
	message	+='<p>Record Saved Successfully..</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="ShowView()"/>'+
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function ShowView(){
	window.location.href='TBCcnsizePrm.html';
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