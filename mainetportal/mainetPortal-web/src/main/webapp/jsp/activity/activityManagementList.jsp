 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script> -->
<style>.col-hide select{display:none;}
.col-hide{vertical-align: middle !important;}
</style>
<script>

function viewOrEdit(objParent, id,mode){ 
	

var theForm	=	'';

theForm	=	'#'+findClosestElementId(objParent,'form');

var url	=	$(theForm).attr('action');

url+='?edit';
var data	=	'actId='+id+'&mode='+mode;
var response =__doAjaxRequest(url,'post',data,false,'html');

if (typeof(response) === "string")
{
	$('.content-page').html(response);	
	/*prepareTags()*/
}

}







function deleteActivity(actId) {

		showConfirmBoxForDelete(actId);
	
}
function showConfirmBoxForDelete(actId) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'+ getLocalMessage('Do you want to delete?') +'</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + actId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(actId) {
	$.fancybox.close();
	var requestData = 'actId=' + actId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('ActivityManagement.html?'+ 'delete', requestData, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">

<div class="widget">

	<div class="widget-content padding">
		<form action="ActivityManagement.html" method="get" class="form-horizontal" id="frmActivityManagement" name="frmActivityManagement">
			<div class="text-center margin-bottom-20 ">
				<a href="javascript:void(0);"  onclick="openForm('ActivityManagement.html','create')" class="btn btn-blue-2"><i
					class="fa fa-plus-circle"></i> &nbsp;<spring:message code="activity.create" text="Create"/></a>
			</div>
			<table class="table text-left table-striped table-bordered dataTableClass">

				<thead>
					<tr>
						<th class="text-center"><spring:message code="activity.type" text="Activity Type"/></th>
						<th class="text-center"><spring:message code="activity.desc" text="Activity Description"/></th>
						<th class="text-center"><spring:message code="activity.assignee" text="Assignee"/></th>
						<th class="text-center"><spring:message code="activity.due.date" text="Due Date"/></th>
						<th class="text-center"><spring:message code="activity.priority" text="Priority"/></th>
						<th class="text-center"><spring:message code="activity.status" text="STATUS"/></th>
						<th width="12%" class="col-hide"><spring:message code="master.grid.column.action" text="Action"/></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${allActivity}" var="activity">
					<tr>
						<td><spring:eval expression="T(com.abm.mainet.common.util.CommonMasterUtility).getCPDDescription(activity.actType,'E')"/></td>
						<td><c:out value="${activity.actName}"/></td>
						<td>
							<c:forEach items="${employees}" var="employe">
								<c:if test="${employe.empId eq  activity.actEmpid}">
									<c:out value="${employe.fullName}"/>
								</c:if>
							</c:forEach>
						</td>
						
						<td><fmt:formatDate type="date" value="${activity.actEnddt}" pattern="dd/MM/yyyy" var="dueDate" /><c:out value="${dueDate}"/></td>
							<td><spring:eval expression="T(com.abm.mainet.common.util.CommonMasterUtility).getCPDDescription(activity.actPriority,'E')"/></td>
							<td><spring:eval expression="T(com.abm.mainet.common.util.CommonMasterUtility).getCPDDescription(activity.actStatus,'E')"/></td>
							<td><a href="#" onclick="viewOrEdit(this,${activity.actId},'view')" class="btn btn-warning btn-sm"	title="View"><i class="fa fa-eye" aria-hidden="true"></i></a>
								<a href="#" onclick="viewOrEdit(this,${activity.actId},'edit')" class="btn btn-blue-2 btn-sm" title="Edit"><i class="fa fa-pencil" aria-hidden="true"></i></a>
								<a href="#" onclick="deleteActivity(${activity.actId})" class="btn btn-danger btn-sm" title="Delete"><i class="fa fa-trash" aria-hidden="true"></i></a> 
							</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</div>
</div>
