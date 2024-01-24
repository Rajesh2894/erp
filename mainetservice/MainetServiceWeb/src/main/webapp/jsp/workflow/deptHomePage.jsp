<div id="onlineServices" title="D2K Application" style="display: none"
	class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/workflow/deptDashBoard.js"></script>
<!-- D#128616 DataTable-->
<!-- <link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->

<script src="assets/libs/jquery-slimscroll/jquery.slimscroll.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<!-- <script src="assets/js/jquery.mark.js"></script> -->
<style>.card {
    margin-bottom: 1.5rem;
    position: relative;
    display: -ms-flexbox;
    display: flex;
    -ms-flex-direction: column;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-color: #fff;
    background-clip: border-box;
    border: 1px solid #c8ced3;
    border-radius: .25rem;
}
.card-header:first-child {
    border-radius: calc(.25rem - 1px) calc(.25rem - 1px) 0 0;
}
.card-header {
    padding: .75rem 1.25rem;
    margin: 0;
    background-color: #84d8a9;
    border-bottom: 1px solid #c8ced3;
    color:#246335;
    font-size: 1.2em;
    text-transform: uppercase;
    letter-spacing: 0.1em;
    text-align:center;
    font-weight:600;
}
.card-body {
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    padding: 1.25rem;
}
.card-title {
    margin-bottom: .75rem;
}
.card-body  ul li{
    margin-bottom: 2px;
    border-bottom: 1px dotted #9fd8ba;    color: #00a74b;    padding: 2px;
}
.card-h3{    text-align: center;
    padding: 10px;
    text-transform: uppercase;
    background:linear-gradient(#72d49d,#cee8da);
    margin: 0;
    color: #236334;
    height:45px;font-size: 1em;
    letter-spacing: 0.1em;}
 .card-h3 a{color: #236334;}   
 
 .main-heading{
        color: #008432;
    text-transform: uppercase;
    font-weight: 600;
    text-decoration: none;
    position: relative;
    margin-bottom: 20px; 
}
.main-heading:after {
        position: absolute;
        content: '';
        height: 2px;
		bottom: -4px; 
		margin: 0 auto;
		left: 16px;
        width: 40%;
		background: green;
		-o-transition:.5s;
  		-ms-transition:.5s;
        -moz-transition:.5s;
        -webkit-transition:.5s;
        transition:.5s;
    }
.main-heading:hover:after {
		  width: 60%;
		  background: orange;
    }
.card-body {
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    padding: 1rem !important;
    text-align: center;
	font-weight: 600;
    letter-spacing: 3px;
    background: #bde4cf;
    color: #056329;
}
.active .card-body{
    background: #02843e;
    color: #ffffff;}
.menu-child{
    height: 140px;
    border: 1px solid #e3e8e6;
    box-shadow: chartreuse;
    -webkit-box-shadow: 5px 3px 5px 5px rgba(202,222,204,0.76);
    -moz-box-shadow: 5px 3px 5px 5px rgba(202,222,204,0.76);
    box-shadow: 5px 3px 5px 5px rgba(202,222,204,0.76);
	margin-bottom: 10px;
}
.child-logo img{
height: 60px;
}

.child-logo{text-align: center; padding: 5px;}
.child-content p{
padding: 5px;
    text-align: center;
    color: #00a24d;
    font-weight: 600;font-size: 0.95em;
    letter-spacing: 2px;
}
.widget .nav-tabs > li.active > a, 
.widget .nav-tabs > li.active > a:hover, 
.widget .nav-tabs > li.active > a:focus,
.widget .nav-tabs > li > a {    
    padding: 10px!important;
}
.widget .nav-tabs li a:hover::before, 
.widget .nav-tabs li.active a:before,
.widget .nav-tabs li a:before{
	content: ""!important;
    margin: 0px 1px!important;
}
.nav-tabs > li > a:hover{
 	cursor: pointer;
 }
#wrapper{background: #ffffff !important;}

#taskDiv .bg-danger,
#taskDiv .bg-danger > a {
	color: #000000;
	background-color: #ffb3b3;
}
#taskDiv .bg-primary,
#taskDiv .bg-primary > a {
	color: #ffffff;
	background-color: #007AFF;
}
#taskDiv .bg-success,
#taskDiv .bg-success > a {
	color: #ffffff;
	background-color: #48b358;
}
#taskDiv .bg-deep-danger,
#taskDiv .bg-deep-danger > a {
	color: #ffffff;
	background-color: #fb1e33f2;
}
#taskDiv .bg-warning,
#taskDiv .bg-warning > a {
	background-color: #ffa667;
}
#taskDiv .text-warning,
#taskDiv .text-primary,
#taskDiv .text-success,
#taskDiv .text-deep-danger {
	font-weight: 600;
}
#taskDiv .text-warning,
#taskDiv .text-warning > a {
	color: #ffa667;
}
#taskDiv .text-primary,
#taskDiv .text-primary > a {
	color: #007AFF !important
}
#taskDiv .text-success,
#taskDiv .text-success > a {
	color: #48b358;
}
#taskDiv .text-deep-danger,
#taskDiv .text-deep-danger > a {
	color: #fb1e33f2;
}
.table-legend ul li a {
	/* cursor: default; */
}
</style>

<script> 
$(function(){
	$('.nav-tabs > li > a').hover(function() {
	  $(this).tab('show');
	});
/* $('.card-body').slimScroll({
    height: '250px',
    position: 'right',
    size: "5px",
    alwaysVisible: true,
    railOpacity: 1,
    color: '#000'
}); */
$(".footer-logos").removeClass('hidden');
});
$(window).on("load",function(){	
	var target = window.location.hash;
	if(target!=""){
	var headerHeight = $(".topbar").height();	
	$("html,body").animate({scrollTop:$(target).offset().top-(headerHeight+10)},1000);}	
})

function childcall(obj){
	var a=$(obj).attr("id");
	$("#smfid1").attr("value",a);
	$(".child-div").removeClass("hidden");
	$(".child-level").each(function(){
		var b=$(this).val();
		if(a==b){
			$(".child-level").removeClass("");
		}
	})
	alert(a);
}

</script>
<style>
footer{display:none;}
#wrapper.enlarged .content-page {
    margin-left: 0px;
}
h3 {
  margin-top: 0;
}

.badge {background-color: #777;}

.heading-size {
    font-size: 1.5em;
}
	  
</style>
<script>
function openMenu(){
	if($(".leftmenu").hasClass("hidden-sm")){
		$(".leftmenu").removeClass("hidden-sm hidden-xs");
		$(".tab-content").attr("id","showmenu");
	}else{
		$(".leftmenu").addClass("hidden-sm hidden-xs");
		$(".mobile .tab-content").css("left","0px");
		$(".tab-content").attr("id","");
	}
}

$(window).resize(function(e) {
	if ($(window).width() <= 766 && $(window).width() >= 567) {
        if($('.tab-pane.active .navigation').length==1){
        	$('.tab-pane.active .navigation').css("width","100%")
        }
    }
    else {
    	if($('.tab-pane.active .navigation').length==1){
        	$('.tab-pane.active .navigation').css("width","")
        }
    }
	
	if ($(window).width() <= 766 && $(window).width() >= 567) {
        if($('.tab-pane.active .navigation').length==1){
        	$(this).css("width","100%")
        }
    }
    else {
    	if($('.tab-pane.active .navigation').length==1){
        	$(this).css("width","")
        }
    }
	
    if ($(window).width() <= 767) {
        $('.tabs-left').addClass('mobile');
        $('.tabs-left').removeClass('desktop');
    }
    else {
        $('.tabs-left').removeClass('mobile');
        $('.tabs-left').addClass('desktop');
    }
    
    if ($(window).width() <= 991) {
    	$(".tab-content").attr("id","");
    	$(".leftmenu").addClass("hidden-sm hidden-xs");
    }
    else {
    	$(".tab-content").attr("id","showmenu"); 
    	
    }
  
});

$(document).ready(function(){
  $(window).resize(); // call once for good measure!
  
  $('.tab-buttons button:first-child').addClass('tab-selected');
  var tabs = $('.tab-buttons button');
  tabs.on('click', function(){
	 $(this).addClass('tab-selected').siblings().removeClass("tab-selected");
  });
 
});
$(document).on('click', '.mobile .nav-tabs li a, .desktop .nav-tabs li a', function() {
	$(".leftmenu").addClass("hidden-sm hidden-xs");
	$(".tab-content").css("left"," 0px");
	$(".tab-content").attr("id","");
});
</script>
<div id="taskDiv">
<div class="container tab-page">
  <div class="row">
    <div class="">
      <div class="tabs-left desktop">
      <div class="leftmenu hidden-sm hidden-xs">
        <ul class="nav nav-tabs">
          <li class="active all-tasks"><a href="#a" data-toggle="tab">&nbsp;<spring:message code="dashboard.node.myTasks" text="My Tasks" /></a></li>
          <!--D#127111  -->
           <input type="hidden" id="applicableENV"  value="${command.applicableENV}">
           <input type="hidden" path="" id="filterBTValue" value="" />
            <input type="hidden" path="" id="filterState" value="PENDING" />
          <c:if test="${command.applicableENV}">
					<li class="all-tasks"><a href="#complaints" data-toggle="tab">&nbsp;<spring:message code="dashboard.node.complaintTasks" text="Complaint Tasks" /></a></li>
		  </c:if>
          
          <c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
          <li>
          <a role="link" data-toggle="tab" title="${masters.entitle.smfname}" id="go${masters.entitle.smfid}" name="go${masters.entitle.smfid}"  href="#${masters.entitle.smfid}" onclick="dodajAktywne(this);breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});"><!-- <i class="fa fa-circle-thin" aria-hidden="true"></i>&nbsp;&nbsp; --><span>${masters.entitle.smfname}</span></a>
          </li>
          </c:forEach>         
        </ul>        
        </div> 
        
        <div class="tab-content">        	
          <div class="tab-pane active" id="a">
          					<div class="text-center clear bold pending heading-size"><spring:message code="dashboard.pendingItems" text="Pending Items" /></div>
							<div class="text-center clear bold completed heading-size"><spring:message code="dashboard.completedItems" text="Completed Items" /></div>
							<div class="text-left clear padding-10 tab-buttons">
								<button type="submit" class="btn btn-warning"
									onclick="removeFilterValue();getTaskListWithStatus('<spring:message code="dashboard.pendingStatus" text="PENDING" />')" name="button-submit"
									style="" id="button-submit">
									<spring:message code="dashboard.pending" text="Pending" /> <span id="pendingITCount"> </span>
								</button>

								<button type="submit" class="button-input btn btn-success"
									onclick="removeFilterValue();getTaskListWithStatus('<spring:message code="dashboard.completedStatus" text="COMPLETED" />')"
									name="button-submit" style="" id="button-submit">
									<spring:message code="dashboard.completed" text="Completed" /> <span id="completedITCount"> </span>
								</button>
							</div>
							
							<c:if test="${userSession.countersts eq 'I' }">
								<div class="text-center">
									<p class="text-red text-bold text-extra-large">
										<spring:message code="" text="Your Scheduling time has been expired"/>
									</p>
								</div>
							</c:if>
							
							<!--D#127178  -->
						<div class="col-sm-4">
							<div class="table-legend">
								<ul>
									<li class="bg-warning">
										<a href="#" onclick="hitFilterValue('0-3');getTaskListWithStatus('PENDING')"  title="<spring:message code="dashboard.color.code.1" text="0 to 3 Days spring"/>">
											<spring:message code="dashboard.color.code.1" text="0 to 3 Days "/>
										</a>
									</li>
									<li class="bg-primary">
										<a href="#" onclick="hitFilterValue('4-6');getTaskListWithStatus('PENDING')" title="<spring:message code="dashboard.color.code.2" text="4 to 6 Days"/>">
											<spring:message code="dashboard.color.code.2" text="4 to 6 Days"/>
										</a>
									</li>
									<li class="bg-success">
										<a href="#" onclick="hitFilterValue('6-180');getTaskListWithStatus('PENDING')" title="<spring:message code="dashboard.color.code.3" text="6 to above"/>">
											<spring:message code="dashboard.color.code.3" text="6 to above"/>
										</a>
									</li>
									
									<c:if test="${!command.applicableENV}">
										<li class="bg-deep-danger">
											<a href="#" onclick="hitFilterValue('reopen');getTaskListWithStatus('PENDING')" title="<spring:message code="dashboard.color.code.4" text="REOPEN"/>">
												<spring:message code="dashboard.color.code.4" text="REOPEN"/>
											</a>
										</li
								    </c:if>
									
								</ul>
							</div>
						</div>
							<!-- Added Department dropdown for Suda US#141152-->
								<c:if test="${command.sudaCheck}">
									<div class="col-sm-4 dept-dropdown">
										<select
											class="form-control chosen-select-no-results required-control"
											id="deptId" onchange="deptFillter();">
											<option value="" selected="selected"><spring:message
													code='master.serviceMas.department'
													text="Select Department" /></option>
											<c:choose>
												<c:when test="${userSession.languageId eq 1}">
													<c:forEach items="${command.departmentsList}"
														var="deptData">
														<option value="${deptData.dpDeptid }"
															code="${deptData.dpDeptcode }">${deptData.dpDeptdesc }</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach items="${command.departmentsList}"
														var="deptData">
														<option value="${deptData.dpDeptid }"
															code="${deptData.dpDeptcode }">${deptData.dpNameMar }</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</c:if>
							<div class="clear table-responsive">	
								<table class="table table-striped table-bordered " id="datatables" >
									<thead>
										<tr>
											<th width="14%" align="right"><spring:message code="dashboard.refno" text="Reference No."/></th>
											<th width="11%" align="center"><spring:message code="dashboard.appdate" text="Reference Date"/></th>
											<th width="12%" align="center"><spring:message code="dashboard.dept" text="Department"/></th>
											<th width="15%" align="center"><spring:message code="dashboard.desc" text="Description/Service"/></th>
											<th align="center"><spring:message code="dashboard.taskname" text="Task"/></th>
											<%-- <th width="14%" align="center"><spring:message code="dashboard.lastDecision" text="User Decision"/></th> --%>
											<th width="8%" align="center"><spring:message code="dashboard.status" text="Task Status"/></th>
											<th width="6%" align="center"><spring:message code="dashboard.color.slaRemaining" text="No. of Day Remaining"/></th>
											<th width="10%" align="center"><spring:message code="dashboard.action" text="Action"/></th>	
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						
						<p class="text-bold text-extra-small">
							<spring:message code="dashboard.note.label" text="Please Note:"/>
						</p><p class="text-red text-bold text-extra-small">
							<spring:message code="dashboard.note.msg" text="Reference No.=Complaint No.,Application No.,any other Reference No. based on Department Task."/>
						</p>
						</div>
						
						<!--D#127111  -->
						<div class="tab-pane " id="complaints">
          					<div class="text-center clear bold pendingCOMIT heading-size"><spring:message code="dashboard.compl.pendingItems" text="Pending Items" />  </div>
							<div class="text-center clear bold completedCOMIT heading-size"><spring:message code="dashboard.compl.completeItems" text="Completed Items" /> </div>
							<div class="text-left clear padding-10 tab-buttons">
								<button type="submit" class="btn btn-warning"
									onclick="removeFilterValue();getComplaintTaskListWithStatus('<spring:message code="dashboard.pendingStatus" text="PENDING" />')" name="pendingCOMBT"
									style="" id="pendingCOMBT">
									<spring:message code="dashboard.pending" text="Pending" />  <span id="pendingCOMITCount"> </span>
								</button>

								<button type="submit" class="button-input btn btn-success"
									onclick="removeFilterValue();getComplaintTaskListWithStatus('<spring:message code="dashboard.completedStatus" text="COMPLETED" />')"
									name="completeCOMBT" style="" id="completeCOMBT">
									<spring:message code="dashboard.completed" text="Completed" />  <span id="completedCOMITCount"> </span>
								</button>
							</div>
							<!--D#127178  -->
							<div class="table-legend">
								<ul>
									<li class="bg-warning">
										<a href="#" onclick="hitFilterValue('0-3');getComplaintTaskListWithStatus('PENDING')" title="<spring:message code="dashboard.color.code.1" text="0 to 3 Days"/>">
											<spring:message code="dashboard.color.code.1" text="0 to 3 Days"/>
										</a>
									</li>
									<li class="bg-primary">
										<a href="#" onclick="hitFilterValue('4-6');getComplaintTaskListWithStatus('PENDING')" title="<spring:message code="dashboard.color.code.2" text="4 to 6 Days"/>">
											<spring:message code="dashboard.color.code.2" text="4 to 6 Days"/>
										</a>
									</li>
									<li class="bg-success">
										<a href="#" onclick="hitFilterValue('6-180');getComplaintTaskListWithStatus('PENDING')" title="<spring:message code="dashboard.color.code.3" text="6 to above"/>">
											<spring:message code="dashboard.color.code.3" text="6 to above"/>
										</a>
									</li>
									<li class="bg-deep-danger">
										<a href="#" onclick="hitFilterValue('reopen');getComplaintTaskListWithStatus('PENDING')" title="<spring:message code="dashboard.color.code.4" text="REOPEN"/>">
											<spring:message code="dashboard.color.code.4" text="REOPEN"/>
										</a>
									</li>
								</ul>
							</div>
							<div class="clear table-responsive">	
								<table class="table table-striped table-bordered " id="complaintDatatables" >
									<thead>
										<tr>
											<th width="14%" align="right"><spring:message code="dashboard.refno" text="Reference No."/></th>
											<th width="11%" align="center"><spring:message code="dashboard.appdate" text="Reference Date"/></th>
											<th width="12%" align="center"><spring:message code="dashboard.dept" text="Department"/></th>
											<th width="15%" align="center"><spring:message code="dashboard.desc" text="Description/Service"/></th>
											<th align="center"><spring:message code="dashboard.taskname" text="Task"/></th>
											<%-- <th width="14%" align="center"><spring:message code="dashboard.lastDecision" text="User Decision"/></th> --%>
											<th width="8%" align="center"><spring:message code="dashboard.status" text="Task Status"/></th>
											<th width="8%" align="center"><spring:message code="dashboard.color.slaRemaining" text="No. of Day Remaining"/></th>
											<th width="8%" align="center"><spring:message code="dashboard.action" text="Action"/></th>	
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						
						<p class="text-bold text-extra-small">
							<spring:message code="dashboard.note.label" text="Please Note:"/>
						</p><p class="text-red text-bold text-extra-small">
							<spring:message code="dashboard.note.msg" text="Reference No.=Complaint No."/>
						</p>
						</div>
           
          <c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
						
						<div class="tab-pane" id="${masters.entitle.smfid}">	
						<div id="sidebar-menu" class="has_sub active">
						
						<h1>${masters.entitle.smfname}</h1> 
							<ul class="scroll-menu"  id="nav">
								<c:forEach items="${menuRoleEntitlement.childList}" var="data">
								   <c:if test="${masters.entitle.smfid eq  data.parentId}">
										 <c:set var="action0" value="${data.entitle.smfaction}" />
								
										<li class="navigation" id="${data.entitle.smfid}">
											
										<a role="link" title="${data.entitle.smfname}"  <c:choose> <c:when test="${fn:containsIgnoreCase(action0 , '.html')}">onclick="openRelatedForm('${action0}','this','${data.entitle.smfdescription}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});" href="#" class="nolink" </c:when><c:otherwise> href="${action0}" </c:otherwise></c:choose> ><i class="fa fa-cube" aria-hidden="true"></i> ${data.entitle.smfname} </a> 
											<ul class="scroll-menu1">
												<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
													
													<c:if test="${data.entitle.smfid eq  data1.parentId}">
														
														
														
												<c:set var="action1" value="${data1.entitle.smfaction}" />
												
												
												
												<li class="folder " id="${data1.entitle.smfid}"><a title="${data1.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">onclick="openRelatedForm('${action1}','','${data.entitle.smfname}','${data1.entitle.smShortDesc}');breadcrumb('${masters.entitle.smfname}+${data1.entitle.smfname}+${data1.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
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
							</div>
							</div>
					</c:forEach>
					</div>
          
        </div><!-- /tab-content -->
      </div><!-- /tabbable -->
    </div><!-- /col -->
  </div><!-- /row -->
</div><!-- /container -->





					

	    		</div>
			 </div>
			 

</div>










<%-- <div id="taskDiv">
	<ol class="breadcrumb">
		<li><spring:message code="menu.home" /></li>
		<li><spring:message code="admin.link.name"></spring:message></li>
		<li class="active"><spring:message code="citizen.dashboard.heading"></spring:message></li>
	</ol>
	<div class="content">	
		<div class="animated slideInDown">
			<div class="widget">
				<div class="widget-header">
					<h2 id="statusHeading" style="font-weight: 600 !important;">
						<spring:message code="dashboard.my.task.list" text="My Tasks"></spring:message>
					</h2>
					<apptags:helpDoc url="AdminHome.html"></apptags:helpDoc>
				</div>
				<div class="widget-content padding">
					<form:form>
						<div class="clear table-responsive ">	
							<table class="table table-striped table-bordered " id="datatables" >
								<thead>
									<tr>
										<th width="10%" align="right"><spring:message code="dashboard.refno" text="Reference No."/></th>
										<th width="13%" align="center"><spring:message code="dashboard.appdate" text="Reference Date"/></th>
										<th width="15%" align="center"><spring:message code="dashboard.dept" text="Department"/></th>
										<th width="15%" align="center"><spring:message code="dashboard.desc" text="Description/Service"/></th>
										<th width="14%" align="center"><spring:message code="dashboard.taskname" text="Task"/></th>
										<th width="8%" align="center"><spring:message code="dashboard.status" text="Status"/></th>
										<th width="11%" align="center"><spring:message code="dashboard.action" text="Action"/></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<p class="text-bold text-small">
							<spring:message code="dashboard.note.label" text="Please Note:"/>
						</p><p class="text-red text-bold text-small">
							<spring:message code="dashboard.note.msg" text="Reference No.=Complaint No.,Application No.,any other Reference No. based on Department Task."/>
						</p>
					</form:form>				
					
					<div class="clearfix"></div>
					
					
					
					
					
					
					
					
			<!-- Start Added by ABM2144 -->
			<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }" var="user">		
			<div class="container">			
			<div class="row">
			<div class="col-lg-4 col-lg-offset-8 col-md-5 col-md-offset-7 col-sm-6 col-sm-offset-6 col-xs-11 col-xs-offset-1" id="jumpTask">						
			<select id="selectedTask" data-content="" class="form-control chosen-select-no-results">
				<option value="0">Search Task</option>
				<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
					<c:forEach items="${menuRoleEntitlement.childList}" var="data">
					<c:if test="${masters.entitle.smfid eq  data.parentId}">
					<c:set var="action0" value="${data.entitle.smfaction}" />
					<optgroup label="${masters.entitle.smfname} - ${data.entitle.smfname}">
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
    		</c:if>
    		<!-- End Added by ABM2144-->
				
		
										
												
				
			
			<div id="sidebar-menu">
				<ul id="nav" class="has_sub">
				<li id="0" style="display:none"><a>Search Task</a></li>
				<hr class="divider">
				
				<!-- 	<li><a href="javascript:void(0);"
						onclick="openCitizenGuideLines('name=guideLineForCitizen')"><i
							class="icon-info-circled-alt"></i> <span>GuideLine</span></a></li> -->
					
					<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
						<li class="has_sub" id="${masters.entitle.smfid}">
						<a role="link" title="${masters.entitle.smfname}" id="go${masters.entitle.smfid}" name="go${masters.entitle.smfid}"  href="${masters.entitle.smfaction}" onclick="dodajAktywne(this);breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});"><span>${masters.entitle.smfname}</span> </a>
							
							<ul class="scroll-menu">
								<c:forEach items="${menuRoleEntitlement.childList}" var="data">
								   <c:if test="${masters.entitle.smfid eq  data.parentId}">
										 <c:set var="action0" value="${data.entitle.smfaction}" />
										
										<li class="navigation" id="${data.entitle.smfid}">
										<a role="link" title="${data.entitle.smfname}"  <c:choose> <c:when test="${fn:containsIgnoreCase(action0 , '.html')}">onclick="openRelatedForm('${action0}','this','${data.entitle.smfdescription}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});" href="#" class="nolink"</c:when><c:otherwise> href="${action0}"</c:otherwise></c:choose> > ${data.entitle.smfname} </a> 
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

                   
				</ul><br><br><br><br><br><br><br><br><br>
				
				<div class="clearfix"></div>
			</div>
					

	    		</div>
			 </div>
			 
		
		
		
		</div>
	</div>
</div> --%>



<script>
$(function () {
	
    var toolbox = $('.scroll-menu'),
        height = toolbox.height(),
        scrollHeight = toolbox.get(0).scrollHeight;
  
    toolbox.off("mousewheel").on("mousewheel", function (event) {
      var blockScrolling = (this.scrollTop === scrollHeight - height) && (event.deltaY < 0) || (this.scrollTop === 0) && (event.deltaY > 0);      
      return !blockScrolling;
    });
  
   $("#selectedTask").chosen();
   $("#selectedTask_chosen").removeAttr("style");
   $("#selectedTask").on("change",function(){
	   var search = $("#selectedTask option:selected").val().trim();
	   $($("li[id="+search+"]").parentsUntil("ul#nav")).each(function() {if($(this).is("ul")){$(this).css("display","block")}});
 	   $('html, body').animate({scrollTop: $("li[id="+search+"] > a").addClass("flashi").offset().top-85}, 'slow');
   }) 
  });
</script>