<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- <script type="text/javascript" src="js/asset/assetFunctionalLocSearch.js"></script>
 --><script type="text/javascript" src="js/asset/searchFuncLocCode.js"></script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="asset.functional.location.label.header.search" text="" />
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
			</div>
			
			
			<!-- start of section for search functional code-->
			<div class="widget-content padding">
				<form:form action="AssetFunctionalLocation.html"
					class="form-horizontal" name="functionLocationMaster"
					id="functionLocationMaster">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div class="form-group">
							<apptags:input labelCode="asset.functional.location.label.code"
								path="assetfucnLocDTO.funcLocationCode" cssClass="" isMandatory="false" ></apptags:input>

							<apptags:input labelCode="asset.functional.location.label.description"
								path="assetfucnLocDTO.description" 
							cssClass="" isMandatory="false"></apptags:input>
							<%-- <form:input	path="" id="funcLocationCode" name="funcLocationCode"
							class="form-control" maxlength="200" />
							<form:input	path="" id="description" name="description"
							class="form-control" maxlength="200" /> --%>
							
					</div>

					<div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-success"
							id="searchFLC">
							<spring:message code="search.data" text="Search" />
						</button>
						<button type="Reset" class="btn btn-warning" onclick="resetCDM()">
							<spring:message code="reset.msg" text="Reset" />
						</button>
						<button type="button" class="btn btn-blue-2" id="createFunLocCode">
							<spring:message code="add.msg" text="Add" />
						</button>
					</div>

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered" id="dtFLCHome">
							<thead>
								<tr>

									<td width="30%" align="center"><spring:message 
											code="asset.functional.location.label.code" text="" /></td>
									<td width="15%" align="center"><spring:message 
											code="asset.functional.location.label.description" text="" /></td>
									<td width="15%" align="center"><spring:message 
											code="asset.functional.location.label.parentid" text="" /></td>
									<td width="15%" align="center"><spring:message 
											code="asset.functional.location.label.unit" text="" /></td>
									<td width="15%" align="center"><spring:message 
											code="sor.action" text="Action" /></td>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${funcLocDTOList_Search}" var="funcLocDTO"	varStatus="count">							<tr>
							
								<td>${funcLocDTO.funcLocationCode}</td>

								
								<td>${funcLocDTO.description}</td>

								
								<td>${funcLocDTO.parentCode}</td>

								
								<td>${funcLocDTO.unitDesc}</td>

								<td class="text-center">
									<button type="button" class="btn btn-blue-2 btn-sm"
										onClick="viewFLC(${funcLocDTO.funcLocationId})"
										title="View Functional Location Code">
										<i class="fa fa-eye"></i>
									</button>
									<button type="button" class="btn btn-success btn-sm"
										onClick="editFLC(${funcLocDTO.funcLocationId})"
										title="Edit Functional Location Code">
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