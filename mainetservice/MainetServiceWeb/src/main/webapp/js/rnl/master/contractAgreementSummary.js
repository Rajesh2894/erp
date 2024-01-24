var contractUrl='ContractAgreement.html';

$(document).ready(function(){

	$('#addEstateLink').click(function() {
	var ajaxResponse = __doAjaxRequest(contractUrl+'?form', 'POST', {}, false,'html');
		$('.content').html(ajaxResponse);
	 });
	
});

$(document).ready(function(){
$("#contractGrid").jqGrid(
		{   
			url : contractUrl+"?getGridData",
			datatype : "json",
			mtype : "POST",
			colNames : [ '',getLocalMessage('rnl.master.contract.no'),getLocalMessage('rnl.master.contract.date'),getLocalMessage('master.department'), getLocalMessage('rnl.master.represented.by'),getLocalMessage('rnl.master.vender.name'),getLocalMessage('rnl.master.contract.from.date'), getLocalMessage('rnl.master.contract.to.date'),getLocalMessage('estate.grid.column.action')],
			colModel : [ {name : "contId",width : 5,  hidden :  true   },
			             {name : "contNo",width : 30,align : 'center' },
			             {name : "contDate",width : 20,align : 'center',sortable :  true},
			             {name : "contDept",width : 30,align : 'center',sortable :  false}, 
			             {name : 'contp1Name',width : 30,align : 'center',sortable :  false,search :false}, 
			             {name : 'contp2Name',width : 30,align : 'center',sortable :  false,search :false},
			             {name : "contFromDate",width : 20,align : 'center',sortable :  false,search :false}, 
			             {name : "contToDate",width : 20,align : 'center',sortable :  false,search :false},				            
			             {name: 'enbll', index: 'enbll', width: 20, align: 'center !important',formatter:addLink,search :false}
			            ],
			pager : "#estatePager",
			rowNum : 30,
			rowList : [ 5, 10, 20, 30 ],
			sortname : "contId",
			sortorder : "desc",
			height : 'auto',
			viewrecords : true,
			gridview : true,
			loadonce : true,
			postData : {    
				contracNo:function() {
					   return $('#contractNo').val();
				},
				contracDate:function() {
					   return $('#contractDate').val();
				},
				venderId:function() {
					   return $('#vendorId').val();
				},	
				deptId : function() {
					  return $('#departmentId').val();
				},
				viewClosedCon : function() {
					  return $('#viewClosedContracts').val();
				}
			 },
			jsonReader : {
				root : "rows",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false,					
			},
			autoencode : true
		});

function addLink(cellvalue, options, rowObject) 
{
   return "<a class='btn btn-blue-3 btn-sm' title='View Properties' onclick=\"showContract('"+rowObject.contId+"','V')\"><i class='fa fa-eye'></i></a> "+
           "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"showContract('"+rowObject.contId+"','E')\"><i class='fa fa-pencil'></i></a> ";
}


$('#btnsearch').click(function(){
   $("#contractGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
});

});

function showContract(contId,type){		
	if(type == 'V'){

	    showContractForm(contId,type);
	   	 $("#ContractAgreement :input").prop("disabled", true);
		 $('.addCF3').attr('disabled',true);
		 $('.addCF4').attr('disabled',true);
		 $('.addCF5').attr('disabled',true);
		 $('.addCF2').attr('disabled',true);
		 $('.remCF2').attr('disabled',true);
		 $('.remCF3').attr('disabled',true);
		 $('.remCF4').attr('disabled',true);
		 $('.remCF5').attr('disabled',true);
		 $('.uploadbtn').attr('disabled',false);
		 $("#backButton").removeProp("disabled");
	}
	
	if(type == 'E'){
		
	    var requestData = 'contId='+contId+'&type='+type;
		var ajaxResponse	=	doAjaxLoading(contractUrl+'?findContractMapedOrNot', requestData, 'html');
		if(ajaxResponse!='"Y"')
			{
			    showContractForm(contId,type);
				$("#resetButton").prop("disabled", true);
			   	$("#AgreementDate").prop('disabled',true);
			}
		else
			{
				showAlertBox();	
			}
	}
}


function showContractForm(contId,type){
    var requestData = 'contId='+contId+'&type='+type;
	var ajaxResponse	=	doAjaxLoading(contractUrl+'?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);	
	}
	

	function showAlertBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Ok';
		
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">This Contract is  already maped.Edit not allowed.</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="closeAlertForm()"/>'+
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		showModalBox(errMsgDiv);
	}
	
