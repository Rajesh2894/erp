 var tdsId = '';
$(function() {
		$("#grid").jqGrid(
				{
					url : "AccountTDSTaxHeadsMaster.html?getGridData",
					datatype : "json",
					mtype : "POST",
					colNames : [ '', getLocalMessage('accounts.tds.type'),getLocalMessage('accounts.master.status'),getLocalMessage('bill.action')],
					colModel : [ {name : "tdsId",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
					             {name : "tdsDescription",width : 75,sortable : false,searchoptions: { "sopt": [ "eq"] }}, 
					             {name : "statusDescription",width : 75,sortable : false,searchoptions: { "sopt": [ "eq"] }}, 
					             { name: 'tdsId', index: 'tdsId', width:20 , align: 'center !important',formatter:addLink,search :false},
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
					caption : "Statutory Deductions Mapping Masters List"
				});
		 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
		 $("#pagered_left").css("width", "");
	});


	function returnEditUrl(cellValue, options, rowdata, action) {
		tdsId = rowdata.tdsId;
	return "<a href='#'  return false; class='editClass' value='"+tdsId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
	}

	function returnViewUrl(cellValue, options, rowdata, action) {

		return "<a href='#'  return false; class='viewClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
	}
	
	function addLink(cellvalue, options, rowdata) 
	 {
	    return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.tdsId+"' id='"+rowdata.tdsId+"' ><i class='fa fa-building-o'></i></a> " +
	    		 "<a class='btn btn-warning btn-sm editClass' title='Edit'value='"+rowdata.tdsId+"' id='"+rowdata.tdsId+"' ><i class='fa fa-pencil'></i></a> ";
	 }
	
	
	
	
	
	
	
	
	 $(function() {
			$(document).on('click', '.createData', function() {
				
				var url = "AccountTDSTaxHeadsMaster.html?form";
				var requestData ="MODE_DATA=" + "EDIT";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				$('.content').html(returnData);
				prepareDateTag();
				return false;			
			});			
		});
	 
		$(function() {
			$(document).on('click', '.editClass', function() {
				var $link = $(this);
				var tdsId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountTDSTaxHeadsMaster.html?formForUpdate";
				var requestData = "tdsId=" + tdsId + "&MODE_DATA=" + "EDIT";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				
				$('.content').html(returnData);
				
				prepareDateTag();
					
			});
		});
		
		$(function() {
			$(document).on('click', '.viewClass', function() {
				var $link = $(this);
				var tdsId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountTDSTaxHeadsMaster.html?formForView";
				var requestData = "tdsId=" + tdsId + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				
				$('.content').html(returnData);
				
				prepareDateTag();
					
			});
		});
		
		$(document).on('click', '.resetSearch', function() {
			$('#budgetCodeId').val('').trigger('chosen:updated');
			
		});	
		
		
	// to generate dynamic table
	 $("#taxHeadsTable").on("click", '.addButton',function(e) {
		 var errorList = [];
		 $('.appendableClass tr').each(function(i) {
			 row = i-1;
		});
		 errorList = chequeDuplicateBudgetCode(errorList);
		 var statusValue = $("#status"+row+" option:selected").text();
		 if(statusValue == 'Active' ){
				$('#addButtonId'+row).prop( "disabled", true );
				}
		 else{
				$('#addButtonId'+row).prop( "disabled", false );
				$('#delButtonId'+row).prop( "disabled", false );
			}
		 var budgetCode = $.trim($("#budgetCode"+row).val());
		 if(budgetCode==0 || budgetCode=="")
		    errorList.push(getLocalMessage('account.bill.entry.budget.code'));
		 
		 var taxDesc = $.trim($("#cpdIdDeductionType").val());
		 if(taxDesc==0 || taxDesc=="")
		    errorList.push(getLocalMessage('account.select.tdsType'));
		 
		 if(errorList.length > 0){ 
			 
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				});

				errMsg += '</ul>';

				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
		} 
		 
		 
		 e.preventDefault();
		 $(".datepicker").datepicker("destroy");
		
		 var content = $(this).closest('#tdsTaxHeadsTable tr').clone();
		 $(this).closest("#tdsTaxHeadsTable").append(content);
		 // reset values
		 content.find("select").attr("value", "");
		 content.find("input:hidden:eq(0)").attr("value", "");
		 content.find("input:text").val("");
		 content.find('label').closest('.error').remove(); //for removal duplicate
		 content.find("select:eq(0)").attr("id","budgetCode" + (row)); 
		 content.find("select:eq(1)").attr("id","status" + (row)); 
		 content.find("input:hidden:eq(1)").attr("id", "statusHidden"+(row));
		 content.find("select:eq(1)").attr("onchange","setHiddenStatusField("+row+")");
		 content.find("select:eq(0)").attr("name","taxHeadsDtoList["+row+"].budgetCodeId"); 
		 content.find("select:eq(1)").attr("name","taxHeadsDtoList["+row+"].status");
		 content.find("input:hidden:eq(1)").attr("name","taxHeadsDtoList["+row+"].status");
		 content.find('.delButton').attr("id", "delButtonId"+ (row));
		 content.find('.addButton').attr("id", "addButtonId"+ (row));
		 content.find('div.chosen-container').remove();
		 content.find("select:eq(0)").chosen().trigger("chosen:updated");

		 reOrderTableIdSequence();
		 
	 });
	
	// to delete row
	 $("#taxHeadsTable").on("click", '.delButton', function(e) {
	 	
	 	var rowCount = $('#taxHeadsTable tr').length;
	 	if(rowCount<=2){
	 		return false;
	 	}
	 	
	 	 $(this).closest('#taxHeadsTable tr').remove();

	 	 	reOrderTableIdSequence();
	 	 	e.preventDefault();
	 });		
	
	
	function reOrderTableIdSequence(){
	
		 $('.appendableClass tr').each(function(i) {
			i=i-1;
			 $(".datepicker").datepicker("destroy");
			 $(this).find('div.chosen-container').attr('id',"budgetCode"+i+"_chosen");
			 $(this).find("select:eq(0)").attr("id","budgetCode"+i); 
			 $(this).find("select:eq(1)").attr("id","status"+i); 
			 $(this).find("input:hidden:eq(1)").attr("id","statusHidden"+i); 
			 $(this).find("select:eq(1)").attr("onchange","setHiddenStatusField("+i+")");
			 $(this).find("select:eq(0)").attr("name","taxHeadsDtoList["+i+"].budgetCodeId"); 
			 $(this).find("input:hidden:eq(1)").attr("name","taxHeadsDtoList["+i+"].status");
			 $(this).find("select:eq(1)").attr("name","taxHeadsDtoList["+i+"].status"); 
			 $(this).find('.delButton').attr("id", "delButtonId"+i);
			 $(this).find('.addButton').attr("id", "addButtonId"+i);
		 });
	}
	 
function saveTaxHeadForm(element){
	var errorList = [];
			var url="AccountTDSTaxHeadsMaster.html?create";
			var	formName =	findClosestElementId(element, 'form');
		    var theForm	=	'#'+formName;
		    var requestData = __serializeForm(theForm);
		    var status= __doAjaxRequestValidationAccor(element,url, 'post', requestData, false, 'html');
		    if(status != false){
		    	 var obj=$(status).find('#successfulFlag');
				    if($('.content').html(status))
					{
				    	if(obj.val()=='Y'){
				    		showConfirmBox();
				    	}
					}
				 else
					{
					 $(".widget-content").html(status);
				     $(".widget-content").show();
					}
		    }
		
}

function showConfirmBox()
{
	  var	errMsgDiv		='.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		 message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+"Form Submitted Successfully"+'</h5>';
		 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);

}


function proceed() {
	window.location.href = 'AccountTDSTaxHeadsMaster.html'; 
}



function searchTDSData(){
	var url = "AccountTDSTaxHeadsMaster.html?searchTDSData";
	var requestData = {
			"accountHead" : $("#budgetCodeId option:selected").attr("value"),
			"tdsType" : $("#cpdIdDeductionType").val(),
			"status" : $("#status").val()
	};
	
	var result=__doAjaxRequest(url,'POST',requestData,false,'json');	
	if (result !=null && result !='') {
		$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("account.norecord.criteria"));
		if(errorList.length > 0){ 
			 
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
			errMsg += '</ul>';
			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			//return false;
	}
		$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	}
}

function validateDuplicate(){
	
	var errorList = [];
	 $('.tdsClass').each(function(i) {
		 for(var m=0; m<i;m++){
				if(($('#pacHeadId'+m).val() == $('#pacHeadId'+i).val())){
					 errorList.push("Account Heads cannot be same,please select another Account Head");
				}
			}
		
		 });
	 
	 if(errorList.length > 0){ 
		 
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			return false;
	} 
	
}
	
function getAccountHead(count){
	
	 var budgetCode = $('#budgetCode'+count).val();
	 var errorList = [];
	 errorList = chequeDuplicateBudgetCode(errorList);
	 
	
	 if(errorList.length > 0){ 
		 
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			return false;
	} 
	 else{
	 
	 
	 	var postdata = 'budgetCode=' + budgetCode;
	 	var skeyData = '';
	 	var svalueData='';
		
		var response = __doAjaxRequest('AccountTDSTaxHeadsMaster.html?getAccountHeadsByBudgetCode', 'POST', postdata, false, 'json');
		
		$.each(response, function( key, value ) {
			skeyData = key;
			svalueData =  value;
		});
		$('#pacHeadIdHidden' + count).val(skeyData);
		$('#pacHeadId' + count).val(svalueData);
	
	 }
}	



function chequeDuplicateBudgetCode(errorList){
	var updateModeId = $("#updateModeId").val();
	var statusId=''; 
	 $('.tdsClass').each(function(i) {
		 
		 statusId = $("#statusHidden"+i).val();
	 });
	
	if((updateModeId !='update' && statusId != '115') || (updateModeId =='update' && statusId != '115')){
	
	 $('.tdsClass').each(function(i) {
		 for(var m=0; m<i;m++){
				if(($('#budgetCode'+m).val() == $('#budgetCode'+i).val())){
					 errorList.push("Budget Heads cannot be same,please select another Budget Code");
				}
			}
		
		 });
	}
	return errorList;
	
}



function setHiddenStatusField(count){
	var statusId = $("#status"+count).val();
	$("#statusHidden"+count).val(statusId);
	var statusValue = $("#status"+count+" option:selected").text();
	if(statusValue == 'Inactive' ){
		$('#addButtonId'+count).prop( "disabled", false );
		$('#delButtonId'+count).prop( "disabled", false );
		}
	else{
		$('#addButtonId'+count).prop( "disabled", true );
		$('#delButtonId'+count).prop( "disabled", true );
	}
	
}
	
	