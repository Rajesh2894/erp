
/*google.load("elements", "1", {
	packages : "transliteration"
});*/



var selectedIds =[];
var i = 0;

function resetlocationmaster(obj,url){	
	$('#locName').val('');
	searchLocationMaster(obj,url);	
}

function searchLocationMaster(obj,url){
	$("#locationmastergrid").show();
	$("#locationMasButton").show();
	$("#Submit").hide();
	
	var url = "LocationMas.html?search"
	var requestData = { "locationName":$('#locName').val()}
	__doAjaxRequest(url, 'POST', requestData, false, 'json');
	reloadGrid('gridLocationMasGridId');
}

$(function() {		
	$(document).on('click', '.editClass', function() {
		
		var url = "LocationMas.html?editLocation";
		var locId = $(this).attr('value');
		var returnData = {"locId" : locId};
		
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
			data : returnData,
			success : function(response) {						
				var divName = '.form-div';
				$(".widget").html(response);
				
				$(".widget").show();
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
	
	$(document).on('click', '.deleteRow', function() {
		
	    var $link = $(this);
	    var locId = $link.attr('value');
	   
	    selectedIds[i] =locId;
        i++;
	   
	    if($("#"+$(this).closest('tr').attr('id')).attr('style','background-color:gray').find("#RowFromEditMode").attr('class') == "editGridRowClass")
	    {
	    	$("#"+$(this).closest('tr').attr('id')).attr('style','background-color:gray').find("#RowFromEditMode").removeClass("editGridRowClass");
	    }
	    else
	    	{
	    	$("#"+$(this).closest('tr').attr('id')).find("#RowFromEditMode").addClass("editGridRowClass");
	    	$("#"+$(this).closest('tr').attr('id')).removeAttr("style");
	    	}

	});
	
})



$(function() {
	
	$(document).on('click', '.editGridRowClass', function() {
		
	    var $link = $(this);
	    var locId = $link.attr('value');
		var url = "LocationMas.html?editGridRow";
		var requestData = "locId="+locId
		
		$.ajax({
			url : url,
			data : requestData,
			type : "GET",
			success : function(response) {
				
				var divName = '.child-popup-dialog';			
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				 showMsgModalBox(divName);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	    
	    return false;
	  });
	
});

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

 function saveData(obj){
	var errorList = [];
	var deptName 		= $("#departmentId").val();
	var country 		= $("#tryLevel1").val();
	var state 			= $("#tryLevel2").val();
	var district 		= $("#tryLevel3").val();
	var taluka 			= $("#tryLevel4").val();
	var city 			= $("#tryLevel5").val();
	var address 		= $("#locAddressId").val();
	var addressReg 		= $("#locAddressRegId").val();
	var area 			= $("#locationAreaRegId").val();
	var areaReg 		= $("#locationAreaId").val();
	var locationName 	= $("#locationNameId").val();
	var locationNameReg = $("#locationNameRegId").val();
	var operationChkBx 	=$("#operationalChkBxId").is(':checked');
	var status = $('#locActive').val();

	if(deptName == '0') {
		errorList.push(getLocalMessage('locationMas.valMSG.deptName'));
	}
	if(address == '') {
		errorList.push(getLocalMessage('locationMas.valMSG.locAddressEng'));
	}
	if(addressReg == '') {
		errorList.push(getLocalMessage('locationMas.valMSG.locAddressReg'));
	}
	if(area == '') {
		errorList.push(getLocalMessage('locationMas.valMSG.locAreaEng'));
	}
	if(areaReg == '') {
		errorList.push(getLocalMessage('locationMas.valMSG.locAreaReg'));
	}
	if(locationName == '') {
		errorList.push(getLocalMessage('locationMas.valMSG.locNameEng'));
	}
	if(locationNameReg == '') {
		errorList.push(getLocalMessage('locationMas.valMSG.locNameReg'));
	}
	
	if(status == '0') {
		errorList.push(getLocalMessage('locationMas.valMSG.status'));
	}
	
	if(country	!= undefined && country == '0' ){
		errorList.push(getLocalMessage('locationMas.valMSG.locCountry'));
	}
	if(state != undefined &&  state == '0') {
		errorList.push(getLocalMessage('locationMas.valMSG.locState'));
	}
	if(district != undefined && district == '0') {
		errorList.push(getLocalMessage('locationMas.valMSG.locDistrict'));
	}
	if(taluka != undefined && taluka == '0') {
		errorList.push(getLocalMessage('locationMas.valMSG.locTaluka'));
	}
	if(city != undefined && city == '0') {
		errorList.push(getLocalMessage('locationMas.valMSG.locCity'));
	}
	
	if(deptName !='0' &&  operationChkBx == false){
		errorList.push(getLocalMessage('locationMas.valMSG.operationalAreaMapping'));
	} 
	
	if(operationChkBx==true){
		
		var OperLevel1 = $("#codIdOperLevel1").val();
		var OperLevel2 = $("#codIdOperLevel2").val();
		var OperLevel3 = $("#codIdOperLevel3").val();
		var OperLevel4 = $("#codIdOperLevel4").val();
		var OperLevel5 = $("#codIdOperLevel5").val();
		
		if(OperLevel1 != undefined && OperLevel1 == '0') {
			errorList.push("Please Select Level 1 ");
		}
		if(OperLevel2 != undefined && OperLevel2 == '0'){
			errorList.push("Please Select Level 2 ");
		}
		
		if(OperLevel3 != undefined && OperLevel3 == '0'){
			errorList.push("Please Select Level 3 ");
		}
		
		if(OperLevel4 != undefined && OperLevel4 == '0'){
			errorList.push("Please Select Level 4 ");
		}
		
		if(OperLevel5 != undefined && OperLevel5 == '0'){
			errorList.push("Please Select Level 5 ");
		}
	}
	if (errorList.length == 0) {		
		 saveOrUpdateForm(obj,"", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
	return false;
 }
 
 function displayErrorsOnPage(errorList){
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
	}
 
 function closeErrBox() {
	$('.warning-div').addClass('hide');
}

 function showConfirmBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		
		message	+='<p>Form Submitted Successfully</p>';
		 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
		'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
		' onclick="proceed()"/>'+	
		'</p>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
		 return false;
	}


 function proceed() {
	window.location.href='LocationMas.html';
 }
 function goToDashBoard(){
	 window.location.href='LocationMas.html';
 }
 
 function addMoreDepartment () {
	$("#deptName").clone(true,true).insertAfter("div.exportClass:last");
	return false;
}

 $(function() {

		$(document).on('click', '.addLocationClass', function() {
			var $link = $(this);
			var url = "LocationMas.html?addLocation";
			var requestData = {};
			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			var divName = '.widget';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			return false;
		});
	});

 function checkboxFormatter(cellvalue, options, rowObject,rowdata, action) {
		return "<input type='checkbox' value='"+rowObject.locId+"' name='checkboxIsCC' onchange='checkboxClick(this);'>";
	}

 function checkboxClick(obj){   
		var c=0;
		var $link = $(obj);
	    var locId = $link.val();
		var rowIDs = jQuery("#locationmastergrid").jqGrid('getDataIDs');
		 $("input:checkbox").each(function (j) {
			 if($(this).is(':checked')){
		        selectedIds[c] = $(this).val();
		        $(this).closest('tr').find('td:eq(4)').find('a').removeClass('editGridRowClass');
		        $(this).closest('tr').attr('style','background-color:gray').find("#RowFromEditMode");
		        
		        c++;
			 }else{
				 var removeItem = $(this).val();
				 $(this).closest('tr').find('td:eq(4)').find('a').addClass('editGridRowClass');
				 $(this).closest('tr').removeAttr("style").find("#RowFromEditMode");
				 
				 selectedIds = jQuery.grep(selectedIds, function(value) {
				   return value != removeItem;
				 });
				 
			 }
		 });
		 
		 if(selectedIds.length == 0){
				$("#Submit").hide();
			}
			else{
				$("#Submit").show();
			}
		 
	}
	
 function deleteLocationMasterData(){
	 $("#Submit").hide();
		var url='LocationMas.html?deleteLocationMaster';
    	var requestData = "selectedId="+selectedIds;
		$.ajax({
					url : url,
					data : requestData,
					type : 'POST',
					datatype: "json",
					success : function(response) {
						$("#locationmastergrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	 
 }
	
 function showConfirmBoxEmployee(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Yes';
		
		message	+='<p class="padding-top-10">Are you sure want to delete ?</p>';
		 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
		'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-default \'    '+ 
		' onclick="deleteLocationMasterData()"/>'+	
		'</p>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	} 
	
 
 	$(".administrative-box").hide();
 	$('#idministrativeChkBxId').click(function() {
     if($(this).is(":checked")) {
         $(".administrative-box").show();
     } else {
         $(".administrative-box").hide();
     }
 });
 	$(".electoral-box").hide();
 	$('#electoralChkBxId').click(function() {
     if($(this).is(":checked")) {
         $(".electoral-box").show();
     } else {
         $(".electoral-box").hide();
     }
 });
 	$(".revenue-box").hide();
 	 $('#revenueChkBxId').click(function() {
     if($(this).is(":checked")) {
         $(".revenue-box").show();
     } else {
         $(".revenue-box").hide();
     }
 });
 	$(".operational-box").hide();
 	 $('#operationalChkBxId').click(function() {
     if($(this).is(":checked")) {
         $(".operational-box").show();
     } else {
         $(".operational-box").hide();
     }
 });


