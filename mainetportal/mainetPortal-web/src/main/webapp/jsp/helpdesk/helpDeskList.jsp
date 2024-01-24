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

</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">

<div class="widget">

	<div class="widget-content padding">
		<form action="HelpDesk.html" method="get" class="form-horizontal" id="frmHelpDesk" name="frmHelpDesk">
			<div class="text-center margin-bottom-20 ">
				<a href="javascript:void(0);"  onclick="openForm('HelpDesk.html','create')" class="btn btn-blue-2"><i
					class="fa fa-plus-circle"></i> &nbsp;<spring:message code="help.create" text="Create"/></a>	
			</div>
		
			<table class="table text-left table-striped table-bordered dataTableClass">

				<thead>
					<tr>
						<th class="text-center"><spring:message code="help.type" text="Activity Type"/></th>
						<th class="text-center"><spring:message code="help.desc" text="Activity Description"/></th>
						<th class="text-center"><spring:message code="help.assignee" text="Assignee"/></th>
						<th class="text-center"><spring:message code="help.due.date" text="Due Date"/></th>
						<th class="text-center"><spring:message code="help.priority" text="Priority"/></th>
						<th class="text-center"><spring:message code="help.status" text="STATUS"/></th>
						<th width="12%"><spring:message code="master.grid.column.action" text="Action"/></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${allCallLog}" var="activity">
					<tr>
						<td><spring:eval expression="T(com.abm.mainet.common.util.CommonMasterUtility).getCPDDescription(activity.helpType,'')"/></td>
						<td><c:out value="${activity.helpName}"/></td>
						<td>
							<c:forEach items="${employees}" var="employe">
								<c:if test="${employe.empId eq  activity.helpEmpid}">
									<c:out value="${employe.fullName}"/>
								</c:if>
							</c:forEach>
						</td>
						
						<td><fmt:formatDate type="date" value="${activity.helpEnddt}" pattern="dd/MM/yyyy" var="dueDate" /><c:out value="${dueDate}"/></td>
							<td><spring:eval expression="T(com.abm.mainet.common.util.CommonMasterUtility).getCPDDescription(activity.helpPriority,'E')"/></td>
							<td><spring:eval expression="T(com.abm.mainet.common.util.CommonMasterUtility).getCPDDescription(activity.helpStatus,'E')"/></td>
							<td><a href="#" onclick="viewOrEdit(this,${activity.helpId},'view')" class="btn btn-warning btn-sm"	title="View"><i class="fa fa-eye" aria-hidden="true"></i></a>
							<spring:eval expression="T(com.abm.mainet.common.util.CommonMasterUtility).getCPDDescription(activity.helpStatus,'V')" var="status"/>
							
							<c:if test="${helpDeskUser eq true }">
							
								<a href="#" onclick="viewOrEdit(this,${activity.helpId},'edit')" class="btn btn-blue-2 btn-sm" title="Edit"><i class="fa fa-pencil" aria-hidden="true"></i></a>
							</c:if>
							<c:if test="${helpDeskUser eq false && status eq 'O' }">
								<a href="#" onclick="viewOrEdit(this,${activity.helpId},'edit')" class="btn btn-blue-2 btn-sm" title="Edit"><i class="fa fa-pencil" aria-hidden="true"></i></a>
							</c:if>
								<%-- <a href="#" onclick="deleteActivity(${activity.helpId})" class="btn btn-danger btn-sm" title="Delete"><i class="fa fa-trash" aria-hidden="true"></i></a>  --%>
							</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</div>
</div>
