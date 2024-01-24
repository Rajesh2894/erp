<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/water/waterDataEntrySearch.js"></script>


<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<div class="content">
	<div class="widget">
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="WaterDataEntrySuite.html"
				class="form-horizontal form" name="WaterDataEntrySearchSuite"
				id="WaterDataEntrySearchSuite">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.dataentry.property.number" text="Property number" /></label>
					<div class="col-sm-4">
						<form:input type="text"
							class="form-control preventSpace alphaNumeric"
							path="searchDTO.propertyNo" id="propertyNo"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.dataentry.connection.number" text="Connection number" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control preventSpace"
							path="searchDTO.csCcn" id="connectionNo" maxlength="20"
							minlength="9"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="firstName"><spring:message
							code="water.dataentry.consumer.name" text="Consumer Name" /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control"
							path="searchDTO.csName" id="csFirstName"
							data-rule-required="true"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="applicantinfo.label.mobile" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="searchDTO.csContactno" id="csMobileNo"
							data-rule-minlength="10" data-rule-maxlength="10"></form:input>
					</div>
				</div>

				<div class="form-group wardZone">
					<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
						showOnlyLabel="false" pathPrefix="searchDTO.codDwzid"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control " showAll="false"
						showData="true" />
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" id="serchBtn"
						onclick="Searchconnection(this)">
						<i class="fa fa-search"></i>
						<spring:message code="property.changeInAss.Search" />
					</button>
					<button type="button" class="btn btn-success btn-submit"
						id="addDataEntry" onclick="addDataEntryDetails();">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="property.DataEntryForm.Add" text="Add" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetConnection()">
						<spring:message code="property.reset" text="Reset" />
					</button>
				</div>
				<!-- Defect #38916 added-->
				<div id="" align="center">
					<table id="grid"></table>
					<div id="pagered"></div>
				</div>

				<!-- Defect #38916 commented-->
				<%-- <apptags:jQgrid id="WaterDataEntrySuite"
					url="WaterDataEntrySuite.html?SEARCH_GRID_RESULTS" mtype="post"
					gridid="gridWaterDataEntrySuite"
					colHeader="water.dataentry.property.number,MeterReadingDTO.csCcn,water.dataentry.consumer.name,water.reconnection.mobileNo,water.plumberLicense.address"
					colModel="[
								{name : 'propertyNo',index : 'propertyNo',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'csCcn',index : 'csCcn',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'csName',index : 'csName',editable : false,sortable : false,search : true, align : 'center'},
								{name : 'csContactno',index : 'csContactno',editable : false,sortable : false,search : false, align : 'center'},
								{name : 'csAdd',index : 'csAdd',editable : false,sortable : false,search : false, align : 'center'}	
								]"
					height="200" caption="" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="true" hasDelete="false"
					loadonce="false" sortCol="rowId" showrow="true" /> --%>




			</form:form>
		</div>
	</div>
</div>