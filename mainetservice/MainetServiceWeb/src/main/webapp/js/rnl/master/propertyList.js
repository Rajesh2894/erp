/**
 * ritesh.patil
 * 
 */
propertyUrl='EstatePropMas.html';
$(document).ready(function(){
	$("#propertyGrid").jqGrid(
			{   
				url : propertyUrl+"?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('rnl.master.property'),getLocalMessage('rl.property.label.name'),getLocalMessage('rl.property.label.unitno'), getLocalMessage('rl.property.label.Occupancy'), getLocalMessage('rl.property.label.Usage'),getLocalMessage('rl.property.label.Floor'), getLocalMessage('rl.property.label.totalArea'),getLocalMessage('estate.grid.column.action')],
				colModel : [ {name : "propId",width : 5,  hidden :  true   },
							 {name : "code",width : 30,  sortable :  true },
				             {name : "name",width : 30,  sortable :  true  },
				             {name : "unitNo",width : 20,search :true}, 
				             {name : "occValue",width : 20,search :false},
				             {name : "usageValue",width : 20,search :false}, 
				             {name : 'floorValue',width : 20,search :false},
				             {name : 'totalArea',width : 20,search :false}, 
				             { name: 'enbll', index: 'enbll', width: 30, align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#propertyPager",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "name",
				sortorder : "desc",
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : true,
				postData : {    
					locationId : function() {
						  return $('#locationId').val();
					},
					estateId:function() {
						   return $('#estateId').val();
					}
				 },
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,					
				},
				autoencode : true,
				caption : getLocalMessage('rl.master.property.form.name')
			});
	       $("#propertyGrid").jqGrid('navGrid','#propertyPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");

	
	      $('#locationId').change(function(){
	    		
	    		var locationId=$("#locationId").val();
	    		$('#estateId').html("");
	    		$('#estateId').append($("<option></option>").attr("value", "").text(getLocalMessage('selectdropdown')))
	    		if(locationId !="0" && locationId != "" && locationId != undefined ){
			    		var data = { "locId" : locationId };
			    		var ajaxResponse = __doAjaxRequest('EstateMaster.html?getEstate', 'POST', data, false, 'json');
			    		$.each(ajaxResponse, function(key, value) {
			    	        $('#estateId').append($("<option></option>").attr("value", key).text(value));
		                  });
	    		  } 
	    		$('select').trigger("chosen:updated");
	      });
	      
	      $('#estateId').change(function(){
	    	  $('.error-div').hide();
	    	  
	      });
	      
	 $('#addPropLink').click(function() {
		var ajaxResponse = __doAjaxRequest(propertyUrl+'?form', 'POST', {}, false,'html');
		$('.content-page').html(ajaxResponse);
	 });
	 
	$('#btnsearch').click(function(){
		
		 if($("#locationId").val() == '' || $("#estateId").val() == ''){
			 var errorList = [];
			 errorList.push(getLocalMessage('rnl.select.location.estate'));
			 showRLValidation(errorList)
		  }else{
			  $("#propertyGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
		  }
	});
	 
});	 



function addLink(cellvalue, options, rowObject) 
{ 
   return  "<a class='btn btn-blue-3 btn-sm' title='View Properties' onclick=\"showProperty('"+rowObject.propId+"','V')\"><i class='fa fa-building-o'></i></a> " +
   		   " <a class='btn btn-warning btn-sm' title='Edit' onclick=\"showProperty('"+rowObject.propId+"','E')\"><i class='fa fa-pencil'></i></a> " ;  
	      /* " <a class='btn btn-danger btn-sm' title='Delete' onclick=\"deleteProperty('"+rowObject.propId+"','"+rowObject.name+"')\"><i class='fa fa-trash'></i></a>";*/
}


function deleteProperty(propId,name){
	requestData = 'propId='+propId;
	 hasPropertyBooking = __doAjaxRequest('EstateBooking.html?IsPropAssociated', 'POST', requestData, false,'json');
	   if(hasPropertyBooking){
		     hasPropertyContract = __doAjaxRequest('EstateContractMapping.html?IsPropAssociated', 'POST', requestData, false,'json');
		     if(hasPropertyContract){
		             var yes = getLocalMessage('eip.commons.yes');
					 var warnMsg="Are you sure to Inactive " + name ;
					 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
					 message	+='<div class=\'text-center padding-bottom-10\'>'+	
					'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
					' onclick="onDelete(\''+propId+'\')"/>'+	
					'</div>';
		         }else{
				 	  var warnMsg=getLocalMessage("rnl.property.associated.contract");
				 	  message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
		          }
	         }else{
	        	  var warnMsg=getLocalMessage("rnl.property.associated.booking");
			 	  message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	         }
	     
			$(childDivName).addClass('ok-msg').removeClass('warn-msg');
			$(childDivName).html(message);
			$(childDivName).show();
			$('#yes').focus();
			showModalBox(childDivName);
			return false;
}

function onDelete(propId){
	 var requestData = 'propId='+propId;
	 var response = __doAjaxRequest(propertyUrl+'?deleteEstate', 'POST', requestData, false,'json');
	 if(response){
			$("#propertyGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
			closeFancyOnLinkClick(childDivName);
	     }else{
	    	    $(childDivName).html("Internal errors");
	    		showModalBox(childDivName);
	     }
}
function getProperty(esId){
requestData = 'esId='+esId;
addProperty = __doAjaxRequest('EstatePropMas.html?getPropertyAvailable', 'POST', requestData, false, 'json');
if(addProperty!= null)
	showProperty(addProperty,'E');
}

function showProperty(propId,type){
	
	var divName	=	formDivName;
    var requestData = 'propId='+propId+'&type='+type;
	var ajaxResponse	=	doAjaxLoading(propertyUrl+'?form', requestData, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
	if($('#hiddeValue').val() == 'V'){
		 $("#propForm :input").prop("disabled", true);
		 $("#backBtn").removeProp("disabled");
		 $('.addCF').bind('click', false);
		 $('.remCF').bind('click', false);
	}
	if($('#hiddeValue').val() == 'E'){
		 $("#resetProp").prop("disabled", true);
	}
}


function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    errMsg += '<ul>';
    $.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', propertyUrl);
	$("#postMethodForm").submit();
}
