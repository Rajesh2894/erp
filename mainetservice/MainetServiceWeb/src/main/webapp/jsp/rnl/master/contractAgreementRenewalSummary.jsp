<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css"
	rel="stylesheet" type="text/css">

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/contract/contractAgreement.js"></script>
<!-- <script src="js/masters/contract/contractAgreementSummary.js"></script> -->
<script type="text/javascript" src="js/rnl/master/contractRenewal.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script>
$( document ).ready(function() {

	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		maxDate: '-0d',
		changeYear: true,
	});
	
	});	
</script>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="Contract.Renewal.Summary"
					text="Contract Renewal Summary"></spring:message>
			</h2>
			<apptags:helpDoc url="ContractAgreementRenewal.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="rnl.book.field" text="Field with"></spring:message>
					<strong class="text-red-1">*</strong> <spring:message
						code="master.estate.field.mandatory.message" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="ContractAgreementRenewal.html"
				class="form-horizontal form" name="ContractAgreementRenewalSummary"
				id="ContractAgreementRenewal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="error-div warning-div error-div alert alert-danger alert-dismissible"></div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="ContractNo"><spring:message
							code="contract.label.contract.number" text="Contract No."></spring:message></label>
					<div class="col-sm-4">
						<form:input path="" type="text" class="form-control"
							id="contractNo" />
					</div>
					<label class="control-label col-sm-2" for="ContractDate"><spring:message
							code="Contract.Date" text="Contract Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" type="text" class="form-control datepicker"
								id="contractDate" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code="rnl.department" text="Department" /></label>
					<div class="col-sm-4 ">
						<form:select path="" id="departmentId"
							cssClass="form-control chosen-select-no-results"
							class="form-control" data-rule-required="true">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.departmentList}" var="lookUp">
								<form:option value="${lookUp.dpDeptid}">${lookUp.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2"><spring:message
							code="Vendor.Name" text="Vendor Name" /></label>
					<div class="col-sm-4 ">
						<form:select path="" id="vendorId"
							cssClass="form-control chosen-select-no-results"
							class="form-control" data-rule-required="true">
							<form:option value="">Select</form:option>
							<c:forEach items="${vendorList}" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
		</div>
		<div class="text-center padding-bottom-10">
			<button type="button" id="btnsearch"
				class="btn btn-blue-2 searchData" onclick="searchContractRenewal();">
				<strong class="fa fa-search"></strong>
				<spring:message code="search.data" text="Search"></spring:message>
			</button>
			<button class="btn btn-warning  reset" type="reset"
				onclick="window.location.href='ContractAgreementRenewal.html'">
				<i class="button-input"></i>
				<spring:message code="reset.msg" />
			</button>
		</div>

		<div class="table-responsive clear">
			<table class="table table-striped table-bordered"
				id="renewalSummaryDT">
				<thead>
					<tr>
						<%-- <th scope="col" width="10%" align="center"><spring:message
										code="" text="Cont ID" /></th> --%>
						<th scope="col" width="10%" align="center"><spring:message
								code="contract.No" text="Contract No." /></th>
						<th scope="col" width="15%" align="center"><spring:message
								code="contract.label.department" text="Department" /></th>
						<th scope="col" width="10%" align="center"><spring:message
								code="represented.By" text="Represented By" /></th>
						<th scope="col" width="10%" align="center"><spring:message
								code="vendor.Name" text="Vendor Name" /></th>
						<th scope="col" width="8%" align="center"><spring:message
								code="contract.label.contractFromDate" text="Contract From Date" /></th>
						<th scope="col" width="8%" class="text-center"><spring:message
								code="contract.label.contractToDate" text="Contract To Date" /></th>
						<th scope="col" width="10%" class="text-center"><spring:message
								code="estate.grid.column.action" text="Action" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${command.summaryDTOList}" var="mstDto">
						<tr>
							<%-- <td hidden>${mstDto.contId}</td> --%>
							<td>${mstDto.contNo}</td>
							<td>${mstDto.contDept}</td>
							<td>${mstDto.contp1Name}</td>
							<td>${mstDto.contp2Name}</td>
							<td>${mstDto.contFromDate}</td>
							<td>${mstDto.contToDate}</td>
							<td class="text-center">
								<button type="button" class="btn btn-blue-2 btn-sm"
									onclick="actionContractRenewal(${mstDto.contId},'V');"
									title="<spring:message code="contract.master.view.contract"></spring:message>">
									<i class="fa fa-eye"></i>
								</button>

								<button type="button" class="btn btn-warning btn-sm"
									onclick="actionContractRenewal(${mstDto.contId},'E');"
									title="<spring:message code="contract.master.edit.contract"></spring:message>">
									<i class="fa fa-pencil"></i>
								</button>
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
