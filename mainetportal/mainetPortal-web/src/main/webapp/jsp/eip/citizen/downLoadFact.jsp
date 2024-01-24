<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>

<style>
.tableClass tr td {
	border : 1px solid #D8DBDD;
	text-align: left;
}
</style>


<script>
function fn_getrtiInformation(obj) {
 	var deptId = $("#"+obj.id+" option:selected").val();
	var postdata = 'deptId=' + deptId;  
	var resulthtml = __doAjaxRequest('RightToInformation.html?searchRtiInfo', 'post',postdata, false, 'html');
	$('#rtiFactDiv').empty();
	$('#rtiFactDiv').html(resulthtml);
	
}
</script>

<div id="heading_wrapper">

	<div id="heading_bredcrum">
		<ul>
			<li><a href="CitizenHome.html"><spring:message code="menu.home"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="menu.eip"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="eip.download"/></a></li>
			<li>&gt;</li>
			<li>Rights To Information</li>
		</ul>
	</div>

</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="downloadinfact.html" name="frmBankDetail" id="frmBankDetail">
					
					<div class="form-elements">
						<div class="form-elements">
							<div class="element">
								<label for="deptId_dpDeptid"><spring:message
										code="eip.rti.departmentSel" /> <span class="mand">*</span></label>
								<apptags:selectField isLookUpItem="true"
									changeHandler="fn_getrtiInformation(this);"
									items="${command.allDepartments}" hasId="true"
									selectOptionLabelCode="Select Department Name"
									fieldPath="deptId.dpDeptid" />
							</div>
						</div>
					</div>

				</form:form>

					<div id="rtiFactDiv">
					
						<table class="tableClass">
							<tr>
								<th>Department Name</th>
								<th>Subject</th>
								<th>Download Document</th>
							</tr>
	
							<c:forEach var="lookUp" items="${command.righToInformationModel.rightInformation}">
								<tr>
									<td>${lookUp.department.lookUpDesc}</td>
									<td>${lookUp.lookUpDesc}</td>
									<td>
										<c:set var="links" value="${fn:split(lookUp.otherField,',')}"/>
										<c:forEach items="${links}" var="download" varStatus="status"> 
											<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
											<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
											<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}"/>
											<form action="RightToInformation.html?Download" method="post" id="frm${idappender}_${status.count}" target="_blank" >
												<a href="javascript:void(0);" target="_blank" onclick="javascript:document.getElementById('frm${idappender}_${status.count}').submit();">${link}</a>
												<input type="hidden" name="downloadLink" value="${download}">
											</form>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
	
						</table>
					</div>
			</div>
		</div>
	</div>
</div>
