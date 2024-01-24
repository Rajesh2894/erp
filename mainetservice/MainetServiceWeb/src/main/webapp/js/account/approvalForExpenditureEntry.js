var dsgid = '';
var dsgid = '';
$(function() {
	
	$("#grid").jqGrid(
			{
				url : "ApprovalForExpenditureEntry.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['', getLocalMessage('account.budgetprojectedexpendituremaster.budgethead'),getLocalMessage('account.budgetprojectedexpendituremaster.originalestimate(indianrupees)'), getLocalMessage('account.budgetprojectedexpendituremaster.expenditure'),getLocalMessage('account.budgetprojectedexpendituremaster.balancebudget'),getLocalMessage('edit.msg'), getLocalMessage('master.view')],
				colModel : [
				             {name : "",width : 30,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
				             {name : "",width : 65,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "",width : 35,sortable : true,classes:'text-right', editable: true,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] } },
				             {name : "",width : 35,sortable : true,classes:'text-right', editable: true,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] } },
				             {name : "",width : 35,sortable : true,classes:'text-right', editable: true,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] } },
				             {name : 'prApprForExpid',index : 'prApprForExpid',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				             {name : 'prApprForExpid',index : 'prApprForExpid',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "prApprForExpid",
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
				caption : "Approval For Expenditure Entry List",
				
				formatter: function (v) {
				    // uses "c" for currency formatter and "n" for numbers
				    return Globalize.format(Number(v), "c");
				},
				unformat: function (v) {
				    return Globalize.parseFloat(v);
				}
				
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});

function returnisdeletedUrl(cellValue, options, rowdata, action) {

	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"
				+ rowdata.isdeleted
				+ "'  alt='Designation is Active' title='Designation is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"
				+ rowdata.isdeleted
				+ "' alt='Designation is  INActive' title='Designation is InActive'></a>";
	}

}
function returnEditUrl(cellValue, options, rowdata, action) {
	prApprForExpid = rowdata.prApprForExpid;
	return "<a href='#'  return false; class='editBugopnBalMasterClass' value='"+prApprForExpid+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewBugopnBalMasterClass' value='"+prApprForExpid+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

$(document).ready(function() {
	$('.error-div').hide();
	var Error_Status = '${Errore_Value}';

});

function closeOutErrBox() {
	$('.error-div').hide();
}


$(function() {	
	$(document).on('click', '.createData', function() {
	
		var $link = $(this);
		var prApprForExpid = 1;
		var url = "ApprovalForExpenditureEntry.html?form";
		var requestData = "prApprForExpid=" + prApprForExpid  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;		
	});			


	$(document).on('click', '.editBugopnBalMasterClass', function() {
		var $link = $(this);
		var prApprForExpid = $link.closest('tr').find('td:eq(0)').text();
		var url = "ApprovalForExpenditureEntry.html?update";
		var requestData = "prApprForExpid=" + prApprForExpid + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;
	});
	
	$(document)
	.on(
			'click',
			'.viewBugopnBalMasterClass',
			function() {
				var $link = $(this);
				var prApprForExpid = $link.closest('tr').find('td:eq(0)').text();
				var url = "ApprovalForExpenditureEntry.html?formForView";
				var requestData = "prApprForExpid=" + prApprForExpid + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				return false;
			});

});

$(function() {

	$(document).on('click', '.deleteDesignationClass', function() {
		var $link = $(this);
		var dsgid = $link.closest('tr').find('td:eq(0)').text();
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
		showConfirmBoxEmployee(dsgid);

	});	

});


$( document ).ready(function() {
	
	$('.hasMyNumber').keyup(function () {
	    this.value = this.value.replace(/[^0-9.]/g,'');
	    $(this).attr('maxlength','13');
	});
});

function displayAddView(obj) {
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var divName = childDivName;

	var requestData = __serializeForm(theForm);

	var formAction = $(theForm).attr('action');

	var url = formAction + '?create';

	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$(divName).html(returnData);

	prepareDateTag();
}


$(".warning-div ul")
		.each(
				function() {
					var lines = $(this).html().split("<br>");
					$(this)
							.html(
									'<li>'
											+ lines
													.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;")
											+ '</li>');
				});
$('html,body').animate({
	scrollTop : 0
}, 'slow');


var	errMsgDiv		=	'.msg-dialog-box';

function saveLeveledData(obj){
	
var errorList = [];
	
	var levelData = $('#prApprForExpid').val();
	levelData++;
	var sid = 'faYearid' + levelData;
	var selID = '#'+sid;
			
	if($('#count').val()=="0"){
		count=0;
	}else{
		count=parseInt($('#count').val());
	}
	var id=$('#index').val();	
	var assign = count;
	
	var  faYearid= $("#faYearid").val();
	var  cpdBugsubtypeId= $("#cpdBugsubtypeId").val();
	var  dpDeptid= $("#dpDeptid").val();
	
	if(faYearid == '') {
		errorList.push("Please select Financial Year");
	}

	if(cpdBugsubtypeId == '') {
		errorList.push("Please select Budget Sub Type");	
	}
	if(dpDeptid == '') {
		errorList.push("Please select Department");
	}
	
	incrementvalue=++count;
	 var Revid =  $("#indexdata").val();
	 var prBudgetCodeid= $('#prBudgetCodeid'+Revid).val();
	 if(prBudgetCodeid != ""){
			var dec;
		 for(m=0; m<=Revid;m++){
			 for(dec=0; dec<=Revid;dec++){
				 if(m!=dec){
				if($('#prBudgetCodeid'+m).val() == $('#prBudgetCodeid'+dec).val()){
					errorList.push("The Combination AccountHead already exists!");
				}
			  }
			} 
		 }
   	   }
		
	var k =  $("#indexdata").val();
	for(n=0; n<=k;n++){	
	var prBudgetCodeidRev = $('#prBudgetCodeid'+n).val();
	var orginalEstamt = $("#orginalEstamt"+n).val();
	
	if (prBudgetCodeidRev == '') {
		errorList.push("Please select Account heads");
	}		 	 
	 if(orginalEstamt == ''){
	 errorList.push("Please Enter Budget Provision Amount");
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
	    else{					
	var	formName =	findClosestElementId(obj, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');
    var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
    $('.content').html(response);
	    }
    	
}

function setSecondaryCodeFinance(count)
{
	 var primaryCode=$('#prBudgetCodeid'+count).val();
	 
		$('#prBudgetCodeid'+count).find('option:gt(0)').remove();
		
		if (primaryCode > 0) {
			var postdata = 'prBudgetCodeid=' + primaryCode;
			
			var json = __doAjaxRequest('ApprovalForExpenditureEntry.html?sacHeadItemsList',
					'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#prBudgetCodeid'+count).append(optionsAsString );
			
		}
}	

function setHiddenData(){
	 $('#secondaryId').val($('#prBudgetCodeid0').val());
}

function searchBudgetExpenditureData(){
		$('.error-div').hide();
		var errorList = [];
		if (errorList.length == 0) 
		{
		
		var faYearid = $("#faYearid").val();
		var cpdBugsubtypeId = $("#cpdBugsubtypeId").val();
		var dpDeptid = $("#dpDeptid").val();
		var prBudgetCodeid = $("#prBudgetCodeid").val();
		var fundId = $("#fundId").val();
		var functionId = $("#functionId").val();
		
		var url ="ApprovalForExpenditureEntry.html?getjqGridsearch";
		var requestData = {
				"faYearid" :faYearid,
				"cpdBugsubtypeId" : cpdBugsubtypeId,
				"dpDeptid" : dpDeptid,
			    "prBudgetCodeid" : prBudgetCodeid,
			    "fundId" : fundId,
			    "functionId" : functionId};
			
		 $.ajax({
				url : url,
				data : requestData,
				datatype: "json",
				type : 'GET',
				success : function(response) {
						
					$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				
				},
				error : function(xhr, ajaxOptions, thrownError) {
					//alert("No data in the database");
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
		} else {
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';
		$('.error-div').html(errMsg);	
		$('.error-div').show();
		 return false;
		}
	};

	function displayMessageOnSubmit(successMsg){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		
		message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
		 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
		 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showPopUpMsg(errMsgDiv);
	}

	function redirectToDishonorHomePage () {
		$.fancybox.close();
		window.location.href='ApprovalForExpenditureEntry.html';
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

	//to generate dynamic table
	$("#budExpTableDivID").on("click", '.addButton', function(e) {
	var errorList = [];
	   $('.appendableClass').each(function(i) {
			 var prBudgetCodeid = $.trim($("#prBudgetCodeid"+i).val());
			 if(prBudgetCodeid==0 || prBudgetCodeid=="") 
			 errorList.push("Please select Account Heads");
			
			 var orginalEstamt = $.trim($("#orginalEstamt"+i).val());
			 if(orginalEstamt=="")
			 errorList.push("Please Enter Budget Provision Amount");
			
			 for(m=0; m<i;m++){
					if($('#prBudgetCodeid'+m).val() == $('#prBudgetCodeid'+i).val()){
						
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
				var content = $(this).closest('#budExpTable tr').clone();
				$(this).closest("#budExpTable").append(content);
				content.find("select").attr("value", "");
				content.find("input:text").val("");
				content.find("select").val("");
				content.find("input:checkbox").attr('checked',false);
				content.find('div.chosen-container').remove();
				content.find("select").chosen().trigger("chosen:updated");
				reOrderTableIdSequence();

			});
		//to delete row
		$("#budExpTableDivID").on("click", '.delButton', function(e) {
		
		var rowCount = $('#budExpTable tr').length;
		if (rowCount <= 2) {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
		}
		
		$(this).closest('#budExpTable tr').remove();
		reOrderTableIdSequence();
		e.preventDefault();
		});

	function reOrderTableIdSequence() {
	$('.appendableClass').each(function(i) {
			$(this).find("select:eq(0)").attr("id", "prBudgetCodeid" + i);
			$(this).find("input:text:eq(0)").attr("id", "prBudgetCodeid" + i);
			$(this).find("input:text:eq(1)").attr("id", "orginalEstamt" + i);
			$(this).find("select:eq(0)").attr("name","bugExpenditureMasterDtoList[" + i + "].prBudgetCodeid");
			$(this).find("input:text:eq(0)").attr("name","bugExpenditureMasterDtoList[" + i + "].prBudgetCodeid");
			$(this).find("input:text:eq(1)").attr("name","bugExpenditureMasterDtoList[" + i + "].orginalEstamt");
			$(this).find('.delButton').attr("id",	"delButton" + i);
			$(this).find('.addButton').attr("id",	"addButton" + i);
			$(this).find('#prBudgetCodeid'+i).attr("onchange", "findduplicatecombinationexit(" + (i) + ")");
			$(this).closest("tr").attr("id", "budExpId" + (i));
			$("#indexdata").val(i);
			
		});

	}

	function findduplicatecombinationexit(cnt) {
		if ($('#count').val() == "") {
			count = 1;
		} else {
			count = parseInt($('#count').val());
		}
		var assign = count;

		$('.error-div').hide();
		var errorList = [];
		var theForm = '#frmMaster';
		var requestData = __serializeForm(theForm);
		var id =  $("#indexdata").val();
		var prBudgetCodeid = $('#prBudgetCodeid'+id).val();
			 var Expid =  $("#indexdata").val();
				if(prBudgetCodeid != "" ){
					var dec;
				 for(m=0; m<=Expid;m++){
					 for(dec=0; dec<=Expid;dec++){
						 if(m!=dec){
						if($('#prBudgetCodeid'+m).val() == $('#prBudgetCodeid'+dec).val()){
							errorList.push("The Combination AccountHead already exists!");
							$('#functionId'+cnt).val(""); 
							$("#functionId"+cnt).val('').trigger('chosen:updated');
							$("#prBudgetCodeid"+cnt).val("");
							$("#prBudgetCodeid"+cnt).val('').trigger('chosen:updated');
							var orginalEstamt = $("#orginalEstamt"+cnt).val("");
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
			 
		if (errorList.length == 0) {
			
			if ($('#faYearid').val() == "") {
				msgBox('Please Select Financial Year');
				var prBudgetCodeid = $('#prBudgetCodeid'+cnt).val("");
				$("#prBudgetCodeid"+cnt).val('').trigger('chosen:updated');
				return false;
			}
			
			if ($('#cpdBugsubtypeId').val() == "") {
				msgBox('Please Select Budget Sub Type');
				var prBudgetCodeid = $('#prBudgetCodeid'+cnt).val("");
				$("#prBudgetCodeid"+cnt).val('').trigger('chosen:updated');
				return false;
			}
			
			if ($('#dpDeptid').val() == "") {
				msgBox('Please Select Department');
				var prBudgetCodeid = $('#prBudgetCodeid'+cnt).val("");
				$("#prBudgetCodeid"+cnt).val('').trigger('chosen:updated');
				return false;
			}
			
			var url = "ApprovalForExpenditureEntry.html?getBudgetExpDuplicateGridloadData&cnt="+cnt;

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			 if(returnData){
				 
				errorList.push(getLocalMessage("account.Budget.provision.is.already.entered.against"));
				var prBudgetCodeid = $('#prBudgetCodeid'+cnt).val("");
				$("#prBudgetCodeid"+cnt).val('').trigger('chosen:updated');
				$('#functionId'+cnt).val(""); 
				$("#functionId"+cnt).val('').trigger('chosen:updated');
				
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
	
	function clearFormData(cnt) {

		 $('.error-div').hide();
			var errorList = [];

		var prBudgetCodeid = $('#prBudgetCodeid'+cnt).val(""); 
		$("#prBudgetCodeid"+cnt).val('').trigger('chosen:updated');

		var theForm = '#frmMaster';

		var faYearid = $('#faYearid').val();
		var dpDeptid = $('#dpDeptid').val();
		var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

		if (faYearid == '') {
			errorList.push('Please Select Financial Year');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}
		
		if (dpDeptid == '') {
			errorList.push('Please Select Department');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
		}

		if (cpdBugsubtypeId == '') {
			errorList.push('Please Select Budget Sub Type');
			var functionId = $('#functionId'+cnt).val("");
			$("#functionId"+cnt).val('').trigger('chosen:updated');
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
		};

		function clearAllData(obj) {
			var prBudgetCodeid = $('#prBudgetCodeid0').val(""); 
			$("#prBudgetCodeid0").val('').trigger('chosen:updated');
			var orginalEstamt = $('#orginalEstamt0').val(""); 
			var revisedEstamt = $('#revisedEstamt0').val(""); 
			var expenditureAmt = $('#expenditureAmt0').val(""); 
			var prBalanceAmt = $('#prBalanceAmt0').val(""); 
			var prExpBudgetCode = $('#prExpBudgetCode0').val(""); 
			$('.error-div').hide();
			var errorList = [];
				if (errorList.length == 0) {
				
				var divName = ".content";
				
				var formName = findClosestElementId(obj, 'form');

				var theForm = '#' + formName;

				var requestData = __serializeForm(theForm);

				var url = "ApprovalForExpenditureEntry.html?getjqGridload";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
			}
		}
		
		function loadBudgetExpenditureData(obj) {

			$('.error-div').hide();
			var errorList = [];
			
			var prBudgetCodeid = $('#prBudgetCodeid0').val(""); 
			$("#prBudgetCodeid0").val('').trigger('chosen:updated');
			
			var orginalEstamt = $('#orginalEstamt0').val(""); 
			var revisedEstamt = $('#revisedEstamt0').val(""); 
			var prCollected = $('#prCollected0').val(""); 
			var prRevBudgetCode = $('#prRevBudgetCode0').val(""); 
			
			var faYearid = $('#faYearid').val();
			var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

			if (faYearid == '') {
				errorList.push('Please Select Financial Year');
				var dpDeptid = $('#dpDeptid').val("");
				$("#dpDeptid").val('').trigger('chosen:updated');
			}
			
			if (cpdBugsubtypeId == '') {
				errorList.push('Please Select Budget Sub Type');
				var dpDeptid = $('#dpDeptid').val("");
				$("#dpDeptid").val('').trigger('chosen:updated');
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

				var url = "ApprovalForExpenditureEntry.html?getjqGridload";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				$(divName).removeClass('ajaxloader');
				$(divName).html(response);

			}
		};
		
		
		function copyContent(obj) {

			var id=  $(obj).attr('id');
			var arr = id.split('orginalEstamt');
			var indx1=arr[1];
			
			var OldAmount1 = $('#orginalEstamt'+indx1).val();
			$('#prBalanceAmt'+indx1).val(OldAmount1);
	
		}