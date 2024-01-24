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
					<spring:message code="bin.def.heading" text="Bin Definition Master" />
				</h2>
				<apptags:helpDoc url="BinDefMaster.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">

					<form:form id="defSummaryFrm" name="defSummaryFrm"
						class="form-horizontal" action="BinDefMaster.html" method="post">

						<div class="text-center clear padding-10">
							<button class="btn btn-success add" id="AddBinDefintion"
								type="button">
								<i class="fa fa-plus-circle"></i>
								<spring:message code="material.management.add" text="Add" />
							</button>
						</div>

						<div class="table-responsive clear">
							<table class="table table-striped table-bordered"
								id="searchBinDef">
								<thead>
									<tr>
										<td width="3%" align="center"><spring:message
												code="store.master.srno" text="Sr.No." /></td>
										<td align="center"><spring:message
							             code="binDefMaster.defName" text="Definition Name"></spring:message></td>
										<td align="center"><spring:message
							             code="binDefMasDto.priority" text="Priority"></spring:message></td>
										<td align="center"><spring:message
							             code="material.item.master.description" text="Description"></spring:message></td>
										<td align="center"><spring:message code="material.management.action"
												text="Action" /></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.defList}" var="summaryDTO"
										varStatus="index">
										<tr>
											<td class="text-center">${index.count}</td>
											<td align="center">${summaryDTO.defName}</td>
											<td align="center">${summaryDTO.priority}</td>
											<td align="center">${summaryDTO.description}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													onClick="editViewFormBin(${summaryDTO.binDefId},'V')"
													title="<spring:message code="material.management.view" text="View"></spring:message>">
													<i class="fa fa-eye"></i>
												</button>
												<button type="button" class="btn btn-warning btn-sm btn-sm"
													onClick="editViewFormBin(${summaryDTO.binDefId},'E')"
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
