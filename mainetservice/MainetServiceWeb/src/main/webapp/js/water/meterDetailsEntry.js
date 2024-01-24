$(function() {
	$("#workOrderGrid").jqGrid(
			{
				url : "MeterDetailsEntry.html?getGridData",datatype : "json",mtype : "POST",colNames : [ getLocalMessage("water.meterDet.appliNo"), getLocalMessage("water.meterDet.servName"), getLocalMessage("water.meterDet.appliName"), getLocalMessage("view.edit")],
				colModel : [ {name : "applicationNumber",width : 30,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }}, 
				             {name : "serviceName",width : 30,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }},
				             {name : "applicantName",width : 15,sortable : false,searchoptions: { "sopt": [ "eq"] }}, 
				             {name : 'applicationNumber',index : 'applicationNumber',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
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
				caption : getLocalMessage("water.meterDet.applicantDet.grid")
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});


function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='editClass' value='"+rowdata.applicationNumber+"'><img src='css/images/edit.png' width='20px' alt='Edit Charge Master' title='Edit Scrutiny Data' /></a>";
}

$(function() {		
	$(document).on('click', '.editClass', function() {
		var $link = $(this);
		var rowId = $link.closest('tr').find('td:eq(0)').text();
		var url = "MeterDetailsEntry.html?edit";
		var requestData = "rowId=" + rowId;
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		prepareDateTag();
			
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


function saveMeterDetailsEntry(obj){
	return saveOrUpdateForm(obj, 'Saved Successfully', 'MeterDetailsEntry.html', 'saveform');
	/*var url = "MeterDetailsEntry.html?saveform";
	var data = $("#frmMeterDetailsEntry").serialize();
	
	var returnData =__doAjaxRequest(url,'post',data,false,'',obj);
	if ($.isPlainObject(returnData))
	{
	var message = returnData.command.message;
	if(message)
		{
			showSaveResultBox(returnData, message, 'MeterDetailsEntry.html');
		}else{
			showSaveResultBox(returnData, 'Please Try again..', 'MeterDetailsEntry.html');
		}
	}
	$("#widget").html(returnData);
	$("#widget").show();
	$(".warning-div").removeClass("hide");
	return false;*/
}