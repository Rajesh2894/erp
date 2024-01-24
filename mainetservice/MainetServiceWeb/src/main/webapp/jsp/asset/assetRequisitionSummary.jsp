<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/asset/assetRequisition.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="${userSession.moduleDeptCode == 'AST' ? 'asset.requisition.summary':'asset.ITrequisition.summary'}" text="Asset Requisition Summary" />
			</h2>
			<apptags:helpDoc url="AssetRequisition.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="" cssClass="form-horizontal" id="" name="">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<input type="hidden" id="moduleDeptUrl"  value="${userSession.moduleDeptCode == 'AST' ? 'AssetRequisition.html':'ITAssetRequisition.html'}">

				<div class="form-group">

					<c:set var="baseLookupCodeACL"
						value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
					<label class="col-sm-2 control-label" for="assetgroup"> <spring:message
							code="asset.requisition.category" />
					</label>
					<apptags:lookupField path="astRequisitionDTO.astCategory"
						items="${command.getLevelData(baseLookupCodeACL)}"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="Select" />

					<label class="col-sm-2 control-label" for="assetgroup"> <spring:message
							code="asset.requisition.location" /></label>
					<div class="col-sm-4">
						<form:select path="astRequisitionDTO.astLoc" id="astLoc"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="asset.info.select" />
							</form:option>
							<c:forEach items="${command.locList}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>


				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label"
						for="assetgroup"> <spring:message
							code="asset.requisition.department" /></label>

					<div class="col-sm-4">
						<form:select path="astRequisitionDTO.astDept" id="astDept"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="asset.info.select" />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="obj">
								<form:option value="${obj.dpDeptid}" code="${obj.dpDeptcode}">${obj.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title='<spring:message code="asset.search" text="Search" />'
						id="searchRequisition">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="asset.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='${userSession.moduleDeptCode == 'AST' ? 'AssetRequisition.html':'ITAssetRequisition.html'}'"
						class="btn btn-warning" title='<spring:message code="asset.requisition.reset" text="Reset" />'>
						<spring:message code="asset.requisition.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary"
						onclick="openRequisitionForm('${userSession.moduleDeptCode == 'AST' ? 'AssetRequisition.html':'ITAssetRequisition.html'}','addRequisition');"
						title='<spring:message code="asset.add" text="Add" />'>
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="asset.add" text="Add" />
					</button>

				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="requisitionSummaryDT">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message code="asset.requistion.sr.no"
										text="Sr.No" /></th>
								<th class="text-center"><spring:message code="asset.requistion.category"
										text="Category" /></th>
								<th class="text-center"><spring:message code="asset.requistion.asset.name"
										text="Asset Name" /></th>
								<th class="text-center"><spring:message code="asset.requistion.description"
										text="Description" /></th>
								<th class="text-center"><spring:message code="asset.requistion.quantity"
										text="Quantity" /></th>
								<th class="text-center"><spring:message code="asset.requistion.location"
										text="Location" /></th>
								<th class="text-center"><spring:message code="asset.requistion.department"
										text="Department" /></th>
								<th class="text-center"><spring:message code="asset.requistion.status"
										text="Status" /></th>
							</tr>
						</thead>
						<tbody>
						
							<c:forEach items="${command.requisitionList}" var="requi"
								varStatus="status">
								
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${requi.astCategoryDesc}</td>
									<td class="text-center">${requi.astName}</td>
									<td class="text-center">${requi.astDesc}</td>
									<td class="text-center">${requi.astQty}</td>
									<td class="text-center">${requi.astLocDesc}</td>
									 <td class="text-center">${requi.astDeptDesc}</td> 
								<td class="text-center"><c:if test="${requi.status eq 'SUBMITTED'}">
									<spring:message code="asset.submitted"
										 text="SUBMITTED"/>
									
									</c:if>
									<c:if test="${requi.status eq 'APPROVED'}">
									<spring:message code="asset.approved"
										 text="APPROVED"/>
									
									</c:if>
									<c:if test="${requi.status eq 'IN-PROCESS'}">
									<spring:message code="asset.inprocess"
										 text="IN-PROCESS"/>
									
									</c:if> </td>
									<%--  <td class="text-center">${requi.status}</td>  --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->

