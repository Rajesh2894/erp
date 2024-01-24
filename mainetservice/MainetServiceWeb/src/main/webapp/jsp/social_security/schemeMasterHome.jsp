<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/social_security/schemeMasterHome.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="soc.schemeMaster" text="Scheme Master" />
				</h2>
				<apptags:helpDoc url="PensionSchemeMaster.html"></apptags:helpDoc>
			</div>	
			<!-- start of section for search functional code-->
			<div class="widget-content padding">
				<form:form action="PensionSchemeMaster.html"
					class="form-horizontal" name="PensionSchemeMaster"
					id="PensionSchemeMaster">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div class="form-group">
						<label for="" class="col-sm-2 control-label"><spring:message code="social.sec.schemename" text="Scheme Name"/></label>
						<div class="col-sm-4">
							<form:select name="serviceId" path="pensionSchmDto.serviceId"
								id="serviceId" class="form-control  chosen-select-no-results" disabled="false">
								<option value="0"><spring:message text="Select" /></option>
								<c:forEach items="${command.serviceList}" var="objArray">
									<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">
											<form:option value="${objArray[0]}" code="${objArray[2]}"
												label="${objArray[1]}"></form:option>
										</c:when>
										<c:otherwise>
											<form:option value="${objArray[0]}" code="${objArray[2]}"
												label="${objArray[3]}"></form:option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select>
						</div>		
					</div>

					<div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-success"
							id="searchSMH">
							<spring:message code="social.btn.search" text="Search" />
						</button>
						<button type="button" class="btn btn-blue-2" id="createSchemeMaster">
							<spring:message code="social.btn.add" text="Add" />
						</button>

						<button class="btn btn-warning  reset" type="reset"
							onclick="window.location.href='PensionSchemeMaster.html'">
							<i class="button-input"></i>
							<spring:message code="social.btn.reset" text="Reset"/>
						</button>
					</div>

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered" id="schemeMstHome">
							<thead>
								<tr>

									<th width="15%" align="center"><spring:message 
											code="Soc.SrNo" text="Sr.No" /></th>
									<th width="15%" align="center"><spring:message 
											code="social.schemeCode" text="Scheme Code" /></th>
									<th width="30%" align="center"><spring:message 
											code="social.sec.schemename" text="Scheme Name" /></th>
									<th width="15%" align="center"><spring:message 
											code="sor.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${command.viewList}" var="view" varStatus="getindex">							<tr>
							
								<td>${getindex.count}</td>

								
								<td>${view.schemeCode}</td>

								
								<td>${view.schemeName}</td>
								

								<td class="text-center">
									<button type="button" class="btn btn-blue-2 btn-sm"
										onClick="viewSMH(${view.id},${view.orgId})"
										title="View Pension Scheme Master">
										<i class="fa fa-eye"></i>
									</button>
									<button type="button" class="btn btn-success btn-sm"
										onClick="editSMH(${view.id},${view.orgId})"
										title="Edit Pension Scheme Master">
										<i class="fa fa-pencil"></i>
									</button>
								</td>
							</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</form:form>
			</div>
			
						<!-- END of section for search functional code-->
			
			

		</div>
	</div>
</div>