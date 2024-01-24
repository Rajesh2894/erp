<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/arrearEntryDeletion.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.arrear.title" text="Arrear Deletion" />
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="ArrearEntryDeletion.html"
				cssClass="form-horizontal" id="arrearDeletion"
				name="Arrear Entry Deletion">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.arrear.connectionNo" text="Connection Number" /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" path="csmrInfo.csCcn"
							id="csCcn"></form:input>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="water.arrear.billNo" text="Bill No" /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" path="billNo"
							id="billNo"></form:input>
					</div>

				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
								code="common.master.billing.financial" text="Financial Year" />
						</label>
						<div class="col-sm-4">
							<select id="finId" class="form-control" name="">
								<option value=""><spring:message code='master.selectDropDwn'/></option>
								<c:forEach items="${command.finYearData}" var="finYearData">
									<option value="${finYearData.key }">${finYearData.value }</option>
								</c:forEach>
							</select>
						</div>

				</div>

				<div class="form-group">
					<label class="col-sm-10 text-red">Note : If
						you are entering bill no then including that bill no and greater
						than that bill no's will be deleted</label>
				</div>

				<div class="padding-top-10 text-center">
						<button type="button" class="btn btn-blue-2" title="Search"
							id="searchConncection" onclick="searchRecords(this)">
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.search" text="Search" />
						</button>

						<button type="button"
							onclick="window.location.href='ArrearEntryDeletion.html'"
							class="btn btn-warning" title="Reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.reset" text="Reset" />
						</button>
					</div>
			</form:form>
		</div>



	</div>
</div>