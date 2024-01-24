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
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/masters/vendorMaster/vendorMaster.js" type="text/javascript"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.vendor.master" text="Vendor Master" />
			</h2>
			<apptags:helpDoc url="Vendormaster.html" helpDocRefURL="Vendormaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding" >
			<div>
			<form:form action="Vendormaster.html" modelAttribute="tbAcVendormaster"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">

					<label class="control-label col-sm-2"> <spring:message
							code="accounts.vendormaster.vendortype" text="Vendor Type"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="cpdVendortype"
							class="form-control chosen-select-no-results" id="cpdVendortype"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="master.selectDropDwn" text="Select" />
							</form:option>
							<c:forEach items="${venderType}" varStatus="status"
								var="venderType">
								<c:if test="${userSession.languageId eq 1}">
								<form:option code="${venderType.lookUpCode}"
									value="${venderType.lookUpId}">${venderType.descLangFirst}</form:option>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
								<form:option code="${venderType.lookUpCode}"
									value="${venderType.lookUpId}">${venderType.descLangSecond}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2"> <spring:message
							code="accounts.vendormaster.vendorSubType" text="Sub-Type"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="cpdVendorSubType"
							class="form-control chosen-select-no-results"
							id="cpdVendorSubType" disabled="${viewMode}">
							<form:option value="">
								<spring:message code="master.selectDropDwn" text="Select" />
							</form:option>
							<c:forEach items="${vendorStatus}" varStatus="status"
								var="vendorStatus">
									<c:if test="${userSession.languageId eq 1}">
										<form:option code="${vendorStatus.lookUpCode}"
											value="${vendorStatus.lookUpId}">${vendorStatus.descLangFirst}</form:option>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
										<form:option code="${vendorStatus.lookUpCode}"
											value="${vendorStatus.lookUpId}">${vendorStatus.descLangSecond}</form:option>
									</c:if>
								</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2"> <spring:message
							code="accounts.Secondaryhead.Vendor" text="Vendor"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="vmVendorcode"
							class="form-control chosen-select-no-results"
							id="vendor_vmvendorcode" disabled="${viewMode}">
							<form:option value="">
								<spring:message code="master.selectDropDwn" text="Select" />
							</form:option>
							<c:forEach items="${list}" varStatus="status" var="list">
								<form:option code="${list.venderCodeAndName}"
									value="${list.vmVendorcode}">${list.venderCodeAndName}</form:option>
							</c:forEach>
						</form:select>
					</div>



					<label class="control-label col-sm-2"> <spring:message
							code="accounts.vendormaster.vendorStatus" text="Status"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="vmCpdStatus" class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="master.selectDropDwn" text="Select" />
							</form:option>
							<c:forEach items="${vendorStatusAcIn}" var="lookup">
								<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc} </form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 searchData"
						onclick="searchVendorData()">
						<i class="fa fa-search"></i>
						<spring:message code="master.search" text="Search" />
					</button>

					<input type="button" class="btn btn-warning"
						onclick="javascript:openRelatedForm('Vendormaster.html');"
						value="<spring:message code="reset.msg" text="Reset"/>" id="cancelEdit" />
					<button type="button" class="btn btn-success createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="add.msg" text="Add" />
					</button>
					<button type="button" class="btn btn-primary" onclick="exportTemplate();">
						<spring:message code="master.expImp" text="Export/Import" />
					</button>
					<button onclick="printReport(this);"
							class="btn btn-primary hidden-print" type="button">
							<i class="fa fa-print"></i> <spring:message code="account.budget.code.print" text="Print" />
					</button>
				</div>
				<div class="text-right padding-bottom-10"></div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
			
		</div>
	</div>
</div>

