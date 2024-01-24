/**
 * ritesh.patil
 * 
 */
estateUrl='EstateMaster.html';
$(document).ready(function(){
	
	$("#estateGrid").jqGrid(
			{   
				url : estateUrl+"?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('estate.label.code'),getLocalMessage('estate.label.name'), getLocalMessage('estate.label.Location'), getLocalMessage('estate.label.Category'),getLocalMessage('estate.label.Type'), getLocalMessage('estate.label.subType'), getLocalMessage('estate.grid.column.view'),getLocalMessage('estate.grid.column.action')],
				colModel : [ {name : "esId",width : 5,  hidden :  true   },
				             {name : "code",width : 30,  sortable :  true ,  },
				             {name : "nameEng",width : 20}, 
				             {name : "locationEng",width : 20,search :false},
				             {name : "categoryName",width : 20,search :false}, 
				             {name : "typeName",width : 20,search :false}, 
				             {name : 'subTypeName',width : 20,search :false}, 
				             { name: 'enbl', index: 'enbl', width: 30, align: 'center !important',formatter:addPropertyLink,search :false},
				             { name: 'enbll', index: 'enbll', width: 30, align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#estatePager",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "code",
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
				caption : getLocalMessage('master.estate.form.name')
			});
	       $("#estateGrid").jqGrid('navGrid','#estatePager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");

	
	      $('#locationId').change(function(){
	    		
	    		var locationId=$("#locationId").val();
	    		$('#estateId').html("");
	    		$('#estateId').append($("<option></option>").attr("value", "").text(getLocalMessage('selectdropdown')));
	    		if(locationId !="0" && locationId != "" && locationId != undefined ){
	    			    var data = { "locId" : locationId };
			    		var ajaxResponse = __doAjaxRequest(estateUrl+'?getEstate', 'POST', data, false, 'json');
			    		$.each(ajaxResponse, function(key, value) {
			    	        $('#estateId').append($("<option></option>").attr("value", key).text(value));
		                  });
			    	}
	    		$('select').trigger("chosen:updated");
	      });
	      
	 $('#addEstateLink').click(function() {
		var ajaxResponse = __doAjaxRequest(estateUrl+'?form', 'POST', {}, false,'html');
		$('.content-page').html(ajaxResponse);
	 });
	 
	$('#btnsearch').click(function(){
		  //var gridParam = { postData: {"locationId" : $('#locationId').val(),
		 //	"estateId" :   $('#estateId').val()}, page: 1 };                        
	     // myGrid.jqGrid('setGridParam', gridParam).trigger("reloadGrid");
	     $("#estateGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
	});
	 
});	 



function addPropertyLink(cellvalue, options, rowObject) 
{
	return "<a class='btn btn-blue-3 btn-sm' title='View Properties' onclick=\"showEstatePropertyGrid('"+rowObject.esId+"')\"><i class='fa fa-building-o'></i></a>";
}


function addLink(cellvalue, options, rowObject) 
{
   return "<a class='btn btn-blue-3 btn-sm' title='View Estate' onclick=\"showEstate('"+rowObject.esId+"','V')\"><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"showEstate('"+rowObject.esId+"','E')\"><i class='fa fa-pencil'></i></a> " +  
	     "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"deleteEstate('"+rowObject.esId+"','"+rowObject.nameEng+"')\"><i class='fa fa-trash'></i></a>";
}


function deleteEstate(esId,estName){
	 requestData = 'esId='+esId;
	 hasProperty = __doAjaxRequest('EstatePropMas.html?checkPropertyAvailable', 'POST', requestData, false,'json');
    if(hasProperty){
	         var yes = getLocalMessage('eip.commons.yes');
			 var warnMsg=getLocalMessage("rl.common.delettion.msg") + " "+ estName ;
			 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
			 message	+='<div class=\'text-center padding-bottom-10\'>'+	
			'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
			' onclick="onDelete(\''+esId+'\')"/>'+	
			'</div>';
		    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
		    $('#yes').focus();
      }else{
    	  var warnMsg=getLocalMessage("rl.property.master.associate.validate.msg");
    	  message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
        }
    
	$(childDivName).html(message);
	$(childDivName).show();
	showModalBox(childDivName);
	return false;
}

function onDelete(esId){
	 var requestData = 'esId='+esId;
	 var response = __doAjaxRequest(estateUrl+'?deleteEstate', 'POST', requestData, false,'json');
     if(response){
		  $("#estateGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');	
	 	  closeFancyOnLinkClick(childDivName);
        }else{
    	     $(childDivName).html("Internal errors");
    		 showModalBox(childDivName);
        }
  }

function showEstate(esId,type){
	var divName	=	formDivName;
    var requestData = 'esId='+esId+'&type='+type;
	var ajaxResponse	=	doAjaxLoading(estateUrl+'?form', requestData, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
	
	if($('#hiddeValue').val() == 'V'){
		 $("#estateForm :input").prop("disabled", true);
		 $("#backBtn").removeProp("disabled");
	}
	if($('#hiddeValue').val() == 'E'){
		 requestData = 'esId='+esId;
		 hasProperty = __doAjaxRequest('EstatePropMas.html?checkPropertyAvailable', 'POST', requestData, false,'json');
	     if(!hasProperty){
	    	 $('#locId,#type1,#type2,#category,#natureOfLand').prop('disabled',true);
	     }
		$("#resetEstate").prop("disabled", true);
	}
}

function showEstatePropertyGrid(esId,type){
    var requestData = 'esId='+esId;
	var ajaxResponse	=	doAjaxLoading('EstatePropMas.html?estatePropGrid', requestData, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
}


function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', estateUrl);
	$("#postMethodForm").submit();
}
