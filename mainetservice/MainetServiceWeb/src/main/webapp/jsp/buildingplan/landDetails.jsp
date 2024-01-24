
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/cfc/scrutiny.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var landDetailsMap = $('#landDetailsMap').val(); 
	getArea();
	for (var j = 0; j < landDetailsMap; j++) {
		calculateTotalArea(j);
		calculateOldTotalArea(j) ;
		var pTotal = parseFloat($('#total'+j).val()) || 0;
		$('#oldPTotalArea'+j).val(pTotal.toFixed(5));
	}
	
});

function saveLicenseDetails(obj) {
	var errorList=[];
	if (errorList == ''){
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest('LandDetails.html?saveLicenseDetails',
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

function getArea() {
	$('.landTable').each(function(id) {
	var type = $('#type' + id).val(); 
	var kanalValue = parseFloat($(
			'#cBigha'+id).val()) || 0;
			var marlaValue = parseFloat($(
			'#cBiswa'+id).val()) || 0;
			var sarsaiValue = parseFloat($(
			'#cBiswansi'+id).val()) || 0;
	if(type=='C'){
	var conversionKanalFactor = 0.125;
	var conversionMarlaFactor = 0.00625001;
	var conversionSarsaiFactor = 0.0006944438305254;

	var kanalAcreValue = kanalValue
	* conversionKanalFactor;
	var marlaAcreValue = marlaValue
	* conversionMarlaFactor;
	var sarsaiAcreValue = sarsaiValue
	* conversionSarsaiFactor;

	var totalValue = kanalAcreValue
	+ marlaAcreValue
	+ sarsaiAcreValue;

	totalValue = parseFloat(totalValue
	.toFixed(5));

	$('#totalArea'+id).val(
	totalValue);
	}
	else{
	var conversionBighaFactor = 0.619;
	var conversionBiswaFactor = 0.0309;
	var conversionBiswansiFactor = 0.03099173553000000164;

	var bighaAcreValue = kanalValue
		* conversionBighaFactor;
	var biswaAcreValue = marlaValue
		* conversionBiswaFactor;
	var biswansiAcreValue = sarsaiValue
		* conversionBiswansiFactor;

	var totalValue = bighaAcreValue
		+ biswaAcreValue
		+ biswansiAcreValue;

	$('#totalArea'+id).val(
			totalValue);
	}
	});
}
	
function getTotalArea(id,tbId) {
var type = $('#type' + id).val(); 
var kanalValue = parseFloat($(
		'#cBigha'+id).val()) || 0;
		var marlaValue = parseFloat($(
		'#cBiswa'+id).val()) || 0;
		var sarsaiValue = parseFloat($(
		'#cBiswansi'+id).val()) || 0;
if(type=='C'){
var conversionKanalFactor = 0.125;
var conversionMarlaFactor = 0.00625001;
var conversionSarsaiFactor = 0.0006944438305254;

var kanalAcreValue = kanalValue
* conversionKanalFactor;
var marlaAcreValue = marlaValue
* conversionMarlaFactor;
var sarsaiAcreValue = sarsaiValue
* conversionSarsaiFactor;

var totalValue = kanalAcreValue
+ marlaAcreValue
+ sarsaiAcreValue;

totalValue = parseFloat(totalValue
.toFixed(5));

$('#totalArea'+id).val(
totalValue);
calculateTotalArea(tbId);
}
else{
var conversionBighaFactor = 0.619;
var conversionBiswaFactor = 0.0309;
var conversionBiswansiFactor = 0.03099173553000000164;

var bighaAcreValue = kanalValue
	* conversionBighaFactor;
var biswaAcreValue = marlaValue
	* conversionBiswaFactor;
var biswansiAcreValue = sarsaiValue
	* conversionBiswansiFactor;

var totalValue = bighaAcreValue
	+ biswaAcreValue
	+ biswansiAcreValue;

$('#totalArea'+id).val(
		totalValue);
calculateTotalArea(tbId);

}
}

function calculateTotalArea(tbId) {
	var totalConsolArea = 0;
	var grandTotal = 0;
	var tableId = "#tb"+tbId;
	var selectedRow = tableId +" > tbody > tr";
	$(selectedRow).each(function(i){
		var value = parseFloat($(selectedRow+":eq("+i+")").find("input[id^=totalArea]").val()) || 0;
		totalConsolArea += value;
	})
	$('#total'+tbId).val(
			totalConsolArea.toFixed(5));

	 var landDetailsMap = $('#landDetailsMap').val(); 
	for (var j = 0; j < landDetailsMap; j++) {
		var grandValue = parseFloat($(selectedRow+":eq("+j+")").find("input[id^=total]").val()) || 0;
		var grandValue1 = parseFloat($('#total'+j).val()) || 0;
		grandTotal += grandValue1;
	} 

	$('#grandTotalArea').val(
			grandTotal.toFixed(5));
}

function calculateOldTotalArea(tbId) {
var totalConsolArea = 0;
var tableId = "#tb"+tbId;
var selectedRow = tableId +" > tbody > tr";
$(selectedRow).each(function(i){
	var value =(parseFloat($(selectedRow+":eq("+i+")").find("input[id^=consolTotArea]").val()|| 0))+(parseFloat($(selectedRow+":eq("+i+")").find("input[id^=nonConsolTotArea]").val()|| 0));
	totalConsolArea += value;
})
$('#oldTotal'+tbId).val(
		totalConsolArea.toFixed(5));
}

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lincense.landDetails" text="Land Details" />
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
			<form:form action="LandDetails.html" class="form-horizontal form"
				name="frmSiteAff" id="frmSiteAff">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
                <form:hidden path="fieldFlag" id="fieldFlag" />
				<input type="hidden" id="taskId"
					value="${userSession.scrutinyCommonParamMap.taskId}" />
				<input type="hidden" id="workflowId"
					value="${userSession.scrutinyCommonParamMap.workflowId}" />
				<input type="hidden" id="serviceId"
					value="${userSession.scrutinyCommonParamMap.SM_SERVICE_ID}" />
				<input type="hidden" id="appId"
					value="${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID}" />
				<input type="hidden" id="landDetailsMap"
					value="${command.landDetailsMap.size()}" />
				<div class="table-responsive" id="roadDigDiv">
					<c:set var="d" value="0" scope="page"></c:set>
					<c:set var="e" value="0" scope="page"></c:set>
					<c:forEach items="${command.landDetailsMap}" var="desgMapVar1">

						<tr>
							<th><spring:message text="Name of Land Owner:" />
								${desgMapVar1.key}</th>
						</tr>
						<table class="table table-bordered table-striped landTable" id="tb${e}">

							<thead>
								<th><spring:message code="lincense.village" text="Village" /><span
									class="mand">*</span></th>
								<th><spring:message code="lincense.rectangleNumber"
										text="Rectangle Number" /><span class="mand">*</span></th>
								<th><spring:message code="lincense.killaNo"
										text="Killa NO." /><span class="mand">*</span></th>
								<th><spring:message code="lincense.consolidationType"
										text="Consolidation Type" /><span class="mand">*</span></th>
								<th><spring:message code="lincense.area" text="Area" /><span
									class="mand">*</span></th>
								<th><spring:message code="lincense.kanal/bigha"
										text="Kanal/Bigha" /><span class="mand">*</span></th>
								<th><spring:message code="lincense.marla/biswa"
										text="Marla/Biswa" /><span class="mand">*</span></th>
								<th><spring:message code="lincense.sarsai/biswansi"
										text="Sarsai/Biswansi" /><span class="mand">*</span></th>
								<c:if test="${command.fieldFlag eq 'F' }">		
								<th><spring:message 
										text="Patwari Field NCZ" /><span class="mand">*</span></th>
								</c:if>				
								<th><spring:message code="lincense.ownershipVerified"
										text="Ownership Verified" /><span class="mand">*</span></th>
								<th><spring:message code="lincense.Include/ExcludeArea"
										text="Include/Exclude Area" /><span class="mand">*</span></th>
								<th><spring:message code="lincense.totalArea"
										text="Total Area (in acres)" /><span class="mand">*</span></th>
								<th><spring:message code="lincense.remarks" text="Remarks" /><span
									class="mand">*</span></th>

							</thead>
							<tbody>
								<c:forEach var="dataList" items="${desgMapVar1.value}"
									varStatus="status">
									<tr id="tr${d}" class="appendableClass${e}">
										<form:hidden
											path="landDetailsMap[${dataList.landOwnerName}][${status.index}].licAcquDetId" />
										<form:hidden
											path="landDetailsMap[${dataList.landOwnerName}][${status.index}].consolTotArea"
											id="consolTotArea${d}" />
										<form:hidden
											path="landDetailsMap[${dataList.landOwnerName}][${status.index}].nonConsolTotArea"
											id="nonConsolTotArea${d}" />

										<td><form:textarea type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].revEstate"
												disabled="true"></form:textarea> <form:textarea type="text"
												class="form-control hide"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].type"
												id="type${d}"></form:textarea></td>
										<td style="width: 70px;"><form:textarea type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].rectangleNo"
												disabled="true"></form:textarea></td>
										<td style="width: 110px;"><form:textarea type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].khasraNo"
												disabled="true"></form:textarea></td>
										<td style="width: 90px;"><form:input type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].consolType"
												disabled="true"></form:input></td>
										<td style="width: 120px;"><form:input type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].bArea"
												disabled="true"></form:input></td>
										<td style="width: 80px;"><form:input type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].cBigha"
												onchange="getTotalArea(${d},${e})" id="cBigha${d}"></form:input></td>

										<td style="width: 80px;"><form:input type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].cBiswa"
												id="cBiswa${d}" onchange="getTotalArea(${d},${e})"></form:input></td>
										<td style="width: 80px;"><form:input type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].cBiswansi"
												id="cBiswansi${d}" onchange="getTotalArea(${d},${e})"></form:input></td>
										<c:if test="${command.fieldFlag eq 'F' }">		
										<td><form:radiobutton
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].patwariFieldNcz"
												value="Y" name="patwariFieldNcz${d}"
												class="type margin-left-25" disabled="" /> <label>
												<spring:message text="Yes" />
										</label> <form:radiobutton
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].patwariFieldNcz"
												value="N" name="patwariFieldNcz${d}"
												class="type margin-left-40" disabled="" /> <label
											class="margin-left-15"> <spring:message text="No" />
										</label></td>
										</c:if>		
										<td style="width: 70px;"><form:radiobutton
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].ownershipVerified"
												value="Y" name="ownershipVerified${d}"
												class="type margin-left-25" disabled="" /> <label>
												<spring:message text="Yes" />
										</label> <form:radiobutton
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].ownershipVerified"
												value="N" name="ownershipVerified${d}"
												class="type margin-left-40" disabled="" /> <label
											class="margin-left-15"> <spring:message text="No" />
										</label></td>
										<td style="width: 70px;"><form:checkbox
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].includeExcludeArea"
												value="Y" id="type" class="type margin-left-0" disabled="" />
											<label class="margin-left-20"> <spring:message
													text="Excluded" />
										</label></td>
										<td style="width: 90px;"><form:input type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].totalArea"
												id="totalArea${d}" onkeypress="return hasAmount(event, this, 10, 5)"></form:input></td>
										<td><form:textarea type="text" class="form-control"
												path="landDetailsMap[${dataList.landOwnerName}][${status.index}].remarks"></form:textarea></td>
									</tr>

									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th colspan="3"><spring:message
											text="Total Area (in acres)" /><span class="mand">*</span></th>
									<td colspan="2"><form:input type="text" id="oldTotal${e}"
											disabled="true" class="form-control" onkeypress="return hasAmount(event, this, 10, 5)" path=""></form:input></td>
									<c:if test="${command.fieldFlag eq 'F' }">	
									<th colspan="2"><spring:message
											text="Total Area By Patwari HQ(in acres)" /><span class="mand">*</span></th>
									<td colspan="2"><form:input type="text" id="oldPTotalArea${e}"
											disabled="true" class="form-control" onkeypress="return hasAmount(event, this, 10, 5)" path=""></form:input></td>
									</c:if>		
									
									<c:if test="${command.fieldFlag eq 'NF' }">
									<th colspan="5">	<spring:message
											text="Total Area By Patwari HQ(in acres)" /><span
										class="mand">*</span></th></c:if>
									<c:if test="${command.fieldFlag eq 'F' }">	
									<th colspan="2"><spring:message
											text="Total Area By Patwari Field(in acres)" /><span
										class="mand">*</span></th></c:if>	
										
									<td colspan="2"><form:input type="text" disabled="true"
											class="form-control" onkeypress="return hasAmount(event, this, 5, 5)" id="total${e}" path=""></form:input></td>
								</tr>
							</tfoot>
						</table>

						<c:set var="e" value="${e + 1}" scope="page" />
					</c:forEach>
					<table>
						<tr>
							<strong><th colspan="3" style="background: white;"><spring:message
										text="Grand Total Area (in acres)" /><span class="mand">*</span></th></strong>
							<td colspan="7" style="padding-left: 20px;"><form:input type="text" id="grandTotalArea"
									style="background:white;" disabled="true" class="form-control" path=""></form:input></td>
						</tr>
					</table>

				</div>
				<h4>
				<spring:message code="professional.uploaded.document" text="Uploaded Document" />
			</h4>
				<div class="container">
					<table class="table table-bordered table-striped" id="tbl1">
						<tr>
							<th width="20%" align="center"><spring:message
									code="sr.no" /></th>
							<th width="50%" align="center"><spring:message
									code="field.name" text="Field Name" /></th>
							<th width="30%" align="center"><spring:message
									code="file.upload" text="File Upload" /></th>
						</tr>
						<tr id="sUpload1">
							<td class="text-center">1</td>
							<td><spring:message
									 text="Shajra Plan By Patwari Field PDF" /></td>
							<td><apptags:formField fieldType="7" labelCode=""
									hasId="true" folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT"
									fieldPath=""
									fileSize="COMMOM_MAX_SIZE"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									isMandatory="false" showFileNameHTMLId="true" currentCount="0" />
							</td>
						</tr>
						
					</table>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveLicenseDetails(this)">
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

