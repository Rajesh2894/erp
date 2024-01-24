$(document).ready(function() {
	
	$("#conDate").datepicker({
	    changeMonth: true,
        changeYear: true,
        startDate: '01/01/1900',
        dateFormat: 'dd/mm/yy',
		showMonthBeforeYear : true,
		maxDate:0,
		onClose: function(){$(this).toggleClass('ui-state-focus');}			
	});
	
	$("#datatables").dataTable({
			"oLanguage": { "sSearch": "" } ,
			"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
		    "iDisplayLength" : 5, 
		    "bInfo" : false,
		    "lengthChange": true
	});	   
	
	$("#searchButtonId").click(function() {
		
		/*var data = {}
		data["contractNo"] = $('#contractNo').val();
		data["contDate"] = $('#conDate').val();*/
		var contractNo =$('#contractNo').val();
		var contDate = $('#conDate').val();
		if($('#contractNo').val() != '' || $('#conDate').val() != ''){
			var requestData = 'contractNo='+ contractNo+ '&contDate='+ contDate;
			
			var table = $('#datatables').DataTable();
			table.rows().remove().draw();
			/*var ajaxResponse = __doAjaxRequestJson('EstateContractMapping.html?filterListData', 'POST',JSON.stringify(data), false,'json');*/
			
			var ajaxResponse = __doAjaxRequest(
					'EstateContractMapping.html?filterListData','POST', requestData, false,'json');
			var result = [];
			$.each(ajaxResponse, function(index){
				var lookUp = ajaxResponse[index];
				result.push([lookUp.contractNo,lookUp.contDate,lookUp.deptName,lookUp.representedBy,lookUp.vendorName,lookUp.fromDate,lookUp.toDate,"<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm margin-left-30'  onClick=showContractMapping(\"" + lookUp.contId +"\")><strong class='fa fa-eye'></strong><span class='hide'>View</span></a>","<a href='javascript:void(0);' class='btn btn-darkblue-3 margin-left-30' onClick=printContractEstate(\"" +lookUp.contId+"\")><i class='fa fa-print'></i></a>"]);
	         });
			table.rows.add(result);
			table.draw();
		}else{
			var errorList=[];
			
			errorList.push(getLocalMessage("Please.Select.or.enter.any.one.field.to.search.records"));
			showRLValidation(errorList);
		}
   });
	
	
	$('#addMappingId').click(function() {
		var response = __doAjaxRequest('EstateContractMapping.html?contractCount', 'POST', null, false,'json');
		if(response){
			var ajaxResponse = __doAjaxRequest('EstateContractMapping.html?form', 'POST', null, false,'html');
			$('.content-page').html(ajaxResponse);
		}else{
			var errorList=[];
			errorList.push("Contract is not available for Mapping");
			showRLValidation(errorList);
		}
		
	 });
  

	
});



function __doAjaxRequestJson(url, reqType, data, async, dataType) {
	
	showloader(true);
	var result = '';
	//var token='';
	
	/*$.ajax({
		url : "Autherization.html?getRandomKey",
		type : "POST",
		async : false,
		success : function(response) {
			token = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});*/
	
	$.ajax({
		url : url,
		data : data,
		type : reqType,
		async : async,
		dataType : dataType,
		//headers   : {"SecurityToken": token},
		contentType: "application/json; charset=utf-8",
		success : function(response) {
			result = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	showloader(false);
	return result;
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

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action','EstateContractMapping.html');
	$("#postMethodForm").submit();
}

function showContractMapping(contId){
	var ajaxResponse = __doAjaxRequest('EstateContractMapping.html?form', 'POST', {'contId':contId}, false,'html');
	$('.content-page').html(ajaxResponse);
	
}

function printContractEstate(contId){
	var ajaxResponse = __doAjaxRequest('EstateContractMapping.html?printContract', 'POST', {'contId':contId}, false,'html');
	$('#ViewContract').html(ajaxResponse);
	$('#ViewContract').show();
	showModalBox('#ViewContract');
	return false;
	
}

function printContent(el){
	//D#74770
	$.fancybox.close();
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}