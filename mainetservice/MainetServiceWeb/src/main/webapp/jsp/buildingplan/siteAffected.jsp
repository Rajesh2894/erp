
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script src="js/cfc/scrutiny.js"></script>
<script type="text/javascript">
$("#addSite").click(
		function() {
			var cnt = $('#tbl1 tr').length - 1;
			var srNo=cnt+1;
			var countId=cnt-1;
		  if($('#capacity'+countId).val()!='0' && $('#buffer'+countId).val()!='')
                {
			var row = '<td id="srNo">'
				     +srNo
					+ '</td>'
					+ '<td><input  type="text" class="form-control hasNumber" name="listSiteAffDto['+cnt+'].capacity" id="capacity'+cnt+'"></input></td>'
					+ '<td><input  type="text" class="form-control hasNumber" name="listSiteAffDto['+cnt+'].buffer" id="buffer'+cnt+'" onkeypress="return hasAmount(event, this, 13, 2)"></input></td>';
			$('#tbl1 tr')
					.last()
					.after(
							'<tr id="tr'+cnt+'" class="appendableClass">'
									+ row
									+ '<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm" data-original-title="Delete Road" id="deleteSite" onclick="removeRow('
									+ cnt
									+ ')"><i class="fa fa-trash"></i></a></td></tr>');
			        cnt++;
			        reorderSite();
                }
            else
			  {
			   showErrormsgboxTitle("Please Enter the All Mandatory fields");
			   }

		});
		
function reorderSite()
{
	$('.appendableClass').each(function(i) {
		
			$(this).find("input:text:eq(0)").attr("id", "capacity" + (i));
		    $(this).find("input:text:eq(1)").attr("id", "buffer" + (i));
			$(this).closest("tr").attr("id", "tr" + (i));
			$(this).closest('tr').find('#srNo').text(i+1);
		    //names
			$(this).find("input:text:eq(0)").attr("name", "listSiteAffDto[" + (i) + "].capacity");
			$(this).find("input:text:eq(1)").attr("name", "listSiteAffDto[" + (i) + "].buffer");
		    $(this).find("#deleteConnection").attr("onclick", "deleteSite(" + (i) + ")");
			});

}

$("#deleteSite").click(function() {
	if ($('#tbl1 tr').size() > 2) {

		$('#tbl1 tr:last-child').remove();
		cnt--;
	} else {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
	}
	});

function removeRow(cnt){

if($('#tbl1 tr').size()>2){
		$("#tr"+cnt).remove();
		reorderSite();
		cnt--;
		}else{
			var msg="cant not remove";
				showErrormsgboxTitle(msg);
		}
}

function saveSiteAffData(obj) {
	var errorList=[];
	if (errorList == ''){
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest('SiteAffected.html?saveSiteAffData',
			'POST', requestData, false);
	showConfirmBoxFoLoiPayment(returnData);
	}else{
		displayErrorsOnPage(errorList);
	}
}

function showConfirmBoxFoLoiPayment(sucessMsg) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("bt.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ sucessMsg.command.message + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="showScrutinyLabel()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function showScrutinyLabel() {
	$.fancybox.close();
	backToApplicationForm($(
	'#appId').val(),$('#taskId').val(),'ApplicationAuthorization.html',$('#serviceId').val(),$('#workflowId').val());
}

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<c:if test="${command.flag eq 'G' }">
				<h2>
					<spring:message code="lincense.gasPipeLine"
						text="Whether site affected by Gas Pipe Line" />
				</h2>
			</c:if>
			<c:if test="${command.flag eq 'W' }">
				<h2>
					<spring:message code="lincense.waterCourseLine"
						text="Whether site affected by Water Course Line" />
				</h2>
			</c:if>
			<c:if test="${command.flag eq 'H' }">
				<h2>
					<spring:message code="lincense.hTLine"
						text="Whether site affected by HT Line" />
				</h2>
			</c:if>

			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<form:form action="SiteAffected.html" class="form-horizontal form"
				name="frmSiteAff" id="frmSiteAff">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="flag" id="flag" />
				<input type="hidden" id="taskId"
					value="${userSession.scrutinyCommonParamMap.taskId}" />
				<input type="hidden" id="workflowId"
					value="${userSession.scrutinyCommonParamMap.workflowId}" />
				<input type="hidden" id="serviceId"
					value="${userSession.scrutinyCommonParamMap.SM_SERVICE_ID}" />
				<input type="hidden" id="appId"
					value="${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID}" />

				<div class="table-responsive" id="roadDigDiv">
					<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-bordered table-striped" id="tbl1">
						<tr>
							<th><spring:message code="sr.no" /></th>
							<th><spring:message code="lincense.capacity" text="Capacity" /><span
								class="mand">*</span></th>
							<th><spring:message code="lincense.bufferInMeteres"
									text="Buffer (In Meteres)" /><span class="mand">*</span></th>
							<th><a data-toggle="tooltip" data-placement="top" title=""
								class="btn btn-blue-2 btn-sm" data-original-title="Add"
								id="addSite"><i class="fa fa-plus"></i></a></th>
						</tr>
						<c:choose>
							<c:when test="${empty command.listSiteAffDto}">
								<tr id="tr${d}" class="appendableClass">
									<form:hidden path="listSiteAffDto[${d}].sId" />

									<td id="srNo">${d + 1}</td>

									<td><form:input type="text" class="form-control"
											path="listSiteAffDto[${d}].capacity" id="capacity${d}"></form:input></td>
									<td><form:input type="text" class="form-control"
											path="listSiteAffDto[${d}].buffer" id="buffers${d}"
											onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
									<td><a data-toggle="tooltip" data-placement="top" title=""
										class="btn btn-danger btn-sm" data-original-title="Delete"
										id="deleteSite"><i class="fa fa-trash"></i></a></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${command.listSiteAffDto}" var="details"
									varStatus="status">
									<tr id="tr${d}" class="appendableClass">
										<form:hidden path="listSiteAffDto[${d}].sId" />
										<td id="srNo">${d + 1}</td>
										<td><form:input type="text" class="form-control"
												path="listSiteAffDto[${d}].capacity" id="capacity${d}"></form:input></td>
										<td><form:input type="text" class="form-control"
												path="listSiteAffDto[${d}].buffer" id="buffers${d}"
												onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
										<td><a data-toggle="tooltip" data-placement="top"
											title="" class="btn btn-danger btn-sm"
											data-original-title="Delete" id="deleteSite"><i
												class="fa fa-trash"></i></a></td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveSiteAffData(this)">
						<spring:message code="professional.submit" />
					</button>
					<input type="button" class="btn btn-danger"
						onclick="backToApplicationForm(${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID},${userSession.scrutinyCommonParamMap.taskId},'ApplicationAuthorization.html',${userSession.scrutinyCommonParamMap.SM_SERVICE_ID},${userSession.scrutinyCommonParamMap.workflowId})"
						value="<spring:message code="back.button" />">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

