<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/objection/hearingOrderPrint.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
<div class="widget">
	<div class="widget-header">
		<h2>		
			<b><spring:message code="obj.HearingDetailEntry"/></b>
		</h2>
		
	</div>
	
	
	<div class="widget-content padding">
		<form:form action="HearingDetailEntry.html"
					class="form-horizontal" name="HearingDetailEntry"
					id="HearingDetailEntry">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>

			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a1"><spring:message code="obj.HearingDetailEntry"/></a>
					</h4>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
									<apptags:select labelCode="obj.objDept"
										items="${command.departments}" path="objectionDetailsDto.objectionDeptId"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showAll="false" isLookUpItem="true"></apptags:select>
									
									<c:set var="baseLookupCode" value="OBJ"/>
									<apptags:select labelCode="obj.objectiontype"
											items="${command.getLevelData(baseLookupCode)}"
											path=""
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showAll="false" isLookUpItem="true" changeHandler="getObjectionServiceByDept()"></apptags:select>

									
									
							</div>
							
							<div class="form-group">
							<apptags:input labelCode="obj.PropertyNoRTINoBuildingPermissionNo" path="objectionDetailsDto.objectionReferenceNumber" isMandatory="true" cssClass="alphaNumeric"></apptags:input>
							</div>
							
						</div>
						
					<!--Start Button-->
						<div class="text-center padding-15 clear">                   	
	                   		<button type="button" class="btn btn-success btn-submit" onclick="SearchRecords()"><spring:message code="property.search" text="" /></button>
	                   		<button type="Reset" class="btn btn-warning" id="resetformId" onclick="resetForm(resetformId);"><spring:message code="obj.reset"/></button>	
	                   	</div>
					<!--End Button-->
					</div>
					</div>
					<div class="panel panel-default">
					<h4 class="panel-title table padding-top-10" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a2"><spring:message code="obj.appealobjdetails"/></a>
					</h4>
					<div id="a2" class="panel-collapse collapse in">
					
						<table  class="table table-striped table-bordered margin-top-10">
                    		<thead>
		                    <tr>                  
		                        <th width="15%"><spring:message code="obj.PropertyNoRTINoBuildingPermissionNo"/></th>
								<th width="15%"><spring:message code="obj.ObjectionNo"/></th>
								<th width="15%"><spring:message code="obj.ObjectionDate"/></th>
								<th width="15%"><spring:message code="obj.Name"/></th>
								<th width="20%"><spring:message code="obj.objectiontype"/></th>
								<th width="12%"><spring:message code="obj.objstatus"/></th>
								<th width="20%"><spring:message code="obj.HearingDateTime"/></th>
								<th width="10%"><spring:message code="obj.Action"/></th>
								
		                    </tr></thead>
		                     <tbody>
		                     <c:forEach var="objDetail" items="${command.getListObjectionDetails()}" varStatus="status" >  
		                     <tr>
		                        <td>${objDetail.objectionReferenceNumber}</td>
		                        <td>${objDetail.objectionNumber}</td>
		                        <td>${objDetail.objectionDate}</td>
								<td>${objDetail.name}</td>  
		                        <td>${objDetail.objectionType}</td>
								<td>${objDetail.objectionStatus}</td>				
								<td>${objDetail.hearingDate}</td> 
		                        
	                    	<td class="text-center " ><button class="btn btn-primary btn-sm" type="button"><i class="fa fa-print fa-lg" aria-hidden="true" onclick=""></i></button></td>	                           

		                     </tr>  
 		                     </c:forEach> 
		                     </tbody>
                     </table> 
					</div>
					
			</div>
			</div>
			</form:form>
		</div>
			


</div>
</div>