	
// to generate dynamic table
$("#payOtherdID").on("click", '.addButton',function(e) {
			var errorList = [];
			 $('.appendableClass tbody').each(function(i) {
				row = i;
			
				// to validate Rows
				
				 var serviceType = $.trim($("#serviceName").val());
				 if(serviceType==0 || serviceType=="")
				    errorList.push(getLocalMessage('master.select.serviceType'));
				
				
				 var cmChargeapplicableat = $.trim($("#cmChargeapplicableat"+i).val());
				 if(cmChargeapplicableat==0 || cmChargeapplicableat=="")
					    errorList.push(getLocalMessage(' master.select.charge.applicable '));
				
				 var cmChargeType = $.trim($("#cmChargeType"+i).val());
				 if(cmChargeType==0 || cmChargeType=="")
					    errorList.push(getLocalMessage(' master.select.chargeType '));
				 
				 var cmSlabDepend = $.trim($("#cmSlabDepend"+i).val());
				 if(cmSlabDepend==0 || cmSlabDepend=="")
					    errorList.push(getLocalMessage(' master.select.slab.depend '));
				 
				 var cmChargeType = $.trim($("#cmFlatDepend"+i).val());
				 if(cmChargeType==0 || cmChargeType=="")
					    errorList.push(getLocalMessage(' master.select.flat.depend '));
				 

				 var cmChargeSequence = $.trim($("#cmChargeSequence"+i).val());
				 if(cmChargeSequence==0 || cmChargeSequence=="")
				    errorList.push(getLocalMessage('master.select.charge.sequence'));
				 
				 
				 var cmChargeStartDate = $.trim($("#cmChargeStartDateStr"+i).val());
				 if(cmChargeStartDate=="")
					    errorList.push(getLocalMessage(' master.select.charge.startDate '));
				 
				 var cmChargeEndDate = $.trim($("#cmChargeEndDateStr"+i).val());
				 if( cmChargeEndDate=="")
					    errorList.push(getLocalMessage(' master.select.charge.endDate '));
				 
				 if((new Date(dateFormate(cmChargeStartDate)) > new Date(dateFormate(cmChargeEndDate)))){
						errorList.push(getLocalMessage("master.charge.startDate.less.endDate"));
					}
			 });
			 
			 if(errorList.length > 0){ 
				 
					var errMsg = '<ul>';
					$.each(errorList, function(index) {
						errMsg += '<li>' + errorList[index] + '</li>';
					});

					errMsg += '</ul>';

					$('#errorId').html(errMsg);
					$('#errorDivId').show();
					return false;
				 }
			 
			 	e.preventDefault();
			 
			 	$(".datepicker").datepicker("destroy");
			 
				var content = $(this).closest('#chargeMasterTab tbody').clone();
				$(this).closest("#chargeMasterTab").append(content);
				
				// for reset all value 
				content.find("select:eq(0)").attr("value", "");
				content.find("select:eq(1)").attr("value", "");
				content.find("select:eq(2)").attr("value", "");
				content.find("select:eq(3)").attr("value", "");
				content.find("select:eq(4)").attr("value", "");
				content.find("input:text").val("");
				
				// for generating dynamic Id
				content.find("select:eq(4)").attr("id","cmChargeSequence" + (row + 1)); 
				content.find("select:eq(0)").attr("id","cmChargeapplicableat" + (row + 1)); 
				content.find("select:eq(1)").attr("id","cmChargeType" + (row + 1)); 
				content.find("select:eq(2)").attr("id","cmSlabDepend" + (row + 1)); 
				content.find("select:eq(3)").attr("id","cmFlatDepend" + (row + 1)); 
				content.find("input:text:eq(0)").attr("id","cmChargedescriptionEng" + (row + 1)); 
				content.find("input:text:eq(1)").attr("id","cmChargedescriptionReg" + (row + 1)); 
				content.find("input:text:eq(2)").attr("id","cmChargeStartDateStr" + (row + 1)); 
				content.find("input:text:eq(3)").attr("id","cmChargeEndDateStr" + (row + 1)); 
				
			 	
			 	content.find('.delButton').attr("id", "delButton"+ (row+1));
			 	content.find('.addButton').attr("id", "addButton"+ (row+1));
			 	
			 	// for generating dynamic path
				content.find("select:eq(4)").attr("name","dto[" + (row + 1)+ "].cmChargeSequence");	
				content.find("select:eq(0)").attr("name","dto[" + (row + 1)+ "].cmChargeapplicableat");	
				content.find("select:eq(1)").attr("name","dto[" + (row + 1)+ "].cmChargeType");	
				content.find("select:eq(2)").attr("name","dto[" + (row + 1)+ "].cmSlabDepend");	
				content.find("select:eq(3)").attr("name","dto[" + (row + 1)+ "].cmFlatDepend");	
				content.find("input:text:eq(0)").attr("name","dto[" + (row + 1)+ "].cmChargedescriptionEng");	
				content.find("input:text:eq(1)").attr("name","dto[" + (row + 1)+ "].cmChargedescriptionReg");	
				content.find("input:text:eq(2)").attr("name","dto[" + (row + 1)+ "].cmChargeStartDateStr");	
				content.find("input:text:eq(3)").attr("name","dto[" + (row + 1)+ "].cmChargeEndDateStr");	
				
				// to add date picker on dynamically created Date fields
				 $(".datepicker").datepicker({
				        dateFormat: 'dd/mm/yy',		
						changeMonth: true,
						changeYear: true
					});
				 
				 reOrderTableIdSequence();
		});

// to delete row
$("#payOtherdID").on("click", '.delButton', function(e) {
	
	var rowCount = $('#chargeMasterTab tbody').length;
	if(rowCount<=1){
		return false;
	}
	
	 $(this).closest('#chargeMasterTab tbody').remove();

	 reOrderTableIdSequence();
	 e.preventDefault();
});	

// to re-order Id's on delete/Add 
function reOrderTableIdSequence() {

	$('.appendableClass tbody').each(function(i) {

		$(".datepicker").datepicker("destroy");
		
		$(this).find("select:eq(4)").attr("id", "cmChargeSequence" + i);
		$(this).find("select:eq(0)").attr("id", "cmChargeapplicableat" + i);
		$(this).find("select:eq(1)").attr("id", "cmChargeType" + i);
		$(this).find("select:eq(2)").attr("id", "cmSlabDepend" + i);
		$(this).find("select:eq(3)").attr("id", "cmFlatDepend" + i);
		$(this).find("input:text:eq(0)").attr("id", "cmChargedescriptionEng" + i);
		$(this).find("input:text:eq(1)").attr("id", "cmChargedescriptionReg" + i);
		$(this).find("input:text:eq(2)").attr("id", "cmChargeStartDateStr" + i);
		$(this).find("input:text:eq(3)").attr("id", "cmChargeEndDateStr" + i);
		
		$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
		$(this).parents('tr').find('.addButton').attr("id", "addButton"+ i);
		
		$(this).find("select:eq(4)").attr("name", "dto[" + i	+ "].cmChargeSequence");
		$(this).find("select:eq(0)").attr("name", "dto[" + i	+ "].cmChargeapplicableat");
		$(this).find("select:eq(1)").attr("name", "dto[" + i	+ "].cmChargeType");
		$(this).find("select:eq(2)").attr("name", "dto[" + i	+ "].cmSlabDepend");
		$(this).find("select:eq(3)").attr("name", "dto[" + i	+ "].cmFlatDepend");
		$(this).find("input:text:eq(0)").attr("name", "dto[" + i	+ "].cmChargedescriptionEng");
		$(this).find("input:text:eq(1)").attr("name", "dto[" + i	+ "].cmChargedescriptionReg");
		$(this).find("input:text:eq(2)").attr("name", "dto[" + i	+ "].cmChargeStartDateStr");
		$(this).find("input:text:eq(3)").attr("name", "dto[" + i	+ "].cmChargeEndDateStr");
		
		 $(".datepicker").datepicker({
		        dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true
			});
	});
	
}

// to formate date date picker formate to new date formate
function dateFormate(date){
	 	var chunks = date.split('/');
		var newDate = chunks[1]+'/'+chunks[0]+'/'+chunks[2];
		return newDate;
}

// to save and validate form
function submitChargeMasterForm(obj) {
	var errorList = [];	
	
	 var serviceType = $.trim($("#serviceName").val());
	 if(serviceType==0 || serviceType=="")
	    errorList.push(getLocalMessage('master.select.serviceName'));
	
	$('.appendableClass tbody').each(function(i) {
		row = i;
		
		var cmChargeapplicableat = $.trim($("#cmChargeapplicableat"+i).val());
		 if(cmChargeapplicableat==0 || cmChargeapplicableat=="")
			    errorList.push(getLocalMessage(' master.select.charge.applicable '));
		
		 var cmChargeType = $.trim($("#cmChargeType"+i).val());
		 if(cmChargeType==0 || cmChargeType=="")
			    errorList.push(getLocalMessage(' master.select.chargeType '));
		 
		 var cmSlabDepend = $.trim($("#cmSlabDepend"+i).val());
		 if(cmSlabDepend==0 || cmSlabDepend=="")
			    errorList.push(getLocalMessage(' master.select.slab.depend '));
		 
		 var cmChargeType = $.trim($("#cmFlatDepend"+i).val());
		 if(cmChargeType==0 || cmChargeType=="")
			    errorList.push(getLocalMessage(' master.select.flat.depend '));
		 

		 var cmChargeSequence = $.trim($("#cmChargeSequence"+i).val());
		 if(cmChargeSequence==0 || cmChargeSequence=="")
		    errorList.push(getLocalMessage('master.select.charge.sequence'));
		 
		 
		 var cmChargeStartDate = $.trim($("#cmChargeStartDateStr"+i).val());
		 if(cmChargeStartDate=="")
			    errorList.push(getLocalMessage(' master.select.charge.startDate '));
		 
		 var cmChargeEndDate = $.trim($("#cmChargeEndDateStr"+i).val());
		 if( cmChargeEndDate=="")
			    errorList.push(getLocalMessage(' master.select.charge.endDate '));
		 
		 if((new Date(dateFormate(cmChargeStartDate)) > new Date(dateFormate(cmChargeEndDate)))){
				errorList.push(getLocalMessage("master.charge.startDate.less.endDate"));
			}
		
	});
	
	if(errorList.length > 0){ 
		 
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
		
	 }
		
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = __serializeForm(theForm);

	var url	=	$(theForm).attr('action');
	
	var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
	

	if ($("#mode").val() == "A" || $("#mode").val() == "E") {
		var divName = '.form-div';
		$(".widget-content").html(returnData);
		$(".widget-content").show();
		$('#chargeMasterTab').attr('disabled', true);
	} else {
		window.location.href = 'ChargeMaster.html'
	}
	
	return true;
}

var cmId = -1;
$(function () {
	    $("#grid").jqGrid({
	        url: "ChargeMaster.html?getGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("master.ChargeCode"),getLocalMessage("master.ServiceId"),getLocalMessage("master.ChargeDescription"),getLocalMessage("master.ChargeDescriptionReg"), getLocalMessage("sor.startdate"), getLocalMessage("sor.endate"),getLocalMessage("master.editSelected"),getLocalMessage("master.delSelected")],
	        colModel: [
	            { name: "cmId", width:100, sortable: true },
	            { name: "cmServiceId", width:100, sortable: true },
	            { name: "cmChargedescriptionEng", sortable: true },
	            { name: "cmChargedescriptionReg", sortable: true },
	            { name: "fromDateStr", width:100, sortable: false },
	            { name: "toDateStr", width:100, sortable: false },
	            { name: 'cmId', width:50, index: 'cmId', align: 'center', edittype:'radio',formatter:editChecklistMst, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },
      			},
      			{ name: 'cmId', width:50,  index: 'cmId', align: 'center', edittype:'radio',formatter:deleteChecklistMst, editoptions: { value: "Yes:No" },
          			formatoptions: { disabled: false },
      			},
      			
	        ],
	        pager: "#pagered",
	        rowNum: 30,
	        rowList: [5, 10, 20, 30],
	        sortname: "cmId",
	        sortorder: "desc",
	        height:'auto',
	        viewrecords: true,
	        gridview: true,
	        loadonce: false,
	        jsonReader : {
                root: "rows",
                page: "page",
                total: "total",
                records: "records", 
                repeatitems: false,
               }, 
	        autoencode: true,
	        editurl:"ChargeMaster.html?update",
	        caption: "Charge Detail List"
	    }); 
	});
	
	function editChecklistMst(cellValue, options, rowdata, action) {
	    return "<a href='#'  return false; class='editClass' value='"+rowdata.cmId+"'><img src='css/images/edit.png' width='20px' alt='Edit Charge Master' title='Edit Charge Master' /></a>";
	}
	
	 function deleteChecklistMst(cellValue, options, rowdata, action) {
	    return "<a href='#'  return false; class='deleteClass' value='"+rowdata.cmId+"'><img src='css/images/delete.png' width='20px' alt='Delete Charge Master' title='Delete Charge Master' /></a>";
	} 
	
	$(function() {		
		$(document).on('click', '.editClass', function() {
			var gr = jQuery("#grid").jqGrid('getGridParam','selrow');			
			
			$("#errorDivServiceCheckList").hide();
			var url = "ChargeMaster.html?formForUpdate";
			var cmId = $(this).attr('value');
			var returnData = {"cmId" : cmId};
			
			$.ajax({
				url : url,
				datatype: "json",
		        mtype: "POST",
				data : returnData,
				success : function(response) {						
					var divName = '.form-div';
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
				$("#errorDivServiceCheckList").hide();
				var cmId = $(this).attr('value');
				showConfirmBox(cmId);
		});
			
	});
	 
	function showConfirmBox(clmId){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Yes';
		
		message	+='<p>Are you sure want to delete ?</p>';
		 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
		'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
		' onclick="deleteData('+clmId+')"/>'+
		'</p>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function deleteData(cmId){
		
		var url = "ChargeMaster.html?delete";
		var returnData = {"cmId" : cmId}
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
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
		
		$.fancybox.close();
		$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	}
	
	$(function() {	
		$(document).on('click', '.createData', function() {
	
			var url = "ChargeMaster.html?form";
			var returnData = "";
			
			$.ajax({
				url : url,
				success : function(response) {					
					var divName = '.form-div';					
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
	
	function closeOutErrBox(){
		$('.error-div').hide();
	}
	

