<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/property/PropertyNoticeDeletion.js"></script>

<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;

	/* padding-bottom: 0px;
    padding-top: 10px;
    padding-left: 5px;
    padding-right: 5px;
    background-color: #f0feff; */
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong>Demand/Warrant/Special  Notice Deletion</strong>
			</h2>	
				<apptags:helpDoc url="PropertyDemandNoticeGeneration.html"></apptags:helpDoc>	
			
		</div>

		<div class="widget-content padding">

		
			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /></span>
			</div>
		
		
			<form:form action="PropertyNoticeDeletion.html"
				class="form-horizontal form" name="propertyNotice"
				id="propertyDemandNoticeDeletion">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="accordion-toggle ">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" href="#a3">Demand/Warrant/Special  Notice Deletion</a>
							</h4>
						</div>

						<div id="a3" class="panel-collapse collapse in">
							<br>
						<div class="form-group">

										
										<apptags:select cssClass="chosen-select-no-results"
										labelCode="Notice Type" items="${command.demandType}"
										path="specialNotGenSearchDto.noticeType" isMandatory="true"
										isLookUpItem="true" selectOptionLabelCode="select type">
									</apptags:select>	
									
								 	<apptags:input labelCode="Notice No" path="specialNotGenSearchDto.noticeNo" cssClass="form-control hasNumber">
									
									</apptags:input>
									 
								
						</div>
								<div class="form-group">
								<apptags:textArea labelCode="Remarks"
									path="" isMandatory="true"
									cssClass="mandColorClass  alphaNumeric "></apptags:textArea>
								
								</div>
								
							

							<div class="form-group PropDetail ">

								
								
							</div>


							
						</div>

					</div>

		
					<div class="form-group searchBtn">
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-blue-2 " id="serchBtn"
								onclick="serachPropertyDeletion()">
								<i class="fa fa-search"></i>
								<spring:message code="property.changeInAss.Search" />
							</button>
							<button type="button" class="btn btn-warning " id="resetBtn"
								onclick="resetNoticeDeleteForm(this)">		
								Reset
							</button>
							
						</div>
					</div>
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
												path="notGenSearchDtoList[${status.index}].genNotCheck" value="Y"
												cssClass="checkall"  id="genNotCheck" /></label>
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
		<!-- 	Start button -->
		<div class="form-group ">
			<div class="text-center padding-bottom-10">
				<button type="button" class="btn btn-info" id="btn1" onclick="deleteNotice(this);">
					Delete</button>
				
				<button type="button" class="btn btn-danger" id="btn3" onclick="window.location.href='AdminHome.html'">
					Cancel</button>

			</div>
		</div>

		</form:form>
	
	</div>

</div>

</div>


