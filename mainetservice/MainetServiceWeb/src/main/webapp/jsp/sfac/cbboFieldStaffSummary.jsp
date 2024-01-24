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
<script type="text/javascript" src="js/sfac/cbboFiledStaffForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.cbbo.field.staff.summary"
					text="CBBO Field Staff Details Summary" />
			</h2>
			<apptags:helpDoc url="CBBOFiledStaffDetailForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="milestoneEntrySummaryForm"
				action="CBBOFiledStaffDetailForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>



				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.cbbo.field.staff.expert.name" text="CBBO Expert Name" /></label>
					<div class="col-sm-4">
						<form:input path="dto.cbboExpertName" id="cbboExpertName"
							class="form-control hasCharacter" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.cbbo.field.staff.allocation.blocks"
							text="Allocation Blocks" /></label>
					<div class="col-sm-4">
						<form:select path="dto.sdb3" id="sdb3"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.blockList}" var="dto">
								<form:option value="${dto.lookUpId}">${dto.lookUpDesc}</form:option>
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

					<button type="button" class="btn btn-blue-2" " title="Add"
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='CBBOFiledStaffDetailForm.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" id="back" title="Back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="sfac.button.back" text="Back"></spring:message>
					</button>

				</div>



				<div class="table-responsive">
				<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-bordered table-striped"
						id="fpoMastertables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.srno" text="Sr No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.state" text="State" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.district" text="District" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.cbbo.field.staff.allocation.block"
										text="Allocated Block" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.cbbo.field.staff.expert.name"
										text="CBBO Expert Name" />
								<th scope="col" width="15%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.cbboFiledStaffDetailsDtos}" var="dto"
								varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center"><form:select path="cbboFiledStaffDetailsDtos[${d}].sdb1"
											cssClass="form-control chosen-select-no-results" disabled="true">
											<form:option value="">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.stateList}" var="dto0">
												<form:option value="${dto0.lookUpId}">${dto0.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td class="text-center"><form:select path="cbboFiledStaffDetailsDtos[${d}].sdb2"
											cssClass="form-control chosen-select-no-results" disabled="true">
											<form:option value="">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.districtList}" var="dto1">
												<form:option value="${dto1.lookUpId}">${dto1.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td class="text-center"><form:select path="cbboFiledStaffDetailsDtos[${d}].sdb3"
											cssClass="form-control chosen-select-no-results" disabled="true">
											<form:option value="">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.blockList}" var="dto2">
												<form:option value="${dto2.lookUpId}">${dto2.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td class="text-center">${dto.cbboExpertName}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View"
											onclick="modifyCase(${dto.fsdId},'CBBOFiledStaffDetailForm.html','EDIT','V')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm" title="Edit"
													onclick="modifyCase(${dto.fsdId},'CBBOFiledStaffDetailForm.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
											</button>

									</td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</div>


			</form:form>
		</div>
	</div>
</div>
