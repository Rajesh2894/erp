
$("#tbMGItemMaster").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

var dsgid = '';
var dsgid = '';
$(function() {
	
	$("#grid").jqGrid(
			{
				url : "ItemMaster.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['', getLocalMessage('material.item.master.itemcode'),getLocalMessage('material.item.master.name'),getLocalMessage('material.item.master.type'),getLocalMessage('material.item.master.category'),getLocalMessage('material.item.master.group'),getLocalMessage('material.item.master.subgroup'),getLocalMessage('store.master.status'),getLocalMessage('material.management.action')],//"Edit","View"
				colModel : [
				             {name : "itemId",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
				             {name : "itemCode",width : 45,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "name",width : 70,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "typeDesc",width : 70,sortable : true,align : 'center',editable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "categoryDesc",width : 70,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "itemGroupDesc",width : 45,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "itemSubGroupDesc",width : 45,sortable : true,align : 'center',editable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "statusDesc",width : 20,sortable : true,align : 'center',editable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : 'itemId', index: 'itemid', width:30 , align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "itemid",
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
				caption : getLocalMessage('material.item.master.title'),
				
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

function addLink(cellvalue, options, rowdata) {
	var viewMessage = getLocalMessage("material.management.view");
	var editMessage = getLocalMessage("material.management.edit");
	return "<a class='btn btn-blue-3 btn-sm viewItemMasterClass' title='" + viewMessage + "' value='" + rowdata.itemid + "' itemid='" + rowdata.itemid + "' ><i class='fa fa-eye'></i></a> " +
       "<a class='btn btn-warning btn-sm editItemMasterClass' title='" + editMessage + "' value='" + rowdata.itemid + "' itemid='" + rowdata.itemid + "' ><i class='fa fa-pencil'></i></a>";
}

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

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

function closeOutErrBox() {
	$('.error-div').hide();
}

$(function() {	
	$(document).on('click', '.createData', function() {
		var $link = $(this);
		var itemid = 1;
		var url = "ItemMaster.html?form";
		var requestData = "itemid=" + itemid  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		
		return false;		
	});			

	$(document).on('click', '.editItemMasterClass', function() {
		var errorList = [];
		var $link = $(this);
		var itemid = $link.closest('tr').find('td:eq(0)').text(); 
		var authStatus = $link.closest('tr').find('td:eq(7)').text();
		var url = "ItemMaster.html?update";
		var requestData = "itemid=" + itemid + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Inactive"){
			$('.content').html(returnData);
		}
		else{
			errorList.push(getLocalMessage("material.item.master.inactive.msg.validation"));
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
	
	$(document)
	.on(
			'click',
			'.viewItemMasterClass',
			function() {
				var $link = $(this);
				var itemid = $link.closest('tr').find('td:eq(0)').text();
				var url = "ItemMaster.html?formForView";
				var requestData = "itemid=" + itemid + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				$(':checked').attr("disabled", true);
				$('#isexpiry').attr("disabled", true);
				$('#isasset').attr("disabled", true);
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
	
	$('.error-div').hide();
	var errorList = [];
	
	$('body').on('focus', ".hasMyNumber", function() {
		$(".hasMyNumber").keyup(function(event) {
			this.value = this.value.replace(/[^0-9]/g, '');
			$(this).attr('maxlength', '10');
		});
	});
	
	$('body').on('focus', ".hasNumber", function() {
		$(".hasNumber").keyup(function(event) {
			this.value = this.value.replace(/[^0-9]/g, '');
			$(this).attr('maxlength', '5');
		});
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


var objTemp;
function saveLeveledData(obj){
	objTemp=obj;
	var errorList = [];
	
	incrementvalue=++count;
	
	 var Revid =  $("#indexdata").val();
	 
	/* var name = $('#name').val();
	 if(name=="" || name==null || name==undefined){
			errorList.push(getLocalMessage("material.item.master.item.name.validation")); 
	 }
	 
	 var uom = $('#uom').val();
	 if(uom=="" || uom==null || uom==undefined){
			errorList.push(getLocalMessage("material.item.master.base.uom.validation")); 
	 }
	 
	 var category = $('#category').val();
	 if(category=="" || category==null || category==undefined){
			errorList.push(getLocalMessage("material.item.master.category.validation")); 
	 }
	 
	 var type = $('#type').val();
	 if(type=="" || type==null || type==undefined){
			errorList.push(getLocalMessage("material.item.master.item.type.validation")); 
	 }
	 
	 var itemgroup = $('#itemgroup').val();
	 if(itemgroup=="" || itemgroup==null || itemgroup==undefined){
			errorList.push(getLocalMessage("material.item.master.item.group.validation")); 
	 }
	 
	 var itemsubgroup = $('#itemsubgroup').val();
	 if(itemsubgroup=="" || itemsubgroup==null || itemsubgroup==undefined){
			errorList.push(getLocalMessage("material.item.master.item.subgroup.validation")); 
	 }
	 
	 var minlevel = $('#minlevel').val();
	 if(minlevel=="" || minlevel==null || minlevel==undefined){
			errorList.push(getLocalMessage("material.item.master.min.stock.level.validation")); 
	 }
	 
	 var reorderlevel = $('#reorderlevel').val();
	 if(reorderlevel=="" || reorderlevel==null || reorderlevel==undefined){
			errorList.push(getLocalMessage("material.item.master.reorder.level.validation")); 
	 }
	 
	 var taxpercentage = $('#taxpercentage').val();
	 if(taxpercentage=="" || taxpercentage==null || taxpercentage==undefined){
			errorList.push(getLocalMessage("material.item.master.tax.percentage.validation")); 
	 }
	 
	 var hsncode = $('#hsncode').val();
	 if(hsncode=="" || hsncode==null || hsncode==undefined){
			errorList.push(getLocalMessage("material.item.master.hsc.code.validation")); 
	 }else{
		var hsncodeLength = hsncode.length;
		if(hsncodeLength < 4) {
			errorList.push(getLocalMessage("material.item.master.min.4.hsc.code.validation"));
		}
	 }
	 
	 var fundRequiredIsAsset = $('#isasset').is(':checked');
	 if(fundRequiredIsAsset){
		var classification = $('#classification').val();
		if(classification=="" || classification==null || classification==undefined){
			errorList.push(getLocalMessage("material.item.master.classification.validation")); 
		}
	 }
		
	 var fundRequiredIsexpiry = $('#isexpiry').is(':checked');
	 if(fundRequiredIsexpiry){
		var expirytype = $('#expirytype').val();
		if(expirytype=="" || expirytype==null || expirytype==undefined){
			errorList.push(getLocalMessage("material.item.master.expiry.type.validation")); 
		}
	 }
	 	 
	 $(".appendableClass").each(function(i) {
	     var convuom = $("#convuom" + i).val();
		 var units = $("#units" + i).val();

		 if(convuom =="" || convuom =="0" || convuom == undefined || convuom == null)
			 errorList.push(getLocalMessage("material.item.master.conversion.uom.validation"));
		 if(units == "0" || units == "" || units == undefined || units == null)
			 errorList.push(getLocalMessage("material.item.master.conversion.quantity.validation"));		 				   
	 });*/

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
		} else {
			 return saveOrUpdateForm(obj, 'Saved Successfully', 'ItemMaster.html','create');
			 showConfirmBoxSave();
		}
	      
}

function showConfirmBoxSave(){
	
	
	var saveorAproveMsg=getLocalMessage("account.btn.save.msg");
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\">'+saveorAproveMsg+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>  '+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>   '+ 
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

	var	formName =	findClosestElementId(objTemp, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');
    
   // var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
    var response= __doAjaxRequestValidationAccor(objTemp,url+'?create', 'post', requestData, false, 'html');
    if(response != false){
       $('.content').html(response);
    }else{
    	closeConfirmBoxForm();
    }
	
}

function closeErrBox() {
	$('.error-div').hide();
}

function searchItemMasterData(){

	 	/*alert("In search");*/
		
		$('.error-div').hide();

		var errorList = [];

		var category = $("#category").val();
		var type = $("#type").val();
		var itemgroup = $("#itemgroup").val();
		var itemsubgroup = $("#itemsubgroup").val();
		var name = $("#name").val();
		
		if(itemsubgroup===undefined)
		{
			itemsubgroup=""; 
		}
		if(category === undefined){
			category = "";
		}
		if(type === undefined){
			type = "";
		}
		if(itemgroup === undefined){
			itemgroup = "";
		}
		if(name === undefined){
			name = "";
		}
		if ((category == "" || category =="0" || category == undefined) && (type == "" || type =="0" || type == undefined) && (itemgroup == "" || itemgroup =="0" || itemgroup == undefined) && (itemsubgroup == "" || itemsubgroup =="0" || itemsubgroup == undefined) && (name == "" || name == null || name == undefined)) {
			errorList.push(getLocalMessage("material.item.master.at.least.one.search.criteria.validation"));
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
		
		var url ="ItemMaster.html?getjqGridsearch";
		
		var requestData = {
				
				"category" : category,
				"type" : type,
				"itemgroup" : itemgroup,
				"itemsubgroup" : itemsubgroup,
				"name" : name,
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
	
	function displayMessageOnSubmit(successMsg){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = getLocalMessage("bt.proceed");
		
		message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
		 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
		 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		//showPopUpMsg(errMsgDiv);
		showModalBoxWithoutCloseaccount(errMsgDiv);
	}

	function redirectToDishonorHomePage () {
		//$.fancybox.close();
		window.location.href='ItemMaster.html';
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
	$("#budRevTableDivID").on("click", '.addButton', function(e) {
		var errorList = [];
	   $('.appendableClass').each(function(i) {
			
		   	 var convuomId = $.trim($("#convuom"+i).val());
			 if(convuomId==0 || convuomId=="") 
			 errorList.push(getLocalMessage("material.item.master.conversion.uom.validation"));
			 
			 var unitsId = $.trim($("#units"+i).val());
			 if(unitsId==null || unitsId=="") 
			 errorList.push(getLocalMessage("material.item.master.conversion.quantity.validation"));

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
				// call that script function which enable for search
				// remove extra added div by id 
				
				$(this).closest("#budRevTable").append(content);
				// reset values
				content.find("input:text").val("");
				content.find("select").val("");
				content.find('.has-error').removeClass('has-error');
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
		e.preventDefault();
		});

	function reOrderTableIdSequence() {
		$('.appendableClass').each(function(i) {
			$(this).find("select:eq(0)").attr("id", "convuom" + i);
			$(this).find($('[id^="units"]')).attr("id", "units" + i);
			
			$(this).find("select:eq(0)").attr("name","itemMasterConversionDtoList[" + i + "].convUom");
			$(this).find($('[id^="units"]')).attr("name","itemMasterConversionDtoList[" + i + "].units");
			$(this).find('.delButton').attr("id",	"delButton" + i);
			$(this).find('.addButton').attr("id",	"addButton" + i);
			$(this).find('#convuom'+i).attr("onchange", "checkBaseUoMValue(" + (i) + ")");
			
			$(this).closest("tr").attr("id", "budRevId" + (i));
			$("#indexdata").val(i);
			
		});

	}

		function clearAllData(obj) {
			var functionId = $('#functionId0').val(""); 
			$("#functionId0").val('').trigger('chosen:updated');
			
			var pacHeadId = $('#pacHeadId0').val(""); 
			$("#pacHeadId0").val('').trigger('chosen:updated');
			
			var cpdIdStatusFlag = $('#cpdIdStatusFlag0').val(""); 
			
			$('.error-div').hide();
			var errorList = [];
			
				if (errorList.length == 0) {
				
				var divName = ".content";
				
				var formName = findClosestElementId(obj, 'form');

				var theForm = '#' + formName;

				var requestData = __serializeForm(theForm);

				var url = "ItemMaster.html?getjqGridload";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
			}
		}
		
		function loadBudgetReappropriationData(obj) {

			$('.error-div').hide();
			var errorList = [];
			
			if (errorList.length == 0) {
				
				var divName = ".content";
				
				var formName = findClosestElementId(obj, 'form');

				var theForm = '#' + formName;

				var requestData = __serializeForm(theForm);

				var url = "ItemMaster.html?getjqGridload";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
			}
		};
		
		function exportTemplate(){
			
			var $link = $(this);
			var url = "ItemMaster.html?importExportExcelTemplateData";
			var requestData = "";
			var returnData = __doAjaxRequest(url, 'post', requestData, false);

			$('.content').html(returnData);

			prepareDateTag();
			return false;
		}
		function closeOutErrorBox(){
			$('#errorDivSec').hide();
		}

		function isEnableClassification() {
			$('#classification').val("");
			var fundRequired = $('#isasset').is(':checked');
			if(fundRequired){
				$("#classification").prop("disabled",false);
			} else {
				$("#classification").prop("disabled",true);
			}
		}
		
		function isEnableExpiryType() {
			$('#expirytype').val("");
			var fundRequired = $('#isexpiry').is(':checked');
			if(fundRequired){
				$("#expirytype").prop("disabled",false);
			} else {
				$("#expirytype").prop("disabled",true);
			}
		}
		
		function minValueValidation()
		{
			$('.error-div').hide();
			var errorList = [];
			var hsncode = $("#hsncode").val();
			var hsncodeLength = hsncode.length;

			if(hsncodeLength < 4) {
				errorList.push(getLocalMessage("material.item.master.min.4.hsc.code.validation"));
			}
		    
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
		}
		
		function checkBaseUoMValue(cnt) {
			
			$('.error-div').hide();
			var errorList = [];
			var uom = $('#uom').val();
			var convuom = $('#convuom'+cnt).val();
			
			if(uom==0 || uom=="") {
				 errorList.push(getLocalMessage("material.item.master.base.uom.validation"));
				 $('#convuom'+cnt).val("");
			}
			
			if(uom == convuom) {
				errorList.push(getLocalMessage("material.item.master.different.base.uom.validation"));
				$('#convuom'+cnt).val("");
			}
			
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
		}
		
		function CheckName() {
			var errorList = [];
			var data = {
				"name" : $('#name').val(),
			
			};
			var divName = '.content-page';
			var ajaxResponse = __doAjaxRequest("ItemMaster.html?CheckName",
					'POST', data, false, 'json');
			
			if (!ajaxResponse) {
				$('#name').val("");
				errorList.push(getLocalMessage("material.management.item.name.validation"));
				displayErrorsOnPage(errorList);
				
			}
			
			
		}

		
		