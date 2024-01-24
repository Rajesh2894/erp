
    $(function () {
    $("#grid").jqGrid({
        url: "BankAccountMaster.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: ["",getLocalMessage('accounts.bank.acc.no'),getLocalMessage('bank.master.acc.name'),getLocalMessage('bank.master.acc.type'),"",getLocalMessage('bill.action')], //"Edit","View"
        colModel: [
            { name: "baAccountId", width:100, sortable: true ,hidden:true},
            { name: "baAccountNo", width : 30,sortable: true },
            { name: "baAccountName",width : 30, sortable: true },
            { name: "bankTypeDesc",width : 20, sortable: true },
            { name: "ulbBankId", sortable: true,hidden:true },
            { name: 'baAccountId', index: 'baAccountId', width:20 , align: 'center !important',formatter:addLink,search :false},

        ],
        pager: "#pagered",
        rowNum: 30,
        rowList: [5, 10, 20, 30],
        sortname: "bmBankid",
        sortorder: "comDesc",
        height:'auto',
        viewrecords: true,
        gridview: true,
        showrow: true,
        loadonce: false,
        
       jsonReader : {
            root: "rows",
            page: "page",
            total: "total",
            records: "records", 
            repeatitems: false,
           }, 
        autoencode: true,
        editurl:"BankAccountMaster.html?update",
        caption: getLocalMessage("account.bank.master.list")
    }); 
});
   
 
    
    $(function() {		
		$(document).on('click', '.deleteClass', function() {
			
			var baAccountId = $(this).attr('value');
			//var orgid = $(this).attr('id');
			viewData(baAccountId);
		});
			
	});

	 function addLink(cellvalue, options, rowdata) 
	 {
	    return "<a class='btn btn-blue-3 btn-sm deleteClass' title='View Properties'value='"+rowdata.baAccountId+"' id='"+rowdata.baAccountId+"' ><i class='fa fa-building-o'></i></a> " +
	    		 "<a class='btn btn-warning btn-sm editClass' title='Edit'value='"+rowdata.baAccountId+"' id='"+rowdata.baAccountId+"' ><i class='fa fa-pencil'></i></a> ";
	 }
	 
	   $(function() {		
			$(document).on('click', '.editClass', function() {
				var gr = jQuery("#grid").jqGrid('getGridParam','selrow');			
				$("#errorDivServiceCheckList").hide();
				var url = "BankAccountMaster.html?formForUpdate";
			    var baAccountId = $(this).attr('value');
		//		var orgid=$(this).attr('id');
				var returnData = {"baAccountId" : baAccountId,MODE  :"UPDATE"};
				$.ajax({
					url : url,
					datatype: "json",
			        mtype: "POST",
					data : returnData,
					success : function(response) {						
					$(".widget-content").html(response);
					$(".widget-content").show();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});			
			});
			
			
		});
	   
	   
	   
	   
		function viewData(baAccountId){
			
			var url = "BankAccountMaster.html?formForView";
			var returnData = {"baAccountId" : baAccountId,"MODE"  :"VIEW"}
			$.ajax({
				url : url,
				datatype: "json",
		        mtype: "POST",
				data : returnData,
				success : function(response) {
					
					$(".widget-content").html(response);
					$(".widget-content").show();
					
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
			
			$.fancybox.close();
			$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			
		}
		
	/* function searchBankAccountData() {
		   var errorList = [];	
			var url = "BankAccountMaster.html?searchBankAccount";
			
			var accountNo = $("#accountNo").val();
			var accNameId = $("#bankAccountName").val();
			var bankId = $("#bankBranch").val();
			
			var bankName =$("#bankName").val();
			
			if(bankName!='0'  && bankId=='' && accountNo == '' && accNameId =="0")
			{
				errorList.push(getLocalMessage("Please select Bank Branch for selected Bank "));
			 var errMsg = '<ul><li>Please select Bank Branch for selected Bank </li></ul>';
	             $('#errorId').html(errMsg);
				$('#errorDivId').show();	

			}
			
			if(accountNo=='' && accNameId=='0' && bankId=='0')
				{
				errorList.push(getLocalMessage("Please select Bank Account or enter Bank Account Number"));

				}
			  if(errorList.length > 0){ 
			    	showBankError(errorList);
			    return false;
			    }else
				{
			$('.error-div').hide();
			var returnData = {
					"accountNo" 	: accountNo,"accNameId" 	: accNameId, "bankId" 	: bankId
					};
			
			$.ajax({
				url : url,
				data : returnData,
				success : function(response) {						
					$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});	
				}
		}*/
	 
	 
	 function searchBankAccountData() {
		 
		 	/*alert("In search");*/
			
			$('.error-div').hide();

			var errorList = [];

			var accountNo = $("#accountNo").val();
			var accNameId = $("#bankAccountName").val();
			//var bankId = $("#bankBranch").val();
			var bankName =$("#bankName").val();

			if(bankName =='0' && accountNo == '' && accNameId =='0') {
				errorList.push(getLocalMessage('account.deposit.search.validation'));
			}else{
			
			if(bankName == null || bankName == '' || bankName =='0' || bankName == undefined) 
			{
				//errorList.push('Please select Bank Name');
			}
			}
			
			if(errorList.length > 0){ 
			    	showBankError(errorList);
			   return false;
			   }else
			{
			    	
			var url = "BankAccountMaster.html?searchBankAccount";
			var requestData = {
					"accountNo" 	: accountNo,"accNameId" 	: accNameId, "bankId" 	: bankName
					};
			var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
			
			if (result != null && result != "") {
				$("#grid").jqGrid('setGridParam', {
					datatype : 'json'
				}).trigger('reloadGrid');
			} else {
				var errorList = [];
				
				errorList.push(getLocalMessage("account.norecord.criteria"));
				
				if(errorList.length > 0){ 
			    	showBankError(errorList);
			    return false;
			    }
				$("#grid").jqGrid('setGridParam', {
					datatype : 'json'
				}).trigger('reloadGrid');
			}
		}
	};
	
	 $(function() {	
			
			$(document).on('click', '.createData', function() {
		
				var url = "BankAccountMaster.html?form";
			    $.ajax({
					url : url,
					success : function(response) {					
						$(".widget-content").html(response);
						$(".widget-content").show();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});				
			});			
		});
	 
	 
	 
	 function showBankError(errorList)
	 {
		 var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + ' </li>&nbsp;';
			});
            errMsg += '</ul>';
             $('#errorId').html(errMsg);
			$('#errorDivId').show();	 
	 }
	 
	 function saveBankMaster(element)
	 { 
		
		 var errorList = [];	
		 var formMode_Id = $("#formMode_Id").val().toUpperCase();	
		 if(formMode_Id == "CREATE" ){
		   var bankName = $.trim($("#bankName").val());
		    if(bankName==0 || bankName=="" || bankName==undefined){
			errorList.push(getLocalMessage('account.select.bank.name'));}
		    var bankType = $.trim($("#bankType").val());
		    if(bankType==0 || bankType==""||bankType==undefined){
		    errorList.push(getLocalMessage('account.select.bankType'));}
		 }
		    
		    $('.accountClass').each(function(i) {
		     var bankName=$("#bankName").val();
		     
		     var accountNo=$.trim($("#accountNo"+i).val());	
		   	 var accountType = $.trim($("#cpdAccounttype"+i).val());
		     var accountName= $.trim($("#accountName"+i).val());
		     var cpdAccounttype= $.trim($("#cpdAccounttype"+i).val());
		     //var openingDate= $.trim($("#openingDate"+i).val());		
		     var functionId= $.trim($("#functionId"+i).val());
		     var accountHead= $.trim($("#primtHead"+i).val());
		     var field= $.trim($("#fieldItem"+i).val());
		     var fund= $.trim($("#fundId"+i).val());
		     $("#secHead"+i).val(bankName+"-"+accountNo+"-" +accountName);
		     
		     //var opBal=$.trim($("#opBal"+i).val());
		     var status=$.trim($("#babStatus"+i).val());
		     var statusCode=($('#babStatus'+i).find("option:selected").attr('code'));
		     var level=i+1;
		   	 if(accountNo===0 || accountNo==="")
	   		 {
	   		    errorList.push(getLocalMessage("account.enter.accNo.srNo")+" "+level);
	   		 }
		   	 else if(accountNo.length<8 || accountNo.length>16){
		   		 errorList.push(getLocalMessage("account.enter.accNo.srNo.limit")+" "+level); 
		   	 }
		   	 
		   	 
		   	 if(accountType===0 || accountType=="")
		    	{
		   		    errorList.push(getLocalMessage("account.select.accType.srNo")+" "+level);
		   	 }
		   	 if(accountName===0 || accountName==="")
	   		 {
	   		    errorList.push(getLocalMessage("account.enter.accName.srNo")+" "+level);
	   		 }
		 	if(cpdAccounttype==0 || cpdAccounttype=="")
	   		 {
	   		    errorList.push(getLocalMessage("account.enter.accName.type")+" "+level);
	   		 }
		   	 if(functionId===0 || functionId==="")
	   		 {
	   		    errorList.push(getLocalMessage("account.select.function.srNo")+" "+level);
	   		 }
		   	 if(accountHead===0 || accountHead==="")
	   		 {
	   		    errorList.push(getLocalMessage("account.select.primaryHead.srNo")+" "+level);
	   		 }
		   	 /*if(opBal==="")
	   		 {
	   		    errorList.push(getLocalMessage("Please Enter Opening Balance For Sr No."+" "+level));
	   		 }
		   	 if(openingDate===""||openingDate===0)
		   		 {
		   		errorList.push(getLocalMessage("Please Select Opening Balance Date"+" "+level));
		   		 }*/


		     });
		    
		    if(errorList.length > 0){ 
		    	showBankError(errorList);
		    return false;
		    }
		    
		    showConfirmSaveBox();
		    
	 }
	 
	 function showConfirmSaveBox(){
			
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
			
			$.fancybox.close();
			var requestData = $('#bankMasterForm').serialize();
			 var formMode_Id = $("#formMode_Id").val();	
			//var	formName =	findClosestElementId(element, 'form');
		    //var theForm	=	'#'+formName;
		    //var requestData = __serializeForm(theForm);
		    //var url	=	$(theForm).attr('action');
			var url ='BankAccountMaster.html?' + formMode_Id;
            var status=__doAjaxRequestForSave(url, 'post', requestData, false,'', ''); 
		    var obj=$(status).find('#successFlag'); 
	        if(obj.val()=='Y')
		    	{
		    	showConfirmBox();
		    	}
		     else
		    	{
		    	 $(".widget-content").html(status);
			     $(".widget-content").show();
		    	}
		}
	 
	 function removeRow(cnt,mode)
		{
		if ($('#tbl1 tr').size() > 2) {
				$("#tr"+cnt).remove();
				cnt--;
				reorderAccount(mode);
				//content.find("select:eq(0)").chosen().trigger("chosen:updated");
				} else {

						var msg = "can not remove";
						showErrormsgboxTitle(msg);
					}
		}
		function reorderAccount(mode)
		{
			var popupMode=mode;
			$('.accountClass').each(function(i) {
				 //Ids
				$(this).find("input:text:eq(0)").attr("id", "accountNo" + (i));
			    $(this).find("input:text:eq(1)").attr("id", "accountName" + (i));
			    //$(this).find("input:text:eq(2)").attr("id", "openingDate" + (i));
			    //$(this).find("input:text:eq(3)").attr("id", "opBal" + (i)).addClass("hasNumber text-right");
				$(this).find("select:eq(0)").attr("id", "cpdAccounttype" + (i)).attr;
				$(this).find("select:eq(1)").attr("id", "functionId" + (i));
				$(this).find("select:eq(2)").attr("id", "primtHead" + (i));
				$(this).find("select:eq(3)").attr("id", "fieldItem" + (i));
				$(this).find("select:eq(4)").attr("id", "fundId" + (i));
				$(this).find("select:eq(5)").attr("id", "babStatus" + (i));
			
				
			    $(this).find(".deletClass").attr("id", "deleteAccount" + (i));
			    $(this).find(".viewFund").attr("id", "viewFund" + (i));
				$(this).closest("tr").attr("id", "tr" + (i));
				$(this).closest('tr').find('#srNo').text(i+1);
				
			    //names
				$(this).find("input:text:eq(0)").attr("name", "listOfTbBankaccount[" + (i) + "].baAccountNo");
				$(this).find("input:text:eq(1)").attr("name", "listOfTbBankaccount[" + (i) + "].baAccountName");
				//$(this).find("input:text:eq(2)").attr("name", "listOfTbBankaccount[" + (i) + "].baOpenbalDate");
				//$(this).find("input:text:eq(3)").attr("name", "listOfTbBankaccount[" + (i) + "].baOpenBalAmt");
				$(this).find("select:eq(0)").attr("name", "listOfTbBankaccount[" + (i) + "].cpdAccountType");
				$(this).find("select:eq(1)").attr("name", "listOfTbBankaccount[" + (i) + "].functionId");
				$(this).find("select:eq(2)").attr("name", "listOfTbBankaccount[" + (i) + "].pacHeadId");
			    $(this).find("select:eq(3)").attr("name", "listOfTbBankaccount[" + (i) + "].fieldId");
			    $(this).find("select:eq(4)").attr("name", "listOfTbBankaccount[" + (i) + "].fundId");
			   /* $(this).find("select:eq(4)").attr("name", "listOfTbBankaccount[" + (i) + "].acCpdIdStatus");*/
			    if(mode=='update')
			    	{
			    	$(this).find("input:hidden:eq(0)").attr("name", "listOfTbBankaccount[" + (i) + "].baAccountid"); 
			      }
			     $(this).find(".deletClass").attr("onclick", "removeRow(\"" + (i) + "\",\"" + popupMode + "\")");

			     });
		}
		function showConfirmBox(){
			var formMode_Id = $("#formMode_Id").val();	
			if(formMode_Id=="create"){
				var successMsg =getLocalMessage('account.submit.succ');
			}else if(formMode_Id=="update"){
				var successMsg =getLocalMessage('account.update.succ');
			}
			var	errMsgDiv		=	'.msg-dialog-box';
			var message='';
			var cls =   getLocalMessage('account.billEntry.savedBtnProceed');
			message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
			 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
			 
			$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
			$('#btnNo').focus();
			showPopUpMsg(errMsgDiv);
		}
		
		function proceed () {
			window.location.href='BankAccountMaster.html';
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
		
		/*function showConfirmBox()
		{
			  var	errMsgDiv		='.msg-dialog-box';
				var message='';
				var cls = 'Proceed';
				message	+='<p>Record Submitted Successfully</p>';
				message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
				'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
				' onclick="proceed()"/>'+	
				'</p>';
				
				$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
				$(errMsgDiv).html(message);
				$(errMsgDiv).show();
				$('#btnNo').focus();
				showModalBox(errMsgDiv);
		
	  }
		function proceed() {
			window.location.href = 'BankAccountMaster.html'; 
		}*/

		$(document).ready(function(){
			/*$(".datepicker").datepicker({
			    dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true
			});*/
			/*$('.lessthancurrdate').datepicker({
				dateFormat: 'dd/mm/yy',	
				changeMonth: true,
				changeYear: true,
				maxDate: '-0d',
				yearRange: "-100:-0"
			});*/

			$('body').on('focus',".lessthancurrdate", function(){
			
				$('.lessthancurrdate').datepicker({
					dateFormat: 'dd/mm/yy',	
					changeMonth: true,
					changeYear: true,
					maxDate: '-0d',
					yearRange: "-100:-0"
				});
				
				}); 
			$('body').on('focus',".hasNumber", function(){
				$(".hasNumber").keydown(function(event) {
			    this.value = this.value.replace(/[^0-9]/g,'');
			     });
				}); 
			
		 });	

	
  function setBranchNames(obj) { 
	  
	var errorList=[];
	var bankName = $.trim($("#bankName").val());
	$('#bankBranch').empty();
	
	if(bankName==='' ||bankName===0){

		var optionsAsString='0';
		 $("#bankBranch").append(optionsAsString);

	}else{
		  
		var url = "BankAccountMaster.html?getBranchNames" ;		
		var reqData = "bankName=" + bankName;
       	var	branchDetails =__doAjaxRequestForSave(url, 'post', reqData, false,'', obj);

       	var  optionsAsString='0';
       	
		$("#bankBranch").html('');
		$('#bankBranch')
	    .append($("<option></option>")
	    .attr("value","").attr("code","")
	    .text(getLocalMessage('account.common.select')));
       	$.each(branchDetails, function(index){
			var lookUp=branchDetails[index];
			var codId=lookUp.lookUpId;
			var codDesc=lookUp.lookUpDesc;		
			$('#bankBranch').append($('<option>', {
			    value: codId,
			    text: codDesc
			}));
          $('#bankBranch').chosen().trigger("chosen:updated");  
			
		});

		}
	} 

  /*function getAmountFormat(obj){
	   alert("Hi");
	   var id=  $(obj).attr('id');
	   var arr = id.split('opBal');
	   var indx=arr[1];
	   
	   var opBal = ($('#opBal'+indx).val());
	  	  if (opBal != undefined && !isNaN(opBal) && opBal != null && opBal != '') {
	  			
	  			var actualAmt = opBal.toString().split(".")[0];
	  			var decimalAmt = opBal.toString().split(".")[1];
	  			
	  			var decimalPart =".00";
	  			if(decimalAmt == null || decimalAmt == undefined){
	  				$('#opBal'+indx).val(actualAmt+decimalPart);
	  			}else{
	  				if(decimalAmt.length <= 0){
	  					decimalAmt+="00";
	  					$('#opBal'+indx).val(actualAmt+(".")+decimalAmt);
	  				}
	  				else if(decimalAmt.length <= 1){
	  					decimalAmt+="0";
	  					$('#opBal'+indx).val(actualAmt+(".")+decimalAmt);
	  				}else{
	  					if(decimalAmt.length <= 2){
	  					$('#opBal'+indx).val(actualAmt+(".")+decimalAmt);
	  					} 
	  				}	
	  			}
	  		}
	  }
  */
  function getAmountFormatEdit(obj){
		
		var opBal = $('#opBal0').val();

		if (opBal != undefined && !isNaN(opBal) && opBal != null && opBal != '') {
			
		var actualAmt = opBal.toString().split(".")[0];
		var decimalAmt = opBal.toString().split(".")[1];
		
		var decimalPart =".00";
		if(decimalAmt == null || decimalAmt == undefined){
			$('#opBal0').val(actualAmt+decimalPart);
		}else{
			if(decimalAmt.length <= 0){
				decimalAmt+="00";
				$('#opBal0').val(actualAmt+(".")+decimalAmt);
			}
			else if(decimalAmt.length <= 1){
				decimalAmt+="0";
				$('#opBal0').val(actualAmt+(".")+decimalAmt);
			}else{
				if(decimalAmt.length <= 2){
				$('#opBal0').val(actualAmt+(".")+decimalAmt);
				} 
			  }	
		   }
	    }
	}
  
  function findduplicatecombinationexit(cnt) {
	  
	  //alert("Hi");
		if ($('#count').val() == "") {
			count = 1;
		} else {
			count = parseInt($('#count').val());
		}
		var assign = count;

		$('.error-div').hide();
		var errorList = [];

		//var theForm = '#frmMaster';
		//var requestData = __serializeForm(theForm);
		var requestData = $('#bankMasterForm').serialize();
		
		/*var id =  $("#indexdata").val();
		 
		var pacHeadId = $('#pacHeadId'+id).val();
			 
			 var Revid =  $("#indexdata").val();
				if(pacHeadId != "" ){
					var dec;
				 for(m=0; m<=Revid;m++){
					 for(dec=0; dec<=Revid;dec++){
						 if(m!=dec){
						if($('#pacHeadId'+m).val() == $('#pacHeadId'+dec).val()){
							errorList.push("The Combination AccountHead already exists!");							
							$("#accountNo"+cnt).val("");								
						}
					  }
					} 
				  }
				}
			 */
		
		var  bankName = $("#bankName").val();
		//var  bankBranch = $("#bankBranch").val();
		
		if(bankName == '' || bankName == '0') {
	 		errorList.push("Please Select Bank");
	 		$('#accountNo'+cnt).val("");
	 	}
		
		/*if(bankBranch == '' || bankBranch == '0') {
	 		errorList.push("Please Select Branch");
	 		$('#accountNo'+cnt).val("");
	 	}*/
		
				$('.accountClass')
				.each(
						function(i) {
							
							for (var m = 0; m < i; m++) {
								if (($('#accountNo' + m).val() == $('#accountNo' + i).val())) {
								errorList.push("Account Numbers cannot be same,please enter another one against this same Bank!");
								$("#accountNo"+i).val("");	
						}
					}
				});
				
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
			
			var url = "BankAccountMaster.html?getAccountNumberDuplicate&cnt="+cnt;

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			 if(returnData){
				 
				errorList.push("Account Number is Already Exists, against this same Bank!"); 
				$('#accountNo'+cnt).val("");
				
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			  		$.each(errorList, function(index) {
			  			var errorMsg = '<ul>';
				    	$.each(errorList, function(index){
				    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				    	});
				    	errorMsg +='</ul>';	
				    	$('#errorId').html(errorMsg);
				    	$('#errorDivId').show();
						$('html,body').animate({ scrollTop: 0 }, 'slow');
			  		});
			  	return false;
			}
		}
	};
	
	function exportTemplate() {

		
		var $link = $(this);
		/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
		// var functionId = 1;
		var url = "BankAccountMaster.html?exportTemplateData";
		var requestData = "";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		$('.content').html(returnData);

		prepareDateTag();
		return false;
	}
	function closeOutErrorBox() {
		$('#errorDivSec').hide();
	}
	
	
  