<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/circularNotificationForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.circular.notify.summary"
					text="Circular Notification Summary" />
			</h2>
			<apptags:helpDoc url="CircularNotificationForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="CircularNotificationSummaryForm" action="CircularNotificationForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>



				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.circular.title" text="Circular Title" /></label>
					<div class="col-sm-4">
						<form:input path="dto.circularTitle" id="circularTitle"
							class="form-control alphaNumeric" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.circular.number" text="Circular No." /></label>
					<div class="col-sm-4">
						<form:input path="dto.circularNo" id="circularNo"
							class="form-control alphaNumeric" />
					</div>

				</div>


				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm(this)">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-blue-2"" title="Add"
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add"></spring:message>
					</button>
					
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='CircularNotificationForm.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" id="back"  title="Back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="sfac.button.back" text="Back"></spring:message>
					</button>

				</div>


	
				<div class="table-responsive">
					<table class="table table-bordered table-striped"
						id="fpoMastertables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.srno" text="Sr No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.circular.title" text="Circular Title" />		
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.circular.doc" text="Date Of Circular" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.circular.converner.name" text="Convener Name" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.status" text="Status" />				
								
								<th scope="col" width="15%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${command.circularNotificationMasterDtos}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.circularTitle}</td>
										<td class="text-center">${dto.dateOfCircular}</td>
										<td class="text-center">${dto.convenerName}</td>
										<td class="text-center">${dto.status}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"	onclick="modifyCase(${dto.cnId},'CircularNotificationForm.html','EDIT','V')">
												<i class="fa fa-eye"></i>
											</button> 
										
										</td>
									</tr>
								</c:forEach>
						</tbody>
					</table>
				</div> 


			</form:form>
		</div>
	</div>
</div>
