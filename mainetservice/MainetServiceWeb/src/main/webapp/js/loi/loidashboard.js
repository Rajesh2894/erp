loiPrintingUrl='LoiPrintingController.html';
	$(function () {
	    $("#loigrid").jqGrid({
	        url: "LoiPrintingController.html?getLoiPrintData",
	        datatype: "json",
	        mtype: "POST",
	        colNames: [getLocalMessage("master.ApplicationNo"),getLocalMessage("master.serviceId"),getLocalMessage("master.loi.applicant.name"),getLocalMessage("master.loi.ApplicationDate"), getLocalMessage("master.serviceName"),getLocalMessage("master.LoiNo"),getLocalMessage("master.LoiAmount"), getLocalMessage("view.msg")],
	        colModel: [
	            { name: "loiApplicationId", sortable: true ,align: 'center' },
	            { name: "loiServiceId",  sortable: false ,align: 'center',hidden: true },
	            { name: "applicantName",  sortable: false ,align: 'center'},
	            { name: "applDate",sortable: false ,align: 'center'},
	            { name: "serviceName",  sortable: false },
	            { name: "loiNo", sortable: false ,align: 'center'},
	           /* { name: "loiRefId", width: 50, sortable: false ,align: 'center'},*/
	            { name: "loiAmt",  sortable: false,align: 'center' },
	           /* { name: "loiPaid", width: 40, sortable: false },*/
	            { name: 'enbl', index: 'enbl', align: 'center',formatter:addLink}
	        ],
	        pager: "#loipagered",
	        rowNum: 10,
	        rowList: [10, 20, 30, 40],
	        sortname: "loiApplicationId",
	        sortorder: "desc",
	        height:'auto',
	        viewrecords: true,
	        gridview: true,
	        loadonce: true,
	        postData : {    
				deptIdHidden : function() {
					  return $("#deptId").val();
				},
				serviceIdHidden : function() {
					  return $("#serviceId").val();
				},
				appIdHidden : function() {
					  return $("#appId").val();
				},
				loiNoHidden : function() {
					  return $("#loiNo").val();
				},
				applicantNameHidden : function() {
					  return $("#applicantName").val();
				}
			 },
	        jsonReader : {
                root: "rows",
                page: "page",
                total: "total",
                records: "records", 
                repeatitems: false,
               }, 
	        autoencode: true,
	        caption: getLocalMessage("loi.list"),
	        	loadComplete: function(data){ 
					var errorList=[];
					if(jQuery('#flowGrid').jqGrid('getGridParam', 'reccount')==0){
						 errorList.push(getLocalMessage('master.loi.data'));
					}
					if(errorList.length>0){
						showRLValidation(errorList);
					}else{
						$('.error-div').hide();
					}
				   },
	    }); 
	     $("#loigrid").jqGrid('navGrid','#loipagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_center").css("width", "");
	    
	    $('#searchData').click(function(){
		  var errorList=[];
		 $('#loigrid').jqGrid('clearGridData').trigger('reloadGrid');
		 $("#deptIdHidden").val($("#deptId").val());
		 $("#serviceIdHidden").val( $("#serviceId").val());
		 $("#appIdHidden").val( $("#appId").val());
		 $("#loiNoHidden").val( $("#loiNo").val());
		 $("#applicantNameHidden").val($("#applicantName").val());
		 
		 
		 if($("#deptId").val() == 0 &&  $("#serviceId").val() == 0 && $("#appId").val() == 0 && $("#loiNo").val() =='0'){
			 errorList.push(getLocalMessage('master.validate.search'));			 
		 }if($("#deptId").val()>0 ||  $("#serviceId").val()>0 || $("#appId").val()>0 || $("#loiNo").val() !='0' || $("#applicantName").val()!=""){
			 $("#loigrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
		 }
	    if(errorList.length>0){
			showRLValidation(errorList);
		}else{
			$('.error-div').hide();
		}
	});
	    
	}); 
	

    function addLink(cellvalue, options, rowObject) 
	{
	  return "<a href='#' style='height:25px;width:120px;color: blue;text-decoration: underline;'   onclick=\"callLoi('"+rowObject.loiApplicationId+"','"+ rowObject.loiServiceId +"')\" >Click</a>";
	}
	
	
function callLoi(appNo,service){
	    var divName = '.content-page';
		var url ="LoiPrintingController.html?showDetails";
		var data={"appId":appNo,
		         "serviceId":service};
		var response=__doAjaxRequest(url, 'post',data,false, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
	}
	/*function printLoiContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}*/
	
function printLoiContent(printpage) {
		
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
	
function back() {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action',loiPrintingUrl);
		$("#postMethodForm").submit();
	}

function getServices(obj){
	  	var requestData = {"orgId":$('#orgId').val(),"deptId":$(obj).val()};
	  	$('#serviceId').html('');
	  	$('#serviceId').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown')));
	   	
	  	var result=__doAjaxRequest("LoiPrintingController.html?services",'post',requestData,false,'json');
	  	var serviceList=result[0];
	  	
	  	 $.each(serviceList, function(index, value) {
	  		 if($('#langId').val() == 1){
	  			$('#serviceId').append($("<option></option>").attr("value",value[0]).attr("code",value[2]).text(value[1]));
	  		 }else{
	  			$('#serviceId').append($("<option></option>").attr("value",value[0]).attr("code",value[2]).text(value[3]));
	  		 }
	  	});
	  		$('#serviceId').trigger("chosen:updated");
	  };
	  

function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	return false;
}
	