var clmId = -1;
$(function () {
	    $("#grid").jqGrid({
	        url: "DocumentGroupMaster.html?getGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("master.docName"),getLocalMessage("master.docGroup.docNameReg"),getLocalMessage("master.docGroup.docType"),getLocalMessage("master.docGroup.docTypeReg"),getLocalMessage("master.docGroup.docSize"),getLocalMessage("workflow.form.select.mandatory"),getLocalMessage("master.docGroup.sequence"),getLocalMessage("master.docGroup.isRequired"),getLocalMessage("master.docGroup.prefixName")],
	        colModel: [
	            { name: "docName", width:100, sortable: true },
	            { name: "docNameReg", width:60, sortable: true },
	            { name: "docType", width:60, sortable: true },
	            { name: "docTypeReg", width:60, sortable: true },
	            { name: "docSize", width:60, sortable: true },
	            { name: "docMandatory", width:60, sortable: true },
	            { name: "docSrNo", width:60, sortable: true,search :false },
				{ name: "docPrefixRequired", width:60, sortable: true,search :false },
				{ name: "prefixName", width:60, sortable: true,search :false },
	   	        ],
	   	     pager: "#pagered",
	         rowNum: 30,
	         rowList: [5, 10, 20, 30],
	         sortname: "bmBankid",
	         sortorder: "desc",
	         height:'auto',
	         viewrecords: true,
	         gridview: true,
	         showrow: true,
	         loadonce: true,
	         jsonReader : {
                root: "rows",
                page: "page",
                total: "total",
                records: "records", 
                repeatitems: false,
               }, 
	        autoencode: true, 
	        caption: getLocalMessage("master.docGroup.gridheader")
	    }); 
	    jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
		$("#pagered_left").css("width", "");
	});

	
	function editChecklistMst(cellValue, options, rowdata, action) {
		var myVar = $("#groupId").val();
	    return "<a href='#'  return false; class='editClass' value='"+myVar+"'><img src='css/images/edit.png' width='20px' alt='Edit Checklist Master' title='Edit Checklist Master' /></a>";
	}
		
	$(function() {		
		$(document).on('click', '.editClass', function() {
		
			var gr = jQuery("#grid").jqGrid('getGridParam','selrow');				
			var groupId = parseInt($("#groupId").val());
			$("#errorDivServiceCheckList").hide();
			var url = "DocumentGroupMaster.html?formForUpdate";
			var string = $(this).attr('value');
			
			var errorList = [];
			
			if(groupId == undefined ||groupId.toString()=='NaN') {
				errorList.push(getLocalMessage('master.docGroup.valid.searchDocumentGrp'));  
			}
			
			if(errorList.length == 0){
				
			var returnData = {"groupId":groupId};
			
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
			}else{
				
			}
		});
	});
	
	$(function() {	
		$(document).on('click', '.createData', function() {
			 var groupId = parseInt($("#groupId").val());
			  createDocumentData(groupId);
		});			
	});
	
function resetDocumentForm(){
	    var groupId = parseInt($("#groupCpdId").val());
	    createDocumentData(groupId);
	}
		

function createDocumentData(groupId){
    var url = "DocumentGroupMaster.html?form";
	var returnData = {"groupId":groupId};
	var errorList = [];
	
	if(groupId == undefined ||groupId.toString()=='NaN') {
		errorList.push(getLocalMessage('master.docGroup.valid.searchDocumentGrp'));  
	}
	if(errorList.length == 0){
	$.ajax({
		url : url,
		success : function(response) {					
			var divName = '.form-div';					
			data : returnData,
			$(".widget-content").html(response);
			$(".widget-content").show();
			
			var grpId = document.getElementById('groupCpdId');
			   grpId.value = groupId;	
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});		
	}
	else{
	}
}


	function showMsgModalBox(childDialog) {
		
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
		$('.error-div').hide();
	}
	
var noOfTotRow = 0;
	
	$(document).ready(function(){
		
		$("#editGrpButton").hide();
		$("#createGrpButton").hide();
		
		if($("#formModeId").val() == 'update') {			
		   $("#groupCpdId").attr("disabled", true);
		}
		});
	
	function showMsgModalBox(childDialog) {
	
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
		$('.error-div').hide();
	}
	
	$("#checklistMst").on("click", '.addChecklistLink',function(e) {
		 var cnt = $('#checklistMst tr').length - 1;
		 				
				var errorList = [];
				var row=0;
				
				var docNameCounter = 0;
				var docTypeCounter = 0;
				var docSizeCounter = 0;
				var ccmValuesetCounter = 0;
				var prefixReqCounter = 0;
				var prefixNameCounter = 0;
				
				$('.appendableClass').each(function(i) {
					/*row = i;	*/				
					row=cnt;
					var docName = $("#docName" + i).val();
					var docNameReg = $("#docNameReg" + i).val(); 
					var docType = $("#docType" + i).val();
					var docTypeReg = $("#docTypeReg" + i).val();					
					var docSize = $("#docSize" + i).val();
					var ccmValueset = $("#ccmValueset" + i).val();
					var dgId = $("#dgId" + i).val();
					var prefixReq = $("#docPrefixRequired" + i).val();
					var prefixName = $("#prefixName" + i).val();
					
					if(docName== ''){
						if(docNameCounter == 0) {
							 errorList.push(getLocalMessage('master.docGroup.valid.enterDocName')); 
							docNameCounter++;
						}
					}
					if(docType== ''){
						if(docTypeCounter == 0) {
							errorList.push(getLocalMessage('master.docGroup.valid.enterDocType'));
							docTypeCounter++;
						}
					}
					

					if(docNameReg== ''){
						if(docNameCounter == 0) {
							 errorList.push(getLocalMessage('master.docGroup.valid.enterDocNameReg')); 
							docNameCounter++;
						}
					}
					if(docTypeReg== ''){
						if(docTypeCounter == 0) {
							errorList.push(getLocalMessage('master.docGroup.valid.enterDocTypeReg'));
							docTypeCounter++;
						}
					}
					
					if(docSize== ''){
						if(docSizeCounter == 0) {
							errorList.push(getLocalMessage('master.docGroup.valid.enterDocSize'));
							docSizeCounter++;
						}
					}
					
					if(ccmValueset== ''){
						if(ccmValuesetCounter == 0) {
							errorList.push("master.docGroup.valid.selectDd");
							ccmValuesetCounter++;
						}
					}
					
					if(prefixReq== ''){
						if(prefixReqCounter == 0) {
							errorList.push(getLocalMessage('master.docGroup.valid.selectDd'));
							ccmValuesetCounter++;
						}
					}
					if(prefixReq== 'Y') {
						if(prefixName == ''){
								errorList.push(getLocalMessage('master.docGroup.valid.selectPrefixName'));
								prefixNameCounter++;
						}
					}
					if(prefixReq== 'N') {
						prefixName = '';
					}
			
					});
				
				noOfTotRow = row;
				
				if(errorList.length > 0){
					var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

					$.each(errorList, function(index) {
						errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
					});

					errMsg += '</ul>';
					$(".warning-div").html(errMsg);
					$(".warning-div").removeClass('hide');
					$('html,body').animate({ scrollTop: 0 }, 'slow');
					return false;
					
				} else {
					$("#errorDivChecklistMas").addClass('hide');
				}
				
				e.preventDefault();
		
				var clickedRow = $(this).parent().parent().index();	 
				
				/*if(noOfTotRow > 0 && clickedRow < noOfTotRow){
					var errMsg = '<button type="button" class="close" aria-label="Close"   src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul><li><i class="fa fa-exclamation-circle"></i>&nbsp;Can not able to add row in middle</li></ul>';
					$(".warning-div").html(errMsg);
					$(".warning-div").removeClass('hide');					
					$('html,body').animate({ scrollTop: 0 }, 'slow');
					return false;
				}else{
					noOfTotRow++;
				}*/
					 
				noOfTotRow++;
				var content = $('#checklistMst tr').last().clone();
				$('#checklistMst tr').last().after(content);
				//$(this).closest("tr").after(content);
				
				var docSrNo = parseInt($("#docSrNo" + clickedRow).val()) + 1;				
			
				content.find("input:text").attr("value","");
				content.find("input:hidden").attr("value","");
				content.find("select").attr("selected", "selected").val('');
				
				content.find("input:text").val('');
				content.find("select").attr("selected", "selected").val('');
								
				//Ids
			/*	content.find("input:text:eq(0)").attr("id", "docName" + (row + 1));
				content.find("input:text:eq(1)").attr("id", "docType" + (row + 1));
				content.find("input:text:eq(2)").attr("id", "docSize" + (row + 1));
				content.find("input:text:eq(3)").attr("id", "docSrNo" + (row + 1));
				
			 	content.find("input:hidden:eq(0)").attr("id", "dgId" + (row + 1));         
				
				//names
				content.find("input:text:eq(0)").attr("name", "docGroupList[" + (row + 1) + "].docName");
				content.find("input:text:eq(1)").attr("name", "docGroupList[" + (row + 1) + "].docType");	
				content.find("input:text:eq(2)").attr("name", "docGroupList[" + (row + 1) + "].docSize")
				content.find("input:text:eq(3)").attr("name", "docGroupList[" + (row + 1) + "].docSrNo");
				
			    content.find("input:hidden:eq(0)").attr("name", "docGroupList[" + (row + 1) + "].dgId").attr("value", "");  */
			    
		//	    $('#checklistMst tr').last().after('<tr id="tr"'+cnt);
			
				reOrderCheckListGrid();
	});
	
	$("#checklistMst").on("click", '.deleteChargesLink', function(e) {
		 var errorList = [];
		var counter = 0;
		$('.appendableClass').each(function(i) {
			counter+=1;
		});
		var rowCount = $('#checklistMst tr').length;
		if (rowCount <= 2) {
         errorList.push(getLocalMessage("master.first.row.not.delete"));
		}
		if(errorList.length > 0){	
		var errMsg = '<button type="button" class="close" aria-label="Close"   src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul><li><i class="fa fa-exclamation-circle"></i>&nbsp;First row can not be removed </li></ul>';
		$(".warning-div").html(errMsg);
		$(".warning-div").removeClass('hide');					
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		return false;
	}
		$(this).parent().parent().remove();
		noOfTotRow--;
		
		reOrderCheckListGrid();
		
	});
	
	

	
	
	function validateGroup(obj) { 
		var groupCpdId = $("#groupCpdId").val();
		var validateUrl = "DocumentGroupMaster.html?validateGroup" ;
		var formMode = $("#formModeId").val();
		
			var reqData = {
				"groupCpdId" 		: groupCpdId,
		};		
		var returnData = 0;		
		if(formMode == 'create') {
			returnData =__doAjaxRequestForSave(validateUrl, 'post', reqData, false,'', obj);
		}
		
		if(returnData != 0) {
			
			var errMsg = '<button type="button" class="close" aria-label="Close"   src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul><li class="fa fa-exclamation-circle">Document Group already present</li></ul>';
			$(".warning-div").html(errMsg);
			$(".warning-div").removeClass('hide');
			$("#submitBtnId").prop('disabled', false);
			$("#groupCpdId").val('');
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			return false;
		}
	}
	
	function submitDocMasterForm(obj) {
		var groupCpdId = $("#groupCpdId").val();
		
		$('#hiddenGrpCpdId').val(groupCpdId);
		var errorList = [];
		var docSrNoList = [];
		var docNameCounter = 0;
		var docTypeCounter = 0;
		var docSizeCounter = 0;	
		var ccmValuesetCounter = 0;
		var prefixReqCounter = 0;
		var prefixNameCounter = 0;
	
		if(groupCpdId == '') {
			 errorList.push(getLocalMessage('master.docGroup.valid.selectDocumentGrp'));  
		}
		var count=0;
		
		$('.appendableClass').each(function(i) {
			row = i;
			
	
			var docName = $("#docName" + i).val();
			var docNameReg = $("#docNameReg" + i).val(); 
			var docType = $("#docType" + i).val();
			var docTypeReg = $("#docTypeReg" + i).val();
			var docSize = $("#docSize" + i).val();	
			var ccmValueset = $("#ccmValueset" + i).val();
			var ccmValuesets = $("#ccmValueset" + i).find("option:selected").text();
			var prefixReq = $("#docPrefixRequired" + i).val();
			var prefixName = $("#prefixName" + i).val();
			
			var docSrNo=$("#docSrNo" + i).val();
			docSrNoList.push(docSrNo);
			
			if(docName== ''){
				if(docNameCounter == 0) {
					 errorList.push(getLocalMessage('master.docGroup.valid.enterDocName'));  
					docNameCounter++;
				}
			}
			if(docType== ''){
				if(docTypeCounter == 0) {
					errorList.push(getLocalMessage('master.docGroup.valid.enterDocType'));
					docTypeCounter++;
				}
			}
			
			
			if(docNameReg== ''){
				if(docNameCounter == 0) {
					 errorList.push(getLocalMessage('master.docGroup.valid.enterDocNameReg'));  
					docNameCounter++;
				}
			}
			if(docTypeReg== ''){
				if(docTypeCounter == 0) {
					errorList.push(getLocalMessage('master.docGroup.valid.enterDocTypeReg'));
					docTypeCounter++;
				}
			}
			
			if(docSize== ''){
				if(docSizeCounter == 0) {
					errorList.push(getLocalMessage('master.docGroup.valid.enterDocSize'));
					docSizeCounter++;
				}
			}
			
			if(ccmValueset== ''){
				if(ccmValuesetCounter == 0) {
					errorList.push(getLocalMessage('master.docGroup.valid.selectMan'));
					ccmValuesetCounter++;
				}
			}else if(ccmValuesets =='Yes'){
				count++;
			}
			if(prefixReq== ''){
				if(prefixReqCounter == 0) {
					errorList.push(getLocalMessage('master.docGroup.valid.selectDd'));
					ccmValuesetCounter++;
				}
			}
			if(prefixReq== 'Y') {
					if(prefixName == ''){
					errorList.push(getLocalMessage('master.docGroup.valid.selectPrefixName'));
					prefixNameCounter++;
				}
				}
			if(prefixReq== 'N') {
					prefixName = '';
				}
			
		});		
		if(count==0){
			errorList.push(getLocalMessage('master.docGroup.valid.selectAtLeastMan'));
		}
		
	 	    var missingNumber= [];			
			var arrayLength = Math.max.apply(Math, docSrNoList);		
			
			for ( var i = 1; i < arrayLength; i++ ) {				
			    if (docSrNoList.indexOf(""+i) < 0 ) {			    	
			    	missingNumber.push(i);
			    }			    
			}			
			if(missingNumber.length > 0){
				errorList.push(getLocalMessage('master.docGroup.valid.sequenceNo'));
			}
			
			
		if(errorList.length > 0){
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';			 
			$(".warning-div").html(errMsg);					
			$(".warning-div").removeClass('hide')
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			
			errorList = [];				
			return false;
		} else {
			var	formName =	findClosestElementId(obj, 'form');
			var theForm	=	'#'+formName;
			var requestData = __serializeForm(theForm);
	
			var url	=	$(theForm).attr('action');			
			var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
			
			if($.isPlainObject(returnData)) {
				showConfirmBox();
			} else {				
				$("#heading_wrapper").html(returnData);
				$("#heading_wrapper").show();
				$(".warning-div").removeClass("hide");
				return false;
			}
		}
	}
	
	function showConfirmBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		
		message	+='<h4 class=\"text-center text-blue-2 margin-bottom-10\">Form Submitted Successfully</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\' '+ 
		' onclick="proceed()"/>'+	
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
		showModalBoxWithoutClose(errMsgDiv);		

	}

	function proceed() {
		window.location.href='DocumentGroupMaster.html';
	}
	
	function closeErrBox() {
		$('.warning-div').addClass('hide');
	}
	
	function reOrderCheckListGrid(){
		$('.appendableClass').each(function(i) {
			
			//Ids
			$(this).find("input:text:eq(0)").attr("id", "docName" + (i)).attr('onblur','checkForDuplicateDocName(this,'+i+')');
			$(this).find("input:text:eq(1)").attr("id", "docNameReg" + (i)).attr('onblur','checkForDuplicateDocName(this,'+i+')');
			$(this).find("input:text:eq(2)").attr("id", "docType" + (i));
			$(this).find("input:text:eq(3)").attr("id", "docTypeReg" + (i));
			$(this).find("input:text:eq(4)").attr("id", "docSize" + (i));
			$(this).find("select:eq(0)").attr("id", "ccmValueset" + ((i)));
			$(this).find("select:eq(1)").attr("id", "docPrefixRequired" + ((i)));
			$(this).find("select:eq(2)").attr("id", "prefixName" + ((i)));
			$(this).find("input:text:eq(5)").attr("id", "docSrNo" + (i)).attr('onblur','checkForSeqNo(this,'+i+')');
			
	      	$(this).find("input:hidden:eq(0)").attr("id", "dgId" + (i));             
			
			//names
			$(this).find("input:text:eq(0)").attr("name", "docGroupList[" + (i) + "].docName");
			$(this).find("input:text:eq(1)").attr("name", "docGroupList[" + (i) + "].docNameReg");
			$(this).find("input:text:eq(2)").attr("name", "docGroupList[" + (i) + "].docType");			
			$(this).find("input:text:eq(3)").attr("name", "docGroupList[" + (i) + "].docTypeReg");
			$(this).find("input:text:eq(4)").attr("name", "docGroupList[" + (i) + "].docSize");
			$(this).find("select:eq(0)").attr("name", "docGroupList[" + ((i)) + "].ccmValueset");
			$(this).find("select:eq(1)").attr("name", "docGroupList[" + ((i)) + "].docPrefixRequired");
			$(this).find("select:eq(2)").attr("name", "docGroupList[" + ((i)) + "].prefixName");
			$(this).find("input:text:eq(5)").attr("name", "docGroupList[" + (i) + "].docSrNo");
			
			$(this).find("input:hidden:eq(0)").attr("name", "docGroupList[" + (i) + "].dgId").attr("value", "");      
			
		
		});
	}
	
	function checkForDuplicateDocName(obj, currentRow){ 
		
		$('.warning-div').addClass('hide');
		
		$('.appendableClass').each(function(i) {			
			if(currentRow != i && ((obj.value.toLowerCase()) == ($("#docName"+i).val().toLowerCase()))){				
				$("#docName"+currentRow).val("");				
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul><li><i class="fa fa-exclamation-circle"></i> Duplicate Document Name not allowed</li></ul>';
				$(".warning-div").html(errMsg);
				$(".warning-div").removeClass('hide');				
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
			}
			
		});
		
	}

	function checkForSeqNo(obj, currentRow){		
		$('.warning-div').addClass('hide');
		
		$('.appendableClass').each(function(i) {			
			if(currentRow != i && (obj.value == $("#docSrNo"+i).val())){				
				$("#docSrNo"+currentRow).val("");				
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul><li><i class="fa fa-exclamation-circle"></i> Duplicate Sequence not allowed</li></ul>';
				$(".warning-div").html(errMsg);
				$(".warning-div").removeClass('hide');				
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
			}
			
		});		
	}
	
	function searchData(obj){
		var groupId = $("#groupId").val(); 
		var errorList = [];
		 if(groupId==''){
			 errorList.push(getLocalMessage('master.docGroup.valid.searchDocumentGrp'));  
		 }
		 if(errorList.length == 0){
			 
		 $(".error-div").addClass('hide');			 
		 var url="DocumentGroupMaster.html?searchData";
				
		 var requestData={"groupId" : groupId};
				
		     $.ajax({
					url : url,
					datatype: "json",
			        mtype: "POST",
					data : requestData,
					success : function(response) {
						if(response=='N')
							{
							$("#editGrpButton").show();
							$("#createGrpButton").hide();
							}
						else
							{
							$("#editGrpButton").hide();
							$("#createGrpButton").show();
							}
						$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});	
		     
			}else{			 		 
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				$.each(errorList, function(index) {
					errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				});
				
				$("#createGrpButton").hide();
				$("#editGrpButton").hide();
				
				errMsg += '</ul>';			 
				$(".error-div").html(errMsg);	
				$('.error-div').show();	
				$(".error-div").removeClass('hide')
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;				
			}
		 }

function disSelectbox(obj,idval){
	var dissel="#prefixName"+idval;
	var selId=obj.id;
	var seval=$('#'+selId).val();
	if(seval=='N'){
		$(dissel).prop('disabled','disabled');
	}
	
}



