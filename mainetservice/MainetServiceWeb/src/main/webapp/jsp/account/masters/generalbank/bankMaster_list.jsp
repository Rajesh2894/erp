<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/account/generalbank/bankMaster.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/generalbank/bankMaster.js"></script>

<script>
$(document).ready(function(){
 	var val = $('#keyTest').val();
	if (val != '' && val != undefined) {
		displayMessageOnSubmit(val);
	}
});	
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="bank.master.title" />
			</h2>
			<apptags:helpDoc url="GeneralBankMaster.html" helpDocRefURL="GeneralBankMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbBankMaster"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<input type="hidden" value="${isDefault}" id="isDefault" />
				<input type="hidden" value="${envFlag}" id="envFlag" />
				
				<div class="form-group">
					<label class="control-label col-sm-2" for="bank"><spring:message
							code="account.bankmaster.bankname" /></label>
					<div class="col-sm-4">
						<form:select id="bank" path="bank"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="master.selectDropDwn" />
							</form:option>
							<c:forEach items="${listBank}" var="list">
								<form:option value="${list.lookUpDesc}">${list.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2" for="branch"><spring:message
							code="account.bankmaster.bankbranch" /></label>
					<div class="col-sm-4">
						<form:select id="branch" path="branch"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="master.selectDropDwn" />
							</form:option>
							<c:forEach items="${listBranch}" var="list">
								<form:option value="${list.lookUpDesc}">${list.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="ifsc"><spring:message
							code="account.bankmaster.ifsccode" /></label>
					<div class="col-sm-4">
						<form:select id="ifsc" path="ifsc"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="master.selectDropDwn" />
							</form:option>
							<c:forEach items="${listIfsc}" var="list">
								<form:option value="${list.lookUpDesc}">${list.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2" for="micr"><spring:message
							code="account.bankmaster.micrcode" /></label>
					<div class="col-sm-4">
						<form:select id="micr" path="micr"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="master.selectDropDwn" />
							</form:option>
							<c:forEach items="${listMicr}" var="list">
								<form:option value="${list.lookUpDesc}">${list.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="city"><spring:message
							code="account.bankmaster.city" /></label>
					<div class="col-sm-4">
						<form:select id="city" path="city"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="master.selectDropDwn" />
							</form:option>
							<c:forEach items="${listCity}" var="list">
								<form:option value="${list.lookUpDesc}">${list.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-top-10 clear">
					<button type="button" id="search" class="btn btn-blue-2 searchData"
						onclick="searchBankMasterData()">
						<i class="fa fa-search" aria-hidden="true"></i>
						<spring:message code='search.data' />
					</button>
					<button type="reset" id="reset" class="btn btn-warning"
						onclick="bankreset()">
						<spring:message code="reset.msg" />
					</button>
					<button type="button" class="btn btn-success createData"
						id="createData">
						<i class="fa fa-plus-circle" aria-hidden="true"></i>
						<spring:message code='master.addButton' />
					</button>
					<button type="button" class="btn btn-primary" onclick="exportTemplate();">
						<spring:message code="master.expImp" text="Export/Import" />
					</button>
				</div>
				<div class="clear padding-top-10">
					<table id="grid"></table>
					<div id="pagered"></div>
				</div>
			</form:form>
		</div>
	</div>
</div>

