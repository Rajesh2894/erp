<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/fire_management/callRegister.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<style>
table#fireCallRegister tbody tr td:last-child {
	text-align: center;
}
</style>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class=".content-page">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="FireCallRegisterDTO.form.closer" text="Call Closure" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>
			<!-- End mand-label -->

			<form:form action="FireCallClosure.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmFireCallRegister" id="frmFireCallRegister">
				<%-- <form:hidden path="saveMode" id="saveMode" /> --%>

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="FireCallRegisterDTO.form.closer"
										text="Call Closure" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="control-label col-sm-2"
										for="cpdFireStation"> <spring:message
											code="FireCallRegisterDTO.cpdFireStation" text="Fire Station" /></label>
									<c:set var="baseLookupCode" value="FSN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path=""
										cssClass="mandColorClass form-control fireStation" isMandatory="false"
										hasId="true" selectOptionLabelCode="selectdropdown" />
									<apptags:input labelCode="FireCallRegisterDTO.cmplntNo"
										path="" cssClass="complainNo"
										isMandatory="false" maxlegnth="50" />
								</div>
							</div>
						</div>
						
						<div class="text-center clear padding-10">
					
					<button type="button" class="btn btn-blue-2 search"
						title='<spring:message code="bt.search" text="Search"/>'
						onclick="search();return false;">
						<i class="fa fa-search"></i>
						<spring:message code="bt.search" text="Search"/>
					</button>

					<button type="Reset" class="btn btn-warning"
						title='<spring:message code="bt.clear" text="Reset" />'
						onclick="window.location.href='FireCallClosure.html'">
						<spring:message code="bt.clear" text="Reset"></spring:message>
					</button>
					
					<input type="button"
						title='<spring:message code="bckBtn" text="Back" />'
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value='<spring:message code="bckBtn" text="Back" />'>
						</div>


					<div class="table-responsive">
						<table class="table table-striped table-condensed table-bordered"
							id="fireCallRegister">
							<thead>
								<tr>
									<th><spring:message code="FireCallRegisterDTO.srno" text="Sr. No." /></th>
									<th><spring:message code="FireCallRegisterDTO.cmplntNo" text="Call No." /></th>
									<th><spring:message code="FireCallRegisterDTO.cpdFireStation" text="Fire Station" /></th>
									<th><spring:message code="dashboard.actions.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
							
							<c:if test="${callList ne null && callList ne ''}">
							<c:forEach items="${callList}" var="item" varStatus="loop">
							
								<tr>
									<td>${loop.count}</td>
									<td>${item.cmplntNo}</td>
									<td>${item.fsDesc}</td>
															

									<td>
									
									
									  <%--   <c:if test="${item.complaintStatus eq 'A' || item.complaintStatus eq 'I'}"> --%>
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message  code="FireCallRegisterDTO.viewCall" text="View Call"/>"
												onclick="editCall(${item.cmplntId},'FireCallClosure.html','EDIT','V')">
												<i class="fa fa-eye"></i>
											</button>
									<%-- 	</c:if> --%>
											<c:if test="${item.complaintStatus eq 'O' || item.complaintStatus eq 'SB'}">
												<button type="button" class="btn btn-warning btn-sm"
													title="<spring:message  code="FireCallRegisterDTO.editCall" text="Edit Call"/>" 
													onclick="editCall(${item.cmplntId},'FireCallClosure.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
												</button>
											</c:if>
									</td>
								</tr>
								
								</c:forEach>
								</c:if>
								
								<c:if test="${callCloserList ne null && callCloserList ne ''}">
								<c:forEach items="${callCloserList}" var="item" varStatus="loop">
							
									<tr>
										<td>${loop.count}</td>
										<td>${item.cmplntNo}</td>
										<td>${item.fsDesc}</td>
										<%-- <td align="center">
										<spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.fsDesc)"
												var="lookup" />${lookup.lookUpDesc }
										</td>	 --%>						
										<td>
										         <c:if test="${item.complaintStatus eq 'A' || item.complaintStatus eq 'I'}">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message  code="FireCallRegisterDTO.viewCall" text="View Call"/>"
													onclick="editCall(${item.cmplntId},'FireCallClosure.html','EDIT','V')">
													<i class="fa fa-eye"></i>
												</button>
												</c:if>
											
												<c:if test="${item.complaintStatus eq 'O' || item.complaintStatus eq 'SB'}">
													<button type="button" class="btn btn-warning btn-sm"
														title="<spring:message  code="FireCallRegisterDTO.editCall" text="Edit Call"/>" 
														onclick="editCall(${item.cmplntId},'FireCallClosure.html','EDIT','E')">
														<i class="fa fa-pencil"></i>
													</button>
												</c:if>
										</td>
									</tr>	
								</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->
