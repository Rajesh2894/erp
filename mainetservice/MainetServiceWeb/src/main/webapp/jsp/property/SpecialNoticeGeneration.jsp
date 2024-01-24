
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/property/specialNoticeGeneration.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<!-- End JSP Necessary Tags -->

<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;

}
</style>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong>Special Notice Generation</strong>
			</h2>
		
				<apptags:helpDoc url="SpecialNoticeGeneration.html"></apptags:helpDoc>
		
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /></span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="SpecialNoticeGeneration.html"
				class="form-horizontal form" name="SpecialNoticeGeneration"
				id="SpecialNoticeGeneration">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="accordion-toggle ">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" href="#specialNoticeGeneration"><spring:message
										code="property.specialNoticeGeneration" /></a>
							</h4>
						</div>

						<div id="specialNoticeGeneration" class="panel-collapse collapse in">
							<br>
							<div class="form-group">

								<label class="col-sm-2 control-label"> <spring:message
										code="property.specialNotice" /> <span class="mand">*</span>
								</label>
								<div id="searchData col-sm-4">
									<label for="SinMul" class="radio-inline "> <form:radiobutton
											path="specialNotGenSearchDto.specNotSearchType" value="SM"
											class="SinMul" onchange="showSingleMultiple()" /> <spring:message
											code="property.SingleORMultiple" /></label> 
									<label for="All"
										class="radio-inline "> <form:radiobutton
											path="specialNotGenSearchDto.specNotSearchType" value="AL"
											class="All" onchange="showSingleMultiple()" /> <spring:message
											code="property.All" /></label>

								</div>
							</div>

							<div class="form-group PropDetail ">

								<apptags:textArea labelCode="Property No"
									path="specialNotGenSearchDto.propertyNo" isMandatory="true"
									cssClass="mandColorClass hasPropNo"></apptags:textArea>
								<apptags:textArea labelCode="Old Property No"
									path="specialNotGenSearchDto.oldPropertyNo" isMandatory="true"
									cssClass="mandColorClass"></apptags:textArea>

							</div>


							<div class="sectionSeperator padding-top-15">
								<div class="form-group wardZone">

									<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
										showOnlyLabel="false"
										pathPrefix="specialNotGenSearchDto.assWard" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										showData="true" />
								</div>

								<div class="col-sm-12 margin-bottom-5">	<strong><p class="text-center text-small text-red-1 ">
											<spring:message code="property.OR"></spring:message>
										</p></strong>
								</div>



								<div class="form-group Loc">
									<apptags:select cssClass="chosen-select-no-results"
										labelCode="property.location" items="${command.location}"
										path="specialNotGenSearchDto.locId" isMandatory="true"
										isLookUpItem="true" selectOptionLabelCode="Select location">
									</apptags:select>

								</div>
							</div>

							<div class="padding-top-15 ">
								<div class="form-group usageType">

									<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
										showOnlyLabel="false"
										pathPrefix="specialNotGenSearchDto.assdUsagetype"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										showData="true" />
								</div>
							</div>
						</div>

					</div>

			<!-- 	Start Button -->
					<div class="form-group searchBtn">
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-blue-2 " id="serchBtn"
								onclick="searchProperty()">
								<i class="fa fa-search"></i>
								<spring:message code="property.changeInAss.Search" />
							</button>
							
						
							<button type="Reset" class="btn btn-warning"  onclick="resetFormDetails(this)"><spring:message code="property.reset" text="Reset"/></button>
							
							
							
						</div>
					</div>
					<!-- 	End Button -->
					<div class="PropertyListDetails"> 
						<div class="table-responsive clear" id="PropDetails">
							<table id="datatables" class="table table-striped table-bordered">
								<thead>
									<tr>

										<th width="2%"><label class="checkbox-inline"><input
										type="checkbox" id="selectall" />Select All</label></th>
										<th width="15%"><spring:message
												code="property.PropertyNo" /></th>
										<th width="15%"><spring:message
												code="property.OldPropertyNo" /></th>
										<th width="20%"><spring:message code="property.OwnerName" /></th>
										<th width="20%"><spring:message code="property.location" /></th>
									</tr>
								</thead>
								<tbody>
								 <c:forEach var="propList" items="${command.getNotGenSearchDtoList()}" varStatus="status" >  
									<tr>
										<td align="center">
										<label class="checkbox margin-left-20"><form:checkbox
												path="specialNotGenSearchDto.genNotCheck" value="Y"
												cssClass="checkall"  id="genNotCheck" /></label>
												<form:hidden path="specialNotGenSearchDto.checkStatus" id="checkValue" value="Y"/>
										</td>
										
										<td class="text-center">${propList.propertyNo}</td>
										<td class="text-center">${propList.oldPropertyNo}</td>
										<td class="text-center">${propList.ownerName}</td>
										<td class="text-center">${propList.locationName}</td>
									</tr>
								</c:forEach>   
								</tbody>
							</table>
						</div>
					</div>

				</div>

		<!-- 	Start button -->
		<div class="form-group specialNoticebtn">
			<div class="text-center padding-bottom-10">
				<button type="button" class="btn btn-info" id="btn1" onclick="saveSpecialNotice(this)">
					Generate Special Notice</button>

			</div>
		</div>

		</form:form>
		<!-- End Form -->
	</div>
	<!-- End Widget Content -->
</div>
<!-- End Main Page Heading -->
</div>
<!-- Start Content here -->
