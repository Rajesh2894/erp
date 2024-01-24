
var selectedIds = new Array();

	var dpDeptid = '';
	
	$(function () {
	    $("#grid").jqGrid({
	        url: "Department.html?getGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("master.Id"), getLocalMessage("dept.master.ShortDesc"),getLocalMessage("dept.master.NameEng"),getLocalMessage("dept.master.NameHindi"),getLocalMessage("dept.master.status"),getLocalMessage("dept.master.wzprefix"),
	                   getLocalMessage('master.grid.column.action')],
	        colModel: [
	            { name: "dpDeptid", 
	            	width: 17, 
	            	sortable: true,
	            	sorttype: 'number',
	            	search:	true
	            },
	            { name: "dpDeptcode", 
	            	width: 15, 
	            	sortable: true,
	            	search:true
	            },
	            { name: "dpDeptdesc",
	            	width: 55, 
	            	sortable: true,
	            	search:true
	            },
	            { name: "dpNameMar",
	            	width: 55, 
	            	sortable: true,
	            	search:true
	            },
	            { name: 'status', 
	            	index: 'status',
	            	sortable: true,
	            	width: 12, 
	            	align: 'center', 
	            	edittype:'checkbox',
	            	formatter:statusFormatter, editoptions: { value: "Yes:No" },
	            		formatoptions: { disabled: false },
	            	search:true
	  			},
	  			{ name: "dpPrefix", 
	  				width: 15, 
	  				sortable: true,
	  				search:true 
	  			},
	  			{ name: 'dpDeptid', 
	  				index: 'dpDeptid', 
	  				width: 35, 
	  				align: 'center !important',
	  				sortable: false,
	  				search:false,
	  				formatter:actionFormatter
	  			}
	        ],
	        pager: "#pagered",
	        rowNum: 10,
	        rowList: [5, 10, 20, 30],
	        sortname: "dpDeptdesc",
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
	        editurl:"Department.html?update",
	        caption: getLocalMessage("dept.master.gridCap")       	
	}); 
	    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : true});
		$("#pagered_left").css("width", "");
	});
	
	function statusFormatter(cellvalue, options, rowdata) {	
				if(rowdata.status == 'A'){
					return "<a title='Department is Active' alt='Department is Active' value='A' class='fa fa-check-circle fa-2x green' href='#'></a>";	
				}else{
					return "<a title='Department is Inactive' alt='Department is Inactive' value='A' class='fa fa-times-circle fa-2x red' href='#'></a>";
				}	
		}
	function actionFormatter(cellvalue, options, rowObject){
		if($('#orgDefStatus').val() == 'Y'){
			return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewDept('"+rowObject.dpDeptid+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a> " +
			 "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editDept('"+rowObject.dpDeptid+"')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> "; 
	  /*  "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"deleteDept('"+rowObject.dpDeptid+"')\"><i class='fa fa-trash' aria-hidden='true'></i></a>";*/
		}else{
			return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewDept('"+rowObject.dpDeptid+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a> ";
		}
		
	}	

		
		function editDept(dpDeptid) {

			var url = "Department.html?formForUpdate";
			var requestData = "dpDeptid="+dpDeptid;
			
			$.ajax({
				url : url,
				data : requestData,
				success : function(response) {
					
					var divName = '.widget';
					$("#deptDetDiv").html(response);
					$("#deptDetDiv").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showDepError(errorList);
				}
			});	
		};
		
		function deleteDept(dpDeptid) {
			
			
			var url = "Department.html?delete";
			
			var requestData = "dpDeptid="+dpDeptid;
			var returnCheck = checkForTransaction(requestData);
			if(returnCheck > 0){
				$(errMsgDiv).html("<h4 class=\"text-center text-blue-2 padding-10\">Transaction Exists, cannot delete.</h4>");
				$(errMsgDiv).show();
				showMsgModalBoxDep(errMsgDiv);
				return false;
			}else{
				$.ajax({
					url : url,
					data : requestData,
					success : function(response) {	
						if(response == 'success') {
							showConfirmBoxDep();
							$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
							dpDeptid = '';
						}
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showDepError(errorList);
					}
				});
			}
		};
		
			
			function viewDept(deptId) {
				
				var url = "Department.html?getViewForm";
				var requestData = "deptId="+deptId;
				var returnData = __doAjaxRequest(url, 'post', requestData, false);
				$('#deptDetDiv').html(returnData);
		
			};		
		

	 function checkForTransaction(requestData){
		 var checkUrl = "Department.html?checkIfDepOrgMapExists";
		 var returnCheck=__doAjaxRequest(checkUrl, 'post',requestData, false,'');
		 return returnCheck;
	 }
			
	function proceed() {
		window.location.href='Department.html';
	}
	
	function showConfirmBoxDep(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">Status Changed to Inactive Successfully</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="proceed()"/>'+
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showMsgModalBoxDep(errMsgDiv);
	}
	
	
	$(function() {
		$("#createData").click(function(){
			$(".error-div").html('');
			var url = "Department.html?form";
		
			var returnData = "";
			$.ajax({
				url : url,
				success : function(response) {
				
					$("#deptDetDiv").html(response);
					$("#deptDetDiv").show();
					$('.error-div').hide();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showDepError(errorList);
				}
			});				
		});
	});
	

	function showMsgModalBoxDep(childDialog) {
		$.fancybox({
			type : 'inline',
			href : childDialog,
			openEffect : 'elastic',
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
	
	function closeOutErrBox(){
		$("#errorDivDeptMas").hide();
	}
	
	function searchDeptData() {
		
		$("#errorDivDeptMas").hide();
		var errorList = [];
		var url = "Department.html?searchDeptData";
		var deptId=$("#deptId").val();
	    var deptCode=$("#deptCode").val().toUpperCase();
	   
		var requestData = {
				"department" : deptId,
				"deptCode" 	 : deptCode
		}
		
		if(deptId != "" || deptCode != ""){
			$.ajax({
				url : url,
				data:requestData,
				success : function(response) {
					if(response != null && response != ""){
						errorList.push(getLocalMessage(response));
						showDepError(errorList);
					}
					$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				},
				error : function(xhr, ajaxOptions, thrownError) {
					
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showDepError(errorList);
				}
			});
		}else{
			errorList.push(getLocalMessage("department.error.depIdandDepCode"));
			showDepError(errorList);
		}

	}
	
	function resetPage() {
		
		window.location.href = 'Department.html';
	};
	
	function showDepError(errorList){
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorDivDeptMas").html(errMsg);
		$("#errorDivDeptMas").show();
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
