
$(function () {
    $("#grid").jqGrid({
        url: "AccountDepositSlip.html?getGridData",
        datatype: "json",
        mtype: "POST",
        colNames: ["",getLocalMessage('accounts.slip.no'),getLocalMessage('accounts.slip.date'),getLocalMessage('bill.amount'),getLocalMessage('accounts.Secondaryhead.BankAccounts'),getLocalMessage('accounts.receipt.payment_mode'),getLocalMessage('bill.action')],
        colModel: [
            { name: "depositeSlipId", sortable: true,width : 30,hidden:true},
            { name: "depositeSlipNo", sortable: true ,width : 10},
            { name: "depositeSlipDate",  sortable: true,width : 20,align:'center',},
            { name: "amount", sortable: false ,width : 20,align:'right',},
            { name: "baAccountName",  sortable: true,width : 40,},
            { name: "depositeType", sortable: false ,align:'center',width : 25,},
            { name: 'depositeSlipId', index: 'depositeSlipId', width:15 , align: 'center !important',formatter:addLink,search :false}
            //{name : 'depositeSlipId',index : 'depositeSlipId',width : 15,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
            
        ],
        pager: "#pagered",
        emptyrecords: getLocalMessage("jggrid.empty.records.text"),
        rowNum: 30,
        rowList: [5, 10, 20, 30],
        sortname: "cpmId",
        sortorder: "desc",
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
        caption: getLocalMessage('accounts.slip.list')
    }); 
});

function addLink(cellvalue, options, rowdata) 
{
	
	if(rowdata.depositeType =='Cheque'){
		 return "<a class='btn btn-blue-3 btn-sm viewDepositeSlipDetails' title='View' value='"+rowdata.depositeSlipId+"' depositeSlipId='"+rowdata.depositeSlipId+"' ><i class='fa fa-building-o'></i></a> "+
         "<a class='btn btn-warning btn-sm printDepositeSlipDetails' title='Print' value='"+rowdata.depositeSlipId+"' depositeSlipId='"+rowdata.depositeSlipId+"' ><i class='fa fa-print'></i></a> "+
         '<button type="button" class="btn btn-primary btn-sm "  onclick="downloadFile(\''
			+ rowdata.depositeSlipId
			+ '\',\'AccountDepositSlip.html?exportExcelTemplateData\',)"  title="Download Certificate"><i class="fa fa-download"></i></button>';
	
	}
	else{
		

		   return "<a class='btn btn-blue-3 btn-sm viewDepositeSlipDetails' title='View' value='"+rowdata.depositeSlipId+"' depositeSlipId='"+rowdata.depositeSlipId+"' ><i class='fa fa-building-o'></i></a> "+
		         "<a class='btn btn-warning btn-sm printDepositeSlipDetails' title='Print' value='"+rowdata.depositeSlipId+"' depositeSlipId='"+rowdata.depositeSlipId+"' ><i class='fa fa-print'></i></a> ";
}}

/*function returnViewUrl(cellValue, options, rowdata, action) {
	
	return "<a href='#'  return false; class='viewDepositeSlipDetails' value='"+rowdata.depositeSlipId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}*/





function searchSavedReceiptDetails(elements){
	
	var arr =   validateshowFormData();
	if(arr[3]){
	var url = "AccountDepositSlip.html?searchSavedReceiptDetails";
	var requestData = "fromDate=" + $('#fromDate').val() + "&toDate=" + $('#toDate').val() + "&feeMode=" + $("#depositType").val()+ "&account=" + $("#account").val()+ "&slipNumber=" + $("#slipNumber").val();
	var returnData =__doAjaxRequest(url,'post',requestData,false,'json');
	if(returnData==0)
		{
		var errMsg=getLocalMessage('no.record.found');
		$("#errorId").html(errMsg);					
		$("#errorDiv").show();
		}
	else
		{
		$("#errorDiv").hide();
		}
	reloadGrid('grid');
	}
	/* $('.widget').html(returnData);*/
	prepareDateTag(); 
	
}





$( document ).ready(function() {
	
	$("#fieldDeptDiv").hide();
	
	 $(".datepicker").datepicker({
		    dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,	
			maxDate: '0',
		});
	 //$(".datepicker").datepicker('setDate', new Date()); 
	  
	 $('#chequeOrDDDetails').hide();
	
	
	
});
$(function() {		
	$(document).on('click', '.cashOrCheque', function() {
		if($("#cash").is(':checked')){
			$('#clearingDate').show();
		}else{
			$('#clearingDate').hide();
		}
	});
	
	$("#selectall").click(function () {
	    $('.case').attr('checked', this.checked);
	});

	$(".case").click(function(){
		var alertms=getLocalMessage('tp.app.alMessg');
		
		if($(".case").length == 0)	
		{	
			alert(alertms);
		}
		else
			{
			
			if($(".case").length == $(".case:checked").length) {
	          $("#selectall").attr("checked", "checked");
	      } else {
	          $("#selectall").removeAttr("checked");
	      }
			}
		});

	});



$("#payOtherdID").on("click", '.delButton', function(e) {
	
	var rowCount = $('#chargeMasterTab tbody').length;
	if(rowCount<=1){
		return false;
	}
	
	 $(this).closest('#chargeMasterTab tbody').remove();

	 reOrderTableIdSequence();
	 e.preventDefault();
});	




function getReceiptDetails(obj,mode){
	
	$('#receiptDetailId').show();
	if(mode!='true')
		{
	var errorList = [];
	$("#SfromDate").val($("#fromDate").val());
	$("#StoDate").val($("#toDate").val())
	$('#SfeeMode').attr('disabled', false).val("");
	$("#hiddenfundId").val($("#fundCode").val())
	$("#hiddenfieldId").val($("#fieldCode2").val())
	$("#SfeeMode").val($("#deptId option:selected").attr('code'));
	var fundCode =   $('#fundCode').val();
	var fieldCode =  $('#fieldCode2').val();
	var deptIdcode=  $('#deptId2').val();
	var SfromDate =  $('#SfromDate').val();
	var StoDate =  $('#StoDate').val();

	
	if(fundCode == "" )
	{
	errorList.push("Please Select fund ");
	
	}
	if( fieldCode == ""  )
	{
	errorList.push("Please Select  filed  ");
	
	}
	
	if( deptIdcode == "")
	{
	errorList.push("Please Select  department ");
	
	}
	if(SfromDate == "" )
	{
	errorList.push("Please Select fromDate");
	}
	if(StoDate == "" )
	{
	errorList.push("Please Select toDate");
	}
	if(depositDate=="")
		{
		errorList.push("Please Select Deposit Slip Date");
		}
	/*if(fundCode == ""  && fieldCode == "" && deptIdcode =="")
		{
		errorList.push("Please Select fund , filed and department ");
		
		}*/
	if(errorList.length > 0){
		
		 var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});
	
		errMsg += '</ul>';			 
		$("#errorId").html(errMsg);					
		$("#errorDiv").removeClass('hide')
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		
		errorList = [];		
		
		return false;
	}
		
	var url = "AccountDepositSlip.html?getReceiptDetails";
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	requestData = __serializeForm(theForm);
	var returnData =__doAjaxRequest(url,'post',requestData,false);
		
	$('#receiptDetailId').html(returnData);
		}
	else
		{
		  var totalAmount=0;
	      $('.receiptTr').each(function(i) {
	    	  var amount=parseFloat($("#receiptAmount"+i).val())
	    	  totalAmount=totalAmount+amount;
	    	  $("#receiptTotalAmount").val(totalAmount);
	    	  
	      });
	    
		}
	prepareDateTag();
	
}




$(function() {		
	$(document).on('click', '.viewDepositeSlipDetails', function() {
		var $link = $(this);
		var depositeSlipId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountDepositSlip.html?displayDepositeSlipDetails";
		var requestData = "depositeSlipId=" + depositeSlipId + "&MODE=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		$('.widget').html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr('disabled', true);
		$('input[type=checkbox]').attr('disabled', true);
		$('select').prop('disabled', true).trigger("chosen:updated");
		prepareDateTag();
			
	});
		
});

$(function() {		
	$(document).on('click', '.printDepositeSlipDetails', function() {
		var $link = $(this);
		var depositeSlipId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountDepositSlip.html?printReportDepositeSlipDetails";
		var requestData = "depositeSlipId=" + depositeSlipId + "&MODE=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		$('.widget').html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr('disabled', true);
		$('input[type=checkbox]').attr('disabled', true);
		$('select').prop('disabled', true).trigger("chosen:updated");
		prepareDateTag();
			
	});
		
});














function getDraweeBankDetails(obj){
	var url = "AccountDepositSlip.html?draweeBankDetails";
	$("#SfeeMode").val($("#deptId option:selected").attr('code'));
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	requestData = $('#frmMaster,#frmMaster2,#frmMaster3').serialize();
	
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	
	$('#chequeOrDDDetails1').show();
	$('#chequeOrDDDetails2').html(returnData);
	/*prepareDateTag();*/
}



function searchCheckDetails(obj){
	
	// $('.case2').attr('checked', this.checked);
	var url = "AccountDepositSlip.html?chequeOrDDDetails";
	$("#SfeeMode").val($("#deptId option:selected").attr('code'));
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	requestData = $('#frmMaster,#frmMaster2,#frmMaster3').serialize();
	
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	var obj=$(returnData).find('#isEmpty'); 
     if(obj.val()=='Y')
	    	{
    	    return false;
	    	}
     else
    	 {
	$('#chequeOrDDDetails1').show();
	$('#chequeOrDDDetails2').html(returnData);
	prepareDateTag();
    	 }
     return true;
	
	
}

function searchDepositDetails(obj,mode){
	
	var toDate = $('#toDate').val();
	
	$('.error-div').hide();
	var errorList = [];
	
	var depositSlipType = $('#depositSlipType').val();
	$('#saveDeptSlipType').val(depositSlipType);
	
	
	var searchType = $('#searchTypeId').val();
	
	if(searchType == 'ADVANCE'){
		
		var depositSlipType = $('#depositSlipType').val();
		
		 if( $('select[name=depositeType]').val() =='C')
		   {
			if(depositSlipType == 0 || depositSlipType == "") {
				errorList.push("Please Select Deposit Slip Type");
		    } 
		 }
		 
		 if($("#fromDate").val()=="" && $("#toDate").val()=="")
		 {
		 errorList.push("Please Select From Date and To Date");
		 }
		 else if($("#fromDate").val()!="" && $("#toDate").val()=="")
		 {
		 errorList.push("Please Select To Date"); 
		 }
		 else if($("#fromDate").val()=="" && $("#toDate").val()!="")
		 {
		 errorList.push("Please Select From Date"); 
		 }
		 if($("#authStatus").val()==0)
		 {
			errorList.push("Please Select Authorisation Status"); 
		 }
		 if($("#fromDate").val()!=null)
    	 {
    	 errorList = validatedate(errorList,'fromDate');
    	 }
		 if($("#toDate").val()!=null)
		 {
			 errorList = validatedate(errorList,'toDate');
		 }
		 var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		 var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
		 var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		 if (eDate > sDate) {
			 errorList.push("From date Should be less than to Date");
		 }
		 
		 var depositSlipType=$("#depositSlipType option:selected").attr("code");
		 if(depositSlipType=="MWD"){
			 var advFromDate = $('#fromDate').val();
			 var advToDate = $('#toDate').val();
			 if(advFromDate != advToDate){
				 errorList.push("Please select From Date and To Date should be same"); 
			 }
		 }
	}
	if (searchType != 'ADVANCE') {
		$('#searchTypeId').val('NORMAL');
	}
	
	if(errorList.length > 0){
		
		  var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
		
			errMsg += '</ul>';			 
			$("#errorDiv").html(errMsg);	
			$("#errorDiv").show();	
			$("#errorDiv").removeClass('hide');
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			errorList = [];		
	  }
	else{
		
	if(checkValidation())
	{
	/*if($("#depositDate").val()=="")
		{
		errorList.push("Please Enter Remittance Date");   
		}*/
	
	/*  if( $('select[name=depositeType]').val() =='C')
		  {*/
	if($("#account").val()==0 || $("#account").val()=="")
		{
		errorList.push(getLocalMessage('accounts.chequebookleaf.please_selectbankaccount'));
		
		}
      /*  }*/
	 /* if($("#fromDate").val()=="")
		  {
		  errorList.push("Please Select From Date");
		  }
	  if($("#toDate").val()=="")
	  {
		  errorList.push("Please Select To Date");
	  }*/
	/*  if($("#fieldCode2").val()==0 ||$("#fieldCode2").val()=="")
		{
		errorList.push("Please Select Field");
		}
		if($("#deptId2").val()==0 ||$("#deptId2").val()=="")
		{
		errorList.push("Please Select Department");
		}*/
	  if($("#deptId").val()==0 || $("#deptId").val()=="")
		  {
		  errorList.push(getLocalMessage('accounts.chequebookleaf.mode'));
		  }
	  if((searchType != 'ADVANCE') && ($("#rmDate").val()==0 || $("#rmDate").val()==""))
		{
		errorList.push(getLocalMessage('account.select.date'));
		}
	  if(errorList.length > 0){
			
		  var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
		
			errMsg += '</ul>';			 
			$("#errorDiv").html(errMsg);	
			$("#errorDiv").show();	
			$("#errorDiv").removeClass('hide');
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			errorList = [];		
	  }
	  else
		  {
			$("#errorDiv").hide();	
		  
	  if( $('select[name=depositeType]').val() =='C')
	   {
		  if(getLedgerDetails(obj,mode))
			  {
			  $("#errorDiv").hide();	
		  $("#chequeOrDDDetails1").hide();
		   $("#receiptDetailId2").hide()
   	    $("#ledgerDetailId2").show();
   	    $("#Denomination").show();
   	    $("#getReceiptDetails").show();
   	    searchData();
		}
		  else
			  {
			  var errMsg= getLocalMessage('no.record.found');
			 
			  $("#errorDiv").html(errMsg);	
				$("#errorDiv").show();	
				$("#errorDiv").removeClass('hide')
			  }
	   }
	  else
		  {
		  if(searchCheckDetails(obj))
			  {
		  $("#cashMode").hide();
		  $("#errorDiv").hide();	
    	   $("#receiptDetailId2").hide();
    	    $("#ledgerDetailId2").hide();
    	    $("#Denomination").hide();
    	    $("#getReceiptDetails").hide();
    	  $("#chequeOrDDDetails1").show();
    	  searchData();
			  }
		  else
			  {
			  var errMsg= getLocalMessage('no.record.found');
			  $("#errorDiv").html(errMsg);	
				$("#errorDiv").show();	
				$("#errorDiv").removeClass('hide')
			  }
		  }
		  }
	  $("#transactionDateId").val(toDate);
	  $("#depositDateCheque").val(toDate);
	  denaminationDate();
}
	 
}
}

function denaminationDate(){
	
	var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	
	var data =$('#toDate').val();
	//var arr = data.split('/');
	
	//var maxDate = ($('#liveModeDate').val());
	var curArr;
if(data == null || data == undefined || data == ""){
	curArr = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
}else{
	var arr = data.split('/');
		var day=arr[0];
		var month=arr[1];
		var year=arr[2];
		curArr = month+"/"+day+"/"+year;
}
	//var D6 = new Date(curArr); 
	
	//var maxDate = ($('#liveModeDate').val());
	//var toDate = $("#date").html("<span>"+arr[0] + "</span></br>" + arr[1]+"/"+arr[2]);	 

	var today = new Date(curArr);	
	$("#transactionDateId").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		//minDate: today,
		maxDate: '0',
		
		
	});
	
	var today = new Date(curArr);
	 $("#depositDateCheque").datepicker({
		 dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,	
			maxDate: '0',
			
			
		}); 
	
}
function searchData()
{
	
	  $("#searchDeposit").attr("disabled",true);
	  $('#deptId').attr('disabled', true).removeClass("mandColorClass");
	  $('#fieldCode2').attr("disabled",true).trigger('chosen:updated');
	  $('#funcId').attr("disabled",true).trigger('chosen:updated');
	  $('#account').attr("disabled",true).trigger('chosen:updated')
	  $('#fromDate').attr('readonly', true).removeClass("datepicker hasDatepicker");
		/*$('#deptId2').attr('disabled', true);*/
		$('#deptId2').attr("disabled",true).trigger('chosen:updated');
		$('#toDate').attr('readonly', true).removeClass("datepicker hasDatepicker");
		$("#depositSlipType").attr("disabled",true);
		 if($("#deptId").val()=='C')
			{
			 $("#afterSearchDiv").show();
			}
		 else{
				$("#showSaveBtn").show();
			}
		$('#depositDate').attr('readonly', true).removeClass("datepicker hasDatepicker");
		$("#resetDiv").hide();
	  $('#deptId2').attr('disabled', true);
	  $('#toDate').attr('readonly', true).removeClass("datepicker hasDatepicker");
	  //$("#afterSearchDiv").show();
	  $('#depositDate').attr('readonly', true).removeClass("datepicker hasDatepicker");
	  $("#resetDiv").hide();

}
function validateshowFormData(){
	
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var feeMode = $("#depositType").val();
	var array =  new Array();
	array[0]=fromDate;
	array[1]=toDate;
	array[2]=feeMode;
	var errorList = [];
	
	if(fromDate=="" && toDate=="" && feeMode=="" && $("#slipNumber").val()=="" && $("#account").val()=="")
		{
		errorList.push("Please select any one criteria for search");
		}
	else
		{
	
	if(fromDate == '' && toDate !='') {
		errorList.push("Please Select From Date");
	}
	
	if(toDate == ''  && fromDate!='') {
		errorList.push("Please Select To Date");
	}
	
	  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	  var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
	  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
	  if (eDate > sDate) {
		  errorList.push("To Date should not be less than From Date");
	        
	  }
	  
	}
	if(fromDate!=null)
		{
		errorList = validatedate(errorList,'fromDate');
		}
	if(toDate!=null)
		{
		errorList = validatedate(errorList,'toDate');
		}

		
	 if(errorList.length > 0){
			
		 var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
	     errMsg += '</ul>';		 
		$("#errorId").html(errMsg);					
		$("#errorDiv").show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		
		errorList = [];		
		array[3]=false;
		return array;
	}
	 array[3]=true;
	return array;
}

function showForm(obj){
	var url = "AccountDepositSlip.html?createDataForm";
	//var arr =   validateshowFormData()
	/*if(arr[3]){*/
	//var requestData = "fromDate=" + $('#fromDate').val() + "&toDate=" + $('#toDate').val() + "&feeMode=" + $("input[name='depositeType']:checked").val();
	var returnData =__doAjaxRequest(url,'post',"",false);
	$('.widget').html(returnData);
	prepareDateTag();
	/*}*/
}




function saveFormData(obj){
	
	$('.error-div').hide();
	var errorList = [];
	var fundCode =   $('#fundCode').val();
	var fieldCode =  $('#fieldCode2').val();
	var depositeSlipDate =   $('#depositDate').val();	
	var narration =  $('#narration').val();
	var SfromDate =  $('#SfromDate').val();
	var StoDate =  $('#StoDate').val();
	var bmBankid =   $('#bmBankid').val();
	var baAccountid =  $('#account').val();
	var total =  $("#total").val();
	var depositeAmount =  $("#depositeAmount").val();
	/*$("#SfeeMode").val($("#deptId option:selected").attr('code'));*/
	$("#SfeeMode").val($("#deptId option:selected").val());
	
	/*if(fieldCode == 0  || fieldCode == "" )
	{
	errorList.push("Please Select field");
	}*/
	/*if(depositeSlipDate == "" )
	{
	errorList.push("Please Select depositeSlipDate");
	}*/
	if(SfromDate == "" )
	{
	errorList.push("Please Select fromDate");
	}
	if(StoDate == "" )
	{
	errorList.push("Please Select toDate");
	}
	
	
	if(depositeAmount == "" )
	{
	errorList.push("depositeAmount   cant not be null");
	}
/*	if(narration == "")
	{
	errorList.push("Please Enter Remark");
	}*/
	if($("#deptId").val()=="" || $("#deptId").val()==0)
		{
		errorList.push("Please Select Deposit Mode");
		}
	
	if($("#deptId").val()=='C')
	  {
			if($("#depositDateLedger").val()=="")
			{
			errorList.push("Please Enter Remittance Date");   
			}
			if($("#depositDateLedger").val()!=null)
			{
			errorList = validateRemitanceDate(errorList,'depositDateLedger');
			//errorList = validatedate(errorList,'depositDateCheque');
			if (errorList.length == 0) {
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var eDate = new Date($("#depositDateLedger").val().replace(pattern,'$3-$2-$1'));
			var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
			if (eDate < sDate) {
				  errorList.push("Remmitance date should not be less then to Date");
			  	}
			  }
			}
     }
	
	if($("#deptId").val()!='C'){
		if($("#depositDateCheque").val()=="")
		{
		errorList.push("Please Select Remittance Date");   
		}
		if($("#depositDateCheque").val()!=null)
			{
			errorList = validateRemitanceDate(errorList,'depositDateCheque');
			//errorList = validatedate(errorList,'depositDateCheque');
			if (errorList.length == 0) {
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			  var eDate = new Date($("#depositDateCheque").val().replace(pattern,'$3-$2-$1'));
			  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
			  if (eDate < sDate) {
				  errorList.push("Remmitance date should not be less then to Date");
			  }
			}
		}
	}
	
		
	if($("#deptId").val()=='C')
		{
		
		var depositSlipType=$("#depositSlipType option:selected").attr("code");
		if(depositSlipType=="LWD" || depositSlipType=="RWD"){
			
		if(baAccountid == "" || baAccountid ==0)
		{
		errorList.push("Please Select Account");
		}
		if($(".case1:checked").length==0)
				{
				errorList.push("Please Select Required Details.");
				}
				if(total!="" && total!=0){
				if($("#ledgerTotalAmount").val()!=total)
				{
					errorList.push("Denomination total amount should be equal with Total Deposit Slip Amount");
				}
				}
			if(total=="" ||total==0)
				{
				errorList.push(getLocalMessage("account.enter.denomination.details"));
				}

			var searchType = $('#searchTypeId').val();
			if (searchType == 'NORMAL') {
				$('#depositSlipType').val("");
				$('#saveDeptSlipType').val("");
				}
		}
		if(depositSlipType=="MWD"){
			if(total!="" && total!=0){
				if($("#depositSlipAmount").val()!=total){
					errorList.push("Denomination total amount should be equal with Total Deposit Slip Amount");
				}
			}
			if(total=="" ||total==0)
			{
			errorList.push("Please Enter Denomination Details ");
			}
		}
	}
		
	else if($("#deptId").val()!='C' && $("#deptId").val()!='')
		{
		/*if($(".case2:checked").length==0)
		{
			errorList.push("Please Select One Of the Drawee Bank");	
		}
		else
			{*/
		if($("#deptId").val()!='W' || $("#deptId").val()!='POS'){
			if($(".case3:checked").length==0)
			{
				errorList.push("Please Select One Of the Cheque");	
			}
		}
			$("#total").val($("#chequeTotalAmount").val());
			
			/*}*/
		}
	
	if(errorList.length > 0){
		
		 var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});
	
		errMsg += '</ul>';			 
		$("#errorDiv").html(errMsg);	
		$("#errorDiv").show();	
		$("#errorDiv").removeClass('hide')
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		
		errorList = [];		
		
		return false;
	}
	var url = "AccountDepositSlip.html?checkTemplate";
    var requestData = "depMode=" + $("#deptId option:selected").attr('code') + "&deptId=" +$("#deptId2").val();

	var returnData =__doAjaxRequest(url,'post',requestData,false);

	if(returnData=="N")
		{
		alert("Voucher template is not exist");
		return false;
		}
	else
		{
	
		showConfirmBoxSave();
		
		}
	}

function showConfirmBoxSave(){
		
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+' </h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>  '+
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsg(){
	
	$("#saveField").val($("#fieldCode2").val());
	$("#saveFund").val($("#account option:selected").attr('code'));	
	$("#saveAccount").val($("#account option:selected").val());	
	$("#saveDept").val($("#deptId2 option:selected").val());	
	var url = "AccountDepositSlip.html?save";
	//var	formName =	findClosestElementId(obj, 'form');
	//var theForm	=	'#'+formName;
	var requestData = $('#frmMaster,#frmMaster2,#frmMaster3').serialize();
	//var requestData = $('#frmMaster').serialize();
	
    
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	
	var slipNumber= $(returnData).find('#slipNumber'); 
	var generateDeposit = getLocalMessage("account.generated.deposit.slip.no");
	
	var savedSuccessfully = getLocalMessage("account.saved.successfully");
	if(slipNumber.val()!=undefined)
		{
		showConfirmBox(generateDeposit + ' ' +slipNumber.val()+ ' ' + savedSuccessfully);

		}
	else
		{
		$('.content').html(returnData);
		$('.content').html(returnData);
		}
}

function getLedgerDetails(obj,mode){
	
	
	if(mode!='true')
	{
	$("#SfeeMode").val($("#deptId option:selected").attr('code'));
	var url = "AccountDepositSlip.html?getLedgerDetails";
	var	formName =	findClosestElementId(obj, 'form');
	$("#SfeeMode").val($( "#deptId option:selected").attr('code'));
	var theForm	=	'#'+formName;
	requestData =  __serializeForm(theForm);
	requestData = requestData+"&searchType="+$('#searchTypeId').val();
	
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	 var obj=$(returnData).find('#isEmpty'); 
	
     if(obj.val()=='Y')
	    	{
    	    return false;
	    	}
     else
    	 { 
		var depositSlipType=$("#depositSlipType option:selected").attr("code");
			if(depositSlipType=="LWD"){
				 
				$('#ledgerDetailId').html(returnData);
		    	$('#ledgerDetailId').show();
		    	$('#ledgerManualDetailId').hide();
	    	 //$('#ledgerDetailDepSlipId').hide();
		   }
		 if(depositSlipType=="RWD")
		   {
	    	 
	    	 $('#ledgerDetailId').html(returnData);
	    	 $('#ledgerDetailId').show();
	    	 $('#ledgerManualDetailId').hide();
		   }
		 if(depositSlipType=="MWD"){
			$('#ledgerDetailId').html(returnData);
		    $('#ledgerDetailId').hide();
		    $('#ledgerManualDetailId').show();
		  }
    	}
	}
	else
		{
		  var totalAmount=0;
	    
	      $('.ledgerTr').each(function(i) {
	    	 
	    	  var amount=parseFloat($("#ledgerAmount"+i).val())
	    	  
	    	  totalAmount=totalAmount+amount;
	    	  $("#ledgerTotalAmount").val(totalAmount);
	    	  
	      });
		}
	 return true;
}

function getAmount(count){
	
	var descCash = $('#denomDesc'+count).val();
	var denom = 0 ;
	if( $('#denomination'+count).val() != '' && $('#denomination'+count).val() >0)
		{
		 denom = $('#denomination'+count).val();
		
		}
	
	 var amount  = parseFloat(descCash) * parseFloat(denom);
		$('#amount'+count).val(amount);
	
	var total=0;
	var coins = $('#coins2').val();
	var rowCount = $('.tabledenomination tr').length;

	for (var i = 0; i < rowCount -2; i++) {
	  
		total += parseFloat($("#amount" + i).val());
		//$("#total").val(total);
	
	}
	//var result = total.toFixed(2); 
	var result = parseFloat(total);
	
	var finalTotal = result.toFixed(2);
	$("#total").val(finalTotal);
    getAmountFormatInStatic('total');
	
}
function closeErrBox()
{
	$('#errorDiv').hide();
}
function resetSearchDepositForm()
{
	$('#deptId').attr('disabled', false).val("");
	$('#fromDate').attr('disabled', false).removeAttr("datepicker hasDatepicker").val("");
	$('#deptId2').attr('disabled', false).val("");
	$('#toDate').attr('disabled', false).val("");
	$('#fieldCode2').attr('disabled', false).val("");
	$('#account').attr('disabled', false).val("");
	$('#depositeAmount').attr('disabled', false).val("");
	$("#receiptDetailId2").hide();
    $("#ledgerDetailId2").hide();
	$("#Denomination").hide();
	$("#getReceiptDetails").hide();
	$("#cashMode").hide();
	$("cashdepositeType1").val("");
	$("#chequeOrDDDetails1").hide();
	$("#errorDiv").hide();
//	$("#fieldCode2_chosen").val("");
	$('#fieldCode2').val('').trigger('chosen:updated');
	$('#account').val('').trigger('chosen:updated');
	$("#afterSearchDiv").hide();
	//$("#afterSearchManualDiv").hide();
	 $("#searchDeposit").attr("disabled",false);
	$('#depositDate').attr('disabled', false).removeAttr("datepicker hasDatepicker").val("");
	$("#resetDiv").show();
	$("#DenominationDetails").hide();	
	$("#processBtn").hide();	
	$("#showSaveBtn").hide();
}
function resetDepositForm()
{
	//$('#ledgerDetailsId input[type="text"]').val('');
	$('.countClass input[type="text"]').val('');
	$('.checkClass input:checkbox').attr('checked',false);
	$('#ledgerDetailsId input:checkbox').attr('checked',false);
	$('.amountClass input[type="text"]').val('0');
	$("#total").val("");
	$("#ledgerTotalAmount").val("");
	resetSearchDepositForm();
}
function showConfirmBox(msg)
{
		
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = getLocalMessage("account.proceed.btn");
    	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+msg+'</h5>';
	    message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		//showModalBoxWithoutClose(errMsgDiv);
		showModalBoxWithoutCloseaccount(errMsgDiv);
}
function proceed() {
	
	var theForm = '#frmAccountRecieptReport';
	var requestData = __serializeForm(theForm);
	var url = "AccountDepositSlip.html?printDepositReciept"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
	
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.content').html(returnData);
}	
	
	
	

function resetGridForm()
{
	window.location.href = 'AccountDepositSlip.html'; 
}

function enabledCashBankAccounts(obj){

	var account=$("#deptId option:selected").attr("code");
	/*alert(account);*/
	if (account === "C") {
		$('#fundid').prop("disabled",false);
	}
}


function viewAdvSearchAllData(obj) { 
	
	$("#DepositSlipId").show();
	  $("#fieldDeptDiv").show(); 
	  $("#advSearchData").hide(); 
	  $("#fromTodateDiv").show();
	  $("#depositedateDiv").show();
	  $('#searchTypeId').val('ADVANCE');
	  $(".datepicker").datepicker('setDate', new Date()); 
	  $(".Moredatepicker").datepicker('setDate', new Date());
	  $("#RMDate").hide();
	  $('#rmDate').val('');
}

function checkValidation()
{
	var errorList=[];
	
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	
	/*if($("#fromDate").val()=="")
	  {
	  errorList.push("Please Select From Date");
	  }
	if($("#toDate").val()=="")
	{
	  errorList.push("Please Select To Date");
	}*/
	
	if(fromDate != null && fromDate != ""){
		
		  if(toDate == "" || toDate == null)
		  {
		  errorList.push("Please Select To Date"); 
		  }
		  
		  if((fromDate != null && fromDate!="") && ((toDate !="" && toDate!= null))){	
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		  if (eDate > sDate) {
			  errorList.push("From Date can not be less than To Date");
		  }  
		  }
		  }
	if(fromDate != null)
		{
		errorList = validatedate(errorList,'fromDate');
		}
   if(toDate != null)
	{
	errorList = validatedate(errorList,'toDate');
	}

	 if(errorList.length > 0){
			
		  var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
		
			errMsg += '</ul>';			 
			$("#errorDiv").html(errMsg);	
			$("#errorDiv").show();	
			$("#errorDiv").removeClass('hide');
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			return false;
	  }
	return true;
}

function checkValidationForDepositeDate(obj) { 

	$('.error-div').hide();
	var errorList=[];
	
	var baAccountid =  $('#account').val();
	if($("#deptId").val()=='C'||$("#deptId").val()=='W'||$("#deptId").val()=='POS')
		{
		if($("#transactionDateId").val()=="")
		{
		errorList.push(getLocalMessage("account.select.remittance.date"));   
		}
		if($("#transactionDateId").val()!=null && $("#transactionDateId").val()!="")
		{
		errorList = validateRemitanceDate(errorList,'transactionDateId');
		if (errorList.length == 0) {
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($("#transactionDateId").val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		  if (eDate < sDate) {
			  errorList.push("Remmitance date should not be less then to Date");
		     }
		  }
		}
		if(baAccountid == "" || baAccountid ==0)
		{
		errorList.push("Please Select Bank Account");
		}
		var depositSlipType=$("#depositSlipType option:selected").attr("code");
		if(depositSlipType=="LWD" || depositSlipType=="RWD"){
			if($(".case1:checked").length==0)
			{
			errorList.push(getLocalMessage("account.select.required.details"));
			}
		  }
		}
		if(depositSlipType=="MWD"){
			var depositSlipAmount =  $('#depositSlipAmount').val();
			if(depositSlipAmount == "" || depositSlipAmount ==0)
			{
				errorList.push("Please Enter Deposit Slip Amount");
			}
		}
	
	 if(errorList.length > 0){
			
		  var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
		
			errMsg += '</ul>';			 
			$("#errorDiv").html(errMsg);	
			$("#errorDiv").show();	
			$("#errorDiv").removeClass('hide');
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			return false;
	  }
	 
	  if (errorList.length == 0) {
	
		//var theForm = '#frmAccountRecieptReport';
		//D#79853
		var formName = findClosestElementId(obj, 'form');
        var theForm = '#' + formName;
        var requestData = __serializeForm(theForm);
		var url = "AccountDepositSlip.html?checkDepositSlipAmtExist"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var depositSlipAmount = $("#depositSlipAmount").val();
		if(parseInt(depositSlipAmount) > parseInt(returnData)){
			errorList.push("Deposit slip amount should not be greater than balance receipt amount");
			$("#depositSlipAmount").prop("readonly", false);
		}else{
			$("#depositSlipAmount").prop("readonly", true);
		}
		if(errorList.length > 0){
			
			  var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				
				$.each(errorList, function(index) {
					errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				});
			
				errMsg += '</ul>';			 
				$("#errorDiv").html(errMsg);	
				$("#errorDiv").show();	
				$("#errorDiv").removeClass('hide');
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
		  }
	  }
	  
	  $("#DenominationDetails").show(); 
	  $("#processBtn").show(); 
	  $("#afterSearchDiv").hide(); 
	  //$("#afterSearchManualDiv").hide();
	  $("#showSaveBtn").hide();
}

function validateTransactionDate( selectedDate) {
	
	
	
	var searchType = $('#searchTypeId').val();
	var requestData = {
			'transactionDate':selectedDate,
			'searchType': searchType
	}
	
	
	var response = __doAjaxRequest('AccountDepositSlip.html?onTransactionDate', 'GET', requestData, false,'json');
	if (response == 'OK') {
		$('#errorDivId').hide();
	} else {
		$('#transactionDateId').val('');
		var errorList = [];
		errorList.push(response);
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

/**
 * used to get SLI Date from SLI Prefix
 * @returns 
 */
function findSLIDateDate( ) {
	return __doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
}


function checkDeptSlipType(){
	
	var depositSlipType = $('#depositSlipType').val();
	
	 if( $('select[name=depositeType]').val() =='C' || $('select[name=depositeType]').val() =='POS'||  $('select[name=depositeType]').val() =='W')
	   {
		 $("#depositSlipType").attr("disabled",false);
	   }else{
		   //$('#depositSlipType').val("");
		  $("#depositSlipType").attr("disabled",true);
	   }
}

function validateRemitanceDate(errorList,dateElementId)
{
	$('.error-div').hide();
	var dateValue = $("#"+dateElementId).val();

if(dateValue != null && dateValue != ""){
	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	// Match the date format through regular expression
	if(dateValue.match(dateformat))
	{
	//document.form1.text1.focus();
	//Test which seperator is used '/' or '-'
	var opera1 = dateValue.split('/');
	//var opera2 = dateValue.split('-');
	lopera1 = opera1.length;
	//lopera2 = opera2.length;
	// Extract the string into month, date and year
	if (lopera1>1)
	{
	var pdate = dateValue.split('/');
	}
	/*else if (lopera2>1)
	{
	var pdate = dateValue.split('-');
	}*/
	var dd = parseInt(pdate[0]);
	var mm  = parseInt(pdate[1]);
	var yy = parseInt(pdate[2]);
	// Create list of days of a month [assume there is no leap year by default]
	var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	if (mm==1 || mm>2)
	{
	if (dd>ListofDays[mm-1])
	{
	errorList.push('Invalid date format !');
	//alert('Invalid date format!');
	//return false;
	}
	}
	if (mm==2)
	{
	var lyear = false;
	if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
	{
	lyear = true;
	}
	if ((lyear==false) && (dd>=29))
	{
	errorList.push('Invalid date format !');
	//alert('Invalid date format!');
	//return false;
	}
	if ((lyear==true) && (dd>29))
	{
	errorList.push('Invalid date format !');
	//alert('Invalid date format!');
	//return false;
	}
	}
	//date value less than or equal today day in date wise
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var sDate = new Date(dateValue.replace(pattern,'$3-$2-$1'));
	if (sDate > new Date()) {
		//errorList.push('Invalid date format!' + ' max date is :' + ' current date.');		
                        //alert('Invalid date format!');
		//return false;  
	}
	//year value between 1902 and 2018
	if(yy < 1902 || yy > (new Date()).getFullYear()) {
	  //alert("Invalid value for year: " + yy + " - must be between 1902 and " + (new Date()).getFullYear());
	  errorList.push("Invalid value for year: " + yy + " - must be between 1902 and " + (new Date()).getFullYear());
	  //return false;
	}
	}
	else
	{
	errorList.push('Invalid date format !');
	//alert("Invalid date format!");
	//document.form1.text1.focus();
	//return false;
	}	
}
return errorList;
}