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
$(document).ready(function(){
	
	    $('select[name="csmrInfo.codDwzid1"]').attr('disabled',true); 
		  $('select[name="csmrInfo.codDwzid2"]').attr('disabled',true); 
		
	
	$("#addRoad").click(
			function() {
				var cnt = $('#tbl1 tr').length - 1;
				var srNo=cnt+1;
				var countId=cnt-1;
			  if($('#roadType'+countId).val()!='0' && $('#roadLength'+countId).val()!='')
	                {
				var row = '<td id="srNo">'
					     +srNo
						+ '</td>'
						+ '<td>'
						+ '<c:set var="baseLookupCode" value="RDT" />'
						+ '<select name="csmrInfo.roadList['+cnt+'].crtRoadTypes" id="roadType'+cnt+'" class="form-control">'
						+ '<option value="0">'
						+ "Select Road Type"
						+ '</option>'
						+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
						+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
						+ '</c:forEach>'
						+ '</select></td>'
						+ '<td><input  type="text" class="form-control hasNumber" name="csmrInfo.roadList['+cnt+'].crtRoadUnits" id="roadLength'+cnt+'" onkeypress="return hasAmount(event, this, 13, 2)"></input></td>'
						+ '<td><input  type="text" class="form-control hasNumber" name="csmrInfo.roadList['+cnt+'].crtRoadTrench" id="roadTrench'+cnt+'" onkeypress="return hasAmount(event, this, 13, 2)"></input></td>';
				$('#tbl1 tr')
						.last()
						.after(
								'<tr id="tr'+cnt+'" class="appendableClass">'
										+ row
										+ '<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm" data-original-title="Delete Road" id="deleteRoad" onclick="removeRow('
										+ cnt
										+ ')"><i class="fa fa-trash"></i></a></td></tr>');
				        cnt++;
				        reorderRoad();
	                }
	            else
				  {
				   showErrormsgboxTitle("water.enter.mand.fields");
				   }

			});

$("#deleteRoad").click(function() {
if ($('#tbl1 tr').size() > 2) {

	$('#tbl1 tr:last-child').remove();
	cnt--;
} else {

	/*  var msg = getLocalMessage('lgl.cantRemove'); */
	var msg = "can not remove";
	showErrormsgboxTitle(msg);
}
});



});
function saveRoadDetails(element)
{
	var appId='${command.lableValueDTO.applicationId}';
    var serviceId= '${command.serviceId}';
    var labelId='${command.lableValueDTO.lableId}';
	saveOrUpdateForm(element,"Your application saved successfully!", '', 'save');
    if($("#error").val()!='Y')
	{
		 loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',appId,labelId,serviceId); 
	} 
}

function removeRow(cnt)
{

if($('#tbl1 tr').size()>2){
		$("#tr"+cnt).remove();
		reorderRoad();
		
		cnt--;
		
		}else{
			
			/* var msg = getLocalMessage('lgl.cantRemove');*/
			var msg="cant not remove";
				showErrormsgboxTitle(msg);
		}
}

function reorderRoad()
{
	$('.appendableClass').each(function(i) {
		
			$(this).find("input:text:eq(0)").attr("id", "roadLength" + (i));
		    $(this).find("input:text:eq(1)").attr("id", "roadTrench" + (i));
			$(this).find("select:eq(0)").attr("id", "roadType" + (i));
			$(this).closest("tr").attr("id", "tr" + (i));
			
			$(this).closest('tr').find('#srNo').text(i+1);
		
		    //names
			$(this).find("input:text:eq(0)").attr("name", "csmrInfo.roadList[" + (i) + "].crtRoadUnits");
			$(this).find("input:text:eq(1)").attr("name", "csmrInfo.roadList[" + (i) + "].crtRoadTrench");
		    $(this).find("select:eq(0)").attr("name", "csmrInfo.roadList[" + (i) + "].crtRoadTypes");
		    $(this).find("#deleteConnection").attr("onclick", "deleteRoad(" + (i) + ")");
			});

}
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.road.det"/></h2>
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
			<form:form action="RoadDiggingConnectionForm.html"
				class="form-horizontal form" name="frmRoadDetails"
				id="frmRoadDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<h4 class="margin-top-0"><spring:message code="water.form.appdetails"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.road.service"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" path="serviceName" id="serviceName" disabled="true"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.road.applNo"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" disabled="true"
							path="" value="${command.applicationId}"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.road.applName"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled="true"
							path="applicantFullName"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.road.applDate"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" disabled="true"
							path="" value="${command.applicationDate}"></form:input>
					</div>
				</div>
				<div class="form-group"> 
      <apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true" showOnlyLabel="false"
									pathPrefix="csmrInfo.codDwzid" isMandatory="false" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true" cssClass="form-control" showAll="true" />
  
 </div>
				
				<h4><spring:message code="water.road.det"/></h4>
			<!-- 	<div class="form-group">
					<label class="checkbox-inline"> <input name=""
						type="checkbox" value="Digging" id="roadDig"> Road Digging
						Required
					</label>
				</div> -->
			 <div class="table-responsive" id="roadDigDiv">
					<table class="table table-bordered table-striped" id="tbl1">
						<tr>
							<th><spring:message code="water.uploadDoc.srNo"/></th>
							<th><spring:message code="water.road.roadType"/><span class="mand">*</span></th>
							<th><spring:message code="water.road.roadlen"/><span class="mand">*</span></th>
							<th><spring:message code="water.road.roadTrench"/></th>
							<th><a data-toggle="tooltip" data-placement="top" title=""
								class="btn btn-blue-2 btn-sm" data-original-title="Add"
								id="addRoad"><i class="fa fa-plus"></i></a></th>
						</tr>
						<c:choose>
						  <c:when test="${empty command.csmrInfo.roadList}" >
						<tr id="tr0" class="appendableClass">
						<form:hidden path="csmrInfo.roadList[0].crtId"/>
				
							<td id="srNo">1</td>
							<td><c:set var="baseLookupCode" value="RDT" /> <form:select
								path="csmrInfo.roadList[0].crtRoadTypes" class="form-control "
								id="roadType0">
									<form:option value="0"><spring:message code="water.road.sel.roadType"/></form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
							</form:select></td>
							<td><form:input name="" type="text" class="form-control"
									path="csmrInfo.roadList[0].crtRoadUnits" id="roadLength0" onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
							<td><form:input name="" type="text" class="form-control"
									path="csmrInfo.roadList[0].crtRoadTrench" id="roadTrench0" onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
							<td><a data-toggle="tooltip" data-placement="top" title=""
								class="btn btn-danger btn-sm" data-original-title="Delete"
								id="deleteRoad"><i class="fa fa-trash"></i></a></td>
						</tr>
						</c:when>
                       <c:otherwise>
								     <c:forEach items="${command.csmrInfo.roadList}" var="details" varStatus="status">
								     
								<tr id="tr${status.count-1}" class="appendableClass">
								<form:hidden path="csmrInfo.roadList[${status.count-1}].crtId"/>
							    <td id="srNo">${status.count}</td>
									<td>
									<c:set var="baseLookupCode" value="RDT" /> <form:select
								path="csmrInfo.roadList[${status.count-1}].crtRoadTypes" class="form-control "
								id="roadType${status.count-1}">
									<form:option value="0"><spring:message code="water.road.sel.roadType"/></form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
							   </form:select>
								 </td>
									<td><form:input name="" type="text" class="form-control"
											path="csmrInfo.roadList[${status.count-1}].crtRoadUnits" id="roadLength${status.count-1}" onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
									<td><form:input name="" type="text" class="form-control"
											path="csmrInfo.roadList[${status.count-1}].crtRoadTrench" id="roadTrench${status.count-1}" onkeypress="return hasAmount(event, this, 13, 2)"></form:input></td>
								    <td><a data-toggle="tooltip" data-placement="top" title=""
										class="btn btn-danger btn-sm"
									data-original-title="Delete" id=deleteRoad onclick="removeRow(${status.count-1})"><i
											class="fa fa-trash"></i></a></td>
								</tr>
								
								</c:forEach>
								</c:otherwise>
								</c:choose>
					</table>
				</div>  
				<c:set var="appId" value="${command.lableValueDTO.applicationId}"/>
				<c:set var="labelId" value="${command.lableValueDTO.lableId}"/>
				<c:set var="serviceId" value="${command.serviceId}"/>
				  <form:hidden path="hasError" id="error"/> 
				  <form:hidden path="rowCount" id="rowCount"/>
					<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit" onclick="saveRoadDetails(this)"><spring:message code="water.btn.submit"/></button>
				 <input type="button" onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${appId}','${labelId}','${serviceId}')" 
				 class="btn btn-danger" value="<spring:message code="water.btn.back"/>">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

 