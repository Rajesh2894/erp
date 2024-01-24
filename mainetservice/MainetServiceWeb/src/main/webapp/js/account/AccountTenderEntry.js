var trTenderId = '';
$(function() {
	$("#grid").jqGrid(
			{
				url : "AccountTenderEntry.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('accounts.workorder.no'),getLocalMessage('account.tenderentrydetails.tenderdate'),getLocalMessage('account.tenderentrydetails.tenderamount'),getLocalMessage('accounts.emd.amount'), getLocalMessage('accounts.master.status'), "", getLocalMessage('bill.action')],
				colModel : [ 
				             {name : "trTenderId",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name : "trTenderNo",width : 30,align : 'center',sortable : true,searchoptions: { "sopt": ["bw", "eq"] }},
				     	     {name : "trEntryDate",width : 35, align : 'center',sortable : false,searchoptions: { "sopt": [ "eq"] }}, 
				             {name : "trTenderAmount",width : 35,sortable : true,classes:'text-right',searchoptions: { "sopt": [ "eq"] }}, 
				             {name : "trEmdAmt", width : 35,sortable : true,classes:'text-right',secrchoptions: { "sopt": ["eq"]}},
				             {name : "authStatus", width : 30,sortable : true,align : 'center',secrchoptions: { "sopt": ["eq"]}},
				             {name : "statusCodeValue", width : 30,sortable : true,align : 'center',secrchoptions: { "sopt": ["eq"]}, hidden:true},
				             {name: 'trTenderId', index: 'trTenderId', width:30 , align: 'center !important',formatter:addLink,search :false}
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
							caption :'Work Order Entry List', 
						});
				 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
				 $("#pagered_left").css("width", "");
			});

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.trTenderId+"' trTenderId='"+rowdata.trTenderId+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editClass' title='Edit'value='"+rowdata.trTenderId+"' trTenderId='"+rowdata.trTenderId+"' ><i class='fa fa-pencil'></i></a> " +
   		"<a class='btn btn-success btn-sm' title='Bill Claim' onclick=\"doOpenBillPageForWorkOrder('"+rowdata.trTenderId+"','"+rowdata.authStatus+"','"+rowdata.statusCodeValue+"','"+rowdata.trTenderAmount+"')\"><i class='fa fa-file-text text-white'></i></a> ";
}

$(function() {
		$(document).on('click', '.createData', function() {
			var url = "AccountTenderEntry.html?form";
			var requestData ="MODE_DATA=" + "EDIT";
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			$('.content').html(returnData);
			prepareDateTag();
			return false;			
		});			
	});

$(function(){
		$(document).on('click','.updateData', function(){
			
			var url="AccountTenderEntry.htm?getData";
			var requestData="MODE_DATA=" + "EDIT";
			var returnData=__doAjaxRequest(url,'post',requestData,false);
			$('.content').html(returnData);
			prepareDataTag();
			return false;
			})
	});



function getDetails(i){
		var url="AccountTenderEntry.html?getDetails";
		var returnData = 	{"tenderEntryDate": $("#trEntryDate").val(),
							"pacHeadId": $("#pacHeadId"+i).val(),
							"sacHeadId": $("#sacHeadId"+i).val(),
							"fundId"	:	$("#fundId"+i).val(),
							"functionId" :$("#functionId"+i).val(),
							"fieldId" :	$("#fieldId"+i).val(),}
		var response =	__doAjaxRequest(url,'POST',returnData,false);
		for(var i=0;i<response.length;  i++){
			$("#budgetaryProv"+i).val(response[i].budgetaryProv);
			$("#balanceProv"+i).val(response[i].balanceProv);
		
		}		
}

$(function() {
	$("#search").click(function(){ 
		var errorList1 = [];
		var tenderId = $('#trTenderId').val(); 
		 var trTenderId = $.trim($("#trTenderId").val());
		 if(trTenderId==0 || trTenderId=="")
		    errorList1.push(getLocalMessage('account.select.tender.no'));
		 if(errorList1.length > 0){ 
				var errMsg = '<ul>';
				$.each(errorList1, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList1[index] + '</li>';
				});
				errMsg += '</ul>';
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
		} 
		
		
		if( tenderId != '' ) {				
			var url = "AccountTenderEntry.html?getTenderDetailsForEdit";
			var returnData = "trTenderId="+tenderId;
			
			$.ajax({
				url : url,
				data : returnData,
				success : function(response) {
					
					var divName = '.widget';
					
					
					$("#tenderDiv").html(response);
					$("#tenderDiv").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});				
			
			
		}
	
	});
});
	$(document).on('click', '.editClass', function() {
		var errorList = [];
		var $link = $(this);
		var trTenderId = $link.closest('tr').find('td:eq(0)').text();
		var authStatus = $link.closest('tr').find('td:eq(5)').text();
		var url = "AccountTenderEntry.html?getTenderDetailsForEdit";
		var requestData = "trTenderId=" + trTenderId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Approved"){
			$('.content').html(returnData);
		}
		else{
			errorList.push("It is already approved so EDIT is not allowed!");
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    	});
		    	errorMsg +='</ul>';
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
		    }
		}
	});

$(function() {
	$(document).on('click', '.viewClass', function() {
		var $link = $(this);
		var trTenderId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountTenderEntry.html?getTenderDetailsForEdit";
		var requestData = "trTenderId=" + trTenderId + "&MODE_DATA=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
		prepareDateTag();
			
	});
});

function doOpenBillPageForWorkOrder(trTenderId,statusCodeFlag,statusCodeValue,balanceAmount){
	
	
    var errorList = [];
	var gr = jQuery("#depositGrid").jqGrid('getGridParam','selrow');	
	var url = "AccountBillEntry.html?createformFromWorkOrder";
	var requestData = {"trTenderId" : trTenderId,"MODE"  :"CREATE"};
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	
	if(statusCodeValue=="Y"){
		errorList.push("It is already claim initiated, so Bill Claim is not allowed!");
	} else if(statusCodeFlag=="Disapproved"){
		errorList.push("It is already Disapproved so Bill Claim is not allowed!");
	}if(errorList.length>0){
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    	});
    	errorMsg +='</ul>';
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');	
		return false;
    }else{
    	$('.content').html(returnData);
    }
};


function closeErrBox() {
	$('.error-div').hide();
}


	
//to generate dynamic table
$("#budRevTableDivID").on("click", '.addButton', function(e) {
var errorList = [];

   $('.appendableClass').each(function(i) {
		
		 var sacHeadId = $.trim($("#sacHeadId"+i).val());
		 if(sacHeadId==0 || sacHeadId=="") 
		 errorList.push("Please Select Account Heads");
		
		 var trTenderAmount = $.trim($("#trTenderAmount"+i).val());
		 if(trTenderAmount==0 || trTenderAmount=="") 
		 errorList.push("Please Enter Budget Head Description");
		
		 for(m=0; m<i;m++){
				if(($('#sacHeadId'+m).val() == $('#sacHeadId'+i).val())){
					
					errorList.push("The Combination AccountHead code already exists!");
				}
			}

		$("#indexdata").val(i);
   });
			if (errorList.length > 0) {
				$('#index').val(i);
				var errMsg = '<ul>';
				$.each(
								errorList,
								function(index) {
									errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
											+ errorList[index]
											+ '</li>';
								});

				errMsg += '</ul>';

				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
				return false;
			}
			e.preventDefault();
			var content = $(this).closest('#budRevTable tr').clone();
			$(this).closest("#budRevTable").append(content);
			content.find("input:text").val("");
			content.find("textarea").val("");
			content.find("input:checkbox").attr('checked',false);
			content.find('div.chosen-container').remove();
			content.find("select").chosen().trigger("chosen:updated");
			content.find('label').closest('.error').remove(); //for removal duplicate
			reOrderTableIdSequence();

		});

	
	//to delete row
	$("#budRevTableDivID").on("click", '.delButton', function(e) {
	
	var rowCount = $('#budRevTable tr').length;
	if (rowCount <= 2) {
	//alert("Can Not Remove");
	var msg = "can not remove";
	showErrormsgboxTitle(msg);
	return false;
	}
	
	$(this).closest('#budRevTable tr').remove();
	reOrderTableIdSequence();
	getSumOfWorkOrderValue();
	e.preventDefault();
	});

function reOrderTableIdSequence() {
$('.appendableClass').each(function(i) {
		$(this).find($('[id^="sacHeadId"]')).attr('id',"sacHeadId"+i+"_chosen");
		 $(this).find("select:eq(0)").attr("id","sacHeadId" + i); 
		 $(this).find("input:text:eq(0)").attr("id", "sacHeadId" + i);
		 $(this).find("input:text:eq(1)").attr("id", "budgetaryProv" + i);
		 $(this).find("input:text:eq(2)").attr("id", "balanceProv" + i);
		 $(this).find("input:text:eq(3)").attr("id", "trTenderAmount" + i);
		 $(this).find("select:eq(0)").attr("name","tenderDetList["+i+"].sacHeadId"); 
		 $(this).find("input:text:eq(0)").attr("name","tenderDetList["+i+"].sacHeadId");
		 $(this).find("input:text:eq(1)").attr("name","tenderDetList["+i+"].budgetaryProv");
		 $(this).find("input:text:eq(2)").attr("name","tenderDetList["+i+"].balanceProv");
		 $(this).find("input:text:eq(3)").attr("name","tenderDetList["+i+"].trTenderAmount");
		 $(this).find('.delButton').attr("id",	"delButton" + i);
		 $(this).find('.addButton').attr("id",	"addButton" + i);
		 $(this).find('#sacHeadId'+i).attr("onchange", "getOrgBalAmount(" + (i) + ")");
		 $(this).closest("tr").attr("id", "budRevId" + (i));
		 $("#indexdata").val(i);
		
	});

}

var thisCount;
function setSecondaryCodeFinance(count)
 {
	thisCount = count;
	 var primaryAcCode=$('#pacHeadId'+count).val();
	 
		$('#sacHeadId'+count).find('option:gt(0)').remove();
		
		if (primaryAcCode > 0) {
			var postdata = 'pacHeadId=' + primaryAcCode;
			
			var json = __doAjaxRequest('AccountTenderEntry.html?sacHeadItemsList','POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#sacHeadId'+count).append(optionsAsString );
			
		}
 }		

$(document).ready(function(){
	
	  $('.tenderClass').each(function(i) {
	    	 var primaryCode=$('#pacHeadId'+i).val();
	    	 $('#sacHeadId'+i).find('option:gt(0)').remove();
	    	 if (primaryCode > 0) {
	    	 var postdata = 'pacHeadId=' + primaryCode;
	    	 var json = __doAjaxRequest('AccountTenderEntry.html?sacHeadItemsList',
	    				'POST', postdata, false, 'json');
	    		var  optionsAsString='';
	    		
	    		$.each( json, function( key, value ) {
	    			if(key== $('#selectedSecondaryValue'+i).val())
	       			{	
	       				optionsAsString += "<option value='" +key+"' selected>" + value + "</option>";
	       			}
	       			else
	       	   			{
	       			 optionsAsString += "<option value='" +key+"'>" + value + "</option>";
	       	   			}
	    			});
	    		$('#sacHeadId'+i).append(optionsAsString );
	    		
	    	}
	    });
	  
	  $("#dpDeptid").chosen();
	  $("#vmVendorid").chosen();
	  $("#trEmdAmt").chosen();
	  $("#sacHeadId0").chosen();
});

var thisCount;
function setBudgetaryProv(count)
{
	thisCount=count;
	var secondaryAcCode=$("sacHeadId="+count).val();
		$('budgetaryProv'+count).find('option:get(0)').remove();
		
		if(secondaryAccode>0){
				var postdata='sacHeadId='+secondaryAcCode;
				var json=__doAjaxRequest('AccountTenderEntry.html?getDetails','POST',postdata,false,'json');
				var  optionsAsString='';
				
				$.each( json, function( key, value ) {
					optionsAsString += "<option value='" +key+"'>" + value + "</option>";
					});
				$('#sacHeadId'+count).append(optionsAsString );
				
				
		}
}

 function removeRow(cnt,mode)
{
if ($('#tbl1 tr').size() > 2) {
		$("#tr"+cnt).remove();
		cnt--;
		reorderAccount(mode);
		
		} else {

				  var msg = getLocalMessage('lgl.cantRemove'); 
				var msg = "can not remove";
				showErrormsgboxTitle(msg);
			}
}

function saveTenderEntryForm(element){
	
	var errorList = [];
	errorList = validatedates(errorList);

	errorList = validatedate(errorList,'trProposalDate');
	if (errorList.length == 0) {
		var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
		if(response == "Y"){
			errorList.push("SLI Prefix is not configured");
		}else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var date = new Date($("#trProposalDate").val().replace(pattern,'$3-$2-$1'));
		var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
		if (date < sliDate) {
			errorList.push("Proposal date can not be less than SLI date");
		}
		var tenderDate = new Date($("#trTenderDate").val().replace(pattern,'$3-$2-$1'));
		if (tenderDate < sliDate) {
			errorList.push("Work order date can not be less than SLI date");
		  }
		}
	}
	if (errorList.length == 0) {
	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url	=	$(theForm).attr('action');
    var response= __doAjaxRequestValidationAccor(element,url+'?create', 'post', requestData, false, 'html');
    if(response != false){
       $('.content').html(response);
    }
	} else {
		if(errorList.length>0){
	    	
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    	});
	    	errorMsg +='</ul>';
	    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
	    		    	
	    }
	}
}

function loadBudgetExpenditureData(obj) {

	$('.error-div').hide();
	var errorList = [];
	
	$('#vmVendorid').val(""); 
	$("#vmVendorid").val('').trigger('chosen:updated');
	
	var prBudgetCodeid = $('#sacHeadId0').val(""); 
	$("#sacHeadId0").val('').trigger('chosen:updated');
	
	var budgetaryProv = $('#budgetaryProv0').val(""); 
	var balanceProv = $('#balanceProv0').val(""); 
	var trTenderAmount = $('#trTenderAmount0').val(""); 
		
	if (errorList.length == 0) {
		
		var divName = ".content";
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountTenderEntry.html?getBudgetExpenditureData";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
	}
};

function loadDepositTypeEmdData(obj) {

	$('.error-div').hide();
	var errorList = [];

	
	
	$('#trEmdAmt').val(""); 
	$("#trEmdAmt").val('').trigger('chosen:updated');
	
	var dpDeptid = $("#dpDeptid").val();
	
	if(dpDeptid == "" || dpDeptid=='0' || dpDeptid== null || dpDeptid== undefined){
		errorList.push("Pleasae Select Department!");
		$("#vmVendorid").val("");
		$("#vmVendorid").val('').trigger('chosen:updated');
	}
	
	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    		    	
    }
	
	if (errorList.length == 0) {
		
		var divName = ".content";
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountTenderEntry.html?getBudgetExpenditureData";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
	}
	
	if (errorList.length == 0) {
		
		var divName = ".content";
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountTenderEntry.html?getDepositTypeEmdData";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
	}	

};

function getOrgBalAmount(cnt) {

	$('.error-div').hide();
	var errorList = [];

	var dpDeptid = $("#dpDeptid").val();
	
	if(dpDeptid == "" || dpDeptid=='0' || dpDeptid== null || dpDeptid== undefined){
		errorList.push("Pleasae Select Department");
		$("#sacHeadId"+cnt).val("");
		$("#sacHeadId"+cnt).val('').trigger('chosen:updated');
	}
	
	var trTenderDate = $("#trTenderDate").val();
	
	if(trTenderDate == "" || trTenderDate=='0' || trTenderDate== null || trTenderDate== undefined){
		errorList.push("Pleasae Select Work Order Date");
		$("#sacHeadId"+cnt).val("");
		$("#sacHeadId"+cnt).val('').trigger('chosen:updated');
	}
	
	var theForm = '#tenderEntry';
	
	$("#budgetaryProv" + cnt).val("");
	$("#balanceProv" + cnt).val("");	
	$("#trTenderAmount" + cnt).val("");
	
	 var prBudgetCodeId = $.trim($("#sacHeadId"+cnt).val());
	 
	var editModeExp = $('#editModeExp').val();
	if(editModeExp == "edit"){
		var Revid = $('#Count').val();
	}else{
		 var Revid = $('#indexdata').val();
	}
		
	if(prBudgetCodeId != "" ){
		var dec;
	 for(m=0; m<=Revid;m++){
		 for(dec=0; dec<=Revid;dec++){
			 if(m!=dec){
			if(($('#sacHeadId'+m).val() == $('#sacHeadId'+dec).val())){
				errorList.push("The budget head already exists in expenditure details!");
				$("#sacHeadId"+cnt).val("");
				$("#sacHeadId"+cnt).val('').trigger('chosen:updated');
				$("#trTenderAmount" + cnt).val("");
			}
		  }
		} 
	  }
	}
	 
	 if(errorList.length>0){
	    	
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    	});
	    	errorMsg +='</ul>';
	    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
	    		    	
	    }
	
	 var requestData = __serializeForm(theForm);
	 
	if (errorList.length == 0) {

		var url = "AccountTenderEntry.html?getOrgBalGridload&cnt="+cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {

			if (key == 'BEAMT') {
				$("#balanceProv" + cnt).val(value);
			}
			if (key == 'OEAMT') {
				$("#budgetaryProv" + cnt).val(value);
			}
		});
	}
};


function copyContent(obj) {

	$('.error-div').hide();
	var errorList = [];
	
	
	var balanceProv = ($('#balanceProv0').val());
	var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();
	if(balanceProv == "" || balanceProv == null || balanceProv == undefined || balanceProv == "0" ){
			errorList.push("Balance budget provision is insufficient.");
			if(bugDefParamStatusFlag == 'Y'){
				$("#trTenderAmount0").val("");
			}
	}
	if (errorList.length > 0) {
			var errMsg = '<ul>';
				$.each(
				errorList,
				function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index]
					+ '</li>';
				});
				errMsg += '</ul>';
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
			return false;
	}
	
	if (errorList.length == 0) 
	{
		var id=  $(obj).attr('id');
		var arr = id.split('trTenderAmount');
		var indx=arr[1];
		
		 var prExpBudgetCode = $.trim($("#sacHeadId"+indx).val());
			
		 if(prExpBudgetCode==0 || prExpBudgetCode=="") {
		 var msg = "Please select budget head.!";
		  showErrormsgboxTitle(msg);
		 $('#trTenderAmount'+indx).val("");
		  return false;
		 }
		 
		 	var editModeExp = $('#editModeExp').val();
			if(editModeExp == "edit"){
				var idSum = $('#Count').val();
			}else{
				 var idSum = $('#indexdata').val();
			}
			
				var total =0;
				for(k=0; k<=idSum;k++){
					var collectedAmt = $("#trTenderAmount"+k).val();
					
					if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
						total += parseFloat($("#trTenderAmount"+k).val());
					}
				}
				var result = total.toFixed(2); 
				$("#trTenderAmount").val(result);
				
		var minAmt = ($('#balanceProv'+indx).val());
		var maxAmt = ($('#trTenderAmount'+indx).val());
		  
		  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
		    try{
		      maxAmt = parseFloat(maxAmt);
		      minAmt = parseFloat(minAmt);

		      if(maxAmt > minAmt) {
		        var msg = "work order amount can not be greater than balance budget provision amount.!";
		    	  showErrormsgboxTitle(msg);
		        $('#trTenderAmount'+indx).val("");
		        return false;
		      }
		    }catch(e){
		      return false;
		    }
		  } //end maxAmt minAmt comparison  
	}
}


function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToTenderEntryHomePage()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToTenderEntryHomePage () {
	window.location.href='AccountTenderEntry.html';
}

function showPopUpMsg(childDialog){
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        closeBtn : false ,
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	return false;
}

function searchWorkOrderEntryData(){
	$('.error-div').hide();
	var errorList = [];
	var trTenderNo = $("#trTenderNo").val();
	var vmVendorid = $("#vmVendorid").val();
	var trTypeCpdId = $("#trTypeCpdId").val();
	var sacHeadId = $("#sacHeadId").val();
	var trTenderAmount = $("#trTenderAmount").val();
	var statusId = $("#statusId option:selected").attr("code");
	
	if(statusId != null && statusId != "" && statusId!="0" && statusId != undefined){
		var authStatusId = statusId;
	}else{
		var authStatusId = "";
	}
	
	if ((trTenderNo == "" || trTenderNo =="0") && (vmVendorid == "" || vmVendorid =="0") && (trTypeCpdId == "" || trTypeCpdId =="0") && (sacHeadId == "" || sacHeadId =="0") && (trTenderAmount == "" || trTenderAmount =="0") && (authStatusId == "" ||  authStatusId=="0")) {
		errorList.push('Kindly select at least one search criteria');
	}
	
	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    		
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    		    	
    }
	 
	if (errorList.length == 0) 
	{

	var url ="AccountTenderEntry.html?getjqGridsearch";
	var requestData = {
			"trTenderNo" : trTenderNo,
			"vmVendorid" : vmVendorid,
			"trTypeCpdId" : trTypeCpdId,
			"sacHeadId" : sacHeadId,
			"trTenderAmount" : trTenderAmount,
			"statusId" : authStatusId,
		};
	
	var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	
	if (result != null && result != "") {
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	} else {
		var errorList = [];
		
		errorList.push(getLocalMessage("account.norecord.criteria"));
		
		if(errorList.length>0){		    	
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';		    		
	    	});
	    	errorMsg +='</ul>';		    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');		    		    	
	    }
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	}
}
};

function getSumOfWorkOrderValue(){
	
		var idSum = $('#indexdata').val();
	
		var total =0;
		for(k=0; k<=idSum;k++){
			var collectedAmt = $("#trTenderAmount"+k).val();
			
			if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
				total += parseFloat($("#trTenderAmount"+k).val());
			}
		}
		var result = total.toFixed(2); 
		$("#trTenderAmount").val(result);
}

function checkWorkOrderDate(){
	
	var errorList = [];
	
	
	var trTenderDate = $("#trTenderDate").val();
	if ((trTenderDate == "" || trTenderDate =="0")) {
		errorList.push('Kindly select work order date');
		$("#trProposalDate").val("");
	}
	
	if (errorList.length == 0) {
	var from = $("#trTenderDate").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#trProposalDate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Proposal date should not be greater than work order date");
			$("#trProposalDate").val("");
			}
		}
	}
	if (errorList.length > 0) {
			var errMsg = '<ul>';
				$.each(
				errorList,
				function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index]
					+ '</li>';
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

function checkProposalDate(){
	
	$("#trProposalDate").val("");
}

$("#trTenderDate").keyup(function(e){
    if (e.keyCode != 8){    
        if ($(this).val().length == 2){
            $(this).val($(this).val() + "/");
        }else if ($(this).val().length == 5){
            $(this).val($(this).val() + "/");
        }
    }
    checkProposalDate();
});

function validatedates(errorList)
{
	$('.error-div').hide();
	
var trTenderDate = $("#trTenderDate").val();
var name = "Work Order Date";
if(trTenderDate != null && trTenderDate != ""){
	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	// Match the date format through regular expression
	if(trTenderDate.match(dateformat))
	{
	//Test which seperator is used '/' or '-'
	var opera1 = trTenderDate.split('/');
	lopera1 = opera1.length;
	// Extract the string into month, date and year
	if (lopera1>1)
	{
	var pdate = trTenderDate.split('/');
	}
	var dd = parseInt(pdate[0]);
	var mm  = parseInt(pdate[1]);
	var yy = parseInt(pdate[2]);
	// Create list of days of a month [assume there is no leap year by default]
	var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	if (mm==1 || mm>2)
	{
	if (dd>ListofDays[mm-1])
	{
	errorList.push('Invalid date format in '+ name +'!');
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
	errorList.push('Invalid date format in '+ name +'!');
	}
	if ((lyear==true) && (dd>29))
	{
	errorList.push('Invalid date format in '+ name +'!');
	}
	}
	//date value less than or equal today day in date wise
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var sDate = new Date(trTenderDate.replace(pattern,'$3-$2-$1'));
	if (sDate > new Date()) {
		errorList.push('Invalid date format in '+ name +'!' + ' max date is :' + ' current date.');
	}
	//year value between 1902 and 2018
	if(yy < 1902 || yy > (new Date()).getFullYear()) {
	  errorList.push("Invalid value for year: " + yy + " - must be between 1902 and " + (new Date()).getFullYear());
	}
	}
	else
	{
	errorList.push('Invalid date format in '+ name +'!');
	}	
}

	var from = $("#trTenderDate").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#trProposalDate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Proposal date should not be greater than work order date");
		}
	}

return errorList;
}

