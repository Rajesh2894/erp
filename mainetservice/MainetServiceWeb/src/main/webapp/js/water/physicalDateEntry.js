$(function() {
	$("#workOrderGrid").jqGrid(
			{
				url : "PhysicalDateEntry.html?getGridData",datatype : "json",mtype : "GET",colNames : [ getLocalMessage("water.meterDet.appliNo"), getLocalMessage("water.meterDet.servName"), getLocalMessage("water.meterDet.appliName"), "ServiceShortCode",getLocalMessage("view.edit")],
				colModel : [ {name : "applicationNumber",index:'applicationNumber',width : 30,sortable : true,search : true}, 
				             {name : "serviceName",width : 30,sortable : false,search : false },
				             {name : "applicantName",width : 20,sortable : false,search : false}, 
				             {name : "serviceShortCode",width : 20,sortable : false,search : false,hidden :true},
				             {name : 'applicationNumber',index : 'applicationNumber',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false,sortable : false}
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "applicationNumber",
				sortorder : "desc",
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : false,
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,
				},
				autoencode : true,
				caption : getLocalMessage("water.physConn.det")
			});
	 jQuery("#workOrderGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,refresh:false}); 
	 $("#pagered_left").css("width", "");
});

var appId;
var serviceCode;
function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='editClass' value='"+rowdata.applicationNumber+"'><img src='css/images/edit.png' width='20px' alt='Edit Charge Master' title='Edit Scrutiny Data' /></a>";
}

$(function() {
	$(document).on('click', '.editClass', function() {
		var $link = $(this);
		var divName = '.content-page';
		var rowId = $link.closest('tr').find('td:eq(0)').text();
		var serviceShortCode = $link.closest('tr').find('td:eq(3)').text();
		serviceCode=serviceShortCode;
		appId=rowId;
		if(serviceShortCode != '' && serviceShortCode == 'WNC'){
			var url = "PhysicalDateEntry.html?edit";
			var requestData = "rowId=" + rowId;
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			
			$('.content').html(returnData);
			
			prepareDateTag();
		}else if(serviceShortCode != '' && serviceShortCode == 'WCC'){
			var url = "ExecuteDisConnectionProcess.html?showDetails";
			var requestData =  {
					"actualTaskId" : 0,
					"appNo" : rowId
					};
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			prepareTags();
		}else if(serviceShortCode != '' && serviceShortCode == 'WRC'){
			var url = "waterExecuteReconnectionForm.html?showDetails";
			var requestData =  {
					"actualTaskId" : 0,
					"appNo" : rowId
					};
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			prepareTags();
		}
		
			
	});
		
		
});


function flagCheck(){
	
	 var checked = $('#physicalConnFlag').is(':checked');
	if(checked){
	 $('#connDate').attr('disabled',false);
	}else{
		$('#connDate').val('');
		$('#connDate').attr('disabled',true);
	}
}


function savePhysicalConnectionDateEntry(obj) {
	var status;
	var errorList;
	status = saveOrUpdateForm(obj, '', 'PhysicalDateEntry.html', 'saveform');
	if (serviceCode != '' && serviceCode == 'WNC') {
	var	connDate=$('#connDate').val();
		if (connDate==null || connDate==''||connDate==undefined) {
		displayErrorsOnPage(errorList);
		} else {
			waterCertificate(status);	
		}
	}
	/*
	 * var url = "PhysicalDateEntry.html?saveform"; var data =
	 * $("#frmPhysicalDateEntry").serialize();
	 * 
	 * var returnData =__doAjaxRequest(url,'post',data,false,'',obj); if
	 * ($.isPlainObject(returnData)) { var message = returnData.command.message;
	 * if(message) { showSaveResultBox(returnData, message,
	 * 'PhysicalDateEntry.html'); }else{ showSaveResultBox(returnData, 'Please
	 * Try again..', 'PhysicalDateEntry.html'); } }
	 * $("#widget").html(returnData); $("#widget").show();
	 * $(".warning-div").removeClass("hide"); return false;
	 */
}

function preparePhysicalDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}
function waterCertificate(status){

    if (!status) {
    	var requestData =  {
				"appNo" : appId
				};
	var URL = 'NewWaterConnectionForm.html?printWaterConCompltCert';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);

	var title = 'Agency Registration Acknowlegement';
	var printWindow = window.open('', '_blank');

	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
		.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
		.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
		.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
		.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
		.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
		.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
		.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
    }

}
