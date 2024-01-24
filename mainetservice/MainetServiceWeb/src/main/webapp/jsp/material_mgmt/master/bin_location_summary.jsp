<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/material_mgmt/master/binMas.js" type="text/javascript"></script>
<div id="searchAssetPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="binLocationMaster.summary" text="Bin Location Master Summary" />
				</h2>
				<apptags:helpDoc url="BinLocationMas.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">

					<form:form id="locSummaryFrm" name="locSummaryFrm"
						class="form-horizontal" action="BinLocationMas.html" method="post">


						<div class="text-center clear padding-10">
							<button class="btn btn-success add" id="AddBinLocation"
								type="button">
								<i class="fa fa-plus-circle"></i>
								<spring:message code="material.management.add" text="Add" />
							</button>
						</div>

						<div class="table-responsive clear">
							<table class="table table-striped table-bordered"
								id="searchBinLoc">
								<thead>
									<tr>
										<td width="3%" align="center"><spring:message
												code="store.master.srno" text="Sr.No." /></td>
										<td align="center"><spring:message
												code="store.master.name" text="Store Name"></spring:message></td>
															<td align="center"><spring:message
												code="store.master.locationStore" text="Store Location"></spring:message></td>
															<td align="center"><spring:message
												code="store.master.address" text="Store Address"></spring:message></td>
															<td align="center"><spring:message
												code="binLocMasDto.binLocation" text="Bin Location"></spring:message></td>		
										<td align="center"><spring:message code="material.management.action"
												text="Action" /></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.locList}" var="summaryDTO"
										varStatus="index">
										<tr>
											<td class="text-center">${index.count}</td>
											<td align="center">${summaryDTO.storeName}</td>
											<td align="center">${summaryDTO.storeLocation}</td>
											<td align="center">${summaryDTO.storeAdd}</td>
											<td align="center">${summaryDTO.binLocation}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													onClick="editViewFormBinLoc(${summaryDTO.binLocId},'V')"
													title="<spring:message code="material.management.view" text="View"></spring:message>">
													<i class="fa fa-eye"></i>
												</button>
												<button type="button" class="btn btn-warning btn-sm btn-sm"
													onClick="editViewFormBinLoc(${summaryDTO.binLocId},'E')"
													title="<spring:message code="material.management.edit" text="Edit"></spring:message>">
													<i class="fa fa-pencil" aria-hidden="true"></i>
												</button>												
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
