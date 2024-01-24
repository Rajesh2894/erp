<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/material_mgmt/master/itemMaster.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.item.master.title" text="Item Master"></spring:message>
			</h2>
			<apptags:helpDoc url="ItemMaster.html" helpDocRefURL="ItemMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbMGItemMaster"
				class="form-horizontal">

				<form:hidden path="itemId" id="Itemid" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="itemCategory"><spring:message
						code="material.item.master.itemCategory" text="Item Category"/></label>
					<div class="col-sm-4">
						<form:select path="category" class="form-control mandColorClass"
							id="category" disabled="${viewMode}" data-rule-required="false">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${itemMasterCategoryMap}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label" for="itemType"><spring:message
							code="material.item.master.type" text="Item Type"/></label>
					<div class="col-sm-4">
						<form:select path="type" class="form-control" id="type">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${itemTypeMap}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="itemGroup"><spring:message
							code="material.item.master.group" text="Item Group" /></label>
					<div class="col-sm-4">
						<form:select path="itemGroup" class="form-control mandColorClass"
							id="itemgroup" disabled="${viewMode}" data-rule-required="false">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${itemMasterGroupMap}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="itemSubGroup"><spring:message
							code="material.item.master.subgroup" text="Item Subgroup" /></label>
					<div class="col-sm-4">
						<form:select path="itemSubGroup" class="form-control"
							id="itemsubgroup">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${itemMasterSubGroupMap}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="name"><spring:message
							code="material.item.master.name" text="Item Name" /></label>
					<div class="col-sm-4">
						<form:select id="name" path="name"
							cssClass="form-control chosen-select-no-results"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${list}" varStatus="status" var="accountHeads">
								<form:option value="${accountHeads.name}"
									code="${accountHeads.name}">${accountHeads.name}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchItemMasterData()">
						<i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL" value="ItemMaster.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}">
						<i class="fa fa-refresh"></i>
						<spring:message code="material.management.reset" text="Reset" /></a>
					<button type="button" class="btn btn-success createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="material.management.add" text="Add" />
					</button>
					<button type="button" class="btn btn-primary add" onclick="exportTemplate();">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="material.item.master.listExportImport" text="Export/Import" />
					</button>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>

