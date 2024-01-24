<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/bankMaster.js"></script>
<script src="js/mainet/script-library.js"></script>
<style>
.child-popup-dialog {
	width: 600px !important;
	height: 300px;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="bank.master.header" text="Bank" />
				<strong><spring:message
						code="account.budget.code.master.brdCum.master" text="Master" /></strong>
			</h2>
		<apptags:helpDoc url="GeneralBankMaster.html" helpDocRefURL="GeneralBankMaster.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">
			<form class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.bankfortds.ptbbankname" text="Bank Name" /></label>
					<div class="col-sm-4">
						<select id="bankName" name="bmBankname"
							class="form-control mandClassColor">
							<option value="0"><spring:message
									code="master.selectDropDwn" /></option>
							<c:forEach items="${list}" var="bank">
								<option value="${bank.bmBankid}">${bank.bmBankname}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<input type="BUTTON" id="search"
						value="<spring:message code="search.data"/>"
						class="btn btn-primary" onclick="searchBankData()" /> <input
						type="button" value="Create Data"
						class="btn btn-success createData">
				</div>
				<div class="text-right padding-bottom-10"></div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form>
		</div>

	</div>
</div>
