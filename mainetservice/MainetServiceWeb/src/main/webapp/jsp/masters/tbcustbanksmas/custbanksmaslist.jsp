<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/humanity/jquery.ui.all.css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script type="text/javascript">

	var selectedIds = new Array();

	var cmBankid = '';
	
	$(function () {
	    $("#grid").jqGrid({
	        url: "CustbanksMaster.html?getGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("master.custbanksmaslist.BankId"), getLocalMessage("master.custbanksmaslist.Bankname"), getLocalMessage("master.custbanksmaslist.UserId"), getLocalMessage("tbComparamDet.langId"), getLocalMessage("master.custbanksmaslist.LmodDate"), getLocalMessage("tbComparamDet.updatedBy"), getLocalMessage("master.custbanksmaslist.updatedDate"), getLocalMessage("master.selectDropDwn")],
	        colModel: [
	            { name: "cmBankid", width:100,  sortable: true },
	            { name: "cmBankname", sortable: false },
	            { name: "userId",  width:100,  sortable: false },
	            { name: "langId",  width:100,  sortable: false },
	            { name: "lmodDateStr", width:100, sortable: false },
	            { name: "updatedBy", width:100,  sortable: false },
	            { name: "updatedDateStr", width:100, sortable: false },
	            { name: 'cmBankid', index: 'cmBankid', width:60, align: 'center', sortable: false, edittype:'radio',formatter:checkboxFormatter, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },
      			}
	        ],
	        pager: "#pagered",
	        rowNum: 5,
	        rowList: [5, 10, 20, 30],
	        sortname: "cmBankid",
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
	        editurl:"CustbanksMaster.html?update",
	        caption: "Customer Bank Entry List"
	    }); 
	}); 
	
	$(function() {
		
		$("#bedata").click(function(){ 

			var gr = jQuery("#grid").jqGrid('getGridParam','selrow');
			if( cmBankid != '' ) {
				var url = "CustbanksMaster.html?formForUpdate";
				var returnData = "cmBankid="+cmBankid;
				
				$.ajax({
					url : url,
					data : returnData,
					success : function(response) {
						
						var divName = '.form-div';
						
						$("#custBankDiv").html(response);
						$("#custBankDiv").show();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});				
				
			}
			else {
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeBankErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Please select a row</li></ul>';
				$(".warning-div").html(errMsg);
				$(".warning-div").removeClass('hide');
			}
		});
	});
	
	$(function() {
			
		$("#delData").click(function(){
			
			if( cmBankid != '' ) {
				showConfirmBox();
			}
			else {
				
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeBankErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Please select a row</li></ul>';
				
				$(".warning-div").html(errMsg);
				$(".warning-div").removeClass('hide');
			}
			
		});
			
	});
	
	function showConfirmBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = getLocalMessage("message.yes");
		
		message	+='<h5 class=\'text-center padding-horizontal-5 padding-top-10 text-blue-2\'>Are you sure want to delete ?</h5>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-danger \'    '+ 
		' onclick="deleteData()"/>'+	
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function deleteData(){
		
		var url = "CustbanksMaster.html?deleteRecord";
		var returnData = {"cmBankid" : cmBankid};

		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
			data : returnData,
			success : function(response) {
				$.fancybox.close();
				$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				cmBankid = '';
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
		
	}
	
	$(function() {
		
		$("#createData").click(function(){
			$('.warning-div').hide();
			var url = "CustbanksMaster.html?form";
			
			var returnData = "";
			
			$.ajax({
				url : url,
				success : function(response) {					
					$("#custBankDiv").html(response);					
					$("#custBankDiv").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});				
				
		});
			
	});
	
	function checkboxFormatter(cellvalue, options, rowObject) {	
		return "<input type='radio' name='checkboxIsCC' id='checkboxIsCC' onchange='checkboxClick(this);'>";
	}

	function checkboxClick(obj){
		 
		cmBankid = $(obj).closest('tr').find('td:eq(0)').text();
		
		 
	}
	
	function showMsgModalBox(childDialog) {

		$.fancybox({
			type : 'inline',
			href : childDialog,
			openEffect : 'elastic', // 'elastic', 'fade' or 'none'
			helpers : {
				overlay : {
					closeClick : false
				}
			},
			keys : {
				close : null
			}
		});

		return false;
	}
	
	function closeBankErrBox(){
		$('.warning-div').addClass('hide')
	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="custBankDiv">
	<div class="widget">
		<form action="">
		<div class="widget-header">
			<h2>
				<spring:message code="common.master.cust.bank" text="Cust Bank"/> <strong><spring:message code="common.master.custbank.master" text="Master"/></strong>
			</h2>
		</div>
		<div class="warning-div error-div alert alert-danger alert-dismissible hide" id="errorDivCustBankMas2">
			<button type="button" class="close" aria-label="Close" onclick="closeBankErrBox()"><span aria-hidden="true">&times;</span></button>
			<script>				
	  			$('html,body').animate({ scrollTop: 0 }, 'slow');
	  		</script>
		</div>
		<div class="widget-content padding">
		
		<div class="form-group">
		<table id="grid"></table>
		<div id="pagered"></div>
		</div>
		<div class="text-center padding-bottom-20">
			<input type="BUTTON" id="bedata" value="Edit Selected" class="btn btn-success btn-submit" /> 
			<input type="BUTTON" id="delData" value="Delete Selected" class="btn btn-default" /> 
			<input type="BUTTON" id="createData" value="Create Data" class="btn btn-primary" />

		</div>
	</div>
	</form>
	</div>
</div>