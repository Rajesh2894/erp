$(function() {	
	
		$(document).on('click', '.createData', function() {
	
			var url = "BankMaster.html?form";
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
    $(function () {
    $("#grid").jqGrid({
        url: "BankMaster.html?getGridData",
        datatype: "json",
        mtype: "GET",
        colNames: ["",getLocalMessage('accounts.bankfortds.ptbbankcode'),getLocalMessage('accounts.bankfortds.ptbbankname'),getLocalMessage('accounts.bankfortds.ptbbankbranch'),"",getLocalMessage('account.edit'),getLocalMessage('bank.master.view')],
        colModel: [
            { name: "bmBankid", width:100, sortable: true ,hidden:true},
            { name: "bmBankcode", sortable: true },
            { name: "bmBankname", sortable: true },
            { name: "bmBankbranch", sortable: true },
            { name: "orgid", sortable: true,hidden:true },
            { name: 'bmBankid', width:50, index: 'bmBankid', align: 'center', edittype:'radio',formatter:editMst, editoptions: { value: "Yes:No" },
      			formatoptions: { disabled: false },
  			},
  			{ name: 'bmBankid', width:50,  index: 'bmBankid', align: 'center', edittype:'radio',formatter:deleteMst, editoptions: { value: "Yes:No" },
      			formatoptions: { disabled: false },
  			},
  			
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
        editurl:"BankMaster.html?update",
        caption: "Bank Master List"
    }); 
});
   
    $(function() {		
		$(document).on('click', '.editClass', function() {
			var gr = jQuery("#grid").jqGrid('getGridParam','selrow');			
			$("#errorDivServiceCheckList").hide();
			var url = "BankMaster.html?formForUpdate";
		    var bmBankid = $(this).attr('value');
			var orgid=$(this).attr('id');
			var returnData = {"bmBankid" : bmBankid,"orgid"  :orgid};
			
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
    
    $(function() {		
		$(document).on('click', '.deleteClass', function() {
			
			var bmBankid = $(this).attr('value');
			var orgid = $(this).attr('id');
			viewData(bmBankid,orgid);
		});
			
	});
  
	function viewData(bmBankid,orgid){
		
		var url = "BankMaster.html?formForView";
		var returnData = {"bmBankid" : bmBankid,"orgid" :orgid}
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
	 function deleteMst(cellValue, options, rowdata, action) {
	    return "<a href='#'  return false; class='deleteClass' value='"+rowdata.bmBankid+"' id='"+rowdata.orgid+"'><img src='css/images/view.png' width='20px' alt='View Master' title='View Master' /></a>";
	} 
	 function editMst(cellValue, options, rowdata, action) {
		    return "<a href='#'  return false; class='editClass' value='"+rowdata.bmBankid+"' id='"+rowdata.orgid+"'><img src='css/images/edit.png' width='20px' alt='Edit Master' title='Edit Master' /></a>";
		}
	 function searchBankData() {
			var url = "BankMaster.html?searchBank";
			if($("#bankName").val()==0)
				{
				  errMsg = '<ul><li>Please select Bank name</li></ul>';
		             $('#errorId').html(errMsg);
					$('#errorDivId').show();	
			
				}
			else
				{
			var returnData = {
					"bmBankId" 	: $("#bankName option:selected").attr("value")
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
		}
	 function showBankError(errorList)
	 {
		 var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
            errMsg += '</ul>';
             $('#errorId').html(errMsg);
			$('#errorDivId').show();	 
	 }
	 function saveBankMaster(element)
	 {
		 var errorList = [];	
		    var bankType = $.trim($("#bankType").val());
		    if(bankType==0 || bankType=="")
		    errorList.push(getLocalMessage('account.select.bankType'));
		    
		    var bankCode = $.trim($("#bankCode").val());
		    if(bankCode==0 || bankCode=="")
			errorList.push(getLocalMessage('account.enter.valid.bankCode'));
		    
		    var bankName = $.trim($("#bankName").val());
		    if(bankName==0 || bankName=="")
			errorList.push(getLocalMessage('account.enter.bank.name'));
		    
		    var bankBranch = $.trim($("#bankBranch").val());
		    if(bankBranch==0 || bankBranch=="")
			errorList.push(getLocalMessage('account.enter.bank.branch'));
		 
		    
		    $('.accountClass').each(function(i) {
		     var accountNo=$.trim($("#accountNo"+i).val());	
		   	 var accountType = $.trim($("#cpdAccounttype"+i).val());
		     var accountName= $.trim($("#accountName"+i).val());
		     var opBal=$.trim($("#opBal"+i).val());
		     var status=$.trim($("#babStatus"+i).val());
		     var statusCode=($('#babStatus'+i).find("option:selected").attr('code'));
		     var level=i+1;
		   	 if(accountNo==0 || accountNo=="")
	   		 {
	   		    errorList.push(getLocalMessage("account.select.accNo.srNo"+" "+level));
	   		 }
		   	 if(accountType==0 || accountType=="")
		   		 {
		   		    errorList.push(getLocalMessage("account.select.accType.srNo"+" "+level));
		   		 }
		   	 if(accountName==0 || accountName=="")
	   		 {
	   		    errorList.push(getLocalMessage("account.enter.accName.srNo"+" "+level));
	   		 }
		
		   	 if(opBal=="")
	   		 {
	   		    errorList.push(getLocalMessage("account.select.opening.blnc.srNo"+" "+level));
	   		 }
		   	
		   	 if(status==0 || status=="")
	   		 {
	   		    errorList.push(getLocalMessage("account.select.status.srNo"+" "+level));
	   		 }
		   if(statusCode=="C")
		   		 {
			 if(opBal!=""||opBal!=0)
		   		 {
		   		  errorList.push(getLocalMessage("account.closingBlnc.zero.srNo"+" "+level)); 
		   		 }
		   		 
		   		 }
		   $("#closeBal"+i).val(opBal);
		   
		     });
		    
		    if(errorList.length > 0){ 
		    	showBankError(errorList);
		    return false;
		    }
		    var	formName =	findClosestElementId(element, 'form');
		    var theForm	=	'#'+formName;
		    var requestData = __serializeForm(theForm);
		    var url	=	$(theForm).attr('action');
		    var status=__doAjaxRequestForSave(url, 'post', requestData, false,'', element); 
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
				
				} else {

						/*  var msg = getLocalMessage('lgl.cantRemove'); */
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
			    $(this).find("input:text:eq(2)").attr("id", "openingDate" + (i));
			    $(this).find("input:text:eq(3)").attr("id", "opBal" + (i));
			    $(this).find("input:text:eq(4)").attr("id", "closeBal" + (i));
			    $(this).find("input:checkbox:eq(0)").attr("id", "isFundRequired" + (i));
				$(this).find("select:eq(0)").attr("id", "cpdAccounttype" + (i));
				$(this).find("select:eq(1)").attr("id", "babStatus" + (i));
			    $(this).find(".deletClass").attr("id", "deleteAccount" + (i));
			    $(this).find(".viewFund").attr("id", "viewFund" + (i));
				$(this).closest("tr").attr("id", "tr" + (i));
				$(this).closest('tr').find('#srNo').text(i+1);
				
			    //names
				$(this).find("input:text:eq(0)").attr("name", "listOfTbBankaccount[" + (i) + "].baAccountcode");
				$(this).find("input:text:eq(1)").attr("name", "listOfTbBankaccount[" + (i) + "].baAccountname");
				$(this).find("input:text:eq(2)").attr("name", "listOfTbBankaccount[" + (i) + "].baCurrentdate");
				$(this).find("input:text:eq(3)").attr("name", "listOfTbBankaccount[" + (i) + "].listOfTbBankaccountBal[0].babOpeningbalance");
				$(this).find("input:text:eq(4)").attr("name", "listOfTbBankaccount[" + (i) + "].listOfTbBankaccountBal[0].babClosingbalance");
				$(this).find("select:eq(0)").attr("name", "listOfTbBankaccount[" + (i) + "].cpdAccounttype");
			    $(this).find("select:eq(1)").attr("name", "listOfTbBankaccount[" + (i) + "].acCpdId");
			    if(mode=='update')
			    	{
			    	$(this).find("input:hidden:eq(0)").attr("name", "listOfTbBankaccount[" + (i) + "].baAccountid"); 
			      }
			     
				$(this).find("input:checkbox:eq(0)").attr("name", "listOfTbBankaccount[" + (i) + "].fundAplFlag");
				
			     $(this).find(".deletClass").attr("onclick", "removeRow(\"" + (i) + "\",\"" + popupMode + "\")");
			    
			     $(this).find(".viewFund").attr("onclick", "viewFundDetails(\"" + (i) + "\",\"" + popupMode + "\")");
				});

		}
		function copyBal(idcount)
		{
			var opbal=$("#opBal"+idcount).val();
			$("#closeBal"+idcount).val(opbal);
		}
		
		function showConfirmBox()
		{
			  var	errMsgDiv		='.msg-dialog-box';
				var message='';
				var cls = 'Proceed';
				message	+='<p>Bank Master Submitted Successfully</p>';
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
			window.location.href = 'BankMaster.html'; 
		}
		
		function openFundForm(count)
		{
		 var fundRequired = $('#isFundRequired'+count).is(':checked');
		 var requestData = {"count" : count};
	     if(fundRequired)
				 {
				 var url = "BankMaster.html?addFundForm";
					$.ajax({
						url : url,
						data :requestData,
						type : 'GET',
						async : false,
						dataType : '',
					    success : function(response) {
							var divName = '.child-popup-dialog';
							
							$(divName).removeClass('ajaxloader');
							$(divName).html(response);
							//prepareTags();
							showModalBox(divName);
						},
						error : function(xhr, ajaxOptions, thrownError) {
							var errorList = [];
							errorList.push(getLocalMessage("admin.login.internal.server.error"));
							showError(errorList);
						}
					});		
				 }	
		 else
			 {
			 $("#viewFund"+count).prop("disabled",true);
			 }
		}
		
			
		function viewFundDetails(viewCount,popupMode)
		{
			
			 var requestData = {"count" : viewCount,"popupMode" :popupMode};
			 var url = "BankMaster.html?viewFundForm";
				$.ajax({
					url : url,
					data :requestData,
					type : 'GET',
					async : false,
					dataType : '',
				    success : function(response) {
						var divName = '.child-popup-dialog';
						
						$(divName).removeClass('ajaxloader');
						$(divName).html(response);
						//prepareTags();
						showModalBox(divName);
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});		
		}
	
		$(document).ready(function(){
			/*$(".datepicker").datepicker({
			    dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true
			});*/
			$('.lessthancurrdate').datepicker({
				dateFormat: 'dd/mm/yy',	
				changeMonth: true,
				changeYear: true,
				maxDate: '-0d',
				yearRange: "-100:-0"
			});

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
		
		
		function exportTemplate() {

			
			var $link = $(this);
			/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
			// var functionId = 1;
			var url = "GeneralBankMaster.html?exportTemplateData";
			var requestData = "";
			var returnData = __doAjaxRequest(url, 'post', requestData, false);

			$('.content').html(returnData);

			prepareDateTag();
			return false;
		}
		function closeOutErrorBox() {
			$('#errorDivSec').hide();
		}

		