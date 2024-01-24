var SORURL = "ScheduleOfRate.html";
var removeIdArray=[];
$(document).ready(function(){
	
	$('#scheduleOfRate').validate({
		onkeyup: function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout: function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}});
	
	var mode = $("#modeType").val();
	
	if(mode == 'V' || mode == 'E'|| mode == 'A'){
		 $('[id^="sordCategory"]').chosen().trigger("chosen:updated"); 
	}
	 $('[id^="sordCategory"]').data('rule-required',true);
	 $('[id^="sorDIteamNo"]').data('rule-required',true);
	 $('[id^="sorDDescription"]').data('rule-required',true);
	 $('[id^="sorIteamUnit"]').data('rule-required',true);
	 $('[id^="sorBasicRate"]').data('rule-required',true);
	 
	$("#datatables").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true
	    });	 
	
	if(mode == 'V' || mode == 'E'){
		triggerDatatable();
	}
	
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});
	
	
	$('.datepickerEndDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2200"
	});
	
  	var dateFields = $('.datepicker');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	
  	var datepickerEndDate = $('.datepickerEndDate');
  	datepickerEndDate.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	
	
$("#searchSOR").click(function() {
	var errorList=[];
	
		var sorNameId = $('#sorCpdId').val();
		var sorFromDate = $('#sorFromDate').val();
		var sorToDate = $('#sorToDate').val();
		var uadStatus = $('#uadStatus').val();
		var isDefaultStatus = $('#isDefaultStatus').val();
		if( sorNameId!= '0' ||sorFromDate != '' || sorToDate != ''){
			var requestData = 'sorNameId='+sorNameId +'&sorFromDate='+sorFromDate+'&sorToDate='+sorToDate;
			var table = $('#datatables').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest(SORURL+'?filterSORListData', 'POST',requestData, false,'json');
			var result = [];
			$.each(ajaxResponse, function(index){
				
				var obj = ajaxResponse[index];
				if(uadStatus == 'YES' && isDefaultStatus != 'Y'){
					if(obj.sorActive == 'Y'){
						result.push([obj.sorName,obj.fromDate,obj.toDate,'<td class="text-center">'+
							  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="viewSOR(\''+obj.sorId+'\')" title="View Schedule"><i class="fa fa-eye"></i></button>'+
							  '</td>']);
					}else if(obj.sorActive == 'N'){
						result.push([obj.sorName,obj.fromDate,obj.toDate,'<td >'+
							  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:110px;"  onClick="viewSOR(\''+obj.sorId+'\')" title="View Schedule"><i class="fa fa-eye"></i></button>'+
							  '</td>']);
					}
				}else{
					if(obj.sorActive == 'Y'){
						result.push([obj.sorName,obj.fromDate,obj.toDate,'<td class="text-center">'+
							  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="viewSOR(\''+obj.sorId+'\')" title="View Schedule"><i class="fa fa-eye"></i></button>'+
							  '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="editSOR(\''+obj.sorId+'\')"  title="Edit Schedule"><i class="fa fa-pencil-square-o"></i></button>'+
							  '</td>']);
					}else if(obj.sorActive == 'N'){
						result.push([obj.sorName,obj.fromDate,obj.toDate,'<td >'+
							  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:110px;"  onClick="viewSOR(\''+obj.sorId+'\')" title="View Schedule"><i class="fa fa-eye"></i></button>'+
							  '</td>']);
					}
   				}
				});
			table.rows.add(result);
			table.draw();
		}else{
			errorList.push(getLocalMessage('tender.search.validation'));
			displayErrorsOnPage(errorList);
		}
   });
	
$("#createSOR").click(function(){
    	var requestData ={"type":"C"}
		var ajaxResponse = __doAjaxRequest(SORURL+'?form', 'POST', requestData, false,'html');
		$('.pagediv').html(ajaxResponse);	
	});

$("#importSOR").click(function(){
	var requestData ={"type":"U"}
	var ajaxResponse = __doAjaxRequest(SORURL+'?form', 'POST', requestData, false,'html');
	$('.pagediv').html(ajaxResponse);	
	$(".tableDiv").hide();
});

$("#sorFromDate").click(function() {
	$(".alert").hide();
	});



});


function editSOR(sorId){
	var requestData = 'sorId='+sorId+'&type=E';
	var response = __doAjaxRequest(SORURL+'?form', 'POST',requestData, false,'html');
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(response);
	var table = $('#sortbl').DataTable();
	table.rows().remove().draw();
}

function viewSOR(sorId){
	var requestData = 'sorId='+sorId+'&type=V';
	var response = __doAjaxRequest(SORURL+'?form', 'POST',requestData, false,'html');
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(response);
}

function saveSOR(obj){
	var errorList=[];
	var mode = $("#modeType").val();
	if(mode == 'V' || mode == 'E'){
		if ( $.fn.DataTable.isDataTable('#sortbl') ) {
			  $('#sortbl').DataTable().destroy();
			}
	}
	errorList  = validateSORMas(errorList);
	if(errorList.length == 0){
	return saveOrUpdateForm(obj,"", SORURL, 'saveform');
	}else{
		if(mode == 'V' || mode == 'E'){
			triggerDatatable();
		}
		displayErrorsOnPage(errorList);
	}
}

function deleteSOR(sorId){
	 var yes = getLocalMessage('eip.commons.yes');
	 var no = getLocalMessage('eip.commons.no');
	 var warnMsg=getLocalMessage('sor.are.you.sure.inactive'); 
	 var message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
	' onclick="onDelete(\''+sorId+'\')"/>'+	
	'<input class="btn btn-danger" style="margin-right:10px" type=\'button\' value=\''+no+'\'  id=\'yes\' '+ 
	' onclick="CloseIt()"/>'+	
	'</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBoxWithoutClose(childDivName);
	return false;
}

function onDelete(sorId){
	 var requestData = 'sorId='+sorId;
	 var response = __doAjaxRequest(SORURL+'?inactiveSORMaster', 'POST', requestData, false,'json');
	 if(response){
	  showConfirmBox();
    }else{
	    $(childDivName).html("Internal errors");
		showModalBox(childDivName);
	  }
}


function validateSORMas(errorList){
	errorList  = validateSORMasDetails(errorList);
	errorList  = validateSORDetails(errorList);
	return errorList;
}
function validateSORMasDetails(errorList){
	var sorName = $("#sorCpdId").val();
	var sorFromDate = $("#sorFromDate").val();
	var sorToDate = $("#sorToDate").val();
	if(sorName == 0 || sorName == null  ){
		errorList.push(getLocalMessage('sor.select.sorname'));
	}
	if(sorFromDate == "" || sorFromDate == undefined){
		errorList.push(getLocalMessage('sor.select.fromdate'));
	}
	if(sorToDate != "" && sorToDate != undefined && sorFromDate != "" || sorFromDate != undefined){
		 var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		 var eDate = new Date(sorFromDate.replace(pattern,'$3-$2-$1'));
		 var sDate = new Date(sorToDate.replace(pattern,'$3-$2-$1'));
		 if (eDate > sDate) {
			  errorList.push(getLocalMessage("sor.fromdate.enddate.validation"));
		   }
	}
	return errorList;
}

function validateSORDetails(errorList){
	$(".sorClass").each(function(i){
		var sordCategory = $("#sordCategory"+i).val();
		var itemNo = $("#sorDIteamNo"+i).val();
		var descri = $("#sorDDescription"+i).val();
		var unit = $("#sorIteamUnit"+i).val();
		var basicRate = $("#sorBasicRate"+i).val();
		var leadUpto = $("#leadUpto"+i).val();
		var leadUnit = $("#leadUnit"+i).val();
		
		var c = i+1;
	if(sordCategory == '') {
		errorList.push(getLocalMessage("sor.select.category")+" - " +c);
	}
	if(itemNo == "" ||  itemNo == null){
		errorList.push(getLocalMessage("sor.enter.itemno")+" - " +c);
	}
	if(descri == "" ||  descri == null){
		errorList.push(getLocalMessage("sor.enter.sordesc")+" - " +c);
	}/*else if(descri.length>4000){
		errorList.push(getLocalMessage("sor.desc.length")+" - " +c);
	}*/
	if(unit == "" ||  unit == null){
		errorList.push(getLocalMessage("sor.select.sorunit")+" - " +c);
	}
	if(basicRate == "" ||  basicRate == null){
		errorList.push(getLocalMessage("sor.enter.basicrate")+" - " +c);
	}
	if(leadUpto != ""){
		if(leadUnit == "" ||  leadUnit == null){
			errorList.push(getLocalMessage("sor.select.leadunit")+" - " +c);
		}
	}
	
	});
	return errorList;
}

$("#sortbl").on("click", ".addSOR", function(i){
	var errorList= [];
	
	var mode = $("#modeType").val();
	if(mode == 'E'){
		if ( $.fn.DataTable.isDataTable('#sortbl') ) {
			  $('#sortbl').DataTable().destroy();
			}
	}
    errorList = validateSORDetails(errorList);
	if (errorList.length  == 0 ) {
		$(".warning-div").hide();
		var clickedRow = $('#sortbl tr').length-2;	
		  var category= $('#sordCategory'+clickedRow).val();
		  var unit= $('#sorIteamUnit'+clickedRow).val();
		  var content = $('#sortbl tr').last().clone();
		$('#sortbl tr').last().after(content);
		content.find("input:text").val('');
		content.find("textarea").val('');
		content.find("input:hidden").val('');
		content.find("select").val('');
		content.find('div.chosen-container').remove();
		content.find("select:eq(0)").chosen().trigger("chosen:updated"); 
		reOrderSORTableSequence();
		$('#sordCategory'+(clickedRow+1)).val(category).trigger("chosen:updated");
		$('#sorIteamUnit'+(clickedRow+1)).val(unit);
	}else{
		if(mode == 'E'){
			triggerDatatable();
		}
		displayErrorsOnPage(errorList);
	}
});

$("#sortbl").on("click", '.deleteSOR', function(e) {
	var errorList = [];
	if($("#modeType").val() == 'E'){
		if ( $.fn.DataTable.isDataTable('#sortbl') ) {
			  $('#sortbl').DataTable().destroy();
			}
	}
	
	var rowCount = $('#sortbl tr').length;
	if (rowCount <= 2) {
    errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	}
if(errorList.length > 0){	
	if($("#modeType").val() == 'E'){
		triggerDatatable();
	}
	 displayErrorsOnPage(errorList);
    return false;
}else{
	$(this).parent().parent().remove();
	var deletedSodId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
	 if(deletedSodId != ''){
		 removeIdArray.push(deletedSodId);
	 }
		 
 $('#removeChildIds').val(removeIdArray);
	reOrderSORTableSequence();
}
});

function reOrderSORTableSequence(){
	var catMode= $("#subCatMode").val();
$(".sorClass").each(function(i){
	
if(catMode !='Y'){
	
	$(this).find($('[id^="sordCategory"]')).attr('id',"sordCategory"+i+"_chosen");
	$(this).find("input:hidden:eq(0)").attr("id", "sordId" + (i)).attr("name", "mstDto.detDto["+(i)+"].sordId");  
	$(this).find("input:hidden:eq(1)").attr("id", "schActiveFlag" + (i)).val("A");  
	$(this).find("select:eq(0)").attr("id", "sordCategory"+(i)).attr("name", "mstDto.detDto["+(i)+"].sordCategory").attr("onchange","resetIteamNo(this,"+i+")");
	$(this).find("input:text:eq(0)").attr("id", "sordCategory"+(i));
	$(this).find("input:text:eq(1)").attr("id", "sordSubCategory" + (i)).attr("name", "mstDto.detDto["+(i)+"].sordSubCategory");
	$(this).find("input:text:eq(2)").attr("id", "sorDIteamNo" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorDIteamNo").attr("onchange","checkForDuplicateIteamNo(this,"+i+")");   
	$(this).find("textarea:eq(0)").attr("id", "sorDDescription" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorDDescription");
	$(this).find("select:eq(1)").attr("id", "sorIteamUnit"+(i)).attr("name", "mstDto.detDto["+(i)+"].sorIteamUnit");
	$(this).find("input:text:eq(3)").attr("id", "sorBasicRate" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorBasicRate");
	$(this).find("input:text:eq(4)").attr("id", "sorLabourRate" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorLabourRate");
	$(this).find("input:text:eq(5)").attr("id", "leadUpto" + (i)).attr("name", "mstDto.detDto["+(i)+"].leadUpto");
	$(this).find("select:eq(2)").attr("id", "leadUnit"+(i)).attr("name", "mstDto.detDto["+(i)+"].leadUnit");
	$(this).find("input:text:eq(6)").attr("id", "liftUpto" + (i)).attr("name", "mstDto.detDto["+(i)+"].liftUpto");   
	
	$(this).find("input:hidden:eq(1)").attr("name", "mstDto.detDto["+(i)+"].schActiveFlag");
	
	}else{
		$(this).find($('[id^="sordCategory"]')).attr('id',"sordCategory"+i+"_chosen");
		$(this).find("input:hidden:eq(0)").attr("id", "sordId" + (i)).attr("name", "mstDto.detDto["+(i)+"].sordId");  
		$(this).find("input:hidden:eq(1)").attr("id", "schActiveFlag" + (i)).val("A");  
		$(this).find("select:eq(0)").attr("id", "sordCategory"+(i)).attr("name", "mstDto.detDto["+(i)+"].sordCategory").attr("onchange","resetIteamNo(this,"+i+")");
		$(this).find("input:text:eq(0)").attr("id", "sordCategory"+(i));
		//$(this).find("input:text:eq(1)").attr("id", "sordSubCategory" + (i)).attr("name", "mstDto.detDto["+(i)+"].sordSubCategory");
		$(this).find("input:text:eq(1)").attr("id", "sorDIteamNo" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorDIteamNo").attr("onchange","checkForDuplicateIteamNo(this,"+i+")");   
		$(this).find("textarea:eq(0)").attr("id", "sorDDescription" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorDDescription");
		$(this).find("select:eq(1)").attr("id", "sorIteamUnit"+(i)).attr("name", "mstDto.detDto["+(i)+"].sorIteamUnit");
		$(this).find("input:text:eq(2)").attr("id", "sorBasicRate" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorBasicRate");
		//$(this).find("input:text:eq(3)").attr("id", "sorLabourRate" + (i)).attr("name", "mstDto.detDto["+(i)+"].sorLabourRate");
		$(this).find("input:text:eq(3)").attr("id", "leadUpto" + (i)).attr("name", "mstDto.detDto["+(i)+"].leadUpto");
		$(this).find("select:eq(2)").attr("id", "leadUnit"+(i)).attr("name", "mstDto.detDto["+(i)+"].leadUnit");
		$(this).find("input:text:eq(4)").attr("id", "liftUpto" + (i)).attr("name", "mstDto.detDto["+(i)+"].liftUpto");   
		
		$(this).find("input:hidden:eq(1)").attr("name", "mstDto.detDto["+(i)+"].schActiveFlag");
	}
	  

	});
if($("#modeType").val() == 'E'){
	triggerDatatable();
}

}

function resetSOR(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',SORURL);
	$("#postMethodForm").submit();
}

function closeErrBox (){
	$(".warning-div").hide();
}
function CloseIt(){
	$.fancybox.close();
}
function displayErrorsOnPage(errorList){
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';			 
	$(".warning-div").html(errMsg);					
	$(".warning-div").removeClass('hide')
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	$(".warning-div").show();
	errorList = [];	
	return false;
}

function BackSOR(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',SORURL);
	$("#postMethodForm").submit();
}

function inputPreventSpace(key,obj){
	if(key == 32 && obj.value.charAt(0)==' '){
    	 $(obj).val('');
    }
}


function triggerDatatable(){
	$("#sortbl").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
	    "iDisplayLength" : 10, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "scrollCollapse": true,
	    "bSort" : false
	   }).fnPageChange( 'last' );	 	 
}

function showConfirmBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+ getLocalMessage("sor.inactive.sucess")+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="BackSOR()"/>'+
	'</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}


function checkForDuplicateIteamNo(event, currentRow){
	 $(".error-div").hide();
		 var errorList = [];
		 if($("#modeType").val() == 'E'){
			 if ( $.fn.DataTable.isDataTable('#sortbl') ) {
				  $('#sortbl').DataTable().destroy();
				}			}
	
	    		$('.sorClass').each(function(i) {
	    			//var sordCategory = $('#sordCategory'+i+' :selected').attr('code');
	    			var sordCategory = $('#sordCategory'+i).val();
	    			var itemNo = $("#sorDIteamNo"+i).val();
	    			var c = i+1;
	    			if(sordCategory == undefined || sordCategory == '') {
	    				errorList.push(getLocalMessage("sor.select.category")+" - " +c);
	    			}
	    			if(itemNo == "" ||  itemNo == null){
	    				errorList.push(getLocalMessage("sor.enter.itemno")+" - " +c);
	    			}
	    			 if(errorList.length == 0) {
	    					if(currentRow != i && ($("#sordCategory"+currentRow).val() == sordCategory && event.value ==itemNo))
		  	    			{	
		  	    				errorList.push(getLocalMessage('sor.duplicate.itemNo.validation'));
		  	    				$("#sorDIteamNo"+currentRow).val("");		
		  	    				displayErrorsOnPage(errorList);
		  	    				return false;
		  	    			} 
	    		      }else{
	    		    	  $("#sorDIteamNo"+i).val('');
	    		    	  displayErrorsOnPage(errorList);
	    					 return false;  
	    		      }
	    	});
	   if($("#modeType").val() == 'E'){
	    triggerDatatable();
	   }
}
$('.save').on('click', function() {
    var $this = $(this);
    $this.button('loading');
    setTimeout(function() {
    	uploadExcelFile();
       $this.button('reset');
   }, 80);
});

function uploadExcelFile(){
	
	var errorList=[];
	errorList  = validateSORMasDetails(errorList);
	var fileName=$("#excelFileName").val().replace(/C:\\fakepath\\/i, '');
	if(fileName==null || fileName==""){
		errorList.push(getLocalMessage("excel.upload.vldn.error"));	
	}	
	var sorCpdId = $("#sorCpdId").val();
	var sorFromDate = $("#sorFromDate").val();
	var reqData = {"sorCpdId":sorCpdId,"sorFromDate":sorFromDate};
	var invalidaDate=__doAjaxRequest(SORURL+'?validateSorStartDate','post',reqData,false,'');
	if(invalidaDate){
		errorList.push(getLocalMessage("sor.date.validation"));
	}
	if(errorList.length == 0){
		$("#filePath").val(fileName);
		var requestData = $.param($('#scheduleOfRate').serializeArray())
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(SORURL+ "?loadExcelData",requestData, 'html');
		 var status=$(ajaxResponse).find('#successFlag'); 
		if(status.val() == "Y"){
			showUploadConfirmBox();	
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}else if(status.val() == "E"){
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
			$(".tableDiv").hide();
			$("#errorTable").show();
		}
	}else{
		displayErrorsOnPage(errorList);
	}
}

function resetIteamNo(obj,index){
	 $("#sorDIteamNo"+index).val("");	
}

function exportSORExcelData(){
	window.location.href=SORURL+"?exportExcelData";
}

function printSORData() {
	if ( $.fn.DataTable.isDataTable('#sortbl') ) {
		  $('#sortbl').DataTable().destroy();
		}
	var theForm = "#scheduleOfRate";
    var requestData = __serializeForm(theForm);
	var ajaxResponse = __doAjaxRequest(SORURL+"?printSORReport", 'POST', requestData, false, 'html');
	var divContents = ajaxResponse; 
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title></title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>') 
	printWindow.document.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
	triggerDatatable();
}

function editSorByChapter(){
	if($("#editChapterId").val()!="" && $("#editChapterId").val()!="0"){
	 var requestData = 'sorId='+$("#sorId").val()+'&type=E'+'&chapterId='+$("#editChapterId").val();
		var response = __doAjaxRequest(SORURL+'?sorFormChapterWise', 'POST',requestData, false,'html');
		$('.pagediv').removeClass('ajaxloader');
		$('.pagediv').html(response);
	}
}


function showUploadConfirmBox(){
	
	var errMsgDiv = '.msg-dialog-box';
	var message ='' ;
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+getLocalMessage('sor.create.success')+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForUplod()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceedForUplod(){
			
	$(".tableDiv").show();
	$("#errorTable").hide();
	$.fancybox.close();
}
