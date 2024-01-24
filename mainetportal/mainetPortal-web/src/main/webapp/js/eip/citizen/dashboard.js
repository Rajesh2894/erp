 var url='CitizenDashBoard.html';var method='POST'; var datatype='json';


	$(function () {
	    $("#grid").jqGrid({
	        url: url+"?getGridData",
	        datatype: datatype,
	        mtype: method,
	        colNames: [getLocalMessage("citizen.dashboard.applNo"),getLocalMessage("citizen.dashboard.appldate"),getLocalMessage("citizen.dashboard.dept"), getLocalMessage("citizen.dashboard.service"), 
	        	getLocalMessage("citizen.dashboard.service.reg"),getLocalMessage("citizen.dashboard.status"),getLocalMessage("citizen.dashboard.action")],
	        colModel: [
	            { name: "appId", width: 25, sortable: true },
	            { name: "appDate", width: 30, sortable: true },
	            { name: "deptName", width: 40, sortable: true },
	            { name: "serviceName", width: 40, sortable: true ,search :false },
	            { name: "smServiceNameMar", width: 40, sortable: false,search :false },
	            { name: "status", width: 20, sortable: true,search :false },
	            { name: 'enbl', index: 'enbl', width: 20, align: 'center !important',search :false,formatter:addLink}
	        ],
	        pager: "#pagered",
	        rowNum: 10,
	        rowList: [10, 20, 30, 40],
	        sortname: "appId",
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
	        caption: "Pending Application List"
	    }).navGrid('#pagered', {
			add : false,
			edit : false,
			del : false,
			search : true,
			refresh : false,
			view : false
		},{},{},{},{closeAfterSearch: true,
			closeAfterReset: true},{
			odata :['equal', 'not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain'],sopt: ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc','nu','nn'] ,
						});
	}); 
	
$(function () {
    $("#gridClosed").jqGrid({
        url: url+"?getGridClosedData",
        datatype: datatype,
        mtype: method,
        colNames: [getLocalMessage("citizen.dashboard.applNo"),getLocalMessage("citizen.dashboard.appldate"),
        	getLocalMessage("citizen.dashboard.dept"), getLocalMessage("citizen.dashboard.service"), 
        	getLocalMessage("citizen.dashboard.service.reg"),getLocalMessage("citizen.dashboard.status"),getLocalMessage("citizen.dashboard.action")],
        colModel: [
            { name: "appId", width: 25, sortable: true },
            { name: "appDate", width: 30, sortable: true },
            { name: "deptName", width: 40, sortable: true },
            { name: "serviceName", width: 40, sortable: true ,search :false },
            { name: "smServiceNameMar", width: 40, sortable: false,search :false },
            { name: "status", width: 20, sortable: true,search :false },
            { name: 'enbl', index: 'enbl', width: 20, align: 'center !important',search :false,formatter:addLink}
        ],
        pager: "#pageredClosed",
        rowNum: 10,
        rowList: [10, 20, 30, 40],
        sortname: "appId",
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
        caption: "Closed Application List"
    }).navGrid('#pageredClosed', {
		add : false,
		edit : false,
		del : false,
		search : true,
		refresh : false,
		view : false
	},{},{},{},{closeAfterSearch: true,
		closeAfterReset: true},{
		odata :['equal', 'not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain'],sopt: ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc','nu','nn'] ,
					});
}); 


	
	$(function () {
	    $("#gridLOI").jqGrid({
	    	url: url+"?getGridLOIData",
	    	datatype: datatype,
	        mtype: method,
	        colNames: [getLocalMessage("citizen.dashboard.applNo"),getLocalMessage("citizen.dashboard.appldate"),
	        	getLocalMessage("citizen.dashboard.dept"), getLocalMessage("citizen.dashboard.service"), 
	        	getLocalMessage("citizen.dashboard.service.reg"),getLocalMessage("citizen.dashboard.action")],
	        colModel: [
	            { name: "appId", width: 25, sortable: true },
	            { name: "appDate", width: 30, sortable: true },
	            { name: "deptName", width: 40, sortable: true },
	            { name: "serviceName", width: 40, sortable: true ,search :false},
	            { name: "smServiceNameMar", width: 40, sortable: false,search :false},
	            { name: 'enbl', index: 'enbl', width: 20, align: 'center !important',formatter:addLinkLOI}
	        ],
	        pager: "#pageredLOI",
	        rowNum: 10,
	        rowList: [10, 20, 30, 40],
	        sortname: "appId",
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
	        caption: "Payment Pending Application List"
	    }).navGrid('#pageredLOI', {
			add : false,
			edit : false,
			del : false,
			search : true,
			refresh : false,
			view : false
		},{},{},{},{closeAfterSearch: true,
			closeAfterReset: true},{
			odata :['equal', 'not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain'],sopt: ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc','nu','nn'] ,
						});
	}); 
	
	
	$(function () {
	    $("#gridHold").jqGrid({
	    	url: url+"?getGridHoldData",
	    	datatype: datatype,
	        mtype: method,
	        colNames: [getLocalMessage("citizen.dashboard.applNo"),getLocalMessage("citizen.dashboard.appldate"),
	        	getLocalMessage("citizen.dashboard.dept"), getLocalMessage("citizen.dashboard.service"), 
	        	getLocalMessage("citizen.dashboard.service.reg"),getLocalMessage("citizen.dashboard.action")],
	        colModel: [
	            { name: "appId", width: 20, sortable: true },
	            { name: "appDate", width: 25, sortable: true },
	            { name: "deptName", width: 40, sortable: true },
	            { name: "serviceName", width: 40, sortable: true,search :false },
	            { name: "smServiceNameMar", width: 40, sortable: false,search :false },
	            { name: 'hold', index: 'hold', width: 20, align: 'center !important',formatter:addLinkHold},
	        ],
	        pager: "#pageredHold",
	        rowNum: 10,
	        rowList: [10, 20, 30, 40],
	        sortname: "appId",
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
	        caption: "Hold Application List"
	    }).navGrid('#pageredHold', {
			add : false,
			edit : false,
			del : false,
			search : true,
			refresh : false,
			view : false
		},{},{},{},{closeAfterSearch: true,
			closeAfterReset: true},{
			odata :['equal', 'not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain'],sopt: ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc','nu','nn'] ,
						}); 
	}); 
	
	
	
	function addLink(cellvalue, options, rowObject) 
	{
 /*"<a href='#' style='height:25px;width:120px;color: blue;text-decoration: underline;'   onclick=\"dashboardView('"+rowObject.appId+"','"+rowObject.smShortdesc+"','"+rowObject.orgId+"')\" >View</a>"+*/
		  return  "<a class='btn btn-blue-2 btn-sm '  title='View Application' onclick=\"dashboardView('"+rowObject.appId+"','"+rowObject.smShortdesc+"','"+rowObject.orgId+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
	             "<a class='btn btn-danger  btn-sm ' title='View Applicaiton History'  onclick=\"dashboardViewHistory('"+rowObject.appId+"','"+rowObject.smShortdesc+"','"+rowObject.orgId+"')\"><i class='fa fa-history''></i></a> " ;
	        
	}
	
	function addLinkLOI(cellvalue, options, rowObject) 
	{
	  return "<a href='#' style='height:25px;width:120px;color: blue;text-decoration: underline;'   onclick=\"callService('"+rowObject.appId+"','"+ rowObject.eventUrl +"')\" >Click</a>";
	}
	
	function addLinkHold(cellvalue, options, rowObject) 
	{
		//"<a href='#' style='height:25px;width:120px;color: blue;text-decoration: underline;'   onclick=\"callServiceHold('"+rowObject.appId+"','"+ rowObject.eventUrl +"')\" >ReSubmit</a>"+
	  return "<a class='btn btn-warning  btn-sm ' title='ReSubmit Applicaiton'  onclick=\"callServiceHold('"+rowObject.appId+"','"+ rowObject.eventUrl +"')\"><i class='fa fa-paper-plane-o''></i></a> "+
	        "<a class='btn btn-danger  btn-sm ' title='View Applicaiton History'  onclick=\"dashboardViewHistory('"+rowObject.appId+"','"+rowObject.smShortdesc+"','"+rowObject.orgId+"')\"><i class='fa fa-history''></i></a> ";
	}
	
	
	
	
	function callService(appNo,shortCode){
		var response=__doAjaxRequest(url+'?showDetails', 'post','appId='+appId+'&orgId='+orgId+'&shortName='+shortCode,false, 'html');
		$('#dashBoardDiv').html(response);
	}
	
	function callServiceHold(appNo,eventUrl){
		var response=__doAjaxRequest(eventUrl+'?applId', 'post','appId='+appNo,false, 'html');
		$('#dashBoardDiv').html(response);
	}
	
	function dashboardView(appId,shortCode,orgId){
		/*var divName	=	'#dashBoardDiv';
	    var requestData = 'appId='+appId+'&orgId='+orgId+'&shortName='+shortCode;
		var ajaxResponse	=	doAjaxLoading(url+'?viewFormDetails', requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		//prepareTags();
		$(divName).show();*/
}
	
	function dashboardViewHistory(appId,shortCode,orgId){
		var divName	=	'#dashBoardDiv';
	    var requestData = 'appId='+appId+'&orgId='+orgId+'&shortName='+shortCode;
		var ajaxResponse	=	doAjaxLoading(url+'?viewFormHistoryDetails', requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		$(divName).show();
}
	/*Earlier getting both referenceId & applicationId from same column(apm_application_id) 
	 * Now we have added one more column reference_id and now we are getting both values in respective columns.*/
	function dashboardViewHistory(appId,refId,shortCode,orgId){
		var divName	=	'#dashBoardDiv';
	    var requestData = 'appId='+appId+'&orgId='+orgId+'&shortName='+shortCode+'&refId='+refId;
		var ajaxResponse	=	doAjaxLoading(url+'?showHistoryDetails', requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		$(divName).show();
	}
	
	function onlineFailureRepayment(appId){
		var divName	=	'#dashBoardDiv';
	    var requestData = 'rowId='+appId;
		var ajaxResponse	=	doAjaxLoading('RePaymentForm.html?edit', requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		$(divName).show();
}
	
	function  ViewBillHistory(proertyNo)
	{
		
		var data = {"proertyNo":proertyNo}; 
		
		var response=__doAjaxRequest('ViewPropertyDetail.html?viewBillHistory', 'POST',data,false);

		$(formDivName).html(response);
		 
	}


	function  ViewPaymentHistory(proertyNo)
	{
		
		var data = {"proertyNo":proertyNo}; 
		
		var response=__doAjaxRequest('ViewPropertyDetail.html?viewPaymentHistory', 'POST',data,false);

		
		$(formDivName).html(response);
		 
	}
	

	function dashboardPropertyView(proertyNo,deptShortCode)
	{
		
		var data = {"deptShortCode":deptShortCode}; 
		
		var response=__doAjaxRequest('CitizenDashBoard.html?getUrlForBillPayStatement', 'POST',data,false);
		var actionParam = "viewDetails";
		
		var data={"proertyNo":proertyNo};
		
		var viewFormDetails =__doAjaxRequest(response+'?'+actionParam, 'POST', data, false);

		$(formDivName).html(viewFormDetails);
	}

function backProperty()
{

	
	  var data={};
	  
	var response=__doAjaxRequest('ViewPropertyDetail.html?BackToBillHistory', 'POST',data,false);

	$(formDivName).html(response);
	
	
	
	
}

function doLoiPayment(appId,taskId){
	var data={"rowId":appId,"taskId":taskId};
	var response =__doAjaxRequest('PendingLoiForm.html?edit', 'POST', data, false);
	$(formDivName).html(response);
}

function dashboardDownload(docName,docPath,filePath){
	
	 var requestData = 'docName='+docName+'&docPath='+docPath;
	 var appList=__doAjaxRequest('CitizenDashBoard.html?getDocument', 'post',requestData,false, 'json');
    var filePath=filePath;
    var action="rtsService.html?Download";
   downloadFile(filePath,action)
	
}

function downloadFile(filePath,action)
{
	
	var csrf_token = $('meta[name=_csrf]').attr('content');
   var csrf_param = $('meta[name=_csrf_header]').attr('content');
   
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action=action;
	my_form.target='_blank';
	
	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='downloadLink';
	my_tb.value=filePath;
	my_form.appendChild(my_tb); 
	
	
	var	my_tb_csrf=document.createElement('INPUT');
	my_tb_csrf.type='HIDDEN';
	my_tb_csrf.name= '_csrf';
	my_tb_csrf.value = csrf_token;
	my_form.appendChild(my_tb_csrf); 
	
	document.body.appendChild(my_form);
	my_form.submit();

	    
}


//Defect #117792
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

