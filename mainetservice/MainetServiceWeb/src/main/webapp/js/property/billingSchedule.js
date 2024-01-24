billURL= "BillingSchedule.html";

$(document).ready(function(){
    $("#sheduleGrid").jqGrid({
        url: billURL+"?getGridData",
        datatype: "json",
        mtype: "POST",
        colNames: [getLocalMessage('bill.finYear'),getLocalMessage('bill.frequency'),getLocalMessage('bill.action')],
        colModel: [
            { name: "financialYear", width: 100, align:'center',sortable: true, search:true},
            { name: "billingFreq", width: 100, align:'center',sortable: true, search:true},
            { name: 'id', width: 80,index: '', align: 'center', sortable: false,search : false,formatter:viewSchedule}
        ],
        pager: "#pagered",
        rowNum: 30,
        rowList: [5, 10, 20, 30],
        sortname: "financialYear",
        sortorder: "asc",
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
        caption: "Billing Schedule List"
    }); 
    jQuery("#sheduleGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:true}); 
	$("#pagered_left").css("width", "");
	
	$('#createSchedule').click(function() {
		var ajaxResponse = __doAjaxRequest(billURL+'?form', 'POST', {}, false,'html');
		$('.pagediv').html(ajaxResponse);
	 });
});

function viewSchedule(cellValue, options, rowdata, action){
    return    "<a class='btn btn-blue-3 btn-sm' title='View Schedule' onclick=\"showShedule('"+rowdata.id+"','V')\"><i class='fa fa-eye'></i></a> " +
	       " <a class='btn btn-warning btn-sm' title='Edit Schedule' onclick=\"showShedule('"+rowdata.id+"','E')\"><i class='fa fa-pencil'></i></a> "+
	       "<a class='btn btn-danger btn-sm' title='Delete Schedule' onclick=\"deleteShedule('"+rowdata.id+"')\"><i class='fa fa-trash' aria-hidden='true'></i></a> ";

}

function showShedule(id,type){
	var requestData= 'id='+id +'&type='+type;
	var ajaxResponse     =  doAjaxLoading(billURL+'?form',requestData,'html');
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(ajaxResponse);
	//$("select").chosen().trigger("chosen:updated")
}

function deleteShedule(id){
	showDelConfirmBox(id);
}

function showDelConfirmBox(id){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	if(id != 0){
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">Are you sure want to delete?</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="deleteData('+id+')"/>'+
		'</div>';		
	}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function deleteData(id){
	postdata='id='+id;
	 __doAjaxRequest( billURL+'?deleteSchedule','POST', postdata, false, 'json');
	$.fancybox.close();
	$("#sheduleGrid").jqGrid('setGridParam', {datatype : 'json'}).trigger('reloadGrid');
	
}

function saveData(obj){
	var errorList=[];
	var financialYear = $("#financialYear").val();
	var billFrequency = $("#asBillFrequency").val();

	if(financialYear == null || financialYear == undefined){
		errorList.push(getLocalMessage('billschedule.select.finYear'));
	}
	if(billFrequency == "0" || billFrequency == null || billFrequency == undefined ){
		errorList.push(getLocalMessage('billschedule.select.frequency'));
	}
	
	  $('.dueDateDetail').each(function(i) {
			var level=i+1;
		 	var calculateFrom = $.trim($("#calculateFrom"+i).val());
		 	var noOfDay =$.trim($("#noOfDay"+i).val());
	  	    if(calculateFrom==0 || calculateFrom==null) {
		    	errorList.push(getLocalMessage('billscheduleselect.calFrom')+" "+level);
	  	    }
		    if(noOfDay==0 || noOfDay==""){
		    	errorList.push(getLocalMessage('billschedule.enter.noOfDay')+" "+level);
		    }
	  });
	if(errorList.length == 0){
		return saveOrUpdateForm(obj,"", billURL, 'saveform');
	}else{
		displayErrorsOnPage(errorList);
	}

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
	errorList = [];	
	return false;
}

function closeErrBox() {
	$('.warning-div').addClass('hide');
}

function proceed() {
	window.location.href=billURL;
 }

function back(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',billURL);
	$("#postMethodForm").submit();
}

function resetBillSchedule(obj)
{
	$('.error-div').remove();
    $(':input',obj)
    .not(':button, :submit, :reset, :hidden')
    .val('')
    .removeAttr('checked')
    $("#SchedulewiseDueDate").html("");
	return false; 
}


function setToMonthData(obj) {
	var schFreq = $("#asBillFrequency").val();
	var finYear = $("#financialYear").val()
	if(schFreq!=0){
		var data = {"schFreq" : schFreq};	
		var URL = 'BillingSchedule.html?createSchedule';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#reloadDiv").html(returnData);
		if(finYear!=null){
			$("select[id=financialYear]").val(finYear);
		}
	}else{
		$("#SchedulewiseDueDate").html("");
	}
	
/*	var billFreq = $("#asBillFrequency").find('option:selected').attr('code')-1;
	var fromMnth =$("#asFrequencyFrom").find('option:selected').attr('code');
	var monthVal = parseInt(fromMnth) + parseInt(billFreq);
	var id;
	if(monthVal > 12) {
		monthVal = monthVal - 12;
		monthVal='0'+monthVal;
		 id=$("#tempToId option[code=" + monthVal + "]").val();
		
		//$("#tempToId").find("option[code=" + monthVal + "]").attr("selected", true);
		$("#cnsToDateId").val(id);
		$("#tempToId").val(id);
	} else {
		monthVal=parseInt(fromMnth) + parseInt(billFreq);
		if(monthVal <10){
			monthVal='0'+monthVal;	
		}
		 id=$("#tempToId option[code=" + monthVal + "]").val();
		//$("#tempToId").find("option[code=" + monthVal + "]").attr("selected", true);
		$("#cnsToDateId").val(id);
		$("#tempToId").val(id);
	}*/
}


