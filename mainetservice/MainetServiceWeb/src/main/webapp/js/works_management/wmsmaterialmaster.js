var removeIdArray=[];
var errorList = [];	

$(document).ready(function() {
	
	triggerDatatable();

	$(".add").hide();
	/*$('#materialMasterTab').DataTable();*/
	$('#errorTableRateType').dataTable({
		"pageLength" : 5
	});
	if ($("#errorLengh").val() == 0) {
		$("#errorTable").hide();
	} else {
		$("#materialMasterAdd").hide();
		$("#errorTable").show();
	}
	
	$(function() {
		$('.custDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true
		});
	});
	
	$('.decimal').on('input', function() {
	    this.value = this.value
	      .replace(/[^\d.]/g, '')             // numbers and decimals only
	      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
	      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
	      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
	  });
	
	$("#MaterialMasterGrid").jqGrid(
			{url : 'WmsMaterialMaster.html'+ "?MaterialMaster",
			datatype : "json",
			mtype : "POST",
			colNames : ['',getLocalMessage('sor.name'),getLocalMessage('material.master.startdate'),getLocalMessage('material.master.enddate'),getLocalMessage('estate.grid.column.action')],
				colModel : [ {name : "sorId",width : 5,hidden : true},
					{name : "sorName",width : 30,sortable : true}, 
					{name : "sorFromDate",width : 30,sortable : true,formatter : dateTemplate},
					{name : "sorToDate",width : 30,sortable : true,formatter : dateTemplate},
					{name : 'enbll',index : 'enbll',width : 30,align : 'center !important',formatter : actionFormatter,search : false} ],
				pager : "#MaterialPager",rowNum : 30,rowList : [ 5, 10, 20, 50,100 ],sortname : "code",sortorder : "desc",height : 'auto',viewrecords : true,
				gridview : true,
				loadonce : true,
				jsonReader : {root : "rows",page : "page",total : "total",records : "records",repeatitems : false,},
				autoencode : true,
				caption : ''
			});
	$("#MaterialMasterGrid").jqGrid('navGrid','#MaterialMasterPager', {edit : false,add : false,del : false,search : true,refresh : true,closeAfterSearch : true});
	$("#MaterialMasterPager_left").css("width", "");

	function actionFormatter(cellvalue, options, rowObject) {
		if(rowObject.sorToDate != "" && rowObject.sorToDate != null ){
		return "<a class='btn btn-blue-2 btn-sm' title='View' onclick=\"showGridOption('"
		+ rowObject.sorId
		+ "','V')\"><i class='fa fa-eye' aria-hidden='true'></i></a> ";
		}
		else{
			return "<a class='btn btn-blue-2 btn-sm' title='View' onclick=\"showGridOption('"
			+ rowObject.sorId
			+ "','V')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "
			+ "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"showGridOption('"
			+ rowObject.sorId
			+ "','E')\"><i class='fa fa-pencil-square-o' aria-hidden='true'></i></a> "
			+ "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"showGridOption('"
			+ rowObject.sorId
			+ "','D')\"><i class='fa fa-trash' aria-hidden='true'></i></a> ";
		}
}
	
	// custom dynamic table
	$("#materialMasterTab").on(
			"click", '.addButton',
			function(e) {
				if ( $.fn.DataTable.isDataTable('#materialMasterTab') ) {
					  $('#materialMasterTab').DataTable().destroy();
					}
				var errorList = [];	
				$("#errorDiv").hide();
				
				if(!validateMaterialMasterList()){
					triggerDatatable();
					return false;
				}
				e.preventDefault();
					
					var content = $('#materialMasterTab tr').last().clone();
					content.id;
					$('#materialMasterTab tr').last().after(content);
					content.attr("id");
					content.find("input:text").val('');
					content.find("select:eq(1)").val('');
					content.find("input:hidden").val('');
					
					$(this).find("select:eq(0)").attr("id", "maTypeId"+ i)
				 	content.find("input:text:eq(0)").attr("id","maItemNo" + (row + 1));
					content.find("input:hidden:eq(0)").attr("id","maId" + (row + 1));
				 	content.find("input:text:eq(1)").attr("id","maDescription" + (row + 1));
				 	content.find("select:eq(1)").attr("id","maItemUnit" + (row + 1));
				 	content.find("input:text:eq(2)").attr("id","maRate" + (row + 1));
				 	content.attr("id" ,"filterId" +(row + 1));
				 	
				 	content.find('.delButton').attr("id", "delButton"+ (row+1));
				 	
				 	content.find("input:text:eq(0)").attr("name","materialMasterListDto[" + (row + 1)+ "].maItemNo");
				 	content.find("input:hidden:eq(0)").attr("name","materialMasterListDto[" + (row + 1)+ "].maId");
				 	content.find("input:text:eq(1)").attr("name","materialMasterListDto[" + (row + 1)+ "].maDescription");
				 	content.find("select:eq(0)").attr("name","materialMasterListDto[" + (row + 1)+ "].maTypeId");
				 	content.find("select:eq(1)").attr("name","materialMasterListDto[" + (row + 1)+ "].maItemUnit");
				 	content.find("input:text:eq(2)").attr("name","materialMasterListDto[" + (row + 1)+ "].maRate");
				 	$(this).data('id');
				 	$('.decimal').on('input', function() {
					    this.value = this.value
					      .replace(/[^\d.]/g, '')             // numbers and decimals only
					      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
					      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
					      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
					  });
					
					reOrderTableIdSequence();
					$("#maTypeId"+(row+1)).val($("#maTypeId"+(row)).val());
			});

	$("#materialMasterTab").on("click", '.delButton', function(e) {
		
		if ( $.fn.DataTable.isDataTable('#materialMasterTab') ) {
			  $('#materialMasterTab').DataTable().destroy();
			}
		var countRows = -1;
		$('.appendableClass').each(function(i) {
			if ( $(this).closest('tr').is(':visible') ) {
					countRows = countRows + 1;			
			}
		});
		row = countRows;
		if (row != 0) {
			$(this).parent().parent().remove();
			row--;
			var deletedSodId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
			 if(deletedSodId != ''){
				 removeIdArray.push(deletedSodId);
			 }
		 $('#removeChildIds').val(removeIdArray);
			reOrderTableIdSequence();
		}
		e.preventDefault();
	});	


	function reOrderTableIdSequence() {
		
		$('.appendableClass').each(function(i) {
			$(this).find("input:text:eq(0)").attr("id", "maItemNo"+ i);
			$(this).find("input:hidden:eq(0)").attr("id", "maId"+ i);
			$(this).find("input:text:eq(1)").attr("id", "maDescription"+ i);
			$(this).find("select:eq(0)").attr("id", "maTypeId"+ i);
			$(this).find("select:eq(1)").attr("id", "maItemUnit"+ i);
			$(this).find("input:text:eq(2)").attr("id", "maRate"+ i);
			$(this).attr("id" ,"filterId" +i);

			$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
			$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
			
			$(this).find("input:text:eq(0)").attr("name", "materialMasterListDto[" + i + "].maItemNo").attr("onchange","checkForDuplicateIteamNo(this,"+i+")");
			$(this).find("input:hidden:eq(0)").attr("name", "materialMasterListDto[" + i + "].maId");
			$(this).find("input:text:eq(1)").attr("name", "materialMasterListDto[" + i + "].maDescription");
			$(this).find("select:eq(0)").attr("name", "materialMasterListDto[" + i + "].maTypeId").attr("onchange","resetIteamNo(this,"+i+")");
			$(this).find("select:eq(1)").attr("name", "materialMasterListDto[" + i + "].maItemUnit");
			$(this).find("input:text:eq(2)").attr("name", "materialMasterListDto[" + i + "].maRate");
		});
		triggerDatatable();
	}
	//end dynamic table
});

function triggerDatatable(){
	$("#materialMasterTab").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
	    "iDisplayLength" : 10, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "scrollCollapse": true,
	    "bSort" : false
	   }).fnPageChange( 'last' );	 	 
}

function resetIteamNo(obj,index){
	 $("#maItemNo"+index).val("");	
}

function checkForDuplicateIteamNo(event, currentRow){
	
	 $(".error-div").hide();
		 var errorList = [];
		 if ( $.fn.DataTable.isDataTable('#materialMasterTab') ) {
			  $('#materialMasterTab').DataTable().destroy();
			}
	
	    		$('.appendableClass').each(function(i) {
	    			//var sordCategory = $('#sordCategory'+i+' :selected').attr('code');
	    			var typeId = $('#maTypeId'+i).val();
	    			var itemNo = $("#maItemNo"+i).val();
	    			
	    			if(typeId == undefined || typeId == '') {
	    				errorList.push(getLocalMessage("sor.select.category"));
	    			}
	    			if(itemNo == "" ||  itemNo == null){
	    				errorList.push(getLocalMessage("sor.enter.itemno"));
	    			}
	    			 if(errorList.length == 0) {
	    					if(currentRow != i && ($("#maTypeId"+currentRow).val() == typeId && event.value ==itemNo))
		  	    			{	
		  	    				errorList.push(getLocalMessage('generalRate.duplicate.itemNo.validation'));
		  	    				$("#maItemNo"+currentRow).val("");	
		  	    				$(".error-div").show();
		  	    				showErr(errorList);
		  	    				return false;
		  	    			} 
	    		      }else{
	    		    	  $("#maItemNo"+i).val('');
	    		    	  $(".error-div").show();
	    		    	  showErr(errorList);
	    					 return false;  
	    		      }
	    	});
	    		triggerDatatable();
}

function openAddMaterialMaster(formUrl, actionParam) {
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function getsorNameByType() {
	var requestData = {
		"sorId" : $('.sorType').val()
	};
	var result = __doAjaxRequest("WmsMaterialMaster.html?getSorNames", 'post',
			requestData, false, 'json');

	$('#sorName').html('');
	$('#sorName').append($("<option></option>").attr("value", "0").text(getLocalMessage('selectdropdown')));
	$.each(result, function(index, value) {
		$('#sorName').append($('<option></option>').attr("value", value[0]).text((value[1])));
		$('#sorName').trigger("chosen:updated");
	});

};

function getDateBySorName(){
	if($("#sorName").val()==0){
		return false;
	}
	var requestData = {
			"sorName" : $('#sorName').val()
		};
		var result = __doAjaxRequest("WmsMaterialMaster.html?getDateBySorName", 'post',
				requestData, false, 'json');
		var dates=result.split(",");
		$('#sorFromDate').val(dates[0]);
		if(dates[1]!='null')
		$('#sorToDate').val(dates[1]);
		else{
			$('#sorToDate').val("");
		}
}

function searchMaterialMaster() {
	var errorList = [];
	if($("#sorName").val()==0 || $("#sorName").val()==null){
		errorList.push(getLocalMessage("material.master.vldn.sorname"));
		showErr(errorList);
		$("#errorDiv").show();
		return false;
	}
	var requestData = '&sorName=' + $("#sorName").val()
			+ '&sorNameStr=' +$("#sorName option:selected").text();
	var ajaxResponse = doAjaxLoading(
			'WmsMaterialMaster.html?getMaterialMasterGridData', requestData,
			'html');
	if (ajaxResponse == '"N"') {
		errorList.push(getLocalMessage("work.management.vldn.grid.nodatafound"));
		showErr(errorList);
		$("#errorDiv").show();
		$(".add").show();
	} else {
		$("#errorDiv").hide();
		$(".add").hide();
	}
	$("#MaterialMasterGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');
}

function showGridOption(sorId, action) {
	var actionData;
	var divName = formDivName;
	var requestData = '&sorId=' + sorId + '&mode=' + action;
	if (action == "E" || action == "V" || action=="U") {
		actionData = 'editMaterialMasterData';
		var ajaxResponse = doAjaxLoading('WmsMaterialMaster.html?' + actionData,
				requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	if (action == "D") {
		actionData = 'deleteMaterialMaster';
		showConfirmBoxForDelete(sorId, actionData);
	}
}

function showConfirmBoxForDelete(sorId, actionData) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+getLocalMessage('material.master.vldn.delete')+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + sorId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(sorId) {
	$.fancybox.close();
	var requestData = 'sorId=' + sorId;
	var ajaxResponse = doAjaxLoading('WmsMaterialMaster.html?'
			+ 'deleteMaterialMaster', requestData, 'html');
	$("#MaterialMasterGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');
}

function backMaterialMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WmsMaterialMaster.html');
	$("#postMethodForm").submit();
}

function saveMaterialMasterList(element){
	if ($.fn.DataTable.isDataTable('#materialMasterTab')) {
		$('#materialMasterTab').DataTable().destroy();
	}
	if(!validateMaterialMasterList()){
		$('#materialMasterTab').DataTable()
		return false;
	}
	return saveOrUpdateForm(element,getLocalMessage('material.master.saveList'), 'WmsMaterialMaster.html', 'saveform');
	
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+getLocalMessage('material.master.saveList')+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="backMaterialMasterForm()"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function validateMaterialMasterList(){
	$('.appendableClass').each(function(i) {
		row = i;
		var maTypeId = $.trim($("#maTypeId"+i).val());
		var maItemNo = $.trim($("#maItemNo"+i).val());
		var maDescription = $.trim($("#maDescription"+i).val());
		var maItemUnit = $.trim($("#maItemUnit"+i).val());
		var maRate = $.trim($("#maRate"+i).val());	
		if(maTypeId=="" || maTypeId==0){
			errorList.push(getLocalMessage("material.master.vldn.maTypeId"));
		}
		if(maItemNo==""){
			errorList.push(getLocalMessage("material.master.vldn.itemcode"));
		}
		if(maDescription == ""){
			errorList.push(getLocalMessage("material.master.vldn.materialname"));	
		}
		if(maItemUnit == "" || maItemUnit==0){
			errorList.push(getLocalMessage("material.master.vldn.unit"));	
		}
		if(maRate == ""){
			errorList.push(getLocalMessage("material.master.vldn.rate"));	
		}
	}); 
	 if (errorList.length > 0) {
			$("#errorDiv").show();
			showErr(errorList);
			errorList = [];
			return false;
		}
	 return true;
}

function getFilterCode() {
	if ($.fn.DataTable.isDataTable('#materialMasterTab')) {
		$('#materialMasterTab').DataTable().destroy();
	}
	var selectedType = $("#filterType").val();
	if (selectedType == "0") {
		$('.appendableClass').each(function(i) {
			$("#filterId" + i).show();
		});
		$('#materialMasterTab').DataTable();
		return false;
	}
	$('.appendableClass').each(function(i) {
		if ($("#maTypeId" + (i)).val() == selectedType) {
			$("#filterId" + i).show();
		} else {
			$("#filterId" + i).hide();
		}
	});
	$('#materialMasterTab').DataTable();

}

function uploadExcelFile(){
	
	var errorLis=[];
	var fileName=$("#excelFilePath").val().replace(/C:\\fakepath\\/i, '');
	if(fileName==null || fileName==""){
		errorLis.push(getLocalMessage("excel.upload.vldn.error"));
		showErr(errorLis);
		return false;
	}
	$("#filePath").val(fileName);
	var requestData = $.param($('#wmsMaterialMaster').serializeArray())
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WmsMaterialMaster.html?' + "loadExcelData",
			requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	if($("#errorLengh").val()==0){
		$("#materialMasterAdd").Show();
		$("#errorTable").hide();
	}else{
		$("#materialMasterAdd").hide();	
		$("#errorTable").show();	
	}
}

function exportExcelData(){
	window.location.href='WmsMaterialMaster.html?exportRateExcelData';
}

function formatDate(date) {
	var parts = date.split("-");
	var formattedDate=parts[2]+"/"+parts[1]+"/"+parts[0];
	return formattedDate;
}

