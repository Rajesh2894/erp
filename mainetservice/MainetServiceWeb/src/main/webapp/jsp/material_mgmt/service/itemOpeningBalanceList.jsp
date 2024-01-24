<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/material_mgmt/service/itemOpeningBalanceList.js" type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.item.openingBalnace"
					text="Item Opening balance"></spring:message>
			</h2>
			<apptags:helpDoc url="ItemOpeningBalance.html"
				helpDocRefURL="ItemOpeningBalance.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form class="form-horizontal" commandName="command"
				action="ItemOpeningBalance.html" method="POST" name="balSerachFrm"
				id="balSerachFrm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="storeName"><spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="itemOpeningBalanceDto.storeId"
							cssClass="form-control chosen-select-no-results" id="storeIdSearch">
							<form:option value="0">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeObjectList}" var="storeObject">
								<form:option value="${storeObject[0]}" code="${storeObject[2]}">${storeObject[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="name"><spring:message
							code="material.item.master.name" text="Item Name" /></label>
					<div class="col-sm-4">
						<form:select id="itemIdSearch" path="itemOpeningBalanceDto.itemId" cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<form:options items="${command.itemList}" itemLabel="name" itemValue="itemId" />
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchItemOpenBalanceData(this);">
						<i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" onclick="window.location.href='ItemOpeningBalance.html'">
						<i class="fa fa-refresh"></i>
						<spring:message code="material.management.reset" text="Reset"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success"
						onclick="openBalnceForm('ItemOpeningBalance.html','AddForm');" >
						<i class="fa fa-plus-circle"></i>
						<spring:message code="material.management.add" text="Add" />
					</button>
					<button type="button" class="btn btn-primary" onclick="exportTemplate();">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="material.item.master.listExportImport" text="Export/Import" />
					</button>
				</div>

				<div class="table-responsive">
					<table id="id_itemOpeningBalance" summary="Store Master Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="store.master.srno" text="Sr.No." /></th>
								<th><spring:message code="material.item.master.itemcode"
										text="Item Code" /></th>
								<th><spring:message code="material.item.master.name"
										text="Item Name" /></th>
								<th><spring:message code="store.master.name"
										text="Store Name" /></th>
								<th><spring:message code="material.item.openingBalnace"
										text="Item Opening Balance" /></th>

								<th><spring:message code="store.master.status"
										text="Status" /></th>
								<th width="150"><spring:message
										code="material.management.action" text="Action" /></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.openingBalList}" var="data"
								varStatus="loop">
								<tr>
									<td class="text-center"><c:out value="${loop.index + 1}"></c:out></td>
									<td class="text-center">${data.itemCode}</td>
									<td class="text-center">${data.itemName}</td>
									<td class="text-center">${data.storeName}</td>
									<td class="text-center">${data.openingBalance}</td>

									<td class="text-center"><c:choose>
											<c:when test="${data.status}">
												<i class="fa fa-check-circle"></i>
											</c:when>
											<c:otherwise>
												<i class="fa fa-times-circle-o"></i>
											</c:otherwise>
										</c:choose></td>
									<td class="text-center">

										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="editViewFormOpening(${data.openBalId},'V')"
											title="<spring:message code="material.management.view" text="View"></spring:message>">
											<i class="fa fa-eye"></i>
										</button> <c:if test="${data.status}">
											<button type="button" class="btn btn-warning btn-sm btn-sm"
												onClick="editViewFormOpening(${data.openBalId},'E')"
												title="<spring:message code="material.management.edit" text="Edit"></spring:message>">
												<i class="fa fa-pencil" aria-hidden="true"></i>
											</button>
											<button type="button" class="btn btn-danger btn-sm"
												onClick="showConfirmForDeActive(${data.openBalId})"
												title="delete">
												<strong class="fa fa-trash"></strong><span class="hide"><spring:message
														code="material.management.delete" text="Delete"></spring:message></span>
											</button>
										</c:if>
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