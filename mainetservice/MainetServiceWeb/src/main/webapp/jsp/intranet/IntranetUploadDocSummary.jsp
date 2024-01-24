<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"	rel="stylesheet" type="text/css" />

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/intranet/intranetUploadDocSummary.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
		
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="intranet.uploadDocSummary" text="Intranet Upload Document Summary" /></strong>
			</h2>
		</div>
		
		<div class="widget-content padding">
			<!-- Search Criteria -->
			<form:form action="UploadIntranetDocSummary.html" method="POST"
						commandName="command" class="form-horizontal form"
						name="frmUploadIntranetDocSummary" id="frmUploadIntranetDocSummary">
			<jsp:include page="/jsp/tiles/validationerror.jsp"/>

				<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;">
						<i class="fa fa-plus-circle"></i>
				</div>
				

				   <div class="form-group">
						<label class="control-label col-sm-2 required-control"
						for="Census"> <spring:message code="intranet.DocCatagoryType" text="Document Category Type" />
						</label>
							     <c:set var="baseLookupCode" value="IDC" />
								 <apptags:lookupField
								  items="${command.getLevelData(baseLookupCode)}"
								  path="intranetDto.docCateType" cssClass="form-control"
								  isMandatory="true" hasId="true"
								  selectOptionLabelCode="selectdropdown" />
				</div>
			
				<!-- Buttons start -->

				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2" onclick = "searchIntranetData()"
						title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="intranet.Srch" text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='UploadIntranetDocSummary.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="intranet.Reset" text="Reset" />
					</button>
					
					<input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="Back"> 

					<button type="button" id="add" class="btn btn-blue-2"
						onclick="openFormIntranet('UploadIntranetDocSummary.html','UploadIntranetDoc', 'ADD')"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="intranet.Add" text="Add" />
					</button>

				</div>
				
			    <!-- Table Grid Start  -->
	         
	          <div class="table-responsive clear">
	          
						<table class="table table-striped table-bordered"
							id="hospitalDataTable">
							<thead>
								<tr>
									<th><spring:message code="intranet.srNo" text="Sr No" /></th>
									<th><spring:message code="intranet.docCat" text="Document Category" /></th>
									<th><spring:message code="intranet.DocName" text="Document Name" /></th>
									<th><spring:message code="intranet.DocDesc" text="Document Description" /></th>
									<th><spring:message code="intranet.dept" text="Department" /></th>
									<th width="10%" align="center"><spring:message code="" text="Action" /></th> 
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty command.fetchIntranetListMas}">
									<c:forEach items="${command.fetchIntranetListMas}" var="lookUp" varStatus="item">		
										<tr>
										   <td class="text-center">${item.count}</td>
											<td align="center">${lookUp.docCatDesc}</td>
											<td align="center">${lookUp.docName}</td>
											<td align="center">${lookUp.docDesc}</td>
											<td align="center">${lookUp.deptDesc}</td>
											<td>
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit Hospital Master"
													onclick="modifyIntranetDoc('${lookUp.inId}','UploadIntranetDocSummary.html','editIntranetForm','E')">
													<i class="fa fa-pencil"></i>
												</button> 		
											</td> 						
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${not empty command.fetchintranetDtoList}">
									<c:forEach items="${command.fetchintranetDtoList}" var="lookUpList" varStatus="item">		
										<tr>
										   <td class="text-center">${item.count}</td>
											<td align="center">${lookUpList.docCatDesc}</td>
											<td align="center">${lookUpList.docName}</td>
											<td align="center">${lookUpList.docDesc}</td>
											<td align="center">${lookUpList.deptDesc}</td>
											<td>
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit Hospital Master"
													onclick="modifyIntranetDoc('${lookUpList.inId}','UploadIntranetDocSummary.html','editIntranetForm','E')">
													<i class="fa fa-pencil"></i>
												</button> 		
											</td> 						
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
				</div>		
			</form:form>
</div>
</div>
</div>

<!-- ashish test -->

