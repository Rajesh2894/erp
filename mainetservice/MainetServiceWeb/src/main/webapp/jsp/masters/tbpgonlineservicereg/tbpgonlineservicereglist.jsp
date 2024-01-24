

<script type="text/javascript">

	var selectedIds = new Array();
	var olserId = '';
$(function () {
	var orgid = '';
	    $("#grid").jqGrid({
	        url: "TbPgOnlineservicereg.html?getGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("master.tborganisationadddetform.olserId"),getLocalMessage("master.tborganisationadddetform.MerchantName"),getLocalMessage("master.tborganisationadddetform.ServiceId"),getLocalMessage("workflow.eventMaster.form.table.column.serviceURL"),getLocalMessage("master.selectDropDwn")],
	        colModel: [
	            { name: "olserId", width: 35, sortable: true },
	            { name: "merchantName", width: 100, sortable: false },
	            { name: "smServiceId", width: 35, sortable: false },
	            { name: "serUrl", width: 35, sortable: false },
	            { name: 'enbl', index: 'enbl', width: 30, align: 'center', edittype:'radio',formatter:checkboxFormatter, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },
      			}
	        ],
	        pager: "#pagered",
	        rowNum: 5,
	        rowList: [5, 10, 20, 30],
	        sortname: "olserId",
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
	        editurl:"TbPgOnlineservicereg.html?update",
	        caption: "Online service List"
	    }); 
	}); 
	
	$(function() {
		
		$("#bedata").click(function(){ 

			var gr = jQuery("#grid").jqGrid('getGridParam','selrow');
			
			if( olserId != '' ) {
				
				var url = "TbPgOnlineservicereg.html?formForUpdate";
				
				var returnData = "olserId="+olserId;
				
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
				
				var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="32"/></div><ul><li>Please select a row</li></ul>';
				$("#errorDivOnlineService").html(errMsg);
				$("#errorDivOnlineService").show();
			}
		});
	});
	
	$(function() {
			
		$("#delData").click(function(){
			if( olserId != '' ) {
				showConfirmBox();
			}else {
				
				var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="32"/></div><ul><li>Please select a row</li></ul>';
				$("#errorDivOnlineService").html(errMsg);
				$("#errorDivOnlineService").show();
			}
			
		});
			
	});
				
$(function() {
		
		$("#createData").click(function(){
			$('.error-div').hide();
			var url = "TbPgOnlineservicereg.html?form";
			
			var returnData = "";
			
			$.ajax({
				url : url,
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
				
		});
			
	});
	
	function checkboxFormatter(cellvalue, options, rowObject) {	
		return "<input type='radio' name='checkboxIsCC' id='checkboxIsCC' onchange='checkboxClick(this);'>";
	}

	function checkboxClick(obj){
		 
		olserId = $(obj).closest('tr').find('td:eq(0)').text();
		
		 
	}
	
	
	function showConfirmBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Yes';
		
		message	+='<p>Are you sure want to delete ?</p>';
		 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
		'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
		' onclick="deleteData()"/>'+	
		'</p>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function deleteData(){
		
		var url = "TbPgOnlineservicereg.html?delete";
		
		var returnData = "olserId="+olserId;
		
		$.ajax({
			url : url,
			data : returnData,
			success : function(response) {
				$.fancybox.close();
				$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
	}
	
	function closeOutErrBox(){
		$('.error-div').hide();
	}
</script>

<h2><a href="index.jsp" style="color: red; text-decoration: none; border-bottom: 1px solid green;">Home</a></h2>
<br/>
<br/>
<div class="error-div" style="display:none;" id="errorDivOnlineService"></div>
<div id="custBankDiv">
	<table id="grid"></table>
	<div id="pagered"></div> 
	<div class="btn_fld padding_10 clear">
	
		<input type="BUTTON" id="bedata" value="Edit Selected" class="css_btn"/>
		<input type="BUTTON" id="delData" value="Delete Selected" class="css_btn"/>
		<input type="BUTTON" id="createData" value="Create Data" class="css_btn"/>
		
	</div>
</div>