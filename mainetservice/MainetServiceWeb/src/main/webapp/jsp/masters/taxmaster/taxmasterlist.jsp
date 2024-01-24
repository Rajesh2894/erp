<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/masters/taxmaster/taxmaster.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget" id="taxMasDiv">
		<div class="widget-header"><h2><spring:message code='common.tax'/> <spring:message code='common.master'/></h2>
		 <apptags:helpDoc url="TaxMaster.html" helpDocRefURL="TaxMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix"><span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /></span></div>
		<div class="error-div alert alert-danger alert-dismissible" id="errorDivTaxMas"></div>
			<form:form class="form-horizontal" id="taxmaster">
			
				<input type="hidden" value="${selectedTaxDescId}" id="selectedTaxDescId"/>
				<input type="hidden" value="${selectedDeptId}" id="selectedDeptId"/>
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="dpDeptId"><spring:message code='master.department'/></label>
					<div class="col-sm-4">
						<form:select id="dpDeptId" path="dpDeptId" class="form-control chosen-select-no-results"  onchange="getTaxList();">
							<form:option value=""><spring:message code='common.taxmaster.selDept' text="Select Department"/></form:option>
							<c:forEach items="${deptList}" var="deptListData">
								<form:option value="${deptListData.dpDeptid }" >${deptListData.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label" for="taxList"><spring:message code='tax.list' text="tax.list"/></label>
					<div class="col-sm-4" id="tax-div">
						<form:select id="taxList" path="taxId" class="form-control chosen-select-no-results">
							<form:option value=""><spring:message code='tax.selectTax' text="Select Tax"/></form:option>
							<c:forEach items="${txnPrefixData}" var="txnPrefixData">
								<form:option value="${txnPrefixData.lookUpId}">${txnPrefixData.lookUpDesc }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="text-center padding-top-10 clear">
					<button type="button" id="search" class="btn btn-blue-2" onclick="searchTaxMasData()"><i class="fa fa-search" aria-hidden="true"></i> <spring:message code='search.data'/></button>
					<input type="reset" id="reset" value="<spring:message code='reset.msg'/>" class="btn btn-warning" onclick="taxMasterReset();"/>
					<button type="button" class="btn btn-success btn-submit" onclick="openTaxMasAddForm()"><i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code='master.addButton'/></button>
 				</div>

				<div class="clear padding-top-10">
					<table id="grid"></table>
					<div id="pagered"></div> 
 				</div>
			</form:form>
		</div>
	</div>
</div>
