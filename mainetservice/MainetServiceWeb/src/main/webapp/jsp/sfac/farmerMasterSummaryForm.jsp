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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/sfac/farmerMasterForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.frm.formname"
					text="Farmer Master Details" />
			</h2>
			<apptags:helpDoc url="FarmerMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="farmerMasterForm" action="FarmerMasterForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

					<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.farmerName" text="Farmer Name" /></label>
					<div class="col-sm-4">
						<form:select path="farmerMasterDto.frmId" id="frmId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.masterDtoList}" var="dto">
								<form:option value="${dto.frmId}">${dto.frmName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
				 <label class="col-sm-2 control-label"><spring:message
							code="" text="FPO Registration No." /></label>
					<div class="col-sm-4">
						<form:select path="farmerMasterDto.frmFPORegNo" id="frmFPORegNo"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.masterDtoList}" var="dto">
								<form:option value="${dto.frmFPORegNo}">${dto.frmFPORegNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					</div>
				
				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm(this)">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-blue-2"" title="Search"
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add"></spring:message>
					</button>
					
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='FarmerMasterForm.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="sfac.button.back" text="Back"></spring:message>
					</button>

				</div>
				
				<div class="table-responsive">
					<table class="table table-bordered table-striped"
						id="farmerMastertables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.srno" text="Sr No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.farmerName" text="Farmer Name" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.frm.adharN" text="Aadhar No" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.associated.fpo" text="Associated FPO" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${command.farmerMasterDtoList}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.frmName}</td>
										<td class="text-center">${dto.frmAadharNo}</td>
										<td class="text-center">${dto.fpoName}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View" onclick="modifyCase(${dto.frmId},'FarmerMasterForm.html','EDIT','V')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit" onclick="modifyCase(${dto.frmId},'FarmerMasterForm.html','EDIT','E')">
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
	</div>
</div>

