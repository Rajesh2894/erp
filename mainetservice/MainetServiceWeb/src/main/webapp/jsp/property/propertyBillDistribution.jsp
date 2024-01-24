<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/property/propertyBillDistribution.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="property.biil.distribution.heading" />
			</h2>

			<apptags:helpDoc url="PropertyBillDistribution.html"></apptags:helpDoc>

		</div>
		<div class="widget-content padding">
			<form:form action="PropertyBillDistribution.html"
				class="form-horizontal" name="PropertyBillDistribution"
				id="PropertyBillDistribution">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
				<div class="form-group">

					<label class="col-sm-2 control-label"> <spring:message
							code="" text="Selection Criteria" /> <span class="mand">*</span>
					</label>
					<div class="col-sm-4">
					<div id="searchData col-sm-4">
						<label for="SinMul" class="radio-inline "> <form:radiobutton
								path="specialNotGenSearchDto.specNotSearchType" value="S"
								class="SinMul" onchange="showSingleMultiple()" /> <spring:message
								code="property.Single" /></label> <label for="SinMul"
							class="radio-inline "> <form:radiobutton
								path="specialNotGenSearchDto.specNotSearchType" value="M"
								class="SinMul" onchange="showSingleMultiple()" /> <spring:message
								code="property.multiple" /></label>

					</div>
					</div>
					<label class="col-sm-2 control-label"> <spring:message
							code="" text="Action"/> <span class="mand">*</span>
					</label>
					<div class="col-sm-4">
					<div id="searchData col-sm-4">
						<label for="SinMul" class="radio-inline "> <form:radiobutton
								path="specialNotGenSearchDto.action" value="A"
								class="SinMul" onchange="showSingleMultiple()" /> <spring:message
								code="" text="Add"/></label> <label for="SinMul"
							class="radio-inline "> <form:radiobutton
								path="specialNotGenSearchDto.action" value="E"
								class="SinMul" onchange="showSingleMultiple()" /> <spring:message
								code="" text="Edit"/></label>

					</div>
					</div>
				</div>

				<div class="form-group PropDetail ">

					<apptags:input labelCode="propertydetails.PropertyNo."
						path="specialNotGenSearchDto.propertyNo" onBlur="getFlatNo()"></apptags:input>

					<apptags:textArea labelCode="Old Property No"
						path="specialNotGenSearchDto.oldPropertyNo" isMandatory="true"
						cssClass="mandColorClass"></apptags:textArea>

				</div>

				<div class="form-group wardZone">

					<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
						showOnlyLabel="false" pathPrefix="specialNotGenSearchDto.assWard"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control " showAll="false"
						showData="true" />
				</div>

				<div class="form-group" id="showFlatNo">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Flat No" /></label>
					<div class="col-sm-4">
						<form:select path="specialNotGenSearchDto.flatNo" id="flatNo"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="">
								<spring:message code='adh.select' />
							</form:option>
							<c:forEach items="${command.flatNoList}" var="flatNoList">
								<form:option value="${flatNoList}">${flatNoList}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group searchBtn">
					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2 " id="serchBtn"
							onclick="serachProperty()">
							<i class="fa fa-search"></i>
							<spring:message code="property.changeInAss.Search" />
						</button>
						<button type="button" class="btn btn-warning " id="resetBtn"
							onclick="resetFormDetails(this)">
							<spring:message code="property.reset" text="Reset" />
						</button>

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.commonBillDistriDate"
							text="Common Bill distribution date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="specialNotGenSearchDto.multiBillDistrDate"
								id="multiBillDistrDate" onchange="setBillDistriDate()"
								class="form-control mandColorClass billDistribDate1 Moredatepicker cal" />
						</div>
					</div>
				</div>

				<div class="table-responsive clear" id="PropDetails">
				<c:set var="d" value="0" scope="page" />
					<table id="datatables" class="table table-striped table-bordered">
						<thead>
							<tr>

								<th width="10%"><label class="checkbox-inline"><input
										type="checkbox" id="selectall" />Select All</label></th>
								<th width="15%"><spring:message code="property.PropertyNo" /></th>
								<th width="10%"><spring:message code="" text="Flat No" /></th>
								<th width="20%"><spring:message code="property.OwnerName" /></th>							
								<th width="10%"><spring:message code="" text="Bill No" /></th>
								<th width="15%"><spring:message code="" text="Bill Generated Date" /></th>
								<th width="10%"><spring:message code="" text="Bill Year" /></th>
								<th width="40%"><spring:message code="" text="Bill Distribution Date" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="propList"
								items="${command.getNotGenSearchDtoList()}" varStatus="status">

								<tr>

									<td align="center"><label class="checkbox margin-left-20"><form:checkbox
												path="notGenSearchDtoList[${status.index}].genNotCheck"
												value="Y" cssClass="checkall" id="genNotCheck" /></label></td>
									<td class="text-center">${propList.propertyNo}</td>
									<td class="text-center">${propList.flatNo}</td>
									<td align="left">${propList.ownerName}</td>								
									<td class="text-center">${propList.billNo}</td>
									<td class="text-center">${propList.billDate}</td>
									<td class="text-center">${propList.finYear}</td>
									<td class="text-center"><form:input path="notGenSearchDtoList[${d}].billDistribDate" cssClass="billDistribDate1 Moredatepicker cal form-control" 
							
							id="billDistribDate${d}" /></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>

				</div>
				
				 <div class="text-center padding-top-10">
          <button type="button" class="btn btn-success btn-submit"  onclick="updateDueDate(this)"><spring:message code="propertyBill.Submit"/></button>
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="propertyBill.Back" text="Back"></spring:message>
					</button>
				</div>
         

	
	</form:form>
	</div>
</div>
</div>

