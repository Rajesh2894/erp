<div id="onlineServices" title="D2K Application" style="display: none"
	class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript">
       
    /* used for show applications on dash board */
    $(document).ready(function(){
    	var table = $('#datatables').DataTable({
    		"oLanguage": { "sSearch": "" } ,
    		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
    	    "iDisplayLength" : 5, 
    	    "bInfo" : true,
    	    "lengthChange": true,
    	    "bPaginate": true,
    	    "bFilter": true
    	    });
    	
    	   $('#datatables tbody')
           .on( 'mouseenter', 'td', function () {
               var colIdx = table.cell(this).index().column;
               $( table.cells().nodes() ).removeClass( 'highlight' );
               $( table.column( colIdx ).nodes() ).addClass( 'highlight' );
           } );
    }); 
	
    /* used for take action against application on dash board */
	function callService(appNo,serviceId,url,taskId){
		var data = {}; 
		data.appNo = appNo;
		data.taskId = serviceId;
		data.actualTaskId = taskId;
	
		var response =__doAjaxRequest(url+'?showDetails', 'post', data, false, 'html');
		$('#taskDiv').html(response);
	}
	
	 /* used for show application history */
	function dashboardViewHistory(appId){
		var divName	=	'#dashBoardDiv';
	    var requestData = 'appId='+appId;
		var response =__doAjaxRequest('DeptDashBoard.html?viewFormHistoryDetails', 'post', requestData, false, 'html');
		$('#taskDiv').html(response);

}

</script>

<div id="taskDiv">
	<ol class="breadcrumb">
		<li><spring:message code="menu.home" /></li>
		<li><spring:message code="admin.link.name"></spring:message></li>
		<li class="active"><spring:message
				code="citizen.dashboard.heading"></spring:message></li>
	</ol>
	<div class="content">
		<div class="animated slideInDown">
			<div class="widget">
				<div class="widget-header">
					<h2 id="statusHeading" style="font-weight:600">
						<spring:message code="" text="${level} : Tasks"></spring:message>
					</h2>
					<div class="additional-btn">
						<a href="#" data-toggle="tooltip" data-original-title="Help"><i
							class="fa fa-question-circle fa-lg"></i></a>
					</div>
				</div>
				<div class="widget-content padding">
					<form:form>
						

						<div class="clear table-responsive">
							<table
								class="table table-striped table-bordered"
								id="datatables">
								<thead>
		<tr>
		<th width="10%" align="center"><spring:message code="dashboard.refno" text="Reference No."/></th>
		<th width="10%" align="center"><spring:message code="dashboard.appdate" text="Reference Date"/></th>
		<th width="15%" align="center"><spring:message code="dashboard.dept" text="Department"/></th>
		<th width="15%" align="center"><spring:message code="dashboard.desc" text="Description"/></th>
		<th width="14%" align="center"><spring:message code="dashboard.taskname" text="Task Name"/></th>
		<th width="10%" align="center"><spring:message code="dashboard.status" text="Status"/></th>
		<th width="11%" align="center"><spring:message code="dashboard.action" text="Action"/></th>
		</tr>
		</thead>
								<tbody>
									<c:forEach items="${applicationList}" var="appl">
										<tr>
											<td>${appl.applicationNo}</td>
											<td>${appl.requestDate}</td>
											<td>${appl.deDeptDesc}</td>
											<td>${appl.serviceName}</td>
											<td>${appl.eventName}</td>
											<td>${appl.taskStatus}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm margin-right-10"
													onClick="callService(${appl.applicationNo},${appl.serviceId},${appl.serviceURL},${appl.taskId})"
													title="Application">
													<i class="fa fa-paper-plane-o"></i>
												</button>
												<button type="button" class="btn btn-danger btn-sm"
													onClick="dashboardViewHistory(${appl.applicationNo})" title="View History">
													<i class="fa fa-history"></i>
												</button>

											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>



