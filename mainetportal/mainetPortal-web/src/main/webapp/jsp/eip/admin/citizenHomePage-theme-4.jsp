<div id="onlineServices" title="D2K Application" style="display: none"
	class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script src="js/eip/citizen/dashboard.js"></script>
<script src="js/mainet/dashboard/moment.min.js"></script>
<script
	src="js/eip/citizen/citizenResetPasswordProcess.js"></script>
<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script> 
<script src="js/eip/citizen/highcharts.js"></script>
<script src="js/eip/citizen/highcharts-3d.js"></script>

<style>
.content-page .content{   background: none !important;}
.card-box .parent-tab {    border-bottom: 2px solid #dfdede6b;background: #dfdede6b;}
.card-box  .parent-tab > li.active > a, .nav-tabs > li.active > a:focus, .nav-tabs > li.active > a:hover { border-width: 0; background-color: #f2f1f1;}
.card-box  .parent-tab  > li > a { border: none; color: #373737 !important; font-size: 1.1em; }
.card-box  .parent-tab > li.active > a::after, .parent-tab > li:hover > a::after { transform: scale(1); height:4px;}
.tab-nav > li > a::after { background: #21527d none repeat scroll 0% 0%; color: #fff; }
.tab-pane { padding: 15px 0; }
.tab-content{margin-bottom: 30px;}
#dash-nav .nav-tabs>li>a{background: #000000;}
.card-box {background: #FFF none repeat scroll 0% 0%; box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.3); margin-bottom: 30px; }
.nav>li>a,.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover{color:#fff;}
</style>

<script>
var setp2Form = 'verifyOTPForm';

function getCLoginForm() {
	var msg = getLocalMessage("citizen.login.resetPass.success.msg");

	var childPoppup = '.popup-dialog';

	$(childPoppup).html("<h3>"+msg+"</h3>");
	$(childPoppup).show();
	showModalBox(childPoppup);
}

function getCitizenResetPassStepII() {

		var formData = $(document.getElementById(setpIFormName)).serialize();
		var response = __doAjaxRequest(citizenResetPassword+getOTPform, 'post', formData, false, 'html');
		openPopupWithResponse(response);
	
}

document.onscroll = function() {
    if (window.innerHeight + window.scrollY+300 > document.body.clientHeight) {
       $('.mbr-arrow').css('display','none');
    }
    else{
    	$('.mbr-arrow').css('display','block');
    }
}
	$(document).ready(function() {
	    $(".mbr-arrow a").click(function(event){
	        $('html, body').animate({scrollTop: '+=630px'}, 800);
	    });
	});
</script>
<script>
var closedCount=0;
var holdCount=0;
var payPendCount=0;
var pendingCount=0;
var expireCount=0;
var loiPendingCount=0;
var draftCount=0;
var firstAppealCount=0;
var secondAppealCount=0; 

var pendingList = [];
var holdList = [];
var closedList = [];
var payPendList = [];
var expireList = [];
//var loiPaymentList = [];
var billingList =[];
var waterBillingList =[];
var draftList = [];
var firstAppealList = [];
var secondAppealList = [];

$(document).ready(function(){
	
	 var appList=__doAjaxRequest('CitizenDashBoard.html?getApplications', 'post','',false, 'json');
	 if(appList.length=='0'){
		 $("#container").hide();
	 }else{
		 $("#container").show();
	 }
	
	 //added
	 
	 
	 var closedTable = $('#closedTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	 
	 var pendingTable = $('#pendingTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	 
	 var holdTable = $('#holdTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	 
	 
	 var expiredTable = $('#expiredTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});


	 
	 var draftModeTable = $('#draftModeTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	 
	 
	 var firstAppealTable = $('#firstAppealTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	 
	 var secondAppealTable = $('#secondAppealTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	    closedTable.rows().remove().draw();
	    pendingTable.rows().remove().draw();
	    holdTable.rows().remove().draw();
	    expiredTable.rows().remove().draw();
	    //loiPaymentTable.rows().remove().draw();
	    draftModeTable.rows().remove().draw();
	    firstAppealTable.rows().remove().draw();
	    secondAppealTable.rows().remove().draw();
		
	    
	 	$.each(appList,function(i){
			var app = appList[i];
		 
			if(app.status=='CLOSED' ){
				closedCount++;
				closedList.push([app.appId,app.appDate,app.deptName,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
					 "<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"dashboardView('"+app.appId+"','"+app.smShortdesc+"','"+app.status+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		             "<a class='btn btn-danger  btn-sm' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> " + 
					  "</div>"]);
			}
			 if(app.status=='PENDING' && app.lastDecision=='HOLD'){
				holdCount++;
				 holdList.push([app.appId,app.appDate,app.deptName,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
					"<a class='btn btn-warning  btn-sm ' title='ReSubmit Application'  onclick=\"callServiceHold('"+app.appId+"','"+ app.serviceEventUrl +"')\"><i class='fa fa-paper-plane-o''></i></a> "+
			        "<a class='btn btn-danger  btn-sm ' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> "+ 
					  "</div>"]);
					  
			} 
		if(app.status=='PENDING' && app.lastDecision!='HOLD'){
				
				pendingCount++;
				pendingList.push([app.appId,app.appDate,app.deptName,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
					 "<a class='btn btn-blue-2 btn-sm '  title='View Application' onclick=\"dashboardView('"+app.appId+"','"+app.smShortdesc+"','"+app.status+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		             "<a class='btn btn-danger  btn-sm ' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> "  + 
					  "</div>"]);
			} 
			if(app.status== "EXPIRED"){
				expireCount++;
				expireList.push([app.appId,app.appDate,app.deptName,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
					 "<a class='btn btn-blue-2 btn-sm '  title='View Application' onclick=\"dashboardView('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		             "<a class='btn btn-danger  btn-sm ' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> "  + 
					  "</div>"]);
			}
			
		/* 	if(app.serviceEventName== $("#loiPayment").val()){
				loiPendingCount++;
				loiPaymentList.push([app.appId,app.appDate,app.deptName,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
					"<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"dashboardView('"+app.appId+"','"+app.smShortdesc+"','"+app.status+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		             "<a class='btn btn-danger  btn-sm' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> " + 
					  "</div>"]);
			} */
	
			if(app.status=="DRAFT" ){
				draftCount++;
				draftList.push([app.appId,app.appDate,app.deptName,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
					"<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"dashboardView('"+app.appId+"','"+app.smShortdesc+"','"+app.status+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		             "<a class='btn btn-danger  btn-sm' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> " + 
					  "</div>"]);
			}
			
		});
	 	
	 	closedTable.rows.add(closedList);
	 	closedTable.draw();
		pendingTable.rows.add(pendingList);
	 	pendingTable.draw();
	 	holdTable.rows.add(holdList);
	 	holdTable.draw();
	 	expiredTable.rows.add(expireList);
	 	expiredTable.draw();
	 	//loiPaymentTable.rows.add(loiPaymentList);
	 	//loiPaymentTable.draw();
	 	draftModeTable.rows.add(draftList);
	 	draftModeTable.draw();
	 	
	 	//Defect #117792	 	
	 	var payPendingList =__doAjaxRequest('CitizenDashBoard.html?getPayFailureApp', 'post','',false, 'json');
	 	
	 	 var payPendingTable = $('#payPendingTable').DataTable({
				"language": { "search": "" }, 
				 "iDisplayLength" : 5, 
				"pagingType": "full_numbers",
				"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
				});

	 	$.each(payPendingList,function(i){
			var app = payPendingList[i];
				payPendCount++;
				payPendList.push([app.refId,app.appDate,app.deptName,app.deptNameMar,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
		             "<a class='btn btn-blue-2  btn-sm' title='Payment'  onclick=\"onlineFailureRepayment('"+app.onlTransactionId+"')\"><i class='fa fa-credit-card' aria-hidden='true'></i></a> " + 
					  "</div>"]);
			
	 	});
	 	payPendingTable.rows.add(payPendList);
	 	payPendingTable.draw();
	 	
	 	
	 	 var loiPaymentList = __doAjaxRequest('CitizenDashBoard.html?getLoiInformation', 'post','',false, 'json');
	 	 var loiPaymentTable = $('#loiPaymentTable').DataTable({
				"language": { "search": "" }, 
				 "iDisplayLength" : 5,
				"pagingType": "full_numbers",
				"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
				});
	 	 var loiPaymntList = [];
	 	$.each(loiPaymentList,function(i){
	 		var app = loiPaymentList[i];
			loiPendingCount++;
			loiPaymntList.push([app.loiApplicationId,app.serviceName,app.serviceNameMar,app.loiNo,app.loiRefId,app.loiAmount,app.loiPaid ,"<div class='text-center'>"+
				"<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"callLoi('"+app.loiApplicationId+"','"+app.loiServiceId+"','"+app.applicationDate+"','"+app.loiNo+"','"+app.loiAmount+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
	             "<a class='btn btn-blue-2  btn-sm' title='Payment'  onclick=\"doLoiPayment('"+app.loiApplicationId+"','"+app.taskId+"')\"><i class='fa fa-credit-card' aria-hidden='true'></i></a> " + 
				  "</div>"] );
	 	});
	 	
	 	loiPaymentTable.rows.add(loiPaymntList);
	 	loiPaymentTable.draw(); 
	 	
	 	//update code here based on RTS service
	 	//set first appeal list
	 	let rtsFirstAppealList =__doAjaxRequest('CitizenDashBoard.html?fetch-first-appeal-list', 'post','',false, 'json');
	 	$.each(rtsFirstAppealList,function(i){
	 		let app = rtsFirstAppealList[i];
	 		firstAppealCount++;
		 	firstAppealList.push([app.appId,app.appDate,app.deptName,app.serviceName,app.smServiceNameMar,app.status,"<div class='text-center'>"+
					 "<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"dashboardViewFirstRTS('"+app.appId+"','"+app.dpDeptcode+"','"+app.status+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		             "<a class='btn btn-danger  btn-sm' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> " + 
					  "</div>"]);
	 	});
	 	firstAppealTable.rows.add(firstAppealList);
	 	firstAppealTable.draw();
	 	
	 	
	 	//set second appeal list
	 	let rtsSecondAppealList =__doAjaxRequest('CitizenDashBoard.html?fetch-second-appeal-list', 'post','',false, 'json');
	 	 
	 	$.each(rtsSecondAppealList,function(i){
			let app = rtsSecondAppealList[i]; 
			secondAppealCount++;
			secondAppealList.push([app.appId,app.refId,app.serviceName,app.appDate,app.status,"<div class='text-center'>"+
					 "<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"dashboardViewSecondRTS('"+app.appId+"','"+app.dpDeptcode+"','"+app.status+"')\" ><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		             "<a class='btn btn-danger  btn-sm' title='View Application History'  onclick=\"dashboardViewHistory('"+app.appId+"','"+app.smShortdesc+"','"+app.orgId+"')\"><i class='fa fa-history''></i></a> " + 
					  "</div>"]);
	 	});
	 	secondAppealTable.rows.add(secondAppealList);
	 	secondAppealTable.draw();
	 	
	 	

	 	
	 		
	 	var billList =__doAjaxRequest('CitizenDashBoard.html?getBillingStatus', 'post','',false, 'json');

	 	var billingTable = $('#billingTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	 	
	    billingTable.rows().remove().draw();

	 	$.each(billList,function(i){
			var bill = billList[i];
				closedCount++;
				billingList.push([bill.proertyNo,bill.appDate,bill.deptName,"<div class='text-center'>"+
		             "<a class='btn btn-danger btn-sm' title='View Billing  History'  onclick=\"ViewBillHistory('"+bill.proertyNo+"')\"><i class='fa fa-file-text-o' aria-hidden='true'></i></a>"+ 
					  "</div>","<div class='text-center'>"+
		             "<a class='btn btn-warning  btn-sm' title='View Payment  History'  onclick=\"ViewPaymentHistory('"+bill.proertyNo+"')\"><i class='fa fa-money' aria-hidden='true'></i></a> "+"</div>", 
		             "<div class='text-center'>"+
					 "<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"dashboardPropertyView('"+bill.proertyNo+"','"+bill.deptShortName+"')\"><i class='fa fa-history' aria-hidden='true'></i></a> " +
					  "</div>"]);
	 	});

	 	billingTable.rows.add(billingList);
	 	billingTable.draw();

	 	/* var waterBillList =__doAjaxRequest('CitizenDashBoard.html?getWaterBillingStatus', 'post','',false, 'json');

	 	var waterBillingTable = $('#waterBillingTable').DataTable({
			"language": { "search": "" }, 
			 "iDisplayLength" : 5,
			 responsive: true,
			"pagingType": "full_numbers",
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
			});
	 	
	 	waterBillingTable.rows().remove().draw();

		console.log(waterBillingList.length);
		
	 	$.each(waterBillList,function(i){
			var bill = waterBillList[i];

				closedCount++;
				waterBillingList.push([bill.connectionNo,bill.applDate,bill.deptName,"<div class='text-center'>"+
		             "<a class='btn btn-danger btn-sm' title='View Billing  History'  onclick=\"ViewBillHistory('"+bill.connectionNo+"','"+bill.deptShortName+"')\"><i class='fa fa-file-text-o' aria-hidden='true'></i></a>"+ 
					  "</div>","<div class='text-center'>"+
		             "<a class='btn btn-warning  btn-sm' title='View Payment  History'  onclick=\"ViewPaymentHistory('"+bill.applDate+"','"+bill.deptShortName+"')\"><i class='fa fa-money' aria-hidden='true'></i></a> "+"</div>", 
		             "<div class='text-center'>"+
					 "<a class='btn btn-blue-2 btn-sm'  title='View Application' onclick=\"dashboardPropertyView('"+bill.connectionNo+"','"+bill.deptShortName+"')\"><i class='fa fa-history' aria-hidden='true'></i></a> " +
					  "</div>"]);
	 	});

	 	billingTable.rows.add(waterBillingList);
	 	billingTable.draw(); */

	 	
});

/* function  ViewBillHistory(proertyNo)
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

 */
 //Defect #117792 
 function callLoi(appId,serviceId,loiDate,loiNo,loiAmount){
		var url ="PendingLoiForm.html?showDetails";
		 var data={"appId":appId,
		         "serviceId":serviceId,
		         "loiDate":loiDate,
		         "loiNo": loiNo,
		         "loiAmount":loiAmount};
		var response=__doAjaxRequest(url, 'post',data,false, 'html');
		$(formDivName).html(response);
	}

function doLoiPayment(appId,taskId){
		var data={"rowId":appId,"taskId":taskId};
		var response =__doAjaxRequest('PendingLoiForm.html?edit', 'POST', data, false);
		$(formDivName).html(response);
	}
	

function dashboardView(appId,seviceShortCode,appStatus)
{
	var data = {"appId":appId,
			"serviceCode":seviceShortCode,
			"appStatus":appStatus}; 
	
	var response=__doAjaxRequest('CitizenDashBoard.html?viewDetails', 'POST',data,false);
	var actionParam = "showDetails";
	var viewFormDetails =__doAjaxRequest(response+'?'+actionParam, 'POST', data, false);

	$(formDivName).html(viewFormDetails);
}
 
 function dashboardViewFirstRTS(appId,seviceShortCode,appStatus){
	let data = {
			"appId":appId,
			"appStatus":appStatus
	};
	let viewFormDetails =__doAjaxRequest('FirstAppeal.html?showDetails', 'POST', data, false);

	$(formDivName).html(viewFormDetails);
 }
 
 function dashboardViewSecondRTS(appId,seviceShortCode,appStatus){
		let data = {
				"appId":appId,
				"appStatus":appStatus
		};
		let viewFormDetails =__doAjaxRequest('SecondAppeal.html?showDetails', 'POST', data, false);

		$(formDivName).html(viewFormDetails);
	 }

 
 function   propertyBillDownload(proertyNo)
 {
 	
 	var data = {"proertyNo":proertyNo}; 
 	
 	var response=__doAjaxRequest('ViewPropertyDetail.html?propertyBillDownload', 'POST',data,false);

 	
 	$(formDivName).html(response);
 	 
 }
 
 

/* function dashboardPropertyView(proertyNo,deptShortCode)
{
	
	var data = {"deptShortCode":deptShortCode}; 
	
	var response=__doAjaxRequest('CitizenDashBoard.html?getUrlForBillPayStatement', 'POST',data,false);
	var actionParam = "viewDetails";
	
	var data={"proertyNo":proertyNo};
	
	var viewFormDetails =__doAjaxRequest(response+'?'+actionParam, 'POST', data, false);

		//__doAjaxRequest('ViewPropertyDetail.html?viewDetails', 'POST',data,false);

	$(formDivName).html(viewFormDetails);
}
 */








$(function () {
	var color=[];
	   var close=0;
	   var pending=0;
	   var expired=0;
	   var payPending=0;
	   var hold=0;
 
	  
	   var browserdata=[];
	   if(pendingCount!=0)
		   {
		   browserdata.push({
	           name: 'Pending',
	           y: pendingCount,
	        }); 
		   color.push('#F2910A');
		   }
	   if(closedCount!=0)
	   {
	   browserdata.push({
        name: 'Closed',
        y: closedCount,
     }); 
	   color.push('#02a22d');
	   }
	   
	   if(holdCount!=0)
	   {
	   browserdata.push({
           name: 'Hold',
           y: holdCount,
        }); 
	   color.push('#af7704');
	   }
	   
   if(expireCount!=0)
   {
   browserdata.push({
       name: 'Expired',
       y: expireCount,
    }); 
   color.push('#ca000e');
   }
   if(payPendCount!=0)
   {
   browserdata.push({
    name: 'Pay Pending',
    y: payPendCount,
 	}); 
   color.push('#2966b3');
   }
   if(firstAppealCount!=0)
   {
   browserdata.push({
       name: 'First Appeal',
       y: firstAppealCount,
    }); 
   color.push('#236FE5');
   }
   if(secondAppealCount!=0){
	   browserdata.push({
	       name: 'Second Appeal',
	       y: secondAppealCount,
	    }); 
   color.push('#44E3E8');
   }
	   
	   
   Highcharts.setOptions({
	   colors: color
	   	});   
    $('#container1').highcharts({
        chart: {
            type: 'pie',
            options3d: {
				enabled: true,
                alpha: 45,
                beta: 0,
            }
        },
        title: {
            enabled: true,
            text: 'Service Applications',
            verticalAlign: 'top',
            style: {
                color: '#000',
                font: 'bold 2.8em  oswold'
                
             }
        },
        plotOptions: {
            pie: {
                depth: 25,
                allowPointSelect: true,                   
                cursor: 'pointer', 
                showInLegend: true,  
                dataLabels: {                             
                    enabled: true,                        
                    formatter: function() {
                	    var sliceIndex = this.point.index;
                	    var sliceName = this.series.chart.axes[0].categories[sliceIndex];
                	    return '<b>' +sliceName +
                	      ' </b> <b> (' + this.y + ') </b>';
                	  }              
                }  
            }
        },
        tooltip: {
        	  formatter: function() {
        	    var sliceIndex = this.point.index;
        	    var sliceName = this.series.chart.axes[0].categories[sliceIndex];
        	    return sliceName +'</b> <b> : ' + this.y + '</b>';
        	  }
        	},
        legend: {
        	  enabled: true,
        	  floating: true,
              verticalAlign: 'xbottom',
              align:'right',
              layout: 'vertical',
              y: $(this).find('#container').height()/4,
        	  labelFormatter: function() {
        	    var legendIndex = this.index;
        	    var legendName = this.series.chart.axes[0].categories[legendIndex];
        	    return legendName;
        	  }
        	},
        	 xAxis: {
        	      categories: ["Pending Applications", "Closed Applications", "Hold Applications","Expired Application", "Payment Pending Applications","First Appeal","Second Appeal"],
        	    },	  
        	    series: [{
        	        name: '#',
        	        data:browserdata
        	      }]
        	  
    });
});




/* window.onload = function () {
	//Better to construct options first and then pass it as a parameter
	var options = {
		title: {
			text: "Applications"              
		},
		theme: "light1", // "light2", "dark1", "dark2"
		//exportEnabled: true,
		animationEnabled: true, 
		showInLegend: true, 
		//toolTipContent: "{label} <br/> {y} ", 
		//legendText: "{label}",
		//indexLabel: "{label}({y})", // change to true		
		data: [              
		{
			// Change type to "doughnut", "line", "splineArea", etc.
			type: "column",
			dataPoints: [
				{ label: "Pending",  y: pendingCount  },
				{ label: "Closed", y: closedCount  },
				{ label: "Hold", y: holdCount  },
				{ label: "Expired",  y: expireCount },
				{ label: "Payment Pending",  y: payPendCount }
			]
		}
		]
	};
	$("#chartContainer").CanvasJSChart(options);
	} */

</script>

<div id="dashBoardDiv" class="cit-dash">
	<c:set var="now" value="<%=new java.util.Date()%>" />

	<ul class="breadcrumb">
		<li><i class="fa fa-home"></i></li>
		<li><spring:message code="citizen.link.name"></spring:message></li>
		<li class="active"><spring:message
				code="citizen.dashboard.heading"></spring:message></li>
	</ul>

	<div class="content">
		<c:set var="now" value="<%=new java.util.Date()%>" />
		<c:set var="applications" value="hi" />
		<input type="hidden" value='' id="citizenApp"> <input
			type="hidden" value='${EIPMenuManager.userType}' id="userType">
		<spring:message code="workflow.event.loiPayment" var="loiPayment" />
		<input type="hidden" id="loiPayment" value="${loiPayment}" />

		<div class="animated">
			<div class="sidebar-menu-btn">
				<a class="btn menu-btn menuBtn"><spring:message code="CitizenServices" text="CitizenServices"></spring:message></a>
				<a class="btn menu-btn-close menuBtnClose"><spring:message code="bt.close" text="Close"></spring:message></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 citDashContent">
				<!-- Nav tabs -->
				<div class="card-box">
					<ul class="nav nav-tabs parent-tab " role="tablist">
						<li role="presentation" class="active col-sm-4 text-center"><a
							href="#home" aria-controls="home" role="tab" data-toggle="tab">
								<spring:message code="" text="Status of Service Applications" />
						</a></li>
						<li role="presentation" class="col-sm-4 text-center"><a
							href="#payment" aria-controls="home" role="tab" data-toggle="tab">
								<spring:message code="" text="Status of Payments" />
						</a></li>
						<li role="presentation" class="col-sm-4 text-center"><a
							href="#biil-status" aria-controls="profile" role="tab"
							data-toggle="tab"> <spring:message
									code="citizen.application.payment.billing.status"
									text="Billing and Payment Statement" />
						</a></li>

					</ul>

					<!-- Tab panes -->






					<div class="tab-content" id="dash-nav">
						<div role="tabpanel" class="tab-pane active" id="home">

							<ul id="demo1" class="nav nav-tabs ">
								<li class="active "><a href="#Inprocess" class="pending"
									data-toggle="tab"><spring:message
											code="citizen.application.applicationComp_Reject" />
								</a></li>
								<li><a href="#closed" data-toggle="tab"><spring:message
											code="citizen.application.applicationcomlete"
											text="Applications Closed" /></a></li>
								<li><a href="#Pending" data-toggle="tab"><spring:message
											code="citizen.application.applicationhold"
											text="Application Hold" /></a></li>
								<li><a href="#Expired" data-toggle="tab"><spring:message
											code="citizen.application.applicationexpired"
											text="Application Expired" /></a></li>
								<li><a href="#draft" data-toggle="tab"><spring:message
											code="citizen.application.applicationdraft"
											text="Application Draft" /></a></li>
								<li><a href="#firstAppeal" data-toggle="tab"><spring:message
											code=""
											text="First Appeal" /></a></li>
								<li><a href="#secondAppeal" data-toggle="tab"><spring:message
											code=""
											text="Second Appeal" /></a></li>
							</ul>
							<div class="tab-content tab-boxed">


								<div class="tab-pane fade active in" id="Inprocess">

									<table class="table-responsive table table-striped table-bordered dataTable"
										id="pendingTable">
										<thead>
											<tr>
												<td width="12%" align="center"><spring:message
														code="citizen.dashboard.applNo" text="Application No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service" text="Service Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service.reg"
														text="Service Name Reg" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.status" text="Status" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>

								</div>
								<div class="tab-pane fade" id="closed">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="closedTable">
										<thead>
											<tr>
												<td width="12%" align="center"><spring:message
														code="citizen.dashboard.applNo" text="Application No" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.service" text="Service Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service.reg"
														text="Service Name Reg" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.status" text="Status" /></td>
												<td style="width: 10%;" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>

								<div class="tab-pane fade" id="Pending">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="holdTable">
										<thead>
											<tr>
												<td style="width: 12%;" align="center"><spring:message
														code="citizen.dashboard.applNo" text="Application No" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.service" text="Service Name" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.service.reg"
														text="Service Name Reg" /></td>
												<td style="width: 150px;" align="center"><spring:message
														code="citizen.dashboard.status" text="Status" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>


								<div class="tab-pane fade" id="Expired">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="expiredTable">
										<thead>
											<tr>
												<td width="12%" align="center"><spring:message
														code="citizen.dashboard.applNo" text="Application No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service" text="Service Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service.reg"
														text="Service Name Reg" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.status" text="Status" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
								
								<div class="tab-pane fade" id="draft">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="draftModeTable">
										<thead>
											<tr>
												<td width="12%" align="center"><spring:message
														code="citizen.dashboard.applNo" text="Application No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service" text="Service Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service.reg"
														text="Service Name Reg" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.status" text="Status" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
								<!-- First Appeal DASHBOARD -->
									<div class="tab-pane fade" id="firstAppeal">
										<table class="table-responsive table table-striped table-bordered dataTable"
											id="firstAppealTable">
											<thead>
												<tr>
													<td width="12%" align="center"><spring:message
															code="citizen.dashboard.applNo" text="Application No" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="citizen.dashboard.appldate" text="Application Date" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="citizen.dashboard.dept" text="Department Name" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="citizen.dashboard.service" text="Service Name" /></td>
													<td width="15%" align="center"><spring:message
															code="citizen.dashboard.service.reg"
															text="Service Name Reg" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="citizen.dashboard.status" text="Status" /></td>
													<td style="width: 10%;" align="center"><spring:message
															code="citizen.dashboard.action" text="Action" /></td>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								<!-- Second Appeal DASHBOARD -->
								<div class="tab-pane fade" id="secondAppeal">
										<table class="table-responsive table table-striped table-bordered dataTable"
											id="secondAppealTable">
											<thead>
												<tr>
													<td width="12%" align="center"><spring:message
															code="citizen.dashboard.applNo" text="Application No" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="" text="Appeal No" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="citizen.dashboard.service" text="Service Name" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="" text="Date of Approval" /></td>
													<td style="width: 150px;" align="center"><spring:message
															code="citizen.dashboard.status" text="Status" /></td>
													<td style="width: 10%;" align="center"><spring:message
															code="citizen.dashboard.action" text="Action" /></td>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
								</div>

							</div>
							<!-- <div id="container1" style="height: 400px;"></div> -->
							<div id="container1"></div>
							<div id="dialog" title="">
								<p>${successMessage}</p>
							</div>
						</div>

						<div role="tabpanel" class="tab-pane" id="payment">

							<ul id="demo2" class=" nav nav-tabs ">
								<li class="active "><a href="#LoiPayment" data-toggle="tab"><spring:message
											code="citizen.application.loi.to.be.paid" text="LOI To Be Paid" /></a></li>
								<li><a href="#OnlineTransfail" data-toggle="tab"><spring:message
											code="citizen.application.applicationpayment"
											text="Application Payment Pending" /></a></li>

							</ul>
							<div class="tab-content tab-boxed">

								<div class="tab-pane fade active in" id="LoiPayment">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="loiPaymentTable">
										<thead>
											<tr>
												<td width="12%" align="center"><spring:message
														code="citizen.dashboard.applNo" text="Application No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service" text="Service Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service.reg" text="Service Name Reg" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.loiNo" text="LOI No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.loiRefNo" text="LOI Ref NO" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.loiAmt" text="LOI Amount" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.status" text="Status" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>

								<div class="tab-pane fade" id="OnlineTransfail">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="payPendingTable">
										<thead>
											<tr>
												<td width="12%" align="center"><spring:message
														code="citizen.dashboard.applNo" text="Application No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.dept.reg"
														text="Department Name Reg" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service" text="Service Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.service.reg"
														text="Service Name Reg" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.status" text="Status" /></td>
												<td width="10%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>

						<div role="tabpanel" class="tab-pane" id="biil-status">

							<ul id="demo3" class=" nav nav-tabs ">
								<li class="active "><a href="#Property" data-toggle="tab"><spring:message
											code="" text="Property" /> </a></li>
								<li><a href="#Water" data-toggle="tab"><spring:message
											code="" text="Water" /></a></li>
							</ul>
							<div class="tab-content tab-boxed">
								<div role="tabpanel" class="tab-pane active in" id="Property">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="billingTable">
										<thead>
											<tr>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.ReferenceNo" text="Reference No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.Billing" text="Billing History" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.PayHistory" text="Payment History" /></td>

												<td width="7%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>

											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>

								<div role="tabpanel" class="tab-pane" id="Water">
									<table class="table-responsive table table-striped table-bordered dataTable"
										id="waterBillingTable">
										<thead>
											<tr>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.ReferenceNo" text="Reference No" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.appldate" text="Application Date" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.dept" text="Department Name" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.Billing" text="Billing History" /></td>
												<td width="15%" align="center"><spring:message
														code="citizen.dashboard.PayHistory" text="Payment History" /></td>

												<td width="7%" align="center"><spring:message
														code="citizen.dashboard.action" text="Action" /></td>

											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%-- <div class="clearfix"></div> --%>
			
			<!-- Start Added by ABM2144 -->
			<%-- <c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }" var="user">		
			<div class="container">			
			<div class="row">
			<div class="col-lg-4 col-lg-offset-8 col-md-5 col-md-offset-7 col-sm-6 col-sm-offset-6 col-xs-11 col-xs-offset-1" id="jumpTask">						
			<select id="selectedTask" data-content="" class="form-control chosen-select-no-results">
				<option value="0">Search Task</option>
				<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
					<c:forEach items="${menuRoleEntitlement.childList}" var="data">
					<c:if test="${masters.entitle.smfid eq  data.parentId}">
					<c:set var="action0" value="${data.entitle.smfaction}" />
					<optgroup label="${masters.entitle.smfname}">
			    	<option value="${data.entitle.smfid}">${data.entitle.smfname}</option>
			    	<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
			    	<c:if test="${data.entitle.smfid eq  data1.parentId}">
			    	<c:set var="action1" value="${data1.entitle.smfaction}" />
			    	<option value="${data1.entitle.smfid}">${data1.entitle.smfname}</option>
			    	<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
					<c:if test="${data1.entitle.smfid eq  data2.parentId}">																	
					<c:set var="action2" value="${data2.entitle.smfaction}" />
					<option value="${data2.entitle.smfid}">${data2.entitle.smfname}</option>
					<c:forEach items="${menuRoleEntitlement.childList}"	var="data3">
					<c:if test="${data2.entitle.smfid eq  data3.parentId}">	
					<c:set var="action3" value="${data3.entitle.smfaction}" />
					<option value="${data3.entitle.smfid}">${data3.entitle.smfname}</option>
					</c:if></c:forEach>
					</c:if></c:forEach>
			    	</c:if></c:forEach>
			    	</optgroup> 
			    	</c:if></c:forEach>
				</c:forEach>
			</select>
			</div></div></div>				
    		</c:if> --%>
    		<!-- End Added by ABM2144-->
    		
			<h2 id="dashmenu"><span class="hidden"><spring:message code="CitizenServices" text="CitizenServices"></spring:message></span></h2> 
			<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 padding-right-0 sidebar-main">
				<div id="sidebar-menu">
					<ul id="nav" class="has_sub">
						<li id="0" style="display:none"><a>Search Task</a></li>
						<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
						<li class="has_sub" id="${masters.entitle.smfid}">
						<a role="link" title="${masters.entitle.smfname}" id="go${masters.entitle.smfid}" name="go${masters.entitle.smfid}"  href="${masters.entitle.smfaction}" onclick="dodajAktywne(this);breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});"><span>${masters.entitle.smfname}</span> </a>
							
							<ul class="scroll-menu">
								<c:forEach items="${menuRoleEntitlement.childList}" var="data">
								   <c:if test="${masters.entitle.smfid eq  data.parentId}">
										 <c:set var="action0" value="${data.entitle.smfaction}" />
										
										<li class="navigation" id="${data.entitle.smfid}">
										<a role="link" title="${data.entitle.smfname}"  <c:choose> <c:when test="${fn:containsIgnoreCase(action0 , '.html')}">onclick="openRelatedForm('${action0}','this','${data.entitle.smfdescription}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise> href="${action0}" </c:otherwise></c:choose> > ${data.entitle.smfname} </a> 
											<ul class="scroll-menu1">
												<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
													
													<c:if test="${data.entitle.smfid eq  data1.parentId}">
														
														
														
												<c:set var="action1" value="${data1.entitle.smfaction}" />
												
												
												
												<li class="folder " id="${data1.entitle.smfid}"><a title="${data1.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
															<ul>
																<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																	<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																	
																		<c:set var="action2" value="${data2.entitle.smfaction}" />
																		<li class="folder" id="${data2.entitle.smfid}"><a role="link" title="${data2.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																			<ul>
																				<c:forEach items="${menuRoleEntitlement.childList}"	var="data3">
																					<c:if test="${data2.entitle.smfid eq  data3.parentId}">	
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																						<li class="folder" id="${data3.entitle.smfid}"><a role="link" title="${data3.entitle.smfname}" <c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
																					</c:if>
																				</c:forEach>
																			</ul></li>
																	</c:if>
																</c:forEach>
															</ul></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:if>
								</c:forEach>
							</ul>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>		
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!-- Down Arrow Start -->
	<!-- <div class="mbr-arrow hidden-sm-down" aria-hidden="true">
		<a> <i class="fa fa-arrow-down" aria-hidden="true"></i></a>
	</div> -->
<!-- Down Arrow End -->
<script>
$(window).on('load', function(){
	$("#selectedTask").chosen();
	$("#selectedTask_chosen").removeAttr("style");
	$("#selectedTask").on("change",function(){
		var search = $("#selectedTask option:selected").val().trim();
		$(".scroll-menu .navigation").find(".flashi").removeClass("flashi");
		$($("li[id="+search+"]").parentsUntil("ul#nav")).each(function() {if($(this).is("ul")){$(this).css("display","block")}});
		$('html, body').animate({
			scrollTop: $("li[id="+search+"] > a").addClass("flashi").offset().top-50}, 'slow');
	}) 
});
</script>