/* used for show applications on dash board */
var empStatusFlag='A';
$(document).ready(function(){
	$('.pending').show();
	$('.completed').hide();
	resulto = getTaskList('PENDING', domReady);
	getEmployeeStatus();
	
	//D#127111
	$('.pendingCOMIT').show();
	$('.completedCOMIT').hide();
	complaints = getComplaintTaskList('PENDING', domComplaintsReady);
}); 

function getEmployeeStatus(){
	var url = "DeptDashBoard.html?getEmployeeStatus";
	
	empStatusFlag = __doAjaxRequest(url, 'post', {}, false,
			'json');
}


function domReady(resulto){
	var table = $('#datatables').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
		"iDisplayLength" : 5, 
		"bInfo" : true,
		"lengthChange": true,
		"bPaginate": true,
		"bFilter": true
	});
	table.rows().remove().draw();
	table.rows.add(resulto);
	table.order( [ 1, 'desc' ] ).draw();

	$('#datatables tbody').on( 'mouseenter', 'td', function () {
		var colIdx = table.cell(this).index().column;
		$( table.cells().nodes() ).removeClass( 'highlight' );
		$( table.column( colIdx ).nodes() ).addClass( 'highlight' );
	});
	
	
	
}

function getTaskListWithStatus(status) {
	$('#deptId option:eq(0)').prop('selected',true);
	$('.chosen-single').html('<span>Select Department</span>');
	if (status == getLocalMessage("dashboard.completedStatus")) {
		$('.completed').show();
		$('.pending').hide();
		$('.table-legend').hide();
		$('#filterState').val('COMPLETED');
	} else {
		$('.pending').show();
		$('.completed').hide();
		$('.table-legend').show();
		$('#filterState').val('PENDING');
	}
	results = getTaskList(status, getByStatus);
}

function getByStatus(results){
	var table1 = $('#datatables').DataTable({"oLanguage": { "sSearch": "" }});
	table1.rows().remove().draw();	
	table1.rows.add(results);
	table1.order( [ 1, 'desc' ] ).draw();
}

function getTaskList(status,callback){
	if ( $.fn.DataTable.isDataTable('#datatables') ) {
		  $('#datatables').DataTable().destroy();
		}
	var filterDataID=$('#deptId').val();
	var takeActionMsg = getLocalMessage("workflow.task.actions.takeAction");
	var viewHistoryMsg = getLocalMessage("workflow.task.actions.viewHistory");
	showloader(true);
	var setUniqId = function(obj) {
		var uniqId = (obj.applicationId != null)?obj.applicationId:((obj.referenceId != null)?obj.referenceId:'');
		var uniqIdToDispaly = (obj.applicationId != null && obj.referenceId != null)?obj.referenceId:uniqId;
		obj.uniqId = uniqId;
		obj.uniqIdToDispaly = uniqIdToDispaly;
	}

	var getReferenceLink = function(obj) {
		var html = '<td class="text-right">'
			+'<a href="#"  onClick="breadcrumb(\''+obj.deptName+'+'+obj.serviceName+'+'+obj.taskName+'\',0);callService(\''+obj.uniqId+'\',\''+obj.serviceId+'\',\''+obj.serviceEventUrl+'\',\''+obj.taskId+ '\',\''+obj.workflowId+'\',\''+obj.taskName+'\',\''+obj.taskStatus+'\')">'
			+obj.uniqIdToDispaly
			+'</a>'
			+'</td>';
		return html;
	}
		
	var getActionButtons = function(obj) {
		var html;
		if(empStatusFlag != 'I'){
			html = '<td>'+
			'<center><button type="button" title="'+takeActionMsg+'" class="task-button btn btn-danger btn-sm actionBtn" onClick="breadcrumb(\''+obj.deptName+'+'+obj.serviceName+'+'+obj.serviceEventName+'\',0);callService(\''+obj.uniqId+'\',\''+obj.serviceId+'\',\''+obj.serviceEventUrl+'\',\''+obj.taskId+ '\',\''+obj.workflowId+'\',\''+obj.taskName+'\',\''+obj.taskStatus+'\');"><i class="fa fa-bell-o"></i></button>'+
			'<button type="button" title="'+viewHistoryMsg+'" class="task-button btn btn-primary btn-sm " onClick="dashboardViewHistory(\''+obj.applicationId+'\',\''+obj.referenceId+'\',\''+obj.serviceName+'\',\''+obj.requestDate + '\','+obj.workflowReqId+')"><i class="fa fa-history"></i></button>'+
			'</center></td>';	
		}else{
			html = '<td>'+
			'<center>'+
			'<button type="button" title="'+viewHistoryMsg+'" class="task-button btn btn-primary btn-sm " onClick="dashboardViewHistory(\''+obj.applicationId+'\',\''+obj.referenceId+'\',\''+obj.serviceName+'\',\''+obj.requestDate + '\','+obj.workflowReqId+')"><i class="fa fa-history"></i></button>'+
			'</center></td>';		
		}
		
		return html;
	}
	
	var getActionButtonsForCompleted = function(obj) {
		var html = '<td>'+
			'<center><button type="button" title="'+viewHistoryMsg+'" class="task-button btn btn-primary btn-sm " onClick="dashboardCompletedViewHistory(\''+obj.applicationId+'\',\''+obj.referenceId+'\',\''+obj.serviceName+'\',\''+obj.requestDate + '\','+obj.workflowReqId+')"><i class="fa fa-history"></i></button>'+
			'</center></td>';
		return html;
	}
	
	setTimeout(function(){
	var result = [];
	
	
	var filterBTValue = $('#filterBTValue').val();

	/*if(filterBTValue!= undefined && filterBTValue!=''){
		var reuqestData= {};
		var fil=filterBTValue.split("-");
		var fromDay=fil[0];
		var toDay=fil[1];
		reuqestData.fromDay=fromDay;
		reuqestData.toDay=toDay;
		//below is the case of reopen
		if(toDay == undefined || toDay==''){
			reuqestData.fromDay="-1";
		}
	}*/
	
	
	
	if(filterDataID=="" || filterDataID==undefined || filterDataID == null){
		var reuqestData={
			"status" : status
		}	
		if(filterBTValue!= undefined && filterBTValue!=''){
			var fil=filterBTValue.split("-");
			var fromDay=fil[0];
			var toDay=fil[1];
			reuqestData.fromDay=fromDay;
			reuqestData.toDay=toDay;
			//below is the case of reopen
			if(toDay == undefined || toDay==''){
				reuqestData.fromDay="-1";
			}
		}
		var ajaxResponse = __doAjaxRequest("DeptDashBoard.html?getGridDataList", 'POST',reuqestData, false,'json');
	}else{
		var reuqestData={
			"deptId" : filterDataID
		}
		if(filterBTValue!= undefined && filterBTValue!=''){
			var fil=filterBTValue.split("-");
			var fromDay=fil[0];
			var toDay=fil[1];
			reuqestData.fromDay=fromDay;
			reuqestData.toDay=toDay;
			//below is the case of reopen
			if(toDay == undefined || toDay==''){
				reuqestData.fromDay="-1";
			}
		}
		var ajaxResponse = __doAjaxRequest("DeptDashBoard.html?getDeptGridDataList", 'POST',reuqestData, false,'json');
	}
	
	let appENV=$('#applicableENV').val();
	if(status==getLocalMessage("dashboard.completedStatus")){
		$.each(ajaxResponse, function(index){
			var obj = ajaxResponse[index];
			var colorCode="";
			//D#131615 #161047
			if(!( appENV=='true' && obj.smShortCode == 'CARE')){
				setUniqId(obj);
				if((obj.taskName!=null) && !(obj.taskName.includes("Start") || obj.taskName.includes("Hidden_Task") || obj.taskName.includes("Reopen By Call Center"))){
					/**For DSCL Color Code for Events***/
					if(obj.serviceEventName == "Grievance"){
						colorCode="text-success";
					}else if(obj.serviceEventName == "Grievance 2"){
						colorCode="text-warning";
					}else if(obj.serviceEventName == "Grievance 3" || obj.serviceEventName == "Grievance 4" || obj.serviceEventName == "Grievance 5"){
						colorCode="text-deep-danger";
					}
					/** END DSCL Color Code for Events***/
					result.push([
						getReferenceLink(obj),
						'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span>'+obj.requestDate,
						'<div class=" '+colorCode+' data-col">'+obj.deptName+'</div>',
						'<div class=" '+colorCode+' data-col">'+obj.serviceName+'</div>',
						'<div class=" '+colorCode+' data-col">'+obj.serviceEventName+'</div>',
						//obj.lastDecision,
						//obj.taskStatus,
						'<div class=" '+colorCode+' data-col">'+obj.taskStatusDesc+'</div>',					
						'<div class="text-center">'+ "-" + '</div>',
						getActionButtonsForCompleted(obj)
						]);
					
			}
			}
			else{
				setUniqId(obj);
			if((obj.taskName!=null) && !(obj.taskName.includes("Start") || obj.taskName.includes("Hidden_Task") || obj.taskName.includes("Reopen By Call Center"))){
				result.push([
					getReferenceLink(obj),
					'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span>'+obj.requestDate,
					obj.deptName,
					obj.serviceName,
					obj.serviceEventName,
					//obj.lastDecision,
					//obj.taskStatus,
					obj.taskStatusDesc,					
					'<div class="text-center">'+ "-" + '</div>',
					getActionButtonsForCompleted(obj)
					]);
				
		}
		}
		});
		//D#127111
		$('#completedITCount').html(result.length);
		
	}else{
		$.each(ajaxResponse, function(index){
			var obj = ajaxResponse[index];
			//D#131615 #161047
			if(!( appENV=='true' && obj.smShortCode == 'CARE')){
				//U#96675
				var colorCode="";
				var remainDays="0";//RJ
				
				if(obj.lastDecision == 'REOPENED'){
					colorCode="text-deep-danger";
				}
				if(obj.serviceEventName == "Grievance"){
					colorCode="text-deep-danger";
				}

				if(obj.taskSlaDurationInMS != "0.0" && obj.taskSlaDurationInMS != null){
					
					let appDate = moment(obj.dateOfAssignment).startOf("day");
					//get difference of two date 
					let subDays = moment().diff(appDate,'days');
					
					let duration = moment.duration(obj.taskSlaDurationInMS, 'milliseconds');
					let slaDays = duration.days();
					remainDays=slaDays-subDays;
					//here in case of negative remainDays than days should be display as 0 told by RJ
					if(obj.lastDecision == 'REOPENED'){
						if(remainDays<0){
							remainDays=0;//RJ
						}
						colorCode="text-deep-danger";
					}else if(remainDays>=0 && remainDays<=3 || remainDays<0 ){
						colorCode="text-warning";
						if(remainDays<0){
							remainDays=0;//RJ
						}
					}else if(remainDays>=4 && remainDays<=6){
						colorCode="text-primary";
					}else{
						colorCode="text-success";
					}
					/**For DSCL Color Code for Events***/
					if(obj.serviceEventName == "Grievance"){
						colorCode="text-success";
					}else if(obj.serviceEventName == "Grievance 2"){
						colorCode="text-warning";
					}else if(obj.serviceEventName == "Grievance 3" || obj.serviceEventName == "Grievance 4" || obj.serviceEventName == "Grievance 5"){
						colorCode="text-deep-danger";
					}
					/** END DSCL Color Code for Events***/
				}
				
				setUniqId(obj);
				var serviceEventName = (obj.serviceEventName != null )?obj.serviceEventName:obj.taskName;
				obj.serviceEventName=serviceEventName;
				if((obj.taskName!=null) && !(obj.taskName.includes("Hidden_Task") || obj.taskName.includes("Reopen By Call Center"))){
					/*result.push([
						getReferenceLink(obj),
						obj.requestDate,
						obj.deptName,
						obj.serviceName,
						obj.serviceEventName,
						//obj.lastDecision,
						obj.taskStatus,
						obj.taskStatusDesc,
						getActionButtons(obj)
						]);*/
					
					//color code set here
					result.push([
						'<div class=" '+colorCode+' data-col">' + getReferenceLink(obj) + '</div>',
						'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span><div class=" '+colorCode+' data-col"><p>'+ obj.requestDate + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>'+ obj.deptName + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>'+ obj.serviceName + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>'+ obj.serviceEventName + '</p></div>',
						//obj.lastDecision,
						/*'<div class=" '+colorCode+' data-col"><p>' + obj.taskStatus + '</p></div>',*/
						'<div class=" '+colorCode+' data-col"><p>' + obj.taskStatusDesc + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>' + remainDays + '</p></div>',
						
						getActionButtons(obj)
						]);
				}			
			}
			else{

				//U#96675
				var colorCode="";
				var remainDays="0";//RJ
				
				if(obj.lastDecision == 'REOPENED'){
					colorCode="text-deep-danger";
				}

				if(obj.taskSlaDurationInMS != "0.0" && obj.taskSlaDurationInMS != null){
					
					let appDate = moment(obj.dateOfAssignment).startOf("day");
					//get difference of two date 
					let subDays = moment().diff(appDate,'days');
					
					let duration = moment.duration(obj.taskSlaDurationInMS, 'milliseconds');
					let slaDays = duration.days();
					remainDays=slaDays-subDays;
					//here in case of negative remainDays than days should be display as 0 told by RJ
					if(obj.lastDecision == 'REOPENED'){
						if(remainDays<0){
							remainDays=0;//RJ
						}
						colorCode="text-deep-danger";
					}else if(remainDays>=0 && remainDays<=3 || remainDays<0 ){
						colorCode="text-warning";
						if(remainDays<0){
							remainDays=0;//RJ
						}
					}else if(remainDays>=4 && remainDays<=6){
						colorCode="text-primary";
					}else{
						colorCode="text-success";
					}
					
				}
				
				setUniqId(obj);
				if((obj.taskName!=null) && !(obj.taskName.includes("Hidden_Task") || obj.taskName.includes("Reopen By Call Center"))){
					/*result.push([
						getReferenceLink(obj),
						obj.requestDate,
						obj.deptName,
						obj.serviceName,
						obj.serviceEventName,
						//obj.lastDecision,
						obj.taskStatus,
						obj.taskStatusDesc,
						getActionButtons(obj)
						]);*/
					
					//color code set here
					result.push([
						'<div class=" '+colorCode+' data-col">' + getReferenceLink(obj) + '</div>',
						'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span><div class=" '+colorCode+' data-col"><p>'+ obj.requestDate + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>'+ obj.deptName + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>'+ obj.serviceName + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>'+ obj.serviceEventName + '</p></div>',
						//obj.lastDecision,
						/*'<div class=" '+colorCode+' data-col"><p>' + obj.taskStatus + '</p></div>',*/
						'<div class=" '+colorCode+' data-col"><p>' + obj.taskStatusDesc + '</p></div>',
						'<div class=" '+colorCode+' data-col"><p>' + remainDays + '</p></div>',
						
						getActionButtons(obj)
						]);
				}
			
			}

		});	
		//D#127111
		$('#pendingITCount').html(result.length);
	}
	/*if(result.length!=0){
		return callback(result)
	}*/
		return callback(result)
	},0);	
}

/* used for take action against application on dash board */ 
function callService(appNo, serviceId, url, taskId, workflowId, taskName,
		taskStatus) {
	
	var data = {};
	data.appNo = appNo;
	data.taskId = serviceId;
	data.actualTaskId = taskId;
	data.workflowId = workflowId;
	data.taskName = taskName;
	if (taskStatus == getLocalMessage("dashboard.completedStatus")) {
		var response = __doAjaxRequest(url + '?viewRefNoDetails', 'post', data,
				false, 'html');
	} else {
		var response = __doAjaxRequest(url + '?showDetails', 'post', data,
				false, 'html');
	}
	$('#taskDiv').html(response);
}

function backToApplicationForm(appNo,serviceId,url,taskId,workflowId,taskName){
	
	var data = {}; 
	data.appNo = appNo;
	data.taskId = serviceId;
	data.actualTaskId = serviceId;//D#121682
	data.workflowId=workflowId;
	data.taskName=taskName;
	var response =__doAjaxRequest(url+'?showDetails', 'post', data, false, 'html');
	$('.content-page').html(response);
}

/* used for show application history */
function dashboardViewHistory(appId,refId,servName,appDate,workflowReqId){
	var requestData = {"appId":appId,"refId":refId,"appDate":appDate,"servName":servName,"workflowReqId":workflowReqId};
	var response =__doAjaxRequest('DeptDashBoard.html?viewFormHistoryDetails', 'post', requestData, false, 'html');
	$('#taskDiv').html(response);
}

/*Defect #127224 used for show completed application history */
function dashboardCompletedViewHistory(appId,refId,servName,appDate,workflowReqId){
	var requestData = {"appId":appId,"refId":refId,"appDate":appDate,"servName":servName,"workflowReqId":workflowReqId};
	var response =__doAjaxRequest('DeptDashBoard.html?viewCompletedFormHistoryDetails', 'post', requestData, false, 'html');
	$('#taskDiv').html(response);
}



//D#127111 start here after any change made in future so please be  careful with variable

function domComplaintsReady(complaints){
	
	var complaintTable = $('#complaintDatatables').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
		"iDisplayLength" : 5, 
		"bInfo" : true,
		"lengthChange": true,
		"bPaginate": true,
		"bFilter": true,
		"aaSorting": []
	});
	complaintTable.rows().remove().draw();
	complaintTable.rows.add(complaints);
	//complaintTable.order( [ 2, 'desc' ] ).draw();
	complaintTable.draw();

	$('#complaintDatatables tbody').on( 'mouseenter', 'td', function () {
		var colIdx = complaintTable.cell(this).index().column;
		$( complaintTable.cells().nodes() ).removeClass( 'highlight' );
		$( complaintTable.column( colIdx ).nodes() ).addClass( 'highlight' );
	});	
}

function getComplaintTaskListWithStatus(status) {
	if (status == getLocalMessage("dashboard.completedStatus")) {
		$('.completedCOMIT').show();
		$('.pendingCOMIT').hide();
		$('.table-legend').hide();
	} else {
		$('.pendingCOMIT').show();
		$('.completedCOMIT').hide();
		$('.table-legend').show();
	}
	complaintResults = getComplaintTaskList(status, getByComplaintStatus);
}

function getByComplaintStatus(complaintResults){
	var complaintTable1 = $('#complaintDatatables').DataTable();
	complaintTable1.rows().remove().draw();	
	complaintTable1.rows.add(complaintResults);
	//complaintTable1.order( [ 2, 'desc' ] ).draw();
	complaintTable1.draw();
}

function getComplaintTaskList(status,complaintCallback){
	var takeActionMsg = getLocalMessage("workflow.task.actions.takeAction");
	var viewHistoryMsg = getLocalMessage("workflow.task.actions.viewHistory");
	showloader(true);
	let setUniqId = function(obj) {
		let uniqId = (obj.applicationId != null)?obj.applicationId:((obj.referenceId != null)?obj.referenceId:'');
		let uniqIdToDispaly = (obj.applicationId != null && obj.referenceId != null)?obj.referenceId:uniqId;
		obj.uniqId = uniqId;
		obj.uniqIdToDispaly = uniqIdToDispaly;
	}

	let getReferenceLink = function(obj) {
		let html = '<td class="text-right">'
			+'<a href="#"  onClick="breadcrumb(\''+obj.deptName+'+'+obj.serviceName+'+'+obj.taskName+'\',0);callService(\''+obj.uniqId+'\',\''+obj.serviceId+'\',\''+obj.serviceEventUrl+'\',\''+obj.taskId+ '\',\''+obj.workflowId+'\',\''+obj.taskName+'\',\''+obj.taskStatus+'\')">'
			+obj.uniqIdToDispaly
			+'</a>'
			+'</td>';
		return html;
	}
		
	let getActionButtons = function(obj) {
		
		let html;
		if(empStatusFlag != 'I'){
			html = '<td>'+
			'<center><button type="button" title="'+takeActionMsg+'" class="task-button btn btn-danger btn-sm actionBtn" onClick="breadcrumb(\''+obj.deptName+'+'+obj.serviceName+'+'+obj.serviceEventName+'\',0);callService(\''+obj.uniqId+'\',\''+obj.serviceId+'\',\''+obj.serviceEventUrl+'\',\''+obj.taskId+ '\',\''+obj.workflowId+'\',\''+obj.taskName+'\',\''+obj.taskStatus+'\');"><i class="fa fa-bell-o"></i></button>'+
			'<button type="button" title="'+viewHistoryMsg+'" class="task-button btn btn-primary btn-sm " onClick="dashboardViewHistory(\''+obj.applicationId+'\',\''+obj.referenceId+'\',\''+obj.serviceName+'\',\''+obj.requestDate + '\','+obj.workflowReqId+')"><i class="fa fa-history"></i></button>'+
			'</center></td>';	
		}else{
			html = '<td>'+
			'<center>'+
			'<button type="button" title="'+viewHistoryMsg+'" class="task-button btn btn-primary btn-sm " onClick="dashboardViewHistory(\''+obj.applicationId+'\',\''+obj.referenceId+'\',\''+obj.serviceName+'\',\''+obj.requestDate + '\','+obj.workflowReqId+')"><i class="fa fa-history"></i></button>'+
			'</center></td>';		
		}
		
		return html;
	}
	
	let getActionButtonsForCompleted = function(obj) {
		let html = '<td>'+
			'<center><button type="button" title="'+viewHistoryMsg+'" class="task-button btn btn-primary btn-sm " onClick="dashboardCompletedViewHistory(\''+obj.applicationId+'\',\''+obj.referenceId+'\',\''+obj.serviceName+'\',\''+obj.requestDate + '\','+obj.workflowReqId+')"><i class="fa fa-history"></i></button>'+
			'</center></td>';
		return html;
	}
	let reuqestData={
			"status" : status
	}	
	setTimeout(function(){
	let complaintResult = [];
	
	let filterBTValue = $('#filterBTValue').val();

	if(filterBTValue!= undefined && filterBTValue!=''){
		var fil=filterBTValue.split("-");
		var fromDay=fil[0];
		var toDay=fil[1];
		reuqestData.fromDay=fromDay;
		reuqestData.toDay=toDay;
		//below is the case of reopen
		if(toDay == undefined || toDay==''){
			reuqestData.fromDay="-1";
		}
	}
	
	let ajaxResponse = __doAjaxRequest("DeptDashBoard.html?getComplaintGridDataList", 'POST',reuqestData, false,'json');
	if(status==getLocalMessage("dashboard.completedStatus")){
		$.each(ajaxResponse, function(index){
			let obj = ajaxResponse[index];
			setUniqId(obj);
			if((obj.taskName!=null) && !(obj.taskName.includes("Start") || obj.taskName.includes("Hidden_Task") || obj.taskName.includes("Reopen By Call Center"))){
				complaintResult.push([
					getReferenceLink(obj),
/*					'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span>'+obj.requestDate,
*/					obj.mobileNo,
					obj.comments,
					obj.attPath,
					//obj.lastDecision,
					//obj.taskStatus,
					obj.taskStatusDesc,					
					'<div class="text-center">'+ "-" + '</div>',
					getActionButtonsForCompleted(obj)
					]);
			}
		});

		var compLength=complaintResult.length;
		$('#completedCOMITCount').html(compLength);
		
		
	}else{
		$.each(ajaxResponse, function(index){
			let obj = ajaxResponse[index];
			
			//U#96675
			let colorCode="";
			let remainDays="0";//RJ
			
			if(obj.lastDecision == 'REOPENED'){
				colorCode="text-deep-danger";
			}

			if(obj.taskSlaDurationInMS != "0.0" && obj.taskSlaDurationInMS != null){
				
				//let appDate = moment(obj.requestDate,'DD/MM/YYYY hh:mm a');
				let appDate = moment(obj.dateOfAssignment);
				//get difference of two date 
				let subDays = moment().diff(appDate,'days');
				
				let duration = moment.duration(obj.taskSlaDurationInMS, 'milliseconds');
				let slaDays = duration.days();
				remainDays=slaDays-subDays;
				//here in case of negative remainDays than days should be display as 0 told by RJ
				if(obj.lastDecision == 'REOPENED'){
					if(remainDays<0){
						remainDays=0;//RJ
					}
					colorCode="text-deep-danger";
				}else if(remainDays>=0 && remainDays<=3 || remainDays<0 ){
					colorCode="text-warning";
					if(remainDays<0){
						remainDays=0;//RJ
					}
				}else if(remainDays>=4 && remainDays<=6){
					colorCode="text-primary";
				}else{
					colorCode="text-success";
				}
				
			}
			
			setUniqId(obj);
			if((obj.taskName!=null) && !(obj.taskName.includes("Hidden_Task") || obj.taskName.includes("Reopen By Call Center"))){
				/*result.push([
					getReferenceLink(obj),
					obj.requestDate,
					obj.deptName,
					obj.serviceName,
					obj.serviceEventName,
					//obj.lastDecision,
					obj.taskStatus,
					obj.taskStatusDesc,
					getActionButtons(obj)
					]);*/
				
				//color code set here
				complaintResult.push([
					'<div class=" '+colorCode+' data-col">' + getReferenceLink(obj) + '</div>',
					/*'<span style="display:none">'+moment(obj.requestDate,'DD/MM/YYYY HH:mm A').format("YYYYMMDD HHmm")+'</span><div class=" '+colorCode+' data-col"><p>'+ obj.requestDate + '</p></div>',*/
					'<div class=" '+colorCode+' data-col"><p>'+ obj.mobileNo + '</p></div>',
					'<div class=" '+colorCode+' data-col"><p>'+ obj.comments + '</p></div>',
					'<div class=" '+colorCode+' data-col"><p>'+ obj.attPath + '</p></div>',
					//obj.lastDecision,
					/*'<div class=" '+colorCode+' data-col"><p>' + obj.taskStatus + '</p></div>',*/
					'<div class=" '+colorCode+' data-col"><p>' + obj.taskStatusDesc + '</p></div>',
					'<div class=" '+colorCode+' data-col"><p>' + remainDays + '</p></div>',
					
					getActionButtons(obj)
					]);
			}
			
		});	
		//D#127111
		
		var pendLength=complaintResult.length;
		$('#pendingCOMITCount').html(pendLength);
	}
	/*if(complaintResult.length!=0){
		return complaintCallback(complaintResult)
	}*/
	return complaintCallback(complaintResult);
	},0);	
}

function hitFilterValue(filterValue){
	$('#filterBTValue').val(filterValue);
}
function removeFilterValue(){
	$('#deptId option:eq(0)').prop('selected',true);
	$('#filterBTValue').val("");
	
}

function deptFillter() {//US-141152
	var flterStatus=$('#filterState').val();
	
	
	if (flterStatus == 'COMPLETED') {
		$('.completed').show();
		$('.pending').hide();
		$('.table-legend').hide();
	} else {
		$('.pending').show();
		$('.completed').hide();
		$('.table-legend').show();
	}
	results = getTaskList(flterStatus, getByStatus);
}
