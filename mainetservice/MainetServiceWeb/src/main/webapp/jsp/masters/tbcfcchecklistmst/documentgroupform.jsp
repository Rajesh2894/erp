<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/masters/tbcfcchecklistmst/documentgrouplist.js" type="text/javascript"></script>
<script src="js/tableHeadFixer.js"></script> 
<div id="heading_wrapper">
	<div class="form-div">
		<c:url value="${saveAction}" var="url_form_submit" />
		<form:form method="post" action="${url_form_submit}" name="checklistMas" id="checklistMas" class="form-horizontal" modelAttribute="cfcChecklistMstDto">
		<div class="warning-div alert alert-danger alert-dismissible hide" id="errorDivChecklistMas">
			<button type="button" class="close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
			<ul><form:errors path="*"/></ul>
		</div>
			<form:hidden path="" value="${mode}" id="formModeId"/>
			<c:if test="${mode != 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="docGroupMst.groupCpdId" />
			</c:if>			
				 <form:hidden path="docGroupMst.hiddenGroupCpdId" id="hiddenGrpCpdId" />	
		
			<div class="form-group">
               <label class="col-sm-2 control-label" for="groupCpdId"><spring:message code="master.docGroup"/><span class="mand">*</span></label>
              <div class="col-sm-4"><form:select id="groupCpdId" path="docGroupMst.groupCpdId" onchange="validateGroup(this)" disabled="true" cssClass="form-control mandClassColor" > <%-- ${mode != 'create' ? true : false} --%>
						<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
						<c:forEach items="${clgPrefixList}" var="clgPrefixData">						
							<form:option value="${clgPrefixData.lookUpId}">${clgPrefixData.lookUpDesc}</form:option>						
						</c:forEach>
					</form:select>
              </div>
 			</div>
  			<div class="table-responsive max-height-300" id="parent">
				<c:set var="d" value="0" scope="page" /> 
				<table class="table table-bordered table-striped" id="checklistMst"> 
					<thead>
						<tr>
							<th scope="col" width="200"><spring:message code="master.docGroup.docName"/></th>
							<th scope="col" width="200"><spring:message code="master.docGroup.docNameReg"/></th>
							<th scope="col" width="150"><spring:message code="master.docGroup.docType"/></th>
							<th scope="col" width="150"><spring:message code="master.docGroup.docTypeReg"/></th>
							<th scope="col" width="150"><spring:message code="master.docGroup.docSize"/></th>
							<th scope="col" width="150"><spring:message code="master.docGroup.mandatory" text="Mandatory"/></th>
							<th scope="col" width="20"><spring:message code="master.docGroup.sequence"/></th>
							<th scope="col" width="100"><spring:message code="master.docGroup.isRequired"/></th>
							<th scope="col" width="100"><spring:message code="master.docGroup.prefixName"/></th>
							<th scope="col" width="35"><a href=${mode eq 'View' ? 'javascript:void(0)' : '#'} onclick='return false;' class="btn btn-blue-2 btn-sm addChecklistLink"><i class="fa fa-plus-circle"></i></a> </th>
						</tr>
					</thead>
					<tbody>
						<c:choose>			
							<c:when test="${fn:length(cfcChecklistMstDto.docGroupList) > 0}">
								<c:forEach var="chcLstData" items="${cfcChecklistMstDto.docGroupList}" varStatus="status" >
									<tr class="appendableClass" >
										<td><form:input path="docGroupList[${d}].docName" cssClass="form-control" maxlength="500" id="docName${d}" onblur="checkForDuplicateDocName(this,${d});"/></td>
										<td><form:input path="docGroupList[${d}].docNameReg" cssClass="form-control" maxlength="500" id="docNameReg${d}" onblur="checkForDuplicateDocName(this,${d});"/></td>
										<td><form:input path="docGroupList[${d}].docType" cssClass="form-control" id="docType${d}"/></td>
										<td><form:input path="docGroupList[${d}].docTypeReg" cssClass="form-control" id="docTypeReg${d}"/></td>
										<td><form:input path="docGroupList[${d}].docSize" cssClass="form-control" id="docSize${d}" onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')" /></td>
										<td><form:select path="docGroupList[${d}].ccmValueset" class="form-control" id="ccmValueset${d}" >
												<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
												<c:forEach items="${setPrefixList}" var="setPrefix">
												<c:if test="${userSession.languageId == 1}">
												<form:option value="${setPrefix.lookUpId}" code="${setPrefix.lookUpCode}" >${setPrefix.descLangFirst}</form:option>
												</c:if>
												<c:if test="${userSession.languageId == 2}">
												<form:option value="${setPrefix.lookUpId}" code="${setPrefix.lookUpCode}" >${setPrefix.descLangSecond}</form:option>
												</c:if>
												</c:forEach>
											</form:select>
										</td>
										<td><form:input path="docGroupList[${d}].docSrNo" cssClass="form-control" id="docSrNo${d}" onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')" onblur="checkForSeqNo(this,${d});"/>
										<td>
										<form:select path="docGroupList[${d}].docPrefixRequired" class="form-control" onchange="disSelectbox(this,${d});" id="docPrefixRequired${d}" >
											<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
											<form:option value="Y">YES</form:option>
											<form:option value="N">NO</form:option>
										</form:select>
									</td>
									<td>
										<form:select path="docGroupList[${d}].prefixName" class="form-control" id="prefixName${d}" >
											<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
											<form:option value="UDN">UDN</form:option>
											<form:option value="IDP">IDP</form:option>
										</form:select>
									</td>
										<form:hidden path="docGroupList[${d}].dgId" id="dgId${d}" /></td>
										<td class="text-center"><a href='#'  onclick='return false;' class='btn btn-danger btn-sm deleteChargesLink'><i class="fa fa-trash"></i></a></td>
										<c:set var="d" value="${d + 1}" scope="page"/>
									</tr>					
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="appendableClass" >
									<td><form:input path="docGroupList[${d}].docName" cssClass="form-control" id="docName${d}" onblur="checkForDuplicateDocName(this,${d});"/></td>
									<td><form:input path="docGroupList[${d}].docNameReg" cssClass="form-control" id="docNameReg${d}" onblur="checkForDuplicateDocName(this,${d});"/></td>
									<td><form:input path="docGroupList[${d}].docType" cssClass="form-control" id="docType${d}"/></td>
									<td><form:input path="docGroupList[${d}].docTypeReg" cssClass="form-control" id="docTypeReg${d}"/></td>
									<td><form:input path="docGroupList[${d}].docSize" cssClass="form-control" id="docSize${d}" onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')" /></td>
									<td><form:select path="docGroupList[${d}].ccmValueset" class="form-control" id="ccmValueset${d}" >
											<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
											<c:forEach items="${setPrefixList}" var="setPrefix">
												<form:option value="${setPrefix.lookUpId}" code="${setPrefix.lookUpCode}" >${setPrefix.descLangFirst}</form:option>
											</c:forEach>
										</form:select>						
									</td>
									<td><form:input path="docGroupList[${d}].docSrNo" cssClass="form-control" id="docSrNo${d}" onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')" onblur="checkForSeqNo(this,${d});"/>
									<td>
										<form:select path="docGroupList[${d}].docPrefixRequired" class="form-control" onchange="disSelectbox(this,${d});" id="docPrefixRequired${d}" >
											<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
											<form:option value="Y">YES</form:option>
											<form:option value="N">NO</form:option>
										</form:select>
									</td>
									<td>
										<form:select path="docGroupList[${d}].prefixName" class="form-control" id="prefixName${d}" >
											<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
											<form:option value="UDN">UDN</form:option>
											<form:option value="IDP">IDP</form:option>
										</form:select>
									</td>
									<form:hidden path="docGroupList[${d}].dgId" id="dgId${d}" /></td>									
									<td class="text-center"><a href='#'  onclick='return false;' class='btn btn-danger btn-sm deleteChargesLink'><i class="fa fa-trash"></i></a></td>
									<c:set var="d" value="${d + 1}" scope="page"/>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>				
			</div>			
			<div class="text-center padding-top-10">
				<input type="button" class="btn btn-success btn-submit" value="<spring:message code="save"/>" onclick="return submitDocMasterForm(this);" id="submitBtnId">
		    	 <input type="button" class="btn btn-warning" value="<spring:message code="reset.msg" text="Reset"/>" onclick="resetDocumentForm();">
				<input type="button" class="btn btn-danger" value="<spring:message code="back.msg" text="Back"/>" onclick="window.location.href='DocumentGroupMaster.html'" />
			</div>
			
		</form:form>
	</div>
</div>

<script>
$(document).ready(function() {
	$("#checklistMst").tableHeadFixer(); 
});
</script>









