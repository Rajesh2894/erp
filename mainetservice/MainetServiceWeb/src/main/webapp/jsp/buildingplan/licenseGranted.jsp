
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
		  if($('#categoryOfAppliedLand'+countId).val()!='0' && $('#area'+countId).val()!='' && $('#npa'+countId).val()!='' && $('#balanceNpa'+countId).val()!='')
                {
			var row = '<td id="srNo">'
				     +srNo
					+ '</td>'
					+ '<td><input  type="text" class="form-control hasNumber" name="listLicenseDto['+cnt+'].categoryOfAppliedLand" id="categoryOfAppliedLand'+cnt+'"></input></td>'
					+ '<td><input  type="text" class="form-control hasNumber" name="listLicenseDto['+cnt+'].area" id="area'+cnt+'" onkeypress="return hasAmount(event, this, 13, 2)"></input></td>'
					+ '<td><input  type="text" class="form-control hasNumber" name="listLicenseDto['+cnt+'].npa" id="npa'+cnt+'" onkeypress="return hasAmount(event, this, 13, 2)"></input></td>'
					+ '<td><input  type="text" class="form-control hasNumber" name="listLicenseDto['+cnt+'].balanceNpa" id="balanceNpa'+cnt+'" onkeypress="return hasAmount(event, this, 13, 2)"></input></td>';
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
		
			$(this).find("input:text:eq(0)").attr("id", "categoryOfAppliedLand" + (i));
		    $(this).find("input:text:eq(1)").attr("id", "area" + (i));
		    $(this).find("input:text:eq(2)").attr("id", "npa" + (i));
		    $(this).find("input:text:eq(3)").attr("id", "balanceNpa" + (i));
			$(this).closest("tr").attr("id", "tr" + (i));
			$(this).closest('tr').find('#srNo').text(i+1);
		    //names
			
			$(this).find("input:text:eq(0)").attr("name", "listLicenseDto[" + (i) + "].categoryOfAppliedLand");
			$(this).find("input:text:eq(1)").attr("name", "listLicenseDto[" + (i) + "].area");
			$(this).find("input:text:eq(2)").attr("name", "listLicenseDto[" + (i) + "].npa");
			$(this).find("input:text:eq(3)").attr("name", "listLicenseDto[" + (i) + "].balanceNpa");
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

function saveLicenseData(obj) {
	var errorList=[];
	if (errorList == ''){
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest('LicenseGranted.html?saveLicenseData',
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

			<h2>
				<spring:message code="lincense.granted"
					text="License granted in sector where applied land is situated" />
			</h2>

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
			<form:form action="LicenseGranted.html" class="form-horizontal form"
				name="frmSiteAff" id="frmSiteAff">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

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
							<th><spring:message code="lincense.categoryofAppliedLand"
									text="Category of Applied Land" /><span class="mand">*</span></th>
							<th><spring:message code="lincense.area(InAcre)"
									text="Area (In Acre)" /><span class="mand">*</span></th>
							<th><spring:message code="lincense.nPANetPlannedArea"
									text="NPA (Net Planned Area) (In Acre)" /><span class="mand">*</span></th>
							<th><spring:message code="lincense.balanceNPAInAcre"
									text="Balance NPA (In Acre)" /><span class="mand">*</span></th>
							<th><a data-toggle="tooltip" data-placement="top" title=""
								class="btn btn-blue-2 btn-sm" data-original-title="Add"
								id="addSite"><i class="fa fa-plus"></i></a></th>
						</tr>
						<c:choose>
							<c:when test="${empty command.listLicenseDto}">
								<tr id="tr${d}" class="appendableClass">
									<form:hidden path="listLicenseDto[${d}].dId" />

									<td id="srNo">${d + 1}</td>

									<td><form:input type="text" class="form-control"
											path="listLicenseDto[${d}].categoryOfAppliedLand"
											id="categoryOfAppliedLand${d}"></form:input></td>
									<td><form:input type="text" class="form-control"
											path="listLicenseDto[${d}].area" id="area${d}"
											onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
									<td><form:input type="text" class="form-control"
											path="listLicenseDto[${d}].npa" id="npa${d}"
											onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
									<td><form:input type="text" class="form-control"
											path="listLicenseDto[${d}].balanceNpa" id="balanceNpa${d}"
											onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
									<td><a data-toggle="tooltip" data-placement="top" title=""
										class="btn btn-danger btn-sm" data-original-title="Delete"
										id="deleteSite"><i class="fa fa-trash"></i></a></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${command.listLicenseDto}" var="details"
									varStatus="status">
									<tr id="tr${d}" class="appendableClass">
										<form:hidden path="listLicenseDto[${d}].dId" />

										<td id="srNo">${d + 1}</td>

										<td><form:input type="text" class="form-control"
												path="listLicenseDto[${d}].categoryOfAppliedLand"
												id="categoryOfAppliedLand${d}"></form:input></td>
										<td><form:input type="text" class="form-control"
												path="listLicenseDto[${d}].area" id="area${d}"
												onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
										<td><form:input type="text" class="form-control"
												path="listLicenseDto[${d}].npa" id="npa${d}"
												onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
										<td><form:input type="text" class="form-control"
												path="listLicenseDto[${d}].balanceNpa" id="balanceNpa${d}"
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
						onclick="saveLicenseData(this)">
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

