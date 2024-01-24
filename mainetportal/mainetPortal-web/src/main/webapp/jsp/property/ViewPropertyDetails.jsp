<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/property/viewPropertyDetails.js"></script>
<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname eq 'NOUSER'}">
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/mainet/framework.grid.min.js"></script>
</c:if>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<script src="js\eip\citizen\citizenLoginForm.js"></script>
<script src="js\mainet\login.js"></script>

<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>
<ol class="breadcrumb">
<li><a href="CitizenHome.html" class="internal"><i class="fa fa-home"></i> Home</a></li>
<li><spring:message code="property.property" text="Property Tax"/></li><li><spring:message code="viewPropDetails" text="View Property Search"/></li></ol>

<div class="content">
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="viewPropDetails" text="View Property Search"/></strong></h2>				
				<div class="additional-btn">
					<apptags:helpDoc url="ViewPropertyDetail.html"></apptags:helpDoc>	
				</div>
	</div>

	<div class="widget-content padding">
 		
		<form:form action="ViewPropertyDetail.html"
					class="form-horizontal form" name="ViewPropertyDetail"
					id="ViewPropertyDetail">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
 <c:if test="${userSession.getCurrent().getOrganisation().getOrgid() ne '1'}"> 
			<div class="form-group orgName">
				<label class="col-sm-2 control-label"
									for="orgName"><spring:message code="property.viewPropDetails.orgName" text="Organization Name"/></label>
			 <div class="col-sm-4">									
					<form:input path="" type="text" class="form-control preventSpace" id="orgName" disabled="true" value="${userSession.getCurrent().getOrganisation().getONlsOrgname()}"/>
			</div> 
			</div>

 </c:if>		
		
		<div class="form-group">
				
				<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo" path="searchDto.proertyNo" cssClass="preventSpace"></apptags:input>
				<apptags:input labelCode="property.ChangeInAss.oldpid" path="searchDto.oldPid" cssClass="preventSpace"></apptags:input>
		</div>
			
			<p class="text-center text-small padding-bottom-10 text-red-1 "><strong><spring:message code="property.OR"></spring:message></strong></p>
	
			<div class="form-group" >
				<apptags:input labelCode="property.OwnerName" path="searchDto.ownerName" cssClass="preventSpace"></apptags:input>
				<apptags:input labelCode="ownersdetail.mobileno" path="searchDto.mobileno" cssClass="preventSpace"></apptags:input>
			
			</div>
			
			<p class="text-center text-small padding-bottom-10 text-red-1 "><strong><spring:message code="property.OR"></spring:message></strong></p>
			
			<div class="form-group wardZone">

									<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
										showOnlyLabel="false"
										pathPrefix="searchDto.assWard" 
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										showData="true" />
			</div>
			
			<div class="form-group Loc">
					<apptags:select cssClass="chosen-select-no-results"
									labelCode="property.location" items="${command.location}"
									path="searchDto.locId" 
									isLookUpItem="true" selectOptionLabelCode="select Location">
					</apptags:select>

					<apptags:input labelCode="propertydetails.csnno" path="searchDto.khasraNo"></apptags:input>										
	
			</div>
			
			<div class="form-group">
					<apptags:input labelCode="propertydetails.fromamount" path="searchDto.fromAmout" cssClass="hasNumber"></apptags:input>
					<apptags:input labelCode="propertydetails.toamount" path="searchDto.toAmount" cssClass="hasNumber"></apptags:input>
			</div>
			
		
			<div class="text-center padding-top-10">
						<button type="button" class="btn btn-blue-2" id="serchBtn"
									onclick="SearchButton()">
						<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
						</button> 	
						
						<button type="button" class="btn btn-warning" 
									onclick="" id="resetform"><spring:message code="property.Reset" text="Reset"/>
						</button>
						
						<button type="button" class="btn btn-danger" id="back"
								onclick="history.back()">
								<spring:message code="propertyBill.Back"></spring:message>
							</button>
			</div>
				
					
		<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th scope="col" width="12%" align="center"><spring:message
										code="propertydetails.PropertyNo." text="" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="propertydetails.oldpropertyno" text="" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="property.OwnerName" text="" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="property.viewPropDetails.MobileNumber" text="" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="property.viewPropDetails.LastReceiptDate" text="" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="property.viewPropDetails.OutstandingAmount" text="" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="property.action" text="" /></th> 
							</tr>
						</thead>
						<tbody>
						
						</tbody>
					</table>
		</div>
		
		
		</form:form>
	</div>
	</div>
</div>
<script>
$(function(){
	$("li.prev a").attr("aria-label","Previous Page");
	$("li.next a").attr("aria-label","Next Page");	
	
})
</script>